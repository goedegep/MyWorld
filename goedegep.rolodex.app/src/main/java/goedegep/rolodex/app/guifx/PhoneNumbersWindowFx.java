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
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.objectcontrols.ObjectControlEnumComboBox;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.rolodex.model.ConnectionType;
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
public class PhoneNumbersWindowFx extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PhoneNumbersWindowFx.class.getName());
  private static final String WINDOW_TITLE   = "Phone numbers";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<PhoneNumber> phoneNumbersTable;
  private ReferredByPanel referredByPanel;
  private PhoneNumberEditPanel phoneNumberEditPanel;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public PhoneNumbersWindowFx(CustomizationFx customization, Rolodex rolodex) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    this.rolodex = rolodex;
    componentFactory = customization.getComponentFactoryFx();
    
    referredByPanel = new ReferredByPanel(customization, "Referred by");
    phoneNumberEditPanel = new PhoneNumberEditPanel(customization, rolodex);
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>(){

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        phoneNumbersTable.setFilterExpression(newValue, null);
      }
      
    });
    
    phoneNumbersTable.addObjectSelectionListener((source, phoneNumber) -> {
        referredByPanel.setObject(phoneNumber);
        phoneNumberEditPanel.setPhoneNumber(phoneNumber);
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
    
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    rootLayout.getChildren().add(eObjectTableControlPanel);
    rootLayout.getChildren().add(createPhoneNumbersTable());    
    rootLayout.getChildren().add(referredByPanel.getPanel());
    rootLayout.getChildren().add(phoneNumberEditPanel.getEditPanel());
        
    setScene(new Scene(rootLayout, 1300, 700));
  }
  
  /**
   * Create the phoneNumbersTable.
   * 
   * @return the created phoneNumbersTable
   */
  private EObjectTable<PhoneNumber> createPhoneNumbersTable() {
    phoneNumbersTable = new EObjectTable<PhoneNumber>(customization, ROLODEX_PACKAGE.getPhoneNumber(), new PhoneNumbersTableDescriptor(), rolodex.getPhoneNumberList(), RolodexPackage.eINSTANCE.getPhoneNumberList_PhoneNumbers());
        
    return phoneNumbersTable;
  }

}

/**
 * This class provides a panel with controls to edit the properties of a PhoneNumber.<br/>
 * There are buttons for updating the values of a PhoneNumber, or to add a new PhoneNumber.
 */
class PhoneNumberEditPanel {
  private static RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;
  
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private GridPane gridPane;
  
