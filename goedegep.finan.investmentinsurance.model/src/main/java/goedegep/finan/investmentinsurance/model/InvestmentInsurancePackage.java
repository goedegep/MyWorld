/**
 */
package goedegep.finan.investmentinsurance.model;

import goedegep.rolodex.model.RolodexPackage;
import goedegep.types.model.TypesPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see goedegep.finan.investmentinsurance.model.InvestmentInsuranceFactory
 * @model kind="package"
 * @generated
 */
public interface InvestmentInsurancePackage extends EPackage {
	/**
   * The package name.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	String eNAME = "model";

	/**
   * The package namespace URI.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	String eNS_URI = "http://www.goedegep.org/finan";

	/**
   * The package namespace name.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	String eNS_PREFIX = "finan";

	/**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	InvestmentInsurancePackage eINSTANCE = goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl.init();

	/**
   * The meta object id for the '{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl <em>Investment Insurance</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getInvestmentInsurance()
   * @generated
   */
  int INVESTMENT_INSURANCE = 0;

  /**
   * The feature id for the '<em><b>Insurance Company</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE__INSURANCE_COMPANY = 0;

  /**
   * The feature id for the '<em><b>Policy Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE__POLICY_NUMBER = 1;

  /**
   * The feature id for the '<em><b>Starting Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE__STARTING_DATE = 2;

  /**
   * The feature id for the '<em><b>Policy Holder</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE__POLICY_HOLDER = 3;

  /**
   * The feature id for the '<em><b>Premium</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE__PREMIUM = 4;

  /**
   * The feature id for the '<em><b>Insured Benefit On Death</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE__INSURED_BENEFIT_ON_DEATH = 5;

  /**
   * The feature id for the '<em><b>Investment Parts</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE__INVESTMENT_PARTS = 6;

  /**
   * The feature id for the '<em><b>Events</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE__EVENTS = 7;

  /**
   * The number of structural features of the '<em>Investment Insurance</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE_FEATURE_COUNT = 8;

  /**
   * The operation id for the '<em>Get Last Known Value</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE___GET_LAST_KNOWN_VALUE = 0;

  /**
   * The operation id for the '<em>Get Date For Last Known Value</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE___GET_DATE_FOR_LAST_KNOWN_VALUE = 1;

  /**
   * The operation id for the '<em>Get Last Annual Statement</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE___GET_LAST_ANNUAL_STATEMENT = 2;

  /**
   * The operation id for the '<em>Get Average Return On Investment</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE___GET_AVERAGE_RETURN_ON_INVESTMENT = 3;

  /**
   * The number of operations of the '<em>Investment Insurance</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCE_OPERATION_COUNT = 4;

  /**
   * The meta object id for the '{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancesDataImpl <em>Investment Insurances Data</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancesDataImpl
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getInvestmentInsurancesData()
   * @generated
   */
  int INVESTMENT_INSURANCES_DATA = 1;

  /**
   * The feature id for the '<em><b>Insurance Companies</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCES_DATA__INSURANCE_COMPANIES = 0;

  /**
   * The feature id for the '<em><b>Investment Insurances</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCES_DATA__INVESTMENT_INSURANCES = 1;

  /**
   * The feature id for the '<em><b>Person List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCES_DATA__PERSON_LIST = 2;

  /**
   * The feature id for the '<em><b>Address List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCES_DATA__ADDRESS_LIST = 3;

  /**
   * The feature id for the '<em><b>City List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCES_DATA__CITY_LIST = 4;

  /**
   * The feature id for the '<em><b>Phone Number List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCES_DATA__PHONE_NUMBER_LIST = 5;

  /**
   * The number of structural features of the '<em>Investment Insurances Data</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCES_DATA_FEATURE_COUNT = 6;

  /**
   * The operation id for the '<em>Get Last Known Total Value</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCES_DATA___GET_LAST_KNOWN_TOTAL_VALUE = 0;

  /**
   * The operation id for the '<em>Get Date For Last Known Total Value</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCES_DATA___GET_DATE_FOR_LAST_KNOWN_TOTAL_VALUE = 1;

  /**
   * The operation id for the '<em>Get Total Premium</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCES_DATA___GET_TOTAL_PREMIUM = 2;

  /**
   * The operation id for the '<em>Get Average Total Return On Investment</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCES_DATA___GET_AVERAGE_TOTAL_RETURN_ON_INVESTMENT = 3;

  /**
   * The number of operations of the '<em>Investment Insurances Data</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_INSURANCES_DATA_OPERATION_COUNT = 4;

  /**
   * The meta object id for the '{@link goedegep.finan.investmentinsurance.model.impl.InsuranceCompanyImpl <em>Insurance Company</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.investmentinsurance.model.impl.InsuranceCompanyImpl
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getInsuranceCompany()
   * @generated
   */
  int INSURANCE_COMPANY = 2;

  /**
   * The feature id for the '<em><b>Phone Numbers</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSURANCE_COMPANY__PHONE_NUMBERS = RolodexPackage.INSTITUTION__PHONE_NUMBERS;

  /**
   * The feature id for the '<em><b>Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSURANCE_COMPANY__ADDRESS = RolodexPackage.INSTITUTION__ADDRESS;

  /**
   * The feature id for the '<em><b>Previous Addresses</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSURANCE_COMPANY__PREVIOUS_ADDRESSES = RolodexPackage.INSTITUTION__PREVIOUS_ADDRESSES;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSURANCE_COMPANY__NAME = RolodexPackage.INSTITUTION__NAME;

  /**
   * The feature id for the '<em><b>Mailing Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSURANCE_COMPANY__MAILING_ADDRESS = RolodexPackage.INSTITUTION__MAILING_ADDRESS;

  /**
   * The feature id for the '<em><b>Investment Funds</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSURANCE_COMPANY__INVESTMENT_FUNDS = RolodexPackage.INSTITUTION_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Department</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSURANCE_COMPANY__DEPARTMENT = RolodexPackage.INSTITUTION_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Website</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSURANCE_COMPANY__WEBSITE = RolodexPackage.INSTITUTION_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Insurance Company</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSURANCE_COMPANY_FEATURE_COUNT = RolodexPackage.INSTITUTION_FEATURE_COUNT + 3;

  /**
   * The number of operations of the '<em>Insurance Company</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSURANCE_COMPANY_OPERATION_COUNT = RolodexPackage.INSTITUTION_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl <em>Annual Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getAnnualStatement()
   * @generated
   */
  int ANNUAL_STATEMENT = 3;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__DATE = TypesPackage.EVENT__DATE;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__NOTES = TypesPackage.EVENT__NOTES;

