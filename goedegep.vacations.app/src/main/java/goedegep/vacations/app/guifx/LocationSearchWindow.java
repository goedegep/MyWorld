package goedegep.vacations.app.guifx;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.atlis.location.nominatim.NominatimAPI;
import com.atlis.location.nominatim.OSMAddress;
import com.atlis.location.nominatim.OSMLocationInfo;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.JfxUtil;
import goedegep.jfx.eobjecttreeview.EEnumEditorDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItem;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemClassListReferenceDescriptor;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemForObject;
import goedegep.jfx.eobjecttreeview.EObjectTreeItemForObjectList;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.poi.app.guifx.POIIcons;
import goedegep.poi.model.POICategoryId;
import goedegep.util.emf.EmfUtil;
import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;
import goedegep.util.string.StringUtil;
import goedegep.vacations.app.logic.NominatimUtil;
import goedegep.vacations.model.Boundary;
import goedegep.vacations.model.BoundingBox;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.VacationsFactory;
import goedegep.vacations.model.VacationsPackage;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;

/**
 * This class provides a window to search for locations.
 * <p>
 * The top part provides 3 different ways to search for locations:
 * <ul>
 * <li><b>Free text search</b><br/>
 * To search for a location based on a free format text.
 * </li>
 * <li><b>Reverse geocode search</b><br/>
 * To search for the address that corresponds to a specified latitude/longitude pair.
 * </li>
 * <li><b>Hierarchical search</b><br/>
 * To search for a location based on country, city, street and housenumber (or any subset thereof).
 * </li>
 * </ul>
 * Searching is based on the Open Street Map Nominatim API.
 * <p>
 * The center part shows all details of the search results, as obtained from the Nominatim API. There is combobox to select one of the results.
 * 
 * <p>
 * The bottom part shows the <code>Location</code> derived from the result selected in the center part.<br/>
 * The values here can be edited and then the location can be added to the <code>Vacations</code> tree.
 * <p>
 * Each of the 5 panels of this window is defined by its own class.
 */
public class LocationSearchWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(LocationSearchWindow.class.getName());
  
  /**
   * The GUI customization.
   */
  private CustomizationFx customization;
  
  /**
   * An instance of the {@link NominatimAPI} used to perform the search queries.
   */
  private NominatimAPI nominatimAPI = null;
  
  /**
   * An instance of a {@code VacationsWIndow}.
   */
  private VacationsWindow vacationsWindow;
  
  /**
   * A map layer on which the search result can be shown.
   */
  private SearchResultLayer searchResultLayer;
  
  /**
   * The factory for providing GUI elements
   */
  private ComponentFactoryFx componentFactory;
  
  /**
   * A {@link POIIcons} instance for getting POI icons
   */
  private POIIcons poiIcons;
  
  /**
   * Panel for showing status information.
   */
  private Label statusPanel;
  
  /**
   * The panel for the free text search
   */
  private FreeTextSearchPanel freeTextSearchPanel;
  
  /**
   * The panel for a reverse geocode search.
   */
  private ReverseGeoCodePanel reverseGeoCodePanel;
  
  /**
   * The panel for a hierarchical search.
   */
  private HierarchicalSearchPanel hierarchicalSearchPanel;
  
  /**
   * The panel for showing OSM search results and selecting a result.
   */
  private OsmSearchResultsPanel locationInfosPanel;
  
  /**
   * The panel for the actual {@link Location} to be added to a Vacations data structure.
   */
  private LocationPanel locationPanel;

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param poiIcons A {@link POIIcons} instance for getting POI icons.
   * @param nominatimAPI a reference to a <code>NominatimAPI</code>.
   * @param searchResultLayer a reference to a <code>SearchResultLayer</code> for showing search results.
   */
  public LocationSearchWindow(CustomizationFx customization, POIIcons poiIcons, NominatimAPI nominatimAPI, VacationsWindow vacationsWindow) {
    super("Search for a Location", customization);
    
    this.customization = customization;
    this.poiIcons = poiIcons;
    this.nominatimAPI = nominatimAPI;
    this.vacationsWindow = vacationsWindow;
    
    this.searchResultLayer = vacationsWindow.getTravelMapView().getSearchResultLayer();
    
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    VBox mainVBox = componentFactory.createVBox(12.0, 12.0);
    
    HBox searchControlsHBox = componentFactory.createHBox(12.0);
    
    // Left side is input for free text search and input for reverse geocode search.
    VBox leftSideSearchControlsBox = componentFactory.createVBox(12.0);
    
    freeTextSearchPanel = new FreeTextSearchPanel(customization, this::searchOnFreeText);
    reverseGeoCodePanel = new ReverseGeoCodePanel(customization, this::performReverseGeocodeSearch);
    leftSideSearchControlsBox.getChildren().addAll(freeTextSearchPanel, reverseGeoCodePanel);
    
    searchControlsHBox.getChildren().add(leftSideSearchControlsBox);
    
    // Right side is input for hierarchical search
    hierarchicalSearchPanel  = new HierarchicalSearchPanel(customization, this::searchHierarchical);
    searchControlsHBox.getChildren().add(hierarchicalSearchPanel);
    
    mainVBox.getChildren().add(searchControlsHBox);
    
    locationInfosPanel = new OsmSearchResultsPanel(customization, searchResultLayer);
    locationPanel = new LocationPanel(customization, reverseGeoCodePanel, vacationsWindow, poiIcons, locationInfosPanel);
    mainVBox.getChildren().addAll(locationInfosPanel, locationPanel);
    
    statusPanel = componentFactory.createLabel(null);
    mainVBox.getChildren().add(statusPanel);
    
    setScene(new Scene(mainVBox, 1400, 1300));
  }
    
  /**
   * Perform a reverse geocode search based on the latitude/longitude of the <code>reverseGeoCodePanel</code>.
   * <p>
   * The result is handed over to the {@code locationInfosPanel}.
   */
  void performReverseGeocodeSearch(double latitude, double longitude) {
    statusPanel.setText(null);
    OSMLocationInfo locationInfo;
    try {
      locationInfo = nominatimAPI.getAddressFromMapPoint(latitude, longitude);
      List<OSMLocationInfo> osmLocationInfos = new ArrayList<>();
      osmLocationInfos.add(locationInfo);
      locationInfosPanel.setLocationInfos(osmLocationInfos);
    } catch (IOException e) {
      reportProblemViaStatusPanel(e);
    }
  }
  
  /**
   * Perform a reverse geocode search for specified coordinates.
   * <p>
   * The latitude/longitude for the search are specified via parameters.
   * These values are set in the <code>reverseGeoCodePanel</code> before performing the search (by calling {@code reverseGeocodeSearch).
   */
  public void reverseGeocodeSearch(double latitude, double longitude) {
    reverseGeoCodePanel.reverseGeocodeSearch(latitude, longitude);
  }

  /**
   * Perform a free text search based on the text of the {@code freeTextSearchPanel}.
   * <p>
   * The result is handed over to the {@code locationInfosPanel}.
   */
  void searchOnFreeText(String searchText) {
    LOGGER.info("=> searchText=" + searchText);
    
    
    statusPanel.setText(null);
    try {
      List<OSMLocationInfo> osmLocationInfos = nominatimAPI.freeTextSearch(searchText);
      LOGGER.info("osmLocationInfos=" + osmLocationInfos.size());
      
      locationInfosPanel.setLocationInfos(osmLocationInfos);
    } catch (IOException e) {
      reportProblemViaStatusPanel(e);
    }
    
  }

  /**
   * Perform a hierarchical search, based on the information filled in in the {@code hierarchicalSearchPanel}.
   * <p>
   * The result is handed over to the {@code locationInfosPanel}.
   */
  void searchHierarchical(String country, String city, String street, String houseNumber) {
    try {
      List<OSMLocationInfo> osmLocationInfos = nominatimAPI.searchHierarchical(country, city, street, houseNumber);

      locationInfosPanel.setLocationInfos(osmLocationInfos);
    } catch (IOException e) {
      reportProblemViaStatusPanel(e);
    }
  }
  
  /**
   * Report an {@code IOException} information via the {@statusPanel}.
   * <p>
   * Only an {@code UnknownHostException} is reported.
   * 
   * @param e the {@code IOException} to be reported.
   */
  private void reportProblemViaStatusPanel(IOException e) {
    StringBuilder buf = new StringBuilder();
    
    buf.append("Error: the search couldn't be performed. ");
    if (e instanceof UnknownHostException) {
      UnknownHostException unknownHostException = (UnknownHostException) e;
      buf.append("Unknown host: " + unknownHostException.getLocalizedMessage());
    } else {
      LOGGER.severe("Unexpected exception: " + e.getMessage());
    }
    
    statusPanel.setText(buf.toString());
  }
}


