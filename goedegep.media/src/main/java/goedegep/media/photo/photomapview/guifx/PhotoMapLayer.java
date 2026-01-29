package goedegep.media.photo.photomapview.guifx;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.google.common.geometry.S2Iterator;
import com.google.common.geometry.S2PointIndex;

import goedegep.geo.WGS84BoundingBox;
import goedegep.geo.WGS84Coordinates;
import goedegep.jfx.CustomizationFx;
import goedegep.mapview.MapViewUtil;
import goedegep.media.photo.IPhotoMetaData;
import goedegep.media.photo.IPhotoMetaDataWithImage;
import goedegep.media.photo.photoshow.guifx.IPhotoInfo;
import goedegep.media.photo.photoshow.guifx.PhotoInfo;
import goedegep.resources.ImageResource;
import goedegep.resources.ImageSize;
import goedegep.util.img.ImageUtils;
import goedegep.util.objectselector.ObjectSelectionListener;
import goedegep.util.objectselector.ObjectSelector;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

/**
 * This class is a {@link MapLayer} which shows the locations of photos on the map.
 * <p>
 * <b>index</b><br/>
 * An point index (type S2PointIndex<PhotoMetaData>) can be set via method setPhotoIndex.
 * 
 * <b>photos being shown</b><br/>
 * if <code>showPhotoIndexOnMap</code> is true, only all photos of the index are shown on the map. Otherwise the photos set via addPhoto(IPhotoMetaDataWithImage) are shown.
 * 
 * <b>photo icon</b>
 * Photos are represented by a small camera icon.<br/>
 * If the photo has approximate coordinates the icon is gray, otherwise it is black.
 * 
 * <b>dropping files</b><br/>
 * Photo files can be dropped on this layer. If the photo already has coordinates, these are not changed. Otherwise the coordinates are set to the location where the photo(s) is/are dropped.
 * 
 * In the photo information, the photo pathname is shown relative to the <code>rootFolderName</code>, which is passed to the constructor.<br/>
 * This map layer adds itself as layer to the specified mapView.
 */
public class PhotoMapLayer extends MapLayer implements ObjectSelector<IPhotoInfo> {
  private static final Logger LOGGER = Logger.getLogger(PhotoMapView.class.getName());
  
  /**
   * Dimensions for showing a photo
   */
  private static double MAX_IMAGE_WIDTH = 900;
  private static double MAX_IMAGE_HEIGHT = 500;
  
  /**
   * The GUI customization
   */
  private CustomizationFx customization;
  
//  /**
//   * Information of the photos shown, per folder.
//   */
//  private ObservableMap<String, List<IPhotoMetaDataWithImage>> photoInfosPerFolder;
  
  private ObservableSet<IPhotoInfo> modifiedPhotos;
  
  /**
   * A small window to show image information and thumbnail and to edit image information.
   */
  PhotoMetaDataEditor imageInfoStage;
  
  /**
   * The photo that is currently being relocated.
   */
  private IPhotoMetaData relocatingPhoto = null;
  
  /**
   * Photos shown on this layer.
   */
  private final ObservableList<PhotoData> photos  = FXCollections.observableArrayList();
  
  /**
   * The photo that is currently selected.
   */
  private PhotoData selectedPhoto = null;
  
  /**
   * The photo that is currently shown.
   * TODO Description: I think currentPhoto shall either be same as selectedPhoto or null.
   */
  private PhotoData currentPhoto = null;
  
  /**
   * Photo index
   */
  private S2PointIndex<IPhotoMetaData> index;
  
  /**
   * If true, only all photos of the index are shown on the map.
   * Otherwise the photos set via addPhoto(IPhotoMetaDataWithImage) are shown.
   */
  private boolean showPhotoIndexOnMap = false;
  
  /**
   * Listeners to the selected photo, used for the implementation of {@link ObjectSelector}.
   */
  private List<ObjectSelectionListener<IPhotoInfo>> objectSelectionListeners = new ArrayList<>();

  /**
   * The {@code MapView} to which this map layer is added.
   */
  private MapView mapView;
  
  /**
   * Zoom rectangle
   */
  private BoundingBoxData zoomRectangle = null;


