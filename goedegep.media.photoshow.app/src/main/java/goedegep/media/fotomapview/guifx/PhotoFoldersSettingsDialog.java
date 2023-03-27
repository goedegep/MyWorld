package goedegep.media.fotomapview.guifx;

import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * This class lets the user specify the photo folders for which the photos will be shown on the map.
 * <p>
 * The following items can be specified:
 * <ul>
 *   <li>The main photos folder</li>
 *   <li>The folders to be ignored</li>
 * </ul>
 */
public class PhotoFoldersSettingsDialog extends Dialog<ButtonType> {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(PhotoFoldersSettingsDialog.class.getName());
  private final static String WINDOW_TITLE = "Photo folder selection wizard";
  
  
  private ComponentFactoryFx componentFactory;

  // GUI components
  private ObjectControlFolderSelecter folderSelecter;
  private Button okButton;    // this will only be enabled if photoFolders is not empty.
  
  // State information;
//  private BooleanProperty selectionValidProperty;
  private String ignoreFolders = null;

  /**
   * Constructor.
   * 
   * @param customization the window customization
   * @param ownerWindow the window which owns (created) this wizard.
   * @param initiallySelectedFolder the initially selected main photo folder
   * @param initialIgnoreFolders the initial, comma separated, list of folders to be ignored
   */
  public PhotoFoldersSettingsDialog(CustomizationFx customization, Stage ownerWindow, String initiallySelectedFolder, String initialIgnoreFolders) {
    if (initialIgnoreFolders != null) {
      ignoreFolders = initialIgnoreFolders;
    } else {
      ignoreFolders = "";
    }
    
    setTitle(WINDOW_TITLE);
    
    initOwner(ownerWindow);

    componentFactory = customization.getComponentFactoryFx();
    
    createGUI(ownerWindow, initiallySelectedFolder);
    setResizable(true);
  }
  
  /**
   * Get the selected main folder.
   * 
   * @return the selected main folder.
   */
  public String getSelectedFolder() {
    if (folderSelecter.ocIsValid()) {
      return folderSelecter.ocGetValue();
    } else {
      return null;
    }
  }
  
  /**
   * Get the comma separated list of folders to be ignored, when determining the photo folders.
   * 
   * @return the list of folders to be ignored
   */
  public String getIgnoreFolders() {
    return ignoreFolders;
  }
  
  /*
   * Create the GUI.
   */
  private void createGUI(Stage ownerWindow, String initiallySelectedFolder) {
    setHeaderText("Select the folder of which the sub folders contain the photos to be shown on the map.");
    
    GridPane contentPanel = componentFactory.createGridPane();
    
    // Row 0: folder selection; label, textfield and button to start directory chooser.
    Label folderNameLabel = componentFactory.createLabel("Photo folder:");
    contentPanel.add(folderNameLabel, 0, 0);
    
    folderSelecter = componentFactory.createFolderSelecter(initiallySelectedFolder, 400, "Currently selected folder",
        "Choose folder", "Select photo folder via a file chooser", "Select the folder with photos");
    
    Node folderName = folderSelecter.getPathTextField();
    folderSelecter.addListener((observable) -> {
      LOGGER.severe("In textProperty Listener");
      handleNewPhotoFolderSelected(folderSelecter.ocIsValid(), folderSelecter.ocGetValue());      
    });
    contentPanel.add(folderName, 1, 0);
    
    Button folderChooserButton = folderSelecter.getFolderChooserButton();
    contentPanel.add(folderChooserButton, 2, 0);
    
//    selectionValidProperty = folderSelecter.ocValidProperty();
    
    // Row 1: folders to be ignored; label, textfield
    Label ignoreFoldersLabel = componentFactory.createLabel("Sub-folders to be ignored:");
    contentPanel.add(ignoreFoldersLabel, 0, 1);
    
    TextField ignoreFolderTextField = componentFactory.createTextField(ignoreFolders, 200, "a comma separated list of sub-folders to be ignored");
    ignoreFolderTextField.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        ignoreFolders = ignoreFolderTextField.getText();
      }
      
    });
    contentPanel.add(ignoreFolderTextField, 1, 1);
    
    getDialogPane().setContent(contentPanel);

    getDialogPane().getButtonTypes().add(ButtonType.OK);
    getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    
    okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
    
    handleNewPhotoFolderSelected(folderSelecter.ocIsValid(), folderSelecter.ocGetValue());
  }
  
  /**
   * Handle any change in the folder selection text.
   * <p>
   * If the selection is valid:
   * <ul>
   * <li>
   * The set of photo folders is determined (see {@link #determinePhotoFolders(String)}.
   * </li>
   * <li>
   * These folders are shown in the {@link #photoFoldersArea}.
   * </li>
   * <li>
   * The OK button is enabled.
   * </li>
   * </ul>
   * Otherwise:
   * <ul>
   * <li>
   * The {@link #photoFoldersArea} is cleared.
   * </li>
   * <li>
   * The OK button is disabled.
   * </li>
   * </ul>
   * 
   * @param selectionIsValid If true, <code>selectedFolder</code> is the absolute path of an existing folder.
   * @param selectedFolder the folder under which the photo folders are located.
   */
  private void handleNewPhotoFolderSelected(Boolean selectionIsValid, String selectedFolder) {
    okButton.setDisable(!selectionIsValid);    
  }
}
