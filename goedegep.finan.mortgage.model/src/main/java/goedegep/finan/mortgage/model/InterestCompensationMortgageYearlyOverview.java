/**
 */
package goedegep.finan.mortgage.model;

import goedegep.util.money.PgCurrency;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interest Compensation Mortgage Yearly Overview</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationBorrower <em>Compensation Borrower</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getDecemberPayment <em>December Payment</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationPayment <em>Compensation Payment</em>}</li>
 * </ul>
 *
 * @see goedegep.finan.mortgage.model.MortgagePackage#getInterestCompensationMortgageYearlyOverview()
 * @model
 * @generated
 */
public interface InterestCompensationMortgageYearlyOverview extends MortgageYearlyOverview {
  /**
   * Returns the value of the '<em><b>Compensation Borrower</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Compensation Borrower</em>' attribute.
   * @see #isSetCompensationBorrower()
   * @see #unsetCompensationBorrower()
   * @see #setCompensationBorrower(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getInterestCompensationMortgageYearlyOverview_CompensationBorrower()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getCompensationBorrower();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationBorrower <em>Compensation Borrower</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Compensation Borrower</em>' attribute.
   * @see #isSetCompensationBorrower()
   * @see #unsetCompensationBorrower()
   * @see #getCompensationBorrower()
   * @generated
   */
  void setCompensationBorrower(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationBorrower <em>Compensation Borrower</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCompensationBorrower()
   * @see #getCompensationBorrower()
   * @see #setCompensationBorrower(PgCurrency)
   * @generated
   */
  void unsetCompensationBorrower();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationBorrower <em>Compensation Borrower</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Compensation Borrower</em>' attribute is set.
   * @see #unsetCompensationBorrower()
   * @see #getCompensationBorrower()
   * @see #setCompensationBorrower(PgCurrency)
   * @generated
   */
  boolean isSetCompensationBorrower();

  /**
   * Returns the value of the '<em><b>December Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>December Payment</em>' attribute.
   * @see #isSetDecemberPayment()
   * @see #unsetDecemberPayment()
   * @see #setDecemberPayment(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getInterestCompensationMortgageYearlyOverview_DecemberPayment()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getDecemberPayment();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getDecemberPayment <em>December Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>December Payment</em>' attribute.
   * @see #isSetDecemberPayment()
   * @see #unsetDecemberPayment()
   * @see #getDecemberPayment()
   * @generated
   */
  void setDecemberPayment(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getDecemberPayment <em>December Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDecemberPayment()
   * @see #getDecemberPayment()
   * @see #setDecemberPayment(PgCurrency)
   * @generated
   */
  void unsetDecemberPayment();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getDecemberPayment <em>December Payment</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>December Payment</em>' attribute is set.
   * @see #unsetDecemberPayment()
   * @see #getDecemberPayment()
   * @see #setDecemberPayment(PgCurrency)
   * @generated
   */
  boolean isSetDecemberPayment();

  /**
   * Returns the value of the '<em><b>Compensation Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Compensation Payment</em>' attribute.
   * @see #isSetCompensationPayment()
   * @see #unsetCompensationPayment()
   * @see #setCompensationPayment(PgCurrency)
   * @see goedegep.finan.mortgage.model.MortgagePackage#getInterestCompensationMortgageYearlyOverview_CompensationPayment()
   * @model unsettable="true" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getCompensationPayment();

  /**
   * Sets the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationPayment <em>Compensation Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Compensation Payment</em>' attribute.
   * @see #isSetCompensationPayment()
   * @see #unsetCompensationPayment()
   * @see #getCompensationPayment()
   * @generated
   */
  void setCompensationPayment(PgCurrency value);

  /**
   * Unsets the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationPayment <em>Compensation Payment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCompensationPayment()
   * @see #getCompensationPayment()
   * @see #setCompensationPayment(PgCurrency)
   * @generated
   */
  void unsetCompensationPayment();

  /**
   * Returns whether the value of the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationPayment <em>Compensation Payment</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Compensation Payment</em>' attribute is set.
   * @see #unsetCompensationPayment()
   * @see #getCompensationPayment()
   * @see #setCompensationPayment(PgCurrency)
   * @generated
   */
  boolean isSetCompensationPayment();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model kind="operation" dataType="goedegep.types.model.EMoney"
   * @generated
   */
  PgCurrency getCompensationToBePaid();

} // InterestCompensationMortgageYearlyOverview
