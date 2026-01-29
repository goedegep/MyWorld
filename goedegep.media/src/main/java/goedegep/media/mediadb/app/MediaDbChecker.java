package goedegep.media.mediadb.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;

/**
 * This class provides methods to perform checks on (a part of) a media database ({@link MediaDb}).
 * <p>
 * The checks are written as predicates for which holds:
 * <ul>
 * <li>
 * They return a boolean result. This is most useful if you run a single check.
 * </li>
 * <li>
 * They have an optional error list parameter. If this value is not null, any error is added to this list.<br/>
 * This is most useful if you e.g. check the complete database and then want to present a list of problems.
 * </li>
 * </ul>
 */
public class MediaDbChecker {
  private static final Logger LOGGER = Logger.getLogger(MediaDbChecker.class.getName());
  
  /**
   * Constructor
   * <p>
   * As this is a utility class, it should not be instantiated. This is guaranteed by this private constructor.
   */
  private MediaDbChecker() {
  }

  /**
   * Check the contents of a <code>MediaDd</code>.
   * <p>
   * The following information is checked:
   * <ul>
   * <li>An {@code Album} must always have its {@code MyInfo} set .
   * <li>There shouldn't be tracks which aren't referred to (see {@link #areThereNoObsoleteTracks}).</li>
   * <li>There shouldn't be artists which aren't referred to (see {@link #areThereNoObsoleteArtists}).</li>
   * <li>Information for each album (see {@link #checkAlbum}).</li>
   * <li>'originalTrackReference' shall only be set to point to itself (as this will change to boolean originalTrack) (see {@link #areThereNoTrackReferencesThatHaveOriginalAlbumTrackReferenceReferringToAnotherTrackReference})</li>
   * </ul>
   * 
   * @param mediaDb the media database to be checked
   * @param errors the list to which errors shall be appended if this value is not null
   */
  public static void checkMediaDb(MediaDb mediaDb, List<Object> errors) {
    areThereNoAlbumsWithoutMyInfo(mediaDb, errors);
    areThereNoObsoleteTracks(mediaDb, errors);
    areThereNoObsoleteArtists(mediaDb, errors);
//    areThereNoTrackReferencesThatHaveOriginalAlbumTrackReferenceReferringToAnotherTrackReference(mediaDb, errors);
//    areThereNoTrackReferencesThatHaveCollectionSet(mediaDb, errors);

    for (Album album: mediaDb.getAlbums()) {
      checkAlbum(album, errors);
    }
    
    if (!errors.isEmpty()) {
      LOGGER.severe("<= number of errors = " + errors.size());
    }
  }
  
  /**
   * Check that there are no albums which don't have {@code myInfo} set.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended if this value is not null
   */
  private static void areThereNoAlbumsWithoutMyInfo(MediaDb mediaDb, List<Object> errors) {
    for (Album album: mediaDb.getAlbums()) {
      if (album.getMyInfo() == null) {
        LOGGER.severe("Album which doesn't have myInfo set: " + album.getArtistAndTitle());
        if (errors != null) {
          MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
          errorInfo.setErrorCode(MediaDbError.ALBUM_WITHOUT_MYINFO);
          errorInfo.setAlbum(album);
          errors.add(errorInfo);
        }
      }
    }
  }

  /**
   * Check that there are no Tracks which aren't referred to.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended if this value is not null
   */
  public static boolean areThereNoObsoleteTracks(MediaDb mediaDb, List<Object> errors) {
    boolean result = true;
    
//    Track trackToRemove = null;

    for (Track track: mediaDb.getTracks()) {
      if (track.getReferredBy().isEmpty()) {
        result = false;
        if (errors != null) {
          MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
          errorInfo.setErrorCode(MediaDbError.OBSOLETE_TRACK);
          errorInfo.setTrack(track);
          errors.add(errorInfo);
//          trackToRemove = track;
        }
        LOGGER.severe("Obsolete track: " + track.getTitle());
      }
    }
    
//    if (trackToRemove != null) {
//      mediaDb.getTracks().remove(trackToRemove);
//    }

    return result;
  }

