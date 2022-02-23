/**
 */
package goedegep.finan.mortgage.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see goedegep.finan.mortgage.model.MortgageFactory
 * @model kind="package"
 * @generated
 */
public interface MortgagePackage extends EPackage {
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
  String eNS_URI = "http://www.goedegep.org/mortgage";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "mortgage";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  MortgagePackage eINSTANCE = goedegep.finan.mortgage.model.impl.MortgagePackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.MortgageImpl <em>Mortgage</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.MortgageImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getMortgage()
   * @generated
   */
  int MORTGAGE = 0;

  /**
   * The feature id for the '<em><b>Lender</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__LENDER = 0;

  /**
   * The feature id for the '<em><b>Lender Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__LENDER_ADDRESS = 1;

  /**
   * The feature id for the '<em><b>Lender Signer1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__LENDER_SIGNER1 = 2;

  /**
   * The feature id for the '<em><b>Lender Signer2</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__LENDER_SIGNER2 = 3;

  /**
   * The feature id for the '<em><b>Lender Bank Account Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER = 4;

  /**
   * The feature id for the '<em><b>Lender Bank Account Number Name And Address</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS = 5;

  /**
   * The feature id for the '<em><b>Borrower Title And Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__BORROWER_TITLE_AND_NAME = 6;

  /**
   * The feature id for the '<em><b>Mortgage Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__MORTGAGE_NAME = 7;

  /**
   * The feature id for the '<em><b>Borrower Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__BORROWER_ADDRESS = 8;

  /**
   * The feature id for the '<em><b>Mortgage Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__MORTGAGE_NUMBER = 9;

  /**
   * The feature id for the '<em><b>Mortgage Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__MORTGAGE_TYPE = 10;

  /**
   * The feature id for the '<em><b>Starting Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__STARTING_DATE = 11;

  /**
   * The feature id for the '<em><b>First Payment Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__FIRST_PAYMENT_DATE = 12;

  /**
   * The feature id for the '<em><b>Duration</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__DURATION = 13;

  /**
   * The feature id for the '<em><b>Principal</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__PRINCIPAL = 14;

  /**
   * The feature id for the '<em><b>Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__INTEREST_RATE = 15;

  /**
   * The feature id for the '<em><b>Fixed Interest Period</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__FIXED_INTEREST_PERIOD = 16;

  /**
   * The feature id for the '<em><b>Mortgage Events</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE__MORTGAGE_EVENTS = 17;

  /**
   * The number of structural features of the '<em>Mortgage</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_FEATURE_COUNT = 18;

  /**
   * The operation id for the '<em>Get Id</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE___GET_ID = 0;

  /**
   * The operation id for the '<em>Add Mortgage Event</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE___ADD_MORTGAGE_EVENT__MORTGAGEEVENT = 1;

  /**
   * The operation id for the '<em>Add Mortgage Event</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE___ADD_MORTGAGE_EVENT__MORTGAGEEVENT_MORTGAGEEVENT_BOOLEAN = 2;

  /**
   * The number of operations of the '<em>Mortgage</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_OPERATION_COUNT = 3;


  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.MortgageEventImpl <em>Event</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.MortgageEventImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getMortgageEvent()
   * @generated
   */
  int MORTGAGE_EVENT = 1;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_EVENT__DATE = 0;

  /**
   * The feature id for the '<em><b>Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_EVENT__COMMENTS = 1;

  /**
   * The feature id for the '<em><b>New Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_EVENT__NEW_INTEREST_RATE = 2;

  /**
   * The number of structural features of the '<em>Event</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_EVENT_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Event</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_EVENT_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.FinalPaymentImpl <em>Final Payment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.FinalPaymentImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getFinalPayment()
   * @generated
   */
  int FINAL_PAYMENT = 2;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FINAL_PAYMENT__DATE = MORTGAGE_EVENT__DATE;

  /**
   * The feature id for the '<em><b>Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FINAL_PAYMENT__COMMENTS = MORTGAGE_EVENT__COMMENTS;

  /**
   * The feature id for the '<em><b>New Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FINAL_PAYMENT__NEW_INTEREST_RATE = MORTGAGE_EVENT__NEW_INTEREST_RATE;

  /**
   * The number of structural features of the '<em>Final Payment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FINAL_PAYMENT_FEATURE_COUNT = MORTGAGE_EVENT_FEATURE_COUNT + 0;

  /**
   * The number of operations of the '<em>Final Payment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FINAL_PAYMENT_OPERATION_COUNT = MORTGAGE_EVENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.PeriodicPaymentImpl <em>Periodic Payment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.PeriodicPaymentImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getPeriodicPayment()
   * @generated
   */
  int PERIODIC_PAYMENT = 3;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT__DATE = MORTGAGE_EVENT__DATE;

  /**
   * The feature id for the '<em><b>Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT__COMMENTS = MORTGAGE_EVENT__COMMENTS;

  /**
   * The feature id for the '<em><b>New Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT__NEW_INTEREST_RATE = MORTGAGE_EVENT__NEW_INTEREST_RATE;

  /**
   * The feature id for the '<em><b>Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT__PAYMENT = MORTGAGE_EVENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Interest</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT__INTEREST = MORTGAGE_EVENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Balance After Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT__BALANCE_AFTER_PAYMENT = MORTGAGE_EVENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT__INTEREST_RATE = MORTGAGE_EVENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Next Payment Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT__NEXT_PAYMENT_DATE = MORTGAGE_EVENT_FEATURE_COUNT + 4;

  /**
   * The number of structural features of the '<em>Periodic Payment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_FEATURE_COUNT = MORTGAGE_EVENT_FEATURE_COUNT + 5;

  /**
   * The number of operations of the '<em>Periodic Payment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_OPERATION_COUNT = MORTGAGE_EVENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.NewInterestRateImpl <em>New Interest Rate</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.NewInterestRateImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getNewInterestRate()
   * @generated
   */
  int NEW_INTEREST_RATE = 4;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE__DATE = MORTGAGE_EVENT__DATE;

  /**
   * The feature id for the '<em><b>Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE__COMMENTS = MORTGAGE_EVENT__COMMENTS;

  /**
   * The feature id for the '<em><b>New Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE__NEW_INTEREST_RATE = MORTGAGE_EVENT__NEW_INTEREST_RATE;

  /**
   * The feature id for the '<em><b>Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE__INTEREST_RATE = MORTGAGE_EVENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Fixed Interest Period</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE__FIXED_INTEREST_PERIOD = MORTGAGE_EVENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Starting Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE__STARTING_DATE = MORTGAGE_EVENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>New Interest Rate</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE_FEATURE_COUNT = MORTGAGE_EVENT_FEATURE_COUNT + 3;

  /**
   * The number of operations of the '<em>New Interest Rate</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE_OPERATION_COUNT = MORTGAGE_EVENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.MortgagesImpl <em>Mortgages</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.MortgagesImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getMortgages()
   * @generated
   */
  int MORTGAGES = 5;

