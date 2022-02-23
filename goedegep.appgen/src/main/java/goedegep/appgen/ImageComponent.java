package goedegep.appgen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import goedegep.util.objectselector.ObjectSelectionListener;

/**
 * A Component showing an image.
 * If a size is specified, the image will be scaled to the specified size, maintaining the width/height ratio of the image.
 * @author Peter
 *
 */
@SuppressWarnings("serial")
public class ImageComponent extends Component implements ObjectSelectionListener<File> {
  private static final Logger         LOGGER = Logger.getLogger(ImageComponent.class.getName());
  private int           width;
  private int           height;
  private Image img;
  
  public ImageComponent(int width, int height) {
    this.width = width;
    this.height = height;
  }
  
  public ImageComponent(Image img) {
    this.img = img;
    width = img.getWidth(null);
    height = img.getHeight(null);
  }

  public Dimension getPreferredSize() {
    return new Dimension(width, height);
  }

  public void paint(Graphics g) {
    if (img != null) {
      int horizontalMargin = 0;
      int verticalMargin = 0;
      double horizontalScaleFactor = (double) img.getWidth(null) / (double) width;
      LOGGER.fine("horizontalScaleFactor = " + horizontalScaleFactor);
      double verticalScaleFactor = (double) img.getHeight(null) / (double) height;
      LOGGER.fine("verticalScaleFactor = " + verticalScaleFactor);
      
      double scaleFactor = Math.max(horizontalScaleFactor, verticalScaleFactor);
      LOGGER.fine("scaleFactor = " + scaleFactor);
      int usedX = (int) (img.getWidth(null) / scaleFactor);
      LOGGER.fine("usedX = " + usedX);
      horizontalMargin = (width - usedX) / 2;
      LOGGER.fine("horizontalMargin = " + horizontalMargin);
      int usedY = (int) (img.getHeight(null) / scaleFactor);
      LOGGER.fine("usedY = " + usedY);
      verticalMargin = (height - usedY) / 2;
      LOGGER.fine("verticalMargin = " + verticalMargin);
      g.drawImage(img,
          horizontalMargin, verticalMargin, width - horizontalMargin, height - verticalMargin,
          0, 0, img.getWidth(null), img.getHeight(null),
          null);    
    } else {
      g.drawImage(img, 0, 0, null);
      g.setColor(Color.BLACK);
      g.drawString("geen afbeelding", 30, height / 2);
    }
  }

  public void objectSelected(File imageFile) {    
    if (imageFile != null) {
      try {
        img = ImageIO.read(imageFile);
      } catch (IOException e) {
        System.out.println("IO Exception: " + e.getMessage() + ", imageFile: " + imageFile.getAbsolutePath());
      }
    } else {
      img = null;
    }
    repaint();
  }
  
  public static void main(String args[]) throws IOException {
    String imageFileName = "demo.jpg";
    createImageFile(imageFileName);
    JFrame frame = new JFrame("ImageComponent demo");
    
    ImageComponent imageComponent = new ImageComponent(600, 400);
    File imageFile = new File(imageFileName);
    imageComponent.objectSelected(imageFile );
    
    frame.getContentPane().add(imageComponent);
    frame.pack();
    frame.setVisible(true);
    
    Path imageFilePath = Paths.get(imageFileName);
    Files.deleteIfExists(imageFilePath);
  }

  private static void createImageFile(String imageFileName) throws IOException {
    BufferedImage bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_USHORT_555_RGB);
    Graphics2D g2 = bufferedImage.createGraphics();
    
    GradientPaint redToWhite = new GradientPaint(0, 0, Color.RED, 1000, 400 , Color.YELLOW);
    g2.setPaint(redToWhite);
    
    g2.fillArc(20, 20, 900, 900, 0, 360);
    
    File outputFile = new File(imageFileName);
    ImageIO.write(bufferedImage, "jpg", outputFile);
  }
}