package goedegep.appgen;

public enum ImageSize {
  SIZE_0(16, 16),
  SIZE_1(32, 32),
  SIZE_2(48, 48),
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
