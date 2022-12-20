package goedegep.media.photo;

import javafx.scene.image.Image;

public interface IPhotoMetaDataWithImage extends IPhotoMetaData {
  public Image getImage();
  
  public void setImage(Image image);
}