  /**
   * The feature id for the '<em><b>Period From</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__PERIOD_FROM = TypesPackage.EVENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Period Until</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__PERIOD_UNTIL = TypesPackage.EVENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Premium Paid</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__PREMIUM_PAID = TypesPackage.EVENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Premium Death Risk Coverage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__PREMIUM_DEATH_RISK_COVERAGE = TypesPackage.EVENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Premium Occupational Disability Risk Coverage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE = TypesPackage.EVENT_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Upkeep Premium</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__UPKEEP_PREMIUM = TypesPackage.EVENT_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>First Costs Insurance Company</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__FIRST_COSTS_INSURANCE_COMPANY = TypesPackage.EVENT_FEATURE_COUNT + 6;

  /**
   * The feature id for the '<em><b>Continuing Costs Insurance Company</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__CONTINUING_COSTS_INSURANCE_COMPANY = TypesPackage.EVENT_FEATURE_COUNT + 7;

  /**
   * The feature id for the '<em><b>Management Costs</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__MANAGEMENT_COSTS = TypesPackage.EVENT_FEATURE_COUNT + 8;

  /**
   * The feature id for the '<em><b>Buy And Sell Costs</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__BUY_AND_SELL_COSTS = TypesPackage.EVENT_FEATURE_COUNT + 9;

  /**
   * The feature id for the '<em><b>Mutation Costs</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__MUTATION_COSTS = TypesPackage.EVENT_FEATURE_COUNT + 10;

  /**
   * The feature id for the '<em><b>Costs Restitution</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__COSTS_RESTITUTION = TypesPackage.EVENT_FEATURE_COUNT + 11;

  /**
   * The feature id for the '<em><b>Corrections</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__CORRECTIONS = TypesPackage.EVENT_FEATURE_COUNT + 12;

  /**
   * The feature id for the '<em><b>Earned On The Participations</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__EARNED_ON_THE_PARTICIPATIONS = TypesPackage.EVENT_FEATURE_COUNT + 13;

  /**
   * The feature id for the '<em><b>Participations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__PARTICIPATIONS = TypesPackage.EVENT_FEATURE_COUNT + 14;

  /**
   * The feature id for the '<em><b>Example Capital On End Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__EXAMPLE_CAPITAL_ON_END_DATE = TypesPackage.EVENT_FEATURE_COUNT + 15;

  /**
   * The feature id for the '<em><b>Expected Yearly Costs Increase</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT__EXPECTED_YEARLY_COSTS_INCREASE = TypesPackage.EVENT_FEATURE_COUNT + 16;

  /**
   * The number of structural features of the '<em>Annual Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT_FEATURE_COUNT = TypesPackage.EVENT_FEATURE_COUNT + 17;

  /**
   * The operation id for the '<em>Total Costs</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT___TOTAL_COSTS = TypesPackage.EVENT_OPERATION_COUNT + 0;

  /**
   * The operation id for the '<em>Value At Year Start</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT___VALUE_AT_YEAR_START = TypesPackage.EVENT_OPERATION_COUNT + 1;

  /**
   * The operation id for the '<em>Percentage Costs</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT___PERCENTAGE_COSTS = TypesPackage.EVENT_OPERATION_COUNT + 2;

  /**
   * The operation id for the '<em>Fixed Costs</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT___FIXED_COSTS = TypesPackage.EVENT_OPERATION_COUNT + 3;

  /**
   * The number of operations of the '<em>Annual Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ANNUAL_STATEMENT_OPERATION_COUNT = TypesPackage.EVENT_OPERATION_COUNT + 4;

  /**
   * The meta object id for the '{@link goedegep.finan.investmentinsurance.model.impl.InvestmentFundImpl <em>Investment Fund</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentFundImpl
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getInvestmentFund()
   * @generated
   */
  int INVESTMENT_FUND = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_FUND__NAME = 0;

  /**
   * The feature id for the '<em><b>Fund Information</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_FUND__FUND_INFORMATION = 1;

  /**
   * The feature id for the '<em><b>Stock Prices</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_FUND__STOCK_PRICES = 2;

  /**
   * The number of structural features of the '<em>Investment Fund</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_FUND_FEATURE_COUNT = 3;

  /**
   * The operation id for the '<em>Get Stock Price</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_FUND___GET_STOCK_PRICE__LOCALDATE = 0;

  /**
   * The number of operations of the '<em>Investment Fund</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_FUND_OPERATION_COUNT = 1;

  /**
   * The meta object id for the '{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl <em>Participation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.investmentinsurance.model.impl.ParticipationImpl
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getParticipation()
   * @generated
   */
  int PARTICIPATION = 5;

  /**
   * The feature id for the '<em><b>Investment Fund</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__INVESTMENT_FUND = 0;

  /**
   * The feature id for the '<em><b>Participation Mutations</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__PARTICIPATION_MUTATIONS = 1;

  /**
   * The feature id for the '<em><b>Example Return On Investment Net Historic</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC = 2;

  /**
   * The feature id for the '<em><b>Example Return On Investment Gross</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS = 3;

  /**
   * The feature id for the '<em><b>Example Return On Investment Gross Company Own</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN = 4;

  /**
   * The feature id for the '<em><b>Example Return On Investment Pessimistic</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC = 5;

  /**
   * The feature id for the '<em><b>Total Expense Ratio</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__TOTAL_EXPENSE_RATIO = 6;

  /**
   * The feature id for the '<em><b>Example Capital Net Historic</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__EXAMPLE_CAPITAL_NET_HISTORIC = 7;

  /**
   * The feature id for the '<em><b>Example Capital Gross</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__EXAMPLE_CAPITAL_GROSS = 8;

  /**
   * The feature id for the '<em><b>Example Capital Gross Company Own</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__EXAMPLE_CAPITAL_GROSS_COMPANY_OWN = 9;

  /**
   * The feature id for the '<em><b>Example Capital Pessimistic</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__EXAMPLE_CAPITAL_PESSIMISTIC = 10;

  /**
   * The feature id for the '<em><b>Return On Investment Reduction Net Historic</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC = 11;

  /**
   * The feature id for the '<em><b>Example Capital After Reduction</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__EXAMPLE_CAPITAL_AFTER_REDUCTION = 12;

  /**
   * The feature id for the '<em><b>Distribution Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__DISTRIBUTION_PERCENTAGE = 13;

  /**
   * The feature id for the '<em><b>Standard Fund Return On Investment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__STANDARD_FUND_RETURN_ON_INVESTMENT = 14;

  /**
   * The feature id for the '<em><b>Example Capital Standard Fund Return On Investment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT = 15;

  /**
   * The feature id for the '<em><b>Number Of Participations End</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__NUMBER_OF_PARTICIPATIONS_END = 16;

  /**
   * The feature id for the '<em><b>Participation Mutations Complete</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__PARTICIPATION_MUTATIONS_COMPLETE = 17;

  /**
   * The feature id for the '<em><b>Annual Statement</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION__ANNUAL_STATEMENT = 18;

  /**
   * The number of structural features of the '<em>Participation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION_FEATURE_COUNT = 19;

  /**
   * The operation id for the '<em>Number Of Participations Start</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION___NUMBER_OF_PARTICIPATIONS_START = 0;

  /**
   * The operation id for the '<em>End Value</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION___END_VALUE = 1;

  /**
   * The operation id for the '<em>Number Of Participations Bought</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION___NUMBER_OF_PARTICIPATIONS_BOUGHT = 2;

  /**
   * The operation id for the '<em>Number Of Participations Sold</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION___NUMBER_OF_PARTICIPATIONS_SOLD = 3;

  /**
   * The number of operations of the '<em>Participation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARTICIPATION_OPERATION_COUNT = 4;

  /**
   * The meta object id for the '{@link goedegep.finan.investmentinsurance.model.impl.InvestmentPartImpl <em>Investment Part</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentPartImpl
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getInvestmentPart()
   * @generated
   */
  int INVESTMENT_PART = 6;

  /**
   * The feature id for the '<em><b>Investment Fund</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_PART__INVESTMENT_FUND = 0;

  /**
   * The feature id for the '<em><b>Percentage</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_PART__PERCENTAGE = 1;

  /**
   * The number of structural features of the '<em>Investment Part</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_PART_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Investment Part</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVESTMENT_PART_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.finan.investmentinsurance.model.impl.FundChangeImpl <em>Fund Change</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.investmentinsurance.model.impl.FundChangeImpl
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getFundChange()
   * @generated
   */
  int FUND_CHANGE = 7;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE__DATE = TypesPackage.EVENT__DATE;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE__NOTES = TypesPackage.EVENT__NOTES;

