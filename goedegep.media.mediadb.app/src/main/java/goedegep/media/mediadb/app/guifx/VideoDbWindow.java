package goedegep.media.mediadb.app.guifx;

import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.jfx.AppResourcesFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.jfx.MenuUtil;
import goedegep.jfx.PropertyDescriptorsEditorFx;
import goedegep.media.common.MediaRegistry;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Subject;
import goedegep.media.mediadb.model.Video;
import goedegep.properties.app.guifx.PropertiesEditor;
import goedegep.resources.ImageSize;
import goedegep.util.datetime.FlexDate;
import goedegep.util.desktop.DesktopUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class provides the main window for the video database
 * <p>
 * Currently the videos are stored in a Films.xmi EMF file. When music is also stored as an EMF file, both may be merged.
 * 
 */
public class VideoDbWindow extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(VideoDbWindow.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");

  private static final String WINDOW_TITLE = "Film Database";
  private static final MediadbFactory FACTORY = MediadbFactory.eINSTANCE;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;

  private AppResourcesFx appResources;

  /**
   * The Media Database.
   */
  private MediaDb mediaDb;
  
  private MediaRegistry mediaRegistry;

  /**
   * A table listing all films.
   */
  private VideosTable videosTable;
    
  private VBox centerPane;
  private Label statusLabel;
  
  /**
   * Constructor
   * <p>
   * 
   * @param customization the GUI customization.
   */
  public VideoDbWindow(CustomizationFx customization) {
    super(customization, WINDOW_TITLE);

    mediaRegistry = MediaRegistry.getInstance();
    appResources = customization.getResources();

    // Start with an empty media database
    mediaDb = FACTORY.createMediaDb();
    
    // Add a first film
    Video film = FACTORY.createVideo();
    film.setDate(new FlexDate(17, 7, 2022));
    film.setTitle("Example film");
    film.setImage("D:\\Photo\\Thema's\\1978-08 Dagje rivieren, Gorinchem, eend teller rond\\dia0125 - teller eend 00000.jpg");
    Subject subject;
    subject = FACTORY.createSubject();
    subject.setDate(new FlexDate(18, 7, 2022));
    subject.setTitle("Subject1");
    subject.getTags().add("Tag1");
    subject.getTags().add("Tag2");
    film.getSubjects().add(subject);
    subject = FACTORY.createSubject();
    subject.setDate(new FlexDate(19, 7, 2022));
    subject.setTitle("Subject2");
    subject.getTags().add("Tag1");
    subject.getTags().add("Tag4");
    film.getSubjects().add(subject);
    mediaDb.getVideos().add(film);

    createGUI();
        
    EContentAdapter eContentAdapter = new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(org.eclipse.emf.common.notify.Notification notification) {
        super.notifyChanged(notification);
        LOGGER.info("Change detected: " + notification.toString());
        
        if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
          // for now no action
          return;
        }
        
        EObject notifierEObject = (EObject) notification.getNotifier();
        LOGGER.info("notifierEObject: " + notifierEObject.toString());
        Object feature = notification.getFeature();
          if (MEDIA_DB_PACKAGE.getMediaDb_Videos().equals(feature)) {
            videosTable.setObjects(mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Videos());
          }
      }

    };
    mediaDb.eAdapters().add(eContentAdapter);
    
    videosTable.setObjects(mediaDb, MediadbPackage.eINSTANCE.getMediaDb_Videos());

