package goedegep.jfx.objectcontrols;

import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;


public class ObjectControlBoolean extends ObjectControlTemplate<Boolean> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlBoolean.class.getName());
  
  /**
   * The Control
   */
  CheckBox checkBox = null;

  
  /**
   * Constructor.
   * 
   * @param text Text to display with the CheckBox.
   * 
   * @param selected Initial selection value.
   * @param isOptional Indication of whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlBoolean(ComponentFactoryFx componentFactory, String text, boolean selected, boolean isOptional, String toolTipText) {
    super(isOptional);
    
    checkBox = componentFactory.createCheckBox(text, selected);  // TODO check that ocSetValue leads to calling ociHandleNewUserInput()
    
    checkBox.selectedProperty().addListener((o)-> ociHandleNewUserInput(checkBox));
    
    if (toolTipText != null) {
      checkBox.setTooltip(new Tooltip(toolTipText));
    }

//    if (ocGetValue() != selected) {
      setValue(selected);
//    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public CheckBox getControl() {
    return checkBox;
  }

  /**
   * {@inheritDoc}
   * A checkbox is always filled in.
   */
  @Override
  public boolean ociDetermineFilledIn(Object source) {
    // as there is only one control, we can ignore the source parameter
    
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean ociDetermineValue(Object source) {
    return checkBox.isSelected();
  }

  /**
   * {@inheritDoc}
   * No action, as there can't be an error.
   */
  @Override
  public void ociSetErrorFeedback(boolean valid) {
  }

  /**
   * {@inheritDoc}
   * No need to redraw, so no action.
   */
  @Override
  public void ociRedrawValue() {
  }

  @Override
  protected void ociUpdateNonSourceControls(Object source) {
    checkBox.setSelected(getValue());
  }

  /**
   * {@inheritDoc}
   * Not very useful for a boolean, but we just return 'true' or 'false'.
   */
  @Override
  public String getValueAsFormattedText() {
    return getValue() ? "true" : "false";
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("ObjectControl type=Boolean");
    buf.append(", id=").append(getId() != null ? getId() : "<null>");
    buf.append(", value=").append(getValue() != null ? getValue() : "<null>");
    buf.append(", referenceValue=").append(referenceValue != null ? "\"" + referenceValue + "\"" : "<null>");
    
    return buf.toString();
  }

}
