package goedegep.mapview;

import java.util.logging.Logger;

import com.gluonhq.impl.maps.BaseMap;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import goedegep.geo.WGS84BoundingBox;
import javafx.geometry.Point2D;

public class MapViewUtil {
  private static final Logger LOGGER = Logger.getLogger(MapViewUtil.class.getName());
  
  /**
   * Private constructor as this is a utility class.
   */
  private MapViewUtil() {
    
  }
  
  public static WGS84BoundingBox getVisibleMapCoordinates(MapView mapView) {
    BaseMap baseMap = mapView.getBaseMap();
    baseMap.centerLat().doubleValue();
    
    double height = baseMap.getParent().getLayoutBounds().getHeight();
    double width = baseMap.getParent().getLayoutBounds().getWidth();
    
    Point2D center = baseMap.getMapPoint(baseMap.centerLat().doubleValue(), baseMap.centerLon().doubleValue());
    
    MapPoint bottomLeft = mapView.getMapPosition(center.getX() - width / 2, center.getY() - height / 2);
    MapPoint topRight = mapView.getMapPosition(center.getX() + width / 2, center.getY() + height / 2);
    LOGGER.severe("Bottom left: " + bottomLeft.getLatitude() + ", " + bottomLeft.getLongitude());
    LOGGER.severe("Top right: " + topRight.getLatitude() + ", " + topRight.getLongitude());
    
    return new WGS84BoundingBox(bottomLeft.getLongitude(), topRight.getLatitude(), topRight.getLongitude(), bottomLeft.getLatitude());
  }
}
