/**
 */
package goedegep.vacations.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Travel Categories</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.TravelCategories#getTravelcategories <em>Travelcategories</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.model.VacationsPackage#getTravelCategories()
 * @model
 * @generated
 */
public interface TravelCategories extends EObject {
  /**
   * Returns the value of the '<em><b>Travelcategories</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.vacations.model.TravelCategory}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Travelcategories</em>' containment reference list.
   * @see goedegep.vacations.model.VacationsPackage#getTravelCategories_Travelcategories()
   * @model containment="true"
   * @generated
   */
  EList<TravelCategory> getTravelcategories();

} // TravelCategories
