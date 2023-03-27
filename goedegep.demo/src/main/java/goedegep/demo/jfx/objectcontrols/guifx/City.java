package goedegep.demo.jfx.objectcontrols.guifx;

import java.util.Arrays;
import java.util.List;

public class City {
  private static final List<City> CITIES = Arrays.asList(
      new City("Vlissingen"), new City("Tilburg"), new City("Middelburg"), new City("Groningen"), new City("'s Hertogenbosch"), new City("Utrecht"),
      new City("Best"), new City("Goes"), new City("Breda"), new City("Venlo"), new City("Amersfoort"), new City("Rotterdam"), new City("Leiden"),
      new City("Leeuwarden"), new City("Arnhem"), new City("Haarlem"), new City("Maastricht"), new City("Roermond"));
  
  private String name;
  
  /**
   * Constructor
   */
  public City(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<City> getCities() {
    return CITIES;
  }

  public static City getCityForName(String cityName) {
    for (City city: CITIES) {
      if (city.name.equals(cityName)) {
        return city;
      }
    }
    
    return null;
  }
}
