package goedegep.jfx.editor;

import java.util.function.Supplier;

import goedegep.util.listener.ValueAndOrStatusChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Common interface for all editor components (EditorControl, EditorMultiControl and EditPanel).
 * <p>
 * This interface supports the following.
 * 
 * <h4>Object identification</h4>
 * All objects can have an identification string (id), which is mainly used for debugging.
 * See {@link #setId()} and {@link #getId()}.
 * 
 * <h4>Optional versus mandatory</h4>
 * Any value in the editor is either optional or not, in which case it is mandatory. Optional means that no value has to be provided.
 * This is something that doesn't change while the editor is active.
 * Therefore the 'optional' indication shall be set via the constructor of any implementation. The value can be obtained via {@link #isOptional()}.
 * 
 * <h4>Filled in</h4>
 * 'Filled in' means that something is entered in a component, it may be valid or not. The {@link #isFilledIn()} method can be used to check on this.
 * 
 * <h4>Valid</h4>
 * Indication of whether the information in a component is valid or not.
 * Where valid is defined as:
 * <ul>
 * <li>
 * Either: the component is optional and nothing is filled in
 * </li>
 * <li>
 * Or: What is filled in can be translated to a legal object of the type of the component.
 * </li>
 * </ul>
 * The method {@link #isValid()} can be used to obtain this information. 
 * 
 * <h4>Label</h4>
 * An editor component provides a {@link Label} via the method {@link #getLabel()}.<br/>
 * The text of the label is: 'label base text' + {@link #MANDATORY_SYMBOL} + ':'.<br/>
 * Where:
 * <ul>
 * <li>the 'label base text' can be set via {@link #setLabelBaseText()}.</li>
 * <li>the MANDATORY_SYMBOL is only there for mandatory (non optional) values</li>
 * </ul>
 * 
 * <h4>Values</h4>
 * There's a difference between an {@code EditorControl} and an {@code EditorMultiControl} or {@code EditPanel}.
 * 
 * <h5>EditorControl</h5>
 * An {@code EditorControl} is used to show and change a simple object like a {@code String}, {@code Date} or {@code Integer}.
 * These are usually unmodifiable objects. So the {@code EditorControl} never changes the set value.
 * <ul>
 * <li>Setting the value<br/>
 * The method to programmatically set the value is {@link EditorControl#setObject}. Note that the value parameter is final.
 * The internal value of the control is set to this value and the GUI component(s) will represent this value.
 * </li>
 * <li>User edits<br/>
 * The user edits the value via the GUI component(s). This updates the internal value of the control.
 * <li>Getting the value<br/>
 * Getting the value is done via the method {@link EditorControl#getValue}. This returns a new object with the current value of the control.
 * </li>
 * <li>Get the current value<br/>
 * This interface provides a method {@link #getCurrentValue}, which in this case is the same as {@link EditorControl#getValue}.
 * </li>
 * </ul>
 * 
 * 
 * <h5>EditorMultiControl and EditPanel</h5>
 * These components are used to show and change a complex object, an object with attributes and/or sub classes.
 * These are assumed to be modifiable objects. So these components change the set object.
 * <ul>
 * <li>Setting the value<br/>TODO
 * The method to programmatically set the value is {@link EditorControl#setObject}. Note that the value parameter is final.
 * The internal value of the control is set to this value and the GUI component(s) will represent this value.
 * </li>
 * <li>User edits<br/>TODO
 * The user edits the value via the GUI component(s). This updates the internal value of the control.
 * <li>Getting the value<br/>
 * Getting the value is done via the method {@link EditorControl#getValue}. This returns a new object with the current value of the control.
 * </li>
 * <li>Get the current value<br/>TODO
 * This interface provides a method {@link #getCurrentValue}, which in this case is the same as {@link EditorControl#getValue}.
 * </li>
 * </ul>
 * <p>
 * 
 * @param <T> the data type handled by the component.
 */
public interface EditorComponent<T> {
  final static String MANDATORY_SYMBOL = "*";
  
  /**
   * Get the unique id of the object.
   * 
   * @return the unique id of the ObjectControl, or null if this hasn't been set.
   */
  String getId();
  
  /**
   * Set the unique id of the ObjectControl.
   * 
   * @param id the unique id for the ObjectControl.
   */
  void setId(String id);

  /**
   * Indication of whether the Object provided by this component is optional or not (mandatory).
   * 
   * @return true if the Object provided by this component is optional, false if the Object is mandatory.
   */
  boolean isOptional();
  
  /**
   * Indicates whether information is filled in or not.
   * <p>
   * If information is filled in, this may be a legal value but also an illegal value.
   * 
   * @return true if something is filled in, false otherwise.
   */
  boolean isFilledIn();

  /**
   * Indicates whether the information is valid or not.
   * Where valid is defined as:
   * <ul>
   * <li>
   * Either: the control is optional and nothing is filled in
   * </li>
   * <li>
   * Or: What is filled in can be translated to a legal object of the type of the control.
   * </li>
   * </ul>
   * 
   * @return true if the information is valid, false otherwise.
   */
  boolean isValid();

  /**
   * Check whether the control has changed since the last call to setValue().
   * 
   * @return true if the control has changed since the last call to setValue().
   */
  boolean isChanged();
  
  String getStatusSymbol();
  
  /**
   * Get a node which represents the status of the control.
   * 
   * @return a node which represents the status of the control.
   */
  Node getStatusIndicator();
  
  /**
   * Get a text which explains why the control has no valid value.
   * 
   * @return a text which explains why the control has no valid value.
   */
  String getErrorText();
  
  /**
   * Set the supplier for providing the error text, which is obtained by {@link #getErrorText()}.
   * 
   * @param errorTextSupplier a method which provides the error text.
   */
  void setErrorTextSupplier(Supplier<String> errorTextSupplier);
  
  /**
   * Add a listener for value and/or status changes.
   * 
   * @param valueAndOrStatusChangeListener the listener called on a value and/or status change.
   */
  void addValueAndOrStatusChangeListener(ValueAndOrStatusChangeListener valueAndOrStatusChangeListener);
  
  /**
   * Remove a listener for value and/or status changes.
   * 
   * @param valueAndOrStatusChangeListener the listener to be removed.
   */
  void removeValueAndOrStatusChangeListener(ValueAndOrStatusChangeListener valueAndOrStatusChangeListener);
  
  /**
   * Remove all listeners for value and/or status changes.
   */
  void removeValueAndOrStatusChangeListeners();
  
  /**
   * Create the GUI controls and perform any further initialization.
   */
  void createControls();
  
  /**
   * Get the main GUI control.
   * <p>
   * If an implementation has more than one GUI control, separate methods to obtain these have to be provided.
   * 
   * @return the main GUI control.
   */
  Node getControl();
  
  /**
   * Set the basis for the label text. If the component is mandatory, a '*' is added. After this a ':' is added.
   * 
   * @param labelBaseText the basis for the label text.
   */
  void setLabelBaseText(String labelBaseText);

  /**
   * Get the {@code Label} describing the control.
   */
  Label getLabel();
  
  /**
   * Get the value currently represented by the controls. The object value is not changed.
   * 
   * @return the value currently represented by the controls.
   */
  T getCurrentValue() throws EditorException;
  
  /**
   * Get the value as formatted text.
   * <p>
   * This shall provide a textual representation of the value that can be used in the GUI or at least for debugging purposes.
   * This is specifically useful for components that use a formatter, which is then used to provide this text. Then the client doesn't need its own formatter.
   * 
   * @return the value as formatted text.
   */
  String getValueAsFormattedText();
}
