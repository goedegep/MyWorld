package goedegep.media.fotomapview.guifx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.eclipse.emf.common.util.WrappedException;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.google.common.geometry.S2LatLng;
import com.google.common.geometry.S2Point;
import com.google.common.geometry.S2PointIndex;

import goedegep.geo.WGS84Coordinates;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.media.app.MediaRegistry;
import goedegep.media.fotoshow.app.guifx.GatherPhotoInfoStatusPanel;
import goedegep.media.fotoshow.app.guifx.PhotoInfo;
import goedegep.media.photo.GatherPhotoInfoTask;
import goedegep.media.photo.GatherPhotoMetaDataTask;
import goedegep.media.photo.PhotoMetaData;
import goedegep.media.photo.PhotoMetaDataWithImage;
import goedegep.media.photoshow.model.FolderTimeOffsetSpecification;
import goedegep.media.photoshow.model.PhotoShowFactory;
import goedegep.media.photoshow.model.PhotoShowPackage;
import goedegep.media.photoshow.model.PhotoShowSpecification;
import goedegep.util.Tuplet;
import goedegep.util.datetime.DurationUtil;
import goedegep.util.emf.EMFResource;
import goedegep.util.img.PhotoFileMetaDataHandler;
import goedegep.util.string.StringUtil;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * This class shows photos on a map.
 * <p>
 * Photo meta data is read from a photos folder.<br/>
 * Photos, with GPS coordinates, are shown as small cameras on a map.<br/>
 * Photos, without GPS coordinates, can be dropped on their location on the map.<br/>
 * Photos shown on the map can be relocated.
 */
