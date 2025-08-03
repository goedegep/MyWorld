package goedegep.resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;

import javafx.scene.image.Image;

import goedegep.util.img.PhotoFileMetaDataHandler;

/**
 * This enum defines and provides available images.
 * <p>
 * An image is defined as follows
 * <ul>
 * <li>information on the picture files<br/>
 * Often an image is provided in more than one size, therefore this is a list with information per file:
 * <ul>
 * <li>the file name</li>
 * <li>the width of the image in pixels</li>
 * <li>the height of the image in pixels</li>
 * </ul>
 * </li>
 * <li>description</br>
 * A short description of the image, e.g. 'a person canoeing'.
 * </li>
 * <li>credits<br/>
 * Information about the source of the picture.
 * </li>
 * </ul>
 *
 * There are methods to get the largest image available, or to get an images of a specific size. In the latter case the best fitting image is resized to the requested size.
 * The requested size can be specified as width and height, or via one of the constants defined by the {@link ImageSize} enum.<br/>
 * <br/>
 * There are also methods to get the filename or URL for an image of a specific size. This can e.g. be used if you generate an HTML file which contains images.<br/>
 * <br/>
 * The images are loaded when first requested and are kept in memory from then on.
 */
public enum ImageResource {
  AIRPORT(new ImageFileInfo[] {new ImageFileInfo("Airport - 34x39.png", 34, 39)}, "airport", null),
  AQUADUCT(new ImageFileInfo[] {new ImageFileInfo("Aquaduct - 256x152.png", 256, 152)}, "aquaduct", null),
  ATM(new ImageFileInfo[] {new ImageFileInfo("ATM - 34x39.png", 34, 39)}, "ATM", null),
  AUTOMOBILE_CLUB(new ImageFileInfo[] {new ImageFileInfo("AutomobileClub - 34x39.png", 34, 39)}, "automobile club", null),
  BACKPACK(new ImageFileInfo[] {
      new ImageFileInfo("Backpack - 64x64.png", 64, 64),
      new ImageFileInfo("Backpack - 128x128.png", 128, 128),
      new ImageFileInfo("Backpack - 512x512.png", 512, 512),
      }, "a backpack", null),
  BANK(new ImageFileInfo[] {new ImageFileInfo("Bank - 34x39.png", 34, 39)}, "bank", null),
  BAR(new ImageFileInfo[] {new ImageFileInfo("Bar - 34x39.png", 34, 39)}, "a drink", null),
  BEACH(new ImageFileInfo[] {new ImageFileInfo("Beach - 256x246.png", 256, 246)}, "beach", null),
  BED_AND_BREAKFAST(new ImageFileInfo[] {new ImageFileInfo("BedAndBreakfast - 250x250.png", 250, 250)}, "bed and breakfast", null),
  BOAT(new ImageFileInfo[] {
      new ImageFileInfo("Boat - 48x48.png", 48, 48),
      new ImageFileInfo("Boat - 512x512.png", 512, 512)
      }, "boat", "https://iconscout.com/"),
  BORDER_CROSSING(new ImageFileInfo[] {new ImageFileInfo("BorderCrossing - 34x39.png", 34, 39)}, "border crossing", null),
  BOWLING_CENTRE(new ImageFileInfo[] {new ImageFileInfo("BowlingCentre - 32x32.gif", 32, 32)}, "a bowler", null),
  BRIDGE(new ImageFileInfo[] {new ImageFileInfo("Bridge - 256x134.png", 256, 134)}, "bridge", null),
  BUILDING(new ImageFileInfo[] {new ImageFileInfo("Building - 256x256.png", 256, 256)}, "building", null),
  BUS(new ImageFileInfo[] {new ImageFileInfo("Bus - 256x252.png", 256, 252)}, "bus", "https://uxwing.com/"),
  BUSSTATION(new ImageFileInfo[] {new ImageFileInfo("Busstation - 34x39.png", 34, 39)}, "busstation", null),
  BUS_STOP(new ImageFileInfo[] {
      new ImageFileInfo("BusStop - 48x48.png", 48, 48),
      new ImageFileInfo("BusStop - 512x512.png", 512, 512)
      }, "bus stop", "<a href=\"https://www.flaticon.com/free-icons/bus\" title=\"bus icons\">Bus icons created by Freepik - Flaticon</a>"),
  CABLE_CAR(new ImageFileInfo[] {
      new ImageFileInfo("CableCar - 800x800.png", 800, 800),
      new ImageFileInfo("CableCar - 48x48.png", 48, 48)
      }, "a cable car", null),
  CAFE(new ImageFileInfo[] {new ImageFileInfo("Coffee - 245x256.png", 245, 256)}, "a cup of coffee", null),
  CAMEL_RIDE(new ImageFileInfo[] {new ImageFileInfo("CamelRide - 256x219.png", 256, 219)}, "woman riding a camel", null),
  CAMERA(new ImageFileInfo[] {new ImageFileInfo("Camera - 34x39.png", 34, 39)}, "photo camera", null),
  CAMERA_BLACK(new ImageFileInfo[] {new ImageFileInfo("CameraBlack - 28x22.png", 28, 22)}, "a black photo camera", null),
  CAMERA_GRAY(new ImageFileInfo[] {new ImageFileInfo("CameraGray - 28x22.png", 28, 22)}, "a gray photo camera", null),
  CAMERA_BLUE(new ImageFileInfo[] {new ImageFileInfo("CameraBlue - 28x22.png", 28, 22)}, "a blue photo camera", null),
  CAMPING(new ImageFileInfo[] {new ImageFileInfo("Camping - 34x39.png", 34, 39)}, "a tent", null),
  CANYON(new ImageFileInfo[] {new ImageFileInfo("Canyon - 256x256.png", 256, 256)}, "canyon", null),
  CAR(new ImageFileInfo[] {
      new ImageFileInfo("Car - 16x16.png", 16, 16),
      new ImageFileInfo("Car - 24x24.png", 24, 24),
      new ImageFileInfo("Car - 32x32.png", 32, 32),
      new ImageFileInfo("Car - 48x48.png", 48, 48),
      new ImageFileInfo("Car - 64x64.png", 64, 64),
      new ImageFileInfo("Car - 128x128.png", 128, 128),
      new ImageFileInfo("Car - 256x256.png", 256, 256),
      new ImageFileInfo("Car - 512x512.png", 512, 512)
      }, "a car", "downloaded from https://icons8.com/, as Creative Commons Attribution-NoDerivs 3.0."),
  CAR_RENTAL(new ImageFileInfo[] {new ImageFileInfo("CarRental - 34x39.png", 34, 39)}, "car rental", null),
  CAR_SERVICE(new ImageFileInfo[] {new ImageFileInfo("CarService - 34x39.png", 34, 39)}, "car service", null),
  CAR_WASH(new ImageFileInfo[] {new ImageFileInfo("CarWash - 34x39.png", 34, 39)}, "car wash", null),
  CANOEING(new ImageFileInfo[] {new ImageFileInfo("Canoeing - 256x256.png", 256, 256)}, "a person canoeing", null),
  CASINO(new ImageFileInfo[] {new ImageFileInfo("Casino - 34x39.png", 34, 39)}, "playing cards", null),
  CASTLE(new ImageFileInfo[] {new ImageFileInfo("Castle - 128x128.png", 128, 128)}, "castle", null),
  CAVE(new ImageFileInfo[] {new ImageFileInfo("Cave - 256x256.png", 256, 256)}, "castle", null),
  CINEMA(new ImageFileInfo[] {new ImageFileInfo("Cinema - 34x39.png", 34, 39)}, "cinema", null),
  CITY(new ImageFileInfo[] {new ImageFileInfo("City - 256x256.png", 256, 256)}, "city", null),
  CITY_CENTER(new ImageFileInfo[] {new ImageFileInfo("CityCenter - 34x39.png", 34, 39)}, "city center", null),
  COMMUNITY_CENTER(new ImageFileInfo[] {new ImageFileInfo("CommunityCenter - 34x39.png", 34, 39)}, "community center", null),
  CONVENIENCE_STORE(new ImageFileInfo[] {new ImageFileInfo("ConvenienceStore - 34x39.png", 34, 39)}, "shopping cart", null),
  CONVENTION_CENTER(new ImageFileInfo[] {new ImageFileInfo("ConventionCenter - 34x39.png", 34, 39)}, "convention center", null),
  CYCLING(new ImageFileInfo[] {new ImageFileInfo("RegularBiking - 128x128.png", 128, 128)}, "regular biking", "from https://findicons.com/"),
  DEFAULT_POI(new ImageFileInfo[] {new ImageFileInfo("DefaultPOI - 32x32.gif", 32, 32)}, "default POI", null),
  DEMO(new ImageFileInfo[] {new ImageFileInfo("Demo - 256x179.png", 256, 179)}, "computer screen with demo as text", "<a href=\"https://www.flaticon.com/free-icons/demo\" title=\"demo icons\">Demo icons created by Freepik - Flaticon</a>"),
  EMERGENCY(new ImageFileInfo[] {new ImageFileInfo("Emergency - 34x39.png", 34, 39)}, "emergency", null),
  ENVELOPE(new ImageFileInfo[] {new ImageFileInfo("Envelope - 34x39.png", 34, 39)}, "envelope", null),
  EXHIBITION(new ImageFileInfo[] {new ImageFileInfo("Exhibition - 32x32.gif", 32, 32)}, "exhibition", null),
  FACTORY(new ImageFileInfo[] {new ImageFileInfo("Factory - 34x39.png", 34, 39)}, "factory", null),
  FERRY(new ImageFileInfo[] {new ImageFileInfo("Ferry - 34x39.png", 34, 39)}, "ferry", null),
  FIRE_DEPARTMENT(new ImageFileInfo[] {new ImageFileInfo("FireDepartment - 34x39.png", 34, 39)}, "fire department", null),
  FOLDER_WITH_FILES(new ImageFileInfo[] {new ImageFileInfo("FolderWithFiles - 64x64.png", 64, 64)}, "a folder containing files", "<a href=\"https://www.flaticon.com/free-icons/files-and-folders\" title=\"files and folders icons\">Files and folders icons created by NajmunNahar - Flaticon</a>"),
  GLACIER(new ImageFileInfo[] {new ImageFileInfo("Glacier - 128x128.png", 128, 128)}, "mountains with glacier", null),
  GOLFING(new ImageFileInfo[] {new ImageFileInfo("Golfing - 34x39.png", 34, 39)}, "golfing", null),
  GOVERMENT(new ImageFileInfo[] {new ImageFileInfo("Goverment - 34x39.png", 34, 39)}, "capitol like building", null),
  GPX(new ImageFileInfo[] {new ImageFileInfo("Gpx - 256x240.png", 256, 240)}, "a map with a track and a location", null),
  GUESTHOUSE(new ImageFileInfo[] {new ImageFileInfo("Guesthouse - 34x39.png", 34, 39)}, "guesthouse", null),
  HORSE_RIDING(new ImageFileInfo[] {
      new ImageFileInfo("HorseRiding - 48x48.png", 48, 48),
      new ImageFileInfo("HorseRiding - 512x512.png", 512, 512)

      }, "horse riding", "<a href=\"https://www.flaticon.com/free-icons/rider\" title=\"rider icons\">Rider icons created by Freepik - Flaticon</a>"),
  HOSPITAL(new ImageFileInfo[] {new ImageFileInfo("Hospital - 34x39.png", 34, 39)}, "hospital", null),
  HOTEL(new ImageFileInfo[] {new ImageFileInfo("Hotel - 34x39.png", 34, 39)}, "hotel", null),
  HOUSE(new ImageFileInfo[] {new ImageFileInfo("House - 256x256.png", 256, 256)}, "house", null),
  HOUSES(new ImageFileInfo[] {new ImageFileInfo("Houses - 166x166.png", 166, 166)}, "houses", null),
  ICE_SKATER(new ImageFileInfo[] {new ImageFileInfo("IceSkater - 32x32.gif", 32, 32)}, "ice skater", null),
  ISLAND(new ImageFileInfo[] {
      new ImageFileInfo("Island - 32x32.png", 32, 32),
      new ImageFileInfo("Island - 512x512.png", 512, 512)

      }, "an island with a palm tree", "<a href=\"https://www.flaticon.com/free-icons/island\" title=\"island icons\">Island icons created by Freepik - Flaticon</a>"),
  LAKE(new ImageFileInfo[] {new ImageFileInfo("Lake - 256x256.png", 256, 256)}, "lake with mountains and trees", null),
  LANDSCAPE(new ImageFileInfo[] {new ImageFileInfo("Landscape - 128x128.png", 128, 128)}, "landscape", null),
  LIBRARY(new ImageFileInfo[] {new ImageFileInfo("Library - 34x39.png", 34, 39)}, "library", null),
  LOCATION_FLAG_BLUE(new ImageFileInfo[] {new ImageFileInfo("LocationFlagBlue - 121x126.png", 121, 126)}, "a blue location flag", null),
  LOCATION_FLAG_YELLOW(new ImageFileInfo[] {new ImageFileInfo("LocationFlagYellow - 121x126.png", 121, 126)}, "a yellow location flag", null),
  LODGE(new ImageFileInfo[] {new ImageFileInfo("Lodge - 150x150.png", 150, 150)}, "lodge", null),
  MAGNIFYING_GLASS(new ImageFileInfo[] {new ImageFileInfo("MagnifyingGlass - 256x256.png", 256, 256)}, "a magnifying glass", null),
  MAP(new ImageFileInfo[] {new ImageFileInfo("Map - 256x201.png", 256, 201)}, "a map", null),
  MARINA(new ImageFileInfo[] {new ImageFileInfo("Marina - 34x39.png", 34, 39)}, "sailboat", null),
  MARKET(new ImageFileInfo[] {
      new ImageFileInfo("Market - 48x48.png", 48, 48),
      new ImageFileInfo("Market - 512x512.png", 512, 512)}, "a market", null),
  MARKDOWN(new ImageFileInfo[] {new ImageFileInfo("MarkdownLogo - 256x163.png", 256, 163)}, "Markdown logo", "https://imgbin.com/"),
  MEMORIAL(new ImageFileInfo[] {new ImageFileInfo("Memorial - 256x256.png", 256, 256)}, "memorial", null),
  MONUMENT(new ImageFileInfo[] {new ImageFileInfo("Monument - 34x39.png", 34, 39)}, "monument", null),
  MOTORCYCLE(new ImageFileInfo[] {new ImageFileInfo("Motorcycle - 256x256.png", 256, 256)}, "Motorcycle", "<a href=\"https://www.flaticon.com/free-icons/motorcycle\" title=\"motorcycle icons\">Motorcycle icons created by Freepik - Flaticon</a>"),
  MOUNTAIN(new ImageFileInfo[] {new ImageFileInfo("Mountain - 128x128.png", 128, 128)}, "mountains", null),
  MOUNTAIN_PASS(new ImageFileInfo[] {new ImageFileInfo("MountainPass - 128x128.png", 128, 128)}, "mountains with road", null),
  MS_WORD(new ImageFileInfo[] {new ImageFileInfo("MSWord - 256x250.png", 256, 250)}, "Microsoft Word document icon", "User:Airhogs777"),
  MUSEUM(new ImageFileInfo[] {new ImageFileInfo("Museum - 32x32.gif", 32, 32)}, "museum", null),
  NIGHTLIFE(new ImageFileInfo[] {new ImageFileInfo("Nightlife - 34x39.png", 34, 39)}, "nightlife", null),
  ODT(new ImageFileInfo[] {new ImageFileInfo("ODT - 232x256.png", 232, 256)}, "OpenDocument Text document icon", "<a href=\"https://www.flaticon.com/free-icons/files-and-folders\" title=\"files and folders icons\">Files and folders icons created by Awicon - Flaticon</a>"),
  PARK(new ImageFileInfo[] {new ImageFileInfo("Park - 34x39.png", 34, 39)}, "park", null),
  PARK_AND_RIDE(new ImageFileInfo[] {new ImageFileInfo("ParkAndRide - 32x32.gif", 32, 32)}, "park and Ride", null),
  PARKING(new ImageFileInfo[] {new ImageFileInfo("Parking - 34x39.png", 34, 39)}, "parking", null),
  PLANE_TAKEOFF(new ImageFileInfo[] {
      new ImageFileInfo("PlaneTakeoff - 32x32.png", 32, 32),
      new ImageFileInfo("PlaneTakeoff - 64x64.png", 64, 64),
      new ImageFileInfo("PlaneTakeoff - 128x128.png", 128, 128),
      new ImageFileInfo("PlaneTakeoff - 256x256.png", 256, 256),
      new ImageFileInfo("PlaneTakeoff - 512x512.png", 512, 512)
      }, "a plane taking off", "https://icon-icons.com/"),
  PDF(new ImageFileInfo[] {new ImageFileInfo("PDF - 209x256.png", 209, 256)}, "PDF file logo", null),
  PETROL_STATION(new ImageFileInfo[] {new ImageFileInfo("PetrolStation - 34x39.png", 34, 39)}, "petrol station", null),
  PHARMACY(new ImageFileInfo[] {new ImageFileInfo("Pharmacy - 34x39.png", 34, 39)}, "pharmacy", null),
  PHOTO_FOLDER(new ImageFileInfo[] {new ImageFileInfo("PhotoFolder - 199x217.png", 199, 217)}, "Photo folder icon", "https://icon-library.com/icon/microsoft-folder-icon-24.html.html>Microsoft Folder Icon # 392312"),
  PLACE_OF_WORSHIP(new ImageFileInfo[] {new ImageFileInfo("PlaceOfWorship - 34x39.png", 34, 39)}, "church", null),
  POLICE_CAR(new ImageFileInfo[] {new ImageFileInfo("PoliceCar - 34x39.png", 34, 39)}, "police car", null),
  RAILWAY_STATION(new ImageFileInfo[] {new ImageFileInfo("RailwayStation - 34x39.png", 34, 39)}, "railway station", null),
  RECREATION(new ImageFileInfo[] {new ImageFileInfo("Recreation - 34x39.png", 34, 39)}, "recreation", null),
  REST_AREA(new ImageFileInfo[] {new ImageFileInfo("RestArea - 32x32.gif", 32, 32)}, "rest area", null),
  RESTAURANT(new ImageFileInfo[] {new ImageFileInfo("Restaurant - 34x39.png", 34, 39)}, "restaurant", null),
  RIVER(new ImageFileInfo[] {new ImageFileInfo("River - 256x256.png", 256, 256)}, "a river", "<a href=\"https://www.freepik.com/icons/river\">Icon by Warangkhana Sookruay</a>"),
  ROAD_TO_HORIZON(new ImageFileInfo[] {
      new ImageFileInfo("RoadToHorizon - 57x32.png", 57, 32),
      new ImageFileInfo("RoadToHorizon - 114x64.png", 114, 64),
      new ImageFileInfo("RoadToHorizon - 128x128.png", 128, 128),
      new ImageFileInfo("RoadToHorizon - 256x256.png", 256, 256),
      new ImageFileInfo("RoadToHorizon - 512x512.png", 512, 512)},
      "a road to the horizon", null),
  ROLLERCOASTER(new ImageFileInfo[] {new ImageFileInfo("Rollercoaster - 34x39.png", 34, 39)}, "rollercoaster", null),
  RUNNING(new ImageFileInfo[] {
      new ImageFileInfo("RunningFigure - 48x48.png", 48, 48),
      new ImageFileInfo("RunningFigure - 512x512.png", 512, 512)
      }, "running figure", "<a href=\"https://www.flaticon.com/free-icons/run\" title=\"run icons\">Run icons created by Freepik - Flaticon</a>"),
  SCENIC_VIEWPOINT(new ImageFileInfo[] {new ImageFileInfo("ScenicViewpoint - 256x256.png", 256, 256)}, "scenic viewpoint", null),
  SHOP(new ImageFileInfo[] {new ImageFileInfo("Shop - 34x39.png", 34, 39)}, "shop", null),
  SHOPPING_CENTER(new ImageFileInfo[] {new ImageFileInfo("ShoppingCenter - 34x39.png", 34, 39)}, "shopping center", null),
  SKIÏNG(new ImageFileInfo[] {new ImageFileInfo("Skiïng - 34x39.png", 34, 39)}, "a skiër", null),
  SNORKELER(new ImageFileInfo[] {new ImageFileInfo("Snorkeler - 200x200.png", 200, 200)}, "a snorkeler", null),
  SNOW_MOUNTAINS(new ImageFileInfo[] {new ImageFileInfo("SnowMountains - 64x30.png", 64, 30)}, "snow capped mountains", null),
  SPORT(new ImageFileInfo[] {new ImageFileInfo("Sport - 34x39.png", 34, 39)}, "a soccer field", null),
  SQUARE(new ImageFileInfo[] {new ImageFileInfo("Square - 256x256.png", 256, 256)}, "square", null),
  SUNRISE(new ImageFileInfo[] {new ImageFileInfo("Sunrise - 256x189.png", 256, 189)}, "sunrise (or sunset)", null),
  SWIMMING_POOL(new ImageFileInfo[] {new ImageFileInfo("SwimmingPool - 256x181.png", 256, 181)}, "swimming pool", null),
  TEACHING(new ImageFileInfo[] {new ImageFileInfo("Teaching - 34x39.png", 34, 39)}, "teaching", null),
  TEXT(new ImageFileInfo[] {new ImageFileInfo("Text - 256x231.png", 256, 231)}, "a letter T for Text", null),
  TEXT_FILE(new ImageFileInfo[] {new ImageFileInfo("TextFile - 238x256.png", 238, 256)}, "Text file icon", "<a href=\"https://www.flaticon.com/free-icons/text-file\" title=\"text file icons\">Text file icons created by Freepik - Flaticon</a>"),
  THEATER(new ImageFileInfo[] {new ImageFileInfo("Theater - 34x39.png", 34, 39)}, "theater", null),
  TOURIST_INFORMATION(new ImageFileInfo[] {new ImageFileInfo("TouristInformation - 34x39.png", 34, 39)}, "tourist information", null),
  TRAIN(new ImageFileInfo[] {
      new ImageFileInfo("Train - 48x48.png", 48, 48),
      new ImageFileInfo("Train - 512x512.png", 512, 512)
      }, "a train", "<a href=\"https://www.freepik.com/icon/train_4540243#fromView=keyword&page=1&position=67&uuid=b9dfd6a4-8d00-4a11-9397-c7033983ba0e\">Icon by Milkghost Studio</a>"),
  TREES(new ImageFileInfo[] {
      new ImageFileInfo("Trees - 32x32.png", 32, 32),
      new ImageFileInfo("Trees - 64x64.png", 64, 64),
      new ImageFileInfo("Trees - 512x512.png", 512, 512)
      }, "trees", "<a href=\"https://www.flaticon.com/free-icons/trees\" title=\"trees icons\">Trees icons created by surang - Flaticon</a>"),
  VIDEO_FOLDER(new ImageFileInfo[] {new ImageFileInfo("VideoFolder - 216x256.png", 216, 256)}, "Video folder icon", null),
  WALKING(new ImageFileInfo[] {new ImageFileInfo("Hiking - 256x256.png", 256, 256)}, "a person walking with stick and backpack", null),
  WATERFALL(new ImageFileInfo[] {new ImageFileInfo("Waterfall - 98x102.png", 98, 102)}, "waterfall", null),
  WINERY(new ImageFileInfo[] {new ImageFileInfo("Winery - 32x32.gif", 32, 32)}, "winery", null),
  YOUTH_HOSTEL(new ImageFileInfo[] {new ImageFileInfo("YouthHostel - 100x100.png", 100, 100)}, "youth hostel", null),
  ZOO(new ImageFileInfo[] {
      new ImageFileInfo("Zoo - 32x32.png", 32, 32),
      new ImageFileInfo("Zoo - 512x512.png", 512, 512)
      }, "zoo gate", "<a href=\"https://www.flaticon.com/free-icons/zoo\" title=\"zoo icons\">Zoo icons created by Freepik - Flaticon</a>");
  
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(ImageResource.class.getName());
  
