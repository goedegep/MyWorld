package goedegep.travels.app.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.atlis.location.nominatim.NominatimAPI;
import com.atlis.location.nominatim.OSMAddress;
import com.atlis.location.nominatim.OSMLocationInfo;

import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Polygon;
import goedegep.geo.WGS84Coordinates;
import goedegep.poi.app.LocationCategory;
import goedegep.travels.model.BoundingBox;
import goedegep.travels.model.Location;
import goedegep.travels.model.TravelsFactory;
import javafx.util.Pair;

/**
 * This class provides Vacations data model specific utility methods on top of the nominatim API.
 * 
 */
public class NominatimUtil {
  private static final Logger LOGGER = Logger.getLogger(NominatimUtil.class.getName());
  
  /**
   * URL of the Nominatim server.
   */
  private static final String NOMINATIM_SERVER_URL = "https://nominatim.openstreetmap.org/";

  /**
   * Mapping of the OSM 'type' attribute to a POICategoryId.
   */
  private static Map<String, LocationCategory> osmTypeToPOICategoryMap = new HashMap<>();
  
  
  private static Map<String, LocationCategory> osmPOIAttributeNameToPOICategoryMap = new HashMap<>();
  private static TravelsFactory VACATIONS_FACTORY = TravelsFactory.eINSTANCE;
  
  /**
   * Instance (singleton) of the Nominatim API.
   */
  private static NominatimAPI nominatimAPI = null;
  
  
  static {
    osmTypeToPOICategoryMap.put("aerodrome", LocationCategory.AIRPORT);
    osmTypeToPOICategoryMap.put("car_rental", LocationCategory.CAR_RENTAL);
    osmTypeToPOICategoryMap.put("cathedral", LocationCategory.PLACE_OF_WORSHIP);
    osmTypeToPOICategoryMap.put("hotel", LocationCategory.HOTEL);
    osmTypeToPOICategoryMap.put("monument", LocationCategory.MONUMENT);
    osmTypeToPOICategoryMap.put("peak", LocationCategory.MOUNTAIN);
    osmTypeToPOICategoryMap.put("place_of_worship", LocationCategory.PLACE_OF_WORSHIP);
    
    osmPOIAttributeNameToPOICategoryMap.put("attraction", LocationCategory.TOURIST_ATTRACTION);
    osmPOIAttributeNameToPOICategoryMap.put("castle", LocationCategory.CASTLE);
    osmPOIAttributeNameToPOICategoryMap.put("hospital", LocationCategory.HOSPITAL);
    osmPOIAttributeNameToPOICategoryMap.put("memorial", LocationCategory.MEMORIAL);
//    osmPOIAttributeNameToPOICategoryMap.put("monument", LocationCategory.MONUMENT);
    osmPOIAttributeNameToPOICategoryMap.put("museum", LocationCategory.MUSEUM);
    osmPOIAttributeNameToPOICategoryMap.put("theme park", LocationCategory.AMUSEMENT);
    osmPOIAttributeNameToPOICategoryMap.put("tourism", LocationCategory.HOTEL);
  }
  
