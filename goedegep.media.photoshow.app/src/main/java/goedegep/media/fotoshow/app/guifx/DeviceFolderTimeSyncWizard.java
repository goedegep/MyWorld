package goedegep.media.fotoshow.app.guifx;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.media.photo.IPhotoMetaDataWithImage;
import goedegep.media.photoshow.model.FolderTimeOffsetSpecification;
import goedegep.media.photoshow.model.PhotoShowFactory;
import goedegep.media.photoshow.model.PhotoShowSpecification;
import goedegep.util.datetime.DurationUtil;
import goedegep.util.img.PhotoFileMetaDataHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class provides a wizard to determine the 'calculation times' at which photos are taken.
 * <p>
 * The absolute times can be adjusted, or photos in one folder can be synchronized to the photos in another folder. 
 *
 */
public class DeviceFolderTimeSyncWizard extends Dialog<ButtonType> {
  private final static Logger LOGGER = Logger.getLogger(DeviceFolderTimeSyncWizard.class.getName());
  private final static String NEWLINE = System.getProperty("line.separator");

  private final static String WINDOW_TITLE = "Time synchronization wizard";
  
  private ComboBox<String> toBeCorrectedFolderSelectionBox;
  
  private Stage ownerWindow;
  private PhotoShowSpecification photoShowSpecification;
  private Map<String, List<IPhotoInfo>> photoInfoListsMap;
  private ComponentFactoryFx componentFactory;
  private ListView<IPhotoMetaDataWithImage> timeReferencePhotoList;
  private ListView<IPhotoMetaDataWithImage> timeToBeAdjustedPhotoList;
  private String timeToBeAdjustedFolderName;
  private Duration timeOffset = null;

  /**
   * Constructor.
   * 
   * @param customization GUI customization.
   * @param ownerWindow The window owning this dialog.
   * @param photoInfoListsMap information on the photos per folder.
   */
  public DeviceFolderTimeSyncWizard(CustomizationFx customization, Stage ownerWindow, PhotoShowSpecification photoShowSpecification, Map<String, List<IPhotoInfo>> photoInfoListsMap) {
    
    setTitle(WINDOW_TITLE);
    this.ownerWindow = ownerWindow;
    this.photoShowSpecification = photoShowSpecification;
    this.photoInfoListsMap = photoInfoListsMap;
        
    componentFactory = customization.getComponentFactoryFx();
    
    initOwner(ownerWindow);
    
    createGUI(ownerWindow);
    setResizable(true);
  }
  
  public String getTimeToBeAdjustedFolderName() {
    return timeToBeAdjustedFolderName;
  }
  
  public Duration getAdjustmentTime() {
    return timeOffset;
  }
    
