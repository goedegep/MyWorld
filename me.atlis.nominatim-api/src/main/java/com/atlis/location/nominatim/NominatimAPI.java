package com.atlis.location.nominatim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import de.micromata.opengis.kml.v_2_2_0.Kml;
import goedegep.util.url.UrlUtil;
import javafx.util.Pair;

/**
 * This class provides access to the Open Street Map Nominatim web API.
 * <p>
 * See https://nominatim.org/ for all information about the Nominatim web API.<br/>
 * See https://nominatim.org/release-docs/develop/ for the documentation.<br/>
 * 
 * Known servers of the Nominatim web API:
 * <ul>
 * <li>https://nominatim.openstreetmap.org/</li>
 * </ul>
 * <b>Overview of the web API and how it is supported by this class</b>
 * The web API has the following endpoints for querying the data:
 * <ul>
 * <li>
 * /search - search OSM objects by name or type</br>
 * The search API allows you to look up a location from a textual description. Nominatim supports structured as well as free-form search queries.
 * 
 * </li>
 * <li>
 * /reverse - search OSM object by their location</br>
 * Currently not supported
 * </li>
 * <li>
 * /lookup - look up address details for OSM objects by their ID</br>
 * Currently not supported
 * </li>
 * <li>
 * /status - query the status of the server</br>
 * Currently not supported
 * </li>
 * <li>
 * /deletable - list objects that have been deleted in OSM but are held back in Nominatim in case the deletion was accidental</br>
 * Currently not supported
 * </li>
 * <li>
 * /polygons - list of broken polygons detected by Nominatim</br>
 * Currently not supported
 * </li>
 * <li>
 * /details - show internal details for an object (for debugging only)</br>
 * Currently not supported
 * </li>
 * </ul>
 * 
 * This class is an upgraded version of the original by michaelassraf.
 * 
 * @author michaelassraf, Peter Goedegebure
 */
public class NominatimAPI {
  private static final Logger LOGGER = Logger.getLogger(NominatimAPI.class.getName());
  private static final String NOMINATIM_SERVER_URL = "https://nominatim.openstreetmap.org/";   // Default server.
  private static final String DEFAULT_LANGUAGE_CODE = "en";

  static Gson gson = new Gson();      // JSON parser

  /**
   * URL of the nominatim API server to use. E.g.: "https://nominatim.openstreetmap.org/".
   */
  private String serverUrl;

  // key/value pairs for parameters and their values.
  private static final String PARAM_FORMAT = "format";                       // Response Format: [html|json]
  private static final String PARAM_FORMAT_VALUE = "jsonv2";
  
  private static final String PARAM_ACCEPT_LANGUAGE = "accept-language";      // Language of results: comma separated list of language specifications (2 character ISO-639 language code, optionally followed bij '-' and a 2 character ISO-3166 country code)
  private static final String PARAM_ACCEPT_LANGUAGE_VALUE = "en";
  
  private static final String PARAM_ZOOM = "zoom";                            // Level of detail required for the address (18 is max level of detail)
  private static final String PARAM_ZOOM_VALUE = "18";
  
//  private static final String PARAM_OSM_TYPE = "osm_type";                    // A specific OSM node(N), way(W) or relation(R) to return an address for. Needs an osm_id=<value> parameter. Use either this or lat/lon in 'reverse' query.
//  private static final String PARAM_OSM_TYPE_VALUE = "N";                     // Use the node type
  
  private static final String PARAM_ADDRESS_DETAILS = "addressdetails";       // Include a breakdown of the address into elements
  private static final String PARAM_ADDRESS_DETAILS_VALUE = "1";              // true 

  private static final String PARAM_LATITUDE = "lat";
  private static final String PARAM_LONGITUDE = "lon";
  private static final String PARAM_FREE_FORMAT_QUERY = "q";
  
//  private static final String PARAM_POLYGON_GEOJSON = "polygon_geojson";
  private static final String PARAM_POLYGON_GEOJSON = "polygon_kml";
  private static final String PARAM_POLYGON_GEOJSON_VALUE = "1";

  private static final String ENDPOINT_FOR_GECODING = "search";
  private static final String ENDPOINT_FOR_REVERSE_GECODING = "reverse";

  private String languageCode = PARAM_ACCEPT_LANGUAGE_VALUE;
  

