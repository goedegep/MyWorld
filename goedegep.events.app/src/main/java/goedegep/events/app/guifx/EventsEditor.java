package goedegep.events.app.guifx;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import goedegep.events.app.EventsRegistry;
import goedegep.events.model.EventInfo;
import goedegep.events.model.Events;
import goedegep.events.model.EventsFactory;
import goedegep.events.model.EventsPackage;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.FileReferencePanel;
import goedegep.jfx.FileReferenceTypeInfo;
import goedegep.jfx.objectcontrols.ObjectControlBoolean;
import goedegep.jfx.objectcontrols.ObjectControlFileSelecter;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlHTMLString;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.jfx.objecteditor.ObjectEditorTemplate;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.types.model.TypesPackage;
import goedegep.util.PgUtilities;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.dir.DirectoryChangesMonitoringTask;
import goedegep.util.emf.EmfUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class provides a panel to show and edit EventInfo.
 * <p>
 * As long as both <code>events</code> and <code>event</code> are not set (the initial situation),
 * the user can enter values, but an event cannot be created. As it can not be added to the <code>events</code>.
 *
 */
public class EventsEditor extends ObjectEditorTemplate<EventInfo> {
  private static final Logger LOGGER = Logger.getLogger(EventsEditor.class.getName());
  private static final String WINDOW_TITLE = "Event editor";
  private static final String ATTACHMENT_PANEL_TITLE = "attachment";
  private static final FlexDateFormat FDF =  new FlexDateFormat(true, true);
  
  /**
   * The GUI customization
   */
  private CustomizationFx customization = null;
  
  /**
   * The collection of events to which a new event will be added.
   */
  private Events events = null;
  
  /**
   * Factory used to create all GUI elements
   */
  private ComponentFactoryFx componentFactory;
  
  // Controls
  private ObjectControlFlexDate eventDateControl;
  private ObjectControlString eventTitleControl;
  private ObjectControlString eventFolderControl;
  private ObjectControlBoolean eventFolderExistsControl;
  private Button createEventsFolderButton;
  
  /**
   * {@code ObjectControl} for the event picture.
   */
  private ObjectControlFileSelecter pictureFileSelecter;
  private ObjectControlHTMLString notesControl;
  private ImageView pictureImageView;
  
  /**
   * List of panels, one for each element.
   */
  private ObservableList<FileReferencePanel> attachmentPanels;
  
  /**
   * Panel in which the {@code attachmentPanels} are shown.
   */
  private VBox attachmentsVBox;
  
