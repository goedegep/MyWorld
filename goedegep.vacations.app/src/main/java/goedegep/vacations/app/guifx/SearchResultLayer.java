package goedegep.vacations.app.guifx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;

import goedegep.geo.WGS84Coordinates;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.util.Pair;

public class SearchResultLayer extends MapLayer {
  private static final Logger         LOGGER = Logger.getLogger(SearchResultLayer.class.getName());
  
  private Integer locationId = 0;
  private Map<Integer, Pair<Double[], Polygon>> boundingBoxesMap = new HashMap<>();
  private Map<Integer, Pair<MapPoint, Node>> locationsMap = new HashMap<>();
  private Map<Integer, List<Pair<List<WGS84Coordinates>, Polyline>>> polylinesMap = new HashMap<>();
  

  public SearchResultLayer() {
      super();
  }
  
  public Integer addLocation(Double lat, Double lon, Double[] boundingBox, List<List<WGS84Coordinates>> polylines) {
    LOGGER.severe("polylines: " + (polylines != null ? polylines.size() : "<null>"));
    locationId++;
    
    if (lat != null  &&  lon != null) {
      MapPoint mapPoint = new MapPoint(lat, lon);
      
      final Circle icon = new Circle(5, Color.BLUE);
      icon.setVisible(true);
      icon.setStroke(Color.RED);
      icon.setStrokeWidth(1);
                    
      Pair<MapPoint, Node> locationPair = new Pair<MapPoint, Node>(mapPoint, icon);
      locationsMap.put(locationId, locationPair);
      this.getChildren().add(icon);
   }
    
    boolean polylineDrawn = false;
    if (polylines != null) {
      List<Pair<List<WGS84Coordinates>, Polyline>> pairs = new ArrayList<>();
      for (List<WGS84Coordinates> polylineCoordinates: polylines) {
        if (polylineCoordinates != null  &&  !polylineCoordinates.isEmpty()) {
          Polyline polyline = new Polyline();
          polyline.setStroke(Color.GREEN);
          polyline.setStrokeWidth(3.0);
          polyline.setVisible(true);
          pairs.add(new Pair<>(polylineCoordinates, polyline));
          getChildren().add(polyline);
          LOGGER.severe("Polyline added");
          polylineDrawn = true;
        }
      }
      polylinesMap.put(locationId, pairs);
    }
    
    if (!polylineDrawn  &&  boundingBox != null) {
      if (boundingBox.length != 4) {
        throw new IllegalArgumentException("A bounding box shall have 4 values, parameter has: " + boundingBox.length);
      }
      
      
      Polygon boxPolygon = new Polygon();
      boxPolygon.setVisible(true);
      boxPolygon.setStrokeWidth(2);
      boxPolygon.setStroke(Color.BLUE);
      Paint fillPaint = Color.rgb(100, 100, 220, 0.1);
      boxPolygon.setFill(fillPaint);
      
      Pair<Double[], Polygon> boundingBoxPair = new Pair<Double[], Polygon>(boundingBox, boxPolygon);
      boundingBoxesMap.put(locationId, boundingBoxPair);
      this.getChildren().add(boxPolygon);
    }

    this.markDirty();
    
    return locationId;
  }
  
  public void removeLocation(Integer locationId) {
    Pair<MapPoint, Node> locationPair = locationsMap.remove(locationId);
    this.getChildren().remove(locationPair.getValue());
    
    Pair<Double[], Polygon> boundingBoxPair = boundingBoxesMap.remove(locationId);
    if (boundingBoxPair != null) {
      this.getChildren().remove(boundingBoxPair.getValue());
    }
    
    List<Pair<List<WGS84Coordinates>, Polyline>> polylinePairs = polylinesMap.remove(locationId);
    
    if (polylinePairs != null) {
      for (Pair<List<WGS84Coordinates>, Polyline> polylinePair: polylinePairs) {
        this.getChildren().remove(polylinePair.getValue());
      }
    }
  }

  public void clear() {
    LOGGER.info("=>");
    
    polylinesMap.clear();
    getChildren().clear();
    markDirty();

    LOGGER.info("<=");
  }
  
  @Override
  protected void layoutLayer() {
    for (Pair<Double[], Polygon> boundingBoxPair: boundingBoxesMap.values()) {
      Double[] boundingBoxCoords = boundingBoxPair.getKey();
      Polygon boxPolygon = boundingBoxPair.getValue();
      
      ObservableList<Double> points = boxPolygon.getPoints();
      points.clear();
      Point2D mapPoint;
      mapPoint = baseMap.getMapPoint(boundingBoxCoords[0], boundingBoxCoords[2]);
      points.add(mapPoint.getX());
      points.add(mapPoint.getY());
      mapPoint = baseMap.getMapPoint(boundingBoxCoords[0], boundingBoxCoords[3]);
      points.add(mapPoint.getX());
      points.add(mapPoint.getY());
      mapPoint = baseMap.getMapPoint(boundingBoxCoords[1], boundingBoxCoords[3]);
      points.add(mapPoint.getX());
      points.add(mapPoint.getY());
      mapPoint = baseMap.getMapPoint(boundingBoxCoords[1], boundingBoxCoords[2]);
      points.add(mapPoint.getX());
      points.add(mapPoint.getY());
    }
    
    for (Pair<MapPoint, Node> locationPair: locationsMap.values()) {
      MapPoint point = locationPair.getKey();
      Node icon = locationPair.getValue();
      final Point2D mapPoint = baseMap.getMapPoint(point.getLatitude(), point.getLongitude());
      icon.toFront();
      icon.setTranslateX(mapPoint.getX());
      icon.setTranslateY(mapPoint.getY());
    }    
    
    for (List<Pair<List<WGS84Coordinates>, Polyline>> polylinePairList: polylinesMap.values()) {
      for (Pair<List<WGS84Coordinates>, Polyline> polylinePair: polylinePairList) {
        List<WGS84Coordinates> coordinatesList = polylinePair.getKey();
        Polyline polyline = polylinePair.getValue();
        ObservableList<Double> points = polyline.getPoints();
        points.clear();
        for (WGS84Coordinates coordinates: coordinatesList) {
          Point2D point2D = baseMap.getMapPoint(coordinates.getLatitude(), coordinates.getLongitude());
          points.add(point2D.getX());
          points.add(point2D.getY());
        }
      }
    }
  }
}
