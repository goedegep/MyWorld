package goedegep.jfx.objectcontrols;

import java.util.List;

import javafx.beans.Observable;
import javafx.scene.Node;

public interface ObjectControlStatus extends Observable  {
  
  /**
   * Get the unique id of the ObjectControl.
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
   * Indication of whether the Object provided by this component is optional or not.
   * <p>
   * This method is identical to ocOptionalProperty().get().
   * 
   * @return true if the Object provided by this component is optional, false if the Object is mandatory.
   */
  boolean isOptional();
  
  /**
   * Indicates whether information is filled in or not.
   * <p>
   * If information is filled in, this may be a valid value but also an invalid value.
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
   * Or: What is filled in can be translated to an object of the type of the control.
   * </li>
   * </ul>
   * 
   * @param errorMessageBuffer A StringBuilder to append a possible error message to. This parameter may be null.
   * @return true if the information is valid, false otherwise.
   */
  boolean isValid();

  /**
   * Check whether the control has changed since the last call to ocSetValue().
   * 
   * @return true if the control has changed since the last call to ocSetValue().
   */
  boolean isChanged();
  
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
   * Remove all listeners, which have been added via {@code addListener()}.
   */
  void removeListeners();

//  /**
//   * A list of {@code ObjectControlStatus} children.
//   * @return
//   */
//  List<ObjectControl> getObjectControls();
}