  /**
   * Check that there are no Artists which aren't referred to.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended if this value is not null
   */
  private static void areThereNoObsoleteArtists(MediaDb mediaDb, List<Object> errors) {
    List<Artist> artistsNotReferredTo = new ArrayList<>(mediaDb.getArtists());
    
    // album artist and album players:artist
    for (Album album: mediaDb.getAlbums()) {
      Artist albumArtist = album.getArtist();
      if (albumArtist != null) {
        artistsNotReferredTo.remove(albumArtist);
      }
      
      for (Player player: album.getPlayers()) {
        Artist playerArtist = player.getArtist();
        if (playerArtist != null) {
          artistsNotReferredTo.remove(playerArtist);
        }
      }
    }
    
    // track author and artist
    for (Track track: mediaDb.getTracks()) {
      for (Artist author: track.getAuthors()) {
        artistsNotReferredTo.remove(author);
      }
      
      Artist artist = track.getArtist();
      if (artist != null) {
        artistsNotReferredTo.remove(artist);
      }
    }
    
    // If an artist is referred to as container artist, by an artist which itself is referred to, that container artist is marked as referred to.
    for (Artist artist: mediaDb.getArtists()) {
      if (!artistsNotReferredTo.contains(artist)) {
        Artist containerArtist = artist.getContainerArtist();
        if (containerArtist != null) {
          if (artistsNotReferredTo.remove(containerArtist)) {
            LOGGER.info("Container artist ok: " + containerArtist.getName() + " referred to by: " + artist.getName());
          }
        }
      }
    }
    
    for (Artist artistNotReferredTo: artistsNotReferredTo) {
      if (errors != null) {
        MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
        errorInfo.setErrorCode(MediaDbError.ARTIST_NOT_REFERRED_TO);
        errorInfo.setArtist(artistNotReferredTo);
        errors.add(errorInfo);
      }
      LOGGER.severe("Artist not referred to: " + artistNotReferredTo.getName());
    }
  }
  
  /**
   * Check the information of an <code>Album</code>.
   * <p>
   * The following information is checked:
   * <ul>
   * <li>the album shall have a title</li>
   * <li>the album shall have an artist if it is not a soundtrack</li>
   * <li>If 'I have on' is set at album level, it shall not be set on any track</li>
   * 
   * </ul>
   * 
   * @param album the <code>Album</code> to be checked
   * @param errors the list to which errors shall be appended if this value is not null
   * @return true if there are no errors, false otherwise
   */
  public static void checkAlbum(Album album, List<Object> errors) {
    isAlbumTitleSet(album, errors);
    isAlbumArtistSetOrIsAlbumASoundtrack(album, errors);
    isIHaveOnNotSetAtTrackLevelIfItIsSetAtAlbumLevel(album, errors);
    isIHaveOnNotTheSameForAllAlbumTracks(album, errors);
    checkMyInfoIWant(album, errors);  // TODO hier verder

    for (Disc disc: album.getDiscs()) {
      checkDisc(disc, errors);
    }
  }

  /**
   * Check that an album has a non-empty title.
   * 
   * @param album the <code>Album</code> to be checked
   * @param errors the list to which errors shall be appended if this value is not null
   * @return true if the {@code album} has a non-empty title
   */
  public static boolean isAlbumTitleSet(Album album, List<Object> errors) {
    if (album.getTitle() == null)  {
      if (errors != null) {
        MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
        errorInfo.setErrorCode(MediaDbError.ALBUM_WITHOUT_TITLE);
        errorInfo.setAlbum(album);
        errors.add(errorInfo);
      }
      LOGGER.severe("No Album title set for album: " + album.getArtistAndTitle());

      return false;
    }
    
    if (album.getTitle().isEmpty()) {
      if (errors != null) {
        MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
        errorInfo.setErrorCode(MediaDbError.ALBUM_WITH_EMPTY_TITLE);
        errorInfo.setAlbum(album);
        errors.add(errorInfo);
      }
      LOGGER.severe("Empty Album title for album: " + album.getArtistAndTitle());

      return false;
    }
    
    return true;
  }
  
