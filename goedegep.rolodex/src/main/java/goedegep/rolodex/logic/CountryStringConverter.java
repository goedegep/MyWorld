package goedegep.rolodex.logic;


import goedegep.rolodex.model.Country;
import goedegep.rolodex.model.Rolodex;
import javafx.util.StringConverter;

/**
 * This class is a {@link StringConverter} for a Country.
 * <p>
 * The toString method returns the country name.<br/>
 * The fromString method retrieves the Country from the provided Rolodex, based on the provided country name.
 */
public class CountryStringConverter extends StringConverter<Object> {
  private Rolodex rolodex;

  public CountryStringConverter(Rolodex rolodex) {
    this.rolodex = rolodex;
  }
  
  @Override
  public String toString(Object object) {
    Country country = (Country) object;
    if (country != null) {
      return country.getCountryName();
    } else {
      return null;
    }
  }

  @Override
  public Object fromString(String countryName) {
    return rolodex.getCountryList().getCountry(countryName);
  }

}
