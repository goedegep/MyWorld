/**
 */
package goedegep.vacations.checklist.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item Status</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.checklist.model.ItemStatus#getVacationChecklistItem <em>Vacation Checklist Item</em>}</li>
 *   <li>{@link goedegep.vacations.checklist.model.ItemStatus#getPackStatus <em>Pack Status</em>}</li>
 * </ul>
 *
 * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getItemStatus()
 * @model
 * @generated
 */
public interface ItemStatus extends EObject {
  /**
   * Returns the value of the '<em><b>Vacation Checklist Item</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Vacation Checklist Item</em>' reference.
   * @see #isSetVacationChecklistItem()
   * @see #unsetVacationChecklistItem()
   * @see #setVacationChecklistItem(VacationChecklistItem)
   * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getItemStatus_VacationChecklistItem()
   * @model unsettable="true"
   * @generated
   */
  VacationChecklistItem getVacationChecklistItem();

  /**
   * Sets the value of the '{@link goedegep.vacations.checklist.model.ItemStatus#getVacationChecklistItem <em>Vacation Checklist Item</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Vacation Checklist Item</em>' reference.
   * @see #isSetVacationChecklistItem()
   * @see #unsetVacationChecklistItem()
   * @see #getVacationChecklistItem()
   * @generated
   */
  void setVacationChecklistItem(VacationChecklistItem value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.checklist.model.ItemStatus#getVacationChecklistItem <em>Vacation Checklist Item</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetVacationChecklistItem()
   * @see #getVacationChecklistItem()
   * @see #setVacationChecklistItem(VacationChecklistItem)
   * @generated
   */
  void unsetVacationChecklistItem();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.checklist.model.ItemStatus#getVacationChecklistItem <em>Vacation Checklist Item</em>}' reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Vacation Checklist Item</em>' reference is set.
   * @see #unsetVacationChecklistItem()
   * @see #getVacationChecklistItem()
   * @see #setVacationChecklistItem(VacationChecklistItem)
   * @generated
   */
  boolean isSetVacationChecklistItem();

  /**
   * Returns the value of the '<em><b>Pack Status</b></em>' attribute.
   * The literals are from the enumeration {@link goedegep.vacations.checklist.model.PackStatus}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pack Status</em>' attribute.
   * @see goedegep.vacations.checklist.model.PackStatus
   * @see #isSetPackStatus()
   * @see #unsetPackStatus()
   * @see #setPackStatus(PackStatus)
   * @see goedegep.vacations.checklist.model.VacationChecklistPackage#getItemStatus_PackStatus()
   * @model unsettable="true"
   * @generated
   */
  PackStatus getPackStatus();

  /**
   * Sets the value of the '{@link goedegep.vacations.checklist.model.ItemStatus#getPackStatus <em>Pack Status</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Pack Status</em>' attribute.
   * @see goedegep.vacations.checklist.model.PackStatus
   * @see #isSetPackStatus()
   * @see #unsetPackStatus()
   * @see #getPackStatus()
   * @generated
   */
  void setPackStatus(PackStatus value);

  /**
   * Unsets the value of the '{@link goedegep.vacations.checklist.model.ItemStatus#getPackStatus <em>Pack Status</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPackStatus()
   * @see #getPackStatus()
   * @see #setPackStatus(PackStatus)
   * @generated
   */
  void unsetPackStatus();

  /**
   * Returns whether the value of the '{@link goedegep.vacations.checklist.model.ItemStatus#getPackStatus <em>Pack Status</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Pack Status</em>' attribute is set.
   * @see #unsetPackStatus()
   * @see #getPackStatus()
   * @see #setPackStatus(PackStatus)
   * @generated
   */
  boolean isSetPackStatus();

} // ItemStatus
