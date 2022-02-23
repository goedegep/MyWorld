package goedegep.jfx.controls;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
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
   */
  private ObjectProperty<String> objectValue = new SimpleObjectProperty<>();
  
  /**
   * List of InvalidationListeners.
   */
  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  
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
    
    objectValue.bind(getPathTextField().textProperty());
    
    isValid.set(false);
 }

  /**
   * Get the TextField to show and edit the currently selected file or folder.
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
    return !pathTextField.getText().isEmpty();
  }

  /**
   * @InheritDoc
   */
  @Override
  public String getObjectValue() {
    return pathTextField.getText();
  }

  /**
   * @InheritDoc
   */
  @Override
  public void setObjectValue(String filePathText) {
    pathTextField.setText(filePathText);
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