  /**
   * The cached images.
   * <p>
   * A returned image may have a different size than the requested size.
   * Therefore the requested size is stored with a cached image, so that a request for the same size can be detected.
   */
  private List<ImageWithRequestedSize> cachedImages = null;
  
  /**
   * The different files, for different sizes, for the image. Ordered by increasing size.
   */
  private ImageFileInfo[] imageFilesInfo;
  
  /**
   * A short description of the image.
   */
  private String description;
  
  /**
   * Information about the source of the image.
   */
  private String credits;

  /**
   * Constructor
   * 
   * @param imageFilesInfo information about the image files for the image
   * @param description a description of the image
   * @param credits information about the source of the image.
   */
  ImageResource(ImageFileInfo[] imageFilesInfo, String description, String credits) {
    this.imageFilesInfo = imageFilesInfo;
    this.description = description;
    this.credits = credits;
  }
  
  /**
   * Get information on the image files
   */
  public ImageFileInfo[] getImageFilesInfo() {
    return imageFilesInfo;
  }
 
  /**
   * Get the description of the image.
   * 
   * @return the description of the image.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Get the image credits (information about the source of the image).
   * 
   * @return the image credits.
   */
  public String getCredits() {
    return credits;
  }

  
  /**
   * Get the largest available image for an ImageId.
   * 
   * @param imageId the ImageId to get an image for
   * @return the largest available image for <code>imageId</code>
   */
  public Image getImage() {
    if (cachedImages == null) {
      cachedImages = new ArrayList<>();
    }
    
    // get information on the largest image, which is the last one in the images info.
    ImageFileInfo imageInfo = imageFilesInfo[imageFilesInfo.length - 1];
    
    // check whether this image is available
    ImageWithRequestedSize imageWithSize = getImageWithImageSize(cachedImages, imageInfo.width(), imageInfo.height());
    
    Image image = null;
    if (imageWithSize == null) {
      image = new Image(ImageResource.class.getResourceAsStream(imageInfo.filename()));
      imageWithSize = new ImageWithRequestedSize(image, imageInfo.width(), imageInfo.height());
      cachedImages.add(imageWithSize);
    } else {
      image = imageWithSize.image();
    }
    
    return image;
  }

  /**
   * Get the image with a predefined size for an ImageId.
   * 
   * @param imageId the ImageId to get an image for
   * @param size the predefined ImageSize
   * @return an image for <code>imageId</code> for the requested <code>size</code>
   */
  public Image getImage(ImageSize size) {
    return getImage(size.getWidth(), size.getHeight());
  }

  /**
   * Get the image with a predefined size for an ImageId.
   * 
   * @param imageId the ImageId to get an image for
   * @param size the predefined ImageSize
   * @return an image for <code>imageId</code> for the requested <code>size</code>
   */
  public Image getImage(int width, int height) {    
    if (cachedImages == null) {
      cachedImages = new ArrayList<>();
    }
    
    // check whether this image is available
    ImageWithRequestedSize imageWithSize = getImageWithRequestedSize(cachedImages, width, height);
   
    Image image = null;
    if (imageWithSize == null) {
      ImageFileInfo imageInfo = selectImageInfoForSize(imageFilesInfo, width, height);
      try {
      image = new Image(ImageResource.class.getResourceAsStream(imageInfo.filename()), width, height, true, true);
      imageWithSize = new ImageWithRequestedSize(image, width, height);
      cachedImages.add(imageWithSize);
      } catch (NullPointerException e) {
        throw new RuntimeException("Cannot get image as file doesn't seem to exist: " + imageInfo.filename());
      }
    } else {
      image = imageWithSize.image();
    }
        
    return image;
  }
  
  /**
   * Get the image for a specific filename.
   * 
   * @param imageFilename the filename of the Image
   * @return the Image for <code>imageFilename</code>
   */
  public Image getImage(String imageFilename) {
    if (cachedImages == null) {
      cachedImages = new ArrayList<>();
    }
    
    // get information on the file.
    ImageFileInfo imageFileInfo = null;
    for (ImageFileInfo ifo: imageFilesInfo) {
      if (ifo.filename().equals(imageFilename)) {
        imageFileInfo = ifo;
      }
    }
    
    // check whether this image is available
    ImageWithRequestedSize imageWithSize = getImageWithImageSize(cachedImages, imageFileInfo.width(), imageFileInfo.height());
    
    Image image = null;
    if (imageWithSize == null) {
      image = new Image(ImageResource.class.getResourceAsStream(imageFilename));
      imageWithSize = new ImageWithRequestedSize(image, imageFileInfo.width(), imageFileInfo.height());
      cachedImages.add(imageWithSize);
    } else {
      image = imageWithSize.image();
    }
    
    return image;
  }
  
  /**
   * Get a URL for an image. This may e.g. be used in an HTML file.
   * 
   * @return a URL for the image.
   */
  public URL getImageUrl() {
    
    // get information on the largest image, which is the last one in the images info.
    ImageFileInfo imageInfo = imageFilesInfo[imageFilesInfo.length - 1];
    
    URL iconURL = ImageResource.class.getResource(imageInfo.filename());
    
    return iconURL;
  }
  
  /**
   * Get a URL for an image. This may e.g. be used in an HTML file.
   * 
   * @param imageSize the requested image size.
   * @return a URL for the image.
   */
  public URL getImageUrl(ImageSize imageSize) {
    return getImageUrl(imageSize.getWidth(), imageSize.getHeight());
  }
  
  /**
   * Get a URL for an image. This may e.g. be used in an HTML file.
   * 
   * @param width the requested image width.
   * @param height the requested height width.
   * @return a URL for the image.
   */
  public URL getImageUrl(int width, int height) {
    String imageFilename = getImageFilename(width, height);
    URL iconURL = ImageResource.class.getResource(imageFilename);
    
    return iconURL;
  }
  
  /**
   * Get a filename for the image.
   * 
   * @param imageSize the requested image size.
   * @return a filename for the image.
   */
  public String getImageFilename(ImageSize imageSize) {
    return getImageFilename(imageSize.getWidth(), imageSize.getHeight());
  }
  
  /**
   * Get a filename for the image.
   * 
   * @param width the requested image width.
   * @param height the requested height width.
   * @return a filename for the image.
   */
  public String getImageFilename(int width, int height) {
    ImageFileInfo imageInfo = selectImageInfoForSize(imageFilesInfo, width, height);
    
    return imageInfo.filename();
  }
  
