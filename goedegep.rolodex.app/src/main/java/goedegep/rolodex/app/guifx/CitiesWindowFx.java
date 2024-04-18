package goedegep.rolodex.app.guifx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.appgen.Operation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorChoiceBox;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.rolodex.app.logic.CitiesComparator;
import goedegep.rolodex.app.logic.CountryStringConverter;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.util.PgUtilities;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class provides a window with information on Cities.
 * <p>
 * The top part of the window shows an editable Cities table.<br/>
 * The bottom part shows all details of a City. The information here can be edited to update the City information, or to add a new City.
 */
public class CitiesWindowFx extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(CitiesWindowFx.class.getName());
  private static final String WINDOW_TITLE   = "Cities";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private Rolodex rolodex;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<City> citiesTable;
  private ReferredByPanel referredByPanel;
  private CityEditPanel cityEditPanel;
  
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public CitiesWindowFx(CustomizationFx customization, Rolodex rolodex) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    this.rolodex = rolodex;
    
    referredByPanel = new ReferredByPanel(customization, "Addresses in this city");
    cityEditPanel = new CityEditPanel(customization, rolodex);
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>(){

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        citiesTable.setFilterExpression(newValue, null);
      }
      
    });
    
    citiesTable.addObjectSelectionListener((source, city) -> {
        referredByPanel.setObject(city);
        cityEditPanel.setCity(city);
    });
    
    show();
  }
  
  /**
   * Create the GUI.
   * <p>
   * The window consists of the following parts:
   * <ul>
   * <li>Controls which operate on the table.</li>
   * <li>The cities table</li>
   * <li>A panel to add a new city</li>
   * </ul>
   */
  private void createGUI() {
    VBox rootLayout = new VBox();
    
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    rootLayout.getChildren().add(eObjectTableControlPanel);
    rootLayout.getChildren().add(createCitiesTable());
    rootLayout.getChildren().add(referredByPanel.getPanel());
    rootLayout.getChildren().add(cityEditPanel.getEditPanel());
    
    setScene(new Scene(rootLayout));
  }
  
  /**
   * Create the citiesTable.
   * 
   * @return the created citiesTable
   */
  private EObjectTable<City> createCitiesTable() {
    citiesTable = new EObjectTable<City>(customization, ROLODEX_PACKAGE.getCity(), new CitiesTableDescriptor(rolodex), rolodex.getCityList(), RolodexPackage.eINSTANCE.getCityList_Cities());
            
    return citiesTable;
  }

  public void selectCity(City city) {
    citiesTable.getSelectionModel().select(city);
  }
  
}


/**
 * This class provides a panel with controls to edit the properties of a City.<br/>
 * There are buttons for updating the values of a City, or to add a new City.
 */
class CityEditPanel {
  private static RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;
  
  private CustomizationFx customization;
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private GridPane gridPane;
  
  // Controls
  private ObjectControlString cityTextField;
  private CountryTextField countryTextField;
  private int countryTextFieldRow;
  private SimpleObjectProperty<City> cityProperty = new SimpleObjectProperty<>();
  private ObjectControlGroup objectControlGroup;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public CityEditPanel(CustomizationFx customization, Rolodex rolodex) {
    this.customization = customization;
    this.rolodex = rolodex;
    
    componentFactory = customization.getComponentFactoryFx();
    
    // Create the controls.
    cityTextField = componentFactory.createObjectControlString(null, 300, false, "Enter the name of a city");
    countryTextField = new CountryTextField(customization, rolodex);
    objectControlGroup = new ObjectControlGroup();
    objectControlGroup.addObjectControls(cityTextField, countryTextField);
    
    createGUI();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {    
    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    int row = 0;
    
    Label label = componentFactory.createStrongLabel("Add/Edit a city");
    gridPane.add(label, 0, row);
    
    row++;
        
    label = componentFactory.createLabel("City:");
    gridPane.add(label, 0, row);
    
    gridPane.add(cityTextField.getControl(), 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Country:");
    gridPane.add(label, 0, row);

    gridPane.add(countryTextField.getControl(), 1, row);
    countryTextFieldRow = row;

    Button addButton = componentFactory.createButton("Add", "Add the city to the Rolodex");
    addButton.setOnAction((e) -> {
      City newCity = createCityFromFields();
      if (newCity != null) {
        rolodex.getCityList().getCities().add(newCity);
      }
     
    });
    gridPane.add(addButton, 2, row);
    
    Button updateButton = componentFactory.createButton("Update", "Update the city in the Rolodex");
    cityProperty.addListener(new ChangeListener<City>() {

      @Override
      public void changed(ObservableValue<? extends City> observable, City oldValue, City newValue) {
        updateButton.setDisable(newValue == null);
      }
    });
    updateButton.setDisable(cityProperty.getValue() == null);

    updateButton.setOnAction((e) -> {
      updateCityIfControlsAreValid();
    });
    gridPane.add(updateButton, 3, row);
    
  }
  
  /**
   * Get the edit panel.
   * 
   * @return the edit panel.
   */
  public Node getEditPanel() {
    return gridPane;
  }
  
  /**
   * Set the city for which information will be shown and which can be updated.
   * 
   * @param city the city for which information will be shown and which can be updated.
   */
  public void setCity(City city) {
    cityProperty.setValue(city);
    fillFieldsFromCity(city);
  }
  
  /**
   * Update the current city, if the controls all have valid values.
   */
  private void updateCityIfControlsAreValid() {
    handleNewCountryName();
    
    // Only update if all controls have valid values.
    if (!objectControlGroup.getIsValid()) {
      return;
    }
    
    updateCityFromFields(cityProperty.get());
  }
  
  /**
   * Fill the controls with the values of a specific city.
   * 
   * @param city the City who's values will be applied to the controls.
   */
  private void fillFieldsFromCity(City city) {
    cityTextField.setValue(city.getCityName());
    countryTextField.setValue(city.getCountry().getCountryName());
  }
  
  /**
   * Create a City from the values of the controls.
   * <p>
   * The City will only be created if the controls all have valid values.
   * 
   * @return the newly created City, or null, if any control has an invalid value.
   */
  private City createCityFromFields() {
    handleNewCountryName();
    
    // Only update if all controls have valid values.
    if (!objectControlGroup.getIsValid()) {
      return null;
    }
    
    City newCity = ROLODEX_FACTORY.createCity();
    
    updateCityFromFields(newCity);
    
    return newCity;
  }
  
  /**
   * Update a City with the values of the controls.
   * <p>
   * Values are only set, if the new value differs from the current value. This to avoid unnecessary updates of listeners to this object.
   * 
   * @param city The City object to be updated.
   */
  public boolean updateCityFromFields(City city) {
      String cityName = cityTextField.getValue();
      if (!PgUtilities.equals(city.getCityName(), cityName)) {
        city.setCityName(cityName);
      }
      
      Country country = countryTextField.getCountry();
      if (!PgUtilities.equals(city.getCountry(), country)) {
        city.setCountry(country);
      }
        
    return true;
  }
  
  /**
   * Handle the fact that an unknown Country name may be used for a new City.
   * <p>
   * In this case a dialog is shown where the user is asked whether the country is to be added to the Rolodex.
   * If the user answers yes, a Country with the specified name is added to the Rolodex.
   * If the user answers no, the country is not added, which implies that the city can also not be added.
   */
  private void handleNewCountryName() {
    if (countryTextField.isNonExistingCountyName()) {
      String countryName = countryTextField.getValue();
      Alert alert = componentFactory.createYesNoConfirmationDialog(
          "Unknown country",
          "The country \'" + countryName + "\' doesn't exist in the Rolodex yet.",
          "Do you want to add it? (if you select no, also the city isn't added to the Rolodex)");
      alert.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
        Country country = ROLODEX_FACTORY.createCountry();
        country.setCountryName(countryName);
        rolodex.getCountryList().getCountries().add(country);

        // The only way I found to update the autocompletion list is by recreating the textfield. 
        countryTextField = new CountryTextField(customization, rolodex);
        countryTextField.setValue(countryName);
        gridPane.add(countryTextField.getControl(), 1, countryTextFieldRow);
      });
    }
  }
  
}



/**
 * This class provides the descriptor for the Cities Table.
 */
class CitiesTableDescriptor extends EObjectTableDescriptor<City> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;

  private static EObjectTableColumnDescriptorBasic<City> cityColumnDescriptor = new EObjectTableColumnDescriptorBasic<>(ROLODEX_PACKAGE.getCity_CityName(), "City", true, true);
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<City>> rowOperations = new HashMap<>() {
    {
    put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };

  public CitiesTableDescriptor(Rolodex rolodex) {
    super("The are no cities in the Rolodex", CitiesComparator.getInstance(), null, rowOperations);

    List<EObjectTableColumnDescriptorAbstract<City>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<City>>of(
        cityColumnDescriptor,
        new EObjectTableColumnDescriptorChoiceBox<City>(ROLODEX_PACKAGE.getCity_Country(), "Country", 400, true, true, FXCollections.observableArrayList(rolodex.getCountryList().getCountries()), new CountryStringConverter(rolodex))
        );

    setColumnDescriptors(columnDescriptors);
  }
}
