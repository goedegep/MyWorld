/**
 */
package goedegep.finan.mortgage.model;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Periodic Payment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This class represents a periodic, usually monthly, payment.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.PeriodicPayment#getPayment <em>Payment</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.PeriodicPayment#getInterest <em>Interest</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.PeriodicPayment#getBalanceAfterPayment <em>Balance After Payment</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.PeriodicPayment#getInterestRate <em>Interest Rate</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.PeriodicPayment#getNextPaymentDate <em>Next Payment Date</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getPeriodicPayment()
 * @model
 * @generated
 */
public interface PeriodicPayment extends MortgageEvent {
  /**
   * Returns the value of the '<em><b>Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The amount paid this period.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Payment</em>' attribute.
   * @see #isSetPayment()
   * @see #unsetPayment()
   * @see #setPayment(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getPeriodicPayment_Payment()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getPayment();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getPayment <em>Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Payment</em>' attribute.
   * @see #isSetPayment()
   * @see #unsetPayment()
   * @see #getPayment()
   * @generated
   */
  void setPayment(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getPayment <em>Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetPayment()
   * @see #getPayment()
   * @see #setPayment(PgCurrency)
   * @generated
   */
  void unsetPayment();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getPayment <em>Payment</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Payment</em>' attribute is set.
   * @see #unsetPayment()
   * @see #getPayment()
   * @see #setPayment(PgCurrency)
   * @generated
   */
  boolean isSetPayment();

  /**
   * Returns the value of the '<em><b>Interest</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Interest paid this period. The payment towards the principal is the payment minus this interest.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Interest</em>' attribute.
   * @see #isSetInterest()
   * @see #unsetInterest()
   * @see #setInterest(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getPeriodicPayment_Interest()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getInterest();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getInterest <em>Interest</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interest</em>' attribute.
   * @see #isSetInterest()
   * @see #unsetInterest()
   * @see #getInterest()
   * @generated
   */
  void setInterest(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getInterest <em>Interest</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetInterest()
   * @see #getInterest()
   * @see #setInterest(PgCurrency)
   * @generated
   */
  void unsetInterest();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getInterest <em>Interest</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Interest</em>' attribute is set.
   * @see #unsetInterest()
   * @see #getInterest()
   * @see #setInterest(PgCurrency)
   * @generated
   */
  boolean isSetInterest();

  /**
   * Returns the value of the '<em><b>Balance After Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * New debt (after this payment).
   * <!-- end-model-doc -->
   * @return the value of the '<em>Balance After Payment</em>' attribute.
   * @see #isSetBalanceAfterPayment()
   * @see #unsetBalanceAfterPayment()
   * @see #setBalanceAfterPayment(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getPeriodicPayment_BalanceAfterPayment()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getBalanceAfterPayment();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getBalanceAfterPayment <em>Balance After Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Balance After Payment</em>' attribute.
   * @see #isSetBalanceAfterPayment()
   * @see #unsetBalanceAfterPayment()
   * @see #getBalanceAfterPayment()
   * @generated
   */
  void setBalanceAfterPayment(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getBalanceAfterPayment <em>Balance After Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBalanceAfterPayment()
   * @see #getBalanceAfterPayment()
   * @see #setBalanceAfterPayment(PgCurrency)
   * @generated
   */
  void unsetBalanceAfterPayment();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getBalanceAfterPayment <em>Balance After Payment</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Balance After Payment</em>' attribute is set.
   * @see #unsetBalanceAfterPayment()
   * @see #getBalanceAfterPayment()
   * @see #setBalanceAfterPayment(PgCurrency)
   * @generated
   */
  boolean isSetBalanceAfterPayment();

  /**
   * Returns the value of the '<em><b>Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Interest rate for this period. If the interest rate changes each month, this can be specified in this payment. Instead of having a NewInterestRate event each month.
   * 
   * <!-- end-model-doc -->
   * @return the value of the '<em>Interest Rate</em>' attribute.
   * @see #isSetInterestRate()
   * @see #unsetInterestRate()
   * @see #setInterestRate(FixedPointValue)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getPeriodicPayment_InterestRate()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
  FixedPointValue getInterestRate();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getInterestRate <em>Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interest Rate</em>' attribute.
   * @see #isSetInterestRate()
   * @see #unsetInterestRate()
   * @see #getInterestRate()
   * @generated
   */
  void setInterestRate(FixedPointValue value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getInterestRate <em>Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetInterestRate()
   * @see #getInterestRate()
   * @see #setInterestRate(FixedPointValue)
   * @generated
   */
  void unsetInterestRate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getInterestRate <em>Interest Rate</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Interest Rate</em>' attribute is set.
   * @see #unsetInterestRate()
   * @see #getInterestRate()
   * @see #setInterestRate(FixedPointValue)
   * @generated
   */
  boolean isSetInterestRate();

  /**
   * Returns the value of the '<em><b>Next Payment Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Date for the next periodic payment.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Next Payment Date</em>' attribute.
   * @see #isSetNextPaymentDate()
   * @see #unsetNextPaymentDate()
   * @see #setNextPaymentDate(Date)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getPeriodicPayment_NextPaymentDate()
   * @model unsettable="true"
   * @generated
   */
  Date getNextPaymentDate();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getNextPaymentDate <em>Next Payment Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Next Payment Date</em>' attribute.
   * @see #isSetNextPaymentDate()
   * @see #unsetNextPaymentDate()
   * @see #getNextPaymentDate()
   * @generated
   */
  void setNextPaymentDate(Date value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getNextPaymentDate <em>Next Payment Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetNextPaymentDate()
   * @see #getNextPaymentDate()
   * @see #setNextPaymentDate(Date)
   * @generated
   */
  void unsetNextPaymentDate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.PeriodicPayment#getNextPaymentDate <em>Next Payment Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Next Payment Date</em>' attribute is set.
   * @see #unsetNextPaymentDate()
   * @see #getNextPaymentDate()
   * @see #setNextPaymentDate(Date)
   * @generated
   */
  boolean isSetNextPaymentDate();

} // PeriodicPayment
