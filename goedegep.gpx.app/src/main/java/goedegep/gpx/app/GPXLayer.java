package goedegep.gpx.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.xml.type.AnyType;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;

import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.gpx.model.ExtensionsType;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.MetadataType;
import goedegep.gpx.model.RteType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.jfx.stringconverterandchecker.WGS84CoordinatesStringConverterAndChecker;
import goedegep.mapview.MapViewUtil;
import goedegep.poi.app.guifx.POIIcons;
import goedegep.poi.model.POICategoryId;
import goedegep.resources.ImageResource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 * This class is a {@link MapLayer} to show a GPX tracks (represented by a {@link GpxType}) on a map.
 * <p>
 * The following is shown on the map:
 * <ul>
 * <li>Waypoints<br/>
 * These are shown as yellow flags. A tooltip provides details about each point.
 * </li>
 * <li>Routes<br/>
 * The points of the routes are shown as blue flags. A tooltip provides details about each point.
 * </li>
 * <li>Tracks<br/>
 * The points of each track segment are shown as small circles, connected by blue lines. A tooltip provides details about each point.
 * </li>
 * <li>Bounding box<br/>
 * A bounding box is drawn around all waypoints, routes and tracks.
 * </li>
 * <li>Label<br/>
 * A text label is shown at the center of the bounding box.
 * </li>
 * </ul>
 *
 */
public class GPXLayer extends MapLayer {
  private static final Logger LOGGER = Logger.getLogger(GPXLayer.class.getName());
  private static final double WAYPOINT_ICON_SIZE = 24;
  private static final double ROUTE_POINT_ICON_SIZE = 16;
  
  private static final double STROKE_WIDTH = 4;
  private static final double STROKE_WIDTH_SELECTED = 4;
  private static final boolean DRAW_TRACK_POINTS = false;
  
  // Converter to convert {@code WGS84Coordinates} to text. Used to show the coordinates in the tooltips.
  private static final WGS84CoordinatesStringConverterAndChecker WGS84_COORDINATES_TO_STRING_CONVERTER = WGS84CoordinatesStringConverterAndChecker.getDecimalFormatInstance();
  
  /**
   * Image for waypoints.
   */
  private static Image waypointFlagImage = null;
  
  /**
   * Image for route points.
   */
  private static Image routePointFlagImage = null;

  /**
   * Maximum number of points shown for a single track segment.
   */
  public final static double MAX_DATAPOINTS = 1000d;
  
  /**
   * All information shown for GPX files.
   */
  private final ObservableMap<GpxType, GpxFileData> gpxFileDataMap = FXCollections.observableHashMap();
  
  /**
   * Selected track points, which will be highlighted.
   */
  private final List<WptType> selectedGPXWaypoints = new ArrayList<>();
  
  /**
   * Bounding box around the GPX data, which is being handled by addGpx().
   */
  private WGS84BoundingBox fileBoundingBox;

  /**
   * Constructor.
   */
  public GPXLayer() {
      super();
  }
    
  /**
   * Add a GPX file.
   * <p>
   * The waypoints are represented by flags.</br>
   * The route points of all routes are represented by a different color flags.</br>
   * All points of all track segments are represented by small circles, connected by a line.</br>
   * A bounding box is drawn around all data.</br>
   * A label is shown at the center of the bounding box.</br>
   * All items (except the connecting lines) have tooltips.
   * <p>
   * Note: Any {@code GpxType} can only be added once.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param gpxType the GPX data (may not be null).
   * @return a bounding box surrounding all elements of the track.
   */
  public WGS84BoundingBox addGpx(final String title, final String fileName, final GpxType gpxType) {
    Objects.requireNonNull(gpxType, "The gpxType may not be null");
    GpxFileData gpxFileData = gpxFileDataMap.get(gpxType);
    if (gpxFileData != null) {
      throw new RuntimeException("Cannot add a GPX track that is already shown");
    }
    
    fileBoundingBox = null;
    
    gpxFileData = createGpxData(title, fileName, gpxType);
    gpxFileDataMap.put(gpxType, gpxFileData);
    
    gpxType.eAdapters().add(new EContentAdapter() {

      @Override
      public void notifyChanged(Notification notification) {
        super.notifyChanged(notification);
        LOGGER.info("Notification: " + notification.getEventType());
        if (!notification.isTouch()  &&  notification.getEventType() != Notification.REMOVING_ADAPTER) {
          updateGpx(gpxType);
        }
      }

    });

    markDirty();
    return fileBoundingBox;
  }
  