  /**
   * Factory method to obtain an instance of the NominatimAPI for a specific URL.
   * 
   * @param serverUrl URL of the nominatim API server to use.
   * @return an instance of the NominatimAPI for the specified <code>serverUrl</code>
   */
  public static NominatimAPI with(String serverUrl) {
    GsonBuilder builder = new GsonBuilder(); 
    builder.registerTypeAdapter(OSMLocationInfo.class, new OSMLocationInfoAdapter()); 
    builder.setPrettyPrinting(); 
    gson = builder.create();  

    
    "".concat(serverUrl);
    
    NominatimAPI nominatimAPI = new NominatimAPI();
    
    nominatimAPI.serverUrl = serverUrl;
    
    return nominatimAPI;
  }

  /**
   * Factory method to obtain an instance of the NominatimAPI for the default URL and a specific Locale (language).
   * 
   * @param locale preferred language.
   * @return an instance of the NominatimAPI for the specified <code>locale</code>
   */
  public static NominatimAPI with(Locale locale) {
    
    NominatimAPI nominatimAPI = NominatimAPI.with(NOMINATIM_SERVER_URL);
    
    String languageCode = locale.getLanguage();
    LOGGER.info("languageCode:" + languageCode);
    if (languageCode.equals(DEFAULT_LANGUAGE_CODE)) {
      // use default language
      nominatimAPI.setAcceptLanguage(languageCode);
    } else {
      // use default locale language with default language as backup
      nominatimAPI.setAcceptLanguage(languageCode + "," + DEFAULT_LANGUAGE_CODE);
    }
    
    return nominatimAPI;
  }
  
  /**
   * Search for a location.
   * 
   * @param country A country, may be null.
   * @param city A city within the <code>country</code>, may be null.
   * @param street A street with the <code>city</code>, may be null.
   * @param housenumber A housenumber within the <code>street</code>, may be null. This parameter is ignored if <code>street</code> is null.
   * @return a list of OSMLocationInfo with the search results.
   * @throws IOException 
   */
  public List<OSMLocationInfo> searchHierarchical(String country, String city, String street, String housenumber) throws IOException {
    LOGGER.severe("=> country=" + country + ", city=" + city + ", street=" + street + ", housenumber=" + housenumber);

    String url = createBaseUrl(ENDPOINT_FOR_GECODING);

    Map<String, String> params = new HashMap<>();

    if (country != null) {
      params.put("country", country);
    }

    if (city != null) {
      params.put("city", city);
    }

    if (street != null) {
      params.put("street", housenumber != null ? housenumber + " " + street : street);
    }

    // Standard parameters for this type of query
    params.put(PARAM_FORMAT, PARAM_FORMAT_VALUE);
    params.put(PARAM_ZOOM, PARAM_ZOOM_VALUE);
    params.put(PARAM_ACCEPT_LANGUAGE, languageCode);
    params.put(PARAM_ADDRESS_DETAILS, PARAM_ADDRESS_DETAILS_VALUE);
    params.put(PARAM_POLYGON_GEOJSON, PARAM_POLYGON_GEOJSON_VALUE);

    try {
      url = UrlUtil.appendGetParamsToUrl(url, params, true);
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
      return null;
    }

    LOGGER.severe("url=" + url);

    String responseStr = performQuery(url);
    
    return getLocationInfosFromOSMResponse(responseStr);
  }
  

