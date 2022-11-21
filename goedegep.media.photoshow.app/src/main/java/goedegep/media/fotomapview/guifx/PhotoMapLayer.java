package goedegep.media.fotomapview.guifx;

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
import goedegep.media.photo.PhotoMetaData;
import goedegep.media.photo.PhotoMetaDataWithImage;
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
 * An point index (type S2PointIndex<PhotoMetaData>) can be set via method setPhotoIndex. If this index is set, it will be used to determine whether a photo has to be drawn or not.
 * So it will improve the performance.<br/>
 * Don't use this index if you are using this layer to add coordinates to a photo, because then of course this photo isn't in the index yet.<br/>
 * 
 * <b>photos being shown</b><br/>
 * if <code>showPhotoIndexOnMap</code> is true, only all photos of the index are shown on the map. Otherwise the photos set via ... are shown.
 * 
 * <b>photo icon</b>
 * Photos are represented by a small camera icon.<br/>
 * If the photo has approximate coordinates the icon is gray, otherwise it is black.
 * 
 * <b>dropping files</b><br/>
 * Photo files can be dropped on this layer. If the photo already has coordinates, these are not changed. Otherwise the coordinates are set to the location where the photo(s) is/are dropped.
 * 
 * In the photo information, the photo pathname is shown relative to the <code>rootFolderName</code>, which is passed to the constructor.<br/>
 * This map layer add itself as layer to the specified mapView.
 */
public class PhotoMapLayer extends MapLayer implements ObjectSelector<PhotoMetaDataWithImage> {
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
  
  /**
   * the {@link MapView} on which this layer is shown.
   */
  private MapView mapView;
  
  /**
   * Information of the photos shown, per folder.
   */
  private ObservableMap<String, List<PhotoMetaDataWithImage>> photoInfosPerFolder;
  
  /**
   * A small window to show image information and thumbnail and to edit image information.
   */
  PhotoMetaDataEditor imageInfoStage;
  
  /**
   * The photo that is currently being relocated.
   */
  private PhotoMetaDataWithImage relocatingPhoto = null;
  
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
   */
  private PhotoData currentPhoto = null;
  
  /**
   * Photo index
   */
  private S2PointIndex<PhotoMetaData> index;
  
  /**
   * If true, only all photos of the index are shown on the map.
   * Otherwise the photos set via ... are shown.
   */
  private boolean showPhotoIndexOnMap = false;
  
  /**
   * Listeners to the selected photo, used for the implementation of ObjectSelector.
   */
  private List<ObjectSelectionListener<PhotoMetaDataWithImage>> objectSelectionListeners = new ArrayList<>();
  
  // TEMP
  private Polygon polygon = new Polygon();



  /**
   * Constructor.
   * 
   * @param customization GUI customization
   * @param mapView the {@link MapView} on which this layer is shown.
   * @param photoInfosPerFolder information on the photos per folder.
   * @param modifiedPhotos a list of modified photos
   * @param rootFolderName the root folder name.
   */
  public PhotoMapLayer(CustomizationFx customization, PhotoMapView photoMapView, MapView mapView, ObservableMap<String, List<PhotoMetaDataWithImage>> photoInfosPerFolder, ObservableSet<PhotoMetaDataWithImage> modifiedPhotos, String rootFolderName) {
    this.customization = customization;
    this.mapView = mapView;
    this.photoInfosPerFolder = photoInfosPerFolder;
   
    MapChangeListener<String, List<PhotoMetaDataWithImage>> ml = (change) -> markDirty();
    photoInfosPerFolder.addListener(ml);
    
    mapView.addLayer(this);
            
    // Handle a drag dropped; add photos for the dropped files.
    mapView.setOnDragDropped(dragEvent -> {
      Dragboard dragboard = dragEvent.getDragboard();
      boolean success = false;
      if (dragboard.hasFiles()) {
        MapPoint mapPoint = mapView.getMapPosition(dragEvent.getX(), dragEvent.getY());
        WGS84Coordinates coordinates = new WGS84Coordinates(mapPoint.getLatitude(), mapPoint.getLongitude());
        photoMapView.addPhotos(dragboard.getFiles(), coordinates);
        
        success = true;
      }
      
      dragEvent.setDropCompleted(success);
      dragEvent.consume();
    });
        
    // Indicate that files can be dropped on the mapView (on this layer)
    mapView.setOnDragOver(dragEvent -> {
      if (dragEvent.getGestureSource() != mapView
          && dragEvent.getDragboard().hasFiles()) {
        dragEvent.acceptTransferModes(TransferMode.COPY);
      }
      dragEvent.consume();
    });
    
    getChildren().add(polygon);
    
    markDirty();
  }
  