  /**
   * Check that an album is either a soundtrack or that its artist is set.
   * 
   * @param album the <code>Album</code> to be checked
   * @param errors the list to which errors shall be appended if this value is not null
   * @return true if the {@code album} is either a soundtrack or that its artist is set, false otherwise..
   */
  public static boolean isAlbumArtistSetOrIsAlbumASoundtrack(Album album, List<Object> errors) {
    if (album.getArtist() == null  &&  !album.isSoundtrack()) {
      if (errors != null) {
        MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
        errorInfo.setErrorCode(MediaDbError.ALBUM_WITHOUT_ARTIST);
        errorInfo.setAlbum(album);
        errors.add(errorInfo);
      }
      LOGGER.severe("No Artist set for album: " + album.getArtistAndTitle());
      
      return false;
    }
    
    return true;
  }
    
  /**
   * Check that 'I have on' is not set at track level if it is set at album level.
   * <p>
   * If IHaveOn is the same for all tracks (on all discs), it should be set at album level and then not at track level.
   * Otherwise it shall be set at track level and not at album level.<br/>
   * There is no 'my information' at disc level. So if I have e.g. one disc completely of a two-disc album then for that disc
   * all tracks have the 'I have on' set.
   * 
   * @param album the {@code Album} to be checked
   * @param errors the list to which errors shall be appended if this value is not null
   * @return true if 'I have on' is not set at track level if it is set at album level, false otherwise.
   */
  public static boolean isIHaveOnNotSetAtTrackLevelIfItIsSetAtAlbumLevel(Album album, List<Object> errors) {
    boolean result = true;
    
    MyInfo albumMyInfo = album.getMyInfo();
    if (albumMyInfo != null) {
      if (!albumMyInfo.getIHaveOn().isEmpty()) {
        // It is set at album level so check that it is not set on any track
        for (Disc disc: album.getDiscs()) {
          for (TrackReference trackReference: disc.getTrackReferences()) {
           MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
           if (myTrackInfo != null) {
             if (!myTrackInfo.getIHaveOn().isEmpty()) {
               LOGGER.severe("IHaveOn set on track level, while it is already set at album level. Album \'" + album.getArtistAndTitle()
               + "\', track \'" + trackReference.getTrack().getTitle() + "\'.");
               if (errors != null) {
                 MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
                 errorInfo.setErrorCode(MediaDbError.IHAVEON_AT_ALBUM_AND_TRACK_LEVEL);
                 errorInfo.setAlbum(album);
                 errorInfo.setTrackReference(trackReference);
                 errors.add(errorInfo);
               }
               result = false;
             }
           }
          }
        }
      }
    }
    
    return result;
  }
  
  
  /**
   * If 'IHaveOn' is the same for all tracks of an album, it shall be set at album level and not at track level.
   * So 'IHaveOn' shall never be the same for all tracks of an album.
   * 
   * @param album the {@code Album} to be checked
   * @param errors the list to which errors shall be appended if this value is not null
   * @return true if 'IHaveOn' is not the same for all tracks or if there aren't any track references. False otherwise.
   */
  public static boolean isIHaveOnNotTheSameForAllAlbumTracks(Album album, List<Object> errors) {
    boolean first = true;
    boolean differentInfoFound = false;
    List<MediumInfo> mediumInfosFirstTrack = null;
    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        if (myTrackInfo != null) {
          List<MediumInfo> trackMediumInfos = myTrackInfo.getIHaveOn();
          if (first) {
            mediumInfosFirstTrack = trackMediumInfos;
            first = false;
          } else {
            if (!mediumInfosAreEqual(mediumInfosFirstTrack, trackMediumInfos)) {
              differentInfoFound = true;
              break;
            }

          }

        } else {
          if (first) {
            mediumInfosFirstTrack = new ArrayList<>();
            first = false;
          } else {
            if (!mediumInfosAreEqual(mediumInfosFirstTrack, new ArrayList<>())) {
              differentInfoFound = true;
              break;
            }
          }
        }
      }
      