/**
 * This class is a panel with the controls for a free text search.
 */
class FreeTextSearchPanel extends VBox {

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param performFreeTextSearchMethod a {@code FunctionalInterface} for performing a free text search.
   */
  public FreeTextSearchPanel(CustomizationFx customization, Consumer<String> performFreeTextSearchMethod) {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    setPadding(new Insets(12.0));

    Label label = componentFactory.createLabel("Free text search");
    label.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
    getChildren().add(label);    
    
    GridPane freeTextSearchParamPane = componentFactory.createGridPane(12.0, 12.0, 12.0);

    label = componentFactory.createLabel("Search text:");
    freeTextSearchParamPane.add(label, 0, 0);
    
    TextField searchTextField = componentFactory.createTextField(350, "Enter any text to search on");
    freeTextSearchParamPane.add(searchTextField, 1, 0);
    getChildren().add(freeTextSearchParamPane);
    
    Button button = componentFactory.createButton("Free text search", "Click to perform a free text search");
    button.setOnAction((actionEvent) -> performFreeTextSearchMethod.accept(searchTextField.getText()));
    getChildren().add(button);
  }  
}


/**
 * This class is a panel with the controls for a reverse geocode search.
 */
class ReverseGeoCodePanel extends VBox {
  private TextField latitudeTextField;  
  private TextField longitudeTextField;
  private BiConsumer<Double, Double> reverseGeocodeSearchMethod;

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param locationSearchWindow the main window.
   */
  public ReverseGeoCodePanel(CustomizationFx customization, BiConsumer<Double, Double> reverseGeocodeSearchMethod) {
    this.reverseGeocodeSearchMethod = reverseGeocodeSearchMethod;
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    setPadding(new Insets(12.0));

    Label label = componentFactory.createLabel("Reverse geocode search");
    label.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
    getChildren().add(label);

    GridPane reverseGeoCodeSearchParamPane = componentFactory.createGridPane(12.0, 12.0, 12.0);

    label = componentFactory.createLabel("Latitude:");
    reverseGeoCodeSearchParamPane.add(label, 0, 0);
    
    latitudeTextField = componentFactory.createTextField(350, "Enter latitude to search on");
    reverseGeoCodeSearchParamPane.add(latitudeTextField, 1, 0);

    label = componentFactory.createLabel("Longitude:");
    reverseGeoCodeSearchParamPane.add(label, 0, 1);
    
    longitudeTextField = componentFactory.createTextField(350, "Enter longitude to search on");
    reverseGeoCodeSearchParamPane.add(longitudeTextField, 1, 1);
    
    getChildren().add(reverseGeoCodeSearchParamPane);
    
    Button button = componentFactory.createButton("Reverse geocode search", "Click to perform a reverse geocode search (find an address for coordinates)");
    button.setOnAction((actionEvent) -> reverseGeocodeSearchMethod.accept(getLatitude(), getLongitude()));
    getChildren().add(button);
  }
  
  /**
   * Get the latitude coordinate.
   * 
   * @return the latitude coordinate.
   */
  Double getLatitude() {
    return Double.valueOf(latitudeTextField.getText());
  }
  
  /**
   * Get the longitude coordinate.
   * 
   * @return the longitude coordinate.
   */
  Double getLongitude() {
    return Double.valueOf(longitudeTextField.getText());
  }
  
  /**
   * Set the coordinates.
   */
  public void reverseGeocodeSearch(double latitude, double longitude) {
    latitudeTextField.setText(String.valueOf(latitude));
    longitudeTextField.setText(String.valueOf(longitude));
    reverseGeocodeSearchMethod.accept(latitude, longitude);
  }
}


/**
 * Interface which defines a hierarchical search method (used as callback in the {@code HierarchicalSearchPanel)).
 */
@FunctionalInterface
interface HierarchicalSearch {
  void searchHierarchical(String country, String city, String street, String houseNumber);
}


/**
 * This class is a panel with the controls for a hierarchical search.
 */
class HierarchicalSearchPanel extends VBox {
  private TextField countryTextField;
  private TextField cityTextField;
  private TextField streetTextField;
  private TextField houseNumberTextField;

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param locationSearchWindow the main window.
   */
  public HierarchicalSearchPanel(CustomizationFx customization, HierarchicalSearch hierarchicalSearch) {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    setPadding(new Insets(12.0));
    
    Label label = componentFactory.createLabel("Hierarchical search");
    label.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
    getChildren().add(label);

    GridPane hierarchicalSearchParamPane = componentFactory.createGridPane(12.0, 12.0, 12.0);

    label = componentFactory.createLabel("Country:");
    hierarchicalSearchParamPane.add(label, 0, 0);

    countryTextField = componentFactory.createTextField(350, "Country");
    hierarchicalSearchParamPane.add(countryTextField, 1, 0);

    label = componentFactory.createLabel("City:");
    hierarchicalSearchParamPane.add(label, 0, 1);

    cityTextField = componentFactory.createTextField(350, "City");
    hierarchicalSearchParamPane.add(cityTextField, 1, 1);

    label = componentFactory.createLabel("Street:");
    hierarchicalSearchParamPane.add(label, 0, 2);

    streetTextField = componentFactory.createTextField(350, "Street");
    hierarchicalSearchParamPane.add(streetTextField, 1, 2);

    label = componentFactory.createLabel("Housenumber:");
    hierarchicalSearchParamPane.add(label, 0, 3);

    houseNumberTextField = componentFactory.createTextField(350, "Housenumber");
    hierarchicalSearchParamPane.add(houseNumberTextField, 1, 3);
    
    getChildren().add(hierarchicalSearchParamPane);
    
    Button performFreeTextSearchButton = componentFactory.createButton("Hierarchical search", "Click to perform a hierarchical search");
    performFreeTextSearchButton.setOnAction((actionEvent) -> hierarchicalSearch.searchHierarchical(getCountry(), getCity(), getStreet(), getHouseNumber()));
    getChildren().add(performFreeTextSearchButton);
  }
  
