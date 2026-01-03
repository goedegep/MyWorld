package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlStatusList;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.jfx.objectcontrols.ObjectEditPanelTemplate;
import goedegep.jfx.objecteditor.EditMode;
import goedegep.jfx.objecteditor.ObjectEditorException;
import goedegep.media.common.IMediaDbService;
import goedegep.media.mediadb.app.derivealbuminfo.DiscInfo;
import goedegep.media.mediadb.app.derivealbuminfo.TrackInfo;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.util.emf.EmfUtil;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is an ObjectEditPanel for editing a {@code Disc}.
 * <p>
 * It is kind of a mix between an ObjectEditor and an ObjectControl.
 * Editor - upon setting the object (disc), all information is stored in the controls
 * With an object control, the object value is directly updated upon any change in the controls,
 * here this is done only on getting the object value (which performs the editor method fillObjectFromControls)
 * <p>
 * First row: 'Title:' Disc.title
 */
class DiscEditPanel extends ObjectEditPanelTemplate<Disc> {
  private static final Logger LOGGER = Logger.getLogger(DiscEditPanel.class.getName());
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  
  
  /**
   * The GUI customization.
   */
  private CustomizationFx customization;
  
  private IMediaDbService mediaDbService;
  
  /**
   * The media database.
   */
  private MediaDb mediaDb;
  
  /**
   * Top level panel.
   */
  protected TitledPane titledPane;
  
  /**
   * Content of the titledPane.
   */
  protected VBox discVBox;
  
  /**
   * The ObjectInput for the Disc.title.
   */
  protected ObjectControlString titleControl;
  
  /**
   * A {@code GridPane} to which the track references are added.
   */
  private GridPane gridPane = null;
  
  /**
   * Indicates whether we're creating new Disc, or editing an existing Disc.
   */
  protected EditMode editMode = EditMode.NEW;
  
  /**
   * Index of the first track reference row in the {@code gridPane}.
   */
  private int firstTrackRowInGridPane;
  
  /**
   * Control for handling the status of the list of track references.
   */
  private ObjectControlStatusList trackReferencesObjectControl;
  
  /**
   * One panel per track reference, for editing all track details.
   */
  protected List<TrackReferenceAndMyTrackInfoControls> trackReferenceControls = new ArrayList<>();
  
  
  /**
   * Factory method to obtain a new instance of a {@code DiscEditPanel}.
   * 
   * @param customization the GUI customization.
   * @param mediaDb the media database.
   * @return a newly created {@code MediumInfoListEditor}.
   */
  public static DiscEditPanel newInstance(CustomizationFx customization, IMediaDbService mediaDbService) {
    DiscEditPanel discEditPanel = new DiscEditPanel(customization, mediaDbService);
    discEditPanel.performInitialization();
    
    return discEditPanel;
  }
  
  
  /**
   * Constructor
   * 
   * @param customization the GUI customization.
   * @param mediaDb The Media Database, used to find/add tracks.
   */
  private DiscEditPanel(CustomizationFx customization, IMediaDbService mediaDbService) {
    super(customization);
    
    this.customization = customization;
    this.mediaDbService = mediaDbService;
    mediaDb = mediaDbService.getMediaDbResource().getEObject();
    
  }

