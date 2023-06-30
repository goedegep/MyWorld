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

public class EObjectTreeViewDemo extends JfxStage {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(EObjectTreeViewDemo.class.getName());
  
  private ComponentFactoryFx componentFactory;
  
  private Company company;
  
  
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
//    HBox hBox = componentFactory.createHBox();
    
    EObjectTreeView treeView = new EObjectTreeView(company, true);

    setScene(new Scene(treeView));
  }
  
  private Company createCompany() {
    EmfSampleFactory factory = EmfSampleFactory.eINSTANCE;
    
//    Company company = factory.createCompany();
    
    EMFResource<Company> emfResource = new EMFResource<>(EmfSamplePackage.eINSTANCE, () -> factory.createCompany(), ".xmi");
    Company company = emfResource.newEObject();
    
    Person person;
    Birthday birthday;
    
    person = factory.createPerson();
    birthday = factory.createBirthday();
    birthday.setDay(12);
    birthday.setMonth(4);
    birthday.setYear(1987);
    person.setBirthday(birthday);
    person.setFirstname("John");
    person.setSurname("Williams");
    person.setRetirementDate(DateUtil.createDate(1, birthday.getMonth(), birthday.getYear() + 67));
    person.setGender(Gender.MALE);
    person.setHasChildren(true);
    company.getEmployees().add(person);
    company.getBirthdays().add(birthday);
    
    person = factory.createPerson();
    birthday = factory.createBirthday();
    birthday.setDay(23);
    birthday.setMonth(8);
    birthday.setYear(1966);
    person.setBirthday(birthday);
    person.setFirstname("Eliza");
    person.setSurname("Jones");
    person.setRetirementDate(DateUtil.createDate(1, birthday.getMonth(), birthday.getYear() + 67));
    person.setGender(Gender.FEMALE);
    person.setHasChildren(true);
    company.getEmployees().add(person);
    company.getBirthdays().add(birthday);
    
    person = factory.createPerson();
    birthday = factory.createBirthday();
    birthday.setDay(1);
    birthday.setMonth(12);
    birthday.setYear(2001);
    person.setBirthday(birthday);
    person.setFirstname("Jim");
    person.setSurname("Dales");
    person.setRetirementDate(DateUtil.createDate(1, birthday.getMonth(), birthday.getYear() + 67));
    person.setGender(Gender.MALE);
    person.setHasChildren(false);
    company.getEmployees().add(person);
    company.getBirthdays().add(birthday);
    
    return company;
  }
}
