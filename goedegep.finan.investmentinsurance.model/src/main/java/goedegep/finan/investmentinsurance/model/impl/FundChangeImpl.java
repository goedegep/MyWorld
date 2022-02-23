/**
 */
package goedegep.finan.investmentinsurance.model.impl;

import goedegep.finan.investmentinsurance.model.FundChange;
import goedegep.finan.investmentinsurance.model.InvestmentFund;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage;
import goedegep.types.model.impl.EventImpl;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Fonds Wijziging</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.FundChangeImpl#getFromFund <em>From Fund</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.FundChangeImpl#getToFund <em>To Fund</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.FundChangeImpl#getFromNumberOfParticipations <em>From Number Of Participations</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.FundChangeImpl#getToNumberOfParticipations <em>To Number Of Participations</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.FundChangeImpl#getFromStockPrice <em>From Stock Price</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.FundChangeImpl#getToStockPrice <em>To Stock Price</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.FundChangeImpl#getFromAverageHistoricReturnOnInvestment <em>From Average Historic Return On Investment</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.FundChangeImpl#getFromTER <em>From TER</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.FundChangeImpl#getToAverageHistoricReturnOnInvestment <em>To Average Historic Return On Investment</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.FundChangeImpl#getToTER <em>To TER</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FundChangeImpl extends EventImpl implements FundChange {
	/**
   * The cached value of the '{@link #getFromFund() <em>From Fund</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromFund()
   * @generated
   * @ordered
   */
  protected InvestmentFund fromFund;

  /**
   * This is true if the From Fund reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean fromFundESet;

  /**
   * The cached value of the '{@link #getToFund() <em>To Fund</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToFund()
   * @generated
   * @ordered
   */
  protected InvestmentFund toFund;

  /**
   * This is true if the To Fund reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean toFundESet;

  /**
   * The default value of the '{@link #getFromNumberOfParticipations() <em>From Number Of Participations</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromNumberOfParticipations()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue FROM_NUMBER_OF_PARTICIPATIONS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFromNumberOfParticipations() <em>From Number Of Participations</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromNumberOfParticipations()
   * @generated
   * @ordered
   */
  protected FixedPointValue fromNumberOfParticipations = FROM_NUMBER_OF_PARTICIPATIONS_EDEFAULT;

  /**
   * This is true if the From Number Of Participations attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean fromNumberOfParticipationsESet;

  /**
   * The default value of the '{@link #getToNumberOfParticipations() <em>To Number Of Participations</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToNumberOfParticipations()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue TO_NUMBER_OF_PARTICIPATIONS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getToNumberOfParticipations() <em>To Number Of Participations</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToNumberOfParticipations()
   * @generated
   * @ordered
   */
  protected FixedPointValue toNumberOfParticipations = TO_NUMBER_OF_PARTICIPATIONS_EDEFAULT;

  /**
   * This is true if the To Number Of Participations attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean toNumberOfParticipationsESet;

  /**
   * The default value of the '{@link #getFromStockPrice() <em>From Stock Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromStockPrice()
   * @generated
   * @ordered
   */
  protected static final PgCurrency FROM_STOCK_PRICE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFromStockPrice() <em>From Stock Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromStockPrice()
   * @generated
   * @ordered
   */
  protected PgCurrency fromStockPrice = FROM_STOCK_PRICE_EDEFAULT;

  /**
   * This is true if the From Stock Price attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean fromStockPriceESet;

  /**
   * The default value of the '{@link #getToStockPrice() <em>To Stock Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToStockPrice()
   * @generated
   * @ordered
   */
  protected static final PgCurrency TO_STOCK_PRICE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getToStockPrice() <em>To Stock Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToStockPrice()
   * @generated
   * @ordered
   */
  protected PgCurrency toStockPrice = TO_STOCK_PRICE_EDEFAULT;

  /**
   * This is true if the To Stock Price attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean toStockPriceESet;

  /**
   * The default value of the '{@link #getFromAverageHistoricReturnOnInvestment() <em>From Average Historic Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromAverageHistoricReturnOnInvestment()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFromAverageHistoricReturnOnInvestment() <em>From Average Historic Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromAverageHistoricReturnOnInvestment()
   * @generated
   * @ordered
   */
  protected FixedPointValue fromAverageHistoricReturnOnInvestment = FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT_EDEFAULT;

  /**
   * This is true if the From Average Historic Return On Investment attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean fromAverageHistoricReturnOnInvestmentESet;

  /**
   * The default value of the '{@link #getFromTER() <em>From TER</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromTER()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue FROM_TER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFromTER() <em>From TER</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromTER()
   * @generated
   * @ordered
   */
  protected FixedPointValue fromTER = FROM_TER_EDEFAULT;

  /**
   * This is true if the From TER attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean fromTERESet;

  /**
   * The default value of the '{@link #getToAverageHistoricReturnOnInvestment() <em>To Average Historic Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToAverageHistoricReturnOnInvestment()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getToAverageHistoricReturnOnInvestment() <em>To Average Historic Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToAverageHistoricReturnOnInvestment()
   * @generated
   * @ordered
   */
  protected FixedPointValue toAverageHistoricReturnOnInvestment = TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT_EDEFAULT;

  /**
   * This is true if the To Average Historic Return On Investment attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean toAverageHistoricReturnOnInvestmentESet;

  /**
   * The default value of the '{@link #getToTER() <em>To TER</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToTER()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue TO_TER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getToTER() <em>To TER</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToTER()
   * @generated
   * @ordered
   */
  protected FixedPointValue toTER = TO_TER_EDEFAULT;

  /**
   * This is true if the To TER attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean toTERESet;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected FundChangeImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EClass eStaticClass() {
    return InvestmentInsurancePackage.Literals.FUND_CHANGE;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public InvestmentFund getFromFund() {
    if (fromFund != null && fromFund.eIsProxy()) {
      InternalEObject oldFromFund = (InternalEObject)fromFund;
      fromFund = (InvestmentFund)eResolveProxy(oldFromFund);
      if (fromFund != oldFromFund) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, InvestmentInsurancePackage.FUND_CHANGE__FROM_FUND, oldFromFund, fromFund));
      }
    }
    return fromFund;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public InvestmentFund basicGetFromFund() {
    return fromFund;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setFromFund(InvestmentFund newFromFund) {
    InvestmentFund oldFromFund = fromFund;
    fromFund = newFromFund;
    boolean oldFromFundESet = fromFundESet;
    fromFundESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.FUND_CHANGE__FROM_FUND, oldFromFund, fromFund, !oldFromFundESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFromFund() {
    InvestmentFund oldFromFund = fromFund;
    boolean oldFromFundESet = fromFundESet;
    fromFund = null;
    fromFundESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.FUND_CHANGE__FROM_FUND, oldFromFund, null, oldFromFundESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetFromFund() {
    return fromFundESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public InvestmentFund getToFund() {
    if (toFund != null && toFund.eIsProxy()) {
      InternalEObject oldToFund = (InternalEObject)toFund;
      toFund = (InvestmentFund)eResolveProxy(oldToFund);
      if (toFund != oldToFund) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, InvestmentInsurancePackage.FUND_CHANGE__TO_FUND, oldToFund, toFund));
      }
    }
    return toFund;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public InvestmentFund basicGetToFund() {
    return toFund;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setToFund(InvestmentFund newToFund) {
    InvestmentFund oldToFund = toFund;
    toFund = newToFund;
    boolean oldToFundESet = toFundESet;
    toFundESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.FUND_CHANGE__TO_FUND, oldToFund, toFund, !oldToFundESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetToFund() {
    InvestmentFund oldToFund = toFund;
    boolean oldToFundESet = toFundESet;
    toFund = null;
    toFundESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.FUND_CHANGE__TO_FUND, oldToFund, null, oldToFundESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetToFund() {
    return toFundESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getFromNumberOfParticipations() {
    return fromNumberOfParticipations;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setFromNumberOfParticipations(FixedPointValue newFromNumberOfParticipations) {
    FixedPointValue oldFromNumberOfParticipations = fromNumberOfParticipations;
    fromNumberOfParticipations = newFromNumberOfParticipations;
    boolean oldFromNumberOfParticipationsESet = fromNumberOfParticipationsESet;
    fromNumberOfParticipationsESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.FUND_CHANGE__FROM_NUMBER_OF_PARTICIPATIONS, oldFromNumberOfParticipations, fromNumberOfParticipations, !oldFromNumberOfParticipationsESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFromNumberOfParticipations() {
    FixedPointValue oldFromNumberOfParticipations = fromNumberOfParticipations;
    boolean oldFromNumberOfParticipationsESet = fromNumberOfParticipationsESet;
    fromNumberOfParticipations = FROM_NUMBER_OF_PARTICIPATIONS_EDEFAULT;
    fromNumberOfParticipationsESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.FUND_CHANGE__FROM_NUMBER_OF_PARTICIPATIONS, oldFromNumberOfParticipations, FROM_NUMBER_OF_PARTICIPATIONS_EDEFAULT, oldFromNumberOfParticipationsESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetFromNumberOfParticipations() {
    return fromNumberOfParticipationsESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getToNumberOfParticipations() {
    return toNumberOfParticipations;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setToNumberOfParticipations(FixedPointValue newToNumberOfParticipations) {
    FixedPointValue oldToNumberOfParticipations = toNumberOfParticipations;
    toNumberOfParticipations = newToNumberOfParticipations;
    boolean oldToNumberOfParticipationsESet = toNumberOfParticipationsESet;
    toNumberOfParticipationsESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.FUND_CHANGE__TO_NUMBER_OF_PARTICIPATIONS, oldToNumberOfParticipations, toNumberOfParticipations, !oldToNumberOfParticipationsESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetToNumberOfParticipations() {
    FixedPointValue oldToNumberOfParticipations = toNumberOfParticipations;
    boolean oldToNumberOfParticipationsESet = toNumberOfParticipationsESet;
    toNumberOfParticipations = TO_NUMBER_OF_PARTICIPATIONS_EDEFAULT;
    toNumberOfParticipationsESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.FUND_CHANGE__TO_NUMBER_OF_PARTICIPATIONS, oldToNumberOfParticipations, TO_NUMBER_OF_PARTICIPATIONS_EDEFAULT, oldToNumberOfParticipationsESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetToNumberOfParticipations() {
    return toNumberOfParticipationsESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getFromStockPrice() {
    return fromStockPrice;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setFromStockPrice(PgCurrency newFromStockPrice) {
    PgCurrency oldFromStockPrice = fromStockPrice;
    fromStockPrice = newFromStockPrice;
    boolean oldFromStockPriceESet = fromStockPriceESet;
    fromStockPriceESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.FUND_CHANGE__FROM_STOCK_PRICE, oldFromStockPrice, fromStockPrice, !oldFromStockPriceESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFromStockPrice() {
    PgCurrency oldFromStockPrice = fromStockPrice;
    boolean oldFromStockPriceESet = fromStockPriceESet;
    fromStockPrice = FROM_STOCK_PRICE_EDEFAULT;
    fromStockPriceESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.FUND_CHANGE__FROM_STOCK_PRICE, oldFromStockPrice, FROM_STOCK_PRICE_EDEFAULT, oldFromStockPriceESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetFromStockPrice() {
    return fromStockPriceESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getToStockPrice() {
    return toStockPrice;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setToStockPrice(PgCurrency newToStockPrice) {
    PgCurrency oldToStockPrice = toStockPrice;
    toStockPrice = newToStockPrice;
    boolean oldToStockPriceESet = toStockPriceESet;
    toStockPriceESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.FUND_CHANGE__TO_STOCK_PRICE, oldToStockPrice, toStockPrice, !oldToStockPriceESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetToStockPrice() {
    PgCurrency oldToStockPrice = toStockPrice;
    boolean oldToStockPriceESet = toStockPriceESet;
    toStockPrice = TO_STOCK_PRICE_EDEFAULT;
    toStockPriceESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.FUND_CHANGE__TO_STOCK_PRICE, oldToStockPrice, TO_STOCK_PRICE_EDEFAULT, oldToStockPriceESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetToStockPrice() {
    return toStockPriceESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getFromAverageHistoricReturnOnInvestment() {
    return fromAverageHistoricReturnOnInvestment;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setFromAverageHistoricReturnOnInvestment(FixedPointValue newFromAverageHistoricReturnOnInvestment) {
    FixedPointValue oldFromAverageHistoricReturnOnInvestment = fromAverageHistoricReturnOnInvestment;
    fromAverageHistoricReturnOnInvestment = newFromAverageHistoricReturnOnInvestment;
    boolean oldFromAverageHistoricReturnOnInvestmentESet = fromAverageHistoricReturnOnInvestmentESet;
    fromAverageHistoricReturnOnInvestmentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.FUND_CHANGE__FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT, oldFromAverageHistoricReturnOnInvestment, fromAverageHistoricReturnOnInvestment, !oldFromAverageHistoricReturnOnInvestmentESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFromAverageHistoricReturnOnInvestment() {
    FixedPointValue oldFromAverageHistoricReturnOnInvestment = fromAverageHistoricReturnOnInvestment;
    boolean oldFromAverageHistoricReturnOnInvestmentESet = fromAverageHistoricReturnOnInvestmentESet;
    fromAverageHistoricReturnOnInvestment = FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT_EDEFAULT;
    fromAverageHistoricReturnOnInvestmentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.FUND_CHANGE__FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT, oldFromAverageHistoricReturnOnInvestment, FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT_EDEFAULT, oldFromAverageHistoricReturnOnInvestmentESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetFromAverageHistoricReturnOnInvestment() {
    return fromAverageHistoricReturnOnInvestmentESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getFromTER() {
    return fromTER;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setFromTER(FixedPointValue newFromTER) {
    FixedPointValue oldFromTER = fromTER;
    fromTER = newFromTER;
    boolean oldFromTERESet = fromTERESet;
    fromTERESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.FUND_CHANGE__FROM_TER, oldFromTER, fromTER, !oldFromTERESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFromTER() {
    FixedPointValue oldFromTER = fromTER;
    boolean oldFromTERESet = fromTERESet;
    fromTER = FROM_TER_EDEFAULT;
    fromTERESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.FUND_CHANGE__FROM_TER, oldFromTER, FROM_TER_EDEFAULT, oldFromTERESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetFromTER() {
    return fromTERESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getToAverageHistoricReturnOnInvestment() {
    return toAverageHistoricReturnOnInvestment;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setToAverageHistoricReturnOnInvestment(FixedPointValue newToAverageHistoricReturnOnInvestment) {
    FixedPointValue oldToAverageHistoricReturnOnInvestment = toAverageHistoricReturnOnInvestment;
    toAverageHistoricReturnOnInvestment = newToAverageHistoricReturnOnInvestment;
    boolean oldToAverageHistoricReturnOnInvestmentESet = toAverageHistoricReturnOnInvestmentESet;
    toAverageHistoricReturnOnInvestmentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.FUND_CHANGE__TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT, oldToAverageHistoricReturnOnInvestment, toAverageHistoricReturnOnInvestment, !oldToAverageHistoricReturnOnInvestmentESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetToAverageHistoricReturnOnInvestment() {
    FixedPointValue oldToAverageHistoricReturnOnInvestment = toAverageHistoricReturnOnInvestment;
    boolean oldToAverageHistoricReturnOnInvestmentESet = toAverageHistoricReturnOnInvestmentESet;
    toAverageHistoricReturnOnInvestment = TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT_EDEFAULT;
    toAverageHistoricReturnOnInvestmentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.FUND_CHANGE__TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT, oldToAverageHistoricReturnOnInvestment, TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT_EDEFAULT, oldToAverageHistoricReturnOnInvestmentESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetToAverageHistoricReturnOnInvestment() {
    return toAverageHistoricReturnOnInvestmentESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getToTER() {
    return toTER;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setToTER(FixedPointValue newToTER) {
    FixedPointValue oldToTER = toTER;
    toTER = newToTER;
    boolean oldToTERESet = toTERESet;
    toTERESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.FUND_CHANGE__TO_TER, oldToTER, toTER, !oldToTERESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetToTER() {
    FixedPointValue oldToTER = toTER;
    boolean oldToTERESet = toTERESet;
    toTER = TO_TER_EDEFAULT;
    toTERESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.FUND_CHANGE__TO_TER, oldToTER, TO_TER_EDEFAULT, oldToTERESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetToTER() {
    return toTERESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_FUND:
        if (resolve) return getFromFund();
        return basicGetFromFund();
      case InvestmentInsurancePackage.FUND_CHANGE__TO_FUND:
        if (resolve) return getToFund();
        return basicGetToFund();
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_NUMBER_OF_PARTICIPATIONS:
        return getFromNumberOfParticipations();
      case InvestmentInsurancePackage.FUND_CHANGE__TO_NUMBER_OF_PARTICIPATIONS:
        return getToNumberOfParticipations();
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_STOCK_PRICE:
        return getFromStockPrice();
      case InvestmentInsurancePackage.FUND_CHANGE__TO_STOCK_PRICE:
        return getToStockPrice();
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT:
        return getFromAverageHistoricReturnOnInvestment();
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_TER:
        return getFromTER();
      case InvestmentInsurancePackage.FUND_CHANGE__TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT:
        return getToAverageHistoricReturnOnInvestment();
      case InvestmentInsurancePackage.FUND_CHANGE__TO_TER:
        return getToTER();
    }
    return super.eGet(featureID, resolve, coreType);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_FUND:
        setFromFund((InvestmentFund)newValue);
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__TO_FUND:
        setToFund((InvestmentFund)newValue);
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_NUMBER_OF_PARTICIPATIONS:
        setFromNumberOfParticipations((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__TO_NUMBER_OF_PARTICIPATIONS:
        setToNumberOfParticipations((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_STOCK_PRICE:
        setFromStockPrice((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__TO_STOCK_PRICE:
        setToStockPrice((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT:
        setFromAverageHistoricReturnOnInvestment((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_TER:
        setFromTER((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT:
        setToAverageHistoricReturnOnInvestment((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__TO_TER:
        setToTER((FixedPointValue)newValue);
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
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_FUND:
        unsetFromFund();
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__TO_FUND:
        unsetToFund();
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_NUMBER_OF_PARTICIPATIONS:
        unsetFromNumberOfParticipations();
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__TO_NUMBER_OF_PARTICIPATIONS:
        unsetToNumberOfParticipations();
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_STOCK_PRICE:
        unsetFromStockPrice();
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__TO_STOCK_PRICE:
        unsetToStockPrice();
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT:
        unsetFromAverageHistoricReturnOnInvestment();
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_TER:
        unsetFromTER();
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT:
        unsetToAverageHistoricReturnOnInvestment();
        return;
      case InvestmentInsurancePackage.FUND_CHANGE__TO_TER:
        unsetToTER();
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
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_FUND:
        return isSetFromFund();
      case InvestmentInsurancePackage.FUND_CHANGE__TO_FUND:
        return isSetToFund();
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_NUMBER_OF_PARTICIPATIONS:
        return isSetFromNumberOfParticipations();
      case InvestmentInsurancePackage.FUND_CHANGE__TO_NUMBER_OF_PARTICIPATIONS:
        return isSetToNumberOfParticipations();
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_STOCK_PRICE:
        return isSetFromStockPrice();
      case InvestmentInsurancePackage.FUND_CHANGE__TO_STOCK_PRICE:
        return isSetToStockPrice();
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT:
        return isSetFromAverageHistoricReturnOnInvestment();
      case InvestmentInsurancePackage.FUND_CHANGE__FROM_TER:
        return isSetFromTER();
      case InvestmentInsurancePackage.FUND_CHANGE__TO_AVERAGE_HISTORIC_RETURN_ON_INVESTMENT:
        return isSetToAverageHistoricReturnOnInvestment();
      case InvestmentInsurancePackage.FUND_CHANGE__TO_TER:
        return isSetToTER();
    }
    return super.eIsSet(featureID);
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
    result.append(" (fromNumberOfParticipations: ");
    if (fromNumberOfParticipationsESet) result.append(fromNumberOfParticipations); else result.append("<unset>");
    result.append(", toNumberOfParticipations: ");
    if (toNumberOfParticipationsESet) result.append(toNumberOfParticipations); else result.append("<unset>");
    result.append(", fromStockPrice: ");
    if (fromStockPriceESet) result.append(fromStockPrice); else result.append("<unset>");
    result.append(", toStockPrice: ");
    if (toStockPriceESet) result.append(toStockPrice); else result.append("<unset>");
    result.append(", fromAverageHistoricReturnOnInvestment: ");
    if (fromAverageHistoricReturnOnInvestmentESet) result.append(fromAverageHistoricReturnOnInvestment); else result.append("<unset>");
    result.append(", fromTER: ");
    if (fromTERESet) result.append(fromTER); else result.append("<unset>");
    result.append(", toAverageHistoricReturnOnInvestment: ");
    if (toAverageHistoricReturnOnInvestmentESet) result.append(toAverageHistoricReturnOnInvestment); else result.append("<unset>");
    result.append(", toTER: ");
    if (toTERESet) result.append(toTER); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //FondsWijzigingImpl
