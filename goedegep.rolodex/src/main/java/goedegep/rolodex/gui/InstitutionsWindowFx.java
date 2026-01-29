package goedegep.rolodex.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.Operation;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.eobjecttable.TableRowOperationDescriptor;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.rolodex.logic.PhoneNumberListStringConverter;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
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
 * This class provides a window with information on Institutions.
 * <p>
 * The top part of the window shows an editable Institutions table.<br/>
 * The bottom part shows all details of an Institution. The information here can be edited to update the Institution information, or to add a new Institution.
 */
public class InstitutionsWindowFx extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(InstitutionsWindowFx.class.getName());
  private static final String WINDOW_TITLE   = "Institutions";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private Rolodex rolodex;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<Institution> institutionsTable;
  private ReferredByPanel referredByPanel;
  private InstitutionEditPanel institutionEditPanel;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public InstitutionsWindowFx(CustomizationFx customization, Rolodex rolodex) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    this.rolodex = rolodex;

    referredByPanel = new ReferredByPanel(customization, "Referred by");
    institutionEditPanel = new InstitutionEditPanel(customization, rolodex);
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>(){

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        institutionsTable.setFilterExpression(newValue, null);
      }
      
    });
    
    institutionsTable.addObjectSelectionListener((source, institution) -> {
        referredByPanel.setObject(institution);
        institutionEditPanel.setInstitution(institution);
    });
    
    show();
  }
  
  /**
   * Create the GUI.
   * <p>
   * The window consists of the following parts:
   * <ul>
   * <li>Controls which operate on the table.</li>
   * <li>The institutions table</li>
   * <li>A panel to add a new person</li>
   * </ul>
   */
  private void createGUI() {
    VBox rootLayout = new VBox();
    
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    rootLayout.getChildren().add(eObjectTableControlPanel);
    rootLayout.getChildren().add(createInstitutionsTable());
    rootLayout.getChildren().add(referredByPanel.getPanel());
    rootLayout.getChildren().add(institutionEditPanel.getEditPanel());
        
    setScene(new Scene(rootLayout, 1300, 700));
  }
  
  /**
   * Create the personsTable.
   * 
   * @return the created personsTable
   */
  private EObjectTable<Institution> createInstitutionsTable() {
    institutionsTable = new EObjectTable<Institution>(customization, ROLODEX_PACKAGE.getInstitution(), new InstitutionsTableDescriptor(), rolodex.getInstitutionList(),RolodexPackage.eINSTANCE.getInstitutionList_Institutions());
        
    return institutionsTable;
  }

}


/**
 * This class provides a panel with controls to edit the properties of an Institution.<br/>
 * There are buttons for updating the values of an Institution, or to add a new Institution.
 */
class InstitutionEditPanel {
  private static RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;
  
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private GridPane gridPane;
  
  // Controls
  private ObjectControlString institutionNameTextField;
  private AddressTextField addressTextField;
  private AddressTextField mailingAddressTextField;
  private PhoneNumberTextField phoneNumberTextFields[];
  private SimpleObjectProperty<Institution> institutionProperty = new SimpleObjectProperty<>();
  private ObjectControlGroup objectControlGroup = new ObjectControlGroup();
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a {@code Rolodex}
   */
  public InstitutionEditPanel(CustomizationFx customization, Rolodex rolodex) {
    this.rolodex = rolodex;
    
    componentFactory = customization.getComponentFactoryFx();
    
    // Create the controls.
    institutionNameTextField = componentFactory.createObjectControlString(null, 100, true, "Enter the family name");
    addressTextField = new AddressTextField(customization, rolodex);
    mailingAddressTextField = new AddressTextField(customization, rolodex);
    objectControlGroup.addObjectControls(institutionNameTextField, addressTextField, mailingAddressTextField);

    phoneNumberTextFields = new PhoneNumberTextField[4];
    objectControlGroup.addObjectControls(institutionNameTextField, addressTextField, mailingAddressTextField);
    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      phoneNumberTextFields[i] = new PhoneNumberTextField(customization, rolodex);
    }
    objectControlGroup.addObjectControls(phoneNumberTextFields);
        
    createGUI();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    int row = 0;
    
    Label label = componentFactory.createStrongLabel("Add/Edit an institution");
    gridPane.add(label, 0, row, 2, 1);

    HBox buttonsBox = componentFactory.createHBox(12.0);
    Button addButton = componentFactory.createButton("Add institution", "Add the institution to the Rolodex");
    addButton.setOnAction((e) -> {
      Institution newInstitution = createInstitutionFromFields();
      if (newInstitution != null) {
        rolodex.getInstitutionList().getInstitutions().add(newInstitution);
      }
     
    });
    buttonsBox.getChildren().add(addButton);    
    
    Button updateButton = componentFactory.createButton("Update institution", "Update the institution in the Rolodex");
    institutionProperty.addListener(new ChangeListener<Institution>() {

      @Override
      public void changed(ObservableValue<? extends Institution> observable, Institution oldValue, Institution newValue) {
        updateButton.setDisable(newValue == null);
      }
    });
    updateButton.setDisable(institutionProperty.getValue() == null);

    updateButton.setOnAction((e) -> {
      updateInstitutionIfControlsAreValid();
    });
    buttonsBox.getChildren().add(updateButton);
    gridPane.add(buttonsBox, 5, row);
    
