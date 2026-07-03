package goedegep.demo.jfx.editor.step1.gui;

import java.util.Objects;

import goedegep.demo.jfx.editor.logic.CompanyService;
import goedegep.emfsample.model.Person;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanel;
import goedegep.jfx.editor.EditorTemplate;

/**
 * This class is the first step of an example editor (editing some fields of a {@link Person}).
 */
public class PersonEditor extends EditorTemplate<Person> {
  private static final String WINDOW_TITLE = "Person editor";
  
  /**
   * Main {@code EditPanel}
   */
  private PersonEditPanel personEditPanel;
  
  /**
   * Factory method to obtain a new instance of a {@code PersonEditor}.
   * 
   * @param customization the GUI customization.
   * @param companyService the company 'database' service.
   * @return a newly created {@code PersonEditor}.
   */
  public static PersonEditor newInstance(CustomizationFx customization, CompanyService companyService) {
    Objects.requireNonNull(companyService, "companyService may not be null");
    
    PersonEditor personEditor = new PersonEditor(customization, companyService);
    personEditor.performInitialization();
    
    return personEditor;
  }
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param companyService the company 'database' service.
   */
  private PersonEditor(CustomizationFx customization, CompanyService companyService) {
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
    personEditPanel = PersonEditPanel.newInstance(customization);

    return personEditPanel;
  }
}