  /**
   * Create the GUI
   * <p>
   * Header text explains the use of this dialog.
   * The content pane starts with the controls for selecting the reference folder and the to be corrected folder.
   * Below that are the lists with the photos; one list per folder.
   * Below that are the ok and cancel buttons.
   * 
   * 
   * @param ownerWindow The window owning this dialog.
   */
  private void createGUI(Stage ownerWindow) {
    setHeaderText("For the photo show, the photos will be sorted on the time that the photos were taken. " +
                  "This leads to a problem however, if the times of the devices (camera, smart phone), with which the photos were taken, were not synchronized."+ NEWLINE +
                  "Select the time reference folder. This is the folder with the most reliable time." + NEWLINE +
                  "Typically this is a folder with photos taken with a smartphone, as a smartphone normally uses the local time from the phone network." + NEWLINE +
                  "Select the folder for which the time has to be corrected." + NEWLINE +
                  "This is often a folder with photos taken with a digital camera. On this camera you often don't set the time to the local time.");
    
    // VBox: folder selection - selection lists
    VBox dialogPane = componentFactory.createVBox(15);
    
    // HBox: reference folder label - reference folder selection - to be corrected folder label - to be corrected folder selection.
    HBox folderSelectionBox = componentFactory.createHBox(15);
    
    Label label = componentFactory.createLabel("Reference folder:");
    folderSelectionBox.getChildren().add(label);
    
    ComboBox<String> referenceFolderSelectionBox = componentFactory.createComboBox(photoInfoListsMap.keySet());
    if (!photoInfoListsMap.keySet().isEmpty()) {
      referenceFolderSelectionBox.getSelectionModel().select(0);
    }
    folderSelectionBox.getChildren().add(referenceFolderSelectionBox);
    
    label = componentFactory.createLabel("To be corrected folder:");
    folderSelectionBox.getChildren().add(label);
    
    toBeCorrectedFolderSelectionBox = componentFactory.createComboBox(photoInfoListsMap.keySet());
    if (photoInfoListsMap.keySet().size() > 1) {
      toBeCorrectedFolderSelectionBox.getSelectionModel().select(1);
    }
    folderSelectionBox.getChildren().add(toBeCorrectedFolderSelectionBox);
    
    dialogPane.getChildren().add(folderSelectionBox);
    
    // Photo lists
    HBox photoListsBox = componentFactory.createHBox(10);
    VBox referenceFolderPhotoListBox = componentFactory.createVBox();
    referenceFolderPhotoListBox.setMinHeight(700);
    referenceFolderPhotoListBox.setMinWidth(500);
    referenceFolderPhotoListBox.setStyle("-fx-border-style: solid inside;");
    VBox toBeCorrectedFolderPhotoListBox = componentFactory.createVBox();
    toBeCorrectedFolderPhotoListBox.setMinHeight(700);
    toBeCorrectedFolderPhotoListBox.setMinWidth(500);
    toBeCorrectedFolderPhotoListBox.setStyle("-fx-border-style: solid inside;");
    photoListsBox.getChildren().addAll(referenceFolderPhotoListBox, toBeCorrectedFolderPhotoListBox);

    dialogPane.getChildren().add(photoListsBox);

    getDialogPane().setContent(dialogPane);
    
    referenceFolderSelectionBox.setOnAction(event -> {
      String referenceFolderName = referenceFolderSelectionBox.getSelectionModel().getSelectedItem();
      if (referenceFolderName == null) {
        referenceFolderPhotoListBox.getChildren().clear();
      } else {
        timeReferencePhotoList = addPictureList(referenceFolderName, referenceFolderPhotoListBox, true);
      }
      
      if (referenceFolderSelectionBox.getItems().size() > 1) {
        int referenceFolderIndex = referenceFolderSelectionBox.getSelectionModel().getSelectedIndex();
        int toBeCorrectedFolderIndex;
        if (referenceFolderIndex == 0) {
          toBeCorrectedFolderIndex = 1;
        } else {
          toBeCorrectedFolderIndex = 0;
        }
        toBeCorrectedFolderSelectionBox.getSelectionModel().select(toBeCorrectedFolderIndex);
      }
    });

    toBeCorrectedFolderSelectionBox.setOnAction(event -> {
      String toBeCorrectedFolderName = toBeCorrectedFolderSelectionBox.getSelectionModel().getSelectedItem();
      if (toBeCorrectedFolderName == null) {
        toBeCorrectedFolderPhotoListBox.getChildren().clear();
      } else {
        timeToBeAdjustedPhotoList = addPictureList(toBeCorrectedFolderName, toBeCorrectedFolderPhotoListBox, false);
      }

      if (referenceFolderSelectionBox.getItems().size() > 1) {
        int toBeCorrectedFolderIndex = toBeCorrectedFolderSelectionBox.getSelectionModel().getSelectedIndex();
        int referenceFolderIndex;
        if (toBeCorrectedFolderIndex == 0) {
          referenceFolderIndex = 1;
        } else {
          referenceFolderIndex = 0;
        }
        referenceFolderSelectionBox.getSelectionModel().select(referenceFolderIndex);
      }
    });
    
    getDialogPane().getButtonTypes().add(ButtonType.OK);    
    getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    
    
    String referenceFolderName = referenceFolderSelectionBox.getSelectionModel().getSelectedItem();
    if (referenceFolderName != null) {
      timeReferencePhotoList = addPictureList(referenceFolderName, referenceFolderPhotoListBox, true);
    }
    String toBeCorrectedFolderName = toBeCorrectedFolderSelectionBox.getSelectionModel().getSelectedItem();
    if (toBeCorrectedFolderName != null) {
      timeToBeAdjustedPhotoList = addPictureList(toBeCorrectedFolderName, toBeCorrectedFolderPhotoListBox, false);
    }
  }