  /**
   * Constructor.
   * 
   * @param customization GUI customization
   * @param photoMapView the PhotoMapView for which this is a layer.
   * @param photoInfosPerFolder information on the photos per folder.
   * @param modifiedPhotos a list of modified photos
   */
  public PhotoMapLayer(CustomizationFx customization, PhotoMapView photoMapView, MapView mapView, ObservableMap<String, List<IPhotoInfo>> photoInfosPerFolder, ObservableSet<IPhotoInfo> modifiedPhotos) {
    this.customization = customization;
    this.mapView = mapView;
    this.modifiedPhotos = modifiedPhotos;
   
    MapChangeListener<String, List<IPhotoInfo>> ml = (change) -> markDirty();
    photoInfosPerFolder.addListener(ml);
    
    mapView.addLayer(this);
            
    // Handle a drag dropped; add photos for the dropped files.
    mapView.setOnDragDropped(dragEvent -> {
      LOGGER.severe("Drag Dropped");
      Dragboard dragboard = dragEvent.getDragboard();
      boolean success = false;
      if (dragboard.hasFiles()) {
        javafx.scene.robot.Robot robot = new javafx.scene.robot.Robot();
        double robotX = robot.getMouseX();
        double robotY = robot.getMouseY();
        Point2D point = this.screenToLocal(robot.getMousePosition());
        LOGGER.info("robotX, robotY = " + robotX + ", " + robotY);
        LOGGER.info("screenX, screenY = " + point.getX() + ", " + point.getY());
        
        double x = dragEvent.getX();
        double y = dragEvent.getY();
        LOGGER.info("x, y = " + x + ", " + y);
//        MapPoint mapPoint = baseMap.getMapPosition(dragEvent.getX(), dragEvent.getY());
        MapPoint mapPoint = baseMap.getMapPosition(point.getX(), point.getY());
        WGS84Coordinates coordinates = new WGS84Coordinates(mapPoint.getLatitude(), mapPoint.getLongitude());
        photoMapView.addPhotos(dragboard.getFiles(), coordinates);
        
        success = true;
      }
      
      dragEvent.setDropCompleted(success);
      dragEvent.consume();
    });
        
    // Indicate that files can be dropped on the mapView (on this layer)
    mapView.setOnDragOver(dragEvent -> {
//      LOGGER.severe("Drag Over");
//      for (File file: dragEvent.getDragboard().getFiles()) {
//        LOGGER.severe("File: " + file.getAbsolutePath());
//      }
      if (dragEvent.getGestureSource() != baseMap
          && dragEvent.getDragboard().hasFiles()) {
        dragEvent.acceptTransferModes(TransferMode.COPY);
      }
      dragEvent.consume();
    });
    
    mapView.setOnDragEntered(_ -> LOGGER.severe("Drag Entered"));
    
    // Dragging to create a zoom rectangle
//    mapView.setOnMouseDragged(event -> {
//      LOGGER.severe("Mouse Dragged");
//      LOGGER.info("SceneX: " + event.getSceneX() + ", SceneY: " + event.getSceneY());
//      if (!event.isControlDown()) {
//        return;
//      }
//      Point2D point2D = mapView.sceneToLocal(event.getSceneX(), event.getSceneY());
//      MapPoint mapPoint = baseMap.getMapPosition(point2D.getX(), point2D.getY());
//      if (zoomRectangle != null) {
//        LOGGER.severe("point2D: " + point2D.getX() + ", " + point2D.getY());
//        WGS84BoundingBox bb = zoomRectangle.boundingBox();
//        bb = bb.extend(mapPoint.getLatitude(), mapPoint.getLongitude());
//        LOGGER.severe("bb: " + bb.toString());
//        zoomRectangle = new BoundingBoxData(bb, zoomRectangle.polygon());
//      } else {
//        getScene().setCursor(Cursor.CROSSHAIR);
//        Polygon polygon = new Polygon();
//        polygon.setStroke(Color.BLUE);
//        polygon.setFill(Color.TRANSPARENT);
//        polygon.setVisible(true);
//        getChildren().add(polygon);
//        zoomRectangle = new BoundingBoxData(new WGS84BoundingBox(mapPoint.getLatitude(), mapPoint.getLongitude()), polygon);
//      }
//      markDirty();
//      event.consume();
//    });
    
    // End of dragging, zoom to rectangle.
    this.setOnMouseReleased(event -> {
      LOGGER.info("Mouse Released");
      if (!event.isControlDown()) {
        return;
      }
      getScene().setCursor(Cursor.DEFAULT);
      // TODO zoom to rectangle
      zoomRectangle = null;
      markDirty();
      event.consume();
    });
    
    markDirty();
  }
  
