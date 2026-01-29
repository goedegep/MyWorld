package goedegep.media.photo;

import javafx.scene.image.Image;

/**
 * This interface adds an {@link Image} to the {@link IPhotoMetaData} interface.
 */
public interface IPhotoMetaDataWithImage extends IPhotoMetaData {
  /**
   * Get the image.
   * 
   * @return the {@code Image}
   */
  Image getImage();
  
  /**
   * Set the image.
   * 
   * @param image the {@Image}
   */
  void setImage(Image image);

  /**
   * Set the {@code IPhotoMetaData}
   * 
   * @param photoMetaData the {@code IPhotoMetaData}.
   */
  void setPhotoMetaData(IPhotoMetaData photoMetaData);
  
  /**
   * Get the {@code IPhotoMetaData}.
   * 
   * @return the {@code IPhotoMetaData}.
   */
  IPhotoMetaData getIPhotoMetaData();
}
