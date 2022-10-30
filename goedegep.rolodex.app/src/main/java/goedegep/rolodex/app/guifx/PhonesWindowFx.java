package goedegep.rolodex.app.guifx;

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
import goedegep.jfx.controls.ObjectControlEnumComboBox;
import goedegep.jfx.controls.ObjectControlString;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.rolodex.model.Phone;
import goedegep.rolodex.model.PhoneAddressBook;
import goedegep.rolodex.model.PhoneAddressBookEntry;
import goedegep.rolodex.model.PhoneAddressBookEntryType;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.util.PgUtilities;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class provides a window with information on PhoneNumbers.
 * <p>
 * The top part of the window shows an editable PhoneNumbers table.<br/>
 * The bottom part shows all details of a PhoneNumbers. The information here can be edited to update the PhoneNumbers information, or to add a new PhoneNumbers.
 */
public class PhonesWindowFx extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PhonesWindowFx.class.getName());
  private static final String WINDOW_TITLE   = "Phone numbers";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private EObjectTable<Phone> phonesTable;
  private EObjectTable<PhoneAddressBookEntry> phoneAddressBookTable;
  private ReferredByPanel referredByPanel;
  private PhoneEditPanel phoneEditPanel;
  private PhoneAddressBookEntryEditPanel phoneAddressBookEntryEditPanel;
  
  private EList<Phone> phones;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public PhonesWindowFx(CustomizationFx customization, Rolodex rolodex) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    this.rolodex = rolodex;
    componentFactory = customization.getComponentFactoryFx();
    
    referredByPanel = new ReferredByPanel(customization, "Referred by");
    phoneEditPanel = new PhoneEditPanel(customization, rolodex);
    phoneAddressBookEntryEditPanel = new PhoneAddressBookEntryEditPanel(customization, rolodex);
    
    createGUI();
    
    phonesTable.addObjectSelectionListener((source, phone) -> {
        referredByPanel.setObject(phone);
        phoneEditPanel.setPhone(phone);
        if (phone != null) {
          phoneAddressBookTable.setObjects(phone.getPhoneAddressBook(), phone.getPhoneAddressBook().getEntries());
          phoneAddressBookEntryEditPanel.setPhoneAddressBook(phone.getPhoneAddressBook());
        } else {
          phoneAddressBookTable.setObjects(null, null);
          phoneAddressBookEntryEditPanel.setPhoneAddressBook(null);
        }
    });
    
    phoneAddressBookTable.addObjectSelectionListener((source, phoneAddressBookEntry) -> {
        phoneAddressBookEntryEditPanel.setPhoneAddressBookEntry(phoneAddressBookEntry);
    });
    
    show();
  }
  
  /**
   * Create the GUI.
   * <p>
   * The window consists of the following parts:
   * <ul>
   * <li>Controls which operate on the table.</li>
   * <li>The phone numbers table</li>
   * <li>A panel to add a new person</li>
   * </ul>
   */
  private void createGUI() {
    VBox rootLayout = componentFactory.createVBox();
    
    rootLayout.getChildren().add(createPhonesTable());    
    rootLayout.getChildren().add(referredByPanel.getPanel());
    rootLayout.getChildren().add(phoneEditPanel.getEditPanel());
    rootLayout.getChildren().add(createPhoneAddressBookTable());
    rootLayout.getChildren().add(phoneAddressBookEntryEditPanel.getEditPanel());
        
    setScene(new Scene(rootLayout, 1300, 700));
  }
  
  /**
   * Create the phonesTable.
   * 
   * @return the created phonesTable
   */
  private EObjectTable<Phone> createPhonesTable() {
    phones = rolodex.getPhoneList().getPhones();
    phonesTable = new EObjectTable<Phone>(customization, ROLODEX_PACKAGE.getPhone(), new PhonesTableDescriptor(), rolodex.getPhoneList(), phones);
        
    return phonesTable;
  }

  
  /**
   * Create the phoneAddressBookTable.
   * 
   * @return the created phoneAddressBookTable
   */
  private EObjectTable<PhoneAddressBookEntry> createPhoneAddressBookTable() {
    phoneAddressBookTable = new EObjectTable<PhoneAddressBookEntry>(customization, ROLODEX_PACKAGE.getPhone(), new PhoneAddressBookTableDescriptor(), null, null);
        
    return phoneAddressBookTable;
  }
}

