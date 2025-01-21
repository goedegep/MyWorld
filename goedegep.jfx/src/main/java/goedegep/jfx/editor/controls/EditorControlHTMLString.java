package goedegep.jfx.editor.controls;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditorControlTemplate;
import goedegep.jfx.editor.EditorException;
import javafx.scene.input.InputEvent;
import javafx.scene.web.HTMLEditor;

public class EditorControlHTMLString extends EditorControlTemplate<String> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlHTMLString.class.getName());  
  
  
  private HTMLEditor htmlEditor = null;
  
  /**
   * Get an {@code EditorControlHTMLString} instance.
   * 
   * @param customization The GUI customization.
   * @param optional Indicates whether the control is optional (if true) or mandatory.
   * @return a new {@code EditorControlHTMLString} instance.
   */
  public static EditorControlHTMLString newInstance(CustomizationFx customization, boolean optional) {
    EditorControlHTMLString editorControlHTMLString = new EditorControlHTMLString(customization, optional);
    editorControlHTMLString.performInitialization();
    
    return editorControlHTMLString;
  }
      
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param optional Indicates whether the control is optional (if true) or mandatory.
   */
  private EditorControlHTMLString(CustomizationFx customization, boolean optional) {
    super(customization, optional);
    
  }
  
  @Override
  public void createControls() {
    htmlEditor = customization.getComponentFactoryFx().createHTMLEditor();
    htmlEditor.addEventHandler(InputEvent.ANY, (e) -> handleNewUserInput(htmlEditor));
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
  public boolean determineFilledIn(Object source) {
    // As there is only one control we don't have to check the source.
    
    return (htmlEditor.getHtmlText() != null)  &&  !htmlEditor.getHtmlText().equals("<html dir=\"ltr\"><head></head><body contenteditable=\"true\"></body></html>")  &&
        !htmlEditor.getHtmlText().equals("<html><head></head></html>");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String determineValue(Object source) {
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
  public void setErrorFeedback(boolean valid) {
  }
  
  /**
   * {@inheritDoc}
   * No action needed.
   */
  @Override
  public void redrawValue() {
  }

  @Override
  protected void updateNonSourceControls(Object source) {
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

  @Override
  protected void fillControlsWithDefaultValues() {
    htmlEditor.setHtmlText("<html><head></head></html>");
    
  }
  
}
