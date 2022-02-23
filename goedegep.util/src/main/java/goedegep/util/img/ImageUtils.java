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

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.imaging.ImageReadException;

import goedegep.geo.dbl.WGS84Coordinates;

/**
 * This is a utility class with methods which operate on images (Image).
 *
 */
public class ImageUtils {
  
  /**
   * Private constructor, so this class cannot be instantiated.
   */
  private ImageUtils() {
    
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
    ImageInputStream inputStream = ImageIO.createImageInputStream(imageFile);

    try {
      final Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(inputStream);
      while (imageReaders.hasNext()) {
        ImageReader imageReader = imageReaders.next();
        try {
          imageReader.setInput(inputStream);
          return new Dimension(imageReader.getWidth(0), imageReader.getHeight(0));
        } finally {
          imageReader.dispose();
        }
      }
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
    }

    return null;
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
