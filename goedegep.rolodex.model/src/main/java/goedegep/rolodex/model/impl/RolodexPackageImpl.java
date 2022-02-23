/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.AddressForPeriod;
import goedegep.rolodex.model.AddressHolder;
import goedegep.rolodex.model.AddressList;
import goedegep.rolodex.model.Archive;
import goedegep.rolodex.model.Birthday;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.CityList;
import goedegep.rolodex.model.ConnectionType;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.CountryList;
import goedegep.rolodex.model.Description;
import goedegep.rolodex.model.Employee;
import goedegep.rolodex.model.EmployeeList;
import goedegep.rolodex.model.Family;
import goedegep.rolodex.model.FamilyList;
import goedegep.rolodex.model.Gender;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.InstitutionList;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.PersonList;
import goedegep.rolodex.model.Phone;
import goedegep.rolodex.model.PhoneAddressBook;
import goedegep.rolodex.model.PhoneAddressBookEntry;
import goedegep.rolodex.model.PhoneAddressBookEntryType;
import goedegep.rolodex.model.PhoneList;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.PhoneNumberHolder;
import goedegep.rolodex.model.PhoneNumberList;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.types.model.TypesPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RolodexPackageImpl extends EPackageImpl implements RolodexPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass rolodexEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass countryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass cityEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass addressEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass personEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass institutionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass employeeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass phoneNumberEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass familyEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass countryListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass cityListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass addressListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass phoneNumberListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass institutionListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass employeeListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass personListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass familyListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass phoneNumberHolderEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass phoneEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass phoneListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass phoneAddressBookEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass phoneAddressBookEntryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass addressHolderEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass birthdayEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass descriptionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass addressForPeriodEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass archiveEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum genderEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum connectionTypeEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum phoneAddressBookEntryTypeEEnum = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see goedegep.rolodex.model.RolodexPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private RolodexPackageImpl() {
    super(eNS_URI, RolodexFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   *
   * <p>This method is used to initialize {@link RolodexPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static RolodexPackage init() {
    if (isInited)
      return (RolodexPackage) EPackage.Registry.INSTANCE.getEPackage(RolodexPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredRolodexPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    RolodexPackageImpl theRolodexPackage = registeredRolodexPackage instanceof RolodexPackageImpl
        ? (RolodexPackageImpl) registeredRolodexPackage
        : new RolodexPackageImpl();

    isInited = true;

    // Initialize simple dependencies
    TypesPackage.eINSTANCE.eClass();
    XMLTypePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theRolodexPackage.createPackageContents();

    // Initialize created meta-data
    theRolodexPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theRolodexPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(RolodexPackage.eNS_URI, theRolodexPackage);
    return theRolodexPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getRolodex() {
    return rolodexEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getRolodex_CountryList() {
    return (EReference) rolodexEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getRolodex_CityList() {
    return (EReference) rolodexEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getRolodex_AddressList() {
    return (EReference) rolodexEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getRolodex_PhoneNumberList() {
    return (EReference) rolodexEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getRolodex_InstitutionList() {
    return (EReference) rolodexEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getRolodex_EmployeeList() {
    return (EReference) rolodexEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getRolodex_PersonList() {
    return (EReference) rolodexEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getRolodex_FamilyList() {
    return (EReference) rolodexEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getRolodex_PhoneList() {
    return (EReference) rolodexEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCountry() {
    return countryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getCountry_CountryName() {
    return (EAttribute) countryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCity() {
    return cityEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getCity_Country() {
    return (EReference) cityEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getCity_CityName() {
    return (EAttribute) cityEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getCity_Id() {
    return (EAttribute) cityEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getAddress() {
    return addressEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAddress_StreetName() {
    return (EAttribute) addressEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAddress_HouseNumber() {
    return (EAttribute) addressEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAddress_HouseNumberExtension() {
    return (EAttribute) addressEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getAddress_City() {
    return (EReference) addressEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAddress_POBox() {
    return (EAttribute) addressEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAddress_PostalCode() {
    return (EAttribute) addressEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAddress_Id() {
    return (EAttribute) addressEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPerson() {
    return personEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPerson_Infix() {
    return (EAttribute) personEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPerson_Firstname() {
    return (EAttribute) personEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPerson_Surname() {
    return (EAttribute) personEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPerson_Initials() {
    return (EAttribute) personEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPerson_Gender() {
    return (EAttribute) personEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPerson_Id() {
    return (EAttribute) personEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPerson_Family() {
    return (EReference) personEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPerson_Birthday() {
    return (EReference) personEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getPerson__GetName() {
    return personEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getPerson__RetrieveAddress() {
    return personEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getInstitution() {
    return institutionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getInstitution_Name() {
    return (EAttribute) institutionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getInstitution_MailingAddress() {
    return (EReference) institutionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getEmployee() {
    return employeeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getEmployee_Institution() {
    return (EReference) employeeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getEmployee_Person() {
    return (EReference) employeeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPhoneNumber() {
    return phoneNumberEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPhoneNumber_PhoneNumber() {
    return (EAttribute) phoneNumberEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPhoneNumber_Description() {
    return (EAttribute) phoneNumberEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPhoneNumber_ConnectionType() {
    return (EAttribute) phoneNumberEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPhoneNumber_NumberHolders() {
    return (EReference) phoneNumberEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getFamily() {
    return familyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getFamily_FamilyTitle() {
    return (EAttribute) familyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getFamily_FamilyName() {
    return (EAttribute) familyEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getFamily_Members() {
    return (EReference) familyEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCountryList() {
    return countryListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getCountryList_Countries() {
    return (EReference) countryListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getCountryList__AddCountry__String() {
    return countryListEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getCountryList__GetCountry__String() {
    return countryListEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getCountryList__RemoveCountry__Country() {
    return countryListEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCityList() {
    return cityListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getCityList_Cities() {
    return (EReference) cityListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getCityList__FindCityById__String() {
    return cityListEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getCityList__AddCity__String_Country() {
    return cityListEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getCityList__GetCity__String_Country() {
    return cityListEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getCityList__GetCity__String() {
    return cityListEClass.getEOperations().get(3);
  }

  /**
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getAddressList() {
    return addressListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getAddressList_Addresses() {
    return (EReference) addressListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getAddressList__FindAddressById__String() {
    return addressListEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
  	 * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getAddressList__FindAddressByPostalCode__String_Integer_String() {
    return addressListEClass.getEOperations().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getAddressList__GetAddress__String_Integer_String_String_String() {
    return addressListEClass.getEOperations().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getAddressList__GetAddress__String_Integer_String_String() {
    return addressListEClass.getEOperations().get(3);
  }

  /**
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPhoneNumberList() {
    return phoneNumberListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPhoneNumberList_PhoneNumbers() {
    return (EReference) phoneNumberListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getInstitutionList() {
    return institutionListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getInstitutionList_Institutions() {
    return (EReference) institutionListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getEmployeeList() {
    return employeeListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getEmployeeList_Employees() {
    return (EReference) employeeListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPersonList() {
    return personListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPersonList_Persons() {
    return (EReference) personListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getPersonList__FindPersonById__String() {
    return personListEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getFamilyList() {
    return familyListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getFamilyList_Families() {
    return (EReference) familyListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPhoneNumberHolder() {
    return phoneNumberHolderEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPhoneNumberHolder_PhoneNumbers() {
    return (EReference) phoneNumberHolderEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPhone() {
    return phoneEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPhone_Description() {
    return (EAttribute) phoneEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPhone_PhoneType() {
    return (EAttribute) phoneEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPhone_PhoneAddressBook() {
    return (EReference) phoneEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPhoneList() {
    return phoneListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPhoneList_Phones() {
    return (EReference) phoneListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPhoneAddressBook() {
    return phoneAddressBookEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPhoneAddressBook_Entries() {
    return (EReference) phoneAddressBookEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getPhoneAddressBookEntry() {
    return phoneAddressBookEntryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPhoneAddressBookEntry_EntryName() {
    return (EAttribute) phoneAddressBookEntryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getPhoneAddressBookEntry_PhoneNumber() {
    return (EReference) phoneAddressBookEntryEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getPhoneAddressBookEntry_EntryType() {
    return (EAttribute) phoneAddressBookEntryEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getAddressHolder() {
    return addressHolderEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getAddressHolder_Address() {
    return (EReference) addressHolderEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getAddressHolder_PreviousAddresses() {
    return (EReference) addressHolderEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getBirthday() {
    return birthdayEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getBirthday_Month() {
    return (EAttribute) birthdayEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getBirthday_Day() {
    return (EAttribute) birthdayEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getBirthday_Year() {
    return (EAttribute) birthdayEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getDescription() {
    return descriptionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDescription_Description() {
    return (EAttribute) descriptionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getAddressForPeriod() {
    return addressForPeriodEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAddressForPeriod_FromDate() {
    return (EAttribute) addressForPeriodEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getAddressForPeriod_UntillDate() {
    return (EAttribute) addressForPeriodEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getAddressForPeriod_Address() {
    return (EReference) addressForPeriodEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getArchive() {
    return archiveEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getArchive_Archived() {
    return (EAttribute) archiveEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getGender() {
    return genderEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getConnectionType() {
    return connectionTypeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getPhoneAddressBookEntryType() {
    return phoneAddressBookEntryTypeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public RolodexFactory getRolodexFactory() {
    return (RolodexFactory) getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated)
      return;
    isCreated = true;

    // Create classes and their features
    rolodexEClass = createEClass(ROLODEX);
    createEReference(rolodexEClass, ROLODEX__COUNTRY_LIST);
    createEReference(rolodexEClass, ROLODEX__CITY_LIST);
    createEReference(rolodexEClass, ROLODEX__ADDRESS_LIST);
    createEReference(rolodexEClass, ROLODEX__PHONE_NUMBER_LIST);
    createEReference(rolodexEClass, ROLODEX__INSTITUTION_LIST);
    createEReference(rolodexEClass, ROLODEX__EMPLOYEE_LIST);
    createEReference(rolodexEClass, ROLODEX__PERSON_LIST);
    createEReference(rolodexEClass, ROLODEX__FAMILY_LIST);
    createEReference(rolodexEClass, ROLODEX__PHONE_LIST);

    countryEClass = createEClass(COUNTRY);
    createEAttribute(countryEClass, COUNTRY__COUNTRY_NAME);

    cityEClass = createEClass(CITY);
    createEReference(cityEClass, CITY__COUNTRY);
    createEAttribute(cityEClass, CITY__CITY_NAME);
    createEAttribute(cityEClass, CITY__ID);

    addressEClass = createEClass(ADDRESS);
    createEAttribute(addressEClass, ADDRESS__STREET_NAME);
    createEAttribute(addressEClass, ADDRESS__HOUSE_NUMBER);
    createEAttribute(addressEClass, ADDRESS__HOUSE_NUMBER_EXTENSION);
    createEReference(addressEClass, ADDRESS__CITY);
    createEAttribute(addressEClass, ADDRESS__PO_BOX);
    createEAttribute(addressEClass, ADDRESS__POSTAL_CODE);
    createEAttribute(addressEClass, ADDRESS__ID);

    personEClass = createEClass(PERSON);
    createEAttribute(personEClass, PERSON__FIRSTNAME);
    createEAttribute(personEClass, PERSON__INFIX);
    createEAttribute(personEClass, PERSON__SURNAME);
    createEAttribute(personEClass, PERSON__INITIALS);
    createEAttribute(personEClass, PERSON__GENDER);
    createEAttribute(personEClass, PERSON__ID);
    createEReference(personEClass, PERSON__FAMILY);
    createEReference(personEClass, PERSON__BIRTHDAY);
    createEOperation(personEClass, PERSON___GET_NAME);
    createEOperation(personEClass, PERSON___RETRIEVE_ADDRESS);

    institutionEClass = createEClass(INSTITUTION);
    createEAttribute(institutionEClass, INSTITUTION__NAME);
    createEReference(institutionEClass, INSTITUTION__MAILING_ADDRESS);

    employeeEClass = createEClass(EMPLOYEE);
    createEReference(employeeEClass, EMPLOYEE__INSTITUTION);
    createEReference(employeeEClass, EMPLOYEE__PERSON);

    phoneNumberEClass = createEClass(PHONE_NUMBER);
    createEAttribute(phoneNumberEClass, PHONE_NUMBER__PHONE_NUMBER);
    createEAttribute(phoneNumberEClass, PHONE_NUMBER__DESCRIPTION);
    createEAttribute(phoneNumberEClass, PHONE_NUMBER__CONNECTION_TYPE);
    createEReference(phoneNumberEClass, PHONE_NUMBER__NUMBER_HOLDERS);

    familyEClass = createEClass(FAMILY);
    createEAttribute(familyEClass, FAMILY__FAMILY_TITLE);
    createEAttribute(familyEClass, FAMILY__FAMILY_NAME);
    createEReference(familyEClass, FAMILY__MEMBERS);

    countryListEClass = createEClass(COUNTRY_LIST);
    createEReference(countryListEClass, COUNTRY_LIST__COUNTRIES);
    createEOperation(countryListEClass, COUNTRY_LIST___ADD_COUNTRY__STRING);
    createEOperation(countryListEClass, COUNTRY_LIST___GET_COUNTRY__STRING);
    createEOperation(countryListEClass, COUNTRY_LIST___REMOVE_COUNTRY__COUNTRY);

    cityListEClass = createEClass(CITY_LIST);
    createEReference(cityListEClass, CITY_LIST__CITIES);
    createEOperation(cityListEClass, CITY_LIST___FIND_CITY_BY_ID__STRING);
    createEOperation(cityListEClass, CITY_LIST___ADD_CITY__STRING_COUNTRY);
    createEOperation(cityListEClass, CITY_LIST___GET_CITY__STRING_COUNTRY);
    createEOperation(cityListEClass, CITY_LIST___GET_CITY__STRING);

    addressListEClass = createEClass(ADDRESS_LIST);
    createEReference(addressListEClass, ADDRESS_LIST__ADDRESSES);
    createEOperation(addressListEClass, ADDRESS_LIST___FIND_ADDRESS_BY_ID__STRING);
    createEOperation(addressListEClass, ADDRESS_LIST___FIND_ADDRESS_BY_POSTAL_CODE__STRING_INTEGER_STRING);
    createEOperation(addressListEClass, ADDRESS_LIST___GET_ADDRESS__STRING_INTEGER_STRING_STRING_STRING);
    createEOperation(addressListEClass, ADDRESS_LIST___GET_ADDRESS__STRING_INTEGER_STRING_STRING);

    phoneNumberListEClass = createEClass(PHONE_NUMBER_LIST);
    createEReference(phoneNumberListEClass, PHONE_NUMBER_LIST__PHONE_NUMBERS);

    institutionListEClass = createEClass(INSTITUTION_LIST);
    createEReference(institutionListEClass, INSTITUTION_LIST__INSTITUTIONS);

    employeeListEClass = createEClass(EMPLOYEE_LIST);
    createEReference(employeeListEClass, EMPLOYEE_LIST__EMPLOYEES);

    personListEClass = createEClass(PERSON_LIST);
    createEReference(personListEClass, PERSON_LIST__PERSONS);
    createEOperation(personListEClass, PERSON_LIST___FIND_PERSON_BY_ID__STRING);

    familyListEClass = createEClass(FAMILY_LIST);
    createEReference(familyListEClass, FAMILY_LIST__FAMILIES);

    phoneNumberHolderEClass = createEClass(PHONE_NUMBER_HOLDER);
    createEReference(phoneNumberHolderEClass, PHONE_NUMBER_HOLDER__PHONE_NUMBERS);

    phoneEClass = createEClass(PHONE);
    createEAttribute(phoneEClass, PHONE__DESCRIPTION);
    createEAttribute(phoneEClass, PHONE__PHONE_TYPE);
    createEReference(phoneEClass, PHONE__PHONE_ADDRESS_BOOK);

    phoneListEClass = createEClass(PHONE_LIST);
    createEReference(phoneListEClass, PHONE_LIST__PHONES);

    phoneAddressBookEClass = createEClass(PHONE_ADDRESS_BOOK);
    createEReference(phoneAddressBookEClass, PHONE_ADDRESS_BOOK__ENTRIES);

    phoneAddressBookEntryEClass = createEClass(PHONE_ADDRESS_BOOK_ENTRY);
    createEAttribute(phoneAddressBookEntryEClass, PHONE_ADDRESS_BOOK_ENTRY__ENTRY_NAME);
    createEReference(phoneAddressBookEntryEClass, PHONE_ADDRESS_BOOK_ENTRY__PHONE_NUMBER);
    createEAttribute(phoneAddressBookEntryEClass, PHONE_ADDRESS_BOOK_ENTRY__ENTRY_TYPE);

    addressHolderEClass = createEClass(ADDRESS_HOLDER);
    createEReference(addressHolderEClass, ADDRESS_HOLDER__ADDRESS);
    createEReference(addressHolderEClass, ADDRESS_HOLDER__PREVIOUS_ADDRESSES);

    birthdayEClass = createEClass(BIRTHDAY);
    createEAttribute(birthdayEClass, BIRTHDAY__MONTH);
    createEAttribute(birthdayEClass, BIRTHDAY__DAY);
    createEAttribute(birthdayEClass, BIRTHDAY__YEAR);

    descriptionEClass = createEClass(DESCRIPTION);
    createEAttribute(descriptionEClass, DESCRIPTION__DESCRIPTION);

    addressForPeriodEClass = createEClass(ADDRESS_FOR_PERIOD);
    createEAttribute(addressForPeriodEClass, ADDRESS_FOR_PERIOD__FROM_DATE);
    createEAttribute(addressForPeriodEClass, ADDRESS_FOR_PERIOD__UNTILL_DATE);
    createEReference(addressForPeriodEClass, ADDRESS_FOR_PERIOD__ADDRESS);

    archiveEClass = createEClass(ARCHIVE);
    createEAttribute(archiveEClass, ARCHIVE__ARCHIVED);

    // Create enums
    genderEEnum = createEEnum(GENDER);
    connectionTypeEEnum = createEEnum(CONNECTION_TYPE);
    phoneAddressBookEntryTypeEEnum = createEEnum(PHONE_ADDRESS_BOOK_ENTRY_TYPE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized)
      return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    TypesPackage theTypesPackage = (TypesPackage) EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
    XMLTypePackage theXMLTypePackage = (XMLTypePackage) EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    countryEClass.getESuperTypes().add(theTypesPackage.getComparable());
    cityEClass.getESuperTypes().add(theTypesPackage.getComparable());
    personEClass.getESuperTypes().add(this.getPhoneNumberHolder());
    personEClass.getESuperTypes().add(this.getAddressHolder());
    personEClass.getESuperTypes().add(this.getDescription());
    personEClass.getESuperTypes().add(this.getArchive());
    institutionEClass.getESuperTypes().add(this.getPhoneNumberHolder());
    institutionEClass.getESuperTypes().add(this.getAddressHolder());
    employeeEClass.getESuperTypes().add(this.getPhoneNumberHolder());
    familyEClass.getESuperTypes().add(this.getPhoneNumberHolder());
    familyEClass.getESuperTypes().add(this.getAddressHolder());
    familyEClass.getESuperTypes().add(this.getDescription());
    familyEClass.getESuperTypes().add(this.getArchive());

    // Initialize classes, features, and operations; add parameters
    initEClass(rolodexEClass, Rolodex.class, "Rolodex", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getRolodex_CountryList(), this.getCountryList(), null, "countryList", null, 1, 1, Rolodex.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getRolodex_CityList(), this.getCityList(), null, "cityList", null, 1, 1, Rolodex.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getRolodex_AddressList(), this.getAddressList(), null, "addressList", null, 1, 1, Rolodex.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getRolodex_PhoneNumberList(), this.getPhoneNumberList(), null, "phoneNumberList", null, 1, 1,
        Rolodex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
        IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRolodex_InstitutionList(), this.getInstitutionList(), null, "institutionList", null, 1, 1,
        Rolodex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
        IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRolodex_EmployeeList(), this.getEmployeeList(), null, "employeeList", null, 1, 1, Rolodex.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getRolodex_PersonList(), this.getPersonList(), null, "personList", null, 1, 1, Rolodex.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getRolodex_FamilyList(), this.getFamilyList(), null, "familyList", null, 1, 1, Rolodex.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getRolodex_PhoneList(), this.getPhoneList(), null, "phoneList", null, 1, 1, Rolodex.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEClass(countryEClass, Country.class, "Country", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCountry_CountryName(), ecorePackage.getEString(), "countryName", null, 1, 1, Country.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(cityEClass, City.class, "City", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCity_Country(), this.getCountry(), null, "country", null, 1, 1, City.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getCity_CityName(), ecorePackage.getEString(), "cityName", null, 1, 1, City.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCity_Id(), ecorePackage.getEString(), "id", null, 0, 1, City.class, !IS_TRANSIENT, !IS_VOLATILE,
        !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(addressEClass, Address.class, "Address", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAddress_StreetName(), ecorePackage.getEString(), "streetName", null, 0, 1, Address.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAddress_HouseNumber(), ecorePackage.getEIntegerObject(), "houseNumber", null, 0, 1, Address.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAddress_HouseNumberExtension(), ecorePackage.getEString(), "houseNumberExtension", null, 0, 1,
        Address.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEReference(getAddress_City(), this.getCity(), null, "city", null, 0, 1, Address.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getAddress_POBox(), ecorePackage.getEString(), "pOBox", null, 0, 1, Address.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAddress_PostalCode(), ecorePackage.getEString(), "postalCode", null, 0, 1, Address.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAddress_Id(), ecorePackage.getEString(), "id", null, 1, 1, Address.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

    initEClass(personEClass, Person.class, "Person", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPerson_Firstname(), ecorePackage.getEString(), "firstname", null, 0, 1, Person.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPerson_Infix(), ecorePackage.getEString(), "infix", null, 0, 1, Person.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPerson_Surname(), ecorePackage.getEString(), "surname", null, 0, 1, Person.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPerson_Initials(), ecorePackage.getEString(), "initials", null, 0, 1, Person.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPerson_Gender(), this.getGender(), "gender", null, 0, 1, Person.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPerson_Id(), ecorePackage.getEString(), "id", null, 1, 1, Person.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
    initEReference(getPerson_Family(), this.getFamily(), this.getFamily_Members(), "family", null, 0, 1, Person.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getPerson_Birthday(), this.getBirthday(), null, "birthday", null, 0, 1, Person.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);

    initEOperation(getPerson__GetName(), ecorePackage.getEString(), "getName", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEOperation(getPerson__RetrieveAddress(), this.getAddress(), "retrieveAddress", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(institutionEClass, Institution.class, "Institution", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getInstitution_Name(), ecorePackage.getEString(), "name", null, 0, 1, Institution.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInstitution_MailingAddress(), this.getAddress(), null, "mailingAddress", null, 0, 1,
        Institution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE,
        IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(employeeEClass, Employee.class, "Employee", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getEmployee_Institution(), this.getInstitution(), null, "institution", null, 1, 1, Employee.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getEmployee_Person(), this.getPerson(), null, "person", null, 1, 1, Employee.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);

    initEClass(phoneNumberEClass, PhoneNumber.class, "PhoneNumber", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPhoneNumber_PhoneNumber(), ecorePackage.getEString(), "phoneNumber", null, 1, 1,
        PhoneNumber.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getPhoneNumber_Description(), ecorePackage.getEString(), "description", null, 0, 1,
        PhoneNumber.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEAttribute(getPhoneNumber_ConnectionType(), this.getConnectionType(), "connectionType", null, 0, 1,
        PhoneNumber.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);
    initEReference(getPhoneNumber_NumberHolders(), this.getPhoneNumberHolder(),
        this.getPhoneNumberHolder_PhoneNumbers(), "numberHolders", null, 0, -1, PhoneNumber.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);

    initEClass(familyEClass, Family.class, "Family", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFamily_FamilyTitle(), ecorePackage.getEString(), "familyTitle", null, 0, 1, Family.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFamily_FamilyName(), ecorePackage.getEString(), "familyName", null, 0, 1, Family.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getFamily_Members(), this.getPerson(), this.getPerson_Family(), "members", null, 1, -1, Family.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEClass(countryListEClass, CountryList.class, "CountryList", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCountryList_Countries(), this.getCountry(), null, "countries", null, 0, -1, CountryList.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    EOperation op = initEOperation(getCountryList__AddCountry__String(), this.getCountry(), "addCountry", 0, 1,
        IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "countryName", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getCountryList__GetCountry__String(), this.getCountry(), "getCountry", 0, 1, IS_UNIQUE,
        IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "countryName", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getCountryList__RemoveCountry__Country(), null, "removeCountry", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getCountry(), "country", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(cityListEClass, CityList.class, "CityList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCityList_Cities(), this.getCity(), null, "cities", null, 0, -1, CityList.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);

    op = initEOperation(getCityList__FindCityById__String(), this.getCity(), "findCityById", 0, 1, IS_UNIQUE,
        IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "id", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getCityList__AddCity__String_Country(), this.getCity(), "addCity", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "cityName", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getCountry(), "country", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getCityList__GetCity__String_Country(), this.getCity(), "getCity", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "cityName", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, this.getCountry(), "country", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getCityList__GetCity__String(), this.getCity(), "getCity", 0, -1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "cityName", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(addressListEClass, AddressList.class, "AddressList", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getAddressList_Addresses(), this.getAddress(), null, "addresses", null, 0, -1, AddressList.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    op = initEOperation(getAddressList__FindAddressById__String(), this.getAddress(), "findAddressById", 0, 1,
        IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "id", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getAddressList__FindAddressByPostalCode__String_Integer_String(), this.getAddress(),
        "findAddressByPostalCode", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "postalCode", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEIntegerObject(), "houseNumber", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "houseNumberExtension", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getAddressList__GetAddress__String_Integer_String_String_String(), this.getAddress(),
        "getAddress", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "streetName", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEIntegerObject(), "houseNumber", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "houseNumberExternsion", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "postalCode", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "city", 0, 1, IS_UNIQUE, IS_ORDERED);

    op = initEOperation(getAddressList__GetAddress__String_Integer_String_String(), this.getAddress(), "getAddress", 0,
        1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "streetName", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEIntegerObject(), "houseNumber", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "houseNumberExtension", 0, 1, IS_UNIQUE, IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "city", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(phoneNumberListEClass, PhoneNumberList.class, "PhoneNumberList", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPhoneNumberList_PhoneNumbers(), this.getPhoneNumber(), null, "phoneNumbers", null, 0, -1,
        PhoneNumberList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
        !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(institutionListEClass, InstitutionList.class, "InstitutionList", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getInstitutionList_Institutions(), this.getInstitution(), null, "institutions", null, 0, -1,
        InstitutionList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
        !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(employeeListEClass, EmployeeList.class, "EmployeeList", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getEmployeeList_Employees(), this.getEmployee(), null, "employees", null, 0, -1, EmployeeList.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEClass(personListEClass, PersonList.class, "PersonList", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPersonList_Persons(), this.getPerson(), null, "persons", null, 0, -1, PersonList.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    op = initEOperation(getPersonList__FindPersonById__String(), this.getPerson(), "findPersonById", 0, 1, IS_UNIQUE,
        IS_ORDERED);
    addEParameter(op, ecorePackage.getEString(), "id", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(familyListEClass, FamilyList.class, "FamilyList", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getFamilyList_Families(), this.getFamily(), null, "families", null, 0, -1, FamilyList.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEClass(phoneNumberHolderEClass, PhoneNumberHolder.class, "PhoneNumberHolder", IS_ABSTRACT, IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPhoneNumberHolder_PhoneNumbers(), this.getPhoneNumber(), this.getPhoneNumber_NumberHolders(),
        "phoneNumbers", null, 0, -1, PhoneNumberHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
        IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(phoneEClass, Phone.class, "Phone", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPhone_Description(), ecorePackage.getEString(), "description", null, 1, 1, Phone.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPhone_PhoneType(), ecorePackage.getEString(), "phoneType", null, 0, 1, Phone.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPhone_PhoneAddressBook(), this.getPhoneAddressBook(), null, "phoneAddressBook", null, 1, 1,
        Phone.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
        IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(phoneListEClass, PhoneList.class, "PhoneList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPhoneList_Phones(), this.getPhone(), null, "phones", null, 0, -1, PhoneList.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);

    initEClass(phoneAddressBookEClass, PhoneAddressBook.class, "PhoneAddressBook", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPhoneAddressBook_Entries(), this.getPhoneAddressBookEntry(), null, "entries", null, 0, -1,
        PhoneAddressBook.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
        !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(phoneAddressBookEntryEClass, PhoneAddressBookEntry.class, "PhoneAddressBookEntry", !IS_ABSTRACT,
        !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPhoneAddressBookEntry_EntryName(), ecorePackage.getEString(), "entryName", null, 0, 1,
        PhoneAddressBookEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getPhoneAddressBookEntry_PhoneNumber(), this.getPhoneNumber(), null, "phoneNumber", null, 1, 1,
        PhoneAddressBookEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
        !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPhoneAddressBookEntry_EntryType(), this.getPhoneAddressBookEntryType(), "entryType", null, 0, 1,
        PhoneAddressBookEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);

    initEClass(addressHolderEClass, AddressHolder.class, "AddressHolder", IS_ABSTRACT, IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEReference(getAddressHolder_Address(), this.getAddress(), null, "address", null, 0, 1, AddressHolder.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getAddressHolder_PreviousAddresses(), this.getAddressForPeriod(), null, "previousAddresses", null, 0,
        -1, AddressHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
        !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(birthdayEClass, Birthday.class, "Birthday", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBirthday_Month(), ecorePackage.getEInt(), "month", null, 0, 1, Birthday.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBirthday_Day(), ecorePackage.getEInt(), "day", null, 0, 1, Birthday.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBirthday_Year(), ecorePackage.getEInt(), "year", null, 0, 1, Birthday.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(descriptionEClass, Description.class, "Description", IS_ABSTRACT, IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDescription_Description(), ecorePackage.getEString(), "description", null, 0, 1,
        Description.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);

    initEClass(addressForPeriodEClass, AddressForPeriod.class, "AddressForPeriod", !IS_ABSTRACT, !IS_INTERFACE,
        IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAddressForPeriod_FromDate(), theTypesPackage.getEFlexDate(), "fromDate", null, 0, 1,
        AddressForPeriod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAddressForPeriod_UntillDate(), theTypesPackage.getEFlexDate(), "untillDate", null, 0, 1,
        AddressForPeriod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
        !IS_DERIVED, IS_ORDERED);
    initEReference(getAddressForPeriod_Address(), this.getAddress(), null, "address", null, 0, 1,
        AddressForPeriod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
        IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(archiveEClass, Archive.class, "Archive", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getArchive_Archived(), theXMLTypePackage.getBoolean(), "archived", null, 0, 1, Archive.class,
        !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(genderEEnum, Gender.class, "Gender");
    addEEnumLiteral(genderEEnum, Gender.MALE);
    addEEnumLiteral(genderEEnum, Gender.FEMALE);

    initEEnum(connectionTypeEEnum, ConnectionType.class, "ConnectionType");
    addEEnumLiteral(connectionTypeEEnum, ConnectionType.VAST);
    addEEnumLiteral(connectionTypeEEnum, ConnectionType.MOBIEL);
    addEEnumLiteral(connectionTypeEEnum, ConnectionType.SERVICENUMMER_BETAALD);
    addEEnumLiteral(connectionTypeEEnum, ConnectionType.SERVICENUMMER_GRATIS);

    initEEnum(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.class, "PhoneAddressBookEntryType");
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.NUMBER_LOCATION_0);
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.NUMBER_LOCATION_1);
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.NUMBER_LOCATION_2);
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.NUMBER_LOCATION_3);
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.NUMBER_LOCATION_4);
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.NUMBER_LOCATION_5);
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.NUMBER_LOCATION_6);
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.NUMBER_LOCATION_7);
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.NUMBER_LOCATION_8);
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.NUMBER_LOCATION_9);
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.PHONE_NUMBER_ENTRY);
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.NAME_AND_PHONE_NUMBER_ENTRY);
    addEEnumLiteral(phoneAddressBookEntryTypeEEnum, PhoneAddressBookEntryType.NAME_AND_PHONE_NUMBER_AND_TYPE_ENTRY);

    // Create resource
    createResource(eNS_URI);
  }

} //RolodexPackageImpl
