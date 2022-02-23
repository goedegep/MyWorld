package goedegep.appgen.swing;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import goedegep.appgen.ImageSize;
import goedegep.util.img.ImageUtils;

/**
 * This class provides the common part of the implementation of AppResources.
 * Typically any class the wants to provide the AppResources interface will extend this class.
 */
public abstract class AbstractAppResources implements AppResources {
  private static final Logger         LOGGER = Logger.getLogger(AbstractAppResources.class.getName());
  
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
  
  // The default icons for the different supported sizes.
  // Each image is created when it is needed for the first time.
  private static Icon[] defaultInfoIcons = new ImageIcon[NUMBER_OF_SIZES];
  private static Icon[] defaultErrorIcons = new ImageIcon[NUMBER_OF_SIZES];
  private static Icon[] defaultAttentionIcons = new ImageIcon[NUMBER_OF_SIZES];
  private static Icon[] defaultApplicationIcons = new ImageIcon[NUMBER_OF_SIZES];
  
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
  
  // The sub class specific icons for the different supported sizes.
  // Each image is created when it is needed for the first time.
  private Icon[] infoIcons = null;
  private Icon[] errorIcons = null;
  private Icon[] attentionIcons = null;
  private Icon[] applicationIcons = null;
    
  // The sub class specific picture.
  private Image picture;

  
  public AbstractAppResources() {
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

  
  /*
   * get Image with border
   */
  
  
  @Override
  public Image getInfoImageWithBorder(ImageSize imageSize) {
    init();
    
    Image[] rawImages;
    
    if (rawInfoImages != null) {
      rawImages = rawInfoImages;
    } else {
      rawImages = rawDefaultInfoImages;
    }
    
    return createImageWithBorderForSize(rawImages, imageSize);
  }
  
  @Override
  public Image getErrorImageWithBorder(ImageSize imageSize) {
    init();
    
    Image[] rawImages;
    
    if (rawErrorImages != null) {
      rawImages = rawErrorImages;
    } else {
      rawImages = rawDefaultErrorImages;
    }
    
    return createImageWithBorderForSize(rawImages, imageSize);
  }
  
  @Override
  public Image getAttentionImageWithBorder(ImageSize imageSize) {
    init();
    
    Image[] rawImages;
    
    if (rawAttentionImages != null) {
      rawImages = rawAttentionImages;
    } else {
      rawImages = rawDefaultAttentionImages;
    }
    
    return createImageWithBorderForSize(rawImages, imageSize);
  }
  
  @Override
  public Image getApplicationImageWithBorder(ImageSize imageSize) {
    init();
    
    Image[] rawImages;
    
    if (rawApplicationImages != null) {
      rawImages = rawApplicationImages;
    } else {
      rawImages = rawDefaultApplicationImages;
    }
    
    return createImageWithBorderForSize(rawImages, imageSize);
  }

  
  /*
   * get Icon
   */
  
  
  @Override
  public Icon getInfoIcon(ImageSize imageSize) {
    init();

    Icon icon;
    
    if (rawInfoImages != null) {
      if (infoIcons[imageSize.ordinal()] == null) {
        infoIcons[imageSize.ordinal()] = new ImageIcon(getInfoImage(imageSize));
      }
      icon = infoIcons[imageSize.ordinal()];
    } else {
      if (defaultInfoIcons[imageSize.ordinal()] == null) {
        defaultInfoIcons[imageSize.ordinal()] = new ImageIcon(getInfoImage(imageSize));
      }
      icon = defaultInfoIcons[imageSize.ordinal()];
    }
    
    return icon;
  }
  
  @Override
  public Icon getErrorIcon(ImageSize imageSize) {
    init();
    
    Icon icon;
    
    if (rawErrorImages != null) {
      if (errorIcons[imageSize.ordinal()] == null) {
        errorIcons[imageSize.ordinal()] = new ImageIcon(getErrorImage(imageSize));
      }
      icon = errorIcons[imageSize.ordinal()];
    } else {
      if (defaultErrorIcons[imageSize.ordinal()] == null) {
        defaultErrorIcons[imageSize.ordinal()] = new ImageIcon(getErrorImage(imageSize));
      }
      icon = defaultErrorIcons[imageSize.ordinal()];
    }
    
    return icon;
  }
  
  @Override
  public Icon getAttentionIcon(ImageSize imageSize) {
    init();

    Icon icon;
    
    if (rawAttentionImages != null) {
      if (attentionIcons[imageSize.ordinal()] == null) {
        attentionIcons[imageSize.ordinal()] = new ImageIcon(getAttentionImage(imageSize));
      }
      icon = attentionIcons[imageSize.ordinal()];
    } else {
      if (defaultAttentionIcons[imageSize.ordinal()] == null) {
        defaultAttentionIcons[imageSize.ordinal()] = new ImageIcon(getAttentionImage(imageSize));
      }
      icon = defaultAttentionIcons[imageSize.ordinal()];
    }
    
    return icon;
  }
  
  @Override
  public Icon getApplicationIcon(ImageSize imageSize) {
    init();
    
    Icon icon;
    
    if (rawApplicationImages != null) {
      if (applicationIcons[imageSize.ordinal()] == null) {
        applicationIcons[imageSize.ordinal()] = new ImageIcon(getApplicationImage(imageSize));
      }
      icon = applicationIcons[imageSize.ordinal()];
    } else {
      if (defaultApplicationIcons[imageSize.ordinal()] == null) {
        defaultApplicationIcons[imageSize.ordinal()] = new ImageIcon(getApplicationImage(imageSize));
      }
      icon = defaultApplicationIcons[imageSize.ordinal()];
    }
    
    return icon;
  }
  
  
  /*
   * get Icon with border
   */

  @Override
  public Icon getInfoIconWithBorder(ImageSize imageSize) {
    return new ImageIcon(getInfoImageWithBorder(imageSize));
  }

  @Override
  public Icon getErrorIconWithBorder(ImageSize imageSize) {
    return new ImageIcon(getErrorImageWithBorder(imageSize));
  }

  @Override
  public Icon getAttentionIconWithBorder(ImageSize imageSize) {
    return new ImageIcon(getAttentionImageWithBorder(imageSize));
  }

  @Override
  public Icon getApplicationIconWithBorder(ImageSize imageSize) {
    return new ImageIcon(getApplicationImageWithBorder(imageSize));
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
//      if (rawInfoImages != null) {
        infoImages = new Image[NUMBER_OF_SIZES];
        infoIcons = new ImageIcon[NUMBER_OF_SIZES];
//      }
      
//      if (rawErrorImages != null) {
        errorImages = new Image[NUMBER_OF_SIZES];
        errorIcons = new ImageIcon[NUMBER_OF_SIZES];
//      }

//      if (rawAttentionImages != null) {
        attentionImages = new Image[NUMBER_OF_SIZES];
        attentionIcons = new ImageIcon[NUMBER_OF_SIZES];
//      }

//      if (rawApplicationImages != null) {
        applicationImages = new Image[NUMBER_OF_SIZES];
        applicationIcons = new ImageIcon[NUMBER_OF_SIZES];
//      }

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
   * Create an image, with a border, for a specific size.
   * The size includes the border.
   */
  private Image createImageWithBorderForSize(Image[] rawImages, ImageSize imageSize) {
    return createImageWithBorder(getImageForSize(rawImages, imageSize.getWidth() - 2 * IMAGE_BORDER_SIZE, imageSize.getHeight() - 2 * IMAGE_BORDER_SIZE));
  }
  
  private static Image createImageWithBorder(Image img) {
    BufferedImage bi = new BufferedImage(img.getWidth(null) + 2 * IMAGE_BORDER_SIZE, img.getHeight(null) + 2 * IMAGE_BORDER_SIZE,
        BufferedImage.TYPE_INT_ARGB);
    Graphics g = bi.getGraphics();
    g.drawImage(img, IMAGE_BORDER_SIZE, IMAGE_BORDER_SIZE, null);
    
    return bi;
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
      
      if ((secondImage.getWidth(null) > width)  ||
          (secondImage.getHeight(null) > height)) {
        break;
      }
    }
    
    Image image;
    if (firstImage == null) {
      image = secondImage;
    } else {
      float requestedSize = width * height;
      float firstImageSize = firstImage.getWidth(null) * firstImage.getHeight(null);
      float firstImageSizeRatio = requestedSize / firstImageSize;
      float secondImageSize = secondImage.getWidth(null) * secondImage.getHeight(null);
      float secondImageSizeRatio = secondImageSize / requestedSize;

      if (firstImageSizeRatio < secondImageSizeRatio) {
        image = firstImage; 
      } else {
        image = secondImage;
      }
    }
    
    if ((image.getWidth(null) == width) &&
        (image.getHeight(null) == height)) {
      return image;
    } else {
      return ImageUtils.scaleImage(image, width, height);
    }
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
          LOGGER.info("Going to read image: " + INFO_IMAGE_NAMES[i]);
          rawDefaultInfoImages[i] = ImageIO.read(goedegep.appgen.swing.AbstractAppResources.class.getResource(INFO_IMAGE_NAMES[i]));
        }
      }

