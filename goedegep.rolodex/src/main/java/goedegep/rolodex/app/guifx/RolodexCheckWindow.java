package goedegep.rolodex.app.guifx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.rolodex.app.logic.RolodexChecker;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.Rolodex;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class RolodexCheckWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(RolodexCheckWindow.class.getName());
  private static final String WINDOW_TITLE   = "Rolodex checker";
  
  private ComponentFactoryFx componentFactory;
  private RolodexChecker rolodexChecker;
  private List<CheckPanel> checks;

  public RolodexCheckWindow(CustomizationFx customization, Rolodex rolodex) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    rolodexChecker = new RolodexChecker(rolodex);
    
    checks = new ArrayList<>();
    checks.add(new CountriesNotReferredToCheckPanel(customization, rolodex, rolodexChecker));
    checks.add(new CitiesNotReferredToCheckPanel(customization, rolodex, rolodexChecker));
    checks.add(new CitiesWithoutCountryCheckPanel(customization, rolodex, rolodexChecker));
    checks.add(new AddressesNotReferredToCheckPanel(customization, rolodex, rolodexChecker));
    checks.add(new PhoneNumbersNotReferredToCheckPanel(customization, rolodex, rolodexChecker));
    checks.add(new EmptyBirthdaysCheckPanel(customization, rolodex, rolodexChecker));
   
    createGUI();
    
    show();
  }

  private void createGUI() {
    VBox vBox =componentFactory.createVBox(6.0, 12.0);
    
    vBox.getChildren().add(createGeneralCheckPanel());
    vBox.getChildren().addAll(checks);
    
    setScene(new Scene(vBox, 900, 1100));
  }

  private Node createGeneralCheckPanel() {
    HBox hBox = componentFactory.createHBox(12.0, 12.0);
    hBox.setStyle("-fx-border-color: black");

    CheckBox selectAllOrNoneCheckBox = componentFactory.createCheckBox("(de)select all checks", false);
    selectAllOrNoneCheckBox.setOnAction(this::selectChecks);
    hBox.getChildren().add(selectAllOrNoneCheckBox);
    
    Region region = new Region();
    HBox.setHgrow(region, Priority.ALWAYS);
    Label label = componentFactory.createStrongLabel("Run selected checks");
    hBox.getChildren().addAll(region, label);
        
    Button runChecksButton = componentFactory.createButton("Run", "Run the selected checks");
    runChecksButton.setOnAction(this::runSelectedChecks);
    hBox.getChildren().add(runChecksButton);
    
    return hBox;
  }
  
  private void runSelectedChecks(ActionEvent e) {  // NOPMD
    for (CheckPanel checkPanel: checks) {
      checkPanel.runCheckIfSelected();
    }
  }
  
  private void selectChecks(ActionEvent e) {
    CheckBox checkBox = (CheckBox) e.getSource();
    for (CheckPanel checkPanel: checks) {
      checkPanel.selectCheck(checkBox.isSelected());
    }
  }
}

/**
 * This class provides the common part for all check panels.
 */
abstract class CheckPanel extends VBox {
  private CustomizationFx customization;
  private Rolodex rolodex;
  private RolodexChecker rolodexChecker;
  private ComponentFactoryFx componentFactory;
  private VBox messagesBox;

  private CheckBox selectCheckCheckBox;
  
  public CheckPanel(CustomizationFx customization, Rolodex rolodex, RolodexChecker rolodexChecker) {
    this.customization = customization;
    this.rolodex = rolodex;
    this.rolodexChecker = rolodexChecker;
    componentFactory = customization.getComponentFactoryFx();

    selectCheckCheckBox = componentFactory.createCheckBox(null, false);
    
    setPadding(new Insets(12.0));
    setStyle("-fx-border-color: black");
  }
  
  protected void selectCheck(boolean select) {
    selectCheckCheckBox.setSelected(select);
  }

  protected void runCheckIfSelected() {
    if (selectCheckCheckBox.isSelected()) {
      runCheck(null);
    }
  }
  
  protected abstract void runCheck(ActionEvent e);

  public Rolodex getRolodex() {
    return rolodex;
  }

  public CustomizationFx getCustomization() {
    return customization;
  }

  public RolodexChecker getRolodexChecker() {
    return rolodexChecker;
  }

  public ComponentFactoryFx getComponentFactory() {
    return componentFactory;
  }

  public CheckBox getSelectCheckCheckBox() {
    return selectCheckCheckBox;
  }
  
  public VBox getMessagesBox() {
    return messagesBox;
  }

  protected void createGUI(String labelText, String runButtonToolTipText) {
    HBox topRow = componentFactory.createHBox(12.0);
    topRow.getChildren().add(selectCheckCheckBox);
    
    Label label = getComponentFactory().createStrongLabel(labelText);
    topRow.getChildren().add(label);
    
    
    Region region = new Region();
    HBox.setHgrow(region, Priority.ALWAYS);
    Button button = getComponentFactory().createButton("Run", runButtonToolTipText);
    button.setOnAction(this::runCheck);
    topRow.getChildren().addAll(region, button);
    getChildren().add(topRow);

    messagesBox = getComponentFactory().createVBox(12.0);
    getChildren().add(messagesBox);
    
  }
}

