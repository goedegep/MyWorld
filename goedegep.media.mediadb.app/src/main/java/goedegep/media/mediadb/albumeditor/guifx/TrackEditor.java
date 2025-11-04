package goedegep.media.mediadb.albumeditor.guifx;

import java.util.function.Consumer;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlDuration;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.jfx.objecteditor.ObjectEditorException;
import goedegep.jfx.objecteditor.ObjectEditorTemplate;
import goedegep.media.common.IMediaDbService;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Track;
import goedegep.util.emf.EmfUtil;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class is a track editor.
 * <p>
 * It can be used to create an new Track, or to make changes to an existing Track.
 *
 */
public class TrackEditor extends ObjectEditorTemplate<Track> {
  
  /**
   * Factory for creating the GUI components.
   */
  private ComponentFactoryFx componentFactory;
  
  /**
   * {@code ObjectControl} for the track title.
   */
  private ObjectControlString trackTitleObjectControl;
  
  /**
   * {@code ObjectControl} for the track artist.
   */
  private ArtistObjectControl artistObjectControl;
  
  /**
   * {@code ObjectControl} for the track duration.
   */
  private ObjectControlDuration durationObjectControl;
  
  private PlayersEditPanel playersEditPanel;
  
  private ArtistObjectControl authorObjectControl;
  
  
  /**
   * Factory method to obtain a new instance of a {@code TrackEditor}.
   * 
   * @param customization the GUI customization.
   * @param mediaDb the media database.
   * @return a newly created {@code TrackEditor}.
   */
  public static TrackEditor newInstance(CustomizationFx customization, IMediaDbService mediaDbService) {
    TrackEditor trackEditor = new TrackEditor(customization, mediaDbService);
    trackEditor.performInitialization();
    
    return trackEditor;
  }

  public void setTrackTitle(String title) {
    trackTitleObjectControl.setObject(title);
  }

  public void setArtist(Artist artist) {
    // TODO Auto-generated method stub
    
  }
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param mediaDb the media database.
   */
  private TrackEditor(CustomizationFx customization, IMediaDbService mediaDbService) {
    super(customization, "Track selecter/editor", mediaDbService::addTrackToMediaDatabase);
    
    componentFactory = customization.getComponentFactoryFx();
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
    trackTitleObjectControl = componentFactory.createObjectControlString("Title", 250, false, "Click to edit the track title.");

    objectControlsGroup.addObjectControls(trackTitleObjectControl);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createEditPanel(VBox rootPane) {
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0);
    int row = 0;
    
    Label label = componentFactory.createLabel("Title:");
    gridPane.add(label, 0, row);
    gridPane.add(trackTitleObjectControl.getControl(), 1, row);
    
    rootPane.getChildren().add(gridPane);
  }

  @Override
  protected void createObject() {
    object = MediadbFactory.eINSTANCE.createTrack();
  }

  @Override
  protected void fillControlsWithDefaultValues() {
    trackTitleObjectControl.setObject(null);    
  }

  @Override
  protected void fillControlsFromObject() {
    trackTitleObjectControl.setObject(object.getTitle());
    
  }

  @Override
  protected void updateObjectFromControls() throws ObjectEditorException {
    MediadbPackage mediaDbPackage = MediadbPackage.eINSTANCE;
    
    EmfUtil.setFeatureValue(object, mediaDbPackage.getTrack_Title(), trackTitleObjectControl.getValue());
    
  }

}