  /**
   * The feature id for the '<em><b>Mortgages</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGES__MORTGAGES = 0;

  /**
   * The feature id for the '<em><b>Interest Rate Sets</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGES__INTEREST_RATE_SETS = 1;

  /**
   * The number of structural features of the '<em>Mortgages</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGES_FEATURE_COUNT = 2;

  /**
   * The operation id for the '<em>Get Mortgage</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGES___GET_MORTGAGE__STRING = 0;

  /**
   * The number of operations of the '<em>Mortgages</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGES_OPERATION_COUNT = 1;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.InterestCompensationMortgageImpl <em>Interest Compensation Mortgage</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.InterestCompensationMortgageImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getInterestCompensationMortgage()
   * @generated
   */
  int INTEREST_COMPENSATION_MORTGAGE = 6;

  /**
   * The feature id for the '<em><b>Lender</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__LENDER = MORTGAGE__LENDER;

  /**
   * The feature id for the '<em><b>Lender Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__LENDER_ADDRESS = MORTGAGE__LENDER_ADDRESS;

  /**
   * The feature id for the '<em><b>Lender Signer1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__LENDER_SIGNER1 = MORTGAGE__LENDER_SIGNER1;

  /**
   * The feature id for the '<em><b>Lender Signer2</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__LENDER_SIGNER2 = MORTGAGE__LENDER_SIGNER2;

  /**
   * The feature id for the '<em><b>Lender Bank Account Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER = MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER;

  /**
   * The feature id for the '<em><b>Lender Bank Account Number Name And Address</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS = MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS;

  /**
   * The feature id for the '<em><b>Borrower Title And Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__BORROWER_TITLE_AND_NAME = MORTGAGE__BORROWER_TITLE_AND_NAME;

  /**
   * The feature id for the '<em><b>Mortgage Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__MORTGAGE_NAME = MORTGAGE__MORTGAGE_NAME;

  /**
   * The feature id for the '<em><b>Borrower Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__BORROWER_ADDRESS = MORTGAGE__BORROWER_ADDRESS;

  /**
   * The feature id for the '<em><b>Mortgage Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__MORTGAGE_NUMBER = MORTGAGE__MORTGAGE_NUMBER;

  /**
   * The feature id for the '<em><b>Mortgage Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__MORTGAGE_TYPE = MORTGAGE__MORTGAGE_TYPE;

  /**
   * The feature id for the '<em><b>Starting Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__STARTING_DATE = MORTGAGE__STARTING_DATE;

  /**
   * The feature id for the '<em><b>First Payment Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__FIRST_PAYMENT_DATE = MORTGAGE__FIRST_PAYMENT_DATE;

  /**
   * The feature id for the '<em><b>Duration</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__DURATION = MORTGAGE__DURATION;

  /**
   * The feature id for the '<em><b>Principal</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__PRINCIPAL = MORTGAGE__PRINCIPAL;

  /**
   * The feature id for the '<em><b>Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__INTEREST_RATE = MORTGAGE__INTEREST_RATE;

  /**
   * The feature id for the '<em><b>Fixed Interest Period</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__FIXED_INTEREST_PERIOD = MORTGAGE__FIXED_INTEREST_PERIOD;

  /**
   * The feature id for the '<em><b>Mortgage Events</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__MORTGAGE_EVENTS = MORTGAGE__MORTGAGE_EVENTS;

  /**
   * The feature id for the '<em><b>Compensation Percentage Borrower</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PERCENTAGE_BORROWER = MORTGAGE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Percentage December Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__PERCENTAGE_DECEMBER_PAYMENT = MORTGAGE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Compensation Payments Per Year</b></em>' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PAYMENTS_PER_YEAR = MORTGAGE_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Interest Compensation Mortgage</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_FEATURE_COUNT = MORTGAGE_FEATURE_COUNT + 3;

  /**
   * The operation id for the '<em>Get Id</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE___GET_ID = MORTGAGE___GET_ID;

  /**
   * The operation id for the '<em>Add Mortgage Event</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE___ADD_MORTGAGE_EVENT__MORTGAGEEVENT = MORTGAGE___ADD_MORTGAGE_EVENT__MORTGAGEEVENT;

  /**
   * The operation id for the '<em>Add Mortgage Event</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE___ADD_MORTGAGE_EVENT__MORTGAGEEVENT_MORTGAGEEVENT_BOOLEAN = MORTGAGE___ADD_MORTGAGE_EVENT__MORTGAGEEVENT_MORTGAGEEVENT_BOOLEAN;

  /**
   * The number of operations of the '<em>Interest Compensation Mortgage</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_OPERATION_COUNT = MORTGAGE_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.IntegerToEListImpl <em>Integer To EList</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.IntegerToEListImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getIntegerToEList()
   * @generated
   */
  int INTEGER_TO_ELIST = 7;

  /**
   * The feature id for the '<em><b>Key</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_TO_ELIST__KEY = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_TO_ELIST__VALUE = 1;

  /**
   * The number of structural features of the '<em>Integer To EList</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_TO_ELIST_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Integer To EList</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_TO_ELIST_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.CompensationPaymentImpl <em>Compensation Payment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.CompensationPaymentImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getCompensationPayment()
   * @generated
   */
  int COMPENSATION_PAYMENT = 8;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPENSATION_PAYMENT__DATE = 0;

  /**
   * The feature id for the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPENSATION_PAYMENT__AMOUNT = 1;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPENSATION_PAYMENT__DESCRIPTION = 2;

  /**
   * The number of structural features of the '<em>Compensation Payment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPENSATION_PAYMENT_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Compensation Payment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPENSATION_PAYMENT_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.CompensationPaymentsImpl <em>Compensation Payments</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.CompensationPaymentsImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getCompensationPayments()
   * @generated
   */
  int COMPENSATION_PAYMENTS = 9;

  /**
   * The feature id for the '<em><b>Compensationpayments</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPENSATION_PAYMENTS__COMPENSATIONPAYMENTS = 0;

  /**
   * The number of structural features of the '<em>Compensation Payments</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPENSATION_PAYMENTS_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Compensation Payments</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPENSATION_PAYMENTS_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.PeriodicPaymentWithCompensationImpl <em>Periodic Payment With Compensation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.PeriodicPaymentWithCompensationImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getPeriodicPaymentWithCompensation()
   * @generated
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION = 10;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION__DATE = PERIODIC_PAYMENT__DATE;

  /**
   * The feature id for the '<em><b>Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION__COMMENTS = PERIODIC_PAYMENT__COMMENTS;

  /**
   * The feature id for the '<em><b>New Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION__NEW_INTEREST_RATE = PERIODIC_PAYMENT__NEW_INTEREST_RATE;

  /**
   * The feature id for the '<em><b>Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION__PAYMENT = PERIODIC_PAYMENT__PAYMENT;

  /**
   * The feature id for the '<em><b>Interest</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION__INTEREST = PERIODIC_PAYMENT__INTEREST;

  /**
   * The feature id for the '<em><b>Balance After Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION__BALANCE_AFTER_PAYMENT = PERIODIC_PAYMENT__BALANCE_AFTER_PAYMENT;

  /**
   * The feature id for the '<em><b>Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION__INTEREST_RATE = PERIODIC_PAYMENT__INTEREST_RATE;

  /**
   * The feature id for the '<em><b>Next Payment Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION__NEXT_PAYMENT_DATE = PERIODIC_PAYMENT__NEXT_PAYMENT_DATE;

  /**
   * The feature id for the '<em><b>Borrower Compensation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION__BORROWER_COMPENSATION = PERIODIC_PAYMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>December Payment Accumulation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION__DECEMBER_PAYMENT_ACCUMULATION = PERIODIC_PAYMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Periodic Payment With Compensation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION_FEATURE_COUNT = PERIODIC_PAYMENT_FEATURE_COUNT + 2;

  /**
   * The number of operations of the '<em>Periodic Payment With Compensation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERIODIC_PAYMENT_WITH_COMPENSATION_OPERATION_COUNT = PERIODIC_PAYMENT_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.NewInterestRateWithCompensationImpl <em>New Interest Rate With Compensation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.NewInterestRateWithCompensationImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getNewInterestRateWithCompensation()
   * @generated
   */
  int NEW_INTEREST_RATE_WITH_COMPENSATION = 11;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE_WITH_COMPENSATION__DATE = NEW_INTEREST_RATE__DATE;

