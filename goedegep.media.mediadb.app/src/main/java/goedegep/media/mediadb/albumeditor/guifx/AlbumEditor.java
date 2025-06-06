package goedegep.media.mediadb.albumeditor.guifx;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlBoolean;
import goedegep.jfx.objectcontrols.ObjectControlEnumComboBox;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.jfx.objectcontrols.ObjectControlMultiLineString;
import goedegep.jfx.objectcontrols.ObjectControlStatusList;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.jfx.objectcontrols.ObjectEditPanelTemplate;
import goedegep.jfx.objecteditor.EditMode;
import goedegep.jfx.objecteditor.ObjectEditorException;
import goedegep.jfx.objecteditor.ObjectEditorTemplate;
import goedegep.media.app.MediaRegistry;
import goedegep.media.mediadb.app.MediaDbService;
import goedegep.media.mediadb.app.derivealbuminfo.AlbumInfo;
import goedegep.media.mediadb.app.derivealbuminfo.DeriveAlbumInfo;
import goedegep.media.mediadb.app.derivealbuminfo.DiscInfo;
import goedegep.media.mediadb.app.guifx.ArtistDetailsEditor;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.AlbumType;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.Track;
import goedegep.util.datetime.FlexDate;
import goedegep.util.emf.EmfUtil;
import goedegep.util.file.FileUtils;
import goedegep.util.string.StringUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 * This class is an {@link Album} editor.
 * <p>
 * It can be used to create an new Album, or to make changes to an existing Album.
 * For creating a new Album it is possible to derive the information from the music files in a folder on disc.
 *
 */
public class AlbumEditor extends ObjectEditorTemplate<Album> {
  
  /*
   * Strategy:
   * This editor can be used to edit album details, or to create a new album.
   * If the field 'object' (Album) is null (by default), the editor is in the 'new album' mode. Otherwise it is in 'edit album' mode.
   * 
   * All information of an album is 'stored' in the GUI controls and a number of lists. Together these are referred to as the GUI controls.
   * When a new album is set (which may be null), the information from the album is stored in the controls. If the album is null,
   * all information is cleared or default values are used.
   * Any user changes, or 'imports' are only stored in the GUI controls.
   * Upon 'add album' an album is created from the controls, 'object' is set to this value and the album is added to the database.
   * Upon 'update', the 'album' is updated from the controls.
   */
  
  private static final Logger LOGGER = Logger.getLogger(AlbumEditor.class.getName());
  private static final String NEW_LINE = System.getProperty("line.separator");
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
  private MediaDbService mediaDbService;
  
  /**
   * The Media Database.
   */
  private MediaDb mediaDb;
    
  /**
   * Panel with the controls for importing information from a folder with album tracks.
   */
  private Node importControlsPanel;
  
  /**
   * Album type control
   */
  ObjectControlEnumComboBox<AlbumType> albumTypeObjectControl;

  /**
   * Control for the album title.
   * The title is plain text.
   */
  private ObjectControlString albumTitleTextFieldObjectControl;
  
  /**
   * Control for the album artist.
   */
  private ArtistObjectControl albumArtistObjectControl;
  
  /**
   * Control for the album release date.
   * A <code>FlexDateFormat</code> is used to fill and parse this value.
   */
  private ObjectControlFlexDate albumIssueDateObjectControl;
  
  /**
   * Control for the album Id.<br/>
   * The album Id is plain text.
   */
  private ObjectControlString albumIdObjectControl;
  
  /**
   * Object control for the collaborating artists, a.k.a. the {@code players}.
   */
  private ObjectEditPanelTemplate<List<Player>> playersObjectControl;
  
  /**
   * Object control for the Description Title (which is plain text).
   */
  private ObjectControlString descriptionTitleObjectControl;
  
  /**
   * Object control for the Description (which is plain MarkDown text).
   */
  private ObjectControlMultiLineString descriptionObjectControl;
  
  /**
   * Object control for the front images.  
   */
  private ObjectControlForImages frontImagesObjectControl;
  
  /**
   * Object control for the front inside images.  
   */
  private ObjectControlForImages frontInsideImagesObjectControl;
  
  /**
   * Object control for the back images.  
   */
  private ObjectControlForImages backImagesObjectControl;
  
  /**
   * Object control for the label images.  
   */
  private ObjectControlForImages labelImagesObjectControl;
  
  /**
   * Object control to select whether the album is a movie soundtrack or not.
   */
  private ObjectControlBoolean soundTrackObjectControl;
  
  /**
   * Indicates whether I've had the album on lp or not.
   */
  private ObjectControlBoolean iveHadOnLpObjectControl;
  
  /**
   * Shows the 'I want' information
   */
  private ObjectControlEnumComboBox<IWant> iWantObjectControl;
  
  /**
   * Shows the 'I have on' information
   */
  private IHaveOnObjectControl iHaveOnObjectControl;
  
  /**
   * Shows My Comments
   */
  private ObjectControlMultiLineString myCommentsObjectControl;
  
  /**
   * Shows myInfo albumReferences
   */
  private VBox albumReferencesVBox;

  /**
   * Shows the discs
   * <p>
   * All children are the control of a DiscEditPanel.
   */
  private VBox discsVBox;
  
  /**
   * List of ObjectEditPanelTemplate<Disc>
   * <p>
   * This extra list (extra to the discsVBox) is needed, because that box only contains the controls and you can't get the panel from the control.
   */
  private List<ObjectEditPanelTemplate<Disc>> discPanels;
  
  /**
   * Control for handling the status of the list of album discs.
   */
  private ObjectControlStatusList discsObjectControl;
  
  /**
   * Factory method to obtain a new instance of an {@code AlbumEditor}.
   * 
   * @param customization the GUI customization.
   * @param mediaDbService the media database service.
   * @return a newly created {@code AlbumEditor}.
   */
  public static AlbumEditor newInstance(CustomizationFx customization, MediaDbService mediaDbService) {
    AlbumEditor albumEditor = new AlbumEditor(customization, mediaDbService);
    albumEditor.performInitialization();
    
    return albumEditor;
  }
    
