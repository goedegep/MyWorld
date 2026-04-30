package goedegep.mapview.image.impl;

import static java.lang.Math.floor;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.mapview.impl.BaseMapAbstract;
import goedegep.mapview.impl.MapViewCommon;
import goedegep.mapview.image.impl.MapTileImageView;

/**
 *
 * The BaseMap provides the underlying maptiles.
 * On top of this, additional layers can be rendered.
 */
public class BaseMap extends BaseMapAbstract<MapTileImageView> {
  private static final Logger LOGGER = Logger.getLogger(BaseMap.class.getName() );


  /**
   * Constructor.
   * 
   * @param mapView the map view for which this is the base map.
   */
  public BaseMap(MapViewCommon mapView) {
    super(mapView);
    
    dirty = true;
  }

  @Override
  public MapTile createMapTile(BaseMapAbstract baseMapAbstract, int zoom, long i, long j) {
    return new MapTile(this, zoom, i, j);
  }

  @Override
  public void loadTiles() {
    LOGGER.severe("=>");
    
    double width = mapViewCommon.getWidth();
    double height = mapViewCommon.getHeight();
    
    if ((width <= 0) || (height <= 0)) {
      LOGGER.severe("Can't load tiles, width or height <= 0");
      return;
    }

//    int nearestZoom = (Math.min((int) floor(mapViewCommon.getZoom() + TIPPING), MAX_ZOOM - 1));
    int nearestZoom = getTileZoomLevel(mapViewCommon.getZoom());
    double activeZoom = mapViewCommon.getZoom();
//    double deltaZ = nearestZoom - activeZoom;
    long i_max = 1 << nearestZoom;
    long j_max = 1 << nearestZoom;
    double tx = getTranslateX();
    double ty = getTranslateY();
    
    long imin = Math.max(0, (long) Math.floor(-tx / 256));
    long jmin = Math.max(0, (long) Math.floor(-ty / 256));
    long imax = Math.min(i_max - 1, (long) Math.ceil((-tx + width) / 256));
    long jmax = Math.min(j_max - 1, (long) Math.ceil((-ty + height) / 256));

//    long imin = Math.max(0, (long) (-tx * Math.pow(2, deltaZ) / 256) - 1);
//    long jmin = Math.max(0, (long) (-ty * Math.pow(2, deltaZ) / 256));
//    long imax = Math.min(i_max, imin + (long) (width * Math.pow(2, deltaZ) / 256) + 3);
//    long jmax = Math.min(j_max, jmin + (long) (height * Math.pow(2, deltaZ) / 256) + 3);
    LOGGER.fine("Zoom = " + nearestZoom + ", active = " + activeZoom + ", tx = " + tx + ", loadtiles, check i-range: " + imin + ", " + imax + " and j-range: " + jmin + ", " + jmax);
    List<MapTileImageView> tilesNeeded = new ArrayList<>();
    for (long i = imin; i < imax; i++) {
      for (long j = jmin; j < jmax; j++) {
        Long key = i * i_max + j;
        SoftReference<MapTileImageView> ref = tiles[nearestZoom].get(key);
        if ((ref == null) || (ref.get() == null)) {
          if (ref != null) {
            LOGGER.fine("RECLAIMED: z=" + nearestZoom + ",i=" + i + ",j=" + j);
          }
          MapTileImageView tile = new MapTileImageView(this, nearestZoom, i, j);
          tilesNeeded.add(tile);
          tiles[nearestZoom].put(key, new SoftReference<>(tile));

          getChildren().add(tile);
          tile.calculatePosition();
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
    //   calculateCenterCoords();
//    cleanupTiles();
  }

}