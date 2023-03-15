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
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorChoiceBox;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.objectcontrols.ObjectControl;
import goedegep.rolodex.app.logic.InstitutionStringConverter;
import goedegep.rolodex.app.logic.PersonStringConverter;
import goedegep.rolodex.app.logic.PhoneNumberListStringConverter;
import goedegep.rolodex.model.Employee;
import goedegep.rolodex.model.Institution;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class provides a window with information on Employees.
 * <p>
 * The top part of the window shows an editable Employees table.<br/>
 * The bottom part shows all details of an Employee. The information here can be edited to update the Employee information, or to add a new Employee.
 */
public class EmployeesWindowFx extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(EmployeesWindowFx.class.getName());
  private static final String WINDOW_TITLE   = "Employees";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<Employee> employeesTable;
  private ReferredByPanel referredByPanel;
  private EmployeeEditPanel employeeEditPanel;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public EmployeesWindowFx(CustomizationFx customization, Rolodex rolodex) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    this.rolodex = rolodex;
    componentFactory = customization.getComponentFactoryFx();

    referredByPanel = new ReferredByPanel(customization, "Referred by");
    employeeEditPanel = new EmployeeEditPanel(customization, rolodex);
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>(){

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        employeesTable.setFilterExpression(newValue, null);
      }
      
    });
    
    employeesTable.addObjectSelectionListener((source, employee) -> {
        referredByPanel.setObject(employee);
        employeeEditPanel.setEmployee(employee);
    });
    
    show();
  }
  
  /**
   * Create the GUI.
   * <p>
   * The window consists of the following parts:
   * <ul>
   * <li>Controls which operate on the table.</li>
   * <li>The employees table</li>
   * <li>A panel to add a new person</li>
   * </ul>
   */
  private void createGUI() {
    VBox rootLayout = componentFactory.createVBox();
    
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    rootLayout.getChildren().add(eObjectTableControlPanel);
    rootLayout.getChildren().add(createEmployeesTable());
    rootLayout.getChildren().add(referredByPanel.getPanel());
    rootLayout.getChildren().add(employeeEditPanel.getEditPanel());
        
    setScene(new Scene(rootLayout, 1300, 700));
  }
  
  /**
   * Create the employeesTable.
   * 
   * @return the created employeesTable
   */
  private EObjectTable<Employee> createEmployeesTable() {
    employeesTable = new EObjectTable<Employee>(customization, ROLODEX_PACKAGE.getEmployee(), new EmployeesTableDescriptor(rolodex), rolodex.getEmployeeList(), RolodexPackage.eINSTANCE.getEmployeeList_Employees());
        
    return employeesTable;
  }
    
}


/**
 * This class provides a panel with controls to edit the properties of an Employee.<br/>
 * There are buttons for updating the values of an Employee, or to add a new Employee.
 */
class EmployeeEditPanel {
  private static RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;
  
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private GridPane gridPane;
  
  // Controls
  private PersonTextField personTextField;
  private InstitutionTextField institutionTextField;
  private PhoneNumberTextField phoneNumberTextFields[];
  private SimpleObjectProperty<Employee> employeeProperty = new SimpleObjectProperty<>();
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public EmployeeEditPanel(CustomizationFx customization, Rolodex rolodex) {
    this.rolodex = rolodex;
    
    componentFactory = customization.getComponentFactoryFx();
    
    // Create the controls.
    personTextField = new PersonTextField(customization, rolodex);
    institutionTextField = new InstitutionTextField(customization, rolodex);
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
    
    Label label = componentFactory.createStrongLabel("Add/Edit an employee");
    gridPane.add(label, 0, row, 2, 1);

    HBox buttonsBox = componentFactory.createHBox(12.0);
    Button addButton = componentFactory.createButton("Add employee", "Add the employee to the Rolodex");
    addButton.setOnAction((e) -> {
      Employee newEmployee = createEmployeeFromFields();
      if (newEmployee != null) {
        rolodex.getEmployeeList().getEmployees().add(newEmployee);
      }
     
    });
    buttonsBox.getChildren().add(addButton);
    
    Button updateButton = componentFactory.createButton("Update employee", "Update the employee in the Rolodex");
    employeeProperty.addListener(new ChangeListener<Employee>() {

      @Override
      public void changed(ObservableValue<? extends Employee> observable, Employee oldValue, Employee newValue) {
        updateButton.setDisable(newValue == null);
      }
    });
    updateButton.setDisable(employeeProperty.getValue() == null);

    updateButton.setOnAction((e) -> {
      updateEmployeeIfControlsAreValid();
    });
    buttonsBox.getChildren().add(updateButton);
    gridPane.add(buttonsBox, 5, row);
    
    row++;
    
    label = componentFactory.createLabel("Person:");
    gridPane.add(label, 0, row);

    gridPane.add(personTextField, 1, row);
    
    row++;
    
    label = componentFactory.createLabel("Institution:");
    gridPane.add(label, 0, row);

    gridPane.add(institutionTextField, 1, row);
    
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
   * Set the employee for which information will be shown and which can be updated.
   * 
   * @param employee the Employee for which information will be shown and which can be updated.
   */
  public void setEmployee(Employee employee) {
    employeeProperty.setValue(employee);
    fillFieldsFromEmployee(employee);
  }
  
  /**
   * Update the current employee, if the controls all have valid values.
   */
  private void updateEmployeeIfControlsAreValid() {
    
    // Only update if all controls have valid values.
    if (!ObjectControl.areControlsValid(personTextField, institutionTextField,
        phoneNumberTextFields[0], phoneNumberTextFields[1], phoneNumberTextFields[2], phoneNumberTextFields[3])) {
      return;
    }
        
    updateEmployeeFromFields(employeeProperty.get());
  }
  
  /**
   * Fill the controls with the values of a specific Employee.
   * 
   * @param employee the Employee who's values will be applied to the controls.
   */
  private void fillFieldsFromEmployee(Employee employee) {
    clearFields();
    
    if (employee == null) {
      return;
    }
    
    personTextField.setText(employee.getPerson().getName());
    institutionTextField.setText(employee.getInstitution().getName());
    
    List<PhoneNumber> phoneNumbers = employee.getPhoneNumbers();
    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      if (phoneNumbers.size() > i) {
        phoneNumberTextFields[i].setText(phoneNumbers.get(i).toString());
      } else {
        phoneNumberTextFields[i].setText(null);
      }
    }
  }
  
