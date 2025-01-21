package goedegep.media.mediadb.albumeditor.guifx;

import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DoubleClickEventDispatcher;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.media.mediadb.app.ArtistStringConverterAndChecker;
import goedegep.media.mediadb.app.MediaDbService;
import goedegep.media.mediadb.app.guifx.ArtistDetailsEditor;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbPackage;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class ArtistObjectControl extends ObjectControlAutoCompleteTextField<Artist> {
  private static final Logger         LOGGER = Logger.getLogger(ArtistObjectControl.class.getName());
  
  /**
   * The GUI customization
   */
  private CustomizationFx customization;
  
  /**
   * The media database service.
   */
  private MediaDbService mediaDbService;
  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param mediaDb the media database.
   */
  public ArtistObjectControl(CustomizationFx customization, MediaDbService mediaDbService) {
    super(customization, new ArtistStringConverterAndChecker(mediaDbService.getMediaDbResource().getEObject()), null, 200, false, "Click and start typing to select an artist, or double click to add a new artist");
    this.customization = customization;
    this.mediaDbService = mediaDbService;
    setOptions(mediaDbService.getMediaDbResource().getEObject().getArtists());
    
    getControl().setEventDispatcher(new DoubleClickEventDispatcher(getControl().getEventDispatcher()));
    getControl().addEventHandler(DoubleClickEventDispatcher.MOUSE_DOUBLE_CLICKED, e -> handleMouseDoubleClickedEvent(getControl(), e));
    
    installChangeListernerForArtists();
  }
  
  /**
   * React to changes in the list of Artists.
   * <p>
   * On any change, update the albumArtistObjectControl values.
   * If the change is a single ADD, set the albumArtistObjectControl to this new value.
   */
  private void installChangeListernerForArtists() {
    Adapter adapter = new EContentAdapter() {
      public void notifyChanged(Notification notification) {
        super.notifyChanged(notification);
        
        if (notification.isTouch()  ||  notification.getEventType() == Notification.REMOVING_ADAPTER) {
          // no action
          return;
        }
        
        if (notification.getFeature() == null  ||  !notification.getFeature().equals(MediadbPackage.eINSTANCE.getMediaDb_Artists())) {
          return;
        }
        
        updateAlbumArtistComboBox();
        
        if (notification.getEventType() == Notification.ADD) {
//          albumArtistObjectControl.setValue((Artist) notification.getNewValue());
        }
      }

    };
    
    mediaDbService.getMediaDbResource().getEObject().eAdapters().add(adapter);
  }

  /**
   * Update the albumArtistObjectControl, in case there is a change in the list of artists.
   */
  private void updateAlbumArtistComboBox() {
    Artist currentSelectedArtist = getValue();
    setOptions(mediaDbService.getMediaDbResource().getEObject().getArtists());
    setObject(currentSelectedArtist);
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
    ArtistDetailsEditor artistDetailsEditor = ArtistDetailsEditor.newInstance(customization, "New artist", mediaDbService);
    artistDetailsEditor.setObject(value);
    artistDetailsEditor.addListener((o) -> {
      setObject(artistDetailsEditor.getObject());
    });
    artistDetailsEditor.show();
  }
}
