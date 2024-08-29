package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlEnumComboBox;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.jfx.objecteditor.ObjectEditorException;
import goedegep.media.mediadb.app.derivealbuminfo.TrackInfo;
import goedegep.media.mediadb.model.Album;
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
import javafx.scene.control.Label;
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
class TrackReferenceControlsNormalAlbum extends TrackReferenceControlsAbstract {
  private static final Logger LOGGER = Logger.getLogger(TrackReferenceControlsNormalAlbum.class.getName());

  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;

  /**
   * The GUI customization.
   */
  private CustomizationFx customization;
  
  /**
   * Factory for creating GUI components.
   */
  private ComponentFactoryFx componentFactory;
  
  /**
   * The {@code TrackReference} to which the controls apply
   */
  private TrackReference trackReference;
  
  /**
   * The list of TrackReferenceAndMyTrackInfoControls of which this control is part.
   */
  private List<TrackReferenceControlsNormalAlbum> trackReferenceControls;
  
  /**
   * The media database.
   */
  private MediaDb mediaDb;
  
  /**
   * Object control for the {@code Track} referred to.
   */
  private TrackObjectControl trackObjectControl;
    
  /**
   * Object contol for the bonus track, which is free format text
   */
  private ObjectControlTextField<String> bonusTrackObjectControl;

  /**
   * MyTrackInfo:IWant object control
   */
  private ObjectControlEnumComboBox<IWant> iWantObjectControl;
    
//  private List<MediumInfoControls> iHaveOnMediumInfoControlsList = new ArrayList<>();
  
  /**
   * Constructor
   * 
   * @param customization The GUI customization
   * @param trackReferenceControls TODO
   * @param trackReference the TrackReference to which the controls apply. This shall be part of a Disc, which shall be part of an Album
   * @param mediaDb the media database
   */
  TrackReferenceControlsNormalAlbum(CustomizationFx customization, List<TrackReferenceControlsNormalAlbum> trackReferenceControls, TrackReference trackReference, MediaDb mediaDb) {
    super(false);
    
    this.customization = customization;
    this.trackReferenceControls = trackReferenceControls;
    this.trackReference = trackReference;
    this.mediaDb = mediaDb;
    
    componentFactory = customization.getComponentFactoryFx();
    trackObjectControl = new TrackObjectControl(customization);
        
//    if (trackReference != null) {
//      track = trackReference.getTrack();
//    }
    
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
  
  public void fillControlsFromObject() {
    setControlsToDefaultValues();
    
    if (trackReference == null) {
      return;
    }
    
//    GridPane gridPane = new GridPane();
//    gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 0);
        
    /*
     * TrackReference fields 
     */
    
    // For a normal album the track title is shown.
    trackObjectControl.setValue(trackReference.getTrack());
//    trackTitleControlTextField = createTrackNodeForNormalAlbum(trackReference);
//    gridPane.add(trackTitleControlTextField.getControl(), column++, myRow);
    
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
    
    // Bonus track
    String bonusTrackText = null;
    if (trackReference != null) {
      bonusTrackText = trackReference.getBonusTrack();
    }
    bonusTrackObjectControl = componentFactory.createObjectControlTextField(null, bonusTrackText, 300, true, "If the track is a bonus track, enter a text here e.g. just 'bonus track' or 'bonus track on Japan release'");
//    gridPane.add(bonusTrackObjectControl.getControl(), column++, myRow);
    
    /*
     * MyTrackInfo
     */
    MyTrackInfo myTrackInfo = null;
    if (trackReference != null) {
      myTrackInfo = trackReference.getMyTrackInfo();
    }
    
    // MyTrackInfo:IWant
    iWantObjectControl = componentFactory.createObjectControlEnumComboBox(IWant.NOT_SET, IWant.NOT_SET, MediadbPackage.eINSTANCE.getIWant(), true, "Select whether you want this track or not");
    IWant iWant = null;
    if (myTrackInfo != null) {
      iWant = myTrackInfo.getIWant();
    }
    iWantObjectControl.setValue(iWant);
//    gridPane.add(iWantObjectControl.getControl(), column++, myRow);
    
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
//      compilationTrackReferenceDisc = compilationTrackReference.getDisc();
//      compilationTrackReferenceAlbum = compilationTrackReferenceDisc.getAlbum();
//      compilationTrackReferenceDiscId = compilationTrackReferenceDisc.getTitle();
//      if (compilationTrackReferenceDiscId == null) {
//        compilationTrackReferenceDiscId = String.valueOf(compilationTrackReferenceDisc.getDiscNr());
//      }
    }

    if (compilationTrackReferenceAlbum != null) {
      compilationTrackReferenceAlbumId = compilationTrackReferenceAlbum.getArtistAndTitle();
      if (compilationTrackReferenceAlbum.isMultiDiscAlbum() &&  (compilationTrackReferenceDiscId != null)) {
        compilationTrackReferenceAlbumId = compilationTrackReferenceAlbumId + " - " + compilationTrackReferenceDiscId;
      }
    }
    ObjectControlTextField<String> compilationTrackReferenceDiscControl = componentFactory.createObjectControlTextField(null, compilationTrackReferenceAlbumId, 300, true, null);
//    gridPane.add(compilationTrackReferenceDiscControl.getControl(), column++, myRow);
    
    // Identification of the track of the track reference of the Compilation Track Reference (not Yet editable)
    String compilationTrackReferenceTrackTitle = null;
    if (compilationTrackReference != null) {
      Track compilationTrackReferenceTrack = compilationTrackReference.getTrack();
      if (compilationTrackReferenceTrack != null) {
        compilationTrackReferenceTrackTitle = compilationTrackReferenceTrack.getTitle();
      }
    }
    ObjectControlTextField<String> compilationTrackReferenceTrackControl = componentFactory.createObjectControlTextField(null, compilationTrackReferenceTrackTitle, 300, true, null);
//    gridPane.add(compilationTrackReferenceTrackControl.getControl(), column++, myRow);
    
    // IHaveOn
    if (myTrackInfo != null) {
      for (MediumInfo iHaveOnMediumInfo: myTrackInfo.getIHaveOn()) {
//        iHaveOnMediumInfoControlsList.add(new MediumInfoControls(customization, gridPane, myRow, column, iHaveOnMediumInfo));
//        column += 4;
      }
    }
    
    // New 'I Have on button'
    Button newIHaveOnButton = componentFactory.createButton("+", "Add an extra 'I have on'");
    newIHaveOnButton.setOnAction(e -> {
//      gridPane.getChildren().remove(newIHaveOnButton);
//      iHaveOnMediumInfoControlsList.add(new MediumInfoControls(customization, gridPane, myRow, column, MediadbFactory.eINSTANCE.createMediumInfo()));
//      column += 4;
//      gridPane.add(newIHaveOnButton, column, myRow);
    });
//    gridPane.add(newIHaveOnButton, column, myRow);
   
  }
  
