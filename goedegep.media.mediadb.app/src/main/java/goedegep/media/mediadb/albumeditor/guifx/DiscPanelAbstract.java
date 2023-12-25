package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.List;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlAbstract;
import goedegep.jfx.objectcontrols.ObjectControlGroup;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.jfx.objecteditor.EditMode;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Track;
import goedegep.util.emf.EmfUtil;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class provides a panel for editing a disc.
 * <p>
 * It is kind of a mix between an ObjectEditor and an ObjectControl.
 * Editor - upon setting the object (disc), all information is stored in the controls
 * With an object control, the object value is directly updated upon any change in the controls,
 * here this is done only on getting the object value (which performs the editor method fillObjectFromControls)
 */
public abstract class DiscPanelAbstract extends ObjectControlAbstract<Disc> {
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
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
  protected ObjectControlTextField<String> titleControl;
  
  /**
   * The Disc being edited.
   */
  protected Disc disc;
  
  /**
   * Indicates whether we're creating new Disc, or editing an existing Disc.
   */
  protected EditMode editMode = EditMode.NEW;
  
  /**
   * Group for all controls of this class plus all TrackReferenceControls.
   */
  ObjectControlGroup objectControlsGroup;
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param disc the Disc being edited.
   */
  public DiscPanelAbstract(CustomizationFx customization, Disc disc) {
    super(false);
    
    objectControlsGroup = new ObjectControlGroup();
    
    this.disc = disc;
    
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    
    discVBox = componentFactory.createVBox(12.0, 12.0);
    titledPane = new TitledPane("Disc", discVBox);

    HBox titleBox = componentFactory.createHBox(12.0, 12.0);
    Label label = componentFactory.createLabel("Title:");
    titleBox.getChildren().add(label);
    titleControl = componentFactory.createObjectControlTextField(null, null, 300, true, null);
    titleControl.addListener((o)-> updateTitle());
    objectControlsGroup.addObjectControl(titleControl);
    titleBox.getChildren().add(titleControl.getControl());
    discVBox.getChildren().add(titleBox);
    
    editMode = disc != null ? EditMode.EDIT : EditMode.NEW;
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
   * Update the title of the TitledPane.
   */
  private void updateTitle() {
    titledPane.setText(titleControl.getValue() != null ? titleControl.getValue() : "Disc n");
  }
  
  public void fillControlsFromDisc(Disc disc) {
    titleControl.ociSetValue(disc.getTitle());
  }

  public void setControlsToDefautlValues() {
    titleControl.ociSetValue(null);
  }

  public Disc getDisc() {
    return disc;
  }

  public void updateObjectFromControls() {
    EmfUtil.setFeatureValue(disc, MEDIA_DB_PACKAGE.getDisc_Title(), titleControl.getValue());
  }

  public abstract List<Track> getNewTracks();
}
