/**
 */
package goedegep.finan.mortgage.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.finan.mortgage.model.MortgagePackage
 * @generated
 */
public interface MortgageFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  MortgageFactory eINSTANCE = goedegep.finan.mortgage.model.impl.MortgageFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Mortgage</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Mortgage</em>'.
   * @generated
   */
  Mortgage createMortgage();

  /**
   * Returns a new object of class '<em>Final Payment</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Final Payment</em>'.
   * @generated
   */
  FinalPayment createFinalPayment();

  /**
   * Returns a new object of class '<em>Periodic Payment</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Periodic Payment</em>'.
   * @generated
   */
  PeriodicPayment createPeriodicPayment();

  /**
   * Returns a new object of class '<em>New Interest Rate</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>New Interest Rate</em>'.
   * @generated
   */
  NewInterestRate createNewInterestRate();

  /**
   * Returns a new object of class '<em>Mortgages</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Mortgages</em>'.
   * @generated
   */
  Mortgages createMortgages();

  /**
   * Returns a new object of class '<em>Interest Compensation Mortgage</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Interest Compensation Mortgage</em>'.
   * @generated
   */
  InterestCompensationMortgage createInterestCompensationMortgage();

  /**
   * Returns a new object of class '<em>Compensation Payment</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Compensation Payment</em>'.
   * @generated
   */
  CompensationPayment createCompensationPayment();

  /**
   * Returns a new object of class '<em>Compensation Payments</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Compensation Payments</em>'.
   * @generated
   */
  CompensationPayments createCompensationPayments();

  /**
   * Returns a new object of class '<em>Periodic Payment With Compensation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Periodic Payment With Compensation</em>'.
   * @generated
   */
  PeriodicPaymentWithCompensation createPeriodicPaymentWithCompensation();

  /**
   * Returns a new object of class '<em>New Interest Rate With Compensation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>New Interest Rate With Compensation</em>'.
   * @generated
   */
  NewInterestRateWithCompensation createNewInterestRateWithCompensation();

  /**
   * Returns a new object of class '<em>Yearly Overview</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Yearly Overview</em>'.
   * @generated
   */
  MortgageYearlyOverview createMortgageYearlyOverview();

  /**
   * Returns a new object of class '<em>Interest Compensation Mortgage Yearly Overview</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Interest Compensation Mortgage Yearly Overview</em>'.
   * @generated
   */
  InterestCompensationMortgageYearlyOverview createInterestCompensationMortgageYearlyOverview();

  /**
   * Returns a new object of class '<em>Interest Rate Set</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Interest Rate Set</em>'.
   * @generated
   */
  InterestRateSet createInterestRateSet();

  /**
   * Returns a new object of class '<em>Rate</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Rate</em>'.
   * @generated
   */
  Rate createRate();

  /**
   * Returns a new object of class '<em>Yearly Overviews</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Yearly Overviews</em>'.
   * @generated
   */
  MortgageYearlyOverviews createMortgageYearlyOverviews();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  MortgagePackage getMortgagePackage();

} //MortgageFactory