  /**
   * The feature id for the '<em><b>Comments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE_WITH_COMPENSATION__COMMENTS = NEW_INTEREST_RATE__COMMENTS;

  /**
   * The feature id for the '<em><b>New Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE_WITH_COMPENSATION__NEW_INTEREST_RATE = NEW_INTEREST_RATE__NEW_INTEREST_RATE;

  /**
   * The feature id for the '<em><b>Interest Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE_WITH_COMPENSATION__INTEREST_RATE = NEW_INTEREST_RATE__INTEREST_RATE;

  /**
   * The feature id for the '<em><b>Fixed Interest Period</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE_WITH_COMPENSATION__FIXED_INTEREST_PERIOD = NEW_INTEREST_RATE__FIXED_INTEREST_PERIOD;

  /**
   * The feature id for the '<em><b>Starting Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE_WITH_COMPENSATION__STARTING_DATE = NEW_INTEREST_RATE__STARTING_DATE;

  /**
   * The feature id for the '<em><b>Compensation Percentage Borrower</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE_WITH_COMPENSATION__COMPENSATION_PERCENTAGE_BORROWER = NEW_INTEREST_RATE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Percentage December Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE_WITH_COMPENSATION__PERCENTAGE_DECEMBER_PAYMENT = NEW_INTEREST_RATE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>New Interest Rate With Compensation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE_WITH_COMPENSATION_FEATURE_COUNT = NEW_INTEREST_RATE_FEATURE_COUNT + 2;

  /**
   * The number of operations of the '<em>New Interest Rate With Compensation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NEW_INTEREST_RATE_WITH_COMPENSATION_OPERATION_COUNT = NEW_INTEREST_RATE_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.MortgageYearlyOverviewImpl <em>Yearly Overview</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.MortgageYearlyOverviewImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getMortgageYearlyOverview()
   * @generated
   */
  int MORTGAGE_YEARLY_OVERVIEW = 12;

  /**
   * The feature id for the '<em><b>Debt At Beginning Of Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_BEGINNING_OF_YEAR = 0;

  /**
   * The feature id for the '<em><b>Debt At End Of Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_END_OF_YEAR = 1;

  /**
   * The feature id for the '<em><b>Interest Paid</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_YEARLY_OVERVIEW__INTEREST_PAID = 2;

  /**
   * The feature id for the '<em><b>Repayment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_YEARLY_OVERVIEW__REPAYMENT = 3;

  /**
   * The feature id for the '<em><b>Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_YEARLY_OVERVIEW__YEAR = 4;

  /**
   * The number of structural features of the '<em>Yearly Overview</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_YEARLY_OVERVIEW_FEATURE_COUNT = 5;

  /**
   * The operation id for the '<em>Get Payments Total</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_YEARLY_OVERVIEW___GET_PAYMENTS_TOTAL = 0;

  /**
   * The number of operations of the '<em>Yearly Overview</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MORTGAGE_YEARLY_OVERVIEW_OPERATION_COUNT = 1;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.InterestCompensationMortgageYearlyOverviewImpl <em>Interest Compensation Mortgage Yearly Overview</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.InterestCompensationMortgageYearlyOverviewImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getInterestCompensationMortgageYearlyOverview()
   * @generated
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW = 13;

  /**
   * The feature id for the '<em><b>Debt At Beginning Of Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_BEGINNING_OF_YEAR = MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_BEGINNING_OF_YEAR;

  /**
   * The feature id for the '<em><b>Debt At End Of Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_END_OF_YEAR = MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_END_OF_YEAR;

  /**
   * The feature id for the '<em><b>Interest Paid</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__INTEREST_PAID = MORTGAGE_YEARLY_OVERVIEW__INTEREST_PAID;

  /**
   * The feature id for the '<em><b>Repayment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__REPAYMENT = MORTGAGE_YEARLY_OVERVIEW__REPAYMENT;

  /**
   * The feature id for the '<em><b>Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__YEAR = MORTGAGE_YEARLY_OVERVIEW__YEAR;

  /**
   * The feature id for the '<em><b>Compensation Borrower</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_BORROWER = MORTGAGE_YEARLY_OVERVIEW_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>December Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__DECEMBER_PAYMENT = MORTGAGE_YEARLY_OVERVIEW_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Compensation Payment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_PAYMENT = MORTGAGE_YEARLY_OVERVIEW_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Interest Compensation Mortgage Yearly Overview</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW_FEATURE_COUNT = MORTGAGE_YEARLY_OVERVIEW_FEATURE_COUNT + 3;

  /**
   * The operation id for the '<em>Get Payments Total</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW___GET_PAYMENTS_TOTAL = MORTGAGE_YEARLY_OVERVIEW___GET_PAYMENTS_TOTAL;

  /**
   * The operation id for the '<em>Get Compensation To Be Paid</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW___GET_COMPENSATION_TO_BE_PAID = MORTGAGE_YEARLY_OVERVIEW_OPERATION_COUNT + 0;

  /**
   * The number of operations of the '<em>Interest Compensation Mortgage Yearly Overview</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW_OPERATION_COUNT = MORTGAGE_YEARLY_OVERVIEW_OPERATION_COUNT + 1;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.InterestRateSetImpl <em>Interest Rate Set</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.InterestRateSetImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getInterestRateSet()
   * @generated
   */
  int INTEREST_RATE_SET = 14;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_RATE_SET__DESCRIPTION = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_RATE_SET__NAME = 1;

  /**
   * The feature id for the '<em><b>Rates</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_RATE_SET__RATES = 2;

  /**
   * The number of structural features of the '<em>Interest Rate Set</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_RATE_SET_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Interest Rate Set</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEREST_RATE_SET_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.impl.RateImpl <em>Rate</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.impl.RateImpl
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getRate()
   * @generated
   */
  int RATE = 15;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RATE__DATE = 0;

  /**
   * The feature id for the '<em><b>Rate</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RATE__RATE = 1;

  /**
   * The number of structural features of the '<em>Rate</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RATE_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Rate</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RATE_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.finan.mortgage.model.MortgageType <em>Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.finan.mortgage.model.MortgageType
   * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getMortgageType()
   * @generated
   */
  int MORTGAGE_TYPE = 16;


  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.Mortgage <em>Mortgage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Mortgage</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage
   * @generated
   */
  EClass getMortgage();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getLender <em>Lender</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Lender</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getLender()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_Lender();

