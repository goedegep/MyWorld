package goedegep.media.mediadb.albumeditor.guifx;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControl;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlImageFile;
import goedegep.jfx.objectcontrols.ObjectControlTemplate;
import goedegep.media.app.MediaRegistry;
import goedegep.util.file.FileUtils;
import goedegep.util.string.StringUtil;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * This {@link ObjectControl} can be used to edit a list of images.
 */
public class ObjectControlForImages extends ObjectControlTemplate<List<String>> {
  private static final Logger LOGGER = Logger.getLogger(ObjectControlForImages.class.getName());

  
  /**
   * Main layout
   * <p>
   * On the left is a VBox with a label and an 'Add' button.
   * This is followed by an {@code HBox} (imagesHBox), containing one {@code ObjectControlImageFile} per image.
   */
  private HBox mainLayout;
  
  /**
   * An {@code HBox} filled with the images using {@code ObjectControlImageFile}.
   */
  private HBox imagesHBox;
  
  /**
   * An {@code ObjectControlGroup} to contain all Object Controls.
   */
  private ObjectControlGroup objectControlsGroup;
  
  private boolean ignoreChanges = false;
  

  /**
   * Constructor
   * 
   * @param customization the GUI customization
   * @param labelText a text to identify the list of images
   */
  public ObjectControlForImages(CustomizationFx customization, String labelText) {
    super(customization, true);
    
    LOGGER.info("=>");
    mainLayout = componentFactory.createHBox(10.0, 10.0);
    
    // Controls
    VBox controlsBox = componentFactory.createVBox(10.0);
    Label label = componentFactory.createLabel(labelText);
    controlsBox.getChildren().add(label);
    
    Button addImageButton = componentFactory.createButton("Add image", "click to select an image file to add");
    controlsBox.getChildren().add(addImageButton);
    mainLayout.getChildren().add(controlsBox);
    
    // Images
    imagesHBox = componentFactory.createHBox(10.0, 10.0);
    mainLayout.getChildren().add(imagesHBox);
    
    objectControlsGroup = new ObjectControlGroup();
    objectControlsGroup.addListener((e) -> {
      if (!ignoreChanges) {
        ociHandleNewUserInput(this);
      }
    });

    // Status indicator
    mainLayout.getChildren().add(getStatusIndicator());
    
    addImageButton.setOnAction((e) -> {
      LOGGER.info("Adding new image control =>");
      ObjectControlImageFileWithDelete objectControlImageFile = new ObjectControlImageFileWithDelete(customization, this::deleteImageObjectControl);
      FileChooser fileChooser = objectControlImageFile.getFileChooser();
      File file = fileChooser.showOpenDialog(null);
      if (file != null) {
        addImageControl(objectControlImageFile, "Pictures" + File.separator + file.getName());
        ociHandleNewUserInput(this);
      }
      LOGGER.info("Adding new image control <=");
    });
    
    setValue(new ArrayList<String>());
    LOGGER.info("<=");
  }
  
//  private void updateValue() {
//    LOGGER.severe("=>");
//    value.clear();
//    Iterator<ObjectControl<? extends Object>> iterator = objectControlsGroup.iterator();
//    while (iterator.hasNext()) {
//      ObjectControl<? extends Object> objectControl = iterator.next();
//      if (objectControl instanceof ObjectControlImageFile objectControlImageFile) {
//        String fileName = objectControlImageFile.getValue().getAbsolutePath();
//        value.add(fileName);
//      }
//    }
//    LOGGER.severe("<=");
//  }
  
  /**
   * Add an {@code ObjectControlImageFile} to the {@code objectControlsGroup} and add it to the {@code imagesHBox}.<br/>
   * Also a button is added on the image to delete it.
   * 
   * @param objectControlImageFile the {@code ObjectControlImageFile} to be added.
   * @param imageFileName file name of the image file.
   * @return
   */
  private void addImageControl(ObjectControlImageFileWithDelete objectControlImageFileWithDelete, String imageFileName) {
    LOGGER.info("=>");
    objectControlImageFileWithDelete.setValue(new File(MediaRegistry.albumInfoDirectory + File.separator + imageFileName));
    
    objectControlsGroup.addObjectControls(objectControlImageFileWithDelete);
    
    imagesHBox.getChildren().add(objectControlImageFileWithDelete.getControl());
    LOGGER.info("<=");
  }
  
