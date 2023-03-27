package goedegep.jfx.objectcontrols;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextArea;

public class ObjectControlMultiLineString extends TextArea implements ObjectControl<String> {
  
  
  /**
   * Indication of whether the control is optional (if true) or mandatory.
   */
  private BooleanProperty ocOptionalProperty = new SimpleBooleanProperty(false);
  
  /**
   * Indication of whether the control is filled-in or not.
   */
  private BooleanProperty ocFilledInProperty = new SimpleBooleanProperty(true);
  
  /**
   * Indication of whether the control has a valid value or not.
   */
  private BooleanProperty ocValidProperty = new SimpleBooleanProperty(true);
  
  /**
   * The current value.
   */
  private ObjectProperty<String> ocValueProperty = new SimpleObjectProperty<>();
  
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  
  /**
   * Constructor.
   * 
   * @param text Initial value to set the text to (may be null).
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlMultiLineString(String text, double width, boolean isOptional, String toolTipText) {
    ocOptionalProperty.set(isOptional);

    textProperty().addListener((observableValue, oldValue, newValue) -> ociHandleNewUserInput());

    setText(text);
  }

//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public BooleanProperty ocOptionalProperty() {
//    return ocOptionalProperty;
//  }
//  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public BooleanProperty ocValidProperty() {
//    return ocValidProperty;
//  }
//  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public BooleanProperty ocFilledInProperty() {
//    return ocFilledInProperty;
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public ObjectProperty<String> ocValueProperty() {
//    return ocValueProperty;
//  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ocSetValue(String objectValue) {
    setText(objectValue);    
  }
  
//  private void handleChanges(final String newValue) {
//    ocValueProperty.set(newValue);
//    
//    if (!ocIsOptional()  &&  !isFilledIn()) {
//      ocValidProperty.set(false);
//    } else {
//      ocValidProperty.set(true);
//    }
//    
//    ocFilledInProperty.set(isFilledIn());
//    
//    notifyListeners();
//  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ociDetermineFilledIn() {
    return getText() != null  &&  !getText().isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String ociDetermineValue() {
    String text = getText();
    
    if (text != null) {
      text = text.trim();
    }
    
    return text;
  }
  
  /**
   * {@inheritDoc}
   * The text is always valid, so no action.
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
  
  /**
   * {@inheritDoc}
   * There is no formatting, so just return the value.
   */
  @Override
  public String ocGetObjectValueAsFormattedText()  {
    return ocValueProperty.get();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<InvalidationListener> ociGetInvalidationListeners() {
    return invalidationListeners;
  }

  @Override
  public boolean ocIsOptional() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean ocIsFilledIn() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean ocIsValid() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String ocGetValue() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void ociSetValue(String value) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void ociSetValid(boolean valid) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void ociSetFilledIn(boolean filledIn) {
    // TODO Auto-generated method stub
    
  }

}
