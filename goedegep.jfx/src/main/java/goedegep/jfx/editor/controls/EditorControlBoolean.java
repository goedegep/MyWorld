package goedegep.jfx.editor.controls;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditorControlBuilder;
import goedegep.jfx.editor.EditorControlTemplate;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;

/**
 * This class provides a CheckBox to be used to edit a {@code boolean}.
 */
public class EditorControlBoolean extends EditorControlTemplate<Boolean> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlBoolean.class.getName());
  
  
  private String toolTipText;
  
  /**
   * The Control
   */
  CheckBox checkBox = null;

  
  /**
   * Constructor using a builder.
   * 
   * @param customization The GUI customization.
   * @param id the (unique) Id of the control
   * @param labelBaseText the base text for the label
   * @param toolTipText An optional Tooltip text.
   */
  public EditorControlBoolean(Builder builder) {
    super(builder.customization, true);
    
    setId(builder.id);  // TODO add as argument to super
    toolTipText = builder.toolTipText;
    setLabelBaseText(builder.labelBaseText);
  }

  @Override
  public void createControls() {
    
    checkBox = componentFactory.createCheckBox(null, false);
    
    checkBox.selectedProperty().addListener((_)-> handleNewUserInput(checkBox));
    
    if (toolTipText != null) {
      checkBox.setTooltip(new Tooltip(toolTipText));
    }
  }

  @Override
  public Node getControl() {
    return checkBox;
  }

  @Override
  public String getValueAsFormattedText() {
    return getCurrentValue().toString();
  }

  @Override
  protected void fillControlsWithDefaultValues() {
    checkBox.setSelected(false);
    handleNewUserInput(null);  // needed because setSelected does not trigger the listener if the selected state isn't actually changed.
  }

  @Override
  protected boolean determineFilledIn(Object source) {
    return true;
  }

  @Override
  protected Boolean determineValue(Object source) {
    return checkBox.isSelected();
  }

  @Override
  protected void updateNonSourceControls(Object source) {
    if (source == null) {
      checkBox.setSelected(getCurrentValue());
    }
    
  }

  @Override
  protected void redrawValue() {
    // No action needed
    
  }

  @Override
  protected void setErrorFeedback(boolean valid) {
    // No action needed
    
  }
  
  
  /**
   * This class is a builder to build an instance of {@code EditorControlBoolean}.
   */
  public static class Builder extends EditorControlBuilder {
    
    /**
     * Constructor
     * 
   * @param id The unique id of the ObjectControl (may not be null).
     */
    public Builder(String id) {
      super(id);
    }
    
    /**
     * Set the GUI customization.
     * 
     * @param customization The GUI customization
     * @return this
     */
    public Builder setCustomization(CustomizationFx customization) {
      this.customization = customization;
      
      return this;
    }
    
    /**
     * Set the base text for the label.
     * 
     * @param labelBaseText  the base text for the label.
     * @return this
     */
    public Builder setLabelBaseText(String labelBaseText) {
      this.labelBaseText = labelBaseText;
      
      return this;
    }
    
    /**
     * Set the ToolTip text.
     * 
     * @param toolTipText the ToolTip text
     * @return this
     */
    public Builder setToolTipText(String toolTipText) {
      this.toolTipText = toolTipText;
      
      return this;
    }
    
    /**
     * Create the {@code EditorControlBoolean} instance.
     * @return the {@code EditorControlBoolean} instance.
     */
    public EditorControlBoolean build() {
      EditorControlBoolean editorControlBoolean = new EditorControlBoolean(this);
      editorControlBoolean.performInitialization();
      
      return editorControlBoolean;
    }
  }
}
