package goedegep.mapview.view;

import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.geo.WGS84Coordinates;
import goedegep.mapview.impl.MapViewCommon;
import goedegep.mapview.view.impl.BaseMap;
import goedegep.mapview.view.impl.TileImageView;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 *
 * This is the top UI element of the map component. The center location and the
 * zoom level of the map can be altered by input events (mouse/touch/gestures)
 * or by calling the methods setCenter and setZoom.
 */
public class MapView extends MapViewCommon {
//  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MapView.class.getName());
  
  /**
   * X, Y coordinates of the start of a drag event, used to calculate the delta when dragging the map.
   */
  private double x0;
  private double y0;

  /**
   * With mouse events and gestures we can be either dragging or zooming, but not both at the same time.
   */
  private boolean zooming = false;
  private boolean enableDragging = false;
  
  /**
   * {@code TimeLine} used for the flyTo animation.
   */
  private Timeline timeline;

  /**
   * Constructor.
   */
  public MapView() {
    super();
    
    baseMapAbstract = new BaseMap(this);
    getChildren().add(baseMapAbstract);
    registerInputListeners();

    parentProperty().addListener((_, _, _) ->        {
      getParent().layoutBoundsProperty().addListener(observable -> {
        if (observable instanceof ReadOnlyObjectProperty property) {
          Object object = property.get();
          if (object instanceof BoundingBox boundingBox) {
            setDimensions(boundingBox.getWidth(), boundingBox.getHeight());
            if (boundingBox.getWidth() != 0  &&  boundingBox.getHeight() != 0) {
              initialize();
            }
          }
        }
      });
    });
  }

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
  public void flyTo(double waitTime, WGS84Coordinates mapPoint, double seconds, Double endZoom) {
    if ((timeline != null) && (timeline.getStatus() == Status.RUNNING)) {
      timeline.stop();
    }
    
    if (endZoom == null) {
      endZoom = getZoom();
    }
    
    // calculate flying distance.
    WGS84Coordinates flyToPoint = new WGS84Coordinates(mapPoint.getLatitude(), mapPoint.getLongitude());
    WGS84Coordinates center = getCenter();
    WGS84Coordinates currentCenter = new WGS84Coordinates(center.getLatitude(), center.getLongitude());
    double flyingDistance = currentCenter.getDistanceMeters(flyToPoint) / 1000.0;
    
    // As each zoom level is 2 times more zoomed in than the previous one, we can calculate the zoom level for flying as follows:
    double d1 = Math.pow(flyingDistance, 0.15);
    double zoomLevel = 20 / d1;
    
    if (zoomLevel < 0) {
      LOGGER.severe("Value too low for zoomLevel: " + zoomLevel);
      zoomLevel = 0;
    }
    if (zoomLevel > 20) {
      LOGGER.severe("Value too high for zoomLevel: " + zoomLevel);
      zoomLevel = 20;
    }
    
    // Don't fly at calculated zoom level and then zoom out at the end.
    // So if endZoom is lower than the calculated zoom level, use endZoom as the zoom level for flying.
    if (endZoom < zoomLevel) {
      zoomLevel = endZoom;
    }
    
    // Also we don't zoom in before flying.
    if (zoomLevel > getZoom()) {
      zoomLevel = getZoom();
    }
    
    double zoomOutTime = 0.5 * (getZoom() - zoomLevel);
    double zoomInTime = 0.5 * (endZoom - zoomLevel);
    
    double t0 = 0;         // start time, current location and zoom level
    double t1 = t0 + waitTime;  // t0 - t1 waiting, current location and zoom level
    double t2 = t1 + zoomOutTime; // t1 - t2 zooming out, current location and zoomLevel
    double t3 = t2 + 3; // t2 - t3 flying, mapPoint and zoom level is zoomLevel
    double t4 = t3 + zoomInTime; // t3 - t4 zooming in, mapPoint and zoom level is endZoom
    
    double currentLat = center.getLatitude();
    double currentLon = center.getLongitude();
    
    // Use prefCenter (WGS84Coordinates) as the target of the animation. A custom Interpolator
    // is provided to interpolate between two WGS84Coordinates instances by linearly
    // interpolating latitude and longitude.
    Interpolator mapPointInterpolator = new Interpolator() {
      @Override
      public double curve(double t) {
        // linear progression
        return t;
      }

      @Override
      public Object interpolate(Object startValue, Object endValue, double frac) {
        WGS84Coordinates s = (WGS84Coordinates) startValue;
        WGS84Coordinates e = (WGS84Coordinates) endValue;
        double lat = s.getLatitude() + (e.getLatitude() - s.getLatitude()) * frac;
        double lon = s.getLongitude() + (e.getLongitude() - s.getLongitude()) * frac;
        return new WGS84Coordinates(lat, lon);
      }
    };
    
    timeline = new Timeline(
        new KeyFrame(Duration.ZERO, new KeyValue(prefCenterProperty, new WGS84Coordinates(currentLat, currentLon)), new KeyValue(prefZoomProperty, zoomProperty.get())),
        new KeyFrame(Duration.seconds(t1), new KeyValue(prefCenterProperty, new WGS84Coordinates(currentLat, currentLon)), new KeyValue(prefZoomProperty, zoomProperty.get())),
        new KeyFrame(Duration.seconds(t2), new KeyValue(prefCenterProperty, new WGS84Coordinates(currentLat, currentLon)), new KeyValue(prefZoomProperty, zoomLevel)),
        new KeyFrame(Duration.seconds(t3), new KeyValue(prefCenterProperty, mapPoint, mapPointInterpolator), new KeyValue(prefZoomProperty, zoomLevel)),
        new KeyFrame(Duration.seconds(t4), new KeyValue(prefCenterProperty, mapPoint), new KeyValue(prefZoomProperty, endZoom))
        );
    timeline.play();
  }

