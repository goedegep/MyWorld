package goedegep.rolodex.app.logic;


import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Rolodex;
import javafx.util.StringConverter;

/**
 * This class is a {@link StringConverter} for a Country.
 * <p>
 * The toString method returns the country name.<br/>
 * The fromString method retrieves the Country from the provided Rolodex, based on the provided country name.
 */
public class CityStringConverter extends StringConverter<Object> {
  private Rolodex rolodex;

  public CityStringConverter(Rolodex rolodex) {
    this.rolodex = rolodex;
  }
  
  @Override
  public String toString(Object object) {
    City city = (City) object;
    if (city != null) {
      return city.getCityName();
    } else {
      return null;
    }
  }

  @Override
  public Object fromString(String cityName) {
    return rolodex.getCityList().getCity(cityName);
  }

}
