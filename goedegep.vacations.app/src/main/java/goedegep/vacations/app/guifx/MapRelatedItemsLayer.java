package goedegep.vacations.app.guifx;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

import com.gluonhq.maps.LabeledIcon;
import com.gluonhq.maps.MapLayer;

import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.jfx.CustomizationFx;
import goedegep.mapview.MapViewUtil;
import goedegep.poi.app.guifx.POIIcons;
import goedegep.poi.model.POICategoryId;
import goedegep.resources.ImageResource;
import goedegep.util.img.ImageUtils;
import goedegep.util.img.PhotoFileMetaDataHandler;
import goedegep.vacations.app.LocationDescriptionDialog;
import goedegep.vacations.model.Boundary;
import goedegep.vacations.model.BoundingBox;
import goedegep.vacations.model.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This class is a {@link MapLayer} to show map related information as overlay on a MapView.
 * <p>
 * The following types of items are supported:
 * <ul>
 * <li>
 * Locations<br/>
 * 
 * </li>
 * </ul>
 * 
 */
public class MapRelatedItemsLayer extends MapLayer {
  private static final Logger         LOGGER = Logger.getLogger(MapRelatedItemsLayer.class.getName());
  private static int MAX_IMAGE_WIDTH = 900;
  private static int MAX_IMAGE_HEIGHT = 500;

  /**
   * GUI customization.
   */
  private CustomizationFx customization;
  
  /**
   * The window that created this layer. Used for showing dialogs.
   */
  private Stage ownerWindow;
  
  private POIIcons poiIcons;
  
  private VacationsAppResourcesFx appResources;
  
  /**
   * For items added for display, the Nodes (e.g. ImageView, Canvas, Polygon) are added as children to this instance and information is kept
   * in the following fields. In layoutLayer the 'locations on the map' are re-calculated.
   */
  private final ObservableList<LocationData> locations = FXCollections.observableArrayList();
  private final ObservableList<PhotoData> photos  = FXCollections.observableArrayList();
  private final ObservableList<BoundingBoxData> boundingBoxes = FXCollections.observableArrayList();
  private final ObservableList<PolylineData> locationsVisitedList  = FXCollections.observableArrayList();
  private PhotoData currentPhoto = null;


  /**
   * in edit mode a bounding box is also shown when a polyline/polygon is available.
   */
  private boolean editMode = false;
  
  VBox group;

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param ownerWindow the window which created this layer.
   */
  public MapRelatedItemsLayer(CustomizationFx customization, POIIcons poiIcons, Stage ownerWindow) {
    this.customization = customization;
    this.poiIcons = poiIcons;
    this.ownerWindow = ownerWindow;
    
    appResources = (VacationsAppResourcesFx) customization.getResources();
  }
  
  /**
   * Add a {@code Location} to be shown on this layer.
   * <p>
   * If the coordinates are set, an icon is shown at the coordinates of the location.
   * The location type determines which icon is shown.</br>
   * The text, if specified, is shown below the icon.</br>
   * If the location contains any boundaries, these are shown.</br>
   * If the location has a bounding box, this is always drawn in edit mode. Otherwise it is only drawn if the location has no boundaries.</br>
   * On a mouse click on the icon, a {@code LocationDescriptionDialog} is shown.
   * 
   * @param location the {@code Location} (mandatory).
   * @param text an optional text to be shown below the icon.
   */
  public WGS84BoundingBox addLocation(final Location location, String text) {
    return addLocation(location, text, null);
  }
  
