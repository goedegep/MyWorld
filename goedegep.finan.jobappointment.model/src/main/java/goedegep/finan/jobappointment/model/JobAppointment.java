/**
 */
package goedegep.finan.jobappointment.model;

import goedegep.util.money.PgCurrency;

import java.time.LocalDate;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Job Appointment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.jobappointment.model.JobAppointment#getSalaryevents <em>Salaryevents</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.JobAppointment#getCommencementOfEmploymentDate <em>Commencement Of Employment Date</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.JobAppointment#getStartingSalary <em>Starting Salary</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.JobAppointment#getStartingParttimePercentage <em>Starting Parttime Percentage</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getJobAppointment()
 * @model
 * @generated
 */
public interface JobAppointment extends EObject {
  /**
   * Returns the value of the '<em><b>Salaryevents</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.finan.jobappointment.model.SalaryEvent}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Salaryevents</em>' containment reference list.
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getJobAppointment_Salaryevents()
   * @model containment="true"
   * @generated
   */
  EList<SalaryEvent> getSalaryevents();

  /**
   * Returns the value of the '<em><b>Commencement Of Employment Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Commencement Of Employment Date</em>' attribute.
   * @see #isSetCommencementOfEmploymentDate()
   * @see #unsetCommencementOfEmploymentDate()
   * @see #setCommencementOfEmploymentDate(LocalDate)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getJobAppointment_CommencementOfEmploymentDate()
   * @model unsettable="true" dataType="goedegep.types.model.ELocalDate"
   * @generated
   */
  LocalDate getCommencementOfEmploymentDate();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.JobAppointment#getCommencementOfEmploymentDate <em>Commencement Of Employment Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Commencement Of Employment Date</em>' attribute.
   * @see #isSetCommencementOfEmploymentDate()
   * @see #unsetCommencementOfEmploymentDate()
   * @see #getCommencementOfEmploymentDate()
   * @generated
   */
  void setCommencementOfEmploymentDate(LocalDate value);

  /**
   * Unsets the value of the '{@link goedegep.finan.jobappointment.model.JobAppointment#getCommencementOfEmploymentDate <em>Commencement Of Employment Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCommencementOfEmploymentDate()
   * @see #getCommencementOfEmploymentDate()
   * @see #setCommencementOfEmploymentDate(LocalDate)
   * @generated
   */
  void unsetCommencementOfEmploymentDate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.jobappointment.model.JobAppointment#getCommencementOfEmploymentDate <em>Commencement Of Employment Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Commencement Of Employment Date</em>' attribute is set.
   * @see #unsetCommencementOfEmploymentDate()
   * @see #getCommencementOfEmploymentDate()
   * @see #setCommencementOfEmploymentDate(LocalDate)
   * @generated
   */
  boolean isSetCommencementOfEmploymentDate();

  /**
   * Returns the value of the '<em><b>Starting Salary</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Starting Salary</em>' attribute.
   * @see #isSetStartingSalary()
   * @see #unsetStartingSalary()
   * @see #setStartingSalary(PgCurrency)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getJobAppointment_StartingSalary()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getStartingSalary();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.JobAppointment#getStartingSalary <em>Starting Salary</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Starting Salary</em>' attribute.
   * @see #isSetStartingSalary()
   * @see #unsetStartingSalary()
   * @see #getStartingSalary()
   * @generated
   */
  void setStartingSalary(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.jobappointment.model.JobAppointment#getStartingSalary <em>Starting Salary</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetStartingSalary()
   * @see #getStartingSalary()
   * @see #setStartingSalary(PgCurrency)
   * @generated
   */
  void unsetStartingSalary();

  /**
   * Returns whether the value of the '{@link goedegep.finan.jobappointment.model.JobAppointment#getStartingSalary <em>Starting Salary</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Starting Salary</em>' attribute is set.
   * @see #unsetStartingSalary()
   * @see #getStartingSalary()
   * @see #setStartingSalary(PgCurrency)
   * @generated
   */
  boolean isSetStartingSalary();

  /**
   * Returns the value of the '<em><b>Starting Parttime Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Starting Parttime Percentage</em>' attribute.
   * @see #isSetStartingParttimePercentage()
   * @see #unsetStartingParttimePercentage()
   * @see #setStartingParttimePercentage(Integer)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getJobAppointment_StartingParttimePercentage()
   * @model unsettable="true"
   * @generated
   */
  Integer getStartingParttimePercentage();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.JobAppointment#getStartingParttimePercentage <em>Starting Parttime Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Starting Parttime Percentage</em>' attribute.
   * @see #isSetStartingParttimePercentage()
   * @see #unsetStartingParttimePercentage()
   * @see #getStartingParttimePercentage()
   * @generated
   */
  void setStartingParttimePercentage(Integer value);

  /**
   * Unsets the value of the '{@link goedegep.finan.jobappointment.model.JobAppointment#getStartingParttimePercentage <em>Starting Parttime Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetStartingParttimePercentage()
   * @see #getStartingParttimePercentage()
   * @see #setStartingParttimePercentage(Integer)
   * @generated
   */
  void unsetStartingParttimePercentage();

  /**
   * Returns whether the value of the '{@link goedegep.finan.jobappointment.model.JobAppointment#getStartingParttimePercentage <em>Starting Parttime Percentage</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Starting Parttime Percentage</em>' attribute is set.
   * @see #unsetStartingParttimePercentage()
   * @see #getStartingParttimePercentage()
   * @see #setStartingParttimePercentage(Integer)
   * @generated
   */
  boolean isSetStartingParttimePercentage();

} // JobAppointment
