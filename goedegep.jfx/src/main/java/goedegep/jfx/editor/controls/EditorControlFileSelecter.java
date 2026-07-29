package goedegep.jfx.editor.controls;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class is an {@code EditorControl} for selecting a file.
 * <p>
 * It offers the following GUI controls:
 * <ul>
 * <li>
 * A TextField which shows the currently selected file path and in which the file path can be entered.<br/>
 * If the text in the text field is not a valid file path, the text is shown in red.
 * </li>
 * <li>
 * A Button to call up a <@link FileChooser>.
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
public class EditorControlFileSelecter extends EditorControlFileOrFolderSelecterAbstract {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(EditorControlFileSelecter.class.getName());

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
   * The text shown on the button to call up a FileChooser
   */
  private String fileChooserButtonText;

  /**
   * If not null, this text will be used as Tooltip for the button to call up a FileChooser.
   */
  private String fileChooserButtonToolTipText;
  
    
  /**
   * Constructor with a {@code Builder}.
   * 
   * @param builder the {@code Builder} to set all properties for this {@code EditorControlFileSelecter}.
   */
  public EditorControlFileSelecter(Builder builder) {
    super(builder);
    
    this.fileChooserButtonText = builder.fileChooserButtonText;
    this.fileChooserButtonToolTipText = builder.fileChooserButtonToolTipText;
    this.isSaveDialog = builder.isSaveDialog;
    this.extensionFilters = builder.extensionFilters;
    this.selectedExtensionFilter = builder.selectedExtensionFilter;
  }
  
  
  @Override
  public void createControls() {
    super.createControls();

    fileChooserButton = componentFactory.createButton(fileChooserButtonText, fileChooserButtonToolTipText);
    fileChooserButton.setOnAction((_) -> handleFileChooserButtonPressed());
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
      if (folder != null) {
        fileChooser.setInitialDirectory(folder);
      }
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
      handleNewUserInput(fileChooser);
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
          fileSelectedByFileChooser != null;
    } else {
      return fileSelectedByFileChooser != null;
    }
  }

  /**
   * @InheritDoc
   */
  @Override
  public File determineValue(Object source) {
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
  protected void updateNonSourceControls(Object source) {
    if (source != pathTextField) {
      setPathTextFieldText();
    }
  }
  
  
  /**
   * Builder class to set all properties for an {@code EditorControlFileSelecter}.
   */
  public static class Builder extends EditorControlFileOrFolderSelecterAbstract.Builder {

    /**
     * The text shown on the button to call up a FileChooser
     */
    private String fileChooserButtonText;

    /**
     * If not null, this text will be used as Tooltip for the button to call up a FileChooser.
     */
    private String fileChooserButtonToolTipText;

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
     * Constructor with mandatory arguments.
     * 
     * @param customization The GUI customization
     * @param id The unique id of the ObjectControl (may not be null).
     */
    public Builder(CustomizationFx customization, String id) {
      super(customization, id);
      Objects.requireNonNull(id, "The id may not be null");
    }
        
    @Override
    public Builder setOptional(boolean optional) {
      super.setOptional(optional);
      
      return this;
    }
    
    @Override
    public Builder setLabelBaseText(String labelBaseText) {
      super.setLabelBaseText(labelBaseText);

      return this;
    }
    
    @Override
    public Builder setFileOrFolderChooserTitle(String fileOrFolderChooserTitle) {
      super.setFileOrFolderChooserTitle(fileOrFolderChooserTitle);
      
      return this;
    }
    
    @Override
    public Builder setTextFieldWidth(double textFieldWidth) {
      super.setTextFieldWidth(textFieldWidth);
      
      return this;
    }
    
    @Override
    public Builder setTextFieldToolTipText(String textFieldToolTipText) {
      super.setTextFieldToolTipText(textFieldToolTipText);
      
      return this;
    }
    
    /**
     * Set the text shown on the button to call up a FileChooser.
     * 
     * @param fileChooserButtonText the text shown on the button to call up a FileChooser (may not be null)
     * @return this
     */
    public Builder setFileChooserButtonText(String fileChooserButtonText) {
      this.fileChooserButtonText = fileChooserButtonText;
      
      return this;
    }
    
    /**
     * Set the Tooltip text for the button to call up a FileChooser.
     * 
     * @param fileChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a FileChooser.
     * @return this
     */
    public Builder setFileChooserButtonToolTipText(String fileChooserButtonToolTipText) {
      this.fileChooserButtonToolTipText = fileChooserButtonToolTipText;
      
      return this;
    }
    
    @Override
    public Builder setInitialFolderSupplier(Supplier<String> initialFolderSupplier) {
      super.setInitialFolderSupplier(initialFolderSupplier);
      
      return this;
    }
    
    @Override
    public Builder setPrefix(String prefix) {
      super.setPrefix(prefix);

      return this;
    }

    /**
     * Specify whether this is a 'file open' or 'save as ..' dialog.
     * 
     * @param isSaveDialog if true, it is a 'save as ..' dialog, else it is a 'file open' dialog.
     */
    public Builder setOpenOrSaveDialog(boolean isSaveDialog) {
      this.isSaveDialog = isSaveDialog;
      
      return this;
    }

    /**
     * Add a file type, being a file extension with a related description.
     * <p>
     * @param fileTypeExtension the file name extension for this type, e.g. ".pdf".
     * @param fileTypeDescription a description of this file type, e.g. "Portable Data Format".
     * @param setAsSelected if true, this file type will initially be selected in the {@code FileChooser}.
     *        The last call to this method, with this parameter set to true, determines the initially selected file type.
     */
    public Builder addFileType(String fileTypeExtension, String fileTypeDescription, boolean setAsSelected) {
      ExtensionFilter extensionFilter = new ExtensionFilter(fileTypeDescription, "*" + fileTypeExtension);
      extensionFilters.add(extensionFilter);

      if (setAsSelected) {
        selectedExtensionFilter = extensionFilter;
      }
      
      return this;
    }
        
    /**
     * Create the {@code EditorControlFileSelecter}.
     * 
     * @return the {@code EditorControlFileSelecter}.
     */
    public EditorControlFileSelecter build() {
      EditorControlFileSelecter editorControlFileSelecter = new EditorControlFileSelecter(this);
      editorControlFileSelecter.performInitialization();
      
      return editorControlFileSelecter;
    }
    
  }

}
