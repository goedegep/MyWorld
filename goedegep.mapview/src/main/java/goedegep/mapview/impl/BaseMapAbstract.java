package goedegep.mapview.impl;

import static java.lang.Math.floor;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.geo.WGS84BoundingBox;
import goedegep.mapview.MapPoint;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;

/**
 * This class is the base map for the MapView. It is responsible for loading and displaying the map tiles.
 * 
 * @param <T> the type of the map tiles, which must extend MapTileAbstract. The specific map tile is needed, because not all implementations use covering tiles.
 */
public abstract class BaseMapAbstract<T extends TileImageViewAbstract> extends Group {
  private static final Logger LOGGER = Logger.getLogger(BaseMapAbstract.class.getName() );

  /**
   * When the zoom-factor is less than TIPPING below an integer, we will use
   * the higher-level zoom and scale down, otherwise we scale up.
   * Using the higher level zoom and scaling down shows more details than using the lower level zoom and scaling up,
   * but as the texts are also scaled scaling down soon leads to too small texts.
   * The value chosen here is a compromise between showing more details and having readable texts.
   * Actually the value is 0.2, but an extra 0.01 is added in order to handle a zoom of 8.799999999999997 as 8.8.
   */
  public static final double TIPPING = 0.21;

  /**
   * The maximum zoom level this map (and OpenStreetMap) supports.
   * The zoom levels are 0, 1, 2, ..., NUMBER_OF_ZOOM_LEVELS - 1. So the maximum zoom level is NUMBER_OF_ZOOM_LEVELS - 1.
   */
  public static final int NUMBER_OF_ZOOM_LEVELS = 20;

  /**
   * Currently loaded tiles, organized by zoom level and tile indices.
   * There is one array entry for each zoom level, and each entry is a map with the tile indices as key and the tile as value.
   * The key is calculated as i * (2^zoom) + j, where i and j are the tile indices in x and y direction, respectively.
   */
  @SuppressWarnings("unchecked")
  protected final Map<Long, T>[] tiles = new HashMap[NUMBER_OF_ZOOM_LEVELS];

  
  protected boolean dirty = false;

  /**
   * The MapView for which this is the base map.
   * This is needed to get the current zoom level and to set the zoom level when zooming in or out.
   */
  protected MapViewCommon mapViewCommon;

  
  /**
   * Constructor.
   * @param mapView the MapView for which this is the base map.
   */
  public BaseMapAbstract(MapViewCommon mapView) {
    this.mapViewCommon = mapView;

    for (int i = 0; i < tiles.length; i++) {
      tiles[i] = new HashMap<>();
    }

  }
  
  /**
   * Get the maximum zoom level for this map.
   * 
   * @return the maximum zoom level for this map.
   */
  public static int getMaximumZoomLevel() {
    return NUMBER_OF_ZOOM_LEVELS - 1;
  }

  /**
   * Get the property of the zoom level of the map.
   * 
   * @return the property of the zoom level of the map.
   */
  public ReadOnlyDoubleProperty zoom() {
    return mapViewCommon.zoomReadOnlyProperty();
  }

//  /**
//   * Set the center of the map to the given center point.
//   * 
//   * @param center the center point to set the center of the map to.
//   */
//  public void setCenter(Point2D center) {
//    doSetCenter(center.getX(), center.getY());
//  }

  /**
   * Set the center of the map to the given latitude and longitude.
   * 
   * @param lat the latitude of the center point to set the center of the map to.
   * @param lon the longitude of the center point to set the center of the map to.
   */
  protected void doSetCenter(MapPoint center) {
    LOGGER.severe("=> doSetCenter, lat = " + center.getLatitude() + ", lon = " + center.getLongitude());
    double lat = center.getLatitude();
    double lon = center.getLongitude();
        
    double width = mapViewCommon.getDimensions().getWidth();
    double height = mapViewCommon.getDimensions().getHeight();
    
    // the zoom level.
    double activeZoom = mapViewCommon.getZoom();

    // n is the number of tiles in one direction (x or y) for the active zoom level
    double n = nrOfTilesAtZoomLevel(activeZoom);

    // Calculate the x tile index.
    double id = horizontalTileIndexForDegreesLongitude(n, lon);

    // Calculate the y tile index.
    // Here Mercator projection has to be used, which uses radians.
    double lat_rad = Math.toRadians(lat);
    double jd = n * (1 - (mercatorY(lat_rad) / Math.PI)) / 2;

    // Calculate the pixel coordinates of the center of the map. Each tile is 256 pixels wide and high, so we multiply the tile indices by 256.
    double mex = id * 256;
    double mey = jd * 256;

    // Calculate the offset to have the center of the map at the center of the screen.
    double ttx = mex - width / 2;
    double tty = mey - height / 2;

    // Set the translation of the map to the negative of the calculated offset, so that the center of the map is at the center of the screen.
    setTranslateX(-1 * ttx);
    setTranslateY(-1 * tty);
    LOGGER.info("setCenter, tx = " + this.getTranslateX() + ", with = " + mapViewCommon.getWidth() / 2 + ", mex = " + mex);
    mapViewCommon.centerProperty.set(new MapPoint(lat, lon));
    markDirty();

    LOGGER.severe("<= doSetCenter");
  }
  
