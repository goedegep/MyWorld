package goedegep.mapview.image.impl;

import java.util.logging.Logger;

import goedegep.mapview.impl.BaseMapAbstract;
import goedegep.mapview.impl.MapTileAbstract;

/**
 *
 */
class MapTile extends MapTileAbstract {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MapTile.class.getName());


  /**
   * Constructor.
   * 
   * @param baseMapAbstract The base map for which this is a map tile.
   * @param myZoom The zoom level of the tile
   * @param i The tile column.
   * @param j The tile row.
   */
  MapTile(BaseMapAbstract<?> baseMapAbstract, int myZoom, long i, long j) {
    super(baseMapAbstract, myZoom, i, j);

    // The Image is loaded synchronously, so we don't have track progress.
    final TileImageView tileImageView = new TileImageView(myZoom, i, j);

    getChildren().add(tileImageView);
    
    setNeedsLayout(true);
        
//    calculatePosition();
  }
}
