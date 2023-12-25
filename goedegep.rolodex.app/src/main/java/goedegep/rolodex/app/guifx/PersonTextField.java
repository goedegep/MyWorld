package goedegep.rolodex.app.guifx;

import java.util.ArrayList;
import java.util.List;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.Rolodex;

/**
 * This class is a TextField, with autocompletion, for a person
 */
public class PersonTextField extends ObjectControlAutoCompleteTextField<String> {
  private Rolodex rolodex;
  
  /**
   * Constructor.
   */
  public PersonTextField(CustomizationFx customization, Rolodex rolodex) {
    super(customization, null, 300, false, "Enter the name of a person");
    
    this.rolodex = rolodex;
    
    customization.getComponentFactoryFx().customizeTextInputControl(getControl());
    getControl().getEntries().addAll(personsToString(rolodex.getPersonList().getPersons()));
  }
  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public boolean isDataValid() {
//    return (!getMatchingPersons().isEmpty());
//  }
  
  public boolean isNonExistingPersonName() {
    if (isFilledIn() && (getMatchingPersons().isEmpty())) {
      return true;
    } else {
      return false;
    }
  }
  
  public List<Person> getMatchingPersons() {
    List<Person> matchingPersons = new ArrayList<>();
    
    for (Person person: rolodex.getPersonList().getPersons()) {
      if (person.getName().equals(getValue())) {
        matchingPersons.add(person);
      }
    }
    
    return matchingPersons;
  }
  
  public City getCity(Country country) {
    return rolodex.getCityList().getCity(getValue(), country);
  }
  
  private static List<String> personsToString(List<Person> persons) {
    List<String> personNames = new ArrayList<>();
    
    for (Person person: persons) {
      personNames.add(person.getName());
    }
    
    return personNames;
  }
}