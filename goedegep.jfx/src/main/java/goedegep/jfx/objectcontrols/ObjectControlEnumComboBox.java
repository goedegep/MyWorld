package goedegep.jfx.objectcontrols;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;


/**
 * This class provides a ComboBox ObjectControl for an Enum.
 * <p>
 * It is possible to specify a 'not set value', for which a null String will be used. Enums often have a 'not set value' like e.g. NOT_SET (with in case of en EMF EEnum a literal '&lt;not-set&gt;').
 * The '&lt;not-set&gt;' doesn't look nice in the values of the ComboBox.
 * <p>
 * What kind of texts are shown in the ComboBox depends on the constructor used:
 * <ul>
 * <li>
 * If nothing is specified, the ComboBox uses toString() to get a text for an enum constant. So you can use this if you are happy with the enum constant, or if your enum overrides toString().
 * </li>
 * <li>
 * When you specify an EEnum in the constructor, the literals of the enum constants are used.
 * </li>
 * <li>
 * If you provide a Map for the mapping of the enum constants to their texts, then this mapping will be used.
 * </li>
 * </ul>
 * 
 * @param <T> The Enum type.
 */
public class ObjectControlEnumComboBox<T extends Enum<T>> extends ObjectControlTemplate<T> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlEnumComboBox.class.getName());
  
  
  private ComboBox<T> comboBox = null;
  
  /**
   * Constructor
   * <p>
   * With this constructor the text for an enum constant is provided by the  <code>eEnum</code>.
   * 
   * @param customization the GUI customization
   * @param enumConstant A single enum constant of the enum.
   * @param isOptional indicates whether the value is optional or not
   * @param toolTipText an optional tooltip text
   */
  public ObjectControlEnumComboBox(CustomizationFx customization, T enumConstant, boolean isOptional, String toolTipText) {
    super(customization, isOptional);
//    enumTextConverter = new EnumTextConverter<T>(enumConstant, notSetValue, eEnum);
    
    init(customization, enumConstant, toolTipText);
  }
    
  public ComboBox<T> getControl() {
    return comboBox;
  }

  /**
   * Handle initialization.
   * 
   * @param isOptional indicates whether the value is optional or not
   * @param toolTipText an optional tooltip text
   */
  private void init(CustomizationFx customization, T enumConstant, String toolTipText) {
    comboBox = customization.getComponentFactoryFx().createComboBox(null);
    
    comboBox.getItems().clear();
    for (T constant: enumConstant.getDeclaringClass().getEnumConstants()) {
      comboBox.getItems().add(constant);
    }
    
    
    comboBox.valueProperty().addListener((o) -> ociHandleNewUserInput(comboBox));
            
    if (toolTipText != null) {
      comboBox.setTooltip(new Tooltip(toolTipText));
    }
    
    comboBox.setOnAction(event -> ociHandleNewUserInput(comboBox));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ociDetermineFilledIn(Object source) {
    // as there is only one control, we can ignore the source parameter
        
    return comboBox.getSelectionModel().getSelectedItem() != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T ociDetermineValue(Object source) {
    return comboBox.getSelectionModel().getSelectedItem();
  }

  /**
   * {@inheritDoc}
   * Input is always valid, so no action needed.
   */
  @Override
  public void ociSetErrorFeedback(boolean valid) {
  }

  /**
   * {@inheritDoc}
   * No action needed.
   */
  @Override
  public void ociRedrawValue() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValueAsFormattedText() {
    if (getValue() != null) {
      return comboBox.getConverter().toString(getValue());
    } else {
      return null;
    }
  }

  @Override
  protected void ociUpdateNonSourceControls(Object source) {
    comboBox.getSelectionModel().select(getValue());
  }

}