  /**
   * Clear the photos shown on the layer. The index is not cleared.
   */
  public void clear() {
    currentPhoto = null;
    
    if (imageInfoStage != null) {
      imageInfoStage.close();
      imageInfoStage = null;
    }
    
    photos.clear();
    
    getChildren().clear();
  }

  /**
   * Add a photo to the layer.
   * <p>
   * If the photo is already on the map, nothing is done.
   * For the photo an ImageView (icon) is created.
   * On this icon mouse handling is installed by calling installMouseHandlingOnPhotoIcon() and the icon is added as a child to this layer.
   * PhotoData is created for the photoMetaDataWithImage and the icon, and this is added to <code>photos</code>.
   * A WGS84BoundingBox is created for the coordinates of the photo and this is returned.
   * 
   * @param photoMetaDataWithImage information on the photo.
   * @return a single point WGS84BoundingBox for the coordinates of the photo.
   */
  public WGS84BoundingBox addPhoto(IPhotoInfo photoMetaDataWithImage) {
    if (photoAlreadyOnMap(photoMetaDataWithImage)) {
      return null;
    }
    
    ImageView photoIcon = getImageViewForPhoto(photoMetaDataWithImage);
    installMouseHandlingOnPhotoIcon(photoIcon, photoMetaDataWithImage);
    
    PhotoData photoData = new PhotoData(photoMetaDataWithImage, photoIcon);
    photos.add(photoData);
    getChildren().add(photoIcon);

    WGS84Coordinates coordinates = photoMetaDataWithImage.getCoordinates();
    WGS84BoundingBox wgs84BoundingBox = new WGS84BoundingBox(coordinates.getLongitude(), coordinates.getLatitude());
    return wgs84BoundingBox;
  }
  
  /**
   * Create an ImageView (icon) for a photo (specified by the IPhotoMetaData).
   * <p>
   * The icon depends on whether the photo has approximate or precise coordinates.
   * For precise coordinates the icon is a black camera (ImageResource.CAMERA_BLACK)
   * else it is a gray camera (ImageResource.CAMERA_GRAY).
   * 
   * @param photoMetaData information on the photo
   * @return an ImageView icon for the <code>photoMetaData</code>
   */
  private ImageView getImageViewForPhoto(IPhotoMetaData photoMetaData) {
    Image photoImage;
    if (photoMetaData == null  ||  photoMetaData.hasApproximateGPScoordinates()) {
      photoImage = ImageResource.CAMERA_GRAY.getImage(ImageSize.SIZE_0);
    } else {
      photoImage = ImageResource.CAMERA_BLACK.getImage(ImageSize.SIZE_0);
    }
    ImageView photoIcon = new ImageView(photoImage);
    return photoIcon;
  }
  
  /**
   * Set the photo index.
   * 
   * @param index an <code>S2PointIndex<PhotoMetaData></code> which will be used to determine whether a photo has to be drawn or not. A null values removes the index.
   * @param showOnMap if true, all photos of this index will be shown, else the photos added via .... are shown.
   */
  public void setPhotoIndex(S2PointIndex<IPhotoMetaData> index) {
    LOGGER.info("Index set");
    this.index = index;
    markDirty();
  }
  
  /**
   * Show all photos in the index, or the photos added via ...
   * 
   * @param showPhotoIndexOnMap if true, all photos of the index will be shown, else the photos added via .... are shown.
   */
  public void showPhotoIndexOnMap(boolean showPhotoIndexOnMap) {
    LOGGER.severe("showPhotoIndexOnMap: " + showPhotoIndexOnMap);
    if (showPhotoIndexOnMap != this.showPhotoIndexOnMap) {
      if (showPhotoIndexOnMap) {
        switchToShowPhotoIndexOnMap();
      } else {
        switchToShowSpecifiedPhotosOnMap();
      }
      
      this.showPhotoIndexOnMap = showPhotoIndexOnMap;
      markDirty();
    }
  }
  
