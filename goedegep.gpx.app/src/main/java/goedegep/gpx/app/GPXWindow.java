package goedegep.gpx.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.gpx.GpxUtil;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GPXFactory;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemContent;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.util.douglaspeuckerreducer.DouglasPeuckerReducer;
import goedegep.util.emf.EMFResource;
import goedegep.util.emf.EMFResourceSet;
import goedegep.util.objectselector.ObjectSelectionListener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class GPXWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(GPXWindow.class.getName());
  private static final String WINDOW_TITLE = "GPX Editor";

  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private EMFResource<DocumentRoot> gpxResource = null;
  private EObjectTreeView gpxTreeView;
  private MapView mapView;
  private GPXLayer gpxLayer;
  private DocumentRoot documentRoot = null;
  // This menu item defines the 'edit status'.
  private CheckMenuItem gpxTreeEditableMenuItem;
  
  private ObjectSelectionListener<TreeItem<EObjectTreeItemContent>> gpxTreeViewSelectionListener;
  private ReduceTrackPointsWindow reduceTrackPointsWindow;

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  public GPXWindow(CustomizationFx customization, String fileToOpen) {
    super(null, customization);

    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();
    
//    EPackage.Registry.INSTANCE.put(GPXPackage.eNS_URI, GPXPackage.eINSTANCE);
    gpxResource = GpxUtil.createEMFResource();
    gpxResource.dirtyProperty().addListener((observable, oldValue, newValue) -> updateTitle());
    
    updateTitle();
    
    show();
    
    if (fileToOpen != null) {
      openGpxFile(new File(fileToOpen));
    }
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    VBox vBox = new VBox();
    
    // Menu bar
    vBox.getChildren().add(createMenuBar());
    
    // Splitpane: left is treeView, right is map
    SplitPane centerPane = new SplitPane();
    centerPane.setOrientation(Orientation.HORIZONTAL);
    centerPane.setDividerPositions(0.3);
    
    // TreeView
    gpxTreeView = new GPXTreeView(customization, null);
    centerPane.getItems().add(gpxTreeView);
    
    // MapView
    mapView = new MapView();
    gpxLayer = new GPXLayer();
    mapView.addLayer(gpxLayer);
    centerPane.getItems().add(mapView);
    
    vBox.getChildren().add(centerPane);
    

    setScene(new Scene(vBox, 1700, 900));
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

    // File menu
    menu = componentFactory.createMenu("File");
    
    menuItem = componentFactory.createMenuItem("New GPX file");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        handleNewGpxFileRequest();
      }
    });
    menu.getItems().add(menuItem);
    
    menuItem = componentFactory.createMenuItem("Open GPX file ...");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        handleOpenGpxFileRequest();
      }
    });
    menu.getItems().add(menuItem);
    
    menuItem = componentFactory.createMenuItem("Save GPX file");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        handleSaveGpxFileRequest();
      }
    });
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    menu.getItems().add(menuItem);
    
    menuItem = componentFactory.createMenuItem("Save GPX file as ...");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        handleSaveGpxFileAsRequest();
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
    
    // Tools menu
    menu = componentFactory.createMenu("Tools");
    
    menuItem = componentFactory.createMenuItem("Import tracks segments");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        importTrackSegments();
      }
    });
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);
    
    
    // Edit menu
    menu = new Menu("Edit");

    // Edit: Edit GPX data
    gpxTreeEditableMenuItem = new CheckMenuItem("Edit GPX data");
    gpxTreeEditableMenuItem.setSelected(false);
    
    gpxTreeEditableMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        gpxTreeView.setEditMode(gpxTreeEditableMenuItem.isSelected());
      }
    });
    menu.getItems().add(gpxTreeEditableMenuItem);

    // Edit: Trim end of track
    menuItem = componentFactory.createMenuItem("Trim end of track");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        trimEndOfTrack();
      }
    });
    menu.getItems().add(menuItem);
    
    menuItem = componentFactory.createMenuItem("Reduce track points");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        reduceTrackPoints();
      }
    });
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);
    
    // View menu
    menu = new Menu("View");

    // View: Details
    menuItem = componentFactory.createMenuItem("Details");
    menuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        viewDetails();
      }
    });
    menu.getItems().add(menuItem);
    
    menuBar.getMenus().add(menu);
        

    return menuBar;
  }

  /**
   * Start with a completely new GPX file (so information is cleared).
   * <p>
   * If there are unsaved changes in the current GPX file, a popup is shown to ask the user to continue or cancel.
   */
  private void handleNewGpxFileRequest() {
    if(gpxResource.isDirty()) {
      Alert alert = componentFactory.createOkCancelConfirmationDialog("New GPX file?", "The current GPX file hasn't been saved.", "Continue without saving?");
      alert.getButtonTypes().remove(ButtonType.OK);
      alert.getButtonTypes().add(ButtonType.CANCEL);
      alert.getButtonTypes().add(ButtonType.YES);
      Optional<ButtonType> res = alert.showAndWait();

      if(!res.isPresent()  ||  res.get().equals(ButtonType.CANCEL)) {
        return;
      }
    }
    
    documentRoot = gpxResource.newEObject();
    
    handleNewGpxFile();
  }
  
  /**
   * Open an existing GPX file.
   * <p>
   * If there are unsaved changes in the current GPX file, a pop-up is shown to ask the user to continue or cancel.<br/>
   * If a file is opened, this will be the 'current file'.
   * 
   */
  private void handleOpenGpxFileRequest() {
    // If there are unsaved changes, only continue after user confirmation.
    if(gpxResource.isDirty()) {
      Alert alert = componentFactory.createOkCancelConfirmationDialog("Open GPX file?", "The current GPX file hasn't been saved.", "Continue without saving?");
      alert.getButtonTypes().remove(ButtonType.OK);
      alert.getButtonTypes().add(ButtonType.YES);
      
      Optional<ButtonType> usersChoice = alert.showAndWait();

      if(!usersChoice.isPresent()  ||  usersChoice.get().equals(ButtonType.CANCEL)) {
        return;
      }
    }
    
    FileChooser fileChooser = componentFactory.createFileChooser("Open GPX file");    
    ExtensionFilter extensionFilter = new ExtensionFilter("GPX file", "*.gpx");
    fileChooser.getExtensionFilters().add(extensionFilter);
    File gpxFile = fileChooser.showOpenDialog(this);
    if (gpxFile != null) {
      openGpxFile(gpxFile);
    }
  }
  
  private void openGpxFile(File gpxFile) {
    LOGGER.info("Opening: " + gpxFile.getAbsolutePath());

    try {
        documentRoot = gpxResource.load(gpxFile.getAbsolutePath());
        handleNewGpxFile();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (WrappedException wrappedException) {
        componentFactory.createExceptionDialog("An exception occurred while reading the file: '" + gpxFile.getAbsolutePath() + "'.", wrappedException).show();
    }
  }
  
  /**
   * Save the current GPX file.
   * <p>
   * The GPX data is saved to the 'current file', if it is set.
   * Otherwise the specification is saved by calling {@link #handleSaveGpxFileAsRequest}.
   */
  private void handleSaveGpxFileRequest() {
    if (!gpxResource.getFileName().isEmpty()) {
      try {
        gpxResource.save();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      handleSaveGpxFileAsRequest();
    }
  }
  
  /**
   * Save the current GPX data, where the user first has to specify a file name via a FileChooser.
   * <p>
   * If the user cancels the FileChooser, no further action takes place (the specification isn't saved).
   */
  private void handleSaveGpxFileAsRequest() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save GPX file");
    File file = fileChooser.showSaveDialog(this);
    if (file != null) {
      LOGGER.severe("Saving: " + file.getAbsolutePath());
      try {
        gpxResource.save(file.getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * Handle the fact that there is a new GPX file (so a new documentRoot).
   */
  private void handleNewGpxFile() {
    gpxTreeView.setEObject(documentRoot);
    GpxType gpx = documentRoot.getGpx();
    List<TrkType> tracks = gpx.getTrk();
    for (TrkType track: tracks) {
    	LOGGER.info("Track length: " + track.getLength());
    }
    
    WGS84BoundingBox gpxBoundingBox = gpxLayer.addGpx(null, gpxResource.getFileName(), gpx);
    Double zoomLevel = MapView.getZoomLevel(gpxBoundingBox);
    if (zoomLevel != null) {
      mapView.setZoom(zoomLevel);
    }
    
    WGS84Coordinates center = gpxBoundingBox.getCenter();
    LOGGER.info("center: " + center.toString());
    MapPoint mapCenter = new MapPoint(center.getLatitude(), center.getLongitude());
    
    if (mapCenter != null) {
      mapView.flyTo(0.0, mapCenter, 2);
    }
  }

  private void importTrackSegments() {
    if (documentRoot == null) {
      LOGGER.severe("documentRoot is null");
      return;
    }
    
    GpxType gpxType = documentRoot.getGpx();
    if (gpxType == null) {
      LOGGER.severe("documentRoot has no GpxType");
      return;
    }
    
    List<TrkType> tracks = gpxType.getTrk();
    TrkType track;
    if (tracks == null  ||  tracks.isEmpty()) {
      LOGGER.severe("GpxType has no tracks");
      track = GPXFactory.eINSTANCE.createTrkType();
      tracks.add(track);
    } else {
      track = tracks.get(0);
    }
   
   FileChooser fileChooser = componentFactory.createFileChooser("Select GPX files to import track segments from");
   List<File> gpxFiles = fileChooser.showOpenMultipleDialog(this);
   
   EMFResource<DocumentRoot> gpxFileResource = GpxUtil.createEMFResource();
   for (File gpxFile: gpxFiles) {
     LOGGER.severe("Handling file: " + gpxFile.getAbsolutePath());
     
     try {
      DocumentRoot fileDocumentRoot = gpxFileResource.load(gpxFile.getAbsolutePath());
      
      if (fileDocumentRoot == null) {
        LOGGER.severe("file documentRoot is null");
        continue;
      }
      
      GpxType fileGpxType = fileDocumentRoot.getGpx();
      if (fileGpxType == null) {
        LOGGER.severe("file documentRoot has no GpxType");
        continue;
      }
      
      List<TrkType> fileTracks = fileGpxType.getTrk();
      if (fileTracks == null  ||  fileTracks.isEmpty()) {
        LOGGER.severe("GpxType has no tracks");
        continue;
      }
      
     for (TrkType fileTrack: fileTracks) {
       for (TrksegType trackSegment: fileTrack.getTrkseg()) {
         LOGGER.severe("Adding segment");
         TrksegType trackSegmentCopy = EcoreUtil.copy(trackSegment);
         track.getTrkseg().add(trackSegmentCopy);
       }
     }
      
    } catch (FileNotFoundException e) {
      LOGGER.severe("Error reading GPX file: " + gpxFile.getAbsolutePath());
      e.printStackTrace();
    }

   }
   
   gpxLayer.clear();
   handleNewGpxFile();
  }
  
  private void trimEndOfTrack() {
//    ResourceSet resourceSet = EMFResourceSet.getResourceSet();
//
//    // disable DTD resolution
//    Map<String, Boolean> parserFeatures = new HashMap<>();
//    parserFeatures.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
//    resourceSet.getLoadOptions().put(XMLResource.OPTION_PARSER_FEATURES, parserFeatures);
//    
//    Path resourceFilePath = Paths.get("C:/Users/Peter/Downloads/2023-02-07_14-33_Tue.gpx").toAbsolutePath().normalize();
//    LOGGER.severe("path=" + resourceFilePath.toString());
//    if (!Files.exists(resourceFilePath)) {
//      LOGGER.severe("file not found");
//      return;
//    }
//    
//    URI fileURI = URI.createFileURI(resourceFilePath.toString());
//    Resource resource = resourceSet.getResource(fileURI, true);
//    DocumentRoot root = (DocumentRoot) resource.getContents().get(0);

    
    // Get the track TODO get selected track from Tree
    GpxType gpxType = documentRoot.getGpx();
    List<TrkType> tracks = gpxType.getTrk();
    if (tracks.isEmpty()) {
      return;
    }
    TrkType track = tracks.get(0);
    List<TrksegType> segments = track.getTrkseg();
    if (segments.isEmpty()) {
      return;
    }
    TrksegType segment = segments.get(0);
    List<WptType> wayPoints = segment.getTrkpt();
//    List<WptType> wayPoints = new ArrayList<>(wayPointsOrig);
    if (wayPoints.size() <  3) {
      return;
    }
    
    // Get the start of the track
    WptType startingPoint = wayPoints.get(0);
//    WGS84Coordinates startCoordinates = new WGS84Coordinates(startingPoint.getLat().doubleValue(), startingPoint.getLon().doubleValue(), startingPoint.getEle().doubleValue());
    LOGGER.severe("Starting location: " + startingPoint.getLat().doubleValue() + ", " + startingPoint.getLon().doubleValue());
    
    // Remove from end of track, until start is encountered
    boolean startFound = false;
    List<WptType> pointsToRemove = new ArrayList<>();
    int index = wayPoints.size() - 1;
    while (!startFound) {
      WptType lastPoint = wayPoints.get(index--);
      if ((startingPoint.getLat().doubleValue() - lastPoint.getLat().doubleValue() < 0.0001)  &&
          (startingPoint.getLon().doubleValue() - lastPoint.getLon().doubleValue() < 0.0001)) {
        startFound = true;
        LOGGER.severe("Start found");
      } else {
//        wayPoints.remove(wayPoints.size() - 1);
        pointsToRemove.add(lastPoint);
        LOGGER.severe("index: " + index);
      }
    }
    wayPoints.removeAll(pointsToRemove);
    
//    gpxLayer.removeGpx(gpxType);
   
//    wayPointsOrig.clear();
//    wayPointsOrig.addAll(wayPoints);
  }

  private void viewDetails() {
    new DetailsView(customization, documentRoot);
  }
  
  private void reduceTrackPoints() {
    reduceTrackPointsWindow = new ReduceTrackPointsWindow(customization, documentRoot != null ? documentRoot.getGpx() : null);
    reduceTrackPointsWindow.setOnHidden((e) -> {
      reduceTrackPointsWindow = null;
      gpxTreeView.removeObjectSelectionListener(gpxTreeViewSelectionListener);
    });
      
    gpxTreeViewSelectionListener = (source, treeItem) -> {
      if (treeItem == null) {
        reduceTrackPointsWindow.objectSelected(null, null);
        return;
      }

      EObjectTreeItemContent eObjectTreeItemContent = treeItem.getValue();
      Object object = eObjectTreeItemContent.getObject();
      if (object != null  &&
          (object instanceof GpxType  ||  object instanceof TrkType  ||  object instanceof TrksegType)) {
        reduceTrackPointsWindow.objectSelected(null, object);
      } else {
        reduceTrackPointsWindow.objectSelected(null, null);
      }
    };
  
    gpxTreeView.addObjectSelectionListener(gpxTreeViewSelectionListener);
    
//    // Get the track TODO get selected track from Tree
//    GpxType gpxType = documentRoot.getGpx();
//    List<TrkType> tracks = gpxType.getTrk();
//    if (tracks.isEmpty()) {
//      return;
//    }
//    TrkType track = tracks.get(0);
//    List<TrksegType> segments = track.getTrkseg();
//    if (segments.isEmpty()) {
//      return;
//    }
//    TrksegType segment = segments.get(0);
//    List<WptType> wayPoints = segment.getTrkpt();
//    
//    List<WptType> reducedWaypoints = DouglasPeuckerReducer.reduceWithTolerance(wayPoints, 40.0, GPXWindow::coordinateExtractor);
//    LOGGER.severe("Original number of waypoints: " + wayPoints.size() + ", new number of waypoints: " + reducedWaypoints.size());
//    wayPoints.clear();
//    wayPoints.addAll(reducedWaypoints);
  }
  
  public static WGS84Coordinates coordinateExtractor(Object object) {
    return GpxUtil.waypointLatLonToWGS84Coordinates((WptType) object);
  }

  /**
   * Update the window title.
   * <p>
   * The format of the title is: &lt;title&gt; - &lt;dirty&gt;&lt;file-name&gt;<br/>
   * Where:<br/>
   * &lt;title&gt; is the language specific window title<br/>
   * &lt;dirty&gt; is a '*' symbol if the vacations file is dirty, empty otherwise<br/>
   * &lt;file-name&gt; is the name of the vacations file.
   * 
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(WINDOW_TITLE);
    buf.append(" - ");
    if (gpxResource.isDirty()) {
      buf.append("*");
    }
    String fileName = gpxResource.getFileName();
    if (fileName == null) {
      fileName = "<NoName>";
    }
    buf.append(fileName);
    
    setTitle(buf.toString());
  }
}
