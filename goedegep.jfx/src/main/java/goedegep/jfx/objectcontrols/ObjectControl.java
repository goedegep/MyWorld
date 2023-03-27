package goedegep.jfx.objectcontrols;

import java.text.ParseException;
import java.util.List;

import javafx.beans.InvalidationListener;
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
 * Each ObjectControl is a node, so it also supports methods like: setId().
 * <p>
 * <b>How to write an ObjectControl</b>
 * <ul>
 * <li>
 * Extend a JavaFx contol, or an existing ObjectControl.
 * </li>
 * <li>
 * Define the Properties: ocOptionalProperty, ocFilledInProperty, ocValidProperty and ocValueProperty (for as far these aren't defined by the control you're extending).
 * </li>
 * <li>
 * Create the first constructor.
 * </li>
 * </ul>
 * 
 */
public interface ObjectControl<T extends Object> extends Observable {
  
  public static final String OK_INDICATOR = "✓";
  public static final String NOK_INDICATOR = "!";
  
//  public ObjectProperty<ObjectControlState<T>> ocStateProperty();
//  
//  public ObjectControlState<T> ocGetState();
  
//  /**
//   * Property that indicates whether the Object provided by this component is optional or not.
//   * <p>
//   * This value of this property is usually set by the application upon creation of the control and then never changed.
//   * The default value is false.
//   * 
//   * @return a Property that indicates whether the Object provided by this component is optional or not.
//   */
//  public BooleanProperty ocOptionalProperty();
  
  /**
   * Indication of whether the Object provided by this component is optional or not.
   * <p>
   * This method is identical to ocOptionalProperty().get().
   * 
   * @return true if the Object provided by this component is optional, false if the Object is mandatory.
   */
  public boolean ocIsOptional();
//  default public boolean ocIsOptional() {
//	  return ocOptionalProperty().get();
//  }

//  /**
//   * Get a <code>BooleanProperty</code> which indicates whether the control is filled-in or not.
//   * 
//   * @return a <code>BooleanProperty</code> which indicates whether the control is filled-in or not.
//   */
//  public BooleanProperty ocFilledInProperty();
  
  /**
   * Indicates whether information is filled in or not. The value returned is the value of the isFilledIn property.
   * <p>
   * If information is filled in, this may be a valid value but also an invalid value.
   * This method is identical to ocFilledInProperty().get().
   * 
   * @return true if something is filled in, false otherwise.
   */
  public boolean ocIsFilledIn();
//  default public boolean ocIsFilledIn() {
//	  return ocFilledInProperty().get();
//  }

//  /**
//   * Get a <code>BooleanProperty</code> which represents the validity of the control.
//   * 
//   * @return a <code>BooleanProperty</code> which represents the validity of the control.
//   */
//  public BooleanProperty ocValidProperty();
  
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
  public boolean ocIsValid();
//  default public boolean ocIsValid() {
//	  return ocValidProperty().get();
//  }
  
//  /**
//   * Get an <code>ObjectProperty</code> which represents the current object value.
//   * 
//   * @return an <code>ObjectProperty</code> which represents the current object value.
//   */
//  public ObjectProperty<T> ocValueProperty();

  /**
   * Get the current Object value.
   * 
   * @return the Object value.
   */
  public T ocGetValue();
//  default public T ocGetValue() {
//    return ocValueProperty().get();
//  }
  
  /**
   * Set the Object value.
   * 
   * @param objectValue the value to set the Object to.
   */
  public void ocSetValue(final T objectValue);
  
  public default Node ocGetValidIndicator() {
    Label validIndicationLabel = new Label(ocIsValid() ? OK_INDICATOR : NOK_INDICATOR);
    addListener((o) -> {
        if (ocIsValid()) {
          validIndicationLabel.setText(OK_INDICATOR);
        } else {
          validIndicationLabel.setText(NOK_INDICATOR);
        }
        
    });
    
    return validIndicationLabel;
  }
  
  /**
   * Get a text which explains why the control has no valid value.
   * 
   * @return a text which explains why the control has no valid value.
   */
  default public String ocGetErrorText() {
	  throw new UnsupportedOperationException();
  }
  