  /**
   * Remove a GPX track.
   * 
   * @param gpxType the GPX track to be removed.
   */
  public void removeGpx(final GpxType gpxType) {
    gpxType.eAdapters().clear();
    gpxFileDataMap.remove(gpxType);
    markDirty();
  }
  
  /**
   * Handle any change in a GPX track.
   * 
   * @param gpxType the GPX data.
   * @return a bounding box surrounding all elements of the track.
   */
  private WGS84BoundingBox updateGpx(final GpxType gpxType) {
    GpxFileData gpxFileData = gpxFileDataMap.remove(gpxType);
    if (gpxFileData == null) {
      throw new RuntimeException("Cannot update a GPX track that doesn't exist yet");
    }
    
    removeWaypoints(gpxFileData.gpx().waypointsDataList());
    removeRoutes(gpxFileData.gpx().routeDataList());
    removeTracks(gpxFileData.gpx().trackDataList());
    getChildren().remove(gpxFileData.gpx().boundingBox().polygon());
    removeLabel(gpxFileData.label());
    
    fileBoundingBox = null;
    
    gpxFileData = createGpxData(gpxFileData.title(), gpxFileData.fileName(), gpxType);
    gpxFileDataMap.put(gpxType, gpxFileData);

    markDirty();
    return fileBoundingBox;
  }

  /**
   * Create the nodes and data for a GPX track.
   * <p>
   * All required nodes and the related information are created.
   *  
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param gpxType the GPX data.
   * @return the created {@code GpxFileData}
   */
  private GpxFileData createGpxData(final String title, final String fileName, final GpxType gpxType) {
    ObservableList<WaypointData> waypointDataList = createWaypoints(title, fileName, gpxType.getWpt());
    ObservableList<RouteData> routeDataList = createRoutes(title, fileName, gpxType.getRte());
    ObservableList<TrackData> trackDataList = addTracks(title, fileName, gpxType.getTrk());
    
    Polygon polygon = new Polygon();
    polygon.setStroke(Color.YELLOW);
    polygon.setFill(Color.TRANSPARENT);
    polygon.setVisible(true);
    getChildren().add(polygon);
    BoundingBoxData boundingBoxData = new BoundingBoxData(fileBoundingBox, polygon);

    Node label = createLabel(title, fileName, gpxType);
    GpxData gpxData = new GpxData(waypointDataList, new ArrayList<>(), routeDataList, trackDataList, boundingBoxData);
    
    GpxFileData gpxFileData = new GpxFileData(title, fileName, gpxData, label);
    return gpxFileData;
  }

  /**
   * Create the waypoints. The related information is created.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param waypoints the waypoints to add to the map.
   * @return a list with waypoint data.
   */
  private ObservableList<WaypointData> createWaypoints(String title, String fileName, EList<WptType> waypoints) {
    ObservableList<WaypointData> waypointDataList = FXCollections.observableArrayList();
    for (WptType gpxWaypoint : waypoints) {
            Image waypointImage = getImageForWaypoint(gpxWaypoint);
            if (waypointImage == null) {
              waypointImage = getWaypointFlagImage();
            }
            final ImageView icon = new ImageView(waypointImage);
            icon.setPreserveRatio(true);
            icon.setFitHeight(16);
            icon.setX(-WAYPOINT_ICON_SIZE/2);
            icon.setY(-WAYPOINT_ICON_SIZE/2);
            
            final Tooltip tooltip = new Tooltip(createTooltipText(title, fileName, gpxWaypoint));
            tooltip.setShowDuration(Duration.seconds(10));
            Tooltip.install(icon, tooltip);
            
            getChildren().add(icon);

            waypointDataList.add(new WaypointData(gpxWaypoint, icon));

            fileBoundingBox= WGS84BoundingBox.extend(fileBoundingBox, gpxWaypoint.getLon().doubleValue(), gpxWaypoint.getLat().doubleValue());
    }
    
    return waypointDataList;
  }
  
