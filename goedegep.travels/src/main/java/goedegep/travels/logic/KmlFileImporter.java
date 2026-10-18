package goedegep.travels.logic;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.atlis.location.nominatim.NominatimAPI;
import com.atlis.location.nominatim.OSMLocationInfo;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Data;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.ExtendedData;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.TimePrimitive;
import de.micromata.opengis.kml.v_2_2_0.TimeSpan;
import goedegep.gpx.GpxUtil;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GPXFactory;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.MetadataType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.gpxeditor.logic.Activity;
import goedegep.poi.app.LocationCategory;
import goedegep.travels.model.GPXTrack;
import goedegep.travels.model.Location;
import goedegep.travels.model.TravelsFactory;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.util.datetime.DateUtil;
import goedegep.util.datetime.TimeZoneRetriever;

public class KmlFileImporter {
  private static final Logger LOGGER = Logger.getLogger(KmlFileImporter.class.getName());
  
  /**
   * A map to convert KML categories (as {@code String}) to Activities {@code Activity}.
   */
  private static Map<String, Activity> kmlCategoryToActivityMap = new HashMap<>();
  
  /**
   * A map to convert KML categories (as {@code String}) to LocationCategory {@code LocationCategory}.
   */
  private static Map<String, LocationCategory> kmlCategoryToLocationCategoryMap = new HashMap<>();
  
  private static String[] knownUnsupportedAddresses = {
      "C. de los Helechos, s/n, 38611, Santa Cruz de Tenerife, Spanje",
      "C. Cupido, 12, 38400 Puerto de la Cruz, Santa Cruz de Tenerife, Spanje"
  };
  
  /**
   * The date time formatter used to format the date/time values in a GPX file.
   */
  private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
  
  /**
   * An instance of the {@link NominatimAPI} used to perform a reverse geocode search.
   */
  private NominatimAPI nominatimAPI = null;
  
  /**
   * The KML file from which information is to be imported.
   */
  private File kmlFile;
  
