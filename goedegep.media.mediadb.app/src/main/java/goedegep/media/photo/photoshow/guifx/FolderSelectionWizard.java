package goedegep.media.photo.photoshow.guifx;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.util.file.FileUtils;
import goedegep.util.string.StringUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * This class provides a photo show folder selection wizard.
 * <p>
 * In the wizard, the user can choose (via a DirectoryChooser) a folder, which should contain sub folders with photos.
 * Upon selection of a folder, the sub folders (whose names are not in the 'ignore folders') are determined and shown.
 * These sub folders are the photo folders.
 */
public class FolderSelectionWizard extends Dialog<ButtonType> {
  private final static Logger LOGGER = Logger.getLogger(FolderSelectionWizard.class.getName());
  private final static String NEWLINE = System.getProperty("line.separator");
  
  private final static String WINDOW_TITLE = "Photo folder selection wizard";
  
  private ComponentFactoryFx componentFactory;

  // GUI components
  private ObjectControlFolderSelecter folderSelecter;
  private TextArea photoFoldersArea;
  private Button okButton;    // this will only be enabled if photoFolders is not empty.
  
  // State information;
//  private BooleanProperty selectionValidProperty;
  private String ignoreFolders = null;
  private List<Path> photoFolders = null;

  /**
   * Constructor.
   * 
   * @param customization the window customization
   * @param ownerWindow the window which owns (created) this wizard.
   * @param initiallySelectedFolder the initially selected main photo folder
   * @param initialIgnoreFolders the initial, comma separated, list of folders to be ignored
   */
  public FolderSelectionWizard(CustomizationFx customization, Stage ownerWindow, String initiallySelectedFolder, String initialIgnoreFolders) {
    if (initialIgnoreFolders != null) {
      ignoreFolders = initialIgnoreFolders;
    } else {
      ignoreFolders = "";
    }
    
    setTitle(WINDOW_TITLE);
    
    initOwner(ownerWindow);

    componentFactory = customization.getComponentFactoryFx();
    
    createGUI(initiallySelectedFolder);
    setResizable(true);
  }
  
  /**
   * Get the selected main folder.
   * 
   * @return the selected main folder.
   */
  public String getSelectedFolder() {
    if (folderSelecter.isValid()) {
      return folderSelecter.getAbsolutePath();
    } else {
      return null;
    }
  }
  
  /**
   * Get the comma separated list of folders to be ignored, when determining the photo folders.
   * 
   * @return the list of folders to be ignored
   */
  public String getIgnoreFolders() {
    return ignoreFolders;
  }
  
  /**
   * Get the list of photo folders.
   * 
   * @return the list of photo folders.
   */
  public List<Path> getPhotoFolders() {
    if (photoFolders != null) {
      return photoFolders;
    } else {
      return new ArrayList<Path>();
    }
  }
  