      if (differentInfoFound) {
        break;
      }
    }
    
    boolean result = differentInfoFound  ||  mediumInfosFirstTrack == null  ||  mediumInfosFirstTrack.isEmpty();
    if (!result) {
      if (errors != null) {
        LOGGER.severe("IHaveOn the same for all tracks");
        MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
        errorInfo.setErrorCode(MediaDbError.IHAVEON_SAME_FOR_ALL_TRACKS);
        errorInfo.setAlbum(album);
        errors.add(errorInfo);
      }
    }
    
    return result;
  }
  
  /**
   * Check IWant.
   * <p>
   * If IWant is the same for all tracks, it should be set at album level and then not at track level.
   * Otherwise it shall be set at track level and not at album level.
   * 
   * @param album the <code>Album</code> to be checked
   * @param errors the list to which errors shall be appended
   */
  private static void checkMyInfoIWant(Album album, List<Object> errors) {
    MediaDbErrorInfo errorInfo;
    
    // Check that if IWant is set at album level, it is not set at track level.
    MyInfo albumMyInfo = album.getMyInfo();
    if (albumMyInfo != null) {
      IWant iWant = albumMyInfo.getIWant();
      if (iWant != null  && iWant != IWant.NOT_SET) {
        // It is set at album level so check that it is not set on any track
        for (Disc disc: album.getDiscs()) {
          for (TrackReference trackReference: disc.getTrackReferences()) {
           MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
           if (myTrackInfo != null) {
             IWant trackIWant = myTrackInfo.getIWant();
             if (trackIWant != null  &&  trackIWant != IWant.NOT_SET) {
               LOGGER.severe("IWant set on track level, while it is already set at album level. Album: " + album.getArtistAndTitle() + ", track: " + trackReference.getTrack().getTitle());
               LOGGER.severe("IWant at album level: " + iWant + ", at track level: " + trackIWant);
               errorInfo = new MediaDbErrorInfo();
               errorInfo.setErrorCode(MediaDbError.IWANT_AT_ALBUM_AND_TRACK_LEVEL);
               errorInfo.setAlbum(album);
               errorInfo.setTrack(trackReference.getTrack());
               errors.add(errorInfo);
             }
           }
          }
        }
      }
    }
    
    // Check that IHaveOn is not the same for all tracks.
    boolean first = true;
    boolean differentIHaveFound = false;
    IWant iWantFirstTrack = null;
    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        if (myTrackInfo != null) {
          IWant trackIWant = myTrackInfo.getIWant();
          if (first) {
            iWantFirstTrack = trackIWant;
            first = false;
          } else {
            if (!Objects.equals(iWantFirstTrack, trackIWant)) {
              differentIHaveFound = true;
              break;
            }
          }
        } else {
          if (first) {
            iWantFirstTrack = null;
            first = false;
          } else {
            if (!Objects.equals(iWantFirstTrack, null)) {
              differentIHaveFound = true;
              break;
            }
          }
        }
      }
      
      if (differentIHaveFound) {
        break;
      }
    }
    
    if (!differentIHaveFound) {
      LOGGER.info("IWant the same for all tracks");
    }

  }

  /**
   * Check that two lists of type {@code MediumInfo} are equal.
   * <p>
   * The lists are equal if they contains the same {@code MediumInfo} values, the order is not relevant.
   * 
   * @param mediumInfosFirstTrack the first {@code MediumInfo} list
   * @param trackMediumInfos the second {@code MediumInfo} list
   * @return true if the lists are equal
   */
  private static boolean mediumInfosAreEqual(List<MediumInfo> mediumInfosFirstTrack, List<MediumInfo> trackMediumInfos) {
    if (mediumInfosFirstTrack.size() != trackMediumInfos.size()) {
      return false;
    }
    
    List<MediumInfo> handledMediumInfos = new ArrayList<>();
    for (MediumInfo mediumInfoFirstTrack: mediumInfosFirstTrack) {
      for (MediumInfo mediumInfo: trackMediumInfos) {
        if (handledMediumInfos.contains(mediumInfo)) {
          continue;
        }
        
        if (!mediumInfoIsEqual(mediumInfoFirstTrack, mediumInfo)) {
          return false;
        } else {
          handledMediumInfos.add(mediumInfo);
        }
      }
    }
    return true;
  }
  

  private static boolean mediumInfoIsEqual(MediumInfo mediumInfoFirstTrack, MediumInfo mediumInfo) {
    if (!Objects.equals(mediumInfoFirstTrack.getInformationType(), mediumInfo.getInformationType())) {
      return false;
    }
    if (!Objects.equals(mediumInfoFirstTrack.getMediumType(), mediumInfo.getMediumType())) {
      return false;
    }
    if (!Objects.equals(mediumInfoFirstTrack.getSourceBitRate(), mediumInfo.getSourceBitRate())) {
      return false;
    }
    // TODO check source types.
    
    return true;
  }

  /**
   * Check a Disc and optionally repair problems.
   * 
   * @param disc the Disc to check.
   * @param repair if true, problems that can be repaired are repaired.
   */
  private static void checkDisc(Disc disc, List<Object> errors) {
    
    for (TrackReference trackReference: disc.getTrackReferences()) {
      checkTrackReference(trackReference, errors);
    }
    
  }

  private static void checkTrackReference(TrackReference trackReference, List<Object> errors) {
    MediaDbErrorInfo errorInfo;
    Disc disc = trackReference.getDisc();
    Album album = disc.getAlbum();

    Track track = trackReference.getTrack();
    if (track == null) {
      LOGGER.severe("No track set in TrackReference: " + album.getArtistAndTitle() + ", track number: " + trackReference.getTrackNr());
      
//      TrackReference originalAlbumTrackReference = trackReference.getOriginalAlbumTrackReference();
//      if (originalAlbumTrackReference != null) {
//        Track originalAlbumTrackReferenceTrack = originalAlbumTrackReference.getTrack();
//        if (originalAlbumTrackReferenceTrack != null) {
////          LOGGER.severe("Setting track to: " + originalAlbumTrackReferenceTrack);
//          trackReference.setTrack(originalAlbumTrackReferenceTrack);
//        } else {
//          LOGGER.severe("Also no track set in OriginalAlbumTrackReference");
//        }
//      } else {
//        LOGGER.severe("Also no OriginalAlbumTrackReference");
//      }
    } else {
      String trackTitle = track.getTitle();
      if (trackTitle != null) {
        if (trackTitle.toLowerCase().contains("bonus track")) {
          errorInfo = new MediaDbErrorInfo();
          errorInfo.setErrorCode(MediaDbError.BONUS_TRACK_IN_TITLE);
          errorInfo.setAlbum(album);
          errorInfo.setTrackReference(trackReference);
          errors.add(errorInfo);
          LOGGER.severe("Track title contains 'bonus track'. Album: " + album.getArtistAndTitle() + ", Track: " + trackTitle);
        }
      } else {
        errorInfo = new MediaDbErrorInfo();
        errorInfo.setErrorCode(MediaDbError.TRACK_WITHOUT_TITLE);
        errorInfo.setTrackReference(trackReference);
        errorInfo.setAlbum(album);
        errors.add(errorInfo);
        LOGGER.severe("No track title set for track'. Album: " + album.getArtistAndTitle() + ", Track: " + trackTitle);
      }
    }
    
  }
}
