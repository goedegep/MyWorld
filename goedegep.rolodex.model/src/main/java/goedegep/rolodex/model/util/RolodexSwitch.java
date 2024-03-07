/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import goedegep.rolodex.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see goedegep.rolodex.model.RolodexPackage
 * @generated
 */
public class RolodexSwitch<T1> extends Switch<T1> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static RolodexPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RolodexSwitch() {
    if (modelPackage == null) {
      modelPackage = RolodexPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage) {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T1 doSwitch(int classifierID, EObject theEObject) {
    switch (classifierID) {
    case RolodexPackage.ROLODEX: {
      Rolodex rolodex = (Rolodex) theEObject;
      T1 result = caseRolodex(rolodex);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.COUNTRY: {
      Country country = (Country) theEObject;
      T1 result = caseCountry(country);
      if (result == null)
        result = (T1) caseComparable(country);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.CITY: {
      City city = (City) theEObject;
      T1 result = caseCity(city);
      if (result == null)
        result = (T1) caseComparable(city);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.ADDRESS: {
      Address address = (Address) theEObject;
      T1 result = caseAddress(address);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.PERSON: {
      Person person = (Person) theEObject;
      T1 result = casePerson(person);
      if (result == null)
        result = casePhoneNumberHolder(person);
      if (result == null)
        result = caseAddressHolder(person);
      if (result == null)
        result = caseDescription(person);
      if (result == null)
        result = caseArchive(person);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.INSTITUTION: {
      Institution institution = (Institution) theEObject;
      T1 result = caseInstitution(institution);
      if (result == null)
        result = casePhoneNumberHolder(institution);
      if (result == null)
        result = caseAddressHolder(institution);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.EMPLOYEE: {
      Employee employee = (Employee) theEObject;
      T1 result = caseEmployee(employee);
      if (result == null)
        result = casePhoneNumberHolder(employee);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.PHONE_NUMBER: {
      PhoneNumber phoneNumber = (PhoneNumber) theEObject;
      T1 result = casePhoneNumber(phoneNumber);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.FAMILY: {
      Family family = (Family) theEObject;
      T1 result = caseFamily(family);
      if (result == null)
        result = casePhoneNumberHolder(family);
      if (result == null)
        result = caseAddressHolder(family);
      if (result == null)
        result = caseDescription(family);
      if (result == null)
        result = caseArchive(family);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.COUNTRY_LIST: {
      CountryList countryList = (CountryList) theEObject;
      T1 result = caseCountryList(countryList);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.CITY_LIST: {
      CityList cityList = (CityList) theEObject;
      T1 result = caseCityList(cityList);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.ADDRESS_LIST: {
      AddressList addressList = (AddressList) theEObject;
      T1 result = caseAddressList(addressList);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.PHONE_NUMBER_LIST: {
      PhoneNumberList phoneNumberList = (PhoneNumberList) theEObject;
      T1 result = casePhoneNumberList(phoneNumberList);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.INSTITUTION_LIST: {
      InstitutionList institutionList = (InstitutionList) theEObject;
      T1 result = caseInstitutionList(institutionList);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.EMPLOYEE_LIST: {
      EmployeeList employeeList = (EmployeeList) theEObject;
      T1 result = caseEmployeeList(employeeList);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.PERSON_LIST: {
      PersonList personList = (PersonList) theEObject;
      T1 result = casePersonList(personList);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.FAMILY_LIST: {
      FamilyList familyList = (FamilyList) theEObject;
      T1 result = caseFamilyList(familyList);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.PHONE_NUMBER_HOLDER: {
      PhoneNumberHolder phoneNumberHolder = (PhoneNumberHolder) theEObject;
      T1 result = casePhoneNumberHolder(phoneNumberHolder);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.PHONE: {
      Phone phone = (Phone) theEObject;
      T1 result = casePhone(phone);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.PHONE_LIST: {
      PhoneList phoneList = (PhoneList) theEObject;
      T1 result = casePhoneList(phoneList);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.PHONE_ADDRESS_BOOK: {
      PhoneAddressBook phoneAddressBook = (PhoneAddressBook) theEObject;
      T1 result = casePhoneAddressBook(phoneAddressBook);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY: {
      PhoneAddressBookEntry phoneAddressBookEntry = (PhoneAddressBookEntry) theEObject;
      T1 result = casePhoneAddressBookEntry(phoneAddressBookEntry);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.ADDRESS_HOLDER: {
      AddressHolder addressHolder = (AddressHolder) theEObject;
      T1 result = caseAddressHolder(addressHolder);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.BIRTHDAY: {
      Birthday birthday = (Birthday) theEObject;
      T1 result = caseBirthday(birthday);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.DESCRIPTION: {
      Description description = (Description) theEObject;
      T1 result = caseDescription(description);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.ADDRESS_FOR_PERIOD: {
      AddressForPeriod addressForPeriod = (AddressForPeriod) theEObject;
      T1 result = caseAddressForPeriod(addressForPeriod);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case RolodexPackage.ARCHIVE: {
      Archive archive = (Archive) theEObject;
      T1 result = caseArchive(archive);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    default:
      return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Rolodex</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Rolodex</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseRolodex(Rolodex object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Country</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Country</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseCountry(Country object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>City</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>City</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseCity(City object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Address</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Address</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseAddress(Address object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Person</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Person</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 casePerson(Person object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Institution</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Institution</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseInstitution(Institution object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Employee</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Employee</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseEmployee(Employee object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Phone Number</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Phone Number</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 casePhoneNumber(PhoneNumber object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Family</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Family</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseFamily(Family object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Country List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Country List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseCountryList(CountryList object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>City List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>City List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseCityList(CityList object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Address List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Address List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseAddressList(AddressList object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Phone Number List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Phone Number List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 casePhoneNumberList(PhoneNumberList object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Institution List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Institution List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseInstitutionList(InstitutionList object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Employee List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Employee List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseEmployeeList(EmployeeList object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Person List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Person List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 casePersonList(PersonList object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Family List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Family List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseFamilyList(FamilyList object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Phone Number Holder</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Phone Number Holder</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 casePhoneNumberHolder(PhoneNumberHolder object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Phone</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Phone</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 casePhone(Phone object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Phone List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Phone List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 casePhoneList(PhoneList object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Phone Address Book</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Phone Address Book</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 casePhoneAddressBook(PhoneAddressBook object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Phone Address Book Entry</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Phone Address Book Entry</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 casePhoneAddressBookEntry(PhoneAddressBookEntry object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Address Holder</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Address Holder</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseAddressHolder(AddressHolder object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Birthday</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Birthday</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseBirthday(Birthday object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Description</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Description</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseDescription(Description object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Address For Period</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Address For Period</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseAddressForPeriod(AddressForPeriod object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Archive</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Archive</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T1 caseArchive(Archive object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Comparable</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Comparable</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  @SuppressWarnings("rawtypes")
  public <T> T1 caseComparable(Comparable<T> object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T1 defaultCase(EObject object) {
    return null;
  }

} //RolodexSwitch