  private Image getImageForWaypoint(WptType gpxWaypoint) {
    ExtensionsType extensionsType = gpxWaypoint.getExtensions();
    if (extensionsType == null) {
      return null;
    }
    
    FeatureMap featureMap = extensionsType.getAny();

    Iterator<Entry> entryIterator = featureMap.iterator();
    while (entryIterator.hasNext()) {
      Entry entry = entryIterator.next();
      LOGGER.info("entry: " + entry.toString());
      LOGGER.info("value: " + entry.getValue());
      LOGGER.info("structural feature: " + entry.getEStructuralFeature().getName());
      AnyType anyType = (AnyType) entry.getValue();
      if (!entry.getEStructuralFeature().getName().equals("icon")) {
        continue;
      }
      
      FeatureMap anyTypeFeatureMap = anyType.getMixed();
      Iterator<Entry> anyEntryIterator = anyTypeFeatureMap.iterator();
      while (anyEntryIterator.hasNext()) {
        Entry anyEntry = anyEntryIterator.next();
        LOGGER.info("any entry: " + anyEntry.toString());
        LOGGER.info("any value: " + anyEntry.getValue());
        LOGGER.info("any structural feature: " + anyEntry.getEStructuralFeature().getName());
        String anyAnyType = (String) anyEntry.getValue();
        return getIconForOsmAndValue(anyAnyType);
      }
    }
    
    return null;
  }

  private Image getIconForOsmAndValue(String anyAnyType) {
    POIIcons poiIcons = new POIIcons("POIIconResourceInfo.xmi");

    switch (anyAnyType) {
    case "amenity_restaurant":
      return poiIcons.getIcon(POICategoryId.RESTAURANT);
      
    case "tourism_camp_site":
      return poiIcons.getIcon(POICategoryId.CAMPING);
      
    default:
      LOGGER.severe("UNKNOWN ICON: " + anyAnyType);
    }
    return null;
  }

  /**
   * Remove a list of waypoints from the map.
   * This means removing the icon nodes from the map.
   * 
   * @param waypointDataList the list of waypoints to be removed.
   */
  private void removeWaypoints(ObservableList<WaypointData> waypointDataList) {
    for (WaypointData waypointData: waypointDataList) {
      getChildren().remove(waypointData.node());
    }
  }

  /**
   * Create the routes. For each route the nodes are added to the map and the related information is created.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param routes the routes to add to the map.
   * @return a list with route data
   */
  private ObservableList<RouteData> createRoutes(String title, String fileName, EList<RteType> routes) {
    ObservableList<RouteData> routeDataList = FXCollections.observableArrayList();
    
    for (RteType route: routes) {
      RouteData routeData = createRoute(title, fileName, route);
      routeDataList.add(routeData);
    }
    
    return routeDataList;
  }

  /**
   * Create a single route. The nodes are added to the map and the related information is created.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param route the route to add to the map.
   * @return the route data
   */
  private RouteData createRoute(String title, String fileName, RteType route) {
    ObservableList<WaypointData> routePointsDataList = FXCollections.observableArrayList();
    for (WptType gpxWaypoint : route.getRtept()) {
            final ImageView icon = new ImageView(getRoutePointFlagImage());
            icon.setPreserveRatio(true);
            icon.setFitHeight(16);
            icon.setX(-ROUTE_POINT_ICON_SIZE/2);
            icon.setY(-ROUTE_POINT_ICON_SIZE/2);
            
            final Tooltip tooltip = new Tooltip(createTooltipText(title, fileName, gpxWaypoint));
            tooltip.setShowDuration(Duration.seconds(10));
            Tooltip.install(icon, tooltip);
            
            getChildren().add(icon);

            routePointsDataList.add(new WaypointData(gpxWaypoint, icon));

            fileBoundingBox= WGS84BoundingBox.extend(fileBoundingBox, gpxWaypoint.getLon().doubleValue(), gpxWaypoint.getLat().doubleValue());
    }
    
    RouteData routeData = new RouteData(routePointsDataList, new ArrayList<>());
    
    return routeData;
  }
  
