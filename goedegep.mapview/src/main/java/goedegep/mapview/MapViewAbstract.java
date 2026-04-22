package goedegep.mapview;

import java.util.logging.Logger;

import goedegep.geo.WGS84BoundingBox;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.layout.Region;

///
/// This class is the interface for a map view.
/// It provides the following functionality:
/// * Zooming   
///   The API provides methods to get and set the zoom level; [#getZoom()] and [#setZoom()].
///
///
public abstract class MapViewAbstract extends Region {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MapViewAbstract.class.getName());
  

  /**
   * Request the map to set its zoom level to the specified value.<br/>
   * If the specified zoom level is not supported by the map, the map sets the zoom level to the closest supported zoom level.
   *
   * @param zoom the requested zoom level
   */
  public abstract void setZoom(double zoom);

  /**
   * Returns the preferred zoom level of this map.
   * @return the zoom level
   */
  public abstract double getZoom();
  

  public abstract ReadOnlyDoubleProperty zoom();

  /**
   * Request the map to position itself around the specified center
   *
   * @param mapPoint
   */
  public abstract void setCenter(MapPoint mapPoint);

  /**
   * Get the center point of the map.
   * 
   * @return the center point of the map.
   */
  public abstract MapPoint getCenter();
  
//  /**
//   * Get the latitude of the center point of the map.
//   * @return the longitude of the center point of the map.
//   */
//  public double getCenterLatitude() {
//      return getCenter().getLatitude();
//  }
//  
//  /**
//   * Get the longitude of the center point of the map.
//   * @return the longitude of the center point of the map.
//   */
//  public double getCenterLongitude() {
//      return getCenter().getLongitude();
//  }

  /**
   * Request the map to position itself around the specified center
   *
   * @param lat
   * @param lon
   */
  public abstract void setCenter(double lat, double lon);

  /**
   * Add a new layer on top of this map. Layers are displayed in order of
   * addition, with the last added layer to be on top
   *
   * @param child
   */
  public abstract void addLayer(MapLayer mapLayer);

  /**
   * Removes the specified layer from the map
   *
   * @param child
   */
  public abstract void removeLayer(MapLayer mapLayer);

  /**
   * Wait a bit, then move to the specified mapPoint in seconds time.
   * <p>
   * Before flying the map will zoom out to an appropriate level based on the distance to fly, and then zoom in to the specified endZoom level at the end of the flight.
   * If endZoom is null, the current zoom level will used for the end location.
   *
   * @param waitTime the time to wait before we start moving
   * @param mapPoint the destination of the move
   * @param seconds the time the move should take // TODO currently ignored. Change to speed factor!
   * @param endZoom the zoom level to end. If null, the end zoom level will be the current zoom level.
   */
  public abstract void flyTo(double waitTime, MapPoint mapPoint, double seconds, Double endZoom);

  /**
   * Get the position on the map for the given scene coordinates.
   * This is useful for example when you want to add a marker on the map at the position where the user clicked.
   * You can use this method to convert the scene coordinates of the mouse click to map coordinates, and then add a marker at that position.
   *
   * @param sceneX x coordinate
   * @param sceneY y coordinate
   * @return map position
   */
  public abstract MapPoint getMapPosition(double sceneX, double sceneY);
  
  /***
   * Get a WGS84BoundingBox which covers the visible map area.
   * 
   * @param mapView the <code>mapView</code> for which the bounding box is requested.
   * @return a WGS84BoundingBox which covers the visible map area.
   */
  public abstract WGS84BoundingBox getVisibleMapCoordinates();
  
  public abstract Point2D getMapPoint(double lat, double lon);
  
//  public Point2D getMapPoint(double lat, double lon) {
//      return getMapPoint(zoom.get(), lat, lon);
//  }

  public abstract Point2D getMapPoint(double zoom, double lat, double lon);
  
//  private Point2D getMapPoint(double zoom, double lat, double lon) {
//      if (this.getScene() == null) {
//          return null;
//      }
//      double n = Math.pow(2, zoom);
//      double lat_rad = Math.PI * lat / 180;
//      double id = n / 360. * (180 + lon);
//      double jd = n * (1 - (Math.log(Math.tan(lat_rad) + 1 / Math.cos(lat_rad)) / Math.PI)) / 2;
//      double mex = id * 256;
//      double mey = jd * 256;
//      double x = this.getTranslateX() + mex;
//      double y = this.getTranslateY() + mey;
//      Point2D answer = new Point2D(x, y);
//      return answer;
//  }

}