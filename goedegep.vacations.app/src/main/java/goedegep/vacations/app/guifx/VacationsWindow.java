package goedegep.vacations.app.guifx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.imaging.ImageReadException;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;

import com.atlis.location.nominatim.NominatimAPI;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import goedegep.appgen.TableRowOperation;
import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.gpx.GpxUtil;
import goedegep.gpx.app.Activity;
import goedegep.gpx.app.GpxAppUtil;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.MetadataType;
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
import goedegep.properties.model.PropertiesFactory;
import goedegep.properties.model.PropertiesPackage;
import goedegep.properties.model.Property;
import goedegep.properties.model.PropertyGroup;
import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesPackage;
import goedegep.util.Tuplet;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.emf.EMFResource;
import goedegep.util.emf.EmfPackageHelper;
import goedegep.util.file.FileUtils;
import goedegep.util.i18n.TranslationFormatter;
import goedegep.util.img.PhotoFileMetaDataHandler;
import goedegep.vacations.app.LocationDescriptionDialog;
import goedegep.vacations.app.VacationsKmlConverter;
import goedegep.vacations.app.logic.NominatimUtil;
import goedegep.vacations.app.logic.OsmAndUtil;
import goedegep.vacations.app.logic.Ov2Util;
import goedegep.vacations.app.logic.PhotoImportResult;
import goedegep.vacations.app.logic.PhotosImporter;
import goedegep.vacations.app.logic.VacationToHtmlConverter;
import goedegep.vacations.app.logic.VacationsRegistry;
import goedegep.vacations.app.logic.VacationsUtils;
import goedegep.vacations.checklist.model.VacationChecklist;
import goedegep.vacations.checklist.model.VacationChecklistCategoriesList;
import goedegep.vacations.checklist.model.VacationChecklistFactory;
import goedegep.vacations.checklist.model.VacationChecklistLabelsList;
import goedegep.vacations.checklist.model.VacationChecklistPackage;
import goedegep.vacations.model.BoundingBox;
import goedegep.vacations.model.Day;
import goedegep.vacations.model.DayTrip;
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
import goedegep.vacations.model.util.VacationElementReferenceException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.print.PrinterJob;
import javafx.print.PrinterJob.JobStatus;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
 * Window with information about Travels.
 * <p>
 * The travel meta information is stored in a file, of which the name has to be specified via
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
  private static final POIPackage POI_PACKAGE = POIPackage.eINSTANCE; 
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
  private static final FlexDateFormat FDF = new FlexDateFormat();
  
  private static final double FULL_MAP_ZOOM_LEVEL = 1.7;     // Shows almost complete world map. Determined with trial and error.
  private static final double VACATION_ZOOM_LEVEL = 8.0;     // For the time being hard coded zoom level to show one vacation. Determined with trial and error.
  private static final double PICTURE_ZOOM_LEVEL = 12.0;     // For the time being hard coded zoom level to show a picture. Determined with trial and error.
  private static final double HOME_ZOOM_LEVEL = 8.0;         // The zoom level for showing the home location.
  
  private CustomizationFx customization = null;
  private static CustomizationFx staticCustomization = null;
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
  private TravelMapView travelMapView;
  private SearchResultLayer searchResultLayer;
  private LocationSearchWindow locationSearchWindow = null;
  
  private Browser browser;
  private Tab browserTab;
  private Browser tipsBrowser;
  // This menu item defines the 'edit status'.
  private CheckMenuItem vacationTreeEditableMenuItem;
  private NominatimAPI nominatimAPI = null;
  
  private int pulseCounter;

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
    
    editUserSettingsIfNeeded();
    
    this.customization = customization;
    staticCustomization = customization;
    
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
      LOGGER.info(VacationsRegistry.vacationChecklistFileName);
    } catch (FileNotFoundException e) {
      LOGGER.severe("File not found: " + e.getMessage());
      Alert alert = componentFactory.createYesNoConfirmationDialog(
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
        
    if (vacations != null) {
      treeView.setEObject(vacations);
    }
    
    flyHome();
    updateTitle();
    
    vacationsResource.dirtyProperty().addListener((observable, oldValue, newValue) -> updateTitle());
        
    vacationsResource.addNotificationListener(this::handleChangesInTheVacationsData);
    
    handleNewTreeItemSelected(null, null);
    updateTipsPane();
    
    show();
    
    LOGGER.info("<=");
  }

  public MapView getMapView() {
    return travelMapView;
  }
  
  /**
   * If the vacations file doesn't exist and/or the vacations folder doesn't exist ask the user whether they should be created, or to change the user settings.
   */
  private void editUserSettingsIfNeeded() {
    File vacationsFile = new File(VacationsRegistry.vacationsFileName);
    File vacationsFolder = new File(VacationsRegistry.vacationsFolderName);
    if (!vacationsFile.exists()  ||  !vacationsFolder.exists()) {
      String headerText = null;
      if (!vacationsFile.exists()  &&  !vacationsFolder.exists()) {
        headerText = "The vacations file '" + VacationsRegistry.vacationsFileName + "' and the vacations folder '" + VacationsRegistry.vacationsFolderName + "' both don't exist";
      } else if (!vacationsFile.exists()) {
        headerText = "The vacations file '" + VacationsRegistry.vacationsFileName + "' doesn't exist";
      } else {
        headerText = "The vacations folder '" + VacationsRegistry.vacationsFolderName + "' doesn't exist";
      }
      Optional<UserChoice> optionalUserChoice = componentFactory.createChoiceDialog("How to continue?", headerText, "what to do?", UserChoice.SHOW_SETTINGS_EDITOR, UserChoice.values()).showAndWait();
      if (optionalUserChoice.isPresent()) {
        UserChoice userChoice = optionalUserChoice.get();
        switch (userChoice) {
        case SHOW_SETTINGS_EDITOR:
          showPropertiesEditor();
          break;
          
        case CREATE_VACATIONS_FILE:
          // TODO
          break;
          
        case CREATE_VACATIONS_FOLDER:
          // TODO
          break;
          
        case CREATE_VACATIONS_FILE_AND_FOLDER:
          // TODO
          break;
        }
      }
    }
    File userPropertiesFile = new File(VacationsRegistry.customPropertiesFile);
    LOGGER.severe("userPropertiesFile: " + userPropertiesFile.getAbsolutePath());
    if (!userPropertiesFile.exists()) {
      EMFResource<PropertyGroup> propertiesResource = new EMFResource<>(PropertiesPackage.eINSTANCE, () -> PropertiesFactory.eINSTANCE.createPropertyGroup());
      PropertyGroup propertyGroup = propertiesResource.newEObject();
      propertyGroup.setName("Vacations");
      Property property = PropertiesFactory.eINSTANCE.createProperty();
      property.setName(NEWLINE);
    }
  }
  

  enum UserChoice {
    SHOW_SETTINGS_EDITOR("Edit User Settings"),
    CREATE_VACATIONS_FILE("Create vacations file"),
    CREATE_VACATIONS_FOLDER("Create vacations folder"),
    CREATE_VACATIONS_FILE_AND_FOLDER("Create vacations file and vacations folder");


    private String text;

    UserChoice(String text) {
      this.text = text;
    }

    public String toString() {
      return text;
    }
  }
  
  private void handleChangesInTheVacationsData(Notification notification) {
    LOGGER.severe("=>");
    
    updateTipsPane();
    
    LOGGER.severe("<=");
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
    treeView.addObjectSelectionListener(this::handleNewTreeItemSelected);
    centerPane.getItems().add(treeView);

    // MapView
    travelMapView = new TravelMapView(customization, this, this, poiIcons, getNominatimAPI());
    travelMapView.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent mouseEvent) {
        LOGGER.info("=>");
        
        // If 'right' button clicked, show context menu.
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
          LOGGER.info("mapView MouseEvent: x=" + mouseEvent.getX() + ", y=" + mouseEvent.getY());
          MapPoint newPoint = travelMapView.getMapPosition(mouseEvent.getX(), mouseEvent.getY());
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
          
          contextMenu.setAutoHide(true);
          
          travelMapView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

              contextMenu.show(travelMapView, event.getScreenX(), event.getScreenY());
            }
          });
        }
      }
      
    });
    
    treeView.addObjectSelectionListener((source, treeItem) -> {
      //TreeItem<EObjectTreeItemContent> treeItem
      if (treeItem != null) {
        EObjectTreeItemContent value = treeItem.getValue();
        if (value != null) {
          Object object = value.getObject();
          travelMapView.selectObject(object);
        }
      }
    });
    travelMapView.addObjectSelectionListener((source, object) -> {
      treeView.selectTreeItem(object);
    });
    
    TabPane tabPane = new TabPane();
    Tab tab = new Tab();
    tab.setText("Map");
    tab.setContent(travelMapView);
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

  public static void saveImageAsJpeg(Image image, File file) {
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

    // File: Export selected vacation as PDF
    menuItem = componentFactory.createMenuItem("Export selected vacation as PDF");
    menuItem.setOnAction(event -> exportVacationToPdf());
    menu.getItems().add(menuItem);

    // File: Export selected vacation as HTML
    menuItem = componentFactory.createMenuItem("Export selected vacation as HTML");
    menuItem.setOnAction(event -> exportVacationToHtml());
    menu.getItems().add(menuItem);

    // File: Print current map
    menuItem = componentFactory.createMenuItem("Print current map");
    menuItem.setOnAction(event -> print(travelMapView));
    menu.getItems().add(menuItem);
    
    // File: Import photos
    menuItem = componentFactory.createMenuItem("Import photos");
    menuItem.setOnAction(event -> {
      EObjectTreeItem treeItem = treeView.getSelectedObject();
      Vacation vacation = getVacationForTreeItem(treeItem);
      if (vacation == null) {
        Alert alert = componentFactory.createErrorDialog(
            "No vacation selected",
            "You have to select a vacation in order to be able to import photos for that vacation");
        
        alert.showAndWait();
      } else {
        if (vacation.getPictures() == null  ||  vacation.getPictures().isEmpty()) {
          Alert alert = componentFactory.createErrorDialog(
              "No picture folder set for vacation",
              "You have to set the pictures folder for the vacation in order to be able to import photos for that vacation");
          
          alert.showAndWait();
          
        } else {
          treeView.setEObject(null);
          List<PhotoImportResult> photoImportResults = new PhotosImporter().importPhotos(vacation);
          treeView.setEObject(vacations);
          new PhotoImportResultWindow(customization, treeView, photoImportResults);
        }
      }
    });
    menu.getItems().add(menuItem);

    // File: Import vacations
    menuItem = componentFactory.createMenuItem("Import vacations");
    menuItem.setOnAction(event -> importVacations());
    menu.getItems().add(menuItem);
    
    // File: Create a kml file
    menuItem = componentFactory.createMenuItem("Export selected vacation to KML file");
    menuItem.setOnAction(event -> exportVacationToKml());
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
    
    vacationTreeEditableMenuItem.setOnAction(event -> {
        treeView.setEditMode(vacationTreeEditableMenuItem.isSelected());
        travelMapView.setEditMode(vacationTreeEditableMenuItem.isSelected());
    });
    menu.getItems().add(vacationTreeEditableMenuItem);
    
    
    // Menu: Update map image files
    MenuUtil.addMenuItem(menu, "Update map image files", event -> updateMapImageFiles());
    
    menuBar.getMenus().add(menu);
    
    // Settings menu
    menu = new Menu("Settings");
    
    CheckMenuItem showCoordinatesInDocumentMenuItem = componentFactory.createCheckMenuItem("Show coordinates in Document");
    showCoordinatesInDocumentMenuItem.setSelected(VacationsRegistry.showCoordinatesInDocument);
    
    showCoordinatesInDocumentMenuItem.setOnAction(event -> {
        VacationsRegistry.showCoordinatesInDocument = showCoordinatesInDocumentMenuItem.isSelected();
        updateDocumentView(treeView.getSelectedObject());
    });
    menu.getItems().add(showCoordinatesInDocumentMenuItem);
    
    menuBar.getMenus().add(menu);
    
    
    
    // Checklist menu
    menu = new Menu("Checklist");

    // Checklist: Vacation checklist
    MenuUtil.addMenuItem(menu, "Vacation checklist", event ->  new VacationChecklistWindow(customization, vacationChecklistResource));

    // Checklist: Edit vacation Checklist
    MenuUtil.addMenuItem(menu, "Edit vacation Checklist", event -> new VacationChecklistEditor(customization, vacationChecklistResource));
    
    menuBar.getMenus().add(menu);
    
    // Tools menu
    menu = new Menu("Tools");

    // Tools: Check vacations
    MenuUtil.addMenuItem(menu, "Check vacations", event -> new CheckVacationsWindow(customization, vacations));
    
    menuBar.getMenus().add(menu);

    // Help menu
    menu = new Menu("Help");

    // Help: About
    MenuUtil.addMenuItem(menu, "About", event -> showHelpAboutDialog());

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
    
    travelMapView.clear();
    
    travelMapView.setZoom(8);
    
    MapPoint mapCenter = null;
    Location homeLocation = vacations.getHome();
    if (homeLocation != null) {
      mapCenter = new MapPoint(vacations.getHome().getLatitude(), vacations.getHome().getLongitude());
    }
        
    if (mapCenter != null) {
      travelMapView.setZoom(HOME_ZOOM_LEVEL);
      flyToIfEnabled(0.1, mapCenter, 3.0);
    }
    
    LOGGER.info("<=");
  }
  
  /**
   * Show the 'complete' world map.
   */
  private void showWorldMap() {
    MapPoint mapCenter = new MapPoint(0.0, 0.0);
    
    travelMapView.setZoom(FULL_MAP_ZOOM_LEVEL);
    flyToIfEnabled(0.1, mapCenter, 3.0);
  }
    
  /**
   * Handle the fact that a new item is selected in the {@link #treeView}.
   * <p>
   * This is handled by updating the document view and the vacations layer.
   * 
   * @param treeItem the newly selected item in the <code>treeView</code>.
   */
  private void handleNewTreeItemSelected(Object source, TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.severe("=>");
    
//    if (treeItem != null) {
//      EObjectTreeItemContent value = treeItem.getValue();
//      if (value != null) {
//        Object object = value.getObject();
//        if (object instanceof MapImage mapImage) {
//          createMapImageView(mapImage, poiIcons, true);
//          //          createMapImageFile(mapImage, poiIcons);
//        }
//      }
//    }

    updateDocumentView(treeItem);
    LOGGER.severe("Before updateMapForTreeItem");
    updateMapForTreeItem(travelMapView, treeItem);
    
    LOGGER.severe("<=");
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
   * Update the information shown on the map.
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
   * Besides this, all elements with a location of the related vacation are shown. 
   * </li>
   * <li>
   * Else, if the <code>treeItem</code> is a <code>Location</code> related to a <b>Vacation</b>, the elements with a location of that vacation are shown.
   * The map center is changed to this location and the zoom level is set to show the elements of the vacation.
   * </li>
   * <li>
   * Else, if the <code>treeItem</code> is a GPX track related to a <b>Vacation</b>, the elements with a location of that vacation are shown.
   * The map center is changed to the center of the track and the zoom level is set to show the items of the vacation.
   * </li>
   * <li>
   * Else, if the <code>treeItem</code> is a <b>Vacation</b> node, or any of its attributes or children, the elements with a location of that vacation are shown.
   * The map center is changed to the center of the shown elements and the zoom level is set to show the elements of the vacation.
   * </li>
   * <li>
   * Else, if the <code>treeItem</code> is the <b>vacations</b> attribute of <b>Vacations</b>, 
   * the stayed at locations of all vacations are shown.<br/>
   * The world map is shown.
   * </li>
   * <li>
   * Finally, if the <code>treeItem</code> is the <b>Vacations</b> node,
   * the home location and the stayed at locations of all vacations are shown.<br/>
   * The world map is shown.
   * </li>
   * </ul>
   * @param mapViewStructure represents the map on which the information is to be drawn.
   * @param treeItem the <code>TreeItem</code> for which information is to be shown.
   */
  private void updateMapForTreeItem(TravelMapView travelMapView, TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.info("=>");
    
    // Remove any existing information.
    travelMapView.clear();
    
    addHomeLocationToVacationsLayer(travelMapView);
    
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
    DayTrip dayTrip = null;
    List<Vacation> vacationsList = null;
    
    
    if ((pictureDataTuplet = getPictureDataForTreeItem(treeItem)) != null) {                // Picture
      Picture vacationElementPicture = pictureDataTuplet.getObject1();
      
      vacation = vacationElementPicture.getVacation();
      if (vacation != null) {
        addVacationToMapView(travelMapView, vacation, false);
      }
      dayTrip = vacationElementPicture.getDayTrip();
      if (dayTrip != null) {
        addDayTripToMapView(travelMapView, dayTrip);
      }
      travelMapView.getMapRelatedItemsLayer().showCurrentPhoto(vacationElementPicture.getPictureReference().getFile());
      WGS84Coordinates coordinates = pictureDataTuplet.getObject2();
      mapCenter = new MapPoint(coordinates.getLatitude() - 0.05, coordinates.getLongitude() + 0.1);
      zoomLevel = PICTURE_ZOOM_LEVEL;
    } else if ((locationDataTuplet = getLocationDataForTreeItem(treeItem)) != null) {       // Location
      Location location = locationDataTuplet.getObject1();
      vacation = locationDataTuplet.getObject2();
      addVacationToMapView(travelMapView, vacation, false);
      mapCenter = new MapPoint(location.getLatitude(), location.getLongitude());
      zoomLevel = VACATION_ZOOM_LEVEL;
    } else if ((trackDataTuplet = getTrackDataForTreeItem(treeItem)) != null) {             // Track
      GPXTrack track = trackDataTuplet.getObject1();
      vacation = trackDataTuplet.getObject2();
      addVacationToMapView(travelMapView, vacation, false);
      FileReference fileReference = track.getTrackReference();
      if ((fileReference != null)  &&  (fileReference.getFile() != null)) {
        EMFResource<DocumentRoot> gpxResource = GpxUtil.createEMFResource();
        try {
          gpxResource.load(fileReference.getFile());
          DocumentRoot documentRoot = gpxResource.getEObject();
          GpxType gpxType = documentRoot.getGpx();
          WGS84BoundingBox boundingBox = GpxUtil.calculateBoundingBox(gpxType);
          WGS84Coordinates center = boundingBox.getCenter();
          LOGGER.info("center: " + center.toString());
          mapCenter = new MapPoint(center.getLatitude(), center.getLongitude());
          zoomLevel = VACATION_ZOOM_LEVEL;
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }    	  
      }
    } else if ((vacation = getVacationForTreeItem(treeItem)) != null) {                      // vacation
      LOGGER.severe("before addVacationToMapView");
      WGS84BoundingBox vacationsLayerBoundingBox = addVacationToMapView(travelMapView, vacation, false);
      LOGGER.severe("after addVacationToMapView");
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
    } else if ((dayTrip = getDayTripForTreeItem(treeItem)) != null) {                      // day trip
      WGS84BoundingBox vacationsLayerBoundingBox = addDayTripToMapView(travelMapView, dayTrip);
      if (vacationsLayerBoundingBox != null) {
        WGS84Coordinates center = vacationsLayerBoundingBox.getCenter();
        mapCenter = new MapPoint(center.getLatitude(), center.getLongitude());
      }
      if (vacationsLayerBoundingBox != null) {
        zoomLevel = MapView.getZoomLevel(vacationsLayerBoundingBox);
      }
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
    LOGGER.severe("=>");
    Picture vacationElementPicture = getRelatedPictureForTreeItem(treeItem);

    if (vacationElementPicture == null) {
      LOGGER.severe("<= (null)");
      return null;
    }

    try {
      WGS84Coordinates pictureCoordinates = getPictureCoordinates(vacationElementPicture);

      if (pictureCoordinates == null) {
        LOGGER.severe("<= (null)");
        return null;
      }

      LOGGER.info("<= <picture>");
      return new Tuplet<>(vacationElementPicture, pictureCoordinates);
    } catch (VacationElementReferenceException vacationElementReferenceException) {
      LOGGER.severe("VacationElementReferenceException");
      VacationElement vacationElement = vacationElementReferenceException.getVacationElement();
      String fileName = null;
      String elementType = "Unknown";
      if (vacationElement instanceof Picture picture) {
        FileReference fileReference = picture.getPictureReference();
        if (fileReference != null) {
          fileName = fileReference.getFile();
        }
        elementType = "Picture";
      }
      String message = String.format("Reference not found!\nThe file \'%s\', referred to by the %s \'%s\', doesn't exist", fileName, elementType, vacationElement.toString());
      componentFactory.createExceptionDialog(message, vacationElementReferenceException).showAndWait();
    }
    
    LOGGER.severe("<= (null)");
    return null;
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
   * @return the <code>Picture</code> related to <code>treeItem</code>, or null if there is no related <code>Picture</code>.
   */
  private Picture getRelatedPictureForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.severe("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      LOGGER.severe("<= (null)");
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

    LOGGER.severe("<= vacationElementPicture=" + (vacationElementPicture != null ? vacationElementPicture.toString() : "(null)"));
    return vacationElementPicture;
  }
  
  /**
   * Get the coordinates to be used for showing a photo on the map.
   * <p>
   * If the photo has coordinates, these are the ones to be used.
   * Else if there is a parent location, the coordinates of this location are used.
   * 
   * @return the coordinates for the photo, or null is no coordinates are available.
   * @throws VacationElementReferenceException if the file, specified in the picture reference, doesn't exist.
   */
  private static WGS84Coordinates getPictureCoordinates(Picture vacationElementPicture) throws VacationElementReferenceException {
    String fileName = vacationElementPicture.getPictureReference().getFile();
    if (!new File(fileName).exists()) {
      throw new VacationElementReferenceException(vacationElementPicture);
    }
    LOGGER.info("before getting geo location");
    WGS84Coordinates coordinates = PhotoFileMetaDataHandler.getGeoLocation(vacationElementPicture.getPictureReference().getFile());
    LOGGER.info("after getting geo location");
    
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
  private static Location getRelatedLocation(Picture vacationElementPicture) {
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
    LOGGER.severe("=>");
    
    Location location = getRelatedLocationForTreeItem(treeItem);
    
    if (location == null) {
      LOGGER.severe("<= (null)");
      return null;
    }
    
    Vacation vacation = location.getVacation();
    
    if (vacation == null) {
      LOGGER.severe("<= (null)");
      return null;
    }
    
    LOGGER.severe("<= <location>");
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
//    LOGGER.severe("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    LOGGER.severe("=>");
    
    GPXTrack track = getRelatedTrackForTreeItem(treeItem);
    
    if (track == null) {
      LOGGER.severe("<= (null) (no track)");
      return null;
    }
    
    Vacation vacation = track.getVacation();
    
    if (vacation == null) {
      LOGGER.severe("<= (null) (no vacation for track)");
      return null;
    }
    
    LOGGER.severe("<= track=" + (track != null ? track.toString() : "(null)") + ", vacation=" + (vacation != null ? vacation.toString() : "(null)"));
    
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
    LOGGER.severe("=>");
    
    if (treeItem == null) {
      LOGGER.info("<= (null)");
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

    LOGGER.severe("<= vacation=" + (vacation != null ? vacation.toString() : "(null)"));
    return vacation;
  }
  
  /**
   * For a given treeItem, try to find the related DayTrip.
   * <p>
   * If the item is a <code>DayTrip</code>, this is the related DayTrip. Otherwise the parent relations are followed until
   * either a <code>DayTrip</code> is found, or that the top of the hierarchy is encountered.
   * If a <code>DayTrip</code> is found, that will be the related DayTrip, otherwise there is no related DayTrip.<br/>
   * 
   * @param treeItem the <code>TreeItem</code> for which the related DayTrip is to be found.
   * @return the related DayTrip, or null if this doesn't exist.
   */
  private static DayTrip getDayTripForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.severe("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      LOGGER.info("<= (null)");
      return null;
    }
    
    EObjectTreeItemContent eObjectTreeItemContent = treeItem.getValue();
    Object treeItemObject = eObjectTreeItemContent.getObject();
    DayTrip dayTrip = null;
    
    while (treeItem != null  &&  !(treeItemObject instanceof DayTrip)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        eObjectTreeItemContent = treeItem.getValue();
        treeItemObject = eObjectTreeItemContent.getObject();
      }
    }
    
    if (treeItemObject instanceof DayTrip) {
      dayTrip = (DayTrip) treeItemObject;
    }

    LOGGER.severe("<= dayTrip=" + (dayTrip != null ? dayTrip.toString() : "(null)"));
    return dayTrip;
  }
  
  /**
   * For a given treeItem, try to find the related Day.
   * <p>
   * If the item is a <code>Day</code>, this is the related Day. Otherwise the parent relations are followed until
   * either a <code>Day</code> is found, or that the top of the hierarchy is encountered.
   * If a <code>Day</code> is found, that will be the related Day, otherwise there is no related Day.<br/>
   * 
   * @param treeItem the <code>TreeItem</code> for which the related Day is to be found.
   * @return the related Day, or null if this doesn't exist.
   */
  private static Day getDayForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      return null;
    }
    
    EObjectTreeItemContent eObjectTreeItemContent = treeItem.getValue();
    Object treeItemObject = eObjectTreeItemContent.getObject();
    
    while (treeItem != null  &&  !(treeItemObject instanceof Day)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        eObjectTreeItemContent = treeItem.getValue();
        treeItemObject = eObjectTreeItemContent.getObject();
      }
    }
    
    if (treeItemObject instanceof Day day) {
      LOGGER.info("<= day=" + day.toString());
      return day;
    }

    LOGGER.info("<= null");
    return null;
  }
  
  /**
   * For a given treeItem, check whether it is the vacations attribute of a Vacations object. If so, return the vacations list, else return null.
   * 
   * @param treeItem the <code>TreeItem</code> to check.
   * @return the vacations list, or null.
   */
  @SuppressWarnings("unchecked")
  private List<Vacation> getVacationsListForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