  /**
   * Clear the photos shown on the layer. The index is not cleared.
   */
  public void clear() {
    if (currentPhoto != null) {
      removeCurrentPhoto();
    }
    
    if (imageInfoStage != null) {
      imageInfoStage.close();
      imageInfoStage = null;
    }
    
    photos.clear();
    
    if (!showPhotoIndexOnMap) {
      getChildren().clear();
    }
  }

  /**
   * Add a photo to the layer.
   * 
   * @param photoInfo information on the photo.
   */
  public WGS84BoundingBox addPhoto(PhotoMetaDataWithImage photoInfo) {
    if (photoAlreadyOnMap(photoInfo)) {
      return null;
    }
    
    Image photoImage;
    if (photoInfo.isApproximateGPScoordinates()) {
      photoImage = ImageResource.CAMERA_GRAY.getImage(ImageSize.SIZE_0);
    } else {
      photoImage = ImageResource.CAMERA_BLACK.getImage(ImageSize.SIZE_0);
    }
    ImageView photoIcon = new ImageView(photoImage);
    installMouseHandlingOnPhotoIcon(photoIcon, photoInfo);
    
    PhotoData photoData = new PhotoData(photoInfo, photoIcon);
    getChildren().add(photoIcon);
    photos.add(photoData);

    WGS84Coordinates coordinates = photoInfo.getCoordinates();
    WGS84BoundingBox wgs84BoundingBox = new WGS84BoundingBox(coordinates.getLongitude(), coordinates.getLatitude());
    return wgs84BoundingBox;
  }
  