  /**
   * The generated or actual event folder (the folder where attachments of {@code event} are located).
   * <p>
   * If there is an attachment with a file reference to a sub folder of the Events Folder, this is the event folder.
   * Otherwise if there is an event date and an event title, the folder is derived from these values.
   */
  private SimpleObjectProperty<File> generatedEventFolder;
  
  
  /**
   * Constructor
   * <p>
   * Create all controls and the GUI.
   * 
   * @param customization the GUI customization.
   */
  public EventsEditor(CustomizationFx customization, Events events) {
    super(customization, WINDOW_TITLE);
    
    this.customization = customization;
    this.events = events;
    
    componentFactory = customization.getComponentFactoryFx();
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
    eventTitleControl = componentFactory.createObjectControlString(null, 300, false, "Title of the event");
    eventFolderControl = componentFactory.createObjectControlString(null, 300, false, "Folder where event attachments are stored");
    eventFolderControl.getControl().setDisable(true);
    eventFolderExistsControl = componentFactory.createObjectControlBoolean(null, false, true, "Checked indicates that the event folder exists");
    createEventsFolderButton = componentFactory.createButton("create", "Click to create the event folder as shown on the left");
    createEventsFolderButton.setOnAction(this::createEventsFolder);
    pictureFileSelecter = componentFactory.createFileSelecterObjectControl(300, "file name of a picture", "file chooser", "Start a file chooser", "Select picture file", true);
    pictureFileSelecter.setInitialFolderProvider(this::getEventRelatedFilesFolder);
    pictureFileSelecter.setPrefix(EventsRegistry.eventsFolderName);
    pictureFileSelecter.setId("pictureFileSelecter");
    pictureImageView = new ImageView();
    notesControl = componentFactory.createObjectControlHTMLString(null, 400, true, "Enter the notes in HTML format");
    
    // Add the object controls to the {@code objectControlsGroup}
    objectControlsGroup.addObjectControls(eventDateControl, eventTitleControl, notesControl, pictureFileSelecter);
    
    generatedEventFolder = new SimpleObjectProperty<>();
    generatedEventFolder.addListener((e) -> updateEventFolderInfo());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createAttributeEditDescriptors() {
    // This editor doesn't use attribute edit descriptors, so no action.
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
    eventFolderExistsControl.setValue(false);
  }
    
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel(VBox rootPane) {
    
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    Label label;
    
    // Date
    label = componentFactory.createLabel("Date:");
    gridPane.add(label, 0, 0);
    gridPane.add(eventDateControl.getControl(), 1, 0);
    gridPane.add(eventDateControl.getStatusIndicator(), 2, 0);
    
    // Title + Event folder
    label = componentFactory.createLabel("Title:");
    gridPane.add(label, 0, 1);
    gridPane.add(eventTitleControl.getControl(), 1, 1);
    gridPane.add(eventTitleControl.getStatusIndicator(), 2, 1);
    
    HBox eventFolderControlsBox = componentFactory.createHBox(12.0);    
    label = componentFactory.createLabel("Event folder:");
    eventFolderControlsBox.getChildren().add(label);
    
    eventFolderControlsBox.getChildren().add(eventFolderControl.getControl());
    eventFolderControlsBox.getChildren().add(eventFolderExistsControl.getControl());
    eventFolderControlsBox.getChildren().add(createEventsFolderButton);

    gridPane.add(eventFolderControlsBox, 3, 1);
    
    // Picture
    label = componentFactory.createLabel("Picture:");
    gridPane.add(label, 0, 2);

    gridPane.add(pictureFileSelecter.getControl(), 1, 2);
    HBox pictureControlsBox = componentFactory.createHBox(12.0);
    pictureControlsBox.getChildren().addAll(pictureFileSelecter.getStatusIndicator(), pictureFileSelecter.getFileChooserButton());
    gridPane.add(pictureControlsBox, 2, 2);
        
    // Notes + Picture image view
    label = componentFactory.createLabel("Notes:");
    gridPane.add(label, 0, 3);
    gridPane.add(notesControl.getControl(), 1, 3);
    gridPane.add(notesControl.getStatusIndicator(), 2, 3);
    
    pictureImageView.setFitWidth(400);
    pictureImageView.setFitHeight(400);
    pictureImageView.setPreserveRatio(true);
    gridPane.add(pictureImageView, 3, 3, 3, 2);
    
    rootPane.getChildren().add(gridPane);
    
    rootPane.getChildren().add(createAttachmentsPanel());
  }
  
  /**
   * Create the attachments panel.
   * 
   * @return the created attachments panel.
   */
  private Node createAttachmentsPanel() {
    VBox documentsMainVBox = componentFactory.createVBox(12.0, 12.0);
    documentsMainVBox.setMinSize(300, 300);
    documentsMainVBox.setBorder(componentFactory.getRectangularBorder());
    
    Label label = componentFactory.createStrongLabel("Attachments");
    documentsMainVBox.getChildren().add(label);
    
    attachmentsVBox = componentFactory.createVBox();
    attachmentPanels = FXCollections.observableArrayList();
    attachmentPanels.addListener(new ListChangeListener<FileReferencePanel>() {

      @Override
      public void onChanged(Change<? extends FileReferencePanel> c) {
        while (c.next()) {
          if (c.wasPermutated()) {
            // No action needed here
          } else if (c.wasUpdated()) {
            // update item
          } else {
            for (FileReferencePanel documentReferencePanel: c.getRemoved()) {
              objectControlsGroup.removeObjectControlGroup(documentReferencePanel.getObjectControlsGroup());
              handleChanges();
            }
            for (FileReferencePanel documentReferencePanel: c.getAddedSubList()) {
              objectControlsGroup.addObjectControlGroup(documentReferencePanel.getObjectControlsGroup());
              handleChanges();
            }
          }
        }
        
        updateAttachmentPanel();
        
      }
      
    });

    documentsMainVBox.getChildren().add(attachmentsVBox);
    
    Button newAttachmentButton = componentFactory.createButton("+ Add attachment", "Add an attachment");
    newAttachmentButton.setOnAction(e -> createNewAttachmentPanel(true));
    documentsMainVBox.getChildren().add(newAttachmentButton);
    
    return documentsMainVBox;
  }
  
  /**
   * Update the attachments panel.
   */
  private void updateAttachmentPanel() {
    attachmentsVBox.getChildren().clear();
    attachmentsVBox.getChildren().addAll(attachmentPanels);
  }
  
  /**
   * Create a new attachment panel.
   * 
   * @param expand if true, the panel will be expanded upon creation.
   * @return the created attachment panel.
   */
  private FileReferencePanel createNewAttachmentPanel(boolean expand) {
    FileReferencePanel attachmentPanel = new FileReferencePanel.FileReferencePanelBuilder(customization, attachmentPanels)
        .setDefaultPaneTitle(ATTACHMENT_PANEL_TITLE)
        .setExpandPaneOnCreation(expand)
        .setInitialFolderSupplier(this::getEventRelatedFilesFolder)
        .addFileReferenceTypes(
            new FileReferenceTypeInfo(AttachmentTypeInfo.FILE.getTag(), AttachmentTypeInfo.FILE.getDisplayName(), false, EventsRegistry.eventsFolderName),
            new FileReferenceTypeInfo(AttachmentTypeInfo.PHOTO_FOLDER.getTag(), AttachmentTypeInfo.PHOTO_FOLDER.getDisplayName(), true, null),
            new FileReferenceTypeInfo(AttachmentTypeInfo.VIDEO_TAKES_FOLDER.getTag(), AttachmentTypeInfo.VIDEO_TAKES_FOLDER.getDisplayName(), true, null)
            )
        .build();

    attachmentPanels.add(attachmentPanel);

    return attachmentPanel;
  }
  
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
  
//  
//  private void saveAsNewEvent() {
//    EventInfo newEvent = EventsFactory.eINSTANCE.createEventInfo();
//    fillEventFromControls(newEvent);
//    
//    List<EventInfo> eventList = events.getEvents();
//    FlexDate newEventDate = newEvent.getDate();
//    boolean eventInserted = false;
//    for (int index = 0; index < eventList.size(); index++) {
//      EventInfo event = eventList.get(index);
//      if (newEventDate.compareTo(event.getDate()) == 1) {
//        eventList.add(index, newEvent);
//        eventInserted = true;
//        break;
//      }
//    }
//    if (!eventInserted) {
//      eventList.add(newEvent);
//    }    
//  }
  
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
    
    fillAttachmentsFromAttachmentPanels();
  }
  
