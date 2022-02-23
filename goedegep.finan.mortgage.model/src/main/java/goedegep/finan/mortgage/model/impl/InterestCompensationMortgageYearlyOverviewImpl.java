/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview;
import goedegep.finan.mortgage.model.MortgagePackage;

import goedegep.util.money.PgCurrency;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interest Compensation Mortgage Yearly Overview</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.impl.InterestCompensationMortgageYearlyOverviewImpl#getCompensationBorrower <em>Compensation Borrower</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.InterestCompensationMortgageYearlyOverviewImpl#getDecemberPayment <em>December Payment</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.InterestCompensationMortgageYearlyOverviewImpl#getCompensationPayment <em>Compensation Payment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InterestCompensationMortgageYearlyOverviewImpl extends MortgageYearlyOverviewImpl implements InterestCompensationMortgageYearlyOverview {
  /**
   * The default value of the '{@link #getCompensationBorrower() <em>Compensation Borrower</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompensationBorrower()
   * @generated
   * @ordered
   */
  protected static final PgCurrency COMPENSATION_BORROWER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCompensationBorrower() <em>Compensation Borrower</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompensationBorrower()
   * @generated
   * @ordered
   */
  protected PgCurrency compensationBorrower = COMPENSATION_BORROWER_EDEFAULT;

  /**
   * This is true if the Compensation Borrower attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean compensationBorrowerESet;

  /**
   * The default value of the '{@link #getDecemberPayment() <em>December Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDecemberPayment()
   * @generated
   * @ordered
   */
  protected static final PgCurrency DECEMBER_PAYMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDecemberPayment() <em>December Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDecemberPayment()
   * @generated
   * @ordered
   */
  protected PgCurrency decemberPayment = DECEMBER_PAYMENT_EDEFAULT;

  /**
   * This is true if the December Payment attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean decemberPaymentESet;

  /**
   * The default value of the '{@link #getCompensationPayment() <em>Compensation Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompensationPayment()
   * @generated
   * @ordered
   */
  protected static final PgCurrency COMPENSATION_PAYMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCompensationPayment() <em>Compensation Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompensationPayment()
   * @generated
   * @ordered
   */
  protected PgCurrency compensationPayment = COMPENSATION_PAYMENT_EDEFAULT;

  /**
   * This is true if the Compensation Payment attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean compensationPaymentESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InterestCompensationMortgageYearlyOverviewImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MortgagePackage.Literals.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getCompensationBorrower() {
    return compensationBorrower;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCompensationBorrower(PgCurrency newCompensationBorrower) {
    PgCurrency oldCompensationBorrower = compensationBorrower;
    compensationBorrower = newCompensationBorrower;
    boolean oldCompensationBorrowerESet = compensationBorrowerESet;
    compensationBorrowerESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_BORROWER, oldCompensationBorrower, compensationBorrower, !oldCompensationBorrowerESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCompensationBorrower() {
    PgCurrency oldCompensationBorrower = compensationBorrower;
    boolean oldCompensationBorrowerESet = compensationBorrowerESet;
    compensationBorrower = COMPENSATION_BORROWER_EDEFAULT;
    compensationBorrowerESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_BORROWER, oldCompensationBorrower, COMPENSATION_BORROWER_EDEFAULT, oldCompensationBorrowerESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCompensationBorrower() {
    return compensationBorrowerESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getDecemberPayment() {
    return decemberPayment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDecemberPayment(PgCurrency newDecemberPayment) {
    PgCurrency oldDecemberPayment = decemberPayment;
    decemberPayment = newDecemberPayment;
    boolean oldDecemberPaymentESet = decemberPaymentESet;
    decemberPaymentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__DECEMBER_PAYMENT, oldDecemberPayment, decemberPayment, !oldDecemberPaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDecemberPayment() {
    PgCurrency oldDecemberPayment = decemberPayment;
    boolean oldDecemberPaymentESet = decemberPaymentESet;
    decemberPayment = DECEMBER_PAYMENT_EDEFAULT;
    decemberPaymentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__DECEMBER_PAYMENT, oldDecemberPayment, DECEMBER_PAYMENT_EDEFAULT, oldDecemberPaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDecemberPayment() {
    return decemberPaymentESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getCompensationPayment() {
    return compensationPayment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCompensationPayment(PgCurrency newCompensationPayment) {
    PgCurrency oldCompensationPayment = compensationPayment;
    compensationPayment = newCompensationPayment;
    boolean oldCompensationPaymentESet = compensationPaymentESet;
    compensationPaymentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_PAYMENT, oldCompensationPayment, compensationPayment, !oldCompensationPaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCompensationPayment() {
    PgCurrency oldCompensationPayment = compensationPayment;
    boolean oldCompensationPaymentESet = compensationPaymentESet;
    compensationPayment = COMPENSATION_PAYMENT_EDEFAULT;
    compensationPaymentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_PAYMENT, oldCompensationPayment, COMPENSATION_PAYMENT_EDEFAULT, oldCompensationPaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetCompensationPayment() {
    return compensationPaymentESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public PgCurrency getCompensationToBePaid() {
    if (isSetCompensationBorrower()  &&  isSetCompensationPayment()) {
      return getCompensationBorrower().subtract(getCompensationPayment());
    } else {
      return new PgCurrency(66666);
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_BORROWER:
        return getCompensationBorrower();
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__DECEMBER_PAYMENT:
        return getDecemberPayment();
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_PAYMENT:
        return getCompensationPayment();
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
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_BORROWER:
        setCompensationBorrower((PgCurrency)newValue);
        return;
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__DECEMBER_PAYMENT:
        setDecemberPayment((PgCurrency)newValue);
        return;
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_PAYMENT:
        setCompensationPayment((PgCurrency)newValue);
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
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_BORROWER:
        unsetCompensationBorrower();
        return;
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__DECEMBER_PAYMENT:
        unsetDecemberPayment();
        return;
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_PAYMENT:
        unsetCompensationPayment();
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
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_BORROWER:
        return isSetCompensationBorrower();
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__DECEMBER_PAYMENT:
        return isSetDecemberPayment();
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_PAYMENT:
        return isSetCompensationPayment();
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
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW___GET_COMPENSATION_TO_BE_PAID:
        return getCompensationToBePaid();
    }
    return super.eInvoke(operationID, arguments);
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
    result.append(" (compensationBorrower: ");
    if (compensationBorrowerESet) result.append(compensationBorrower); else result.append("<unset>");
    result.append(", decemberPayment: ");
    if (decemberPaymentESet) result.append(decemberPayment); else result.append("<unset>");
    result.append(", compensationPayment: ");
    if (compensationPaymentESet) result.append(compensationPayment); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //InterestCompensationMortgageYearlyOverviewImpl
