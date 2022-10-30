package goedegep.media.mediadb.app;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;

public class MediaDbChecker {
  private static final Logger LOGGER = Logger.getLogger(MediaDbChecker.class.getName());

  /**
   * Check a media database for problems.
   * 
   * @param mediaDb the media database to check.
   * @param repair if set to true, problems that can be fixed are fixed.
   */
  public static void checkMediaDb(MediaDb mediaDb, boolean repair) {
    checkOnObsoleteTracks(mediaDb, repair);
    
    for (Album album: mediaDb.getAlbums()) {
      checkAlbum(album, repair);
    }
  }

  /**
   * Check that there are no Tracks which aren't referred to and optionally delete those Tracks.
   * 
   * @param mediaDb the media database to check.
   * @param repair if true, obsolete Tracks are deleted.
   */
  public static void checkOnObsoleteTracks(MediaDb mediaDb, boolean repair) {
    List<Track> tracksToBeRemoved = new ArrayList<>();
    
    for (Track track: mediaDb.getTracks()) {
      if (track.getReferredBy().isEmpty()) {
        LOGGER.severe("Obsolete track: " + track.getTitle());
        tracksToBeRemoved.add(track);
      }
    }
    
    if (repair) {
      for (Track track: tracksToBeRemoved) {
        LOGGER.severe("Removing obsolete track: " + track.getTitle());
        mediaDb.getTracks().remove(track);
      }
    }
  }
  
  /**
   * Check an Album and optionally repair problems.
   * 
   * @param album the Album to check.
   * @param repair if true, problems that can be repaired are repaired.
   */
  private static void checkAlbum(Album album, boolean repair) {
    for (Disc disc: album.getDiscs()) {
      checkDisc(disc, repair);
    }
  }

  /**
   * Check a Disc and optionally repair problems.
   * 
   * @param disc the Disc to check.
   * @param repair if true, problems that can be repaired are repaired.
   */
  private static void checkDisc(Disc disc, boolean repair) {
    for (TrackReference trackReference: disc.getTrackReferences()) {
      checkTrackReference(trackReference, repair);
    }
    
  }

  private static void checkTrackReference(TrackReference trackReference, boolean repair) {
    Track track = trackReference.getTrack();
    if (track == null) {
      Disc disc = trackReference.getDisc();
      Album album = disc.getAlbum();
//      LOGGER.severe("No track set in TrackReference: " + album.getArtistAndTitle() + ", track number: " + trackReference.getTrackNr());
      
      TrackReference originalAlbumTrackReference = trackReference.getOriginalAlbumTrackReference();
      if (originalAlbumTrackReference != null) {
        Track originalAlbumTrackReferenceTrack = originalAlbumTrackReference.getTrack();
        if (originalAlbumTrackReferenceTrack != null) {
//          LOGGER.severe("Setting track to: " + originalAlbumTrackReferenceTrack);
          trackReference.setTrack(originalAlbumTrackReferenceTrack);
        } else {
          LOGGER.severe("Also no track set in OriginalAlbumTrackReference");
        }
      } else {
        LOGGER.severe("Also no OriginalAlbumTrackReference");
      }
    }
    
  }
}
