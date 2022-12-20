package goedegep.media.photo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import goedegep.geo.WGS84Coordinates;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * This class stores meta data for a photo.
 * <p>
 * The meta data consists of:
 * <ul>
 * <li>
 * {@link #fileName}
 * </li>
 * </ul>
 */
public class PhotoMetaData implements Serializable, IPhotoMetaData {
  private static final long serialVersionUID = 3907695290202971996L;
  
  /**
   * The full pathname of the photo file
   */
  private String fileName;
  
  
  private String title;
  private WGS84Coordinates coordinates;
  private boolean approximateGPScoordinates;
  private LocalDateTime deviceSpecificPhotoTakenTime;
  private LocalDateTime modificationDateTime;
  
  private List<ChangeListener<? super IPhotoMetaData>> changeListeners = new ArrayList<>();
  
  public PhotoMetaData() {
    this(null, null, null);
  }

  public PhotoMetaData(String fileName, String title, WGS84Coordinates coordinates) {
    super();
    this.fileName = fileName;
    this.title = title;
    this.coordinates = coordinates;
  }
  
  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
    notifyChangeListeners();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
    notifyChangeListeners();
  }

  public WGS84Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(WGS84Coordinates coordinates) {
    this.coordinates = coordinates;
    notifyChangeListeners();
  }

  public boolean isApproximateGPScoordinates() {
    return approximateGPScoordinates;
  }

  public void setApproximateGPScoordinates(boolean approximateGPScoordinates) {
    this.approximateGPScoordinates = approximateGPScoordinates;
    notifyChangeListeners();
  }

  public LocalDateTime getDeviceSpecificPhotoTakenTime() {
    return deviceSpecificPhotoTakenTime;
  }

  public void setDeviceSpecificPhotoTakenTime(LocalDateTime deviceSpecificPhotoTakenTime) {
    this.deviceSpecificPhotoTakenTime = deviceSpecificPhotoTakenTime;
    notifyChangeListeners();
  }

  public LocalDateTime getModificationDateTime() {
    return modificationDateTime;
  }

  public void setModificationDateTime(LocalDateTime modificationDateTime) {
    this.modificationDateTime = modificationDateTime;
    notifyChangeListeners();
  }

  /**
   * Get the data/time to be used for sorting.
   * <p>
   * In the future this may be extended with e.g. an offset, or use the modification date/time if the device time isn't available.
   * 
   * @return the date/time to be used for sorting, or null if this isn't available.
   */
  public LocalDateTime getSortingDateTime() {
    return getDeviceSpecificPhotoTakenTime();
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }
  
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(fileName).append(", ").append(title).append(", ");
    buf.append(coordinates != null ? coordinates.toString() : "(null)").append(", ").append(modificationDateTime);
    
    return buf.toString();
  }

  /*
   * Partial implementation of ObservableValue (only ChangeListeners are supported).
   */
  
  @Override
  public void addListener(InvalidationListener listener) {
    throw new UnsupportedOperationException("Only change listeners are supported");
  }

  @Override
  public void removeListener(InvalidationListener listener) {
    throw new UnsupportedOperationException("Only change listeners are supported");
  }

  @Override
  public void addListener(ChangeListener<? super IPhotoMetaData> changeListener) {
    Objects.requireNonNull(changeListener, "changeListener may not be null");
    changeListeners.add(changeListener);
  }

  @Override
  public void removeListener(ChangeListener<? super IPhotoMetaData> changeListener) {
    changeListeners.remove(changeListener);
  }

  protected void notifyChangeListeners() {
    for (ChangeListener<? super IPhotoMetaData> changeListener: changeListeners) {
      changeListener.changed(this, null, this);
    }
  }

  @Override
  public PhotoMetaData getValue() {
    return this;
  }
}
