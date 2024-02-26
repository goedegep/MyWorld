package goedegep.vacations.app.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import goedegep.poi.model.POICategoryId;
import goedegep.util.html.HtmlUtil;
import goedegep.util.sgml.SgmlUtil;
import goedegep.util.text.Indent;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.VacationsPackage;

public class OsmAndUtil {
  private static final Logger LOGGER = Logger.getLogger(OsmAndUtil.class.getName());
  static final String NEW_LINE = System.getProperty("line.separator");
  
  private static Map<POICategoryId, String> colorMap = new HashMap<>();
  private static Map<POICategoryId, String> iconMap = new HashMap<>();
  
  static {
    /*
     * Category colors
     */
    
    // Red for places to stay.
    colorMap.put(POICategoryId.BED_AND_BREAKFAST, "#ff0000");
    colorMap.put(POICategoryId.CAMPING, "#ff0000");
    colorMap.put(POICategoryId.HOTEL, "#ff0000");
    
    // Travel information
    
    // Things to see or do
    colorMap.put(POICategoryId.SCENIC_VIEWPOINT, "#0000ff");
    colorMap.put(POICategoryId.CASTLE, "#00ff00");
    colorMap.put(POICategoryId.MONUMENT, "#00ff00");
    
    /*
     * Category icons
     * zie C:\Users\Peter\Downloads\OsmAnd-resources-master\rendering_styles\style-icons\poi-icons-png\drawable-hdpi
     * Verder met mx_special_building.png
     */
    iconMap.put(POICategoryId.AIRPORT, "aeroway_airport");
    iconMap.put(POICategoryId.AMUSEMENT, "attraction_amusement_ride");
    iconMap.put(POICategoryId.ATM, "amenity_atm");
    iconMap.put(POICategoryId.ATM, "amenity_bank");
    iconMap.put(POICategoryId.BANK, "accommodation");
    iconMap.put(POICategoryId.BAR, "amenity_bar");
    iconMap.put(POICategoryId.BEACH, "beach");
    iconMap.put(POICategoryId.BUILDING, "building");
    iconMap.put(POICategoryId.BRIDGE, "bridge_structure_suspension");
    iconMap.put(POICategoryId.BUS_STATION, "amenity_bus_station");
    iconMap.put(POICategoryId.CAMPING, "camp_pitch");
    iconMap.put(POICategoryId.CANYON, "strait");
    iconMap.put(POICategoryId.CAR_RENTAL, "amenity_car_rental");
    iconMap.put(POICategoryId.CASINO, "amenity_casino");
    iconMap.put(POICategoryId.CASTLE, "historic_castle");
    iconMap.put(POICategoryId.CHURCH, "building_type_church");
    iconMap.put(POICategoryId.CINEMA, "amenity_cinema");
    iconMap.put(POICategoryId.CITY, "place_city");
    iconMap.put(POICategoryId.COMMUNITY_BUILDING, "amenity_community_centre");
    iconMap.put(POICategoryId.CONVENTION_CENTER, "conference_centre");
    iconMap.put(POICategoryId.FERRY, "cargo_vehicle");
    iconMap.put(POICategoryId.FIRE_DEPARTMENT, "amenity_fire_station");
    iconMap.put(POICategoryId.GOLF_COURSE, "golf_course");
    iconMap.put(POICategoryId.HOSPITAL, "amenity_hospital");
    iconMap.put(POICategoryId.HOTEL, "accomodation");
    iconMap.put(POICategoryId.ICE_SKATING_RING, "ice_skating");
    iconMap.put(POICategoryId.INDUSTRY, "industrial");
    iconMap.put(POICategoryId.LIBRARY, "amenity_library");
    iconMap.put(POICategoryId.MARINA, "leisure_marina");
    iconMap.put(POICategoryId.MONUMENT, "monument");
    iconMap.put(POICategoryId.MOUNTAIN, "natural");
    iconMap.put(POICategoryId.MOUNTAIN_PASS, "mountain_pass");
    iconMap.put(POICategoryId.NEIGHBOURHOOD, "village");
    iconMap.put(POICategoryId.NIGHTLIFE, "dance_floor");
    iconMap.put(POICategoryId.PETROL_STATION, "amenity_fuel");
    iconMap.put(POICategoryId.PHARMACY, "amenity_pharmacy");
    iconMap.put(POICategoryId.PARK, "park");
    iconMap.put(POICategoryId.PARKING, "parking");
    iconMap.put(POICategoryId.POLICE, "amenity_police");
    iconMap.put(POICategoryId.POST_OFFICE, "amenity_post_office");
    iconMap.put(POICategoryId.RESTAURANT, "amenity_restaurant");
    iconMap.put(POICategoryId.SCENIC_VIEWPOINT, "for_tourists");
    iconMap.put(POICategoryId.SHOP, "bag");
    iconMap.put(POICategoryId.SHOPPING_CENTER, "bag");
    iconMap.put(POICategoryId.SKI_RESORT, "piste");
    iconMap.put(POICategoryId.SQUARE, "square");
    iconMap.put(POICategoryId.SWIMMING_POOL, "swimming_pool");
    iconMap.put(POICategoryId.RAILWAY_STATION, "locomotive");
    iconMap.put(POICategoryId.THEATER, "amenity_theatre");
    iconMap.put(POICategoryId.TOURIST_ATTRACTION, "camera");
    iconMap.put(POICategoryId.WINERY, "craft_winery");
  }
  