  /**
   * Get the country name.
   * 
   * @return the country name.
   */
  private String getCountry() {
    return JfxUtil.getTextFieldText(countryTextField);
  }
  
  /**
   * Get the city name.
   * 
   * @return the city name.
   */
  private String getCity() {
    return JfxUtil.getTextFieldText(cityTextField);
  }
  
  /**
   * Get the streetname.
   * 
   * @return the streetname.
   */
  private String getStreet() {
    return JfxUtil.getTextFieldText(streetTextField);
  }
  
  /**
   * Get the housenumber.
   * 
   * @return the housenumber
   */
  private String getHouseNumber() {
    return JfxUtil.getTextFieldText(houseNumberTextField);
  }
}


/**
 * This class provides a panel to show the OSMLocationInfo (including the OSMResponseAddress) values obtained from an OSM query and to select one of the results.
 * <p>
 * At the top is a ComboBox listing all values by their 'display_name'.<br/>
 * For the selected value the details are shown:
 * 'osm_type' - values seen so far: 'relation'
 * 'osm_id' - a unique number in the database? (can be removed)
 * 'boundingbox' - box around the location
 * 'lat', 'lon' - coordinates shown as 'coordinates'.
 * 'class' - e.g. boundary, shop
 * 'type' - e.g. administrative, art
 * 'importance' - a floating point number, e.g. 0.7039505210664336
 * 'icon' - the URL of the related icon
 * 
 * The following details are not shown, as they are not interesting:
 * 'place_id' - a unique number in the database?
 * 'license' - copyright information
 * 'display_name' - already shown in the location selection ComboBox
 */
class OsmSearchResultsPanel extends VBox implements ObjectSelector<OSMLocationInfo> {
  private static final Logger LOGGER = Logger.getLogger(OsmSearchResultsPanel.class.getName());
  
  private SearchResultLayer searchResultLayer;      // A map layer on which to show the results
  private List<OSMLocationInfo> locationInfos;      // The information returned from the OSM Nominatim API
  private ComboBox<String> displayNamesComboBox;    // Lists the display names of the <code>locationInfos</code>
  
  // OSMLocationInfo attributes
  private TextField osmTypeTextField;               // Shows the 'osm_type' of the selected (via <code>displayNamesComboBox</code> <code>OSMLocationInfo</code>.
  private TextField osmIdTextField;                 // Shows the 'osm_id' of the selected (via <code>displayNamesComboBox</code> <code>OSMLocationInfo</code>.
  private TextField classTextField;                 // Currently not used. As 'class is a java keyword, it cannot be a field in OSMLocationInfo and retrieved from JSON               
  private TextField coordinatesTextField;           // Shows the 'lat', 'lon' of the selected (via <code>displayNamesComboBox</code> <code>OSMLocationInfo</code>.
  private TextField boundingBoxTextField;           // Shows the 'boundingbox' of the selected (via <code>displayNamesComboBox</code> <code>OSMLocationInfo</code>.
  private TextField typeTextField;                  // Shows the 'type' of the selected (via <code>displayNamesComboBox</code> <code>OSMLocationInfo</code>.
  private TextField importanceTextField;            // Shows the 'importance' of the selected (via <code>displayNamesComboBox</code> <code>OSMLocationInfo</code>.
  private ImageView iconImageView;                  // Shows the 'icon' of the selected (via <code>displayNamesComboBox</code> <code>OSMLocationInfo</code>.
  
  // OSMAddress attributes
  private TextField countryTextField;
  private TextField countryCodeTextField;
  private TextField countyTextField;
  private TextField stateTextField;
  private TextField cityTextField;
  private TextField villageTextField;
  private TextField suburbTextField;
  private TextField neighbourhoodTextField;
  private TextField roadTextField;
  private TextField pedestrianTextField;
  private TextField housenumberTextField;
  private TextField housenameTextField;
  private TextField postcodeTextField;
  private TextField poiTypeTextField;
  private TextField poiValueTextField;
  private TextArea nominatimResultString;

  private OSMLocationInfo osmLocationInfo;
  private Integer idOfLocationShownOnMap = null;    // The id, returned by the <code>searchResultLayer</code> of a location shown on this layer.
  
