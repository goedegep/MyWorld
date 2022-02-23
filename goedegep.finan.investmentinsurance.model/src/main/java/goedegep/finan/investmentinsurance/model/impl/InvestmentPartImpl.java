/**
 */
package goedegep.finan.investmentinsurance.model.impl;

import goedegep.finan.investmentinsurance.model.InvestmentFund;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage;
import goedegep.finan.investmentinsurance.model.InvestmentPart;
import goedegep.util.fixedpointvalue.FixedPointValue;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Beleggingsdeel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentPartImpl#getInvestmentFund <em>Investment Fund</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentPartImpl#getPercentage <em>Percentage</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvestmentPartImpl extends MinimalEObjectImpl.Container implements InvestmentPart {
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
   * The default value of the '{@link #getPercentage() <em>Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getPercentage()
   * @generated
   * @ordered
   */
	protected static final FixedPointValue PERCENTAGE_EDEFAULT = null;

	/**
   * The cached value of the '{@link #getPercentage() <em>Percentage</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getPercentage()
   * @generated
   * @ordered
   */
	protected FixedPointValue percentage = PERCENTAGE_EDEFAULT;

	/**
   * This is true if the Percentage attribute has been set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	protected boolean percentageESet;

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected InvestmentPartImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EClass eStaticClass() {
    return InvestmentInsurancePackage.Literals.INVESTMENT_PART;
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
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, InvestmentInsurancePackage.INVESTMENT_PART__INVESTMENT_FUND, oldInvestmentFund, investmentFund));
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
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_PART__INVESTMENT_FUND, oldInvestmentFund, investmentFund));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public FixedPointValue getPercentage() {
    return percentage;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setPercentage(FixedPointValue newPercentage) {
    FixedPointValue oldPercentage = percentage;
    percentage = newPercentage;
    boolean oldPercentageESet = percentageESet;
    percentageESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_PART__PERCENTAGE, oldPercentage, percentage, !oldPercentageESet));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void unsetPercentage() {
    FixedPointValue oldPercentage = percentage;
    boolean oldPercentageESet = percentageESet;
    percentage = PERCENTAGE_EDEFAULT;
    percentageESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.INVESTMENT_PART__PERCENTAGE, oldPercentage, PERCENTAGE_EDEFAULT, oldPercentageESet));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetPercentage() {
    return percentageESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case InvestmentInsurancePackage.INVESTMENT_PART__INVESTMENT_FUND:
        if (resolve) return getInvestmentFund();
        return basicGetInvestmentFund();
      case InvestmentInsurancePackage.INVESTMENT_PART__PERCENTAGE:
        return getPercentage();
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
      case InvestmentInsurancePackage.INVESTMENT_PART__INVESTMENT_FUND:
        setInvestmentFund((InvestmentFund)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_PART__PERCENTAGE:
        setPercentage((FixedPointValue)newValue);
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
      case InvestmentInsurancePackage.INVESTMENT_PART__INVESTMENT_FUND:
        setInvestmentFund((InvestmentFund)null);
        return;
      case InvestmentInsurancePackage.INVESTMENT_PART__PERCENTAGE:
        unsetPercentage();
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
      case InvestmentInsurancePackage.INVESTMENT_PART__INVESTMENT_FUND:
        return investmentFund != null;
      case InvestmentInsurancePackage.INVESTMENT_PART__PERCENTAGE:
        return isSetPercentage();
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
    result.append(" (percentage: ");
    if (percentageESet) result.append(percentage); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //BeleggingsdeelImpl
