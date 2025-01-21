/**
 */
package goedegep.vacations.model.impl;

import goedegep.vacations.model.TravelCategories;
import goedegep.vacations.model.TravelCategory;
import goedegep.vacations.model.VacationsPackage;

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
 * An implementation of the model object '<em><b>Travel Categories</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.impl.TravelCategoriesImpl#getTravelcategories <em>Travelcategories</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TravelCategoriesImpl extends MinimalEObjectImpl.Container implements TravelCategories {
  /**
   * The cached value of the '{@link #getTravelcategories() <em>Travelcategories</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTravelcategories()
   * @generated
   * @ordered
   */
  protected EList<TravelCategory> travelcategories;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TravelCategoriesImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationsPackage.Literals.TRAVEL_CATEGORIES;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<TravelCategory> getTravelcategories() {
    if (travelcategories == null) {
      travelcategories = new EObjectContainmentEList<TravelCategory>(TravelCategory.class, this,
          VacationsPackage.TRAVEL_CATEGORIES__TRAVELCATEGORIES);
    }
    return travelcategories;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case VacationsPackage.TRAVEL_CATEGORIES__TRAVELCATEGORIES:
      return ((InternalEList<?>) getTravelcategories()).basicRemove(otherEnd, msgs);
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
    case VacationsPackage.TRAVEL_CATEGORIES__TRAVELCATEGORIES:
      return getTravelcategories();
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
    case VacationsPackage.TRAVEL_CATEGORIES__TRAVELCATEGORIES:
      getTravelcategories().clear();
      getTravelcategories().addAll((Collection<? extends TravelCategory>) newValue);
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
    case VacationsPackage.TRAVEL_CATEGORIES__TRAVELCATEGORIES:
      getTravelcategories().clear();
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
    case VacationsPackage.TRAVEL_CATEGORIES__TRAVELCATEGORIES:
      return travelcategories != null && !travelcategories.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //TravelCategoriesImpl
