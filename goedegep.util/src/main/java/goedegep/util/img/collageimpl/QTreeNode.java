package goedegep.util.img.collageimpl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Logger;

/**
 * This class represents a node in a {@link QTree}.
 *
 */
public class QTreeNode {
  private static final Logger LOGGER = Logger.getLogger(QTreeNode.class.getName());
  private static final NumberFormat NF = new DecimalFormat("000");
  private static final int NUMBER_OF_CHILDREN = 4;

  // Child nodes, either they are all null (in which case the node is a leaf), or all have a value.
  private QTreeNode northWest;
  private QTreeNode northEast;
  private QTreeNode southEast;
  private QTreeNode southWest;

  private QTreeNode parent;

  // 'coordinates' at each level. Range from 0,0 to level,level
  private int xLocation;
  private int yLocation;

  long picturePoints = 0;   // Number of points of this node, including the points of all its offspring.

  /**
   * Constructor
   * 
   * @param xLoc the 'x' coordinate.
   * @param yLoc the 'y' coordinate.
   */
  public QTreeNode(int xLoc, int yLoc) {
    this.xLocation = xLoc;
    this.yLocation = yLoc;
  }
  
  public void setParent(QTreeNode parent) {
    this.parent = parent;
  }

  public void setNorthWest(QTreeNode northWest) {
    this.northWest = northWest;
  }


  public void setNorthEast(QTreeNode northEast) {
    this.northEast = northEast;
  }


  public void setSouthEast(QTreeNode southEast) {
    this.southEast = southEast;
  }


  public void setSouthWest(QTreeNode southWest) {
    this.southWest = southWest;
  }


  public int getXLocation() {
    return xLocation;
  }

  public int getYLocation() {
    return yLocation;
  }

  /**
   * Select a node to add the next image to.
   * <p>
   * If this node is a leaf, then this will be the node.
   * Otherwise a child is selected (see {@link #selectChild} and this method is recursively called on that child.
   * 
   * @return the node selected to add the next image to
   */
  public QTreeNode pickImageNode() {
    if (isLeaf()) {
      return this;
    } else {
      return selectChild().pickImageNode();
    }
  }

  /**
   * Check whether this node is a leaf or not.
   * 
   * @return true if the node is a leaf, false otherwise.
   */
  public boolean isLeaf() {
    return northWest == null;
  }

  /**
   * Select a child ta add the next image to.
   * 
   * @return the child node of this node, to which the next image is to be added.
   */
  public QTreeNode selectChild() {
    // The children, starting with a random child, but then in fixed order nw, ne, se, sw
    QTreeNode[] children = new QTreeNode[NUMBER_OF_CHILDREN];
    int index = (int) (Math.random() * 3 + 0.5);  // random index in the range 1 .. 3  TODO shouldn't this be 0 .. 3?
    LOGGER.severe("=> index=" + index);
    children[index++] = northWest;
    if (index == 4)
      index = 0;
    children[index++] = northEast;
    if (index == 4)
      index = 0;
    children[index++] = southEast;
    if (index == 4)
      index = 0;
    children[index] = southWest;


    // simple alg. : select child with least number of points
    QTreeNode node = children[0];

    for (int i = 1; i < NUMBER_OF_CHILDREN; i++) {
      if (children[i].picturePoints < node.picturePoints) {
        node = children[i];
      }
    }

    LOGGER.severe("<= node=" + node.toString());
    return node;
  }

  /**
   * Add picture points to this node and all of its parent nodes.
   * 
   * @param points the number of points to be added.
   */
  public void addPoints(int points) {
    picturePoints += points;
    addPointsToParents(points);
  }

  /**
   * Add picture points to the parents of this node.
   * <p>
   * If this node has no parents, nothing is done.<br/>
   * Otherwise the points are added to the parent of this node, and this method is recursively called on
   * that parent node.
   * @param points
   */
  private void addPointsToParents(int points) {
    if (parent != null) {
      parent.picturePoints += points;
      parent.addPointsToParents(points);
    }
  }

  public void print() {
    System.out.print(xLocation + "," + yLocation + " ");

  }

  public void printPoints() {

    //		if (picturePoints == 20) {
    //			System.out.print("XXX ");
    //		} else {
    System.out.print(NF.format(picturePoints) + " ");
    //		}
  }
  
  public String toString() {
    return NF.format(picturePoints) + " ";
  }
}
