package goedegep.jfx.objectcontrols;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

import goedegep.jfx.CustomizationFx;
import goedegep.util.PgUtilities;
import goedegep.util.file.FileUtils;
import javafx.scene.control.TextField;

/**
 * This class provides the common functionality for the FileSelecter and FolderSelecter.
 */
public abstract class ObjectControlFileOrFolderSelecterAbstract extends ObjectControlAbstract<File> {
	
  
  /**
   * A {@link Supplier} to provide the initial folder for the {@code fileChooser}.
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
   * Ignore changes on the {@code pathTextField}.
   */
  private boolean ignorePathTextFieldChanges = false;
  
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
    
    pathTextField.textProperty().addListener((o) -> ociHandleNewUserInput(pathTextField));
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
  * {@inheritDoc}
  */
 @Override
 protected void ociHandleNewUserInput(Object source) {
   if (!ignorePathTextFieldChanges) {
     super.ociHandleNewUserInput(source);
   }
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
   * @InheritDoc
   */
  @Override
  public void setValue(File file) {
    ocSetFilename(file != null ? file.getAbsolutePath() : null);
  }
  
  /**
   * Set the value via the name of the file.
   * 
   * @param filename the file name.
   */
  public void ocSetFilename(String filename) {
    if (filename != null) {
      referenceValue = new File(filename);
    } else {
      referenceValue = null;
    }
    ociSetValue(referenceValue);
    setPathTextFieldText();
  }
  
  /**
   * Set the text of the {@code pathTextField}.
   * <p>
   * As we set the text ourselves, the change of the TextField shall not lead to reacting to changes. Therefore {@code ignorePathTextFieldChanges} is set before making the change and reset afterwards.
   * If the {@code prefix} is set, this is stripped from the text.
   */
  protected void setPathTextFieldText() {
    ignorePathTextFieldChanges = true;
    
    File file = getValue();
    if (file != null) {
      String filename = file.getAbsolutePath();
      filename = removePrefixIfSet(filename);    
      pathTextField.textProperty().set(filename);
    } else {
      pathTextField.textProperty().set(null);
    }
    
    ignorePathTextFieldChanges = false;
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
    String filePathText = value != null ? value.getAbsolutePath() : null;
    pathTextField.textProperty().set(filePathText);
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
  public String getPathNameUsingPrefix() {
    return getValue() != null ? addPrefixIfSet(getValue().getAbsolutePath()) : null;
  }
  
  /**
   * Add the {@code prefix}, if set, to a relative path.
   * 
   * @param path the file or folder path to which the {@ code prefix} is to be added.
   * @return {@code path} prepended with the {@code prefix} if it is set, {@code path} otherwise.
   */
  protected String addPrefixIfSet(String path) {
    if (prefix != null) {
      File file = new File (getPrefix(), pathTextField.textProperty().get());
      return file.getAbsolutePath();
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
   * {@inheritDoc}
   */
  @Override
  public boolean isChanged() {
    return !PgUtilities.equals(getAbsolutePath(), referenceValue);
  }
}
