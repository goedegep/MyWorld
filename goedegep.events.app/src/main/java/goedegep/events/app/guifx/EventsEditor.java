package goedegep.events.app.guifx;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
import goedegep.jfx.JfxStage;
import goedegep.jfx.controls.FileSelecter;
import goedegep.jfx.controls.ObjectControlBoolean;
import goedegep.jfx.controls.ObjectControlFlexDate;
import goedegep.jfx.controls.ObjectControlGroup;
import goedegep.jfx.controls.ObjectControlHTMLString;
import goedegep.jfx.controls.ObjectControlString;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesFactory;
import goedegep.types.model.TypesPackage;
import goedegep.util.PgUtilities;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class provides a panel to show and edit EventInfo.
 * <p>
 * As long as both <code>events</code> and <code>event</code> are not set (the initial situation),
 * the user can enter values, but an event cannot be created. As it can not be added to the <code>events</code>.
 *
 */
public class EventsEditor extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(EventsEditor.class.getName());
  private static final String WINDOW_TITLE = "Event editor";
  private static final FlexDateFormat FDF =  new FlexDateFormat(true, true);
  
  /**
   * The GUI customization
   */
  private CustomizationFx customization = null;
  
  /**
   * The information on events
   */
  private Events events = null;
  
  /**
   * The current event being shown/edited.
   */
  private EventInfo event = null;
  
  /**
   * The folder where attachments of {@code event} are located.
   */
  private File eventFolder = null;
  
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
  private FileSelecter pictureFileSelecter;
  private ObjectControlHTMLString notesControl;
  private ImageView pictureImageView;
  
  /**
   * List of panels, one for each element.
   */
  private ObservableList<FileReferencePanel> attachmentPanels;
  private VBox attachmentsVBox;
  
  /**
   * ObjectControlGroup used to check the validity of the invoice controls.
   */
  private ObjectControlGroup objectControlGroup;
  
  // Action buttons and box
  private HBox buttonsBox;
  private Button editButton;
  private Button saveAsButton;
  private Button updateButton;
  private Button clearButton;
  
  private Mode mode = Mode.NEW_MODE;
  
  /**
   * Constructor
   * <p>
   * Create all controls and the GUI.
   * 
   * @param customization the GUI customization.
   */
  public EventsEditor(CustomizationFx customization) {
    super(WINDOW_TITLE, customization);
    
    this.customization = customization;
    
    componentFactory = customization.getComponentFactoryFx();
    
    // Create object controls
    eventDateControl = componentFactory.createObjectInputFlexDate(null, 300, false, "Date of the event", null);
    eventTitleControl = componentFactory.createObjectInputString(null, 300, false, "Title of the event");
    eventFolderControl = componentFactory.createObjectInputString(null, 300, false, "Folder where event attachments are stored");
    eventFolderControl.setDisable(true);
    eventFolderExistsControl = componentFactory.createObjectInputBoolean(null, false, true, "Checked indicates that the event folder exists");
    createEventsFolderButton = componentFactory.createButton("create", "Click to create the event folder as shown on the left");
    pictureFileSelecter = componentFactory.createFileSelecter(null, 300, "file name of a picture", "file chooser", "Start a file chooser", "Select picture file");
    if (EventsRegistry.eventsFolderName != null) {
      pictureFileSelecter.setPrefix(EventsRegistry.eventsFolderName);
    }
    notesControl = componentFactory.createObjectControlHTMLString(null, 400, true, "Enter the notes in HTML format", null);
    notesControl.setPrefHeight(100);
    pictureImageView = new ImageView();
    pictureImageView.setFitWidth(400);
    pictureImageView.setFitHeight(400);
    pictureImageView.setPreserveRatio(true);
    
    // Create object controls group
    objectControlGroup = new ObjectControlGroup();
    objectControlGroup.addObjectControls(eventDateControl, eventTitleControl, notesControl);
    
    createGUI();
    
    objectControlGroup.addListener(observable -> handleChanges(observable));
    pictureFileSelecter.addListener((e) -> {
      String filename = pictureFileSelecter.getObjectValue();
      if (filename != null) {
        File file;
        if (EventsRegistry.eventsFolderName != null) {
          file = new File(EventsRegistry.eventsFolderName, filename);
        } else {
          file = new File(filename);
        }
        Image picture = new Image("file:" + file.getAbsolutePath());
        pictureImageView.setImage(picture);
      } else {
        pictureImageView.setImage(null);
      }
    });
    
    handleChanges(null);
    show();
  }
  
  /**
   * Set the <code>Events</code> to which a new event will be added.
   * 
   * @param events the <code>Events</code> to which a new event will be added. 
   */
  public void setEvents(Events events) {
    this.events = events;
    updateButtons();
  }

  /** Set the <code>event</code> of which details will shown and which can be edited.
   * 
   * @param eventInfo the new current event.
   */
  public void setEvent(EventInfo event) {
    this.event = event;
    fillControlsFromEvent(event);
    switchToEditMode();
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {
    VBox rootPane = componentFactory.createVBox();
    
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0, 12.0);
    Label label;
    
    // Date
    label = componentFactory.createLabel("Date:");
    gridPane.add(label, 0, 0);
    
    gridPane.add(eventDateControl, 1, 0);
    
    // Title + Event folder
    label = componentFactory.createLabel("Title:");
    gridPane.add(label, 0, 1);
    
    gridPane.add(eventTitleControl, 1, 1);
    
    HBox eventFolderControlsBox = componentFactory.createHBox(12.0);    
    label = componentFactory.createLabel("Event folder:");
    eventFolderControlsBox.getChildren().add(label);
    
    eventFolderControlsBox.getChildren().add(eventFolderControl);
    eventFolderControlsBox.getChildren().add(eventFolderExistsControl);
    eventFolderControlsBox.getChildren().add(createEventsFolderButton);

    gridPane.add(eventFolderControlsBox, 2, 1);
    
    // Picture
    label = componentFactory.createLabel("Picture:");
    gridPane.add(label, 0, 2);

    gridPane.add(pictureFileSelecter.getPathTextField(), 1, 2);
    gridPane.add(pictureFileSelecter.getFileChooserButton(), 2, 2);
    gridPane.add(pictureImageView, 4, 0, 1, 4);
        
    // Notes
    label = componentFactory.createLabel("Notes:");
    gridPane.add(label, 0, 3);
    
    gridPane.add(notesControl, 1, 3);
    
    rootPane.getChildren().add(gridPane);
    
    rootPane.getChildren().add(createAttachmentsPanel());
    
    // Buttons panel
    rootPane.getChildren().add(createButtonsBox());
    
    setScene(new Scene(rootPane));
  }
  
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
              objectControlGroup.removeObjectControlGroup(documentReferencePanel.getObjectInputContainer());
            }
            for (FileReferencePanel documentReferencePanel: c.getAddedSubList()) {
              objectControlGroup.addObjectControlGroup(documentReferencePanel.getObjectInputContainer());
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
  
  private void updateAttachmentPanel() {
    attachmentsVBox.getChildren().clear();
    attachmentsVBox.getChildren().addAll(attachmentPanels);
  }
  
  private FileReferencePanel createNewAttachmentPanel(boolean expand) {
    FileReferencePanel attachmentPanel = new FileReferencePanel(customization, attachmentPanels, expand, Arrays.asList(AttachmentType.values()));
    if (eventFolder != null) {
      attachmentPanel.getFileSelecter().setInitiallySelectedFolder(eventFolder.getAbsolutePath());
    }
    
    attachmentPanels.add(attachmentPanel);
    
    return attachmentPanel;
  }
  
  /**
   * Create the box with the action buttons.
   * <p>
   * View mode (existing event is shown), no changes possible.
   * Buttons: Edit - switch to edit mode
   * 
   * New event mode (starting with cleared controls), changes possible.
   * Buttons: Save as, Clear
   * 
   * Edit mode (existing event is shown), changes possible
   * Buttons: Update, Save as, Clear
   *  
   * The box has the following buttons:
   * <ul>
   * <li>   </li>
   * </ul>
   * 
   * @return
   */
  private Node createButtonsBox() {
    buttonsBox = componentFactory.createHBox(12.0, 12.0);
    
    // Edit button
    editButton = componentFactory.createButton("Edit", "Edit this event");
    editButton.setOnAction(e -> switchToEditMode());
    
    // Save as new event button
    saveAsButton = componentFactory.createButton("Save as new event", "Create a new event");
    saveAsButton.setOnAction(e -> saveAsNewEvent());
    
    // Update event button
    updateButton = componentFactory.createButton("Update", "Update the current event");
    updateButton.setOnAction(e -> updateEvent());
    
    // Clear button
    clearButton = componentFactory.createButton("Clear", "Clear all controls");
    clearButton.setOnAction(e -> clearControls());
        
    return  buttonsBox;
  }
  
  private void switchToEditMode() {
    eventDateControl.setDisable(false);
    eventTitleControl.setDisable(false);
    pictureFileSelecter.getPathTextField().setDisable(false);
    pictureFileSelecter.getFileChooserButton().setDisable(false);
    
    mode = Mode.EDIT_MODE;
    
    updateButtons();
  }
  
  private void saveAsNewEvent() {
    EventInfo newEvent = EventsFactory.eINSTANCE.createEventInfo();
    fillEventFromControls(newEvent);
    
    List<EventInfo> eventList = events.getEvents();
    FlexDate newEventDate = newEvent.getDate();
    boolean eventInserted = false;
    for (int index = 0; index < eventList.size(); index++) {
      EventInfo event = eventList.get(index);
      if (newEventDate.compareTo(event.getDate()) == 1) {
        eventList.add(index, newEvent);
        eventInserted = true;
        break;
      }
    }
    if (!eventInserted) {
      eventList.add(newEvent);
    }
    
    event = newEvent;
    
    switchToEditMode();
  }
  
  private void updateEvent() {
    fillEventFromControls(event);
  }
  
  private void fillEventFromControls(EventInfo event) {
    EmfUtil.setFeatureValue(event, TypesPackage.eINSTANCE.getEvent_Date(), eventDateControl.getObjectValue());
    EmfUtil.setFeatureValue(event, EventsPackage.eINSTANCE.getEventInfo_Title(), eventTitleControl.getObjectValue());
    EmfUtil.setFeatureValue(event, EventsPackage.eINSTANCE.getEventInfo_Picture(), pictureFileSelecter.getObjectValue());
//    EmfUtil.setFeatureValue(event, TypesPackage.eINSTANCE.getEvent_Notes(), notesControl.getText());
    EmfUtil.setFeatureValue(event, TypesPackage.eINSTANCE.getEvent_Notes(), notesControl.getObjectValue());
    
    fillAttachmentsFromAttachmentPanels(event);
  }
  
  private void fillAttachmentsFromAttachmentPanels(EventInfo event) {
    // Check for any changes. If there are changes, recreate the complete list.
    boolean changes = false;
    
    if (event.getAttachments().size() != attachmentPanels.size()) {
      changes = true;
    }
    
    if (!changes) {
      int index = 0;
      for (FileReference attachment: event.getAttachments()) {
        FileReferencePanel fileReferencePanel = attachmentPanels.get(index++);
        FileReferenceTypeInfo fileReferencePanelReferenceType = fileReferencePanel.getReferenceType();
        String fileReferencePanelReferenceTypeTag = (fileReferencePanelReferenceType != null ? fileReferencePanelReferenceType.getTag() : null);
        String fileReferencePanelFile = FileUtils.getPathRelativeToFolder(EventsRegistry.eventsFolderName, fileReferencePanel.getFile());
        if (!PgUtilities.equals(attachment.getTags(), fileReferencePanelReferenceTypeTag)  ||
            !attachment.getFile().equals(fileReferencePanelFile)  ||
            !PgUtilities.equals(attachment.getTitle(), fileReferencePanel.titleTextField().getObjectValue())) {
          changes = true;
          break;
        }
      }
    }
    
    if (changes) {
      List<FileReference> fileReferences = event.getAttachments();
      fileReferences.clear();
      
      for (FileReferencePanel fileReferencePanel: attachmentPanels) {
        FileReference fileReference = TypesFactory.eINSTANCE.createFileReference();
        updateFileReferenceFromFileReferencePanel(fileReference, fileReferencePanel);
        fileReferences.add(fileReference);
      }
    }
  }

  private void updateFileReferenceFromFileReferencePanel(FileReference fileReference, FileReferencePanel fileReferencePanel) {
    FileReferenceTypeInfo fileReferencePanelReferenceType = fileReferencePanel.getReferenceType();
    if (fileReferencePanelReferenceType != null) {
      fileReference.setTags(fileReferencePanelReferenceType.getTag());
    }
    
    
    if (fileReferencePanel.getFile() != null) {
      String filename = fileReferencePanel.getFile();
      
      if (fileReferencePanelReferenceType == null  ||  fileReferencePanelReferenceType.getTag() != AttachmentType.PHOTO_FOLDER.getTag()) {
        LOGGER.info("filename=" + filename);
        filename = FileUtils.getPathRelativeToFolder(EventsRegistry.eventsFolderName, filename);
        LOGGER.info("filename=" + filename);
      }
      fileReference.setFile(stripBaseDirFromFilename(filename));
    }

    if (fileReferencePanel.titleTextField().getIsFilledIn()) {
      fileReference.setTitle(fileReferencePanel.titleTextField().getObjectValue());
    }    
  }
  
  
  private String stripBaseDirFromFilename(String fileName) {
    File baseDir = new File(EventsRegistry.eventsFolderName);
    if ((fileName.length() > baseDir.getAbsolutePath().length() + 1)  &&
        fileName.startsWith(baseDir.getAbsolutePath())  &&
        (fileName.charAt(baseDir.getAbsolutePath().length()) == '\\')) {
      fileName = fileName.substring(baseDir.getAbsolutePath().length() + 1);
    }
    
    return fileName;
  }

  private void clearControls() {
    eventDateControl.setObjectValue(null);
    eventTitleControl.setObjectValue(null);
    pictureFileSelecter.setObjectValue(null);
  }
  
  private void updateButtons() {
    if (events == null) {
      saveAsButton.setDisable(true);
    } else {
      saveAsButton.setDisable(false);
    }
    
    buttonsBox.getChildren().clear();
    
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    buttonsBox.getChildren().add(spacer);
    
    switch (mode) {
    case EDIT_MODE:
      buttonsBox.getChildren().add(updateButton);
      buttonsBox.getChildren().add(saveAsButton);
      buttonsBox.getChildren().add(clearButton);
      break;
      
    case NEW_MODE:
      buttonsBox.getChildren().add(saveAsButton);
      buttonsBox.getChildren().add(clearButton);
      break;
      
    case VIEW_MODE:
      buttonsBox.getChildren().add(editButton);
      break;
    }
  }
  
  /**
   * Update information related to the event folder.
   */
  private void updateEventFolderInfo() {
    String eventFolderName = createEventFolderName();
    eventFolderControl.setObjectValue(eventFolderName);
    
    if (eventFolderName != null) {
      pictureFileSelecter.setInitiallySelectedFolder(eventFolderName);
    }
    
    if (eventFolderName != null) {
      eventFolder = new File(EventsRegistry.eventsFolderName, eventFolderName);
      eventFolderExistsControl.setSelected(eventFolder.exists());
    } else {
      eventFolderExistsControl.setSelected(false);
    }
    
    createEventsFolderButton.setDisable((eventFolderName == null)  ||  eventFolderExistsControl.isSelected());
  }
  
  private String createEventFolderName() {
    FlexDate eventDate = eventDateControl.getObjectValue();
    if (eventDate == null) {
      return null;
    }
    String eventDateText = FDF.format(eventDate);
    
    String title = eventTitleControl.getObjectValue();
    if (title == null) {
      return null;
    }
    
    return eventDateText + " " + title;
  }
  
  private void fillControlsFromEvent(EventInfo eventInfo) {
    if (eventInfo == null) {
      clearControls();
      return;
    }
    eventDateControl.setObjectValue(eventInfo.getDate());
    eventTitleControl.setObjectValue(eventInfo.getTitle());
    pictureFileSelecter.setObjectValue(eventInfo.getPicture());
    notesControl.setObjectValue(eventInfo.getNotes());
    attachmentPanels.clear();
    for (FileReference attachment: eventInfo.getAttachments()) {
      FileReferencePanel fileReferencePanel = new FileReferencePanel(customization, attachmentPanels, false);
      fillFileReferencePanelFromAttachment(fileReferencePanel, attachment);
      attachmentPanels.add(fileReferencePanel);
    }
  }
  
  private void fillFileReferencePanelFromAttachment(FileReferencePanel fileReferencePanel, FileReference attachment) {
    String  filename = attachment.getFile();

    Path filePath = Paths.get(EventsRegistry.eventsFolderName, filename);    
    fileReferencePanel.setFile(filePath.toAbsolutePath().toString());
    fileReferencePanel.titleTextField().setObjectValue(attachment.getTitle());
  }
  
  
  /**
   * Handle changes in any of the controls.
   * 
   * @param observable
   */
  private void handleChanges(Observable observable) {
    updateEventFolderInfo();
    updateButtons();
  }
}

enum Mode {
  VIEW_MODE,
  NEW_MODE,
  EDIT_MODE;
}
