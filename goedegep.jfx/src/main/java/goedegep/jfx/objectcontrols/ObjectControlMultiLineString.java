package goedegep.jfx.objectcontrols;

import goedegep.jfx.CustomizationFx;
import javafx.scene.control.TextArea;

public class ObjectControlMultiLineString extends ObjectControlTemplate<String> {
  
    
  private TextArea textArea = null;
  
  
  /**
   * Constructor.
   * 
   * @param text Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlMultiLineString(CustomizationFx customization, String text, boolean isOptional) {
    super(customization, isOptional);

    textArea = customization.getComponentFactoryFx().createTextArea();
    textArea.textProperty().addListener((observableValue, oldValue, newValue) -> ociHandleNewUserInput(textArea));

    setObject(text);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public TextArea getControl() {
    return textArea;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ociDetermineFilledIn(Object source) {
    // As there is only one control we don't have to check the source.
    
    return textArea.getText() != null  &&  !textArea.getText().isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String ociDetermineValue(Object source) {
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

  @Override
  protected void ociUpdateNonSourceControls(Object source) {
    textArea.setText(getValue());
  }
  
  /**
   * {@inheritDoc}
   * There is no formatting, so just return the value.
   */
  @Override
  public String getValueAsFormattedText()  {
    return getValue();
  }
  
}
