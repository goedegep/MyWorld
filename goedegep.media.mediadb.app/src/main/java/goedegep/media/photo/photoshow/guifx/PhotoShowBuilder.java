package goedegep.media.photo.photoshow.guifx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.geo.WGS84Coordinates;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.media.common.MediaRegistry;
import goedegep.media.photo.GatherPhotoInfoTask;
import goedegep.media.photo.IPhotoMetaDataWithImage;
import goedegep.media.photo.photoshow.logic.OrderedNameGenerator;
import goedegep.media.photo.photoshow.logic.PhotoshowCommons;
import goedegep.media.photoshow.model.FolderTimeOffsetSpecification;
import goedegep.media.photoshow.model.PhotoShowFactory;
import goedegep.media.photoshow.model.PhotoShowPackage;
import goedegep.media.photoshow.model.PhotoShowSpecification;
import goedegep.util.Tuplet;
import goedegep.util.datetime.DurationFormat;
import goedegep.util.emf.EMFResource;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import goedegep.util.mslinks.ShellLink;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * This class provides a photo show builder.
 * <p>
 * <ul>
 *   <li>
 *   You start by specifying a folder, of which the sub-folders contain the photos to be shown.
 *   </li>
 * </ul>
 */
public class PhotoShowBuilder extends JfxStage {
  private final static Logger LOGGER = Logger.getLogger(PhotoShowBuilder.class.getName());
  private final static String NEWLINE = System.getProperty("line.separator");
  private final static DurationFormat DF = new DurationFormat();
  
  private final static String WINDOW_TITLE = "Photoshow builder";
  
  private final static List<String> SUPPORTED_FILE_TYPES = Arrays.asList(".jpg");
  private static final PhotoShowFactory PHOTO_SHOW_FACTORY = PhotoShowFactory.eINSTANCE;

  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private final Stage thisStage = this;
  
  // GUI objects
  /**
   * A tree view, in the top left corner, with the complete specification for the show.
   */
  EObjectTreeView photoShowSpecificationTreeView = null;
  
  private Label photoShowLabel;                        // label on top of the Photo Show panel
  private GatherPhotoInfoStatusPanel statusPanel;                     // shows status information.
  private List<Node> guiNodesToBeDisabled = new ArrayList<>();  // a list of GUI nodes which are disabled when certain computations take place.
  
  // State information
  private ShowFileType showFileType = ShowFileType.SHORTCUT;
  
  /**
   * The currently selected main folder for the photos to be shown. This value is only stored to set as initial folder in folder selection wizard.
   * The default value is taken from the Registry.
   */
  private String currentlySelectedFolder = null;
  private String ignoreFolders = null;                 // Stored to set as initial value in the folder selection wizard. Default is taken from the Registry.
  private ObservableMap<String, List<IPhotoInfo>> photoInfoListsMap = FXCollections.observableHashMap();  // Photo info of all photos per folder
  
  /**
   * The current photo show list (including photos not selected for the show).
   */
  private ObservableList<IPhotoInfo> photoShowList = FXCollections.observableArrayList(IPhotoInfo.extractor());
  private ListView<IPhotoInfo> listView;                                                    // Displays the photoShowList
  private EMFResource<PhotoShowSpecification> emfResource = null;                          // For loading and storing to a file
  private PhotoShowSpecification photoShowSpecification = null;                            // Specification of the show
  private Thread gatherPhotoInfoThread = null;
    
  // Names of the folders for which information has to be gathered (by a GatherPhotoInfoTask)
  private BlockingQueue<String> photoFoldersToHandle= new ArrayBlockingQueue<>(20);
  
  // Temporary information to handle an opened specification
  private int openSpecificationNumberOfFoldersToHandle = 0;  // If not 0, we're handling a just opened specification.
  private List<String> openSpecificationPhotosToShow;

