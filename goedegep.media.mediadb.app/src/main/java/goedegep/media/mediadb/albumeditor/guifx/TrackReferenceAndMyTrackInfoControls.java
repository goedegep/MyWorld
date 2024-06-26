package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlEnumComboBox;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.media.mediadb.app.derivealbuminfo.TrackInfo;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.AlbumType;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.util.emf.EmfUtil;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
  
/**
 * This class creates and adds controls for a TrackReference and its contained MyTrackInfo, and adds these controls to the specified {@code GridPane}.
 * <p>
 * TrackReference:
 * <ul>
 * <li>Track number - Label<br/>
 *     This is not editable, for reference only.</li>
 * <li>Track name/reference<br/>
 *     This column depends on the kind of album.
 *     <ul>
 *     <li>Normal album<br/>
 *     The track name can be entered in a TextField. This is the name of a Track, with the album of the trackReference as OriginalAlbum.</li>
 *     </ul>
 * </li>
 * </ul>
 * MyTrackInfo:
 * <ul>
 * </ul>
 * 
 * @param customization The GUI customization.
 * @param trackReference The <code>TrackReference</code> to create a panel for.
 */
class TrackReferenceAndMyTrackInfoControls extends TrackReferenceControlsAbstract {
  private static final Logger LOGGER = Logger.getLogger(TrackReferenceAndMyTrackInfoControls.class.getName());

  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;

  /**
   * The GUI customization.
   */
  private CustomizationFx customization;

  private ComponentFactoryFx componentFactory;
  
  /**
   * The GridPane to which the controls are to be added.
   */
  private GridPane gridPane;
  
  /**
   * The {@code TrackReference} to which the controls apply
   */
  private TrackReference trackReference;
  
  /**
   * 
   */
  private AlbumType albumType;
  
  /**
   * The row in the {@code gridPane} at which the controls are added.
   */
  private int myRow;
  
  /**
   * The list of TrackReferenceAndMyTrackInfoControls of which this control is part.
   */
  private List<TrackReferenceAndMyTrackInfoControls> trackReferenceControls;
  
  /**
   * The media database.
   */
  private MediaDb mediaDb;
  
  /**
   * The current column in the {@code gridPane}.
   */
  private int column;
  
  /**
   * The {@code Track} referred to.
   */
  private Track track;
  
  /**
   * The new Track if applicable
   */
  private Track newTrack;
  
  private TextField trackNameTextField;
  private ObjectControlTextField<String> bonusTrack;
  private ObjectControlEnumComboBox<goedegep.media.mediadb.model.Collection> collectionComboBox;
  private ObjectControlEnumComboBox<IWant> iWantComboBox;
  private List<MediumInfoControls> iHaveOnMediumInfoControlsList = new ArrayList<>();
  
