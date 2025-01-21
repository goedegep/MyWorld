/**
 */
package goedegep.vacations.model;

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
 *   <li>{@link goedegep.vacations.model.DayTrip#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getDayTrip()
 * @model
 * @generated
 */
public interface DayTrip extends Travel {
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

} // DayTrip
