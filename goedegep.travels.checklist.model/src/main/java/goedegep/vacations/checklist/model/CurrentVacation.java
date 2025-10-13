/**
 */
package goedegep.vacations.checklist.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Current Vacation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.checklist.model.CurrentVacation#getSelectedLabels <em>Selected Labels</em>}</li>
 *   <li>{@link goedegep.vacations.checklist.model.CurrentVacation#getItemStatuses <em>Item Statuses</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getCurrentVacation()
 * @model
 * @generated
 */
public interface CurrentVacation extends EObject {
  /**
   * Returns the value of the '<em><b>Selected Labels</b></em>' reference list.
   * The list contents are of type {@link goedegep.vacations.checklist.model.VacationChecklistLabel}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Selected Labels</em>' reference list.
   * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getCurrentVacation_SelectedLabels()
   * @model
   * @generated
   */
  EList<VacationChecklistLabel> getSelectedLabels();

  /**
   * Returns the value of the '<em><b>Item Statuses</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.vacations.checklist.model.ItemStatus}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Item Statuses</em>' containment reference list.
   * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getCurrentVacation_ItemStatuses()
   * @model containment="true"
   * @generated
   */
  EList<ItemStatus> getItemStatuses();

} // CurrentVacation