  /**
   * Get the address for a location specified by coordinates (known as reverse geocoding).
   * 
   * @param mapPoint a <code>MapPoint</code> specifying the location for which an address is requested.
   *        This parameter may not be <code>null</code>, and both latitude and longitude have to be set.
   * @return the <code>OSMLocationInfo</code> obtained for the <code>mapPoint</code>, or null in case the information couldn't be obtained.
   * @throws IOException 
   */
  public OSMLocationInfo getAddressFromMapPoint(double latitude, double longitude) throws IOException {
    LOGGER.severe("=>");

    String url = createBaseUrl(ENDPOINT_FOR_REVERSE_GECODING);
    Map<String, String> params = new HashMap<>();
    params.put(PARAM_LATITUDE, String.valueOf(latitude));
    params.put(PARAM_LONGITUDE, String.valueOf(longitude));

    // Standard parameters for this type of query
    params.put(PARAM_FORMAT, PARAM_FORMAT_VALUE);
    params.put(PARAM_ZOOM, PARAM_ZOOM_VALUE);
    params.put(PARAM_ACCEPT_LANGUAGE, languageCode);
    params.put(PARAM_ADDRESS_DETAILS, PARAM_ADDRESS_DETAILS_VALUE);
    
    try {
      url = UrlUtil.appendGetParamsToUrl(url, params, true);
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
      return null;
    }

    LOGGER.severe("url=" + url);
    String responseStr = performQuery(url);
    OSMLocationInfo openStreetMapResponse = gson.fromJson(responseStr, OSMLocationInfo.class);
    
    if (openStreetMapResponse == null || openStreetMapResponse.getError() != null) {
      LOGGER.severe("can't reverse geocode location. error from open street map : " + 
          (openStreetMapResponse != null ? openStreetMapResponse.getError() + " url is " + url : " open street map error is null") +
          " object we tried to find is " + String.valueOf(latitude) + ", " + String.valueOf(longitude));
      LOGGER.severe("<= null");
      return null;
    }
    
    if (openStreetMapResponse != null) {
      openStreetMapResponse.setNominatimResponse(responseStr);
    }
        
    LOGGER.severe("<= " + ToStringBuilder.reflectionToString(openStreetMapResponse));
    return openStreetMapResponse;
  }

  /**
   * Perform a free text search.
   * 
   * @param searchText the free text to search with.
   * @return the <code>OSMLocationInfo</code> obtained for the <code>searchText</code>, or null in case the information couldn't be obtained.
   */
  public List<OSMLocationInfo> freeTextSearch(String searchText) throws IOException {
    if (searchText == null) {
      throw new IllegalArgumentException("searchText may not be null");
    }
    
    LOGGER.severe("=> searchText=" + searchText);
    
    String url = createBaseUrl(ENDPOINT_FOR_GECODING);
    Map<String, String> params = new HashMap<>();
    params.put(PARAM_FREE_FORMAT_QUERY, searchText);

    // Standard parameters for this type of query
    params.put(PARAM_FORMAT, PARAM_FORMAT_VALUE);
    params.put(PARAM_ZOOM, PARAM_ZOOM_VALUE);
    params.put(PARAM_ACCEPT_LANGUAGE, languageCode);
    params.put(PARAM_ADDRESS_DETAILS, PARAM_ADDRESS_DETAILS_VALUE);
    params.put(PARAM_POLYGON_GEOJSON, PARAM_POLYGON_GEOJSON_VALUE);
    
    try {
      url = UrlUtil.appendGetParamsToUrl(url, params, true);
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
      return null;
    }

    LOGGER.severe("url=" + url);
        
    String responseStr = performQuery(url);

    return getLocationInfosFromOSMResponse(responseStr);
  }
  
  private List<OSMLocationInfo> getLocationInfosFromOSMResponse(String responseStr) {
    List<OSMLocationInfo> openStreetMapResponses = null;
    
    openStreetMapResponses = gson.fromJson(responseStr, new TypeToken<List<OSMLocationInfo>>() {}.getType());
    
    /*
     * In the responses:
     * - the response string is added (for testing purposes)
     * - the textual <code>geokml</code>, if available, is unmarshalled to a Kml structure
     */
    if (openStreetMapResponses != null) {
      for (OSMLocationInfo osmLocationInfo: openStreetMapResponses) {
        
        osmLocationInfo.setNominatimResponse(responseStr);
        
        unmarshallGeoKml(osmLocationInfo);
      }
    } else {
      LOGGER.severe("NO result");
    }
    
    return openStreetMapResponses;
  }
  
