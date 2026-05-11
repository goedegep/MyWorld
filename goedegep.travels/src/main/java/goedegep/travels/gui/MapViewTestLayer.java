package goedegep.travels.gui;

import java.util.logging.Logger;

import goedegep.geo.WGS84BoundingBox;
import goedegep.mapview.MapLayer;
import goedegep.mapview.MapPoint;
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
    centerCircle = new Circle(0, 0, 4);
    centerCircle.setFill(Color.BLUE);
    getChildren().add(centerCircle);
    
    //51.476743, 5.429724
    homeBoundingBox = new WGS84BoundingBox(5.35, 51.55, 5.62, 51.38);
    homePolygon = new Polygon();
    homePolygon.setStroke(Color.YELLOW);
    homePolygon.setFill(Color.TRANSPARENT);
    homePolygon.setStrokeWidth(8.0);
    homePolygon.setVisible(true);
    getChildren().add(homePolygon);
    
    markDirty();
  }
  
  @Override
  public void layoutLayer() {
    WGS84BoundingBox visibleArea = mapViewAbstract.getVisibleMapBoundingBox();
    if (visibleArea.containsAtLeastPartly(homeBoundingBox)) {
      mapViewAbstract.updateBoundingBoxPolygon(homePolygon, homeBoundingBox);
      homePolygon.setVisible(true);
    } else {
      homePolygon.getPoints().clear();
      homePolygon.setVisible(false);
    }
    
    MapPoint centerMapPoint = mapViewAbstract.getCenter();
    Point2D centerPoint2D = mapViewAbstract.getMapPoint(centerMapPoint.getLatitude(), centerMapPoint.getLongitude());
    centerCircle.setCenterX(centerPoint2D.getX());
    centerCircle.setCenterY(centerPoint2D.getY());
  }
}
