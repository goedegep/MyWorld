package goedegep.media.mediadb.albumeditor.guifx;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DoubleClickEventDispatcher;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.media.mediadb.app.ArtistStringConverterAndChecker;
import goedegep.media.mediadb.app.guifx.ArtistDetailsEditor;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class ArtistObjectControl extends ObjectControlAutoCompleteTextField<Artist> {
  private static final Logger         LOGGER = Logger.getLogger(ArtistObjectControl.class.getName());
  
  /**
   * The GUI customization
   */
  private CustomizationFx customization;
  
  /**
   * The media database.
   */
  private MediaDb mediaDb;
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param mediaDb the media database.
   */
  public ArtistObjectControl(CustomizationFx customization, MediaDb mediaDb) {
    super(customization, new ArtistStringConverterAndChecker(mediaDb), null, 200, false, "Click and start typing to select an artist, or double click to add a new artist");
    this.customization = customization;
    this.mediaDb = mediaDb;
    setOptions(mediaDb.getArtists());
    
    getControl().setEventDispatcher(new DoubleClickEventDispatcher(getControl().getEventDispatcher()));
    getControl().addEventHandler(DoubleClickEventDispatcher.MOUSE_DOUBLE_CLICKED, e -> handleMouseDoubleClickedEvent(getControl(), e));    
  }
  
  /**
   * Handle a double click mouse event. In this case by opening an {@code ArtistEditor}.
   * Also a listener is added to the {@code TrackEditor} to update our value based on the value
   * in the editor.
   * 
   * @param trackObjectControlNode the {@code Node} on which the event occurred (not used).
   * @param e the {@code MouseEvent}.
   */
  private void handleMouseDoubleClickedEvent(Node trackObjectControlNode, MouseEvent e) {
    LOGGER.severe("=>");
    ArtistDetailsEditor artistDetailsEditor = new ArtistDetailsEditor(customization, "New artist", mediaDb);
    artistDetailsEditor.runEditor();
    artistDetailsEditor.setObject(value);
    artistDetailsEditor.addListener((o) -> {
      setValue(artistDetailsEditor.getObject());
    });
  }
}
