package goedegep.rolodex.app.guifx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;

import goedegep.appgen.TableRowOperation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.controls.ObjectControl;
import goedegep.jfx.controls.ObjectControlBoolean;
import goedegep.jfx.controls.ObjectControlInteger;
import goedegep.jfx.controls.ObjectControlString;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCheckBox;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorChoiceBox;
import goedegep.jfx.eobjecttable.EObjectTableColumnGroupDescriptor;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.eobjecttable.TableFilterBooleanPredicate;
import goedegep.rolodex.app.logic.FamilyStringConverter;
import goedegep.rolodex.app.logic.PersonsComparator;
import goedegep.rolodex.app.logic.PhoneNumberListStringConverter;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.AddressForPeriod;
import goedegep.rolodex.model.Birthday;
import goedegep.rolodex.model.Gender;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.util.PgUtilities;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class provides a window with information on Persons.
 * <p>
 * The top part of the window shows an editable Persons table.<br/>
 * The bottom part shows all details of a Person. The information here can be edited to update the Person information, or to add a new Person.
 */
public class PersonsWindowFx extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(PersonsWindowFx.class.getName());
  private static final String WINDOW_TITLE   = "Persons";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;
  private Rolodex rolodex;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<Person> personsTable;
  private EObjectTable<AddressForPeriod> previousAddressesTable;
  private ReferredByPanel referredByPanel;
  private PersonEditPanel personEditPanel;
  private TableFilterBooleanPredicate<Person> showArchivedItemsPredicate = null;
  private CheckMenuItem  showArchivedItems;
  private CheckMenuItem  showPersonIdColumn;
  
  private EList<Person> persons;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public PersonsWindowFx(CustomizationFx customization, Rolodex rolodex) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    this.rolodex = rolodex;
    componentFactory = customization.getComponentFactoryFx();
    
    referredByPanel = new ReferredByPanel(customization, "Referred by");
    personEditPanel = new PersonEditPanel(customization, rolodex);
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>(){

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        personsTable.setFilterExpression(newValue, null);
      }
      
    });
    
    personsTable.addObjectSelectionListener((source, person) -> {
        if (person != null) {
          previousAddressesTable.setObjects(person, person.getPreviousAddresses());
        } else {
          previousAddressesTable.setObjects(null, null);
        }
        referredByPanel.setObject(person);
        personEditPanel.setPerson(person);
    });
    
    updateShowArchivedItems();
    updateShowPersonIdColumn();    
    show();
  }
  
  /**
   * Create the GUI.
   * <p>
   * The window consists of the following parts:
   * <ul>
   * <li>Controls which operate on the table.</li>
   * <li>The persons table</li>
   * <li>A panel to add a new person</li>
   * </ul>
   */
  private void createGUI() {
    VBox rootLayout = new VBox();
    
    rootLayout.getChildren().add(createMenuBar());
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    rootLayout.getChildren().add(eObjectTableControlPanel);
    rootLayout.getChildren().add(createPersonsTable());
    rootLayout.getChildren().add(createPreviousAddressesTable());
    rootLayout.getChildren().add(referredByPanel.getPanel());
    rootLayout.getChildren().add(personEditPanel.getEditPanel());
        
    setScene(new Scene(rootLayout, 1300, 700));
  }

  private MenuBar createMenuBar() {
    MenuBar menuBar = componentFactory.createMenuBar();
    Menu menu;

    // Show menu
    menu = componentFactory.createMenu("Show");
    
    // Show: Show archived items
    showArchivedItems = new CheckMenuItem("Show archived items");
    showArchivedItems.setOnAction(new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        updateShowArchivedItems();
      }
    });
    menu.getItems().add(showArchivedItems);
    
    // Show: Show person Id column
    showPersonIdColumn = new CheckMenuItem("Show person Id column");
    showPersonIdColumn.setOnAction(new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        updateShowPersonIdColumn();
      }
    });
    menu.getItems().add(showPersonIdColumn);
        
    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
  private void updateShowArchivedItems() {
    LOGGER.info("Show archived items: " + showArchivedItems.isSelected());
    if (showArchivedItems.isSelected()) {
      if (showArchivedItemsPredicate != null) {
        personsTable.removePredicate(showArchivedItemsPredicate);
      }
    } else {
      showArchivedItemsPredicate = new TableFilterBooleanPredicate<>(false, ROLODEX_PACKAGE.getArchive_Archived());
      personsTable.addPredicate(showArchivedItemsPredicate);
    }
  }
  
  private void updateShowPersonIdColumn() {
    LOGGER.info("Show person Id column: " + showPersonIdColumn.isSelected());
    TableColumn<Person, ?> idColumn = personsTable.getTableColumn(PersonsTableDescriptor.ID_COLUMN_ID);
    for (TableColumn<Person, ?> tableColumn: personsTable.getColumns()) {
      if (tableColumn.getText().equals(PersonsTableDescriptor.ID_COLUMN_ID)) {
        idColumn = tableColumn;
        break;
      }
    }
    if (idColumn == null) {
      return;
    }
    
    idColumn.setVisible(showPersonIdColumn.isSelected());
  }
  
  /**
   * Create the personsTable.
   * 
   * @return the created personsTable
   */
  private EObjectTable<Person> createPersonsTable() {
    persons = rolodex.getPersonList().getPersons();
    personsTable = new EObjectTable<Person>(customization, ROLODEX_PACKAGE.getPerson(), new PersonsTableDescriptor(rolodex), rolodex.getPersonList(), persons);
                
    return personsTable;
  }
  
  /**
   * Create the previousAddressesTable.
   * 
   * @return the created previousAddressesTable
   */
  private EObjectTable<AddressForPeriod> createPreviousAddressesTable() {
    previousAddressesTable = new EObjectTable<AddressForPeriod>(customization, ROLODEX_PACKAGE.getAddressForPeriod(), new AddressForPeriodTableDescriptor(), null, null);
                
    return previousAddressesTable;
  }
    
  public void selectPerson(Person person) {
    personsTable.getSelectionModel().select(person);
  }
}

