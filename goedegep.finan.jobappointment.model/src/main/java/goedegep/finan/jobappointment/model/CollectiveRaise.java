/**
 */
package goedegep.finan.jobappointment.model;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Collective Raise</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.jobappointment.model.CollectiveRaise#getPercentage <em>Percentage</em>}</li>
 *   <li>{@link goedegep.finan.jobappointment.model.CollectiveRaise#getAmount <em>Amount</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getCollectiveRaise()
 * @model
 * @generated
 */
public interface CollectiveRaise extends SalaryEvent {

  /**
   * Returns the value of the '<em><b>Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Percentage</em>' attribute.
   * @see #setPercentage(FixedPointValue)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getCollectiveRaise_Percentage()
   * @model dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
  FixedPointValue getPercentage();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.CollectiveRaise#getPercentage <em>Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Percentage</em>' attribute.
   * @see #getPercentage()
   * @generated
   */
  void setPercentage(FixedPointValue value);

  /**
   * Returns the value of the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Amount</em>' attribute.
   * @see #setAmount(PgCurrency)
   * @see goedegep.finan.jobappointment.model.JobAppointmentPackage#getCollectiveRaise_Amount()
   * @model dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getAmount();

  /**
   * Sets the value of the '{@link goedegep.finan.jobappointment.model.CollectiveRaise#getAmount <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Amount</em>' attribute.
   * @see #getAmount()
   * @generated
   */
  void setAmount(PgCurrency value);
} // CollectiveRaise
