package goedegep.jfx.objectcontrols;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class is an {@code ObjectControl} for selecting a file.
 * <p>
 * It offers the following GUI controls:
 * <ul>
 * <li>
 * A TextField which shows the currently selected file path and in which the file path can be entered.<br/>
 * If the text in the text field is not a valid file path, the text is shown in red.
 * </li>
 * <li>
 * A Button to call up a FileChooser
 * </li>
 * <li>
 * A status indicator node.
 * </li>
 * </ul>
 * By providing separate controls (instead of e.g. a Node containing the controls) there is complete freedom in using the
 * controls in a GUI.
 * 
 * <h4>File types (filters on file extensions)</h4>
 * You can add file types via the method {@code addFileType()}.
 *
 * <h4>Prefix</h4>
 * You can set a prefix, which is a base path for the selected file.<br/>
 * In this case {@code setValue()} still expects an absolute path and {@code getValue()} still returns an absolute path.
 * But:
 * <ul>
 * <li>The TextField shows the file path relative to the prefix, if the absolute path starts with the prefix.</li>
 * <li>If the text entered in the TextField is a relative path, the prefix is prepended to this text to obtain an absolute path. If the text is an absolute path, starting with the prefix,
 * this part is removed (resulting in a relative path). If the text is an absolute path not starting with the prefix, the text is used as is.</li>
 * <li>Method {@getPathNameUsingPrefix()} returns a path relative to the prefix, if the absolute path starts with the prefix, otherwise it also just returns the absolute path.</li>
 * </ul>
 * 
 */
public class ObjectControlFileSelecter extends ObjectControlFileOrFolderSelecterAbstract {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(ObjectControlFileSelecter.class.getName());

  /**
   * Distinguish between an open or save dialog.
   * <p>
   * With an open dialog, the file has to exist.
   * For save this is not the case. Instead the user has to confirm that the file will be overwritten.
   */
  private boolean isSaveDialog = false;  // open dialog is the default
  
  /**
   * List of accepted file extensions.
   */
  private List<ExtensionFilter> extensionFilters = new ArrayList<>();
  
  /**
   * The currently selected {@code ExtensionFilter}.
   */
  private ExtensionFilter selectedExtensionFilter = null;
  
  /**
   * {@code Button} to activate the {@code FileChooser}.
   */
  private Button fileChooserButton;
  
  /**
   * The {@FileChooser} to select a file.
   */
  private FileChooser fileChooser = null;
  
