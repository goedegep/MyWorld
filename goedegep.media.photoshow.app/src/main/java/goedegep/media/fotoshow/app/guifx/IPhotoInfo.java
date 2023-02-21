package goedegep.media.fotoshow.app.guifx;

import java.time.LocalDateTime;

import goedegep.media.photo.IPhotoMetaDataWithImage;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.util.Callback;

public interface IPhotoInfo extends IPhotoMetaDataWithImage {
  
  public void setPhotoMetaDataWithImage(IPhotoMetaDataWithImage iPhotoMetaDataWithImage);
  
  public IPhotoMetaDataWithImage getPhotoMetaDataWithImage();

  public void setSortingDateTime(LocalDateTime sortingDateTime);
  
  /**
   * Get the data/time to be used for sorting.
   * <p>
   * In the future this may be extended with e.g. an offset, or use the modification date/time if the device time isn't available.
   * 
   * @return the date/time to be used for sorting, or null if this isn't available.
   */
  public LocalDateTime getSortingDateTime();

  public boolean isSelectedForTheShow();
  public BooleanProperty selectedForTheShowProperty();
  

  static Callback<IPhotoInfo, Observable[]> extractor() {
    return (IPhotoInfo p) -> new Observable[]{p.selectedForTheShowProperty()};
  }


  public boolean toggleSelectedForTheShow();

}
