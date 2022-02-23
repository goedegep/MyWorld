/**
 */
package goedegep.finan.mortgage.model;

import goedegep.util.money.PgCurrency;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Yearly Overview</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getDebtAtBeginningOfYear <em>Debt At Beginning Of Year</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getDebtAtEndOfYear <em>Debt At End Of Year</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getInterestPaid <em>Interest Paid</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getRepayment <em>Repayment</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getYear <em>Year</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgageYearlyOverview()
 * @model
 * @generated
 */
public interface MortgageYearlyOverview extends EObject {
  /**
   * Returns the value of the '<em><b>Debt At Beginning Of Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Debt At Beginning Of Year</em>' attribute.
   * @see #isSetDebtAtBeginningOfYear()
   * @see #unsetDebtAtBeginningOfYear()
   * @see #setDebtAtBeginningOfYear(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgageYearlyOverview_DebtAtBeginningOfYear()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getDebtAtBeginningOfYear();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getDebtAtBeginningOfYear <em>Debt At Beginning Of Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Debt At Beginning Of Year</em>' attribute.
   * @see #isSetDebtAtBeginningOfYear()
   * @see #unsetDebtAtBeginningOfYear()
   * @see #getDebtAtBeginningOfYear()
   * @generated
   */
  void setDebtAtBeginningOfYear(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getDebtAtBeginningOfYear <em>Debt At Beginning Of Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDebtAtBeginningOfYear()
   * @see #getDebtAtBeginningOfYear()
   * @see #setDebtAtBeginningOfYear(PgCurrency)
   * @generated
   */
  void unsetDebtAtBeginningOfYear();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getDebtAtBeginningOfYear <em>Debt At Beginning Of Year</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Debt At Beginning Of Year</em>' attribute is set.
   * @see #unsetDebtAtBeginningOfYear()
   * @see #getDebtAtBeginningOfYear()
   * @see #setDebtAtBeginningOfYear(PgCurrency)
   * @generated
   */
  boolean isSetDebtAtBeginningOfYear();

  /**
   * Returns the value of the '<em><b>Debt At End Of Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Debt At End Of Year</em>' attribute.
   * @see #isSetDebtAtEndOfYear()
   * @see #unsetDebtAtEndOfYear()
   * @see #setDebtAtEndOfYear(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgageYearlyOverview_DebtAtEndOfYear()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getDebtAtEndOfYear();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getDebtAtEndOfYear <em>Debt At End Of Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Debt At End Of Year</em>' attribute.
   * @see #isSetDebtAtEndOfYear()
   * @see #unsetDebtAtEndOfYear()
   * @see #getDebtAtEndOfYear()
   * @generated
   */
  void setDebtAtEndOfYear(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getDebtAtEndOfYear <em>Debt At End Of Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDebtAtEndOfYear()
   * @see #getDebtAtEndOfYear()
   * @see #setDebtAtEndOfYear(PgCurrency)
   * @generated
   */
  void unsetDebtAtEndOfYear();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getDebtAtEndOfYear <em>Debt At End Of Year</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Debt At End Of Year</em>' attribute is set.
   * @see #unsetDebtAtEndOfYear()
   * @see #getDebtAtEndOfYear()
   * @see #setDebtAtEndOfYear(PgCurrency)
   * @generated
   */
  boolean isSetDebtAtEndOfYear();

  /**
   * Returns the value of the '<em><b>Interest Paid</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Interest Paid</em>' attribute.
   * @see #isSetInterestPaid()
   * @see #unsetInterestPaid()
   * @see #setInterestPaid(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgageYearlyOverview_InterestPaid()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getInterestPaid();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getInterestPaid <em>Interest Paid</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interest Paid</em>' attribute.
   * @see #isSetInterestPaid()
   * @see #unsetInterestPaid()
   * @see #getInterestPaid()
   * @generated
   */
  void setInterestPaid(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getInterestPaid <em>Interest Paid</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetInterestPaid()
   * @see #getInterestPaid()
   * @see #setInterestPaid(PgCurrency)
   * @generated
   */
  void unsetInterestPaid();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getInterestPaid <em>Interest Paid</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Interest Paid</em>' attribute is set.
   * @see #unsetInterestPaid()
   * @see #getInterestPaid()
   * @see #setInterestPaid(PgCurrency)
   * @generated
   */
  boolean isSetInterestPaid();

  /**
   * Returns the value of the '<em><b>Repayment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Repayment</em>' attribute.
   * @see #isSetRepayment()
   * @see #unsetRepayment()
   * @see #setRepayment(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgageYearlyOverview_Repayment()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getRepayment();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getRepayment <em>Repayment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Repayment</em>' attribute.
   * @see #isSetRepayment()
   * @see #unsetRepayment()
   * @see #getRepayment()
   * @generated
   */
  void setRepayment(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getRepayment <em>Repayment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetRepayment()
   * @see #getRepayment()
   * @see #setRepayment(PgCurrency)
   * @generated
   */
  void unsetRepayment();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getRepayment <em>Repayment</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Repayment</em>' attribute is set.
   * @see #unsetRepayment()
   * @see #getRepayment()
   * @see #setRepayment(PgCurrency)
   * @generated
   */
  boolean isSetRepayment();

  /**
   * Returns the value of the '<em><b>Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Year</em>' attribute.
   * @see #isSetYear()
   * @see #unsetYear()
   * @see #setYear(Integer)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getMortgageYearlyOverview_Year()
   * @model unsettable="true"
   * @generated
   */
  Integer getYear();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getYear <em>Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Year</em>' attribute.
   * @see #isSetYear()
   * @see #unsetYear()
   * @see #getYear()
   * @generated
   */
  void setYear(Integer value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getYear <em>Year</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetYear()
   * @see #getYear()
   * @see #setYear(Integer)
   * @generated
   */
  void unsetYear();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getYear <em>Year</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Year</em>' attribute is set.
   * @see #unsetYear()
   * @see #getYear()
   * @see #setYear(Integer)
   * @generated
   */
  boolean isSetYear();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getPaymentsTotal();

} // MortgageYearlyOverview
