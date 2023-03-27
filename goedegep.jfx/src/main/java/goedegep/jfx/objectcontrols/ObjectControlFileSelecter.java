package goedegep.jfx.objectcontrols;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.scene.control.Button;
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
public class ObjectControlFileSelecter extends ObjectControlFileOrFolderSelecterAbstract {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(ObjectControlFileSelecter.class.getName());

  private Button fileChooserButton;
  private boolean isSaveDialog = false;  // open dialog is the default
  private List<ExtensionFilter> extensionFilters = new ArrayList<>();
  private ExtensionFilter selectedExtensionFilter = null;
  private String initiallySelectedFolder = null;
  private FileChooser fileChooser = null;

  /**
   * Constructor.
   * 
   * @param initiallySelectedFilename The initially selected file. If not null, this is filled-in in the text field,
   *                                and it is used as initial value for the FileChooser.
   * @param textFieldWidth Width of the TextField (in pixels). If this value is -1, the default width is used.
   * @param textFiedlToolTipText if not null, this text will be used as Tooltip for the TextField
   * @param fileChooserButtonText the text shown on the button to call up a FileChooser (may not be null)
   * @param fileChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a FileChooser.
   * @param fileChooserTitle title for the FileChooser (may not be null)
   */
  public ObjectControlFileSelecter(String initiallySelectedFilename, int textFieldWidth, String textFiedlToolTipText,
      String fileChooserButtonText, String fileChooserButtonToolTipText, String fileChooserTitle) {
    super(textFieldWidth, textFiedlToolTipText);

    getPathTextField().textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        File file = new File(addPrefixIfSet(newValue));
        if (isSaveDialog) {
          // For saving a file, the folder shall exist
          File folder = file.getParentFile();
          if (folder != null &&  folder.exists()  &&  folder.isDirectory()) {
            //        	  ocValidProperty().set(true);
            valid = true;
          } else {
            //        	  ocValidProperty().set(false);
            valid = false;
          }
        } else {
          // For opening the file shall exist
          if (file.exists()  &&  file.isFile()) {
            //        	  ocValidProperty().set(true);
            valid = true;
          } else {
            //        	  ocValidProperty().set(false);
            valid = false;
          }
        }
        filledIn = !newValue.isEmpty();
//        ocFilledInProperty().set(!newValue.isEmpty());
      } else {
//        ocValidProperty().set(false);
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


    fileChooserButton = new Button(fileChooserButtonText);

    if (fileChooserButtonToolTipText != null) {
      fileChooserButton.setTooltip(new Tooltip(fileChooserButtonToolTipText));
    }

    fileChooserButton.setOnAction(actionEvent -> {
      if (fileChooser == null) {
        fileChooser = new FileChooser();
        fileChooser.setTitle(fileChooserTitle);

        if (!extensionFilters.isEmpty()) {
          fileChooser.getExtensionFilters().addAll(extensionFilters);
        }
        if (selectedExtensionFilter != null) {
          fileChooser.setSelectedExtensionFilter(selectedExtensionFilter);
        }

        //        File file = null;
        //        String filename = objectValue().get();
        //        if (filename != null) {
        //          if (getPrefix() != null) {
        //            file = new File (getPrefix(), getPathTextField().textProperty().get());
        //          } else {
        //            file = new File (getPathTextField().textProperty().get());
        //          }
        //        }
        //        if ((file != null)  &&  !file.isDirectory()) {
        //          file = file.getParentFile();
        //        }
        //        if ((file != null)  &&  file.isDirectory()) {
        if (initiallySelectedFolder != null) {
          File file;
          if (getPrefix() != null) {
            file = new File(getPrefix(), initiallySelectedFolder);
          } else {
            file = new File (initiallySelectedFolder);
          }
          if (!file.exists()) {
            file = file.getParentFile();
          }
          if (file.exists()) {
            fileChooser.setInitialDirectory(file);
          }
        }
        //        }
      }
      File selectedFile;
      if (isSaveDialog) {
        selectedFile = fileChooser.showSaveDialog(null);
      } else {
        selectedFile = fileChooser.showOpenDialog(null);
      }

      if (selectedFile != null) {
        // The selected folder is written to the text field, which automatically leads an update of the selectionValidProperty.
        String newFilename = selectedFile.getAbsolutePath();
        newFilename = removePrefixIfSet(newFilename);
        getPathTextField().setText(newFilename);
      }
    });

    // Do this at the end, so it automatically leads an update of the selectionValidProperty.
    if (initiallySelectedFilename != null) {
      File initiallySelectedFile = new File(initiallySelectedFilename);
      initiallySelectedFolder = initiallySelectedFile.getParent();

      getPathTextField().setText(initiallySelectedFilename);
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
   * Add a file type, being a file extension with a related description.
   */
  public void addFileType(String fileTypeExtension, String fileTypeDescription, boolean setAsSelected) {
    ExtensionFilter extensionFilter = new ExtensionFilter(fileTypeDescription, "*" + fileTypeExtension);
    extensionFilters.add(extensionFilter);

    if (setAsSelected) {
      selectedExtensionFilter = extensionFilter;
    }
  }

  /**
   * Get the Button for calling up a FileChooser.
   * 
   * @return the Button for calling up a FileChooser
   */
  public Button getFileChooserButton() {
    return fileChooserButton;
  }

  public void setInitiallySelectedFolder(String initiallySelectedFolder) {
    this.initiallySelectedFolder = initiallySelectedFolder;
  }
}
