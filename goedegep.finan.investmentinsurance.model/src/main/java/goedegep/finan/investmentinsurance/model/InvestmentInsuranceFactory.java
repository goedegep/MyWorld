/**
 */
package goedegep.finan.investmentinsurance.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage
 * @generated
 */
public interface InvestmentInsuranceFactory extends EFactory {
	/**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	InvestmentInsuranceFactory eINSTANCE = goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceFactoryImpl.init();

	/**
   * Returns a new object of class '<em>Investment Insurance</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Investment Insurance</em>'.
   * @generated
   */
  InvestmentInsurance createInvestmentInsurance();

  /**
   * Returns a new object of class '<em>Investment Insurances Data</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Investment Insurances Data</em>'.
   * @generated
   */
  InvestmentInsurancesData createInvestmentInsurancesData();

  /**
   * Returns a new object of class '<em>Insurance Company</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Insurance Company</em>'.
   * @generated
   */
  InsuranceCompany createInsuranceCompany();

  /**
   * Returns a new object of class '<em>Annual Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Annual Statement</em>'.
   * @generated
   */
  AnnualStatement createAnnualStatement();

  /**
   * Returns a new object of class '<em>Investment Fund</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Investment Fund</em>'.
   * @generated
   */
  InvestmentFund createInvestmentFund();

  /**
   * Returns a new object of class '<em>Participation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Participation</em>'.
   * @generated
   */
  Participation createParticipation();

  /**
   * Returns a new object of class '<em>Investment Part</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Investment Part</em>'.
   * @generated
   */
  InvestmentPart createInvestmentPart();

  /**
   * Returns a new object of class '<em>Fund Change</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Fund Change</em>'.
   * @generated
   */
  FundChange createFundChange();

  /**
   * Returns a new object of class '<em>Extra Investment</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Extra Investment</em>'.
   * @generated
   */
  ExtraInvestment createExtraInvestment();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  InvestmentInsurancePackage getInvestmentInsurancePackage();

} //BeleggingsVerzekeringFactory
