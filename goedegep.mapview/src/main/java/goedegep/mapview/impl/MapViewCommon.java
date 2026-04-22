package goedegep.mapview.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.mapview.MapLayer;
import goedegep.mapview.MapPoint;
import goedegep.mapview.MapViewAbstract;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * This class implements the common functionality of a map view.
 * <p>
 * This class maintains the main parameters of the map, such as the center location and the zoom level.
 * It provides a base map, on top of which map layers can be added.
 * The base map is responsible for rendering the map tiles, while the map layers are responsible for rendering additional information on top of the base map.
 */
public abstract class MapViewCommon extends MapViewAbstract {
//  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MapViewCommon.class.getName());

  
  /**
   * The center point of the map. Latitude and longitude in degrees.
   */
  public final ReadOnlyObjectWrapper<MapPoint> center = new ReadOnlyObjectWrapper();  // TODO Don't make public
  
//  /**
//   *  the latitude of the center of the map in degrees.
//   */
//  protected final ReadOnlyDoubleWrapper centerLat = new ReadOnlyDoubleWrapper();
  
//  /**
//   *  the longitude of the center of the map in degrees.
//   */
//  protected final ReadOnlyDoubleWrapper centerLon = new ReadOnlyDoubleWrapper();
  
  /**
   * the zoom level of the map.
   */
  protected final ReadOnlyDoubleWrapper zoom = new ReadOnlyDoubleWrapper();
  
  /**
   * The value to which the center longitude has to change.
   * Main reason why this is a {@link javafx.beans.property.Property} is that it can be used in a {@link javafx.animation.KeyValue} of an animation
   * like flyTo().
   * TODO rename to prefCenterLongitude.
   */
  private final DoubleProperty prefCenterLon = new SimpleDoubleProperty();
  
  /**
   * The value to which the center latitude has to change.
   * Main reason why this is a {@link javafx.beans.property.Property} is that it can be used in a {@link javafx.animation.KeyValue} of an animation
   * like flyTo().
   * TODO rename to prefCenterLatitude.
   */
  private final DoubleProperty prefCenterLat = new SimpleDoubleProperty();

  /**
   * The value to which the zoom level has to change.
   * Main reason why this is a {@link javafx.beans.property.Property} is that it can be used in a {@link javafx.animation.KeyValue} of an animation
   * like flyTo().
   */
  private final DoubleProperty prefZoom  = new SimpleDoubleProperty();
  

  /*
   *  Node clipping rectangle.
   */
  private final Rectangle clip;

  
  /**
   * {@code TimeLine} used for the flyTo animation.
   */
  private Timeline timeline;
  

  /**
   * The basic map (map tiles).
   */
  protected BaseMapAbstract<?> baseMapAbstract;
  
  /**
   * Layers of information shown on top of the {@code baseMap}.
   */
  private final List<MapLayer> layers = new LinkedList<>();
  
  /**
   * Indicates whether the map view has been initialized.
   * <p>
   * All the calculations for the map view and the loading of tiles can only be done if our {@code Region) has a width and height, which is not the case when the map view is created.
   * The center and zoom can be set before we have a width and height, but the map view will only be initialized when we have a width and height.
   * We listen to changes in the width and height, and when we have a width and height, we initialize the map view.
   */
  private boolean initialized = false;
  
  /**
   * Constructor.
   */
  public MapViewCommon() {
    LOGGER.severe("=>");

    clip = new Rectangle();
    this.setClip(clip);

    prefCenterLat.addListener(_ -> 
    baseMapAbstract.doSetCenter(new MapPoint(prefCenterLat.get(), prefCenterLon.get()))
    );
    prefCenterLon.addListener(_ -> baseMapAbstract.doSetCenter(new MapPoint(prefCenterLat.get(), prefCenterLon.get())));
    prefZoom.addListener(_ -> doZoom(prefZoom.get()));
    
    center.addListener(_ -> {
      LOGGER.severe("centerLat updated: " + center.get());
    });

    parentProperty().addListener((_, _, _) ->        {
      initialize();
    });
    LOGGER.severe("<= centerLat: " + center.get());
  }
  
  private void initialize() {
    if (initialized) {
      return;
    }
    
    double width = getWidth();
    double height = getHeight();
    LOGGER.severe("initialize, width: " + width + ", height: " + height);
    
    baseMapAbstract.doSetCenter(new MapPoint(prefCenterLat.get(), prefCenterLon.get()));
    
    widthProperty().addListener(_ -> {
      LOGGER.severe("Width changed, width: " + getWidth());
      baseMapAbstract.doSetCenter(new MapPoint(prefCenterLat.get(), prefCenterLon.get()));
    });
    
    heightProperty().addListener(_ -> {
      LOGGER.severe("Height changed, height: " + getHeight());
      baseMapAbstract.doSetCenter(new MapPoint(prefCenterLat.get(), prefCenterLon.get()));
    });
  }

  @Override
  public void setZoom(double zoom) {
    if (zoom < 0) {
      zoom = 0; 
    }
    if (zoom > BaseMapAbstract.getMaximumZoomLevel()) {
      zoom = BaseMapAbstract.getMaximumZoomLevel();
    }
    
    // Set prefZoom, which will result in a call to doZoom().
    prefZoom.set(zoom);
  }

