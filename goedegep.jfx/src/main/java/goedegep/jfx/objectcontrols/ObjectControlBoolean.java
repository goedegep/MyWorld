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
   * Indicates whether the control is optional (if true) or mandatory.
   */
  private BooleanProperty optionalProperty = new SimpleBooleanProperty(false);
  private BooleanProperty isValidProperty = new SimpleBooleanProperty(true);
  private BooleanProperty isFilledInProperty = new SimpleBooleanProperty(true);
  private ObjectProperty<Boolean> objectValueProperty = new SimpleObjectProperty<>(false);
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  public ObjectControlBoolean(String text, boolean selected, boolean isOptional, String toolTipText) {
    super(text);
    
    setSelected(selected);
    objectValueProperty.set(selected);
    
    optionalProperty.set(isOptional);
    
    selectedProperty().addListener(new ChangeListener<Boolean>() {
      
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        objectValueProperty.set(newValue);
        notifyListeners();
      }
      
    });
    
    if (toolTipText != null) {
      setTooltip(new Tooltip(toolTipText));
    }
    //this.onInputMethodTextChangedProperty()
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

  @Override
  public boolean getIsFilledIn() {
    return true;
  }

  @Override
  public boolean getIsValid(StringBuilder buf) {
    return isValidProperty.get();
  }

  @Override
  public Boolean getObjectValue() {
    return isSelected();
  }

  @Override
  public void setObjectValue(Boolean objectValue) {
    setSelected(objectValue);    
  }
  
  @Override
  public ObjectProperty<Boolean> objectValue() {
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
  
  /**
   * Notify the <code>invalidationListeners</code> that something has changed.
   */
  private void notifyListeners() {
    for (InvalidationListener invalidationListener: invalidationListeners) {
      invalidationListener.invalidated(this);
    }
  }

}
