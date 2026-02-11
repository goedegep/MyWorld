package goedegep.travels.gui;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.controls.EditorControlEnumComboBox;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class LevelSelectionPanel extends HBox {
  private ObjectProperty<ShowLevel> levelProperty = new SimpleObjectProperty<>();
  private Button upButton;
  private Button downButton;
  private EditorControlEnumComboBox<ShowLevel> showLevelComboBox;
  
  public LevelSelectionPanel(CustomizationFx customization) {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    componentFactory.customizePane(this);
    this.setSpacing(12.0);
    this.setPadding(new Insets(0.0, 15.0, 0.0, 0.0));
    
    downButton = componentFactory.createButton("-", "Click to decrease the show level, to show less at once");
    downButton.setOnAction((_) -> decreaseLevel());
    showLevelComboBox = new EditorControlEnumComboBox.Builder<>(ShowLevel.TRAVEL, "Show level")
        .setCustomization(customization)
        .setLabelBaseText("Show level")
        .setToolTipText("Select the level of information to be shown on the map")
        .build();
    showLevelComboBox.addValueAndOrStatusChangeListener((_, _) -> handleNewShowLevelSelection());
    upButton = componentFactory.createButton("+", "Click to increase the show level, to show more at once");
    upButton.setOnAction((_) -> increaseLevel());
    
    getChildren().addAll(showLevelComboBox.getLabel(), downButton, showLevelComboBox.getControl(), upButton);
    showLevelComboBox.setObject(ShowLevel.TRAVEL);
  }
  
  private void handleNewShowLevelSelection() {
    enableOrDisableButtons();
    levelProperty.set(showLevelComboBox.getValue());
  }
  
  ObjectProperty<ShowLevel> levelProperty() {
    return levelProperty;
  }
  
  private void decreaseLevel() {
    showLevelComboBox.setObject(showLevelComboBox.getValue().previous());
  }
  
  private void increaseLevel() {
    showLevelComboBox.setObject(showLevelComboBox.getValue().next());
  }
  
  private void enableOrDisableButtons() {
    ShowLevel showLevel = showLevelComboBox.getValue();
    
    showLevel.ordinal();
    downButton.setDisable(showLevel.ordinal() == 0);
    upButton.setDisable(showLevel.ordinal() == 2);
  }
  
}
