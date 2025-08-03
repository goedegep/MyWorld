package goedegep.vacations.app.guifx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.atlis.location.nominatim.NominatimAPI;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.gpx.GpxUtil;
import goedegep.gpx.app.Activity;
import goedegep.gpx.app.GPXTreeViewCreator;
import goedegep.gpx.app.GpxAppUtil;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.MetadataType;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttreeview.EObjectTreeCell;
import goedegep.jfx.eobjecttreeview.EObjectTreeItem;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemAttributeDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemForObject;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemForObjectList;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.jfx.objectcontrols.ObjectControlBoolean;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import goedegep.resources.ImageResource;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesPackage;
import goedegep.util.emf.EMFResource;
import goedegep.util.emf.EmfUtil;
import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;
import goedegep.vacations.app.logic.KmlFileImporter;
import goedegep.vacations.app.logic.KmlPlacemarkImportData;
import goedegep.vacations.app.logic.VacationsUtils;
import goedegep.vacations.model.GPXTrack;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.Travel;
import goedegep.vacations.model.VacationElement;
import goedegep.vacations.model.Vacations;
import goedegep.vacations.model.VacationsPackage;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * This class provides a window for importing information from a KML file.
 * <p>
 * The window consists of three parts.
 * <h3>Top part to select the KML file and the element to import</h3>
 * In this part you can select the KML file from which to import information.<br/>
 * There is a checkbox to specify whether extra location information shall be obtained from the Open Street Map Naminatim API.
 * If checked, for each location information from the Nominatim API is obtained, based on the coordinates in the KML Placemark.<br/>
 * Obtaining information from the Nominatim is quite slow, so in this case retrieving the information takes quites some time.<br/>
 * Once the information is available you can select a vacation element, which will then be shown in the next part.
 * 
 * <h3>The middle part to view and edit the element</h3>
 * This part shows the selected element and you can modify it before adding it to a vacation.<br/>
 * The content of this part fully depends on the type of element.
 * 
 * <h4>Location</h4>
 * From left to right the following is shown:
 * <ul>
 * <li><b>The Location in a tree view</b><br/>
 * This tree view is always in edit mode, so here you can edit the location.
 * </li>
 * <li><b>A map view centered on the location</b><br/>
 * </li>
 * <li><b>Address information of the location</b><br/>
 * The address text from the Placemark from the KML file is show.
 * As this text has many possible formats it's not easy to parse this into a country, city, street and house number.
 * Therefor you can manually copy and paste parts of this address text to the information in the tree view.<br/>
 * If also information is obtained from the Nominatim API, this information is also shown.
 * In this case separate values for country, city, street and house number are available.
 * But the values may not match with the real address.
 * </li>
 * </ul>
 * <h4>GPXTrack</h4>
 * From left to right the following is shown:
 * <ul>
 * <li><b>The GPXTrack in a tree view</b><br/>
 * This tree view is always in edit mode, so here you can edit the GPX track.
 * However, you typically don't enter a 'Title' if you have a good name in the GPX data.<br/>
 * And the 'File' field is automatically filled in when you select an item in the tree view in the Vacations window.
 * The folder name is derived from the vacation to which the element will be added and the filename consists of the start time and the name
 * of the GPX track.
 * </li>
 * <li><b>A map view centered on the GPX track</b><br/>
 * <li><b>The GPX data in a tree view</b><br/>
 * This tree view is always in edit mode, so here you can edit the GPX data. This is the data that will be stored in the '.gpx' file.
 * </li>
 * </ul>
 * 
 * <h3>The bottom part to add the element to a vacation</h3>
 * To add the element to a vacation, you have to select an item in the vacations tree view in the Vacations window.
 * If this is not done, you will be informed about this. If a list is selected, the element will be added to the end of the list.
 * If an element in a list is selected, you can choose between adding the element before or after this element.<br/>
 * In case of a GPXTrack, adding the element is accompanied by creating the GPX file.
 * 
 */
public class KmlFileImportWindow extends JfxStage {
  private final static Logger LOGGER = Logger.getLogger(KmlFileImportWindow.class.getName());
  private final static String WINDOW_TITLE = "Import information from a KML file";

  /**
   * The GUI customization
   */
  private CustomizationFx customization;

  /**
   * The {@code VacationsWindow} used for:<br/>
   * Knowing the item selected in the vacations tree view.<br/>
   * Obtaining the Home location from the Vacations (used to determine the home country and to skip it when importing information from a KML file).
   */
  private VacationsWindow vacationsWindow;

  /**
   * A {@code NominatimAPI} to get Open Street Map information about a location.
   */
  private NominatimAPI nominatimAPI;

  /**
   * The {@code ComponentFactoryFx} to create al GUI components.
   */
  private ComponentFactoryFx componentFactory;

  /**
   * {@code EMFResource} for the selected element. The element has to be placed in a {@code Resource} in order to show it in an {@code EObjectTree}.
   */
  private EMFResource<EObject> elementEmfResource;

  /**
   * {@code EMFResource} for the GPX file. The element has to be placed in a {@code Resource} in order to show it in an {@code EObjectTree} and it is used to save the GPX file.
   */
  private EMFResource<DocumentRoot> gpxEmfResource;

  /**
   * The {@code KmlFileSelectionPanel}. The top panel to select the KML file from which to import information and to select the element to handle.
   */
  private KmlFileSelectionPanel kmlFileSelectionPanel;

  /**
   * The {@code VacationElementPanel}. The center panel to view and edit the selected element.
   */
  private VacationElementPanel vacationElementPanel;