  /**
   * The feature id for the '<em><b>From Fund</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE__FROM_FUND = TypesPackage.EVENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>To Fund</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE__TO_FUND = TypesPackage.EVENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>From Number Of Participations</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE__FROM_NUMBER_OF_PARTICIPATIONS = TypesPackage.EVENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>To Number Of Participations</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE__TO_NUMBER_OF_PARTICIPATIONS = TypesPackage.EVENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>From Stock Price</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE__FROM_STOCK_PRICE = TypesPackage.EVENT_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>To Stock Price</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE__TO_STOCK_PRICE = TypesPackage.EVENT_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>From Average Historic Return On Investment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE__FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT = TypesPackage.EVENT_FEATURE_COUNT + 6;

  /**
   * The feature id for the '<em><b>From TER</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE__FROM_TER = TypesPackage.EVENT_FEATURE_COUNT + 7;

  /**
   * The feature id for the '<em><b>To Average Historic Return On Investment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE__TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT = TypesPackage.EVENT_FEATURE_COUNT + 8;

  /**
   * The feature id for the '<em><b>To TER</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE__TO_TER = TypesPackage.EVENT_FEATURE_COUNT + 9;

  /**
   * The number of structural features of the '<em>Fund Change</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE_FEATURE_COUNT = TypesPackage.EVENT_FEATURE_COUNT + 10;

  /**
   * The number of operations of the '<em>Fund Change</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FUND_CHANGE_OPERATION_COUNT = TypesPackage.EVENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.finan.investmentinsurance.model.impl.ExtraInvestmentImpl <em>Extra Investment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.investmentinsurance.model.impl.ExtraInvestmentImpl
   * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getExtraInvestment()
   * @generated
   */
  int EXTRA_INVESTMENT = 8;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRA_INVESTMENT__DATE = TypesPackage.EVENT__DATE;

  /**
   * The feature id for the '<em><b>Notes</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRA_INVESTMENT__NOTES = TypesPackage.EVENT__NOTES;

  /**
   * The feature id for the '<em><b>Premium</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRA_INVESTMENT__PREMIUM = TypesPackage.EVENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Deposit Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRA_INVESTMENT__DEPOSIT_DATE = TypesPackage.EVENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Investment Parts</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRA_INVESTMENT__INVESTMENT_PARTS = TypesPackage.EVENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Overview Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRA_INVESTMENT__OVERVIEW_DATE = TypesPackage.EVENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Participations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRA_INVESTMENT__PARTICIPATIONS = TypesPackage.EVENT_FEATURE_COUNT + 4;

  /**
   * The number of structural features of the '<em>Extra Investment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRA_INVESTMENT_FEATURE_COUNT = TypesPackage.EVENT_FEATURE_COUNT + 5;

  /**
   * The number of operations of the '<em>Extra Investment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXTRA_INVESTMENT_OPERATION_COUNT = TypesPackage.EVENT_OPERATION_COUNT + 0;

  /**
   * Returns the meta object for class '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance <em>Investment Insurance</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Investment Insurance</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance
   * @generated
   */
  EClass getInvestmentInsurance();

  /**
   * Returns the meta object for the reference '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInsuranceCompany <em>Insurance Company</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Insurance Company</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInsuranceCompany()
   * @see #getInvestmentInsurance()
   * @generated
   */
  EReference getInvestmentInsurance_InsuranceCompany();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPolicyNumber <em>Policy Number</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Policy Number</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPolicyNumber()
   * @see #getInvestmentInsurance()
   * @generated
   */
  EAttribute getInvestmentInsurance_PolicyNumber();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getStartingDate <em>Starting Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Starting Date</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance#getStartingDate()
   * @see #getInvestmentInsurance()
   * @generated
   */
  EAttribute getInvestmentInsurance_StartingDate();

  /**
   * Returns the meta object for the reference '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPolicyHolder <em>Policy Holder</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Policy Holder</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPolicyHolder()
   * @see #getInvestmentInsurance()
   * @generated
   */
  EReference getInvestmentInsurance_PolicyHolder();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPremium <em>Premium</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Premium</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance#getPremium()
   * @see #getInvestmentInsurance()
   * @generated
   */
  EAttribute getInvestmentInsurance_Premium();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInsuredBenefitOnDeath <em>Insured Benefit On Death</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Insured Benefit On Death</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInsuredBenefitOnDeath()
   * @see #getInvestmentInsurance()
   * @generated
   */
  EAttribute getInvestmentInsurance_InsuredBenefitOnDeath();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInvestmentParts <em>Investment Parts</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Investment Parts</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance#getInvestmentParts()
   * @see #getInvestmentInsurance()
   * @generated
   */
  EReference getInvestmentInsurance_InvestmentParts();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getEvents <em>Events</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Events</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance#getEvents()
   * @see #getInvestmentInsurance()
   * @generated
   */
  EReference getInvestmentInsurance_Events();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getLastKnownValue() <em>Get Last Known Value</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Last Known Value</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance#getLastKnownValue()
   * @generated
   */
  EOperation getInvestmentInsurance__GetLastKnownValue();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getDateForLastKnownValue() <em>Get Date For Last Known Value</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Date For Last Known Value</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance#getDateForLastKnownValue()
   * @generated
   */
  EOperation getInvestmentInsurance__GetDateForLastKnownValue();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getLastAnnualStatement() <em>Get Last Annual Statement</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Last Annual Statement</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance#getLastAnnualStatement()
   * @generated
   */
  EOperation getInvestmentInsurance__GetLastAnnualStatement();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurance#getAverageReturnOnInvestment() <em>Get Average Return On Investment</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Average Return On Investment</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurance#getAverageReturnOnInvestment()
   * @generated
   */
  EOperation getInvestmentInsurance__GetAverageReturnOnInvestment();

  /**
   * Returns the meta object for class '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData <em>Investment Insurances Data</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Investment Insurances Data</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancesData
   * @generated
   */
  EClass getInvestmentInsurancesData();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getInsuranceCompanies <em>Insurance Companies</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Insurance Companies</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getInsuranceCompanies()
   * @see #getInvestmentInsurancesData()
   * @generated
   */
  EReference getInvestmentInsurancesData_InsuranceCompanies();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getInvestmentInsurances <em>Investment Insurances</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Investment Insurances</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getInvestmentInsurances()
   * @see #getInvestmentInsurancesData()
   * @generated
   */
  EReference getInvestmentInsurancesData_InvestmentInsurances();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getPersonList <em>Person List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Person List</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getPersonList()
   * @see #getInvestmentInsurancesData()
   * @generated
   */
  EReference getInvestmentInsurancesData_PersonList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getAddressList <em>Address List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Address List</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getAddressList()
   * @see #getInvestmentInsurancesData()
   * @generated
   */
  EReference getInvestmentInsurancesData_AddressList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getCityList <em>City List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>City List</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getCityList()
   * @see #getInvestmentInsurancesData()
   * @generated
   */
  EReference getInvestmentInsurancesData_CityList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getPhoneNumberList <em>Phone Number List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Phone Number List</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getPhoneNumberList()
   * @see #getInvestmentInsurancesData()
   * @generated
   */
  EReference getInvestmentInsurancesData_PhoneNumberList();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getLastKnownTotalValue() <em>Get Last Known Total Value</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Last Known Total Value</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getLastKnownTotalValue()
   * @generated
   */
  EOperation getInvestmentInsurancesData__GetLastKnownTotalValue();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getDateForLastKnownTotalValue() <em>Get Date For Last Known Total Value</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Date For Last Known Total Value</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getDateForLastKnownTotalValue()
   * @generated
   */
  EOperation getInvestmentInsurancesData__GetDateForLastKnownTotalValue();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getTotalPremium() <em>Get Total Premium</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Total Premium</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getTotalPremium()
   * @generated
   */
  EOperation getInvestmentInsurancesData__GetTotalPremium();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getAverageTotalReturnOnInvestment() <em>Get Average Total Return On Investment</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Average Total Return On Investment</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.InvestmentInsurancesData#getAverageTotalReturnOnInvestment()
   * @generated
   */
  EOperation getInvestmentInsurancesData__GetAverageTotalReturnOnInvestment();

