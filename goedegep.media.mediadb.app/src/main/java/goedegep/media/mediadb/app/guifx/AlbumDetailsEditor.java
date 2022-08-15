package goedegep.media.mediadb.app.guifx;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.common.base.Optional;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import ealvatag.audio.AudioFile;
import ealvatag.audio.AudioFileIO;
import ealvatag.audio.exceptions.CannotReadException;
import ealvatag.audio.exceptions.InvalidAudioFrameException;
import ealvatag.tag.FieldKey;
import ealvatag.tag.NullTag;
import ealvatag.tag.Tag;
import ealvatag.tag.TagException;
import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.controls.AutoCompleteTextFieldObjectInput;
import goedegep.jfx.controls.FolderSelecter;
import goedegep.jfx.controls.ObjectControlFlexDate;
import goedegep.jfx.controls.ObjectControlMultiLineString;
import goedegep.jfx.controls.TextFieldObjectInput;
import goedegep.media.app.MediaRegistry;
import goedegep.media.mediadb.app.ArtistStringConverterAndChecker;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.InformationType;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyCompilation;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.impl.DiscImpl;
import goedegep.media.mediadb.model.impl.MediumInfoImpl;
import goedegep.media.mediadb.model.impl.MyTrackInfoImpl;
import goedegep.media.mediadb.model.impl.TrackReferenceImpl;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.util.Tuplet;
import goedegep.util.datetime.FlexDate;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import goedegep.util.string.StringUtil;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlbumDetailsEditor extends AlbumDetailsAbstract {
  
  /*
   * Strategy:
   * This editor can be used to edit album details, or to create a new album.
   * If the field 'album' is null (by default), the editor is in the 'new album' mode. Otherwise it is in 'edit album' mode.
   * 
   * All information of an album is 'stored' in the GUI controls and a number of lists. Together these are referred to as the GUI controls.
   * When a new album is set (which may be null), the information from the album is stored in the controls. If the album is null,
   * all information is cleared.
   * Any user changes, or 'imports' are only stored in the GUI controls.
   * Upon 'add album' an album is created from the controls, 'album' is set to this value and the 'album' is added to the database.
   * Upon 'update', the 'album' is cleared and filled from the controls.
   */
  
  private static final Logger LOGGER = Logger.getLogger(AlbumDetailsWindow.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;

  /**
   * The Media Database.
   */
  private MediaDb mediaDb;
  
  private Map<Track, Path> trackDiscLocationMap;
  private ArtistStringConverterAndChecker artistStringConverterAndChecker;
  
  
  /**
   * Main UI panel.
   */
  private BorderPane mainPane;
  
  /**
   * Panel with the controls for importing information from a folder with album tracks.
   */
  private Node importControlsPanel;
  
  /**
   * Control for the album title.
   * The title is plain text.
   */
  private TextFieldObjectInput<String> albumTitleTextField;
  
  /**
   * Control for the album artist name.
   */
  private AutoCompleteTextFieldObjectInput<Artist> albumArtistObjectControl;
  
  /**
   * Control for the album release date.
   * A <code>FlexDateFormat</code> is used to fill and parse this value.
   */
  private ObjectControlFlexDate albumIssueDateTextField;
  
  /**
   * Control for the album Id.
   * The album Id is plain text.
   */
  private TextFieldObjectInput<String> albumIdTextField;
  
  /**
   * Control for the Description Title (which is plain text).
   */
  private TextFieldObjectInput<String> descriptionTitleTextField;
  
  /**
   * Control for the Description (which is plain MarkDown text).
   */
  private ObjectControlMultiLineString descriptionTextArea;
  
  /**
   * Control for the collaborating artists, a.k.a. the <code>players</code>
   */
  private GridPane playersGridPane;
  
  /**
   * List of players (collaborating artists).
   * These are shown in the <code>playersGridPane</code>
   */
  private List<Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField>> players = null;
  
  /**
   * List of front images files.
   * These are shown in the <code>frontImagesHBox</code>.
   */
  private List<File> frontImageFiles = null;
  
  /**
   * Shows the album front pictures, which are stored in the <code>frontImageFiles</code>.
   */
  private HBox frontImagesHBox;
  
  /**
   * List of front inside images files.
   * These are shown in the <code>frontInsideImagesHBox</code>.
   */
  private List<File> frontInsideImageFiles = null;
  
  /**
   * Shows the album front inlay pictures, which are stored in the <code>frontInsideImageFiles</code>.
   */
  private HBox frontInsideImagesHBox;
  
  /**
   * List of back images files.
   * These are shown in the <code>backImagesHBox</code>.
   */
  private List<File> backImageFiles = null;
  
  /**
   * Shows the album back pictures, which are stored in the <code>backImageFiles</code>.
   */
  private HBox backImagesHBox;
  
  /**
   * List of label images files.
   * These are shown in the <code>labelImagesHBox</code>.
   */
  private List<File> labelImageFiles = null;
  
  /**
   * Shows the album label pictures, which are stored in the <code>labelImageFiles</code>.
   */
  private HBox labelImagesHBox;
  
  /**
   * Shows the media on which the album was issued
   */
  private HBox issuedOnMediaPane;
  
  /**
   * List of media on which the album was issued
   */
  private List<Label> issuedOnMediaLabels = null;
  
  /**
   * Shows whether the album is a compilation album or not.
   */
  private CheckBox isCompilationAlbumCheckBox;
  
  /**
   * Shows whether the album is 'my own' compilation or not.
   */
  private CheckBox isOwnCompilationCheckBox;
  
  /**
   * Shows whether the album is a movie soundtrack or not.
   */
  private CheckBox isSoundTrackCheckBox;
  
  /**
   * Indicates whether I've had the album on lp or not.
   */
  private CheckBox iveHadOnLpCheckBox;
  
  /**
   * Shows the 'I want' information
   */
  private ComboBox<IWantWrapper> iWantComboBox;
  
  /**
   * Shows My Comments
   */
  private ObjectControlMultiLineString myCommentsTextArea;

  /**
   * Shows the tracks per disc in tables
   */
  private VBox discsVBox;
  
  /**
   * List of the Disc copies of the Discs of the album.
   */
  private List<DiscCopy> discCopies;
  
  /**
   * Controls for the NEW mode.
   */
  private Node addOrCancelPanel;
  
  /**
   * Controls for the EDIT mode.
   */
  private Node updateOrCancelPanel;
 
  /**
   * The Album which is currently being edited.
   */
  private Album album = null;
  
  private Stage currentLargePictureStage;
  
  private EditMode editMode = EditMode.NEW;

  /**
   * Constructor.
   * <p>
   * Use this constructor to open the editor for creating a new album.
   * 
   * @param customization the GUI customization.
   */
  public AlbumDetailsEditor(CustomizationFx customization, MediaDb mediaDb, Map<Track, Path> trackDiscLocationMap) {
    super(null, customization);
    
    this.customization = customization;
    this.mediaDb = mediaDb;
    this.trackDiscLocationMap = trackDiscLocationMap;
    
    componentFactory = customization.getComponentFactoryFx();
    artistStringConverterAndChecker = new ArtistStringConverterAndChecker(mediaDb);
    players = new ArrayList<>();
    frontImageFiles = new ArrayList<>();
    frontInsideImageFiles = new ArrayList<>();
    backImageFiles = new ArrayList<>();
    labelImageFiles = new ArrayList<>();
    issuedOnMediaLabels = new ArrayList<>();
    discCopies = new ArrayList<>();

    createGUI();
    
    updateControlsForAlbum(null);
    updateTitle();
  }

  /**
   * Constructor.
   * <p>
   * Use this constructor to edit an existing album.
   * 
   * @param customization the GUI customization.
   */
  public AlbumDetailsEditor(CustomizationFx customization, MediaDb mediaDb, Map<Track, Path> trackDiscLocationMap, Album album) {
    this(customization, mediaDb, trackDiscLocationMap);
    
    setAlbum(album);
  }

  /**
   * Change the <code>Album</code> for which the information is shown and which can be modified.
   * 
   * @param album the <code>Album</code> for which the information is shown in this window.
   */
  public void setAlbum(Album album) {
    this.album = album;
    updateControlsForAlbum(album);
    
    updateEditMode(album);
  }
  
  /**
   * Create the GUI.
   */
  private void createGUI() {

    /*
     * Main pane is a BorderPane.
     * Top contains import controls
     * Center is a VBox with all album details
     */

    mainPane = new BorderPane();
    

    /*
     * Import controls panel at the top
     */
    importControlsPanel = createImportControlsPanel();
    mainPane.setTop(importControlsPanel);
    
    
    /*
     * Center: album details
     */
    VBox centerPane = componentFactory.createVBox(18);
    
    /*
     * General information
     */
    GridPane gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'Album: <album-title>'
    Label label = componentFactory.createLabel("Title:");
    gridPane.add(label, 0, 0);
    
    albumTitleTextField = componentFactory.createTextFieldObjectInput(null, null, 600, false, "The album title is a mandatory field");
    gridPane.add(albumTitleTextField, 1, 0);

    // Second row: 'Artist: <artist>'   'New artist' button
    label = componentFactory.createLabel("Artist:");
    gridPane.add(label, 0, 1);
    
    albumArtistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(artistStringConverterAndChecker, null, 300, false, "Enter the album artist");
    updateContainerArtistComboBox();
    gridPane.add(albumArtistObjectControl, 1, 1);
    
    Button newArtistButton = componentFactory.createButton("New artist", "The artist of the album has to be selected from the list of known artists. With this button you can add a new artist to the database");
    newArtistButton.setOnAction(e -> (new ArtistDetailsEditor(getCustomization(), "New artist", mediaDb)).show());
    gridPane.add(newArtistButton, 2, 1);
   
    // Third row: 'Issue date: <album-issue-date>'
    label = componentFactory.createLabel("Issued:");
    gridPane.add(label, 0, 2);
    
    albumIssueDateTextField = componentFactory.createObjectInputFlexDate(null, 600, true, "Optional first issue date of the album", null);
    gridPane.add(albumIssueDateTextField, 1, 2);
    
    // Fourth row: 'Album Id: <album-id>'
    label = componentFactory.createLabel("Album Id:");
    gridPane.add(label, 0, 3);
    
    albumIdTextField = componentFactory.createTextFieldObjectInput(null, null, 600, true, "Enter the optional Id of the album");
    gridPane.add(albumIdTextField, 1, 3);
    
    // Fifth row: 'Collaborating Artists: '
    label = componentFactory.createLabel("Collaborating artists:");
    gridPane.add(label, 0, 4);
    
    Node node = createCollaboratingArtistsPanel();
    gridPane.add(node, 1, 4, 2, 4);
    
    centerPane.getChildren().add(gridPane);
    
    /*
     * Front images
     */
    HBox albumImagesPanel = componentFactory.createHBox(10.0);
    VBox albumImagesControlsBox = componentFactory.createVBox(10.0);
    frontImagesHBox = componentFactory.createHBox(10.0, 10.0);
    label = componentFactory.createLabel("Front images");
    albumImagesControlsBox.getChildren().add(label);
    Button addImageButton = componentFactory.createButton("Add image", "click to select an image file to add");
    addImageButton.setOnAction((e) -> {
      FileChooser fileChooser = componentFactory.createFileChooser("Add front image");
      if (MediaRegistry.albumInfoDirectory != null) {
        fileChooser.setInitialDirectory(new File(MediaRegistry.albumInfoDirectory + File.separator + "Pictures"));
      }
      File file = fileChooser.showOpenDialog(this);
      if (file != null) {
        frontImageFiles.add(file);
      }
      updateImagesPanel(frontImagesHBox, frontImageFiles);
    });
    albumImagesControlsBox.getChildren().add(addImageButton);
    albumImagesPanel.getChildren().add(albumImagesControlsBox);
    albumImagesPanel.getChildren().add(frontImagesHBox);
    centerPane.getChildren().add(albumImagesPanel);
    
    /*
     * Front Inside images
     */
    albumImagesPanel = componentFactory.createHBox(10.0);
    albumImagesControlsBox = componentFactory.createVBox(10.0);
    frontInsideImagesHBox = componentFactory.createHBox(10.0, 10.0);
    label = componentFactory.createLabel("Front Inside images");
    albumImagesControlsBox.getChildren().add(label);
    addImageButton = componentFactory.createButton("Add image", "click to select an image file to add");
    addImageButton.setOnAction((e) -> {
      FileChooser fileChooser = componentFactory.createFileChooser("Add front inside image");
      if (MediaRegistry.albumInfoDirectory != null) {
        fileChooser.setInitialDirectory(new File(MediaRegistry.albumInfoDirectory + File.separator + "Pictures"));
      }
      File file = fileChooser.showOpenDialog(this);
      if (file != null) {
        frontInsideImageFiles.add(file);
      }
      updateImagesPanel(frontInsideImagesHBox, frontInsideImageFiles);
    });
    albumImagesControlsBox.getChildren().add(addImageButton);
    albumImagesPanel.getChildren().add(albumImagesControlsBox);
    albumImagesPanel.getChildren().add(frontInsideImagesHBox);
    centerPane.getChildren().add(albumImagesPanel);
    
    /*
     * Back images
     */
    albumImagesPanel = componentFactory.createHBox(10.0);
    albumImagesControlsBox = componentFactory.createVBox(10.0);
    backImagesHBox = componentFactory.createHBox(10.0, 10.0);
    label = componentFactory.createLabel("Back images");
    albumImagesControlsBox.getChildren().add(label);
    addImageButton = componentFactory.createButton("Add image", "click to select an image file to add");
    addImageButton.setOnAction((e) -> {
      FileChooser fileChooser = componentFactory.createFileChooser("Add back image");
      if (MediaRegistry.albumInfoDirectory != null) {
        fileChooser.setInitialDirectory(new File(MediaRegistry.albumInfoDirectory + File.separator + "Pictures"));
      }
      File file = fileChooser.showOpenDialog(this);
      if (file != null) {
        backImageFiles.add(file);
      }
      updateImagesPanel(backImagesHBox, backImageFiles);
    });
    albumImagesControlsBox.getChildren().add(addImageButton);
    albumImagesPanel.getChildren().add(albumImagesControlsBox);
    albumImagesPanel.getChildren().add(backImagesHBox);
    centerPane.getChildren().add(albumImagesPanel);
    
    /*
     * Label images
     */
    albumImagesPanel = componentFactory.createHBox(10.0);
    albumImagesControlsBox = componentFactory.createVBox(10.0);
    labelImagesHBox = componentFactory.createHBox(10.0, 10.0);
    label = componentFactory.createLabel("Label images");
    albumImagesControlsBox.getChildren().add(label);
    addImageButton = componentFactory.createButton("Add image", "click to select an image file to add");
    addImageButton.setOnAction((e) -> {
      FileChooser fileChooser = componentFactory.createFileChooser("Add label image");
      if (MediaRegistry.albumInfoDirectory != null) {
        fileChooser.setInitialDirectory(new File(MediaRegistry.albumInfoDirectory + File.separator + "Pictures"));
      }
      File file = fileChooser.showOpenDialog(this);
      if (file != null) {
        labelImageFiles.add(file);
      }
      updateImagesPanel(labelImagesHBox, labelImageFiles);
    });
    albumImagesControlsBox.getChildren().add(addImageButton);
    albumImagesPanel.getChildren().add(albumImagesControlsBox);
    albumImagesPanel.getChildren().add(labelImagesHBox);
    centerPane.getChildren().add(albumImagesPanel);
    
    /*
     * Description (with Title), comments
     */
    gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'Description title: <description-title>'
    label = componentFactory.createLabel("Description title:");
    gridPane.add(label, 0, 0);
    
    descriptionTitleTextField = componentFactory.createTextFieldObjectInput(null, null, 600, true, "Enter the optional titel of the description");
    gridPane.add(descriptionTitleTextField, 1, 0);
    
    // second row: 'Description: <description>'
    label = componentFactory.createLabel("Description:");
    gridPane.add(label, 0, 1);
    
    descriptionTextArea = componentFactory.createObjectInputMultiLineString(null, 400, true, "Enter an optional description of the album", null);
    gridPane.add(descriptionTextArea, 1, 1);
    
    // Fourth row: 'Issued on: <medium-1> ... <medium-n> <medium-select-combobox> <add-medium-button>    
    label = componentFactory.createLabel("Issued on:");
    gridPane.add(label, 0, 2);
    
    node = createIssuedOnMediaPanel();
    gridPane.add(node, 1, 2, 2, 4);
    
    centerPane.getChildren().add(gridPane);
    
    /*
     * Checkboxes for 'compilation album', 'own compilation' and 'soundtrack'.
     */
    HBox hBox = componentFactory.createHBox(10.0, 10.0);
    isCompilationAlbumCheckBox = componentFactory.createCheckBox("compilation album", false);
    isOwnCompilationCheckBox = componentFactory.createCheckBox("own compilation", false);
    isSoundTrackCheckBox = componentFactory.createCheckBox("soundtrack", false);
    iveHadOnLpCheckBox = componentFactory.createCheckBox("I've had on lp", false);
    hBox.getChildren().addAll(isCompilationAlbumCheckBox, isOwnCompilationCheckBox, isSoundTrackCheckBox, iveHadOnLpCheckBox);
    
    centerPane.getChildren().add(hBox);
    
    /*
     * MyInfo
     */
    gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'I want: <i-want>'
    label = componentFactory.createLabel("I want:");
    gridPane.add(label, 0, 0);
    
    iWantComboBox = componentFactory.createComboBox(IWantWrapper.values());
    gridPane.add(iWantComboBox, 1, 0);
    
    // Second row: 'MyComments: <my-comments>'
    label = componentFactory.createLabel("My comments:");
    gridPane.add(label, 0, 1);
    
    myCommentsTextArea = componentFactory.createObjectInputMultiLineString(null, 400, true, "Enter an optional personal comment about the album", null);
    gridPane.add(myCommentsTextArea, 1, 1);
    
    centerPane.getChildren().add(gridPane);

    
    /*
     * Disc tracks tables.
     */
    discsVBox = componentFactory.createVBox(10.0, 10.0);
    centerPane.getChildren().add(discsVBox);
    
    mainPane.setCenter(centerPane);
    
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


    /*
     * Controls panel on the bottom
     */
    addOrCancelPanel = createAddOrCancelPanel();
    updateOrCancelPanel = createUpdateOrCancelPanel();
    
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(mainPane);
//    Group root = new Group();
//    final ScrollBar scrollBar = new ScrollBar();
//    scrollBar.setMin(0);
//    scrollBar.setOrientation(Orientation.VERTICAL);
//    scrollBar.setPrefHeight(180);
//    scrollBar.setMax(360);
//    
//    scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
//      public void changed(ObservableValue<? extends Number> ov,
//          Number old_val, Number new_val) {
//        mainPane.setLayoutY(-new_val.doubleValue() * 1.5);
//      }
//    });
//    
//    root.getChildren().addAll(mainPane, scrollBar);

    Scene scene = new Scene(scrollPane, 1200, 1200);
    setScene(scene);
    show();
  }
  
  private Node createCollaboratingArtistsPanel() {
    playersGridPane = componentFactory.createGridPane(10.0, 10.0);

    return playersGridPane;
  }
  
  private Node createIssuedOnMediaPanel() {
    issuedOnMediaPane = componentFactory.createHBox(10.0);
    
    return issuedOnMediaPane;
  }
    
  /**
   * Update the albumArtistObjectControl, in case there is a change in the list of artists.
   */
  private void updateContainerArtistComboBox() {
    Artist currentSelectedArtist = albumArtistObjectControl.getObjectValue();
    albumArtistObjectControl.setOptions(mediaDb.getArtists());
    albumArtistObjectControl.setObjectValue(currentSelectedArtist);
  }
    
  /**
   * Update all GUI controls for the details of an album.
   * <p>
   * If the album is null, all controls are cleared.
   * 
   * @param album the <code>Album</code> for which the GUI controls are updated. This value may be null, in which case all controls are cleared.
   */
  private void updateControlsForAlbum(Album album) {
    // First update the lists
    updatePlayersForAlbum(album);
    updatePicturesForAlbum(album);
    updateDiscs(album);
    
    // Then the controls
    updateGeneralInformation(album);
    updateCollaboratingArtists(album);
    updateIssuedOnMedia(album);
    updateImagesPanel(frontImagesHBox, frontImageFiles);
    updateImagesPanel(frontInsideImagesHBox, frontInsideImageFiles);
    updateImagesPanel(backImagesHBox, backImageFiles);
    updateImagesPanel(labelImagesHBox, labelImageFiles);
    updateCheckBoxes(album);
    updateDiscsPanel(album);
  }
  
  private void updatePlayersForAlbum(Album album) {
    players.clear();
    
    if (album != null) {
      for (Player player: album.getPlayers()) {
        
        AutoCompleteTextFieldObjectInput<Artist> artistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(artistStringConverterAndChecker, null, 300, false, "Enter a player");
        artistObjectControl.setObjectValue(player.getArtist());

        TextField playerInstrumentTextField = componentFactory.createTextField(300, "A comma separated list of instruments");
        playerInstrumentTextField.setText(StringUtil.stringCollectionToCommaSeparatedStrings(player.getInstruments()));

        Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> tuplet = createPlayerTuplet(player.getArtist(), player.getInstruments());
        players.add(tuplet);
      }      
    }
  }
  
  /**
   * Create a player tuplet, which consists of an AutoCompleteTextFieldObjectInput for the artist and
   * a TextField for the instruments.
   * 
   * @param artist Initial value for the artist AutoCompleteTextFieldObjectInput
   * @param instruments Initial value for the instruments, shown in the TextField.
   * @return the newly created tuplet.
   */
  private Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> createPlayerTuplet(Artist artist, List<String> instruments) {
    AutoCompleteTextFieldObjectInput<Artist> artistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(artistStringConverterAndChecker, null, 300, false, "Enter a player");
    artistObjectControl.setObjectValue(artist);
    artistObjectControl.setOptions(mediaDb.getArtists());

    TextField playerInstrumentTextField = componentFactory.createTextField(300, "A comma separated list of instruments");
    playerInstrumentTextField.setText(StringUtil.stringCollectionToCommaSeparatedStrings(instruments));

    Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> tuplet = new Tuplet<>(artistObjectControl, playerInstrumentTextField);
    
    return tuplet;
  }
  
  private void updatePicturesForAlbum(Album album) {
    frontImageFiles.clear();
    frontInsideImageFiles.clear();
    backImageFiles.clear();
    labelImageFiles.clear();
    
    if (album != null) {
      for (String imageFileName: album.getImagesFront()) {
        String imagePathName = MediaRegistry.albumInfoDirectory + File.separator + imageFileName;
        frontImageFiles.add(new File(imagePathName));
      }
      
      for (String imageFileName: album.getImagesFrontInside()) {
        String imagePathName = MediaRegistry.albumInfoDirectory + File.separator + imageFileName;
        frontInsideImageFiles.add(new File(imagePathName));
      }
      
      for (String imageFileName: album.getImagesBack()) {
        String imagePathName = MediaRegistry.albumInfoDirectory + File.separator + imageFileName;
        backImageFiles.add(new File(imagePathName));
      }
      
      for (String imageFileName: album.getImagesLabel()) {
        String imagePathName = MediaRegistry.albumInfoDirectory + File.separator + imageFileName;
        labelImageFiles.add(new File(imagePathName));
      }
    }
  }
  
  /**
   * Update the discs.
   */
  private void updateDiscs(Album album) {
    discCopies.clear();

    if (album != null) {
      for (Disc disc: album.getDiscs()) {
        DiscCopy discCopy = new DiscCopy(disc);
        discCopies.add(discCopy);
      }
    }
  }
  
  /**
   * Update the general information of the <code>album</code>.
   */
  private void updateGeneralInformation(Album album) {
    issuedOnMediaLabels.clear();

    if (album != null) {
      albumTitleTextField.setObjectValue(album.getTitle());
      
      albumArtistObjectControl.setObjectValue(album.getArtist());
      
      albumIssueDateTextField.setObjectValue(album.getReleaseDate());
      
      albumIdTextField.setObjectValue(album.getId());
      
      descriptionTitleTextField.setObjectValue(album.getDescriptionTitle());
      
      descriptionTextArea.setObjectValue(album.getDescription());
      
      for (MediumType mediumType: album.getIssuedOnMediums()) {
        Label label = componentFactory.createLabel(mediumType.getLiteral());
        issuedOnMediaLabels.add(label);
      }
      
      if (album.isSetMyInfo()) {
        MyInfo myInfo = album.getMyInfo();
        
        if (myInfo.isSetIWant()) {
          iWantComboBox.setValue(IWantWrapper.forIWant(myInfo.getIWant()));
        }
        
        myCommentsTextArea.setObjectValue(myInfo.getMyComments());
        
//        if (myInfo.isSetCollection()) {
//          collectionComboBox.setValue(CollectionWrapper.forCollection(myInfo.getCollection()));
//        }
      }
      
    } else {
      albumTitleTextField.setObjectValue(null);
      albumArtistObjectControl.setObjectValue(null);
      albumIssueDateTextField.setObjectValue(null);
      albumIdTextField.setObjectValue(null);
      descriptionTitleTextField.setObjectValue(null);
      descriptionTextArea.setObjectValue(null);
      myCommentsTextArea.setObjectValue(null);
    }
  }
  
  private void updateCollaboratingArtists(Album album) {
    playersGridPane.getChildren().clear();
    int row = 0;
    playersGridPane.add(componentFactory.createLabel("Artist"), 0, row);
    playersGridPane.add(componentFactory.createLabel("Instruments"), 1, row);
    

    row++;
    for (Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> tuplet: players) {
      AutoCompleteTextFieldObjectInput<Artist> playerNameTextField = tuplet.getObject1();
      playersGridPane.add(playerNameTextField, 0, row);

      TextField playerInstrumentTextField = tuplet.getObject2();
      playersGridPane.add(playerInstrumentTextField, 1, row);

      Button deletePlayerButton = componentFactory.createButton("Remove", "Delete this player from the album");
      deletePlayerButton.setOnAction((e) -> {
        players.remove(tuplet);
        updateCollaboratingArtists(album);
      });
      playersGridPane.add(deletePlayerButton, 2, row);

      row++;
    }
    
    Button addPlayerButton = componentFactory.createButton("Add player", "Add a player to the collaborators of the album");
    addPlayerButton.setOnAction((e) -> {
      Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> tuplet = createPlayerTuplet(null, null);
      players.add(tuplet);
      updateCollaboratingArtists(album);
    });
    playersGridPane.add(addPlayerButton, 2, row);

//    ComboBox<Artist> artistsComboBox = componentFactory.createComboBox(null);
//    artistsComboBox.setMaxWidth(300.0);
//    ObservableList<Artist> artists = FXCollections.observableArrayList(mediaDb.getArtists());
//    artists.sort((Artist artist1, Artist artist2) -> {return artist1.getName().compareTo(artist2.getName());});
//    artistsComboBox.setItems(artists);
//    playersGridPane.add(artistsComboBox, 0, row);
//
//    TextField playerInstrumentTextField = componentFactory.createTextField(300, "A comma separated list of instruments");
//    playersGridPane.add(playerInstrumentTextField, 1, row);
//    
//    Button addPlayerButton = componentFactory.createButton("Add player", "Add this player to the collaborators of the album");
//    addPlayerButton.setOnAction((e) -> {
//      TextField newPlayerNameTextField = componentFactory.createTextField(300, null);
//      newPlayerNameTextField.setText(artistsComboBox.getValue().getName());
//      newPlayerNameTextField.setEditable(false);
//
//      TextField newPlayerInstrumentTextField = componentFactory.createTextField(300, "A comma separated list of instruments");
//      newPlayerInstrumentTextField.setText(playerInstrumentTextField.getText());
//
//      Tuplet<TextField, TextField> tuplet = new Tuplet<>(newPlayerNameTextField, newPlayerInstrumentTextField);
//      players.add(tuplet);
//      updateCollaboratingArtists(album);
//    });
//    playersGridPane.add(addPlayerButton, 2, row);
    
  }
  
  private void updateIssuedOnMedia(Album album) {
    issuedOnMediaPane.getChildren().clear();
    
    issuedOnMediaPane.getChildren().addAll(issuedOnMediaLabels);
    
    ComboBox<MediumTypeWrapper> mediaComboBox = componentFactory.createComboBox(MediumTypeWrapper.values());
    issuedOnMediaPane.getChildren().add(mediaComboBox);
    
    Button addMediumTypeButton = componentFactory.createButton("Add medium", "Add this medium type");
    addMediumTypeButton.setOnAction((e) -> {
      MediumTypeWrapper selectedMediumTypeWrapper = mediaComboBox.getValue();
      if (selectedMediumTypeWrapper != null) {
        Label label = componentFactory.createLabel(selectedMediumTypeWrapper.toString());
        issuedOnMediaLabels.add(label);
        updateIssuedOnMedia(album);
      }
    });
    issuedOnMediaPane.getChildren().add(addMediumTypeButton);
  }
  
  private void updateImagesPanel(HBox imagesBox, List<File> imageFiles) {
    LOGGER.info("=>");
    
    imagesBox.getChildren().clear();
    
    for (File file: imageFiles) {
      Image image = new Image("file:" + file.getAbsolutePath(), 0.0, 200.0, true, true);
      ImageView imageView = new ImageView(image);
      imageView.setOnMouseEntered(e -> showLargePicture("file:" + file.getAbsolutePath()));
      imageView.setOnMouseExited(e -> {
        if (currentLargePictureStage != null) {
          currentLargePictureStage.close();
        }
      });
      imagesBox.getChildren().add(imageView);
    }
    
    LOGGER.info("<=");
  }
    
  /**
   * Update the checkboxes.
   */
  private void updateCheckBoxes(Album album) {
    if (album != null) {      
      isCompilationAlbumCheckBox.setSelected(album.isCompilation());
      isOwnCompilationCheckBox.setSelected(album instanceof MyCompilation);
      isSoundTrackCheckBox.setSelected(album.isSoundtrack());
      iveHadOnLpCheckBox.setSelected(album.getMyInfo().isIveHadOnLP());
    } else {
      isCompilationAlbumCheckBox.setSelected(false);
      isOwnCompilationCheckBox.setSelected(false);
      isSoundTrackCheckBox.setSelected(false);
      iveHadOnLpCheckBox.setSelected(false);
    }
  }
  
  /**
   * Update the discs panel (<code>discTracksVBox</code>).
   * <p>
   * The panel is cleared and then for each disc of the <code>Album</code> a {@link DiscTracksTable} is added to the panel.
   */
  private void updateDiscsPanel(Album album) {
    discsVBox.getChildren().clear();

    for (Disc disc: discCopies) {
      DiscTracksTable discTracksTable = new DiscTracksTable(customization, disc.getTrackReferences(), trackDiscLocationMap);
      discsVBox.getChildren().add(discTracksTable);
    }
  }
  
  /**
   * Show a picture, full size, in a separate <code>Stage</code> (without any header or border).
   * 
   * @param fileName file name of the picture to be shown.
   */
  private void showLargePicture(String fileName) {
    Image image = new Image(fileName);
    ImageView currentLargePicture = new ImageView(image);
    
    currentLargePictureStage = new Stage();
    currentLargePictureStage.initStyle(StageStyle.UNDECORATED);
    BorderPane pane = new BorderPane();
    pane.setCenter(currentLargePicture);
    currentLargePictureStage.setScene(new Scene(pane));
    currentLargePictureStage.show();
  }
  
  private Node createImportControlsPanel() {
    HBox outerHBox = componentFactory.createHBox(10.0, 12.0);
    HBox hBox = componentFactory.createHBox(10.0, 12.0);
    hBox.setBorder(componentFactory.getRectangularBorder());
   
    // Import folder selection: Label, textField, Chooser button, import button
    FolderSelecter folderSelecter = componentFactory.createFolderSelecter("D:\\SoulSeek\\complete", 600, "Folder to import album information from", "Select source folder", "Open source folder chooser", "Source folder");

    Label label = componentFactory.createLabel("Derive album details from:");
    hBox.getChildren().add(label);
    
    hBox.getChildren().add(folderSelecter.getFolderPathTextField());
    hBox.getChildren().add(folderSelecter.getFolderChooserButton());
    
    Button button = componentFactory.createButton("Derive album details", "Derive the album details from this folder");
    button.setOnAction(e -> deriveAlbumDetails(folderSelecter.getFolderPathTextField().getText()));
    hBox.getChildren().add(button);
    
    outerHBox.getChildren().add(hBox);
        
    return outerHBox;
  }
  
  /**
   * Create a panel with 'Add album' and a 'Cancel' buttons.
   * <p>
   * The action for the 'Add album' button is {@link #addAlbum()}.
   * The action for the 'Cancel' button is {@link #closeWindow()}.
   * 
   * @return  a panel with 'Add album' and a 'Cancel' buttons. 
   */
  private Node createAddOrCancelPanel() {
    HBox hBox = componentFactory.createHBox(10.0, 12.0);
   
    // Two buttons: "Add album", "Cancel".
    Button button;
    
    button = componentFactory.createButton("Add album", "Add the album to the media database");
    button.setOnAction(e -> addAlbum());
    hBox.getChildren().add(button);
    
    button = componentFactory.createButton("Cancel", "Exit window without saving");
    button.setOnAction(e -> closeWindow());
    hBox.getChildren().add(button);
    
    return hBox;
  }
  
  /**
   * Create a panel with 'Update album' and a 'Cancel' buttons.
   * <p>
   * The action for the 'Update album' button is {@link #updateAlbum()}.
   * The action for the 'Cancel' button is {@link #closeWindow()}.
   * 
   * @return a panel with 'Update album' and a 'Cancel' buttons.
   */
  private Node createUpdateOrCancelPanel() {
    HBox hBox = componentFactory.createHBox(10.0, 12.0);
   
    // Two buttons: "Update album", "Cancel".
    Button button;
    
    button = componentFactory.createButton("Update album", "Update the album in the media database");
    button.setOnAction(e -> updateAlbum());
    hBox.getChildren().add(button);
    
    button = componentFactory.createButton("Cancel", "Exit window without saving");
    button.setOnAction(e -> closeWindow());
    hBox.getChildren().add(button);
    
    return hBox;
  }
  
  /**
   * Derive the information of an album from a folder (containing the tracks of an album).
   * 
   * @param sourceFolderName the folder from which the information is to be derived.
   */
  private void deriveAlbumDetails(String sourceFolderName) {
    LOGGER.severe("=> sourceFolderName" + sourceFolderName);
    
    // Create the information in an Album structure, and then fill the control with this information. Don't set 'album'.
    Album album = MEDIA_DB_FACTORY.createAlbum();
    Disc disc = MEDIA_DB_FACTORY.createDisc();
    album.getDiscs().add(disc);
    
    Path sourceFolder = Paths.get(sourceFolderName);

    // Try to find picture files.
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceFolder)) {
      for (Path path: stream) {
        String fileName = path.getFileName().toString();
        LOGGER.severe("Handling path: " + fileName);
        if (!Files.isDirectory(path)) {
          String[] fileNameAndExtension = FileUtils.getFileNameAndExtension(path);
          LOGGER.severe("filename: " + fileNameAndExtension[0] + ", extension: " + fileNameAndExtension[1]);
          
          switch (fileNameAndExtension[1]) {
          case "jpg":
            if (fileNameAndExtension[0].equalsIgnoreCase("front")) {
              album.getImagesFront().add(path.toAbsolutePath().toString());
            }
            if (fileNameAndExtension[0].equalsIgnoreCase("back")) {
              album.getImagesBack().add(path.toAbsolutePath().toString());
            }
            if (fileNameAndExtension[0].equalsIgnoreCase("inside")) {
              album.getImagesFrontInside().add(path.toAbsolutePath().toString());
            }
            break;
            
          default:
            break;
          }
          
        } else {
          LOGGER.severe("Skipping folder: " + fileName);
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      System.err.println(x);
    }
    
    // read all MP3 and FLAC files in the folder
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceFolder)) {
      List<Path> flacFiles = null;
      
      for (Path path: stream) {
        String fileName = path.getFileName().toString();
        LOGGER.severe("Handling path: " + fileName);
        if (!Files.isDirectory(path)) {
          String extension = FileUtils.getFileExtension(path);
          LOGGER.severe("extension: " + extension);
          
          switch (extension) {
          case ".mp3":
            deriveInfoFromMp3File(album, path);
            break;
            
          case ".flac":
            if (flacFiles == null) {
              flacFiles = new ArrayList<>();
            }
            flacFiles.add(path);
            break;
            
          default:
            break;
          }
          
        } else {
          LOGGER.severe("Skipping folder: " + fileName);
        }
      }
      
      if (flacFiles != null) {
        deriveInfoFromFlacFiles(album, flacFiles);
      }
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      System.err.println(x);
    }
    
    if (!album.getImagesFront().isEmpty()  ||  !album.getImagesFrontInside().isEmpty()  ||
        !album.getImagesBack().isEmpty()  ||  !album.getImagesLabel().isEmpty()) {
      ImportAlbumPicturesWindow importAlbumPicturesWindow = new ImportAlbumPicturesWindow(getCustomization(), album);
      importAlbumPicturesWindow.showAndWait();
    }
    
    Album existingAlbum = mediaDb.getAlbum(album.getReleaseDate(), album.getArtist(), album.getTitle());
    if (existingAlbum != null) {
      LOGGER.severe("Album already exists in the media database");
    }
    
    updateControlsForAlbum(album);
  }
  
  /**
   * Derive ...
   * 
   * @param album
   * @param flacFilePaths
   */
  private void deriveInfoFromFlacFiles(Album album, List<Path> flacFilePaths) {
    LOGGER.severe("=>");
    
    Disc disc = album.getDiscs().get(0);
    
    /*
     *  Get the information from the tracks.
     */
    List<Tag> tags = new ArrayList<>();
    for (Path flacFilePath: flacFilePaths) {
      File inputFile = new File(flacFilePath.toString());
      try {
        AudioFile audioFile = AudioFileIO.read(inputFile);
        Tag tag = audioFile.getTag().or(NullTag.INSTANCE);
        tags.add(tag);
        
        // Debugging only. Print all FieldKey values, except the ones which aren't supported by the library.
        for (FieldKey fieldKey: FieldKey.values()) {
          if (fieldKey == FieldKey.COVER_ART  ||
              fieldKey == FieldKey.ITUNES_GROUPING) {
            continue;
          }
          
          Optional<String> optionalTag = tag.getValue(fieldKey);
          if (optionalTag.isPresent()) {
            LOGGER.severe("fieldKey:" + fieldKey.name() + "=" + optionalTag.get());
          }
        }
      } catch (CannotReadException | IOException | TagException | InvalidAudioFrameException e) {
        e.printStackTrace();
      }
    }
    
    /*
     *  Sort the tracks. This is based on TRACK. If there's at least one track for which this isn't set, get it from the filename.
     */
    
    /*
     *  Determine the album artist name.
     *  This is the ALBUM_ARTIST. This shall be the same for all tracks., or, if this isn't available, the ARTIST (if this is the same for all tracks).
     *  If the ALBUM_ARTIST isn't available, and the ARTIST is the same for all tracks, then this is the album artist name.
     */
    String albumArtistName = null;
    for (Tag tag: tags) {
      Optional<String> optionalTag = tag.getValue(FieldKey.ALBUM_ARTIST);
      if (optionalTag.isPresent()) {
        String currentAlbumArtistName = optionalTag.get();
        if (albumArtistName == null) {
          albumArtistName = currentAlbumArtistName;
        } else {
          if (!albumArtistName.equals(currentAlbumArtistName)) {
            LOGGER.severe("Different album artist names detected.");
          }
        }
      }
    }
    
    if (albumArtistName == null) {
      for (Tag tag: tags) {
        Optional<String> optionalTag = tag.getValue(FieldKey.ARTIST);
        if (optionalTag.isPresent()) {
          String currentArtistName = optionalTag.get();
          if (albumArtistName == null) {
            albumArtistName = currentArtistName;
          } else {
            if (!albumArtistName.equals(currentArtistName)) {
              LOGGER.severe("Different artist names detected.");
            }
          }
        }
      }
    }
    
    /*
     *  If the artist isn't known in de database, ask the user whether it should be added.
     */
    Artist artist = null;
    if (albumArtistName != null) {
      artist = mediaDb.getArtist(albumArtistName);
      if (artist == null) {
        ArtistDetailsEditor artistDetailsEditor = new ArtistDetailsEditor(getCustomization(), "New artist", mediaDb, "The artist doesn't exist in the database yet. Do you want to add it?", albumArtistName);
//        artistDetailsEditor.initModality(Modality.APPLICATION_MODAL);
        artistDetailsEditor.showAndWait();
        // retry to get the artist, should succeed if it has been added.
        artist = mediaDb.getArtist(albumArtistName);
      }
      if (artist != null) {
        album.setArtist(artist);
      }
    }
    
    // Determine the album title. This is the ALBUM. This shall be the same for all tracks.
    for (Tag tag: tags) {
      Optional<String> optionalTag = tag.getValue(FieldKey.ALBUM);
      if (optionalTag.isPresent()) {
        String albumTitle = optionalTag.get();
        if (!album.isSetTitle()) {
          album.setTitle(albumTitle);
        } else {
          if (!album.getTitle().equals(albumTitle)) {
            LOGGER.severe("Different album titles detected.");
          }
        }
      }
    }
    
    // Determine the year of issue. This is the YEAR. This shall be the same for all tracks.
    Integer issueYear = null;
    for (Tag tag: tags) {
      Optional<String> optionalTag = tag.getValue(FieldKey.YEAR);
      if (optionalTag.isPresent()) {
        try {
          Integer currentIssueYear = Integer.valueOf(optionalTag.get());
          if (issueYear == null) {
            issueYear = currentIssueYear;
          } else {
            if (!issueYear.equals(currentIssueYear)) {
              LOGGER.severe("Different issue years detected.");
            }
          }
        } catch (NumberFormatException e) {
          // no action
        }
      }
    }
    if (issueYear != null) {
      album.setReleaseDate(new FlexDate(issueYear));
    }
    
    // Determine the track title. This is the TITLE. Create and add track with this title
    for (Tag tag: tags) {
//      Track track = MEDIA_DB_FACTORY.createTrack();
      
//      if (artist != null) {
//        track.setArtist(artist);
//      }
      
      String trackTitle = null;
      Optional<String> optionalTag = tag.getValue(FieldKey.TITLE);
      if (optionalTag.isPresent()) {
        trackTitle = optionalTag.get();
      }
      
//      track.setOriginalAlbum(album);
      
//      mediaDb.getTracks().add(track);
      Track track = MediaDbUtil.getOrAddTrack(mediaDb, artist, trackTitle, disc);
      
      TrackReference trackReference = MEDIA_DB_FACTORY.createTrackReference();
      trackReference.setTrack(track);
      
      disc.getTrackReferences().add(trackReference);
    }
      
    LOGGER.severe("<=");
  }
  
  /**
   * Derive track and album information from an .mp3 file.
   * 
   * @param album The {@link Album} to add the derived information to. This album shall already have 1 disc.
   * @param mp3FilePath the <code>Path</code> of the .mp3 file.
   */
  private void deriveInfoFromMp3File(Album album, Path mp3FilePath) {
    LOGGER.severe("=>");

    Mp3File mp3file;
    Disc disc = album.getDiscs().get(0);

    try {
      mp3file = new Mp3File(mp3FilePath);
      Track track = MEDIA_DB_FACTORY.createTrack();
      long lengthInSeconds = mp3file.getLengthInSeconds();
      LOGGER.severe("Length of this mp3 is: " + lengthInSeconds + " seconds");
      track.setDuration((int) lengthInSeconds);
      //      System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
      //      System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
      //      System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
      //      System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
      //      System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));

      if (mp3file.hasId3v1Tag()) {
        ID3v1 id3v1Tag = mp3file.getId3v1Tag();

        System.out.println("Track: " + id3v1Tag.getTrack());
        System.out.println("Artist: " + id3v1Tag.getArtist());
        String artistName = id3v1Tag.getArtist();
        Artist artist = mediaDb.getArtist(artistName);
        if (artist == null) {
          ArtistDetailsEditor artistDetailsEditor = new ArtistDetailsEditor(getCustomization(), "New artist", mediaDb, "The artist doesn't exist in the database yet. Do you want to add it?", artistName);
          artistDetailsEditor.initModality(Modality.APPLICATION_MODAL);
          artistDetailsEditor.showAndWait();
          // retry to get the artist, should succeed if it has been added.
          artist = mediaDb.getArtist(id3v1Tag.getArtist());
        }
        if (artist != null) {
          album.setArtist(artist);
        }
        System.out.println("Title: " + id3v1Tag.getTitle());
        track.setTitle(id3v1Tag.getTitle());
        System.out.println("Album: " + id3v1Tag.getAlbum());
        album.setTitle(id3v1Tag.getAlbum());
        track = MediaDbUtil.getOrAddTrack(mediaDb, artist, id3v1Tag.getTitle(), disc);
        System.out.println("Year: " + id3v1Tag.getYear());
        Integer year = Integer.valueOf(id3v1Tag.getYear());
        FlexDate releaseDate = new FlexDate(year);
        album.setReleaseDate(releaseDate);
        System.out.println("Genre: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
        System.out.println("Comment: " + id3v1Tag.getComment());
        System.out.println("Version: " + id3v1Tag.getVersion());
        System.out.println("Year: " + id3v1Tag.getYear());
        mediaDb.getTracks().add(track);
        TrackReference trackReference = MEDIA_DB_FACTORY.createTrackReference();
        trackReference.setTrack(track);
        disc.getTrackReferences().add(trackReference);
      }
    } catch (UnsupportedTagException | InvalidDataException | IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Create an Album based on the provided information and add it to the media database.
   * <p>
   * If the provided information is correct, the Album is created and added to the media database.
   * Otherwise, an Alert is shown with information about what is wrong.
   */
  protected void addAlbum() {
    try {
    Album album = createAlbumFromControls();
    mediaDb.getAlbums().add(album);
    } catch (AlbumDetailsException e) {
      StringBuilder buf = new StringBuilder();
      for (String problem: e.getProblems()) {
        buf.append(problem);
        buf.append(NEW_LINE);
      }
      componentFactory.createErrorDialog("Problem in album details", buf.toString()).showAndWait();
    }
  }
  
  /**
   * Update the current Album based on the provided information.
   * <p>
   * If the provided information is correct, the Album is updated.
   * Otherwise, an Alert is shown with information about what is wrong.
   */
  protected void updateAlbum() {
    try {
      fillAlbumFromControls(album);
    } catch (AlbumDetailsException e) {
      StringBuilder buf = new StringBuilder();
      for (String problem: e.getProblems()) {
        buf.append(problem);
        buf.append(NEW_LINE);
      }
      componentFactory.createErrorDialog("Problem in album details", buf.toString()).showAndWait();
    }
  }
  
  /**
   * Create an Album based on the provided information.
   * <p>
   * If the provided information isn't correct, an {@link AlbumDetailsException} is throw with a list error messages.
   * 
   * @return the new Album
   * @throws AlbumtDetailsException if the provided information isn't correct.
   */
  private Album createAlbumFromControls() throws AlbumDetailsException {
    Album album;
    
    if (isOwnCompilationCheckBox.isSelected()) {
      album = MEDIA_DB_FACTORY.createMyCompilation();
    } else {
      album = MEDIA_DB_FACTORY.createAlbum();
    }
    
    fillAlbumFromControls(album);
    
    return album;
  }
  
  /**
   * Update an album from the information in the controls.
   * 
   * @param album the Album to be updated
   * @throws AlbumDetailsException in case one or more controls has an illegal value.
   */
  private void fillAlbumFromControls(Album album) throws AlbumDetailsException {
    
    List<String> problems = new ArrayList<>();
    
    // Album title
    String titleText = albumTitleTextField.getObjectValue();
    if (titleText == null) {
      problems.add("Album title may not be empty");
    }
    EmfUtil.setFeatureValue(album, MEDIA_DB_PACKAGE.getAlbum_Title(), titleText);
    
    // Artist
    Artist artist = albumArtistObjectControl.getObjectValue();
    if (artist == null) {
      problems.add("There is no artist selected");
    }
    EmfUtil.setFeatureValue(album, MEDIA_DB_PACKAGE.getAlbum_Artist(), artist);

    // Issue date
    FlexDate releaseDate = albumIssueDateTextField.getObjectValue();
    EmfUtil.setFeatureValue(album, MEDIA_DB_PACKAGE.getAlbum_ReleaseDate(), releaseDate);
    
    // Album Id
    String albumIdText = albumIdTextField.getText();
    EmfUtil.setFeatureValue(album, MEDIA_DB_PACKAGE.getAlbum_Id(), albumIdText);
    
    // Players
    // Update the complete list of players if there is any change.
    boolean playersChanged = false;
    if (players.size() != album.getPlayers().size()) {
      playersChanged = true;
    }
    
    if (!playersChanged) {
      for (int i = 0; i < players.size(); i++) {
        Player currentPlayer = album.getPlayers().get(i);
        Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> tuplet = players.get(i);
        Artist newArtist = tuplet.getObject1().getObjectValue();
        List<String> newInstruments = StringUtil.commaSeparatedValuesToListOfValues(tuplet.getObject2().getText());
        if (!newArtist.equals(currentPlayer.getArtist())  ||
            !newInstruments.equals(currentPlayer.getInstruments())) {
          playersChanged = true;
          break;
        }
      }
    }
    
    if (playersChanged) {
      album.getPlayers().clear();
      for (Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> tuplet: players) {
        Player player = MEDIA_DB_FACTORY.createPlayer();
        AutoCompleteTextFieldObjectInput<Artist> artistControl = tuplet.getObject1();
        player.setArtist(artistControl.getObjectValue());

        TextField playerInstrumentsTextField = tuplet.getObject2();
        player.getInstruments().addAll(StringUtil.commaSeparatedValuesToListOfValues(playerInstrumentsTextField.getText()));

        album.getPlayers().add(player);
      }
    }
    
    // Images front
    setImagesIfChanged(album.getImagesFront(), frontImageFiles);
    
    // Images front inside
    setImagesIfChanged(album.getImagesFrontInside(), frontInsideImageFiles);
    
    // Images back
    setImagesIfChanged(album.getImagesBack(), backImageFiles);
    
    // Images label
    setImagesIfChanged(album.getImagesLabel(), labelImageFiles);
    
    // Issued on
    setIssuedOnMediumsIfChanged(album.getIssuedOnMediums(), issuedOnMediaLabels);

    // Description title
    String descriptionTitleText = descriptionTitleTextField.getObjectValue();
    EmfUtil.setFeatureValue(album, MEDIA_DB_PACKAGE.getAlbum_DescriptionTitle(), descriptionTitleText);

    // Description
    String descriptionText = descriptionTextArea.getObjectValue();
    EmfUtil.setFeatureValue(album, MEDIA_DB_PACKAGE.getAlbum_Description(), descriptionText);

    /*
     * MyInfo
     */
    MyInfo albumMyInfo = album.getMyInfo();
    MyInfo myInfoToFill = null;
    boolean myInfoNeeded = isMyInfoNeeded();
    if (myInfoNeeded) {
      if (albumMyInfo == null) {
        myInfoToFill = MEDIA_DB_FACTORY.createMyInfo();
      } else {
        myInfoToFill = albumMyInfo;
      }
    } else {
      if (albumMyInfo != null) {
        EmfUtil.setFeatureValue(albumMyInfo, MEDIA_DB_PACKAGE.getMyInfo_IWant(), IWant.NOT_SET);
        EmfUtil.setFeatureValue(albumMyInfo, MEDIA_DB_PACKAGE.getMyInfo_IveHadOnLP(), false);
        EmfUtil.setFeatureValue(albumMyInfo, MEDIA_DB_PACKAGE.getMyInfo_MyComments(), null);
//        album.setMyInfo(null); TODO in future remove MyInfo if nothing set
      }
    }

    if (myInfoNeeded) {
      IWantWrapper iWantWrapper = iWantComboBox.getValue();
      if (iWantWrapper != null) {
        myInfoToFill.setIWant(iWantWrapper.getIWant());
      }
      
      myInfoToFill.setIveHadOnLP(iveHadOnLpCheckBox.isSelected());
      
      String myComments = myCommentsTextArea.getText();
      EmfUtil.setFeatureValue(myInfoToFill, MEDIA_DB_PACKAGE.getMyInfo_MyComments(), myComments);
      
      if (albumMyInfo == null) {
        album.setMyInfo(myInfoToFill);
      }
    }
    
    for (DiscCopy discCopy: discCopies) {  // TODO handle discs added, removed and reordered.
      discCopy.updateSourceDisc();
//      updateDiscFromControls(albumDisc, disc);
    }
    
    if (problems.size() != 0) {
      throw new AlbumDetailsException(problems);
    }
  }
  
  
  
//  private void updateDiscFromControls(Disc albumDisc, Disc disc) {
//    EmfUtil.setFeatureValue(albumDisc, MEDIA_DB_PACKAGE.getDisc_Title(), disc.getTitle());
//    
//    // If there's any change in the track references, set all new references.
//    boolean trackReferencesChanged = false;
//    List<TrackReference> albumDiscTrackReferences = albumDisc.getTrackReferences();
//    List<TrackReference> discTrackReferences = disc.getTrackReferences();
//    if (albumDiscTrackReferences.size() != discTrackReferences.size()) {
//      trackReferencesChanged = true;
//    }
//    
//    if (!trackReferencesChanged) {
//      for (int i = 0; i < discTrackReferences.size(); i++) {
//        TrackReference albumTrackReference = albumDiscTrackReferences.get(i);
//        TrackReference trackReference = discTrackReferences.get(i);
//        if (!trackReference.equals(albumTrackReference)) {
//            LOGGER.severe("Track references differ: albumTrackReference=" + albumTrackReference + ", trackReference" + trackReference);
//            trackReferencesChanged = true;
//            break;
//        }
//      }
//    }
//    
//    for (TrackReference trackReference: albumDiscTrackReferences) {
//      printCrossReferences(trackReference);
//    }
//    
//    for (TrackReference trackReference: discTrackReferences) {
//      for (TrackReference discTrackReference: albumDiscTrackReferences) {
//      }
//    }
//    
//    if (trackReferencesChanged) {
//      albumDiscTrackReferences.clear();
//      albumDiscTrackReferences.addAll(discTrackReferences);
//    }
//    
//  }
  
  private void printCrossReferences(EObject eObject) {
    
    ResourceSet resourceSet = null;
    Resource resource = eObject.eResource();
    if (resource != null) {
      resourceSet = resource.getResourceSet();
    }
    
    Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(eObject, resourceSet);
    if (settings.size() != 0) {
      StringBuffer buf = new StringBuffer();
      buf.append("There are ");
      buf.append(settings.size());
      buf.append(" references to this object.");
      buf.append(NEW_LINE);

      for (Object object: settings) {
        EStructuralFeature.Setting setting = (EStructuralFeature.Setting) object;
        EStructuralFeature feature = setting.getEStructuralFeature();
        String inOrAs = " as ";
        if (feature.isMany()) {
          inOrAs = " in ";
        }
        EObject referringObject = setting.getEObject();
        EObject container = referringObject.eContainer();
        buf.append(container.getClass().getName());
        buf.append(" in ");
        buf.append(referringObject.toString());
        buf.append(inOrAs);
        buf.append(setting.getEStructuralFeature().getName());
        buf.append(NEW_LINE);
      }
      LOGGER.severe(buf.toString());
    }
    
  }

  /**
   * Check whether a MyInfo object is needed.
   * <p>
   * The MyInfo is needed if any of the related controls is filled in.
   */
  private boolean isMyInfoNeeded() {
    if (myCommentsTextArea.getObjectValue() != null) {  // TODO add other controls
      return true;
    }
    
    return false;
  }
  
  private void setIssuedOnMediumsIfChanged(EList<MediumType> issuedOnMediums, List<Label> issuedOnMediaLabels) {
    // Update the complete list if there is any change.
    boolean issuedOnMediumsChanged = false;
    
    if (issuedOnMediaLabels.size() != issuedOnMediums.size()) {
      issuedOnMediumsChanged = true;
    }
    
    if (!issuedOnMediumsChanged) {
      for (int i = 0; i < issuedOnMediaLabels.size(); i++) {
        Label label = issuedOnMediaLabels.get(i);
        MediumType newMediumType = MediumType.get(label.getText());
        LOGGER.severe("newMediumType: " + newMediumType);
        MediumType currentMediumType = issuedOnMediums.get(i);
        LOGGER.severe("currentMediumType: " + currentMediumType);
        if (!newMediumType.equals(currentMediumType)) {
          issuedOnMediumsChanged = true;
          break;
        }
      }
    }
    
    if (issuedOnMediumsChanged) {
      issuedOnMediums.clear();
      for (Label label: issuedOnMediaLabels) {
        MediumType newMediumType = MediumType.get(label.getText());
        issuedOnMediums.add(newMediumType);
      }
    }
  }

  /**
   * Set the list of image files (front, inside, back, label) if there is any change.
   * 
   * @param currentImages the list of image files.
   * @param newImageFiles the new values for the image files.
   */
  private void setImagesIfChanged(List<String> currentImages, List<File> newImageFiles) {
    // Update the complete list of images if there is any change.
    boolean imagesChanged = false;
    if (newImageFiles.size() != currentImages.size()) {
      imagesChanged = true;
    }
    
    if (!imagesChanged) {
      for (int i = 0; i < newImageFiles.size(); i++) {
        File newImageFile = newImageFiles.get(i);
        LOGGER.severe("newImageFile: " + newImageFile);
        String newImageFileName = FileUtils.getPathRelativeToFolder(MediaRegistry.albumInfoDirectory, newImageFile.getAbsolutePath());
        String currentImageFilename = currentImages.get(i);
        if (!newImageFileName.equals(currentImageFilename)) {
          imagesChanged = true;
          break;
        }
      }
    }
    
    if (imagesChanged) {
      currentImages.clear();
      for (File file: newImageFiles) {
        String imageFileName = FileUtils.getPathRelativeToFolder(MediaRegistry.albumInfoDirectory, file.getAbsolutePath());
        currentImages.add(imageFileName);
      }
    }
    
  }

  private void closeWindow() {
    System.out.println("AlbumDetailsEditor: closeWindow");
    this.close();
  }
  
  /**
   * Check whether any value of the controls differs from the album value.
   * 
   * @return true if any value of the controls differs from the album value, false otherwise.
   */
  private boolean dirty() {
    return true; // ToDo implementation needed.
  }
  
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    if (dirty()) {
      buf.append("*");
    }
    
    if (editMode == EditMode.NEW) {
      buf.append("New album");
    } else {
      buf.append("Edit album");
    }
    
    setTitle(buf.toString());
  }
  
  private void updateEditMode(Album album) {
    if (album != null) {
      editMode = EditMode.EDIT;
      mainPane.setTop(null);
      mainPane.setBottom(updateOrCancelPanel);
    } else {
      editMode = EditMode.NEW;
      mainPane.setTop(importControlsPanel);
      mainPane.setBottom(addOrCancelPanel);
    }
    updateTitle();
  }
}

/**
 * An exception with a list messages about what is wrong in the provided album information.
 */
@SuppressWarnings("serial")
class AlbumDetailsException extends Exception {
  private List<String> problems;
  
  AlbumDetailsException(List<String> problems) {
    this.problems = problems;
  }
  
  List<String> getProblems() {
    return problems;
  }
}

enum EditMode {
  /**
   * Creating a new album.
   */
  NEW,
  
  /**
   * Editing an existing album.
   */
  EDIT
}


class DiscCopy extends DiscImpl {
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
  private Disc sourceDisc;
  
  DiscCopy(Disc disc) {
    sourceDisc = disc;
    
    String title = disc.getTitle();
    if (title != null) {
      setTitle(new String(title));
    }
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getDisc_Title(), disc.getTitle());
    
    for (TrackReference trackReference: disc.getTrackReferences()) {
      getTrackReferences().add(new TrackReferenceCopy(trackReference));
    }    
  }

  public void updateSourceDisc() {
    EmfUtil.setFeatureValue(sourceDisc, MEDIA_DB_PACKAGE.getDisc_Title(), getTitle());
    
    for (TrackReference trackReference: trackReferences) {
      TrackReferenceCopy trackReferenceCopy = (TrackReferenceCopy) trackReference;
      trackReferenceCopy.updateSourceTrackReference();
    }
  }
  
}

