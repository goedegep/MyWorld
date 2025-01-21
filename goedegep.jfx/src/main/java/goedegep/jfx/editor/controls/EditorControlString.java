package goedegep.jfx.editor.controls;

import java.util.Objects;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditorControlTemplate;
import goedegep.jfx.editor.EditorException;
import javafx.scene.control.TextField;

public class EditorControlString extends EditorControlTemplate<String> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlString.class.getName());
  
  /**
   * The control.  
   */
  private TextField textField = null;
  
  private double width;
  private String toolTipText;
  
  /**
   * Get an {@code EditorControlString} instance.
   * 
   * @param customization The GUI customization.
   * @param initialValue the initial value.
   * @param width width of the TextField.
   * @param optional indication of whether the value is optional or not.
   * @param toolTipText an optional tooltip text.
   * @return a new {@code EditorControlString} instance.
   */
  public static EditorControlString newInstance(CustomizationFx customization, double width, boolean optional, String toolTipText) {
    EditorControlString objectControlString = new EditorControlString(customization, width, optional, toolTipText);
    objectControlString.performInitialization();
    
    return objectControlString;
  }
      
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param width width of the TextField.
   * @param optional indication of whether the value is optional or not.
   * @param toolTipText an optional tooltip text.
   */
  private EditorControlString(CustomizationFx customization, double width, boolean optional, String toolTipText) {
    super(customization, optional);
    
    this.width = width;
    this.toolTipText = toolTipText;
  }

  @Override
  public void createControls() {
    textField = customization.getComponentFactoryFx().createTextField(width, toolTipText);

    textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
      handleNewUserInput(textField);
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
  }
  
  @Override
  public TextField getControl() {
    return textField;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean determineFilledIn(Object source) {
    // As there is only one control we don't have to check the source.
    
    return textField.getText() != null  &&  !textField.getText().isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String determineValue(Object source) {
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
  public void setErrorFeedback(boolean valid) {
  }
  
  /**
   * {@inheritDoc}
   * There is no formatting, so no action.
   */
  @Override
  public void redrawValue() {
  }

  @Override
  protected void updateNonSourceControls(Object source) {
    if (source == null) {
      textField.setText(value);
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
    buf.append(", value=").append(value != null ? value : "<null>");
    buf.append(", referenceValue=").append(referenceValue != null ? "\"" + referenceValue + "\"" : "<null>");
    
    return buf.toString();
  }

  @Override
  public void fillControlsWithDefaultValues() {
    textField.setText("");
  }
}
