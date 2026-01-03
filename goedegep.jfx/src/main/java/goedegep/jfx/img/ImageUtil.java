package goedegep.jfx.img;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

import goedegep.util.img.PhotoFileMetaDataHandler;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * This is a utility class that provides utility methods for an {@code Image}.
 */
public class ImageUtil {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(ImageUtil.class.getName());
  
  /**
   * Private constructor as this is a utility class.
   */
  private ImageUtil() {
    
  }
  
  /**
   * Load an {@code Image}, taking the TIFF orientation into account.
   * <p>
   * The standard {@code new Image(String url)} loads an image but doesn't take the TIFF orientation into account. So if you just put this {@code Image} into an {@code ImageView}, it may be rotated.<br/>
   * This method loads the image with {@code new Image(String url)}, and then reads the TIFF information.<br/>
   * If no rotation is needed, the image is returned, else a new rotated image is created and then this correctly rotated image is returned.
   * 
   * @param file The image {@code File}.
   * @return the loaded {@code Image}
   */
  public static Image loadImage(File file) {
    try {
      return loadImage(file.toURI().toURL().toString());
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * Load an {@code Image}, taking the TIFF orientation into account.
   * <p>
   * The standard {@code new Image(String url)} loads an image but doesn't take the TIFF orientation into account. So if you just put this {@code Image} into an {@code ImageView}, it may be rotated.<br/>
   * This method loads the image with {@code new Image(String url)}, and then reads the TIFF information.<br/>
   * If no rotation is needed, the image is returned, else a new rotated image is created and then this correctly rotated image is returned.
   * 
   * @param urlString The URL (as String) of the image.
   * @return the loaded {@code Image}
   */
  public static Image loadImage(String urlString) {
       Image image = new Image(urlString);
    
    int orientation = TiffTagConstants.ORIENTATION_VALUE_HORIZONTAL_NORMAL; // default orientation, used if there is no 'Orientation' in the meta data.
    
    URI uri;
    try {
      uri = new URI(urlString);
      URL url = uri.toURL();
      String fileName = url.getFile();
      fileName = fileName.replaceAll("%20", " ");  // In a URL string spaces are represented as '%20'. This doesn't work with a File.
      
      PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(new File(fileName));
      String orientationText = photoFileMetaDataHandler.getTiffItemValue(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT, "Orientation");
      if (orientationText != null) {
        orientation = Integer.valueOf(orientationText);
      }
      
      return rotateImageBaseOnTiffOrientation(image, orientation);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (ImageReadException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
        
    return image;
  }

  /**
   * Rotate an {@code Image} based on the TIFF orientation value.
   * <p>
   * If no rotation is needed, the image is returned, else a new rotated image is created and then this correctly rotated image is returned.
   * 
   * @param image The {@code Image} to be rotated.
   * @param orientationValue The TIFF orientation value as defined in {@link TiffTagConstants}.
   * @return a correctly rotated image, which may be the input image.
   */
  public static Image rotateImageBaseOnTiffOrientation(Image image, int orientationValue) {
    if (image == null) {
      return null;
    }
    
    if (orientationValue == TiffTagConstants.ORIENTATION_VALUE_HORIZONTAL_NORMAL  ||  orientationValue == 0) {
      return image;
    }

    PixelReader reader = image.getPixelReader();
    // If the image isn't loaded yet or no pixel reader available, return the original image
    if (reader == null) {
      return image;
    }

    int srcWidth = (int) Math.round(image.getWidth());
    int srcHeight = (int) Math.round(image.getHeight());
    
    int rotatedImageWidth;
    int rotatedImageHeight;
    
    switch (orientationValue) {
    // values for which the dimensions remain the same.
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL:
    case TiffTagConstants.ORIENTATION_VALUE_ROTATE_180:
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_VERTICAL:
      rotatedImageWidth = srcWidth;
      rotatedImageHeight = srcHeight;
      break;
      
    // values for which width and height are swapped.
    case TiffTagConstants.ORIENTATION_VALUE_ROTATE_90_CW:
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_90_CW:
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_270_CW:
    case TiffTagConstants.ORIENTATION_VALUE_ROTATE_270_CW:
      rotatedImageWidth = srcHeight;
      rotatedImageHeight = srcWidth;
      break;
      
    default:
      throw new RuntimeException("Unknown orientation value");
    }

    WritableImage rotatedImage = new WritableImage(rotatedImageWidth, rotatedImageHeight);
    PixelWriter writer = rotatedImage.getPixelWriter();

    for (int srcY = 0; srcY < srcHeight; srcY++) {
      for (int srcX = 0; srcX < srcWidth; srcX++) {
        int argb = reader.getArgb(srcX, srcY);
        int destX = 0;
        int destY = 0;
        
        switch (orientationValue) {
        case TiffTagConstants.ORIENTATION_VALUE_ROTATE_90_CW:
          destX = srcHeight - 1 - srcY;
          destY = srcX;
          break;
          
        case TiffTagConstants.ORIENTATION_VALUE_ROTATE_180:
          destX = srcWidth - 1 - srcX;
          destY = srcHeight - 1 - srcY;
          break;
          
        case TiffTagConstants.ORIENTATION_VALUE_ROTATE_270_CW:
          destX = srcY;
          destY = srcWidth - 1 - srcX;
          break;
                    
        case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL:
          destX = srcWidth - 1 - srcX;
          destY = srcY;
          break;

        case TiffTagConstants.ORIENTATION_VALUE_MIRROR_VERTICAL:
          destX = srcX;
          destY = srcHeight - 1 - srcY;
          break;

        case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_90_CW:
          destX = srcHeight - 1 - srcY;
          destY = srcWidth - 1 - srcX;
          break;

        case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_270_CW:
          destX = srcY;
          destY =  srcX;
          break;
        }
                
        writer.setArgb(destX, destY, argb);
      }
    }

    return rotatedImage;
  }

//  /**
//   * Create a thumbnail for an image file.
//   * <p>
//   * An image file with a size of 1000 x 1000 is created for a given (larger) image.
//   * 
//   * @param imageFilename filename of the picture for which a thumbnail is to be created.
//   * @param thumbnailFolder the folder in which the thumbnail will be created.
//   * @throws IOException if the thumbnail file cannot be written.
//   */
//  private void createThumbnail(String imageFilename, File thumbnailFolder) throws IOException {
//    LOGGER.info("=> fileName=" + imageFilename + ", showFolder=" + thumbnailFolder.getAbsolutePath());
//    Image image = new Image("file:" + imageFilename, 1000.0, 1000.0, true, true);
//    File imageFile = new File(imageFilename);
//    String imageFileName = imageFile.getName();
//    Path thumbnailPath = Paths.get(thumbnailFolder.getAbsolutePath(), imageFileName);
//    File file = thumbnailPath.toFile();
//    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", file);
//  }

}
