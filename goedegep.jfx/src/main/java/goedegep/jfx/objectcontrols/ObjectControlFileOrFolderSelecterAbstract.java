package goedegep.jfx.objectcontrols;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.util.file.FileUtils;
import javafx.scene.control.TextField;

/**
 * This class provides the common functionality for the FileSelecter and FolderSelecter.
 * <p>
 * This common functionality consists of:
 * <h4>{@link #pathTextField}</h4>
 * The {@code TextField} showing the currently selected file or folder.
 * 
 * <h4>{@link #fileOrFolderChooserTitle}</h4>
 * The title of the {@code FileChooser} or {@code FolderChooser}.
 * 
 * <h4>{@link #initialFolderSupplier}</h4>
 * Upon creating the {@code FileChooser} or {@code FolderChooser} this method, if set,
 * is called to obtain the initial folder name. This is then used to set the initial folder
 * on the {@code FileChooser} or {@code FolderChooser} (by calling {@code setInitialDirectory}).
 * 
 * <h4>{@link prefix}</h4>
 * You can set a prefix, which is a base path for the folder (in which the file is located).
 * 
 */
public abstract class ObjectControlFileOrFolderSelecterAbstract extends ObjectControlTemplate<File> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlFileOrFolderSelecterAbstract.class.getName());
	

  /**
   * The title of the {@code FileChooser} or {@code FolderChooser}.
   */
  protected String fileOrFolderChooserTitle = null;
  
  /**
   * A {@link Supplier} to provide the initial folder for the {@code FileChooser} or {@code FolderChooser}.
   */
  protected Supplier<String> initialFolderSupplier = null;
  
  /**
   * TextField to show and edit the currently selected file or folder.
   */
  protected TextField pathTextField = null;
  
  /**
   * Prefix - The first part of the file or folder path.
   * <p>
   * If this value is null, no Prefix is used.
   */
  private String prefix = null;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param textFieldWidth Value for the width of the TextField. If this value is -1, the default width is used.
   * @param textFieldToolTipText an optional tooltip text for the TextField.
   * @param fileOrFolderChooserTitle The title of the {@code FileChooser} or {@code FolderChooser}.
   * @param isOptional Indication of whether the control is optional (if true) or mandatory.
   */
  protected ObjectControlFileOrFolderSelecterAbstract(
      CustomizationFx customization,
      int textFieldWidth,
      String textFieldToolTipText,
      String fileOrFolderChooserTitle,
      boolean isOptional) {
    super(customization, isOptional);
    
    this.fileOrFolderChooserTitle = fileOrFolderChooserTitle;
    
    pathTextField = customization.getComponentFactoryFx().createTextField(textFieldWidth, textFieldToolTipText);
    
    pathTextField.textProperty().addListener((o) -> {
      ociHandleNewUserInput(pathTextField);
    });
    pathTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
      if (!newValue)
        ociRedrawValue();
    });
    
    setComparator((o1, o2) -> {
      int result = Objects.equals(getAbsolutePath(o1), getAbsolutePath(o2)) ? 0 : -1;
      return result;
    });
 }
  
  /**
   * Set the method for obtaining the initial folder.
   * 
   * @param initialFolderSupplier the method for obtaining the initial folder.
   */
  public void setInitialFolderProvider(Supplier<String> initialFolderSupplier) {
    this.initialFolderSupplier = initialFolderSupplier;
  }

  /**
   * Get the TextField to show and edit the currently selected file or folder.
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
   * </ul>
   * 
   * @return the TextField to show and edit the currently selected file or folder.
   */
  @Override
  public TextField getControl() {
    return pathTextField;
  }
    
  /**
   * Set the text of the {@code pathTextField}.
   * <p>
   * If the {@code prefix} is set, this is stripped from the text.
   */
  protected void setPathTextFieldText() {
    
    File file = getValue();
    if (file != null) {
      String filename = file.getAbsolutePath();
      filename = removePrefixIfSet(filename);    
      pathTextField.textProperty().set(filename);
    } else {
      pathTextField.textProperty().set(null);
    }
    
  }

  /**
   * @InheritDoc
   * For now no action. For 'open' this could be based on whether the file exists.
   */
  @Override
  public void ociSetErrorFeedback(boolean valid) {
    if (!valid) {
      getControl().setStyle("-fx-text-inner-color: red;");
    } else {
      getControl().setStyle("-fx-text-inner-color: black;");
    }
  }

  /**
   * @InheritDoc
   */
  @Override
  public void ociRedrawValue() {
    setPathTextFieldText();
  }

  /**
   * @InheritDoc
   * For now just return the 'value'.
   */
  @Override
  public String getValueAsFormattedText() {
    return getValue().getAbsolutePath();
  }
  
  /**
   * Get the {@link #prefix}.
   * 
   * @return the {@code prefix}.
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * Set the {@link #prefix}.
   * 
   * @param prefix the new {@code prefix} value.
   */
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }
  
  /**
   * Get the path relative to the prefix, if the absolute path starts with the prefix, otherwise it also just returns the absolute path.
   * <p>
   * This method can e.g. be used to store a path relative to some base folder.
   * 
   * @return the path relative to the prefix, if the absolute path starts with the prefix, otherwise it also just returns the absolute path.
   */
  public String getPathNameRelativeToPrefix() {
    return getValue() != null ? removePrefixIfSet(getValue().getAbsolutePath()) : null;
  }
  
  public void setPathNameRelativeToPrefix(String pathName) {
    if (pathName == null) {
      setObject(null);
    } else {
      pathName = addPrefixIfSet(pathName);
      File file = new File(pathName);
      setObject(file);
    }
  }
  
  /**
   * Add the {@code prefix}, if set, to a relative path.
   * 
   * @param path the file or folder path to which the {@ code prefix} is to be added.
   * @return {@code path} prepended with the {@code prefix} if it is set, {@code path} otherwise.
   */
  protected String addPrefixIfSet(String path) {
    if (prefix != null) {
      return FileUtils.createPathNameFromPrefixAndFileName(prefix, path);
    } else {
      return path;
    }
  }
  
  /**
   * Remove the {@code prefix} from a file or folder path.
   * <p>
   * 
   * @param path the file or folder path from which the {@code prefix} is to be removed.
   * @return {@code path} from which {@code prefix} is removed,
   *         if the {@code prefix} is set and {@code path} starts with this {@code prefix}.
   *         Otherwise {@code path} is returned unchanged.
   */
  protected String removePrefixIfSet(String path) {
    if (prefix != null) {
      return FileUtils.getPathRelativeToFolder(prefix, path);
    } else {
      return path;
    }
  }
  
  /**
   * Get the current value as a Path.
   * 
   * @return the current value as a Path.
   */
  public Path ocGetValueAsPath() {
    return getValue() != null ? Paths.get(getValue().getAbsolutePath()) : null;
  }
  
  /**
   * Get the absolute path of a file.
   * This method accepts a null input in which case null is returned.
   * 
   * @param file a {@code File}, which may be null.
   * @return the absolute path of {@code file}, or null is {@code file} is null.
   */
  private String getAbsolutePath(File file) {
    if (file != null) {
      return file.getAbsolutePath();
    } else {
      return null;
    }
    
  }
  
  /**
   * Get the absolute path for the current value.
   * 
   * @return the absolute path for the current value.
   */
  public String getAbsolutePath() {
    return getAbsolutePath(getValue());
  }
  
  /**
   * Get the absolute path for the reference value.
   * 
   * @return the absolute path for the reference value.
   */
  public String getReferenceAbsolutePath() {
    return getAbsolutePath(referenceValue);
  }
  
}
