package goedegep.events.app.guifx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import goedegep.events.app.EventsRegistry;
import goedegep.events.model.EventInfo;
import goedegep.events.model.Events;
import goedegep.events.model.EventsFactory;
import goedegep.events.model.EventsPackage;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlBoolean;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlHTMLString;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.jfx.objecteditor.FileReferenceEditPanel;
import goedegep.jfx.objecteditor.FileReferenceTypeInfo;
import goedegep.jfx.objecteditor.FileReferencesEditPanel;
import goedegep.jfx.objecteditor.FileReferencesEditPanel.FileReferencesEditPanelBuilder;
import goedegep.jfx.objecteditor.ObjectEditorTemplate;
import goedegep.types.model.TypesPackage;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.dir.DirectoryChangesMonitoringTask;
import goedegep.util.emf.EmfUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class provides a panel to show and edit an event (of type {@link EventInfo}).
 */
public class EventsEditor extends ObjectEditorTemplate<EventInfo> {
  private static final Logger LOGGER = Logger.getLogger(EventsEditor.class.getName());
  private static final String WINDOW_TITLE = "Event editor";
  private static final String ATTACHMENTS_PANEL_TITLE = "Attachments";
  private static final String ATTACHMENT_PANEL_TITLE = "Attachment";
  private static final FlexDateFormat FDF =  new FlexDateFormat(true, true);
  
  /**
   * The collection of events to which a new event will be added.
   */
  private Events events = null;
  
  /*
   * Controls
   */
  
  /**
   * {@code ObjectControl} for the date of the event.
   */
  private ObjectControlFlexDate eventDateControl;
  
  /**
   * {@code ObjectControl} for the title of the event.
   */
  private ObjectControlString eventTitleControl;
  
  /**
   * {@code ObjectControl} for the folder where files related to the event are stored.
   */
  private ObjectControlString eventFolderControl;
  
  /**
   * {@code ObjectControl} to indicate whether the event folder already exists or not.
   */
  private ObjectControlBoolean eventFolderExistsControl;
  
  /**
   * {@code ObjectControl} for the picture representing the event.
   */
  private ObjectControlFileSelecter pictureFileSelecter;
  
  /**
   * {@code ObjectControl} for notes about the event.
   */
  private ObjectControlHTMLString notesControl;
  
  /*
   * Other GUI items
   */
  private ImageView pictureImageView;
  
  private FileReferencesEditPanel attachmentsEditPanel;
  
  private Button createEventsFolderButton;
  
  /**
   * List of panels, one for each element.
   */
  private ObservableList<FileReferenceEditPanel> attachmentPanels;
//  
//  /**
//   * Panel in which the {@code attachmentPanels} are shown.
//   */
//  private VBox attachmentsVBox;
  
