package goedegep.events.app.guifx;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import goedegep.appgen.Operation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.events.app.EventsLauncher;
import goedegep.events.app.EventsRegistry;
import goedegep.events.model.EventInfo;
import goedegep.events.model.EventsPackage;
import goedegep.gpx.app.GPXWindow;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.jfx.img.ImageStage;
import goedegep.resources.ImageResource;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesPackage;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.file.FileUtils;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;

/**
 * This class is a table for events ({@link EventInfo}).
 */
public class EventsTable extends EObjectTable<EventInfo> {
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   */
  public EventsTable(CustomizationFx customization) {
    super(customization, EventsPackage.eINSTANCE.getEventInfo(), EventsTableDescriptorFactory.createDescriptor(customization));
    
    handleRowDoubleClicked(getSelectedObject());
  }
  
  @Override
  protected void handleRowDoubleClicked(EventInfo event) {
    if (event != null) {
      EventsLauncher.getInstance().LaunchEventsEditor(event);
    }
  }
}


/**
 * This class is a factory to create the descriptor for the events table.
 */
class EventsTableDescriptorFactory {
  private static final EventsPackage EVENTS_PACKAGE = EventsPackage.eINSTANCE;
  private static final TypesPackage TYPES_PACKAGE = TypesPackage.eINSTANCE;
  
  /**
   * Create the descriptor for the events table.
   * 
   * @param customization the GUI customization.
   * @return the descriptor for the events table.
   */
  public static EObjectTableDescriptor<EventInfo> createDescriptor(CustomizationFx customization) {
    EObjectTableDescriptor<EventInfo> descriptor = new EObjectTableDescriptor<>();
    
    /*
     * Columns
     */
    
    // Date
    descriptor.getColumnDescriptors().add(
        EObjectTableColumnDescriptorBasic.<EventInfo>createForFeature(TYPES_PACKAGE.getEvent_Date()).
        setColumnName("Date")
        );
    
    // Title
    descriptor.getColumnDescriptors().add(
        EObjectTableColumnDescriptorBasic.<EventInfo>createForFeature(EVENTS_PACKAGE.getEventInfo_Title()).
        setColumnName("Title")
        );
    
    // Picture
    ImageCellFactory imageCellFactory = new ImageCellFactory(120, EventsRegistry.eventsFolderName);
    EObjectTableColumnDescriptorCustom<EventInfo> columnDescriptor = EObjectTableColumnDescriptorCustom.<EventInfo>createForFeature(EVENTS_PACKAGE.getEventInfo_Picture()).
        setColumnName("Picture").
        setCellFactory(imageCellFactory);
    columnDescriptor.setMinimumWidth(250);
    descriptor.getColumnDescriptors().add(columnDescriptor);
    
    // Attachments
    AttachmentsCellFactory attachmentsCellFactory = new AttachmentsCellFactory(customization, 120, EventsRegistry.eventsFolderName);
    descriptor.getColumnDescriptors().add(
        EObjectTableColumnDescriptorCustom.<EventInfo>createForFeature(null).
        setColumnName("Attachments").
        setCellFactory(attachmentsCellFactory)
        );
    
    // Notes
    NotesCellFactory notesCellFactory = new NotesCellFactory(120);
    descriptor.getColumnDescriptors().add(
        EObjectTableColumnDescriptorCustom.<EventInfo>createForFeature(TYPES_PACKAGE.getEvent_Notes()).
        setColumnName("Notes").
        setCellFactory(notesCellFactory)
        );
    
    /*
     * Row operations
     */
    descriptor.getRowOperations().put(Operation.DELETE_OBJECT, new TableRowOperationDescriptor<EventInfo>("Remove this Event"));
    
    return descriptor;
  }
}


/**
 * A factory to create an 'Image' cell.
 */
class ImageCellFactory implements Callback<TableColumn<EventInfo, Object>, TableCell<EventInfo, Object>> {
  /**
   * Height of an image.
   */
  private int imageHeight;
  private String eventsDirectory;
  
