package goedegep.media.mediadb.albumeditor.guifx;

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

import org.eclipse.emf.common.notify.Adapter;
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
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.jfx.objectcontrols.ObjectControlEnumComboBox;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlImageFile;
import goedegep.jfx.objectcontrols.ObjectControlMultiLineString;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.jfx.objecteditor.EditMode;
import goedegep.jfx.objecteditor.ObjectEditorAbstract;
import goedegep.media.app.MediaRegistry;
import goedegep.media.mediadb.app.AlbumDetailsException;
import goedegep.media.mediadb.app.ArtistStringConverterAndChecker;
import goedegep.media.mediadb.app.guifx.ArtistDetailsEditor;
import goedegep.media.mediadb.app.guifx.ImportAlbumPicturesWindow;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyCompilation;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.util.PgUtilities;
import goedegep.util.Tuplet;
import goedegep.util.datetime.FlexDate;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import goedegep.util.string.StringUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlbumEditor extends ObjectEditorAbstract {
  
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
  
  private static final Logger LOGGER = Logger.getLogger(AlbumEditor.class.getName());
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
  private ObjectControlTextField<String> albumTitleTextFieldObjectControl;
  
  /**
   * Control for the album artist name.
   */
  private ObjectControlAutoCompleteTextField<Artist> albumArtistObjectControl;
  
  /**
   * Control for the album release date.
   * A <code>FlexDateFormat</code> is used to fill and parse this value.
   */
  private ObjectControlFlexDate albumIssueDateTextField;
  
  /**
   * Control for the album Id.
   * The album Id is plain text.
   */
  private ObjectControlTextField<String> albumIdTextField;
  
  /**
   * Control for the Description Title (which is plain text).
   */
  private ObjectControlTextField<String> descriptionTitleTextField;
  
  /**
   * Control for the Description (which is plain MarkDown text).
   */
  private ObjectControlMultiLineString descriptionTextArea;
  
  /**
   * Panel for the collaborating artists, a.k.a. the <code>players</code>
   */
  private GridPane playersGridPane;
  
  /**
   * List of players (collaborating artists).
   * These are shown in the <code>playersGridPane</code>
   */
  private List<PlayerObjectControl> playerObjectControls = null;
  
  /**
   * List of front images files.
   * These are shown in the <code>frontImagesHBox</code>.
   */
  private List<ObjectControlImageFile> frontImageControls = null;
  
  /**
   * Shows the album front pictures, which are stored in the <code>frontImageFiles</code>.
   */
  private HBox frontImagesHBox;
  
  /**
   * List of front inside images files.
   * These are shown in the <code>frontInsideImagesHBox</code>.
   */
  private List<ObjectControlImageFile> frontInsideImageControls = null;
  
  /**
   * Shows the album front inlay pictures, which are stored in the <code>frontInsideImageFiles</code>.
   */
  private HBox frontInsideImagesHBox;
  
  /**
   * List of back images files.
   * These are shown in the <code>backImagesHBox</code>.
   */
  private List<ObjectControlImageFile> backImageControls = null;
  
  /**
   * Shows the album back pictures, which are stored in the <code>backImageFiles</code>.
   */
  private HBox backImagesHBox;
  
  /**
   * List of label images files.
   * These are shown in the <code>labelImagesHBox</code>.
   */
  private List<ObjectControlImageFile> labelImageControls = null;
  
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
   * ComboBox to select an 'issued on' medium.
   */
  private ObjectControlEnumComboBox<MediumType> mediaComboBox;
  
  /**
   * Button to add an 'issued on' medium.
   */
  Button addMediumTypeButton;
  
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
  private ObjectControlEnumComboBox<IWant> iWantComboBox;
  
  /**
   * Shows My Comments
   */
  private ObjectControlMultiLineString myCommentsTextArea;
  
  /**
   * Shows myInfo albumReferences
   */
  private VBox albumReferencesVBox;
  
  /**
   * Album type
   */
//  EnumComboBox<AlbumType> albumTypeObjectControlEnumComboBox;
  ObjectControlEnumComboBox<AlbumType> albumTypeObjectControlEnumComboBox;

  /**
   * Shows the tracks per disc in tables
   */
  private VBox discsVBox;
  
  /**
   * List of DiscPanels
   */
  private List<DiscPanel> discPanels;
  
  private ObjectControlGroup controlsGroup;
  
  /**
   * Panel for the add/update and cancel buttons.
   */
  private HBox addUpdateAndCancelButtonsPanel;
  
  /**
   * Add or Update button
   */
  private Button addOrUpdateButton;
 
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
  public AlbumEditor(CustomizationFx customization, MediaDb mediaDb, Map<Track, Path> trackDiscLocationMap) {
    super(customization, null);
    
    this.customization = customization;
    this.mediaDb = mediaDb;
    this.trackDiscLocationMap = trackDiscLocationMap;
    
    componentFactory = customization.getComponentFactoryFx();
    
    artistStringConverterAndChecker = new ArtistStringConverterAndChecker(mediaDb);
    
    createControls();

    createGUI();
    
    updateEditMode();
    installChangeListernerForArtists();
    updateContainerArtistComboBox();
    controlsGroup.addListener((e) -> handleChanges());
    
    setAlbum(null);
  }

  /**
   * Constructor.
   * <p>
   * Use this constructor to edit an existing album.
   * 
   * @param customization the GUI customization.
   */
  public AlbumEditor(CustomizationFx customization, MediaDb mediaDb, Map<Track, Path> trackDiscLocationMap, Album album) {
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
    fillControlsFromAlbum(album);
    
    updateEditMode();
  }
  
  @Override
  protected void createControls() {
    albumTitleTextFieldObjectControl = componentFactory.createObjectControlTextField(null, null, 600, false, "The album title is a mandatory field");
    albumArtistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(artistStringConverterAndChecker, null, 300, false, "Enter the album artist");
    albumIssueDateTextField = componentFactory.createObjectControlFlexDate(null, 600, true, "Optional first issue date of the album");
    albumIdTextField = componentFactory.createObjectControlTextField(null, null, 600, true, "Enter the optional Id of the album");
    
    playerObjectControls = new ArrayList<>();
    frontImageControls = new ArrayList<>();
    frontInsideImageControls = new ArrayList<>();
    backImageControls = new ArrayList<>();
    labelImageControls = new ArrayList<>();

    descriptionTitleTextField = componentFactory.createObjectControlTextField(null, null, 600, true, "Enter the optional titel of the description");
    descriptionTextArea = componentFactory.createObjectControlMultiLineString(null, 400, true, "Enter an optional description of the album");
    
    mediaComboBox = componentFactory.createObjectControlEnumComboBox(MediumType.CD_AUDIO, MediumType.NOT_SET, MEDIA_DB_PACKAGE.getMediumType(), false, "Select a medium on which the album is issued");
    
    addMediumTypeButton = componentFactory.createButton("Add medium", "Add this medium type");
    addMediumTypeButton.setOnAction((e) -> {
      MediumType selectedMediumType = mediaComboBox.ocGetValue();
      if (selectedMediumType != null  &&  selectedMediumType != MediumType.NOT_SET) {
        Label label = componentFactory.createLabel(selectedMediumType.getLiteral());
        issuedOnMediaLabels.add(label);
        updateIssuedOnMediaPane();
      }
    });
    issuedOnMediaLabels = new ArrayList<>();
    
    isCompilationAlbumCheckBox = componentFactory.createCheckBox("compilation album", false);
    isOwnCompilationCheckBox = componentFactory.createCheckBox("own compilation", false);
    isSoundTrackCheckBox = componentFactory.createCheckBox("soundtrack", false);
    iveHadOnLpCheckBox = componentFactory.createCheckBox("I've had on lp", false);

    iWantComboBox = componentFactory.createObjectControlEnumComboBox(IWant.NOT_SET, IWant.NOT_SET, MEDIA_DB_PACKAGE.getIWant(), true, "Select whether you want this album or not");
    
    myCommentsTextArea = componentFactory.createObjectControlMultiLineString(null, 400, true, "Enter an optional personal comment about the album");
    
    discPanels = new ArrayList<>();
    
    controlsGroup = new ObjectControlGroup();
    controlsGroup.addObjectControls(
        albumTitleTextFieldObjectControl,
        albumArtistObjectControl,
        albumIssueDateTextField,
        albumIdTextField,
        descriptionTitleTextField,
        descriptionTextArea,
        iWantComboBox
    );
  }
  
  /**
   * Create the GUI.
   */
  @Override
  protected void createGUI() {

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
     * General information in a GridPane
     */
    GridPane gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'Album: <album-title>'
    Label label = componentFactory.createLabel("Title:");
    gridPane.add(label, 0, 0);
    gridPane.add(albumTitleTextFieldObjectControl.ocGetControl(), 1, 0);
    gridPane.add(albumTitleTextFieldObjectControl.ocGetValidIndicator(), 2, 0);

    // Second row: 'Artist: <artist>'   'New artist' button
    label = componentFactory.createLabel("Artist:");
    gridPane.add(label, 0, 1);
    gridPane.add(albumArtistObjectControl.ocGetControl(), 1, 1);
    gridPane.add(albumArtistObjectControl.ocGetValidIndicator(), 2, 1);
    Button newArtistButton = componentFactory.createButton("New artist", "The artist of the album has to be selected from the list of known artists. With this button you can add a new artist to the database");
    newArtistButton.setOnAction(e -> (new ArtistDetailsEditor(getCustomization(), "New artist", mediaDb)).show());
    gridPane.add(newArtistButton, 3, 1);
   
    // Third row: 'Issue date: <album-issue-date>'
    label = componentFactory.createLabel("Issued:");
    gridPane.add(label, 0, 2);
    gridPane.add(albumIssueDateTextField.ocGetControl(), 1, 2);
    gridPane.add(albumIssueDateTextField.ocGetValidIndicator(), 2, 2);
    
    // Fourth row: 'Album Id: <album-id>'
    label = componentFactory.createLabel("Album Id:");
    gridPane.add(label, 0, 3);
    gridPane.add(albumIdTextField.ocGetControl(), 1, 3);
    gridPane.add(albumIdTextField.ocGetValidIndicator(), 2, 3);
    
    // Fifth row: 'Collaborating Artists:'
    label = componentFactory.createLabel("Collaborating artists:");
    gridPane.add(label, 0, 4);
    playersGridPane = componentFactory.createGridPane(10.0, 10.0);
    gridPane.add(playersGridPane, 1, 4, 2, 4);
    
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
      ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
      if (MediaRegistry.albumInfoDirectory != null) {
        objectControlImageFile.setInitialDirectory(new File(MediaRegistry.albumInfoDirectory + File.separator + "Pictures"));
      }
      frontImageControls.add(objectControlImageFile);
      controlsGroup.addObjectControl(objectControlImageFile);
      objectControlImageFile.selectNewFile();
      redrawImagesPanel(frontImagesHBox, frontImageControls);
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
      ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
      if (MediaRegistry.albumInfoDirectory != null) {
        objectControlImageFile.setInitialDirectory(new File(MediaRegistry.albumInfoDirectory + File.separator + "Pictures"));
      }
      frontInsideImageControls.add(objectControlImageFile);
      controlsGroup.addObjectControl(objectControlImageFile);
      objectControlImageFile.selectNewFile();
      redrawImagesPanel(frontInsideImagesHBox, frontInsideImageControls);
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
      ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
      if (MediaRegistry.albumInfoDirectory != null) {
        objectControlImageFile.setInitialDirectory(new File(MediaRegistry.albumInfoDirectory + File.separator + "Pictures"));
      }
      backImageControls.add(objectControlImageFile);
      controlsGroup.addObjectControl(objectControlImageFile);
      objectControlImageFile.selectNewFile();
      redrawImagesPanel(backImagesHBox, backImageControls);
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
      ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
      if (MediaRegistry.albumInfoDirectory != null) {
        objectControlImageFile.setInitialDirectory(new File(MediaRegistry.albumInfoDirectory + File.separator + "Pictures"));
      }
      labelImageControls.add(objectControlImageFile);
      controlsGroup.addObjectControl(objectControlImageFile);
      objectControlImageFile.selectNewFile();
      redrawImagesPanel(labelImagesHBox, labelImageControls);
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
    gridPane.add(descriptionTitleTextField.ocGetControl(), 1, 0);
    
    // second row: 'Description: <description>'
    label = componentFactory.createLabel("Description:");
    gridPane.add(label, 0, 1);
    gridPane.add(descriptionTextArea.ocGetControl(), 1, 1);
    
    // Third row: 'Issued on: <medium-1> ... <medium-n> <medium-select-combobox> <add-medium-button>    
    label = componentFactory.createLabel("Issued on:");
    gridPane.add(label, 0, 2);
    issuedOnMediaPane = componentFactory.createHBox(10.0);
    gridPane.add(issuedOnMediaPane, 1, 2, 2, 4);
    
    centerPane.getChildren().add(gridPane);
    
    /*
     * Checkboxes for 'compilation album', 'own compilation', 'soundtrack' and 'I've had on lp'.
     */
    HBox hBox = componentFactory.createHBox(10.0, 10.0);
    hBox.getChildren().addAll(isCompilationAlbumCheckBox, isOwnCompilationCheckBox, isSoundTrackCheckBox, iveHadOnLpCheckBox);
    
    centerPane.getChildren().add(hBox);
    
    /*
     * MyInfo
     */
    gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'I want: <i-want>'
    label = componentFactory.createLabel("I want:");
    gridPane.add(label, 0, 0);
    gridPane.add(iWantComboBox.ocGetControl(), 1, 0);
    
    // Second row: 'MyComments: <my-comments>'
    label = componentFactory.createLabel("My comments:");
    gridPane.add(label, 0, 1);
    gridPane.add(myCommentsTextArea.ocGetControl(), 1, 1);
    
    centerPane.getChildren().add(gridPane);
    
    // Album references
    albumReferencesVBox = componentFactory.createVBox(10.0, 10.0);
    centerPane.getChildren().add(albumReferencesVBox);
    
    /*
     * Album type
     */
    HBox albumTypeHBox = componentFactory.createHBox(10.0, 10.0);
    label = componentFactory.createLabel("Album type:");
    albumTypeObjectControlEnumComboBox = componentFactory.createObjectControlEnumComboBox(AlbumType.NORMAL, null, true, "Select the type of album. This value determines how the discs/tracks are to be filled in");
//    albumTypeObjectControlEnumComboBox = new EnumComboBox<AlbumType>(AlbumType.NORMAL);
    ChangeListener<AlbumType> cl = new ChangeListener<>() {

      @Override
      public void changed(ObservableValue<? extends AlbumType> observable, AlbumType oldValue, AlbumType newValue) {
        LOGGER.severe("AlbumType: " + newValue);
        
      }
      
    };
    albumTypeObjectControlEnumComboBox.ocGetControl().getSelectionModel().selectedItemProperty().addListener(cl);
//    albumTypeObjectControlEnumComboBox.select(AlbumType.NORMAL);
    albumTypeHBox.getChildren().addAll(label, albumTypeObjectControlEnumComboBox.ocGetControl());
    centerPane.getChildren().add(albumTypeHBox);
    
    /*
     * Disc tracks tables.
     */
    discsVBox = componentFactory.createVBox(10.0, 10.0);
    centerPane.getChildren().add(discsVBox);
    
    Button newDiscButton = componentFactory.createButton("New disc", "click to add a disc to the album");
    newDiscButton.setOnAction((e) -> addNewDiscPanel(null));
    centerPane.getChildren().add(newDiscButton);
    
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
     * Control buttons panel on the bottom
     */
    addUpdateAndCancelButtonsPanel = componentFactory.createHBox(10.0, 12.0);
    mainPane.setBottom(addUpdateAndCancelButtonsPanel);
    
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(mainPane);

    setScene(new Scene(scrollPane));
    
    show();
  }
  
  @Override
  protected void handleChanges() {
    LOGGER.severe("=>");
    updateControls();
    redrawImagesPanel(frontImagesHBox, frontImageControls);
    updateTitle();
  }
  
  private void updateControls() {
    boolean disable = !controlsGroup.isValid().getValue()  ||  (editMode == EditMode.EDIT  &&  !dirty());
    addOrUpdateButton.setDisable(disable);
  }
  
  /**
   * React to changes in the list of Artists.
   * <p>
   * On any change, update the albumArtistObjectControl values.
   * If the change is a single ADD, set the albumArtistObjectControl to this new value.
   */
  private void installChangeListernerForArtists() {
    Adapter adapter = new EContentAdapter() {
      public void notifyChanged(Notification notification) {
        LOGGER.info("Notification received: " + EmfUtil.printNotification(notification, false));
        super.notifyChanged(notification);
        
        if (notification.isTouch()  ||  notification.getEventType() == Notification.REMOVING_ADAPTER) {
          // no action
          return;
        }
        
        updateContainerArtistComboBox();
        
        if (notification.getEventType() == Notification.ADD) {
          albumArtistObjectControl.ocSetValue((Artist) notification.getNewValue());
        }
      }

    };
    
    mediaDb.eAdapters().add(adapter);
  }

    
  /**
   * Update the albumArtistObjectControl, in case there is a change in the list of artists.
   */
  private void updateContainerArtistComboBox() {
    Artist currentSelectedArtist = albumArtistObjectControl.ocGetValue();
    albumArtistObjectControl.setOptions(mediaDb.getArtists());
    albumArtistObjectControl.ocSetValue(currentSelectedArtist);
  }
    
  /**
   * Fill all GUI controls from the details of an album.
   * <p>
   * If the album is null, all controls are cleared.
   * 
   * @param album the <code>Album</code> for which the GUI controls are updated. This value may be null, in which case all controls are cleared.
   */
  private void fillControlsFromAlbum(Album album) {
    // First update the lists
    fillPlayersFromAlbum(album);
    updatePicturesForAlbum(album);
    
    // Then the controls
    fillGeneralControlsFromAlbum(album);
    redrawPlayersPane();
    updateIssuedOnMediaPane();
    redrawImagesPanel(frontImagesHBox, frontImageControls);
    redrawImagesPanel(frontInsideImagesHBox, frontInsideImageControls);
    redrawImagesPanel(backImagesHBox, backImageControls);
    redrawImagesPanel(labelImagesHBox, labelImageControls);
    updateCheckBoxes(album);
    updateAlbumReferences(album);
    updateDiscsPanel(album);
  }

  /**
   * Fill the general controls from the <code>album</code>.
   * 
   * @param album the <code>Album</code> for which the controls are updated. This value may be null, in which case all controls are cleared.
   */
  private void fillGeneralControlsFromAlbum(Album album) {
    issuedOnMediaLabels.clear();

    if (album != null) {
      albumTitleTextFieldObjectControl.ocSetValue(album.getTitle());

      albumArtistObjectControl.ocSetValue(album.getArtist());

      albumIssueDateTextField.ocSetValue(album.getReleaseDate());

      albumIdTextField.ocSetValue(album.getId());

      descriptionTitleTextField.ocSetValue(album.getDescriptionTitle());

      descriptionTextArea.ocSetValue(album.getDescription());

      for (MediumType mediumType: album.getIssuedOnMediums()) {
        Label label = componentFactory.createLabel(mediumType.getLiteral());
        issuedOnMediaLabels.add(label);
      }

      if (album.isSetMyInfo()) {
        MyInfo myInfo = album.getMyInfo();

        iWantComboBox.ocSetValue(myInfo.getIWant());

        myCommentsTextArea.ocSetValue(myInfo.getMyComments());
      }

    } else {
      albumTitleTextFieldObjectControl.ocSetValue(null);
      albumArtistObjectControl.ocSetValue(null);
      albumIssueDateTextField.ocSetValue(null);
      albumIdTextField.ocSetValue(null);
      descriptionTitleTextField.ocSetValue(null);
      descriptionTextArea.ocSetValue(null);
      myCommentsTextArea.ocSetValue(null);
    }
  }

  private void fillPlayersFromAlbum(Album album) {
    for (PlayerObjectControl playerObjectControl: playerObjectControls) {
      controlsGroup.removeObjectInput(playerObjectControl);
    }
    
    playerObjectControls.clear();
    
    if (album != null) {
      for (Player player: album.getPlayers()) {
        PlayerObjectControl playerObjectControl = new PlayerObjectControl(customization, mediaDb);
        playerObjectControl.ocSetValue(player);
//        AutoCompleteTextFieldObjectInput<Artist> artistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(artistStringConverterAndChecker, null, 300, false, "Enter a player");
//        artistObjectControl.setObjectValue(player.getArtist());

//        TextField playerInstrumentTextField = componentFactory.createTextField(300, "A comma separated list of instruments");
//        playerInstrumentTextField.setText(StringUtil.stringCollectionToCommaSeparatedStrings(player.getInstruments()));

//        Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> tuplet = createPlayerTuplet(player.getArtist(), player.getInstruments());
        controlsGroup.addObjectControl(playerObjectControl);
        playerObjectControls.add(playerObjectControl);
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
  private Tuplet<ObjectControlAutoCompleteTextField<Artist>, TextField> createPlayerTuplet(Artist artist, List<String> instruments) {
    ObjectControlAutoCompleteTextField<Artist> artistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(artistStringConverterAndChecker, null, 300, false, "Enter a player");
    artistObjectControl.ocSetValue(artist);
    artistObjectControl.setOptions(mediaDb.getArtists());

    TextField playerInstrumentTextField = componentFactory.createTextField(300, "A comma separated list of instruments");
    playerInstrumentTextField.setText(StringUtil.stringCollectionToCommaSeparatedStrings(instruments));

    Tuplet<ObjectControlAutoCompleteTextField<Artist>, TextField> tuplet = new Tuplet<>(artistObjectControl, playerInstrumentTextField);
    
    return tuplet;
  }
  
  private void updatePicturesForAlbum(Album album) {
    frontImageControls.clear();
    frontInsideImageControls.clear();
    backImageControls.clear();
    labelImageControls.clear();
    
    if (album != null) {
      for (String imageFileName: album.getImagesFront()) {
        ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
        objectControlImageFile.ocSetValue(new File(MediaRegistry.albumInfoDirectory + File.separator + imageFileName));
        frontImageControls.add(objectControlImageFile);
      }
      
      for (String imageFileName: album.getImagesFrontInside()) {
        ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
        objectControlImageFile.ocSetValue(new File(MediaRegistry.albumInfoDirectory + File.separator + imageFileName));
        frontInsideImageControls.add(objectControlImageFile);
      }
      
      for (String imageFileName: album.getImagesBack()) {
        ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
        objectControlImageFile.ocSetValue(new File(MediaRegistry.albumInfoDirectory + File.separator + imageFileName));
        backImageControls.add(objectControlImageFile);
      }
      
      for (String imageFileName: album.getImagesLabel()) {
        ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
        objectControlImageFile.ocSetValue(new File(MediaRegistry.albumInfoDirectory + File.separator + imageFileName));
        labelImageControls.add(objectControlImageFile);
      }
    }
  }
  
  private void redrawPlayersPane() {
    playersGridPane.getChildren().clear();
    int row = 0;
    playersGridPane.add(componentFactory.createLabel("Artist"), 0, row);
    playersGridPane.add(componentFactory.createLabel("Instruments"), 1, row);

    row++;
    for (PlayerObjectControl playerObjectControl: playerObjectControls) {
      playersGridPane.add(playerObjectControl.getArtistObjectControl().ocGetControl(), 0, row);
//      AutoCompleteTextFieldObjectInput<Artist> playerNameTextField = tuplet.getObject1();
//      playersGridPane.add(playerNameTextField, 0, row);

      playersGridPane.add(playerObjectControl.getPlayerInstrumentTextField().ocGetControl(), 1, row);
//      TextField playerInstrumentTextField = tuplet.getObject2();
//      playersGridPane.add(playerInstrumentTextField, 1, row);
      
      playersGridPane.add(playerObjectControl.ocGetValidIndicator(), 2, row);

      Button deletePlayerButton = componentFactory.createButton("Remove", "Delete this player from the album");
      deletePlayerButton.setOnAction((e) -> {
        playerObjectControls.remove(playerObjectControl);
//        playerObjectControls.remove(tuplet);
        redrawPlayersPane();
      });
      playersGridPane.add(deletePlayerButton, 3, row);

      row++;
    }
    
    Button addPlayerButton = componentFactory.createButton("Add player", "Add a player to the collaborators of the album");
    addPlayerButton.setOnAction((e) -> {
      PlayerObjectControl playerObjectControl = new PlayerObjectControl(customization, mediaDb);
      playerObjectControls.add(playerObjectControl);
      controlsGroup.addObjectControl(playerObjectControl);
//      Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> tuplet = createPlayerTuplet(null, null);
//      playerObjectControls.add(tuplet);
      redrawPlayersPane();
    });
    playersGridPane.add(addPlayerButton, 0, row);   
  }
  
  /**
   * Update the {@code issuedOnMediaPane}.
   * 
   * @param album
   */
  private void updateIssuedOnMediaPane() {
    issuedOnMediaPane.getChildren().clear();
    
    issuedOnMediaPane.getChildren().addAll(issuedOnMediaLabels);
    
    issuedOnMediaPane.getChildren().addAll(mediaComboBox.ocGetControl(), addMediumTypeButton);
  }
  
  private void redrawImagesPanel(HBox imagesBox, List<ObjectControlImageFile> objectControlsImageFile) {
    LOGGER.severe("=>");
    
    imagesBox.getChildren().clear();
    
    for (ObjectControlImageFile objectControlImageFile: objectControlsImageFile) {
      StackPane stackPane = objectControlImageFile.ocGetControl();
      LOGGER.severe("Redrawing: " + objectControlImageFile.ocGetValue());
      Button deleteImageButton = componentFactory.createButton("Delete", "Remove this image");
      StackPane.setAlignment(deleteImageButton, Pos.TOP_RIGHT);
      stackPane.getChildren().add(deleteImageButton);
      deleteImageButton.setOnAction((e) -> {
        LOGGER.severe("Removing image");
        controlsGroup.removeObjectInput(objectControlImageFile);
        objectControlsImageFile.remove(objectControlImageFile);
      });
      imagesBox.getChildren().add(stackPane);
    }
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
  
  private void updateAlbumReferences(Album album) {
    albumReferencesVBox.getChildren().clear();
    
    if (album != null) {
      MyInfo myInfo = album.getMyInfo();

      if (myInfo != null) {
        boolean first = true;
        for (Album referredAlbum: myInfo.getAlbumReferences()) {
          if (first) {
            Label label = componentFactory.createLabel("Album references:");
            albumReferencesVBox.getChildren().add(label);
            first = false;
          }
          ObjectControlString albumReference = componentFactory.createObjectControlString(referredAlbum.getArtistAndTitle(), 600, false, "Reference to album");
          albumReferencesVBox.getChildren().add(albumReference.ocGetControl());
        }
      }
    }
  }
  
  /**
   * Update the discs panel (<code>discTracksVBox</code>).
   * <p>
   * The panel is cleared and then for each disc (disc copy) of the <code>Album</code> a DiscPanel is added to the panel.
   */
  private void updateDiscsPanel(Album album) {
    discsVBox.getChildren().clear();
    discPanels.clear();

    if (album != null) {
      for (Disc disc: album.getDiscs()) {
        addNewDiscPanel(disc);
      }
    } else {
      addNewDiscPanel(null);  // There shall always be at least one disc.
    }
  }
  
  private void addNewDiscPanel(Disc disc) {
//    DiscPanel discPanel = new DiscPanel(customization, disc, albumTypeObjectControlEnumComboBox.valueProperty(), mediaDb);
    DiscPanel discPanel = new DiscPanel(customization, disc, null, mediaDb);
    discPanels.add(discPanel);
    discsVBox.getChildren().add(discPanel);
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
    ObjectControlFolderSelecter folderSelecter = componentFactory.createFolderSelecter("D:\\SoulSeek\\complete", 600, "Folder to import album information from", "Select source folder", "Open source folder chooser", "Source folder");

    Label label = componentFactory.createLabel("Derive album details from:");
    hBox.getChildren().add(label);
    
    hBox.getChildren().add(folderSelecter.ocGetControl());
    hBox.getChildren().add(folderSelecter.getFolderChooserButton());
    
    Button button = componentFactory.createButton("Derive album details", "Derive the album details from this folder");
    button.setOnAction(e -> deriveAlbumDetails(folderSelecter.ocGetAbsolutePath()));
    hBox.getChildren().add(button);
    
    outerHBox.getChildren().add(hBox);
        
    return outerHBox;
  }
  
  /**
   * Update the panel with the Add/Update and Cancel buttons.
   */
  private void updateAddUpdateAndCancelButtonsPanel() {
    addUpdateAndCancelButtonsPanel.getChildren().clear();

    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    addUpdateAndCancelButtonsPanel.getChildren().add(spacer);
    
    Button button;
    
    if (editMode == EditMode.NEW) {
      addOrUpdateButton = componentFactory.createButton("Add album", "Add the album to the media database");
      addOrUpdateButton.setOnAction(e -> addAlbum());
    } else {
      addOrUpdateButton = componentFactory.createButton("Update album", "Update the album in the media database");
      addOrUpdateButton.setOnAction(e -> updateAlbum());
    }
    addUpdateAndCancelButtonsPanel.getChildren().add(addOrUpdateButton);
    
    button = componentFactory.createButton("Cancel", "Exit window without saving");
    button.setOnAction(e -> closeWindow());
    addUpdateAndCancelButtonsPanel.getChildren().add(button);
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
    
    fillControlsFromAlbum(album);
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
      Album album;
      
      if (isOwnCompilationCheckBox.isSelected()) {
        album = MEDIA_DB_FACTORY.createMyCompilation();
      } else {
        album = MEDIA_DB_FACTORY.createAlbum();
      }
      
      updateAlbumFromControls(album);
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
      updateAlbumFromControls(album);
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
   * Update an album from the information in the controls.
   * 
   * @param album the Album to be updated
   * @throws AlbumDetailsException in case one or more controls has an illegal value.
   */
  private void updateAlbumFromControls(Album album) throws AlbumDetailsException {
    
    List<String> problems = new ArrayList<>();
    
    // Album title
    String titleText = albumTitleTextFieldObjectControl.ocGetValue();
    if (titleText == null) {
      problems.add("Album title may not be empty");
    }
    EmfUtil.setFeatureValue(album, MEDIA_DB_PACKAGE.getAlbum_Title(), titleText);
    
    // Artist
    Artist artist = albumArtistObjectControl.ocGetValue();
    if (artist == null) {
      problems.add("There is no artist selected");
    }
    EmfUtil.setFeatureValue(album, MEDIA_DB_PACKAGE.getAlbum_Artist(), artist);

    // Issue date
    FlexDate releaseDate = albumIssueDateTextField.ocGetValue();
    EmfUtil.setFeatureValue(album, MEDIA_DB_PACKAGE.getAlbum_ReleaseDate(), releaseDate);
    
    // Album Id
    String albumIdText = albumIdTextField.ocGetValue();
    EmfUtil.setFeatureValue(album, MEDIA_DB_PACKAGE.getAlbum_Id(), albumIdText);
    
    // Players
    // Update the complete list of players if there is any change.
    boolean playersChanged = false;
    if (playerObjectControls.size() != album.getPlayers().size()) {
      playersChanged = true;
    }
    
    if (!playersChanged) {
      for (int i = 0; i < playerObjectControls.size(); i++) {
        Player currentPlayer = album.getPlayers().get(i);
        PlayerObjectControl playerObjectControl = playerObjectControls.get(i);
//        Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> tuplet = playerObjectControls.get(i);
        Artist newArtist = playerObjectControl.getArtistObjectControl().ocGetValue();
//        Artist newArtist = tuplet.getObject1().getObjectValue();
        List<String> newInstruments = StringUtil.commaSeparatedValuesToListOfValues(playerObjectControl.getPlayerInstrumentTextField().ocGetValue());
//        List<String> newInstruments = StringUtil.commaSeparatedValuesToListOfValues(tuplet.getObject2().getText());
        if (!newArtist.equals(currentPlayer.getArtist())  ||
            !newInstruments.equals(currentPlayer.getInstruments())) {
          playersChanged = true;
          break;
        }
      }
    }
    
    if (playersChanged) {
      album.getPlayers().clear();
      for (PlayerObjectControl playerObjectControl: playerObjectControls) {
        Player player = MEDIA_DB_FACTORY.createPlayer();
        player.setArtist(playerObjectControl.getArtistObjectControl().ocGetValue());
        player.getInstruments().addAll(StringUtil.commaSeparatedValuesToListOfValues(playerObjectControl.getPlayerInstrumentTextField().ocGetValue()));

        album.getPlayers().add(player);
      }
//      for (Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> tuplet: playerObjectControls) {
//        Player player = MEDIA_DB_FACTORY.createPlayer();
//        AutoCompleteTextFieldObjectInput<Artist> artistControl = tuplet.getObject1();
//        player.setArtist(artistControl.getObjectValue());
//
//        TextField playerInstrumentsTextField = tuplet.getObject2();
//        player.getInstruments().addAll(StringUtil.commaSeparatedValuesToListOfValues(playerInstrumentsTextField.getText()));
//
//        album.getPlayers().add(player);
//      }
    }
    
    // Images front
    setImagesIfChanged2(album.getImagesFront(), frontImageControls);
    
    // Images front inside
    setImagesIfChanged2(album.getImagesFrontInside(), frontInsideImageControls);
    
    // Images back
    setImagesIfChanged2(album.getImagesBack(), backImageControls);
    
    // Images label
    setImagesIfChanged2(album.getImagesLabel(), labelImageControls);
    
    // Issued on
    setIssuedOnMediumsIfChanged(album.getIssuedOnMediums(), issuedOnMediaLabels);

    // Description title
    String descriptionTitleText = descriptionTitleTextField.ocGetValue();
    EmfUtil.setFeatureValue(album, MEDIA_DB_PACKAGE.getAlbum_DescriptionTitle(), descriptionTitleText);

    // Description
    String descriptionText = descriptionTextArea.ocGetValue();
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
      IWant iWant = iWantComboBox.ocGetValue();
      if (iWant == null) {
        iWant = IWant.NOT_SET;
      }
      EmfUtil.setFeatureValue(myInfoToFill, MEDIA_DB_PACKAGE.getMyInfo_IWant(), iWant);
      
      EmfUtil.setFeatureValue(myInfoToFill, MEDIA_DB_PACKAGE.getMyInfo_IveHadOnLP(), iveHadOnLpCheckBox.isSelected());
      
      String myComments = myCommentsTextArea.ocGetValue();
      EmfUtil.setFeatureValue(myInfoToFill, MEDIA_DB_PACKAGE.getMyInfo_MyComments(), myComments);
      
      if (albumMyInfo == null) {
        album.setMyInfo(myInfoToFill);
      }
    }
    
    for (DiscPanel discPanel: discPanels) {
      updateDiscFromDiscPanel(discPanel);
    }
    
    if (problems.size() != 0) {
      throw new AlbumDetailsException(problems);
    }
  }
  
  /**
   * Update a Disc from the information in a DiscPanel.
   * 
   * @param discPanel the DiscPanel to update the disc from.
   */
  private void updateDiscFromDiscPanel(DiscPanel discPanel) {
    Disc disc = discPanel.getDisc();
    EmfUtil.setFeatureValue(disc, MEDIA_DB_PACKAGE.getDisc_Title(), discPanel.getDiscTitle());
  
    for (TrackReferenceAndMyTrackInfoControls trackReferencePanel: discPanel.getTrackReferencePanels()) {
      updateTrackReferenceFromTrackReferencePanel(trackReferencePanel);
    }
    
  }

  /**
   * Update a TrackReference based on TrackReferenceAndMyTrackInfoControls
   *  
   * @param trackReferencePanel the TrackReferenceAndMyTrackInfoControls to handle.
   */
  private void updateTrackReferenceFromTrackReferencePanel(TrackReferenceAndMyTrackInfoControls trackReferencePanel) {
    TrackReference trackReference = trackReferencePanel.getTrackReference();

    // If the Track has changed, the original Track that was referred to may become obsolete. If so we delete it.
    Track originalTrack = trackReference.getTrack();
    EmfUtil.setFeatureValue(trackReference, MEDIA_DB_PACKAGE.getTrackReference_Track(), trackReferencePanel.getTrack());
    if (originalTrack.getReferredBy().isEmpty()) {
      mediaDb.getTracks().remove(originalTrack);
    }
    
    EmfUtil.setFeatureValue(trackReference, MEDIA_DB_PACKAGE.getTrackReference_BonusTrack(), trackReferencePanel.getBonusTrack());
    
    boolean isMyTrackInfoNeeded = isMyTrackInfoNeeded(trackReferencePanel);
    if (isMyTrackInfoNeeded) {
      EmfUtil.setFeatureValue(trackReference.getMyTrackInfo(), MEDIA_DB_PACKAGE.getMyTrackInfo_Collection(), trackReferencePanel.getCollection());
      EmfUtil.setFeatureValue(trackReference.getMyTrackInfo(), MEDIA_DB_PACKAGE.getMyTrackInfo_IWant(), trackReferencePanel.getIWant());
    }
  }

  private boolean isMyTrackInfoNeeded(TrackReferenceAndMyTrackInfoControls trackReferencePanel) {
    if (trackReferencePanel.getCollection() != goedegep.media.mediadb.model.Collection.NOT_SET) {
      return true;
    }
    
    if (trackReferencePanel.getIWant() != IWant.NOT_SET) {
      return true;
    }
    
    return false;
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
    if (myCommentsTextArea.ocGetValue() != null) {  // TODO add other controls
      return true;
    }
    
    if (iWantComboBox.ocGetValue() != IWant.NOT_SET) {
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
  private void setImagesIfChanged2(List<String> currentImages, List<ObjectControlImageFile> newImageFiles) {
    // Update the complete list of images if there is any change.
    boolean imagesChanged = false;
    if (newImageFiles.size() != currentImages.size()) {
      imagesChanged = true;
    }
    
    if (!imagesChanged) {
      for (int i = 0; i < newImageFiles.size(); i++) {
        ObjectControlImageFile newImageFile = newImageFiles.get(i);
        String newImageFileName = FileUtils.getPathRelativeToFolder(MediaRegistry.albumInfoDirectory, newImageFile.ocGetAbsolutePath());
        String currentImageFilename = currentImages.get(i);
        if (!newImageFileName.equals(currentImageFilename)) {
          imagesChanged = true;
          break;
        }
      }
    }
    
    if (imagesChanged) {
      currentImages.clear();
      for (ObjectControlImageFile file: newImageFiles) {
        String imageFileName = FileUtils.getPathRelativeToFolder(MediaRegistry.albumInfoDirectory, file.ocGetAbsolutePath());
        currentImages.add(imageFileName);
      }
    }
    
  }

  private void closeWindow() {
    this.close();
  }
  
  /**
   * Check whether any value of the controls differs from the album value.
   * 
   * @return true if any value of the controls differs from the album value, false otherwise.
   */
  private boolean dirty() {
    return !PgUtilities.equals(albumTitleTextFieldObjectControl.ocGetValue(), album.eGet(MediadbPackage.eINSTANCE.getAlbum_Title()));
  }
  
  /**
   * Update the title.
   * <p>
   * The title consists of:
   * - A dirty indicator '*' in case there are changed values and the edit mode is EDIT.
   * - 'New album' (in NEW mode), or 'Edit album' in (in EDIT mode).
   */
  private void updateTitle() {
    StringBuilder buf = new StringBuilder();
    
    if (editMode == EditMode.NEW) {
      buf.append("New album: ");
      Artist artist = albumArtistObjectControl.ocGetValue();
      String artistName = artist != null ? artist.getName() : "no artist";
      String title = albumTitleTextFieldObjectControl.ocGetValue();
      if (title == null) {
        title = "no title";
      }
      buf.append(artistName).append(" - ").append(title);

    } else {
      if (dirty()) {
        buf.append("*");
      }
      
      buf.append("Edit album: ");
      buf.append(album.getArtistAndTitle());
    }
    
    setTitle(buf.toString());
  }
  
  /**
   * Update the Edit mode.
   * <p>
   * This has to be called when the {@code album} value has changed.
   */
  private void updateEditMode() {
    if (album != null) {
      editMode = EditMode.EDIT;
      mainPane.setTop(null);
    } else {
      editMode = EditMode.NEW;
      mainPane.setTop(importControlsPanel);
    }
    updateAddUpdateAndCancelButtonsPanel();
    updateControls();
    updateTitle();
  }
}


record DiscPanelInfo(Disc disc, Node panel) {
}





