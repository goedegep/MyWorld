package goedegep.gpxeditor.logic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import goedegep.myworld.common.Registry;
import goedegep.util.Tuplet;

/**
 * This registry class provides information to be shared within the complete GPX application.
 */
public class GPXRegistry extends Registry {  
  
  /**
   * Singleton instance of the GPXRegistry.
   */
  private static GPXRegistry instance = null;
  
  /**
   * User Agent string for the OpenStreetMap API's; map tiles and Nominatim geocoding service.
   */
  private static String osmUserAgent = null;

  /**
   * Get the singleton instance of the GPXRegistry.
   * 
   * @return the singleton instance of the GPXRegistry.
   */
  public static GPXRegistry getInstance() {
    if (instance == null) {
      instance = new GPXRegistry();
    }
    
    return instance;
  }

  /**
   * Get the User Agent string for the OpenStreetMap API's; map tiles and Nominatim geocoding service.
   * 
   * @return the User Agent string for the Nominatim geocoding service.
   */
  public String getOSMUserAgent() {
    return osmUserAgent;
  }

  /**
   * Set the User Agent string for the OpenStreetMap API's; map tiles and Nominatim geocoding service..
   * 
   * @param nominatimUserAgent the User Agent string for the Nominatim geocoding service.
   */
  public void setOSMUserAgent(String nominatimUserAgent) {
    GPXRegistry.osmUserAgent = nominatimUserAgent;
  }
  
  /**
   * Private constructor for the GPXRegistry.
   */
  private GPXRegistry() {
    super();
    
    setAuthor("Peter Goedegebure");
    setShortProductInfo("GPX editor and viewer");
    setPropertyDescriptorsFileName("GPXPropertyDescriptors.xmi");
    setUserPropertiesFileName("GPXUserPreferences.xmi");
  }

  @Override
  public boolean setValue(String name, String value) {
    if (super.setValue(name, value)) {
      return true;
    }

    boolean known = true;
    switch (name) {
    case "osmUserAgent" -> osmUserAgent = value;
    default -> known = false;
    }

    return known;
  }

  @Override
  public List<Tuplet<Field, Object>> getFields() {
    List<Tuplet<Field, Object>> tuplets = new ArrayList<>();
    Class<?> c = getClass();

    for (Field field : c.getDeclaredFields()) {
      int mods = field.getModifiers();
      
      if ("instance".equals(field.getName())) {
        // skip the 'instance' field
        continue;
      }
      
      // only read static fields; skip synthetic compiler-generated fields
      if (java.lang.reflect.Modifier.isStatic(mods) && !field.isSynthetic()) {
        try {
          Object value = field.get(null); // null because field is static
          tuplets.add(new Tuplet<Field, Object>(field, value));
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    
    return tuplets;
  }
}