/**
 * This class provides a panel with controls to edit the properties of a Person.<br/>
 * There are buttons for updating the values of a Person, or to add a new Person.
 */
class PersonEditPanel {
  private static RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;
  
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private GridPane gridPane;
  
  // Controls
  private ObjectControlString firstNameTextField;
  private ObjectControlString infixTextField;
  private ObjectControlString surNameTextField;
  private ObjectControlString initialsTextField;
  private ObjectControlInteger birthdayDayTextField;
  private ObjectControlInteger birthdayMonthTextField;
  private ObjectControlInteger birthdayYearTextField;
  private ComboBox<Gender> genderComboBox;
  private AddressTextField addressTextField;
  private ObjectControlBoolean moveToAddress;
  private PhoneNumberTextField phoneNumberTextFields[];
  private SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>();
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public PersonEditPanel(CustomizationFx customization, Rolodex rolodex) {
    this.rolodex = rolodex;
    
    componentFactory = customization.getComponentFactoryFx();
    
    // Create the controls.
    firstNameTextField = componentFactory.createObjectInputString(null, 300, true, "Enter the persons firstname");
    infixTextField = componentFactory.createObjectInputString(null, 100, true, "Enter the infix of the persons name");
    surNameTextField = componentFactory.createObjectInputString(null, 300, true, "Enter the persons surname (lastname)");
    initialsTextField = componentFactory.createObjectInputString(null, 300, true, "Enter the persons initials");
    birthdayDayTextField = componentFactory.createObjectInputInteger((Integer) null, 25, true, "Enter the day in the month of the persons birthday");
    birthdayMonthTextField = componentFactory.createObjectInputInteger((Integer) null, 25, true, "Enter the month of the persons birthday");
    birthdayYearTextField = componentFactory.createObjectInputInteger((Integer) null, 40, true, "Enter the year of the persons birthday");
    genderComboBox = new ComboBox<Gender>();
    genderComboBox.setItems(FXCollections.observableList(Arrays.asList(Gender.values())));
    addressTextField = new AddressTextField(customization, rolodex);
    moveToAddress = new ObjectControlBoolean("Move to", false, true, "Select for moving to this address. In this case the existing address is moved to the 'previous addresses'.");
    phoneNumberTextFields = new PhoneNumberTextField[4];
    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      phoneNumberTextFields[i] = new PhoneNumberTextField(customization, rolodex);
    }
    