  /**
   * Add a photo list to one of the photo lists panel.
   * 
   * @param photoFolderName name of the photo folder, shown on top of the list.
   * @param photoListPanel the panel to which the list is to be added.
   * @param addBeforeTime
   * @return
   */
  private ListView<IPhotoMetaDataWithImage> addPictureList(String photoFolderName, VBox photoListPanel, boolean addBeforeTime) {
    photoListPanel.getChildren().clear();
    
    // Photo folder name on top
    Label folderNameLabel = new Label(photoFolderName);
    photoListPanel.getChildren().add(folderNameLabel);
    
    List<IPhotoInfo> photoInfoList = photoInfoListsMap.get(photoFolderName);

    ObservableList<IPhotoMetaDataWithImage> photos = FXCollections.observableArrayList(photoInfoList);

    ListView<IPhotoMetaDataWithImage> listView = new ListView<>();
    listView.setItems(photos);
    listView.setCellFactory(param -> new ListCell<IPhotoMetaDataWithImage>() {
      private ImageView imageView = new ImageView();
      private StackPane stackPane = new StackPane();

      @Override
      public void updateItem(IPhotoMetaDataWithImage photoInfo, boolean empty) {
        super.updateItem(photoInfo, empty);
        if (empty) {
          setText(null);
          setGraphic(null);
        } else {
          imageView.setFitHeight(150);
          imageView.setPreserveRatio(true);
          imageView.setImage(photoInfo.getImage());
          File aFile = new File(photoInfo.getFileName());
          stackPane.getChildren().clear();
          stackPane.getChildren().add(imageView);
          setText(aFile.getName());
          setGraphic(stackPane);

          setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
              System.out.println("Photo: " + photoInfo.getFileName());
              imageView.setFitHeight(150);
            }
          });

          setOnMouseEntered(event -> {
            if (stackPane.getChildren().size() == 1) {
              VBox vBox = new VBox();
              
              Button isBeforeButton = new Button("is Before");
              isBeforeButton.setOnAction(actionEvent -> {
                syncFolderTimes(addBeforeTime);
              });
              vBox.getChildren().add(isBeforeButton);
              
              Button correctTimeButton = new Button("correct time");
              correctTimeButton.setOnAction(actionEvent -> {
                correctFolderTimes(photoInfo);
              });
              vBox.getChildren().add(correctTimeButton);
              
              stackPane.getChildren().add(vBox);
//              StackPane.setAlignment(vBox, Pos.TOP_RIGHT);
            }
          });

          setOnMouseExited(event -> {
            ObservableList<Node> stackPaneChildren = stackPane.getChildren();
            if (stackPaneChildren.size() > 1) {
              stackPaneChildren.remove(1);
            }
          });
        }
      }
    });

    listView.setMinHeight(660.0);
    photoListPanel.getChildren().add(listView);

    return listView;
  }
  
  /**
   * Correct the times of the photos in a folder
   * 
   * @param timeReferencePhoto
   */
  private void correctFolderTimes(IPhotoMetaDataWithImage timeReferencePhoto) {
    LOGGER.severe("photo: " + timeReferencePhoto.getFileName());
    
    LocalDateTime timeReferencePhotoCreationDate = timeReferencePhoto.getDeviceSpecificPhotoTakenTime();

    if (timeReferencePhotoCreationDate != null) {
      final CorrectTimeDetailsDialog correctTimeDetailsDialog = new CorrectTimeDetailsDialog(ownerWindow, photoShowSpecification, timeReferencePhoto);
      correctTimeDetailsDialog.showAndWait();
    }
  }
  
  private void syncFolderTimes(boolean addBeforeTime) {
    IPhotoMetaDataWithImage timeReferencePhoto = timeReferencePhotoList.getSelectionModel().getSelectedItem();
    IPhotoMetaDataWithImage timeToBeAdjustedPhoto = timeToBeAdjustedPhotoList.getSelectionModel().getSelectedItem();
    
    if (timeReferencePhoto == null) {
      LOGGER.severe("No time reference photo selected");
    } else if (timeToBeAdjustedPhoto == null) {
      LOGGER.severe("No time to be adjusted photo selected");
    } else {
      LocalDateTime timeReferencePhotoCreationDate =  null;
      try {
        PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(new File(timeReferencePhoto.getFileName()));
        timeReferencePhotoCreationDate = photoFileMetaDataHandler.getCreationDateTime();
      } catch (ImageReadException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      LocalDateTime timeToBeAdjustedPhotoCreationDate =  null;
      try {
        PhotoFileMetaDataHandler photoFileMetaDataHandler = new PhotoFileMetaDataHandler(new File(timeToBeAdjustedPhoto.getFileName()));
        timeToBeAdjustedPhotoCreationDate = photoFileMetaDataHandler.getCreationDateTime();
      } catch (ImageReadException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      
      if (timeReferencePhotoCreationDate != null  &&  timeToBeAdjustedPhotoCreationDate != null) {
        timeOffset = Duration.between(timeToBeAdjustedPhotoCreationDate, timeReferencePhotoCreationDate);
        final SyncDetailsDialog syncDetailsDialog = new SyncDetailsDialog(ownerWindow, timeReferencePhotoCreationDate, timeToBeAdjustedPhotoCreationDate, timeOffset);
        Optional<ButtonType> result = syncDetailsDialog.showAndWait();
        
        if (result.isPresent()  &&  result.get() == ButtonType.OK) {
          Duration beforeTime = syncDetailsDialog.getBeforeTime();
          if (addBeforeTime) {
            timeOffset = timeOffset.plus(beforeTime);
          } else {
            timeOffset = timeOffset.minus(beforeTime);
          }
          timeToBeAdjustedFolderName = toBeCorrectedFolderSelectionBox.getSelectionModel().getSelectedItem();
        }
        
      }
    }
  }

}

class SyncDetailsDialog extends Dialog<ButtonType> {
  private final static String WINDOW_TITLE = "Time synchronization details";
  public static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  
  private TextField beforeText;
  private Button okButton;    // this will only be enabled if the before time is valid (text can be parsed and isn't 0).
  
  private LocalDateTime referencePhotoCreationDate;
  private LocalDateTime toBeAdjustedPhotoCreationDate;
  private Duration timeOffset;
  private Duration beforeTime = Duration.ofSeconds(1l);
  
  public SyncDetailsDialog(Stage ownerWindow, LocalDateTime referencePhotoCreationDate, LocalDateTime toBeAdjustedPhotoCreationDate, Duration timeOffset) {
    this.referencePhotoCreationDate = referencePhotoCreationDate;
    this.toBeAdjustedPhotoCreationDate = toBeAdjustedPhotoCreationDate;
    this.timeOffset = timeOffset;
    
    setTitle(WINDOW_TITLE);
    
    initOwner(ownerWindow);
    
    createGUI();
    setResizable(true);
    
    interpretBeforeText();
  }
  
  public Duration getBeforeTime() {
    return beforeTime;
  }
  
  private void createGUI() {
    setHeaderText("The time difference between the photos is shown. Specify how long one photo is taken before the other photo.");
    
    GridPane detailsPane = new GridPane();
    
    Label label = new Label("Reference photo is taken at:");
    detailsPane.add(label, 0, 0);
    Text text = new Text(referencePhotoCreationDate.format(DTF));
    detailsPane.add(text, 1, 0);
    
    label = new Label("To be adjusted photo is taken at:");
    detailsPane.add(label, 0, 1);
    text = new Text(toBeAdjustedPhotoCreationDate.format(DTF));
    detailsPane.add(text, 1, 1);
    
    label = new Label("Time difference:");
    detailsPane.add(label, 0, 2);
    text = new Text(DurationUtil.durationToString(timeOffset, true));
    detailsPane.add(text, 1, 2);
    
    label = new Label("How long before?:");
    detailsPane.add(label, 0, 3);
    beforeText = new TextField();
    beforeText.setText(DurationUtil.durationToString(beforeTime, false));
    beforeText.textProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        System.out.println("ChangeListener: text = " + beforeText.getText());        
        interpretBeforeText();
      }
      
    });
    detailsPane.add(beforeText, 1, 3);
    
    getDialogPane().setContent(detailsPane);

    getDialogPane().getButtonTypes().add(ButtonType.OK);    
    getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    
    okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
  }
  
  private void interpretBeforeText() {
    boolean beforeTimeValid = false;
    
    try {
      beforeTime = DurationUtil.durationFromString(beforeText.getText());
      beforeTimeValid = (beforeTime != null)  && !beforeTime.isZero();
    } catch ( java.lang.NumberFormatException e) {
      // no action
    }
    
    okButton.setDisable(!beforeTimeValid);
  }
}