  /**
   * Constructor
   * 
   * @param customization The GUI customization
   * @param gridPane The GridPane to which the controls are to be added.
   * @param row The row in the gridPane at which the controls are to be added.
   * @param trackReference the TrackReference to which the controls apply. This shall be part of a Disc, which shall be part of an Album
   * @param albumType the type of album we're editing.
   * @param mediaDb the media database
   */
  TrackReferenceAndMyTrackInfoControls(CustomizationFx customization, GridPane gridPane, int row, List<TrackReferenceAndMyTrackInfoControls> trackReferenceControls, TrackReference trackReference, AlbumType albumType, MediaDb mediaDb) {
    super(false);
    
    this.customization = customization;
    this.gridPane = gridPane;
    myRow = row;
    this.trackReferenceControls = trackReferenceControls;
    this.trackReference = trackReference;
    this.albumType = albumType;
    this.mediaDb = mediaDb;
    
    componentFactory = customization.getComponentFactoryFx();
        
    if (trackReference != null) {
      track = trackReference.getTrack();
    }
    
//    /*
//     * TrackReference fields 
//     */
//    
//    // Track number not editable, for reference only
//    String trackNrText = null;
//    if (trackReference != null) {
//      int trackNr = trackReference.getTrackNr();
//      trackNrText = String.valueOf(trackNr);
//    }
//    Label label = componentFactory.createLabel(trackNrText, 30);
//    gridPane.add(label, column++, row);
//    
//    // Track text/reference
//    Node trackTextOrReferenceNode = null;
//    if (albumType == AlbumType.NORMAL_ALBUM) {
//      trackTextOrReferenceNode = createTrackNodeForNormalAlbum(trackReference);
//    gridPane.add(trackTextOrReferenceNode, column++, row);
//    } else {
//      column++;
//    }
////    String trackText = createTrackText();
////    Button referredTrackButton = componentFactory.createButton(trackText, "click to select the track (on the original album)");
////    referredTrackButton.setOnAction(e -> {
////      if (track != null) {
////        List<TrackWrapper> options = new ArrayList<>();
////        for (Track aTrack: mediaDb.getTracks()) {
////          if (aTrack.getTitle() != null  &&  aTrack.getTitle().equals(track.getTitle())) {
////            LOGGER.info("Adding track option: " + aTrack);
////            options.add(new TrackWrapper(aTrack));
////          }
////        }
////        TrackWrapper[] trackWrapperOptions = new TrackWrapper[options.size()];
////        int i = 0;
////        for (TrackWrapper trackWrapper: options) {
////          trackWrapperOptions[i++] = trackWrapper;
////        }
////        TrackWrapper defaultTrack = options.get(0);
////        ChoiceDialog<TrackWrapper> choiceDialog = componentFactory.createChoiceDialog("Track selection", "Select the original album track", "TODO", defaultTrack, trackWrapperOptions);
////        choiceDialog.showAndWait();
////        LOGGER.info("Selected track: " + choiceDialog.getSelectedItem());
////        TrackWrapper selectedTrackWrapper = choiceDialog.getSelectedItem();
////        Track selectedTrack = selectedTrackWrapper.getTrack();
////        LOGGER.severe("Before referred by: " + track.getReferredBy().size());
////        LOGGER.severe("Contains: " + track.getReferredBy().contains(trackReference));
//////        track.getReferredBy().remove(trackReference);
////        LOGGER.severe("After referred by: " + track.getReferredBy().size());
//////        trackReference.setTrack(selectedTrack);
////        track = selectedTrack;
////        referredTrackButton.setText(createTrackText());
//////        if (track.getReferredBy().isEmpty()) {   // TODO This may only be removed when the album is updated.
//////          LOGGER.severe("Removing track: " + track);
//////          mediaDb.getTracks().remove(track);
//////        } else {
//////          LOGGER.severe("Track still referred by: " + track.getReferredBy());
//////        }
////      }
////    });
////    gridPane.add(referredTrackButton, column++, row);
//    
//    // Identification of the disc of the track reference of the Original Track Reference (not Yet editable)
//    Disc originalAlbumTrackReferenceDisc = null;
//    Album originalAlbumTrackReferenceAlbum = null;
//    String originalAlbumTrackReferenceAlbumId = null;
//    String originalAlbumTrackReferenceDiscId = null;
//    TrackReference originalTrackReference = null;
//    if (trackReference != null) {
//      originalTrackReference = trackReference.getOriginalAlbumTrackReference();
//    }
//    
//    if (originalTrackReference != null) {
//      originalAlbumTrackReferenceDisc = originalTrackReference.getDisc();
//      originalAlbumTrackReferenceAlbum = originalAlbumTrackReferenceDisc.getAlbum();
//      originalAlbumTrackReferenceDiscId = originalAlbumTrackReferenceDisc.getTitle();
//      if (originalAlbumTrackReferenceDiscId == null) {
//        originalAlbumTrackReferenceDiscId = String.valueOf(originalAlbumTrackReferenceDisc.getDiscNr());
//      }
//    }
//
//    if (originalAlbumTrackReferenceAlbum != null) {
//      originalAlbumTrackReferenceAlbumId = originalAlbumTrackReferenceAlbum.getArtistAndTitle();
//      if (originalAlbumTrackReferenceAlbum.isMultiDiscAlbum() &&  (originalAlbumTrackReferenceDiscId != null)) {
//        originalAlbumTrackReferenceAlbumId = originalAlbumTrackReferenceAlbumId + " - " + originalAlbumTrackReferenceDiscId;
//      }
//    }
////    TextFieldObjectInput<String> originalAlbumTrackReferenceDiscControl = componentFactory.createTextFieldObjectInput(null, originalAlbumTrackReferenceAlbumId, 300, true, null);
////    gridPane.add(originalAlbumTrackReferenceDiscControl, column++, row);
//    
//    // Identification of the track of the track reference of the Original Track Reference (not Yet editable)
//    String originalAlbumTrackReferenceTrackTitle = null;
//    if (originalTrackReference != null) {
//      Track originalAlbumTrackReferenceTrack = originalTrackReference.getTrack();
//      if (originalAlbumTrackReferenceTrack != null) {
//        originalAlbumTrackReferenceTrackTitle = originalAlbumTrackReferenceTrack.getTitle();
//      }
//    }
////    TextFieldObjectInput<String> originalAlbumTrackReferenceTrackControl = componentFactory.createTextFieldObjectInput(null, originalAlbumTrackReferenceTrackTitle, 300, true, null);
//    Button button = componentFactory.createButton(originalAlbumTrackReferenceAlbumId + ":" + originalAlbumTrackReferenceTrackTitle, "click to select the track on the original album");
//    button.setOnAction(e -> {
//      if (track != null) {
//        List<Track> options = new ArrayList<>();
//        for (Track aTrack: mediaDb.getTracks()) {
//          if (aTrack.getTitle() != null  &&  aTrack.getTitle().equals(track.getTitle())) {
//            LOGGER.severe("Adding track option: " + aTrack);
//            options.add(aTrack);
//          }
//        }
//        ChoiceDialog<Object> choiceDialog = componentFactory.createChoiceDialog("Track selection", "Select the original album track", "TODO", options.get(0), options);
//        choiceDialog.showAndWait();
//        LOGGER.severe("Selected track: " + choiceDialog.getSelectedItem().getClass().getName());
//        LOGGER.severe("Selected track: " + choiceDialog.getSelectedItem());
//      }
//    });
//    gridPane.add(button, column++, row);
//    
//    // Bonus track
//    String bonusTrackText = null;
//    if (trackReference != null) {
//      bonusTrackText = trackReference.getBonusTrack();
//    }
//    bonusTrack = componentFactory.createObjectControlTextField(null, bonusTrackText, 300, true, null);
//    gridPane.add(bonusTrack.ocGetControl(), column++, row);
//    
//    /*
//     * MyTrackInfo
//     */
//    MyTrackInfo myTrackInfo = null;
//    if (trackReference != null) {
//      myTrackInfo = trackReference.getMyTrackInfo();
//    }
//    
//    // Identification of the disc of the track reference of the Compilation Track Reference (not Yet editable)
//    Disc compilationTrackReferenceDisc = null;
//    Album compilationTrackReferenceAlbum = null;
//    String compilationTrackReferenceAlbumId = null;
//    String compilationTrackReferenceDiscId = null;
//    TrackReference compilationTrackReference = null;
//    
//    if (myTrackInfo != null) {
//      compilationTrackReference = myTrackInfo.getCompilationTrackReference();
//    }
//    
//    if (compilationTrackReference != null) {
//      compilationTrackReferenceDisc = compilationTrackReference.getDisc();
//      compilationTrackReferenceAlbum = compilationTrackReferenceDisc.getAlbum();
//      compilationTrackReferenceDiscId = compilationTrackReferenceDisc.getTitle();
//      if (compilationTrackReferenceDiscId == null) {
//        compilationTrackReferenceDiscId = String.valueOf(compilationTrackReferenceDisc.getDiscNr());
//      }
//    }
//
//    if (compilationTrackReferenceAlbum != null) {
//      compilationTrackReferenceAlbumId = compilationTrackReferenceAlbum.getArtistAndTitle();
//      if (compilationTrackReferenceAlbum.isMultiDiscAlbum() &&  (compilationTrackReferenceDiscId != null)) {
//        compilationTrackReferenceAlbumId = compilationTrackReferenceAlbumId + " - " + compilationTrackReferenceDiscId;
//      }
//    }
//    ObjectControlTextField<String> compilationTrackReferenceDiscControl = componentFactory.createObjectControlTextField(null, compilationTrackReferenceAlbumId, 300, true, null);
//    gridPane.add(compilationTrackReferenceDiscControl.ocGetControl(), column++, row);
//    
//    // Identification of the track of the track reference of the Compilation Track Reference (not Yet editable)
//    String compilationTrackReferenceTrackTitle = null;
//    if (compilationTrackReference != null) {
//      Track compilationTrackReferenceTrack = compilationTrackReference.getTrack();
//      if (compilationTrackReferenceTrack != null) {
//        compilationTrackReferenceTrackTitle = compilationTrackReferenceTrack.getTitle();
//      }
//    }
//    ObjectControlTextField<String> compilationTrackReferenceTrackControl = componentFactory.createObjectControlTextField(null, compilationTrackReferenceTrackTitle, 300, true, null);
//    gridPane.add(compilationTrackReferenceTrackControl.ocGetControl(), column++, row);
//    
//    // MyTrackInfo:Collection
//    collectionComboBox = componentFactory.createObjectControlEnumComboBox(goedegep.media.mediadb.model.Collection.NOT_SET, goedegep.media.mediadb.model.Collection.NOT_SET, MediadbPackage.eINSTANCE.getCollection(), true, "If applicable, select the collection in which this track resides");
//    goedegep.media.mediadb.model.Collection collection = null;
//    if (myTrackInfo != null) {
//      collection = myTrackInfo.getCollection();
//    }
//    collectionComboBox.ocSetValue(collection);
//    gridPane.add(collectionComboBox.ocGetControl(), column++, row);
//    
//    // MyTrackInfo:IWant
//    iWantComboBox = componentFactory.createObjectControlEnumComboBox(IWant.NOT_SET, IWant.NOT_SET, MediadbPackage.eINSTANCE.getIWant(), true, "Select whether you want this track or not");
//    IWant iWant = null;
//    if (myTrackInfo != null) {
//      iWant = myTrackInfo.getIWant();
//    }
//    iWantComboBox.ocSetValue(iWant);
//    gridPane.add(iWantComboBox.ocGetControl(), column++, row);
//    
//    // IHaveOn
//    if (myTrackInfo != null) {
//      for (MediumInfo iHaveOnMediumInfo: myTrackInfo.getIHaveOn()) {
//        iHaveOnMediumInfoControlsList.add(new MediumInfoControls(customization, gridPane, row, column, iHaveOnMediumInfo));
//        column += 4;
//      }
//    }
//    
//    // New 'I Have on button'
//    Button newIHaveOnButton = componentFactory.createButton("+", "Add an extra 'I have on'");
//    newIHaveOnButton.setOnAction(e -> {
//      gridPane.getChildren().remove(newIHaveOnButton);
//      iHaveOnMediumInfoControlsList.add(new MediumInfoControls(customization, gridPane, myRow, column, MediadbFactory.eINSTANCE.createMediumInfo()));
//      column += 4;
//      gridPane.add(newIHaveOnButton, column, row);
//    });
//    gridPane.add(newIHaveOnButton, column, row);
    
    fillControlsFromObject();
  }
  
//  /**
//   * Constructor
//   * 
//   * @param customization The GUI customization
//   * @param gridPane The GridPane to which the controls are to be added.
//   * @param row The row in the gridPane at which the controls are to be added.
//   * @param trackReference the TrackReference to which the controls apply. This shall be part of a Disc, which shall be part of an Album
//   * @param mediaDb the media database
//   */
//  TrackReferenceAndMyTrackInfoControls(CustomizationFx customization, GridPane gridPane, int row, TrackInfo trackInfo, AlbumType albumType, MediaDb mediaDb) {
//    super(false);
//    this.trackReference = null;
//    myRow = row;
//    
//    componentFactory = customization.getComponentFactoryFx();
//        
////    if (trackInfo != null) {
////      track = trackInfo.getTrack();
////    }
//    
//
//    
//    /*
//     * TrackReference fields 
//     */
//    
//    // Track number not editable, for reference only
//    String trackNrText = null;
////    if (trackReference != null) {
////      int trackNr = trackReference.getTrackNr();
////      trackNrText = String.valueOf(trackNr);
////    }
//    Label label = componentFactory.createLabel(trackNrText, 30);
//    gridPane.add(label, column++, row);
//    
//    // Track text/reference
//    Node trackTextOrReferenceNode = null;
//    if (albumType == AlbumType.NORMAL_ALBUM) {
//      trackTextOrReferenceNode = createTrackNodeForNormalAlbum(trackInfo.trackTitle());
//      gridPane.add(trackTextOrReferenceNode, column++, row);
//    } else {
//      column++;
//    }
////    String trackText = createTrackText();
////    Button referredTrackButton = componentFactory.createButton(trackText, "click to select the track (on the original album)");
////    referredTrackButton.setOnAction(e -> {
////      if (track != null) {
////        List<TrackWrapper> options = new ArrayList<>();
////        for (Track aTrack: mediaDb.getTracks()) {
////          if (aTrack.getTitle() != null  &&  aTrack.getTitle().equals(track.getTitle())) {
////            LOGGER.info("Adding track option: " + aTrack);
////            options.add(new TrackWrapper(aTrack));
////          }
////        }
////        TrackWrapper[] trackWrapperOptions = new TrackWrapper[options.size()];
////        int i = 0;
////        for (TrackWrapper trackWrapper: options) {
////          trackWrapperOptions[i++] = trackWrapper;
////        }
////        TrackWrapper defaultTrack = options.get(0);
////        ChoiceDialog<TrackWrapper> choiceDialog = componentFactory.createChoiceDialog("Track selection", "Select the original album track", "TODO", defaultTrack, trackWrapperOptions);
////        choiceDialog.showAndWait();
////        LOGGER.info("Selected track: " + choiceDialog.getSelectedItem());
////        TrackWrapper selectedTrackWrapper = choiceDialog.getSelectedItem();
////        Track selectedTrack = selectedTrackWrapper.getTrack();
////        LOGGER.severe("Before referred by: " + track.getReferredBy().size());
////        LOGGER.severe("Contains: " + track.getReferredBy().contains(trackReference));
//////        track.getReferredBy().remove(trackReference);
////        LOGGER.severe("After referred by: " + track.getReferredBy().size());
//////        trackReference.setTrack(selectedTrack);
////        track = selectedTrack;
////        referredTrackButton.setText(createTrackText());
//////        if (track.getReferredBy().isEmpty()) {   // TODO This may only be removed when the album is updated.
//////          LOGGER.severe("Removing track: " + track);
//////          mediaDb.getTracks().remove(track);
//////        } else {
//////          LOGGER.severe("Track still referred by: " + track.getReferredBy());
//////        }
////      }
////    });
////    gridPane.add(referredTrackButton, column++, row);
//    
//    // Identification of the disc of the track reference of the Original Track Reference (not Yet editable)
//    Disc originalAlbumTrackReferenceDisc = null;
//    Album originalAlbumTrackReferenceAlbum = null;
//    String originalAlbumTrackReferenceAlbumId = null;
//    String originalAlbumTrackReferenceDiscId = null;
//    TrackReference originalTrackReference = null;
//    if (trackReference != null) {
//      originalTrackReference = trackReference.getOriginalAlbumTrackReference();
//    }
//    
//    if (originalTrackReference != null) {
//      originalAlbumTrackReferenceDisc = originalTrackReference.getDisc();
//      originalAlbumTrackReferenceAlbum = originalAlbumTrackReferenceDisc.getAlbum();
//      originalAlbumTrackReferenceDiscId = originalAlbumTrackReferenceDisc.getTitle();
//      if (originalAlbumTrackReferenceDiscId == null) {
//        originalAlbumTrackReferenceDiscId = String.valueOf(originalAlbumTrackReferenceDisc.getDiscNr());
//      }
//    }
//
//    if (originalAlbumTrackReferenceAlbum != null) {
//      originalAlbumTrackReferenceAlbumId = originalAlbumTrackReferenceAlbum.getArtistAndTitle();
//      if (originalAlbumTrackReferenceAlbum.isMultiDiscAlbum() &&  (originalAlbumTrackReferenceDiscId != null)) {
//        originalAlbumTrackReferenceAlbumId = originalAlbumTrackReferenceAlbumId + " - " + originalAlbumTrackReferenceDiscId;
//      }
//    }
////    TextFieldObjectInput<String> originalAlbumTrackReferenceDiscControl = componentFactory.createTextFieldObjectInput(null, originalAlbumTrackReferenceAlbumId, 300, true, null);
////    gridPane.add(originalAlbumTrackReferenceDiscControl, column++, row);
//    
//    // Identification of the track of the track reference of the Original Track Reference (not Yet editable)
//    String originalAlbumTrackReferenceTrackTitle = null;
//    if (originalTrackReference != null) {
//      Track originalAlbumTrackReferenceTrack = originalTrackReference.getTrack();
//      if (originalAlbumTrackReferenceTrack != null) {
//        originalAlbumTrackReferenceTrackTitle = originalAlbumTrackReferenceTrack.getTitle();
//      }
//    }
////    TextFieldObjectInput<String> originalAlbumTrackReferenceTrackControl = componentFactory.createTextFieldObjectInput(null, originalAlbumTrackReferenceTrackTitle, 300, true, null);
//    Button button = componentFactory.createButton(originalAlbumTrackReferenceAlbumId + ":" + originalAlbumTrackReferenceTrackTitle, "click to select the track on the original album");
//    button.setOnAction(e -> {
//      if (track != null) {
//        List<Track> options = new ArrayList<>();
//        for (Track aTrack: mediaDb.getTracks()) {
//          if (aTrack.getTitle() != null  &&  aTrack.getTitle().equals(track.getTitle())) {
//            LOGGER.severe("Adding track option: " + aTrack);
//            options.add(aTrack);
//          }
//        }
//        ChoiceDialog<Object> choiceDialog = componentFactory.createChoiceDialog("Track selection", "Select the original album track", "TODO", options.get(0), options);
//        choiceDialog.showAndWait();
//        LOGGER.severe("Selected track: " + choiceDialog.getSelectedItem().getClass().getName());
//        LOGGER.severe("Selected track: " + choiceDialog.getSelectedItem());
//      }
//    });
//    gridPane.add(button, column++, row);
//    
//    // Bonus track
//    String bonusTrackText = null;
//    if (trackReference != null) {
//      bonusTrackText = trackReference.getBonusTrack();
//    }
//    bonusTrack = componentFactory.createObjectControlTextField(null, bonusTrackText, 300, true, null);
//    gridPane.add(bonusTrack.ocGetControl(), column++, row);
//    
//    /*
//     * MyTrackInfo
//     */
//    MyTrackInfo myTrackInfo = null;
//    if (trackReference != null) {
//      myTrackInfo = trackReference.getMyTrackInfo();
//    }
//    
//    // Identification of the disc of the track reference of the Compilation Track Reference (not Yet editable)
//    Disc compilationTrackReferenceDisc = null;
//    Album compilationTrackReferenceAlbum = null;
//    String compilationTrackReferenceAlbumId = null;
//    String compilationTrackReferenceDiscId = null;
//    TrackReference compilationTrackReference = null;
//    
//    if (myTrackInfo != null) {
//      compilationTrackReference = myTrackInfo.getCompilationTrackReference();
//    }
//    
//    if (compilationTrackReference != null) {
//      compilationTrackReferenceDisc = compilationTrackReference.getDisc();
//      compilationTrackReferenceAlbum = compilationTrackReferenceDisc.getAlbum();
//      compilationTrackReferenceDiscId = compilationTrackReferenceDisc.getTitle();
//      if (compilationTrackReferenceDiscId == null) {
//        compilationTrackReferenceDiscId = String.valueOf(compilationTrackReferenceDisc.getDiscNr());
//      }
//    }
//
//    if (compilationTrackReferenceAlbum != null) {
//      compilationTrackReferenceAlbumId = compilationTrackReferenceAlbum.getArtistAndTitle();
//      if (compilationTrackReferenceAlbum.isMultiDiscAlbum() &&  (compilationTrackReferenceDiscId != null)) {
//        compilationTrackReferenceAlbumId = compilationTrackReferenceAlbumId + " - " + compilationTrackReferenceDiscId;
//      }
//    }
//    ObjectControlTextField<String> compilationTrackReferenceDiscControl = componentFactory.createObjectControlTextField(null, compilationTrackReferenceAlbumId, 300, true, null);
//    gridPane.add(compilationTrackReferenceDiscControl.ocGetControl(), column++, row);
//    
//    // Identification of the track of the track reference of the Compilation Track Reference (not Yet editable)
//    String compilationTrackReferenceTrackTitle = null;
//    if (compilationTrackReference != null) {
//      Track compilationTrackReferenceTrack = compilationTrackReference.getTrack();
//      if (compilationTrackReferenceTrack != null) {
//        compilationTrackReferenceTrackTitle = compilationTrackReferenceTrack.getTitle();
//      }
//    }
//    ObjectControlTextField<String> compilationTrackReferenceTrackControl = componentFactory.createObjectControlTextField(null, compilationTrackReferenceTrackTitle, 300, true, null);
//    gridPane.add(compilationTrackReferenceTrackControl.ocGetControl(), column++, row);
//    
//    // MyTrackInfo:Collection
//    collectionComboBox = componentFactory.createObjectControlEnumComboBox(goedegep.media.mediadb.model.Collection.NOT_SET, goedegep.media.mediadb.model.Collection.NOT_SET, MediadbPackage.eINSTANCE.getCollection(), true, "If applicable, select the collection in which this track resides");
//    goedegep.media.mediadb.model.Collection collection = null;
//    if (myTrackInfo != null) {
//      collection = myTrackInfo.getCollection();
//    }
//    collectionComboBox.ocSetValue(collection);
//    gridPane.add(collectionComboBox.ocGetControl(), column++, row);
//    
//    // MyTrackInfo:IWant
//    iWantComboBox = componentFactory.createObjectControlEnumComboBox(IWant.NOT_SET, IWant.NOT_SET, MediadbPackage.eINSTANCE.getIWant(), true, "Select whether you want this track or not");
//    IWant iWant = null;
//    if (myTrackInfo != null) {
//      iWant = myTrackInfo.getIWant();
//    }
//    iWantComboBox.ocSetValue(iWant);
//    gridPane.add(iWantComboBox.ocGetControl(), column++, row);
//    
//    // IHaveOn
//    if (myTrackInfo != null) {
//      for (MediumInfo iHaveOnMediumInfo: myTrackInfo.getIHaveOn()) {
//        iHaveOnMediumInfoControlsList.add(new MediumInfoControls(customization, gridPane, row, column, iHaveOnMediumInfo));
//        column += 4;
//      }
//    }
//    
//    // New 'I Have on button'
//    Button newIHaveOnButton = componentFactory.createButton("+", "Add an extra 'I have on'");
//    newIHaveOnButton.setOnAction(e -> {
//      gridPane.getChildren().remove(newIHaveOnButton);
//      iHaveOnMediumInfoControlsList.add(new MediumInfoControls(customization, gridPane, myRow, column, MediadbFactory.eINSTANCE.createMediumInfo()));
//      column += 4;
//      gridPane.add(newIHaveOnButton, column, row);
//    });
//    gridPane.add(newIHaveOnButton, column, row);
//  }
  
