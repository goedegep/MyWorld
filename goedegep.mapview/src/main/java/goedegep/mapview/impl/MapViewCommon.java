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
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
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
 * BaseMap layout
 * The tiles (TileImageView) calculate their own position in the grid of tiles. Tile [0,0] is at 0,0 in screen coordinates. Tile [1,0] is at 256,0. Tile [0,1] is at 0,256. Tile [1,1] is at 256,256.
 * So the position of tile [i,j] is at (i*256, j*256).
 * However, the tiles can also be scaled, depending on the zoom level. So the position of tile [i,j] is at (i*256*scale, j*256*scale), where scale is 2^(zoom - tileZoom).
 * A full step in the zoom level is a factor 2. The zooming of the tile is based on the difference between the current zoom level and the tile zoom level. Therefore the scale factor is 2^(zoom - tileZoom).
 * 
 * Example
 * A map view with a size of 300 x 150 pixels. This means that only 2 to 6 tiles are needed.
 * The center of the map is at geo coordinates (51.443611, 5.4468137) (the location of the Evoluon in Eindhoven).
 * Zoom level 4 (so the tiles are shown at their normal size).
 * This requires the following tiles:
 * * * tile 4 [7,5]
 * translateX = 7 * 256 = 1792, translateY = 5 * 256 = 1280
 * * * tile 4 [8,5]
 * translateX = 8 * 256 = 2048, translateY = 5 * 256 = 1280
 * 
 */
public abstract class MapViewCommon extends MapViewAbstract {
//  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MapViewCommon.class.getName());

  
  /**
   * The center point of the map. Latitude and longitude in degrees.
   */
  public final ReadOnlyObjectWrapper<MapPoint> centerProperty = new ReadOnlyObjectWrapper<>();
  
  /**
   * The zoom level of the map.
   */
  protected final ReadOnlyDoubleWrapper zoomProperty = new ReadOnlyDoubleWrapper();
  
  /**
   * Dimensions (width and height) of the map.
   */
  protected Dimension2D dimensions;
  
  /**
   * The value to which the center has to change.
   * Main reason why this is a {@link javafx.beans.property.Property} is that it can be used in a {@link javafx.animation.KeyValue} of an animation
   * like flyTo().
   */
  protected final ObjectProperty<MapPoint> prefCenterProperty = new SimpleObjectProperty<>();
  
  /**
   * The value to which the zoom level has to change.
   * Main reason why this is a {@link javafx.beans.property.Property} is that it can be used in a {@link javafx.animation.KeyValue} of an animation
   * like flyTo().
   */
  protected final DoubleProperty prefZoomProperty  = new SimpleDoubleProperty();

  /*
   *  Node clipping rectangle. This is kept at the same size as the map view ({@code dimensions}).
   */
  protected final Rectangle clipRectangle;
  
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
  public boolean initialized = false;
  
  /**
   * Indicates whether the map view needs to be laid out. This is set to true when the center or zoom level changes, and is set to false after layout.
   */
  private boolean dirty = false;
  
  
  /**
   * Constructor.
   */
  public MapViewCommon() {
    clipRectangle = new Rectangle();
    this.setClip(clipRectangle);

    prefCenterProperty.addListener(_ -> baseMapAbstract.doSetCenter(prefCenterProperty.get()));

    prefZoomProperty.addListener(_ -> doZoom(prefZoomProperty.get()));    
  }

  @Override
  public MapPoint getCenter() {
    return centerProperty.get();
  }

  @Override
  public void setCenter(double latitude, double longitude) {
    setCenter(new MapPoint(latitude, longitude));
  }

  @Override
  public ReadOnlyObjectProperty<MapPoint> centerReadOnlyProperty() {
    return centerProperty.getReadOnlyProperty();
  }

  @Override
  public void setCenter(MapPoint mapPoint) {
    if (!initialized) {
      centerProperty.set(mapPoint);
    } else {
      baseMapAbstract.doSetCenter(mapPoint);
    }
  }

  @Override
  public double getZoom() {
    return zoomProperty.get();
  }
  
  @Override
  public ReadOnlyDoubleProperty zoomReadOnlyProperty() {
    return zoomProperty.getReadOnlyProperty();
  }

  @Override
  public void setZoom(double zoom) {
    if (zoom < 0) {
      zoom = 0; 
    }
    if (zoom > BaseMapAbstract.getMaximumZoomLevel()) {
      zoom = BaseMapAbstract.getMaximumZoomLevel();
    }
    
    doZoom(zoom);
  }
  
  @Override
  public Dimension2D getDimensions() {
    return dimensions;
  }

  @Override
  public void addLayer(MapLayer mapLayer) {
    mapLayer.setMapView(this);
    layers.add(mapLayer);
    this.getChildren().add(mapLayer);
    markDirty();
  }
  