  /**
   * The {@code AddElementToVacationPanel}. The bottom panel to add the selected element to a vacation.
   */
  private AddElementToVacationPanel addElementToVacationPanel;

  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param vacationsWindow the {@code VacationsWindow} to which this window belongs
   * @param poiIcons {@code POIIcons} for the icons in the tree views
   * @param nominatimAPI a {@code NominatimAPI} to retrieve location information from
   */
  public KmlFileImportWindow(CustomizationFx customization, VacationsWindow vacationsWindow, NominatimAPI nominatimAPI) {
    super(customization, WINDOW_TITLE);

    this.customization = customization;
    this.vacationsWindow = vacationsWindow;
    this.nominatimAPI = nominatimAPI;

    componentFactory = customization.getComponentFactoryFx();

    elementEmfResource = new EMFResource<>(
        VacationsPackage.eINSTANCE, 
        null,
        ".xmi",
        true);

    gpxEmfResource = GpxUtil.createEMFResource();

    createControls();

    createGUI();

    // listen to Vacations tree view selection changes
    EObjectTreeView vacationsTreeView = vacationsWindow.getTreeView();
    vacationsTreeView.addObjectSelectionListener((_, selectedTreeItem) -> handleNewTreeItemSelected(selectedTreeItem));

    // react to a new element selected.
    kmlFileSelectionPanel.addObjectSelectionListener((_, element) -> handleNewTupletSelected(element));

    handleNewTreeItemSelected(vacationsTreeView.getSelectedObject());

    show();
  }

  /**
   * Create the controls, which in this case are the three panels.
   */
  private void createControls() {
    kmlFileSelectionPanel = new KmlFileSelectionPanel(vacationsWindow.getVacations());
    vacationElementPanel = new VacationElementPanel();
    addElementToVacationPanel = new AddElementToVacationPanel();    
  }

  /**
   * Handle the fact that a new item is selected in the Travels tree view.
   * <p>
   * The related {@code Vacation} is passed on to the {@code vacationElementPanel}.
   * The {@code selectedTreeItem} is passed on to the {@code addElementToVacationPanel}.
   * 
   * @param selectedTreeItem the currently selected item in the Vacations tree view.
   */
  private void handleNewTreeItemSelected(TreeItem<Object> selectedTreeItem) {
    Travel travel = VacationsWindow.getVacationForTreeItem(selectedTreeItem, VacationsPackage.eINSTANCE.getTravel());
    if (travel != null) {
      vacationElementPanel.handleNewVacationSelected(travel);
      addElementToVacationPanel.handleNewTreeItemSelected(selectedTreeItem);
    }
  }

  /**
   * Handle the fact that a new {@code KmlPlacemarkImportData} (information on an element to import) item is selected.
   * <p>
   * This new selection is passed on to the {@code vacationElementPanel} and the {@code addElementToVacationPanel }.
   * 
   * @param kmlPlacemarkImportData information on the element to import
   */
  private void handleNewTupletSelected(KmlPlacemarkImportData kmlPlacemarkImportData) {
    vacationElementPanel.handleElementSelected(kmlPlacemarkImportData);
    addElementToVacationPanel.handleElementSelected(kmlPlacemarkImportData);
  }

  /*
   * Create the GUI.
   * <p>
   * The GUI consists of a VBox, containing the three panel.
   */
  private void createGUI() {
    VBox mainVBox = componentFactory.createVBox(12.0, 12.0);

    mainVBox.getChildren().addAll(kmlFileSelectionPanel, vacationElementPanel, addElementToVacationPanel);

    setScene(new Scene(mainVBox, 1800, 1300));
  }


  /**
   * This inner class is the panel to select the KML file from which to import the vacation elements.
   */
  class KmlFileSelectionPanel extends VBox implements ObjectSelector<KmlPlacemarkImportData> {
    //  private final static Logger LOGGER = Logger.getLogger(KmlFileSelectionPanel.class.getName());

    /**
     * {@code Vacations} used to get the home location, which in turn is used to get the home country.
     */
    private Vacations vacations;

    /**
     * Import data retrieved from a KML file and extended with information obtained via a {@code NominatimAPI}.
     */
    private List<KmlPlacemarkImportData> kmlPlacemarksImportData;

    /**
     * The currently selected {@code KmlPlacemarkImportData}.
     */
    private KmlPlacemarkImportData currentKmlPlacemarkImportData;

    /**
     * Listeners to the {@code currentKmlPlacemarkImportData}.
     */
    private List<ObjectSelectionListener<KmlPlacemarkImportData>> objectSelectionListeners = new ArrayList<>();

    /**
     * Control for selecting the KML file.
     */
    private ObjectControlFileSelecter fileSelecter;

    /**
     * Indication of whether information is to be obtained from the NominatimAPI.
     */
    private ObjectControlBoolean useNominatimApi;

    /**
     * The elements that can be imported
     */
    private ComboBox<String> kmlPlacemarkImportDataComboBox;


    /**
     * Constructor
     * 
     * @param vacations {@code Vacations} used to get the home location, which in turn is used to get the home country.
     */
    public KmlFileSelectionPanel(Vacations vacations) {
      this.vacations = vacations;

      setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
      setPadding(new Insets(12.0));

      createControls();
      createGUI();

      fileSelecter.addListener((_) -> handleNewKmlFileSelected());
      kmlPlacemarkImportDataComboBox.setOnAction((_) -> handleNewKmlPlacemarkImportDataSelected());
    }

    /**
     * Create the GUI controls.
     */
    private void createControls() {
      fileSelecter = componentFactory.createFileSelecterObjectControl(300, "Path to KML file", "Choose file", "click to select a KML file via a file chooser", "Select KML file", false);
      fileSelecter.addFileType(".kml", "Google KML file", true);

      useNominatimApi = componentFactory.createObjectControlBoolean("use Nominatim API", false, true, "If selected address information is also obtained from the Open Street Map nominatim API (based on the coordinates of the location");

      kmlPlacemarkImportDataComboBox = componentFactory.createComboBox(null);    
    }

    /**
     * Create the GUI.
     * <p>
     * The GUI is an HBox with components to select a KML file, to indicate whether extra information is to be obtained from the Nominatim API and
     * a ComboBox to select an element to import.
     */
    private void createGUI() {
      Label label = componentFactory.createLabel("KML file and Vacation element selection");
      label.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
      getChildren().add(label);

      HBox hBox = componentFactory.createHBox(12.0, 12.0);

      label = componentFactory.createLabel("KML file:");
      hBox.getChildren().addAll(label, fileSelecter.getControl(), fileSelecter.getFileChooserButton(), useNominatimApi.getControl());

      Region spacer = new Region();
      spacer.setPrefWidth(100);
      hBox.getChildren().add(spacer);

      label = componentFactory.createLabel("Vacation element:");
      hBox.getChildren().addAll(label, kmlPlacemarkImportDataComboBox);

      getChildren().add(hBox);
    }