  private List<ObjectSelectionListener<OSMLocationInfo>> objectSelectionListeners = new ArrayList<>();
  
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param searchResultLayer a map layer on which to show a search result
   */
  public OsmSearchResultsPanel(CustomizationFx customization, SearchResultLayer searchResultLayer) {
    this.searchResultLayer = searchResultLayer;
    
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    setPadding(new Insets(12.0));
    

    Label label = componentFactory.createLabel("Open Street Map search results");
    label.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
    getChildren().add(label);
    
    GridPane gridPane;
    
    /*
     * Search results of which one can be selected for details
     */
    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    // Display names: Label + ComboBox
    label = componentFactory.createLabel("Search results:");
    gridPane.add(label, 0, 0);
    
    displayNamesComboBox = componentFactory.createComboBox(null);
    displayNamesComboBox.setOnAction((actionEvent) -> updateLocationInfo());
    gridPane.add(displayNamesComboBox, 1, 0);
    
    this.getChildren().add(gridPane);

    /*
     * Details for selected location information
     */
    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    // osm_type
    int row = 0;
    label = componentFactory.createLabel("OSM type:");
    gridPane.add(label, 0, row);
    
    osmTypeTextField = componentFactory.createTextField(200, null);
    osmTypeTextField.setEditable(false);
    gridPane.add(osmTypeTextField, 1, row);
    
    
    // osm_id - unique Id in a single database instance.
    label = componentFactory.createLabel("OSM id:");
    gridPane.add(label, 2, row);
    
    osmIdTextField = componentFactory.createTextField(200, null);
    osmIdTextField.setEditable(false);
    gridPane.add(osmIdTextField, 3, row);
    
    
    // class
    label = componentFactory.createLabel("Class:");
    gridPane.add(label, 4, row);

    classTextField = componentFactory.createTextField(200, null);
    classTextField.setEditable(false);
    gridPane.add(classTextField, 5, row);
    
    // lat, lon and bounding box
    row++;
    label = componentFactory.createLabel("Lat/lon:");
    gridPane.add(label, 0, row);
    
    coordinatesTextField = componentFactory.createTextField(200, null);
    coordinatesTextField.setEditable(false);
    gridPane.add(coordinatesTextField, 1, row);
    
    // bounding box
    label = componentFactory.createLabel("Bounding box:");
    gridPane.add(label, 2, row);
    
    boundingBoxTextField = componentFactory.createTextField(300, null);
    boundingBoxTextField.setEditable(false);
    gridPane.add(boundingBoxTextField, 3, row);
    
    row++;
    
    // type, importance and icon
    label = componentFactory.createLabel("Type:");
    gridPane.add(label, 0, row);
    
    typeTextField = componentFactory.createTextField(200, null);
    typeTextField.setEditable(false);
    gridPane.add(typeTextField, 1, row);
    
    // importance
    label = componentFactory.createLabel("Importance:");
    gridPane.add(label, 2, row);
    
    importanceTextField = componentFactory.createTextField(200, null);
    importanceTextField.setEditable(false);
    gridPane.add(importanceTextField, 3, row);
    
    label = componentFactory.createLabel("Icon:");
    gridPane.add(label, 4, row);
    
    iconImageView = new ImageView();
    gridPane.add(iconImageView, 5, row);
    
    /*
     * OSMAddress attributes
     */
    
    row++;
        
    // Country
    label = componentFactory.createLabel("Country");
    gridPane.add(label, 0, row);
    
    countryTextField = componentFactory.createTextField(350, null);
    countryTextField.setEditable(false);
    gridPane.add(countryTextField, 1, row);
    
    // Country code
    label = componentFactory.createLabel("Country code");
    gridPane.add(label, 2, row);
    
    countryCodeTextField = componentFactory.createTextField(350, null);
    countryCodeTextField.setEditable(false);
    gridPane.add(countryCodeTextField, 3, row);
    
    row++;
    
    // County
    label = componentFactory.createLabel("County");
    gridPane.add(label, 0, row);
    
    countyTextField = componentFactory.createTextField(350, null);
    countyTextField.setEditable(false);
    gridPane.add(countyTextField, 1, row);
    
    // State
    label = componentFactory.createLabel("State");
    gridPane.add(label, 2, row);
    
    stateTextField = componentFactory.createTextField(350, null);
    stateTextField.setEditable(false);
    gridPane.add(stateTextField, 3, row);
    
    row++;
    
    // City
    label = componentFactory.createLabel("City");
    gridPane.add(label, 0, row);
    
    cityTextField = componentFactory.createTextField(350, null);
    cityTextField.setEditable(false);
    gridPane.add(cityTextField, 1, row);
    
    // Village
    label = componentFactory.createLabel("Village");
    gridPane.add(label, 2, row);
    
    villageTextField = componentFactory.createTextField(350, null);
    villageTextField.setEditable(false);
    gridPane.add(villageTextField, 3, row);
    
    row++;
    
    // Suburb
    label = componentFactory.createLabel("Suburb");
    gridPane.add(label, 0, row);
    
    suburbTextField = componentFactory.createTextField(350, null);
    suburbTextField.setEditable(false);
    gridPane.add(suburbTextField, 1, row);
    
    // Neighbourhood
    label = componentFactory.createLabel("Neighbourhood");
    gridPane.add(label, 2, row);
    
    neighbourhoodTextField = componentFactory.createTextField(350, null);
    neighbourhoodTextField.setEditable(false);
    gridPane.add(neighbourhoodTextField, 3, row);
    
    row++;
    
    // Road
    label = componentFactory.createLabel("Road");
    gridPane.add(label, 0, row);
    
    roadTextField = componentFactory.createTextField(350, null);
    roadTextField.setEditable(false);
    gridPane.add(roadTextField, 1, row);
    
    // Pedestrian
    label = componentFactory.createLabel("Pedestrian");
    gridPane.add(label, 2, row);
    
    pedestrianTextField = componentFactory.createTextField(350, null);
    pedestrianTextField.setEditable(false);
    gridPane.add(pedestrianTextField, 3, row);
    
    // Housenumber
    label = componentFactory.createLabel("Housenumber");
    gridPane.add(label, 4, row);
    
    housenumberTextField = componentFactory.createTextField(200, null);
    housenumberTextField.setEditable(false);
    gridPane.add(housenumberTextField, 5, row);
    
    row++;
    
    // Housename
    label = componentFactory.createLabel("Housename");
    gridPane.add(label, 0, row);
    
    housenameTextField = componentFactory.createTextField(350, null);
    housenameTextField.setEditable(false);
    gridPane.add(housenameTextField, 1, row);
    
    row++;
    
    // Postcode
    label = componentFactory.createLabel("Postcode");
    gridPane.add(label, 0, row);
    
    postcodeTextField = componentFactory.createTextField(350, null);
    postcodeTextField.setEditable(false);
    gridPane.add(postcodeTextField, 1, row);
    
    row++;
    
    // POI Type
    label = componentFactory.createLabel("POI Type");
    gridPane.add(label, 0, row);
    
    poiTypeTextField = componentFactory.createTextField(350, null);
    poiTypeTextField.setEditable(false);
    gridPane.add(poiTypeTextField, 1, row);
    
    // POI Value
    label = componentFactory.createLabel("POI Value");
    gridPane.add(label, 2, row);
    
    poiValueTextField = componentFactory.createTextField(350, null);
    poiValueTextField.setEditable(false);
    gridPane.add(poiValueTextField, 3, row);
    
    row++;
    
    // Full nominatim search result
    label = componentFactory.createLabel("Nominatim result");
    gridPane.add(label, 0, row);

    nominatimResultString = componentFactory.createTextArea();
    nominatimResultString.setWrapText(true);
    gridPane.add(nominatimResultString, 1, row, 5, 2);

    this.getChildren().add(gridPane);
  }
  
  /**
   * Set the list of location information values.
   * 
   * @param locationInfos a list of <OSMLocationInfo> values
   */
  public void setLocationInfos(List<OSMLocationInfo> locationInfos) {
    LOGGER.info("=> " + (locationInfos != null ? "number of locations: " + locationInfos.size() : "locationInfos = null"));
    this.locationInfos = locationInfos;
    displayNamesComboBox.getItems().clear();
    ObservableList<String> items = displayNamesComboBox.getItems();
    
    for (OSMLocationInfo locationInfo: locationInfos) {
      items.add(locationInfo.getDisplay_name());
    }
    
    if (items.size() != 0) {
      displayNamesComboBox.getSelectionModel().select(0);
    }
  }
  
