package goedegep.media.mediadb.albumeditor.guifx;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.jfx.controls.AutoCompleteTextFieldObjectInput;
import goedegep.jfx.controls.ObjectControl;
import goedegep.jfx.controls.TextFieldObjectControl;
import goedegep.media.mediadb.app.ArtistStringConverterAndChecker;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.Player;
import goedegep.util.string.StringUtil;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class PlayerObjectControl implements ObjectControl<Player> {
  private static final Logger LOGGER = Logger.getLogger(PlayerObjectControl.class.getName());

  private List<InvalidationListener> invalidationListeners = new ArrayList<>();
  private BooleanProperty isFilledInProperty = new SimpleBooleanProperty(true);
  private ArtistStringConverterAndChecker artistStringConverterAndChecker;
  
  private AutoCompleteTextFieldObjectInput<Artist> artistObjectControl;
  private TextFieldObjectControl<String> playerInstrumentTextField;

  public PlayerObjectControl(CustomizationFx customization, MediaDb mediaDb) {
    ComponentFactoryFx componentFactory = customization.getComponentFactoryFx();
    artistStringConverterAndChecker = new ArtistStringConverterAndChecker(mediaDb);
    
    artistObjectControl = componentFactory.createObjectControlAutoCompleteTextField(artistStringConverterAndChecker, null, 300, false, "Enter a player");
    artistObjectControl.setOptions(mediaDb.getArtists());
    playerInstrumentTextField = componentFactory.createTextFieldObjectInput(null, null, 300, true, "A comma separated list of instruments");
    
    artistObjectControl.addListener((e) -> notifyListeners());
    playerInstrumentTextField.addListener((e) -> notifyListeners());
  }
  
  /**
   * {@inheritDoc}
   * 
   * If there is a control for a Player, the player has to be filled in. So this method always returns false.
   * 
   * @return false
   */
  @Override
  public boolean isOptional() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BooleanProperty isFilledIn() {
    return isFilledInProperty;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getIsFilledIn() {
    return isFilledInProperty.getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BooleanProperty isValid() {
    return artistObjectControl.isValid();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getIsValid(StringBuilder errorMessageBuffer) {
    return artistObjectControl.getIsValid(errorMessageBuffer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Player getObjectValue() throws ParseException {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setObjectValue(Player objectValue) {
    throw new UnsupportedOperationException();
  }
  
  public void fillFromPlayer(Player player) {
    artistObjectControl.setObjectValue(player.getArtist());
    playerInstrumentTextField.setText(StringUtil.stringCollectionToCommaSeparatedStrings(player.getInstruments()));
  }

  public AutoCompleteTextFieldObjectInput<Artist> getArtistObjectControl() {
    return artistObjectControl;
  }

  public TextFieldObjectControl<String> getPlayerInstrumentTextField() {
    return playerInstrumentTextField;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ObjectProperty<Player> objectValue() {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {
    return artistObjectControl.getId();
  }


  @Override
  public void addListener(InvalidationListener listener) {
    invalidationListeners.add(listener);    
  }

  @Override
  public void removeListener(InvalidationListener listener) {
    invalidationListeners.remove(listener);    
  }
  
  /**
   * Notify the <code>invalidationListeners</code> that something has changed.
   */
  private void notifyListeners() {
    LOGGER.severe("=>");
    for (InvalidationListener invalidationListener: invalidationListeners) {
      LOGGER.severe("Notifying: " + invalidationListener);
      invalidationListener.invalidated(this);
    }
  }

}
