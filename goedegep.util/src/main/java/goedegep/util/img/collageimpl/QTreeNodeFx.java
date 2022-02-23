package goedegep.util.img.collageimpl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Logger;

/**
 * This class represents a node in a {@link QTree}.
 *
 */
public class QTreeNodeFx {
  private static final Logger LOGGER = Logger.getLogger(QTreeNodeFx.class.getName());
  private static final NumberFormat NF = new DecimalFormat("000");
  private static final int NUMBER_OF_CHILDREN = 4;

  // Child nodes, either they are all null (in which case the node is a leaf), or all have a value.
  private QTreeNodeFx northWest;
  private QTreeNodeFx northEast;
  private QTreeNodeFx southEast;
  private QTreeNodeFx southWest;

  private QTreeNodeFx parent;

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
  public QTreeNodeFx(int xLoc, int yLoc) {
    this.xLocation = xLoc;
    this.yLocation = yLoc;
  }
  
  public void setParent(QTreeNodeFx parent) {
    this.parent = parent;
  }

  public void setNorthWest(QTreeNodeFx northWest) {
    this.northWest = northWest;
  }


  public void setNorthEast(QTreeNodeFx northEast) {
    this.northEast = northEast;
  }


  public void setSouthEast(QTreeNodeFx southEast) {
    this.southEast = southEast;
  }


  public void setSouthWest(QTreeNodeFx southWest) {
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
  public QTreeNodeFx pickImageNode() {
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
   * Select a child to add the next image to.
   * <p>
   * The child with the least number of points will be selected.
   * 
   * @return the child node of the node, to which the next image is to be added.
   */
  public QTreeNodeFx selectChild() {
    // The children, starting with a random child, but then in fixed order nw, ne, se, sw
    QTreeNodeFx[] children = new QTreeNodeFx[NUMBER_OF_CHILDREN];
    int index = (int) (Math.random() * 3 + 0.5);  // random index in the range 0 .. 3
    LOGGER.info("=> index=" + index);
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
    QTreeNodeFx node = children[0];

    for (int i = 1; i < NUMBER_OF_CHILDREN; i++) {
      if (children[i].picturePoints < node.picturePoints) {
        node = children[i];
      }
    }

    LOGGER.fine("<= node=" + node.toString());
    return node;
  }

  /**
   * Add picture points to this node and all of its parent nodes.
   * 
   * @param points the number of points to be added.
   */
  public void addPointsToNodeAndAllAncestors(int points) {
    picturePoints += points;
    addPointsToParents(points);
  }

  /**
   * Add picture points to the ancestors of this node.
   * <p>
   * If this node has no parent, nothing is done.<br/>
   * Otherwise the points are added to the parent of this node, and this method is recursively called on
   * that parent node.
   * @param points the points to be added.
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

    //      if (picturePoints == 20) {
    //          System.out.print("XXX ");
    //      } else {
    System.out.print(NF.format(picturePoints) + " ");
    //      }
  }
  
  public String toString() {
    return NF.format(picturePoints) + " ";
  }
}