  /**
   * Get the number of tiles at a specific zoom level.
   * <p>
   * Al level 0 there is one tile. At each higher level a tile is replaced by 2 x 2 tiles.
   * So the number of tiles doubles per level, Math.pow(2, zoomLevel).
   * 
   * @param zoomLevel
   * @return
   */
  public double nrOfTilesAtZoomLevel(double zoomLevel) {
    return Math.pow(2, zoomLevel);
  }
  
  /**
   * Get the horizontal tile index for a longitude and a specific number of tiles.
   * <p>
   * Let d be the number of degrees per tile. Then
   * 
   *   nrOfTiles * d = 360  or  d = 360 / nrOfTiles
   *   
   * Let degx be the number of degrees at the start of tile x. Then
   * 
   *   degx = d * x  or  x = degx / d = degx / 360 * nrOfTiles
   *   
   * As degreesLongitude is in the range -180 .. 180, degx = 180 + degreesLongitude.
   * 
   * @param nrOfTiles The number of tiles for the full 360 degrees range.
   * @param degreesLongitude a longitude in degrees in the range -180 .. 180.
   * @return the tile index
   */
  public double horizontalTileIndexForDegreesLongitude(double nrOfTiles, double degreesLongitude) {
    return nrOfTiles / 360. * (180 + degreesLongitude);
  }

  /**
   * Perform mercator projection for the latitude.
   * 
   * @param lat_rad The latitude in radians.
   * @return the mercator projection value for {@code lat_rad}
   */
  public double mercatorY(double lat_rad) {
    return Math.log(Math.tan(lat_rad) + 1 / Math.cos(lat_rad));
  }

  /**
   * Move the center of the map horizontally by a number of pixels. After this
   * operation, it will be checked if new tiles need to be downloaded
   *
   * @param dx the number of pixels
   */
  public void moveX(double dx) {
    MapPoint center = mapViewCommon.centerProperty.get();
    double currentCenterLat = center.getLatitude();
    double currentCenterLon = center.getLongitude();
    Point2D currentMapPoint = getMapPoint(currentCenterLat, currentCenterLon);
    MapPoint newMapPosition = getMapPosition(currentMapPoint.getX() + dx, currentMapPoint.getY());
    mapViewCommon.centerProperty.set(newMapPosition);
//    mapViewCommon.centerLat.set(newMapPosition.getLatitude());
//    mapViewCommon.centerLon.set(newMapPosition.getLongitude());
    setTranslateX(getTranslateX() - dx);
    markDirty();
  }

  /**
   * Move the center of the map vertically by a number of pixels. After this
   * operation, it will be checked if new tiles need to be downloaded
   *
   * @param dy the number of pixels
   */
  public void moveY(double dy) {
    double z = mapViewCommon.getZoom();
    double maxty = 256 * Math.pow(2, z) - mapViewCommon.getHeight();
    LOGGER.config("ty = " + getTranslateY() + " and dy = " + dy);
    if (getTranslateY() <= 0) {
      if (getTranslateY() + maxty >= 0) {
        setTranslateY(Math.min(0, getTranslateY() - dy));
      } else {
        setTranslateY(-maxty + 1);
      }
    } else {
      setTranslateY(0);
    }
    markDirty();
  }


  /**
   * Zoom the map in or out by a specific amount, around a specific pivot point. The pivot point is given in scene coordinates.
   * 
   * Only called internally when a zoom around a specific point is requested.
   * 
   * @param delta the amount to zoom in (positive) or out (negative)
   * @param pivotX the x coordinate of the pivot point in scene coordinates.
   * @param pivotY the y coordinate of the pivot point in scene coordinates.
   */
  public void zoom(double delta, double pivotX, double pivotY) {
    LOGGER.severe("=> zoom, delta = " + delta + ", pivotX = " + pivotX + ", pivotY = " + pivotY);
    double currentZoomLevel = mapViewCommon.getZoom();

    double currentTranslateX = getTranslateX();
    double t1x = pivotX - getTranslateX();  // the vector from the map’s translation origin to pivotX

    // Increasing the zoom level by one, is an increase of a factor of 2 in the scale.
    // So, the factor by which we need to increase the translation is 1 - 2^delta. Note that for a delta of 0 this factor is 0 and for a delta of 1 this factor is -1.
    double t2x = 1. - Math.pow(2, delta);
    double totX = t1x * t2x;
    double newTranslateX = currentTranslateX + totX;

    double currentTranslateY = getTranslateY();
    double t1y = pivotY - currentTranslateY;
    double t2y = 1. - Math.pow(2, delta);
    double totY = t1y * t2y;
    double newTranslateY = currentTranslateY + totY;
    LOGGER.fine("currentZoomLevel = " + currentZoomLevel + ", txold = " + currentTranslateX + ", totx = " + totX + ", tyold = " + currentTranslateY + ", toty = " + totY);
    double newZoomLevel = currentZoomLevel + delta;
//    if (newZoomLevel < 0) {
//      newZoomLevel = 0;
//    } else if (newZoomLevel > MAX_ZOOM) {
//      newZoomLevel = MAX_ZOOM;
//    }
//    setTranslateX(newTranslateX);
//    setTranslateY(newTranslateY);
//    mapViewCommon.setZoom(newZoomLevel);
//    markDirty();

    if ((delta > 0)) {
      if (currentZoomLevel < NUMBER_OF_ZOOM_LEVELS) {
        setTranslateX(currentTranslateX + totX);
        setTranslateY(currentTranslateY + totY);
        mapViewCommon.setZoom(currentZoomLevel + delta);
        markDirty();
      }
    } else if (currentZoomLevel > 1) {
      double nz = currentZoomLevel + delta;
      if (Math.pow(2, nz) * 256 > mapViewCommon.getHeight()) {
        // also, we need to fit on the current screen
        setTranslateX(currentTranslateX + totX);
        setTranslateY(currentTranslateY + totY);
        mapViewCommon.setZoom(currentZoomLevel + delta);
        markDirty();
      }
    }
    LOGGER.severe("after, zp = " + mapViewCommon.getZoom() + ", tx = " + getTranslateX());
  }

  public MapPoint getMapPosition(double sceneX, double sceneY) {
//    final SimpleDoubleProperty _lat = new SimpleDoubleProperty();
//    final SimpleDoubleProperty _lon = new SimpleDoubleProperty();
    ObjectProperty<MapPoint> mapPoint = new SimpleObjectProperty<>();
    calculateCoords(sceneX - getTranslateX(), sceneY - getTranslateY(), mapPoint);  
    return new MapPoint(mapPoint.get().getLatitude(), mapPoint.get().getLongitude());
  }
  
  public Point2D getMapPoint(double lat, double lon) {
    return getMapPoint(mapViewCommon.getZoom(), lat, lon);
  }

  public Point2D getMapPoint(double zoom, double lat, double lon) {
    
    double width = mapViewCommon.getWidth();
    double height = mapViewCommon.getHeight();
//    if (width == 0 || height == 0) {
//      abortedTileLoad = true;
//      return null;
//    }

    double n = Math.pow(2, zoom);
    double lat_rad = Math.PI * lat / 180;
    double id = n / 360. * (180 + lon);
    double jd = n * (1 - (Math.log(Math.tan(lat_rad) + 1 / Math.cos(lat_rad)) / Math.PI)) / 2;
    double mex = id * 256;
    double mey = jd * 256;
    //        double ttx = mex - this.getMyWidth() / 2;
    //        double tty = mey - this.getMyHeight() / 2;
    double x = this.getTranslateX() + mex;
    double y = this.getTranslateY() + mey;
    Point2D answer = new Point2D(x, y);
    return answer;
  }


  public abstract void loadTiles();
  
