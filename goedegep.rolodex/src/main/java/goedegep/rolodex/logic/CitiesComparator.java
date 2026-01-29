package goedegep.rolodex.logic;

import java.util.Comparator;

import goedegep.rolodex.model.City;

/**
 * This class provides a (singleton) <code>Comparator</code> to compare Cities.
 */
public class CitiesComparator implements Comparator<City> {
  private static CitiesComparator instance;
  private static CountriesComparator countriesComparator;

  /**
   * Private constructor.
   */
  private CitiesComparator() {
    countriesComparator = CountriesComparator.getInstance();
  }
  
  @Override
  public int compare(City city1, City city2) {
    int result = 0;
    
    if ((city1 == null)  &&  (city2 == null)) {
      return 0;
    } else if ((city1 == null)  &&  (city2 != null)) {
      result = -1;
    } else if ((city1 != null)  &&  (city2 == null)) {
      result = 1;
    }
    
    if (result != 0) {
      return result;
    }    
    
    String cityName1 = city1.getCityName();
    String cityName2 = city2.getCityName();
    if ((cityName1 == null)  &&  (cityName2 != null)) {
      result = -1;
    } else if ((cityName1 != null)  &&  (cityName2 == null)) {
      result = 1;
    } else if ((cityName1 != null)  &&  (cityName2 != null)) {
      result = cityName1.compareTo(cityName2);
    }
    
    if (result != 0) {
      return result;
    }
    
    return countriesComparator.compare(city1.getCountry(), city2.getCountry());
  }

  /**
   * Get the Comparator instance.
   * 
   * @return the Comparator instance.
   */
  public static CitiesComparator getInstance() {
    if (instance == null) {
      instance = new CitiesComparator();
    }
    
    return instance;
  }
}
