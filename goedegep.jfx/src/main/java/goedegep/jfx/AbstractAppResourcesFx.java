package goedegep.jfx;

import java.util.logging.Logger;

import goedegep.appgen.ImageSize;
//import goedegep.util.img.ImageUtil;
import javafx.scene.image.Image;

/**
 * This class provides the common part of the implementation of AppResources.
 * Typically any class the wants to provide the AppResources interface will extend this class.
 */
public abstract class AbstractAppResourcesFx implements AppResourcesFx {
  private static final Logger LOGGER = Logger.getLogger(AbstractAppResourcesFx.class.getName());
  private final static String NEWLINE = System.getProperty("line.separator");
  
  public static final int NUMBER_OF_SIZES = ImageSize.values().length;
  
  // Names of files for the default icons.
  private static final String[] INFO_IMAGE_NAMES = {"info_225x225.jpg"};
  private static final String[] ERROR_IMAGE_NAMES = {"error_204x204.jpg"};  // TODO Add transparancy to icon.
  private static final String[] ATTENTION_IMAGE_NAMES = {"attention_239x211.png"};
  private static final String[] APPLICATION_IMAGE_NAMES = {"java_icon.gif"};
  
  // Raw default images: the images read from the files.
  // These images are read, if the sub class doesn't provide ALL the raw images.
  private static Image[] rawDefaultInfoImages = null;        // One or more info images read from a file.
  private static Image[] rawDefaultErrorImages = null;       // One or more error images read from a file.
  private static Image[] rawDefaultAttentionImages = null;   // One or more attention images read from a file.
  private static Image[] rawDefaultApplicationImages = null; // One or more application images read from a file.
  
  // The default images for the different supported sizes; the best raw image, scaled to the size.
  // Each image is created when it is needed for the first time.
  private static Image[] defaultInfoImages = new Image[NUMBER_OF_SIZES];
  private static Image[] defaultErrorImages = new Image[NUMBER_OF_SIZES];
  private static Image[] defaultAttentionImages = new Image[NUMBER_OF_SIZES];
  private static Image[] defaultApplicationImages = new Image[NUMBER_OF_SIZES];
    
  private boolean initialized = false;
  
  // The sub class specific raw images; the images read from the files.
  // The raw images are read, by a sub class, on the first image request.
  private Image[] rawInfoImages = null;          // One or more info images read from file.
  private Image[] rawErrorImages = null;         // One or more error images read from file.
  private Image[] rawAttentionImages = null;     // One or more attention images read from file.
  private Image[] rawApplicationImages = null;   // One or more application images read from file.
  
  // The sub class specific images for the different supported sizes; the best raw image, scaled to the size.
  // Each image is created when it is needed for the first time.
  private Image[] infoImages = null;
  private Image[] errorImages = null;
  private Image[] attentionImages = null;
  private Image[] applicationImages = null;
      
  // The sub class specific picture.
  private Image picture;

  
  public AbstractAppResourcesFx() {
    // Nothing is done in this constructor. Information is read and created on request.
    LOGGER.info("=> <=");
  }
  
  
  /*
   * get Raw Default Images
   */

  @Override
  public Image[] getRawDefaultInfoImages() {
    init();
    
    return rawDefaultInfoImages;
  }
  
  @Override
  public Image[] getRawDefaultErrorImages() {
    init();

    return rawDefaultErrorImages;
  }
  
  @Override
  public Image[] getRawDefaultAttentionImages() {
    init();
    
    return rawDefaultAttentionImages;
  }
  
  @Override
  public Image[] getRawDefaultApplicationImages() {
    init();
    
    return rawDefaultApplicationImages;
  }
  
  
  /*
   * get Raw Images
   */
  
  @Override
  public Image[] getRawInfoImages() {
    init();
    
    Image[] images;

    if (rawInfoImages != null) {
      images = rawInfoImages;
    } else {
      images = rawDefaultInfoImages;
    }

    return images;
  }
  
  @Override
  public Image[] getRawErrorImages() {
    init();

    Image[] images;

    if (rawErrorImages != null) {
      images = rawErrorImages;
    } else {
      images = rawDefaultErrorImages;
    }

    return images;
  }
  
  @Override
  public Image[] getRawAttentionImages() {
    init();
    
    Image[] images;

    if (rawAttentionImages != null) {
      images = rawAttentionImages;
    } else {
      images = rawDefaultAttentionImages;
    }

    return images;
  }
  
  @Override
  public Image[] getRawApplicationImages() {
    init();
    
    Image[] images;

    if (rawApplicationImages != null) {
      images = rawApplicationImages;
    } else {
      images = rawDefaultApplicationImages;
    }

    return images;
  }

  
  /*
   * get Image
   */
  
