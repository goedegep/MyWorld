package goedegep.jfx.objectcontrols;

import java.io.File;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.DirectoryChooser;

/**
 * This class provides a related set of controls (and a property) to select a folder.
 * <ul>
 * <li>
 * A boolean property which indicates whether a valid folder is selected.
 * </li>
 * <li>
 * A TextField which shows the currently selected folder path and in which the folder path can be entered.<br/>
 * If the boolean property indicates that a valid folder is selected, the text of this text field provides the value.<br/>
 * If the text in the text field is not a valid folder path, the text is shown in red.
 * </li>
 * <li>
 * A Button to call up a DirectoryChooser
 * </li>
 * </ul>
 * By providing separate controls (instead of e.g. a Node containing the controls) there is complete freedom in using the
 * controls in a GUI.
 */
public class ObjectControlFolderSelecter extends ObjectControlFileOrFolderSelecterAbstract {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(ObjectControlFolderSelecter.class.getName());
  
  private Button folderChooserButton;
  private DirectoryChooser directoryChooser = null;
  
  /**
   * Constructor.
   * 
   * @param initiallySelecterFolder The initially selected folder. If not null, this is filled-in in the text field,
   *                                and it is used as initial value for the DirectoryChooser.
   * @param textFieldWidth Width of the TextField (in pixels). If this value is -1, the default width is used.
   * @param textFieldToolTipText if not null, this text will be used as Tooltip for the TextField
   * @param folderChooserButtonText the text shown on the button to call up a DirectoryChooser (may not be null)
   * @param folderChooserButtonToolTipText if not null, this text will be used as Tooltip for the button to call up a DirectoryChooser.
   * @param directoryChooserTitle title for the DirectoryChooser (may not be null)
   */
  public ObjectControlFolderSelecter(CustomizationFx customization, String initiallySelecterFolder, int textFieldWidth, String textFieldToolTipText,
      String folderChooserButtonText, String folderChooserButtonToolTipText, String directoryChooserTitle) {
    super(customization, textFieldWidth, textFieldToolTipText);
    
    folderChooserButton = new Button(folderChooserButtonText);
    
    if (folderChooserButtonToolTipText != null) {
      folderChooserButton.setTooltip(new Tooltip(folderChooserButtonToolTipText));
    }
    
    folderChooserButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (directoryChooser == null) {
          directoryChooser = new DirectoryChooser();
          directoryChooser.setTitle(directoryChooserTitle);
        }
        if (ocIsValid()) {
          directoryChooser.setInitialDirectory(new File(ocGetControl().getText()));
        }
        File selectedFolder = directoryChooser.showDialog(null);
        if (selectedFolder != null) {
          // The selected folder is written to the text field, which automatically leads an update of the selectionValidProperty.
          ocGetControl().setText(selectedFolder.getAbsolutePath());
        }
      }
    });
    
    // Do this at the end, so it automatically leads an update of the selectionValidProperty.
    if (initiallySelecterFolder != null) {
      ocSetValue(new File(initiallySelecterFolder));
    }
  }
  
  public String ocGetAbsolutePath() {
    if (value != null) {
      return value.getAbsolutePath();
    } else {
      return null;
    }
  }

  /**
   * Get the Button for calling up a FolderChooser.
   * 
   * @return the Button for calling up a FolderChooser
   */
  public Button getFolderChooserButton() {
    return folderChooserButton;
  }

  @Override
  public void ociSetValue(File value) {
    this.value = value;
    if (directoryChooser != null) {
      directoryChooser.setInitialDirectory(value);
    }
  }
}
