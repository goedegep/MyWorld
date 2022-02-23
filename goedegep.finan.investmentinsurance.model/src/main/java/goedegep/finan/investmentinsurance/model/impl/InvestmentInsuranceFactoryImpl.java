/**
 */
package goedegep.finan.investmentinsurance.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import goedegep.finan.investmentinsurance.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InvestmentInsuranceFactoryImpl extends EFactoryImpl implements InvestmentInsuranceFactory {
	/**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public static InvestmentInsuranceFactory init() {
    try {
      InvestmentInsuranceFactory theInvestmentInsuranceFactory = (InvestmentInsuranceFactory)EPackage.Registry.INSTANCE.getEFactory(InvestmentInsurancePackage.eNS_URI);
      if (theInvestmentInsuranceFactory != null) {
        return theInvestmentInsuranceFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new InvestmentInsuranceFactoryImpl();
  }

	/**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public InvestmentInsuranceFactoryImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE: return createInvestmentInsurance();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA: return createInvestmentInsurancesData();
      case InvestmentInsurancePackage.INSURANCE_COMPANY: return createInsuranceCompany();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT: return createAnnualStatement();
      case InvestmentInsurancePackage.INVESTMENT_FUND: return createInvestmentFund();
      case InvestmentInsurancePackage.PARTICIPATION: return createParticipation();
      case InvestmentInsurancePackage.INVESTMENT_PART: return createInvestmentPart();
      case InvestmentInsurancePackage.FUND_CHANGE: return createFundChange();
      case InvestmentInsurancePackage.EXTRA_INVESTMENT: return createExtraInvestment();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvestmentInsurance createInvestmentInsurance() {
    InvestmentInsuranceImpl investmentInsurance = new InvestmentInsuranceImpl();
    return investmentInsurance;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvestmentInsurancesData createInvestmentInsurancesData() {
    InvestmentInsurancesDataImpl investmentInsurancesData = new InvestmentInsurancesDataImpl();
    return investmentInsurancesData;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InsuranceCompany createInsuranceCompany() {
    InsuranceCompanyImpl insuranceCompany = new InsuranceCompanyImpl();
    return insuranceCompany;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AnnualStatement createAnnualStatement() {
    AnnualStatementImpl annualStatement = new AnnualStatementImpl();
    return annualStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvestmentFund createInvestmentFund() {
    InvestmentFundImpl investmentFund = new InvestmentFundImpl();
    return investmentFund;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Participation createParticipation() {
    ParticipationImpl participation = new ParticipationImpl();
    return participation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvestmentPart createInvestmentPart() {
    InvestmentPartImpl investmentPart = new InvestmentPartImpl();
    return investmentPart;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FundChange createFundChange() {
    FundChangeImpl fundChange = new FundChangeImpl();
    return fundChange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExtraInvestment createExtraInvestment() {
    ExtraInvestmentImpl extraInvestment = new ExtraInvestmentImpl();
    return extraInvestment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvestmentInsurancePackage getInvestmentInsurancePackage() {
    return (InvestmentInsurancePackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
	   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
	@Deprecated
	public static InvestmentInsurancePackage getPackage() {
    return InvestmentInsurancePackage.eINSTANCE;
  }

} //BeleggingsVerzekeringFactoryImpl
