package goedegep.jfx.editor.controls;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
import goedegep.jfx.editor.EditorControlTemplate;
import javafx.scene.input.InputEvent;
import javafx.scene.web.HTMLEditor;

public class EditorControlHTMLString extends EditorControlTemplate<String> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlHTMLString.class.getName());  
  
  
  private HTMLEditor htmlEditor = null;
        
  
  /**
   * Constructor using builder.
   * 
   * @param builder The {@code Builder} providing all settings.
   */
  protected EditorControlHTMLString(Builder builder) {
    super(builder.customization, builder.id, builder.optional);
    
    setLabelBaseText(builder.labelBaseText);
  }
  
  
  @Override
  public void createControls() {
    htmlEditor = customization.getComponentFactoryFx().createHTMLEditor();
    htmlEditor.addEventHandler(InputEvent.ANY, (_) -> handleNewUserInput(htmlEditor));
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
  
  /**
   * Builder class to set all properties for an {@code EditorControlHTMLString}.
   */
  public static class Builder {
    
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
     * Constructor with mandatory arguments.
     * 
     * @param customization The GUI customization (may not be null).
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public Builder(CustomizationFx customization, String id) {
      this.customization = customization;
      this.id = id;
    }
    
    
    /**
     * Set the optional indication.
     * 
     * @param optional Indication of whether a value is optional or not (default value is {@code false}, indicating mandatory).
     * @return this
     */
    public Builder setOptional(boolean optional) {
      this.optional = optional;
      
      return this;
    }
    
    /**
     * Set the base text for the label.
     * 
     * @param labelBaseText The base text for the label.
     * @return this
     */
    public Builder setLabelBaseText(String labelBaseText) {
      this.labelBaseText = labelBaseText;
      
      return this;
    }
    
    /**
     * Create the {@code EditorControlHTMLString}.
     * 
     * @return the {@code EditorControlHTMLString}.
     */
    public EditorControlHTMLString build() {
      EditorControlHTMLString editorControlHTMLString = new EditorControlHTMLString(this);
      editorControlHTMLString.performInitialization();
      
      return editorControlHTMLString;
    }
    
  }
}