  private void fillControlsFromObject() {
    setControlsToDefaultValues();
    
    gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == myRow);
        
    column = 0;
    
    /*
     * TrackReference fields 
     */
    
    addTrackNrField();
    
    // Track text/reference
    Node trackTextOrReferenceNode = null;
    if (albumType == AlbumType.NORMAL_ALBUM) {
      trackTextOrReferenceNode = createTrackNodeForNormalAlbum(trackReference);
    gridPane.add(trackTextOrReferenceNode, column++, myRow);
    } else {
      column++;
    }
//    String trackText = createTrackText();
//    Button referredTrackButton = componentFactory.createButton(trackText, "click to select the track (on the original album)");
//    referredTrackButton.setOnAction(e -> {
//      if (track != null) {
//        List<TrackWrapper> options = new ArrayList<>();
//        for (Track aTrack: mediaDb.getTracks()) {
//          if (aTrack.getTitle() != null  &&  aTrack.getTitle().equals(track.getTitle())) {
//            LOGGER.info("Adding track option: " + aTrack);
//            options.add(new TrackWrapper(aTrack));
//          }
//        }
//        TrackWrapper[] trackWrapperOptions = new TrackWrapper[options.size()];
//        int i = 0;
//        for (TrackWrapper trackWrapper: options) {
//          trackWrapperOptions[i++] = trackWrapper;
//        }
//        TrackWrapper defaultTrack = options.get(0);
//        ChoiceDialog<TrackWrapper> choiceDialog = componentFactory.createChoiceDialog("Track selection", "Select the original album track", "TODO", defaultTrack, trackWrapperOptions);
//        choiceDialog.showAndWait();
//        LOGGER.info("Selected track: " + choiceDialog.getSelectedItem());
//        TrackWrapper selectedTrackWrapper = choiceDialog.getSelectedItem();
//        Track selectedTrack = selectedTrackWrapper.getTrack();
//        LOGGER.severe("Before referred by: " + track.getReferredBy().size());
//        LOGGER.severe("Contains: " + track.getReferredBy().contains(trackReference));
////        track.getReferredBy().remove(trackReference);
//        LOGGER.severe("After referred by: " + track.getReferredBy().size());
////        trackReference.setTrack(selectedTrack);
//        track = selectedTrack;
//        referredTrackButton.setText(createTrackText());
////        if (track.getReferredBy().isEmpty()) {   // TODO This may only be removed when the album is updated.
////          LOGGER.severe("Removing track: " + track);
////          mediaDb.getTracks().remove(track);
////        } else {
////          LOGGER.severe("Track still referred by: " + track.getReferredBy());
////        }
//      }
//    });
//    gridPane.add(referredTrackButton, column++, row);
    