  public String getId();
  
  /*
   * Implementation methods, which may be overwritten.
   */

  /**
   * Handle any new user input (typically registered as listener on changes on the extended control).
   * <p>
   * Properties are set in the following order: ocValueProperty, ocValidProperty, ocFilledInProperty.
   * So when you listen to the ocValidProperty and this changes to true, the ocValueProperty will be valid.
   * Listeners to the contol are notified after the properties have been set.
   */
  default void ociHandleNewUserInput() {
    boolean filledIn = ociDetermineFilledIn();
    boolean dataValid;
    T value;
    
    if (filledIn) {
      value = ociDetermineValue();
      if (value != null) {
        dataValid = true;
      } else {
        dataValid = false;
      }
    } else {
      dataValid = true;
      value = null;
    }
    ociSetErrorFeedback(dataValid);
    
    ociSetValue(value);
    ociSetValid(ociDetermineValidity(filledIn, dataValid));
    ociSetFilledIn(ociDetermineFilledIn());
    
    ociNotifyListeners();
  }
  
  public void ociSetValue(T value);
  
  public void ociSetValid(boolean valid);
  
  public void ociSetFilledIn(boolean filledIn);
  
//  public void setControlState(boolean filledIn, boolean valid, T value);
  
  /**
   * Determine whether something is filled in or not.
   */
  boolean ociDetermineFilledIn();
//  default boolean determineFilledIn() {
//    return false;
//  }
  
//  default boolean isInputValid() {
//    return false;
//  }
  
  /**
   * Determine whether the input is valid or not.
   */
  default boolean ociDetermineValidity(boolean filledIn, boolean dataValid) {
    
    if (ocIsOptional()  &&  !filledIn) {
      return true;
    }
        
    return (dataValid);
  }
  
//  /**
//   * Check whether input data is either null, or has a valid value.
//   * 
//   * @return whether input data is either null, or has a valid value.
//   */
//  default boolean isDataValid() {
//    return false;
//  }
  
  /**
   * Determine the actual value.
   * <p>
   * PRE: control has to be filled in (determineFilledIn() has returned true).
   */
  T ociDetermineValue();
//  default T determineValue() {
//    return null;
//  }
  
  /**
   * Provide feedback to the user if entered data is wrong.
   * <p>
   * Overwrite this method to e.g. change the input text from black to red in case of wrong input.
   * 
   * @param valid if true the data is valid, else it is wrong.
   */
  void ociSetErrorFeedback(boolean valid);
//  default void setErrorFeedback(boolean valid) {
//    
//  }
  
//  /**
//   * Update the error feedback, based on the input being a valid value or not.
//   * <p>
//   * PRE: ocFilledInProperty and ocValidProperty are up to date.
//   */
//  default void updateErrorFeedback() {
//    setErrorFeedback(!ocIsFilledIn()  ||  ocIsValid());
//  }
  
  /**
   * Redraw the value on focus lost.
   * <p>
   * Overwrite this method if you always want to present values in your 'standard' way.
   * E.g.: the user may e.g. enter a date as 14-3-2023, this may be redrawn as 14-03-2023.
   */
  void ociRedrawValue();
//  default void redrawValue() {
//    throw new UnsupportedOperationException();
//  }
  
  public List<InvalidationListener> ociGetInvalidationListeners();

  @Override
  default public void addListener(InvalidationListener listener) {
    ociGetInvalidationListeners().add(listener);    
  }

  @Override
  default public void removeListener(InvalidationListener listener) {
    ociGetInvalidationListeners().remove(listener);    
  }
  
  /**
   * Notify the {@code invalidationListeners} that something has changed.
   */
  default void ociNotifyListeners() {
    for (InvalidationListener invalidationListener: ociGetInvalidationListeners()) {
      invalidationListener.invalidated(this);
    }
  }
  
  /**
   * Get the object value as formatted text.
   * 
   * @return the object value as formatted text.
   */
  String ocGetObjectValueAsFormattedText();
}
