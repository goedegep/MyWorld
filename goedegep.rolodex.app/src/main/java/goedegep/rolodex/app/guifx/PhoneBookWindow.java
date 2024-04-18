package goedegep.rolodex.app.guifx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import goedegep.appgen.Operation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.rolodex.model.Employee;
import goedegep.rolodex.model.Family;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.PhoneNumberHolder;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexPackage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;

/**
 * This class provides a phone book like window.
 * <p>
 * The window shows a PhoneNumbers table.<br/>
 */
public class PhoneBookWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PhoneBookWindow.class.getName());
  private static final String WINDOW_TITLE   = "Phone book";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<PhoneNumberHolder> phoneNumbersTable;
  
  private EList<PhoneNumberHolder> phoneNumberHolders;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public PhoneBookWindow(CustomizationFx customization, Rolodex rolodex) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    this.rolodex = rolodex;
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>(){

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        phoneNumbersTable.setFilterExpression(newValue, null);
      }
      
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
        
    setScene(new Scene(rootLayout, 1300, 700));
  }
  
  /**
   * Create the phoneNumbersTable.
   * 
   * @return the created phoneNumbersTable
   */
  private EObjectTable<PhoneNumberHolder> createPhoneNumbersTable() {
    phoneNumberHolders = new BasicEList<>();
    
    // Add families
    for (Family family: rolodex.getFamilyList().getFamilies()) {
      if (!family.getPhoneNumbers().isEmpty()) {
        phoneNumberHolders.add(family);
      }
    }
    
    // Add persons
    for (Person person: rolodex.getPersonList().getPersons()) {
      if (!person.getPhoneNumbers().isEmpty()) {
        phoneNumberHolders.add(person);
      }
    }
    
    // Add institutions
    for (Institution institution: rolodex.getInstitutionList().getInstitutions()) {
      if (!institution.getPhoneNumbers().isEmpty()) {
        phoneNumberHolders.add(institution);
      }
    }
    
    // Add employees
    for (Employee employee: rolodex.getEmployeeList().getEmployees()) {
      if (!employee.getPhoneNumbers().isEmpty()) {
        phoneNumberHolders.add(employee);
      }
    }
    
    phoneNumbersTable = new EObjectTable<PhoneNumberHolder>(customization, ROLODEX_PACKAGE.getPhoneNumber(), new PhoneBookTableDescriptor(), phoneNumberHolders);
        
    return phoneNumbersTable;
  }

}


/**
 * This class provides the descriptor for the phone numbers table.
 */
class PhoneBookTableDescriptor extends EObjectTableDescriptor<PhoneNumberHolder> {
  private static List<EObjectTableColumnDescriptorAbstract<PhoneNumberHolder>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<PhoneNumberHolder>>of(
      new EObjectTableColumnDescriptorCustom<PhoneNumberHolder>(null, "Name", null, true, true, column -> {
        TableCell<PhoneNumberHolder, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {            
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
              setGraphic(null);
            }
            else {
              setText(null);
              setGraphic(null);
              String name;
              if (item instanceof Family) {
                Family family = (Family) item;
                name = family.getFamilyTitle() + " " + family.getFamilyName();
              } else if (item instanceof Person) {
                Person person = (Person) item;
                name = person.getName();
              } else if (item instanceof Employee) {
                Employee employee = (Employee) item;
                name = employee.getPerson().getName() + " at " + employee.getInstitution().getName();
              } else {
                Institution institution = (Institution) item;
                name = institution.getName();
              }
              setText(name);
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<PhoneNumberHolder>(null, "Phone numbers", null, true, true, column -> {
        TableCell<PhoneNumberHolder, Object> cell = new TableCell<>() {

          @Override
          protected void updateItem(Object item, boolean empty) {            
            super.updateItem(item, empty);
            if(empty || (item == null)) {
              setText(null);
              setGraphic(null);
            }
            else {
              setText(null);
              setGraphic(null);
              PhoneNumberHolder phoneNumberHolder = (PhoneNumberHolder) item;
              List<PhoneNumber> phoneNumbers = phoneNumberHolder.getPhoneNumbers();
              boolean separatorNeeded = false;
              StringBuilder buf = new StringBuilder();
              for (PhoneNumber phoneNumber: phoneNumbers) {
                if (separatorNeeded) {
                  buf.append(", ");
                }
                buf.append(phoneNumber.toString());
                separatorNeeded = true;
              }
              setText(buf.toString());
            }
          }
        };

        return cell;
      })
      );
  
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<PhoneNumberHolder>> rowOperations = new HashMap<>() {
    {
    put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<>("Delete"));
    }
  };
  
  public PhoneBookTableDescriptor() {
    super("The are no phone numbers to show", null, columnDescriptors, rowOperations);
  }

}
