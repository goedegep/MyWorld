package goedegep.util.img.collageimpl;


import java.util.logging.Logger;

import goedegep.util.Tuplet;

/**
 * This class represents a QTree, a tree where each non-leaf node has four children.
 * These children represent North-West, North-East, South-East and South-West.
 *
 */
public class QTreeFx {
  private static final Logger LOGGER = Logger.getLogger(QTree.class.getName());
  private static final int IMAGE_POINTS = 20;

  private QTreeNodeFx rootNode;        // The root of the tree
  private int bottomLevel;
  private double minimumImageSize;
  private double borderWidth;
  private double nodeWidth;
  private double borderHeight;
  private double nodeHeight;

  private QTreeNodeFx[][][] qtreeNodeLevels;

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
  public QTreeFx(int depth, int collageWidth, int collageHeight, double imageSize) {
    bottomLevel = depth - 1;
    
    
    // Determine max and min image sizes.
    minimumImageSize = imageSize / 1.15;
    
    // create the qtreeNodeLevels arrays.
    qtreeNodeLevels = new QTreeNodeFx[depth][][];
    int levelSize = 1;
    for (int level = 0; level < depth; level++) {
      qtreeNodeLevels[level] = new QTreeNodeFx[levelSize][levelSize];
      levelSize *= 2;
    }

    // fill the arrays with nodes.
    for (int level = 0; level < qtreeNodeLevels.length; level++) {
      for (int y = 0; y < qtreeNodeLevels[level].length; y++) {
        for (int x = 0; x < qtreeNodeLevels[level][y].length; x++) {
          QTreeNodeFx node = new QTreeNodeFx(x, y);
          qtreeNodeLevels[level][y][x] = node;
        }
      }
    }
    rootNode = qtreeNodeLevels[0][0][0];

    // initialize node hierarchy.
    for (int level = 0; level < qtreeNodeLevels.length - 1; level++) {
      for (int y = 0; y < qtreeNodeLevels[level].length; y++) {
        for (int x = 0; x < qtreeNodeLevels[level][y].length; x++) {
          QTreeNodeFx child;
          QTreeNodeFx parent = qtreeNodeLevels[level][y][x];

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
    LOGGER.info("borderWidth = " + borderWidth);
    double widthToCover = collageWidth - 2 * borderWidth;
    LOGGER.info("widthToCover = " + widthToCover);
    int horizontalNodes = qtreeNodeLevels[bottomLevel][0].length;
    LOGGER.info("horizontalNodes = " + horizontalNodes);
    nodeWidth = widthToCover / (horizontalNodes - 1);
    LOGGER.info("nodeWidth = " + nodeWidth);
    
    borderHeight = 0.6 * Math.sqrt(minimumImageSize);
    LOGGER.info("Qtree: borderHeight = " + borderHeight);
    double heightToCover = collageHeight - 2 * borderHeight;
    LOGGER.info("Qtree: heightToCover = " + heightToCover);
    int verticalNodes = qtreeNodeLevels[bottomLevel].length;
    LOGGER.info("Qtree: verticalNodes = " + verticalNodes);
    nodeHeight = heightToCover / (verticalNodes - 1);
    LOGGER.info("Qtree: nodeHeight = " + nodeHeight);
  }

  /**
   * Add an image to the collage
   * 
   * @param image the image to be added
   */
  public Tuplet<Integer, Integer> addImage() {
    QTreeNodeFx imageNode = rootNode.pickImageNode();
    imageNode.addPointsToNodeAndAllAncestors(IMAGE_POINTS);

    // add points to surrounding nodes.
    double decreaseFactor = 0.6;
    int distance = 1;
    int points = (int) (decreaseFactor * IMAGE_POINTS);
    int xIndex = imageNode.getXLocation();
    int yIndex = imageNode.getYLocation();
    LOGGER.info("Qtree: imageNode xIndex,yIndex = " + xIndex + "," + yIndex);

    while (points > 0) {
      LOGGER.info("point =" + points );
      addPointsToNeighbours(xIndex, yIndex, distance, points);

      distance++;
      points = (int) (decreaseFactor * points);
    }

    // calculate the x,y location of the image.
    int x = (int) (borderWidth + nodeWidth * (xIndex + 0.5));
    int y = (int) (borderHeight + nodeHeight * (yIndex + 0.5));
    LOGGER.info("Qtree: x =" + x + ", y=" + y);
    
    return new Tuplet<>(x, y);
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
    //      System.out.println("Adding " + points + " to " + x + "," + y);
    qtreeNodeLevels[bottomLevel][y][x].addPointsToNodeAndAllAncestors(points);
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
