/**
 */
package goedegep.finan.investmentinsurance.model.impl;

import goedegep.finan.investmentinsurance.model.InvestmentFund;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage;
import goedegep.types.model.DateRateTuplet;
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
 * An implementation of the model object '<em><b>Beleggings Fonds</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentFundImpl#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentFundImpl#getFundInformation <em>Fund Information</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentFundImpl#getStockPrices <em>Stock Prices</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvestmentFundImpl extends MinimalEObjectImpl.Container implements InvestmentFund {
	/**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * This is true if the Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean nameESet;

  /**
   * The default value of the '{@link #getFundInformation() <em>Fund Information</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFundInformation()
   * @generated
   * @ordered
   */
  protected static final String FUND_INFORMATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFundInformation() <em>Fund Information</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFundInformation()
   * @generated
   * @ordered
   */
  protected String fundInformation = FUND_INFORMATION_EDEFAULT;

  /**
   * This is true if the Fund Information attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean fundInformationESet;

  /**
   * The cached value of the '{@link #getStockPrices() <em>Stock Prices</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStockPrices()
   * @generated
   * @ordered
   */
  protected EList<DateRateTuplet> stockPrices;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected InvestmentFundImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EClass eStaticClass() {
    return InvestmentInsurancePackage.Literals.INVESTMENT_FUND;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public String getName() {
    return name;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    boolean oldNameESet = nameESet;
    nameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_FUND__NAME, oldName, name, !oldNameESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetName() {
    String oldName = name;
    boolean oldNameESet = nameESet;
    name = NAME_EDEFAULT;
    nameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.INVESTMENT_FUND__NAME, oldName, NAME_EDEFAULT, oldNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetName() {
    return nameESet;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public EList<DateRateTuplet> getStockPrices() {
    if (stockPrices == null) {
      stockPrices = new EObjectContainmentEList<DateRateTuplet>(DateRateTuplet.class, this, InvestmentInsurancePackage.INVESTMENT_FUND__STOCK_PRICES);
    }
    return stockPrices;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public String getFundInformation() {
    return fundInformation;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setFundInformation(String newFundInformation) {
    String oldFundInformation = fundInformation;
    fundInformation = newFundInformation;
    boolean oldFundInformationESet = fundInformationESet;
    fundInformationESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_FUND__FUND_INFORMATION, oldFundInformation, fundInformation, !oldFundInformationESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFundInformation() {
    String oldFundInformation = fundInformation;
    boolean oldFundInformationESet = fundInformationESet;
    fundInformation = FUND_INFORMATION_EDEFAULT;
    fundInformationESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.INVESTMENT_FUND__FUND_INFORMATION, oldFundInformation, FUND_INFORMATION_EDEFAULT, oldFundInformationESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public boolean isSetFundInformation() {
    return fundInformationESet;
  }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PgCurrency getStockPrice(LocalDate date) {
	  for (DateRateTuplet dateRateTuplet : getStockPrices()) {
	    if (dateRateTuplet.getDate().equals(date)) {
	      return dateRateTuplet.getRate();
	    }
	  }

	  return null;
	}

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvestmentInsurancePackage.INVESTMENT_FUND__STOCK_PRICES:
        return ((InternalEList<?>)getStockPrices()).basicRemove(otherEnd, msgs);
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
      case InvestmentInsurancePackage.INVESTMENT_FUND__NAME:
        return getName();
      case InvestmentInsurancePackage.INVESTMENT_FUND__FUND_INFORMATION:
        return getFundInformation();
      case InvestmentInsurancePackage.INVESTMENT_FUND__STOCK_PRICES:
        return getStockPrices();
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
      case InvestmentInsurancePackage.INVESTMENT_FUND__NAME:
        setName((String)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_FUND__FUND_INFORMATION:
        setFundInformation((String)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_FUND__STOCK_PRICES:
        getStockPrices().clear();
        getStockPrices().addAll((Collection<? extends DateRateTuplet>)newValue);
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
      case InvestmentInsurancePackage.INVESTMENT_FUND__NAME:
        unsetName();
        return;
      case InvestmentInsurancePackage.INVESTMENT_FUND__FUND_INFORMATION:
        unsetFundInformation();
        return;
      case InvestmentInsurancePackage.INVESTMENT_FUND__STOCK_PRICES:
        getStockPrices().clear();
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
      case InvestmentInsurancePackage.INVESTMENT_FUND__NAME:
        return isSetName();
      case InvestmentInsurancePackage.INVESTMENT_FUND__FUND_INFORMATION:
        return isSetFundInformation();
      case InvestmentInsurancePackage.INVESTMENT_FUND__STOCK_PRICES:
        return stockPrices != null && !stockPrices.isEmpty();
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
      case InvestmentInsurancePackage.INVESTMENT_FUND___GET_STOCK_PRICE__LOCALDATE:
        return getStockPrice((LocalDate)arguments.get(0));
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
    result.append(" (name: ");
    if (nameESet) result.append(name); else result.append("<unset>");
    result.append(", fundInformation: ");
    if (fundInformationESet) result.append(fundInformation); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //BeleggingsFondsImpl
