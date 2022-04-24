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

import goedegep.geo.dbl.WGS84BoundingBox;
import goedegep.geo.dbl.WGS84Coordinates;
import goedegep.jfx.CustomizationFx;
import goedegep.media.fotoshow.app.guifx.PhotoInfo;
import goedegep.resources.Resources;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class is a {@link MapLayer} which shows the locations of photos on the map.
 * <p>
 * In the photo information, the photo pathname is shown relative to the <code>rootFolderName</code>, which is passed to the constructor.
 */
public class PhotoMapLayer extends MapLayer implements ObjectSelector<PhotoInfo> {
  private static final Logger LOGGER = Logger.getLogger(PhotoMapView.class.getName());
  
  private static double MAX_IMAGE_WIDTH = 900;
  private static double MAX_IMAGE_HEIGHT = 500;
  
  private CustomizationFx customization;
  private MapView mapView;
  
  /**
   * Information of the photos shown, per folder.
   */
  private ObservableMap<String, List<PhotoInfo>> photoInfosPerFolder;
  
  /**
   * A small window to show image information and thumbnail.
   */
  PhotoMetaDataEditor imageInfoStage;
  
  private PhotoInfo relocatingPhoto = null;
  
  /**
   * Photos shown on this layer.
   */
  private final ObservableList<PhotoData> photos  = FXCollections.observableArrayList();
  private PhotoData selectedPhoto = null;
  private PhotoData currentPhoto = null;
  
  private List<ObjectSelectionListener<PhotoInfo>> objectSelectionListeners = new ArrayList<>();


  /**
   * Constructor.
   * 
   * @param customization GUI customization
   * @param mapView the {@link MapView} on which this layer is shown.
   * @param photoInfosPerFolder information on the photos per folder.
   * @param modifiedPhotos a list of modified photos
   * @param rootFolderName the root folder name.
   */
  public PhotoMapLayer(CustomizationFx customization, PhotoMapView photoMapView, MapView mapView, ObservableMap<String, List<PhotoInfo>> photoInfosPerFolder, ObservableSet<PhotoInfo> modifiedPhotos, String rootFolderName) {
    this.customization = customization;
    this.mapView = mapView;
    this.photoInfosPerFolder = photoInfosPerFolder;
   
    MapChangeListener<String, List<PhotoInfo>> ml = new MapChangeListener<>() {

      @Override
      public void onChanged(Change<? extends String, ? extends List<PhotoInfo>> change) {
        markDirty();        
      }

      
    };
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
        
//          for (File file: dragboard.getFiles()) {
//            LOGGER.info(file.getAbsolutePath());
//            PhotoInfo photoInfo = GatherPhotoInfoTask.createPhotoInfo(file.getAbsolutePath(), -1);
//            if (photoInfo.getCoordinates() == null) {
//              MapPoint mapPoint = mapView.getMapPosition(dragEvent.getX(), dragEvent.getY());
//              WGS84Coordinates coordinates = new WGS84Coordinates(mapPoint.getLatitude(), mapPoint.getLongitude());
//              photoInfo.setCoordinates(coordinates);
//              markDirty();
//              addPhoto(photoInfo);
//              modifiedPhotos.add(photoInfo);
//            } else {
//              LOGGER.info("Coordinates already set");
//              addPhoto(photoInfo);
//              markDirty();
//            }
//            
//          }
          success = true;
      }
      /* let the source know whether the string was successfully 
       * transferred and used */
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
    