  private void switchToShowPhotoIndexOnMap() {
    clear();

    if (index != null) {
//      WGS84BoundingBox mapBoundingBox = MapViewUtil.getVisibleMapCoordinates(baseMap);

      LOGGER.info("Number of photos to show: " + index.numPoints());
      S2Iterator<S2PointIndex.Entry<IPhotoMetaData>> iterator = index.iterator();

      while (!iterator.done()) {
        IPhotoMetaData photoMetaData = iterator.entry().data();
        IPhotoInfo photoInfo = new PhotoInfo();
        photoInfo.setPhotoMetaData(photoMetaData);

        ImageView photoIcon = getImageViewForPhoto(photoMetaData);
//        final Point2D mapPoint = baseMap.getMapPoint(coordinates.getLatitude(), coordinates.getLongitude());
//
//        photoIcon.setTranslateX(mapPoint.getX());
//        photoIcon.setTranslateY(mapPoint.getY());
        photoIcon.setOnMouseClicked(e -> handleMouseEventOnPhotoIcon(e, photoInfo));

        getChildren().add(photoIcon);
        PhotoData photoData = new PhotoData(photoInfo, photoIcon);
        photos.add(photoData);

        iterator.next();
      }
    }
    
  }
  
  private void switchToShowSpecifiedPhotosOnMap() {
    clear();
  }
  
  /**
   * Install mouse handling on a photo icon.
   * <p>
   * See {@link #handleMouseEventOnPhotoIcon} for the action on clicking on the photo icon.<br/>
   * On a new drag event, the cursor is changed to a crosshair and the <code>relocatingPhoto</code> is set to photo.
   * On the following drag events the coordinates of the <code>relocatingPhoto</code> are set to the location of the cursor.<br/>
   * On a mouse released event the cursor is set back to the default cursor en the <code>relocatingPhoto</code> is set to null.
   * 
   * @param photoIcon the icon on which to install mouse handling
   * @param photoInfo the PhotoMetaDataWithImage related to the <code>photoIcon</code>
   */
  private void installMouseHandlingOnPhotoIcon(ImageView photoIcon, IPhotoInfo photoInfo) {
    photoIcon.setOnMouseClicked(e -> handleMouseEventOnPhotoIcon(e, photoInfo));
    
    photoIcon.setOnMouseDragged(event -> {
      LOGGER.info("Mouse Dragged");
      LOGGER.info("SceneX: " + event.getSceneX() + ", SceneY: " + event.getSceneY());
      if (relocatingPhoto != null) {
        Point2D point2D = mapView.sceneToLocal(event.getSceneX(), event.getSceneY());
        LOGGER.info("point2D: " + point2D.getX() + ", " + point2D.getY());
        MapPoint mapPoint = baseMap.getMapPosition(point2D.getX(), point2D.getY());
        WGS84Coordinates coordinates = new WGS84Coordinates(mapPoint.getLatitude(), mapPoint.getLongitude());
        photoInfo.setCoordinates(coordinates);
        markDirty();
      } else {
        getScene().setCursor(Cursor.CROSSHAIR);
        relocatingPhoto = photoInfo;
      }
      event.consume();
    });
    
    photoIcon.setOnMouseReleased(event -> {
      LOGGER.info("Mouse Released");
      getScene().setCursor(Cursor.DEFAULT);
      modifiedPhotos.add(photoInfo);
      relocatingPhoto = null;
      event.consume();
    });
    
  }

  /**
   * Set the selected photo.
   * 
   * @param photoMetaData The photo to be selected (may be null).
   */
  public void setSelectedPhoto(Object caller, IPhotoInfo photoMetaData) {
    // if there currently is a selected photo, deselect it.
    if (selectedPhoto != null) {
      Node node = selectedPhoto.node();
      getChildren().remove(node);
      
      ImageView photoIcon = getImageViewForPhoto(photoMetaData);
      installMouseHandlingOnPhotoIcon(photoIcon, selectedPhoto.photoMetaData());
      getChildren().add(photoIcon);
      photos.remove(selectedPhoto);
      PhotoData newPhotoData = new PhotoData(selectedPhoto.photoMetaData(), photoIcon);
      photos.add(newPhotoData);
      selectedPhoto = null;
    }
    
    PhotoData currentPhotoData = null;
    if (photoMetaData != null) {
      for (PhotoData photoData: photos) {
        if (photoMetaData.equals(photoData.photoMetaData())) {
          currentPhotoData = photoData;
          break;
        }
      }
    }
    
    if (currentPhotoData != null) {
      Node node = currentPhotoData.node();
      getChildren().remove(node);
      
      Image photoImage = ImageResource.CAMERA_BLUE.getImage(ImageSize.SIZE_0);
      ImageView photoIcon = new ImageView(photoImage);
      installMouseHandlingOnPhotoIcon(photoIcon, photoMetaData);
      getChildren().add(photoIcon);
      photos.remove(currentPhotoData);
      PhotoData newPhotoData = new PhotoData(photoMetaData, photoIcon);
      photos.add(newPhotoData);
      selectedPhoto = newPhotoData;
    }
    
    markDirty();
    notifyObjectSelectionListeners(caller);
  }
  
