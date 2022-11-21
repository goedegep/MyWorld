package goedegep.media.fotomapview.guifx;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.media.photo.PhotoMetaDataWithImage;
import javafx.collections.ObservableSet;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class lets the user select which modified photos to save.
 */
public class SaveModifiedPhotosDialog extends Dialog<ButtonType> {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(SaveModifiedPhotosDialog.class.getName());
  private final static String WINDOW_TITLE = "Save photos";
  
  
  private ComponentFactoryFx componentFactory;

  private ObservableSet<PhotoMetaDataWithImage> photoInfos;
  private Map<PhotoMetaDataWithImage, CheckBox> selectionCheckBoxes = new HashMap<>();

  /**
   * Constructor.
   * 
   * @param customization the window customization
   * @param ownerWindow the window which owns (created) this wizard.
   * @param initiallySelectedFolder the initially selected main photo folder
   * @param initialIgnoreFolders the initial, comma separated, list of folders to be ignored
   */
  public SaveModifiedPhotosDialog(CustomizationFx customization, Stage ownerWindow, ObservableSet<PhotoMetaDataWithImage> photoInfos) {
    this.photoInfos = photoInfos;
    
    setTitle(WINDOW_TITLE);
    
    initOwner(ownerWindow);

    componentFactory = customization.getComponentFactoryFx();
    
    createGUI(ownerWindow);
    setResizable(true);
  }
  
  /**
   * Get the photos to be saved.
   * 
   * @return the photos to be saved.
   */
  public List<PhotoMetaDataWithImage> getSelectedPhotos() {
    List<PhotoMetaDataWithImage> selectedPhotos = new ArrayList<>();
    
    for (PhotoMetaDataWithImage photoInfo: photoInfos) {
      CheckBox checkBox = selectionCheckBoxes.get(photoInfo);
      if (checkBox.isSelected()) {
        selectedPhotos.add(photoInfo);
      }
    }
    
    return selectedPhotos;
  }
    
  /*
   * Create the GUI.
   */
  private void createGUI(Stage ownerWindow) {
    setHeaderText("Select the photos to be saved.");
    
    VBox vBox = componentFactory.createVBox(12.0);
    
    CheckBox allOrNothingCheckBox = componentFactory.createCheckBox("All", true);
    allOrNothingCheckBox.setOnAction(e -> selectAllOrNothing(allOrNothingCheckBox.isSelected()));
    vBox.getChildren().add(allOrNothingCheckBox);
    
    for (PhotoMetaDataWithImage photoInfo: photoInfos) {
      File file = new File(photoInfo.getFileName());
      String buttonName = file.getName();
      CheckBox checkBox = componentFactory.createCheckBox(buttonName, true);
      selectionCheckBoxes.put(photoInfo, checkBox);
      vBox.getChildren().add(checkBox);
    }
        
    getDialogPane().setContent(vBox);

    getDialogPane().getButtonTypes().add(ButtonType.OK);
    getDialogPane().getButtonTypes().add(ButtonType.CANCEL);    
  }
  
  private void selectAllOrNothing(boolean selected) {
    for (CheckBox checkBox: selectionCheckBoxes.values()) {
      checkBox.setSelected(selected);
    }
  }
}
