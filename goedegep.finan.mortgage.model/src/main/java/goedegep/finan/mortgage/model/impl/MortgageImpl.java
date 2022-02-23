/**
 */
package goedegep.finan.mortgage.model.impl;

import goedegep.finan.mortgage.model.Mortgage;
import goedegep.finan.mortgage.model.MortgageEvent;
import goedegep.finan.mortgage.model.MortgagePackage;

import goedegep.finan.mortgage.model.MortgageType;
import goedegep.rolodex.model.Address;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mortgage</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getLender <em>Lender</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getLenderAddress <em>Lender Address</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getLenderSigner1 <em>Lender Signer1</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getLenderSigner2 <em>Lender Signer2</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getLenderBankAccountNumber <em>Lender Bank Account Number</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getLenderBankAccountNumberNameAndAddress <em>Lender Bank Account Number Name And Address</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getBorrowerTitleAndName <em>Borrower Title And Name</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getMortgageName <em>Mortgage Name</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getBorrowerAddress <em>Borrower Address</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getMortgageNumber <em>Mortgage Number</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getMortgageType <em>Mortgage Type</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getStartingDate <em>Starting Date</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getFirstPaymentDate <em>First Payment Date</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getDuration <em>Duration</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getPrincipal <em>Principal</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getInterestRate <em>Interest Rate</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getFixedInterestPeriod <em>Fixed Interest Period</em>}</li>
 *   <li>{@link goedegep.finan.mortgage.model.impl.MortgageImpl#getMortgageEvents <em>Mortgage Events</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MortgageImpl extends MinimalEObjectImpl.Container implements Mortgage {
  protected static final SimpleDateFormat  DF = new SimpleDateFormat("dd-MM-yyyy");

  /**
   * The default value of the '{@link #getLender() <em>Lender</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLender()
   * @generated
   * @ordered
   */
  protected static final String LENDER_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getLender() <em>Lender</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLender()
   * @generated
   * @ordered
   */
  protected String lender = LENDER_EDEFAULT;
  /**
   * This is true if the Lender attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean lenderESet;
  /**
   * The cached value of the '{@link #getLenderAddress() <em>Lender Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLenderAddress()
   * @generated
   * @ordered
   */
  protected Address lenderAddress;
  /**
   * This is true if the Lender Address reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean lenderAddressESet;
  /**
   * The default value of the '{@link #getLenderSigner1() <em>Lender Signer1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLenderSigner1()
   * @generated
   * @ordered
   */
  protected static final String LENDER_SIGNER1_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getLenderSigner1() <em>Lender Signer1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLenderSigner1()
   * @generated
   * @ordered
   */
  protected String lenderSigner1 = LENDER_SIGNER1_EDEFAULT;
  /**
   * This is true if the Lender Signer1 attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean lenderSigner1ESet;
  /**
   * The default value of the '{@link #getLenderSigner2() <em>Lender Signer2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLenderSigner2()
   * @generated
   * @ordered
   */
  protected static final String LENDER_SIGNER2_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getLenderSigner2() <em>Lender Signer2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLenderSigner2()
   * @generated
   * @ordered
   */
  protected String lenderSigner2 = LENDER_SIGNER2_EDEFAULT;
  /**
   * This is true if the Lender Signer2 attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean lenderSigner2ESet;
  /**
   * The default value of the '{@link #getLenderBankAccountNumber() <em>Lender Bank Account Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLenderBankAccountNumber()
   * @generated
   * @ordered
   */
  protected static final String LENDER_BANK_ACCOUNT_NUMBER_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getLenderBankAccountNumber() <em>Lender Bank Account Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLenderBankAccountNumber()
   * @generated
   * @ordered
   */
  protected String lenderBankAccountNumber = LENDER_BANK_ACCOUNT_NUMBER_EDEFAULT;
  /**
   * This is true if the Lender Bank Account Number attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean lenderBankAccountNumberESet;
  /**
   * The default value of the '{@link #getLenderBankAccountNumberNameAndAddress() <em>Lender Bank Account Number Name And Address</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLenderBankAccountNumberNameAndAddress()
   * @generated
   * @ordered
   */
  protected static final String LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getLenderBankAccountNumberNameAndAddress() <em>Lender Bank Account Number Name And Address</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLenderBankAccountNumberNameAndAddress()
   * @generated
   * @ordered
   */
  protected String lenderBankAccountNumberNameAndAddress = LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS_EDEFAULT;
  /**
   * This is true if the Lender Bank Account Number Name And Address attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean lenderBankAccountNumberNameAndAddressESet;
  /**
   * The default value of the '{@link #getBorrowerTitleAndName() <em>Borrower Title And Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBorrowerTitleAndName()
   * @generated
   * @ordered
   */
  protected static final String BORROWER_TITLE_AND_NAME_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getBorrowerTitleAndName() <em>Borrower Title And Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBorrowerTitleAndName()
   * @generated
   * @ordered
   */
  protected String borrowerTitleAndName = BORROWER_TITLE_AND_NAME_EDEFAULT;
  /**
   * This is true if the Borrower Title And Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean borrowerTitleAndNameESet;
  /**
   * The default value of the '{@link #getMortgageName() <em>Mortgage Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMortgageName()
   * @generated
   * @ordered
   */
  protected static final String MORTGAGE_NAME_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getMortgageName() <em>Mortgage Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMortgageName()
   * @generated
   * @ordered
   */
  protected String mortgageName = MORTGAGE_NAME_EDEFAULT;
  /**
   * This is true if the Mortgage Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean mortgageNameESet;
  /**
   * The cached value of the '{@link #getBorrowerAddress() <em>Borrower Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBorrowerAddress()
   * @generated
   * @ordered
   */
  protected Address borrowerAddress;
  /**
   * This is true if the Borrower Address reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean borrowerAddressESet;
  /**
   * The default value of the '{@link #getMortgageNumber() <em>Mortgage Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMortgageNumber()
   * @generated
   * @ordered
   */
  protected static final String MORTGAGE_NUMBER_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getMortgageNumber() <em>Mortgage Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMortgageNumber()
   * @generated
   * @ordered
   */
  protected String mortgageNumber = MORTGAGE_NUMBER_EDEFAULT;
  /**
   * This is true if the Mortgage Number attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean mortgageNumberESet;
  /**
   * The default value of the '{@link #getMortgageType() <em>Mortgage Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMortgageType()
   * @generated
   * @ordered
   */
  protected static final MortgageType MORTGAGE_TYPE_EDEFAULT = MortgageType.ANNUITY;
  /**
   * The cached value of the '{@link #getMortgageType() <em>Mortgage Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMortgageType()
   * @generated
   * @ordered
   */
  protected MortgageType mortgageType = MORTGAGE_TYPE_EDEFAULT;
  /**
   * This is true if the Mortgage Type attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean mortgageTypeESet;
  /**
   * The default value of the '{@link #getStartingDate() <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartingDate()
   * @generated
   * @ordered
   */
  protected static final Date STARTING_DATE_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getStartingDate() <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartingDate()
   * @generated
   * @ordered
   */
  protected Date startingDate = STARTING_DATE_EDEFAULT;
  /**
   * This is true if the Starting Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean startingDateESet;
  /**
   * The default value of the '{@link #getFirstPaymentDate() <em>First Payment Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFirstPaymentDate()
   * @generated
   * @ordered
   */
  protected static final Date FIRST_PAYMENT_DATE_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getFirstPaymentDate() <em>First Payment Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFirstPaymentDate()
   * @generated
   * @ordered
   */
  protected Date firstPaymentDate = FIRST_PAYMENT_DATE_EDEFAULT;
  /**
   * This is true if the First Payment Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean firstPaymentDateESet;
  /**
   * The default value of the '{@link #getDuration() <em>Duration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDuration()
   * @generated
   * @ordered
   */
  protected static final int DURATION_EDEFAULT = 0;
  /**
   * The cached value of the '{@link #getDuration() <em>Duration</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDuration()
   * @generated
   * @ordered
   */
  protected int duration = DURATION_EDEFAULT;
  /**
   * This is true if the Duration attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean durationESet;
  /**
   * The default value of the '{@link #getPrincipal() <em>Principal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPrincipal()
   * @generated
   * @ordered
   */
  protected static final PgCurrency PRINCIPAL_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getPrincipal() <em>Principal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPrincipal()
   * @generated
   * @ordered
   */
  protected PgCurrency principal = PRINCIPAL_EDEFAULT;
  /**
   * This is true if the Principal attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean principalESet;
  /**
   * The default value of the '{@link #getInterestRate() <em>Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterestRate()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue INTEREST_RATE_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getInterestRate() <em>Interest Rate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterestRate()
   * @generated
   * @ordered
   */
  protected FixedPointValue interestRate = INTEREST_RATE_EDEFAULT;
  /**
   * This is true if the Interest Rate attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean interestRateESet;
  /**
   * The default value of the '{@link #getFixedInterestPeriod() <em>Fixed Interest Period</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFixedInterestPeriod()
   * @generated
   * @ordered
   */
  protected static final int FIXED_INTEREST_PERIOD_EDEFAULT = 0;
  /**
   * The cached value of the '{@link #getFixedInterestPeriod() <em>Fixed Interest Period</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFixedInterestPeriod()
   * @generated
   * @ordered
   */
  protected int fixedInterestPeriod = FIXED_INTEREST_PERIOD_EDEFAULT;
  /**
   * This is true if the Fixed Interest Period attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean fixedInterestPeriodESet;
  /**
   * The cached value of the '{@link #getMortgageEvents() <em>Mortgage Events</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMortgageEvents()
   * @generated
   * @ordered
   */
  protected EList<MortgageEvent> mortgageEvents;
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MortgageImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return MortgagePackage.Literals.MORTGAGE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getLender() {
    return lender;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLender(String newLender) {
    String oldLender = lender;
    lender = newLender;
    boolean oldLenderESet = lenderESet;
    lenderESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__LENDER, oldLender, lender, !oldLenderESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetLender() {
    String oldLender = lender;
    boolean oldLenderESet = lenderESet;
    lender = LENDER_EDEFAULT;
    lenderESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__LENDER, oldLender, LENDER_EDEFAULT, oldLenderESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetLender() {
    return lenderESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Address getLenderAddress() {
    if (lenderAddress != null && lenderAddress.eIsProxy()) {
      InternalEObject oldLenderAddress = (InternalEObject)lenderAddress;
      lenderAddress = (Address)eResolveProxy(oldLenderAddress);
      if (lenderAddress != oldLenderAddress) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MortgagePackage.MORTGAGE__LENDER_ADDRESS, oldLenderAddress, lenderAddress));
      }
    }
    return lenderAddress;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Address basicGetLenderAddress() {
    return lenderAddress;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLenderAddress(Address newLenderAddress) {
    Address oldLenderAddress = lenderAddress;
    lenderAddress = newLenderAddress;
    boolean oldLenderAddressESet = lenderAddressESet;
    lenderAddressESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__LENDER_ADDRESS, oldLenderAddress, lenderAddress, !oldLenderAddressESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetLenderAddress() {
    Address oldLenderAddress = lenderAddress;
    boolean oldLenderAddressESet = lenderAddressESet;
    lenderAddress = null;
    lenderAddressESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__LENDER_ADDRESS, oldLenderAddress, null, oldLenderAddressESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetLenderAddress() {
    return lenderAddressESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getLenderSigner1() {
    return lenderSigner1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLenderSigner1(String newLenderSigner1) {
    String oldLenderSigner1 = lenderSigner1;
    lenderSigner1 = newLenderSigner1;
    boolean oldLenderSigner1ESet = lenderSigner1ESet;
    lenderSigner1ESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__LENDER_SIGNER1, oldLenderSigner1, lenderSigner1, !oldLenderSigner1ESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetLenderSigner1() {
    String oldLenderSigner1 = lenderSigner1;
    boolean oldLenderSigner1ESet = lenderSigner1ESet;
    lenderSigner1 = LENDER_SIGNER1_EDEFAULT;
    lenderSigner1ESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__LENDER_SIGNER1, oldLenderSigner1, LENDER_SIGNER1_EDEFAULT, oldLenderSigner1ESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetLenderSigner1() {
    return lenderSigner1ESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getLenderSigner2() {
    return lenderSigner2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLenderSigner2(String newLenderSigner2) {
    String oldLenderSigner2 = lenderSigner2;
    lenderSigner2 = newLenderSigner2;
    boolean oldLenderSigner2ESet = lenderSigner2ESet;
    lenderSigner2ESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__LENDER_SIGNER2, oldLenderSigner2, lenderSigner2, !oldLenderSigner2ESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetLenderSigner2() {
    String oldLenderSigner2 = lenderSigner2;
    boolean oldLenderSigner2ESet = lenderSigner2ESet;
    lenderSigner2 = LENDER_SIGNER2_EDEFAULT;
    lenderSigner2ESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__LENDER_SIGNER2, oldLenderSigner2, LENDER_SIGNER2_EDEFAULT, oldLenderSigner2ESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetLenderSigner2() {
    return lenderSigner2ESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getLenderBankAccountNumber() {
    return lenderBankAccountNumber;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLenderBankAccountNumber(String newLenderBankAccountNumber) {
    String oldLenderBankAccountNumber = lenderBankAccountNumber;
    lenderBankAccountNumber = newLenderBankAccountNumber;
    boolean oldLenderBankAccountNumberESet = lenderBankAccountNumberESet;
    lenderBankAccountNumberESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER, oldLenderBankAccountNumber, lenderBankAccountNumber, !oldLenderBankAccountNumberESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetLenderBankAccountNumber() {
    String oldLenderBankAccountNumber = lenderBankAccountNumber;
    boolean oldLenderBankAccountNumberESet = lenderBankAccountNumberESet;
    lenderBankAccountNumber = LENDER_BANK_ACCOUNT_NUMBER_EDEFAULT;
    lenderBankAccountNumberESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER, oldLenderBankAccountNumber, LENDER_BANK_ACCOUNT_NUMBER_EDEFAULT, oldLenderBankAccountNumberESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetLenderBankAccountNumber() {
    return lenderBankAccountNumberESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getLenderBankAccountNumberNameAndAddress() {
    return lenderBankAccountNumberNameAndAddress;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLenderBankAccountNumberNameAndAddress(String newLenderBankAccountNumberNameAndAddress) {
    String oldLenderBankAccountNumberNameAndAddress = lenderBankAccountNumberNameAndAddress;
    lenderBankAccountNumberNameAndAddress = newLenderBankAccountNumberNameAndAddress;
    boolean oldLenderBankAccountNumberNameAndAddressESet = lenderBankAccountNumberNameAndAddressESet;
    lenderBankAccountNumberNameAndAddressESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS, oldLenderBankAccountNumberNameAndAddress, lenderBankAccountNumberNameAndAddress, !oldLenderBankAccountNumberNameAndAddressESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetLenderBankAccountNumberNameAndAddress() {
    String oldLenderBankAccountNumberNameAndAddress = lenderBankAccountNumberNameAndAddress;
    boolean oldLenderBankAccountNumberNameAndAddressESet = lenderBankAccountNumberNameAndAddressESet;
    lenderBankAccountNumberNameAndAddress = LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS_EDEFAULT;
    lenderBankAccountNumberNameAndAddressESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS, oldLenderBankAccountNumberNameAndAddress, LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS_EDEFAULT, oldLenderBankAccountNumberNameAndAddressESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetLenderBankAccountNumberNameAndAddress() {
    return lenderBankAccountNumberNameAndAddressESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getBorrowerTitleAndName() {
    return borrowerTitleAndName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBorrowerTitleAndName(String newBorrowerTitleAndName) {
    String oldBorrowerTitleAndName = borrowerTitleAndName;
    borrowerTitleAndName = newBorrowerTitleAndName;
    boolean oldBorrowerTitleAndNameESet = borrowerTitleAndNameESet;
    borrowerTitleAndNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__BORROWER_TITLE_AND_NAME, oldBorrowerTitleAndName, borrowerTitleAndName, !oldBorrowerTitleAndNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetBorrowerTitleAndName() {
    String oldBorrowerTitleAndName = borrowerTitleAndName;
    boolean oldBorrowerTitleAndNameESet = borrowerTitleAndNameESet;
    borrowerTitleAndName = BORROWER_TITLE_AND_NAME_EDEFAULT;
    borrowerTitleAndNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__BORROWER_TITLE_AND_NAME, oldBorrowerTitleAndName, BORROWER_TITLE_AND_NAME_EDEFAULT, oldBorrowerTitleAndNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetBorrowerTitleAndName() {
    return borrowerTitleAndNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getMortgageName() {
    return mortgageName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setMortgageName(String newMortgageName) {
    String oldMortgageName = mortgageName;
    mortgageName = newMortgageName;
    boolean oldMortgageNameESet = mortgageNameESet;
    mortgageNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__MORTGAGE_NAME, oldMortgageName, mortgageName, !oldMortgageNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetMortgageName() {
    String oldMortgageName = mortgageName;
    boolean oldMortgageNameESet = mortgageNameESet;
    mortgageName = MORTGAGE_NAME_EDEFAULT;
    mortgageNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__MORTGAGE_NAME, oldMortgageName, MORTGAGE_NAME_EDEFAULT, oldMortgageNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetMortgageName() {
    return mortgageNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Address getBorrowerAddress() {
    if (borrowerAddress != null && borrowerAddress.eIsProxy()) {
      InternalEObject oldBorrowerAddress = (InternalEObject)borrowerAddress;
      borrowerAddress = (Address)eResolveProxy(oldBorrowerAddress);
      if (borrowerAddress != oldBorrowerAddress) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, MortgagePackage.MORTGAGE__BORROWER_ADDRESS, oldBorrowerAddress, borrowerAddress));
      }
    }
    return borrowerAddress;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Address basicGetBorrowerAddress() {
    return borrowerAddress;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBorrowerAddress(Address newBorrowerAddress) {
    Address oldBorrowerAddress = borrowerAddress;
    borrowerAddress = newBorrowerAddress;
    boolean oldBorrowerAddressESet = borrowerAddressESet;
    borrowerAddressESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__BORROWER_ADDRESS, oldBorrowerAddress, borrowerAddress, !oldBorrowerAddressESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetBorrowerAddress() {
    Address oldBorrowerAddress = borrowerAddress;
    boolean oldBorrowerAddressESet = borrowerAddressESet;
    borrowerAddress = null;
    borrowerAddressESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__BORROWER_ADDRESS, oldBorrowerAddress, null, oldBorrowerAddressESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetBorrowerAddress() {
    return borrowerAddressESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getMortgageNumber() {
    return mortgageNumber;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setMortgageNumber(String newMortgageNumber) {
    String oldMortgageNumber = mortgageNumber;
    mortgageNumber = newMortgageNumber;
    boolean oldMortgageNumberESet = mortgageNumberESet;
    mortgageNumberESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__MORTGAGE_NUMBER, oldMortgageNumber, mortgageNumber, !oldMortgageNumberESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetMortgageNumber() {
    String oldMortgageNumber = mortgageNumber;
    boolean oldMortgageNumberESet = mortgageNumberESet;
    mortgageNumber = MORTGAGE_NUMBER_EDEFAULT;
    mortgageNumberESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__MORTGAGE_NUMBER, oldMortgageNumber, MORTGAGE_NUMBER_EDEFAULT, oldMortgageNumberESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetMortgageNumber() {
    return mortgageNumberESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public MortgageType getMortgageType() {
    return mortgageType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setMortgageType(MortgageType newMortgageType) {
    MortgageType oldMortgageType = mortgageType;
    mortgageType = newMortgageType == null ? MORTGAGE_TYPE_EDEFAULT : newMortgageType;
    boolean oldMortgageTypeESet = mortgageTypeESet;
    mortgageTypeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__MORTGAGE_TYPE, oldMortgageType, mortgageType, !oldMortgageTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetMortgageType() {
    MortgageType oldMortgageType = mortgageType;
    boolean oldMortgageTypeESet = mortgageTypeESet;
    mortgageType = MORTGAGE_TYPE_EDEFAULT;
    mortgageTypeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__MORTGAGE_TYPE, oldMortgageType, MORTGAGE_TYPE_EDEFAULT, oldMortgageTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetMortgageType() {
    return mortgageTypeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Date getStartingDate() {
    return startingDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setStartingDate(Date newStartingDate) {
    Date oldStartingDate = startingDate;
    startingDate = newStartingDate;
    boolean oldStartingDateESet = startingDateESet;
    startingDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__STARTING_DATE, oldStartingDate, startingDate, !oldStartingDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetStartingDate() {
    Date oldStartingDate = startingDate;
    boolean oldStartingDateESet = startingDateESet;
    startingDate = STARTING_DATE_EDEFAULT;
    startingDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__STARTING_DATE, oldStartingDate, STARTING_DATE_EDEFAULT, oldStartingDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetStartingDate() {
    return startingDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Date getFirstPaymentDate() {
    return firstPaymentDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFirstPaymentDate(Date newFirstPaymentDate) {
    Date oldFirstPaymentDate = firstPaymentDate;
    firstPaymentDate = newFirstPaymentDate;
    boolean oldFirstPaymentDateESet = firstPaymentDateESet;
    firstPaymentDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__FIRST_PAYMENT_DATE, oldFirstPaymentDate, firstPaymentDate, !oldFirstPaymentDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFirstPaymentDate() {
    Date oldFirstPaymentDate = firstPaymentDate;
    boolean oldFirstPaymentDateESet = firstPaymentDateESet;
    firstPaymentDate = FIRST_PAYMENT_DATE_EDEFAULT;
    firstPaymentDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__FIRST_PAYMENT_DATE, oldFirstPaymentDate, FIRST_PAYMENT_DATE_EDEFAULT, oldFirstPaymentDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFirstPaymentDate() {
    return firstPaymentDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getDuration() {
    return duration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDuration(int newDuration) {
    int oldDuration = duration;
    duration = newDuration;
    boolean oldDurationESet = durationESet;
    durationESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__DURATION, oldDuration, duration, !oldDurationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDuration() {
    int oldDuration = duration;
    boolean oldDurationESet = durationESet;
    duration = DURATION_EDEFAULT;
    durationESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__DURATION, oldDuration, DURATION_EDEFAULT, oldDurationESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDuration() {
    return durationESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getPrincipal() {
    return principal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPrincipal(PgCurrency newPrincipal) {
    PgCurrency oldPrincipal = principal;
    principal = newPrincipal;
    boolean oldPrincipalESet = principalESet;
    principalESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__PRINCIPAL, oldPrincipal, principal, !oldPrincipalESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPrincipal() {
    PgCurrency oldPrincipal = principal;
    boolean oldPrincipalESet = principalESet;
    principal = PRINCIPAL_EDEFAULT;
    principalESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__PRINCIPAL, oldPrincipal, PRINCIPAL_EDEFAULT, oldPrincipalESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPrincipal() {
    return principalESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FixedPointValue getInterestRate() {
    return interestRate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInterestRate(FixedPointValue newInterestRate) {
    FixedPointValue oldInterestRate = interestRate;
    interestRate = newInterestRate;
    boolean oldInterestRateESet = interestRateESet;
    interestRateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__INTEREST_RATE, oldInterestRate, interestRate, !oldInterestRateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetInterestRate() {
    FixedPointValue oldInterestRate = interestRate;
    boolean oldInterestRateESet = interestRateESet;
    interestRate = INTEREST_RATE_EDEFAULT;
    interestRateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__INTEREST_RATE, oldInterestRate, INTEREST_RATE_EDEFAULT, oldInterestRateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetInterestRate() {
    return interestRateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getFixedInterestPeriod() {
    return fixedInterestPeriod;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFixedInterestPeriod(int newFixedInterestPeriod) {
    int oldFixedInterestPeriod = fixedInterestPeriod;
    fixedInterestPeriod = newFixedInterestPeriod;
    boolean oldFixedInterestPeriodESet = fixedInterestPeriodESet;
    fixedInterestPeriodESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, MortgagePackage.MORTGAGE__FIXED_INTEREST_PERIOD, oldFixedInterestPeriod, fixedInterestPeriod, !oldFixedInterestPeriodESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFixedInterestPeriod() {
    int oldFixedInterestPeriod = fixedInterestPeriod;
    boolean oldFixedInterestPeriodESet = fixedInterestPeriodESet;
    fixedInterestPeriod = FIXED_INTEREST_PERIOD_EDEFAULT;
    fixedInterestPeriodESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, MortgagePackage.MORTGAGE__FIXED_INTEREST_PERIOD, oldFixedInterestPeriod, FIXED_INTEREST_PERIOD_EDEFAULT, oldFixedInterestPeriodESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFixedInterestPeriod() {
    return fixedInterestPeriodESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<MortgageEvent> getMortgageEvents() {
    if (mortgageEvents == null) {
      mortgageEvents = new EObjectContainmentEList<MortgageEvent>(MortgageEvent.class, this, MortgagePackage.MORTGAGE__MORTGAGE_EVENTS);
    }
    return mortgageEvents;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String getId() {
    StringBuilder buf = new StringBuilder();
    buf.append(getLender()).append(" ");
    buf.append(getMortgageName()).append(" ");
    if (isSetMortgageNumber()) {
      buf.append(getMortgageNumber());
    } else if (isSetStartingDate()) {
      buf.append(DF.format(getStartingDate()));
    }
    
    return buf.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public void addMortgageEvent(MortgageEvent mortgageEvent) {
    getMortgageEvents().add(mortgageEvent);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public void addMortgageEvent(MortgageEvent mortgageEvent, MortgageEvent insertLocation, boolean before) {
    EList<MortgageEvent> events = getMortgageEvents();
    int insertIndex = events.indexOf(insertLocation);
    if (insertIndex == -1) {
      throw(new IllegalArgumentException());
    }

    if (before) {
      events.add(insertIndex, mortgageEvent);
    } else {
      insertIndex++;
      if (insertIndex >= events.size()) {
        events.add(mortgageEvent);
      } else {
        events.add(insertIndex, mortgageEvent);
      }
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case MortgagePackage.MORTGAGE__MORTGAGE_EVENTS:
        return ((InternalEList<?>)getMortgageEvents()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case MortgagePackage.MORTGAGE__LENDER:
        return getLender();
      case MortgagePackage.MORTGAGE__LENDER_ADDRESS:
        if (resolve) return getLenderAddress();
        return basicGetLenderAddress();
      case MortgagePackage.MORTGAGE__LENDER_SIGNER1:
        return getLenderSigner1();
      case MortgagePackage.MORTGAGE__LENDER_SIGNER2:
        return getLenderSigner2();
      case MortgagePackage.MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER:
        return getLenderBankAccountNumber();
      case MortgagePackage.MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS:
        return getLenderBankAccountNumberNameAndAddress();
      case MortgagePackage.MORTGAGE__BORROWER_TITLE_AND_NAME:
        return getBorrowerTitleAndName();
      case MortgagePackage.MORTGAGE__MORTGAGE_NAME:
        return getMortgageName();
      case MortgagePackage.MORTGAGE__BORROWER_ADDRESS:
        if (resolve) return getBorrowerAddress();
        return basicGetBorrowerAddress();
      case MortgagePackage.MORTGAGE__MORTGAGE_NUMBER:
        return getMortgageNumber();
      case MortgagePackage.MORTGAGE__MORTGAGE_TYPE:
        return getMortgageType();
      case MortgagePackage.MORTGAGE__STARTING_DATE:
        return getStartingDate();
      case MortgagePackage.MORTGAGE__FIRST_PAYMENT_DATE:
        return getFirstPaymentDate();
      case MortgagePackage.MORTGAGE__DURATION:
        return getDuration();
      case MortgagePackage.MORTGAGE__PRINCIPAL:
        return getPrincipal();
      case MortgagePackage.MORTGAGE__INTEREST_RATE:
        return getInterestRate();
      case MortgagePackage.MORTGAGE__FIXED_INTEREST_PERIOD:
        return getFixedInterestPeriod();
      case MortgagePackage.MORTGAGE__MORTGAGE_EVENTS:
        return getMortgageEvents();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case MortgagePackage.MORTGAGE__LENDER:
        setLender((String)newValue);
        return;
      case MortgagePackage.MORTGAGE__LENDER_ADDRESS:
        setLenderAddress((Address)newValue);
        return;
      case MortgagePackage.MORTGAGE__LENDER_SIGNER1:
        setLenderSigner1((String)newValue);
        return;
      case MortgagePackage.MORTGAGE__LENDER_SIGNER2:
        setLenderSigner2((String)newValue);
        return;
      case MortgagePackage.MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER:
        setLenderBankAccountNumber((String)newValue);
        return;
      case MortgagePackage.MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS:
        setLenderBankAccountNumberNameAndAddress((String)newValue);
        return;
      case MortgagePackage.MORTGAGE__BORROWER_TITLE_AND_NAME:
        setBorrowerTitleAndName((String)newValue);
        return;
      case MortgagePackage.MORTGAGE__MORTGAGE_NAME:
        setMortgageName((String)newValue);
        return;
      case MortgagePackage.MORTGAGE__BORROWER_ADDRESS:
        setBorrowerAddress((Address)newValue);
        return;
      case MortgagePackage.MORTGAGE__MORTGAGE_NUMBER:
        setMortgageNumber((String)newValue);
        return;
      case MortgagePackage.MORTGAGE__MORTGAGE_TYPE:
        setMortgageType((MortgageType)newValue);
        return;
      case MortgagePackage.MORTGAGE__STARTING_DATE:
        setStartingDate((Date)newValue);
        return;
      case MortgagePackage.MORTGAGE__FIRST_PAYMENT_DATE:
        setFirstPaymentDate((Date)newValue);
        return;
      case MortgagePackage.MORTGAGE__DURATION:
        setDuration((Integer)newValue);
        return;
      case MortgagePackage.MORTGAGE__PRINCIPAL:
        setPrincipal((PgCurrency)newValue);
        return;
      case MortgagePackage.MORTGAGE__INTEREST_RATE:
        setInterestRate((FixedPointValue)newValue);
        return;
      case MortgagePackage.MORTGAGE__FIXED_INTEREST_PERIOD:
        setFixedInterestPeriod((Integer)newValue);
        return;
      case MortgagePackage.MORTGAGE__MORTGAGE_EVENTS:
        getMortgageEvents().clear();
        getMortgageEvents().addAll((Collection<? extends MortgageEvent>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case MortgagePackage.MORTGAGE__LENDER:
        unsetLender();
        return;
      case MortgagePackage.MORTGAGE__LENDER_ADDRESS:
        unsetLenderAddress();
        return;
      case MortgagePackage.MORTGAGE__LENDER_SIGNER1:
        unsetLenderSigner1();
        return;
      case MortgagePackage.MORTGAGE__LENDER_SIGNER2:
        unsetLenderSigner2();
        return;
      case MortgagePackage.MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER:
        unsetLenderBankAccountNumber();
        return;
      case MortgagePackage.MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS:
        unsetLenderBankAccountNumberNameAndAddress();
        return;
      case MortgagePackage.MORTGAGE__BORROWER_TITLE_AND_NAME:
        unsetBorrowerTitleAndName();
        return;
      case MortgagePackage.MORTGAGE__MORTGAGE_NAME:
        unsetMortgageName();
        return;
      case MortgagePackage.MORTGAGE__BORROWER_ADDRESS:
        unsetBorrowerAddress();
        return;
      case MortgagePackage.MORTGAGE__MORTGAGE_NUMBER:
        unsetMortgageNumber();
        return;
      case MortgagePackage.MORTGAGE__MORTGAGE_TYPE:
        unsetMortgageType();
        return;
      case MortgagePackage.MORTGAGE__STARTING_DATE:
        unsetStartingDate();
        return;
      case MortgagePackage.MORTGAGE__FIRST_PAYMENT_DATE:
        unsetFirstPaymentDate();
        return;
      case MortgagePackage.MORTGAGE__DURATION:
        unsetDuration();
        return;
      case MortgagePackage.MORTGAGE__PRINCIPAL:
        unsetPrincipal();
        return;
      case MortgagePackage.MORTGAGE__INTEREST_RATE:
        unsetInterestRate();
        return;
      case MortgagePackage.MORTGAGE__FIXED_INTEREST_PERIOD:
        unsetFixedInterestPeriod();
        return;
      case MortgagePackage.MORTGAGE__MORTGAGE_EVENTS:
        getMortgageEvents().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case MortgagePackage.MORTGAGE__LENDER:
        return isSetLender();
      case MortgagePackage.MORTGAGE__LENDER_ADDRESS:
        return isSetLenderAddress();
      case MortgagePackage.MORTGAGE__LENDER_SIGNER1:
        return isSetLenderSigner1();
      case MortgagePackage.MORTGAGE__LENDER_SIGNER2:
        return isSetLenderSigner2();
      case MortgagePackage.MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER:
        return isSetLenderBankAccountNumber();
      case MortgagePackage.MORTGAGE__LENDER_BANK_ACCOUNT_NUMBER_NAME_AND_ADDRESS:
        return isSetLenderBankAccountNumberNameAndAddress();
      case MortgagePackage.MORTGAGE__BORROWER_TITLE_AND_NAME:
        return isSetBorrowerTitleAndName();
      case MortgagePackage.MORTGAGE__MORTGAGE_NAME:
        return isSetMortgageName();
      case MortgagePackage.MORTGAGE__BORROWER_ADDRESS:
        return isSetBorrowerAddress();
      case MortgagePackage.MORTGAGE__MORTGAGE_NUMBER:
        return isSetMortgageNumber();
      case MortgagePackage.MORTGAGE__MORTGAGE_TYPE:
        return isSetMortgageType();
      case MortgagePackage.MORTGAGE__STARTING_DATE:
        return isSetStartingDate();
      case MortgagePackage.MORTGAGE__FIRST_PAYMENT_DATE:
        return isSetFirstPaymentDate();
      case MortgagePackage.MORTGAGE__DURATION:
        return isSetDuration();
      case MortgagePackage.MORTGAGE__PRINCIPAL:
        return isSetPrincipal();
      case MortgagePackage.MORTGAGE__INTEREST_RATE:
        return isSetInterestRate();
      case MortgagePackage.MORTGAGE__FIXED_INTEREST_PERIOD:
        return isSetFixedInterestPeriod();
      case MortgagePackage.MORTGAGE__MORTGAGE_EVENTS:
        return mortgageEvents != null && !mortgageEvents.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
      case MortgagePackage.MORTGAGE___GET_ID:
        return getId();
      case MortgagePackage.MORTGAGE___ADD_MORTGAGE_EVENT__MORTGAGEEVENT:
        addMortgageEvent((MortgageEvent)arguments.get(0));
        return null;
      case MortgagePackage.MORTGAGE___ADD_MORTGAGE_EVENT__MORTGAGEEVENT_MORTGAGEEVENT_BOOLEAN:
        addMortgageEvent((MortgageEvent)arguments.get(0), (MortgageEvent)arguments.get(1), (Boolean)arguments.get(2));
        return null;
    }
    return super.eInvoke(operationID, arguments);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (lender: ");
    if (lenderESet) result.append(lender); else result.append("<unset>");
    result.append(", lenderSigner1: ");
    if (lenderSigner1ESet) result.append(lenderSigner1); else result.append("<unset>");
    result.append(", lenderSigner2: ");
    if (lenderSigner2ESet) result.append(lenderSigner2); else result.append("<unset>");
    result.append(", lenderBankAccountNumber: ");
    if (lenderBankAccountNumberESet) result.append(lenderBankAccountNumber); else result.append("<unset>");
    result.append(", lenderBankAccountNumberNameAndAddress: ");
    if (lenderBankAccountNumberNameAndAddressESet) result.append(lenderBankAccountNumberNameAndAddress); else result.append("<unset>");
    result.append(", borrowerTitleAndName: ");
    if (borrowerTitleAndNameESet) result.append(borrowerTitleAndName); else result.append("<unset>");
    result.append(", mortgageName: ");
    if (mortgageNameESet) result.append(mortgageName); else result.append("<unset>");
    result.append(", mortgageNumber: ");
    if (mortgageNumberESet) result.append(mortgageNumber); else result.append("<unset>");
    result.append(", mortgageType: ");
    if (mortgageTypeESet) result.append(mortgageType); else result.append("<unset>");
    result.append(", startingDate: ");
    if (startingDateESet) result.append(startingDate); else result.append("<unset>");
    result.append(", firstPaymentDate: ");
    if (firstPaymentDateESet) result.append(firstPaymentDate); else result.append("<unset>");
    result.append(", duration: ");
    if (durationESet) result.append(duration); else result.append("<unset>");
    result.append(", principal: ");
    if (principalESet) result.append(principal); else result.append("<unset>");
    result.append(", interestRate: ");
    if (interestRateESet) result.append(interestRate); else result.append("<unset>");
    result.append(", fixedInterestPeriod: ");
    if (fixedInterestPeriodESet) result.append(fixedInterestPeriod); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //MortgageImpl
