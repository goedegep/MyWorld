/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.finan.mortgage.model.PeriodicPayment;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Periodic Payment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.impl.PeriodicPaymentImpl#getPayment <em>Payment</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.PeriodicPaymentImpl#getInterest <em>Interest</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.PeriodicPaymentImpl#getBalanceAfterPayment <em>Balance After Payment</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.PeriodicPaymentImpl#getInterestRate <em>Interest Rate</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.PeriodicPaymentImpl#getNextPaymentDate <em>Next Payment Date</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PeriodicPaymentImpl extends MortgageEventImpl implements PeriodicPayment {
  /**
   * The default value of the '{@link #getPayment() <em>Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPayment()
   * @generated
   * @ordered
   */
  protected static final PgCurrency PAYMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPayment() <em>Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPayment()
   * @generated
   * @ordered
   */
  protected PgCurrency payment = PAYMENT_EDEFAULT;

  /**
   * This is true if the Payment attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean paymentESet;

  /**
   * The default value of the '{@link #getInterest() <em>Interest</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterest()
   * @generated
   * @ordered
   */
  protected static final PgCurrency INTEREST_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInterest() <em>Interest</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterest()
   * @generated
   * @ordered
   */
  protected PgCurrency interest = INTEREST_EDEFAULT;

  /**
   * This is true if the Interest attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean interestESet;

  /**
   * The default value of the '{@link #getBalanceAfterPayment() <em>Balance After Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBalanceAfterPayment()
   * @generated
   * @ordered
   */
  protected static final PgCurrency BALANCE_AFTER_PAYMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBalanceAfterPayment() <em>Balance After Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBalanceAfterPayment()
   * @generated
   * @ordered
   */
  protected PgCurrency balanceAfterPayment = BALANCE_AFTER_PAYMENT_EDEFAULT;

  /**
   * This is true if the Balance After Payment attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean balanceAfterPaymentESet;

  /**
   * The default value of the '{@link #getInterestRate() <em>Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterestRate()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue INTEREST_RATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInterestRate() <em>Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterestRate()
   * @generated
   * @ordered
   */
  protected FixedPointValue interestRate = INTEREST_RATE_EDEFAULT;

  /**
   * This is true if the Interest Rate attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean interestRateESet;

  /**
   * The default value of the '{@link #getNextPaymentDate() <em>Next Payment Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNextPaymentDate()
   * @generated
   * @ordered
   */
  protected static final Date NEXT_PAYMENT_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNextPaymentDate() <em>Next Payment Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNextPaymentDate()
   * @generated
   * @ordered
   */
  protected Date nextPaymentDate = NEXT_PAYMENT_DATE_EDEFAULT;

  /**
   * This is true if the Next Payment Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean nextPaymentDateESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PeriodicPaymentImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MortgagePackage.Literals.PERIODIC_PAYMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getPayment() {
    return payment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPayment(PgCurrency newPayment) {
    PgCurrency oldPayment = payment;
    payment = newPayment;
    boolean oldPaymentESet = paymentESet;
    paymentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.PERIODIC_PAYMENT__PAYMENT, oldPayment, payment, !oldPaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPayment() {
    PgCurrency oldPayment = payment;
    boolean oldPaymentESet = paymentESet;
    payment = PAYMENT_EDEFAULT;
    paymentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.PERIODIC_PAYMENT__PAYMENT, oldPayment, PAYMENT_EDEFAULT, oldPaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPayment() {
    return paymentESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getInterest() {
    return interest;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInterest(PgCurrency newInterest) {
    PgCurrency oldInterest = interest;
    interest = newInterest;
    boolean oldInterestESet = interestESet;
    interestESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.PERIODIC_PAYMENT__INTEREST, oldInterest, interest, !oldInterestESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetInterest() {
    PgCurrency oldInterest = interest;
    boolean oldInterestESet = interestESet;
    interest = INTEREST_EDEFAULT;
    interestESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.PERIODIC_PAYMENT__INTEREST, oldInterest, INTEREST_EDEFAULT, oldInterestESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetInterest() {
    return interestESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getBalanceAfterPayment() {
    return balanceAfterPayment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBalanceAfterPayment(PgCurrency newBalanceAfterPayment) {
    PgCurrency oldBalanceAfterPayment = balanceAfterPayment;
    balanceAfterPayment = newBalanceAfterPayment;
    boolean oldBalanceAfterPaymentESet = balanceAfterPaymentESet;
    balanceAfterPaymentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.PERIODIC_PAYMENT__BALANCE_AFTER_PAYMENT, oldBalanceAfterPayment, balanceAfterPayment, !oldBalanceAfterPaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetBalanceAfterPayment() {
    PgCurrency oldBalanceAfterPayment = balanceAfterPayment;
    boolean oldBalanceAfterPaymentESet = balanceAfterPaymentESet;
    balanceAfterPayment = BALANCE_AFTER_PAYMENT_EDEFAULT;
    balanceAfterPaymentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.PERIODIC_PAYMENT__BALANCE_AFTER_PAYMENT, oldBalanceAfterPayment, BALANCE_AFTER_PAYMENT_EDEFAULT, oldBalanceAfterPaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetBalanceAfterPayment() {
    return balanceAfterPaymentESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FixedPointValue getInterestRate() {
    return interestRate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInterestRate(FixedPointValue newInterestRate) {
    FixedPointValue oldInterestRate = interestRate;
    interestRate = newInterestRate;
    boolean oldInterestRateESet = interestRateESet;
    interestRateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.PERIODIC_PAYMENT__INTEREST_RATE, oldInterestRate, interestRate, !oldInterestRateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetInterestRate() {
    FixedPointValue oldInterestRate = interestRate;
    boolean oldInterestRateESet = interestRateESet;
    interestRate = INTEREST_RATE_EDEFAULT;
    interestRateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.PERIODIC_PAYMENT__INTEREST_RATE, oldInterestRate, INTEREST_RATE_EDEFAULT, oldInterestRateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetInterestRate() {
    return interestRateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Date getNextPaymentDate() {
    return nextPaymentDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setNextPaymentDate(Date newNextPaymentDate) {
    Date oldNextPaymentDate = nextPaymentDate;
    nextPaymentDate = newNextPaymentDate;
    boolean oldNextPaymentDateESet = nextPaymentDateESet;
    nextPaymentDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.PERIODIC_PAYMENT__NEXT_PAYMENT_DATE, oldNextPaymentDate, nextPaymentDate, !oldNextPaymentDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetNextPaymentDate() {
    Date oldNextPaymentDate = nextPaymentDate;
    boolean oldNextPaymentDateESet = nextPaymentDateESet;
    nextPaymentDate = NEXT_PAYMENT_DATE_EDEFAULT;
    nextPaymentDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.PERIODIC_PAYMENT__NEXT_PAYMENT_DATE, oldNextPaymentDate, NEXT_PAYMENT_DATE_EDEFAULT, oldNextPaymentDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetNextPaymentDate() {
    return nextPaymentDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case MortgagePackage.PERIODIC_PAYMENT__PAYMENT:
        return getPayment();
      case MortgagePackage.PERIODIC_PAYMENT__INTEREST:
        return getInterest();
      case MortgagePackage.PERIODIC_PAYMENT__BALANCE_AFTER_PAYMENT:
        return getBalanceAfterPayment();
      case MortgagePackage.PERIODIC_PAYMENT__INTEREST_RATE:
        return getInterestRate();
      case MortgagePackage.PERIODIC_PAYMENT__NEXT_PAYMENT_DATE:
        return getNextPaymentDate();
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
      case MortgagePackage.PERIODIC_PAYMENT__PAYMENT:
        setPayment((PgCurrency)newValue);
        return;
      case MortgagePackage.PERIODIC_PAYMENT__INTEREST:
        setInterest((PgCurrency)newValue);
        return;
      case MortgagePackage.PERIODIC_PAYMENT__BALANCE_AFTER_PAYMENT:
        setBalanceAfterPayment((PgCurrency)newValue);
        return;
      case MortgagePackage.PERIODIC_PAYMENT__INTEREST_RATE:
        setInterestRate((FixedPointValue)newValue);
        return;
      case MortgagePackage.PERIODIC_PAYMENT__NEXT_PAYMENT_DATE:
        setNextPaymentDate((Date)newValue);
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
      case MortgagePackage.PERIODIC_PAYMENT__PAYMENT:
        unsetPayment();
        return;
      case MortgagePackage.PERIODIC_PAYMENT__INTEREST:
        unsetInterest();
        return;
      case MortgagePackage.PERIODIC_PAYMENT__BALANCE_AFTER_PAYMENT:
        unsetBalanceAfterPayment();
        return;
      case MortgagePackage.PERIODIC_PAYMENT__INTEREST_RATE:
        unsetInterestRate();
        return;
      case MortgagePackage.PERIODIC_PAYMENT__NEXT_PAYMENT_DATE:
        unsetNextPaymentDate();
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
      case MortgagePackage.PERIODIC_PAYMENT__PAYMENT:
        return isSetPayment();
      case MortgagePackage.PERIODIC_PAYMENT__INTEREST:
        return isSetInterest();
      case MortgagePackage.PERIODIC_PAYMENT__BALANCE_AFTER_PAYMENT:
        return isSetBalanceAfterPayment();
      case MortgagePackage.PERIODIC_PAYMENT__INTEREST_RATE:
        return isSetInterestRate();
      case MortgagePackage.PERIODIC_PAYMENT__NEXT_PAYMENT_DATE:
        return isSetNextPaymentDate();
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
    result.append(" (payment: ");
    if (paymentESet) result.append(payment); else result.append("<unset>");
    result.append(", interest: ");
    if (interestESet) result.append(interest); else result.append("<unset>");
    result.append(", balanceAfterPayment: ");
    if (balanceAfterPaymentESet) result.append(balanceAfterPayment); else result.append("<unset>");
    result.append(", interestRate: ");
    if (interestRateESet) result.append(interestRate); else result.append("<unset>");
    result.append(", nextPaymentDate: ");
    if (nextPaymentDateESet) result.append(nextPaymentDate); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //PeriodicPaymentImpl
