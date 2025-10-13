/**
 */
package goedegep.vacations.model.impl;

import goedegep.vacations.model.Travel;
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
 * An implementation of the model object '<em><b>Travel Category</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.vacations.model.impl.TravelCategoryImpl#getTravels <em>Travels</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TravelCategoryImpl extends MinimalEObjectImpl.Container implements TravelCategory {
  /**
   * The cached value of the '{@link #getTravels() <em>Travels</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTravels()
   * @generated
   * @ordered
   */
  protected EList<Travel> travels;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TravelCategoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return VacationsPackage.Literals.TRAVEL_CATEGORY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Travel> getTravels() {
    if (travels == null) {
      travels = new EObjectContainmentEList<Travel>(Travel.class, this, VacationsPackage.TRAVEL_CATEGORY__TRAVELS);
    }
    return travels;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case VacationsPackage.TRAVEL_CATEGORY__TRAVELS:
      return ((InternalEList<?>) getTravels()).basicRemove(otherEnd, msgs);
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
    case VacationsPackage.TRAVEL_CATEGORY__TRAVELS:
      return getTravels();
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
    case VacationsPackage.TRAVEL_CATEGORY__TRAVELS:
      getTravels().clear();
      getTravels().addAll((Collection<? extends Travel>) newValue);
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
    case VacationsPackage.TRAVEL_CATEGORY__TRAVELS:
      getTravels().clear();
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
    case VacationsPackage.TRAVEL_CATEGORY__TRAVELS:
      return travels != null && !travels.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //TravelCategoryImpl
