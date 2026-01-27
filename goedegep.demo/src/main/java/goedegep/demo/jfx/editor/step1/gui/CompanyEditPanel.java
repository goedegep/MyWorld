package goedegep.demo.jfx.editor.step1.gui;

import goedegep.demo.jfx.editor.logic.CompanyService;
import goedegep.emfsample.model.Company;
import goedegep.emfsample.model.EmfSampleFactory;
import goedegep.emfsample.model.EmfSamplePackage;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanelTemplate;
import goedegep.jfx.editor.EditorException;
import goedegep.jfx.editor.controls.EditorControlDate;
import goedegep.jfx.editor.controls.EditorControlString;
import goedegep.util.emf.EmfUtil;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * This class is the main edit panel for the {@link CompanyEditor} (step 1).
 */
public class CompanyEditPanel extends EditPanelTemplate<Company> {
  private static final EmfSampleFactory EMF_SAMPLE_FACTORY = EmfSampleFactory.eINSTANCE;
  private static final EmfSamplePackage EMF_SAMPLE_PACKAGE = EmfSamplePackage.eINSTANCE;
  
  /*
   * EditorControls
   */
  
  /**
   * Company name control
   */
  private EditorControlString companyNameControl;
  
  /**
   * Date of company establishment control
   */
  private EditorControlDate companyEstablismentDateControl;
  
  
  /**
   * Other GUI items
   */
  
  /**
   * Main edit panel
   */
  private VBox mainPane;
  
  
  /**
   * Other
   */
  
  /**
   * The company database service
   */
  @SuppressWarnings("unused")
  private CompanyService companyService;
  
  /**
   * Create an instance of the {@code CompanyEditPanel}.
   * 
   * @param customization the GUI customization.
   * @param companyService the company 'database' service.
   * @return a newly created {@code CompanyEditPanel}.
   */
  public static CompanyEditPanel newInstance(CustomizationFx customization, CompanyService companyService) {
    CompanyEditPanel companyEditPanel = new CompanyEditPanel(customization, companyService);
    companyEditPanel.performInitialization();
    
    return companyEditPanel;
  }
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param companyService the company 'database' service.
   */
  private CompanyEditPanel(CustomizationFx customization, CompanyService companyService) {
    super(customization, false);
    
    this.companyService = companyService;
    setId("companyEditPanel");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls() {
    
    companyNameControl = new EditorControlString.Builder("companyName")
        .setWidth(300d)
        .setLabelBaseText("Company _name")
        .setToolTipText("The company name is a mandatory field")
        .setErrorTextSupplier(() -> "The company name is not filled in")
        .build();
    
    companyEstablismentDateControl = new EditorControlDate.EditorControlDateBuilder("companyEstablismentDate")
        .setWidth(300d)
        .setLabelBaseText("Establisment _date")
        .setToolTipText("Optional date of when the company was established")
        .setOptional(true)
        .build();
    
    registerEditorComponents(companyNameControl, companyEstablismentDateControl);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getControl() {
    return mainPane;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void fillControlsFromObject() {
    if (value == null) {
      return;
    }
    companyNameControl.setObject(value.getCompanyName());
    companyEstablismentDateControl.setObject(value.getDateOfEstablishment());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillObjectFromControls(Company object, boolean getCurrentValue) throws EditorException {
    EmfUtil.setFeatureValue(object, EMF_SAMPLE_PACKAGE.getCompany_CompanyName(), companyNameControl.getValue());
    EmfUtil.setFeatureValue(object, EMF_SAMPLE_PACKAGE.getCompany_DateOfEstablishment(), companyEstablismentDateControl.getValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel() {
    mainPane = componentFactory.createVBox();
    mainPane.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    int row = 0;
    
    // First row: 'Company name: <company-name>'
    gridPane.addRow(row++, companyNameControl.getLabel(), companyNameControl.getControl(), companyNameControl.getStatusIndicator());
    
    // Second row: 'Company establisment date: <company-establisment-date>
    gridPane.addRow(row++, companyEstablismentDateControl.getLabel(), companyEstablismentDateControl.getControl(), companyEstablismentDateControl.getStatusIndicator());

    mainPane.getChildren().add(gridPane);
    
    
    mainPane.setMinHeight(100);
    mainPane.setPrefHeight(100);
    mainPane.setMinWidth(500);
    mainPane.setPrefWidth(500);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setErrorFeedback(boolean valid) {
    // No action at this level.
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Company createObject() {
    return EMF_SAMPLE_FACTORY.createCompany();
  }

  @Override
  public String getValueAsFormattedText() {
    return null;
  }
  
}
