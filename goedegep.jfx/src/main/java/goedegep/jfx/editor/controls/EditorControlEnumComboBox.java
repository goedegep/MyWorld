package goedegep.jfx.editor.controls;

import java.util.Objects;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.editor.EditorControl;
import goedegep.jfx.editor.EditorControlTemplate;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;


/**
 * This class provides a ComboBox {@link EditorControl} for an {@code Enum}.
 * <p>
 * It is possible to specify a 'not set value', for which a null String will be used. Enums often have a 'not set value' like e.g. NOT_SET (which in case of en EMF EEnum a literal '&lt;not-set&gt;').
 * The '&lt;not-set&gt;' doesn't look nice in the values of the ComboBox. (Not implemented yet)
 * <p>
 * What kind of texts are shown in the ComboBox depends on the constructor used:
 * <ul>
 * <li>
 * If nothing is specified, the ComboBox uses toString() to get a text for an enum constant. So you can use this if you are happy with the enum constant, or if your enum overrides toString().
 * (Not implemented yet)
 * </li>
 * <li>
 * When you specify an EEnum in the constructor, the literals of the enum constants are used.
 * </li>
 * <li>
 * If you provide a Map for the mapping of the enum constants to their texts, then this mapping will be used.
 * (Not implemented yet)
 * </li>
 * </ul>
 * 
 * @param <T> The Enum type.
 */
public class EditorControlEnumComboBox<T extends Enum<T>> extends EditorControlTemplate<T> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlEnumComboBox.class.getName());
  
  /**
   * The GUI control
   */
  private ComboBox<T> comboBox = null;
  
  /**
   * The toolTip text for the {@code comboBox}.
   */
  private String toolTipText = null;
  
  /**
   * A single enum constant of the enum.
   */
  private T enumConstant;
  
  
  /**
   * Constructor using a Builder.
   * 
   * @param builder The {@code Builder} providing all settings.
   */
  private EditorControlEnumComboBox(Builder<T> builder) {
    super(builder.customization != null ? builder.customization : DefaultCustomizationFx.getInstance(), builder.optional);
    
    enumConstant = builder.enumConstant;
    toolTipText = builder.toolTipText;
    setId(builder.id);
    setLabelBaseText(builder.labelBaseText);
  }

  @Override
  public void createControls() {
    comboBox = customization.getComponentFactoryFx().createComboBox(null);
    
    comboBox.getItems().clear();
    for (T constant: enumConstant.getDeclaringClass().getEnumConstants()) {
      comboBox.getItems().add(constant);
    }
    
    comboBox.valueProperty().addListener(_ -> handleNewUserInput(comboBox));
            
    if (toolTipText != null) {
      comboBox.setTooltip(new Tooltip(toolTipText));
    }
    
    comboBox.setOnAction(_ -> handleNewUserInput(comboBox));
  }

  @Override
  protected void fillControlsWithDefaultValues() {
    comboBox.getSelectionModel().select(0);    
  }

  @Override
  public ComboBox<T> getControl() {
    return comboBox;
  }

  @Override
  public String getValueAsFormattedText() {
    return value.name();
  }

  @Override
  protected boolean determineFilledIn(Object source) {
    // as there is only one control, we can ignore the source parameter
    
    return comboBox.getSelectionModel().getSelectedItem() != null;
  }

  @Override
  protected T determineValue(Object source) {
    return comboBox.getSelectionModel().getSelectedItem();
  }

  @Override
  protected void updateNonSourceControls(Object source) {
    comboBox.getSelectionModel().select(getValue());
  }

  @Override
  protected void redrawValue() {
    // No action needed    
  }

  @Override
  protected void setErrorFeedback(boolean valid) {
    // No action needed
  }
 
  
  /**
   * Builder class to set all properties for an {@code EditorControlEnumComboBox}.
   */
  public static class Builder<T extends Enum<T>> {
    
    /**
     * A single enum constant of the enum (mandatory).
     */
    private T enumConstant;
    
    /**
     * The unique id of the ObjectControl (mandatory).
     */
    String id;
    
    /**
     * The GUI customization (optional).
     * <p>
     * If not set an instance of the {@link DefaultCustomizationFx} is used.
     */
    private CustomizationFx customization;
    
    
    /**
     * Indication of whether a value is optional or not (default value is {@code false}, indicating mandatory).
     */
    private boolean optional;
    
    /**
     * Base text for the label.
     */
    private String labelBaseText;
    
    /**
     * ToolTip text for the control (optional).
     */
    private String toolTipText;
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param enumConstant A single enum constant of the enum (may not be null).
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public Builder(T enumConstant, String id) {
      Objects.requireNonNull(enumConstant, "The enumConstant may not be null");
      Objects.requireNonNull(id, "The id may not be null");
      
      this.enumConstant = enumConstant;
      this.id = id;
    }
    
    /**
     * Set the GUI customization.
     * 
     * @param customization The GUI customization
     * @return this
     */
    public Builder<T> setCustomization(CustomizationFx customization) {
      this.customization = customization;
      
      return this;
    }
    
    /**
     * Set the optional indication.
     * 
     * @param optional Indication of whether a value is optional or not (default value is {@code false}, indicating mandatory).
     * @return this
     */
    public Builder<T> setOptional(boolean optional) {
      this.optional = optional;
      
      return this;
    }
    
    /**
     * Set the base text for the label.
     * 
     * @param labelBaseText the base text for the label.
     * @return this
     */
    public Builder<T> setLabelBaseText(String labelBaseText) {
      this.labelBaseText = labelBaseText;
      
      return this;
    }
    
    /**
     * Set the ToolTip text.
     * 
     * @param toolTipText the ToolTip text.
     * @return this
     */
    public Builder<T> setToolTipText(String toolTipText) {
      this.toolTipText = toolTipText;
      
      return this;
    }
    
    /**
     * Create the {@code EditorControlEnumComboBox}.
     * 
     * @return the {@code EditorControlEnumComboBox}.
     */
    public EditorControlEnumComboBox<T> build() {
      EditorControlEnumComboBox<T> editorControlEnumComboBox = new EditorControlEnumComboBox<T>(this);
      editorControlEnumComboBox.performInitialization();
      
      return editorControlEnumComboBox;
    }
    
  }
}
