package goedegep.resources;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.scene.image.Image;

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
  BACKPACK(new ImageFileInfo[] {
      new ImageFileInfo("Backpack - 64x64.png", 64, 64),
      new ImageFileInfo("Backpack - 128x128.png", 128, 128),
      new ImageFileInfo("Backpack - 512x512.png", 512, 512),
      }, "a backpack", null),
  BOAT(new ImageFileInfo[] {
      new ImageFileInfo("Boat - 48x48.png", 48, 48),
      new ImageFileInfo("Boat - 512x512.png", 512, 512)
      }, "boat", "https://iconscout.com/"),
  BUS(new ImageFileInfo[] {new ImageFileInfo("Bus - 512x504.png", 512, 504)}, "bus", "https://uxwing.com/"),
  BUS_STOP(new ImageFileInfo[] {
      new ImageFileInfo("BusStop - 48x48.png", 48, 48),
      new ImageFileInfo("BusStop - 512x512.png", 512, 512)
      }, "bus stop", "<a href=\"https://www.flaticon.com/free-icons/bus\" title=\"bus icons\">Bus icons created by Freepik - Flaticon</a>"),
  CABLE_CAR(new ImageFileInfo[] {
      new ImageFileInfo("CableCar - 800x800.png", 800, 800),
      new ImageFileInfo("CableCar - 48x48.png", 48, 48)
      }, "a cable car", null),
  CAFE(new ImageFileInfo[] {new ImageFileInfo("Coffee - 512x512.png", 512, 512)}, "a cup of coffee", null),
  CAMERA_BLACK(new ImageFileInfo[] {new ImageFileInfo("CameraBlack.png", 28, 22)}, "a black photo camera", null),
  CAMERA_GRAY(new ImageFileInfo[] {new ImageFileInfo("CameraGray.png", 28, 22)}, "a gray photo camera", null),
  CAMERA_BLUE(new ImageFileInfo[] {new ImageFileInfo("CameraBlue.png", 28, 22)}, "a blue photo camera", null),
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
  CANOEING(new ImageFileInfo[] {new ImageFileInfo("Canoeing - 512x512.png", 512, 512)}, "a person canoeing", null),
  CYCLING(new ImageFileInfo[] {new ImageFileInfo("RegularBiking.png", 128, 128)}, "regular biking", "from https://findicons.com/"),
  DEMO(new ImageFileInfo[] {new ImageFileInfo("Demo - 512x512.png", 128, 128)}, "computer screen with demo as text", "<a href=\"https://www.flaticon.com/free-icons/demo\" title=\"demo icons\">Demo icons created by Freepik - Flaticon</a>"),
  FOLDER_WITH_FILES(new ImageFileInfo[] {new ImageFileInfo("FolderWithFiles - 64x64.png", 64, 64)}, "a folder containing files", "<a href=\"https://www.flaticon.com/free-icons/files-and-folders\" title=\"files and folders icons\">Files and folders icons created by NajmunNahar - Flaticon</a>"),
  GPX(new ImageFileInfo[] {new ImageFileInfo("Gpx.png", 359, 329)}, "a map with a track and a location", null),
  HORSE_RIDING(new ImageFileInfo[] {
      new ImageFileInfo("HorseRiding - 48x48.png", 48, 48),
      new ImageFileInfo("HorseRiding - 512x512.png", 512, 512)

      }, "horse riding", "<a href=\"https://www.flaticon.com/free-icons/rider\" title=\"rider icons\">Rider icons created by Freepik - Flaticon</a>"),
  ISLAND(new ImageFileInfo[] {
      new ImageFileInfo("Island - 32x32.png", 32, 32),
      new ImageFileInfo("Island - 512x512.png", 512, 512)

      }, "an island with a palm tree", "<a href=\"https://www.flaticon.com/free-icons/island\" title=\"island icons\">Island icons created by Freepik - Flaticon</a>"),
  LOCATION_FLAG_BLUE(new ImageFileInfo[] {new ImageFileInfo("LocationFlagBlue.png", 121, 126)}, "a blue location flag", null),
  LOCATION_FLAG_YELLOW(new ImageFileInfo[] {new ImageFileInfo("LocationFlagYellow.png", 121, 126)}, "a yellow location flag", null),
  MAGNIFYING_GLASS(new ImageFileInfo[] {new ImageFileInfo("MagnifyingGlass - 256x256.png", 256, 256)}, "a magnifying glass", null),
  MAP(new ImageFileInfo[] {new ImageFileInfo("Map - 487x487.png", 487, 487)}, "a map", null),
  MARKET(new ImageFileInfo[] {
      new ImageFileInfo("Market - 48x48.png", 48, 48),
      new ImageFileInfo("Market - 512x512.png", 512, 512)}, "a market", null),
  MARKDOWN(new ImageFileInfo[] {new ImageFileInfo("Markdown logo - 1600x1600.png", 487, 487)}, "Markdown logo", "https://imgbin.com/"),
  MOTORCYCLE(new ImageFileInfo[] {new ImageFileInfo("Motorcycle - 512x512.png", 487, 487)}, "Motorcycle", "<a href=\"https://www.flaticon.com/free-icons/motorcycle\" title=\"motorcycle icons\">Motorcycle icons created by Freepik - Flaticon</a>"),
  MS_WORD(new ImageFileInfo[] {new ImageFileInfo("MSWord - 1047x1024.png", 1047, 1024)}, "Microsoft Word document icon", "User:Airhogs777"),
  ODT(new ImageFileInfo[] {new ImageFileInfo("ODT - 512x512.png", 1047, 1024)}, "OpenDocument Text document icon", "<a href=\"https://www.flaticon.com/free-icons/files-and-folders\" title=\"files and folders icons\">Files and folders icons created by Awicon - Flaticon</a>"),
  PLANE_TAKEOFF(new ImageFileInfo[] {
      new ImageFileInfo("PlaneTakeoff - 32x32.png", 32, 32),
      new ImageFileInfo("PlaneTakeoff - 64x64.png", 64, 64),
      new ImageFileInfo("PlaneTakeoff - 128x128.png", 128, 128),
      new ImageFileInfo("PlaneTakeoff - 256x256.png", 256, 256),
      new ImageFileInfo("PlaneTakeoff - 512x512.png", 512, 512)
      }, "a plane taking off", "https://icon-icons.com/"),
  PDF(new ImageFileInfo[] {new ImageFileInfo("PDF - 417x512.png", 417, 512)}, "PDF file logo", null),
  PHOTO_FOLDER(new ImageFileInfo[] {new ImageFileInfo("PhotoFolder - 199x217.png", 417, 512)}, "Photo folder icon", "https://icon-library.com/icon/microsoft-folder-icon-24.html.html>Microsoft Folder Icon # 392312"),
  RIVER(new ImageFileInfo[] {new ImageFileInfo("River - 512x512.png", 57, 32)}, "a river", "<a href=\"https://www.freepik.com/icons/river\">Icon by Warangkhana Sookruay</a>"),
  ROAD_TO_HORIZON(new ImageFileInfo[] {new ImageFileInfo("RoadToHorizon - 57x32.png", 57, 32), new ImageFileInfo("RoadToHorizon - 114x64.png", 114, 64)}, "a road to the horizon", null),
  RUNNING(new ImageFileInfo[] {
      new ImageFileInfo("RunningFigure - 48x48.png", 48, 48),
      new ImageFileInfo("RunningFigure - 512x512.png", 512, 512)
      }, "running figure", "<a href=\"https://www.flaticon.com/free-icons/run\" title=\"run icons\">Run icons created by Freepik - Flaticon</a>"),
  SKIÏNG(new ImageFileInfo[] {new ImageFileInfo("Skiïng.png", 34, 39)}, "a skiër", null),
  SNOW_MOUNTAINS(new ImageFileInfo[] {new ImageFileInfo("SnowMountains - 64x30.png", 64, 30)}, "snow capped mountains", null),
  SUNRISE(new ImageFileInfo[] {new ImageFileInfo("Sunrise - 256x189.png", 256, 189)}, "sunrise (or sunset)", null),
  TEXT(new ImageFileInfo[] {new ImageFileInfo("Text - 512x512.png", 512, 512)}, "a letter T for Text", null),
  TEXT_FILE(new ImageFileInfo[] {new ImageFileInfo("TextIcon - 512x512.png", 512, 512)}, "Text file icon", "<a href=\"https://www.flaticon.com/free-icons/text-file\" title=\"text file icons\">Text file icons created by Freepik - Flaticon</a>"),
  TRAIN(new ImageFileInfo[] {
      new ImageFileInfo("Train - 48x48.png", 48, 48),
      new ImageFileInfo("Train - 512x512.png", 512, 512)
      }, "a train", "<a href=\"https://www.freepik.com/icon/train_4540243#fromView=keyword&page=1&position=67&uuid=b9dfd6a4-8d00-4a11-9397-c7033983ba0e\">Icon by Milkghost Studio</a>"),
  TREES(new ImageFileInfo[] {
      new ImageFileInfo("Trees - 32x32.png", 32, 32),
      new ImageFileInfo("Trees - 64x64.png", 64, 64),
      new ImageFileInfo("Trees - 512x512.png", 512, 512)
      }, "trees", "<a href=\"https://www.flaticon.com/free-icons/trees\" title=\"trees icons\">Trees icons created by surang - Flaticon</a>"),
  VIDEO_FOLDER(new ImageFileInfo[] {new ImageFileInfo("VideoFolder - 936x936.png", 936, 936)}, "Video folder icon", null),
  WALKING(new ImageFileInfo[] {new ImageFileInfo("Walking - 512x512.png", 512, 512)}, "a person walking with stick and backpack", null),
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