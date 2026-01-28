package goedegep.rolodex.app.guifx;

import java.time.Month;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableControlPanel;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.rolodex.app.logic.BirthdaysComparator;
import goedegep.rolodex.model.Birthday;
import goedegep.rolodex.model.Family;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexPackage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.layout.BorderPane;

/**
 * This class provides a window with a phone numbers table.
 */
public class BirthdaysWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(BirthdaysWindow.class.getName());
  private static final String WINDOW_TITLE   = "Birthdays";
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private Rolodex rolodex;
  private EObjectTableControlPanel eObjectTableControlPanel;
  private EObjectTable<Person> birthdaysTable;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param rolodex a <code>Rolodex</code>
   */
  public BirthdaysWindow(CustomizationFx customization, Rolodex rolodex) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    this.rolodex = rolodex;
    
    createGUI();
    
    eObjectTableControlPanel.filterTextProperty().addListener(new ChangeListener<String>(){

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        birthdaysTable.setFilterExpression(newValue, null);
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
   * <li>The birthdays table</li>
   * </ul>
   */
  private void createGUI() {
    BorderPane rootLayout = new BorderPane();
    
    eObjectTableControlPanel = new EObjectTableControlPanel(customization);
    rootLayout.setTop(eObjectTableControlPanel);
    rootLayout.setCenter(createBirthdaysTable());
        
    setScene(new Scene(rootLayout, 1300, 700));
  }
  
  /**
   * Create the birthdaysTable.
   * 
   * @return the created birthdaysTable
   */
  private EObjectTable<Person> createBirthdaysTable() {
    birthdaysTable = new EObjectTable<Person>(customization, ROLODEX_PACKAGE.getPerson(), new BirthdaysTableDescriptor(), rolodex.getPersonList(), RolodexPackage.eINSTANCE.getPersonList_Persons());
        
    return birthdaysTable;
  }
    
}


/**
 * This class provides the descriptor for the Cities Table.
 */
class BirthdaysTableDescriptor extends EObjectTableDescriptor<Person> {
  
  private static List<EObjectTableColumnDescriptorAbstract<Person>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Person>>of(
      new EObjectTableColumnDescriptorCustom<Person>(null, "Name", null, true, true, _ -> {
        TableCell<Person, Object> cell = new TableCell<>() {

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
              Person person = (Person) item;
              if (person.isSetSurname()) {
                setText(person.getName());
              } else {
                StringBuilder buf = new StringBuilder();
                boolean spaceNeeded = false;
                if (person.isSetFirstname()) {
                  buf.append(person.getFirstname());
                  spaceNeeded = true;
                }
                Family family = person.getFamily();
                if (family != null) {
                  String familyName = family.getFamilyName();
                  if (familyName != null) {
                    if (spaceNeeded) {
                      buf.append(" ");
                    }
                    buf.append("*" + familyName + "*");
                  }
                }
                setText(buf.toString());
              }
            }
          }
        };

        return cell;
      }),
      new EObjectTableColumnDescriptorCustom<Person>(null, "Birthday", null, true, true, _ -> {
        TableCell<Person, Object> cell = new TableCell<>() {

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
              Person person = (Person) item;
              Birthday birthday = person.getBirthday();
              if (birthday != null) {                
                setText(birthday.getDay() + " " + Month.of(birthday.getMonth()).name().toLowerCase());
              }
            }
          }
        };

        return cell;
      })
      );
  
  public BirthdaysTableDescriptor() {
    super("The are no birthdays to show", BirthdaysComparator.getInstance(), columnDescriptors, null);
    
    setFilterPredicate(new Predicate<Person>() {

      @Override
      public boolean test(Person person) {
         if (person.getBirthday() != null) {
          return true;
        } else {
          return false;
        }
      }
      
    });
  }

}