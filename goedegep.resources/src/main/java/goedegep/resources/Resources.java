package goedegep.resources;

import javafx.scene.image.Image;

/**
 * This class provides general purpose images.
 *
 */
public class Resources {
  private static Image cameraBlackImage = null;
  private static Image cameraBlueImage = null;
  private static Image cameraGrayImage = null;

  private static Image blueLocationFlagImage = null;
  private static Image yellowLocationFlagImage = null;
  private static Image gpxImage = null;
  
  
  /**
   * Get a black camera image.
   * 
   * @return a black camera image.
   */
  public static Image getCameraBlackIcon() {
    if (cameraBlackImage == null) {
      cameraBlackImage = new Image(Resources.class.getResourceAsStream("CameraBlack.png"), 12, 12, true, true);
    }
    return cameraBlackImage;
  }
  
  /**
   * Get a blue camera image.
   * 
   * @return a blue camera image.
   */
  public static Image getCameraBlueIcon() {
    if (cameraBlueImage == null) {
      cameraBlueImage = new Image(Resources.class.getResourceAsStream("CameraBlue.png"), 12, 12, true, true);
    }
    return cameraBlueImage;
  }
  
  /**
   * Get a gray camera image.
   * 
   * @return a gray camera image.
   */
  public static Image getCameraGrayIcon() {
    if (cameraGrayImage == null) {
      cameraGrayImage = new Image(Resources.class.getResourceAsStream("CameraGray.png"), 12, 12, true, true);
    }
    return cameraGrayImage;
  }

  /**
   * Get an image which represents a GPX track or file.
   * 
   * @return a GPX track or file image.
   */
  public static Image getGpxIcon() {
    if (gpxImage == null) {
      gpxImage = new Image(Resources.class.getResourceAsStream("Gpx.png"));
    }
    return gpxImage;
  }

  /**
   * Get a blue location flag image.
   * 
   * @return a blue location flag image.
   */
  public static Image getBlueLocationFlagImage() {
    if (blueLocationFlagImage == null) {
      blueLocationFlagImage = new Image(Resources.class.getResourceAsStream("BlueLocationFlag.png"));
    }
    return blueLocationFlagImage;
  }

  /**
   * Get a yellow location flag image.
   * 
   * @return a yellow location flag image.
   */
  public static Image getYellowLocationFlagImage() {
    if (yellowLocationFlagImage == null) {
      yellowLocationFlagImage = new Image(Resources.class.getResourceAsStream("YellowLocationFlag.png"));
    }
    return yellowLocationFlagImage;
  }

}
