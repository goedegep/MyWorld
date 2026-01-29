package goedegep.events.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import goedegep.events.logic.EventsRegistry;
import goedegep.events.model.EventInfo;
import goedegep.events.model.EventsFactory;
import goedegep.events.model.EventsPackage;
import goedegep.events.svc.EventsService;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditPanelTemplate;
import goedegep.jfx.editor.EditorException;
import goedegep.jfx.editor.controls.EditorControlFileSelecter;
import goedegep.jfx.editor.controls.EditorControlFlexDate;
import goedegep.jfx.editor.controls.EditorControlHTMLString;
import goedegep.jfx.editor.controls.EditorControlString;
import goedegep.jfx.editor.panels.FileReferenceTypeInfo;
import goedegep.jfx.editor.panels.FileReferencesEditPanel;
import goedegep.jfx.editor.panels.FileReferencesEditPanel.FileReferencesEditPanelBuilder;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesPackage;
import goedegep.util.dir.DirectoryChangesMonitoringTask;
import goedegep.util.emf.EmfUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is the main edit panel for the {@link EventEditor}.
 */
public class EventEditPanel extends EditPanelTemplate<EventInfo> {
  private static final Logger LOGGER = Logger.getLogger(EventEditPanel.class.getName());
  private static final String ATTACHMENTS_PANEL_TITLE = "Attachments";
  private static final String ATTACHMENT_PANEL_TITLE = "Attachment";
  
  /**
   * The Events Service
   */
  EventsService eventsService;
  
  /*
   * Controls
   */

  /**
   * {@code EditorControl} for the date of the event.
   */
  private EditorControlFlexDate eventDateControl;
  
  /**
   * {@code EditorControl} for the event title.
   */
  private EditorControlString eventTitleControl;

  /**
   * {@code EditorControl} for the folder where files related to the event are stored.
   */
  private EditorControlString eventFolderControl;

  /**
   * {@code EditorControl} for the picture representing the event.
   */
  private EditorControlFileSelecter pictureFileSelecter;

  /**
   * {@code EditorControl} for notes about the event.
   */
  private EditorControlHTMLString notesControl;
  
  /**
   * {@code EditPanel} for the event attachments
   */
  private FileReferencesEditPanel attachmentsEditPanel;
  
  /*
   * Other GUI items
   */
  
  /**
   * Main edit panel
   */
  private VBox mainPane;
  
  /**
   * An {@code ImageView} to show the event picture.
   */
  private ImageView pictureImageView;
  
  /**
   * A {@code Button} to create (if it doesn't exist yet) or open the event folder.
   */
  private Button createOrOpenEventFolderButton;
  
  
  /*
   * Anything else
   */
  
  /**
   * The generated or actual event folder (the folder where attachments of the {@code event} are located).
   * <p>
   * If there is an attachment with a file reference to a sub folder of the Events Folder, this is the event folder.
   * Otherwise if there is an event date and an event title, the folder is derived from these values.
   * In both cases the folder itself may exist or not.
   */
  private SimpleObjectProperty<Path> eventFolderPathProperty;