  @Override
  public void removeLayer(MapLayer mapLayer) {
    layers.remove(mapLayer);
    this.getChildren().remove(mapLayer);
    markDirty();
  }

  @Override
  public MapPoint getMapPosition(double sceneX, double sceneY) {
    return baseMapAbstract.getMapPosition(sceneX, sceneY);
  }
  
  @Override
  public Point2D getMapPoint(double lat, double lon) {
    return baseMapAbstract.getMapPoint(lat, lon);
  }
  
  @Override
  public String getStatusInformation() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(baseMapAbstract.getStatusInformation());
    
    return buf.toString();
  }

  @Override
  public WGS84BoundingBox getVisibleMapBoundingBox() {
    if (getCenter() == null) {
      return null;
    }
    
    double height = dimensions.getHeight();
    double width = dimensions.getWidth();

    Point2D center = getMapPoint(getCenter().getLatitude(), getCenter().getLongitude());
    
    if (center == null) {
      return null;
    }

    MapPoint bottomLeft = getMapPosition(center.getX() - width / 2, center.getY() + height / 2);
    MapPoint topRight = getMapPosition(center.getX() + width / 2, center.getY() - height / 2);

    return new WGS84BoundingBox(bottomLeft.getLongitude(), topRight.getLatitude(), topRight.getLongitude(), bottomLeft.getLatitude());
  }
  
  @Override
  public double getZoomLevelForShowingBoundedBox(WGS84BoundingBox boundingBox) {
    // http://stackoverflow.com/questions/4266754/how-to-calculate-google-maps-zoom-level-for-a-bounding-box-in-java
    int zoomLevel;

    final double maxDiff = Math.max(boundingBox.getWidth(), boundingBox.getHeight());
    if (maxDiff < 360d / Math.pow(2, 20)) {
      zoomLevel = BaseMapAbstract.getMaximumZoomLevel();
    } else {
      zoomLevel = (int) (-1d*( (Math.log(maxDiff)/Math.log(2d)) - (Math.log(360d)/Math.log(2d))) + 1d);
      if (zoomLevel < 1)
        zoomLevel = 1;
    }

    return zoomLevel;
  }

  @Override
  public void updateBoundingBoxPolygon(Polygon polygon, WGS84BoundingBox mapBoundingBox) {
    ObservableList<Double> points = polygon.getPoints();
    points.clear();

    if (mapBoundingBox != null) {
      Point2D northWest = getMapPoint(mapBoundingBox.getNorth(), mapBoundingBox.getWest());
      if (northWest == null) {
        LOGGER.severe("Unable to get map point for north-west corner of bounding box: " + mapBoundingBox);
        return;
      }
      
      // Assume that if getMapPoint returns non null for the north-west corner, it will also work for the other corners.
      points.add(northWest.getX());
      points.add(northWest.getY());
      Point2D northEast = getMapPoint(mapBoundingBox.getNorth(), mapBoundingBox.getEast());
      points.add(northEast.getX());
      points.add(northEast.getY());
      Point2D southEast = getMapPoint(mapBoundingBox.getSouth(), mapBoundingBox.getEast());
      points.add(southEast.getX());
      points.add(southEast.getY());
      Point2D southWest = getMapPoint(mapBoundingBox.getSouth(), mapBoundingBox.getWest());
      points.add(southWest.getX());
      points.add(southWest.getY());
    }
  }
  
  /**
   * Set the dimensions (width and height) of the map.
   * 
   * @param width the width of the map in pixels.
   * @param height the height of the map in pixels.
   */
  public void setDimensions(double width, double height) {
    dimensions = new Dimension2D(width, height);
    
    clipRectangle.setWidth(width);
    clipRectangle.setHeight(height);
  }
  
  /**
   * Initialize the map view.
   */
  protected void initialize() {
    if (initialized) {
      return;
    }
    
    if (centerProperty.get() == null) {
      centerProperty.set(new MapPoint(0, 0));
    }
    
    baseMapAbstract.doSetCenter(centerProperty.get());
    doZoom(zoomProperty.get());
    
    initialized = true;
    
    markDirty();
  }

  /**
   * Zoom to a specific zoom level.
   * 
   * @param z the zoom level to use.
   */
  private void doZoom(double z) {
      zoomProperty.set(z);
      
      if (!initialized) {
        return;
      }
      
      baseMapAbstract.doSetCenter(centerProperty.get());
      markDirty();
  }

  @Override
  protected void layoutChildren() {
    if (!dirty) {
      return;
    }
    
    baseMapAbstract.layoutChildren();

    for (MapLayer layer : layers) {
      layer.layoutLayer();
    }
    
    super.layoutChildren();
    
    dirty = false;
  }

  public void markDirty() {
//    LOGGER.severe("=> markDirty");
    
    dirty = true;

    setNeedsLayout(true);
    Platform.requestNextPulse();
    
//    LOGGER.severe("<= markDirty");
  }
}
