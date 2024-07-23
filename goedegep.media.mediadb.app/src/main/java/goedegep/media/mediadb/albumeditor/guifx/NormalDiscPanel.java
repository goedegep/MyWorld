package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objecteditor.EditMode;
import goedegep.media.mediadb.app.derivealbuminfo.DiscInfo;
import goedegep.media.mediadb.app.derivealbuminfo.TrackInfo;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class NormalDiscPanel extends DiscPanelAbstract {
  private static final Logger LOGGER = Logger.getLogger(TrackReferenceControlsNormalAlbum.class.getName());
  
  private CustomizationFx customization;
  private MediaDb mediaDb;
  private ComponentFactoryFx componentFactory;
  private GridPane gridPane;
  
  /**
   * One panel per track reference, for editing all track details.
   */
  protected List<TrackReferenceControlsNormalAlbum> trackReferencePanels = new ArrayList<>();
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param disc The Disc (Object) we're editing
   * @param mediaDb The Media Database, used to find/add tracks.
   */
  public NormalDiscPanel(CustomizationFx customization, Disc disc, MediaDb mediaDb) {
    super(customization, disc);
    
    this.customization = customization;
    this.mediaDb = mediaDb;
    componentFactory = customization.getComponentFactoryFx();
    
    createGUI();
    
    fillControlsFromObject(disc);
    
  }
  
  /**
   * Create the GUI.
   * <p>
   * The GUI is a 'table' (actually a GridPane) with tracks.
   * This method only creates the GridPane and the header row.
   * The track rows are filled by the TrackReferenceControlsNormalAlbum.
   */
  private void createGUI() {
    gridPane = componentFactory.createGridPane(12.0, 12.0);
    
    discVBox.getChildren().add(gridPane);
  }
    
  /**
   * Fill the control from the specified object (Disc in this case).
   * 
   * @param disc the {@code Disc) from which to fill the controls.
   */
  private void fillControlsFromObject(Disc disc) {
    setControlsToDefaultValues();
    
    gridPane.getChildren().clear();
   
    int row = 0;
    int column = 0;
    gridPane.add(componentFactory.createStrongLabel("Nr."), column++, row);
    gridPane.add(componentFactory.createStrongLabel("Title"), column++, row);
    gridPane.add(componentFactory.createStrongLabel("Bonus Track"), column++, row);
    gridPane.add(componentFactory.createStrongLabel("I Want"), column++, row);
    int nrOfIHaveOns = 0;
    if (disc != null) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        if (myTrackInfo != null) {
          nrOfIHaveOns = Math.max(nrOfIHaveOns, myTrackInfo.getIHaveOn().size());
        }
      }
    }
    for (int i = 0; i < nrOfIHaveOns; i++) {
      gridPane.add(componentFactory.createStrongLabel("Medium type"), column++, row);
      gridPane.add(componentFactory.createStrongLabel("Information type"), column++, row);
      gridPane.add(componentFactory.createStrongLabel("Source type"), column++, row);
      gridPane.add(componentFactory.createStrongLabel("Source bit rate"), column++, row);
    }
    row++;
    if (disc != null) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        TrackReferenceControlsNormalAlbum trackReferencePanel = new TrackReferenceControlsNormalAlbum(customization, gridPane, row++, trackReferencePanels, trackReference, mediaDb);
        trackReferencePanel.setId("trackReferencePanel row " + (row - 1));
        trackReferencePanels.add(trackReferencePanel);
        objectControlsGroup.addObjectControl(trackReferencePanel);
        trackReferencePanel.fillControlsFromObject();
      }
    } else { // A disc has at least one track
      TrackReferenceControlsNormalAlbum trackReferencePanel = new TrackReferenceControlsNormalAlbum(customization, gridPane, row++, trackReferencePanels, (TrackReference) null, mediaDb);
      trackReferencePanel.setId("trackReferencePanel row " + (row - 1));
      trackReferencePanels.add(trackReferencePanel);
      objectControlsGroup.addObjectControl(trackReferencePanel);
      trackReferencePanel.fillControlsFromObject();
    }
  }
  
  public void fillControlsFromDiscInfo(DiscInfo discInfo) {
    setControlsToDefaultValues();
    
    gridPane.getChildren().clear();
    
    int row = 0;
    int column = 0;
    gridPane.add(componentFactory.createStrongLabel("Track Nr."), column++, row);
    gridPane.add(componentFactory.createStrongLabel("Track"), column++, row);
    gridPane.add(componentFactory.createStrongLabel("Bonus Track"), column++, row);
    gridPane.add(componentFactory.createStrongLabel("I Want"), column++, row);
    row++;
    if (discInfo != null) {
      for (TrackInfo trackInfo: discInfo.tracks()) {
        TrackReference trackReference = MediadbFactory.eINSTANCE.createTrackReference();
        disc.getTrackReferences().add(trackReference);
        TrackReferenceControlsNormalAlbum trackReferencePanel = new TrackReferenceControlsNormalAlbum(customization, gridPane, row++, trackReferencePanels, trackReference, mediaDb);
        trackReferencePanel.setId("trackReferencePanel row " + (row - 1));
        trackReferencePanels.add(trackReferencePanel);
        objectControlsGroup.addObjectControl(trackReferencePanel);
        trackReferencePanel.fillControlsFromTrackInfo(trackInfo);
      }
    } else { // A disc has at least one track
      TrackReference trackReference = MediadbFactory.eINSTANCE.createTrackReference();
      disc.getTrackReferences().add(trackReference);
      TrackReferenceControlsNormalAlbum trackReferencePanel = new TrackReferenceControlsNormalAlbum(customization, gridPane, row++, trackReferencePanels, trackReference, mediaDb);
      trackReferencePanel.setId("trackReferencePanel row " + (row - 1));
      trackReferencePanels.add(trackReferencePanel);
      objectControlsGroup.addObjectControl(trackReferencePanel);
    }
  }
  
  private void setControlsToDefaultValues() {
    super.setControlsToDefautlValues();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Disc getValue() {
    updateObjectFromControls();
    
    for (TrackReferenceControlsNormalAlbum trackReferencePanel: trackReferencePanels) {
      if (editMode == EditMode.NEW) {
        disc.getTrackReferences().add(trackReferencePanel.getValue());
      }
      trackReferencePanel.getValue();
    }
    
    return disc;
  }
  
  public void updateObjectFromControls() {
    super.updateObjectFromControls();
    
    for (TrackReferenceControlsNormalAlbum trackReferencePanel: trackReferencePanels) {
      TrackReference trackReference = trackReferencePanel.getValue();
      if (editMode == EditMode.NEW) {
        disc.getTrackReferences().add(trackReference);
      }
//      updateTrackReferenceFromTrackReferencePanel(trackReferencePanel);
    }
  }

  @Override
  public Node getControl() {
    return titledPane;
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
  protected Disc ociDetermineValue(Object source) {
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
  
  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public List<Track> getNewTracks() {
    List<Track> newTracks = new ArrayList<>();
    
    for (TrackReferenceControlsNormalAlbum trackReferencePanel: trackReferencePanels) {
      Track track = trackReferencePanel.getNewTrack();
      if (track != null) {
        newTracks.add(track);
      }
    }
    
    LOGGER.severe("<= Number of new tracks: " + newTracks.size());
    return newTracks;
  }

  @Override
  protected void ociUpdateNonSourceControls(Object source) {
    // TODO Auto-generated method stub
    
  }

}