  /**
   * Add a {@code Location} to be shown on this layer.
   * <p>
   * If the coordinates are set, an icon is shown at the coordinates of the location.
   * The location type determines which icon is shown.</br>
   * The text, if specified, is shown below the icon.</br>
   * If the location contains any boundaries, these are shown.</br>
   * If the location has a bounding box, this is always drawn in edit mode. Otherwise it is only drawn if the location has no boundaries.</br>
   * On a mouse click on the icon, a {@code LocationDescriptionDialog} is shown.
   * 
   * @param location the {@code Location} (mandatory).
   * @param text an optional text to be shown below the icon.
   * @param boundariesColor an optional {@code Color} for the color of the boundaries.
   */
  public WGS84BoundingBox addLocation(final Location location, String text, Color boundariesColor) {
    LOGGER.info("=> location=" + location.toString() + " - " + (text != null ? text : "<no text>"));
    
    WGS84BoundingBox wgs84BoundingBox = null;

    // Icon with text at the coordinates of the location.
    LabeledIcon labeledIcon = null;
    if (location.getLatitude() != null  ||  location.getLongitude() != null) {
      POICategoryId poiCategoryId = location.getLocationType();
      Image locationIcon = poiIcons.getIcon(poiCategoryId);    
      labeledIcon = new LabeledIcon(locationIcon, text);
      labeledIcon.mySetOnMouseClicked(e -> (new LocationDescriptionDialog(customization, ownerWindow, location)).show());
      getChildren().add(labeledIcon);
      
      wgs84BoundingBox = new WGS84BoundingBox(location.getLongitude(), location.getLatitude());
    }
    
    
    // Polylines for the boundaries.
    List<PolylineData> boundariesDataList = new ArrayList<>();
    if (boundariesColor == null) {
      boundariesColor = Color.GREEN;
    }
    List<Boundary> boundaries = location.getBoundaries();
    for (Boundary boundary: boundaries) {
      List<WGS84Coordinates> boundaryPoints = boundary.getPoints();
      if (!boundaryPoints.isEmpty()) {
        Polyline polyline = new Polyline();
        polyline.setStroke(boundariesColor);
        polyline.setStrokeWidth(3.0);
        polyline.setVisible(true);
        getChildren().add(polyline);
        PolylineData polylineData = new PolylineData(boundaryPoints, polyline);
        boundariesDataList.add(polylineData);
        for (WGS84Coordinates wgs84Coordinates: boundaryPoints) {
          wgs84BoundingBox = WGS84BoundingBox.extend(wgs84BoundingBox, wgs84Coordinates);
        }
        LOGGER.info("Polyline added");
      } else {
        LOGGER.info("Empty points list, no Polyline added");
      }
    }
    
    // Bounding box
    BoundingBoxData boundingBoxData = null;
    if ((boundariesDataList.isEmpty() || editMode)  &&  (location != null)  &&  location.isSetBoundingbox()) {
      BoundingBox boundingBox = location.getBoundingbox();

      if ((boundingBox != null)  &&  boundingBox.isValid()) {
        WGS84BoundingBox locationBoundingBox = new WGS84BoundingBox(boundingBox.getWest(), boundingBox.getNorth(), boundingBox.getEast(), boundingBox.getSouth());
        Polygon polygon = new Polygon();
        polygon.setStroke(Color.YELLOW);
        polygon.setFill(Color.TRANSPARENT);
        polygon.setVisible(true);
        getChildren().add(polygon);

        boundingBoxData = new BoundingBoxData(locationBoundingBox, polygon);
        //        boundingBoxData = createBoundingBoxData(locationBoundingBox);
        wgs84BoundingBox = WGS84BoundingBox.extend(wgs84BoundingBox, locationBoundingBox);
      }
    }
    
    LocationData locationData = new LocationData(location, labeledIcon, boundariesDataList, boundingBoxData);
    locations.add(locationData);
    
    markDirty();

    LOGGER.info("<=");
    return wgs84BoundingBox;
  }

  /**
   * Add a photo to the layer.
   * 
   * @param coordinates The {@code WGS84Coordinates} at which the photo will be shown.
   * @param text An optional title for the photo.
   * @param fileName the photo filename.
   */
  public WGS84BoundingBox addPhoto(WGS84Coordinates coordinates, String text, String fileName) {
    LOGGER.info("=> coordinates: " + coordinates.toString());
    Image photoImage = ImageResource.CAMERA_BLACK.getImage(12, 12);
    ImageView photoIcon = new ImageView(photoImage);
    photoIcon.setOnMouseClicked(e -> showCurrentPhoto(coordinates, text, fileName));
    getChildren().add(photoIcon);
    
    PhotoData photoData = new PhotoData(coordinates, fileName, text, photoIcon);
    photos.add(photoData);

    WGS84BoundingBox wgs84BoundingBox = new WGS84BoundingBox(coordinates.getLongitude(), coordinates.getLatitude());
    return wgs84BoundingBox;
  }
  