    // Identification of the disc of the track reference of the Original Track Reference (not Yet editable)
    Disc originalAlbumTrackReferenceDisc = null;
    Album originalAlbumTrackReferenceAlbum = null;
    String originalAlbumTrackReferenceAlbumId = null;
    String originalAlbumTrackReferenceDiscId = null;
    TrackReference originalTrackReference = null;
    if (trackReference != null) {
      originalTrackReference = trackReference.getOriginalAlbumTrackReference();
    }
    
    if (originalTrackReference != null) {
      originalAlbumTrackReferenceDisc = originalTrackReference.getDisc();
      originalAlbumTrackReferenceAlbum = originalAlbumTrackReferenceDisc.getAlbum();
      originalAlbumTrackReferenceDiscId = originalAlbumTrackReferenceDisc.getTitle();
      if (originalAlbumTrackReferenceDiscId == null) {
        originalAlbumTrackReferenceDiscId = String.valueOf(originalAlbumTrackReferenceDisc.getDiscNr());
      }
    }

    if (originalAlbumTrackReferenceAlbum != null) {
      originalAlbumTrackReferenceAlbumId = originalAlbumTrackReferenceAlbum.getArtistAndTitle();
      if (originalAlbumTrackReferenceAlbum.isMultiDiscAlbum() &&  (originalAlbumTrackReferenceDiscId != null)) {
        originalAlbumTrackReferenceAlbumId = originalAlbumTrackReferenceAlbumId + " - " + originalAlbumTrackReferenceDiscId;
      }
    }
//    TextFieldObjectInput<String> originalAlbumTrackReferenceDiscControl = componentFactory.createTextFieldObjectInput(null, originalAlbumTrackReferenceAlbumId, 300, true, null);
//    gridPane.add(originalAlbumTrackReferenceDiscControl, column++, row);
    
