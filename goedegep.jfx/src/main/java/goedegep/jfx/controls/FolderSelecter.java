package goedegep.jfx.controls;

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
public class FolderSelecter extends FileOrFolderSelecterAbstract {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(FolderSelecter.class.getName());
  
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
  public FolderSelecter(String initiallySelecterFolder, int textFieldWidth, String textFieldToolTipText,
      String folderChooserButtonText, String folderChooserButtonToolTipText, String directoryChooserTitle) {
    super(textFieldWidth, textFieldToolTipText);
    
    getPathTextField().textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        File folder = new File(newValue);
        if (folder.exists()  &&  folder.isDirectory()) {
          isValid().set(true);
        } else {
          isValid().set(false);
        }
        isFilledIn().set(!newValue.isEmpty());
      } else {
        isValid().set(false);
        isFilledIn().set(false);
      }
      
      if (!isValid().get()) {
        getPathTextField().setStyle("-fx-text-inner-color: red;");
      } else {
        
        getPathTextField().setStyle("-fx-text-inner-color: black;");
      }
      
      notifyListeners();
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
        if (isValid().get()) {
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
      setObjectValue(initiallySelecterFolder);
    }
  }

//  /**
//   * Get the TextField.
//   * <p>
//   * This TextField:
//   * <ul>
//   * <li>
//   * Shows the current value.
//   * </li>
//   * <li>
//   * Can be used to enter a value.
//   * </li>
//   * <li>
//   * Indicates (to the user) whether the value is valid (red text indicates an invalid value).
//   * </li>
//   * <li>
//   * Provides the current value, if the selectionValidProperty is <code>true</code>.
//   * </li>
//   * </ul>
//   * 
//   * @return the TextField
//   */
//  public TextField getFolderPathTextField() {
//    return getPathTextField();
//  }

  /**
   * Get the Button for calling up a FolderChooser.
   * 
   * @return the Button for calling up a FolderChooser
   */
  public Button getFolderChooserButton() {
    return folderChooserButton;
  }

}
