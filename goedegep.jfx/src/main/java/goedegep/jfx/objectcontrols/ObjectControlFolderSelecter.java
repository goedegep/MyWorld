package goedegep.jfx.objectcontrols;

import java.io.File;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;

/**
 * This class is an {@code ObjectControl} for selecting a folder.
 * <p>
 * It offers the following GUI controls:
 * <ul>
 * <li>
 * A TextField which shows the currently selected folder path and in which the file path can be entered.<br/>
 * If the text in the text field is not a valid folder path, the text is shown in red.
 * </li>
 * <li>
 * A Button to call up a FolderChooser
 * </li>
 * <li>
 * A status indicator node.
 * </li>
 * </ul>
 * By providing separate controls (instead of e.g. a Node containing the controls) there is complete freedom in using the
 * controls in a GUI.<br/>
 * 
 * <b>Prefix</b><br/>
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
public class ObjectControlFolderSelecter extends ObjectControlFileOrFolderSelecterAbstract {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(ObjectControlFolderSelecter.class.getName());
  
  private ComponentFactoryFx componentFactory;
  
  /**
   * The title of the DirectoryChooser
   */
  private String directoryChooserTitle = null;
  
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
   * A {@link Supplier} to provide the initial folder for the {@code directoryChooser}.
   */
  private Supplier<String> initialFolderSupplier = null;
  
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param textFieldWidth Width of the TextField (in pixels). If this value is -1, the default width is used.
   * @param textFieldToolTipText if not null, this text will be used as Tooltip for the TextField
   * @param folderChooserButtonText the text shown on the button to call up a DirectoryChooser (may not be null)
   * @param folderChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a DirectoryChooser.
   * @param directoryChooserTitle title for the DirectoryChooser (may not be null)
   * @param isOptional Indication of whether the control is optional (if true) or mandatory.
   */
  public ObjectControlFolderSelecter(CustomizationFx customization, int textFieldWidth, String textFieldToolTipText,
      String folderChooserButtonText, String folderChooserButtonToolTipText, String directoryChooserTitle, boolean isOptional) {
    super(customization, textFieldWidth, textFieldToolTipText, isOptional);
    
    this.directoryChooserTitle = directoryChooserTitle;
    
    componentFactory = customization.getComponentFactoryFx();
    
    folderChooserButton = componentFactory.createButton(folderChooserButtonText, folderChooserButtonToolTipText);    
    folderChooserButton.setOnAction(actionEvent -> handleFileChooserButtonPressed());
    
//    // Do this at the end, so it automatically leads an update of the selectionValidProperty.
//    if (initiallySelecterFolder != null) {
//      ocSetValue(new File(initiallySelecterFolder));
//    }
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
   * <li></li>
   * </ul>
   * 
   */
  private void handleFileChooserButtonPressed() {
    if (directoryChooser == null) {
      directoryChooser = componentFactory.createDirectoryChooser(directoryChooserTitle);
    }
    
    if (value != null) {  // If there is a valid file value.
      directoryChooser.setInitialDirectory(value);
    } else if (initialFolderSupplier != null) {  // If there is a initialFolderProvider.
      String initialFolderName = initialFolderSupplier.get();
      if (initialFolderName != null) {
        File initialFolder = new File(initialFolderName);
        directoryChooser.setInitialDirectory(initialFolder);
      }
    }
    
    folderSelectedByDirectoryChooser = directoryChooser.showDialog(null);

    if (folderSelectedByDirectoryChooser != null) {
      ociHandleNewUserInput(directoryChooser);
    }
  }

  /**
   * @InheritDoc
   * <p>
   */
  @Override
  public boolean ociDetermineFilledIn() {
    return ((pathTextField.textProperty().get() != null  &&  !pathTextField.textProperty().get().isEmpty())  ||
        folderSelectedByDirectoryChooser != null);
  }

  /**
   * @InheritDoc
   */
  @Override
  public File ociDetermineValue(Object source) {
    File file = null;
    
    if (source == directoryChooser) {
      file = folderSelectedByDirectoryChooser;
    } else {
      file = new File(addPrefixIfSet(pathTextField.textProperty().get()));
    }
    
    return file;
  }
  
}
