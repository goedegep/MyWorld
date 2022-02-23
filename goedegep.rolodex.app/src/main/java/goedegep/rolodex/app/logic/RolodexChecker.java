package goedegep.rolodex.app.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.Birthday;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.Rolodex;

/**
 * This class provides various checks on a Rolodex.
 */
public class RolodexChecker {
  private ResourceSet resourceSet;
  private Rolodex rolodex;
  
  public RolodexChecker(Rolodex rolodex) {
    resourceSet = rolodex.eResource().getResourceSet();
    this.rolodex = rolodex;
  }
  
  /**
   * Check for Countries which aren't referred to.
   * 
   * @return a list of Countries to which there are no references.
   */
  public List<Country> checkCountriesNotReferredTo() {
    List<Country> countriesNotReferredTo = new ArrayList<>();
    
    for (Country country: rolodex.getCountryList().getCountries()) {
      Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(country, resourceSet);
      if (settings.isEmpty()) {
        countriesNotReferredTo.add(country);
      }
    }
    
    return countriesNotReferredTo;
  }
  
  /**
   * Check for Cities which aren't referred to.
   * 
   * @return a list of Cities to which there are no references.
   */
  public List<City> checkCitiesNotReferredTo() {
    List<City> citiesNotReferredTo = new ArrayList<>();
    
    for (City city: rolodex.getCityList().getCities()) {
      Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(city, resourceSet);
      if (settings.isEmpty()) {
        citiesNotReferredTo.add(city);
      }
    }
    
    return citiesNotReferredTo;
  }

  public List<City> checkCitiesWithoutCountry() {
    List<City> citiesWithoutCountry = new ArrayList<>();
    
    for (City city: rolodex.getCityList().getCities()) {
      if (city.getCountry() == null) {
        citiesWithoutCountry.add(city);
      }
    }
    
    return citiesWithoutCountry;
  }
  
  /**
   * Check for Addresses which aren't referred to.
   * 
   * @return a list of Addresses to which there are no references.
   */
  public List<Address> checkAddressesNotReferredTo() {
    List<Address> addressesNotReferredTo = new ArrayList<>();
    
    for (Address address: rolodex.getAddressList().getAddresses()) {
      Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(address, resourceSet);
      if (settings.isEmpty()) {
        addressesNotReferredTo.add(address);
      }
    }
    
    return addressesNotReferredTo;
  }
  
  /**
   * Check for PhoneNumbers which aren't referred to.
   * 
   * @return a list of PhoneNumbers to which there are no references.
   */
  public List<PhoneNumber> checkPhoneNumbersNotReferredTo() {
    List<PhoneNumber> phoneNumbersNotReferredTo = new ArrayList<>();
    
    for (PhoneNumber phoneNumber: rolodex.getPhoneNumberList().getPhoneNumbers()) {
      Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(phoneNumber, resourceSet);
      if (settings.isEmpty()) {
        phoneNumbersNotReferredTo.add(phoneNumber);
      }
    }
    
    return phoneNumbersNotReferredTo;
  }
  
  /**
   * Check for empty birthdays.
   * 
   * @return a list of Persons who have an empty birthday set.
   */
  public List<Person> checkEmptyBirthdays() {
    List<Person> personsWithEmptyBirthday = new ArrayList<>();
    
    for (Person person: rolodex.getPersonList().getPersons()) {
      Birthday birthday = person.getBirthday();
      if (birthday != null) {
        if ((birthday.getDay() == 0)  &&  (birthday.getMonth() == 0)  &&  (birthday.getYear() == 0)) {
          personsWithEmptyBirthday.add(person);
        }
      }
    }
    
    return personsWithEmptyBirthday;
  }
}
