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
   * Indicates whether the control is optional (if true) or mandatory.
   */
  private BooleanProperty optionalProperty = new SimpleBooleanProperty(false);
  private BooleanProperty isValidProperty = new SimpleBooleanProperty(true);
  private BooleanProperty isFilledInProperty = new SimpleBooleanProperty(false);
  private ObjectProperty<String> objectValueProperty = new SimpleObjectProperty<>();
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
      
  public ObjectControlMultiLineString(String text, double width, boolean isOptional, String toolTipText, String id) {
    optionalProperty.set(isOptional);
    if (id != null) {
      setId(id);
    }

    textProperty().addListener((observableValue, oldValue, newValue) -> handleChanges(newValue));
    
    handleChanges(textProperty().get());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BooleanProperty ocOptionalProperty() {
    return optionalProperty;
  }

  @Override
  public boolean isOptional() {
    return optionalProperty.get();
  }
  
  private void handleChanges(final String newValue) {
    objectValueProperty.setValue(newValue);
    
    if (!isOptional()  &&  !getIsFilledIn()) {
      isValidProperty.set(false);
    } else {
      isValidProperty.set(true);
    }
    
    isFilledInProperty.set(getIsFilledIn());
    
    notifyListeners();
  }
  
  /**
   * Notify the <code>invalidationListeners</code> that something has changed.
   */
  private void notifyListeners() {
    for (InvalidationListener invalidationListener: invalidationListeners) {
      invalidationListener.invalidated(this);
    }
  }

  @Override
  public boolean getIsFilledIn() {
    String text = getText();
    
    return (text != null)  &&  !text.isEmpty();
  }

  @Override
  public boolean getIsValid(StringBuilder errorMessageBuffer) {
    return isValidProperty.get();
  }

  @Override
  public String getObjectValue() {
    String text = getText();
    
    if (text != null) {
      text = text.trim();
    }
    
    return text;
  }

  @Override
  public void setObjectValue(String objectValue) {
    setText(objectValue);    
  }

  @Override
  public ObjectProperty<String> objectValue() {
    return objectValueProperty;
  }

  @Override
  public BooleanProperty isValid() {
    return isValidProperty;
  }

  @Override
  public BooleanProperty isFilledIn() {
    return isFilledInProperty;
  }

  @Override
  public void addListener(InvalidationListener listener) {
    invalidationListeners.add(listener);    
  }

  @Override
  public void removeListener(InvalidationListener listener) {
    invalidationListeners.remove(listener);    
  }

}
