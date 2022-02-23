/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.CompensationPayment;
import goedegep.finan.mortgage.model.CompensationPayments;
import goedegep.finan.mortgage.model.FinalPayment;
import goedegep.finan.mortgage.model.InterestCompensationMortgage;
import goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview;
import goedegep.finan.mortgage.model.InterestRateSet;
import goedegep.finan.mortgage.model.Mortgage;
import goedegep.finan.mortgage.model.MortgageEvent;
import goedegep.finan.mortgage.model.MortgageFactory;
import goedegep.finan.mortgage.model.MortgagePackage;

import goedegep.finan.mortgage.model.MortgageType;
import goedegep.finan.mortgage.model.MortgageYearlyOverview;
import goedegep.finan.mortgage.model.Mortgages;
import goedegep.finan.mortgage.model.NewInterestRate;
import goedegep.finan.mortgage.model.NewInterestRateWithCompensation;
import goedegep.finan.mortgage.model.PeriodicPayment;
import goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation;
import goedegep.finan.mortgage.model.Rate;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.types.model.TypesPackage;

import java.util.Map;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
public class MortgagePackageImpl extends EPackageImpl implements MortgagePackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mortgageEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mortgageEventEClass = null;
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass finalPaymentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass periodicPaymentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass newInterestRateEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mortgagesEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass interestCompensationMortgageEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass integerToEListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass compensationPaymentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass compensationPaymentsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass periodicPaymentWithCompensationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass newInterestRateWithCompensationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mortgageYearlyOverviewEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass interestCompensationMortgageYearlyOverviewEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass interestRateSetEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass rateEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum mortgageTypeEEnum = null;

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
   * @see goedegep.finan.mortgage.model.MortgagePackage#eNS_URI
   * @see #init()
   * @generated
   */
  private MortgagePackageImpl() {
    super(eNS_URI, MortgageFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link MortgagePackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static MortgagePackage init() {
    if (isInited) return (MortgagePackage)EPackage.Registry.INSTANCE.getEPackage(MortgagePackage.eNS_URI);

    // Obtain or create and register package
    Object registeredMortgagePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    MortgagePackageImpl theMortgagePackage = registeredMortgagePackage instanceof MortgagePackageImpl ? (MortgagePackageImpl)registeredMortgagePackage : new MortgagePackageImpl();

    isInited = true;

    // Initialize simple dependencies
    RolodexPackage.eINSTANCE.eClass();
    TypesPackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theMortgagePackage.createPackageContents();

    // Initialize created meta-data
    theMortgagePackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theMortgagePackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(MortgagePackage.eNS_URI, theMortgagePackage);
    return theMortgagePackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getMortgage() {
    return mortgageEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_Lender() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMortgage_LenderAddress() {
    return (EReference)mortgageEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_LenderSigner1() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_LenderSigner2() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_LenderBankAccountNumber() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_LenderBankAccountNumberNameAndAddress() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_BorrowerTitleAndName() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_MortgageName() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMortgage_BorrowerAddress() {
    return (EReference)mortgageEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_MortgageNumber() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_MortgageType() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_StartingDate() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_FirstPaymentDate() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_Duration() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_Principal() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_InterestRate() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgage_FixedInterestPeriod() {
    return (EAttribute)mortgageEClass.getEStructuralFeatures().get(16);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMortgage_MortgageEvents() {
    return (EReference)mortgageEClass.getEStructuralFeatures().get(17);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getMortgage__GetId() {
    return mortgageEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getMortgage__AddMortgageEvent__MortgageEvent() {
    return mortgageEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getMortgage__AddMortgageEvent__MortgageEvent_MortgageEvent_boolean() {
    return mortgageEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getMortgageEvent() {
    return mortgageEventEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgageEvent_Date() {
    return (EAttribute)mortgageEventEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgageEvent_Comments() {
    return (EAttribute)mortgageEventEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgageEvent_NewInterestRate() {
    return (EAttribute)mortgageEventEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getFinalPayment() {
    return finalPaymentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPeriodicPayment() {
    return periodicPaymentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPeriodicPayment_Payment() {
    return (EAttribute)periodicPaymentEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPeriodicPayment_Interest() {
    return (EAttribute)periodicPaymentEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPeriodicPayment_BalanceAfterPayment() {
    return (EAttribute)periodicPaymentEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPeriodicPayment_InterestRate() {
    return (EAttribute)periodicPaymentEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPeriodicPayment_NextPaymentDate() {
    return (EAttribute)periodicPaymentEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getNewInterestRate() {
    return newInterestRateEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getNewInterestRate_InterestRate() {
    return (EAttribute)newInterestRateEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getNewInterestRate_FixedInterestPeriod() {
    return (EAttribute)newInterestRateEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getNewInterestRate_StartingDate() {
    return (EAttribute)newInterestRateEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getMortgages() {
    return mortgagesEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMortgages_Mortgages() {
    return (EReference)mortgagesEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getMortgages_InterestRateSets() {
    return (EReference)mortgagesEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getMortgages__GetMortgage__String() {
    return mortgagesEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getInterestCompensationMortgage() {
    return interestCompensationMortgageEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getInterestCompensationMortgage_CompensationPercentageBorrower() {
    return (EAttribute)interestCompensationMortgageEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getInterestCompensationMortgage_PercentageDecemberPayment() {
    return (EAttribute)interestCompensationMortgageEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getInterestCompensationMortgage_CompensationPaymentsPerYear() {
    return (EReference)interestCompensationMortgageEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getIntegerToEList() {
    return integerToEListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getIntegerToEList_Key() {
    return (EAttribute)integerToEListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getIntegerToEList_Value() {
    return (EReference)integerToEListEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCompensationPayment() {
    return compensationPaymentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getCompensationPayment_Date() {
    return (EAttribute)compensationPaymentEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getCompensationPayment_Amount() {
    return (EAttribute)compensationPaymentEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getCompensationPayment_Description() {
    return (EAttribute)compensationPaymentEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCompensationPayments() {
    return compensationPaymentsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getCompensationPayments_Compensationpayments() {
    return (EReference)compensationPaymentsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPeriodicPaymentWithCompensation() {
    return periodicPaymentWithCompensationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPeriodicPaymentWithCompensation_BorrowerCompensation() {
    return (EAttribute)periodicPaymentWithCompensationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPeriodicPaymentWithCompensation_DecemberPaymentAccumulation() {
    return (EAttribute)periodicPaymentWithCompensationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getNewInterestRateWithCompensation() {
    return newInterestRateWithCompensationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getNewInterestRateWithCompensation_CompensationPercentageBorrower() {
    return (EAttribute)newInterestRateWithCompensationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getNewInterestRateWithCompensation_PercentageDecemberPayment() {
    return (EAttribute)newInterestRateWithCompensationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getMortgageYearlyOverview() {
    return mortgageYearlyOverviewEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgageYearlyOverview_DebtAtBeginningOfYear() {
    return (EAttribute)mortgageYearlyOverviewEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgageYearlyOverview_DebtAtEndOfYear() {
    return (EAttribute)mortgageYearlyOverviewEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgageYearlyOverview_InterestPaid() {
    return (EAttribute)mortgageYearlyOverviewEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgageYearlyOverview_Repayment() {
    return (EAttribute)mortgageYearlyOverviewEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getMortgageYearlyOverview_Year() {
    return (EAttribute)mortgageYearlyOverviewEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getMortgageYearlyOverview__GetPaymentsTotal() {
    return mortgageYearlyOverviewEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getInterestCompensationMortgageYearlyOverview() {
    return interestCompensationMortgageYearlyOverviewEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getInterestCompensationMortgageYearlyOverview_CompensationBorrower() {
    return (EAttribute)interestCompensationMortgageYearlyOverviewEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getInterestCompensationMortgageYearlyOverview_DecemberPayment() {
    return (EAttribute)interestCompensationMortgageYearlyOverviewEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getInterestCompensationMortgageYearlyOverview_CompensationPayment() {
    return (EAttribute)interestCompensationMortgageYearlyOverviewEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getInterestCompensationMortgageYearlyOverview__GetCompensationToBePaid() {
    return interestCompensationMortgageYearlyOverviewEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getInterestRateSet() {
    return interestRateSetEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getInterestRateSet_Description() {
    return (EAttribute)interestRateSetEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getInterestRateSet_Name() {
    return (EAttribute)interestRateSetEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getInterestRateSet_Rates() {
    return (EReference)interestRateSetEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getRate() {
    return rateEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getRate_Date() {
    return (EAttribute)rateEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getRate_Rate() {
    return (EAttribute)rateEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getMortgageType() {
    return mortgageTypeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MortgageFactory getMortgageFactory() {
    return (MortgageFactory)getEFactoryInstance();
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
    mortgageEClass = createEClass(MORTGAGE);
    createEAttribute(mortgageEClass, MORTGAGE__LENDER);
    createEReference(mortgageEClass, MORTGAGE__LENDER_ADDRESS);
    createEAttribute(mortgageEClass, MORTGAGE__LENDER_SIGNER1);
    createEAttribute(mortgageEClass, MORTGAGE__LENDER_SIGNER2);
    createEAttribute(mortgageEClass, MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER);
    createEAttribute(mortgageEClass, MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS);
    createEAttribute(mortgageEClass, MORTGAGE__BORROWER_TITLE_AND_NAME);
    createEAttribute(mortgageEClass, MORTGAGE__MORTGAGE_NAME);
    createEReference(mortgageEClass, MORTGAGE__BORROWER_ADDRESS);
    createEAttribute(mortgageEClass, MORTGAGE__MORTGAGE_NUMBER);
    createEAttribute(mortgageEClass, MORTGAGE__MORTGAGE_TYPE);
    createEAttribute(mortgageEClass, MORTGAGE__STARTING_DATE);
    createEAttribute(mortgageEClass, MORTGAGE__FIRST_PAYMENT_DATE);
    createEAttribute(mortgageEClass, MORTGAGE__DURATION);
    createEAttribute(mortgageEClass, MORTGAGE__PRINCIPAL);
    createEAttribute(mortgageEClass, MORTGAGE__INTEREST_RATE);
    createEAttribute(mortgageEClass, MORTGAGE__FIXED_INTEREST_PERIOD);
    createEReference(mortgageEClass, MORTGAGE__MORTGAGE_EVENTS);
    createEOperation(mortgageEClass, MORTGAGE___GET_ID);
    createEOperation(mortgageEClass, MORTGAGE___ADD_MORTGAGE_EVENT__MORTGAGEEVENT);
    createEOperation(mortgageEClass, MORTGAGE___ADD_MORTGAGE_EVENT__MORTGAGEEVENT_MORTGAGEEVENT_BOOLEAN);

    mortgageEventEClass = createEClass(MORTGAGE_EVENT);
    createEAttribute(mortgageEventEClass, MORTGAGE_EVENT__DATE);
    createEAttribute(mortgageEventEClass, MORTGAGE_EVENT__COMMENTS);
    createEAttribute(mortgageEventEClass, MORTGAGE_EVENT__NEW_INTEREST_RATE);

    finalPaymentEClass = createEClass(FINAL_PAYMENT);

    periodicPaymentEClass = createEClass(PERIODIC_PAYMENT);
    createEAttribute(periodicPaymentEClass, PERIODIC_PAYMENT__PAYMENT);
    createEAttribute(periodicPaymentEClass, PERIODIC_PAYMENT__INTEREST);
    createEAttribute(periodicPaymentEClass, PERIODIC_PAYMENT__BALANCE_AFTER_PAYMENT);
    createEAttribute(periodicPaymentEClass, PERIODIC_PAYMENT__INTEREST_RATE);
    createEAttribute(periodicPaymentEClass, PERIODIC_PAYMENT__NEXT_PAYMENT_DATE);

    newInterestRateEClass = createEClass(NEW_INTEREST_RATE);
    createEAttribute(newInterestRateEClass, NEW_INTEREST_RATE__INTEREST_RATE);
    createEAttribute(newInterestRateEClass, NEW_INTEREST_RATE__FIXED_INTEREST_PERIOD);
    createEAttribute(newInterestRateEClass, NEW_INTEREST_RATE__STARTING_DATE);

    mortgagesEClass = createEClass(MORTGAGES);
    createEReference(mortgagesEClass, MORTGAGES__MORTGAGES);
    createEReference(mortgagesEClass, MORTGAGES__INTEREST_RATE_SETS);
    createEOperation(mortgagesEClass, MORTGAGES___GET_MORTGAGE__STRING);

    interestCompensationMortgageEClass = createEClass(INTEREST_COMPENSATION_MORTGAGE);
    createEAttribute(interestCompensationMortgageEClass, INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PERCENTAGE_BORROWER);
    createEAttribute(interestCompensationMortgageEClass, INTEREST_COMPENSATION_MORTGAGE__PERCENTAGE_DECEMBER_PAYMENT);
    createEReference(interestCompensationMortgageEClass, INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PAYMENTS_PER_YEAR);

    integerToEListEClass = createEClass(INTEGER_TO_ELIST);
    createEAttribute(integerToEListEClass, INTEGER_TO_ELIST__KEY);
    createEReference(integerToEListEClass, INTEGER_TO_ELIST__VALUE);

    compensationPaymentEClass = createEClass(COMPENSATION_PAYMENT);
    createEAttribute(compensationPaymentEClass, COMPENSATION_PAYMENT__DATE);
    createEAttribute(compensationPaymentEClass, COMPENSATION_PAYMENT__AMOUNT);
    createEAttribute(compensationPaymentEClass, COMPENSATION_PAYMENT__DESCRIPTION);

    compensationPaymentsEClass = createEClass(COMPENSATION_PAYMENTS);
    createEReference(compensationPaymentsEClass, COMPENSATION_PAYMENTS__COMPENSATIONPAYMENTS);

    periodicPaymentWithCompensationEClass = createEClass(PERIODIC_PAYMENT_WITH_COMPENSATION);
    createEAttribute(periodicPaymentWithCompensationEClass, PERIODIC_PAYMENT_WITH_COMPENSATION__BORROWER_COMPENSATION);
    createEAttribute(periodicPaymentWithCompensationEClass, PERIODIC_PAYMENT_WITH_COMPENSATION__DECEMBER_PAYMENT_ACCUMULATION);

    newInterestRateWithCompensationEClass = createEClass(NEW_INTEREST_RATE_WITH_COMPENSATION);
    createEAttribute(newInterestRateWithCompensationEClass, NEW_INTEREST_RATE_WITH_COMPENSATION__COMPENSATION_PERCENTAGE_BORROWER);
    createEAttribute(newInterestRateWithCompensationEClass, NEW_INTEREST_RATE_WITH_COMPENSATION__PERCENTAGE_DECEMBER_PAYMENT);

    mortgageYearlyOverviewEClass = createEClass(MORTGAGE_YEARLY_OVERVIEW);
    createEAttribute(mortgageYearlyOverviewEClass, MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_BEGINNING_OF_YEAR);
    createEAttribute(mortgageYearlyOverviewEClass, MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_END_OF_YEAR);
    createEAttribute(mortgageYearlyOverviewEClass, MORTGAGE_YEARLY_OVERVIEW__INTEREST_PAID);
    createEAttribute(mortgageYearlyOverviewEClass, MORTGAGE_YEARLY_OVERVIEW__REPAYMENT);
    createEAttribute(mortgageYearlyOverviewEClass, MORTGAGE_YEARLY_OVERVIEW__YEAR);
    createEOperation(mortgageYearlyOverviewEClass, MORTGAGE_YEARLY_OVERVIEW___GET_PAYMENTS_TOTAL);

    interestCompensationMortgageYearlyOverviewEClass = createEClass(INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW);
    createEAttribute(interestCompensationMortgageYearlyOverviewEClass, INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_BORROWER);
    createEAttribute(interestCompensationMortgageYearlyOverviewEClass, INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__DECEMBER_PAYMENT);
    createEAttribute(interestCompensationMortgageYearlyOverviewEClass, INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_PAYMENT);
    createEOperation(interestCompensationMortgageYearlyOverviewEClass, INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW___GET_COMPENSATION_TO_BE_PAID);

    interestRateSetEClass = createEClass(INTEREST_RATE_SET);
    createEAttribute(interestRateSetEClass, INTEREST_RATE_SET__DESCRIPTION);
    createEAttribute(interestRateSetEClass, INTEREST_RATE_SET__NAME);
    createEReference(interestRateSetEClass, INTEREST_RATE_SET__RATES);

    rateEClass = createEClass(RATE);
    createEAttribute(rateEClass, RATE__DATE);
    createEAttribute(rateEClass, RATE__RATE);

    // Create enums
    mortgageTypeEEnum = createEEnum(MORTGAGE_TYPE);
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
    RolodexPackage theRolodexPackage = (RolodexPackage)EPackage.Registry.INSTANCE.getEPackage(RolodexPackage.eNS_URI);
    TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    finalPaymentEClass.getESuperTypes().add(this.getMortgageEvent());
    periodicPaymentEClass.getESuperTypes().add(this.getMortgageEvent());
    newInterestRateEClass.getESuperTypes().add(this.getMortgageEvent());
    interestCompensationMortgageEClass.getESuperTypes().add(this.getMortgage());
    periodicPaymentWithCompensationEClass.getESuperTypes().add(this.getPeriodicPayment());
    newInterestRateWithCompensationEClass.getESuperTypes().add(this.getNewInterestRate());
    interestCompensationMortgageYearlyOverviewEClass.getESuperTypes().add(this.getMortgageYearlyOverview());

    // Initialize classes, features, and operations; add parameters
    initEClass(mortgageEClass, Mortgage.class, "Mortgage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMortgage_Lender(), ecorePackage.getEString(), "lender", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMortgage_LenderAddress(), theRolodexPackage.getAddress(), null, "lenderAddress", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_LenderSigner1(), ecorePackage.getEString(), "lenderSigner1", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_LenderSigner2(), ecorePackage.getEString(), "lenderSigner2", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_LenderBankAccountNumber(), ecorePackage.getEString(), "lenderBankAccountNumber", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_LenderBankAccountNumberNameAndAddress(), ecorePackage.getEString(), "lenderBankAccountNumberNameAndAddress", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_BorrowerTitleAndName(), ecorePackage.getEString(), "borrowerTitleAndName", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_MortgageName(), ecorePackage.getEString(), "mortgageName", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMortgage_BorrowerAddress(), theRolodexPackage.getAddress(), null, "borrowerAddress", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_MortgageNumber(), ecorePackage.getEString(), "mortgageNumber", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_MortgageType(), this.getMortgageType(), "mortgageType", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_StartingDate(), ecorePackage.getEDate(), "startingDate", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_FirstPaymentDate(), ecorePackage.getEDate(), "firstPaymentDate", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_Duration(), ecorePackage.getEInt(), "duration", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_Principal(), theTypesPackage.getEMoney(), "principal", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_InterestRate(), theTypesPackage.getEFixedPointValue(), "interestRate", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgage_FixedInterestPeriod(), ecorePackage.getEInt(), "fixedInterestPeriod", null, 0, 1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMortgage_MortgageEvents(), this.getMortgageEvent(), null, "mortgageEvents", null, 0, -1, Mortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getMortgage__GetId(), ecorePackage.getEString(), "getId", 0, 1, IS_UNIQUE, IS_ORDERED);

    EOperation op = initEOperation(getMortgage__AddMortgageEvent__MortgageEvent(), null, "addMortgageEvent", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getMortgageEvent(), "mortgageEvent", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getMortgage__AddMortgageEvent__MortgageEvent_MortgageEvent_boolean(), null, "addMortgageEvent", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getMortgageEvent(), "mortgageEvent", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getMortgageEvent(), "insertLocation", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEBoolean(), "before", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(mortgageEventEClass, MortgageEvent.class, "MortgageEvent", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMortgageEvent_Date(), ecorePackage.getEDate(), "date", null, 0, 1, MortgageEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgageEvent_Comments(), ecorePackage.getEString(), "comments", null, 0, 1, MortgageEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgageEvent_NewInterestRate(), theTypesPackage.getEFixedPointValue(), "newInterestRate", null, 0, 1, MortgageEvent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(finalPaymentEClass, FinalPayment.class, "FinalPayment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(periodicPaymentEClass, PeriodicPayment.class, "PeriodicPayment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPeriodicPayment_Payment(), theTypesPackage.getEMoney(), "payment", null, 0, 1, PeriodicPayment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPeriodicPayment_Interest(), theTypesPackage.getEMoney(), "interest", null, 0, 1, PeriodicPayment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPeriodicPayment_BalanceAfterPayment(), theTypesPackage.getEMoney(), "balanceAfterPayment", null, 0, 1, PeriodicPayment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPeriodicPayment_InterestRate(), theTypesPackage.getEFixedPointValue(), "interestRate", null, 0, 1, PeriodicPayment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPeriodicPayment_NextPaymentDate(), ecorePackage.getEDate(), "nextPaymentDate", null, 0, 1, PeriodicPayment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(newInterestRateEClass, NewInterestRate.class, "NewInterestRate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getNewInterestRate_InterestRate(), theTypesPackage.getEFixedPointValue(), "interestRate", null, 0, 1, NewInterestRate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getNewInterestRate_FixedInterestPeriod(), ecorePackage.getEInt(), "fixedInterestPeriod", null, 0, 1, NewInterestRate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getNewInterestRate_StartingDate(), ecorePackage.getEDate(), "startingDate", null, 0, 1, NewInterestRate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(mortgagesEClass, Mortgages.class, "Mortgages", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMortgages_Mortgages(), this.getMortgage(), null, "mortgages", null, 0, -1, Mortgages.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMortgages_InterestRateSets(), this.getInterestRateSet(), null, "interestRateSets", null, 0, -1, Mortgages.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    op = initEOperation(getMortgages__GetMortgage__String(), this.getMortgage(), "getMortgage", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "mortgageNumber", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(interestCompensationMortgageEClass, InterestCompensationMortgage.class, "InterestCompensationMortgage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getInterestCompensationMortgage_CompensationPercentageBorrower(), theTypesPackage.getEFixedPointValue(), "compensationPercentageBorrower", null, 0, 1, InterestCompensationMortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInterestCompensationMortgage_PercentageDecemberPayment(), theTypesPackage.getEFixedPointValue(), "percentageDecemberPayment", null, 0, 1, InterestCompensationMortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInterestCompensationMortgage_CompensationPaymentsPerYear(), this.getIntegerToEList(), null, "compensationPaymentsPerYear", null, 0, -1, InterestCompensationMortgage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(integerToEListEClass, Map.Entry.class, "IntegerToEList", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getIntegerToEList_Key(), ecorePackage.getEIntegerObject(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getIntegerToEList_Value(), this.getCompensationPayment(), null, "value", null, 0, -1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(compensationPaymentEClass, CompensationPayment.class, "CompensationPayment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCompensationPayment_Date(), ecorePackage.getEDate(), "date", null, 0, 1, CompensationPayment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCompensationPayment_Amount(), theTypesPackage.getEMoney(), "amount", null, 0, 1, CompensationPayment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCompensationPayment_Description(), ecorePackage.getEString(), "description", null, 0, 1, CompensationPayment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(compensationPaymentsEClass, CompensationPayments.class, "CompensationPayments", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCompensationPayments_Compensationpayments(), this.getCompensationPayment(), null, "compensationpayments", null, 0, -1, CompensationPayments.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(periodicPaymentWithCompensationEClass, PeriodicPaymentWithCompensation.class, "PeriodicPaymentWithCompensation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPeriodicPaymentWithCompensation_BorrowerCompensation(), theTypesPackage.getEMoney(), "borrowerCompensation", null, 0, 1, PeriodicPaymentWithCompensation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPeriodicPaymentWithCompensation_DecemberPaymentAccumulation(), theTypesPackage.getEMoney(), "decemberPaymentAccumulation", null, 0, 1, PeriodicPaymentWithCompensation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(newInterestRateWithCompensationEClass, NewInterestRateWithCompensation.class, "NewInterestRateWithCompensation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getNewInterestRateWithCompensation_CompensationPercentageBorrower(), theTypesPackage.getEFixedPointValue(), "compensationPercentageBorrower", null, 0, 1, NewInterestRateWithCompensation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getNewInterestRateWithCompensation_PercentageDecemberPayment(), theTypesPackage.getEFixedPointValue(), "percentageDecemberPayment", null, 0, 1, NewInterestRateWithCompensation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(mortgageYearlyOverviewEClass, MortgageYearlyOverview.class, "MortgageYearlyOverview", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMortgageYearlyOverview_DebtAtBeginningOfYear(), theTypesPackage.getEMoney(), "debtAtBeginningOfYear", null, 0, 1, MortgageYearlyOverview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgageYearlyOverview_DebtAtEndOfYear(), theTypesPackage.getEMoney(), "debtAtEndOfYear", null, 0, 1, MortgageYearlyOverview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgageYearlyOverview_InterestPaid(), theTypesPackage.getEMoney(), "interestPaid", null, 0, 1, MortgageYearlyOverview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgageYearlyOverview_Repayment(), theTypesPackage.getEMoney(), "repayment", null, 0, 1, MortgageYearlyOverview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMortgageYearlyOverview_Year(), ecorePackage.getEIntegerObject(), "year", null, 0, 1, MortgageYearlyOverview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getMortgageYearlyOverview__GetPaymentsTotal(), theTypesPackage.getEMoney(), "getPaymentsTotal", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(interestCompensationMortgageYearlyOverviewEClass, InterestCompensationMortgageYearlyOverview.class, "InterestCompensationMortgageYearlyOverview", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getInterestCompensationMortgageYearlyOverview_CompensationBorrower(), theTypesPackage.getEMoney(), "compensationBorrower", null, 0, 1, InterestCompensationMortgageYearlyOverview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInterestCompensationMortgageYearlyOverview_DecemberPayment(), theTypesPackage.getEMoney(), "decemberPayment", null, 0, 1, InterestCompensationMortgageYearlyOverview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInterestCompensationMortgageYearlyOverview_CompensationPayment(), theTypesPackage.getEMoney(), "compensationPayment", null, 0, 1, InterestCompensationMortgageYearlyOverview.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getInterestCompensationMortgageYearlyOverview__GetCompensationToBePaid(), theTypesPackage.getEMoney(), "getCompensationToBePaid", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(interestRateSetEClass, InterestRateSet.class, "InterestRateSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getInterestRateSet_Description(), ecorePackage.getEString(), "description", null, 0, 1, InterestRateSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInterestRateSet_Name(), ecorePackage.getEString(), "name", null, 0, 1, InterestRateSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInterestRateSet_Rates(), this.getRate(), null, "rates", null, 0, -1, InterestRateSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(rateEClass, Rate.class, "Rate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRate_Date(), ecorePackage.getEDate(), "date", null, 0, 1, Rate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getRate_Rate(), theTypesPackage.getEFixedPointValue(), "rate", null, 0, 1, Rate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(mortgageTypeEEnum, MortgageType.class, "MortgageType");
    addEEnumLiteral(mortgageTypeEEnum, MortgageType.ANNUITY);
    addEEnumLiteral(mortgageTypeEEnum, MortgageType.INTEREST_ONLY);

    // Create resource
    createResource(eNS_URI);
  }

} //MortgagePackageImpl
