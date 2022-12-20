package goedegep.util.img;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

import goedegep.geo.WGS84Coordinates;

/**
 * This is a utility class with methods which operate on images (Image).
 *
 */
public class ImageUtils {
  private static final Logger LOGGER = Logger.getLogger(ImageUtils.class.getName());
  
  /**
   * Private constructor, so this class cannot be instantiated.
   */
  private ImageUtils() {
    
  }
  
  /**
   * Get the <code>WGS84DoubleCoordinates</code> from an image file.
   * 
   * @return the coordinates of the photo file, or null if these aren't available.
   */
  public static WGS84Coordinates getGeoLocation(String imageFileName) {
    WGS84Coordinates coordinates = null;
    
    if (imageFileName == null) {
      return coordinates;
    }
    File file = new File(imageFileName);

    try {
      PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(file);
      coordinates = photoFileMetaDataHandler.getGeoLocation();
    } catch (ImageReadException | IOException e) {
      e.printStackTrace();
    }
    
    return coordinates;
  }
  
  /**
   * Scale an Image to a specific size.
   * <p>
   * Note that if the ratio between the specified width and height differs from the ratio of the image, the scaled image is
   * a horizontally or vertically stretched image.
   * 
   * @param image The Image to be scaled.
   * @param width The width of the scaled image.
   * @param height The height of the scaled image.
   * @return The <code>image</code> scaled to the specified <code>width</code> and <code>height</code>.
   */
  public static Image scaleImage(Image image, int width, int height) {
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics g = bufferedImage.getGraphics();
    
    g.drawImage(image, 0, 0, width - 1, height - 1, 0, 0, image.getWidth(null), image.getHeight(null), null);
    
    return bufferedImage;
  }
  

  /**
   * Read an image from a file, scale it to a specific maximum height and add a border.
   * <p>
   * The specified height is for the image plus border, so the image is scaled to the specified size, minus two times the border size.
   * If the height of the image is not greater than the specified maximum height, the original image is used (no upscaling).
   * Otherwise, the image is scaled down to the maximum height. The width/height ratio is preserved.
   * 
   * @param file the <code>File</code> from which the image will be read.
   * @param maxHeight the maximum height of the image.
   * @param borderSize the size of the border.
   * @return the read image, scaled and provided with a border.
   */
  public static BufferedImage scaleImageToMaxHeightWithBorderAdded(File file, int maxHeight, int borderSize) throws IOException {
      BufferedImage scaledImage = scaleImageToMaxHeight(file, maxHeight - borderSize * 2);

      int width = scaledImage.getWidth();
      int height = scaledImage.getHeight();
      int borderedImageWidth = width + borderSize * 2;
      int borderedImageHeight = height + borderSize * 2;

      BufferedImage borderedImage = new BufferedImage(borderedImageWidth, borderedImageHeight, BufferedImage.TYPE_3BYTE_BGR);

      Graphics2D g2 = borderedImage.createGraphics();
      g2.setColor(Color.YELLOW);
      g2.fillRect(0, 0, borderedImageWidth, borderedImageHeight);
      g2.drawImage(scaledImage, borderSize, borderSize, width + borderSize, height + borderSize, 0, 0, width, height, Color.YELLOW, null);

      return borderedImage;
  }

  /**
   * Read an image from a file and scale it to a specific maximum height.
   * <p>
   * If the height of the image is not greater than the specified maximum height, the image is not changed.
   * Otherwise, the image is scaled down to the maximum height. The width/height ratio is preserved.
   * 
   * @param file the <code>File</code> from which the image will be read.
   * @param maxHeight the maximum height of the image.
   * @return the read image, scaled to <code>maxHeight</code> or the original image, if the image height isn't larger then <code>maxHeight</code>.
   */
  public static BufferedImage scaleImageToMaxHeight(File file, int maxHeight) throws IOException {
      return scaleImageToMaxHeight(ImageIO.read(file), maxHeight);
  }

  /**
   * Scale an image to a specific maximum height.
   * <p>
   * If the height of the image is not greater than the specified maximum height, the original image is returned.
   * Otherwise, the image is scaled down to the maximum height. The width/height ratio is preserved.
   * 
   * @param originalBufferedImage the image to be scaled.
   * @param maxHeight the maximum height of the image.
   * @return the <code>originalBufferedImage</code> scaled to <code>maxHeight</code> or the original image, if the image height isn't larger then <code>maxHeight</code>.
   */
  public static BufferedImage scaleImageToMaxHeight(BufferedImage originalBufferedImage, int maxHeight) {
    int currentHeight = originalBufferedImage.getHeight();
    if (currentHeight <= maxHeight) {
      return originalBufferedImage;
    }
    int currentWidth = originalBufferedImage.getWidth();
    
    int width;
    int height = maxHeight;
    double scaleFactor = (double) maxHeight / currentHeight;
    width = (int) (scaleFactor * currentWidth);
    
    BufferedImage scaledBufferedImage = new BufferedImage(width, height, Transparency.OPAQUE);
    Graphics2D g2d = scaledBufferedImage.createGraphics();

    AffineTransform affineTransform = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
    g2d.drawRenderedImage(originalBufferedImage, affineTransform);
    g2d.dispose();

    return scaledBufferedImage;
  }

