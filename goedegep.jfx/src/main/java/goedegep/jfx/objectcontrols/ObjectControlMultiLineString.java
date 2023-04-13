package goedegep.jfx.objectcontrols;

import goedegep.jfx.CustomizationFx;
import javafx.scene.control.TextArea;

public class ObjectControlMultiLineString extends ObjectControlAbstract<String> {
  
    
  private TextArea textArea = null;
  
  
  /**
   * Constructor.
   * 
   * @param text Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlMultiLineString(CustomizationFx customization, String text, double width, boolean isOptional, String toolTipText) {
    super(isOptional);

    textArea = customization.getComponentFactoryFx().createTextArea();
    textArea.textProperty().addListener((observableValue, oldValue, newValue) -> ociHandleNewUserInput());

    ocSetValue(text);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public TextArea ocGetControl() {
    return textArea;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ocSetValue(String objectValue) {
    referenceValue = objectValue;
    textArea.setText(objectValue);    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ociDetermineFilledIn() {
    return textArea.getText() != null  &&  !textArea.getText().isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String ociDetermineValue() {
    String text = textArea.getText();
    
    if (text != null) {
      text = text.trim();
    }
    
    return text;
  }
  
  /**
   * {@inheritDoc}
   * The text is always valid, so no action.
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
   * There is no formatting, so just return the value.
   */
  @Override
  public String ocGetObjectValueAsFormattedText()  {
    return value;
  }
  
}
