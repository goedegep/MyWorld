package goedegep.mapview.view.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.mapview.impl.BaseMapAbstract;
import goedegep.mapview.impl.MapTileAbstract;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.ReadOnlyDoubleProperty;

/**
 *
 */
class MapTile extends MapTileAbstract {
  private static final Logger LOGGER = Logger.getLogger(MapTile.class.getName() );
  
  /**
   * The loading progress. 1.0 indicates fully loaded.
   */
  public ReadOnlyDoubleProperty progress;
  
  /**
   *  A list of tiles that this tile is covering. In case the covered tiles are 
   *  not yet loaded, this tile will be rendered.
   */
  protected final List<MapTileAbstract> coveredTiles = new LinkedList<>();

  private final InvalidationListener zl = _ -> calculatePosition();

  /**
   * Constructor.
   * 
   * @param baseMapAbstract The base map for which this is a map tile.
   * @param myZoom The zoom level of the tile
   * @param i The tile column.
   * @param j The tile row.
   */
  MapTile(BaseMapAbstract<?> baseMapAbstract, int myZoom, long i, long j) {
    LOGGER.severe("=> Creating tile: " + myZoom + " [i = " + i + ", j]" + j);
    super(baseMapAbstract, myZoom, i, j);

    final TileImageView tileImageView = new TileImageView(myZoom, i, j);
    tileImageView.exceptionProperty().addListener((_, _, nv) -> LOGGER.severe("Error: " + nv.getMessage()));
    tileImageView.setMouseTransparent(true);
    
    progress = tileImageView.progressProperty();

    getChildren().add(tileImageView);
    
    if (progress.get() == 1.0) {
      LOGGER.info("Already got image [" + myZoom + "], i = " + i + ", j = " + j);
      setNeedsLayout(true);
    } else {
      progress.addListener(new InvalidationListener() {
        @Override
        public void invalidated(Observable observable) {
          if (progress.get() >= 1.0d) {
            LOGGER.info("Got image [" + myZoom + "], i = " + i + ", j = " + j);
            setNeedsLayout(true);
            progress.removeListener(this);
          }
        }
      });
    }
    
    // If the zoom level (or the translation of the base map changes), we need to recalculate the position of this tile.
    baseMapAbstract.zoom().addListener(new WeakInvalidationListener(zl));
//    baseMapAbstract.translateXProperty().addListener(new WeakInvalidationListener(zl));
//    baseMapAbstract.translateYProperty().addListener(new WeakInvalidationListener(zl));
    
    calculatePosition();
    this.setMouseTransparent(true);
    
    LOGGER.severe("<= Tile created: " + myZoom + " [i = " + i + ", j]" + j);
  }

  /**
   * Indication that the tile is loading.
   * 
   * @return true if the tile is loading, false otherwise.
   */
  public boolean loading() {
    return progress.get() < 1.0;
  }
  
  /**
   * Check whether this tile is covering any tiles.
   * 
   * @return true if this tile is covering one or more tiles, false otherwise.
   */
  public boolean isCovering() {
      return coveredTiles.size() > 0;
  }

  /**
   * This tile is covering for the child tile that is still being loaded.
   *
   * @param child
   */
  public void addCovering(MapTile child) {
    coveredTiles.add(child);
    InvalidationListener il = createProgressListener(child);
    //        System.out.println("We have to cover, add "+il);
    child.progress.addListener(il);
    calculatePosition();
  }

  private InvalidationListener createProgressListener(MapTile child) {
    return new InvalidationListener() {
      @Override
      public void invalidated(Observable o) {
        if (child.progress.get() >= 1.0d) {
          MapTile.this.coveredTiles.remove(child);
          child.progress.removeListener(this);
        }
      }
    };
  }
}