  /**
   * Remove routes.
   * This means that for each route the icon nodes are removed from the map.
   * 
   * @param routeDataList the information about the routes.
   */
  private void removeRoutes(ObservableList<RouteData> routeDataList) {
    for (RouteData routeData: routeDataList) {
      removeRoute(routeData);
    }
  }
  
  /**
   * Remove a route.
   * This means removing the icon nodes from the map.
   * 
   * @param routeData the information about the route.
   */
  private void removeRoute(RouteData routeData) {
    ObservableList<WaypointData> routePointsDataList = routeData.routePointsDataList();
    for (WaypointData waypointData: routePointsDataList) {
      getChildren().remove(waypointData.node());
    }
  }

  /**
   * Create the tracks.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param tracks the tracks to add to the map.
   * @return a list with track data
   */
  private ObservableList<TrackData> addTracks(String title, String fileName, EList<TrkType> tracks) {
    ObservableList<TrackData> trackDataList = FXCollections.observableArrayList();
    
    for (TrkType track: tracks) {
      TrackData trackData = createTrack(title, fileName, track);
      trackDataList.add(trackData);
    }
    
    return trackDataList;
  }

  /**
   * Create a track.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param track the track to add to the map.
   * @return the track data
   */
  private TrackData createTrack(String title, String fileName, TrkType track) {
    ObservableList<TrackSegmentData> trackSegmentsDataList = FXCollections.observableArrayList();
    
    for (TrksegType trackSegment: track.getTrkseg()) {
      TrackSegmentData trackSegmentData = createTrackSegment(title, fileName, trackSegment);
      trackSegmentsDataList.add(trackSegmentData);
    }
    
    TrackData trackData = new TrackData(trackSegmentsDataList);
    return trackData;
  }

  /**
   * Create a track segment.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param trackSegment the track segment to add to the map.
   * @return the track segment data
   */
  private TrackSegmentData createTrackSegment(String title, String fileName, TrksegType trackSegment) {
    List<WptType> trackPoints = trackSegment.getTrkpt();
    double ratio = MAX_DATAPOINTS / trackPoints.size();
    
    double zoom = baseMap.getZoom();
    if (ratio > 1.0) {
      ratio = (zoom + 1) / 20;
    }

    for (WptType trackPoint : trackPoints) {
        fileBoundingBox= WGS84BoundingBox.extend(fileBoundingBox, trackPoint.getLon().doubleValue(), trackPoint.getLat().doubleValue());
    }
        
    TrackSegmentData trackSegmentData = new TrackSegmentData(trackPoints, new ArrayList<>());
    return trackSegmentData;
  }
  
  /**
   * Remove all nodes for all segments for a list of tracks from the map.
   * 
   * @param trackDataList information about the tracks.
   */
  private void removeTracks(ObservableList<TrackData> trackDataList) {
    for (TrackData trackData: trackDataList) {
      removeTrack(trackData);
    }
  }

  /**
   * Remove all nodes for all segments of a track from the map.
   * 
   * @param trackData information about the track.
   */
  private void removeTrack(TrackData trackData) {
    for (TrackSegmentData trackSegmentData: trackData.trackSegmentsDataList()) {
      removeTrackSegment(trackSegmentData);
    }

  }