    // Identification of the track of the track reference of the Original Track Reference (not Yet editable)
    String originalAlbumTrackReferenceTrackTitle = null;
    if (originalTrackReference != null) {
      Track originalAlbumTrackReferenceTrack = originalTrackReference.getTrack();
      if (originalAlbumTrackReferenceTrack != null) {
        originalAlbumTrackReferenceTrackTitle = originalAlbumTrackReferenceTrack.getTitle();
      }
    }
//    TextFieldObjectInput<String> originalAlbumTrackReferenceTrackControl = componentFactory.createTextFieldObjectInput(null, originalAlbumTrackReferenceTrackTitle, 300, true, null);
    Button button = componentFactory.createButton(originalAlbumTrackReferenceAlbumId + ":" + originalAlbumTrackReferenceTrackTitle, "click to select the track on the original album");
    button.setOnAction(e -> {
      if (track != null) {
        List<Track> options = new ArrayList<>();
        for (Track aTrack: mediaDb.getTracks()) {
          if (aTrack.getTitle() != null  &&  aTrack.getTitle().equals(track.getTitle())) {
            LOGGER.severe("Adding track option: " + aTrack);
            options.add(aTrack);
          }
        }
        ChoiceDialog<Object> choiceDialog = componentFactory.createChoiceDialog("Track selection", "Select the original album track", "TODO", options.get(0), options);
        choiceDialog.showAndWait();
        LOGGER.severe("Selected track: " + choiceDialog.getSelectedItem().getClass().getName());
        LOGGER.severe("Selected track: " + choiceDialog.getSelectedItem());
      }
    });
    gridPane.add(button, column++, myRow);
    
    // Bonus track
    String bonusTrackText = null;
    if (trackReference != null) {
      bonusTrackText = trackReference.getBonusTrack();
    }
    bonusTrack = componentFactory.createObjectControlTextField(null, bonusTrackText, 300, true, null);
    gridPane.add(bonusTrack.getControl(), column++, myRow);
    
    /*
     * MyTrackInfo
     */
    MyTrackInfo myTrackInfo = null;
    if (trackReference != null) {
      myTrackInfo = trackReference.getMyTrackInfo();
    }
    
    // Identification of the disc of the track reference of the Compilation Track Reference (not Yet editable)
    Disc compilationTrackReferenceDisc = null;
    Album compilationTrackReferenceAlbum = null;
    String compilationTrackReferenceAlbumId = null;
    String compilationTrackReferenceDiscId = null;
    TrackReference compilationTrackReference = null;
    
    if (myTrackInfo != null) {
      compilationTrackReference = myTrackInfo.getCompilationTrackReference();
    }
    
    if (compilationTrackReference != null) {
      compilationTrackReferenceDisc = compilationTrackReference.getDisc();
      compilationTrackReferenceAlbum = compilationTrackReferenceDisc.getAlbum();
      compilationTrackReferenceDiscId = compilationTrackReferenceDisc.getTitle();
      if (compilationTrackReferenceDiscId == null) {
        compilationTrackReferenceDiscId = String.valueOf(compilationTrackReferenceDisc.getDiscNr());
      }
    }

