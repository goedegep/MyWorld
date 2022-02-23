package goedegep.media.fotomapview.guifx;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import goedegep.geo.dbl.WGS84BoundingBox;
import goedegep.geo.dbl.WGS84Coordinates;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.media.app.base.MediaAppResourcesFx;
import goedegep.media.fotoshow.app.guifx.PhotoInfo;
import goedegep.media.fotoshow.app.logic.GatherPhotoInfoTask;
import goedegep.util.img.ImageUtils;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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
public class PhotoMapLayer extends MapLayer {
  private static final Logger LOGGER = Logger.getLogger(PhotoMapView.class.getName());
  
  private static double MAX_IMAGE_WIDTH = 900;
  private static double MAX_IMAGE_HEIGHT = 500;
  
  private CustomizationFx customization;
  private MapView mapView;
  
  /**
   * Information of the photos shown, per folder.
   */
  private ObservableMap<String, List<PhotoInfo>> photoInfosPerFolder;
  
  private ComponentFactoryFx componentFactory;
  
  private boolean contextMenuActive = false;
  
  /**
   * A small window to show image information and thumbnail.
   */
  PhotoMetaDataEditor imageInfoStage;
  
  private PhotoInfo relocatingPhoto = null;
  
  private MediaAppResourcesFx appResources;
  
  private PhotoMapView photoMapView = null;
  
  /**
   * Photos shown on this layer.
   */
  private final ObservableList<PhotoData> photos  = FXCollections.observableArrayList();
  private PhotoData currentPhoto = null;


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
    this.photoMapView = photoMapView;
    this.mapView = mapView;
    this.photoInfosPerFolder = photoInfosPerFolder;
    
    componentFactory = customization.getComponentFactoryFx();
    
    appResources = (MediaAppResourcesFx) customization.getResources();
    
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
      photoImage = appResources.getPhotoGrayIcon();
    } else {
      photoImage = appResources.getPhotoIcon();
    }
    ImageView photoIcon = new ImageView(photoImage);
    photoIcon.setOnMouseClicked(e -> handleMouseEventOnPhotoIcon(e, photoInfo));
    getChildren().add(photoIcon);
    
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
    
    PhotoData photoData = new PhotoData(photoInfo, photoIcon);
    photos.add(photoData);

    WGS84Coordinates coordinates = photoInfo.getCoordinates();
    WGS84BoundingBox wgs84BoundingBox = new WGS84BoundingBox(coordinates.getLongitude(), coordinates.getLatitude());
    return wgs84BoundingBox;
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