    row++;
    
    label = componentFactory.createLabel("Institution name:");
    gridPane.add(label, 0, row);

    gridPane.add(institutionNameTextField.getControl(), 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Address:");
    gridPane.add(label, 0, row);

    gridPane.add(addressTextField.getControl(), 1, row);
    
    label = componentFactory.createLabel("Mailing address:");
    gridPane.add(label, 2, row);

    gridPane.add(mailingAddressTextField.getControl(), 3, row);
    
    row++;
    
    label = componentFactory.createLabel("Phone numbers");
    gridPane.add(label,  0, row);
    
    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      gridPane.add(phoneNumberTextFields[i].getControl(), 1 + i, row);
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
   * Set the institution for which information will be shown and which can be updated.
   * 
   * @param institution the Institution for which information will be shown and which can be updated.
   */
  public void setInstitution(Institution institution) {
    institutionProperty.setValue(institution);
    fillFieldsFromInstitution(institution);
  }
  
  /**
   * Update the current institution, if the controls all have valid values.
   */
  private void updateInstitutionIfControlsAreValid() {
    
    // Only update if all controls have valid values.
    if (!objectControlGroup.isValid()) {
      return;
    }
        
    updateInstitutionFromFields(institutionProperty.get());
  }
  
  /**
   * Fill the controls with the values of a specific Institution.
   * 
   * @param institution the Family who's values will be applied to the controls.
   */
  private void fillFieldsFromInstitution(Institution institution) {
    clearFields();
    
    if (institution == null) {
      return;
    }
    
    institutionNameTextField.setObject(institution.getName());
    
    Address address = institution.getAddress();
    if (address != null) {
      addressTextField.setObject(address.toString());
    }
    
    Address mailingAddress = institution.getMailingAddress();
    if (mailingAddress != null) {
      mailingAddressTextField.setObject(mailingAddress.toString());
    }
    
    List<PhoneNumber> phoneNumbers = institution.getPhoneNumbers();
    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      if (phoneNumbers.size() > i) {
        phoneNumberTextFields[i].setObject(phoneNumbers.get(i).toString());
      } else {
        phoneNumberTextFields[i].setObject(null);
      }
    }
  }
  
  private void clearFields() {
    institutionNameTextField.setObject(null);

    addressTextField.setObject(null);
    mailingAddressTextField.setObject(null);
    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      phoneNumberTextFields[i].setObject(null);
    }
  }
  
  /**
   * Create an Institution from the values of the controls.
   * <p>
   * The Institution will only be created if the controls all have valid values.
   * 
   * @return the newly created Institution, or null, if any control has an invalid value.
   */
  private Institution createInstitutionFromFields() {
    
    // Only create if all controls have valid values.
    if (!objectControlGroup.isValid()) {
      return null;
    }
    
    Institution newInstitution = ROLODEX_FACTORY.createInstitution();
    
    updateInstitutionFromFields(newInstitution);
    
    return newInstitution;
  }
  
  /**
   * Update an Institution with the values of the controls.
   * <p>
   * Values are only set, if the new value differs from the current value. This to avoid unnecessary updates of listeners to this object.
   * 
   * @param institution The Institution object to be updated.
   */
  public boolean updateInstitutionFromFields(Institution institution) {
      String institutionName = institutionNameTextField.getValue();
      if (!Objects.equals(institution.getName(), institutionName)) {
        institution.setName(institutionName);
      }
                  
      Address address = addressTextField.getMatchingAddress();
      if (!Objects.equals(institution.getAddress(), address)) {
        institution.setAddress(address);
      }
      
      Address mailingAddress = mailingAddressTextField.getMatchingAddress();
      if (!Objects.equals(institution.getMailingAddress(), mailingAddress)) {
        institution.setMailingAddress(mailingAddress);
      }
      
      for (int i = 0; i < phoneNumberTextFields.length; i++) {
        PhoneNumber phoneNumber = phoneNumberTextFields[i].getMatchingPhoneNumber();
        if (phoneNumber != null) {
          institution.getPhoneNumbers().add(phoneNumber);
        }
      }
        
    return true;
  }
    
}


/**
 * This class provides the descriptor for the Cities Table.
 */
class InstitutionsTableDescriptor extends EObjectTableDescriptor<Institution> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<Institution>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Institution>>of(
      new EObjectTableColumnDescriptorBasic<Institution>(ROLODEX_PACKAGE.getInstitution_Name(), "Name of the institution", true, true),
      new EObjectTableColumnDescriptorBasic<Institution>(ROLODEX_PACKAGE.getAddressHolder_Address(), "Address", true, true),
      new EObjectTableColumnDescriptorBasic<Institution>(ROLODEX_PACKAGE.getInstitution_MailingAddress(), "Mailing address", true, true),
      new EObjectTableColumnDescriptorBasic<Institution>(ROLODEX_PACKAGE.getPhoneNumberHolder_PhoneNumbers(), "Phone numbers", 150, true, true, new PhoneNumberListStringConverter())
      );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<Institution>> rowOperations = new HashMap<>() {
    {
    put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public InstitutionsTableDescriptor() {
    super("The are no institutions to show", null, columnDescriptors, rowOperations);
  }

}