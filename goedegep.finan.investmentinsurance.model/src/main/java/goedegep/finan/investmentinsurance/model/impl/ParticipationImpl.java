/**
 */
package goedegep.finan.investmentinsurance.model.impl;

import goedegep.finan.investmentinsurance.model.AnnualStatement;
import goedegep.finan.investmentinsurance.model.InvestmentFund;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage;
import goedegep.finan.investmentinsurance.model.Participation;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Participatie</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getInvestmentFund <em>Investment Fund</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getParticipationMutations <em>Participation Mutations</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getExampleReturnOnInvestmentNetHistoric <em>Example Return On Investment Net Historic</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getExampleReturnOnInvestmentGross <em>Example Return On Investment Gross</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getExampleReturnOnInvestmentGrossCompanyOwn <em>Example Return On Investment Gross Company Own</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getExampleReturnOnInvestmentPessimistic <em>Example Return On Investment Pessimistic</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getTotalExpenseRatio <em>Total Expense Ratio</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getExampleCapitalNetHistoric <em>Example Capital Net Historic</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getExampleCapitalGross <em>Example Capital Gross</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getExampleCapitalGrossCompanyOwn <em>Example Capital Gross Company Own</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getExampleCapitalPessimistic <em>Example Capital Pessimistic</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getReturnOnInvestmentReductionNetHistoric <em>Return On Investment Reduction Net Historic</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getExampleCapitalAfterReduction <em>Example Capital After Reduction</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getDistributionPercentage <em>Distribution Percentage</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getStandardFundReturnOnInvestment <em>Standard Fund Return On Investment</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getExampleCapitalStandardFundReturnOnInvestment <em>Example Capital Standard Fund Return On Investment</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getNumberOfParticipationsEnd <em>Number Of Participations End</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#isParticipationMutationsComplete <em>Participation Mutations Complete</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ParticipationImpl#getAnnualStatement <em>Annual Statement</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParticipationImpl extends MinimalEObjectImpl.Container implements Participation {
	/**
   * The cached value of the '{@link #getInvestmentFund() <em>Investment Fund</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvestmentFund()
   * @generated
   * @ordered
   */
  protected InvestmentFund investmentFund;

  /**
   * The cached value of the '{@link #getParticipationMutations() <em>Participation Mutations</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParticipationMutations()
   * @generated
   * @ordered
   */
  protected EList<FixedPointValue> participationMutations;

  /**
   * The default value of the '{@link #getExampleReturnOnInvestmentNetHistoric() <em>Example Return On Investment Net Historic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleReturnOnInvestmentNetHistoric()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExampleReturnOnInvestmentNetHistoric() <em>Example Return On Investment Net Historic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleReturnOnInvestmentNetHistoric()
   * @generated
   * @ordered
   */
  protected FixedPointValue exampleReturnOnInvestmentNetHistoric = EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC_EDEFAULT;

  /**
   * This is true if the Example Return On Investment Net Historic attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean exampleReturnOnInvestmentNetHistoricESet;

  /**
   * The default value of the '{@link #getExampleReturnOnInvestmentGross() <em>Example Return On Investment Gross</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleReturnOnInvestmentGross()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue EXAMPLE_RETURN_ON_INVESTMENT_GROSS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExampleReturnOnInvestmentGross() <em>Example Return On Investment Gross</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleReturnOnInvestmentGross()
   * @generated
   * @ordered
   */
  protected FixedPointValue exampleReturnOnInvestmentGross = EXAMPLE_RETURN_ON_INVESTMENT_GROSS_EDEFAULT;

  /**
   * This is true if the Example Return On Investment Gross attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean exampleReturnOnInvestmentGrossESet;

  /**
   * The default value of the '{@link #getExampleReturnOnInvestmentGrossCompanyOwn() <em>Example Return On Investment Gross Company Own</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleReturnOnInvestmentGrossCompanyOwn()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExampleReturnOnInvestmentGrossCompanyOwn() <em>Example Return On Investment Gross Company Own</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleReturnOnInvestmentGrossCompanyOwn()
   * @generated
   * @ordered
   */
  protected FixedPointValue exampleReturnOnInvestmentGrossCompanyOwn = EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN_EDEFAULT;

  /**
   * This is true if the Example Return On Investment Gross Company Own attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean exampleReturnOnInvestmentGrossCompanyOwnESet;

  /**
   * The default value of the '{@link #getExampleReturnOnInvestmentPessimistic() <em>Example Return On Investment Pessimistic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleReturnOnInvestmentPessimistic()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExampleReturnOnInvestmentPessimistic() <em>Example Return On Investment Pessimistic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleReturnOnInvestmentPessimistic()
   * @generated
   * @ordered
   */
  protected FixedPointValue exampleReturnOnInvestmentPessimistic = EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC_EDEFAULT;

  /**
   * This is true if the Example Return On Investment Pessimistic attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean exampleReturnOnInvestmentPessimisticESet;

  private static final Logger LOGGER = Logger.getLogger(ParticipationImpl.class.getName());

	/**
   * The default value of the '{@link #getTotalExpenseRatio() <em>Total Expense Ratio</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getTotalExpenseRatio()
   * @generated
   * @ordered
   */
	protected static final FixedPointValue TOTAL_EXPENSE_RATIO_EDEFAULT = null;

	/**
   * The cached value of the '{@link #getTotalExpenseRatio() <em>Total Expense Ratio</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getTotalExpenseRatio()
   * @generated
   * @ordered
   */
	protected FixedPointValue totalExpenseRatio = TOTAL_EXPENSE_RATIO_EDEFAULT;

	/**
   * This is true if the Total Expense Ratio attribute has been set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	protected boolean totalExpenseRatioESet;

	/**
   * The default value of the '{@link #getExampleCapitalNetHistoric() <em>Example Capital Net Historic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalNetHistoric()
   * @generated
   * @ordered
   */
  protected static final PgCurrency EXAMPLE_CAPITAL_NET_HISTORIC_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExampleCapitalNetHistoric() <em>Example Capital Net Historic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalNetHistoric()
   * @generated
   * @ordered
   */
  protected PgCurrency exampleCapitalNetHistoric = EXAMPLE_CAPITAL_NET_HISTORIC_EDEFAULT;

  /**
   * This is true if the Example Capital Net Historic attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean exampleCapitalNetHistoricESet;

  /**
   * The default value of the '{@link #getExampleCapitalGross() <em>Example Capital Gross</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalGross()
   * @generated
   * @ordered
   */
  protected static final PgCurrency EXAMPLE_CAPITAL_GROSS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExampleCapitalGross() <em>Example Capital Gross</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalGross()
   * @generated
   * @ordered
   */
  protected PgCurrency exampleCapitalGross = EXAMPLE_CAPITAL_GROSS_EDEFAULT;

  /**
   * This is true if the Example Capital Gross attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean exampleCapitalGrossESet;

  /**
   * The default value of the '{@link #getExampleCapitalGrossCompanyOwn() <em>Example Capital Gross Company Own</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalGrossCompanyOwn()
   * @generated
   * @ordered
   */
  protected static final PgCurrency EXAMPLE_CAPITAL_GROSS_COMPANY_OWN_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExampleCapitalGrossCompanyOwn() <em>Example Capital Gross Company Own</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalGrossCompanyOwn()
   * @generated
   * @ordered
   */
  protected PgCurrency exampleCapitalGrossCompanyOwn = EXAMPLE_CAPITAL_GROSS_COMPANY_OWN_EDEFAULT;

  /**
   * This is true if the Example Capital Gross Company Own attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean exampleCapitalGrossCompanyOwnESet;

  /**
   * The default value of the '{@link #getExampleCapitalPessimistic() <em>Example Capital Pessimistic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalPessimistic()
   * @generated
   * @ordered
   */
  protected static final PgCurrency EXAMPLE_CAPITAL_PESSIMISTIC_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExampleCapitalPessimistic() <em>Example Capital Pessimistic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalPessimistic()
   * @generated
   * @ordered
   */
  protected PgCurrency exampleCapitalPessimistic = EXAMPLE_CAPITAL_PESSIMISTIC_EDEFAULT;

  /**
   * This is true if the Example Capital Pessimistic attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean exampleCapitalPessimisticESet;

  /**
   * The default value of the '{@link #getReturnOnInvestmentReductionNetHistoric() <em>Return On Investment Reduction Net Historic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getReturnOnInvestmentReductionNetHistoric()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getReturnOnInvestmentReductionNetHistoric() <em>Return On Investment Reduction Net Historic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getReturnOnInvestmentReductionNetHistoric()
   * @generated
   * @ordered
   */
  protected FixedPointValue returnOnInvestmentReductionNetHistoric = RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC_EDEFAULT;

  /**
   * This is true if the Return On Investment Reduction Net Historic attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean returnOnInvestmentReductionNetHistoricESet;

  /**
   * The default value of the '{@link #getExampleCapitalAfterReduction() <em>Example Capital After Reduction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalAfterReduction()
   * @generated
   * @ordered
   */
  protected static final PgCurrency EXAMPLE_CAPITAL_AFTER_REDUCTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExampleCapitalAfterReduction() <em>Example Capital After Reduction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalAfterReduction()
   * @generated
   * @ordered
   */
  protected PgCurrency exampleCapitalAfterReduction = EXAMPLE_CAPITAL_AFTER_REDUCTION_EDEFAULT;

  /**
   * This is true if the Example Capital After Reduction attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean exampleCapitalAfterReductionESet;

  /**
   * The default value of the '{@link #getDistributionPercentage() <em>Distribution Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDistributionPercentage()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue DISTRIBUTION_PERCENTAGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDistributionPercentage() <em>Distribution Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDistributionPercentage()
   * @generated
   * @ordered
   */
  protected FixedPointValue distributionPercentage = DISTRIBUTION_PERCENTAGE_EDEFAULT;

  /**
   * This is true if the Distribution Percentage attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean distributionPercentageESet;

  /**
   * The default value of the '{@link #getStandardFundReturnOnInvestment() <em>Standard Fund Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStandardFundReturnOnInvestment()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue STANDARD_FUND_RETURN_ON_INVESTMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStandardFundReturnOnInvestment() <em>Standard Fund Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStandardFundReturnOnInvestment()
   * @generated
   * @ordered
   */
  protected FixedPointValue standardFundReturnOnInvestment = STANDARD_FUND_RETURN_ON_INVESTMENT_EDEFAULT;

  /**
   * This is true if the Standard Fund Return On Investment attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean standardFundReturnOnInvestmentESet;

  /**
   * The default value of the '{@link #getExampleCapitalStandardFundReturnOnInvestment() <em>Example Capital Standard Fund Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalStandardFundReturnOnInvestment()
   * @generated
   * @ordered
   */
  protected static final PgCurrency EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExampleCapitalStandardFundReturnOnInvestment() <em>Example Capital Standard Fund Return On Investment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExampleCapitalStandardFundReturnOnInvestment()
   * @generated
   * @ordered
   */
  protected PgCurrency exampleCapitalStandardFundReturnOnInvestment = EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT_EDEFAULT;

  /**
   * This is true if the Example Capital Standard Fund Return On Investment attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean exampleCapitalStandardFundReturnOnInvestmentESet;

  /**
   * The default value of the '{@link #getNumberOfParticipationsEnd() <em>Number Of Participations End</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfParticipationsEnd()
   * @generated
   * @ordered
   */
  protected static final FixedPointValue NUMBER_OF_PARTICIPATIONS_END_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNumberOfParticipationsEnd() <em>Number Of Participations End</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfParticipationsEnd()
   * @generated
   * @ordered
   */
  protected FixedPointValue numberOfParticipationsEnd = NUMBER_OF_PARTICIPATIONS_END_EDEFAULT;

  /**
   * This is true if the Number Of Participations End attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean numberOfParticipationsEndESet;

  /**
   * The default value of the '{@link #isParticipationMutationsComplete() <em>Participation Mutations Complete</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isParticipationMutationsComplete()
   * @generated
   * @ordered
   */
  protected static final boolean PARTICIPATION_MUTATIONS_COMPLETE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isParticipationMutationsComplete() <em>Participation Mutations Complete</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isParticipationMutationsComplete()
   * @generated
   * @ordered
   */
  protected boolean participationMutationsComplete = PARTICIPATION_MUTATIONS_COMPLETE_EDEFAULT;

  /**
   * The cached value of the '{@link #getAnnualStatement() <em>Annual Statement</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAnnualStatement()
   * @generated
   * @ordered
   */
  protected AnnualStatement annualStatement;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected ParticipationImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EClass eStaticClass() {
    return InvestmentInsurancePackage.Literals.PARTICIPATION;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public InvestmentFund getInvestmentFund() {
    if (investmentFund != null && investmentFund.eIsProxy()) {
      InternalEObject oldInvestmentFund = (InternalEObject)investmentFund;
      investmentFund = (InvestmentFund)eResolveProxy(oldInvestmentFund);
      if (investmentFund != oldInvestmentFund) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, InvestmentInsurancePackage.PARTICIPATION__INVESTMENT_FUND, oldInvestmentFund, investmentFund));
      }
    }
    return investmentFund;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvestmentFund basicGetInvestmentFund() {
    return investmentFund;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setInvestmentFund(InvestmentFund newInvestmentFund) {
    InvestmentFund oldInvestmentFund = investmentFund;
    investmentFund = newInvestmentFund;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__INVESTMENT_FUND, oldInvestmentFund, investmentFund));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public EList<FixedPointValue> getParticipationMutations() {
    if (participationMutations == null) {
      participationMutations = new EDataTypeUniqueEList.Unsettable<FixedPointValue>(FixedPointValue.class, this, InvestmentInsurancePackage.PARTICIPATION__PARTICIPATION_MUTATIONS);
    }
    return participationMutations;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetParticipationMutations() {
    if (participationMutations != null) ((InternalEList.Unsettable<?>)participationMutations).unset();
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetParticipationMutations() {
    return participationMutations != null && ((InternalEList.Unsettable<?>)participationMutations).isSet();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getExampleReturnOnInvestmentNetHistoric() {
    return exampleReturnOnInvestmentNetHistoric;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setExampleReturnOnInvestmentNetHistoric(FixedPointValue newExampleReturnOnInvestmentNetHistoric) {
    FixedPointValue oldExampleReturnOnInvestmentNetHistoric = exampleReturnOnInvestmentNetHistoric;
    exampleReturnOnInvestmentNetHistoric = newExampleReturnOnInvestmentNetHistoric;
    boolean oldExampleReturnOnInvestmentNetHistoricESet = exampleReturnOnInvestmentNetHistoricESet;
    exampleReturnOnInvestmentNetHistoricESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC, oldExampleReturnOnInvestmentNetHistoric, exampleReturnOnInvestmentNetHistoric, !oldExampleReturnOnInvestmentNetHistoricESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetExampleReturnOnInvestmentNetHistoric() {
    FixedPointValue oldExampleReturnOnInvestmentNetHistoric = exampleReturnOnInvestmentNetHistoric;
    boolean oldExampleReturnOnInvestmentNetHistoricESet = exampleReturnOnInvestmentNetHistoricESet;
    exampleReturnOnInvestmentNetHistoric = EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC_EDEFAULT;
    exampleReturnOnInvestmentNetHistoricESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC, oldExampleReturnOnInvestmentNetHistoric, EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC_EDEFAULT, oldExampleReturnOnInvestmentNetHistoricESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetExampleReturnOnInvestmentNetHistoric() {
    return exampleReturnOnInvestmentNetHistoricESet;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getExampleReturnOnInvestmentGross() {
    return exampleReturnOnInvestmentGross;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setExampleReturnOnInvestmentGross(FixedPointValue newExampleReturnOnInvestmentGross) {
    FixedPointValue oldExampleReturnOnInvestmentGross = exampleReturnOnInvestmentGross;
    exampleReturnOnInvestmentGross = newExampleReturnOnInvestmentGross;
    boolean oldExampleReturnOnInvestmentGrossESet = exampleReturnOnInvestmentGrossESet;
    exampleReturnOnInvestmentGrossESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS, oldExampleReturnOnInvestmentGross, exampleReturnOnInvestmentGross, !oldExampleReturnOnInvestmentGrossESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetExampleReturnOnInvestmentGross() {
    FixedPointValue oldExampleReturnOnInvestmentGross = exampleReturnOnInvestmentGross;
    boolean oldExampleReturnOnInvestmentGrossESet = exampleReturnOnInvestmentGrossESet;
    exampleReturnOnInvestmentGross = EXAMPLE_RETURN_ON_INVESTMENT_GROSS_EDEFAULT;
    exampleReturnOnInvestmentGrossESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS, oldExampleReturnOnInvestmentGross, EXAMPLE_RETURN_ON_INVESTMENT_GROSS_EDEFAULT, oldExampleReturnOnInvestmentGrossESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetExampleReturnOnInvestmentGross() {
    return exampleReturnOnInvestmentGrossESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getExampleReturnOnInvestmentGrossCompanyOwn() {
    return exampleReturnOnInvestmentGrossCompanyOwn;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setExampleReturnOnInvestmentGrossCompanyOwn(FixedPointValue newExampleReturnOnInvestmentGrossCompanyOwn) {
    FixedPointValue oldExampleReturnOnInvestmentGrossCompanyOwn = exampleReturnOnInvestmentGrossCompanyOwn;
    exampleReturnOnInvestmentGrossCompanyOwn = newExampleReturnOnInvestmentGrossCompanyOwn;
    boolean oldExampleReturnOnInvestmentGrossCompanyOwnESet = exampleReturnOnInvestmentGrossCompanyOwnESet;
    exampleReturnOnInvestmentGrossCompanyOwnESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN, oldExampleReturnOnInvestmentGrossCompanyOwn, exampleReturnOnInvestmentGrossCompanyOwn, !oldExampleReturnOnInvestmentGrossCompanyOwnESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetExampleReturnOnInvestmentGrossCompanyOwn() {
    FixedPointValue oldExampleReturnOnInvestmentGrossCompanyOwn = exampleReturnOnInvestmentGrossCompanyOwn;
    boolean oldExampleReturnOnInvestmentGrossCompanyOwnESet = exampleReturnOnInvestmentGrossCompanyOwnESet;
    exampleReturnOnInvestmentGrossCompanyOwn = EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN_EDEFAULT;
    exampleReturnOnInvestmentGrossCompanyOwnESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN, oldExampleReturnOnInvestmentGrossCompanyOwn, EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN_EDEFAULT, oldExampleReturnOnInvestmentGrossCompanyOwnESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetExampleReturnOnInvestmentGrossCompanyOwn() {
    return exampleReturnOnInvestmentGrossCompanyOwnESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getExampleReturnOnInvestmentPessimistic() {
    return exampleReturnOnInvestmentPessimistic;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setExampleReturnOnInvestmentPessimistic(FixedPointValue newExampleReturnOnInvestmentPessimistic) {
    FixedPointValue oldExampleReturnOnInvestmentPessimistic = exampleReturnOnInvestmentPessimistic;
    exampleReturnOnInvestmentPessimistic = newExampleReturnOnInvestmentPessimistic;
    boolean oldExampleReturnOnInvestmentPessimisticESet = exampleReturnOnInvestmentPessimisticESet;
    exampleReturnOnInvestmentPessimisticESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC, oldExampleReturnOnInvestmentPessimistic, exampleReturnOnInvestmentPessimistic, !oldExampleReturnOnInvestmentPessimisticESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetExampleReturnOnInvestmentPessimistic() {
    FixedPointValue oldExampleReturnOnInvestmentPessimistic = exampleReturnOnInvestmentPessimistic;
    boolean oldExampleReturnOnInvestmentPessimisticESet = exampleReturnOnInvestmentPessimisticESet;
    exampleReturnOnInvestmentPessimistic = EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC_EDEFAULT;
    exampleReturnOnInvestmentPessimisticESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC, oldExampleReturnOnInvestmentPessimistic, EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC_EDEFAULT, oldExampleReturnOnInvestmentPessimisticESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetExampleReturnOnInvestmentPessimistic() {
    return exampleReturnOnInvestmentPessimisticESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getTotalExpenseRatio() {
    return totalExpenseRatio;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setTotalExpenseRatio(FixedPointValue newTotalExpenseRatio) {
    FixedPointValue oldTotalExpenseRatio = totalExpenseRatio;
    totalExpenseRatio = newTotalExpenseRatio;
    boolean oldTotalExpenseRatioESet = totalExpenseRatioESet;
    totalExpenseRatioESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__TOTAL_EXPENSE_RATIO, oldTotalExpenseRatio, totalExpenseRatio, !oldTotalExpenseRatioESet));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void unsetTotalExpenseRatio() {
    FixedPointValue oldTotalExpenseRatio = totalExpenseRatio;
    boolean oldTotalExpenseRatioESet = totalExpenseRatioESet;
    totalExpenseRatio = TOTAL_EXPENSE_RATIO_EDEFAULT;
    totalExpenseRatioESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__TOTAL_EXPENSE_RATIO, oldTotalExpenseRatio, TOTAL_EXPENSE_RATIO_EDEFAULT, oldTotalExpenseRatioESet));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetTotalExpenseRatio() {
    return totalExpenseRatioESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getExampleCapitalNetHistoric() {
    return exampleCapitalNetHistoric;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setExampleCapitalNetHistoric(PgCurrency newExampleCapitalNetHistoric) {
    PgCurrency oldExampleCapitalNetHistoric = exampleCapitalNetHistoric;
    exampleCapitalNetHistoric = newExampleCapitalNetHistoric;
    boolean oldExampleCapitalNetHistoricESet = exampleCapitalNetHistoricESet;
    exampleCapitalNetHistoricESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_NET_HISTORIC, oldExampleCapitalNetHistoric, exampleCapitalNetHistoric, !oldExampleCapitalNetHistoricESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetExampleCapitalNetHistoric() {
    PgCurrency oldExampleCapitalNetHistoric = exampleCapitalNetHistoric;
    boolean oldExampleCapitalNetHistoricESet = exampleCapitalNetHistoricESet;
    exampleCapitalNetHistoric = EXAMPLE_CAPITAL_NET_HISTORIC_EDEFAULT;
    exampleCapitalNetHistoricESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_NET_HISTORIC, oldExampleCapitalNetHistoric, EXAMPLE_CAPITAL_NET_HISTORIC_EDEFAULT, oldExampleCapitalNetHistoricESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetExampleCapitalNetHistoric() {
    return exampleCapitalNetHistoricESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getExampleCapitalGross() {
    return exampleCapitalGross;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setExampleCapitalGross(PgCurrency newExampleCapitalGross) {
    PgCurrency oldExampleCapitalGross = exampleCapitalGross;
    exampleCapitalGross = newExampleCapitalGross;
    boolean oldExampleCapitalGrossESet = exampleCapitalGrossESet;
    exampleCapitalGrossESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_GROSS, oldExampleCapitalGross, exampleCapitalGross, !oldExampleCapitalGrossESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetExampleCapitalGross() {
    PgCurrency oldExampleCapitalGross = exampleCapitalGross;
    boolean oldExampleCapitalGrossESet = exampleCapitalGrossESet;
    exampleCapitalGross = EXAMPLE_CAPITAL_GROSS_EDEFAULT;
    exampleCapitalGrossESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_GROSS, oldExampleCapitalGross, EXAMPLE_CAPITAL_GROSS_EDEFAULT, oldExampleCapitalGrossESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetExampleCapitalGross() {
    return exampleCapitalGrossESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getExampleCapitalGrossCompanyOwn() {
    return exampleCapitalGrossCompanyOwn;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setExampleCapitalGrossCompanyOwn(PgCurrency newExampleCapitalGrossCompanyOwn) {
    PgCurrency oldExampleCapitalGrossCompanyOwn = exampleCapitalGrossCompanyOwn;
    exampleCapitalGrossCompanyOwn = newExampleCapitalGrossCompanyOwn;
    boolean oldExampleCapitalGrossCompanyOwnESet = exampleCapitalGrossCompanyOwnESet;
    exampleCapitalGrossCompanyOwnESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_GROSS_COMPANY_OWN, oldExampleCapitalGrossCompanyOwn, exampleCapitalGrossCompanyOwn, !oldExampleCapitalGrossCompanyOwnESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetExampleCapitalGrossCompanyOwn() {
    PgCurrency oldExampleCapitalGrossCompanyOwn = exampleCapitalGrossCompanyOwn;
    boolean oldExampleCapitalGrossCompanyOwnESet = exampleCapitalGrossCompanyOwnESet;
    exampleCapitalGrossCompanyOwn = EXAMPLE_CAPITAL_GROSS_COMPANY_OWN_EDEFAULT;
    exampleCapitalGrossCompanyOwnESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_GROSS_COMPANY_OWN, oldExampleCapitalGrossCompanyOwn, EXAMPLE_CAPITAL_GROSS_COMPANY_OWN_EDEFAULT, oldExampleCapitalGrossCompanyOwnESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetExampleCapitalGrossCompanyOwn() {
    return exampleCapitalGrossCompanyOwnESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getExampleCapitalPessimistic() {
    return exampleCapitalPessimistic;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setExampleCapitalPessimistic(PgCurrency newExampleCapitalPessimistic) {
    PgCurrency oldExampleCapitalPessimistic = exampleCapitalPessimistic;
    exampleCapitalPessimistic = newExampleCapitalPessimistic;
    boolean oldExampleCapitalPessimisticESet = exampleCapitalPessimisticESet;
    exampleCapitalPessimisticESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_PESSIMISTIC, oldExampleCapitalPessimistic, exampleCapitalPessimistic, !oldExampleCapitalPessimisticESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetExampleCapitalPessimistic() {
    PgCurrency oldExampleCapitalPessimistic = exampleCapitalPessimistic;
    boolean oldExampleCapitalPessimisticESet = exampleCapitalPessimisticESet;
    exampleCapitalPessimistic = EXAMPLE_CAPITAL_PESSIMISTIC_EDEFAULT;
    exampleCapitalPessimisticESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_PESSIMISTIC, oldExampleCapitalPessimistic, EXAMPLE_CAPITAL_PESSIMISTIC_EDEFAULT, oldExampleCapitalPessimisticESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetExampleCapitalPessimistic() {
    return exampleCapitalPessimisticESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getReturnOnInvestmentReductionNetHistoric() {
    return returnOnInvestmentReductionNetHistoric;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setReturnOnInvestmentReductionNetHistoric(FixedPointValue newReturnOnInvestmentReductionNetHistoric) {
    FixedPointValue oldReturnOnInvestmentReductionNetHistoric = returnOnInvestmentReductionNetHistoric;
    returnOnInvestmentReductionNetHistoric = newReturnOnInvestmentReductionNetHistoric;
    boolean oldReturnOnInvestmentReductionNetHistoricESet = returnOnInvestmentReductionNetHistoricESet;
    returnOnInvestmentReductionNetHistoricESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC, oldReturnOnInvestmentReductionNetHistoric, returnOnInvestmentReductionNetHistoric, !oldReturnOnInvestmentReductionNetHistoricESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetReturnOnInvestmentReductionNetHistoric() {
    FixedPointValue oldReturnOnInvestmentReductionNetHistoric = returnOnInvestmentReductionNetHistoric;
    boolean oldReturnOnInvestmentReductionNetHistoricESet = returnOnInvestmentReductionNetHistoricESet;
    returnOnInvestmentReductionNetHistoric = RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC_EDEFAULT;
    returnOnInvestmentReductionNetHistoricESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC, oldReturnOnInvestmentReductionNetHistoric, RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC_EDEFAULT, oldReturnOnInvestmentReductionNetHistoricESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetReturnOnInvestmentReductionNetHistoric() {
    return returnOnInvestmentReductionNetHistoricESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getExampleCapitalAfterReduction() {
    return exampleCapitalAfterReduction;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setExampleCapitalAfterReduction(PgCurrency newExampleCapitalAfterReduction) {
    PgCurrency oldExampleCapitalAfterReduction = exampleCapitalAfterReduction;
    exampleCapitalAfterReduction = newExampleCapitalAfterReduction;
    boolean oldExampleCapitalAfterReductionESet = exampleCapitalAfterReductionESet;
    exampleCapitalAfterReductionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_AFTER_REDUCTION, oldExampleCapitalAfterReduction, exampleCapitalAfterReduction, !oldExampleCapitalAfterReductionESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetExampleCapitalAfterReduction() {
    PgCurrency oldExampleCapitalAfterReduction = exampleCapitalAfterReduction;
    boolean oldExampleCapitalAfterReductionESet = exampleCapitalAfterReductionESet;
    exampleCapitalAfterReduction = EXAMPLE_CAPITAL_AFTER_REDUCTION_EDEFAULT;
    exampleCapitalAfterReductionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_AFTER_REDUCTION, oldExampleCapitalAfterReduction, EXAMPLE_CAPITAL_AFTER_REDUCTION_EDEFAULT, oldExampleCapitalAfterReductionESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetExampleCapitalAfterReduction() {
    return exampleCapitalAfterReductionESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getDistributionPercentage() {
    return distributionPercentage;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setDistributionPercentage(FixedPointValue newDistributionPercentage) {
    FixedPointValue oldDistributionPercentage = distributionPercentage;
    distributionPercentage = newDistributionPercentage;
    boolean oldDistributionPercentageESet = distributionPercentageESet;
    distributionPercentageESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__DISTRIBUTION_PERCENTAGE, oldDistributionPercentage, distributionPercentage, !oldDistributionPercentageESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDistributionPercentage() {
    FixedPointValue oldDistributionPercentage = distributionPercentage;
    boolean oldDistributionPercentageESet = distributionPercentageESet;
    distributionPercentage = DISTRIBUTION_PERCENTAGE_EDEFAULT;
    distributionPercentageESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__DISTRIBUTION_PERCENTAGE, oldDistributionPercentage, DISTRIBUTION_PERCENTAGE_EDEFAULT, oldDistributionPercentageESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetDistributionPercentage() {
    return distributionPercentageESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getStandardFundReturnOnInvestment() {
    return standardFundReturnOnInvestment;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setStandardFundReturnOnInvestment(FixedPointValue newStandardFundReturnOnInvestment) {
    FixedPointValue oldStandardFundReturnOnInvestment = standardFundReturnOnInvestment;
    standardFundReturnOnInvestment = newStandardFundReturnOnInvestment;
    boolean oldStandardFundReturnOnInvestmentESet = standardFundReturnOnInvestmentESet;
    standardFundReturnOnInvestmentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__STANDARD_FUND_RETURN_ON_INVESTMENT, oldStandardFundReturnOnInvestment, standardFundReturnOnInvestment, !oldStandardFundReturnOnInvestmentESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetStandardFundReturnOnInvestment() {
    FixedPointValue oldStandardFundReturnOnInvestment = standardFundReturnOnInvestment;
    boolean oldStandardFundReturnOnInvestmentESet = standardFundReturnOnInvestmentESet;
    standardFundReturnOnInvestment = STANDARD_FUND_RETURN_ON_INVESTMENT_EDEFAULT;
    standardFundReturnOnInvestmentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__STANDARD_FUND_RETURN_ON_INVESTMENT, oldStandardFundReturnOnInvestment, STANDARD_FUND_RETURN_ON_INVESTMENT_EDEFAULT, oldStandardFundReturnOnInvestmentESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetStandardFundReturnOnInvestment() {
    return standardFundReturnOnInvestmentESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getExampleCapitalStandardFundReturnOnInvestment() {
    return exampleCapitalStandardFundReturnOnInvestment;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setExampleCapitalStandardFundReturnOnInvestment(PgCurrency newExampleCapitalStandardFundReturnOnInvestment) {
    PgCurrency oldExampleCapitalStandardFundReturnOnInvestment = exampleCapitalStandardFundReturnOnInvestment;
    exampleCapitalStandardFundReturnOnInvestment = newExampleCapitalStandardFundReturnOnInvestment;
    boolean oldExampleCapitalStandardFundReturnOnInvestmentESet = exampleCapitalStandardFundReturnOnInvestmentESet;
    exampleCapitalStandardFundReturnOnInvestmentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT, oldExampleCapitalStandardFundReturnOnInvestment, exampleCapitalStandardFundReturnOnInvestment, !oldExampleCapitalStandardFundReturnOnInvestmentESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetExampleCapitalStandardFundReturnOnInvestment() {
    PgCurrency oldExampleCapitalStandardFundReturnOnInvestment = exampleCapitalStandardFundReturnOnInvestment;
    boolean oldExampleCapitalStandardFundReturnOnInvestmentESet = exampleCapitalStandardFundReturnOnInvestmentESet;
    exampleCapitalStandardFundReturnOnInvestment = EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT_EDEFAULT;
    exampleCapitalStandardFundReturnOnInvestmentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT, oldExampleCapitalStandardFundReturnOnInvestment, EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT_EDEFAULT, oldExampleCapitalStandardFundReturnOnInvestmentESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetExampleCapitalStandardFundReturnOnInvestment() {
    return exampleCapitalStandardFundReturnOnInvestmentESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getNumberOfParticipationsEnd() {
    return numberOfParticipationsEnd;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setNumberOfParticipationsEnd(FixedPointValue newNumberOfParticipationsEnd) {
    FixedPointValue oldNumberOfParticipationsEnd = numberOfParticipationsEnd;
    numberOfParticipationsEnd = newNumberOfParticipationsEnd;
    boolean oldNumberOfParticipationsEndESet = numberOfParticipationsEndESet;
    numberOfParticipationsEndESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__NUMBER_OF_PARTICIPATIONS_END, oldNumberOfParticipationsEnd, numberOfParticipationsEnd, !oldNumberOfParticipationsEndESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetNumberOfParticipationsEnd() {
    FixedPointValue oldNumberOfParticipationsEnd = numberOfParticipationsEnd;
    boolean oldNumberOfParticipationsEndESet = numberOfParticipationsEndESet;
    numberOfParticipationsEnd = NUMBER_OF_PARTICIPATIONS_END_EDEFAULT;
    numberOfParticipationsEndESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.PARTICIPATION__NUMBER_OF_PARTICIPATIONS_END, oldNumberOfParticipationsEnd, NUMBER_OF_PARTICIPATIONS_END_EDEFAULT, oldNumberOfParticipationsEndESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetNumberOfParticipationsEnd() {
    return numberOfParticipationsEndESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isParticipationMutationsComplete() {
    return participationMutationsComplete;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setParticipationMutationsComplete(boolean newParticipationMutationsComplete) {
    boolean oldParticipationMutationsComplete = participationMutationsComplete;
    participationMutationsComplete = newParticipationMutationsComplete;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__PARTICIPATION_MUTATIONS_COMPLETE, oldParticipationMutationsComplete, participationMutationsComplete));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public AnnualStatement getAnnualStatement() {
    if (annualStatement != null && annualStatement.eIsProxy()) {
      InternalEObject oldAnnualStatement = (InternalEObject)annualStatement;
      annualStatement = (AnnualStatement)eResolveProxy(oldAnnualStatement);
      if (annualStatement != oldAnnualStatement) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, InvestmentInsurancePackage.PARTICIPATION__ANNUAL_STATEMENT, oldAnnualStatement, annualStatement));
      }
    }
    return annualStatement;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public AnnualStatement basicGetAnnualStatement() {
    return annualStatement;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setAnnualStatement(AnnualStatement newAnnualStatement) {
    AnnualStatement oldAnnualStatement = annualStatement;
    annualStatement = newAnnualStatement;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.PARTICIPATION__ANNUAL_STATEMENT, oldAnnualStatement, annualStatement));
  }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public FixedPointValue numberOfParticipationsStart() {
		LOGGER.info("=> ");
		FixedPointValue numberOfParticipationsStart = null;

		if (numberOfParticipationsEnd != null && participationMutationsComplete) {
			numberOfParticipationsStart = numberOfParticipationsEnd.cloneFixedPointValue();

			for (FixedPointValue mutations : getParticipationMutations()) {
				numberOfParticipationsStart = numberOfParticipationsStart.subtract(mutations);
			}
		}

		LOGGER.info("<= " + numberOfParticipationsStart);
		return numberOfParticipationsStart;
	}

	/**
	   * <!-- begin-user-doc -->
	   * <!-- end-user-doc -->
	   * @generated NOT
	   */
	public PgCurrency endValue() {
		LocalDate periodeEindDatum = annualStatement.getPeriodUntil();
		if (periodeEindDatum == null) {
			return null;
		}
		PgCurrency periodeEindKoers = getInvestmentFund().getStockPrice(periodeEindDatum);
		if (periodeEindKoers == null) {
			return null;
		}
		if (!numberOfParticipationsEndESet) {
			return null;
		}
		PgCurrency periodeEindWaarde = periodeEindKoers.multiply(numberOfParticipationsEnd.doubleValue());

		return periodeEindWaarde;
	}

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public FixedPointValue numberOfParticipationsBought() {
    FixedPointValue numberOfParticipationsBought = null;
    for (FixedPointValue participationMutation: getParticipationMutations()) {
      if (!participationMutation.isNegative()) {
        if (numberOfParticipationsBought == null) {
          numberOfParticipationsBought = participationMutation;
        } else {
          numberOfParticipationsBought = numberOfParticipationsBought.add(participationMutation);
        }
      }
    }
    
    return numberOfParticipationsBought;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public FixedPointValue numberOfParticipationsSold() {
    FixedPointValue numberOfParticipationsSold = null;
    for (FixedPointValue participationMutation: getParticipationMutations()) {
      if (participationMutation.isNegative()) {
        if (numberOfParticipationsSold == null) {
          numberOfParticipationsSold = participationMutation;
        } else {
          numberOfParticipationsSold = numberOfParticipationsSold.add(participationMutation);
        }
      }
    }
    
    if (numberOfParticipationsSold != null) {
      numberOfParticipationsSold = numberOfParticipationsSold.changeSign();
    }
    
    return numberOfParticipationsSold;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case InvestmentInsurancePackage.PARTICIPATION__INVESTMENT_FUND:
        if (resolve) return getInvestmentFund();
        return basicGetInvestmentFund();
      case InvestmentInsurancePackage.PARTICIPATION__PARTICIPATION_MUTATIONS:
        return getParticipationMutations();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC:
        return getExampleReturnOnInvestmentNetHistoric();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS:
        return getExampleReturnOnInvestmentGross();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN:
        return getExampleReturnOnInvestmentGrossCompanyOwn();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC:
        return getExampleReturnOnInvestmentPessimistic();
      case InvestmentInsurancePackage.PARTICIPATION__TOTAL_EXPENSE_RATIO:
        return getTotalExpenseRatio();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_NET_HISTORIC:
        return getExampleCapitalNetHistoric();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_GROSS:
        return getExampleCapitalGross();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_GROSS_COMPANY_OWN:
        return getExampleCapitalGrossCompanyOwn();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_PESSIMISTIC:
        return getExampleCapitalPessimistic();
      case InvestmentInsurancePackage.PARTICIPATION__RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC:
        return getReturnOnInvestmentReductionNetHistoric();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_AFTER_REDUCTION:
        return getExampleCapitalAfterReduction();
      case InvestmentInsurancePackage.PARTICIPATION__DISTRIBUTION_PERCENTAGE:
        return getDistributionPercentage();
      case InvestmentInsurancePackage.PARTICIPATION__STANDARD_FUND_RETURN_ON_INVESTMENT:
        return getStandardFundReturnOnInvestment();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT:
        return getExampleCapitalStandardFundReturnOnInvestment();
      case InvestmentInsurancePackage.PARTICIPATION__NUMBER_OF_PARTICIPATIONS_END:
        return getNumberOfParticipationsEnd();
      case InvestmentInsurancePackage.PARTICIPATION__PARTICIPATION_MUTATIONS_COMPLETE:
        return isParticipationMutationsComplete();
      case InvestmentInsurancePackage.PARTICIPATION__ANNUAL_STATEMENT:
        if (resolve) return getAnnualStatement();
        return basicGetAnnualStatement();
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
      case InvestmentInsurancePackage.PARTICIPATION__INVESTMENT_FUND:
        setInvestmentFund((InvestmentFund)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__PARTICIPATION_MUTATIONS:
        getParticipationMutations().clear();
        getParticipationMutations().addAll((Collection<? extends FixedPointValue>)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC:
        setExampleReturnOnInvestmentNetHistoric((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS:
        setExampleReturnOnInvestmentGross((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN:
        setExampleReturnOnInvestmentGrossCompanyOwn((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC:
        setExampleReturnOnInvestmentPessimistic((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__TOTAL_EXPENSE_RATIO:
        setTotalExpenseRatio((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_NET_HISTORIC:
        setExampleCapitalNetHistoric((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_GROSS:
        setExampleCapitalGross((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_GROSS_COMPANY_OWN:
        setExampleCapitalGrossCompanyOwn((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_PESSIMISTIC:
        setExampleCapitalPessimistic((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC:
        setReturnOnInvestmentReductionNetHistoric((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_AFTER_REDUCTION:
        setExampleCapitalAfterReduction((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__DISTRIBUTION_PERCENTAGE:
        setDistributionPercentage((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__STANDARD_FUND_RETURN_ON_INVESTMENT:
        setStandardFundReturnOnInvestment((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT:
        setExampleCapitalStandardFundReturnOnInvestment((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__NUMBER_OF_PARTICIPATIONS_END:
        setNumberOfParticipationsEnd((FixedPointValue)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__PARTICIPATION_MUTATIONS_COMPLETE:
        setParticipationMutationsComplete((Boolean)newValue);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__ANNUAL_STATEMENT:
        setAnnualStatement((AnnualStatement)newValue);
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
      case InvestmentInsurancePackage.PARTICIPATION__INVESTMENT_FUND:
        setInvestmentFund((InvestmentFund)null);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__PARTICIPATION_MUTATIONS:
        unsetParticipationMutations();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC:
        unsetExampleReturnOnInvestmentNetHistoric();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS:
        unsetExampleReturnOnInvestmentGross();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN:
        unsetExampleReturnOnInvestmentGrossCompanyOwn();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC:
        unsetExampleReturnOnInvestmentPessimistic();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__TOTAL_EXPENSE_RATIO:
        unsetTotalExpenseRatio();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_NET_HISTORIC:
        unsetExampleCapitalNetHistoric();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_GROSS:
        unsetExampleCapitalGross();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_GROSS_COMPANY_OWN:
        unsetExampleCapitalGrossCompanyOwn();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_PESSIMISTIC:
        unsetExampleCapitalPessimistic();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC:
        unsetReturnOnInvestmentReductionNetHistoric();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_AFTER_REDUCTION:
        unsetExampleCapitalAfterReduction();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__DISTRIBUTION_PERCENTAGE:
        unsetDistributionPercentage();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__STANDARD_FUND_RETURN_ON_INVESTMENT:
        unsetStandardFundReturnOnInvestment();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT:
        unsetExampleCapitalStandardFundReturnOnInvestment();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__NUMBER_OF_PARTICIPATIONS_END:
        unsetNumberOfParticipationsEnd();
        return;
      case InvestmentInsurancePackage.PARTICIPATION__PARTICIPATION_MUTATIONS_COMPLETE:
        setParticipationMutationsComplete(PARTICIPATION_MUTATIONS_COMPLETE_EDEFAULT);
        return;
      case InvestmentInsurancePackage.PARTICIPATION__ANNUAL_STATEMENT:
        setAnnualStatement((AnnualStatement)null);
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
      case InvestmentInsurancePackage.PARTICIPATION__INVESTMENT_FUND:
        return investmentFund != null;
      case InvestmentInsurancePackage.PARTICIPATION__PARTICIPATION_MUTATIONS:
        return isSetParticipationMutations();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_NET_HISTORIC:
        return isSetExampleReturnOnInvestmentNetHistoric();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS:
        return isSetExampleReturnOnInvestmentGross();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_GROSS_COMPANY_OWN:
        return isSetExampleReturnOnInvestmentGrossCompanyOwn();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_RETURN_ON_INVESTMENT_PESSIMISTIC:
        return isSetExampleReturnOnInvestmentPessimistic();
      case InvestmentInsurancePackage.PARTICIPATION__TOTAL_EXPENSE_RATIO:
        return isSetTotalExpenseRatio();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_NET_HISTORIC:
        return isSetExampleCapitalNetHistoric();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_GROSS:
        return isSetExampleCapitalGross();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_GROSS_COMPANY_OWN:
        return isSetExampleCapitalGrossCompanyOwn();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_PESSIMISTIC:
        return isSetExampleCapitalPessimistic();
      case InvestmentInsurancePackage.PARTICIPATION__RETURN_ON_INVESTMENT_REDUCTION_NET_HISTORIC:
        return isSetReturnOnInvestmentReductionNetHistoric();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_AFTER_REDUCTION:
        return isSetExampleCapitalAfterReduction();
      case InvestmentInsurancePackage.PARTICIPATION__DISTRIBUTION_PERCENTAGE:
        return isSetDistributionPercentage();
      case InvestmentInsurancePackage.PARTICIPATION__STANDARD_FUND_RETURN_ON_INVESTMENT:
        return isSetStandardFundReturnOnInvestment();
      case InvestmentInsurancePackage.PARTICIPATION__EXAMPLE_CAPITAL_STANDARD_FUND_RETURN_ON_INVESTMENT:
        return isSetExampleCapitalStandardFundReturnOnInvestment();
      case InvestmentInsurancePackage.PARTICIPATION__NUMBER_OF_PARTICIPATIONS_END:
        return isSetNumberOfParticipationsEnd();
      case InvestmentInsurancePackage.PARTICIPATION__PARTICIPATION_MUTATIONS_COMPLETE:
        return participationMutationsComplete != PARTICIPATION_MUTATIONS_COMPLETE_EDEFAULT;
      case InvestmentInsurancePackage.PARTICIPATION__ANNUAL_STATEMENT:
        return annualStatement != null;
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
      case InvestmentInsurancePackage.PARTICIPATION___NUMBER_OF_PARTICIPATIONS_START:
        return numberOfParticipationsStart();
      case InvestmentInsurancePackage.PARTICIPATION___END_VALUE:
        return endValue();
      case InvestmentInsurancePackage.PARTICIPATION___NUMBER_OF_PARTICIPATIONS_BOUGHT:
        return numberOfParticipationsBought();
      case InvestmentInsurancePackage.PARTICIPATION___NUMBER_OF_PARTICIPATIONS_SOLD:
        return numberOfParticipationsSold();
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
    result.append(" (participationMutations: ");
    result.append(participationMutations);
    result.append(", exampleReturnOnInvestmentNetHistoric: ");
    if (exampleReturnOnInvestmentNetHistoricESet) result.append(exampleReturnOnInvestmentNetHistoric); else result.append("<unset>");
    result.append(", exampleReturnOnInvestmentGross: ");
    if (exampleReturnOnInvestmentGrossESet) result.append(exampleReturnOnInvestmentGross); else result.append("<unset>");
    result.append(", exampleReturnOnInvestmentGrossCompanyOwn: ");
    if (exampleReturnOnInvestmentGrossCompanyOwnESet) result.append(exampleReturnOnInvestmentGrossCompanyOwn); else result.append("<unset>");
    result.append(", exampleReturnOnInvestmentPessimistic: ");
    if (exampleReturnOnInvestmentPessimisticESet) result.append(exampleReturnOnInvestmentPessimistic); else result.append("<unset>");
    result.append(", totalExpenseRatio: ");
    if (totalExpenseRatioESet) result.append(totalExpenseRatio); else result.append("<unset>");
    result.append(", exampleCapitalNetHistoric: ");
    if (exampleCapitalNetHistoricESet) result.append(exampleCapitalNetHistoric); else result.append("<unset>");
    result.append(", exampleCapitalGross: ");
    if (exampleCapitalGrossESet) result.append(exampleCapitalGross); else result.append("<unset>");
    result.append(", exampleCapitalGrossCompanyOwn: ");
    if (exampleCapitalGrossCompanyOwnESet) result.append(exampleCapitalGrossCompanyOwn); else result.append("<unset>");
    result.append(", exampleCapitalPessimistic: ");
    if (exampleCapitalPessimisticESet) result.append(exampleCapitalPessimistic); else result.append("<unset>");
    result.append(", returnOnInvestmentReductionNetHistoric: ");
    if (returnOnInvestmentReductionNetHistoricESet) result.append(returnOnInvestmentReductionNetHistoric); else result.append("<unset>");
    result.append(", exampleCapitalAfterReduction: ");
    if (exampleCapitalAfterReductionESet) result.append(exampleCapitalAfterReduction); else result.append("<unset>");
    result.append(", distributionPercentage: ");
    if (distributionPercentageESet) result.append(distributionPercentage); else result.append("<unset>");
    result.append(", standardFundReturnOnInvestment: ");
    if (standardFundReturnOnInvestmentESet) result.append(standardFundReturnOnInvestment); else result.append("<unset>");
    result.append(", exampleCapitalStandardFundReturnOnInvestment: ");
    if (exampleCapitalStandardFundReturnOnInvestmentESet) result.append(exampleCapitalStandardFundReturnOnInvestment); else result.append("<unset>");
    result.append(", numberOfParticipationsEnd: ");
    if (numberOfParticipationsEndESet) result.append(numberOfParticipationsEnd); else result.append("<unset>");
    result.append(", participationMutationsComplete: ");
    result.append(participationMutationsComplete);
    result.append(')');
    return result.toString();
  }

} //ParticipatieImpl
