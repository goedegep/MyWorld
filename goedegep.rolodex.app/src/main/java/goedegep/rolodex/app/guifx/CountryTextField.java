package goedegep.rolodex.app.guifx;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.textfield.TextFields;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.controls.TextFieldObjectInput;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Rolodex;

/**
 * This class is a TextField, with autocompletion, for a country name
 */
public class CountryTextField extends TextFieldObjectInput<String> {
  private Rolodex rolodex;
  
  /**
   * Constructor.
   */
  public CountryTextField(CustomizationFx customization, Rolodex rolodex) {
    super(null, 300, false, "Enter the name of the country in which the city is located");
    
    this.rolodex = rolodex;
    
    customization.getComponentFactoryFx().customizeTextInputControl(this);
    TextFields.bindAutoCompletion(this, countriesToString(rolodex.getCountryList().getCountries()));
  }
  
  @Override
  public boolean getIsValid(StringBuilder buf) {
    
    if (isOptional()  &&  !getIsFilledIn()) {
      return true;
    }
        
    return (getCountry() != null);
  }
  
  public boolean isNonExistingCountyName() {
    if (getIsFilledIn() && (getCountry() == null)) {
      return true;
    } else {
      return false;
    }
  }
  
  public Country getCountry() {
    return rolodex.getCountryList().getCountry(getText());    
  }
  
  private static List<String> countriesToString(List<Country> countries) {
    List<String> countryNames = new ArrayList<>();
    
    for (Country country: countries) {
      countryNames.add(country.getCountryName());
    }
    
    return countryNames;
  }
}