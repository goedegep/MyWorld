package goedegep.mapview.view.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.mapview.impl.BaseMapAbstract;
import goedegep.mapview.impl.MapViewCommon;

/**
 *
 * The BaseMap provides the underlying maptiles.
 * On top of this, additional layers can be rendered.
 */
public class BaseMap extends BaseMapAbstract<TileImageView> {
  private static final Logger LOGGER = Logger.getLogger( BaseMap.class.getName() );
    

  /**
   * Constructor.
   * 
   * @param mapView the map view for which this is the base map.
   */
  public BaseMap(MapViewCommon mapView) {
    super(mapView);
  }

  /**
   * Update the set of tiles.
   */
  public void loadTiles() {
    int tileZoom = getTileZoomLevel();
    long i_max = 1 << tileZoom;
    long j_max = 1 << tileZoom;
    double tx = getTranslateX();
    double ty = getTranslateY();
    double width = mapViewCommon.getDimensions().getWidth();
    double height = mapViewCommon.getDimensions().getHeight();
    double actualTileSize = 256 * getTileScaleFactor(tileZoom);
    long imin = Math.max(0, (long) Math.floor(-tx / actualTileSize));  // Number of tiles left of the left edge of the visible area. We need the next tile, so we would need to add 1, but as the index start at 0, we don't need to add 1.
    long imax = (long) Math.ceil((-tx + width) / actualTileSize);      // Number of the last tile needed. As the index starts at 0, imax is actually the index of the first tile that is not needed.
    imax = Math.max(imin + 1, imax);                        // imax should be at least one more than imin.
    imax = Math.min(i_max, imax);                           // imax should not be higher than the maximum tile index.
    
    long jmin = Math.max(0, (long) Math.floor(-ty / actualTileSize));
    long jmax = (long) Math.ceil((-ty + height) / actualTileSize);
    jmax = Math.max(jmin + 1, jmax);
    jmax = Math.min(j_max, jmax);
    
    List<TileImageView> tilesNeeded = new ArrayList<>();
    for (long i = imin; i < imax; i++) {
      for (long j = jmin; j < jmax; j++) {
        Long key = createTileKey(i, j, tileZoom);
        TileImageView tile = tiles[tileZoom].get(key);
        if ((tile == null)) {
          tile = new TileImageView(this, tileZoom, i, j);
          tilesNeeded.add(tile);
          tiles[tileZoom].put(key, tile);
          
          // If the tile is still loading, we try to find a covering tile to show in the meantime.
//          if (tile.progress.get() < 1.0) {
            TileImageView covering = getCoveringTile(tile);
            if (covering != null) {
              tilesNeeded.add(covering);
              covering.addCovering(tile);
              if (!getChildren().contains(covering)) {
                getChildren().add(covering);
              }
            }
//          }

          getChildren().add(tile);
        } else {
          tilesNeeded.add(tile);
          if (!getChildren().contains(tile)) {
            getChildren().add(tile);
          }
        }
      }
    }
    StringBuilder buf = new StringBuilder();
    for (TileImageView mt: tilesNeeded) {
      buf.append(", ").append(mt);
    }
//    LOGGER.severe("Tiles needed: " + buf.toString());
    
    List<TileImageView> currentTiles = new ArrayList<>();
    List<TileImageView> tilesToDelete = new ArrayList<>();
    for (Object o: getChildren()) {
      if (o instanceof TileImageView mt) {
        currentTiles.add(mt);
        if (!tilesNeeded.contains(mt)) {
          tilesToDelete.add(mt);
        }
      }
    }
    buf = new StringBuilder();
    for (TileImageView mt: currentTiles) {
      buf.append(", ").append(mt);
    }
//    LOGGER.severe("Current Tiles: " + buf.toString());
    buf = new StringBuilder();
    for (TileImageView mt: tilesToDelete) {
      buf.append(", ").append(mt);
    }
//    LOGGER.severe("Tiles to delete: " + buf.toString());
    getChildren().removeAll(tilesToDelete);

    cleanupTileCache(tileZoom, imin, imax, jmin, jmax);
    
//    LOGGER.severe("Tiles after cleanup:");
    for (Object o: getChildren()) {
      if (o instanceof TileImageView mt) {
//        LOGGER.severe("Tile in view: " + mt);
      }
    }
    
//    cleanupDiskTileCache();
//    LOGGER.severe("<= loadTiles");
  }
  