  private StackPane getStackPaneForObjectControl(StackPane stackPane) {
    LOGGER.info("=>");
    for (Object object: imagesHBox.getChildren()) {
      if (object.equals(stackPane)) {
        LOGGER.info("=> " + object);
        return (StackPane) object;
      }
    }
    
    LOGGER.info("=> null");
    return null;
  }

  @Override
  public Node getControl() {
    LOGGER.info("<=>");
    return mainLayout;
  }

  @Override
  public String getValueAsFormattedText() {
    LOGGER.info("<=>");
    return StringUtil.stringCollectionToCommaSeparatedStrings(getValue());
  }

  @Override
  protected boolean ociDetermineFilledIn(Object source) {
    LOGGER.info("=>");
    boolean result = objectControlsGroup.isAnyObjectControlFilledIn();
    LOGGER.info("<= " + result);
   return result;
  }

  @Override
  protected List<String> ociDetermineValue(Object source) {
    LOGGER.info("=>");

    List<String> newValue = new ArrayList<>();
    
    Iterator<ObjectControl<? extends Object>> iterator = objectControlsGroup.iterator();
    while (iterator.hasNext()) {
      ObjectControl<? extends Object> objectControl = iterator.next();
      if (objectControl instanceof ObjectControlImageFile objectControlImageFile) {
        String fileName = objectControlImageFile.getValue().getAbsolutePath();
        fileName = FileUtils.getPathRelativeToFolder(MediaRegistry.albumInfoDirectory, fileName);
        newValue.add(fileName);
      }
    }
    LOGGER.info("<= " + newValue);
    return newValue;
  }

  @Override
  protected void ociSetErrorFeedback(boolean valid) {
    LOGGER.info("<=> " + valid);
    // Not applicable
    
  }

  @Override
  protected void ociRedrawValue() {
    LOGGER.info("<=>");
    // No action as images are always shown in the 'standard' way.
  }

  @Override
  protected void ociUpdateNonSourceControls(Object source) {
    LOGGER.info("=>");

    if (source == null) {
      ignoreChanges = true;
      
      imagesHBox.getChildren().clear();
      objectControlsGroup.clear();
      
      for (String imageFileName: getValue()) {
        addImageControl(new ObjectControlImageFileWithDelete(customization, this::deleteImageObjectControl), imageFileName);
      }
      
      ignoreChanges = false;
    }
    
    LOGGER.info("<=");
  }
  
  private void deleteImageObjectControl(ObjectControlImageFileWithDelete objectControlImageFileWithDelete) {
    LOGGER.info("=>");

    objectControlsGroup.removeObjectControl(objectControlImageFileWithDelete);
    //  getValue().remove(imagesHBox.getChildren().indexOf(getStackPaneForObjectControl(objectControlImageFile.getControl())));
    imagesHBox.getChildren().remove(getStackPaneForObjectControl(objectControlImageFileWithDelete.getControl()));

    LOGGER.info("<=");
  }
}

class ObjectControlImageFileWithDelete extends ObjectControlImageFile {
  private static final Logger LOGGER = Logger.getLogger(ObjectControlImageFileWithDelete.class.getName());

  ObjectControlImageFileWithDelete(CustomizationFx customization, Consumer<ObjectControlImageFileWithDelete> deleteImageObjectControl) {
    super(customization, false);
    LOGGER.info("=>");

    if (MediaRegistry.albumInfoDirectory != null) {
      setInitialDirectory(new File(MediaRegistry.albumInfoDirectory + File.separator + "Pictures"));
    }
    
    StackPane stackPane = getControl();
    LOGGER.info("Drawing: " + getValue());
    Button deleteImageButton = customization.getComponentFactoryFx().createButton("Delete", "Remove this image");
    StackPane.setAlignment(deleteImageButton, Pos.TOP_RIGHT);
    stackPane.getChildren().add(deleteImageButton);
    deleteImageButton.setOnAction((e) -> {
      LOGGER.info("Removing image =>");
      deleteImageObjectControl.accept(this);
      LOGGER.info("Removing image <=");
    });

    LOGGER.info("<=");
  }

}
