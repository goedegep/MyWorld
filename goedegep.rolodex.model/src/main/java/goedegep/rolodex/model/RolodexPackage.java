/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import goedegep.types.model.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see goedegep.rolodex.model.RolodexFactory
 * @model kind="package"
 * @generated
 */
public interface RolodexPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "model";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://goedegep.rolodex/model";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "rolodex";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  RolodexPackage eINSTANCE = goedegep.rolodex.model.impl.RolodexPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.RolodexImpl <em>Rolodex</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.RolodexImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getRolodex()
   * @generated
   */
  int ROLODEX = 0;

  /**
   * The feature id for the '<em><b>Country List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLODEX__COUNTRY_LIST = 0;

  /**
   * The feature id for the '<em><b>City List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLODEX__CITY_LIST = 1;

  /**
   * The feature id for the '<em><b>Address List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLODEX__ADDRESS_LIST = 2;

  /**
   * The feature id for the '<em><b>Phone Number List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLODEX__PHONE_NUMBER_LIST = 3;

  /**
   * The feature id for the '<em><b>Institution List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLODEX__INSTITUTION_LIST = 4;

  /**
   * The feature id for the '<em><b>Employee List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLODEX__EMPLOYEE_LIST = 5;

  /**
   * The feature id for the '<em><b>Person List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLODEX__PERSON_LIST = 6;

  /**
   * The feature id for the '<em><b>Family List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLODEX__FAMILY_LIST = 7;

  /**
   * The feature id for the '<em><b>Phone List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLODEX__PHONE_LIST = 8;

  /**
   * The number of structural features of the '<em>Rolodex</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLODEX_FEATURE_COUNT = 9;

  /**
   * The number of operations of the '<em>Rolodex</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLODEX_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.CountryImpl <em>Country</em>}' class.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.CountryImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getCountry()
   * @generated
   */
  int COUNTRY = 1;

  /**
   * The feature id for the '<em><b>Country Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COUNTRY__COUNTRY_NAME = TypesPackage.COMPARABLE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Country</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COUNTRY_FEATURE_COUNT = TypesPackage.COMPARABLE_FEATURE_COUNT + 1;

  /**
   * The number of operations of the '<em>Country</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COUNTRY_OPERATION_COUNT = TypesPackage.COMPARABLE_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.CityImpl <em>City</em>}' class.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.CityImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getCity()
   * @generated
   */
  int CITY = 2;

  /**
   * The feature id for the '<em><b>Country</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CITY__COUNTRY = TypesPackage.COMPARABLE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>City Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CITY__CITY_NAME = TypesPackage.COMPARABLE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CITY__ID = TypesPackage.COMPARABLE_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>City</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CITY_FEATURE_COUNT = TypesPackage.COMPARABLE_FEATURE_COUNT + 3;

  /**
   * The number of operations of the '<em>City</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CITY_OPERATION_COUNT = TypesPackage.COMPARABLE_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.AddressImpl <em>Address</em>}' class.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.AddressImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getAddress()
   * @generated
   */
  int ADDRESS = 3;

  /**
   * The feature id for the '<em><b>Street Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS__STREET_NAME = 0;

  /**
   * The feature id for the '<em><b>House Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS__HOUSE_NUMBER = 1;

  /**
   * The feature id for the '<em><b>House Number Extension</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS__HOUSE_NUMBER_EXTENSION = 2;

  /**
   * The feature id for the '<em><b>City</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS__CITY = 3;

  /**
   * The feature id for the '<em><b>PO Box</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS__PO_BOX = 4;

  /**
   * The feature id for the '<em><b>Postal Code</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS__POSTAL_CODE = 5;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS__ID = 6;

  /**
   * The number of structural features of the '<em>Address</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_FEATURE_COUNT = 7;

  /**
   * The number of operations of the '<em>Address</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.PersonImpl <em>Person</em>}' class.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.PersonImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPerson()
   * @generated
   */
  int PERSON = 4;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.InstitutionImpl <em>Institution</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.InstitutionImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getInstitution()
   * @generated
   */
  int INSTITUTION = 5;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.EmployeeImpl <em>Employee</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.EmployeeImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getEmployee()
   * @generated
   */
  int EMPLOYEE = 6;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.PhoneNumberImpl <em>Phone Number</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.PhoneNumberImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneNumber()
   * @generated
   */
  int PHONE_NUMBER = 7;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.FamilyImpl <em>Family</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.FamilyImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getFamily()
   * @generated
   */
  int FAMILY = 8;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.CountryListImpl <em>Country List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.CountryListImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getCountryList()
   * @generated
   */
  int COUNTRY_LIST = 9;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.CityListImpl <em>City List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.CityListImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getCityList()
   * @generated
   */
  int CITY_LIST = 10;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.AddressListImpl <em>Address List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.AddressListImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getAddressList()
   * @generated
   */
  int ADDRESS_LIST = 11;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.PhoneNumberListImpl <em>Phone Number List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.PhoneNumberListImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneNumberList()
   * @generated
   */
  int PHONE_NUMBER_LIST = 12;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.InstitutionListImpl <em>Institution List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.InstitutionListImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getInstitutionList()
   * @generated
   */
  int INSTITUTION_LIST = 13;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.EmployeeListImpl <em>Employee List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.EmployeeListImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getEmployeeList()
   * @generated
   */
  int EMPLOYEE_LIST = 14;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.PersonListImpl <em>Person List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.PersonListImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPersonList()
   * @generated
   */
  int PERSON_LIST = 15;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.FamilyListImpl <em>Family List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.FamilyListImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getFamilyList()
   * @generated
   */
  int FAMILY_LIST = 16;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.PhoneNumberHolder <em>Phone Number Holder</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.PhoneNumberHolder
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneNumberHolder()
   * @generated
   */
  int PHONE_NUMBER_HOLDER = 17;

  /**
   * The feature id for the '<em><b>Phone Numbers</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_NUMBER_HOLDER__PHONE_NUMBERS = 0;

  /**
   * The number of structural features of the '<em>Phone Number Holder</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_NUMBER_HOLDER_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Phone Number Holder</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_NUMBER_HOLDER_OPERATION_COUNT = 0;

  /**
   * The feature id for the '<em><b>Phone Numbers</b></em>' reference list.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__PHONE_NUMBERS = PHONE_NUMBER_HOLDER__PHONE_NUMBERS;

  /**
   * The feature id for the '<em><b>Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__ADDRESS = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Previous Addresses</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__PREVIOUS_ADDRESSES = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__DESCRIPTION = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Archived</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__ARCHIVED = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Firstname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__FIRSTNAME = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Infix</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__INFIX = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>Surname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__SURNAME = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 6;

  /**
   * The feature id for the '<em><b>Initials</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__INITIALS = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 7;

  /**
   * The feature id for the '<em><b>Gender</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__GENDER = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 8;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__ID = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 9;

  /**
   * The feature id for the '<em><b>Family</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__FAMILY = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 10;

  /**
   * The feature id for the '<em><b>Birthday</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON__BIRTHDAY = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 11;

  /**
   * The number of structural features of the '<em>Person</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON_FEATURE_COUNT = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 12;

  /**
   * The operation id for the '<em>Get Name</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON___GET_NAME = PHONE_NUMBER_HOLDER_OPERATION_COUNT + 0;

  /**
   * The operation id for the '<em>Retrieve Address</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON___RETRIEVE_ADDRESS = PHONE_NUMBER_HOLDER_OPERATION_COUNT + 1;

  /**
   * The number of operations of the '<em>Person</em>' class.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON_OPERATION_COUNT = PHONE_NUMBER_HOLDER_OPERATION_COUNT + 2;

  /**
   * The feature id for the '<em><b>Phone Numbers</b></em>' reference list.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTITUTION__PHONE_NUMBERS = PHONE_NUMBER_HOLDER__PHONE_NUMBERS;

  /**
   * The feature id for the '<em><b>Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTITUTION__ADDRESS = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Previous Addresses</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTITUTION__PREVIOUS_ADDRESSES = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTITUTION__NAME = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Mailing Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTITUTION__MAILING_ADDRESS = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Institution</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTITUTION_FEATURE_COUNT = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 4;

  /**
   * The number of operations of the '<em>Institution</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTITUTION_OPERATION_COUNT = PHONE_NUMBER_HOLDER_OPERATION_COUNT + 0;

  /**
   * The feature id for the '<em><b>Phone Numbers</b></em>' reference list.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EMPLOYEE__PHONE_NUMBERS = PHONE_NUMBER_HOLDER__PHONE_NUMBERS;

  /**
   * The feature id for the '<em><b>Institution</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EMPLOYEE__INSTITUTION = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Person</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EMPLOYEE__PERSON = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Employee</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EMPLOYEE_FEATURE_COUNT = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 2;

  /**
   * The number of operations of the '<em>Employee</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EMPLOYEE_OPERATION_COUNT = PHONE_NUMBER_HOLDER_OPERATION_COUNT + 0;

  /**
   * The feature id for the '<em><b>Phone Number</b></em>' attribute.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_NUMBER__PHONE_NUMBER = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_NUMBER__DESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>Connection Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_NUMBER__CONNECTION_TYPE = 2;

  /**
   * The feature id for the '<em><b>Number Holders</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_NUMBER__NUMBER_HOLDERS = 3;

  /**
   * The number of structural features of the '<em>Phone Number</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_NUMBER_FEATURE_COUNT = 4;

  /**
   * The number of operations of the '<em>Phone Number</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_NUMBER_OPERATION_COUNT = 0;

  /**
   * The feature id for the '<em><b>Phone Numbers</b></em>' reference list.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__PHONE_NUMBERS = PHONE_NUMBER_HOLDER__PHONE_NUMBERS;

  /**
   * The feature id for the '<em><b>Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__ADDRESS = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Previous Addresses</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__PREVIOUS_ADDRESSES = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__DESCRIPTION = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Archived</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__ARCHIVED = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Family Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__FAMILY_TITLE = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Family Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__FAMILY_NAME = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>Members</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY__MEMBERS = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 6;

  /**
   * The number of structural features of the '<em>Family</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY_FEATURE_COUNT = PHONE_NUMBER_HOLDER_FEATURE_COUNT + 7;

  /**
   * The number of operations of the '<em>Family</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY_OPERATION_COUNT = PHONE_NUMBER_HOLDER_OPERATION_COUNT + 0;

  /**
   * The feature id for the '<em><b>Countries</b></em>' containment reference list.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COUNTRY_LIST__COUNTRIES = 0;

  /**
   * The number of structural features of the '<em>Country List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COUNTRY_LIST_FEATURE_COUNT = 1;

  /**
   * The operation id for the '<em>Add Country</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COUNTRY_LIST___ADD_COUNTRY__STRING = 0;

  /**
   * The operation id for the '<em>Get Country</em>' operation.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COUNTRY_LIST___GET_COUNTRY__STRING = 1;

  /**
   * The operation id for the '<em>Remove Country</em>' operation.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COUNTRY_LIST___REMOVE_COUNTRY__COUNTRY = 2;

  /**
   * The number of operations of the '<em>Country List</em>' class.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COUNTRY_LIST_OPERATION_COUNT = 3;

  /**
   * The feature id for the '<em><b>Cities</b></em>' containment reference list.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CITY_LIST__CITIES = 0;

  /**
   * The number of structural features of the '<em>City List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CITY_LIST_FEATURE_COUNT = 1;

  /**
   * The operation id for the '<em>Find City By Id</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CITY_LIST___FIND_CITY_BY_ID__STRING = 0;

  /**
   * The operation id for the '<em>Add City</em>' operation.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CITY_LIST___ADD_CITY__STRING_COUNTRY = 1;

  /**
   * The operation id for the '<em>Get City</em>' operation.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CITY_LIST___GET_CITY__STRING_COUNTRY = 2;

  /**
   * The operation id for the '<em>Get City</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CITY_LIST___GET_CITY__STRING = 3;

  /**
   * The number of operations of the '<em>City List</em>' class.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CITY_LIST_OPERATION_COUNT = 4;

  /**
   * The feature id for the '<em><b>Addresses</b></em>' containment reference list.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_LIST__ADDRESSES = 0;

  /**
   * The number of structural features of the '<em>Address List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_LIST_FEATURE_COUNT = 1;

  /**
   * The operation id for the '<em>Find Address By Id</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_LIST___FIND_ADDRESS_BY_ID__STRING = 0;

  /**
   * The operation id for the '<em>Find Address By Postal Code</em>' operation.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_LIST___FIND_ADDRESS_BY_POSTAL_CODE__STRING_INTEGER_STRING = 1;

  /**
   * The operation id for the '<em>Get Address</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_LIST___GET_ADDRESS__STRING_INTEGER_STRING_STRING_STRING = 2;

  /**
   * The operation id for the '<em>Get Address</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_LIST___GET_ADDRESS__STRING_INTEGER_STRING_STRING = 3;

  /**
   * The number of operations of the '<em>Address List</em>' class.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_LIST_OPERATION_COUNT = 4;

  /**
   * The feature id for the '<em><b>Phone Numbers</b></em>' containment reference list.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_NUMBER_LIST__PHONE_NUMBERS = 0;

  /**
   * The number of structural features of the '<em>Phone Number List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_NUMBER_LIST_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Phone Number List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_NUMBER_LIST_OPERATION_COUNT = 0;

  /**
   * The feature id for the '<em><b>Institutions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTITUTION_LIST__INSTITUTIONS = 0;

  /**
   * The number of structural features of the '<em>Institution List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTITUTION_LIST_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Institution List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTITUTION_LIST_OPERATION_COUNT = 0;

  /**
   * The feature id for the '<em><b>Employees</b></em>' containment reference list.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EMPLOYEE_LIST__EMPLOYEES = 0;

  /**
   * The number of structural features of the '<em>Employee List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EMPLOYEE_LIST_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Employee List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EMPLOYEE_LIST_OPERATION_COUNT = 0;

  /**
   * The feature id for the '<em><b>Persons</b></em>' containment reference list.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON_LIST__PERSONS = 0;

  /**
   * The number of structural features of the '<em>Person List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON_LIST_FEATURE_COUNT = 1;

  /**
   * The operation id for the '<em>Find Person By Id</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON_LIST___FIND_PERSON_BY_ID__STRING = 0;

  /**
   * The number of operations of the '<em>Person List</em>' class.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERSON_LIST_OPERATION_COUNT = 1;

  /**
   * The feature id for the '<em><b>Families</b></em>' containment reference list.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY_LIST__FAMILIES = 0;

  /**
   * The number of structural features of the '<em>Family List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY_LIST_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Family List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FAMILY_LIST_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.PhoneImpl <em>Phone</em>}' class.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.PhoneImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhone()
   * @generated
   */
  int PHONE = 18;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE__DESCRIPTION = 0;

  /**
   * The feature id for the '<em><b>Phone Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE__PHONE_TYPE = 1;

  /**
   * The feature id for the '<em><b>Phone Address Book</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE__PHONE_ADDRESS_BOOK = 2;

  /**
   * The number of structural features of the '<em>Phone</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Phone</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.PhoneListImpl <em>Phone List</em>}' class.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.PhoneListImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneList()
   * @generated
   */
  int PHONE_LIST = 19;

  /**
   * The feature id for the '<em><b>Phones</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_LIST__PHONES = 0;

  /**
   * The number of structural features of the '<em>Phone List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_LIST_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Phone List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_LIST_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.PhoneAddressBookImpl <em>Phone Address Book</em>}' class.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.PhoneAddressBookImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneAddressBook()
   * @generated
   */
  int PHONE_ADDRESS_BOOK = 20;

  /**
   * The feature id for the '<em><b>Entries</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_ADDRESS_BOOK__ENTRIES = 0;

  /**
   * The number of structural features of the '<em>Phone Address Book</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_ADDRESS_BOOK_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Phone Address Book</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_ADDRESS_BOOK_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.PhoneAddressBookEntryImpl <em>Phone Address Book Entry</em>}' class.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.PhoneAddressBookEntryImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneAddressBookEntry()
   * @generated
   */
  int PHONE_ADDRESS_BOOK_ENTRY = 21;

  /**
   * The feature id for the '<em><b>Entry Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_ADDRESS_BOOK_ENTRY__ENTRY_NAME = 0;

  /**
   * The feature id for the '<em><b>Phone Number</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_ADDRESS_BOOK_ENTRY__PHONE_NUMBER = 1;

  /**
   * The feature id for the '<em><b>Entry Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_ADDRESS_BOOK_ENTRY__ENTRY_TYPE = 2;

  /**
   * The number of structural features of the '<em>Phone Address Book Entry</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_ADDRESS_BOOK_ENTRY_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Phone Address Book Entry</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PHONE_ADDRESS_BOOK_ENTRY_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.AddressHolder <em>Address Holder</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.AddressHolder
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getAddressHolder()
   * @generated
   */
  int ADDRESS_HOLDER = 22;

  /**
   * The feature id for the '<em><b>Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_HOLDER__ADDRESS = 0;

  /**
   * The feature id for the '<em><b>Previous Addresses</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_HOLDER__PREVIOUS_ADDRESSES = 1;

  /**
   * The number of structural features of the '<em>Address Holder</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_HOLDER_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Address Holder</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_HOLDER_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.BirthdayImpl <em>Birthday</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.BirthdayImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getBirthday()
   * @generated
   */
  int BIRTHDAY = 23;

  /**
   * The feature id for the '<em><b>Month</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BIRTHDAY__MONTH = 0;

  /**
   * The feature id for the '<em><b>Day</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BIRTHDAY__DAY = 1;

  /**
   * The feature id for the '<em><b>Year</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BIRTHDAY__YEAR = 2;

  /**
   * The number of structural features of the '<em>Birthday</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BIRTHDAY_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Birthday</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BIRTHDAY_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.Description <em>Description</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.Description
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getDescription()
   * @generated
   */
  int DESCRIPTION = 24;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIPTION__DESCRIPTION = 0;

  /**
   * The number of structural features of the '<em>Description</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIPTION_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Description</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIPTION_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.impl.AddressForPeriodImpl <em>Address For Period</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.impl.AddressForPeriodImpl
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getAddressForPeriod()
   * @generated
   */
  int ADDRESS_FOR_PERIOD = 25;

  /**
   * The feature id for the '<em><b>From Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_FOR_PERIOD__FROM_DATE = 0;

  /**
   * The feature id for the '<em><b>Untill Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_FOR_PERIOD__UNTILL_DATE = 1;

  /**
   * The feature id for the '<em><b>Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_FOR_PERIOD__ADDRESS = 2;

  /**
   * The number of structural features of the '<em>Address For Period</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_FOR_PERIOD_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Address For Period</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDRESS_FOR_PERIOD_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.Archive <em>Archive</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.Archive
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getArchive()
   * @generated
   */
  int ARCHIVE = 26;

  /**
   * The feature id for the '<em><b>Archived</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARCHIVE__ARCHIVED = 0;

  /**
   * The number of structural features of the '<em>Archive</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARCHIVE_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Archive</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARCHIVE_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.Gender <em>Gender</em>}' enum.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.Gender
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getGender()
   * @generated
   */
  int GENDER = 27;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.ConnectionType <em>Connection Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.ConnectionType
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getConnectionType()
   * @generated
   */
  int CONNECTION_TYPE = 28;

  /**
   * The meta object id for the '{@link goedegep.rolodex.model.PhoneAddressBookEntryType <em>Phone Address Book Entry Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.rolodex.model.PhoneAddressBookEntryType
   * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneAddressBookEntryType()
   * @generated
   */
  int PHONE_ADDRESS_BOOK_ENTRY_TYPE = 29;

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.Rolodex <em>Rolodex</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Rolodex</em>'.
   * @see goedegep.rolodex.model.Rolodex
   * @generated
   */
  EClass getRolodex();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.rolodex.model.Rolodex#getCountryList <em>Country List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Country List</em>'.
   * @see goedegep.rolodex.model.Rolodex#getCountryList()
   * @see #getRolodex()
   * @generated
   */
  EReference getRolodex_CountryList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.rolodex.model.Rolodex#getCityList <em>City List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>City List</em>'.
   * @see goedegep.rolodex.model.Rolodex#getCityList()
   * @see #getRolodex()
   * @generated
   */
  EReference getRolodex_CityList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.rolodex.model.Rolodex#getAddressList <em>Address List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Address List</em>'.
   * @see goedegep.rolodex.model.Rolodex#getAddressList()
   * @see #getRolodex()
   * @generated
   */
  EReference getRolodex_AddressList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.rolodex.model.Rolodex#getPhoneNumberList <em>Phone Number List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Phone Number List</em>'.
   * @see goedegep.rolodex.model.Rolodex#getPhoneNumberList()
   * @see #getRolodex()
   * @generated
   */
  EReference getRolodex_PhoneNumberList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.rolodex.model.Rolodex#getInstitutionList <em>Institution List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Institution List</em>'.
   * @see goedegep.rolodex.model.Rolodex#getInstitutionList()
   * @see #getRolodex()
   * @generated
   */
  EReference getRolodex_InstitutionList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.rolodex.model.Rolodex#getEmployeeList <em>Employee List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Employee List</em>'.
   * @see goedegep.rolodex.model.Rolodex#getEmployeeList()
   * @see #getRolodex()
   * @generated
   */
  EReference getRolodex_EmployeeList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.rolodex.model.Rolodex#getPersonList <em>Person List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Person List</em>'.
   * @see goedegep.rolodex.model.Rolodex#getPersonList()
   * @see #getRolodex()
   * @generated
   */
  EReference getRolodex_PersonList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.rolodex.model.Rolodex#getFamilyList <em>Family List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Family List</em>'.
   * @see goedegep.rolodex.model.Rolodex#getFamilyList()
   * @see #getRolodex()
   * @generated
   */
  EReference getRolodex_FamilyList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.rolodex.model.Rolodex#getPhoneList <em>Phone List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Phone List</em>'.
   * @see goedegep.rolodex.model.Rolodex#getPhoneList()
   * @see #getRolodex()
   * @generated
   */
  EReference getRolodex_PhoneList();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.Country <em>Country</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Country</em>'.
   * @see goedegep.rolodex.model.Country
   * @generated
   */
  EClass getCountry();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Country#getCountryName <em>Country Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Country Name</em>'.
   * @see goedegep.rolodex.model.Country#getCountryName()
   * @see #getCountry()
   * @generated
   */
  EAttribute getCountry_CountryName();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.City <em>City</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>City</em>'.
   * @see goedegep.rolodex.model.City
   * @generated
   */
  EClass getCity();

  /**
   * Returns the meta object for the reference '{@link goedegep.rolodex.model.City#getCountry <em>Country</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Country</em>'.
   * @see goedegep.rolodex.model.City#getCountry()
   * @see #getCity()
   * @generated
   */
  EReference getCity_Country();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.City#getCityName <em>City Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>City Name</em>'.
   * @see goedegep.rolodex.model.City#getCityName()
   * @see #getCity()
   * @generated
   */
  EAttribute getCity_CityName();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.City#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see goedegep.rolodex.model.City#getId()
   * @see #getCity()
   * @generated
   */
  EAttribute getCity_Id();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.Address <em>Address</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Address</em>'.
   * @see goedegep.rolodex.model.Address
   * @generated
   */
  EClass getAddress();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Address#getStreetName <em>Street Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Street Name</em>'.
   * @see goedegep.rolodex.model.Address#getStreetName()
   * @see #getAddress()
   * @generated
   */
  EAttribute getAddress_StreetName();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Address#getHouseNumber <em>House Number</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>House Number</em>'.
   * @see goedegep.rolodex.model.Address#getHouseNumber()
   * @see #getAddress()
   * @generated
   */
  EAttribute getAddress_HouseNumber();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Address#getHouseNumberExtension <em>House Number Extension</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>House Number Extension</em>'.
   * @see goedegep.rolodex.model.Address#getHouseNumberExtension()
   * @see #getAddress()
   * @generated
   */
  EAttribute getAddress_HouseNumberExtension();

  /**
   * Returns the meta object for the reference '{@link goedegep.rolodex.model.Address#getCity <em>City</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>City</em>'.
   * @see goedegep.rolodex.model.Address#getCity()
   * @see #getAddress()
   * @generated
   */
  EReference getAddress_City();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Address#getPOBox <em>PO Box</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>PO Box</em>'.
   * @see goedegep.rolodex.model.Address#getPOBox()
   * @see #getAddress()
   * @generated
   */
  EAttribute getAddress_POBox();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Address#getPostalCode <em>Postal Code</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Postal Code</em>'.
   * @see goedegep.rolodex.model.Address#getPostalCode()
   * @see #getAddress()
   * @generated
   */
  EAttribute getAddress_PostalCode();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Address#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see goedegep.rolodex.model.Address#getId()
   * @see #getAddress()
   * @generated
   */
  EAttribute getAddress_Id();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.Person <em>Person</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Person</em>'.
   * @see goedegep.rolodex.model.Person
   * @generated
   */
  EClass getPerson();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Person#getInfix <em>Infix</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Infix</em>'.
   * @see goedegep.rolodex.model.Person#getInfix()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_Infix();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Person#getFirstname <em>Firstname</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Firstname</em>'.
   * @see goedegep.rolodex.model.Person#getFirstname()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_Firstname();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Person#getSurname <em>Surname</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Surname</em>'.
   * @see goedegep.rolodex.model.Person#getSurname()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_Surname();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Person#getInitials <em>Initials</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Initials</em>'.
   * @see goedegep.rolodex.model.Person#getInitials()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_Initials();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Person#getGender <em>Gender</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Gender</em>'.
   * @see goedegep.rolodex.model.Person#getGender()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_Gender();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Person#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see goedegep.rolodex.model.Person#getId()
   * @see #getPerson()
   * @generated
   */
  EAttribute getPerson_Id();

  /**
   * Returns the meta object for the reference '{@link goedegep.rolodex.model.Person#getFamily <em>Family</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Family</em>'.
   * @see goedegep.rolodex.model.Person#getFamily()
   * @see #getPerson()
   * @generated
   */
  EReference getPerson_Family();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.rolodex.model.Person#getBirthday <em>Birthday</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Birthday</em>'.
   * @see goedegep.rolodex.model.Person#getBirthday()
   * @see #getPerson()
   * @generated
   */
  EReference getPerson_Birthday();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.Person#getName() <em>Get Name</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Name</em>' operation.
   * @see goedegep.rolodex.model.Person#getName()
   * @generated
   */
  EOperation getPerson__GetName();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.Person#retrieveAddress() <em>Retrieve Address</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Retrieve Address</em>' operation.
   * @see goedegep.rolodex.model.Person#retrieveAddress()
   * @generated
   */
  EOperation getPerson__RetrieveAddress();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.Institution <em>Institution</em>}'.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @return the meta object for class '<em>Institution</em>'.
   * @see goedegep.rolodex.model.Institution
   * @generated
   */
  EClass getInstitution();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Institution#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.rolodex.model.Institution#getName()
   * @see #getInstitution()
   * @generated
   */
  EAttribute getInstitution_Name();

  /**
   * Returns the meta object for the reference '{@link goedegep.rolodex.model.Institution#getMailingAddress <em>Mailing Address</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Mailing Address</em>'.
   * @see goedegep.rolodex.model.Institution#getMailingAddress()
   * @see #getInstitution()
   * @generated
   */
  EReference getInstitution_MailingAddress();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.Employee <em>Employee</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Employee</em>'.
   * @see goedegep.rolodex.model.Employee
   * @generated
   */
  EClass getEmployee();

  /**
   * Returns the meta object for the reference '{@link goedegep.rolodex.model.Employee#getInstitution <em>Institution</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Institution</em>'.
   * @see goedegep.rolodex.model.Employee#getInstitution()
   * @see #getEmployee()
   * @generated
   */
  EReference getEmployee_Institution();

  /**
   * Returns the meta object for the reference '{@link goedegep.rolodex.model.Employee#getPerson <em>Person</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Person</em>'.
   * @see goedegep.rolodex.model.Employee#getPerson()
   * @see #getEmployee()
   * @generated
   */
  EReference getEmployee_Person();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.PhoneNumber <em>Phone Number</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Phone Number</em>'.
   * @see goedegep.rolodex.model.PhoneNumber
   * @generated
   */
  EClass getPhoneNumber();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.PhoneNumber#getPhoneNumber <em>Phone Number</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Phone Number</em>'.
   * @see goedegep.rolodex.model.PhoneNumber#getPhoneNumber()
   * @see #getPhoneNumber()
   * @generated
   */
  EAttribute getPhoneNumber_PhoneNumber();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.PhoneNumber#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.rolodex.model.PhoneNumber#getDescription()
   * @see #getPhoneNumber()
   * @generated
   */
  EAttribute getPhoneNumber_Description();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.PhoneNumber#getConnectionType <em>Connection Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Connection Type</em>'.
   * @see goedegep.rolodex.model.PhoneNumber#getConnectionType()
   * @see #getPhoneNumber()
   * @generated
   */
  EAttribute getPhoneNumber_ConnectionType();

  /**
   * Returns the meta object for the reference list '{@link goedegep.rolodex.model.PhoneNumber#getNumberHolders <em>Number Holders</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Number Holders</em>'.
   * @see goedegep.rolodex.model.PhoneNumber#getNumberHolders()
   * @see #getPhoneNumber()
   * @generated
   */
  EReference getPhoneNumber_NumberHolders();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.Family <em>Family</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Family</em>'.
   * @see goedegep.rolodex.model.Family
   * @generated
   */
  EClass getFamily();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Family#getFamilyTitle <em>Family Title</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Family Title</em>'.
   * @see goedegep.rolodex.model.Family#getFamilyTitle()
   * @see #getFamily()
   * @generated
   */
  EAttribute getFamily_FamilyTitle();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Family#getFamilyName <em>Family Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Family Name</em>'.
   * @see goedegep.rolodex.model.Family#getFamilyName()
   * @see #getFamily()
   * @generated
   */
  EAttribute getFamily_FamilyName();

  /**
   * Returns the meta object for the reference list '{@link goedegep.rolodex.model.Family#getMembers <em>Members</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Members</em>'.
   * @see goedegep.rolodex.model.Family#getMembers()
   * @see #getFamily()
   * @generated
   */
  EReference getFamily_Members();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.CountryList <em>Country List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Country List</em>'.
   * @see goedegep.rolodex.model.CountryList
   * @generated
   */
  EClass getCountryList();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.rolodex.model.CountryList#getCountries <em>Countries</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Countries</em>'.
   * @see goedegep.rolodex.model.CountryList#getCountries()
   * @see #getCountryList()
   * @generated
   */
  EReference getCountryList_Countries();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.CountryList#addCountry(java.lang.String) <em>Add Country</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Add Country</em>' operation.
   * @see goedegep.rolodex.model.CountryList#addCountry(java.lang.String)
   * @generated
   */
  EOperation getCountryList__AddCountry__String();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.CountryList#getCountry(java.lang.String) <em>Get Country</em>}' operation.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Country</em>' operation.
   * @see goedegep.rolodex.model.CountryList#getCountry(java.lang.String)
   * @generated
   */
  EOperation getCountryList__GetCountry__String();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.CountryList#removeCountry(goedegep.rolodex.model.Country) <em>Remove Country</em>}' operation.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @return the meta object for the '<em>Remove Country</em>' operation.
   * @see goedegep.rolodex.model.CountryList#removeCountry(goedegep.rolodex.model.Country)
   * @generated
   */
  EOperation getCountryList__RemoveCountry__Country();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.CityList <em>City List</em>}'.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @return the meta object for class '<em>City List</em>'.
   * @see goedegep.rolodex.model.CityList
   * @generated
   */
  EClass getCityList();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.rolodex.model.CityList#getCities <em>Cities</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Cities</em>'.
   * @see goedegep.rolodex.model.CityList#getCities()
   * @see #getCityList()
   * @generated
   */
  EReference getCityList_Cities();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.CityList#findCityById(java.lang.String) <em>Find City By Id</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Find City By Id</em>' operation.
   * @see goedegep.rolodex.model.CityList#findCityById(java.lang.String)
   * @generated
   */
  EOperation getCityList__FindCityById__String();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.CityList#addCity(java.lang.String, goedegep.rolodex.model.Country) <em>Add City</em>}' operation.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @return the meta object for the '<em>Add City</em>' operation.
   * @see goedegep.rolodex.model.CityList#addCity(java.lang.String, goedegep.rolodex.model.Country)
   * @generated
   */
  EOperation getCityList__AddCity__String_Country();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.CityList#getCity(java.lang.String, goedegep.rolodex.model.Country) <em>Get City</em>}' operation.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get City</em>' operation.
   * @see goedegep.rolodex.model.CityList#getCity(java.lang.String, goedegep.rolodex.model.Country)
   * @generated
   */
  EOperation getCityList__GetCity__String_Country();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.CityList#getCity(java.lang.String) <em>Get City</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get City</em>' operation.
   * @see goedegep.rolodex.model.CityList#getCity(java.lang.String)
   * @generated
   */
  EOperation getCityList__GetCity__String();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.AddressList <em>Address List</em>}'.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @return the meta object for class '<em>Address List</em>'.
   * @see goedegep.rolodex.model.AddressList
   * @generated
   */
  EClass getAddressList();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.rolodex.model.AddressList#getAddresses <em>Addresses</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Addresses</em>'.
   * @see goedegep.rolodex.model.AddressList#getAddresses()
   * @see #getAddressList()
   * @generated
   */
  EReference getAddressList_Addresses();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.AddressList#findAddressById(java.lang.String) <em>Find Address By Id</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Find Address By Id</em>' operation.
   * @see goedegep.rolodex.model.AddressList#findAddressById(java.lang.String)
   * @generated
   */
  EOperation getAddressList__FindAddressById__String();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.AddressList#findAddressByPostalCode(java.lang.String, java.lang.Integer, java.lang.String) <em>Find Address By Postal Code</em>}' operation.
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @return the meta object for the '<em>Find Address By Postal Code</em>' operation.
   * @see goedegep.rolodex.model.AddressList#findAddressByPostalCode(java.lang.String, java.lang.Integer, java.lang.String)
   * @generated
   */
  EOperation getAddressList__FindAddressByPostalCode__String_Integer_String();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.AddressList#getAddress(java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String) <em>Get Address</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Address</em>' operation.
   * @see goedegep.rolodex.model.AddressList#getAddress(java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
   * @generated
   */
  EOperation getAddressList__GetAddress__String_Integer_String_String_String();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.AddressList#getAddress(java.lang.String, java.lang.Integer, java.lang.String, java.lang.String) <em>Get Address</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Address</em>' operation.
   * @see goedegep.rolodex.model.AddressList#getAddress(java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
   * @generated
   */
  EOperation getAddressList__GetAddress__String_Integer_String_String();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.PhoneNumberList <em>Phone Number List</em>}'.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @return the meta object for class '<em>Phone Number List</em>'.
   * @see goedegep.rolodex.model.PhoneNumberList
   * @generated
   */
  EClass getPhoneNumberList();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.rolodex.model.PhoneNumberList#getPhoneNumbers <em>Phone Numbers</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Phone Numbers</em>'.
   * @see goedegep.rolodex.model.PhoneNumberList#getPhoneNumbers()
   * @see #getPhoneNumberList()
   * @generated
   */
  EReference getPhoneNumberList_PhoneNumbers();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.InstitutionList <em>Institution List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Institution List</em>'.
   * @see goedegep.rolodex.model.InstitutionList
   * @generated
   */
  EClass getInstitutionList();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.rolodex.model.InstitutionList#getInstitutions <em>Institutions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Institutions</em>'.
   * @see goedegep.rolodex.model.InstitutionList#getInstitutions()
   * @see #getInstitutionList()
   * @generated
   */
  EReference getInstitutionList_Institutions();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.EmployeeList <em>Employee List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Employee List</em>'.
   * @see goedegep.rolodex.model.EmployeeList
   * @generated
   */
  EClass getEmployeeList();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.rolodex.model.EmployeeList#getEmployees <em>Employees</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Employees</em>'.
   * @see goedegep.rolodex.model.EmployeeList#getEmployees()
   * @see #getEmployeeList()
   * @generated
   */
  EReference getEmployeeList_Employees();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.PersonList <em>Person List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Person List</em>'.
   * @see goedegep.rolodex.model.PersonList
   * @generated
   */
  EClass getPersonList();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.rolodex.model.PersonList#getPersons <em>Persons</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Persons</em>'.
   * @see goedegep.rolodex.model.PersonList#getPersons()
   * @see #getPersonList()
   * @generated
   */
  EReference getPersonList_Persons();

  /**
   * Returns the meta object for the '{@link goedegep.rolodex.model.PersonList#findPersonById(java.lang.String) <em>Find Person By Id</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Find Person By Id</em>' operation.
   * @see goedegep.rolodex.model.PersonList#findPersonById(java.lang.String)
   * @generated
   */
  EOperation getPersonList__FindPersonById__String();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.FamilyList <em>Family List</em>}'.
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @return the meta object for class '<em>Family List</em>'.
   * @see goedegep.rolodex.model.FamilyList
   * @generated
   */
  EClass getFamilyList();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.rolodex.model.FamilyList#getFamilies <em>Families</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Families</em>'.
   * @see goedegep.rolodex.model.FamilyList#getFamilies()
   * @see #getFamilyList()
   * @generated
   */
  EReference getFamilyList_Families();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.PhoneNumberHolder <em>Phone Number Holder</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Phone Number Holder</em>'.
   * @see goedegep.rolodex.model.PhoneNumberHolder
   * @generated
   */
  EClass getPhoneNumberHolder();

  /**
   * Returns the meta object for the reference list '{@link goedegep.rolodex.model.PhoneNumberHolder#getPhoneNumbers <em>Phone Numbers</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Phone Numbers</em>'.
   * @see goedegep.rolodex.model.PhoneNumberHolder#getPhoneNumbers()
   * @see #getPhoneNumberHolder()
   * @generated
   */
  EReference getPhoneNumberHolder_PhoneNumbers();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.Phone <em>Phone</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Phone</em>'.
   * @see goedegep.rolodex.model.Phone
   * @generated
   */
  EClass getPhone();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Phone#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.rolodex.model.Phone#getDescription()
   * @see #getPhone()
   * @generated
   */
  EAttribute getPhone_Description();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Phone#getPhoneType <em>Phone Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Phone Type</em>'.
   * @see goedegep.rolodex.model.Phone#getPhoneType()
   * @see #getPhone()
   * @generated
   */
  EAttribute getPhone_PhoneType();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.rolodex.model.Phone#getPhoneAddressBook <em>Phone Address Book</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Phone Address Book</em>'.
   * @see goedegep.rolodex.model.Phone#getPhoneAddressBook()
   * @see #getPhone()
   * @generated
   */
  EReference getPhone_PhoneAddressBook();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.PhoneList <em>Phone List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Phone List</em>'.
   * @see goedegep.rolodex.model.PhoneList
   * @generated
   */
  EClass getPhoneList();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.rolodex.model.PhoneList#getPhones <em>Phones</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Phones</em>'.
   * @see goedegep.rolodex.model.PhoneList#getPhones()
   * @see #getPhoneList()
   * @generated
   */
  EReference getPhoneList_Phones();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.PhoneAddressBook <em>Phone Address Book</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Phone Address Book</em>'.
   * @see goedegep.rolodex.model.PhoneAddressBook
   * @generated
   */
  EClass getPhoneAddressBook();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.rolodex.model.PhoneAddressBook#getEntries <em>Entries</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Entries</em>'.
   * @see goedegep.rolodex.model.PhoneAddressBook#getEntries()
   * @see #getPhoneAddressBook()
   * @generated
   */
  EReference getPhoneAddressBook_Entries();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.PhoneAddressBookEntry <em>Phone Address Book Entry</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Phone Address Book Entry</em>'.
   * @see goedegep.rolodex.model.PhoneAddressBookEntry
   * @generated
   */
  EClass getPhoneAddressBookEntry();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.PhoneAddressBookEntry#getEntryName <em>Entry Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Entry Name</em>'.
   * @see goedegep.rolodex.model.PhoneAddressBookEntry#getEntryName()
   * @see #getPhoneAddressBookEntry()
   * @generated
   */
  EAttribute getPhoneAddressBookEntry_EntryName();

  /**
   * Returns the meta object for the reference '{@link goedegep.rolodex.model.PhoneAddressBookEntry#getPhoneNumber <em>Phone Number</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Phone Number</em>'.
   * @see goedegep.rolodex.model.PhoneAddressBookEntry#getPhoneNumber()
   * @see #getPhoneAddressBookEntry()
   * @generated
   */
  EReference getPhoneAddressBookEntry_PhoneNumber();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.PhoneAddressBookEntry#getEntryType <em>Entry Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Entry Type</em>'.
   * @see goedegep.rolodex.model.PhoneAddressBookEntry#getEntryType()
   * @see #getPhoneAddressBookEntry()
   * @generated
   */
  EAttribute getPhoneAddressBookEntry_EntryType();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.AddressHolder <em>Address Holder</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Address Holder</em>'.
   * @see goedegep.rolodex.model.AddressHolder
   * @generated
   */
  EClass getAddressHolder();

  /**
   * Returns the meta object for the reference '{@link goedegep.rolodex.model.AddressHolder#getAddress <em>Address</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Address</em>'.
   * @see goedegep.rolodex.model.AddressHolder#getAddress()
   * @see #getAddressHolder()
   * @generated
   */
  EReference getAddressHolder_Address();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.rolodex.model.AddressHolder#getPreviousAddresses <em>Previous Addresses</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Previous Addresses</em>'.
   * @see goedegep.rolodex.model.AddressHolder#getPreviousAddresses()
   * @see #getAddressHolder()
   * @generated
   */
  EReference getAddressHolder_PreviousAddresses();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.Birthday <em>Birthday</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Birthday</em>'.
   * @see goedegep.rolodex.model.Birthday
   * @generated
   */
  EClass getBirthday();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Birthday#getMonth <em>Month</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Month</em>'.
   * @see goedegep.rolodex.model.Birthday#getMonth()
   * @see #getBirthday()
   * @generated
   */
  EAttribute getBirthday_Month();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Birthday#getDay <em>Day</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Day</em>'.
   * @see goedegep.rolodex.model.Birthday#getDay()
   * @see #getBirthday()
   * @generated
   */
  EAttribute getBirthday_Day();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Birthday#getYear <em>Year</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Year</em>'.
   * @see goedegep.rolodex.model.Birthday#getYear()
   * @see #getBirthday()
   * @generated
   */
  EAttribute getBirthday_Year();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.Description <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Description</em>'.
   * @see goedegep.rolodex.model.Description
   * @generated
   */
  EClass getDescription();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Description#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.rolodex.model.Description#getDescription()
   * @see #getDescription()
   * @generated
   */
  EAttribute getDescription_Description();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.AddressForPeriod <em>Address For Period</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Address For Period</em>'.
   * @see goedegep.rolodex.model.AddressForPeriod
   * @generated
   */
  EClass getAddressForPeriod();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.AddressForPeriod#getFromDate <em>From Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>From Date</em>'.
   * @see goedegep.rolodex.model.AddressForPeriod#getFromDate()
   * @see #getAddressForPeriod()
   * @generated
   */
  EAttribute getAddressForPeriod_FromDate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.AddressForPeriod#getUntillDate <em>Untill Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Untill Date</em>'.
   * @see goedegep.rolodex.model.AddressForPeriod#getUntillDate()
   * @see #getAddressForPeriod()
   * @generated
   */
  EAttribute getAddressForPeriod_UntillDate();

  /**
   * Returns the meta object for the reference '{@link goedegep.rolodex.model.AddressForPeriod#getAddress <em>Address</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Address</em>'.
   * @see goedegep.rolodex.model.AddressForPeriod#getAddress()
   * @see #getAddressForPeriod()
   * @generated
   */
  EReference getAddressForPeriod_Address();

  /**
   * Returns the meta object for class '{@link goedegep.rolodex.model.Archive <em>Archive</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Archive</em>'.
   * @see goedegep.rolodex.model.Archive
   * @generated
   */
  EClass getArchive();

  /**
   * Returns the meta object for the attribute '{@link goedegep.rolodex.model.Archive#isArchived <em>Archived</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Archived</em>'.
   * @see goedegep.rolodex.model.Archive#isArchived()
   * @see #getArchive()
   * @generated
   */
  EAttribute getArchive_Archived();

  /**
   * Returns the meta object for enum '{@link goedegep.rolodex.model.Gender <em>Gender</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Gender</em>'.
   * @see goedegep.rolodex.model.Gender
   * @generated
   */
  EEnum getGender();

  /**
   * Returns the meta object for enum '{@link goedegep.rolodex.model.ConnectionType <em>Connection Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Connection Type</em>'.
   * @see goedegep.rolodex.model.ConnectionType
   * @generated
   */
  EEnum getConnectionType();

  /**
   * Returns the meta object for enum '{@link goedegep.rolodex.model.PhoneAddressBookEntryType <em>Phone Address Book Entry Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Phone Address Book Entry Type</em>'.
   * @see goedegep.rolodex.model.PhoneAddressBookEntryType
   * @generated
   */
  EEnum getPhoneAddressBookEntryType();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  RolodexFactory getRolodexFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.RolodexImpl <em>Rolodex</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.RolodexImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getRolodex()
    	 * @generated
    	 */
    EClass ROLODEX = eINSTANCE.getRolodex();

    /**
    	 * The meta object literal for the '<em><b>Country List</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ROLODEX__COUNTRY_LIST = eINSTANCE.getRolodex_CountryList();

    /**
    	 * The meta object literal for the '<em><b>City List</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ROLODEX__CITY_LIST = eINSTANCE.getRolodex_CityList();

    /**
    	 * The meta object literal for the '<em><b>Address List</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ROLODEX__ADDRESS_LIST = eINSTANCE.getRolodex_AddressList();

    /**
    	 * The meta object literal for the '<em><b>Phone Number List</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ROLODEX__PHONE_NUMBER_LIST = eINSTANCE.getRolodex_PhoneNumberList();

    /**
    	 * The meta object literal for the '<em><b>Institution List</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ROLODEX__INSTITUTION_LIST = eINSTANCE.getRolodex_InstitutionList();

    /**
    	 * The meta object literal for the '<em><b>Employee List</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ROLODEX__EMPLOYEE_LIST = eINSTANCE.getRolodex_EmployeeList();

    /**
    	 * The meta object literal for the '<em><b>Person List</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ROLODEX__PERSON_LIST = eINSTANCE.getRolodex_PersonList();

    /**
    	 * The meta object literal for the '<em><b>Family List</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ROLODEX__FAMILY_LIST = eINSTANCE.getRolodex_FamilyList();

    /**
    	 * The meta object literal for the '<em><b>Phone List</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ROLODEX__PHONE_LIST = eINSTANCE.getRolodex_PhoneList();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.CountryImpl <em>Country</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.CountryImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getCountry()
    	 * @generated
    	 */
    EClass COUNTRY = eINSTANCE.getCountry();

    /**
    	 * The meta object literal for the '<em><b>Country Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute COUNTRY__COUNTRY_NAME = eINSTANCE.getCountry_CountryName();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.CityImpl <em>City</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.CityImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getCity()
    	 * @generated
    	 */
    EClass CITY = eINSTANCE.getCity();

    /**
    	 * The meta object literal for the '<em><b>Country</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference CITY__COUNTRY = eINSTANCE.getCity_Country();

    /**
    	 * The meta object literal for the '<em><b>City Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute CITY__CITY_NAME = eINSTANCE.getCity_CityName();

    /**
    	 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute CITY__ID = eINSTANCE.getCity_Id();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.AddressImpl <em>Address</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.AddressImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getAddress()
    	 * @generated
    	 */
    EClass ADDRESS = eINSTANCE.getAddress();

    /**
    	 * The meta object literal for the '<em><b>Street Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ADDRESS__STREET_NAME = eINSTANCE.getAddress_StreetName();

    /**
    	 * The meta object literal for the '<em><b>House Number</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ADDRESS__HOUSE_NUMBER = eINSTANCE.getAddress_HouseNumber();

    /**
    	 * The meta object literal for the '<em><b>House Number Extension</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ADDRESS__HOUSE_NUMBER_EXTENSION = eINSTANCE.getAddress_HouseNumberExtension();

    /**
    	 * The meta object literal for the '<em><b>City</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ADDRESS__CITY = eINSTANCE.getAddress_City();

    /**
    	 * The meta object literal for the '<em><b>PO Box</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ADDRESS__PO_BOX = eINSTANCE.getAddress_POBox();

    /**
    	 * The meta object literal for the '<em><b>Postal Code</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ADDRESS__POSTAL_CODE = eINSTANCE.getAddress_PostalCode();

    /**
    	 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ADDRESS__ID = eINSTANCE.getAddress_Id();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.PersonImpl <em>Person</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.PersonImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPerson()
    	 * @generated
    	 */
    EClass PERSON = eINSTANCE.getPerson();

    /**
    	 * The meta object literal for the '<em><b>Infix</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PERSON__INFIX = eINSTANCE.getPerson_Infix();

    /**
    	 * The meta object literal for the '<em><b>Firstname</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PERSON__FIRSTNAME = eINSTANCE.getPerson_Firstname();

    /**
    	 * The meta object literal for the '<em><b>Surname</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PERSON__SURNAME = eINSTANCE.getPerson_Surname();

    /**
    	 * The meta object literal for the '<em><b>Initials</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PERSON__INITIALS = eINSTANCE.getPerson_Initials();

    /**
    	 * The meta object literal for the '<em><b>Gender</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PERSON__GENDER = eINSTANCE.getPerson_Gender();

    /**
    	 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PERSON__ID = eINSTANCE.getPerson_Id();

    /**
    	 * The meta object literal for the '<em><b>Family</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PERSON__FAMILY = eINSTANCE.getPerson_Family();

    /**
    	 * The meta object literal for the '<em><b>Birthday</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PERSON__BIRTHDAY = eINSTANCE.getPerson_Birthday();

    /**
    	 * The meta object literal for the '<em><b>Get Name</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation PERSON___GET_NAME = eINSTANCE.getPerson__GetName();

    /**
    	 * The meta object literal for the '<em><b>Retrieve Address</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation PERSON___RETRIEVE_ADDRESS = eINSTANCE.getPerson__RetrieveAddress();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.InstitutionImpl <em>Institution</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.InstitutionImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getInstitution()
    	 * @generated
    	 */
    EClass INSTITUTION = eINSTANCE.getInstitution();

    /**
    	 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute INSTITUTION__NAME = eINSTANCE.getInstitution_Name();

    /**
    	 * The meta object literal for the '<em><b>Mailing Address</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference INSTITUTION__MAILING_ADDRESS = eINSTANCE.getInstitution_MailingAddress();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.EmployeeImpl <em>Employee</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.EmployeeImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getEmployee()
    	 * @generated
    	 */
    EClass EMPLOYEE = eINSTANCE.getEmployee();

    /**
    	 * The meta object literal for the '<em><b>Institution</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference EMPLOYEE__INSTITUTION = eINSTANCE.getEmployee_Institution();

    /**
    	 * The meta object literal for the '<em><b>Person</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference EMPLOYEE__PERSON = eINSTANCE.getEmployee_Person();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.PhoneNumberImpl <em>Phone Number</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.PhoneNumberImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneNumber()
    	 * @generated
    	 */
    EClass PHONE_NUMBER = eINSTANCE.getPhoneNumber();

    /**
    	 * The meta object literal for the '<em><b>Phone Number</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PHONE_NUMBER__PHONE_NUMBER = eINSTANCE.getPhoneNumber_PhoneNumber();

    /**
    	 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PHONE_NUMBER__DESCRIPTION = eINSTANCE.getPhoneNumber_Description();

    /**
    	 * The meta object literal for the '<em><b>Connection Type</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PHONE_NUMBER__CONNECTION_TYPE = eINSTANCE.getPhoneNumber_ConnectionType();

    /**
    	 * The meta object literal for the '<em><b>Number Holders</b></em>' reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PHONE_NUMBER__NUMBER_HOLDERS = eINSTANCE.getPhoneNumber_NumberHolders();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.FamilyImpl <em>Family</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.FamilyImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getFamily()
    	 * @generated
    	 */
    EClass FAMILY = eINSTANCE.getFamily();

    /**
    	 * The meta object literal for the '<em><b>Family Title</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute FAMILY__FAMILY_TITLE = eINSTANCE.getFamily_FamilyTitle();

    /**
    	 * The meta object literal for the '<em><b>Family Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute FAMILY__FAMILY_NAME = eINSTANCE.getFamily_FamilyName();

    /**
    	 * The meta object literal for the '<em><b>Members</b></em>' reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference FAMILY__MEMBERS = eINSTANCE.getFamily_Members();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.CountryListImpl <em>Country List</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.CountryListImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getCountryList()
    	 * @generated
    	 */
    EClass COUNTRY_LIST = eINSTANCE.getCountryList();

    /**
    	 * The meta object literal for the '<em><b>Countries</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference COUNTRY_LIST__COUNTRIES = eINSTANCE.getCountryList_Countries();

    /**
    	 * The meta object literal for the '<em><b>Add Country</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation COUNTRY_LIST___ADD_COUNTRY__STRING = eINSTANCE.getCountryList__AddCountry__String();

    /**
    	 * The meta object literal for the '<em><b>Get Country</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation COUNTRY_LIST___GET_COUNTRY__STRING = eINSTANCE.getCountryList__GetCountry__String();

    /**
    	 * The meta object literal for the '<em><b>Remove Country</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation COUNTRY_LIST___REMOVE_COUNTRY__COUNTRY = eINSTANCE.getCountryList__RemoveCountry__Country();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.CityListImpl <em>City List</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.CityListImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getCityList()
    	 * @generated
    	 */
    EClass CITY_LIST = eINSTANCE.getCityList();

    /**
    	 * The meta object literal for the '<em><b>Cities</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference CITY_LIST__CITIES = eINSTANCE.getCityList_Cities();

    /**
    	 * The meta object literal for the '<em><b>Find City By Id</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation CITY_LIST___FIND_CITY_BY_ID__STRING = eINSTANCE.getCityList__FindCityById__String();

    /**
    	 * The meta object literal for the '<em><b>Add City</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation CITY_LIST___ADD_CITY__STRING_COUNTRY = eINSTANCE.getCityList__AddCity__String_Country();

    /**
    	 * The meta object literal for the '<em><b>Get City</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation CITY_LIST___GET_CITY__STRING_COUNTRY = eINSTANCE.getCityList__GetCity__String_Country();

    /**
    	 * The meta object literal for the '<em><b>Get City</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation CITY_LIST___GET_CITY__STRING = eINSTANCE.getCityList__GetCity__String();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.AddressListImpl <em>Address List</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.AddressListImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getAddressList()
    	 * @generated
    	 */
    EClass ADDRESS_LIST = eINSTANCE.getAddressList();

    /**
    	 * The meta object literal for the '<em><b>Addresses</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ADDRESS_LIST__ADDRESSES = eINSTANCE.getAddressList_Addresses();

    /**
    	 * The meta object literal for the '<em><b>Find Address By Id</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation ADDRESS_LIST___FIND_ADDRESS_BY_ID__STRING = eINSTANCE.getAddressList__FindAddressById__String();

    /**
    	 * The meta object literal for the '<em><b>Find Address By Postal Code</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation ADDRESS_LIST___FIND_ADDRESS_BY_POSTAL_CODE__STRING_INTEGER_STRING = eINSTANCE
        .getAddressList__FindAddressByPostalCode__String_Integer_String();

    /**
    	 * The meta object literal for the '<em><b>Get Address</b></em>' operation.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation ADDRESS_LIST___GET_ADDRESS__STRING_INTEGER_STRING_STRING_STRING = eINSTANCE
        .getAddressList__GetAddress__String_Integer_String_String_String();

    /**
    	 * The meta object literal for the '<em><b>Get Address</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation ADDRESS_LIST___GET_ADDRESS__STRING_INTEGER_STRING_STRING = eINSTANCE
        .getAddressList__GetAddress__String_Integer_String_String();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.PhoneNumberListImpl <em>Phone Number List</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.PhoneNumberListImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneNumberList()
    	 * @generated
    	 */
    EClass PHONE_NUMBER_LIST = eINSTANCE.getPhoneNumberList();

    /**
    	 * The meta object literal for the '<em><b>Phone Numbers</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PHONE_NUMBER_LIST__PHONE_NUMBERS = eINSTANCE.getPhoneNumberList_PhoneNumbers();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.InstitutionListImpl <em>Institution List</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.InstitutionListImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getInstitutionList()
    	 * @generated
    	 */
    EClass INSTITUTION_LIST = eINSTANCE.getInstitutionList();

    /**
    	 * The meta object literal for the '<em><b>Institutions</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference INSTITUTION_LIST__INSTITUTIONS = eINSTANCE.getInstitutionList_Institutions();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.EmployeeListImpl <em>Employee List</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.EmployeeListImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getEmployeeList()
    	 * @generated
    	 */
    EClass EMPLOYEE_LIST = eINSTANCE.getEmployeeList();

    /**
    	 * The meta object literal for the '<em><b>Employees</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference EMPLOYEE_LIST__EMPLOYEES = eINSTANCE.getEmployeeList_Employees();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.PersonListImpl <em>Person List</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.PersonListImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPersonList()
    	 * @generated
    	 */
    EClass PERSON_LIST = eINSTANCE.getPersonList();

    /**
    	 * The meta object literal for the '<em><b>Persons</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PERSON_LIST__PERSONS = eINSTANCE.getPersonList_Persons();

    /**
    	 * The meta object literal for the '<em><b>Find Person By Id</b></em>' operation.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EOperation PERSON_LIST___FIND_PERSON_BY_ID__STRING = eINSTANCE.getPersonList__FindPersonById__String();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.FamilyListImpl <em>Family List</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.FamilyListImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getFamilyList()
    	 * @generated
    	 */
    EClass FAMILY_LIST = eINSTANCE.getFamilyList();

    /**
    	 * The meta object literal for the '<em><b>Families</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference FAMILY_LIST__FAMILIES = eINSTANCE.getFamilyList_Families();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.PhoneNumberHolder <em>Phone Number Holder</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.PhoneNumberHolder
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneNumberHolder()
    	 * @generated
    	 */
    EClass PHONE_NUMBER_HOLDER = eINSTANCE.getPhoneNumberHolder();

    /**
    	 * The meta object literal for the '<em><b>Phone Numbers</b></em>' reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PHONE_NUMBER_HOLDER__PHONE_NUMBERS = eINSTANCE.getPhoneNumberHolder_PhoneNumbers();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.PhoneImpl <em>Phone</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.PhoneImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhone()
    	 * @generated
    	 */
    EClass PHONE = eINSTANCE.getPhone();

    /**
    	 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PHONE__DESCRIPTION = eINSTANCE.getPhone_Description();

    /**
    	 * The meta object literal for the '<em><b>Phone Type</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PHONE__PHONE_TYPE = eINSTANCE.getPhone_PhoneType();

    /**
    	 * The meta object literal for the '<em><b>Phone Address Book</b></em>' containment reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PHONE__PHONE_ADDRESS_BOOK = eINSTANCE.getPhone_PhoneAddressBook();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.PhoneListImpl <em>Phone List</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.PhoneListImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneList()
    	 * @generated
    	 */
    EClass PHONE_LIST = eINSTANCE.getPhoneList();

    /**
    	 * The meta object literal for the '<em><b>Phones</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PHONE_LIST__PHONES = eINSTANCE.getPhoneList_Phones();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.PhoneAddressBookImpl <em>Phone Address Book</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.PhoneAddressBookImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneAddressBook()
    	 * @generated
    	 */
    EClass PHONE_ADDRESS_BOOK = eINSTANCE.getPhoneAddressBook();

    /**
    	 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PHONE_ADDRESS_BOOK__ENTRIES = eINSTANCE.getPhoneAddressBook_Entries();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.PhoneAddressBookEntryImpl <em>Phone Address Book Entry</em>}' class.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.PhoneAddressBookEntryImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneAddressBookEntry()
    	 * @generated
    	 */
    EClass PHONE_ADDRESS_BOOK_ENTRY = eINSTANCE.getPhoneAddressBookEntry();

    /**
    	 * The meta object literal for the '<em><b>Entry Name</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PHONE_ADDRESS_BOOK_ENTRY__ENTRY_NAME = eINSTANCE.getPhoneAddressBookEntry_EntryName();

    /**
    	 * The meta object literal for the '<em><b>Phone Number</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference PHONE_ADDRESS_BOOK_ENTRY__PHONE_NUMBER = eINSTANCE.getPhoneAddressBookEntry_PhoneNumber();

    /**
    	 * The meta object literal for the '<em><b>Entry Type</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute PHONE_ADDRESS_BOOK_ENTRY__ENTRY_TYPE = eINSTANCE.getPhoneAddressBookEntry_EntryType();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.AddressHolder <em>Address Holder</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.AddressHolder
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getAddressHolder()
    	 * @generated
    	 */
    EClass ADDRESS_HOLDER = eINSTANCE.getAddressHolder();

    /**
    	 * The meta object literal for the '<em><b>Address</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ADDRESS_HOLDER__ADDRESS = eINSTANCE.getAddressHolder_Address();

    /**
    	 * The meta object literal for the '<em><b>Previous Addresses</b></em>' containment reference list feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ADDRESS_HOLDER__PREVIOUS_ADDRESSES = eINSTANCE.getAddressHolder_PreviousAddresses();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.BirthdayImpl <em>Birthday</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.BirthdayImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getBirthday()
    	 * @generated
    	 */
    EClass BIRTHDAY = eINSTANCE.getBirthday();

    /**
    	 * The meta object literal for the '<em><b>Month</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute BIRTHDAY__MONTH = eINSTANCE.getBirthday_Month();

    /**
    	 * The meta object literal for the '<em><b>Day</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute BIRTHDAY__DAY = eINSTANCE.getBirthday_Day();

    /**
    	 * The meta object literal for the '<em><b>Year</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute BIRTHDAY__YEAR = eINSTANCE.getBirthday_Year();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.Description <em>Description</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.Description
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getDescription()
    	 * @generated
    	 */
    EClass DESCRIPTION = eINSTANCE.getDescription();

    /**
    	 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute DESCRIPTION__DESCRIPTION = eINSTANCE.getDescription_Description();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.impl.AddressForPeriodImpl <em>Address For Period</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.impl.AddressForPeriodImpl
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getAddressForPeriod()
    	 * @generated
    	 */
    EClass ADDRESS_FOR_PERIOD = eINSTANCE.getAddressForPeriod();

    /**
    	 * The meta object literal for the '<em><b>From Date</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ADDRESS_FOR_PERIOD__FROM_DATE = eINSTANCE.getAddressForPeriod_FromDate();

    /**
    	 * The meta object literal for the '<em><b>Untill Date</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ADDRESS_FOR_PERIOD__UNTILL_DATE = eINSTANCE.getAddressForPeriod_UntillDate();

    /**
    	 * The meta object literal for the '<em><b>Address</b></em>' reference feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EReference ADDRESS_FOR_PERIOD__ADDRESS = eINSTANCE.getAddressForPeriod_Address();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.Archive <em>Archive</em>}' class.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.Archive
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getArchive()
    	 * @generated
    	 */
    EClass ARCHIVE = eINSTANCE.getArchive();

    /**
    	 * The meta object literal for the '<em><b>Archived</b></em>' attribute feature.
    	 * <!-- begin-user-doc -->
    	 * <!-- end-user-doc -->
    	 * @generated
    	 */
    EAttribute ARCHIVE__ARCHIVED = eINSTANCE.getArchive_Archived();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.Gender <em>Gender</em>}' enum.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.Gender
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getGender()
    	 * @generated
    	 */
    EEnum GENDER = eINSTANCE.getGender();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.ConnectionType <em>Connection Type</em>}' enum.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.ConnectionType
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getConnectionType()
    	 * @generated
    	 */
    EEnum CONNECTION_TYPE = eINSTANCE.getConnectionType();

    /**
    	 * The meta object literal for the '{@link goedegep.rolodex.model.PhoneAddressBookEntryType <em>Phone Address Book Entry Type</em>}' enum.
    	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
    	 * @see goedegep.rolodex.model.PhoneAddressBookEntryType
    	 * @see goedegep.rolodex.model.impl.RolodexPackageImpl#getPhoneAddressBookEntryType()
    	 * @generated
    	 */
    EEnum PHONE_ADDRESS_BOOK_ENTRY_TYPE = eINSTANCE.getPhoneAddressBookEntryType();

  }

} //RolodexPackage
