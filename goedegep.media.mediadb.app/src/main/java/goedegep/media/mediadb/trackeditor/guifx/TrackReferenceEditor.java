package goedegep.media.mediadb.trackeditor.guifx;

import java.util.function.Consumer;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.jfx.objectcontrols.ObjectControlEnumComboBox;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.jfx.objecteditor.ObjectEditorException;
import goedegep.jfx.objecteditor.ObjectEditorTemplate;
import goedegep.media.common.IMediaDbService;
import goedegep.media.mediadb.app.ArtistStringConverterAndChecker;
import goedegep.media.mediadb.app.guifx.ArtistDetailsEditor;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.util.emf.EmfUtil;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class is a track (actually a {@link TrackReference}) editor.
 * <p>
 * It can be used to create an new Track, or to make changes to an existing Track.
 * This is only to be used for separate track (track in collections) not for tracks for which the album
 * they appear on is also part of the media database.
 *
 */
public class TrackReferenceEditor extends ObjectEditorTemplate<TrackReference> {
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  

  /**
   * The Media Database service.
   */
  private IMediaDbService mediaDbService;
  
  private ArtistStringConverterAndChecker artistStringConverterAndChecker;
  
  /**
   * Control for the artist name.
   */
  private ObjectControlAutoCompleteTextField<Artist> trackArtistObjectControl;
  
  /**
   * Control for the track title.
   * The title is plain text.
   */
  private ObjectControlTextField<String> trackTitleTextFieldObjectControl;
  
  /**
   * The 'I want' information
   */
  private ObjectControlEnumComboBox<IWant> iWantComboBox;

  
  /**
   * Factory method to obtain a new instance of a {@code TrackEditor}.
   * 
   * @param customization the GUI customization.
   * @param mediaDb the media database.
   * @return a newly created {@code TrackEditor}.
   */
  public static TrackReferenceEditor newInstance(CustomizationFx customization, Consumer<TrackReference> addTrackReferenceMethod, IMediaDbService mediaDbService) {
    TrackReferenceEditor trackEditor = new TrackReferenceEditor(customization, addTrackReferenceMethod, mediaDbService);
    trackEditor.performInitialization();
    
    return trackEditor;
  }
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param mediaDb the media database.
   */
  private TrackReferenceEditor(CustomizationFx customization, Consumer<TrackReference> addTrackReferenceMethod, IMediaDbService mediaDbService) {
    super(customization, null, addTrackReferenceMethod);
    
    this.mediaDbService = mediaDbService;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureEditor() {
    setAddObjectTexts("Add track", "Add the track to the media database");
    setUpdateObjectTexts("Update track", "Update the current track");
    setNewObjectTexts("New", "Clear the controls to start entering new track data");
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void createControls() {
    artistStringConverterAndChecker = new ArtistStringConverterAndChecker(mediaDbService.getMediaDbResource().getEObject());
    trackArtistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(artistStringConverterAndChecker, null, 300, false, "Enter the artist");
    trackTitleTextFieldObjectControl = componentFactory.createObjectControlTextField(null, null, 600, false, "The album title is a mandatory field");

    iWantComboBox = componentFactory.createObjectControlEnumComboBox(IWant.NOT_SET, true, "Select whether you want this album or not");
    
    
    objectControlsGroup.addObjectControls(
        trackArtistObjectControl,
        trackTitleTextFieldObjectControl,
        iWantComboBox
    );
    
    updateTrackArtistComboBox();
  }

  /**
   * Update the albumArtistObjectControl, in case there is a change in the list of artists.
   */
  private void updateTrackArtistComboBox() {
    Artist currentSelectedArtist = trackArtistObjectControl.getValue();
    trackArtistObjectControl.setOptions(mediaDbService.getMediaDbResource().getEObject().getArtists());
    trackArtistObjectControl.setObject(currentSelectedArtist);
  }

  @Override
  protected void createEditPanel(VBox rootPane) {
    GridPane gridPane = componentFactory.createGridPane(10.0, 10.0, 10.0);
    
    // First row: 'Artist: <artist>'   'New artist' button
    Label label = componentFactory.createLabel("Artist:");
    gridPane.add(label, 0, 0);
    gridPane.add(trackArtistObjectControl.getControl(), 1, 0);
    gridPane.add(trackArtistObjectControl.getStatusIndicator(), 2, 0);
    Button newArtistButton = componentFactory.createButton("New artist", "The artist of the track has to be selected from the list of known artists. With this button you can add a new artist to the database");
    newArtistButton.setOnAction(e -> ArtistDetailsEditor.newInstance(customization, "New artist", mediaDbService).show());
    gridPane.add(newArtistButton, 3, 0);
    
    // Second row: 'Title: <track-title>'
    label = componentFactory.createLabel("Title:");
    gridPane.add(label, 0, 1);
    gridPane.add(trackTitleTextFieldObjectControl.getControl(), 1, 1);
    gridPane.add(trackTitleTextFieldObjectControl.getStatusIndicator(), 2, 1);
    

    // Third row: 'I want: <i-want>'
    label = componentFactory.createLabel("I want:");
    gridPane.add(label, 0, 2);
    gridPane.add(iWantComboBox.getControl(), 1, 2);
    gridPane.add(iWantComboBox.getStatusIndicator(), 2, 2);
    
    rootPane.getChildren().add(gridPane);
  }

  @Override
  protected void createObject() {
    object = MEDIA_DB_FACTORY.createTrackReference();
  }

  @Override
  protected void fillControlsWithDefaultValues() {
    trackArtistObjectControl.setObject(null);
    trackTitleTextFieldObjectControl.setObject(null);
    iWantComboBox.setObject(IWant.DONT_KNOW);
  }

  @Override
  protected void fillControlsFromObject() {
    fillControlsWithDefaultValues();
    
    if (object == null) {
      return;
    }
    
    Track track = object.getTrack();
    if (track != null) {
      trackArtistObjectControl.setObject(track.getArtist());
      trackTitleTextFieldObjectControl.setObject(track.getTitle());
    }
    
    MyTrackInfo myTrackInfo = object.getMyTrackInfo();
    if (myTrackInfo != null) {
      iWantComboBox.setObject(myTrackInfo.getIWant());
    }
  }

  @Override
  protected void updateObjectFromControls() throws ObjectEditorException {
    // Track
    Track track = mediaDbService.getMediaDbResource().getEObject().getTrack(trackArtistObjectControl.getValue(), trackTitleTextFieldObjectControl.getValue());
    
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getTrackReference_Track(), track);
    
    // Iwant
    MyTrackInfo myTrackInfo = object.getMyTrackInfo();
    if (myTrackInfo == null) {
      myTrackInfo = MEDIA_DB_FACTORY.createMyTrackInfo();
      object.setMyTrackInfo(myTrackInfo);
    }
    EmfUtil.setFeatureValue(myTrackInfo, MEDIA_DB_PACKAGE.getMyTrackInfo_IWant(), iWantComboBox.getValue());
  }

}