  /**
   * Get the title of the disc.
   * 
   * @return The title of the disc, which can be null.
   */
  public String getDiscTitle() {
    return titleControl.getValue();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void createControls() {
    titleControl = componentFactory.createObjectControlString(null, 300, true, "Here you can enter a title for the disc");
    titleControl.setId("disc title");
    titleControl.addListener((o)-> updateTitle());
    
    trackReferencesObjectControl = new ObjectControlStatusList(true);
    trackReferencesObjectControl.setId("track references");
    
    objectControlsGroup.setId("DiscEditPanel");
    objectControlsGroup.addObjectControls(titleControl, trackReferencesObjectControl);
    
    // Note: the discs are created when needed.
  }

  /**
   * {@inheritDoc}
   * 
   * The edit panel is a 'table' (actually a GridPane) with tracks references.<br/>
   * This method only creates the GridPane and the header row.
   * The track rows are filled by {@code fillContolsFromObject}.
   */
  @Override
  protected void createEditPanel() {
    
    discVBox = componentFactory.createVBox(12.0, 12.0);
    titledPane = new TitledPane("Disc", discVBox);
    
    HBox titleBox = componentFactory.createHBox(12.0, 12.0);
    Label label = componentFactory.createLabel("Title:");
    titleBox.getChildren().add(label);
    
    titleBox.getChildren().add(titleControl.getControl());
    titleBox.getChildren().add(titleControl.getStatusIndicator());
    discVBox.getChildren().add(titleBox);
    
    editMode = object != null ? EditMode.EDIT : EditMode.NEW;
    
    gridPane = componentFactory.createGridPane(12.0, 12.0);
    
    discVBox.getChildren().add(gridPane);
    
    
    Button newTrackButton = componentFactory.createButton("Add track", "Click to add a track reference");
    newTrackButton.setOnAction((e) -> addTrackReference());
    discVBox.getChildren().add(newTrackButton);
  }
  
  private void createTracksPane() {
    int column = 0;
    gridPane.add(componentFactory.createStrongLabel("Nr."), column++, 0);
    gridPane.add(componentFactory.createStrongLabel("Title"), column++, 0);
    gridPane.add(componentFactory.createStrongLabel("Bonus Track"), column++, 0);
    gridPane.add(componentFactory.createStrongLabel("I Want"), column++, 0);
    int nrOfIHaveOns = 1;
//    if (disc != null) {
//      for (TrackReference trackReference: disc.getTrackReferences()) {
//        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
//        if (myTrackInfo != null) {
//          nrOfIHaveOns = Math.max(nrOfIHaveOns, myTrackInfo.getIHaveOn().size());
//        }
//      }
//    }
    for (int i = 0; i < nrOfIHaveOns; i++) {
      gridPane.add(componentFactory.createStrongLabel("I Have On"), column++, 0);
    }
    firstTrackRowInGridPane = 1;    
  }
  
  /**
   * Update the title of the TitledPane.
   */
  private void updateTitle() {
    titledPane.setText(titleControl.getValue() != null ? titleControl.getValue() : "Disc n");
  }
  
  /**
   * Add a track reference.
   */
  private void addTrackReference() {
    if (trackReferenceControls.isEmpty()) {
      createTracksPane();
    }
    TrackReferenceAndMyTrackInfoControls trackReferencePanel = TrackReferenceAndMyTrackInfoControls.newInstance(customization, trackReferenceControls, null, mediaDbService);
    trackReferencePanel.setId("trackReferencePanel row " + trackReferenceControls.size());
    trackReferencePanel.createObject();
    trackReferencesObjectControl.getValue().add(trackReferencePanel.getValue());
    addTrackReferenceControlsToGridPane(trackReferenceControls.size(), trackReferencePanel, firstTrackRowInGridPane + trackReferenceControls.size());
    trackReferenceControls.add(trackReferencePanel);
    objectControlsGroup.addObjectControlGroup(trackReferencePanel.getObjectControlsGroup());
  }
  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public void createObject() {
//    object = MEDIA_DB_FACTORY.createDisc();
//  }

  @Override
  protected void fillControlsWithDefaultValues() {
    titleControl.setObject(null);
    
    for (TrackReferenceAndMyTrackInfoControls trackReferenceControl: trackReferenceControls) {
      objectControlsGroup.removeObjectControlGroup(trackReferenceControl.getObjectControlsGroup());
    }
    trackReferenceControls.clear();;
    
  }

  /**
   * {@inheritDoc}
   * 
   * Fill the controls from the specified object (Disc in this case).
   * 
   * @param disc the {@code Disc) from which to fill the controls.
   */
  protected void fillControlsFromObject() {
    setControlsToDefaultValues();

    gridPane.getChildren().clear();
    
    if (object != null) {
      titleControl.setObject(object.getTitle());

      if (!object.getTrackReferences().isEmpty()) {
        createTracksPane();
      }
      int row = firstTrackRowInGridPane;
      int trackNr = 1;
      
      trackReferencesObjectControl.setObject(new ArrayList<>(object.getTrackReferences()));
      for (TrackReference trackReference: object.getTrackReferences()) {
        TrackReferenceAndMyTrackInfoControls trackReferenceAndMyTrackInfoControls = TrackReferenceAndMyTrackInfoControls.newInstance(customization, trackReferenceControls, null, mediaDbService);
        trackReferenceAndMyTrackInfoControls.setId("trackReferencePanel row " + (row));
        trackReferenceControls.add(trackReferenceAndMyTrackInfoControls);
        objectControlsGroup.addObjectControlGroup(trackReferenceAndMyTrackInfoControls.getObjectControlsGroup());
//        trackReferenceAndMyTrackInfoControls.fillControlsFromObject();
        addTrackReferenceControlsToGridPane(trackNr++, trackReferenceAndMyTrackInfoControls, row++);
        if (trackReference != null) {
          trackReferenceAndMyTrackInfoControls.setObject(trackReference);
        } else {
          trackReferenceAndMyTrackInfoControls.createObject();
        }
      }
    }
  }
  
  private void addTrackReferenceControlsToGridPane(int trackNr, TrackReferenceAndMyTrackInfoControls trackReferencePanel, int row) {
    int column = 0;
    
    String trackNrText = String.valueOf(row - firstTrackRowInGridPane + 1);
    Label label = componentFactory.createLabel(trackNrText, 30);
    gridPane.add(label, column++, row);

    gridPane.add(trackReferencePanel.getTrackControl(), column++, row);
    gridPane.add(trackReferencePanel.getBonusTrackControl(), column++, row);
    gridPane.add(trackReferencePanel.getIWantControl(), column++, row);
    gridPane.add(trackReferencePanel.getIHaveOnControl(), column++, row);
//    gridPane.add(trackReferencePanel.getStatusIndicator(), column++, row);
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
        object.getTrackReferences().add(trackReference);
//        TrackReferenceControlsNormalAlbum trackReferencePanel = new TrackReferenceControlsNormalAlbum(customization, gridPane, row++, trackReferencePanels, trackReference, mediaDb);
//        trackReferencePanel.setId("trackReferencePanel row " + (row - 1));
//        trackReferencePanels.add(trackReferencePanel);
//        objectControlsGroup.addObjectControl(trackReferencePanel);
//        trackReferencePanel.fillControlsFromTrackInfo(trackInfo);
      }
    } else { // A disc has at least one track
      TrackReference trackReference = MediadbFactory.eINSTANCE.createTrackReference();
      object.getTrackReferences().add(trackReference);
//      TrackReferenceControlsNormalAlbum trackReferencePanel = new TrackReferenceControlsNormalAlbum(customization, gridPane, row++, trackReferencePanels, trackReference, mediaDb);
//      trackReferencePanel.setId("trackReferencePanel row " + (row - 1));
//      trackReferencePanels.add(trackReferencePanel);
//      objectControlsGroup.addObjectControl(trackReferencePanel);
    }
  }
  
