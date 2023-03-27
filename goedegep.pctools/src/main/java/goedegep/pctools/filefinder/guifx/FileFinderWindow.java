package goedegep.pctools.filefinder.guifx;

import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.pctools.filefinder.logic.FileFinderTask;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This class provides the GUI of the File Finder component.
 * <p>
 * Currently the following files can be searched for:
 * <ul>
 * <li>
 * FrameMaker files
 * </li>
 * </ul>
 */
public class FileFinderWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(FileFinderWindow.class.getName());
  private static final String WINDOW_TITLE = "Bestanden zoeken";
  /**
   * Text to show on the searchFrameMakerFilesButton.
   */
  private static final String SEARCH_FRAMEMAKER_FILES_BUTTON_TEXT = "FrameMaker files";
  private static final String SEARCH_FRAMEMAKER_FILES_BUTTON_TOOLTIP = "zoek FrameMaker files";
  /**
   * Text to show on the searchFrameMakerFilesButton, when a search is active and can be cancelled.
   */
  private static final String CANCEL_TEXT = "Cancel";
  private static final String CANCEL_TOOLTIP = "Cancel current search operation";
  
  private ComponentFactoryFx componentFactory;

//  private String currentlySelectedFolder = null;
//  private BooleanProperty folderValidProperty = null;
  private FileFinderTask fileFinderTask = null;
  
  private ObjectControlFolderSelecter folderSelecter;
  private HBox filesFoundBox;
  private Button searchFrameMakerFilesButton;          // Button is only enabled if a folder is specified.
  private StatusPanel statusPanel;                     // shows status information.

  public FileFinderWindow(CustomizationFx customization) {
    super(WINDOW_TITLE, customization);
        
    componentFactory = getComponentFactory();
    
    createGUI();
    
    updateSearchFrameMakerFilesButton();

    statusPanel.updateText("Nothing to report for now");
  }

  /**
   * Create the GUI.
   * <p>
   * Top pane: controls: left Folder selection, right action buttons.
   * Below that: search result list.
   * Below that: Status/progress bar
   */
  private void createGUI() {
    VBox rootPane = new VBox();
    
    // Controls
    HBox controlsPane = componentFactory.createHBox(12.0, 10.0);
    
    Label folderNameLabel = componentFactory.createLabel("Search folder:");
    controlsPane.getChildren().add(folderNameLabel);
    
    ObjectControlFolderSelecter folderSelecter = componentFactory.createFolderSelecter(null, 400, "Currently selected folder",
        "Choose folder", "Select search folder via a file chooser", "Select the search folder");
    
    Node folderNameTextField = folderSelecter.getPathTextField();
    controlsPane.getChildren().add(folderNameTextField);
    
    Button folderChooserButton = folderSelecter.getFolderChooserButton();
    controlsPane.getChildren().add(folderChooserButton);
    
//    folderValidProperty = folderSelecter.ocValidProperty();
    folderSelecter.addListener(new InvalidationListener() {

      @Override
      public void invalidated(Observable observable) {
        updateSearchFrameMakerFilesButton();
      }
      
    });
    
    searchFrameMakerFilesButton = componentFactory.createButton("FrameMaker files", "zoek FrameMaker files");
    searchFrameMakerFilesButton.setOnAction(e -> searchFrameMakerFiles(folderSelecter.ocGetValue()));
    controlsPane.getChildren().add(searchFrameMakerFilesButton);
    
    rootPane.getChildren().add(controlsPane);
    
    // Search result list
    filesFoundBox = componentFactory.createHBox(new Insets(10, 10, 10, 10));
    filesFoundBox.setMinHeight(500);

    reportNothingToReport();
    rootPane.getChildren().add(filesFoundBox);
    
    // Status panel
    statusPanel = new StatusPanel(componentFactory);
    rootPane.getChildren().add(statusPanel);
    
    setScene(new Scene(rootPane));
  }
  
  /**
   * Update the searchFrameMakerFilesButton.
   * <p>
   * The button has the following states:
   * <ul>
   * <li>
   * Enabled with the text {@link #SEARCH_FRAMEMAKER_FILES_BUTTON_TEXT}<br/>
   * This is the case when a search can be started. This is true if a search folder is specified and there is currently no search active.
   * </li>
   * <li>
   * Disabled with the text SEARCH_FRAMEMAKER_FILES_BUTTON_TEXT<br/>
   * This is the case when when no search folder is specified and there is currently no search active.
   * </li>
   * <li>
   * Enabled with the text {@link #CANCEL_TEXT}<br/>
   * This is the case when a search is active.
   * </li>
   * </ul>
   */
  private void updateSearchFrameMakerFilesButton() {
    if (fileFinderTask != null) {
      searchFrameMakerFilesButton.setText(CANCEL_TEXT);
      searchFrameMakerFilesButton.setTooltip(new Tooltip(CANCEL_TOOLTIP));
    } else {
      searchFrameMakerFilesButton.setText(SEARCH_FRAMEMAKER_FILES_BUTTON_TEXT);
      searchFrameMakerFilesButton.setTooltip(new Tooltip(SEARCH_FRAMEMAKER_FILES_BUTTON_TOOLTIP));
      searchFrameMakerFilesButton.setDisable(!folderSelecter.ocIsValid());
    }
  }
  
  /**
   * Start a search for FrameMaker files.
   * <p>
   * If a search is active (fileFinderTask != null), it will be cancelled.
   * The search is started in the background. Progress and status messages are reported via the StatusPanel.
   * 
   * @param searchFolder the folder to be searched.
   */
  private void searchFrameMakerFiles(String searchFolder) {
    LOGGER.info("=>");
    
    reportNothingToReport();

    if (fileFinderTask != null) {
      fileFinderTask.cancel();
    } else {

      fileFinderTask = new FileFinderTask(searchFolder);

      // Handle messages - messages are shown in the statusPanel
      fileFinderTask.messageProperty().addListener(new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
          statusPanel.updateText(newValue);
        }

      });

      // Handle progress reports - progress is shown in the statusPanel
      fileFinderTask.progressProperty().addListener(new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
          statusPanel.updateProgress(newValue.doubleValue());
        }

      });

      // upon successful completion, show the result.
      fileFinderTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

        @Override
        public void handle(WorkerStateEvent event) {
          List<String> filesFound = fileFinderTask.getValue();
          reportFilesFound(filesFound);
          fileFinderTask = null;
          updateSearchFrameMakerFilesButton();
        }

      });

      // Report failure via statusLabel
      fileFinderTask.setOnFailed(new EventHandler<WorkerStateEvent>() {

        @Override
        public void handle(WorkerStateEvent event) {
          statusPanel.updateText("Something went wrong while searching files, system message: " + event.toString());
          fileFinderTask = null;
          updateSearchFrameMakerFilesButton();
        }

      });

      // Report cancelled via statusLabel
      fileFinderTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {

        @Override
        public void handle(WorkerStateEvent event) {
          statusPanel.updateText("Search cancelled: " + event.toString());
          fileFinderTask = null;
          updateSearchFrameMakerFilesButton();
        }

      });

      // report any intermediate search results.
      fileFinderTask.valueProperty().addListener(new ChangeListener<List<String>>() {

        @Override
        public void changed(ObservableValue<? extends List<String>> observable,
            List<String> oldValue,
            List<String> newValue) {
          reportFilesFound(newValue);
        }

      });

      Thread  fileFinderTaskThread = new Thread(fileFinderTask);
      fileFinderTaskThread.setDaemon(true);
      fileFinderTaskThread.start();
      
      updateSearchFrameMakerFilesButton();
    }

    LOGGER.fine("<=");

  }

  /**
   * Show that there is nothing to report in the filesFoundBox. 
   */
  private void reportNothingToReport() {
    filesFoundBox.getChildren().clear();

    // This label is replaced by the result list when results are available.
    Label label = new Label("no results available yet");  // This is only a text to be shown, so it is not created via the componentFactory.
    label.setStyle("-fx-text-fill: LightGray; -fx-font-size: 200%;");
    filesFoundBox.getChildren().add(label);
  }
  
  /**
   * Show the files found in the filesFoundBox.
   * 
   * @param filesFound a list of the files found
   */
  private void reportFilesFound(List<String> filesFound) {
    LOGGER.info("=> number of files found: " + filesFound.size());
    
    ListView<String> filesFoundListView = new ListView<String>();

    for (String fileFound: filesFound) {
      filesFoundListView.getItems().add(fileFound);
    }

    filesFoundBox.getChildren().clear();
    filesFoundBox.getChildren().add(filesFoundListView);
    filesFoundListView.prefWidthProperty().bind(filesFoundBox.widthProperty());

    
    LOGGER.info("<=");
  }
  
}

