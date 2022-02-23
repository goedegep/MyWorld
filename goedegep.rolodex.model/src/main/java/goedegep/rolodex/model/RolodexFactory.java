/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.rolodex.model.RolodexPackage
 * @generated
 */
public interface RolodexFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  RolodexFactory eINSTANCE = goedegep.rolodex.model.impl.RolodexFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Rolodex</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Rolodex</em>'.
   * @generated
   */
  Rolodex createRolodex();

  /**
   * Returns a new object of class '<em>Country</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Country</em>'.
   * @generated
   */
  Country createCountry();

  /**
   * Returns a new object of class '<em>City</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>City</em>'.
   * @generated
   */
  City createCity();

  /**
   * Returns a new object of class '<em>Address</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Address</em>'.
   * @generated
   */
  Address createAddress();

  /**
   * Returns a new object of class '<em>Person</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Person</em>'.
   * @generated
   */
  Person createPerson();

  /**
   * Returns a new object of class '<em>Institution</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Institution</em>'.
   * @generated
   */
  Institution createInstitution();

  /**
   * Returns a new object of class '<em>Employee</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Employee</em>'.
   * @generated
   */
  Employee createEmployee();

  /**
   * Returns a new object of class '<em>Phone Number</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Phone Number</em>'.
   * @generated
   */
  PhoneNumber createPhoneNumber();

  /**
   * Returns a new object of class '<em>Family</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Family</em>'.
   * @generated
   */
  Family createFamily();

  /**
   * Returns a new object of class '<em>Country List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Country List</em>'.
   * @generated
   */
  CountryList createCountryList();

  /**
   * Returns a new object of class '<em>City List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>City List</em>'.
   * @generated
   */
  CityList createCityList();

  /**
   * Returns a new object of class '<em>Address List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Address List</em>'.
   * @generated
   */
  AddressList createAddressList();

  /**
   * Returns a new object of class '<em>Phone Number List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Phone Number List</em>'.
   * @generated
   */
  PhoneNumberList createPhoneNumberList();

  /**
   * Returns a new object of class '<em>Institution List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Institution List</em>'.
   * @generated
   */
  InstitutionList createInstitutionList();

  /**
   * Returns a new object of class '<em>Employee List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Employee List</em>'.
   * @generated
   */
  EmployeeList createEmployeeList();

  /**
   * Returns a new object of class '<em>Person List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Person List</em>'.
   * @generated
   */
  PersonList createPersonList();

  /**
   * Returns a new object of class '<em>Family List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Family List</em>'.
   * @generated
   */
  FamilyList createFamilyList();

  /**
   * Returns a new object of class '<em>Phone</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Phone</em>'.
   * @generated
   */
  Phone createPhone();

  /**
   * Returns a new object of class '<em>Phone List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Phone List</em>'.
   * @generated
   */
  PhoneList createPhoneList();

  /**
   * Returns a new object of class '<em>Phone Address Book</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Phone Address Book</em>'.
   * @generated
   */
  PhoneAddressBook createPhoneAddressBook();

  /**
   * Returns a new object of class '<em>Phone Address Book Entry</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Phone Address Book Entry</em>'.
   * @generated
   */
  PhoneAddressBookEntry createPhoneAddressBookEntry();

  /**
   * Returns a new object of class '<em>Birthday</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Birthday</em>'.
   * @generated
   */
  Birthday createBirthday();

  /**
   * Returns a new object of class '<em>Address For Period</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Address For Period</em>'.
   * @generated
   */
  AddressForPeriod createAddressForPeriod();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  RolodexPackage getRolodexPackage();

} //RolodexFactory
