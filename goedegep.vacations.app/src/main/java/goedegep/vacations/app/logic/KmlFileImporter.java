package goedegep.vacations.app.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
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

import javax.net.ssl.HttpsURLConnection;

import com.atlis.location.nominatim.NominatimAPI;
import com.atlis.location.nominatim.OSMLocationInfo;
import com.google.gson.Gson;

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
import goedegep.gpx.app.Activity;
import goedegep.gpx.model.DocumentRoot;
import goedegep.gpx.model.GPXFactory;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.MetadataType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.poi.app.LocationCategory;
import goedegep.poi.model.POICategoryId;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.util.datetime.DateUtil;
import goedegep.util.datetime.TimeZoneRetriever;
import goedegep.vacations.model.GPXTrack;
import goedegep.vacations.model.Location;
import goedegep.vacations.model.VacationsFactory;

public class KmlFileImporter {
  private static final Logger LOGGER = Logger.getLogger(KmlFileImporter.class.getName());
  
  private static Map<String, Activity> kmlCategoryToActivityMap = new HashMap<>();
  private static Map<String, LocationCategory> kmlCategoryToPOICategoryIdMap = new HashMap<>();
  private static String[] knownUnsupportedAddresses = {
      "C. de los Helechos, s/n, 38611, Santa Cruz de Tenerife, Spanje",
      "C. Cupido, 12, 38400 Puerto de la Cruz, Santa Cruz de Tenerife, Spanje"
  };
  
  private Gson gson = new Gson();      // JSON parser
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
    
