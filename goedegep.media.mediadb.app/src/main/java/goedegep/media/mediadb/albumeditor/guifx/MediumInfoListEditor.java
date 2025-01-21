package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objecteditor.ObjectEditorException;
import goedegep.jfx.objecteditor.ObjectEditorTemplate;
import goedegep.media.mediadb.model.MediumInfo;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class provides the controls for MediumInfo.
 */
class MediumInfoListEditor extends ObjectEditorTemplate<List<MediumInfo>> {
  
  /**
   * The GUI customization
   */
  private CustomizationFx customization;
  
  /**
   * List of medium infos currently set in the controls.
   */
  private List<MediumInfoObjectControlAggregation> mediumInfoObjectControlAggregations;
  
  /**
   * The pane where all medium info is shown.
   */
  private GridPane gridPane;
  
  
  /**
   * Factory method to obtain a new instance of a {@code MediumInfoListEditor}.
   * 
   * @param customization the GUI customization.
   * @param mediaDb the media database.
   * @return a newly created {@code MediumInfoListEditor}.
   */
  public static MediumInfoListEditor newInstance(CustomizationFx customization, Consumer<List<MediumInfo>> addMediumInfoMethod) {
    MediumInfoListEditor mediumInfoListEditor = new MediumInfoListEditor(customization, addMediumInfoMethod);
    mediumInfoListEditor.performInitialization();
    
    return mediumInfoListEditor;
  }

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
  private MediumInfoListEditor (CustomizationFx customization, Consumer<List<MediumInfo>> addMediumInfoMethod) {
    super(customization, "Medium information editor", addMediumInfoMethod);
    
    this.customization = customization;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureEditor() {
    setAddObjectTexts("No action", "");
    setUpdateObjectTexts("Update medium info", "Update the medium information");
    setNewObjectTexts("New", "Clear the controls to start entering new media information");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createControls() {
    mediumInfoObjectControlAggregations = new ArrayList<>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel(VBox rootPane) {
    gridPane = componentFactory.createGridPane(12.0, 12.0);    
    rootPane.getChildren().add(gridPane);
    
    Button addMediumInfoButton = componentFactory.createButton("Add an extra medium info", "Click to add another medium information");
    addMediumInfoButton.setOnAction((e) -> addNewMediaInfomation());
    rootPane.getChildren().add(addMediumInfoButton);
  }

  /**
   * Add a new {@code MediumInfo}.
   */
  private void addNewMediaInfomation() {
    MediumInfoObjectControlAggregation mediumInfoObjectControlAggregation = MediumInfoObjectControlAggregation.newInstance(customization);
    objectControlsGroup.addObjectControlGroup(mediumInfoObjectControlAggregation.getObjectControlsGroup());
    mediumInfoObjectControlAggregations.add(mediumInfoObjectControlAggregation);
    
    redrawMediumInfos();
  }
  
  private void redrawMediumInfos() {
    gridPane.getChildren().clear();
    
    if (!mediumInfoObjectControlAggregations.isEmpty()) {
      createHeader();
    }

    int row = 1;
    for (MediumInfoObjectControlAggregation mediumInfoObjectControlAggregation: mediumInfoObjectControlAggregations) {
      addMediumInfo(mediumInfoObjectControlAggregation, row++);
    }
  }

  private void createHeader() {
    int column = 0;
    gridPane.add(componentFactory.createStrongLabel("Medium type"), column++, 0);
    gridPane.add(componentFactory.createStrongLabel("Information type"), column++, 0);
    gridPane.add(componentFactory.createStrongLabel("Source type"), column++, 0);
    gridPane.add(componentFactory.createStrongLabel("Source bit rate"), column++, 0);
  }

  private void addMediumInfo(MediumInfoObjectControlAggregation mediumInfoObjectControlAggregation, int row) {
    int column = 0;
    gridPane.add(mediumInfoObjectControlAggregation.getMediumTypeObjectControl().getControl(), column++, row);
    gridPane.add(mediumInfoObjectControlAggregation.getInformationTypeObjectControl().getControl(), column++, row);
    gridPane.add(mediumInfoObjectControlAggregation.getSourceTypeObjectControl().getControl(), column++, row);
    gridPane.add(mediumInfoObjectControlAggregation.getSourceBitRateObjectControl().getControl(), column++, row);
  }

  @Override
  protected void createObject() {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void fillControlsWithDefaultValues() {
    mediumInfoObjectControlAggregations.clear();    
  }

  @Override
  protected void fillControlsFromObject() {
    for (MediumInfo mediumInfo: object) {
      addNewMediaInfomation();
      MediumInfoObjectControlAggregation mediumInfoObjectControlAggregation = mediumInfoObjectControlAggregations.getLast();
      mediumInfoObjectControlAggregation.setId("medium info "  + mediumInfoObjectControlAggregations.size());
      mediumInfoObjectControlAggregation.setObject(mediumInfo);
    }
    
  }

  @Override
  protected void updateObjectFromControls() throws ObjectEditorException {
    // TODO Auto-generated method stub
    
  }
}
