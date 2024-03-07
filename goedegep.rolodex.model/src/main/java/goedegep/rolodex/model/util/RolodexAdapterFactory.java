/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import goedegep.rolodex.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.rolodex.model.RolodexPackage
 * @generated
 */
public class RolodexAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static RolodexPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RolodexAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = RolodexPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object) {
    if (object == modelPackage) {
      return true;
    }
    if (object instanceof EObject) {
      return ((EObject) object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("rawtypes")
  protected RolodexSwitch<Adapter> modelSwitch = new RolodexSwitch<Adapter>() {
    @Override
    public Adapter caseRolodex(Rolodex object) {
      return createRolodexAdapter();
    }

    @Override
    public Adapter caseCountry(Country object) {
      return createCountryAdapter();
    }

    @Override
    public Adapter caseCity(City object) {
      return createCityAdapter();
    }

    @Override
    public Adapter caseAddress(Address object) {
      return createAddressAdapter();
    }

    @Override
    public Adapter casePerson(Person object) {
      return createPersonAdapter();
    }

    @Override
    public Adapter caseInstitution(Institution object) {
      return createInstitutionAdapter();
    }

    @Override
    public Adapter caseEmployee(Employee object) {
      return createEmployeeAdapter();
    }

    @Override
    public Adapter casePhoneNumber(PhoneNumber object) {
      return createPhoneNumberAdapter();
    }

    @Override
    public Adapter caseFamily(Family object) {
      return createFamilyAdapter();
    }

    @Override
    public Adapter caseCountryList(CountryList object) {
      return createCountryListAdapter();
    }

    @Override
    public Adapter caseCityList(CityList object) {
      return createCityListAdapter();
    }

    @Override
    public Adapter caseAddressList(AddressList object) {
      return createAddressListAdapter();
    }

    @Override
    public Adapter casePhoneNumberList(PhoneNumberList object) {
      return createPhoneNumberListAdapter();
    }

    @Override
    public Adapter caseInstitutionList(InstitutionList object) {
      return createInstitutionListAdapter();
    }

    @Override
    public Adapter caseEmployeeList(EmployeeList object) {
      return createEmployeeListAdapter();
    }

    @Override
    public Adapter casePersonList(PersonList object) {
      return createPersonListAdapter();
    }

    @Override
    public Adapter caseFamilyList(FamilyList object) {
      return createFamilyListAdapter();
    }

    @Override
    public Adapter casePhoneNumberHolder(PhoneNumberHolder object) {
      return createPhoneNumberHolderAdapter();
    }

    @Override
    public Adapter casePhone(Phone object) {
      return createPhoneAdapter();
    }

    @Override
    public Adapter casePhoneList(PhoneList object) {
      return createPhoneListAdapter();
    }

    @Override
    public Adapter casePhoneAddressBook(PhoneAddressBook object) {
      return createPhoneAddressBookAdapter();
    }

    @Override
    public Adapter casePhoneAddressBookEntry(PhoneAddressBookEntry object) {
      return createPhoneAddressBookEntryAdapter();
    }

    @Override
    public Adapter caseAddressHolder(AddressHolder object) {
      return createAddressHolderAdapter();
    }

    @Override
    public Adapter caseBirthday(Birthday object) {
      return createBirthdayAdapter();
    }

    @Override
    public Adapter caseDescription(Description object) {
      return createDescriptionAdapter();
    }

    @Override
    public Adapter caseAddressForPeriod(AddressForPeriod object) {
      return createAddressForPeriodAdapter();
    }

    @Override
    public Adapter caseArchive(Archive object) {
      return createArchiveAdapter();
    }

    @Override
    public <T> Adapter caseComparable(Comparable<T> object) {
      return createComparableAdapter();
    }

    @Override
    public Adapter defaultCase(EObject object) {
      return createEObjectAdapter();
    }
  };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target) {
    return modelSwitch.doSwitch((EObject) target);
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.Rolodex <em>Rolodex</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.Rolodex
   * @generated
   */
  public Adapter createRolodexAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.Country <em>Country</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.Country
   * @generated
   */
  public Adapter createCountryAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.City <em>City</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.City
   * @generated
   */
  public Adapter createCityAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.Address <em>Address</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.Address
   * @generated
   */
  public Adapter createAddressAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.Person <em>Person</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.Person
   * @generated
   */
  public Adapter createPersonAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.Institution <em>Institution</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.Institution
   * @generated
   */
  public Adapter createInstitutionAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.Employee <em>Employee</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.Employee
   * @generated
   */
  public Adapter createEmployeeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.PhoneNumber <em>Phone Number</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.PhoneNumber
   * @generated
   */
  public Adapter createPhoneNumberAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.Family <em>Family</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.Family
   * @generated
   */
  public Adapter createFamilyAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.CountryList <em>Country List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.CountryList
   * @generated
   */
  public Adapter createCountryListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.CityList <em>City List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.CityList
   * @generated
   */
  public Adapter createCityListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.AddressList <em>Address List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.AddressList
   * @generated
   */
  public Adapter createAddressListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.PhoneNumberList <em>Phone Number List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.PhoneNumberList
   * @generated
   */
  public Adapter createPhoneNumberListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.InstitutionList <em>Institution List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.InstitutionList
   * @generated
   */
  public Adapter createInstitutionListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.EmployeeList <em>Employee List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.EmployeeList
   * @generated
   */
  public Adapter createEmployeeListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.PersonList <em>Person List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.PersonList
   * @generated
   */
  public Adapter createPersonListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.FamilyList <em>Family List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.FamilyList
   * @generated
   */
  public Adapter createFamilyListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.PhoneNumberHolder <em>Phone Number Holder</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.PhoneNumberHolder
   * @generated
   */
  public Adapter createPhoneNumberHolderAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.Phone <em>Phone</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.Phone
   * @generated
   */
  public Adapter createPhoneAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.PhoneList <em>Phone List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.PhoneList
   * @generated
   */
  public Adapter createPhoneListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.PhoneAddressBook <em>Phone Address Book</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.PhoneAddressBook
   * @generated
   */
  public Adapter createPhoneAddressBookAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.PhoneAddressBookEntry <em>Phone Address Book Entry</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.PhoneAddressBookEntry
   * @generated
   */
  public Adapter createPhoneAddressBookEntryAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.AddressHolder <em>Address Holder</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.AddressHolder
   * @generated
   */
  public Adapter createAddressHolderAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.Birthday <em>Birthday</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.Birthday
   * @generated
   */
  public Adapter createBirthdayAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.Description <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.Description
   * @generated
   */
  public Adapter createDescriptionAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.AddressForPeriod <em>Address For Period</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.AddressForPeriod
   * @generated
   */
  public Adapter createAddressForPeriodAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.rolodex.model.Archive <em>Archive</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.rolodex.model.Archive
   * @generated
   */
  public Adapter createArchiveAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link java.lang.Comparable <em>Comparable</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see java.lang.Comparable
   * @generated
   */
  public Adapter createComparableAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter() {
    return null;
  }

} //RolodexAdapterFactory