    // initialize the kmlCategoryToPOICategoryIdMap
    kmlCategoryToPOICategoryIdMap.put("Autoverhuur", LocationCategory.CAR_RENTAL);
    kmlCategoryToPOICategoryIdMap.put("Bakkerij", LocationCategory.SHOP);
    kmlCategoryToPOICategoryIdMap.put("Bar", LocationCategory.BAR);
    kmlCategoryToPOICategoryIdMap.put("Barbecue", LocationCategory.RESTAURANT);
    kmlCategoryToPOICategoryIdMap.put("Bergkabelbaan", LocationCategory.CABLE_CAR);
    kmlCategoryToPOICategoryIdMap.put("Bergtop", LocationCategory.MOUNTAIN);
    kmlCategoryToPOICategoryIdMap.put("Bierwinkel", LocationCategory.SHOP);
    kmlCategoryToPOICategoryIdMap.put("Cultureel monument", LocationCategory.MONUMENT);
    kmlCategoryToPOICategoryIdMap.put("Station bergspoorweg", LocationCategory.RAILWAY_STATION);
    kmlCategoryToPOICategoryIdMap.put("Boerenmarkt", LocationCategory.MARKET);
    kmlCategoryToPOICategoryIdMap.put("Buffet", LocationCategory.RESTAURANT);
    kmlCategoryToPOICategoryIdMap.put("Buitenzwembad", LocationCategory.SWIMMING_POOL);
    kmlCategoryToPOICategoryIdMap.put("Bushalte", LocationCategory.BUS_STOP);
    kmlCategoryToPOICategoryIdMap.put("Cadeauwinkel", LocationCategory.SHOP);
    kmlCategoryToPOICategoryIdMap.put("Dierentuin", LocationCategory.ZOO);
    kmlCategoryToPOICategoryIdMap.put("Delicatessenwinkel", LocationCategory.SHOP);
    kmlCategoryToPOICategoryIdMap.put("Eiland", LocationCategory.ISLAND);
    kmlCategoryToPOICategoryIdMap.put("Espressobar", LocationCategory.CAFE);
    kmlCategoryToPOICategoryIdMap.put("Fastfood", LocationCategory.RESTAURANT);
    kmlCategoryToPOICategoryIdMap.put("Gebouw", LocationCategory.BUILDING);
    kmlCategoryToPOICategoryIdMap.put("Grieks", LocationCategory.RESTAURANT);
    kmlCategoryToPOICategoryIdMap.put("Herberg", LocationCategory.GUESTHOUSE);
    kmlCategoryToPOICategoryIdMap.put("Historisch herkenningspunt", LocationCategory.DEFAULT_POI);
    kmlCategoryToPOICategoryIdMap.put("Hotel", LocationCategory.HOTEL);
    kmlCategoryToPOICategoryIdMap.put("Italiaans", LocationCategory.RESTAURANT);
    kmlCategoryToPOICategoryIdMap.put("Kabelbaanstation", LocationCategory.RAILWAY_STATION);
    kmlCategoryToPOICategoryIdMap.put("Kantoor van lokale/provinciale overheid", LocationCategory.GOVERMENT);
    kmlCategoryToPOICategoryIdMap.put("Koffiehuis", LocationCategory.CAFE);
    kmlCategoryToPOICategoryIdMap.put("Internationaal vliegveld", LocationCategory.AIRPORT);
    kmlCategoryToPOICategoryIdMap.put("Lodge", LocationCategory.LODGE);
    kmlCategoryToPOICategoryIdMap.put("Luchthaven", LocationCategory.AIRPORT);
    kmlCategoryToPOICategoryIdMap.put("Markt", LocationCategory.MARKET);
    kmlCategoryToPOICategoryIdMap.put("Momo restaurant", LocationCategory.RESTAURANT);
    kmlCategoryToPOICategoryIdMap.put("Nationaal park", LocationCategory.PARK);
    kmlCategoryToPOICategoryIdMap.put("Nepalees", LocationCategory.RESTAURANT);
    kmlCategoryToPOICategoryIdMap.put("Observatieplatform", LocationCategory.SCENIC_VIEWPOINT);
    kmlCategoryToPOICategoryIdMap.put("Ov-station", LocationCategory.RAILWAY_STATION);
    kmlCategoryToPOICategoryIdMap.put("Pannenkoekenrestaurant", LocationCategory.RESTAURANT);
    kmlCategoryToPOICategoryIdMap.put("Panoramisch uitzicht", LocationCategory.SCENIC_VIEWPOINT);
    kmlCategoryToPOICategoryIdMap.put("Park", LocationCategory.PARK);
    kmlCategoryToPOICategoryIdMap.put("Parkeergarage", LocationCategory.PARKING);
    kmlCategoryToPOICategoryIdMap.put("Parkeerplaats", LocationCategory.PARKING);
    kmlCategoryToPOICategoryIdMap.put("Paspoortkantoor", LocationCategory.BORDER_CROSSING);
    kmlCategoryToPOICategoryIdMap.put("Pension", LocationCategory.GUESTHOUSE);
    kmlCategoryToPOICategoryIdMap.put("Plein", LocationCategory.SQUARE);
    kmlCategoryToPOICategoryIdMap.put("Rivier", LocationCategory.RIVER);
    kmlCategoryToPOICategoryIdMap.put("Restaurant", LocationCategory.RESTAURANT);
    kmlCategoryToPOICategoryIdMap.put("Restaurant", LocationCategory.RESTAURANT);
    kmlCategoryToPOICategoryIdMap.put("Snackbar", LocationCategory.RESTAURANT);
    kmlCategoryToPOICategoryIdMap.put("Skioord", LocationCategory.SKI_RESORT);
    kmlCategoryToPOICategoryIdMap.put("Supermarkt", LocationCategory.SHOP);
    kmlCategoryToPOICategoryIdMap.put("Tankstation", LocationCategory.PETROL_STATION);
    kmlCategoryToPOICategoryIdMap.put("Thais", LocationCategory.RESTAURANT);
    kmlCategoryToPOICategoryIdMap.put("Toeristische attractie", LocationCategory.TOURIST_ATTRACTION);
    kmlCategoryToPOICategoryIdMap.put("Treinstation", LocationCategory.RAILWAY_STATION);
    kmlCategoryToPOICategoryIdMap.put("Vakantiecomplex", LocationCategory.HOTEL);
    kmlCategoryToPOICategoryIdMap.put("Veerbootterminal", LocationCategory.FERRY);
    kmlCategoryToPOICategoryIdMap.put("Winkelcentrum", LocationCategory.SHOPPING_CENTER);
    kmlCategoryToPOICategoryIdMap.put("Woods", LocationCategory.FOREST);
  }
  
  /**
   * Constructor
   */
  private KmlFileImporter(Builder builder) {
    this.kmlFile = builder.kmlFile;
    this.nominatimAPI = builder.nominatimAPI;
    this.homeCountry = builder.homeCountry;
  }

  /**
   * Get a list of <code>VacationElement</code>s from a KML file.
   * <p>
   * For each {@code Placemark} in the KML file a VacationElement is created.
   * If the {@code geometry} in the {@code Placemark} is a {@code Point}, a {@code Location} is derived from the information in the {@code Placemark}.
   * If the {@code geometry} in the {@code Placemark} is a {@code LineString}, a {@code GPXTrack} VacationElement is derived from the information in the {@code Placemark} together with
   * a GPX {@DocumentType}, which shall be stored to the file referred to by the {@code GPXTrack}.
   * 
   * @param file The KML file to get the locations from.
   * @return A list of {@code KmlPlacemarkImportData}. A list of all locations (Placemarks) in the KML file. 
   *
   */
  public List<KmlPlacemarkImportData> getLocationsFromKmlFile() {
    List<KmlPlacemarkImportData> vacationElements = new ArrayList<>();
    
    Kml kml = Kml.unmarshal(kmlFile);
    
    Feature feature = kml.getFeature();
    getLocationsFromKmlFeature(null, feature, null, vacationElements);
        
    return vacationElements;
  }
  
  private void getLocationsFromKmlFeature(Feature previousFeature, Feature feature, Feature nextFeature, List<KmlPlacemarkImportData> vacationElements) {
    switch (feature.getClass().getName()) {
    case "de.micromata.opengis.kml.v_2_2_0.Document":
      getLocationsFromKmlDocument((Document) feature, vacationElements);
      break;
      
    case "de.micromata.opengis.kml.v_2_2_0.Folder":
      getLocationsFomKmlFolder((Folder) feature, vacationElements);
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

  private void getLocationsFromKmlDocument(Document document, List<KmlPlacemarkImportData> vacationElements) {
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
      getLocationsFromKmlFeature(previousFeature, feature, nextFeature, vacationElements);
    }
  }

  /**
   * Get the locations from within a KML Folder.
   * 
   * @param folder the KML Folder to get the locations from
   * @param locations the list to add the locations to
   */
  private void getLocationsFomKmlFolder(Folder folder, List<KmlPlacemarkImportData> vacationElements) {
    List<Feature> features = folder.getFeature();
    for (int i = 0; i < features.size() - 1; i++) {
      Feature feature = features.get(i);
      
      if (feature instanceof Folder) {
        getLocationsFomKmlFolder((Folder) feature, vacationElements);
      } else if (feature instanceof Placemark placemark) {
        Placemark previousPlacemark = null;
        if (i > 0) {
          Feature previousFeature = features.get(i - 1);
          if (previousFeature instanceof Placemark) {
            previousPlacemark = (Placemark) previousFeature;
          }
        }
        
        Placemark nextPlacemark = null;
        if (i < features.size() - 2) {
          Feature nextFeature = features.get(i + 1);
          if (nextFeature instanceof Placemark) {
            nextPlacemark = (Placemark) nextFeature;
          }
        }
        getVacationElementFromKmlPlacemark(previousPlacemark, placemark, nextPlacemark, vacationElements);
      }
    }
  }
  
  /**
   * Get a {@code VacationElement} from a KML Placemark.
   * <p>
   * If the Placemark is a point location (i.e. the geometry is a Point), the VacationElement will be a Location.<br/>
   * If the Placemark is a track (i.e. the geometry is a LineString), the VacationElement will be a GPXTrack VacationElement with a related GPXTrack.
   * 
   * @param placemark the {@code Placemark} to derive the information from.
   * @param vacationElements the {@code Map} to add the element to.
   */
  private void getVacationElementFromKmlPlacemark(Placemark previousPlacemark, Placemark placemark, Placemark nextPlacemark, List<KmlPlacemarkImportData> vacationElements) {
    Geometry geometry = placemark.getGeometry();
    if (geometry == null) {
      LOGGER.severe("Placemark without geometry: " + KmlUtil.geometryToString(geometry));
      return;
    }
    
    if (geometry instanceof Point point) {
      getLocationFromKmlPlacemark(placemark, vacationElements);
    } else if (geometry instanceof LineString lineString) {
      getGPXTrackFromKmlPlacemark(previousPlacemark, placemark, nextPlacemark, vacationElements);
    } else {
      throw new RuntimeException("Unsupported Geometry subtype: " + geometry.getClass().getName());
    }
  }

  /**
   * Create a <code>Location</code> for a <code>Placemark</code>.
   * 
   * @param placemark The <code>Placemark</code> for which to create a <code>Location</code>.
   * @param vacationElements the {@code Map} to add the element to.
   */
  private void getLocationFromKmlPlacemark(Placemark placemark, List<KmlPlacemarkImportData> vacationElements) {
    VacationsFactory vacationsFactory = VacationsFactory.eINSTANCE;
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
//    String description = placemark.getDescription();
//    if (description != null) {
//      description = description.trim();
//    }
//    if (description != null  &&  !description.isEmpty()) {
//      location.setDescription(description);
//    }
    
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

  private LocationCategory kmlCategoryToPOICategoryId(String category) {
    LocationCategory poiCategoryId = kmlCategoryToPOICategoryIdMap.get(category);
    
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
   * @param latitude the latitude coordinate of the location.
   * @param longitude the longitude coordinate of the location.
   */
  private void fillLocationAddressFromPlacemark(Location location, Placemark placemark, Location locationFromNominatim) {
    String placemarkAddressText = placemark.getAddress();
    
    if (isKnownUnsupportedAddress(placemarkAddressText)) {
      return;
    }
    
    String[] addressParts = placemarkAddressText.split(",");
    if (addressParts.length < 1  ||  addressParts.length > 3) {
      return;
//      throw new RuntimeException("Unsupported address format: " + placemarkAddressText);
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
  
  private boolean isKnownUnsupportedAddress(String placemarkAddressText) {
    for (String address: knownUnsupportedAddresses) {
      if (address.equals(placemarkAddressText)) {
        LOGGER.severe("Recognizing unsupported address: " + placemarkAddressText);
        return true;
      }
    }
    return false;
  }

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
   * Perform a reverse geocode search based on the latitude/longitude of the <code>reverseGeoCodePanel</code>.
   * <p>
   * The result is handed over to the {@code locationInfosPanel}.
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
   * Create a <code>GPXTrack</code> for a <code>Placemark</code>.
   * 
   * @param placemark The <code>Placemark</code> for which to create a <code>GPXTrack</code>.
   * @param vacationElements the {@code Map} to add the element to.
   */
  private void getGPXTrackFromKmlPlacemark(Placemark previousPlacemark, Placemark placemark, Placemark nextPlacemark, List<KmlPlacemarkImportData> vacationElements) {
    VacationsFactory vacationsFactory = VacationsFactory.eINSTANCE;
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
//    metadataType.setDesc(placemark.getDescription()); Description doesn't seem interesting
    if (placemark.getName() != null) {
      metadataType.setKeywords(placemark.getName());
    }
    gpxType.setMetadata(metadataType);
    
    gpxType.setCreator("Google Maps Timeline");
    
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
  
  private ZoneId getZoneId(Coordinate coordinate) {
    return TimeZoneRetriever.getZoneId(coordinate.getLatitude(), coordinate.getLongitude());
//    String lat = String.valueOf(coordinate.getLatitude());
//    String lon = String.valueOf(coordinate.getLongitude());
//
//    String urlString = "https://api.geotimezone.com/public/timezone?latitude=" + lat + "&longitude=" + lon;
//    try {
//      HttpsURLConnection connection = (HttpsURLConnection) new URL(urlString).openConnection();
//      String responseStr = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
//      LOGGER.info("Response: " + responseStr);
//
//      GeoTimeZoneResponse geoTimeZoneResponse = gson.fromJson(responseStr, GeoTimeZoneResponse.class);
//      LOGGER.info("geoTimeZoneResponse: " + geoTimeZoneResponse.toString());
//      
//      String ianaTimeZone = geoTimeZoneResponse.getIana_timezone();
//      if (ianaTimeZone != null) {
//        ZoneId zoneId = ZoneId.of(ianaTimeZone);
//        return zoneId;
//      } else if (geoTimeZoneResponse.getTimezone_abbreviation() != null) {
//        ZoneId zoneId = ZoneId.of(geoTimeZoneResponse.getTimezone_abbreviation());
//        LOGGER.info("ZoneId from abbreviation: " + zoneId);
//        return zoneId;
//      } else if (geoTimeZoneResponse.getOffset() != null) {
//        String offset = geoTimeZoneResponse.getOffset();
//        offset = offset.substring(3);
//        ZoneId zoneId = ZoneId.of(offset);
//        LOGGER.info("ZoneId from offset: " + zoneId);
//        return zoneId;
//      }
//      
//      
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    
//    return null;
  }
  
  /**
   * Create the name for a GPX track from information in the Placemarks
   * 
   * @param previousPlacemark
   * @param placemark
   * @param nextPlacemark
   * @return
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
      if (geometry != null  &&  geometry instanceof Point point) {
        buf.append(" van ").append(previousPlacemark.getName());
      }
    }
    
    if (nextPlacemark != null) {
      Geometry geometry = nextPlacemark.getGeometry();
      if (geometry != null  &&  geometry instanceof Point point) {
        buf.append(" naar ").append(nextPlacemark.getName());
      }
    }
    
    return buf.toString();
  }
  
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
   * Get a specific value from the ExtededData of a Placemark.
   * 
   * @param placemark
   * @param name
   * @return
   */
  public static String getExtendedDataValue(Placemark placemark, String name) {
    ExtendedData extendedData = placemark.getExtendedData();
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
    
    private NominatimAPI nominatimAPI;
    
    /**
     * The users home country, used as default if a {@code Placemark} has no country filled in.
     */
    private String homeCountry;
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param kmlFile the KML file from which information is to be imported.
     */
    public Builder(File kmlFile, NominatimAPI nominatimAPI) {
      this.kmlFile = kmlFile;
      this.nominatimAPI = nominatimAPI;
    }
    
    public Builder setHomeCountry(String homeCountry) {
      this.homeCountry = homeCountry;
      
      return this;
    }
    
    public KmlFileImporter build() {
      return new KmlFileImporter(this);
    }
    
  }
}
