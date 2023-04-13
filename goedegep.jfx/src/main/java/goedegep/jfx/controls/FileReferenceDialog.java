package goedegep.jfx.controls;

import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import goedegep.types.model.FileReference;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * This class provides a dialog to create a {@link FileReference}.
 * 
 */
public class FileReferenceDialog extends Dialog<ButtonType> {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(FileReferenceDialog.class.getName());
  private final static String WINDOW_TITLE = "Create file reference";
  
  private ComponentFactoryFx componentFactory;
  private FileReference fileReference = null;
  private Node fileName;

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param ownerWindow the <code>Stage</code> which owns this dialog.
   * @param initiallySelectedFolder initial folder for the FileChooser.
   */
  public FileReferenceDialog(CustomizationFx customization, Stage ownerWindow, String initiallySelectedFolder) {
    setTitle(WINDOW_TITLE);
    
    initOwner(ownerWindow);
    
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI(ownerWindow, initiallySelectedFolder);
  }
  
  /**
   * Get the created <code>FileReference</code>.
   * 
   * @return the created <code>FileReference</code> (in case the user pressed the OK button), or null (in case the user pressed the Cancel button).
   */
  public FileReference getFileReference() {
    return fileReference;
  }

  /*
   * Create the GUI.
   */
  private void createGUI(Stage ownerWindow, String initiallySelectedFolder) {
    setHeaderText("Select a file and optionally give it a title");
    
    GridPane wizardPanel = componentFactory.createGridPane();
    
    // Row 0: file selection; label, textfield and button to start file chooser.
    Label fileNameLabel = componentFactory.createLabel("File:");
    wizardPanel.add(fileNameLabel, 0, 0);
    
    ObjectControlFileSelecter fileSelecter = componentFactory.createFileSelecter(initiallySelectedFolder, 400, "Currently selected folder",
        "Choose file", "Select a file via a file chooser", "Select the file");
    
    fileName = fileSelecter.ocGetControl();
    wizardPanel.add(fileName, 1, 0);
    
    Button fileChooserButton = fileSelecter.getFileChooserButton();
    wizardPanel.add(fileChooserButton, 2, 0);
    
    // Row 1: title; label, textfield
    Label titleLabel = componentFactory.createLabel("Title:");
    wizardPanel.add(titleLabel, 0, 1);
    
    TextField titleTextField = componentFactory.createTextField(null, 200, "a title for the file");
    wizardPanel.add(titleTextField, 1, 1);
    
    getDialogPane().setContent(wizardPanel);
    
    getDialogPane().getButtonTypes().add(ButtonType.OK);
    getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
  }
}
