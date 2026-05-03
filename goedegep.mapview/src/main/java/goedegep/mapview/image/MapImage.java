package goedegep.mapview.image;

import java.util.logging.Logger;

import goedegep.geo.WGS84BoundingBox;
import goedegep.mapview.MapPoint;
import goedegep.mapview.image.impl.BaseMap;
import goedegep.mapview.impl.MapViewCommon;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

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
  
  /**
   * Set the size of the map image. This method sets the width and height of the map image to the specified values.
   *
   * @param width  the width of the map image
   * @param height the height of the map image
   */
  public void setSize(double width, double height) {
    super.setDimensions(width, height);
    setWidth(width);
    setHeight(height);
    setPrefWidth(width);
    setPrefHeight(height);
    setMinWidth(width);
    setMinHeight(height);
    initialize();
  }

  @Override
  public WGS84BoundingBox getVisibleMapBoundingBox() {
    return baseMapAbstract.getVisibleMapCoordinates();
  }

  @Override
  public Point2D getMapPoint(double lat, double lon) {
    return baseMapAbstract.getMapPoint(lat, lon);
  }
}
