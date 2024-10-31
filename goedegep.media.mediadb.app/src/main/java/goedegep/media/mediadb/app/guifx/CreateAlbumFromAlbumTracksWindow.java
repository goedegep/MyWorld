package goedegep.media.mediadb.app.guifx;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.media.musicfolder.TrackFile;
import goedegep.util.file.FileUtils;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is temporary.
 * Based on the audio files in a folder, it generates the <track> elements for an album in an AlbumInfo file.
 * This text can than be copied and paste into the .xml file.
 * In the future, the information derived from the files will be shown in an AlbumDetailsEditor.
 */
public class CreateAlbumFromAlbumTracksWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(CreateAlbumFromAlbumTracksWindow.class.getName());
  private static final String WINDOW_TITLE = "Create Album from tracks in a folder";
  private static final String NEW_LINE = System.getProperty("line.separator");
  private String SOURCE_FOLDER = "D:\\SoulSeek\\Complete";
  
  private ComponentFactoryFx componentFactory;
  private ObjectControlFolderSelecter sourceFolderSelector;
  private TextArea statusPanel;
  private Button actionButton; // To perform preview or import
  private String sourceFolderName;
  private TextArea proposedTracksTextArea;
  
  public CreateAlbumFromAlbumTracksWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);
        
    componentFactory = customization.getComponentFactoryFx();
        
    createGUI();
    
    sourceFolderName = sourceFolderSelector.getAbsolutePath();
    
    show();
  }
  
  private void createGUI() {
    VBox mainLayout =  componentFactory.createVBox(12.0);
    
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(12.0));
    gridPane.setHgap(12.0);
    gridPane.setVgap(12.0);
    
    /*
     * Part 1; main controls
     */
    
    // First row: 'Source folder:' <source-folder-text-field> <folder-chooser-button>
    Label label = componentFactory.createLabel("Source folder:");
    gridPane.add(label, 0, 0);
    
    sourceFolderSelector = componentFactory.createFolderSelecter(
        400,
        "Enter the name of the folder with the tracks of the album that has to be imported",
        "Choose source folder",
        "Click to start folder chooser",
        "Select the folder with the tracks of the album that has to be imported",
        false);
    sourceFolderSelector.setInitialFolderProvider(() -> SOURCE_FOLDER);
    Node sourceFolderTextField = sourceFolderSelector.getControl();
    sourceFolderSelector.addListener((observable) -> handleNewSourceFolder(sourceFolderSelector.getAbsolutePath()));
    gridPane.add(sourceFolderTextField, 1, 0);
    
    Button sourceFolderChooserButton = sourceFolderSelector.getFolderChooserButton();
    gridPane.add(sourceFolderChooserButton, 2, 0);
    
    mainLayout.getChildren().add(gridPane);
        
    /*
     * Part 2; Text Area with proposed tracks
     */
    proposedTracksTextArea = componentFactory.createTextArea();
    proposedTracksTextArea.setMinHeight(800.0);
    proposedTracksTextArea.setPrefHeight(800.0);
    
    mainLayout.getChildren().add(proposedTracksTextArea);
    
    /*
     * Part 3; Buttons TODO
     */
    mainLayout.getChildren().add(createOkOrCancelPanel());
    
    /*
     * Part 4; Status panel
     */
    statusPanel = componentFactory.createTextArea("Nothing to report for now");
    mainLayout.getChildren().add(statusPanel);

    Scene scene = new Scene(mainLayout, 1100, 700);
    setScene(scene);
    
  }
  
  /**
   * Create a panel with 'Import tracks' and a 'Cancel' buttons.
   * <p>
   * The action for the 'Import tracks' button is {@link #importTrack()}.
   * The action for the 'Cancel' button is {@link #closeWindow()}.
   * 
   * @return
   */
  private Node createOkOrCancelPanel() {
    HBox hBox = componentFactory.createHBox(10.0, 12.0);
   
    // Two buttons: "Preview/Import tracks", "Cancel".
    actionButton = componentFactory.createButton("Scan tracks", null);
    actionButton.setOnAction(e -> scanTracksFolder());
    hBox.getChildren().add(actionButton);
    
    Button button = componentFactory.createButton("Cancel", "Exit window");
    button.setOnAction(e -> closeWindow());
    hBox.getChildren().add(button);
    
    return hBox;
  }
  
  private void handleNewSourceFolder(String newSourceFolderName) {
    LOGGER.severe("=> sourceFolderName=" + sourceFolderName);
    
    Path path = Paths.get(newSourceFolderName);
    if (Files.isDirectory(path)) {
      sourceFolderName = newSourceFolderName;
      scanTracksFolder();
    }
    
    LOGGER.severe("<=");
  }
  
  private void closeWindow() {
    System.out.println("AlbumDetailsEditor: closeWindow");
    this.close();
  }
  
  private void scanTracksFolder() {
    List<String> trackFileNames = FileUtils.getAudioFileNamesInFolder(sourceFolderName);
    StringBuilder buf = new StringBuilder();
    for (String trackFileName: trackFileNames) {
      buf.append("    <Track>").append(NEW_LINE);
      buf.append("      <TrackTitle>").append(deriveTrackTitleFromFileName(trackFileName)).append("</TrackTitle>").append(NEW_LINE);
      buf.append("    </Track>").append(NEW_LINE);
    }
    proposedTracksTextArea.setText(buf.toString());
  }
  
  private String deriveTrackTitleFromFileName(String fileName) {
    fileName = FileUtils.getFileNameWithoutExtension(fileName);
    String title = TrackFile.getTrackTitle(fileName);
    if (title == null) {
      title = "<no-title>";
    }
    
    String origTitle = title;
    title = title.trim();
    if (!title.equals(origTitle)) {
      LOGGER.severe("Stripped");
    }
    
    return title;
  }
}
