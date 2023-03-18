package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.List;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.TrackReference;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * This class provides a panel to edit Disc information.
 * <p>
 * First row: 'Title:' Disc.title
 */
class DiscPanel extends Group {
  /**
   * The Disc being edited in this panel.
   */
  private Disc disc;
  
  /**
   * The ObjectInput for the Disc.title.
   */
  private ObjectControlTextField<String> titleControl;
  
  /**
   * One panel per track reference, for editing all track details.
   */
  private List<TrackReferenceAndMyTrackInfoControls> trackReferencePanels = new ArrayList<>();
  
  DiscPanel(CustomizationFx customization, Disc disc, ObjectProperty<String> albumTypeProperty, MediaDb mediaDb) {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    this.disc = disc;
    
    VBox discVBox = componentFactory.createVBox(12.0, 12.0);
    discVBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    HBox titleBox = componentFactory.createHBox(12.0, 12.0);
    Label label = new Label("Title:");
    titleBox.getChildren().add(label);
    titleControl = componentFactory.createObjectControlTextField(null, disc != null ? disc.getTitle() : null, 300, true, null);
    titleBox.getChildren().add(titleControl);
    discVBox.getChildren().add(titleBox);
    
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
        TrackReferenceAndMyTrackInfoControls trackReferencePanel = new TrackReferenceAndMyTrackInfoControls(customization, gridPane, row++, trackReference, AlbumType.NORMAL, mediaDb);
        trackReferencePanels.add(trackReferencePanel);
      }
    } else { // A disc has at least one track
      TrackReferenceAndMyTrackInfoControls trackReferencePanel = new TrackReferenceAndMyTrackInfoControls(customization, gridPane, row++, null, AlbumType.NORMAL, mediaDb);
      trackReferencePanels.add(trackReferencePanel);
    }
    discVBox.getChildren().add(gridPane);
    
    getChildren().add(discVBox);    
  }

  public String getDiscTitle() {
    return titleControl.getObjectValue();
  }

  public Disc getDisc() {
    return disc;
  }

  public List<TrackReferenceAndMyTrackInfoControls> getTrackReferencePanels() {
    return trackReferencePanels;
  }
}
