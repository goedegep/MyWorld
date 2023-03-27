package goedegep.jfx.objectcontrols;

import java.io.File;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.DirectoryChooser;

/**
 * This class provides a related set of controls (and a property) to select a folder.
 * <ul>
 * <li>
 * A boolean property which indicates whether a valid folder is selected.
 * </li>
 * <li>
 * A TextField which shows the currently selected folder path and in which the folder path can be entered.<br/>
 * If the boolean property indicates that a valid folder is selected, the text of this text field provides the value.<br/>
 * If the text in the text field is not a valid folder path, the text is shown in red.
 * </li>
 * <li>
 * A Button to call up a DirectoryChooser
 * </li>
 * </ul>
 * By providing separate controls (instead of e.g. a Node containing the controls) there is complete freedom in using the
 * controls in a GUI.
 */
public class ObjectControlFolderSelecter extends ObjectControlFileOrFolderSelecterAbstract {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(ObjectControlFolderSelecter.class.getName());
  
  private Button folderChooserButton;
  
  /**
   * Constructor.
   * 
   * @param initiallySelecterFolder The initially selected folder. If not null, this is filled-in in the text field,
   *                                and it is used as initial value for the DirectoryChooser.
   * @param textFieldWidth Width of the TextField (in pixels). If this value is -1, the default width is used.
   * @param textFieldToolTipText if not null, this text will be used as Tooltip for the TextField
   * @param folderChooserButtonText the text shown on the button to call up a DirectoryChooser (may not be null)
   * @param folderChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a DirectoryChooser.
   * @param directoryChooserTitle title for the DirectoryChooser (may not be null)
   */
  public ObjectControlFolderSelecter(String initiallySelecterFolder, int textFieldWidth, String textFieldToolTipText,
      String folderChooserButtonText, String folderChooserButtonToolTipText, String directoryChooserTitle) {
    super(textFieldWidth, textFieldToolTipText);
    
    getPathTextField().textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        File folder = new File(newValue);
        if (folder.exists()  &&  folder.isDirectory()) {
//        	ocValidProperty().set(true);
        	valid = true;
        } else {
//        	ocValidProperty().set(false);
        	valid = false;
        }
//        ocFilledInProperty().set(!newValue.isEmpty());
        filledIn = !newValue.isEmpty();
      } else {
//    	  ocValidProperty().set(false);
          valid = false;
//        ocFilledInProperty().set(false);
        filledIn = false;
      }
      
      if (!ocIsValid()) {
        getPathTextField().setStyle("-fx-text-inner-color: red;");
      } else {
        
        getPathTextField().setStyle("-fx-text-inner-color: black;");
      }
      
      ociNotifyListeners();
    });
    
    folderChooserButton = new Button(folderChooserButtonText);
    
    if (folderChooserButtonToolTipText != null) {
      folderChooserButton.setTooltip(new Tooltip(folderChooserButtonToolTipText));
    }
    
    folderChooserButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(directoryChooserTitle);
        if (ocIsValid()) {
          directoryChooser.setInitialDirectory(new File(getPathTextField().getText()));
        }
        File selectedFolder = directoryChooser.showDialog(null);
        if (selectedFolder != null) {
          // The selected folder is written to the text field, which automatically leads an update of the selectionValidProperty.
          getPathTextField().setText(selectedFolder.getAbsolutePath());
        }
      }
    });
    
    // Do this at the end, so it automatically leads an update of the selectionValidProperty.
    if (initiallySelecterFolder != null) {
      ocSetValue(initiallySelecterFolder);
    }
  }

  /**
   * Get the Button for calling up a FolderChooser.
   * 
   * @return the Button for calling up a FolderChooser
   */
  public Button getFolderChooserButton() {
    return folderChooserButton;
  }

}