/**
 * This class provides a panel with controls to edit the properties of a Phone.<br/>
 * There are buttons for updating the values of a Phone, or to add a new Phone.
 */
class PhoneEditPanel {
  private static RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;
  
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private GridPane gridPane;
  
  // Controls
  private ObjectControlString phoneDescriptionTextField;
  private ObjectControlString phoneTypeTextField;
  private SimpleObjectProperty<Phone> phoneProperty = new SimpleObjectProperty<>();
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public PhoneEditPanel(CustomizationFx customization, Rolodex rolodex) {
    this.rolodex = rolodex;
    
    componentFactory = customization.getComponentFactoryFx();
    
    // Create the controls.
    phoneDescriptionTextField = componentFactory.createObjectInputString(null, 300, true, "Enter a description for the phone, e.g. the owner");
    phoneTypeTextField = componentFactory.createObjectInputString(null, 300, true, "Enter the phone model and/or type");
    
    createGUI();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    int row = 0;
    
    Label label = componentFactory.createStrongLabel("Add/Edit a phone");
    gridPane.add(label, 0, row, 2, 1);

    HBox buttonsBox = componentFactory.createHBox(12.0);
    Button addButton = componentFactory.createButton("Add phone", "Add the phone to the Rolodex");
    addButton.setOnAction((e) -> {
      Phone newPhone = createPhoneFromFields();
      if (newPhone != null) {
        rolodex.getPhoneList().getPhones().add(newPhone);
      }
     
    });
    buttonsBox.getChildren().add(addButton);
    
    Button updateButton = componentFactory.createButton("Update phone", "Update the phone in the Rolodex");
    phoneProperty.addListener(new ChangeListener<Phone>() {

      @Override
      public void changed(ObservableValue<? extends Phone> observable, Phone oldValue, Phone newValue) {
        updateButton.setDisable(newValue == null);
      }
    });
    updateButton.setDisable(phoneProperty.getValue() == null);

    updateButton.setOnAction((e) -> {
      updatePhoneIfControlsAreValid();
    });
    buttonsBox.getChildren().add(updateButton);
    gridPane.add(buttonsBox, 5, row);
    
    row++;
    
    label = componentFactory.createLabel("Phone description:");
    gridPane.add(label, 0, row);

    gridPane.add(phoneDescriptionTextField, 1, row);
        
    row++;
    
    label = componentFactory.createLabel("Phone type:");
    gridPane.add(label, 0, row);

    gridPane.add(phoneTypeTextField, 1, row);
    
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
   * Set the phone for which information will be shown and which can be updated.
   * 
   * @param phone the Phone for which information will be shown and which can be updated.
   */
  public void setPhone(Phone phone) {
    phoneProperty.setValue(phone);
    fillFieldsFromPhone(phone);
  }
  
  /**
   * Update the current phone, if the controls all have valid values.
   */
  private void updatePhoneIfControlsAreValid() {
    
    // Only update if all controls have valid values.
    if (!ObjectControl.areControlsValid(phoneDescriptionTextField, phoneTypeTextField)) {
      return;
    }
        
    updatePhoneFromFields(phoneProperty.get());
  }
  
  /**
   * Fill the controls with the values of a specific Phone.
   * 
   * @param phone the Phone who's values will be applied to the controls.
   */
  private void fillFieldsFromPhone(Phone phone) {
    clearFields();
    
    if (phone == null) {
      return;
    }
    
    phoneDescriptionTextField.setText(phone.getDescription());
    phoneTypeTextField.setText(phone.getPhoneType());
  }
  
  private void clearFields() {
    phoneDescriptionTextField.setText(null);
    phoneTypeTextField.setText(null);
  }
  
  /**
   * Create a Phone from the values of the controls.
   * <p>
   * The Phone will only be created if the controls all have valid values.
   * 
   * @return the newly created Phone, or null, if any control has an invalid value.
   */
  private Phone createPhoneFromFields() {
    
    // Only create if all controls have valid values.
    if (!ObjectControl.areControlsValid(phoneDescriptionTextField, phoneTypeTextField)) {
      return null;
    }
    
    Phone newPhone = ROLODEX_FACTORY.createPhone();
    
    updatePhoneFromFields(newPhone);
    
    return newPhone;
  }
  
  /**
   * Update a Phone with the values of the controls.
   * <p>
   * Values are only set, if the new value differs from the current value. This to avoid unnecessary updates of listeners to this object.
   * 
   * @param phone The Phone object to be updated.
   */
  public boolean updatePhoneFromFields(Phone phone) {
    String phoneDescription = phoneDescriptionTextField.getObjectValue();
    if (!PgUtilities.equals(phone.getDescription(), phoneDescription)) {
      phone.setDescription(phoneDescription);
    }      
        
    String phoneType = phoneTypeTextField.getObjectValue();
    if (!PgUtilities.equals(phone.getPhoneType(), phoneType)) {
      phone.setPhoneType(phoneType);
    }      
        
    return true;
  }
    
}


/**
 * This class provides a panel with controls to edit the properties of a PhoneAddressBookEntry.<br/>
 * There are buttons for updating the values of a PhoneAddressBookEntry, or to add a new PhoneAddressBookEntry.
 */
class PhoneAddressBookEntryEditPanel {
  private static final Logger LOGGER = Logger.getLogger(PhoneAddressBookEntryEditPanel.class.getName());