  /**
   * Constructor.
   * <p>
   * Use this constructor to edit an existing album.
   * 
   * @param customization the GUI customization.
   * @param mediaDb the media database.
   */
  private AlbumEditor(CustomizationFx customization, MediaDbService mediaDbService) {
    // title is set to null, as it is updated via updateTitle().
    super(customization, null, mediaDbService::addAlbumToMediaDatabase);
    
    this.mediaDbService = mediaDbService;
    mediaDb = mediaDbService.getMediaDbResource().getEObject();
    
    objectControlsGroup.setId("AlbumEditor");
    
    setMinWidth(1400);
    setMinHeight(1200);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureEditor() {
    setAddObjectTexts("Add album", "Add the album to the media database");
    setUpdateObjectTexts("Update album", "Update the current album");
    setNewObjectTexts("New", "Clear the controls to start entering new Album data");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createControls() {
    albumTypeObjectControl = componentFactory.createObjectControlEnumComboBox(AlbumType.NORMAL_ALBUM, true, "Select the type of album. This value determines how the discs/tracks are to be filled in");
    albumTypeObjectControl.setId("album type");
    albumTypeObjectControl.setLabelBaseText("Album type");
    
    albumTitleTextFieldObjectControl = componentFactory.createObjectControlString(null, 600, false, "The album title is a mandatory field");
    albumTitleTextFieldObjectControl.setId("album title");
    albumTitleTextFieldObjectControl.setLabelBaseText("Title");

    albumArtistObjectControl = new ArtistObjectControl(customization, mediaDbService);
    albumArtistObjectControl.setId("album artist");
    albumArtistObjectControl.setLabelBaseText("Artist");

    albumIssueDateObjectControl = componentFactory.createObjectControlFlexDate(null, 600, false, "Optional first issue date of the album");
    albumIssueDateObjectControl.setId("issue date");
    albumIssueDateObjectControl.setLabelBaseText("Issued");
    
    albumIdObjectControl = componentFactory.createObjectControlString(null, 600, true, "Enter the optional Id of the album");
    albumIdObjectControl.setId("album Id");
    albumIdObjectControl.setLabelBaseText("Album Id");
    
    discsObjectControl = new ObjectControlStatusList(true);
    discsObjectControl.setId("discs");
    
    
    playersObjectControl = PlayersEditPanel.newInstance(customization, mediaDbService);
    playersObjectControl.setId("players");
    objectControlsGroup.addObjectControlGroup(playersObjectControl.getObjectControlsGroup());
    
    frontImagesObjectControl = new ObjectControlForImages(customization, "Front images");
    frontImagesObjectControl.setId("front images");
    frontInsideImagesObjectControl = new ObjectControlForImages(customization, "Front inside images");
    frontInsideImagesObjectControl.setId("front inside images");
    backImagesObjectControl = new ObjectControlForImages(customization, "Back images");
    backImagesObjectControl.setId("back images");
    labelImagesObjectControl = new ObjectControlForImages(customization, "Label images");
    labelImagesObjectControl.setId("label images");

    descriptionTitleObjectControl = componentFactory.createObjectControlString(null, 600, true, "Enter the optional titel of the description");
    descriptionTitleObjectControl.setId("description title");
    descriptionTitleObjectControl.setLabelBaseText("Description title");
    
    descriptionObjectControl = componentFactory.createObjectControlMultiLineString(null, true);
    descriptionObjectControl.setId("description");
    descriptionObjectControl.setLabelBaseText("Description");

    soundTrackObjectControl = componentFactory.createObjectControlBoolean("soundtrack", false, true, "Select if the album is a movie soundtrack");
    soundTrackObjectControl.setId("sound track");
    iveHadOnLpObjectControl = componentFactory.createObjectControlBoolean("I've had on lp", false, true, "Select if you owned the album on lp");
    iveHadOnLpObjectControl.setId("i've had on lp");

    iWantObjectControl = componentFactory.createObjectControlEnumComboBox(IWant.NOT_SET,true, "Select whether you want this album or not");
    iWantObjectControl.setId("i want");
    iWantObjectControl.setLabelBaseText("I want");

    iHaveOnObjectControl = new IHaveOnObjectControl(customization);
    iHaveOnObjectControl.setId("i have on");
    iHaveOnObjectControl.setLabelBaseText("I have on");
    
    myCommentsObjectControl = componentFactory.createObjectControlMultiLineString(null, true);
    myCommentsObjectControl.setId("my comments");
    myCommentsObjectControl.setLabelBaseText("My comments");
    
    
    discPanels = new ArrayList<>();
    
    objectControlsGroup.addObjectControls(
        albumTypeObjectControl,
        albumTitleTextFieldObjectControl,
        albumArtistObjectControl,
        albumIssueDateObjectControl,
        albumIdObjectControl,
        discsObjectControl,
        frontImagesObjectControl,
        frontInsideImagesObjectControl,
        backImagesObjectControl,
        labelImagesObjectControl,
        descriptionTitleObjectControl,
        descriptionObjectControl,
        soundTrackObjectControl,
        iveHadOnLpObjectControl,
        iWantObjectControl,
        iHaveOnObjectControl,
        myCommentsObjectControl
    );
    
    objectControlsGroup.addObjectControlGroup(playersObjectControl.getObjectControlsGroup());
    
    addListener((o) -> updateTitle());
  }
  
  /**
   * The Edit panel consists of:
   * <ul>
   * <li>Import controls</li>
   * <li>Album details</li>
   *   <ul>
   *   <li>Album type</li>
   *   <li>Title</li>
   *   <li>Artist</li>
   *   <li>Issued</li>
   *   <li>Album Id</li>
   *   <li>Collaborating artists (ToDo)</li>
   *   <li>Front images</li>
   *   <li>Front Inside images</li>
   *   <li>Back images</li>
   *   <li>Label images</li>
   *   </ul>
   * <ul>
   */
  @Override
  protected void createEditPanel(VBox rootPane) {

    /*
     * Panel is a VBox.
     * Starts with import controls
     * Followed by all album details
     */

    VBox mainPane = componentFactory.createVBox();

    /*
     * Import controls panel at the top
     */
    importControlsPanel = createImportControlsPanel();
    mainPane.getChildren().add(importControlsPanel);    
    
    /*
     * Album details
     */
    
    /*
     * General information in a GridPane
     */
    GridPane gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'Album type: <album-type>'
    gridPane.add(albumTypeObjectControl.getLabel(), 0, 0);
    ChangeListener<AlbumType> cl = new ChangeListener<>() {

      @Override
      public void changed(ObservableValue<? extends AlbumType> observable, AlbumType oldValue, AlbumType newValue) {
        LOGGER.severe("AlbumType: " + newValue);
        
      }
      
    };
    albumTypeObjectControl.getControl().getSelectionModel().selectedItemProperty().addListener(cl);
    gridPane.add(albumTypeObjectControl.getControl(), 1, 0);
    gridPane.add(albumTypeObjectControl.getStatusIndicator(), 2, 0);
    
    // Second row: 'Album: <album-title>'
    gridPane.add(albumTitleTextFieldObjectControl.getLabel(), 0, 1);
    gridPane.add(albumTitleTextFieldObjectControl.getControl(), 1, 1);
    gridPane.add(albumTitleTextFieldObjectControl.getStatusIndicator(), 2, 1);

    // Third row: 'Artist: <artist>'   'New artist' button
    gridPane.add(albumArtistObjectControl.getLabel(), 0, 2);
    gridPane.add(albumArtistObjectControl.getControl(), 1, 2);
    gridPane.add(albumArtistObjectControl.getStatusIndicator(), 2, 2);
    Button newArtistButton = componentFactory.createButton("New artist", "The artist of the album has to be selected from the list of known artists. With this button you can add a new artist to the database");
    newArtistButton.setOnAction(e -> ArtistDetailsEditor.newInstance(customization, "New artist", mediaDbService).show());
    gridPane.add(newArtistButton, 3, 2);
   
    // Fourth row: 'Issue date: <album-issue-date>'
    gridPane.add(albumIssueDateObjectControl.getLabel(), 0, 3);
    gridPane.add(albumIssueDateObjectControl.getControl(), 1, 3);
    gridPane.add(albumIssueDateObjectControl.getStatusIndicator(), 2, 3);
    
    // Fifth row: 'Album Id: <album-id>'
    gridPane.add(albumIdObjectControl.getLabel(), 0, 4);
    gridPane.add(albumIdObjectControl.getControl(), 1, 4);
    gridPane.add(albumIdObjectControl.getStatusIndicator(), 2, 4);
    
    mainPane.getChildren().add(gridPane);
    
    mainPane.getChildren().add(playersObjectControl.getControl());

    /*
     * Front, front inside, back and label images
     */
    mainPane.getChildren().addAll(frontImagesObjectControl.getControl(), frontInsideImagesObjectControl.getControl(), backImagesObjectControl.getControl(), labelImagesObjectControl.getControl());    
    
    /*
     * Description (with Title), comments
     */
    gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);

    // First row: 'Description title: <description-title>'
    gridPane.add(descriptionTitleObjectControl.getLabel(), 0, 0);
    gridPane.add(descriptionTitleObjectControl.getControl(), 1, 0);

    // second row: 'Description: <description>'
    gridPane.add(descriptionObjectControl.getLabel(), 0, 1);
    gridPane.add(descriptionObjectControl.getControl(), 1, 1);

    mainPane.getChildren().add(gridPane);

    /*
     * 'issued on', 'soundtrack' and 'I've had on lp'.
     */
    HBox hBox = componentFactory.createHBox(10.0, 10.0);
    hBox.getChildren().addAll(soundTrackObjectControl.getControl(), soundTrackObjectControl.getStatusIndicator(), iveHadOnLpObjectControl.getControl(), iveHadOnLpObjectControl.getStatusIndicator());
    
    mainPane.getChildren().add(hBox);
    
    /*
     * MyInfo
     */
    gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'I want: <i-want> I Have On: <i-have-on>'
    gridPane.add(iWantObjectControl.getLabel(), 0, 0);
    gridPane.add(iWantObjectControl.getControl(), 1, 0);
    gridPane.add(iWantObjectControl.getStatusIndicator(), 2, 0);
    
    gridPane.add(iHaveOnObjectControl.getLabel(), 3, 0);
    gridPane.add(iHaveOnObjectControl.getControl(), 4, 0);
    gridPane.add(iHaveOnObjectControl.getStatusIndicator(), 5, 0);
    
    
    
    // Second row: 'MyComments: <my-comments>'
    gridPane.add(myCommentsObjectControl.getLabel(), 0, 1);
    gridPane.add(myCommentsObjectControl.getControl(), 1, 1);
    
    mainPane.getChildren().add(gridPane);
    
    // Album references
    albumReferencesVBox = componentFactory.createVBox(10.0, 10.0);
    mainPane.getChildren().add(albumReferencesVBox);

    /*
     * Discs
     */
    discsVBox = componentFactory.createVBox(10.0, 10.0);
    mainPane.getChildren().add(discsVBox);

    Button newDiscButton = componentFactory.createButton("Add disc", "click to add a disc to the album");
    newDiscButton.setOnAction((e) -> addNewDiscPanel(null));
    mainPane.getChildren().add(newDiscButton);

    rootPane.getChildren().add(mainPane);
  }
  
  
  /**
   * Fill the controls with values derived from the files in a folder.
   * 
   * @param albumFolderName Name of a folder that contains album tracks. This has to be an existing folder.
   */
  private void newAlbumBasedOnTrackInFolder(String albumFolderName) {
    
    String imagesFolder = Paths.get(MediaRegistry.albumInfoDirectory, "Pictures").toString();
    MediaDb importMediaDb = new DeriveAlbumInfo(MEDIA_DB_FACTORY.createMediaDb()).deriveAlbumDetails(albumFolderName, imagesFolder);
    Album importAlbum = !importMediaDb.getAlbums().isEmpty() ? importMediaDb.getAlbums().get(0) : null;
    if (importAlbum != null) {
      createObject();
      if (!handleArtistReferences(importMediaDb)) {
        return;
      }
      if (!handleTrackReferences(importMediaDb, importAlbum)) {
        return;
      }
      // TODO Use a wizard to check/handle all references (Tracks and Artists)
      fillControlsFromAlbum(importAlbum);
    }
  }
  
  /**
   * Handle the artist references in an import media database.
   * <p>
   * If an artist in the import media database doesn't exist in the media database, the user is asked whether it should be added.
   * If the user doesn't accept this, processing stops and false is returned.
   * The references to the artists in the import media database are set to the artists in the media database.
   * 
   * @param importMediaDb the import media database in which to handle the artist references.
   * @return true if all references could be updated, false otherwise.
   */
  private boolean handleArtistReferences(MediaDb importMediaDb) {
    
    Album importAlbum = !importMediaDb.getAlbums().isEmpty() ? importMediaDb.getAlbums().get(0) : null;
    
    // The artists list is a containment list, this implies that when a track is added to the mediaDb artists list, it is automatically removed from the importMediaDb artists list.
    // Therefore we use the constuction while (!importMediaDb.getArtists().isEmpty()) artist = importMediaDb.getArtists().get(0).
    while (!importMediaDb.getArtists().isEmpty()) {
      Artist artist = importMediaDb.getArtists().get(0);
      Artist artistInMediaDb = mediaDb.getArtist(artist.getName());
      
      if (artistInMediaDb == null) {
        ArtistDetailsEditor artistDetailsEditor = ArtistDetailsEditor.newInstance(customization, "New artist", mediaDbService);
        artistDetailsEditor.initModality(Modality.APPLICATION_MODAL);
        StringBuilder buf = new StringBuilder();
        buf.append("The artist ")
        .append(artist.getName())
        .append("isn't in the database yet.\nHere you can add it. If you don't, the import is aborted.");
        artistDetailsEditor.setExplanation(buf.toString());
        artistDetailsEditor.getButtonTypes().remove(ObjectEditorTemplate.NEW);
        artistDetailsEditor.setObject(artist, false, true);
        artistDetailsEditor.showAndWait();
        
        if (!mediaDb.getArtists().contains(artist)) {
          return false;  // Seems user cancelled the editor without adding the artist
        }
      }
    }
    
    return true;
  }
  
  /**
   * Handle the track references in an import media database.
   * <p>
   * If a track in the import media database doesn't exist in the media database, the user is asked whether it should be added.
   * If the user doesn't accept this, processing stops and false is returned.
   * The references to the tracks in the import media database are set to the tracks in the media database.
   * 
   * @param importMediaDb the import media database in which to handle the track references.
   * @return true if all references could be updated, false otherwise.
   */
  private boolean handleTrackReferences(MediaDb importMediaDb, Album album) {
    Artist albumArtist = album.getArtist();
    
    // The tracks list is a containment list, this implies that when a track is added to the mediaDb tracks list, it is automatically removed from the importMediaDb tracks list.
    // Therefore we use the constuction while (!importMediaDb.getTracks().isEmpty()) track = importMediaDb.getTracks().get(0).
    while (!importMediaDb.getTracks().isEmpty()) {
      Track track = importMediaDb.getTracks().get(0);
      Artist trackArtist = track.getArtist();
      if (trackArtist == null) {
        trackArtist = albumArtist;
      }
      LOGGER.severe("Track: " + track.getTitle());
      
      Track trackInMediaDb = mediaDb.getTrack(trackArtist, track.getTitle());
      
      if (trackInMediaDb == null) {
        TrackEditor trackEditor = TrackEditor.newInstance(customization, mediaDbService);
        trackEditor.initModality(Modality.APPLICATION_MODAL);
        StringBuilder buf = new StringBuilder();
        buf.append("The track '")
        .append(track.getTitle())
        .append("' isn't in the database yet.\nHere you can add it. If you don't, the import is aborted.");
        trackEditor.setExplanation(buf.toString());
        trackEditor.getButtonTypes().remove(ObjectEditorTemplate.NEW);
        trackEditor.setObject(track, false, true);
        trackEditor.showAndWait();
        
        if (!mediaDb.getTracks().contains(track)) {
          return false;  // Seems user cancelled the editor without adding the track
        }
      }            
    }
        
    return true;
  }

  /**
   * Fill all GUI controls from the details of the current album (being {@code object}).
   * <p>
   * If the {@code object} is null, all controls are cleared.
   */
  @Override
  protected void fillControlsFromObject() {
    fillControlsFromAlbum(object);
  }
    
  /**
   * Fill all GUI controls from an {@code Album}.
   * 
   * @param album
   */
  private void fillControlsFromAlbum(Album album) {
    fillControlsWithDefaultValues();

    if (album == null) {
      return;
    }
    
    albumTitleTextFieldObjectControl.setObject(album.getTitle());
    albumArtistObjectControl.setObject(album.getArtist());
    albumIssueDateObjectControl.setObject(album.getReleaseDate());
    albumIdObjectControl.setObject(album.getId());
    descriptionTitleObjectControl.setObject(album.getDescriptionTitle());
    descriptionObjectControl.setObject(album.getDescription());

    MyInfo myInfo = album.getMyInfo();
    albumTypeObjectControl.setObject(myInfo.getAlbumType());
    iWantObjectControl.setObject(myInfo.getIWant());
    iHaveOnObjectControl.setObject(myInfo.getIHaveOn());
    myCommentsObjectControl.setObject(myInfo.getMyComments());

    fillGeneralControlsFromAlbum(album);
    playersObjectControl.setObject(album.getPlayers());
    frontImagesObjectControl.setObject(new ArrayList<>(album.getImagesFront()));
    frontInsideImagesObjectControl.setObject(new ArrayList<>(album.getImagesFrontInside()));
    backImagesObjectControl.setObject(new ArrayList<>(album.getImagesBack()));
    labelImagesObjectControl.setObject(new ArrayList<>(album.getImagesLabel()));
    updateCheckBoxes(album);
    updateAlbumReferences(album);
    updateDiscsPanel(album);
  }

  /**
   * Fill all GUI controls from the details of an album.
   * <p>
   * If the album is null, all controls are cleared.
   * 
   * @param album the <code>Album</code> for which the GUI controls are updated. This value may be null, in which case all controls are cleared.
   */
  private void fillControlsFromPseudoAlbum(Album album) {
    // ToDo check how to do this. E.g. for each 'artist' check whether it is in de database.
    fillControlsWithDefaultValues();
    
    // make references to the database
    // album artist
    Artist artist = album.getArtist();
    if (artist != null) {
      Artist mediaDbArtist = mediaDb.getArtist(artist.getName());
      if (mediaDbArtist == null) {
        throw new RuntimeException("New artists not yet handled");
      }
      album.setArtist(mediaDbArtist);
    }
    
    patchImageList(album.getImagesFront());
    // Then the controls
    fillGeneralControlsFromAlbum(album);
//    redrawPlayersPane();
    frontImagesObjectControl.setObject(album.getImagesFront());
    frontInsideImagesObjectControl.setObject(album.getImagesFrontInside());
    backImagesObjectControl.setObject(album.getImagesBack());
    labelImagesObjectControl.setObject(album.getImagesLabel());

//    updateIssuedOnMediaPane();
//    redrawImagesPanel(frontImagesHBox, frontImageControls);
//    redrawImagesPanel(frontInsideImagesHBox, frontInsideImageControls);
//    redrawImagesPanel(backImagesHBox, backImageControls);
//    redrawImagesPanel(labelImagesHBox, labelImageControls);
//    updateCheckBoxes(album);
//    updateAlbumReferences(album);
//    //  updateDiscsPanel(album);
  }
  
  private void patchImageList(List<String> imageFileNames) {
    List<String> fileNames = new ArrayList<>(imageFileNames);
    imageFileNames.clear();
    for (String fileName: fileNames) {
      imageFileNames.add(FileUtils.getPathRelativeToFolder(MediaRegistry.albumInfoDirectory, fileName));
    }
  }
  
//  private void fillControlsFromAlbumInfo(AlbumInfo albumInfo) {
//    fillControlsWithDefaultValues();
//    
//    // First update the lists
//    // albumInfo has no players, so no PlayerObjectControls have to be filled in.
//    // updatePicturesForAlbum(object);  TODO not implemented yet in deriveAlbumInfo
//    
//    // Then the controls
//    fillGeneralControlsFromAlbumInfo(albumInfo);
////    redrawPlayersPane();
//    updateIssuedOnMediaPane();
//    redrawImagesPanel(frontImagesHBox, frontImageControls);
//    redrawImagesPanel(frontInsideImagesHBox, frontInsideImageControls);
//    redrawImagesPanel(backImagesHBox, backImageControls);
//    redrawImagesPanel(labelImagesHBox, labelImageControls);
//    updateCheckBoxes(null);
//    updateAlbumReferences(null);
//    updateDiscsPanelFromAlbumInfo(albumInfo);
//  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillControlsWithDefaultValues() {
    albumTypeObjectControl.setObject(AlbumType.NORMAL_ALBUM);
    albumTitleTextFieldObjectControl.setObject(null);
    albumArtistObjectControl.setObject(null);
    albumIssueDateObjectControl.setObject(null);
    albumIdObjectControl.setObject(null);
    playersObjectControl.setObject(null);
    descriptionTitleObjectControl.setObject(null);
    descriptionObjectControl.setObject(null);
    myCommentsObjectControl.setObject(null);
    iWantObjectControl.setObject(IWant.DONT_KNOW);
    iHaveOnObjectControl.setObject(null);
  }

  /**
   * Fill the general controls from the <code>album</code>.
   * 
   * @param album the <code>Album</code> for which the controls are updated. This value may be null, in which case all controls are cleared.
   */
  private void fillGeneralControlsFromAlbum(Album album) {

    if (album != null) {
      albumTitleTextFieldObjectControl.setObject(album.getTitle());

      albumArtistObjectControl.setObject(album.getArtist());

      albumIssueDateObjectControl.setObject(album.getReleaseDate());

      albumIdObjectControl.setObject(album.getId());

      descriptionTitleObjectControl.setObject(album.getDescriptionTitle());

      descriptionObjectControl.setObject(album.getDescription());

      MyInfo myInfo = album.getMyInfo();
      albumTypeObjectControl.setObject(myInfo.getAlbumType());

      iWantObjectControl.setObject(myInfo.getIWant());
      iHaveOnObjectControl.setObject(myInfo.getIHaveOn());

      myCommentsObjectControl.setObject(myInfo.getMyComments());

    }
  }

//  /**
//   * Fill the general controls from the <code>album</code>.
//   * 
//   * @param album the <code>Album</code> for which the controls are updated. This value may be null, in which case all controls are cleared.
//   */
//  private void fillGeneralControlsFromAlbumInfo(AlbumInfo albumInfo) {
//    issuedOnMediaLabels.clear();
//
//    albumTitleTextFieldObjectControl.setValue(albumInfo.albumTitle());
//
//    Artist albumArtist = mediaDb.getArtist(albumInfo.albumArtist());
//    albumArtistObjectControl.setValue(albumArtist);
//    albumIssueDateTextField.setValue(albumInfo.albumDate());
//  }
  
  
  
    
  /**
   * Update the checkboxes.
   */
  private void updateCheckBoxes(Album album) {
    if (album != null) {      
      soundTrackObjectControl.setObject(album.isSoundtrack());
      iveHadOnLpObjectControl.setObject(album.getMyInfo().isIveHadOnLP());
    } else {
      soundTrackObjectControl.setObject(false);
      iveHadOnLpObjectControl.setObject(false);
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
          albumReferencesVBox.getChildren().add(albumReference.getControl());
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
    
    for (ObjectEditPanelTemplate<Disc> discPanel: discPanels) {
      objectControlsGroup.removeObjectControlGroup(discPanel.getObjectControlsGroup());
    }
    
    discPanels.clear();

    if (album != null) {
      discsObjectControl.setObject(new ArrayList<Disc>(album.getDiscs()));
      for (Disc disc: album.getDiscs()) {
        addNewDiscPanel(disc);
      }
    }
  }
  
  private void addNewDiscPanel(Disc disc) {
    DiscEditPanel discEditPanel = DiscEditPanel.newInstance(customization, mediaDbService);
    //      discEditPanel.createObject();
    discEditPanel.setId("Disc panel");
    discPanels.add(discEditPanel);
    objectControlsGroup.addObjectControlGroup(discEditPanel.getObjectControlsGroup());
    discsVBox.getChildren().add(discEditPanel.getControl());
    if (disc != null) {
      discEditPanel.setObject(disc);
    } else {
      //        discEditPanel.createObject();
      discsObjectControl.getValue().add(discEditPanel.getValue());
    }
  }
  
  /**
   * Update the discs panel (<code>discTracksVBox</code>).
   * <p>
   * The panel is cleared and then for each disc (of the <code>AlbumInfo</code> a DiscPanel is added to the panel.
   */
  private void updateDiscsPanelFromAlbumInfo(AlbumInfo albumInfo) {
    discsVBox.getChildren().clear();
    
    for (Object discPanel: discPanels) {
//      objectControlsGroup.removeObjectInput(discPanel);
    }

    discPanels.clear();

      for (DiscInfo discInfo: albumInfo.discs()) {
        addNewDiscPanelForDiscInfo(discInfo);
      }
  }
  
  private void addNewDiscPanelForDiscInfo(DiscInfo discInfo) {
//    switch (albumTypeObjectControl.getValue()) {
//    case NORMAL_ALBUM:
//      Disc disc = MEDIA_DB_FACTORY.createDisc();
//      NormalDiscPanel normalDiscPanel = new NormalDiscPanel(customization, disc, mediaDb);
//      normalDiscPanel.fillControlsFromDiscInfo(discInfo);
//      discPanels.add(normalDiscPanel);
//      objectControlsGroup.addObjectControl(normalDiscPanel);
//      discsVBox.getChildren().add(normalDiscPanel.getControl());
//      break;
//      
//    default:
////      DiscPanelAbstract discPanel = new DiscPanel(customization, disc, null, mediaDb);
////      discPanels.add(discPanel);
////      discsVBox.getChildren().add(discPanel.ocGetControl());
//      break;
//    }
    
  }
    
  /**
   * Create the panel with the import controls.
   * <p>
   * The bordered panel has a description label, a TextField for the folder to import from,
   * a button to open a FolderChooser and a button to perform the import.
   * 
   * @return the created panel with the import controls.
   */
  private Node createImportControlsPanel() {
    HBox outerHBox = componentFactory.createHBox(10.0, 12.0);
    HBox hBox = componentFactory.createHBox(10.0, 12.0);
    hBox.setBorder(componentFactory.getRectangularBorder());
   
    // Import folder selection: Label, textField, Chooser button, import button
    ObjectControlFolderSelecter folderSelecter = componentFactory.createFolderSelecter(600, "Folder to import album information from", "Select source folder", "Open source folder chooser", "Source folder", false);
    
    if (MediaRegistry.albumImportDirectory != null) {
      folderSelecter.setInitialFolderProvider(() -> MediaRegistry.albumImportDirectory);
    }
    
    Label label = componentFactory.createLabel("Derive album details from:");
    hBox.getChildren().add(label);
    
    hBox.getChildren().add(folderSelecter.getControl());
    hBox.getChildren().add(folderSelecter.getFolderChooserButton());
    
    Button button = componentFactory.createButton("Derive album details", "Derive the album details from this folder");
    button.setOnAction(e -> newAlbumBasedOnTrackInFolder(folderSelecter.getAbsolutePath()));
    folderSelecter.addListener((o) -> updateDeriveAlbumDetailsButtonStatus(button, folderSelecter));
    updateDeriveAlbumDetailsButtonStatus(button, folderSelecter);
    hBox.getChildren().add(button);
    
    outerHBox.getChildren().add(hBox);
        
    return outerHBox;
  }
  
  /**
   * Enable/disable "Derive album details" button based on whether a valid directory is selected.
   * 
   * @param button the {@code Button} to be enabled/disabled.
   * @param folderSelecter the {@code ObjectControlFolderSelecter} providing a directory.
   */
  private void updateDeriveAlbumDetailsButtonStatus(Button button, ObjectControlFolderSelecter folderSelecter) {
    File file = folderSelecter.getValue();
    if (file == null) {
      button.setDisable(true);
    } else {
      button.setDisable(!Files.exists(file.toPath()));
    }
  }

  /**
   * Derive the information of an album from a folder (containing the tracks of an album).
   * <p>
   * 
   * @param sourceFolderName the folder from which the information is to be derived.
   */
  private void deriveAlbumDetails(String sourceFolderName) {

//    // Try to find picture files.
//    try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceFolder)) {
//      for (Path path: stream) {
//        String fileName = path.getFileName().toString();
//        LOGGER.severe("Handling path: " + fileName);
//        if (!Files.isDirectory(path)) {
//          String[] fileNameAndExtension = FileUtils.getFileNameAndExtension(path);
//          LOGGER.severe("filename: " + fileNameAndExtension[0] + ", extension: " + fileNameAndExtension[1]);
//          
//          switch (fileNameAndExtension[1]) {
//          case "jpg":
//            if (fileNameAndExtension[0].equalsIgnoreCase("front")) {
//              album.getImagesFront().add(path.toAbsolutePath().toString());
//            }
//            if (fileNameAndExtension[0].equalsIgnoreCase("back")) {
//              album.getImagesBack().add(path.toAbsolutePath().toString());
//            }
//            if (fileNameAndExtension[0].equalsIgnoreCase("inside")) {
//              album.getImagesFrontInside().add(path.toAbsolutePath().toString());
//            }
//            break;
//            
//          default:
//            break;
//          }
//          
//        } else {
//          LOGGER.severe("Skipping folder: " + fileName);
//        }
//      }
//    } catch (IOException | DirectoryIteratorException x) {
//      // IOException can never be thrown by the iteration.
//      System.err.println(x);
//    }
    
    
//    if (!album.getImagesFront().isEmpty()  ||  !album.getImagesFrontInside().isEmpty()  ||
//        !album.getImagesBack().isEmpty()  ||  !album.getImagesLabel().isEmpty()) {
//      ImportAlbumPicturesWindow importAlbumPicturesWindow = new ImportAlbumPicturesWindow(getCustomization(), album);
//      importAlbumPicturesWindow.showAndWait();
//    }
//    
//    Album existingAlbum = mediaDb.getAlbum(album.getReleaseDate(), album.getArtist(), album.getTitle());
//    if (existingAlbum != null) {
//      LOGGER.severe("Album already exists in the media database");
//    }
//    
//    fillControlsFromObject();  // TODO
  }
  
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createObject() {
    object = MEDIA_DB_FACTORY.createAlbum();
  }
      
  /**
   * Update an album from the information in the controls.
   * 
   * @param object the Album to be updated
   * @throws AlbumDetailsException in case one or more controls has an illegal value.
   */
  @Override
  protected void updateObjectFromControls() throws ObjectEditorException {
    
    List<String> problems = new ArrayList<>();
    
    // Album title
    String titleText = albumTitleTextFieldObjectControl.getValue();
    if (titleText == null) {
      problems.add("Album title may not be empty");
    }
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_Title(), titleText);
    
    // Artist
    Artist artist = albumArtistObjectControl.getValue();
    if (artist == null) {
      problems.add("There is no artist selected");
    }
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_Artist(), artist);

    // Issue date
    FlexDate releaseDate = albumIssueDateObjectControl.getValue();
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_ReleaseDate(), releaseDate);
    
    // Album Id
    String albumIdText = albumIdObjectControl.getValue();
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_Id(), albumIdText);
    
    // Players
    List<Player> players = playersObjectControl.getValue();

    // Update the complete list of players if there is any change.
    boolean playersChanged = false;
    if (players.size() != object.getPlayers().size()) {
      playersChanged = true;
    }

    if (!playersChanged) {
      for (int i = 0; i < players.size(); i++) {
        Player currentPlayer = object.getPlayers().get(i);
        String currentInstruments = StringUtil.stringCollectionToCommaSeparatedStrings(currentPlayer.getInstruments());
        Player newPlayer = players.get(i);
        String newInstruments = StringUtil.stringCollectionToCommaSeparatedStrings(newPlayer.getInstruments());
        if (!newPlayer.getArtist().equals(currentPlayer.getArtist())  ||
            !newInstruments.equals(currentInstruments)) {
          playersChanged = true;
          break;
        }
      }
    }

    if (playersChanged) {
      object.getPlayers().clear();

      for (Player newPlayer: players) {
        Player player = MEDIA_DB_FACTORY.createPlayer();
        Artist newArtist = mediaDb.getArtist(newPlayer.getArtist().getName());
        player.setArtist(newArtist);
        player.getInstruments().addAll(newPlayer.getInstruments());

        object.getPlayers().add(player);
      }
    }

    // Images front
    setImagesIfChanged(object.getImagesFront(), frontImagesObjectControl);

    // Images front inside
    setImagesIfChanged(object.getImagesFrontInside(), frontInsideImagesObjectControl);

    // Images back
    setImagesIfChanged(object.getImagesBack(), backImagesObjectControl);

    // Images label
    setImagesIfChanged(object.getImagesLabel(), labelImagesObjectControl);

    // Description title
    String descriptionTitleText = descriptionTitleObjectControl.getValue();
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_DescriptionTitle(), descriptionTitleText);

    // Description
    String descriptionText = descriptionObjectControl.getValue();
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_Description(), descriptionText);

    /*
     * MyInfo
     */
    // MyInfo shall always be there, because albumType always has to be set.
    MyInfo myInfo = object.getMyInfo();
    
    EmfUtil.setFeatureValue(myInfo, MEDIA_DB_PACKAGE.getMyInfo_AlbumType(), albumTypeObjectControl.getValue());
    
