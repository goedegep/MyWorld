package goedegep.media.mediadb.guifx;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.media.common.MediaAppResourcesFx;
import goedegep.media.common.MediaRegistry;
import goedegep.media.musicfolder.MusicFolderDescription;
import goedegep.resources.ImageSize;
import javafx.concurrent.Task;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This is an application window for functionality related to a Dune media player.
 * <p>
 * The window provides the following functionality:
 * <ul>
 * <li>Synchronize the music on the Dune with the Music Folder on PC</li>
 * <li>Check for errors in the Music Folder and repair them</li>
 * </ul>
 *
 */
public class DuneWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(DuneWindow.class.getName());
  private static final String WINDOW_TITLE   = "Dune HD TV-303D synchronization";
  private static final String NEW_LINE = System.getProperty("line.separator");
  
  private MediaRegistry mediaRegistry;
  private MediaAppResourcesFx appResources;
  private String currentMusicFolder = null;
  private String currentDuneMusicFolderPath = null;
  private String currentDunePlaylistsFolderPath = null;

  private CheckBox synchronizeToDuneCheckBox = null;
  private ObjectControlFolderSelecter duneMusicFolderSelecter;
  private CheckBox updatePlayListsCheckBox = null;
  private ObjectControlFolderSelecter playListFolderPathSelecter;
  private Button runButton = null;
  private Button abortButton = null;
  private TextArea statusTextArea = null;
  
  private Task<?> currentTask = null;
  
  /**
   * Constructor
   * 
   * @param customization the application customization to be used.
   */
  public DuneWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
    
    mediaRegistry = MediaRegistry.getInstance();
    appResources = (MediaAppResourcesFx) getResources();
    currentMusicFolder = mediaRegistry.getMusicDirectory();
    currentDuneMusicFolderPath = mediaRegistry.getDuneMusicFolderPath();
    currentDunePlaylistsFolderPath = mediaRegistry.getDunePlaylistsFolderPath();
    
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
    synchronizeToDuneCheckBox = componentFactory.createCheckBox("Synchronize to Dune", false);
    synchronizeToDuneCheckBox.selectedProperty().addListener(_ -> handleChanges());
    grid.add(synchronizeToDuneCheckBox, 0, 1);
    
    label = componentFactory.createLabel("Dune music folder path:");
    grid.add(label, 1, 1);
    
    duneMusicFolderSelecter = componentFactory.createFolderSelecter(200, "Currently selected Dune music folder path",
        "Choose folder", "Select  Dune music folder path via a file chooser", "Select the music folder on the Dune", false);
    duneMusicFolderSelecter.setInitialFolderProvider(() -> currentDuneMusicFolderPath);
    Node duneFolderPathTextField = duneMusicFolderSelecter.getControl();
    duneMusicFolderSelecter.addListener(_ -> {
      currentDuneMusicFolderPath = duneMusicFolderSelecter.getAbsolutePath();
      handleChanges();
      });
    grid.add(duneFolderPathTextField, 2, 1);
    
    button = duneMusicFolderSelecter.getFolderChooserButton();
    grid.add(button, 3, 1);
    
    // Third row
    updatePlayListsCheckBox = componentFactory.createCheckBox("Update playlists on Dune", false);
    updatePlayListsCheckBox.selectedProperty().addListener(_ -> handleChanges());
    grid.add(updatePlayListsCheckBox, 0, 2);
    
    label = componentFactory.createLabel("Dune playlist folder path:");
    grid.add(label, 1, 2);
    
    playListFolderPathSelecter = componentFactory.createFolderSelecter(200, "Currently selected Dune playlists folder path",
        "Choose folder", "Select Dune playlists folder path via a file chooser", "Select the playlists folder on the Dune", false);
    playListFolderPathSelecter.setInitialFolderProvider(() -> currentDunePlaylistsFolderPath);
    Node playListFolderPathTextField = playListFolderPathSelecter.getControl();
    playListFolderPathSelecter.addListener(_ -> {
      currentDunePlaylistsFolderPath = playListFolderPathSelecter.getAbsolutePath();
      handleChanges();
      });
    grid.add(playListFolderPathTextField, 2, 2);
    
    button = playListFolderPathSelecter.getFolderChooserButton();
    grid.add(button, 3, 2);
    
    
    // Fourth row: Run and Abort button
    HBox buttonsBox = componentFactory.createHBox(20.0);
    runButton = componentFactory.createButton("Run", null);
    runButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        handleSynchronizationActions(musicFolderSelecter.getAbsolutePath(), currentDuneMusicFolderPath, currentDunePlaylistsFolderPath);
      }
      
    });
    abortButton = componentFactory.createButton("Abort", null);
    abortButton.setDisable(true);
    abortButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        if (currentTask != null) {
          currentTask.cancel();
        }
      }
      
    });
    buttonsBox.getChildren().addAll(runButton, abortButton);
    grid.add(buttonsBox, 2, 4);
        
    controlsAndStatusBox.getChildren().add(grid);
    
    // Status panel
    statusTextArea = componentFactory.createTextArea();
    statusTextArea.setMinHeight(60);
    statusTextArea.setOpacity(0.5);  // Make the TextArea transparent
    statusTextArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    
    controlsAndStatusBox.getChildren().add(statusTextArea);
    
    rootLayout.setCenter(controlsAndStatusBox);
    
    ImageView duneImageView = new ImageView(appResources.getDuneImage());
    rootLayout.setLeft(duneImageView);

    setScene(new Scene(rootLayout));
  }
  
  /**
   * Handle changes in the inputs.
   */
  private void handleChanges() {
    boolean inputsValid = true;
    
    // To synchronize to the Dune, the Dune music folder shall be valid.
    if (synchronizeToDuneCheckBox.isSelected()) {
      if (!duneMusicFolderSelecter.isValid()) {
        inputsValid = false;
      }
    }
    
    // To update playlists on the Dune, the Dune playlists folder shall be valid.
    if (updatePlayListsCheckBox.isSelected()) {
      if (!playListFolderPathSelecter.isValid()) {
        inputsValid = false;
      }
    }
    
    runButton.setDisable(!inputsValid);
  }
  
  /**
   * Perform the actual work.
   * <p>
   * Which actions are performed is related to the values of the check boxes:
   * <ul>
   * <li>
   * synchronizeToDuneCheckBox<br/>
   * The musicFolder is synchronized to the <code>duneFolderPath</code>.
   * </li>
   * <li>
   * updatePlayListsCheckBox<br/>
   * The playlists under the  <code>dunePlayListsFolderPath</code> are updated, based on the content the musicFolder.
   * </li>
   * </ul>
   * @param musicFolderName the folder, on this pc,  with all music.
   * @param duneFolderPath the folder, on the Dune, with all music.
   * @param dunePlayListsFolderPath the folder, on the Dune, with all playlists.
   */
  private void handleSynchronizationActions(String musicFolderName, String duneFolderPath, String dunePlayListsFolderPath) {
    clearStatusPanel();
    runButton.setDisable(true);

    if (synchronizeToDuneCheckBox.isSelected()) {
      addTextToStatusPanel("Synchronizing ...");
      
      SynchronizeToDuneTask synchronizeToDuneTask = new SynchronizeToDuneTask(musicFolderName, duneFolderPath);
      currentTask = synchronizeToDuneTask;

      // Messages are always error messages and thus are reported as such.
      synchronizeToDuneTask.messageProperty().addListener((_, _, newValue) -> {
          LOGGER.severe("Message: " + newValue);
          addTextToStatusPanel(NEW_LINE + "Error: " + newValue + NEW_LINE);
      });

      // On successful completion, print it and continue with the next task.
      synchronizeToDuneTask.setOnSucceeded((event) -> {
          LOGGER.info("Succeeded: " + event);
          currentTask = null;
          abortButton.setDisable(true);
          addTextToStatusPanel(NEW_LINE + "Synchronization complete" + NEW_LINE);
          handleUpdatePlaylists(musicFolderName, dunePlayListsFolderPath);
      });

      // Upon cancel, don't continue to the next task and report to the user
      synchronizeToDuneTask.setOnCancelled((event) -> {
          LOGGER.severe("Cancelled: " + event);
          currentTask = null;
          abortButton.setDisable(true);
          addTextToStatusPanel(NEW_LINE + "Synchronization cancelled: " + event.toString() + NEW_LINE);
          runButton.setDisable(false);
      });

      // Show progress
      synchronizeToDuneTask.progressProperty().addListener(progress -> {
          LOGGER.info("Progress: " + progress);
          addTextToStatusPanel(".");
      });

      Thread  synchronizeToDuneThread = new Thread(synchronizeToDuneTask);
      synchronizeToDuneThread.setDaemon(true);
      synchronizeToDuneThread.start();
      abortButton.setDisable(false);

    } else {
      handleUpdatePlaylists(musicFolderName, dunePlayListsFolderPath);
    }

  }
  
  /**
   * Run the UpdatePlaylistsTask if the corresponding checkbox is selected.
   * 
   * @param musicFolderName the folder, on this pc, with all music.
   * @param dunePlayListsFolderPath the folder, on the Dune, with all playlists.
   */
  private void handleUpdatePlaylists(String musicFolderName, String dunePlayListsFolderPath) {
    
    if (updatePlayListsCheckBox.isSelected()) {
      addTextToStatusPanel("Updating playlists ...");
      
      UpdatePlaylistsTask updatePlaylistsTask = new UpdatePlaylistsTask(musicFolderName, dunePlayListsFolderPath);
      currentTask = updatePlaylistsTask;

      // Messages are always error messages and thus are reported as such.
      updatePlaylistsTask.messageProperty().addListener((_, _, newValue) -> {
          LOGGER.severe("Message: " + newValue);
          addTextToStatusPanel(NEW_LINE + "Error: " + newValue + NEW_LINE);
      });


      // On successful completion, print it and enable the run button.
      updatePlaylistsTask.setOnSucceeded((event) -> {
          LOGGER.info("Succeeded: " + event);
          currentTask = null;
          abortButton.setDisable(true);
          addTextToStatusPanel(NEW_LINE + "Playlists updated" + NEW_LINE);
          addTextToStatusPanel("All done");
          runButton.setDisable(false);
      });

      // Upon cancel, report to the user
      updatePlaylistsTask.setOnCancelled((event) -> {
          LOGGER.severe("Cancelled: " + event);
          currentTask = null;
          abortButton.setDisable(true);
          addTextToStatusPanel(NEW_LINE + "Updating playlists cancelled: " + event.toString() + NEW_LINE);
          runButton.setDisable(false);
      });

      // Show progress
      updatePlaylistsTask.progressProperty().addListener(progress -> {
        LOGGER.info("Progress: " + progress);
        addTextToStatusPanel(".");
      });

      Thread  updatePlaylistsThread = new Thread(updatePlaylistsTask);
      updatePlaylistsThread.setDaemon(true);
      updatePlaylistsThread.start();
      abortButton.setDisable(false);
            
    } else {
      addTextToStatusPanel("All done");
      runButton.setDisable(false);
    }
    
  }
  
  /**
   * Clear the content of the status panel.
   */
  private void clearStatusPanel() {
    statusTextArea.setText("");
    statusTextArea.fireEvent(new ActionEvent());
  }
  
  /**
   * Add text (a String) 
   * @param text to the content of the status panel.
   */
  private void addTextToStatusPanel(String text) {
    StringBuilder buf = new StringBuilder(statusTextArea.getText());
    
    buf.append(text);
    
    statusTextArea.setText(buf.toString());
    statusTextArea.fireEvent(new ActionEvent());
  }
  
}