  public PhotoShowBuilder(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
        
    // react to changes in the photoShowList.
    photoShowList.addListener(new ListChangeListener<IPhotoInfo>() {
      boolean handlingChange = false;

      @Override
      public void onChanged(Change<? extends IPhotoInfo> change) {
        if (!handlingChange) {
          handlingChange = true;
          handleChangedPhotoShowList();
          handlingChange = false;
        }
        
      }
      
    });
    
    /*
     *  react to changes in the photoInfoListsMap
     *  For a new entry, the photos have to be added to the photoShowList.
     *  For a deleted entry, the photos have to be removed from this list.
     */
    photoInfoListsMap.addListener(new MapChangeListener<String, List<IPhotoInfo>>() {

      @Override
      public void onChanged(Change<? extends String, ? extends List<IPhotoInfo>> change) {
        LOGGER.fine("=> change=" + change.toString());
        if (change.wasAdded()) {
          change.getKey();
          change.getValueAdded();
          handlePhotoFolderAddedToPhotoInfoListsMap(change.getKey(), change.getValueAdded());
        } else if (change.wasRemoved()) {
          handlePhotoFolderRemovedFromPhotoInfoListsMap();
        } else {
          throw new RuntimeException("Unknown change in photoInfoListsMap: " + change.toString());
        }
        
      }
      
    });
    
    createGatherPhotoInfoTaskIfNotYetDone();
    
    /*
     * Create the resource, and start with a new (empty) specification.
     */
    emfResource = new EMFResource<PhotoShowSpecification>(
        PhotoShowPackage.eINSTANCE,
        () -> PhotoShowFactory.eINSTANCE.createPhotoShowSpecification(),
        ".xmi");
    photoShowSpecification = emfResource.newEObject();
    
    createGUI();
    
    updateWindowTitle();
    updatePhotoShowLabel();
    statusPanel.updateText("Nothing to report for now");
    
    getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    
    // Update the window title if the 'dirtyness' of the specification changes.
    emfResource.dirtyProperty().addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        updateWindowTitle();        
      }
      
    });
    
    // Update the window title if the file name changes
    emfResource.fileNameProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        updateWindowTitle();
      }
      
    });
    
    handleNewPhotoShowSpecification();
  }
  
  /**
   * Create a GatherPhotoInfoTask and handle information provided by this task
   */
  private void createGatherPhotoInfoTaskIfNotYetDone() {
    if (gatherPhotoInfoThread != null) {
      return;
    }
    
    /*
     * Create a GatherPhotoInfoTask and handle information provided by this task
     */
    GatherPhotoInfoTask gatherPhotoInfoTask = new GatherPhotoInfoTask(photoFoldersToHandle, SUPPORTED_FILE_TYPES, 150);
    
    // Handle results - new information for a folder is added to the photoInfoListsMap.
    gatherPhotoInfoTask.valueProperty().addListener(new ChangeListener<Tuplet<String, List<IPhotoMetaDataWithImage>>>() {

      @Override
      public void changed(ObservableValue<? extends Tuplet<String, List<IPhotoMetaDataWithImage>>> observable,
          Tuplet<String, List<IPhotoMetaDataWithImage>> oldValue, Tuplet<String, List<IPhotoMetaDataWithImage>> newValue) {
        List<IPhotoInfo> photoInfos = new ArrayList<>();
        for (IPhotoMetaDataWithImage photoMetaDataWithImage: newValue.getObject2()) {
          PhotoInfo photoInfo = PhotoInfo.fromIPhotoMetaDataWithImage(photoMetaDataWithImage);
          photoInfos.add(photoInfo);
        }
        photoInfoListsMap.put(newValue.getObject1(), photoInfos);
      }


    });
    
    // Handle messages - messages are shown in the statusPanel
    gatherPhotoInfoTask.messageProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        statusPanel.updateText(newValue);
      }

    });
    
    // Handle progress reports - progress is shown in the statusPanel
    gatherPhotoInfoTask.progressProperty().addListener(new ChangeListener<Number>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        statusPanel.updateProgress(newValue.doubleValue());
      }
      
    });

    gatherPhotoInfoThread = new Thread(gatherPhotoInfoTask);
    gatherPhotoInfoThread.setDaemon(true);
    gatherPhotoInfoThread.start();
  }
  
  /**
   * Create the main window of the application.
   * <p>
   * Layout:
   * Top level is VBox:
   *   Top is the menu bar
   *   Below that Tree view of specification to the left - Specification wizards to the right
   *   Below that a VBox with: the title "Photo Show" and the listView with the photos.
   *   Bottom is the StatusPanel.
   *   
   * @param stage the Stage to add the scene to.
   */
  private void createGUI() {
    VBox topLevelVBox = componentFactory.createVBox(12.0, 12.0);
    
    // Menu bar
    topLevelVBox.getChildren().add(createMenuBar());
    
    // Specification as TreeView and Wizards panel
    HBox specificationAndWizardsPanel = componentFactory.createHBox(12.0);
    photoShowSpecificationTreeView = new PhotoShowSpecificationTreeViewCreator()
        .createPhotoShowSpecificationTreeView(customization);
    photoShowSpecificationTreeView.setEObject(photoShowSpecification);
    photoShowSpecificationTreeView.setEditMode(true);
    photoShowSpecificationTreeView.setMinWidth(800);
    photoShowSpecificationTreeView.setStyle("-fx-border-style: solid inside;");
    
    Node wizardsPanel = createWizardsPanel();
    specificationAndWizardsPanel.getChildren().addAll(photoShowSpecificationTreeView, wizardsPanel);
   
    // Photo show
    VBox photoShowPanel = componentFactory.createVBox(10.0);
    photoShowLabel = componentFactory.createStrongLabel(null);
    photoShowPanel.getChildren().add(photoShowLabel);
    createPhotoShowView();
    photoShowPanel.getChildren().add(listView);
        
    // Status panel
    statusPanel = new GatherPhotoInfoStatusPanel(componentFactory);
    
    guiNodesToBeDisabled.add(specificationAndWizardsPanel);
    topLevelVBox.getChildren().addAll(specificationAndWizardsPanel, photoShowPanel, statusPanel);
    
    Scene scene = new Scene(topLevelVBox, 1300, 950);
    setScene(scene);
    show();
  }
  
  /**
   * Create the Photo Show View
   * <p>
   * This is a ListView, with a special Cell Factory.
   * The cell has the following properties:
   * - photos, which are not selected for the show, are shown smaller
   * - double clicking on a photo toggles whether the photo is selected for the show
   * - hovering over the photo enables buttons: Details
   * 
   * Selection mode is MULTIPLE
   */
  private void createPhotoShowView() {
    listView = new ListView<>();
    listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    
    listView.setCellFactory(new Callback<ListView<IPhotoInfo>, ListCell<IPhotoInfo>>() {

      @Override
      public ListCell<IPhotoInfo> call(ListView<IPhotoInfo> listView) {
        
        ListCell<IPhotoInfo> listCell = new ListCell<IPhotoInfo>() {
 
          @Override
          public void updateItem(IPhotoInfo photoInfo, boolean empty) {
            super.updateItem(photoInfo, empty);
            if (empty) {
              setText(null);
              setGraphic(null);
            } else {
              /*
               * The Graphic is a StackPane: the photo, with on top (in case of mouse over) buttons
               */
              StackPane stackPane = new StackPane();

              ImageView imageView = new ImageView();

              // Photos which aren't selected for the show are shown smaller.
              boolean selected = photoInfo.isSelectedForTheShow();
              if (selected) {
                imageView.setFitHeight(150);   // normal height
              } else {
                imageView.setFitHeight(75);    // smaller height
              }
              imageView.setPreserveRatio(true);
              imageView.setImage(photoInfo.getImage());
              if (photoInfo.getRotationAngle() != null) {
                imageView.setRotate(photoInfo.getRotationAngle());
              }
              stackPane.getChildren().add(imageView);
              setGraphic(stackPane);

              // Get the photo file name from the full path name
              File aFile = new File(photoInfo.getFileName());
              setText(aFile.getName());
              
              // react to changes in the 'selected for the show'status
              photoInfo.selectedForTheShowProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                  listView.refresh();
//                  updatePhotoShowLabel();
                }

              });

              // Install 'Double clicking' toggles 'selected'.
              setOnMouseClicked(event -> {
                if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                  photoInfo.toggleSelectedForTheShow();
                }
              });

              // React to mouse entered/exited to show/hide 'Details' button.
              setOnMouseEntered(event -> {
                if (photoInfo.isSelectedForTheShow()  &&  (stackPane.getChildren().size() == 1)) {
                  Button detailsButton = new Button("Details");
                  detailsButton.setOnAction(actionEvent -> {
                    showDetailsPopup(thisStage, photoInfo);
                  });
                  stackPane.getChildren().add(detailsButton);              
                  StackPane.setAlignment(detailsButton, Pos.TOP_RIGHT);
                }
              });

              setOnMouseExited(event -> {
                ObservableList<Node> stackPaneChildren = stackPane.getChildren();
                if (stackPaneChildren.size() > 1) {
                  stackPaneChildren.remove(1);
                }
              });
            }
          }
        };
        return listCell;
      }
    });

    listView.setMinHeight(600.0);
    listView.setMinWidth(500.0);
  }
  
  /**
   * Create the menu bar for this window.
   * @param developmentMode if true, the application is in development mode, in which case extra menu options may be available.
   * 
   * @return the menu bar for this window.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = componentFactory.createMenuBar();
    Menu menu;
    MenuItem menuItem;

    // Bestand menu
    menu = componentFactory.createMenu("File");
    
    menuItem = componentFactory.createMenuItem("New Photo Show Specification");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        handleNewPhotoShowSpecificationRequest();
      }
    });
    menu.getItems().add(menuItem);
    
    menuItem = componentFactory.createMenuItem("Open Photo Show Specification ...");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        handleOpenPhotoShowSpecificationRequest();
      }
    });
    menu.getItems().add(menuItem);
    
    menuItem = componentFactory.createMenuItem("Save Photo Show Specification");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        handleSavePhotoShowSpecificationRequest();
      }
    });
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    menu.getItems().add(menuItem);
    
    menuItem = componentFactory.createMenuItem("Save Photo Show Specification as ...");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        handleSavePhotoShowSpecificationAsRequest();
      }
    });
    menu.getItems().add(menuItem);
            
    menuItem = componentFactory.createMenuItem("Exit");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        Platform.exit();
      }
    });
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);

    return menuBar;
  }

  /**
   * Create the wizards panel, which provides a button to start each wizard step.
   * <p>
   * The following wizard buttons exist:
   * <ul>
   * <li>
   * Wizard Step 0 - not an actual wizard, but a text about preparation of photos in folders
   * </li>
   * <li>
   * Wizard Step 1 - to select the photo folders
   * </li>
   * <li>
   * Wizard Step 2 - to synchronize folder (device) times
   * </li>
   * <li>
   * Wizard Step 3 - Select photos (not) to be shown<br/>
   * </li>
   * </ul>
   * Besides the wizards, this panel also contains the buttons to 'write a playlist' and 'write a show folder'.
   * 
   * @return the wizards panel
   */
  private Node createWizardsPanel() {
    // One wizard per row; first column is the button to start it, seconds column the explaining text.
    GridPane wizardPanel = componentFactory.createGridPane(20.0, 10.0, 10.0);
    wizardPanel.setStyle("-fx-border-style: solid inside;");
    
    // Wizard Step 0 - not an actual wizard, but a text about preparation of photos in folders
    Button button = componentFactory.createButton("Step 0", null);
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        showOrganizePhotosWizard();
      }
    });
    wizardPanel.add(button, 0, 0);
    Text text = componentFactory.createText("Organize your photos in folders");
    wizardPanel.add(text, 1, 0);
    
    // Wizard Step 1 - Select folders
    button = componentFactory.createButton("Step 1", null);
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        showFolderSelectionWizard();
      }
    });
    wizardPanel.add(button, 0, 1);
    text = componentFactory.createText("Select folders");
    wizardPanel.add(text, 1, 1);
    
    // Wizard Step 2 - Synchronize folder (device) times
    button = componentFactory.createButton("Step 2", null);
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        showDeviceFolderTimeSyncWizard();
      }
    });
    wizardPanel.add(button, 0, 2);
    text = componentFactory.createText("Synchronize folder (device) times");
    wizardPanel.add(text, 1, 2);
    
    // Wizard Step 3 - Select photos (not) to be shown
    button = componentFactory.createButton("Step 3", null);
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        showStep3Dialog();
      }
    });
    wizardPanel.add(button, 0, 3);
    text = componentFactory.createText("Select photos (not) to be shown");
    wizardPanel.add(text, 1, 3);
    
    // Action buttons: write playlist,  write show folder, view full screen
    HBox actionButtonsPanel = componentFactory.createHBox(20);
    
    button = componentFactory.createButton("Write playlist", null);   
    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        writePlaylist();
      }
      
    });
    actionButtonsPanel.getChildren().add(button);
    
    button = componentFactory.createButton("Write show folder", null);
    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        writeShowFolder();
      }
      
    });
    actionButtonsPanel.getChildren().add(button);
    
    button = componentFactory.createButton("View selection full screen", null);
    button.setOnAction(actionEvent -> viewSelectionFullScreen());
    actionButtonsPanel.getChildren().add(button);
    wizardPanel.add(actionButtonsPanel, 0, 4, 2, 1);
    
    button = componentFactory.createButton("Play show", null);
    button.setOnAction(actionEvent -> playShow());
    wizardPanel.add(button, 0, 5);
    
    return wizardPanel;
  }
  
  /**
   * Show the OrganizePhotosWizard.
   * <p>
   * The {@link OrganizePhotosWizard} is shown.
   * As this wizard only shows information, this method just waits till the wizard is closed.
   */
  private void showOrganizePhotosWizard() {
    final OrganizePhotosWizard folderSelectionWizard = new OrganizePhotosWizard(customization, this);
    folderSelectionWizard.showAndWait();
  }
  
  /**
   * Show the FolderSelectionWizard and react to the selection made.
   * <p>
   * The {@link FolderSelectionWizard} is shown, with the last known 'selected folder' and 'ignore folders'.<br/>
   * If there's no last known 'selected folder', the value {@link MediaRegistry#photosFolder} is used.<br/>
   * If there's no last known 'ignore folders', the value {@link MediaRegistry#ignoreFolderNames} is used.
   * <p>
   * Upon selection of photo folders:
   * <ul>
   * <li>
   * The chosen top level folder is stored in the {@link #currentlySelectedFolder} (being the 'selected folder').
   * </li>
   * <li>
   * The title of the specification is set to the name of the chosen top level folder.
   * </li>
   * <li>
   * The 'ignore folders' are set to the new value.
   * </li>
   * <li>
   * The photo folders are handled by calling {@link #handleNewPhotoFoldersRequest}.
   * </li>
   * </ul>
   */
  private void showFolderSelectionWizard() {
    String initiallySelectedFolder = currentlySelectedFolder;
    if (initiallySelectedFolder == null) {
      initiallySelectedFolder = MediaRegistry.photosFolder;
    }
    String initialIgnoreFolders = ignoreFolders;
    if (initialIgnoreFolders == null) {
      initialIgnoreFolders = MediaRegistry.ignoreFolderNames;
    }
    
    final FolderSelectionWizard folderSelectionWizard = new FolderSelectionWizard(customization, this, initiallySelectedFolder, initialIgnoreFolders);
    Optional<ButtonType> result = folderSelectionWizard.showAndWait();
    
    if (result.isPresent()  &&  result.get() == ButtonType.OK) {
      currentlySelectedFolder = folderSelectionWizard.getSelectedFolder();
      
      Path mainPhotosFolderPath = Paths.get(currentlySelectedFolder);
      String mainPhotosFolder = mainPhotosFolderPath.getFileName().toString();
      photoShowSpecification.setTitle(mainPhotosFolder);
      
      ignoreFolders = folderSelectionWizard.getIgnoreFolders();
      
      List<Path> photoFolders = folderSelectionWizard.getPhotoFolders();
      handleNewPhotoFoldersRequest(photoFolders);      
    }
  }
  
  /**
   * Show the DeviceFolderTimeSyncWizard and handle the selected correction.
   */
  private void showDeviceFolderTimeSyncWizard() {
    final DeviceFolderTimeSyncWizard deviceFolderTimeSyncWizard = new DeviceFolderTimeSyncWizard(customization, this, photoShowSpecification, photoInfoListsMap);
    Optional<ButtonType> result = deviceFolderTimeSyncWizard.showAndWait();
    
    if (result.isPresent()  &&  result.get() == ButtonType.OK) {
      String timeToBeAdjustedFolderName = deviceFolderTimeSyncWizard.getTimeToBeAdjustedFolderName();
      Duration timeOffset = deviceFolderTimeSyncWizard.getAdjustmentTime();
      
      if (timeToBeAdjustedFolderName != null  &&  timeOffset != null) {
        FolderTimeOffsetSpecification folderTimeOffsetSpecification = PHOTO_SHOW_FACTORY.createFolderTimeOffsetSpecification();
        folderTimeOffsetSpecification.setFolderName(timeToBeAdjustedFolderName);
        folderTimeOffsetSpecification.setTimeOffset(DF.format(timeOffset));
        LOGGER.severe("timeOffset set to: " + folderTimeOffsetSpecification.getTimeOffset());
        photoShowSpecification.getFolderTimeOffsetSpecifications().add(folderTimeOffsetSpecification);
      }
    }
  }
  
  /**
   * Show the Step 3 dialog.
   * <p>
   * The {@link SelectPhotosWizard} is shown.
   * As this wizard only shows information, this method just waits till the wizard is closed.
   */
  private void showStep3Dialog() {
    final SelectPhotosWizard selectPhotosWizard = new SelectPhotosWizard(customization, this);
    selectPhotosWizard.showAndWait();
  }
  
  private void lockGUI() {
    for (Node node: guiNodesToBeDisabled) {
      node.setDisable(true);
    }
  }
  
  private void unlockGUI() {
    for (Node node: guiNodesToBeDisabled) {
      node.setDisable(false);
    }
  }
  
  /**
   * Create a playlist file.
   * <p>
   * Via a FileChooser the user is requested to select a file, to which the playlist will be written.<br/>
   * If the {@link #currentlySelectedFolder} is set, this will be set as the initial folder for the FileChooser.<br/>
   * The actual playlist file is created by calling {@link #writePlaylist(File)}.
   */
  private void writePlaylist() {
    // Let user select file name
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save playlist");
    
    if (currentlySelectedFolder != null) {
      File initialPlaylistFolder = new File(currentlySelectedFolder);
      fileChooser.setInitialDirectory(initialPlaylistFolder);
    }
    File playlistFile = fileChooser.showSaveDialog(this);
    LOGGER.severe("Playlist file: " + (playlistFile != null ? playlistFile.getAbsolutePath() : "(null)"));
    
    // Write the list
    writePlaylist(playlistFile);
  }
  
  /**
   * Write a playlist to the specified file.
   * <p>
   * A playlist is a file with one filename per line. In this case the filenames are the names of the photos in
   * the {@link #photoShowList}.
   * 
   * @param playlistFile the file in which the playlist is to be created.
   */
  private void writePlaylist(File playlistFile) {
    FileOutputStream fileOutputStream;
    try {
      fileOutputStream = new FileOutputStream(playlistFile);
      BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

      for (IPhotoInfo photoInfo: photoShowList) {
        if (photoInfo.isSelectedForTheShow()) {
          bufferedWriter.write(photoInfo.getFileName());
          bufferedWriter.newLine();
        }
      }

      bufferedWriter.close();    
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Create a photo show folder
   * <p>
   * Via a DirectoryChooser the user is requested to select a folder, to which the show photos will be written.<br/>
   * If the {@link #currentlySelectedFolder} is set, this will be set as the initial folder for the DirectoryChooser.<br/>
   * The actual show is created by calling {@link #writeShowFolder(File)}.
   * 
   */
  private void writeShowFolder() {
    // Let user select folder name
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Create show folder");
    
    if (currentlySelectedFolder != null) {
      File initialPlaylistFolder = new File(currentlySelectedFolder);
      directoryChooser.setInitialDirectory(initialPlaylistFolder);
    }
    File showFolder = directoryChooser.showDialog(this);
    
    // Create the show
    writeShowFolder(showFolder);
  }
  
  /**
   * Create a photo show in a specified folder.
   * <p>
   * All the photos of the {@link #photoShowList} are copied to the specified folder.<br/>
   * In order to have the photos shown in the right order, a prefix is prepended to the filenames.
   * The prefix has the format 'imgxxxx', where xxxx is a number starting at 0, and it's incremented for each photo.
   * 
   * @param showFolder to folder to which the photos will be copied.
   */
  private void writeShowFolder(File showFolder) {    
    OrderedNameGenerator orderedNameGenerator = new OrderedNameGenerator();
    
    for (IPhotoInfo photoInfo: photoShowList) {
      if (photoInfo.isSelectedForTheShow()) {
        Path source = Paths.get(photoInfo.getFileName());      

        switch (showFileType) {
        case COPY:
          createCopyOfPhoto(source, showFolder, orderedNameGenerator);
          break;
          
        case SHORTCUT:
          createShortcutToPhoto(source, showFolder, orderedNameGenerator);
          break;
          
        case SYMBOLIC_LINK:
          createSymbolicLinkForPhoto(source, showFolder, orderedNameGenerator);
          break;
        }
        
      }
    }
  }
  
  private void createCopyOfPhoto(Path photoPath, File showFolder, OrderedNameGenerator orderedNameGenerator) {
    String showFileName = orderedNameGenerator.generateName() + "_" + photoPath.getFileName().toString();
    Path shortcutFile = Paths.get(showFolder.getAbsolutePath(), showFileName);
    try {
      Files.copy(photoPath, shortcutFile);
    } catch (IOException e) {
      String errorMessage;
      if (e instanceof FileAlreadyExistsException) {
        errorMessage = "File " + e.getMessage() + " already exists. File not copied.";
      } else {
        errorMessage = e.getMessage();
      }
      componentFactory.createErrorDialog("File copying failed", errorMessage).showAndWait();
    }
  }

  private void createShortcutToPhoto(Path photoPath, File showFolder, OrderedNameGenerator orderedNameGenerator) {
    String photoFilenameWithoutExtension = FileUtils.getFileNameWithoutExtension(photoPath.getFileName().toString());
    String showFileName = orderedNameGenerator.generateName() + "_" + photoFilenameWithoutExtension + ".lnk";
    Path target = Paths.get(showFolder.getAbsolutePath(), showFileName);
    try {
      ShellLink.createLink(photoPath.toAbsolutePath().toString(), target.toAbsolutePath().toString());
    } catch (IOException e) {
      String errorMessage;
      if (e instanceof FileAlreadyExistsException) {
        errorMessage = "File " + e.getMessage() + " already exists. File not copied.";
      } else {
        errorMessage = e.getMessage();
      }
      componentFactory.createErrorDialog("File copying failed", errorMessage).showAndWait();
    }
  }

  private void createSymbolicLinkForPhoto(Path photoPath, File showFolder, OrderedNameGenerator orderedNameGenerator) {
    String showFileName = orderedNameGenerator.generateName() + "_" + photoPath.getFileName().toString();
    Path target = Paths.get(showFolder.getAbsolutePath(), showFileName);
    try {
      Files.createSymbolicLink(target, photoPath);
    } catch (IOException e) {
      String errorMessage;
      if (e instanceof FileAlreadyExistsException) {
        errorMessage = "File " + e.getMessage() + " already exists. File not copied.";
      } else {
        errorMessage = e.getMessage();
      }
      componentFactory.createErrorDialog("File copying failed", errorMessage).showAndWait();
    }
  }

    
  /**
   * Handle a Close Window event.
   * <p>
   * If there are unsaved changes in the Photo Show Specification, a popup is shown to ask the user to continue or cancel.
   *  
   * @param event a window event.
   */
  private void closeWindowEvent(WindowEvent event) {
    if(emfResource.isDirty()) {
      Alert alert = componentFactory.createOkCancelConfirmationDialog("Quit application?", "The current Photo Show Specification hasn't been saved.", "Close without saving?");
      alert.getButtonTypes().remove(ButtonType.OK);
//      alert.getButtonTypes().add(ButtonType.CANCEL);
      alert.getButtonTypes().add(ButtonType.YES);
      Optional<ButtonType> res = alert.showAndWait();

      if(res.isPresent()  &&  res.get().equals(ButtonType.CANCEL)) {
        event.consume();
      }
    }
  }

  
  /**
   * Start with a completely new specification (so information is cleared).
   * <p>
   * If there are unsaved changes in the Photo Show Specification, a popup is shown to ask the user to continue or cancel.
   */
  private void handleNewPhotoShowSpecificationRequest() {
    if(emfResource.isDirty()) {
      Alert alert = componentFactory.createOkCancelConfirmationDialog("New Photo Show Specification?", "The current Photo Show Specification hasn't been saved.", "Continue without saving?");
      alert.getButtonTypes().remove(ButtonType.OK);
      alert.getButtonTypes().add(ButtonType.CANCEL);
      alert.getButtonTypes().add(ButtonType.YES);
      Optional<ButtonType> res = alert.showAndWait();

      if(!res.isPresent()  ||  res.get().equals(ButtonType.CANCEL)) {
        return;
      }
    }
    
    photoShowSpecification = emfResource.newEObject();
    
    handleNewPhotoShowSpecification();
  }
  
  /**
   * Open an existing Photo Show Specification file.
   * <p>
   * If there are unsaved changes in the Photo Show Specification, a pop-up is shown to ask the user to continue or cancel.<br/>
   * If a file is opened, this will be the 'current file'.
   * 
   */
  private void handleOpenPhotoShowSpecificationRequest() {
    // If there are unsaved changes, only continue after user confirmation.
    if(emfResource.isDirty()) {
      Alert alert = componentFactory.createOkCancelConfirmationDialog("Open Photo Show Specification?", "The current Photo Show Specification hasn't been saved.", "Continue without saving?");
      alert.getButtonTypes().remove(ButtonType.OK);
//      alert.getButtonTypes().add(ButtonType.CANCEL);
      alert.getButtonTypes().add(ButtonType.YES);      
      
      Optional<ButtonType> usersChoice = alert.showAndWait();

      if(!usersChoice.isPresent()  ||  usersChoice.get().equals(ButtonType.CANCEL)) {
        return;
      }
    }
    
    FileChooser fileChooser = componentFactory.createFileChooser("Open Photo Show Specification");
    if (MediaRegistry.photosFolder != null) {
      File photosFolder = new File(MediaRegistry.photosFolder);
      fileChooser.setInitialDirectory(photosFolder);
    }
    File photoShowSpecificationFile = fileChooser.showOpenDialog(this);
    
    if (photoShowSpecificationFile == null) {
      return;
    }
    
    LOGGER.info("Opening: " + photoShowSpecificationFile.getAbsolutePath());
    
    try {
      photoShowSpecification = emfResource.load(photoShowSpecificationFile.getAbsolutePath());
      handleNewPhotoShowSpecification();
      
      /*
       * Handle the photoFolders by calling handleNewPhotoFolderInSpecification().
       * When the photoShowList is filled, the FolderTimeOffsetSpecifications are automatically taken into account.
       * When a folder is read, normally all photos are set to 'selected for the show'. However now the photos which are
       * in photosToShow are the selection.
       * 
       * - block GUI
       * - set openSpecificationNumberOfFoldersToHandle to the number of folders to handle
       * - add photosToShow to openSpecificationPhotosToShow
       * - in handlePhotoFolderAddedToPhotoInfoListsMap, check whether openSpecificationPhotosToShow is not null. If so use this list.
       *   In this case also openSpecificationNumberOfFoldersToHandle is decremented. If it is 0, openSpecificationPhotosToShow is set to null
       *   and the GUI is enabled.
       */
      lockGUI();
      
      openSpecificationPhotosToShow = new ArrayList<>(photoShowSpecification.getPhotosToShow());
      
      openSpecificationNumberOfFoldersToHandle =  photoShowSpecification.getPhotoFolders().size();
      
      for (String photoFolderName: photoShowSpecification.getPhotoFolders()) {        
        handleNewPhotoFolderInSpecification(photoFolderName);
      }
      
      componentFactory.createInformationDialog(null,
          "Photoshow specification " + photoShowSpecificationFile.getAbsolutePath() + " is being opened.",
          "Note that photos, which were added to a folder after the specification was created, " +
          "are added as 'not selected for the show'." + NEWLINE +
          "Any photos that were selected for the show and now don't exist anymore will be reported after all photo information" +
          "has been read from the file system.").showAndWait();
      
    } catch (IOException e) {
      e.printStackTrace();
    } catch (WrappedException wrappedException) {
      componentFactory.createExceptionDialog("An exception occurred while reading the file: '" + photoShowSpecificationFile.getAbsolutePath() + "'.", wrappedException).show();
//      new JavaFxExceptionAlert(customization, null, "THE HEADER", wrappedException).showAndWait();
    }
    
  }
  
  /**
   * Save the current photo show specification.
   * <p>
   * The specification is saved to the 'current file', if it is set.
   * Otherwise the specification is saved by calling {@link #savePhotoShowSpecificationAs}.
   */
  private void handleSavePhotoShowSpecificationRequest() {
    if (!emfResource.getFileName().isEmpty()) {
      try {
        emfResource.save();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      handleSavePhotoShowSpecificationAsRequest();
    }
  }
  
  /**
   * Save the current photo show specification, where the user first has to specify a file name via a FileChooser.
   * <p>
   * If the user cancels the FileChooser, no further action takes place (the specification isn't saved).
   */
  private void handleSavePhotoShowSpecificationAsRequest() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Photo Show Specification");
    fileChooser.setInitialDirectory(new File(currentlySelectedFolder));
    if (photoShowSpecification.getTitle() != null) {
      fileChooser.setInitialFileName(photoShowSpecification.getTitle());
    }
    ExtensionFilter extensionFilter = new ExtensionFilter("Photoshow file", PhotoshowCommons.DEFAULT_PHOTOSHOW_SPECIFICATION_FILE_EXTENSION);
    fileChooser.getExtensionFilters().add(extensionFilter);
    fileChooser.setSelectedExtensionFilter(extensionFilter);
    
    File file = fileChooser.showSaveDialog(this);
    if (file != null) {
      LOGGER.severe("Saving: " + file.getAbsolutePath());
      try {
        emfResource.save(file.getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * Handle the fact that a new set of Photo Folders is selected by the user.
   * <p>
   * Folders in the current set of folders in the specification that aren't in the new set are removed from the specification.<br/>
   * Folders in the new set, that aren't in the set of current folders, are added to the specification.
   * 
   * @param photoFolders the new set of Photo Folders.
   */
  private void handleNewPhotoFoldersRequest(List<Path> photoFolders) {
    // Remove current folders that aren't in the new photoFolders.
    for (String currentPhotoFolder: photoShowSpecification.getPhotoFolders()) {
      Path currentPhotoPath = Paths.get(currentPhotoFolder);
      if (!pathListContainsPath(photoFolders, currentPhotoPath)) {
        LOGGER.severe("Removing photo folder: " + currentPhotoFolder.toString());
        photoShowSpecification.getPhotoFolders().remove(currentPhotoFolder);
      }
    }
    
    // Add new photoFolders that aren't in the current folders.
    for (Path newPhotoFolderPath: photoFolders) {
      String newPhoteFolder = newPhotoFolderPath.toString();
      if (!photoShowSpecification.getPhotoFolders().contains(newPhoteFolder)) {
        LOGGER.fine("Adding photo folder: " + newPhoteFolder.toString());
        photoShowSpecification.getPhotoFolders().add(newPhoteFolder);
      }
    }
  }
  
  /**
   * Handle a new Photo Show Specification.
   * <p>
   * Clear relevant information.
   * An EContentAdapter is added to the new specification to react to changes.
   */
  private void handleNewPhotoShowSpecification() {
    photoFoldersToHandle.clear();
    photoInfoListsMap.clear();
    photoShowList.clear();
    
    EContentAdapter eContentAdapter = new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(Notification notification) {
        super.notifyChanged(notification);
        
        if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
          // no action
          return;
        }
        
        Object feature = notification.getFeature();
        LOGGER.info("Feature: " + feature);
        LOGGER.info("Position: " + notification.getPosition());
        int eventType = notification.getEventType();
        if (feature instanceof EStructuralFeature) {
          EStructuralFeature eAttribute = (EStructuralFeature) feature;
          switch (eAttribute.getFeatureID()) {
          case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__TITLE:
            // no action for now. Where is the title in the GUI?
            break;

          case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__PHOTO_FOLDERS:
            LOGGER.info("Change in the list of Photo Folders");
            LOGGER.info("EventType: " + eventType);
            switch (eventType) {
            case Notification.ADD:
              handleNewPhotoFolderInSpecification(photoShowSpecification.getPhotoFolders().get(notification.getPosition()));
              break;
              
            case Notification.REMOVE_MANY:
              throw new RuntimeException("REMOVE_MANY is not supported.");
              //              break;

            default:
              throw new RuntimeException("EventType not supported: " + eventType);
            }
            break;
            
          case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__PHOTOS_TO_SHOW:
            // photosToShow is updated when the photoShowList (or the related PhotoInfo) changes, and not the other way around.
            // photosToShow will not be editable in the TreeView.
            // So, no action here.
            break;
            
          case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION__FOLDER_TIME_OFFSET_SPECIFICATIONS:
            // with any change, the sorting times have to be updated and re-sorting and re-diplaying is needed.
            handleChangeInFolderTimeOffsetSpecifications();
            break;

          default:
            LOGGER.severe("ERROR Unknown attribute: name=" + eAttribute.getName() + ", featureId=" + eAttribute.getFeatureID());
//            throw new RuntimeException("Unknown attribute: name=" + eAttribute.getName() + ", featureId=" + eAttribute.getFeatureID());
          }
        } else {
          if (feature != null) {
            throw new RuntimeException("Unknown feature: " + feature.toString());
          } else {
            LOGGER.severe("Notification: " + EmfUtil.printNotification(notification, true));
            throw new RuntimeException("Feature is null ");
          }
        }
        notification.getFeature();
      }

    };

    photoShowSpecification.eAdapters().add(eContentAdapter);
    //    System.out.println("Adapter installed");

    photoShowSpecificationTreeView.setEObject(photoShowSpecification);
  }
  
  /**
   * Handle the fact that a new photo folder is added to the specification.
   * <p>
   * The folder name is added to the request queue of the GatherPhotoInfoTask.
   * 
   * @param newPhotoFolderName
   */
  private void handleNewPhotoFolderInSpecification(final String newPhotoFolderName) {
    LOGGER.info("=> photoFolderName=" + newPhotoFolderName);
    
    try {
      photoFoldersToHandle.put(newPhotoFolderName);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }    
    
    LOGGER.info("<=");
  }
  
  /**
   * Handle a change in the folderTimeOffsetSpecifications.
   * <p>
   * The 'sorting times' of all photos have to be updated.
   * And as the 'sorting times' are changed, to photos also have to be sorted again, and displayed again.
   */
  private void handleChangeInFolderTimeOffsetSpecifications() {
    updatePhotoInfoSortingTimes();
    
    handleChangedPhotoShowList();
  }
  
  /**
   * Handle the fact that a photo is added to photosToShow in the specification.
   * 
   * @param photoFileName the filename of the new photo
   */
  private void handleChangedPhotoShowList() {
    // sort the list
    Collections.sort(photoShowList, new Comparator<IPhotoInfo>() {

      @Override
      public int compare(IPhotoInfo photoInfo1, IPhotoInfo photoInfo2) {
        LocalDateTime photoDateTime1 = photoInfo1.getSortingDateTime();
        LocalDateTime photoDateTime2 = photoInfo2.getSortingDateTime();
        if (photoDateTime1 == null) {
          LOGGER.severe("No sorting time for photoInfo1: " + photoInfo1);
          return 0;  // FIXME
        }
        if (photoDateTime2 == null) {
          LOGGER.severe("No sorting time for photoInfo2: " + photoInfo2);
          return 0;   // FIXME
        }
        return photoInfo1.getSortingDateTime().compareTo(photoInfo2.getSortingDateTime());
      }
      
    });
    
    // show the list
    displayShowList();
    
    // update photosToShow in the specification
    syncPhotosToShowInSpecificationToPhotoShowList();
    
    updatePhotoShowLabel();
  }
    
  /**
   * Check whether a {@code Path} exists in a list of {@code Path}s.
   * 
   * @param pathList the list of {@code Path}s to check against.
   * @param path the {@code Path} that should be in {@code pathList}.
   * @return true if {@code pathList} contains {@code path}.
   */
  private boolean pathListContainsPath(List<Path> pathList, Path path) {
    // contains() uses equals, which is implemented for a WindowsPath. So we can simply call contains().
    return pathList.contains(path);
  }
    
  /**
   * Handle the fact that information of a photo folder is added to the photoInfoListsMap.
   * <p>
   * All the photos are added to the photoShowList.
   * All the photos are set to be part of the show.
   * 
   * @param photoFolderName name of the added photo folder
   * @param photoInfoList information on all the photos in this folder
   */
  private void handlePhotoFolderAddedToPhotoInfoListsMap(String photoFolderName, List<IPhotoInfo> photoInfoList) {
    LOGGER.info("=>");
    
    updatePhotoInfoSortingTimesForOneFolder(photoFolderName);
    
    List<PhotoInfo> photosToAddToPhotoShowList = new ArrayList<>();
    
    for (IPhotoMetaDataWithImage photoInfo: photoInfoList) {
      if (openSpecificationNumberOfFoldersToHandle != 0) {
        boolean openSpecificationPhotosToShowContainsPhoto = openSpecificationPhotosToShow.contains(photoInfo.getFileName());
        ((PhotoInfo) photoInfo).setSelectedForTheShow(openSpecificationPhotosToShowContainsPhoto);
        if (openSpecificationPhotosToShowContainsPhoto) {
          openSpecificationPhotosToShow.remove(photoInfo.getFileName());
        }
      } else {
        ((PhotoInfo) photoInfo).setSelectedForTheShow(true);
      }
      photosToAddToPhotoShowList.add((PhotoInfo) photoInfo);
      ((PhotoInfo) photoInfo).selectedForTheShowProperty().addListener(new ChangeListener<Boolean>() {

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
          syncPhotosToShowInSpecificationToPhotoShowList();
        }
        
      });
    }
    
    photoShowList.addAll(photosToAddToPhotoShowList);
    
    // Report any photos not existing anymore, and remove them from the specification.
    if (openSpecificationNumberOfFoldersToHandle != 0) {  // TODO !=
      openSpecificationNumberOfFoldersToHandle--;
      if (openSpecificationNumberOfFoldersToHandle == 0) {
        if (!openSpecificationPhotosToShow.isEmpty()) {
          StringBuilder buf = new StringBuilder();
          buf.append("The following photos don't exist anymore:").append(NEWLINE);
          for (String photoFileName: openSpecificationPhotosToShow) {
            photoShowSpecification.getPhotosToShow().remove(photoFileName);
            buf.append("    ").append(photoFileName).append(NEWLINE);
          }
          buf.append("These photos are removed from the specification.").append(NEWLINE);
          componentFactory.createWarningDialog("One or more photos which were selected for the show don't exist anymore.", buf.toString()).showAndWait();
        }
        openSpecificationPhotosToShow = null;
        unlockGUI();
      }
    }
    
    LOGGER.info("<=");
  }
  
  /**
   * Let photosToShow in the specification mirror the list of 'Selected for the show' items in the photoShowList.
   */
  private void syncPhotosToShowInSpecificationToPhotoShowList() {
    if (openSpecificationNumberOfFoldersToHandle != 0) {
      return;
    }
    
    List<String> newPhotosToShow = new ArrayList<>();
    for (IPhotoInfo photoInfo: photoShowList) {
      if (photoInfo.isSelectedForTheShow()) {
        newPhotosToShow.add(photoInfo.getFileName());
      }
    }
    
    List<String> photosToShow = photoShowSpecification.getPhotosToShow();
    if (!photosToShow.equals(newPhotosToShow)) {
      photosToShow.clear();
      photosToShow.addAll(newPhotosToShow);
    }
  }

  private void handlePhotoFolderRemovedFromPhotoInfoListsMap() {
    LOGGER.severe("=>");
    
    LOGGER.severe("<=");
  }
   
    
  /**
   * Update the Sorting Times in all PhotoInfo.
   */
  private void updatePhotoInfoSortingTimes() {
    
    // Iterate over all photoInfoLists in the photoInfoListsMap
    for (String folderName: photoInfoListsMap.keySet()) {
      updatePhotoInfoSortingTimesForOneFolder(folderName);
    }
  }
  
  /**
   * Updata the Sorting Times in all PhotoInfo for one folder.
   * 
   * @param folderName The name of the folder for which the Sorting Times are to be updated.
   */
  private void updatePhotoInfoSortingTimesForOneFolder(String folderName) {
    List<IPhotoInfo> photoInfoList = photoInfoListsMap.get(folderName);

    // clear the sorting time for all photos in this folder
    for (IPhotoInfo photoMetaDataWithImage: photoInfoList) {
      photoMetaDataWithImage.setSortingDateTime(null);
    }
    
    // Check all 'folder time offset specifications'. If it is for this folder, apply the offset to all photos of this folder.
    for (FolderTimeOffsetSpecification folderTimeOffsetSpecification: photoShowSpecification.getFolderTimeOffsetSpecifications()) {
      String timeOffsetFolderName = folderTimeOffsetSpecification.getFolderName();
      if (timeOffsetFolderName.equals(folderName)) {
        Duration timeOffset = DF.parse(folderTimeOffsetSpecification.getTimeOffset());
        LOGGER.fine("time offset text: " + folderTimeOffsetSpecification.getTimeOffset());
        LOGGER.fine("timeOffset: " + timeOffset.toString());
        
        for (IPhotoMetaDataWithImage photoInfo: photoInfoList) {
          ((PhotoInfo) photoInfo).setSortingDateTime(photoInfo.getDeviceSpecificPhotoTakenTime().plus(timeOffset));
          LOGGER.fine("file: " + photoInfo.getFileName() + 
              ", taken at: " + photoInfo.getDeviceSpecificPhotoTakenTime() +
              ", sorting time: " + ((PhotoInfo) photoInfo).getSortingDateTime());
        }
      }
    }
    
  }
  
  private void displayShowList() {
    listView.setItems(photoShowList);
  }
    
  
  private void showDetailsPopup(Window owner, IPhotoInfo photoInfo) {
    LocalDateTime creationDate =  photoInfo.getDeviceSpecificPhotoTakenTime();
    LocalDateTime modificationDate = photoInfo.getModificationDateTime();
    LocalDateTime sortingDate = photoInfo.getSortingDateTime();

    final Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(owner);
    VBox dialogVbox = new VBox(20);
    StringBuilder buf = new StringBuilder();
    buf.append("Creation date: ").append(creationDate != null ? creationDate.toString() : "");
    if (modificationDate != null) {
      buf.append(NEWLINE).append("Modification date: ").append(modificationDate != null ? modificationDate.toString() : "");
    }
    buf.append(NEWLINE).append("Sorting date: ").append(sortingDate != null ? sortingDate.toString() : "");
    
    WGS84Coordinates coordinates = photoInfo.getCoordinates();
    buf.append(NEWLINE).append("Latitude: ");
    if (coordinates != null) {
      buf.append(coordinates.getLatitude());
    } else {
      buf.append("-");
    }
    buf.append(NEWLINE).append("Longitude: ");
    if (coordinates != null) {
      buf.append(coordinates.getLongitude());
    } else {
      buf.append("-");
    }
    
    dialogVbox.getChildren().add(new Text(buf.toString()));
    Scene dialogScene = new Scene(dialogVbox, 300, 200);
    dialog.setScene(dialogScene);
    dialog.show();
  }
  
  /**
   * Show the photos, which are currently selected in the listView, in the Full Screen Viewer.
   */
  private void viewSelectionFullScreen() {
    new FullScreenViewer(listView.getSelectionModel().getSelectedItems());
  }
  
  private void playShow() {
    List<String> showList = new ArrayList<>();
    
    for (IPhotoInfo photoInfo: photoShowList) {
      if (photoInfo.isSelectedForTheShow()) {
        showList.add(photoInfo.getFileName());
      }
    }
    
    new PhotoWindow(customization, showList, photoShowSpecification.getTitle());
  }
  
  /*
   * Methods which could be moved to EObjectBasedStage
   * abstract class EObjectBasedStage<E extends EObject> extends JavaFxStage
   * abstract E createEMFResource
   */
 EMFResource<PhotoShowSpecification> createEMFResource() {
   return new EMFResource<PhotoShowSpecification>(PhotoShowPackage.eINSTANCE, () -> PhotoShowFactory.eINSTANCE.createPhotoShowSpecification(), ".xmi");   
 }
 
 /**
  * Update the 'Photo Show' label.
  * <p>
  */
 private void updatePhotoShowLabel() {
   StringBuilder buf = new StringBuilder();
   
   buf.append("Photo Show");
   
   if (photoShowList != null) {
     buf.append(" - total number of photos: ");
     buf.append(photoShowList.size());
     buf.append(", number of photos selected for the show: ");
     int numberOfPhotosSelectedForTheShow = 0;
     for (IPhotoInfo photoInfo: photoShowList) {
       if (photoInfo.isSelectedForTheShow()) {
         numberOfPhotosSelectedForTheShow++;
       }
     }
     buf.append(numberOfPhotosSelectedForTheShow);
   }
   
   photoShowLabel.setText(buf.toString());
 }
 
 /**
  * Update the window title.
  * <p>
  * The title has the following format: "<application-name> - [*]<file-name>"
  */
 private void updateWindowTitle() {
   StringBuilder buf = new StringBuilder();
   
   buf.append(WINDOW_TITLE);
   buf.append(" - ");
   if (emfResource.isDirty()) {
     buf.append("*");
   }
   String fileName = emfResource.getFileName();
   if (fileName == null) {
     fileName = "<NoName>";
   }
   buf.append(fileName);
   
   setTitle(buf.toString());
 }
    
}

enum ShowFileType {
  COPY,          // Create copies
  SHORTCUT,      // Create Windows shortcuts
  SYMBOLIC_LINK  // Create Symbolic Links
}