/**
 * Dialog to enter and handle a time correction.
 */
class CorrectTimeDetailsDialog extends Dialog<ButtonType> {
  private final static Logger LOGGER = Logger.getLogger(CorrectTimeDetailsDialog.class.getName());
  private final static String WINDOW_TITLE = "Time correction details";
  public static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  
  private Button okButton;    // this will only be enabled if the before time is valid (text can be parsed and isn't 0).
  
  private PhotoShowSpecification photoShowSpecification;
  private IPhotoMetaDataWithImage timeReferencePhoto;
  private LocalDateTime correctedPhotoCreationDate;
  private TextField actualDateTimeTextField;
  private List<InfoPerFolder> folderInfos = new ArrayList<>();
  
  public CorrectTimeDetailsDialog(Stage ownerWindow, PhotoShowSpecification photoShowSpecification, IPhotoMetaDataWithImage timeReferencePhoto) {
    this.photoShowSpecification = photoShowSpecification;
    this.timeReferencePhoto = timeReferencePhoto;
    
    setTitle(WINDOW_TITLE);
    
    correctedPhotoCreationDate = LocalDateTime.from(timeReferencePhoto.getDeviceSpecificPhotoTakenTime());
    
    String folder = getPhotoFolder(timeReferencePhoto);
    Duration timeOffset = getFolderTimeOffset(folder);
    if (timeOffset != null) {
      correctedPhotoCreationDate = correctedPhotoCreationDate.plus(timeOffset);
    }
    createPhotoInfos();
    
    initOwner(ownerWindow);
    
    createGUI();
    setResizable(true);
    
    handleChanges();
  }
  
  private void createPhotoInfos() {
    for (String folder: photoShowSpecification.getPhotoFolders()) {
      
      CheckBox applyToThisFolderCheckBox = new CheckBox(folder);
      applyToThisFolderCheckBox.setSelected(true);
      String timeReferencePhotoFolder = getPhotoFolder(timeReferencePhoto);
      if (folder.equals(timeReferencePhotoFolder)) {
        applyToThisFolderCheckBox.setDisable(true);  // This folder shall always be updated.
      }
      
      Duration timeOffset = getFolderTimeOffset(folder);
      CheckBox keepCurrentOffsetCheckBox = null;
      if (timeOffset != null) {
        // Only add this check box if there is an offset.
        keepCurrentOffsetCheckBox = new CheckBox("Keep current offset");
      }
      
      TextField currentOffsetTextField = new TextField();
      if (timeOffset != null) {
        currentOffsetTextField.setText(DurationUtil.durationToString(timeOffset, true));
      }
      currentOffsetTextField.setEditable(false);
      
      TextField newOffsetTextField = new TextField();
      newOffsetTextField.setEditable(false);
      
      InfoPerFolder infoPerFolder = new InfoPerFolder(folder, timeOffset, applyToThisFolderCheckBox, keepCurrentOffsetCheckBox, currentOffsetTextField, newOffsetTextField);
      folderInfos.add(infoPerFolder);
    }
    
  }
  