  /*
   * Update the location details for the selected location.
   */
  private void updateLocationInfo() {
    osmLocationInfo = null;

    int selectedIndex = displayNamesComboBox.getSelectionModel().getSelectedIndex();
    
    if (selectedIndex != -1) {
      osmLocationInfo = locationInfos.get(selectedIndex);

      osmTypeTextField.setText(osmLocationInfo.getOsm_type());
      classTextField.setText(osmLocationInfo.getCategory());
      osmIdTextField.setText(osmLocationInfo.getOsm_id());
      coordinatesTextField.setText(osmLocationInfo.getLat() + "," + osmLocationInfo.getLon());
      Double[] bb = osmLocationInfo.getBoundingbox();
      if (bb != null) {
        boundingBoxTextField.setText(bb[0] + ", " + bb[1] + ", " + bb[2] + ", " + bb[3]);
      }
      typeTextField.setText(osmLocationInfo.getType());
      
      Double importance = osmLocationInfo.getImportance();
      if (importance != null) {
        importanceTextField.setText(importance.toString());
      } else {
        importanceTextField.setText(null);
      }

      String iconUrl = osmLocationInfo.getIcon();
      if (iconUrl != null) {
        Image iconImage = new Image(osmLocationInfo.getIcon());
        iconImageView.setImage(iconImage);
      } else {
        iconImageView.setImage(null);
      }
      
      nominatimResultString.setText(osmLocationInfo.getNominatimResponse());

      if (searchResultLayer != null) {
        if (idOfLocationShownOnMap != null) {
          searchResultLayer.removeLocation(idOfLocationShownOnMap);
        }
        LOGGER.info("Adding location to search results layer");

        List<Boundary> boundaries = NominatimUtil.getBoundaries(osmLocationInfo);
        List<List<WGS84Coordinates>> polylines = null;
        if (!boundaries.isEmpty()) {
          polylines = new ArrayList<>();
          for (Boundary boundary: boundaries) {
            LOGGER.info("Adding boundary: " + boundary.getPoints());
            polylines.add(boundary.getPoints());
          }
        }
        
        WGS84BoundingBox boundingBox = new WGS84BoundingBox(bb[2], bb[0], bb[3], bb[1]);
        idOfLocationShownOnMap = searchResultLayer.addLocation(osmLocationInfo.getLat(), osmLocationInfo.getLon(), boundingBox, polylines);
      }
    } else {
      osmTypeTextField.setText(null);
      classTextField.setText(null);
      osmIdTextField.setText(null);
      coordinatesTextField.setText(null);
      boundingBoxTextField.setText(null);
      typeTextField.setText(null);
      importanceTextField.setText(null);

      iconImageView.setImage(null);

      if (idOfLocationShownOnMap != null) {
        searchResultLayer.removeLocation(idOfLocationShownOnMap);
        idOfLocationShownOnMap = null;
      }
    }
    
    OSMAddress osmResponseAddress = null;
    if (osmLocationInfo != null) {
      osmResponseAddress = osmLocationInfo.getAddress();
    }
    
    if (osmResponseAddress != null) {
      countryTextField.setText(osmResponseAddress.getCountry());
      countryCodeTextField.setText(osmResponseAddress.getCountry_code());
      countyTextField.setText(osmResponseAddress.getCounty());
      stateTextField.setText(osmResponseAddress.getState());
      String cityOrTownName = osmResponseAddress.getCity();
      if (cityOrTownName == null) {
        cityOrTownName = osmResponseAddress.getTown();
      }
      cityTextField.setText(cityOrTownName);
      villageTextField.setText(osmResponseAddress.getVillage());
      suburbTextField.setText(osmResponseAddress.getSuburb());
      neighbourhoodTextField.setText(osmResponseAddress.getNeighbourhood());
      roadTextField.setText(osmResponseAddress.getRoad());
      pedestrianTextField.setText(osmResponseAddress.getPedestrian());
      housenumberTextField.setText(osmResponseAddress.getHousenumber());
      housenameTextField.setText(osmResponseAddress.getHousename());
      postcodeTextField.setText(osmResponseAddress.getPostcode());
      Pair<String, String> poiInfo = NominatimAPI.getPoiInfoFromAddress(osmResponseAddress);
      if (poiInfo != null) {
        poiTypeTextField.setText(poiInfo.getKey());
        poiValueTextField.setText(poiInfo.getValue());
      }
    } else {
      countryTextField.setText(null);
      countryCodeTextField.setText(null);
      countyTextField.setText(null);
      stateTextField.setText(null);
      cityTextField.setText(null);
      suburbTextField.setText(null);
      neighbourhoodTextField.setText(null);
      roadTextField.setText(null);
      pedestrianTextField.setText(null);
      housenumberTextField.setText(null);
      housenameTextField.setText(null);
      postcodeTextField.setText(null);
      poiTypeTextField.setText(null);
      poiValueTextField.setText(null);
    }
    
    notifyListeners();
  }

  @Override
  public void addObjectSelectionListener(ObjectSelectionListener<OSMLocationInfo> objectSelectionListener) {
    objectSelectionListeners.add(objectSelectionListener);
    
  }

  @Override
  public void removeObjectSelectionListener(ObjectSelectionListener<OSMLocationInfo> objectSelectionListener) {
    objectSelectionListeners.remove(objectSelectionListener);    
  }

  @Override
  public OSMLocationInfo getSelectedObject() {
    return osmLocationInfo;
  }
  
  private void notifyListeners() {
    for (ObjectSelectionListener<OSMLocationInfo> objectSelectionListener: objectSelectionListeners) {
      objectSelectionListener.objectSelected(this, osmLocationInfo);
    }
  }
}

/**
 * This class is a panel that shows the selected OSM search result, translated to a {@code Location}.
 * <p>
 * The values can be edited and then added to a node in a Vacations tree or update a Location.
 */
class LocationPanel extends VBox {
  private static final Logger LOGGER = Logger.getLogger(LocationPanel.class.getName());
  private static VacationsFactory VACATIONS_FACTORY = VacationsFactory.eINSTANCE;
  private static final String BEFORE_TEXT = "before";
  private static final String AFTER_TEXT = "after";

//  private VacationsWindow vacationsWindow;
  private ComponentFactoryFx componentFactory;
  private ReverseGeoCodePanel reverseGeoCodePanel;
  private VacationsWindow vacationsWindow;
  private POIIcons poiIcons;
  private EObjectTreeItem selectedTreeItem;
  
  private TextField nameTextField;
  private TextField countryTextField;
  private TextField cityTextField;
  private TextField streetTextField;
  private TextField houseNumberTextField;
  private EEnumEditorDescriptor<POICategoryId> eEnumEditorDescriptor;
  private ComboBox<String> locationTypeComboBox;
  private ImageView locationTypeIconImageView;
  private TextField coordinatesTextField;
  private TextField boundingBoxTextField;
  
  private VBox actionVBox;
  private Label infoLabel;
  private Button addLocationButton;
  private Button setLocationButton;
  private Button updateBoundaryButton;
  private Label addAsLastChildToListLabel;
  private ComboBox<String> beforeOrAfterComboBox;
  private TextField selectedTreeItemDescription;
  private CheckBox useReverseGeocodeSearchCoordinates;
  
