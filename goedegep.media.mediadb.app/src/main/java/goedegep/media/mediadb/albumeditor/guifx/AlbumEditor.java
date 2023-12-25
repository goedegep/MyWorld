package goedegep.media.mediadb.albumeditor.guifx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.jfx.objectcontrols.ObjectControlEnumComboBox;
import goedegep.jfx.objectcontrols.ObjectControlFlexDate;
import goedegep.jfx.objectcontrols.ObjectControlFolderSelecter;
import goedegep.jfx.objectcontrols.ObjectControlImageFile;
import goedegep.jfx.objectcontrols.ObjectControlMultiLineString;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.jfx.objecteditor.EditMode;
import goedegep.jfx.objecteditor.ObjectEditorException;
import goedegep.jfx.objecteditor.ObjectEditorTemplate;
import goedegep.media.app.MediaRegistry;
import goedegep.media.mediadb.app.ArtistStringConverterAndChecker;
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
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
  
  private CustomizationFx customization;
  private ComponentFactoryFx componentFactory;

  /**
   * The Media Database.
   */
  private MediaDb mediaDb;
  
  private Map<Track, Path> trackDiscLocationMap;
  private ArtistStringConverterAndChecker artistStringConverterAndChecker;
  
    
  /**
   * Panel with the controls for importing information from a folder with album tracks.
   */
  private Node importControlsPanel;
  
  /**
   * Album type control
   */
  ObjectControlEnumComboBox<AlbumType> albumTypeObjectControlEnumComboBox;
  
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
   * Shows the tracks per disc in tables
   */
  private VBox discsVBox;
  
  /**
   * List of DiscPanels
   */
  private List<DiscPanelAbstract> discPanels;
    
 
  private Stage currentLargePictureStage;
  