    /**
     * Handle the fact that a new KML file is selected.
     * <p>
     * A {@link KmlFileImporter} is used to obtain the information from the KML file.<br/>
     * If possible, the Home location is removed from the list of elements (as the home location is always already shown on the map).<br/>
     * Then {@link #handleNewListOfElementsSelected(Object, List)} is called.
     */
    private void handleNewKmlFileSelected() {
      File file = fileSelecter.getValue();
      NominatimAPI actualNominatimAPI = useNominatimApi.getValue() ? nominatimAPI : null;
      KmlFileImporter kmlFileImporter = new KmlFileImporter.Builder(file, actualNominatimAPI)
          .setHomeCountry(vacations.getHome().getCountry())
          .build();
      kmlPlacemarksImportData = kmlFileImporter.getLocationsFromKmlFile();

      filterOutHomeLocation(kmlPlacemarksImportData);

      handleNewListOfKmlPlacemarkImportData();
    }

    /**
     * Handle the fact that a new list of {@code KmlPlacemarkImportData} is available.
     * <p>
     * The {@code vacationElementsComboBox} is updated for the new values and, if possible, the first element is selected.
     */
    void handleNewListOfKmlPlacemarkImportData() {
      kmlPlacemarkImportDataComboBox.getItems().clear();

      if (kmlPlacemarksImportData != null) {
        for (KmlPlacemarkImportData tuplet: kmlPlacemarksImportData) {
          kmlPlacemarkImportDataComboBox.getItems().add(kmlPlacemarkImportDataToString(tuplet));
          kmlPlacemarkImportDataComboBox.setDisable(false);
        }
      } else {
        kmlPlacemarkImportDataComboBox.getItems().add("no KML file selected");
        kmlPlacemarkImportDataComboBox.setDisable(true);
      }

      if (!kmlPlacemarkImportDataComboBox.getItems().isEmpty()) {
        kmlPlacemarkImportDataComboBox.getSelectionModel().select(0);
      }
    }

    /**
     * Get a String identification for a {@code KmlPlacemarkImportData}.
     * <p>
     * This is meant to be used for the values in a ComboBox.
     * For a {@code Location} the text is the name of the {@code Location}.<br/>
     * For a {@code GPXTrack} the text is the name in the meta data of the GPX data.<br/>
     * For any other type a {@code RuntimeException} is thrown.
     * 
     * @param kmlPlacemarkImportData the value for which a String is to be obtained.
     * @return a String identification for the {@code kmlPlacemarkImportData}
     */
    private String kmlPlacemarkImportDataToString(KmlPlacemarkImportData kmlPlacemarkImportData) {
      VacationElement vacationElement = kmlPlacemarkImportData.vacationElement();

      if (vacationElement instanceof Location location) {
        return "Location: " + location.getName();
      } else if (vacationElement instanceof GPXTrack) {
        DocumentRoot documentRoot = kmlPlacemarkImportData.gpxFileDocumentRoot();
        GpxType gpxType = documentRoot.getGpx();
        MetadataType metadata = gpxType.getMetadata();
        return "GPXTrack: " + metadata.getName();
      } else {
        throw new RuntimeException("Unsupported sub type of VacationElement: " + vacationElement.getClass().toString());
      }
    }

    /**
     * Handle the fact that a new {@code KmlPlacemarkImportData} is selected via the {@code vacationElementsComboBox}.
     * <p>
     * This panel doesn't do anything with this, except notifying the listeners.
     */
    private void handleNewKmlPlacemarkImportDataSelected() {
      int selectedIndex = kmlPlacemarkImportDataComboBox.getSelectionModel().getSelectedIndex();

      if (selectedIndex == -1) {
        LOGGER.info("no item selected");
        currentKmlPlacemarkImportData = null;
        return;
      }

      currentKmlPlacemarkImportData = kmlPlacemarksImportData.get(selectedIndex);

      notifyListeners();
    }

    /**
     * Filter out the Home location from a list of {@code KmlPlacemarkImportData}.
     * 
     * @param kmlPlacemarkImportDatas the list from which the Home location is to be filtered out.
     */
    private void filterOutHomeLocation(List<KmlPlacemarkImportData> kmlPlacemarkImportDatas) {
      Iterator<KmlPlacemarkImportData> iterator = kmlPlacemarkImportDatas.iterator();
      while (iterator.hasNext()) {
        KmlPlacemarkImportData kmlPlacemarkImportData = iterator.next();
        VacationElement vacationElement = kmlPlacemarkImportData.vacationElement();
        if (vacationElement instanceof Location location) {
          if (locationIsHomeLocation(location)) {
            iterator.remove();
          }
        }
      }
    }