  /**
   * Remove all nodes for a segment from the map.
   * 
   * @param trackSegmentData information about the track segment.
   */
  private void removeTrackSegment(TrackSegmentData trackSegmentData) {
    getChildren().removeAll(trackSegmentData.currentItemsOnMap());
    trackSegmentData.currentItemsOnMap().clear();
  }
  
  /**
   * Create a label.
   * <p>
   * The text is created by calling {@link #createLabelText}.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param gpxType the GPX data.
   * @return the created label (as a {@code TextFlow}).
   */
  private Node createLabel(final String title, final String fileName, final GpxType gpxType) {
    String text = createLabelText(title, fileName, gpxType);
    
    Label label = new Label(text);
    label.setWrapText(true);
    label.setMaxWidth(200);
    label.setTextAlignment(TextAlignment.CENTER);
    TextFlow flow = new TextFlow(label);
    Color color = new Color(0.9, 0.9, 0.9, 0.6);
    BackgroundFill backgroundFill = new BackgroundFill(color, CornerRadii.EMPTY , Insets.EMPTY);
    Background backGround = new Background(backgroundFill);
    label.setBackground(backGround);
    
    String tooltipText = createLabelTooltipText(title, fileName, gpxType);
    final Tooltip tooltip = new Tooltip(tooltipText);
    tooltip.setShowDuration(Duration.seconds(10));
    Tooltip.install(flow, tooltip);

    getChildren().add(flow);
    
    return flow;
  }
  
  /**
   * Create a label text.
   * <p>
   * If a title is specified, this will be the text. Otherwise the text is the file name of {@code fileName} (i.e. the path is removed).
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @return the label text to be shown for the GPX data.
   */
  private String createLabelText(final String title, final String fileName, final GpxType gpxType) {
    if (title != null) {
      return title;
    }
    
    if (fileName != null) {
      File file = new File(fileName);
      return file.getName();
    }
    
    MetadataType metaData = gpxType.getMetadata();
    if (metaData != null) {
      String name = metaData.getName();
      if (name != null  &&  !name.isEmpty()) {
        return name;
      }
    }
    
    return "<no name>";
  }
  
  /**
   * Create the label tooltip text.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param gpxType the GPX data.
   * @return the tooltip text to be shown for the label.
   */
  private String createLabelTooltipText(final String title, final String fileName, final GpxType gpxType) {
    StringBuilder buf = new StringBuilder();
    double lengthInMeters = gpxType.getLength();
    
    buf.append("length: ").append(String.format("%1$.2f", lengthInMeters / 1000)).append(" km");
    
    return buf.toString();
  }
  
  /**
   * Remove a label from the map.
   * 
   * @param canvas the label to be removed.
   */
  private void removeLabel(Node canvas) {
    getChildren().remove(canvas);
  }

  /**
   * Clear this layer.
   */
  public void clear() {
    LOGGER.info("=>");
    
    gpxFileDataMap.clear();
    getChildren().clear();
    markDirty();

    LOGGER.info("<=");
  }
  
  public void setSelectedGPXWaypoints(final List<WptType> gpxWaypoints) {
      selectedGPXWaypoints.clear();
      selectedGPXWaypoints.addAll(gpxWaypoints);
      markDirty();
  }

