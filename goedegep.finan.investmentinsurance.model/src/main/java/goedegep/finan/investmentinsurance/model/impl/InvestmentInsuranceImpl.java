/**
 */
package goedegep.finan.investmentinsurance.model.impl;

import goedegep.finan.investmentinsurance.model.AnnualStatement;
import goedegep.finan.investmentinsurance.model.InsuranceCompany;
import goedegep.finan.investmentinsurance.model.InvestmentFund;
import goedegep.finan.investmentinsurance.model.InvestmentInsurance;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage;
import goedegep.finan.investmentinsurance.model.InvestmentPart;
import goedegep.finan.investmentinsurance.model.Participation;
import goedegep.rolodex.model.Person;
import goedegep.types.model.Event;
import goedegep.util.finance.FinanceUtil;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Collection;

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
 * An implementation of the model object '<em><b>Beleggings Verzekering</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl#getInsuranceCompany <em>Insurance Company</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl#getPolicyNumber <em>Policy Number</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl#getStartingDate <em>Starting Date</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl#getPolicyHolder <em>Policy Holder</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl#getPremium <em>Premium</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl#getInsuredBenefitOnDeath <em>Insured Benefit On Death</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl#getInvestmentParts <em>Investment Parts</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsuranceImpl#getEvents <em>Events</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvestmentInsuranceImpl extends MinimalEObjectImpl.Container implements InvestmentInsurance {
	/**
   * The cached value of the '{@link #getInsuranceCompany() <em>Insurance Company</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInsuranceCompany()
   * @generated
   * @ordered
   */
  protected InsuranceCompany insuranceCompany;

  /**
   * The default value of the '{@link #getPolicyNumber() <em>Policy Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPolicyNumber()
   * @generated
   * @ordered
   */
  protected static final String POLICY_NUMBER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPolicyNumber() <em>Policy Number</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getPolicyNumber()
   * @generated
   * @ordered
   */
	protected String policyNumber = POLICY_NUMBER_EDEFAULT;

	/**
   * This is true if the Policy Number attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean policyNumberESet;

  /**
   * The default value of the '{@link #getStartingDate() <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartingDate()
   * @generated
   * @ordered
   */
  protected static final LocalDate STARTING_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStartingDate() <em>Starting Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStartingDate()
   * @generated
   * @ordered
   */
  protected LocalDate startingDate = STARTING_DATE_EDEFAULT;

  /**
   * This is true if the Starting Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean startingDateESet;

  /**
   * The cached value of the '{@link #getPolicyHolder() <em>Policy Holder</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPolicyHolder()
   * @generated
   * @ordered
   */
  protected Person policyHolder;

  /**
   * This is true if the Policy Holder reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean policyHolderESet;

  /**
   * The default value of the '{@link #getPremium() <em>Premium</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPremium()
   * @generated
   * @ordered
   */
  protected static final PgCurrency PREMIUM_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPremium() <em>Premium</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPremium()
   * @generated
   * @ordered
   */
  protected PgCurrency premium = PREMIUM_EDEFAULT;

  /**
   * This is true if the Premium attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean premiumESet;

  /**
   * The default value of the '{@link #getInsuredBenefitOnDeath() <em>Insured Benefit On Death</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInsuredBenefitOnDeath()
   * @generated
   * @ordered
   */
  protected static final PgCurrency INSURED_BENEFIT_ON_DEATH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInsuredBenefitOnDeath() <em>Insured Benefit On Death</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInsuredBenefitOnDeath()
   * @generated
   * @ordered
   */
  protected PgCurrency insuredBenefitOnDeath = INSURED_BENEFIT_ON_DEATH_EDEFAULT;

  /**
   * This is true if the Insured Benefit On Death attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean insuredBenefitOnDeathESet;

  /**
   * The cached value of the '{@link #getInvestmentParts() <em>Investment Parts</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvestmentParts()
   * @generated
   * @ordered
   */
  protected EList<InvestmentPart> investmentParts;

  /**
   * The cached value of the '{@link #getEvents() <em>Events</em>}' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getEvents()
   * @generated
   * @ordered
   */
	protected EList<Event> events;

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected InvestmentInsuranceImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EClass eStaticClass() {
    return InvestmentInsurancePackage.Literals.INVESTMENT_INSURANCE;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public InsuranceCompany getInsuranceCompany() {
    if (insuranceCompany != null && insuranceCompany.eIsProxy()) {
      InternalEObject oldInsuranceCompany = (InternalEObject)insuranceCompany;
      insuranceCompany = (InsuranceCompany)eResolveProxy(oldInsuranceCompany);
      if (insuranceCompany != oldInsuranceCompany) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, InvestmentInsurancePackage.INVESTMENT_INSURANCE__INSURANCE_COMPANY, oldInsuranceCompany, insuranceCompany));
      }
    }
    return insuranceCompany;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InsuranceCompany basicGetInsuranceCompany() {
    return insuranceCompany;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setInsuranceCompany(InsuranceCompany newInsuranceCompany) {
    InsuranceCompany oldInsuranceCompany = insuranceCompany;
    insuranceCompany = newInsuranceCompany;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCE__INSURANCE_COMPANY, oldInsuranceCompany, insuranceCompany));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public String getPolicyNumber() {
    return policyNumber;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setPolicyNumber(String newPolicyNumber) {
    String oldPolicyNumber = policyNumber;
    policyNumber = newPolicyNumber;
    boolean oldPolicyNumberESet = policyNumberESet;
    policyNumberESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_NUMBER, oldPolicyNumber, policyNumber, !oldPolicyNumberESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPolicyNumber() {
    String oldPolicyNumber = policyNumber;
    boolean oldPolicyNumberESet = policyNumberESet;
    policyNumber = POLICY_NUMBER_EDEFAULT;
    policyNumberESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_NUMBER, oldPolicyNumber, POLICY_NUMBER_EDEFAULT, oldPolicyNumberESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetPolicyNumber() {
    return policyNumberESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public LocalDate getStartingDate() {
    return startingDate;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setStartingDate(LocalDate newStartingDate) {
    LocalDate oldStartingDate = startingDate;
    startingDate = newStartingDate;
    boolean oldStartingDateESet = startingDateESet;
    startingDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCE__STARTING_DATE, oldStartingDate, startingDate, !oldStartingDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetStartingDate() {
    LocalDate oldStartingDate = startingDate;
    boolean oldStartingDateESet = startingDateESet;
    startingDate = STARTING_DATE_EDEFAULT;
    startingDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.INVESTMENT_INSURANCE__STARTING_DATE, oldStartingDate, STARTING_DATE_EDEFAULT, oldStartingDateESet));
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
  public Person getPolicyHolder() {
    if (policyHolder != null && policyHolder.eIsProxy()) {
      InternalEObject oldPolicyHolder = (InternalEObject)policyHolder;
      policyHolder = (Person)eResolveProxy(oldPolicyHolder);
      if (policyHolder != oldPolicyHolder) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_HOLDER, oldPolicyHolder, policyHolder));
      }
    }
    return policyHolder;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Person basicGetPolicyHolder() {
    return policyHolder;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPolicyHolder(Person newPolicyHolder) {
    Person oldPolicyHolder = policyHolder;
    policyHolder = newPolicyHolder;
    boolean oldPolicyHolderESet = policyHolderESet;
    policyHolderESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_HOLDER, oldPolicyHolder, policyHolder, !oldPolicyHolderESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPolicyHolder() {
    Person oldPolicyHolder = policyHolder;
    boolean oldPolicyHolderESet = policyHolderESet;
    policyHolder = null;
    policyHolderESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_HOLDER, oldPolicyHolder, null, oldPolicyHolderESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetPolicyHolder() {
    return policyHolderESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getPremium() {
    return premium;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setPremium(PgCurrency newPremium) {
    PgCurrency oldPremium = premium;
    premium = newPremium;
    boolean oldPremiumESet = premiumESet;
    premiumESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCE__PREMIUM, oldPremium, premium, !oldPremiumESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPremium() {
    PgCurrency oldPremium = premium;
    boolean oldPremiumESet = premiumESet;
    premium = PREMIUM_EDEFAULT;
    premiumESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.INVESTMENT_INSURANCE__PREMIUM, oldPremium, PREMIUM_EDEFAULT, oldPremiumESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetPremium() {
    return premiumESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PgCurrency getInsuredBenefitOnDeath() {
    return insuredBenefitOnDeath;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setInsuredBenefitOnDeath(PgCurrency newInsuredBenefitOnDeath) {
    PgCurrency oldInsuredBenefitOnDeath = insuredBenefitOnDeath;
    insuredBenefitOnDeath = newInsuredBenefitOnDeath;
    boolean oldInsuredBenefitOnDeathESet = insuredBenefitOnDeathESet;
    insuredBenefitOnDeathESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCE__INSURED_BENEFIT_ON_DEATH, oldInsuredBenefitOnDeath, insuredBenefitOnDeath, !oldInsuredBenefitOnDeathESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetInsuredBenefitOnDeath() {
    PgCurrency oldInsuredBenefitOnDeath = insuredBenefitOnDeath;
    boolean oldInsuredBenefitOnDeathESet = insuredBenefitOnDeathESet;
    insuredBenefitOnDeath = INSURED_BENEFIT_ON_DEATH_EDEFAULT;
    insuredBenefitOnDeathESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.INVESTMENT_INSURANCE__INSURED_BENEFIT_ON_DEATH, oldInsuredBenefitOnDeath, INSURED_BENEFIT_ON_DEATH_EDEFAULT, oldInsuredBenefitOnDeathESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetInsuredBenefitOnDeath() {
    return insuredBenefitOnDeathESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public EList<InvestmentPart> getInvestmentParts() {
    if (investmentParts == null) {
      investmentParts = new EObjectContainmentEList<InvestmentPart>(InvestmentPart.class, this, InvestmentInsurancePackage.INVESTMENT_INSURANCE__INVESTMENT_PARTS);
    }
    return investmentParts;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public EList<Event> getEvents() {
    if (events == null) {
      events = new EObjectContainmentEList<Event>(Event.class, this, InvestmentInsurancePackage.INVESTMENT_INSURANCE__EVENTS);
    }
    return events;
  }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PgCurrency getLastKnownValue() {
		AnnualStatement jaarOpgave = getLastAnnualStatement();

		if (jaarOpgave != null) {
			PgCurrency totaleEindWaarde = null;
			for (Participation participatie : jaarOpgave.getParticipations()) {
				InvestmentFund beleggingsFonds = participatie.getInvestmentFund();
				FixedPointValue aantalParticipaties = participatie.getNumberOfParticipationsEnd();
				PgCurrency koers = beleggingsFonds.getStockPrice(jaarOpgave.getPeriodUntil());
				if (koers != null && aantalParticipaties != null) {
					koers = koers.certifyFactor(1000);
					PgCurrency fondsWaardeExact = koers.multiply(aantalParticipaties.doubleValue());
					if (totaleEindWaarde == null) {
						totaleEindWaarde = fondsWaardeExact;
					} else {
						totaleEindWaarde = totaleEindWaarde.add(fondsWaardeExact);
					}
				}
			}
			if (totaleEindWaarde != null) {
				totaleEindWaarde = totaleEindWaarde.certifyFactor(100);
			}
			return totaleEindWaarde;
		} else {
			return getPremium();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public LocalDate getDateForLastKnownValue() {
		AnnualStatement jaarOpgave = getLastAnnualStatement();

		if (jaarOpgave != null) {
			return jaarOpgave.getPeriodUntil();
		} else {
			return getStartingDate();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public AnnualStatement getLastAnnualStatement() {
		AnnualStatement jaarOpgave = null;

		for (Event event : getEvents()) {
			if (event instanceof AnnualStatement) {
				jaarOpgave = (AnnualStatement) event;
			}
		}

		return jaarOpgave;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public FixedPointValue getAverageReturnOnInvestment() {
		int openingsJaar = getStartingDate().getYear();
		int laatstBekendeWaardeJaar = getDateForLastKnownValue().getYear();

		return FinanceUtil.returnOnInvestment(getPremium().certifyCurrency(PgCurrency.EURO),
				getLastKnownValue().certifyCurrency(PgCurrency.EURO), laatstBekendeWaardeJaar - openingsJaar);
	}

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INVESTMENT_PARTS:
        return ((InternalEList<?>)getInvestmentParts()).basicRemove(otherEnd, msgs);
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__EVENTS:
        return ((InternalEList<?>)getEvents()).basicRemove(otherEnd, msgs);
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
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INSURANCE_COMPANY:
        if (resolve) return getInsuranceCompany();
        return basicGetInsuranceCompany();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_NUMBER:
        return getPolicyNumber();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__STARTING_DATE:
        return getStartingDate();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_HOLDER:
        if (resolve) return getPolicyHolder();
        return basicGetPolicyHolder();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__PREMIUM:
        return getPremium();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INSURED_BENEFIT_ON_DEATH:
        return getInsuredBenefitOnDeath();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INVESTMENT_PARTS:
        return getInvestmentParts();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__EVENTS:
        return getEvents();
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
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INSURANCE_COMPANY:
        setInsuranceCompany((InsuranceCompany)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_NUMBER:
        setPolicyNumber((String)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__STARTING_DATE:
        setStartingDate((LocalDate)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_HOLDER:
        setPolicyHolder((Person)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__PREMIUM:
        setPremium((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INSURED_BENEFIT_ON_DEATH:
        setInsuredBenefitOnDeath((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INVESTMENT_PARTS:
        getInvestmentParts().clear();
        getInvestmentParts().addAll((Collection<? extends InvestmentPart>)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__EVENTS:
        getEvents().clear();
        getEvents().addAll((Collection<? extends Event>)newValue);
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
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INSURANCE_COMPANY:
        setInsuranceCompany((InsuranceCompany)null);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_NUMBER:
        unsetPolicyNumber();
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__STARTING_DATE:
        unsetStartingDate();
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_HOLDER:
        unsetPolicyHolder();
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__PREMIUM:
        unsetPremium();
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INSURED_BENEFIT_ON_DEATH:
        unsetInsuredBenefitOnDeath();
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INVESTMENT_PARTS:
        getInvestmentParts().clear();
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__EVENTS:
        getEvents().clear();
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
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INSURANCE_COMPANY:
        return insuranceCompany != null;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_NUMBER:
        return isSetPolicyNumber();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__STARTING_DATE:
        return isSetStartingDate();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__POLICY_HOLDER:
        return isSetPolicyHolder();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__PREMIUM:
        return isSetPremium();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INSURED_BENEFIT_ON_DEATH:
        return isSetInsuredBenefitOnDeath();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__INVESTMENT_PARTS:
        return investmentParts != null && !investmentParts.isEmpty();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE__EVENTS:
        return events != null && !events.isEmpty();
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
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE___GET_LAST_KNOWN_VALUE:
        return getLastKnownValue();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE___GET_DATE_FOR_LAST_KNOWN_VALUE:
        return getDateForLastKnownValue();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE___GET_LAST_ANNUAL_STATEMENT:
        return getLastAnnualStatement();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCE___GET_AVERAGE_RETURN_ON_INVESTMENT:
        return getAverageReturnOnInvestment();
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
    result.append(" (policyNumber: ");
    if (policyNumberESet) result.append(policyNumber); else result.append("<unset>");
    result.append(", startingDate: ");
    if (startingDateESet) result.append(startingDate); else result.append("<unset>");
    result.append(", premium: ");
    if (premiumESet) result.append(premium); else result.append("<unset>");
    result.append(", insuredBenefitOnDeath: ");
    if (insuredBenefitOnDeathESet) result.append(insuredBenefitOnDeath); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //BeleggingsVerzekeringImpl
