package goedegep.poi.app;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.poi.model.POICategoryId;
import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import javafx.scene.image.Image;

public enum LocationCategory {
  AIRPORT("Airport", ImageResource.AIRPORT),
  AMUSEMENT("Amusement", ImageResource.ROLLERCOASTER),
  AQUADUCT("Aquaduct", ImageResource.AQUADUCT),
  ATM("ATM", ImageResource.ATM),
  AUTOMOBILE_CLUB("Automobile club", ImageResource.AUTOMOBILE_CLUB),
  BANK("Bank", ImageResource.BANK),
  BAR("Bar", ImageResource.BAR),
  BEACH("Beach", ImageResource.BEACH),
  BED_AND_BREAKFAST("Bed and Breakfast", ImageResource.BED_AND_BREAKFAST),
  BORDER_CROSSING("Border crossing", ImageResource.BORDER_CROSSING),
  BOWLING_CENTRE("Bowling center", ImageResource.BOWLING_CENTRE),
  BRIDGE("Bridge", ImageResource.BRIDGE),
  BUILDING("Building", ImageResource.BUILDING),
  BUSSTATION("Bus station", ImageResource.BUSSTATION),
  BUS_STOP("Bus stop", ImageResource.BUS_STOP),
  CABLE_CAR("Cable car", ImageResource.CABLE_CAR),
  CAFE("Cafe", ImageResource.CAFE),
  CAMPING("Camping", ImageResource.CAMPING),
  CANYON("Canyon", ImageResource.CANYON),
  CAR_RENTAL("Car rental", ImageResource.CAR_RENTAL),
  CAR_SERVICE("Car service", ImageResource.CAR_SERVICE),
  CAR_WASH("Car wash", ImageResource.CAR_WASH),
  CASINO("Casino", ImageResource.CASINO),
  CASTLE("Castle", ImageResource.CASTLE),
  CAVE("Cave", ImageResource.CAVE),
  CINEMA("Cinema", ImageResource.CINEMA),
  CITY("City", ImageResource.CITY),
  CITY_CENTER("City center", ImageResource.CITY_CENTER),
  COMMUNITY_CENTER("Community center", ImageResource.COMMUNITY_CENTER),
  CONVENIENCE_STORE("Convenience store", ImageResource.CONVENIENCE_STORE),
  CONVENTION_CENTER("Convention center", ImageResource.CONVENTION_CENTER),
  DEFAULT_POI("Default POI", ImageResource.DEFAULT_POI),
  EMERGENCY("Emergency", ImageResource.EMERGENCY),
  EXHIBITION_CENTER("Exhibition center", ImageResource.EXHIBITION),
  FERRY("Ferry", ImageResource.FERRY),
  FIRE_DEPARTMENT("Fire department", ImageResource.FIRE_DEPARTMENT),
  FOREST("Forest", ImageResource.TREES),
  GLACIER("Glacier", ImageResource.GLACIER),
  GOLF_COURSE("Golf course", ImageResource.GOLFING),
  GOVERMENT("Goverment", ImageResource.GOVERMENT),
  GUESTHOUSE("Guesthouse", ImageResource.GUESTHOUSE),
  HIKING("Hiking", ImageResource.WALKING),
  HOME("Home", ImageResource.HOUSE),
  HOSPITAL("Hospital", ImageResource.HOSPITAL),
  HOTEL("Hotel", ImageResource.HOTEL),
  ICE_SKATING_RING("Ice skating ring", ImageResource.ICE_SKATER),
  INDUSTRY("Industry", ImageResource.FACTORY),
  ISLAND("Island", ImageResource.ISLAND),
  LAKE("Lake", ImageResource.LAKE),
  LANDSCAPE("Landscape", ImageResource.LANDSCAPE),
  LEARNING("Learning", ImageResource.TEACHING),
  LIBRARY("Library", ImageResource.LIBRARY),
  LODGE("Lodge", ImageResource.LODGE),
  MARINA("Marina", ImageResource.MARINA),
  MARKET("Market", ImageResource.MARKET),
  MEMORIAL("Memorial", ImageResource.MEMORIAL),
  MONUMENT("Monument", ImageResource.MONUMENT),
  MOTORCYCLE_SERVICE("Motorcycle service", ImageResource.MOTORCYCLE),
  MOUNTAIN("Mountain", ImageResource.MOUNTAIN),
  MOUNTAIN_PASS("Mountain pass", ImageResource.MOUNTAIN_PASS),
  MUNICIPALITY("Municipality", ImageResource.HOUSES),
  MUSEUM("Museum", ImageResource.MUSEUM),
  NEIGHBOURHOOD("Neighbourhood", ImageResource.HOUSES),
  NIGHTLIFE("Nightlife", ImageResource.NIGHTLIFE),
  PARK("Park", ImageResource.PARK),
  PARK_AND_RIDE("Park and Ride", ImageResource.PARK_AND_RIDE),
  PARKING("Parking", ImageResource.PARKING),
  PETROL_STATION("Petrol station", ImageResource.PETROL_STATION),
  PHARMACY("Pharmacy", ImageResource.PHARMACY),
  PLACE_OF_WORSHIP("Place of worship", ImageResource.PLACE_OF_WORSHIP),
  POLICE("Police", ImageResource.POLICE_CAR),
  POST_OFFICE("Post office", ImageResource.ENVELOPE),
  RAILWAY_STATION("Railway station", ImageResource.RAILWAY_STATION),
  RECREATION("Recreation", ImageResource.RECREATION),
  REST_AREA("Rest area", ImageResource.REST_AREA),
  RESTAURANT("Restaurant", ImageResource.RESTAURANT),
  RIVER("River", ImageResource.RIVER),
  SCENIC_VIEWPOINT("Scenic viewpoint", ImageResource.SCENIC_VIEWPOINT),
  SHOP("Shop", ImageResource.SHOP),
  SHOPPING_CENTER("Shopping center", ImageResource.SHOPPING_CENTER),
  SKI_RESORT("Ski resort", ImageResource.SKIÏNG),
  SNORKELING_LOCATION("Snorkeling location", ImageResource.SNORKELER),
  SPORT("Sport", ImageResource.SPORT),
  SQUARE("Square", ImageResource.SQUARE),
  SWIMMING_POOL("Swimming pool", ImageResource.SWIMMING_POOL),
  THEATER("Theater", ImageResource.THEATER),
  TOURIST_ATTRACTION("Tourist attraction", ImageResource.CAMERA),
  TOURIST_INFORMATION("Tourist information", ImageResource.TOURIST_INFORMATION),
  WATERFALL("Waterfall", ImageResource.WATERFALL),
  WINERY("Winery", ImageResource.WINERY),
  YOUTH_HOSTEL("Youth hostel", ImageResource.YOUTH_HOSTEL),
  ZOO("Zoo", ImageResource.ZOO);
  
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(LocationCategory.class.getName());
  
