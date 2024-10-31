package goedegep.rolodex.app.guifx;

import java.util.ArrayList;
import java.util.List;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Rolodex;

/**
 * This class is a TextField, with autocompletion, for a city name
 */
public class CityTextField extends ObjectControlAutoCompleteTextField<String> {
  private Rolodex rolodex;
  
  /**
   * Constructor.
   */
  public CityTextField(CustomizationFx customization, Rolodex rolodex) {
    super(customization, 300, false, "Enter the name of a city");
    
    this.rolodex = rolodex;
    
    customization.getComponentFactoryFx().customizeTextInputControl(this.getControl());
    getControl().getEntries().addAll(citiesToString(rolodex.getCityList().getCities()));
  }
  
  public boolean isNonExistingCityName() {
    if (isFilledIn() && getMatchingCities().isEmpty()) {
      return true;
    } else {
      return false;
    }
  }
  
  public List<City> getMatchingCities() {
    return rolodex.getCityList().getCity(getControl().getText());
  }
  
  public City getCity(Country country) {
    return rolodex.getCityList().getCity(getControl().getText(), country);
  }
  
  private static List<String> citiesToString(List<City> cities) {
    List<String> cityNames = new ArrayList<>();
    
    for (City city: cities) {
      cityNames.add(city.getCityName());
    }
    
    return cityNames;
  }
}