/**
 */
package goedegep.events.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Events</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.events.model.Events#getEvents <em>Events</em>}</li>
 * </ul>
 *
 * @see goedegep.events.model.EventsPackage#getEvents()
 * @model
 * @generated
 */
public interface Events extends EObject {
  /**
   * Returns the value of the '<em><b>Events</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.events.model.EventInfo}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Events</em>' containment reference list.
   * @see goedegep.events.model.EventsPackage#getEvents_Events()
   * @model containment="true"
   * @generated
   */
  EList<EventInfo> getEvents();

} // Events