  /**
   * Check whether a photo is already shown.
   * 
   * @param photoInfo information on the photo to be checked.
   * @return true if the photo is already on the map, false otherwise.
   */
  private boolean photoAlreadyOnMap(IPhotoMetaDataWithImage photoInfo) {
    for (PhotoData photoData: photos) {
      if (photoData.photoMetaData().getFileName().equals(photoInfo.getFileName())) {
        return true;
      }
    }
    
    return false;
  }

  /**
   * Show the single current photo.
   * <p>
   * If there is a current photo, is will be removed.<br/>
   * After this the current photo is shown, limiting the dimensions to MAX_IMAGE_WIDTH and MAX_IMAGE_HEIGHT.
   * @param mouseEvent 
   * 
   * @param coordinates the location where the photo is shown.
   * @param text An optional title for the photo. 
   * @param fileName the file name of the photo to be shown.
   */
  private void handleMouseEventOnPhotoIcon(MouseEvent mouseEvent, IPhotoInfo photoMetaData) {
    LOGGER.severe("=>");
    if (mouseEvent.getButton() == MouseButton.SECONDARY) {
      // right mouse, show PhotoMetaDataEditor
      if (currentPhoto != null) {
        removeCurrentPhoto();
      }
      
      imageInfoStage = new PhotoMetaDataEditor(customization, photoMetaData);
    } else {
      setSelectedPhoto(this, photoMetaData);
      
      if (imageInfoStage != null) {
        imageInfoStage.close();
        imageInfoStage = null;
      }
      
      if ((currentPhoto != null)  &&  currentPhoto.photoMetaData().equals(photoMetaData)) {
        removeCurrentPhoto();
      } else {
        if (currentPhoto != null) {
          removeCurrentPhoto();
        }

        String fileName = photoMetaData.getFileName();
        Dimension dimension = null;
        try {
          dimension = ImageUtils.getImageDimensions(new File(fileName));
        } catch (IOException e) {
          e.printStackTrace();
        }
        Image image;
        if (dimension == null  ||  dimension.width > MAX_IMAGE_WIDTH  ||  dimension.height > MAX_IMAGE_HEIGHT) {
          image = new Image("file:" + fileName, MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT, true, true);
        } else {
          image = new Image("file:" + fileName);
        }

        Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0);

        // Title on photo
        String title = photoMetaData.getTitle();
        if (title != null) {
          Font font = new Font(18);
          gc.setFont(font);
          gc.setFill(Color.WHITE);
          gc.fillText(title, 20, image.getHeight() - 15);
        }

        getChildren().add(canvas);
        currentPhoto = new PhotoData(photoMetaData, canvas);
        canvas.setOnMouseClicked(e -> removeCurrentPhoto());
      }
    }
  }
  
  /**
   * Remove the current photo.
   */
  private void removeCurrentPhoto() {
    Node node = currentPhoto.node();
    getChildren().remove(node);
    currentPhoto = null;
  }
  