  /**
   * Fill the event attachments from the attachment panels.
   * <p>
   * If there any changes in the attachments, the complete list of attachments is recreated.
   */
  private void fillAttachmentsFromAttachmentPanels() {
    // Check for any changes. If there are changes, recreate the complete list.
    boolean changes = false;
    
    if (object.getAttachments().size() != attachmentPanels.size()) {
      changes = true;
    }
    
    if (!changes) {
      int index = 0;
      for (FileReference attachment: object.getAttachments()) {
        FileReferencePanel fileReferencePanel = attachmentPanels.get(index++);
        FileReferenceTypeInfo fileReferencePanelReferenceType = fileReferencePanel.getReferenceType();
        String fileReferencePanelReferenceTypeTag = (fileReferencePanelReferenceType != null ? fileReferencePanelReferenceType.tag() : null);
        String fileReferencePanelFile = fileReferencePanel.getPathNameRelativeToPrefix();
        if (!PgUtilities.equals(attachment.getTags(), fileReferencePanelReferenceTypeTag)  ||
            !attachment.getFile().equals(fileReferencePanelFile)  ||
            !PgUtilities.equals(attachment.getTitle(), fileReferencePanel.getTitleObjectControl().getValue())) {
          changes = true;
          break;
        }
      }
    }
    
    if (changes) {
      List<FileReference> fileReferences = object.getAttachments();
      fileReferences.clear();
      
      for (FileReferencePanel fileReferencePanel: attachmentPanels) {
        FileReference fileReference = TypesFactory.eINSTANCE.createFileReference();
        updateFileReferenceFromFileReferencePanel(fileReference, fileReferencePanel);
        fileReferences.add(fileReference);
      }
    }
  }