  /**
   * The {@code File} selected using the {@code fileChooser}.
   * <p>
   * This value is used by the methods called by {@code ociHandleNewUserInput()}.
   */
  private File fileSelectedByFileChooser = null;
  
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param textFieldWidth Width of the TextField (in pixels). If this value is -1, the default width is used.
   * @param textFiedlToolTipText if not null, this text will be used as Tooltip for the TextField
   * @param fileChooserButtonText the text shown on the button to call up a FileChooser (may not be null)
   * @param fileChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a FileChooser.
   * @param fileChooserTitle title for the FileChooser (may not be null)
   * @param isOptional Indication of whether the control is optional (if true) or mandatory.
   */
  public ObjectControlFileSelecter(CustomizationFx customization, int textFieldWidth, String textFiedlToolTipText,
      String fileChooserButtonText, String fileChooserButtonToolTipText, String fileChooserTitle, boolean isOptional) {
    super(customization, textFieldWidth, textFiedlToolTipText, fileChooserTitle, isOptional);

    fileChooserButton = componentFactory.createButton(fileChooserButtonText, fileChooserButtonToolTipText);
    fileChooserButton.setOnAction(actionEvent -> handleFileChooserButtonPressed());

    setObject(null);
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
   * <p>
   * @param fileTypeExtension the file name extension for this type, e.g. ".pdf".
   * @param fileTypeDescription a description of this file type, e.g. "Portable Data Format".
   * @param setAsSelected if true, this file type will initially be selected in the {@code FileChooser}.
   *        The last call to this method, with this parameter set to true, determines the initially selected file type.
   */
  public void addFileType(String fileTypeExtension, String fileTypeDescription, boolean setAsSelected) {
    ExtensionFilter extensionFilter = new ExtensionFilter(fileTypeDescription, "*" + fileTypeExtension);
    extensionFilters.add(extensionFilter);

    if (setAsSelected) {
      selectedExtensionFilter = extensionFilter;
    }
  }
  
  /**
   * Set the method for obtaining the initial file.
   * <p>
   * THIS METHOD IS NOT SUPPORTED! This because it is not supported on Window.
   * 
   * @param initialFileSupplier the method for obtaining the initial file.
   */
  public void setInitialFileProvider(Supplier<String> initialFileSupplier) {
    throw new UnsupportedOperationException("Setting the initial file name is not implemented, as it is not supported on Windows.");
  }

  /**
   * Get the Button for calling up a FileChooser.
   * 
   * @return the Button for calling up a FileChooser
   */
  public Button getFileChooserButton() {
    return fileChooserButton;
  }

  /**
   * Handle the fact the the {@code fileChooserButton} is pressed.
   * <p>
   * If the {@code fileChooser} hasn't been created yet:
   * <ul>
   * <li>The {@code fileChooser} is created<./li>
   * <li>Its title is set to {@code fileChooserTitle}.</li>
   * <li>Any extension filters, defined by {@code extensionFilters}, are added.</li>
   * <li>If there is a 'selected extension filter' defined, via {@code selectedExtensionFilter}, this filter is selected.</li>
   * <li></li>
   * </ul>
   * 
   */
  private void handleFileChooserButtonPressed() {
    if (fileChooser == null) {
      fileChooser = componentFactory.createFileChooser(fileOrFolderChooserTitle);

      if (!extensionFilters.isEmpty()) {
        fileChooser.getExtensionFilters().addAll(extensionFilters);
      }
      if (selectedExtensionFilter != null) {
        fileChooser.setSelectedExtensionFilter(selectedExtensionFilter);
      }
    }
    
    if (getValue() != null) {  // If there is a valid File value.
      File folder = getValue().getParentFile();
      fileChooser.setInitialDirectory(folder);
      // Setting the file with fileChooser.setInitialFileName(getValue().getName()); doesn't work on Windows.
    } else {
      if (initialFolderSupplier != null) {
        String initialFolderName = initialFolderSupplier.get();
        if (initialFolderName != null) {
          File initialFolder = new File(initialFolderName);
          if (initialFolder.exists()) {
            fileChooser.setInitialDirectory(initialFolder);
          }
        }
      }
    }
    
    if (isSaveDialog) {
      fileSelectedByFileChooser = fileChooser.showSaveDialog(null);
    } else {
      fileSelectedByFileChooser = fileChooser.showOpenDialog(null);
    }

    if (fileSelectedByFileChooser != null) {
      ociHandleNewUserInput(fileChooser);
    }
  }

  /**
   * @InheritDoc
   * <p>
   */
  @Override
  public boolean ociDetermineFilledIn(Object source) {
    if (source == pathTextField) {
      return (pathTextField.textProperty().get() != null  &&  !pathTextField.textProperty().get().isEmpty())  ||
          fileSelectedByFileChooser != null;
    } else {
      return fileSelectedByFileChooser != null;
    }
  }

  /**
   * @InheritDoc
   */
  @Override
  public File ociDetermineValue(Object source) {
    File file = null;
    
    if (source == fileChooser) {
      file = fileSelectedByFileChooser;
    } else {
      file = new File(addPrefixIfSet(pathTextField.textProperty().get()));
    }
    
    return file;
  }
  
  /**
   * {@inheritDoc}
   */
  protected void ociUpdateNonSourceControls(Object source) {
    if (source != pathTextField) {
      setPathTextFieldText();
    }
  }
}
