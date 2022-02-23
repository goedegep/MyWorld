package goedegep.jfx.img;

import java.util.logging.Logger;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class TransparentBackgroundCreator {
  private static final Logger LOGGER = Logger.getLogger(TransparentBackgroundCreator.class.getName());
  
  public Image addBackgroundTransparencyToImage(Image image) {
    PixelReader pixelReader = image.getPixelReader();
    Color transparentColor = determineTransparentColor(image);
    
    if (transparentColor == null) {
      return image;
    }
    
    WritableImage writableImage = new WritableImage(pixelReader, (int) image.getWidth(), (int) image.getHeight());
    
    int numberOfPixels = (int) writableImage.getWidth() * (int) writableImage.getHeight();
    
    // Add transparency from each corner
    PixelWriter pixelWriter = writableImage.getPixelWriter();
    int[] xIndexes = new int[numberOfPixels];
    int[] yIndexes = new int[numberOfPixels];
    boolean[][] handledPixels = new boolean[(int) writableImage.getWidth()][(int) writableImage.getHeight()];
    for (int x = 0; x < writableImage.getWidth(); x++) {
      for (int y = 0; y < writableImage.getHeight(); y++) {
        handledPixels[x][y] = false;
      }
    }
    
    handleFromCorner(writableImage, pixelReader, pixelWriter, transparentColor, xIndexes, yIndexes, handledPixels, 0, 0);
    handleFromCorner(writableImage, pixelReader, pixelWriter, transparentColor, xIndexes, yIndexes, handledPixels, (int) writableImage.getWidth() - 1, 0);
    handleFromCorner(writableImage, pixelReader, pixelWriter, transparentColor, xIndexes, yIndexes, handledPixels, 0, (int) writableImage.getHeight() - 1);
    handleFromCorner(writableImage, pixelReader, pixelWriter, transparentColor, xIndexes, yIndexes, handledPixels, (int) writableImage.getWidth() - 1, (int) writableImage.getHeight() - 1);
        
    return writableImage;
  }
  
  private void handleFromCorner(WritableImage writableImage, PixelReader pixelReader, PixelWriter pixelWriter, Color transparentColor,
      int[] xIndexes, int[] yIndexes, boolean handledPixels[][], int startX, int startY) {
    
    xIndexes[0] = startX;
    yIndexes[0] = startY;
    handledPixels[startX][startY] = true;
    
    int readIndex = 0;
    int writeIndex = 1;
    
    while (readIndex < writeIndex) {
      int x = xIndexes[readIndex];
      int y = yIndexes[readIndex];
      readIndex++;
      Color color = pixelReader.getColor(x, y);
      if (isColorSimilarToTransparentColor(transparentColor, color)) {
        pixelWriter.setArgb(x, y, 0);
        
        int surroundingX;
        int surroundingY;
        
        surroundingX = x - 1;
        surroundingY = y;
        if (surroundingX >= 0 && !handledPixels[surroundingX][surroundingY]) {
          xIndexes[writeIndex] = surroundingX;
          yIndexes[writeIndex] = surroundingY;
          writeIndex++;
          handledPixels[surroundingX][surroundingY] = true;
        }
        surroundingX = x + 1;
        surroundingY = y;
        if (surroundingX < writableImage.getWidth() && !handledPixels[surroundingX][surroundingY]) {
          xIndexes[writeIndex] = surroundingX;
          yIndexes[writeIndex] = surroundingY;
          writeIndex++;
          handledPixels[surroundingX][surroundingY] = true;
        }
        surroundingX = x;
        surroundingY = y - 1;
        if (surroundingY >= 0 && !handledPixels[surroundingX][surroundingY]) {
          xIndexes[writeIndex] = surroundingX;
          yIndexes[writeIndex] = surroundingY;
          writeIndex++;
          handledPixels[surroundingX][surroundingY] = true;
        }
        surroundingX = x;
        surroundingY = y + 1;
        if (surroundingY < writableImage.getHeight() && !handledPixels[surroundingX][surroundingY]) {
          xIndexes[writeIndex] = surroundingX;
          yIndexes[writeIndex] = surroundingY;
          writeIndex++;
          handledPixels[surroundingX][surroundingY] = true;
        }
      }
    }
  }
  
  private static boolean isColorSimilarToTransparentColor(Color transparentColor, Color color) {
    if ((Math.abs(color.getBlue() - transparentColor.getBlue()) < 0.01)  &&
        (Math.abs(color.getGreen() - transparentColor.getGreen()) < 0.01)  &&
        (Math.abs(color.getRed() - transparentColor.getRed()) < 0.015)) {
     return true;
    } else {
      return false;
    }
  }
  
  /**
   * Determine whether an image probably has a transparent surrounding, and if so which color
   * this is represented by.
   * <p>
   * An image has a transparent color if at least 2 corners have 3 pixels of the same color.
   * 
   * @param image the Image for which the transparent color is to be determined
   * @return the transparent color, or null if the image doesn't have one
   */
  public Color determineTransparentColor(Image image) {
    LOGGER.severe("=>");
    
    Color topLeftColor = null;
    Color topRightColor = null;
    Color bottomLeftColor = null;
    Color bottomRightColor = null;
    
    PixelReader pixelReader = image.getPixelReader();
    
    // Determine whether there is a Top Left transparent color
    Color color1 = pixelReader.getColor(0, 0);
    Color color2 = pixelReader.getColor(0, 1);
    Color color3 = pixelReader.getColor(1, 0);
    if (isColorSimilarToTransparentColor(color1, color2)  &&  isColorSimilarToTransparentColor(color1, color3)) {
      topLeftColor = color1;
    }
    
    // Determine whether there is a Top Right transparent color
    color1 = pixelReader.getColor((int) image.getWidth() - 1, 0);
    color2 = pixelReader.getColor((int) image.getWidth() - 2, 0);
    color3 = pixelReader.getColor((int) image.getWidth() - 1, 1);
    if (isColorSimilarToTransparentColor(color1, color2)  &&  isColorSimilarToTransparentColor(color1, color3)) {
      topRightColor = color1;
    }
    
    // Determine whether there is a Bottom Left transparent color
    color1 = pixelReader.getColor(0, (int) image.getHeight() - 1);
    color2 = pixelReader.getColor(1, (int) image.getHeight() - 1);
    color3 = pixelReader.getColor(0, (int) image.getHeight() - 2);
    if (isColorSimilarToTransparentColor(color1, color2)  &&  isColorSimilarToTransparentColor(color1, color3)) {
      bottomLeftColor = color1;
    }
    
    // Determine whether there is a Bottom Right transparent color
    color1 = pixelReader.getColor((int) image.getWidth() - 1, (int) image.getHeight() - 1);
    color2 = pixelReader.getColor((int) image.getWidth() - 2, (int) image.getHeight() - 1);
    color3 = pixelReader.getColor((int) image.getWidth() - 1, (int) image.getHeight() - 1);
    if (isColorSimilarToTransparentColor(color1, color2)  &&  isColorSimilarToTransparentColor(color1, color3)) {
      bottomRightColor = color1;
    }
    
    if (topLeftColor != null  &&
        ((topRightColor == null)  ||  topRightColor.equals(topLeftColor))  &&
        ((bottomLeftColor == null)  ||  bottomLeftColor.equals(topLeftColor))  &&
        ((bottomRightColor == null)  ||  bottomRightColor.equals(topLeftColor))) {
      LOGGER.severe("<= topLeftColor");
      return topLeftColor;
    }
    
    if (topRightColor != null  &&
        ((bottomLeftColor == null)  ||  bottomLeftColor.equals(topRightColor))  &&
        ((bottomRightColor == null)  ||  bottomRightColor.equals(topRightColor))) {
      LOGGER.severe("<= topRightColor");
      return topRightColor;
    }
    
    if (bottomLeftColor != null  &&
        ((bottomRightColor == null)  ||  bottomRightColor.equals(bottomLeftColor))) {
      LOGGER.severe("<= bottomLeftColor");
      return bottomLeftColor;
    }
    
    if (bottomRightColor != null) {
      LOGGER.severe("<= bottomRightColor");
      return bottomRightColor;
    }
    
    LOGGER.severe("<= null");
    return null;
  }
  
}
