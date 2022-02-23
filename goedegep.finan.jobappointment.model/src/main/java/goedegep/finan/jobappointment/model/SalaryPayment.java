/**
 */
package goedegep.finan.jobappointment.model;

import goedegep.util.money.PgCurrency;

import java.time.YearMonth;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Salary Payment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.jobappointment.model.SalaryPayment#getGrossSalary <em>Gross Salary</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.SalaryPayment#getPeriod <em>Period</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryPayment()
 * @model
 * @generated
 */
public interface SalaryPayment extends SalaryEvent {
  /**
   * Returns the value of the '<em><b>Gross Salary</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The gross salary paid.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Gross Salary</em>' attribute.
   * @see #setGrossSalary(PgCurrency)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryPayment_GrossSalary()
   * @model dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getGrossSalary();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.SalaryPayment#getGrossSalary <em>Gross Salary</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Gross Salary</em>' attribute.
   * @see #getGrossSalary()
   * @generated
   */
  void setGrossSalary(PgCurrency value);

  /**
   * Returns the value of the '<em><b>Period</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The period to which the payment applies.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Period</em>' attribute.
   * @see #setPeriod(YearMonth)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryPayment_Period()
   * @model dataType="goedegep.types.model.EYearMonth"
   * @generated
   */
  YearMonth getPeriod();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.SalaryPayment#getPeriod <em>Period</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Period</em>' attribute.
   * @see #getPeriod()
   * @generated
   */
  void setPeriod(YearMonth value);

} // SalaryPayment
