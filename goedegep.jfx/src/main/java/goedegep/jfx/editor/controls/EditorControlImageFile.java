package goedegep.jfx.editor.controls;

import java.io.File;
import java.util.logging.Logger;

import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditorControlTemplate;
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
public class EditorControlImageFile extends EditorControlTemplate<File> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(EditorControlImageFile.class.getName());
  
  /**
   * The image file selected by the user via the file chooser.
   * <p>
   * We have to save this value, as it is returned by the open dialog of the file chooser and we have to provide it in {@code TODO} and {@code TODO}.
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
  
  /**
   * Indication of whether error feedback is needed.
   */
  private boolean useErrorFeedback = false;
  

  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param isOptional if true, the value provided by this control is optional.
   */
  private EditorControlImageFile(Builder builder) {
    super(builder.customization, builder.optional);
    
    setId(builder.id);
    setLabelBaseText(builder.labelBaseText);
  }

  @Override
  public void createControls() {
    stackPane = new StackPane();
    imageView = new ImageView();
    imageView.setFitHeight(200.0);
    imageView.setPreserveRatio(true);
    imageView.setOnMouseEntered(e -> showLargePicture(e));
    imageView.setOnMouseExited(_ -> {
      if (largePictureStage != null) {
        largePictureStage.close();
        largePictureStage = null;
      }
    });
    stackPane.getChildren().add(imageView);
    
    Button changeButton = componentFactory.createButton("Change", "Click to select a different picture via a file chooser");
    changeButton.setOnAction(_ -> selectNewFile());
    stackPane.getChildren().add(changeButton);              
    StackPane.setAlignment(changeButton, Pos.TOP_LEFT);
    
    label = componentFactory.createLabel(errorText);
    label.setBackground(new Background(new BackgroundFill(customization.getLook().getLabelBackgroundColor(), new CornerRadii(3), new Insets(0))));
    stackPane.getChildren().add(label);
    StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
  }

  @Override
  protected void fillControlsWithDefaultValues() {
    updateControl(null);    
  }

  @Override
  protected void setErrorFeedback(boolean valid) {
    useErrorFeedback = !valid;
  }

  @Override
  protected void updateNonSourceControls(Object source) {
    if (source == null) {
      updateControl(getValue());
    }
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

  @Override
  protected boolean determineFilledIn(Object source) {
    // There is either a filename or not, we don't have to check the source.
    return file != null;
  }

  @Override
  protected File determineValue(Object source) {
    return file;
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
  
  /**
   * Launch a FileChooser to let the user select a file.
   */
  public void selectNewFile() {
    FileChooser fileChooser = getFileChooser();
    file = fileChooser.showOpenDialog(null);
    if (file != null) {
      useErrorFeedback = false;
      updateControl(file);
      handleNewUserInput(fileChooser);
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
  
  public String getAbsolutePath() {
    if (getValue() != null) {
      return getValue().getAbsolutePath();
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   * There is no formatting, so no action.
   */
  @Override
  protected void redrawValue() {
  }

  
  
  public static class Builder {
    
    /**
     * The unique id of the ObjectControl.
     */
    private String id;
    
    /**
     * The GUI customization.
     */
    private CustomizationFx customization;
    
    /**
     * Indication of whether a value is optional or not.
     */
    private boolean optional;
    
    /**
     * Label base text.
     */
    private String labelBaseText;
    
    /**
     * Constructor with mandatory arguments.
     * 
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public Builder(String id) {
      this.id = id;
    }
    
    /**
     * Set the GUI customization.
     * 
     * @param customization the GUI customization.
     * @return this
     */
    public Builder setCustomization(CustomizationFx customization) {
      this.customization = customization;
      
      return this;
    }
    
    /**
     * Set the base text for the label.
     * 
     * @param labelBaseText the base text for the label.
     * @return this
     */
    public Builder setLabelBaseText(String labelBaseText) {
      this.labelBaseText = labelBaseText;
      
      return this;
    }
    
    /**
     * Set whether a value is optional or not.
     * 
     * @param optional true indicates that a value is optional, else it is mandatory.
     * @return this
     */
    public Builder setOptional(boolean optional) {
      this.optional = optional;
      
      return this;
    }

    /**
     * Create the control.
     * 
     * @return the newly created control.
     */
    public EditorControlImageFile build() {
      EditorControlImageFile editorControlImageFile = new EditorControlImageFile(this);
      editorControlImageFile.performInitialization();
      
      return editorControlImageFile;
    }
    
  }
}
