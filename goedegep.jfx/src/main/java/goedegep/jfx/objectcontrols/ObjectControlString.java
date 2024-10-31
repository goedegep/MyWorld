package goedegep.jfx.objectcontrols;

import java.util.Objects;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import javafx.scene.control.TextField;

// TODO why do I have this, instead of using ObjectControlTextField?
public class ObjectControlString extends ObjectControlTemplate<String> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlString.class.getName());
  
  /**
   * The control.  
   */
  private TextField textField = null;
      
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param initialValue the initial value.
   * @param width width of the TextField.
   * @param isOptional indication of whether the value is optional or not.
   * @param toolTipText an optional tooltip text.
   */
  public ObjectControlString(CustomizationFx customization, String initialValue, double width, boolean isOptional, String toolTipText) {
    super(customization, isOptional);
    
    textField = customization.getComponentFactoryFx().createTextField(width, toolTipText);

    textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
      if ("disc title".equals(getId())) {
        LOGGER.severe("Disc title");
      }
      ociHandleNewUserInput(textField);
    });
        
    setComparator((o1, o2) -> {
      // For Strings we handle an empty String as equal to null.
      if (o1 != null  &&  o1.isEmpty()) {
        o1 = null;
      }
      
      if (o2 != null  &&  o2.isEmpty()) {
        o2 = null;
      }
      
      int result = Objects.equals(o1, o2) ? 0 : -1;
      return result;
    });
    
    setValue(initialValue);
  }
  
  public TextField getControl() {
    return textField;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ociDetermineFilledIn(Object source) {
    // As there is only one control we don't have to check the source.
    
    return textField.getText() != null  &&  !textField.getText().isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String ociDetermineValue(Object source) {
    String text = textField.getText();
    
    if (text != null) {
      text = text.trim();
    }
    
    return text;
  }
  
  /**
   * {@inheritDoc}
   * A value is never invalid, so no action.
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
    if (source == null) {
      textField.setText(getValue());
    }
  }
  
  /**
   * {@inheritDoc}
   * There is no formatting, so just return the text.
   */
  @Override
  public String getValueAsFormattedText()  {
    return getValue();
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("ObjectControl type=String");
    buf.append(", id=").append(getId() != null ? getId() : "<null>");
    buf.append(", value=").append(getValue() != null ? getValue() : "<null>");
    buf.append(", referenceValue=").append(referenceValue != null ? "\"" + referenceValue + "\"" : "<null>");
    
    return buf.toString();
  }
}