  /**
   * Check whether there is an OsmAnd icon available for each POICategoryId.
   */
  public static void checkIcons() {
    for (POICategoryId id: POICategoryId.values()) {
      String iconName = iconMap.get(id);
      if (iconName == null) {
        LOGGER.severe("No icon for: " + id);
      }
    }
  }

  /**
   * Create an OsmAnd favourites file for an EObject hierarchy.
   * <p>
   * A favourite is created for each {@link Location} in the specified EObject hierarchy.
   * See {@link #eObjectToFavourite} for details about which information is written per location.
   * 
   * 
   * @param startEObject the EObject from below which the favourites will be created.
   * @param group the optional 'category' for the favourite.
   * @param file The favourites file that will be created.
   * @return a Map with the favourites names per category.
   */
  public static Map<String, List<String>> createFavouritesFile(EObject startEObject, String group, Path file) {
    LOGGER.info("=> startEObject=" + startEObject.toString() + ", file=" + file);
    
    StringBuilder buf = new StringBuilder();
    Indent indent = new Indent(2);
    Map<String, List<String>> categoryToFavouriteNamesMap = new HashMap<>();
    
    // write header part.
    buf.append("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>").append(NEW_LINE);
    buf.append("<gpx version=\"1.1\" xmlns=\"http://www.topografix.com/GPX/1/1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\">").append(NEW_LINE);
    buf.append("  <metadata>").append(NEW_LINE);
    buf.append("    <name>favourites</name>").append(NEW_LINE);
    buf.append("  </metadata>").append(NEW_LINE);
    
    // write the locations
    indent.increment();
    
    eObjectToFavourite(startEObject, group, buf, indent, categoryToFavouriteNamesMap);
    
    TreeIterator<EObject> vacationIterator = startEObject.eAllContents();
    
    while (vacationIterator.hasNext()) {
      EObject eObject = vacationIterator.next();
      eObjectToFavourite(eObject, group, buf, indent, categoryToFavouriteNamesMap);
    }
    indent.decrement();
    
    // write the footer
    buf.append("</gpx>").append(NEW_LINE);
    
    try {
      Files.write(file, buf.toString().getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }    
    
    return categoryToFavouriteNamesMap;
  }
  
  /**
   * Add a single EObject as a favourite xml element to a StringBuffer.
   * <p>
   * Only if the specified EObject is a {@link Location} a favourite is created, otherwise nothing is done.<br/>
   * Furthermore a favourite is only created if the Location has both latitude and longitude set.<br/>
   * The created favourite is filled as follows:
   * <ul>
   * <li>lat, lon attributes<br/>
   * Set to the latitude and longitude of the Location.
   * </li>
   * <li>name element<br/>
   * If the Location has the name set, this is used. Else, if the Location is of type POICategoryId.CITY and the city is set, this city is used.
   * Else the name element is omitted.
   * </li>
   * <li>category element<br/>
   * Note that the category element appears as a group in OsmAnd.<br/>
   * If you specify a group (parameter group isn't null), this is used as the category. Else, if the location type (a POICategoryId) is set, the literal of this type is used.
   * If this also isn't available, a default value 'Other' is used.<br/>
   * This way you can e.g. group all locations for a vacation (by setting group to the name of your vacation),
   * or you can group all locations of the same location type (e.g. all Parkings, Restaurants, etc.).
   * </li>
   * <li>desc element<br/>
   * If the Location has a description, this is used. Else this element is omitted.
   * </li>
   * <li>extensions/color element<br/>
   * If the Location has a location type, and there is an entry for this location type in the colorMap, this value is used. Otherwise this element is omitted.
   * </li>
   * <li>extensions/icon element<br/>
   * If the Location has a location type, and there is an entry for this location type in the iconMap, this value is used. Otherwise this element is omitted.
   * </li>
   * </ul>
   * 
   * 
   * @param eObject the EObject to be added.
   * @param group the optional 'category' for the favourite.
   * @param buf the StringBuilder to add the generated text to.
   * @param the indentation to use.
   */
  private static void eObjectToFavourite(EObject eObject, String group, StringBuilder buf, Indent indent, Map<String, List<String>> categoryToNameMap) {
    String message = null;
    
    switch(eObject.eClass().getClassifierID()) {
    
    // Only locations are written as waypoints
    case VacationsPackage.LOCATION:
      Location location = (Location) eObject;
      
      // Only add a location if it has a latitude and longitude.
      if (location.isSetLatitude()  &&  location.isSetLongitude()) {
        String[] attributeDefinitions = new String[2];
        attributeDefinitions[0] = "lat=\"" + location.getLatitude() + "\"";
        attributeDefinitions[1] = "lon=\"" + location.getLongitude() + "\"";
        buf.append(SgmlUtil.createElementOpen(indent, null, "wpt", attributeDefinitions)).append(NEW_LINE);
        indent.increment();
        
        // name: option 1 is location name, option 2 is city name.
        String name = null;
        if (location.isSetName()) {
          name = location.getName();
        } else if (location.getLocationType() == POICategoryId.CITY  &&  location.isSetCity()) {
          name = location.getCity();
        }
        if (name != null) {
          buf.append(SgmlUtil.createElement(indent, null, "name", HtmlUtil.encodeHTML(name))).append(NEW_LINE);
        }
        
        // category: is the literal of the location type if available, else 'Other'.
        String category = null;
        if (group != null) {
          category = group;
        }
        if ((category == null)  &&  location.isSetLocationType()) {
          category = location.getLocationType().getLiteral();
        }
        if (category == null) {
          category = "Other";
        }
        buf.append(SgmlUtil.createElement(indent, null, "category", HtmlUtil.encodeHTML(category))).append(NEW_LINE);
        
        // description: is the location description if available.
        if (location.isSetDescription()) {
          buf.append(SgmlUtil.createElement(indent, null, "desc", HtmlUtil.encodeHTML(location.getDescription()))).append(NEW_LINE);
        }
        
        // color: based on the location type and color map.
        if (location.getLocationType() != null) {
          String colorString = colorMap.get(location.getLocationType());
          String iconString = iconMap.get(location.getLocationType());
          if (iconString == null) {
            message = "No OsmAnd icon for this location type";
            LOGGER.severe("No OsmAnd icon for location type " + location.getLocationType().getName());
          }
          if (colorString != null  ||  iconString != null) {
            buf.append(SgmlUtil.createElementOpen(indent, null, "extensions")).append(NEW_LINE);
            indent.increment();

            if (colorString != null) {
              buf.append(SgmlUtil.createElement(indent, null, "color", colorString)).append(NEW_LINE);
            }
            
            if (iconString != null) {
              buf.append(SgmlUtil.createElement(indent, null, "icon", iconString)).append(NEW_LINE);
            }

            indent.decrement();
            buf.append(SgmlUtil.createElementClose(indent, null, "extensions")).append(NEW_LINE);
          }
        } else {
          message = "Location has no location type set";
          LOGGER.severe("Location has no location type set");
        }
        
        indent.decrement();
        buf.append(SgmlUtil.createElementClose(indent, null, "wpt")).append(NEW_LINE);
        
        addLocationToCategoryToNameMap(category, name, message, categoryToNameMap);
        
      }
      break;
      
    }
  }
  
  private static void addLocationToCategoryToNameMap(String category, String name, String message, Map<String, List<String>> categoryToNameMap) {
    List<String> locationsForCategory = categoryToNameMap.get(category);
    
    if (locationsForCategory == null) {
      locationsForCategory = new ArrayList<>();
      categoryToNameMap.put(category, locationsForCategory);
    }
    
    StringBuilder buf = new StringBuilder();
    if (name != null) {
      buf.append(name);
    }
    
    if (message != null) {
      if (!buf.isEmpty()) {
        buf.append(" - ");
      }
      buf.append(message);
    }
    locationsForCategory.add(buf.toString());
  }

}