  /**
   * Get the size of an image in a file.
   * 
   * @param imageFile the image file
   * @return the size of the image
   * @throws IOException if a something goes wrong.
   */
  public static Dimension getImageDimensions(File imageFile) throws IOException {
    Dimension dimension = null;
    
    if (imageFile == null) {
      return dimension;
    }

    try {
      PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(imageFile);
      Integer width = photoFileMetaDataHandler.getWidth();
      Integer height = photoFileMetaDataHandler.getHeight();
      if (width != null  &&  height != null) {
        dimension = new Dimension(width, height);
      }
    } catch (ImageReadException | IOException e) {
      e.printStackTrace();
    }
    
    return dimension;
    
    
//    ImageInputStream inputStream = ImageIO.createImageInputStream(imageFile);
//
//    try {
//      final Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(inputStream);
//      while (imageReaders.hasNext()) {
//        ImageReader imageReader = imageReaders.next();
//        try {
//          imageReader.setInput(inputStream);
//          return new Dimension(imageReader.getWidth(0), imageReader.getHeight(0));
//        } finally {
//          imageReader.dispose();
//        }
//      }
//    } finally {
//      if (inputStream != null) {
//        inputStream.close();
//      }
//    }
//
//    return null;
  }
  
  public static Canvas createImageOnCanvas(String imageFilename, Integer maxWidth, Integer maxHeight) {
    Integer width = null;
    Integer height = null;
    int orientation = TiffTagConstants.ORIENTATION_VALUE_HORIZONTAL_NORMAL; // default orientation, used if there is no 'Orientation' in the meta data.
    
    try {
      PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(new File(imageFilename));
      width = photoFileMetaDataHandler.getWidth();
      height = photoFileMetaDataHandler.getHeight();
      String orientationText = photoFileMetaDataHandler.getTiffItemValue(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT, "Orientation");
      if (orientationText != null) {
        orientation = Integer.valueOf(orientationText);
      }
    } catch (ImageReadException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    javafx.scene.image.Image image = null;
    if ((width != null  &&  width > maxWidth)  ||  (height != null  &&  height > maxHeight)) {
      image = new javafx.scene.image.Image("file:" + imageFilename, maxWidth, maxHeight, true, true);
    } else {
      image = new javafx.scene.image.Image("file:" + imageFilename);
    }
    
    Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
    GraphicsContext gc = canvas.getGraphicsContext2D();
    
    // Rotate image based on the 'Orientation'.
    double degrees = 0;
    switch (orientation) {
    case TiffTagConstants.ORIENTATION_VALUE_HORIZONTAL_NORMAL:
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL:
      degrees = 0;
      break;
      
    case TiffTagConstants.ORIENTATION_VALUE_ROTATE_90_CW:
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_90_CW:
      degrees = 90;
      break;      
      
    case TiffTagConstants.ORIENTATION_VALUE_ROTATE_180:
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_VERTICAL:
      degrees = 180;
      break;
      
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_270_CW:
    case TiffTagConstants.ORIENTATION_VALUE_ROTATE_270_CW:
      degrees = 270;
      break;
    }
    
    if (degrees != 0) {
      gc.save();
      gc.rotate(degrees);
      gc.drawImage(image, 0, 0);
      gc.restore();
    } else {
      gc.drawImage(image, 0, 0);
    }
    
    return canvas;
  }
  
  public static ImageView createImageViewFromFile(String filename) {
    javafx.scene.image.Image image = new javafx.scene.image.Image("file:" + filename);
    ImageView imageView = new ImageView(image);
    
    int orientation = TiffTagConstants.ORIENTATION_VALUE_HORIZONTAL_NORMAL; // default orientation, used if there is no 'Orientation' in the meta data.
    
    try {
      PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(new File(filename));
      String orientationText = photoFileMetaDataHandler.getTiffItemValue(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT, "Orientation");
      if (orientationText != null) {
        orientation = Integer.valueOf(orientationText);
      }
    } catch (ImageReadException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    // Rotate image based on the 'Orientation'.
    switch (orientation) {
    case TiffTagConstants.ORIENTATION_VALUE_HORIZONTAL_NORMAL:
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL:
      imageView.setRotate(0);
      break;
      
    case TiffTagConstants.ORIENTATION_VALUE_ROTATE_90_CW:
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_90_CW:
      imageView.setRotate(90);
      break;      
      
    case TiffTagConstants.ORIENTATION_VALUE_ROTATE_180:
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_VERTICAL:
      imageView.setRotate(180);
      break;
      
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_270_CW:
    case TiffTagConstants.ORIENTATION_VALUE_ROTATE_270_CW:
      imageView.setRotate(270);
      break;
    }
    
    return imageView;
  }
  
  public static void main(String[] args) {// scaleImageToMaxHeightWithBorderAdded
    File sourceFile = new File("target/classes/goedegep/util/img/IMG_2588.jpg");
    try {
      Dimension dimension = getImageDimensions(sourceFile);
      System.out.println("Dimension: " + dimension.getWidth() + ", " + dimension.getHeight());
    } catch (IOException e) {
      System.out.println("Unable to get Dimension: " + e.toString());
    }
  }
}