    if (compilationTrackReferenceAlbum != null) {
      compilationTrackReferenceAlbumId = compilationTrackReferenceAlbum.getArtistAndTitle();
      if (compilationTrackReferenceAlbum.isMultiDiscAlbum() &&  (compilationTrackReferenceDiscId != null)) {
        compilationTrackReferenceAlbumId = compilationTrackReferenceAlbumId + " - " + compilationTrackReferenceDiscId;
      }
    }
    ObjectControlTextField<String> compilationTrackReferenceDiscControl = componentFactory.createObjectControlTextField(null, compilationTrackReferenceAlbumId, 300, true, null);
    gridPane.add(compilationTrackReferenceDiscControl.getControl(), column++, myRow);
    
    // Identification of the track of the track reference of the Compilation Track Reference (not Yet editable)
    String compilationTrackReferenceTrackTitle = null;
    if (compilationTrackReference != null) {
      Track compilationTrackReferenceTrack = compilationTrackReference.getTrack();
      if (compilationTrackReferenceTrack != null) {
        compilationTrackReferenceTrackTitle = compilationTrackReferenceTrack.getTitle();
      }
    }
    ObjectControlTextField<String> compilationTrackReferenceTrackControl = componentFactory.createObjectControlTextField(null, compilationTrackReferenceTrackTitle, 300, true, null);
    gridPane.add(compilationTrackReferenceTrackControl.getControl(), column++, myRow);
    
    // MyTrackInfo:Collection
    collectionComboBox = componentFactory.createObjectControlEnumComboBox(goedegep.media.mediadb.model.Collection.NOT_SET, goedegep.media.mediadb.model.Collection.NOT_SET, MediadbPackage.eINSTANCE.getCollection(), true, "If applicable, select the collection in which this track resides");
    goedegep.media.mediadb.model.Collection collection = null;
    if (myTrackInfo != null) {
      collection = myTrackInfo.getCollection();
    }
    collectionComboBox.setValue(collection);
    gridPane.add(collectionComboBox.getControl(), column++, myRow);
    
    // MyTrackInfo:IWant
    iWantComboBox = componentFactory.createObjectControlEnumComboBox(IWant.NOT_SET, IWant.NOT_SET, MediadbPackage.eINSTANCE.getIWant(), true, "Select whether you want this track or not");
    IWant iWant = null;
    if (myTrackInfo != null) {
      iWant = myTrackInfo.getIWant();
    }
    iWantComboBox.setValue(iWant);
    gridPane.add(iWantComboBox.getControl(), column++, myRow);
    
    // IHaveOn
    if (myTrackInfo != null) {
      for (MediumInfo iHaveOnMediumInfo: myTrackInfo.getIHaveOn()) {
        iHaveOnMediumInfoControlsList.add(new MediumInfoControls(customization, gridPane, myRow, column, iHaveOnMediumInfo));
        column += 4;
      }
    }
    
    // New 'I Have on button'
    Button newIHaveOnButton = componentFactory.createButton("+", "Add an extra 'I have on'");
    newIHaveOnButton.setOnAction(e -> {
      gridPane.getChildren().remove(newIHaveOnButton);
      iHaveOnMediumInfoControlsList.add(new MediumInfoControls(customization, gridPane, myRow, column, MediadbFactory.eINSTANCE.createMediumInfo()));
      column += 4;
      gridPane.add(newIHaveOnButton, column, myRow);
    });
    gridPane.add(newIHaveOnButton, column, myRow);
   
  }
  
  public void fillControlsFromTrackInfo(TrackInfo trackInfo) {
    gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == myRow);
    
    column = 0;
    
    /*
     * TrackReference fields 
     */
    
    addTrackNrField();
    
    // Track text/reference
    if (albumType == AlbumType.NORMAL_ALBUM) {
      // For a normal album the track title is shown.
      trackNameTextField = createTrackNodeForNormalAlbum(trackInfo.trackTitle());
      gridPane.add(trackNameTextField, column++, myRow);
    } else {
      column++;
    }
//    String trackText = createTrackText();
//    Button referredTrackButton = componentFactory.createButton(trackText, "click to select the track (on the original album)");
//    referredTrackButton.setOnAction(e -> {
//      if (track != null) {
//        List<TrackWrapper> options = new ArrayList<>();
//        for (Track aTrack: mediaDb.getTracks()) {
//          if (aTrack.getTitle() != null  &&  aTrack.getTitle().equals(track.getTitle())) {
//            LOGGER.info("Adding track option: " + aTrack);
//            options.add(new TrackWrapper(aTrack));
//          }
//        }
//        TrackWrapper[] trackWrapperOptions = new TrackWrapper[options.size()];
//        int i = 0;
//        for (TrackWrapper trackWrapper: options) {
//          trackWrapperOptions[i++] = trackWrapper;
//        }
//        TrackWrapper defaultTrack = options.get(0);
//        ChoiceDialog<TrackWrapper> choiceDialog = componentFactory.createChoiceDialog("Track selection", "Select the original album track", "TODO", defaultTrack, trackWrapperOptions);
//        choiceDialog.showAndWait();
//        LOGGER.info("Selected track: " + choiceDialog.getSelectedItem());
//        TrackWrapper selectedTrackWrapper = choiceDialog.getSelectedItem();
//        Track selectedTrack = selectedTrackWrapper.getTrack();
//        LOGGER.severe("Before referred by: " + track.getReferredBy().size());
//        LOGGER.severe("Contains: " + track.getReferredBy().contains(trackReference));
////        track.getReferredBy().remove(trackReference);
//        LOGGER.severe("After referred by: " + track.getReferredBy().size());
////        trackReference.setTrack(selectedTrack);
//        track = selectedTrack;
//        referredTrackButton.setText(createTrackText());
////        if (track.getReferredBy().isEmpty()) {   // TODO This may only be removed when the album is updated.
////          LOGGER.severe("Removing track: " + track);
////          mediaDb.getTracks().remove(track);
////        } else {
////          LOGGER.severe("Track still referred by: " + track.getReferredBy());
////        }
//      }
//    });
//    gridPane.add(referredTrackButton, column++, row);
    
    // Identification of the disc of the track reference of the Original Track Reference (not Yet editable)
    Disc originalAlbumTrackReferenceDisc = null;
    Album originalAlbumTrackReferenceAlbum = null;
    String originalAlbumTrackReferenceAlbumId = null;
    String originalAlbumTrackReferenceDiscId = null;
    TrackReference originalTrackReference = null;
    if (trackReference != null) {
      originalTrackReference = trackReference.getOriginalAlbumTrackReference();
    }
    
    if (originalTrackReference != null) {
      originalAlbumTrackReferenceDisc = originalTrackReference.getDisc();
      originalAlbumTrackReferenceAlbum = originalAlbumTrackReferenceDisc.getAlbum();
      originalAlbumTrackReferenceDiscId = originalAlbumTrackReferenceDisc.getTitle();
      if (originalAlbumTrackReferenceDiscId == null) {
        originalAlbumTrackReferenceDiscId = String.valueOf(originalAlbumTrackReferenceDisc.getDiscNr());
      }
    }

    if (originalAlbumTrackReferenceAlbum != null) {
      originalAlbumTrackReferenceAlbumId = originalAlbumTrackReferenceAlbum.getArtistAndTitle();
      if (originalAlbumTrackReferenceAlbum.isMultiDiscAlbum() &&  (originalAlbumTrackReferenceDiscId != null)) {
        originalAlbumTrackReferenceAlbumId = originalAlbumTrackReferenceAlbumId + " - " + originalAlbumTrackReferenceDiscId;
      }
    }
