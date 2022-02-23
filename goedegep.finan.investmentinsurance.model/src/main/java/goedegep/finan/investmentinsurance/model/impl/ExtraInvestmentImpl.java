/**
 */
package goedegep.finan.investmentinsurance.model.impl;

import goedegep.finan.investmentinsurance.model.ExtraInvestment;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage;
import goedegep.finan.investmentinsurance.model.InvestmentPart;
import goedegep.finan.investmentinsurance.model.Participation;
import goedegep.types.model.impl.EventImpl;
import goedegep.util.money.PgCurrency;

import java.time.LocalDate;
import java.util.Collection;
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
 * An implementation of the model object '<em><b>Extra Inleg</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ExtraInvestmentImpl#getPremium <em>Premium</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ExtraInvestmentImpl#getDepositDate <em>Deposit Date</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ExtraInvestmentImpl#getInvestmentParts <em>Investment Parts</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ExtraInvestmentImpl#getOverviewDate <em>Overview Date</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.ExtraInvestmentImpl#getParticipations <em>Participations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtraInvestmentImpl extends EventImpl implements ExtraInvestment {
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
   * The default value of the '{@link #getDepositDate() <em>Deposit Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDepositDate()
   * @generated
   * @ordered
   */
  protected static final LocalDate DEPOSIT_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDepositDate() <em>Deposit Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDepositDate()
   * @generated
   * @ordered
   */
  protected LocalDate depositDate = DEPOSIT_DATE_EDEFAULT;

  /**
   * This is true if the Deposit Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean depositDateESet;

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
   * The default value of the '{@link #getOverviewDate() <em>Overview Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOverviewDate()
   * @generated
   * @ordered
   */
  protected static final LocalDate OVERVIEW_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getOverviewDate() <em>Overview Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOverviewDate()
   * @generated
   * @ordered
   */
  protected LocalDate overviewDate = OVERVIEW_DATE_EDEFAULT;

  /**
   * This is true if the Overview Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean overviewDateESet;

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
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected ExtraInvestmentImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EClass eStaticClass() {
    return InvestmentInsurancePackage.Literals.EXTRA_INVESTMENT;
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
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.EXTRA_INVESTMENT__PREMIUM, oldPremium, premium, !oldPremiumESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.EXTRA_INVESTMENT__PREMIUM, oldPremium, PREMIUM_EDEFAULT, oldPremiumESet));
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
  public LocalDate getDepositDate() {
    return depositDate;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDepositDate(LocalDate newDepositDate) {
    LocalDate oldDepositDate = depositDate;
    depositDate = newDepositDate;
    boolean oldDepositDateESet = depositDateESet;
    depositDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.EXTRA_INVESTMENT__DEPOSIT_DATE, oldDepositDate, depositDate, !oldDepositDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDepositDate() {
    LocalDate oldDepositDate = depositDate;
    boolean oldDepositDateESet = depositDateESet;
    depositDate = DEPOSIT_DATE_EDEFAULT;
    depositDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.EXTRA_INVESTMENT__DEPOSIT_DATE, oldDepositDate, DEPOSIT_DATE_EDEFAULT, oldDepositDateESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetDepositDate() {
    return depositDateESet;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<InvestmentPart> getInvestmentParts() {
    if (investmentParts == null) {
      investmentParts = new EObjectContainmentEList<InvestmentPart>(InvestmentPart.class, this, InvestmentInsurancePackage.EXTRA_INVESTMENT__INVESTMENT_PARTS);
    }
    return investmentParts;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public LocalDate getOverviewDate() {
    return overviewDate;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setOverviewDate(LocalDate newOverviewDate) {
    LocalDate oldOverviewDate = overviewDate;
    overviewDate = newOverviewDate;
    boolean oldOverviewDateESet = overviewDateESet;
    overviewDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.EXTRA_INVESTMENT__OVERVIEW_DATE, oldOverviewDate, overviewDate, !oldOverviewDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetOverviewDate() {
    LocalDate oldOverviewDate = overviewDate;
    boolean oldOverviewDateESet = overviewDateESet;
    overviewDate = OVERVIEW_DATE_EDEFAULT;
    overviewDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.EXTRA_INVESTMENT__OVERVIEW_DATE, oldOverviewDate, OVERVIEW_DATE_EDEFAULT, oldOverviewDateESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetOverviewDate() {
    return overviewDateESet;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Participation> getParticipations() {
    if (participations == null) {
      participations = new EObjectContainmentEList<Participation>(Participation.class, this, InvestmentInsurancePackage.EXTRA_INVESTMENT__PARTICIPATIONS);
    }
    return participations;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__INVESTMENT_PARTS:
        return ((InternalEList<?>)getInvestmentParts()).basicRemove(otherEnd, msgs);
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__PARTICIPATIONS:
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
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__PREMIUM:
        return getPremium();
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__DEPOSIT_DATE:
        return getDepositDate();
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__INVESTMENT_PARTS:
        return getInvestmentParts();
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__OVERVIEW_DATE:
        return getOverviewDate();
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__PARTICIPATIONS:
        return getParticipations();
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
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__PREMIUM:
        setPremium((PgCurrency)newValue);
        return;
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__DEPOSIT_DATE:
        setDepositDate((LocalDate)newValue);
        return;
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__INVESTMENT_PARTS:
        getInvestmentParts().clear();
        getInvestmentParts().addAll((Collection<? extends InvestmentPart>)newValue);
        return;
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__OVERVIEW_DATE:
        setOverviewDate((LocalDate)newValue);
        return;
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__PARTICIPATIONS:
        getParticipations().clear();
        getParticipations().addAll((Collection<? extends Participation>)newValue);
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
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__PREMIUM:
        unsetPremium();
        return;
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__DEPOSIT_DATE:
        unsetDepositDate();
        return;
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__INVESTMENT_PARTS:
        getInvestmentParts().clear();
        return;
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__OVERVIEW_DATE:
        unsetOverviewDate();
        return;
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__PARTICIPATIONS:
        getParticipations().clear();
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
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__PREMIUM:
        return isSetPremium();
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__DEPOSIT_DATE:
        return isSetDepositDate();
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__INVESTMENT_PARTS:
        return investmentParts != null && !investmentParts.isEmpty();
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__OVERVIEW_DATE:
        return isSetOverviewDate();
      case InvestmentInsurancePackage.EXTRA_INVESTMENT__PARTICIPATIONS:
        return participations != null && !participations.isEmpty();
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
    result.append(" (premium: ");
    if (premiumESet) result.append(premium); else result.append("<unset>");
    result.append(", depositDate: ");
    if (depositDateESet) result.append(depositDate); else result.append("<unset>");
    result.append(", overviewDate: ");
    if (overviewDateESet) result.append(overviewDate); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //ExtraInlegImpl