  public void fillControlsFromTrackInfo(TrackInfo trackInfo) {
//    gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == myRow);
    
//    column = 0;
    
    /*
     * TrackReference fields 
     */
    
    // For a normal album the track title is shown.
//    trackTitleControlTextField = createTrackNodeForNormalAlbum(trackInfo.trackTitle());
//    gridPane.add(trackTitleControlTextField.getControl(), column++, myRow);
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
    
    // Bonus track. No information available on this, so just an empty textfield.
    bonusTrackObjectControl = componentFactory.createObjectControlTextField(null, null, 300, true, "If the track is a bonus track, enter a text here e.g. just 'bonus track' or 'bonus track on Japan release'");
//    gridPane.add(bonusTrackObjectControl.getControl(), column++, myRow);
    
    /*
     * MyTrackInfo
     */
    MyTrackInfo myTrackInfo = null;
    if (trackReference != null) {
      myTrackInfo = trackReference.getMyTrackInfo();
    }
    
    // MyTrackInfo:IWant
    iWantObjectControl = componentFactory.createObjectControlEnumComboBox(IWant.NOT_SET, null, MediadbPackage.eINSTANCE.getIWant(), true, "Select whether you want this track or not");
    iWantObjectControl.setValue(IWant.DONT_KNOW);
//    gridPane.add(iWantObjectControl.getControl(), column++, myRow);
    
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
//    gridPane.add(compilationTrackReferenceDiscControl.getControl(), column++, myRow);
    
    // Identification of the track of the track reference of the Compilation Track Reference (not Yet editable)
    String compilationTrackReferenceTrackTitle = null;
    if (compilationTrackReference != null) {
      Track compilationTrackReferenceTrack = compilationTrackReference.getTrack();
      if (compilationTrackReferenceTrack != null) {
        compilationTrackReferenceTrackTitle = compilationTrackReferenceTrack.getTitle();
      }
    }
    ObjectControlTextField<String> compilationTrackReferenceTrackControl = componentFactory.createObjectControlTextField(null, compilationTrackReferenceTrackTitle, 300, true, null);
//    gridPane.add(compilationTrackReferenceTrackControl.getControl(), column++, myRow);
    
    // IHaveOn
    if (myTrackInfo != null) {
      for (MediumInfo iHaveOnMediumInfo: myTrackInfo.getIHaveOn()) {
//        iHaveOnMediumInfoControlsList.add(new MediumInfoControls(customization, gridPane, myRow, column, iHaveOnMediumInfo));
//        column += 4;
      }
    }
    
    // New 'I Have on button'
    Button newIHaveOnButton = componentFactory.createButton("+", "Add an extra 'I have on'");
    newIHaveOnButton.setOnAction(e -> {
//      gridPane.getChildren().remove(newIHaveOnButton);
//      iHaveOnMediumInfoControlsList.add(new MediumInfoControls(customization, gridPane, myRow, column, MediadbFactory.eINSTANCE.createMediumInfo()));
//      column += 4;
//      gridPane.add(newIHaveOnButton, column, myRow);
    });
//    gridPane.add(newIHaveOnButton, column, myRow);
  }
    