    createGUI();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    int row = 0;
    
    Label label = componentFactory.createStrongLabel("Add/Edit a person");
    gridPane.add(label, 0, row, 2, 1);

    HBox buttonsBox = componentFactory.createHBox(12.0);
    Button addButton = componentFactory.createButton("Add person", "Add the person to the Rolodex");
    addButton.setOnAction((e) -> {
      Person newPerson = createPersonFromFields();
      if (newPerson != null) {
        rolodex.getPersonList().getPersons().add(newPerson);
      }
     
    });
    buttonsBox.getChildren().add(addButton);
    
    Button updateButton = componentFactory.createButton("Update person", "Update the person in the Rolodex");
    personProperty.addListener(new ChangeListener<Person>() {

      @Override
      public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
        updateButton.setDisable(newValue == null);
      }
    });
    updateButton.setDisable(personProperty.getValue() == null);

    updateButton.setOnAction((e) -> {
      updatePersonIfControlsAreValid();
    });
    buttonsBox.getChildren().add(updateButton);
    gridPane.add(buttonsBox, 5, row);
    
    row++;
    
    label = componentFactory.createLabel("Firstname:");
    gridPane.add(label, 0, row);

    gridPane.add(firstNameTextField, 1, row);
    
    label = componentFactory.createLabel("Infix:");
    gridPane.add(label, 2, row);

    gridPane.add(infixTextField, 3, row);
    
    label = componentFactory.createLabel("Surname:");
    gridPane.add(label, 4, row);

    gridPane.add(surNameTextField, 5, row);
    
    row++;
    
    label = componentFactory.createLabel("Initials:");
    gridPane.add(label, 0, row);

    gridPane.add(initialsTextField, 1, row);
    
    label = componentFactory.createLabel("Gender:");
    gridPane.add(label, 2, row);

    gridPane.add(genderComboBox, 3, row);
    
    row++;
    
    label = componentFactory.createLabel("Birthday:");
    gridPane.add(label, 0, row);
        
    HBox birthdayBox = componentFactory.createHBox(12.0);
    label = componentFactory.createLabel("Day:");
    birthdayBox.getChildren().add(label);
    
    birthdayBox.getChildren().add(birthdayDayTextField);
    
    label = componentFactory.createLabel("Month:");
    birthdayBox.getChildren().add(label);
    
    birthdayBox.getChildren().add(birthdayMonthTextField);
    
    label = componentFactory.createLabel("Year:");
    birthdayBox.getChildren().add(label);
    
    birthdayBox.getChildren().add(birthdayYearTextField);
    gridPane.add(birthdayBox, 1, row, 3, 1);
    
    row++;
    
    label = componentFactory.createLabel("Address:");
    gridPane.add(label, 0, row);

    gridPane.add(addressTextField, 1, row);
    
    gridPane.add(moveToAddress, 2, row);
    
    row++;
    
    label = componentFactory.createLabel("Phone numbers");
    gridPane.add(label,  0, row);
    
    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      gridPane.add(phoneNumberTextFields[i], 1 + i, row);
    }
    
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
   * @param person the Person for which information will be shown and which can be updated.
   */
  public void setPerson(Person person) {
    personProperty.setValue(person);
    fillFieldsFromPerson(person);
  }
  
  /**
   * Update the current person, if the controls all have valid values.
   */
  private void updatePersonIfControlsAreValid() {
    
    // Only update if all controls have valid values.
    if (!ObjectControl.areControlsValid(firstNameTextField, infixTextField, surNameTextField, initialsTextField, birthdayDayTextField, birthdayMonthTextField, birthdayYearTextField, addressTextField,
        phoneNumberTextFields[0], phoneNumberTextFields[1], phoneNumberTextFields[2], phoneNumberTextFields[3])) {
      return;
    }
        
    updatePersonFromFields(personProperty.get());
  }
  
  /**
   * Fill the controls with the values of a specific person.
   * 
   * @param person the Person who's values will be applied to the controls.
   */
  private void fillFieldsFromPerson(Person person) {
    clearFields();
    
    if (person == null) {
      return;
    }
    
    firstNameTextField.setText(person.getFirstname());
    infixTextField.setText(person.getInfix());
    surNameTextField.setText(person.getSurname());
    initialsTextField.setText(person.getInitials());
    genderComboBox.setValue(person.getGender());
    birthdayDayTextField.setText(null);
    birthdayMonthTextField.setText(null);
    birthdayYearTextField.setText(null);
    Birthday birthday = person.getBirthday();
    if (birthday != null) {
      birthdayDayTextField.setObjectValue(birthday.getDay());
      birthdayMonthTextField.setObjectValue(birthday.getMonth());
      birthdayYearTextField.setObjectValue(birthday.getYear());
    }
    
    addressTextField.setText(null);
    Address address = person.getAddress();
    if (address != null) {
      addressTextField.setText(address.toString());
    }
    
    List<PhoneNumber> phoneNumbers = person.getPhoneNumbers();
    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      if (phoneNumbers.size() > i) {
        phoneNumberTextFields[i].setText(phoneNumbers.get(i).toString());
      } else {
        phoneNumberTextFields[i].setText(null);
      }
    }
  }
  
  private void clearFields() {
    firstNameTextField.setText(null);
    infixTextField.setText(null);
    surNameTextField.setText(null);
    initialsTextField.setText(null);
    genderComboBox.setValue(null);
    birthdayDayTextField.setText(null);
    birthdayMonthTextField.setText(null);
    birthdayYearTextField.setText(null);
    birthdayDayTextField.setObjectValue(null);
    birthdayMonthTextField.setObjectValue(null);
    birthdayYearTextField.setObjectValue(null);

    addressTextField.setText(null);
    moveToAddress.setSelected(false);
    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      phoneNumberTextFields[i].setText(null);
    }
  }
  
  /**
   * Create a Person from the values of the controls.
   * <p>
   * The Person will only be created if the controls all have valid values.
   * 
   * @return the newly created Person, or null, if any control has an invalid value.
   */
  private Person createPersonFromFields() {
    
    // Only create if all controls have valid values.
    if (!ObjectControl.areControlsValid(firstNameTextField, infixTextField, surNameTextField, initialsTextField, birthdayDayTextField, birthdayMonthTextField, birthdayYearTextField, addressTextField,
        phoneNumberTextFields[0], phoneNumberTextFields[1], phoneNumberTextFields[2], phoneNumberTextFields[3])) {
      return null;
    }
    
    Person newPerson = ROLODEX_FACTORY.createPerson();
    
    updatePersonFromFields(newPerson);
    
    return newPerson;
  }
  
  /**
   * Update a Person with the values of the controls.
   * <p>
   * Values are only set, if the new value differs from the current value. This to avoid unnecessary updates of listeners to this object.
   * 
   * @param person The Person object to be updated.
   */
  public boolean updatePersonFromFields(Person person) {
      String firstName = firstNameTextField.getObjectValue();
      if (!PgUtilities.equals(person.getFirstname(), firstName)) {
        person.setFirstname(firstName);
      }
      
      String infix = infixTextField.getObjectValue();
      if (!PgUtilities.equals(person.getInfix(), infix)) {
        person.setInfix(infix);
      }
      
      String surName = surNameTextField.getObjectValue();
      if (!PgUtilities.equals(person.getSurname(), surName)) {
        person.setSurname(surName);
      }
      
      String initials = initialsTextField.getObjectValue();
      if (!PgUtilities.equals(person.getInitials(), initials)) {
        person.setInitials(initials);
      }
      
      Gender gender = genderComboBox.getValue();
      if (!PgUtilities.equals(person.getGender(), gender)) {
        person.setGender(gender);
      }
      
      // For now, no check on changes in birtday
      if (birthdayDayTextField.getIsFilledIn()  || birthdayMonthTextField.getIsFilledIn() ||  birthdayYearTextField.getIsFilledIn()) {
        Birthday birthday = person.getBirthday();
        
        if (birthday == null) {
          birthday = ROLODEX_FACTORY.createBirthday();
        }
        
        if (birthdayDayTextField.getIsFilledIn()) {
          birthday.setDay(birthdayDayTextField.getObjectValue());
        }
        
        if (birthdayMonthTextField.getIsFilledIn()) {
          birthday.setMonth(birthdayMonthTextField.getObjectValue());
        }
        
        if (birthdayYearTextField.getIsFilledIn()) {
          birthday.setYear(birthdayYearTextField.getObjectValue());
        }
        
        if (!person.isSetBirthday()) {
          person.setBirthday(birthday);
        }
      } else {
        person.setBirthday(null);
      }
            
      Address address = addressTextField.getMatchingAddress();
      if (moveToAddress.isSelected()) {
        Address currentAddress = person.getAddress();
        if (currentAddress != null) {
          AddressForPeriod addressForPeriod = ROLODEX_FACTORY.createAddressForPeriod();
          addressForPeriod.setAddress(currentAddress);
          person.getPreviousAddresses().add(addressForPeriod);
        }
      }
      if (!PgUtilities.equals(person.getAddress(), address)) {
        person.setAddress(address);
      }
      
      for (int i = 0; i < phoneNumberTextFields.length; i++) {
        PhoneNumber phoneNumber = phoneNumberTextFields[i].getMatchingPhoneNumber();
        if (phoneNumber != null) {
          person.getPhoneNumbers().add(phoneNumber);
        }
      }
        
    return true;
  }
    
}

