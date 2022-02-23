package goedegep.appgen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * A cell renderer for a list of images.
 * The images are shown horizontally and centered in the cell.
 * The images are identified by their filename.
 * Names starting with a '#' sign, are reserved names.<br>
 * Pre-defined images:<br>
 * <ul>
 * <li>#IMAGE_ONE - a simple CD representation.</li>
 * </ul>
 */
@SuppressWarnings("serial")
public class ImageListCellRenderer extends DefaultTableCellRenderer {
  private static final Logger     LOGGER = Logger.getLogger(ImageListCellRenderer.class.getName());

  private String toolTipText;
  private String albumInfoDirectory;
  private Dimension imageDimension;

  public ImageListCellRenderer(String toolTipText, String albumInfoDirectory, Dimension imageDimension) {
    super();
    this.toolTipText = toolTipText;
    this.albumInfoDirectory = albumInfoDirectory;
    this.imageDimension = imageDimension;
  }
  
  @SuppressWarnings("unchecked")
  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column){
    List<String>  imageFileNames = new ArrayList<String>();
    
    if (value != null) {
      imageFileNames.addAll((List<String>) value);
    }
    if (imageFileNames.isEmpty()) {
      imageFileNames.add("#IMAGE_ONE");
    }
    
    if (toolTipText != null) {
      setToolTipText(toolTipText);
    }
    
    Image image = null;
    JPanel panel = new JPanel();
    for (String imageFileName: imageFileNames) {
      if (imageFileName.startsWith("#")) {
        switch (imageFileName) {
        case "#IMAGE_ONE":
          image = createImageOne();
          ImagePanel imagePanel = new ImagePanel(image, imageDimension);
          panel.add(imagePanel);
          LOGGER.fine("Adding #IMAGE_ONE");
          break;
          
        default:
          throw new IllegalArgumentException("Unknown image: " + imageFileName);
        }
      } else {
        File imageFile = new File(albumInfoDirectory + "\\" + imageFileName);
        LOGGER.fine("Going to read image from file: " + imageFile.getAbsolutePath());
        try {
          image = ImageIO.read(imageFile);
          ImagePanel imagePanel = new ImagePanel(image, imageDimension);
          panel.add(imagePanel);
        } catch (IOException e) {
          LOGGER.severe("Fout bij het lezen van de afbeelding: \"" + imageFile.getAbsolutePath() + "\", systeem foutmelding: " + e.getMessage());
        }
      }
    }
    
    return panel;
  }

  private Image createImageOne() {
    BufferedImage bufferedImage = new BufferedImage(imageDimension.width, imageDimension.height, BufferedImage.TYPE_USHORT_555_RGB);
    Graphics2D g2 = bufferedImage.createGraphics();
    g2.setColor(Color.WHITE);
    g2.fillRect(0, 0, imageDimension.width, imageDimension.height);
    g2.setColor(Color.BLUE);
    g2.drawRect(2, 2, imageDimension.width - 5, imageDimension.height - 5);
    g2.setColor(Color.LIGHT_GRAY);
    g2.fillOval(3, 3, imageDimension.width - 7, imageDimension.height - 7);
    g2.setColor(Color.BLACK);
    g2.fillOval(imageDimension.width / 2 - 2, imageDimension.height / 2 - 2, 4, 4);
    
    return bufferedImage;
  }
}
