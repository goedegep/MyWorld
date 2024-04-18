package goedegep.media.mediadb.app.guifx;

import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.media.app.MediaRegistry;
import goedegep.media.app.base.MediaAppResourcesFx;
import goedegep.media.musicfolder.MusicFolder;
import goedegep.media.musicfolder.MusicFolderDescription;
import goedegep.media.musicfolder.MusicFolderStructureErrorInfo;
import goedegep.resources.ImageSize;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This is an application window for functionality related to the Music Folder.
 * <p>
 * The window provides the following functionality:
 * <ul>
 * <li>Check for errors in the Music Folder and repair them</li>
 * </ul>
 *
 */
public class MusicFolderWindow extends JfxStage {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(MusicFolderWindow.class.getName());
  private static final String WINDOW_TITLE   = "Music Folder";
  private static final String NEW_LINE = System.getProperty("line.separator");
  
  private ComponentFactoryFx componentFactory;
  private MediaAppResourcesFx appResources;
  private MusicFolder musicFolder;
  private String currentMusicFolder = null;
  private CheckBox verifyStructureCheckBox = null;
  private CheckBox repairStructureErrorsCheckBox = null;
  private TextArea statusTextArea = null;
  
  /**
   * Constructor
   * 
   * @param customization the application customization to be used.
   */
  public MusicFolderWindow(CustomizationFx customization) {
    super(WINDOW_TITLE, customization);
    
    componentFactory = customization.getComponentFactoryFx();
    appResources = (MediaAppResourcesFx) getResources();
    currentMusicFolder = MediaRegistry.musicDirectory;
    
    musicFolder = new MusicFolder();
    
    createGUI();
  }