  private void clearFields() {
    personTextField.setText(null);
    institutionTextField.setText(null);

    for (int i = 0; i < phoneNumberTextFields.length; i++) {
      phoneNumberTextFields[i].setText(null);
    }
  }
  
  /**
   * Create an Employee from the values of the controls.
   * <p>
   * The Employee will only be created if the controls all have valid values.
   * 
   * @return the newly created Employee, or null, if any control has an invalid value.
   */
  private Employee createEmployeeFromFields() {
    
    // Only create if all controls have valid values.
    if (!ObjectControl.areControlsValid(personTextField, institutionTextField,
        phoneNumberTextFields[0], phoneNumberTextFields[1], phoneNumberTextFields[2], phoneNumberTextFields[3])) {
      return null;
    }
    
    Employee newEmployee = ROLODEX_FACTORY.createEmployee();
    
    updateEmployeeFromFields(newEmployee);
    
    return newEmployee;
  }
  
  /**
   * Update an Employee with the values of the controls.
   * <p>
   * Values are only set, if the new value differs from the current value. This to avoid unnecessary updates of listeners to this object.
   * 
   * @param employee The Employee object to be updated.
   */
  public boolean updateEmployeeFromFields(Employee employee) {
      List<Person> persons = personTextField.getMatchingPersons();
      if (persons.size() == 1) {
        Person person = persons.get(0);
        if (!PgUtilities.equals(employee.getPerson(), person)) {
          employee.setPerson(person);
        }
      }
      
      List<Institution> institutions = institutionTextField.getMatchingInstitutions();
      if (institutions.size() == 1) {
        Institution institution = institutions.get(0);
        if (!PgUtilities.equals(employee.getInstitution(), institution)) {
          employee.setInstitution(institution);
        }
      }
      
      for (int i = 0; i < phoneNumberTextFields.length; i++) {
        PhoneNumber phoneNumber = phoneNumberTextFields[i].getMatchingPhoneNumber();
        if (phoneNumber != null) {
          employee.getPhoneNumbers().add(phoneNumber);
        }
      }
        
    return true;
  }
    
}



/**
 * This class provides the descriptor for the Cities Table.
 */
class EmployeesTableDescriptor extends EObjectTableDescriptor<Employee> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  @SuppressWarnings("serial")
  private static Map<TableRowOperation, TableRowOperationDescriptor<Employee>> rowOperations = new HashMap<>() {
    {
    put(TableRowOperation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public EmployeesTableDescriptor(Rolodex rolodex) {
    super("The are no employees to show", null, null, rowOperations);

    List<EObjectTableColumnDescriptorAbstract<Employee>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Employee>>of(
        new EObjectTableColumnDescriptorChoiceBox<Employee>(ROLODEX_PACKAGE.getEmployee_Person(), "Employee name", 400, true, true, FXCollections.observableArrayList(rolodex.getPersonList().getPersons()), new PersonStringConverter()),
        new EObjectTableColumnDescriptorChoiceBox<Employee>(ROLODEX_PACKAGE.getEmployee_Institution(), "Name of the institution", 400, true, true, FXCollections.observableArrayList(rolodex.getInstitutionList().getInstitutions()), new InstitutionStringConverter(rolodex)),
        new EObjectTableColumnDescriptorBasic<Employee>(ROLODEX_PACKAGE.getPhoneNumberHolder_PhoneNumbers(), "Phone numbers", 400, true, true, new PhoneNumberListStringConverter())
        );

    setColumnDescriptors(columnDescriptors);
  }

}