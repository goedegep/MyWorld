/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.CompensationPayment;
import goedegep.finan.mortgage.model.CompensationPayments;
import goedegep.finan.mortgage.model.MortgagePackage;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compensation Payments</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.impl.CompensationPaymentsImpl#getCompensationpayments <em>Compensationpayments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompensationPaymentsImpl extends MinimalEObjectImpl.Container implements CompensationPayments {
  /**
   * The cached value of the '{@link #getCompensationpayments() <em>Compensationpayments</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompensationpayments()
   * @generated
   * @ordered
   */
  protected EList<CompensationPayment> compensationpayments;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CompensationPaymentsImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MortgagePackage.Literals.COMPENSATION_PAYMENTS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<CompensationPayment> getCompensationpayments() {
    if (compensationpayments == null) {
      compensationpayments = new EObjectResolvingEList<CompensationPayment>(CompensationPayment.class, this, MortgagePackage.COMPENSATION_PAYMENTS__COMPENSATIONPAYMENTS);
    }
    return compensationpayments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case MortgagePackage.COMPENSATION_PAYMENTS__COMPENSATIONPAYMENTS:
        return getCompensationpayments();
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
      case MortgagePackage.COMPENSATION_PAYMENTS__COMPENSATIONPAYMENTS:
        getCompensationpayments().clear();
        getCompensationpayments().addAll((Collection<? extends CompensationPayment>)newValue);
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
      case MortgagePackage.COMPENSATION_PAYMENTS__COMPENSATIONPAYMENTS:
        getCompensationpayments().clear();
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
      case MortgagePackage.COMPENSATION_PAYMENTS__COMPENSATIONPAYMENTS:
        return compensationpayments != null && !compensationpayments.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //CompensationPaymentsImpl
