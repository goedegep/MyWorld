package goedegep.media.photo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import goedegep.geo.WGS84Coordinates;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;

public class PhotoMetaDataWithImage implements Serializable, IPhotoMetaDataWithImage {
  private static final long serialVersionUID = -4467398989079454134L;
  
  private IPhotoMetaData photoMetaData = new PhotoMetaData();
  private Image image;
  
  /**
   * ChangeListener to react to changes in the photoMetaData.
   * This is created when the first listener is added to this object.
   * And from then on it is added to the photoMetaData.
   */
  private ChangeListener<IPhotoMetaData> metaDataChangeListener = null;

  private List<ChangeListener<? super IPhotoMetaData>> changeListeners = new ArrayList<>();

  public IPhotoMetaData getPhotoMetaData() {
    return photoMetaData;
  }

  public void setPhotoMetaData(IPhotoMetaData photoMetaData) {
    if (metaDataChangeListener != null  &&  this.photoMetaData != null) {
      this.photoMetaData.removeListener(metaDataChangeListener);
    }
    
    this.photoMetaData = photoMetaData;
    
    if (metaDataChangeListener != null) {
      photoMetaData.addListener(metaDataChangeListener);
    }
  }
  
  @Override
  public IPhotoMetaData getIPhotoMetaData() {
    return photoMetaData;
  }


  public Image getImage() {
    return image;
  }
  
  

  public void setImage(Image image) {
    this.image = image;
    notifyChangeListeners();
  }

  
  public String getTitle() {
    return photoMetaData != null ? photoMetaData.getTitle() : null;
  }

  @Override
  public void setTitle(String title) {
    if (photoMetaData == null) {
      photoMetaData = new PhotoMetaData();
    }
    
    photoMetaData.setTitle(title);
  }

  public String getFileName() {
    return photoMetaData != null ? photoMetaData.getFileName() : null;
  }

  public void setFileName(String filename) {
    if (photoMetaData == null) {
      photoMetaData = new PhotoMetaData();
    }
    
    photoMetaData.setFileName(filename);
  }

  public WGS84Coordinates getCoordinates() {
    return photoMetaData != null ? photoMetaData.getCoordinates() : null;
  }

  @Override
  public void setCoordinates(WGS84Coordinates coordinates) {
    if (photoMetaData == null) {
      photoMetaData = new PhotoMetaData();
    }
    
    photoMetaData.setCoordinates(coordinates);
  }

  public LocalDateTime getDeviceSpecificPhotoTakenTime() {
    return photoMetaData != null ? photoMetaData.getDeviceSpecificPhotoTakenTime() : null;
  }
  

  public void setDeviceSpecificPhotoTakenTime(LocalDateTime deviceSpecificPhotoTakenTime) {
    if (photoMetaData == null) {
      photoMetaData = new PhotoMetaData();
    }
    
    photoMetaData.setDeviceSpecificPhotoTakenTime(deviceSpecificPhotoTakenTime);
  }
 
  public boolean hasApproximateGPScoordinates() {
    return photoMetaData != null ? photoMetaData.hasApproximateGPScoordinates() : null;
  }

  @Override
  public void setApproximateGPScoordinates(boolean approximateGPScoordinates) {
    if (photoMetaData == null) {
      photoMetaData = new PhotoMetaData();
    }
    
    photoMetaData.setApproximateGPScoordinates(approximateGPScoordinates);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getRotationAngle() {
    return photoMetaData != null ? photoMetaData.getRotationAngle() : null;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setRotationAngle(Integer rotationAngle) {
    if (photoMetaData == null) {
      photoMetaData = new PhotoMetaData();
    }
    
    photoMetaData.setRotationAngle(rotationAngle);
  }

  public LocalDateTime getModificationDateTime() {
    return photoMetaData != null ? photoMetaData.getModificationDateTime() : null;
  }

  public void setModificationDateTime(LocalDateTime modificationDateTime) {
    if (photoMetaData == null) {
      photoMetaData = new PhotoMetaData();
    }
    
    photoMetaData.setModificationDateTime(modificationDateTime);
  }

//  /**
//   * Get the data/time to be used for sorting.
//   * <p>
//   * In the future this may be extended with e.g. an offset, or use the modification date/time if the device time isn't available.
//   * 
//   * @return the date/time to be used for sorting, or null if this isn't available.
//   */
//  public LocalDateTime getSortingDateTime() {
//    return photoMetaData != null ? photoMetaData.getSortingDateTime() : null;
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
    if (metaDataChangeListener == null) {
      metaDataChangeListener = (observable, oldValue, newValue) -> notifyChangeListeners();
    }
    
    if (changeListeners.isEmpty()  &&  photoMetaData != null) {
      photoMetaData.addListener(metaDataChangeListener);
    }
    changeListeners.add(changeListener);
  }

  @Override
  public void removeListener(ChangeListener<? super IPhotoMetaData> changeListener) {
    changeListeners.remove(changeListener);
    if (changeListeners.isEmpty() && photoMetaData != null) {
      photoMetaData.removeListener(metaDataChangeListener);
    }
  }

  protected void notifyChangeListeners() {
    for (ChangeListener<? super IPhotoMetaData> changeListener: changeListeners) {
      changeListener.changed(this, null, this);
    }
  }

  @Override
  public PhotoMetaDataWithImage getValue() {
    return this;
  }

//  public static Comparator<IPhotoMetaDataWithImage> getSortingDateTimeComparator() {
//    return new SortingDateTimeComparator();
//  }

//  @Override
//  public void setPhotoMetaData(IPhotoMetaData photoMetaData) {
//    this.photoMetaData = photoMetaData;
//    
//  }
}

//class SortingDateTimeComparator implements Comparator<IPhotoMetaDataWithImage> {
//
//  @Override
//  public int compare(IPhotoMetaDataWithImage photoMetaDataWithImage1, IPhotoMetaDataWithImage photoMetaDataWithImage2) {
//    LocalDateTime localDateTime1 = photoMetaDataWithImage1.getSortingDateTime();
//    LocalDateTime localDateTime2 = photoMetaDataWithImage2.getSortingDateTime();
//    if ((localDateTime1 == null)  ||  (localDateTime2 == null)) {
//      return 0;
//    } else {
//      return photoMetaDataWithImage1.getSortingDateTime().compareTo(photoMetaDataWithImage2.getSortingDateTime());
//    }
//  }
//}
//