  /**
   * The users home country, used as default if a {@code Placemark} has no country filled in.
   */
  private String homeCountry;
  
  
  /**
   * Initialize the KML category to Activity map and the KML category to LocationCategory map.
   */
  static {
    // initialize the kmlCategoryToActivityMap
    kmlCategoryToActivityMap.put("Boating", Activity.BOAT_TRIP);
    kmlCategoryToActivityMap.put("Cycling", Activity.CYCLING);
    kmlCategoryToActivityMap.put("Driving", Activity.CAR_RIDE);
    kmlCategoryToActivityMap.put("Flying", Activity.FLYING);
    kmlCategoryToActivityMap.put("In a gondola lift", Activity.CABLE_CAR);
    kmlCategoryToActivityMap.put("Motorcycling", Activity.FLYING);
    kmlCategoryToActivityMap.put("On a bus", Activity.BUS_RIDE);
    kmlCategoryToActivityMap.put("On a ferry", Activity.BOAT_TRIP);
    kmlCategoryToActivityMap.put("On a train", Activity.TRAIN_RIDE);
    kmlCategoryToActivityMap.put("On a tram", Activity.TRAIN_RIDE);
    kmlCategoryToActivityMap.put("On the subway", Activity.TRAIN_RIDE);
    kmlCategoryToActivityMap.put("Running", Activity.RUNNING);
    kmlCategoryToActivityMap.put("Walking", Activity.WALKING);
    
    // initialize the kmlCategoryToLocationCategoryMap
    kmlCategoryToLocationCategoryMap.put("Autoverhuur", LocationCategory.CAR_RENTAL);
    kmlCategoryToLocationCategoryMap.put("Bakkerij", LocationCategory.SHOP);
    kmlCategoryToLocationCategoryMap.put("Bar", LocationCategory.BAR);
    kmlCategoryToLocationCategoryMap.put("Barbecue", LocationCategory.RESTAURANT);
    kmlCategoryToLocationCategoryMap.put("Bergkabelbaan", LocationCategory.CABLE_CAR);
    kmlCategoryToLocationCategoryMap.put("Bergtop", LocationCategory.MOUNTAIN);
    kmlCategoryToLocationCategoryMap.put("Bierwinkel", LocationCategory.SHOP);
    kmlCategoryToLocationCategoryMap.put("Cultureel monument", LocationCategory.MONUMENT);
    kmlCategoryToLocationCategoryMap.put("Station bergspoorweg", LocationCategory.RAILWAY_STATION);
    kmlCategoryToLocationCategoryMap.put("Boerenmarkt", LocationCategory.MARKET);
    kmlCategoryToLocationCategoryMap.put("Buffet", LocationCategory.RESTAURANT);
    kmlCategoryToLocationCategoryMap.put("Buitenzwembad", LocationCategory.SWIMMING_POOL);
    kmlCategoryToLocationCategoryMap.put("Bushalte", LocationCategory.BUS_STOP);
    kmlCategoryToLocationCategoryMap.put("Cadeauwinkel", LocationCategory.SHOP);
    kmlCategoryToLocationCategoryMap.put("Dierentuin", LocationCategory.ZOO);
    kmlCategoryToLocationCategoryMap.put("Delicatessenwinkel", LocationCategory.SHOP);
    kmlCategoryToLocationCategoryMap.put("Eiland", LocationCategory.ISLAND);
    kmlCategoryToLocationCategoryMap.put("Espressobar", LocationCategory.CAFE);
    kmlCategoryToLocationCategoryMap.put("Fastfood", LocationCategory.RESTAURANT);
    kmlCategoryToLocationCategoryMap.put("Gebouw", LocationCategory.BUILDING);
    kmlCategoryToLocationCategoryMap.put("Grieks", LocationCategory.RESTAURANT);
    kmlCategoryToLocationCategoryMap.put("Herberg", LocationCategory.GUESTHOUSE);
    kmlCategoryToLocationCategoryMap.put("Historisch herkenningspunt", LocationCategory.DEFAULT_POI);
    kmlCategoryToLocationCategoryMap.put("Hotel", LocationCategory.HOTEL);
    kmlCategoryToLocationCategoryMap.put("Italiaans", LocationCategory.RESTAURANT);
    kmlCategoryToLocationCategoryMap.put("Kabelbaanstation", LocationCategory.RAILWAY_STATION);
    kmlCategoryToLocationCategoryMap.put("Kantoor van lokale/provinciale overheid", LocationCategory.GOVERMENT);
    kmlCategoryToLocationCategoryMap.put("Koffiehuis", LocationCategory.CAFE);
    kmlCategoryToLocationCategoryMap.put("Internationaal vliegveld", LocationCategory.AIRPORT);
    kmlCategoryToLocationCategoryMap.put("Lodge", LocationCategory.LODGE);
    kmlCategoryToLocationCategoryMap.put("Luchthaven", LocationCategory.AIRPORT);
    kmlCategoryToLocationCategoryMap.put("Markt", LocationCategory.MARKET);
    kmlCategoryToLocationCategoryMap.put("Momo restaurant", LocationCategory.RESTAURANT);
    kmlCategoryToLocationCategoryMap.put("Nationaal park", LocationCategory.PARK);
    kmlCategoryToLocationCategoryMap.put("Nepalees", LocationCategory.RESTAURANT);
    kmlCategoryToLocationCategoryMap.put("Observatieplatform", LocationCategory.SCENIC_VIEWPOINT);
    kmlCategoryToLocationCategoryMap.put("Ov-station", LocationCategory.RAILWAY_STATION);
    kmlCategoryToLocationCategoryMap.put("Pannenkoekenrestaurant", LocationCategory.RESTAURANT);
    kmlCategoryToLocationCategoryMap.put("Panoramisch uitzicht", LocationCategory.SCENIC_VIEWPOINT);
    kmlCategoryToLocationCategoryMap.put("Park", LocationCategory.PARK);
    kmlCategoryToLocationCategoryMap.put("Parkeergarage", LocationCategory.PARKING);
    kmlCategoryToLocationCategoryMap.put("Parkeerplaats", LocationCategory.PARKING);
    kmlCategoryToLocationCategoryMap.put("Paspoortkantoor", LocationCategory.BORDER_CROSSING);
    kmlCategoryToLocationCategoryMap.put("Pension", LocationCategory.GUESTHOUSE);
    kmlCategoryToLocationCategoryMap.put("Plein", LocationCategory.SQUARE);
    kmlCategoryToLocationCategoryMap.put("Rivier", LocationCategory.RIVER);
    kmlCategoryToLocationCategoryMap.put("Restaurant", LocationCategory.RESTAURANT);
    kmlCategoryToLocationCategoryMap.put("Restaurant", LocationCategory.RESTAURANT);
    kmlCategoryToLocationCategoryMap.put("Snackbar", LocationCategory.RESTAURANT);
    kmlCategoryToLocationCategoryMap.put("Skioord", LocationCategory.SKI_RESORT);
    kmlCategoryToLocationCategoryMap.put("Supermarkt", LocationCategory.SHOP);
    kmlCategoryToLocationCategoryMap.put("Tankstation", LocationCategory.PETROL_STATION);
    kmlCategoryToLocationCategoryMap.put("Thais", LocationCategory.RESTAURANT);
    kmlCategoryToLocationCategoryMap.put("Toeristische attractie", LocationCategory.TOURIST_ATTRACTION);
    kmlCategoryToLocationCategoryMap.put("Treinstation", LocationCategory.RAILWAY_STATION);
    kmlCategoryToLocationCategoryMap.put("Vakantiecomplex", LocationCategory.HOTEL);
    kmlCategoryToLocationCategoryMap.put("Veerbootterminal", LocationCategory.FERRY);
    kmlCategoryToLocationCategoryMap.put("Winkelcentrum", LocationCategory.SHOPPING_CENTER);
    kmlCategoryToLocationCategoryMap.put("Woods", LocationCategory.FOREST);
  }
  
