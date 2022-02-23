/**
 */
package goedegep.vacations.checklist.model.impl;

import goedegep.vacations.checklist.model.VacationChecklistLabel;
import goedegep.vacations.checklist.model.VacationChecklistLabelsList;
import goedegep.vacations.checklist.model.VacationChecklistPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Labels List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.checklist.model.impl.VacationChecklistLabelsListImpl#getVacationChecklistLabels <em>Vacation Checklist Labels</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VacationChecklistLabelsListImpl extends MinimalEObjectImpl.Container implements VacationChecklistLabelsList {
  /**
   * The cached value of the '{@link #getVacationChecklistLabels() <em>Vacation Checklist Labels</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVacationChecklistLabels()
   * @generated
   * @ordered
   */
  protected EList<VacationChecklistLabel> vacationChecklistLabels;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VacationChecklistLabelsListImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationChecklistPackage.Literals.VACATION_CHECKLIST_LABELS_LIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<VacationChecklistLabel> getVacationChecklistLabels() {
    if (vacationChecklistLabels == null) {
      vacationChecklistLabels = new EObjectContainmentEList<VacationChecklistLabel>(VacationChecklistLabel.class, this, VacationChecklistPackage.VACATION_CHECKLIST_LABELS_LIST__VACATION_CHECKLIST_LABELS);
    }
    return vacationChecklistLabels;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case VacationChecklistPackage.VACATION_CHECKLIST_LABELS_LIST__VACATION_CHECKLIST_LABELS:
        return ((InternalEList<?>)getVacationChecklistLabels()).basicRemove(otherEnd, msgs);
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
      case VacationChecklistPackage.VACATION_CHECKLIST_LABELS_LIST__VACATION_CHECKLIST_LABELS:
        return getVacationChecklistLabels();
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
      case VacationChecklistPackage.VACATION_CHECKLIST_LABELS_LIST__VACATION_CHECKLIST_LABELS:
        getVacationChecklistLabels().clear();
        getVacationChecklistLabels().addAll((Collection<? extends VacationChecklistLabel>)newValue);
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
      case VacationChecklistPackage.VACATION_CHECKLIST_LABELS_LIST__VACATION_CHECKLIST_LABELS:
        getVacationChecklistLabels().clear();
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
      case VacationChecklistPackage.VACATION_CHECKLIST_LABELS_LIST__VACATION_CHECKLIST_LABELS:
        return vacationChecklistLabels != null && !vacationChecklistLabels.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //VacationChecklistLabelsListImpl
