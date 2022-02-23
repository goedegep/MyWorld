package goedegep.media.fotoshow.app.guifx;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import goedegep.geo.dbl.WGS84Coordinates;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class PhotoInfo implements ObservableValue<PhotoInfo> {
  private final static Logger LOGGER = Logger.getLogger(PhotoInfo.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  private List<ChangeListener<? super PhotoInfo>> changeListeners = new ArrayList<>();
  
  private String fileName;
  private String title;
  private Image image;
  private LocalDateTime deviceSpecificPhotoTakenTime;
  private LocalDateTime modificationDateTime;
  private LocalDateTime sortingDateTime;
  private WGS84Coordinates coordinates;
  private boolean approximateGPScoordinates;
  private BooleanProperty selectedForTheShow = new SimpleBooleanProperty(false);
  
  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
    notifyChangeListeners();
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
    notifyChangeListeners();
  }
  
  /**
   * Get the time the photo was taken, according to the time of the device on which it was taken.
   * 
   * @return the time at which the photo was taken
   */
  public LocalDateTime getDeviceSpecificPhotoTakenTime() {
    return deviceSpecificPhotoTakenTime;
  }

  /**
   * Set the time the photo was taken, according to the time of the device on which it was taken.
   * 
   * @param deviceSpecificPhotoTakenTime the time at which the photo was taken
   */
  public void setDeviceSpecificPhotoTakenTime(LocalDateTime deviceSpecificPhotoTakenTime) {
    this.deviceSpecificPhotoTakenTime = deviceSpecificPhotoTakenTime;
    notifyChangeListeners();
  }

  /**
   * Get the time at which the photo was last modified (e.g. edited with PhotoShop).
   *   
   * @return the time at which the photo was last modified, or null is the photo hasn't been modified.
   */
  public LocalDateTime getModificationDateTime() {
    return modificationDateTime;
  }

  /**
   * Set the time at which the photo was last modified (e.g. edited with PhotoShop).
   * 
   * @param modificationDateTime the time at which the photo was last modified, or null is the photo hasn't been modified
   */
  public void setModificationDateTime(LocalDateTime modificationDateTime) {
    this.modificationDateTime = modificationDateTime;
    notifyChangeListeners();
  }

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
      LOGGER.info("<= deviceSpecificPhotoTakenTime=" + (deviceSpecificPhotoTakenTime != null ? deviceSpecificPhotoTakenTime.toString() : "null"));
      return deviceSpecificPhotoTakenTime;
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
    
    buf.append("fileName: ").append(fileName).append(NEWLINE);
    
    buf.append("image: ");
    if (image == null) {
      buf.append("not ");
    }
    buf.append("set").append(NEWLINE);
    
    buf.append("deviceSpecificPhotoTakenTime: ").append(deviceSpecificPhotoTakenTime != null ? DTF.format(deviceSpecificPhotoTakenTime) : "--").append(NEWLINE);
    buf.append("modificationDateTime: ").append(modificationDateTime != null ? DTF.format(modificationDateTime) : "--").append(NEWLINE);
    buf.append("sortingDateTime: ").append(sortingDateTime != null ? DTF.format(sortingDateTime) : "--").append(NEWLINE);
    
    buf.append("coordinates: ");
    if (coordinates != null) {
      buf.append(coordinates.getLatitude()).append(", ").append(coordinates.getLongitude());
    } else {
      buf.append("--");
    }
    buf.append("set").append(NEWLINE);
    
    buf.append("approximateGPScoordinates: ").append(approximateGPScoordinates).append(NEWLINE);
    buf.append("selectedForTheShow: ").append(selectedForTheShow.getValue()).append(NEWLINE);
    
    return buf.toString();
  }
  
  public static Comparator<PhotoInfo> getSortingDateTimeComparator() {
    return new SortingDateTimeComparator();
  }

  @Override
  public void addListener(InvalidationListener listener) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void removeListener(InvalidationListener listener) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void addListener(ChangeListener<? super PhotoInfo> changeListener) {
    changeListeners.add(changeListener);
  }

  @Override
  public void removeListener(ChangeListener<? super PhotoInfo> changeListener) {
    changeListeners.remove(changeListener);
  }

  private void notifyChangeListeners() {
    for (ChangeListener<? super PhotoInfo> changeListener: changeListeners) {
      changeListener.changed(this, null, this);
    }
  }

  @Override
  public PhotoInfo getValue() {
    return this;
  }
}


class SortingDateTimeComparator implements Comparator<PhotoInfo> {

  @Override
  public int compare(PhotoInfo photoInfo1, PhotoInfo photoInfo2) {
    LocalDateTime localDateTime1 = photoInfo1.getSortingDateTime();
    LocalDateTime localDateTime2 = photoInfo2.getSortingDateTime();
    if ((localDateTime1 == null)  ||  (localDateTime2 == null)) {
      return 0;
    } else {
      return photoInfo1.getSortingDateTime().compareTo(photoInfo2.getSortingDateTime());
    }
  }
}

