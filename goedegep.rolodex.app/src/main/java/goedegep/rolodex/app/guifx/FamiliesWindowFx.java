package goedegep.rolodex.app.guifx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.appgen.TableRowOperation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCheckBox;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.objectcontrols.ObjectControlBoolean;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.rolodex.app.logic.PhoneNumberListStringConverter;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.AddressForPeriod;
import goedegep.rolodex.model.Family;
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
 * This class provides a window with information on Families.
 * <p>
 * The top part of the window shows an editable Families table.<br/>
 * The bottom part shows all details of a Family. The information here can be edited to update the Family information, or to add a new Family.
 */
public class FamiliesWindowFx extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(FamiliesWindowFx.class.getName());
  private static final String WINDOW_TITLE   = "Families";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<Family> familiesTable;
  private FamilyMembersPanel familyMembersPanel;
  private PreviousAddressesPanel previousAddressesPanel;
  private ReferredByPanel referredByPanel;
  private FamilyEditPanel familyEditPanel;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public FamiliesWindowFx(CustomizationFx customization, Rolodex rolodex) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    this.rolodex = rolodex;
    componentFactory = customization.getComponentFactoryFx();
    
    familyMembersPanel = new FamilyMembersPanel(customization, rolodex);
    previousAddressesPanel = new PreviousAddressesPanel(customization, rolodex);
    referredByPanel = new ReferredByPanel(customization, "Referred by");
    familyEditPanel = new FamilyEditPanel(customization, rolodex);
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>(){

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        familiesTable.setFilterExpression(newValue, null);
      }
      
    });
    
    familiesTable.addObjectSelectionListener((source, family) -> {
        familyMembersPanel.setFamily(family);
        previousAddressesPanel.setAddressHolder(family);
        referredByPanel.setObject(family);
        familyEditPanel.setFamily(family);
    });
    
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
    VBox rootLayout = componentFactory.createVBox();
    
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    rootLayout.getChildren().add(eObjectTableControlPanel);
    rootLayout.getChildren().add(createFamiliesTable());
    
    rootLayout.getChildren().add(familyMembersPanel);
    rootLayout.getChildren().add(previousAddressesPanel);
    
    rootLayout.getChildren().add(referredByPanel.getPanel());
    rootLayout.getChildren().add(familyEditPanel.getEditPanel());
        
    setScene(new Scene(rootLayout, 1300, 700));
  }
  
  /**
   * Create the familiesTable.
   * 
   * @return the created familiesTable
   */
  private EObjectTable<Family> createFamiliesTable() {
    familiesTable = new EObjectTable<Family>(customization, ROLODEX_PACKAGE.getFamily(), new FamiliesTableDescriptor(), rolodex.getFamilyList(), RolodexPackage.eINSTANCE.getFamilyList_Families());
        
    return familiesTable;
  }
}

/**
 * This class provides a panel with controls to edit the properties of a Family.<br/>
 * There are buttons for updating the values of a Family, or to add a new Family.
 */
class FamilyEditPanel {
  private static RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;
  
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private GridPane gridPane;
  
  // Controls
  private ObjectControlString familyTitleTextField;
  private ObjectControlString familyNameTextField;
  private AddressTextField addressTextField;
  private ObjectControlBoolean moveToAddress;
  private PhoneNumberTextField phoneNumberTextFields[];
  private SimpleObjectProperty<Family> familyProperty = new SimpleObjectProperty<>();
  private ObjectControlGroup objectControlGroup;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public FamilyEditPanel(CustomizationFx customization, Rolodex rolodex) {
    this.rolodex = rolodex;
    
    componentFactory = customization.getComponentFactoryFx();
    
    // Create the controls.
    familyTitleTextField = componentFactory.createObjectControlString(null, 300, true, "Enter the family title");
    familyNameTextField = componentFactory.createObjectControlString(null, 100, true, "Enter the family name");
    addressTextField = new AddressTextField(customization, rolodex);
    moveToAddress = new ObjectControlBoolean("Move to", false, true, "Select for moving to this address. In this case the existing address is moved to the 'previous addresses'.");
    phoneNumberTextFields = new PhoneNumberTextField[4];
    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      phoneNumberTextFields[i] = new PhoneNumberTextField(customization, rolodex);
    }
    objectControlGroup = new ObjectControlGroup();
    objectControlGroup.addObjectControls(familyTitleTextField, familyNameTextField, addressTextField,
        phoneNumberTextFields[0], phoneNumberTextFields[1], phoneNumberTextFields[2], phoneNumberTextFields[3]);
    
    createGUI();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    int row = 0;
    
    Label label = componentFactory.createStrongLabel("Add/Edit a family");
    gridPane.add(label, 0, row, 2, 1);

    HBox buttonsBox = componentFactory.createHBox(12.0);
    Button addButton = componentFactory.createButton("Add family", "Add the family to the Rolodex");
    addButton.setOnAction((e) -> {
      Family newFamily = createFamilyFromFields();
      if (newFamily != null) {
        rolodex.getFamilyList().getFamilies().add(newFamily);
      }
     
    });
    buttonsBox.getChildren().add(addButton);
    
    Button updateButton = componentFactory.createButton("Update family", "Update the family in the Rolodex");
    familyProperty.addListener(new ChangeListener<Family>() {

      @Override
      public void changed(ObservableValue<? extends Family> observable, Family oldValue, Family newValue) {
        updateButton.setDisable(newValue == null);
      }
    });
    updateButton.setDisable(familyProperty.getValue() == null);