  /**
   * {@inheritDoc}
   * 
   * Any {@code GpxData} is only drawn if it is on the visible area of the map.
   * In more detail: Only if the bounding box of the {@code GpxData} intersects with the visible map area, all its information is drawn (so also the parts that are not visible).
   */
  @Override
  protected void layoutLayer() {
    
    for (GpxFileData gpxFileData: gpxFileDataMap.values()) {
      GpxData gpxData = gpxFileData.gpx();
      
      // Determine whether this GpxData has to be drawn or not.
      boolean drawGPXData = true;
      BoundingBoxData boundingBoxData = gpxData.boundingBox();
      WGS84BoundingBox boundingBox = boundingBoxData.boundingBox();
      MapPoint topLeft = baseMap.getMapPosition(0, 0);
      double width = baseMap.getParent().getLayoutBounds().getWidth();
      double height = baseMap.getParent().getLayoutBounds().getHeight();
      MapPoint bottomRight = baseMap.getMapPosition(width - 1, height - 1);
      WGS84BoundingBox mapBoundingBox = new WGS84BoundingBox(topLeft.getLongitude(), topLeft.getLatitude(), bottomRight.getLongitude(), bottomRight.getLatitude());
      if (boundingBox == null  ||  !mapBoundingBox.intersects(boundingBox)) {
        drawGPXData = false;
      }

      if (drawGPXData) {
        for (WaypointData waypointData : gpxData.waypointsDataList()) {
          final Node icon = waypointData.node();
          final WptType point = waypointData.waypoint();

          final Point2D mapPoint = baseMap.getMapPoint(point.getLat().doubleValue(), point.getLon().doubleValue());
          icon.setTranslateX(mapPoint.getX());
          icon.setTranslateY(mapPoint.getY());

//          getChildren().add(icon);
          gpxData.currentWaypointsOnMap().add(icon);
        }
      }
      
      // Routes
      for (RouteData routeData: gpxData.routeDataList()) {
        for (Object o: routeData.currentWaypointsOnMap()) {
          LOGGER.severe("Wpt on map: " + o);
        }
        getChildren().removeAll(routeData.currentWaypointsOnMap());
        routeData.currentWaypointsOnMap().clear();

        if (drawGPXData) {
          for (WaypointData waypointData : routeData.routePointsDataList()) {
            final WptType point = waypointData.waypoint();
            Node icon = waypointData.node();

            final Point2D mapPoint = baseMap.getMapPoint(point.getLat().doubleValue(), point.getLon().doubleValue());
            icon.setTranslateX(mapPoint.getX());
            icon.setTranslateY(mapPoint.getY());
            
//            getChildren().add(icon);
//            routeData.currentWaypointsOnMap().add(icon);
          }
        }
      }
      
      // Tracks
      for (TrackData trackData: gpxData.trackDataList()) {
        
        for (TrackSegmentData trackSegmentData: trackData.trackSegmentsDataList()) {
          // Remove current points and lines.
          getChildren().removeAll(trackSegmentData.currentItemsOnMap());
          trackSegmentData.currentItemsOnMap().clear();
          
          if (!drawGPXData) {
            continue;
          }
          
          Point2D previousMapPoint = null;  // used to check whether the current point is far enough away on screen. If not it is skipped.
          boolean prevSelected = false;
          Node prevIcon = null;  // used to bring it to front after drawing the connecting line

          Iterator<WptType> trackPointIterator = trackSegmentData.trackPointsDataList().iterator();
          while (trackPointIterator.hasNext()) {
            final WptType point = trackPointIterator.next();
            
            final Point2D mapPoint = baseMap.getMapPoint(point.getLat().doubleValue(), point.getLon().doubleValue());
            if (previousMapPoint != null  &&  trackPointIterator.hasNext()) {
              if (Math.abs(mapPoint.getX() - previousMapPoint.getX()) +  Math.abs(mapPoint.getY() - previousMapPoint.getY()) < 14) {
                continue;
              }
            }
            
            Node icon = null;
            
            boolean trackPoint = false;
            if (prevIcon == null) {
              icon = createStartImage();
            } else if (!trackPointIterator.hasNext()) {
              icon = createEndImage();
            } else {
              icon = createTrackPointImage();
              trackPoint = true;
            }
            
            icon.setTranslateX(mapPoint.getX());
            icon.setTranslateY(mapPoint.getY());
             
            final Tooltip tooltip = new Tooltip(createTooltipText(gpxFileData.title(), gpxFileData.fileName(), point));
            Tooltip.install(icon, tooltip);
            
            
            // if its not the first point we also want a line
            Line line = null;
            if (prevIcon != null) {
                line = new Line();
                line.setVisible(true);
                
                final boolean selected = selectedGPXWaypoints.contains(point);
                if (selected && prevSelected) {
                  // if selected AND previously selected => red line
                  line.setStrokeWidth(STROKE_WIDTH_SELECTED);
                  line.setStroke(Color.RED);
                } else {
                  line.setStrokeWidth(STROKE_WIDTH);
                  line.setStroke(Color.BLUE);
                }
                prevSelected = selected;

                // bind ends of line:
//                line.startXProperty().bind(prevIcon.layoutXProperty().add(prevIcon.translateXProperty()));
//                line.startYProperty().bind(prevIcon.layoutYProperty().add(prevIcon.translateYProperty()));
                line.startXProperty().bind(prevIcon.translateXProperty());
                line.startYProperty().bind(prevIcon.translateYProperty());
//                line.endXProperty().bind(icon.layoutXProperty().add(icon.translateXProperty()));
//                line.endYProperty().bind(icon.layoutYProperty().add(icon.translateYProperty()));
                line.endXProperty().bind(icon.translateXProperty());
                line.endYProperty().bind(icon.translateYProperty());

                getChildren().add(line);
                trackSegmentData.currentItemsOnMap().add(line);
            }
            
            if (!trackPoint  ||  DRAW_TRACK_POINTS) {
              getChildren().add(icon);
            }
            trackSegmentData.currentItemsOnMap().add(icon);
            
            if (prevIcon != null) {
              prevIcon.toFront();
            }

            prevIcon = icon;            
            previousMapPoint = mapPoint;
          }
        }
      }
      
      // Bounding box
      if (boundingBoxData != null) {
        MapViewUtil.updateBoundingBoxPolygon(boundingBoxData.polygon(), boundingBoxData.boundingBox(), baseMap);
//        layoutBoundingBox(boundingBoxData);
      }
      
      // Label
      if (boundingBoxData != null) {
        if (boundingBox != null) {
          WGS84Coordinates center = boundingBox.getCenter();
          Point2D mapPoint = baseMap.getMapPoint(center.getLatitude(), center.getLongitude());
          Node node = gpxFileData.label();
          node.setTranslateX(mapPoint.getX() - node.prefWidth(-1) / 2);
          node.setTranslateY(mapPoint.getY() - node.prefHeight(-1) / 2);
        }
      }
    }
  }

