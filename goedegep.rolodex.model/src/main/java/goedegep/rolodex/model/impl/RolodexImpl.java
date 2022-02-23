/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.rolodex.model.AddressList;
import goedegep.rolodex.model.CityList;
import goedegep.rolodex.model.CountryList;
import goedegep.rolodex.model.EmployeeList;
import goedegep.rolodex.model.FamilyList;
import goedegep.rolodex.model.InstitutionList;
import goedegep.rolodex.model.PersonList;
import goedegep.rolodex.model.PhoneList;
import goedegep.rolodex.model.PhoneNumberList;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rolodex</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.RolodexImpl#getCountryList <em>Country List</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.RolodexImpl#getCityList <em>City List</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.RolodexImpl#getAddressList <em>Address List</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.RolodexImpl#getPhoneNumberList <em>Phone Number List</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.RolodexImpl#getInstitutionList <em>Institution List</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.RolodexImpl#getEmployeeList <em>Employee List</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.RolodexImpl#getPersonList <em>Person List</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.RolodexImpl#getFamilyList <em>Family List</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.RolodexImpl#getPhoneList <em>Phone List</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RolodexImpl extends MinimalEObjectImpl.Container implements Rolodex {
  /**
   * The cached value of the '{@link #getCountryList() <em>Country List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCountryList()
   * @generated
   * @ordered
   */
  protected CountryList countryList;

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
   * The cached value of the '{@link #getAddressList() <em>Address List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAddressList()
   * @generated
   * @ordered
   */
  protected AddressList addressList;

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
   * The cached value of the '{@link #getInstitutionList() <em>Institution List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstitutionList()
   * @generated
   * @ordered
   */
  protected InstitutionList institutionList;

  /**
   * The cached value of the '{@link #getEmployeeList() <em>Employee List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEmployeeList()
   * @generated
   * @ordered
   */
  protected EmployeeList employeeList;

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
   * The cached value of the '{@link #getFamilyList() <em>Family List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFamilyList()
   * @generated
   * @ordered
   */
  protected FamilyList familyList;

  /**
   * The cached value of the '{@link #getPhoneList() <em>Phone List</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoneList()
   * @generated
   * @ordered
   */
  protected PhoneList phoneList;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected RolodexImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.ROLODEX;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CountryList getCountryList() {
    return countryList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCountryList(CountryList newCountryList, NotificationChain msgs) {
    CountryList oldCountryList = countryList;
    countryList = newCountryList;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          RolodexPackage.ROLODEX__COUNTRY_LIST, oldCountryList, newCountryList);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCountryList(CountryList newCountryList) {
    if (newCountryList != countryList) {
      NotificationChain msgs = null;
      if (countryList != null)
        msgs = ((InternalEObject) countryList).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__COUNTRY_LIST, null, msgs);
      if (newCountryList != null)
        msgs = ((InternalEObject) newCountryList).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__COUNTRY_LIST, null, msgs);
      msgs = basicSetCountryList(newCountryList, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ROLODEX__COUNTRY_LIST, newCountryList,
          newCountryList));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RolodexPackage.ROLODEX__CITY_LIST,
          oldCityList, newCityList);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCityList(CityList newCityList) {
    if (newCityList != cityList) {
      NotificationChain msgs = null;
      if (cityList != null)
        msgs = ((InternalEObject) cityList).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__CITY_LIST, null, msgs);
      if (newCityList != null)
        msgs = ((InternalEObject) newCityList).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__CITY_LIST, null, msgs);
      msgs = basicSetCityList(newCityList, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, RolodexPackage.ROLODEX__CITY_LIST, newCityList, newCityList));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          RolodexPackage.ROLODEX__ADDRESS_LIST, oldAddressList, newAddressList);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setAddressList(AddressList newAddressList) {
    if (newAddressList != addressList) {
      NotificationChain msgs = null;
      if (addressList != null)
        msgs = ((InternalEObject) addressList).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__ADDRESS_LIST, null, msgs);
      if (newAddressList != null)
        msgs = ((InternalEObject) newAddressList).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__ADDRESS_LIST, null, msgs);
      msgs = basicSetAddressList(newAddressList, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ROLODEX__ADDRESS_LIST, newAddressList,
          newAddressList));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          RolodexPackage.ROLODEX__PHONE_NUMBER_LIST, oldPhoneNumberList, newPhoneNumberList);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPhoneNumberList(PhoneNumberList newPhoneNumberList) {
    if (newPhoneNumberList != phoneNumberList) {
      NotificationChain msgs = null;
      if (phoneNumberList != null)
        msgs = ((InternalEObject) phoneNumberList).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__PHONE_NUMBER_LIST, null, msgs);
      if (newPhoneNumberList != null)
        msgs = ((InternalEObject) newPhoneNumberList).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__PHONE_NUMBER_LIST, null, msgs);
      msgs = basicSetPhoneNumberList(newPhoneNumberList, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ROLODEX__PHONE_NUMBER_LIST,
          newPhoneNumberList, newPhoneNumberList));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public InstitutionList getInstitutionList() {
    return institutionList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetInstitutionList(InstitutionList newInstitutionList, NotificationChain msgs) {
    InstitutionList oldInstitutionList = institutionList;
    institutionList = newInstitutionList;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          RolodexPackage.ROLODEX__INSTITUTION_LIST, oldInstitutionList, newInstitutionList);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInstitutionList(InstitutionList newInstitutionList) {
    if (newInstitutionList != institutionList) {
      NotificationChain msgs = null;
      if (institutionList != null)
        msgs = ((InternalEObject) institutionList).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__INSTITUTION_LIST, null, msgs);
      if (newInstitutionList != null)
        msgs = ((InternalEObject) newInstitutionList).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__INSTITUTION_LIST, null, msgs);
      msgs = basicSetInstitutionList(newInstitutionList, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ROLODEX__INSTITUTION_LIST,
          newInstitutionList, newInstitutionList));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EmployeeList getEmployeeList() {
    return employeeList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetEmployeeList(EmployeeList newEmployeeList, NotificationChain msgs) {
    EmployeeList oldEmployeeList = employeeList;
    employeeList = newEmployeeList;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          RolodexPackage.ROLODEX__EMPLOYEE_LIST, oldEmployeeList, newEmployeeList);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setEmployeeList(EmployeeList newEmployeeList) {
    if (newEmployeeList != employeeList) {
      NotificationChain msgs = null;
      if (employeeList != null)
        msgs = ((InternalEObject) employeeList).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__EMPLOYEE_LIST, null, msgs);
      if (newEmployeeList != null)
        msgs = ((InternalEObject) newEmployeeList).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__EMPLOYEE_LIST, null, msgs);
      msgs = basicSetEmployeeList(newEmployeeList, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ROLODEX__EMPLOYEE_LIST, newEmployeeList,
          newEmployeeList));
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
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          RolodexPackage.ROLODEX__PERSON_LIST, oldPersonList, newPersonList);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPersonList(PersonList newPersonList) {
    if (newPersonList != personList) {
      NotificationChain msgs = null;
      if (personList != null)
        msgs = ((InternalEObject) personList).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__PERSON_LIST, null, msgs);
      if (newPersonList != null)
        msgs = ((InternalEObject) newPersonList).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__PERSON_LIST, null, msgs);
      msgs = basicSetPersonList(newPersonList, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ROLODEX__PERSON_LIST, newPersonList,
          newPersonList));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FamilyList getFamilyList() {
    return familyList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFamilyList(FamilyList newFamilyList, NotificationChain msgs) {
    FamilyList oldFamilyList = familyList;
    familyList = newFamilyList;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          RolodexPackage.ROLODEX__FAMILY_LIST, oldFamilyList, newFamilyList);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFamilyList(FamilyList newFamilyList) {
    if (newFamilyList != familyList) {
      NotificationChain msgs = null;
      if (familyList != null)
        msgs = ((InternalEObject) familyList).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__FAMILY_LIST, null, msgs);
      if (newFamilyList != null)
        msgs = ((InternalEObject) newFamilyList).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__FAMILY_LIST, null, msgs);
      msgs = basicSetFamilyList(newFamilyList, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ROLODEX__FAMILY_LIST, newFamilyList,
          newFamilyList));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PhoneList getPhoneList() {
    return phoneList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPhoneList(PhoneList newPhoneList, NotificationChain msgs) {
    PhoneList oldPhoneList = phoneList;
    phoneList = newPhoneList;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RolodexPackage.ROLODEX__PHONE_LIST,
          oldPhoneList, newPhoneList);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPhoneList(PhoneList newPhoneList) {
    if (newPhoneList != phoneList) {
      NotificationChain msgs = null;
      if (phoneList != null)
        msgs = ((InternalEObject) phoneList).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__PHONE_LIST, null, msgs);
      if (newPhoneList != null)
        msgs = ((InternalEObject) newPhoneList).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.ROLODEX__PHONE_LIST, null, msgs);
      msgs = basicSetPhoneList(newPhoneList, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ROLODEX__PHONE_LIST, newPhoneList,
          newPhoneList));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case RolodexPackage.ROLODEX__COUNTRY_LIST:
      return basicSetCountryList(null, msgs);
    case RolodexPackage.ROLODEX__CITY_LIST:
      return basicSetCityList(null, msgs);
    case RolodexPackage.ROLODEX__ADDRESS_LIST:
      return basicSetAddressList(null, msgs);
    case RolodexPackage.ROLODEX__PHONE_NUMBER_LIST:
      return basicSetPhoneNumberList(null, msgs);
    case RolodexPackage.ROLODEX__INSTITUTION_LIST:
      return basicSetInstitutionList(null, msgs);
    case RolodexPackage.ROLODEX__EMPLOYEE_LIST:
      return basicSetEmployeeList(null, msgs);
    case RolodexPackage.ROLODEX__PERSON_LIST:
      return basicSetPersonList(null, msgs);
    case RolodexPackage.ROLODEX__FAMILY_LIST:
      return basicSetFamilyList(null, msgs);
    case RolodexPackage.ROLODEX__PHONE_LIST:
      return basicSetPhoneList(null, msgs);
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
    case RolodexPackage.ROLODEX__COUNTRY_LIST:
      return getCountryList();
    case RolodexPackage.ROLODEX__CITY_LIST:
      return getCityList();
    case RolodexPackage.ROLODEX__ADDRESS_LIST:
      return getAddressList();
    case RolodexPackage.ROLODEX__PHONE_NUMBER_LIST:
      return getPhoneNumberList();
    case RolodexPackage.ROLODEX__INSTITUTION_LIST:
      return getInstitutionList();
    case RolodexPackage.ROLODEX__EMPLOYEE_LIST:
      return getEmployeeList();
    case RolodexPackage.ROLODEX__PERSON_LIST:
      return getPersonList();
    case RolodexPackage.ROLODEX__FAMILY_LIST:
      return getFamilyList();
    case RolodexPackage.ROLODEX__PHONE_LIST:
      return getPhoneList();
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
    case RolodexPackage.ROLODEX__COUNTRY_LIST:
      setCountryList((CountryList) newValue);
      return;
    case RolodexPackage.ROLODEX__CITY_LIST:
      setCityList((CityList) newValue);
      return;
    case RolodexPackage.ROLODEX__ADDRESS_LIST:
      setAddressList((AddressList) newValue);
      return;
    case RolodexPackage.ROLODEX__PHONE_NUMBER_LIST:
      setPhoneNumberList((PhoneNumberList) newValue);
      return;
    case RolodexPackage.ROLODEX__INSTITUTION_LIST:
      setInstitutionList((InstitutionList) newValue);
      return;
    case RolodexPackage.ROLODEX__EMPLOYEE_LIST:
      setEmployeeList((EmployeeList) newValue);
      return;
    case RolodexPackage.ROLODEX__PERSON_LIST:
      setPersonList((PersonList) newValue);
      return;
    case RolodexPackage.ROLODEX__FAMILY_LIST:
      setFamilyList((FamilyList) newValue);
      return;
    case RolodexPackage.ROLODEX__PHONE_LIST:
      setPhoneList((PhoneList) newValue);
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
    case RolodexPackage.ROLODEX__COUNTRY_LIST:
      setCountryList((CountryList) null);
      return;
    case RolodexPackage.ROLODEX__CITY_LIST:
      setCityList((CityList) null);
      return;
    case RolodexPackage.ROLODEX__ADDRESS_LIST:
      setAddressList((AddressList) null);
      return;
    case RolodexPackage.ROLODEX__PHONE_NUMBER_LIST:
      setPhoneNumberList((PhoneNumberList) null);
      return;
    case RolodexPackage.ROLODEX__INSTITUTION_LIST:
      setInstitutionList((InstitutionList) null);
      return;
    case RolodexPackage.ROLODEX__EMPLOYEE_LIST:
      setEmployeeList((EmployeeList) null);
      return;
    case RolodexPackage.ROLODEX__PERSON_LIST:
      setPersonList((PersonList) null);
      return;
    case RolodexPackage.ROLODEX__FAMILY_LIST:
      setFamilyList((FamilyList) null);
      return;
    case RolodexPackage.ROLODEX__PHONE_LIST:
      setPhoneList((PhoneList) null);
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
    case RolodexPackage.ROLODEX__COUNTRY_LIST:
      return countryList != null;
    case RolodexPackage.ROLODEX__CITY_LIST:
      return cityList != null;
    case RolodexPackage.ROLODEX__ADDRESS_LIST:
      return addressList != null;
    case RolodexPackage.ROLODEX__PHONE_NUMBER_LIST:
      return phoneNumberList != null;
    case RolodexPackage.ROLODEX__INSTITUTION_LIST:
      return institutionList != null;
    case RolodexPackage.ROLODEX__EMPLOYEE_LIST:
      return employeeList != null;
    case RolodexPackage.ROLODEX__PERSON_LIST:
      return personList != null;
    case RolodexPackage.ROLODEX__FAMILY_LIST:
      return familyList != null;
    case RolodexPackage.ROLODEX__PHONE_LIST:
      return phoneList != null;
    }
    return super.eIsSet(featureID);
  }

} //RolodexImpl