  private static RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private ComponentFactoryFx componentFactory;
  private GridPane gridPane;
  
  // Controls
  private ObjectControlString entryNameTextField;
  private ObjectControlEnumComboBox<PhoneAddressBookEntryType> phoneAddressBookEntryTypeField;
  private PhoneNumberTextField phoneNumberTextField;
  
  private PhoneAddressBook phoneAddressBook;
  private SimpleObjectProperty<PhoneAddressBookEntry> phoneAddressBookEntryProperty = new SimpleObjectProperty<>();
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public PhoneAddressBookEntryEditPanel(CustomizationFx customization, Rolodex rolodex) {
    
    componentFactory = customization.getComponentFactoryFx();
    
    // Create the controls.
    entryNameTextField = componentFactory.createObjectInputString(null, 300, true, "Enter the entry name");
    phoneAddressBookEntryTypeField =componentFactory.createObjectInputEEnumComboBox(PhoneAddressBookEntryType.NAME_AND_PHONE_NUMBER_ENTRY, null, true, "Enter the type of entry");
    phoneNumberTextField = new PhoneNumberTextField(customization, rolodex);
    
    createGUI();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    int row = 0;
    
    Label label = componentFactory.createStrongLabel("Add/Edit a phone address book entry");
    gridPane.add(label, 0, row, 2, 1);

    HBox buttonsBox = componentFactory.createHBox(12.0);
    Button addButton = componentFactory.createButton("Add entry", "Add the phone address book entry");
    addButton.setOnAction((e) -> {
      PhoneAddressBookEntry newPhoneAddressBookEntry = createPhoneAddressBookEntryFromFields();
      if (newPhoneAddressBookEntry != null) {
        int index = 1;
        for  (PhoneAddressBookEntry entry: phoneAddressBook.getEntries()) {
          LOGGER.severe("entry " + index++ + ": " + entry.toString());
        }
        phoneAddressBook.getEntries().add(newPhoneAddressBookEntry);
      }
     
    });
    buttonsBox.getChildren().add(addButton);
    
    Button updateButton = componentFactory.createButton("Update entry", "Update the phone address book entry");
    phoneAddressBookEntryProperty.addListener(new ChangeListener<PhoneAddressBookEntry>() {

      @Override
      public void changed(ObservableValue<? extends PhoneAddressBookEntry> observable, PhoneAddressBookEntry oldValue, PhoneAddressBookEntry newValue) {
        LOGGER.severe("Container: " + (newValue != null ? newValue.eContainer() : "(null)"));
        if (newValue != null) {
          phoneAddressBook = (PhoneAddressBook) newValue.eContainer();
        }
        updateButton.setDisable(newValue == null);
      }
    });
    updateButton.setDisable(phoneAddressBookEntryProperty.getValue() == null);

    updateButton.setOnAction((e) -> {
      updatePhoneAddressBookEntryIfControlsAreValid();
    });
    buttonsBox.getChildren().add(updateButton);
    gridPane.add(buttonsBox, 5, row);
    
    row++;
    
    label = componentFactory.createLabel("Entry name:");
    gridPane.add(label, 0, row);

    gridPane.add(entryNameTextField, 1, row);
    
    label = componentFactory.createLabel("Entry type:");
    gridPane.add(label, 2, row);

    gridPane.add(phoneAddressBookEntryTypeField, 3, row);
    
    row++;
    
    label = componentFactory.createLabel("Phone number:");
    gridPane.add(label, 0, row);

    gridPane.add(phoneNumberTextField, 1, row);
    
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
   * Set the phone address book to which an entry can be added.
   * 
   * @param phoneAddressBook the PhoneAddressBook to which an entry can be added.
   */
  public void setPhoneAddressBook(PhoneAddressBook phoneAddressBook) {
    this.phoneAddressBook = phoneAddressBook;
    phoneAddressBookEntryProperty.setValue(null);
  }
  
  /**
   * Set the phone address book entry for which information will be shown and which can be updated.
   * 
   * @param phone the Phone for which information will be shown and which can be updated.
   */
  public void setPhoneAddressBookEntry(PhoneAddressBookEntry phoneAddressBookEntry) {
    phoneAddressBookEntryProperty.setValue(phoneAddressBookEntry);
    fillFieldsFromPhoneAddressBookEntry(phoneAddressBookEntry);
  }
  
  /**
   * Update the current phone address book entry, if the controls all have valid values.
   */
  private void updatePhoneAddressBookEntryIfControlsAreValid() {
    
    // Only update if all controls have valid values.
    if (!ObjectControl.areControlsValid(entryNameTextField, phoneAddressBookEntryTypeField, phoneNumberTextField)) {
      return;
    }
        
    updatePhoneAddressBookEntryFromFields(phoneAddressBookEntryProperty.get());
  }
  
  /**
   * Fill the controls with the values of a specific PhoneAddressBookEntry.
   * 
   * @param phoneAddressBookEntry the PhoneNumber who's values will be applied to the controls.
   */
  private void fillFieldsFromPhoneAddressBookEntry(PhoneAddressBookEntry phoneAddressBookEntry) {
    clearFields();
    
    if (phoneAddressBookEntry == null) {
      return;
    }
    
    entryNameTextField.setText(phoneAddressBookEntry.getEntryName());
    phoneAddressBookEntryTypeField.setObjectValue(phoneAddressBookEntry.getEntryType());
    phoneNumberTextField.setObjectValue(phoneAddressBookEntry.getPhoneNumber().toString());
  }
  
  private void clearFields() {
    entryNameTextField.setText(null);
    phoneAddressBookEntryTypeField.setObjectValue(null);
    phoneNumberTextField.setText(null);
  }
  
  /**
   * Create a PhoneAddressBookEntry from the values of the controls.
   * <p>
   * The PhoneAddressBookEntry will only be created if the controls all have valid values.
   * 
   * @return the newly created v, or null, if any control has an invalid value.
   */
  private PhoneAddressBookEntry createPhoneAddressBookEntryFromFields() {
    
    // Only create if all controls have valid values.
    if (!ObjectControl.areControlsValid(entryNameTextField, phoneAddressBookEntryTypeField, phoneNumberTextField)) {
      return null;
    }
    
    PhoneAddressBookEntry newPhoneAddressBookEntry = ROLODEX_FACTORY.createPhoneAddressBookEntry();
    
    updatePhoneAddressBookEntryFromFields(newPhoneAddressBookEntry);
    
    return newPhoneAddressBookEntry;
  }
  
  /**
   * Update a PhoneAddressBookEntry with the values of the controls.
   * <p>
   * Values are only set, if the new value differs from the current value. This to avoid unnecessary updates of listeners to this object.
   * 
   * @param phoneAddressBookEntry The PhoneNumber object to be updated.
   */
  public boolean updatePhoneAddressBookEntryFromFields(PhoneAddressBookEntry phoneAddressBookEntry) {
    String entryName = entryNameTextField.getObjectValue();
    if (!PgUtilities.equals(phoneAddressBookEntry.getEntryName(), entryName)) {
      phoneAddressBookEntry.setEntryName(entryName);
    }      
        
    PhoneAddressBookEntryType entryType = (PhoneAddressBookEntryType) phoneAddressBookEntryTypeField.getObjectValue();
    if (!PgUtilities.equals(phoneAddressBookEntry.getEntryType(), entryType)) {
      phoneAddressBookEntry.setEntryType(entryType);
    } 
    
    PhoneNumber phoneNumber = phoneNumberTextField.getMatchingPhoneNumber();
    if (!PgUtilities.equals(phoneAddressBookEntry.getPhoneNumber(), phoneNumber)) {
      phoneAddressBookEntry.setPhoneNumber(phoneNumber);
    }      
        
    return true;
  }
    
}



/**
 * This class provides the descriptor for the Phones Table.
 */
class PhonesTableDescriptor extends EObjectTableDescriptor<Phone> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<Phone>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Phone>>of(
      new EObjectTableColumnDescriptorBasic<Phone>(ROLODEX_PACKAGE.getPhone_Description(), "Phone description", true, true),
      new EObjectTableColumnDescriptorBasic<Phone>(ROLODEX_PACKAGE.getPhone_PhoneType(), "Phone type", true, true)
      );
  