  /**
   * Returns the meta object for class '{@link goedegep.finan.investmentinsurance.model.InsuranceCompany <em>Insurance Company</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Insurance Company</em>'.
   * @see goedegep.finan.investmentinsurance.model.InsuranceCompany
   * @generated
   */
  EClass getInsuranceCompany();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.investmentinsurance.model.InsuranceCompany#getInvestmentFunds <em>Investment Funds</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Investment Funds</em>'.
   * @see goedegep.finan.investmentinsurance.model.InsuranceCompany#getInvestmentFunds()
   * @see #getInsuranceCompany()
   * @generated
   */
  EReference getInsuranceCompany_InvestmentFunds();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.InsuranceCompany#getDepartment <em>Department</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Department</em>'.
   * @see goedegep.finan.investmentinsurance.model.InsuranceCompany#getDepartment()
   * @see #getInsuranceCompany()
   * @generated
   */
  EAttribute getInsuranceCompany_Department();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.InsuranceCompany#getWebsite <em>Website</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Website</em>'.
   * @see goedegep.finan.investmentinsurance.model.InsuranceCompany#getWebsite()
   * @see #getInsuranceCompany()
   * @generated
   */
  EAttribute getInsuranceCompany_Website();

  /**
   * Returns the meta object for class '{@link goedegep.finan.investmentinsurance.model.AnnualStatement <em>Annual Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Annual Statement</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement
   * @generated
   */
  EClass getAnnualStatement();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPeriodFrom <em>Period From</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Period From</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getPeriodFrom()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_PeriodFrom();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPeriodUntil <em>Period Until</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Period Until</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getPeriodUntil()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_PeriodUntil();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumPaid <em>Premium Paid</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Premium Paid</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumPaid()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_PremiumPaid();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumDeathRiskCoverage <em>Premium Death Risk Coverage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Premium Death Risk Coverage</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumDeathRiskCoverage()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_PremiumDeathRiskCoverage();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumOccupationalDisabilityRiskCoverage <em>Premium Occupational Disability Risk Coverage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Premium Occupational Disability Risk Coverage</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getPremiumOccupationalDisabilityRiskCoverage()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_PremiumOccupationalDisabilityRiskCoverage();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getUpkeepPremium <em>Upkeep Premium</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Upkeep Premium</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getUpkeepPremium()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_UpkeepPremium();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getFirstCostsInsuranceCompany <em>First Costs Insurance Company</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>First Costs Insurance Company</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getFirstCostsInsuranceCompany()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_FirstCostsInsuranceCompany();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getContinuingCostsInsuranceCompany <em>Continuing Costs Insurance Company</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Continuing Costs Insurance Company</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getContinuingCostsInsuranceCompany()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_ContinuingCostsInsuranceCompany();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getManagementCosts <em>Management Costs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Management Costs</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getManagementCosts()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_ManagementCosts();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getBuyAndSellCosts <em>Buy And Sell Costs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Buy And Sell Costs</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getBuyAndSellCosts()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_BuyAndSellCosts();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getMutationCosts <em>Mutation Costs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Mutation Costs</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getMutationCosts()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_MutationCosts();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getCostsRestitution <em>Costs Restitution</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Costs Restitution</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getCostsRestitution()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_CostsRestitution();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getCorrections <em>Corrections</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Corrections</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getCorrections()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_Corrections();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getEarnedOnTheParticipations <em>Earned On The Participations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Earned On The Participations</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getEarnedOnTheParticipations()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_EarnedOnTheParticipations();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getParticipations <em>Participations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Participations</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getParticipations()
   * @see #getAnnualStatement()
   * @generated
   */
  EReference getAnnualStatement_Participations();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getExampleCapitalOnEndDate <em>Example Capital On End Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Example Capital On End Date</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getExampleCapitalOnEndDate()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_ExampleCapitalOnEndDate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#getExpectedYearlyCostsIncrease <em>Expected Yearly Costs Increase</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Expected Yearly Costs Increase</em>'.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#getExpectedYearlyCostsIncrease()
   * @see #getAnnualStatement()
   * @generated
   */
  EAttribute getAnnualStatement_ExpectedYearlyCostsIncrease();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#totalCosts() <em>Total Costs</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Total Costs</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#totalCosts()
   * @generated
   */
  EOperation getAnnualStatement__TotalCosts();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#valueAtYearStart() <em>Value At Year Start</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Value At Year Start</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#valueAtYearStart()
   * @generated
   */
  EOperation getAnnualStatement__ValueAtYearStart();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#percentageCosts() <em>Percentage Costs</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Percentage Costs</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#percentageCosts()
   * @generated
   */
  EOperation getAnnualStatement__PercentageCosts();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.AnnualStatement#fixedCosts() <em>Fixed Costs</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Fixed Costs</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.AnnualStatement#fixedCosts()
   * @generated
   */
  EOperation getAnnualStatement__FixedCosts();

  /**
   * Returns the meta object for class '{@link goedegep.finan.investmentinsurance.model.InvestmentFund <em>Investment Fund</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Investment Fund</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentFund
   * @generated
   */
  EClass getInvestmentFund();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentFund#getName()
   * @see #getInvestmentFund()
   * @generated
   */
  EAttribute getInvestmentFund_Name();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getFundInformation <em>Fund Information</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Fund Information</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentFund#getFundInformation()
   * @see #getInvestmentFund()
   * @generated
   */
  EAttribute getInvestmentFund_FundInformation();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getStockPrices <em>Stock Prices</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Stock Prices</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentFund#getStockPrices()
   * @see #getInvestmentFund()
   * @generated
   */
  EReference getInvestmentFund_StockPrices();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.InvestmentFund#getStockPrice(java.time.LocalDate) <em>Get Stock Price</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Stock Price</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.InvestmentFund#getStockPrice(java.time.LocalDate)
   * @generated
   */
  EOperation getInvestmentFund__GetStockPrice__LocalDate();

  /**
   * Returns the meta object for class '{@link goedegep.finan.investmentinsurance.model.Participation <em>Participation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Participation</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation
   * @generated
   */
  EClass getParticipation();

  /**
   * Returns the meta object for the reference '{@link goedegep.finan.investmentinsurance.model.Participation#getInvestmentFund <em>Investment Fund</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Investment Fund</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getInvestmentFund()
   * @see #getParticipation()
   * @generated
   */
  EReference getParticipation_InvestmentFund();

