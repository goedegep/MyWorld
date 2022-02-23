package goedegep.media.fotoshow.app.guifx;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.List;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

import goedegep.util.img.PhotoFileMetaDataHandler;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
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
  private ObservableList<PhotoInfo> picturesToView = null;
  
  /**
   * List of picture files to view.
   */
  private List<String> pictureFilesToView = null;
  
  /**
   * Current index in {@code picturesToView} or {@code pictureFilesToView}
   */
  private int pictureIndex = 0;
  
  /**
   * Information on the current picture.
   */
  private PhotoInfo currentPictureInfo;
  
  private boolean cursorOn;
  private Cursor noCursor;
  private Cursor pointerCursor;
  
  
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
  
  
  public FullScreenViewer(ObservableList<PhotoInfo> picturesToView) {
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
    
    noCursor = Cursor.NONE;
    Image cursorImage = new Image(getClass().getResourceAsStream("pointerCursor.png"));
    pointerCursor = new ImageCursor(cursorImage, 0d, 0d);
    
    cursorOn = true;
    toggleCursor();
    show();
    
    LOGGER.severe("stackPane/imageView size = " + stackPane.getWidth() + ", " + stackPane.getHeight());
    
    updateImage();
    
//    currentPictureInfo.selectedForTheShowProperty().addListener(new ChangeListener<Boolean>() {
//
//      @Override
//      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//        updateNotSelectedIndication(currentPictureInfo.isSelectedForTheShow());
//      }
//       
//    });
  }
  
  public FullScreenViewer(List<String> picturesFilesToView) {
    Objects.requireNonNull(picturesFilesToView, "picturesFilesToView may not be null");
    if (picturesFilesToView.isEmpty()) {
      throw new IllegalArgumentException("picturesFilesToView may not be empty");
    }
    LOGGER.severe("=> number of picturesFilesToView: " + picturesFilesToView.size());
    this.pictureFilesToView = picturesFilesToView;
    
    setTitle(WINDOW_TITLE);

    setFullScreen(true);
    
    createFullScreenView();

    noCursor = Cursor.NONE;
    Image cursorImage = new Image(getClass().getResourceAsStream("pointerCursor.png"));
    pointerCursor = new ImageCursor(cursorImage, 0d, 0d);
    
    cursorOn = true;
    toggleCursor();
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
      LOGGER.severe("MOUSE PRESSED: initx=" + dragStartX + ", inity=" + dragStartY);
      dragStartPoint = imageViewCoordinatesToImageCoordinates(imageView, dragStartX, dragStartY);
      // Dragging cursor
      imageView.setCursor(Cursor.CLOSED_HAND);
    });
    imageView.setOnMouseReleased(e -> {
      // Back to normal cursor
      imageView.setCursor(pointerCursor);
    });
    imageView.setOnMouseDragged(e -> {
      double dragToX = e.getSceneX();
      double dragToY = e.getSceneY();
      Point2D dragPoint = imageViewCoordinatesToImageCoordinates(imageView, dragToX, dragToY);
      double dragX = -(dragPoint.getX() - dragStartPoint.getX());
      double dragY = -(dragPoint.getY() - dragStartPoint.getY());
      Rectangle2D currentViewport = imageView.getViewport();
      Rectangle2D newViewport = new Rectangle2D(currentViewport.getMinX() + dragX, currentViewport.getMinY() + dragY, currentViewport.getWidth(), currentViewport.getHeight());
      newViewport = updateOffsetsToAvoidWhiteSpace(newViewport);
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
          if (picturesToView != null) {
            if (pictureIndex < picturesToView.size() - 1) {
              pictureIndex++;
              updateImage();
            }
          } else {
            if (pictureIndex < pictureFilesToView.size() - 1) {
              pictureIndex++;
              updateImage();
            }
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

        // Numpad '+' key: zoom in (on cursor if visible).
        case ADD:
          zoomInOnCursorPosition(1.0);
          break;

        // Numpad '-' key: zoom out around the center of the image, moving the image to fill the view if needed.
        case SUBTRACT:
          zoomOutFromCenter(1.0);
          break;

        // 'C' key: toggle the cursor on/off
        case C:
          toggleCursor();
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
    double rotation = getImageViewImageRotation(imageView);
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
    double rotation = getImageViewImageRotation(imageView);
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
    double rotation = getImageViewImageRotation(imageView);
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
    double rotation = getImageViewImageRotation(imageView);
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
//    newViewport = updateOffsetsToAvoidWhiteSpace(newViewport);
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
    LOGGER.severe("mouseLocationOnScreen=" + mouseLocationOnScreen.x + "," + mouseLocationOnScreen.y);
    
    // Convert to coordinates within the imageView
    Point2D mouseCoordinates = imageView.screenToLocal(mouseLocationOnScreen.x, mouseLocationOnScreen.y);
    LOGGER.severe("mouseCoordinates=" + mouseCoordinates.getX() + "," + mouseCoordinates.getY());
    
    // Calculate zoom point
    Point2D imageZoomPoint = imageViewCoordinatesToImageCoordinates(imageView, mouseCoordinates.getX(), mouseCoordinates.getY());
    LOGGER.severe("zoom point=" + imageZoomPoint.getX() + ", " + imageZoomPoint.getY());

    zoomLevel += zoomLevelIncrease;
    if (zoomLevel > 20) {
      zoomLevel = 20;
    }
        
    // Calculate new viewport parameters.
    Dimension2D viewportDimensions = calculateViewportDimensions(imageView);
    double rotation = getImageViewImageRotation(imageView);
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
    
//    newViewport = updateOffsetsToAvoidWhiteSpace(newViewport);
    
    imageView.setViewport(newViewport);
    
    // Move the mouse cursor to the point on which we zoomed in.
//    Point2D newMouseCursorLocation = imageCoordinatesToImageViewCoordinates(imageView, imageZoomPoint.getX(), imageZoomPoint.getY());
//    moveMousePointer(newMouseCursorLocation);
  }
  
  /**
   * Zoom out from the center of the screen.
   * 
   * @param zoomLevelDecrease the amount to zoom out.
   */
  private void zoomOutFromCenter(double zoomLevelDecrease) {
    // Coordinates for the center of the imageView.
    Point2D imageViewCenterCoordinates = new Point2D(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
    Point2D zoomOutPoint = imageViewCoordinatesToImageCoordinates(imageView, imageViewCenterCoordinates.getX(), imageViewCenterCoordinates.getY());
    
    Rectangle2D currentViewport = imageView.getViewport();
    double viewportRectangleXOffset = currentViewport.getMinX();
    double viewportRectangleYOffset = currentViewport.getMinY();
    double imageToViewScaleFactor = imageView.getFitWidth() / getImageViewImageHorizontalSize(imageView);
    double currentX = currentViewport.getWidth() / 2 / zoomLevel / imageToViewScaleFactor;
    double currentY = currentViewport.getHeight() / 2 / zoomLevel / imageToViewScaleFactor;
    
    zoomLevel -= zoomLevelDecrease;
    if (zoomLevel < 1.0) {
      zoomLevel = 1.0;
    }
    
    // Calculate new viewport parameters.
    Dimension2D viewportDimensions = calculateViewportDimensions(imageView);
    
    double newX = viewportDimensions.getWidth() / 2 / zoomLevel / imageToViewScaleFactor;
    double newY = viewportDimensions.getHeight() / 2 / zoomLevel / imageToViewScaleFactor;
    viewportRectangleXOffset -= newX - currentX;
    viewportRectangleYOffset -= newY - currentY;
    
    
    // Create and set new viewport
    Rectangle2D viewportRectangle = new Rectangle2D(viewportRectangleXOffset, viewportRectangleYOffset, viewportDimensions.getWidth(), viewportDimensions.getHeight());
    viewportRectangle = updateOffsetsToAvoidWhiteSpace(viewportRectangle);
    imageView.setViewport(viewportRectangle);
    
    // Move the mouse cursor to the point on which we zoomed out.
    Point2D newMouseCursorLocation = imageCoordinatesToImageViewCoordinates(imageView, zoomOutPoint.getX(), zoomOutPoint.getY());
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
    LOGGER.severe("=>");
    String fileName;
    if (picturesToView != null) {
      currentPictureInfo = picturesToView.get(pictureIndex);
      fileName = currentPictureInfo.getFileName();
    } else {
      fileName = pictureFilesToView.get(pictureIndex);
    }
    int orientation = 1; // default orientation, used if there is no 'Orientation' in the meta data.
    
    PhotoFileMetaDataHandler photoFileMetaDataHandler;
    try {
      photoFileMetaDataHandler = new PhotoFileMetaDataHandler(new File(fileName));
      String orientationText = photoFileMetaDataHandler.getTiffItemValue(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT, "Orientation");
      if (orientationText != null) {
        orientation = Integer.valueOf(orientationText);
      }
      LOGGER.severe("orientation: " + orientation);
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
    
    LOGGER.severe("imageWidth=" + getImageViewImageHorizontalSize(imageView) + ", imageHeight=" + getImageViewImageVerticalSize(imageView));

    imageToViewScaleFactor = calculateImageToViewScaleFactor(image, stackPane);
    LOGGER.severe("imageToViewScaleFactor: " + imageToViewScaleFactor);
    
    Dimension2D imageViewDimensions = calculateImageViewDimensions(imageView, imageToViewScaleFactor);
    LOGGER.severe("imageViewDimensions: " + imageViewDimensions.getWidth() + ", " + imageViewDimensions.getHeight());
    imageView.setFitWidth(imageViewDimensions.getWidth());
    imageView.setFitHeight(imageViewDimensions.getHeight());
    
    Dimension2D viewportDimensions = calculateViewportDimensions(imageView);
    Rectangle2D viewportRectangle = new Rectangle2D(0.0, 0.0, viewportDimensions.getWidth(), viewportDimensions.getHeight());
    LOGGER.severe("viewport: " + viewportRectangle.getMinX() + ", " + viewportRectangle.getMinY() + ", " + viewportRectangle.getWidth() + ", " + viewportRectangle.getHeight());
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
   * @param image the Image for which the factor is to be calculated.
   * @param region the Region in which the <code>image</code> shall fit.
   * @return the scale factor to be applied to the <code>image</code> for the best fit in the <code>region</code>.
   */
  private double calculateImageToViewScaleFactor(Image image, Region region) {
    double horizontalImageScreenRatio =  region.getWidth() / getImageViewImageHorizontalSize(imageView);
    double verticalImageScreenRatio =  region.getHeight() / getImageViewImageVerticalSize(imageView);
    
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
      LOGGER.severe("Moving mouse to: " + pointOnScreen.getX() + ", " + pointOnScreen.getY());
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
      if (stackPane.getChildren().size() > 1) {
        // It's already there, no action
      } else {
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
      } else {
        // It's not there, no action
      }
    }
  }
  
  /**
   * Calculate the width and height of the viewport rectangle.
   * <p>
   * The size is related to the {@code zoomLevel}.
   * 
   * @param imageView the ImageView
   * 
   */
  private Dimension2D calculateViewportDimensions(ImageView imageView) {
    double viewportRectangleWidth = imageView.getImage().getWidth() / zoomLevel;
    double viewportRectangleHeight = imageView.getImage().getHeight() / zoomLevel;
    
    return new Dimension2D(viewportRectangleWidth, viewportRectangleHeight);
  }
  
  /**
   * Calculate the dimensions of an ImageView to show an Image with a specific scale factor.
   * <p>
   * The width and height of the ImageView are the width and height of the Image, multiplied by the scale factor.
   * 
   * @param image the Image to be displayed in an ImageView
   * @param imageToViewScaleFactor the scale factor that will be applied to the Image.
   * @return the dimensions for an ImageView to show the <code>image</code> with the <code>imageToViewScaleFactor</code>.
   */
  private Dimension2D calculateImageViewDimensions(ImageView imageView, double imageToViewScaleFactor) {
    // ImageView width and Image width are always in the same direction (also in case of rotation), as the complete ImageView is rotated.
    double imageViewWidth =  imageView.getImage().getWidth() * imageToViewScaleFactor;
    double imageViewHeight =  imageView.getImage().getHeight() * imageToViewScaleFactor;
    Dimension2D imageViewDimensions = new Dimension2D(imageViewWidth, imageViewHeight);
    LOGGER.info("<= " + imageViewDimensions.getWidth() + ", " + imageViewDimensions.getHeight());
    
    return imageViewDimensions;
  }
  
  /**
   * Update the offsets of a viewport, so the imageView is completely filled.
   * 
   * @param viewport The viewport to be checked.
   * @return the {@code viewport} if it didn't have to be updated, or a new {@code Rectangle2D} in case the offsets had to be adapted.
   */
  private Rectangle2D updateOffsetsToAvoidWhiteSpace(Rectangle2D viewport) {
    boolean updated = false;
    
    double viewportRectangleXOffset = viewport.getMinX();
    double viewportRectangleYOffset = viewport.getMinY();
    double viewportRectangleWidth = viewport.getWidth();
    double viewportRectangleHeight = viewport.getHeight();
    
    double rotation = getImageViewImageRotation(imageView);
    
    // If any part of the viewport falls outside of the Image, this will lead to a black area on the screen.
    if (rotation == 90.0  ||  rotation == -90.0) {
      if (viewportRectangleXOffset < 0) {
        viewportRectangleXOffset = 0;
        updated = true;
        LOGGER.severe("Shifting up, viewportRectangleXOffset set to: " + viewportRectangleXOffset);
      }
      if (viewportRectangleXOffset + viewportRectangleWidth > imageView.getImage().getWidth()) {
        viewportRectangleXOffset = imageView.getImage().getWidth() - viewportRectangleWidth;
        updated = true;
        LOGGER.severe("Shifting down, xviewportRectangleXOffset set to: " + viewportRectangleXOffset);
      }
      if (viewportRectangleYOffset < 0) {
        viewportRectangleYOffset = 0;
        updated = true;
        LOGGER.severe("Shifting left, viewportRectangleYOffset set to: " + viewportRectangleYOffset);
      }
      if (viewportRectangleYOffset + viewportRectangleHeight > imageView.getImage().getHeight()) {
        viewportRectangleYOffset = imageView.getImage().getHeight() - viewportRectangleHeight;
        updated = true;
        LOGGER.severe("Shifting right, viewportRectangleYOffset set to: " + viewportRectangleYOffset);
      }
    } else {
      if (viewportRectangleXOffset < 0) {
        viewportRectangleXOffset = 0;
        updated = true;
        LOGGER.severe("Shifting left, viewportRectangleXOffset set to: " + viewportRectangleXOffset);
      }
      if (viewportRectangleXOffset + viewportRectangleWidth > imageView.getImage().getWidth()) {
        viewportRectangleXOffset = imageView.getImage().getWidth() - viewportRectangleWidth;
        updated = true;
        LOGGER.severe("Shifting right, xviewportRectangleXOffset set to: " + viewportRectangleXOffset);
      }
      if (viewportRectangleYOffset < 0) {
        viewportRectangleYOffset = 0;
        updated = true;
        LOGGER.severe("Shifting up, viewportRectangleYOffset set to: " + viewportRectangleYOffset);
      }
      if (viewportRectangleYOffset + viewportRectangleHeight > imageView.getImage().getHeight()) {
        viewportRectangleYOffset = imageView.getImage().getHeight() - viewportRectangleHeight;
        updated = true;
        LOGGER.severe("Shifting down, viewportRectangleYOffset set to: " + viewportRectangleYOffset);
      }
    }
    
    
    
    if (updated) {
      return new Rectangle2D(viewportRectangleXOffset, viewportRectangleYOffset, viewportRectangleWidth, viewportRectangleHeight);
    } else {
      return viewport;
    }
  }
  
  /**
   * Get the horizontal size of the Image shown in an ImageView.
   * <p>
   * Note: this method only works for rotations of 0, 90, 180 and 270 degrees.
   * For rotations of 90 and 270 degrees, the horizontal size is the height of the Image,
   * else it is the width of the image.
   * 
   * @param imageView the ImageView
   * @return The horizontal size of the Image shown in the {@code imageView}.
   */
  private double getImageViewImageHorizontalSize(ImageView imageView) {
    double rotation = getImageViewImageRotation(imageView);
    
    if (rotation == 90.0  ||  rotation == 270.0) {
      return imageView.getImage().getHeight();
    } else {
      return imageView.getImage().getWidth();
    }
  }
  
  /**
   * Get the vertical size of the Image shown in an ImageView.
   * <p>
   * Note: this method only works for rotations of 0, 90, 180 and 270 degrees.
   * For rotations of 90 and 270 degrees, the vertical size is the width of the Image,
   * else it is the height of the image.
   * 
   * @param imageView the ImageView
   * @return The vertical size of the Image shown in the {@code imageView}.
   */
  private double getImageViewImageVerticalSize(ImageView imageView) {
    double rotation = getImageViewImageRotation(imageView);
    
    if (rotation == 90.0  ||  rotation == -90.0) {
      return imageView.getImage().getWidth();
    } else {
      return imageView.getImage().getHeight();
    }
    
  }
  
  /**
   * Get the rotation in degrees of the Image shown in an ImageView.
   * 
   * @param imageView the ImageView
   * @return The rotation of the Image shown in the {@code imageView}.
   */
  private double getImageViewImageRotation(ImageView imageView) {
    return imageView.rotateProperty().getValue();
  }
  
  /**
   * Translate the coordinates within an ImageView to the coordinates within the Image shown in the ImageView.
   * <p>
   * Note: the result is based on the current viewport on the ImageView.
   * 
   * @param imageView the ImageView.
   * @param imageViewX the X-coordinate within the {@code imageView}.
   * @param imageViewY the Y-coordinate within the {@code imageView}.
   * @return the coordinates within the Image shown in the {@code imageView}.
   */
  private Point2D imageViewCoordinatesToImageCoordinates(ImageView imageView, double imageViewX, double imageViewY) {
    Rectangle2D viewport = imageView.getViewport();
    
    double imageX;
    double imageY;
    double zoomFactor = getImageViewImageHorizontalSize(imageView) / (viewport.getMaxX() - viewport.getMinX());
    double imageToViewScaleFactor = imageView.getFitWidth() / getImageViewImageHorizontalSize(imageView);
    
    double rotation = getImageViewImageRotation(imageView);
    
    if (rotation == 90.0  ||  rotation == -90.0) {
      imageX = viewport.getMinY() + imageViewY / imageToViewScaleFactor / zoomFactor;
      imageY = viewport.getMinX() + imageViewX / imageToViewScaleFactor / zoomFactor;
    } else {
      imageX = viewport.getMinX() + imageViewX / imageToViewScaleFactor / zoomFactor;
      imageY = viewport.getMinY() + imageViewY / imageToViewScaleFactor / zoomFactor;
    }
    
    imageX = viewport.getMinX() + imageViewX / imageToViewScaleFactor / zoomFactor;
    imageY = viewport.getMinY() + imageViewY / imageToViewScaleFactor / zoomFactor;
    
    LOGGER.severe("=> imageViewX=" + imageViewX + ", imageViewY=" + imageViewY + ", viewport.getMinX()=" + viewport.getMinX() + ", imageToViewScaleFactor= " + imageToViewScaleFactor + ", zoomFactor" + zoomFactor + "   <= imageX=" + imageX + ", imageY=" + imageY);
    return new Point2D(imageX, imageY);
  }
  
  /**
   * Translate the coordinates within an Image, shown in an ImageView, to the coordinates within the ImageView.
   * <p>
   * Note: the result is based on the current viewport on the ImageView.
   * 
   * @param imageView the ImageView.
   * @param imageX the X-coordinate within the Image shown in the {@code imageView}.
   * @param imageY the Y-coordinate within the Image shown in the {@code imageView}.
   * @return the coordinates within the {@code imageView}.
   */
  private Point2D imageCoordinatesToImageViewCoordinates(ImageView imageView, double imageX, double imageY) {
    Rectangle2D viewport = imageView.getViewport();
    double zoomFactor = getImageViewImageHorizontalSize(imageView) / (viewport.getMaxX() - viewport.getMinX());
    double imageToViewScaleFactor = imageView.getFitWidth() / getImageViewImageHorizontalSize(imageView);
    double imageViewX = (imageX - viewport.getMinX()) * imageToViewScaleFactor * zoomFactor;
    double imageViewY = (imageY - viewport.getMinY()) * imageToViewScaleFactor * zoomFactor;
    
    LOGGER.severe("=> imageX=" + imageX + ", imageY=" + imageY + "   <= imageViewX=" + imageViewX + ", imageViewY=" + imageViewY);
    if ((imageViewX < 0)  ||  (imageViewX > imageView.getFitWidth() - 1)  ||
        (imageViewY < 0)  ||  (imageViewY > imageView.getFitHeight() - 1)) {
      System.out.println("<= null");
      return null;
    } else {
      return new Point2D(imageViewX, imageViewY);
    }
  }
  
  /**
   * Toggle the cursor on/off.
   */
  private void toggleCursor() {
    LOGGER.info("toggleCursor =>");
    
    if (cursorOn) {
      // Switch if off
      getScene().setCursor(noCursor);
      imageView.setCursor(noCursor);
    } else {
      // Switch cursor on
      getScene().setCursor(pointerCursor);
      imageView.setCursor(pointerCursor);
    }
    cursorOn = !cursorOn;
  }
  
  /**
   * Close this window (which may also close the application).
   */
  private void closeWindow() {
    this.close();
  }
}
