package goedegep.media.photo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import goedegep.geo.WGS84Coordinates;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class PhotoMetaData implements Serializable, ObservableValue<PhotoMetaData> {
  private static final long serialVersionUID = 3907695290202971996L;
  
  private String fileName;
  private String title;
  private WGS84Coordinates coordinates;
  private boolean approximateGPScoordinates;
  private LocalDateTime deviceSpecificPhotoTakenTime;
  private LocalDateTime modificationDateTime;
  
  private List<ChangeListener<? super PhotoMetaData>> changeListeners = new ArrayList<>();
  
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
  public void addListener(ChangeListener<? super PhotoMetaData> changeListener) {
    changeListeners.add(changeListener);
  }

  @Override
  public void removeListener(ChangeListener<? super PhotoMetaData> changeListener) {
    changeListeners.remove(changeListener);
  }

  protected void notifyChangeListeners() {
    for (ChangeListener<? super PhotoMetaData> changeListener: changeListeners) {
      changeListener.changed(this, null, this);
    }
  }

  @Override
  public PhotoMetaData getValue() {
    return this;
  }
}
