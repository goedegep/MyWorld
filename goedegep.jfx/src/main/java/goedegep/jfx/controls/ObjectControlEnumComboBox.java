package goedegep.jfx.controls;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EEnum;

import goedegep.util.emf.EnumTextConverter;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;


/**
 * This class provides a ComboBox ObjectControl for an Enum.
 * <p>
 * It is possible to specify a 'not set value', for which a null String will be used. Enums often have a 'not set value' like e.g. NOT_SET (with in case of en EMF EEnum a literal '<not-set>').
 * The '<not-set>' doesn't look nice in the values of the ComboBox.
 * <p>
 * What kind of texts are shown in the ComboBox depends on the constructor used:
 * <ul>
 * <li>
 * If nothing is specified, the text for an enum constant is its name.
 * </li>
 * <li>
 * When you specify an EEnum in the constructor, the literals of the enum constants are used.
 * </li>
 * <li>
 * If you provide a Map for the mapping of the enum constants to their texts, then this mapping will be used.
 * </li>
 * </ul>
 * 
 * @param <T> The Enum type.
 */
public class ObjectControlEnumComboBox<T extends Enum<T>> extends ComboBox<String> implements ObjectControl<T> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlEnumComboBox.class.getName());
  
  /**
   * Provides the set of text values for the enum constants and provides converstion from enum constant to text and vice versa.
   */
  private EnumTextConverter<T> enumTextConverter;
  
  private BooleanProperty isValidProperty = new SimpleBooleanProperty(true);
  private BooleanProperty isFilledInProperty = new SimpleBooleanProperty(true);
  private boolean isOptional;
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  /**
   * Constructor
   * <p>
   * With this constructor the text for an enum constant is its name.
   * 
   * @param enumConstant A single enum constant of the enum.
   * @param notSetValue The 'not set' value.
   * @param isOptional indicates whether the value is optional or not
   * @param toolTipText an optional tooltip text
   */
  public ObjectControlEnumComboBox(T enumConstant, T notSetValue, boolean isOptional, String toolTipText) {
    enumTextConverter = new EnumTextConverter<T>(enumConstant, notSetValue);
    
    init(isOptional, toolTipText);
  }
  
  /**
   * Constructor
   * <p>
   * With this constructor the text for an enum constant is provided by the  <code>eEnum</code>.
   * 
   * @param enumConstant A single enum constant of the enum.
   * @param notSetValue The 'not set' value.
   * @param eEnum an EEnum providing the literal values for the texts for the constants.
   * @param isOptional indicates whether the value is optional or not
   * @param toolTipText an optional tooltip text
   */
  public ObjectControlEnumComboBox(T enumConstant, T notSetValue, EEnum eEnum, boolean isOptional, String toolTipText) {
    enumTextConverter = new EnumTextConverter<T>(enumConstant, notSetValue, eEnum);
    
    init(isOptional, toolTipText);
  }
  
  /**
   * Constructor
   * <p>
   * With this constructor the text for an enum constant is the literal for the constant as given by the <code>enumToStringMap</code>.
   * 
   * @param enumConstant A single enum constant of the enum.
   * @param notSetValue The 'not set' value.
   * @param enumToStringMap provides the names for the enum constants.
   * @param isOptional indicates whether the value is optional or not
   * @param toolTipText an optional tooltip text
   */
  public ObjectControlEnumComboBox(T enumConstant, T notSetValue, Map<T, String> enumToStringMap, boolean isOptional, String toolTipText) {
    enumTextConverter = new EnumTextConverter<T>(enumConstant, notSetValue, enumToStringMap);
    
    init(isOptional, toolTipText);
  }
  
  /**
   * Handle initialization.
   * 
   * @param isOptional indicates whether the value is optional or not
   * @param toolTipText an optional tooltip text
   */
  private void init(boolean isOptional, String toolTipText) {
    this.isOptional = isOptional;
    
    getItems().addAll(enumTextConverter.getStringValues());
    
    valueProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
       LOGGER.info(newValue != null ? newValue.toString() : "null");
        
       notifyListeners();
      }
      
    });
            
    if (toolTipText != null) {
      setTooltip(new Tooltip(toolTipText));
    }
    
    this.setOnAction(event -> checkOnValid());
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
    String stringValue = getValue();
    T enumValue = enumTextConverter.getEnumForString(stringValue);
    return enumValue;
  }
  
  @Override
  public void setObjectValue(T objectValue) {
    setValue(enumTextConverter.getStringValue(objectValue));
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

}