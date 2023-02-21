/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.finan.mortgage.model.MortgageYearlyOverview;
import goedegep.finan.mortgage.model.MortgageYearlyOverviews;

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
 * An implementation of the model object '<em><b>Yearly Overviews</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageYearlyOverviewsImpl#getYearlyOverviews <em>Yearly Overviews</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MortgageYearlyOverviewsImpl extends MinimalEObjectImpl.Container implements MortgageYearlyOverviews {
  /**
   * The cached value of the '{@link #getYearlyOverviews() <em>Yearly Overviews</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getYearlyOverviews()
   * @generated
   * @ordered
   */
  protected EList<MortgageYearlyOverview> yearlyOverviews;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MortgageYearlyOverviewsImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MortgagePackage.Literals.MORTGAGE_YEARLY_OVERVIEWS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<MortgageYearlyOverview> getYearlyOverviews() {
    if (yearlyOverviews == null) {
      yearlyOverviews = new EObjectContainmentEList<MortgageYearlyOverview>(MortgageYearlyOverview.class, this, MortgagePackage.MORTGAGE_YEARLY_OVERVIEWS__YEARLY_OVERVIEWS);
    }
    return yearlyOverviews;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEWS__YEARLY_OVERVIEWS:
        return ((InternalEList<?>)getYearlyOverviews()).basicRemove(otherEnd, msgs);
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
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEWS__YEARLY_OVERVIEWS:
        return getYearlyOverviews();
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
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEWS__YEARLY_OVERVIEWS:
        getYearlyOverviews().clear();
        getYearlyOverviews().addAll((Collection<? extends MortgageYearlyOverview>)newValue);
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
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEWS__YEARLY_OVERVIEWS:
        getYearlyOverviews().clear();
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
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEWS__YEARLY_OVERVIEWS:
        return yearlyOverviews != null && !yearlyOverviews.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //MortgageYearlyOverviewsImpl
