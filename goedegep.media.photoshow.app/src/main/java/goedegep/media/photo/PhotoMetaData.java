package goedegep.media.photo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import goedegep.geo.WGS84Coordinates;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;

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
   * The full pathname of the photo file to which this information applies
   */
  private String fileName = null;
  
  /**
   * Title of the photo.
   */
  private String title = null;
  
  /**
   * Coordinates of the location where the photo was taken.
   */
  private WGS84Coordinates coordinates = null;
  
  /**
   * Indication of the accuracy of the {@code coordinates}.
   * If set, the accuracy of the {@code coordinates} is low.
   */
  private boolean approximateGPScoordinates = false;
  
  /**
   * The number of degrees the photo has to be rotated to show it upright.
   */
  Integer rotationAngle = null;
  
  /**
   * The time the photo was taken as recorded by the device with which it was taken.
   * <p>
   * If you take pictures in a different time zone, without adjusting the time of
   * your camera, this may be a strange value.
   */
  private LocalDateTime deviceSpecificPhotoTakenTime;
  
  /**
   * The date/time the photo file was last modified.
   * <p>
   * If the photo was edited, or e.g. downloaded, this time will not be the time
   * that the photo was taken.
   */
  private LocalDateTime modificationDateTime;
  
  private List<ChangeListener<? super IPhotoMetaData>> changeListeners = new ArrayList<>();
  
  /**
   * Constructor for empty PhotoMetaData.
   */
  public PhotoMetaData() {
    this(null, null, null);
  }

  /**
   * Constructor for setting the basic properties.
   * 
   * @param fileName the full pathname of the photo file to which this information applies.
   * @param title the title of the photo
   * @param coordinates the coordinates of the location where the photo was taken.
   */
  public PhotoMetaData(String fileName, String title, WGS84Coordinates coordinates) {
    super();
    this.fileName = fileName;
    this.title = title;
    this.coordinates = coordinates;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getFileName() {
    return fileName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFileName(String fileName) {
    this.fileName = fileName;
    notifyChangeListeners();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {
    this.title = title;
    notifyChangeListeners();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public WGS84Coordinates getCoordinates() {
    return coordinates;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCoordinates(WGS84Coordinates coordinates) {
    this.coordinates = coordinates;
    notifyChangeListeners();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasApproximateGPScoordinates() {
    return approximateGPScoordinates;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setApproximateGPScoordinates(boolean approximateGPScoordinates) {
    this.approximateGPScoordinates = approximateGPScoordinates;
    notifyChangeListeners();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getRotationAngle() {
    return rotationAngle;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setRotationAngle(Integer rotationAngle) {
    this.rotationAngle = rotationAngle;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public LocalDateTime getDeviceSpecificPhotoTakenTime() {
    return deviceSpecificPhotoTakenTime;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDeviceSpecificPhotoTakenTime(LocalDateTime deviceSpecificPhotoTakenTime) {
    this.deviceSpecificPhotoTakenTime = deviceSpecificPhotoTakenTime;
    notifyChangeListeners();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LocalDateTime getModificationDateTime() {
    return modificationDateTime;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModificationDateTime(LocalDateTime modificationDateTime) {
    this.modificationDateTime = modificationDateTime;
    notifyChangeListeners();
  }

//  /**
//   * Get the data/time to be used for sorting.
//   * <p>
//   * In the future this may be extended with e.g. an offset, or use the modification date/time if the device time isn't available.
//   * 
//   * @return the date/time to be used for sorting, or null if this isn't available.
//   */
//  public LocalDateTime getSortingDateTime() {
//    return getDeviceSpecificPhotoTakenTime();
//  }

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
