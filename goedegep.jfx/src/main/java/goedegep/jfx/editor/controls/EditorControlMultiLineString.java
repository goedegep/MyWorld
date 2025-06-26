package goedegep.jfx.editor.controls;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditorControl;
import goedegep.jfx.editor.EditorControlTemplate;
import javafx.scene.control.TextArea;

/**
 * This class is an {@link EditorControl} for editing a multi line text.
 */
public class EditorControlMultiLineString extends EditorControlTemplate<String> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlMultiLineString.class.getName());  
  
  
  private TextArea textArea = null;
  
  /**
   * Get an {@code EditorControlMultiLineString} instance.
   * 
   * @param customization The GUI customization.
   * @param optional Indicates whether the control is optional (if true) or mandatory.
   * @return a new {@code EditorControlMultiLineString} instance.
   */
  public static EditorControlMultiLineString newInstance(CustomizationFx customization, boolean optional) {
    EditorControlMultiLineString editorControlMultiLineString = new EditorControlMultiLineString(customization, optional);
    editorControlMultiLineString.performInitialization();
    
    return editorControlMultiLineString;
  }
      
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param optional Indicates whether the control is optional (if true) or mandatory.
   */
  private EditorControlMultiLineString(CustomizationFx customization, boolean optional) {
    super(customization, optional);
    
  }
  
  @Override
  public void createControls() {
    textArea = customization.getComponentFactoryFx().createTextArea();
    textArea.textProperty().addListener((_, _, _) -> handleNewUserInput(textArea));
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
  public boolean determineFilledIn(Object source) {
    // As there is only one control we don't have to check the source.
    
    return textArea.getText() != null  &&  !textArea.getText().isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String determineValue(Object source) {
    String text = textArea.getText();
    
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
    textArea.setText(getValue());
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
    textArea.setText("");
  }
  
}
