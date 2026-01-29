package goedegep.rolodex.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.Operation;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorAbstract;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.eobjecttable.TableRowOperationDescriptor;
import goedegep.rolodex.logic.PersonsComparator;
import goedegep.rolodex.model.Family;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexPackage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FamilyMembersPanel extends VBox {
  
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;

  private CustomizationFx customization;
  private Rolodex rolodex;
  private ComponentFactoryFx componentFactory;
  
  private EObjectTable<Person> basicPersonsTable;
  private Family family;
  
  public FamilyMembersPanel(CustomizationFx customization, Rolodex rolodex) {
    this.customization = customization;
    this.rolodex = rolodex;

    componentFactory = customization.getComponentFactoryFx();

    basicPersonsTable = new EObjectTable<Person>(customization, ROLODEX_PACKAGE.getPerson(), new BasicPersonsTableDescriptor());

    createGUI();
  }
  
  private void createGUI() {
    setSpacing(6.0);
    setPadding(new Insets(12.0));
    
    Label label = componentFactory.createStrongLabel("Family members");
    getChildren().add(label);   
    
    getChildren().add(basicPersonsTable);
    
    HBox addMemberHBox = componentFactory.createHBox(12.0);
    label = componentFactory.createLabel("Add a member to the family:");
    addMemberHBox.getChildren().add(label);
    PersonTextField personTextField = new PersonTextField(customization, rolodex);
    addMemberHBox.getChildren().add(personTextField.getControl());
    Button addMemberButton = componentFactory.createButton("Add family member", "Add the selected person as member to the family");
    addMemberButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        if (family == null) {
          return;
        }
        
        List<Person> matchingPersons = personTextField.getMatchingPersons();
        if (matchingPersons.size() == 1) {
          family.getMembers().add(matchingPersons.get(0));
        }
      }
      
    });
    addMemberHBox.getChildren().add(addMemberButton);
    getChildren().add(addMemberHBox);
    
  }

  public void setFamily(Family family) {
    this.family = family;
    basicPersonsTable.setObjects(family, RolodexPackage.eINSTANCE.getFamily_Members());
  }
  
  protected static void removePersonFromFamily(List<Person> familyMembers, Person member) {
    familyMembers.remove(member);
  }
}



/**
 * This class provides the descriptor for the Persons Table.
 */
class BasicPersonsTableDescriptor extends EObjectTableDescriptor<Person> {
  private static RolodexPackage ROLODEX_PACKAGE = RolodexPackage.eINSTANCE;
  
  private static List<EObjectTableColumnDescriptorAbstract<Person>> columnDescriptors = List.<EObjectTableColumnDescriptorAbstract<Person>>of(
      new EObjectTableColumnDescriptorBasic<Person>(ROLODEX_PACKAGE.getPerson_Firstname(), "First name", true, true),
      new EObjectTableColumnDescriptorBasic<Person>(ROLODEX_PACKAGE.getPerson_Infix(), "Infix", true, true),
      new EObjectTableColumnDescriptorBasic<Person>(ROLODEX_PACKAGE.getPerson_Surname(), "Surname", true, true)
      );
    
  @SuppressWarnings("serial")
  private static Map<Operation, TableRowOperationDescriptor<Person>> rowOperations = new HashMap<>() {
    {
      put(Operation.EXTENDED_OPERATION, new TableRowOperationDescriptor<>("Remove person from family", (BiConsumer<List<Person>, Person>) FamilyMembersPanel::removePersonFromFamily));
    }
  };
  
  public BasicPersonsTableDescriptor() {
    super("There are no family members to show", PersonsComparator.getInstance(), columnDescriptors, rowOperations);    
  }
}
