package goedegep.media.fotoshow.app.guifx;

import java.time.LocalDateTime;

import goedegep.media.photo.IPhotoMetaDataWithImage;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.util.Callback;

public interface IPhotoInfo extends IPhotoMetaDataWithImage {

  void setSortingDateTime(LocalDateTime sortingDateTime);
  

  public boolean isSelectedForTheShow();
  public BooleanProperty selectedForTheShowProperty();
  

  static Callback<IPhotoInfo, Observable[]> extractor() {
    return (IPhotoInfo p) -> new Observable[]{p.selectedForTheShowProperty()};
  }


  public boolean toggleSelectedForTheShow();

}
