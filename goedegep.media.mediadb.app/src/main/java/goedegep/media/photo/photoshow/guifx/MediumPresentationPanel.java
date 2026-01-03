package goedegep.media.photo.photoshow.guifx;

import goedegep.util.datetime.FlexDate;
import javafx.geometry.Point2D;
import javafx.scene.Parent;

/**
 * A presentation panel for showing media.
 */
public interface MediumPresentationPanel {
  /**
   * Get the root node of this presentation panel.
   * <p>
   * The value returned will be used to set a root node of a Scene.
   * 
   * @return the root node of this presentation panel.
   */
  Parent getRootNode();

  /**
   * Show the medium with the specified file name in this presentation panel.
   * 
   * @param fileName the file name of the medium to show.
   * @param title an optional title which will be shown on the first medium, until any key is pressed.
   * @param date an optional date which will be shown on the first medium (below the title), until any key is pressed.
   */
  void showMedium(String fileName, String title, FlexDate flexDate);

  /**
   * Check whether this medium shows a title.
   * 
   * @return true if this medium shows a title; false otherwise.
   */
  boolean hasTitle();
  
  /**
   * Remove the title from this medium.
   */
  void removeTitle();
  
  
  /**
   * Zoom in on the current cursor position if the cursor is shown, else zoom in on the center of the photo.
   * <p>
   * If after zooming there would be black space around the image, the image is shifted to avoid this.
   * 
   * @param zoomLevelIncrease the amount to increase the zoom level.
   * @param cursorOn true if the cursor is shown; false otherwise.
   * @return the new location where the cursor shall be moved to.
   */
  Point2D zoomInOnCursorPosition(double zoomLevelIncrease, boolean cursorOn);
  
  /**
   * Zoom out from the center of the screen.
   * 
   * @param zoomLevelDecrease the amount to zoom out.
   */
  Point2D zoomOutFromCenter(double zoomLevelDecrease);
  
  /**
   * Move the image a specified percentage of its size to the right.
   * <p>
   * The image will be moved by the specified amount, unless it reaches the border.<br/>
   * 
   * @param percentage the amount to move to the right.
   */
  void moveImageRight(double percentage);
  
  /**
   * Move the image a specified percentage of its size to the left.
   * <p>
   * The image will be moved by the specified amount, unless it reaches the border.<br/>
   * 
   * @param percentage the amount to move to the left.
   */
  void moveImageLeft(double percentage);
  
  /**
   * Move the image a specified percentage of its size upwards.
   * <p>
   * The image will be moved by the specified amount, unless it reaches the border.<br/>
   * 
   * @param percentage the amount to move upwards.
   */
  void moveImageUp(double percentage);
  
  /**
   * Move the image a specified percentage of its size downwards.
   * <p>
   * The image will be moved by the specified amount, unless it reaches the border.<br/>
   * 
   * @param percentage the amount to move downwards.
   */
  void moveImageDown(double percentage);
  
  /**
   * Move the image a specified number of pixels to the right.
   * <p>
   * The image will be moved by the specified amount, unless it reaches the border.<br/>
   * 
   * @param pixels the number of pixels to move to the right.
   */
  void moveImageRight(int pixels);
  
  /**
   * Move the image a specified number of pixels to the left.
   * <p>
   * The image will be moved by the specified amount, unless it reaches the border.<br/>
   * 
   * @param pixels the number of pixels to move to the left.
   */
  void moveImageLeft(int pixels);
  
  /**
   * Move the image a specified number of pixels upwards.
   * <p>
   * The image will be moved by the specified amount, unless it reaches the border.<br/>
   * 
   * @param pixels the number of pixels to move upwards.
   */
  void moveImageUp(int pixels);
  
  /**
   * Move the image a specified number of pixels downwards.
   * <p>
   * The image will be moved by the specified amount, unless it reaches the border.<br/>
   * 
   * @param pixels the number of pixels to move downwards.
   */
  void moveImageDown(int pixels);
}
