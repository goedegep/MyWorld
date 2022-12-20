package goedegep.mapview;

import java.util.logging.Logger;

import com.gluonhq.impl.maps.BaseMap;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import goedegep.geo.WGS84BoundingBox;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class MapViewUtil {
  private static final Logger LOGGER = Logger.getLogger(MapViewUtil.class.getName());
  
  /**
   * Private constructor as this is a utility class.
   */
  private MapViewUtil() {
    
  }
  
  /***
   * Get a WGS84BoundingBox which covers the visible map area.
   * 
   * @param mapView the <code>mapView</code> for which the bounding box is requested.
   * @return a WGS84BoundingBox which covers the visible map area.
   */
  public static WGS84BoundingBox getVisibleMapCoordinates(BaseMap baseMap) {
//    BaseMap baseMap = mapView.getBaseMap();
    baseMap.centerLat().doubleValue();
    
    double height = baseMap.getParent().getLayoutBounds().getHeight();
    double width = baseMap.getParent().getLayoutBounds().getWidth();
    
    Point2D center = baseMap.getMapPoint(baseMap.centerLat().doubleValue(), baseMap.centerLon().doubleValue());
    
    MapPoint bottomLeft = baseMap.getMapPosition(center.getX() - width / 2, center.getY() - height / 2);
    MapPoint topRight = baseMap.getMapPosition(center.getX() + width / 2, center.getY() + height / 2);
    LOGGER.info("Bottom left: " + bottomLeft.getLatitude() + ", " + bottomLeft.getLongitude());
    LOGGER.info("Top right: " + topRight.getLatitude() + ", " + topRight.getLongitude());
    
    return new WGS84BoundingBox(bottomLeft.getLongitude(), topRight.getLatitude(), topRight.getLongitude(), bottomLeft.getLatitude());
  }

  /**
   * Do the work to re-draw a polygon for a bounding box.
   * <p>
   * The points of the polygon are cleared and points are added for the four corners of the bounding box.
   * 
   * @param polygon the polygon to be updated.
   * @param mapBoundingBox the bounding box for which the polygon is to be updated.
   */
  public static void updateBoundingBoxPolygon(Polygon polygon, WGS84BoundingBox mapBoundingBox, BaseMap baseMap) {
    ObservableList<Double> points = polygon.getPoints();
    points.clear();
    Point2D northWest = baseMap.getMapPoint(mapBoundingBox.getNorth(), mapBoundingBox.getWest());
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
