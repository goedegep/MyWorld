package goedegep.demo.jfx.objectcontrols.guifx;

import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class demonstrates the ObjectConrols from goedegep.jfx.objectcontrols.
 * The contols are shown in an editor, which can also be used as a template.
 * 
 * The editor
 * When the editor is started for a new object, all controls are cleared and optionally set to default values.
 * 
 */
public class ObjectControlsDemo extends JfxStage {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(ObjectControlsDemo.class.getName());
  
  private ComponentFactoryFx componentFactory;
  
  private ObservableList<InquiryData> inquiryDataList;
  private Button editButton;
  private InquiryDataPanel inquiryDataPanel;
  private EditorPanel editorPanel;
  
  public ObjectControlsDemo(CustomizationFx customization) {
    super("ObjectControls demo", customization);
    
    componentFactory = customization.getComponentFactoryFx();
    
    inquiryDataPanel = new InquiryDataPanel(customization);
    inquiryDataList = InquiryData.getInquiryDataList();
    editorPanel = new EditorPanel(customization, inquiryDataList);
    inquiryDataPanel.setItems(inquiryDataList);
    editButton = componentFactory.createButton("Edit InquiryData", "Edit the selected InquiryData");
    editButton.setOnAction((e) -> {
      editorPanel.setInquiryData(inquiryDataPanel.getSelectionModel().getSelectedItem());
    });
    
    createGUI();
    
    show();
  }
  
  /**
   * Create the GUI.
   * Left: list InquiryData (with Edit button). Shows also previous value.
   * Right: EditorPanel (with New, Add/Update buttons)
   */
  private void createGUI() {
    HBox hBox = componentFactory.createHBox();
    
    VBox inquiryDataOuterPanel = componentFactory.createVBox();
    inquiryDataOuterPanel.setPrefWidth(600.0);
    inquiryDataOuterPanel.setPrefHeight(1000.0);
    inquiryDataOuterPanel.setPadding(new Insets(12.0));
    inquiryDataPanel.prefHeightProperty().bind(inquiryDataOuterPanel.heightProperty());
    inquiryDataOuterPanel.getChildren().add(inquiryDataPanel);
    VBox leftPanel = componentFactory.createVBox();
    HBox buttonBox = componentFactory.createHBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().add(editButton);
    leftPanel.getChildren().addAll(inquiryDataOuterPanel, buttonBox);
    
    hBox.getChildren().add(leftPanel);
    hBox.getChildren().add(editorPanel);

    setScene(new Scene(hBox));
  }
}
