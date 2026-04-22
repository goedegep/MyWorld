package goedegep.mapview;

import java.util.logging.Logger;

import goedegep.geo.WGS84BoundingBox;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class MapViewUtil {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MapViewUtil.class.getName());
  
  /**
   * Private constructor as this is a utility class.
   */
  private MapViewUtil() {
    
  }
  
//  /***
//   * Get a WGS84BoundingBox which covers the visible map area.
//   * 
//   * @param mapView the <code>mapView</code> for which the bounding box is requested.
//   * @return a WGS84BoundingBox which covers the visible map area.
//   */
//  public static WGS84BoundingBox getVisibleMapCoordinates(BaseMap baseMap) {
//    double height = baseMap.getParent().getLayoutBounds().getHeight();
//    double width = baseMap.getParent().getLayoutBounds().getWidth();
//    
//    Point2D center = baseMap.getMapPoint(baseMap.centerLat().doubleValue(), baseMap.centerLon().doubleValue());
//    
//    MapPoint bottomLeft = baseMap.getMapPosition(center.getX() - width / 2, center.getY() - height / 2);
//    MapPoint topRight = baseMap.getMapPosition(center.getX() + width / 2, center.getY() + height / 2);
//    LOGGER.info("Bottom left: " + bottomLeft.getLatitude() + ", " + bottomLeft.getLongitude());
//    LOGGER.info("Top right: " + topRight.getLatitude() + ", " + topRight.getLongitude());
//    
//    return new WGS84BoundingBox(bottomLeft.getLongitude(), topRight.getLatitude(), topRight.getLongitude(), bottomLeft.getLatitude());
//  }

  /**
   * Do the work to re-draw a polygon for a bounding box.
   * <p>
   * The points of the polygon are cleared and points are added for the four corners of the bounding box.
   * 
   * @param polygon the polygon to be updated.
   * @param mapBoundingBox the bounding box for which the polygon is to be updated.
   */
  public static void updateBoundingBoxPolygon(Polygon polygon, WGS84BoundingBox mapBoundingBox, MapViewAbstract baseMap) {
    ObservableList<Double> points = polygon.getPoints();
    points.clear();

    if (mapBoundingBox != null) {
      Point2D northWest = baseMap.getMapPoint(mapBoundingBox.getNorth(), mapBoundingBox.getWest());
      if (northWest == null) {
        LOGGER.severe("Unable to get map point for north-west corner of bounding box: " + mapBoundingBox);
        return;
      }
      
      // Assume that if getMapPoint returns non null for the north-west corner, it will also work for the other corners.
      points.add(northWest.getX());
      points.add(northWest.getY());
      Point2D northEast = baseMap.getMapPoint(mapBoundingBox.getNorth(), mapBoundingBox.getEast());
      points.add(northEast.getX());
      points.add(northEast.getY());
      Point2D southEast = baseMap.getMapPoint(mapBoundingBox.getSouth(), mapBoundingBox.getEast());
      points.add(southEast.getX());
      points.add(southEast.getY());
      Point2D southWest = baseMap.getMapPoint(mapBoundingBox.getSouth(), mapBoundingBox.getWest());
      points.add(southWest.getX());
      points.add(southWest.getY());
    }
  }
}
