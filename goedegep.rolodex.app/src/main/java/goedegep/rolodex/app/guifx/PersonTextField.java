package goedegep.rolodex.app.guifx;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.textfield.TextFields;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.controls.TextFieldObjectInput;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.Rolodex;

/**
 * This class is a TextField, with autocompletion, for a person
 */
public class PersonTextField extends TextFieldObjectInput<String> {
  private Rolodex rolodex;
  
  /**
   * Constructor.
   */
  public PersonTextField(CustomizationFx customization, Rolodex rolodex) {
    super(null, 300, false, "Enter the name of a person");
    
    this.rolodex = rolodex;
    
    customization.getComponentFactoryFx().customizeTextInputControl(this);
    TextFields.bindAutoCompletion(this, personsToString(rolodex.getPersonList().getPersons()));
  }
  
  @Override
  public boolean getIsValid(StringBuilder buf) {
    
    if (isOptional()  &&  !getIsFilledIn()) {
      return true;
    }
        
    return (!getMatchingPersons().isEmpty());
  }
  
  public boolean isNonExistingPersonName() {
    if (getIsFilledIn() && (getMatchingPersons().isEmpty())) {
      return true;
    } else {
      return false;
    }
  }
  
  public List<Person> getMatchingPersons() {
    List<Person> matchingPersons = new ArrayList<>();
    
    for (Person person: rolodex.getPersonList().getPersons()) {
      if (person.getName().equals(getText())) {
        matchingPersons.add(person);
      }
    }
    
    return matchingPersons;
  }
  
  public City getCity(Country country) {
    return rolodex.getCityList().getCity(getText(), country);
  }
  
  private static List<String> personsToString(List<Person> persons) {
    List<String> personNames = new ArrayList<>();
    
    for (Person person: persons) {
      personNames.add(person.getName());
    }
    
    return personNames;
  }
}