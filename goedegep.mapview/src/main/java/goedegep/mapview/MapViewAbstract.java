package goedegep.mapview;

import java.util.logging.Logger;

import goedegep.geo.WGS84BoundingBox;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.layout.Region;

///
/// This class is the interface for a map view.   
/// What is shown on the map view is determined by:
/// * The center of the map.
/// * The zoom level of the map.
/// * The width and height of the map.
/// * The layers that are added to the map.
///
/// There are two implementations:
/// * goedegep.mapview.view.MapView   
///   This implementation provides an interactive map. Where you can zoom in and out and pan using the mouse. The map tiles are loaded asynchronously.
///   The size of the map is determined by its parent container, and the map will automatically resize when the parent container is resized.
///
/// * goedegep.mapview.image.MapImage   
///   This implementation is meant for creating a map and save it to an image file. It does not react to mouse events and loads the map tiles synchronously.
///   This size of the map has to be set explicitly.
///
/// Functionality:
///
/// * Center of the map *
///   The center of the map can be set and retrieved using the [#setCenter()] and [#getCenter()] methods. The center is represented as a [MapPoint] which contains the latitude and longitude of the center point of the map.   
///   In case of the MapView implementation, the center can also be set by dragging the map with the mouse.
///
/// * Zoom level   
///   The API provides methods to get and set the zoom level; [#getZoom()] and [#setZoom()].
///   In case of the MapView implementation, the zoom level can also be changed by using the mouse wheel. In this case zooming is centered around the mouse cursor position, so the map will zoom in or out towards the position of the mouse cursor.
///
/// * Width and height of the map
///   For the MapView the width and height of the map are determined by the size of the parent container. The map will automatically resize when the parent container is resized.   
///   In case of the MapImage implementation, the size of the map has to be set explicitly using the [MapImage#setSize()] method.
///   In both cases, the width and height of the map can be retrieved using the [#getDimension()] method.
///
/// * Fly to a location
///   The MapView API provides a method to fly to a location on the map ([#flyTo()]). The map will then animate the transition to the new center and zoom level.
///
/// * Layers on the map
///   Layers can be added to the map using the [#addLayer()] method and removed using the [#removeLayer()] method. Layers are displayed in order of addition, with the last added layer to be on top.
///
/// Utility methods:   
/// A number of utility methods are provided, which a mainly meant for the implementation of map layers.
///
/// * [#getMapPosition()]   
///   This can be used to get the position on the map, as a [MapPoint], for given scene coordinates.
///   When the user clicks on the map, you can use this method to convert the scene coordinates of the mouse click to map coordinates, and then add a marker at that position.
///
/// * [#getMapPoint()]   
///   This is the inverse of the [#getMapPosition()] method.
///   It can be used to get the position on the map, as a [Point2D] with scene coordinates, for given latitude and longitude.
///   This is useful when you want to draw a marker on the map for a specific latitude and longitude.
///
/// * [#getVisibleMapBoundingBox()]
///   This returns a [WGS84BoundingBox] which covers the visible map area. This is useful for filtering the items to be shown on a map layer.
///
public abstract class MapViewAbstract extends Region {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MapViewAbstract.class.getName());
  

  /**
   * Get the center point of the map.
   * 
   * @return the center point of the map.
   */
  public abstract MapPoint getCenter();

  /**
   * Request the map to position itself around the specified center
   *
   * @param mapPoint
   */
  public abstract void setCenter(MapPoint mapPoint);

  /**
   * Returns the preferred zoom level of this map.
   * @return the zoom level
   */
  public abstract double getZoom();

  /**
   * Request the map to set its zoom level to the specified value.<br/>
   * If the specified zoom level is not supported by the map, the map sets the zoom level to the closest supported zoom level.
   *
   * @param zoom the requested zoom level
   */
  public abstract void setZoom(double zoom);
  
  /**
   * Get the zoom level of the map as a read only property.
   * 
   * @return the zoom level of the map as a read only property.
   */
  public abstract ReadOnlyDoubleProperty zoomReadOnlyProperty();
  
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
   * Get the position on the map for the given scene coordinates.
   * This is useful for example when you want to add a marker on the map at the position where the user clicked.
   * You can use this method to convert the scene coordinates of the mouse click to map coordinates, and then add a marker at that position.
   *
   * @param sceneX x coordinate
   * @param sceneY y coordinate
   * @return map position
   */
  public abstract MapPoint getMapPosition(double sceneX, double sceneY);
  
  /**
   * Get the dimensions (width and height) of the map.
   * 
   * @return the dimensions of the map, or null if the map doesn't have any dimensions yet.
   */
  public abstract Dimension2D getDimension();
  
  /**
   * Get the position on the map, as a [Point2D] with scene coordinates, for the given latitude and longitude.
   *
   * @param lat latitude
   * @param lon longitude
   * @return a [Point2D] with the scene coordinates for the given latitude and longitude.
   */
  public abstract Point2D getMapPoint(double lat, double lon);
  
  /**
   * Get a WGS84BoundingBox which covers the visible map area.
   * 
   * @return a WGS84BoundingBox which covers the visible map area.
   */
  public abstract WGS84BoundingBox getVisibleMapBoundingBox();
  
  /**
   * Get status information about the map view. All information about the base map and ...
   * @return status information about the map view
   */
  public abstract String getStatusInformation();
}