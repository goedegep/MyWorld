/**
 */
package goedegep.finan.investmentinsurance.model.impl;

import goedegep.finan.investmentinsurance.model.InsuranceCompany;
import goedegep.finan.investmentinsurance.model.InvestmentFund;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage;
import goedegep.rolodex.model.impl.InstitutionImpl;

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
 * An implementation of the model object '<em><b>Maatschappij</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InsuranceCompanyImpl#getInvestmentFunds <em>Investment Funds</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InsuranceCompanyImpl#getDepartment <em>Department</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InsuranceCompanyImpl#getWebsite <em>Website</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InsuranceCompanyImpl extends InstitutionImpl implements InsuranceCompany {
	/**
   * The cached value of the '{@link #getInvestmentFunds() <em>Investment Funds</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvestmentFunds()
   * @generated
   * @ordered
   */
  protected EList<InvestmentFund> investmentFunds;

  /**
   * The default value of the '{@link #getDepartment() <em>Department</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDepartment()
   * @generated
   * @ordered
   */
  protected static final String DEPARTMENT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDepartment() <em>Department</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDepartment()
   * @generated
   * @ordered
   */
  protected String department = DEPARTMENT_EDEFAULT;

  /**
   * This is true if the Department attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean departmentESet;

  /**
   * The default value of the '{@link #getWebsite() <em>Website</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getWebsite()
   * @generated
   * @ordered
   */
	protected static final String WEBSITE_EDEFAULT = null;

	/**
   * The cached value of the '{@link #getWebsite() <em>Website</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getWebsite()
   * @generated
   * @ordered
   */
	protected String website = WEBSITE_EDEFAULT;

	/**
   * This is true if the Website attribute has been set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	protected boolean websiteESet;

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected InsuranceCompanyImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EClass eStaticClass() {
    return InvestmentInsurancePackage.Literals.INSURANCE_COMPANY;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public EList<InvestmentFund> getInvestmentFunds() {
    if (investmentFunds == null) {
      investmentFunds = new EObjectContainmentEList<InvestmentFund>(InvestmentFund.class, this, InvestmentInsurancePackage.INSURANCE_COMPANY__INVESTMENT_FUNDS);
    }
    return investmentFunds;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public String getDepartment() {
    return department;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setDepartment(String newDepartment) {
    String oldDepartment = department;
    department = newDepartment;
    boolean oldDepartmentESet = departmentESet;
    departmentESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INSURANCE_COMPANY__DEPARTMENT, oldDepartment, department, !oldDepartmentESet));
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDepartment() {
    String oldDepartment = department;
    boolean oldDepartmentESet = departmentESet;
    department = DEPARTMENT_EDEFAULT;
    departmentESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.INSURANCE_COMPANY__DEPARTMENT, oldDepartment, DEPARTMENT_EDEFAULT, oldDepartmentESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetDepartment() {
    return departmentESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public String getWebsite() {
    return website;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void setWebsite(String newWebsite) {
    String oldWebsite = website;
    website = newWebsite;
    boolean oldWebsiteESet = websiteESet;
    websiteESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INSURANCE_COMPANY__WEBSITE, oldWebsite, website, !oldWebsiteESet));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public void unsetWebsite() {
    String oldWebsite = website;
    boolean oldWebsiteESet = websiteESet;
    website = WEBSITE_EDEFAULT;
    websiteESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.INSURANCE_COMPANY__WEBSITE, oldWebsite, WEBSITE_EDEFAULT, oldWebsiteESet));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetWebsite() {
    return websiteESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvestmentInsurancePackage.INSURANCE_COMPANY__INVESTMENT_FUNDS:
        return ((InternalEList<?>)getInvestmentFunds()).basicRemove(otherEnd, msgs);
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
      case InvestmentInsurancePackage.INSURANCE_COMPANY__INVESTMENT_FUNDS:
        return getInvestmentFunds();
      case InvestmentInsurancePackage.INSURANCE_COMPANY__DEPARTMENT:
        return getDepartment();
      case InvestmentInsurancePackage.INSURANCE_COMPANY__WEBSITE:
        return getWebsite();
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
      case InvestmentInsurancePackage.INSURANCE_COMPANY__INVESTMENT_FUNDS:
        getInvestmentFunds().clear();
        getInvestmentFunds().addAll((Collection<? extends InvestmentFund>)newValue);
        return;
      case InvestmentInsurancePackage.INSURANCE_COMPANY__DEPARTMENT:
        setDepartment((String)newValue);
        return;
      case InvestmentInsurancePackage.INSURANCE_COMPANY__WEBSITE:
        setWebsite((String)newValue);
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
      case InvestmentInsurancePackage.INSURANCE_COMPANY__INVESTMENT_FUNDS:
        getInvestmentFunds().clear();
        return;
      case InvestmentInsurancePackage.INSURANCE_COMPANY__DEPARTMENT:
        unsetDepartment();
        return;
      case InvestmentInsurancePackage.INSURANCE_COMPANY__WEBSITE:
        unsetWebsite();
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
      case InvestmentInsurancePackage.INSURANCE_COMPANY__INVESTMENT_FUNDS:
        return investmentFunds != null && !investmentFunds.isEmpty();
      case InvestmentInsurancePackage.INSURANCE_COMPANY__DEPARTMENT:
        return isSetDepartment();
      case InvestmentInsurancePackage.INSURANCE_COMPANY__WEBSITE:
        return isSetWebsite();
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
    result.append(" (department: ");
    if (departmentESet) result.append(department); else result.append("<unset>");
    result.append(", website: ");
    if (websiteESet) result.append(website); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //MaatschappijImpl
