package goedegep.media.photo.photoshow.guifx;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.util.datetime.FlexDate;
import goedegep.util.file.FileUtils;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class shows a list of photos full screen.
 * <p>
 * As this is a full screen window, there is no menu or other controls visible. Therefore this class doesn't extend {@code jfxStage}<br/>
 * 
 * On the first photo of the show an optional title and date may be shown. This overlay disappears on the first key press.<br/>
 * After this the following key definitions apply:
 * <pre>
 * Getting help
 *   H - shows a this dialog
 *
 * Navigating through the list of photos
 *   ← - Go to previous photo
 *   → - Go to next photo
 *
 * Moving the (zoomed) image on the screen
 *   W - move the image 10% up
 *   S - move the image 10% down
 *   A - move the image 10% to the left
 *   D - move the image 10% to the right
 *   V - move the image a little up
 *   B - move the image a little down
 *   Z - move the image a little to the left
 *   X - move the image a little to the right
 *
 * Cursor on/of
 *   C - toggle the cursor on/off
 *
 * Zooming
 *   +, = - zoom in on the cursor position if shown, else on the center of the photo. Numpad '+' key and '=' key (below the '+' so you don't have to use 'Shift').
 *   -    - zoom out around the center of the image, moving the image to fill the view if needed. 
 *
 * Closing the view
 *   ESC - closes the view
 * </pre>
 */
public class PhotoWindow extends Stage implements CursorControl {
  /*
   * This class creates a Scene.
   * Each medium type has its own presentation panel. This presentation panel creates its own root node which is set as the root node of the Scene.
   */
  private final static Logger LOGGER = Logger.getLogger(PhotoWindow.class.getName());
  
  /**
   * This is a full screen application. However, you cannot debug a full screen application.
   * So when we set debug to true, we don't go full screen. Instead we use a fixed size window.
   */
  private boolean debug = false;
  
  /**
   * The GUI customization.
   */
  private CustomizationFx customization;
  
  /**
   * The component factory to create GUI components.
   */
  private ComponentFactoryFx componentFactory;
  
  /**
   * List of media file names to view.
   */
  private List<String> mediaFilesToView = null;
  
  /**
   * An optional title which will be shown on the first medium, until any key is pressed.
   */
  private String title;
  
  /**
   * An optional date which will be shown on the first medium (below the title), until any key is pressed.
   */
  private FlexDate flexDate;
  
  /**
   * Current index in {@code mediaFilesToView}
   */
  private int pictureIndex = 0;  // 17
  
  /**
   * Indicates whether the cursor is on or off.
   */
  private boolean cursorOn;
  
  /**
   * The 'no' cursor {@code Cursor}.
   */
  private Cursor noCursor = Cursor.NONE;
  
  /**
   * The special 'pointer' cursor {@code Cursor}.
   */
  private Cursor pointerCursor;
  
  /**
   * The special dragging {@code Cursor}.
   */
  private Cursor draggingCursor;

  /**
   * Indicates whether the title has been shown.
   * <p>
   * The title is only shown on the first medium shown.
   */
  private boolean titleShown = false;
  
  /**
   * The {@code MediumType} of the last shown medium.
   */
  private MediumType currentMediumType;
  
  /**
   * The presentation panel for the current medium.
   */
  private MediumPresentationPanel mediumPresentationPanel;
  
  /**
   * Map of MediumType to corresponding MediumPresentationPanel.
   */
  private Map<MediumType, MediumPresentationPanel> mediumTypeToPresentationPanelMap = new HashMap<>();
  
  /**
   * The width of the screen.
   * <p>
   * When the application starts, it shows the first medium. At this point the window isn't shown yet, so we cannot determine the screen size then.<br/>
   * Therefore we determine the screen size in the constructor and store it here for later use.
   */
  private double screenWidth;
  
  /**
   * The height of the screen.
   * <p>
   * When the application starts, it shows the first medium. At this point the window isn't shown yet, so we cannot determine the screen size then.<br/>
   * Therefore we determine the screen size in the constructor and store it here for later use.
   */
  private double screenHeight;
  
  /**
   * Constructor
   * 
   * @param mediaFilesToView list of media files to be shown
   * @param title an optional title which will be shown on the first medium, until any key is pressed.
   * @param date an optional date which will be shown on the first medium (below the title), until any key is pressed.
   */
  public PhotoWindow(CustomizationFx customization, List<String> mediaFilesToView, String title, FlexDate flexDate) {
    Objects.requireNonNull(mediaFilesToView, "mediaFilesToView may not be null");
    if (mediaFilesToView.isEmpty()) {
      throw new IllegalArgumentException("mediaFilesToView may not be empty");
    }

    this.customization = customization;
    this.mediaFilesToView = mediaFilesToView;
    this.title = title;
    this.flexDate = flexDate;

    componentFactory = customization.getComponentFactoryFx();

    Region region = new Region();
    
    if (debug) {
      region.setPrefWidth(1200);
      region.setPrefHeight(800);
      screenWidth = 1200;
      screenHeight = 800;
    } else {
      setFullScreenExitHint("");
      setFullScreen(true);
      Rectangle2D screenVisualBounds = Screen.getPrimary().getVisualBounds();
      screenWidth = screenVisualBounds.getWidth();
      screenHeight = screenVisualBounds.getHeight();
    }    

    Scene scene = new Scene(region);
    setScene(scene);
        
    // Use Key handling on the scene, instead of on the imageView. So they also work if the cursor is not on the imageView.
    // Initially the title and date (if available) are shown. In this case any key just removes this. After this normal key handling takes place.
    getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent event) {
        if (mediumPresentationPanel.hasTitle()) {
          mediumPresentationPanel.removeTitle();
        } else {
          handleKeyEvent(event);
        }
        
        event.consume();
      }

    });

    // Create the (large) pointer and dragging cursors.
    Image cursorImage = new Image(getClass().getResourceAsStream("pointerCursor.png"));
    pointerCursor = new ImageCursor(cursorImage, 0d, 0d);
    cursorImage = new Image(getClass().getResourceAsStream("draggingCursor.png"));
    draggingCursor = new ImageCursor(cursorImage, 0d, 0d);
    
    // Initially the cursor is on. So switch if off.
    cursorOn = true;
    toggleCursor();

    // Show the first image.
    updateImage();

    show();
  }
        
   /**
    * Handle a key event.
    * 
    * @param event the {@code KeyEvent} to handle.
    */
   private void handleKeyEvent(KeyEvent event) {
     LOGGER.info("Key typed: " + event.getCode());
     
     switch (event.getCode()) {

     // Right arrow: go to next image.
     case RIGHT:
       if (mediaFilesToView != null  &&  pictureIndex < mediaFilesToView.size() - 1) {
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
       
     // 'Enter' key: reload the image.
     case ENTER:
       updateImage();
       break;
      
    // 'W' key: move image 10% up.
    case W:
      mediumPresentationPanel.moveImageUp(10.0);
      break;

    // 'S' key: move image 10% down
    case S:
      mediumPresentationPanel.moveImageDown(10.0);
      break;

    // 'A' key: move image 10% left
    case A:
      mediumPresentationPanel.moveImageLeft(10.0);
      break;

    // 'D' key: move image 10% right
    case D:
      mediumPresentationPanel.moveImageRight(10.0);
      break;

    // Numpad '+' key and '=' key (below the '+' so you don't have to use 'Shift'): zoom in (on cursor if visible).
    case ADD:
    case EQUALS:
      handleZoomInOnCursorPosition();
      break;

    // Numpad '-' key and '-' key: zoom out around the center of the image, moving the image to fill the view if needed.
    case SUBTRACT:
    case MINUS:
      handleZoomOutFromCenter();
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
      mediumPresentationPanel.moveImageRight(10);
      break;
      
    // 'X' key: decrease MinX
    case X:
      mediumPresentationPanel.moveImageLeft(10);
      break;
      
    // 'V' key: increase MinY
    case V:
      mediumPresentationPanel.moveImageUp(10);
      break;
      
    // 'B' key: decrease MinY
    case B:
      mediumPresentationPanel.moveImageDown(10);
      break;
      
    // 'H' key: show help dialog
    case H:
      showHelpDialog();
      break;
      

    default:
      // no action
    }
    
  }
  
  /**
   * Show a dialog with a help text.
   */
  private void showHelpDialog() {
    String keyInfo = """
            Getting help
              H - shows a this dialog

            Navigating through the list of photos
              ←     - Go to previous photo
              →     - Go to next photo
              ENTER - reload the image

            Moving the image on the screen
              W - move the image 10% up
              S - move the image 10% down
              A - move the image 10% to the left
              D - move the image 10% to the right
              V - move the image a little up
              B - move the image a little down
              Z - move the image a little to the left
              X - move the image a little to the right

            Cursor on/of
              C - toggle the cursor on/off

            Zooming
              +, = - zoom in on the cursor position if shown, else on the center of the photo. Numpad '+' key and '=' key (below the '+' so you don't have to use 'Shift').
              -    - zoom out around the center of the image, moving the image to fill the view if needed. 

            Closing the view
              ESC - closes the view
        """;
    componentFactory.createInformationDialog("Photoshow viewer help", keyInfo, null).showAndWait(); 
  }
    
  /**
   * Zoom in on the current cursor position if the cursor is shown, else zoom in on the center of the photo.
   * <p>
   * The actual zooming is done by the MediumPresentationPanel, which may return a new cursor location.<br/>
   * If so, the mouse pointer is moved to this location.
   */
  private void handleZoomInOnCursorPosition() {
    Point2D newMouseCursorLocation = mediumPresentationPanel.zoomInOnCursorPosition(1.0, cursorOn);

    if (newMouseCursorLocation != null) {
      moveMousePointer(newMouseCursorLocation);
    }
  }
  
  /**
   * Zoom out from the center of the screen.
   * <p>
   * The actual zooming is done by the MediumPresentationPanel, which may return a new cursor location.<br/>
   * If so, the mouse pointer is moved to this location.
   */
  private void handleZoomOutFromCenter() {
    Point2D newMouseCursorLocation = mediumPresentationPanel.zoomOutFromCenter(1.0);

    if (newMouseCursorLocation != null) {
      moveMousePointer(newMouseCursorLocation);
    }
  }
    
  /**
   * Update the image upon a new {@code pictureIndex}.
   * <p>
   * An {@code 
   * The image is read from the file which is specified in the currentPhotoInfo.<br/>
   * The image is shown as large as possible, preserving the ratio.<br/>
   * The zoomLevel is reset to the INITIAL_ZOOM_LEVEL.
   */
  private void updateImage() {
    String fileName = mediaFilesToView.get(pictureIndex);
    
    // Determine the medium type and set the corresponding MediumPresentationPanel.
    MediumType mediumType = determineMediumType(fileName);
    setMediumPresentationPanel(mediumType);
    
    if (!titleShown) {
      mediumPresentationPanel.showMedium(fileName, title, flexDate);
      titleShown = true;
    } else {
      mediumPresentationPanel.showMedium(fileName, null, null);
    }       
  }
  
  /**
   * Set the MediumPresentationPanel for the specified MediumType.
   * 
   * @param mediumType the {@code MediumType}
   */
  private void setMediumPresentationPanel(MediumType mediumType) {
    if (currentMediumType != mediumType) {
      mediumPresentationPanel = mediumTypeToPresentationPanelMap.get(mediumType);
      if (mediumPresentationPanel == null) {
        switch (mediumType) {
        case NORMAL_IMAGE:
          mediumPresentationPanel = new PicturePresentationPanel(customization, screenWidth, screenHeight, this);
          break;
          
        case PHOTOSPHERE:
          mediumPresentationPanel = new PhotospherePresentationPanel();
          break;
          
        default:
          LOGGER.severe("Not implemented");
        }
        
        mediumTypeToPresentationPanelMap.put(mediumType, mediumPresentationPanel);
      }
      
      getScene().setRoot(mediumPresentationPanel.getRootNode());
      currentMediumType = mediumType;
    }
  }
  
  /**
   * Move the mouse pointer to a specified location.
   */
  private void moveMousePointer(Point2D mouseCursorLocation) {
    try {
      Robot robot = new Robot();
      robot.mouseMove((int) mouseCursorLocation.getX(), (int) mouseCursorLocation.getY());
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Determine the MediumType for the specified file name.
   * 
   * @param fileName the file name of the medium.
   * @return the {@code MediumType} of the specified file name.
   */
  private MediumType determineMediumType(String fileName) {    
    if (FileUtils.isPictureFile(fileName)) {
      String fileNameWithoutExtension = FileUtils.getFileNameWithoutExtension(fileName).toLowerCase();
      if (fileNameWithoutExtension.endsWith("photosphere")) {
        return MediumType.PHOTOSPHERE;
      } else if (fileNameWithoutExtension.endsWith("panorama")) {
        return MediumType.PANORAMA;
      } else {
        return MediumType.NORMAL_IMAGE;
      }
    } else {
      return MediumType.UNKNOWN;
    }
  }
  
  /**
   * Toggle the cursor on/off.
   */
  private void toggleCursor() {
    LOGGER.info("=>");
    
    if (cursorOn) {
      // Switch if off
      getScene().setCursor(noCursor);
    } else {
      // Switch cursor on
      getScene().setCursor(pointerCursor);
    }
    cursorOn = !cursorOn;
  }
  
  /**
   * Close this window (which may also close the application).
   */
  private void closeWindow() {
    this.close();
  }

  @Override
  public void setDraggingCursor() {
    getScene().setCursor(draggingCursor);
  }

  @Override
  public void restoreCursor() {
    if (cursorOn) {
      getScene().setCursor(pointerCursor);
    } else {
      getScene().setCursor(noCursor);
    }
  }
}

/**
 * The different medium types we can handle.
 */
enum MediumType {
  NORMAL_IMAGE,
  PANORAMA,
  PHOTOSPHERE,
  UNKNOWN
}
