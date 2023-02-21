/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.*;

import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MortgageFactoryImpl extends EFactoryImpl implements MortgageFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static MortgageFactory init() {
    try {
      MortgageFactory theMortgageFactory = (MortgageFactory)EPackage.Registry.INSTANCE.getEFactory(MortgagePackage.eNS_URI);
      if (theMortgageFactory != null) {
        return theMortgageFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new MortgageFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MortgageFactoryImpl() {
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
      case MortgagePackage.MORTGAGE: return createMortgage();
      case MortgagePackage.FINAL_PAYMENT: return createFinalPayment();
      case MortgagePackage.PERIODIC_PAYMENT: return createPeriodicPayment();
      case MortgagePackage.NEW_INTEREST_RATE: return createNewInterestRate();
      case MortgagePackage.MORTGAGES: return createMortgages();
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE: return createInterestCompensationMortgage();
      case MortgagePackage.INTEGER_TO_ELIST: return (EObject)createIntegerToEList();
      case MortgagePackage.COMPENSATION_PAYMENT: return createCompensationPayment();
      case MortgagePackage.COMPENSATION_PAYMENTS: return createCompensationPayments();
      case MortgagePackage.PERIODIC_PAYMENT_WITH_COMPENSATION: return createPeriodicPaymentWithCompensation();
      case MortgagePackage.NEW_INTEREST_RATE_WITH_COMPENSATION: return createNewInterestRateWithCompensation();
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEW: return createMortgageYearlyOverview();
      case MortgagePackage.INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW: return createInterestCompensationMortgageYearlyOverview();
      case MortgagePackage.INTEREST_RATE_SET: return createInterestRateSet();
      case MortgagePackage.RATE: return createRate();
      case MortgagePackage.MORTGAGE_YEARLY_OVERVIEWS: return createMortgageYearlyOverviews();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue) {
    switch (eDataType.getClassifierID()) {
      case MortgagePackage.MORTGAGE_TYPE:
        return createMortgageTypeFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue) {
    switch (eDataType.getClassifierID()) {
      case MortgagePackage.MORTGAGE_TYPE:
        return convertMortgageTypeToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Mortgage createMortgage() {
    MortgageImpl mortgage = new MortgageImpl();
    return mortgage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FinalPayment createFinalPayment() {
    FinalPaymentImpl finalPayment = new FinalPaymentImpl();
    return finalPayment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PeriodicPayment createPeriodicPayment() {
    PeriodicPaymentImpl periodicPayment = new PeriodicPaymentImpl();
    return periodicPayment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NewInterestRate createNewInterestRate() {
    NewInterestRateImpl newInterestRate = new NewInterestRateImpl();
    return newInterestRate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Mortgages createMortgages() {
    MortgagesImpl mortgages = new MortgagesImpl();
    return mortgages;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public InterestCompensationMortgage createInterestCompensationMortgage() {
    InterestCompensationMortgageImpl interestCompensationMortgage = new InterestCompensationMortgageImpl();
    return interestCompensationMortgage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Map.Entry<Integer, EList<CompensationPayment>> createIntegerToEList() {
    IntegerToEListImpl integerToEList = new IntegerToEListImpl();
    return integerToEList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CompensationPayment createCompensationPayment() {
    CompensationPaymentImpl compensationPayment = new CompensationPaymentImpl();
    return compensationPayment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CompensationPayments createCompensationPayments() {
    CompensationPaymentsImpl compensationPayments = new CompensationPaymentsImpl();
    return compensationPayments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PeriodicPaymentWithCompensation createPeriodicPaymentWithCompensation() {
    PeriodicPaymentWithCompensationImpl periodicPaymentWithCompensation = new PeriodicPaymentWithCompensationImpl();
    return periodicPaymentWithCompensation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NewInterestRateWithCompensation createNewInterestRateWithCompensation() {
    NewInterestRateWithCompensationImpl newInterestRateWithCompensation = new NewInterestRateWithCompensationImpl();
    return newInterestRateWithCompensation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MortgageYearlyOverview createMortgageYearlyOverview() {
    MortgageYearlyOverviewImpl mortgageYearlyOverview = new MortgageYearlyOverviewImpl();
    return mortgageYearlyOverview;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public InterestCompensationMortgageYearlyOverview createInterestCompensationMortgageYearlyOverview() {
    InterestCompensationMortgageYearlyOverviewImpl interestCompensationMortgageYearlyOverview = new InterestCompensationMortgageYearlyOverviewImpl();
    return interestCompensationMortgageYearlyOverview;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public InterestRateSet createInterestRateSet() {
    InterestRateSetImpl interestRateSet = new InterestRateSetImpl();
    return interestRateSet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Rate createRate() {
    RateImpl rate = new RateImpl();
    return rate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MortgageYearlyOverviews createMortgageYearlyOverviews() {
    MortgageYearlyOverviewsImpl mortgageYearlyOverviews = new MortgageYearlyOverviewsImpl();
    return mortgageYearlyOverviews;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MortgageType createMortgageTypeFromString(EDataType eDataType, String initialValue) {
    MortgageType result = MortgageType.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertMortgageTypeToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MortgagePackage getMortgagePackage() {
    return (MortgagePackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static MortgagePackage getPackage() {
    return MortgagePackage.eINSTANCE;
  }

} //MortgageFactoryImpl
