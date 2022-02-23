/**
 */
package goedegep.finan.mortgage.model;

import goedegep.util.money.PgCurrency;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Periodic Payment With Compensation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation#getBorrowerCompensation <em>Borrower Compensation</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation#getDecemberPaymentAccumulation <em>December Payment Accumulation</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getPeriodicPaymentWithCompensation()
 * @model
 * @generated
 */
public interface PeriodicPaymentWithCompensation extends PeriodicPayment {
  /**
   * Returns the value of the '<em><b>Borrower Compensation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Borrower Compensation</em>' attribute.
   * @see #isSetBorrowerCompensation()
   * @see #unsetBorrowerCompensation()
   * @see #setBorrowerCompensation(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getPeriodicPaymentWithCompensation_BorrowerCompensation()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getBorrowerCompensation();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation#getBorrowerCompensation <em>Borrower Compensation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Borrower Compensation</em>' attribute.
   * @see #isSetBorrowerCompensation()
   * @see #unsetBorrowerCompensation()
   * @see #getBorrowerCompensation()
   * @generated
   */
  void setBorrowerCompensation(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation#getBorrowerCompensation <em>Borrower Compensation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetBorrowerCompensation()
   * @see #getBorrowerCompensation()
   * @see #setBorrowerCompensation(PgCurrency)
   * @generated
   */
  void unsetBorrowerCompensation();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation#getBorrowerCompensation <em>Borrower Compensation</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Borrower Compensation</em>' attribute is set.
   * @see #unsetBorrowerCompensation()
   * @see #getBorrowerCompensation()
   * @see #setBorrowerCompensation(PgCurrency)
   * @generated
   */
  boolean isSetBorrowerCompensation();

  /**
   * Returns the value of the '<em><b>December Payment Accumulation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>December Payment Accumulation</em>' attribute.
   * @see #isSetDecemberPaymentAccumulation()
   * @see #unsetDecemberPaymentAccumulation()
   * @see #setDecemberPaymentAccumulation(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getPeriodicPaymentWithCompensation_DecemberPaymentAccumulation()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getDecemberPaymentAccumulation();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation#getDecemberPaymentAccumulation <em>December Payment Accumulation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>December Payment Accumulation</em>' attribute.
   * @see #isSetDecemberPaymentAccumulation()
   * @see #unsetDecemberPaymentAccumulation()
   * @see #getDecemberPaymentAccumulation()
   * @generated
   */
  void setDecemberPaymentAccumulation(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation#getDecemberPaymentAccumulation <em>December Payment Accumulation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDecemberPaymentAccumulation()
   * @see #getDecemberPaymentAccumulation()
   * @see #setDecemberPaymentAccumulation(PgCurrency)
   * @generated
   */
  void unsetDecemberPaymentAccumulation();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation#getDecemberPaymentAccumulation <em>December Payment Accumulation</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>December Payment Accumulation</em>' attribute is set.
   * @see #unsetDecemberPaymentAccumulation()
   * @see #getDecemberPaymentAccumulation()
   * @see #setDecemberPaymentAccumulation(PgCurrency)
   * @generated
   */
  boolean isSetDecemberPaymentAccumulation();

} // PeriodicPaymentWithCompensation