  /**
   * Returns the meta object for the attribute list '{@link goedegep.finan.investmentinsurance.model.Participation#getParticipationMutations <em>Participation Mutations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Participation Mutations</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getParticipationMutations()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ParticipationMutations();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentNetHistoric <em>Example Return On Investment Net Historic</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Example Return On Investment Net Historic</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentNetHistoric()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ExampleReturnOnInvestmentNetHistoric();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentGross <em>Example Return On Investment Gross</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Example Return On Investment Gross</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentGross()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ExampleReturnOnInvestmentGross();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentGrossCompanyOwn <em>Example Return On Investment Gross Company Own</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Example Return On Investment Gross Company Own</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentGrossCompanyOwn()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ExampleReturnOnInvestmentGrossCompanyOwn();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentPessimistic <em>Example Return On Investment Pessimistic</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Example Return On Investment Pessimistic</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getExampleReturnOnInvestmentPessimistic()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ExampleReturnOnInvestmentPessimistic();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getTotalExpenseRatio <em>Total Expense Ratio</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Total Expense Ratio</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getTotalExpenseRatio()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_TotalExpenseRatio();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalNetHistoric <em>Example Capital Net Historic</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Example Capital Net Historic</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalNetHistoric()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ExampleCapitalNetHistoric();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalGross <em>Example Capital Gross</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Example Capital Gross</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalGross()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ExampleCapitalGross();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalGrossCompanyOwn <em>Example Capital Gross Company Own</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Example Capital Gross Company Own</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalGrossCompanyOwn()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ExampleCapitalGrossCompanyOwn();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalPessimistic <em>Example Capital Pessimistic</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Example Capital Pessimistic</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalPessimistic()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ExampleCapitalPessimistic();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getReturnOnInvestmentReductionNetHistoric <em>Return On Investment Reduction Net Historic</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Return On Investment Reduction Net Historic</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getReturnOnInvestmentReductionNetHistoric()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ReturnOnInvestmentReductionNetHistoric();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalAfterReduction <em>Example Capital After Reduction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Example Capital After Reduction</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalAfterReduction()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ExampleCapitalAfterReduction();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getDistributionPercentage <em>Distribution Percentage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Distribution Percentage</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getDistributionPercentage()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_DistributionPercentage();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getStandardFundReturnOnInvestment <em>Standard Fund Return On Investment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Standard Fund Return On Investment</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getStandardFundReturnOnInvestment()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_StandardFundReturnOnInvestment();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalStandardFundReturnOnInvestment <em>Example Capital Standard Fund Return On Investment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Example Capital Standard Fund Return On Investment</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getExampleCapitalStandardFundReturnOnInvestment()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ExampleCapitalStandardFundReturnOnInvestment();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#getNumberOfParticipationsEnd <em>Number Of Participations End</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Number Of Participations End</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getNumberOfParticipationsEnd()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_NumberOfParticipationsEnd();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.Participation#isParticipationMutationsComplete <em>Participation Mutations Complete</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Participation Mutations Complete</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#isParticipationMutationsComplete()
   * @see #getParticipation()
   * @generated
   */
  EAttribute getParticipation_ParticipationMutationsComplete();

  /**
   * Returns the meta object for the reference '{@link goedegep.finan.investmentinsurance.model.Participation#getAnnualStatement <em>Annual Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Annual Statement</em>'.
   * @see goedegep.finan.investmentinsurance.model.Participation#getAnnualStatement()
   * @see #getParticipation()
   * @generated
   */
  EReference getParticipation_AnnualStatement();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.Participation#numberOfParticipationsStart() <em>Number Of Participations Start</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Number Of Participations Start</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.Participation#numberOfParticipationsStart()
   * @generated
   */
  EOperation getParticipation__NumberOfParticipationsStart();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.Participation#endValue() <em>End Value</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>End Value</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.Participation#endValue()
   * @generated
   */
  EOperation getParticipation__EndValue();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.Participation#numberOfParticipationsBought() <em>Number Of Participations Bought</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Number Of Participations Bought</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.Participation#numberOfParticipationsBought()
   * @generated
   */
  EOperation getParticipation__NumberOfParticipationsBought();

  /**
   * Returns the meta object for the '{@link goedegep.finan.investmentinsurance.model.Participation#numberOfParticipationsSold() <em>Number Of Participations Sold</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Number Of Participations Sold</em>' operation.
   * @see goedegep.finan.investmentinsurance.model.Participation#numberOfParticipationsSold()
   * @generated
   */
  EOperation getParticipation__NumberOfParticipationsSold();

  /**
   * Returns the meta object for class '{@link goedegep.finan.investmentinsurance.model.InvestmentPart <em>Investment Part</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Investment Part</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentPart
   * @generated
   */
  EClass getInvestmentPart();

  /**
   * Returns the meta object for the reference '{@link goedegep.finan.investmentinsurance.model.InvestmentPart#getInvestmentFund <em>Investment Fund</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Investment Fund</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentPart#getInvestmentFund()
   * @see #getInvestmentPart()
   * @generated
   */
  EReference getInvestmentPart_InvestmentFund();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.InvestmentPart#getPercentage <em>Percentage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Percentage</em>'.
   * @see goedegep.finan.investmentinsurance.model.InvestmentPart#getPercentage()
   * @see #getInvestmentPart()
   * @generated
   */
  EAttribute getInvestmentPart_Percentage();

  /**
   * Returns the meta object for class '{@link goedegep.finan.investmentinsurance.model.FundChange <em>Fund Change</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Fund Change</em>'.
   * @see goedegep.finan.investmentinsurance.model.FundChange
   * @generated
   */
  EClass getFundChange();

  /**
   * Returns the meta object for the reference '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromFund <em>From Fund</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>From Fund</em>'.
   * @see goedegep.finan.investmentinsurance.model.FundChange#getFromFund()
   * @see #getFundChange()
   * @generated
   */
  EReference getFundChange_FromFund();

  /**
   * Returns the meta object for the reference '{@link goedegep.finan.investmentinsurance.model.FundChange#getToFund <em>To Fund</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>To Fund</em>'.
   * @see goedegep.finan.investmentinsurance.model.FundChange#getToFund()
   * @see #getFundChange()
   * @generated
   */
  EReference getFundChange_ToFund();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromNumberOfParticipations <em>From Number Of Participations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>From Number Of Participations</em>'.
   * @see goedegep.finan.investmentinsurance.model.FundChange#getFromNumberOfParticipations()
   * @see #getFundChange()
   * @generated
   */
  EAttribute getFundChange_FromNumberOfParticipations();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.FundChange#getToNumberOfParticipations <em>To Number Of Participations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>To Number Of Participations</em>'.
   * @see goedegep.finan.investmentinsurance.model.FundChange#getToNumberOfParticipations()
   * @see #getFundChange()
   * @generated
   */
  EAttribute getFundChange_ToNumberOfParticipations();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromStockPrice <em>From Stock Price</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>From Stock Price</em>'.
   * @see goedegep.finan.investmentinsurance.model.FundChange#getFromStockPrice()
   * @see #getFundChange()
   * @generated
   */
  EAttribute getFundChange_FromStockPrice();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.FundChange#getToStockPrice <em>To Stock Price</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>To Stock Price</em>'.
   * @see goedegep.finan.investmentinsurance.model.FundChange#getToStockPrice()
   * @see #getFundChange()
   * @generated
   */
  EAttribute getFundChange_ToStockPrice();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromAverageHistoricReturnOnInvestment <em>From Average Historic Return On Investment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>From Average Historic Return On Investment</em>'.
   * @see goedegep.finan.investmentinsurance.model.FundChange#getFromAverageHistoricReturnOnInvestment()
   * @see #getFundChange()
   * @generated
   */
  EAttribute getFundChange_FromAverageHistoricReturnOnInvestment();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.FundChange#getFromTER <em>From TER</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>From TER</em>'.
   * @see goedegep.finan.investmentinsurance.model.FundChange#getFromTER()
   * @see #getFundChange()
   * @generated
   */
  EAttribute getFundChange_FromTER();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.FundChange#getToAverageHistoricReturnOnInvestment <em>To Average Historic Return On Investment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>To Average Historic Return On Investment</em>'.
   * @see goedegep.finan.investmentinsurance.model.FundChange#getToAverageHistoricReturnOnInvestment()
   * @see #getFundChange()
   * @generated
   */
  EAttribute getFundChange_ToAverageHistoricReturnOnInvestment();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.FundChange#getToTER <em>To TER</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>To TER</em>'.
   * @see goedegep.finan.investmentinsurance.model.FundChange#getToTER()
   * @see #getFundChange()
   * @generated
   */
  EAttribute getFundChange_ToTER();

