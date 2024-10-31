package goedegep.util.img.collageimpl;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.logging.Logger;

/**
 * This class represents a QTree, a tree where each non-leaf node has four children.
 * These children represent North-West, North-East, South-East and South-West.
 *
 */
public class QTree {
  private static final Logger LOGGER = Logger.getLogger(QTree.class.getName());
  private static final int IMAGE_POINTS = 20;

  private QTreeNode rootNode;        // The root of the tree
  private Graphics2D graphics2D;
  private int bottomLevel;
  private double minimumImageSize;
  private double maximumImageSize;
  private double borderWidth;
  private double nodeWidth;
  private double borderHeight;
  private double nodeHeight;

  private QTreeNode[][][] qtreeNodeLevels;

  /**
   * Constructor
   * <p>
   * Create a QTree with a specific depth.<br/>
   * The 'average' image size can be specified. The actual images vary from 15% smaller to 15% bigger.<br/>
   * The collage starts with a black background, which may remain partly visible if not enough images are added
   * to the collage.
   * 
   * @param depth the depth of the tree
   * @param collage the collage image to draw
   * @param imageSize the width and height of an image
   */
  public QTree(int depth, Image collage, double imageSize) {
    graphics2D = (Graphics2D) collage.getGraphics();
    bottomLevel = depth - 1;
    
    
    // Determine max and min image sizes.
    minimumImageSize = imageSize / 1.15;
    maximumImageSize = 1.15 * imageSize;
    
    // Start with a black background.
    graphics2D.setColor(new Color(0, 0, 0));
    graphics2D.fillRect(0, 0, collage.getWidth(null), collage.getHeight(null));

    // create the qtreeNodeLevels arrays.
    qtreeNodeLevels = new QTreeNode[depth][][];
    int levelSize = 1;
    for (int level = 0; level < depth; level++) {
      qtreeNodeLevels[level] = new QTreeNode[levelSize][levelSize];
      levelSize *= 2;
    }

    // fill the arrays with nodes.
    for (int level = 0; level < qtreeNodeLevels.length; level++) {
      for (int y = 0; y < qtreeNodeLevels[level].length; y++) {
        for (int x = 0; x < qtreeNodeLevels[level][y].length; x++) {
          QTreeNode node = new QTreeNode(x, y);
//          node.xLoc = x;
//          node.yLoc = y;
          qtreeNodeLevels[level][y][x] = node;
        }
      }
    }
    rootNode = qtreeNodeLevels[0][0][0];

    // initialize node hierarchy.
    for (int level = 0; level < qtreeNodeLevels.length - 1; level++) {
      for (int y = 0; y < qtreeNodeLevels[level].length; y++) {
        for (int x = 0; x < qtreeNodeLevels[level][y].length; x++) {
          QTreeNode child;
          QTreeNode parent = qtreeNodeLevels[level][y][x];

          child = qtreeNodeLevels[level + 1][2*y + 0][2*x + 0];
          parent.setNorthWest(child);
          child.setParent(parent);

          child = qtreeNodeLevels[level + 1][2*y + 0][2*x + 1];
          parent.setNorthEast(child);
          child.setParent(parent);

          child = qtreeNodeLevels[level + 1][2*y + 1][2*x + 1];
          parent.setSouthEast(child);
          child.setParent(parent);

          child = qtreeNodeLevels[level + 1][2*y + 1][2*x + 0];
          parent.setSouthWest(child);
          child.setParent(parent);
        }
      }
    }
    
    // General values for x,y location calculation
    borderWidth = 0.6 * Math.sqrt(minimumImageSize);
    LOGGER.severe("borderWidth = " + borderWidth);
    double widthToCover = collage.getWidth(null) - 2 * borderWidth;
    LOGGER.severe("widthToCover = " + widthToCover);
    int horizontalNodes = qtreeNodeLevels[bottomLevel][0].length;
    LOGGER.severe("horizontalNodes = " + horizontalNodes);
    nodeWidth = widthToCover / (horizontalNodes - 1);
    LOGGER.severe("nodeWidth = " + nodeWidth);
    
    borderHeight = 0.6 * Math.sqrt(minimumImageSize);
    LOGGER.severe("Qtree: borderHeight = " + borderHeight);
    double heightToCover = collage.getHeight(null) - 2 * borderHeight;
    LOGGER.severe("Qtree: heightToCover = " + heightToCover);
    int verticalNodes = qtreeNodeLevels[bottomLevel].length;
    LOGGER.severe("Qtree: verticalNodes = " + verticalNodes);
    nodeHeight = heightToCover / (verticalNodes - 1);
    LOGGER.severe("Qtree: nodeHeight = " + nodeHeight);
  }

