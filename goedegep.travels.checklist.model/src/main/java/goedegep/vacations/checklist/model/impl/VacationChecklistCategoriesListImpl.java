/**
 */
package goedegep.vacations.checklist.model.impl;

import goedegep.vacations.checklist.model.VacationChecklistCategoriesList;
import goedegep.vacations.checklist.model.VacationChecklistCategory;
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
 * An implementation of the model object '<em><b>Categories List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.checklist.model.impl.VacationChecklistCategoriesListImpl#getVacationChecklistCategories <em>Vacation Checklist Categories</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VacationChecklistCategoriesListImpl extends MinimalEObjectImpl.Container implements VacationChecklistCategoriesList {
  /**
   * The cached value of the '{@link #getVacationChecklistCategories() <em>Vacation Checklist Categories</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVacationChecklistCategories()
   * @generated
   * @ordered
   */
  protected EList<VacationChecklistCategory> vacationChecklistCategories;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VacationChecklistCategoriesListImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationChecklistPackage.Literals.VACATION_CHECKLIST_CATEGORIES_LIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<VacationChecklistCategory> getVacationChecklistCategories() {
    if (vacationChecklistCategories == null) {
      vacationChecklistCategories = new EObjectContainmentEList<VacationChecklistCategory>(VacationChecklistCategory.class, this, VacationChecklistPackage.VACATION_CHECKLIST_CATEGORIES_LIST__VACATION_CHECKLIST_CATEGORIES);
    }
    return vacationChecklistCategories;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORIES_LIST__VACATION_CHECKLIST_CATEGORIES:
        return ((InternalEList<?>)getVacationChecklistCategories()).basicRemove(otherEnd, msgs);
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
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORIES_LIST__VACATION_CHECKLIST_CATEGORIES:
        return getVacationChecklistCategories();
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
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORIES_LIST__VACATION_CHECKLIST_CATEGORIES:
        getVacationChecklistCategories().clear();
        getVacationChecklistCategories().addAll((Collection<? extends VacationChecklistCategory>)newValue);
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
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORIES_LIST__VACATION_CHECKLIST_CATEGORIES:
        getVacationChecklistCategories().clear();
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
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORIES_LIST__VACATION_CHECKLIST_CATEGORIES:
        return vacationChecklistCategories != null && !vacationChecklistCategories.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //VacationChecklistCategoriesListImpl