  private void cleanupTileCache(int zoom, long iMin, long iMax, long jMin, long jMax) {
    
    // Only clean this zoom level and lower zoom levels.
    
//    long totalTilesInCache = 0;
    
    // Keep levels 0 up to 5 in cache.
    for (int z = zoom; z > 6; z--) {
      Map<Long, TileImageView> tileMap = tiles[z];
      
      long iFirstKeep= iMin - 5;
      long iLastKeep= iMax + 5;
      long jFirstKeep= jMin - 5;
      long jLastKeep= jMax + 5;
      
//      totalTilesInCache += tileMap.size();
            
      List<Long> keysToRemove = new ArrayList<>();
      for (long key: tileMap.keySet()) {
        long i = getIFromTileKey(key, z);
        long j = getJFromTileKey(key, z);
//        MapTile mapTile = tileMap.get(key).get();
//        LOGGER.severe("z = " + z + ", i = " + i + ", j = " + j + ", mapTile = " + (mapTile != null ? "Set" : "Not set"));
        if ((i < iFirstKeep) || (i > iLastKeep) || (j < jFirstKeep) || (j > jLastKeep)) {
//          LOGGER.severe("Removing tile from cache, z = " + z + ", i = " + i + ", j = " + j);
          keysToRemove.add(key);
        }
      }
      for (long key: keysToRemove) {
        tileMap.remove(key);
      }
      
      iMin = iMin / 2;
      iMax = iMax / 2;
      jMin = jMin / 2;
      jMax = jMax / 2;
    }

//    LOGGER.severe("Total tiles in cache: " + totalTilesInCache);
  }
  
  /**
   * Generate a key for the tile with the specified i, j and z. The key is generated in such a way that it can be easily decoded to get the i and j values back.
   * 
   * @param i the i (column) value of the tile
   * @param j the j (row) value of the tile
   * @param z the zoom level of the tile
   * @return a key for the tile with the specified i, j and z
   */
  private long createTileKey(long i, long j, int z) {
    long i_max = 1 << z;
    return i * i_max + j;
  }
  
  /**
   * Get the i value from the tile key for the specified zoom level.
   * 
   * @param key the tile key
   * @param z the zoom level of the tile
   * @return the i value for the tile with the specified key and zoom level
   */
  private long getIFromTileKey(long key, int z) {
    long i_max = 1 << z;
    return key / i_max;
  }
  
  /**
   * Get the j value from the tile key for the specified zoom level.
   * 
   * @param key the tile key
   * @param z the zoom level of the tile
   * @return the j value for the tile with the specified key and zoom level
   */
  private long getJFromTileKey(long key, int z) {
    long i_max = 1 << z;
    long jMask = i_max - 1;
    return key & jMask;
  }

  /**
   * Get the tile that covers the specified tile. This is used to find out what tile we have to show while a new tile is still loading.
   * 
   * @param tile the tile for which we want to find a covering tile
   * @return the covering tile, or null if no covering tile is available
   */
  private TileImageView getCoveringTile(TileImageView tile) {
    int z = tile.getTileZoom();
    if (z > 0) {
      long pi = tile.i / 2;
      long pj = tile.j / 2;
      long i_max = 1 << (z - 1);
      Long key = pi * i_max + pj;
      // LongTuple it = new LongTuple(i,j);
      TileImageView candidate = tiles[z - 1].get(key);
      if (candidate != null) {
        return candidate;
      }
    }
    return null;
  }


  /**
   * Find the "nearest" lower-zoom tile that covers a specific tile. This is
   * used to find out what tile we have to show while a new tile is still
   * loading
   *
   * @param zoom
   * @param i
   * @param j
   * @return the lower-zoom tile which covers the specified tile
   */
  protected TileImageView findCovering(int zoom, long i, long j) {
    while (zoom > 0) {
      zoom--;
      i = i / 2;
      j = j / 2;
      TileImageView candidate = (TileImageView) findTile(zoom, i, j);
      if ((candidate != null) && (!candidate.loading())) {
        return candidate;
      }
    }
    return null;
  }

}
