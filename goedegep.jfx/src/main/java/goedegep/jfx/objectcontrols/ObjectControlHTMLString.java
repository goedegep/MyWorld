package goedegep.jfx.objectcontrols;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import javafx.scene.input.InputEvent;
import javafx.scene.web.HTMLEditor;

public class ObjectControlHTMLString extends ObjectControlTemplate<String> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlHTMLString.class.getName());  
  
  
  private HTMLEditor htmlEditor = null;
      
  /**
   * Constructor.
   * 
   * @param text Initial value to set the text to (may be null).
   * @param width The width of the HTMLEditor
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlHTMLString(CustomizationFx customization, String text, boolean isOptional) {
    super(customization, isOptional);
    
    htmlEditor = customization.getComponentFactoryFx().createHTMLEditor();
    htmlEditor.addEventHandler(InputEvent.ANY, (e) -> ociHandleNewUserInput(htmlEditor));
    
    setObject(text);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public HTMLEditor getControl() {
    return htmlEditor;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ociDetermineFilledIn(Object source) {
    // As there is only one control we don't have to check the source.
    
    return (htmlEditor.getHtmlText() != null)  &&  !htmlEditor.getHtmlText().equals("<html dir=\"ltr\"><head></head><body contenteditable=\"true\"></body></html>")  &&
        !htmlEditor.getHtmlText().equals("<html><head></head></html>");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String ociDetermineValue(Object source) {
    String text = htmlEditor.getHtmlText();
    
    if (text != null) {
      text = text.trim();
    }
    
    return text;
  }
  
  /**
   * {@inheritDoc}
   * For now no action.
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

  @Override
  protected void ociUpdateNonSourceControls(Object source) {
    if (source != htmlEditor) {
      if (getValue() != null) {
        htmlEditor.setHtmlText(getValue());
      } else {
        htmlEditor.setHtmlText("<html><head></head></html>");
      }
    }
  }
  
  /**
   * {@inheritDoc}
   * In this case the formatted text can only be shown in a browser, so just return the HTML text.
   */
  @Override
  public String getValueAsFormattedText()  {
    return getValue();
  }
  
}
