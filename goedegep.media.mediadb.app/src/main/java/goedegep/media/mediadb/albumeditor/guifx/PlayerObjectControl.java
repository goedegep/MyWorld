package goedegep.media.mediadb.albumeditor.guifx;

import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.controls.AutoCompleteTextField;
import goedegep.jfx.objectcontrols.ObjectControlAbstract;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.media.mediadb.app.ArtistStringConverterAndChecker;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.Player;
import goedegep.util.string.StringUtil;

/**
 * This {@ObjectControl} can be used to create/edit a {@link Player}.
 * <p>
 * This ObjectControl has two controls; an autocomplete textfield for the Artist and a textfield for the instruments played by that artist.
 *
 */
public class PlayerObjectControl extends ObjectControlAbstract<Player> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(PlayerObjectControl.class.getName());

  private ArtistStringConverterAndChecker artistStringConverterAndChecker;
  
  private ObjectControlAutoCompleteTextField<Artist> artistObjectControl;
  private ObjectControlTextField<String> playerInstrumentTextField;
  

  public PlayerObjectControl(CustomizationFx customization, MediaDb mediaDb) {
    super(false);  // If there is a control for a Player, the player has to be filled in. So this control is never optional.
    LOGGER.severe("=>");
    
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    artistStringConverterAndChecker = new ArtistStringConverterAndChecker(mediaDb);
    
    artistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(artistStringConverterAndChecker, null, 300, false, "Enter a player");
    artistObjectControl.setOptions(mediaDb.getArtists());
    playerInstrumentTextField = componentFactory.createObjectControlTextField(null, null, 300, true, "A comma separated list of instruments");
    
    artistObjectControl.addListener((e) -> ociHandleNewUserInput(artistObjectControl));
    playerInstrumentTextField.addListener((e) -> ociHandleNewUserInput(playerInstrumentTextField));
  }

  /**
   * {@inheritDoc}
   * The Artist control is the primary control.
   */
  @Override
  public AutoCompleteTextField ocGetControl() {
    return artistObjectControl.ocGetControl();
  }

  // TODO should not be needed -> artistObjectControl.ocGetControl()
  public ObjectControlAutoCompleteTextField<Artist> getArtistObjectControl() {
    return artistObjectControl;
  }

  // TODO should not be needed -> playerInstrumentTextField.ocGetControl()
  public ObjectControlTextField<String> getPlayerInstrumentTextField() {
    return playerInstrumentTextField;
  }

//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public Player ocGetValue() {
//    throw new UnsupportedOperationException();
//  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ocSetValue(final Player player) {
    if (player != null) {
      artistObjectControl.ocSetValue(player.getArtist());
      playerInstrumentTextField.ocSetValue(StringUtil.stringCollectionToCommaSeparatedStrings(player.getInstruments()));
    } else {
      artistObjectControl.ocSetValue(null);
      playerInstrumentTextField.ocSetValue(null);
    }
  }
  
//  public void fillFromPlayer(Player player) {
//    artistObjectControl.ocSetValue(player.getArtist());
//    playerInstrumentTextField.ocSetValue(StringUtil.stringCollectionToCommaSeparatedStrings(player.getInstruments()));
//  }

//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public ObjectProperty<Player> ocValueProperty() {
//    throw new UnsupportedOperationException();
//  }

//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public String ocGetId() {
//    return artistObjectControl.ocGetId();
//  }

//  /**
//   * {@inheritDoc}
//   */
//  public List<InvalidationListener> ociGetInvalidationListeners() {
//    return invalidationListeners;
//  }

  /**
   * {@inheritDoc}
   * This ObjectControl is filled in if at least the Artist is filled in.
   */
  @Override
  public boolean ociDetermineFilledIn() {
    return artistObjectControl.ocIsFilledIn();
  }

  @Override
  public Player ociDetermineValue(Object source) {
    Player player = MediadbFactory.eINSTANCE.createPlayer();
    
    Artist artist = artistObjectControl.ocGetValue();
    player.setArtist(artist);
    
    List<String> instruments = StringUtil.commaSeparatedValuesToListOfValues(playerInstrumentTextField.ocGetValue());
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
  public String ocGetObjectValueAsFormattedText() {
    return ocGetValue().toString();
  }

}
