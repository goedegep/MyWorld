package goedegep.demo.jfx.eobjecttreeview.guifx;

import java.util.logging.Logger;

import goedegep.emfsample.model.Birthday;
import goedegep.emfsample.model.Company;
import goedegep.emfsample.model.EmfSampleFactory;
import goedegep.emfsample.model.EmfSamplePackage;
import goedegep.emfsample.model.Gender;
import goedegep.emfsample.model.Person;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.eobjecttreeview.EObjectTreeView;
import goedegep.util.datetime.DateUtil;
import goedegep.util.emf.EMFResource;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EObjectTreeViewDemo extends JfxStage {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(EObjectTreeViewDemo.class.getName());

  private final static EmfSampleFactory EMF_SAMPLE_FACTORY = EmfSampleFactory.eINSTANCE;
    
  private ComponentFactoryFx componentFactory;
  
  /**
   * A dummy {@link Company}. The data model represented by the tree view.
   */
  private Company company;
  
  
  /**
   * Constructor
   * <p>
   * This does the following:
   * Create initial information for the {@link #company} (see method {@link #createCompany}).
   * @param customization
   */
  public EObjectTreeViewDemo(CustomizationFx customization) {
    super("EObjectTreeView demo", customization);
    
    componentFactory = customization.getComponentFactoryFx();
    
    company = createCompany();
        
    createGUI();
    
    show();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    VBox vBox = componentFactory.createVBox();
    
    vBox.getChildren().add(createMenuBar());
    
    HBox treesHBox = componentFactory.createHBox();
//    EObjectTreeView defaultTreeView = new EObjectTreeView(company, true);
//    defaultTreeView.setMinWidth(400);
//    defaultTreeView.setMinHeight(700);
//    treesHBox.getChildren().add(defaultTreeView);
    
    EObjectTreeView descriptorBasedTreeView = new EObjectTreeView(company, CompanyTreeViewDescriptorFactory.createDescriptor(), true);
    descriptorBasedTreeView.setMinWidth(400);
    descriptorBasedTreeView.setMinHeight(700);
    treesHBox.getChildren().add(descriptorBasedTreeView);
    
    vBox.getChildren().add(treesHBox);

    setScene(new Scene(vBox));
  }
  
  /**
   * Create the menu bar.
   * 
   * @return the created menu bar.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu menu;
    MenuItem menuItem;

    // Action menu
    menu = new Menu("Action");

    // Action: Add former employee
    menuItem = componentFactory.createMenuItem("Add former employee");
    menuItem.setOnAction(event -> addFormerEmployee());
    menu.getItems().add(menuItem);

    // Action: Employee to former employees
    menuItem = componentFactory.createMenuItem("Employee to former employees");
    menuItem.setOnAction(event -> employeeToFormerEmployees());
    menu.getItems().add(menuItem);

    menuBar.getMenus().add(menu);
    
    return menuBar;
  }

  /**
   * Create a new employee and add this to the companies employees list.
   */
  private void addFormerEmployee() {
    Person person;
    Birthday birthday;
    
    person = EMF_SAMPLE_FACTORY.createPerson();
    birthday = EMF_SAMPLE_FACTORY.createBirthday();
    birthday.setDay(16);
    birthday.setMonth(6);
    birthday.setYear(1991);
    person.setBirthday(birthday);
    person.getFirstnames().add("Serena");
    person.setSurname("Donahue");
    person.setRetirementDate(DateUtil.createDate(1, birthday.getMonth(), birthday.getYear() + 64));
    person.setGender(Gender.FEMALE);
    person.setHasChildren(true);
    company.getFormerEmployees().add(person);
  }
  
  /**
   * Move an employee from the list of employees to the list of former employees.
   */
  private void employeeToFormerEmployees() {
    if (company.getEmployees().size() >= 2) {
      Person person = company.getEmployees().remove(1);
      company.getFormerEmployees().add(person);
    }
  }

  /**
   * Create the initial company information.
   * <p>
   * The {@code Company} is created by using an {@link EMFResource}.
   * 
   * @return a {@code Company}; the newly created company information.
   */
  private Company createCompany() {
    EMFResource<Company> emfResource = new EMFResource<>(EmfSamplePackage.eINSTANCE, () -> EMF_SAMPLE_FACTORY.createCompany(), ".xmi");
    Company company = emfResource.newEObject();
    
    Person person;
    Birthday birthday;
    
    person = EMF_SAMPLE_FACTORY.createPerson();
    birthday = EMF_SAMPLE_FACTORY.createBirthday();
    birthday.setDay(12);
    birthday.setMonth(4);
    birthday.setYear(1987);
    person.setBirthday(birthday);
    person.getFirstnames().add("John");
    person.setSurname("Williams");
    person.setRetirementDate(DateUtil.createDate(1, birthday.getMonth(), birthday.getYear() + 67));
    person.setGender(Gender.MALE);
    person.setHasChildren(true);
    company.getEmployees().add(person);
    company.getBirthdays().add(birthday);
    
    person = EMF_SAMPLE_FACTORY.createPerson();
//    birthday = EMF_SAMPLE_FACTORY.createBirthday();
//    birthday.setDay(23);
//    birthday.setMonth(8);
//    birthday.setYear(1966);
//    person.setBirthday(birthday);
    person.getFirstnames().add("Eliza");
    person.getFirstnames().add("Marie");
    person.setSurname("Jones");
    person.setRetirementDate(DateUtil.createDate(1, birthday.getMonth(), birthday.getYear() + 67));
    person.setGender(Gender.FEMALE);
    person.setHasChildren(true);
    company.getEmployees().add(person);
    company.getBirthdays().add(birthday);
    
    person = EMF_SAMPLE_FACTORY.createPerson();
    birthday = EMF_SAMPLE_FACTORY.createBirthday();
    birthday.setDay(1);
    birthday.setMonth(12);
    birthday.setYear(2001);
    person.setBirthday(birthday);
    person.getFirstnames().add("Jim");
    person.setSurname("Dales");
    person.setRetirementDate(DateUtil.createDate(1, birthday.getMonth(), birthday.getYear() + 67));
    person.setGender(Gender.MALE);
    person.setHasChildren(false);
    company.getEmployees().add(person);
    company.getBirthdays().add(birthday);
    
    return company;
  }
}
