/**
 */
package goedegep.finan.investmentinsurance.model.impl;

import goedegep.finan.investmentinsurance.model.AnnualStatement;
import goedegep.finan.investmentinsurance.model.InvestmentInsuranceFactory;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage;
import goedegep.finan.investmentinsurance.model.ExtraInvestment;
import goedegep.finan.investmentinsurance.model.FundChange;
import goedegep.finan.investmentinsurance.model.InsuranceCompany;
import goedegep.finan.investmentinsurance.model.InvestmentFund;
import goedegep.finan.investmentinsurance.model.InvestmentInsurance;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancesData;
import goedegep.finan.investmentinsurance.model.InvestmentPart;
import goedegep.finan.investmentinsurance.model.Participation;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.types.model.TypesPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InvestmentInsurancePackageImpl extends EPackageImpl implements InvestmentInsurancePackage {
	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass investmentInsuranceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass investmentInsurancesDataEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass insuranceCompanyEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass annualStatementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass investmentFundEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass participationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass investmentPartEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fundChangeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass extraInvestmentEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage#eNS_URI
   * @see #init()
   * @generated
   */
	private InvestmentInsurancePackageImpl() {
    super(eNS_URI, InvestmentInsuranceFactory.eINSTANCE);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private static boolean isInited = false;

	/**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   *
   * <p>This method is used to initialize {@link InvestmentInsurancePackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
	public static InvestmentInsurancePackage init() {
    if (isInited) return (InvestmentInsurancePackage)EPackage.Registry.INSTANCE.getEPackage(InvestmentInsurancePackage.eNS_URI);

    // Obtain or create and register package
    Object registeredInvestmentInsurancePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    InvestmentInsurancePackageImpl theInvestmentInsurancePackage = registeredInvestmentInsurancePackage instanceof InvestmentInsurancePackageImpl ? (InvestmentInsurancePackageImpl)registeredInvestmentInsurancePackage : new InvestmentInsurancePackageImpl();

    isInited = true;

    // Initialize simple dependencies
    RolodexPackage.eINSTANCE.eClass();
    TypesPackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theInvestmentInsurancePackage.createPackageContents();

    // Initialize created meta-data
    theInvestmentInsurancePackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theInvestmentInsurancePackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(InvestmentInsurancePackage.eNS_URI, theInvestmentInsurancePackage);
    return theInvestmentInsurancePackage;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInvestmentInsurance() {
    return investmentInsuranceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvestmentInsurance_InsuranceCompany() {
    return (EReference)investmentInsuranceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvestmentInsurance_PolicyNumber() {
    return (EAttribute)investmentInsuranceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvestmentInsurance_StartingDate() {
    return (EAttribute)investmentInsuranceEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvestmentInsurance_PolicyHolder() {
    return (EReference)investmentInsuranceEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvestmentInsurance_Premium() {
    return (EAttribute)investmentInsuranceEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvestmentInsurance_InsuredBenefitOnDeath() {
    return (EAttribute)investmentInsuranceEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvestmentInsurance_InvestmentParts() {
    return (EReference)investmentInsuranceEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvestmentInsurance_Events() {
    return (EReference)investmentInsuranceEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getInvestmentInsurance__GetLastKnownValue() {
    return investmentInsuranceEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getInvestmentInsurance__GetDateForLastKnownValue() {
    return investmentInsuranceEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getInvestmentInsurance__GetLastAnnualStatement() {
    return investmentInsuranceEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getInvestmentInsurance__GetAverageReturnOnInvestment() {
    return investmentInsuranceEClass.getEOperations().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInvestmentInsurancesData() {
    return investmentInsurancesDataEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvestmentInsurancesData_InsuranceCompanies() {
    return (EReference)investmentInsurancesDataEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvestmentInsurancesData_InvestmentInsurances() {
    return (EReference)investmentInsurancesDataEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvestmentInsurancesData_PersonList() {
    return (EReference)investmentInsurancesDataEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvestmentInsurancesData_AddressList() {
    return (EReference)investmentInsurancesDataEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvestmentInsurancesData_CityList() {
    return (EReference)investmentInsurancesDataEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvestmentInsurancesData_PhoneNumberList() {
    return (EReference)investmentInsurancesDataEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getInvestmentInsurancesData__GetLastKnownTotalValue() {
    return investmentInsurancesDataEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getInvestmentInsurancesData__GetDateForLastKnownTotalValue() {
    return investmentInsurancesDataEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getInvestmentInsurancesData__GetTotalPremium() {
    return investmentInsurancesDataEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getInvestmentInsurancesData__GetAverageTotalReturnOnInvestment() {
    return investmentInsurancesDataEClass.getEOperations().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInsuranceCompany() {
    return insuranceCompanyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInsuranceCompany_InvestmentFunds() {
    return (EReference)insuranceCompanyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInsuranceCompany_Department() {
    return (EAttribute)insuranceCompanyEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInsuranceCompany_Website() {
    return (EAttribute)insuranceCompanyEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAnnualStatement() {
    return annualStatementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_PeriodFrom() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_PeriodUntil() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_PremiumPaid() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_PremiumDeathRiskCoverage() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_PremiumOccupationalDisabilityRiskCoverage() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_UpkeepPremium() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_FirstCostsInsuranceCompany() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_ContinuingCostsInsuranceCompany() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_ManagementCosts() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_BuyAndSellCosts() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_MutationCosts() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_CostsRestitution() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_Corrections() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_EarnedOnTheParticipations() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAnnualStatement_Participations() {
    return (EReference)annualStatementEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_ExampleCapitalOnEndDate() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAnnualStatement_ExpectedYearlyCostsIncrease() {
    return (EAttribute)annualStatementEClass.getEStructuralFeatures().get(16);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getAnnualStatement__TotalCosts() {
    return annualStatementEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getAnnualStatement__ValueAtYearStart() {
    return annualStatementEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getAnnualStatement__PercentageCosts() {
    return annualStatementEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getAnnualStatement__FixedCosts() {
    return annualStatementEClass.getEOperations().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInvestmentFund() {
    return investmentFundEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvestmentFund_Name() {
    return (EAttribute)investmentFundEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvestmentFund_FundInformation() {
    return (EAttribute)investmentFundEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvestmentFund_StockPrices() {
    return (EReference)investmentFundEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getInvestmentFund__GetStockPrice__LocalDate() {
    return investmentFundEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getParticipation() {
    return participationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getParticipation_InvestmentFund() {
    return (EReference)participationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ParticipationMutations() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ExampleReturnOnInvestmentNetHistoric() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ExampleReturnOnInvestmentGross() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ExampleReturnOnInvestmentGrossCompanyOwn() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ExampleReturnOnInvestmentPessimistic() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_TotalExpenseRatio() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ExampleCapitalNetHistoric() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ExampleCapitalGross() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ExampleCapitalGrossCompanyOwn() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ExampleCapitalPessimistic() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ReturnOnInvestmentReductionNetHistoric() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ExampleCapitalAfterReduction() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_DistributionPercentage() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_StandardFundReturnOnInvestment() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ExampleCapitalStandardFundReturnOnInvestment() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_NumberOfParticipationsEnd() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(16);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParticipation_ParticipationMutationsComplete() {
    return (EAttribute)participationEClass.getEStructuralFeatures().get(17);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getParticipation_AnnualStatement() {
    return (EReference)participationEClass.getEStructuralFeatures().get(18);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getParticipation__NumberOfParticipationsStart() {
    return participationEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getParticipation__EndValue() {
    return participationEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getParticipation__NumberOfParticipationsBought() {
    return participationEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getParticipation__NumberOfParticipationsSold() {
    return participationEClass.getEOperations().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInvestmentPart() {
    return investmentPartEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvestmentPart_InvestmentFund() {
    return (EReference)investmentPartEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvestmentPart_Percentage() {
    return (EAttribute)investmentPartEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFundChange() {
    return fundChangeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getFundChange_FromFund() {
    return (EReference)fundChangeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getFundChange_ToFund() {
    return (EReference)fundChangeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFundChange_FromNumberOfParticipations() {
    return (EAttribute)fundChangeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFundChange_ToNumberOfParticipations() {
    return (EAttribute)fundChangeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFundChange_FromStockPrice() {
    return (EAttribute)fundChangeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFundChange_ToStockPrice() {
    return (EAttribute)fundChangeEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFundChange_FromAverageHistoricReturnOnInvestment() {
    return (EAttribute)fundChangeEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFundChange_FromTER() {
    return (EAttribute)fundChangeEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFundChange_ToAverageHistoricReturnOnInvestment() {
    return (EAttribute)fundChangeEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFundChange_ToTER() {
    return (EAttribute)fundChangeEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getExtraInvestment() {
    return extraInvestmentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExtraInvestment_Premium() {
    return (EAttribute)extraInvestmentEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExtraInvestment_DepositDate() {
    return (EAttribute)extraInvestmentEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getExtraInvestment_InvestmentParts() {
    return (EReference)extraInvestmentEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExtraInvestment_OverviewDate() {
    return (EAttribute)extraInvestmentEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getExtraInvestment_Participations() {
    return (EReference)extraInvestmentEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvestmentInsuranceFactory getInvestmentInsuranceFactory() {
    return (InvestmentInsuranceFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
	   * <!-- end-user-doc -->
   * @generated
   */
	private boolean isCreated = false;

	/**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void createPackageContents() {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    investmentInsuranceEClass = createEClass(INVESTMENT_INSURANCE);
    createEReference(investmentInsuranceEClass, INVESTMENT_INSURANCE__INSURANCE_COMPANY);
    createEAttribute(investmentInsuranceEClass, INVESTMENT_INSURANCE__POLICY_NUMBER);
    createEAttribute(investmentInsuranceEClass, INVESTMENT_INSURANCE__STARTING_DATE);
    createEReference(investmentInsuranceEClass, INVESTMENT_INSURANCE__POLICY_HOLDER);
    createEAttribute(investmentInsuranceEClass, INVESTMENT_INSURANCE__PREMIUM);
    createEAttribute(investmentInsuranceEClass, INVESTMENT_INSURANCE__INSURED_BENEFIT_ON_DEATH);
    createEReference(investmentInsuranceEClass, INVESTMENT_INSURANCE__INVESTMENT_PARTS);
    createEReference(investmentInsuranceEClass, INVESTMENT_INSURANCE__EVENTS);
    createEOperation(investmentInsuranceEClass, INVESTMENT_INSURANCE___GET_LAST_KNOWN_VALUE);
    createEOperation(investmentInsuranceEClass, INVESTMENT_INSURANCE___GET_DATE_FOR_LAST_KNOWN_VALUE);
    createEOperation(investmentInsuranceEClass, INVESTMENT_INSURANCE___GET_LAST_ANNUAL_STATEMENT);
    createEOperation(investmentInsuranceEClass, INVESTMENT_INSURANCE___GET_AVERAGE_RETURN_ON_INVESTMENT);

    investmentInsurancesDataEClass = createEClass(INVESTMENT_INSURANCES_DATA);
    createEReference(investmentInsurancesDataEClass, INVESTMENT_INSURANCES_DATA__INSURANCE_COMPANIES);
    createEReference(investmentInsurancesDataEClass, INVESTMENT_INSURANCES_DATA__INVESTMENT_INSURANCES);
    createEReference(investmentInsurancesDataEClass, INVESTMENT_INSURANCES_DATA__PERSON_LIST);
    createEReference(investmentInsurancesDataEClass, INVESTMENT_INSURANCES_DATA__ADDRESS_LIST);
    createEReference(investmentInsurancesDataEClass, INVESTMENT_INSURANCES_DATA__CITY_LIST);
    createEReference(investmentInsurancesDataEClass, INVESTMENT_INSURANCES_DATA__PHONE_NUMBER_LIST);
    createEOperation(investmentInsurancesDataEClass, INVESTMENT_INSURANCES_DATA___GET_LAST_KNOWN_TOTAL_VALUE);
    createEOperation(investmentInsurancesDataEClass, INVESTMENT_INSURANCES_DATA___GET_DATE_FOR_LAST_KNOWN_TOTAL_VALUE);
    createEOperation(investmentInsurancesDataEClass, INVESTMENT_INSURANCES_DATA___GET_TOTAL_PREMIUM);
    createEOperation(investmentInsurancesDataEClass, INVESTMENT_INSURANCES_DATA___GET_AVERAGE_TOTAL_RETURN_ON_INVESTMENT);

    insuranceCompanyEClass = createEClass(INSURANCE_COMPANY);
    createEReference(insuranceCompanyEClass, INSURANCE_COMPANY__INVESTMENT_FUNDS);
    createEAttribute(insuranceCompanyEClass, INSURANCE_COMPANY__DEPARTMENT);
    createEAttribute(insuranceCompanyEClass, INSURANCE_COMPANY__WEBSITE);

    annualStatementEClass = createEClass(ANNUAL_STATEMENT);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__PERIOD_FROM);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__PERIOD_UNTIL);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__PREMIUM_PAID);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__PREMIUM_DEATH_RISK_COVERAGE);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__UPKEEP_PREMIUM);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__FIRST_COSTS_INSURANCE_COMPANY);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__CONTINUING_COSTS_INSURANCE_COMPANY);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__MANAGEMENT_COSTS);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__BUY_AND_SELL_COSTS);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__MUTATION_COSTS);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__COSTS_RESTITUTION);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__CORRECTIONS);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__EARNED_ON_THE_PARTICIPATIONS);
    createEReference(annualStatementEClass, ANNUAL_STATEMENT__PARTICIPATIONS);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__EXAMPLE_CAPITAL_ON_END_DATE);
    createEAttribute(annualStatementEClass, ANNUAL_STATEMENT__EXPECTED_YEARLY_COSTS_INCREASE);
    createEOperation(annualStatementEClass, ANNUAL_STATEMENT___TOTAL_COSTS);
    createEOperation(annualStatementEClass, ANNUAL_STATEMENT___VALUE_AT_YEAR_START);
    createEOperation(annualStatementEClass, ANNUAL_STATEMENT___PERCENTAGE_COSTS);
    createEOperation(annualStatementEClass, ANNUAL_STATEMENT___FIXED_COSTS);

    investmentFundEClass = createEClass(INVESTMENT_FUND);
    createEAttribute(investmentFundEClass, INVESTMENT_FUND__NAME);
    createEAttribute(investmentFundEClass, INVESTMENT_FUND__FUND_INFORMATION);
    createEReference(investmentFundEClass, INVESTMENT_FUND__STOCK_PRICES);
    createEOperation(investmentFundEClass, INVESTMENT_FUND___GET_STOCK_PRICE__LOCALDATE);

    participationEClass = createEClass(PARTICIPATION);
    createEReference(participationEClass, PARTICIPATION__INVESTMENT_FUND);
    createEAttribute(participationEClass, PARTICIPATION__PARTICIPATION_MUTATIONS);
    createEAttribute(participationEClass, PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC);
    createEAttribute(participationEClass, PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS);
    createEAttribute(participationEClass, PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN);
    createEAttribute(participationEClass, PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC);
    createEAttribute(participationEClass, PARTICIPATION__TOTAL_EXPENSE_RATIO);
    createEAttribute(participationEClass, PARTICIPATION__EXAMPLE_CAPITAL_NET_HISTORIC);
    createEAttribute(participationEClass, PARTICIPATION__EXAMPLE_CAPITAL_GROSS);
    createEAttribute(participationEClass, PARTICIPATION__EXAMPLE_CAPITAL_GROSS_COMPANY_OWN);
    createEAttribute(participationEClass, PARTICIPATION__EXAMPLE_CAPITAL_PESSIMISTIC);
    createEAttribute(participationEClass, PARTICIPATION__RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC);
    createEAttribute(participationEClass, PARTICIPATION__EXAMPLE_CAPITAL_AFTER_REDUCTION);
    createEAttribute(participationEClass, PARTICIPATION__DISTRIBUTION_PERCENTAGE);
    createEAttribute(participationEClass, PARTICIPATION__STANDARD_FUND_RETURN_ON_INVESTMENT);
    createEAttribute(participationEClass, PARTICIPATION__EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT);
    createEAttribute(participationEClass, PARTICIPATION__NUMBER_OF_PARTICIPATIONS_END);
    createEAttribute(participationEClass, PARTICIPATION__PARTICIPATION_MUTATIONS_COMPLETE);
    createEReference(participationEClass, PARTICIPATION__ANNUAL_STATEMENT);
    createEOperation(participationEClass, PARTICIPATION___NUMBER_OF_PARTICIPATIONS_START);
    createEOperation(participationEClass, PARTICIPATION___END_VALUE);
    createEOperation(participationEClass, PARTICIPATION___NUMBER_OF_PARTICIPATIONS_BOUGHT);
    createEOperation(participationEClass, PARTICIPATION___NUMBER_OF_PARTICIPATIONS_SOLD);

    investmentPartEClass = createEClass(INVESTMENT_PART);
    createEReference(investmentPartEClass, INVESTMENT_PART__INVESTMENT_FUND);
    createEAttribute(investmentPartEClass, INVESTMENT_PART__PERCENTAGE);

    fundChangeEClass = createEClass(FUND_CHANGE);
    createEReference(fundChangeEClass, FUND_CHANGE__FROM_FUND);
    createEReference(fundChangeEClass, FUND_CHANGE__TO_FUND);
    createEAttribute(fundChangeEClass, FUND_CHANGE__FROM_NUMBER_OF_PARTICIPATIONS);
    createEAttribute(fundChangeEClass, FUND_CHANGE__TO_NUMBER_OF_PARTICIPATIONS);
    createEAttribute(fundChangeEClass, FUND_CHANGE__FROM_STOCK_PRICE);
    createEAttribute(fundChangeEClass, FUND_CHANGE__TO_STOCK_PRICE);
    createEAttribute(fundChangeEClass, FUND_CHANGE__FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT);
    createEAttribute(fundChangeEClass, FUND_CHANGE__FROM_TER);
    createEAttribute(fundChangeEClass, FUND_CHANGE__TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT);
    createEAttribute(fundChangeEClass, FUND_CHANGE__TO_TER);

    extraInvestmentEClass = createEClass(EXTRA_INVESTMENT);
    createEAttribute(extraInvestmentEClass, EXTRA_INVESTMENT__PREMIUM);
    createEAttribute(extraInvestmentEClass, EXTRA_INVESTMENT__DEPOSIT_DATE);
    createEReference(extraInvestmentEClass, EXTRA_INVESTMENT__INVESTMENT_PARTS);
    createEAttribute(extraInvestmentEClass, EXTRA_INVESTMENT__OVERVIEW_DATE);
    createEReference(extraInvestmentEClass, EXTRA_INVESTMENT__PARTICIPATIONS);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private boolean isInitialized = false;

	/**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void initializePackageContents() {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
    RolodexPackage theRolodexPackage = (RolodexPackage)EPackage.Registry.INSTANCE.getEPackage(RolodexPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    insuranceCompanyEClass.getESuperTypes().add(theRolodexPackage.getInstitution());
    annualStatementEClass.getESuperTypes().add(theTypesPackage.getEvent());
    fundChangeEClass.getESuperTypes().add(theTypesPackage.getEvent());
    extraInvestmentEClass.getESuperTypes().add(theTypesPackage.getEvent());

    // Initialize classes, features, and operations; add parameters
    initEClass(investmentInsuranceEClass, InvestmentInsurance.class, "InvestmentInsurance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getInvestmentInsurance_InsuranceCompany(), this.getInsuranceCompany(), null, "insuranceCompany", null, 0, 1, InvestmentInsurance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvestmentInsurance_PolicyNumber(), ecorePackage.getEString(), "policyNumber", null, 1, 1, InvestmentInsurance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvestmentInsurance_StartingDate(), theTypesPackage.getELocalDate(), "startingDate", null, 0, 1, InvestmentInsurance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvestmentInsurance_PolicyHolder(), theRolodexPackage.getPerson(), null, "policyHolder", null, 0, 1, InvestmentInsurance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvestmentInsurance_Premium(), theTypesPackage.getEMoney(), "premium", null, 0, 1, InvestmentInsurance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvestmentInsurance_InsuredBenefitOnDeath(), theTypesPackage.getEMoney(), "insuredBenefitOnDeath", null, 0, 1, InvestmentInsurance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvestmentInsurance_InvestmentParts(), this.getInvestmentPart(), null, "investmentParts", null, 0, -1, InvestmentInsurance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvestmentInsurance_Events(), theTypesPackage.getEvent(), null, "events", null, 0, -1, InvestmentInsurance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getInvestmentInsurance__GetLastKnownValue(), theTypesPackage.getEMoney(), "getLastKnownValue", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getInvestmentInsurance__GetDateForLastKnownValue(), theTypesPackage.getELocalDate(), "getDateForLastKnownValue", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getInvestmentInsurance__GetLastAnnualStatement(), this.getAnnualStatement(), "getLastAnnualStatement", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getInvestmentInsurance__GetAverageReturnOnInvestment(), theTypesPackage.getEFixedPointValue(), "getAverageReturnOnInvestment", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(investmentInsurancesDataEClass, InvestmentInsurancesData.class, "InvestmentInsurancesData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getInvestmentInsurancesData_InsuranceCompanies(), this.getInsuranceCompany(), null, "insuranceCompanies", null, 0, -1, InvestmentInsurancesData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvestmentInsurancesData_InvestmentInsurances(), this.getInvestmentInsurance(), null, "investmentInsurances", null, 0, -1, InvestmentInsurancesData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvestmentInsurancesData_PersonList(), theRolodexPackage.getPersonList(), null, "personList", null, 1, 1, InvestmentInsurancesData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvestmentInsurancesData_AddressList(), theRolodexPackage.getAddressList(), null, "addressList", null, 0, 1, InvestmentInsurancesData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvestmentInsurancesData_CityList(), theRolodexPackage.getCityList(), null, "cityList", null, 0, 1, InvestmentInsurancesData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvestmentInsurancesData_PhoneNumberList(), theRolodexPackage.getPhoneNumberList(), null, "phoneNumberList", null, 0, 1, InvestmentInsurancesData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getInvestmentInsurancesData__GetLastKnownTotalValue(), theTypesPackage.getEMoney(), "getLastKnownTotalValue", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getInvestmentInsurancesData__GetDateForLastKnownTotalValue(), theTypesPackage.getELocalDate(), "getDateForLastKnownTotalValue", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getInvestmentInsurancesData__GetTotalPremium(), theTypesPackage.getEMoney(), "getTotalPremium", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getInvestmentInsurancesData__GetAverageTotalReturnOnInvestment(), theTypesPackage.getEFixedPointValue(), "getAverageTotalReturnOnInvestment", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(insuranceCompanyEClass, InsuranceCompany.class, "InsuranceCompany", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getInsuranceCompany_InvestmentFunds(), this.getInvestmentFund(), null, "investmentFunds", null, 0, -1, InsuranceCompany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInsuranceCompany_Department(), ecorePackage.getEString(), "department", null, 0, 1, InsuranceCompany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInsuranceCompany_Website(), ecorePackage.getEString(), "website", null, 0, 1, InsuranceCompany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(annualStatementEClass, AnnualStatement.class, "AnnualStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAnnualStatement_PeriodFrom(), theTypesPackage.getELocalDate(), "periodFrom", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_PeriodUntil(), theTypesPackage.getELocalDate(), "periodUntil", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_PremiumPaid(), theTypesPackage.getEMoney(), "premiumPaid", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_PremiumDeathRiskCoverage(), theTypesPackage.getEMoney(), "premiumDeathRiskCoverage", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_PremiumOccupationalDisabilityRiskCoverage(), theTypesPackage.getEMoney(), "premiumOccupationalDisabilityRiskCoverage", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_UpkeepPremium(), theTypesPackage.getEMoney(), "upkeepPremium", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_FirstCostsInsuranceCompany(), theTypesPackage.getEMoney(), "firstCostsInsuranceCompany", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_ContinuingCostsInsuranceCompany(), theTypesPackage.getEMoney(), "continuingCostsInsuranceCompany", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_ManagementCosts(), theTypesPackage.getEMoney(), "managementCosts", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_BuyAndSellCosts(), theTypesPackage.getEMoney(), "buyAndSellCosts", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_MutationCosts(), theTypesPackage.getEMoney(), "mutationCosts", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_CostsRestitution(), theTypesPackage.getEMoney(), "costsRestitution", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_Corrections(), theTypesPackage.getEMoney(), "corrections", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_EarnedOnTheParticipations(), theTypesPackage.getEMoney(), "earnedOnTheParticipations", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAnnualStatement_Participations(), this.getParticipation(), null, "participations", null, 0, -1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_ExampleCapitalOnEndDate(), theTypesPackage.getELocalDate(), "exampleCapitalOnEndDate", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAnnualStatement_ExpectedYearlyCostsIncrease(), theTypesPackage.getEFixedPointValue(), "expectedYearlyCostsIncrease", null, 0, 1, AnnualStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getAnnualStatement__TotalCosts(), theTypesPackage.getEMoney(), "totalCosts", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getAnnualStatement__ValueAtYearStart(), theTypesPackage.getEMoney(), "valueAtYearStart", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getAnnualStatement__PercentageCosts(), theTypesPackage.getEMoney(), "percentageCosts", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getAnnualStatement__FixedCosts(), theTypesPackage.getEMoney(), "fixedCosts", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(investmentFundEClass, InvestmentFund.class, "InvestmentFund", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getInvestmentFund_Name(), ecorePackage.getEString(), "name", null, 0, 1, InvestmentFund.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvestmentFund_FundInformation(), ecorePackage.getEString(), "fundInformation", null, 0, 1, InvestmentFund.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvestmentFund_StockPrices(), theTypesPackage.getDateRateTuplet(), null, "stockPrices", null, 0, -1, InvestmentFund.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    EOperation op = initEOperation(getInvestmentFund__GetStockPrice__LocalDate(), theTypesPackage.getEMoney(), "getStockPrice", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, theTypesPackage.getELocalDate(), "date", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(participationEClass, Participation.class, "Participation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getParticipation_InvestmentFund(), this.getInvestmentFund(), null, "investmentFund", null, 1, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ParticipationMutations(), theTypesPackage.getEFixedPointValue(), "participationMutations", null, 0, -1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ExampleReturnOnInvestmentNetHistoric(), theTypesPackage.getEFixedPointValue(), "exampleReturnOnInvestmentNetHistoric", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ExampleReturnOnInvestmentGross(), theTypesPackage.getEFixedPointValue(), "exampleReturnOnInvestmentGross", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ExampleReturnOnInvestmentGrossCompanyOwn(), theTypesPackage.getEFixedPointValue(), "exampleReturnOnInvestmentGrossCompanyOwn", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ExampleReturnOnInvestmentPessimistic(), theTypesPackage.getEFixedPointValue(), "exampleReturnOnInvestmentPessimistic", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_TotalExpenseRatio(), theTypesPackage.getEFixedPointValue(), "totalExpenseRatio", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ExampleCapitalNetHistoric(), theTypesPackage.getEMoney(), "exampleCapitalNetHistoric", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ExampleCapitalGross(), theTypesPackage.getEMoney(), "exampleCapitalGross", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ExampleCapitalGrossCompanyOwn(), theTypesPackage.getEMoney(), "exampleCapitalGrossCompanyOwn", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ExampleCapitalPessimistic(), theTypesPackage.getEMoney(), "exampleCapitalPessimistic", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ReturnOnInvestmentReductionNetHistoric(), theTypesPackage.getEFixedPointValue(), "returnOnInvestmentReductionNetHistoric", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ExampleCapitalAfterReduction(), theTypesPackage.getEMoney(), "exampleCapitalAfterReduction", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_DistributionPercentage(), theTypesPackage.getEFixedPointValue(), "distributionPercentage", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_StandardFundReturnOnInvestment(), theTypesPackage.getEFixedPointValue(), "standardFundReturnOnInvestment", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ExampleCapitalStandardFundReturnOnInvestment(), theTypesPackage.getEMoney(), "exampleCapitalStandardFundReturnOnInvestment", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_NumberOfParticipationsEnd(), theTypesPackage.getEFixedPointValue(), "numberOfParticipationsEnd", null, 0, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParticipation_ParticipationMutationsComplete(), ecorePackage.getEBoolean(), "participationMutationsComplete", null, 1, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getParticipation_AnnualStatement(), this.getAnnualStatement(), null, "annualStatement", null, 1, 1, Participation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getParticipation__NumberOfParticipationsStart(), theTypesPackage.getEFixedPointValue(), "numberOfParticipationsStart", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getParticipation__EndValue(), theTypesPackage.getEMoney(), "endValue", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getParticipation__NumberOfParticipationsBought(), theTypesPackage.getEFixedPointValue(), "numberOfParticipationsBought", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getParticipation__NumberOfParticipationsSold(), theTypesPackage.getEFixedPointValue(), "numberOfParticipationsSold", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(investmentPartEClass, InvestmentPart.class, "InvestmentPart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getInvestmentPart_InvestmentFund(), this.getInvestmentFund(), null, "investmentFund", null, 0, 1, InvestmentPart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvestmentPart_Percentage(), theTypesPackage.getEFixedPointValue(), "percentage", null, 0, 1, InvestmentPart.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fundChangeEClass, FundChange.class, "FundChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getFundChange_FromFund(), this.getInvestmentFund(), null, "fromFund", null, 1, 1, FundChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getFundChange_ToFund(), this.getInvestmentFund(), null, "toFund", null, 1, 1, FundChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFundChange_FromNumberOfParticipations(), theTypesPackage.getEFixedPointValue(), "fromNumberOfParticipations", null, 1, 1, FundChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFundChange_ToNumberOfParticipations(), theTypesPackage.getEFixedPointValue(), "toNumberOfParticipations", null, 1, 1, FundChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFundChange_FromStockPrice(), theTypesPackage.getEMoney(), "fromStockPrice", null, 0, 1, FundChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFundChange_ToStockPrice(), theTypesPackage.getEMoney(), "toStockPrice", null, 0, 1, FundChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFundChange_FromAverageHistoricReturnOnInvestment(), theTypesPackage.getEFixedPointValue(), "fromAverageHistoricReturnOnInvestment", null, 0, 1, FundChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFundChange_FromTER(), theTypesPackage.getEFixedPointValue(), "fromTER", null, 0, 1, FundChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFundChange_ToAverageHistoricReturnOnInvestment(), theTypesPackage.getEFixedPointValue(), "toAverageHistoricReturnOnInvestment", null, 0, 1, FundChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFundChange_ToTER(), theTypesPackage.getEFixedPointValue(), "toTER", null, 0, 1, FundChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(extraInvestmentEClass, ExtraInvestment.class, "ExtraInvestment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getExtraInvestment_Premium(), theTypesPackage.getEMoney(), "premium", null, 0, 1, ExtraInvestment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExtraInvestment_DepositDate(), theTypesPackage.getELocalDate(), "depositDate", null, 0, 1, ExtraInvestment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getExtraInvestment_InvestmentParts(), this.getInvestmentPart(), null, "investmentParts", null, 0, -1, ExtraInvestment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExtraInvestment_OverviewDate(), theTypesPackage.getELocalDate(), "overviewDate", null, 0, 1, ExtraInvestment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getExtraInvestment_Participations(), this.getParticipation(), null, "participations", null, 0, -1, ExtraInvestment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //BeleggingsVerzekeringPackageImpl
