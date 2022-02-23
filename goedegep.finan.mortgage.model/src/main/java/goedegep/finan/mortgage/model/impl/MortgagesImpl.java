/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.InterestRateSet;
import goedegep.finan.mortgage.model.Mortgage;
import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.finan.mortgage.model.Mortgages;

import java.lang.reflect.InvocationTargetException;
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
 * An implementation of the model object '<em><b>Mortgages</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgagesImpl#getMortgages <em>Mortgages</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgagesImpl#getInterestRateSets <em>Interest Rate Sets</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MortgagesImpl extends MinimalEObjectImpl.Container implements Mortgages {
  /**
   * The cached value of the '{@link #getMortgages() <em>Mortgages</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMortgages()
   * @generated
   * @ordered
   */
  protected EList<Mortgage> mortgages;

  /**
   * The cached value of the '{@link #getInterestRateSets() <em>Interest Rate Sets</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterestRateSets()
   * @generated
   * @ordered
   */
  protected EList<InterestRateSet> interestRateSets;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MortgagesImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MortgagePackage.Literals.MORTGAGES;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Mortgage> getMortgages() {
    if (mortgages == null) {
      mortgages = new EObjectContainmentEList<Mortgage>(Mortgage.class, this, MortgagePackage.MORTGAGES__MORTGAGES);
    }
    return mortgages;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<InterestRateSet> getInterestRateSets() {
    if (interestRateSets == null) {
      interestRateSets = new EObjectContainmentEList<InterestRateSet>(InterestRateSet.class, this, MortgagePackage.MORTGAGES__INTEREST_RATE_SETS);
    }
    return interestRateSets;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public Mortgage getMortgage(String mortgageNumber) {
    for (Mortgage mortgage: getMortgages()) {
      if (mortgage.isSetMortgageNumber()  &&  mortgage.getMortgageNumber().equals(mortgageNumber)) {
        return mortgage;
      }
    }
    
    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case MortgagePackage.MORTGAGES__MORTGAGES:
        return ((InternalEList<?>)getMortgages()).basicRemove(otherEnd, msgs);
      case MortgagePackage.MORTGAGES__INTEREST_RATE_SETS:
        return ((InternalEList<?>)getInterestRateSets()).basicRemove(otherEnd, msgs);
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
      case MortgagePackage.MORTGAGES__MORTGAGES:
        return getMortgages();
      case MortgagePackage.MORTGAGES__INTEREST_RATE_SETS:
        return getInterestRateSets();
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
      case MortgagePackage.MORTGAGES__MORTGAGES:
        getMortgages().clear();
        getMortgages().addAll((Collection<? extends Mortgage>)newValue);
        return;
      case MortgagePackage.MORTGAGES__INTEREST_RATE_SETS:
        getInterestRateSets().clear();
        getInterestRateSets().addAll((Collection<? extends InterestRateSet>)newValue);
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
      case MortgagePackage.MORTGAGES__MORTGAGES:
        getMortgages().clear();
        return;
      case MortgagePackage.MORTGAGES__INTEREST_RATE_SETS:
        getInterestRateSets().clear();
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
      case MortgagePackage.MORTGAGES__MORTGAGES:
        return mortgages != null && !mortgages.isEmpty();
      case MortgagePackage.MORTGAGES__INTEREST_RATE_SETS:
        return interestRateSets != null && !interestRateSets.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
      case MortgagePackage.MORTGAGES___GET_MORTGAGE__STRING:
        return getMortgage((String)arguments.get(0));
    }
    return super.eInvoke(operationID, arguments);
  }

} //MortgagesImpl
