/**
 */
package goedegep.finan.mortgage.model;

import java.util.Date;

import goedegep.util.fixedpointvalue.FixedPointValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>New Interest Rate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.NewInterestRate#getInterestRate <em>Interest Rate</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.NewInterestRate#getFixedInterestPeriod <em>Fixed Interest Period</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.NewInterestRate#getStartingDate <em>Starting Date</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getNewInterestRate()
 * @model
 * @generated
 */
public interface NewInterestRate extends MortgageEvent {
  /**
   * Returns the value of the '<em><b>Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The new interest rate in centi-percent.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Interest Rate</em>' attribute.
   * @see #isSetInterestRate()
   * @see #unsetInterestRate()
   * @see #setInterestRate(FixedPointValue)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getNewInterestRate_InterestRate()
   * @model unsettable="true" dataType="goedegep.types.model.EFixedPointValue"
   * @generated
   */
  FixedPointValue getInterestRate();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.NewInterestRate#getInterestRate <em>Interest Rate</em>}' attribute.
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
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.NewInterestRate#getInterestRate <em>Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetInterestRate()
   * @see #getInterestRate()
   * @see #setInterestRate(FixedPointValue)
   * @generated
   */
  void unsetInterestRate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.NewInterestRate#getInterestRate <em>Interest Rate</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Fixed Interest Period</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * New period, in months, during which the interest rate is fixed.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Fixed Interest Period</em>' attribute.
   * @see #isSetFixedInterestPeriod()
   * @see #unsetFixedInterestPeriod()
   * @see #setFixedInterestPeriod(int)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getNewInterestRate_FixedInterestPeriod()
   * @model unsettable="true"
   * @generated
   */
  int getFixedInterestPeriod();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.NewInterestRate#getFixedInterestPeriod <em>Fixed Interest Period</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fixed Interest Period</em>' attribute.
   * @see #isSetFixedInterestPeriod()
   * @see #unsetFixedInterestPeriod()
   * @see #getFixedInterestPeriod()
   * @generated
   */
  void setFixedInterestPeriod(int value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.NewInterestRate#getFixedInterestPeriod <em>Fixed Interest Period</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFixedInterestPeriod()
   * @see #getFixedInterestPeriod()
   * @see #setFixedInterestPeriod(int)
   * @generated
   */
  void unsetFixedInterestPeriod();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.NewInterestRate#getFixedInterestPeriod <em>Fixed Interest Period</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Fixed Interest Period</em>' attribute is set.
   * @see #unsetFixedInterestPeriod()
   * @see #getFixedInterestPeriod()
   * @see #setFixedInterestPeriod(int)
   * @generated
   */
  boolean isSetFixedInterestPeriod();

  /**
   * Returns the value of the '<em><b>Starting Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * Date from which the new rate applies.
   * <!-- end-model-doc -->
   * @return the value of the '<em>Starting Date</em>' attribute.
   * @see #isSetStartingDate()
   * @see #unsetStartingDate()
   * @see #setStartingDate(Date)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getNewInterestRate_StartingDate()
   * @model unsettable="true"
   * @generated
   */
  Date getStartingDate();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.NewInterestRate#getStartingDate <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Starting Date</em>' attribute.
   * @see #isSetStartingDate()
   * @see #unsetStartingDate()
   * @see #getStartingDate()
   * @generated
   */
  void setStartingDate(Date value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.NewInterestRate#getStartingDate <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetStartingDate()
   * @see #getStartingDate()
   * @see #setStartingDate(Date)
   * @generated
   */
  void unsetStartingDate();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.NewInterestRate#getStartingDate <em>Starting Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Starting Date</em>' attribute is set.
   * @see #unsetStartingDate()
   * @see #getStartingDate()
   * @see #setStartingDate(Date)
   * @generated
   */
  boolean isSetStartingDate();

} // NewInterestRate
