package goedegep.rolodex.app.guifx;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.textfield.TextFields;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.controls.TextFieldObjectInput;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Rolodex;

/**
 * This class is a TextField, with autocompletion, for a city name
 */
public class CityTextField extends TextFieldObjectInput<String> {
  private Rolodex rolodex;
  
  /**
   * Constructor.
   */
  public CityTextField(CustomizationFx customization, Rolodex rolodex) {
    super(null, 300, false, "Enter the name of a city");
    
    this.rolodex = rolodex;
    
    customization.getComponentFactoryFx().customizeTextInputControl(this);
    TextFields.bindAutoCompletion(this, citiesToString(rolodex.getCityList().getCities()));
  }
  
  @Override
  public boolean getIsValid(StringBuilder buf) {
    
    if (isOptional()  &&  !getIsFilledIn()) {
      return true;
    }
        
    return (!getMatchingCities().isEmpty());
  }
  
  public boolean isNonExistingCityName() {
    if (getIsFilledIn() && (getMatchingCities().isEmpty())) {
      return true;
    } else {
      return false;
    }
  }
  
  public List<City> getMatchingCities() {
    return rolodex.getCityList().getCity(getText());
  }
  
  public City getCity(Country country) {
    return rolodex.getCityList().getCity(getText(), country);
  }
  
  private static List<String> citiesToString(List<City> cities) {
    List<String> cityNames = new ArrayList<>();
    
    for (City city: cities) {
      cityNames.add(city.getCityName());
    }
    
    return cityNames;
  }
}