  /**
   * Set a supplier of an Image that can be used as placeholder by the Tile
   * while the final image is being retrieved
   *
   * @param supplier a supplier that provides a placeholder Image
   */
  public static void setPlaceholderImageSupplier(Supplier<Image> supplier) {
    TileImageView.setPlaceholderImageSupplier(supplier);
  }


  /**
   * Install mouse listeners to support dragging and zooming the map.
   */
  private void registerInputListeners() {
    // Handle possible start of a drag event.
    setOnMousePressed(t -> {
      if (zooming) return;
      
      // Save x, y for a possible drag event.
      x0 = t.getX();
      y0 = t.getY();
      // dragging is enabled only after a pressed event, to prevent dragging right after zooming
      enableDragging = true;
    });
    
    // Handle dragging the map.
    setOnMouseDragged(t -> {
      if (zooming || !enableDragging) {
        return;
      }
      moveXY(x0 - t.getX(), y0 - t.getY());
      x0 = t.getX();
      y0 = t.getY();
    });
    
    // End off dragging when the mouse is released.
    setOnMouseReleased(_ -> enableDragging = false);
    
    // Handle zooming started.
    setOnZoomStarted(_ -> {
      zooming = true;
      enableDragging = false;
    });
    
    // Handle zooming finished.
    setOnZoomFinished(_ -> zooming = false);
    
    // Handle zooming the map. We use the zoom factor to calculate the delta zoom level, and we use the x and y coordinates of the zoom event as the pivot point for zooming.
    setOnZoom(t -> baseMapAbstract.zoom(t.getZoomFactor() - 1, t.getX(), t.getY()));  // zoom factor greater than 1 means zoom in, smaller than 1 means zoom out, so we need to subtract 1 to get the delta.
    
    // Handle zooming with the mouse wheel.
    setOnScroll(t -> {
      // Using the mouse wheel, we zoom in or out by a fixed .1 step.
      final double delta;
      if (t.isControlDown()) {
        delta = t.getDeltaY() > 1 ? 1.0 : t.getDeltaY() < -1 ? -1.0 : 0;
      } else {
        delta = t.getDeltaY() > 1 ? .1 : t.getDeltaY() < -1 ? -.1 : 0;
      }
      baseMapAbstract.zoom(delta, t.getX(), t.getY());
    });
  }
  
  /**
   * Move the map by the specified delta in x and y direction.
   * 
   * @param deltaX the distance to move in x direction
   * @param deltaY the distance to move in y direction
   */
  private void moveXY(double deltaX, double deltaY) {
    baseMapAbstract.moveXY(deltaX, deltaY);
  }

  /**
   * Move the map by the specified delta in x direction.
   * 
   * @param distance the distance to move in x direction
   */
  public void moveX(double distance) {
    moveXY(distance, 0);
  }
  
  /**
   * Move the map by the specified delta in y direction.
   * 
   * @param distance the distance to move in y direction
   */
  public void moveY(double distance) {
    moveXY(0, distance);
  }
}