//    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    LOGGER.severe("=>");
    
    if (treeItem == null) {
      LOGGER.severe("<= (null)");
      return null;
    }
    
    List<Vacation> vacations = null;
    EObjectTreeItemContent treeItemContent = treeItem.getValue();
    
    if ((treeItemContent.getEStructuralFeature() != null)  &&
        treeItemContent.getEStructuralFeature().equals(VACATIONS_PACKAGE.getVacations_Vacations())) {
      vacations = (List<Vacation>) treeItemContent.getObject();
    }
    
    LOGGER.severe("<= vacations=" + (vacations != null ? vacations.toString() : "(null)"));
    return vacations;
  }
  
  /**
   * For a given treeItem, check whether it is the vacations attribute of a Vacations object. If so, return the vacations list, else return null.
   * 
   * @param treeItem the <code>TreeItem</code> to check.
   * @return the vacations list, or null.
   */
  private Vacations getVacationsForTreeItem(TreeItem<EObjectTreeItemContent> treeItem) {
    LOGGER.severe("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      LOGGER.info("<= (null)");
      return null;
    }
    
    Vacations vacations = null;
    EObjectTreeItemContent treeItemContent = treeItem.getValue();
    Object object = treeItemContent.getObject();
    
    if (object instanceof Vacations) {
      vacations = (Vacations) object;
    }
        
    LOGGER.severe("<= vacations=" + (vacations != null ? vacations.toString() : "(null)"));
    return vacations;
  }
  
  /**
   * Add the 'stayed at' locations of a list of <code>Vacation</code>s to the vacations layer.
   * 
   * @param vacations the list of <code>Vacation</code>s of which the 'stayed at' locations are to be added to the vacation layer.
   */
  private void addVacationsToVacationsLayer(List<Vacation> vacations) {
    for (Vacation vacation: vacations) {
      addVacationToVacationsLayer(travelMapView, vacation, true);
    }
  }
  
  /**
   * Add the home location, if available, to the vacations layer.
   * 
   * @return a <code>MapPoint</code> with the coordinates of the home location, or null if there is no home location.
   */
  private void addHomeLocationToVacationsLayer(TravelMapView travelMapView) {
    LOGGER.info("=>");
        
    if ((vacations != null)  &&  vacations.isSetHome()) {
      Location homeLocation = vacations.getHome();
      if (homeLocation.isSetLatitude()  &&  homeLocation.isSetLongitude()) {
        travelMapView.getMapRelatedItemsLayer().addLocation(homeLocation, "Home");
      }
    }
        
    LOGGER.info("<=");
  }
  
  /**
   * Add all relevant information of a vacation to a map view.
   * 
   * @param vacation the <code>Vacation</code> of which the <code>Location</code>s are to be added.
   * @param stayedAtOnly if <code>true</code> only the 'stayed at' locations are added.
   * @return a {@code WGS84BoundingBox} around all items added to the map view.
   */
  private WGS84BoundingBox addVacationToMapView(TravelMapView travelMapView, Vacation vacation, boolean stayedAtOnly) {
    WGS84BoundingBox boundingBox = addVacationToVacationsLayer(travelMapView, vacation, false);
//    if (vacationTreeEditableMenuItem.isSelected()  &&  (boundingBox != null)) {
//      mapViewStructure.mapRelatedItemsLayer().addBoundingBox(boundingBox);
//    }

    return boundingBox;
  }

  /**
   * Add the <code>Location</code>s of a <code>Vacation</code> to the vacations layer.
   * 
   * @param vacation the <code>Vacation</code> of which the <code>Location</code>s are to be added.
   * @param stayedAtOnly if <code>true</code> only the 'stayed at' locations are added.
   */
  private WGS84BoundingBox addVacationToVacationsLayer(TravelMapView travelMapView, Vacation vacation, boolean stayedAtOnly) {
    LOGGER.info("=> vacation=" + vacation.toString());
    WGS84BoundingBox totalWGS84BoundingBox = null;
        
    LOGGER.info("Going to handle new vacation elements");
    for (VacationElement vacationElement: vacation.getElements()) {
      WGS84BoundingBox wgs84BoundingBox = handleVacationElementForMapDisplay(travelMapView, vacationElement, stayedAtOnly, null);
      totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
    }
    
    try {
      List<WGS84Coordinates> lineNodes = VacationsUtils.getGeoLocations(vacation);
      if (lineNodes.size() > 1) {
        travelMapView.getMapRelatedItemsLayer().addLocationsVisitedPolyLine(FXCollections.observableList(lineNodes));
      }
    } catch (FileNotFoundException e) {
      LOGGER.severe("File not found");
      componentFactory.createErrorDialog("File not found", e.getMessage()).showAndWait();
    }
    
    LOGGER.info("<=");
    return totalWGS84BoundingBox;
  }

  /**
   * Add the <code>Location</code>s of a <code>DayTrip</code> to the vacations layer.
   * 
   * @param dayTrip the <code>DayTrip</code> of which the <code>Location</code>s are to be added.
   */
  private WGS84BoundingBox addDayTripToMapView(TravelMapView travelMapView, DayTrip dayTrip) {
    LOGGER.info("=> dayTrip=" + dayTrip.toString());
    WGS84BoundingBox totalWGS84BoundingBox = null;
        
    LOGGER.info("Going to handle new dayTrip elements");
    for (VacationElement vacationElement: dayTrip.getElements()) {
      WGS84BoundingBox wgs84BoundingBox = handleVacationElementForMapDisplay(travelMapView, vacationElement, false, null);
      totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
    }
        
    LOGGER.info("<=");
    return totalWGS84BoundingBox;
  }
  
  /**
   * Add all relevant information of a day to a map view.
   * 
   * @param vacation the <code>Vacation</code> of which the <code>Location</code>s are to be added.
   * @param stayedAtOnly if <code>true</code> only the 'stayed at' locations are added.
   * @return a {@code WGS84BoundingBox} around all items added to the map view.
   */
  private WGS84BoundingBox addDayToMapView(TravelMapView travelMapView, Day day, boolean stayedAtOnly) {
    WGS84BoundingBox boundingBox = addDayToVacationsLayer(travelMapView, day, false);

    return boundingBox;
  }

  /**
   * Add the <code>Location</code>s of a <code>Vacation</code> to the vacations layer.
   * 
   * @param vacation the <code>Vacation</code> of which the <code>Location</code>s are to be added.
   * @param stayedAtOnly if <code>true</code> only the 'stayed at' locations are added.
   */
  private WGS84BoundingBox addDayToVacationsLayer(TravelMapView travelMapView, Day day, boolean stayedAtOnly) {
    LOGGER.info("=> day=" + day.toString());
    WGS84BoundingBox totalWGS84BoundingBox = null;
        
    LOGGER.info("Going to handle new day elements");
    for (VacationElement vacationElement: day.getChildren()) {
      WGS84BoundingBox wgs84BoundingBox = handleVacationElementForMapDisplay(travelMapView, vacationElement, stayedAtOnly, null);
      totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
    }
        
    try {
    List<WGS84Coordinates> lineNodes = VacationsUtils.getGeoLocations(day);
    if (lineNodes.size() > 1) {
      travelMapView.getMapRelatedItemsLayer().addLocationsVisitedPolyLine(FXCollections.observableList(lineNodes));
    }
    } catch (FileNotFoundException e) {
      LOGGER.severe("File not found");
      componentFactory.createErrorDialog("File not found", e.getMessage()).showAndWait();
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
  private WGS84BoundingBox handleVacationElementForMapDisplay(TravelMapView travelMapView, VacationElement vacationElement, boolean stayedAtOnly, WGS84BoundingBox totalWGS84BoundingBox) {
    LOGGER.info("=> totalWGS84BoundingBox: " + (totalWGS84BoundingBox != null ? totalWGS84BoundingBox.toString() : "(null)"));
    
    if (vacationElement instanceof Location location) {
      if (stayedAtOnly && (!location.isStayedAtThisLocation())) {
        return totalWGS84BoundingBox;
      }
      
      LOGGER.info("Going to add location to the map: location=" + location.toString());
      String text = location.getName();
      if (text == null) {
        text = location.getCity();
      }
      WGS84BoundingBox wgs84BoundingBox = travelMapView.getMapRelatedItemsLayer().addLocation(location, text);
      totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
    } else if (vacationElement instanceof Picture picture) {
      if (stayedAtOnly) {
        return totalWGS84BoundingBox;
      }
      
      try {
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
              WGS84BoundingBox wgs84BoundingBox = travelMapView.getMapRelatedItemsLayer().addPhoto(coordinates, text, fileName);
              totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
            }
          }
        }
      } catch (VacationElementReferenceException vacationElementReferenceException) {
        LOGGER.severe("VacationElementReferenceException");
        VacationElement element = vacationElementReferenceException.getVacationElement();
        String fileName = null;
        String elementType = "Unknown";
        if (element instanceof Picture elementPicture) {
          FileReference fileReference = elementPicture.getPictureReference();
          if (fileReference != null) {
            fileName = fileReference.getFile();
          }
          elementType = "Picture";
        }
        String message = String.format("Reference not found!\nThe file \'%s\', referred to by the %s \'%s\', doesn't exist", fileName, elementType, element.toString());
        componentFactory.createExceptionDialog(message, vacationElementReferenceException).showAndWait();
      }
      
    } else if (vacationElement instanceof GPXTrack track) {
      LOGGER.info("Going to add GPX track to the map: track=" + track.toString());
      
      FileReference bestandReferentie = track.getTrackReference();
      if ((bestandReferentie != null)  &&  (bestandReferentie.getFile() != null)  &&  !bestandReferentie.getFile().isEmpty()) {
        EMFResource<DocumentRoot> gpxResource = GpxUtil.createEMFResource();
        try {
          gpxResource.load(bestandReferentie.getFile());
          DocumentRoot documentRoot = gpxResource.getEObject();
          GpxType gpxType = documentRoot.getGpx();
          WGS84BoundingBox wgs84BoundingBox = travelMapView.getGpxLayer().addGpx(bestandReferentie.getTitle(), bestandReferentie.getFile(), gpxType);
          totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
    
    for (VacationElement childElement: vacationElement.getChildren()) {
      totalWGS84BoundingBox = handleVacationElementForMapDisplay(travelMapView, childElement, stayedAtOnly, totalWGS84BoundingBox);
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
    createAndAddEObjectTreeDescriptorForDayTrip(eObjectTreeDescriptor, vakantiesPackageHelper);
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
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass, (eObject) -> "Travel information", true, null,
        eObject -> TravelImageResource.TRAVEL.getIcon(ImageSize.SIZE_1));
    eObjectTreeItemClassDescriptor.setStrongText(true);

    // Vacations.tips
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getVacations_Tips(), "Tips", PresentationType.MULTI_LINE_TEXT, null));

    // Vacations.home
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Create home location"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete home location"));
    EObjectTreeItemClassReferenceDescriptor homeDescriptor = new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getVacations_Home(), vakantiesPackageHelper.getEClass("goedegep.vacations.model.Location"), (eObject) -> "Home location", false, nodeOperationDescriptors);
    homeDescriptor.setStrongText(true);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(homeDescriptor);
    
    // Vacations.vacations
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New vacation"));
    EObjectTreeItemClassListReferenceDescriptor vacationsDescriptor = new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacations_Vacations(), "Vacations", true, nodeOperationDescriptors,
        eObject -> TravelImageResource.VACATIONS.getIcon(ImageSize.SIZE_1));
    vacationsDescriptor.setStrongText(true);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(vacationsDescriptor);
    
    // Vacations.dayTrips
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New day trip"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacations_DayTrips(), "Day trips", true, nodeOperationDescriptors));
    
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
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move element up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move element down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
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
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move element up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move element down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
    nodeOperationDescriptors.add(new ExtendedNodeOperationDescriptor("Reduce boundaries sizes", (Predicate<EObjectTreeItem>) this::isMenuToBeEnabled, (Consumer<EObjectTreeItem>) this::reduceBoundariesSizes));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
          StringBuilder buf = new StringBuilder();
          boolean spaceNeeded = false;
          Location location = (Location) eObject;
          if (location.isSetLocationType()) {
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
          return poiIcons.getIcon(poiCategoryId, 16, 16);
        });
    
    // Location.stayedAtThisLocation
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_StayedAtThisLocation(), "Is stayed at location", null));
    // VacationElement.startDate
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_StartDate(), "From", FDF, null));
    // VacationElement.endDate
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_EndDate(), "Until", FDF, null));
    // Location.duration
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Duration(), "Duration of stay", null));
    // Location.description
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Description(), "Description", PresentationType.MULTI_LINE_TEXT, null));

    // Location.name
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Name(), "Name", null));
    // Location.locationType
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_LocationType(), "Location type", null));
    // Location.country
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Country(), "Country", null));
    // Location.city
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_City(), "City", null));
    // Location.street
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Street(), "Street", null));
    // Location.houseNumber
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_HouseNumber(), "Housenumber", null));
    // Location.webSite
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.OPEN, "Open document"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_WebSite(), "Website", nodeOperationDescriptors));
    // Location.referenceOnly
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_ReferenceOnly(), "Reference only", null));
    // Location.latitude
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Latitude(), "Latitude", null));
    // Location.longitude
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getLocation_Longitude(), "Longitude", null));
    // Location.boundingBox
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Create bounding box"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete Bounding Box"));
    nodeOperationDescriptors.add(new ExtendedNodeOperationDescriptor("Obtain bounding box", null, new BoundingBoxObtainer()));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getLocation_Boundingbox(), vakantiesPackageHelper.getEClass("goedegep.vacations.model.BoundingBox"), (eObject) -> "Bounding box", true, nodeOperationDescriptors));

    // Location.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Nieuw element", this::fillMapImage));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
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
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New vacation before this one"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New vacation after this one"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move vacation up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move vacation down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete vacation"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
            Vacation vacation = (Vacation) eObject;
            return vacation.getId();
          }, false, nodeOperationDescriptors,
        eObject -> {
            return customization.getResources().getApplicationImage(ImageSize.SIZE_0);
        });
    eObjectTreeItemClassDescriptor.setStrongText(true);
    
    // Vacation.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getVacation_Title(), "Title", null));
    // Vacation.date (startDate)
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(typesPackage.getEvent_Date(), "From", FDF, null));
    // Vacation.endDate
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getVacation_EndDate(), "Until", FDF, null));
    // Vacation.notes
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(typesPackage.getEvent_Notes(), "General", PresentationType.MULTI_LINE_TEXT, null));
    
    // Vacation.documents
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New document"));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacation_Documents(), "Documents", false, nodeOperationDescriptors));
    
    // Vacation.pictures
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.OPEN, "Open photos folder"));
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getVacation_Pictures(), "Photos", PresentationType.FOLDER, nodeOperationDescriptors);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(eObjectTreeItemAttributeDescriptor);

    // Vacation.elements
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", this::fillMapImage));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacation_Elements(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }

  /**
   * Create the descriptor for the EClass goedegep.model.vacations.DayTrip.
   * 
   * @param eObjectTreeDescriptor the tree descriptor to which the DayTrip descriptor is to be added.
   * @param vakantiesPackageHelper an <code>EmfPackageHelper</code> for the <code>VacationsPackage</code>
   */
  private void createAndAddEObjectTreeDescriptorForDayTrip(EObjectTreeDescriptor eObjectTreeDescriptor, EmfPackageHelper vakantiesPackageHelper) {
    EClass eClass = vakantiesPackageHelper.getEClass("goedegep.vacations.model.DayTrip");
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
        
    // DayTrip
    List<NodeOperationDescriptor> nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New day trip before this one"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New day trip after this one"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move day trip up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move day trip down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete day trip"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
            DayTrip dayTrip = (DayTrip) eObject;
            return dayTrip.getId();
          }, false, nodeOperationDescriptors,
        eObject -> {
            return TravelImageResource.DAY_TRIP.getIcon(ImageSize.SIZE_0);
        });
    
    // DayTrip.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getDayTrip_Title(), "Title", null));
    // DayTrip.date
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(typesPackage.getEvent_Date(), "Date", FDF, null));

    // Vacation.elements
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", this::fillMapImage));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getDayTrip_Elements(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
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
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New document before this one"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New document after this one"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move document up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move document down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete document"));
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
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.OPEN, "Open document"));
    EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = new EObjectTreeItemAttributeDescriptor(typesPackage.getFileReference_File(), "File", PresentationType.FILE, nodeOperationDescriptors);
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
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element ..."));
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
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move element up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move element down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
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
          }, false, nodeOperationDescriptors, object -> TravelImageResource.DAY.getIcon(ImageSize.SIZE_0));
    
    // Day.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getDay_Title(), "Title", null));
    
    // Day.days
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getDay_Days(), "Days", null));

    // Day.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", this::fillMapImage));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
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
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move element up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move element down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
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
          }, false, nodeOperationDescriptors, object -> ImageResource.TEXT.getImage(ImageSize.SIZE_0));
    
    // VacationElementText.text
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getText_Text(), "Text", PresentationType.MULTI_LINE_TEXT, null));

    // VacationElementText.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", this::fillMapImage));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
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
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move element up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move element down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
          Picture picture = (Picture) eObject;
          String text = "...";  // default value
          File file = null;
          if (picture.isSetPictureReference()) {
            FileReference bestandReferentie = picture.getPictureReference();
            text = bestandReferentie.getTitle();  // first preference; the title set in the FileReference
            
            if (text == null  ||  text.isEmpty()) {
              String fileName = bestandReferentie.getFile();
              if (fileName != null) {
                try {
                  file = new File(fileName);
                  PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(file);
                  text = photoFileMetaDataHandler.getTitle();   // second preference; title set in the photo
                } catch (ImageReadException | IOException e) {
                  e.printStackTrace();
                }
              }
              
              if (text == null  ||  text.isEmpty()) {
                if (file != null) {
                  text = file.getName();
                }
              }
              return text;
            }
          } else {
            return "Picture";
          }
          
          return text;
        }, false, nodeOperationDescriptors, (object) -> {
          if (object instanceof Picture picture) {
            FileReference fileReference = picture.getPictureReference();
            Image image = null;
            if (fileReference != null) {
              image = new Image("file:" + picture.getPictureReference().getFile(), 150, 150, true, true);
            }
            return image;
          } else {
            return ImageResource.CAMERA_BLACK.getImage(ImageSize.SIZE_0);
          }
        });

    // VacationElementPicture.pictureReference
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Create photo reference"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete photo reference"));
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
    EmfPackageHelper typesPackageHelper = new EmfPackageHelper(typesPackage);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getPicture_PictureReference(), typesPackageHelper.getEClass("goedegep.types.model.FileReference"), (eObject) -> "Photo reference", true, nodeOperationDescriptors));
    
    // VacationElementText.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", this::fillMapImage));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
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
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move element up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move element down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        this::generateTextForGpxTrack, false, nodeOperationDescriptors, this::generateIconForGpxTrack);
    
    // VacationElementGPX.trackReference
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "Create GPX track reference"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete GPX track reference"));
    TypesPackage typesPackage = TypesPackage.eINSTANCE;
    EmfPackageHelper typesPackageHelper = new EmfPackageHelper(typesPackage);
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassReferenceDescriptor(VACATIONS_PACKAGE.getGPXTrack_TrackReference(), typesPackageHelper.getEClass("goedegep.types.model.FileReference"), (eObject) -> "GPX track reference", true, nodeOperationDescriptors));
    
    // VacationElementGPX.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", this::fillMapImage));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
    eObjectTreeDescriptor.addEClassDescriptor(eClass, eObjectTreeItemClassDescriptor);
  }
  
  /**
   * Generate the text to be shown for a GPXTrack item in the TreeView.
   * <p>
   * In order of availablility the text is:
   * <ul>
   * <li>The title in the FileReference in the gpxTrack.</li>
   * <li>The name attribute in the GPX file.</li>
   * <li>The name of the GPX file.</li>
   * </ul>
   * 
   * @param gpxTrack The <code>GPXTrack</code> for which a text is to be generated.
   */
  private String generateTextForGpxTrack(EObject eObject) {
    GPXTrack gpxTrack = (GPXTrack) eObject;
    String text = null;
    FileReference fileReference = gpxTrack.getTrackReference();

    // Try to use the title in the FileReference 
    if (fileReference != null) {
      if (fileReference != null) {
        String title = fileReference.getTitle();
        if (title != null  &&  !title.isEmpty()) {
          text = title;
        }
      }
    }
    
    if (text == null) {
      // Try to use the name attribute in the GPX file
      if ((fileReference != null)  &&  (fileReference.getFile() != null)  &&  !fileReference.getFile().isEmpty()) {
        EMFResource<DocumentRoot> gpxResource = GpxUtil.createEMFResource();
        try {
          gpxResource.load(fileReference.getFile());
          DocumentRoot documentRoot = gpxResource.getEObject();
          GpxType gpxType = documentRoot.getGpx();
          MetadataType metadataType = gpxType.getMetadata();
          String name = metadataType.getName();
          if (name != null  &&  !name.isEmpty()) {
            text = name;
          }
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }      
    }
    
    if (text == null) {
      if (fileReference != null) {
        String fileName = fileReference.getFile();
        if (fileName != null  &&  !fileName.isEmpty()) {
          text = fileName;
        }
      }
    }
    
    if (text == null) {
      text = "GPX track";
    }
    
    return text;
  }
  
  private Image generateIconForGpxTrack(Object object) {
    Image gpxTrackImage = ImageResource.GPX.getImage();

    GPXTrack gpxTrack = (GPXTrack) object;
    FileReference fileReference = gpxTrack.getTrackReference();

    Activity activity = null;
    if (fileReference != null) {
      String fileName = fileReference.getFile();
      if (fileName != null  && !fileName.isEmpty()) {
        EMFResource<DocumentRoot> gpxResource = GpxUtil.createEMFResource();
        try {
          gpxResource.load(fileName);
          DocumentRoot documentRoot = gpxResource.getEObject();
          GpxType gpxType = documentRoot.getGpx();
          activity = GpxAppUtil.getActivity(gpxType);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    }

    Image activityImage = null;
    if (activity != null) {
      activityImage = activity.getIcon();
    }

    if (activityImage != null) {
      gpxTrackImage = scale(gpxTrackImage, gpxTrackImage.getWidth(), activityImage.getHeight());
      double width = gpxTrackImage.getWidth() + activityImage.getWidth() + 4;
      double height = Math.max(gpxTrackImage.getHeight(), activityImage.getHeight());
      Canvas iconCanvas = new Canvas(width, height);
      GraphicsContext gc = iconCanvas.getGraphicsContext2D();
      gc.drawImage(gpxTrackImage, 0, 0);
      gc.drawImage(activityImage, gpxTrackImage.getWidth() + 4, 0);

      return iconCanvas.snapshot(null, null);
    }

    return gpxTrackImage;
  }

  public Image scale(Image source, double targetWidth, double targetHeight) {
    ImageView imageView = new ImageView(source);
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(targetWidth);
    imageView.setFitHeight(targetHeight);
    return imageView.snapshot(null, null);
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
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_BEFORE, "New element before this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT_AFTER, "New element after this one ..."));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_UP, "Move element up"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.MOVE_OBJECT_DOWN, "Move element down"));
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.DELETE_OBJECT, "Delete element"));
    nodeOperationDescriptors.add(new ExtendedNodeOperationDescriptor("Update  image file", (eObjectTreeItem) -> true, this::updateMapImageFile));
    EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = new EObjectTreeItemClassDescriptor(eClass,
        eObject -> {
            MapImage picture = (MapImage) eObject;
            String title = picture.getTitle();
            if (title != null  &&  !title.isEmpty()) {
              return title;
            } else {
              return "MapImage";
            }
          }, false, nodeOperationDescriptors, object -> ImageResource.MAP.getImage());

    // MapImage.title
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_Title(), "Title", null));

    // MapImage.imageWidth
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_ImageWidth(), "Image width", null));

    // MapImage.imageHeight
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_ImageHeight(), "Image height", null));

    // MapImage.zoom
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_Zoom(), "Zoom level", null));

    // MapImage.centerLatitude
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_CenterLatitude(), "Center latitude", null));

    // MapImage.centerLongitude
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_CenterLongitude(), "Center longitude", null));

    // MapImage.fileName
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemAttributeDescriptor(VACATIONS_PACKAGE.getMapImage_FileName(), "Filename", PresentationType.FILE, null));
    
    // MapImage.children
    nodeOperationDescriptors = new ArrayList<>();
    nodeOperationDescriptors.add(new NodeOperationDescriptor(TableRowOperation.NEW_OBJECT, "New element", this::fillMapImage));
    eObjectTreeItemClassDescriptor.addStructuralFeatureDescriptor(new EObjectTreeItemClassListReferenceDescriptor(VACATIONS_PACKAGE.getVacationElement_Children(), "Elements", true, nodeOperationDescriptors, object -> EObjectTreeView.getListIcon()));
    
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
      new PrintWindow(customization, this, vacation);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Export the currently selected vacation to a PDF file.
   * <p>
   * The setting of the Settings menu are taken into account.
   */
  private void exportVacationToPdf() {
    EObjectTreeItem treeItem = treeView.getSelectedObject();
    Vacation vacation = getVacationForTreeItem(treeItem);
    if (vacation == null) {
      statusLabel.setText("No vacation selected");
      return;
    }
    
    // Generate HTML for the vacation.
    String htmlText = vacationToHtmlConverter.vacationToHtml(vacation);
        
    // Generate a PDF file for the HTML String
    String vacationsFolder = VacationsUtils.getVacationFolder(vacation);
    if (vacationsFolder == null) {
      statusLabel.setText("No vacation folder found");
      return;
    }
    String vacationId = vacation.getId();
    if (vacationId == null  ||  vacationId.isEmpty()) {
      statusLabel.setText("No vacation Id");
      return;
    }
    Path pdfFilePath = Paths.get(vacationsFolder, vacationId + ".pdf");
    try(OutputStream os = Files.newOutputStream(pdfFilePath)) {
      PdfRendererBuilder builder = new PdfRendererBuilder();
      builder.useFastMode();
      builder.withHtmlContent(htmlText, null);
      builder.toStream(os);
      builder.run();
      os.close();
      statusLabel.setText("Vacation exported to " + pdfFilePath.toString());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Export the currently selected vacation to an HTML file.
   * <p>
   * The setting of the Settings menu are taken into account.
   */
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
    String vacationId = vacation.getId();
    if (vacationId == null  ||  vacationId.isEmpty()) {
      statusLabel.setText("No vacation Id");
      return;
    }
    File file = new File(vacationsFolder, vacationId + ".html");
    
    try(PrintWriter out = new PrintWriter(file)) {
      out.println(vacationHtmlDocument);
      out.close();
      statusLabel.setText("Vacation exported to " + file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Print a <code>Node</code>.
   * 
   * @param node A <code>Node</code>, typically the <code>mapView</code>.
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
   * Import vacations
   */
  private void importVacations() {
    VacationsImporter vacationsImporter = new VacationsImporter(customization, vacations);
    vacationsImporter.importVacations(VacationsRegistry.vacationsFolderName, VacationsRegistry.vacationPicturesFolderName);
    treeView.setEObject(vacations);  // trick to update the treeView
  }
  
  /**
   * Generate a KML file from the vacations.
   */
  private void exportVacationToKml() {
    // Let user select a file to save to.
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Kml file");
    FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("KML files (*.kml)", "*.kml");
    fileChooser.getExtensionFilters().add(extensionFilter);
    fileChooser.setSelectedExtensionFilter(extensionFilter);
    File file = fileChooser.showSaveDialog(this);
    LOGGER.severe("Creating kml file: " + file.getAbsolutePath());
    
    EObjectTreeItem treeItem = treeView.getSelectedObject();
    Vacation vacation = getVacationForTreeItem(treeItem);
    if (vacation == null) {
      statusLabel.setText("No vacation selected");
      return;
    }
    
    // Generate the file using the VacationsKmlConverter
    VacationsKmlConverter vacationsKmlConverter = new VacationsKmlConverter(poiIcons);
    try {
      vacationsKmlConverter.createKmlForVacation(vacation, file);
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
  
  /**
   * Show the User Properties editor.
   */
  private void showPropertiesEditor() {
    new PropertiesEditor("Vacation properties", customization, getBundle(VacationsWindow.class, "VacationsPropertyDescriptorsResource"),
        VacationsRegistry.propertyDescriptorsResource, VacationsRegistry.customPropertiesFile);
  }

  /**
   * Show a dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
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
    travelMapView.snapshot(snapShotParameters, writebleImage);
    
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
//      mapViewStructure.mapView().flyTo(waitTime, mapPoint, seconds);
      travelMapView.setCenter(mapPoint);
    }
    
  }
  
  private void setZoomIfEnabled(Double zoomLevel) {
    if (!vacationTreeEditableMenuItem.isSelected()) {
      travelMapView.setZoom(zoomLevel);
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
    
    new ReduceBoundarySizesWindow(DefaultCustomizationFx.getInstance(), location, travelMapView);

  }
  
  /**
   * Fill a newly created EObject with available information.
   * 
   * @param eObject the newly created EObject
   * @param treeItem the <code>EObjectTreeItem</code> to which the <code>eObject</code> is added.
   */
  private void fillMapImage(EObject eObject, EObjectTreeItem treeItem) {
    LOGGER.severe("=> eObject=" + eObject + ", treeItem=" + treeItem);
    
    if (eObject instanceof MapImage mapImage) {
      // No default value for 'title'.
      
      double imageHeight = travelMapView.getHeight();
      mapImage.setImageHeight(imageHeight);
      double imageWidth = travelMapView.getWidth();
      mapImage.setImageWidth(imageWidth);
      mapImage.setCenterLatitude(travelMapView.getBaseMap().centerLat().get());
      mapImage.setCenterLongitude(travelMapView.getBaseMap().centerLon().get());
      double zoom = travelMapView.getBaseMap().zoom().get();
      mapImage.setZoom(zoom);
      
      Object contentObject = getDayForTreeItem(treeItem);
      if (contentObject == null) {
        contentObject = getVacationForTreeItem(treeItem);
      }
      
      Vacation vacation = getVacationForTreeItem(treeItem);
      if (vacation == null) {
        LOGGER.severe("No vacation found for treeItem");
      }
      LOGGER.severe("Vacation: " + vacation.getTitle());
      String vacationFolderName = VacationsUtils.getVacationFolder(vacation);
      if (vacationFolderName == null) {
        LOGGER.severe("No vacation folder found");
      }
      String fileName = FileUtils.createBackupFileName("MapImage.jpg");  // add to model
      File file = new File(vacationFolderName, fileName);      
      mapImage.setFileName(file.getAbsolutePath());
    }
  }
  
  
  private void updateMapImageFiles() {
    EObjectTreeItem treeItem = treeView.getSelectedObject();
    Vacation vacation = getVacationForTreeItem(treeItem);
    if (vacation == null) {
      statusLabel.setText("No vacation selected");
      return;
    }
    updateMapImageFiles(vacation);
  }
  
  private void updateMapImageFiles(Vacation vacation) {
    TreeIterator<EObject> vacationIterator = vacation.eAllContents();
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      if (eObject instanceof MapImage mapImage) {
        createMapImageView(mapImage, poiIcons, false);
      }
    }
  }
  
  private void updateMapImageFile(EObjectTreeItem eObjectTreeItem) {
    Object object = eObjectTreeItem.getValue().getObject();
    if (object instanceof MapImage mapImage) {
      createMapImageView(mapImage, poiIcons, false);
    }
  }

  public MapView createMapImageView(MapImage mapImage, POIIcons poiIcons, boolean show) {
    String title = mapImage.getTitle();
    if (title == null) {
      title = "MapImage";
    }
    JfxStage jfxStage = new JfxStage(title, staticCustomization);
    TravelMapView imageTravelMapView = new TravelMapView(customization, jfxStage, this, poiIcons, getNominatimAPI());

    Double height = mapImage.getImageHeight();
    if (height != null) {
      imageTravelMapView.setPrefHeight(height);
    }
    Double width = mapImage.getImageWidth();
    if (width != null) {
      imageTravelMapView.setPrefWidth(width);
    }

    Scene scene = new Scene(imageTravelMapView);
    pulseCounter = 0;
    if (!show) {
      scene.addPostLayoutPulseListener(() -> {
        if (imageTravelMapView.getBaseMap().allTilesAvailable()  &&  pulseCounter++ > 40) {
          LOGGER.severe("Map should be ready");


          SnapshotParameters snapShotParameters = new SnapshotParameters();
          WritableImage writebleImage = new WritableImage((int) imageTravelMapView.getWidth(), (int) imageTravelMapView.getHeight());
          imageTravelMapView.snapshot(snapShotParameters, writebleImage);

          // Save the image
          File file = new File(mapImage.getFileName());
//          if (!file.exists()) {
            saveImageAsJpeg(writebleImage, file);
//          } else {
//            LOGGER.severe("File exists");
//          }
          jfxStage.close();
        }
      });
    }
    jfxStage.setScene(scene);
    jfxStage.show();

    imageTravelMapView.setZoom(mapImage.getZoom());

    Point2D center = new Point2D(mapImage.getCenterLatitude(), mapImage.getCenterLongitude());
    imageTravelMapView.getBaseMap().setCenter(center);
    
    Object contentObject = null;
    EObject container = mapImage.eContainer();
    while (container != null) {
      if (container instanceof Day  ||  container instanceof Vacation) {
        contentObject = container;
        break;
      }
      
      container = container.eContainer();
    }
    
    if (contentObject instanceof Day day) {
      addDayToMapView(imageTravelMapView, day, false);
    } else if (contentObject instanceof Vacation vacation) {
      addVacationToMapView(imageTravelMapView, vacation, false);
    }

    return imageTravelMapView;
  }
  
  public void createMapImageFile(MapImage mapImage, POIIcons poiIcons) {
    
    createMapImageView(mapImage, poiIcons, false);
       
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