    /**
     * Check whether a {@code Location} is the Home location.
     * <p>
     * A {@code Location} is assumed to be the Home location if the name starts with 'Thuis'.
     * 
     * @param location the {@code Location} to be checked.
     * @return true if the {@code location} is the Home location.
     */
    private boolean locationIsHomeLocation(Location location) {
      String name = location.getName();

      if (name != null) {
        Pattern pattern = Pattern.compile("^Thuis (.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        boolean matchFound = matcher.find();
        if(matchFound) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }

    /**
     * Notify the listeners about the fact that a new {@code KmlPlacemarkImportData} has been selected.
     */
    private void notifyListeners() {
      for (ObjectSelectionListener<KmlPlacemarkImportData> listener: objectSelectionListeners) {
        listener.objectSelected(this, currentKmlPlacemarkImportData);
      }
    }

    /**
     * {@inheritDoc}
     * In this case listen to the selected {@code KmlPlacemarkImportData}.
     */
    @Override
    public void addObjectSelectionListener(ObjectSelectionListener<KmlPlacemarkImportData> objectSelectionListener) {
      objectSelectionListeners.add(objectSelectionListener);
    }

    /**
     * {@inheritDoc}
     * In this case stop listening to the selected {@code KmlPlacemarkImportData}.
     */
    @Override
    public void removeObjectSelectionListener(ObjectSelectionListener<KmlPlacemarkImportData> objectSelectionListener) {
      objectSelectionListeners.remove(objectSelectionListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KmlPlacemarkImportData getSelectedObject() {
      return currentKmlPlacemarkImportData;
    }
  }  // End of KmlFileSelectionPanel


  /**
   * This inner class is the panel to select, view and edit an element to be imported.
   */
  class VacationElementPanel extends VBox {

    /**
     * A {@code SimpleDateFormat} used to create a GPX file filename.
     */
    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd hh-mm");

    /**
     * The {@code Location} being handled. This is null if a {@code GPXTrack} is being handled.
     */
    private Location location = null;

    /**
     * The {@code GPXTrack} being handled. This is null if a {@code Location} is being handled.
     */
    private GPXTrack gpxTrack = null;

    /**
     * The GPX data related to the {@code gpxTrack}. This is null if a {@code Location} is being handled. 
     */
    private DocumentRoot documentRoot = null;

    /**
     * The {@code HBox} where all information of the element is shown.
     */
    private HBox previewBox;

    /**
     * The {@code TravelMapView} showing the element
     */
    private TravelMapView travelMapView;

    /**
     * A tree view for showing a {@code GPXTrack}
     * <p>
     * Instead of the VacationsTreeView a separate tree view is used, because the information is derived in a special way.
     */
    //  private GPXElementTreeView gpxElementTreeView;
    private EObjectTreeView gpxElementTreeView;

    /**
     * A {@code VacationsTreeView} used to show a {@code Location}.
     */
    private EObjectTreeView vacationsTreeView;

    /**
     * A tree view for showing GPX data (a {@code DocumentRoot}).
     */
    private EObjectTreeView gpxTreeView;

    /**
     * The currently handled {@code KmlPlacemarkImportData}.
     */
    private KmlPlacemarkImportData kmlPlacemarkImportData;

    /**
     * Used to determine the folder name for the track reference.
     */
    private Travel travel;
    


    /**
     * Constructor
     * 
     * @param poiIcons {@code POIIcons} used in the map view and tree views.
     * @param elementEmfResource The {@code EMFResource} for vacation elements.
     * @param gpxEmfResource The {@code EMFResource} for the GPX data.
     */
    public VacationElementPanel() {

      setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
      setPadding(new Insets(12.0));

      createControls();
      createGUI();
    }

    /**
     * Create the GUI controls.
     */
    private void createControls() {
      travelMapView = new TravelMapView(customization, null, null);
      travelMapView.setMinWidth(800);
      travelMapView.removeControlsLayer();

      vacationsTreeView = new VacationsTreeViewCreator(customization)
          .createVacationsTreeView();
      vacationsTreeView.setEditMode(true);
      vacationsTreeView.setMinWidth(300);

//      gpxElementTreeView = new GPXElementTreeView(customization,
//          this::generateTextForGpxTrack, this::generateIconForGpxTrack,
//          this::getInitialFolderName, this::getInitialFileName);
      createGpxElementTreeView();

      gpxTreeView = new GPXTreeViewCreator().createGPXTreeView(customization);
      gpxTreeView.setMinWidth(300);
      gpxTreeView.setEditMode(true);
    }
    
    /**
     * Create the tree view for the {@code GPXTrack} element.
     */
    private void createGpxElementTreeView() {
      gpxElementTreeView = new GPXElementTreeViewCreator().createGPXElementTreeView(customization);

      TypesPackage typesPackage = TypesPackage.eINSTANCE;
      EObjectTreeItemAttributeDescriptor eObjectTreeItemAttributeDescriptor = gpxElementTreeView.getEObjectTreeItemAttributeDescriptor(typesPackage.getFileReference(), typesPackage.getFileReference_File());
      eObjectTreeItemAttributeDescriptor.setInitialDirectoryNameFunction(this::getInitialFolderName);
      eObjectTreeItemAttributeDescriptor.setInitialFileNameFunction(this::getInitialFileName);
      
      EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor  = gpxElementTreeView.getDescriptorForEClass(VacationsPackage.eINSTANCE.getGPXTrack());
      eObjectTreeItemClassDescriptor.setNodeTextFunction(this::generateTextForGpxTrack);
      eObjectTreeItemClassDescriptor.setNodeIconFunction(this::generateIconForGpxTrack);

      gpxElementTreeView.setMinWidth(300);
      
    }

    /**
     * Create the GUI.
     * <p>
     * Main part of the GUI is the {@code previewBox} which is an {@code HBox}.
     * This box is where the {@code Location} or {@code GPXTrack} specific information is filled in.
     */
    private void createGUI() {
      Label label = componentFactory.createLabel("View and edit vacation element");
      label.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
      getChildren().add(label);

      previewBox = componentFactory.createHBox();
      getChildren().add(previewBox);

    }

    /**
     * Handle the fact that a new Travel is selected.
     * <p>
     * Only if the current element is a {@code GPXTrack}, {@link #setGpxTrackReference} is called to update the track reference.
     * 
     * @param travel the new {@code Travel}.
     */
    void handleNewVacationSelected(Travel travel) {
      LOGGER.info("New travel selected: " + (travel != null ? travel.getTitle() : "null"));
      this.travel = travel;

      if (gpxTrack != null) {
        // No GPXTrack means we don't have to update the file in its track reference.
        setGpxTrackReferenceForTravel();
      }
    }

    /**
     * Set the GPX track reference.
     * <p>
     * The folder name is the name of the folder where the files related to the current Vacation are stored (see {@link VacationsUtils.getVacationFolder}).<br/>
     * The complete filename is obtained by calling {@link #createGpxTrackFileName}.
     */
    private void setGpxTrackReferenceForTravel() {
      String travelFolder = null;

      if (travel != null) {
        travelFolder = VacationsUtils.getTravelFilesFolder(travel);
      }

      String gpxTrackFileName = null;

      if (travelFolder != null) {
        gpxTrackFileName = createGpxTrackFileName(travelFolder);
      }

      gpxTrack.getTrackReference().setFile(gpxTrackFileName);
    }

    /**
     * Create the file name for a GPX track.
     * <p>
     * The folder is the {@code vacationFolder}.
     * The file name has the following format: {@literal <yyyy-mm-dd>-<name>}<br/>
     * Where:<br/>
     * {@literal <yyyy-MM-dd hh-mm>} is the date/time of the track<br/>
     * {@literal <name>} is the name of the track
     * 
     * @return the file name for the GPX track.
     */
    private String createGpxTrackFileName(String vacationFolder) {
      LOGGER.info("=> vacationFolder=" + vacationFolder);

      String fileName = "track.gpx";  // Default filename
      if (documentRoot != null) {
        GpxType gpxType = documentRoot.getGpx();
        String name = gpxType.getMetadata().getName();
        Date startTime = gpxType.getStartTime();
        name = createLegalFileName(name);
        LOGGER.info("startTime (" + name + "): " + startTime);
        fileName = DF.format(startTime) + " " + name + ".gpx";
      }
      Path path = Paths.get(vacationFolder, fileName);

      return path.toString();
    }
    
    private String createLegalFileName(String name) {
      name = name.replace("|", ",");
      name = name.replace("\"", "'");
      name = name.replace("/", "-");
      name = name.replace(":", ";");
      
      return name;
    }

    String getInitialFolderName(EObjectTreeCell eObjectTreeCell) {
      if (gpxTrack == null) {
        return null;
      }

      String fileName = gpxTrack.getTrackReference().getFile();
      if (fileName == null) {
        return null;
      }

      File file = new File(fileName);
      return file.getParent();
    }

    String getInitialFileName(EObjectTreeCell eObjectTreeCell) {
      if (gpxTrack == null) {
        return null;
      }

      String fileName = gpxTrack.getTrackReference().getFile();
      if (fileName == null) {
        return null;
      }

      File file = new File(fileName);
      return file.getName();
    }

    void handleElementSelected(KmlPlacemarkImportData tuplet) {
      this.kmlPlacemarkImportData = tuplet;

      if (tuplet == null) {
        LOGGER.info("no item selected");
        tuplet = null;
        clearPreviewBox();
        return;
      }

      VacationElement vacationElement = tuplet.vacationElement();

      if (vacationElement instanceof Location) {
        location = (Location) vacationElement;
        gpxTrack = null;
        documentRoot = null;
        switchPreviewBoxToLocation();
      } else if (vacationElement instanceof GPXTrack) {
        gpxTrack = (GPXTrack) vacationElement;
        documentRoot = (DocumentRoot) tuplet.gpxFileDocumentRoot();
        location = null;
        setGpxTrackReferenceForTravel();
        switchPreviewBoxToGpxTrack();
      } else {
        throw new RuntimeException("Unsupported VacationElement sub type: " + vacationElement.getClass().getName());
      }
    }

    private void clearPreviewBox() {
      previewBox.getChildren().clear();
    }

    private void switchPreviewBoxToLocation() {
      elementEmfResource.setEObject(location);

      travelMapView.getMapRelatedItemsLayer().clear();
      travelMapView.getGpxLayer().clear();
      MapPoint mapCenter = new MapPoint(location.getLatitude(), location.getLongitude());
      travelMapView.setCenter(mapCenter);
      travelMapView.setZoom(8.0);

      travelMapView.getMapRelatedItemsLayer().addLocation(location, location.getName());

      vacationsTreeView.setEObject(location);
      vacationsTreeView.getRoot().setExpanded(true);

      previewBox.getChildren().clear();

      SplitPane mainSplitPane = new SplitPane();
      mainSplitPane.setOrientation(Orientation.HORIZONTAL);
      mainSplitPane.setDividerPositions(0.3);

      SplitPane rightSplitPane = new SplitPane();
      rightSplitPane.setOrientation(Orientation.HORIZONTAL);

      VBox addressOptionsPane = createAddressOptionsPane();
      rightSplitPane.getItems().addAll(travelMapView, addressOptionsPane);
      mainSplitPane.getItems().addAll(vacationsTreeView, rightSplitPane);

      previewBox.getChildren().add(mainSplitPane);
    }

    private VBox createAddressOptionsPane() {
      VBox vBox = componentFactory.createVBox(12.0, 12.0);

      Label label = componentFactory.createLabel("Placemark address:");
      vBox.getChildren().add(label);

      TextField textField = componentFactory.createTextField(300, "Address text from the Placemark");
      textField.setEditable(false);
      textField.setText(kmlPlacemarkImportData.placemarkAddress());
      vBox.getChildren().add(textField);

      Region spacer = new Region();
      spacer.setPrefHeight(50);
      vBox.getChildren().add(spacer);

      String nominatimAddressLabel = "Nominatim address:";
      Location nominatimLocation = kmlPlacemarkImportData.nominatimLocation();
      if (nominatimLocation == null) {
        nominatimAddressLabel += " not available";
      }

      label = componentFactory.createLabel(nominatimAddressLabel);
      vBox.getChildren().add(label);

      if (nominatimLocation != null) {
        GridPane gridPane = componentFactory.createGridPane();
        label = componentFactory.createLabel("name:");
        gridPane.add(label, 0, 0);
        textField = componentFactory.createTextField(300, "Name of the location obtained from the Nominatim API");
        textField.setEditable(false);
        textField.setText(nominatimLocation.getName() != null ? nominatimLocation.getName() : "");
        gridPane.add(textField, 1, 0);

        label = componentFactory.createLabel("street:");
        gridPane.add(label, 0, 1);
        textField = componentFactory.createTextField(300, "Street name of the location obtained from the Nominatim API");
        textField.setEditable(false);
        textField.setText(nominatimLocation.getStreet() != null ? nominatimLocation.getStreet() : "");
        gridPane.add(textField, 1, 1);

        label = componentFactory.createLabel("housenumber:");
        gridPane.add(label, 0, 2);
        textField = componentFactory.createTextField(300, "Housenumber of the location obtained from the Nominatim API");
        textField.setEditable(false);
        textField.setText(nominatimLocation.getHouseNumber() != null ? nominatimLocation.getHouseNumber() : "");
        gridPane.add(textField, 1, 2);

        label = componentFactory.createLabel("city:");
        gridPane.add(label, 0, 3);
        textField = componentFactory.createTextField(300, "City name of the location obtained from the Nominatim API");
        textField.setEditable(false);
        textField.setText(nominatimLocation.getCity() != null ? nominatimLocation.getCity() : "");
        gridPane.add(textField, 1, 3);

        label = componentFactory.createLabel("country: ");
        gridPane.add(label, 0, 4);
        textField = componentFactory.createTextField(300, "Country name of the location obtained from the Nominatim API");
        textField.setEditable(false);
        textField.setText(nominatimLocation.getCountry() != null ? nominatimLocation.getCountry() : "");
        gridPane.add(textField, 1, 4);

        vBox.getChildren().add(gridPane);
      }

      return vBox;
    }

    private void switchPreviewBoxToGpxTrack() {
      elementEmfResource.setEObject(gpxTrack);
      gpxEmfResource.setEObject(documentRoot);

      travelMapView.getMapRelatedItemsLayer().clear();
      travelMapView.getGpxLayer().clear();

      GpxType gpxType = documentRoot.getGpx();
      if (gpxType != null) {
        WGS84BoundingBox gpxBoundingBox = travelMapView.getGpxLayer().addGpx(null, null, documentRoot.getGpx());

        if (gpxBoundingBox != null) {
          Double zoomLevel = MapView.getZoomLevel(gpxBoundingBox);
          if (zoomLevel != null) {
            travelMapView.setZoom(zoomLevel);
          }
        }

        if (gpxBoundingBox != null) {
          WGS84Coordinates center = gpxBoundingBox.getCenter();
          LOGGER.info("center: " + center.toString());
          MapPoint mapCenter = new MapPoint(center.getLatitude(), center.getLongitude());

          if (mapCenter != null) {
            travelMapView.flyTo(0.0, mapCenter, 2);
          }
        }
      }

      gpxElementTreeView.setEObject(gpxTrack);
      gpxTreeView.setEObject(documentRoot);
      gpxTreeView.getRoot().setExpanded(true);

      previewBox.getChildren().clear();

      SplitPane mainSplitPane = new SplitPane();
      mainSplitPane.setOrientation(Orientation.HORIZONTAL);
      mainSplitPane.setDividerPositions(0.3);

      SplitPane rightSplitPane = new SplitPane();
      rightSplitPane.setOrientation(Orientation.HORIZONTAL);

      rightSplitPane.getItems().addAll(travelMapView, gpxTreeView);
      mainSplitPane.getItems().addAll(gpxElementTreeView, rightSplitPane);

      previewBox.getChildren().add(mainSplitPane);
    }

    /**
     * Generate the text to be shown for a GPXTrack item in the TreeView.
     * <p>
     * In order of availablility the text is:
     * <ul>
     * <li>The title in the FileReference in the gpxTrack.</li>
     * <li>The name attribute in the GPX 'file' (in this case the DocumentRoot).</li>
     * <li>The name of the GPX file.</li>
     * <li>The default text 'GPX track reference'</li>
     * </ul>
     * 
     * @param gpxTrack The <code>GPXTrack</code> for which a text is to be generated.
     */
    String generateTextForGpxTrack(EObject eObject) {
      GPXTrack gpxTrack = (GPXTrack) eObject;
      String text = null;
      FileReference fileReference = gpxTrack.getTrackReference();

      // Try to use the title in the FileReference 
      if (fileReference != null) {
        String title = fileReference.getTitle();
        if (title != null  &&  !title.isEmpty()) {
          text = title;
        }
      }

      if (text == null) {
        // Try to use the name attribute in the 'GPX file'
        GpxType gpxType = documentRoot.getGpx();
        MetadataType metadataType = gpxType.getMetadata();
        String name = metadataType.getName();
        if (name != null  &&  !name.isEmpty()) {
          text = name;
        }
      }

      if (text == null) {
        // Try to use the name of the GPX file.
        if (fileReference != null) {
          String fileName = fileReference.getFile();
          if (fileName != null  &&  !fileName.isEmpty()) {
            text = fileName;
          }
        }
      }

      if (text == null) {
        // Use default text
        text = "GPX track";
      }

      return text;
    }

    Image generateIconForGpxTrack(Object object) {
      Image gpxTrackImage = ImageResource.GPX.getImage();

      Activity activity = null;
      GpxType gpxType = documentRoot.getGpx();
      activity = GpxAppUtil.getActivity(gpxType);

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

  } // End of VacationElementPanel

  
  /**
   * This inner class is a panel for adding the element to a vacation.
   */
  class AddElementToVacationPanel extends VBox {
    private static final String BEFORE_TEXT = "before";
    private static final String AFTER_TEXT = "after";

    /**
     * Item currently selected in the vacations tree, determining the location where the element is to be added.
     */
    private EObjectTreeItem selectedTreeItem;
    
    /**
     * The currently selected {@code KmlPlacemarkImportData}.
     */
    private KmlPlacemarkImportData selectedKmlPlacemarkImportData;

    /**
     * The actual GUI panel
     */
    private VBox actionVBox;
    
    /**
     * Label informing the user that first an element to be added has to be selected.
     */
    private Label selectElementLabel;
    
    /**
     * Label used to inform the user that a node in the Vacations tree has to be selected.
     */
    private Label infoLabel;
    
    /**
     * Button to add a Location.
     */
    private Button addLocationButton;
    
    /**
     * Button to add a GPXTrack
     */
    private Button addGPXTrackButton;
    
    /**
     * Label providing information for the {@link beforeOrAfterComboBox}.
     */
    private Label addAsLastChildToListLabel;
    
    /**
     * A {@code ComboBox} to choose between adding the element before or after the selected tree item.
     */
    private ComboBox<String> beforeOrAfterComboBox;
    
    
    /**
     * Label providing feedback on the {@link #selectedTreeItem}.
     */
    private TextField selectedTreeItemDescription;

    /**
     * Constructor
     */
    public AddElementToVacationPanel() {

      setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
      setPadding(new Insets(12.0));

      Label label = componentFactory.createLabel("Adding the element");
      label.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
      getChildren().add(label);

      GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);

      this.getChildren().add(gridPane);

      actionVBox = componentFactory.createVBox(12.0);

      // actionHBox items for when no element is selected.
      selectElementLabel = componentFactory.createLabel("Select an element to be added to a vacation");

      // actionHBox items for when no tree item is selected to add the location to.
      infoLabel = componentFactory.createLabel("Select a node in the Vacations tree where an element can be added");

      // actionHBox items for when a list of Locations is selected
      addAsLastChildToListLabel = componentFactory.createLabel("as last child to");
      beforeOrAfterComboBox = componentFactory.createComboBox(null);
      beforeOrAfterComboBox.getItems().addAll(BEFORE_TEXT, AFTER_TEXT);
      beforeOrAfterComboBox.getSelectionModel().select(AFTER_TEXT);
      selectedTreeItemDescription = componentFactory.createTextField(800, null);
      selectedTreeItemDescription.setEditable(false);

      // actionHBox items for when a Location is selected
      addLocationButton = componentFactory.createButton("Add Location", "Add this Location at the selected tree item");
      addLocationButton.setOnAction((_) -> addLocation());

      // actionHBox items for when a GPXTrack is selected
      addGPXTrackButton = componentFactory.createButton("Add GPXTrack", "Add this GPXTrack at the selected tree item and create the GPX file");
      addGPXTrackButton.setOnAction((_) -> addGPXTrack());

      this.getChildren().add(actionVBox);

    }

    /**
     * Add the selected location element to the tree.
     * <p>
     * If the selected tree item is a list of (a supertype of) Locations, it is added as last child of this list.<br/>
     * If the selected tree item is an item in a list of (a supertype of) Locations, it is added to this list before or after the selected item.
     * The value of the <code>beforeOrAfterComboBox</code> determines whether it is added before or after the selected item.<br/>
     */
    private void addLocation() {
      addElementToVacations(VacationsPackage.eINSTANCE.getLocation());
    }

    /**
     * Create the GPX file and add the selected GPX element to the tree.
     * <p>
     * If the selected tree item is a list of (a supertype of) Locations, it is added as last child of this list.<br/>
     * If the selected tree item is an item in a list of (a supertype of) Locations, it is added to this list before or after the selected item.
     * The value of the <code>beforeOrAfterComboBox</code> determines whether it is added before or after the selected item.<br/>
     */
    private void addGPXTrack() {
      createGPXFile();
      addElementToVacations(VacationsPackage.eINSTANCE.getGPXTrack());
    }

    /**
     * Add the {@code selectedKmlPlacemarkImportData} to the Vacations data.
     * 
     * @param elementClass the {code @EClass} of the element to be added.
     */
    private void addElementToVacations(EClass elementClass) {
      Object eObjectTreeItemContent = selectedTreeItem.getValue();

      // If the selected tree item is a list of (a supertype of) the elementClass, it is added as last child of this list.
      EStructuralFeature eStructuralFeature = selectedTreeItem.getEStructuralFeature();
      if (eStructuralFeature instanceof EReference eReference) {
        EClass listType = eReference.getEReferenceType();
        if (EmfUtil.isInstanceof(elementClass, listType)  &&  eReference.isMany()) {
          LOGGER.severe("match");
          @SuppressWarnings("unchecked")
          EList<EObject> eObjectList = (EList<EObject>) eObjectTreeItemContent;

          eObjectList.add(selectedKmlPlacemarkImportData.vacationElement());

          return;
        }
      }

      // If the selected tree item is an item in a list of (a supertype of) elementClass, it is added to this list before or after the selected item.
      // In this case the parent item of the selected item shall be a list of (a supertype of) elementClass.
      EObjectTreeItem parentTreeItem = (EObjectTreeItem) selectedTreeItem.getParent();
      Object eObjectTreeItemContentParentTreeItem = parentTreeItem.getValue();
      EStructuralFeature eStructuralFeatureParentTreeItem = parentTreeItem.getEStructuralFeature();
      if (eStructuralFeatureParentTreeItem instanceof EReference eReference) {
        EClass listType = eReference.getEReferenceType();
        if (EmfUtil.isInstanceof(elementClass, listType)  &&  eReference.isMany()) {
          LOGGER.info("parent match");
          @SuppressWarnings("unchecked")
          EList<EObject> eObjectList = (EList<EObject>) eObjectTreeItemContentParentTreeItem;

          int index = eObjectList.indexOf(eObjectTreeItemContent);
          boolean before = beforeOrAfterComboBox.getValue().equals(BEFORE_TEXT);
          if (!before) {
            index++;
          }
          eObjectList.add(index, selectedKmlPlacemarkImportData.vacationElement());

          return;
        }
      }
    }

    /**
     * Create the GPX file.
     */
    private void createGPXFile() {
      GPXTrack gpxTrack = (GPXTrack) selectedKmlPlacemarkImportData.vacationElement();
      String fileName = gpxTrack.getTrackReference().getFile();
      try {
        gpxEmfResource.save(fileName);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    /**
     * Handle the fact that a new item is selected in the tree view.
     * 
     * @param selectedTreeItem the currently selected item in the tree view.
     */
    void handleNewTreeItemSelected(TreeItem<Object> selectedTreeItem) {
      this.selectedTreeItem = (EObjectTreeItem) selectedTreeItem;

      updateActionHBox();
    }

    /**
     * Handle the fact that a new element is selected to be added to a vacation.
     */
    void handleElementSelected(KmlPlacemarkImportData kmlPlacemarkImportData) {
      this.selectedKmlPlacemarkImportData = kmlPlacemarkImportData;

      updateActionHBox();
    }

    /**
     * Update the actionHBox
     * <p>
     * The contents of this box depends on the item selected in the tree view.
     */
    private void updateActionHBox() {
      actionVBox.getChildren().clear();

      if (selectedKmlPlacemarkImportData == null) {
        // message: no tuplet selected
        actionVBox.getChildren().add(selectElementLabel);
      } else {
        boolean selectedTreeItemIsLocationsList = selectedTreeItemIsLocationsList();
        boolean selectedTreeItemIsLocationsListItem = selectedTreeItemIsLocationsListItem();

        if (!selectedTreeItemIsLocationsList  &&  !selectedTreeItemIsLocationsListItem) {
          // message: select an item in the vacations tree where an element can be added.
          actionVBox.getChildren().add(infoLabel);
        } else {
          VacationElement vacationElement = selectedKmlPlacemarkImportData.vacationElement();
          selectedTreeItemDescription.setText(getTreeItemDescription(selectedTreeItem));

          if (vacationElement instanceof Location) {
            // fill box for adding Location
            if (selectedTreeItemIsLocationsList) {
              // add as last child to list
              HBox hBox = componentFactory.createHBox(12.0);
              hBox.getChildren().addAll(addLocationButton, addAsLastChildToListLabel, selectedTreeItemDescription);
              actionVBox.getChildren().add(hBox);
            } else {
              // add before or after item in a list
              HBox hBox = componentFactory.createHBox(12.0);
              hBox.getChildren().addAll(addLocationButton, beforeOrAfterComboBox, selectedTreeItemDescription);
              actionVBox.getChildren().add(hBox);           
            }
          } else if (vacationElement instanceof GPXTrack gpxTrack) {
            // fill box for adding GPXTrack and creating GPX file.
            Label createGPXFileLabel = componentFactory.createLabel("and create GPX file: " + gpxTrack.getTrackReference().getFile());
            if (selectedTreeItemIsLocationsList) {
              // add as last child to list
              GridPane gridPane = componentFactory.createGridPane(12.0, 12.0);
              gridPane.add(addGPXTrackButton, 0, 0);
              gridPane.add(addAsLastChildToListLabel, 1, 0);
              gridPane.add(selectedTreeItemDescription, 2, 0);
              gridPane.add(createGPXFileLabel, 1, 1, 2, 1);
              actionVBox.getChildren().add(gridPane);
            } else {
              // add before or after item in a list
              GridPane gridPane = componentFactory.createGridPane(12.0, 12.0);
              gridPane.add(addGPXTrackButton, 0, 0);
              gridPane.add(beforeOrAfterComboBox, 1, 0);
              gridPane.add(selectedTreeItemDescription, 2, 0);
              gridPane.add(createGPXFileLabel, 1, 1, 2, 1);
              actionVBox.getChildren().add(gridPane);
            }
          } else {
            throw new RuntimeException("Vacation element of type '" + vacationElement.getClass().getName() + "' isn't supported");
          }
        }
      }    
    }

    /**
     * Get a textual representation for an {@code EObjectTreeItem}.
     * 
     * @param eObjectTreeItem the {@code EObjectTreeItem} for which to get a textual representation.
     * @returna textual representation for a {@code eObjectTreeItem}.
     */
    private String getTreeItemDescription(EObjectTreeItem eObjectTreeItem) {
      if (eObjectTreeItem == null) {
        return null;
      }

      String parentsText = getTreeItemDescription((EObjectTreeItem) eObjectTreeItem.getParent());

      String itemText = "NO INFORMATION";
      Object eObjectTreeItemContent = eObjectTreeItem.getValue();
      if (eObjectTreeItem instanceof EObjectTreeItemForObjectList eObjectTreeItemForObjectList) {
        EObjectTreeItemClassListReferenceDescriptor eObjectTreeItemClassListReferenceDescriptor = eObjectTreeItemForObjectList.getEObjectTreeItemClassListReferenceDescriptor();

        itemText =  eObjectTreeItemClassListReferenceDescriptor.getLabelText();
      } else if (eObjectTreeItem instanceof EObjectTreeItemForObject eObjectTreeItemForObject) {
        EObjectTreeItemClassDescriptor eObjectTreeItemClassDescriptor = eObjectTreeItemForObject.getClassDescriptor();
        if (eObjectTreeItemClassDescriptor == null) {
          LOGGER.severe("STOP");
        }
        EObject eObject = (EObject) eObjectTreeItemContent;

        if (eObjectTreeItemClassDescriptor.getNodeTextFunction() != null) {
          itemText = eObjectTreeItemClassDescriptor.getNodeTextFunction().apply(eObject);
        } else if (eObject != null) {
          itemText = eObject.getClass().getSimpleName();
        }
      } else {
        LOGGER.severe("What to show for: " + eObjectTreeItem);;
      }

      if (parentsText != null) {
        return parentsText + "/" + itemText;
      } else {
        return itemText;
      }
    }

    /**
     * Check whether the selected tree item is a list of (supertypes of) Locations.
     * 
     * @return true if the selected tree item is a list of (supertypes of) Locations, false otherwise.
     */
    private boolean selectedTreeItemIsLocationsList() {
      return VacationsTreeViewCreator.treeItemIsLocationsList(selectedTreeItem);
    }

    /**
     * Check whether the selected tree item is an item in a list of (supertypes of) Locations.
     * 
     * @return true if the selected tree item is an item in a list of (supertypes of) Locations, false otherwise.
     */
    private boolean selectedTreeItemIsLocationsListItem() {
      if (selectedTreeItem == null) {
        return false;
      }

      EObjectTreeItem parentTreeItem = (EObjectTreeItem) selectedTreeItem.getParent();
      if (parentTreeItem == null) {
        return false;
      }

      return VacationsTreeViewCreator.treeItemIsLocationsList(parentTreeItem);
    }

  } // End of AddElementToVacationPanel

}

