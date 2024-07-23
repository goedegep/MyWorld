package goedegep.poi.app.guifx;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.poi.model.POICategoryId;
import goedegep.poi.model.POIFactory;
import goedegep.poi.model.POIIconResourceDescriptor;
import goedegep.poi.model.POIIconResourceInfo;
import goedegep.poi.model.POIPackage;
import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import goedegep.util.emf.EMFResource;
import javafx.scene.image.Image;

/**
 * This class provides POI icons.
 * <p>
 * Upon creation, it loads the icon resource descriptors from the specified files.<br/>
 * Each icon is loaded when it is first requested (lazy loading).
 */
public class POIIcons {
  private final static Logger LOGGER = Logger.getLogger(POIIcons.class.getName());

  private Map<POICategoryId, String> iconFileNameMap = new HashMap<>();
  private Map<POICategoryId, Image> iconMap = new HashMap<>();
  
  public POIIcons(String poiIconResourceInfoFileName) {
    EMFResource<POIIconResourceInfo> emfResource = new EMFResource<>(
        POIPackage.eINSTANCE,
        () -> createPOIIconResourceInfo(),
        ".xmi");
    
    // TEMP
    emfResource.newEObject();
    try {
      emfResource.save(poiIconResourceInfoFileName);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    
    try {
      POIIconResourceInfo poiIconResourceInfo = emfResource.load(poiIconResourceInfoFileName);
      for (POIIconResourceDescriptor poiIconResourceDescriptor: poiIconResourceInfo.getPoiIconResourceDescriptors()) {
        iconFileNameMap.put(poiIconResourceDescriptor.getCategory(), poiIconResourceDescriptor.getIconFileName());
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public Image getIcon(POICategoryId poiCategoryId) {
    Image icon = iconMap.get(poiCategoryId);
    if (icon == null) {
      
      String iconFileName = iconFileNameMap.get(poiCategoryId);
      if (iconFileName.startsWith("file:")) {
        icon = new Image(iconFileName);
      } else {
        InputStream iconInputStream = POIIcons.class.getResourceAsStream(iconFileName);
        if (iconInputStream == null) {
          LOGGER.severe("Icon file doesn't seem to exist: " + iconFileName + " (category is " + poiCategoryId.getLiteral() + ")");
          return getIcon(POICategoryId.DEFAULT_POI);        
        }
        icon = new Image(iconInputStream, 36, 36, true, true);
      }
      iconMap.put(poiCategoryId, icon);
    }
    
    return icon;
  }
  
  public Image getIcon(POICategoryId poiCategoryId, double requestedWitdh, double requestedHeight) {
    String iconFileName = iconFileNameMap.get(poiCategoryId);
    
    if (iconFileName.startsWith("file:")) {
      return new Image(iconFileName, requestedWitdh, requestedHeight, true, true);
    } else {
      InputStream iconInputStream = POIIcons.class.getResourceAsStream(iconFileName);
      if (iconInputStream == null) {
        LOGGER.severe("Icon file doesn't seem to exist: " + iconFileName + " (category is " + poiCategoryId.getLiteral() + ")");
        return getIcon(POICategoryId.DEFAULT_POI);        
      }
      return new Image(iconInputStream, requestedWitdh, requestedHeight, true, true);
    }
  }
  
  public URL getIconUrl(POICategoryId poiCategoryId) {
    String iconFileName = iconFileNameMap.get(poiCategoryId);
    URL iconURL = null;
    if (iconFileName.startsWith("file:")) {
      try {
        iconURL = new URI(iconFileName).toURL();
      } catch (MalformedURLException | URISyntaxException e) {
        e.printStackTrace();
      }
    } else {
      iconURL = POIIcons.class.getResource(iconFileName);
    }

    return iconURL;
  }
  
  public String getIconFileName(POICategoryId poiCategoryId) {
    return iconFileNameMap.get(poiCategoryId);
  }

  private POIIconResourceInfo createPOIIconResourceInfo() {
    POIFactory factory = POIFactory.eINSTANCE;
    POIIconResourceDescriptor poiIconResourceDescriptor;
    
    POIIconResourceInfo poiIconResourceInfo = factory.createPOIIconResourceInfo();
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.AMUSEMENT);
    poiIconResourceDescriptor.setIconFileName("amusement_park.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.AUTOMOBILE_CLUB);
    poiIconResourceDescriptor.setIconFileName("automobile_club.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.BANK);
    poiIconResourceDescriptor.setIconFileName("bank.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.BORDER_CROSSING);
    poiIconResourceDescriptor.setIconFileName("border_crossing.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.BOWLING_CENTER);
    poiIconResourceDescriptor.setIconFileName("bowling_centre.gif");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.BUS_STATION);
    poiIconResourceDescriptor.setIconFileName("busstation.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.BUS_STOP);
    poiIconResourceDescriptor.setIconFileName(ImageResource.BUS_STOP.getImageUrl(ImageSize.SIZE_2).toString());
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CAMPING);
    poiIconResourceDescriptor.setIconFileName("camping.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CAR_RENTAL);
    poiIconResourceDescriptor.setIconFileName("car_rental.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CAR_REPAIR);
    poiIconResourceDescriptor.setIconFileName("car_service.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CASINO);
    poiIconResourceDescriptor.setIconFileName("casino.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CHURCH);
    poiIconResourceDescriptor.setIconFileName("place_of_worship.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CITY_CENTER);
    poiIconResourceDescriptor.setIconFileName("city_center.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CINEMA);
    poiIconResourceDescriptor.setIconFileName("cinema.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.COMMUNITY_BUILDING);
    poiIconResourceDescriptor.setIconFileName("community_center.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.EXHIBITION_CENTER);
    poiIconResourceDescriptor.setIconFileName("exhibition.gif");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.FERRY);
    poiIconResourceDescriptor.setIconFileName("ferry.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.FIRE_DEPARTMENT);
    poiIconResourceDescriptor.setIconFileName("fire_department.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.GOLF_COURSE);
    poiIconResourceDescriptor.setIconFileName("golfing.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.HOSPITAL);
    poiIconResourceDescriptor.setIconFileName("hospital.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.HOSPITAL);
    poiIconResourceDescriptor.setIconFileName("hospital.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.HOTEL);
    poiIconResourceDescriptor.setIconFileName("hotel.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.ICE_SKATING_RING);
    poiIconResourceDescriptor.setIconFileName("ice_skating.gif");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.INDUSTRY);
    poiIconResourceDescriptor.setIconFileName("industry.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.LEARNING);
    poiIconResourceDescriptor.setIconFileName("learning.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.LIBRARY);
    poiIconResourceDescriptor.setIconFileName("library.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.MARINA);
    poiIconResourceDescriptor.setIconFileName("marina.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.MARKET);
    poiIconResourceDescriptor.setIconFileName(ImageResource.MARKET.getImageUrl(ImageSize.SIZE_2).toString());
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.MONUMENT);
    poiIconResourceDescriptor.setIconFileName("monument.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.MOTORCYCLE_SERVICE);
    poiIconResourceDescriptor.setIconFileName("motorcycle.gif");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.MUNICIPALITY);
    poiIconResourceDescriptor.setIconFileName("municip.gif");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.MUSEUM);
    poiIconResourceDescriptor.setIconFileName("museum.gif");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.NIGHTLIFE);
    poiIconResourceDescriptor.setIconFileName("nightlife.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.PARK);
    poiIconResourceDescriptor.setIconFileName("park.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.PARK_AND_RIDE);
    poiIconResourceDescriptor.setIconFileName("park_and_ride.gif");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.PARKING);
    poiIconResourceDescriptor.setIconFileName("parking.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.PETROL_STATION);
    poiIconResourceDescriptor.setIconFileName("petrol_station.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.PHARMACY);
    poiIconResourceDescriptor.setIconFileName("pharmacy.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.AIRPORT);
    poiIconResourceDescriptor.setIconFileName("airport.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.ATM);
    poiIconResourceDescriptor.setIconFileName("atm.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.DEFAULT_POI);
    poiIconResourceDescriptor.setIconFileName("poi_general.gif");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.SKI_RESORT);
    poiIconResourceDescriptor.setIconFileName("skiing.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.POLICE);
    poiIconResourceDescriptor.setIconFileName("police.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.POST_OFFICE);
    poiIconResourceDescriptor.setIconFileName("post_office.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.RAILWAY_STATION);
    poiIconResourceDescriptor.setIconFileName("railway_station.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.REST_AREA);
    poiIconResourceDescriptor.setIconFileName("rest_area.gif");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.RESTAURANT);
    poiIconResourceDescriptor.setIconFileName("restaurant.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.SHOPPING_CENTER);
    poiIconResourceDescriptor.setIconFileName("shopping_center.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.SHOP);
    poiIconResourceDescriptor.setIconFileName("shop.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.SPORT);
    poiIconResourceDescriptor.setIconFileName("sport.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.THEATER);
    poiIconResourceDescriptor.setIconFileName("theater.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.TOURIST_INFORMATION);
    poiIconResourceDescriptor.setIconFileName("tourist_information.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.TOURIST_ATTRACTION);
    poiIconResourceDescriptor.setIconFileName("tourist_attraction.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.WINERY);
    poiIconResourceDescriptor.setIconFileName("winery.gif");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.SNORKELING_LOCATION);
    poiIconResourceDescriptor.setIconFileName("snorkeling_location.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.HOME);
    poiIconResourceDescriptor.setIconFileName("home.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.BED_AND_BREAKFAST);
    poiIconResourceDescriptor.setIconFileName("bed_and_breakfast.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.SCENIC_VIEWPOINT);
    poiIconResourceDescriptor.setIconFileName("scenic_viewpoint.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.LODGE);
    poiIconResourceDescriptor.setIconFileName("lodge.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CAR_WASH);
    poiIconResourceDescriptor.setIconFileName("car_wash.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CONVENTION_CENTER);
    poiIconResourceDescriptor.setIconFileName("convention_center.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CONVENIENCE_STORE);
    poiIconResourceDescriptor.setIconFileName("convenience_store.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.EMERGENCY);
    poiIconResourceDescriptor.setIconFileName("emergency.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.GOVERMENT);
    poiIconResourceDescriptor.setIconFileName("goverment.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.GUESTHOUSE);
    poiIconResourceDescriptor.setIconFileName("guesthouse.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.ISLAND);
    poiIconResourceDescriptor.setIconFileName(ImageResource.ISLAND.getImageUrl(ImageSize.SIZE_2).toString());
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.BAR);
    poiIconResourceDescriptor.setIconFileName("bar.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.RECREATION);
    poiIconResourceDescriptor.setIconFileName("recreation.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.YOUTH_HOSTEL);
    poiIconResourceDescriptor.setIconFileName("youth_hostel.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CITY);
    poiIconResourceDescriptor.setIconFileName("city.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.MOUNTAIN);
    poiIconResourceDescriptor.setIconFileName("Mountain.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.MOUNTAIN_PASS);
    poiIconResourceDescriptor.setIconFileName("Mountain pass.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.BEACH);
    poiIconResourceDescriptor.setIconFileName("beach-icon.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CAVE);
    poiIconResourceDescriptor.setIconFileName("cave.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.MEMORIAL);
    poiIconResourceDescriptor.setIconFileName("memorial.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.SQUARE);
    poiIconResourceDescriptor.setIconFileName("square.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CASTLE);
    poiIconResourceDescriptor.setIconFileName("castle-128x128.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.BRIDGE);
    poiIconResourceDescriptor.setIconFileName("bridge.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.NEIGHBOURHOOD);
    poiIconResourceDescriptor.setIconFileName("neighbourhood.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.AQUADUCT);
    poiIconResourceDescriptor.setIconFileName("aquaduct.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.LAKE);
    poiIconResourceDescriptor.setIconFileName("lake.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.BUILDING);
    poiIconResourceDescriptor.setIconFileName("building.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.CANYON);
    poiIconResourceDescriptor.setIconFileName("canyon.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.LANDSCAPE);
    poiIconResourceDescriptor.setIconFileName("landscape.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.HIKING);
    poiIconResourceDescriptor.setIconFileName("hiking.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.GLACIER);
    poiIconResourceDescriptor.setIconFileName("Glacier.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.WATERFALL);
    poiIconResourceDescriptor.setIconFileName("waterfall.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.SWIMMING_POOL);
    poiIconResourceDescriptor.setIconFileName("swimmingPool.png");
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    poiIconResourceDescriptor = factory.createPOIIconResourceDescriptor();
    poiIconResourceDescriptor.setCategory(POICategoryId.ZOO);
    poiIconResourceDescriptor.setIconFileName(ImageResource.ZOO.getImageUrl(ImageSize.SIZE_2).toString());
    poiIconResourceInfo.getPoiIconResourceDescriptors().add(poiIconResourceDescriptor);
    
    return poiIconResourceInfo;
  }

}