  /**
   * Returns the meta object for class '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment <em>Extra Investment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Extra Investment</em>'.
   * @see goedegep.finan.investmentinsurance.model.ExtraInvestment
   * @generated
   */
  EClass getExtraInvestment();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getPremium <em>Premium</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Premium</em>'.
   * @see goedegep.finan.investmentinsurance.model.ExtraInvestment#getPremium()
   * @see #getExtraInvestment()
   * @generated
   */
  EAttribute getExtraInvestment_Premium();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getDepositDate <em>Deposit Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Deposit Date</em>'.
   * @see goedegep.finan.investmentinsurance.model.ExtraInvestment#getDepositDate()
   * @see #getExtraInvestment()
   * @generated
   */
  EAttribute getExtraInvestment_DepositDate();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getInvestmentParts <em>Investment Parts</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Investment Parts</em>'.
   * @see goedegep.finan.investmentinsurance.model.ExtraInvestment#getInvestmentParts()
   * @see #getExtraInvestment()
   * @generated
   */
  EReference getExtraInvestment_InvestmentParts();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getOverviewDate <em>Overview Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Overview Date</em>'.
   * @see goedegep.finan.investmentinsurance.model.ExtraInvestment#getOverviewDate()
   * @see #getExtraInvestment()
   * @generated
   */
  EAttribute getExtraInvestment_OverviewDate();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.investmentinsurance.model.ExtraInvestment#getParticipations <em>Participations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Participations</em>'.
   * @see goedegep.finan.investmentinsurance.model.ExtraInvestment#getParticipations()
   * @see #getExtraInvestment()
   * @generated
   */
  EReference getExtraInvestment_Participations();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  InvestmentInsuranceFactory getInvestmentInsuranceFactory();

  /**
   * <!-- begin-user-doc -->
	   * Defines literals for the meta objects that represent
	   * <ul>
	   *   <li>each class,</li>
	   *   <li>each feature of each class,</li>
	   *   <li>each enum,</li>
	   *   <li>and each data type</li>
	   * </ul>
	   * <!-- end-user-doc -->
   * @generated
   */
	interface Literals {
		/**
     * The meta object literal for the '{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl <em>Investment Insurance</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getInvestmentInsurance()
     * @generated
     */
    EClass INVESTMENT_INSURANCE = eINSTANCE.getInvestmentInsurance();

    /**
     * The meta object literal for the '<em><b>Insurance Company</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVESTMENT_INSURANCE__INSURANCE_COMPANY = eINSTANCE.getInvestmentInsurance_InsuranceCompany();

    /**
     * The meta object literal for the '<em><b>Policy Number</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVESTMENT_INSURANCE__POLICY_NUMBER = eINSTANCE.getInvestmentInsurance_PolicyNumber();

    /**
     * The meta object literal for the '<em><b>Starting Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVESTMENT_INSURANCE__STARTING_DATE = eINSTANCE.getInvestmentInsurance_StartingDate();

    /**
     * The meta object literal for the '<em><b>Policy Holder</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVESTMENT_INSURANCE__POLICY_HOLDER = eINSTANCE.getInvestmentInsurance_PolicyHolder();

    /**
     * The meta object literal for the '<em><b>Premium</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVESTMENT_INSURANCE__PREMIUM = eINSTANCE.getInvestmentInsurance_Premium();

    /**
     * The meta object literal for the '<em><b>Insured Benefit On Death</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVESTMENT_INSURANCE__INSURED_BENEFIT_ON_DEATH = eINSTANCE.getInvestmentInsurance_InsuredBenefitOnDeath();

    /**
     * The meta object literal for the '<em><b>Investment Parts</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVESTMENT_INSURANCE__INVESTMENT_PARTS = eINSTANCE.getInvestmentInsurance_InvestmentParts();

    /**
     * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVESTMENT_INSURANCE__EVENTS = eINSTANCE.getInvestmentInsurance_Events();

    /**
     * The meta object literal for the '<em><b>Get Last Known Value</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation INVESTMENT_INSURANCE___GET_LAST_KNOWN_VALUE = eINSTANCE.getInvestmentInsurance__GetLastKnownValue();

    /**
     * The meta object literal for the '<em><b>Get Date For Last Known Value</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation INVESTMENT_INSURANCE___GET_DATE_FOR_LAST_KNOWN_VALUE = eINSTANCE.getInvestmentInsurance__GetDateForLastKnownValue();

    /**
     * The meta object literal for the '<em><b>Get Last Annual Statement</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation INVESTMENT_INSURANCE___GET_LAST_ANNUAL_STATEMENT = eINSTANCE.getInvestmentInsurance__GetLastAnnualStatement();

    /**
     * The meta object literal for the '<em><b>Get Average Return On Investment</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation INVESTMENT_INSURANCE___GET_AVERAGE_RETURN_ON_INVESTMENT = eINSTANCE.getInvestmentInsurance__GetAverageReturnOnInvestment();

    /**
     * The meta object literal for the '{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancesDataImpl <em>Investment Insurances Data</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancesDataImpl
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getInvestmentInsurancesData()
     * @generated
     */
    EClass INVESTMENT_INSURANCES_DATA = eINSTANCE.getInvestmentInsurancesData();

    /**
     * The meta object literal for the '<em><b>Insurance Companies</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVESTMENT_INSURANCES_DATA__INSURANCE_COMPANIES = eINSTANCE.getInvestmentInsurancesData_InsuranceCompanies();

    /**
     * The meta object literal for the '<em><b>Investment Insurances</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVESTMENT_INSURANCES_DATA__INVESTMENT_INSURANCES = eINSTANCE.getInvestmentInsurancesData_InvestmentInsurances();

    /**
     * The meta object literal for the '<em><b>Person List</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVESTMENT_INSURANCES_DATA__PERSON_LIST = eINSTANCE.getInvestmentInsurancesData_PersonList();

    /**
     * The meta object literal for the '<em><b>Address List</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVESTMENT_INSURANCES_DATA__ADDRESS_LIST = eINSTANCE.getInvestmentInsurancesData_AddressList();

    /**
     * The meta object literal for the '<em><b>City List</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVESTMENT_INSURANCES_DATA__CITY_LIST = eINSTANCE.getInvestmentInsurancesData_CityList();

    /**
     * The meta object literal for the '<em><b>Phone Number List</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVESTMENT_INSURANCES_DATA__PHONE_NUMBER_LIST = eINSTANCE.getInvestmentInsurancesData_PhoneNumberList();

    /**
     * The meta object literal for the '<em><b>Get Last Known Total Value</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation INVESTMENT_INSURANCES_DATA___GET_LAST_KNOWN_TOTAL_VALUE = eINSTANCE.getInvestmentInsurancesData__GetLastKnownTotalValue();

    /**
     * The meta object literal for the '<em><b>Get Date For Last Known Total Value</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation INVESTMENT_INSURANCES_DATA___GET_DATE_FOR_LAST_KNOWN_TOTAL_VALUE = eINSTANCE.getInvestmentInsurancesData__GetDateForLastKnownTotalValue();

    /**
     * The meta object literal for the '<em><b>Get Total Premium</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation INVESTMENT_INSURANCES_DATA___GET_TOTAL_PREMIUM = eINSTANCE.getInvestmentInsurancesData__GetTotalPremium();

    /**
     * The meta object literal for the '<em><b>Get Average Total Return On Investment</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation INVESTMENT_INSURANCES_DATA___GET_AVERAGE_TOTAL_RETURN_ON_INVESTMENT = eINSTANCE.getInvestmentInsurancesData__GetAverageTotalReturnOnInvestment();

    /**
     * The meta object literal for the '{@link goedegep.finan.investmentinsurance.model.impl.InsuranceCompanyImpl <em>Insurance Company</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.investmentinsurance.model.impl.InsuranceCompanyImpl
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getInsuranceCompany()
     * @generated
     */
    EClass INSURANCE_COMPANY = eINSTANCE.getInsuranceCompany();

