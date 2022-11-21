package goedegep.resources;

/**
 * This enum provides constants for some often used image sizes.
 */
public enum ImageSize {
  /**
   * The image size is a bounding box of 16 x 16.
   */
  SIZE_0(16, 16),
  /**
   * The image size is a bounding box of 32 x 32.
   */
  SIZE_1(32, 32),
  /**
   * The image size is a bounding box of 48 x 48.
   */
  SIZE_2(48, 48),
  /**
   * The image size is a bounding box of 96 x 96.
   */
  SIZE_3(96, 96);

  private int width;
  private int height;

  private ImageSize(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
}
