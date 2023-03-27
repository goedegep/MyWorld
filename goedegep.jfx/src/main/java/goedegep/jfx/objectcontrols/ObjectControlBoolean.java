package goedegep.jfx.objectcontrols;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;


public class ObjectControlBoolean extends CheckBox implements ObjectControl<Boolean> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlBoolean.class.getName());
  
  /**
   * Indication of whether the control is optional (if true) or mandatory.
   */
  private boolean optional;
//  private BooleanProperty ocOptionalProperty = new SimpleBooleanProperty(false);
//  
//  /**
//   * Indication of whether the control is filled-in or not.
//   */
//  private BooleanProperty ocFilledInProperty = new SimpleBooleanProperty(true);
//  
//  /**
//   * Indication of whether the control has a valid value or not.
//   */
//  private BooleanProperty ocValidProperty = new SimpleBooleanProperty(true);
//  
//  /**
//   * The current value.
//   */
//  private ObjectProperty<Boolean> ocValueProperty = new SimpleObjectProperty<>(false);
  
  /**
   * The invalidation listeners
   */
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  public ObjectControlBoolean(String text, boolean selected, boolean isOptional, String toolTipText) {
    super(text);
    
    setSelected(selected);
//    ocValueProperty.set(selected);
    
    optional = isOptional;
//    ocOptionalProperty.set(isOptional);
    
    selectedProperty().addListener((o)-> ociHandleNewUserInput());
    
    if (toolTipText != null) {
      setTooltip(new Tooltip(toolTipText));
    }
    //this.onInputMethodTextChangedProperty()
  }

  /**
   * {@inheritDoc}
   */
//  @Override
//  public BooleanProperty ocOptionalProperty() {
//    return ocOptionalProperty;
//  }
//
//  @Override
//  public void ocSetValue(Boolean objectValue) {
//    setSelected(objectValue);    
//  }
//  
//  @Override
//  public ObjectProperty<Boolean> ocValueProperty() {
//    return ocValueProperty;
//  }
//
//  @Override
//  public BooleanProperty ocValidProperty() {
//    return ocValidProperty;
//  }
//
//  @Override
//  public BooleanProperty ocFilledInProperty() {
//    return ocFilledInProperty;
//  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<InvalidationListener> ociGetInvalidationListeners() {
    return invalidationListeners;
  }

  /**
   * {@inheritDoc}
   * A checkbox is always filled in.
   */
  @Override
  public boolean ociDetermineFilledIn() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean ociDetermineValue() {
    return isSelected();
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

  /**
   * {@inheritDoc}
   * Not very useful for a boolean, but we just return 'true' or 'false'.
   */
  @Override
  public String ocGetObjectValueAsFormattedText() {
    return isSelected() ? "true" : "false";
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
  public Boolean ocGetValue() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void ocSetValue(Boolean objectValue) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void ociSetValue(Boolean value) {
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