  /**
   * Get the ImageWithSize for a specific image width and height.
   * 
   * @param imagesWithSizes the list in which to search for the ImageWithSize.
   * @param width the image width
   * @param height the image height
   * @return the ImageWithSize for <code>width</code> and <code>height</code>, or null if this isn't available.
   */
  private static ImageWithRequestedSize getImageWithImageSize(List<ImageWithRequestedSize> imagesWithSizes, int width, int height) {
    for (ImageWithRequestedSize imageWithSize: imagesWithSizes) {
      Image image = imageWithSize.image();
      if ((int) image.getWidth() == width  &&  (int) image.getHeight() == height) {
        return imageWithSize;
      }
    }
    
    return null;
  }
  
  /**
   * Get the ImageWithSize for a specific requested width and height.
   * 
   * @param imagesWithSizes the list in which to search for the ImageWithSize.
   * @param width the requested width
   * @param height the requested height
   * @return the ImageWithSize for <code>width</code> and <code>height</code>, or null if this isn't available.
   */
  private static ImageWithRequestedSize getImageWithRequestedSize(List<ImageWithRequestedSize> imagesWithSizes, int width, int height) {
    for (ImageWithRequestedSize imageWithSize: imagesWithSizes) {
      if (imageWithSize.requestedWidth() == width  &&  imageWithSize.requestedHeight() == height) {
        return imageWithSize;
      }
    }
    
    return null;
  }
  
  
  /*
   * Select the best image for a specific size.
   */
  private static ImageFileInfo selectImageInfoForSize(ImageFileInfo[] imagesInfo, int width, int height) {
    ImageFileInfo firstImage = null;
    ImageFileInfo secondImage = null;

    // find the images with best fitting sizes
    for (int i = 0; i < imagesInfo.length; i++) {
      firstImage = secondImage;
      secondImage = imagesInfo[i];

      if ((secondImage.width() > width)  ||
          (secondImage.height() > height)) {
        break;
      }
    }

    ImageFileInfo imageInfo;
    if (firstImage == null) {
      imageInfo = secondImage;
    } else {
      double requestedSize = width * height;
      double firstImageSize = firstImage.width() * firstImage.height();
      double firstImageSizeRatio = requestedSize / firstImageSize;
      double secondImageSize = secondImage.width() * secondImage.height();
      double secondImageSizeRatio = secondImageSize / requestedSize;

      if (firstImageSizeRatio < secondImageSizeRatio) {
        imageInfo = firstImage; 
      } else {
        imageInfo = secondImage;
      }
    }

    return imageInfo;
  }
  