  /**
   * Create an instance of the {@code EventEditPanel}.
   * 
   * @param customization the GUI customization
   * @param eventsService the events service
   * @return the newly created {@code EventEditPanel2}.
   */
  public static EventEditPanel newInstance(CustomizationFx customization, EventsService eventsService) {
    EventEditPanel eventEditPanel = new EventEditPanel(customization, eventsService);
    eventEditPanel.performInitialization();
    
    return eventEditPanel;
  }
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param eventsService the events service
   */
  private EventEditPanel(CustomizationFx customization, EventsService eventsService) {
    super(customization, false);
    
    this.eventsService = eventsService;
    
    setId("EventEditPanel");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls() {
    
    eventDateControl = new EditorControlFlexDate.FlexDateBuilder("eventDate")
        .setWidth(300d)
        .setLabelBaseText("Date")
        .setToolTipText("Date of the event")
        .setErrorTextSupplier(() -> "The event date is not filled in")
        .build();
    eventDateControl.addValueAndOrStatusChangeListener((_, _) -> updateEventFolderPathProperty());
    
    eventTitleControl = new EditorControlString.Builder("eventTitle")
        .setWidth(300d)
        .setLabelBaseText("Title")
        .setToolTipText("Title of the event")
        .setErrorTextSupplier(() -> "The event title is not filled in")
        .build();
    
    eventTitleControl.addValueAndOrStatusChangeListener((_, _) -> updateEventFolderPathProperty());
    
    eventFolderControl = new EditorControlString.Builder("eventFolder")
        .setWidth(300d)
        .setLabelBaseText("Event folder")
        .setToolTipText("Folder where event attachments are stored")
        .setOptional(true)
        .build();
    
    eventFolderControl.getControl().setEditable(false);
   
    pictureFileSelecter = componentFactory.createEditorControlFileSelecter(300, "file name of a picture", "file chooser", "Start a file chooser", "Select picture file", true);
    pictureFileSelecter.setId("pictureFileSelecter");
    pictureFileSelecter.setLabelBaseText("Picture");
    pictureFileSelecter.setInitialFolderProvider(this::getEventRelatedFilesFolder);
    pictureFileSelecter.setPrefix(EventsRegistry.eventsFolderName);
    pictureFileSelecter.addValueAndOrStatusChangeListener((valueChanged, statusChanged) -> updatePictureImageView(valueChanged, statusChanged));
    
    notesControl = componentFactory.createEditorControlHTMLString(true);
    notesControl.setId("notes");
    notesControl.setLabelBaseText("Notes");
    
    attachmentsEditPanel = new FileReferencesEditPanelBuilder(customization)
        .setReferencesEditPanelTitle(ATTACHMENTS_PANEL_TITLE)
        .setReferenceEditPanelTitle(ATTACHMENT_PANEL_TITLE)
        .setAddFileReferenceButtonText("Add attachment")
        .setAddFileReferenceButtonTooltipText("Add an attachment")
        .setInitialFolderSupplier(this::getInitialFolder)
        .addFileReferenceTypes(
            new FileReferenceTypeInfo(AttachmentTypeInfo.FILE.getTag(), AttachmentTypeInfo.FILE.getDisplayName(), false, EventsRegistry.eventsFolderName),
            new FileReferenceTypeInfo(AttachmentTypeInfo.PHOTO_FOLDER.getTag(), AttachmentTypeInfo.PHOTO_FOLDER.getDisplayName(), true, null),
            new FileReferenceTypeInfo(AttachmentTypeInfo.VIDEO_TAKES_FOLDER.getTag(), AttachmentTypeInfo.VIDEO_TAKES_FOLDER.getDisplayName(), true, null)
            )
        .setPrefix(EventsRegistry.eventsFolderName)
        .build();
    attachmentsEditPanel.setId("attachments");
    attachmentsEditPanel.addValueAndOrStatusChangeListener((_, _) -> updateEventFolderPathProperty());
    
    registerEditorComponents(eventDateControl, eventTitleControl, pictureFileSelecter, notesControl, attachmentsEditPanel);
    
    
    pictureImageView = new ImageView();
    
    createOrOpenEventFolderButton = componentFactory.createButton("", "");
    createOrOpenEventFolderButton.setId("create or open events folder button");
    
    eventFolderPathProperty = new SimpleObjectProperty<>();
    eventFolderPathProperty.addListener(_ -> updateEventFolderInfo());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getControl() {
    return mainPane;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void fillControlsFromObject() {
    if (value == null) {
      return;
    }
    eventDateControl.setObject(value.getDate());
    eventTitleControl.setObject(value.getTitle());
    pictureFileSelecter.setPathNameRelativeToPrefix(value.getPicture());
    notesControl.setObject(value.getNotes());
    
    attachmentsEditPanel.setObject(value.getAttachments());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillObjectFromControls(EventInfo object, boolean getCurrentValue) throws EditorException {
    EmfUtil.setFeatureValue(object, TypesPackage.eINSTANCE.getEvent_Date(), eventDateControl.getValue());
    EmfUtil.setFeatureValue(object, EventsPackage.eINSTANCE.getEventInfo_Title(), eventTitleControl.getValue());
    EmfUtil.setFeatureValue(object, EventsPackage.eINSTANCE.getEventInfo_Picture(), pictureFileSelecter.getPathNameRelativeToPrefix());
    EmfUtil.setFeatureValue(object, TypesPackage.eINSTANCE.getEvent_Notes(), notesControl.getValue());
    EmfUtil.setFeatureValue(object, TypesPackage.eINSTANCE.getEvent_Notes(), notesControl.getValue());
    
    if (getCurrentValue) {
      List<FileReference> attachments = attachmentsEditPanel.getCurrentValue();
      object.getAttachments().clear();
      object.getAttachments().addAll(attachments);
    } else {
      attachmentsEditPanel.accept();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel() {
    mainPane = componentFactory.createVBox();

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
    eventFolderControlsBox.getChildren().add(createOrOpenEventFolderButton);

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

    mainPane.getChildren().add(gridPane);

    mainPane.getChildren().add(attachmentsEditPanel.getControl());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setErrorFeedback(boolean valid) {
    // No action at this level.
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected EventInfo createObject() {
    return EventsFactory.eINSTANCE.createEventInfo();
  }

  /**
   * Install listeners for changes in the controls.
   * <p>
   * And in this case listen to changes in the events folder.
   */
  protected void installChangeListeners() {
    if (EventsRegistry.eventsFolderName != null) {
      DirectoryChangesMonitoringTask directoryMonitoringTask = new DirectoryChangesMonitoringTask(EventsRegistry.eventsFolderName);

      directoryMonitoringTask.valueProperty().addListener((_, _, _) -> {
        updateEventFolderInfo();
      });

      Thread directoryMonitoringThread = new Thread(directoryMonitoringTask);
      directoryMonitoringThread.setDaemon(true);
      directoryMonitoringThread.start();
    }
  }
  
  /**
   * Update the {@code eventFolderPathProperty}.
   * @param valueChanged
   * @param statusChanged
   */
  private void updateEventFolderPathProperty() {
    String eventFolderName = null;
    try {
      eventFolderName = eventsService.determineEventFolderName(getCurrentValue());
    } catch (EditorException e) {
      e.printStackTrace();
    }
    eventFolderPathProperty.set(eventFolderName != null ? Paths.get(eventFolderName) : null);
  }
  
  /**
   * Update the {@code pictureImageView}.
   */
  private void updatePictureImageView(boolean valueChanged, boolean statusChanged) {
    LOGGER.info("=>");
    File pictureFile = pictureFileSelecter.getValue();
    
    if (pictureFile != null) {
      Image picture = new Image("file:" + pictureFile.getAbsolutePath());
      pictureImageView.setImage(picture);
    } else {
      pictureImageView.setImage(null);
    }
  }
  
  /**
   * Update information related to the event folder.
   * <p>
   * This method is to be called when this information might have changed.
   * This is the case if:
   * <ul>
   * <li>The folder name is changed</li>
   * <li>There is a changed in the file system below the events folder</li>
   * </ul>
   */
  private void updateEventFolderInfo() {
    Path eventFolder = eventFolderPathProperty.get();
    
    String eventFolderName = null;
    String toolTipText = "Folder where event attachments are stored";
    if (eventFolder != null) {
      eventFolderName = eventFolder.getFileName().toString();
      toolTipText = "Attachments of the event are stored in the folder: " + eventFolder.toString();
    }
    eventFolderControl.setObject(eventFolderName);
    eventFolderControl.getControl().setTooltip(new Tooltip(toolTipText));
    
    if (eventFolder != null  &&  Files.exists(eventFolder)) {
      createOrOpenEventFolderButton.setText("Open");
      createOrOpenEventFolderButton.setOnAction(_ -> {
        try {
          Desktop.getDesktop().open(eventFolder.toFile());
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      });
    } else {
      createOrOpenEventFolderButton.setText("Create");
      createOrOpenEventFolderButton.setOnAction(_ -> {
        createEventsFolder(null);
      });
      createOrOpenEventFolderButton.setDisable(eventFolder == null);
    }
    
  }
    
  /**
   * Get the initial folder to be set as initial folder in a FolderChooser.
   * <p>
   * The initial folder depends on the tag specified in a FileReferenceTypeInfo.
   * 
   * @param fileReferenceTypeInfo
   * @return
   */
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
    if (eventFolderPathProperty.get() != null) {
      return eventFolderPathProperty.get().toString();
    } else {
      return EventsRegistry.eventsFolderName;
    }
  }
  
  /**
   * Create a folder for the files related to an event.
   */
  private void createEventsFolder(ActionEvent event) {
    if (eventFolderPathProperty.get() != null) {
      try {
        Files.createDirectory(eventFolderPathProperty.get());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public String getValueAsFormattedText() {
    return null;
  }
  
}
