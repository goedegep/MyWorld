package goedegep.jfx.img;

import java.util.logging.Logger;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class provides utility methods for an {@link ImageView}.
 */
public class ImageViewUtil {
  private final static Logger LOGGER = Logger.getLogger(ImageViewUtil.class.getName());
  
  
  /**
   * Constructor
   * <p>
   * Private constructor because this is a utility class.
   */
  private ImageViewUtil() {
    
  }
  
  /**
   * Calculate the scale factor between an Image and the Region (specified by the Regions width and height) in which it will be displayed (via an ImageView).
   * <p>
   * If the Image is larger than the Region, the factor is less than 1.
   * The factor is such that the largest size (horizontal or vertical) will exactly fit the Region and the other size may be smaller than the Region.<br/>
   * Instead of passing the Region, its width and height are passed as parameters. This is because the size of a region is only available after layouting.
   * So you should only use this method if you known what the width and height of the Region will be.
   * 
   * @param image the Image for which the factor is to be calculated.
   * @param width the width of the Region in which the <code>image</code> shall fit.
   * @param height the height of the Region in which the <code>image</code> shall fit.
   * @return the scale factor to be applied to the <code>image</code> for the best fit in the <code>region</code>.
   */
  public static double calculateImageToViewScaleFactor(ImageView imageView, double width, double height) {
    double horizontalImageScreenRatio =  width / imageView.getImage().getWidth();
    double verticalImageScreenRatio =  height / imageView.getImage().getHeight();
    
    double imageToViewScaleFactor = Math.min(horizontalImageScreenRatio, verticalImageScreenRatio);
    
    return imageToViewScaleFactor;
  }
  
  /**
   * Calculate the width and height of a viewport rectangle for a specific zoom level.
   * <p>
   * The size is related to the {@code zoomLevel}.
   * 
   * @param imageView the ImageView
   * @param zoomLevel the zoom level
   * 
   */
  public static Dimension2D calculateViewportDimensions(ImageView imageView, double zoomLevel) {
    double viewportRectangleWidth = imageView.getImage().getWidth() / zoomLevel;
    double viewportRectangleHeight = imageView.getImage().getHeight() / zoomLevel;
    
    return new Dimension2D(viewportRectangleWidth, viewportRectangleHeight);
  }
  
  /**
   * Calculate the dimensions of an ImageView to show an Image with a specific scale factor.
   * <p>
   * The width and height of the ImageView are the width and height of the Image, multiplied by the scale factor.
   * 
   * @param image the Image to be displayed in an ImageView
   * @param imageToViewScaleFactor the scale factor that will be applied to the Image.
   * @return the dimensions for an ImageView to show the <code>image</code> with the <code>imageToViewScaleFactor</code>.
   */
  public static Dimension2D calculateImageViewDimensions(ImageView imageView, double imageToViewScaleFactor) {
    // ImageView width and Image width are always in the same direction (also in case of rotation), as the complete ImageView is rotated.
    double imageViewWidth =  imageView.getImage().getWidth() * imageToViewScaleFactor;
    double imageViewHeight =  imageView.getImage().getHeight() * imageToViewScaleFactor;
    Dimension2D imageViewDimensions = new Dimension2D(imageViewWidth, imageViewHeight);
    
    return imageViewDimensions;
  }
  
  /**
   * Update the offsets of a viewport, so the imageView is completely filled.
   * 
   * @param viewport The viewport to be checked.
   * @return the {@code viewport} if it didn't have to be updated, or a new {@code Rectangle2D} in case the offsets had to be adapted.
   */
  public static Rectangle2D updateOffsetsToAvoidWhiteSpace(ImageView imageView, Rectangle2D viewport) {
    boolean updated = false;
    
    double viewportRectangleXOffset = viewport.getMinX();
    double viewportRectangleYOffset = viewport.getMinY();
    double viewportRectangleWidth = viewport.getWidth();
    double viewportRectangleHeight = viewport.getHeight();
    
    double rotation = imageView.rotateProperty().getValue();
    
    // If any part of the viewport falls outside of the Image, this will lead to a black area on the screen.
    if (rotation == 90.0  ||  rotation == -90.0) {
      if (viewportRectangleXOffset < 0) {
        viewportRectangleXOffset = 0;
        updated = true;
        LOGGER.info("Shifting up, viewportRectangleXOffset set to: " + viewportRectangleXOffset);
      }
      if (viewportRectangleXOffset + viewportRectangleWidth > imageView.getImage().getWidth()) {
        viewportRectangleXOffset = imageView.getImage().getWidth() - viewportRectangleWidth;
        updated = true;
        LOGGER.info("Shifting down, xviewportRectangleXOffset set to: " + viewportRectangleXOffset);
      }
      if (viewportRectangleYOffset < 0) {
        viewportRectangleYOffset = 0;
        updated = true;
        LOGGER.info("Shifting left, viewportRectangleYOffset set to: " + viewportRectangleYOffset);
      }
      if (viewportRectangleYOffset + viewportRectangleHeight > imageView.getImage().getHeight()) {
        viewportRectangleYOffset = imageView.getImage().getHeight() - viewportRectangleHeight;
        updated = true;
        LOGGER.info("Shifting right, viewportRectangleYOffset set to: " + viewportRectangleYOffset);
      }
    } else {
      if (viewportRectangleXOffset < 0) {
        viewportRectangleXOffset = 0;
        updated = true;
        LOGGER.info("Shifting left, viewportRectangleXOffset set to: " + viewportRectangleXOffset);
      }
      if (viewportRectangleXOffset + viewportRectangleWidth > imageView.getImage().getWidth()) {
        viewportRectangleXOffset = imageView.getImage().getWidth() - viewportRectangleWidth;
        updated = true;
        LOGGER.info("Shifting right, xviewportRectangleXOffset set to: " + viewportRectangleXOffset);
      }
      if (viewportRectangleYOffset < 0) {
        viewportRectangleYOffset = 0;
        updated = true;
        LOGGER.info("Shifting up, viewportRectangleYOffset set to: " + viewportRectangleYOffset);
      }
      if (viewportRectangleYOffset + viewportRectangleHeight > imageView.getImage().getHeight()) {
        viewportRectangleYOffset = imageView.getImage().getHeight() - viewportRectangleHeight;
        updated = true;
        LOGGER.info("Shifting down, viewportRectangleYOffset set to: " + viewportRectangleYOffset);
      }
    }
    
    
    
    if (updated) {
      return new Rectangle2D(viewportRectangleXOffset, viewportRectangleYOffset, viewportRectangleWidth, viewportRectangleHeight);
    } else {
      return viewport;
    }
  }
  