  private void setControlsToDefaultValues() {
    // Currently no action as we are rebuilding the complete row.
    
  }

  private ObjectControlTextField<String> createTrackNodeForNormalAlbum(TrackReference trackReference) {
    ObjectControlTextField<String> textField = componentFactory.createObjectControlTextField(null, null, 300, false, "Enter the track name");
    if (trackReference != null) {
      Track track = trackReference.getTrack();
      if (track != null) {
        textField.setValue(track.getTitle());
      }
    }
    
    return textField;
  }
  
  private ObjectControlTextField<String> createTrackNodeForNormalAlbum(String trackTitle) {
    ObjectControlTextField<String> textField = componentFactory.createObjectControlTextField(null, null, 300, false, "Enter the track name");
    textField.setValue(trackTitle);
    
    return textField;
  }
  
  /**
   * Get the TrackReference to which this panel applies.
   * 
   * @return the TrackReference to which this panel applies.
   */
  public TrackReference getTrackReference() {
    return trackReference;
  }
  
  public String getBonusTrack() {
    return bonusTrackObjectControl.getValue();
  }
  
  public Track getReference() {
    return null;
  }
  
  public IWant getIWant() {
    IWant iWant = iWantObjectControl.getValue();
    if (iWant == null) {
      iWant = IWant.NOT_SET;
    }
    
    return iWant;
  }

//  @Override
//  public TrackReference getValue() {
//    Track aTrack = getTrack();
//    if (aTrack == null) {
//      if (trackReference.getDisc().getAlbum() != null) {  // TODO should not happen
////        newTrack = MEDIA_DB_FACTORY.createTrack();
////        newTrack.setTitle(trackTitleControlTextField.getValue());
////        newTrack.setArtist(trackReference.getDisc().getAlbum().getArtist());
////        newTrack.setOriginalDisc(trackReference.getDisc());
////        mediaDb.getTracks().add(newTrack);
////        aTrack = newTrack;
//      }
//    }
////    trackReference.setTrack(newTrack);
//    // If the Track has changed, the original Track that was referred to may become obsolete. If so we delete it.
////    Track originalTrack = trackReference.getTrack();
////    EmfUtil.setFeatureValue(trackReference, MEDIA_DB_PACKAGE.getTrackReference_Track(), trackReferencePanel.getTrack());
////    if (originalTrack.getReferredBy().isEmpty()) {
////      mediaDb.getTracks().remove(originalTrack);
////    }
//    
//    EmfUtil.setFeatureValue(trackReference, MEDIA_DB_PACKAGE.getTrackReference_BonusTrack(), getBonusTrack());
//    
//    boolean isMyTrackInfoNeeded = isMyTrackInfoNeeded();
//    if (isMyTrackInfoNeeded) {
//      if (trackReference.getMyTrackInfo() == null) {
//        MyTrackInfo myTrackInfo = MEDIA_DB_FACTORY.createMyTrackInfo();
//        trackReference.setMyTrackInfo(myTrackInfo);
//      }
//      EmfUtil.setFeatureValue(trackReference.getMyTrackInfo(), MEDIA_DB_PACKAGE.getMyTrackInfo_IWant(), getIWant());
//    }
//    
//    return trackReference;
//  }
  
  /**
   * Find a track in the media database.
   * <p>
   * 
   * 
   * @return
   */
  public Track getTrack() {
    if (trackReference.getDisc().getAlbum() == null  ||  trackReference.getDisc().getAlbum().getArtist() == null) {
      return null;
    }
    
    Artist artist = trackReference.getDisc().getAlbum().getArtist();
    
    for (Track track: mediaDb.getTracks()) {
      String trackTitle = track.getTitle();
      Artist trackArtist = track.getArtist();
      
      if (trackTitle == null) {
        LOGGER.severe("Found a track without a title: " + track.toString());
      }
      
//      if (trackTitle != null &&  trackTitle.equals(trackTitleControlTextField.getValue()) &&
//          trackArtist != null  &&  trackArtist.equals(trackReference.getDisc().getAlbum().getArtist())) {
//        return track;
//      }
    }
    
    return null;
  }

  private boolean isMyTrackInfoNeeded() {
    if (getIWant() != IWant.NOT_SET) {
      return true;
    }
    
    return false;
  }
  
  @Override
  public Node getControl() {
    return trackObjectControl.getControl();
  }

  @Override
  public String getValueAsFormattedText() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected boolean ociDetermineFilledIn(Object source) {
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
    getValue();
//    LOGGER.severe("<= newTrack: " + (newTrack != null ? newTrack.toString() : "<null>"));
//    return newTrack;
    return null;
  }

  @Override
  protected void ociUpdateNonSourceControls(Object source) {
    // TODO Auto-generated method stub
    
  }
  
}