  // Controls
  private ObjectControlString phoneNumberTextField;
  private ObjectControlEnumComboBox<ConnectionType> connectionTypeTextField;
  private ObjectControlString descriptionTextField;
  private SimpleObjectProperty<PhoneNumber> phoneNumberProperty = new SimpleObjectProperty<>();
  private ObjectControlGroup objectControlGroup;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public PhoneNumberEditPanel(CustomizationFx customization, Rolodex rolodex) {
    this.rolodex = rolodex;
    
    componentFactory = customization.getComponentFactoryFx();
    
    // Create the controls.
    phoneNumberTextField = componentFactory.createObjectControlString(null, 300, true, "Enter the phone number");
    connectionTypeTextField =componentFactory.createObjectControlEnumComboBox(ConnectionType.VAST, true, "Enter the kind of connection");
//    connectionTypeTextField = new ObjectInputConnectionType(ROLODEX_PACKAGE.getConnectionType(), true, "Enter the kind of connection");
    descriptionTextField = componentFactory.createObjectControlString(null, 300, true, "Enter a description");
    objectControlGroup = new ObjectControlGroup();
    objectControlGroup.addObjectControls(phoneNumberTextField, connectionTypeTextField, descriptionTextField);
    
    createGUI();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    int row = 0;
    
    Label label = componentFactory.createStrongLabel("Add/Edit an phone number");
    gridPane.add(label, 0, row, 2, 1);

    HBox buttonsBox = componentFactory.createHBox(12.0);
    Button addButton = componentFactory.createButton("Add phone number", "Add the phone number to the Rolodex");
    addButton.setOnAction((e) -> {
      PhoneNumber newPhoneNumber = createPhoneNumberFromFields();
      if (newPhoneNumber != null) {
        rolodex.getPhoneNumberList().getPhoneNumbers().add(newPhoneNumber);
      }
     
    });
    buttonsBox.getChildren().add(addButton);
    
    Button updateButton = componentFactory.createButton("Update phone number", "Update the phone number in the Rolodex");
    phoneNumberProperty.addListener(new ChangeListener<PhoneNumber>() {

      @Override
      public void changed(ObservableValue<? extends PhoneNumber> observable, PhoneNumber oldValue, PhoneNumber newValue) {
        updateButton.setDisable(newValue == null);
      }
    });
    updateButton.setDisable(phoneNumberProperty.getValue() == null);

    updateButton.setOnAction((e) -> {
      updatePhoneNumberIfControlsAreValid();
    });
    buttonsBox.getChildren().add(updateButton);
    gridPane.add(buttonsBox, 5, row);
    
    row++;
    
    label = componentFactory.createLabel("Phone number:");
    gridPane.add(label, 0, row);

    gridPane.add(phoneNumberTextField.getControl(), 1, row);
    
    label = componentFactory.createLabel("Connection type:");
    gridPane.add(label, 2, row);

    gridPane.add(connectionTypeTextField.getControl(), 3, row);
    
    row++;
    
    label = componentFactory.createLabel("Description:");
    gridPane.add(label, 0, row);

    gridPane.add(descriptionTextField.getControl(), 1, row);
    
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
   * Set the phone number for which information will be shown and which can be updated.
   * 
   * @param phoneNumber the PhoneNumber for which information will be shown and which can be updated.
   */
  public void setPhoneNumber(PhoneNumber phoneNumber) {
    phoneNumberProperty.setValue(phoneNumber);
    fillFieldsFromPhoneNumber(phoneNumber);
  }
  
  /**
   * Update the current phone number, if the controls all have valid values.
   */
  private void updatePhoneNumberIfControlsAreValid() {
    
    // Only update if all controls have valid values.
    if (!objectControlGroup.isValid()) {
      return;
    }
        
    updatePhoneNumberFromFields(phoneNumberProperty.get());
  }
  
  /**
   * Fill the controls with the values of a specific PhoneNumber.
   * 
   * @param phoneNumber the PhoneNumber who's values will be applied to the controls.
   */
  private void fillFieldsFromPhoneNumber(PhoneNumber phoneNumber) {
    clearFields();
    
    if (phoneNumber == null) {
      return;
    }
    
    phoneNumberTextField.setValue(phoneNumber.getPhoneNumber());
    connectionTypeTextField.setValue(phoneNumber.getConnectionType());
    descriptionTextField.setValue(phoneNumber.getDescription());    
  }
  
  private void clearFields() {
    phoneNumberTextField.setValue(null);
    connectionTypeTextField.setValue(null);
    descriptionTextField.setValue(null);
  }
  
  /**
   * Create a PhoneNumber from the values of the controls.
   * <p>
   * The PhoneNumber will only be created if the controls all have valid values.
   * 
   * @return the newly created PhoneNumber, or null, if any control has an invalid value.
   */
  private PhoneNumber createPhoneNumberFromFields() {
    
    // Only create if all controls have valid values.
    if (!objectControlGroup.isValid()) {
      return null;
    }
    
    PhoneNumber newPhoneNumber = ROLODEX_FACTORY.createPhoneNumber();
    
    updatePhoneNumberFromFields(newPhoneNumber);
    
    return newPhoneNumber;
  }
  
  /**
   * Update a PhoneNumber with the values of the controls.
   * <p>
   * Values are only set, if the new value differs from the current value. This to avoid unnecessary updates of listeners to this object.
   * 
   * @param phoneNumber The PhoneNumber object to be updated.
   */
  public boolean updatePhoneNumberFromFields(PhoneNumber phoneNumber) {
    String phoneNumberText = phoneNumberTextField.getValue();
    if (!PgUtilities.equals(phoneNumber.getPhoneNumber(), phoneNumberText)) {
      phoneNumber.setPhoneNumber(phoneNumberText);
    }      
        
    ConnectionType connectionType = connectionTypeTextField.getValue();
//    ConnectionType connectionType = ConnectionType.getByName(connectionTypeText);
    if (!PgUtilities.equals(phoneNumber.getConnectionType(), connectionType)) {
      phoneNumber.setConnectionType(connectionType);
    } 
    
    String description = descriptionTextField.getValue();
    if (!PgUtilities.equals(phoneNumber.getDescription(), description)) {
      phoneNumber.setDescription(description);
    }      
        
    return true;
  }
    
}



/**
 * This class provides the descriptor for the Cities Table.
 */
class PhoneNumbersTableDescriptor extends EObjectTableDescriptor<PhoneNumber> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<PhoneNumber>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<PhoneNumber>>of(
      new EObjectTableColumnDescriptorBasic<PhoneNumber>(ROLODEX_PACKAGE.getPhoneNumber_PhoneNumber(), "Phone number", true, true),
      new EObjectTableColumnDescriptorBasic<PhoneNumber>(ROLODEX_PACKAGE.getPhoneNumber_Description(), "Description", true, true),
      new EObjectTableColumnDescriptorBasic<PhoneNumber>(ROLODEX_PACKAGE.getPhoneNumber_ConnectionType(), "Type", true, true)
      );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<PhoneNumber>> rowOperations = new HashMap<>() {
    {
    put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public PhoneNumbersTableDescriptor() {
    super("The are no phone numbers to show", null, columnDescriptors, rowOperations);
  }

}
