package goedegep.gluonmaps.gpx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapLayer.BoundingBoxData;

import goedegep.geo.dbl.WGS84BoundingBox;
import goedegep.geo.dbl.WGS84Coordinates;
import goedegep.gpx.model.GpxType;
import goedegep.gpx.model.RteType;
import goedegep.gpx.model.TrkType;
import goedegep.gpx.model.TrksegType;
import goedegep.gpx.model.WptType;
import goedegep.jfx.stringconverters.WGS84CoordinatesStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class GPXLayer extends MapLayer {
  private static final Logger LOGGER = Logger.getLogger(GPXLayer.class.getName());
  private static final double WAYPOINT_ICON_SIZE = 24;
  private static final double ROUTE_POINT_ICON_SIZE = 16;
  
  private static final WGS84CoordinatesStringConverter wgs84CoordinatesStringConverter = WGS84CoordinatesStringConverter.getInstance();
  
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
   * All information showns for GPX files.
   */
  private final ObservableList<GpxFileData> gpxFileDataList = FXCollections.observableArrayList();
  
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
   * The bounding box of all data is drawn.</br>
   * A label is shown at the center of the bounding box.</br>
   * All items (except the connecting lines) have tooltips.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param gpxType the GPX data.
   */
  public WGS84BoundingBox addGpx(final String title, final String fileName, final GpxType gpxType) {
    fileBoundingBox = null;
    ObservableList<WaypointData> waypointDataList = createWaypoints(title, fileName, gpxType.getWpt());
    ObservableList<RouteData> routeDataList = createRoutes(title, fileName, gpxType.getRte());
    ObservableList<TrackData> trackDataList = addTracks(title, fileName, gpxType.getTrk());
    BoundingBoxData boundingBoxData = createBoundingBoxData(fileBoundingBox);
    Canvas label = createLabel(title, fileName, gpxType);
    GpxData gpxData = new GpxData(waypointDataList, routeDataList, trackDataList, boundingBoxData);
    
    GpxFileData gpxFileData = new GpxFileData(title, fileName, gpxData, label);
    gpxFileDataList.add(gpxFileData);

    this.markDirty();
    return fileBoundingBox;
  }

  /**
   * Create the waypoints.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param waypoints the waypoints to add to the map.
   * @return a list with waypoint data.
   */
  private ObservableList<WaypointData> createWaypoints(String title, String fileName, EList<WptType> waypoints) {
    ObservableList<WaypointData> waypointDataList = FXCollections.observableArrayList();
    for (WptType gpxWaypoint : waypoints) {
            final ImageView icon = new ImageView(getWaypointFlagImage());
            icon.setX(-WAYPOINT_ICON_SIZE/2);
            icon.setY(-WAYPOINT_ICON_SIZE/2);
            
            final Tooltip tooltip = new Tooltip(createTooltipText(title, fileName, gpxWaypoint));
            tooltip.setShowDuration(Duration.seconds(10));
            Tooltip.install(icon, tooltip);
            
            this.getChildren().add(icon);

            waypointDataList.add(new WaypointData(gpxWaypoint, icon));

            fileBoundingBox= WGS84BoundingBox.extend(fileBoundingBox, gpxWaypoint.getLon().doubleValue(), gpxWaypoint.getLat().doubleValue());
    }
    
    return waypointDataList;
  }

  /**
   * Create the routes.
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
   * Create a single route.
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
            icon.setX(-ROUTE_POINT_ICON_SIZE/2);
            icon.setY(-ROUTE_POINT_ICON_SIZE/2);
            
            final Tooltip tooltip = new Tooltip(createTooltipText(title, fileName, gpxWaypoint));
            tooltip.setShowDuration(Duration.seconds(10));
            Tooltip.install(icon, tooltip);
            
            this.getChildren().add(icon);

            routePointsDataList.add(new WaypointData(gpxWaypoint, icon));

            fileBoundingBox= WGS84BoundingBox.extend(fileBoundingBox, gpxWaypoint.getLon().doubleValue(), gpxWaypoint.getLat().doubleValue());
    }
    
    RouteData routeData = new RouteData(routePointsDataList);
    
    return routeData;
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
    ObservableList<TrackPointData> trackPointsDataList = FXCollections.observableArrayList();
    
    List<WptType> trackPoints = trackSegment.getTrkpt();
    final double ratio = MAX_DATAPOINTS / trackPoints.size();
    
    Node prevIcon = null;

    double count = 0d;
    double i = 0d;
    for (WptType trackPoint : trackPoints) {
        i++;
        if (i * ratio >= count) {
            final Circle icon = new Circle(3.5, Color.LIGHTGOLDENRODYELLOW);
            icon.setVisible(true);
            icon.setStroke(Color.RED);
            icon.setStrokeWidth(1);
            
            final Tooltip tooltip = new Tooltip(createTooltipText(title, fileName, trackPoint));
            
            Tooltip.install(icon, tooltip);
            
            this.getChildren().add(icon);

            Line line = null;
            // if its not the first point we also want a line
            if (prevIcon != null) {
                line = new Line();
                line.setVisible(true);
                line.setStrokeWidth(2);
                line.setStroke(Color.BLUE);

                // bind ends of line:
                line.startXProperty().bind(prevIcon.layoutXProperty().add(prevIcon.translateXProperty()));
                line.startYProperty().bind(prevIcon.layoutYProperty().add(prevIcon.translateYProperty()));
                line.endXProperty().bind(icon.layoutXProperty().add(icon.translateXProperty()));
                line.endYProperty().bind(icon.layoutYProperty().add(icon.translateYProperty()));

                this.getChildren().add(line);
            }

            TrackPointData trackPointData = new TrackPointData(trackPoint, icon, line);
            trackPointsDataList.add(trackPointData);
            
            prevIcon = icon;

            fileBoundingBox= WGS84BoundingBox.extend(fileBoundingBox, trackPoint.getLon().doubleValue(), trackPoint.getLat().doubleValue());
            count++;
        }
    }
        
    TrackSegmentData trackSegmentData = new TrackSegmentData(trackPointsDataList);
    return trackSegmentData;
  }

//  /**
//   * Create the bounding box around the GPX data.
//   * 
//   * @return bounding the created bounding box data.
//   */
//  private BoundingBoxData createBoundingBox() {
//    Polygon polygon = new Polygon();
//    polygon.setStroke(Color.YELLOW);
//    polygon.setFill(Color.TRANSPARENT);
//    polygon.setVisible(true);
//    getChildren().add(polygon);
//    
//    BoundingBoxData boundingBoxData = new BoundingBoxData(fileBoundingBox, polygon);
//    return boundingBoxData;
//  }
  
  /**
   * Create a label.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @param gpxType the GPX data.
   * @return the created label (as a {@code Canvas}).
   */
  private Canvas createLabel(final String title, final String fileName, final GpxType gpxType) {
    String text = createLabelText(title, fileName);
    
    Canvas canvas = new Canvas(200, 80);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.fillText(text, 10, 10);
//    canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//      @Override
//      public void handle(MouseEvent mouseEvent) {
//        LOGGER.severe("canvas MouseEvent: x=" + mouseEvent.getX() + ", y=" + mouseEvent.getY());
//      }
//
//    });
//    canvas.setOnMousePressed(mouseEvent -> {
//      LOGGER.severe("canvas MousePressesEvent: x=" + mouseEvent.getX() + ", y=" + mouseEvent.getY());
//    });
    
    String tooltipText = createLabelTooltipText(title, fileName, gpxType);
    final Tooltip tooltip = new Tooltip(tooltipText);
    tooltip.setShowDuration(Duration.seconds(10));
    Tooltip.install(canvas, tooltip);

    getChildren().add(canvas);
    
    return canvas;
  }
  
  /**
   * Create a label text.
   * 
   * @param title the (optional) title of the file
   * @param fileName the file name of the GPX file of the {@code gpxType}.
   * @return the label text to be shown for the GPX data.
   */
  private String createLabelText(final String title, final String fileName) {
    if (title != null) {
      return title;
    }
    
    if (fileName != null) {
      return fileName;
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

  public void clear() {
    LOGGER.info("=>");
    
    gpxFileDataList.clear();
    getChildren().clear();
    markDirty();

    LOGGER.info("<=");
  }
  
  public void setSelectedGPXWaypoints(final List<WptType> gpxWaypoints) {
      selectedGPXWaypoints.clear();
      selectedGPXWaypoints.addAll(gpxWaypoints);
      this.markDirty();
  }

//  public MapPoint getCenter() {
//      return new MapPoint(fileBoundingBox.getWest() + fileBoundingBox.getWidth()/2, fileBoundingBox.getNorth() + fileBoundingBox.getHeight()/2);
//  }

  public double getZoom() {
      // TODO: calculate zooom level
      // http://stackoverflow.com/questions/4266754/how-to-calculate-google-maps-zoom-level-for-a-bounding-box-in-java
      int zoomLevel;
      
      final double maxDiff = (fileBoundingBox.getWidth() > fileBoundingBox.getHeight()) ? fileBoundingBox.getWidth() : fileBoundingBox.getHeight();
      if (maxDiff < 360d / Math.pow(2, 20)) {
          zoomLevel = 21;
      } else {
          zoomLevel = (int) (-1d*( (Math.log(maxDiff)/Math.log(2d)) - (Math.log(360d)/Math.log(2d))) + 1d);
          if (zoomLevel < 1)
              zoomLevel = 1;
      }
      
      return zoomLevel;
  }

  @Override
  protected void layoutLayer() {
    
    for (GpxFileData gpxFileData: gpxFileDataList) {
      GpxData gpxData = gpxFileData.gpx();

      // Waypoints
      for (WaypointData waypointData : gpxData.waypointsDataList()) {
        final WptType point = waypointData.waypoint();
        final Node icon = waypointData.node();

        final Point2D mapPoint = baseMap.getMapPoint(point.getLat().doubleValue(), point.getLon().doubleValue());
        icon.toFront();
        icon.setTranslateX(mapPoint.getX());
        icon.setTranslateY(mapPoint.getY());
      }
      
      // Routes
      for (RouteData routeData: gpxData.routeDataList()) {
        for (WaypointData waypointData : routeData.routePointsDataList()) {
          final WptType point = waypointData.waypoint();
          final Node icon = waypointData.node();

          final Point2D mapPoint = baseMap.getMapPoint(point.getLat().doubleValue(), point.getLon().doubleValue());
          icon.toFront();
          icon.setTranslateX(mapPoint.getX());
          icon.setTranslateY(mapPoint.getY());
        }
      }
      
      // Tracks
      for (TrackData trackData: gpxData.trackDataList()) {
        for (TrackSegmentData trackSegmentData: trackData.trackSegmentsDataList()) {
          boolean prevSelected = false;
          for (TrackPointData triple : trackSegmentData.trackPointsDataList()) {
            final WptType point = triple.waypoint();
            final Node icon = triple.node();
            final Line line = triple.line();

            final boolean selected = selectedGPXWaypoints.contains(point);
            // first point doesn't have a line
            if (line != null) {
              if (selected && prevSelected) {
                // if selected AND previously selected => red line
                line.setStrokeWidth(3);
                line.setStroke(Color.RED);
              } else {
                line.setStrokeWidth(2);
                line.setStroke(Color.BLUE);
              }
            }
            prevSelected = selected;

            final Point2D mapPoint = baseMap.getMapPoint(point.getLat().doubleValue(), point.getLon().doubleValue());
            icon.toFront();
            icon.setTranslateX(mapPoint.getX());
            icon.setTranslateY(mapPoint.getY());
          }
        }
      }
      
      // Bounding box
      BoundingBoxData boundingBoxData = gpxData.boundingBox();
      layoutBoundingBox(boundingBoxData);
      
      // Label
      WGS84BoundingBox boundingBox = boundingBoxData.boundingBox();
      WGS84Coordinates center = boundingBox.getCenter();
      Point2D mapPoint = baseMap.getMapPoint(center.getLatitude(), center.getLongitude());
      Canvas label = gpxFileData.label();
      label.setTranslateX(mapPoint.getX() - 30);
      label.setTranslateY(mapPoint.getY());
      
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
    buf.append("lat: ").append(latitude).append("\n");
    buf.append("lon: ").append(longitude).append("\n");
    if (gpxWaypoint.getEle() != null) {
      buf.append("ele: ").append(gpxWaypoint.getEle()).append("\n");
    }
    buf.append("coordinates: ").append(wgs84CoordinatesStringConverter.toString(new WGS84Coordinates(latitude, longitude))).append("\n");
    
    return buf.toString();
  }
  
  /**
   * Get the waypoint flag image.
   * 
   * @return the waypoint flag image.
   */
  private Image getWaypointFlagImage() {
    if (waypointFlagImage == null) {
      waypointFlagImage = new Image(getClass().getResourceAsStream("waypointflag.png"), WAYPOINT_ICON_SIZE, WAYPOINT_ICON_SIZE, true, true);
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
      routePointFlagImage = new Image(getClass().getResourceAsStream("routepointflag.png"), ROUTE_POINT_ICON_SIZE, ROUTE_POINT_ICON_SIZE, true, true);
    }
    
    return routePointFlagImage;
  }
}

record GpxFileData(String title, String fileName, GpxData gpx, Canvas label) {
}

record GpxData(ObservableList<WaypointData> waypointsDataList, ObservableList<RouteData> routeDataList, ObservableList<TrackData> trackDataList, BoundingBoxData boundingBox) {
}

record WaypointData(WptType waypoint, Node node) {
}

record RouteData(ObservableList<WaypointData> routePointsDataList) {
}

record TrackData(ObservableList<TrackSegmentData> trackSegmentsDataList) {
}

record TrackSegmentData(ObservableList<TrackPointData> trackPointsDataList) {
}

record TrackPointData(WptType waypoint, Node node, Line line) {
}