/**
 * Check panel to check for Countries which aren't referred to.
 */
class CountriesNotReferredToCheckPanel extends CheckPanel {
  
  public CountriesNotReferredToCheckPanel(CustomizationFx customization, Rolodex rolodex, RolodexChecker rolodexChecker) {
    super(customization, rolodex, rolodexChecker);
    
    createGUI();
  }
  
  private void createGUI() {
    super.createGUI("Check on countries which aren't referred to", "Run the countries check");    
  }
  
  public void runCheck(ActionEvent e) {
    List<Country> countriesNotReferredTo = getRolodexChecker().checkCountriesNotReferredTo();
    updateMessagesBox(countriesNotReferredTo);
  }
  
  private void updateMessagesBox(List<Country> countriesNotReferredTo) {
    getMessagesBox().getChildren().clear();
    
    for (Country country: countriesNotReferredTo) {
      Node node = createMessagePanel(country);
      getMessagesBox().getChildren().add(node);
    }
  }

  private Node createMessagePanel(final Country country) {
    HBox hBox = getComponentFactory().createHBox(12.0);
    
    Label label = getComponentFactory().createLabel("There are no references to country: " + country.getCountryName());
    label.setMinWidth(300.0);
    hBox.getChildren().add(label);
    
    Button button = getComponentFactory().createButton("Delete", "Delete this country from the rolodex");
    button.setOnAction(e -> {
      getRolodex().getCountryList().getCountries().remove(country);
    });
    hBox.getChildren().add(button);
    
    return hBox;
  }
}

/**
 * Check panel to check for Cities which aren't referred to.
 */
class CitiesNotReferredToCheckPanel extends CheckPanel  {
  
  public CitiesNotReferredToCheckPanel(CustomizationFx customization, Rolodex rolodex, RolodexChecker rolodexChecker) {
    super(customization, rolodex, rolodexChecker);
    
    createGUI();
  }
  
  private void createGUI() {
    super.createGUI("Check on cities which aren't referred to", "Run the cities check");    
  }
  
  public void runCheck(ActionEvent e) {
    List<City> citiesNotReferredTo = getRolodexChecker().checkCitiesNotReferredTo();
    updateMessagesBox(citiesNotReferredTo);
  }
  
  private void updateMessagesBox(List<City> citiesNotReferredTo) {
    getMessagesBox().getChildren().clear();
    
    for (City city: citiesNotReferredTo) {
      Node node = createMessagePanel(city);
      getMessagesBox().getChildren().add(node);
    }
  }

  private Node createMessagePanel(final City city) {
    HBox hBox = getComponentFactory().createHBox(12.0);
    
    Country country = city.getCountry();
    String countryName = null;
    if (country != null) {
      countryName = " (" + country.getCountryName() + ")";
    }
    Label label = getComponentFactory().createLabel("There are no references to city: " + city.getCityName() + countryName);
    label.setMinWidth(300.0);
    hBox.getChildren().add(label);
    
    Button button = getComponentFactory().createButton("Delete", "Delete this city from the rolodex");
    button.setOnAction(e -> {
      getRolodex().getCityList().getCities().remove(city);
    });
    hBox.getChildren().add(button);
    
    return hBox;
  }
}

/**
 * Check panel to check for Cities which don't have a country reference.
 */
class CitiesWithoutCountryCheckPanel extends CheckPanel  {
  
  public CitiesWithoutCountryCheckPanel(CustomizationFx customization, Rolodex rolodex, RolodexChecker rolodexChecker) {
    super(customization, rolodex, rolodexChecker);
    
    createGUI();
  }
  
  private void createGUI() {
    super.createGUI("Check on cities which don't have a country reference", "Run the cities without country check");    
  }
  
  public void runCheck(ActionEvent e) {
    List<City> citiesWithOutCountry = getRolodexChecker().checkCitiesWithoutCountry();
    updateMessagesBox(citiesWithOutCountry);
  }
  
  private void updateMessagesBox(List<City> citiesWithOutCountry) {
    getMessagesBox().getChildren().clear();
    
    for (City city: citiesWithOutCountry) {
      Node node = createMessagePanel(city);
      getMessagesBox().getChildren().add(node);
    }
  }

  private Node createMessagePanel(final City city) {
    HBox hBox = getComponentFactory().createHBox(12.0);
    
    Label label = getComponentFactory().createLabel("There is no country set for the city: " + city.getCityName());
    label.setMinWidth(300.0);
    hBox.getChildren().add(label);
    
    Button button = getComponentFactory().createButton("Edit", "Edit this city in the Cities window");
    button.setOnAction(e -> {
      CitiesWindowFx citiesWindow = new CitiesWindowFx(getCustomization(), getRolodex());
      citiesWindow.selectCity(city);
    });
    hBox.getChildren().add(button);
    
    return hBox;
  }
}

/**
 * Check panel to check for Addresses which aren't referred to.
 */