//    init();
  }

  /**
   * Create the GUI.
   */
  private void createGUI() {

    /*
     * Main pane is a BorderPane.
     * North is the menu bar
     * Center is a VBox; the control panel and below that the films table
     * Bottom is a status label
     */

    BorderPane mainPane = new BorderPane();

    mainPane.setTop(createMenuBar());
    
    centerPane = componentFactory.createVBox(18);
    
    centerPane.getChildren().add(createControlPanel());
    
    videosTable = new VideosTable(customization, this, mediaDb);
    videosTable.setMinHeight(800);
    centerPane.getChildren().add(videosTable);

    mainPane.setCenter(centerPane);
    
    // Bottom: Status labal
    String statusText = "Nothing to report for now";
    
    statusLabel = componentFactory.createLabel(statusText);
    mainPane.setBottom(statusLabel);

    Scene scene = new Scene(mainPane, 1300, 950);
    setScene(scene);
    show();
  }

  /**
   * Create the menu bar for this window.
   * 
   * @return the menu bar for this window.
   */
  private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    Menu menu;

    // File menu
    menu = new Menu("File");
    
    // File: Save Video information
    MenuUtil.addMenuItem(menu, "Save Video information", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        saveVideoInformation();
      }
    });
    
    // File: Edit Property Descriptors
    if (mediaRegistry.isDevelopmentMode()) {
      MenuUtil.addMenuItem(menu, "Edit Property Descriptors", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showPropertyDescriptorsEditor();
        }
      });

      // File: Edit User Settings
      MenuUtil.addMenuItem(menu, "Edit User Settings", new EventHandler<ActionEvent>()  {
        public void handle(ActionEvent e) {
          showUserSettingsEditor();
        }
      });
    }

    menuBar.getMenus().add(menu);

    // Video menu
    menu = new Menu("Video");

    // Video: New video
    MenuUtil.addMenuItem(menu, "New video", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        openVideoDetailsEditor(null);
      }
    });

    menuBar.getMenus().add(menu);

    // Help menu
    menu = new Menu("Help");

    // Help: Media DB Information
    MenuUtil.addMenuItem(menu, "Video DB Information", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        DesktopUtil.open("http://mydigitallife.rf.gd/myworld-user-manual/media/mediadb-the-media-database/");
//        new BrowserWindow("MediaDbHelp", customization, "http://mydigitallife.rf.gd/myworld-user-manual/media/mediadb-the-media-database/");
      }
    });

    // Help: About
    MenuUtil.addMenuItem(menu, "About", new EventHandler<ActionEvent>()  {
      public void handle(ActionEvent e) {
        showHelpAboutDialog();
      }
    });

    menuBar.getMenus().add(menu);

    return menuBar;
  }
  
  private Node createControlPanel() {
    HBox controlPanel = componentFactory.createHBox(18, 12);
    
    // Label: "Filter on text:"
    Label label = componentFactory.createLabel("Filter on text:");
    controlPanel.getChildren().add(label);
    
    // TextField: for the text to filter on
    TextField textField = componentFactory.createTextField(200, "Enter the text that has to be in de video information");
    textField.textProperty().addListener((_, _, newValue) -> {
      setFilter(newValue);
    });
    controlPanel.getChildren().add(textField);
        
    return controlPanel;
  }
  
  /**
   * Save the information from the <code>mediaDb</code> to the video information file.
   */
  private void saveVideoInformation() {
    // TODO
  }
  
  /**
   * Open the PropertyDescriptors editor.
   */
  private void showPropertyDescriptorsEditor() {
    new PropertyDescriptorsEditorFx(customization, mediaRegistry.getPropertyDescriptorsFileURI());
  }

  /**
   * Open the User Settings editor.
   */
  private void showUserSettingsEditor() {
    PropertiesEditor propertiesEditor = new PropertiesEditor("Edit Media settings", customization,
        mediaRegistry.getPropertyDescriptorsFileURI(), mediaRegistry.getUserPropertiesFileName());
    propertiesEditor.show();
  }

  /**
   * Show the dialog with information about this application.
   */
  private void showHelpAboutDialog() {
    componentFactory.createApplicationInformationDialog(
        "About MediaDb",
        appResources.getApplicationImage(ImageSize.SIZE_3),
        null,
        mediaRegistry.getShortProductInfo() + NEWLINE +
        "Versie: " + mediaRegistry.getVersion() + NEWLINE +
        mediaRegistry.getCopyrightMessage() + NEWLINE +
        "Auteur: " + mediaRegistry.getAuthor())
        .showAndWait();
  }
  
  /**
   * Set the filter expression on the <code>filmsTable</code>.
   * 
   * @param text the filter expression to set on the table.
   */
  private void setFilter(String text) {
    videosTable.setFilterExpression(text, null);
  }
  
  /**
   * Open the VideoDetailsEditor to enter a new video.
   */
  void openVideoDetailsEditor(Video video) {
    VideoDetailsEditor videoDetailsEditor = new VideoDetailsEditor(customization, mediaDb);
    if (video != null) {
      videoDetailsEditor.setVideo(video);
    }
  }
}