class TrackReferenceCopy extends TrackReferenceImpl {
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
    
  private TrackReference sourceTrackReference;
  
  /**
   * Constructor
   * 
   * @param trackReference the TrackReference of which a copy is to be made.
   */
  TrackReferenceCopy(TrackReference trackReference) {
    sourceTrackReference = trackReference;
    
    String bonusTrack = trackReference.getBonusTrack();
    if (bonusTrack != null) {
      setBonusTrack(new String(bonusTrack));
    }
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getTrackReference_Track(), trackReference.getTrack());  // no copy needed as it cannot be changed.
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getTrackReference_OriginalAlbumTrackReference(), trackReference.getOriginalAlbumTrackReference());  // no copy needed as it cannot be changed.
    
    MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
    if (myTrackInfo != null) {
      setMyTrackInfo(new MyTrackInfoCopy(myTrackInfo));
    }
  }

  public void updateSourceTrackReference() {
    String bonusTrack = getBonusTrack();
    if (bonusTrack != null) {
      sourceTrackReference.setBonusTrack(new String(bonusTrack));  // Also make a copy here, as editing may continue and then be cancelled.
    }
    EmfUtil.setFeatureValue(sourceTrackReference, MEDIA_DB_PACKAGE.getTrackReference_Track(), getTrack());
    EmfUtil.setFeatureValue(sourceTrackReference, MEDIA_DB_PACKAGE.getTrackReference_OriginalAlbumTrackReference(), getOriginalAlbumTrackReference());
    
    MyTrackInfoCopy myTrackInfoCopy = (MyTrackInfoCopy) getMyTrackInfo();
    if (myTrackInfoCopy != null) {
      myTrackInfoCopy.updateSourceMyTrackInfo();
    }
  }
}

