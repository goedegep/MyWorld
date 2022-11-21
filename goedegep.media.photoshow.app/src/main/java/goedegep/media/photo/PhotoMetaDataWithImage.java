package goedegep.media.photo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import goedegep.geo.WGS84Coordinates;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class PhotoMetaDataWithImage implements Serializable, ObservableValue<PhotoMetaDataWithImage> {
  private static final long serialVersionUID = -4467398989079454134L;
  
  private PhotoMetaData photoMetaData = new PhotoMetaData();
  private Image image;
  
  /**
   * ChangeListener to react to changes in the photoMetaData.
   * This is created when the first listener is added to this object.
   * And from then on it is added to the photoMetaData.
   */
  private ChangeListener<PhotoMetaData> metaDataChangeListener = null;

  private List<ChangeListener<? super PhotoMetaDataWithImage>> changeListeners = new ArrayList<>();

  public PhotoMetaData getPhotoMetaData() {
    return photoMetaData;
  }

  public void setPhotoMetaData(PhotoMetaData photoMetaData) {
    if (this.photoMetaData != null) {
      this.photoMetaData.removeListener(metaDataChangeListener);
    }
    
    this.photoMetaData = photoMetaData;
    
    photoMetaData.addListener(metaDataChangeListener);
  }

  public Image getImage() {
    return image;
  }
  
  

  public void setImage(Image image) {
    this.image = image;
    notifyChangeListeners();
  }

  /*
   * Convenience methods for accessing photoMetaData
   */
  
  public String getTitle() {
    return photoMetaData != null ? photoMetaData.getTitle() : null;
  }

  public String getFileName() {
    return photoMetaData != null ? photoMetaData.getFileName() : null;
  }

  public WGS84Coordinates getCoordinates() {
    return photoMetaData != null ? photoMetaData.getCoordinates() : null;
  }

  public LocalDateTime getDeviceSpecificPhotoTakenTime() {
    return photoMetaData != null ? photoMetaData.getDeviceSpecificPhotoTakenTime() : null;
  }

  public boolean isApproximateGPScoordinates() {
    return photoMetaData != null ? photoMetaData.isApproximateGPScoordinates() : null;
  }

  public LocalDateTime getModificationDateTime() {
    return photoMetaData != null ? photoMetaData.getModificationDateTime() : null;
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
  public void addListener(ChangeListener<? super PhotoMetaDataWithImage> changeListener) {
    if (changeListener == null) {
      metaDataChangeListener = (observable, oldValue, newValue) -> notifyChangeListeners();
    }
    
    if (changeListeners.isEmpty()) {
      if (photoMetaData != null) {
        photoMetaData.addListener(metaDataChangeListener);
      }
    }
    changeListeners.add(changeListener);
  }

  @Override
  public void removeListener(ChangeListener<? super PhotoMetaDataWithImage> changeListener) {
    changeListeners.remove(changeListener);
    if (changeListeners.isEmpty() && photoMetaData != null) {
      photoMetaData.removeListener(metaDataChangeListener);
    }
  }

  protected void notifyChangeListeners() {
    for (ChangeListener<? super PhotoMetaDataWithImage> changeListener: changeListeners) {
      changeListener.changed(this, null, this);
    }
  }

  @Override
  public PhotoMetaDataWithImage getValue() {
    return this;
  }
}
