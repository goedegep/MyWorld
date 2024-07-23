package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.List;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.media.mediadb.model.AlbumType;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * This class provides a panel to edit Disc information.
 * <p>
 * First row: 'Title:' Disc.title
 */
class DiscPanel extends DiscPanelAbstract {
  
  
  /**
   * One panel per track reference, for editing all track details.
   */
  private List<TrackReferenceAndMyTrackInfoControls> trackReferencePanels = new ArrayList<>();

  
  DiscPanel(CustomizationFx customization, Disc disc, ObjectProperty<String> albumTypeProperty, MediaDb mediaDb) {
    super(customization, disc);
    
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    GridPane gridPane = componentFactory.createGridPane(12.0, 12.0);
    int row = 0;
    int column = 0;
    gridPane.add(componentFactory.createStrongLabel("Track Nr."), column++, row);
    gridPane.add(componentFactory.createStrongLabel("Track"), column++, row);
    gridPane.add(componentFactory.createStrongLabel("Original Album Track Reference"), column++, row);
    gridPane.add(componentFactory.createStrongLabel("Bonus Track"), column++, row);
    gridPane.add(componentFactory.createStrongLabel("Compilation Track Reference"), column++, row, 2, 1);
    column++;
    gridPane.add(componentFactory.createStrongLabel("Collection"), column++, row);
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
        TrackReferenceAndMyTrackInfoControls trackReferencePanel = new TrackReferenceAndMyTrackInfoControls(customization, gridPane, row++, trackReferencePanels, trackReference, AlbumType.NORMAL_ALBUM, mediaDb);
        trackReferencePanels.add(trackReferencePanel);
      }
    }
//    } else { // A disc has at least one track
//      TrackReferenceAndMyTrackInfoControls trackReferencePanel = new TrackReferenceAndMyTrackInfoControls(customization, gridPane, row++, (TrackInfo) null, AlbumType.NORMAL_ALBUM, mediaDb);
//      trackReferencePanels.add(trackReferencePanel);
//    }
    discVBox.getChildren().add(gridPane);
    
    setValue(disc);
    
//    getChildren().add(discVBox);    
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
  public List<Track> getNewTracks() {
    List<Track> newTracks = new ArrayList<>();
    
    for (TrackReferenceAndMyTrackInfoControls trackReferencePanel: trackReferencePanels) {
      Track track = trackReferencePanel.getNewTrack();
      if (track != null) {
        newTracks.add(track);
      }
    }
    
    return newTracks;
  }

  @Override
  protected void ociUpdateNonSourceControls(Object source) {
    // TODO Auto-generated method stub
    
  }
}
