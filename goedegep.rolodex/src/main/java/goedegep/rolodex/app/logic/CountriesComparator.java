package goedegep.rolodex.app.logic;

import java.util.Comparator;

import goedegep.rolodex.model.Country;

/**
 * This class provides a (singleton) <code>Comparator</code> to compare Countries.
 */
public class CountriesComparator implements Comparator<Country> {
  private static CountriesComparator instance;

  /**
   * Private constructor.
   */
  private CountriesComparator() {
  }
  
  @Override
  public int compare(Country country1, Country country2) {
    if ((country1 == null)  &&  (country2 == null)) {
      return 0;
    } else if ((country1 == null)  &&  (country2 != null)) {
      return -1;
    } else if ((country1 != null)  &&  (country2 == null)) {
      return 1;
    }
    
    String countryName1 = country1.getCountryName();
    String countryName2 = country2.getCountryName();
    if ((countryName1 == null)  &&  (countryName2 == null)) {
      return 0;
    } else if ((countryName1 == null)  &&  (countryName2 != null)) {
      return -1;
    } else if ((countryName1 != null)  &&  (countryName2 == null)) {
      return 1;
    } else {
      return countryName1.compareTo(countryName2);
    }
  }

  /**
   * Get the Comparator instance.
   * 
   * @return the Comparator instance.
   */
  public static CountriesComparator getInstance() {
    if (instance == null) {
      instance = new CountriesComparator();
    }
    
    return instance;
  }
}
