package goedegep.travels.gui;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.controls.EditorControlEnumComboBox;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * This class is a panel to select the Show Level (what information is shown for a selected item in the travels tree).
 */
public class LevelSelectionPanel extends HBox {
  /**
   * Property to be used by the client to react on a new selected value.
   */
  private ObjectProperty<ShowLevel> levelProperty = new SimpleObjectProperty<>();
  
  /**
   * Button to choose a higher level.
   */
  private Button upButton;
  
  /**
   * Button to choose a lower level.
   */
  private Button downButton;
  
  /**
   * An {@code EditorControlEnumComboBox} providing the combo box.
   */
  private EditorControlEnumComboBox<ShowLevel> showLevelComboBox;
  
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   */
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
  
  /**
   * React to a new value of the {@code showLevelComboBox}
   * <p>
   * If there is a new value, the up- and downButtons may have to be enabled or disabled.
   */
  private void handleNewShowLevelSelection() {
    enableOrDisableButtons();
    levelProperty.set(showLevelComboBox.getValue());
  }
  
  /**
   * Get the {@link levelProperty}
   * 
   * @return the {@code levelProperty}
   */
  ObjectProperty<ShowLevel> levelProperty() {
    return levelProperty;
  }
  
  /**
   * Decrease the show level.
   * <p>
   * If the lowest level is already selected, this doesn't change, else the level is decreased.
   */
  private void decreaseLevel() {
    showLevelComboBox.setObject(showLevelComboBox.getValue().previous());
  }
  
  /**
   * Increase the show level.
   * <p>
   * If the highest level is already selected, this doesn't change, else the level is increased.
   */
  private void increaseLevel() {
    showLevelComboBox.setObject(showLevelComboBox.getValue().next());
  }
  
  /**
   * Enable or disable the up- and downButtons.
   * <p>
   * If the lowest level is selected, the downButton is disabled, else it is enabled.
   * If the highest level is selected, the upButton is disabled, else it is enabled.
   */
  private void enableOrDisableButtons() {
    ShowLevel showLevel = showLevelComboBox.getValue();
    
    showLevel.ordinal();
    downButton.setDisable(showLevel.ordinal() == 0);
    upButton.setDisable(showLevel.ordinal() == ShowLevel.values().length - 1);
  }
  
}
