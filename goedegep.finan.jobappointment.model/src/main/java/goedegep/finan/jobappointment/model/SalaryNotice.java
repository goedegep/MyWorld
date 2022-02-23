/**
 */
package goedegep.finan.jobappointment.model;

import goedegep.util.money.PgCurrency;

import java.time.LocalDate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Salary Notice</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.jobappointment.model.SalaryNotice#getStartingDate <em>Starting Date</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.SalaryNotice#getCurrentSalaryFulltime <em>Current Salary Fulltime</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.SalaryNotice#getCurrentSalaryParttime <em>Current Salary Parttime</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.SalaryNotice#getCurrentParttimePercentage <em>Current Parttime Percentage</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.SalaryNotice#getNewSalaryFulltime <em>New Salary Fulltime</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.SalaryNotice#getNewSalaryParttime <em>New Salary Parttime</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.SalaryNotice#getNewParttimePercentage <em>New Parttime Percentage</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.SalaryNotice#getFunctionGroup <em>Function Group</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryNotice()
 * @model
 * @generated
 */
public interface SalaryNotice extends SalaryEvent {
  /**
   * Returns the value of the '<em><b>Starting Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Starting Date</em>' attribute.
   * @see #setStartingDate(LocalDate)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryNotice_StartingDate()
   * @model dataType="goedegep.types.model.ELocalDate"
   * @generated
   */
  LocalDate getStartingDate();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.SalaryNotice#getStartingDate <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Starting Date</em>' attribute.
   * @see #getStartingDate()
   * @generated
   */
  void setStartingDate(LocalDate value);

  /**
   * Returns the value of the '<em><b>Current Salary Fulltime</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Current Salary Fulltime</em>' attribute.
   * @see #setCurrentSalaryFulltime(PgCurrency)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryNotice_CurrentSalaryFulltime()
   * @model dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getCurrentSalaryFulltime();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.SalaryNotice#getCurrentSalaryFulltime <em>Current Salary Fulltime</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Current Salary Fulltime</em>' attribute.
   * @see #getCurrentSalaryFulltime()
   * @generated
   */
  void setCurrentSalaryFulltime(PgCurrency value);

  /**
   * Returns the value of the '<em><b>Current Salary Parttime</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Current Salary Parttime</em>' attribute.
   * @see #setCurrentSalaryParttime(PgCurrency)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryNotice_CurrentSalaryParttime()
   * @model dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getCurrentSalaryParttime();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.SalaryNotice#getCurrentSalaryParttime <em>Current Salary Parttime</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Current Salary Parttime</em>' attribute.
   * @see #getCurrentSalaryParttime()
   * @generated
   */
  void setCurrentSalaryParttime(PgCurrency value);

  /**
   * Returns the value of the '<em><b>Current Parttime Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Current Parttime Percentage</em>' attribute.
   * @see #setCurrentParttimePercentage(Integer)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryNotice_CurrentParttimePercentage()
   * @model
   * @generated
   */
  Integer getCurrentParttimePercentage();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.SalaryNotice#getCurrentParttimePercentage <em>Current Parttime Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Current Parttime Percentage</em>' attribute.
   * @see #getCurrentParttimePercentage()
   * @generated
   */
  void setCurrentParttimePercentage(Integer value);

  /**
   * Returns the value of the '<em><b>New Salary Fulltime</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>New Salary Fulltime</em>' attribute.
   * @see #setNewSalaryFulltime(PgCurrency)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryNotice_NewSalaryFulltime()
   * @model dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getNewSalaryFulltime();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.SalaryNotice#getNewSalaryFulltime <em>New Salary Fulltime</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>New Salary Fulltime</em>' attribute.
   * @see #getNewSalaryFulltime()
   * @generated
   */
  void setNewSalaryFulltime(PgCurrency value);

  /**
   * Returns the value of the '<em><b>New Salary Parttime</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>New Salary Parttime</em>' attribute.
   * @see #setNewSalaryParttime(PgCurrency)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryNotice_NewSalaryParttime()
   * @model dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getNewSalaryParttime();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.SalaryNotice#getNewSalaryParttime <em>New Salary Parttime</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>New Salary Parttime</em>' attribute.
   * @see #getNewSalaryParttime()
   * @generated
   */
  void setNewSalaryParttime(PgCurrency value);

  /**
   * Returns the value of the '<em><b>New Parttime Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>New Parttime Percentage</em>' attribute.
   * @see #setNewParttimePercentage(Integer)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryNotice_NewParttimePercentage()
   * @model
   * @generated
   */
  Integer getNewParttimePercentage();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.SalaryNotice#getNewParttimePercentage <em>New Parttime Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>New Parttime Percentage</em>' attribute.
   * @see #getNewParttimePercentage()
   * @generated
   */
  void setNewParttimePercentage(Integer value);

  /**
   * Returns the value of the '<em><b>Function Group</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Function Group</em>' attribute.
   * @see #setFunctionGroup(String)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryNotice_FunctionGroup()
   * @model
   * @generated
   */
  String getFunctionGroup();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.SalaryNotice#getFunctionGroup <em>Function Group</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Function Group</em>' attribute.
   * @see #getFunctionGroup()
   * @generated
   */
  void setFunctionGroup(String value);

} // SalaryNotice
