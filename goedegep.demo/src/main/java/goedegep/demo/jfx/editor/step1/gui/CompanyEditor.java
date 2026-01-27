package goedegep.demo.jfx.editor.step1.gui;

import java.util.Objects;

import goedegep.demo.jfx.editor.logic.CompanyService;
import goedegep.emfsample.model.Company;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanel;
import goedegep.jfx.editor.EditorTemplate;

/**
 * This class is the first step of an editor for a {@link Company}.
 */
public class CompanyEditor extends EditorTemplate<Company> {
  private static final String WINDOW_TITLE = "Company editor";

  private CompanyService companyService;
  
  /**
   * Main {@code EditPanel}
   */
  private CompanyEditPanel companyEditPanel;
  
  /**
   * Factory method to abtain a new instance of an {@code CompanyEditor}.
   * 
   * @param customization the GUI customization.
   * @param companyService the company 'database' service.
   * @return a newly created {@code CompanyEditor}.
   */
  public static CompanyEditor newInstance(CustomizationFx customization, CompanyService companyService) {
    Objects.requireNonNull(companyService, "companyService may not be null");
    
    CompanyEditor companyEditor = new CompanyEditor(customization, companyService);
    companyEditor.performInitialization();
    
    return companyEditor;
  }
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param companyService the company 'database' service.
   */
  private CompanyEditor(CustomizationFx customization, CompanyService companyService) {
    super(customization, WINDOW_TITLE, companyService::addCompany);
    
    this.companyService = companyService;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureEditor() {
    setAddObjectTexts("Add company", "Add the company to the companies database");
    setUpdateObjectTexts("Update company", "Update the current company");
    setNewObjectTexts("New", "Clear the controls to start entering new company data");
  }

  /**
   * {@inheritDoc}
   * 
   * In this case an CompanyEditPanel is created and returned.
   */
  @Override
  protected EditPanel<Company> getMainEditPanel() {
    companyEditPanel = CompanyEditPanel.newInstance(customization, companyService);

    return companyEditPanel;
  }
}
