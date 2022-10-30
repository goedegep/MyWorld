package goedegep.events.app.guifx;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import goedegep.appgen.TableRowOperation;
import goedegep.appgen.TableRowOperationDescriptor;
import goedegep.events.app.EventsRegistry;
import goedegep.events.model.EventInfo;
import goedegep.events.model.Events;
import goedegep.events.model.EventsPackage;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.eobjecttable.EObjectTable;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorBasic;
import goedegep.jfx.eobjecttable.EObjectTableColumnDescriptorCustom;
import goedegep.jfx.eobjecttable.EObjectTableDescriptor;
import goedegep.types.model.FileReference;
import goedegep.types.model.TypesPackage;
import javafx.geometry.Pos;
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

public class EventsTable extends EObjectTable<EventInfo> {
  private CustomizationFx customization;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   */
  public EventsTable(CustomizationFx customization, Events events) {
    super(customization, EventsPackage.eINSTANCE.getEventInfo(), EventsTableDescriptorFactory.createDescriptor(customization), events, null);
    
    this.customization = customization;
//    setMinHeight(300);
    
    // refresh table on selection, used to show the selected row with larger images.
    addObjectSelectionListener((o, e) -> refresh());
    
    handleRowDoubleClicked(getSelectedObject());
  }
  
  @Override
  protected void handleRowDoubleClicked(EventInfo event) {
    if (event != null) {
      EventsEditor eventsEditor = new EventsEditor(customization);
      eventsEditor.setEvent(event);
    }
  }
}

class EventsTableDescriptorFactory {
  private static final EventsPackage EVENTS_PACKAGE = EventsPackage.eINSTANCE;
  private static final TypesPackage TYPES_PACKAGE = TypesPackage.eINSTANCE;
  
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
    ImageCellFactory imageCellFactory = new ImageCellFactory(customization, 120, EventsRegistry.eventsFolderName);
    descriptor.getColumnDescriptors().add(
        EObjectTableColumnDescriptorCustom.<EventInfo>createForFeature(EVENTS_PACKAGE.getEventInfo_Picture()).
        setColumnName("Picture").
        setCellFactory(imageCellFactory)
        );
    
    // Attachments
    AttachmentsCellFactory attachmentsCellFactory = new AttachmentsCellFactory(customization, 120, EventsRegistry.eventsFolderName);
    descriptor.getColumnDescriptors().add(
        EObjectTableColumnDescriptorCustom.<EventInfo>createForFeature(EVENTS_PACKAGE.getEventInfo_Attachments()).
        setColumnName("Attachments").
        setCellFactory(attachmentsCellFactory)
        );
    
    // Notes
    NotesCellFactory notesCellFactory = new NotesCellFactory(customization, 120);
    descriptor.getColumnDescriptors().add(
        EObjectTableColumnDescriptorCustom.<EventInfo>createForFeature(TYPES_PACKAGE.getEvent_Notes()).
        setColumnName("Notes").
        setCellFactory(notesCellFactory)
        );
    
    /*
     * Row operations
     */
    descriptor.getRowOperations().put(TableRowOperation.NEW_OBJECT_BEFORE, new TableRowOperationDescriptor<EventInfo>("New Event before this one"));
    descriptor.getRowOperations().put(TableRowOperation.NEW_OBJECT_AFTER, new TableRowOperationDescriptor<EventInfo>("New Event below this one"));
    descriptor.getRowOperations().put(TableRowOperation.DELETE_OBJECT, new TableRowOperationDescriptor<EventInfo>("Remove this Event"));
    
    return descriptor;
  }
}


/**
 * A factory to create an 'Image' cell.
 */
class ImageCellFactory implements Callback<TableColumn<EventInfo, Object>, TableCell<EventInfo, Object>> {
  private CustomizationFx customization;
  private int imageHeight;
  private String eventsDirectory;
  
  public ImageCellFactory(CustomizationFx customization, int imageHeight, String eventsDirectory) {
    this.customization = customization;
    this.imageHeight = imageHeight;
    this.eventsDirectory = eventsDirectory;
  }

  @Override
  public TableCell<EventInfo, Object> call(TableColumn<EventInfo, Object> arg0) {
    return new ImageCell(customization, imageHeight, eventsDirectory);
  }
  
}


/**
 * A table cell for an Image.
 */
