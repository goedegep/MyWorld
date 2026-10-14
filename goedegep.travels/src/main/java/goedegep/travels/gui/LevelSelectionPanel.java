package goedegep.travels.gui;

import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.controls.EditorControlEnumComboBox;
import goedegep.travels.model.InformationLevel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * This class is a panel to select the Show Level (what information is shown for a selected item in the travels tree).
 * <p>
 * The level can be selected by a combo box or by two buttons to increase or decrease the level.
 */
public class LevelSelectionPanel extends HBox {
  private final static Logger LOGGER = Logger.getLogger(LevelSelectionPanel.class.getName());

  
  /**
   * Property to be used by the client to react on a new selected value.
   */
  private ObjectProperty<InformationLevel> levelProperty = new SimpleObjectProperty<>();
  
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
  private EditorControlEnumComboBox<InformationLevel> showLevelComboBox;
  
  
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
    showLevelComboBox = new EditorControlEnumComboBox.Builder<>(InformationLevel.TRAVEL, "Show level")
        .setCustomization(customization)
        .setLabelBaseText("Show level")
        .setToolTipText("Select the level of information to be shown on the map")
        .build();
    showLevelComboBox.addValueAndOrStatusChangeListener((_, _) -> handleNewShowLevelSelection());
    upButton = componentFactory.createButton("+", "Click to increase the show level, to show more at once");
    upButton.setOnAction((_) -> increaseLevel());
    
    getChildren().addAll(showLevelComboBox.getLabel(), downButton, showLevelComboBox.getControl(), upButton);
    showLevelComboBox.setValue(InformationLevel.TRAVEL);
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
  ObjectProperty<InformationLevel> levelProperty() {
    return levelProperty;
  }
  
  /**
   * Decrease the show level.
   * <p>
   * If the lowest level is already selected, this doesn't change, else the level is decreased.
   */
  private void decreaseLevel() {
    InformationLevel previousLevel = previousInformationLevel(showLevelComboBox.getValue());
    showLevelComboBox.setValue(previousLevel);
    InformationLevel currentLevel = showLevelComboBox.getValue();
    if (currentLevel != previousLevel) {
      LOGGER.severe("Current level incorrect " + previousLevel + " to " + currentLevel);
    }
  }
  
  /**
   * Increase the show level.
   * <p>
   * If the highest level is already selected, this doesn't change, else the level is increased.
   */
  private void increaseLevel() {
    showLevelComboBox.setValue(nextInformationLevel(showLevelComboBox.getValue()));
  }
  
  /**
   * Enable or disable the up- and downButtons.
   * <p>
   * If the lowest level is selected, the downButton is disabled, else it is enabled.
   * If the highest level is selected, the upButton is disabled, else it is enabled.
   */
  private void enableOrDisableButtons() {
    InformationLevel informationLevel = showLevelComboBox.getValue();
    
    informationLevel.ordinal();
    downButton.setDisable(informationLevel.ordinal() == 0);
    upButton.setDisable(informationLevel.ordinal() == InformationLevel.values().length - 1);
  }
  
  /**
   * Get the next lower information level.
   * 
   * @param informationLevel an information level
   * @return the previous information level, or {@code informationLevel) if it is already the lowest level.
   */
  private InformationLevel previousInformationLevel(InformationLevel informationLevel) {
    LOGGER.severe("InformationLevel: " + informationLevel + ", ordinal: " + informationLevel.ordinal() + ", length: " + InformationLevel.values().length);
    if (informationLevel.ordinal() > 0) {
      return InformationLevel.values()[(informationLevel.ordinal() - 1)];
    } else {
      return informationLevel;
    }
  }
  
  
  /**
   * Get the next higher information level.
   * 
   * @param informationLevel an information level
   * @return the next information level, or {@code informationLevel) if it is already the highest level.
   */
  private InformationLevel nextInformationLevel(InformationLevel informationLevel) {
    LOGGER.severe("InformationLevel: " + informationLevel + ", ordinal: " + informationLevel.ordinal() + ", length: " + InformationLevel.values().length);
    if (informationLevel.ordinal() < InformationLevel.values().length - 1) {
      LOGGER.severe("Next InformationLevel: " + InformationLevel.values()[(informationLevel.ordinal() + 1)]);
      return InformationLevel.values()[(informationLevel.ordinal() + 1)];
    } else {
      LOGGER.severe("Next InformationLevel is informationLevel: " + informationLevel);
      return informationLevel;
    }
  }
}
