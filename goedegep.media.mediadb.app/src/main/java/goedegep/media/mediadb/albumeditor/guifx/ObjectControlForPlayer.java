package goedegep.media.mediadb.albumeditor.guifx;

import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlTemplate;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.media.common.IMediaDbService;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.Player;
import goedegep.util.string.StringUtil;
import javafx.scene.layout.HBox;

/**
 * This {@ObjectControl} can be used to create/edit a {@link Player}.
 * <p>
 * This ObjectControl has two controls; an autocomplete textfield for the Artist and a textfield for the instruments played by that artist.
 *
 */
public class ObjectControlForPlayer extends ObjectControlTemplate<Player> {
  private static final Logger LOGGER = Logger.getLogger(ObjectControlForPlayer.class.getName());
  

  /**
   * Object control for the artist.
   */
  private ArtistObjectControl artistObjectControl;
  
  /**
   * Object control for the instruments.
   */
  private ObjectControlTextField<String> playerInstrumentTextField;
  
  /**
   * HBox to hold the two controls.
   */
  private HBox hBox;
  

  /**
   * Constructor.
   * 
   * @param customization the GUI customization.
   * @param mediaDb the media database holding the artists.
   */
  public ObjectControlForPlayer(CustomizationFx customization, IMediaDbService mediaDbService) {
    super(customization, false);  // If there is a control for a Player, the player has to be filled in. So this control is never optional.
    
    artistObjectControl = new ArtistObjectControl(customization, mediaDbService);
    playerInstrumentTextField = componentFactory.createObjectControlTextField(null, null, 300, true, "A comma separated list of instruments");
    
    artistObjectControl.addListener((e) -> ociHandleNewUserInput(artistObjectControl));
    playerInstrumentTextField.addListener((e) -> ociHandleNewUserInput(playerInstrumentTextField));
    
    hBox = componentFactory.createHBox(12.0, 2.0);
    hBox.getChildren().addAll(artistObjectControl.getControl(), playerInstrumentTextField.getControl(), getStatusIndicator());
  }

  /**
   * {@inheritDoc}
   * The Artist control is the primary control.
   */
  @Override
  public HBox getControl() {
    return hBox;
  }

  public ObjectControlTextField<String> getPlayerInstrumentTextField() {
    return playerInstrumentTextField;
  }

  /**
   * {@inheritDoc}
   * This ObjectControl is filled in if at least the Artist is filled in.
   */
  @Override
  public boolean ociDetermineFilledIn(Object source) {
    // There is only one control so we don't have to check the source.
    
    return artistObjectControl.isFilledIn();
  }

  @Override
  public Player ociDetermineValue(Object source) {
    Player player = MediadbFactory.eINSTANCE.createPlayer();
    
    Artist artist = artistObjectControl.getValue();
    player.setArtist(artist);
    
    List<String> instruments = StringUtil.commaSeparatedValuesToListOfValues(playerInstrumentTextField.getValue());
    player.getInstruments().addAll(instruments);
    
    return player;
  }

  @Override
  public void ociSetErrorFeedback(boolean valid) {
    artistObjectControl.ociSetErrorFeedback(valid);
    playerInstrumentTextField.ociSetErrorFeedback(valid);
  }

  @Override
  public void ociRedrawValue() {
    artistObjectControl.ociRedrawValue();
    playerInstrumentTextField.ociRedrawValue();
  }

  @Override
  public String getValueAsFormattedText() {
    return getValue().toString();
  }

  @Override
  protected void ociUpdateNonSourceControls(Object source) {
    if (artistObjectControl != source  &&  getValue() != null) {
      artistObjectControl.setObject(getValue().getArtist());
    }
    
    if (playerInstrumentTextField != source  &&  getValue() != null) {
      playerInstrumentTextField.setObject(StringUtil.stringCollectionToCommaSeparatedStrings(getValue().getInstruments()));
    }
  }

}