/**
 * This class provides the descriptor for the Persons Table.
 */
class PersonsTableDescriptor extends EObjectTableDescriptor<Person> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  public static final String ID_COLUMN_ID = "id";
  
  private static EObjectTableColumnDescriptorChoiceBox<Person> familyColumnDescriptor = new EObjectTableColumnDescriptorChoiceBox<Person>(ROLODEX_PACKAGE.getPerson_Family(), "Family name", null, true, true, null, null);
  private static EObjectTableColumnDescriptorBasic<Person> birthDayDayColumnDescriptor = new EObjectTableColumnDescriptorBasic<Person>(
      Arrays.asList(ROLODEX_PACKAGE.getPerson_Birthday(), ROLODEX_PACKAGE.getBirthday_Day()),
      "Day", null, true, true);
  private static EObjectTableColumnDescriptorBasic<Person> birthDayMonthColumnDescriptor = new EObjectTableColumnDescriptorBasic<Person>(
      Arrays.asList(ROLODEX_PACKAGE.getPerson_Birthday(), ROLODEX_PACKAGE.getBirthday_Month()),
      "Month", null, true, true);
  private static EObjectTableColumnDescriptorBasic<Person> birthDayYearColumnDescriptor = new EObjectTableColumnDescriptorBasic<Person>(
      Arrays.asList(ROLODEX_PACKAGE.getPerson_Birthday(), ROLODEX_PACKAGE.getBirthday_Year()),
      "Year", null, true, true);
    
  private static List<EObjectTableColumnDescriptorAbstract<Person>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Person>>of(
      new EObjectTableColumnDescriptorBasic<Person>(ROLODEX_PACKAGE.getPerson_Firstname(), "First name", true, true),
      new EObjectTableColumnDescriptorBasic<Person>(ROLODEX_PACKAGE.getPerson_Infix(), "Infix", true, true),
      new EObjectTableColumnDescriptorBasic<Person>(ROLODEX_PACKAGE.getPerson_Surname(), "Surname", true, true),
      new EObjectTableColumnDescriptorBasic<Person>(ROLODEX_PACKAGE.getPerson_Initials(), "Initials", true, true),
      familyColumnDescriptor,
      new EObjectTableColumnGroupDescriptor<Person>("Birthday", birthDayDayColumnDescriptor, birthDayMonthColumnDescriptor, birthDayYearColumnDescriptor),
      new EObjectTableColumnDescriptorChoiceBox<Person>(ROLODEX_PACKAGE.getPerson_Gender(), "Gender", 150, true, true, FXCollections.observableList(Arrays.asList((Object[]) Gender.values())), null),
      new EObjectTableColumnDescriptorBasic<Person>(ROLODEX_PACKAGE.getAddressHolder_Address(), "Address", true, true),
      new EObjectTableColumnDescriptorBasic<Person>(ROLODEX_PACKAGE.getPhoneNumberHolder_PhoneNumbers(), "Phone numbers", 150, true, true, new PhoneNumberListStringConverter()),
      new EObjectTableColumnDescriptorBasic<Person>(ROLODEX_PACKAGE.getDescription_Description(), "Remarks", true, true),
      new EObjectTableColumnDescriptorCheckBox<Person>(ROLODEX_PACKAGE.getArchive_Archived(), "Archived", true, true),
      new EObjectTableColumnDescriptorBasic<Person>(ROLODEX_PACKAGE.getPerson_Id(), ID_COLUMN_ID, "Id", true, true)
      );
  
  @SuppressWarnings("serial")
  private static Map<TableRowOperation, TableRowOperationDescriptor<Person>> rowOperations = new HashMap<>() {
    {
    put(TableRowOperation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public PersonsTableDescriptor(Rolodex rolodex) {
    super("The are no addressess in the Rolodex", PersonsComparator.getInstance(), columnDescriptors, rowOperations);
    
    familyColumnDescriptor.setStringConverter(new FamilyStringConverter(rolodex));
    familyColumnDescriptor.setItems(FXCollections.observableArrayList(rolodex.getFamilyList().getFamilies()));
    
  }

}


/**
 * This class provides the descriptor for the AddressForPeriods Table.
 */
class AddressForPeriodTableDescriptor extends EObjectTableDescriptor<AddressForPeriod> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  public static final String ID_COLUMN_ID = "id";
  
  private static List<EObjectTableColumnDescriptorAbstract<AddressForPeriod>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<AddressForPeriod>>of(
      new EObjectTableColumnDescriptorBasic<AddressForPeriod>(ROLODEX_PACKAGE.getAddressForPeriod_Address(), "Address", 300, true, true),
      new EObjectTableColumnDescriptorBasic<AddressForPeriod>(ROLODEX_PACKAGE.getAddressForPeriod_FromDate(), "From", true, true),
      new EObjectTableColumnDescriptorBasic<AddressForPeriod>(ROLODEX_PACKAGE.getAddressForPeriod_UntillDate(), "Until", true, true)
      );
  
  @SuppressWarnings("serial")
  private static Map<TableRowOperation, TableRowOperationDescriptor<AddressForPeriod>> rowOperations = new HashMap<>() {
    {
    put(TableRowOperation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public AddressForPeriodTableDescriptor() {
    super("The are no previous addresses", null, columnDescriptors, rowOperations);
        
  }

}