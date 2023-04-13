package goedegep.jfx.objectcontrols;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import goedegep.jfx.CustomizationFx;
import goedegep.util.PgUtilities;
import goedegep.util.file.FileUtils;
import javafx.scene.control.TextField;

/**
 * This class provides the common functionality for the FileSelecter and FolderSelecter.
 */
public abstract class ObjectControlFileOrFolderSelecterAbstract extends ObjectControlAbstract<File> {
	
  
  /**
   * TextField to show and edit the currently selected file or folder.
   */
  private TextField pathTextField = null;
  
  /**
   * Prefix - The first part of the file or folder path.
   */
  private String prefix = null;
  
  /**
   * Constructor
   * 
   * @param textFieldWidth Value for the width of the TextField. If this value is -1, the default width is used.
   * @param textFieldToolTipText an optional tooltip text for the TextField.
   */
  protected ObjectControlFileOrFolderSelecterAbstract(CustomizationFx customization, int textFieldWidth, String textFieldToolTipText) {
    super(false);
    
    pathTextField = customization.getComponentFactoryFx().createTextField(textFieldWidth, textFieldToolTipText);
    
    pathTextField.textProperty().addListener((o) -> ociHandleNewUserInput());
    pathTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
      if (!newValue)
        ociRedrawValue();
    });
    
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
  public TextField ocGetControl() {
    return pathTextField;
  }

  /**
   * @InheritDoc
   */
  @Override
  public void ocSetValue(File file) {
    ocSetFilename(file != null ? file.getAbsolutePath() : null);
  }
  
  public void ocSetFilename(String filename) {
    if (filename != null) {
      referenceValue = new File(filename);
    } else {
      referenceValue = null;
    }
    pathTextField.textProperty().set(filename);
  }

  /**
   * @InheritDoc
   */
  @Override
  public boolean ociDetermineFilledIn() {
    return pathTextField.textProperty().get() != null  &&  !pathTextField.textProperty().get().isEmpty();
  }

  /**
   * @InheritDoc
   */
  @Override
  public File ociDetermineValue() {
    File file = new File(pathTextField.textProperty().get());
    return file;
  }

  /**
   * @InheritDoc
   * For now no action. For 'open' this could be based on whether the file exists.
   */
  @Override
  public void ociSetErrorFeedback(boolean valid) {
    if (!ocIsValid()) {
      ocGetControl().setStyle("-fx-text-inner-color: red;");
    } else {
      ocGetControl().setStyle("-fx-text-inner-color: black;");
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
  public String ocGetObjectValueAsFormattedText() {
    return value.getAbsolutePath();
  }
  
  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }
  
  protected String addPrefixIfSet(String path) {
    if (prefix != null) {
      File file = new File (getPrefix(), pathTextField.textProperty().get());
      return file.getAbsolutePath();
    } else {
      return path;
    }
  }
  
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
  public String ocGetAbsolutePath() {
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
  public boolean ocIsChanged() {
    return !PgUtilities.equals(ocGetAbsolutePath(), referenceValue != null ? referenceValue.getAbsolutePath() : "null");
  }
}
