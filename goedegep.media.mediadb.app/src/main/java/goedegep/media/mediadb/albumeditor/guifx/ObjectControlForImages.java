package goedegep.media.mediadb.albumeditor.guifx;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlAbstract;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlImageFile;
import goedegep.media.app.MediaRegistry;
import goedegep.util.string.StringUtil;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class ObjectControlForImages extends ObjectControlAbstract<List<String>> {
  private static final Logger LOGGER = Logger.getLogger(ObjectControlForImages.class.getName());
  
  private ComponentFactoryFx componentFactory;
  private CustomizationFx customization;
  
  /**
   * Main layout
   * <p>
   * On the left is a VBox with a label and an 'Add' button.
   * This is followed by an HBox (imagesHBox), containing one ObjectControlImageFile per image.
   */
  private HBox mainLayout;
  
  /**
   * An HBox filled with the images using ObjectControlImageFile.
   */
  private HBox imagesHBox;
  
  /**
   * An {@code ObjectControlGroup} to contain all Object Controls.
   */
  private ObjectControlGroup objectControlsGroup;

  /**
   * Constructor
   */
  public ObjectControlForImages(CustomizationFx customization, String labelText) {
    super(true);
    this.customization = customization;
    componentFactory = customization.getComponentFactoryFx();
    mainLayout = componentFactory.createHBox(10.0, 10.0);
    
    VBox controlsBox = componentFactory.createVBox(10.0);
    Label label = componentFactory.createLabel(labelText);
    controlsBox.getChildren().add(label);
    
    Button addImageButton = componentFactory.createButton("Add image", "click to select an image file to add");
    controlsBox.getChildren().add(addImageButton);
    mainLayout.getChildren().add(controlsBox);
    
    imagesHBox = componentFactory.createHBox(10.0, 10.0);
    mainLayout.getChildren().add(imagesHBox);
    
    objectControlsGroup = new ObjectControlGroup();
    
    mainLayout.getChildren().add(getStatusIndicator());
    
    addImageButton.setOnAction((e) -> {
      ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
      if (MediaRegistry.albumInfoDirectory != null) {
        objectControlImageFile.setInitialDirectory(new File(MediaRegistry.albumInfoDirectory + File.separator + "Pictures"));
      }
      FileChooser fileChooser = objectControlImageFile.getFileChooser();
      File file = fileChooser.showOpenDialog(null);
      if (file != null) {
        createAndAddImageControl("Pictures" + File.separator + file.getName());
      }
    });
    
    isValid();
  }
  
  private ObjectControlImageFile createAndAddImageControl(String imageFileName) {
    ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
    if (MediaRegistry.albumInfoDirectory != null) {
      objectControlImageFile.setInitialDirectory(new File(MediaRegistry.albumInfoDirectory + File.separator + "Pictures"));
    }
    objectControlImageFile.setValue(new File(MediaRegistry.albumInfoDirectory + File.separator + imageFileName));
    
    objectControlsGroup.addObjectControl(objectControlImageFile);
    
    StackPane stackPane = objectControlImageFile.getControl();
    LOGGER.severe("Drawing: " + objectControlImageFile.getValue());
    Button deleteImageButton = componentFactory.createButton("Delete", "Remove this image");
    StackPane.setAlignment(deleteImageButton, Pos.TOP_RIGHT);
    stackPane.getChildren().add(deleteImageButton);
    deleteImageButton.setOnAction((e) -> {
      LOGGER.severe("Removing image");
      objectControlsGroup.removeObjectInput(objectControlImageFile);
      imagesHBox.getChildren().remove(getStackPaneForObjectControl(objectControlImageFile.getControl()));
    });
    imagesHBox.getChildren().add(stackPane);
    
    return objectControlImageFile;
  }
  
  private StackPane getStackPaneForObjectControl(StackPane stackPane) {
    for (Object object: imagesHBox.getChildren()) {
      if (object.equals(stackPane)) {
        return (StackPane) object;
      }
    }
    
    return null;
  }

  @Override
  public void setValue(List<String> imageFileNames) {
    for (String imageFileName: imageFileNames) {
      createAndAddImageControl(imageFileName);
    }
  }

  @Override
  public Node getControl() {
    return mainLayout;
  }

  @Override
  public String getValueAsFormattedText() {
    return StringUtil.stringCollectionToCommaSeparatedStrings(getValue());
  }

  @Override
  protected boolean ociDetermineFilledIn() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected List<String> ociDetermineValue(Object source) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void ociSetErrorFeedback(boolean valid) {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void ociRedrawValue() {
    // No action as images are always shown in the 'standard' way.
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid() {
    return objectControlsGroup.getIsValid();
  }
}
