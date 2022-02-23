/**
 */
package goedegep.finan.investmentinsurance.model.impl;

import goedegep.finan.investmentinsurance.model.AnnualStatement;
import goedegep.finan.investmentinsurance.model.InvestmentFund;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage;
import goedegep.finan.investmentinsurance.model.Participation;
import goedegep.types.model.impl.EventImpl;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Jaar Opgave</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getPeriodFrom <em>Period From</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getPeriodUntil <em>Period Until</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getPremiumPaid <em>Premium Paid</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getPremiumDeathRiskCoverage <em>Premium Death Risk Coverage</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getPremiumOccupationalDisabilityRiskCoverage <em>Premium Occupational Disability Risk Coverage</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getUpkeepPremium <em>Upkeep Premium</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getFirstCostsInsuranceCompany <em>First Costs Insurance Company</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getContinuingCostsInsuranceCompany <em>Continuing Costs Insurance Company</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getManagementCosts <em>Management Costs</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getBuyAndSellCosts <em>Buy And Sell Costs</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getMutationCosts <em>Mutation Costs</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getCostsRestitution <em>Costs Restitution</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getCorrections <em>Corrections</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getEarnedOnTheParticipations <em>Earned On The Participations</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getParticipations <em>Participations</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getExampleCapitalOnEndDate <em>Example Capital On End Date</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.AnnualStatementImpl#getExpectedYearlyCostsIncrease <em>Expected Yearly Costs Increase</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnnualStatementImpl extends EventImpl implements AnnualStatement {
	/**
   * The default value of the '{@link #getPeriodFrom() <em>Period From</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPeriodFrom()
   * @generated
   * @ordered
   */
  protected static final LocalDate PERIOD_FROM_EDEFAULT = null;

  private static final Logger LOGGER = Logger.getLogger(AnnualStatementImpl.class.getName());

	/**
   * The cached value of the '{@link #getPeriodFrom() <em>Period From</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getPeriodFrom()
   * @generated
   * @ordered
   */
	protected LocalDate periodFrom = PERIOD_FROM_EDEFAULT;

	/**
   * This is true if the Period From attribute has been set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	protected boolean periodFromESet;

	/**
   * The default value of the '{@link #getPeriodUntil() <em>Period Until</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPeriodUntil()
   * @generated
   * @ordered
   */
  protected static final LocalDate PERIOD_UNTIL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPeriodUntil() <em>Period Until</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPeriodUntil()
   * @generated
   * @ordered
   */
  protected LocalDate periodUntil = PERIOD_UNTIL_EDEFAULT;

  /**
   * This is true if the Period Until attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean periodUntilESet;

  /**
   * The default value of the '{@link #getPremiumPaid() <em>Premium Paid</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPremiumPaid()
   * @generated
   * @ordered
   */
  protected static final PgCurrency PREMIUM_PAID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPremiumPaid() <em>Premium Paid</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPremiumPaid()
   * @generated
   * @ordered
   */
  protected PgCurrency premiumPaid = PREMIUM_PAID_EDEFAULT;

  /**
   * This is true if the Premium Paid attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean premiumPaidESet;

  /**
   * The default value of the '{@link #getPremiumDeathRiskCoverage() <em>Premium Death Risk Coverage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPremiumDeathRiskCoverage()
   * @generated
   * @ordered
   */
  protected static final PgCurrency PREMIUM_DEATH_RISK_COVERAGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPremiumDeathRiskCoverage() <em>Premium Death Risk Coverage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPremiumDeathRiskCoverage()
   * @generated
   * @ordered
   */
  protected PgCurrency premiumDeathRiskCoverage = PREMIUM_DEATH_RISK_COVERAGE_EDEFAULT;

  /**
   * This is true if the Premium Death Risk Coverage attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean premiumDeathRiskCoverageESet;

  /**
   * The default value of the '{@link #getPremiumOccupationalDisabilityRiskCoverage() <em>Premium Occupational Disability Risk Coverage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPremiumOccupationalDisabilityRiskCoverage()
   * @generated
   * @ordered
   */
  protected static final PgCurrency PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPremiumOccupationalDisabilityRiskCoverage() <em>Premium Occupational Disability Risk Coverage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPremiumOccupationalDisabilityRiskCoverage()
   * @generated
   * @ordered
   */
  protected PgCurrency premiumOccupationalDisabilityRiskCoverage = PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE_EDEFAULT;

  /**
   * This is true if the Premium Occupational Disability Risk Coverage attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean premiumOccupationalDisabilityRiskCoverageESet;

  /**
   * The default value of the '{@link #getUpkeepPremium() <em>Upkeep Premium</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUpkeepPremium()
   * @generated
   * @ordered
   */
  protected static final PgCurrency UPKEEP_PREMIUM_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUpkeepPremium() <em>Upkeep Premium</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getUpkeepPremium()
   * @generated
   * @ordered
   */
	protected PgCurrency upkeepPremium = UPKEEP_PREMIUM_EDEFAULT;

	/**
   * This is true if the Upkeep Premium attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean upkeepPremiumESet;

  /**
   * The default value of the '{@link #getFirstCostsInsuranceCompany() <em>First Costs Insurance Company</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFirstCostsInsuranceCompany()
   * @generated
   * @ordered
   */
  protected static final PgCurrency FIRST_COSTS_INSURANCE_COMPANY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFirstCostsInsuranceCompany() <em>First Costs Insurance Company</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getFirstCostsInsuranceCompany()
   * @generated
   * @ordered
   */
	protected PgCurrency firstCostsInsuranceCompany = FIRST_COSTS_INSURANCE_COMPANY_EDEFAULT;

	/**
   * This is true if the First Costs Insurance Company attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean firstCostsInsuranceCompanyESet;

  /**
   * The default value of the '{@link #getContinuingCostsInsuranceCompany() <em>Continuing Costs Insurance Company</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getContinuingCostsInsuranceCompany()
   * @generated
   * @ordered
   */
  protected static final PgCurrency CONTINUING_COSTS_INSURANCE_COMPANY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getContinuingCostsInsuranceCompany() <em>Continuing Costs Insurance Company</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getContinuingCostsInsuranceCompany()
   * @generated
   * @ordered
   */
	protected PgCurrency continuingCostsInsuranceCompany = CONTINUING_COSTS_INSURANCE_COMPANY_EDEFAULT;

	/**
   * This is true if the Continuing Costs Insurance Company attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean continuingCostsInsuranceCompanyESet;

  /**
   * The default value of the '{@link #getManagementCosts() <em>Management Costs</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getManagementCosts()
   * @generated
   * @ordered
   */
  protected static final PgCurrency MANAGEMENT_COSTS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getManagementCosts() <em>Management Costs</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getManagementCosts()
   * @generated
   * @ordered
   */
	protected PgCurrency managementCosts = MANAGEMENT_COSTS_EDEFAULT;

	/**
   * This is true if the Management Costs attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean managementCostsESet;

  /**
   * The default value of the '{@link #getBuyAndSellCosts() <em>Buy And Sell Costs</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBuyAndSellCosts()
   * @generated
   * @ordered
   */
  protected static final PgCurrency BUY_AND_SELL_COSTS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBuyAndSellCosts() <em>Buy And Sell Costs</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getBuyAndSellCosts()
   * @generated
   * @ordered
   */
	protected PgCurrency buyAndSellCosts = BUY_AND_SELL_COSTS_EDEFAULT;

	/**
   * This is true if the Buy And Sell Costs attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean buyAndSellCostsESet;

  /**
   * The default value of the '{@link #getMutationCosts() <em>Mutation Costs</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMutationCosts()
   * @generated
   * @ordered
   */
  protected static final PgCurrency MUTATION_COSTS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMutationCosts() <em>Mutation Costs</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getMutationCosts()
   * @generated
   * @ordered
   */
	protected PgCurrency mutationCosts = MUTATION_COSTS_EDEFAULT;

	/**
   * This is true if the Mutation Costs attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean mutationCostsESet;

  /**
   * The default value of the '{@link #getCostsRestitution() <em>Costs Restitution</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCostsRestitution()
   * @generated
   * @ordered
   */
  protected static final PgCurrency COSTS_RESTITUTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCostsRestitution() <em>Costs Restitution</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCostsRestitution()
   * @generated
   * @ordered
   */
	protected PgCurrency costsRestitution = COSTS_RESTITUTION_EDEFAULT;

	/**
   * This is true if the Costs Restitution attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean costsRestitutionESet;

  /**
   * The default value of the '{@link #getCorrections() <em>Corrections</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCorrections()
   * @generated
   * @ordered
   */
  protected static final PgCurrency CORRECTIONS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCorrections() <em>Corrections</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCorrections()
   * @generated
   * @ordered
   */
	protected PgCurrency corrections = CORRECTIONS_EDEFAULT;

	/**
   * This is true if the Corrections attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean correctionsESet;

  /**
   * The default value of the '{@link #getEarnedOnTheParticipations() <em>Earned On The Participations</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEarnedOnTheParticipations()
   * @generated
   * @ordered
   */
  protected static final PgCurrency EARNED_ON_THE_PARTICIPATIONS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEarnedOnTheParticipations() <em>Earned On The Participations</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getEarnedOnTheParticipations()
   * @generated
   * @ordered
   */
	protected PgCurrency earnedOnTheParticipations = EARNED_ON_THE_PARTICIPATIONS_EDEFAULT;

	/**
   * This is true if the Earned On The Participations attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean earnedOnTheParticipationsESet;

  /**
   * The cached value of the '{@link #getParticipations() <em>Participations</em>}' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getParticipations()
   * @generated
   * @ordered
   */
	protected EList<Participation> participations;

	/**
   * The default value of the '{@link #getExampleCapitalOnEndDate() <em>Example Capital On End Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalOnEndDate()
   * @generated
   * @ordered
   */
  protected static final LocalDate EXAMPLE_CAPITAL_ON_END_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExampleCapitalOnEndDate() <em>Example Capital On End Date</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getExampleCapitalOnEndDate()
   * @generated
   * @ordered
   */
	protected LocalDate exampleCapitalOnEndDate = EXAMPLE_CAPITAL_ON_END_DATE_EDEFAULT;

	/**
   * This is true if the Example Capital On End Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean exampleCapitalOnEndDateESet;

  /**
   * The default value of the '{@link #getExpectedYearlyCostsIncrease() <em>Expected Yearly Costs Increase</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpectedYearlyCostsIncrease()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue EXPECTED_YEARLY_COSTS_INCREASE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExpectedYearlyCostsIncrease() <em>Expected Yearly Costs Increase</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getExpectedYearlyCostsIncrease()
   * @generated
   * @ordered
   */
	protected FixedPointValue expectedYearlyCostsIncrease = EXPECTED_YEARLY_COSTS_INCREASE_EDEFAULT;

	/**
   * This is true if the Expected Yearly Costs Increase attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean expectedYearlyCostsIncreaseESet;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected AnnualStatementImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EClass eStaticClass() {
    return InvestmentInsurancePackage.Literals.ANNUAL_STATEMENT;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public LocalDate getPeriodFrom() {
    return periodFrom;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPeriodFrom(LocalDate newPeriodFrom) {
    LocalDate oldPeriodFrom = periodFrom;
    periodFrom = newPeriodFrom;
    boolean oldPeriodFromESet = periodFromESet;
    periodFromESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__PERIOD_FROM, oldPeriodFrom, periodFrom, !oldPeriodFromESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void unsetPeriodFrom() {
    LocalDate oldPeriodFrom = periodFrom;
    boolean oldPeriodFromESet = periodFromESet;
    periodFrom = PERIOD_FROM_EDEFAULT;
    periodFromESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__PERIOD_FROM, oldPeriodFrom, PERIOD_FROM_EDEFAULT, oldPeriodFromESet));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetPeriodFrom() {
    return periodFromESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public LocalDate getPeriodUntil() {
    return periodUntil;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPeriodUntil(LocalDate newPeriodUntil) {
    LocalDate oldPeriodUntil = periodUntil;
    periodUntil = newPeriodUntil;
    boolean oldPeriodUntilESet = periodUntilESet;
    periodUntilESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__PERIOD_UNTIL, oldPeriodUntil, periodUntil, !oldPeriodUntilESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void unsetPeriodUntil() {
    LocalDate oldPeriodUntil = periodUntil;
    boolean oldPeriodUntilESet = periodUntilESet;
    periodUntil = PERIOD_UNTIL_EDEFAULT;
    periodUntilESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__PERIOD_UNTIL, oldPeriodUntil, PERIOD_UNTIL_EDEFAULT, oldPeriodUntilESet));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetPeriodUntil() {
    return periodUntilESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getPremiumPaid() {
    return premiumPaid;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPremiumPaid(PgCurrency newPremiumPaid) {
    PgCurrency oldPremiumPaid = premiumPaid;
    premiumPaid = newPremiumPaid;
    boolean oldPremiumPaidESet = premiumPaidESet;
    premiumPaidESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_PAID, oldPremiumPaid, premiumPaid, !oldPremiumPaidESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPremiumPaid() {
    PgCurrency oldPremiumPaid = premiumPaid;
    boolean oldPremiumPaidESet = premiumPaidESet;
    premiumPaid = PREMIUM_PAID_EDEFAULT;
    premiumPaidESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_PAID, oldPremiumPaid, PREMIUM_PAID_EDEFAULT, oldPremiumPaidESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPremiumPaid() {
    return premiumPaidESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PgCurrency getPremiumDeathRiskCoverage() {
    return premiumDeathRiskCoverage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPremiumDeathRiskCoverage(PgCurrency newPremiumDeathRiskCoverage) {
    PgCurrency oldPremiumDeathRiskCoverage = premiumDeathRiskCoverage;
    premiumDeathRiskCoverage = newPremiumDeathRiskCoverage;
    boolean oldPremiumDeathRiskCoverageESet = premiumDeathRiskCoverageESet;
    premiumDeathRiskCoverageESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_DEATH_RISK_COVERAGE, oldPremiumDeathRiskCoverage, premiumDeathRiskCoverage, !oldPremiumDeathRiskCoverageESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPremiumDeathRiskCoverage() {
    PgCurrency oldPremiumDeathRiskCoverage = premiumDeathRiskCoverage;
    boolean oldPremiumDeathRiskCoverageESet = premiumDeathRiskCoverageESet;
    premiumDeathRiskCoverage = PREMIUM_DEATH_RISK_COVERAGE_EDEFAULT;
    premiumDeathRiskCoverageESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_DEATH_RISK_COVERAGE, oldPremiumDeathRiskCoverage, PREMIUM_DEATH_RISK_COVERAGE_EDEFAULT, oldPremiumDeathRiskCoverageESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPremiumDeathRiskCoverage() {
    return premiumDeathRiskCoverageESet;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getPremiumOccupationalDisabilityRiskCoverage() {
    return premiumOccupationalDisabilityRiskCoverage;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setPremiumOccupationalDisabilityRiskCoverage(
			PgCurrency newPremiumOccupationalDisabilityRiskCoverage) {
    PgCurrency oldPremiumOccupationalDisabilityRiskCoverage = premiumOccupationalDisabilityRiskCoverage;
    premiumOccupationalDisabilityRiskCoverage = newPremiumOccupationalDisabilityRiskCoverage;
    boolean oldPremiumOccupationalDisabilityRiskCoverageESet = premiumOccupationalDisabilityRiskCoverageESet;
    premiumOccupationalDisabilityRiskCoverageESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE, oldPremiumOccupationalDisabilityRiskCoverage, premiumOccupationalDisabilityRiskCoverage, !oldPremiumOccupationalDisabilityRiskCoverageESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPremiumOccupationalDisabilityRiskCoverage() {
    PgCurrency oldPremiumOccupationalDisabilityRiskCoverage = premiumOccupationalDisabilityRiskCoverage;
    boolean oldPremiumOccupationalDisabilityRiskCoverageESet = premiumOccupationalDisabilityRiskCoverageESet;
    premiumOccupationalDisabilityRiskCoverage = PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE_EDEFAULT;
    premiumOccupationalDisabilityRiskCoverageESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE, oldPremiumOccupationalDisabilityRiskCoverage, PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE_EDEFAULT, oldPremiumOccupationalDisabilityRiskCoverageESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetPremiumOccupationalDisabilityRiskCoverage() {
    return premiumOccupationalDisabilityRiskCoverageESet;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getUpkeepPremium() {
    return upkeepPremium;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setUpkeepPremium(PgCurrency newUpkeepPremium) {
    PgCurrency oldUpkeepPremium = upkeepPremium;
    upkeepPremium = newUpkeepPremium;
    boolean oldUpkeepPremiumESet = upkeepPremiumESet;
    upkeepPremiumESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__UPKEEP_PREMIUM, oldUpkeepPremium, upkeepPremium, !oldUpkeepPremiumESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetUpkeepPremium() {
    PgCurrency oldUpkeepPremium = upkeepPremium;
    boolean oldUpkeepPremiumESet = upkeepPremiumESet;
    upkeepPremium = UPKEEP_PREMIUM_EDEFAULT;
    upkeepPremiumESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__UPKEEP_PREMIUM, oldUpkeepPremium, UPKEEP_PREMIUM_EDEFAULT, oldUpkeepPremiumESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetUpkeepPremium() {
    return upkeepPremiumESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getFirstCostsInsuranceCompany() {
    return firstCostsInsuranceCompany;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setFirstCostsInsuranceCompany(PgCurrency newFirstCostsInsuranceCompany) {
    PgCurrency oldFirstCostsInsuranceCompany = firstCostsInsuranceCompany;
    firstCostsInsuranceCompany = newFirstCostsInsuranceCompany;
    boolean oldFirstCostsInsuranceCompanyESet = firstCostsInsuranceCompanyESet;
    firstCostsInsuranceCompanyESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__FIRST_COSTS_INSURANCE_COMPANY, oldFirstCostsInsuranceCompany, firstCostsInsuranceCompany, !oldFirstCostsInsuranceCompanyESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFirstCostsInsuranceCompany() {
    PgCurrency oldFirstCostsInsuranceCompany = firstCostsInsuranceCompany;
    boolean oldFirstCostsInsuranceCompanyESet = firstCostsInsuranceCompanyESet;
    firstCostsInsuranceCompany = FIRST_COSTS_INSURANCE_COMPANY_EDEFAULT;
    firstCostsInsuranceCompanyESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__FIRST_COSTS_INSURANCE_COMPANY, oldFirstCostsInsuranceCompany, FIRST_COSTS_INSURANCE_COMPANY_EDEFAULT, oldFirstCostsInsuranceCompanyESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetFirstCostsInsuranceCompany() {
    return firstCostsInsuranceCompanyESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getContinuingCostsInsuranceCompany() {
    return continuingCostsInsuranceCompany;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setContinuingCostsInsuranceCompany(PgCurrency newContinuingCostsInsuranceCompany) {
    PgCurrency oldContinuingCostsInsuranceCompany = continuingCostsInsuranceCompany;
    continuingCostsInsuranceCompany = newContinuingCostsInsuranceCompany;
    boolean oldContinuingCostsInsuranceCompanyESet = continuingCostsInsuranceCompanyESet;
    continuingCostsInsuranceCompanyESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__CONTINUING_COSTS_INSURANCE_COMPANY, oldContinuingCostsInsuranceCompany, continuingCostsInsuranceCompany, !oldContinuingCostsInsuranceCompanyESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetContinuingCostsInsuranceCompany() {
    PgCurrency oldContinuingCostsInsuranceCompany = continuingCostsInsuranceCompany;
    boolean oldContinuingCostsInsuranceCompanyESet = continuingCostsInsuranceCompanyESet;
    continuingCostsInsuranceCompany = CONTINUING_COSTS_INSURANCE_COMPANY_EDEFAULT;
    continuingCostsInsuranceCompanyESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__CONTINUING_COSTS_INSURANCE_COMPANY, oldContinuingCostsInsuranceCompany, CONTINUING_COSTS_INSURANCE_COMPANY_EDEFAULT, oldContinuingCostsInsuranceCompanyESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetContinuingCostsInsuranceCompany() {
    return continuingCostsInsuranceCompanyESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getManagementCosts() {
    return managementCosts;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setManagementCosts(PgCurrency newManagementCosts) {
    PgCurrency oldManagementCosts = managementCosts;
    managementCosts = newManagementCosts;
    boolean oldManagementCostsESet = managementCostsESet;
    managementCostsESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__MANAGEMENT_COSTS, oldManagementCosts, managementCosts, !oldManagementCostsESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetManagementCosts() {
    PgCurrency oldManagementCosts = managementCosts;
    boolean oldManagementCostsESet = managementCostsESet;
    managementCosts = MANAGEMENT_COSTS_EDEFAULT;
    managementCostsESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__MANAGEMENT_COSTS, oldManagementCosts, MANAGEMENT_COSTS_EDEFAULT, oldManagementCostsESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetManagementCosts() {
    return managementCostsESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getBuyAndSellCosts() {
    return buyAndSellCosts;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setBuyAndSellCosts(PgCurrency newBuyAndSellCosts) {
    PgCurrency oldBuyAndSellCosts = buyAndSellCosts;
    buyAndSellCosts = newBuyAndSellCosts;
    boolean oldBuyAndSellCostsESet = buyAndSellCostsESet;
    buyAndSellCostsESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__BUY_AND_SELL_COSTS, oldBuyAndSellCosts, buyAndSellCosts, !oldBuyAndSellCostsESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetBuyAndSellCosts() {
    PgCurrency oldBuyAndSellCosts = buyAndSellCosts;
    boolean oldBuyAndSellCostsESet = buyAndSellCostsESet;
    buyAndSellCosts = BUY_AND_SELL_COSTS_EDEFAULT;
    buyAndSellCostsESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__BUY_AND_SELL_COSTS, oldBuyAndSellCosts, BUY_AND_SELL_COSTS_EDEFAULT, oldBuyAndSellCostsESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetBuyAndSellCosts() {
    return buyAndSellCostsESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getMutationCosts() {
    return mutationCosts;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setMutationCosts(PgCurrency newMutationCosts) {
    PgCurrency oldMutationCosts = mutationCosts;
    mutationCosts = newMutationCosts;
    boolean oldMutationCostsESet = mutationCostsESet;
    mutationCostsESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__MUTATION_COSTS, oldMutationCosts, mutationCosts, !oldMutationCostsESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetMutationCosts() {
    PgCurrency oldMutationCosts = mutationCosts;
    boolean oldMutationCostsESet = mutationCostsESet;
    mutationCosts = MUTATION_COSTS_EDEFAULT;
    mutationCostsESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__MUTATION_COSTS, oldMutationCosts, MUTATION_COSTS_EDEFAULT, oldMutationCostsESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetMutationCosts() {
    return mutationCostsESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getCostsRestitution() {
    return costsRestitution;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setCostsRestitution(PgCurrency newCostsRestitution) {
    PgCurrency oldCostsRestitution = costsRestitution;
    costsRestitution = newCostsRestitution;
    boolean oldCostsRestitutionESet = costsRestitutionESet;
    costsRestitutionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__COSTS_RESTITUTION, oldCostsRestitution, costsRestitution, !oldCostsRestitutionESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCostsRestitution() {
    PgCurrency oldCostsRestitution = costsRestitution;
    boolean oldCostsRestitutionESet = costsRestitutionESet;
    costsRestitution = COSTS_RESTITUTION_EDEFAULT;
    costsRestitutionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__COSTS_RESTITUTION, oldCostsRestitution, COSTS_RESTITUTION_EDEFAULT, oldCostsRestitutionESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetCostsRestitution() {
    return costsRestitutionESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getCorrections() {
    return corrections;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setCorrections(PgCurrency newCorrections) {
    PgCurrency oldCorrections = corrections;
    corrections = newCorrections;
    boolean oldCorrectionsESet = correctionsESet;
    correctionsESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__CORRECTIONS, oldCorrections, corrections, !oldCorrectionsESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetCorrections() {
    PgCurrency oldCorrections = corrections;
    boolean oldCorrectionsESet = correctionsESet;
    corrections = CORRECTIONS_EDEFAULT;
    correctionsESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__CORRECTIONS, oldCorrections, CORRECTIONS_EDEFAULT, oldCorrectionsESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetCorrections() {
    return correctionsESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getEarnedOnTheParticipations() {
    return earnedOnTheParticipations;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setEarnedOnTheParticipations(PgCurrency newEarnedOnTheParticipations) {
    PgCurrency oldEarnedOnTheParticipations = earnedOnTheParticipations;
    earnedOnTheParticipations = newEarnedOnTheParticipations;
    boolean oldEarnedOnTheParticipationsESet = earnedOnTheParticipationsESet;
    earnedOnTheParticipationsESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__EARNED_ON_THE_PARTICIPATIONS, oldEarnedOnTheParticipations, earnedOnTheParticipations, !oldEarnedOnTheParticipationsESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetEarnedOnTheParticipations() {
    PgCurrency oldEarnedOnTheParticipations = earnedOnTheParticipations;
    boolean oldEarnedOnTheParticipationsESet = earnedOnTheParticipationsESet;
    earnedOnTheParticipations = EARNED_ON_THE_PARTICIPATIONS_EDEFAULT;
    earnedOnTheParticipationsESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__EARNED_ON_THE_PARTICIPATIONS, oldEarnedOnTheParticipations, EARNED_ON_THE_PARTICIPATIONS_EDEFAULT, oldEarnedOnTheParticipationsESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetEarnedOnTheParticipations() {
    return earnedOnTheParticipationsESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public EList<Participation> getParticipations() {
    if (participations == null) {
      participations = new EObjectContainmentEList<Participation>(Participation.class, this, InvestmentInsurancePackage.ANNUAL_STATEMENT__PARTICIPATIONS);
    }
    return participations;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public LocalDate getExampleCapitalOnEndDate() {
    return exampleCapitalOnEndDate;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setExampleCapitalOnEndDate(LocalDate newExampleCapitalOnEndDate) {
    LocalDate oldExampleCapitalOnEndDate = exampleCapitalOnEndDate;
    exampleCapitalOnEndDate = newExampleCapitalOnEndDate;
    boolean oldExampleCapitalOnEndDateESet = exampleCapitalOnEndDateESet;
    exampleCapitalOnEndDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__EXAMPLE_CAPITAL_ON_END_DATE, oldExampleCapitalOnEndDate, exampleCapitalOnEndDate, !oldExampleCapitalOnEndDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetExampleCapitalOnEndDate() {
    LocalDate oldExampleCapitalOnEndDate = exampleCapitalOnEndDate;
    boolean oldExampleCapitalOnEndDateESet = exampleCapitalOnEndDateESet;
    exampleCapitalOnEndDate = EXAMPLE_CAPITAL_ON_END_DATE_EDEFAULT;
    exampleCapitalOnEndDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__EXAMPLE_CAPITAL_ON_END_DATE, oldExampleCapitalOnEndDate, EXAMPLE_CAPITAL_ON_END_DATE_EDEFAULT, oldExampleCapitalOnEndDateESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetExampleCapitalOnEndDate() {
    return exampleCapitalOnEndDateESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getExpectedYearlyCostsIncrease() {
    return expectedYearlyCostsIncrease;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setExpectedYearlyCostsIncrease(FixedPointValue newExpectedYearlyCostsIncrease) {
    FixedPointValue oldExpectedYearlyCostsIncrease = expectedYearlyCostsIncrease;
    expectedYearlyCostsIncrease = newExpectedYearlyCostsIncrease;
    boolean oldExpectedYearlyCostsIncreaseESet = expectedYearlyCostsIncreaseESet;
    expectedYearlyCostsIncreaseESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.ANNUAL_STATEMENT__EXPECTED_YEARLY_COSTS_INCREASE, oldExpectedYearlyCostsIncrease, expectedYearlyCostsIncrease, !oldExpectedYearlyCostsIncreaseESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetExpectedYearlyCostsIncrease() {
    FixedPointValue oldExpectedYearlyCostsIncrease = expectedYearlyCostsIncrease;
    boolean oldExpectedYearlyCostsIncreaseESet = expectedYearlyCostsIncreaseESet;
    expectedYearlyCostsIncrease = EXPECTED_YEARLY_COSTS_INCREASE_EDEFAULT;
    expectedYearlyCostsIncreaseESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.ANNUAL_STATEMENT__EXPECTED_YEARLY_COSTS_INCREASE, oldExpectedYearlyCostsIncrease, EXPECTED_YEARLY_COSTS_INCREASE_EDEFAULT, oldExpectedYearlyCostsIncreaseESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetExpectedYearlyCostsIncrease() {
    return expectedYearlyCostsIncreaseESet;
  }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return 
	 * @generated NOT
	 */
	public PgCurrency totalCosts() {
		PgCurrency totaleKosten = null;

		if (isSetPremiumDeathRiskCoverage()) {
			totaleKosten = premiumDeathRiskCoverage;
		}

		if (isSetPremiumOccupationalDisabilityRiskCoverage()) {
			if (totaleKosten == null) {
				totaleKosten = premiumOccupationalDisabilityRiskCoverage;
			} else {
				totaleKosten = totaleKosten.add(premiumOccupationalDisabilityRiskCoverage);
			}
		}

		if (isSetUpkeepPremium()) {
			if (totaleKosten == null) {
				totaleKosten = upkeepPremium;
			} else {
				totaleKosten = totaleKosten.add(upkeepPremium);
			}
		}

		if (isSetFirstCostsInsuranceCompany()) {
			if (totaleKosten == null) {
				totaleKosten = firstCostsInsuranceCompany;
			} else {
				totaleKosten = totaleKosten.add(firstCostsInsuranceCompany);
			}
		}

		if (isSetContinuingCostsInsuranceCompany()) {
			if (totaleKosten == null) {
				totaleKosten = continuingCostsInsuranceCompany;
			} else {
				totaleKosten = totaleKosten.add(continuingCostsInsuranceCompany);
			}
		}

		if (isSetManagementCosts()) {
			if (totaleKosten == null) {
				totaleKosten = managementCosts;
			} else {
				totaleKosten = totaleKosten.add(managementCosts);
			}
		}

		if (isSetBuyAndSellCosts()) {
			totaleKosten = totaleKosten.add(buyAndSellCosts);
		}

		if (isSetMutationCosts()) {
			if (totaleKosten == null) {
				totaleKosten = mutationCosts;
			} else {
				totaleKosten = totaleKosten.add(mutationCosts);
			}
		}

		if (isSetCorrections()) {
			if (totaleKosten == null) {
				totaleKosten = corrections;
			} else {
				totaleKosten = totaleKosten.add(corrections);
			}
		}

		return totaleKosten;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PgCurrency valueAtYearStart() {
		PgCurrency totaleBeginWaarde = new PgCurrency(PgCurrency.EURO, 0l, 1000);
		for (Participation participatie : getParticipations()) {
			InvestmentFund beleggingsFonds = participatie.getInvestmentFund();
			FixedPointValue aantalParticipaties = participatie.numberOfParticipationsStart();
			if (aantalParticipaties == null) {
				LOGGER.info("Geen aantal participaties voor beleggingsfonds " + beleggingsFonds.getName()
						+ " voor datum " + getPeriodFrom());
				return null;
			}
			PgCurrency participatieWaarde;
			if (aantalParticipaties.isZero()) {
				participatieWaarde = new PgCurrency(PgCurrency.EURO, 0l, 1000);
			} else {
				PgCurrency koers = beleggingsFonds.getStockPrice(getPeriodFrom());
				if (koers == null) {
					LOGGER.info("Geen koers voor beleggingsfonds " + beleggingsFonds.getName() + " voor datum "
							+ getPeriodFrom());
					return null;
				}
				if (koers.getFactor() < 1000) {
					koers = koers.certifyFactor(1000);
				}
				participatieWaarde = koers.multiply(aantalParticipaties.doubleValue());
			}
			if (totaleBeginWaarde == null) {
				totaleBeginWaarde = participatieWaarde;
			} else {
				if (totaleBeginWaarde.getFactor() > participatieWaarde.getFactor()) {
					participatieWaarde = participatieWaarde.certifyFactor(totaleBeginWaarde.getFactor());
				} else if (totaleBeginWaarde.getFactor() < participatieWaarde.getFactor()) {
					totaleBeginWaarde = totaleBeginWaarde.certifyFactor(participatieWaarde.getFactor());
				}
				totaleBeginWaarde = totaleBeginWaarde.add(participatieWaarde);
			}
		}
		totaleBeginWaarde = totaleBeginWaarde.certifyFactor(100);

		return totaleBeginWaarde;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PgCurrency percentageCosts() {
		PgCurrency procentueleKosten = new PgCurrency(0);

		if (isSetManagementCosts()) {
			procentueleKosten = procentueleKosten.add(managementCosts);
		}

		return procentueleKosten;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PgCurrency fixedCosts() {
		PgCurrency totaleKosten = new PgCurrency(0);

		if (isSetPremiumDeathRiskCoverage()) {
			totaleKosten = totaleKosten.add(premiumDeathRiskCoverage);
		}

		if (isSetPremiumOccupationalDisabilityRiskCoverage()) {
			totaleKosten = totaleKosten.add(premiumOccupationalDisabilityRiskCoverage);
		}

		if (isSetUpkeepPremium()) {
			totaleKosten = totaleKosten.add(upkeepPremium);
		}

		if (isSetContinuingCostsInsuranceCompany()) {
			totaleKosten = totaleKosten.add(continuingCostsInsuranceCompany);
		}

		return totaleKosten;
	}

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PARTICIPATIONS:
        return ((InternalEList<?>)getParticipations()).basicRemove(otherEnd, msgs);
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
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PERIOD_FROM:
        return getPeriodFrom();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PERIOD_UNTIL:
        return getPeriodUntil();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_PAID:
        return getPremiumPaid();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_DEATH_RISK_COVERAGE:
        return getPremiumDeathRiskCoverage();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE:
        return getPremiumOccupationalDisabilityRiskCoverage();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__UPKEEP_PREMIUM:
        return getUpkeepPremium();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__FIRST_COSTS_INSURANCE_COMPANY:
        return getFirstCostsInsuranceCompany();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__CONTINUING_COSTS_INSURANCE_COMPANY:
        return getContinuingCostsInsuranceCompany();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__MANAGEMENT_COSTS:
        return getManagementCosts();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__BUY_AND_SELL_COSTS:
        return getBuyAndSellCosts();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__MUTATION_COSTS:
        return getMutationCosts();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__COSTS_RESTITUTION:
        return getCostsRestitution();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__CORRECTIONS:
        return getCorrections();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__EARNED_ON_THE_PARTICIPATIONS:
        return getEarnedOnTheParticipations();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PARTICIPATIONS:
        return getParticipations();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__EXAMPLE_CAPITAL_ON_END_DATE:
        return getExampleCapitalOnEndDate();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__EXPECTED_YEARLY_COSTS_INCREASE:
        return getExpectedYearlyCostsIncrease();
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
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PERIOD_FROM:
        setPeriodFrom((LocalDate)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PERIOD_UNTIL:
        setPeriodUntil((LocalDate)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_PAID:
        setPremiumPaid((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_DEATH_RISK_COVERAGE:
        setPremiumDeathRiskCoverage((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE:
        setPremiumOccupationalDisabilityRiskCoverage((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__UPKEEP_PREMIUM:
        setUpkeepPremium((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__FIRST_COSTS_INSURANCE_COMPANY:
        setFirstCostsInsuranceCompany((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__CONTINUING_COSTS_INSURANCE_COMPANY:
        setContinuingCostsInsuranceCompany((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__MANAGEMENT_COSTS:
        setManagementCosts((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__BUY_AND_SELL_COSTS:
        setBuyAndSellCosts((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__MUTATION_COSTS:
        setMutationCosts((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__COSTS_RESTITUTION:
        setCostsRestitution((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__CORRECTIONS:
        setCorrections((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__EARNED_ON_THE_PARTICIPATIONS:
        setEarnedOnTheParticipations((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PARTICIPATIONS:
        getParticipations().clear();
        getParticipations().addAll((Collection<? extends Participation>)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__EXAMPLE_CAPITAL_ON_END_DATE:
        setExampleCapitalOnEndDate((LocalDate)newValue);
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__EXPECTED_YEARLY_COSTS_INCREASE:
        setExpectedYearlyCostsIncrease((FixedPointValue)newValue);
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
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PERIOD_FROM:
        unsetPeriodFrom();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PERIOD_UNTIL:
        unsetPeriodUntil();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_PAID:
        unsetPremiumPaid();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_DEATH_RISK_COVERAGE:
        unsetPremiumDeathRiskCoverage();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE:
        unsetPremiumOccupationalDisabilityRiskCoverage();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__UPKEEP_PREMIUM:
        unsetUpkeepPremium();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__FIRST_COSTS_INSURANCE_COMPANY:
        unsetFirstCostsInsuranceCompany();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__CONTINUING_COSTS_INSURANCE_COMPANY:
        unsetContinuingCostsInsuranceCompany();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__MANAGEMENT_COSTS:
        unsetManagementCosts();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__BUY_AND_SELL_COSTS:
        unsetBuyAndSellCosts();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__MUTATION_COSTS:
        unsetMutationCosts();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__COSTS_RESTITUTION:
        unsetCostsRestitution();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__CORRECTIONS:
        unsetCorrections();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__EARNED_ON_THE_PARTICIPATIONS:
        unsetEarnedOnTheParticipations();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PARTICIPATIONS:
        getParticipations().clear();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__EXAMPLE_CAPITAL_ON_END_DATE:
        unsetExampleCapitalOnEndDate();
        return;
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__EXPECTED_YEARLY_COSTS_INCREASE:
        unsetExpectedYearlyCostsIncrease();
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
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PERIOD_FROM:
        return isSetPeriodFrom();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PERIOD_UNTIL:
        return isSetPeriodUntil();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_PAID:
        return isSetPremiumPaid();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_DEATH_RISK_COVERAGE:
        return isSetPremiumDeathRiskCoverage();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PREMIUM_OCCUPATIONAL_DISABILITY_RISK_COVERAGE:
        return isSetPremiumOccupationalDisabilityRiskCoverage();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__UPKEEP_PREMIUM:
        return isSetUpkeepPremium();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__FIRST_COSTS_INSURANCE_COMPANY:
        return isSetFirstCostsInsuranceCompany();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__CONTINUING_COSTS_INSURANCE_COMPANY:
        return isSetContinuingCostsInsuranceCompany();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__MANAGEMENT_COSTS:
        return isSetManagementCosts();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__BUY_AND_SELL_COSTS:
        return isSetBuyAndSellCosts();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__MUTATION_COSTS:
        return isSetMutationCosts();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__COSTS_RESTITUTION:
        return isSetCostsRestitution();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__CORRECTIONS:
        return isSetCorrections();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__EARNED_ON_THE_PARTICIPATIONS:
        return isSetEarnedOnTheParticipations();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__PARTICIPATIONS:
        return participations != null && !participations.isEmpty();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__EXAMPLE_CAPITAL_ON_END_DATE:
        return isSetExampleCapitalOnEndDate();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT__EXPECTED_YEARLY_COSTS_INCREASE:
        return isSetExpectedYearlyCostsIncrease();
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
      case InvestmentInsurancePackage.ANNUAL_STATEMENT___TOTAL_COSTS:
        return totalCosts();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT___VALUE_AT_YEAR_START:
        return valueAtYearStart();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT___PERCENTAGE_COSTS:
        return percentageCosts();
      case InvestmentInsurancePackage.ANNUAL_STATEMENT___FIXED_COSTS:
        return fixedCosts();
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
    result.append(" (periodFrom: ");
    if (periodFromESet) result.append(periodFrom); else result.append("<unset>");
    result.append(", periodUntil: ");
    if (periodUntilESet) result.append(periodUntil); else result.append("<unset>");
    result.append(", premiumPaid: ");
    if (premiumPaidESet) result.append(premiumPaid); else result.append("<unset>");
    result.append(", premiumDeathRiskCoverage: ");
    if (premiumDeathRiskCoverageESet) result.append(premiumDeathRiskCoverage); else result.append("<unset>");
    result.append(", premiumOccupationalDisabilityRiskCoverage: ");
    if (premiumOccupationalDisabilityRiskCoverageESet) result.append(premiumOccupationalDisabilityRiskCoverage); else result.append("<unset>");
    result.append(", upkeepPremium: ");
    if (upkeepPremiumESet) result.append(upkeepPremium); else result.append("<unset>");
    result.append(", firstCostsInsuranceCompany: ");
    if (firstCostsInsuranceCompanyESet) result.append(firstCostsInsuranceCompany); else result.append("<unset>");
    result.append(", continuingCostsInsuranceCompany: ");
    if (continuingCostsInsuranceCompanyESet) result.append(continuingCostsInsuranceCompany); else result.append("<unset>");
    result.append(", managementCosts: ");
    if (managementCostsESet) result.append(managementCosts); else result.append("<unset>");
    result.append(", buyAndSellCosts: ");
    if (buyAndSellCostsESet) result.append(buyAndSellCosts); else result.append("<unset>");
    result.append(", mutationCosts: ");
    if (mutationCostsESet) result.append(mutationCosts); else result.append("<unset>");
    result.append(", costsRestitution: ");
    if (costsRestitutionESet) result.append(costsRestitution); else result.append("<unset>");
    result.append(", corrections: ");
    if (correctionsESet) result.append(corrections); else result.append("<unset>");
    result.append(", earnedOnTheParticipations: ");
    if (earnedOnTheParticipationsESet) result.append(earnedOnTheParticipations); else result.append("<unset>");
    result.append(", exampleCapitalOnEndDate: ");
    if (exampleCapitalOnEndDateESet) result.append(exampleCapitalOnEndDate); else result.append("<unset>");
    result.append(", expectedYearlyCostsIncrease: ");
    if (expectedYearlyCostsIncreaseESet) result.append(expectedYearlyCostsIncrease); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //JaarOpgaveImpl