//  /**
//   * Constructor.
//   * <p>
//   * Use this constructor to open the editor for creating a new album.
//   * 
//   * @param customization the GUI customization.
//   */
//  public AlbumEditor(CustomizationFx customization, MediaDb mediaDb, Map<Track, Path> trackDiscLocationMap) {
//    this(customization, mediaDb, trackDiscLocationMap, null);
//  }

  /**
   * Constructor.
   * <p>
   * Use this constructor to edit an existing album.
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
    
    installChangeListernerForArtists();
//    updateContainerArtistComboBox();
    
//    objectControlsGroup.addListener((o) -> updateAddUpdateAndNewButtonsPanel());
    
//    setObject(album);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureEditor() {
    setAddObjectTexts("Add album", "Add the album to the media database");
    setUpdateObjectTexts("Update album", "Update the current album");
    setNewObjectTexts("New", "Clear the control to start entering new Album data");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createControls() {
    albumTypeObjectControlEnumComboBox = componentFactory.createObjectControlEnumComboBox(AlbumType.NORMAL_ALBUM, null, true, "Select the type of album. This value determines how the discs/tracks are to be filled in");
    albumTitleTextFieldObjectControl = componentFactory.createObjectControlTextField(null, null, 600, false, "The album title is a mandatory field");
    albumArtistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(artistStringConverterAndChecker, null, 300, false, "Enter the album artist");
    albumIssueDateTextField = componentFactory.createObjectControlFlexDate(null, 600, false, "Optional first issue date of the album");
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
      MediumType selectedMediumType = mediaComboBox.getValue();
      if (selectedMediumType != null  &&  selectedMediumType != MediumType.NOT_SET) {
        Label label = componentFactory.createLabel(selectedMediumType.getLiteral());
        issuedOnMediaLabels.add(label);
        updateIssuedOnMediaPane();
      }
    });
    issuedOnMediaLabels = new ArrayList<>();
    
    isCompilationAlbumCheckBox = componentFactory.createCheckBox("compilation album", false);
    isSoundTrackCheckBox = componentFactory.createCheckBox("soundtrack", false);
    iveHadOnLpCheckBox = componentFactory.createCheckBox("I've had on lp", false);

    iWantComboBox = componentFactory.createObjectControlEnumComboBox(IWant.NOT_SET, IWant.NOT_SET, MEDIA_DB_PACKAGE.getIWant(), true, "Select whether you want this album or not");
    
    myCommentsTextArea = componentFactory.createObjectControlMultiLineString(null, 400, true, "Enter an optional personal comment about the album");
    
    discPanels = new ArrayList<>();
    
    objectControlsGroup.addObjectControls(
        albumTitleTextFieldObjectControl,
        albumArtistObjectControl,
        albumIssueDateTextField,
        albumIdTextField,
        descriptionTitleTextField,
        descriptionTextArea,
        iWantComboBox
    );
    
    updateContainerArtistComboBox();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createAttributeEditDescriptors() {
    // No action. This implementation doesn't use edit descriptors.
  }
  
  /**
   * Create the GUI.
   */
  @Override
  protected void createEditPanel(VBox rootPane) {

    /*
     * Panel is a VBox.
     * Starts with import controls
     * Followed all album details
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
    Label label = componentFactory.createLabel("Album type:");
    gridPane.add(label, 0, 0);
    ChangeListener<AlbumType> cl = new ChangeListener<>() {

      @Override
      public void changed(ObservableValue<? extends AlbumType> observable, AlbumType oldValue, AlbumType newValue) {
        LOGGER.severe("AlbumType: " + newValue);
        
      }
      
    };
    albumTypeObjectControlEnumComboBox.getControl().getSelectionModel().selectedItemProperty().addListener(cl);
    gridPane.add(albumTypeObjectControlEnumComboBox.getControl(), 1, 0);
    gridPane.add(albumTypeObjectControlEnumComboBox.getStatusIndicator(), 2, 0);
    
    // Second row: 'Album: <album-title>'
    label = componentFactory.createLabel("Title:");
    gridPane.add(label, 0, 1);
    gridPane.add(albumTitleTextFieldObjectControl.getControl(), 1, 1);
    gridPane.add(albumTitleTextFieldObjectControl.getStatusIndicator(), 2, 1);

    // Third row: 'Artist: <artist>'   'New artist' button
    label = componentFactory.createLabel("Artist:");
    gridPane.add(label, 0, 2);
    gridPane.add(albumArtistObjectControl.getControl(), 1, 2);
    gridPane.add(albumArtistObjectControl.getStatusIndicator(), 2, 2);
    Button newArtistButton = componentFactory.createButton("New artist", "The artist of the album has to be selected from the list of known artists. With this button you can add a new artist to the database");
    newArtistButton.setOnAction(e -> (new ArtistDetailsEditor(getCustomization(), "New artist", mediaDb)).show());
    gridPane.add(newArtistButton, 3, 2);
   
    // Fourth row: 'Issue date: <album-issue-date>'
    label = componentFactory.createLabel("Issued:");
    gridPane.add(label, 0, 3);
    gridPane.add(albumIssueDateTextField.getControl(), 1, 3);
    gridPane.add(albumIssueDateTextField.getStatusIndicator(), 2, 3);
    
    // Fifth row: 'Album Id: <album-id>'
    label = componentFactory.createLabel("Album Id:");
    gridPane.add(label, 0, 4);
    gridPane.add(albumIdTextField.getControl(), 1, 4);
    gridPane.add(albumIdTextField.getStatusIndicator(), 2, 4);
    
    // Sixth row: 'Collaborating Artists:'
    label = componentFactory.createLabel("Collaborating artists:");
    gridPane.add(label, 0, 5);
    playersGridPane = componentFactory.createGridPane(10.0, 10.0);
    gridPane.add(playersGridPane, 1, 4, 2, 5);
    
    mainPane.getChildren().add(gridPane);
    
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
      objectControlsGroup.addObjectControl(objectControlImageFile);
      objectControlImageFile.selectNewFile();
      redrawImagesPanel(frontImagesHBox, frontImageControls);
    });
    albumImagesControlsBox.getChildren().add(addImageButton);
    albumImagesPanel.getChildren().add(albumImagesControlsBox);
    albumImagesPanel.getChildren().add(frontImagesHBox);
    mainPane.getChildren().add(albumImagesPanel);
    
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
      objectControlsGroup.addObjectControl(objectControlImageFile);
      objectControlImageFile.selectNewFile();
      redrawImagesPanel(frontInsideImagesHBox, frontInsideImageControls);
    });
    albumImagesControlsBox.getChildren().add(addImageButton);
    albumImagesPanel.getChildren().add(albumImagesControlsBox);
    albumImagesPanel.getChildren().add(frontInsideImagesHBox);
    mainPane.getChildren().add(albumImagesPanel);
    
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
      objectControlsGroup.addObjectControl(objectControlImageFile);
      objectControlImageFile.selectNewFile();
      redrawImagesPanel(backImagesHBox, backImageControls);
    });
    albumImagesControlsBox.getChildren().add(addImageButton);
    albumImagesPanel.getChildren().add(albumImagesControlsBox);
    albumImagesPanel.getChildren().add(backImagesHBox);
    mainPane.getChildren().add(albumImagesPanel);
    
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
      objectControlsGroup.addObjectControl(objectControlImageFile);
      objectControlImageFile.selectNewFile();
      redrawImagesPanel(labelImagesHBox, labelImageControls);
    });
    albumImagesControlsBox.getChildren().add(addImageButton);
    albumImagesPanel.getChildren().add(albumImagesControlsBox);
    albumImagesPanel.getChildren().add(labelImagesHBox);
    mainPane.getChildren().add(albumImagesPanel);
    
    /*
     * Description (with Title), comments
     */
    gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'Description title: <description-title>'
    label = componentFactory.createLabel("Description title:");
    gridPane.add(label, 0, 0);
    gridPane.add(descriptionTitleTextField.getControl(), 1, 0);
    
    // second row: 'Description: <description>'
    label = componentFactory.createLabel("Description:");
    gridPane.add(label, 0, 1);
    gridPane.add(descriptionTextArea.getControl(), 1, 1);
    
    // Third row: 'Issued on: <medium-1> ... <medium-n> <medium-select-combobox> <add-medium-button>    
    label = componentFactory.createLabel("Issued on:");
    gridPane.add(label, 0, 2);
    issuedOnMediaPane = componentFactory.createHBox(10.0);
    gridPane.add(issuedOnMediaPane, 1, 2, 2, 4);
    
    mainPane.getChildren().add(gridPane);
    
    /*
     * Checkboxes for 'compilation album', 'soundtrack' and 'I've had on lp'.
     */
    HBox hBox = componentFactory.createHBox(10.0, 10.0);
    hBox.getChildren().addAll(isCompilationAlbumCheckBox, isSoundTrackCheckBox, iveHadOnLpCheckBox);
    
    mainPane.getChildren().add(hBox);
    
    /*
     * MyInfo
     */
    gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'I want: <i-want>'
    label = componentFactory.createLabel("I want:");
    gridPane.add(label, 0, 0);
    gridPane.add(iWantComboBox.getControl(), 1, 0);
    gridPane.add(iWantComboBox.getStatusIndicator(), 2, 0);
    
    // Second row: 'MyComments: <my-comments>'
    label = componentFactory.createLabel("My comments:");
    gridPane.add(label, 0, 1);
    gridPane.add(myCommentsTextArea.getControl(), 1, 1);
    
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


//    /*
//     * Control buttons panel on the bottom
//     */
//    mainPane.getChildren().add(addUpdateAndNewButtonsPanel);
    rootPane.getChildren().add(mainPane);
    
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
//        LOGGER.info("Notification received: " + EmfUtil.printNotification(notification, false));
        super.notifyChanged(notification);
        
        if (notification.isTouch()  ||  notification.getEventType() == Notification.REMOVING_ADAPTER) {
          // no action
          return;
        }
        
        if (notification.getFeature() == null  ||  !notification.getFeature().equals(MediadbPackage.eINSTANCE.getMediaDb_Artists())) {
          return;
        }
        
        updateContainerArtistComboBox();
        
        if (notification.getEventType() == Notification.ADD) {
          albumArtistObjectControl.setValue((Artist) notification.getNewValue());
        }
      }

    };
    
    mediaDb.eAdapters().add(adapter);
  }
  
  private void newAlbumBasedOnTrackInFolder(String albumFolderName) {
    if (albumFolderName == null) {
      return;
    }
    
    String imagesFolder = Paths.get(MediaRegistry.albumInfoDirectory, "Pictures").toString();
    AlbumInfo albumInfo = DeriveAlbumInfo.deriveAlbumDetails(albumFolderName, imagesFolder);
    if (albumInfo != null) {
      fillControlsFromAlbumInfo(albumInfo);
    }
  }

    
  /**
   * Update the albumArtistObjectControl, in case there is a change in the list of artists.
   */
  private void updateContainerArtistComboBox() {
    Artist currentSelectedArtist = albumArtistObjectControl.getValue();
    albumArtistObjectControl.setOptions(mediaDb.getArtists());
    albumArtistObjectControl.setValue(currentSelectedArtist);
  }
    
  /**
   * Fill all GUI controls from the details of an album.
   * <p>
   * If the album is null, all controls are cleared.
   * 
   * @param album the <code>Album</code> for which the GUI controls are updated. This value may be null, in which case all controls are cleared.
   */
  @Override
  protected void fillControlsFromObject() {
    fillControlsWithDefaultValues();
    
    // First update the lists
    fillPlayersFromAlbum(object);
    updatePicturesForAlbum(object);
    
    // Then the controls
    fillGeneralControlsFromAlbum(object);
    redrawPlayersPane();
    updateIssuedOnMediaPane();
    redrawImagesPanel(frontImagesHBox, frontImageControls);
    redrawImagesPanel(frontInsideImagesHBox, frontInsideImageControls);
    redrawImagesPanel(backImagesHBox, backImageControls);
    redrawImagesPanel(labelImagesHBox, labelImageControls);
    updateCheckBoxes(object);
    updateAlbumReferences(object);
    updateDiscsPanel(object);
  }
  
  private void fillControlsFromAlbumInfo(AlbumInfo albumInfo) {
    fillControlsWithDefaultValues();
    
    // First update the lists
    // albumInfo has no players, so no PlayerObjectControls have to be filled in.
    // updatePicturesForAlbum(object);  TODO not implemented yet in deriveAlbumInfo
    
    // Then the controls
    fillGeneralControlsFromAlbumInfo(albumInfo);
    redrawPlayersPane();
    updateIssuedOnMediaPane();
    redrawImagesPanel(frontImagesHBox, frontImageControls);
    redrawImagesPanel(frontInsideImagesHBox, frontInsideImageControls);
    redrawImagesPanel(backImagesHBox, backImageControls);
    redrawImagesPanel(labelImagesHBox, labelImageControls);
    updateCheckBoxes(null);
    updateAlbumReferences(null);
    updateDiscsPanelFromAlbumInfo(albumInfo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fillControlsWithDefaultValues() {
    albumTypeObjectControlEnumComboBox.setValue(AlbumType.NORMAL_ALBUM);
    albumTitleTextFieldObjectControl.setValue(null);
    albumArtistObjectControl.setValue(null);
    albumIssueDateTextField.setValue(null);
    albumIdTextField.setValue(null);
    descriptionTitleTextField.setValue(null);
    descriptionTextArea.setValue(null);
    myCommentsTextArea.setValue(null);
    iWantComboBox.ociSetValue(IWant.DONT_KNOW);
  }

  /**
   * Fill the general controls from the <code>album</code>.
   * 
   * @param album the <code>Album</code> for which the controls are updated. This value may be null, in which case all controls are cleared.
   */
  private void fillGeneralControlsFromAlbum(Album album) {
    issuedOnMediaLabels.clear();

    if (album != null) {
      if (album.getMyInfo().getAlbumType() != null) {
        albumTypeObjectControlEnumComboBox.setValue(album.getMyInfo().getAlbumType());
      }
      albumTitleTextFieldObjectControl.setValue(album.getTitle());

      albumArtistObjectControl.setValue(album.getArtist());

      albumIssueDateTextField.setValue(album.getReleaseDate());

      albumIdTextField.setValue(album.getId());

      descriptionTitleTextField.setValue(album.getDescriptionTitle());

      descriptionTextArea.setValue(album.getDescription());

      for (MediumType mediumType: album.getIssuedOnMediums()) {
        Label label = componentFactory.createLabel(mediumType.getLiteral());
        issuedOnMediaLabels.add(label);
      }

      if (album.isSetMyInfo()) {
        MyInfo myInfo = album.getMyInfo();

        iWantComboBox.setValue(myInfo.getIWant());

        myCommentsTextArea.setValue(myInfo.getMyComments());
      }

    } else {
      albumTitleTextFieldObjectControl.setValue(null);
      albumArtistObjectControl.setValue(null);
      albumIssueDateTextField.setValue(null);
      albumIdTextField.setValue(null);
      descriptionTitleTextField.setValue(null);
      descriptionTextArea.setValue(null);
      myCommentsTextArea.setValue(null);
    }
  }

  /**
   * Fill the general controls from the <code>album</code>.
   * 
   * @param album the <code>Album</code> for which the controls are updated. This value may be null, in which case all controls are cleared.
   */
  private void fillGeneralControlsFromAlbumInfo(AlbumInfo albumInfo) {
    issuedOnMediaLabels.clear();

    albumTitleTextFieldObjectControl.setValue(albumInfo.albumTitle());

    Artist albumArtist = mediaDb.getArtist(albumInfo.albumArtist());
    albumArtistObjectControl.setValue(albumArtist);
    albumIssueDateTextField.setValue(albumInfo.albumDate());
  }

  /**
   * Fill the PlayerObjectControls for the Players of an Album.
   * 
   * @param album for the Players of this {@code Album} controls will be filled.
   */
  private void fillPlayersFromAlbum(Album album) {
    for (PlayerObjectControl playerObjectControl: playerObjectControls) {
      objectControlsGroup.removeObjectInput(playerObjectControl);
    }
    
    playerObjectControls.clear();
    
    if (album != null) {
      for (Player player: album.getPlayers()) {
        PlayerObjectControl playerObjectControl = new PlayerObjectControl(customization, mediaDb);
        playerObjectControl.setValue(player);
//        AutoCompleteTextFieldObjectInput<Artist> artistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(artistStringConverterAndChecker, null, 300, false, "Enter a player");
//        artistObjectControl.setObjectValue(player.getArtist());

//        TextField playerInstrumentTextField = componentFactory.createTextField(300, "A comma separated list of instruments");
//        playerInstrumentTextField.setText(StringUtil.stringCollectionToCommaSeparatedStrings(player.getInstruments()));

//        Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> tuplet = createPlayerTuplet(player.getArtist(), player.getInstruments());
        objectControlsGroup.addObjectControl(playerObjectControl);
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
    artistObjectControl.setValue(artist);
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
        objectControlImageFile.setValue(new File(MediaRegistry.albumInfoDirectory + File.separator + imageFileName));
        frontImageControls.add(objectControlImageFile);
      }
      
      for (String imageFileName: album.getImagesFrontInside()) {
        ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
        objectControlImageFile.setValue(new File(MediaRegistry.albumInfoDirectory + File.separator + imageFileName));
        frontInsideImageControls.add(objectControlImageFile);
      }
      
      for (String imageFileName: album.getImagesBack()) {
        ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
        objectControlImageFile.setValue(new File(MediaRegistry.albumInfoDirectory + File.separator + imageFileName));
        backImageControls.add(objectControlImageFile);
      }
      
      for (String imageFileName: album.getImagesLabel()) {
        ObjectControlImageFile objectControlImageFile = new ObjectControlImageFile(customization);
        objectControlImageFile.setValue(new File(MediaRegistry.albumInfoDirectory + File.separator + imageFileName));
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
      playersGridPane.add(playerObjectControl.getArtistObjectControl().getControl(), 0, row);
//      AutoCompleteTextFieldObjectInput<Artist> playerNameTextField = tuplet.getObject1();
//      playersGridPane.add(playerNameTextField, 0, row);

      playersGridPane.add(playerObjectControl.getPlayerInstrumentTextField().getControl(), 1, row);
//      TextField playerInstrumentTextField = tuplet.getObject2();
//      playersGridPane.add(playerInstrumentTextField, 1, row);
      
      playersGridPane.add(playerObjectControl.getStatusIndicator(), 2, row);

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
      objectControlsGroup.addObjectControl(playerObjectControl);
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
    
    issuedOnMediaPane.getChildren().addAll(mediaComboBox.getControl(), addMediumTypeButton);
  }
  
  private void redrawImagesPanel(HBox imagesBox, List<ObjectControlImageFile> objectControlsImageFile) {
    LOGGER.info("=>");
    
    imagesBox.getChildren().clear();
    
    for (ObjectControlImageFile objectControlImageFile: objectControlsImageFile) {
      StackPane stackPane = objectControlImageFile.getControl();
      LOGGER.severe("Redrawing: " + objectControlImageFile.getValue());
      Button deleteImageButton = componentFactory.createButton("Delete", "Remove this image");
      StackPane.setAlignment(deleteImageButton, Pos.TOP_RIGHT);
      stackPane.getChildren().add(deleteImageButton);
      deleteImageButton.setOnAction((e) -> {
        LOGGER.severe("Removing image");
        objectControlsGroup.removeObjectInput(objectControlImageFile);
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
      isSoundTrackCheckBox.setSelected(album.isSoundtrack());
      iveHadOnLpCheckBox.setSelected(album.getMyInfo().isIveHadOnLP());
    } else {
      isCompilationAlbumCheckBox.setSelected(false);
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
    
    for (DiscPanelAbstract discPanel: discPanels) {
      objectControlsGroup.removeObjectInput(discPanel);
    }
    
    discPanels.clear();

    if (album != null) {
      for (Disc disc: album.getDiscs()) {
        addNewDiscPanel(disc);
      }
    } else {
//      addNewDiscPanel(null);  // There shall always be at least one disc, but this doesn't mean that the user is going to provide information about it.
    }
  }
  
  private void addNewDiscPanel(Disc disc) {
//    DiscPanel discPanel = new DiscPanel(customization, disc, albumTypeObjectControlEnumComboBox.valueProperty(), mediaDb);
    switch (albumTypeObjectControlEnumComboBox.getValue()) {
    case NORMAL_ALBUM:
      NormalDiscPanel normalDiscPanel = new NormalDiscPanel(customization, disc, mediaDb);
      discPanels.add(normalDiscPanel);
      objectControlsGroup.addObjectControl(normalDiscPanel);
      discsVBox.getChildren().add(normalDiscPanel.getControl());
      break;
      
    default:
      DiscPanelAbstract discPanel = new DiscPanel(customization, disc, null, mediaDb);
      discPanels.add(discPanel);
      objectControlsGroup.addObjectControl(discPanel);
      discsVBox.getChildren().add(discPanel.getControl());
      break;
    }
    
  }
  
  /**
   * Update the discs panel (<code>discTracksVBox</code>).
   * <p>
   * The panel is cleared and then for each disc (of the <code>AlbumInfo</code> a DiscPanel is added to the panel.
   */
  private void updateDiscsPanelFromAlbumInfo(AlbumInfo albumInfo) {
    discsVBox.getChildren().clear();
    
    for (DiscPanelAbstract discPanel: discPanels) {
      objectControlsGroup.removeObjectInput(discPanel);
    }

    discPanels.clear();

      for (DiscInfo discInfo: albumInfo.discs()) {
        addNewDiscPanelForDiscInfo(discInfo);
      }
  }
  
  private void addNewDiscPanelForDiscInfo(DiscInfo discInfo) {
    switch (albumTypeObjectControlEnumComboBox.getValue()) {
    case NORMAL_ALBUM:
      Disc disc = MEDIA_DB_FACTORY.createDisc();
      NormalDiscPanel normalDiscPanel = new NormalDiscPanel(customization, disc, mediaDb);
      normalDiscPanel.fillControlsFromDiscInfo(discInfo);
      discPanels.add(normalDiscPanel);
      objectControlsGroup.addObjectControl(normalDiscPanel);
      discsVBox.getChildren().add(normalDiscPanel.getControl());
      break;
      
    default:
//      DiscPanelAbstract discPanel = new DiscPanel(customization, disc, null, mediaDb);
//      discPanels.add(discPanel);
//      discsVBox.getChildren().add(discPanel.ocGetControl());
      break;
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
    ObjectControlFolderSelecter folderSelecter = componentFactory.createFolderSelecter(600, "Folder to import album information from", "Select source folder", "Open source folder chooser", "Source folder", false);
    folderSelecter.setInitialFolderProvider(() -> "D:\\SoulSeek\\complete");
    
    Label label = componentFactory.createLabel("Derive album details from:");
    hBox.getChildren().add(label);
    
    hBox.getChildren().add(folderSelecter.getControl());
    hBox.getChildren().add(folderSelecter.getFolderChooserButton());
    
    Button button = componentFactory.createButton("Derive album details", "Derive the album details from this folder");
    button.setOnAction(e -> newAlbumBasedOnTrackInFolder(folderSelecter.ocGetAbsolutePath()));
    hBox.getChildren().add(button);
    
    outerHBox.getChildren().add(hBox);
        
    return outerHBox;
  }
  
//  
//  /**
//   * Derive the information of an album from a folder (containing the tracks of an album).
//   * 
//   * @param sourceFolderName the folder from which the information is to be derived.
//   */
//  private void deriveAlbumDetails(String sourceFolderName) {
//    LOGGER.severe("=> sourceFolderName" + sourceFolderName);
//    
//    // Create the information in an Album structure, and then fill the control with this information. Don't set 'album'.
//    Album album = MEDIA_DB_FACTORY.createAlbum();
//    Disc disc = MEDIA_DB_FACTORY.createDisc();
//    album.getDiscs().add(disc);
//    
//    Path sourceFolder = Paths.get(sourceFolderName);
//
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
//    
//    // read all MP3 and FLAC files in the folder
//    try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceFolder)) {
//      List<Path> flacFiles = null;
//      
//      for (Path path: stream) {
//        String fileName = path.getFileName().toString();
//        LOGGER.severe("Handling path: " + fileName);
//        if (!Files.isDirectory(path)) {
//          String extension = FileUtils.getFileExtension(path);
//          LOGGER.severe("extension: " + extension);
//          
//          switch (extension) {
//          case ".mp3":
//            deriveInfoFromMp3File(album, path);
//            break;
//            
//          case ".flac":
//            if (flacFiles == null) {
//              flacFiles = new ArrayList<>();
//            }
//            flacFiles.add(path);
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
//      
//      if (flacFiles != null) {
//        DeriveAlbumInfo.deriveInfoFromFlacFiles(album, flacFiles, mediaDb);
//      }
//    } catch (IOException | DirectoryIteratorException x) {
//      // IOException can never be thrown by the iteration.
//      System.err.println(x);
//    }
//    
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
//  }
//  
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
  protected void addObjectAction() {
    LOGGER.severe("=>");
    
    try {
      createObject();
      
      updateObjectFromControls();
      
      List<Track> allNewTracks = new ArrayList<>();
      for (DiscPanelAbstract discPanel: discPanels) {
        List<Track> newTracks = discPanel.getNewTracks();
        allNewTracks.addAll(newTracks);
      }
      if (!allNewTracks.isEmpty()) {
        StringBuilder buf = new StringBuilder();
        buf.append("If you create this new album, the following tracks will be added to the media database");
        for (Track track: allNewTracks) {
          buf.append(track.toString()).append(NEW_LINE);
        }
        Optional<ButtonType> choice = componentFactory.createOkCancelConfirmationDialog("Create new tracks", buf.toString(), "Create album and tracks?").showAndWait();
        if (choice.isPresent()  &&  choice.get() == ButtonType.OK) {
          LOGGER.severe("Adding");
          addObjectToCollection();
          setObject(object, false);
        } else {
          LOGGER.severe("Cancelled");
        }
      }
      
      
    } catch (ObjectEditorException e) {
      StringBuilder buf = new StringBuilder();
      for (String problem: e.getProblems()) {
        buf.append(problem);
        buf.append(NEW_LINE);
      }
      componentFactory.createErrorDialog("Problem in album details", buf.toString()).showAndWait();
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createObject() {
    object = MEDIA_DB_FACTORY.createAlbum();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void addObjectToCollection() {
    mediaDb.getAlbums().add(object);
  }
  
  /**
   * Update the current Album based on the provided information.
   * <p>
   * If the provided information is correct, the Album is updated.
   * Otherwise, an Alert is shown with information about what is wrong.
   */
  protected void updateObject() {
    try {
      updateObjectFromControls();
    } catch (ObjectEditorException e) {
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
   * @param object the Album to be updated
   * @throws AlbumDetailsException in case one or more controls has an illegal value.
   */
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
    FlexDate releaseDate = albumIssueDateTextField.getValue();
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_ReleaseDate(), releaseDate);
    
    // Album Id
    String albumIdText = albumIdTextField.getValue();
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_Id(), albumIdText);
    
    // Players
    // Update the complete list of players if there is any change.
    boolean playersChanged = false;
    if (playerObjectControls.size() != object.getPlayers().size()) {
      playersChanged = true;
    }
    
    if (!playersChanged) {
      for (int i = 0; i < playerObjectControls.size(); i++) {
        Player currentPlayer = object.getPlayers().get(i);
        PlayerObjectControl playerObjectControl = playerObjectControls.get(i);
//        Tuplet<AutoCompleteTextFieldObjectInput<Artist>, TextField> tuplet = playerObjectControls.get(i);
        Artist newArtist = playerObjectControl.getArtistObjectControl().getValue();
//        Artist newArtist = tuplet.getObject1().getObjectValue();
        List<String> newInstruments = StringUtil.commaSeparatedValuesToListOfValues(playerObjectControl.getPlayerInstrumentTextField().getValue());
//        List<String> newInstruments = StringUtil.commaSeparatedValuesToListOfValues(tuplet.getObject2().getText());
        if (!newArtist.equals(currentPlayer.getArtist())  ||
            !newInstruments.equals(currentPlayer.getInstruments())) {
          playersChanged = true;
          break;
        }
      }
    }
    
    if (playersChanged) {
      object.getPlayers().clear();
      for (PlayerObjectControl playerObjectControl: playerObjectControls) {
        Player player = MEDIA_DB_FACTORY.createPlayer();
        player.setArtist(playerObjectControl.getArtistObjectControl().getValue());
        player.getInstruments().addAll(StringUtil.commaSeparatedValuesToListOfValues(playerObjectControl.getPlayerInstrumentTextField().getValue()));

        object.getPlayers().add(player);
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
    setImagesIfChanged2(object.getImagesFront(), frontImageControls);
    
    // Images front inside
    setImagesIfChanged2(object.getImagesFrontInside(), frontInsideImageControls);
    
    // Images back
    setImagesIfChanged2(object.getImagesBack(), backImageControls);
    
    // Images label
    setImagesIfChanged2(object.getImagesLabel(), labelImageControls);
    
    // Issued on
    setIssuedOnMediumsIfChanged(object.getIssuedOnMediums(), issuedOnMediaLabels);

    // Description title
    String descriptionTitleText = descriptionTitleTextField.getValue();
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_DescriptionTitle(), descriptionTitleText);

    // Description
    String descriptionText = descriptionTextArea.getValue();
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getAlbum_Description(), descriptionText);

    /*
     * MyInfo
     */
    MyInfo albumMyInfo = object.getMyInfo();
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
      IWant iWant = iWantComboBox.getValue();
      if (iWant == null) {
        iWant = IWant.NOT_SET;
      } else {
        EmfUtil.setFeatureValue(myInfoToFill, MEDIA_DB_PACKAGE.getMyInfo_IWant(), iWant);
      }
      
      EmfUtil.setFeatureValue(myInfoToFill, MEDIA_DB_PACKAGE.getMyInfo_IveHadOnLP(), iveHadOnLpCheckBox.isSelected());
      
      String myComments = myCommentsTextArea.getValue();
      EmfUtil.setFeatureValue(myInfoToFill, MEDIA_DB_PACKAGE.getMyInfo_MyComments(), myComments);
      
      if (albumMyInfo == null) {
        object.setMyInfo(myInfoToFill);
      }
    }
    
    for (DiscPanelAbstract discPanel: discPanels) {
      Disc disc = discPanel.getDisc();
      if (editMode == EditMode.NEW) {
        object.getDiscs().add(disc);
      }
      discPanel.getValue();
//      updateDiscFromDiscPanel(discPanel);
    }
    
    if (problems.size() != 0) {
      throw new ObjectEditorException(problems);
    }
  }
  
//  /**
//   * Update a Disc from the information in a DiscPanel.
//   * 
//   * @param discPanel the DiscPanel to update the disc from.
//   */
//  private void updateDiscFromDiscPanel(DiscPanelAbstract discPanel) {
//    Disc disc = discPanel.getDisc();
//    if (disc == null) {
//      disc = MEDIA_DB_FACTORY.createDisc();
//    }
//    EmfUtil.setFeatureValue(disc, MEDIA_DB_PACKAGE.getDisc_Title(), discPanel.getDiscTitle());
//  
//    for (TrackReferenceAndMyTrackInfoControls trackReferencePanel: discPanel.getTrackReferencePanels()) {
//      updateTrackReferenceFromTrackReferencePanel(trackReferencePanel);
//    }
//    
//  }

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
    if (myCommentsTextArea.getValue() != null) {  // TODO add other controls
      return true;
    }
    
    if (iWantComboBox.getValue() != IWant.NOT_SET) {
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


record DiscPanelInfo(Disc disc, Node panel) {
}