  /**
   * The generated or actual event folder (the folder where attachments of {@code event} are located).
   * <p>
   * If there is an attachment with a file reference to a sub folder of the Events Folder, this is the event folder.
   * Otherwise if there is an event date and an event title, the folder is derived from these values.
   */
  private SimpleObjectProperty<File> generatedEventFolder;
  
  
  /**
   * Factory method to obtain a new instance of an {@code EventsEditor}.
   * 
   * @param customization the GUI customization.
   * @param events the {link Events} to which a new {@code Event} will be added.
   * @return a newly created {@code EventsEditor}.
   */
  public static EventsEditor newInstance(CustomizationFx customization, Events events) {
    Objects.requireNonNull(events, "events may not be null");
    
    EventsEditor eventsEditor = new EventsEditor(customization, events);
    eventsEditor.performInitialization();
    
    return eventsEditor;
  }
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param events the {@code Events} to which a new {@code Event} will be added.
   */
  private EventsEditor(CustomizationFx customization, Events events) {
    super(customization, WINDOW_TITLE);
    
    this.events = events;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureEditor() {
    setAddObjectTexts("Add event", "Add the event to the events");
    setUpdateObjectTexts("Update", "Update the current event");
    setNewObjectTexts("New", "Clear the controls to start entering new event data");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createControls() {
    // Create object controls
    eventDateControl = componentFactory.createObjectControlFlexDate(null, 300, false, "Date of the event");
    eventDateControl.setId("event date");
    eventDateControl.setLabelBaseText("Date");
    eventDateControl.setErrorTextSupplier(() -> "The event date is not filled in");
    
    eventTitleControl = componentFactory.createObjectControlString(null, 300, false, "Title of the event");
    eventTitleControl.setId("event title");
    eventTitleControl.setLabelBaseText("Title");
    eventTitleControl.setErrorTextSupplier(() -> "The event title is not filled in");
    
    eventFolderControl = componentFactory.createObjectControlString(null, 300, true, "Folder where event attachments are stored");
    eventFolderControl.setId("event folder");
    eventFolderControl.setLabelBaseText("Event folder");
    eventFolderControl.getControl().setDisable(true);
    
    eventFolderExistsControl = componentFactory.createObjectControlBoolean(null, false, true, "Checked indicates that the event folder exists");
    eventFolderExistsControl.setId("events folder exists");
    
    createEventsFolderButton = componentFactory.createButton("create", "Click to create the event folder as shown on the left");
    createEventsFolderButton.setId("create events folder");
    createEventsFolderButton.setOnAction(this::createEventsFolder);
    
    pictureFileSelecter = componentFactory.createFileSelecterObjectControl(300, "file name of a picture", "file chooser", "Start a file chooser", "Select picture file", true);
    pictureFileSelecter.setId("pictureFileSelecter");
    pictureFileSelecter.setLabelBaseText("Picture");
    pictureFileSelecter.setInitialFolderProvider(this::getEventRelatedFilesFolder);
    pictureFileSelecter.setPrefix(EventsRegistry.eventsFolderName + "\\");
    
    pictureImageView = new ImageView();
    
    notesControl = componentFactory.createObjectControlHTMLString(null, true);
    notesControl.setId("notes");
    notesControl.setLabelBaseText("Notes");
    
    attachmentsEditPanel = new FileReferencesEditPanelBuilder(customization)
        .setReferencesEditPanelTitle(ATTACHMENTS_PANEL_TITLE)
        .setReferenceEditPanelTitle(ATTACHMENT_PANEL_TITLE)
        .setAddFileReferenceButtonText("Add attachment")
        .setAddFileReferenceButtonTooltipText("Add an attachment")
        .setInitialFolderSupplier(this::getInitialFolder)
        .addFileReferenceTypes(
            new FileReferenceTypeInfo(AttachmentTypeInfo.FILE.getTag(), AttachmentTypeInfo.FILE.getDisplayName(), false, EventsRegistry.eventsFolderName + "\\"),
            new FileReferenceTypeInfo(AttachmentTypeInfo.PHOTO_FOLDER.getTag(), AttachmentTypeInfo.PHOTO_FOLDER.getDisplayName(), true, null),
            new FileReferenceTypeInfo(AttachmentTypeInfo.VIDEO_TAKES_FOLDER.getTag(), AttachmentTypeInfo.VIDEO_TAKES_FOLDER.getDisplayName(), true, null)
            )
        .build();
    attachmentsEditPanel.setId("attachments");
    
    // Add the object controls to the {@code objectControlsGroup}
    objectControlsGroup.setId("events editor");
    objectControlsGroup.addObjectControls(eventDateControl, eventTitleControl, notesControl, pictureFileSelecter);
    objectControlsGroup.addObjectControlGroup(attachmentsEditPanel.getObjectControlsGroup());
    
    generatedEventFolder = new SimpleObjectProperty<>();
    generatedEventFolder.addListener((e) -> updateEventFolderInfo());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillControlsWithDefaultValues() {
    eventDateControl.setValue(null);
    eventTitleControl.setValue(null);
    eventFolderControl.setValue(null);
    pictureFileSelecter.setValue(null);
    notesControl.setValue(null);
    attachmentsEditPanel.setObject(new ArrayList<>());
    
    eventFolderExistsControl.setValue(false);
  }
    
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel(VBox rootPane) {
    
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    
    // Date
    gridPane.add(eventDateControl.getLabel(), 0, 0);
    gridPane.add(eventDateControl.getControl(), 1, 0);
    gridPane.add(eventDateControl.getStatusIndicator(), 2, 0);
    
    // Title + Event folder
    gridPane.add(eventTitleControl.getLabel(), 0, 1);
    gridPane.add(eventTitleControl.getControl(), 1, 1);
    gridPane.add(eventTitleControl.getStatusIndicator(), 2, 1);
    
    HBox eventFolderControlsBox = componentFactory.createHBox(12.0);    
    eventFolderControlsBox.getChildren().add(eventFolderControl.getLabel());
    eventFolderControlsBox.getChildren().add(eventFolderControl.getControl());
    eventFolderControlsBox.getChildren().add(eventFolderExistsControl.getControl());
    eventFolderControlsBox.getChildren().add(createEventsFolderButton);

    gridPane.add(eventFolderControlsBox, 3, 1);
    
    // Picture
    gridPane.add(pictureFileSelecter.getLabel(), 0, 2);
    gridPane.add(pictureFileSelecter.getControl(), 1, 2);
    HBox pictureControlsBox = componentFactory.createHBox(12.0);
    pictureControlsBox.getChildren().addAll(pictureFileSelecter.getStatusIndicator(), pictureFileSelecter.getFileChooserButton());
    gridPane.add(pictureControlsBox, 2, 2);
        
    // Notes + Picture image view
    gridPane.add(notesControl.getLabel(), 0, 3);
    gridPane.add(notesControl.getControl(), 1, 3);
    gridPane.add(notesControl.getStatusIndicator(), 2, 3);
    
    pictureImageView.setFitWidth(400);
    pictureImageView.setFitHeight(400);
    pictureImageView.setPreserveRatio(true);
    gridPane.add(pictureImageView, 3, 3, 3, 2);
    
    rootPane.getChildren().add(gridPane);
    
    rootPane.getChildren().add(attachmentsEditPanel.getControl());
    
    
    // TODO temp for development
    Button showObjectControlGroupButton = new Button("ObjectControlsGroup");
    showObjectControlGroupButton.setOnAction(e -> objectControlsGroup.print());
    rootPane.getChildren().add(showObjectControlGroupButton);
    
//    rootPane.getChildren().add(createAttachmentsPanel());
  }
  
//  /**
//   * Create the attachments panel.
//   * 
//   * @return the created attachments panel.
//   */
//  private Node createAttachmentsPanel() {
//    VBox documentsMainVBox = componentFactory.createVBox(12.0, 12.0);
//    documentsMainVBox.setMinSize(300, 300);
//    documentsMainVBox.setBorder(componentFactory.getRectangularBorder());
//    
//    Label label = componentFactory.createStrongLabel("Attachments");
//    documentsMainVBox.getChildren().add(label);
//    
//    attachmentsVBox = componentFactory.createVBox();
//    attachmentPanels = FXCollections.observableArrayList();
//    attachmentPanels.addListener(new ListChangeListener<FileReferenceEditPanel>() {
//
//      @Override
//      public void onChanged(Change<? extends FileReferenceEditPanel> c) {
//        while (c.next()) {
//          if (c.wasPermutated()) {  // NOPMD
//            // No action needed here
//          } else if (c.wasUpdated()) {
//            LOGGER.severe("Update not handled!!");
//          } else {
//            for (FileReferenceEditPanel documentReferencePanel: c.getRemoved()) {
//              objectControlsGroup.removeObjectControlGroup(documentReferencePanel.getObjectControlsGroup());
//              handleChanges();
//            }
//            for (FileReferenceEditPanel documentReferencePanel: c.getAddedSubList()) {
//              objectControlsGroup.addObjectControlGroup(documentReferencePanel.getObjectControlsGroup());
//              handleChanges();
//            }
//          }
//        }
//        
//        updateAttachmentPanel();
//        
//      }
//      
//    });
//
//    documentsMainVBox.getChildren().add(attachmentsVBox);
//    
//    Button newAttachmentButton = componentFactory.createButton("+ Add attachment", "Add an attachment");
//    newAttachmentButton.setOnAction(e -> createNewAttachmentPanel(true));
//    documentsMainVBox.getChildren().add(newAttachmentButton);
//    
//    return documentsMainVBox;
//  }
  
//  /**
//   * Update the attachments panel.
//   */
//  private void updateAttachmentPanel() {
//    attachmentsVBox.getChildren().clear();
//    for (FileReferenceEditPanel fileReferenceEditPanel: attachmentPanels) {
//      attachmentsVBox.getChildren().add(fileReferenceEditPanel.getControl());
//    }
//  }
  
//  /**
//   * Create a new attachment panel.
//   * 
//   * @param expand if true, the panel will be expanded upon creation.
//   * @return the created attachment panel.
//   */
//  private FileReferenceEditPanel createNewAttachmentPanel(boolean expand) {
//    FileReferenceEditPanel attachmentPanel = new FileReferenceEditPanel.FileReferencePanelBuilder(customization)
//        .setDefaultPaneTitle(ATTACHMENT_PANEL_TITLE)
//        .setExpandPaneOnCreation(expand)
//        .setInitialFolderSupplier(this::getEventRelatedFilesFolder)
//        .addFileReferenceTypes(
//            new FileReferenceTypeInfo(AttachmentTypeInfo.FILE.getTag(), AttachmentTypeInfo.FILE.getDisplayName(), false, EventsRegistry.eventsFolderName + "\\"),
//            new FileReferenceTypeInfo(AttachmentTypeInfo.PHOTO_FOLDER.getTag(), AttachmentTypeInfo.PHOTO_FOLDER.getDisplayName(), true, null),
//            new FileReferenceTypeInfo(AttachmentTypeInfo.VIDEO_TAKES_FOLDER.getTag(), AttachmentTypeInfo.VIDEO_TAKES_FOLDER.getDisplayName(), true, null)
//            )
//        .build();
//
//    objectControlsGroup.addObjectControlGroup(attachmentPanel.getObjectControlsGroup());
//    attachmentPanels.add(attachmentPanel);
//
//    return attachmentPanel;
//  }
  
  /**
   * Install listeners for changes in the controls.
   * <p>
   * And in this case listen to changes in the events folder.
   */
  protected void installChangeListeners() {
    super.installChangeListeners();

    if (EventsRegistry.eventsFolderName != null) {
      DirectoryChangesMonitoringTask directoryMonitoringTask = new DirectoryChangesMonitoringTask(EventsRegistry.eventsFolderName);

      directoryMonitoringTask.valueProperty().addListener((observable, oldValue, newValue) -> {
        String fileRelatedFilesFolderName = getEventRelatedFilesFolder();
        if (fileRelatedFilesFolderName != null) {
          File file = new File(fileRelatedFilesFolderName);
          if (file.exists() && file.isDirectory()) {
            eventFolderExistsControl.setValue(true);
            createEventsFolderButton.setDisable(true);
          } else {
            eventFolderExistsControl.setValue(false);
            createEventsFolderButton.setDisable(false);
          }
        }
      });

      Thread directoryMonitoringThread = new Thread(directoryMonitoringTask);
      directoryMonitoringThread.setDaemon(true);
      directoryMonitoringThread.start();
    }
  }
  
  private String getInitialFolder(FileReferenceTypeInfo fileReferenceTypeInfo) {
    if (fileReferenceTypeInfo == null) {
      return null;
    }
        
    String result = null;
    String tag = fileReferenceTypeInfo.tag();
    if (tag.equals(AttachmentTypeInfo.FILE.getTag())) {
      result = getEventRelatedFilesFolder();
    } else if (tag.equals(AttachmentTypeInfo.PHOTO_FOLDER.getTag())) {
      result = "D:\\Photo";
    } else if (tag.equals(AttachmentTypeInfo.VIDEO_TAKES_FOLDER.getTag())) {
      result = "D:\\Video\\Edit\\Takes";
    } else {
      throw new IllegalArgumentException("Unknown tag: '" + tag + "'");
    }
    
    return result;
  }
  
  /**
   * Get the event related files folder, or if this is not available, the Events Folder.
   * 
   * @return the event related files folder, or if this is not available, the Events Folder.
   */
  private String getEventRelatedFilesFolder() {
    if (generatedEventFolder.get() != null) {
      return generatedEventFolder.get().getAbsolutePath();
    } else {
      return EventsRegistry.eventsFolderName;
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createObject() {
    object = EventsFactory.eINSTANCE.createEventInfo();
  }
  
  /**
   * {@inheritDoc}
   * <p>
   * The events are kept ordered by date.
   */
  @Override
  protected void addObjectToCollection() {
    List<EventInfo> eventList = events.getEvents();
    
    FlexDate newEventDate = object.getDate();
    boolean eventInserted = false;
    for (int index = 0; index < eventList.size(); index++) {
      EventInfo event = eventList.get(index);
      if (newEventDate.compareTo(event.getDate()) == 1) {
        eventList.add(index, object);
        eventInserted = true;
        break;
      }
    }
    if (!eventInserted) {
      eventList.add(object);
    }
  }
    
  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateObjectFromControls() {
    EmfUtil.setFeatureValue(object, TypesPackage.eINSTANCE.getEvent_Date(), eventDateControl.getValue());
    EmfUtil.setFeatureValue(object, EventsPackage.eINSTANCE.getEventInfo_Title(), eventTitleControl.getValue());
    EmfUtil.setFeatureValue(object, EventsPackage.eINSTANCE.getEventInfo_Picture(), pictureFileSelecter.getPathNameRelativeToPrefix());
    EmfUtil.setFeatureValue(object, TypesPackage.eINSTANCE.getEvent_Notes(), notesControl.getValue());
    EmfUtil.setFeatureValue(object, TypesPackage.eINSTANCE.getEvent_Notes(), notesControl.getValue());
    attachmentsEditPanel.updateObject();
  }
  

//  /**
//   * Update a {@code FileReference} from a {@code FileReferencePanel}.
//   * 
//   * @param fileReference the {@code FileReference} to update
//   * @param fileReferencePanel the {@code FileReferencePanel} from which the {@code fileReference} will be updated.
//   */
//  private void updateFileReferenceFromFileReferencePanel(FileReference fileReference, FileReferenceEditPanel fileReferencePanel) {
//    FileReferenceTypeInfo fileReferencePanelReferenceType = fileReferencePanel.getReferenceType();
//    if (fileReferencePanelReferenceType != null) {
//      fileReference.setTags(fileReferencePanelReferenceType.tag());
//      LOGGER.severe("tags set to: " + fileReferencePanelReferenceType.tag());
//    }
//    
//    if (fileReferencePanel.getPathNameRelativeToPrefix() != null) {
//      String filename = fileReferencePanel.getPathNameRelativeToPrefix();
//      
//      fileReference.setFile(filename);
//      LOGGER.severe("file set to: " + filename);
//    }
//
//    if (fileReferencePanel.getTitleObjectControl().isFilledIn()) {
//      fileReference.setTitle(fileReferencePanel.getTitleObjectControl().getValue());
//      LOGGER.severe("title set to: " + fileReferencePanel.getTitleObjectControl().getValue());
//    }    
//  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected Double getWindowHeight() {
    return 1320.0;
  }  
  
  /**
   * Update the {@code generatedEventFolder}
   * <p>
   * If there is an attachment with a file reference to a sub folder of the Events Folder, this is the event folder.
   * Otherwise if there is an event date and an event title, the folder is derived from these values.
   */
  private void updateGeneratedEventFolder() {
    String eventFolderName = deriveEventFolderNameFromAttachments();
    
    if (eventFolderName == null) {
      eventFolderName = deriveEventFolderNameFromDateAndTitle();
    }
    
    generatedEventFolder.set(eventFolderName != null ? new File(eventFolderName) : null);
  }
  
  /**
   * Get the event folder name derived from the attachments.
   * <p>
   * The event folder is the folder of the first attachment, which is a sub folder of the Events Folder.
   * 
   * @return the event folder name derived from the attachments, or null if this cannot be determined.
   */
  private String deriveEventFolderNameFromAttachments() {
    if (EventsRegistry.eventsFolderName == null) {
      return null;
    }
    
    for (FileReferenceEditPanel fileReferenceEditPanel: attachmentsEditPanel.getFileReferenceEditPanels()) {
      File file = fileReferenceEditPanel.getFile();
      if (file != null ) {
        file = file.getParentFile();  // This is the possible event directory
        if (file != null) {
          File eventsFolder = file.getParentFile();
          String eventsFolderName = eventsFolder.getAbsolutePath();
          if (eventsFolderName != null  &&  eventsFolderName.equals(EventsRegistry.eventsFolderName)) {
            String eventFolderName = file.getAbsolutePath();
            return eventFolderName;
          }
        }
      }
    }
    
    return null;
  }
  
  /**
   * Update information related to the event folder.
   */
  private void updateEventFolderInfo() {
    File eventFolder = generatedEventFolder.get();
    String eventFolderName = eventFolder != null ? eventFolder.getAbsolutePath() : null;
    
    eventFolderControl.setValue(eventFolderName);
    
    if (eventFolder != null) {
      eventFolderExistsControl.setValue(eventFolder.exists());
    } else {
      eventFolderExistsControl.setValue(false);
    }
    
    // Disable the create events folder button if we don't know the folder name or if it already exists.
    createEventsFolderButton.setDisable((eventFolder == null)  ||  eventFolderExistsControl.getValue());    
  }
  
  /**
   * Update the {@code pictureImageView}.
   */
  private void updatePictureImageView() {
    LOGGER.info("=>");
    File pictureFile = pictureFileSelecter.getValue();
    
    if (pictureFile != null) {
      Image picture = new Image("file:" + pictureFile.getAbsolutePath());
      LOGGER.info("Image loaded from: " + pictureFile.getAbsolutePath());
      pictureImageView.setImage(picture);
    } else {
      LOGGER.info("Clearing image");
      pictureImageView.setImage(null);
    }
  }
  
  /**
   * Derive the event folder from the date and title controls.
   * 
   * @return the event folder derived from the date and title controls, or null if these controls don't have the needed information.
   */
  private String deriveEventFolderNameFromDateAndTitle() {
    FlexDate eventDate = eventDateControl.getValue();
    if (eventDate == null) {
      return null;
    }
    String eventDateText = FDF.format(eventDate);
    
    String title = eventTitleControl.getValue();
    if (title == null) {
      return null;
    }
    
    String subFolderName = eventDateText + " " + title;
    File eventFolder = new File(EventsRegistry.eventsFolderName, subFolderName);
    
    return eventFolder.getAbsolutePath();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillControlsFromObject() {
    if (object == null) {
      return;
    }
    eventDateControl.setValue(object.getDate());
    eventTitleControl.setValue(object.getTitle());
    pictureFileSelecter.setPathNameRelativeToPrefix(object.getPicture());
    notesControl.setValue(object.getNotes());
    
    attachmentsEditPanel.setObject(object.getAttachments());
    
//    attachmentPanels.clear();
//    for (FileReference attachment: object.getAttachments()) {
//      FileReferenceEditPanel fileReferencePanel = createNewAttachmentPanel(false);
//      fillFileReferencePanelFromAttachment(fileReferencePanel, attachment);
//    }
  }
  
//  /**
//   * Fill a {@code FileReferencePanel} from a {@code FileReference}.
//   * 
//   * @param fileReferencePanel the {@code FileReferencePanel} to be filled.
//   * @param attachment the attachment (a {@code FileReference} to fill the {@code fileReferencePanel} from.
//   */
//  private void fillFileReferencePanelFromAttachment(FileReferenceEditPanel fileReferencePanel, FileReference attachment) {
//    String tag = attachment.getTags();
//    if (tag != null) {
//      fileReferencePanel.setReferenceType(tag);
//    }
//    fileReferencePanel.setPathNameRelativeToPrefix(attachment.getFile());
//    fileReferencePanel.getTitleObjectControl().setValue(attachment.getTitle());
//  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleChanges() {
    updateGeneratedEventFolder();
    updateEventFolderInfo();
    updatePictureImageView();
    
    super.handleChanges();
  }
  
  /**
   * Create a folder for the files related to an event.
   */
  private void createEventsFolder(ActionEvent event) {  // NOPMD
    if (generatedEventFolder.get() != null) {
      generatedEventFolder.get().mkdir();
    }
  }
}
