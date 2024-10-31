package goedegep.jfx.collage;


import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import goedegep.util.Tuplet;
import goedegep.util.img.collageimpl.QTreeFx;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

/**
 * This class provides methods to create collages of pictures.
 * <p>
 * <h3>Tiled photo collage</h3>
 * The method {@link #createTiledCollage} creates a tiled collage. This is a collage where pictures are placed in rows and columns.
 * <h3>Collage</h3>
 * The method {@link #createCollageImage} creates a normal collage. This is a collage where the pictures are rotated and spread over the image.
 */
public class CollageImage {
  private static final Logger LOGGER = Logger.getLogger(CollageImage.class.getName());
  
  /**
   * The amount of overlap of pictures in a collage.
   */
  private static final double OVERLAP_FACTOR = 3.0;

  /**
   * Create a tiled photo collage.
   * <p>
   * The number of horizontal pictures is specified. The number of vertical images is calculated as the number of
   * imageFiles divided by the number of horizontal images.<br/>
   * The width is specified. So the width per picture is collageWidth / nrOfHorizontalImages. The height per picture is
   * the same as the width per picture. So the collageHeight is width per picture times the number of vertical images.
   * 
   * @param collageWidth the width of the collage in pixels
   * @param nrOfHorizontalImages the number of horizontal pictures
   * @param imageFiles the image files to use for the collage
   * @return the created collage
   */
  public static Canvas createTiledCollage(int collageWidth, int nrOfHorizontalImages, List<File> imageFiles) {
    LOGGER.info("=> collageWidth=" + collageWidth + ", nrOfHorizontalImages=" + nrOfHorizontalImages);
    
    int nrOfImages = imageFiles.size();
    int nrOfVerticalImages = nrOfImages / nrOfHorizontalImages;
    int widthPerImage = collageWidth / nrOfHorizontalImages;
    int collageHeight = nrOfVerticalImages * widthPerImage;
    LOGGER.info("nrOfImages=" + nrOfImages + ", nrOfVerticalImages= " + nrOfVerticalImages +
        ", widthPerImage=" + widthPerImage + ", collageHeight=" + collageHeight);
    
    Canvas canvas = new Canvas(collageWidth, collageHeight);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    
    int imageFileIndex = 0;
    gc.setGlobalAlpha(0.5);
    for (int tlpy = 0; tlpy < collageHeight; tlpy += widthPerImage) {
      for (int tlpx = 0; tlpx < collageWidth; tlpx += widthPerImage) {
        javafx.scene.image.Image image = new javafx.scene.image.Image("file:" + imageFiles.get(imageFileIndex++).getAbsolutePath(), widthPerImage - 0, widthPerImage - 0, true, false);
        gc.drawImage(image, tlpx, tlpy);
      }
    }
    
    return canvas;
  }

  /**
   * Create a collage.
   * 
   * @param collageWidth the width of the collage in pixels.
   * @param collageHeight the height of the collage in pixels.
   * @param imageFiles the names of the picture files to use in the collage.
   * @return the created collage.
   */
  public static Canvas createCollageImage(int collageWidth, int collageHeight, List<File> imageFiles) {
    LOGGER.info("=> collageWidth = " + collageWidth + ", collageHeight = " + collageHeight);
    
    // The size of the images is made dependent on the size of the collage and the number images available.
    int collageSizeInPixels = collageWidth * collageHeight;
    double imageSizeInPixels = OVERLAP_FACTOR * collageSizeInPixels / imageFiles.size();
    double imageWidthAndLength = Math.sqrt(imageSizeInPixels);
    LOGGER.info("imageSizeInPixels=" + imageSizeInPixels + ", imageWidthAndLength=" + imageWidthAndLength);

    Canvas canvas = new Canvas(collageWidth, collageHeight);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    // Use a QTree to divide the images over the collage.
    QTreeFx qtree = new QTreeFx(6, collageWidth, collageHeight, imageWidthAndLength);

    for (File file: imageFiles) {
      addImage(gc, qtree, file, imageWidthAndLength);
    }
    
    Canvas transparentCanvas = new Canvas(collageWidth, collageHeight);
    GraphicsContext transparentCanvasGC = transparentCanvas.getGraphicsContext2D();
    WritableImage writeableImage = new WritableImage(collageWidth, collageHeight);
    canvas.snapshot(null, writeableImage);
    transparentCanvasGC.setGlobalAlpha(0.2);
    transparentCanvasGC.drawImage(writeableImage, 0, 0);
    
    return transparentCanvas;
  }