  private void setControlsToDefaultValues() {
    titleControl.setObject(null);
  }

  public void updateObjectFromControls() {
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getDisc_Title(), titleControl.getValue());
    
    List<TrackReference> trackReferences = object.getTrackReferences();
    for (TrackReferenceAndMyTrackInfoControls trackReferenceAndMyTrackInfoControls: trackReferenceControls) {
      try {
        trackReferenceAndMyTrackInfoControls.updateObjectFromControls();
        TrackReference trackReference = trackReferenceAndMyTrackInfoControls.getValue();
        if (!trackReferences.contains(trackReference)) {
          trackReferences.add(trackReference);
        }
      } catch (ObjectEditorException e) {
        e.printStackTrace();
      }
//      updateTrackReferenceFromTrackReferencePanel(trackReferencePanel);
    }
  }

  @Override
  public Node getControl() {
    return titledPane;
  }


  public List<Track> getNewTracks() {
    List<Track> newTracks = new ArrayList<>();
    
//    for (TrackReferenceAndMyTrackInfoControls trackReferencePanel: trackReferenceControls) {
//      Track track = trackReferencePanel.getNewTrack();
//      if (track != null) {
//        newTracks.add(track);
//      }
//    }
    
    LOGGER.severe("<= Number of new tracks: " + newTracks.size());
    return newTracks;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getValueAsFormattedText() {
    StringBuilder buf = new StringBuilder();
    
    buf.append(getDiscTitle()).append(NEW_LINE);
    
    for (TrackReferenceAndMyTrackInfoControls trackReferenceAndMyTrackInfoControls: trackReferenceControls) {
      buf.append(trackReferenceAndMyTrackInfoControls.getValueAsFormattedText()).append(NEW_LINE);
    }
    
    return buf.toString();
  }

}
