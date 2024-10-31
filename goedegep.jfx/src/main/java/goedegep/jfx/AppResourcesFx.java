package goedegep.jfx;


import goedegep.resources.ImageSize;
import javafx.scene.image.Image;

/**
 * Interface to obtain application specific resources.
 * There are images defined for:
 *  Info        - information
 *  Error       - errors
 *  Attention   - attention
 *  Application - the application
 *
 * For each image the following image types are available:
 * The raw images 
 * 
 */
public interface AppResourcesFx {
  int IMAGE_BORDER_SIZE = 4;
  
  /*
   * Get the raw default images. These are the fall back images for when there are no class specific images.
   */
  Image[] getRawDefaultInfoImages();
  Image[] getRawDefaultErrorImages();
  Image[] getRawDefaultAttentionImages();
  Image[] getRawDefaultApplicationImages();
  
  /*
   * Get the raw images. These are the images as read from the files.
   * If there are application specific images, these will be returned,
   * else the default (fall back) images are returned.
   */
  Image[] getRawInfoImages();
  Image[] getRawErrorImages();
  Image[] getRawAttentionImages();
  Image[] getRawApplicationImages();
  
  /*
   * Get an image for a specific size. Each image is derived from the best fitting raw image.
   */
  Image getInfoImage(ImageSize imageSize);
  Image getErrorImage(ImageSize imageSize);
  Image getAttentionImage(ImageSize imageSize);
  Image getApplicationImage(ImageSize imageSize);
  
  /*
   * Get an image for a specific size, with a border. Each image is derived from the best fitting raw image.
   */
//  Image getInfoImageWithBorder(ImageSize imageSize);
//  Image getErrorImageWithBorder(ImageSize imageSize);
//  Image getAttentionImageWithBorder(ImageSize imageSize);
//  Image getApplicationImageWithBorder(ImageSize imageSize);
  
  /*
   * Get an icon for a specific size.
   */
//  Icon getInfoIcon(ImageSize imageSize);
//  Icon getErrorIcon(ImageSize imageSize);
//  Icon getAttentionIcon(ImageSize imageSize);
//  Icon getApplicationIcon(ImageSize imageSize);
  
  
  /*
   * Get an icon for a specific size, with a border.
   */
//  Icon getInfoIconWithBorder(ImageSize imageSize);
//  Icon getErrorIconWithBorder(ImageSize imageSize);
//  Icon getAttentionIconWithBorder(ImageSize imageSize);
//  Icon getApplicationIconWithBorder(ImageSize imageSize);
  
  // Get the application specific picture (image).
  Image getPicture();   
}