  /**
   * Returns the meta object for the reference '{@link goedegep.finan.mortgage.model.Mortgage#getLenderAddress <em>Lender Address</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Lender Address</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getLenderAddress()
   * @see #getMortgage()
   * @generated
   */
  EReference getMortgage_LenderAddress();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getLenderSigner1 <em>Lender Signer1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Lender Signer1</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getLenderSigner1()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_LenderSigner1();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getLenderSigner2 <em>Lender Signer2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Lender Signer2</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getLenderSigner2()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_LenderSigner2();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getLenderBankAccountNumber <em>Lender Bank Account Number</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Lender Bank Account Number</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getLenderBankAccountNumber()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_LenderBankAccountNumber();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getLenderBankAccountNumberNameAndAddress <em>Lender Bank Account Number Name And Address</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Lender Bank Account Number Name And Address</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getLenderBankAccountNumberNameAndAddress()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_LenderBankAccountNumberNameAndAddress();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getBorrowerTitleAndName <em>Borrower Title And Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Borrower Title And Name</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getBorrowerTitleAndName()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_BorrowerTitleAndName();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageName <em>Mortgage Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Mortgage Name</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getMortgageName()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_MortgageName();

  /**
   * Returns the meta object for the reference '{@link goedegep.finan.mortgage.model.Mortgage#getBorrowerAddress <em>Borrower Address</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Borrower Address</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getBorrowerAddress()
   * @see #getMortgage()
   * @generated
   */
  EReference getMortgage_BorrowerAddress();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageNumber <em>Mortgage Number</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Mortgage Number</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getMortgageNumber()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_MortgageNumber();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageType <em>Mortgage Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Mortgage Type</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getMortgageType()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_MortgageType();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getStartingDate <em>Starting Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Starting Date</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getStartingDate()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_StartingDate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getFirstPaymentDate <em>First Payment Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>First Payment Date</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getFirstPaymentDate()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_FirstPaymentDate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getDuration <em>Duration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Duration</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getDuration()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_Duration();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getPrincipal <em>Principal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Principal</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getPrincipal()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_Principal();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getInterestRate <em>Interest Rate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Interest Rate</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getInterestRate()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_InterestRate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Mortgage#getFixedInterestPeriod <em>Fixed Interest Period</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Fixed Interest Period</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getFixedInterestPeriod()
   * @see #getMortgage()
   * @generated
   */
  EAttribute getMortgage_FixedInterestPeriod();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.mortgage.model.Mortgage#getMortgageEvents <em>Mortgage Events</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Mortgage Events</em>'.
   * @see goedegep.finan.mortgage.model.Mortgage#getMortgageEvents()
   * @see #getMortgage()
   * @generated
   */
  EReference getMortgage_MortgageEvents();

  /**
   * Returns the meta object for the '{@link goedegep.finan.mortgage.model.Mortgage#getId() <em>Get Id</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Id</em>' operation.
   * @see goedegep.finan.mortgage.model.Mortgage#getId()
   * @generated
   */
  EOperation getMortgage__GetId();

  /**
   * Returns the meta object for the '{@link goedegep.finan.mortgage.model.Mortgage#addMortgageEvent(goedegep.finan.mortgage.model.MortgageEvent) <em>Add Mortgage Event</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Add Mortgage Event</em>' operation.
   * @see goedegep.finan.mortgage.model.Mortgage#addMortgageEvent(goedegep.finan.mortgage.model.MortgageEvent)
   * @generated
   */
  EOperation getMortgage__AddMortgageEvent__MortgageEvent();

  /**
   * Returns the meta object for the '{@link goedegep.finan.mortgage.model.Mortgage#addMortgageEvent(goedegep.finan.mortgage.model.MortgageEvent, goedegep.finan.mortgage.model.MortgageEvent, boolean) <em>Add Mortgage Event</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Add Mortgage Event</em>' operation.
   * @see goedegep.finan.mortgage.model.Mortgage#addMortgageEvent(goedegep.finan.mortgage.model.MortgageEvent, goedegep.finan.mortgage.model.MortgageEvent, boolean)
   * @generated
   */
  EOperation getMortgage__AddMortgageEvent__MortgageEvent_MortgageEvent_boolean();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.MortgageEvent <em>Event</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Event</em>'.
   * @see goedegep.finan.mortgage.model.MortgageEvent
   * @generated
   */
  EClass getMortgageEvent();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.MortgageEvent#getDate <em>Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Date</em>'.
   * @see goedegep.finan.mortgage.model.MortgageEvent#getDate()
   * @see #getMortgageEvent()
   * @generated
   */
  EAttribute getMortgageEvent_Date();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.MortgageEvent#getComments <em>Comments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Comments</em>'.
   * @see goedegep.finan.mortgage.model.MortgageEvent#getComments()
   * @see #getMortgageEvent()
   * @generated
   */
  EAttribute getMortgageEvent_Comments();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.MortgageEvent#getNewInterestRate <em>New Interest Rate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>New Interest Rate</em>'.
   * @see goedegep.finan.mortgage.model.MortgageEvent#getNewInterestRate()
   * @see #getMortgageEvent()
   * @generated
   */
  EAttribute getMortgageEvent_NewInterestRate();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.FinalPayment <em>Final Payment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Final Payment</em>'.
   * @see goedegep.finan.mortgage.model.FinalPayment
   * @generated
   */
  EClass getFinalPayment();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.PeriodicPayment <em>Periodic Payment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Periodic Payment</em>'.
   * @see goedegep.finan.mortgage.model.PeriodicPayment
   * @generated
   */
  EClass getPeriodicPayment();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.PeriodicPayment#getPayment <em>Payment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Payment</em>'.
   * @see goedegep.finan.mortgage.model.PeriodicPayment#getPayment()
   * @see #getPeriodicPayment()
   * @generated
   */
  EAttribute getPeriodicPayment_Payment();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.PeriodicPayment#getInterest <em>Interest</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Interest</em>'.
   * @see goedegep.finan.mortgage.model.PeriodicPayment#getInterest()
   * @see #getPeriodicPayment()
   * @generated
   */
  EAttribute getPeriodicPayment_Interest();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.PeriodicPayment#getBalanceAfterPayment <em>Balance After Payment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Balance After Payment</em>'.
   * @see goedegep.finan.mortgage.model.PeriodicPayment#getBalanceAfterPayment()
   * @see #getPeriodicPayment()
   * @generated
   */
  EAttribute getPeriodicPayment_BalanceAfterPayment();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.PeriodicPayment#getInterestRate <em>Interest Rate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Interest Rate</em>'.
   * @see goedegep.finan.mortgage.model.PeriodicPayment#getInterestRate()
   * @see #getPeriodicPayment()
   * @generated
   */
  EAttribute getPeriodicPayment_InterestRate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.PeriodicPayment#getNextPaymentDate <em>Next Payment Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Next Payment Date</em>'.
   * @see goedegep.finan.mortgage.model.PeriodicPayment#getNextPaymentDate()
   * @see #getPeriodicPayment()
   * @generated
   */
  EAttribute getPeriodicPayment_NextPaymentDate();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.NewInterestRate <em>New Interest Rate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>New Interest Rate</em>'.
   * @see goedegep.finan.mortgage.model.NewInterestRate
   * @generated
   */
  EClass getNewInterestRate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.NewInterestRate#getInterestRate <em>Interest Rate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Interest Rate</em>'.
   * @see goedegep.finan.mortgage.model.NewInterestRate#getInterestRate()
   * @see #getNewInterestRate()
   * @generated
   */
  EAttribute getNewInterestRate_InterestRate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.NewInterestRate#getFixedInterestPeriod <em>Fixed Interest Period</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Fixed Interest Period</em>'.
   * @see goedegep.finan.mortgage.model.NewInterestRate#getFixedInterestPeriod()
   * @see #getNewInterestRate()
   * @generated
   */
  EAttribute getNewInterestRate_FixedInterestPeriod();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.NewInterestRate#getStartingDate <em>Starting Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Starting Date</em>'.
   * @see goedegep.finan.mortgage.model.NewInterestRate#getStartingDate()
   * @see #getNewInterestRate()
   * @generated
   */
  EAttribute getNewInterestRate_StartingDate();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.Mortgages <em>Mortgages</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Mortgages</em>'.
   * @see goedegep.finan.mortgage.model.Mortgages
   * @generated
   */
  EClass getMortgages();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.mortgage.model.Mortgages#getMortgages <em>Mortgages</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Mortgages</em>'.
   * @see goedegep.finan.mortgage.model.Mortgages#getMortgages()
   * @see #getMortgages()
   * @generated
   */
  EReference getMortgages_Mortgages();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.mortgage.model.Mortgages#getInterestRateSets <em>Interest Rate Sets</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Interest Rate Sets</em>'.
   * @see goedegep.finan.mortgage.model.Mortgages#getInterestRateSets()
   * @see #getMortgages()
   * @generated
   */
  EReference getMortgages_InterestRateSets();

