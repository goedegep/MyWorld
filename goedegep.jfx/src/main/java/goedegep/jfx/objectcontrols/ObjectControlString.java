package goedegep.jfx.objectcontrols;

import goedegep.jfx.CustomizationFx;
import javafx.scene.control.TextField;

public class ObjectControlString extends ObjectControlAbstract<String> {
  
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

    textField.textProperty().addListener((observableValue, oldValue, newValue) -> ociHandleNewUserInput());
    
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
  public boolean ociDetermineFilledIn() {
    return textField.getText() != null  &&  !textField.getText().isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String ociDetermineValue() {
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

}