//    TextFieldObjectInput<String> originalAlbumTrackReferenceDiscControl = componentFactory.createTextFieldObjectInput(null, originalAlbumTrackReferenceAlbumId, 300, true, null);
//    gridPane.add(originalAlbumTrackReferenceDiscControl, column++, row);
    
    // Identification of the track of the track reference of the Original Track Reference (not Yet editable)
    String originalAlbumTrackReferenceTrackTitle = null;
    if (originalTrackReference != null) {
      Track originalAlbumTrackReferenceTrack = originalTrackReference.getTrack();
      if (originalAlbumTrackReferenceTrack != null) {
        originalAlbumTrackReferenceTrackTitle = originalAlbumTrackReferenceTrack.getTitle();
      }
    }
//    TextFieldObjectInput<String> originalAlbumTrackReferenceTrackControl = componentFactory.createTextFieldObjectInput(null, originalAlbumTrackReferenceTrackTitle, 300, true, null);
    Button button = componentFactory.createButton(originalAlbumTrackReferenceAlbumId + ":" + originalAlbumTrackReferenceTrackTitle, "click to select the track on the original album");
    button.setOnAction(e -> {
      if (track != null) {
        List<Track> options = new ArrayList<>();
        for (Track aTrack: mediaDb.getTracks()) {
          if (aTrack.getTitle() != null  &&  aTrack.getTitle().equals(track.getTitle())) {
            LOGGER.severe("Adding track option: " + aTrack);
            options.add(aTrack);
          }
        }
        ChoiceDialog<Object> choiceDialog = componentFactory.createChoiceDialog("Track selection", "Select the original album track", "TODO", options.get(0), options);
        choiceDialog.showAndWait();
        LOGGER.severe("Selected track: " + choiceDialog.getSelectedItem().getClass().getName());
        LOGGER.severe("Selected track: " + choiceDialog.getSelectedItem());
      }
    });
    gridPane.add(button, column++, myRow);
    
    // Bonus track
    String bonusTrackText = null;
    if (trackReference != null) {
      bonusTrackText = trackReference.getBonusTrack();
    }
    bonusTrack = componentFactory.createObjectControlTextField(null, bonusTrackText, 300, true, null);
    gridPane.add(bonusTrack.getControl(), column++, myRow);
    
    /*
     * MyTrackInfo
     */
    MyTrackInfo myTrackInfo = null;
    if (trackReference != null) {
      myTrackInfo = trackReference.getMyTrackInfo();
    }
    
    // Identification of the disc of the track reference of the Compilation Track Reference (not Yet editable)
    Disc compilationTrackReferenceDisc = null;
    Album compilationTrackReferenceAlbum = null;
    String compilationTrackReferenceAlbumId = null;
    String compilationTrackReferenceDiscId = null;
    TrackReference compilationTrackReference = null;
    
    if (myTrackInfo != null) {
      compilationTrackReference = myTrackInfo.getCompilationTrackReference();
    }
    
    if (compilationTrackReference != null) {
      compilationTrackReferenceDisc = compilationTrackReference.getDisc();
      compilationTrackReferenceAlbum = compilationTrackReferenceDisc.getAlbum();
      compilationTrackReferenceDiscId = compilationTrackReferenceDisc.getTitle();
      if (compilationTrackReferenceDiscId == null) {
        compilationTrackReferenceDiscId = String.valueOf(compilationTrackReferenceDisc.getDiscNr());
      }
    }

    if (compilationTrackReferenceAlbum != null) {
      compilationTrackReferenceAlbumId = compilationTrackReferenceAlbum.getArtistAndTitle();
      if (compilationTrackReferenceAlbum.isMultiDiscAlbum() &&  (compilationTrackReferenceDiscId != null)) {
        compilationTrackReferenceAlbumId = compilationTrackReferenceAlbumId + " - " + compilationTrackReferenceDiscId;
      }
    }
    ObjectControlTextField<String> compilationTrackReferenceDiscControl = componentFactory.createObjectControlTextField(null, compilationTrackReferenceAlbumId, 300, true, null);
    gridPane.add(compilationTrackReferenceDiscControl.getControl(), column++, myRow);
    
    // Identification of the track of the track reference of the Compilation Track Reference (not Yet editable)
    String compilationTrackReferenceTrackTitle = null;
    if (compilationTrackReference != null) {
      Track compilationTrackReferenceTrack = compilationTrackReference.getTrack();
      if (compilationTrackReferenceTrack != null) {
        compilationTrackReferenceTrackTitle = compilationTrackReferenceTrack.getTitle();
      }
    }
    ObjectControlTextField<String> compilationTrackReferenceTrackControl = componentFactory.createObjectControlTextField(null, compilationTrackReferenceTrackTitle, 300, true, null);
    gridPane.add(compilationTrackReferenceTrackControl.getControl(), column++, myRow);
    
    // MyTrackInfo:Collection
    collectionComboBox = componentFactory.createObjectControlEnumComboBox(goedegep.media.mediadb.model.Collection.NOT_SET, goedegep.media.mediadb.model.Collection.NOT_SET, MediadbPackage.eINSTANCE.getCollection(), true, "If applicable, select the collection in which this track resides");
    goedegep.media.mediadb.model.Collection collection = null;
    if (myTrackInfo != null) {
      collection = myTrackInfo.getCollection();
    }
    collectionComboBox.setValue(collection);
    gridPane.add(collectionComboBox.getControl(), column++, myRow);
    
    // MyTrackInfo:IWant
    iWantComboBox = componentFactory.createObjectControlEnumComboBox(IWant.NOT_SET, IWant.NOT_SET, MediadbPackage.eINSTANCE.getIWant(), true, "Select whether you want this track or not");
    IWant iWant = null;
    if (myTrackInfo != null) {
      iWant = myTrackInfo.getIWant();
    }
    iWantComboBox.setValue(iWant);
    gridPane.add(iWantComboBox.getControl(), column++, myRow);
    
    // IHaveOn
    if (myTrackInfo != null) {
      for (MediumInfo iHaveOnMediumInfo: myTrackInfo.getIHaveOn()) {
        iHaveOnMediumInfoControlsList.add(new MediumInfoControls(customization, gridPane, myRow, column, iHaveOnMediumInfo));
        column += 4;
      }
    }
    
    // New 'I Have on button'
    Button newIHaveOnButton = componentFactory.createButton("+", "Add an extra 'I have on'");
    newIHaveOnButton.setOnAction(e -> {
      gridPane.getChildren().remove(newIHaveOnButton);
      iHaveOnMediumInfoControlsList.add(new MediumInfoControls(customization, gridPane, myRow, column, MediadbFactory.eINSTANCE.createMediumInfo()));
      column += 4;
      gridPane.add(newIHaveOnButton, column, myRow);
    });
    gridPane.add(newIHaveOnButton, column, myRow);
  }
  
  /**
   * Add the track number to the row.
   * <p>
   * The track number is not editable, it's for reference only.
   */
  private void addTrackNrField() {
    int myTrackNr = trackReferenceControls.indexOf(this) + 1;
    String trackNrText = String.valueOf(myTrackNr);
    Label label = componentFactory.createLabel(trackNrText, 30);
    gridPane.add(label, column++, myRow);
  }
  
  private void setControlsToDefaultValues() {
    // Currently no action as we are rebuilding the complete row.
    
  }

  private Node createTrackNodeForNormalAlbum(TrackReference trackReference) {
    TextField textField = componentFactory.createTextField(300, "Enter the track name");
    if (trackReference != null) {
      Track track = trackReference.getTrack();
      if (track != null) {
        textField.setText(track.getTitle());
      }
    }
    
    return textField;
  }
  
  private TextField createTrackNodeForNormalAlbum(String trackTitle) {
    TextField textField = componentFactory.createTextField(300, "Enter the track name");
    textField.setText(trackTitle);
    
    return textField;
  }
  
  /**
   * Create the text for a Track
   * <p>
   * The format of the text is: <artist-name> - <track-title> (<original-album-id>)
   * The <artist-name> is only set if an artist is specified for the track.
   * The <original-album-id> is only set if the original album is a different album then the album which contains this trackReference.
   * 
   * If the track property isn't set, the text '&lt;no track specified&gt;' is returned.
   * If the track doesn't have the originalDisc property set, the text '&lt;no original disc specified&gt;' is returned.
   * 
   * @param trackReference The TrackReference for which a text is to be created.
   * @return the text for the <code>trackReference</code>.
   */
  private String createTrackText() {
    if (track == null) {
      return "<no track specified>";
    }

    TrackReference originalDiscTrackReference = track.getOriginalDiscTrackReference();
    if (originalDiscTrackReference == null) {
      return "no original track reference found";
    }
    
    boolean fullReferenceText = false;
    if (originalDiscTrackReference != trackReference) {
      fullReferenceText = true;
    }
    
    StringBuilder buf = new StringBuilder();
    
    if (fullReferenceText) {
      Disc originalDisc = originalDiscTrackReference.getDisc();
      Album originalAlbum = originalDisc.getAlbum();
      buf.append(originalAlbum.getArtistAndTitle()).append(" - ");
      
      if (originalAlbum.isMultiDiscAlbum()) {
        String discId = originalDisc.getTitle();
        if (discId == null) {
          discId = String.valueOf(originalDisc.getDiscNr());
        }
        buf.append(discId).append(":");
      }
      
      buf.append(originalDiscTrackReference.getTrackNr());
      buf.append(" - ");
    }
    
    Artist trackArtist = track.getArtist();
    if (trackArtist != null) {
      buf.append(trackArtist.getName()).append(" - ");
    }
    
    String trackTitle = track.getTitle();
    buf.append(trackTitle);
    
    return buf.toString();
  }
  