  public static void checkResources() {
    for (ImageResource imageResource: ImageResource.values()) {
      imageResource.checkFileName(imageResource);
      imageResource.checkSize(imageResource);
    }
  }

  /**
   * Check whether the image files have the correct width and height specified.
   * 
   * @param imageResource the ImageResource to check
   */
  void checkSize(ImageResource imageResource) {
    for (ImageFileInfo imageFileInfo: imageResource.getImageFilesInfo()) {
      Image image = new Image(ImageResource.class.getResourceAsStream(imageFileInfo.filename()));
      int width = (int) image.getWidth();
      int height = (int) image.getHeight();
      if (width != imageFileInfo.width() || height != imageFileInfo.height()) {
        System.err.println("ImageResource: " + imageResource.name() + " has wrong size for file: " + imageFileInfo.filename() +
            ", expected: " + imageFileInfo.width() + "x" + imageFileInfo.height() +
            ", but got: " + width + "x" + height);
      }
    }
  }

  /**
   * Check whether the image files have the correct filename.
   * 
   * @param imageResource the ImageResource to check
   */
  void checkFileName(ImageResource imageResource) {
    for (ImageFileInfo imageFileInfo: imageResource.getImageFilesInfo()) {
      String filename = imageFileInfo.filename();
      String[] nameAndSize = filename.split(" - ");
      if (nameAndSize.length != 2) {
        System.err.println("ImageResource: " + imageResource.name() + " has wrong filename: " + filename +
            ", expected format: <name> - <width>x<height>.<extension>");
      } else {
        String[] sizeParts = nameAndSize[1].split("x");
        if (sizeParts.length != 2) {
          System.err.println("ImageResource: " + imageResource.name() + " has wrong filename: " + filename +
              ", expected format: <name> - <width>x<height>.<extension>");
        } else {
          try {
            int width = Integer.parseInt(sizeParts[0]);
            int height = Integer.parseInt(sizeParts[1].replaceAll("\\..*", ""));
            if (width != imageFileInfo.width() || height != imageFileInfo.height()) {
              System.err.println("ImageResource: " + imageResource.name() + " has wrong size in filename: " + filename);
            }
          } catch (NumberFormatException e) {
            System.err.println("ImageResource: " + imageResource.name() + " has wrong size in filename: " + filename +
                ", expected integers for width and height.");
          }
        }
      }
    }
    
  }

}





/**
 * This record provides information about the requested size of a cached image.
 * 
 * @param image an Image.
 * @param requestedWidth the width that was requested for the <code>image</code>.
 * @param requestedHeight the height that was requested for the <code>image</code>.
 */
record ImageWithRequestedSize(Image image, int requestedWidth, int requestedHeight) {
  
}