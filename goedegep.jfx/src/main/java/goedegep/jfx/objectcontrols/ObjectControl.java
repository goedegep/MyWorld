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
    
  /**
   * Indication of whether the Object provided by this component is optional or not.
   * <p>
   * This method is identical to ocOptionalProperty().get().
   * 
   * @return true if the Object provided by this component is optional, false if the Object is mandatory.
   */
  public boolean ocIsOptional();
  
  /**
   * Indicates whether information is filled in or not. The value returned is the value of the isFilledIn property.
   * <p>
   * If information is filled in, this may be a valid value but also an invalid value.
   * This method is identical to ocFilledInProperty().get().
   * 
   * @return true if something is filled in, false otherwise.
   */
  public boolean ocIsFilledIn();

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
  
  /**
   * Get the current Object value.
   * 
   * @return the Object value.
   */
  public T ocGetValue();
  
  /**
   * Set the Object value.
   * 
   * @param objectValue the value to set the Object to.
   */
  public void ocSetValue(final T objectValue);
  
  
  /**
   * Get a node which represents the status of the control.
   * 
   * @return a node which represents the status of the control.
   */
  public Node ocGetStatusIndicator();
  
  /**
   * Get the primary control.
   * 
   * @return the primary control.
   */
  Node ocGetControl();
  
  /**
   * Get a text which explains why the control has no valid value.
   * 
   * @return a text which explains why the control has no valid value.
   */
  public String ocGetErrorText();
  
  /**
   * Get the unique id of the ObjectControl.
   * 
   * @return the unique id of the ObjectControl, or null if this hasn't been set.
   */
  public String ocGetId();
  
  /**
   * Set the unique id of the ObjectControl.
   * 
   * @param id the unique id for the ObjectControl.
   */
  public void ocSetId(String id);
  
  /**
   * Get the object value as formatted text.
   * 
   * @return the object value as formatted text.
   */
  String ocGetObjectValueAsFormattedText();

  /**
   * Check whether the control has changed since the last call to ocSetValue().
   * 
   * @return true if the control has changed since the last call to ocSetValue().
   */
  public boolean ocIsChanged();
  
  /**
   * Remove all listeners, which have been added via {@code addListener()}.
   */
  public void removeListeners();
}
