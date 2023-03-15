package goedegep.jfx.controls;

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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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

public class ObjectControlImageFile implements ObjectControl<String> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlImageFile.class.getName());
    
  private ComponentFactoryFx componentFactory;
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
  private String filename;
  private File initialDirectory;
  private StackPane stackPane;
  private ImageView imageView;
  private Label label;
  private Stage largePictureStage = null;

  public ObjectControlImageFile(CustomizationFx customization) {
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
    changeButton.setOnAction(actionEvent -> {
      changeFile();
    });
    stackPane.getChildren().add(changeButton);              
    StackPane.setAlignment(changeButton, Pos.TOP_LEFT);
    
    label = new Label();
    stackPane.getChildren().add(label);
    StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
    
//    node.getChildren().remove(changeButton);
  }

  public void setInitialDirectory(File initialDirectory) {
    this.initialDirectory = initialDirectory;
  }
  
  public void changeFile() {
    FileChooser fileChooser = componentFactory.createFileChooser("Select front image");
    if (initialDirectory != null) {
      fileChooser.setInitialDirectory(initialDirectory);
    }
    File file = fileChooser.showOpenDialog(null);
    if (file != null) {
      filename = file.getAbsolutePath();
      setFilename(filename);
    }
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
    
    Image image = new Image("file:" + filename, 0.0, 200.0, true, true);
    imageView.setImage(image);
    
    File file = new File(filename);
    label.setText(file.getName());
    Color color = Color.color(1.0, 1.0, 1.0);
    label.setBackground(new Background(new BackgroundFill(color, new CornerRadii(3), new Insets(0))));
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
  public boolean isOptional() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public BooleanProperty isFilledIn() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean getIsFilledIn() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public BooleanProperty isValid() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean getIsValid(StringBuilder errorMessageBuffer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String getObjectValue() throws ParseException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setObjectValue(String objectValue) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public ObjectProperty<String> objectValue() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addListener(InvalidationListener listener) {
    invalidationListeners.add(listener);    
  }

  @Override
  public void removeListener(InvalidationListener listener) {
    invalidationListeners.remove(listener);    
  }

  
  /**
   * Notify the <code>invalidationListeners</code> that something has changed.
   */
  private void notifyListeners() {
    LOGGER.severe("=>");
    for (InvalidationListener invalidationListener: invalidationListeners) {
      LOGGER.severe("Notifying listener: " + invalidationListener);
      invalidationListener.invalidated(this);
    }
  }

  public StackPane getStackPane() {
    return stackPane;
  }
}
