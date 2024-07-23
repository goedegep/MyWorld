package goedegep.media.mediadb.albumeditor.guifx;

import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlTemplate;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.jfx.objectcontrols.ObjectControlTextField;
import goedegep.media.mediadb.app.ArtistStringConverterAndChecker;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;
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
   * Used to convert an {@code Artist} to String and vice versa.
   */
  private ArtistStringConverterAndChecker artistStringConverterAndChecker;
  
  /**
   * Object control for the artist.
   */
  private ObjectControlAutoCompleteTextField<Artist> artistObjectControl;
  
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
  public ObjectControlForPlayer(CustomizationFx customization, MediaDb mediaDb) {
    super(false);  // If there is a control for a Player, the player has to be filled in. So this control is never optional.
    LOGGER.severe("=>");
    
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    artistStringConverterAndChecker = new ArtistStringConverterAndChecker(mediaDb);
    
    artistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(artistStringConverterAndChecker, null, 300, false, "Enter a player");
    artistObjectControl.setOptions(mediaDb.getArtists());
    playerInstrumentTextField = componentFactory.createObjectControlTextField(null, null, 300, true, "A comma separated list of instruments");
    
    artistObjectControl.addListener((e) -> ociHandleNewUserInput(artistObjectControl));
    playerInstrumentTextField.addListener((e) -> ociHandleNewUserInput(playerInstrumentTextField));
    
    hBox = componentFactory.createHBox(12.0, 12.0);
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

//  // TODO should not be needed -> artistObjectControl.ocGetControl()
//  public ObjectControlAutoCompleteTextField<Artist> getArtistObjectControl() {
//    return artistObjectControl;
//  }

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
  public void setValue(final Player player) {
    if (player != null) {
      artistObjectControl.setValue(player.getArtist());
      playerInstrumentTextField.setValue(StringUtil.stringCollectionToCommaSeparatedStrings(player.getInstruments()));
    } else {
      artistObjectControl.setValue(null);
      playerInstrumentTextField.setValue(null);
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
    // TODO Auto-generated method stub
    
  }

}