//  private TrackReference getOriginalDiscTrackReference(Track track) {
//    for (TrackReference trackReference: track.getReferredBy()) {
//      LOGGER.severe("Handling: " + trackReference.toString());
//      if (trackReference.getOriginalAlbumTrackReference() == null) {
//        LOGGER.severe("<= " + trackReference);
//        return trackReference;
//      }
//    }
//    
//    return null;
//  }

//  /**
//   * Get the selected track (which may be the original track if not changed by the user).
//   * 
//   * @return the selected Track
//   */
//  public Object getTrack() {
//    return track;
//  }

  /**
   * Get the TrackReference to which this panel applies.
   * 
   * @return the TrackReference to which this panel applies.
   */
  public TrackReference getTrackReference() {
    return trackReference;
  }
  
  public String getBonusTrack() {
    return bonusTrack.getValue();
  }
  
  public Track getReference() {
    return null;
  }

  /**
   * Get the value for the MyTrackInfo.collection attribute.
   * @return
   */
  public goedegep.media.mediadb.model.Collection getCollection() {
    goedegep.media.mediadb.model.Collection collection = collectionComboBox.getValue();
    if (collection == null) {
      collection = goedegep.media.mediadb.model.Collection.NOT_SET;
    }
    
    return collection;
  }
  
  public IWant getIWant() {
    IWant iWant = iWantComboBox.getValue();
    if (iWant == null) {
      iWant = IWant.NOT_SET;
    }
    
    return iWant;
  }

  @Override
  public void setValue(TrackReference objectValue) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public TrackReference getValue() {
//    Track tr = mediaDb.getTrack();
    Track aTrack = getTrack();
    if (aTrack == null) {
      aTrack = MEDIA_DB_FACTORY.createTrack();
      aTrack.setTitle(trackNameTextField.getText());
      aTrack.setArtist(trackReference.getDisc().getAlbum().getArtist());
      aTrack.setOriginalDisc(trackReference.getDisc());
      mediaDb.getTracks().add(aTrack);
    }
    trackReference.setTrack(aTrack);
    // If the Track has changed, the original Track that was referred to may become obsolete. If so we delete it.
//    Track originalTrack = trackReference.getTrack();
//    EmfUtil.setFeatureValue(trackReference, MEDIA_DB_PACKAGE.getTrackReference_Track(), trackReferencePanel.getTrack());
//    if (originalTrack.getReferredBy().isEmpty()) {
//      mediaDb.getTracks().remove(originalTrack);
//    }
    
    EmfUtil.setFeatureValue(trackReference, MEDIA_DB_PACKAGE.getTrackReference_BonusTrack(), getBonusTrack());
    
    boolean isMyTrackInfoNeeded = isMyTrackInfoNeeded();
    if (isMyTrackInfoNeeded) {
      EmfUtil.setFeatureValue(trackReference.getMyTrackInfo(), MEDIA_DB_PACKAGE.getMyTrackInfo_Collection(), getCollection());
      EmfUtil.setFeatureValue(trackReference.getMyTrackInfo(), MEDIA_DB_PACKAGE.getMyTrackInfo_IWant(), getIWant());
    }
    
    return trackReference;
  }
  
  public Track getTrack() {
    for (Track track: mediaDb.getTracks()) {
      if (track.getTitle() != null &&  track.getTitle().equals(trackNameTextField.getText()) && track.getArtist() != null  &&  track.getArtist().equals(trackReference.getDisc().getAlbum().getArtist())) {
        return track;
      }
    }
    
    return null;
  }

  private boolean isMyTrackInfoNeeded() {
    if (getCollection() != goedegep.media.mediadb.model.Collection.NOT_SET) {
      return true;
    }
    
    if (getIWant() != IWant.NOT_SET) {
      return true;
    }
    
    return false;
  }
  
  @Override
  public Node getControl() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getValueAsFormattedText() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected boolean ociDetermineFilledIn() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected TrackReference ociDetermineValue(Object source) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void ociSetErrorFeedback(boolean valid) {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void ociRedrawValue() {
    // TODO Auto-generated method stub
    
  }

  public Track getNewTrack() {
    return newTrack;
  }
  
}
