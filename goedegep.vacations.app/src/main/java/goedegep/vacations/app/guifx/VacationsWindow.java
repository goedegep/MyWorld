package goedegep.vacations.app.guifx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.imaging.ImageReadException;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.locationtech.spatial4j.distance.DistanceUtils;

import com.atlis.location.nominatim.NominatimAPI;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.google.common.geometry.S1Angle;
import com.google.common.geometry.S2LatLng;
import com.google.common.geometry.S2LatLngRect;

import goedegep.appgen.EMFResource;
import goedegep.appgen.ImageSize;
import goedegep.appgen.TableRowOperation;
import goedegep.geo.dbl.WGS84BoundingBox;
import goedegep.geo.dbl.WGS84Coordinates;
import goedegep.gluonmaps.gpx.GPXLayer;
import goedegep.gpx.GpxUtil;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GPXFactory;
import goedegep.gpx.model.GPXPackage;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.util.GPXResourceFactoryImpl;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.jfx.browser.Browser;
import goedegep.jfx.eobjecttreeview.EEnumEditorDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeCell;
import goedegep.jfx.eobjecttreeview.EObjectTreeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItem;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemContent;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.eobjecttreeview.ExtendedNodeOperationDescriptor;
import goedegep.jfx.eobjecttreeview.NodeOperationDescriptor;
import goedegep.jfx.eobjecttreeview.PresentationType;
import goedegep.poi.app.guifx.POIIcons;
import goedegep.poi.model.POICategoryId;
import goedegep.poi.model.POIPackage;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.types.model.TypesPackage;
import goedegep.util.Tuplet;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.emf.EmfPackageHelper;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import goedegep.util.i18n.TranslationFormatter;
import goedegep.util.img.PhotoFileMetaDataHandler;
import goedegep.vacations.app.LocationDescriptionDialog;
import goedegep.vacations.app.VacationsKmlConverter;
import goedegep.vacations.app.logic.NominatimUtil;
import goedegep.vacations.app.logic.OsmAndUtil;
import goedegep.vacations.app.logic.Ov2Util;
import goedegep.vacations.app.logic.VacationToHtmlConverter;
import goedegep.vacations.app.logic.VacationsRegistry;
import goedegep.vacations.app.logic.VacationsUtils;
import goedegep.vacations.checklist.model.VacationChecklist;
import goedegep.vacations.checklist.model.VacationChecklistCategoriesList;
import goedegep.vacations.checklist.model.VacationChecklistFactory;
import goedegep.vacations.checklist.model.VacationChecklistLabelsList;
import goedegep.vacations.checklist.model.VacationChecklistPackage;
import goedegep.vacations.model.ActivityLabel;
import goedegep.vacations.model.BoundingBox;
import goedegep.vacations.model.Day;
import goedegep.vacations.model.GPXTrack;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.MapImage;
import goedegep.vacations.model.Picture;
import goedegep.vacations.model.Text;
import goedegep.vacations.model.Vacation;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.Vacations;
import goedegep.vacations.model.VacationsFactory;
import goedegep.vacations.model.VacationsPackage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.print.PrinterJob;
import javafx.print.PrinterJob.JobStatus;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Window with information about vacations.
 * <p>
 * The vacation information is always coupled to a file, of which the name has to be specified via
 * {@link VacationsRegistry#vacationsFileName}.<br/>
 * If the file name isn't specified, a message is shown and this window will be closed.<br/>
 * If the specified file doesn't exist yet, the user is asked whether the file shall be created. Upon a
 * negative reply, the window will also be closed.
 */
public class VacationsWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(VacationsWindow.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final ResourceBundle TRANSLATIONS = getBundle(VacationsWindow.class, "VacationsResource");
  private static final VacationsPackage VACATIONS_PACKAGE = VacationsPackage.eINSTANCE;
  private static final VacationsFactory VACATIONS_FACTORY = VacationsFactory.eINSTANCE;
  private static final POIPackage POI_PACKAGE = POIPackage.eINSTANCE; 
  private static TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  private static final double FULL_MAP_ZOOM_LEVEL = 1.7;     // Shows almost complete world map. Determined with trial and error.
  private static final double VACATION_ZOOM_LEVEL = 8.0;     // For the time being hard coded zoom level to show one vacation. Determined with trial and error.
  private static final double PICTURE_ZOOM_LEVEL = 12.0;     // For the time being hard coded zoom level to show a picture. Determined with trial and error.
  private static final double HOME_ZOOM_LEVEL = 8.0;         // The zoom level for showing the home location.
  
  private CustomizationFx customization = null;
  private ComponentFactoryFx componentFactory = null;
  private VacationsAppResourcesFx appResources;
  private TranslationFormatter translationFormatter = new TranslationFormatter(TRANSLATIONS);
  private EMFResource<Vacations> vacationsResource = null;
  private EMFResource<VacationChecklist> vacationChecklistResource = null;
  private Vacations vacations = null;
  private VacationsTreeView treeView = null;
  private Label statusLabel = new Label("");
  
  private VacationToHtmlConverter vacationToHtmlConverter;
  private POIIcons poiIcons;
  private MapView mapView;
  private MapRelatedItemsLayer mapRelatedItemsLayer;
  private GPXLayer trackLayer;
  private SearchResultLayer searchResultLayer;
  private LocationSearchWindow locationSearchWindow = null;
  
  private Browser browser;
  private Tab browserTab;
  private Browser tipsBrowser;
  // This menu item defines the 'edit status'.
  private CheckMenuItem vacationTreeEditableMenuItem;
  private NominatimAPI nominatimAPI = null;
  

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   */
  public VacationsWindow(CustomizationFx customization) {
    super(null, customization);
    LOGGER.info("=>");
    
    componentFactory = customization.getComponentFactoryFx();
    appResources = (VacationsAppResourcesFx) getResources();
    
    if (VacationsRegistry.vacationsFileName == null) {
      statusLabel.setText(TRANSLATIONS.getString("VacationsWindow.statusLabel.noVacationsFileNameMsg"));
      Alert alert = componentFactory.createErrorDialog(
          TRANSLATIONS.getString("VacationsWindow.alertNoVacationsFileName.header"),
          TRANSLATIONS.getString("VacationsWindow.alertNoVacationsFileName.content"));
      
      ButtonType editorButtonType = new ButtonType(TRANSLATIONS.getString("VacationsWindow.alertNoVacationsFileName.editorButton"));
      alert.getButtonTypes().add(editorButtonType);
      
      alert.showAndWait().ifPresent(response -> {
        if (response == editorButtonType) {
          showPropertiesEditor();
        }
      });
      
      return;
    }
    
    if (VacationsRegistry.vacationChecklistFileName == null) {
      statusLabel.setText(TRANSLATIONS.getString("VacationsWindow.statusLabel.noVacationChecklistFileNameMsg"));
      Alert alert = componentFactory.createErrorDialog(
          TRANSLATIONS.getString("VacationsWindow.alertNoVacationChecklistFileName.header"),
          TRANSLATIONS.getString("VacationsWindow.alertNoVacationChecklistFileName.content"));
      
      ButtonType editorButtonType = new ButtonType(TRANSLATIONS.getString("VacationsWindow.alertNoVacationsFileName.editorButton"));
      alert.getButtonTypes().add(editorButtonType);
      
      alert.showAndWait().ifPresent(response -> {
        if (response == editorButtonType) {
          showPropertiesEditor();
        }
      });
      
      return;
    }
    
    this.customization = customization;
    
    poiIcons = new POIIcons("POIIconResourceInfo.xmi");
    LocationDescriptionDialog.setPoiIcons(poiIcons);
    
    createGUI();
    
    vacationToHtmlConverter = new VacationToHtmlConverter(poiIcons);
    
    vacationsResource = new EMFResource<>(
        VacationsPackage.eINSTANCE, 
        () -> VacationsFactory.eINSTANCE.createVacations(),
        true);
    
    try {
      vacations = vacationsResource.load(VacationsRegistry.vacationsFileName);
    } catch (FileNotFoundException e) {
      LOGGER.severe("File not found: " + e.getMessage());
      Alert alert = componentFactory.createYesNoConfirmationDialog(
          null,
          null,
          translationFormatter.formatText("VacationsWindow.alertVacationsFileNotFound.header", VacationsRegistry.vacationsFileName),
          TRANSLATIONS.getString("VacationsWindow.alertVacationsFileNotFound.content"));
      alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
          LOGGER.severe("yes, create file");
          vacations = vacationsResource.newEObject();
          try {
            vacationsResource.save(VacationsRegistry.vacationsFileName);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        } else {
          LOGGER.severe("no, don't create file");
        }
      });
    }
    
    vacationChecklistResource = new EMFResource<>(
        VacationChecklistPackage.eINSTANCE, 
        () -> VacationChecklistFactory.eINSTANCE.createVacationChecklist(),
        true);
    
    try {
      vacationChecklistResource.load(VacationsRegistry.vacationChecklistFileName);
      LOGGER.severe(VacationsRegistry.vacationChecklistFileName);
    } catch (FileNotFoundException e) {
      LOGGER.severe("File not found: " + e.getMessage());
      Alert alert = componentFactory.createYesNoConfirmationDialog(
          null,
          null,
          translationFormatter.formatText("VacationsWindow.alertVacationChecklistFileNotFound.header", VacationsRegistry.vacationChecklistFileName),
          TRANSLATIONS.getString("VacationsWindow.alertVacationChecklistFileNotFound.content"));
      alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
          LOGGER.severe("yes, create file");
          VacationChecklist vacationChecklist = vacationChecklistResource.newEObject();
          VacationChecklistCategoriesList vacationChecklistCategoriesList = VacationChecklistFactory.eINSTANCE.createVacationChecklistCategoriesList();
          vacationChecklist.setVacationChecklistCategoriesList(vacationChecklistCategoriesList);
          VacationChecklistLabelsList vacationChecklistLabelsList = VacationChecklistFactory.eINSTANCE.createVacationChecklistLabelsList();
          vacationChecklist.setVacationChecklistLabelsList(vacationChecklistLabelsList);
          try {
            vacationChecklistResource.save(VacationsRegistry.vacationChecklistFileName);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        } else {
          LOGGER.severe("no, don't create file");
        }
      });
    }
       
    setX(10);
    setY(20);
    
    /*
     * TEMP
     * Convert polygons to a Boundary
     */
    
    convertPolygonsToBoundaries(vacations);
    
    /*
     * END TEMP
     */
    
    if (vacations != null) {
      treeView.setEObject(vacations);
    }
    
    flyHome();
    updateTitle();
    
    vacationsResource.dirtyProperty().addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        updateTitle();        
      }
      
    });
    vacationsResource.dirtyProperty().addListener((observable, oldValue, newValue) -> updateTitle());
        
    vacationsResource.addNotificationListener(this::handleChangesInTheVacationsData);
    
    handleNewTreeItemSelected(null);
    updateTipsPane();
    
    show();
    
    LOGGER.info("<=");
  }
  
  public MapView getMapView() {
    return mapView;
  }
  
  private void handleChangesInTheVacationsData(Notification notification) {
    LOGGER.info("=>");
    
    updateTipsPane();
  }
  
  private void convertPolygonsToBoundaries(Vacations vacations) {
//    TreeIterator<EObject> vacationIterator = vacations.eAllContents();
//    while (vacationIterator.hasNext()) {
//      EObject eObject = vacationIterator.next();
//      if (eObject instanceof Location) {
//        Location location = (Location) eObject;
//        List<WGS84Coordinates> coordinates = location.getPolygon();
//        if (coordinates != null) {
//          LOGGER.info("coordinates not null");
//          if (!coordinates.isEmpty()) {
//            LOGGER.severe("Converting: " + location.getName());
//            Boundary boundary = VACATIONS_FACTORY.createBoundary();
//            boundary.getPoints().addAll(coordinates);
//            
//            location.getBoundaries().add(boundary);
//            coordinates.clear();
//          } else {
//            LOGGER.info("coordinates is empty");
//          }
//        }
//      }
//    }
  }

  /**
   * Get a {@link ResourceBundle} for the default locale.
   * 
   * @param clazz a class from the package in which the ResourceBundle is located.
   * @param bundle basename of the resource bundle
   * @return the requested ResourceBundle
   */
  public static ResourceBundle getBundle(Class<? extends Object> clazz, String bundle) {
      String bundlePath = clazz.getPackage().getName() + "." + bundle;
      LOGGER.info("bundlePath: " + bundlePath);
      Locale locale = Locale.getDefault();
      ClassLoader classLoader = clazz.getClassLoader();
      LOGGER.info("classLoader: " + classLoader.getName());
      return ResourceBundle.getBundle(bundlePath, locale, classLoader);
  }
  
  public POIIcons getPOIIcons() {
    return poiIcons;
  }
  
  /**
   * Get the {@code VacationsTreeView}.
   * 
   * @return the {@code VacationsTreeView}.
   */
  public VacationsTreeView getTreeView() {
    return treeView;
  }
  
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    
    /*
     * Main pane is a BorderPane.
     * North is the menu bar
     * Center is a SplitPane with a TreeView for the Holidays on the left and TabPane on the right.
     *        There are Tabs for a map and an HTML view.
     * Bottom is a status label
     */
    
    BorderPane mainPane = new BorderPane();
        
    mainPane.setTop(createMenuBar());
    
    SplitPane centerPane = new SplitPane();
    centerPane.setOrientation(Orientation.HORIZONTAL);
    centerPane.setDividerPositions(0.3);
    
    // Vacations TreeView - centerPane left
    EObjectTreeDescriptor eObjectTreeDescriptor = createEObjectTreeDescriptor();
    treeView = new VacationsTreeView(eObjectTreeDescriptor, vacationTreeEditableMenuItem.isSelected());
    treeView.setMinWidth(300);
    treeView.addObjectSelectionListener(treeItem -> handleNewTreeItemSelected(treeItem));
    centerPane.getItems().add(treeView);

    // MapView
    mapView = new MapView();
    mapRelatedItemsLayer = new MapRelatedItemsLayer(customization, poiIcons, this);
    mapView.addLayer(mapRelatedItemsLayer);
    trackLayer = new GPXLayer();
    mapView.addLayer(trackLayer);
    searchResultLayer = new SearchResultLayer();
    mapView.addLayer(searchResultLayer);
    ControlsLayer controlsLayer = new ControlsLayer(customization, this, getNominatimAPI(), searchResultLayer);
    mapView.addLayer(controlsLayer);
    mapView.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent mouseEvent) {
        LOGGER.info("=>");
        
        // If 'right' button clicked, show context menu.
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
          LOGGER.info("mapView MouseEvent: x=" + mouseEvent.getX() + ", y=" + mouseEvent.getY());
          MapPoint newPoint = mapView.getMapPosition(mouseEvent.getX(), mouseEvent.getY());
          LOGGER.info("newPoint= " + newPoint.getLatitude() + "," + newPoint.getLongitude());
          
          ContextMenu contextMenu = new ContextMenu();
          MenuItem menuItem;
          
          // Get location information
          menuItem = componentFactory.createMenuItem("Get location information");
          TreeItem<EObjectTreeItemContent> treeItem = treeView.getSelectedObject();
          
          menuItem.setOnAction((ActionEvent event) -> {
            openLocationSearchWindow((EObjectTreeItem) treeItem, newPoint);
          });
          contextMenu.getItems().add(menuItem);
          
          // Add MapImage
          menuItem = componentFactory.createMenuItem("Add map image");
          menuItem.setOnAction((ActionEvent event) -> {
            createAndAddMapImage((EObjectTreeItem) treeItem);
          });
          contextMenu.getItems().add(menuItem);

          
          contextMenu.setAutoHide(true);
          
          mapView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

              contextMenu.show(mapView, event.getScreenX(), event.getScreenY());
            }
          });
        }
      }
      
    });
    
    TabPane tabPane = new TabPane();
    Tab tab = new Tab();
    tab.setText("Kaart");
    tab.setContent(mapView);
    tabPane.getTabs().add(tab);
    
    browserTab = new Tab();
    browserTab.setText("Document");
    browser = new Browser(null);
    browserTab.setContent(browser);
    ChangeListener<? super Tab> c = new ChangeListener<>() {

      @Override
      public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
        if (newValue == null  ||  newValue != browserTab) {
          // clear browser content and disable updating
          browser.loadContent(null);
        } else {
          // reload browser content and enable updating
          updateDocumentView(treeView.getSelectedObject());                    
        }
      }
    };
    tabPane.getSelectionModel().selectedItemProperty().addListener(c);
    tabPane.getTabs().add(browserTab);
    
    tab = new Tab();
    tab.setText("Tips");
    tipsBrowser = new Browser(null);
    tab.setContent(tipsBrowser);
    tabPane.getTabs().add(tab);
    
    
    centerPane.getItems().add(tabPane);
    
    mainPane.setCenter(centerPane);
    
    mainPane.setBottom(statusLabel);

    setScene(new Scene(mainPane, 1700, 900));
  }
  
  /**
   * Create a <code>Location</code> and add it as last child to a TreeItem.
   * 
   * @param treeItem The TreeItem to which the new <code>Location</code> is to be added.
   * @param mapPoint A <code>MapPoint</code> which provides the coordinates of the <code>Location</code> to be created.
   */
  private void openLocationSearchWindow(EObjectTreeItem treeItem, MapPoint mapPoint) {
    LOGGER.severe("=> mapPoint=" + mapPoint.getLatitude() + "," + mapPoint.getLongitude());
    
    if (locationSearchWindow == null) {
      locationSearchWindow = new LocationSearchWindow(customization, this, nominatimAPI, searchResultLayer);
    }
    
    locationSearchWindow.show();
    locationSearchWindow.reverseGeocodeSearch(mapPoint.getLatitude(), mapPoint.getLongitude());
        
    LOGGER.severe("<=");
  }
  
  /**
   * Create an image (.jpg) file for the current map view and add that image as MapImage to the selected tree item.
   * <p>
   * The image is stored in the folder related to the vacation.
   * 
   * @param treeItem The TreeItem to which the new <code>MapImage</code> is to be added.
   */
  private void createAndAddMapImage(EObjectTreeItem treeItem) {
    LOGGER.severe("=>");

    if (treeItem == null) {
      LOGGER.severe("treeItem is null, no action");
      return;
    }

    // Create the image
    SnapshotParameters snapShotParameters = new SnapshotParameters();
    WritableImage writebleImage = new WritableImage((int) mapView.getWidth(), (int) mapView.getHeight());
    mapView.snapshot(snapShotParameters, writebleImage);

    // Save the image to the vacation folder
    Vacation vacation = getVacationForTreeItem(treeItem);
    if (vacation == null) {
      LOGGER.severe("No vacation found for treeItem, no action");
    }
    LOGGER.severe("Vacation: " + vacation.getTitle());
    String vacationFolderName = VacationsUtils.getVacationFolder(vacation);
    if (vacationFolderName == null) {
      LOGGER.severe("No vacation folder found");
    }
    String fileName = FileUtils.createBackupFileName("MapImage.jpg");
    File file = new File(vacationFolderName, fileName);
    saveImageAsJpeg(writebleImage, file);
    
    // Create a MapImage and add it to the treeItem
    MapImage mapImage = VACATIONS_FACTORY.createMapImage();
    FileReference imageFileReference = TYPES_FACTORY.createFileReference();
    imageFileReference.setFile(file.getAbsolutePath());
    mapImage.setImageReference(imageFileReference);
    addVacationElement(treeItem, mapImage);
  }

  public void saveImageAsJpeg(Image image, File file) {
    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
    BufferedImage imageRGB = new BufferedImage(
        bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.OPAQUE);
    Graphics2D graphics = imageRGB.createGraphics();
    graphics.drawImage(bufferedImage, 0, 0, null);
    try {
      ImageIO.write(imageRGB, "jpg", file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Add the location, as specified in this panel, to the tree.
   * <p>
   * If the selected tree item is a list of (a supertype of) Locations, it is added as last child of this list.<br/>
   * If the selected tree item is an item in a list of (a supertype of) Locations, it is added to this list before or after the selected item.
   * The value of the <code>beforeOrAfterComboBox</code> determines whether it is added before or after the selected item.<br/>
   * If the <code>useReverseGeocodeSearchCoordinates</code> checkbox is checked, the coordinates are not taken from this panel,
   * but from the <code>ReverseGeocodePanel</code> (if available).
   */
  private void addVacationElement(EObjectTreeItem treeItem, VacationElement vacationElement) {
    
    EObjectTreeItemContent eObjectTreeItemContent = treeItem.getValue();
    
    // If the selected tree item is a list of (a supertype of) Locations, it is added as last child of this list.
    EStructuralFeature eStructuralFeature = eObjectTreeItemContent.getEStructuralFeature();
    if (eStructuralFeature instanceof EReference) {
      EReference eReference = (EReference) eStructuralFeature;
      EClass listType = eReference.getEReferenceType();
      EClass locationEClass = VacationsPackage.eINSTANCE.getLocation();
      if (EmfUtil.isInstanceof(locationEClass, listType)  && eReference.isMany()) {
        LOGGER.severe("match");
        @SuppressWarnings("unchecked")
        EList<EObject> eObjectList = (EList<EObject>) eObjectTreeItemContent.getObject();

        eObjectList.add(vacationElement);
        treeItem.rebuildChildren();
        
        return;
      }
    }
    
    // If the selected tree item is an item in a list of (a supertype of) Locations, it is added to this list before or after the selected item.
    // In this case the parent item of the selected item shall be a list of (a supertype of) Locations.
    EObjectTreeItem parentTreeItem = (EObjectTreeItem) treeItem.getParent();
    EObjectTreeItemContent eObjectTreeItemContentParentTreeItem = parentTreeItem.getValue();
    EStructuralFeature eStructuralFeatureParentTreeItem = eObjectTreeItemContentParentTreeItem.getEStructuralFeature();
    if (eStructuralFeatureParentTreeItem instanceof EReference) {
      EReference eReference = (EReference) eStructuralFeatureParentTreeItem;
      EClass listType = eReference.getEReferenceType();
      EClass locationEClass = VacationsPackage.eINSTANCE.getLocation();
      if (EmfUtil.isInstanceof(locationEClass, listType)  && eReference.isMany()) {
        LOGGER.severe("parent match");
        @SuppressWarnings("unchecked")
        EList<EObject> eObjectList = (EList<EObject>) eObjectTreeItemContentParentTreeItem.getObject();
        
        Object object = eObjectTreeItemContent.getObject();
        
        int index = eObjectList.indexOf(object);
        LOGGER.info("index=" + index);
        eObjectList.add(index, vacationElement);

        parentTreeItem.rebuildChildren();
        
        return;
      }
    }
    
  }
  
  /**
   * Create the menu bar for this window.
   * @param developmentMode 
   * 
   * @return the menu bar for this window.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu menu;
    MenuItem menuItem;

    // File menu
    menu = new Menu("File");

    // File: Save vacations
    menuItem = componentFactory.createMenuItem("Save vacations");
    menuItem.setOnAction(event -> saveVacations());
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    menu.getItems().add(menuItem);

    // File: Print selected vacation
    menuItem = componentFactory.createMenuItem("Print selected vacation");
    menuItem.setOnAction(event -> printVacation());
    menu.getItems().add(menuItem);

    // File: Export selected vacation as HTML
    menuItem = componentFactory.createMenuItem("Export selected vacation as HTML");
    menuItem.setOnAction(event -> exportVacationToHtml());
    menu.getItems().add(menuItem);

    // File: Print current map
    menuItem = componentFactory.createMenuItem("Print current map");
    menuItem.setOnAction(event -> print(mapView));
    menu.getItems().add(menuItem);
    
    // File: Import photos
    menuItem = componentFactory.createMenuItem("Import photos");
    menuItem.setOnAction(event -> importPhotos());
    menu.getItems().add(menuItem);    

    // File: Import vacations
    menuItem = componentFactory.createMenuItem("Import vacations");
    menuItem.setOnAction(event -> importVacations());
    menu.getItems().add(menuItem);
    
    // File: Create a kml file
    menuItem = componentFactory.createMenuItem("Create a kml file");
    menuItem.setOnAction(event -> createVacationsKml());
    menu.getItems().add(menuItem);
    
    // File: Import locations from kml/kmz file
    menuItem = componentFactory.createMenuItem("Import locations from kml/kmz file");
    menuItem.setOnAction(event -> importLocationsFromKmlFile());
    menu.getItems().add(menuItem);
    
    // File: Create OsmAnd favorites file
    menuItem = componentFactory.createMenuItem("Create OsmAnd favorites file");
    menuItem.setOnAction(event -> createOsmAndFavouritesFile());
    menu.getItems().add(menuItem);
    
    // File: Create TomTom ov2 file
    menuItem = componentFactory.createMenuItem("Create TomTom ov2 file");
    menuItem.setOnAction(event -> createTomTomOv2File());
    menu.getItems().add(menuItem);
    
    if (VacationsRegistry.developmentMode) {
      // File: Edit Property Descriptors
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", event -> showPropertyDescriptorsEditor());
      
      // File: Edit Properties
      MenuUtil.addMenuItem(menu, "Edit Properties", event -> showPropertiesEditor());
      
      // File: Map Snapshot popup
      MenuUtil.addMenuItem(menu, "Map Snapshot popup", event -> showMapSnapshotPopup());
      
      // File: Use demo vacations file
      MenuUtil.addMenuItem(menu, "Use demo vacations file", event -> useDemoVacationsFile());
    }
    
    menuBar.getMenus().add(menu);
    
    // Edit menu
    menu = new Menu("Edit");

    // Edit: Edit vacations
    vacationTreeEditableMenuItem = new CheckMenuItem("Edit vacations");
    vacationTreeEditableMenuItem.setSelected(false);
    
    vacationTreeEditableMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        treeView.setEditMode(vacationTreeEditableMenuItem.isSelected());
        mapRelatedItemsLayer.setEditMode(vacationTreeEditableMenuItem.isSelected());
      }
    });
    menu.getItems().add(vacationTreeEditableMenuItem);
    
    menuBar.getMenus().add(menu);
    
    // Checklist menu
    menu = new Menu("Checklist");

    // Checklist: Vacation checklist
    MenuUtil.addMenuItem(menu, "Vacation checklist", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        new VacationChecklistWindow(customization, vacationChecklistResource);
      }
    });

    // Checklist: Edit vacation Checklist
    MenuUtil.addMenuItem(menu, "Edit vacation Checklist", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        new VacationChecklistEditor(customization, vacationChecklistResource);
      }
    });
    
    menuBar.getMenus().add(menu);
    
    // Tools menu
    menu = new Menu("Tools");

    // Tools: Check vacations
    MenuUtil.addMenuItem(menu, "Check vacations", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        new CheckVacationsWindow(customization, vacations);
      }
    });
    
    menuBar.getMenus().add(menu);

    // Help menu
    menu = new Menu("Help");

    // Help: About
    MenuUtil.addMenuItem(menu, "About", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        showHelpAboutDialog();
      }
    });

    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
  /**
   * Try to fly to the home location (lat/long of vacations.getHome()).
   * If this isn't available, go to the zero lat/long location.
   * <p>
   * The zoom level is set to HOME_ZOOM_LEVEL, if we fly to the home location.
   * Otherwise the zoom level is set to FULL_MAP_ZOOM_LEVEL.</br>
   * The home icon is the only icon shown.<br/>
   * Animation is used while moving to the new location.
   */
  private void flyHome() {
    LOGGER.info("=>");
    
    mapRelatedItemsLayer.clear();
    
    mapView.setZoom(8);
    
    MapPoint mapCenter = null;
    Location homeLocation = vacations.getHome();
    if (homeLocation != null) {
      mapCenter = new MapPoint(vacations.getHome().getLatitude(), vacations.getHome().getLongitude());
    }
        
    if (mapCenter != null) {
      mapView.setZoom(HOME_ZOOM_LEVEL);
      flyToIfEnabled(0.1, mapCenter, 3.0);
    }
    
    LOGGER.info("<=");
  }
  
  /**
   * Show the 'complete' world map.
   */
  private void showWorldMap() {
    MapPoint mapCenter = new MapPoint(0.0, 0.0);
    
    mapView.setZoom(FULL_MAP_ZOOM_LEVEL);
    flyToIfEnabled(0.1, mapCenter, 3.0);
  }
    
  /**
   * Handle the fact that a new item is selected in the {@link #treeView}.
   * <p>
   * This is handled by updating the document view and the vacations layer.
   * 
   * @param treeItem the newly selected item in the <code>treeView</code>.
   */
  private void handleNewTreeItemSelected(TreeItem<EObjectTreeItemContent> treeItem) {
    updateDocumentView(treeItem);
    updateVacationsLayer(treeItem);
  }
  
  /**
   * Update the document view for a newly selected item.
   * <p>
   * The document view shows the generated document for the vacation related to the selected item,
   * or a message if the selected item is not related to a vacation.<br/>
   * If the selected item is a Vacation, this is the related vacation. Otherwise the parent relations are followed until
   * either a vacation is found, or that the top of the hierarchy is encountered.
   * If a vacation is found, that will be the related vacation, otherwise there is no related vacation.<br/>
   * If the <code>treeItem</code> is null, there is also no related vacation.
   * 
   * @param treeItem the newly selected item in the <code>treeView</code>, which may be null.
   */
  private void updateDocumentView(TreeItem<EObjectTreeItemContent> treeItem) {
    if (!browserTab.isSelected()) {
      return;
    }
    
    String htmlText = TRANSLATIONS.getString("VacationsWindow.noVacationSelectedMsg");
    LOGGER.info("htmlText=" + htmlText);
    
    if (treeItem != null) {
      Vacation vacation = getVacationForTreeItem(treeItem);
      if (vacation != null) {
        htmlText = vacationToHtmlConverter.vacationToHtml(vacation);
      }
    }
    
    browser.loadContent(htmlText);
  }
  
  /**
   * Update the tips pane for new content.
   * 
   * @param treeItem the newly selected item in the <code>treeView</code>, which may be null.
   */
  private void updateTipsPane() {
    String htmlText = "No tips available yet";
    
    if (vacations != null) {
      String tips = vacations.getTips();
      if ((tips != null)  &&  !tips.isEmpty()) {
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        org.commonmark.node.Node document = parser.parse(tips);
        htmlText = renderer.render(document);  
      }
    }
    
    tipsBrowser.loadContent(htmlText);
  }

  /**
   * Update the information shown in the vacations layer.
   * <p>
   * The <b>home</b> location of the <b>Vacations</b> is always shown.<br/>
   * Any other information shown, is related to the specified {@code TreeItem}:
   * <ul>
   * <li>
   * If the <code>treeItem</code> is null, nothing is shown and the center of the map and the zoom level aren't changed.
   * </li>
   * <li>
   * Else, if the <code>treeItem</code> is related to a <b>Picture</b>, and the location of the picture can be determined,
   * this picture is shown at that location. The location of the picture can either come from the picture itself, or from any parent Location element.
   * The map center is changed such that the picture will be visible. The zoomlevel is set such that the picture is visible and fills most of the screen.  
   * Besides this, all locations/tracks of the related vacation are shown.
   * </li>
   * <li>
   * Else, if the <code>treeItem</code> is a <code>Location</code> related to a <b>Vacation</b>, the locations/tracks of that vacation are shown.
   * The map center is changed to this location and the zoom level is set to show most of the items of the vacation.
   * </li>
   * <li>
   * Else, if the <code>treeItem</code> is a GPX track related to a <b>Vacation</b>, the locations/tracks of that vacation are shown.
   * The map center is changed to the center of the track and the zoom level is set to show most of the items of the vacation.
   * </li>
   * <li>
   * Else, if the <code>treeItem</code> is a <b>Vacation</b> node, or any of its attributes or children, the locations/tracks of that vacation are shown.
   * The map center is changed to the center of the locations/tracks and the zoom level is set to show most of the items of the vacation.
   * </li>
   * <li>
   * Else, if the <code>treeItem</code> is the <b>vacations</b> attribute of <b>Vacations</b>, 
   * the locations/tracks of all vacations are shown.<br/>
   * The world map is shown.
   * </li>
   * <li>
   * Finally, if the <code>treeItem</code> is the <b>Vacations</b> node,
   * the home location and the locations/tracks of all vacations are shown.<br/>
   * The world map is shown.
   * </li>
   * </ul>
   * @param treeItem the <code>TreeItem</code> for which information is to be shown.
   */
  private void updateVacationsLayer(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    mapRelatedItemsLayer.clear();
    trackLayer.clear();
    addHomeLocationToVacationsLayer();
    
    if (treeItem == null) {
      return;
    }
    
    MapPoint mapCenter = null;
    Double zoomLevel = null;
    Tuplet<Picture, WGS84Coordinates> pictureDataTuplet;
    Tuplet<Location, Vacation> locationDataTuplet;
    Tuplet<GPXTrack, Vacation> trackDataTuplet;
    Vacation vacation = null;
    Vacations vacations = null;
    List<Vacation> vacationsList = null;
    
    
    if ((pictureDataTuplet = getPictureDataForTreeItem(treeItem)) != null) {                // Picture
      Picture vacationElementPicture = pictureDataTuplet.getObject1();
      vacation = vacationElementPicture.getVacation();
      addVacationToMapView(vacation, false);
      mapRelatedItemsLayer.showCurrentPhoto(vacationElementPicture.getPictureReference().getFile());
      WGS84Coordinates coordinates = pictureDataTuplet.getObject2();
      mapCenter = new MapPoint(coordinates.getLatitude() - 0.05, coordinates.getLongitude() + 0.1);
      zoomLevel = PICTURE_ZOOM_LEVEL;
    } else if ((locationDataTuplet = getLocationDataForTreeItem(treeItem)) != null) {       // Location
      Location location = locationDataTuplet.getObject1();
      vacation = locationDataTuplet.getObject2();
      addVacationToMapView(vacation, false);
      mapCenter = new MapPoint(location.getLatitude(), location.getLongitude());
      zoomLevel = VACATION_ZOOM_LEVEL;
    } else if ((trackDataTuplet = getTrackDataForTreeItem(treeItem)) != null) {             // Track
      GPXTrack track = trackDataTuplet.getObject1();
      vacation = trackDataTuplet.getObject2();
      addVacationToMapView(vacation, false);
      FileReference fileReference = track.getTrackReference();
      if ((fileReference != null)  &&  (fileReference.getFile() != null)) {
        EMFResource<DocumentRoot> gpxResource = new EMFResource<>(GPXPackage.eINSTANCE, () -> GPXFactory.eINSTANCE.createDocumentRoot(), false);
        gpxResource.addResourceFactoryForFileExtension("gpx", new GPXResourceFactoryImpl());
        try {
          gpxResource.load(fileReference.getFile());
          DocumentRoot documentRoot = gpxResource.getEObject();
          GpxType gpxType = documentRoot.getGpx();
          WGS84BoundingBox boundingBox = GpxUtil.calculateBoundingBox(gpxType);
          WGS84Coordinates center = boundingBox.getCenter();
          LOGGER.severe("center: " + center.toString());
          mapCenter = new MapPoint(center.getLatitude(), center.getLongitude());
          zoomLevel = VACATION_ZOOM_LEVEL;
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }    	  
      }
    } else if ((vacation = getVacationForTreeItem(treeItem)) != null) {                      // vacation
      WGS84BoundingBox vacationsLayerBoundingBox = addVacationToMapView(vacation, false);
      if (vacationsLayerBoundingBox != null) {
        WGS84Coordinates center = vacationsLayerBoundingBox.getCenter();
        mapCenter = new MapPoint(center.getLatitude(), center.getLongitude());
      }
      if (vacationsLayerBoundingBox != null) {
        zoomLevel = MapView.getZoomLevel(vacationsLayerBoundingBox);
      }
    } else if ((vacationsList = getVacationsListForTreeItem(treeItem)) != null) {            // vacations (the list)
      addVacationsToVacationsLayer(vacationsList);
      showWorldMap();
    } else if ((vacations = getVacationsForTreeItem(treeItem)) != null) {
      addVacationsToVacationsLayer(vacations.getVacations());
      showWorldMap();
    } else {
      Object treeItemObject = treeItem.getValue().getObject();
      LOGGER.severe("Don't know what to show for selected treeItem, treeItemObject=" + (treeItemObject != null ? treeItemObject.toString() : "null"));
    }
    
    if (zoomLevel != null) {
      setZoomIfEnabled(zoomLevel);
    }
    
    if (mapCenter != null) {
      flyToIfEnabled(0.1, mapCenter, 3.0);
    }
    
    LOGGER.info("<=");
  }
  
  /**
   * For a given treeItem, try to find the related picture and, if found, try to find the related Location.
   * <p>
   * If the item is a <code>Picture</code>, this is the related picture. Otherwise the parent relations are followed until
   * either a <code>Picture</code> is found, or that the top of the hierarchy is encountered.
   * If a <code>Picture</code> is found, that will be the related picture, otherwise there is no related picture.<br/>
   * Parent relations are followed, because if one of the children of a picture is selected, we also want to focus on the picture.<br/>
   * If there is a related picture, the location for this picture is determined. First it is tried to obtain the location from the picture file
   * itself. If this fails, from this picture parent relations (container) are followed until a Location is encountered, or until there's no parent anymore.
   * 
   * @param treeItem the <code>TreeItem</code> for which the related picture and location are to be found.
   * @return a <code>Tuplet</code> with the related picture and location, or null if one of these doesn't exist.
   */
  private Tuplet<Picture, WGS84Coordinates> getPictureDataForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.info("=>");
    Picture vacationElementPicture = getRelatedPictureForTreeItem(treeItem);
    
    if (vacationElementPicture == null) {
      return null;
    }
    
    WGS84Coordinates pictureCoordinates = getPictureCoordinates(vacationElementPicture);
    
    if (pictureCoordinates == null) {
      LOGGER.severe("<= (null)");
      return null;
    }
    
    LOGGER.info("<=");
    return new Tuplet<>(vacationElementPicture, pictureCoordinates);
  }
  
  /**
   * Get the <code>VacationElementPicture</code> related to a <code>TreeItem</code>.
   * <p>
   * If the item is a <code>Picture</code>, this is the related picture. Otherwise the parent relations are followed until
   * either a <code>Picture</code> is found, or that the top of the hierarchy is encountered.
   * If a <code>Picture</code> is found, with a non empty file reference, that will be the related picture,
   * otherwise there is no related picture.<br/>
   * If the <code>treeItem</code> is null, there is also no related picture.
   * 
   * @param treeItem
   * @return
   */
  private Picture getRelatedPictureForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      return null;
    }
    
    EObjectTreeItemContent eObjectTreeItemContent = treeItem.getValue();
    Object treeItemObject = eObjectTreeItemContent.getObject();
    Picture vacationElementPicture = null;
    
    while (treeItem != null  &&  !(treeItemObject instanceof Picture)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        eObjectTreeItemContent = treeItem.getValue();
        treeItemObject = eObjectTreeItemContent.getObject();
      }
    }
    
    if (treeItemObject instanceof Picture) {
      vacationElementPicture = (Picture) treeItemObject;
      if (vacationElementPicture.isSetPictureReference()) {
        FileReference pictureReference = vacationElementPicture.getPictureReference();
        String fileName = pictureReference.getFile();
        if (fileName == null  ||  fileName.isEmpty()) {
          vacationElementPicture = null;
        }
      } else {
        vacationElementPicture = null;
      }
    }

    LOGGER.info("<= vacationElementPicture=" + (vacationElementPicture != null ? vacationElementPicture.toString() : "(null)"));
    return vacationElementPicture;
  }
  
  /**
   * Get the coordinates to be used for showing a photo on the map.
   * <p>
   * If the photo has coordinates, these are the ones to be used.
   * Else if there is a parent location, the coordinates of this location are used.
   * 
   * @return the coordinates for the photo, or null is no coordinates are available.
   */
  private WGS84Coordinates getPictureCoordinates(Picture vacationElementPicture) {
    WGS84Coordinates coordinates = PhotoFileMetaDataHandler.getGeoLocation(vacationElementPicture.getPictureReference().getFile());
    
    if (coordinates == null) {
      Location location = getRelatedLocation(vacationElementPicture);
      if ((location != null)  &&  (location.getLatitude() != null)  &&  (location.getLongitude() != null)) {
        LOGGER.info("Location: " + location.getLatitude() + ", " + location.getLongitude());
        coordinates = new WGS84Coordinates(location.getLatitude(), location.getLongitude());
      }
    }
    
    return coordinates;
  }
  
  /**
   * Get the <code>Location</code> to which a picture belongs.
   * <p>
   * Parent relations (container) are followed until a Location is encountered, or until there's no parent anymore.
   * 
   * @param vacationElementPicture for which the related location is to be found.
   * @return The <code>Location</code> to which the picture is related.
   */
  private Location getRelatedLocation(Picture vacationElementPicture) {
    if (vacationElementPicture == null) {
      return null;
    }
    
    EObject container = vacationElementPicture.eContainer();
    
    while ((container != null)  &&  !(container instanceof Location)) {
      container = container.eContainer();
    }
    
    return (Location) container;
  }
  
  /**
   * For a given treeItem, try to find the related Location and, if found, try to find the related Vacation.
   * <p>
   * If the item is a <code>Location</code>, this is the related Location. Otherwise the parent relations are followed until
   * either a <code>Location</code> is found, or that the top of the hierarchy is encountered.
   * If a <code>Location</code> is found, that will be the related location, otherwise there is no related location.<br/>
   * If there is a related location, from this location parent relations (container) are followed until a Vacation is encountered, or until there's no parent anymore.
   * 
   * @param treeItem the <code>TreeItem</code> for which the related Location and Vacation are to be found.
   * @return a <code>Tuplet</code> with the related Location and Vacation, or null if one of these doesn't exist.
   */
  private Tuplet<Location, Vacation> getLocationDataForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    Location location = getRelatedLocationForTreeItem(treeItem);
    
    if (location == null) {
      return null;
    }
    
    Vacation vacation = location.getVacation();
    
    if (vacation == null) {
      return null;
    }
    
    return new Tuplet<>(location, vacation);
  }
  
  /**
   * Get the <code>Location</code> related to a <code>TreeItem</code>.
   * <p>
   * If the item is a <code>Location</code>, this is the related Location. Otherwise the parent relations are followed until
   * either a <code>Location</code> is found, or that the top of the hierarchy is encountered.
   * If a <code>Location</code> is found, that will be the related Location, otherwise there is no related Location.<br/>
   * If the <code>treeItem</code> is null, there is also no related location.
   * 
   * @param treeItem the <code>TreeItem</code> for which the related location is to be found.
   * @return the <code>Location</code> related to the <code>treeItem</code>, or null if this doesn't exist.
   */
  private Location getRelatedLocationForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      return null;
    }
    
    EObjectTreeItemContent eObjectTreeItemContent = treeItem.getValue();
    Object treeItemObject = eObjectTreeItemContent.getObject();
    Location location = null;
    
    while (treeItem != null  &&  !(treeItemObject instanceof Location)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        eObjectTreeItemContent = treeItem.getValue();
        treeItemObject = eObjectTreeItemContent.getObject();
      }
    }
    
    if (treeItemObject instanceof Location) {
      location = (Location) treeItemObject;
      // It is only a useful location if the coordinates are set.
      if (!location.isSetLatitude()  ||  !location.isSetLongitude()) {
        location = null;
      }
    }

    LOGGER.info("<= location=" + (location != null ? location.toString() : "(null)"));
    return location;
  }
  
  /**
   * For a given treeItem, try to find the related Track and, if found, try to find the related Vacation.
   * <p>
   * If the item is a <code>VacationElementGPX</code>, this is the related Track. Otherwise the parent relations are followed until
   * either a <code>VacationElementGPX</code> is found, or that the top of the hierarchy is encountered.
   * If a <code>VacationElementGPX</code> is found, that will be the related Track, otherwise there is no related Track.<br/>
   * If there is a related Track, from this location parent relations (container) are followed until a Vacation is encountered,
   * or until there's no parent anymore.
   * 
   * @param treeItem the <code>TreeItem</code> for which the related Track and Vacation are to be found.
   * @return a <code>Tuplet</code> with the related Track and Vacation, or null if one of these doesn't exist.
   */
  private Tuplet<GPXTrack, Vacation> getTrackDataForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    GPXTrack track = getRelatedTrackForTreeItem(treeItem);
    
    if (track == null) {
      LOGGER.info("<= (null) (no track)");
      return null;
    }
    
    Vacation vacation = track.getVacation();
    
    if (vacation == null) {
      LOGGER.info("<= (null) (no vacation for track)");
      return null;
    }
    
    LOGGER.info("<= track=" + (track != null ? track.toString() : "(null)") + ", vacation=" + (vacation != null ? vacation.toString() : "(null)"));
    
    return new Tuplet<>(track, vacation);
  }
  
  /**
   * Get the <code>VacationElementGPX</code> related to a <code>TreeItem</code>.
   * <p>
   * If the item is a <code>VacationElementGPX</code>, this is the related Track. Otherwise the parent relations are followed until
   * either a <code>VacationElementGPX</code> is found, or that the top of the hierarchy is encountered.
   * If a <code>VacationElementGPX</code> is found, that will be the related Track, otherwise there is no related Track.<br/>
   * If the <code>treeItem</code> is null, there is also no related Track.
   * 
   * @param treeItem the <code>TreeItem</code> for which the related Track is to be found.
   * @return the <code>VacationElementGPX</code> related to the <code>treeItem</code>, or null if this doesn't exist.
   */
  private GPXTrack getRelatedTrackForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      LOGGER.info("<= (null) (treeItem == null)");
      return null;
    }
    
    EObjectTreeItemContent eObjectTreeItemContent = treeItem.getValue();
    Object treeItemObject = eObjectTreeItemContent.getObject();
    GPXTrack track = null;
    
    while (treeItem != null  &&  !(treeItemObject instanceof GPXTrack)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        eObjectTreeItemContent = treeItem.getValue();
        treeItemObject = eObjectTreeItemContent.getObject();
      }
    }
    
    if (treeItemObject instanceof GPXTrack) {
      track = (GPXTrack) treeItemObject;
    }

    LOGGER.info("<= track=" + (track != null ? track.toString() : "(null)"));
    return track;
  }
  
  /**
   * For a given treeItem, try to find the related Vacation.
   * <p>
   * If the item is a <code>Vacation</code>, this is the related Vacation. Otherwise the parent relations are followed until
   * either a <code>Vacation</code> is found, or that the top of the hierarchy is encountered.
   * If a <code>Vacation</code> is found, that will be the related Vacation, otherwise there is no related Vacation.<br/>
   * 
   * @param treeItem the <code>TreeItem</code> for which the related Vacation is to be found.
   * @return the related Vacation, or null if this doesn't exist.
   */
  private static Vacation getVacationForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      return null;
    }
    
    EObjectTreeItemContent eObjectTreeItemContent = treeItem.getValue();
    Object treeItemObject = eObjectTreeItemContent.getObject();
    Vacation vacation = null;
    
    while (treeItem != null  &&  !(treeItemObject instanceof Vacation)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        eObjectTreeItemContent = treeItem.getValue();
        treeItemObject = eObjectTreeItemContent.getObject();
      }
    }
    
    if (treeItemObject instanceof Vacation) {
      vacation = (Vacation) treeItemObject;
    }

    LOGGER.info("<= vacation=" + (vacation != null ? vacation.toString() : "(null)"));
    return vacation;
  }
  
  /**
   * For a given treeItem, check whether it is the vacations attribute of a Vacations object. If so, return the vacations list, else return null.
   * 
   * @param treeItem the <code>TreeItem</code> to check.
   * @return the vacations list, or null.
   */
  @SuppressWarnings("unchecked")
  private List<Vacation> getVacationsListForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      return null;
    }
    
    List<Vacation> vacations = null;
    EObjectTreeItemContent treeItemContent = treeItem.getValue();
    
    if ((treeItemContent.getEStructuralFeature() != null)  &&
        treeItemContent.getEStructuralFeature().equals(VACATIONS_PACKAGE.getVacations_Vacations())) {
      vacations = (List<Vacation>) treeItemContent.getObject();
    }
    
    LOGGER.info("<= vacations=" + (vacations != null ? vacations.toString() : "(null)"));
    return vacations;
  }
  
  /**
   * For a given treeItem, check whether it is the vacations attribute of a Vacations object. If so, return the vacations list, else return null.
   * 
   * @param treeItem the <code>TreeItem</code> to check.
   * @return the vacations list, or null.
   */
  private Vacations getVacationsForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      return null;
    }
    
    Vacations vacations = null;
    EObjectTreeItemContent treeItemContent = treeItem.getValue();
    Object object = treeItemContent.getObject();
    
    if (object instanceof Vacations) {
      vacations = (Vacations) object;
    }
        
    LOGGER.info("<= vacations=" + (vacations != null ? vacations.toString() : "(null)"));
    return vacations;
  }
  
  /**
   * Add the 'stayed at' locations of a list of <code>Vacation</code>s to the vacations layer.
   * 
   * @param vacations the list of <code>Vacation</code>s of which the 'stayed at' locations are to be added to the vacation layer.
   */
  private void addVacationsToVacationsLayer(List<Vacation> vacations) {
    for (Vacation vacation: vacations) {
      addVacationToVacationsLayer(vacation, true);
    }
  }
  
  /**
   * Add the home location, if available, to the vacations layer.
   * 
   * @return a <code>MapPoint</code> with the coordinates of the home location, or null if there is no home location.
   */
  private void addHomeLocationToVacationsLayer() {
    LOGGER.info("=>");
        
    if ((vacations != null)  &&  vacations.isSetHome()) {
      Location homeLocation = vacations.getHome();
      if (homeLocation.isSetLatitude()  &&  homeLocation.isSetLongitude()) {
        mapRelatedItemsLayer.addLocation(homeLocation, "Home");
      }
    }
        
    LOGGER.info("<=");
  }
  
  /**
   * Add all relevant information of a vacation to the map view.
   * 
   * @param vacation the <code>Vacation</code> of which the <code>Location</code>s are to be added.
   * @param stayedAtOnly if <code>true</code> only the 'stayed at' locations are added.
   * @return a {@code WGS84BoundingBox} around all items added to the map view.
   */
  private WGS84BoundingBox addVacationToMapView(Vacation vacation, boolean stayedAtOnly) {
    WGS84BoundingBox boundingBox = addVacationToVacationsLayer(vacation, false);
//    WGS84BoundingBox tracksBoundingBox = addVacationToTracksLayer(vacation);
//    boundingBox = WGS84BoundingBox.extend(boundingBox, tracksBoundingBox);
    if (vacationTreeEditableMenuItem.isSelected()  &&  (boundingBox != null)) {
      mapRelatedItemsLayer.addBoundingBox(boundingBox);
    }

    return boundingBox;
  }

  /**
   * Add the <code>Location</code>s of a <code>Vacation</code> to the vacations layer.
   * 
   * @param vacation the <code>Vacation</code> of which the <code>Location</code>s are to be added.
   * @param stayedAtOnly if <code>true</code> only the 'stayed at' locations are added.
   */
  private WGS84BoundingBox addVacationToVacationsLayer(Vacation vacation, boolean stayedAtOnly) {
    LOGGER.info("=> vacation=" + vacation.toString());
    WGS84BoundingBox totalWGS84BoundingBox = null;
        
    LOGGER.info("Going to handle new vacation elements");
    for (VacationElement vacationElement: vacation.getElements()) {
      WGS84BoundingBox wgs84BoundingBox = handleVacationElementForMapDisplay(vacationElement, stayedAtOnly, null);
      totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
    }
        
    List<WGS84Coordinates> lineNodes = VacationsUtils.getGeoLocations(vacation);
    if (lineNodes.size() > 1) {
      mapRelatedItemsLayer.addLocationsVisitedPolyLine(FXCollections.observableList(lineNodes));
    }
    
    LOGGER.info("<=");
    return totalWGS84BoundingBox;
  }
  
  /**
   * Handle a single <code>VacationElement</code> for possibly adding it to the vacations layer.
   * <p>
   * Only elements of type <code>Location</code> are added.
   * 
   * @param vacationElement the element to be added.
   * @param stayedAtOnly if true, only 'stayed at' locations are added.
   */
  private WGS84BoundingBox handleVacationElementForMapDisplay(VacationElement vacationElement, boolean stayedAtOnly, WGS84BoundingBox totalWGS84BoundingBox) {
    LOGGER.info("=> totalWGS84BoundingBox: " + (totalWGS84BoundingBox != null ? totalWGS84BoundingBox.toString() : "(null)"));
    
    if (vacationElement instanceof Location location) {
      if (stayedAtOnly && (!location.isSetLabel() ||  location.getLabel() != ActivityLabel.VERBLIJF)) {
        return totalWGS84BoundingBox;
      }
      
      LOGGER.info("Going to add location to the map: location=" + location.toString());
      String text = location.getName();
      if (text == null) {
        text = location.getCity();
      }
      WGS84BoundingBox wgs84BoundingBox = mapRelatedItemsLayer.addLocation(location, text);
      totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
    } else if (vacationElement instanceof Picture picture) {
      if (stayedAtOnly) {
        return totalWGS84BoundingBox;
      }
      
      FileReference fileReference = picture.getPictureReference();
      if (fileReference != null) {
        String fileName = fileReference.getFile();
        if ((fileName != null)  &&  !fileName.isEmpty()) {
          WGS84Coordinates coordinates = getPictureCoordinates(picture);
          if (coordinates != null) {
            String text = fileReference.getTitle();
            if ((text == null)  ||  text.isEmpty()) {
              text = fileName;
            }
            WGS84BoundingBox wgs84BoundingBox = mapRelatedItemsLayer.addPhoto(coordinates, text, fileName);
            totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
          }
        }
      }
      
    } else if (vacationElement instanceof GPXTrack track) {
      LOGGER.info("Going to add GPX track to the map: track=" + track.toString());
      
      FileReference bestandReferentie = track.getTrackReference();
      if ((bestandReferentie != null)  &&  (bestandReferentie.getFile() != null)  &&  !bestandReferentie.getFile().isEmpty()) {
        EMFResource<DocumentRoot> gpxResource = new EMFResource<>(GPXPackage.eINSTANCE, () -> GPXFactory.eINSTANCE.createDocumentRoot(), false);
        gpxResource.addResourceFactoryForFileExtension("gpx", new GPXResourceFactoryImpl());
        try {
          gpxResource.load(bestandReferentie.getFile());
          DocumentRoot documentRoot = gpxResource.getEObject();
          GpxType gpxType = documentRoot.getGpx();
          WGS84BoundingBox wgs84BoundingBox = trackLayer.addGpx(bestandReferentie.getTitle(), bestandReferentie.getFile(), gpxType);
          totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
    
    for (VacationElement childElement: vacationElement.getChildren()) {
      totalWGS84BoundingBox = handleVacationElementForMapDisplay(childElement, stayedAtOnly, totalWGS84BoundingBox);
    }
    
    LOGGER.info("<= totalWGS84BoundingBox: " + (totalWGS84BoundingBox != null ? totalWGS84BoundingBox.toString() : "(null)"));
    return totalWGS84BoundingBox;
  }

  /**
   * Create the EObjectTreeDescriptor for the Vacations tree view.
   * 
   * @return the EObjectTreeDescriptor for the Vacations tree view
   */
  private EObjectTreeDescriptor createEObjectTreeDescriptor() {
    EmfPackageHelper vakantiesPackageHelper = new EmfPackageHelper(VACATIONS_PACKAGE);
    EmfPackageHelper poiPackageHelper = new EmfPackageHelper(POI_PACKAGE);
    EObjectTreeDescriptor eObjectTreeDescriptor = new EObjectTreeDescriptor();
    
    createAndAddEObjectTreeDescriptorForVacations(eObjectTreeDescriptor, vakantiesPackageHelper);
    createAndAddEObjectTreeDescriptorForBoundingBox(eObjectTreeDescriptor, vakantiesPackageHelper);
    createAndAddEObjectTreeDescriptorForLocation(eObjectTreeDescriptor, vakantiesPackageHelper);
    createAndAddEObjectTreeDescriptorForVacation(eObjectTreeDescriptor, vakantiesPackageHelper);
    createAndAddEObjectTreeDescriptorForVacationElement(eObjectTreeDescriptor, vakantiesPackageHelper);
    createAndAddEObjectTreeDescriptorForFileReference(eObjectTreeDescriptor);
    createAndAddEObjectTreeDescriptorForDay(eObjectTreeDescriptor, vakantiesPackageHelper);
    createAndAddEObjectTreeDescriptorForText(eObjectTreeDescriptor, vakantiesPackageHelper);
    createAndAddEObjectTreeDescriptorForPicture(eObjectTreeDescriptor, vakantiesPackageHelper);
    createAndAddEObjectTreeDescriptorForGPXTrack(eObjectTreeDescriptor, vakantiesPackageHelper);
    createAndAddEObjectTreeDescriptorForMapImage(eObjectTreeDescriptor, vakantiesPackageHelper);
    
    creaeAndAddEEnumEditorDescriptorForLocationType(eObjectTreeDescriptor, poiPackageHelper);
  
    return eObjectTreeDescriptor;
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Vacations.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacations descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForVacations(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Vacations");
        
    // Vacations = "Vakanties informatie" (root node)
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Vakantie informatie", true, null);

    // Vacations.tips
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getVacations_Tips(), "Tips", PresentationType.MULTI_LINE_TEXT, null));

    // Vacations.home
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Thuis locatie creëren"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Thuis locatie verwijderen"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getVacations_Home(), vakantiesPackageHelper.getEClass("goedegep.vacations.model.Location"), (eObject) -> "Thuis locatie", false, nodeOperationDescriptors));
    
    // Vacations.vacations
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuwe vakantie"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacations_Vacations(), "Vakanties", true, nodeOperationDescriptors));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  /**
   * Create the descriptor for the EClass goedegep.model.vacations.BoundingBox.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the BoundingBox descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForBoundingBox(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.BoundingBox");
    
    // BoundingBox
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "Nieuw element hiervoor ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "Nieuw element hierna ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Element omhoog verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Element omlaag verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Element verwijderen"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
          StringBuilder buf = new StringBuilder();
          BoundingBox boundingBox = (BoundingBox) eObject;
          if (boundingBox.isSetWest()) {
            buf.append(boundingBox.getWest());
          } else {
            buf.append("..");
          }
          buf.append(", ");
          if (boundingBox.isSetNorth()) {
            buf.append(boundingBox.getNorth());
          } else {
            buf.append("..");
          }
          buf.append(", ");
          if (boundingBox.isSetEast()) {
            buf.append(boundingBox.getEast());
          } else {
            buf.append("..");
          }
          buf.append(", ");
          if (boundingBox.isSetSouth()) {
            buf.append(boundingBox.getSouth());
          } else {
            buf.append("..");
          }
          return buf.toString();
        }, false, nodeOperationDescriptors);
    
    // BoundingBox.west
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getBoundingBox_West(), "West", null));
    // BoundingBox.north
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getBoundingBox_North(), "North", null));
    // BoundingBox.east
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getBoundingBox_East(), "East", null));
    // BoundingBox.south
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getBoundingBox_South(), "South", null));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Location.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Location descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForLocation(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Location");
    
    // VacationElementLocation
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "Nieuw element hiervoor ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "Nieuw element hierna ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Element omhoog verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Element omlaag verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Element verwijderen"));
    nodeOperationDescriptors.add(new ExtendedNodeOperationDescriptor("Reduce boundaries sizes", (Predicate<EObjectTreeItem>) this::isMenuToBeEnabled, (Consumer<EObjectTreeItem>) this::reduceBoundariesSizes));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
          StringBuilder buf = new StringBuilder();
          boolean spaceNeeded = false;
          Location location = (Location) eObject;
          if (location.isSetLocationType()) {
//            buf.append(location.getLocationType()).append(":");
            spaceNeeded = true;
          }
          if (location.isSetName()) {
            if (spaceNeeded) {
              buf.append(" ");
            }
            buf.append(location.getName());
          } else if (location.isSetCity()) {
            if (spaceNeeded) {
              buf.append(" ");
            }
            buf.append(location.getCity());
          }
          
          if (buf.length() == 0) {
            buf.append("Locatie");
          }
          return buf.toString();
        }, false, nodeOperationDescriptors,
        object -> {
          Location location = (Location) object;
          POICategoryId poiCategoryId = location.getLocationType();
          return poiIcons.getIcon(poiCategoryId);
        });
    
    // Location.label
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Label(), "Label", null));
    // VacationElement.startDate
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_StartDate(), "Van", FDF, null));
    // VacationElement.endDate
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_EndDate(), "Tot", FDF, null));
    // Location.duration
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Duration(), "Verblijfsduur", null));
    // Location.description
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Description(), "Omschrijving", PresentationType.MULTI_LINE_TEXT, null));

    // Location.name
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Name(), "Naam", null));
    // Location.locationType
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_LocationType(), "Locatie type", null));
    // Location.country
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Country(), "Land", null));
    // Location.city
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_City(), "Plaats", null));
    // Location.street
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Street(), "Straat", null));
    // Location.houseNumber
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_HouseNumber(), "Huisnummer", null));
    // Location.webSite
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.OPEN, "Document openen"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_WebSite(), "Website", nodeOperationDescriptors));
    // Location.latitude
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Latitude(), "Latitude", null));
    // Location.longitude
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Longitude(), "Longitude", null));
    // Location.boundingBox
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Bounding box creëren"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Bounding Box verwijderen"));
    nodeOperationDescriptors.add(new ExtendedNodeOperationDescriptor("Obtain bounding box", null, new BoundingBoxObtainer()));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getLocation_Boundingbox(), vakantiesPackageHelper.getEClass("goedegep.vacations.model.BoundingBox"), (eObject) -> "Bounding box", true, nodeOperationDescriptors));

    // VacationElement.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuw element"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elementen", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Vacation.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the Vacation descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForVacation(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Vacation");
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
        
    // Vacation
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuwe ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "Nieuwe vakantie hiervoor"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "Nieuwe vakantie hierna"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Vakantie omhoog verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Vakantie omlaag verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Vakantie verwijderen"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
            Vacation vacation = (Vacation) eObject;
            return vacation.getId();
          }, false, nodeOperationDescriptors,
        eObject -> {
            return customization.getResources().getApplicationImage(ImageSize.SIZE_0);
        });
    
    // Vacation.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getVacation_Title(), "Titel", null));
    // Vacation.date (startDate)
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(typesPackage.getEvent_Date(), "Van", FDF, null));
    // Vacation.endDate
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getVacation_EndDate(), "Tot", FDF, null));
    // Vacation.notes
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(typesPackage.getEvent_Notes(), "Algemeen", PresentationType.MULTI_LINE_TEXT, null));
    
    // Vacation.documents
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuw document"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacation_Documents(), "Documenten", false, nodeOperationDescriptors));
    
    // Vacation.pictures
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.OPEN, "Map met foto's openen"));
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getVacation_Pictures(), "Foto's", PresentationType.FOLDER, nodeOperationDescriptors);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Vacation.elements
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuw element"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacation_Elements(), "Elementen", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  /**
   * Create the descriptor for the EClass goedegep.types.model.FileReference.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the FileReference descriptor is to be added.
   */
  private void createAndAddEObjectTreeDescriptorForFileReference(EObjectTreeDescriptor eObjectTreeDescriptor) {
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
    EmfPackageHelper typesPackageHelper = new EmfPackageHelper(typesPackage);
    EClass eClass = typesPackageHelper.getEClass("goedegep.types.model.FileReference");

    // FileReference
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "Nieuw document hiervoor"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "Nieuw document hierna"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Document omhoog verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Document omlaag verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Document verwijderen"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
          eObject -> {
            FileReference bestandReferentie = (FileReference) eObject;
            return bestandReferentie.getTitle();
          },
          false, nodeOperationDescriptors);
    
    // FileReference.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(typesPackage.getFileReference_Title(), "Titel", null));
    // FileReference.file
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.OPEN, "Document openen"));
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(typesPackage.getFileReference_File(), "Bestand", PresentationType.FILE, nodeOperationDescriptors);
    eObjectTreeItemAttributeDescriptor.setInitialDirectoryNameFunction(VacationsWindow::getVacationFolder);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);
        
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  private static String getVacationFolder(EObjectTreeCell eObjectTreeCell) {
    Vacation vacation = getVacationForTreeItem(eObjectTreeCell.getTreeItem());
    
    if (vacation == null) {
      return null;
    }
    
    EObjectTreeItem parentTreeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem().getParent();
    if (parentTreeItem != null) {
      EObjectTreeItem grandparentTreeItem = (EObjectTreeItem) parentTreeItem.getParent();
      if (grandparentTreeItem != null) {
        Object object = grandparentTreeItem.getValue().getObject();
        LOGGER.info("Class=" + object.getClass().getName());
        if (object instanceof Picture) {
          return VacationsUtils.vacationPicturesFolder(vacation);
        } else {
          return VacationsUtils.getVacationFolder(vacation);      
        }
      }
    }
    
    return null;
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.VacationElement.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElement descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForVacationElement(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.VacationElement");
        
    // VacationElement
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuw element ..."));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
            return "Vacation element";
          }, false, nodeOperationDescriptors);
        
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.VacationElementDay.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElementDay descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForDay(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Day");
        
    // Day (extends VacationElement)
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "Nieuw element hiervoor ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "Nieuw element hierna ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Element omhoog verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Element omlaag verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Element verwijderen"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
            Day day = (Day) eObject;
            StringBuilder buf = new StringBuilder();
            buf.append("Day: ");
            Integer dayNr = day.getDayNr();
            if (dayNr != null) {
              buf.append(dayNr.toString());
            }
            Date date = day.getDate();
            if (date != null) {
              buf.append(" - ");
              buf.append(DF.format(date));
            }
            if (day.isSetTitle()) {
              buf.append(" - ");
              buf.append(day.getTitle());
            }
            return buf.toString();
          }, false, nodeOperationDescriptors, object -> appResources.getDayIcon());
    
    // Day.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getDay_Title(), "Titel", null));
    
    // Day.days
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getDay_Days(), "Dagen", null));

    // Day.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuw element"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elementen", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.VacationElementText.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElementText descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForText(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Text");
        
    // VacationElementText (extends VacationElement)
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "Nieuw element hiervoor ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "Nieuw element hierna ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Element omhoog verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Element omlaag verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Element verwijderen"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
            Text text = (Text) eObject;
            if (text.isSetText()) {
              int maxTextLength = 80;
              String displayText = text.getText();
              if (displayText.length() > maxTextLength) {
                displayText = displayText.substring(0, maxTextLength - 4) + "...";
              }
              return displayText;
            } else {
              return "Text";
            }
          }, false, nodeOperationDescriptors, object -> appResources.getTextIcon());
    
    // VacationElementText.text
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getText_Text(), "Tekst", PresentationType.MULTI_LINE_TEXT, null));

    // VacationElementText.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuw element"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elementen", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.Picture.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElementPicture descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForPicture(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.Picture");
        
    // VacationElementPicture (extends VacationElement)
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "Nieuw element hiervoor ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "Nieuw element hierna ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Element omhoog verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Element omlaag verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Element verwijderen"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
            Picture picture = (Picture) eObject;
            if (picture.isSetPictureReference()) {
              FileReference bestandReferentie = picture.getPictureReference();
              String text = bestandReferentie.getTitle();
              if (text == null  ||  text.isEmpty()) {
                text = bestandReferentie.getFile();
              }
              if (text == null  ||  text.isEmpty()) {
                text = "...";
              }
              return text;
            } else {
              return "Picture";
            }
          }, false, nodeOperationDescriptors);
    
    // VacationElementPicture.pictureReference
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Foto referentie creëren"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Foto referentie verwijderen"));
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
    EmfPackageHelper typesPackageHelper = new EmfPackageHelper(typesPackage);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getPicture_PictureReference(), typesPackageHelper.getEClass("goedegep.types.model.FileReference"), (eObject) -> "Foto referentie", true, nodeOperationDescriptors));
    
    // VacationElementText.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuw element"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elementen", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.VacationElementGPX.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the VacationElementGPX descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForGPXTrack(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.GPXTrack");
        
    // VacationElementGPX (extends VacationElement)
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "Nieuw element hiervoor ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "Nieuw element hierna ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Element omhoog verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Element omlaag verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Element verwijderen"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
          GPXTrack gpxTrack = (GPXTrack) eObject;
          if (gpxTrack.isSetTrackReference()) {
            FileReference bestandReferentie = gpxTrack.getTrackReference();
            String text = bestandReferentie.getTitle();
            if (text == null  ||  text.isEmpty()) {
              text = bestandReferentie.getFile();
            }
            if (text == null  ||  text.isEmpty()) {
              text = "...";
            }
            return text;
          } else {
            return "GPX track";
          }
        }, false, nodeOperationDescriptors);
    
    // VacationElementGPX.trackReference
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "GPX track referentie creeeren"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "GPX track referentie verwijderen"));
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
    EmfPackageHelper typesPackageHelper = new EmfPackageHelper(typesPackage);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getGPXTrack_TrackReference(), typesPackageHelper.getEClass("goedegep.types.model.FileReference"), (eObject) -> "GPX track referentie", true, nodeOperationDescriptors));
    
    // VacationElementGPX.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuw element"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elementen", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.MapImage.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the MapImage descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForMapImage(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.MapImage");
        
    // MapImage (extends VacationElement)
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "Nieuw element hiervoor ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "Nieuw element hierna ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Element omhoog verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Element omlaag verplaatsen"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Element verwijderen"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
            MapImage picture = (MapImage) eObject;
            if (picture.getImageReference() != null) {
              FileReference bestandReferentie = picture.getImageReference();
              String text = bestandReferentie.getTitle();
              if (text == null  ||  text.isEmpty()) {
                text = bestandReferentie.getFile();
              }
              if (text == null  ||  text.isEmpty()) {
                text = "...";
              }
              return text;
            } else {
              return "MapImage";
            }
          }, false, nodeOperationDescriptors, object -> appResources.getMapIcon());
    
    // MapImage.imageReference
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "MapImage referentie creëren"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "MapImage referentie verwijderen"));
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
    EmfPackageHelper typesPackageHelper = new EmfPackageHelper(typesPackage);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getMapImage_ImageReference(), typesPackageHelper.getEClass("goedegep.types.model.FileReference"), (eObject) -> "MapImage referentie", true, nodeOperationDescriptors));
    
    // MapImage.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuw element"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elementen", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  private void creaeAndAddEEnumEditorDescriptorForLocationType(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper poiPackageHelper) {
    EEnum eEnum = poiPackageHelper.getEEnum("goedegep.poi.model.POICategoryId");
    
    EEnumEditorDescriptor<POICategoryId> eEnumEditorDescriptor = new EEnumEditorDescriptor<>(true);
        
    for (POICategoryId poiCategoryId: POICategoryId.values()) {
      eEnumEditorDescriptor.addDisplayNameForEEnum(poiCategoryId, poiCategoryId.getLiteral());
    }
    
    eObjectTreeDescriptor.addEEnumEditorDescriptor(eEnum, eEnumEditorDescriptor);
  }

  /**
   * Save the vacations information to the related file.
   */
  private void saveVacations() {
    if (vacationsResource != null) {
      try {
        vacationsResource.save();
        statusLabel.setText("Vacations saved to '" + vacationsResource.getFileName() + "'");
      } catch (IOException e) {        
        componentFactory.createErrorDialog(
            "Saving the vacations has failed.",
            "System error message: "  + e.getMessage()
            ).showAndWait();
      }
    }
  }
  
  /**
   * print the document representation of the currently selected vacation.
   */
  private void printVacation() {
    try {
      EObjectTreeItem treeItem = treeView.getSelectedObject();
      Vacation vacation = getVacationForTreeItem(treeItem);
      if (vacation == null) {
        statusLabel.setText("No vacation selected");
        return;
      }
      new PrintWindow(customization, vacation);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void exportVacationToHtml() {
    EObjectTreeItem treeItem = treeView.getSelectedObject();
    Vacation vacation = getVacationForTreeItem(treeItem);
    if (vacation == null) {
      statusLabel.setText("No vacation selected");
      return;
    }

    String vacationHtmlDocument = vacationToHtmlConverter.vacationToHtml(vacation);
    String vacationsFolder = VacationsUtils.getVacationFolder(vacation);
    if (vacationsFolder == null) {
      statusLabel.setText("No vacation folder found");
      return;
    }
    File file = new File(vacationsFolder, vacation.getTitle() + ".html");
    
    try {
      PrintWriter out = new PrintWriter(file);
      out.println(vacationHtmlDocument);
      out.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Print a <code>Node</code>.
   * 
   * @param node
   */
  private void print(Node node) {
    statusLabel.setText("Creating a printer job...");

    PrinterJob printerJob = PrinterJob.createPrinterJob();

    if (printerJob != null) {
      printerJob.jobStatusProperty().addListener(new ChangeListener<JobStatus>() {

        @Override
        public void changed(ObservableValue<? extends JobStatus> observable, JobStatus oldValue, JobStatus newValue) {
          statusLabel.setText(newValue.toString());
        }
        
      });
      
      // Print the node
      Scale scale = new Scale(0.5, 0.5);
      node.getTransforms().add(scale);
      boolean printed = printerJob.printPage(node);
      node.getTransforms().remove(scale);

      if (printed) {
        // End the printer job
        printerJob.endJob();
      } else {
        // Write Error Message
        statusLabel.setText("Printing failed.");
      }
    } else {
      // Write Error Message
      statusLabel.setText("Could not create a printer job.");
    }
  }
  
  /**
   * Import photos to the vacation related to the currently selected tree item.
   */
  private void importPhotos() {
//    SpatialContextFactory spatialContextFactory = new SpatialContextFactory();
//    SpatialContext spatialContext = spatialContextFactory.newSpatialContext();
//    ShapeFactory shapeFactory = spatialContext.getShapeFactory();
    
    EObjectTreeItem treeItem = treeView.getSelectedObject();
    Vacation vacation = getVacationForTreeItem(treeItem);  

    // Create a set of all locations of this vacation.
    List<VacationElement> vacationLocations = getElementsWithBoundingBox(vacation);
    for (VacationElement vacationElement: vacationLocations) {
      if (vacationElement instanceof Location) {
        Location location = (Location) vacationElement;
        LOGGER.severe("VacationElement:Location: " + location.getName());
      } else if (vacationElement instanceof GPXTrack) {
        GPXTrack gpxTrack = (GPXTrack) vacationElement;
        LOGGER.severe("VacationElement:GPXTrack: " + gpxTrack.getTrackReference().getFile());
      }
    }
        
    List<Path> vacationPhotoFolderPaths = getVactionPhotosSubFoldersPaths(vacation);
          
    for (Path photosPath: vacationPhotoFolderPaths)  {
      LOGGER.severe("Photo folder: " + photosPath.toString());
      
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(photosPath)) {

        for (Path checkFile: stream) {
          if (!Files.isDirectory(checkFile)) {
            String fileExtension = FileUtils.getFileExtension(checkFile);
            LOGGER.severe("checking: " + checkFile);
            if (fileExtension.equals(".jpg")) {
              File file = new File(checkFile.toAbsolutePath().toString());
              try {
                PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(file);
                WGS84Coordinates coordinates = photoFileMetaDataHandler.getGeoLocation();
                if (coordinates != null) {
                  LOGGER.info("Coordinates: " + coordinates.getLatitude() + ", " + coordinates.getLongitude());
                  for (VacationElement vacationElement: vacationLocations) {
                    S2LatLng lo = null;
                    S2LatLng hi = null;
                    String name = null;
                    if (vacationElement instanceof Location) {
                      Location location = (Location) vacationElement;
                      LOGGER.severe("VacationElement:Location: " + location.getName());
                      name = location.getName();
                      if (location.isSetBoundingbox()) {
                        BoundingBox boundingBox = location.getBoundingbox();
                        if (boundingBox.isValid()) {
                          LOGGER.severe("using bounding box");
                          lo = S2LatLng.fromDegrees(boundingBox.getSouth(), boundingBox.getWest());
                          hi = S2LatLng.fromDegrees(boundingBox.getNorth(), boundingBox.getEast());
                        }
                      }
                      
                      if (lo == null) {
                        if (location.isSetLatitude()  &&  location.isSetLongitude()) {
                          lo = S2LatLng.fromDegrees(location.getLatitude() - 0.0003, location.getLongitude() - 0.0003);
                          hi = S2LatLng.fromDegrees(location.getLatitude() + 0.0003, location.getLongitude() + 0.0003);
                        }
                      }
                    } else if (vacationElement instanceof GPXTrack) {
                      GPXTrack gpxTrack = (GPXTrack) vacationElement;
                      LOGGER.severe("VacationElement:GPXTrack: " + gpxTrack.getTrackReference().getFile());
                      name = gpxTrack.getTrackReference().getFile();
                      
                      EMFResource<DocumentRoot> gpxResource = new EMFResource<>(GPXPackage.eINSTANCE, () -> GPXFactory.eINSTANCE.createDocumentRoot(), false);
                      gpxResource.addResourceFactoryForFileExtension("gpx", new GPXResourceFactoryImpl());
                      try {
                        gpxResource.load(name);
                        DocumentRoot documentRoot = gpxResource.getEObject();
                        GpxType gpxType = documentRoot.getGpx();
                        WGS84BoundingBox boundingBox = GpxUtil.calculateBoundingBox(gpxType);
                        lo = S2LatLng.fromDegrees(boundingBox.getSouth(), boundingBox.getWest());
                        hi = S2LatLng.fromDegrees(boundingBox.getNorth(), boundingBox.getEast());
                      } catch (FileNotFoundException e) {
                        e.printStackTrace();
                      }
                      
//                      File gpxTrackFile = new File(gpxTrack.getTrackReference().getFile());
//                      GPXFile gpxFile = new GPXFile(gpxTrackFile);
//                      GPX gpx = gpxFile.getGPX();
//                      WGS84BoundingBox boundingBox = gpx.getBoundingBox();
//                      lo = S2LatLng.fromDegrees(boundingBox.getSouth(), boundingBox.getWest());
//                      hi = S2LatLng.fromDegrees(boundingBox.getNorth(), boundingBox.getEast());
                    }
                    
                    S2LatLngRect locationBoundingBox = new S2LatLngRect(lo, hi);
                    LOGGER.severe("locationBoundingBox: " + locationBoundingBox.toString());
                    S2LatLng photoLatLng = S2LatLng.fromDegrees(coordinates.getLatitude(), coordinates.getLongitude());
                    LOGGER.severe("photoLatLng: " + photoLatLng.toString());
                    S1Angle distanceAngle = locationBoundingBox.getDistance(photoLatLng);
                    double degrees = distanceAngle.degrees();
                    double distanceInKm = DistanceUtils.degrees2Dist(degrees, DistanceUtils.EARTH_EQUATORIAL_RADIUS_KM);
                    LOGGER.severe("Calculated distance: " + distanceInKm);
                    
//                    Point photoPoint = shapeFactory.pointLatLon(coordinates.getLatitude(), coordinates.getLongitude());
//                    Point locationPoint = shapeFactory.pointLatLon(location.getLatitude(), location.getLongitude());
//                    double distance = spatialContext.calcDistance(photoPoint, locationPoint);
//                    double distanceInKm2 = DistanceUtils.degrees2Dist(distance, DistanceUtils.EARTH_EQUATORIAL_RADIUS_KM);
//                    LOGGER.severe("Calculated distance: " + distanceInKm2);
                   
                    if (distanceInKm < 0.100) {
                      LOGGER.severe("Found a match");
                      Picture vacationElementPicture = VACATIONS_FACTORY.createPicture();
                      FileReference pictureReference = TYPES_FACTORY.createFileReference();
                      pictureReference.setFile(file.getAbsolutePath());
                      pictureReference.setTitle(name);
                      vacationElementPicture.setPictureReference(pictureReference);
                      vacationElement.getChildren().add(vacationElementPicture);
                      break;
                    } else {
                      LOGGER.severe("No match");
                    }
                  }
                } else {
                  LOGGER.severe("No Coordinates");
                }
              } catch (ImageReadException | IOException e) {
                e.printStackTrace();
              }
            }
          }
        }
      } catch (IOException | DirectoryIteratorException x) {
        LOGGER.severe("Problem in finding photos folder: " + x.getMessage());
      }
    }
  }
  
  /**
   * Get all photo folders for a vacation.
   * @return
   */
  private static List<Path> getVactionPhotosSubFoldersPaths(Vacation vacation) {
    List<Path> vacationPhotoFolderPaths = new ArrayList<>();
    
    Path vacationPhotosFolderPath = getVacationPhotosFolderPath(vacation);

    if (vacationPhotosFolderPath == null) {
      return vacationPhotoFolderPaths;
    }
    
    try {
      Files.walkFileTree(vacationPhotosFolderPath, new FileVisitor<Path>() {
        boolean containsPhoto = false;

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
          LOGGER.severe("preVisitDirectory");
          containsPhoto = false;
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          LOGGER.severe("visitFile: " + file.toString());
          if (!containsPhoto) {
            if (FileUtils.isPictureFile(file.getFileName().toString())) {
              containsPhoto = true;
            }
          }
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
          exc.printStackTrace();
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
          if (containsPhoto) {
            LOGGER.severe("postVisitDirectory: adding " + dir.toString());
            vacationPhotoFolderPaths.add(dir);
          }
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    for (Path path: vacationPhotoFolderPaths) {
      LOGGER.severe("path: " + path.toString());
    }
    
    return vacationPhotoFolderPaths;
  }
  
  /**
   * Get a Path for the folder with photos for a specific vacation.
   * <p>
   * This folder is expected to be a folder with the name equal to the Id of the vacation, and being a sub folder of the
   * folder with all photos of all vacations.
   * 
   * @param vacation the vacation for which to get a Path to its photos folder.
   * @return a Path to the folder with photos for <code>vacation</code>, or null if this cannot be determined.
   */
  private static Path getVacationPhotosFolderPath(Vacation vacation) {
    Path vacationsPhotosFolderPath = getVacationsPhotosFolderPath();
    
    if (vacationsPhotosFolderPath == null) {
      return null;
    }
    
    String vacationId = vacation.getId();
    
    Path vacationPhotosFolderPath = vacationsPhotosFolderPath.resolve(vacationId);
    if (Files.exists(vacationPhotosFolderPath)  &&  Files.isDirectory(vacationPhotosFolderPath)) {
      LOGGER.severe("<= " + vacationPhotosFolderPath);
      return vacationPhotosFolderPath;
    } else {
      LOGGER.severe("<= " + null);
      return null;
    }
  }
  
  /**
   * Get a Path for the folder with photos for all vacations.
   * <p>
   * The name of this folder is specified by VacationsRegistry.vacationPicturesFolderName.
   * 
   * @return a Path to the folder with photos for all vacations, or null if this cannot be determined.
   */
  private static Path getVacationsPhotosFolderPath() {
    String vacationsPhotosFolderName = VacationsRegistry.vacationPicturesFolderName;
    Path vacationsPhotosFolderPath = Paths.get(vacationsPhotosFolderName);
    if (Files.exists(vacationsPhotosFolderPath)  &&  Files.isDirectory(vacationsPhotosFolderPath)) {
      LOGGER.severe("<= " + vacationsPhotosFolderPath);
      return vacationsPhotosFolderPath;
    } else {
      LOGGER.severe("<= " + null);
      return null;
    }
  }
  
  /**
   * Get a list of all children of a vacation, which may have a bounding box (location?).
   * <p>
   * Currently these are all Locations and all GPXTracks, with the file reference set.
   * 
   * @param vacation
   * @return
   */
  private List<VacationElement> getElementsWithBoundingBox(Vacation vacation) {
    List<VacationElement> locations = new ArrayList<>();
    
    TreeIterator<EObject> vacationIterator = vacation.eAllContents();
    
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      
      if (eObject instanceof Location ) {
        locations.add((VacationElement) eObject);
      } else if (eObject instanceof GPXTrack) {
        GPXTrack gpxTrack = (GPXTrack) eObject;
        FileReference fileReference = gpxTrack.getTrackReference();
        if (fileReference != null) {
          locations.add(gpxTrack);
        }
      }
    }
    
    return locations;
  }
  
  private void importVacations() {
    VacationsImporter vacationsImporter = new VacationsImporter(customization, vacations);
    vacationsImporter.importVacations(VacationsRegistry.vacationsFolderName, VacationsRegistry.vacationPicturesFolderName);
    treeView.setEObject(vacations);
  }
  
  /**
   * Generate a KML file from the vacations.
   */
  private void createVacationsKml() {
    // Let user select a file to save to.
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Kml file");
    FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("KML files (*.kml)", "*.kml");
    fileChooser.getExtensionFilters().add(extensionFilter);
    fileChooser.setSelectedExtensionFilter(extensionFilter);
    File file = fileChooser.showSaveDialog(this);
    LOGGER.severe("Creating kml file: " + file.getAbsolutePath());
    
    // Generate the file using the VacationsKmlConverter
    VacationsKmlConverter vacationsKmlConverter = new VacationsKmlConverter(poiIcons);
    try {
      vacationsKmlConverter.createKmlForVacations(vacations, file);
      statusLabel.setText("KML file " + file.getAbsolutePath() + " created");
    } catch (FileNotFoundException e) {
      statusLabel.setText("Failed to write to file: " + file.getAbsolutePath());
    }
  }
  
  /**
   * Import the locations from a KML or KMZ file.
   * <p>
   * The locations are created as children of the currently selected tree item.
   */
  private void importLocationsFromKmlFile() {
    statusLabel.setText(null);

    // check that currently selected tree item is of type Elements
    EObjectTreeItem currentlySelectedItem = treeView.getSelectedObject();
    if (!treeView.treeItemIsLocationsList(currentlySelectedItem)) {
      statusLabel.setText("Please select a node in the tree where locations can be added.");
      return;
    }
    
    // Let the user select a file to read from
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Read Kml file");
    FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("KML files", "*.kml", "*.kmz");
    fileChooser.getExtensionFilters().add(extensionFilter);
    fileChooser.setSelectedExtensionFilter(extensionFilter);
    File file = fileChooser.showOpenDialog(this);
    LOGGER.severe("Reading kml file: " + file.getAbsolutePath());
    
    // Use the VacationsKmlConverter to get the locations
    VacationsKmlConverter vacationsKmlConverter = new VacationsKmlConverter(poiIcons);
    List<Location> locations = vacationsKmlConverter.getLocationsFromKmlFile(file);
    
    // Add the locations to the currently selected tree item.
    EObjectTreeItem treeItem = (EObjectTreeItem) treeView.getSelectedObject();
    EObjectTreeItemContent eObjectTreeItemContent = treeItem.getValue();
    Object object = eObjectTreeItemContent.getObject();
    @SuppressWarnings("unchecked")
    List<VacationElement> elements = (List<VacationElement>) object;
    for (Location location: locations) {
      elements.add(location);
    }
    treeItem.rebuildChildren();
  }
  
  private void createOsmAndFavouritesFile() {
    // Choose file to save to.
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("OsmAnd Favourites file");
    fileChooser.setInitialFileName("favourites.gpx");
    File file = fileChooser.showSaveDialog(this);
    LOGGER.severe("Creating OsmAnd Favourites file: " + file.getAbsolutePath());
    
    // Get selected node in vacations
    TreeItem<EObjectTreeItemContent> treeItem = treeView.getSelectedObject();
    Object selectedObject = treeItem.getValue().getObject();
    if (selectedObject instanceof EObject) {
      EObject eObject = (EObject) selectedObject;
      
      // Generate file
      OsmAndUtil.createFavouritesFiles(eObject, file);
    }
  }
  
  private void createTomTomOv2File() {
    // Choose file to save to.
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("TomTom ov2 file");
    fileChooser.setInitialFileName("MyPlaces.ov2");
    File file = fileChooser.showSaveDialog(this);
    LOGGER.severe("Creating TomTom ov2 file: " + file.getAbsolutePath());
    
    // Get selected node in vacations
    TreeItem<EObjectTreeItemContent> treeItem = treeView.getSelectedObject();
    Object selectedObject = treeItem.getValue().getObject();
    if (selectedObject instanceof EObject) {
      EObject eObject = (EObject) selectedObject;
      
      
      // Generate file
      Ov2Util.createOv2File(eObject, file.getAbsolutePath());
    }
  }

  
  private void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(customization, VacationsRegistry.propertyDescriptorsResource);
  }
  
  private void showPropertiesEditor() {
    new PropertiesEditor("Vacation properties", customization, VacationsRegistry.propertyDescriptorsResource, VacationsRegistry.customPropertiesFile);
  }

  /**
   * Show a dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createInformationDialog(
        "About Vacations",
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null, 
        VacationsRegistry.shortProductInfo + NEWLINE +
        "Version: " + VacationsRegistry.version + NEWLINE +
        VacationsRegistry.copyrightMessage + NEWLINE +
        "Author: " + VacationsRegistry.author)
        .showAndWait();
  }
  
  private void showMapSnapshotPopup() {
    SnapshotParameters snapShotParameters = new SnapshotParameters();
    WritableImage writebleImage = new WritableImage(500, 500);
    mapView.snapshot(snapShotParameters, writebleImage);
    
    Stage stage = new Stage();
    ImageView imageView = new ImageView();
    imageView.setImage(writebleImage);
    HBox hBox = new HBox();
    hBox.getChildren().add(imageView);
    Scene scene = new Scene(hBox);
    stage.setScene(scene);
    stage.show();
  }
  
  private void useDemoVacationsFile() {
    vacations = null;
    
    try {
      vacations = vacationsResource.load("../../../goedegep.vacations.app/src/main/resources/goedegep/vacations/VacationsDemo.xmi");
    } catch (FileNotFoundException e) {
      LOGGER.severe("File not found: " + e.getMessage());
      Alert alert = componentFactory.createYesNoConfirmationDialog(
          null,
          null,
          translationFormatter.formatText("VacationsWindow.alertVacationsFileNotFound.header", "../../../goedegep.vacations.app/src/main/resources/goedegep/vacations/VacationsDemo.xmi"),
          TRANSLATIONS.getString("VacationsWindow.alertVacationsFileNotFound.content"));
      alert.showAndWait();
    }
    
    if (vacations != null) {
      treeView.setEObject(vacations);
      updateTitle();
    }
  }


  /**
   * Get an instance of the Nominatim API
   * 
   * @return an instance of the Nominatim API
   */
  private NominatimAPI getNominatimAPI() {
    if (nominatimAPI == null) {
      nominatimAPI = NominatimAPI.with(Locale.getDefault());
    }
    
    return nominatimAPI;
  }
  
  /**
   * If enable: Wait a bit, then move to the specified mapPoint in seconds time.
   * <p>
   * The fly to is only performed if the vacations are not in edit mode.
   *
   * @param waitTime the time to wait before we start moving
   * @param mapPoint the destination of the move
   * @param seconds the time the move should take
   */
  private void flyToIfEnabled(double waitTime, MapPoint mapPoint, double seconds) {
    
    if (!vacationTreeEditableMenuItem.isSelected()) {
      mapView.flyTo(waitTime, mapPoint, seconds);
    }
    
  }
  
  private void setZoomIfEnabled(Double zoomLevel) {
    if (!vacationTreeEditableMenuItem.isSelected()) {
      mapView.setZoom(zoomLevel);
    }
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
    
    buf.append(TRANSLATIONS.getString("VacationsWindow.windowTitle"));
    buf.append(" - ");
    if (vacationsResource.isDirty()) {
      buf.append("*");
    }
    String fileName = vacationsResource.getFileName();
    if (fileName.equals("")) {
      fileName = "<NoName>";
    }
    buf.append(fileName);
    
    setTitle(buf.toString());
  }
  
  public boolean isMenuToBeEnabled(EObjectTreeItem eObjectTreeItem) {
    return true;
  }
  
  public void reduceBoundariesSizes(EObjectTreeItem eObjectTreeItem) {
    LOGGER.info("=> " + eObjectTreeItem);
    
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    Object object = eObjectTreeItemContent.getObject();
    if (!(object instanceof Location)) {
      LOGGER.severe("EObjectTreeItem doesn't contain a Location");
      return;
    }
    
    Location location = (Location) object;
    
    new ReduceBoundarySizesWindow(DefaultCustomizationFx.getInstance(), location, mapView);

  }
  
}

/**
 * This class is a {@link Consumer} of which the <code>accept</code> method retrieves the bounding box
 * for an <code>EObjectTreeItem</code> from Open Street Map.
 * The <code>EObjectTreeItem</code> shall be the bounding box attribute of a Location.
 */
class BoundingBoxObtainer implements Consumer<EObjectTreeItem> {
  private static final Logger LOGGER = Logger.getLogger(BoundingBoxObtainer.class.getName());

  @Override
  public void accept(EObjectTreeItem eObjectTreeItem) {
    LOGGER.severe("=> eObjectTreeItem=" + eObjectTreeItem.toString());
    EObjectTreeItemContent eObjectTreeItemContent = eObjectTreeItem.getValue();
    Object object = eObjectTreeItemContent.getObject();
    BoundingBox boundingBox;
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();      
    EObjectTreeItemContent eObjectParentTreeItemContent = parentEObjectTreeItem.getValue();
    Location location = (Location) eObjectParentTreeItemContent.getObject();
    // Create BoundingBox object if it doesn't exist yet.
    if (object != null) {
      boundingBox = (BoundingBox) object;
    } else {
            
      boundingBox = VacationsFactory.eINSTANCE.createBoundingBox();
           
      location.setBoundingbox(boundingBox);
      
    }
    
    // Obtain the BB from OSM
    Location locationFromNominatim = NominatimUtil.obtainLocationInfoFromNominatim(location.getLatitude(), location.getLongitude());
    BoundingBox boundingBoxFromNominatim = locationFromNominatim.getBoundingbox();
    if (boundingBoxFromNominatim != null) {
      boundingBox.setWest(boundingBoxFromNominatim.getWest());
      boundingBox.setNorth(boundingBoxFromNominatim.getNorth());
      boundingBox.setEast(boundingBoxFromNominatim.getEast());
      boundingBox.setSouth(boundingBoxFromNominatim.getSouth());
    }
    
    parentEObjectTreeItem.rebuildChildren();
    
    LOGGER.severe("<=");
  }
  
}