  /**
   * Constructor
   * <p>
   * A private constructor, use the {@code Builder} to create an instance of this class.
   */
  private KmlFileImporter(Builder builder) {
    this.kmlFile = builder.kmlFile;
    this.nominatimAPI = builder.nominatimAPI;
    this.homeCountry = builder.homeCountry;
  }

  /**
   * Get a list of {@code KmlPlacemarkImportData}s from a KML file.
   * <p>
   * For each {@code Placemark} in the KML file a VacationElement is created.
   * If the {@code geometry} in the {@code Placemark} is a {@code Point}, a {@code Location} is derived from the information in the {@code Placemark}.
   * If the {@code geometry} in the {@code Placemark} is a {@code LineString}, a {@code GPXTrack} VacationElement is derived from the information in the {@code Placemark} together with
   * a GPX {@DocumentType}, which shall be stored to the file referred to by the {@code GPXTrack}.
   * 
   * @return A list of {@code KmlPlacemarkImportData}. A list of all locations (Placemarks) in the KML file.
   *
   */
  public List<KmlPlacemarkImportData> getLocationsFromKmlFile() {
    List<KmlPlacemarkImportData> vacationElements = new ArrayList<>();
    
    Kml kml = Kml.unmarshal(kmlFile);
    
    Feature feature = kml.getFeature();
    getVacationElementsFromKmlFeature(null, feature, null, vacationElements);
        
    return vacationElements;
  }
  
  /**
   * Get the vacation elements from within a KML Feature.
   * <p>
   * The feature can be a {@code Document}, a {@code Folder} or a {@code Placemark}.
   * 
   * @param previousFeature the previous KML Feature, which can be null
   * @param feature the KML Feature to get the acation elements from
   * @param nextFeature the next KML Feature, which can be null
   * @param vacationElements the list to add the vacation elements information to
   */
  private void getVacationElementsFromKmlFeature(Feature previousFeature, Feature feature, Feature nextFeature, List<KmlPlacemarkImportData> vacationElements) {
    switch (feature.getClass().getName()) {
    case "de.micromata.opengis.kml.v_2_2_0.Document":
      getVacationElementsFromKmlDocument((Document) feature, vacationElements);
      break;
      
    case "de.micromata.opengis.kml.v_2_2_0.Folder":
      getVacationElementsFomKmlFolder((Folder) feature, vacationElements);
      break;
      
    case "de.micromata.opengis.kml.v_2_2_0.Placemark":
      Placemark previousPlacemark = null;
      if (previousFeature != null  &&  previousFeature instanceof Placemark) {
        previousPlacemark = (Placemark) previousFeature;
      }
      Placemark nextPlacemark = null;
      if (nextFeature != null  &&  nextFeature instanceof Placemark) {
        nextPlacemark = (Placemark) nextFeature;
      }
      getVacationElementFromKmlPlacemark(previousPlacemark, (Placemark) feature, nextPlacemark, vacationElements);
      break;
      
    default:
      throw new RuntimeException("Unsupported feature type: " + feature.getClass().getName());
    }
    
  }

