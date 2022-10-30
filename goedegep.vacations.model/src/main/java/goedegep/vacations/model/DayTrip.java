/**
 */
package goedegep.vacations.model;

import goedegep.types.model.Event;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Day Trip</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.DayTrip#getTitle <em>Title</em>}</li>
 *   <li>{@link goedegep.vacations.model.DayTrip#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getDayTrip()
 * @model
 * @generated
 */
public interface DayTrip extends Event {
  /**
   * Returns the value of the '<em><b>Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #setTitle(String)
   * @see goedegep.vacations.model.VacationsPackage#getDayTrip_Title()
   * @model unsettable="true"
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link goedegep.vacations.model.DayTrip#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Title</em>' attribute.
   * @see #isSetTitle()
   * @see #unsetTitle()
   * @see #getTitle()
   * @generated
   */
  void setTitle(String value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.model.DayTrip#getTitle <em>Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  void unsetTitle();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.model.DayTrip#getTitle <em>Title</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Title</em>' attribute is set.
   * @see #unsetTitle()
   * @see #getTitle()
   * @see #setTitle(String)
   * @generated
   */
  boolean isSetTitle();

  /**
   * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.vacations.model.VacationElement}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elements</em>' containment reference list.
   * @see goedegep.vacations.model.VacationsPackage#getDayTrip_Elements()
   * @model containment="true"
   * @generated
   */
  EList<VacationElement> getElements();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation"
   * @generated
   */
  String getId();

} // DayTrip