  /**
   * Obtain location information via the nominatim API.
   * <p>
   * A nominatim request is performed with the provided coordinates.
   * A successful response is translated into a Location.
   * 
   * @param latitude the latitude of the search coordinates
   * @param longitude the longitude of the search coordinates
   * @return The nominatim response translated to a Location, or null if there was no useful response.
   */
  public static Location obtainLocationInfoFromNominatim(String userAgent, double latitude, double longitude) {
    LOGGER.severe("=>");
    Location location = null;
    
    try {
      OSMLocationInfo locationInfo = getNominatimAPI().getAddressFromMapPoint(userAgent, latitude, longitude);
      if (locationInfo != null) {
        location = NominatimUtil.osmLocationInfoToLocation(locationInfo);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return location;
  }
    
  /**
   * Create a Location with the information derived from OSMLocationInfo.
   * <p>
   * The information is derived as follows:
   * <ul>
   * <li>Latitude and longitude<br/>
   * These are copied from the OSMLocationInfo 'lat' and 'lon', but only if both values are available.
   * </li>
   * <li>BoundingBox<br/>
   * These are copied from the OSMLocationInfo 'boundingBox' array, if available. Where south is index 0, north is index 1, west is index 2
   * and east is index 3.
   * </li>
   * <li>Country<br/>
   * This is copied from the 'country' attribute of the Address of the OSMLocationInfo, if available.
   * </li>
   * <li>City<br/>
   * This is copied from the 'city', 'town' or 'village' attribute of the Address of the OSMLocationInfo, whichever is available.
   * </li>
   * <li>Street<br/>
   * This is copied from the 'streetName' attribute of the Address of the OSMLocationInfo, if available.
   * </li>
   * <li>HouseNumber<br/>
   * This is copied from the 'houseNumber' attribute of the Address of the OSMLocationInfo, if available.
   * </li>
   * <li>POICategoryId<br/>
   * Different attributes are used for this. If the 'type' attribute is available, the osmTypeToPOICategoryMap is used to obtain the POICategoryId.<br/>
   * If the 'type' attribute is not available, the address is checked for POI specific attributes in the address.
   * </li>
   * <li>name<br/>
   * If the address contains a POI specific attribute, the value of this attribute is used for the 'name'.
   * </li>
   * <li>boundaries<br/>
   * The boundaries are taken from the KML data in the OSMLocationInfo, if available.
   * </li>
   * </ul>
   * 
   * @param osmLocationInfo the OSMLocationInfo to derive the Location information from. This value may not be null.
   * @return a Location with the information derived from the <code>osmLocationInfo</code>.
   */
  public static Location osmLocationInfoToLocation(OSMLocationInfo osmLocationInfo) {
    Location location = VACATIONS_FACTORY.createLocation();
    
    // Latitude and longitude.
    Double osmLatitude = osmLocationInfo.getLat();
    Double osmLongitude = osmLocationInfo.getLon();
    if ((osmLatitude != null)  &&  (osmLongitude != null)) {
      location.setLatitude(osmLatitude);
      location.setLongitude(osmLongitude);
    }
    
    // BoundingBox
    Double[] osmBoundingBoxValues = osmLocationInfo.getBoundingbox();
    if (osmBoundingBoxValues != null) {
      BoundingBox boundingBox = VACATIONS_FACTORY.createBoundingBox();
      boundingBox.setWest(osmBoundingBoxValues[2]);
      boundingBox.setNorth(osmBoundingBoxValues[1]);
      boundingBox.setEast(osmBoundingBoxValues[3]);
      boundingBox.setSouth(osmBoundingBoxValues[0]);
      location.setBoundingbox(boundingBox);
    }

    // First source for the POICategoryId; the 'type' attribute.
    LocationCategory poiCategoryId = getPOICategoryIdForType(osmLocationInfo.getType());
    
    // First source for the name; the 'name' attribute.
    String name = osmLocationInfo.getName();
    
    OSMAddress osmAddress = osmLocationInfo.getAddress();
    if (osmAddress != null) {
      String value;
      
      // Country
      value = osmAddress.getCountry();
      if ((value != null)  &&  !value.isEmpty()) {
        location.setCountry(value);
      }
      
      // City
      String city = null;
      value = osmAddress.getCity();
      if ((value != null)  &&  !value.isEmpty()) {
        city = value;
      }
      value = osmAddress.getTown();
      if ((value != null)  &&  !value.isEmpty()) {
        if (city == null) {
          city = value;
        } else {
          LOGGER.severe("Both city and town set, city=" + city + ", town=" + value);
        }
      }
      value = osmAddress.getVillage();
      if ((value != null)  &&  !value.isEmpty()) {
        if (city == null) {
          city = value;
        } else {
          LOGGER.severe("Both city and village set, city=" + city + ", village=" + value);
        }
      }
      if (location != null) {
        location.setCity(city);
      }
      
      // Street
      value = getStreetName(osmAddress);
      if ((value != null)  &&  !value.isEmpty()) {
        location.setStreet(value);
      }
      
      // HouseNumber
      value = osmAddress.getHousenumber();
      if ((value != null)  &&  !value.isEmpty()) {
        location.setHouseNumber(value);
      }
      
      // Name and second source for the POICategoryId.
      Pair<String, String> poiInfo = NominatimAPI.getPoiInfoFromAddress(osmAddress);
      if (poiInfo != null) {
        if (poiCategoryId == null) {
          poiCategoryId = getPOICategoryId(poiInfo.getKey());
        } else {
          LOGGER.severe("Found both a 'type' and an address attribute for the POI type, type=" + osmLocationInfo.getType() + ", attribute=" + poiInfo.getKey());
        }
        
        if (name == null) {
          location.setName(poiInfo.getValue());
        } else {
          LOGGER.severe("Found both a 'name' and an address attribute for the POI name, name=" + name + ", attributeName=" + poiInfo.getValue());
        }
      }
    }
    
    if (poiCategoryId != null) {
      location.setLocationCategory(poiCategoryId);
    }
    
    if (name != null) {
      location.setName(name);
    }
    
    List<goedegep.travels.model.Boundary> boundaries = getBoundaries(osmLocationInfo);
    if (!boundaries.isEmpty()) {
      location.getBoundaries().addAll(boundaries);
    }
        
    return location;
  }
  
  /**
   * Get the POICategoryId for an OSM 'type'.
   * <p>
   * The mapping from an OSM 'type' to a POICategoryId is provided by the <code>osmTypeToPOICategoryMap</code>.
   * 
   * @param type OSM type.
   * @return the POICategoryId corresponding to the <code>type</code>.
   */
  public static LocationCategory getPOICategoryIdForType(String type) {
    LOGGER.info("type=" + (type != null ? type : "(null)"));
    if (type == null) {
      return null;
    }
    
    return osmTypeToPOICategoryMap.get(type);
  }
  
  /**
   * Get the POICategoryId for a POI type name (as used in an OSM address).
   * <p>
   * The mapping from an OSM type name to a POICategoryId is provided by the <code>osmPOIAttributeNameToPOICategoryMap</code>.
   * 
   * @param osmPoiTypeName an OSM POI type name.
   * @return the POICategoryId corresponding to the <code>osmPoiTypeName</code>.
   */
  public static LocationCategory getPOICategoryId(String osmPoiTypeName) {
    return osmPOIAttributeNameToPOICategoryMap.get(osmPoiTypeName);
  }
  
  public static String getStreetName(OSMAddress address) {
    String streetName = null;
    
    if (address.getRoad() != null) {
      streetName = address.getRoad();
    }
    
    if (address.getPedestrian() != null) {
      if (streetName != null) {
        throw new RuntimeException("streetname already set, while pedestrian also set");
      }
      streetName = address.getPedestrian();
    }
    
    return streetName;
  }
  
  /**
   * Get the boundaries from an <code>OSMLocationInfo</code>.
   * <p>
   * The boundaries are obtained from KML data in the <code>OSMLocationInfo</code>.
   * 
   * @param osmLocationInfo the <code>OSMLocationInfo</code> to get the boundaries from.
   * @return the Boundaries from the <code>osmLocationInfo</code>.
   */
  public static List<goedegep.travels.model.Boundary> getBoundaries(OSMLocationInfo osmLocationInfo) {
    List<goedegep.travels.model.Boundary> boundaries = new ArrayList<>();

    Kml kml = osmLocationInfo.getKml();
    if (kml != null) {
      Feature feature = kml.getFeature();
      if (feature instanceof Placemark) {
        Placemark placemark = (Placemark) feature;
        Geometry geometry = placemark.getGeometry();
        if (geometry instanceof Polygon) {
          Polygon polygon = (Polygon) geometry;
          Boundary boundary = polygon.getOuterBoundaryIs();
          LinearRing linearRing = boundary.getLinearRing();
          List<Coordinate> coordinates = linearRing.getCoordinates();

          goedegep.travels.model.Boundary locationBoundary = VACATIONS_FACTORY.createBoundary();
          List<WGS84Coordinates> points = locationBoundary.getPoints();
          for (Coordinate coordinate: coordinates) {
            Double altitude = null;
            if (coordinate.getAltitude() != 0) {
              altitude = coordinate.getAltitude();
            }
            WGS84Coordinates wgs84Coordinates = new WGS84Coordinates(coordinate.getLatitude(), coordinate.getLongitude(), altitude);
            points.add(wgs84Coordinates);
          }
          boundaries.add(locationBoundary);
        } else if (geometry instanceof MultiGeometry) {

          MultiGeometry multiGeometry = (MultiGeometry) geometry;
          List<Geometry> geometries = multiGeometry.getGeometry();
          for (Geometry aGeometry: geometries) {
            if (aGeometry instanceof Polygon) {
              Polygon polygon = (Polygon) aGeometry;
              Boundary boundary = polygon.getOuterBoundaryIs();
              LinearRing linearRing = boundary.getLinearRing();
              List<Coordinate> coordinates = linearRing.getCoordinates();

              goedegep.travels.model.Boundary locationBoundary = VACATIONS_FACTORY.createBoundary();
              List<WGS84Coordinates> points = locationBoundary.getPoints();
              for (Coordinate coordinate: coordinates) {
                Double altitude = null;
                if (coordinate.getAltitude() != 0) {
                  altitude = coordinate.getAltitude();
                }
                WGS84Coordinates wgs84Coordinates = new WGS84Coordinates(coordinate.getLatitude(), coordinate.getLongitude(), altitude);
                points.add(wgs84Coordinates);
              }
              boundaries.add(locationBoundary);
            }
          }
        }
      }
//      kml.marshal();
    }

    return boundaries;
  }

  /**
   * Get the instance (singleton) of the Nominatim API.
   * 
   * @return the instance of the Nominatim API.
   */
  private static NominatimAPI getNominatimAPI() {
    if (nominatimAPI == null) {
      nominatimAPI = NominatimAPI.with(NOMINATIM_SERVER_URL);
    }
    
    return nominatimAPI;
  }
}
