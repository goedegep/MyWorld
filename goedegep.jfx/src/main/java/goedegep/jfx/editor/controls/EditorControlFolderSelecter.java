package goedegep.jfx.editor.controls;

import java.io.File;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditorException;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;

/**
 * This class is an {@code EditorControl} for selecting a folder.
 * <p>
 * It offers the following GUI controls:
 * <ul>
 * <li>
 * A TextField which shows the currently selected folder path and in which the folder path can be entered.<br/>
 * If the text in the text field is not a valid folder path, the text is shown in red.
 * </li>
 * <li>
 * A Button to call up a <@link FolderChooser>.
 * </li>
 * <li>
 * A status indicator node.
 * </li>
 * </ul>
 * By providing separate controls (instead of e.g. a Node containing the controls) there is complete freedom in using the
 * controls in a GUI.<br/>
 * 
 * <h4>Prefix</h4>
 * You can set a prefix, which is a base path for the selected folder.
 * In this case {@code setValue()} still expects an absolute path and {@code getValue()} still returns an absolute path.
 * But:
 * <ul>
 * <li>The TextField shows the folder path relative to the prefix, if the absolute path starts with the prefix.</li>
 * <li>If the text entered in the TextField is a relative path, the prefix is prepended to this text to obtain an absolute path. If the text is an absolute path, starting with the prefix,
 * this part is removed (resulting in a relative path). If the text is an absolute path not starting with the prefix, the text is used as is.</li>
 * <li>Method {@getPathNameUsingPrefix()} returns a path relative to the prefix, if the absolute path starts with the prefix, otherwise it also just returns the absolute path.</li>
 * </ul>
 */
public class EditorControlFolderSelecter extends EditorControlFileOrFolderSelecterAbstract {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(EditorControlFolderSelecter.class.getName());
  
  /**
   * {@code Button} to activate the {@code DirectoryChooser}.
   */
  private Button folderChooserButton;
  
  /**
   * The {@code DirectoryChooser} to select a directory.
   */
  private DirectoryChooser directoryChooser = null;
  
  /**
   * The {@code File} (in this case a folder)  selected using the {@code directoryChooser}.
   * <p>
   * This value is used by the methods called by {@code ociHandleNewUserInput()}.
   */
  private File folderSelectedByDirectoryChooser = null;

  /**
   * The text shown on the button to call up a FolderChooser
   */
  private String folderChooserButtonText;

  /**
   * If not null, this text will be used as Tooltip for the button to call up a FolderChooser.
   */
  private String folderChooserButtonToolTipText;
  
  
  /**
   * Get an {@code EditorControlFolderSelecter} instance.
   * 
   * @param customization the GUI customization
   * @param textFieldWidth Width of the TextField (in pixels). If this value is -1, the default width is used.
   * @param textFiedlToolTipText if not null, this text will be used as Tooltip for the TextField
   * @param folderChooserButtonText the text shown on the button to call up a FolderChooser (may not be null)
   * @param folderChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a FolderChooser.
   * @param folderChooserTitle title for the FolderChooser (may not be null)
   * @param optional Indication of whether the control is optional (if true) or mandatory.
   */
  public static EditorControlFolderSelecter newInstance(CustomizationFx customization, int textFieldWidth, String textFiedlToolTipText,
      String folderChooserButtonText, String folderChooserButtonToolTipText, String folderChooserTitle, boolean optional) {
    EditorControlFolderSelecter editorControlFolderSelecter = new EditorControlFolderSelecter(customization, textFieldWidth, textFiedlToolTipText,
        folderChooserButtonText, folderChooserButtonToolTipText, folderChooserTitle, optional);
    editorControlFolderSelecter.performInitialization();
    
    return editorControlFolderSelecter;
  }
    
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param textFieldWidth Width of the TextField (in pixels). If this value is -1, the default width is used.
   * @param textFieldToolTipText if not null, this text will be used as Tooltip for the TextField
   * @param folderChooserButtonText the text shown on the button to call up a DirectoryChooser (may not be null)
   * @param folderChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a DirectoryChooser.
   * @param directoryChooserTitle title for the DirectoryChooser (may not be null)
   * @param optional Indication of whether the control is optional (if true) or mandatory.
   */
  private EditorControlFolderSelecter(CustomizationFx customization, int textFieldWidth, String textFieldToolTipText,
      String folderChooserButtonText, String folderChooserButtonToolTipText, String directoryChooserTitle, boolean optional) {
    super(customization, textFieldWidth, textFieldToolTipText, directoryChooserTitle, optional);
    
    this.folderChooserButtonText = folderChooserButtonText;
    this.folderChooserButtonToolTipText = folderChooserButtonToolTipText;
  }
  
  @Override
  public void createControls() {
    super.createControls();

    folderChooserButton = componentFactory.createButton(folderChooserButtonText, folderChooserButtonToolTipText);
    componentFactory.customizeButton(folderChooserButton);
    folderChooserButton.setOnAction(actionEvent -> handleFolderChooserButtonPressed());
  }
  

  /**
   * Get the Button for calling up a FolderChooser.
   * 
   * @return the Button for calling up a FolderChooser
   */
  public Button getFolderChooserButton() {
    return folderChooserButton;
  }
  
  /**
   * Handle the fact the the {@code folderChooserButton} is pressed.
   * <p>
   * If the {@code directoryChooser} hasn't been created yet:
   * <ul>
   * <li>The {@code directoryChooser} is created<./li>
   * <li>Its title is set to {@code fileChooserTitle}.</li>
   * <li>Any extension filters, defined by {@code extensionFilters}, are added.</li>
   * <li>If there is a 'selected extension filter' defined, via {@code selectedExtensionFilter}, this filter is selected.</li>
   * </ul>
   * 
   */
  private void handleFolderChooserButtonPressed() {
    if (directoryChooser == null) {
      directoryChooser = componentFactory.createDirectoryChooser(fileOrFolderChooserTitle);
    }
    
    if (getValue() != null) {  // If there is a valid File value.
      directoryChooser.setInitialDirectory(getValue());
    } else if (initialFolderSupplier != null) {
      String initialFolderName = initialFolderSupplier.get();
      if (initialFolderName != null) {
        File initialFolder = new File(initialFolderName);
        if (initialFolder.exists()) {
          directoryChooser.setInitialDirectory(initialFolder);
        }
      }
    }
    
    folderSelectedByDirectoryChooser = directoryChooser.showDialog(null);

    if (folderSelectedByDirectoryChooser != null) {
      handleNewUserInput(directoryChooser);
    }
  }

  /**
   * @InheritDoc
   * <p>
   */
  @Override
  public boolean determineFilledIn(Object source) {
    if (source == pathTextField) {
      return (pathTextField.textProperty().get() != null  &&  !pathTextField.textProperty().get().isEmpty())  ||
          folderSelectedByDirectoryChooser != null;
    } else {
      return folderSelectedByDirectoryChooser != null;
    }
  }

  /**
   * @InheritDoc
   */
  @Override
  public File determineValue(Object source) {
    File file = null;
    
    if (source == directoryChooser) {
      file = folderSelectedByDirectoryChooser;
    } else {
      file = new File(addPrefixIfSet(pathTextField.textProperty().get()));
    }
    
    return file;
  }


  @Override
  protected void updateNonSourceControls(Object source) {
    if (source != pathTextField) {
      setPathTextFieldText();
    }
  }
  
}
