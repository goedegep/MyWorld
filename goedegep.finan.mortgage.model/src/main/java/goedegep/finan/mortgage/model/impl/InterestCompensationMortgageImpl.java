/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.CompensationPayment;
import goedegep.finan.mortgage.model.InterestCompensationMortgage;
import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.util.fixedpointvalue.FixedPointValue;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interest Compensation Mortgage</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.impl.InterestCompensationMortgageImpl#getCompensationPercentageBorrower <em>Compensation Percentage Borrower</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.InterestCompensationMortgageImpl#getPercentageDecemberPayment <em>Percentage December Payment</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.InterestCompensationMortgageImpl#getCompensationPaymentsPerYear <em>Compensation Payments Per Year</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InterestCompensationMortgageImpl extends MortgageImpl implements InterestCompensationMortgage {
  /**
   * The default value of the '{@link #getCompensationPercentageBorrower() <em>Compensation Percentage Borrower</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompensationPercentageBorrower()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue COMPENSATION_PERCENTAGE_BORROWER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCompensationPercentageBorrower() <em>Compensation Percentage Borrower</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompensationPercentageBorrower()
   * @generated
   * @ordered
   */
  protected FixedPointValue compensationPercentageBorrower = COMPENSATION_PERCENTAGE_BORROWER_EDEFAULT;

  /**
   * This is true if the Compensation Percentage Borrower attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean compensationPercentageBorrowerESet;

  /**
   * The default value of the '{@link #getPercentageDecemberPayment() <em>Percentage December Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPercentageDecemberPayment()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue PERCENTAGE_DECEMBER_PAYMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPercentageDecemberPayment() <em>Percentage December Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPercentageDecemberPayment()
   * @generated
   * @ordered
   */
  protected FixedPointValue percentageDecemberPayment = PERCENTAGE_DECEMBER_PAYMENT_EDEFAULT;

  /**
   * This is true if the Percentage December Payment attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean percentageDecemberPaymentESet;

  /**
   * The cached value of the '{@link #getCompensationPaymentsPerYear() <em>Compensation Payments Per Year</em>}' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompensationPaymentsPerYear()
   * @generated
   * @ordered
   */
  protected EMap<Integer, EList<CompensationPayment>> compensationPaymentsPerYear;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InterestCompensationMortgageImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MortgagePackage.Literals.INTEREST_COMPENSATION_MORTGAGE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FixedPointValue getCompensationPercentageBorrower() {
    return compensationPercentageBorrower;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCompensationPercentageBorrower(FixedPointValue newCompensationPercentageBorrower) {
    FixedPointValue oldCompensationPercentageBorrower = compensationPercentageBorrower;
    compensationPercentageBorrower = newCompensationPercentageBorrower;
    boolean oldCompensationPercentageBorrowerESet = compensationPercentageBorrowerESet;
    compensationPercentageBorrowerESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PERCENTAGE_BORROWER, oldCompensationPercentageBorrower, compensationPercentageBorrower, !oldCompensationPercentageBorrowerESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCompensationPercentageBorrower() {
    FixedPointValue oldCompensationPercentageBorrower = compensationPercentageBorrower;
    boolean oldCompensationPercentageBorrowerESet = compensationPercentageBorrowerESet;
    compensationPercentageBorrower = COMPENSATION_PERCENTAGE_BORROWER_EDEFAULT;
    compensationPercentageBorrowerESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PERCENTAGE_BORROWER, oldCompensationPercentageBorrower, COMPENSATION_PERCENTAGE_BORROWER_EDEFAULT, oldCompensationPercentageBorrowerESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCompensationPercentageBorrower() {
    return compensationPercentageBorrowerESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FixedPointValue getPercentageDecemberPayment() {
    return percentageDecemberPayment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPercentageDecemberPayment(FixedPointValue newPercentageDecemberPayment) {
    FixedPointValue oldPercentageDecemberPayment = percentageDecemberPayment;
    percentageDecemberPayment = newPercentageDecemberPayment;
    boolean oldPercentageDecemberPaymentESet = percentageDecemberPaymentESet;
    percentageDecemberPaymentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__PERCENTAGE_DECEMBER_PAYMENT, oldPercentageDecemberPayment, percentageDecemberPayment, !oldPercentageDecemberPaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPercentageDecemberPayment() {
    FixedPointValue oldPercentageDecemberPayment = percentageDecemberPayment;
    boolean oldPercentageDecemberPaymentESet = percentageDecemberPaymentESet;
    percentageDecemberPayment = PERCENTAGE_DECEMBER_PAYMENT_EDEFAULT;
    percentageDecemberPaymentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__PERCENTAGE_DECEMBER_PAYMENT, oldPercentageDecemberPayment, PERCENTAGE_DECEMBER_PAYMENT_EDEFAULT, oldPercentageDecemberPaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPercentageDecemberPayment() {
    return percentageDecemberPaymentESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EMap<Integer, EList<CompensationPayment>> getCompensationPaymentsPerYear() {
    if (compensationPaymentsPerYear == null) {
      compensationPaymentsPerYear = new EcoreEMap<Integer,EList<CompensationPayment>>(MortgagePackage.Literals.INTEGER_TO_ELIST, IntegerToEListImpl.class, this, MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PAYMENTS_PER_YEAR);
    }
    return compensationPaymentsPerYear;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PAYMENTS_PER_YEAR:
        return ((InternalEList<?>)getCompensationPaymentsPerYear()).basicRemove(otherEnd, msgs);
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
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PERCENTAGE_BORROWER:
        return getCompensationPercentageBorrower();
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__PERCENTAGE_DECEMBER_PAYMENT:
        return getPercentageDecemberPayment();
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PAYMENTS_PER_YEAR:
        if (coreType) return getCompensationPaymentsPerYear();
        else return getCompensationPaymentsPerYear().map();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PERCENTAGE_BORROWER:
        setCompensationPercentageBorrower((FixedPointValue)newValue);
        return;
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__PERCENTAGE_DECEMBER_PAYMENT:
        setPercentageDecemberPayment((FixedPointValue)newValue);
        return;
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PAYMENTS_PER_YEAR:
        ((EStructuralFeature.Setting)getCompensationPaymentsPerYear()).set(newValue);
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
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PERCENTAGE_BORROWER:
        unsetCompensationPercentageBorrower();
        return;
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__PERCENTAGE_DECEMBER_PAYMENT:
        unsetPercentageDecemberPayment();
        return;
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PAYMENTS_PER_YEAR:
        getCompensationPaymentsPerYear().clear();
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
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PERCENTAGE_BORROWER:
        return isSetCompensationPercentageBorrower();
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__PERCENTAGE_DECEMBER_PAYMENT:
        return isSetPercentageDecemberPayment();
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PAYMENTS_PER_YEAR:
        return compensationPaymentsPerYear != null && !compensationPaymentsPerYear.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (compensationPercentageBorrower: ");
    if (compensationPercentageBorrowerESet) result.append(compensationPercentageBorrower); else result.append("<unset>");
    result.append(", percentageDecemberPayment: ");
    if (percentageDecemberPaymentESet) result.append(percentageDecemberPayment); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //InterestCompensationMortgageImpl
