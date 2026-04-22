package goedegep.mapview.image;

import java.util.logging.Logger;

import goedegep.geo.WGS84BoundingBox;
import goedegep.mapview.MapLayer;
import goedegep.mapview.MapPoint;
import goedegep.mapview.image.impl.BaseMap;
import goedegep.mapview.impl.MapViewCommon;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 *
 * This is the top UI element for a map image. The center location and the
 * zoom level of the map can be altered by input events (mouse/touch/gestures)
 * or by calling the methods setCenter and setZoom.
 */
public class MapImage extends MapViewCommon {
  private static final Logger LOGGER = Logger.getLogger(MapImage.class.getName());

  

  /**
   * Constructor.
   */
  public MapImage() {
    baseMapAbstract = new BaseMap(this);
    getChildren().add(baseMapAbstract);
  }

//  public BaseMap getBaseMap() {
//    return baseMap;
//  }

  /**
   * Get the position on the map represented by a given coordinate
   *
   * @param sceneX x coordinate
   * @param sceneY y coordinate
   * @return map position
   */
  public MapPoint getMapPosition(double sceneX, double sceneY) {
    return baseMapAbstract.getMapPosition(sceneX, sceneY);
  }

  /**
   * Request the map to position itself around the specified center
   *
   * @param mapPoint
   */
  public void setCenter(MapPoint mapPoint) {
    setCenter(mapPoint.getLatitude(), mapPoint.getLongitude());
  }

//  /**
//   * Returns the center point of this map
//   * @return the center point
//   */
//  public MapPoint getCenter() {
//    Point2D center = baseMapAbstract.getCenter();
//    return new MapPoint(center.getX(), center.getY());
//  }

//
//  /**
//   * Add a new layer on top of this map. Layers are displayed in order of
//   * addition, with the last added layer to be on top
//   *
//   * @param mapLayer
//   */
//  public void addLayer(MapImageLayer mapLayer) {
//    mapLayer.setBaseMap(this, baseMapAbstract);
//    layers.add(mapLayer);
//    this.getChildren().add(mapLayer);
//  }

//  /**
//   * Removes the specified layer from the map
//   *
//   * @param mapLayer
//   */
//  public void removeLayer(MapImageLayer mapLayer) {
//    layers.remove(mapLayer);
//    this.getChildren().remove(mapLayer);
//  }

  /**
   * Calculate the zoom level needed to show a bounding box
   * 
   * @param boundingBox a {@code WGS84BoundingBox}.
   * @return the zoom level needed to show {@code boundingBox}.
   */
  public static double getZoomLevel(WGS84BoundingBox boundingBox) {
    // TODO: calculate zooom level
    // http://stackoverflow.com/questions/4266754/how-to-calculate-google-maps-zoom-level-for-a-bounding-box-in-java
    int zoomLevel;

    final double maxDiff = Math.max(boundingBox.getWidth(), boundingBox.getHeight());
    if (maxDiff < 360d / Math.pow(2, 20)) {
      zoomLevel = 21;
    } else {
      zoomLevel = (int) (-1d*( (Math.log(maxDiff)/Math.log(2d)) - (Math.log(360d)/Math.log(2d))) + 1d);
      if (zoomLevel < 1)
        zoomLevel = 1;
    }

    return zoomLevel;
  }
  
  public void setSize(double width, double height) {
    setWidth(width);
    setHeight(height);
    setPrefWidth(width);
    setPrefHeight(height);
    setMinWidth(width);
    setMinHeight(height);
  }

  @Override
  public void flyTo(double waitTime, MapPoint mapPoint, double seconds, Double endZoom) {
    throw new UnsupportedOperationException("flyTo is not supported in MapImage");
  }
  
//  private final void loadTiles() {
//    LOGGER.severe("=>");
//    //        if (getScene() == null) {
//    //            LOGGER.fine("[JVDBG] can't load tiles, scene null");
//    //            return;
//    //        }
//    int nearestZoom = (Math.min((int) floor(zoom.get() + TIPPING), MAX_ZOOM - 1));
//    double activeZoom = zoom.get();
//    double deltaZ = nearestZoom - activeZoom;
//    long i_max = 1 << nearestZoom;
//    long j_max = 1 << nearestZoom;
//    double tx = getTranslateX();
//    double ty = getTranslateY();
//    double width = getMyWidth();
//    double height = getMyHeight();
//    long imin = Math.max(0, (long) (-tx * Math.pow(2, deltaZ) / 256) - 1);
//    long jmin = Math.max(0, (long) (-ty * Math.pow(2, deltaZ) / 256));
//    long imax = Math.min(i_max, imin + (long) (width * Math.pow(2, deltaZ) / 256) + 3);
//    long jmax = Math.min(j_max, jmin + (long) (height * Math.pow(2, deltaZ) / 256) + 3);
//    LOGGER.fine("Zoom = " + nearestZoom + ", active = " + activeZoom + ", tx = " + tx + ", loadtiles, check i-range: " + imin + ", " + imax + " and j-range: " + jmin + ", " + jmax);
//    for (long i = imin; i < imax; i++) {
//      for (long j = jmin; j < jmax; j++) {
//        Long key = i * i_max + j;
//        SoftReference<MapTileAbstract> ref = tiles[nearestZoom].get(key);
//        if ((ref == null) || (ref.get() == null)) {
//          if (ref != null) {
//            LOGGER.fine("RECLAIMED: z=" + nearestZoom + ",i=" + i + ",j=" + j);
//          }
//          MapTile tile = new MapTile(this, nearestZoom, i, j);
//          tiles[nearestZoom].put(key, new SoftReference<>(tile));
//          //                    MapTile covering = getCoveringTile(tile);
//          //                    if (covering != null) {
//          //                        covering.addCovering(tile);
//          //                        if (!getChildren().contains(covering)) {
//          //                            getChildren().add(covering);
//          //                        }
//          //                    }
//
//          getChildren().add(tile);
//        } else {
//          MapTile tile = ref.get();
//          if (!getChildren().contains(tile)) {
//            getChildren().add(tile);
//          }
//        }
//      }
//    }
//    //   calculateCenterCoords();
//    cleanupTiles();
//  }
  

  @Override
  public WGS84BoundingBox getVisibleMapCoordinates() {
    return baseMapAbstract.getVisibleMapCoordinates();
  }

  @Override
  public Point2D getMapPoint(double lat, double lon) {
    return baseMapAbstract.getMapPoint(lat, lon);
  }

  @Override
  public Point2D getMapPoint(double zoom, double lat, double lon) {
    return baseMapAbstract.getMapPoint(zoom, lat, lon);
  }
}