//  /**
//   * Get the photo meta data with image for a file (photo).
//   * 
//   * @param file the File for which to obtain the information.
//   * @return the photo meta data with image for <code>file</code>,
//   *         or null if this information is not available in the <code>photoInfosPerFolder</code>
//   */
//  public IPhotoMetaDataWithImage getPhotoMetaDataWithImage(File file) {
//    String fileName = file.getAbsolutePath();
//    List<IPhotoMetaDataWithImage> photoInfos = photoInfosPerFolder.get(file.getParent());
//    if (photoInfos != null) {
//      for (IPhotoMetaDataWithImage photoInfo: photoInfos) {
//        if (fileName.equals(photoInfo.getFileName())) {
//          return photoInfo;
//        }
//      }
//    }
//    return null;
//  }


  @Override
  protected void layoutLayer() {
    LOGGER.info("=>");
//    WGS84BoundingBox mapBoundingBox = MapViewUtil.getVisibleMapCoordinates(baseMap);

//    if (showPhotoIndexOnMap) {
//      layoutIndexPhotosOnLayer();
//    } else {

      // photos
      for (PhotoData photoData: photos) {
        Node photoIcon = photoData.node();
        WGS84Coordinates coordinates = photoData.photoMetaData().getCoordinates();
        final Point2D mapPoint = baseMap.getMapPoint(coordinates.getLatitude(), coordinates.getLongitude());

        photoIcon.setTranslateX(mapPoint.getX());
        photoIcon.setTranslateY(mapPoint.getY());

      }
//    }

    if (zoomRectangle != null) {
      Polygon polygon = zoomRectangle.polygon();
      MapViewUtil.updateBoundingBoxPolygon(polygon, zoomRectangle.boundingBox(), baseMap);
      LOGGER.severe("Polygon drawn");
    }
    
    LOGGER.info("<=");
  }
  
//  /**
//   * Layout the photos in the <code>index</code> on the layer.
//   * <p>
//   * If there is no index set, nothing will be shown.
//   */
//  private void layoutIndexPhotosOnLayer() {
////    getChildren().clear();
//
//    if (index != null) {
//      WGS84BoundingBox mapBoundingBox = MapViewUtil.getVisibleMapCoordinates(baseMap);
//
//      LOGGER.info("Number of photos to show: " + index.numPoints());
//      S2Iterator<S2PointIndex.Entry<PhotoMetaData>> iterator = index.iterator();
//      int photosShown = 0;
//      int totalPhotos = 0;
//
//      while (!iterator.done()) {
//        totalPhotos++;
//        PhotoMetaData photoMetaData = iterator.entry().data();
//        WGS84Coordinates coordinates = photoMetaData.getCoordinates();
//
//        if (mapBoundingBox.containsPoint(coordinates)) {
//          Image photoImage = ImageResource.CAMERA_BLACK.getImage(ImageSize.SIZE_0);
//          ImageView photoIcon = new ImageView(photoImage);
//          final Point2D mapPoint = baseMap.getMapPoint(coordinates.getLatitude(), coordinates.getLongitude());
//
//          photoIcon.setTranslateX(mapPoint.getX());
//          photoIcon.setTranslateY(mapPoint.getY());
//          photoIcon.setOnMouseClicked(e -> handleMouseEventOnPhotoIcon(e, photoMetaData));
//
//          getChildren().add(photoIcon);
//          photosShown++;
//        }
//
//        iterator.next();
//      }
//      LOGGER.info("total: " + totalPhotos + ", shown: " + photosShown);
//    }
//    
//  }

  /*
   * ObjectSelector implementation
   */
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void addObjectSelectionListener(ObjectSelectionListener<IPhotoInfo> objectSelectionListener) {
    objectSelectionListeners.add(objectSelectionListener);    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeObjectSelectionListener(ObjectSelectionListener<IPhotoInfo> objectSelectionListener) {
    objectSelectionListeners.remove(objectSelectionListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPhotoInfo getSelectedObject() {
    if (selectedPhoto != null) {
      return selectedPhoto.photoMetaData();
    } else {
      return null;
    }
  }
  
  private void notifyObjectSelectionListeners(Object source) {
    IPhotoInfo photoInfo = null;
    if (selectedPhoto != null) {
      photoInfo = selectedPhoto.photoMetaData();
    }
    
    for (ObjectSelectionListener<IPhotoInfo> objectSelectionListener: objectSelectionListeners) {
      objectSelectionListener.objectSelected(source, photoInfo);
    }
  }
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
record PhotoData(IPhotoInfo photoMetaData, Node node) {
}

/**
 * Bounding box data, combines a WGS84BoundingBox with a polygon node.
 */
record BoundingBoxData(WGS84BoundingBox boundingBox, Polygon polygon) {
}


