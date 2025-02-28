package goedegep.rolodex.app.guifx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlInteger;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.rolodex.app.logic.AddressesComparator;
import goedegep.rolodex.app.logic.CityStringConverter;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
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
import javafx.scene.control.TableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class provides a window with information on Addresses.
 * <p>
 * The top part of the window shows an editable Addresses table.<br/>
 * The bottom part shows all details of an Address. The information here can be edited to update the Address information, or to add a new Address.
 */
public class AddressesWindowFx extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(AddressesWindowFx.class.getName());
  private static final String WINDOW_TITLE   = "Addresses";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private Rolodex rolodex;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<Address> addressesTable;
  private ReferredByPanel referredByPanel;
  private AddressEditPanel addressEditPanel;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public AddressesWindowFx(CustomizationFx customization, Rolodex rolodex) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    this.rolodex = rolodex;
        
    referredByPanel = new ReferredByPanel(customization, "Address for");
    addressEditPanel = new AddressEditPanel(customization, rolodex);
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>(){

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        addressesTable.setFilterExpression(newValue, null);
      }
      
    });
    
    addressesTable.addObjectSelectionListener((source, address) -> {
        referredByPanel.setObject(address);
    });
    
    show();
  }
  
  /**
   * Create the GUI.
   * <p>
   * The window consists of the following parts:
   * <ul>
   * <li>Controls which operate on the table.</li>
   * <li>The addresses table</li>
   * <li>A panel to add a new address</li>
   * </ul>
   */
  private void createGUI() {
    VBox rootLayout = new VBox();
    
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    rootLayout.getChildren().add(eObjectTableControlPanel);
    rootLayout.getChildren().add(createAddressesTable());
    rootLayout.getChildren().add(referredByPanel.getPanel());
    rootLayout.getChildren().add(addressEditPanel.getEditPanel());
        
    setScene(new Scene(rootLayout, 1400, 700));
  }
  
  /**
   * Create the addressesTable.
   * 
   * @return the created citiesTable
   */
  private EObjectTable<Address> createAddressesTable() {
    addressesTable = new EObjectTable<Address>(customization, ROLODEX_PACKAGE.getCity(), new AddressesTableDescriptor(rolodex), rolodex.getAddressList(), RolodexPackage.eINSTANCE.getAddressList_Addresses());
        
    return addressesTable;
  }
      
}

/**
 * This class provides a panel with controls to edit the properties of an Address.<br/>
 * There are buttons for updating the values of an Address, or to add a new Address.
 */
class AddressEditPanel {
  private static RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;
  
  private CustomizationFx customization;
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private GridPane gridPane;
  
