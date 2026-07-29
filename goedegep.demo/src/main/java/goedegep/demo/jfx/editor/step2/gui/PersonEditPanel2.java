package goedegep.demo.jfx.editor.step2.gui;

import goedegep.emfsample.model.Birthday;
import goedegep.emfsample.model.EmfSampleFactory;
import goedegep.emfsample.model.EmfSamplePackage;
import goedegep.emfsample.model.Person;
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
 * This class is the main edit panel for the {@link PersonEditor} (step 1).
 */
public class PersonEditPanel2 extends EditPanelTemplate<Person> {
  private static final EmfSampleFactory EMF_SAMPLE_FACTORY = EmfSampleFactory.eINSTANCE;
  private static final EmfSamplePackage EMF_SAMPLE_PACKAGE = EmfSamplePackage.eINSTANCE;
  
  /*
   * EditorControls
   */
  
  /**
   * Surname control
   */
  private EditorControlString surnameControl;
  
  /**
   * Retirement date control
   */
  private EditorControlDate retirementDateControl;
  
  /**
   * Birthday edit panel
   */
  private BirthdayEditPanel birthdayEditPanel;
  
  
  /**
   * Other GUI items
   */
  
  /**
   * Main edit panel
   */
  private VBox mainPane;
  
  
  
  /**
   * Create an instance of the {@code PersonEditPanel}.
   * 
   * @param customization the GUI customization.
   * @return a newly created {@code PersonEditPanel}.
   */
  public static PersonEditPanel2 newInstance(CustomizationFx customization) {
    PersonEditPanel2 personEditPanel = new PersonEditPanel2(customization);
    personEditPanel.performInitialization();
    
    return personEditPanel;
  }
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  private PersonEditPanel2(CustomizationFx customization) {
    super(customization, "personEditPanel", false);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls() {
    
    surnameControl = new EditorControlString.Builder("surname")
        .setWidth(300d)
        .setLabelBaseText("_Surname")
        .setToolTipText("The surname name is a mandatory field")
        .setErrorTextSupplier(() -> "The surname is not filled in")
        .build();
    
    retirementDateControl = new EditorControlDate.Builder("retirementDate")
        .setWidth(300d)
        .setLabelBaseText("_Retirement date")
        .setToolTipText("Optional date of retirement")
        .setOptional(true)
        .build();
    
    birthdayEditPanel = BirthdayEditPanel.newInstance(customization, "birthdayEditPanel");
    
    registerEditorComponents(surnameControl, retirementDateControl, birthdayEditPanel);
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
    surnameControl.setValue(value.getSurname());
    retirementDateControl.setValue(value.getRetirementDate());
    birthdayEditPanel.setObject(value.getBirthday());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillObjectFromControls(Person object, boolean getCurrentValue) throws EditorException {
    EmfUtil.setFeatureValue(object, EMF_SAMPLE_PACKAGE.getPerson_Surname(), surnameControl.getValue());
    EmfUtil.setFeatureValue(object, EMF_SAMPLE_PACKAGE.getPerson_RetirementDate(), retirementDateControl.getValue());
    
    acceptSubClass(birthdayEditPanel, object.getBirthday(), this::setBirthday);
//    birthdayEditPanel.fillObjectFromControls(object.getBirthday(), getCurrentValue);
  }
  
  private void setBirthday(Object object) {
    if (object instanceof Birthday birthday) {
      EmfUtil.setFeatureValue(value, EMF_SAMPLE_PACKAGE.getPerson_Birthday(), birthday);
    } 
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
    
    // First row: 'Surname: <surname>'
    gridPane.addRow(row++, surnameControl.getLabel(), surnameControl.getControl(), surnameControl.getStatusIndicator());
    
    // Second row: 'Retirement date: <retirement date>'
    gridPane.addRow(row++, retirementDateControl.getLabel(), retirementDateControl.getControl(), retirementDateControl.getStatusIndicator());

    mainPane.getChildren().add(gridPane);
    
    mainPane.getChildren().add(birthdayEditPanel.getControl());
    
    mainPane.setMinHeight(200);
    mainPane.setPrefHeight(200);
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
  protected Person createObject() {
    return EMF_SAMPLE_FACTORY.createPerson();
  }

  @Override
  public String getValueAsFormattedText() {
    return null;
  }
  
}
