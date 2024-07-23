package goedegep.jfx.objectcontrols;

import java.io.File;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class provides an ObjectControl for selecting an image file.
 *
 */
public class ObjectControlImageFile extends ObjectControlTemplate<File> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlImageFile.class.getName());
    
  private ComponentFactoryFx componentFactory;
  private File file;
  private File initialDirectory;
  private StackPane stackPane;
  private ImageView imageView;
  private Label label;
  private Stage largePictureStage = null;    
  

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param isOptional if true, the value provided by this control is optional.
   */
  public ObjectControlImageFile(CustomizationFx customization, boolean isOptional) {
    super(isOptional);
    
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
    
    Button changeButton = new Button("Change");
    changeButton.setOnAction(actionEvent -> selectNewFile());
    stackPane.getChildren().add(changeButton);              
    StackPane.setAlignment(changeButton, Pos.TOP_LEFT);
    
    label = new Label();
    Color backgroundColor = Color.color(1.0, 1.0, 1.0);
    label.setBackground(new Background(new BackgroundFill(backgroundColor, new CornerRadii(3), new Insets(0))));
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

  @Override
  public void ociSetValue(File value) {
    this.value = value;   
    
    if (value != null) {
      Image image = new Image("file:" + value.getAbsolutePath(), 0.0, 200.0, true, true);
      imageView.setImage(image);
    } else {
      imageView.setImage(null);
    }
    
    
    if (value != null) {
      label.setText(value.getName());
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
   * As the filename is always selected via a FileChooser, it's always valid. So, no action.
   */
  @Override
  public void ociSetErrorFeedback(boolean valid) {
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
    // TODO Auto-generated method stub
    
  }

  /**
   * {@inheritDoc}
   * There is no formatting, so just return the filename.
   */
  @Override
  public String getValueAsFormattedText() {
    return value.getAbsolutePath();
  }

  /**
   * Set the initial directory to be used by the FileChooser.
   * 
   * @param initialDirectory the initial directory to be used by the FileChooser.
   */
  public void setInitialDirectory(File initialDirectory) {
    this.initialDirectory = initialDirectory;
  }
  
  /**
   * Launch a FileChooser to let the user select a file.
   */
  public void selectNewFile() {
    FileChooser fileChooser = getFileChooser();
    file = fileChooser.showOpenDialog(null);
    if (file != null) {
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
    if (value != null) {
      return value.getAbsolutePath();
    } else {
      return null;
    }
  }

}