  /**
   * Returns the meta object for the '{@link goedegep.finan.mortgage.model.Mortgages#getMortgage(java.lang.String) <em>Get Mortgage</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Mortgage</em>' operation.
   * @see goedegep.finan.mortgage.model.Mortgages#getMortgage(java.lang.String)
   * @generated
   */
  EOperation getMortgages__GetMortgage__String();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.InterestCompensationMortgage <em>Interest Compensation Mortgage</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Interest Compensation Mortgage</em>'.
   * @see goedegep.finan.mortgage.model.InterestCompensationMortgage
   * @generated
   */
  EClass getInterestCompensationMortgage();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.InterestCompensationMortgage#getCompensationPercentageBorrower <em>Compensation Percentage Borrower</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Compensation Percentage Borrower</em>'.
   * @see goedegep.finan.mortgage.model.InterestCompensationMortgage#getCompensationPercentageBorrower()
   * @see #getInterestCompensationMortgage()
   * @generated
   */
  EAttribute getInterestCompensationMortgage_CompensationPercentageBorrower();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.InterestCompensationMortgage#getPercentageDecemberPayment <em>Percentage December Payment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Percentage December Payment</em>'.
   * @see goedegep.finan.mortgage.model.InterestCompensationMortgage#getPercentageDecemberPayment()
   * @see #getInterestCompensationMortgage()
   * @generated
   */
  EAttribute getInterestCompensationMortgage_PercentageDecemberPayment();

  /**
   * Returns the meta object for the map '{@link goedegep.finan.mortgage.model.InterestCompensationMortgage#getCompensationPaymentsPerYear <em>Compensation Payments Per Year</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>Compensation Payments Per Year</em>'.
   * @see goedegep.finan.mortgage.model.InterestCompensationMortgage#getCompensationPaymentsPerYear()
   * @see #getInterestCompensationMortgage()
   * @generated
   */
  EReference getInterestCompensationMortgage_CompensationPaymentsPerYear();

  /**
   * Returns the meta object for class '{@link java.util.Map.Entry <em>Integer To EList</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Integer To EList</em>'.
   * @see java.util.Map.Entry
   * @model keyDataType="org.eclipse.emf.ecore.EIntegerObject"
   *        valueType="goedegep.finan.mortgage.model.CompensationPayment" valueContainment="true" valueMany="true"
   * @generated
   */
  EClass getIntegerToEList();

  /**
   * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Key</em>'.
   * @see java.util.Map.Entry
   * @see #getIntegerToEList()
   * @generated
   */
  EAttribute getIntegerToEList_Key();

  /**
   * Returns the meta object for the containment reference list '{@link java.util.Map.Entry <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Value</em>'.
   * @see java.util.Map.Entry
   * @see #getIntegerToEList()
   * @generated
   */
  EReference getIntegerToEList_Value();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.CompensationPayment <em>Compensation Payment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Compensation Payment</em>'.
   * @see goedegep.finan.mortgage.model.CompensationPayment
   * @generated
   */
  EClass getCompensationPayment();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.CompensationPayment#getDate <em>Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Date</em>'.
   * @see goedegep.finan.mortgage.model.CompensationPayment#getDate()
   * @see #getCompensationPayment()
   * @generated
   */
  EAttribute getCompensationPayment_Date();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.CompensationPayment#getAmount <em>Amount</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Amount</em>'.
   * @see goedegep.finan.mortgage.model.CompensationPayment#getAmount()
   * @see #getCompensationPayment()
   * @generated
   */
  EAttribute getCompensationPayment_Amount();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.CompensationPayment#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.finan.mortgage.model.CompensationPayment#getDescription()
   * @see #getCompensationPayment()
   * @generated
   */
  EAttribute getCompensationPayment_Description();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.CompensationPayments <em>Compensation Payments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Compensation Payments</em>'.
   * @see goedegep.finan.mortgage.model.CompensationPayments
   * @generated
   */
  EClass getCompensationPayments();

