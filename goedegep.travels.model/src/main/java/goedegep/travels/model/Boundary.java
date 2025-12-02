/**
 */
package goedegep.travels.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import goedegep.geo.WGS84Coordinates;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Boundary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.travels.model.Boundary#getPoints <em>Points</em>}</li>
 * </ul>
 *
 * @see goedegep.travels.model.TravelsPackage#getBoundary()
 * @model
 * @generated
 */
public interface Boundary extends EObject {
  /**
   * Returns the value of the '<em><b>Points</b></em>' attribute list.
   * The list contents are of type {@link goedegep.geo.WGS84Coordinates}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Points</em>' attribute list.
   * @see goedegep.travels.model.TravelsPackage#getBoundary_Points()
   * @model dataType="goedegep.types.model.EWGS84Coordinates"
   * @generated
   */
  EList<WGS84Coordinates> getPoints();

} // Boundary
