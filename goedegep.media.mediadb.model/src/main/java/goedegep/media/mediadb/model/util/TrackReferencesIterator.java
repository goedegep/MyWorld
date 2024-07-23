package goedegep.media.mediadb.model.util;

import java.util.Iterator;
import java.util.logging.Logger;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.TrackCollection;
import goedegep.media.mediadb.model.TrackReference;

/**
 * This class is an {@link Iterator} which iterates over all {@code TrackReference}s in a media database ({@code MediaDb}.
 */
public class TrackReferencesIterator implements Iterator<TrackReference> {
  private static final Logger LOGGER = Logger.getLogger(TrackReferencesIterator.class.getName());

  /**
   * Iterator to iterate over the albums.
   */
  private Iterator<Album> albumsIterator = null;

  /**
   * Iterator to iterate over the discs of an album.
   */
  private Iterator<Disc> discsIterator = null;

  /**
   * Iterator to iterate over the track collections
   */
  private Iterator<TrackCollection> trackCollectionsIterator = null;

  /**
   * Iterator to iterate over the track references of a disc, or the track references of a track collection 
   */
  private Iterator<TrackReference> trackReferencesIterator = null;

  /**
   * If true, the iterator iterates over the album discs tracks, else it iterates over the collections.
   * We first iterate over the album tracks.
   */
  private boolean handlingAlbumTracks =  true;

  /**
   * Constructor
   * 
   * @param mediaDb the media database to iterate over.
   */
  public TrackReferencesIterator(MediaDb mediaDb) {
    albumsIterator = mediaDb.getAlbums().iterator();
    trackCollectionsIterator = mediaDb.getTrackcollections().iterator();

    if (albumsIterator.hasNext()) {
      Album album = albumsIterator.next();
      discsIterator = album.getDiscs().iterator();
      if (discsIterator.hasNext()) {
        Disc disc = discsIterator.next();
        trackReferencesIterator = disc.getTrackReferences().iterator();
      }
    }
    
    if (trackReferencesIterator == null) {
      if (trackCollectionsIterator.hasNext()) {
        TrackCollection trackCollection = trackCollectionsIterator.next();
        trackReferencesIterator = trackCollection.getTrackReferences().iterator();
      }
    }
  }

  /**
   * @InheritDoc
   */
  @Override
  public boolean hasNext() {
    if (trackReferencesIterator == null) {  // Only happens in case of an 'empty' database
      return false;
    } else if (trackReferencesIterator.hasNext()) {
      return true;
    } else {
      return findNext();
    }
  }

  /**
   * @InheritDoc
   */
  @Override
  public TrackReference next() {
    TrackReference trackReference = trackReferencesIterator.next();
    LOGGER.info("=> " + trackReference.toString());

    return trackReference;
  }

  /**
   * Called to initialize the trackReferencesIterator and when trackReferencesIterator has no next.
   * <p>
   * We first iterate over the album tracks.<br/>
   * We iterate over the albums (with the {@code albumsIterator}), for each album we iterate over the discs (with the {@code discIterator})
   * and for each disc we iterate over the track references (with the {@code trackReferencesIterator}. We are taking into account that an album may have no discs
   * and a disc may have no tracks.<br/>
   * Upon reaching the end of the album tracks, we switch to iterating over the track collections (using the {@code trackCollectionsIterator}).
   * Also here we have to take into account that there may be collections without tracks.
   * 
   * @return true if a next is still available, false otherwise.
   */
  private boolean findNext() {
    boolean foundNext = false;

    if (handlingAlbumTracks) {
      if (!discsIterator.hasNext()) {
        findNextDiscIterator();
      }

      if (discsIterator.hasNext()) {
        Disc disc = discsIterator.next();
        trackReferencesIterator = disc.getTrackReferences().iterator();
        foundNext = true;
      }

      if (!foundNext) {
        // switch to handling the track collections
        handlingAlbumTracks = false;
        if (trackCollectionsIterator.hasNext()) {
          TrackCollection trackCollection = trackCollectionsIterator.next();
          trackReferencesIterator = trackCollection.getTrackReferences().iterator();
          foundNext = trackReferencesIterator.hasNext();
        }
      }
    };

    if (!foundNext  &&  !handlingAlbumTracks) {
      if (!trackReferencesIterator.hasNext()) {
        while (trackCollectionsIterator.hasNext()  &&  !foundNext) {
          TrackCollection trackCollection = trackCollectionsIterator.next();
          if (!trackCollection.getTrackReferences().isEmpty()) {
            trackReferencesIterator = trackCollection.getTrackReferences().iterator();
            foundNext = true;
          }
        }
      }
    }

    return foundNext;
  }

  /**
   * Called when discsIterator has no next.
   */
  private void findNextDiscIterator() {
    while (albumsIterator.hasNext()) {
      Album album = albumsIterator.next();
      if (!album.getDiscs().isEmpty()) {
        discsIterator = album.getDiscs().iterator();
        return;
      }
    }
  }

}