  /**
   * Translate the coordinates within an Image, shown in an ImageView, to the coordinates within the ImageView.
   * <p>
   * If the ImageView has a Viewport, this is taken into account.
   * 
   * @param imageView the ImageView.
   * @param imageX the X-coordinate within the Image shown in the {@code imageView}.
   * @param imageY the Y-coordinate within the Image shown in the {@code imageView}.
   * @return the coordinates within the {@code imageView}.
   */
  public static Point2D imageCoordinatesToImageViewCoordinates(ImageView imageView, double imageX, double imageY) {
    Rectangle2D viewportRectangle = imageView.getViewport();
    Image image = imageView.getImage();
    
    if (viewportRectangle == null) {
      viewportRectangle = new Rectangle2D(0, 0, image.getWidth(), image.getHeight());
    }

    double zoomFactor = image.getWidth() / (viewportRectangle.getMaxX() - viewportRectangle.getMinX());
    double imageToViewScaleFactor = imageView.getFitWidth() / getImageViewImageHorizontalSize(imageView);
    double imageViewX = (imageX - viewportRectangle.getMinX()) * imageToViewScaleFactor * zoomFactor;
    double imageViewY = (imageY - viewportRectangle.getMinY()) * imageToViewScaleFactor * zoomFactor;
    
    if ((imageViewX < 0)  ||  (imageViewX > imageView.getFitWidth() - 1)  ||
        (imageViewY < 0)  ||  (imageViewY > imageView.getFitHeight() - 1)) {
      System.out.println("<= null");
      return null;
    } else {
      return new Point2D(imageViewX, imageViewY);
    }
  }
  
  /**
   * Translate the coordinates within an ImageView to the coordinates within the Image shown in the ImageView.
   * <p>
   * If the imageView has a ViewPort, this is also taken into account.
   * 
   * @param imageView the ImageView.
   * @param imageViewX the X-coordinate within the {@code imageView}.
   * @param imageViewY the Y-coordinate within the {@code imageView}.
   * @return the coordinates within the Image shown in the {@code imageView}.
   */
  public static Point2D imageViewCoordinatesToImageCoordinates(ImageView imageView, double imageViewX, double imageViewY) {
    Rectangle2D viewportRectangle = imageView.getViewport();
    Image image = imageView.getImage();
    
    if (viewportRectangle == null) {
      viewportRectangle = new Rectangle2D(0, 0, image.getWidth(), image.getHeight());
    }
    
    double imageX;
    double imageY;
    double viewPortZoomFactor = image.getWidth() / (viewportRectangle.getMaxX() - viewportRectangle.getMinX());
    double imageToViewScaleFactor = imageView.getFitWidth() / image.getWidth();
    
    imageX = viewportRectangle.getMinX() + imageViewX / imageToViewScaleFactor / viewPortZoomFactor;
    imageY = viewportRectangle.getMinY() + imageViewY / imageToViewScaleFactor / viewPortZoomFactor;
    
    imageX = viewportRectangle.getMinX() + imageViewX / imageToViewScaleFactor / viewPortZoomFactor;
    imageY = viewportRectangle.getMinY() + imageViewY / imageToViewScaleFactor / viewPortZoomFactor;
    
    return new Point2D(imageX, imageY);
  }

  
  /**
   * Get the horizontal size of the Image shown in an ImageView.
   * <p>
   * If an image is rotated by 90 or 270 degrees, the width is actually the height.<br/>
   * Note: this method only works for rotations of 0, 90, 180 and 270 degrees.
   * For rotations of 90 and 270 degrees, the horizontal size is the height of the Image,
   * else it is the width of the image.
   * 
   * @param imageView the ImageView
   * @return The horizontal size of the Image shown in the {@code imageView}.
   */
  public static double getImageViewImageHorizontalSize(ImageView imageView) {
    double rotation = imageView.rotateProperty().getValue();
    
    if (rotation == 90.0  ||  rotation == 270.0) {
      return imageView.getImage().getHeight();
    } else {
      return imageView.getImage().getWidth();
    }
  }
  
  /**
   * Get the vertical size of the Image shown in an ImageView.
   * <p>
   * If an image is rotated by 90 or 270 degrees, the height is actually the width.<br/>
   * Note: this method only works for rotations of 0, 90, 180 and 270 degrees.
   * For rotations of 90 and 270 degrees, the vertical size is the width of the Image,
   * else it is the height of the image.
   * 
   * @param imageView the ImageView
   * @return The vertical size of the Image shown in the {@code imageView}.
   */
  public static double getImageViewImageVerticalSize(ImageView imageView) {
    double rotation = imageView.rotateProperty().getValue();
    
    if (rotation == 90.0  ||  rotation == -90.0) {
      return imageView.getImage().getWidth();
    } else {
      return imageView.getImage().getHeight();
    }
    
  }
}