      if (rawErrorImages == null  &&  rawDefaultErrorImages == null) {
        rawDefaultErrorImages = new Image[ERROR_IMAGE_NAMES.length];

        for (int i = 0; i < ERROR_IMAGE_NAMES.length; i++) {
          LOGGER.info("Going to read image: " + ERROR_IMAGE_NAMES[i]);
          rawDefaultErrorImages[i] = ImageIO.read(goedegep.appgen.swing.AbstractAppResources.class.getResource(ERROR_IMAGE_NAMES[i]));
        }
      }

      if (rawAttentionImages == null  &&  rawDefaultAttentionImages == null) {
        rawDefaultAttentionImages = new Image[ATTENTION_IMAGE_NAMES.length];

        for (int i = 0; i < ATTENTION_IMAGE_NAMES.length; i++) {
          LOGGER.info("Going to read image: " + ATTENTION_IMAGE_NAMES[i]);
          rawDefaultAttentionImages[i] = ImageIO.read(goedegep.appgen.swing.AbstractAppResources.class.getResource(ATTENTION_IMAGE_NAMES[i]));
        }
      }

      if (rawApplicationImages == null  &&  rawDefaultApplicationImages == null) {
        rawDefaultApplicationImages = new Image[APPLICATION_IMAGE_NAMES.length];

        for (int i = 0; i < APPLICATION_IMAGE_NAMES.length; i++) {
          LOGGER.info("Going to read image: " + APPLICATION_IMAGE_NAMES[i]);
          rawDefaultApplicationImages[i] = ImageIO.read(goedegep.appgen.swing.AbstractAppResources.class.getResource(APPLICATION_IMAGE_NAMES[i]));
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    LOGGER.info("<=");
  }
}