class ImageCell extends TableCell<EventInfo, Object> {
  private static final Logger LOGGER = Logger.getLogger(ImageCell.class.getName());
  
  private int imageHeight;
  private String eventsDirectory;

  public ImageCell(CustomizationFx customization, int imageHeight, String eventsDirectory) {
    LOGGER.info("<=>");
    this.imageHeight = imageHeight;
    this.eventsDirectory = eventsDirectory;
//    Background background = customization.getComponentFactoryFx().getPanelBackground();
//    if (background != null) {
//      setBackground(background);
//    }
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
      
//      Path imagePath = Paths.get(eventsDirectory, imageFileName);
//      Image image = new Image("file:" + imagePath.toAbsolutePath().toString(), -1, imageHeight,  true, true);      D:\Database\Gebeurtenissen\
      Path imagePath = Paths.get(eventsDirectory, imageFileName);
      Image image;
      if (getTableRow() != null  &&  getTableRow().isSelected()) {
        image = new Image("file:" + imagePath.toAbsolutePath().toString(), -1, 3 * imageHeight,  true, true);
      } else {
        image = new Image("file:" + imagePath.toAbsolutePath().toString(), -1, imageHeight,  true, true);
      }

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
 * A table cell for an attachment.
 */
class AttachmentsCell extends TableCell<EventInfo, Object> {
  private static final Logger LOGGER = Logger.getLogger(AttachmentsCell.class.getName());
  
  private ComponentFactoryFx componentFactory = null;
  
  private int imageHeight;
  private String eventsDirectory;

  public AttachmentsCell(CustomizationFx customization, int imageHeight, String eventsDirectory) {
    LOGGER.info("<=> eventsDirectory=" + eventsDirectory);
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
      if (!(item instanceof List)) {
        throw new IllegalArgumentException("item shall be a List, but is of type '" + item.getClass().getName() + "'");
      }
      @SuppressWarnings("unchecked")
      List<FileReference> fileReferences = (List<FileReference>) item;
      
      HBox hBox = componentFactory.createHBox(0, 0);
      for (FileReference fileReference: fileReferences) {
        VBox vBox = componentFactory.createVBox(12.0, 4.0);
        vBox.setAlignment(Pos.CENTER);
        
        String  imageFileName = fileReference.getFile();
        LOGGER.info("imageFileName: " + imageFileName);

        Path imagePath = Paths.get(eventsDirectory, imageFileName);
        Image image;
        if (getTableRow() != null  &&  getTableRow().isSelected()) {
          image = new Image("file:" + imagePath.toAbsolutePath().toString(), 2 * imageHeight, 2 * imageHeight,  true, true);
        } else {
          image = new Image("file:" + imagePath.toAbsolutePath().toString(), imageHeight, imageHeight,  true, true);
        }

        ImageView imageView = new ImageView(image);
        vBox.getChildren().add(imageView);
        
        Label label = componentFactory.createLabel(imagePath.getFileName().toString());
        vBox.getChildren().add(label);
        
        hBox.getChildren().add(vBox);
      }
            
      setGraphic(hBox);
      setText(null);
    }
    LOGGER.info("<=");
  }
}


/**
 * A factory to create an 'Notes' cell.
 */
class NotesCellFactory implements Callback<TableColumn<EventInfo, Object>, TableCell<EventInfo, Object>> {
  private CustomizationFx customization;
  private int imageHeight;
  
  public NotesCellFactory(CustomizationFx customization, int imageHeight) {
    this.customization = customization;
    this.imageHeight = imageHeight;
  }

  @Override
  public TableCell<EventInfo, Object> call(TableColumn<EventInfo, Object> arg0) {
    return new NotesCell(customization, imageHeight);
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

  public NotesCell(CustomizationFx customization, int imageHeight) {
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
      String markDownText = (String) item;
      WebView webView = new WebView();
      webView.setPrefHeight(imageHeight);
      webView.setPrefWidth(300);
      WebEngine webEngine = webView.getEngine();
      org.commonmark.node.Node document = parser.parse(markDownText);
      String text = renderer.render(document);
      text = addHtmlContext(text);

      webEngine.loadContent(text);
            
      setGraphic(webView);
      setText(null);
    }
    LOGGER.info("<=");
  }

  public String addHtmlContext(String text) {
    StringBuilder buf = new StringBuilder();
    buf.append("<html>");

    buf.append(text);

    buf.append("</html>");


    return buf.toString();
  }
}




