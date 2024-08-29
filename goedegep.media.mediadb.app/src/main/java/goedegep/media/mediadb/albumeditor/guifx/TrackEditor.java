package goedegep.media.mediadb.albumeditor.guifx;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlString;
import goedegep.jfx.objecteditor.ObjectEditorException;
import goedegep.jfx.objecteditor.ObjectEditorTemplate;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Track;
import goedegep.util.emf.EmfUtil;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TrackEditor extends ObjectEditorTemplate<Track> {
  
  /**
   * The media database.
   */
  private MediaDb mediaDb;
  
  /**
   * Factory for creating the GUI components.
   */
  private ComponentFactoryFx componentFactory;
  
  /**
   * {@code ObjectControl} for the track title.
   */
  private ObjectControlString titleObjectControl;
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param mediaDb the media database.
   */
  public TrackEditor(CustomizationFx customization, MediaDb mediaDb) {
    super(customization, "Track selecter/editor");
    
    this.mediaDb = mediaDb;
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
    titleObjectControl = componentFactory.createObjectControlString("Title", 250, false, "Click to edit the track title.");

    objectControlsGroup.addObjectControls(titleObjectControl);
  }

  @Override
  protected void createAttributeEditDescriptors() {
    // No action. This implementation doesn't use edit descriptors.
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
    gridPane.add(titleObjectControl.getControl(), 1, row);
    
    rootPane.getChildren().add(gridPane);
  }

  @Override
  protected void createObject() {
    object = MediadbFactory.eINSTANCE.createTrack();
  }

  @Override
  protected void addObjectToCollection() {
    mediaDb.getTracks().add(object);
    
  }

  @Override
  protected void fillControlsWithDefaultValues() {
    titleObjectControl.setValue(null);    
  }

  @Override
  protected void fillControlsFromObject() {
    titleObjectControl.setValue(object.getTitle());
    
  }

  @Override
  protected void updateObjectFromControls() throws ObjectEditorException {
    MediadbPackage mediaDbPackage = MediadbPackage.eINSTANCE;
    
    EmfUtil.setFeatureValue(object, mediaDbPackage.getTrack_Title(), titleObjectControl.getValue());
    
  }

}