public class PhotoMapView extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(PhotoMapView.class.getName());
  private final static String WINDOW_TITLE = "Photo Map View";
  private final static List<String> SUPPORTED_FILE_TYPES = Arrays.asList(".jpg", ".gif", ".bmp");
  
  /**
   * Filename for the file in which the meta data of all photos is stored.
   */
  private final static String PHOTO_INFO_FILE = "photoInfo.pho";
  
  /**
   * Filename for the photo index file.
   */
  private final static String PHOTO_INDEX_FILE = "photoIndex.idx";
  
  /**
   * Maximum numbers of photo folders that can be handled at once.
   * A fixed size blocking queue is used. A non blocking queue is also non blocking at the consumer side, this would make the consumer more complex.
   */
  private final static int MAX_NUMBER_OF_PHOTO_FOLDERS = 1000;
  
  /**
   * GUI customization
   */
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  
  /**
   * Main photos folder.
   * <p>
   * This folder and all its sub folders will be scanned for photos. Folders mentioned in the <code>ignoreFolderNames</code> are skipped.
   * It is assumed that the photosFolder is set in the MediaRegistry, as it has an initial value in the property descriptors file.
   */
  private String photosFolder;
  
  /**
   * A list of folder names, separated by a semicolon, which shall be skipped while gathering photo information.
   */
  private String ignoreFolderNames;
  
  /**
   * Names of the folders for which information has to be gathered (by a GatherPhotoInfoTask).
   */
  private BlockingQueue<String> photoFoldersToScan = new ArrayBlockingQueue<>(MAX_NUMBER_OF_PHOTO_FOLDERS);
  
  /**
   * The thread for the {@code GatherPhotoInfoTask} for gathering the photo information.
   */
  private Thread gatherPhotoInfoThread = null;
  
  /**
   * A map from photo folders to the information of the photos in that folder.
   */
  private ObservableMap<String, List<PhotoMetaDataWithImage>> photoInfosPerFolder = FXCollections.observableHashMap();
  
  /**
   * A map from photo folders to the list of meta data of the photos in that folder.
   */
  private Map<String, List<PhotoMetaData>> photoMetaDatasPerFolder = new HashMap<>();
  
  /**
   * A set of modified photos.
   */
  private ObservableSet<PhotoMetaDataWithImage> modifiedPhotos = FXCollections.observableSet();
  
  /**
   * Sorted list of all photos being handled.
   */
  private ObservableList<PhotoMetaDataWithImage> photoList = FXCollections.observableArrayList();
  
  /**
   * A {@code ListView} showing the photos in the {@code photoShowList}.
   */
  private ListView<PhotoMetaDataWithImage> listView;
  
  /**
   * The map view on which the photos are shown.
   */
  private MapView mapView;
  
  /**
   * A {@link MapLayer} for the {@code mapView} to actually show and relocate the photos.
   */
  private PhotoMapLayer photoMapLayer;
  
  /**
   * Status panel, shown on the bottom.
   */
  private GatherPhotoInfoStatusPanel statusPanel;
  
  /**
   * Indication of whether the maximum number of photo folders is exceeded.
   */
  private boolean maxNumberOfPhotoFoldersExceeded = false;
  
  /**
   * A list of GUI nodes which are disabled when certain computations take place.
   * <p>
   * Handling user actions, while photo information is gathered, can be very complicated.
   * Therefore user actions are blocked while photo information is gathered.
   */
  private List<Object> guiNodesToBeDisabled = new ArrayList<>();
  
  /**
   * A {@code PhotoShowSpecification} which is being opened.
   * <p>
   * This has a value from reading the file until all information from this specification is handled. After this it's set back to null;
   */
  private PhotoShowSpecification photoShowSpecification = null;
  
  /**
   *  Temporary information to handle an opened specification.
   *  <p>
   *  If not 0, we're handling a just opened specification.
   */
  private int openSpecificationNumberOfFoldersToHandle = 0;
  
  /**
   * Point index.
   */
  private S2PointIndex<PhotoMetaData> index;
  
  /**
   * Edit mode menu item.
   * <p>
   * In edit mode, no index is used on the map layer.
   * TODO maybe this isn't true. when dropped on the map, the photo directly gets coordinates and it is on the visible part of the map.
   */
  CheckMenuItem editModeMenuItem;
  
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  public PhotoMapView(CustomizationFx customization) {
    super(WINDOW_TITLE, customization);

    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    
    PhotoListCell.setCustomization(customization);
    PhotoMetaDataEditor.setModifiedPhotos(modifiedPhotos);
    
//    photosFolder = MediaRegistry.photosFolder + "\\Vakanties\\2022-10-24 Hotel Koener - Clervaux";
    photosFolder = MediaRegistry.photosFolder;
    ignoreFolderNames = MediaRegistry.ignoreFolderNames;
    
    createGUI();
    
    // If an item is selected in the list view, this photo will also be selected in the map view and we fly to this photo.
    listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      photoMapLayer.setSelectedPhoto(this, newValue);
      if (newValue.getCoordinates() != null) {
        MapPoint mapPoint = new MapPoint(newValue.getCoordinates().getLatitude(), newValue.getCoordinates().getLongitude());
        mapView.flyTo(0.2, mapPoint, 2);
        mapView.setZoom(18);
      }
    });
    
    statusPanel.updateText("Nothing to report for now");
    
    // Update the dirty indication in the window title when photos are changed.
    modifiedPhotos.addListener((javafx.collections.SetChangeListener.Change<? extends PhotoMetaDataWithImage> change) -> updateWindowTitle());
    
    /*
     *  React to changes in the photoInfosPerFolder map
     *  For a new entry, the photos have to be added to the photoList and, if it has coordinates, also to the map view.
     *  For a deleted entry, the photos have to be removed from this list and the map.
     */
    photoInfosPerFolder.addListener((javafx.collections.MapChangeListener.Change<? extends String, ? extends List<PhotoMetaDataWithImage>> change) -> {
      if (change.wasAdded()) {
        handlePhotoFolderAddedToPhotoInfoListsMap(change.getKey(), change.getValueAdded());
      } else if (change.wasRemoved()) {
        photoList.removeAll(change.getValueRemoved());
      } else {
        throw new RuntimeException("Unknown change in photoInfoListsMap: " + change.toString());
      }
    });
    
    // react to changes in the photoList.
    photoList.addListener(new ListChangeListener<PhotoMetaDataWithImage>() {
      boolean handlingChange = false;

      @Override
      public void onChanged(Change<? extends PhotoMetaDataWithImage> change) {
        LOGGER.info("photoShowList changed");
        if (!handlingChange) {
          handlingChange = true;
          handleChangedPhotoList();
          handlingChange = false;
        }
        
      }
      
    });
    
    // If a new photo is selected on the map view, this is also selected in the list view.
    photoMapLayer.addObjectSelectionListener(this::handleNewPhotoSelected);
    
    show();
    
    // try to read the index and if it doesn't propose the user to create it.
    readIndex();
    handleChangedPhotoList();
    updateWindowTitle();
    
  }
  
  /**
   * Read the photo index.
   * <p>
   * If the 'index' file doesn't exist yet, the user is advised to create it now.
   * Upon a positive answer, the index is created by calling {@link #createOrUpdateIndex()}.
   * 
   * What is read from the file is not the index, but the photo meta data per folder (as the index isn't serializable).
   * After reading the meta data per folder, {@link PhotoMapView#createIndexFromMetaData()} is called to actually create the index.
   */
  @SuppressWarnings("unchecked")
  private void readIndex() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getPhotoIndexFilename()))) {
      photoMetaDatasPerFolder  = (Map<String, List<PhotoMetaData>>) ois.readObject();
      createIndexFromMetaData();
      handleNewIndex();
    } catch (FileNotFoundException e) {
      Optional<ButtonType> answer = componentFactory.createYesNoConfirmationDialog("Photo index not existing",
          "The photo index file doesn't exist yet. It is advised to create it now (which takes quite some time)", "Create photo index?").showAndWait();
      if (answer.isPresent() && answer.get().equals(ButtonType.YES)) {
        createOrUpdateIndex();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Write the photo index.
   * <p>
   * Actually not the index is written, but the photo meta data per folder (as the index isn't serializable).
   */
  private void writeIndex() {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getPhotoIndexFilename()))) {
      out.writeObject(photoMetaDatasPerFolder);
   } catch (FileNotFoundException e) {
     e.printStackTrace();
   } catch (IOException e) {
     e.printStackTrace();
   }
    
  }
  
  /**
   * Create the index from the photo meta data per folder.
   */
  private void createIndexFromMetaData() {
    index = new S2PointIndex<>();

    for (String folder: photoMetaDatasPerFolder.keySet()) {
      for (PhotoMetaData photoMetaData: photoMetaDatasPerFolder.get(folder)) {
        if (photoMetaData.getCoordinates() != null) {
          WGS84Coordinates coordinates = photoMetaData.getCoordinates();
          S2LatLng latLng = coordinates.getS2LatLng();
          S2Point s2Point = latLng.toPoint();
          index.add(s2Point, photoMetaData);

        }
      }
    }
  }
  
  /**
   * Get the filename of the photo index file.
   * 
   * @return the filename of the photo index file.
   */
  private String getPhotoIndexFilename() {
    File photoIndexFile = new File(photosFolder, PHOTO_INDEX_FILE);
    return photoIndexFile.getAbsolutePath();
  }
  
  /**
   * Create or update (in case an index already exists) the index.
   * 
   */
  private void createOrUpdateIndex() {
    createGatherPhotoMetaDataTask();
  }
  
  /**
   * Handle the fact that a new index is available.
   * <p>
   * The index is set on the map photo layer.
   */
  private void handleNewIndex() {
    photoMapLayer.setPhotoIndex(index);
  }
    
  /**
   * Add dropped photos.
   * <p>
   * A dropped photo may already exist in the {@code photoInfosPerFolder} or it's a 'new' photo dropped from the file system.
   * In the first case, it just has to be added to the map view.
   * In the second case, the {@code PhotoMetaDataWithImage} has to be gathered, and the photo has to be added to the {@code photoInfosPerFolder}.
   * In this case the photo will automatically be added to the list view and the map view.
   */
  public void addPhotos(List<File> photoFiles, WGS84Coordinates coordinates) {
    ObservableMap<String, List<PhotoMetaDataWithImage>> newPhotoInfosPerFolder = FXCollections.observableHashMap();

    for (File file: photoFiles) {
      LOGGER.info(file.getAbsolutePath());
      PhotoMetaDataWithImage photoInfo = getPhotoInfoForFile(file);
      if (photoInfo != null) {
        if (photoInfo.getCoordinates() == null) {
          photoInfo.getPhotoMetaData().setCoordinates(coordinates);
        }
        photoMapLayer.addPhoto(photoInfo);
      } else {

        photoInfo = GatherPhotoInfoTask.gatherPhotoMetaDataWithImage(file.getAbsolutePath(), 150);
        if (photoInfo.getCoordinates() == null) {
          photoInfo.getPhotoMetaData().setCoordinates(coordinates);
          modifiedPhotos.add(photoInfo);
        } else {
          LOGGER.info("Coordinates already set");
        }

        String folder = file.getParent();
        List<PhotoMetaDataWithImage> photoInfos = newPhotoInfosPerFolder.get(folder);
        if (photoInfos == null) {
          photoInfos = new ArrayList<>();
          newPhotoInfosPerFolder.put(folder, photoInfos);
        }

        photoInfos.add(photoInfo);
      }
    }
    
    for (String folder: newPhotoInfosPerFolder.keySet()) {
      List<PhotoMetaDataWithImage> newPhotoInfos = newPhotoInfosPerFolder.get(folder);
      
      List<PhotoMetaDataWithImage> photoInfos = photoInfosPerFolder.get(folder);
      if (photoInfos != null) {
        photoInfos.addAll(newPhotoInfos);
        photoList.addAll(newPhotoInfos);
      } else {
        photoInfosPerFolder.put(folder, newPhotoInfos);
      }
    }
  }
  

  /**
   * Get the {@code PhotoMetaDataWithImage} for a file from the {@code photoInfosPerFolder}.
   * 
   * @param file a {@code File}
   * @return the {@code PhotoMetaDataWithImage} for {@code file}, or null if the information is not available.
   */
  private PhotoMetaDataWithImage getPhotoInfoForFile(File file) {
    String folder = file.getParent();
    String filename = file.getAbsolutePath();
    List<PhotoMetaDataWithImage> photoInfos = photoInfosPerFolder.get(folder);
    if (photoInfos != null) {
      for (PhotoMetaDataWithImage photoInfo: photoInfos) {
        if (filename.equals(photoInfo.getFileName())) {
          return photoInfo;
        }
      }
    }
    
    return null;
  }

  /**
   * Create the GUI.
   */
  private void createGUI() {
    
    /*
     * Main pane is a BorderPane.
     * North is the menu bar
     * Center is a splitpane with opened photos list on the left and the map view on the right.
     * Bottom is a {@code GatherPhotoInfoStatusPanel}
     */
    
    BorderPane mainPane = new BorderPane();
    mainPane.setTop(createMenuBar());

    // Center pane
    SplitPane centerPane = new SplitPane();
    centerPane.setOrientation(Orientation.HORIZONTAL);
    centerPane.setDividerPositions(0.2);
    
    // Photos list
    centerPane.getItems().add(createPhotosListPane());

    // MapView
    mapView = new MapView();
    mapView.setZoom(3);
    mapView.setCenter(0, 0);
    mapView.prefWidthProperty().bind(mainPane.prefWidthProperty());
    mapView.prefHeightProperty().bind(mainPane.prefHeightProperty());
    mapView.prefWidthProperty().bind(mainPane.widthProperty());
    mapView.prefHeightProperty().bind(mainPane.heightProperty());
    
    photoMapLayer = new PhotoMapLayer(customization, this, mapView, photoInfosPerFolder, modifiedPhotos, photosFolder);
    centerPane.getItems().add(mapView);
   
    mainPane.setCenter(centerPane);
    
    // Status panel
    statusPanel = new GatherPhotoInfoStatusPanel(componentFactory);    
    mainPane.setBottom(statusPanel);

    setScene(new Scene(mainPane, 1700, 900));
  }
  
  /**
   * Create the ... 
   */
  private void createGatherPhotoMetaDataTask() {
    /*
     * Create a GatherPhotoMetaDataTask and handle information provided by this task.
     */
    GatherPhotoMetaDataTask gatherPhotoMetaDataTask = new GatherPhotoMetaDataTask(photosFolder, StringUtil.semicolonSeparatedValuesToListOfValues(ignoreFolderNames), SUPPORTED_FILE_TYPES);

    // Handle results - new information for a folder is added to the ....
    gatherPhotoMetaDataTask.valueProperty().addListener(
        (ObservableValue<? extends Tuplet<String, List<PhotoMetaData>>> observable,
            Tuplet<String, List<PhotoMetaData>> oldValue, Tuplet<String, List<PhotoMetaData>> newValue) -> {
              if (newValue != null) {
                LOGGER.info("Info obtained for folder: " + newValue);
                photoMetaDatasPerFolder.put(newValue.getObject1(), newValue.getObject2());
              } else {
                // gathering data has finished
                handleMetaDataAvailable();
              }
              
              //      photoInfosPerFolder.put(newValue.getObject1(), newValue.getObject2());
            });

    // Handle messages - messages are shown in the message part of the statusPanel
    gatherPhotoMetaDataTask.messageProperty().addListener((observable, oldValue , newValue) -> statusPanel.updateText(newValue));

    // Handle progress reports - progress is shown in the progress bar of the statusPanel
    gatherPhotoMetaDataTask.progressProperty().addListener((observable, oldValue , newValue) -> statusPanel.updateProgress(newValue.doubleValue()));

    Thread gatherPhotoMetaDataThread = new Thread(gatherPhotoMetaDataTask);
    gatherPhotoMetaDataThread.setDaemon(true);
    gatherPhotoMetaDataThread.start();
  }
  
  private void handleMetaDataAvailable() {
    createIndexFromMetaData();
    
//    S2ClosestPointQuery<PhotoMetaData> query = new S2ClosestPointQuery<>(index);
//    S2LatLng corner1 = S2LatLng.fromDegrees(50.04350248302872, 6.009415947953926);
//    S2LatLng corner2 = S2LatLng.fromDegrees(50.09961374901469, 6.147143184183932);
//    S2LatLngRect rect = new S2LatLngRect(corner1, corner2);
//    S2LatLng pointLatLng = S2LatLng.fromDegrees(50.05, 6.1);
//    S2Point point = pointLatLng.toPoint();
//    query.setMaxPoints(15);
//    query.setRegion(rect);
//    for (Result<PhotoMetaData> result: query.findClosestPoints(point)) {
//      LOGGER.severe("Result: " + result.distance() + ", " + result.entry().data());
//    }
    
    handleNewIndex();
    
    writeIndex();
  }
  
  /**
   * Create a GatherPhotoInfoTask, if it doesn't exist yet, and handle information provided by this task
   */
  private void createGatherPhotoInfoTaskIfNotYetDone() {
    if (gatherPhotoInfoThread != null) {
      return;
    }
    
    /*
     * Create a GatherPhotoInfoTask and handle information provided by this task
     */
    GatherPhotoInfoTask gatherPhotoInfoTask = new GatherPhotoInfoTask(photoFoldersToScan, SUPPORTED_FILE_TYPES, 150);
    
    // Handle results - new information for a folder is added to the photoInfosPerFolder map.
    gatherPhotoInfoTask.valueProperty().addListener(
        (ObservableValue<? extends Tuplet<String, List<PhotoMetaDataWithImage>>> observable,
        Tuplet<String, List<PhotoMetaDataWithImage>> oldValue, Tuplet<String, List<PhotoMetaDataWithImage>> newValue) -> {
      LOGGER.info("Info obtained for folder: " + newValue.getObject1());
      photoInfosPerFolder.put(newValue.getObject1(), newValue.getObject2());
    });
    
    // Handle messages - messages are shown in the message part of the statusPanel
    gatherPhotoInfoTask.messageProperty().addListener((observable, oldValue , newValue) -> statusPanel.updateText(newValue));
    
    // Handle progress reports - progress is shown in the progress bar of the statusPanel
    gatherPhotoInfoTask.progressProperty().addListener((observable, oldValue , newValue) -> statusPanel.updateProgress(newValue.doubleValue()));

    gatherPhotoInfoThread = new Thread(gatherPhotoInfoTask);
    gatherPhotoInfoThread.setDaemon(true);
    gatherPhotoInfoThread.start();
  }
  
  /**
   * Create the panel in which the photo thumbnails are shown
   * 
   * @return the created panel (being {@code listView}).
   */
  private Node createPhotosListPane() {
    listView = new ListView<>();
    listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    
    listView.setCellFactory(listView -> new PhotoListCell());
    
    listView.setMinHeight(600.0);
    listView.setMinWidth(500.0);
    
    return listView;
  }
  
  /**
   * Handle a change in the photo list.
   * <p>
   * Clear the {@code listView} and add all photos.
   * Clear the {@code photoMapLayer} and add all photos.
   */
  private void handleChangedPhotoList() {
    
    // sort the list
    Collections.sort(photoList, PhotoInfo.getSortingDateTimeComparator());
    
    listView.getItems().clear();
    listView.getItems().addAll(photoList);
    
    photoMapLayer.clear();
//    S2PointIndex<PhotoMetaDataWithImage> index = new S2PointIndex<>();

    for (PhotoMetaDataWithImage photoInfo: photoList) {
      if (photoInfo.getCoordinates() != null) {
        photoMapLayer.addPhoto(photoInfo);
//        
//        
//        WGS84Coordinates coordinates = photoInfo.getCoordinates();
//        S2LatLng latLng = coordinates.getS2LatLng();
//        S2Point s2Point = latLng.toPoint();
//        index.add(s2Point, photoInfo);
//        
//      }
    }
//    S2ClosestPointQuery<PhotoMetaDataWithImage> query = new S2ClosestPointQuery<>(index);
//    S2LatLng corner1 = S2LatLng.fromDegrees(50.04350248302872, 6.009415947953926);
//    S2LatLng corner2 = S2LatLng.fromDegrees(50.09961374901469, 6.147143184183932);
//    S2LatLngRect rect = new S2LatLngRect(corner1, corner2);
//    S2LatLng pointLatLng = S2LatLng.fromDegrees(50.05, 6.1);
//    S2Point point = pointLatLng.toPoint();
//    query.setMaxPoints(15);
//    query.setRegion(rect);
//    for (Result<PhotoMetaDataWithImage> result: query.findClosestPoints(point)) {
//      LOGGER.severe("Result: " + result.distance() + ", " + result.entry().data());
    }
  }

  /**
   * Create the menu bar for this window.
   * 
   * @return the menu bar for this window.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = componentFactory.createMenuBar();
    Menu menu;
    MenuItem menuItem;
    RadioMenuItem radioMenuItem;

    // File menu
    menu = componentFactory.createMenu("File");
    
    // File: Open photo folder
    menuItem = componentFactory.createMenuItem("Open Photo folder ...");
    menuItem.setOnAction(event -> handleOpenFolderRequest());    
    guiNodesToBeDisabled.add(menuItem);
    menu.getItems().add(menuItem);
    
    // File: Open photo show file
    menuItem = componentFactory.createMenuItem("Open Photo Show Specification ...");
    menuItem.setOnAction(event -> handleOpenPhotoShowSpecificationRequest());
    menu.getItems().add(menuItem);
    
    // File: Save modified photos
    menuItem = componentFactory.createMenuItem("Save modified photos");
    menuItem.setOnAction(event -> showModifiedPhotosDialog());
    menu.getItems().add(menuItem);
    
//    // File: Save photo information
//    menuItem = componentFactory.createMenuItem("Save photo information");
//    menuItem.setOnAction(event -> writePhotoInfosPerFolder());
//    menu.getItems().add(menuItem);
    
    // File: Create/update index
    menuItem = componentFactory.createMenuItem("Create/update index");
    menuItem.setOnAction(event -> createOrUpdateIndex());
    menu.getItems().add(menuItem);
        
    menuBar.getMenus().add(menu);
    
    // Settings menu
    menu = componentFactory.createMenu("Show");
    
    // Settings: Edit mode
    CheckMenuItem editModeMenuItem = componentFactory.createCheckMenuItem("Edit mode (no index used)");
    editModeMenuItem.setSelected(false);
    
    editModeMenuItem.setOnAction(event -> handleNewEditMode());
    menu.getItems().add(editModeMenuItem);
    
    
    // Settings: Show selected photos / Show all photos in the index
    ToggleGroup toggleGroup = new ToggleGroup();
    radioMenuItem = componentFactory.createRadioMenuItem("Show selected photos");
    radioMenuItem.setOnAction(event -> showIndexPhotos(false));
    radioMenuItem.setToggleGroup(toggleGroup);
    menu.getItems().add(radioMenuItem);
    radioMenuItem = componentFactory.createRadioMenuItem("Show all photos in the index");
    radioMenuItem.setOnAction(event -> showIndexPhotos(true));
    radioMenuItem.setToggleGroup(toggleGroup);
    menu.getItems().add(radioMenuItem);
    
    menuBar.getMenus().add(menu);
    

    return menuBar;
  }
  
  private void handleNewEditMode() {
    
  }
  
  private void showIndexPhotos(boolean showIndexPhotos) {
    photoMapLayer.showPhotoIndexOnMap(showIndexPhotos);
  }
  
  /**
   * Open a photo folder.
   * <p>
   * The user can select a folder via a DirectoryChooser.
   * For the selected folder ...
   */
  private void handleOpenFolderRequest() {
    DirectoryChooser directoryChooser = componentFactory.createDirectoryChooser("Open Photo folder");
    directoryChooser.setInitialDirectory(new File(photosFolder));
    
    File photoFolder = directoryChooser.showDialog(this);
    LOGGER.info("Opening: " + (photoFolder != null ? photoFolder.getAbsolutePath() : "null"));
    
    if (photoFolder != null) {
      List<File> photoFolders = new ArrayList<>();
      photoFolders.add(photoFolder);
      gatherPhotoInformation(photoFolders);
      

      componentFactory.createInformationDialog(null,
          "Photo folder " + photoFolder + " is being opened.",
          "").showAndWait();
    }
  }
  
  /**
   * Open a {@code PhotoShowSpecification}
   */
  private void handleOpenPhotoShowSpecificationRequest() {
    FileChooser fileChooser = componentFactory.createFileChooser("Open Photo Show Specification");
    fileChooser.setInitialDirectory(new File(photosFolder));
    File photoShowSpecificationFile = fileChooser.showOpenDialog(this);
    LOGGER.info("Opening: " + (photoShowSpecificationFile != null ? photoShowSpecificationFile.getAbsolutePath() : "null"));

    EMFResource<PhotoShowSpecification> emfResource = new EMFResource<PhotoShowSpecification>(
        PhotoShowPackage.eINSTANCE,
        () -> PhotoShowFactory.eINSTANCE.createPhotoShowSpecification());

    try {
      photoShowSpecification = emfResource.load(photoShowSpecificationFile.getAbsolutePath());

      List<File> photoFolders = new ArrayList<>();
      for (String photoFolderName: photoShowSpecification.getPhotoFolders()) {
        photoFolders.add(new File(photoFolderName));
      }
      gatherPhotoInformation(photoFolders);

      componentFactory.createInformationDialog(null,
          "Photoshow specification " + photoShowSpecificationFile.getAbsolutePath() + " is being opened.",
          "").showAndWait();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (WrappedException wrappedException) {
      componentFactory.createExceptionDialog("An exception occurred while reading the file: '" + photoShowSpecificationFile.getAbsolutePath() + "'.", wrappedException).show();
    }

  }
  
  /**
   * Start gathering photo information for the photos in a list of folders (and its subfolders).
   * <p>
   * The guiNodesToBeDisabled are locked as we don't support opening a second set of folders while we are gathering information.
   * 
   * @param photoFolders the photo folders to be scanned.
   */
  private void gatherPhotoInformation(List<File> photoFolders) {
    lockGUI();

    openSpecificationNumberOfFoldersToHandle =  photoFolders.size();
    
    photoInfosPerFolder.clear();
    createGatherPhotoInfoTaskIfNotYetDone();
    
    List<String> ignoreFolders = StringUtil.semicolonSeparatedValuesToListOfValues(ignoreFolderNames);

    List<Path> photoFolderPaths = new ArrayList<>();
    for (File photoFolder: photoFolders) {
      photoFolderPaths.add(Paths.get(photoFolder.getAbsolutePath()));
    }
    scanFolders(photoFolderPaths, ignoreFolders);
  }
  
  /**
   * Scan folders and sub-folders.
   * 
   * @param photosFolderPaths the folders to be scanned.
   * @param ignoreFolders the folders to be ignored.
   */
  private void scanFolders(List<Path> photosFolderPaths, List<String> ignoreFolders) {
    for (Path photosFolder: photosFolderPaths) {
      scanFolders(photosFolder, ignoreFolders);
    }
  }
  
  /**
   * Scan a photo folder hierarchy for all folders to obtain information from,
   * and add these folders to the request queue for gathering photo information.
   * 
   * @param photosFolder the folder, with its sub-folders, to scan
   * @param ignoreFolders The folder names to ignore.
   */
  private void scanFolders(Path photosFolder, List<String> ignoreFolders) {
    LOGGER.info("photosFolder=" + photosFolder.toAbsolutePath().toString());
    
    if (maxNumberOfPhotoFoldersExceeded) {
      return;
    }
    
    if (photoFoldersToScan.remainingCapacity() == 0) {
      componentFactory.createErrorDialog("Maximum number of photo folders exceeded.", "This application can only handle " + photoFoldersToScan.size() + " photo folders").showAndWait();
      maxNumberOfPhotoFoldersExceeded = true;
      return;
    }
    
    String directoryName = photosFolder.getFileName().toString();
    if (!ignoreFolders.contains(directoryName)) {
      try {
        photoFoldersToScan.put(photosFolder.toAbsolutePath().toString());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      LOGGER.info("Skipping: " + photosFolder.toAbsolutePath().toString());
    }
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(photosFolder)) {
      for (Path checkDirectory: stream) {
        if (Files.isDirectory(checkDirectory)) {
          LOGGER.info("Handling: " + checkDirectory.toAbsolutePath().toString());
          scanFolders(checkDirectory, ignoreFolders);
        } else {
          // It's a file; no action.
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
  }
  
  /**
   * Lock the {@code guiNodesToBeDisabled}.
   */
  private void lockGUI() {
    for (Object object: guiNodesToBeDisabled) {
      if (object instanceof MenuItem menuItem) {
        menuItem.setDisable(true);
      }
    }
  }
  
  /**
   * Unlock the {@code guiNodesToBeDisabled}.
   */
  private void unlockGUI() {
    for (Object object: guiNodesToBeDisabled) {
      if (object instanceof MenuItem menuItem) {
        menuItem.setDisable(false);
      }
    }
  }
    
  /**
   * Handle the fact that information of a photo folder is added to the photoInfoListsMap.
   * <p>
   * This is called when photo information for a new folder is added to the {@code photoInfosPerFolder} map.
   * The sorting times are updated, based on information in the specification.
   * All the photos are added to the photoShowList.
   * 
   * @param photoFolderName name of the added photo folder
   * @param photoInfoList information on all the photos in this folder
   */
  private void handlePhotoFolderAddedToPhotoInfoListsMap(String photoFolderName, List<PhotoMetaDataWithImage> photoInfoList) {
    LOGGER.info("=> photoFolderName=" + photoFolderName + ", number of photos=" + photoInfoList.size());

    if (photoShowSpecification != null) {
      updatePhotoInfoSortingTimesForOneFolder(photoFolderName);
    }

    photoList.addAll(photoInfoList);

    // Unlock the GUI if this was the last folder to handle.
    if (openSpecificationNumberOfFoldersToHandle != 0) {
      openSpecificationNumberOfFoldersToHandle--;
      if (openSpecificationNumberOfFoldersToHandle == 0) {
        unlockGUI();
        photoShowSpecification = null;
      }
    }

    LOGGER.info("<=");
  }
  
  /**
   * Update the Sorting Times in all PhotoMetaDataWithImage for one folder.
   * 
   * @param folderName The name of the folder for which the Sorting Times are to be updated.
   */
  private void updatePhotoInfoSortingTimesForOneFolder(String folderName) {
    List<PhotoMetaDataWithImage> photoInfoList = photoInfosPerFolder.get(folderName);

    // clear the sorting times for all photos in this folder
    for (PhotoMetaDataWithImage photoInfo: photoInfoList) {
      ((PhotoInfo) photoInfo).setSortingDateTime(null);
    }

    // Check all 'folder time offset specifications'. If it is for this folder, apply the offset to all photos of this folder.
    for (FolderTimeOffsetSpecification folderTimeOffsetSpecification: photoShowSpecification.getFolderTimeOffsetSpecifications()) {
      String timeOffsetFolderName = folderTimeOffsetSpecification.getFolderName();
      if (timeOffsetFolderName.equals(folderName)) {
        Duration timeOffset = DurationUtil.durationFromString(folderTimeOffsetSpecification.getTimeOffset());
        LOGGER.fine("timeOffset: " + timeOffset.toString());
        
        for (PhotoMetaDataWithImage photoInfo: photoInfoList) {
          ((PhotoInfo) photoInfo).setSortingDateTime(photoInfo.getDeviceSpecificPhotoTakenTime().plus(timeOffset));
          LOGGER.fine("file: " + photoInfo.getFileName() + 
              ", taken at: " + photoInfo.getDeviceSpecificPhotoTakenTime() +
              ", sorting time: " + ((PhotoInfo) photoInfo).getSortingDateTime());
        }
      }
    }
  }
  
  /**
   * Show a dialog with the photos that have been modified and let the user select which ones to save.
   */
  private void showModifiedPhotosDialog() {
    SaveModifiedPhotosDialog saveModifiedPhotosDialog = new SaveModifiedPhotosDialog(customization, this, modifiedPhotos);
    Optional<ButtonType> result = saveModifiedPhotosDialog.showAndWait();
    if (result.isPresent()  &&  result.get() == ButtonType.OK) {
      List<PhotoMetaDataWithImage> photosToSave = saveModifiedPhotosDialog.getSelectedPhotos();
      try {
        for (PhotoMetaDataWithImage photoInfo: photosToSave) {
          LOGGER.info("Saving: " + photoInfo.getFileName());
          PhotoFileMetaDataHandler.writeGeoLocationAndTitle(new File(photoInfo.getFileName()), photoInfo.getCoordinates(), photoInfo.isApproximateGPScoordinates(), photoInfo.getTitle());
          modifiedPhotos.remove(photoInfo);
        }
      } catch (ImageReadException | ImageWriteException | IOException e) {
        e.printStackTrace();
      }
    } else {
      LOGGER.info("Save cancelled.");
    }
  }
  
  /**
   * Handle the fact that a new photo is selected on the map view.
   * 
   * @param source the object responsible for the change.
   * @param photoInfo the new selected photo.
   */
  private void handleNewPhotoSelected(Object source, PhotoMetaDataWithImage photoInfo) {
    if (this.equals(source)) {
      // no action if we caused the change.
      return;
    }
    
    listView.getSelectionModel().select(photoInfo);
    listView.scrollTo(photoInfo);
  }

  /**
   * Update the window title.
   * <p>
   * A dirty indicator ('*') is shown before the window title if there is at least one modified photo.
   */
  private void updateWindowTitle() {
    StringBuilder buf = new StringBuilder();
    
    if (!modifiedPhotos.isEmpty()) {
      buf.append("*");
    }
    
    buf.append(WINDOW_TITLE);
    
    setTitle(buf.toString());
  }
}
