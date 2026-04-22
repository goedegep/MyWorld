package goedegep.travels.gui;

import java.util.logging.Logger;

import goedegep.geo.WGS84BoundingBox;
import goedegep.mapview.MapLayer;
import goedegep.mapview.MapPoint;
import goedegep.mapview.MapViewUtil;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class MapViewTestLayer extends MapLayer {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MapViewTestLayer.class.getName());

  Circle centerCircle;
  Polygon homePolygon;
  WGS84BoundingBox homeBoundingBox;

  public MapViewTestLayer() {
  }
  
  @Override
  protected void initialize() {
    centerCircle = new Circle(0, 0, 10);
    centerCircle.setFill(Color.BLUE);
    getChildren().add(centerCircle);
    
    //51.476743, 5.429724
    homeBoundingBox = new WGS84BoundingBox(5.429, 51.477, 5.430, 51.476);
    homePolygon = new Polygon();
    homePolygon.setStroke(Color.YELLOW);
    homePolygon.setFill(Color.TRANSPARENT);
    homePolygon.setVisible(true);
    getChildren().add(homePolygon);
    
    markDirty();
  }
  
  @Override
  public void layoutLayer() {
    MapViewUtil.updateBoundingBoxPolygon(homePolygon, homeBoundingBox, mapViewAbstract);
    
    MapPoint centerMapPoint = mapViewAbstract.getCenter();
    Point2D centerPoint2D = mapViewAbstract.getMapPoint(centerMapPoint.getLatitude(), centerMapPoint.getLongitude());
//    LOGGER.severe("center: " + center.getX() + ", " + center.getY());
    centerCircle.setCenterX(centerPoint2D.getX());
    centerCircle.setCenterY(centerPoint2D.getY());
  }
}
