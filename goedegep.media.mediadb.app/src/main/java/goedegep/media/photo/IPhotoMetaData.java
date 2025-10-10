package goedegep.media.photo;

import java.time.LocalDateTime;

import goedegep.geo.WGS84Coordinates;
import javafx.beans.value.ObservableValue;

/**
 * This interface defines the meta data for a photo.
 *
 */
public interface IPhotoMetaData extends ObservableValue<IPhotoMetaData> {
  
  /**
   * Get the full pathname of the photo file to which this information applies.
   * 
   * @return the full pathname of the photo file to which this information applies.
   */
  String getFileName();
  
  /**
   * Set the full pathname of the photo file to which this information applies.
   * 
   * @param filename the full pathname of the photo file to which this information applies.
   */
  void setFileName(String filename);
 
  /**
   * Get the title of the photo.
   * 
   * @return the title of the photo.
   */
  String getTitle();
  
  /**
   * Set the title of the photo.
   * 
   * @param title
   */
  void setTitle(String title);

  /**
   * Get the coordinates of the location where the photo was taken.
   * 
   * @return the coordinates of the location where the photo was taken.
   */
  WGS84Coordinates getCoordinates();
  
  /**
   * Set the coordinates of the location where the photo was taken.
   * 
   * @param coordinates the coordinates of the location where the photo was taken.
   */
  void setCoordinates(WGS84Coordinates coordinates);

  /**
   * Check whether the photo has inaccurate coordinates.
   * 
   * @return true if the photo has inaccurate coordinates, false otherwise.
   */
  boolean hasApproximateGPScoordinates();
  
  /**
   * Indicate whether the photo has inaccurate coordinates.
   * 
   * @param approximateGPScoordinates set to true to indicate that the photo has inaccurate coordinates.
   */
  void setApproximateGPScoordinates(boolean approximateGPScoordinates);
  
  /**
   * Get the number of degrees the photo has to be rotated to show it upright.
   * 
   * @return the number of degrees the photo has to be rotated to show it upright.
   */
  Integer getRotationAngle();
  
  /**
   * Set the number of degrees the photo has to be rotated to show it upright.
   * 
   * @param rotationAngle the number of degrees the photo has to be rotated to show it upright.
   */
  void setRotationAngle(Integer rotationAngle);
  
  /**
   * Get the time the photo was taken as recorded by the device with which it was taken.
   * 
   * @return the time the photo was taken as recorded by the device with which it was taken.
   */
  LocalDateTime getDeviceSpecificPhotoTakenTime();
  
  /**
   * Set the time the photo was taken as recorded by the device with which it was taken.
   * 
   * @param deviceSpecificPhotoTakenTime the time the photo was taken as recorded by the device with which it was taken.
   */
  void setDeviceSpecificPhotoTakenTime(LocalDateTime deviceSpecificPhotoTakenTime);
  
  /**
   * Get the date/time the photo file was last modified.
   * 
   * @return the date/time the photo file was last modified.
   */
  LocalDateTime getModificationDateTime();
  
  /**
   * Set the date/time the photo file was last modified.
   * 
   * @param modificationDateTime the date/time the photo file was last modified.
   */
  void setModificationDateTime(LocalDateTime modificationDateTime);

//  /**
//   * Get the data/time to be used for sorting.
//   * <p>
//   * In the future this may be extended with e.g. an offset, or use the modification date/time if the device time isn't available.
//   * 
//   * @return the date/time to be used for sorting, or null if this isn't available.
//   */
//  LocalDateTime getSortingDateTime();

}