//    if (myInfoNeeded) {
//      if (albumMyInfo == null) {
//        myInfoToFill = MEDIA_DB_FACTORY.createMyInfo();
//      } else {
//        myInfoToFill = albumMyInfo;
//      }
//    } else {
//      if (albumMyInfo != null) {
//        EmfUtil.setFeatureValue(albumMyInfo, MEDIA_DB_PACKAGE.getMyInfo_IWant(), IWant.NOT_SET);
//        EmfUtil.setFeatureValue(albumMyInfo, MEDIA_DB_PACKAGE.getMyInfo_IveHadOnLP(), false);
//        EmfUtil.setFeatureValue(albumMyInfo, MEDIA_DB_PACKAGE.getMyInfo_MyComments(), null);
////        album.setMyInfo(null); TODO in future remove MyInfo if nothing set
//      }
//    }

//    if (myInfoNeeded) {
      IWant iWant = iWantObjectControl.getValue();
      if (iWant == null) {
        iWant = IWant.NOT_SET;
      } 
      EmfUtil.setFeatureValue(myInfo, MEDIA_DB_PACKAGE.getMyInfo_IWant(), iWant);
      
      EmfUtil.setFeatureValue(myInfo, MEDIA_DB_PACKAGE.getMyInfo_IHaveOn(), iHaveOnObjectControl.getValue());

      
      EmfUtil.setFeatureValue(myInfo, MEDIA_DB_PACKAGE.getMyInfo_IveHadOnLP(), iveHadOnLpObjectControl.getValue());
      
      String myComments = myCommentsObjectControl.getValue();
      EmfUtil.setFeatureValue(myInfo, MEDIA_DB_PACKAGE.getMyInfo_MyComments(), myComments);
      
