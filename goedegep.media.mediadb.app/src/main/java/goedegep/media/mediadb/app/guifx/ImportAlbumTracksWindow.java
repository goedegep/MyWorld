package goedegep.media.mediadb.app.guifx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.media.mediadb.app.DiscTracksImportInfo;
import goedegep.media.mediadb.app.MediaDbAppErrorInfo;
import goedegep.media.mediadb.app.MediaDbAppUtil;
import goedegep.media.mediadb.app.TrackImportInfo;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ImportAlbumTracksWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(ImportAlbumTracksWindow.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");
  private String SOURCE_FOLDER = "D:\\SoulSeek\\Complete";
  
  private MediaDb mediaDb;
  private Album album;
  DiscTracksImportInfo albumTracksImportInfo;
  private ComponentFactoryFx componentFactory;
  private ComboBox<DiscWrapper> discComboBox = null;
  private ObjectControlFolderSelecter sourceFolderSelector;
  private TextField albumFolderTextField;
  private TableView<TrackImportInfo> trackPreviewTable;
  private TextArea statusPanel;
  private Button importButton;
//  private String sourceFolderName;
  
  public ImportAlbumTracksWindow(CustomizationFx customization, MediaDb mediaDb, Album album) {
    super(createWindowTitle(album), customization);
    
    this.mediaDb = mediaDb;
    this.album = album;
    
    componentFactory = customization.getComponentFactoryFx();
        
    createGUI();
    
//    sourceFolderName = sourceFolderTextField.getText();
    updateImportButtonStatus();
  }
  
  /**
   * Create the window title.
   * 
   * @param album The Album of which the tracks are to be imported.
   * @return the window title.
   */
  private static String createWindowTitle(Album album) {
    StringBuilder buf = new StringBuilder();
    buf.append("Import tracks for album: ");
    buf.append(album.getArtistAndTitle());
    
    return buf.toString();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    VBox mainLayout =  componentFactory.createVBox(12.0);
    
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(12.0));
    gridPane.setHgap(12.0);
    gridPane.setVgap(12.0);
    
    /*
     * Part 1; main controls
     */
    Label label;
    int row = 0;
    
    // First row: 'Disc:' <disc selection combobox>
    // Only shown for multi disc albums.
    if (album.isMultiDiscAlbum()) {
      label = componentFactory.createLabel("Disc:");
      gridPane.add(label, 0, row);
      
      album.getDiscs();
      ObservableList<DiscWrapper> discWrapperList = DiscWrapper.createDiscWrapperList(album.getDiscs());
      discComboBox = componentFactory.createComboBox(discWrapperList);
      discComboBox.getSelectionModel().select(0);
      gridPane.add(discComboBox, 1, row);
      
      row++;
    }
    
    // First row: 'Source folder:' <source-folder-text-field> <folder-chooser-button>
    label = componentFactory.createLabel("Source folder:");
    gridPane.add(label, 0, row);
    
    sourceFolderSelector = new ObjectControlFolderSelecter(SOURCE_FOLDER, 400, "Enter the name of the folder with the tracks of the album that has to be imported", "Choose source folder", "Click to start folder chooser", "Select the folder with the tracks of the album that has to be imported");
    Node sourceFolderTextField = sourceFolderSelector.getPathTextField();
    sourceFolderSelector.objectValue().addListener((observable, oldValue, newValue) -> handleNewSourceFolder());
    gridPane.add(sourceFolderTextField, 1, row);
    
    Button sourceFolderChooserButton = sourceFolderSelector.getFolderChooserButton();
    gridPane.add(sourceFolderChooserButton, 2, row);
    
    row++;
    
    // Second row: 'Album folder:' <album-folder-text-field>
    label = componentFactory.createLabel("Album folder:");
    gridPane.add(label, 0, row);
    
    albumFolderTextField = componentFactory.createTextField(550, "Generated album folder");
    albumFolderTextField.setEditable(false);
    gridPane.add(albumFolderTextField, 1, row, 2, 1);
    
    mainLayout.getChildren().add(gridPane);
    
    /*
     * Part 2; Preview table
     */
    // Preview table: trackNr, trackTitle (from database), sourceFileName, destinationFileName
    trackPreviewTable = new TableView<>();
    TableColumn<TrackImportInfo, Object> tableColumn;
    
    tableColumn = new TableColumn<>("TrackNr");
    tableColumn.setCellValueFactory(new PropertyValueFactory<TrackImportInfo, Object>("trackNr"));
    trackPreviewTable.getColumns().add(tableColumn);
    
    tableColumn = new TableColumn<>("Source File");
    tableColumn.setMinWidth(500.0);
    tableColumn.setCellValueFactory(new PropertyValueFactory<TrackImportInfo, Object>("sourceFilePath"));
    trackPreviewTable.getColumns().add(tableColumn);
    
    tableColumn = new TableColumn<>("Destination File");
    tableColumn.setMinWidth(500.0);
    tableColumn.setCellValueFactory(new PropertyValueFactory<TrackImportInfo, Object>("destinationFilePath"));
    trackPreviewTable.getColumns().add(tableColumn);
    
    mainLayout.getChildren().add(trackPreviewTable);
    
    /*
     * Part 3; Import and cancel buttons panel
     */
    mainLayout.getChildren().add(createButtonsPanel());
    
    /*
     * Part 4; Status panel
     */
    statusPanel = componentFactory.createTextArea("Nothing to report for now");
    mainLayout.getChildren().add(statusPanel);

    Scene scene = new Scene(mainLayout, 1300, 700);
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
  private Node createButtonsPanel() {
    HBox hBox = componentFactory.createHBox(10.0, 12.0);
   
    // Two buttons: "Import tracks", "Cancel".
    importButton = componentFactory.createButton("Import", null);
    importButton.setOnAction(e -> importAlbumTracks());
    hBox.getChildren().add(importButton);
    
    Button button = componentFactory.createButton("Cancel", "Exit window without making any modifications");
    button.setOnAction(e -> closeWindow());
    hBox.getChildren().add(button);
    
    return hBox;
  }
  
  /**
   * Handle the fact that a new source folder is selected.
   * <p>
   * If the name is really a directory, the directory is scanned and a preview is shown.
   * 
   * @param newSourceFolderName the newly selected source folder.
   */
  private void handleNewSourceFolder() {
    LOGGER.severe("=>");

    albumTracksImportInfo = null;
    albumFolderTextField.setText("");
    String sourceFolderName = sourceFolderSelector.getObjectValue();
    
    Disc disc = null;
    if (discComboBox != null) {
      DiscWrapper discWrapper = discComboBox.getValue();
      if (discWrapper != null) {
        disc = discWrapper.getDisc();
      }
    } else {
      disc = album.getDisc();
    }
    
    if (sourceFolderName != null  &&  disc != null) {
      Path sourceFolderPath = Paths.get(sourceFolderName);
      if (Files.isDirectory(sourceFolderPath)) {
        createDiscTracksImportData(sourceFolderName, disc);
      }
    }

    LOGGER.severe("<=");
  }
  
  /**
   * Check whether an import is possible.
   * <p>
   * This is the case if there is <code>albumTracksImportInfo</code> available, without any errors.
   * @return
   */
  private boolean isImportPossible() {
    return (albumTracksImportInfo) != null  &&  (albumTracksImportInfo.getErrors().isEmpty());
  }
  
  /**
   * Enable/disable the import button based on whether an import can be performed or not.
   */
  private void updateImportButtonStatus() {
    importButton.setDisable(!isImportPossible());
  }

  /**
   * Close this window.
   */
  private void closeWindow() {
    System.out.println("AlbumDetailsEditor: closeWindow");
    this.close();
  }
  
  /**
   * Create the data required to import the disc tracks.
   */
  private void createDiscTracksImportData(String sourceFolderName, Disc disc) {
    albumTracksImportInfo = MediaDbAppUtil.createDiscTracksImportData(disc, sourceFolderName, mediaDb);
    trackPreviewTable.setItems(albumTracksImportInfo.getTrackImportPreviewInfos());
    if (albumTracksImportInfo.getErrors().size() == 0) {
      albumFolderTextField.setText(albumTracksImportInfo.getDestinationFolderPath().toString());
      updateStatusPanel("Album tracks can be imported", null);
    } else {
      updateStatusPanel("Album tracks cannot be imported", albumTracksImportInfo.getErrors());
    }
    
    updateImportButtonStatus();
  }
  
  /**
   * Import the album tracks.
   */
  private void importAlbumTracks() {
    try {
      Files.createDirectories(albumTracksImportInfo.getDestinationFolderPath());
      for (TrackImportInfo trackImportPreviewInfo: albumTracksImportInfo.getTrackImportPreviewInfos()) {
        Files.copy(trackImportPreviewInfo.getSourceFilePath(), trackImportPreviewInfo.getDestinationFilePath(), StandardCopyOption.COPY_ATTRIBUTES);
      }
      updateStatusPanel("Album tracks have been imported successfully", null);
    } catch (IOException e) {
      updateStatusPanelForException("An exception occured while trying to import the tracks", e);
    }
  }
  
  /**
   * Update the status panel in case an exception was reported.
   * 
   * @param message A textual message.
   * @param exception the reported exception.
   */
  private void updateStatusPanelForException(String message, Exception exception) {
    StringBuilder buf = new StringBuilder();
    
    if (message != null) {
      buf.append(message).append(NEW_LINE);
    }
    
    buf.append("System message: ").append(exception.toString()).append(NEW_LINE);
    
    statusPanel.setText(buf.toString());
  }
  
  /**
   * Update the status panel is case errors were reported.
   * 
   * @param message A textual message.
   * @param errors A list of errors.
   */
  private void updateStatusPanel(String message, List<? extends Object> errors) {
    StringBuilder buf = new StringBuilder();
    if (message != null) {
      buf.append(message).append(NEW_LINE);
    }
    
    if ((errors != null)  &&  !errors.isEmpty()) {
      buf.append("Problems:").append(NEW_LINE);
      for (Object error: errors) {
        if (error instanceof MediaDbAppErrorInfo) {
          MediaDbAppErrorInfo mediaDbAppErrorInfo = (MediaDbAppErrorInfo) error;
          
          switch (mediaDbAppErrorInfo.getErrorCode()) {
          case TRACKS_WITH_DIFFERENT_EXTENSIONS:
            buf.append("There are different types of audio tracks, which isn't supported.").append(NEW_LINE);
            break;
            
          case WRONG_NUMBER_OF_TRACKS:
            buf.append("The source folder doesn't have the correct number of tracks: ").append(mediaDbAppErrorInfo.getDetails()).append(".").append(NEW_LINE);
            break;

          default:
            throw new RuntimeException("Unhandled case: " + mediaDbAppErrorInfo.getErrorCode());

          }
        } else {
          buf.append(error.toString()).append(NEW_LINE);
        }
      }
    }
    
    statusPanel.setText(buf.toString());
  }
}

class DiscWrapper {
  private Disc disc;
  private String displayName;
    
  private DiscWrapper(Disc disc, String displayName) {
    this.disc = disc;
    this.displayName = displayName;
  }
  
  
  public static DiscWrapper getDiscWrapper(Disc disc, List<DiscWrapper> discWrappers) {
    for (DiscWrapper discWrapper: discWrappers) {
      if (discWrapper.disc.equals(disc)) {
        return discWrapper;
      }
    }
    
    return null;
  }
  
  public Disc getDisc() {
    return disc;
  }

  @Override
  public String toString() {
    return displayName;
  }

  public static ObservableList<DiscWrapper> createDiscWrapperList(List<Disc> discs) {
    ObservableList<DiscWrapper> values = FXCollections.observableArrayList();
    
    for (Disc disc: discs) {
      values.add(new DiscWrapper(disc, getDiscId(disc)));
    }
    
    return values;
  }
  
  public static String getDiscId(Disc disc) {
    String discId = disc.getTitle();
    if (discId == null) {
      int discNr = disc.getAlbum().getDiscs().indexOf(disc);
      discId = "Disc " + discNr;
    }
    return discId;
  }
}