  /**
   * Add an image to the collage
   * 
   * @param image the image to be added
   */
  public void addImage(Image image) {
    QTreeNode imageNode = rootNode.pickImageNode();
    imageNode.addPoints(IMAGE_POINTS);

    // add points to surrounding nodes.
    double decreaseFactor = 0.6;
    int distance = 1;
    int points = (int) (decreaseFactor * IMAGE_POINTS);
    int xIndex = imageNode.getXLocation();
    int yIndex = imageNode.getYLocation();
//    System.out.println("Qtree: imageNode x,y = " + xIndex + "," + yIndex);

    while (points > 0) {
//      System.out.println("points = " + points );
      addPointsToNeighbours(xIndex, yIndex, distance, points);

      distance++;
      points = (int) (decreaseFactor * points);
    }

    // calculate the x,y location of the image.
    int x = (int) (borderWidth + nodeWidth * xIndex);
//    System.out.println("Qtree: x = " + x);
    int y = (int) (borderHeight + nodeHeight * yIndex);
//    System.out.println("Qtree: y = " + y);
    
    drawTheImage(addTransparancyToImage(image), x, y);

//    printPoints();
  }
  
  private Image addTransparancyToImage(Image image) {
    int imageWidth = image.getWidth(null);
    int imageHeight = image.getHeight(null);
    BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    
    Graphics2D g2d = (Graphics2D) bi.getGraphics();
    int margin = 0;
    int marginStep = 1;
    drawTransparantImageFrame(g2d, image, imageWidth, imageHeight, margin, marginStep, 0.3f);
    margin += marginStep;
    drawTransparantImageFrame(g2d, image, imageWidth, imageHeight, margin, marginStep, 0.4f);
    margin += marginStep;
    drawTransparantImageFrame(g2d, image, imageWidth, imageHeight, margin, marginStep, 0.5f);
    margin += marginStep;
    drawTransparantImageFrame(g2d, image, imageWidth, imageHeight, margin, marginStep, 0.6f);
    margin += marginStep;
    drawTransparantImageFrame(g2d, image, imageWidth, imageHeight, margin, marginStep, 0.7f);
    margin += marginStep;
    drawTransparantImageFrame(g2d, image, imageWidth, imageHeight, margin, marginStep, 0.8f);
    margin += marginStep;

    AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
    g2d.setComposite(composite);
    g2d.drawImage(image, margin, margin, image.getWidth(null) - margin, image.getHeight(null) - margin, margin, margin, image.getWidth(null) - margin, image.getHeight(null) - margin, null);
        
    return bi;
  }
  
  private void drawTransparantImageFrame(Graphics2D g2d, Image image, int imageWidth, int imageHeight, int margin, int frameSize, float alpha) {
    AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
    g2d.setComposite(composite);
    int x;
    int y;
    int drawWidth;
    int drawHeight;
    
    // Top
    x = margin;
    y = margin;
    drawWidth = imageWidth - 2 * margin;
    drawHeight = frameSize;
    g2d.drawImage(image, x, y, x + drawWidth, y + drawHeight, x, y, x + drawWidth, y + drawHeight, null);
    
    // Bottom
    y = imageHeight - margin - 1 - frameSize;
    g2d.drawImage(image, x, y, x + drawWidth, y + drawHeight, x, y, x + drawWidth, y + drawHeight, null);
    
    // Left
    y = margin + frameSize;
    drawWidth = frameSize;
    drawHeight = imageHeight - 2 * margin - 2 * frameSize - 1;
    g2d.drawImage(image, x, y, x + drawWidth, y + drawHeight, x, y, x + drawWidth, y + drawHeight, null);
    
    // Right
    x = imageWidth - margin - frameSize ;
    g2d.drawImage(image, x, y, x + drawWidth, y + drawHeight, x, y, x + drawWidth, y + drawHeight, null);
  }

