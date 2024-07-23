package goedegep.jfx.objectcontrols;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.util.PgUtilities;
import goedegep.util.file.FileUtils;
import javafx.scene.control.TextField;

/**
 * This class provides the common functionality for the FileSelecter and FolderSelecter.
 * <p>
 * This common functionality consists of:
 * <h4>An initial folder supplier</h4>
 * 
 * 
 * 
 */
public abstract class ObjectControlFileOrFolderSelecterAbstract extends ObjectControlTemplate<File> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlFileOrFolderSelecterAbstract.class.getName());
	
  
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
   * @param textFieldWidth Value for the width of the TextField. If this value is -1, the default width is used.
   * @param textFieldToolTipText an optional tooltip text for the TextField.
   * @param isOptional Indication of whether the control is optional (if true) or mandatory.
   */
  protected ObjectControlFileOrFolderSelecterAbstract(CustomizationFx customization, int textFieldWidth, String textFieldToolTipText, boolean isOptional) {
    super(isOptional);
    
    pathTextField = customization.getComponentFactoryFx().createTextField(textFieldWidth, textFieldToolTipText);
    
    pathTextField.textProperty().addListener((o) -> {
      LOGGER.info("Text property changed, id is: " + getId());
      ociHandleNewUserInput(pathTextField);
    });
    pathTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
      if (!newValue)
        ociRedrawValue();
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
   * For now no action. Could change to showing absolute path, or path relative to prefix (if set).
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
    return value.getAbsolutePath();
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
      setValue(null);
    } else {
      pathName = addPrefixIfSet(pathName);
      File file = new File(pathName);
      setValue(file);
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
//      File file = new File (getPrefix(), pathTextField.textProperty().get());
//      return file.getAbsolutePath();
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
    return value != null ? Paths.get(value.getAbsolutePath()) : null;
  }
  
  /**
   * Get the absolute path for the current value.
   * 
   * @return the absolute path for the current value.
   */
  public String getAbsolutePath() {
    if (value != null) {
      return value.getAbsolutePath();
    } else {
      return null;
    }
  }
  
  /**
   * Get the absolute path for the reference value.
   * 
   * @return the absolute path for the reference value.
   */
  public String getReferenceAbsolutePath() {
    if (referenceValue != null) {
      return referenceValue.getAbsolutePath();
    } else {
      return null;
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isChanged() {
    return !PgUtilities.equals(getAbsolutePath(), getReferenceAbsolutePath());
  }
}
