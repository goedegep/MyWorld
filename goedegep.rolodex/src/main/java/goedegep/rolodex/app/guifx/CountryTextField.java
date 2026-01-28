package goedegep.rolodex.app.guifx;

import java.util.ArrayList;
import java.util.List;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Rolodex;

/**
 * This class is a TextField, with autocompletion, for a country name
 */
public class CountryTextField extends ObjectControlAutoCompleteTextField<String> {
  private Rolodex rolodex;
  
  /**
   * Constructor.
   */
  public CountryTextField(CustomizationFx customization, Rolodex rolodex) {
    super(customization, 300, false, "Enter the name of the country in which the city is located");
    
    this.rolodex = rolodex;
    
    customization.getComponentFactoryFx().customizeTextInputControl(this.getControl());
    getControl().getEntries().addAll(countriesToString(rolodex.getCountryList().getCountries()));
  }
  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public boolean isDataValid() {
//    return (getCountry() != null);
//  }
  
  public boolean isNonExistingCountyName() {
    if (isFilledIn() && (getCountry() == null)) {
      return true;
    } else {
      return false;
    }
  }
  
  public Country getCountry() {
    return rolodex.getCountryList().getCountry(getControl().getText());    
  }
  
  private static List<String> countriesToString(List<Country> countries) {
    List<String> countryNames = new ArrayList<>();
    
    for (Country country: countries) {
      countryNames.add(country.getCountryName());
    }
    
    return countryNames;
  }
}