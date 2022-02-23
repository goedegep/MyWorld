/**
 */
package goedegep.vacations.checklist.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Labels List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.checklist.model.VacationChecklistLabelsList#getVacationChecklistLabels <em>Vacation Checklist Labels</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getVacationChecklistLabelsList()
 * @model
 * @generated
 */
public interface VacationChecklistLabelsList extends EObject {
  /**
   * Returns the value of the '<em><b>Vacation Checklist Labels</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.vacations.checklist.model.VacationChecklistLabel}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Vacation Checklist Labels</em>' containment reference list.
   * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getVacationChecklistLabelsList_VacationChecklistLabels()
   * @model containment="true"
   * @generated
   */
  EList<VacationChecklistLabel> getVacationChecklistLabels();

} // VacationChecklistLabelsList
