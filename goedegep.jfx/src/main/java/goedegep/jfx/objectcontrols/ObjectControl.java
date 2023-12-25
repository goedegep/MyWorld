package goedegep.jfx.objectcontrols;

import javafx.beans.Observable;
import javafx.scene.Node;

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
 * The {@link #isOptional} method can be used to check on this. An implementing class typically provides a constructor with an 'isOptional' parameter.
 * <p>
 * <b>Filled in</b><br/>
 * Indicates that something is entered, it may be valid or not. The {@link #isFilledIn} method can be used to check on this.
 * <p>
 * <b>Valid</b><br/>
 * An edit window typically has several controls. A 'save' button is often only enabled if all required values are valid.<br/>
 * Valid is defined as:
 * <ul>
 * <li>
 * Either: the control is optional and nothing is filled in
 * </li>
 * <li>
 * Or: What is filled in can be translated to an object of the type of the control.
 * </li>
 * </ul>
 * The {@link #isValid} method can be used to check on this.<br/><br/>
 * <p>
 * <b>The Value</b><br/>
 * The value (of type T) represented by the control can be set via {@link #setValue} and obtained via {@link #getValue}.
 * <p>
 * <b>Changes</b><br/>
 * In an editor you usually provide feedback to the user whether he has changed something or not. Therefore this interface provides support for detecting changes.
 * When the value of the control is programmatically changed (via {@code setValue()}) this value is stored as a reference value. Any call to {@link #isChanged()} returns true
 * if the current value of the control differs from this reference value, otherwise it return false.
 * <p>
 * <b>The GUI controls</b>
 * There is of course always at least one GUI control representing the value, e.g. a TextField. This control can be obtained via {@link getControl}.<br/>
 * Besides this there is a status indicator control, which can be obtained via {@link getStatusIndicator}.<br/>
 * If an ObjectControl has more GUI controls (like e.g. the ObjectControlFileSelecter) this control will provide extra methods to obtain these controls.
 * <p>
 * <b>Textual representations</b><br/>
 * As the control often already represents the value in a textual form, this text can also be obtained via {@link #getValueAsFormattedText()}.<br/>
 * In case of an error an error message can be obtained via {@link #getErrorText()}.
 * <p>
 * <b>Id</b><br/>
 * To identify your controls, you can set an Id. See the methods {@link #getId()} and {@link #setId()}.
 * <p>
 * <b>Listeners</b>
 * This interface extends the {@link Observable} interface. It notifies listeners upon any change in the control.<br/>
 * For convenience there is an extra method {@link #removeListeners()} to remove all listeners.
 * <b>How to write an ObjectControl</b>
 * Extend the class ObjectControlAbstract and follow the documentation within that class.
 * 
 * @param T The value type handled by the control
 */
public interface ObjectControl<T extends Object> extends Observable {
    
  /**
   * Indication of whether the Object provided by this component is optional or not.
   * <p>
   * This method is identical to ocOptionalProperty().get().
   * 
   * @return true if the Object provided by this component is optional, false if the Object is mandatory.
   */
  public boolean isOptional();
  
  /**
   * Indicates whether information is filled in or not.
   * <p>
   * If information is filled in, this may be a valid value but also an invalid value.
   * 
   * @return true if something is filled in, false otherwise.
   */
  public boolean isFilledIn();

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
  public boolean isValid();
  
  /**
   * Get the current Object value.
   * 
   * @return the Object value.
   */
  public T getValue();
  
  /**
   * Set the Object value.
   * 
   * @param objectValue the value to set the Object to.
   */
  public void setValue(final T objectValue);
  
  
  /**
   * Get a node which represents the status of the control.
   * 
   * @return a node which represents the status of the control.
   */
  public Node getStatusIndicator();
  
  /**
   * Get the primary control.
   * 
   * @return the primary control.
   */
  Node getControl();
  
  /**
   * Get a text which explains why the control has no valid value.
   * 
   * @return a text which explains why the control has no valid value.
   */
  public String getErrorText();
  
  /**
   * Get the unique id of the ObjectControl.
   * 
   * @return the unique id of the ObjectControl, or null if this hasn't been set.
   */
  public String getId();
  
  /**
   * Set the unique id of the ObjectControl.
   * 
   * @param id the unique id for the ObjectControl.
   */
  public void setId(String id);
  
  /**
   * Get the object value as formatted text.
   * 
   * @return the object value as formatted text.
   */
  String getValueAsFormattedText();

  /**
   * Check whether the control has changed since the last call to ocSetValue().
   * 
   * @return true if the control has changed since the last call to ocSetValue().
   */
  public boolean isChanged();
  
  /**
   * Remove all listeners, which have been added via {@code addListener()}.
   */
  public void removeListeners();
}
