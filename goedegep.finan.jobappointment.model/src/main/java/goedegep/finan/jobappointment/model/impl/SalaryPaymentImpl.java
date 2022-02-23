/**
 */
package goedegep.finan.jobappointment.model.impl;

import goedegep.finan.jobappointment.model.JobAppointmentPackage;
import goedegep.finan.jobappointment.model.SalaryPayment;

import goedegep.util.money.PgCurrency;

import java.time.YearMonth;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Salary Payment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.SalaryPaymentImpl#getGrossSalary <em>Gross Salary</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.impl.SalaryPaymentImpl#getPeriod <em>Period</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SalaryPaymentImpl extends SalaryEventImpl implements SalaryPayment {
  /**
   * The default value of the '{@link #getGrossSalary() <em>Gross Salary</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGrossSalary()
   * @generated
   * @ordered
   */
  protected static final PgCurrency GROSS_SALARY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getGrossSalary() <em>Gross Salary</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGrossSalary()
   * @generated
   * @ordered
   */
  protected PgCurrency grossSalary = GROSS_SALARY_EDEFAULT;

  /**
   * The default value of the '{@link #getPeriod() <em>Period</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPeriod()
   * @generated
   * @ordered
   */
  protected static final YearMonth PERIOD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPeriod() <em>Period</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPeriod()
   * @generated
   * @ordered
   */
  protected YearMonth period = PERIOD_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SalaryPaymentImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return JobAppointmentPackage.Literals.SALARY_PAYMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getGrossSalary() {
    return grossSalary;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setGrossSalary(PgCurrency newGrossSalary) {
    PgCurrency oldGrossSalary = grossSalary;
    grossSalary = newGrossSalary;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.SALARY_PAYMENT__GROSS_SALARY, oldGrossSalary, grossSalary));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public YearMonth getPeriod() {
    return period;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPeriod(YearMonth newPeriod) {
    YearMonth oldPeriod = period;
    period = newPeriod;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JobAppointmentPackage.SALARY_PAYMENT__PERIOD, oldPeriod, period));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case JobAppointmentPackage.SALARY_PAYMENT__GROSS_SALARY:
        return getGrossSalary();
      case JobAppointmentPackage.SALARY_PAYMENT__PERIOD:
        return getPeriod();
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
      case JobAppointmentPackage.SALARY_PAYMENT__GROSS_SALARY:
        setGrossSalary((PgCurrency)newValue);
        return;
      case JobAppointmentPackage.SALARY_PAYMENT__PERIOD:
        setPeriod((YearMonth)newValue);
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
      case JobAppointmentPackage.SALARY_PAYMENT__GROSS_SALARY:
        setGrossSalary(GROSS_SALARY_EDEFAULT);
        return;
      case JobAppointmentPackage.SALARY_PAYMENT__PERIOD:
        setPeriod(PERIOD_EDEFAULT);
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
      case JobAppointmentPackage.SALARY_PAYMENT__GROSS_SALARY:
        return GROSS_SALARY_EDEFAULT == null ? grossSalary != null : !GROSS_SALARY_EDEFAULT.equals(grossSalary);
      case JobAppointmentPackage.SALARY_PAYMENT__PERIOD:
        return PERIOD_EDEFAULT == null ? period != null : !PERIOD_EDEFAULT.equals(period);
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
    result.append(" (grossSalary: ");
    result.append(grossSalary);
    result.append(", period: ");
    result.append(period);
    result.append(')');
    return result.toString();
  }

} //SalaryPaymentImpl