  /**
   * Show the single current photo.
   * <p>
   * The photo will only be shown if the specified {@code fileName} exists in the {@code photos}.
   * 
   * @param fileName file name of the photo to be shown.
   */
  public void showCurrentPhoto(String fileName) {
    for (PhotoData photoData: photos) {
      if (fileName.equals(photoData.fileName())) {
        showCurrentPhoto(photoData.coordinates(), photoData.text(), photoData.fileName());
      }
    }
  }
  
  /**
   * Show the single current photo.
   * <p>
   * If there is a current photo, is will be removed.<br/>
   * After this the current photo is shown, limiting the dimensions to MAX_IMAGE_WIDTH and MAX_IMAGE_HEIGHT.
   * 
   * @param coordinates the location where the photo is shown.
   * @param text An optional title for the photo. 
   * @param fileName the file name of the photo to be shown.
   */
  private void showCurrentPhoto(WGS84Coordinates coordinates, String text, String fileName) {
    if (currentPhoto != null) {
      getChildren().remove(currentPhoto.node());
    }
        
    Canvas canvas = ImageUtils.createImageOnCanvas(fileName, MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    
    Font font = new Font(18);
    gc.setFont(font);
    gc.setFill(Color.WHITE);
    gc.fillText(text, 20, canvas.getHeight() - 15);

    getChildren().add(canvas);
    currentPhoto = new PhotoData(coordinates, fileName, text, canvas);
    canvas.setOnMouseClicked(e -> removeCurrentPhoto());
  }
  
  /**
   * Remove the current photo.
   */
  private void removeCurrentPhoto() {
    Node node = currentPhoto.node();
    getChildren().remove(node);
    currentPhoto = null;
  }
  
  /**
   * Add a bounding box to the map.
   * 
   * @param wgs84BoundingBox the bounding box to be added.
   */
  public void addBoundingBox(WGS84BoundingBox wgs84BoundingBox) {
    LOGGER.info("=> wgs84BoundingBox=" + wgs84BoundingBox);
    
    Polygon polygon = new Polygon();
    polygon.setStroke(Color.YELLOW);
    polygon.setFill(Color.TRANSPARENT);
    polygon.setVisible(true);
    getChildren().add(polygon);

    BoundingBoxData boundingBoxData = new BoundingBoxData(wgs84BoundingBox, polygon);
//    BoundingBoxData boundingBoxData = createBoundingBoxData(wgs84BoundingBox);
    boundingBoxes.add(boundingBoxData);
  }
  
  /**
   * Add a line along locations visited to the map.
   * 
   * @param locations
   */
  public void addLocationsVisitedPolyLine(final List<WGS84Coordinates> locations) {
    Polyline polyline = new Polyline();
    polyline.setStrokeWidth(3);
    polyline.setStroke(Color.RED);
    getChildren().add(polyline);
    PolylineData polylineData = new PolylineData(locations, polyline);
    locationsVisitedList.add(polylineData);
            
    this.markDirty();
  }

  /**
   * Clear the layer, i.e. remove all nodes to be shown.
   */
  public void clear() {
    LOGGER.info("=>");

    boundingBoxes.clear();
    locationsVisitedList.clear();
    locations.clear();
    photos.clear();
    getChildren().clear();
    markDirty();
    
    currentPhoto = null;

    LOGGER.info("<=");
  }
  
  /**
   * Set edit mode.
   * 
   * @param editMode the new {@code editMode} value. If true the layer operates in edit mode.
   */
  public void setEditMode(boolean editMode) {
    this.editMode = editMode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void layoutLayer() {
    LOGGER.info("=>");
    
    // locations
    for (LocationData locationData: locations) {
      layoutLocation(locationData);
    }
    
    // photos
    for (PhotoData photoData: photos) {
      Node photoIcon = photoData.node();
      WGS84Coordinates coordinates = photoData.coordinates();
      final Point2D mapPoint = baseMap.getMapPoint(coordinates.getLatitude(), coordinates.getLongitude());
      
      photoIcon.setTranslateX(mapPoint.getX());
      photoIcon.setTranslateY(mapPoint.getY());
      
    }
    
    // locationsVisited
    for (PolylineData locations: locationsVisitedList) {
      layoutPolyline(locations);
    }
    
    // boundingBoxes
    for (BoundingBoxData boundingBoxData: boundingBoxes) {
      MapViewUtil.updateBoundingBoxPolygon(boundingBoxData.polygon(), boundingBoxData.boundingBox(), baseMap);
//      layoutBoundingBox(boundingBoxData);
    }
            
    // currentPhoto
    if (currentPhoto != null) {
      Node imageView = currentPhoto.node();
      WGS84Coordinates coordinates = currentPhoto.coordinates();
      final Point2D mapPoint = baseMap.getMapPoint(coordinates.getLatitude(), coordinates.getLongitude());
      
      imageView.setTranslateX(mapPoint.getX());
      imageView.setTranslateY(mapPoint.getY());
    }

    LOGGER.info("<=");
  }

  /**
   * Layout a location, defined by LocationData.
   * 
   * @param locationData the location data.
   */
  private void layoutLocation(LocationData locationData) {
    Location location = locationData.location();
    
    // Labeled icon
    LabeledIcon labeledIcon = locationData.labeledIcon();
    if (labeledIcon != null) {
      double zoomLevel = baseMap.zoom().get();
      double zoomCorrectionX = labeledIcon.getZoomDependendTranslateXCorrection(zoomLevel);
      double zoomCorrectionY = labeledIcon.getZoomDependendTranslateYCorrection(zoomLevel);

      Point2D mapPoint = baseMap.getMapPoint(location.getLatitude(), location.getLongitude());
      labeledIcon.setTranslateX(mapPoint.getX() - zoomCorrectionX);
      labeledIcon.setTranslateY(mapPoint.getY() - zoomCorrectionY);
    }
    
    // Polylines
    for (PolylineData polylineData: locationData.boundaryDataList()) {
      layoutPolyline(polylineData);
    }
    
    // Bounding box
    BoundingBoxData boundingBoxData = locationData.boundingBoxData();
    if (boundingBoxData != null) {
      MapViewUtil.updateBoundingBoxPolygon(boundingBoxData.polygon(), boundingBoxData.boundingBox(), baseMap);
//      layoutBoundingBox(boundingBoxData);
    }
  }

  /**
   * Layout a Polyline, defined by PolylineData.
   * 
   * @param polylineData the Polyline data.
   */
  private void layoutPolyline(PolylineData polylineData) {
    Polyline polyline = polylineData.polyline();
    List<WGS84Coordinates> coordinatesList = polylineData.polylinePoints();
    ObservableList<Double> polylinePoints = polyline.getPoints();
    polylinePoints.clear();
    for (WGS84Coordinates coordinates: coordinatesList) {
      Point2D point2D = baseMap.getMapPoint(coordinates.getLatitude(), coordinates.getLongitude());
      polylinePoints.add(point2D.getX());
      polylinePoints.add(point2D.getY());
    }
  }

  public boolean selectObject(Object object) {
    if (object == null) {
      return false;
    }
    
    if (object instanceof Location location) {
      for (LocationData locationData: locations) {
        if (location.equals(locationData.location())) {
          LOGGER.info("Going to select: " + location);
          locationData.labeledIcon().setSelected(true);
        }
      }
    }
    return false;
  }
}

/**
 * Bounding box data, combines a WGS84BoundingBox with a polygon node.
 */
record BoundingBoxData(WGS84BoundingBox boundingBox, Polygon polygon) {
}

/**
 * Information for a {@code Location}, icon with label, boundaries, bounding box.
 * 
 * @param location the {@code Location}.
 * @param labeledIcon the {@code LabeledIcon} for the {@code location}.
 * @param boundaryDataList the list of {@code PolylineData} for the boundaries of the {@code location}.
 * @param boundingBoxData the {@code BoundingBoxData} for the bounding box of the {@code location}.
 */
record LocationData(Location location, LabeledIcon labeledIcon, List<PolylineData> boundaryDataList, BoundingBoxData boundingBoxData) {
}

/**
 * Information for a polyline.
 * 
 * @param polylinePoints a list of {@code WGS84Coordinates} representing the points of the polyline.
 * @param the {@code Polyline}.
 */
record PolylineData(List<WGS84Coordinates> polylinePoints, Polyline polyline) {
}

/**
 * Information for a photo.
 * 
 * @param coordinates the {@code WGS84Coordinates} at which the photo is to be displayed.
 * @param fileName name of the picture file.
 * @param text an optional text to display on the photo.
 * @param node the {@code Node}.
 *
 */
record PhotoData(WGS84Coordinates coordinates, String fileName, String text, Node node) {
}