  // Note: the polyline coordinates are currently not shown, and can therefore not be edited.
  private List<Boundary> boundaries;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param reverseGeoCodePanel the {@code ReverseGeoCodePanel} from which the reverse geocode coordinates can be obtained.
   * @param vacationsWindow a {@code VacationsWindow} used to get the tree view, the map view and to bring it to front (in case of 'show on map').
   * @param poiIcons a reference to {@code POIIcons} used to show an icon for the selected {@code POICategoryId}.
   * @param osmLocationInfoSelector provider of the selected OSM search result
   */
  public LocationPanel(CustomizationFx customization, ReverseGeoCodePanel reverseGeoCodePanel, VacationsWindow vacationsWindow, POIIcons poiIcons, ObjectSelector<OSMLocationInfo> osmLocationInfoSelector) {
    this.reverseGeoCodePanel = reverseGeoCodePanel;
    this.vacationsWindow = vacationsWindow;
    this.poiIcons = poiIcons;
    
    eEnumEditorDescriptor = EEnumEditorDescriptorForPOIs.getInstance();
    
    setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    setPadding(new Insets(12.0));
    
    componentFactory = customization.getComponentFactoryFx();

    Label label = componentFactory.createLabel("Location");
    label.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
    getChildren().add(label);
    
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    int row = 0;
    
    // name
    label = componentFactory.createLabel("Name:");
    gridPane.add(label, 0, row);
    
    nameTextField = componentFactory.createTextField(200, null);
    gridPane.add(nameTextField, 1, row);
    
    row++;
    
    // country, city
    label = componentFactory.createLabel("Country:");
    gridPane.add(label, 0, row);
    
    countryTextField = componentFactory.createTextField(200, null);
    gridPane.add(countryTextField, 1, row);
    
    label = componentFactory.createLabel("City:");
    gridPane.add(label, 2, row);
    
    cityTextField = componentFactory.createTextField(200, null);
    gridPane.add(cityTextField, 3, row);
    
    row++;
    
    // street, housenumber
    label = componentFactory.createLabel("Street:");
    gridPane.add(label, 0, row);
    
    streetTextField = componentFactory.createTextField(200, null);
    gridPane.add(streetTextField, 1, row);
    
    label = componentFactory.createLabel("Housenumber:");
    gridPane.add(label, 2, row);
    
    houseNumberTextField = componentFactory.createTextField(200, null);
    gridPane.add(houseNumberTextField, 3, row);
    
    row++;
    
    // location type + icon
    label = componentFactory.createLabel("Location type:");
    gridPane.add(label, 0, row);
    
    locationTypeComboBox = componentFactory.createComboBox(eEnumEditorDescriptor.getDisplayNames());
    locationTypeComboBox.setOnAction((actionEvent) -> {
      Object choice = locationTypeComboBox.getValue();
      if (choice != null) {
        POICategoryId poiCategoryId = eEnumEditorDescriptor.getEEnumLiteralForDisplayName((String) choice);
        Image locationIcon = poiIcons.getIcon(poiCategoryId);
        locationTypeIconImageView.setImage(locationIcon);
      } else {
        locationTypeIconImageView.setImage(null);
      }      
    });
    gridPane.add(locationTypeComboBox, 1, row);
    
    label = componentFactory.createLabel("Location type icon:");
    gridPane.add(label, 2, row);
    
    locationTypeIconImageView = new ImageView();
    gridPane.add(locationTypeIconImageView, 3, row);
    
    row++;
    
    // lat/long, bounding box, 'show on map' button
    label = componentFactory.createLabel("Lat/lon:");
    gridPane.add(label, 0, row);
    
    coordinatesTextField = componentFactory.createTextField(200, null);
    gridPane.add(coordinatesTextField, 1, row);
    
    label = componentFactory.createLabel("Bounding box:");
    gridPane.add(label, 2, row);
    
    boundingBoxTextField = componentFactory.createTextField(200, null);
    gridPane.add(boundingBoxTextField, 3, row);
    
    Button button = componentFactory.createButton("show on map", null);
    button.setOnAction(e -> focusMapViewOnLocation());
    gridPane.add(button, 4, row);
    
    this.getChildren().add(gridPane);
    
    actionVBox = componentFactory.createVBox(12.0);
    
    // actionHBox items for when no tree item is selected to add the location to.
    infoLabel = componentFactory.createLabel("Select a node in the Vacations tree where a Location can be added or updated");
    
    // actionHBox items for when a list of Locations is selected
    addLocationButton = componentFactory.createButton("Add location", "Add this location at the selected tree item");
    addLocationButton.setOnAction((actionEvent) -> addLocation());
    setLocationButton = componentFactory.createButton("Set location", "Change the selected location to the specified value");
    setLocationButton.setOnAction((actionEvent) -> setLocation());
    updateBoundaryButton = componentFactory.createButton("Update boundary", "Change the boundary and bounding box of the selected location to the specified values");
    updateBoundaryButton.setOnAction((actionEvent) -> updateBoundary());
    addAsLastChildToListLabel = componentFactory.createLabel("as last child to");
    beforeOrAfterComboBox = componentFactory.createComboBox(null);
    beforeOrAfterComboBox.getItems().addAll(BEFORE_TEXT, AFTER_TEXT);
    beforeOrAfterComboBox.getSelectionModel().select(AFTER_TEXT);
    selectedTreeItemDescription = componentFactory.createTextField(800, null);
    selectedTreeItemDescription.setEditable(false);
    
    useReverseGeocodeSearchCoordinates = componentFactory.createCheckBox("use Reverse geocode search coordinates", false);
    
    this.getChildren().add(actionVBox);
    
    EObjectTreeView treeView = vacationsWindow.getTreeView();
    treeView.addObjectSelectionListener((source, selectedTreeItem) -> handleNewTreeItemSelected(selectedTreeItem));
    osmLocationInfoSelector.addObjectSelectionListener((source, osmLocationInfo) -> setLocation(osmLocationInfo));
    handleNewTreeItemSelected(treeView.getSelectedObject());
  }
  
  /**
   * Focus the map view on the current coordinates.
   */
  private void focusMapViewOnLocation() {
    WGS84Coordinates coordinates = getCoordinates();
    if (coordinates != null) {
      vacationsWindow.toFront();

      MapView mapView = vacationsWindow.getTravelMapView();
      MapPoint mapPoint = new MapPoint(coordinates.getLatitude(), coordinates.getLongitude());
      mapView.flyTo(0, mapPoint, 2);
    }
  }
   