  /**
   * Set the photo index.
   * 
   * @param index an <code>S2PointIndex<PhotoMetaData></code> which will be used to determine whether a photo has to be drawn or not. A null values removes the index.
   * @param showOnMap if true, all photos of this index will be shown, else the photos added via .... are shown.
   */
  public void setPhotoIndex(S2PointIndex<PhotoMetaData> index) {
    LOGGER.severe("Index set");
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
    this.showPhotoIndexOnMap = showPhotoIndexOnMap;
    markDirty();
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
   * @param photoMetaDataWithImage the PhotoMetaDataWithImage related to the <code>photoIcon</code>
   */
  private void installMouseHandlingOnPhotoIcon(ImageView photoIcon, PhotoMetaDataWithImage photoMetaDataWithImage) {
//    photoIcon.setEventDispatcher(new DoubleClickEventDispatcher(getEventDispatcher()));
//    photoIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleMouseEventOnPhotoIcon(1, photoInfo));
//    photoIcon.addEventHandler(DoubleClickEventDispatcher.MOUSE_DOUBLE_CLICKED, e -> handleMouseEventOnPhotoIcon(2, photoInfo));
    
    photoIcon.setOnMouseClicked(e -> handleMouseEventOnPhotoIcon(e, photoMetaDataWithImage));
    
    photoIcon.setOnMouseDragged(event -> {
      LOGGER.info("Mouse Dragged");
      LOGGER.info("SceneX: " + event.getSceneX() + ", SceneY: " + event.getSceneY());
      if (relocatingPhoto != null) {
        Point2D point2D = mapView.sceneToLocal(event.getSceneX(), event.getSceneY());
        LOGGER.info("point2D: " + point2D.getX() + ", " + point2D.getY());
        MapPoint mapPoint = mapView.getMapPosition(point2D.getX(), point2D.getY());
        WGS84Coordinates coordinates = new WGS84Coordinates(mapPoint.getLatitude(), mapPoint.getLongitude());
        photoMetaDataWithImage.getPhotoMetaData().setCoordinates(coordinates);
        markDirty();
      } else {
        getScene().setCursor(Cursor.CROSSHAIR);
        relocatingPhoto = photoMetaDataWithImage;
      }
      event.consume();
    });
    
    photoIcon.setOnMouseReleased(event -> {
      LOGGER.info("Mouse Released");
      getScene().setCursor(Cursor.DEFAULT);
      relocatingPhoto = null;
      event.consume();
    });
    
  }

  /**
   * Set the selected photo.
   * 
   * @param photoInfo The photo to be selected (may be null).
   */
  public void setSelectedPhoto(Object caller, PhotoMetaDataWithImage photoInfo) {
    if (selectedPhoto != null) {
      Node node = selectedPhoto.node();
      getChildren().remove(node);
      
      Image photoImage;
      if (photoInfo.isApproximateGPScoordinates()) {
        photoImage = ImageResource.CAMERA_GRAY.getImage(ImageSize.SIZE_0);
      } else {
//        photoImage = Resources.getCameraBlackIcon();
        photoImage = ImageResource.CAMERA_BLACK.getImage(ImageSize.SIZE_0);
      }
      ImageView photoIcon = new ImageView(photoImage);
      installMouseHandlingOnPhotoIcon(photoIcon, selectedPhoto.photoInfo());
      getChildren().add(photoIcon);
      photos.remove(selectedPhoto);
      PhotoData newPhotoData = new PhotoData(selectedPhoto.photoInfo(), photoIcon);
      photos.add(newPhotoData);
      selectedPhoto = null;
    }
    
    PhotoData currentPhotoData = null;
    if (photoInfo != null) {
      for (PhotoData photoData: photos) {
        if (photoInfo.equals(photoData.photoInfo())) {
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
      installMouseHandlingOnPhotoIcon(photoIcon, photoInfo);
      getChildren().add(photoIcon);
      photos.remove(currentPhotoData);
      PhotoData newPhotoData = new PhotoData(photoInfo, photoIcon);
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
  private boolean photoAlreadyOnMap(PhotoMetaDataWithImage photoInfo) {
    for (PhotoData photoData: photos) {
      if (photoData.photoInfo().getFileName().equals(photoInfo.getFileName())) {
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
  private void handleMouseEventOnPhotoIcon(MouseEvent mouseEvent, PhotoMetaDataWithImage photoInfo) {
    LOGGER.severe("=>");
    if (mouseEvent.getButton() == MouseButton.SECONDARY) {
      if (currentPhoto != null) {
        removeCurrentPhoto();
      }
      
//      WGS84Coordinates coordinates = photoInfo.getCoordinates();
//      final Point2D mp = baseMap.getMapPoint(coordinates.getLatitude(), coordinates.getLongitude());
//        imageInfoStage.setX(getXForImageInfo(mp));
//        imageInfoStage.setY(getYForImageInfo(mp));
//      imageInfoStage.show();
      
      imageInfoStage = new PhotoMetaDataEditor(customization, photoInfo);
    } else {
      setSelectedPhoto(this, photoInfo);
      
      if (imageInfoStage != null) {
        imageInfoStage.close();
        imageInfoStage = null;
      }
      
      if ((currentPhoto != null)  &&  currentPhoto.photoInfo().equals(photoInfo)) {
        removeCurrentPhoto();
      } else {
        if (currentPhoto != null) {
          removeCurrentPhoto();
        }

        String fileName = photoInfo.getFileName();
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
        String title = photoInfo.getTitle();
        if (title != null) {
          Font font = new Font(18);
          gc.setFont(font);
          gc.setFill(Color.WHITE);
          gc.fillText(title, 20, image.getHeight() - 15);
        }

        getChildren().add(canvas);
        currentPhoto = new PhotoData(photoInfo, canvas);
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
  
  
  public PhotoMetaDataWithImage getPhotoInfo(File file) {
    String fileName = file.getAbsolutePath();
    List<PhotoMetaDataWithImage> photoInfos = photoInfosPerFolder.get(file.getParent());
    if (photoInfos != null) {
      for (PhotoMetaDataWithImage photoInfo: photoInfos) {
        if (fileName.equals(photoInfo.getFileName())) {
          return photoInfo;
        }
      }
    }
    return null;
  }


  @Override
  protected void layoutLayer() {
    LOGGER.severe("=>");
    
    // show the photos from the index if applicable
    if (showPhotoIndexOnMap  && index != null) {
      getChildren().clear();
      WGS84BoundingBox mapBoundingBox = MapViewUtil.getVisibleMapCoordinates(mapView);
      
      LOGGER.severe("Number of photos to show: " + index.numPoints());
      S2Iterator<S2PointIndex.Entry<PhotoMetaData>> iterator = index.iterator();
      
      while (!iterator.done()) {
        PhotoMetaData photoMetaData = iterator.entry().data();
        WGS84Coordinates coordinates = photoMetaData.getCoordinates();
        
        Image photoImage = ImageResource.CAMERA_BLACK.getImage(ImageSize.SIZE_0);
        ImageView photoIcon = new ImageView(photoImage);
        final Point2D mapPoint = baseMap.getMapPoint(coordinates.getLatitude(), coordinates.getLongitude());
        
        photoIcon.setTranslateX(mapPoint.getX());
        photoIcon.setTranslateY(mapPoint.getY());
//        photoIcon.setVisible(false);
        
        getChildren().add(photoIcon);
        
        iterator.next();
      }
      
      return;
    }

//    polygon.setStroke(Color.YELLOW);
//    polygon.setStrokeWidth(2);
//    polygon.setFill(Color.TRANSPARENT);
//    polygon.setVisible(true);
//    ObservableList<Double> points = polygon.getPoints();
//    points.clear();
//    Point2D northWest = baseMap.getMapPoint(mapBoundingBox.getNorth(), mapBoundingBox.getWest());
//    points.add(northWest.getX());
//    points.add(northWest.getY());
//    Point2D northEast = baseMap.getMapPoint(mapBoundingBox.getNorth(), mapBoundingBox.getEast());
//    points.add(northEast.getX());
//    points.add(northEast.getY());
//    Point2D southEast = baseMap.getMapPoint(mapBoundingBox.getSouth(), mapBoundingBox.getEast());
//    points.add(southEast.getX());
//    points.add(southEast.getY());
//    Point2D southWest = baseMap.getMapPoint(mapBoundingBox.getSouth(), mapBoundingBox.getWest());
//    points.add(southWest.getX());
//    points.add(southWest.getY());
    
    
    // photos
    for (PhotoData photoData: photos) {
      Node photoIcon = photoData.node();
      WGS84Coordinates coordinates = photoData.photoInfo().getCoordinates();
      final Point2D mapPoint = baseMap.getMapPoint(coordinates.getLatitude(), coordinates.getLongitude());
      
      photoIcon.setTranslateX(mapPoint.getX());
      photoIcon.setTranslateY(mapPoint.getY());
      
    }

    LOGGER.severe("<=");
  }

  /*
   * ObjectSelector implementation
   */
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void addObjectSelectionListener(ObjectSelectionListener<PhotoMetaDataWithImage> objectSelectionListener) {
    objectSelectionListeners.add(objectSelectionListener);    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeObjectSelectionListener(ObjectSelectionListener<PhotoMetaDataWithImage> objectSelectionListener) {
    objectSelectionListeners.remove(objectSelectionListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PhotoMetaDataWithImage getSelectedObject() {
    if (selectedPhoto != null) {
      return selectedPhoto.photoInfo();
    } else {
      return null;
    }
  }
  
  private void notifyObjectSelectionListeners(Object source) {
    PhotoMetaDataWithImage photoInfo = null;
    if (selectedPhoto != null) {
      photoInfo = selectedPhoto.photoInfo();
    }
    
    for (ObjectSelectionListener<PhotoMetaDataWithImage> objectSelectionListener: objectSelectionListeners) {
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
record PhotoData(PhotoMetaDataWithImage photoInfo, Node node) {
}