  /**
   * Create the tooltip text for a waypoint.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param gpxWaypoint the waypoint.
   * @return the tooltip text for the {@code waypoint}.
   */
  private String createTooltipText(String title, String fileName, WptType gpxWaypoint) {
    StringBuilder buf = new StringBuilder();
    
    if (title != null) {
      buf.append("title: ").append(title).append("\n");
    } else {
      if (fileName != null) {
        buf.append("fileName: ").append(fileName).append("\n");
      }
    }
    
    if (gpxWaypoint.getName() != null) {
      buf.append("name: ").append(gpxWaypoint.getName()).append("\n");
    }
    
    if (gpxWaypoint.getDesc() != null) {
      buf.append("desc: ").append(gpxWaypoint.getDesc()).append("\n");
    }
    
    double latitude = gpxWaypoint.getLat().doubleValue();
    double longitude = gpxWaypoint.getLon().doubleValue();
    Double altitude = null;
    buf.append("lat: ").append(latitude).append("\n");
    buf.append("lon: ").append(longitude).append("\n");
    if (gpxWaypoint.getEle() != null) {
      buf.append("ele: ").append(gpxWaypoint.getEle()).append("\n");
      altitude = gpxWaypoint.getEle().doubleValue();
    }
    buf.append("coordinates: ").append(WGS84_COORDINATES_TO_STRING_CONVERTER.toString(new WGS84Coordinates(latitude, longitude, altitude))).append("\n");
    
    return buf.toString();
  }
  
  /**
   * Get the waypoint flag image.
   * 
   * @return the waypoint flag image.
   */
  private Image getWaypointFlagImage() {
    if (waypointFlagImage == null) {
      waypointFlagImage = ImageResource.LOCATION_FLAG_YELLOW.getImage();
    }
    
    return waypointFlagImage;
  }
  
