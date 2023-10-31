package goedegep.jfx.objectcontrols;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.util.PgUtilities;
import javafx.scene.control.TextField;

public class ObjectControlString extends ObjectControlAbstract<String> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlString.class.getName());
  
  /**
   * The control.  
   */
  private TextField textField = null;
      
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param initialValue the initial value.
   * @param width width of the TextField.
   * @param isOptional indication of whether the value is optional or not.
   * @param toolTipText an optional tooltip text.
   */
  public ObjectControlString(CustomizationFx customization, String initialValue, double width, boolean isOptional, String toolTipText) {
    super(isOptional);
    
    textField = customization.getComponentFactoryFx().createTextField(width, toolTipText);

    textField.textProperty().addListener((observableValue, oldValue, newValue) -> ociHandleNewUserInput(textField));
    
    ocSetValue(initialValue);
  }
  
  public TextField ocGetControl() {
    return textField;
  }

  @Override
  public void ocSetValue(String objectValue) {
    referenceValue = objectValue;
    textField.setText(objectValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ocIsChanged() {
    // For Strings we handle an empty String as equal to null.
    if (((value != null)  &&  value.isEmpty())  &&
        (referenceValue == null)) {
      return false;
    }
    
    if (((referenceValue != null)  &&  referenceValue.isEmpty()) &&
        (value == null)) {
      return false;
    }
    return !PgUtilities.equals(value, referenceValue);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ociDetermineFilledIn() {
    return textField.getText() != null  &&  !textField.getText().isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String ociDetermineValue(Object source) {
    String text = textField.getText();
    
    if (text != null) {
      text = text.trim();
    }
    
    return text;
  }
  
  /**
   * {@inheritDoc}
   * A value is never invalid, so no action.
   */
  @Override
  public void ociSetErrorFeedback(boolean valid) {
  }
  
  /**
   * {@inheritDoc}
   * There is no formatting, so no action.
   */
  @Override
  public void ociRedrawValue() {
  }
  
  /**
   * {@inheritDoc}
   * There is no formatting, so just return the text.
   */
  @Override
  public String ocGetObjectValueAsFormattedText()  {
    return value;
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("ObjectControl type=String");
    buf.append(", id=").append(ocGetId() != null ? ocGetId() : "<null>");
    buf.append(", value=").append(value != null ? value : "<null>");
    buf.append(", referenceValue=").append(referenceValue != null ? "\"" + referenceValue + "\"" : "<null>");
    
    return buf.toString();
  }
}
