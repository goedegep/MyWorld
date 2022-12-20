package goedegep.media.photo;

import java.time.LocalDateTime;

import goedegep.geo.WGS84Coordinates;
import javafx.beans.value.ObservableValue;

public interface IPhotoMetaData extends ObservableValue<IPhotoMetaData> {
  public String getFileName();
  
  public void setFileName(String filename);
 
  public String getTitle();
  
  public void setTitle(String title);

  public WGS84Coordinates getCoordinates();
  
  public void setCoordinates(WGS84Coordinates coordinates);

  public boolean isApproximateGPScoordinates();
  
  public void setApproximateGPScoordinates(boolean approximateGPScoordinates);
  
  public LocalDateTime getDeviceSpecificPhotoTakenTime();
  
  public void setDeviceSpecificPhotoTakenTime(LocalDateTime deviceSpecificPhotoTakenTime);
  
  public LocalDateTime getModificationDateTime();
  
  public void setModificationDateTime(LocalDateTime modificationDateTime);

  /**
   * Get the data/time to be used for sorting.
   * <p>
   * In the future this may be extended with e.g. an offset, or use the modification date/time if the device time isn't available.
   * 
   * @return the date/time to be used for sorting, or null if this isn't available.
   */
  public LocalDateTime getSortingDateTime();

}