class AddressesNotReferredToCheckPanel extends CheckPanel  {
  
  public AddressesNotReferredToCheckPanel(CustomizationFx customization, Rolodex rolodex, RolodexChecker rolodexChecker) {
    super(customization, rolodex, rolodexChecker);
    
    createGUI();
  }
  
  private void createGUI() {
    super.createGUI("Check on addresses which aren't referred to", "Run the addresses not referred to check");    
  }
  
  public void runCheck(ActionEvent e) {
    List<Address> addressesNotReferredTo = getRolodexChecker().checkAddressesNotReferredTo();
    updateMessagesBox(addressesNotReferredTo);
  }
  
  private void updateMessagesBox(List<Address> addressesNotReferredTo) {
    getMessagesBox().getChildren().clear();
    
    for (Address address: addressesNotReferredTo) {
      Node node = createMessagePanel(address);
      getMessagesBox().getChildren().add(node);
    }
  }

  private Node createMessagePanel(final Address address) {
    HBox hBox = getComponentFactory().createHBox(12.0);
    
    Label label = getComponentFactory().createLabel("There are no references to address: " + address.toString());
    label.setMinWidth(300.0);
    hBox.getChildren().add(label);
    
    Button button = getComponentFactory().createButton("Delete", "Delete this address from the rolodex");
    button.setOnAction(e -> {
      getRolodex().getAddressList().getAddresses().remove(address);
    });
    hBox.getChildren().add(button);
    
    return hBox;
  }
}


/**
 * Check panel to check for PhoneNumbers which aren't referred to.
 */
class PhoneNumbersNotReferredToCheckPanel extends CheckPanel  {
  
  public PhoneNumbersNotReferredToCheckPanel(CustomizationFx customization, Rolodex rolodex, RolodexChecker rolodexChecker) {
    super(customization, rolodex, rolodexChecker);
    
    createGUI();
  }
  
  private void createGUI() {
    super.createGUI("Check on phone numbers which aren't referred to", "Run the phone numbers not referred to check");    
  }
  
  public void runCheck(ActionEvent e) {
    List<PhoneNumber> phoneNumbersNotReferredTo = getRolodexChecker().checkPhoneNumbersNotReferredTo();
    updateMessagesBox(phoneNumbersNotReferredTo);
  }
  
  private void updateMessagesBox(List<PhoneNumber> phoneNumbersNotReferredTo) {
    getMessagesBox().getChildren().clear();
    
    for (PhoneNumber phoneNumber: phoneNumbersNotReferredTo) {
      Node node = createMessagePanel(phoneNumber);
      getMessagesBox().getChildren().add(node);
    }
  }

  private Node createMessagePanel(final PhoneNumber phoneNumber) {
    HBox hBox = getComponentFactory().createHBox(12.0);
    
    Label label = getComponentFactory().createLabel("There are no references to phone number: " + phoneNumber.toString());
    label.setMinWidth(300.0);
    hBox.getChildren().add(label);
    
    Button button = getComponentFactory().createButton("Delete", "Delete this phone number from the rolodex");
    button.setOnAction(e -> {
      getRolodex().getPhoneNumberList().getPhoneNumbers().remove(phoneNumber);
    });
    hBox.getChildren().add(button);
    
    return hBox;
  }
}


/**
 * Check panel to check for Persons who have an empty birthday set.
 */
class EmptyBirthdaysCheckPanel extends CheckPanel  {
  
  public EmptyBirthdaysCheckPanel(CustomizationFx customization, Rolodex rolodex, RolodexChecker rolodexChecker) {
    super(customization, rolodex, rolodexChecker);
    
    createGUI();
  }
  
  private void createGUI() {
    super.createGUI("Check on persons who have an empty birthday set", "Run the empty birthdays check");    
  }
  
  public void runCheck(ActionEvent e) {
    List<Person> personsWithEmptyBirthday = getRolodexChecker().checkEmptyBirthdays();
    updateMessagesBox(personsWithEmptyBirthday);
  }
  
  private void updateMessagesBox(List<Person> personsWithEmptyBirthday) {
    getMessagesBox().getChildren().clear();
    
    for (Person person: personsWithEmptyBirthday) {
      Node node = createMessagePanel(person);
      getMessagesBox().getChildren().add(node);
    }
  }

  private Node createMessagePanel(final Person person) {
    HBox hBox = getComponentFactory().createHBox(12.0);
    
    Label label = getComponentFactory().createLabel("There is an empty birthday set for person: " + person.toString());
    label.setMinWidth(300.0);
    hBox.getChildren().add(label);
    
    Button button = getComponentFactory().createButton("Delete", "Delete the birthday of this person");
    button.setOnAction(e -> {
      person.setBirthday(null);
    });
    hBox.getChildren().add(button);
    
    button = getComponentFactory().createButton("Edit", "Edit this person in the Persons window");
    button.setOnAction(e -> {
      PersonsWindowFx personsWindow = new PersonsWindowFx(getCustomization(), getRolodex());
      personsWindow.selectPerson(person);
    });
    hBox.getChildren().add(button);
    
    return hBox;
  }
}