  private void createGUI() {
    setHeaderText("Specify the time you think the photo is actually taken");
    
    GridPane detailsPane = new GridPane();
    detailsPane.setPadding(new Insets(12.0));
    detailsPane.setHgap(12.0);
    detailsPane.setVgap(12.0);
    int row = 0;
    
    // row: 'Photo is taken at:' <taken-at-time> (fixed value)
    Label label = new Label("Photo is taken at:");
    detailsPane.add(label, 0, row);
    TextField photoTakenAtTextField = new TextField(timeReferencePhoto.getDeviceSpecificPhotoTakenTime().format(DTF));
    photoTakenAtTextField.setEditable(false);
    detailsPane.add(photoTakenAtTextField, 1, row);
    
    row++;
    
    // row: 'When was the photo actually taken?' <actually-taken-at> (input value)
    label = new Label("When was the photo actually taken?");
    detailsPane.add(label, 0, row);
    actualDateTimeTextField = new TextField();
    actualDateTimeTextField.setText(correctedPhotoCreationDate.format(DTF));  // set initial value, later only changed by the user.
    actualDateTimeTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> handleChanges());
    detailsPane.add(actualDateTimeTextField, 1, row);
    
    row++;
    
    // row: header 'Apply to folders'
    label = new Label("Apply to folders:");
    detailsPane.add(label, 0, row);
    
    // one row per folder: <check-box> <folder-name> <checkbox> <time-offset> (only if there is an offset)
    for (InfoPerFolder folderInfo: folderInfos) {
      row++;
      
      CheckBox folderCheckBox = folderInfo.applyToThisFolderCheckBox();
      folderCheckBox.setOnAction(event -> handleChanges());
      detailsPane.add(folderCheckBox, 0, row, 2, 1);
      
      CheckBox keepOffsetCheckBox = folderInfo.keepCurrentOffsetCheckBox();
      if (keepOffsetCheckBox != null) {
        keepOffsetCheckBox.setSelected(true);
        keepOffsetCheckBox.setOnAction(event -> handleChanges());
        detailsPane.add(keepOffsetCheckBox, 2, row, 2, 1);
      }
      
      row++;
      
      // row: 'Current offset:' <current-offset> 'New offset:' <new-offset>
      label = new Label("Current offset:");
      detailsPane.add(label, 0, row);
      TextField currentOffsetTextField = folderInfo.currentOffsetTextField();
      detailsPane.add(currentOffsetTextField, 1, row);
      
      label = new Label("New offset:");
      detailsPane.add(label, 2, row);
      TextField newOffsetTextField = folderInfo.newOffsetTextField();
      detailsPane.add(newOffsetTextField, 3, row);
    }
    
    getDialogPane().setContent(detailsPane);

