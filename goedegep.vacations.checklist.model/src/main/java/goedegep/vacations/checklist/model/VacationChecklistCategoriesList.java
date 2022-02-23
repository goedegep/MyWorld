/**
 */
package goedegep.vacations.checklist.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Categories List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.checklist.model.VacationChecklistCategoriesList#getVacationChecklistCategories <em>Vacation Checklist Categories</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getVacationChecklistCategoriesList()
 * @model
 * @generated
 */
public interface VacationChecklistCategoriesList extends EObject {
  /**
   * Returns the value of the '<em><b>Vacation Checklist Categories</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.vacations.checklist.model.VacationChecklistCategory}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Vacation Checklist Categories</em>' containment reference list.
   * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getVacationChecklistCategoriesList_VacationChecklistCategories()
   * @model containment="true"
   * @generated
   */
  EList<VacationChecklistCategory> getVacationChecklistCategories();

} // VacationChecklistCategoriesList
