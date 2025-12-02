package goedegep.travels.app.guifx;

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.atlis.location.nominatim.NominatimAPI;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import com.google.gson.Gson;

import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.gpx.GpxUtil;
import goedegep.gpx.app.Activity;
import goedegep.gpx.app.GpxAppUtil;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.MetadataType;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.browser.Browser;
import goedegep.jfx.eobjecttreeview.EObjectTreeCell;
import goedegep.jfx.eobjecttreeview.EObjectTreeItem;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.properties.model.PropertiesFactory;
import goedegep.properties.model.PropertiesPackage;
import goedegep.properties.model.Property;
import goedegep.properties.model.PropertyGroup;
import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import goedegep.travels.app.TravelsService;
import goedegep.travels.app.logic.Item;
import goedegep.travels.app.logic.ItemGpx;
import goedegep.travels.app.logic.MapImageType;
import goedegep.travels.app.logic.NominatimUtil;
import goedegep.travels.app.logic.OsmAndItems;
import goedegep.travels.app.logic.OsmAndUtil;
import goedegep.travels.app.logic.Ov2Util;
import goedegep.travels.app.logic.PhotoImportResult;
import goedegep.travels.app.logic.PhotosImporter;
import goedegep.travels.app.logic.VacationToHtmlConverter;
import goedegep.travels.app.logic.VacationToHtmlConverterSetting;
import goedegep.travels.app.logic.VacationsKmlConverter;
import goedegep.travels.app.logic.TravelsRegistry;
import goedegep.travels.app.logic.VacationsUtils;
import goedegep.travels.model.BoundingBox;
import goedegep.travels.model.Day;
import goedegep.travels.model.DayTrip;
import goedegep.travels.model.Document;
import goedegep.travels.model.GPXTrack;
import goedegep.travels.model.Location;
import goedegep.travels.model.MapImage;
import goedegep.travels.model.Picture;
import goedegep.travels.model.Travel;
import goedegep.travels.model.Vacation;
import goedegep.travels.model.VacationElement;
import goedegep.travels.model.Vacations;
import goedegep.travels.model.TravelsFactory;
import goedegep.travels.model.TravelsPackage;
import goedegep.travels.model.util.VacationElementReferenceException;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.util.Tuplet;
import goedegep.util.dir.DirectoryChangesMonitoringTask;
import goedegep.util.emf.EMFResource;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import goedegep.util.i18n.TranslationFormatter;
import goedegep.util.img.ImageUtils;
import goedegep.vacations.checklist.model.VacationChecklist;
import goedegep.vacations.checklist.model.VacationChecklistCategoriesList;
import goedegep.vacations.checklist.model.VacationChecklistFactory;
import goedegep.vacations.checklist.model.VacationChecklistLabelsList;
import goedegep.vacations.checklist.model.VacationChecklistPackage;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Window with information about Travels.
 * <p>
 * The travel meta information is stored in a file, of which the name has to be specified via
 * {@link TravelsRegistry#vacationsFileName}.<br/>
 * If the file name isn't specified, a message is shown and this window will be closed.<br/>
 * If the specified file doesn't exist yet, the user is asked whether the file shall be created. Upon a
 * negative reply, the window will also be closed.
 */
public class TravelsWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(TravelsWindow.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private static final ResourceBundle TRANSLATIONS = getBundle(TravelsWindow.class, "TravelsResource");
  private static final TravelsPackage VACATIONS_PACKAGE = TravelsPackage.eINSTANCE;
  private static final TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;
  
  
  private static final double FULL_MAP_ZOOM_LEVEL = 1.7;     // Shows almost complete world map. Determined with trial and error.
  private static final double VACATION_ZOOM_LEVEL = 8.0;     // For the time being hard coded zoom level to show one vacation. Determined with trial and error.
  private static final double PICTURE_ZOOM_LEVEL = 12.0;     // For the time being hard coded zoom level to show a picture. Determined with trial and error.
  private static final double HOME_ZOOM_LEVEL = 8.0;         // The zoom level for showing the home location.
  
  private TravelsService travelsService;
  private TravelsRegistry vacationsRegistry;
  private TravelsAppResourcesFx appResources;
  private TranslationFormatter translationFormatter = new TranslationFormatter(TRANSLATIONS);
  private EMFResource<Vacations> vacationsResource = null;
  private EMFResource<VacationChecklist> vacationChecklistResource = null;
  private Vacations vacations = null;
  private EObjectTreeView treeView = null;
  private Label statusLabel = new Label("");
  
  /**
   * Settings for the {@link vacationToHtmlConverter}.
   */
  private Set<VacationToHtmlConverterSetting> vacationToHtmlConverterSettings;
  
  /**
   * The converter to convert a {@link Vacation} to an HTML document.
   */
  private VacationToHtmlConverter vacationToHtmlConverter;
  private TravelMapView travelMapView;
  private LocationSearchWindow locationSearchWindow = null;
  
  private Browser browser;
  private Tab browserTab;
  private Browser tipsBrowser;
  // This menu item defines the 'edit status'.
  private CheckMenuItem vacationTreeEditableMenuItem;
  private NominatimAPI nominatimAPI = null;
  private ObjectProperty<Travel> selectedTravelProperty = new SimpleObjectProperty<>();
  
  private static int pulseCounter;
  private TextField travelRelatedFilesFolder;
  private Button createOrOpenButton;
  
  private TreeItem<Object> selectedTreeItem;
  private int showLevel;
  

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   */
  public TravelsWindow(CustomizationFx customization, TravelsService travelsService) {
    super(customization, null);
    LOGGER.info("=>");
    
    this.travelsService = travelsService;
    vacationsRegistry = TravelsRegistry.getInstance();
    appResources = (TravelsAppResourcesFx) getResources();
    
    if (vacationsRegistry.getVacationsFileName() == null) {
      statusLabel.setText(TRANSLATIONS.getString("VacationsWindow.statusLabel.noVacationsFileNameMsg"));
      Alert alert = componentFactory.createErrorDialog(
          TRANSLATIONS.getString("VacationsWindow.alertNoVacationsFileName.header"),
          TRANSLATIONS.getString("VacationsWindow.alertNoVacationsFileName.content"));
      
      ButtonType editorButtonType = new ButtonType(TRANSLATIONS.getString("VacationsWindow.alertNoVacationsFileName.editorButton"));
      alert.getButtonTypes().add(editorButtonType);
      
      alert.showAndWait().ifPresent(response -> {
        if (response == editorButtonType) {
          travelsService.showPropertiesEditor(getBundle(TravelsWindow.class, "VacationsPropertyDescriptorsResource"));
        }
      });
      
      return;
    }
    
    if (vacationsRegistry.getVacationChecklistFileName() == null) {
      statusLabel.setText(TRANSLATIONS.getString("VacationsWindow.statusLabel.noVacationChecklistFileNameMsg"));
      Alert alert = componentFactory.createErrorDialog(
          TRANSLATIONS.getString("VacationsWindow.alertNoVacationChecklistFileName.header"),
          TRANSLATIONS.getString("VacationsWindow.alertNoVacationChecklistFileName.content"));
      
      ButtonType editorButtonType = new ButtonType(TRANSLATIONS.getString("VacationsWindow.alertNoVacationsFileName.editorButton"));
      alert.getButtonTypes().add(editorButtonType);
      
      alert.showAndWait().ifPresent(response -> {
        if (response == editorButtonType) {
          travelsService.showPropertiesEditor(getBundle(TravelsWindow.class, "VacationsPropertyDescriptorsResource"));
        }
      });
      
      return;
    }
    
    editUserSettingsIfNeeded();
    
    vacationToHtmlConverterSettings = VacationToHtmlConverterSetting.getDefaultSettings();
    vacationToHtmlConverter = new VacationToHtmlConverter(vacationToHtmlConverterSettings);
    
    createGUI();

    vacationToHtmlConverter = new VacationToHtmlConverter(vacationToHtmlConverterSettings);
    
    vacationsResource = new EMFResource<>(
        TravelsPackage.eINSTANCE, 
        () -> TravelsFactory.eINSTANCE.createVacations(),
        ".xmi",
        true);
    
    try {
      vacations = vacationsResource.load(vacationsRegistry.getVacationsFileName());
    } catch (IOException e) {
      LOGGER.severe("File not found: " + e.getMessage());
      Alert alert = componentFactory.createYesNoConfirmationDialog(
          null,
          translationFormatter.formatText("VacationsWindow.alertVacationsFileNotFound.header", vacationsRegistry.getVacationsFileName()),
          TRANSLATIONS.getString("VacationsWindow.alertVacationsFileNotFound.content"));
      alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
          LOGGER.severe("yes, create file");
          vacations = vacationsResource.newEObject();
          try {
            vacationsResource.save(vacationsRegistry.getVacationsFileName());
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
        ".xmi",
        true);
    
    try {
      vacationChecklistResource.load(vacationsRegistry.getVacationChecklistFileName());
      LOGGER.info(vacationsRegistry.getVacationChecklistFileName());
    } catch (IOException e) {
      LOGGER.severe("File not found: " + e.getMessage());
      Alert alert = componentFactory.createYesNoConfirmationDialog(
          null,
          translationFormatter.formatText("VacationsWindow.alertVacationChecklistFileNotFound.header", vacationsRegistry.getVacationChecklistFileName()),
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
            vacationChecklistResource.save(vacationsRegistry.getVacationChecklistFileName());
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
    
    vacationsResource.dirtyProperty().addListener((_, _, _) -> updateTitle());
        
    vacationsResource.addNotificationListener(this::handleChangesInTheVacationsData);
    
    handleNewTreeItemSelected(null, null);
    updateTipsPane();
    
    setOnCloseRequest(_ -> {
      if (locationSearchWindow != null) {
        locationSearchWindow.close();
      }
    });
    
    show();
    
    selectedTravelProperty.addListener((_, _, _) -> updateTravelFilesFolderBar());


    DirectoryChangesMonitoringTask directoryMonitoringTask = new DirectoryChangesMonitoringTask("D:\\Database");

    directoryMonitoringTask.valueProperty().addListener((_, _, _) -> updateTravelFilesFolderBar());

    Thread directoryMonitoringThread = new Thread(directoryMonitoringTask);
    directoryMonitoringThread.setDaemon(true);
    directoryMonitoringThread.start();
    
    
    
    LOGGER.info("<=");
  }

  public TravelMapView getTravelMapView() {
    return travelMapView;
  }  
  
  public ObjectProperty<Travel> selectedTravelProperty() {
    return selectedTravelProperty;
  }

  /**
   * If the vacations file doesn't exist and/or the vacations folder doesn't exist ask the user whether they should be created, or to change the user settings.
   */
  private void editUserSettingsIfNeeded() {
    File vacationsFile = new File(vacationsRegistry.getVacationsFileName());
    File vacationsFolder = new File(vacationsRegistry.getVacationsFolderName());
    if (!vacationsFile.exists()  ||  !vacationsFolder.exists()) {
      String headerText = null;
      if (!vacationsFile.exists()  &&  !vacationsFolder.exists()) {
        headerText = "The vacations file '" + vacationsRegistry.getVacationsFileName() + "' and the vacations folder '" + vacationsRegistry.getVacationsFolderName() + "' both don't exist";
      } else if (!vacationsFile.exists()) {
        headerText = "The vacations file '" + vacationsRegistry.getVacationsFileName() + "' doesn't exist";
      } else {
        headerText = "The vacations folder '" + vacationsRegistry.getVacationsFolderName() + "' doesn't exist";
      }
      Optional<UserChoice> optionalUserChoice = componentFactory.createChoiceDialog("How to continue?", headerText, "what to do?", UserChoice.SHOW_SETTINGS_EDITOR, UserChoice.values()).showAndWait();
      if (optionalUserChoice.isPresent()) {
        UserChoice userChoice = optionalUserChoice.get();
        switch (userChoice) {
        case SHOW_SETTINGS_EDITOR:
          travelsService.showPropertiesEditor(getBundle(TravelsWindow.class, "TravelsPropertyDescriptorsResource"));
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
    File userPropertiesFile = new File(vacationsRegistry.getUserPropertiesFileName());
    LOGGER.info("userPropertiesFile: " + userPropertiesFile.getAbsolutePath());
    if (!userPropertiesFile.exists()) {
      EMFResource<PropertyGroup> propertiesResource = new EMFResource<>(PropertiesPackage.eINSTANCE, () -> PropertiesFactory.eINSTANCE.createPropertyGroup(), ".xmi");
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
  
  private void updateTravelFilesFolderBar() {
    // Get the selected travel
    Travel travel = selectedTravelProperty.get();
    
    // This determines the travel files folder
    String travelFilesFolder = null;
    if (travel != null) {
      travelFilesFolder = VacationsUtils.getTravelFilesFolder(travel);
    }
    
    // show the folder name
    travelRelatedFilesFolder.setText(travelFilesFolder);
    
    // check if the folder exists exists
    boolean fileExists = false;
    if (travelFilesFolder != null) {
      Path path = Paths.get(travelFilesFolder);
      if (path != null) {
        fileExists = Files.exists(path);
      }
    }
    
    // if it exists, set the button as open button, else set the button as create button
    final String travelFilesFolder2 = travelFilesFolder;
    if (fileExists) {
      createOrOpenButton.setText("Open");
      createOrOpenButton.setOnAction((_) -> {
        try {
          Desktop.getDesktop().open(new File(travelFilesFolder2));
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      });
    } else {
      createOrOpenButton.setText("Create");
      createOrOpenButton.setOnAction((_) -> {
        File folder = new File(travelFilesFolder2);
        folder.mkdir();
      });
    }
  }
  
  private void handleChangesInTheVacationsData(Notification notification) {
    LOGGER.info("=>");
    
    updateTipsPane();
    
    LOGGER.info("<=");
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
  
  /**
   * Get the {@code VacationsTreeView}.
   * 
   * @return the {@code VacationsTreeView}.
   */
  public EObjectTreeView getTreeView() {
    return treeView;
  }
  
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    
    /*
     * Main pane is a BorderPane.
     * North is the menu bar + search bar
     * Center is a SplitPane with a TreeView for the Holidays on the left and TabPane on the right.
     *        There are Tabs for a map and an HTML view.
     * Bottom is a status label
     */
    
    BorderPane mainPane = new BorderPane();
        
    HBox menuAndSearchBox = componentFactory.createHBox(12.0, 12.0);
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    final Pane spacer2 = new Pane();
    spacer2.setMinWidth(50);
    LevelSelectionPanel levelSelectionPanel = new LevelSelectionPanel(customization);
    IntegerProperty levelProperty = levelSelectionPanel.levelProperty();
    
    ChangeListener<Number> cl = new ChangeListener<>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        setShowLevel(newValue.intValue());
      }
    };
    levelProperty.addListener(cl);

    menuAndSearchBox.getChildren().addAll(createMenuBar(), spacer, levelSelectionPanel, createTravelFilesFolderBar(), spacer2, createSearchBar());
    mainPane.setTop(menuAndSearchBox);
    
    SplitPane centerPane = new SplitPane();
    centerPane.setOrientation(Orientation.HORIZONTAL);
    centerPane.setDividerPositions(0.3);

    // MapView
    travelMapView = new TravelMapView(customization, this, this::openLocationSearchWindow);
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
          menuItem.setOnAction((ActionEvent _) -> {
            openLocationSearchWindow();
            locationSearchWindow.reverseGeocodeSearch(newPoint.getLatitude(), newPoint.getLongitude());
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
    
    // Vacations TreeView - centerPane left
    treeView = new TravelsTreeViewCreator(customization)
        .setNewEObjectInitializationFunction(this::initializeNewEObject)
        .setMenuToBeEnabledPredicate(this::isMenuToBeEnabled)
        .setTravelMapView(travelMapView)
        .setUpdateMapImageFileFunction(this::updateMapImageFile)
        .createVacationsTreeView();
    treeView.setEditMode(vacationTreeEditableMenuItem.isSelected());
    treeView.setMinWidth(300);
    treeView.addObjectSelectionListener(this::handleNewTreeItemSelected);
    centerPane.getItems().add(treeView);
    
    travelMapView.addObjectSelectionListener((_, object) -> {
      treeView.selectObjectForObject(object);
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
          updateDocumentView();                    
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
  
  protected void setShowLevel(int newShowLevel) {
   showLevel = newShowLevel;
   handleNewTreeItemSelected(null, selectedTreeItem);
  }

  /**
   * Create a <code>Location</code> and add it as last child to a TreeItem.
   * 
   * @param treeItem The TreeItem to which the new <code>Location</code> is to be added.
   * @param mapPoint A <code>MapPoint</code> which provides the coordinates of the <code>Location</code> to be created.
   */
  private LocationSearchWindow openLocationSearchWindow() {
    if (locationSearchWindow == null) {
      locationSearchWindow = new LocationSearchWindow(customization, getNominatimAPI(), this);
    }
    
    locationSearchWindow.show();
        
    return locationSearchWindow;
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

    // File: Open travels file
    menuItem = componentFactory.createMenuItem("Open travels file");
    menuItem.setOnAction(_ -> openTravelsFile());
    menu.getItems().add(menuItem);

    // File: Save vacations
    menuItem = componentFactory.createMenuItem("Save vacations");
    menuItem.setOnAction(_ -> saveVacations());
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    menu.getItems().add(menuItem);

    // File: Print or export selected vacation
    menuItem = componentFactory.createMenuItem("Print selected vacation, or export as PDF or HTML");
    menuItem.setOnAction(_ -> printOrExportVacation());
    menu.getItems().add(menuItem);

    // File: Print current map
    menuItem = componentFactory.createMenuItem("Print current map");
    menuItem.setOnAction(_ -> print(travelMapView));
    menu.getItems().add(menuItem);
    
    // File: Import photos
    menuItem = componentFactory.createMenuItem("Import photos");
    menuItem.setOnAction(_ -> {
      EObjectTreeItem treeItem = treeView.getSelectedObject();
      Vacation vacation = getTravelForTreeItem(treeItem, VACATIONS_PACKAGE.getVacation());
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
    menuItem.setOnAction(_ -> importVacations());
    menu.getItems().add(menuItem);
    
    // File: Export vacations to KML file
    menuItem = componentFactory.createMenuItem("Export vacations to KML file");
    menuItem.setOnAction(_ -> exportVacationsToKml());
    menu.getItems().add(menuItem);
    
    // File: Export selected vacation to KML file
    menuItem = componentFactory.createMenuItem("Export selected vacation to KML file");
    menuItem.setOnAction(_ -> exportVacationToKml());
    menu.getItems().add(menuItem);
    
    // File: Import information from a kml/kmz file
    menuItem = componentFactory.createMenuItem("Import information from a kml/kmz file");
    menuItem.setOnAction(_ -> importLocationsFromKmlFile());
    menu.getItems().add(menuItem);
    
    // File: Create OsmAnd import file
    menuItem = componentFactory.createMenuItem("Create OsmAnd import file");
    menuItem.setOnAction(_ -> createOsmAndImportFile());
    menu.getItems().add(menuItem);
    
    // File: Create TomTom ov2 file
    menuItem = componentFactory.createMenuItem("Create TomTom ov2 file");
    menuItem.setOnAction(_ -> createTomTomOv2File());
    menu.getItems().add(menuItem);
    
    // File: Edit Properties
    MenuUtil.addMenuItem(menu, "Edit Properties", _ -> travelsService.showPropertiesEditor(getBundle(TravelsWindow.class, "TravelsPropertyDescriptorsResource")));
    
    if (vacationsRegistry.isDevelopmentMode()) {
      // File: Edit Property Descriptors
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", _ -> travelsService.showPropertyDescriptorsEditor());
      
      // File: Map Snapshot popup
      MenuUtil.addMenuItem(menu, "Map Snapshot popup", _ -> showMapSnapshotPopup());
      
      // File: Use demo vacations file
      MenuUtil.addMenuItem(menu, "Use demo vacations file", _ -> useDemoVacationsFile());
    }
    
    menuBar.getMenus().add(menu);
    
    // Edit menu
    menu = new Menu("Edit");

    // Edit: Edit vacations
    vacationTreeEditableMenuItem = new CheckMenuItem("Edit vacations");
    vacationTreeEditableMenuItem.setSelected(false);
    
    vacationTreeEditableMenuItem.setOnAction(_ -> {
        treeView.setEditMode(vacationTreeEditableMenuItem.isSelected());
        travelMapView.setEditMode(vacationTreeEditableMenuItem.isSelected());
    });
    menu.getItems().add(vacationTreeEditableMenuItem);
    
    
    // Menu: Update map image files
    MenuUtil.addMenuItem(menu, "Update map image files", _ -> updateMapImageFiles());
    
    menuBar.getMenus().add(menu);
    
    // Settings menu
    menu = new Menu("Settings");
    
    CheckMenuItem showCoordinatesInDocumentMenuItem = componentFactory.createCheckMenuItem("Show coordinates in Document");
    
    showCoordinatesInDocumentMenuItem.setOnAction(_ -> {
      if (showCoordinatesInDocumentMenuItem.isSelected() ) {
        vacationToHtmlConverterSettings.add(VacationToHtmlConverterSetting.SHOW_LOCATION_COORDINATES);
      } else {
        vacationToHtmlConverterSettings.remove(VacationToHtmlConverterSetting.SHOW_LOCATION_COORDINATES);
      }
      vacationToHtmlConverter.updateSettings(vacationToHtmlConverterSettings);
      updateDocumentView();
    });
    menu.getItems().add(showCoordinatesInDocumentMenuItem);
    
    ToggleGroup toggleGroup = new ToggleGroup();

    RadioMenuItem tableMode = new RadioMenuItem("Show days in a table");
    tableMode.setOnAction((_) -> {
      LOGGER.severe("tableMode selected: " + tableMode.isSelected());
      if (tableMode.isSelected()) {
        vacationToHtmlConverterSettings.remove(VacationToHtmlConverterSetting.PARAGRAPH_MODE);
      } else {
        vacationToHtmlConverterSettings.add(VacationToHtmlConverterSetting.PARAGRAPH_MODE);
      }
      vacationToHtmlConverter.updateSettings(vacationToHtmlConverterSettings);
      updateDocumentView();
    });
    tableMode.setToggleGroup(toggleGroup);
    tableMode.setSelected(true);
    vacationToHtmlConverterSettings.remove(VacationToHtmlConverterSetting.PARAGRAPH_MODE);
    RadioMenuItem paragraphMode = new RadioMenuItem("Show days in paragraphs");
    paragraphMode.setOnAction((_) -> {
      LOGGER.severe("paragraphMode selected: " + paragraphMode.isSelected());
      if (paragraphMode.isSelected()) {
        vacationToHtmlConverterSettings.add(VacationToHtmlConverterSetting.PARAGRAPH_MODE);
      } else {
        vacationToHtmlConverterSettings.remove(VacationToHtmlConverterSetting.PARAGRAPH_MODE);
      }
      vacationToHtmlConverter.updateSettings(vacationToHtmlConverterSettings);
      updateDocumentView();
    });
    paragraphMode.setToggleGroup(toggleGroup);
    menu.getItems().addAll(tableMode, paragraphMode);
    
    menuBar.getMenus().add(menu);
    
    // Checklist menu
    menu = new Menu("Checklist");

    // Checklist: Vacation checklist
    MenuUtil.addMenuItem(menu, "Vacation checklist", _ ->  new TravelChecklistWindow(customization, vacationChecklistResource));

    // Checklist: Edit vacation Checklist
    MenuUtil.addMenuItem(menu, "Edit vacation Checklist", _ -> new TravelChecklistEditor(customization, vacationChecklistResource));
    
    menuBar.getMenus().add(menu);
    
    // Tools menu
    menu = new Menu("Tools");

    // Tools: Check vacations
    MenuUtil.addMenuItem(menu, "Check vacations", _ -> new CheckVacationsWindow(customization, vacations, treeView));
    
    menuBar.getMenus().add(menu);

    // Help menu
    menu = new Menu("Help");

    // Help: Online Manual
    MenuUtil.addMenuItem(menu, "Online Manual", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        showOnlineManual();
      }
    });

    // Help: About
    MenuUtil.addMenuItem(menu, "About", _ -> showHelpAboutDialog());

    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
  private Node createTravelFilesFolderBar() {
    HBox travelFilesFolderBar = componentFactory.createHBox(12.0);
    
    Label label = componentFactory.createLabel("Folder with travel related files:");
    travelRelatedFilesFolder = componentFactory.createTextField(300, "Folder with travel related files");
    createOrOpenButton = componentFactory.createButton("", "");
    
    travelFilesFolderBar.getChildren().addAll(label, travelRelatedFilesFolder, createOrOpenButton);
    
    return travelFilesFolderBar;
  }
  
  private Node createSearchBar() {
    HBox searchBar = componentFactory.createHBox(12.0);
    
    Label label = componentFactory.createLabel("Search:");
    TextField searchTextField = componentFactory.createTextField(null, 300, "Enter text to search for");
    searchTextField.setOnAction((_) -> updateSearch(searchTextField.getText()));
    searchBar.getChildren().addAll(label, searchTextField);
    
    return searchBar;
  }
  
  private void updateSearch(String searchText) {
    if (searchText.isEmpty()) {
      searchText = null;
    }
    
    LOGGER.severe(searchText != null ? ("Searching for: " + searchText) : "No search");
    treeView.setSearchText(searchText);
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
   * @parm source not used, but needed for setting this method as ObjectSelectionListener.
   * @param treeItem the newly selected item in the <code>treeView</code>.
   */
  private void handleNewTreeItemSelected(Object source, TreeItem<Object> treeItem) {
    LOGGER.info("=>");
    
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

    selectedTreeItem = treeItem;
    updateDocumentView();
    LOGGER.info("Before updateMapForTreeItem");
    updateMapForTreeItem(travelMapView);
    
    selectedTravelProperty.setValue(getTravelForTreeItem(treeItem));
    
    LOGGER.info("<=");
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
   */
  private void updateDocumentView() {
    if (!browserTab.isSelected()) {
      return;
    }
    
    String htmlText = TRANSLATIONS.getString("VacationsWindow.noVacationSelectedMsg");
    LOGGER.info("htmlText=" + htmlText);
    
    if (selectedTreeItem != null) {
      Vacation vacation = getTravelForTreeItem(selectedTreeItem, VACATIONS_PACKAGE.getVacation());
      if (vacation != null) {
        htmlText = vacationToHtmlConverter.vacationToHtmlWithEmbedImages(vacation);
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
  private void updateMapForTreeItem(TravelMapView travelMapView) {
    LOGGER.info("=>");
    
    // Remove any existing information.
    travelMapView.clear();
    
    addHomeLocationToVacationsLayer(travelMapView);
    
    if (selectedTreeItem == null) {
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
    List<DayTrip> dayTrips = null;
    List<Vacation> vacationsList = null;
    
    
    if ((pictureDataTuplet = getPictureDataForTreeItem(selectedTreeItem)) != null) {                // Picture
      Picture vacationElementPicture = pictureDataTuplet.getObject1();
      
      if (showLevel == 1) {
        // nothing else to do, only the selected item (a picture in this case) is shown.
      } else {
        vacation = vacationElementPicture.getVacation();
        dayTrip = vacationElementPicture.getDayTrip();
        
        if (showLevel == 2) {
          if (vacation != null) {
            // if the picture is part of a day, show that information, else the complete vacation.
            Day day = vacationElementPicture.getDay();
            if (day != null) {
              addDayToMapView(travelMapView, day, false);
            }
          }
        } else {  // currently only level 3
          if (vacation != null) {
            addVacationToMapView(travelMapView, vacation, false, vacationTreeEditableMenuItem.isSelected());
          }
          if (dayTrip != null) {
            addDayTripToMapView(travelMapView, dayTrip);
          }

        }
      }
      
      WGS84Coordinates coordinates = pictureDataTuplet.getObject2();
      travelMapView.getMapRelatedItemsLayer().showCurrentPhoto(vacationElementPicture.getPictureReference().getFile(), coordinates);
      mapCenter = new MapPoint(coordinates.getLatitude() - 0.05, coordinates.getLongitude() + 0.1);
      zoomLevel = PICTURE_ZOOM_LEVEL;
    } else if ((locationDataTuplet = getLocationDataForTreeItem(selectedTreeItem)) != null) {       // Location
      Location location = locationDataTuplet.getObject1();
      vacation = locationDataTuplet.getObject2();
      addVacationToMapView(travelMapView, vacation, false, vacationTreeEditableMenuItem.isSelected());
      mapCenter = new MapPoint(location.getLatitude(), location.getLongitude());
      zoomLevel = VACATION_ZOOM_LEVEL;
    } else if ((trackDataTuplet = getTrackDataForTreeItem(selectedTreeItem)) != null) {             // Track
      GPXTrack track = trackDataTuplet.getObject1();
      vacation = trackDataTuplet.getObject2();
      addVacationToMapView(travelMapView, vacation, false, vacationTreeEditableMenuItem.isSelected());
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
        } catch (IOException e) {
          LOGGER.severe("Error loading GPX file: " + fileReference.getFile() + ", " + e.getMessage());
        }    	  
      }
    } else if ((vacation = getTravelForTreeItem(selectedTreeItem, VACATIONS_PACKAGE.getVacation())) != null) {                      // vacation
      LOGGER.info("before addVacationToMapView");
      WGS84BoundingBox vacationsLayerBoundingBox = addVacationToMapView(travelMapView, vacation, false, vacationTreeEditableMenuItem.isSelected());
      LOGGER.info("after addVacationToMapView");
      if (vacationsLayerBoundingBox != null) {
        WGS84Coordinates center = vacationsLayerBoundingBox.getCenter();
        mapCenter = new MapPoint(center.getLatitude(), center.getLongitude());
      }
      if (vacationsLayerBoundingBox != null) {
        zoomLevel = MapView.getZoomLevel(vacationsLayerBoundingBox);
      }
    } else if ((vacationsList = getVacationsListForTreeItem(selectedTreeItem)) != null) {            // vacations (the list)
      addVacationsToVacationsLayer(vacationsList);
      showWorldMap();
    } else if ((dayTrip = getDayTripForTreeItem(selectedTreeItem)) != null) {                      // day trip
      WGS84BoundingBox vacationsLayerBoundingBox = addDayTripToMapView(travelMapView, dayTrip);
      if (vacationsLayerBoundingBox != null) {
        WGS84Coordinates center = vacationsLayerBoundingBox.getCenter();
        mapCenter = new MapPoint(center.getLatitude(), center.getLongitude());
      }
      if (vacationsLayerBoundingBox != null) {
        zoomLevel = MapView.getZoomLevel(vacationsLayerBoundingBox);
      }
    } else if ((dayTrips = getDayTripListForTreeItem(selectedTreeItem)) != null) {                  // day trips (the list)
      WGS84BoundingBox vacationsLayerBoundingBox = addDayTripsToVacationsLayer(dayTrips);
      if (vacationsLayerBoundingBox != null) {
        WGS84Coordinates center = vacationsLayerBoundingBox.getCenter();
        mapCenter = new MapPoint(center.getLatitude(), center.getLongitude());
      }
 
    } else if ((vacations = getVacationsForTreeItem(selectedTreeItem)) != null) {
      addVacationsToVacationsLayer(vacations.getVacations());
      showWorldMap();
    } else {
      Object treeItemObject = selectedTreeItem.getValue();
      LOGGER.severe("Don't know what to show for selected treeItem, treeItemObject=" + (treeItemObject != null ? treeItemObject.toString() : "null"));
    }
    
    Object value = selectedTreeItem.getValue();
    travelMapView.selectObject(value);

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
  private Tuplet<Picture, WGS84Coordinates> getPictureDataForTreeItem(TreeItem<Object> treeItem) {
    LOGGER.info("=>");
    Picture vacationElementPicture = getRelatedPictureForTreeItem(treeItem);

    if (vacationElementPicture == null) {
      LOGGER.info("<= (null)");
      return null;
    }

    try {
      WGS84Coordinates pictureCoordinates = getPictureCoordinates(vacationElementPicture);

      if (pictureCoordinates == null) {
        LOGGER.severe("<= (null) (picture has no coordinates)");
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
  private Picture getRelatedPictureForTreeItem(TreeItem<Object> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      LOGGER.severe("<= (null)");
      return null;
    }
    
    Object treeItemObject = treeItem.getValue();
    Picture vacationElementPicture = null;
    
    while (treeItem != null  &&  !(treeItemObject instanceof Picture)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        treeItemObject = treeItem.getValue();
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
   * @throws VacationElementReferenceException if the file, specified in the picture reference, doesn't exist.
   */
  private static WGS84Coordinates getPictureCoordinates(Picture vacationElementPicture) throws VacationElementReferenceException {
    String fileName = vacationElementPicture.getPictureReference().getFile();
    if (!new File(fileName).exists()) {
//      throw new VacationElementReferenceException(vacationElementPicture);
    }
    
    LOGGER.info("before getting geo location");
    WGS84Coordinates coordinates = ImageUtils.getGeoLocation(vacationElementPicture.getPictureReference().getFile());
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
  private Tuplet<Location, Vacation> getLocationDataForTreeItem(TreeItem<Object> treeItem) {
    LOGGER.info("=>");
    
    Location location = getRelatedLocationForTreeItem(treeItem);
    
    if (location == null) {
      LOGGER.info("<= (null)");
      return null;
    }
    
    Vacation vacation = location.getVacation();
    
    if (vacation == null) {
      LOGGER.info("<= (null)");
      return null;
    }
    
    LOGGER.info("<= <location>");
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
  private Location getRelatedLocationForTreeItem(TreeItem<Object> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      return null;
    }
    
    Object treeItemObject = treeItem.getValue();
    Location location = null;
    
    while (treeItem != null  &&  !(treeItemObject instanceof Location)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        treeItemObject = treeItem.getValue();
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
  private Tuplet<GPXTrack, Vacation> getTrackDataForTreeItem(TreeItem<Object> treeItem) {
//    LOGGER.severe("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    LOGGER.info("=>");
    
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
  private GPXTrack getRelatedTrackForTreeItem(TreeItem<Object> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      LOGGER.info("<= (null) (treeItem == null)");
      return null;
    }
    
    Object treeItemObject = treeItem.getValue();
    GPXTrack track = null;
    
    while (treeItem != null  &&  !(treeItemObject instanceof GPXTrack)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        treeItemObject = treeItem.getValue();
      }
    }
    
    if (treeItemObject instanceof GPXTrack) {
      track = (GPXTrack) treeItemObject;
    }

    LOGGER.info("<= track=" + (track != null ? track.toString() : "(null)"));
    return track;
  }
  
  static Travel getTravelForTreeItem(TreeItem<Object> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    LOGGER.info("=>");
    
    if (treeItem == null) {
      LOGGER.info("<= (null)");
      return null;
    }
    
    Object treeItemObject = treeItem.getValue();
    Travel travelForTreeItem = null;
    
    while (treeItem != null  &&  !(treeItemObject instanceof Travel)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        treeItemObject = treeItem.getValue();
      }
    }
    
    if (treeItemObject instanceof Travel travel) {
      travelForTreeItem = travel;
    }

    LOGGER.info("<= travel=" + (travelForTreeItem != null ? travelForTreeItem.toString() : "(null)"));
    return travelForTreeItem;
  }
  
  /**
   * For a given treeItem, try to find the related Travel.
   * <p>
   * If the item is a {@code Travel}, this is the related Travel. Otherwise the parent relations are followed until
   * either a {@code Travel} is found, or that the top of the hierarchy is encountered.
   * If a {@code Travel} is found, that will be the related Travel, otherwise there is no related Travel.
   * 
   * @param treeItem the {@code TreeItem} for which the related Travel is to be found.
   * @return the related Travel, or null if this doesn't exist.
   */
  @SuppressWarnings("unchecked")
  static <T> T getTravelForTreeItem(TreeItem<Object> treeItem, EClass eClass) {
    
    if (treeItem == null) {
      return null;
    }
    
    Object treeItemObject = treeItem.getValue();
    T t = null;
    
    while (treeItem != null  &&  !(treeItemObject instanceof Travel)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        treeItemObject = treeItem.getValue();
      }
    }
    
    if (treeItemObject instanceof Travel travel) {
      EClass treeItemObjectEClass = travel.eClass();
      boolean b = EmfUtil.isInstanceof(treeItemObjectEClass, eClass);
      if (b) {
        t = (T) travel;
      }
    }

    return t;
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
  static DayTrip getDayTripForTreeItem(TreeItem<Object> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      LOGGER.info("<= (null)");
      return null;
    }
    
    Object treeItemObject = treeItem.getValue();
    DayTrip dayTrip = null;
    
    while (treeItem != null  &&  !(treeItemObject instanceof DayTrip)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        treeItemObject = treeItem.getValue();
      }
    }
    
    if (treeItemObject instanceof DayTrip) {
      dayTrip = (DayTrip) treeItemObject;
    }

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
  private static Day getDayForTreeItem(TreeItem<Object> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      return null;
    }
    
    Object treeItemObject = treeItem.getValue();
    
    while (treeItem != null  &&  !(treeItemObject instanceof Day)) {
      treeItem = treeItem.getParent();
      LOGGER.info("after getParent() treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
      if (treeItem != null) {
        treeItemObject = treeItem.getValue();
      }
    }
    
    if (treeItemObject instanceof Day day) {
      LOGGER.info("<= day=" + day.toString());
      return day;
    }

    LOGGER.info("<= null");
    return null;
  }
  
  static EObject getFirstAncesterEObject(EObjectTreeItem treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    Object value = treeItem.getValue();
    
    while (treeItem != null  &&  !(value instanceof EObject)) {
      treeItem = (EObjectTreeItem) treeItem.getParent();
      if (treeItem != null) {
        value = treeItem.getValue();
      }
    }
    
    if (value instanceof EObject eObject) {
      return eObject;
    } else {
      return null;
    }
  }
  
  /**
   * For a given treeItem, check whether it is the vacations attribute of a Vacations object. If so, return the vacations list, else return null.
   * 
   * @param treeItem the <code>TreeItem</code> to check.
   * @return the vacations list, or null.
   */
  @SuppressWarnings("unchecked")
  private List<Vacation> getVacationsListForTreeItem(TreeItem<Object> treeItem) {
//    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    LOGGER.info("=>");
    
    if (treeItem == null) {
      LOGGER.severe("<= (null)");
      return null;
    }
    
    List<Vacation> vacations = null;
    Object treeItemContent = treeItem.getValue();;
    
    if ((((EObjectTreeItem) treeItem).getEStructuralFeature() != null)  &&
        ((EObjectTreeItem) treeItem).getEStructuralFeature().equals(VACATIONS_PACKAGE.getVacations_Vacations())) {
      vacations = (List<Vacation>) treeItemContent;
    }
    
    LOGGER.info("<= vacations=" + (vacations != null ? vacations.toString() : "(null)"));
    return vacations;
  }
  
  /**
   * For a given treeItem, check whether it is the dayTrips attribute of a Vacations object. If so, return the day trips list, else return null.
   * 
   * @param treeItem the <code>TreeItem</code> to check.
   * @return the day trips list, or null.
   */
  @SuppressWarnings("unchecked")
  private List<DayTrip> getDayTripListForTreeItem(TreeItem<Object> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
//    LOGGER.info("=>");
    
    if (treeItem == null) {
      LOGGER.severe("<= (null)");
      return null;
    }
    
    List<DayTrip> dayTrips = null;
    Object treeItemContent = treeItem.getValue();;
    
    if ((((EObjectTreeItem) treeItem).getEStructuralFeature() != null)  &&
        ((EObjectTreeItem) treeItem).getEStructuralFeature().equals(VACATIONS_PACKAGE.getVacations_DayTrips())) {
      dayTrips = (List<DayTrip>) treeItemContent;
    }
    
    LOGGER.info("<= dayTrips=" + (dayTrips != null ? dayTrips.toString() : "(null)"));
    return dayTrips;
  }
  
  /**
   * For a given treeItem, check whether it is the vacations attribute of a Vacations object. If so, return the vacations list, else return null.
   * 
   * @param treeItem the <code>TreeItem</code> to check.
   * @return the vacations list, or null.
   */
  private Vacations getVacationsForTreeItem(TreeItem<Object> treeItem) {
    LOGGER.info("=> treeItem=" + (treeItem != null ? treeItem.toString() : "(null)"));
    
    if (treeItem == null) {
      LOGGER.info("<= (null)");
      return null;
    }
    
    Vacations vacations = null;
    Object object = treeItem.getValue();
    
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
      addVacationToMapView(travelMapView, vacation, true, false);
    }
  }
  
  private WGS84BoundingBox addDayTripsToVacationsLayer(List<DayTrip> dayTrips) {
    WGS84BoundingBox vacationsLayerBoundingBox = null;

    for (DayTrip dayTrip: dayTrips) {
      WGS84BoundingBox tripBoundingBox = addDayTripToMapView(travelMapView, dayTrip);
      if (vacationsLayerBoundingBox == null) {
        vacationsLayerBoundingBox = tripBoundingBox;
      } else {
        vacationsLayerBoundingBox.extend(tripBoundingBox);
      }
    }
    
    return vacationsLayerBoundingBox;
  }
  
  /**
   * Add the home location, if available, to the vacations layer.
   * 
   * @return a <code>MapPoint</code> with the coordinates of the home location, or null if there is no home location.
   */
  private void addHomeLocationToVacationsLayer(TravelMapView travelMapView) {
    LOGGER.info("=>");
        
    if ((vacations != null)  &&  vacations.getHome() != null) {
      Location homeLocation = vacations.getHome();
      if (homeLocation.isSetLatitude()  &&  homeLocation.isSetLongitude()) {
        travelMapView.getMapRelatedItemsLayer().addLocation(homeLocation);
      }
    }
        
    LOGGER.info("<=");
  }
  
  /**
   * Add all relevant information of a {@code Vacation} to a map view.
   * 
   * @param travelMapView the {@code TravelMapView} to which the vacation information is to be added.
   * @param vacation the {@code Vacation} of which the locations are to be added.
   * @param stayedAtOnly if {@code true} only the 'stayed at' locations are added.
   * @param drawBoundingBox if {@code true} a bounding box is drawn around the added items, if {@code false} no bounding box is drawn.
   * @return a {@code WGS84BoundingBox} around all items added to the map view.
   */
  WGS84BoundingBox addVacationToMapView(TravelMapView travelMapView, Vacation vacation, boolean stayedAtOnly, boolean drawBoundingBox) {
    WGS84BoundingBox vacationWGS84BoundingBox = null;
    
    for (VacationElement vacationElement: vacation.getElements()) {
      WGS84BoundingBox wgs84BoundingBox = addVacationElementToMapView(travelMapView, vacationElement, stayedAtOnly, null);
      vacationWGS84BoundingBox = WGS84BoundingBox.extend(vacationWGS84BoundingBox, wgs84BoundingBox);
    }
    
    if (!stayedAtOnly) {
      try {
        List<List<WGS84Coordinates>> locationsConnectingLines = VacationsUtils.getLocationConnectingLines(vacation);
        if (!locationsConnectingLines.isEmpty()) {
          travelMapView.getMapRelatedItemsLayer().addLocationsVisitedPolyLines(FXCollections.observableList(locationsConnectingLines));
        }
      } catch (FileNotFoundException e) {
        LOGGER.severe("File not found");
        //      componentFactory.createErrorDialog("File not found", e.toString()).showAndWait();
      }
    }
    
    if (drawBoundingBox  &&  (vacationWGS84BoundingBox != null)) {
      travelMapView.getMapRelatedItemsLayer().addBoundingBox(vacationWGS84BoundingBox);
    }
    
    return vacationWGS84BoundingBox;
  }

  /**
   * Add all relevant information of a {@code Vacation} to a map view.
   * 
   * @param travelMapView the {@code TravelMapView} to which the information is to be added.
   * @param day the {@code Day} of which the information is to be added.
   * @return a {@code WGS84BoundingBox} around all items added to the map view.
   */
  private WGS84BoundingBox addDayToMapView(TravelMapView travelMapView, Day day) {
    WGS84BoundingBox totalWGS84BoundingBox = null;
        
    for (VacationElement vacationElement: day.getChildren()) {
      WGS84BoundingBox wgs84BoundingBox = addVacationElementToMapView(travelMapView, vacationElement, false, null);
      totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
    }
        
    try {
      List<List<WGS84Coordinates>> locationsConnectingLines = VacationsUtils.getLocationConnectingLines(day);
      if (!locationsConnectingLines.isEmpty()) {
        travelMapView.getMapRelatedItemsLayer().addLocationsVisitedPolyLines(FXCollections.observableList(locationsConnectingLines));
      }
    } catch (FileNotFoundException e) {
      LOGGER.severe("File not found");
      //      componentFactory.createErrorDialog("File not found", e.getMessage()).showAndWait();
    }

    return totalWGS84BoundingBox;
  }

  /**
   * Add all relevant information of a {@code DayTrip} to a map view.
   * 
   * @param travelMapView the {@code TravelMapView} to which the information is to be added.
   * @param dayTrip the {@code DayTrip} of which the information is to be added.
   * @return a {@code WGS84BoundingBox} around all items added to the map view.
   */
  private WGS84BoundingBox addDayTripToMapView(TravelMapView travelMapView, DayTrip dayTrip) {
    WGS84BoundingBox totalWGS84BoundingBox = null;
        
    for (VacationElement vacationElement: dayTrip.getElements()) {
      WGS84BoundingBox wgs84BoundingBox = addVacationElementToMapView(travelMapView, vacationElement, false, null);
      totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
    }

    try {
      List<List<WGS84Coordinates>> locationsConnectingLines = VacationsUtils.getLocationConnectingLines(dayTrip);
      if (!locationsConnectingLines.isEmpty()) {
        travelMapView.getMapRelatedItemsLayer().addLocationsVisitedPolyLines(FXCollections.observableList(locationsConnectingLines));
      }
    } catch (FileNotFoundException e) {
      LOGGER.severe("File not found");
      //      componentFactory.createErrorDialog("File not found", e.getMessage()).showAndWait();
    }

    return totalWGS84BoundingBox;
  }
  
  /**
   * Add all relevant information of a day to a map view.
   * 
   * @param vacation the <code>Vacation</code> of which the <code>Location</code>s are to be added.
   * @param stayedAtOnly if <code>true</code> only the 'stayed at' locations are added.
   * @return a {@code WGS84BoundingBox} around all items added to the map view.
   */
  WGS84BoundingBox addDayToMapView(TravelMapView travelMapView, Day day, boolean stayedAtOnly) {
    WGS84BoundingBox boundingBox = addDayToMapView(travelMapView, day);

    return boundingBox;
  }
  
  /**
   * Handle a single {@code VacationElement} for possibly adding it to a map view.
   * <p>
   * The following types of elements are added to the map view:
   * <ul>
   * <li>Location: if it is a 'stayed at' location, or if {@code stayedAtOnly} is false.</li>
   * <li>Picture: if {@code stayedAtOnly} is false, and if the picture has coordinates, or if the picture belongs to a location with coordinates.</li>
   * <li>GPXTrack: if {@code stayedAtOnly} is false, and if the track file exists.</li>
   * </ul>
   * 
   * @param travelMapView the {@code TravelMapView} to which the vacation information is to be added.
   * @param vacationElement the element to be added.
   * @param stayedAtOnly if true, only 'stayed at' locations are added.
   * @param totalWGS84BoundingBox a bounding box which has to be extended with the bounding box of the {@code vacationElement}.
   * @return the extended bounding box.
   */
  private WGS84BoundingBox addVacationElementToMapView(TravelMapView travelMapView, VacationElement vacationElement, boolean stayedAtOnly, WGS84BoundingBox totalWGS84BoundingBox) {
    WGS84BoundingBox wgs84BoundingBox = null;
    
    switch (vacationElement) {
    case Location location -> wgs84BoundingBox = addLocationToMapView(travelMapView, location, stayedAtOnly);
    case Picture picture -> wgs84BoundingBox = addPictureToMapView(travelMapView, picture, stayedAtOnly);
    case GPXTrack gpxTrack -> wgs84BoundingBox = addGPXTrackToMapView(travelMapView, gpxTrack, stayedAtOnly);
    default -> {}  // no action for other types of VacationElement
    }
    
    totalWGS84BoundingBox = WGS84BoundingBox.extend(totalWGS84BoundingBox, wgs84BoundingBox);
        
    for (VacationElement childElement: vacationElement.getChildren()) {
      totalWGS84BoundingBox = addVacationElementToMapView(travelMapView, childElement, stayedAtOnly, totalWGS84BoundingBox);
    }
    
    LOGGER.info("<= totalWGS84BoundingBox: " + (totalWGS84BoundingBox != null ? totalWGS84BoundingBox.toString() : "(null)"));
    return totalWGS84BoundingBox;
  }
  
  /**
   * Add a {@code Location} to a map view.
   * 
   * @param travelMapView the {@code TravelMapView} to which the location is to be added.
   * @param location the {@code Location} to be added.
   * @param stayedAtOnly if true, only a 'stayed at' location is added.
   * @return the bounding box of the {@code location}, which can be null.
   */
  private WGS84BoundingBox addLocationToMapView(TravelMapView travelMapView, Location location, boolean stayedAtOnly) {
    WGS84BoundingBox wgs84BoundingBox = null;
    
    if (!stayedAtOnly  ||  location.isStayedAtThisLocation()) {
      wgs84BoundingBox = travelMapView.getMapRelatedItemsLayer().addLocation(location);
    }
    
    return wgs84BoundingBox;
  }
  
  /**
   * Add a {@code Picture} to a map view.
   * 
   * @param travelMapView the {@code TravelMapView} to which the picture is to be added.
   * @param picture the {@code Picture} to be added.
   * @param stayedAtOnly if true, only a 'stayed at' location is added.
   * @return the bounding box of the {@code picture}, which can be null.
   */
  private WGS84BoundingBox addPictureToMapView(TravelMapView travelMapView, Picture picture, boolean stayedAtOnly) {
    WGS84BoundingBox wgs84BoundingBox = null;
    
    if (!stayedAtOnly) {
      try {
        FileReference fileReference = picture.getPictureReference();
        if (fileReference != null) {
          String fileName = fileReference.getFile();
          if ((fileName != null)  &&  !fileName.isEmpty()) {
            // Note: The photo folder is not available on all computers of a user, so ignore references that don't exist.
            File file = new File(fileName);
            if (!file.exists()) {
              return wgs84BoundingBox;
            }

            WGS84Coordinates coordinates = getPictureCoordinates(picture);
            if (coordinates != null) {
              String text = fileReference.getTitle();
              if ((text == null)  ||  text.isEmpty()) {
                text = fileName;
              }
              wgs84BoundingBox = travelMapView.getMapRelatedItemsLayer().addPhoto(coordinates, text, fileName);
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
    }
    
    return wgs84BoundingBox;
  }
  
  /**
   * Add a {@code GPXTrack} to a map view.
   * 
   * @param travelMapView the {@code TravelMapView} to which the GPX track is to be added.
   * @param gpxTrack the {@code GPXTrack} to be added.
   * @param stayedAtOnly if true, only a 'stayed at' location is added.
   * @return the bounding box of the {@code gpxTrack}, which can be null.
   */
  private WGS84BoundingBox addGPXTrackToMapView(TravelMapView travelMapView, GPXTrack gpxTrack, boolean stayedAtOnly) {
    WGS84BoundingBox wgs84BoundingBox = null;
    
    if (!stayedAtOnly) {
      FileReference bestandReferentie = gpxTrack.getTrackReference();
      if ((bestandReferentie != null)  &&  (bestandReferentie.getFile() != null)  &&  !bestandReferentie.getFile().isEmpty()) {
        EMFResource<DocumentRoot> gpxResource = GpxUtil.createEMFResource();
        try {
          gpxResource.load(bestandReferentie.getFile());
          DocumentRoot documentRoot = gpxResource.getEObject();
          GpxType gpxType = documentRoot.getGpx();
          wgs84BoundingBox = travelMapView.getGpxLayer().addGpx(bestandReferentie.getTitle(), bestandReferentie.getFile(), gpxType);
        } catch (IOException e) {
          LOGGER.severe("File not found: " + bestandReferentie.getFile());
        }
      }
    }
    
    return wgs84BoundingBox;
  }

  /**
   * Determine the 'initial directory' to use in the FolderChooser for choosing the Pictures folder.
   * <p>
   * If the 'Pictures' attribute already has a valid value, this is returned.
   * Otherwise the folder is constructed from: the main vacation photos folder and the vacation Id.
   * If any of the required values (main vacation photos folder, vacation date and vacation title) isn't available, null is returned.
   * 
   * @param eObjectTreeCell the {@code EObjectTreeCell} for the 'Pictures' attribute of a Vacation.
   * @return the 'initial directory' to use in the FolderChooser for choosing the Pictures folder, or null if this folder cannot be determined.
   */
  static String getInitialPhotoFolderName(EObjectTreeCell eObjectTreeCell) {
    if (eObjectTreeCell == null) {
      LOGGER.severe("eObjectTreeCell is null");
      return null;
    }
    
    Object eObjectTreeItemContent = eObjectTreeCell.getItem();
    
    LOGGER.severe("eObjectTreeItemContent=" + eObjectTreeItemContent);
    
    Vacation vacation = getTravelForTreeItem(eObjectTreeCell.getTreeItem(), VACATIONS_PACKAGE.getVacation());
    LOGGER.severe("vacation=" + vacation.getId());
    Path vacationFolderPath = VacationsUtils.getVacationPhotosFolderPath(vacation);
    if (vacationFolderPath != null) {
      LOGGER.severe("vacationFolderPath=" + vacationFolderPath.toString());
      return vacationFolderPath.toString();
    }
    
    return null;
  }
  
  /**
   * Get the best choice for the initial folder for a FolderChooser for selecting the file of a FileReference.
   * <p>
   * If the tree cell is the file of a FileReference of a Picture, the best choice is the pictures folder for the related vacation.
   * Else the best choice is the vacation folder of the related vacation.
   * 
   * @param eObjectTreeCell the {@code EObjectTreeCell} for which the best initial folder is requested.
   * @return the best initial folder, or null if this can't be determined.
   */
  static String getReferredFilesFolder(EObjectTreeCell eObjectTreeCell) {
    EObjectTreeItem treeItem = (EObjectTreeItem) eObjectTreeCell.getTreeItem();
    Vacation vacation = getTravelForTreeItem(treeItem, VACATIONS_PACKAGE.getVacation());
    
    if (vacation == null) {
      LOGGER.severe("No vacation found for: " + treeItem.toString());
      return null;
    } else {
      LOGGER.severe("Vacation is: " + vacation.getTitle());
    }
    
    // The parent is the FileReference, the grand parent is either a Picture, or any other element.
    EObjectTreeItem parentTreeItem = (EObjectTreeItem) treeItem.getParent();
    if (parentTreeItem != null) {
      Object parentContent = parentTreeItem.getValue();
      LOGGER.severe("parent content: " + (parentContent != null ? parentContent.toString() : "<nul>"));
      EObjectTreeItem grandparentTreeItem = (EObjectTreeItem) parentTreeItem.getParent();
      if (grandparentTreeItem != null) {
        Object object = grandparentTreeItem.getValue();
        LOGGER.info("Class=" + object.getClass().getName());
        if (object instanceof Picture) {
          Path path = VacationsUtils.getVacationPhotosFolderPath(vacation);
          return path != null ? path.toAbsolutePath().toString() : null;
        } else {
          return VacationsUtils.getTravelFilesFolder(vacation);      
        }
      } else {
        LOGGER.severe("grandparentTreeItem is null");
      }
    } else {
      LOGGER.severe("parentTreeItem is null");
    }
    
    return null;
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
  static String generateTextForGpxTrack(EObject eObject) {
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
          if (metadataType != null) {
            String name = metadataType.getName();
            if (name != null  &&  !name.isEmpty()) {
              text = name;
            }
          }
        } catch (IOException e) {
          text = "(file doesn't exist)";
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
  
  static Image generateIconForGpxTrack(Object object) {
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
        } catch (IOException e) {
          // no action, no file so no activity
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

  public static Image scale(Image source, double targetWidth, double targetHeight) {
    ImageView imageView = new ImageView(source);
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(targetWidth);
    imageView.setFitHeight(targetHeight);
    return imageView.snapshot(null, null);
  }
  
  /**
   * Open a (non standard) travels file
   */
  private void openTravelsFile() {
    FileChooser fileChooser = componentFactory.createFileChooser("Open a travels file");
    if (vacationsRegistry.getVacationsFolderName() != null) {
      File travelsFolder = new File(vacationsRegistry.getVacationsFolderName());
      fileChooser.setInitialDirectory(travelsFolder);
    }
    File travelsFile = fileChooser.showOpenDialog(locationSearchWindow);
    if (travelsFile != null) {
      try {
        vacations = vacationsResource.load(travelsFile.getAbsolutePath());
                
        if (vacations != null) {
          treeView.setEObject(vacations);
          travelMapView.clear();
          updateTitle();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Save the vacations information to the related file.
   */
  private void saveVacations() {
    if (vacationsResource != null) {
      try {
        vacationsResource.save();
        statusLabel.setText("Vacations saved to '" + vacationsResource.getURI().toFileString() + "'");
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
  private void printOrExportVacation() {
    try {
      EObjectTreeItem treeItem = treeView.getSelectedObject();
      Vacation vacation = getTravelForTreeItem(treeItem, VACATIONS_PACKAGE.getVacation());
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
    vacationsImporter.importVacations(vacationsRegistry.getVacationsFolderName(), vacationsRegistry.getVacationPicturesFolderName());
    treeView.setEObject(vacations);  // trick to update the treeView
  }
  
  /**
   * Generate a KML file from the vacations.
   */
  private void exportVacationsToKml() {
    // Let user select a file to save to.
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Kml file");
    FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("KML files (*.kml)", "*.kml");
    fileChooser.getExtensionFilters().add(extensionFilter);
    fileChooser.setSelectedExtensionFilter(extensionFilter);
    File file = fileChooser.showSaveDialog(this);
    LOGGER.severe("Creating kml file: " + file.getAbsolutePath());
        
    // Generate the file using the VacationsKmlConverter
    VacationsKmlConverter vacationsKmlConverter = new VacationsKmlConverter();
    try {
      vacationsKmlConverter.createKmlForVacations(vacations, file);
      statusLabel.setText("KML file " + file.getAbsolutePath() + " created");
    } catch (FileNotFoundException e) {
      statusLabel.setText("Failed to write to file: " + file.getAbsolutePath());
    }
  }
  
  /**
   * Generate a KML file for the currently selected vacation.
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
    Vacation vacation = getTravelForTreeItem(treeItem, VACATIONS_PACKAGE.getVacation());
    if (vacation == null) {
      statusLabel.setText("No vacation selected");
      return;
    }
    
    // Generate the file using the VacationsKmlConverter
    VacationsKmlConverter vacationsKmlConverter = new VacationsKmlConverter();
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

//    // check that currently selected tree item is of type Elements
//    EObjectTreeItem currentlySelectedItem = treeView.getSelectedObject();
//    if (!treeView.treeItemIsLocationsList(currentlySelectedItem)) {
//      componentFactory.createInformationDialog(
//          "No locations list selected",
//          "In order to import locations from a KML/KMZ file, you have to select an item in the vacations tree where you can add locations",
//          "Please select a list (typically 'Elements') where you can add locations and try again").showAndWait();
//      return;
//    }
    
//    // Let the user select a file to read from
//    FileChooser fileChooser = new FileChooser();
//    fileChooser.setTitle("Read Kml file");
//    FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("KML files", "*.kml", "*.kmz");
//    fileChooser.getExtensionFilters().add(extensionFilter);
//    fileChooser.setSelectedExtensionFilter(extensionFilter);
//    File file = fileChooser.showOpenDialog(this);
//    
//    if (file == null) {
//      return;
//    }
//    LOGGER.severe("Reading kml file: " + file.getAbsolutePath());
    
    // Use the KmlFileImporter to get the vacation elements.
//    KmlFileImporter kmlFileImporter = new KmlFileImporter.Builder(file)
//        .setVacationFolder(VacationsUtils.getVacationFolder(getTravelForTreeItem(treeView.getSelectedObject())))
//        .build();
//    List<Tuplet<VacationElement, Object>> vacationElements = kmlFileImporter.getLocationsFromKmlFile(file);
    new KmlFileImportWindow(customization, this, getNominatimAPI());
    // Add the locations to the currently selected tree item.
//    EObjectTreeItem treeItem = (EObjectTreeItem) treeView.getSelectedObject();
//    Object object = treeItem.getValue();
//    @SuppressWarnings("unchecked")
//    List<VacationElement> elements = (List<VacationElement>) object;
//    for (Tuplet<VacationElement, Object> tuplet: vacationElements) {
//      VacationElement vacationElement = tuplet.getObject1();
//      LOGGER.severe("Handling: " + vacationElement.toString());
//      if (vacationElement instanceof Location location) {
//        if (locationIsHomeLocation(location)) {
//          LOGGER.severe("Skipping home location");
//          continue;
//        }
//        
//        KmlFileImportWindow dialog = new KmlFileImportWindow(customization, this, poiIcons, location);
//        dialog.showAndWait();
//        LOGGER.severe("Closed");
//      } else if (vacationElement instanceof GPXTrack gpxTrack) {
//        GPXTrackPreviewWindow dialog = new GPXTrackPreviewWindow(customization, this, poiIcons, gpxTrack, (DocumentRoot) tuplet.getObject2());
//        dialog.showAndWait();
//      } else {
//        throw new RuntimeException("Unsupported VacationElement subtype: " + vacationElement.getClass().getName());
//      }
//      elements.add(vacationElement);
//    }
//    treeItem.rebuildChildren();
  }
  
  /**
   * Create an OsmAnd import file for a node in the Vacations database.
   * <p>
   * Typical use of this method is to first select a Vacation and then call this method.
   * However this method works on any node in the Travels database.
   * An OsmAnd import file is created containing a favourite for each location below the selected node,
   * and all GPX tracks below the selected node.
   * An OsmAnd import file is a zip file with:
   * <ul>
   *   <li>A gpx file per favorite category, e.g. favorites-CITY.gpx for cities</li>
   *   <li>A folder '_/tracks/import' containing the GPX track to import. 
   * </ul>
   * If the selected no isn't an EObject, the parent node is used (and so on until an EOBject node is encountered).
   * If no node is selected, a popup is shown to inform the user about this and than this method directly returns.
   */
  private void createOsmAndImportFile() {
    
    // Get the selected node in vacations
    EObject eObject = null;
    TreeItem<Object> treeItem = treeView.getSelectedObject();
    if (treeItem != null) {
      Object selectedObject = treeItem.getValue();
      while (selectedObject != null  &&  !(selectedObject instanceof EObject)) {
        treeItem = treeItem.getParent();  // Note: treeItem cannot be null, as the root is an EObject.
        selectedObject = treeItem.getValue();
      }
      if (selectedObject instanceof EObject selectedEObject) {
        eObject = selectedEObject;
      }
    }
    
    if (eObject == null) {
      Alert alert = componentFactory.createInformationDialog("No item selected for which to generate a favourites file",
          "You have to select a node in the Travels tree to create a favourites file.",
          "Press OK, select a node and try again.");
      alert.showAndWait();
      return;
    }
    
    // Choose file to save to.
    FileChooser fileChooser = componentFactory.createFileChooser("File to write OsmAnd import file to");
    String initialFileName = "OsmAndImport.osf";
    if (eObject instanceof Vacation vacation) {
      initialFileName = vacation.getTitle() + ".osf";
    }
    fileChooser.setInitialFileName(initialFileName);
    fileChooser.getExtensionFilters().add(new ExtensionFilter("OsmAnd import file", "*.osf"));
    File file = fileChooser.showSaveDialog(this);
    if (file == null) {
      return;
    }
    
    // We can safely delete the file, because the user agreed to this in the FileChooser.
    if (file.exists()) {
      file.delete();
    }
    
    // create/get an in memory file system (imfs)
    // Create an in-memory file system
    @SuppressWarnings("resource")
    FileSystem imfs = Jimfs.newFileSystem(Configuration.unix());
    
    // Datastructure for creating the contents file (items.json)
    OsmAndItems osmAndItems = new OsmAndItems();
    List<Item> items = new ArrayList<>();
    osmAndItems.items = items;
    
    // Create the favorites.gpx file for the locations
    String group = null;
    if (eObject instanceof Vacation vacation) {
      group = vacation.getTitle();
    }
    String favoritesFileName = "/favorites";
    if (eObject instanceof Vacation vacation) {
      favoritesFileName = favoritesFileName + "-" + vacation.getTitle();
    }
    favoritesFileName = favoritesFileName + ".gpx";
    
    Path favoritesFilePath = imfs.getPath(favoritesFileName);
    Map<String, List<String>> categoryToNameMap = OsmAndUtil.createFavouritesFile(eObject, group, favoritesFilePath);
    StringBuilder buf = new StringBuilder();
    for (String category: categoryToNameMap.keySet()) {
      List<String> locationsForCategory = categoryToNameMap.get(category);
      buf.append("Category: ").append(category).append(NEWLINE);
      for (String location: locationsForCategory) {
        buf.append("    ").append(location).append(NEWLINE);
      }
    }
    
    Item item = new Item();
    item.type = "FAVOURITES";
    item.file = favoritesFilePath.getFileName().toString();
    items.add(item);
    
    Alert alert = componentFactory.createInformationDialog("Favourites file created",
        buf.toString(),
        "Press OK to continue.");
    alert.showAndWait();
    
    // Create the folder for the GPX tracks
    Path gpxTracksFolderPath = imfs.getPath("/_/tracks/import");
    try {
      Files.createDirectories(gpxTracksFolderPath);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    // Copy the GPX tracks to that folder
    TreeIterator<EObject> vacationIterator = eObject.eAllContents();
    
    while (vacationIterator.hasNext()) {
      EObject currentEObject = vacationIterator.next();
      if (currentEObject instanceof GPXTrack gpxTrack) {
        FileReference fileReference = gpxTrack.getTrackReference();
        if (fileReference != null) {
          String fileName = fileReference.getFile();
          if (fileName != null) {
            Path sourcePath = Paths.get(fileName);
            File sourceFile = new File(fileName);
            Path destinationPath = gpxTracksFolderPath.resolve(sourceFile.getName());
            try {
              Files.copy(sourcePath, destinationPath);
              
              ItemGpx itemGpx = new ItemGpx();
              itemGpx.type = "GPX";
              itemGpx.file = destinationPath.toString();
              items.add(itemGpx);
            } catch (IOException e) {
              LOGGER.severe("Failed to copy " + sourcePath.toString() + " to " + destinationPath.toString());
            }
          }
        }
      }
    }
    
    // Create contents file (items.json)
    Gson gson = new Gson();
    String itemsJsonString = gson.toJson(osmAndItems);
    LOGGER.info("itemsJsonString: " + itemsJsonString);
    try {
      Path itemsFilePath = imfs.getPath("/items.json");
      Files.write(itemsFilePath, itemsJsonString.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }    
    
    // Create a zip file from the imfs folder
    Path zipFile = Paths.get(file.getAbsolutePath());
    Path folderToZip = imfs.getPath("/");
    try {
      FileUtils.createZipFileForFolder(zipFile, folderToZip);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    // Delete the imfs folder
    try {
      imfs.close();
    } catch (IOException e) {
      e.printStackTrace();
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
    TreeItem<Object> treeItem = treeView.getSelectedObject();
    Object selectedObject = treeItem.getValue();
    if (selectedObject instanceof EObject) {
      EObject eObject = (EObject) selectedObject;
      
      
      // Generate file
      Ov2Util.createOv2File(eObject, file.getAbsolutePath());
    }
  }
  
  /**
   * Open a browser with the online manual.
   */
  private void showOnlineManual() {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
      try {
        Desktop.getDesktop().browse(new URI("https://petersdigitallife.nl/myworld-user-manual/vacations-user-manual/"));
      } catch (IOException e) {
        e.printStackTrace();
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Show a dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About " + vacationsRegistry.getApplicationName(),
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null, 
        vacationsRegistry.getShortProductInfo() + NEWLINE +
        "Version: " + vacationsRegistry.getVersion() + NEWLINE +
        vacationsRegistry.getCopyrightMessage() + NEWLINE +
        "Author: " + vacationsRegistry.getAuthor() + 
        (vacationsRegistry.isDevelopmentMode() ? (NEWLINE + NEWLINE + "Running in development mode!") : "")
        )
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
    } catch (IOException e) {
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
  
  /**
   * Fill a newly created EObject with available information.
   * 
   * @param eObject the newly created EObject
   * @param treeItem the <code>EObjectTreeItem</code> to which the <code>eObject</code> is added.
   */
  private void initializeNewEObject(EObject eObject, EObjectTreeItem treeItem) {
    LOGGER.info("=> eObject=" + eObject + ", treeItem=" + treeItem);
    
    switch (eObject) {
    case MapImage mapImage -> initializeMapImage(mapImage, treeItem);
    default -> {} // no action, assume no initialization needed
    }
    
    if (eObject instanceof MapImage) {
    } else if (eObject instanceof Document document) {
      FileReference fileReference = TYPES_FACTORY.createFileReference();
      document.setDocumentReference(fileReference);
    } else if (eObject instanceof Picture picture) {
      FileReference fileReference = TYPES_FACTORY.createFileReference();
      picture.setPictureReference(fileReference);
    } else if (eObject instanceof GPXTrack GpxTrack) {
      FileReference fileReference = TYPES_FACTORY.createFileReference();
      GpxTrack.setTrackReference(fileReference);
    }
  }

  /**
   * Initialize a newly created MapImage.
   * <p>
   * The user is asked for a title, the image dimensions are set to the current map view dimensions,
   * the center latitude and longitude and zoom level are set to the current map view values.
   * The file name is set to a file in the travel folder of the vacation containing the MapImage.
   * Finally a jpg file is created with a snapshot of the current map view.
   * 
   * @param mapImage the newly created MapImage
   * @param treeItem the <code>EObjectTreeItem</code> to which the <code>mapImage</code> is added.
   */
  private void initializeMapImage(MapImage mapImage, EObjectTreeItem treeItem) {
    Object value = treeItem.getValue();
    
    // The method getMapImageType needs an EObject, so if the value isn't an EObject, get the parent value.
    // Note: the root is an EObject, so either the value is an EObject, or the parent exists.
    if (!(value instanceof EObject)) {
      EObjectTreeItem parent = (EObjectTreeItem) treeItem.getParent();
      value = parent.getValue();
    }
    MapImageType mapImageType = VacationsUtils.getMapImageType((EObject) value);
    String defaultTitle = createDefaultTitleForMapImage(mapImageType, treeItem);
    Dialog<String> titleDialog = componentFactory.createStringInputDialog("MapImage title",
        """
           The contents of a map shown for a map image depends on the place of the MapImage in the tree.
           * If it is below a node with locaton information (e.g. a location, a GPX track or a picture), the map will only show information for that location.
           * If it is below a day, the map will show all information for that day.
           * if it is below a travel, the map will show all information for that travel.
           The title of the MapImage is also used to create the file name of the related jpg file.
           """,
        "Enter MapImage title", defaultTitle);
    Optional<String> result = titleDialog.showAndWait();
    result.ifPresent(mapImage::setTitle);
    
    // fill attributes based on the current map view
    mapImage.setImageHeight(travelMapView.getHeight());
    mapImage.setImageWidth(travelMapView.getWidth());
    mapImage.setCenterLatitude(travelMapView.getBaseMap().centerLat().get());
    System.out.println("travelMapView.getBaseMap().centerLat().get(): " + travelMapView.getBaseMap().centerLat().get());
    mapImage.setCenterLongitude(travelMapView.getBaseMap().centerLon().get());
    System.out.println("travelMapView.getBaseMap().centerLon().get(): " + travelMapView.getBaseMap().centerLon().get());
    mapImage.setZoom(travelMapView.getBaseMap().zoom().get());
    
//    EObject eObject = getFirstAncesterEObject(treeItem);
//    if (eObject != null) {
//      Day aDay = VacationsUtils.getAncestorOfType(eObject, Day.class);
//    }
//    
//    Object contentObject = getDayForTreeItem(treeItem);
//    if (contentObject == null) {
//      contentObject = getTravelForTreeItem(treeItem, VACATIONS_PACKAGE.getVacation());
//    }
    
    // Get the related vacation to determine the folder for storing the MapImage.
    Vacation vacation = getTravelForTreeItem(treeItem, VACATIONS_PACKAGE.getVacation());
    String vacationFolderName = VacationsUtils.getTravelFilesFolder(vacation);
    String fileName;
    if (mapImage.getTitle() != null  &&  !mapImage.getTitle().isEmpty()) {
      fileName = "MapImage_" + mapImage.getTitle() + ".jpg";
    } else {
      fileName = FileUtils.createBackupFileName("MapImage.jpg");
    }
    File file = new File(vacationFolderName, fileName);
    String filenameRelativeToVacationsFolder = FileUtils.getPathRelativeToFolder(vacationsRegistry.getVacationsFolderName(), file.getAbsolutePath());
    mapImage.setFileName(filenameRelativeToVacationsFolder);
    
    MapImageViewGenerator mapImageViewCreator = new MapImageViewGenerator(customization, this, (EObject) value, mapImage, mapImageType);
//    createMapImageView(mapImage, false);
  }
  
  /**
   * Create a default title for a MapImage based on the MapImageType and the EObjectTreeItem it is added to.
   * 
   * @param mapImageType the type of the MapImage
   * @param treeItem the EObjectTreeItem the MapImage is added to
   * @return a default title, or null if no default title can be created.
   */
  private String createDefaultTitleForMapImage(MapImageType mapImageType, EObjectTreeItem treeItem) {
    String defaultTitle = null;
    
    switch (mapImageType) {
    case LOCATION:
      Object value = treeItem.getValue();
      switch (value) {
      case GPXTrack gpxTrack -> {
        FileReference trackReference = gpxTrack.getTrackReference();
        if (trackReference != null  &&  trackReference.getTitle() != null) {
          defaultTitle = trackReference.getTitle();
        }
      }
      case Location location -> {
        if (location.getName() != null) {
          defaultTitle = location.getName();
        }
      }
      case Picture picture -> {
        FileReference pictureReference = picture.getPictureReference();
        if (pictureReference != null  &&  pictureReference.getTitle() != null) {
          defaultTitle = pictureReference.getTitle();
        }
      }
      default -> {}
      }
      break;
      
    case DAY:
      Day day = getDayForTreeItem(treeItem);
      if (day != null) {
        if (day.getTitle() != null) {
          defaultTitle = day.getTitle() + " overview";
        } else {
          defaultTitle = "Day " + day.getDayNr() + " overview";
        }
      }
      break;
      
    case TRAVEL:
      Vacation vacation = getTravelForTreeItem(treeItem, VACATIONS_PACKAGE.getVacation());
      if (vacation != null  && vacation.getTitle() != null) {
        defaultTitle = vacation.getTitle() + " overview";
      }
      break;
    }
    return defaultTitle;
  }

  private void updateMapImageFiles() {
    EObjectTreeItem treeItem = treeView.getSelectedObject();
    Vacation vacation = getTravelForTreeItem(treeItem, VACATIONS_PACKAGE.getVacation());
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
        createMapImageView(mapImage, false);
      }
    }
  }
  
  /**
   * Update the map image file for the specified EObjectTreeItem.
   *
   * @param eObjectTreeItem the EObjectTreeItem containing a MapImage. If it doesn't contain a MapImage, an exception is thrown.
   */
  void updateMapImageFile(EObjectTreeItem eObjectTreeItem) {
    Object object = eObjectTreeItem.getValue();
    if (object instanceof MapImage mapImage) {
      MapView mapView = createMapImageView(mapImage, false);
//      writeMapViewToFile(mapImage,mapView);
    } else {
      throw new RuntimeException("EObjectTreeItem doesn't contain a MapImage");
    }
  }
  
  private void writeMapViewToFile(MapImage mapImage, MapView mapView) {
    Path mapImageFilePath = Path.of(vacationsRegistry.getVacationsFolderName(), mapImage.getFileName());
    try {
      Files.deleteIfExists(mapImageFilePath);
      
      SnapshotParameters snapShotParameters = new SnapshotParameters();
      WritableImage writebleImage = new WritableImage((int) mapView.getWidth(), (int) mapView.getHeight());
      mapView.snapshot(snapShotParameters, writebleImage);

      // Save the image
//      File file = new File(mapImage.getFileName());
      saveImageAsJpeg(writebleImage, mapImageFilePath.toFile());
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
  
//  // TODO: this is not the right way. Always create a map based on the map image type (location, day, vacation).
//  void createMapImageJpgFile(MapImage mapImage) {
//    if (mapImage == null) {
//      LOGGER.severe("MapImage is null");
//      return;
//    }
//    
//    SnapshotParameters snapShotParameters = new SnapshotParameters();
//    WritableImage writebleImage = new WritableImage((int) travelMapView.getWidth(), (int) travelMapView.getHeight());
//    travelMapView.snapshot(snapShotParameters, writebleImage);
//
//    // Save the image
//    File file = new File(VacationsRegistry.vacationsFolderName, mapImage.getFileName());
////    if (!file.exists()) {
//      saveImageAsJpeg(writebleImage, file);
////    } else {
////      LOGGER.severe("File exists");
////    }
//  }

  public MapView createMapImageView(MapImage mapImage, boolean show) {
    
    String title = mapImage.getTitle();
    if (title == null) {
      title = "MapImage";
    }
    JfxStage jfxStage = new JfxStage(customization, title);
    TravelMapView imageTravelMapView = new TravelMapView(customization, jfxStage, null);

    Double height = mapImage.getImageHeight();
    if (height != null) {
      imageTravelMapView.setPrefHeight(height);
    }
    Double width = mapImage.getImageWidth();
    if (width != null) {
      imageTravelMapView.setPrefWidth(width);
    }

    imageTravelMapView.setZoom(mapImage.getZoom());

    Point2D center = new Point2D(mapImage.getCenterLatitude(), mapImage.getCenterLongitude());
    imageTravelMapView.getBaseMap().setCenter(center);
    
//    MapImageType mapImageType = VacationsUtils.getMapImageType(mapImage);
//    switch (mapImageType) {
//    case LOCATION -> {
////      Location location = getLocationForObject();
////      if (location != null) {
////        addLocationToMapView(imageTravelMapView, location, false);
////      }
//    }
//    case DAY -> {
//      Day day = VacationsUtils.getAncestorOfType(mapImage, Day.class);
//      if (day != null) {
//        addDayToMapView(imageTravelMapView, day, false);
//      }
//    }
//    case TRAVEL -> {
//      Vacation vacation = VacationsUtils.getVacationForObject(mapImage);
//      if (vacation != null) {
//        addVacationToMapView(imageTravelMapView, vacation, false);
//      }
//    }
//    }

    Scene scene = new Scene(imageTravelMapView);
    pulseCounter = 0;
    if (!show) {
      scene.addPostLayoutPulseListener(() -> {
        if (imageTravelMapView.getBaseMap().allTilesAvailable()  &&  pulseCounter++ > 40) {
          LOGGER.severe("Map should be ready");


          SnapshotParameters snapShotParameters = new SnapshotParameters();
          WritableImage writebleImage = new WritableImage((int) imageTravelMapView.getWidth(), (int) imageTravelMapView.getHeight());
          imageTravelMapView.snapshot(snapShotParameters, writebleImage);

//          // Save the image
//          File file = new File(mapImage.getFileName());
////          if (!file.exists()) {
//            saveImageAsJpeg(writebleImage, file);
////          } else {
////            LOGGER.severe("File exists");
////          }
          jfxStage.close();
        }
      });
    }
    jfxStage.setScene(scene);
    jfxStage.show();
    
//    Object contentObject = null;
//    EObject container = mapImage.eContainer();
//    while (container != null) {
//      if (container instanceof Day  ||  container instanceof Vacation) {
//        contentObject = container;
//        break;
//      }
//      
//      container = container.eContainer();
//    }
    
//    if (contentObject instanceof Day day) {
//      addDayToMapView(imageTravelMapView, day, false);
//    } else if (contentObject instanceof Vacation vacation) {
//      addVacationToMapView(imageTravelMapView, vacation, false);
//    }

    writeMapViewToFile(mapImage, imageTravelMapView);
    return imageTravelMapView;
  }
  
  public void createMapImageFile(MapImage mapImage) {
    
    createMapImageView(mapImage, false);
       
  }

  public Vacations getVacations() {
    return vacations;
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
    Object object = eObjectTreeItem.getValue();
    BoundingBox boundingBox;
    EObjectTreeItem parentEObjectTreeItem = (EObjectTreeItem) eObjectTreeItem.getParent();      
    Location location = (Location) parentEObjectTreeItem.getValue();
    // Create BoundingBox object if it doesn't exist yet.
    if (object != null) {
      boundingBox = (BoundingBox) object;
    } else {
            
      boundingBox = TravelsFactory.eINSTANCE.createBoundingBox();
           
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
