package goedegep.media.mediadb.albumeditor.guifx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.DoubleClickEventDispatcher;
import goedegep.jfx.objectcontrols.ObjectControl;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.jfx.stringconverters.StringConverterAndChecker;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediadbPackage;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.util.datetime.FlexDate;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * This class is an {@link ObjectControl} for a {@link Track}.
 */
public class TrackObjectControl extends ObjectControlAutoCompleteTextField<Track> {
  private static final Logger         LOGGER = Logger.getLogger(TrackObjectControl.class.getName());
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
  /**
   * A {@code StringConverterAndChecker} for converting a track to a String and vice versa.
   */
  private static TrackStringConverterAndChecker trackStringConverter = new TrackStringConverterAndChecker();
  
  /**
   * The media database.
   */
  private static MediaDb mediaDb;
  
  /**
   * The GUI customization.
   */
  private CustomizationFx customization;
  
  /**
   * Constructor.
   * 
   * @param customization - The GUI customization.
   */
  public TrackObjectControl(CustomizationFx customization) {
    super(customization, trackStringConverter, null, 200, false, "TODO");

    this.customization = customization;
    setOptions(mediaDb.getTracks());

    getControl().addEventHandler(DoubleClickEventDispatcher.MOUSE_DOUBLE_CLICKED, e -> handleMouseDoubleClickedEvent(getControl(), e));

    EContentAdapter eContentAdapter = new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(org.eclipse.emf.common.notify.Notification notification) {
        super.notifyChanged(notification);
        LOGGER.info("Change detected: " + notification.toString());

        if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
          // for now no action
          return;
        }

        EObject notifierEObject = (EObject) notification.getNotifier();
        LOGGER.info("notifierEObject: " + notifierEObject.toString());
        Object feature = notification.getFeature();
        if (MEDIA_DB_PACKAGE.getMediaDb_Tracks().equals(feature)) {
          setOptions(mediaDb.getTracks());
        }

      }


    };
    mediaDb.eAdapters().add(eContentAdapter);
  }
  
  /**
   * Handle a double click mouse event. In this case by opening a {@code TrackEditor}.
   * Also a listener is added to the {@code TrackEditor} to update our value based on the value
   * in the editor.
   * 
   * @param trackObjectControlNode the {@code Node} on which the event occurred (not used).
   * @param e the {@code MouseEvent}.
   */
  private void handleMouseDoubleClickedEvent(Node trackObjectControlNode, MouseEvent e) {
    LOGGER.severe("=>");
    TrackEditor trackEditor = TrackEditor.newInstance(customization, mediaDb);
    trackEditor.setObject(value);
    trackEditor.addListener((o) -> {
      setValue(trackEditor.getObject());
    });
    trackEditor.show();
  }
  
  /**
   * {@inheritDoc}
   * If a non valid value is entered, set the text to red, else black.
   */
  @Override
  public void ociSetErrorFeedback(boolean valid) {
    super.ociSetErrorFeedback(valid);
    errorText = "No value filled in for the 'Track'";
  }

  public static void setMediaDb(MediaDb mediaDb) {
    if (mediaDb != TrackObjectControl.mediaDb) {
      TrackObjectControl.mediaDb = mediaDb;
      TrackStringConverterAndChecker.setMediaDb(mediaDb);
    }
  }
}

class TrackStringConverterAndChecker extends StringConverterAndChecker<Track> {
  private static final Logger         LOGGER = Logger.getLogger(TrackStringConverterAndChecker.class.getName());
  private static final MediadbPackage MEDIA_DB_PACKAGE = MediadbPackage.eINSTANCE;
  
  private static MediaDb mediaDb;
  
  private static Map<String, Track> titleToTrackMap = new HashMap<>();
  private static Map<Track, String> trackToTitleMap = new HashMap<>();
  
  public TrackStringConverterAndChecker() {
  }
  
