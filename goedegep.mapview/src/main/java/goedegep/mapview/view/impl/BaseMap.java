package goedegep.mapview.view.impl;

import static java.lang.Math.floor;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.mapview.impl.BaseMapAbstract;
import goedegep.mapview.impl.MapTileAbstract;
import goedegep.mapview.impl.MapTileImageViewAbstract;
import goedegep.mapview.impl.MapViewCommon;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;

/**
 *
 * The BaseMap provides the underlying maptiles.
 * On top of this, additional layers can be rendered.
 */
public class BaseMap extends BaseMapAbstract<MapTileImageView> {
  private static final Logger LOGGER = Logger.getLogger( BaseMap.class.getName() );

//  /**
//   * This {@code Rectangle} is used to react to changes in the width and height and
//   * is used to determine which tiles are currently visible.
//   * Its width and height are bound to the parent layout bounds,
//   * and its translation (x, y) is bound to the negative translation of this map.
//   */
//  private final Rectangle area;
  
  //    private double zoomValue;

  /**
   * This listener is used to react to changes in the width and height of the parent layout bounds.
   */
  private final ChangeListener<Number> resizeListener = (_, _, newValue) -> {
    /**
     * Don't do anything if there's no size.
     * 
     */
    if (mapViewCommon.getWidth() == 0 || mapViewCommon.getHeight() == 0) {
      return;
    }
    if (!initialized) {
      doSetCenter(mapViewCommon.center.get());
    }
    markDirty();
  };
    
  private ChangeListener<Scene> sceneListener;


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
    LOGGER.severe("=>");

    int nearestZoom = (Math.min((int) floor(mapViewCommon.getZoom() + TIPPING), NUMBER_OF_ZOOM_LEVELS - 1));
    double activeZoom = mapViewCommon.getZoom();
//    if (activeZoom >= 2) {
//      LOGGER.severe("Zoom = " + nearestZoom + ", activeZoom = " + activeZoom + ", tx = " + getTranslateX() + ", ty = " + getTranslateY());  
//    }
    long i_max = 1 << nearestZoom;
    long j_max = 1 << nearestZoom;
    double tx = getTranslateX();
    double ty = getTranslateY();
    double width = mapViewCommon.getWidth();
    double height = mapViewCommon.getHeight();
    long imin = Math.max(0, (long) Math.floor(-tx / 256));  // Number of tiles left of the left edge of the visible area. We need the next tile, so we would need to add 1, but as the index start at 0, we don't need to add 1.
    long imax = (long) Math.ceil((-tx + width) / 256);      // Number of the last tile needed. As the index starts at 0, imax is actually the index of the first tile that is not needed.
    imax = Math.max(imin + 1, imax);                        // imax should be at least one more than imin.
    imax = Math.min(i_max, imax);                           // imax should not be higher than the maximum tile index.
    
    long jmin = Math.max(0, (long) Math.floor(-ty / 256));
    long jmax = (long) Math.ceil((-ty + height) / 256);
    jmax = Math.max(jmin + 1, jmax);
    jmax = Math.min(j_max, jmax);
    
//    LOGGER.severe("Zoom = " + nearestZoom + ", activeZoom = " + activeZoom + ", tx = " + tx + ", i-range: " + imin + ", " + imax + " and j-range: " + jmin + ", " + jmax);
    List<MapTileImageView> tilesNeeded = new ArrayList<>();
    for (long i = imin; i < imax; i++) {
      for (long j = jmin; j < jmax; j++) {
        Long key = createTileKey(i, j, nearestZoom);
        SoftReference<MapTileImageView> ref = tiles[nearestZoom].get(key);
        if ((ref == null) || (ref.get() == null)) {
          if (ref != null) {
            LOGGER.fine("RECLAIMED: z=" + nearestZoom + ",i=" + i + ",j=" + j);
          }
//          MapTile tile = new MapTile(this, nearestZoom, i, j);
          MapTileImageView tile = new MapTileImageView(this, nearestZoom, i, j);
          tilesNeeded.add(tile);
          tiles[nearestZoom].put(key, new SoftReference<>(tile));
          
          // If the tile is still loading, we try to find a covering tile to show in the meantime.
//          if (tile.progress.get() < 1.0) {
            MapTileImageView covering = getCoveringTile(tile);
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
          MapTileImageView tile = ref.get();
          tilesNeeded.add(tile);
          if (!getChildren().contains(tile)) {
            getChildren().add(tile);
          }
        }
      }
    }
    StringBuilder buf = new StringBuilder();
    for (MapTileImageView mt: tilesNeeded) {
      buf.append(", ").append(mt);
    }
    LOGGER.severe("Tiles needed: " + buf.toString());
    
    List<MapTileImageView> currentTiles = new ArrayList<>();
    List<MapTileImageView> tilesToDelete = new ArrayList<>();
    for (Object o: getChildren()) {
      if (o instanceof MapTileImageView mt) {
        currentTiles.add(mt);
        if (!tilesNeeded.contains(mt)) {
          tilesToDelete.add(mt);
        }
      }
    }
    buf = new StringBuilder();
    for (MapTileImageView mt: currentTiles) {
      buf.append(", ").append(mt);
    }
    LOGGER.severe("Current Tiles: " + buf.toString());
    buf = new StringBuilder();
    for (MapTileImageView mt: tilesToDelete) {
      buf.append(", ").append(mt);
    }
    LOGGER.severe("Tiles to delete: " + buf.toString());
    getChildren().removeAll(tilesToDelete);

