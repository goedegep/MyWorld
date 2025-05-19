package goedegep.media.mediadb.albumeditor.guifx;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.editor.EditorException;
import goedegep.jfx.editor.EditorMultiControlTemplate;
import goedegep.jfx.editor.controls.EditorControlString;
import goedegep.media.mediadb.app.MediaDbService;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Player;
import goedegep.util.emf.EmfUtil;
import goedegep.util.string.StringUtil;
import javafx.scene.Node;

public class PlayerMultiControl extends EditorMultiControlTemplate<Player> {
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  
  /*
   * EditorControls
   */

  /**
   * Object control for the artist.
   */
  private EditorControlArtist artistControl;
  
  /**
   *Editor control for the instruments.
   */
  private EditorControlString instrumentsControl;
  
  
  /**
   * Other
   */
  
  /**
   * the media database service.
   */
  private MediaDbService mediaDbService;

  /**
   * Create an instance of the {@code PlayerEditPanel}.
   * 
   * @param customization the GUI customization
   * @param mediaDbService the media database service.
   * @return the newly created {@code PlayerEditPanel}.
   */
  public static PlayerMultiControl newInstance(CustomizationFx customization, MediaDbService mediaDbService) {
    PlayerMultiControl playerEditPanel = new PlayerMultiControl(customization, mediaDbService);
    playerEditPanel.performInitialization();
    
    return playerEditPanel;
  }
  
  /**
   * Constructor.
   * 
   * @param customization the GUI customization
   * @param mediaDbService the media database service.
   */
  private PlayerMultiControl(CustomizationFx customization, MediaDbService mediaDbService) {
    super(customization, false);
    
    this.mediaDbService = mediaDbService;
    setId("playerEditPanel");
  }


  @Override
  public void createControls() {

    artistControl = EditorControlArtist.newInstance(customization, mediaDbService);
    artistControl.setId("playerArtist");
    artistControl.setLabelBaseText("_Artist");
    
    instrumentsControl = new EditorControlString.Builder("playerInstruments")
        .setWidth(300d)
        .setLabelBaseText("_Instruments")
        .setToolTipText("The instruments played by the artist")
        .setOptional(true)
        .build();
    
    registerEditorComponents(artistControl, instrumentsControl);
  }

  @Override
  public Node getControl() {
    throw new UnsupportedOperationException("This class has no main control. Use getArtistEditorControl() and getInstrumentsEditorControl()");
  }
  
  /**
   * Get the artist {@code EditorControl}.
   * 
   * @return the artist {@code EditorControl}.
   */
  public EditorControlArtist getArtistEditorControl() {
    return artistControl;
  }
  
  /**
   * Get the instruments {@code EditorControl}.
   * 
   * @return the instruments {@code EditorControl}.
   */
  public EditorControlString getInstrumentsEditorControl() {
    return instrumentsControl;
  }

  @Override
  public String getValueAsFormattedText() {
    StringBuilder buf = new StringBuilder();
    Artist artist = artistControl.getCurrentValue();
    if (artist != null) {
      buf.append(artist.getName());
      
      String instruments = instrumentsControl.getCurrentValue();
      if (instruments != null) {
        buf.append(" - ").append(instruments);
      }
    }
    
    return buf.toString();
  }

  @Override
  protected void createEditPanel() {
    // As this is a MultiControl, there is no edit panel
    throw new UnsupportedOperationException();
  }

  @Override
  protected void fillControlsFromObject() {
    artistControl.setObject(value.getArtist());
    instrumentsControl.setObject(StringUtil.stringCollectionToCommaSeparatedStrings(value.getInstruments()));
  }

  @Override
  protected void fillObjectFromControls(Player object, boolean getCurrentValue) throws EditorException {
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getPlayer_Artist(), artistControl.getValue());
    EmfUtil.setFeatureValue(object, MEDIA_DB_PACKAGE.getPlayer_Instruments(), StringUtil.commaSeparatedValuesToListOfValues(instrumentsControl.getValue()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Player createObject() {
    return MEDIA_DB_FACTORY.createPlayer();
  }

  @Override
  protected void setErrorFeedback(boolean valid) {
    // TODO Auto-generated method stub
    
  }
}
