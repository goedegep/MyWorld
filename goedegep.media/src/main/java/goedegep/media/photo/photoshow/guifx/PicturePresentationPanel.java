package goedegep.media.photo.photoshow.guifx;

import java.awt.Point;
import java.io.File;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.img.ImageUtil;
import goedegep.jfx.img.ImageViewUtil;
import goedegep.util.datetime.FlexDate;
import javafx.animation.FadeTransition;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PicturePresentationPanel extends MediumPresentationPanelAbstract {
  private final static Logger LOGGER = Logger.getLogger(PicturePresentationPanel.class.getName());
  
  /**
   * Initial zoom level upon showing a new photo. 1.0 indicates no zoom; image is shown full screen. 
   */
  private final static double INITIAL_ZOOM_LEVEL = 1.0;
  
  /**
   * Maximum scale factor from Image to ImageView.
   * Scaling images more than 2 times gives a bad quality image.
   */
  private final static double MAX_SCALE_FACTOR = 2.0;
  
  /**
   * The GUI customization.
   */
  private CustomizationFx customization;
  
  /**
   * The width of the screen.
   */
  private double screenWidth;
  
  /**
   * The height of the screen.
   */
  private double screenHeight;
  
  private CursorControl cursorControl;

  /**
   * The root node of this presentation panel.
   * <p>
   * It is a <@code StackPane</@code> with a black background containing an <@code ImageView</@code> and an optional title.
   */
  private StackPane stackPane;
  
  /**
   * The <@code ImageView</@code> used to show the image.
   */
  private ImageView imageView;
  
  /**
   * The point of the Image where a drag has started.
   */
  Point2D dragStartPoint;
  
  /**
   * Zoom level.
   * 1.0 indicates no zoom; image is shown full screen.
   * Each higher value zooms in from this initial way the image is displayed.
   */
  private double zoomLevel;
  
  /**
   * Scale factor from the Image to the ImageView.
   */
  private double imageToViewScaleFactor;
    
  /**
   * VBox that overlays title/date on top of the image. Kept so we can remove it later.<br/>
   * If not null, a title is shown.
   */
  private VBox overlayBox;
  
  /**
   * Fade-in transition for the overlayBox.
   */
  private FadeTransition fadeIn = new FadeTransition(Duration.millis(3000));
  
  public PicturePresentationPanel(CustomizationFx customization, double screenWidth, double screenHeight, CursorControl cursorControl) {
    this.customization = customization;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.cursorControl = cursorControl;
  }

  @Override
  public Parent getRootNode() {
    if (stackPane == null) {
      stackPane = new StackPane();

      // Set a black background.
      BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
      Background background = new Background(backgroundFill);
      stackPane.setBackground(background);

      // Center the ImageView.
      stackPane.setAlignment(Pos.CENTER);

      imageView = new ImageView();
      imageView.setPreserveRatio(true);
      imageView.setSmooth(true);
      imageView.setCache(true);

      stackPane.getChildren().add(imageView);
      
      installImageDragging();
    }

    return stackPane;
  }

  /**
   * The image is scaled to fit the screen, but not more than MAX_SCALE_FACTOR.
   * 
   * @param fileName The file name of the image to show.
   * @param title The optional title to show as overlay.
   * @param flexDate The optional date to show as overlay (below the {@code title}).
   */
  @Override
  public void showMedium(String fileName, String title, FlexDate flexDate) {
    /*
     * In a JPEG file a rotation can be specified. An ImageView doesn't take this into account.
     * One option is to set the rotation on the ImageView.
     * However, in calculations you then have to take this rotation into account as in case of a rotation you have to swap width and height.
     * Another option is to rotate the image itself before setting it on the ImageView. This is what ImageUtil.loadImage() does.
     */
    Image image = ImageUtil.loadImage(new File(fileName));
    LOGGER.severe("image size: " + image.getWidth() + " x " + image.getHeight());
    
    imageView.setViewport(null); // Reset any previous viewport.
    imageView.setImage(image);
    
    zoomLevel = INITIAL_ZOOM_LEVEL;
    
    imageToViewScaleFactor = ImageViewUtil.calculateImageToViewScaleFactor(imageView, screenWidth, screenHeight);
    LOGGER.severe("imageToViewScaleFactor: " + imageToViewScaleFactor);
    imageToViewScaleFactor = Math.min(imageToViewScaleFactor, MAX_SCALE_FACTOR);
    LOGGER.severe("maximized imageToViewScaleFactor: " + imageToViewScaleFactor);
    
    Dimension2D imageViewDimensions = ImageViewUtil.calculateImageViewDimensions(imageView, imageToViewScaleFactor);
    LOGGER.severe("imageViewDimensions: " + imageViewDimensions.getWidth() + ", " + imageViewDimensions.getHeight());
    
    // The actual zooming is done by setting the dimensions of the imageView.
    imageView.setFitWidth(imageViewDimensions.getWidth());
    imageView.setFitHeight(imageViewDimensions.getHeight());
    
    // TODO Why is this needed? Seems it isn't
//    Dimension2D viewportDimensions = ImageViewUtil.calculateViewportDimensions(imageView, zoomLevel);
//    LOGGER.severe("viewportDimensions: " + viewportDimensions.getWidth() + ", " + viewportDimensions.getHeight());
//    Rectangle2D viewportRectangle = new Rectangle2D(0.0, 0.0, viewportDimensions.getWidth(), viewportDimensions.getHeight());
//    LOGGER.severe("viewportRectangle: " + viewportRectangle.getMinX() + ", " + viewportRectangle.getMinY() + ", " + viewportRectangle.getWidth() + ", " + viewportRectangle.getHeight());
//    imageView.setViewport(viewportRectangle);
    
    if (title != null  || flexDate != null) {
      showTitle(title, flexDate);
    }
  }
  
  /**
   * Show the title and/or date overlay.
   * 
   * @param title The optional title to show as overlay.
   * @param flexDate The optional date to show as overlay (below the {@code title}).
   */
  private void showTitle(String title, FlexDate flexDate) {
    overlayBox = customization.getComponentFactoryFx().createVBox();
    overlayBox.setBackground(Background.EMPTY);

    // Center the labels inside the vBox and center the vBox on the stackPane
    overlayBox.setAlignment(Pos.CENTER);
    StackPane.setAlignment(overlayBox, Pos.CENTER);

    if (title != null) {
      Text titleLabel = createLabelText(title);        
      overlayBox.getChildren().add(titleLabel);
    }

    if (flexDate != null) {
      Text dateLabel = createLabelText(FDF.format(flexDate));
      overlayBox.getChildren().add(dateLabel);
    }

    fadeIn.setNode(overlayBox);
    fadeIn.setFromValue(0.0);
    fadeIn.setToValue(1.0);
    fadeIn.setCycleCount(1);
    fadeIn.setAutoReverse(false);
    fadeIn.playFromStart();

    stackPane.getChildren().add(overlayBox);
  }

  @Override
  public boolean hasTitle() {
    return overlayBox != null;
  }

  @Override
  public void removeTitle() {
    stackPane.getChildren().remove(overlayBox);
    overlayBox.getChildren().clear();
    overlayBox = null;
  }

  @Override
  public Point2D zoomInOnCursorPosition(double zoomLevelIncrease, boolean cursorOn) {
    LOGGER.severe("Zooming in on cursor position, cursorOn=" + cursorOn + ", zoomLevelIncrease=" + zoomLevelIncrease);
    Point2D zoomCoordinatesInImageView = null;
    
    if (cursorOn) {
      // We can only get the mouse location relative to the Screen.
      Point mouseLocationOnScreen = java.awt.MouseInfo.getPointerInfo().getLocation();

      // Convert to coordinates within the imageView
      zoomCoordinatesInImageView = imageView.screenToLocal(mouseLocationOnScreen.x, mouseLocationOnScreen.y);
      // TODO handle mouse outside the imageView.
    } else {
      // Note: center of the imageView is center of the stackPane.
      zoomCoordinatesInImageView = new Point2D(stackPane.getWidth() / 2, stackPane.getHeight() / 2);
    }
    LOGGER.severe("zoomCoordinatesInImageView: " + zoomCoordinatesInImageView.getX() + ", " + zoomCoordinatesInImageView.getY());
    
    // Calculate zoom point
    Point2D imageZoomPoint = ImageViewUtil.imageViewCoordinatesToImageCoordinates(imageView, zoomCoordinatesInImageView.getX(), zoomCoordinatesInImageView.getY());

    zoomLevel += zoomLevelIncrease;
    if (zoomLevel > 20) {
      zoomLevel = 20;
    }
    LOGGER.severe("New zoom level: " + zoomLevel);
        
    // Calculate new viewport parameters.
    Dimension2D viewportDimensions = ImageViewUtil.calculateViewportDimensions(imageView, zoomLevel);
    LOGGER.severe("New viewport dimensions: " + viewportDimensions.getWidth() + ", " + viewportDimensions.getHeight());
    double viewportRectangleXOffset = imageZoomPoint.getX() - viewportDimensions.getWidth() / 2;
    double viewportRectangleYOffset = imageZoomPoint.getY() - viewportDimensions.getHeight() / 2;
    LOGGER.severe("New viewport offsets: " + viewportRectangleXOffset + " x " + viewportRectangleYOffset);
    Rectangle2D newViewport = new Rectangle2D(viewportRectangleXOffset, viewportRectangleYOffset, viewportDimensions.getWidth(), viewportDimensions.getHeight());
    imageView.setViewport(newViewport);
    
    // Calculate new cursor position.
    Point2D newMouseCursorLocation = ImageViewUtil.imageCoordinatesToImageViewCoordinates(imageView, imageZoomPoint.getX(), imageZoomPoint.getY());
    
    newMouseCursorLocation = imageView.localToScreen(newMouseCursorLocation.getX(), newMouseCursorLocation.getY());

    return newMouseCursorLocation;
  }
  
  @Override
  public Point2D zoomOutFromCenter(double zoomLevelDecrease) {
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
    
    newMouseCursorLocation = imageView.localToScreen(newMouseCursorLocation.getX(), newMouseCursorLocation.getY());
    
    return newMouseCursorLocation;
  }
  
  /**
   * Install mouse handling for dragging the image around.
   */
  private void installImageDragging() {
    /*
     *  Setup dragging the (zoomed) image around with the mouse.
     */
    
    // Mouse pressed is the start of the drag. Store the {@code dragStartPoint} and change the cursor to the closed hand.
    imageView.setOnMousePressed(mouseEvent -> {
      double dragStartX = mouseEvent.getSceneX();
      double dragStartY = mouseEvent.getSceneY();
      dragStartPoint = ImageViewUtil.imageViewCoordinatesToImageCoordinates(imageView, dragStartX, dragStartY);
      
      // Dragging cursor
      cursorControl.setDraggingCursor();
    });
    
    // Mouse released is end of the drag. Just restore the cursor.
    imageView.setOnMouseReleased(_ -> {
      cursorControl.restoreCursor();
    });
    
    // Mouse dragged means move the image. Set a viewport.
    imageView.setOnMouseDragged(e -> {
      double dragToX = e.getSceneX();
      double dragToY = e.getSceneY();
      Point2D dragPoint = ImageViewUtil.imageViewCoordinatesToImageCoordinates(imageView, dragToX, dragToY);
      double dragX = -(dragPoint.getX() - dragStartPoint.getX());
      double dragY = -(dragPoint.getY() - dragStartPoint.getY());
      Rectangle2D currentViewport = imageView.getViewport();
      
      if (currentViewport == null) {
        currentViewport = new Rectangle2D(0, 0, screenWidth, screenHeight);
      }
      
      Rectangle2D newViewport = new Rectangle2D(currentViewport.getMinX() + dragX, currentViewport.getMinY() + dragY, currentViewport.getWidth(), currentViewport.getHeight());
      newViewport = ImageViewUtil.updateOffsetsToAvoidWhiteSpace(imageView, newViewport);
      imageView.setViewport(newViewport);
    });
  }

  @Override
  public void moveImageRight(double percentage) {
    Rectangle2D currentViewport = imageView.getViewport();
    
    // If there is no viewport, the image is not zoomed, so we cannot move it.
    if (currentViewport == null) {
      return;
    }
    
    // Moving to the right is done via a negative x-offset
    updateViewportOffsets(imageView, -(currentViewport.getWidth() * percentage / 100), 0);
  }

  @Override
  public void moveImageLeft(double percentage) {
    Rectangle2D currentViewport = imageView.getViewport();
    
    // If there is no viewport, the image is not zoomed, so we cannot move it.
    if (currentViewport == null) {
      return;
    }
    
    // Moving to the left is done via a positive x-offset
    updateViewportOffsets(imageView, currentViewport.getWidth() * percentage / 100, 0);
  }

  @Override
  public void moveImageUp(double percentage) {
    Rectangle2D currentViewport = imageView.getViewport();
    
    // If there is no viewport, the image is not zoomed, so we cannot move it.
    if (currentViewport == null) {
      return;
    }
    
    // Moving up is done via a positive y-offset
    updateViewportOffsets(imageView, 0, currentViewport.getHeight() * percentage / 100);
  }

  @Override
  public void moveImageDown(double percentage) {
    Rectangle2D currentViewport = imageView.getViewport();
    
    // If there is no viewport, the image is not zoomed, so we cannot move it.
    if (currentViewport == null) {
      return;
    }
    
    // Moving down is done via a negative y-offset
    updateViewportOffsets(imageView, 0, -(currentViewport.getHeight() * percentage) / 100);
  }

  @Override
  public void moveImageRight(int pixels) {
    Rectangle2D currentViewport = imageView.getViewport();
    
    // If there is no viewport, the image is not zoomed, so we cannot move it.
    if (currentViewport == null) {
      return;
    }
    
    // Moving to the right is done via a negative x-offset
    updateViewportOffsets(imageView, pixels, 0);
  }

  @Override
  public void moveImageLeft(int pixels) {
    Rectangle2D currentViewport = imageView.getViewport();
    
    // If there is no viewport, the image is not zoomed, so we cannot move it.
    if (currentViewport == null) {
      return;
    }
    
    // Moving to the left is done via a positive x-offset
    updateViewportOffsets(imageView, -pixels, 0);
  }

  @Override
  public void moveImageUp(int pixels) {
    Rectangle2D currentViewport = imageView.getViewport();
    
    // If there is no viewport, the image is not zoomed, so we cannot move it.
    if (currentViewport == null) {
      return;
    }
    
    // Moving up is done via a positive y-offset
    updateViewportOffsets(imageView, 0, pixels);
  }

  @Override
  public void moveImageDown(int pixels) {
    Rectangle2D currentViewport = imageView.getViewport();
    
    // If there is no viewport, the image is not zoomed, so we cannot move it.
    if (currentViewport == null) {
      return;
    }
    
    // Moving down is done via a negative y-offset
    updateViewportOffsets(imageView, 0, -pixels);
  }
  
  /**
   * Update the viewport offsets of the viewport of the imageView.
   * <p>
   * After applying the delta's to the offsets, the offsets are updated to avoid white space.
   * 
   * @param deltaX the amount to add to the x-offset.
   * @param deltaY the amount to add to the y-offset.
   */
  private void updateViewportOffsets(ImageView imageView, double deltaX, double deltaY) {
    Rectangle2D currentViewport = imageView.getViewport();
    
    // If there is no viewport, the image is not zoomed, so we cannot move it.
    if (currentViewport == null) {
      return;
    }
    
    Rectangle2D newViewport = new Rectangle2D(currentViewport.getMinX() + deltaX, currentViewport.getMinY() + deltaY, currentViewport.getWidth(), currentViewport.getHeight());
    imageView.setViewport(newViewport);
  }
}