    //   calculateCenterCoords();
//    cleanupTiles();
//    cleanupTileCache(nearestZoom, imin, imax, jmin, jmax);
//    cleanupDiskTileCache();
    LOGGER.severe("<= loadTiles");
  }
  
  private void cleanupTileCache(int zoom, long iMin, long iMax, long jMin, long jMax) {
//    LOGGER.severe("=> cleanupTileCache, tiles in cache:");
    
    // Only clean this zoom level and lower zoom levels.
    
    long totalTilesInCache = 0;
    // Keep levels 0 up to 5 in cache.
    for (int z = zoom; z > 6; z--) {
      Map<Long, SoftReference<MapTileImageView>> tileMap = tiles[z];
      
      long iFirstKeep= iMin - 5;
      long iLastKeep= iMax + 5;
      long jFirstKeep= jMin - 5;
      long jLastKeep= jMax + 5;
      
      totalTilesInCache += tileMap.size();
            
      List<Long> keysToRemove = new ArrayList<>();
      for (long key: tileMap.keySet()) {
        long i = getIFromTileKey(key, z);
        long j = getIFromTileKey(key, z);
//        MapTile mapTile = tileMap.get(key).get();
//        LOGGER.severe("z = " + z + ", i = " + i + ", j = " + j + ", mapTile = " + (mapTile != null ? "Set" : "Not set"));
        if ((i < iFirstKeep) || (i > iLastKeep) || (j < jFirstKeep) || (j > jLastKeep)) {
          LOGGER.severe("Removing tile from cache, z = " + z + ", i = " + i + ", j = " + j);
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

    LOGGER.severe("Total tiles in cache: " + totalTilesInCache);
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
  
  private void cleanupDiskTileCache() {
    //TODO implement
  }

  private MapTileImageView getCoveringTile(MapTileImageView tile) {
    int z = tile.getTileZoom();
    if (z > 0) {
      long pi = tile.i / 2;
      long pj = tile.j / 2;
      long i_max = 1 << (z - 1);
      Long key = pi * i_max + pj;
      // LongTuple it = new LongTuple(i,j);
      SoftReference<MapTileImageView> ref = tiles[z - 1].get(key);
      if (ref != null) {
        LOGGER.severe("Covering tile found for maptile " + tile + "covered by " + ref.get());
        return ref.get();
      } else {
        LOGGER.severe("No covering tile found for maptile " + tile);
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
  protected MapTileImageView findCovering(int zoom, long i, long j) {
    while (zoom > 0) {
      zoom--;
      i = i / 2;
      j = j / 2;
      MapTileImageView candidate = (MapTileImageView) findTile(zoom, i, j);
      if ((candidate != null) && (!candidate.loading())) {
        return candidate;
      }
    }
    return null;
  }


  @Override
  public MapTile createMapTile(BaseMapAbstract baseMapAbstract, int zoom, long i, long j) {
    return new MapTile(this, zoom, i, j);
  }

//  protected void cleanupTiles() {
//    //      LOGGER.severe("START CLEANUP, zp = " + mapViewCommon.getZoom());
//    double zp = mapViewCommon.getZoom();
//    List<MapTileAbstract> toRemove = new LinkedList<>();
//    ObservableList<Node> children = this.getChildren();
//    for (Node child : children) {
//      if (child instanceof MapTileAbstract) {
//        MapTile tile = (MapTile) child;
//        boolean intersects = tile.getBoundsInParent().intersects(area.getBoundsInParent());
//        LOGGER.fine("evaluate tile " + tile + ", is = " + intersects + ", tzoom = " + tile.getTileZoom());
//        if (!intersects) {
//          LOGGER.fine("not shown");
//          boolean loading = tile.loading();
//          LOGGER.fine("Reap " + tile + " loading? " + loading);
//          if (!loading) {
//            toRemove.add(tile);
//          }
//        } else if (tile.getTileZoom() > ceil(zp)) {
//          LOGGER.fine("too detailed");
//          toRemove.add(tile);
//        } else if ((tile.getTileZoom() < floor(zp + TIPPING)) && (!tile.isCovering()) && (!(ceil(zp) >= NUMBER_OF_ZOOM_LEVELS))) {
//          LOGGER.fine("not enough detailed");
//          toRemove.add(tile);
//        }
//      }
//    }
//
//    StringBuilder buf = new StringBuilder();
//    for (MapTileAbstract mt: toRemove) {
//      buf.append(", ").append(mt);
//    }
//    LOGGER.severe("CleanupTiles deletes: " + buf.toString());
//    
//    getChildren().removeAll(toRemove);
//
//    LOGGER.fine("DONE CLEANUP, #children = " + getChildren().size());
//  }

}
