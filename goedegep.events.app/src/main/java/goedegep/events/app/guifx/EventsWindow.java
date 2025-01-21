package goedegep.events.app.guifx;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.PageSizeUnits;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import goedegep.events.app.EventsRegistry;
import goedegep.events.app.EventsService;
import goedegep.events.app.EventsToHtmlConverter;
import goedegep.events.model.Events;
import goedegep.events.model.EventsPackage;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.resources.ImageSize;
import goedegep.util.Result;
import goedegep.util.emf.EMFResource;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class is the main window of the Events application.
 * <p>
 * This window shows an overview of the {@link Events} in a table.<br/>
 * Creation of this window requires that {@code EventsRegistry.eventsFileName} has a value that points to a file with {@link Events} (see {@link EventsLauncher}).
 */
public class EventsWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(EventsWindow.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * The {@link EventsService} this window operates on.
   */
  private EventsService eventsService;
  
  /**
   * The resources for this application.
   */
  private EventsAppResources appResources;
  
  /**
   * The {@link EMFResource} for the {@link Events}
   */
  private EMFResource<Events> eventsResource = null;
  
  /**
   * The {@link Events}
   */
  private Events events = null;
  
  /**
   * The table showing the overview of the events.
   */
  private EventsTable eventsTable = null;
  
  /**
   * A {@code Label} showing status information at the bottom of the window.
   */
  private Label statusLabel = new Label("Nothing to report for now");

  
  /**
   * Constructor
   * <p>
   * Creation of this window requires that {@code EventsRegistry.eventsFileName} has a value that points to a file with {@link Events}.
   * 
   * @param customization the GUI customization.
   * @param eventsService the {@code EventsService} this window operates on.
   */
  public EventsWindow(CustomizationFx customization, EventsService eventsService) {
    super(customization, null);
    LOGGER.info("=>");
    
    this.eventsService = eventsService;
    appResources = (EventsAppResources) getResources();
        
    createGUI();

    eventsResource = eventsService.getEventsResource();
    events = eventsResource.getEObject();
               
    setX(10);
    setY(20);
        
    eventsTable.setObjects(events, EventsPackage.eINSTANCE.getEvents_Events());
    
    updateTitle();
    
    eventsResource.dirtyProperty().addListener((observable, oldValue, newValue) -> updateTitle());
    
    show();
    
    LOGGER.info("<=");
  }

  /**
   * Create the GUI.
   */
  private void createGUI() {
    
    /*
     * Main pane is a BorderPane.
     * North is the menu bar
     * Center is the events table.
     * Bottom is a status label
     */
    
    BorderPane mainPane = new BorderPane();
        
    mainPane.setTop(createMenuBar());
    
    VBox vBox = componentFactory.createVBox(12.0);
    // Events EObjectTable
    eventsTable = new EventsTable(customization);
    vBox.getChildren().add(eventsTable);
    
    VBox.setVgrow(vBox, Priority.ALWAYS);
    mainPane.setCenter(eventsTable);
    
    mainPane.setBottom(createStatusAndButtonsPanel());

    setScene(new Scene(mainPane, 1700, 900));
  }
    
  /**
   * Create the menu bar for this window.
   * 
   * @return the menu bar for this window.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu menu;
    MenuItem menuItem;

    // File menu
    menu = new Menu("File");

    // File: Save events
    menuItem = componentFactory.createMenuItem("Save events");
    menuItem.setOnAction(event -> saveEvents());
    menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
    menu.getItems().add(menuItem);

    // File: Export events as PDF
    menuItem = componentFactory.createMenuItem("Export events as PDF");
    menuItem.setOnAction(event -> exportEventsToPdf());
    menu.getItems().add(menuItem);
    
    // File: Edit Properties
    MenuUtil.addMenuItem(menu, "Edit Properties", event -> showPropertiesEditor());
    
    if (EventsRegistry.developmentMode) {
      // File: Edit Property Descriptors
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", event -> showPropertyDescriptorsEditor());
    }
    
    menuBar.getMenus().add(menu);
        
    
    // Help menu
    menu = new Menu("Help");

    // Help: About
    MenuUtil.addMenuItem(menu, "About", event -> showHelpAboutDialog());

    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
  private Node createStatusAndButtonsPanel() {
    HBox hBox = componentFactory.createHBox(4.0, 12.0);
    hBox.getChildren().add(statusLabel);
    
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    hBox.getChildren().add(spacer);
        
    Button newEventButton = componentFactory.createButton("New Event", "Open the Event Editor for a new event");
    newEventButton.setOnAction(e -> {
      EventsLauncher.getInstance().LaunchEventsEditor(null);
    });
    hBox.getChildren().add(newEventButton);
    
    return hBox;
  }

  /**
   * Save the events information to the related file.
   */
  private void saveEvents() {
    Result result = eventsService.saveEvents();
    switch (result.getResultType()) {
    case OK:
      statusLabel.setText(result.getMessage());
      break;
    
    case FAILED:
      componentFactory.createErrorDialog(
          "Saving the events has failed.",
          result.getMessage()
          ).showAndWait();
      break;
      
    default:
      throw new RuntimeException("Unknown result type: " + result.getResultType());
    }
  }
  
  /**
   * Export the events to a PDF file.
   */
  private void exportEventsToPdf() {
    EventsToHtmlConverter eventsToHtmlConverter = new EventsToHtmlConverter();
    String eventsHtmlDocument = eventsToHtmlConverter.eventsToHtml(events);
    
    String eventsFolder = EventsRegistry.eventsFolderName;

    Path pdfFilePath = Paths.get(eventsFolder, "Events.pdf");
    try(OutputStream os = Files.newOutputStream(pdfFilePath)) {
      PdfRendererBuilder builder = new PdfRendererBuilder();
      builder.useFastMode();
      builder.useDefaultPageSize(600, 1000, PageSizeUnits.MM);
      builder.withHtmlContent(eventsHtmlDocument, null);
      builder.toStream(os);
      builder.run();
      os.close();
      statusLabel.setText("Event exported to " + pdfFilePath.toString());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
    
  /**
   * Open an editor to edit the property descriptors (in development mode only).
   */
  private void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(customization, EventsRegistry.propertyDescriptorsResource);
  }
  
  /**
   * Open an editor where the user can edit his preferences.
   */
  private void showPropertiesEditor() {
    new PropertiesEditor("Events properties", customization, null,
        EventsRegistry.propertyDescriptorsResource, EventsRegistry.customPropertiesFile);
  }

  /**
   * Show a dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About Events",
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null, 
        EventsRegistry.shortProductInfo + NEWLINE +
        "Version: " + EventsRegistry.version + NEWLINE +
        EventsRegistry.copyrightMessage + NEWLINE +
        "Author: " + EventsRegistry.author)
        .showAndWait();
  }

  /**
   * Update the window title.
   * <p>
   * The format of the title is: &lt;title&gt; - &lt;dirty&gt;&lt;file-name&gt;<br/>
   * Where:<br/>
   * &lt;title&gt; is the language specific window title<br/>
   * &lt;dirty&gt; is a '*' symbol if the events file is dirty, empty otherwise<br/>
   * &lt;file-name&gt; is the name of the events file.
   * 
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("Events");
    buf.append(" - ");
    if (eventsResource.isDirty()) {
      buf.append("*");
    }
    String fileName = eventsResource.getFileName();
    if (fileName.equals("")) {
      fileName = "<NoName>";
    }
    buf.append(fileName);
    
    setTitle(buf.toString());
  }
}
