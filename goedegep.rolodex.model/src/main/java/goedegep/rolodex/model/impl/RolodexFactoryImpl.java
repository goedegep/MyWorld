/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import goedegep.rolodex.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RolodexFactoryImpl extends EFactoryImpl implements RolodexFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static RolodexFactory init() {
    try {
      RolodexFactory theRolodexFactory = (RolodexFactory) EPackage.Registry.INSTANCE
          .getEFactory(RolodexPackage.eNS_URI);
      if (theRolodexFactory != null) {
        return theRolodexFactory;
      }
    } catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new RolodexFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RolodexFactoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
    case RolodexPackage.ROLODEX:
      return createRolodex();
    case RolodexPackage.COUNTRY:
      return createCountry();
    case RolodexPackage.CITY:
      return createCity();
    case RolodexPackage.ADDRESS:
      return createAddress();
    case RolodexPackage.PERSON:
      return createPerson();
    case RolodexPackage.INSTITUTION:
      return createInstitution();
    case RolodexPackage.EMPLOYEE:
      return createEmployee();
    case RolodexPackage.PHONE_NUMBER:
      return createPhoneNumber();
    case RolodexPackage.FAMILY:
      return createFamily();
    case RolodexPackage.COUNTRY_LIST:
      return createCountryList();
    case RolodexPackage.CITY_LIST:
      return createCityList();
    case RolodexPackage.ADDRESS_LIST:
      return createAddressList();
    case RolodexPackage.PHONE_NUMBER_LIST:
      return createPhoneNumberList();
    case RolodexPackage.INSTITUTION_LIST:
      return createInstitutionList();
    case RolodexPackage.EMPLOYEE_LIST:
      return createEmployeeList();
    case RolodexPackage.PERSON_LIST:
      return createPersonList();
    case RolodexPackage.FAMILY_LIST:
      return createFamilyList();
    case RolodexPackage.PHONE:
      return createPhone();
    case RolodexPackage.PHONE_LIST:
      return createPhoneList();
    case RolodexPackage.PHONE_ADDRESS_BOOK:
      return createPhoneAddressBook();
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY:
      return createPhoneAddressBookEntry();
    case RolodexPackage.BIRTHDAY:
      return createBirthday();
    case RolodexPackage.ADDRESS_FOR_PERIOD:
      return createAddressForPeriod();
    default:
      throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue) {
    switch (eDataType.getClassifierID()) {
    case RolodexPackage.GENDER:
      return createGenderFromString(eDataType, initialValue);
    case RolodexPackage.CONNECTION_TYPE:
      return createConnectionTypeFromString(eDataType, initialValue);
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY_TYPE:
      return createPhoneAddressBookEntryTypeFromString(eDataType, initialValue);
    default:
      throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue) {
    switch (eDataType.getClassifierID()) {
    case RolodexPackage.GENDER:
      return convertGenderToString(eDataType, instanceValue);
    case RolodexPackage.CONNECTION_TYPE:
      return convertConnectionTypeToString(eDataType, instanceValue);
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY_TYPE:
      return convertPhoneAddressBookEntryTypeToString(eDataType, instanceValue);
    default:
      throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Rolodex createRolodex() {
    RolodexImpl rolodex = new RolodexImpl();
    return rolodex;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Country createCountry() {
    CountryImpl country = new CountryImpl();
    return country;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public City createCity() {
    CityImpl city = new CityImpl();
    return city;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Address createAddress() {
    AddressImpl address = new AddressImpl();
    return address;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Person createPerson() {
    PersonImpl person = new PersonImpl();
    return person;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Institution createInstitution() {
    InstitutionImpl institution = new InstitutionImpl();
    return institution;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Employee createEmployee() {
    EmployeeImpl employee = new EmployeeImpl();
    return employee;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PhoneNumber createPhoneNumber() {
    PhoneNumberImpl phoneNumber = new PhoneNumberImpl();
    return phoneNumber;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Family createFamily() {
    FamilyImpl family = new FamilyImpl();
    return family;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CountryList createCountryList() {
    CountryListImpl countryList = new CountryListImpl();
    return countryList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CityList createCityList() {
    CityListImpl cityList = new CityListImpl();
    return cityList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public AddressList createAddressList() {
    AddressListImpl addressList = new AddressListImpl();
    return addressList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PhoneNumberList createPhoneNumberList() {
    PhoneNumberListImpl phoneNumberList = new PhoneNumberListImpl();
    return phoneNumberList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public InstitutionList createInstitutionList() {
    InstitutionListImpl institutionList = new InstitutionListImpl();
    return institutionList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EmployeeList createEmployeeList() {
    EmployeeListImpl employeeList = new EmployeeListImpl();
    return employeeList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PersonList createPersonList() {
    PersonListImpl personList = new PersonListImpl();
    return personList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FamilyList createFamilyList() {
    FamilyListImpl familyList = new FamilyListImpl();
    return familyList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Phone createPhone() {
    PhoneImpl phone = new PhoneImpl();
    return phone;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PhoneList createPhoneList() {
    PhoneListImpl phoneList = new PhoneListImpl();
    return phoneList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PhoneAddressBook createPhoneAddressBook() {
    PhoneAddressBookImpl phoneAddressBook = new PhoneAddressBookImpl();
    return phoneAddressBook;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PhoneAddressBookEntry createPhoneAddressBookEntry() {
    PhoneAddressBookEntryImpl phoneAddressBookEntry = new PhoneAddressBookEntryImpl();
    return phoneAddressBookEntry;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Birthday createBirthday() {
    BirthdayImpl birthday = new BirthdayImpl();
    return birthday;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public AddressForPeriod createAddressForPeriod() {
    AddressForPeriodImpl addressForPeriod = new AddressForPeriodImpl();
    return addressForPeriod;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Gender createGenderFromString(EDataType eDataType, String initialValue) {
    Gender result = Gender.get(initialValue);
    if (result == null)
      throw new IllegalArgumentException(
          "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertGenderToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConnectionType createConnectionTypeFromString(EDataType eDataType, String initialValue) {
    ConnectionType result = ConnectionType.get(initialValue);
    if (result == null)
      throw new IllegalArgumentException(
          "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertConnectionTypeToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PhoneAddressBookEntryType createPhoneAddressBookEntryTypeFromString(EDataType eDataType, String initialValue) {
    PhoneAddressBookEntryType result = PhoneAddressBookEntryType.get(initialValue);
    if (result == null)
      throw new IllegalArgumentException(
          "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertPhoneAddressBookEntryTypeToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public RolodexPackage getRolodexPackage() {
    return (RolodexPackage) getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static RolodexPackage getPackage() {
    return RolodexPackage.eINSTANCE;
  }

} //RolodexFactoryImpl