  /**
   * Returns the zoom level of the map.
   * @return the zoom level of the map.
   */
  public double getZoom() {
    return prefZoom.get();
//    return zoom.get();
  }
  
  public ReadOnlyDoubleProperty zoom() {
    return zoom.getReadOnlyProperty();
  }
  
//  /**
//   *  Get the latitude of the center point of the map.
//   * @return the latitude of the center point of the map.
//   */
//  public double getCenterLatitude() {
//    return centerLat.get();
//  }
  
//  /**
//   * Get the property representing the latitude of the center point of the map.
//   * @return the property representing the latitude of the center point of the map.
//   */
//  protected DoubleProperty centerLatitudeProperty() {
//    return centerLat;
//  }
  
//  /**
//   * Get the read-only property representing the latitude of the center point of the map.
//   * @return the read-only property representing the latitude of the center point of the map.
//   */
//  public ReadOnlyDoubleProperty centerLatitudeReadOnlyProperty() {
//    return centerLat.getReadOnlyProperty();
//  }
  
//  /**
//   * Get the longitude of the center point of the map.
//   * @return the longitude of the center point of the map.
//   */
//  public double getCenterLongitude() {
//    return centerLon.get();
//  }
  
//  /**
//   * Get the property representing the longitude of the center point of the map.
//   * @return the property representing the longitude of the center point of the map.
//   */
//  protected DoubleProperty centerLongitudeProperty() {
//    return centerLon;
//  }
  
//  /**
//   * Get the read-only property representing the longitude of the center point of the map.
//   * @return the read-only property representing the longitude of the center point of the map.
//   */
//  public ReadOnlyDoubleProperty centerLongitudeReadOnlyProperty() {
//    return centerLon.getReadOnlyProperty();
//  }
  
  /**
   * Get the center point of the map.
   * @return the center point of the map.
   */
  public MapPoint getCenter() {
    return center.get();
  }
  
  /**
   * Set the center point of the map to the specified latitude and longitude.
   * 
   * @param latitude the latitude of the center point of the map in degrees.
   * @param longitude the longitude of the center point of the map in degrees.
   */
  public void setCenter(double latitude, double longitude) {
    prefCenterLat.set(latitude);
    prefCenterLon.set(longitude);
  }
  
  public DoubleProperty prefCenterLon() {
      return prefCenterLon;
  }
  
  public DoubleProperty prefCenterLat() {
      return prefCenterLat;
  }

  @Override
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
    
    timeline = new Timeline(
        new KeyFrame(Duration.ZERO, new KeyValue(prefCenterLat, currentLat), new KeyValue(prefCenterLon, currentLon), new KeyValue(prefZoom, zoom.get())),
        new KeyFrame(Duration.seconds(t1), new KeyValue(prefCenterLat, currentLat), new KeyValue(prefCenterLon, currentLon), new KeyValue(prefZoom, zoom.get())),
        new KeyFrame(Duration.seconds(t2), new KeyValue(prefCenterLat, currentLat), new KeyValue(prefCenterLon, currentLon), new KeyValue(prefZoom, zoomLevel)),
        new KeyFrame(Duration.seconds(t3), new KeyValue(prefCenterLat, mapPoint.getLatitude()), new KeyValue(prefCenterLon, mapPoint.getLongitude(), Interpolator.EASE_BOTH), new KeyValue(prefZoom, zoomLevel)),
        new KeyFrame(Duration.seconds(t4), new KeyValue(prefCenterLat, mapPoint.getLatitude()), new KeyValue(prefCenterLon, mapPoint.getLongitude()), new KeyValue(prefZoom, endZoom))
        );
    timeline.play();
  }

  public abstract WGS84BoundingBox getVisibleMapCoordinates();

  /**
   * Zoom to a specific zoom level.
   * 
   * @param z the zoom level to use.
   */
  private void doZoom(double z) {
      zoom.set(z);
//      baseMapAbstract.doSetCenter(baseMapAbstract.lat, baseMapAbstract.lon);
      baseMapAbstract.doSetCenter(center.get());
      baseMapAbstract.markDirty();
  }

  /**
   * Add a new layer on top of this map. Layers are displayed in order of
   * addition, with the last added layer to be on top
   *
   * @param mapLayer
   */
  public void addLayer(MapLayer mapLayer) {
    mapLayer.setMapView(this);
    layers.add(mapLayer);
    this.getChildren().add(mapLayer);
  }
  
  /**
   * Removes the specified layer from the map
   *
   * @param mapLayer
   */
  public void removeLayer(MapLayer mapLayer) {
    layers.remove(mapLayer);
    this.getChildren().remove(mapLayer);
  }

  @Override
  protected void layoutChildren() {
    
    final double w = getWidth();
    final double h = getHeight();
    LOGGER.severe("=> layoutChildren: w=" + w + ", h=" + h);

    clip.setWidth(w);
    clip.setHeight(h);

    for (MapLayer layer : layers) {
      layer.layoutLayer();
    }
    super.layoutChildren();
  }
}
