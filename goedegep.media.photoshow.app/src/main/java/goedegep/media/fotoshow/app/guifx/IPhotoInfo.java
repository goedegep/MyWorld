package goedegep.media.fotoshow.app.guifx;

import java.time.LocalDateTime;

import goedegep.media.photo.IPhotoMetaDataWithImage;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.util.Callback;

public interface IPhotoInfo extends IPhotoMetaDataWithImage {
  
  void setPhotoMetaDataWithImage(IPhotoMetaDataWithImage iPhotoMetaDataWithImage);
  
  IPhotoMetaDataWithImage getPhotoMetaDataWithImage();

  void setSortingDateTime(LocalDateTime sortingDateTime);
  
  /**
   * Get the data/time to be used for sorting.
   * <p>
   * In the future this may be extended with e.g. an offset, or use the modification date/time if the device time isn't available.
   * 
   * @return the date/time to be used for sorting, or null if this isn't available.
   */
  LocalDateTime getSortingDateTime();

  boolean isSelectedForTheShow();
  BooleanProperty selectedForTheShowProperty();
  

  static Callback<IPhotoInfo, Observable[]> extractor() {
    return (IPhotoInfo p) -> new Observable[]{p.selectedForTheShowProperty()};
  }


  boolean toggleSelectedForTheShow();

}
