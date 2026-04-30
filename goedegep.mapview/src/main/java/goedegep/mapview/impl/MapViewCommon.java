package goedegep.mapview.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.geo.WGS84BoundingBox;
import goedegep.mapview.MapLayer;
import goedegep.mapview.MapPoint;
import goedegep.mapview.MapViewAbstract;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 * This class implements the common functionality of a map view.
 * <p>
 * This class maintains the main parameters of the map; the center location, the zoom level and the dimensions.
 * It provides a base map, on top of which map layers can be added.
 * The base map is responsible for rendering the map tiles, while the map layers are responsible for rendering additional information on top of the base map.
 * 
 * Initialization
 * Initialization cannot be done from the constructor. The constructor of this class is called from the subclass constructor. At that time the subclass isn't initialized yet, so we can't call methods of the subclass.
 * Therefore initialization is done when the MapView gets a Parent, or when the width and height are set for the MapImage.
 * 
 * Sequences
 * 
 * MapView initialization
 * It starts of course when the MapView constructor is called.
 * This calls its parents constructor (MapViewCommon), which:
 * * Creates a clipping rectangle and sets this on the Region.
 * * Adds listeners to the prefCenter and prefZoom, which call doSetCenter() and doZoom() respectively.
 * Next it creates a BaseMap and adds this as a child. The BaseMap constructor calls its parents constructor (BaseMapAbstract), which initializes the tiles HashMap array.
 * It calls registerInputListeners(), which registers listeners on mouse events for panning and zooming the map.
 * A listener is added to the center, which calls markDirty(). TODO I think this can go.
 * A listener is added to the parentProperty. If there is a parent, a listener is added to its layoutBoundsProperty. This listener takes care of setting the width and height. Also now the initialize() method is called.
 * 
 * In a sample application, the MapView is added to an HBox which is part of a Stage. When show() is called on the Stage, layoutChildren() is called. This just returns because the MapView isn't initialized yet.
 * After this the layoutBounds of the parent are set, the width and height of the MapView are set, and initialize() is called. This sets the center to (0, 0) and sets initialized to true.
 * 
 */
public abstract class MapViewCommon extends MapViewAbstract {
//  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MapViewCommon.class.getName());

  
  /**
   * The center point of the map. Latitude and longitude in degrees.
   */
  public final ReadOnlyObjectWrapper<MapPoint> center = new ReadOnlyObjectWrapper<>();  // TODO Don't make public
  
  
  /**
   * the zoom level of the map.
   */
  protected final ReadOnlyDoubleWrapper zoom = new ReadOnlyDoubleWrapper();
  
  /**
   * Dimension (width and height) of the map.
   */
  protected Dimension2D dimension;
  
  /**
   * The value to which the center has to change.
   * Main reason why this is a {@link javafx.beans.property.Property} is that it can be used in a {@link javafx.animation.KeyValue} of an animation
   * like flyTo().
   */
  public final ObjectProperty<MapPoint> prefCenter = new SimpleObjectProperty<>();
  
  /**
   * The value to which the zoom level has to change.
   * Main reason why this is a {@link javafx.beans.property.Property} is that it can be used in a {@link javafx.animation.KeyValue} of an animation
   * like flyTo().
   */
  protected final DoubleProperty prefZoom  = new SimpleDoubleProperty();
  

  /*
   *  Node clipping rectangle.
   */
  private final Rectangle clip;

  
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
  
  private boolean dirty = false;
  
  /**
   * Constructor.
   */
  public MapViewCommon() {
    
    clip = new Rectangle();
    this.setClip(clip);

    prefCenter.addListener(_ -> 
      baseMapAbstract.doSetCenter(prefCenter.get())
    );

    prefZoom.addListener(_ -> doZoom(prefZoom.get()));
    
    center.addListener(_ -> {  // TODO Temp
      LOGGER.severe("centerLat updated: " + center.get());
    });
    
  }
  
  /**
   * Initialize the map view.
   */
  protected void initialize() {
    LOGGER.severe("=> initialized: " + initialized);
    if (initialized) {
      return;
    }
    
    baseMapAbstract.doSetCenter(new MapPoint(0, 0));
    
    initialized = true;
    baseMapAbstract.initialized = true;
    
    baseMapAbstract.markDirty();
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
  
  public ReadOnlyDoubleProperty zoomReadOnlyProperty() {
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
    prefCenter.set(new MapPoint(latitude, longitude));
//    prefCenterLat.set(latitude);
//    prefCenterLon.set(longitude);
  }
  
  
//  public DoubleProperty prefCenterLon() {
//      return prefCenterLon;
//  }
  
  @Override
  public Dimension2D getDimension() {
    return dimension;
  }

  //  public DoubleProperty prefCenterLat() {
//      return prefCenterLat;
//  }
  public abstract WGS84BoundingBox getVisibleMapBoundingBox();

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
    
    clip.setWidth(dimension.getWidth());
    clip.setHeight(dimension.getHeight());
    
    baseMapAbstract.layoutChildren();

    for (MapLayer layer : layers) {
      layer.layoutLayer();
    }
    
    super.layoutChildren();
    
    dirty = false;
  }
  
  /**
   * Get the position on the map, as a [Point2D] with scene coordinates, for the given zoom level, latitude and longitude.
   *
   * @param zoom zoom level
   * @param lat latitude
   * @param lon longitude
   * @return a [Point2D] with the scene coordinates for the given zoom level, latitude and longitude.
   */
  public Point2D getMapPoint(double zoom, double lat, double lon) {
    return baseMapAbstract.getMapPoint(zoom, lat, lon);
  }
  
  @Override
  public String getStatusInformation() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(baseMapAbstract.getStatusInformation());
    
    return buf.toString();
  }

  public void markDirty() {
    LOGGER.severe("=> markDirty");
    
    dirty = true;

    setNeedsLayout(true);
    Platform.requestNextPulse();
    
    LOGGER.severe("<= markDirty");
  }
}