//    this.getChildren().clear();
//    for (List<PhotoInfo> photoInfos: photoInfosPerFolder.values()) {
//      for (PhotoInfo photoInfo: photoInfos) {
//        WGS84Coordinates coordinates = photoInfo.getCoordinates();
//        if (coordinates == null) {
//          LOGGER.info("Skipping photo without coordinates");
//          continue;
//        }
//
//        Color color = null;
//        if (photoInfo.isApproximateGPScoordinates()) {
//          color = Color.PINK;
//        } else {
//          color = Color.LIGHTGOLDENRODYELLOW;
//        }
//        final Circle icon = new Circle(4.0, color);
//        icon.setVisible(true);
//        icon.setStroke(Color.RED);
//        icon.setStrokeWidth(1);
//
//        final Point2D mapPoint = baseMap.getMapPoint(coordinates.getLatitude(), coordinates.getLongitude());
//        icon.setTranslateX(mapPoint.getX());
//        icon.setTranslateY(mapPoint.getY());
//
////        EventHandler<MouseEvent> mouseClickedHandler = new EventHandler<>() {
////
////          @Override
////          public void handle(MouseEvent mouseEvent) {
////
////            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
////              ContextMenu contextMenu = createContextMenu(photoInfo);
////              LOGGER.severe("mouseEvent: x=" + mouseEvent.getScreenX() + ", y=" + mouseEvent.getScreenY());
////              contextMenuActive = true;
////              imageInfoStage.hide();
////              contextMenu.show(icon, mouseEvent.getScreenX() + 10, mouseEvent.getScreenY());
////              contextMenu.setOnHidden(e -> {
////                LOGGER.severe("Context menu hidden");
////                contextMenuActive = false;
////              });
////            }
////
////            mouseEvent.consume();
////          }
////
////        };
////
////
////        icon.setOnMouseClicked(mouseClickedHandler);
//        icon.setOnMouseClicked(mouseEvent -> {
//          if (mouseEvent.getButton() == MouseButton.SECONDARY) {
//            ContextMenu contextMenu = createContextMenu(photoInfo);
//            LOGGER.severe("mouseEvent: x=" + mouseEvent.getScreenX() + ", y=" + mouseEvent.getScreenY());
//            contextMenuActive = true;
//            imageInfoStage.hide();
//            contextMenu.show(icon, mouseEvent.getScreenX() + 10, mouseEvent.getScreenY());
//            contextMenu.setOnHidden(e -> {
//              LOGGER.severe("Context menu hidden");
//              contextMenuActive = false;
//            });
//          }
//
//          mouseEvent.consume();
//        });
//
//        icon.hoverProperty().addListener((obs, oldVal, newValue) -> {
//          if (newValue) {
//            if (!contextMenuActive) {
//              LOGGER.severe("mapPoint:" + mapPoint.getX() + ", " + mapPoint.getY());
////              LOGGER.severe("mapView.getLayout:" + mapView.getLayoutX() + ", " + mapView.getLayoutY());
////              LOGGER.severe("mapView.getTranslate:" + mapView.getTranslateX() + ", " + mapView.getTranslateY());
//              imageInfoStage.setImageInfo(photoInfo);
//              imageInfoStage.setX(getXForImageInfo(mapPoint));
//              imageInfoStage.setY(getYForImageInfo(mapPoint));
//              imageInfoStage.show();
//            }
//          } else {
//            imageInfoStage.hide();
//          }
//        });        
//
//        this.getChildren().add(icon);
//
//
//      }
//    }

    LOGGER.info("<=");
  }
  
  private double getXForImageInfo(Point2D mapPoint) {
//    Parent parent = mapView.getParent();
//    BorderPane borderPane = (BorderPane) parent;
//    Point2D p = borderPane.localToScreen(mapPoint);
//    LOGGER.severe("p: x=" + p.getX() + ", y=" + p.getY());
    
    double mapWidth = mapView.getWidth();
    double centerX = mapWidth / 2;
    LOGGER.severe("mapWidth: " + mapWidth + ", centerX: " + centerX);

    double x;
    if (mapPoint.getX() > centerX) {
      x = mapPoint.getX() - 120;
      LOGGER.severe("On right, so move left: " + x);
    } else {
      x = mapPoint.getX() + 490;
      LOGGER.severe("On left, so move right: " + x);
    }
    
    double stageWidth =  imageInfoStage.getWidth();
    LOGGER.severe("stageWidth: " + stageWidth);
    
    return x;
  }
  
  private double getYForImageInfo(Point2D mapPoint) {
    
    double mapHeight = mapView.getHeight();
    LOGGER.severe("mapHeight: " + mapHeight);

    double y = mapPoint.getY();
    
    if (y < 275) {
      y = 275;
    } else if (y > (mapHeight - 275)) {
      y = mapHeight - 275;
    }
    
    return y;
  }
  
  private ContextMenu createContextMenu(PhotoInfo photoInfo) {
    ContextMenu contextMenu = componentFactory.createContextMenu();
    MenuItem menuItem;
    
    menuItem = componentFactory.createMenuItem("Relocate");
    menuItem.setOnAction((ActionEvent event) -> {
      relocatingPhoto = photoInfo;
    });
    contextMenu.getItems().add(menuItem);
    
    return contextMenu;
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

