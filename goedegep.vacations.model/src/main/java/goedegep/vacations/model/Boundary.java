/**
 */
package goedegep.vacations.model;

import goedegep.geo.dbl.WGS84Coordinates;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Boundary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.Boundary#getPoints <em>Points</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getBoundary()
 * @model
 * @generated
 */
public interface Boundary extends EObject {
  /**
   * Returns the value of the '<em><b>Points</b></em>' attribute list.
   * The list contents are of type {@link goedegep.geo.dbl.WGS84Coordinates}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Points</em>' attribute list.
   * @see goedegep.vacations.model.VacationsPackage#getBoundary_Points()
   * @model dataType="goedegep.types.model.EWGS84Coordinates"
   * @generated
   */
  EList<WGS84Coordinates> getPoints();

} // Boundary
