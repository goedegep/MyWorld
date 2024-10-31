package goedegep.media.fotomapview.guifx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class PhotoEditor extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(PhotoEditor.class.getName());
  private final static String WINDOW_TITLE = "Photo Editor";
  
  private ImageView imageView = new ImageView();

  public PhotoEditor(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    imageView.setFitWidth(1600);
    imageView.setPreserveRatio(true);
    
    createGUI();
    
    show();
    
    Image image = new Image("file:C:\\Users\\Peter\\Downloads\\Dorpshuis2\\Dorpshuis5.jpg");
    Image correctedImage = correctImage(image);
    imageView.setImage(correctedImage);
    File file = new File("C:\\Users\\Peter\\Downloads\\Dorpshuis2\\Dorpshuis 2532.jpg");
    saveImageAsJpeg(correctedImage, file);
  }
  


  /**
   * Create the GUI.
   */
  private void createGUI() {
    
    
    BorderPane mainPane = new BorderPane();
//    mainPane.setTop(createMenuBar());

    mainPane.setCenter(imageView);
    
//    mainPane.setBottom(createControlPanel);

    setScene(new Scene(mainPane, 1700, 900));
  }
  
  private static Image correctImage(Image image) {
    LOGGER.severe("image size: " + image.getWidth() + ", " + image.getHeight());
    int imageWidth = (int) (1152.0 / 200.0 * image.getHeight());
//    double scaleFactor = 200 / image.getHeight();
    double scaleFactor = 1.0;
    LOGGER.severe("scaleFactor: " + scaleFactor);
    
    WritableImage writableImage = new WritableImage(imageWidth, (int) image.getHeight());
    LOGGER.severe("corrected image size: "  + writableImage.getWidth() + ", " + writableImage.getHeight());
//    WritableImage writableImage = new WritableImage(1152, 200);
    
    createQuadrant(image, writableImage, scaleFactor, Quadrant.TOP_RIGHT);
    createQuadrant(image, writableImage, scaleFactor, Quadrant.BOTTOM_RIGHT);
    createQuadrant(image, writableImage, scaleFactor, Quadrant.BOTTOM_LEFT);
    createQuadrant(image, writableImage, scaleFactor, Quadrant.TOP_LEFT);
        
    return writableImage;
  }
  
  private static void createQuadrant(Image sourceImage, WritableImage writableDestinationImage, double scaleFactor, Quadrant quadrant) {
    double xStart = 200;
    double xStartY = 300;
    double sourceImageWidth = sourceImage.getWidth();
    double sourceImageHalfWidth = sourceImageWidth / 2;
    
    double sourceImageHeight = sourceImage.getHeight();
    double sourceImageHalfHeight = sourceImageHeight / 2;

    double writableDestinationImageWidth = writableDestinationImage.getWidth();
    double writableDestinationImageHalfWidth = writableDestinationImageWidth / 2;
    
    double writableDestinationImageHeight = writableDestinationImage.getHeight();
    double writableDestinationImageHalfHeight = writableDestinationImageHeight / 2;
    
    LOGGER.severe("sourceImageHalfWidth: " + sourceImageHalfWidth + ", sourceImageHalfHeight: " + sourceImageHalfHeight +
        ", writableDestinationImageHalfWidth: " + writableDestinationImageHalfWidth + ", writableDestinationImageHalfHeight" + writableDestinationImageHalfHeight);
   
    PixelReader pixelReader = sourceImage.getPixelReader();
    PixelWriter pixelWriter = writableDestinationImage.getPixelWriter();
    
    for (int x = (int) writableDestinationImageHalfWidth; continueX(x, writableDestinationImageWidth, quadrant); x = incrOrDecrX(x, quadrant)) {
      double xDestIndex = x - writableDestinationImageHalfWidth;
      double xSource = sourceImageHalfWidth + xDestIndex / scaleFactor;
      double pd;
      if (Math.abs(xDestIndex) > xStart) {
//        double p = Math.pow(Math.abs(xDestIndex) - xStart, 1.25);
        double p = Math.pow(Math.abs(xDestIndex) - xStart, 1.41);
        pd = p / xDestIndex;
      } else {
        pd = 0;
      }
      double cor = xDestIndex * pd;
//      double corN = 0.0126 * cor / scaleFactor;
      double corN = 0.013 * cor / scaleFactor;
      if (!quadrant.isLeft()) {
        xSource = xSource - corN;
      } else {
        xSource = xSource + corN;
      }
      
      if (xSource > sourceImageWidth - 1) xSource = sourceImageWidth - 1;
      if (xSource < 0) xSource = 0;
      for (int y = (int) writableDestinationImageHalfHeight; continueY(y, writableDestinationImageHeight, quadrant); y = incrOrDecrY(y, quadrant)) {
        double yDestIndex = y - writableDestinationImageHalfHeight;  // value is negative for top, positive for bottom
        
        double ySource = sourceImageHalfHeight + yDestIndex / scaleFactor;
        double pyd;
        if (Math.abs(xDestIndex) > xStartY) {
//          double py = Math.pow(Math.abs(xDestIndex) - xStart / 4, 1.045) * (-yDestIndex / 65);
          double py = Math.pow(Math.abs(xDestIndex) - xStartY, 1.07) * (-yDestIndex / 130);
          pyd = py / xDestIndex;
        } else {
          pyd = 0;
        }
        double cory = xDestIndex * pyd;
//        double coryN = 0.045 * cory / scaleFactor;
        double coryN = 0.0054 * cory / scaleFactor;
//        if (yDestIndex < 30) {
//          LOGGER.severe("yDestIndex: " + yDestIndex + ", py: " + py + ", pyd: " + pyd + ", cory: " + cory + ", coryN: " + coryN);
//        }
        ySource = ySource + coryN;

        if (ySource > sourceImageHeight - 1) ySource = sourceImageHeight - 1;
        if (ySource < 0) ySource = 0;
        
        Color color = pixelReader.getColor((int) xSource, (int )ySource);
//        if (Math.abs(xDestIndex) < 5  &&  Math.abs(yDestIndex) < 5) {
//          LOGGER.severe(quadrant + " : " + xSource + " : " + ySource + " : " + x + " : " + y);
//        }
        pixelWriter.setColor(x, y, color);
      }
    }
    
  }

  private static boolean continueY(int y, double writableDestinationImageHeight, Quadrant quadrant) {
    switch (quadrant) {
    case BOTTOM_LEFT:
    case BOTTOM_RIGHT:
      return y < (int) writableDestinationImageHeight;

    case TOP_LEFT:
    case TOP_RIGHT:
      return y >= 0;
    }
    
    return false;
  }
  
  private static int incrOrDecrY(int y, Quadrant quadrant) {
    switch (quadrant) {
    case BOTTOM_LEFT:
    case BOTTOM_RIGHT:
      return y = y + 1;

    case TOP_LEFT:
    case TOP_RIGHT:
      return y = y - 1;
    }
    
    return y;
  }

  private static boolean continueX(int x, double writableDestinationImageWidth, Quadrant quadrant) {
    switch (quadrant) {
    case TOP_RIGHT:
    case BOTTOM_RIGHT:
      return x < (int) writableDestinationImageWidth;

    case TOP_LEFT:
    case BOTTOM_LEFT:
      return x >= 0;
    }
    
    return false;
  }
  
  private static int incrOrDecrX(int x, Quadrant quadrant) {
    switch (quadrant) {
    case TOP_RIGHT:
    case BOTTOM_RIGHT:
      return x = x + 1;

    case TOP_LEFT:
    case BOTTOM_LEFT:
      return x = x - 1;
    }
    
    return x;
  }

  public static void saveImageAsJpeg(Image image, File file) {
    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
    BufferedImage imageRGB = new BufferedImage(
        bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.OPAQUE);
    Graphics2D graphics = imageRGB.createGraphics();
    graphics.drawImage(bufferedImage, 0, 0, null);
    try {
      ImageIO.write(imageRGB, "jpg", file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

enum Quadrant {
  TOP_LEFT(true, true),
  TOP_RIGHT(true, false),
  BOTTOM_RIGHT(false, false),
  BOTTOM_LEFT(false, true);
  
  private boolean left;
  private boolean top;
  
  Quadrant(boolean top, boolean left) {
    this.top = top;
    this.left = left;
  }
  
  public boolean isLeft() {
    return left;
  }
  
  public boolean isTop() {
    return top;
  }
}