  private void unmarshallGeoKml(OSMLocationInfo osmLocationInfo) {
    String kmlText = osmLocationInfo.getGeokml();
    LOGGER.info("kml: " + kmlText);
    if (kmlText != null) {
      StringBuilder buf = new StringBuilder();
      buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
      buf.append("<kml xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:xal=\"urn:oasis:names:tc:ciq:xsdschema:xAL:2.0\" xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">");
      buf.append("<Placemark>");
      buf.append(kmlText);
      buf.append("</Placemark>");
      buf.append("</kml>");
      Kml kml = Kml.unmarshal(buf.toString());
      osmLocationInfo.setKml(kml);
//      kml.marshal();
      
      osmLocationInfo.setGeokml(null);
    }
  }
  
  /**
   * Get POI type name and value from an OSMAddress.
   * <p>
   * For every POI type the Nominatim API uses a different attribute in the address. E.g. monument=name, attraction=name.<br/>
   * This method returns the name of the attribute (actually a POI type) and its value (the name).
   * 
   * @param address the <code>OSMResponseAddress</code> from which the information is to be obtained (may not be null).
   * @return the POI type name and value from the <code>address</address>, or null if the information isn't available in the address.
   * @throws RuntimeException if more than one POI type is found.
   */
  public static Pair<String, String> getPoiInfoFromAddress(OSMAddress address) {
    String poiType = null;
    String poiValue = null;
    
    if (address.getAerodrome() != null) {
      poiType = "aerodrome";
      poiValue = address.getAerodrome();
    }

    if (address.getAttraction() != null) {
      if (poiType != null) {
        throw new RuntimeException("Found 'attraction' while type already set to '" + poiType + "'");
      }
      poiType = "attraction";
      poiValue = address.getAttraction();
    }
    
    if (address.getCastle() != null) {
      if (poiType != null) {
        throw new RuntimeException("Found 'castle' while type already set to '" + poiType + "'");
      }
      poiType = "castle";
      poiValue = address.getCastle();
    }
    
    if (address.getHospital() != null) {
      if (poiType != null) {
        throw new RuntimeException("Found 'hospital' while type already set to '" + poiType + "'");
      }
      poiType = "hospital";
      poiValue = address.getHospital();
    }
    
    if (address.getMemorial() != null) {
      if (poiType != null) {
        throw new RuntimeException("Found 'memorial' while type already set to '" + poiType + "'");
      }
      poiType = "memorial";
      poiValue = address.getMemorial();
    }
    
    if (address.getMonument() != null) {
      if (poiType != null) {
        throw new RuntimeException("Found 'monument' while type already set to '" + poiType + "'");
      }
      poiType = "monument";
      poiValue = address.getMonument();
    }
    
    if (address.getMuseum() != null) {
      if (poiType != null) {
        throw new RuntimeException("Found 'museum' while type already set to '" + poiType + "'");
      }
      poiType = "museum";
      poiValue = address.getMuseum();
    }    
    
    if (address.getTheme_park() != null) {
      if (poiType != null) {
        throw new RuntimeException("Found 'theme park' while type already set to '" + poiType + "'");
      }
      poiType = "theme park";
      poiValue = address.getTheme_park();
    }    
    
    if (address.getTourism() != null) {
      if (poiType != null) {
        throw new RuntimeException("Found 'tourism' while type already set to '" + poiType + "'");
      }
      poiType = "tourism";
      poiValue = address.getTourism();
    }
    
    if (poiType != null) {
      return new Pair<>(poiType, poiValue);
    } else {
      return null;
    }
  }

  /**
   * Overrides default accept language. Languages at:
   * http://wiki.openstreetmap.org/wiki/Nominatim/Country_Codes
   *
   * @param language
   */
  public void setAcceptLanguage(String languageCode) {
    LOGGER.info("languageCode:" + languageCode);
    
    this.languageCode = languageCode;
  }
  

  /**
   * Perform an nominatim query.
   * 
   * @param mapPoint
   * @param address
   * @param endpoint
   * @return
   */
  private String performQuery(String url) throws IOException {
    LOGGER.severe("=>");

    String responseStr;
      HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
      connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

      responseStr = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

    LOGGER.severe("Open street map response: " + responseStr);
    return responseStr;
  }
  
  /**
   * Create the base part of the URL.
   * <p>
   * This consists of the <code>serverUrl</code>, a URL delimiter, the specified <code>endPoint</code> and the parameters start delimiter.
   * 
   * @param endPoint a Nominatim endpoint.
   * @return the created base part of the URL.
   */
  private String createBaseUrl(String endPoint) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(serverUrl);
    buf.append(UrlUtil.URL_DELIMETER);
    buf.append(endPoint);
    buf.append(UrlUtil.START_PARAM_URL_DELIMETER);
    
    return buf.toString();
  }
}
