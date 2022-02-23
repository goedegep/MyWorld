package goedegep.appgen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A JPanel showing an image.
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

  private Image img;

  /**
   * Create an image panel, without specifying the size of the panel.
   * @param img The image to be displayed on the panel.
   */
  public ImagePanel(Image img) {
    this.img = img;
  }

  /**
   * Create an image panel, without specifying the size of the panel.
   * @param img The image to be displayed on the panel.
   */
  public ImagePanel(Image img, boolean setPanelSizeToImageSize) {
    this.img = img;
    
    if (setPanelSizeToImageSize) {
      Dimension panelSize = new Dimension(img.getWidth(null), img.getHeight(null));
      setPreferredSize(panelSize);
      setMinimumSize(panelSize);
      setMaximumSize(panelSize);
    }
  }

  /**
   * Create an image panel, with a specific panel size.
   * @param img The image to be displayed on the panel.
   * @param panelSize the size of the panel.
   */
  public ImagePanel(Image img, Dimension panelSize) {
    this.img = img;
    setPreferredSize(panelSize);
    setMinimumSize(panelSize);
    setMaximumSize(panelSize);
  }

  public void paintComponent(Graphics g) {
    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
  }
  
  public static void main(String args[]) throws IOException {
    Image image = createImage();
    JFrame frame = new JFrame("ImagePanel demo");
    
    ImagePanel imagePanel = new ImagePanel(image);
    frame.getContentPane().add(imagePanel, BorderLayout.NORTH);
    
    imagePanel = new ImagePanel(image, true);
    frame.getContentPane().add(imagePanel, BorderLayout.CENTER);
    
    imagePanel = new ImagePanel(image, new Dimension(700, 300));
    frame.getContentPane().add(imagePanel, BorderLayout.SOUTH);
    
    frame.pack();
    frame.setVisible(true);
  }

  private static Image createImage() throws IOException {
    BufferedImage bufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_USHORT_555_RGB);
    Graphics2D g2 = bufferedImage.createGraphics();
    
    GradientPaint redToWhite = new GradientPaint(0, 0, Color.RED, 300, 200 , Color.YELLOW);
    g2.setPaint(redToWhite);
    
    g2.fillArc(20, 20, 300, 300, 0, 360);
    
    return bufferedImage;
  }

}
