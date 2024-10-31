package goedegep.media.photo;

import javafx.scene.image.Image;

public interface IPhotoMetaDataWithImage extends IPhotoMetaData {
  Image getImage();
  
  void setImage(Image image);

  void setPhotoMetaData(IPhotoMetaData photoMetaData);
  
  IPhotoMetaData getIPhotoMetaData();
}
