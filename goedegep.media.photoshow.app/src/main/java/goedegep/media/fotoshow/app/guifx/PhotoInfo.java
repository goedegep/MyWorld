package goedegep.media.fotoshow.app.guifx;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import goedegep.geo.WGS84Coordinates;
import goedegep.media.photo.IPhotoMetaData;
import goedegep.media.photo.IPhotoMetaDataWithImage;
import goedegep.media.photo.PhotoMetaData;
import goedegep.media.photo.PhotoMetaDataWithImage;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;
import javafx.util.Callback;

public class PhotoInfo implements Serializable, IPhotoInfo {
  private static final long serialVersionUID = 8042429357758147556L;
  private final static Logger LOGGER = Logger.getLogger(PhotoInfo.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  private List<ChangeListener<? super IPhotoMetaData>> changeListeners = new ArrayList<>();
  
  private IPhotoMetaDataWithImage photoMetaDataWithImage;
  private LocalDateTime sortingDateTime;
  private BooleanProperty selectedForTheShow = new SimpleBooleanProperty(false);

  public Image getImage() {
    return photoMetaDataWithImage != null ? photoMetaDataWithImage.getImage() : null;
  }

  public void setImage(Image image) {
    if (photoMetaDataWithImage == null) {
      photoMetaDataWithImage = new PhotoMetaDataWithImage();
    }
    
    photoMetaDataWithImage.setImage(image);
  }
  
  public String getTitle() {
    return photoMetaDataWithImage != null ? photoMetaDataWithImage.getTitle() : null;
  }

  @Override
  public void setTitle(String title) {
    if (photoMetaDataWithImage == null) {
      photoMetaDataWithImage = new PhotoMetaDataWithImage();
    }
    
    photoMetaDataWithImage.setTitle(title);
  }

  public String getFileName() {
    return photoMetaDataWithImage != null ? photoMetaDataWithImage.getFileName() : null;
  }

  public void setFileName(String filename) {
    if (photoMetaDataWithImage == null) {
      photoMetaDataWithImage = new PhotoMetaDataWithImage();
    }
    
    photoMetaDataWithImage.setFileName(filename);
  }

  public WGS84Coordinates getCoordinates() {
    return photoMetaDataWithImage != null ? photoMetaDataWithImage.getCoordinates() : null;
  }

  @Override
  public void setCoordinates(WGS84Coordinates coordinates) {
    if (photoMetaDataWithImage == null) {
      photoMetaDataWithImage = new PhotoMetaDataWithImage();
    }
    
    photoMetaDataWithImage.setCoordinates(coordinates);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getRotationAngle() {
    return photoMetaDataWithImage != null ? photoMetaDataWithImage.getRotationAngle() : null;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setRotationAngle(Integer rotationAngle) {
    if (photoMetaDataWithImage == null) {
      photoMetaDataWithImage = new PhotoMetaDataWithImage();
    }
    
    photoMetaDataWithImage.setRotationAngle(rotationAngle);
  }

  public LocalDateTime getDeviceSpecificPhotoTakenTime() {
    return photoMetaDataWithImage != null ? photoMetaDataWithImage.getDeviceSpecificPhotoTakenTime() : null;
  }
  

  public void setDeviceSpecificPhotoTakenTime(LocalDateTime deviceSpecificPhotoTakenTime) {
    if (photoMetaDataWithImage == null) {
      photoMetaDataWithImage = new PhotoMetaDataWithImage();
    }
    
    photoMetaDataWithImage.setDeviceSpecificPhotoTakenTime(deviceSpecificPhotoTakenTime);
  }
 
  public boolean hasApproximateGPScoordinates() {
    return photoMetaDataWithImage != null ? photoMetaDataWithImage.hasApproximateGPScoordinates() : null;
  }

  @Override
  public void setApproximateGPScoordinates(boolean approximateGPScoordinates) {
    if (photoMetaDataWithImage == null) {
      photoMetaDataWithImage = new PhotoMetaDataWithImage();
    }
    
    photoMetaDataWithImage.setApproximateGPScoordinates(approximateGPScoordinates);
  }

  public LocalDateTime getModificationDateTime() {
    return photoMetaDataWithImage != null ? photoMetaDataWithImage.getModificationDateTime() : null;
  }

  public void setModificationDateTime(LocalDateTime modificationDateTime) {
    if (photoMetaDataWithImage == null) {
      photoMetaDataWithImage = new PhotoMetaDataWithImage();
    }
    
    photoMetaDataWithImage.setModificationDateTime(modificationDateTime);
  }

  
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
  
  public static Callback<PhotoInfo, Observable[]> extractor() {
    return (PhotoInfo p) -> new Observable[]{p.selectedForTheShowProperty()};
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
    
    buf.append("approximateGPScoordinates: ").append(hasApproximateGPScoordinates()).append(NEWLINE);
    buf.append("selectedForTheShow: ").append(selectedForTheShow.getValue()).append(NEWLINE);
    
    return buf.toString();
  }
  
//  public static Comparator<PhotoMetaDataWithImage> getSortingDateTimeComparator() {
//    return new SortingDateTimeComparator();
//  }

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
    changeListeners.add(changeListener);
  }

  @Override
  public void removeListener(ChangeListener<? super IPhotoMetaData> changeListener) {
    changeListeners.remove(changeListener);
  }

  private void notifyChangeListeners() {
    for (ChangeListener<? super IPhotoMetaData> changeListener: changeListeners) {
      changeListener.changed(this, null, this);
    }
  }

  @Override
  public PhotoInfo getValue() {
    return this;
  }

  public static PhotoInfo fromIPhotoMetaDataWithImage(IPhotoMetaDataWithImage photoMetaDataWithImage) {
    PhotoInfo photoInfo = new PhotoInfo();
    photoInfo.setPhotoMetaDataWithImage(photoMetaDataWithImage);
    
//    photoInfo.setFileName(photoMetaDataWithImage.getFileName());
//    photoInfo.setTitle(photoMetaDataWithImage.getTitle());
//    photoInfo.setCoordinates(photoMetaDataWithImage.getCoordinates());
//    photoInfo.setApproximateGPScoordinates(photoMetaDataWithImage.hasApproximateGPScoordinates());
//    photoInfo.setDeviceSpecificPhotoTakenTime(photoMetaDataWithImage.getDeviceSpecificPhotoTakenTime());
//    photoInfo.setModificationDateTime(photoMetaDataWithImage.getModificationDateTime());
//    photoInfo.setImage(photoMetaDataWithImage.getImage());
    
    return photoInfo;
  }

  @Override
  public void setPhotoMetaDataWithImage(IPhotoMetaDataWithImage photoMetaDataWithImage) {
    this.photoMetaDataWithImage = photoMetaDataWithImage;
  }
  
  @Override
  public IPhotoMetaDataWithImage getPhotoMetaDataWithImage() {
    return photoMetaDataWithImage;
  }

  @Override
  public IPhotoMetaData getIPhotoMetaData() {
    return photoMetaDataWithImage.getIPhotoMetaData();
  }

  @Override
  public void setPhotoMetaData(IPhotoMetaData photoMetaData) {
    if (photoMetaDataWithImage == null) {
      photoMetaDataWithImage = new PhotoMetaDataWithImage();
    }
    
    photoMetaDataWithImage.setPhotoMetaData(photoMetaData);
  }
}


class SortingDateTimeComparator implements Comparator<IPhotoInfo> {

  @Override
  public int compare(IPhotoInfo photoInfo1, IPhotoInfo photoInfo2) {
    LocalDateTime localDateTime1 = photoInfo1.getSortingDateTime();
    LocalDateTime localDateTime2 = photoInfo2.getSortingDateTime();
    if ((localDateTime1 == null)  ||  (localDateTime2 == null)) {
      return 0;
    } else {
      return localDateTime1.compareTo(localDateTime2);
    }
  }
}