  /**
   * Constructor.
   * 
   * @param imageHeight height of an image.
   * @param eventsDirectory name of the folder containing the event folders.
   */
  public ImageCellFactory(int imageHeight, String eventsDirectory) {
    this.imageHeight = imageHeight;
    this.eventsDirectory = eventsDirectory;
  }

  @Override
  public TableCell<EventInfo, Object> call(TableColumn<EventInfo, Object> arg0) {
    return new ImageCell(imageHeight, eventsDirectory);
  }
  
}


/**
 * A table cell for an Image.
 */
class ImageCell extends TableCell<EventInfo, Object> {
  private static final Logger LOGGER = Logger.getLogger(ImageCell.class.getName());
  
  private int imageHeight;
  private String eventsDirectory;

  public ImageCell(int imageHeight, String eventsDirectory) {
    LOGGER.info("<=>");
    this.imageHeight = imageHeight;
    this.eventsDirectory = eventsDirectory;
  }
  
  @Override
  protected void updateItem(Object item, boolean empty) {
    LOGGER.info("=> item=" + (item != null ? item.toString() : "(null)") + ", empty=" + empty);
    super.updateItem(item, empty);
    if (empty  ||  (item == null)) {
      setText(null);
      setGraphic(null);
      setTooltip(null);
    } else {
      if (!(item instanceof String)) {
        throw new IllegalArgumentException("item shall be a String, but is of type '" + item.getClass().getName() + "'");
      }
      String  imageFileName = (String) item;
      
      Path imagePath = Paths.get(eventsDirectory, imageFileName);
      Image image;
      image = new Image("file:" + imagePath.toAbsolutePath().toString(), -1, imageHeight,  true, true);

      ImageView imageView = new ImageView(image);
            
      setGraphic(imageView);
      setText(null);
    }
    LOGGER.info("<=");
  }
}


/**
 * A factory to create an attachments cell.
 */
class AttachmentsCellFactory implements Callback<TableColumn<EventInfo, Object>, TableCell<EventInfo, Object>> {
  private CustomizationFx customization;
  private int imageHeight;
  private String eventsDirectory;
  
  public AttachmentsCellFactory(CustomizationFx customization, int imageHeight, String eventsDirectory) {
    this.customization = customization;
    this.imageHeight = imageHeight;
    this.eventsDirectory = eventsDirectory;
  }

  @Override
  public TableCell<EventInfo, Object> call(TableColumn<EventInfo, Object> arg0) {
    return new AttachmentsCell(customization, imageHeight, eventsDirectory);
  }
  
}


/**
 * A table cell for the attachments.
 */
class AttachmentsCell extends TableCell<EventInfo, Object> {
  private static final Logger LOGGER = Logger.getLogger(AttachmentsCell.class.getName());
  private static final FlexDateFormat FDF =  new FlexDateFormat(true, true);
  
  private CustomizationFx customization = null;
  private ComponentFactoryFx componentFactory = null;
  
  private int imageHeight;
  private String eventsDirectory;

  public AttachmentsCell(CustomizationFx customization, int imageHeight, String eventsDirectory) {
    LOGGER.info("<=> eventsDirectory=" + eventsDirectory);
    this.customization = customization;
    this.imageHeight = imageHeight;
    this.eventsDirectory = eventsDirectory;
    componentFactory = customization.getComponentFactoryFx();
  }
  