  /*
   * Create the GUI.
   */
  private void createGUI(String initiallySelectedFolder) {
    setHeaderText("Select the folder of which the sub folders contain the photos.");
    
    GridPane wizardPanel = componentFactory.createGridPane();
    
    // Row 0: folder selection; label, textfield and button to start directory chooser.
    Label folderNameLabel = componentFactory.createLabel("Photo folder:");
    wizardPanel.add(folderNameLabel, 0, 0);
    
    folderSelecter = componentFactory.createFolderSelecter(400, "Currently selected folder",
        "Choose folder", "Select photo folder via a file chooser", "Select the folder with photos", false);
    folderSelecter.setInitialFolderProvider(() -> initiallySelectedFolder);
    
    Node folderName = folderSelecter.getControl();
    folderSelecter.addListener((observable) -> {
      handleNewPhotoFolderSelected(folderSelecter.isValid(), folderSelecter.getAbsolutePath());      
    });
    wizardPanel.add(folderName, 1, 0);
    
    Button folderChooserButton = folderSelecter.getFolderChooserButton();
    wizardPanel.add(folderChooserButton, 2, 0);
    
//    selectionValidProperty = folderSelecter.ocValidProperty();
    
    // Row 1: folders to be ignored; label, textfield
    Label ignoreFoldersLabel = componentFactory.createLabel("Sub-folders to be ignored:");
    wizardPanel.add(ignoreFoldersLabel, 0, 1);
    
    TextField ignoreFolderTextField = componentFactory.createTextField(ignoreFolders, 200, "a comma separated list of sub-folders to be ignored");
    ignoreFolderTextField.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        ignoreFolders = ignoreFolderTextField.getText();
      }
      
    });
    wizardPanel.add(ignoreFolderTextField, 1, 1);
    
    // Row 2: Actual folders; label, TextArea
    Label photoFoldersLabel = componentFactory.createLabel("Photo folders:");
    wizardPanel.add(photoFoldersLabel, 0, 2);
    
    photoFoldersArea = componentFactory.createTextArea();
    photoFoldersArea.setEditable(false);
    wizardPanel.add(photoFoldersArea, 1, 2);
        
    getDialogPane().setContent(wizardPanel);

    getDialogPane().getButtonTypes().add(ButtonType.OK);
    getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    
    okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
    
    handleNewPhotoFolderSelected(folderSelecter.isValid(), folderSelecter.getAbsolutePath());
  }
  
  /**
   * Handle any change in the folder selection text.
   * <p>
   * If the selection is valid:
   * <ul>
   * <li>
   * The set of photo folders is determined (see {@link #determinePhotoFolders(String)}.
   * </li>
   * <li>
   * These folders are shown in the {@link #photoFoldersArea}.
   * </li>
   * <li>
   * The OK button is enabled.
   * </li>
   * </ul>
   * Otherwise:
   * <ul>
   * <li>
   * The {@link #photoFoldersArea} is cleared.
   * </li>
   * <li>
   * The OK button is disabled.
   * </li>
   * </ul>
   * 
   * @param selectionIsValid If true, <code>selectedFolder</code> is the absolute path of an existing folder.
   * @param selectedFolder the folder under which the photo folders are located.
   */
  private void handleNewPhotoFolderSelected(Boolean selectionIsValid, String selectedFolder) {
    if (selectionIsValid) {
      photoFolders = determinePhotoFolders(selectedFolder);
    } else if (photoFolders != null) {
      photoFolders.clear();
    }
    
    okButton.setDisable(photoFolders == null  ||  photoFolders.isEmpty());
    
    StringBuilder buf = new StringBuilder();
    if (photoFolders != null) {
      boolean first = true;
      for (Path photoFolder: photoFolders) {
        if (first) {
          first = false;
        } else {
          buf.append(NEWLINE);
        }
        buf.append(photoFolder.toString());
      }
    }
    photoFoldersArea.setText(buf.toString());
  }
 
  
  /**
   * Determine the list of photo folders to use.
   * <p>
   * These are all folders below the specified <code>mainPhotoFolder</code>, whose names don't occur in the
   * {@link #ignoreFolders}.
   * 
   * @param mainPhotoFolder the folder below which the photo folders are located.
   * @return the list of photo folders
   */
  private List<Path> determinePhotoFolders(String mainPhotoFolder) {
    LOGGER.info("=>");
    List<Path> pictureFolders = new ArrayList<>();
    boolean mainPhotoFolderHasPicture = false;
    
    List<String> ignoreFoldersAsList = StringUtil.semicolonSeparatedValuesToListOfValues(ignoreFolders);

    Path mainPhotosFolderPath = Paths.get(mainPhotoFolder);
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(mainPhotosFolderPath)) {
      for (Path checkDirectory: stream) {
        LOGGER.fine("Handling: " + checkDirectory.toAbsolutePath().toString());
        if (Files.isDirectory(checkDirectory)) {
          String directoryName = checkDirectory.getFileName().toString();
          LOGGER.fine("directoryName: " + directoryName);
          if (!ignoreFoldersAsList.contains(directoryName)) {
            pictureFolders.add(checkDirectory);
          }
        } else {
          String fileName = checkDirectory.toAbsolutePath().toString();
          FileUtils.isPictureFile(fileName);
          mainPhotoFolderHasPicture = true;
        }
      }

    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    if (pictureFolders.isEmpty()  &&  mainPhotoFolderHasPicture) {
      pictureFolders.add(Paths.get(mainPhotoFolder));
    }
    
    LOGGER.info("<=");
    return pictureFolders;
  }
}