  // Controls
  private ObjectControlString streetTextField;
  private ObjectControlInteger houseNumberTextField;
  private ObjectControlString houseNumberExtensionTextField;
  private ObjectControlString poBoxTextField;
  private ObjectControlString postalCodeTextField;
  private CityTextField cityTextField;
  private CountryTextField countryTextField;
  private int cityTextFieldRow;
  private int countryTextFieldRow;
  private SimpleObjectProperty<Address> addressProperty = new SimpleObjectProperty<>();
  private ObjectControlGroup objectControlGroup;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public AddressEditPanel(CustomizationFx customization, Rolodex rolodex) {
    this.customization = customization;
    this.rolodex = rolodex;
    
    componentFactory = customization.getComponentFactoryFx();
    
    // Create the controls.
    streetTextField = componentFactory.createObjectControlString(null, 300, true, "Enter the name of the street");
    houseNumberTextField = componentFactory.createObjectControlInteger((Integer) null, 300, true, "Enter the house number");
    houseNumberExtensionTextField = componentFactory.createObjectControlString(null, 300, true, "Enter the house number extension");
    poBoxTextField = componentFactory.createObjectControlString(null, 300, true, "Enter a PO box number");
    postalCodeTextField = componentFactory.createObjectControlString(null, 300, true, "Enter the postal code");
    cityTextField = new CityTextField(customization, rolodex);
    countryTextField = new CountryTextField(customization, rolodex);
    objectControlGroup = new ObjectControlGroup();
    objectControlGroup.addObjectControls(streetTextField, houseNumberTextField, houseNumberExtensionTextField, poBoxTextField, postalCodeTextField, cityTextField, countryTextField);
    
    cityTextField.addListener((o) -> {
        List<City> cities = cityTextField.getMatchingCities();
        if (!cities.isEmpty()) {
          Country country = cities.get(0).getCountry();
          countryTextField.setObject(country.getCountryName());
        }
      });
    
    createGUI();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    int row = 0;
    
    Label label = componentFactory.createStrongLabel("Add/Edit an address");
    gridPane.add(label, 0, row);
    
    row++;
    
    label = componentFactory.createLabel("Street:");
    gridPane.add(label, 0, row);

    gridPane.add(streetTextField.getControl(), 1, row);
    
    label = componentFactory.createLabel("House number:");
    gridPane.add(label, 2, row);

    gridPane.add(houseNumberTextField.getControl(), 3, row);
    
    label = componentFactory.createLabel("House number extension:");
    gridPane.add(label, 4, row);

    gridPane.add(houseNumberExtensionTextField.getControl(), 5, row);
    
    row++;
    
    label = componentFactory.createLabel("PO Box:");
    gridPane.add(label, 0, row);

    gridPane.add(poBoxTextField.getControl(), 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Postal code:");
    gridPane.add(label, 0, row);

    gridPane.add(postalCodeTextField.getControl(), 1, row);
        
    label = componentFactory.createLabel("City:");
    gridPane.add(label, 2, row);
    
    gridPane.add(cityTextField.getControl(), 3, row);
    cityTextFieldRow = row;
    
    row++;
    
    label = componentFactory.createLabel("Country:");
    gridPane.add(label, 0, row);

    gridPane.add(countryTextField.getControl(), 1, row);
    countryTextFieldRow = row;

    Button addButton = componentFactory.createButton("Add", "Add the address to the Rolodex");
    addButton.setOnAction((e) -> {
      Address newAddress = createAddressFromFields();
      if (newAddress != null) {
        rolodex.getAddressList().getAddresses().add(newAddress);
      }
     
    });
    gridPane.add(addButton, 2, row);
    
    Button updateButton = componentFactory.createButton("Update", "Update the address in the Rolodex");
    addressProperty.addListener(new ChangeListener<Address>() {

      @Override
      public void changed(ObservableValue<? extends Address> observable, Address oldValue, Address newValue) {
        updateButton.setDisable(newValue == null);
      }
    });
    updateButton.setDisable(addressProperty.getValue() == null);

    updateButton.setOnAction((e) -> {
      updateAddressIfControlsAreValid();
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
   * Set the address for which information will be shown and which can be updated.
   * 
   * @param address the address for which information will be shown and which can be updated.
   */
  public void setAddress(Address address) {
    addressProperty.setValue(address);
    fillFieldsFromAddress(address);
  }
  
  /**
   * Update the current address, if the controls all have valid values.
   */
  private void updateAddressIfControlsAreValid() {
    handleNewCountryName();
    
    if (countryTextField.getCountry() != null) {
      handleNewCityName();
    }
    
    // Only update if all controls have valid values.
    if (!objectControlGroup.isValid()) {
      return;
    }
    
    // Extra check: If both city and country are filled in, it must be an existing combination
    List<City> cities = cityTextField.getMatchingCities();
    Country country = countryTextField.getCountry();
    if (!cities.isEmpty()  &&  country != null  &&  cityTextField.getCity(country) == null) {
      return;
    }
    
    updateAddressFromFields(addressProperty.get());
  }
  
  /**
   * Fill the controls with the values of a specific address.
   * 
   * @param address the Address who's values will be applied to the controls.
   */
  private void fillFieldsFromAddress(Address address) {
    streetTextField.setObject(address.getStreetName());
    houseNumberTextField.setObject(address.getHouseNumber());
    houseNumberExtensionTextField.setObject(address.getHouseNumberExtension());
    poBoxTextField.setObject(address.getPOBox());
    postalCodeTextField.setObject(address.getPostalCode());
    City city = address.getCity();
    if (city != null) {
      cityTextField.setObject(city.getCityName());
      Country country = city.getCountry();
      if (country != null) {
        countryTextField.setObject(country.getCountryName());
      }
    }
  }
  
  /**
   * Create an Address from the values of the controls.
   * <p>
   * The City will only be created if the controls all have valid values.
   * 
   * @return the newly created Address, or null, if any control has an invalid value.
   */
  private Address createAddressFromFields() {
    handleNewCountryName();
    
    if (countryTextField.getCountry() != null) {
      handleNewCityName();
    }
    
    // Only update if all controls have valid values.
    if (!objectControlGroup.isValid()) {
      return null;
    }
    
    // Extra check: If both city and country are filled in, it must be an existing combination
    List<City> cities = cityTextField.getMatchingCities();
    Country country = countryTextField.getCountry();
    if (!cities.isEmpty()  &&  country != null  &&  cityTextField.getCity(country) == null) {
      return null;
    }
    
    Address newAddress = ROLODEX_FACTORY.createAddress();
    
    updateAddressFromFields(newAddress);
    
    return newAddress;
  }
  
  /**
   * Update an Address with the values of the controls.
   * <p>
   * Values are only set, if the new value differs from the current value. This to avoid unnecessary updates of listeners to this object.
   * 
   * @param address The Address object to be updated.
   */
  public boolean updateAddressFromFields(Address address) {
      String streetName = streetTextField.getValue();
      if (!Objects.equals(address.getStreetName(), streetName)) {
        address.setStreetName(streetName);
      }
      
      Integer houseNumber = houseNumberTextField.getValue();
      if (!Objects.equals(address.getHouseNumber(), houseNumber)) {
        address.setHouseNumber(houseNumber);
      }
      
      String houseNumberExtension = houseNumberExtensionTextField.getValue();
      if (!Objects.equals(address.getHouseNumberExtension(), houseNumberExtension)) {
        address.setHouseNumberExtension(houseNumberExtension);
      }
      
      String poBox = poBoxTextField.getValue();
      if (!Objects.equals(address.getPOBox(), poBox)) {
        address.setPOBox(poBox);
      }
      
      String postalCode = postalCodeTextField.getValue();
      if (!Objects.equals(address.getPostalCode(), postalCode)) {
        address.setPostalCode(postalCode);
      }
      
      City city = cityTextField.getCity(countryTextField.getCountry());
      if (!Objects.equals(address.getCity(), city)) {
        address.setCity(city);
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
        countryTextField.setObject(countryName);
        gridPane.add(countryTextField.getControl(), 1, countryTextFieldRow);
      });
    }
  }
  
  /**
   * Handle the fact that an unknown City name may be used for an Address.
   * <p>
   * Two cases are handled:
   * <ul>
   * <li>An unknown city name</li>
   * <li>A known city name, but for a different country</li>
   * </ul>
   * For each case a dialog is shown, where user is asked to add the city or not.
   */
  private void handleNewCityName() {
    // Handle non-existing city name
    if (cityTextField.isNonExistingCityName()) {
      String cityName = cityTextField.getValue();
      Alert alert = componentFactory.createYesNoConfirmationDialog(
          "Unknown city",
          "The city \'" + cityName + "\' doesn't exist in the Rolodex yet.",
          "Do you want to add it? (if you select no, also the address isn't added to the Rolodex)");
      alert.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
        City city = ROLODEX_FACTORY.createCity();
        city.setCityName(cityName);
        city.setCountry(countryTextField.getCountry());
        rolodex.getCityList().getCities().add(city);

        // The only way I found to update the autocompletion list is by recreating the textfield. 
        cityTextField = new CityTextField(customization, rolodex);
        cityTextField.setObject(cityName);
        gridPane.add(cityTextField.getControl(), 1, cityTextFieldRow);
      });
    }
    
    // Handle existing name, but in different country
    List<City> cities = cityTextField.getMatchingCities();
    Country country = countryTextField.getCountry();
    if (!cities.isEmpty()  &&  country != null) {
      City city = cityTextField.getCity(country);
      if (city == null) {
        String cityName = cityTextField.getValue();
        Alert alert = componentFactory.createYesNoConfirmationDialog(
            "Unknown city",
            "The city \'" + cityName + "\' in country \'" + country.getCountryName() + "\' doesn't exist in the Rolodex yet.",
            "Do you want to add it? (if you select no, also the address isn't added to the Rolodex)");
        alert.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
          City newCity = ROLODEX_FACTORY.createCity();
          newCity.setCityName(cityName);
          newCity.setCountry(country);
          rolodex.getCityList().getCities().add(newCity);

          // The only way I found to update the autocompletion list is by recreating the textfield. 
          cityTextField = new CityTextField(customization, rolodex);
          cityTextField.setObject(cityName);
          gridPane.add(cityTextField.getControl(), 1, cityTextFieldRow);
        });
      }
    }
  }
  
}