    markDirty();
  }
  
  /**
   * Clear the layer.
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
    getChildren().clear();
  }

  /**
   * Add a photo to the layer.
   * 
   * @param coordinates The {@code WGS84Coordinates} at which the photo will be shown.
   * @param text An optional title for the photo.
   * @param fileName the photo filename.
   */
  public WGS84BoundingBox addPhoto(PhotoInfo photoInfo) {
    if (photoAlreadyOnMap(photoInfo)) {
      return null;
    }
    
    Image photoImage;
    if (photoInfo.isApproximateGPScoordinates()) {
      photoImage = Resources.getCameraGrayIcon();
    } else {
      photoImage = Resources.getCameraBlackIcon();
    }
    ImageView photoIcon = new ImageView(photoImage);
    installMouseHandlingOnPhotoIcon(photoIcon, photoInfo);
//    photoIcon.setOnMouseClicked(e -> handleMouseEventOnPhotoIcon(e, photoInfo));
//    
//    photoIcon.setOnMouseDragged(event -> {
//      LOGGER.info("Mouse Dragged");
//      LOGGER.info("SceneX: " + event.getSceneX() + ", SceneY: " + event.getSceneY());
//      if (relocatingPhoto != null) {
//        Point2D point2D = mapView.sceneToLocal(event.getSceneX(), event.getSceneY());
//        LOGGER.info("point2D: " + point2D.getX() + ", " + point2D.getY());
//        MapPoint mapPoint = mapView.getMapPosition(point2D.getX(), point2D.getY());
//        WGS84Coordinates coordinates = new WGS84Coordinates(mapPoint.getLatitude(), mapPoint.getLongitude());
//        photoInfo.setCoordinates(coordinates);
//        markDirty();
//      } else {
//        getScene().setCursor(Cursor.CROSSHAIR);
//        relocatingPhoto = photoInfo;
//      }
//      event.consume();
//    });
//    
//    photoIcon.setOnMouseReleased(event -> {
//      LOGGER.info("Mouse Released");
//      getScene().setCursor(Cursor.DEFAULT);
//      relocatingPhoto = null;
//      event.consume();
//    });
    
    PhotoData photoData = new PhotoData(photoInfo, photoIcon);
    getChildren().add(photoIcon);
    photos.add(photoData);

    WGS84Coordinates coordinates = photoInfo.getCoordinates();
    WGS84BoundingBox wgs84BoundingBox = new WGS84BoundingBox(coordinates.getLongitude(), coordinates.getLatitude());
    return wgs84BoundingBox;
  }
  
  private void installMouseHandlingOnPhotoIcon(ImageView photoIcon, PhotoInfo photoInfo) {
    photoIcon.setOnMouseClicked(e -> handleMouseEventOnPhotoIcon(e, photoInfo));
    
    photoIcon.setOnMouseDragged(event -> {
      LOGGER.info("Mouse Dragged");
      LOGGER.info("SceneX: " + event.getSceneX() + ", SceneY: " + event.getSceneY());
      if (relocatingPhoto != null) {
        Point2D point2D = mapView.sceneToLocal(event.getSceneX(), event.getSceneY());
        LOGGER.info("point2D: " + point2D.getX() + ", " + point2D.getY());
        MapPoint mapPoint = mapView.getMapPosition(point2D.getX(), point2D.getY());
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
      relocatingPhoto = null;
      event.consume();
    });
    
  }

  /**
   * Set the selected photo.
   * 
   * @param photoInfo The photo to be selected (may be null).
   */
  public void setSelectedPhoto(Object caller, PhotoInfo photoInfo) {
    if (selectedPhoto != null) {
      Node node = selectedPhoto.node();
      getChildren().remove(node);
      
      Image photoImage;
      if (photoInfo.isApproximateGPScoordinates()) {
        photoImage = Resources.getCameraGrayIcon();
      } else {
        photoImage = Resources.getCameraBlackIcon();
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
      
      Image photoImage = Resources.getCameraBlueIcon();
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
  
  private boolean photoAlreadyOnMap(PhotoInfo photoInfo) {
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
  private void handleMouseEventOnPhotoIcon(MouseEvent mouseEvent, PhotoInfo photoInfo) {
    if (mouseEvent.getClickCount() > 1) {
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
  
  
  public PhotoInfo getPhotoInfo(File file) {
    String fileName = file.getAbsolutePath();
    List<PhotoInfo> photoInfos = photoInfosPerFolder.get(file.getParent());
    if (photoInfos != null) {
      for (PhotoInfo photoInfo: photoInfos) {
        if (fileName.equals(photoInfo.getFileName())) {
          return photoInfo;
        }
      }
    }
    return null;
  }


  @Override
  protected void layoutLayer() {
    LOGGER.info("=>");
    
    // photos
    for (PhotoData photoData: photos) {
      Node photoIcon = photoData.node();
      WGS84Coordinates coordinates = photoData.photoInfo().getCoordinates();
      final Point2D mapPoint = baseMap.getMapPoint(coordinates.getLatitude(), coordinates.getLongitude());
      
      photoIcon.setTranslateX(mapPoint.getX());
      photoIcon.setTranslateY(mapPoint.getY());
      
    }

    LOGGER.info("<=");
  }

  /*
   * ObjectSelector implementation
   */
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void addObjectSelectionListener(ObjectSelectionListener<PhotoInfo> objectSelectionListener) {
    objectSelectionListeners.add(objectSelectionListener);    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeObjectSelectionListener(ObjectSelectionListener<PhotoInfo> objectSelectionListener) {
    objectSelectionListeners.remove(objectSelectionListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PhotoInfo getSelectedObject() {
    if (selectedPhoto != null) {
      return selectedPhoto.photoInfo();
    } else {
      return null;
    }
  }
  
  private void notifyObjectSelectionListeners(Object source) {
    PhotoInfo photoInfo = null;
    if (selectedPhoto != null) {
      photoInfo = selectedPhoto.photoInfo();
    }
    
    for (ObjectSelectionListener<PhotoInfo> objectSelectionListener: objectSelectionListeners) {
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
record PhotoData(PhotoInfo photoInfo, Node node) {
}

