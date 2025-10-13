/**
 */
package goedegep.vacations.checklist.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vacation Checklist</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.checklist.model.VacationChecklist#getVacationChecklistLabelsList <em>Vacation Checklist Labels List</em>}</li>
 *   <li>{@link goedegep.vacations.checklist.model.VacationChecklist#getVacationChecklistCategoriesList <em>Vacation Checklist Categories List</em>}</li>
 *   <li>{@link goedegep.vacations.checklist.model.VacationChecklist#getCurrentVacation <em>Current Vacation</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getVacationChecklist()
 * @model
 * @generated
 */
public interface VacationChecklist extends EObject {
  /**
   * Returns the value of the '<em><b>Vacation Checklist Labels List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Vacation Checklist Labels List</em>' containment reference.
   * @see #setVacationChecklistLabelsList(VacationChecklistLabelsList)
   * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getVacationChecklist_VacationChecklistLabelsList()
   * @model containment="true" required="true"
   * @generated
   */
  VacationChecklistLabelsList getVacationChecklistLabelsList();

  /**
   * Sets the value of the '{@link goedegep.vacations.checklist.model.VacationChecklist#getVacationChecklistLabelsList <em>Vacation Checklist Labels List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Vacation Checklist Labels List</em>' containment reference.
   * @see #getVacationChecklistLabelsList()
   * @generated
   */
  void setVacationChecklistLabelsList(VacationChecklistLabelsList value);

  /**
   * Returns the value of the '<em><b>Vacation Checklist Categories List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Vacation Checklist Categories List</em>' containment reference.
   * @see #setVacationChecklistCategoriesList(VacationChecklistCategoriesList)
   * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getVacationChecklist_VacationChecklistCategoriesList()
   * @model containment="true" required="true"
   * @generated
   */
  VacationChecklistCategoriesList getVacationChecklistCategoriesList();

  /**
   * Sets the value of the '{@link goedegep.vacations.checklist.model.VacationChecklist#getVacationChecklistCategoriesList <em>Vacation Checklist Categories List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Vacation Checklist Categories List</em>' containment reference.
   * @see #getVacationChecklistCategoriesList()
   * @generated
   */
  void setVacationChecklistCategoriesList(VacationChecklistCategoriesList value);

  /**
   * Returns the value of the '<em><b>Current Vacation</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Current Vacation</em>' containment reference.
   * @see #setCurrentVacation(CurrentVacation)
   * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getVacationChecklist_CurrentVacation()
   * @model containment="true" required="true"
   * @generated
   */
  CurrentVacation getCurrentVacation();

  /**
   * Sets the value of the '{@link goedegep.vacations.checklist.model.VacationChecklist#getCurrentVacation <em>Current Vacation</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Current Vacation</em>' containment reference.
   * @see #getCurrentVacation()
   * @generated
   */
  void setCurrentVacation(CurrentVacation value);

} // VacationChecklist