  /**
   * Returns the meta object for the reference list '{@link goedegep.finan.mortgage.model.CompensationPayments#getCompensationpayments <em>Compensationpayments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Compensationpayments</em>'.
   * @see goedegep.finan.mortgage.model.CompensationPayments#getCompensationpayments()
   * @see #getCompensationPayments()
   * @generated
   */
  EReference getCompensationPayments_Compensationpayments();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation <em>Periodic Payment With Compensation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Periodic Payment With Compensation</em>'.
   * @see goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation
   * @generated
   */
  EClass getPeriodicPaymentWithCompensation();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation#getBorrowerCompensation <em>Borrower Compensation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Borrower Compensation</em>'.
   * @see goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation#getBorrowerCompensation()
   * @see #getPeriodicPaymentWithCompensation()
   * @generated
   */
  EAttribute getPeriodicPaymentWithCompensation_BorrowerCompensation();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation#getDecemberPaymentAccumulation <em>December Payment Accumulation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>December Payment Accumulation</em>'.
   * @see goedegep.finan.mortgage.model.PeriodicPaymentWithCompensation#getDecemberPaymentAccumulation()
   * @see #getPeriodicPaymentWithCompensation()
   * @generated
   */
  EAttribute getPeriodicPaymentWithCompensation_DecemberPaymentAccumulation();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.NewInterestRateWithCompensation <em>New Interest Rate With Compensation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>New Interest Rate With Compensation</em>'.
   * @see goedegep.finan.mortgage.model.NewInterestRateWithCompensation
   * @generated
   */
  EClass getNewInterestRateWithCompensation();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.NewInterestRateWithCompensation#getCompensationPercentageBorrower <em>Compensation Percentage Borrower</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Compensation Percentage Borrower</em>'.
   * @see goedegep.finan.mortgage.model.NewInterestRateWithCompensation#getCompensationPercentageBorrower()
   * @see #getNewInterestRateWithCompensation()
   * @generated
   */
  EAttribute getNewInterestRateWithCompensation_CompensationPercentageBorrower();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.NewInterestRateWithCompensation#getPercentageDecemberPayment <em>Percentage December Payment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Percentage December Payment</em>'.
   * @see goedegep.finan.mortgage.model.NewInterestRateWithCompensation#getPercentageDecemberPayment()
   * @see #getNewInterestRateWithCompensation()
   * @generated
   */
  EAttribute getNewInterestRateWithCompensation_PercentageDecemberPayment();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview <em>Yearly Overview</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Yearly Overview</em>'.
   * @see goedegep.finan.mortgage.model.MortgageYearlyOverview
   * @generated
   */
  EClass getMortgageYearlyOverview();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getDebtAtBeginningOfYear <em>Debt At Beginning Of Year</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Debt At Beginning Of Year</em>'.
   * @see goedegep.finan.mortgage.model.MortgageYearlyOverview#getDebtAtBeginningOfYear()
   * @see #getMortgageYearlyOverview()
   * @generated
   */
  EAttribute getMortgageYearlyOverview_DebtAtBeginningOfYear();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getDebtAtEndOfYear <em>Debt At End Of Year</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Debt At End Of Year</em>'.
   * @see goedegep.finan.mortgage.model.MortgageYearlyOverview#getDebtAtEndOfYear()
   * @see #getMortgageYearlyOverview()
   * @generated
   */
  EAttribute getMortgageYearlyOverview_DebtAtEndOfYear();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getInterestPaid <em>Interest Paid</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Interest Paid</em>'.
   * @see goedegep.finan.mortgage.model.MortgageYearlyOverview#getInterestPaid()
   * @see #getMortgageYearlyOverview()
   * @generated
   */
  EAttribute getMortgageYearlyOverview_InterestPaid();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getRepayment <em>Repayment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Repayment</em>'.
   * @see goedegep.finan.mortgage.model.MortgageYearlyOverview#getRepayment()
   * @see #getMortgageYearlyOverview()
   * @generated
   */
  EAttribute getMortgageYearlyOverview_Repayment();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getYear <em>Year</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Year</em>'.
   * @see goedegep.finan.mortgage.model.MortgageYearlyOverview#getYear()
   * @see #getMortgageYearlyOverview()
   * @generated
   */
  EAttribute getMortgageYearlyOverview_Year();

  /**
   * Returns the meta object for the '{@link goedegep.finan.mortgage.model.MortgageYearlyOverview#getPaymentsTotal() <em>Get Payments Total</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Payments Total</em>' operation.
   * @see goedegep.finan.mortgage.model.MortgageYearlyOverview#getPaymentsTotal()
   * @generated
   */
  EOperation getMortgageYearlyOverview__GetPaymentsTotal();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview <em>Interest Compensation Mortgage Yearly Overview</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Interest Compensation Mortgage Yearly Overview</em>'.
   * @see goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview
   * @generated
   */
  EClass getInterestCompensationMortgageYearlyOverview();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationBorrower <em>Compensation Borrower</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Compensation Borrower</em>'.
   * @see goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationBorrower()
   * @see #getInterestCompensationMortgageYearlyOverview()
   * @generated
   */
  EAttribute getInterestCompensationMortgageYearlyOverview_CompensationBorrower();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getDecemberPayment <em>December Payment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>December Payment</em>'.
   * @see goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getDecemberPayment()
   * @see #getInterestCompensationMortgageYearlyOverview()
   * @generated
   */
  EAttribute getInterestCompensationMortgageYearlyOverview_DecemberPayment();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationPayment <em>Compensation Payment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Compensation Payment</em>'.
   * @see goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationPayment()
   * @see #getInterestCompensationMortgageYearlyOverview()
   * @generated
   */
  EAttribute getInterestCompensationMortgageYearlyOverview_CompensationPayment();

  /**
   * Returns the meta object for the '{@link goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationToBePaid() <em>Get Compensation To Be Paid</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Compensation To Be Paid</em>' operation.
   * @see goedegep.finan.mortgage.model.InterestCompensationMortgageYearlyOverview#getCompensationToBePaid()
   * @generated
   */
  EOperation getInterestCompensationMortgageYearlyOverview__GetCompensationToBePaid();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.InterestRateSet <em>Interest Rate Set</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Interest Rate Set</em>'.
   * @see goedegep.finan.mortgage.model.InterestRateSet
   * @generated
   */
  EClass getInterestRateSet();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.InterestRateSet#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.finan.mortgage.model.InterestRateSet#getDescription()
   * @see #getInterestRateSet()
   * @generated
   */
  EAttribute getInterestRateSet_Description();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.InterestRateSet#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.finan.mortgage.model.InterestRateSet#getName()
   * @see #getInterestRateSet()
   * @generated
   */
  EAttribute getInterestRateSet_Name();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.finan.mortgage.model.InterestRateSet#getRates <em>Rates</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Rates</em>'.
   * @see goedegep.finan.mortgage.model.InterestRateSet#getRates()
   * @see #getInterestRateSet()
   * @generated
   */
  EReference getInterestRateSet_Rates();

  /**
   * Returns the meta object for class '{@link goedegep.finan.mortgage.model.Rate <em>Rate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Rate</em>'.
   * @see goedegep.finan.mortgage.model.Rate
   * @generated
   */
  EClass getRate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Rate#getDate <em>Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Date</em>'.
   * @see goedegep.finan.mortgage.model.Rate#getDate()
   * @see #getRate()
   * @generated
   */
  EAttribute getRate_Date();

  /**
   * Returns the meta object for the attribute '{@link goedegep.finan.mortgage.model.Rate#getRate <em>Rate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Rate</em>'.
   * @see goedegep.finan.mortgage.model.Rate#getRate()
   * @see #getRate()
   * @generated
   */
  EAttribute getRate_Rate();