  /**
   * Get the locations from within a KML Document.
   * 
   * @param document the KML {@link Document} to get the locations from
   * @param vacationElements the list to add the vacation elements information to
   */
  private void getVacationElementsFromKmlDocument(Document document, List<KmlPlacemarkImportData> vacationElements) {
    List<Feature> features = document.getFeature();
    for (int i = 0; i < features.size() - 1; i++) {
      Feature feature = features.get(i);
      Feature previousFeature = null;
      if (i > 0) {
        previousFeature = features.get(i - 1);
      }
      Feature nextFeature = null;
      if (i < features.size() - 2) {
        nextFeature = features.get(i + 1);
      }
      getVacationElementsFromKmlFeature(previousFeature, feature, nextFeature, vacationElements);
    }
  }

  /**
   * Get the vacation elements from within a KML Folder.
   * 
   * @param folder the KML {@code Folder} to get the locations from
   * @param vacationElements the list to add the vacation elements information to
   */
  private void getVacationElementsFomKmlFolder(Folder folder, List<KmlPlacemarkImportData> vacationElements) {
    List<Feature> features = folder.getFeature();
    for (int i = 0; i < features.size() - 1; i++) {
      Feature feature = features.get(i);
      
      if (feature instanceof Folder featureFolder) {
        getVacationElementsFomKmlFolder(featureFolder, vacationElements);
      } else if (feature instanceof Placemark placemark) {
        Placemark previousPlacemark = null;
        if (i > 0) {
          Feature previousFeature = features.get(i - 1);
          if (previousFeature instanceof Placemark previousFeaturePlacemark) {
            previousPlacemark = previousFeaturePlacemark;
          }
        }
        
        Placemark nextPlacemark = null;
        if (i < features.size() - 2) {
          Feature nextFeature = features.get(i + 1);
          if (nextFeature instanceof Placemark nextFeaturePlacemark) {
            nextPlacemark = nextFeaturePlacemark;
          }
        }
        getVacationElementFromKmlPlacemark(previousPlacemark, placemark, nextPlacemark, vacationElements);
      }
    }
  }
  
  /**
   * Get a {@code VacationElement} from a KML {@link Placemark}.
   * <p>
   * If the placemark is a point location (i.e. the geometry is a Point), the VacationElement will be a Location.<br/>
   * If the placemark is a track (i.e. the geometry is a LineString), the VacationElement will be a GPXTrack VacationElement with a related GPXTrack.
   * 
   * @param placemark the {@code Placemark} to derive the information from.
   * @param vacationElements the list to add the vacation elements information to.
   */
  private void getVacationElementFromKmlPlacemark(Placemark previousPlacemark, Placemark placemark, Placemark nextPlacemark, List<KmlPlacemarkImportData> vacationElements) {
    Geometry geometry = placemark.getGeometry();
    if (geometry == null) {
      LOGGER.severe("Placemark without geometry: " + KmlUtil.geometryToString(geometry));
      return;
    }
    
    if (geometry instanceof Point) {
      getLocationFromKmlPlacemark(placemark, vacationElements);
    } else if (geometry instanceof LineString) {
      getGPXTrackFromKmlPlacemark(previousPlacemark, placemark, nextPlacemark, vacationElements);
    } else {
      throw new RuntimeException("Unsupported Geometry subtype: " + geometry.getClass().getName());
    }
  }

