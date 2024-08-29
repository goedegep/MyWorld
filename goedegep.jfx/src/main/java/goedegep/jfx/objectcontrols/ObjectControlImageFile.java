package goedegep.jfx.objectcontrols;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.resources.ImageSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class provides an ObjectControl for selecting an image file.
 *
 */
public class ObjectControlImageFile extends ObjectControlTemplate<File> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlImageFile.class.getName());
    
  /**
   * The GUI customization.
   */
  private CustomizationFx customization;
  
  /**
   * Factory for creating GUI components.
   */
  private ComponentFactoryFx componentFactory;
  
  /**
   * The image file selected by the user via the file chooser.
   * <p>
   * We have to save this value, as it is returned by the open dialog of the file chooser and we have to provide it in {@code ociDetermineFilledIn} and {@code ociDetermineValue}.
   */
  private File file;
  
  /**
   * The optional initial directory to be set on the file chooser.
   */
  private File initialDirectory;
  
  /**
   * The GUI control
   * <p>
   * This is a {@code StackPane} so that an application can easily add other items like e.g. a 'delete' button.
   */
  private StackPane stackPane;
  
  /**
   * The {@code ImageView} showing the image.
   */
  private ImageView imageView;
  
  /**
   * A {@code Label} that is on top of the {@code imageView} showing the image file name.
   */
  private Label label;
  
  /**
   * A {@link Stage} showing a full size version of the image. This is done upon hoovering over the control.
   */
  private Stage largePictureStage = null;
  
  private boolean useErrorFeedback = false;
  

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param isOptional if true, the value provided by this control is optional.
   */
  public ObjectControlImageFile(CustomizationFx customization, boolean isOptional) {
    super(isOptional);
    
    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    
    stackPane = new StackPane();
    imageView = new ImageView();
    imageView.setFitHeight(200.0);
    imageView.setPreserveRatio(true);
    imageView.setOnMouseEntered(e -> showLargePicture(e));
    imageView.setOnMouseExited(e -> {
      if (largePictureStage != null) {
        largePictureStage.close();
        largePictureStage = null;
      }
    });
    stackPane.getChildren().add(imageView);
    
    Button changeButton = componentFactory.createButton("Change", "Click to select a different picture via a file chooser");
    changeButton.setOnAction(actionEvent -> selectNewFile());
    stackPane.getChildren().add(changeButton);              
    StackPane.setAlignment(changeButton, Pos.TOP_LEFT);
    
    label = componentFactory.createLabel(errorText);
    label.setBackground(new Background(new BackgroundFill(customization.getLook().getLabelBackgroundColor(), new CornerRadii(3), new Insets(0))));
    stackPane.getChildren().add(label);
    StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StackPane getControl() {
    return stackPane;
  }

  /**
   * Update the control for a new value.
   * <p>
   * For this object control this is needed, because the presentation control (the {@code stackPane}) differs from the selection control (a {@code FileChooser}).
   * 
   * @param file the new image file name.
   */
  private void updateControl(File file) {
    if (useErrorFeedback) {
      AppResourcesFx appResources = customization.getResources();
      Image errorImage = appResources.getErrorImage(ImageSize.SIZE_2);
      imageView.setImage(errorImage);
    } else {
      if (file != null) {
        Image image = new Image("file:" + file.getAbsolutePath(), 0.0, 200.0, true, true);
        imageView.setImage(image);
      } else {
        imageView.setImage(null);
      }
    }
    
    if (useErrorFeedback) {
      label.setStyle("-fx-text-fill: red;");
    } else {
      label.setStyle("-fx-text-fill: black;");
    }
    
    if (file != null) {
      label.setText(file.getName());
    } else {
      label.setText(null);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ociDetermineFilledIn(Object source) {
    // There is either a filename or not, we don't have to check the source.
    return file != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File ociDetermineValue(Object source) {
    return file;
  }

  /**
   * {@inheritDoc}
   * 
   * An error occurs if the {@code File} set via {@code setValue()} isn't accessible.
   * In this case we show the error icon.
   */
  @Override
  public void ociSetErrorFeedback(boolean valid) {
      useErrorFeedback = !valid;
  }

  /**
   * {@inheritDoc}
   * There is no formatting, so no action.
   */
  @Override
  public void ociRedrawValue() {
  }

  @Override
  protected void ociUpdateNonSourceControls(Object source) {
    if (source == null) {
      updateControl(getValue());
    }
  }

  /**
   * {@inheritDoc}
   * There is no formatting, so just return the filename.
   */
  @Override
  public String getValueAsFormattedText() {
    return getValue().getAbsolutePath();
  }

  /**
   * Set the initial directory to be used by the FileChooser.
   * 
   * @param initialDirectory the initial directory to be used by the FileChooser.
   */
  public void setInitialDirectory(File initialDirectory) {
    this.initialDirectory = initialDirectory;
  }
  
  @Override
  protected boolean ociIsValueValid(File file) {
    Path path = Paths.get(file.getAbsolutePath().toString());
    boolean result = Files.exists(path);
    LOGGER.severe("<= " + result);
    return result;
  }
  
  /**
   * Launch a FileChooser to let the user select a file.
   */
  public void selectNewFile() {
    FileChooser fileChooser = getFileChooser();
    file = fileChooser.showOpenDialog(null);
    if (file != null) {
      useErrorFeedback = false;
      updateControl(file);
      ociHandleNewUserInput(fileChooser);
    }
  }
  
  /**
   * Get a {@code FileChooser} to select the image file.
   * 
   * @return a {@code FileChooser} to select the image file.
   */
  public FileChooser getFileChooser() {
    FileChooser fileChooser = componentFactory.createFileChooser("Select image");
    if (initialDirectory != null) {
      fileChooser.setInitialDirectory(initialDirectory);
    }
    
    return fileChooser;
  }
  

  /**
   * Show a picture, full size, in a separate <code>Stage</code> (without any header or border).
   */
  private void showLargePicture(MouseEvent e) {
    // determine where to show the picture: not where the mouse is now, but to the right or left, wherever there's the most space.
    double mouseX = e.getScreenX();
    
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    double screenWidth = screenBounds.getWidth();
    
    double largePictureStageX;
    if (mouseX < screenWidth / 2) {
      largePictureStageX = screenWidth / 2 + 20;
    } else {
      largePictureStageX = 20;
    }
    
    Image image = new Image("file:" + getValue());
    ImageView largePicture = new ImageView(image);
    
    largePictureStage = new Stage();
    largePictureStage.initStyle(StageStyle.UNDECORATED);
    largePictureStage.setX(largePictureStageX);
    BorderPane pane = new BorderPane();
    pane.setCenter(largePicture);
    largePictureStage.setScene(new Scene(pane));
    largePictureStage.show();
  }
  
  public String ocGetAbsolutePath() {
    if (getValue() != null) {
      return getValue().getAbsolutePath();
    } else {
      return null;
    }
  }

}