  /**
   * Returns the meta object for enum '{@link goedegep.finan.mortgage.model.MortgageType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Type</em>'.
   * @see goedegep.finan.mortgage.model.MortgageType
   * @generated
   */
  EEnum getMortgageType();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  MortgageFactory getMortgageFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.MortgageImpl <em>Mortgage</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.MortgageImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getMortgage()
     * @generated
     */
    EClass MORTGAGE = eINSTANCE.getMortgage();
    /**
     * The meta object literal for the '<em><b>Lender</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__LENDER = eINSTANCE.getMortgage_Lender();
    /**
     * The meta object literal for the '<em><b>Lender Address</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MORTGAGE__LENDER_ADDRESS = eINSTANCE.getMortgage_LenderAddress();
    /**
     * The meta object literal for the '<em><b>Lender Signer1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__LENDER_SIGNER1 = eINSTANCE.getMortgage_LenderSigner1();
    /**
     * The meta object literal for the '<em><b>Lender Signer2</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__LENDER_SIGNER2 = eINSTANCE.getMortgage_LenderSigner2();
    /**
     * The meta object literal for the '<em><b>Lender Bank Account Number</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER = eINSTANCE.getMortgage_LenderBankAccountNumber();
    /**
     * The meta object literal for the '<em><b>Lender Bank Account Number Name And Address</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS = eINSTANCE.getMortgage_LenderBankAccountNumberNameAndAddress();
    /**
     * The meta object literal for the '<em><b>Borrower Title And Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__BORROWER_TITLE_AND_NAME = eINSTANCE.getMortgage_BorrowerTitleAndName();
    /**
     * The meta object literal for the '<em><b>Mortgage Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__MORTGAGE_NAME = eINSTANCE.getMortgage_MortgageName();
    /**
     * The meta object literal for the '<em><b>Borrower Address</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MORTGAGE__BORROWER_ADDRESS = eINSTANCE.getMortgage_BorrowerAddress();
    /**
     * The meta object literal for the '<em><b>Mortgage Number</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__MORTGAGE_NUMBER = eINSTANCE.getMortgage_MortgageNumber();
    /**
     * The meta object literal for the '<em><b>Mortgage Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__MORTGAGE_TYPE = eINSTANCE.getMortgage_MortgageType();
    /**
     * The meta object literal for the '<em><b>Starting Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__STARTING_DATE = eINSTANCE.getMortgage_StartingDate();
    /**
     * The meta object literal for the '<em><b>First Payment Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__FIRST_PAYMENT_DATE = eINSTANCE.getMortgage_FirstPaymentDate();
    /**
     * The meta object literal for the '<em><b>Duration</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__DURATION = eINSTANCE.getMortgage_Duration();
    /**
     * The meta object literal for the '<em><b>Principal</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__PRINCIPAL = eINSTANCE.getMortgage_Principal();
    /**
     * The meta object literal for the '<em><b>Interest Rate</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__INTEREST_RATE = eINSTANCE.getMortgage_InterestRate();
    /**
     * The meta object literal for the '<em><b>Fixed Interest Period</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE__FIXED_INTEREST_PERIOD = eINSTANCE.getMortgage_FixedInterestPeriod();
    /**
     * The meta object literal for the '<em><b>Mortgage Events</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MORTGAGE__MORTGAGE_EVENTS = eINSTANCE.getMortgage_MortgageEvents();
    /**
     * The meta object literal for the '<em><b>Get Id</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation MORTGAGE___GET_ID = eINSTANCE.getMortgage__GetId();
    /**
     * The meta object literal for the '<em><b>Add Mortgage Event</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation MORTGAGE___ADD_MORTGAGE_EVENT__MORTGAGEEVENT = eINSTANCE.getMortgage__AddMortgageEvent__MortgageEvent();
    /**
     * The meta object literal for the '<em><b>Add Mortgage Event</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation MORTGAGE___ADD_MORTGAGE_EVENT__MORTGAGEEVENT_MORTGAGEEVENT_BOOLEAN = eINSTANCE.getMortgage__AddMortgageEvent__MortgageEvent_MortgageEvent_boolean();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.MortgageEventImpl <em>Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.MortgageEventImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getMortgageEvent()
     * @generated
     */
    EClass MORTGAGE_EVENT = eINSTANCE.getMortgageEvent();
    /**
     * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE_EVENT__DATE = eINSTANCE.getMortgageEvent_Date();
    /**
     * The meta object literal for the '<em><b>Comments</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE_EVENT__COMMENTS = eINSTANCE.getMortgageEvent_Comments();
    /**
     * The meta object literal for the '<em><b>New Interest Rate</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE_EVENT__NEW_INTEREST_RATE = eINSTANCE.getMortgageEvent_NewInterestRate();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.FinalPaymentImpl <em>Final Payment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.FinalPaymentImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getFinalPayment()
     * @generated
     */
    EClass FINAL_PAYMENT = eINSTANCE.getFinalPayment();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.PeriodicPaymentImpl <em>Periodic Payment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.PeriodicPaymentImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getPeriodicPayment()
     * @generated
     */
    EClass PERIODIC_PAYMENT = eINSTANCE.getPeriodicPayment();
    /**
     * The meta object literal for the '<em><b>Payment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERIODIC_PAYMENT__PAYMENT = eINSTANCE.getPeriodicPayment_Payment();
    /**
     * The meta object literal for the '<em><b>Interest</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERIODIC_PAYMENT__INTEREST = eINSTANCE.getPeriodicPayment_Interest();
    /**
     * The meta object literal for the '<em><b>Balance After Payment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERIODIC_PAYMENT__BALANCE_AFTER_PAYMENT = eINSTANCE.getPeriodicPayment_BalanceAfterPayment();
    /**
     * The meta object literal for the '<em><b>Interest Rate</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERIODIC_PAYMENT__INTEREST_RATE = eINSTANCE.getPeriodicPayment_InterestRate();
    /**
     * The meta object literal for the '<em><b>Next Payment Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERIODIC_PAYMENT__NEXT_PAYMENT_DATE = eINSTANCE.getPeriodicPayment_NextPaymentDate();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.NewInterestRateImpl <em>New Interest Rate</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.NewInterestRateImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getNewInterestRate()
     * @generated
     */
    EClass NEW_INTEREST_RATE = eINSTANCE.getNewInterestRate();
    /**
     * The meta object literal for the '<em><b>Interest Rate</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NEW_INTEREST_RATE__INTEREST_RATE = eINSTANCE.getNewInterestRate_InterestRate();
    /**
     * The meta object literal for the '<em><b>Fixed Interest Period</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NEW_INTEREST_RATE__FIXED_INTEREST_PERIOD = eINSTANCE.getNewInterestRate_FixedInterestPeriod();
    /**
     * The meta object literal for the '<em><b>Starting Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NEW_INTEREST_RATE__STARTING_DATE = eINSTANCE.getNewInterestRate_StartingDate();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.MortgagesImpl <em>Mortgages</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.MortgagesImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getMortgages()
     * @generated
     */
    EClass MORTGAGES = eINSTANCE.getMortgages();
    /**
     * The meta object literal for the '<em><b>Mortgages</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MORTGAGES__MORTGAGES = eINSTANCE.getMortgages_Mortgages();
    /**
     * The meta object literal for the '<em><b>Interest Rate Sets</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MORTGAGES__INTEREST_RATE_SETS = eINSTANCE.getMortgages_InterestRateSets();
    /**
     * The meta object literal for the '<em><b>Get Mortgage</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation MORTGAGES___GET_MORTGAGE__STRING = eINSTANCE.getMortgages__GetMortgage__String();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.InterestCompensationMortgageImpl <em>Interest Compensation Mortgage</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.InterestCompensationMortgageImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getInterestCompensationMortgage()
     * @generated
     */
    EClass INTEREST_COMPENSATION_MORTGAGE = eINSTANCE.getInterestCompensationMortgage();
    /**
     * The meta object literal for the '<em><b>Compensation Percentage Borrower</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PERCENTAGE_BORROWER = eINSTANCE.getInterestCompensationMortgage_CompensationPercentageBorrower();
    /**
     * The meta object literal for the '<em><b>Percentage December Payment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEREST_COMPENSATION_MORTGAGE__PERCENTAGE_DECEMBER_PAYMENT = eINSTANCE.getInterestCompensationMortgage_PercentageDecemberPayment();
    /**
     * The meta object literal for the '<em><b>Compensation Payments Per Year</b></em>' map feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTEREST_COMPENSATION_MORTGAGE__COMPENSATION_PAYMENTS_PER_YEAR = eINSTANCE.getInterestCompensationMortgage_CompensationPaymentsPerYear();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.IntegerToEListImpl <em>Integer To EList</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.IntegerToEListImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getIntegerToEList()
     * @generated
     */
    EClass INTEGER_TO_ELIST = eINSTANCE.getIntegerToEList();
    /**
     * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEGER_TO_ELIST__KEY = eINSTANCE.getIntegerToEList_Key();
    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTEGER_TO_ELIST__VALUE = eINSTANCE.getIntegerToEList_Value();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.CompensationPaymentImpl <em>Compensation Payment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.CompensationPaymentImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getCompensationPayment()
     * @generated
     */
    EClass COMPENSATION_PAYMENT = eINSTANCE.getCompensationPayment();
    /**
     * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COMPENSATION_PAYMENT__DATE = eINSTANCE.getCompensationPayment_Date();
    /**
     * The meta object literal for the '<em><b>Amount</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COMPENSATION_PAYMENT__AMOUNT = eINSTANCE.getCompensationPayment_Amount();
    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COMPENSATION_PAYMENT__DESCRIPTION = eINSTANCE.getCompensationPayment_Description();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.CompensationPaymentsImpl <em>Compensation Payments</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.CompensationPaymentsImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getCompensationPayments()
     * @generated
     */
    EClass COMPENSATION_PAYMENTS = eINSTANCE.getCompensationPayments();
    /**
     * The meta object literal for the '<em><b>Compensationpayments</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPENSATION_PAYMENTS__COMPENSATIONPAYMENTS = eINSTANCE.getCompensationPayments_Compensationpayments();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.PeriodicPaymentWithCompensationImpl <em>Periodic Payment With Compensation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.PeriodicPaymentWithCompensationImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getPeriodicPaymentWithCompensation()
     * @generated
     */
    EClass PERIODIC_PAYMENT_WITH_COMPENSATION = eINSTANCE.getPeriodicPaymentWithCompensation();
    /**
     * The meta object literal for the '<em><b>Borrower Compensation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERIODIC_PAYMENT_WITH_COMPENSATION__BORROWER_COMPENSATION = eINSTANCE.getPeriodicPaymentWithCompensation_BorrowerCompensation();
    /**
     * The meta object literal for the '<em><b>December Payment Accumulation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERIODIC_PAYMENT_WITH_COMPENSATION__DECEMBER_PAYMENT_ACCUMULATION = eINSTANCE.getPeriodicPaymentWithCompensation_DecemberPaymentAccumulation();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.NewInterestRateWithCompensationImpl <em>New Interest Rate With Compensation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.NewInterestRateWithCompensationImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getNewInterestRateWithCompensation()
     * @generated
     */
    EClass NEW_INTEREST_RATE_WITH_COMPENSATION = eINSTANCE.getNewInterestRateWithCompensation();
    /**
     * The meta object literal for the '<em><b>Compensation Percentage Borrower</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NEW_INTEREST_RATE_WITH_COMPENSATION__COMPENSATION_PERCENTAGE_BORROWER = eINSTANCE.getNewInterestRateWithCompensation_CompensationPercentageBorrower();
    /**
     * The meta object literal for the '<em><b>Percentage December Payment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NEW_INTEREST_RATE_WITH_COMPENSATION__PERCENTAGE_DECEMBER_PAYMENT = eINSTANCE.getNewInterestRateWithCompensation_PercentageDecemberPayment();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.MortgageYearlyOverviewImpl <em>Yearly Overview</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.MortgageYearlyOverviewImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getMortgageYearlyOverview()
     * @generated
     */
    EClass MORTGAGE_YEARLY_OVERVIEW = eINSTANCE.getMortgageYearlyOverview();
    /**
     * The meta object literal for the '<em><b>Debt At Beginning Of Year</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_BEGINNING_OF_YEAR = eINSTANCE.getMortgageYearlyOverview_DebtAtBeginningOfYear();
    /**
     * The meta object literal for the '<em><b>Debt At End Of Year</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE_YEARLY_OVERVIEW__DEBT_AT_END_OF_YEAR = eINSTANCE.getMortgageYearlyOverview_DebtAtEndOfYear();
    /**
     * The meta object literal for the '<em><b>Interest Paid</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE_YEARLY_OVERVIEW__INTEREST_PAID = eINSTANCE.getMortgageYearlyOverview_InterestPaid();
    /**
     * The meta object literal for the '<em><b>Repayment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE_YEARLY_OVERVIEW__REPAYMENT = eINSTANCE.getMortgageYearlyOverview_Repayment();
    /**
     * The meta object literal for the '<em><b>Year</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MORTGAGE_YEARLY_OVERVIEW__YEAR = eINSTANCE.getMortgageYearlyOverview_Year();
    /**
     * The meta object literal for the '<em><b>Get Payments Total</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation MORTGAGE_YEARLY_OVERVIEW___GET_PAYMENTS_TOTAL = eINSTANCE.getMortgageYearlyOverview__GetPaymentsTotal();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.InterestCompensationMortgageYearlyOverviewImpl <em>Interest Compensation Mortgage Yearly Overview</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.InterestCompensationMortgageYearlyOverviewImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getInterestCompensationMortgageYearlyOverview()
     * @generated
     */
    EClass INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW = eINSTANCE.getInterestCompensationMortgageYearlyOverview();
    /**
     * The meta object literal for the '<em><b>Compensation Borrower</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_BORROWER = eINSTANCE.getInterestCompensationMortgageYearlyOverview_CompensationBorrower();
    /**
     * The meta object literal for the '<em><b>December Payment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__DECEMBER_PAYMENT = eINSTANCE.getInterestCompensationMortgageYearlyOverview_DecemberPayment();
    /**
     * The meta object literal for the '<em><b>Compensation Payment</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW__COMPENSATION_PAYMENT = eINSTANCE.getInterestCompensationMortgageYearlyOverview_CompensationPayment();
    /**
     * The meta object literal for the '<em><b>Get Compensation To Be Paid</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation INTEREST_COMPENSATION_MORTGAGE_YEARLY_OVERVIEW___GET_COMPENSATION_TO_BE_PAID = eINSTANCE.getInterestCompensationMortgageYearlyOverview__GetCompensationToBePaid();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.InterestRateSetImpl <em>Interest Rate Set</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.InterestRateSetImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getInterestRateSet()
     * @generated
     */
    EClass INTEREST_RATE_SET = eINSTANCE.getInterestRateSet();
    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEREST_RATE_SET__DESCRIPTION = eINSTANCE.getInterestRateSet_Description();
    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEREST_RATE_SET__NAME = eINSTANCE.getInterestRateSet_Name();
    /**
     * The meta object literal for the '<em><b>Rates</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTEREST_RATE_SET__RATES = eINSTANCE.getInterestRateSet_Rates();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.impl.RateImpl <em>Rate</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.impl.RateImpl
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getRate()
     * @generated
     */
    EClass RATE = eINSTANCE.getRate();
    /**
     * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RATE__DATE = eINSTANCE.getRate_Date();
    /**
     * The meta object literal for the '<em><b>Rate</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RATE__RATE = eINSTANCE.getRate_Rate();
    /**
     * The meta object literal for the '{@link goedegep.finan.mortgage.model.MortgageType <em>Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.finan.mortgage.model.MortgageType
     * @see goedegep.finan.mortgage.model.impl.MortgagePackageImpl#getMortgageType()
     * @generated
     */
    EEnum MORTGAGE_TYPE = eINSTANCE.getMortgageType();

  }

} //MortgagePackage