/**
 * This class provides a status panel.
 * <p>
 * The panel consists of a status Text, and a ProgressBar.<br/>
 * The progress bar is only shown if there is progress to be reported (progress >= 0.0  &&  progress < 1.0).
 */
class StatusPanel extends HBox {
  private Text text = new Text();
  private ProgressBar progressBar = new ProgressBar(-1);

  StatusPanel(ComponentFactoryFx componentFactory) {
    componentFactory.customizePane(this);
    setPadding(new Insets(10, 10, 10, 10));
    setSpacing(20);
  }
  
  /**
   * Update the status text
   * 
   * @param message The new status text.
   */
  void updateText(String message) {
    getChildren().clear();
    text.setText(message);
    getChildren().add(text);
    
    double progress = progressBar.getProgress();
    
    if (progress >= 0.0  &&  progress < 1.0) {
       getChildren().add(progressBar);
    }
  }
  
  /**
   * Update the progress.
   * <p>
   * If the progress value is greater or equal to zero, and less than 1, the progress bar is shown.
   * Otherwise it is hidden.
   * 
   * @param progress the new progress value.
   */
  void updateProgress(double progress) {
    getChildren().clear();
    getChildren().add(text);
    
    progressBar.setProgress(progress);
    
    if (progress >= 0.0  &&  progress < 1.0) {
       getChildren().add(progressBar);
    }
  }
}

