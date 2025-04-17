package goedegep.jfx.editor.controls;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditorControlTemplate;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;

/**
 * This class provides a CheckBox to be used to edit a {@code boolean}.
 */
public class EditorControlBoolean extends EditorControlTemplate<Boolean> {
  private static final Logger         LOGGER = Logger.getLogger(EditorControlBoolean.class.getName());
  
  
  private String toolTipText;
  
  /**
   * The Control
   */
  CheckBox checkBox = null;

  
  public static EditorControlBoolean newInstance(CustomizationFx customization, String toolTipText) {
    EditorControlBoolean editorControlBoolean = new EditorControlBoolean(customization, toolTipText);
    editorControlBoolean.performInitialization();
    
    return editorControlBoolean;
  }
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param toolTipText An optional Tooltip text.
   */
  public EditorControlBoolean(CustomizationFx customization, String toolTipText) {
    super(customization, true);
    
    this.toolTipText = toolTipText;
  }

  @Override
  public void createControls() {
    
    checkBox = componentFactory.createCheckBox(null, false);
    
    checkBox.selectedProperty().addListener((o)-> handleNewUserInput(checkBox));
    
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
}
