package goedegep.demo.jfx.editor.step2.gui;

import goedegep.emfsample.model.Birthday;
import goedegep.emfsample.model.EmfSampleFactory;
import goedegep.emfsample.model.EmfSamplePackage;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanelTemplate;
import goedegep.jfx.editor.EditorException;
import goedegep.jfx.editor.controls.EditorControlInteger;
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
 * This class is an edit panel for a {@link Birthday}.
 */
public class BirthdayEditPanel extends EditPanelTemplate<Birthday> {
  private static final EmfSampleFactory EMF_SAMPLE_FACTORY = EmfSampleFactory.eINSTANCE;
  private static final EmfSamplePackage EMF_SAMPLE_PACKAGE = EmfSamplePackage.eINSTANCE;
  
  /*
   * EditorControls
   */
  
  /**
   * Day control
   */
  private EditorControlInteger dayControl;
  
  /**
   * Month control
   */
  private EditorControlInteger monthControl;
  
  /**
   * Year control
   */
  private EditorControlInteger yearControl;
  
  
  /**
   * Other GUI items
   */
  
  /**
   * Main edit panel
   */
  private VBox mainPane;
  
  
  
  /**
   * Create an instance of the {@code BirthdayEditPanel}.
   * 
   * @param customization the GUI customization.
   * @return a newly created {@code BirthdayEditPanel}.
   */
  public static BirthdayEditPanel newInstance(CustomizationFx customization, String id) {
    BirthdayEditPanel birthdayEditPanel = new BirthdayEditPanel(customization, id);
    birthdayEditPanel.performInitialization();
    
    return birthdayEditPanel;
  }
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  private BirthdayEditPanel(CustomizationFx customization, String id) {
    super(customization, id, false);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls() {
    
    dayControl = new EditorControlInteger.Builder("day")
        .setWidth(300d)
        .setLabelBaseText("_Day")
        .setToolTipText("The day of the birthday (1-31)")
        .setOptional(true)
        .build();
    
    monthControl = new EditorControlInteger.Builder("month")
        .setWidth(300d)
        .setLabelBaseText("_Month")
        .setToolTipText("The month of the birthday (1-12)")
        .setOptional(true)
        .build();
    
    yearControl = new EditorControlInteger.Builder("year")
        .setWidth(300d)
        .setLabelBaseText("_Year")
        .setToolTipText("The year of the birthday (e.g. 1980)")
        .setOptional(true)
        .build();
    
    registerEditorComponents(dayControl, monthControl, yearControl);
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
    
    if (value.isSetDay()) {
      dayControl.setValue(value.getDay());
    } else {
      dayControl.setValue(null);
    }
    if (value.isSetMonth()) {
      monthControl.setValue(value.getMonth());
    } else {
      monthControl.setValue(null);
    }
    if (value.isSetYear()) {
      yearControl.setValue(value.getYear());
    } else {
      yearControl.setValue(null);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillObjectFromControls(Birthday object, boolean getCurrentValue) throws EditorException {
    if (object == null  &&  anyControlNonNull()) {
      object = createObject();
    }
    EmfUtil.setFeatureValue(object, EMF_SAMPLE_PACKAGE.getBirthday_Day(), dayControl.getValue());
    EmfUtil.setFeatureValue(object, EMF_SAMPLE_PACKAGE.getBirthday_Month(), monthControl.getValue());
    EmfUtil.setFeatureValue(object, EMF_SAMPLE_PACKAGE.getBirthday_Year(), yearControl.getValue());
  }
  
//  protected boolean anyControlNonNull() {
//    return dayControl.getValue() != null  ||  monthControl.getValue() != null  ||  yearControl.getValue() != null;
//  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel() {
    mainPane = componentFactory.createVBox();
    mainPane.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    int row = 0;
    
    // First row: 'Day: <day>'
    gridPane.addRow(row++, dayControl.getLabel(), dayControl.getControl(), dayControl.getStatusIndicator());
    
    // Second row: 'Month: <month>'
    gridPane.addRow(row++, monthControl.getLabel(), monthControl.getControl(), monthControl.getStatusIndicator());
    
    // Third row: 'Year: <year>'
    gridPane.addRow(row++, yearControl.getLabel(), yearControl.getControl(), yearControl.getStatusIndicator());

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
  protected Birthday createObject() {
    Birthday birthday = EMF_SAMPLE_FACTORY.createBirthday();
    birthday.setYear(1984);
    return birthday;
  }

  @Override
  public String getValueAsFormattedText() {
    return null;
  }

}
