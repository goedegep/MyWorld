package goedegep.mapview.view;

import java.util.function.Supplier;
import java.util.logging.Logger;

import com.gluonhq.attach.util.Platform;

import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.mapview.MapPoint;
import goedegep.mapview.impl.MapViewCommon;
import goedegep.mapview.view.impl.BaseMap;
import goedegep.mapview.view.impl.TileImageView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.BoundingBox;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
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
  public double x0, y0;

//  private MapPoint centerPoint = null;
  private boolean zooming = false;
  private boolean enableDragging = false;
  
  /**
   * {@code TimeLine} used for the flyTo animation.
   */
  private Timeline timeline;

  /**
   * Create a MapView component.
   */
  public MapView() {
    super();
    
    baseMapAbstract = new BaseMap(this);
    getChildren().add(baseMapAbstract);
    registerInputListeners();

    center.addListener(_ -> markDirty());

    parentProperty().addListener((_, _, _) ->        {
      getParent().layoutBoundsProperty().addListener(observable -> {
        if (observable instanceof ReadOnlyObjectProperty) {
          ReadOnlyObjectProperty<?> property = (javafx.beans.property.ReadOnlyObjectProperty<?>) observable;
          Object object = property.get();
          if (object instanceof BoundingBox boundingBox) {
            dimension = new Dimension2D(boundingBox.getWidth(), boundingBox.getHeight());
            LOGGER.severe("Parent layoutBounds changed, dimension: " + dimension);
            if (boundingBox.getWidth() != 0  &&  boundingBox.getHeight() != 0) {
              initialize();
            }
          } else {
            LOGGER.severe("Parent layoutBounds changed, but value is not a BoundingBox: " + object);
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
  public void flyTo(double waitTime, MapPoint mapPoint, double seconds, Double endZoom) {
    if ((timeline != null) && (timeline.getStatus() == Status.RUNNING)) {
      timeline.stop();
    }
    
    if (endZoom == null) {
      endZoom = getZoom();
    }
    
    // calculate flying distance.
    WGS84Coordinates flyToPoint = new WGS84Coordinates(mapPoint.getLatitude(), mapPoint.getLongitude());
    MapPoint center = getCenter();
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
//    LOGGER.severe("flyingDistance: " + flyingDistance + " km, zoomLevel: " + zoomLevel);
    
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
    
    // Use prefCenter (MapPoint) as the target of the animation. A custom Interpolator
    // is provided to interpolate between two MapPoint instances by linearly
    // interpolating latitude and longitude.
    Interpolator mapPointInterpolator = new Interpolator() {
      @Override
      public double curve(double t) {
        // linear progression
        return t;
      }

      @Override
      public Object interpolate(Object startValue, Object endValue, double frac) {
        MapPoint s = (MapPoint) startValue;
        MapPoint e = (MapPoint) endValue;
        double lat = s.getLatitude() + (e.getLatitude() - s.getLatitude()) * frac;
        double lon = s.getLongitude() + (e.getLongitude() - s.getLongitude()) * frac;
        return new MapPoint(lat, lon);
      }
    };
    
    timeline = new Timeline(
        new KeyFrame(Duration.ZERO, new KeyValue(prefCenter, new MapPoint(currentLat, currentLon)), new KeyValue(prefZoom, zoom.get())),
        new KeyFrame(Duration.seconds(t1), new KeyValue(prefCenter, new MapPoint(currentLat, currentLon)), new KeyValue(prefZoom, zoom.get())),
        new KeyFrame(Duration.seconds(t2), new KeyValue(prefCenter, new MapPoint(currentLat, currentLon)), new KeyValue(prefZoom, zoomLevel)),
        new KeyFrame(Duration.seconds(t3), new KeyValue(prefCenter, mapPoint, mapPointInterpolator), new KeyValue(prefZoom, zoomLevel)),
        new KeyFrame(Duration.seconds(t4), new KeyValue(prefCenter, mapPoint), new KeyValue(prefZoom, endZoom))
        );
    timeline.play();
  }


  /**
   * Install mouse listeners to support dragging and zooming the map.
   */
  private void registerInputListeners() {
    // Save x, y for a possible drag event.
    setOnMousePressed(t -> {
      if (zooming) return;
      x0 = t.getX();
      y0 = t.getY();
//      centerPoint = null; // once the user starts moving, we don't track the center anymore.
      // dragging is enabled only after a pressed event, to prevent dragging right after zooming
      enableDragging = true;
    });
    
    // Handle dragging the map.
    setOnMouseDragged(t -> {
      if (zooming || !enableDragging) {
        return;
      }
      baseMapAbstract.moveX(x0 - t.getX());
      baseMapAbstract.moveY(y0 - t.getY());
      x0 = t.getX();
      y0 = t.getY();
    });
    
    // End off dragging when the mouse is released.
    setOnMouseReleased(t -> enableDragging = false);
    
    setOnZoomStarted(t -> {
      zooming = true;
      enableDragging = false;
    });
    setOnZoomFinished(t -> zooming = false);
    setOnZoom(t -> baseMapAbstract.zoom(t.getZoomFactor() - 1, t.getX(), t.getY()));  // zoom factor greater than 1 means zoom in, smaller than 1 means zoom out, so we need to subtract 1 to get the delta.
    if (Platform.isDesktop()) {
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
   * Request the map to position itself around the specified center
   *
   * @param mapPoint
   */
  public void setCenter(MapPoint mapPoint) {
    setCenter(mapPoint.getLatitude(), mapPoint.getLongitude());
  }

  //    /**
  //     * Returns the center point of this map
  //     * @return the center point
  //     */
  //    public MapPoint getCenter() {
  //        Point2D center = baseMapAbstract.getCenter();
  //        return new MapPoint(center.getX(), center.getY());
  //    }

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
   * Set a supplier of an Image that can be used as placeholder by the Tile
   * while the final image is being retrieved
   *
   * @param supplier a supplier that provides a placeholder Image
   */
  public static void setPlaceholderImageSupplier(Supplier<Image> supplier) {
    TileImageView.setPlaceholderImageSupplier(supplier);
  }

  private boolean dirty = false;

  public void markDirty() {
    dirty = true;
    this.setNeedsLayout(true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void layoutChildren() {
    LOGGER.severe("=>");

    super.layoutChildren();
    dirty = false;

    // we need to get these values or we won't be notified on new changes
    center.get();
  }

  @Override
  public WGS84BoundingBox getVisibleMapBoundingBox() {
    return baseMapAbstract.getVisibleMapCoordinates();
  }

  @Override
  public Point2D getMapPoint(double lat, double lon) {
    return baseMapAbstract.getMapPoint(lat, lon);
  }
  
  public void moveX(double distance) {
    baseMapAbstract.moveX(distance);
  }
  
  public void moveY(double distance) {
    baseMapAbstract.moveY(distance);
  }
}