  /**
   * Fill the controls based on the information of {@code OSMLocationInfo}.
   * <p>
   * The {@code OSMLocationInfo} is translated to a {@code Location} and then the controls are filled from this value.
   * 
   * @param osmLocationInfo the {@code OSMLocationInfo} from which the controls are to be filled.
   */
  private void setLocation(OSMLocationInfo osmLocationInfo) {
    Location location = null;
    if (osmLocationInfo != null) {
      location = NominatimUtil.osmLocationInfoToLocation(osmLocationInfo);
    }
    
    if (location != null) {
      if (location.isSetName()) {
        nameTextField.setText(location.getName());
      } else {
        nameTextField.setText(null);
      }
        
      if (location.isSetCountry()) {
        countryTextField.setText(location.getCountry());
      } else {
        countryTextField.setText(null);
      }
        
      if (location.isSetCity()) {
        cityTextField.setText(location.getCity());
      } else {
        cityTextField.setText(null);
      }

      if (location.isSetStreet()) {
        streetTextField.setText(location.getStreet());
      } else {
        streetTextField.setText(null);
      }

      if (location.isSetHouseNumber()) {
        houseNumberTextField.setText(location.getHouseNumber());
      } else {
        houseNumberTextField.setText(null);
      }

      if (location.isSetLocationType()) {
        POICategoryId poiCategoryId = location.getLocationType();
        LOGGER.info("poiCategoryId=" + poiCategoryId.getLiteral() + ", " + poiCategoryId.getName());
        String poiLiteral = eEnumEditorDescriptor.getDisplayNameForEEnumLiteral(poiCategoryId);
        LOGGER.info("poiLiteral=" + poiLiteral);
        locationTypeComboBox.setValue(poiLiteral);
        
        Image locationIcon = poiIcons.getIcon(location.getLocationType());
        locationTypeIconImageView.setImage(locationIcon);
      } else {
        locationTypeIconImageView.setImage(null);
      }

      if (location.isSetLatitude()  &&  location.isSetLongitude()) {
        coordinatesTextField.setText(location.getLatitude() + ", " + location.getLongitude());
      } else {
        coordinatesTextField.setText(null);
      }

      if (location.isSetBoundingbox()) {
        BoundingBox boundingBox = location.getBoundingbox();
        boundingBoxTextField.setText(boundingBox.getNorth() + ", " + boundingBox.getEast() + ", " + boundingBox.getSouth() + ", " + boundingBox.getWest());
      } else {
        boundingBoxTextField.setText(null);
      }

      boundaries = location.getBoundaries();
    } else {
      nameTextField.setText(null);
      countryTextField.setText(null);
      cityTextField.setText(null);
      streetTextField.setText(null);
      houseNumberTextField.setText(null);
      locationTypeIconImageView.setImage(null);
      coordinatesTextField.setText(null);
      boundingBoxTextField.setText(null);
      boundaries = null;
    }
  }
  
  /**
   * Create a <code>Location</code> from the values specified in this panel.
   * <p>
   * If the <code>useReverseGeocodeSearchCoordinates</code> checkbox is checked, the coordinates are not taken from this panel,
   * but from the <code>ReverseGeocodePanel</code> (if available).
   */
  public Location getLocation() {
    Location location = VACATIONS_FACTORY.createLocation();
    
    String value;
    List<String> values;
    
    value = JfxUtil.getTextFieldText(nameTextField);
    if (value != null) {
      location.setName(value);
    }
    
    value = JfxUtil.getTextFieldText(countryTextField);
    if (value != null) {
      location.setCountry(value);
    }
    
    value = JfxUtil.getTextFieldText(cityTextField);
    if (value != null) {
      location.setCity(value);
    }
    
    value = JfxUtil.getTextFieldText(streetTextField);
    if (value != null) {
      location.setStreet(value);
    }
    
    value = JfxUtil.getTextFieldText(houseNumberTextField);
    if (value != null) {
      location.setHouseNumber(value);
    }
    
    Object choice = locationTypeComboBox.getValue();
    POICategoryId poiCategoryId = eEnumEditorDescriptor.getEEnumLiteralForDisplayName((String) choice);
    location.setLocationType(poiCategoryId);
    
    if (useReverseGeocodeSearchCoordinates.isSelected()) {
      Double latitude = reverseGeoCodePanel.getLatitude();
      Double longitude = reverseGeoCodePanel.getLongitude();
      if ((latitude != null)  &&  (longitude != null)) {
        location.setLatitude(latitude);
        location.setLongitude(longitude);
      }
    } else {
      WGS84Coordinates coordinates = getCoordinates();
      if (coordinates != null) {
        location.setLatitude(coordinates.getLatitude());
        location.setLongitude(coordinates.getLongitude());
      }
    }
    
    value = JfxUtil.getTextFieldText(boundingBoxTextField);
    if (value != null) {
      values = StringUtil.commaSeparatedValuesToListOfValues(value);
      if (values.size() == 4) {
        BoundingBox boundingBox = VACATIONS_FACTORY.createBoundingBox();
        boundingBox.setNorth(Double.valueOf(values.get(0)));
        boundingBox.setEast(Double.valueOf(values.get(1)));
        boundingBox.setSouth(Double.valueOf(values.get(2)));
        boundingBox.setWest(Double.valueOf(values.get(3)));
        location.setBoundingbox(boundingBox);
      }
    }
    
    List<Boundary> locationBoundaries = location.getBoundaries();
    locationBoundaries.clear();
    if (boundaries != null) {
      locationBoundaries.addAll(boundaries);
    }

    return location;
  }
  
