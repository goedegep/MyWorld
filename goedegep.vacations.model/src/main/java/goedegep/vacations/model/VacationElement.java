/**
 */
package goedegep.vacations.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vacation Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.VacationElement#getChildren <em>Children</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getVacationElement()
 * @model abstract="true"
 * @generated
 */
public interface VacationElement extends EObject {
  /**
   * Returns the value of the '<em><b>Children</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.vacations.model.VacationElement}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Children</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Children</em>' containment reference list.
   * @see goedegep.vacations.model.VacationsPackage#getVacationElement_Children()
   * @model containment="true"
   * @generated
   */
  EList<VacationElement> getChildren();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the day number to which this element belongs.
   * The day number is the sum of all 'days' of all VacationElementDay's up until this element.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Integer getDayNr();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Get the Vacation to which this element belongs.
   * <!-- end-model-doc -->
   * @model kind="operation"
   * @generated
   */
  Vacation getVacation();

} // VacationElement
