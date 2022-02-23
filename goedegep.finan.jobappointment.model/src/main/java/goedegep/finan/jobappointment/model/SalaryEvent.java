/**
 */
package goedegep.finan.jobappointment.model;

import goedegep.types.model.Event;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Salary Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.jobappointment.model.SalaryEvent#getSalaryEventType <em>Salary Event Type</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryEvent()
 * @model abstract="true"
 * @generated
 */
public interface SalaryEvent extends Event {

  /**
   * Returns the value of the '<em><b>Salary Event Type</b></em>' attribute.
   * The literals are from the enumeration {@link goedegep.finan.jobappointment.model.SalaryEventType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Salary Event Type</em>' attribute.
   * @see goedegep.finan.jobappointment.model.SalaryEventType
   * @see #setSalaryEventType(SalaryEventType)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getSalaryEvent_SalaryEventType()
   * @model
   * @generated
   */
  SalaryEventType getSalaryEventType();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.SalaryEvent#getSalaryEventType <em>Salary Event Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Salary Event Type</em>' attribute.
   * @see goedegep.finan.jobappointment.model.SalaryEventType
   * @see #getSalaryEventType()
   * @generated
   */
  void setSalaryEventType(SalaryEventType value);
} // SalaryEvent