  /**
   * Create a {@code Location} for a {@code Placemark}.
   * 
   * @param placemark The {@code Placemark} for which to create a {@code Location}.
   * @param vacationElements the list to add the {@code Location} information to.
   */
  private void getLocationFromKmlPlacemark(Placemark placemark, List<KmlPlacemarkImportData> vacationElements) {
    TravelsFactory vacationsFactory = TravelsFactory.eINSTANCE;
    Location location = vacationsFactory.createLocation();
    
    String category = getExtendedDataValue(placemark, "Category");
    if (category != null  &&  !category.trim().isEmpty()) {
      LocationCategory poiCategoryId = kmlCategoryToPOICategoryId(category);
      location.setLocationCategory(poiCategoryId);
    }
    
    String locationName = placemark.getName();
    if (locationName != null  &&  !locationName.isEmpty()) {
      location.setName(locationName);
    }
    
    // Descriptions doesn't seem interesting
    
    Double latitude = null;
    Double longitude = null;
    Location locationFromNominatim = null;
    Point point =  (Point) placemark.getGeometry();
    List<Coordinate> coordinates = point.getCoordinates();
    if (coordinates.size() == 1) {
      Coordinate coordinate = coordinates.get(0);
      latitude = coordinate.getLatitude();
      longitude = coordinate.getLongitude();
      locationFromNominatim = getLocationFromNominatimAPI(latitude, longitude);
    }
    
    fillLocationAddressFromPlacemark(location, placemark, locationFromNominatim);
    
    if (coordinates.size() == 1) {
      Coordinate coordinate = coordinates.get(0);
      location.setLatitude(coordinate.getLatitude());
      location.setLongitude(coordinate.getLongitude());
    }

    vacationElements.add(new KmlPlacemarkImportData(location, null, placemark.getAddress(), locationFromNominatim));
  }

  /**
   * Convert a KML category to a {@code LocationCategory}.
   * 
   * @param category the KML category to convert.
   * @return the corresponding {@code LocationCategory}, or {@code LocationCategory.DEFAULT_POI} if the category is not supported.
   */
  private LocationCategory kmlCategoryToPOICategoryId(String category) {
    LocationCategory poiCategoryId = kmlCategoryToLocationCategoryMap.get(category);
    
    if (poiCategoryId == null) {
     LOGGER.severe("Unsupported KML category: " + category);
     poiCategoryId = LocationCategory.DEFAULT_POI;
    }
    
    return poiCategoryId;
  }

  /**
   * Fill a {@code Location} from the information in a {@code Placemark}.
   * <p>
   * The 'address' text can have different formats:<br/>
   * {@literal <streetname> <housenumber>, <postalcode> <city>}<br/>
   * {@literal <streetname> <housenumber>, <postalcode> <city>, <country>}<br/>
   * {@literal <postalcode> <city>, <country>}<br/>
   * {@literal <postalcode> <city>}<br/>
   * <p>
   * Address information is also obtained via the NominatimAPI.
   * 
   * If the address has no country specified, we assume it is the {@code homeCountry}.
   * 
   * @param location the {@code Location} to be filled.
   * @param placemark the {@code Placemark} to obtain the information from.
   * @param locationFromNominatim location information obtained from the Nominatim API.
   */
  private void fillLocationAddressFromPlacemark(Location location, Placemark placemark, Location locationFromNominatim) {
    String placemarkAddressText = placemark.getAddress();
    
    if (placemarkAddressText == null  ||  placemarkAddressText.trim().isEmpty()) {
      return;
    }
    
    if (isKnownUnsupportedAddress(placemarkAddressText)) {
      return;
    }
    
    String[] addressParts = placemarkAddressText.split(",");
    if (addressParts.length < 1  ||  addressParts.length > 3) {
      return;
    }
    
    String part1 = addressParts[0].trim();
    String part2 = addressParts.length >= 2 ? addressParts[1].trim() : null;
    String part3 = addressParts.length == 3 ? addressParts[2].trim() : null;
    
    String country = null;
    String city = null;
    String postalCode = null;
    String street = null;
    String housenumber = null;
    
    boolean firstPartIsPostalCodeAndCity = false;
    
    // Get the country
    // if there are 3 parts, the third part is the country
    if (part3 != null) {
      country = part3;
    } else if (part2 != null){
      // the second part may be the country or postal code and city.
      // if the second part equals the country obtained from the nominatim api, we assume the second part is the country.
      // this then implies that the first part is the postal code and city.
      String countryFromNominatim = locationFromNominatim != null ? locationFromNominatim.getCountry() : null;
      if (countryFromNominatim != null  &&  countryFromNominatim.equals(part2)) {
        country = part2;
        firstPartIsPostalCodeAndCity = true;
      }
    }
    
    if (country == null) {
      country = homeCountry;
    }
    LOGGER.info("country: " + country);
    LOGGER.info("country from nominatim: " + (locationFromNominatim != null ? locationFromNominatim.getCountry() : "null"));
    
    if (addressParts.length >= 2) {
      // Get postal code and city
      String postalCodeAndCity =  firstPartIsPostalCodeAndCity ? part1 : part2;
      int spaceAfterPostalCodeIndex = getSpaceAfterPostalCodeIndex(postalCodeAndCity);
      if (spaceAfterPostalCodeIndex != -1) {
        postalCode = postalCodeAndCity.substring(0, spaceAfterPostalCodeIndex -1);
        city = postalCodeAndCity.substring(spaceAfterPostalCodeIndex + 1);
      } else {
        city = postalCodeAndCity;
      }
      LOGGER.info("postalCode: '" + postalCode + "'");
      LOGGER.info("city: '" + city + "'");
      LOGGER.info("city from nominatim: " + (locationFromNominatim != null ? locationFromNominatim.getCity() : "null"));

      // Get street and housenumber
      if (!firstPartIsPostalCodeAndCity) {
        int indexOfSpaceBeforePotentialHouseNumber = part1.lastIndexOf(" ");
        if (indexOfSpaceBeforePotentialHouseNumber == -1) {
          street = part1;
        } else {
          String potentialHousenumber = part1.substring(indexOfSpaceBeforePotentialHouseNumber + 1);
          if (potentialHousenumber.matches("[0-9][0-9]*.*")) {
            housenumber = potentialHousenumber;
            street = part1.substring(0, indexOfSpaceBeforePotentialHouseNumber);
          } else {
            street = part1;
          }
        }
      }
      LOGGER.info("street: '" + street + "'");
      LOGGER.info("street from nominatim: " + (locationFromNominatim != null ? locationFromNominatim.getStreet() : "null"));
      LOGGER.info("housenumber: '" + housenumber + "'");
      LOGGER.info("housenumber from nominatim: " + (locationFromNominatim != null ? locationFromNominatim.getHouseNumber() : "null"));
    }
    
    location.setStreet(street);
    location.setHouseNumber(housenumber);
    location.setCity(city);
    location.setCountry(country);
  }
  
