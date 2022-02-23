package goedegep.jfx.controls;

import java.io.File;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class provides a related set of controls (and a property) to select a file.
 * <ul>
 * <li>
 * A boolean property which indicates whether a valid file is selected.
 * </li>
 * <li>
 * A TextField which shows the currently selected file path and in which the file path can be entered.<br/>
 * If the boolean property indicates that a valid file is selected, the text of this text field provides the value.<br/>
 * If the text in the text field is not a valid file path, the text is shown in red.
 * </li>
 * <li>
 * A Button to call up a FileChooser
 * </li>
 * </ul>
 * By providing separate controls (instead of e.g. a Node containing the controls) there is complete freedom in using the
 * controls in a GUI.
 */
public class FileSelecter extends FileOrFolderSelecterAbstract {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(FileSelecter.class.getName());
  
  private Button fileChooserButton;
  private boolean isSaveDialog = false;  // open dialog is the default
  
  /**
   * Constructor.
   * 
   * @param initiallySelectedFile The initially selected file. If not null, this is filled-in in the text field,
   *                                and it is used as initial value for the FileChooser.
   * @param textFieldWidth Width of the TextField (in pixels). If this value is -1, the default width is used.
   * @param textFiedlToolTipText if not null, this text will be used as Tooltip for the TextField
   * @param fileChooserButtonText the text shown on the button to call up a FileChooser (may not be null)
   * @param fileChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a FileChooser.
   * @param fileChooserTitle title for the FileChooser (may not be null)
   */
  public FileSelecter(String initiallySelectedFile, int textFieldWidth, String textFiedlToolTipText,
      String fileChooserButtonText, String fileChooserButtonToolTipText, String fileChooserTitle, String fileTypeExtension, String fileTypeDescription) {
    super(textFieldWidth, textFiedlToolTipText);
    
    getPathTextField().textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        File file = new File(newValue);
        if (isSaveDialog) {
          // For saving a file, the folder shall exist
          File folder = file.getParentFile();
          if (folder != null &&  folder.exists()  &&  folder.isDirectory()) {
            isValid().set(true);
          } else {
            isValid().set(false);
          }
        } else {
          // For opening the file shall exist
          if (file.exists()  &&  file.isFile()) {
            isValid().set(true);
          } else {
            isValid().set(false);
          }
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

    
    fileChooserButton = new Button(fileChooserButtonText);
    
    if (fileChooserButtonToolTipText != null) {
      fileChooserButton.setTooltip(new Tooltip(fileChooserButtonToolTipText));
    }
    
    fileChooserButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(fileChooserTitle);
        if (fileTypeExtension != null  &&  fileTypeDescription != null) {
          ExtensionFilter extensionFilter = new ExtensionFilter(fileTypeDescription, "*" + fileTypeExtension);
          fileChooser.getExtensionFilters().add(extensionFilter);
          fileChooser.setSelectedExtensionFilter(extensionFilter);
        }
        
        File file = new File (getPathTextField().textProperty().get());
        if (!file.isDirectory()) {
          file = file.getParentFile();
        }
        if ((file != null)  &&  file.isDirectory()) {
          fileChooser.setInitialDirectory(file);
        }
        File selectedFile;
        if (isSaveDialog) {
          selectedFile = fileChooser.showSaveDialog(null);
        } else {
          selectedFile = fileChooser.showOpenDialog(null);
        }
        
        if (selectedFile != null) {
          // The selected folder is written to the text field, which automatically leads an update of the selectionValidProperty.
          getPathTextField().setText(selectedFile.getAbsolutePath());
        }
      }
    });
    
    // Do this at the end, so it automatically leads an update of the selectionValidProperty.
    if (initiallySelectedFile != null) {
      getPathTextField().setText(initiallySelectedFile);
    }
  }
  
  /**
   * Specify whether this is a 'file open' or 'save as ..' dialog.
   * 
   * @param isSaveDialog if true, it is a 'save as ..' dialog, else it is a 'file open' dialog.
   */
  public void setOpenOrSaveDialog(boolean isSaveDialog) {
    this.isSaveDialog = isSaveDialog;
  }

  /**
   * Get the TextField.
   * <p>
   * This TextField:
   * <ul>
   * <li>
   * Shows the current value.
   * </li>
   * <li>
   * Can be used to enter a value.
   * </li>
   * <li>
   * Indicates (to the user) whether the value is valid (red text indicates an invalid value).
   * </li>
   * <li>
   * Provides the current value, if the selectionValidProperty is <code>true</code>.
   * </li>
   * </ul>
   * 
   * @return the TextField
   */
  public TextField getFilePathTextField() {
    return getPathTextField();
  }

  /**
   * Get the Button for calling up a FileChooser.
   * 
   * @return the Button for calling up a FileChooser
   */
  public Button getFileChooserButton() {
    return fileChooserButton;
  }

}
