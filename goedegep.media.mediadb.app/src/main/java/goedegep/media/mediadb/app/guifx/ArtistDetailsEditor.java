package goedegep.media.mediadb.app.guifx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxStage;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * This clas provides a window to add an Artist to a media database.
 */
public class ArtistDetailsEditor extends JfxStage {
  private static final Logger LOGGER = Logger.getLogger(ArtistDetailsEditor.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
  private MediaDb mediaDb;
  private ComponentFactoryFx componentFactory;
  private String message = null;
  private String artistName = null;
  
  private TextField artistNameTextField;
  private ComboBox<Artist> containerArtistComboBox;
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param windowTitle the window title
   * @param mediaDb the media database to add the artist to
   */
  public ArtistDetailsEditor(CustomizationFx customization, String windowTitle, MediaDb mediaDb) {
    this(customization, windowTitle, mediaDb, null, null);
  }
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param windowTitle the window title
   * @param mediaDb the media database to add the artist to
   */
  public ArtistDetailsEditor(CustomizationFx customization, String windowTitle, MediaDb mediaDb, String message, String artistName) {
    super(windowTitle, customization);
    
    this.mediaDb = mediaDb;
    this.message = message;
    this.artistName = artistName;
    
    componentFactory = customization.getComponentFactoryFx();

    createGUI();
  }
  
  /**
   * Create the GUI
   */
  private void createGUI() {
    /*
     * Main pane is a BorderPane.
     * Center is a GridPane with the artist details; Bottom is a control panel with 'add' and 'cancel' buttons.
     */

    BorderPane mainPane = new BorderPane();
    
    if (message != null) {
      HBox messageBox = componentFactory.createHBox(10.0);
      TextArea messageArea = componentFactory.createTextArea();
      messageArea.setText(message);
      messageBox.getChildren().add(messageArea);
      mainPane.setTop(messageBox);
    }
        
    GridPane gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'Name: <artist-name>'
    Label label = componentFactory.createLabel("Name:");
    gridPane.add(label, 0, 0);
    
    artistNameTextField = componentFactory.createTextField(600, null);
    if (artistName != null) {
      artistNameTextField.setText(artistName);
    }
    gridPane.add(artistNameTextField, 1, 0);

    // Second row: 'Container artist: <artist>'
    label = componentFactory.createLabel("Container artist:");
    gridPane.add(label, 0, 1);
    
    containerArtistComboBox = componentFactory.createComboBox(null);
    updateContainerArtistComboBox();
    containerArtistComboBox.setMaxWidth(400.0);
    
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
        if (MEDIA_DB_PACKAGE.getMediaDb_Artists().equals(feature)) {
          updateContainerArtistComboBox();
        }

      }

    };
    mediaDb.eAdapters().add(eContentAdapter);
    
    gridPane.add(containerArtistComboBox, 1, 1);

    mainPane.setCenter(gridPane);
    

    /*
     * Controls panel on the bottom
     */
    mainPane.setBottom(createOkOrCancelPanel());


    Scene scene = new Scene(mainPane);
    setScene(scene);
//    show();
  }
  
  private void updateContainerArtistComboBox() {
    Artist currentSelectedArtist = containerArtistComboBox.getValue();
    ObservableList<Artist> artists = FXCollections.observableArrayList(mediaDb.getArtists());
    artists.sort((Artist artist1, Artist artist2) -> {return artist1.getName().compareTo(artist2.getName());});
    containerArtistComboBox.setItems(artists);
    if (currentSelectedArtist != null) {
      containerArtistComboBox.setValue(currentSelectedArtist);
    }
  }
  
  
  /**
   * Create a panel with 'Add artist' and a 'Cancel' buttons.
   * <p>
   * The action for the 'Add artist' button is {@link #addArtist()}.
   * The action for the 'Cancel' button is {@link #closeWindow()}.
   * 
   * @return
   */
  private Node createOkOrCancelPanel() {
    HBox hBox = componentFactory.createHBox(10.0, 12.0);
   
    // Two buttons: "Add artist", "Cancel".
    Button button;
    
    button = componentFactory.createButton("Add artist", "Add the artist to the media database");
    button.setOnAction(e -> addArtist());
    hBox.getChildren().add(button);
    
    button = componentFactory.createButton("Cancel", "Exit window without saving");
    button.setOnAction(e -> closeWindow());
    hBox.getChildren().add(button);
    
    return hBox;
  }
  
  /**
   * Create an Artist based on the provided information and add it to the media database.
   * <p>
   * If the provided information is correct, the Artist is created and added to the media database.
   * Otherwise, an Alert is shown with information about what is wrong.
   */
  private void addArtist() {
    LOGGER.severe("=>");
    try {
      Artist artist = createArtist();
      LOGGER.severe("No exception, so adding artist to database");
      mediaDb.getArtists().add(artist);
//      LOGGER.severe("New list of artists:");
//      for (Artist a: mediaDb.getArtists()) {
//        LOGGER.severe(a.getName());
//      }
    } catch (ArtistDetailsException e) {
      StringBuilder buf = new StringBuilder();
      for (String problem: e.getProblems()) {
        buf.append(problem);
        buf.append(NEW_LINE);
      }
      componentFactory.createErrorDialog("Problem in artist details", buf.toString()).showAndWait();
    }
    LOGGER.severe("<=");
  }
  
  /**
   * Create an Artist based on the provided information.
   * <p>
   * If the provided information isn't correct, an {@link ArtistDetailsException} is throw with a list error messages.
   * 
   * @return the new Artist
   * @throws ArtistDetailsException if the provided information isn't correct.
   */
  private Artist createArtist() throws ArtistDetailsException {
    Artist artist;
    
    artist = MEDIA_DB_FACTORY.createArtist();
    
    List<String> problems = new ArrayList<>();
    
    // Name
    String name = artistNameTextField.getText().trim();
    if (name.isEmpty()) {
      problems.add("Name may not be empty");
    } else {
      artist.setName(name);
    }
    
    // Container artist
    Artist containerArtist = containerArtistComboBox.getValue();
    if (containerArtist != null) {
      artist.setContainerArtist(containerArtist);
    }    
    
    if (problems.size() != 0) {
      throw new ArtistDetailsException(problems);
    }
    
    return artist;
  }

  private void closeWindow() {
    System.out.println("AlbumDetailsEditor: closeWindow");
    this.close();
  }
}

/**
 * An exception with a list messages about what is wrong in the provided artist information.
 */
@SuppressWarnings("serial")
class ArtistDetailsException extends Exception {
  private List<String> problems;
  
  ArtistDetailsException(List<String> problems) {
    this.problems = problems;
  }
  
  List<String> getProblems() {
    return problems;
  }
}