  /**
   * Return a specific tile
   *
   * @param zoom the zoomlevel
   * @param i the x-index
   * @param j the y-index
   * @return the tile, only if it is still in the cache
   */
  protected TileImageViewAbstract findTile(int zoom, long i, long j) {
    Long key = i * (1 << zoom) + j;
    T tile = tiles[zoom].get(key);
    return (tile == null) ? null : tile;
  }

  /**
   * Called by the JavaFX Application Thread when a pulse is running.
   * In case the dirty flag has been set, we know that something has changed
   * and we need to reload/clean the tiles.
   */
  @Override
  protected void layoutChildren() {
    LOGGER.severe("=> layoutChildren, initialized = " + mapViewCommon.initialized + ", dirty = " + dirty);
    if (!mapViewCommon.initialized) {
      return;
    }
    
//    if (dirty) {
      loadTiles();
//      dirty = false;
//    }
    super.layoutChildren();
    
    LOGGER.severe("<= layoutChildren");
  }

  /**
   * Update the center latitude and longitude properties based on the current center coordinates.
   * <p>
   * The center coordinates are calculated based on the current translation of the map
   * and the size of the map view, so they represent the coordinates of the point that is currently at the center of the map view.
   * TODO rename to updateCenterCoords, as this method does not only calculates the center coordinates, but it updates the center latitude and longitude properties as well.
   */
  private void calculateCenterCoords() {
    LOGGER.severe("=> calculateCenterCoords");
    // Calculate the x,y coordinates of the center of the map (in pixels).
    // At low zoom levels the center of the shown map is not at the center of the window. In that case we don't update the center.
//    if (!mapFillsWindow()) {
//      LOGGER.info("Skipping updating center coords because map doesn't fill window");
//      return;
//    }
    
    // TODO This calculation assumees that the center location is at the center of the window. So we have to make sure that that is always the case.
//    LOGGER.severe("translate x/y: " + getTranslateX() + ", " + getTranslateY());
    double translateX = getTranslateX();
    double translateY = getTranslateY();
    double x = mapViewCommon.getDimensions().getWidth() / 2 - getTranslateX();
    double y = mapViewCommon.getDimensions().getHeight() / 2 - getTranslateY();
//    LOGGER.severe("x, y = " + x + ", " + y);
    
    // Set the center latitude and longitude properties based on the calculated x,y coordinates.    
    calculateCoords(x, y, mapViewCommon.centerProperty);
    
    LOGGER.severe("<= calculateCenterCoords, center lat/lon = " + mapViewCommon.centerProperty.get().getLatitude() + ", " + mapViewCommon.centerProperty.get().getLongitude());
  }
  
  /**
   * Check if the map fills the window. If the map does not fill the window, we cannot calculate the center coordinates, so we should not update them.
   * <p>
   * The map does not fill the window when the zoom level is too low, so that there are not enough tiles to cover the whole window.
   * In that case, the center of the map is not at the center of the window, and we should not update the center coordinates based on the current translation and window size.
   * 
   * @return true if the map fills the window, false if the width and/or height is 0 or if the map doesn't fill the window.
   */
  private boolean mapFillsWindow() {
    double width = mapViewCommon.getWidth();
    double height = mapViewCommon.getHeight();
    LOGGER.info("window dimensions: " + width + ", " + height);
    if (width == 0.0 || height == 0.0) {
      return false;
    }
    
    int nearestZoom = (Math.min((int) floor(mapViewCommon.getZoom() + TIPPING), NUMBER_OF_ZOOM_LEVELS - 1));
    double nrOfTilesAtNearestZoom = nrOfTilesAtZoomLevel(nearestZoom);
    double scale = 1; // TODO calculate real scale
    double totalTilePixels = 256 * scale * nrOfTilesAtNearestZoom;
    
    if (width > totalTilePixels  ||  height > totalTilePixels) {
      return false;
    }
    
    return true;
  }

  /**
   * Calculate the latitude and longitude for given x, y coordinate (in pixels) and the current zoom level.
   * The calculated values are set in the provided properties (so these are a sort of by reference parameters).
   *
   * @param x the x coordinate in pixels
   * @param y the y coordinate in pixels
   * @param lat the property to set the calculated latitude
   * @param lon the property to set the calculated longitude
   */
  private void calculateCoords(double x, double y, ObjectProperty<MapPoint> mapPoint) {
    double z = mapViewCommon.getZoom();
    double latrad = Math.PI - (2.0 * Math.PI * y) / (Math.pow(2, z)*256.);
    double mlat = Math.toDegrees(Math.atan(Math.sinh(latrad)));
    double mlon = x / (256*Math.pow(2, z)) * 360 - 180;
//    LOGGER.severe("calculateCoords: z = " + z + ", lat = " + mlat + ", lon = " + mlon);
    mapPoint.set(new MapPoint(mlat, mlon));
//    lon.set(mlon);
//    lat.set(mlat);
  }

  /**
   * When something changes that would lead to a change in UI representation 
   * (e.g. map is dragged or zoomed), this method should be called.
   * This method will NOT update the map immediately, but it will set a 
   * flag and request a next pulse. 
   * This is much more performant than redrawing the map on each input event.
   */
  protected void markDirty() {
    LOGGER.severe("=> markDirty");
    mapViewCommon.markDirty();
//    this.dirty = true;
////    calculateCenterCoords();
//    setNeedsLayout(true);
//    Platform.requestNextPulse();
    LOGGER.severe("<= markDirty");
  }

  /***
   * Get a WGS84BoundingBox which covers the visible map area.
   * 
   * @param mapViewCommon the <code>mapView</code> for which the bounding box is requested.
   * @return a WGS84BoundingBox which covers the visible map area.
   */
  public WGS84BoundingBox getVisibleMapCoordinates() {
    if (mapViewCommon.centerProperty.get() == null) {
      return null;
    }
    
    double height = mapViewCommon.getHeight();
    double width = mapViewCommon.getWidth();

    Point2D center = getMapPoint(mapViewCommon.centerProperty.get().getLatitude(), mapViewCommon.centerProperty.get().getLongitude());
    
    if (center == null) {
      return null;
    }

    MapPoint bottomLeft = getMapPosition(center.getX() - width / 2, center.getY() - height / 2);
    MapPoint topRight = getMapPosition(center.getX() + width / 2, center.getY() + height / 2);
    LOGGER.info("Bottom left: " + bottomLeft.getLatitude() + ", " + bottomLeft.getLongitude());
    LOGGER.info("Top right: " + topRight.getLatitude() + ", " + topRight.getLongitude());

    return new WGS84BoundingBox(bottomLeft.getLongitude(), topRight.getLatitude(), topRight.getLongitude(), bottomLeft.getLatitude());
  }
  
  /**
   * Get the tile zoom level for a specific zoom level.
   * <p>
   * The zoom level is a double, but there are only tiles for integer zoom levels. So we need to calculate the tile zoom level for a specific zoom level.
   * See the {@link #TIPPING} constant for the logic behind the calculation.
   * 
   * @param zoom
   * @return
   */
  protected int getTileZoomLevel(double zoom) {
    return (int) floor(zoom + TIPPING);
    // For now I don't think the MAX_ZOOM - 1 check is needed, as the zoom level is already limited by the MapViewCommon.setZoom method. But maybe we should add it just to be sure.
    // (Math.min((int) floor(mapViewCommon.getZoom() + TIPPING), MAX_ZOOM - 1))
  }
  
  
  /**
   * Get status information about the map view. All information about the base map and ...
   * @return status information about the map view
   */
  public String getStatusInformation() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("Base map: \n");
    
    buf.append("TranslateX/Y: " + getTranslateX() + ", " + getTranslateY() + "\n");
    List<TileImageViewAbstract> currentTiles = new ArrayList<>();
    for (Object child: getChildren()) {
      if (child instanceof TileImageViewAbstract tile) {
        currentTiles.add(tile);
      }
    }
    currentTiles.sort(new TileComperator());
    for (TileImageViewAbstract tile: currentTiles) {
      buf.append(tile.getStatusInformation());
    }
    
    return buf.toString();
  }
}

class TileComperator implements java.util.Comparator<TileImageViewAbstract> {
  @Override
  public int compare(TileImageViewAbstract o1, TileImageViewAbstract o2) {
    if (o1.tileZoom != o2.tileZoom) {
      return Integer.compare(o1.tileZoom, o2.tileZoom);
    } else if (o1.i != o2.i) {
      return Long.compare(o1.i, o2.i);
    } else {
      return Long.compare(o1.j, o2.j);
    }
  }
}
