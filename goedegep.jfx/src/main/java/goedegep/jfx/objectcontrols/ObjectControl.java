package goedegep.jfx.objectcontrols;

import java.text.ParseException;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * This interface defines a set of methods for GUI controls to show and enter object values.
 * <p>
 * GUI controls, like TextField, represent the value of an object, but the control itself is unaware of the object type.
 * Therefore a set of controls is defined which are object type aware.<br/>
 * For example, if you want to show a LocalDate as text and allow the user to edit this value:
 * <ul>
 * <li>
 * To show the LocalDate, it has to be formatted to a String.<br/>
 * In this interface this is taken care of by the method {@link #setValue}, where the formatting is to be done by the implementing class.
 * </li>
 * <li>
 * The other way around is even more complicated.<br/>
 * To use the text from the TextField to set a LocalDate, you first have to parse the text to create a LocalDate.
 * In this interface this is taken care of by the method {@link #getValue}, where the parsing is to be done by the implementing class.<br/>
 * But there is more, you want to know whether the user has to enter a value, has entered a value and whether the value is valid. This is discussed below.
 * </li>
 * </ul>
 * <b>Optional</b><br/>
 * An edit window typically has several controls. A 'save' button is often only enabled if all required values are filled in.
 * The {@link #isOptional} method can be used to check on this. An implementing class typically provides a constructor with an 'isOptional' parameter.<br/><br/>
 * 
 * <b>Filled in</b><br/>
 * Indicates that something is entered, it may be valid or not. The {@link #isFilledIn} method can be used to check on this.<br/><br/>
 * 
 * <b>Valid</b><br/>
 * An edit window typically has several controls. A 'save' button is often only enabled if all required values are valid.<br/>
 * Valid is defined as:
 * <ul>
 * <li>
 * Either: the control is optional and nothing is filled in
 * <li/>
 * <li>
 * Or: What is filled in can be translated to an object of the type of the control.
 * </li>
 * </ul>
 * The {@link #isValid} method can be used to check on this.<br/><br/>
 * 
 * For clarity, all method names start with an 'oc' prefix.
 * 
 */
public interface ObjectControl<T extends Object> extends Observable {
  
  public static final String OK_INDICATOR = "✓";
  public static final String NOK_INDICATOR = "!";
  
  /**
   * Property that indicates whether the Object provided by this component is optional or not.
   * <p>
   * This value of this property is usually set by the application upon creation of the control and then never changed.
   * The default value is false.
   * 
   * @return a Property that indicates whether the Object provided by this component is optional or not.
   */
  public BooleanProperty ocOptionalProperty();
  
  /**
   * Indication of whether the Object provided by this component is optional or not.
   * <p>
   * This method is identical to ocOptionalProperty().get().
   * 
   * @return true if the Object provided by this component is optional, false if the Object is mandatory.
   */
  public boolean isOptional();

  /**
   * Get a <code>BooleanProperty</code> which indicates whether the control is filled-in or not.
   * 
   * @return a <code>BooleanProperty</code> which indicates whether the control is filled-in or not.
   */
  public BooleanProperty isFilledIn();
  
  /**
   * Indicates whether information is filled in or not. The value returned is the value of the isFilledIn property.
   * If information is filled in, this may be a valid value but also an invalid value.
   * 
   * @return true if something is filled in, false otherwise.
   */
  public boolean getIsFilledIn();

  /**
   * Get a <code>BooleanProperty</code> which represents the validity of the control.
   * 
   * @return a <code>BooleanProperty</code> which represents the validity of the control.
   */
  public BooleanProperty isValid();
  
  /**
   * Indicates whether the information is valid or not.
   * Where valid is defined as:
   * <ul>
   * <li>
   * Either: the control is optional and nothing is filled in
   * </li>
   * <li>
   * Or: What is filled in can be translated to an object of the type of the control.
   * </li>
   * </ul>
   * 
   * @param errorMessageBuffer A StringBuilder to append a possible error message to. This parameter may be null.
   * @return true if the information is valid, false otherwise.
   */
  public boolean getIsValid(StringBuilder errorMessageBuffer);

  /**
   * Get the Object value, as provided by the user.
   * 
   * @return the Object value.
   */
  public T getObjectValue() throws ParseException;
  
  /**
   * Set the Object value.
   * 
   * @param objectValue the value to set the Object to.
   */
  public void setObjectValue(final T objectValue);
  
  /**
   * Get an <code>ObjectProperty</code> which represents the current object value.
   * 
   * @return an <code>ObjectProperty</code> which represents the current object value.
   */
  public ObjectProperty<T> objectValue();
  
  /**
   * Check that a collection of controls (implementing ObjectInput) all have valid values.
   * 
   * @param objectInputs the controls to be checked.
   * @return true, if all controls have valid values, false otherwise.
   */
  @Deprecated
  public static boolean areControlsValid(ObjectControl<?>... objectInputs) {
    for (ObjectControl<?> objectInput: objectInputs) {
      if (!objectInput.getIsValid(null)) {
        return false;
      }
    }
    return true;
  }
  
  public default Node getValidIndicator() {
    Label validIndicationLabel = new Label(isValid().getValue() ? OK_INDICATOR : NOK_INDICATOR);
    isValid().addListener(new ChangeListener<>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
          validIndicationLabel.setText(OK_INDICATOR);
        } else {
          validIndicationLabel.setText(NOK_INDICATOR);
        }
      }
        
    });
    
    
    
    return validIndicationLabel;
  }
  
  public String getId();

}