  /**
   * Get the waypoint flag image.
   * 
   * @return the waypoint flag image.
   */
  private Image getRoutePointFlagImage() {
    if (routePointFlagImage == null) {
      routePointFlagImage = ImageResource.LOCATION_FLAG_BLUE.getImage();
    }
    
    return routePointFlagImage;
  }
  
  private Node createTrackPointImage() {
    final Circle circle = new Circle(3.5, Color.LIGHTGOLDENRODYELLOW);
    circle.setVisible(true);
    circle.setStroke(Color.RED);
    circle.setStrokeWidth(1);
    
    circle.setLayoutX(-2);
    circle.setLayoutY(-2);
    
    return circle;
  }
  
  private Canvas createStartImage() {
    int width = 16;
    int height = 16;
    Canvas canvas = new Canvas(width, height);
    
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.LIGHTBLUE);
    gc.fillRect(0, 0, width, height);
    
    int firstX = 0;
    int secondX = width -1;
    int thirdX = 0;

    int firstY =0;
    int secondY = height / 2 - 1;
    int thirdY = height - 1;

    gc.setFill(Color.GREEN);
    gc.fillPolygon(new double[]{firstX, secondX, thirdX},
            new double[]{firstY, secondY, thirdY}, 3);

    canvas.setLayoutX(- width / 2);
    canvas.setLayoutY(- height / 2);

    return canvas;
  }

  private Canvas createEndImage() {
    int width = 16;
    int height = 16;
    Canvas canvas = new Canvas(width, height);

    GraphicsContext gc = canvas.getGraphicsContext2D();
//    gc.setFill(Color.WHITE);
//    gc.fillRect(0, 0, width / 2 - 1, height / 2 - 1); // Top left white
//    gc.fillRect(width / 2, height / 2, width / 2 - 1, height / 2 - 1); // Bottom right white
//
//    gc.setFill(Color.BLACK);
//    gc.fillRect(width / 2, 0, width / 2 - 1, height / 2 - 1); // Top left white
//    gc.fillRect(0, height / 2, width / 2 - 1, height / 2 - 1); // Top left white
    
    // fill with black
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, width, height); // Top left white
    
    // top left and bottom right white boxes, leaving a black border
    gc.setFill(Color.WHITE);
    gc.fillRect(1, 1, width / 2 - 1, height / 2 - 1); // Top left white
    gc.fillRect(width / 2, height / 2, width / 2 - 1, height / 2 - 1); // Bottom right white

    canvas.setLayoutX(- width / 2);
    canvas.setLayoutY(- height / 2);

    return canvas;
  }
}

/**
 *  All information needed for a GPX track file.
 */
record GpxFileData(String title, String fileName, GpxData gpx, Node label) {
}

/**
 * Bounding box data, combines a WGS84BoundingBox with a polygon node.
 */
record BoundingBoxData(WGS84BoundingBox boundingBox, Polygon polygon) {
}

/**
 *  All information needed for a GPX track.
 */
record GpxData(ObservableList<WaypointData> waypointsDataList, List<Object> currentWaypointsOnMap, ObservableList<RouteData> routeDataList, ObservableList<TrackData> trackDataList, BoundingBoxData boundingBox) {
}

/**
 *  All information needed for a waypoint of the waypoints.
 */
record WaypointData(WptType waypoint, Node node) {
}

/**
 *  All information needed for a route.
 */
record RouteData(ObservableList<WaypointData> routePointsDataList, List<Object> currentWaypointsOnMap) {
}

/**
 *  All information needed for a track.
 */
record TrackData(ObservableList<TrackSegmentData> trackSegmentsDataList) {
}

/**
 *  All information needed for a track segment.
 */
record TrackSegmentData(List<WptType> trackPointsDataList, List<Object> currentItemsOnMap) {
}

///**
// * All information needed for a waypoint of a track.
// */
//record TrackPointData(WptType waypoint) {
//}
