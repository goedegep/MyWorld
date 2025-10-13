/**
 */
package goedegep.vacations.checklist.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Category</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.checklist.model.VacationChecklistCategory#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.vacations.checklist.model.VacationChecklistCategory#getVacationChecklistItems <em>Vacation Checklist Items</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getVacationChecklistCategory()
 * @model
 * @generated
 */
public interface VacationChecklistCategory extends EObject {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #setName(String)
   * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getVacationChecklistCategory_Name()
   * @model unsettable="true" required="true"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.vacations.checklist.model.VacationChecklistCategory#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #isSetName()
   * @see #unsetName()
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.checklist.model.VacationChecklistCategory#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  void unsetName();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.checklist.model.VacationChecklistCategory#getName <em>Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Name</em>' attribute is set.
   * @see #unsetName()
   * @see #getName()
   * @see #setName(String)
   * @generated
   */
  boolean isSetName();

  /**
   * Returns the value of the '<em><b>Vacation Checklist Items</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.vacations.checklist.model.VacationChecklistItem}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Vacation Checklist Items</em>' containment reference list.
   * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getVacationChecklistCategory_VacationChecklistItems()
   * @model containment="true"
   * @generated
   */
  EList<VacationChecklistItem> getVacationChecklistItems();

} // VacationChecklistCategory
