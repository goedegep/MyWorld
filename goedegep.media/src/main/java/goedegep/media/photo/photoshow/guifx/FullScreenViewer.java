package goedegep.media.photo.photoshow.guifx;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

import goedegep.jfx.img.ImageViewUtil;
import goedegep.util.img.PhotoFileMetaDataHandler;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FullScreenViewer extends Stage {
  private final static Logger LOGGER = Logger.getLogger(FullScreenViewer.class.getName());
  
  private final static String WINDOW_TITLE = "Selection Full Screen View";
  private final static double INITIAL_ZOOM_LEVEL = 1.0;
  
  /**
   * List of pictures, with PhotoInfo, to view.
   */
  private ObservableList<IPhotoInfo> picturesToView = null;
  
  /**
   * Current index in {@code picturesToView} or {@code pictureFilesToView}
   */
  private int pictureIndex = 0;
  
  /**
   * Information on the current picture.
   */
  private IPhotoInfo currentPictureInfo;
  
  
  /**
   * Scale factor from the Image to the ImageView.
   */
  private double imageToViewScaleFactor;
  
  private StackPane stackPane;
  private ImageView imageView = new ImageView();
  
  /**
   * The point of the Image where a drag has started.
   */
  Point2D dragStartPoint;
  
  /**
   * Zoom level.
   * 0 indicates no zoom; image is shown full screen.
   * Each higher value zooms in from this initial way the image is displayed.
   */
  private double zoomLevel = 0;
  
  
  public FullScreenViewer(ObservableList<IPhotoInfo> picturesToView) {
    Objects.requireNonNull(picturesToView, "picturesToView may not be null");
    if (picturesToView.isEmpty()) {
      throw new IllegalArgumentException("picturesToView may not be empty");
    }
    LOGGER.severe("=> number of picturesToView: " + picturesToView.size());
    this.picturesToView = picturesToView;
    
    setTitle(WINDOW_TITLE);

    setFullScreen(true);
    
    createFullScreenView();

    // Install 'Double clicking' toggles 'selected'.
    imageView.setOnMouseClicked(event -> {
      if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
        currentPictureInfo.toggleSelectedForTheShow();
        updateNotSelectedIndication(currentPictureInfo.isSelectedForTheShow());
      }
    });
    
    show();
    
    LOGGER.severe("stackPane/imageView size = " + stackPane.getWidth() + ", " + stackPane.getHeight());
    
    updateImage();
    
  }

  /**
   * Create the full screen view.
   */
  private void createFullScreenView() {
    stackPane = new StackPane();
    
    // Set a black background.
    BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
    Background background = new Background(backgroundFill);
    stackPane.setBackground(background);
    
    // Center the ImageView.
    stackPane.setAlignment(Pos.CENTER);

    imageView = new ImageView();
    imageView.setPreserveRatio(false);

    stackPane.getChildren().add(imageView);
    Scene scene = new Scene(stackPane);
    setScene(scene);
    
    /*
     *  Setup dragging the image around with the mouse.
     */
    // Normal cursor
    imageView.setOnMousePressed(e -> {
      double dragStartX = e.getSceneX();
      double dragStartY = e.getSceneY();
      LOGGER.info("MOUSE PRESSED: initx=" + dragStartX + ", inity=" + dragStartY);
      dragStartPoint = ImageViewUtil.imageViewCoordinatesToImageCoordinates(imageView, dragStartX, dragStartY);
      // Dragging cursor
      imageView.setCursor(Cursor.CLOSED_HAND);
    });
    imageView.setOnMouseDragged(e -> {
      double dragToX = e.getSceneX();
      double dragToY = e.getSceneY();
      Point2D dragPoint = ImageViewUtil.imageViewCoordinatesToImageCoordinates(imageView, dragToX, dragToY);
      double dragX = -(dragPoint.getX() - dragStartPoint.getX());
      double dragY = -(dragPoint.getY() - dragStartPoint.getY());
      Rectangle2D currentViewport = imageView.getViewport();
      Rectangle2D newViewport = new Rectangle2D(currentViewport.getMinX() + dragX, currentViewport.getMinY() + dragY, currentViewport.getWidth(), currentViewport.getHeight());
      newViewport = ImageViewUtil.updateOffsetsToAvoidWhiteSpace(imageView, newViewport);
      imageView.setViewport(newViewport);
    });

    // Key handling is on the scene, instead of on the imageView. So they also work if the cursor is not on the imageView.
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent event) {
        LOGGER.info("Key typed: " + event.getCode());
        Rectangle2D currentViewport;
        Rectangle2D newViewport;
        
        switch (event.getCode()) {

        // Right arrow: go to next image.
        case RIGHT:
          if (pictureIndex < picturesToView.size() - 1) {
            pictureIndex++;
            updateImage();
          }
          break;

        // Left arrow: go to previous image.
        case LEFT:
          if (pictureIndex > 0) {
            pictureIndex--;
            updateImage();
          }
          break;
          
        // 'W' key: move image 10% up.
        case W:
          moveImageUp(imageView, 10);
          break;

        // 'S' key: move image 10% down
        case S:
          moveImageDown(imageView, 10);
          break;

        // 'A' key: move image 10% left
        case A:
          moveImageLeft(imageView, 10);
          break;

        // 'D' key: move image 10% right
        case D:
          moveImageRight(imageView, 10);
          break;

        // Numpad '+' key and '=' key (below the '+' so you don't have to use 'Shift'): zoom in (on cursor if visible).
        case ADD:
        case EQUALS:
          zoomInOnCursorPosition(1.0);
          break;

        // Numpad '-' key and '-' key: zoom out around the center of the image, moving the image to fill the view if needed.
        case SUBTRACT:
        case MINUS:
          zoomOutFromCenter(1.0);
          break;

        // 'Esc' key: close the application.
        case ESCAPE:
          closeWindow();
          break;
          
        // 'Z' key: increase MinX
        case Z:
          currentViewport = imageView.getViewport();
          newViewport = new Rectangle2D(currentViewport.getMinX() + 10, currentViewport.getMinY(), currentViewport.getWidth(), currentViewport.getHeight());
          imageView.setViewport(newViewport);
          break;
          
        // 'X' key: decrease MinX
        case X:
          currentViewport = imageView.getViewport();
          newViewport = new Rectangle2D(currentViewport.getMinX() - 10, currentViewport.getMinY(), currentViewport.getWidth(), currentViewport.getHeight());
          imageView.setViewport(newViewport);
          break;
          
        // 'V' key: increase MinY
        case V:
          currentViewport = imageView.getViewport();
          newViewport = new Rectangle2D(currentViewport.getMinX(), currentViewport.getMinY() + 10, currentViewport.getWidth(), currentViewport.getHeight());
          imageView.setViewport(newViewport);
          break;
          
        // 'B' key: decrease MinY
        case B:
          currentViewport = imageView.getViewport();
          newViewport = new Rectangle2D(currentViewport.getMinX(), currentViewport.getMinY() - 10, currentViewport.getWidth(), currentViewport.getHeight());
          imageView.setViewport(newViewport);
          break;
          

        default:
          // no action
        }

        event.consume();
      }

    });
  }
  
  /**
   * Move the image a specified percentage of its size to the right.
   * <p>
   * The image is moved by the specified amount, unless it reaches the border.<br/>
   * 
   * @param imageView the ImageView to move.
   * @param percentage the amount to move to the right.
   */
  private void moveImageRight(ImageView imageView, double percentage) {
    double rotation = imageView.rotateProperty().getValue();
    Rectangle2D currentViewport = imageView.getViewport();
    
    if (rotation == 90.0  ||  rotation == -90.0) {
      // Moving to the right is done via a positive y-offset
      updateViewportOffsets(imageView, 0, currentViewport.getHeight() * percentage / 100);
    } else {
      // Moving to the right is done via a negative x-offset
      updateViewportOffsets(imageView, -(currentViewport.getWidth() * percentage / 100), 0);
    }
  }
  
  /**
   * Move the image a specified percentage of its size to the left.
   * <p>
   * The image is moved by the specified amount, unless it reaches the border.<br/>
   * 
   * @param imageView the ImageView to move.
   * @param percentage the amount to move to the left.
   */
  private void moveImageLeft(ImageView imageView, double percentage) {
    double rotation = imageView.rotateProperty().getValue();
    Rectangle2D currentViewport = imageView.getViewport();
    
    if (rotation == 90.0  ||  rotation == -90.0) {
      // Moving to the left is done via a negative y-offset
      updateViewportOffsets(imageView, 0, -(currentViewport.getHeight() * percentage / 100));
    } else {
      // Moving to the left is done via a positive x-offset
      updateViewportOffsets(imageView, currentViewport.getWidth() * percentage / 100, 0);
    }
  }
  
  /**
   * Move the image a specified percentage of its size upwards.
   * <p>
   * The image is moved by the specified amount, unless it reaches the border.<br/>
   * 
   * @param imageView the ImageView to move.
   * @param percentage the amount to move upwards.
   */
  private void moveImageUp(ImageView imageView, double percentage) {
    double rotation = imageView.rotateProperty().getValue();
    Rectangle2D currentViewport = imageView.getViewport();
    
    if (rotation == 90.0  ||  rotation == -90.0) {
      // Moving up is done via a positive x-offset
      updateViewportOffsets(imageView, currentViewport.getWidth() * percentage / 100, 0);
    } else {
      // Moving up is done via a positive y-offset
      updateViewportOffsets(imageView, 0, currentViewport.getHeight() * percentage / 100);
    }
  }
  
  /**
   * Move the image a specified percentage of its size downwards.
   * <p>
   * The image is moved by the specified amount, unless it reaches the border.<br/>
   * 
   * @param imageView the ImageView to move.
   * @param percentage the amount to move downwards.
   */
  private void moveImageDown(ImageView imageView, double percentage) {
    double rotation = imageView.rotateProperty().getValue();
    Rectangle2D currentViewport = imageView.getViewport();
    
    if (rotation == 90.0  ||  rotation == -90.0) {
      // Moving down is done via a negative x-offset
      updateViewportOffsets(imageView, -(currentViewport.getWidth() * percentage / 100), 0);
    } else {
      // Moving down is done via a negative y-offset
      updateViewportOffsets(imageView, 0, -(currentViewport.getHeight() * percentage) / 100);
    }
  }
  
  /**
   * Update the viewport offsets of the viewport of an ImageView.
   * <p>
   * After applying the delta's to the offsets, the offsets are updated to avoid white space.
   * 
   * @param imageView the ImageView
   * @param deltaX the amount to add to the x-offset.
   * @param deltaY the amount to add to the y-offset.
   */
  private void updateViewportOffsets(ImageView imageView, double deltaX, double deltaY) {
    Rectangle2D currentViewport = imageView.getViewport();
    Rectangle2D newViewport = new Rectangle2D(currentViewport.getMinX() + deltaX, currentViewport.getMinY() + deltaY, currentViewport.getWidth(), currentViewport.getHeight());
    imageView.setViewport(newViewport);
  }
  
  
  /**
   * Zoom in on the current cursor position.
   * <p>
   * The image is zoomed in on the current cursor position, but to the image is shifted so the complete view is filled.
   */
  private void zoomInOnCursorPosition(double zoomLevelIncrease) {
    // We can only get the mouse location relative to the Screen.
    Point mouseLocationOnScreen = java.awt.MouseInfo.getPointerInfo().getLocation();
    LOGGER.info("mouseLocationOnScreen=" + mouseLocationOnScreen.x + "," + mouseLocationOnScreen.y);
    
    // Convert to coordinates within the imageView
    Point2D mouseCoordinates = imageView.screenToLocal(mouseLocationOnScreen.x, mouseLocationOnScreen.y);
    LOGGER.info("mouseCoordinates=" + mouseCoordinates.getX() + "," + mouseCoordinates.getY());
    
    // Calculate zoom point
    Point2D imageZoomPoint = ImageViewUtil.imageViewCoordinatesToImageCoordinates(imageView, mouseCoordinates.getX(), mouseCoordinates.getY());
    LOGGER.info("zoom point=" + imageZoomPoint.getX() + ", " + imageZoomPoint.getY());

    zoomLevel += zoomLevelIncrease;
    if (zoomLevel > 20) {
      zoomLevel = 20;
    }
        
    // Calculate new viewport parameters.
    Dimension2D viewportDimensions = ImageViewUtil.calculateViewportDimensions(imageView, zoomLevel);
    double rotation = imageView.rotateProperty().getValue();
    double viewportRectangleXOffset;
    double viewportRectangleYOffset;
    
    if (rotation == 90.0  ||  rotation == -90.0) {
      viewportRectangleXOffset = imageZoomPoint.getY() - viewportDimensions.getHeight() / 2;
      viewportRectangleYOffset = imageZoomPoint.getX() - viewportDimensions.getWidth() / 2;
    } else {
      viewportRectangleXOffset = imageZoomPoint.getX() - viewportDimensions.getWidth() / 2;
      viewportRectangleYOffset = imageZoomPoint.getY() - viewportDimensions.getHeight() / 2;
    }        
    
    Rectangle2D newViewport = new Rectangle2D(viewportRectangleXOffset, viewportRectangleYOffset, viewportDimensions.getWidth(), viewportDimensions.getHeight());
    
    imageView.setViewport(newViewport);
    
  }
  
  /**
   * Zoom out from the center of the screen.
   * 
   * @param zoomLevelDecrease the amount to zoom out.
   */
  private void zoomOutFromCenter(double zoomLevelDecrease) {
    // Coordinates for the center of the imageView.
    Point2D imageViewCenterCoordinates = new Point2D(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
    Point2D zoomOutPoint = ImageViewUtil.imageViewCoordinatesToImageCoordinates(imageView, imageViewCenterCoordinates.getX(), imageViewCenterCoordinates.getY());
    
    Rectangle2D currentViewport = imageView.getViewport();
    double viewportRectangleXOffset = currentViewport.getMinX();
    double viewportRectangleYOffset = currentViewport.getMinY();
    double imageToViewScaleFactor = imageView.getFitWidth() / ImageViewUtil.getImageViewImageHorizontalSize(imageView);
    double currentX = currentViewport.getWidth() / 2 / zoomLevel / imageToViewScaleFactor;
    double currentY = currentViewport.getHeight() / 2 / zoomLevel / imageToViewScaleFactor;
    
    zoomLevel -= zoomLevelDecrease;
    if (zoomLevel < 1.0) {
      zoomLevel = 1.0;
    }
    
    // Calculate new viewport parameters.
    Dimension2D viewportDimensions = ImageViewUtil.calculateViewportDimensions(imageView, zoomLevel);
    
    double newX = viewportDimensions.getWidth() / 2 / zoomLevel / imageToViewScaleFactor;
    double newY = viewportDimensions.getHeight() / 2 / zoomLevel / imageToViewScaleFactor;
    viewportRectangleXOffset -= newX - currentX;
    viewportRectangleYOffset -= newY - currentY;
    
    
    // Create and set new viewport
    Rectangle2D viewportRectangle = new Rectangle2D(viewportRectangleXOffset, viewportRectangleYOffset, viewportDimensions.getWidth(), viewportDimensions.getHeight());
    viewportRectangle = ImageViewUtil.updateOffsetsToAvoidWhiteSpace(imageView, viewportRectangle);
    imageView.setViewport(viewportRectangle);
    
    // Move the mouse cursor to the point on which we zoomed out.
    Point2D newMouseCursorLocation = ImageViewUtil.imageCoordinatesToImageViewCoordinates(imageView, zoomOutPoint.getX(), zoomOutPoint.getY());
    moveMousePointer(newMouseCursorLocation);
  }

  /**
   * Update the image of the imageView, and the 'not selected indication' upon selection of a new currentPhotoInfo.
   * <p>
   * The image is read from the file which is specified in the currentPhotoInfo.<br/>
   * The image is shown as large as possible, preserving the ratio.<br/>
   * The zoomLevel is reset to the INITIAL_ZOOM_LEVEL.
   */
  private void updateImage() {
    LOGGER.info("=>");
    String fileName;
    currentPictureInfo = picturesToView.get(pictureIndex);
    fileName = currentPictureInfo.getFileName();
    int orientation = TiffTagConstants.ORIENTATION_VALUE_HORIZONTAL_NORMAL; // default orientation, used if there is no 'Orientation' in the meta data.
    
    try {
      PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(new File(fileName));
      String orientationText = photoFileMetaDataHandler.getTiffItemValue(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT, "Orientation");
      if (orientationText != null) {
        orientation = Integer.valueOf(orientationText);
      }
    } catch (ImageReadException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
        
    Image image = new Image("file:" + fileName);
    
    zoomLevel = INITIAL_ZOOM_LEVEL;
    
    imageView.setImage(image);
    
    // Rotate image based on the 'Orientation'.
    switch (orientation) {
    case TiffTagConstants.ORIENTATION_VALUE_HORIZONTAL_NORMAL:
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL:
      imageView.setRotate(0);
      break;
      
    case TiffTagConstants.ORIENTATION_VALUE_ROTATE_90_CW:
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_90_CW:
      imageView.setRotate(90);
      break;      
      
    case TiffTagConstants.ORIENTATION_VALUE_ROTATE_180:
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_VERTICAL:
      imageView.setRotate(180);
      break;
      
    case TiffTagConstants.ORIENTATION_VALUE_MIRROR_HORIZONTAL_AND_ROTATE_270_CW:
    case TiffTagConstants.ORIENTATION_VALUE_ROTATE_270_CW:
      imageView.setRotate(270);
      break;
    }
    
    LOGGER.info("imageWidth=" + ImageViewUtil.getImageViewImageHorizontalSize(imageView) + ", imageHeight=" + ImageViewUtil.getImageViewImageVerticalSize(imageView));

    imageToViewScaleFactor = calculateImageToViewScaleFactor(stackPane);
    LOGGER.info("imageToViewScaleFactor: " + imageToViewScaleFactor);
    
    Dimension2D imageViewDimensions = ImageViewUtil.calculateImageViewDimensions(imageView, imageToViewScaleFactor);
    LOGGER.info("imageViewDimensions: " + imageViewDimensions.getWidth() + ", " + imageViewDimensions.getHeight());
    imageView.setFitWidth(imageViewDimensions.getWidth());
    imageView.setFitHeight(imageViewDimensions.getHeight());
    
    Dimension2D viewportDimensions = ImageViewUtil.calculateViewportDimensions(imageView, zoomLevel);
    Rectangle2D viewportRectangle = new Rectangle2D(0.0, 0.0, viewportDimensions.getWidth(), viewportDimensions.getHeight());
    LOGGER.info("viewport: " + viewportRectangle.getMinX() + ", " + viewportRectangle.getMinY() + ", " + viewportRectangle.getWidth() + ", " + viewportRectangle.getHeight());
    imageView.setViewport(viewportRectangle);
    
    if (currentPictureInfo != null) {
      updateNotSelectedIndication(currentPictureInfo.isSelectedForTheShow());
    }
  }
  
  /**
   * Calculate the scale factor between an Image and the Region in which it will be displayed (via an ImageView).
   * <p>
   * If the Image is larger than the Region, the factor is less than 1.
   * The factor is such that the largest size (horizontal or vertical) will exactly fit the Region and the other size may be smaller than the Region.
   * 
   * @param region the Region in which the <code>image</code> shall fit.
   * @return the scale factor to be applied to the <code>image</code> for the best fit in the <code>region</code>.
   */
  private double calculateImageToViewScaleFactor(Region region) {
    double horizontalImageScreenRatio =  region.getWidth() / ImageViewUtil.getImageViewImageHorizontalSize(imageView);
    double verticalImageScreenRatio =  region.getHeight() / ImageViewUtil.getImageViewImageVerticalSize(imageView);
    
    double imageToViewScaleFactor;
    if (horizontalImageScreenRatio < verticalImageScreenRatio) {
      imageToViewScaleFactor = horizontalImageScreenRatio;
    } else {
      imageToViewScaleFactor = verticalImageScreenRatio;
    }
    LOGGER.info("<= " + imageToViewScaleFactor);
    
    return imageToViewScaleFactor;
  }
  
  /**
   * Move the mouse pointer to a specified location.
   */
  private void moveMousePointer(Point2D mouseCursorLocation) {
    LOGGER.info("=> " + mouseCursorLocation.getX() + ", " + mouseCursorLocation.getY());
    // Convert to a point on the screen.
    Point2D pointOnScreen = imageView.localToScene(mouseCursorLocation);
    LOGGER.info("imageView size=" + imageView.getFitWidth() + ", " + imageView.getFitHeight());
    try {
      Robot robot = new Robot();
      LOGGER.info("Moving mouse to: " + pointOnScreen.getX() + ", " + pointOnScreen.getY());
      robot.mouseMove((int) pointOnScreen.getX(), (int) pointOnScreen.getY());
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Update the 'not selected indication'.
   * <p>
   * This indicatin is a white 'X' in the top right corner.
   * It is shown if the photo isn't selected for the show.
   * 
   * @param selected if true, the photo is selected for the show.
   */
  private void updateNotSelectedIndication(boolean selected) {
    if (!selected) {
      if (stackPane.getChildren().size() <= 1) {
        Label notSelectedLabel = new Label("X");
        notSelectedLabel.setStyle("-fx-font: 40 arial;");
        notSelectedLabel.setTextFill(Color.ANTIQUEWHITE);
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setOffsetX(5.0f);
        ds.setColor(Color.color(0.2f, 0.2f, 0.2f));
        notSelectedLabel.setEffect(ds);
        HBox labelBox = new HBox(50);
        labelBox.setPadding(new Insets(20, 20, 20, 20));
        labelBox.setMaxHeight(80);
        labelBox.setMaxWidth(80);

        labelBox.getChildren().add(notSelectedLabel);
        stackPane.getChildren().add(labelBox);
        StackPane.setAlignment(labelBox, Pos.TOP_RIGHT);
      }
    } else {
      if (stackPane.getChildren().size() > 1) {
        // It's there, remove it
        stackPane.getChildren().remove(1);
      }  // else it's not there, no action
    }
  }
  
  /**
   * Close this window (which may also close the application).
   */
  private void closeWindow() {
    this.close();
  }
}