  /**
   * Create a WGS84Coordinates value from the value of the coordinatesTextField.
   * 
   * @return the WGS84Coordinates value from the value of the coordinatesTextField.
   */
  private WGS84Coordinates getCoordinates() {
    WGS84Coordinates coordinates = null;
    
    String value = JfxUtil.getTextFieldText(coordinatesTextField);
    if (value != null) {
      List<String> values = StringUtil.commaSeparatedValuesToListOfValues(value);
      if (values.size() == 2) {
        coordinates = new WGS84Coordinates(Double.valueOf(values.get(0)), Double.valueOf(values.get(1)));
      }
    }
    
    return coordinates;
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
  private void addLocation() {
    Location location = getLocation();
    
    // Always deselect use reverse geocoordinates, in order to prevent that next 'add' or 'set' uses this unintentionally.
    useReverseGeocodeSearchCoordinates.setSelected(false);
        
    Object eObjectTreeItemContent = selectedTreeItem.getValue();
    
    // If the selected tree item is a list of (a supertype of) Locations, it is added as last child of this list.
    EStructuralFeature eStructuralFeature = selectedTreeItem.getEStructuralFeature();
    if (eStructuralFeature instanceof EReference) {
      EReference eReference = (EReference) eStructuralFeature;
      EClass listType = eReference.getEReferenceType();
      EClass locationEClass = VacationsPackage.eINSTANCE.getLocation();
      if (EmfUtil.isInstanceof(locationEClass, listType)  && eReference.isMany()) {
        LOGGER.severe("match");
        @SuppressWarnings("unchecked")
        EList<EObject> eObjectList = (EList<EObject>) eObjectTreeItemContent;

        eObjectList.add(location);
        
        return;
      }
    }
    
    // If the selected tree item is an item in a list of (a supertype of) Locations, it is added to this list before or after the selected item.
    // In this case the parent item of the selected item shall be a list of (a supertype of) Locations.
    EObjectTreeItem parentTreeItem = (EObjectTreeItem) selectedTreeItem.getParent();
    Object eObjectTreeItemContentParentTreeItem = parentTreeItem.getValue();
    EStructuralFeature eStructuralFeatureParentTreeItem = parentTreeItem.getEStructuralFeature();
    if (eStructuralFeatureParentTreeItem instanceof EReference) {
      EReference eReference = (EReference) eStructuralFeatureParentTreeItem;
      EClass listType = eReference.getEReferenceType();
      EClass locationEClass = VacationsPackage.eINSTANCE.getLocation();
      if (EmfUtil.isInstanceof(locationEClass, listType)  && eReference.isMany()) {
        LOGGER.info("parent match");
        @SuppressWarnings("unchecked")
        EList<EObject> eObjectList = (EList<EObject>) eObjectTreeItemContentParentTreeItem;
        
        int index = eObjectList.indexOf(eObjectTreeItemContent);
        boolean before = beforeOrAfterComboBox.getValue().equals(BEFORE_TEXT);
        if (!before) {
          index++;
        }
        eObjectList.add(index, location);

        return;
      }
    }
    
  }
  
  /**
   * Set the value of the currently selected tree item, which shall be of type Location, to the specified location.
   * <p>
   * If the <code>useReverseGeocodeSearchCoordinates</code> checkbox is checked, the coordinates are not taken from this panel,
   * but from the <code>ReverseGeocodePanel</code> (if available).
   */
  private void setLocation() {
    // Always deselect use reverse geocoordinates, in order to prevent that next 'add' or 'set' uses this unintentionally.
    useReverseGeocodeSearchCoordinates.setSelected(false);
    
    Location location = getLocation();
    
    // If the selected tree item is a (a supertype of) Location, its value is updated.
    EStructuralFeature eStructuralFeature = selectedTreeItem.getEStructuralFeature();
    if (eStructuralFeature instanceof EReference) {
      EReference eReference = (EReference) eStructuralFeature;
      EClass listType = eReference.getEReferenceType();
      EClass locationEClass = VacationsPackage.eINSTANCE.getLocation();
      if (EmfUtil.isInstanceof(locationEClass, listType)  && !eReference.isMany()) {
        LOGGER.severe("set match");
        // Set this attribute on the Object of the parent item.
        EObjectTreeItem parentTreeItem = (EObjectTreeItem) selectedTreeItem.getParent();
        Object eObjectTreeItemContentParentTreeItem = parentTreeItem.getValue();
        EObject eObject = (EObject) eObjectTreeItemContentParentTreeItem;
        eObject.eSet(eStructuralFeature, location);

        selectedTreeItem.rebuildChildren();
        
        return;
      }
    }    
  }
  
  /**
   * Update the bounding box (boundary) of the currently selected tree item, which shall be of type {@code Location}, to the specified bounding box.
   */
  private void updateBoundary() {
    Object  eObjectTreeItemContent = selectedTreeItem.getValue();
    if (eObjectTreeItemContent instanceof Location location) {            
      String value = JfxUtil.getTextFieldText(boundingBoxTextField);
      if (value != null) {
        List<String> values = StringUtil.commaSeparatedValuesToListOfValues(value);
        if (values.size() == 4) {
          BoundingBox boundingBox = VACATIONS_FACTORY.createBoundingBox();
          boundingBox.setNorth(Double.valueOf(values.get(0)));
          boundingBox.setEast(Double.valueOf(values.get(1)));
          boundingBox.setSouth(Double.valueOf(values.get(2)));
          boundingBox.setWest(Double.valueOf(values.get(3)));
          location.setBoundingbox(boundingBox);
        }
      }
      
      List<Boundary> locationBoundaries = location.getBoundaries();
      locationBoundaries.clear();
      if (boundaries != null) {
        LOGGER.severe("boundaries set");
        locationBoundaries.addAll(boundaries);
      }
      
      selectedTreeItem.rebuildChildren();
    }
  }
  
  /**
   * Handle the fact that a new item is selected in the tree view.
   * <p>
   * 
   * 
   * @param selectedTreeItem the currently selected item in the tree view.
   */
  private void handleNewTreeItemSelected(TreeItem<Object> selectedTreeItem) {
    this.selectedTreeItem = (EObjectTreeItem) selectedTreeItem;
    
    updateActionHBox();
  }
  
  /**
   * Update the actionHBox
   * <p>
   * The contents of this box depends on the item selected in the tree view.
   */
  private void updateActionHBox() {
    actionVBox.getChildren().clear();
    
    selectedTreeItemDescription.setText(getTreeItemDescription(selectedTreeItem));
    
    if (selectedTreeItemIsLocationsList()) {
      HBox hBox = componentFactory.createHBox(12.0);
      hBox.getChildren().addAll(addLocationButton, addAsLastChildToListLabel, selectedTreeItemDescription, useReverseGeocodeSearchCoordinates);
      actionVBox.getChildren().add(hBox);
    } else if (selectedTreeItemIsLocationsListItem()) {
      HBox hBox = componentFactory.createHBox(12.0);
      hBox.getChildren().addAll(addLocationButton, beforeOrAfterComboBox, selectedTreeItemDescription, useReverseGeocodeSearchCoordinates);
      actionVBox.getChildren().add(hBox);
      
      hBox = componentFactory.createHBox(12.0);
      hBox.getChildren().addAll(updateBoundaryButton, new Label("of"), selectedTreeItemDescription);
      actionVBox.getChildren().add(hBox);
    } else if (selectedTreeItemIsReferenceToLocation()) {
      HBox hBox = componentFactory.createHBox(12.0);
      hBox.getChildren().addAll(setLocationButton, selectedTreeItemDescription, useReverseGeocodeSearchCoordinates);
      actionVBox.getChildren().add(hBox);
    } else {
      actionVBox.getChildren().add(infoLabel);
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
      
      if (eObjectTreeItemClassDescriptor.getBuildText() != null) {
        itemText = eObjectTreeItemClassDescriptor.getBuildText().apply(eObject);
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
    return vacationsWindow.getTreeView().treeItemIsLocationsList(selectedTreeItem);
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
    
    return vacationsWindow.getTreeView().treeItemIsLocationsList(parentTreeItem);
  }
  
  /**
   * Check whether the {@code selectedTreeItem} is a reference to a {@code Location}.
   * 
   * @return true if the {@code selectedTreeItem} is a reference to a {@code Location}, false otherwise.
   */
  private boolean selectedTreeItemIsReferenceToLocation() {
    if (selectedTreeItem == null) {
      return false;
    }
    
    EClass locationEClass = VacationsPackage.eINSTANCE.getLocation();

    EStructuralFeature eStructuralFeature = selectedTreeItem.getEStructuralFeature();
    if (eStructuralFeature instanceof EReference) {
      EReference eReference = (EReference) eStructuralFeature;
      EClass listTypeEClass = eReference.getEReferenceType();

      return EmfUtil.isInstanceof(locationEClass, listTypeEClass);
    } else {
      return false;
    }
  }
}