  @Override
  public Image getInfoImage(ImageSize imageSize) {
    init();
    
    Image image;
    
    if (rawInfoImages != null) {
      if ((infoImages.length < imageSize.ordinal() + 1)  ||  infoImages[imageSize.ordinal()] == null) {
        infoImages[imageSize.ordinal()] = getImageForSize(rawInfoImages, imageSize.getWidth(), imageSize.getHeight());
      }
      image = infoImages[imageSize.ordinal()];
    } else {
      if (defaultInfoImages[imageSize.ordinal()] == null) {
        defaultInfoImages[imageSize.ordinal()] = getImageForSize(rawDefaultInfoImages, imageSize.getWidth(), imageSize.getHeight());
      }
      image = defaultInfoImages[imageSize.ordinal()];
    }
    
    return image;
  }
  
  @Override
  public Image getErrorImage(ImageSize imageSize) {
    init();
    
    Image image;
    
    if (rawErrorImages != null) {
      if (errorImages[imageSize.ordinal()] == null) {
        errorImages[imageSize.ordinal()] = getImageForSize(rawErrorImages, imageSize.getWidth(), imageSize.getHeight());
      }
      image = errorImages[imageSize.ordinal()];
    } else {
      if (defaultErrorImages[imageSize.ordinal()] == null) {
        defaultErrorImages[imageSize.ordinal()] = getImageForSize(rawDefaultErrorImages, imageSize.getWidth(), imageSize.getHeight());
      }
      image = defaultErrorImages[imageSize.ordinal()];
    }
    
    return image;
  }
  
  @Override
  public Image getAttentionImage(ImageSize imageSize) {
    init();
    
    Image image;
    
    if (rawAttentionImages != null) {
      if (attentionImages[imageSize.ordinal()] == null) {
        attentionImages[imageSize.ordinal()] = getImageForSize(rawAttentionImages, imageSize.getWidth(), imageSize.getHeight());
      }
      image = attentionImages[imageSize.ordinal()];
    } else {
      if (defaultAttentionImages[imageSize.ordinal()] == null) {
        defaultAttentionImages[imageSize.ordinal()] = getImageForSize(rawDefaultAttentionImages, imageSize.getWidth(), imageSize.getHeight());
      }
      image = defaultAttentionImages[imageSize.ordinal()];
    }
    
    return image;
  }
  
  @Override
  public Image getApplicationImage(ImageSize imageSize) {
    init();
    
    Image image;
    
    if (rawApplicationImages != null) {
      if (applicationImages[imageSize.ordinal()] == null) {
        applicationImages[imageSize.ordinal()] = getImageForSize(rawApplicationImages, imageSize.getWidth(), imageSize.getHeight());
      }
      image = applicationImages[imageSize.ordinal()];
    } else {
      if (defaultApplicationImages[imageSize.ordinal()] == null) {
        defaultApplicationImages[imageSize.ordinal()] = getImageForSize(rawDefaultApplicationImages, imageSize.getWidth(), imageSize.getHeight());
      }
      image = defaultApplicationImages[imageSize.ordinal()];
    }
    
    return image;
  }

  
  

  @Override
  public Image getPicture() {
    init();
    
    return picture;
  }

  /* Sub classes shall implement this method to read the resources.
   * If there are images read, it shall call setRawImages() with the images.
   * If there's a picture read, it shall call setPicture() with the image.
   * */
  protected abstract void readResources();
  
  // Method to be called by the readResources() method of the sub classes if there are images read.
  protected void setRawImages(Image[] rawInfoImages, Image[] rawErrorImages, Image[] rawAttentionImages, Image[] rawApplicationImages) {
    this.rawApplicationImages = rawApplicationImages;
  }
  
  // Method to be called by the readResources() method of the sub class if there's a picture image read.
  protected void setPicture(Image picture) {
    this.picture = picture;
  }
  
  // Basic image initialization
  private void init() {
    LOGGER.info("=>");
    if (!initialized) {
      // First read the application specific images
      readResources();
      
      /*
       * For each image:
       * If there's at least one raw image, create the array to hold the images and icons.
       * The images and icons themselves are only created when they're requested.
       */
        infoImages = new Image[NUMBER_OF_SIZES];      
        errorImages = new Image[NUMBER_OF_SIZES];
        attentionImages = new Image[NUMBER_OF_SIZES];
        applicationImages = new Image[NUMBER_OF_SIZES];

      // Then read the default images. This is only done for the images for which there's no application specific image.
      readDefaultResources();
      
      if (rawInfoImages == null) {
        rawInfoImages = rawDefaultInfoImages;
      }
      
      if (rawErrorImages == null) {
        rawErrorImages = rawDefaultErrorImages;
      }
      
      if (rawAttentionImages == null) {
        rawAttentionImages = rawDefaultAttentionImages;
      }

      if (rawApplicationImages.length == 0) {  // TODO provide a default application image.
        throw new RuntimeException("Geen beschikbare afbeeldingen");
      }

      initialized = true;
    }
    LOGGER.info("<=");
  }
    
