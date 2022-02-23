/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.MortgagePackage;
import goedegep.finan.mortgage.model.MortgageYearlyOverview;

import goedegep.util.money.PgCurrency;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Yearly Overview</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageYearlyOverviewImpl#getDebtAtBeginningOfYear <em>Debt At Beginning Of Year</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageYearlyOverviewImpl#getDebtAtEndOfYear <em>Debt At End Of Year</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageYearlyOverviewImpl#getInterestPaid <em>Interest Paid</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageYearlyOverviewImpl#getRepayment <em>Repayment</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageYearlyOverviewImpl#getYear <em>Year</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MortgageYearlyOverviewImpl extends MinimalEObjectImpl.Container implements MortgageYearlyOverview {
  /**
   * The default value of the '{@link #getDebtAtBeginningOfYear() <em>Debt At Beginning Of Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDebtAtBeginningOfYear()
   * @generated
   * @ordered
   */
  protected static final PgCurrency DEBT_AT_BEGINNING_OF_YEAR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDebtAtBeginningOfYear() <em>Debt At Beginning Of Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDebtAtBeginningOfYear()
   * @generated
   * @ordered
   */
  protected PgCurrency debtAtBeginningOfYear = DEBT_AT_BEGINNING_OF_YEAR_EDEFAULT;

  /**
   * This is true if the Debt At Beginning Of Year attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean debtAtBeginningOfYearESet;

  /**
   * The default value of the '{@link #getDebtAtEndOfYear() <em>Debt At End Of Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDebtAtEndOfYear()
   * @generated
   * @ordered
   */
  protected static final PgCurrency DEBT_AT_END_OF_YEAR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDebtAtEndOfYear() <em>Debt At End Of Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDebtAtEndOfYear()
   * @generated
   * @ordered
   */
  protected PgCurrency debtAtEndOfYear = DEBT_AT_END_OF_YEAR_EDEFAULT;

  /**
   * This is true if the Debt At End Of Year attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean debtAtEndOfYearESet;

  /**
   * The default value of the '{@link #getInterestPaid() <em>Interest Paid</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterestPaid()
   * @generated
   * @ordered
   */
  protected static final PgCurrency INTEREST_PAID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInterestPaid() <em>Interest Paid</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterestPaid()
   * @generated
   * @ordered
   */
  protected PgCurrency interestPaid = INTEREST_PAID_EDEFAULT;

  /**
   * This is true if the Interest Paid attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean interestPaidESet;

  /**
   * The default value of the '{@link #getRepayment() <em>Repayment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRepayment()
   * @generated
   * @ordered
   */
  protected static final PgCurrency REPAYMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRepayment() <em>Repayment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRepayment()
   * @generated
   * @ordered
   */
  protected PgCurrency repayment = REPAYMENT_EDEFAULT;

  /**
   * This is true if the Repayment attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean repaymentESet;

  /**
   * The default value of the '{@link #getYear() <em>Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getYear()
   * @generated
   * @ordered
   */
  protected static final Integer YEAR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getYear() <em>Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getYear()
   * @generated
   * @ordered
   */
  protected Integer year = YEAR_EDEFAULT;

  /**
   * This is true if the Year attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean yearESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MortgageYearlyOverviewImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MortgagePackage.Literals.MORTGAGE_YEARLY_OVERVIEW;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getDebtAtBeginningOfYear() {
    return debtAtBeginningOfYear;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDebtAtBeginningOfYear(PgCurrency newDebtAtBeginningOfYear) {
    PgCurrency oldDebtAtBeginningOfYear = debtAtBeginningOfYear;
    debtAtBeginningOfYear = newDebtAtBeginningOfYear;
    boolean oldDebtAtBeginningOfYearESet = debtAtBeginningOfYearESet;
    debtAtBeginningOfYearESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_BEGINNING_OF_YEAR, oldDebtAtBeginningOfYear, debtAtBeginningOfYear, !oldDebtAtBeginningOfYearESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDebtAtBeginningOfYear() {
    PgCurrency oldDebtAtBeginningOfYear = debtAtBeginningOfYear;
    boolean oldDebtAtBeginningOfYearESet = debtAtBeginningOfYearESet;
    debtAtBeginningOfYear = DEBT_AT_BEGINNING_OF_YEAR_EDEFAULT;
    debtAtBeginningOfYearESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_BEGINNING_OF_YEAR, oldDebtAtBeginningOfYear, DEBT_AT_BEGINNING_OF_YEAR_EDEFAULT, oldDebtAtBeginningOfYearESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDebtAtBeginningOfYear() {
    return debtAtBeginningOfYearESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getDebtAtEndOfYear() {
    return debtAtEndOfYear;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDebtAtEndOfYear(PgCurrency newDebtAtEndOfYear) {
    PgCurrency oldDebtAtEndOfYear = debtAtEndOfYear;
    debtAtEndOfYear = newDebtAtEndOfYear;
    boolean oldDebtAtEndOfYearESet = debtAtEndOfYearESet;
    debtAtEndOfYearESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_END_OF_YEAR, oldDebtAtEndOfYear, debtAtEndOfYear, !oldDebtAtEndOfYearESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDebtAtEndOfYear() {
    PgCurrency oldDebtAtEndOfYear = debtAtEndOfYear;
    boolean oldDebtAtEndOfYearESet = debtAtEndOfYearESet;
    debtAtEndOfYear = DEBT_AT_END_OF_YEAR_EDEFAULT;
    debtAtEndOfYearESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_END_OF_YEAR, oldDebtAtEndOfYear, DEBT_AT_END_OF_YEAR_EDEFAULT, oldDebtAtEndOfYearESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDebtAtEndOfYear() {
    return debtAtEndOfYearESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getInterestPaid() {
    return interestPaid;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInterestPaid(PgCurrency newInterestPaid) {
    PgCurrency oldInterestPaid = interestPaid;
    interestPaid = newInterestPaid;
    boolean oldInterestPaidESet = interestPaidESet;
    interestPaidESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__INTEREST_PAID, oldInterestPaid, interestPaid, !oldInterestPaidESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetInterestPaid() {
    PgCurrency oldInterestPaid = interestPaid;
    boolean oldInterestPaidESet = interestPaidESet;
    interestPaid = INTEREST_PAID_EDEFAULT;
    interestPaidESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__INTEREST_PAID, oldInterestPaid, INTEREST_PAID_EDEFAULT, oldInterestPaidESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetInterestPaid() {
    return interestPaidESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getRepayment() {
    return repayment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setRepayment(PgCurrency newRepayment) {
    PgCurrency oldRepayment = repayment;
    repayment = newRepayment;
    boolean oldRepaymentESet = repaymentESet;
    repaymentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__REPAYMENT, oldRepayment, repayment, !oldRepaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetRepayment() {
    PgCurrency oldRepayment = repayment;
    boolean oldRepaymentESet = repaymentESet;
    repayment = REPAYMENT_EDEFAULT;
    repaymentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__REPAYMENT, oldRepayment, REPAYMENT_EDEFAULT, oldRepaymentESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetRepayment() {
    return repaymentESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Integer getYear() {
    return year;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setYear(Integer newYear) {
    Integer oldYear = year;
    year = newYear;
    boolean oldYearESet = yearESet;
    yearESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__YEAR, oldYear, year, !oldYearESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetYear() {
    Integer oldYear = year;
    boolean oldYearESet = yearESet;
    year = YEAR_EDEFAULT;
    yearESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__YEAR, oldYear, YEAR_EDEFAULT, oldYearESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetYear() {
    return yearESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getPaymentsTotal() {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_BEGINNING_OF_YEAR:
        return getDebtAtBeginningOfYear();
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_END_OF_YEAR:
        return getDebtAtEndOfYear();
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__INTEREST_PAID:
        return getInterestPaid();
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__REPAYMENT:
        return getRepayment();
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__YEAR:
        return getYear();
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
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_BEGINNING_OF_YEAR:
        setDebtAtBeginningOfYear((PgCurrency)newValue);
        return;
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_END_OF_YEAR:
        setDebtAtEndOfYear((PgCurrency)newValue);
        return;
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__INTEREST_PAID:
        setInterestPaid((PgCurrency)newValue);
        return;
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__REPAYMENT:
        setRepayment((PgCurrency)newValue);
        return;
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__YEAR:
        setYear((Integer)newValue);
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
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_BEGINNING_OF_YEAR:
        unsetDebtAtBeginningOfYear();
        return;
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_END_OF_YEAR:
        unsetDebtAtEndOfYear();
        return;
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__INTEREST_PAID:
        unsetInterestPaid();
        return;
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__REPAYMENT:
        unsetRepayment();
        return;
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__YEAR:
        unsetYear();
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
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_BEGINNING_OF_YEAR:
        return isSetDebtAtBeginningOfYear();
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_END_OF_YEAR:
        return isSetDebtAtEndOfYear();
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__INTEREST_PAID:
        return isSetInterestPaid();
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__REPAYMENT:
        return isSetRepayment();
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW__YEAR:
        return isSetYear();
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
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW___GET_PAYMENTS_TOTAL:
        return getPaymentsTotal();
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
    result.append(" (debtAtBeginningOfYear: ");
    if (debtAtBeginningOfYearESet) result.append(debtAtBeginningOfYear); else result.append("<unset>");
    result.append(", debtAtEndOfYear: ");
    if (debtAtEndOfYearESet) result.append(debtAtEndOfYear); else result.append("<unset>");
    result.append(", interestPaid: ");
    if (interestPaidESet) result.append(interestPaid); else result.append("<unset>");
    result.append(", repayment: ");
    if (repaymentESet) result.append(repayment); else result.append("<unset>");
    result.append(", year: ");
    if (yearESet) result.append(year); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //MortgageYearlyOverviewImpl
