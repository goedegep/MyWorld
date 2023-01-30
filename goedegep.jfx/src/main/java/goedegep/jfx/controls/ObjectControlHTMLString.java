package goedegep.jfx.controls;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.web.HTMLEditor;

public class ObjectControlHTMLString extends HTMLEditor implements ObjectControl<String> {
  
  private BooleanProperty isValidProperty = new SimpleBooleanProperty(true);
  private BooleanProperty isFilledInProperty = new SimpleBooleanProperty(false);
  private ObjectProperty<String> objectValueProperty = new SimpleObjectProperty<>();
  private boolean isOptional;
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
      
  public ObjectControlHTMLString(String text, double width, boolean isOptional, String toolTipText, String id) {
    this.isOptional = isOptional;
    if (id != null) {
      setId(id);
    }

    addEventHandler(InputEvent.ANY, new EventHandler<InputEvent>() {

      @Override
      public void handle(InputEvent event) {
        handleChanges(getHtmlText());
      }
    });
    
    handleChanges(getHtmlText());
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
  public boolean isOptional() {
    return isOptional;
  }

  @Override
  public boolean getIsFilledIn() {
    String text = getHtmlText();
    
    return (text != null)  &&  !text.isEmpty();
  }

  @Override
  public boolean getIsValid(StringBuilder errorMessageBuffer) {
    return isValidProperty.get();
  }

  @Override
  public String getObjectValue() {
    String text = getHtmlText();
    
    if (text != null) {
      text = text.trim();
    }
    
    return text;
  }

  @Override
  public void setObjectValue(String objectValue) {
    setHtmlText(objectValue);    
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
