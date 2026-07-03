package goedegep.demo.jfx.editor.step2.gui;

import java.util.Objects;

import goedegep.demo.jfx.editor.logic.CompanyService;
import goedegep.emfsample.model.Person;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanel;
import goedegep.jfx.editor.EditorTemplate;

/**
 * This class is the second step of an example editor (editing some fields of a {@link Person}).
 */
public class PersonEditor2 extends EditorTemplate<Person> {
  private static final String WINDOW_TITLE = "Person editor step 2";
  
  /**
   * Main {@code EditPanel}
   */
  private PersonEditPanel2 personEditPanel2;
  
  /**
   * Factory method to obtain a new instance of a {@code PersonEditor2}.
   * 
   * @param customization the GUI customization.
   * @param companyService the company 'database' service.
   * @return a newly created {@code PersonEditor2}.
   */
  public static PersonEditor2 newInstance(CustomizationFx customization, CompanyService companyService) {
    Objects.requireNonNull(companyService, "companyService may not be null");
    
    PersonEditor2 personEditor2 = new PersonEditor2(customization, companyService);
    personEditor2.performInitialization();
    personEditor2.newObject(false);
    
    return personEditor2;
  }
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param companyService the company 'database' service.
   */
  private PersonEditor2(CustomizationFx customization, CompanyService companyService) {
    super(customization, WINDOW_TITLE, companyService::addFormerEmployee);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureEditor() {
    setAddObjectTexts("Add person", "Add the person to the companies employees");
    setUpdateObjectTexts("Update person", "Update the current person");
    setNewObjectTexts("New", "Clear the controls to start entering a new person");
  }

  /**
   * {@inheritDoc}
   * 
   * In this case an CompanyEditPanel is created and returned.
   */
  @Override
  protected EditPanel<Person> getMainEditPanel() {
    personEditPanel2 = PersonEditPanel2.newInstance(customization);

    return personEditPanel2;
  }
}