    updateButton.setOnAction((e) -> {
      updateFamilyIfControlsAreValid();
    });
    buttonsBox.getChildren().add(updateButton);
    gridPane.add(buttonsBox, 5, row);
    
    row++;
    
    label = componentFactory.createLabel("Family title:");
    gridPane.add(label, 0, row);

    gridPane.add(familyTitleTextField, 1, row);
    
    label = componentFactory.createLabel("Family name:");
    gridPane.add(label, 2, row);

    gridPane.add(familyNameTextField, 3, row);
    
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
   * Set the family for which information will be shown and which can be updated.
   * 
   * @param family the Family for which information will be shown and which can be updated.
   */
  public void setFamily(Family family) {
    familyProperty.setValue(family);
    fillFieldsFromFamily(family);
  }
  
  /**
   * Update the current family, if the controls all have valid values.
   */
  private void updateFamilyIfControlsAreValid() {
    
    // Only update if all controls have valid values.
    if (!objectControlGroup.getIsValid()) {
      return;
    }
        
    updateFamilyFromFields(familyProperty.get());
  }
  
  /**
   * Fill the controls with the values of a specific Family.
   * 
   * @param family the Family who's values will be applied to the controls.
   */
  private void fillFieldsFromFamily(Family family) {
    clearFields();
    
    if (family == null) {
      return;
    }
    
    familyTitleTextField.setText(family.getFamilyTitle());
    familyNameTextField.setText(family.getFamilyName());
    
    addressTextField.setText(null);
    Address address = family.getAddress();
    if (address != null) {
      addressTextField.setText(address.toString());
    }
    
    List<PhoneNumber> phoneNumbers = family.getPhoneNumbers();
    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      if (phoneNumbers.size() > i) {
        phoneNumberTextFields[i].setText(phoneNumbers.get(i).toString());
      } else {
        phoneNumberTextFields[i].setText(null);
      }
    }
  }
  
  private void clearFields() {
    familyTitleTextField.setText(null);
    familyNameTextField.setText(null);

    addressTextField.setText(null);
    moveToAddress.setSelected(false);
    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      phoneNumberTextFields[i].setText(null);
    }
  }
  
  /**
   * Create a Family from the values of the controls.
   * <p>
   * The Family will only be created if the controls all have valid values.
   * 
   * @return the newly created Family, or null, if any control has an invalid value.
   */
  private Family createFamilyFromFields() {
    
    // Only create if all controls have valid values.
    if (!objectControlGroup.getIsValid()) {
      return null;
    }
    
    Family newFamily = ROLODEX_FACTORY.createFamily();
    
    updateFamilyFromFields(newFamily);
    
    return newFamily;
  }
  
  /**
   * Update a Family with the values of the controls.
   * <p>
   * Values are only set, if the new value differs from the current value. This to avoid unnecessary updates of listeners to this object.
   * 
   * @param family The Family object to be updated.
   */
  public boolean updateFamilyFromFields(Family family) {
      String familyTitle = familyTitleTextField.ocGetValue();
      if (!PgUtilities.equals(family.getFamilyTitle(), familyTitle)) {
        family.setFamilyTitle(familyTitle);
      }
      
      String familyName = familyNameTextField.ocGetValue();
      if (!PgUtilities.equals(family.getFamilyName(), familyName)) {
        family.setFamilyName(familyName);
      }
                  
      Address address = addressTextField.getMatchingAddress();
      if (moveToAddress.isSelected()) {
        Address currentAddress = family.getAddress();
        if (currentAddress != null) {
          AddressForPeriod addressForPeriod = ROLODEX_FACTORY.createAddressForPeriod();
          addressForPeriod.setAddress(currentAddress);
          family.getPreviousAddresses().add(addressForPeriod);
        }
      }
      if (!PgUtilities.equals(family.getAddress(), address)) {
        family.setAddress(address);
      }
      
      for (int i = 0; i < phoneNumberTextFields.length; i++) {
        PhoneNumber phoneNumber = phoneNumberTextFields[i].getMatchingPhoneNumber();
        if (phoneNumber != null) {
          family.getPhoneNumbers().add(phoneNumber);
        }
      }
        
    return true;
  }
    
}



/**
 * This class provides the descriptor for the Families Table.
 */
class FamiliesTableDescriptor extends EObjectTableDescriptor<Family> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<Family>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Family>>of(
      new EObjectTableColumnDescriptorBasic<Family>(ROLODEX_PACKAGE.getFamily_FamilyTitle(), "Title", true, true),
      new EObjectTableColumnDescriptorBasic<Family>(ROLODEX_PACKAGE.getFamily_FamilyName(), "Family name", true, true),
      new EObjectTableColumnDescriptorBasic<Family>(ROLODEX_PACKAGE.getAddressHolder_Address(), "Address", true, true),
      new EObjectTableColumnDescriptorBasic<Family>(ROLODEX_PACKAGE.getPhoneNumberHolder_PhoneNumbers(), "Phone numbers", 150, true, true, new PhoneNumberListStringConverter()),
      new EObjectTableColumnDescriptorBasic<Family>(ROLODEX_PACKAGE.getDescription_Description(), "Remarks", true, true),
      new EObjectTableColumnDescriptorCheckBox<Family>(ROLODEX_PACKAGE.getArchive_Archived(), "Archived", true, true)
      );
  
  @SuppressWarnings("serial")
  private static Map<TableRowOperation, TableRowOperationDescriptor<Family>> rowOperations = new HashMap<>() {
    {
    put(TableRowOperation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public FamiliesTableDescriptor() {
    super("There are no families to show", null, columnDescriptors, rowOperations);
  }

}