  /**
   * Draws an image on a graphics context.
   *
   * The image is drawn at (tlpx, tlpy) rotated by angle pivoted around the point:
   *   (tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2)
   *
   * @param gc the graphics context the image is to be drawn on.
   * @param angle the angle of rotation.
   * @param tlpx the top left x co-ordinate where the image will be plotted (in canvas co-ordinates).
   * @param tlpy the top left y co-ordinate where the image will be plotted (in canvas co-ordinates).
   */
  private static void drawRotatedTransparentImage(GraphicsContext gc, javafx.scene.image.Image image, double angle, double tlpx, double tlpy) {
      gc.save(); // saves the current state on stack, including the current transform
      rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
      gc.setGlobalAlpha(0.9);
      gc.drawImage(image, tlpx, tlpy);
      gc.restore(); // back to original state (before rotation)
  }
  
  /**
   * Set the transform for the GraphicsContext to rotate around a pivot point.
   *
   * @param gc the graphics context the transform is to be applied to.
   * @param angle the angle of rotation measured in degrees.
   * @param pivotPointX the x pivot co-ordinate for the rotation (in canvas co-ordinates).
   * @param pivotPointY the y pivot co-ordinate for the rotation (in canvas co-ordinates).
   */
  private static void rotate(GraphicsContext gc, double angle, double pivotPointX, double pivotPointY) {
      Rotate rotate = new Rotate(angle, pivotPointX, pivotPointY);
      gc.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
  }
  
  private static void addImage(GraphicsContext gc, QTreeFx qtree, File imageFile, double imageWidthAndLength) {
    LOGGER.info("imageFile=" + imageFile.getAbsolutePath());
    javafx.scene.image.Image image = new javafx.scene.image.Image("file:" + imageFile.getAbsolutePath(), imageWidthAndLength, imageWidthAndLength, true, false);
    Tuplet<Integer, Integer> imageCoordinates = qtree.addImage();
    LOGGER.info("imageCoordinates=" + imageCoordinates.getObject1() + "," + imageCoordinates.getObject2());
        
    double angle = getRandomRotation();
    javafx.scene.image.Image imageWithTransparency = addBackgroundTransparencyToImage(image); 
    drawRotatedTransparentImage(gc, imageWithTransparency,  angle,   imageCoordinates.getObject1() - image.getWidth() / 2, imageCoordinates.getObject2() - image.getHeight() / 2);
  }
  
  private static javafx.scene.image.Image addBackgroundTransparencyToImage(javafx.scene.image.Image image) {
    PixelReader pixelReader = image.getPixelReader();
    Color transparentColor = pixelReader.getColor(0, 0);
    
    WritableImage writableImage = new WritableImage(pixelReader, (int) image.getWidth(), (int) image.getHeight());
    PixelWriter pixelWriter = writableImage.getPixelWriter();
    for (int x = 0; x < (int) image.getWidth(); x++) {
      for (int y = 0; y < (int) image.getHeight(); y++) {
        Color color = pixelReader.getColor(x, y);
        if (color.equals(transparentColor)) {
          pixelWriter.setArgb(x, y, 0);
        }
      }
    }
    
    return writableImage;
  }
  
  private static double getRandomRotation() {
    double rotation = 0.0;
    
    double value = (Math.random() - 0.5) * 20;
    if (value > -6.0  && value < 6.0) {
      rotation = 5 * value;
    }
    
    return rotation;
  }
}