  @SuppressWarnings("serial")
  private static Map<TableRowOperation, TableRowOperationDescriptor<Phone>> rowOperations = new HashMap<>() {
    {
    put(TableRowOperation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public PhonesTableDescriptor() {
    super("The are no phones to show", null, columnDescriptors, rowOperations);
  }

}


/**
 * This class provides the descriptor for the Cities Table.
 */
class PhoneAddressBookTableDescriptor extends EObjectTableDescriptor<PhoneAddressBookEntry> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<PhoneAddressBookEntry>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<PhoneAddressBookEntry>>of(
      new EObjectTableColumnDescriptorBasic<PhoneAddressBookEntry>(ROLODEX_PACKAGE.getPhoneAddressBookEntry_EntryName(), "Entry name", 300, true, true),
      new EObjectTableColumnDescriptorBasic<PhoneAddressBookEntry>(ROLODEX_PACKAGE.getPhoneAddressBookEntry_EntryType(), "Entry type", 300, true, true),
      new EObjectTableColumnDescriptorBasic<PhoneAddressBookEntry>(ROLODEX_PACKAGE.getPhoneAddressBookEntry_PhoneNumber(), "Phone number", 300, true, true)
      );
  
  @SuppressWarnings("serial")
  private static Map<TableRowOperation, TableRowOperationDescriptor<PhoneAddressBookEntry>> rowOperations = new HashMap<>() {
    {
    put(TableRowOperation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public PhoneAddressBookTableDescriptor() {
    super("The are no entries to show", null, columnDescriptors, rowOperations);
  }

}