  /**
   * Create the GUI.
   * <p>
   * The root layout is a BorderPane.<br/>
   * The center provides the main functionality.
   * Layout:<pre>
   * Music folder: <text field showing current folder> [select folder button]
   * "verify structure"        [check box]      "repair problems" [check box]
   * "Synchronize to NAS"      [check box]
   * "Update playlists on NAS" [check box]
   *                                                              [run button]
   * <text area for status report>
   * </pre>
   */
  private void createGUI() {
    
    BorderPane rootLayout = new BorderPane();
    rootLayout.setPadding(new Insets(12.0));
        
    // Add the controls
    VBox controlsAndStatusBox = new VBox(20.0);
    
    // GridPane for the controls
    GridPane grid = componentFactory.createGridPane(10.0, 10.0, 50.0);
    
    Label label;
    Button button;
    
    // First row
    label = componentFactory.createLabel("Music folder:");
    grid.add(label, 1, 0);
    
    ObjectControlFolderSelecter musicFolderSelecter = componentFactory.createFolderSelecter(200, "Currently selected folder",
        "Choose folder", "Select music folder via a file chooser", "Select the folder with all music", false);
    musicFolderSelecter.setInitialFolderProvider(() -> currentMusicFolder);
    Node folderName = musicFolderSelecter.getControl();
    grid.add(folderName, 2, 0);
    
    button = musicFolderSelecter.getFolderChooserButton();
    grid.add(button, 3, 0);
    Button musicFolderInfoButton = componentFactory.createInfoButton("Show information on a Music Folder",
        "Music folder description", appResources.getApplicationImage(ImageSize.SIZE_3),
        () -> MusicFolderDescription.getMusicFolderDescription());    
    grid.add(musicFolderInfoButton, 4, 0);
    
    // Second row
    verifyStructureCheckBox = componentFactory.createCheckBox("Verify structure", true);
    grid.add(verifyStructureCheckBox, 0, 1);
    
    repairStructureErrorsCheckBox = componentFactory.createCheckBox("Repair structure errors", false);
    grid.add(repairStructureErrorsCheckBox, 2, 1);
    
    StringBuilder buf = new StringBuilder();
    buf.append("* Any unexpected Top Level folder is reported");    
    Button verifyRepairInfoButton = componentFactory.createInfoButton("Show information on verifying and repairing a Music Folder",
        "Music folder verify and repair", appResources.getApplicationImage(ImageSize.SIZE_3),
        () -> buf.toString());
    grid.add(verifyRepairInfoButton, 4, 1);    
    
    // Third row
    button = componentFactory.createButton("Run", null);
    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        handleMusicFoldersAction(musicFolderSelecter.getValue().getAbsolutePath());
      }
      
    });
    grid.add(button, 2, 2);
        
    controlsAndStatusBox.getChildren().add(grid);
    
    // Status panel
    statusTextArea = componentFactory.createTextArea();
    statusTextArea.setMinHeight(60);
    statusTextArea.setOpacity(0.5);  // Make the TextArea transparent
    statusTextArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    
    controlsAndStatusBox.getChildren().add(statusTextArea);
    
    rootLayout.setCenter(controlsAndStatusBox);
    
    ImageView musicFolderImageView = new ImageView(appResources.getMusicFolderImage());
    rootLayout.setLeft(musicFolderImageView);

    setScene(new Scene(rootLayout));
  }
  
  /**
   * Perform the actual work.
   * <p>
   * Which actions are performed is related to the values of the check boxes:
   * <ul>
   * <li>
   * verifyStructureCheckBox<br/>
   * The structure of the musicFolder is verified.
   * </li>
   * </ul>
   * @param musicFolderName the folder, on this pc,  with all music.
   */
  private void handleMusicFoldersAction(String musicFolderName) {
    clearStatusPanel();
    
    if (verifyStructureCheckBox.isSelected()) {
      List<MusicFolderStructureErrorInfo> errors = musicFolder.verifyMusicFolderStructure(musicFolderName, repairStructureErrorsCheckBox.isSelected());
      addErrorsToStatusPanel(errors);
    }
    
    addTextToStatusPanel("All done");
  }
  
  /**
   * Clear the content of the status panel.
   */
  private void clearStatusPanel() {
    statusTextArea.setText("");
  }
  
  /**
   * Add text (a String) 
   * @param text to the content of the status panel.
   */
  private void addTextToStatusPanel(String text) {
    StringBuilder buf = new StringBuilder(statusTextArea.getText());
    
    buf.append(text);
    buf.append(NEW_LINE);
    
    statusTextArea.setText(buf.toString());
  }
  
  /**
   * Add errors (of type {@link MusicFolderStructureErrorInfo}) as text to the content of the status panel.
   * 
   * @param errors the list of errors to be added.
   */
  private void addErrorsToStatusPanel(List<MusicFolderStructureErrorInfo> errors) {
    StringBuilder buf = new StringBuilder(statusTextArea.getText());
    
    if (errors.size() == 0) {
      buf.append("Structure is OK").append(NEW_LINE);
    } else {
      for (MusicFolderStructureErrorInfo error: errors) {
        switch (error.getErrorCode()) {
        case UNEXPECTED_FILE:
          buf.append("Found an unexpected file. File: '").append(error.getFile()).append("', in folder '").append(error.getFolder()).append("'");
          if (error.isRepaired()) {
            buf.append(" (repaired)");
          }
          break;
          
        case FOUND_WINDOWS_MEDIA_PLAYER_FILE:
          buf.append("Windows Media Player file found. File: '" + error.getFile() + "', in folder '" + error.getFolder() + "'");
          if (error.isRepaired()) {
            buf.append(" (repaired)");
          }
          break;
          
        case FOUND_NON_AUDIO_FILE:
          buf.append("Found a file which isn't an audio file. File: '" + error.getFile() + "', in folder '" + error.getFolder() + "'");
          if (error.isRepaired()) {
            buf.append(" (repaired)");
          }
          break;
          
        case NO_INDEX_FOLDER_FOUND:
          buf.append("No Main Index Folder was found at the Top Level. Folder: '" + error.getFolder() + "'");
          break;
          
        case UNEXPECTED_FOLDER:
          buf.append("Found an unexpected folder. Folder: '" + error.getFile() + "', in folder '" + error.getFolder() + "'");
          break;
          
        case WRONG_FOLDER_NAME:
          buf.append("Wrong folder name: ");
          buf.append(error.getFolder());
          break;
          
        case WRONG_TRACK_NAME_FORMAT:
          buf.append("Nummer met verkeerde naamgeving: nummer \"");
          buf.append(error.getFile());
          buf.append("\" in map \"");
          buf.append(error.getFolder());
          buf.append("\"");
          break;
          
        case AUDIO_FILE_WITH_MIXED_OR_UPPER_CASE_EXTENSION:
          buf.append("Found an audio file with mixed or upper case extension. File: '" + error.getFile() + "', in folder '" + error.getFolder() + "'");
          if (error.isRepaired()) {
            buf.append(" (repaired)");
          }
          break;
          
        case WAV_FILE_FOUND:
          buf.append("Found a WAV audio file. File: '" + error.getFile() + "', in folder '" + error.getFolder() + "'");
          if (error.isRepaired()) {
            buf.append(" (repaired)");
          }
          break;
          
        case MPEG_4_AUDIO_FILE_FOUND:
          buf.append("Found an MPEG 4 Audio file. File: '" + error.getFile() + "', in folder '" + error.getFolder() + "'");
          break;
          
        case WINDOWS_MEDIA_AUDIO_FILE_FOUND:
          buf.append("Found a Windows Media Audio file. File: '" + error.getFile() + "', in folder '" + error.getFolder() + "'");
          break;
                    
        default:
          throw new RuntimeException("Unknown ErrorCode: " + error.getErrorCode());
        }
        
        buf.append(NEW_LINE);
      }
    }
    
    statusTextArea.setText(buf.toString());
  }
}