    getDialogPane().getButtonTypes().add(ButtonType.OK);    
    getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    
    okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
    okButton.setOnAction(value -> updatePhotoShowSpecification());
  }
  
  /**
   * Get the time offset for a folder.
   * 
   * @param folder the folder
   * @return the time offset for {@code folder}, or null if there is no time offset for this folder.
   */
  private Duration getFolderTimeOffset(String folder) {
    for (FolderTimeOffsetSpecification folderTimeOffsetSpecification: photoShowSpecification.getFolderTimeOffsetSpecifications()) {
      if (folderTimeOffsetSpecification.getFolderName().equals(folder)) {
        String timeOffsetText = folderTimeOffsetSpecification.getTimeOffset();
        return DurationUtil.durationFromString(timeOffsetText);
      }
    }
    
    return null;
  }
  
  /**
   * Get the folder name for a photo's {@code PhotoMetaDataWithImage}.
   * 
   * @param photoInfo the {@code PhotoMetaDataWithImage} of the photo.
   * @return The folder name of the folder in which the photo is located.
   */
  private String getPhotoFolder(IPhotoMetaDataWithImage photoInfo) {
    File file = new File(photoInfo.getFileName());
    return file.getParent();
  }

  /**
   * Handle a change in any control.
   */
  private void handleChanges() {
    LOGGER.severe("=>");
    boolean timeValid = getActualDateTimeFromTextField();
    
    if (timeValid) {
      updateFolderInfos();
    }
  }
  
  /**
   * Get the value of the {@code actualDateTimeTextField}.
   * 
   * @return the {@code LocalDateTime} value for the value of the {@code actualDateTimeTextField}. Null is returned in case of an invalid value.
   */
  private boolean getActualDateTimeFromTextField() {
    LOGGER.severe("=>");
    boolean timeValid = false;
    
    try {
      
      LocalDateTime localDateTime = LocalDateTime.parse(actualDateTimeTextField.getText(), DTF);
      correctedPhotoCreationDate = localDateTime;
      timeValid = true;
    } catch (java.time.format.DateTimeParseException | java.lang.NumberFormatException e) {
      // no action
    }
    
    okButton.setDisable(!timeValid);
    
    LOGGER.severe("<= " + timeValid);
    return timeValid;
  }
  
  /**
   * Update {@code folderInfos} for new GUI control values.
   */
  private void updateFolderInfos() {
    LOGGER.severe("=>");
    Duration timeCorrection = Duration.between(timeReferencePhoto.getDeviceSpecificPhotoTakenTime(), correctedPhotoCreationDate);
    
    for (InfoPerFolder folderInfo: folderInfos) {
      TextField newOffsetTextField = folderInfo.newOffsetTextField();
      CheckBox applyToThisFolderCheckBox = folderInfo.applyToThisFolderCheckBox();
      Duration duration = null;
      if (applyToThisFolderCheckBox.isSelected()) {
        duration = timeCorrection;
        CheckBox keepCurrentOffsetCheckBox = folderInfo.keepCurrentOffsetCheckBox();
        if ((keepCurrentOffsetCheckBox != null)  &&  keepCurrentOffsetCheckBox.isSelected()) {
          duration = duration.plus(folderInfo.currentOffset());
        }
      } else {
        duration = folderInfo.currentOffset();
      }
      if (duration != null) {
        newOffsetTextField.setText(DurationUtil.durationToString(duration, true));
      } else {
        newOffsetTextField.setText(null);
      }
    }
    
  }
  
  private void updatePhotoShowSpecification() {
    List<FolderTimeOffsetSpecification> folderTimeOffsetSpecifications = photoShowSpecification.getFolderTimeOffsetSpecifications();
    folderTimeOffsetSpecifications.clear();
    PhotoShowFactory photoShowFactory = PhotoShowFactory.eINSTANCE;
    
    Duration timeCorrection = Duration.between(timeReferencePhoto.getDeviceSpecificPhotoTakenTime(), correctedPhotoCreationDate);
    
    for (InfoPerFolder folderInfo: folderInfos) {
      CheckBox applyToThisFolderCheckBox = folderInfo.applyToThisFolderCheckBox();
      if (applyToThisFolderCheckBox.isSelected()) {
        Duration duration = timeCorrection;
        CheckBox keepCurrentOffsetCheckBox = folderInfo.keepCurrentOffsetCheckBox();
        if ((keepCurrentOffsetCheckBox != null)  &&  keepCurrentOffsetCheckBox.isSelected()) {
          duration = duration.plus(folderInfo.currentOffset());
        }
        FolderTimeOffsetSpecification folderTimeOffsetSpecification = photoShowFactory.createFolderTimeOffsetSpecification();
        folderTimeOffsetSpecification.setFolderName(folderInfo.folder());
        folderTimeOffsetSpecification.setTimeOffset(DurationUtil.durationToString(duration, true));
        folderTimeOffsetSpecifications.add(folderTimeOffsetSpecification);
      }
    }
  }
}

record InfoPerFolder(String folder, Duration currentOffset, CheckBox applyToThisFolderCheckBox, CheckBox keepCurrentOffsetCheckBox, TextField currentOffsetTextField, TextField newOffsetTextField) {
  
}
