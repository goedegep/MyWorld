package goedegep.jfx.editor.controls;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DefaultCustomizationFx;
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
   * Constructor using builder.
   * 
   * @param builder The {@code Builder} providing all settings.
   */
  public EditorControlMultiLineString(Builder builder) {
    super(builder.customization, builder.id, builder.optional);
    
    setLabelBaseText(builder.labelBaseText);
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

  
  /**
   * Builder class to set all properties for an {@code EditorControlMultiLineString}.
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
    public EditorControlMultiLineString build() {
      EditorControlMultiLineString editorControlMultiLineString = new EditorControlMultiLineString(this);
      editorControlMultiLineString.performInitialization();
      
      return editorControlMultiLineString;
    }
    
  }
  
}