  /**
   * Check if the placemark address text is a known unsupported address.
   * <p>
   * 
   * @param placemarkAddressText the placemark address to check
   * @return true if the {@code placemarkAddressText} is a known unsupported address, false otherwise.
   */
  private boolean isKnownUnsupportedAddress(String placemarkAddressText) {
    for (String address: knownUnsupportedAddresses) {
      if (address.equals(placemarkAddressText)) {
        LOGGER.severe("Recognizing unsupported address: " + placemarkAddressText);
        return true;
      }
    }
    return false;
  }

  /**
   * Get the index of the space after the postal code in a string.
   * <p>
   * The postal code is assumed to be at the start of the string.
   * <p>
   * The postal code format is country specific. For now, we only support the Netherlands and Portugal.
   * 
   * @param string the string to check
   * @return the index of the space after the postal code, or -1 if no postal code was found.
   */
  private int getSpaceAfterPostalCodeIndex(String string) {
    string = string.toLowerCase();
    // Netherlands
    if (string.matches("[0-9][0-9][0-9][0-9] [a-z][a-z] .*")) {
      return 7;
    }
    
    // Portugal
    if (string.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9][0-9] .*")) {
      return 8;
    }
    
    return -1;
  }
  
  /**
   * Get a {@code Location} from the Nominatim API based on the latitude and longitude.
   * 
   * @param latitude the latitude of the location
   * @param longitude the longitude of the location
   * @return a {@code Location} with information obtained from the Nominatim API, or null if no information could be obtained.
   */
  private Location getLocationFromNominatimAPI(Double latitude, Double longitude) {
    if (latitude == null  ||  longitude == null) {
      return null;
    }
    
    OSMLocationInfo locationInfo = performReverseGeocodeSearch(latitude, longitude);
    if (locationInfo == null) {
      return null;
    }
    
    return NominatimUtil.osmLocationInfoToLocation(locationInfo);
  }

  /**
   * Perform a reverse geocode search based on the latitude/longitude of the {@code reverseGeoCodePanel}.
   */
  private OSMLocationInfo performReverseGeocodeSearch(double latitude, double longitude) {
    if (nominatimAPI == null) {
      return null;
    }
    
    OSMLocationInfo locationInfo = null;
    try {
      locationInfo = nominatimAPI.getAddressFromMapPoint(latitude, longitude);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return locationInfo;
  }

  /**
   * Create a {@code GPXTrack} for a {@code Placemark}.
   * 
   * @param previousPlacemark The previous {@code Placemark} in the KML file, or null if there is no previous {@code Placemark}.
   * @param placemark The {@code Placemark} for which to create a {@code GPXTrack}.
   * @param nextPlacemark The next {@code Placemark} in the KML file, or null if there is no next {@code Placemark}.
   * @param vacationElements the list to add the {@code GPXTrack} information to.
   */
  private void getGPXTrackFromKmlPlacemark(Placemark previousPlacemark, Placemark placemark, Placemark nextPlacemark, List<KmlPlacemarkImportData> vacationElements) {
    TravelsFactory vacationsFactory = TravelsFactory.eINSTANCE;
    GPXTrack gpxTrack = vacationsFactory.createGPXTrack();
    
    // Only create the file reference. Leave the title empty, the file is filled in later by the application, when a vacation is specified.
    TypesFactory typesFactory = TypesFactory.eINSTANCE;
    FileReference fileReference = typesFactory.createFileReference();
    gpxTrack.setTrackReference(fileReference);

    // Create the GPX data
    GPXFactory gpxFactory = GPXFactory.eINSTANCE;
    DocumentRoot documentRoot = GpxUtil.createBasicDocumentRoot();
    
    GpxType gpxType = documentRoot.getGpx();
    
    MetadataType metadataType = gpxFactory.createMetadataType();
    metadataType.setName(createGpxTrackName(previousPlacemark, placemark, nextPlacemark));
    if (placemark.getName() != null) {
      metadataType.setKeywords(placemark.getName());
    }
    gpxType.setMetadata(metadataType);
    
    gpxType.setCreator("MyWorld Travels");
    
    TrkType track = gpxFactory.createTrkType();
    List<TrkType> tracks = gpxType.getTrk();
    tracks.add(track);
    
    TrksegType segment = gpxFactory.createTrksegType();
    List<TrksegType> segments = track.getTrkseg();
    segments.add(segment);
    
    WptType waypoint;
    List<WptType> waypoints = segment.getTrkpt();
    LineString lineString = (LineString) placemark.getGeometry();
    List<Coordinate> coordinates = lineString.getCoordinates();
    for (Coordinate coordinate: coordinates) {
      waypoint = gpxFactory.createWptType();
      waypoint.setLat(new BigDecimal(coordinate.getLatitude()));
      waypoint.setLon(new BigDecimal(coordinate.getLongitude()));
      waypoints.add(waypoint);
    }
    
    TimePrimitive timePrimitive = placemark.getTimePrimitive();
    if (timePrimitive instanceof TimeSpan timeSpan) {
      ZoneId beginZoneId = getZoneId(coordinates.get(0));
      LOGGER.info("beginZoneId = " + beginZoneId);
      String beginTimeString = timeSpan.getBegin();
      OffsetDateTime beginOffsetDateTime = OffsetDateTime.parse(beginTimeString);
      LOGGER.info("beginOffsetDateTime: " + beginOffsetDateTime.format(dateTimeFormatter));
      ZonedDateTime beginZonedDateTime = beginOffsetDateTime.atZoneSameInstant(beginZoneId);
      LocalDateTime beginTime = beginZonedDateTime.toLocalDateTime();
      LOGGER.info("beginTime: " + beginTime.format(dateTimeFormatter));        
      gpxType.setStartTime(DateUtil.localDateTimeToDate(beginTime));

      ZoneId endZoneId = getZoneId(coordinates.get(coordinates.size() - 1));
      LOGGER.info("zoneId = " + endZoneId);
      String endTimeString = timeSpan.getEnd();
      OffsetDateTime endOffsetDateTime = OffsetDateTime.parse(endTimeString);
      LOGGER.info("endOffsetDateTime: " + endOffsetDateTime.format(dateTimeFormatter));
      ZonedDateTime endZonedDateTime = endOffsetDateTime.atZoneSameInstant(endZoneId);
      LocalDateTime endTime = endZonedDateTime.toLocalDateTime();
      LOGGER.info("endTime: " + endTime.format(dateTimeFormatter));        
      gpxType.setEndTime(DateUtil.localDateTimeToDate(endTime));
    }

    
    vacationElements.add(new KmlPlacemarkImportData(gpxTrack, documentRoot, null, null));
  }
  
  /**
   * Get the {@link ZoneId} for a given {@code Coordinate}.
   * 
   * @param coordinate the {@code Coordinate} for which to get the {@code ZoneId}.
   * @return the {@code ZoneId} for the given {@code coordinate}.
   */
  private ZoneId getZoneId(Coordinate coordinate) {
    return TimeZoneRetriever.getZoneId(coordinate.getLatitude(), coordinate.getLongitude());
  }
  
  /**
   * Create the name for a GPX track from information in a {@code Placemark}.
   * 
   * @param previousPlacemark The previous {@code Placemark} in the KML file, or null if there is no previous {@code Placemark}.
   * @param placemark The {@code Placemark} for which to create a {@code GPXTrack}.
   * @param nextPlacemark The next {@code Placemark} in the KML file, or null if there is no next {@code Placemark}.
   * @return the name for the GPX track.
   */
  private String createGpxTrackName(Placemark previousPlacemark, Placemark placemark, Placemark nextPlacemark) {
    StringBuilder buf = new StringBuilder();
    
    String category = getExtendedDataValue(placemark, "Category");
    if (category != null) {
      Activity activity = kmlCategoryToActivity(category);
      if (activity != null) {
        buf.append(activity.getGpxKeywords()[0]);
      }
    }
    
    if (previousPlacemark != null) {
      Geometry geometry = previousPlacemark.getGeometry();
      if (geometry != null  &&  geometry instanceof Point) {
        buf.append(" van ").append(previousPlacemark.getName());
      }
    }
    
    if (nextPlacemark != null) {
      Geometry geometry = nextPlacemark.getGeometry();
      if (geometry != null  &&  geometry instanceof Point) {
        buf.append(" naar ").append(nextPlacemark.getName());
      }
    }
    
    return buf.toString();
  }
  
  /**
   * Convert a KML category to an {@code Activity}.
   * 
   * @param kmlCategory the KML category to convert.
   * @return the corresponding {@code Activity}, or null if the category is "Moving".
   * @throws RuntimeException if the category is unknown.
   */
  private static Activity kmlCategoryToActivity(String kmlCategory) {
    if (kmlCategory.equals("Moving")) {
      return null;
    }
    
    Activity activity = kmlCategoryToActivityMap.get(kmlCategory);
    
    if (activity == null) {
      throw new RuntimeException("Unknown KML Category: " + kmlCategory);
    }
    
    return activity;
  }
  
  /**
   * Get a specific value from the {@code ExtendedData} of a {@code Placemark}.
   * 
   * @param placemark the {@code Placemark} from which to get the value.
   * @param name the name of the value to get.
   * @return the value of the specified name, or null if the name is not found.
   */
  public static String getExtendedDataValue(Placemark placemark, String name) {
    ExtendedData extendedData = placemark.getExtendedData();
    
    if (extendedData == null) {
      return null;
    }
    
    for (Data data: extendedData.getData()) {
      if (name.equals(data.getName())) {
        return data.getValue();
      }
    }
    
    return null;
  }
  
  /**
   * Builder class to set all properties for the importer.
   */
  public static class Builder {
    /**
     * The KML file from which information is to be imported.
     */
    private File kmlFile;
    
    /**
     * An instance of the {@link NominatimAPI} used to perform a reverse geocode search.
     */
    private NominatimAPI nominatimAPI;
    
    /**
     * The users home country, used as default if a {@code Placemark} has no country filled in.
     */
    private String homeCountry;
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param kmlFile the KML file from which information is to be imported.
     * @param nominatimAPI an instance of the {@link NominatimAPI} used to perform a reverse geocode search.
     */
    public Builder(File kmlFile, NominatimAPI nominatimAPI) {
      this.kmlFile = kmlFile;
      this.nominatimAPI = nominatimAPI;
    }
    
    /**
     * Set the users home country, used as default if a {@code Placemark} has no country filled in.
     * 
     * @param homeCountry the users home country
     * @return the builder instance
     */
    public Builder setHomeCountry(String homeCountry) {
      this.homeCountry = homeCountry;
      
      return this;
    }
    
    /**
     * Build the {@code KmlFileImporter} instance.
     * 
     * @return the {@code KmlFileImporter} instance
     */
    public KmlFileImporter build() {
      return new KmlFileImporter(this);
    }
    
  }
}