//      if (albumMyInfo == null) {
//        object.setMyInfo(myInfoToFill);
//      }
//    }
    
    List<Disc> discs = object.getDiscs();
    for (ObjectEditPanelTemplate<Disc> discPanel: discPanels) {
      discPanel.updateObject();
      Disc disc = discPanel.getValue();
      if (!discs.contains(disc)) {
        discs.add(disc);
      }
      
//      Disc disc = discPanel.getDisc();
//      if (editMode == EditMode.NEW) {
//        object.getDiscs().add(disc);
//      }
//      discPanel.getValue();
//      updateDiscFromDiscPanel(discPanel);
    }
    
    if (problems.size() != 0) {
      throw new ObjectEditorException(problems);
    }
    
    LOGGER.severe("Album: " + object.toString());
  }
  
  /**
   * Update a Disc from the information in a DiscPanel.
   * 
   * @param discPanel the DiscPanel to update the disc from.
   */
  private void updateDiscFromDiscPanel(DiscEditPanel discPanel) {
    Disc disc = discPanel.getValue();
    if (disc == null) {
      disc = MEDIA_DB_FACTORY.createDisc();
    }
    EmfUtil.setFeatureValue(disc, MEDIA_DB_PACKAGE.getDisc_Title(), discPanel.getDiscTitle());

//    for (TrackReferenceAndMyTrackInfoControls trackReferencePanel: discPanel.getTrackReferencePanels()) {
//      updateTrackReferenceFromTrackReferencePanel(trackReferencePanel);
//    }

  }