class MyTrackInfoCopy extends MyTrackInfoImpl {
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
    
  private MyTrackInfo sourceMyTrackInfo;
  
  MyTrackInfoCopy(MyTrackInfo myTrackInfo) {
    sourceMyTrackInfo = myTrackInfo;
    
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getMyTrackInfo_Collection(), myTrackInfo.getCollection());
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getMyTrackInfo_IWant(), myTrackInfo.getIWant());
    
    for (MediumInfo mediumInfo: myTrackInfo.getIHaveOn()) {
      getIHaveOn().add(new MediumInfoCopy(mediumInfo));
    }
    
    if (myTrackInfo.getCompilationTrackReference() != null) {
      setCompilationTrackReference(new TrackReferenceCopy(myTrackInfo.getCompilationTrackReference()));
    }
  }

  public void updateSourceMyTrackInfo() {
    EmfUtil.setFeatureValue(sourceMyTrackInfo, MEDIA_DB_PACKAGE.getMyTrackInfo_Collection(), getCollection());
    EmfUtil.setFeatureValue(sourceMyTrackInfo, MEDIA_DB_PACKAGE.getMyTrackInfo_IWant(), getIWant());
    
    for (MediumInfo mediumInfo: getIHaveOn()) {
      MediumInfoCopy mediumInfoCopy = (MediumInfoCopy) mediumInfo;
      mediumInfoCopy.updateSourceMediumInfo();
    }
    
    if (getCompilationTrackReference() != null) {
      TrackReferenceCopy compilationTrackReferenceCopy = (TrackReferenceCopy) getCompilationTrackReference();
      compilationTrackReferenceCopy.updateSourceTrackReference();
    }
  }
}

