package goedegep.media.fotoshow.app.guifx;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.logging.Logger;

import goedegep.media.photo.PhotoMetaDataWithImage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class PhotoInfo extends PhotoMetaDataWithImage implements Serializable {
  private static final long serialVersionUID = 8042429357758147556L;
  private final static Logger LOGGER = Logger.getLogger(PhotoInfo.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

//  private List<ChangeListener<? super PhotoInfo>> changeListeners = new ArrayList<>();
  
  private LocalDateTime sortingDateTime;
  private BooleanProperty selectedForTheShow = new SimpleBooleanProperty(false);
  
//  public String getFileName() {
//    return fileName;
//  }
//
//  public void setFileName(String fileName) {
//    this.fileName = fileName;
//  }
//
//  public String getTitle() {
//    return title;
//  }
//
//  public void setTitle(String title) {
//    this.title = title;
//    notifyChangeListeners();
//  }
//
//  public Image getImage() {
//    return image;
//  }
//
//  public void setImage(Image image) {
//    this.image = image;
//    notifyChangeListeners();
//  }
//  
//  /**
//   * Get the time the photo was taken, according to the time of the device on which it was taken.
//   * 
//   * @return the time at which the photo was taken
//   */
//  public LocalDateTime getDeviceSpecificPhotoTakenTime() {
//    return deviceSpecificPhotoTakenTime;
//  }
//
//  /**
//   * Set the time the photo was taken, according to the time of the device on which it was taken.
//   * 
//   * @param deviceSpecificPhotoTakenTime the time at which the photo was taken
//   */
//  public void setDeviceSpecificPhotoTakenTime(LocalDateTime deviceSpecificPhotoTakenTime) {
//    this.deviceSpecificPhotoTakenTime = deviceSpecificPhotoTakenTime;
//    notifyChangeListeners();
//  }
//
//  /**
//   * Get the time at which the photo was last modified (e.g. edited with PhotoShop).
//   *   
//   * @return the time at which the photo was last modified, or null is the photo hasn't been modified.
//   */
//  public LocalDateTime getModificationDateTime() {
//    return modificationDateTime;
//  }
//
//  /**
//   * Set the time at which the photo was last modified (e.g. edited with PhotoShop).
//   * 
//   * @param modificationDateTime the time at which the photo was last modified, or null is the photo hasn't been modified
//   */
//  public void setModificationDateTime(LocalDateTime modificationDateTime) {
//    this.modificationDateTime = modificationDateTime;
//    notifyChangeListeners();
//  }

  /**
   * Get the time to be used for sorting. This can be the time set via {@code setSortingDateTime}, or the time set via
   * {@code setDeviceSpecificPhotoTakenTime} if setSortingDateTime isn't called, or it's called with null as value.
   * 
   * @return the time to be used for sorting, or null if no {@code sortingDateTime} and no {@code deviceSpecificPhotoTakenTime) is available.
   */
  public LocalDateTime getSortingDateTime() {
    if (sortingDateTime != null) {
      LOGGER.info("<= sortingDateTime=" + (sortingDateTime != null ? sortingDateTime.toString() : "null"));
      return sortingDateTime;
    } else {
      LOGGER.info("<= deviceSpecificPhotoTakenTime=" + (getDeviceSpecificPhotoTakenTime() != null ? getDeviceSpecificPhotoTakenTime().toString() : "null"));
      return getDeviceSpecificPhotoTakenTime();
    }
  }

  /**
   * Set the time to be used for sorting. This time only has to be set if the sorting time differs from the deviceSpecificPhotoTakenTime.
   * 
   * @param sortingDateTime the time to be used for sorting
   */
  public void setSortingDateTime(LocalDateTime sortingDateTime) {
    this.sortingDateTime = sortingDateTime;
    notifyChangeListeners();
  }

  public boolean isSelectedForTheShow() {
    return selectedForTheShow.getValue();
  }

  public void setSelectedForTheShow(boolean selectedForTheShow) {
    this.selectedForTheShow.setValue(selectedForTheShow);
    notifyChangeListeners();
  }

//  public WGS84Coordinates getCoordinates() {
//    return coordinates;
//  }
//
//  public void setCoordinates(WGS84Coordinates coordinates) {
//    this.coordinates = coordinates;
//    notifyChangeListeners();
//  }  
//
//  public boolean isApproximateGPScoordinates() {
//    return approximateGPScoordinates;
//  }
//
//  public void setApproximateGPScoordinates(boolean approximateGPScoordinates) {
//    this.approximateGPScoordinates = approximateGPScoordinates;
//    notifyChangeListeners();
//  }

  public boolean toggleSelectedForTheShow() {
    selectedForTheShow.setValue(!selectedForTheShow.getValue());
    notifyChangeListeners();
    
    return selectedForTheShow.getValue();
  }
  
  public BooleanProperty selectedForTheShowProperty() {
    return selectedForTheShow;
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("fileName: ").append(getFileName()).append(NEWLINE);
    
    buf.append("image: ");
    if (getImage() == null) {
      buf.append("not ");
    }
    buf.append("set").append(NEWLINE);
    
    buf.append("deviceSpecificPhotoTakenTime: ").append(getDeviceSpecificPhotoTakenTime() != null ? DTF.format(getDeviceSpecificPhotoTakenTime()) : "--").append(NEWLINE);
    buf.append("modificationDateTime: ").append(getModificationDateTime() != null ? DTF.format(getModificationDateTime()) : "--").append(NEWLINE);
    buf.append("sortingDateTime: ").append(getSortingDateTime() != null ? DTF.format(getSortingDateTime()) : "--").append(NEWLINE);
    
    buf.append("coordinates: ");
    if (getCoordinates() != null) {
      buf.append(getCoordinates().getLatitude()).append(", ").append(getCoordinates().getLongitude());
    } else {
      buf.append("--");
    }
    buf.append("set").append(NEWLINE);
    
    buf.append("approximateGPScoordinates: ").append(isApproximateGPScoordinates()).append(NEWLINE);
    buf.append("selectedForTheShow: ").append(selectedForTheShow.getValue()).append(NEWLINE);
    
    return buf.toString();
  }
  
  public static Comparator<PhotoMetaDataWithImage> getSortingDateTimeComparator() {
    return new SortingDateTimeComparator();
  }

//  /*
//   * Partial implementation of ObservableValue (only ChangeListeners are supported).
//   */
//  
//  @Override
//  public void addListener(InvalidationListener listener) {
//    throw new UnsupportedOperationException("Only change listeners are supported");
//  }
//
//  @Override
//  public void removeListener(InvalidationListener listener) {
//    throw new UnsupportedOperationException("Only change listeners are supported");
//  }
//
//  @Override
//  public void addListener(ChangeListener<? super PhotoInfo> changeListener) {
//    changeListeners.add(changeListener);
//  }
//
//  @Override
//  public void removeListener(ChangeListener<? super PhotoInfo> changeListener) {
//    changeListeners.remove(changeListener);
//  }
//
//  private void notifyChangeListeners() {
//    for (ChangeListener<? super PhotoInfo> changeListener: changeListeners) {
//      changeListener.changed(this, null, this);
//    }
//  }

  @Override
  public PhotoInfo getValue() {
    return this;
  }
}


class SortingDateTimeComparator implements Comparator<PhotoMetaDataWithImage> {

  @Override
  public int compare(PhotoMetaDataWithImage photoInfo1, PhotoMetaDataWithImage photoInfo2) {
    LocalDateTime localDateTime1 = ((PhotoInfo) photoInfo1).getSortingDateTime();
    LocalDateTime localDateTime2 = ((PhotoInfo) photoInfo2).getSortingDateTime();
    if ((localDateTime1 == null)  ||  (localDateTime2 == null)) {
      return 0;
    } else {
      return ((PhotoInfo) photoInfo1).getSortingDateTime().compareTo(((PhotoInfo) photoInfo2).getSortingDateTime());
    }
  }
}