  /*
   * Select the best image for a specific size.
   */
  private Image getImageForSize(Image[] rawImages, int width, int height) {
    Image firstImage = null;
    Image secondImage = null;
    
    // find the rawImages with best fitting sizes
    for (int i = 0; i < rawImages.length; i++) {
      firstImage = secondImage;
      secondImage = rawImages[i];
      
      if ((secondImage.getWidth() > width)  ||
          (secondImage.getHeight() > height)) {
        break;
      }
    }
    
    Image image;
    if (firstImage == null) {
      image = secondImage;
    } else {
      double requestedSize = width * height;
      double firstImageSize = firstImage.getWidth() * firstImage.getHeight();
      double firstImageSizeRatio = requestedSize / firstImageSize;
      double secondImageSize = secondImage.getWidth() * secondImage.getHeight();
      double secondImageSizeRatio = secondImageSize / requestedSize;

      if (firstImageSizeRatio < secondImageSizeRatio) {
        image = firstImage; 
      } else {
        image = secondImage;
      }
    }
    
    return image;
  }
  
  /*
   * Read default images. For the images for which no application specific images is loaded,
   * and for which no default images are loaded yet, the default images are loaded.
   */
  private void readDefaultResources() {
    LOGGER.info("=>");

    try {
      if (rawInfoImages == null &&  rawDefaultInfoImages == null) {
        rawDefaultInfoImages = new Image[INFO_IMAGE_NAMES.length];

        for (int i = 0; i < INFO_IMAGE_NAMES.length; i++) {
          LOGGER.fine("Going to read image: " + INFO_IMAGE_NAMES[i]);
          rawDefaultInfoImages[i] = new Image(goedegep.jfx.AbstractAppResourcesFx.class.getResourceAsStream(INFO_IMAGE_NAMES[i]));
        }
      }

      if (rawErrorImages == null  &&  rawDefaultErrorImages == null) {
        rawDefaultErrorImages = new Image[ERROR_IMAGE_NAMES.length];

        for (int i = 0; i < ERROR_IMAGE_NAMES.length; i++) {
          LOGGER.info("Going to read image: " + ERROR_IMAGE_NAMES[i]);
          rawDefaultErrorImages[i] = new Image(goedegep.jfx.AbstractAppResourcesFx.class.getResourceAsStream(ERROR_IMAGE_NAMES[i]));
        }
      }

      if (rawAttentionImages == null  &&  rawDefaultAttentionImages == null) {
        rawDefaultAttentionImages = new Image[ATTENTION_IMAGE_NAMES.length];

        for (int i = 0; i < ATTENTION_IMAGE_NAMES.length; i++) {
          LOGGER.info("Going to read image: " + ATTENTION_IMAGE_NAMES[i]);
          rawDefaultAttentionImages[i] = new Image(goedegep.jfx.AbstractAppResourcesFx.class.getResourceAsStream(ATTENTION_IMAGE_NAMES[i]));
        }
      }

      if (rawApplicationImages == null  &&  rawDefaultApplicationImages == null) {
        rawDefaultApplicationImages = new Image[APPLICATION_IMAGE_NAMES.length];

        for (int i = 0; i < APPLICATION_IMAGE_NAMES.length; i++) {
          LOGGER.info("Going to read image: " + APPLICATION_IMAGE_NAMES[i]);
          rawDefaultApplicationImages[i] = new Image(goedegep.jfx.AbstractAppResourcesFx.class.getResourceAsStream(APPLICATION_IMAGE_NAMES[i]));
        }
      }

    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    LOGGER.info("<=");
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    if (rawDefaultInfoImages != null) {
      buf.append("Number of rawDefaultInfoImages: ").append(rawDefaultInfoImages.length);
    } else {
      buf.append("No rawDefaultInfoImages");
    }
    buf.append(NEWLINE);
    
    if (rawDefaultErrorImages != null) {
      buf.append("Number of rawDefaultErrorImages: ").append(rawDefaultErrorImages.length);
    } else {
      buf.append("No rawDefaultErrorImages");
    }
    buf.append(NEWLINE);
    
    if (rawDefaultAttentionImages != null) {
      buf.append("Number of rawDefaultAttentionImages: ").append(rawDefaultAttentionImages.length);
    } else {
      buf.append("No rawDefaultAttentionImages");
    }
    buf.append(NEWLINE);
    
    if (rawDefaultApplicationImages != null) {
      buf.append("Number of rawDefaultApplicationImages: ").append(rawDefaultApplicationImages.length);
    } else {
      buf.append("No rawDefaultApplicationImages");
    }
    buf.append(NEWLINE);
    
    
    if (rawInfoImages != null) {
      buf.append("Number of rawInfoImages: ").append(rawInfoImages.length);
    } else {
      buf.append("No rawInfoImages");
    }
    buf.append(NEWLINE);
    
    if (rawErrorImages != null) {
      buf.append("Number of rawErrorImages: ").append(rawErrorImages.length);
    } else {
      buf.append("No rawErrorImages");
    }
    buf.append(NEWLINE);
    
    if (rawAttentionImages != null) {
      buf.append("Number of rawAttentionImages: ").append(rawAttentionImages.length);
    } else {
      buf.append("No rawAttentionImages");
    }
    buf.append(NEWLINE);
    
    if (rawApplicationImages != null) {
      buf.append("Number of rawApplicationImages: ").append(rawApplicationImages.length);
    } else {
      buf.append("No rawApplicationImages");
    }
    buf.append(NEWLINE);
    
    return buf.toString();
  }
}