class MediumInfoCopy extends MediumInfoImpl {
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
  private MediumInfo sourceMediumInfo;
  
  MediumInfoCopy(MediumInfo mediumInfo) {
    sourceMediumInfo = mediumInfo;
    
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getMediumInfo_MediumType(), mediumInfo.getMediumType());
    EmfUtil.setFeatureValue(this, MEDIA_DB_PACKAGE.getMediumInfo_SourceBitRate(), mediumInfo.getSourceBitRate());
    
    getSourceTypes().addAll(mediumInfo.getSourceTypes());
  }

  public void updateSourceMediumInfo() {
    EmfUtil.setFeatureValue(sourceMediumInfo, MEDIA_DB_PACKAGE.getMediumInfo_MediumType(), getMediumType());
    EmfUtil.setFeatureValue(sourceMediumInfo, MEDIA_DB_PACKAGE.getMediumInfo_SourceBitRate(), getSourceBitRate());
    
    // Replace the source types if there's any change
    List<InformationType> sourceSourceTypes = sourceMediumInfo.getSourceTypes();
    List<InformationType> sourceTypes = getSourceTypes();
    boolean sourceTypesChanged = false;
    
    if (sourceSourceTypes.size() != sourceTypes.size()) {
      sourceTypesChanged = true;
    }
    
    if (!sourceTypesChanged) {
      for (int i = 0; i < sourceTypes.size(); i++) {
        InformationType sourceSourceType = sourceSourceTypes.get(i);
        InformationType sourceType = sourceTypes.get(i);
        if (!sourceSourceType.equals(sourceType)) {
          sourceTypesChanged = true;
          break;
        }
      }
    }
    
    if (sourceTypesChanged) {
      sourceSourceTypes.clear();
      sourceSourceTypes.addAll(sourceTypes);
    }
  }
}

