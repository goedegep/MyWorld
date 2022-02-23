/**
 */
package goedegep.vacations.checklist.model.impl;

import goedegep.vacations.checklist.model.CurrentVacation;
import goedegep.vacations.checklist.model.ItemStatus;
import goedegep.vacations.checklist.model.VacationChecklistLabel;
import goedegep.vacations.checklist.model.VacationChecklistPackage;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Current Vacation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.checklist.model.impl.CurrentVacationImpl#getSelectedLabels <em>Selected Labels</em>}</li>
 *   <li>{@link goedegep.vacations.checklist.model.impl.CurrentVacationImpl#getItemStatuses <em>Item Statuses</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CurrentVacationImpl extends MinimalEObjectImpl.Container implements CurrentVacation {
  /**
   * The cached value of the '{@link #getSelectedLabels() <em>Selected Labels</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSelectedLabels()
   * @generated
   * @ordered
   */
  protected EList<VacationChecklistLabel> selectedLabels;

  /**
   * The cached value of the '{@link #getItemStatuses() <em>Item Statuses</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getItemStatuses()
   * @generated
   * @ordered
   */
  protected EList<ItemStatus> itemStatuses;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CurrentVacationImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationChecklistPackage.Literals.CURRENT_VACATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<VacationChecklistLabel> getSelectedLabels() {
    if (selectedLabels == null) {
      selectedLabels = new EObjectResolvingEList<VacationChecklistLabel>(VacationChecklistLabel.class, this, VacationChecklistPackage.CURRENT_VACATION__SELECTED_LABELS);
    }
    return selectedLabels;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<ItemStatus> getItemStatuses() {
    if (itemStatuses == null) {
      itemStatuses = new EObjectContainmentEList<ItemStatus>(ItemStatus.class, this, VacationChecklistPackage.CURRENT_VACATION__ITEM_STATUSES);
    }
    return itemStatuses;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case VacationChecklistPackage.CURRENT_VACATION__ITEM_STATUSES:
        return ((InternalEList<?>)getItemStatuses()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case VacationChecklistPackage.CURRENT_VACATION__SELECTED_LABELS:
        return getSelectedLabels();
      case VacationChecklistPackage.CURRENT_VACATION__ITEM_STATUSES:
        return getItemStatuses();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case VacationChecklistPackage.CURRENT_VACATION__SELECTED_LABELS:
        getSelectedLabels().clear();
        getSelectedLabels().addAll((Collection<? extends VacationChecklistLabel>)newValue);
        return;
      case VacationChecklistPackage.CURRENT_VACATION__ITEM_STATUSES:
        getItemStatuses().clear();
        getItemStatuses().addAll((Collection<? extends ItemStatus>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case VacationChecklistPackage.CURRENT_VACATION__SELECTED_LABELS:
        getSelectedLabels().clear();
        return;
      case VacationChecklistPackage.CURRENT_VACATION__ITEM_STATUSES:
        getItemStatuses().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case VacationChecklistPackage.CURRENT_VACATION__SELECTED_LABELS:
        return selectedLabels != null && !selectedLabels.isEmpty();
      case VacationChecklistPackage.CURRENT_VACATION__ITEM_STATUSES:
        return itemStatuses != null && !itemStatuses.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //CurrentVacationImpl