    /**
     * The meta object literal for the '<em><b>Investment Funds</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INSURANCE_COMPANY__INVESTMENT_FUNDS = eINSTANCE.getInsuranceCompany_InvestmentFunds();

    /**
     * The meta object literal for the '<em><b>Department</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INSURANCE_COMPANY__DEPARTMENT = eINSTANCE.getInsuranceCompany_Department();

    /**
     * The meta object literal for the '<em><b>Website</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INSURANCE_COMPANY__WEBSITE = eINSTANCE.getInsuranceCompany_Website();

    /**
     * The meta object literal for the '{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl <em>Annual Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getAnnualStatement()
     * @generated
     */
    EClass ANNUAL_STATEMENT = eINSTANCE.getAnnualStatement();

    /**
     * The meta object literal for the '<em><b>Period From</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__PERIOD_FROM = eINSTANCE.getAnnualStatement_PeriodFrom();

    /**
     * The meta object literal for the '<em><b>Period Until</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__PERIOD_UNTIL = eINSTANCE.getAnnualStatement_PeriodUntil();

    /**
     * The meta object literal for the '<em><b>Premium Paid</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__PREMIUM_PAID = eINSTANCE.getAnnualStatement_PremiumPaid();

    /**
     * The meta object literal for the '<em><b>Premium Death Risk Coverage</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__PREMIUM_DEATH_RISK_COVERAGE = eINSTANCE.getAnnualStatement_PremiumDeathRiskCoverage();

    /**
     * The meta object literal for the '<em><b>Premium Occupational Disability Risk Coverage</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE = eINSTANCE.getAnnualStatement_PremiumOccupationalDisabilityRiskCoverage();

    /**
     * The meta object literal for the '<em><b>Upkeep Premium</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__UPKEEP_PREMIUM = eINSTANCE.getAnnualStatement_UpkeepPremium();

    /**
     * The meta object literal for the '<em><b>First Costs Insurance Company</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__FIRST_COSTS_INSURANCE_COMPANY = eINSTANCE.getAnnualStatement_FirstCostsInsuranceCompany();

    /**
     * The meta object literal for the '<em><b>Continuing Costs Insurance Company</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__CONTINUING_COSTS_INSURANCE_COMPANY = eINSTANCE.getAnnualStatement_ContinuingCostsInsuranceCompany();

    /**
     * The meta object literal for the '<em><b>Management Costs</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__MANAGEMENT_COSTS = eINSTANCE.getAnnualStatement_ManagementCosts();

    /**
     * The meta object literal for the '<em><b>Buy And Sell Costs</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__BUY_AND_SELL_COSTS = eINSTANCE.getAnnualStatement_BuyAndSellCosts();

    /**
     * The meta object literal for the '<em><b>Mutation Costs</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__MUTATION_COSTS = eINSTANCE.getAnnualStatement_MutationCosts();

    /**
     * The meta object literal for the '<em><b>Costs Restitution</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__COSTS_RESTITUTION = eINSTANCE.getAnnualStatement_CostsRestitution();

    /**
     * The meta object literal for the '<em><b>Corrections</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__CORRECTIONS = eINSTANCE.getAnnualStatement_Corrections();

    /**
     * The meta object literal for the '<em><b>Earned On The Participations</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__EARNED_ON_THE_PARTICIPATIONS = eINSTANCE.getAnnualStatement_EarnedOnTheParticipations();

    /**
     * The meta object literal for the '<em><b>Participations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ANNUAL_STATEMENT__PARTICIPATIONS = eINSTANCE.getAnnualStatement_Participations();

    /**
     * The meta object literal for the '<em><b>Example Capital On End Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__EXAMPLE_CAPITAL_ON_END_DATE = eINSTANCE.getAnnualStatement_ExampleCapitalOnEndDate();

    /**
     * The meta object literal for the '<em><b>Expected Yearly Costs Increase</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ANNUAL_STATEMENT__EXPECTED_YEARLY_COSTS_INCREASE = eINSTANCE.getAnnualStatement_ExpectedYearlyCostsIncrease();

    /**
     * The meta object literal for the '<em><b>Total Costs</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation ANNUAL_STATEMENT___TOTAL_COSTS = eINSTANCE.getAnnualStatement__TotalCosts();

    /**
     * The meta object literal for the '<em><b>Value At Year Start</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation ANNUAL_STATEMENT___VALUE_AT_YEAR_START = eINSTANCE.getAnnualStatement__ValueAtYearStart();

    /**
     * The meta object literal for the '<em><b>Percentage Costs</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation ANNUAL_STATEMENT___PERCENTAGE_COSTS = eINSTANCE.getAnnualStatement__PercentageCosts();

    /**
     * The meta object literal for the '<em><b>Fixed Costs</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation ANNUAL_STATEMENT___FIXED_COSTS = eINSTANCE.getAnnualStatement__FixedCosts();

    /**
     * The meta object literal for the '{@link goedegep.finan.investmentinsurance.model.impl.InvestmentFundImpl <em>Investment Fund</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentFundImpl
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getInvestmentFund()
     * @generated
     */
    EClass INVESTMENT_FUND = eINSTANCE.getInvestmentFund();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVESTMENT_FUND__NAME = eINSTANCE.getInvestmentFund_Name();

    /**
     * The meta object literal for the '<em><b>Fund Information</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVESTMENT_FUND__FUND_INFORMATION = eINSTANCE.getInvestmentFund_FundInformation();

    /**
     * The meta object literal for the '<em><b>Stock Prices</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVESTMENT_FUND__STOCK_PRICES = eINSTANCE.getInvestmentFund_StockPrices();

    /**
     * The meta object literal for the '<em><b>Get Stock Price</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation INVESTMENT_FUND___GET_STOCK_PRICE__LOCALDATE = eINSTANCE.getInvestmentFund__GetStockPrice__LocalDate();

    /**
     * The meta object literal for the '{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl <em>Participation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.investmentinsurance.model.impl.ParticipationImpl
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getParticipation()
     * @generated
     */
    EClass PARTICIPATION = eINSTANCE.getParticipation();