  @Override
  protected void updateItem(Object item, boolean empty) {
    LOGGER.info("=> item=" + (item != null ? item.toString() : "(null)") + ", empty=" + empty);
    super.updateItem(item, empty);
    if (empty  ||  (item == null)) {
      setText(null);
      setGraphic(null);
      setTooltip(null);
    } else {
      if (!(item instanceof EventInfo)) {
        throw new IllegalArgumentException("item shall be a EventInfo, but is of type '" + item.getClass().getName() + "'");
      }
//      if (!(item instanceof List)) {
//        throw new IllegalArgumentException("item shall be a List, but is of type '" + item.getClass().getName() + "'");
//      }
      EventInfo eventInfo = (EventInfo) item;
      List<FileReference> fileReferences = eventInfo.getAttachments();
//      @SuppressWarnings("unchecked")
//      List<FileReference> fileReferences = (List<FileReference>) item;
      
      HBox hBox = componentFactory.createHBox(0, 0);
      for (FileReference fileReference: fileReferences) {
        VBox vBox = componentFactory.createVBox(12.0, 4.0);
        vBox.setAlignment(Pos.CENTER);
        
        String  attachmentFileName = fileReference.getFile();
        LOGGER.info("imageFileName: " + attachmentFileName);
        
        if (fileReference.getTags() != null  &&  fileReference.getTags().equals(AttachmentTypeInfo.PHOTO_FOLDER.getTag())) {
          hBox.getChildren().add(createOpenByDesktopLabeledImage(null, fileReference, ImageResource.PHOTO_FOLDER.getImage()));
        } else if (fileReference.getTags() != null  &&  fileReference.getTags().equals(AttachmentTypeInfo.VIDEO_TAKES_FOLDER.getTag())) {
          hBox.getChildren().add(createOpenByDesktopLabeledImage(null, fileReference, ImageResource.VIDEO_FOLDER.getImage()));
        } else if (FileUtils.isPictureFile(attachmentFileName)) {

          Path imagePath = Paths.get(eventsDirectory, attachmentFileName);
          Image image = new Image("file:" + imagePath.toAbsolutePath().toString(), imageHeight, imageHeight,  true, true);
          vBox.getChildren().add(createImageView(image));
          vBox.getChildren().add(createLabel(fileReference));

          vBox.setOnMouseClicked((e) -> new ImageStage(customization, imagePath.toString()));

          hBox.getChildren().add(vBox);
        } else if (FileUtils.isPDFFile(attachmentFileName)) {
          hBox.getChildren().add(createOpenByDesktopLabeledImage(eventsDirectory, fileReference, ImageResource.PDF.getImage()));
        } else if (FileUtils.isTextFile(attachmentFileName)) {
          hBox.getChildren().add(createOpenByDesktopLabeledImage(eventsDirectory, fileReference, ImageResource.TEXT_FILE.getImage()));
        } else if (FileUtils.isMSWordFile(attachmentFileName)) {
          hBox.getChildren().add(createOpenByDesktopLabeledImage(eventsDirectory, fileReference, ImageResource.MS_WORD.getImage()));
        } else if (FileUtils.isODTFile(attachmentFileName)) {
          hBox.getChildren().add(createOpenByDesktopLabeledImage(eventsDirectory, fileReference, ImageResource.ODT.getImage()));
        } else if (FileUtils.isGpxFile(attachmentFileName)) {
          vBox.getChildren().add(createImageView(ImageResource.GPX.getImage()));
          vBox.getChildren().add(createLabel(fileReference));

          vBox.setOnMouseClicked((e) -> {
            File file = new File(eventsDirectory, fileReference.getFile());
            new GPXWindow(customization, file.getAbsolutePath());
          });

          hBox.getChildren().add(vBox);
        } else {
          Label label = createLabel(fileReference);
          label.setOnMouseClicked((e) -> {
            try {
              Desktop.getDesktop().open(new File(eventsDirectory, fileReference.getFile()));
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          });
          hBox.getChildren().add(label);
        }
      }
      
      String eventFolderName = determineEventFolder(eventInfo);
      if (eventFolderName != null) {
        Path eventFolderPath = Paths.get(eventFolderName);
        if (Files.exists(eventFolderPath)) {
          VBox vBox = componentFactory.createVBox(12.0, 4.0);
          vBox.setAlignment(Pos.CENTER);
          Image image = ImageResource.FOLDER_WITH_FILES.getImage();
          ImageView imageView = new ImageView(image);
          vBox.getChildren().add(imageView);
          boolean eventFolderHasNonReferencedFiles = eventFolderHasNonReferencedFiles(eventInfo, eventFolderName);
          Label label;
          if (eventFolderHasNonReferencedFiles) {
            label = componentFactory.createStrongLabel("Event folder");
          } else {
            label = componentFactory.createLabel("Event folder");
          }
          vBox.getChildren().add(label);
          vBox.setOnMouseClicked((e) -> {
            try {
              Desktop.getDesktop().open(new File(eventFolderName));
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          });
          hBox.getChildren().add(vBox);
        }
      }
            
      setGraphic(hBox);
      setText(null);
    }
    LOGGER.info("<=");
  }
  
  /**
   * Create a {@code Node} with a labeled image which can be opened with a mouse click.
   * 
   * @param prefix a path prefix which is needed if the {@code fileReference} is not an absolute path, otherwise it shall be {@code null}.
   * @param fileReference a {@code FileReference} providing the file name.
   * @param image the {@code Image}
   * @return a {@code Node} with a labeled image which can be opened with a mouse click.
   */
  private Node createOpenByDesktopLabeledImage(String prefix, FileReference fileReference, Image image) {
    VBox vBox = componentFactory.createVBox(12.0, 4.0);
    vBox.getChildren().add(createImageView(image));
    vBox.getChildren().add(createLabel(fileReference));

    vBox.setOnMouseClicked((e) -> {
      try {
        File file = prefix != null ? new File(prefix, fileReference.getFile()) : new File(fileReference.getFile());
        Desktop.getDesktop().open(file);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    });
    
    return vBox;
  }

  /**
   * Create an {@code ImageView} to be shown in the table cell.
   * <p>
   * The height is set to {@code imageHeight} and preserve ratio is set.
   * 
   * @param image The {@code Image} to be shown.
   * @return an {@code ImageView} for {@code}.
   */
  private ImageView createImageView(Image image) {
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(imageHeight);
    imageView.setPreserveRatio(true);
    
    return imageView;
  }
  
  /**
   * Creat a {@code Label} to be shown below an attachment image.
   * 
   * @param fileReference the {@code FileReference} for which a {@code Label} is to be created.
   * @return a {@code Label} for {@code fileReference}.
   */
  private Label createLabel(FileReference fileReference) {
    String labelText = fileReference.getTitle();
    if (labelText == null  &&  fileReference.getFile() != null) {
      File file = new File(fileReference.getFile());
      labelText = file.getName();
    }
    Label label = componentFactory.createLabel(labelText);
    return label;
  }
  
  /**
   * Check whether there are files in an event folder which aren't referred to by the event.
   * <p>
   * A file can be referred to by an attachment or by the event picture.
   * 
   * @param eventInfo the event
   * @param eventFolder the event folder
   * @return true if there is at least one file in the {@code eventFolder} which isn't referred to by the {@code eventInfo}, false otherwise.
   */
  private boolean eventFolderHasNonReferencedFiles(EventInfo eventInfo, String eventFolder) {
    if (eventFolder == null) {
      return false;
    }
    
    Path eventFolderPath = Paths.get(eventFolder);
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(eventFolderPath)) {
      for (Path path: stream) {
        if (!Files.isDirectory(path)) {
          if (!pathIsReferredTo(eventInfo, path)) {
            return true;
          } else {
            LOGGER.info("Skipping file which is referred to: " + path.getFileName().toString());
          }
        } else {
          LOGGER.severe("Skipping folder: " + path.toString());
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    return false;
  }

  /**
   * Check whether a file is referred to or not.
   * 
   * @param path
   * @return
   */
  private boolean pathIsReferredTo(EventInfo eventInfo, Path path) {
    String pathAsString = path.toString();
    for (FileReference fileReference: eventInfo.getAttachments()) {
      String fileName = fileReference.getFile();
      if (fileName != null) {
        if (!(new File(fileName).isAbsolute())) {
          fileName = FileUtils.createPathNameFromPrefixAndFileName(EventsRegistry.eventsFolderName, fileName);
          if (fileName.equals(pathAsString)) {
            return true;
          }
        }
      }
    }
    
    String pictureFileName = eventInfo.getPicture();
    if (pictureFileName != null) {
      if (!(new File(pictureFileName).isAbsolute())) {      
        pictureFileName = FileUtils.createPathNameFromPrefixAndFileName(EventsRegistry.eventsFolderName, pictureFileName);
        if (pictureFileName.equals(pathAsString)) {
          return true;
        }
      }
    }
    
    return false;
  }

  /**
   * Update the {@code generatedEventFolder}
   * <p>
   * If there is an attachment with a file reference to a sub folder of the Events Folder, this is the event folder.
   * Otherwise if there is an event date and an event title, the folder is derived from these values.
   */
  private String determineEventFolder(EventInfo eventInfo) {
    String eventFolderName = deriveEventFolderNameFromAttachments(eventInfo);
    
    if (eventFolderName == null) {
      eventFolderName = deriveEventFolderNameFromDateAndTitle(eventInfo);
    }
    
    return eventFolderName;
  }
  
  /**
   * Get the event folder name derived from the attachments.
   * <p>
   * The event folder is the folder of the first attachment, which is a sub folder of the Events Folder.
   * 
   * @return the event folder name derived from the attachments, or null if this cannot be determined.
   */
  private String deriveEventFolderNameFromAttachments(EventInfo eventInfo) {
    if (EventsRegistry.eventsFolderName == null) {
      return null;
    }
    
    for (FileReference fileReference: eventInfo.getAttachments()) {
      String fileName = fileReference.getFile();
      if (fileName == null) {
        continue;
      }
      
      // If the fileName is an absolute path, the attachment (by definition) is not in the event folder (but e.g. a picture under 'photos'.
      File file = new File(fileName);
      
      if (file.isAbsolute()) {
        continue;
      }
      
      String eventsFolderName = file.getParent();
      if (eventsFolderName != null) {
        return eventsFolderName;
      }
    }
    
    return null;
  }
  
  
  /**
   * Derive the event folder from the date and title controls.
   * 
   * @return the event folder derived from the date and title controls, or null if these controls don't have the needed information.
   */
  private String deriveEventFolderNameFromDateAndTitle(EventInfo eventInfo) {
    FlexDate eventDate = eventInfo.getDate();
    if (eventDate == null) {
      return null;
    }
    String eventDateText = FDF.format(eventDate);
    
    String title = eventInfo.getTitle();
    if (title == null) {
      return null;
    }
    
    String subFolderName = eventDateText + " " + title;
    File eventFolder = new File(EventsRegistry.eventsFolderName, subFolderName);
    
    return eventFolder.getAbsolutePath();
  }
}


/**
 * A factory to create an 'Notes' cell.
 */
class NotesCellFactory implements Callback<TableColumn<EventInfo, Object>, TableCell<EventInfo, Object>> {
  private int imageHeight;
  
  public NotesCellFactory(int imageHeight) {
    this.imageHeight = imageHeight;
  }

  @Override
  public TableCell<EventInfo, Object> call(TableColumn<EventInfo, Object> arg0) {
    return new NotesCell(imageHeight);
  }
  
}


/**
 * A table cell for notes.
 */
class NotesCell extends TableCell<EventInfo, Object> {
  private static final Logger LOGGER = Logger.getLogger(NotesCell.class.getName());
  static Parser parser = Parser.builder().build();
  static HtmlRenderer renderer = HtmlRenderer.builder().build();
  
  private int imageHeight;

  public NotesCell(int imageHeight) {
    LOGGER.info("<=>");
    this.imageHeight = imageHeight;
  }
  
  @Override
  protected void updateItem(Object item, boolean empty) {
    LOGGER.info("=> item=" + (item != null ? item.toString() : "(null)") + ", empty=" + empty);
    super.updateItem(item, empty);
    if (empty  ||  (item == null)) {
      setText(null);
      setGraphic(null);
      setTooltip(null);
    } else {
      if (!(item instanceof String)) {
        throw new IllegalArgumentException("item shall be a String, but is of type '" + item.getClass().getName() + "'");
      }
      String text = (String) item;
      WebView webView = new WebView();
      webView.setPrefHeight(imageHeight);
      webView.setPrefWidth(300);
      WebEngine webEngine = webView.getEngine();
      webEngine.loadContent(text);
            
      setGraphic(webView);
      setText(null);
    }
    LOGGER.info("<=");
  }
}




