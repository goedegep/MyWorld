package goedegep.jfx.objectcontrols;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
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
public class ObjectControlImageFile implements ObjectControl<String> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlImageFile.class.getName());
    
  private ComponentFactoryFx componentFactory;
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  private String filename;
  private File initialDirectory;
  private StackPane stackPane;
  private ImageView imageView;
  private Label label;
  private Stage largePictureStage = null;
  
  
  /**
   * Indication of whether the control is optional (if true) or mandatory.
   */
  private boolean optional;
//  private BooleanProperty ocOptionalProperty = new SimpleBooleanProperty(false);
//  
//  /**
//   * Indication of whether the control is filled-in or not.
//   */
//  private BooleanProperty ocFilledInProperty = new SimpleBooleanProperty(true);
//  
//  /**
//   * Indication of whether the control has a valid value or not.
//   */
//  private BooleanProperty ocValidProperty = new SimpleBooleanProperty(true);
//  
//  /**
//   * The current value.
//   */
//  private ObjectProperty<String> ocValueProperty = new SimpleObjectProperty<>();
  
  

  /**
   * Constructor
   * @param customization the GUI customization.
   */
  public ObjectControlImageFile(CustomizationFx customization) {
    optional = false;
//    ocOptionalProperty.set(false);  // If this control is used, it's never optional.
    
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
    stackPane.getChildren().add(label);
    StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
    
//    node.getChildren().remove(changeButton);
  }

//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public BooleanProperty ocOptionalProperty() {
//    return ocOptionalProperty;
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public BooleanProperty ocValidProperty() {
//    return ocValidProperty;
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public BooleanProperty ocFilledInProperty() {
//    return ocFilledInProperty;
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public ObjectProperty<String> ocValueProperty() {
//    return ocValueProperty;
//  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ocSetValue(String filename) {
    this.filename = filename;
    
    Image image = new Image("file:" + filename, 0.0, 200.0, true, true);
    imageView.setImage(image);
    
    File file = new File(filename);
    label.setText(file.getName());
    Color color = Color.color(1.0, 1.0, 1.0);
    label.setBackground(new Background(new BackgroundFill(color, new CornerRadii(3), new Insets(0))));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean ociDetermineFilledIn() {
    // There is either a filename or not.
    return filename != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String ociDetermineValue() {
    return filename;
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

  /**
   * {@inheritDoc}
   * There is no formatting, so just return the filename.
   */
  @Override
  public String ocGetObjectValueAsFormattedText() {
    return filename;
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
    FileChooser fileChooser = componentFactory.createFileChooser("Select front image");
    if (initialDirectory != null) {
      fileChooser.setInitialDirectory(initialDirectory);
    }
    File file = fileChooser.showOpenDialog(null);
    if (file != null) {
      filename = file.getAbsolutePath();
      ocSetValue(filename);
    }
  }

//  public String getFilename() {
//    return filename;
//  }
  

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
    
    Image image = new Image("file:" + filename);
    ImageView largePicture = new ImageView(image);
    
    largePictureStage = new Stage();
    largePictureStage.initStyle(StageStyle.UNDECORATED);
    largePictureStage.setX(largePictureStageX);
    BorderPane pane = new BorderPane();
    pane.setCenter(largePicture);
    largePictureStage.setScene(new Scene(pane));
    largePictureStage.show();
  }

  @Override
  public String getId() {
    return stackPane.getId();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<InvalidationListener> ociGetInvalidationListeners() {
    return invalidationListeners;
  }

  public StackPane getStackPane() {
    return stackPane;
  }

  @Override
  public boolean ocIsOptional() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean ocIsFilledIn() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean ocIsValid() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String ocGetValue() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void ociSetValue(String value) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void ociSetValid(boolean valid) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void ociSetFilledIn(boolean filledIn) {
    // TODO Auto-generated method stub
    
  }
}
