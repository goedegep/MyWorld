/**
 */
package goedegep.finan.investmentinsurance.model.impl;

import goedegep.finan.investmentinsurance.model.InsuranceCompany;
import goedegep.finan.investmentinsurance.model.InvestmentInsurance;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancePackage;
import goedegep.finan.investmentinsurance.model.InvestmentInsurancesData;
import goedegep.rolodex.model.AddressList;
import goedegep.rolodex.model.CityList;
import goedegep.rolodex.model.PersonList;
import goedegep.rolodex.model.PhoneNumberList;
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
 * An implementation of the model object '<em><b>Beleggings Verzekeringen Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancesDataImpl#getInsuranceCompanies <em>Insurance Companies</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancesDataImpl#getInvestmentInsurances <em>Investment Insurances</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancesDataImpl#getPersonList <em>Person List</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancesDataImpl#getAddressList <em>Address List</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancesDataImpl#getCityList <em>City List</em>}</li>
 *   <li>{@link goedegep.finan.investmentinsurance.model.impl.InvestmentInsurancesDataImpl#getPhoneNumberList <em>Phone Number List</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvestmentInsurancesDataImpl extends MinimalEObjectImpl.Container
		implements InvestmentInsurancesData {
	/**
   * The cached value of the '{@link #getInsuranceCompanies() <em>Insurance Companies</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInsuranceCompanies()
   * @generated
   * @ordered
   */
  protected EList<InsuranceCompany> insuranceCompanies;

  /**
   * The cached value of the '{@link #getInvestmentInsurances() <em>Investment Insurances</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvestmentInsurances()
   * @generated
   * @ordered
   */
  protected EList<InvestmentInsurance> investmentInsurances;

  /**
   * The cached value of the '{@link #getPersonList() <em>Person List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPersonList()
   * @generated
   * @ordered
   */
  protected PersonList personList;

  /**
   * This is true if the Person List containment reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean personListESet;

  /**
   * The cached value of the '{@link #getAddressList() <em>Address List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAddressList()
   * @generated
   * @ordered
   */
  protected AddressList addressList;

  /**
   * The cached value of the '{@link #getCityList() <em>City List</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCityList()
   * @generated
   * @ordered
   */
	protected CityList cityList;

	/**
   * The cached value of the '{@link #getPhoneNumberList() <em>Phone Number List</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getPhoneNumberList()
   * @generated
   * @ordered
   */
	protected PhoneNumberList phoneNumberList;

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected InvestmentInsurancesDataImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EClass eStaticClass() {
    return InvestmentInsurancePackage.Literals.INVESTMENT_INSURANCES_DATA;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public EList<InsuranceCompany> getInsuranceCompanies() {
    if (insuranceCompanies == null) {
      insuranceCompanies = new EObjectContainmentEList<InsuranceCompany>(InsuranceCompany.class, this, InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__INSURANCE_COMPANIES);
    }
    return insuranceCompanies;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public EList<InvestmentInsurance> getInvestmentInsurances() {
    if (investmentInsurances == null) {
      investmentInsurances = new EObjectContainmentEList<InvestmentInsurance>(InvestmentInsurance.class, this, InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__INVESTMENT_INSURANCES);
    }
    return investmentInsurances;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PersonList getPersonList() {
    return personList;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPersonList(PersonList newPersonList, NotificationChain msgs) {
    PersonList oldPersonList = personList;
    personList = newPersonList;
    boolean oldPersonListESet = personListESet;
    personListESet = true;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PERSON_LIST, oldPersonList, newPersonList, !oldPersonListESet);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPersonList(PersonList newPersonList) {
    if (newPersonList != personList) {
      NotificationChain msgs = null;
      if (personList != null)
        msgs = ((InternalEObject)personList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PERSON_LIST, null, msgs);
      if (newPersonList != null)
        msgs = ((InternalEObject)newPersonList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PERSON_LIST, null, msgs);
      msgs = basicSetPersonList(newPersonList, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else {
      boolean oldPersonListESet = personListESet;
      personListESet = true;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PERSON_LIST, newPersonList, newPersonList, !oldPersonListESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicUnsetPersonList(NotificationChain msgs) {
    PersonList oldPersonList = personList;
    personList = null;
    boolean oldPersonListESet = personListESet;
    personListESet = false;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PERSON_LIST, oldPersonList, null, oldPersonListESet);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetPersonList() {
    if (personList != null) {
      NotificationChain msgs = null;
      msgs = ((InternalEObject)personList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PERSON_LIST, null, msgs);
      msgs = basicUnsetPersonList(msgs);
      if (msgs != null) msgs.dispatch();
    }
    else {
      boolean oldPersonListESet = personListESet;
      personListESet = false;
      if (eNotificationRequired())
        eNotify(new ENotificationImpl(this, Notification.UNSET, InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PERSON_LIST, null, null, oldPersonListESet));
    }
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public boolean isSetPersonList() {
    return personListESet;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public AddressList getAddressList() {
    return addressList;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAddressList(AddressList newAddressList, NotificationChain msgs) {
    AddressList oldAddressList = addressList;
    addressList = newAddressList;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__ADDRESS_LIST, oldAddressList, newAddressList);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAddressList(AddressList newAddressList) {
    if (newAddressList != addressList) {
      NotificationChain msgs = null;
      if (addressList != null)
        msgs = ((InternalEObject)addressList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__ADDRESS_LIST, null, msgs);
      if (newAddressList != null)
        msgs = ((InternalEObject)newAddressList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__ADDRESS_LIST, null, msgs);
      msgs = basicSetAddressList(newAddressList, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__ADDRESS_LIST, newAddressList, newAddressList));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public CityList getCityList() {
    return cityList;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCityList(CityList newCityList, NotificationChain msgs) {
    CityList oldCityList = cityList;
    cityList = newCityList;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__CITY_LIST, oldCityList, newCityList);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCityList(CityList newCityList) {
    if (newCityList != cityList) {
      NotificationChain msgs = null;
      if (cityList != null)
        msgs = ((InternalEObject)cityList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__CITY_LIST, null, msgs);
      if (newCityList != null)
        msgs = ((InternalEObject)newCityList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__CITY_LIST, null, msgs);
      msgs = basicSetCityList(newCityList, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__CITY_LIST, newCityList, newCityList));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
  public PhoneNumberList getPhoneNumberList() {
    return phoneNumberList;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPhoneNumberList(PhoneNumberList newPhoneNumberList, NotificationChain msgs) {
    PhoneNumberList oldPhoneNumberList = phoneNumberList;
    phoneNumberList = newPhoneNumberList;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PHONE_NUMBER_LIST, oldPhoneNumberList, newPhoneNumberList);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPhoneNumberList(PhoneNumberList newPhoneNumberList) {
    if (newPhoneNumberList != phoneNumberList) {
      NotificationChain msgs = null;
      if (phoneNumberList != null)
        msgs = ((InternalEObject)phoneNumberList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PHONE_NUMBER_LIST, null, msgs);
      if (newPhoneNumberList != null)
        msgs = ((InternalEObject)newPhoneNumberList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PHONE_NUMBER_LIST, null, msgs);
      msgs = basicSetPhoneNumberList(newPhoneNumberList, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PHONE_NUMBER_LIST, newPhoneNumberList, newPhoneNumberList));
  }

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PgCurrency getLastKnownTotalValue() {
		PgCurrency laatstBekendeTotaleWaarde = new PgCurrency(0);

		for (InvestmentInsurance beleggingsVerzekering : getInvestmentInsurances()) {
			PgCurrency laatstBekendeWaarde = beleggingsVerzekering.getLastKnownValue();
			laatstBekendeWaarde = laatstBekendeWaarde.certifyCurrency(PgCurrency.EURO);
			laatstBekendeTotaleWaarde = laatstBekendeTotaleWaarde.add(laatstBekendeWaarde);
		}

		return laatstBekendeTotaleWaarde;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public LocalDate getDateForLastKnownTotalValue() {
	  LocalDate datumLaatstBekendeTotaleWaarde = null;

		for (InvestmentInsurance beleggingsVerzekering : getInvestmentInsurances()) {
		  LocalDate date = beleggingsVerzekering.getDateForLastKnownValue();

			if (datumLaatstBekendeTotaleWaarde == null) {
				datumLaatstBekendeTotaleWaarde = date;
			} else {
				if (date.isBefore(datumLaatstBekendeTotaleWaarde)) {
					datumLaatstBekendeTotaleWaarde = date;
				}
			}
		}

		return datumLaatstBekendeTotaleWaarde;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PgCurrency getTotalPremium() {
		PgCurrency totalePremie = new PgCurrency(0);

		for (InvestmentInsurance beleggingsVerzekering : getInvestmentInsurances()) {
			PgCurrency premie = beleggingsVerzekering.getPremium().certifyCurrency(PgCurrency.EURO);
			totalePremie = totalePremie.add(premie);
		}

		return totalePremie;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public FixedPointValue getAverageTotalReturnOnInvestment() {
		FixedPointValue gemiddeldTotaalRendement = new FixedPointValue(0l);

		PgCurrency totaleWaarde = getLastKnownTotalValue();

		for (InvestmentInsurance beleggingsVerzekering : getInvestmentInsurances()) {
			double contributionFactor = beleggingsVerzekering.getLastKnownValue().getDoubleValue()
					/ totaleWaarde.getDoubleValue();
			//      double contributionFactor = beleggingsVerzekering.getLaatstBekendeWaarde().divide(totaleWaarde).doubleValue();
			FixedPointValue rendement = beleggingsVerzekering.getAverageReturnOnInvestment();
			gemiddeldTotaalRendement = gemiddeldTotaalRendement.add(rendement.multiply(contributionFactor));
		}

		return gemiddeldTotaalRendement;
	}

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__INSURANCE_COMPANIES:
        return ((InternalEList<?>)getInsuranceCompanies()).basicRemove(otherEnd, msgs);
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__INVESTMENT_INSURANCES:
        return ((InternalEList<?>)getInvestmentInsurances()).basicRemove(otherEnd, msgs);
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PERSON_LIST:
        return basicUnsetPersonList(msgs);
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__ADDRESS_LIST:
        return basicSetAddressList(null, msgs);
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__CITY_LIST:
        return basicSetCityList(null, msgs);
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PHONE_NUMBER_LIST:
        return basicSetPhoneNumberList(null, msgs);
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
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__INSURANCE_COMPANIES:
        return getInsuranceCompanies();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__INVESTMENT_INSURANCES:
        return getInvestmentInsurances();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PERSON_LIST:
        return getPersonList();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__ADDRESS_LIST:
        return getAddressList();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__CITY_LIST:
        return getCityList();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PHONE_NUMBER_LIST:
        return getPhoneNumberList();
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
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__INSURANCE_COMPANIES:
        getInsuranceCompanies().clear();
        getInsuranceCompanies().addAll((Collection<? extends InsuranceCompany>)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__INVESTMENT_INSURANCES:
        getInvestmentInsurances().clear();
        getInvestmentInsurances().addAll((Collection<? extends InvestmentInsurance>)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PERSON_LIST:
        setPersonList((PersonList)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__ADDRESS_LIST:
        setAddressList((AddressList)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__CITY_LIST:
        setCityList((CityList)newValue);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PHONE_NUMBER_LIST:
        setPhoneNumberList((PhoneNumberList)newValue);
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
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__INSURANCE_COMPANIES:
        getInsuranceCompanies().clear();
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__INVESTMENT_INSURANCES:
        getInvestmentInsurances().clear();
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PERSON_LIST:
        unsetPersonList();
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__ADDRESS_LIST:
        setAddressList((AddressList)null);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__CITY_LIST:
        setCityList((CityList)null);
        return;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PHONE_NUMBER_LIST:
        setPhoneNumberList((PhoneNumberList)null);
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
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__INSURANCE_COMPANIES:
        return insuranceCompanies != null && !insuranceCompanies.isEmpty();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__INVESTMENT_INSURANCES:
        return investmentInsurances != null && !investmentInsurances.isEmpty();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PERSON_LIST:
        return isSetPersonList();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__ADDRESS_LIST:
        return addressList != null;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__CITY_LIST:
        return cityList != null;
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA__PHONE_NUMBER_LIST:
        return phoneNumberList != null;
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
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA___GET_LAST_KNOWN_TOTAL_VALUE:
        return getLastKnownTotalValue();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA___GET_DATE_FOR_LAST_KNOWN_TOTAL_VALUE:
        return getDateForLastKnownTotalValue();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA___GET_TOTAL_PREMIUM:
        return getTotalPremium();
      case InvestmentInsurancePackage.INVESTMENT_INSURANCES_DATA___GET_AVERAGE_TOTAL_RETURN_ON_INVESTMENT:
        return getAverageTotalReturnOnInvestment();
    }
    return super.eInvoke(operationID, arguments);
  }

} //BeleggingsVerzekeringenDataImpl