/**
 * This class provides the descriptor for the Cities Table.
 */
class AddressesTableDescriptor extends EObjectTableDescriptor<Address> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private static EObjectTableColumnDescriptorChoiceBox<Address> cityColumnDescriptor =
      new EObjectTableColumnDescriptorChoiceBox<Address>(ROLODEX_PACKAGE.getAddress_City(), "City", null, true, true, null, null);
  
  private static List<EObjectTableColumnDescriptorAbstract<Address>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Address>>of(
      new EObjectTableColumnDescriptorBasic<Address>(ROLODEX_PACKAGE.getAddress_StreetName(), "Streetname", true, true),
      new EObjectTableColumnDescriptorBasic<Address>(ROLODEX_PACKAGE.getAddress_HouseNumber(), "Housenumber", true, true),
      new EObjectTableColumnDescriptorBasic<Address>(ROLODEX_PACKAGE.getAddress_HouseNumberExtension(), "Housenumber ext.", true, true),
      new EObjectTableColumnDescriptorBasic<Address>(ROLODEX_PACKAGE.getAddress_POBox(), "PO Box", true, true),
      new EObjectTableColumnDescriptorBasic<Address>(ROLODEX_PACKAGE.getAddress_PostalCode(), "Postal code", true, true),
      cityColumnDescriptor,
      new EObjectTableColumnDescriptorCustom<Address>(ROLODEX_PACKAGE.getAddress_City(), "Country", null, false, true, column -> {
        TableCell<Address, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {            
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
            }
            else {
              setText(null);
              City city = (City) item;
              if (city != null) {
                Country country = city.getCountry();
                if (country != null) {
                  setText(country.getCountryName());
                }
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorBasic<Address>(ROLODEX_PACKAGE.getAddress_Id(), "Id", false, true)
      );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<Address>> rowOperations = new HashMap<>() {
    {
    put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public AddressesTableDescriptor(Rolodex rolodex) {
    super("The are no addressess in the Rolodex", AddressesComparator.getInstance(), columnDescriptors, rowOperations);
    
    cityColumnDescriptor.setStringConverter(new CityStringConverter(rolodex));
    cityColumnDescriptor.setItems(FXCollections.observableArrayList(rolodex.getCityList().getCities()));
  }

}
