package goedegep.jfx.objectcontrols;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import goedegep.util.file.FileUtils;
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
public abstract class ObjectControlFileOrFolderSelecterAbstract implements ObjectControl<String> {
	

  /**
   * Indication of whether the control is optional (if true) or mandatory.
   */
//  private BooleanProperty ocOptionalProperty = new SimpleBooleanProperty(false);

  /**
   * Indication of whether the control is filled-in or not.
   */
  protected boolean filledIn;
//  private BooleanProperty ocFilledInProperty = new SimpleBooleanProperty(true);

  /**
   * Indication of whether the control has a valid value or not.
   */
//  private BooleanProperty ocValidProperty = new SimpleBooleanProperty(true);
  protected boolean valid;

  /**
   * The current value.
   * Note: it is not possible to use the textProperty of the pathTextField, as this is a StringProperty instead of ObjectProperty<String>.
   * So this property only reflects the value of the textProperty of the pathTextField.
   */
  private ObjectProperty<String> ocValueProperty = new SimpleObjectProperty<>();

  
  /**
   * TextField to show and edit the currently selected file or folder.
   */
  private TextField pathTextField;
  
  
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
  protected ObjectControlFileOrFolderSelecterAbstract(int textFieldWidth, String textFieldToolTipText) {
    
    pathTextField = new TextField();
    
    if (textFieldWidth != -1) {
      pathTextField.setPrefWidth(textFieldWidth);
    }
    
    if (textFieldToolTipText != null) {
      pathTextField.setTooltip(new Tooltip(textFieldToolTipText));
    }
    
    ocValueProperty.bind(pathTextField.textProperty());
    
    valid = false;
//    ocValidProperty.set(false);
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

//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public BooleanProperty ocOptionalProperty() {
//    return ocOptionalProperty;
//  }
//
//  /**
//   * @InheritDoc
//   */
//  @Override
//  public ObjectProperty<String> ocValueProperty() {
//    return ocValueProperty;
//  }
//
//  /**
//   * @InheritDoc
//   */
//  @Override
//  public BooleanProperty ocValidProperty() {
//    return ocValidProperty;
//  }
//
//  /**
//   * @InheritDoc
//   */
//  @Override
//  public BooleanProperty ocFilledInProperty() {
//    return ocFilledInProperty;
//  }  

  /**
   * @InheritDoc
   */
  @Override
  public void ocSetValue(String filePathText) {
    pathTextField.textProperty().set(filePathText);
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
  public String ociDetermineValue() {
    return pathTextField.textProperty().get();
  }

  /**
   * @InheritDoc
   * For now no action. For 'open' this could be based on whether the file exists.
   */
  @Override
  public void ociSetErrorFeedback(boolean valid) {
  }

  /**
   * @InheritDoc
   * For now no action. Could change to showing absolute path, or path relative to prefix (if set).
   */
  @Override
  public void ociRedrawValue() {
  }

  /**
   * @InheritDoc
   * For now just return the 'value'.
   */
  @Override
  public String ocGetObjectValueAsFormattedText() {
    return pathTextField.textProperty().get();
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
   * {@inheritDoc}
   */
  @Override
  public List<InvalidationListener> ociGetInvalidationListeners() {
    return invalidationListeners;
  }

  @Override
  public boolean ocIsOptional() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean ocIsFilledIn() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean ocIsValid() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String ocGetValue() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void ociSetValue(String value) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void ociSetValid(boolean valid) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void ociSetFilledIn(boolean filledIn) {
    // TODO Auto-generated method stub
    
  }
}
