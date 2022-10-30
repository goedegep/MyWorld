package goedegep.jfx.controls;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import goedegep.util.file.FileUtils;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * This class provides the common functionality for the FileSelecter and FolderSelecter.
 */
public abstract class FileOrFolderSelecterAbstract implements ObjectControl<String> {
  
  /**
   * Field to handle {@code isOptional()} in {@code ObjectControl}.
   */
  private boolean isOptional = false;
  
  /**
   * TextField to show and edit the currently selected file or folder.
   */
  private TextField pathTextField;
  
  /**
   * Property to realize {@code isFilledIn()} in {@code ObjectControl}.
   */
  private BooleanProperty isFilledIn = new SimpleBooleanProperty();
  
  /**
   * Property to realize {@code isValid()} in {@code ObjectControl}.
   */
  private BooleanProperty isValid = new SimpleBooleanProperty();
  
  /**
   * Property to realize {@code objectValue()} in {@code ObjectControl}.
   * Note: it is not possible to use the textProperty of the pathTextField, as this is a StringProperty instead of ObjectProperty<String>.
   * So this property only reflects the value of the textProperty of the pathTextField.
   */
  private ObjectProperty<String> objectValue = new SimpleObjectProperty<>();
  
  /**
   * List of InvalidationListeners.
   */
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
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
  protected FileOrFolderSelecterAbstract(int textFieldWidth, String textFieldToolTipText) {
    
    pathTextField = new TextField();
    
    if (textFieldWidth != -1) {
      pathTextField.setPrefWidth(textFieldWidth);
    }
    
    if (textFieldToolTipText != null) {
      pathTextField.setTooltip(new Tooltip(textFieldToolTipText));
    }
    
//    getPathTextField().textProperty().bind(objectValue);
    objectValue.bind(pathTextField.textProperty());
    
    isValid.set(false);
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
  public TextField getPathTextField() {
    return pathTextField;
  }
  
  /**
   * @InheritDoc
   */
  @Override
  public boolean isOptional() {
    return isOptional;
  }

  /**
   * @InheritDoc
   */
  @Override
  public ObjectProperty<String> objectValue() {
    return objectValue;
  }

  /**
   * @InheritDoc
   */
  @Override
  public boolean getIsValid(StringBuilder errorMessageBuffer) {
    return isValid.getValue();
  }

  /**
   * @InheritDoc
   */
  @Override
  public BooleanProperty isValid() {
    return isValid;
  }

  /**
   * @InheritDoc
   */
  @Override
  public BooleanProperty isFilledIn() {
    return isFilledIn;
  }  

  /**
   * @InheritDoc
   */
  @Override
  public boolean getIsFilledIn() {
    return (objectValue.get() != null) && !objectValue.get().isEmpty();
  }

  /**
   * @InheritDoc
   */
  @Override
  public String getObjectValue() {
    return objectValue.get();
  }

  /**
   * @InheritDoc
   */
  @Override
  public void setObjectValue(String filePathText) {
    
    pathTextField.textProperty().set(filePathText);
  }
  
  /**
   * @InheritDoc
   */
  @Override
  public String getId() {
    return pathTextField.getId();
  }
  
  /**
   * @InheritDoc
   */
  public void setId(String id) {
    pathTextField.setId(id);
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
   * @InheritDoc
   */
  @Override
  public void addListener(InvalidationListener listener) {
    invalidationListeners.add(listener);    
  }

  /**
   * @InheritDoc
   */
  @Override
  public void removeListener(InvalidationListener listener) {
    invalidationListeners.remove(listener);    
  }
  
  /**
   * Notify the <code>invalidationListeners</code> that something has changed.
   */
  protected void notifyListeners() {
    for (InvalidationListener invalidationListener: invalidationListeners) {
      invalidationListener.invalidated(this);
    }
  }
}
