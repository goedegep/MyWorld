/**
 */
package goedegep.events.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.events.model.EventsPackage
 * @generated
 */
public interface EventsFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  EventsFactory eINSTANCE = goedegep.events.model.impl.EventsFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Events</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Events</em>'.
   * @generated
   */
  Events createEvents();

  /**
   * Returns a new object of class '<em>Event Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Event Info</em>'.
   * @generated
   */
  EventInfo createEventInfo();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  EventsPackage getEventsPackage();

} //EventsFactory
