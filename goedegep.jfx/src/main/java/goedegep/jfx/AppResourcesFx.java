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
  final static int IMAGE_BORDER_SIZE = 4;
  
  // The basic image represents the application in which it is used.  
//  public ImageIcon getSmallIconDisabled();  // TODO AAA make generic
  
  /*
   * Get the raw default images. These are the fall back images for when there are no class specific images.
   */
  public Image[] getRawDefaultInfoImages();
  public Image[] getRawDefaultErrorImages();
  public Image[] getRawDefaultAttentionImages();
  public Image[] getRawDefaultApplicationImages();
  
  /*
   * Get the raw images. These are the images as read from the files.
   * If there are application specific images, these will be returned,
   * else the default (fall back) images are returned.
   */
  public Image[] getRawInfoImages();
  public Image[] getRawErrorImages();
  public Image[] getRawAttentionImages();
  public Image[] getRawApplicationImages();
  
  /*
   * Get an image for a specific size. Each image is derived from the best fitting raw image.
   */
  public Image getInfoImage(ImageSize imageSize);
  public Image getErrorImage(ImageSize imageSize);
  public Image getAttentionImage(ImageSize imageSize);
  public Image getApplicationImage(ImageSize imageSize);
  
  /*
   * Get an image for a specific size, with a border. Each image is derived from the best fitting raw image.
   */
//  public Image getInfoImageWithBorder(ImageSize imageSize);
//  public Image getErrorImageWithBorder(ImageSize imageSize);
//  public Image getAttentionImageWithBorder(ImageSize imageSize);
//  public Image getApplicationImageWithBorder(ImageSize imageSize);
  
  /*
   * Get an icon for a specific size.
   */
//  public Icon getInfoIcon(ImageSize imageSize);
//  public Icon getErrorIcon(ImageSize imageSize);
//  public Icon getAttentionIcon(ImageSize imageSize);
//  public Icon getApplicationIcon(ImageSize imageSize);
  
  
  /*
   * Get an icon for a specific size, with a border.
   */
//  public Icon getInfoIconWithBorder(ImageSize imageSize);
//  public Icon getErrorIconWithBorder(ImageSize imageSize);
//  public Icon getAttentionIconWithBorder(ImageSize imageSize);
//  public Icon getApplicationIconWithBorder(ImageSize imageSize);
  
  // Get the application specific picture (image).
  public Image getPicture();   
}