  /**
   * Name to be shown to the user.
   */
  private String displayName;
  
  /**
   * The image resource {@code ImageResource} for this location category.
   */
  private ImageResource imageResource;
  
  private List<String> displayNames;
  
  /**
   * Constructor.
   */
  LocationCategory(String displayName, ImageResource imageResource) {
    this.displayName = displayName;
    this.imageResource = imageResource;
  }

  /**
   * Get the name to be shown to the user.
   * @return the name to be shown to the user.
   */
  public static String getDisplayName(Object object) {
    return ((LocationCategory) object).displayName;
  }

  /**
   * Get the list of all display names. This list can e.g. be used to create a combo box with all location categories.
   * @return  the list of all display names.
   */
  public List<String> getDisplayNames() {
    if (displayNames == null) {
      displayNames = new ArrayList<>();
      for (LocationCategory category : LocationCategory.values()) {
        displayNames.add(getDisplayName(category));
      }
    }
    return displayNames;
  }
  
  @Override
  public String toString() {
    return displayName;
  }
  
  /**
   * Get the icon for this location category.
   * 
   * @return the icon for this location category.
   */
  public Image getIcon() {
    return imageResource.getImage(ImageSize.SIZE_1);
  }
  
  /**
   * Get the icon for this location category in a specific size.
   * 
   * @param imageSize the size of the icon to be returned.
   * @return the icon for this location category in the specified size.
   */
  public Image getIcon(ImageSize imageSize) {
    return imageResource.getImage(imageSize);
  }
  
  /**
   * Get the URL of the icon for this location category.
   * @return the URL of the icon.
   */
  public URL getIconURL() {
    return imageResource.getImageUrl();
  }
  
  /**
   * Get the filename of the icon for this location category.
   * @return the filename of the icon.
   */
  public String getIconFilename() {
    return imageResource.getImageFilename(ImageSize.SIZE_1);
  }
  
  /**
   * Get the location category for a given display name.
   * 
   * @param displayName the name of the location category.
   * @return the location category for the given name, or null if no such category exists.
   */
  public LocationCategory getLocationCategoryForName(String displayName) {
    for (LocationCategory locationCategory : LocationCategory.values()) {
      if (getDisplayName(locationCategory).equals(displayName)) {
        return locationCategory;
      }
    }
    return null;
  }
  
  public static LocationCategory locationCategoryForPOICategoryId(POICategoryId poiCategoryId) {
    return null;
  }
}