//  /**
//   * Update a TrackReference based on TrackReferenceAndMyTrackInfoControls
//   *  
//   * @param trackReferencePanel the TrackReferenceAndMyTrackInfoControls to handle.
//   */
//  private void updateTrackReferenceFromTrackReferencePanel(TrackReferenceAndMyTrackInfoControls trackReferencePanel) {
//    TrackReference trackReference = trackReferencePanel.getTrackReference();
//
//    // If the Track has changed, the original Track that was referred to may become obsolete. If so we delete it.
//    Track originalTrack = trackReference.getTrack();
//    EmfUtil.setFeatureValue(trackReference, MEDIA_DB_PACKAGE.getTrackReference_Track(), trackReferencePanel.getTrack());
//    if (originalTrack.getReferredBy().isEmpty()) {
//      mediaDb.getTracks().remove(originalTrack);
//    }
//    
//    EmfUtil.setFeatureValue(trackReference, MEDIA_DB_PACKAGE.getTrackReference_BonusTrack(), trackReferencePanel.getBonusTrack());
//    
//    boolean isMyTrackInfoNeeded = isMyTrackInfoNeeded(trackReferencePanel);
//    if (isMyTrackInfoNeeded) {
//      EmfUtil.setFeatureValue(trackReference.getMyTrackInfo(), MEDIA_DB_PACKAGE.getMyTrackInfo_Collection(), trackReferencePanel.getCollection());
//      EmfUtil.setFeatureValue(trackReference.getMyTrackInfo(), MEDIA_DB_PACKAGE.getMyTrackInfo_IWant(), trackReferencePanel.getIWant());
//    }
//  }
//
//  private boolean isMyTrackInfoNeeded(TrackReferenceAndMyTrackInfoControls trackReferencePanel) {
//    if (trackReferencePanel.getCollection() != goedegep.media.mediadb.model.Collection.NOT_SET) {
//      return true;
//    }
//    
//    if (trackReferencePanel.getIWant() != IWant.NOT_SET) {
//      return true;
//    }
//    
//    return false;
//  }

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
        
    Collection<EStructuralFeature.Setting> settings = EcoreUtil.UsageCrossReferencer.find(eObject, EmfUtil.getRoot(eObject));
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
  private void setImagesIfChanged(List<String> currentImages, ObjectControlForImages objectControlForImages) {
    List<String> newImageFiles = objectControlForImages.getValue();
    
    // Update the complete list of images if there is any change.
    boolean imagesChanged = false;
    if (newImageFiles.size() != currentImages.size()) {
      imagesChanged = true;
    }
    
    if (!imagesChanged) {
      for (int i = 0; i < newImageFiles.size(); i++) {
        String newImageFileName = newImageFiles.get(i);
        String currentImageFilename = currentImages.get(i);
        if (!newImageFileName.equals(currentImageFilename)) {
          imagesChanged = true;
          break;
        }
      }
    }
    
    if (imagesChanged) {
      currentImages.clear();
      for (String newImageFile: newImageFiles) {
        currentImages.add(newImageFile);
      }
    }
    
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
      Artist artist = albumArtistObjectControl.getValue();
      String artistName = artist != null ? artist.getName() : "no artist";
      String title = albumTitleTextFieldObjectControl.getValue();
      if (title == null) {
        title = "no title";
      }
      buf.append(artistName).append(" - ").append(title);

    } else {
      if (changesInInput()) {
        buf.append("*");
      }
      
      buf.append("Edit album: ");
      buf.append(object.getArtistAndTitle());
    }
    
    setTitle(buf.toString());
  }
  
}





