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
    super(customization, null, 300, false, "Enter the name of a city");
    
    this.rolodex = rolodex;
    
    customization.getComponentFactoryFx().customizeTextInputControl(this.ocGetControl());
    ocGetControl().getEntries().addAll(citiesToString(rolodex.getCityList().getCities()));
  }
  
//  @Override
//  public boolean isDataValid() {
//    return (!getMatchingCities().isEmpty());
//  }
  
  public boolean isNonExistingCityName() {
    if (ocIsFilledIn() && (getMatchingCities().isEmpty())) {
      return true;
    } else {
      return false;
    }
  }
  
  public List<City> getMatchingCities() {
    return rolodex.getCityList().getCity(ocGetControl().getText());
  }
  
  public City getCity(Country country) {
    return rolodex.getCityList().getCity(ocGetControl().getText(), country);
  }
  
  private static List<String> citiesToString(List<City> cities) {
    List<String> cityNames = new ArrayList<>();
    
    for (City city: cities) {
      cityNames.add(city.getCityName());
    }
    
    return cityNames;
  }
}