  public static void setMediaDb(MediaDb mediaDb) {
    TrackStringConverterAndChecker.mediaDb = mediaDb;
    fillMaps();
     
    EContentAdapter eContentAdapter = new EContentAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void notifyChanged(org.eclipse.emf.common.notify.Notification notification) {
        super.notifyChanged(notification);
        LOGGER.info("Change detected: " + notification.toString());

        if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
          // for now no action
          return;
        }

        EObject notifierEObject = (EObject) notification.getNotifier();
        LOGGER.info("notifierEObject: " + notifierEObject.toString());
        Object feature = notification.getFeature();
        if (MEDIA_DB_PACKAGE.getMediaDb_Tracks().equals(feature)) {
          fillMaps();
        }

      }
      

    };
    mediaDb.eAdapters().add(eContentAdapter);
    
  }
  
  /**
   * File the {@code titleToTrackMap} and {@code trackToTitleMap} for the tracks in the {@code mediaDb}.
   */
  public static void fillMaps() {
    titleToTrackMap.clear();
    trackToTitleMap.clear();

    for (Track track: mediaDb.getTracks()) {
      String trackText = createTrackText(track);
      
      Track trackInMap = titleToTrackMap.get(trackText);
      if (trackInMap != null) {
        throw new RuntimeException("Duplicate track name: " + trackText + " : " + trackInMap.getTitle());
      }
      titleToTrackMap.put(trackText, track);
      trackToTitleMap.put(track, trackText);
    }
  }
  
  
  /**
   * Create the text for a Track
   * <p>
   * If there is an original disc track reference for the track the format of the text is:<br/>
   * <span style="padding-left: 20px; display:block">
   * {@code  <track-title> (<album-title>:<disc-id>:<track-number>:<release-year>)}<br/>
   * The {@code <disc-id>} and following ':' are only there for multi disc albums.
   * The {@code <disc-id>} is the disc title if set, else it is the disc number.<br/>
   * The {@code <track-number>} is the index number (starting at 1) of the track on the disc.<br/>
   * The {@code <release-year>} is the year of the release date of the album, if available. Else it is set to '-1'.<br/>
   * </span>
   * <p>
   * If there is no original disc track reference for the track the format of the text is:<br/>
   * <span style="padding-left: 20px; display:block">
   * {@code  <track-title> (<artist-name>)}<br/>
   * The {@code <artist-name>} is only there if an artist is specified for the track.
   * </span>
   * 
   * @param track The {@code Track} for which a text is to be created.
   * @return the text for the <code>track</code>.
   */
  private static String createTrackText(Track track) {
    StringBuilder buf = new StringBuilder();

    String trackTitle = track.getTitle();
    buf.append(trackTitle);
    buf.append(" (");

    TrackReference originalDiscTrackReference = track.getOriginalDiscTrackReference();
    if (originalDiscTrackReference != null) {
      Disc disc = originalDiscTrackReference.getDisc();
      Album album = disc.getAlbum();
      buf.append(album.getTitle()).append(":");
      
      if (album.isMultiDiscAlbum()) {
        if (disc.getTitle() != null) {
          buf.append(disc.getTitle());
        } else {
          buf.append(disc.getDiscNr());
        }
        buf.append(":");
      }
      
      int trackNr = originalDiscTrackReference.getTrackNr();
      buf.append(trackNr).append(":");
      
      FlexDate releaseDate = album.getReleaseDate();
      int releaseYear = -1;
      if (releaseDate != null) {
        releaseYear = releaseDate.getYear();
      }
      buf.append(releaseYear);     
    } else {
      Artist artist = track.getTrackArtist();
      if (artist != null) {
        buf.append(artist.getName());
      }
    }
    
    buf.append(")");
    
    return buf.toString();
  }
  
  public List<String> getValues() {
    return new ArrayList<String>(titleToTrackMap.keySet());
  }

  @Override
  public String toString(Track track) {
    if (track == null) {
      return null;
    }
    
    String title = trackToTitleMap.get(track);
    return title;
  }

  @Override
  public Track fromString(String trackTitle) {
//    LOGGER.severe("=> " + trackTitle);
    Track track = titleToTrackMap.get(trackTitle);
//    LOGGER.severe(track.toString());
    return track;
  }

  @Override
  public boolean isValid(String string) {
    Track track = titleToTrackMap.get(string);
//    LOGGER.severe(track.toString());
    return track != null;
  }
  
}