  /**
   * Update a {@code FileReference} from a {@code FileReferencePanel}.
   * 
   * @param fileReference the {@code FileReference} to update
   * @param fileReferencePanel the {@code FileReferencePanel} from which the {@code fileReference} will be updated.
   */
  private void updateFileReferenceFromFileReferencePanel(FileReference fileReference, FileReferencePanel fileReferencePanel) {
    FileReferenceTypeInfo fileReferencePanelReferenceType = fileReferencePanel.getReferenceType();
    if (fileReferencePanelReferenceType != null) {
      fileReference.setTags(fileReferencePanelReferenceType.tag());
      LOGGER.severe("tags set to: " + fileReferencePanelReferenceType.tag());
    }
    
    if (fileReferencePanel.getPathNameRelativeToPrefix() != null) {
      String filename = fileReferencePanel.getPathNameRelativeToPrefix();
      
      fileReference.setFile(filename);
      LOGGER.severe("file set to: " + filename);
    }

    if (fileReferencePanel.getTitleObjectControl().isFilledIn()) {
      fileReference.setTitle(fileReferencePanel.getTitleObjectControl().getValue());
      LOGGER.severe("title set to: " + fileReferencePanel.getTitleObjectControl().getValue());
    }    
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected Double getWindowHeight() {
    return 1320.0;
  }  
  
//  private String stripBaseDirFromFilename(String fileName) {
//    File baseDir = new File(EventsRegistry.eventsFolderName);
//    if ((fileName.length() > baseDir.getAbsolutePath().length() + 1)  &&
//        fileName.startsWith(baseDir.getAbsolutePath())  &&
//        (fileName.charAt(baseDir.getAbsolutePath().length()) == '\\')) {
//      fileName = fileName.substring(baseDir.getAbsolutePath().length() + 1);
//    }
//    
//    return fileName;
//  }
  
//  private void updateButtons() {
//    if (events == null) {
//      saveAsButton.setDisable(true);
//    } else {
//      saveAsButton.setDisable(false);
//    }
//    
//    buttonsBox.getChildren().clear();
//    
//    final Pane spacer = new Pane();
//    HBox.setHgrow(spacer, Priority.ALWAYS);
//    buttonsBox.getChildren().add(spacer);
//    
//    switch (mode) {
//    case EDIT_MODE:
//      buttonsBox.getChildren().add(updateButton);
//      buttonsBox.getChildren().add(saveAsButton);
//      buttonsBox.getChildren().add(clearButton);
//      break;
//      
//    case NEW_MODE:
//      buttonsBox.getChildren().add(saveAsButton);
//      buttonsBox.getChildren().add(clearButton);
//      break;
//      
//    case VIEW_MODE:
//      buttonsBox.getChildren().add(editButton);
//      break;
//    }
//  }
  
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
    
    for (FileReferencePanel fileReferencePanel: attachmentPanels) {
      File file = fileReferencePanel.getFile();
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
    attachmentPanels.clear();
    for (FileReference attachment: object.getAttachments()) {
      FileReferencePanel fileReferencePanel = createNewAttachmentPanel(false);
      fillFileReferencePanelFromAttachment(fileReferencePanel, attachment);
    }
  }
  
  /**
   * Fill a {@code FileReferencePanel} from a {@code FileReference}.
   * 
   * @param fileReferencePanel the {@code FileReferencePanel} to be filled.
   * @param attachment the attachment (a {@code FileReference} to fill the {@code fileReferencePanel} from.
   */
  private void fillFileReferencePanelFromAttachment(FileReferencePanel fileReferencePanel, FileReference attachment) {
    String tag = attachment.getTags();
    if (tag != null) {
      fileReferencePanel.setReferenceType(tag);
    }
    fileReferencePanel.setPathNameRelativeToPrefix(attachment.getFile());
    fileReferencePanel.getTitleObjectControl().setValue(attachment.getTitle());
  }
  
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
  private void createEventsFolder(ActionEvent event) {
    if (generatedEventFolder.get() != null) {
      generatedEventFolder.get().mkdir();
    }
  }
}