  private void drawTheImage(Image image, int x, int y) {
    AffineTransform saveAffineTransform = graphics2D.getTransform();
    
    AffineTransform at = new AffineTransform();
    
    // handle scaling
    long imageSize = (long) Math.sqrt(image.getWidth(null) * image.getHeight(null));
    Double scaleFactor = scaleIfNeeded(imageSize);
    double halfImageWidth = image.getWidth(null) / 2;
    double halfImageHeight = image.getHeight(null) / 2;

    if (scaleFactor != null) {
      halfImageWidth *= scaleFactor;
      halfImageHeight *= scaleFactor;
    }
    
    double xCenter = x - halfImageWidth;
    double yCenter = y - halfImageHeight;

    at.translate(xCenter, yCenter);
    if (scaleFactor != null) {
      at.scale(scaleFactor, scaleFactor);
    }
    

    // add random rotation
    addRandomRotation(at);
    graphics2D.transform(at);
    
    // Change color and transparancy
    float colorScaleFactor = 0.5f;
    float[] scales = {colorScaleFactor, colorScaleFactor, colorScaleFactor, 0.95f };
    float[] offsets = new float[4];
    RescaleOp rop = new RescaleOp(scales, offsets, null);
    graphics2D.drawImage((BufferedImage) image, rop, 0, 0);
    
    graphics2D.setTransform(saveAffineTransform);
  }
  
  private Double scaleIfNeeded(long imageSize) {
    Double scaleFactor = scaleUpIfNeeded(imageSize);
    if (scaleFactor == null) {
      scaleFactor = scaleDownIfNeeded(imageSize);
    }
    return scaleFactor;
  }
  
  private Double scaleUpIfNeeded(long imageSize) {
    Double scaleFactor = null;
    
    if (imageSize < minimumImageSize) {
      scaleFactor = minimumImageSize / imageSize;
//      System.out.println("Qtree: ScaleUp scaleFactor = " + scaleFactor);
    }
    
    return scaleFactor;
  }
  
  private Double scaleDownIfNeeded(long imageSize) {
    Double scaleFactor = null;
    
    if (imageSize > maximumImageSize) {
      scaleFactor = maximumImageSize / imageSize;
//      System.out.println("Qtree: ScaleDown scaleFactor = " + scaleFactor);
    }
    
    return scaleFactor;
  }
  
  private void addRandomRotation(AffineTransform at) {
    double value = (Math.random() - 0.5) * 20;
    if (value > -6.0  && value < 6.0) {
      at.rotate(Math.toRadians(5 * value));
    }
  }

  private void addPointsToNeighbours(int xCenter, int yCenter, int distance, int points) {
    for (int xOffset = -distance; xOffset < distance; xOffset++) {
      addPointsToNeighbour(xCenter, yCenter, xOffset, -distance, points);
    }
    for (int yOffset = -distance; yOffset < distance; yOffset++) {
      addPointsToNeighbour(xCenter, yCenter, distance, yOffset, points);
    }
    for (int xOffset = distance; xOffset > -distance; xOffset--) {
      addPointsToNeighbour(xCenter, yCenter, xOffset, distance, points);
    }
    for (int yOffset = distance; yOffset > -distance; yOffset--) {
      addPointsToNeighbour(xCenter, yCenter, -distance, yOffset, points);
    }
  }

  private void addPointsToNeighbour(int xCenter, int yCenter, int xOffset, int yOffset, int points) {
    int x = xCenter + xOffset;
    int y = yCenter + yOffset;

    if (x < 0) return;
    if (y < 0) return;
    if (y > qtreeNodeLevels[bottomLevel].length - 1) return;
    if (x > qtreeNodeLevels[bottomLevel][y].length - 1) return;
    //		System.out.println("Adding " + points + " to " + x + "," + y);
    qtreeNodeLevels[bottomLevel][y][x].addPoints(points);
  }

  public void print() {
    for (int level = 0; level < qtreeNodeLevels.length; level++) {
      System.out.println("Level = " + level);
      for (int y = 0; y < qtreeNodeLevels[level].length; y++) {
        for (int x = 0; x < qtreeNodeLevels[level][y].length; x++) {
          qtreeNodeLevels[level][y][x].print();
        }
        System.out.println();
      }
      System.out.println();
    }

  }

  public void printPoints() {
    for (int level = 0; level < qtreeNodeLevels.length; level++) {
      System.out.println("Level = " + level);
      for (int y = 0; y < qtreeNodeLevels[level].length; y++) {
        for (int x = 0; x < qtreeNodeLevels[level][y].length; x++) {
          qtreeNodeLevels[level][y][x].printPoints();
        }
        System.out.println();
      }
      System.out.println();
    }
  }
}