    /**
     * The meta object literal for the '<em><b>Investment Fund</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PARTICIPATION__INVESTMENT_FUND = eINSTANCE.getParticipation_InvestmentFund();

    /**
     * The meta object literal for the '<em><b>Participation Mutations</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__PARTICIPATION_MUTATIONS = eINSTANCE.getParticipation_ParticipationMutations();

    /**
     * The meta object literal for the '<em><b>Example Return On Investment Net Historic</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC = eINSTANCE.getParticipation_ExampleReturnOnInvestmentNetHistoric();

    /**
     * The meta object literal for the '<em><b>Example Return On Investment Gross</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS = eINSTANCE.getParticipation_ExampleReturnOnInvestmentGross();

    /**
     * The meta object literal for the '<em><b>Example Return On Investment Gross Company Own</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN = eINSTANCE.getParticipation_ExampleReturnOnInvestmentGrossCompanyOwn();

    /**
     * The meta object literal for the '<em><b>Example Return On Investment Pessimistic</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC = eINSTANCE.getParticipation_ExampleReturnOnInvestmentPessimistic();

    /**
     * The meta object literal for the '<em><b>Total Expense Ratio</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__TOTAL_EXPENSE_RATIO = eINSTANCE.getParticipation_TotalExpenseRatio();

    /**
     * The meta object literal for the '<em><b>Example Capital Net Historic</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__EXAMPLE_CAPITAL_NET_HISTORIC = eINSTANCE.getParticipation_ExampleCapitalNetHistoric();

    /**
     * The meta object literal for the '<em><b>Example Capital Gross</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__EXAMPLE_CAPITAL_GROSS = eINSTANCE.getParticipation_ExampleCapitalGross();

    /**
     * The meta object literal for the '<em><b>Example Capital Gross Company Own</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__EXAMPLE_CAPITAL_GROSS_COMPANY_OWN = eINSTANCE.getParticipation_ExampleCapitalGrossCompanyOwn();

    /**
     * The meta object literal for the '<em><b>Example Capital Pessimistic</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__EXAMPLE_CAPITAL_PESSIMISTIC = eINSTANCE.getParticipation_ExampleCapitalPessimistic();

    /**
     * The meta object literal for the '<em><b>Return On Investment Reduction Net Historic</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC = eINSTANCE.getParticipation_ReturnOnInvestmentReductionNetHistoric();

    /**
     * The meta object literal for the '<em><b>Example Capital After Reduction</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__EXAMPLE_CAPITAL_AFTER_REDUCTION = eINSTANCE.getParticipation_ExampleCapitalAfterReduction();

    /**
     * The meta object literal for the '<em><b>Distribution Percentage</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__DISTRIBUTION_PERCENTAGE = eINSTANCE.getParticipation_DistributionPercentage();

    /**
     * The meta object literal for the '<em><b>Standard Fund Return On Investment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__STANDARD_FUND_RETURN_ON_INVESTMENT = eINSTANCE.getParticipation_StandardFundReturnOnInvestment();

    /**
     * The meta object literal for the '<em><b>Example Capital Standard Fund Return On Investment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT = eINSTANCE.getParticipation_ExampleCapitalStandardFundReturnOnInvestment();

    /**
     * The meta object literal for the '<em><b>Number Of Participations End</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__NUMBER_OF_PARTICIPATIONS_END = eINSTANCE.getParticipation_NumberOfParticipationsEnd();

    /**
     * The meta object literal for the '<em><b>Participation Mutations Complete</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARTICIPATION__PARTICIPATION_MUTATIONS_COMPLETE = eINSTANCE.getParticipation_ParticipationMutationsComplete();

    /**
     * The meta object literal for the '<em><b>Annual Statement</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PARTICIPATION__ANNUAL_STATEMENT = eINSTANCE.getParticipation_AnnualStatement();

    /**
     * The meta object literal for the '<em><b>Number Of Participations Start</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation PARTICIPATION___NUMBER_OF_PARTICIPATIONS_START = eINSTANCE.getParticipation__NumberOfParticipationsStart();

    /**
     * The meta object literal for the '<em><b>End Value</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation PARTICIPATION___END_VALUE = eINSTANCE.getParticipation__EndValue();

    /**
     * The meta object literal for the '<em><b>Number Of Participations Bought</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation PARTICIPATION___NUMBER_OF_PARTICIPATIONS_BOUGHT = eINSTANCE.getParticipation__NumberOfParticipationsBought();

    /**
     * The meta object literal for the '<em><b>Number Of Participations Sold</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation PARTICIPATION___NUMBER_OF_PARTICIPATIONS_SOLD = eINSTANCE.getParticipation__NumberOfParticipationsSold();

    /**
     * The meta object literal for the '{@link goedegep.finan.investmentinsurance.model.impl.InvestmentPartImpl <em>Investment Part</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentPartImpl
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getInvestmentPart()
     * @generated
     */
    EClass INVESTMENT_PART = eINSTANCE.getInvestmentPart();

    /**
     * The meta object literal for the '<em><b>Investment Fund</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVESTMENT_PART__INVESTMENT_FUND = eINSTANCE.getInvestmentPart_InvestmentFund();

    /**
     * The meta object literal for the '<em><b>Percentage</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVESTMENT_PART__PERCENTAGE = eINSTANCE.getInvestmentPart_Percentage();

    /**
     * The meta object literal for the '{@link goedegep.finan.investmentinsurance.model.impl.FundChangeImpl <em>Fund Change</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.investmentinsurance.model.impl.FundChangeImpl
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getFundChange()
     * @generated
     */
    EClass FUND_CHANGE = eINSTANCE.getFundChange();

    /**
     * The meta object literal for the '<em><b>From Fund</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FUND_CHANGE__FROM_FUND = eINSTANCE.getFundChange_FromFund();

    /**
     * The meta object literal for the '<em><b>To Fund</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FUND_CHANGE__TO_FUND = eINSTANCE.getFundChange_ToFund();

    /**
     * The meta object literal for the '<em><b>From Number Of Participations</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FUND_CHANGE__FROM_NUMBER_OF_PARTICIPATIONS = eINSTANCE.getFundChange_FromNumberOfParticipations();

    /**
     * The meta object literal for the '<em><b>To Number Of Participations</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FUND_CHANGE__TO_NUMBER_OF_PARTICIPATIONS = eINSTANCE.getFundChange_ToNumberOfParticipations();

    /**
     * The meta object literal for the '<em><b>From Stock Price</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FUND_CHANGE__FROM_STOCK_PRICE = eINSTANCE.getFundChange_FromStockPrice();

    /**
     * The meta object literal for the '<em><b>To Stock Price</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FUND_CHANGE__TO_STOCK_PRICE = eINSTANCE.getFundChange_ToStockPrice();

    /**
     * The meta object literal for the '<em><b>From Average Historic Return On Investment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FUND_CHANGE__FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT = eINSTANCE.getFundChange_FromAverageHistoricReturnOnInvestment();

    /**
     * The meta object literal for the '<em><b>From TER</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FUND_CHANGE__FROM_TER = eINSTANCE.getFundChange_FromTER();

    /**
     * The meta object literal for the '<em><b>To Average Historic Return On Investment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FUND_CHANGE__TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT = eINSTANCE.getFundChange_ToAverageHistoricReturnOnInvestment();

    /**
     * The meta object literal for the '<em><b>To TER</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FUND_CHANGE__TO_TER = eINSTANCE.getFundChange_ToTER();

    /**
     * The meta object literal for the '{@link goedegep.finan.investmentinsurance.model.impl.ExtraInvestmentImpl <em>Extra Investment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.investmentinsurance.model.impl.ExtraInvestmentImpl
     * @see goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancePackageImpl#getExtraInvestment()
     * @generated
     */
    EClass EXTRA_INVESTMENT = eINSTANCE.getExtraInvestment();

    /**
     * The meta object literal for the '<em><b>Premium</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXTRA_INVESTMENT__PREMIUM = eINSTANCE.getExtraInvestment_Premium();

    /**
     * The meta object literal for the '<em><b>Deposit Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXTRA_INVESTMENT__DEPOSIT_DATE = eINSTANCE.getExtraInvestment_DepositDate();

    /**
     * The meta object literal for the '<em><b>Investment Parts</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXTRA_INVESTMENT__INVESTMENT_PARTS = eINSTANCE.getExtraInvestment_InvestmentParts();

    /**
     * The meta object literal for the '<em><b>Overview Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXTRA_INVESTMENT__OVERVIEW_DATE = eINSTANCE.getExtraInvestment_OverviewDate();

    /**
     * The meta object literal for the '<em><b>Participations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXTRA_INVESTMENT__PARTICIPATIONS = eINSTANCE.getExtraInvestment_Participations();

	}

} //BeleggingsVerzekeringPackage
