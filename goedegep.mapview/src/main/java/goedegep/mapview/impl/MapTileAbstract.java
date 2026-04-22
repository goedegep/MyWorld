package goedegep.mapview.impl;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;

/**
 * This class represents a single tile of the map.The class contains the tile's zoom level and its column and row indices (i, j) in the tile grid.
 */
public abstract class MapTileAbstract extends Region {
  private static final Logger LOGGER = Logger.getLogger(MapTileAbstract.class.getName());

  /**
   * The zoom level of the tile (which may differ from the current zoom level of the map display).
   */
  private final int tileZoom;
  
  /**
   * The tile column.
   */
  public final long i;
  
  /**
   * The tile row.
   */
  public final long j;

  /**
   * The base map for which this is a map tile.
   * In this class we don't need any specific MapTile.
   */
  protected final BaseMapAbstract<?> baseMapAbstract;

  /**
   * In most cases, a tile will be shown scaled. The value for the scale
   * factor depends on the active zoom and the tile-specific myZoom.
   * Note that covering tiles will have a different scale than the 'normal' tiles. Therefore the scale cannot be at base map level. 
   */
  protected final Scale scale = new Scale();
  
  
  /**
   * Constructor.
   * 
   * @param baseMapAbstract The base map for which this is a map tile.
   * @param tileZoom The zoom level of the tile
   * @param i The tile column.
   * @param j The tile row.
   */
  public MapTileAbstract(BaseMapAbstract<?> baseMapAbstract, int tileZoom, long i, long j) {
    this.baseMapAbstract = baseMapAbstract;
    this.tileZoom = tileZoom;
    this.i = i;
    this.j = j;
    
    // Pivot point for scaling is the top left corner of the tile.
    scale.setPivotX(0);
    scale.setPivotY(0);
    getTransforms().add(scale);
  }

  /**
   * Recalculates the position of this tile (translateX, translateY). The position depends on the current zoom level (and translation of the base map).
   * TODO: rename to calculatePositionAndVisible or remove visibility
   */
  public void calculatePosition() {
    LOGGER.severe("=> calculating position for " + this);
    double mapZoom = baseMapAbstract.zoom().get();
//    LOGGER.info("Visible: " + isVisible());
//    if (baseMapAbstract.getChildren().contains(this)) {
//      LOGGER.info("Tile is in scene graph");
//    } else {
//      LOGGER.severe("Tile is NOT in scene graph");
//    }
//    int visibleWindow = (int) floor(mapZoom + BaseMapAbstract.TIPPING);
//    boolean visible =  visibleWindow == myZoom ||
//        isCovering() ||
//        ((visibleWindow >= BaseMap.MAX_ZOOM) && (myZoom == BaseMap.MAX_ZOOM - 1));
//    this.setVisible(visible);
//    LOGGER.fine("visible tile " + this + "? " + this.isVisible() + (this.isVisible() ? " covering? " + isCovering() : ""));
    double sf = Math.pow(2, mapZoom - tileZoom);
    scale.setX(sf);
    scale.setY(sf);
    double translateX = 256 * i * sf;  //  + baseMapAbstract.translateXProperty().get()
    double translateY = 256 * j * sf;  // + baseMapAbstract.translateYProperty().get()
//    LOGGER.severe("TILE: translateX = " + translateX + ", translateY = " + translateY);
    setTranslateX(translateX);
    setTranslateY(translateY);
    LOGGER.severe("<= BASE_MAP: translateX = " + baseMapAbstract.translateXProperty().get() + ", translateY = " + baseMapAbstract.translateYProperty().get());
  }
  
  /**
   * Get the zoom level of this tile (which may differ from the current zoom level of the map display).
   * 
   * @return the zoom level of the tile.
   */
  public int getTileZoom() {
    return tileZoom;
  }

  /**
   * Recalculates the position of this tile. The position depends on the current zoom level and translation of the base map.
   */
  private void calculatePosition2() {
    double currentZoom = baseMapAbstract.zoom().get();
//    int visibleWindow = (int) floor(currentZoom + BaseMapAbstract.TIPPING);
//    boolean visible =  visibleWindow == myZoom ||
//        ((visibleWindow >= BaseMap.MAX_ZOOM) && (myZoom == BaseMap.MAX_ZOOM - 1));
//    this.setVisible(visible);
//    LOGGER.fine("visible tile " + this + "? " + this.isVisible());
    double sf = Math.pow(2, currentZoom - tileZoom);
    scale.setX(sf);
    scale.setY(sf);
    setTranslateX(256 * i * sf);
    setTranslateY(256 * j * sf);
  }

  @Override
  public String toString() {
//    return "tile with z = " + myZoom + " [" + i + "," + j + "]";
    return tileZoom + " [" + i + "," + j + "]";
  }
}
