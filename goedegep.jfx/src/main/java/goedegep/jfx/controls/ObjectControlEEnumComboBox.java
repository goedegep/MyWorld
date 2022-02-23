package goedegep.jfx.controls;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.util.StringConverter;


/**
 * This class provides a ComboBox ObjectInput for an EEnum.
 * 
 * @param <T> The Enum type.
 */
public class ObjectControlEEnumComboBox<T extends Enumerator> extends ComboBox<Enumerator> implements ObjectControl<T> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlEEnumComboBox.class.getName());
  
  private BooleanProperty isValidProperty = new SimpleBooleanProperty(true);
  private BooleanProperty isFilledInProperty = new SimpleBooleanProperty(true);
//  private ObjectProperty<T> valueProperty = new SimpleObjectProperty<>(null);
  private boolean isOptional;
//  private List<ChangeListener<? super T>> changeListeners = new ArrayList<>();
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  public ObjectControlEEnumComboBox(EEnum eEnum, boolean isOptional, String toolTipText) {
    super();
    
    ObservableList<Enumerator> items = FXCollections.observableArrayList();
    for (EEnumLiteral eEnumLiteral: eEnum.getELiterals()) {
      LOGGER.info("eEnumLiteral: " + eEnumLiteral);
      items.add(eEnumLiteral.getInstance());
    }
    this.setItems(items);
    this.setConverter(new StringConverter<Enumerator>() {

      @Override
      public String toString(Enumerator object) {
        if (object != null) {
          return object.getLiteral();
        } else {
          return null;
        }
      }

      @Override
      public EEnumLiteral fromString(String string) {
        return eEnum.getEEnumLiteral(string);
      }
      
    });
    
    valueProperty().addListener(new ChangeListener<Enumerator>() {

      @Override
      public void changed(ObservableValue<? extends Enumerator> observable, Enumerator oldValue, Enumerator newValue) {
       LOGGER.severe(newValue.toString());
        
       notifyListeners();
      }
      
    });
            
    if (toolTipText != null) {
      setTooltip(new Tooltip(toolTipText));
    }
    
    this.setOnAction(e -> checkOnValid());
    
  }
  
  private void checkOnValid() {
    boolean isValid;
    
    if (isOptional()  &&  !getIsFilledIn()) {
      isValid = true;
    } else {
      isValid = getIsFilledIn();
    }
    
    isValidProperty.set(isValid);
    
    isFilledInProperty.set(getIsFilledIn());
  }

  @Override
  public boolean isOptional() {
    return isOptional;
  }

  @Override
  public boolean getIsFilledIn() {
    return super.getValue() != null;
  }

  @Override
  public boolean getIsValid(StringBuilder buf) {
    return isValidProperty.get();
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
  public T getObjectValue() {
    Enumerator eEnumLiteral = getValue();
    LOGGER.severe("eEnumLiteral: " + eEnumLiteral);
    
    @SuppressWarnings("unchecked")
    T t = (T) eEnumLiteral;
    LOGGER.severe("<= " + t);
    return t;
  }
  
//  public T getValue() {
//    return getObjectValue();
//  }

  @Override
  public void setObjectValue(T objectValue) {
    LOGGER.severe("objectValue: " + objectValue);
    setValue(objectValue);
  }
  
  @Override
  public ObjectProperty<T> objectValue() {
//    return valueProperty();
    
    return null;
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

//  @Override
//  public void addListener(ChangeListener<? super T> listener) {
//    // TODO Auto-generated method stub
//    
//  }
//
//  @Override
//  public void removeListener(ChangeListener<? super T> listener) {
//    // TODO Auto-generated method stub
//    
//  }

}
