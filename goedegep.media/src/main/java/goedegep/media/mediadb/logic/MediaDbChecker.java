package goedegep.media.mediadb.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Player;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;

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
   * Check the contents of a {@code  MediaDb}.
   * <p>
   * Rules for IWant.
   * New:
   * By default (upon creation of an album) IWant is set to NOT_SET at album level and at track level.
   * 
   * Decision and acquiring:
   * Start by setting IWant at album level to 'Don't know' (in the editor). Now you know you still have to judge the album.
   * Start judging the album. Now you can set IWant per track or at album level, but never at both levels.
   * When you acquired the complete album at once, make sure that IWant is set to NOT_SET at track level and at album level. This means that you have the complete album and you want it (IWant is not relevant).
   * When you acquire separate tracks, make sure that IWant is set to NOT_SET at album level. For the tracks you have, set IHave On and unset IWant.
   * For the other tracks set the IWant to your preference. This means that you have some tracks and you want the other tracks.
   * 
   * Finally:
   * If you don't want the album, set IWant to 'NO' at album level.
   * If you have the album, set IWant to NOT_SET at album level. For each track set IHave On and set IWant to NOT_SET.
   * If you have some tracks, set IWant to NOT_SET at album level. For the tracks you have, set IHave On and set IWant to NOT_SET. For the other tracks set the IWant to NO.
   *
   * This leads to the following rules:
   * IWant may not be set at album level and track level at the same time.
   * If you have the complete album, IWant shall be set to NOT_SET at album level and at track level.  TODO
   * If you have some tracks: for the tracks you have, IWant shall be set to NOT_SET. For the other tracks, IWant shall be set to a different value than NOT_SET. TODO
   * 
   * The following information is checked:
   * <ul>
   * <li>For {@code Album}s:</li>
   *   <ul>
   *   <li>An {@code Album} must always have a non-empty title (see {@link #checkThatAllAlbumsHaveANonEmptyTitle}).</li>
   *   <li>An {@code Album} must either be a soundtrack or have its artist set (see {@link #checkThatAllAlbumsAreEitherASoundtrackOrHaveTheirArtistSet}).</li>
   *   <li>An {@code Album} must always have its {@code MyInfo} set (see {@link #checkThatAllAlbumsHaveMyInfoSet}).</li>
   *   <li>An {@code Album} may not have 'I Have on' set at both track level and album level (see {@link #checkThatAllAlbumsDontHaveIHaveOnSetAtTrackLevelAndAlbumLevel}).</li>
   *   <li>An {@code Album} may not have 'I Have on' set to the same value for all tracks,
   *       because then it should be set at album level (see {@link #checkThatAllAlbumsDontHaveTheSameIHaveOnForAllAlbumTracks}).</li>
   *   <li>An {@code Album} may not have 'IWant' set at both track level and album level (see {@link #checkThatAllAlbumsDontHaveIWantSetAtTrackLevelAndAlbumLevel}).</li>
   *   <li></li>
   *   <li>If an album track reference is not for a dummy track (a track for which there is no information in the database),
   *       it shall have a track set (see {@link #checkThatAllAlbumTrackReferencesForNonDummyTracksHaveATrackSet}).</li>
   *   </ul>
   * <li>For {@code Track}s:</li>
   *   <ul>
   *   <li>There shouldn't be {@code Track}s which aren't referred to (see {@link #checkThatAllTracksAreReferredTo}).</li>
   *   </ul>
   * <li>For {@code Artist}s:</li>
   *   <ul>
   *   <li>There shouldn't be {@code Artist}s which aren't referred to (see {@link #checkThatAllArtistsAreReferredTo}).</li>
   *   </ul>
   * <li>Information for each album (see {@link #checkAlbum}).</li>
   * <li>'originalTrackReference' shall only be set to point to itself (as this will change to boolean originalTrack) (see {@link #areThereNoTrackReferencesThatHaveOriginalAlbumTrackReferenceReferringToAnotherTrackReference})</li>
   * </ul>
   * 
   * @param mediaDb the media database to be checked
   * @param errors the list to which errors shall be appended if this value is not null
   */
  public static void checkMediaDb(MediaDb mediaDb, List<Object> errors) {
    checkThatThereAreNoLoopsInTrackReferences(mediaDb, errors);
    if (!errors.isEmpty()) {
      return;
    }
    checkThatAllAlbumsHaveANonEmptyTitle(mediaDb, errors);
    checkThatAllAlbumsAreEitherASoundtrackOrHaveTheirArtistSet(mediaDb, errors);
    checkThatAllAlbumsHaveMyInfoSet(mediaDb, errors);
    checkThatAllAlbumsDontHaveIHaveOnSetAtTrackLevelAndAlbumLevel(mediaDb, errors);
    checkThatAllAlbumsDontHaveTheSameIHaveOnForAllAlbumTracks(mediaDb, errors);
    checkThatAllAlbumsDontHaveIWantSetAtTrackLevelAndAlbumLevel(mediaDb, errors);
    checkThatAllAlbumsThatIHaveCompletelyDontHaveIWantSetAtTrackLevelNorAlbumLevel(mediaDb, errors);
    checkThatAllAlbumsThatIHavePartlyDontHaveIWantSetForTheTracksIHave(mediaDb, errors);
    checkThatAllAlbumTrackReferencesForNonDummyTracksHaveATrackSet(mediaDb, errors);
    
    checkThatAllTracksAreReferredTo(mediaDb, errors);
    checkThatTrackTitlesDontContainBonusTrack(mediaDb, errors);
    checkThatAllTracksHaveANonEmptyTitle(mediaDb, errors);
    
    checkThatAllArtistsAreReferredTo(mediaDb, errors);
//    areThereNoTrackReferencesThatHaveOriginalAlbumTrackReferenceReferringToAnotherTrackReference(mediaDb, errors);
//    areThereNoTrackReferencesThatHaveCollectionSet(mediaDb, errors);

    if (!errors.isEmpty()) {
      LOGGER.severe("<= number of errors = " + errors.size());
    }
  }
  
  public static void checkThatThereAreNoLoopsInTrackReferences(MediaDb mediaDb, List<Object> errors) {
    for (Album album: mediaDb.getAlbums()) {
      for (Disc disc: album.getDiscs()) {
        for (TrackReference trackReference: disc.getTrackReferences()) {
          
          if (trackReferenceHasLoop(trackReference)) {
            LOGGER.severe("Loop in original track reference for album: " + MediaDbUtil.getArtistAndTitle(album) + ", track number: " + MediaDbUtil.getTrackNr(trackReference));
            LOGGER.severe("Loop:");
            TrackReference currentTrackReference = trackReference;
            for ( ; ; ) {
              MyTrackInfo myTrackInfo = currentTrackReference.getMyTrackInfo();
              if (myTrackInfo == null) {
                break;
              }
              
              currentTrackReference = myTrackInfo.getTrackReference();
              if (currentTrackReference == null) {
                break;
              }
              
              EObject container = currentTrackReference.eContainer();
              LOGGER.severe("Container class:" + container.getClass().getName());
              LOGGER.severe(currentTrackReference.toString());
              
              if (currentTrackReference == trackReference) {
                break;
              }
            }
            if (errors != null) {
              MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
              errorInfo.setErrorCode(MediaDbError.LOOP_IN_TRACK_REFERENCES);
              errorInfo.setAlbum(album);
              errorInfo.setTrackReference(trackReference);
              errors.add(errorInfo);
            }
          }
        }
      }
    }
    
  }
  
  /**
   * Check whether a track reference has a track reference loop.
   * 
   * @param trackReference the {@code TrackReference} to check.
   * @return true if there is a loop in the track references.
   */
  public static boolean trackReferenceHasLoop(TrackReference trackReference) {
    TrackReference currentTrackReference = trackReference;
    for ( ; ; ) {
      MyTrackInfo myTrackInfo = currentTrackReference.getMyTrackInfo();
      if (myTrackInfo == null) {
        return false;
      }
      
      currentTrackReference = myTrackInfo.getTrackReference();
      if (currentTrackReference == null) {
        return false;
      }
      
      if (currentTrackReference == trackReference) {
        return true;
      }
    }
  }
  
  /**
   * Check that all albums have their title set.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatAllAlbumsHaveANonEmptyTitle(MediaDb mediaDb, List<Object> errors) {
    for (Album album: mediaDb.getAlbums()) {
      checkThatAlbumHasANonEmptyTitle(album, errors);
    }
  }

  /**
   * Check that an album has a non-empty title.
   * 
   * @param album the {@code Album} to be checked
   * @param errors the list to which errors shall be appended, if this value is not null
   * @return true if the {@code album} has a non-empty title
   */
  public static boolean checkThatAlbumHasANonEmptyTitle(Album album, List<Object> errors) {
    if (album.getTitle() == null)  {
      LOGGER.severe("No Album title set for album: " + MediaDbUtil.getArtistAndTitle(album));
      if (errors != null) {
        MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
        errorInfo.setErrorCode(MediaDbError.ALBUM_WITHOUT_TITLE);
        errorInfo.setAlbum(album);
        errors.add(errorInfo);
      }

      return false;
    }
    
    if (album.getTitle().isEmpty()) {
      LOGGER.severe("Empty Album title for album: " + MediaDbUtil.getArtistAndTitle(album));
      if (errors != null) {
        MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
        errorInfo.setErrorCode(MediaDbError.ALBUM_WITH_EMPTY_TITLE);
        errorInfo.setAlbum(album);
        errors.add(errorInfo);
      }

      return false;
    }
    
    return true;
  }
  
  /**
   * Check that all albums are either a soundtrack or have their artist set.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatAllAlbumsAreEitherASoundtrackOrHaveTheirArtistSet(MediaDb mediaDb, List<Object> errors) {
    for (Album album: mediaDb.getAlbums()) {
      checkThatAlbumIsASoundtrackOrHasAnArtistSet(album, errors);
    }
  }
  
  /**
   * Check that an album is either a soundtrack or that its artist is set.
   * 
   * @param album the <code>Album</code> to be checked
   * @param errors the list to which errors shall be appended if this value is not null
   * @return true if the {@code album} is either a soundtrack or that its artist is set, false otherwise..
   */
  public static boolean checkThatAlbumIsASoundtrackOrHasAnArtistSet(Album album, List<Object> errors) {
    if (album.getArtist() == null  &&  !album.isSoundtrack()) {
      if (errors != null) {
        MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
        errorInfo.setErrorCode(MediaDbError.ALBUM_WITHOUT_ARTIST);
        errorInfo.setAlbum(album);
        errors.add(errorInfo);
      }
      LOGGER.severe("No Artist set for album: " + MediaDbUtil.getArtistAndTitle(album));
      
      return false;
    }
    
    return true;
  }
  
  /**
   * Check that there are no albums which don't have {@code myInfo} set.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatAllAlbumsHaveMyInfoSet(MediaDb mediaDb, List<Object> errors) {
    for (Album album: mediaDb.getAlbums()) {
      if (album.getMyInfo() == null) {
        LOGGER.severe("Album which doesn't have myInfo set: " + MediaDbUtil.getArtistAndTitle(album));
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
   * Check that 'I have on' is not set at track level if it is set at album level, for all {@code Album}s in the database.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatAllAlbumsDontHaveIHaveOnSetAtTrackLevelAndAlbumLevel(MediaDb mediaDb, List<Object> errors) {
    for (Album album: mediaDb.getAlbums()) {
      checkThatIHaveOnIsNotSetAtTrackLevelAndAlbumLevel(album, errors);
    }
  }

  /**
   * Check that 'I have on' is not set at track level if it is set at album level.
   * <p>
   * If IHaveOn is the same for all tracks (on all discs), it should be set at album level and then not at track level.
   * Otherwise it shall be set at track level and not at album level.<br/>
   * There is no {@code MyInfo} at disc level. So if I have e.g. one disc completely of a two-disc album then for that disc
   * all tracks have the 'I have on' set.
   * 
   * @param album the {@code Album} to be checked
   * @param errors the list to which errors shall be appended if this value is not null
   * @return true if 'I have on' is not set at track level if it is set at album level, false otherwise.
   */
  public static void checkThatIHaveOnIsNotSetAtTrackLevelAndAlbumLevel(Album album, List<Object> errors) {
    MyInfo myInfo = album.getMyInfo();
    
    if (myInfo == null  ||  myInfo.getIHaveOn().isEmpty()) {
      return;
    }
    
    // It is set at album level so check that it is not set on any track
    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        if (myTrackInfo != null) {
          if (!myTrackInfo.getIHaveOn().isEmpty()) {
            LOGGER.severe("IHaveOn set on track level, while it is already set at album level. Album \'" + MediaDbUtil.getArtistAndTitle(album)
            + "\', track \'" + trackReference.getTrack().getTitle() + "\'.");
            if (errors != null) {
              MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
              errorInfo.setErrorCode(MediaDbError.IHAVEON_AT_ALBUM_AND_TRACK_LEVEL);
              errorInfo.setAlbum(album);
              errorInfo.setTrackReference(trackReference);
              errors.add(errorInfo);
            }
          }
        }
      }
    }
  }
  
  /**
   * For all {@code Album}s in the database, check that 'IHaveOn' is not the same for all tracks of an album.
   * <p>
   * If 'IHaveOn' is the same for all tracks of an album, it shall be set at album level and not at track level.
   * So 'IHaveOn' shall never be the same for all tracks of an album.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatAllAlbumsDontHaveTheSameIHaveOnForAllAlbumTracks(MediaDb mediaDb, List<Object> errors) {
    for (Album album: mediaDb.getAlbums()) {
      checkThatIHaveIsOnNotTheSameForAllAlbumTracks(album, errors);
    }
  }
  
  /**
   * Check that 'IHaveOn' is not the same for all tracks of an album.
   * <p>
   * If 'IHaveOn' is the same for all tracks of an album, it shall be set at album level and not at track level.
   * So 'IHaveOn' shall never be the same for all tracks of an album.
   * 
   * @param album the {@code Album} to be checked
   * @param errors the list to which errors shall be appended if this value is not null
   * @return true if 'IHaveOn' is not the same for all tracks or if there aren't any track references. False otherwise.
   */
  public static void checkThatIHaveIsOnNotTheSameForAllAlbumTracks(Album album, List<Object> errors) {
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
  }
  
  /**
   * For all {@code Album}s in the database, check that 'IWant' is not set at album level if the album has track references.
   * <p>
   * If an album has track references, 'IWant' shall be set at track level and not at album level.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatAllAlbumsDontHaveIWantSetAtTrackLevelAndAlbumLevel(MediaDb mediaDb, List<Object> errors) {
    for (Album album: mediaDb.getAlbums()) {
      checkThatIWantIsIsNotSetAtTrackLevelAndAlbumLevel(album, errors);
    }
  }
  
  /**
   * Check that 'IWant' is not set at album level if the album has track references.
   * <p>
   * If an album has track references, 'IWant' shall be set at track level and not at album level.
   * 
   * @param album the {@code Album} to be checked
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatIWantIsIsNotSetAtTrackLevelAndAlbumLevel(Album album, List<Object> errors) {
    MyInfo myInfo = album.getMyInfo();
    
    if (myInfo == null  ||  !myInfo.isSetIWant()) {
      return;
    }
    
    // It is set at album level so check that it is not set on any track
    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        if (myTrackInfo != null) {
          if (myTrackInfo.isSetIWant()) {
            LOGGER.severe("IWant set on track level, while it is already set at album level. Album \'" + MediaDbUtil.getArtistAndTitle(album)
            + "\', track \'" + trackReference.getTrack().getTitle() + "\'.");
            if (errors != null) {
              MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
              errorInfo.setErrorCode(MediaDbError.IWANT_AT_ALBUM_AND_TRACK_LEVEL);
              errorInfo.setAlbum(album);
              errorInfo.setTrackReference(trackReference);
              errors.add(errorInfo);
            }
          }
        }
      }
    }
  }
  
  /**
   * Check that all albums that I have completely don't have 'IWant' set at track level and album level.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatAllAlbumsThatIHaveCompletelyDontHaveIWantSetAtTrackLevelNorAlbumLevel(MediaDb mediaDb, List<Object> errors) {
    for (Album album: mediaDb.getAlbums()) {
      checkThatIWantIsIsNotSetAtTrackLevelNorAlbumLevelIfIHaveTheAlbumCompletely(album, errors);
    } 
  }
  
  /**
   * Check that 'IWant' is not set at track level nor album level if I have the album completely.
   * 
   * @param album the {@code Album} to be checked
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatIWantIsIsNotSetAtTrackLevelNorAlbumLevelIfIHaveTheAlbumCompletely(Album album, List<Object> errors) {
    MyInfo myInfo = album.getMyInfo();
    
    if (!MediaDbUtil.haveAlbum(album)) {
      return;
    }
    
    if (myInfo != null  &&  myInfo.isSetIWant()) {
      LOGGER.severe("IWant set at album level, while I have the complete album. Album \'" + MediaDbUtil.getArtistAndTitle(album) + "\'.");
      if (errors != null) {
        MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
        errorInfo.setErrorCode(MediaDbError.IWANT_SET_AT_ALBUM_LEVEL_WHILE_I_HAVE_COMPLETE_ALBUM);
        errorInfo.setAlbum(album);
        errors.add(errorInfo);
      }
    }
        
    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        if (myTrackInfo != null  &&  myTrackInfo.isSetIWant()) {
          LOGGER.severe("IWant set at track level, while I have the complete album. Album \'" + MediaDbUtil.getArtistAndTitle(album)
          + "\', track \'" + trackReference.getTrack().getTitle() + "\'.");
          if (errors != null) {
            MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
            errorInfo.setErrorCode(MediaDbError.IWANT_SET_AT_TRACK_LEVEL_WHILE_I_HAVE_COMPLETE_ALBUM);
            errorInfo.setAlbum(album);
            errorInfo.setTrackReference(trackReference);
            errors.add(errorInfo);
          }
        }
      }
    }
  }
  
  /**
   * Check that all albums that I have partly don't have 'IWant' set for the tracks I have.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatAllAlbumsThatIHavePartlyDontHaveIWantSetForTheTracksIHave(MediaDb mediaDb, List<Object> errors) {
    for (Album album: mediaDb.getAlbums()) {
      checkThatIWantIsIsNotSetAtTrackLevelForTheTracksIHaveIfIHaveTheAlbumPartly(album, errors);
    } 
  }
  
  /**
   * Check that 'IWant' is not set at track level for the tracks I have, if I have the album partly.
   * 
   * @param album the {@code Album} to be checked
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatIWantIsIsNotSetAtTrackLevelForTheTracksIHaveIfIHaveTheAlbumPartly(Album album, List<Object> errors) {
    if (!MediaDbUtil.haveAlbumPartly(album)) {
      return;
    }
    
    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        TrackReference finalTrackReference = MediaDbUtil.getFinalTrackReference(trackReference);
        if (MediaDbUtil.haveTrack(finalTrackReference)) {
          MyTrackInfo myTrackInfo = finalTrackReference.getMyTrackInfo();
          if (myTrackInfo != null  &&  myTrackInfo.isSetIWant()) {
            LOGGER.severe("IWant set at track level, while I have the track. Album \'" + MediaDbUtil.getArtistAndTitle(album)
            + "\', track \'" + trackReference.getTrack().getTitle() + "\'.");
            if (errors != null) {
              MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
              errorInfo.setErrorCode(MediaDbError.IWANT_SET_AT_TRACK_LEVEL_FOR_TRACK_I_HAVE);
              errorInfo.setAlbum(album);
              errorInfo.setTrackReference(trackReference);
              errors.add(errorInfo);
            }
          }
        }
      }
    }
  }

  /**
   * Check that all album track references for non-dummy tracks have a track set.
   * <p>
   * A dummy track is a track for which there is no information in the database.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatAllAlbumTrackReferencesForNonDummyTracksHaveATrackSet(MediaDb mediaDb, List<Object> errors) {
    for (Album album: mediaDb.getAlbums()) {
      for (Disc disc: album.getDiscs()) {
        for (TrackReference trackReference: disc.getTrackReferences()) {
          checkThatTrackReferenceIsDummyTrackOrHasATrackSet(trackReference, errors);
        }
      }
    }
  }
  
  /**
   * Check that a track reference is either for a dummy track (a track for which there is no information in the database) or has a track set.
   * 
   * @param trackReference the {@code TrackReference} to check
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatTrackReferenceIsDummyTrackOrHasATrackSet(TrackReference trackReference, List<Object> errors) {
    Track track = trackReference.getTrack();
    if (track == null) {
      if (!MediaDbUtil.isDummyTrackReference(trackReference)) {
        LOGGER.severe("No track set in non dummy TrackReference: " + MediaDbUtil.getArtistAndTitle(MediaDbUtil.getAlbum(MediaDbUtil.getDisc(trackReference))) + ", track number: " + MediaDbUtil.getTrackNr(trackReference));
        if (errors != null) {
          MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
          errorInfo.setErrorCode(MediaDbError.TRACK_NOT_SET_IN_NON_DUMMY_TRACK_REFERENCE);
          errorInfo.setAlbum(MediaDbUtil.getAlbum(MediaDbUtil.getDisc(trackReference)));
          errorInfo.setTrackReference(trackReference);
          errors.add(errorInfo);
        }
      }
    }
  }

  /**
   * Check that there are no {@code Track}s which aren't referred to.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatAllTracksAreReferredTo(MediaDb mediaDb, List<Object> errors) {
    for (Track track: mediaDb.getTracks()) {
      if (track.getReferredBy().isEmpty()) {
        LOGGER.severe("Track which isn't referred to: " + track.toString());
        if (errors != null) {
          MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
          errorInfo.setErrorCode(MediaDbError.OBSOLETE_TRACK);
          errorInfo.setTrack(track);
          errors.add(errorInfo);
        }
      }
    }
  }
  
  /**
   * Check that there are no {@code Track}s which have 'bonus track' in their title.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatTrackTitlesDontContainBonusTrack(MediaDb mediaDb, List<Object> errors) {
    for (Track track: mediaDb.getTracks()) {
      if (track.getTitle() != null  &&  track.getTitle().toLowerCase().contains("bonus track")) {
        LOGGER.severe("Track title contains 'bonus track': " + track.toString());
        if (errors != null) {
          MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
          errorInfo.setErrorCode(MediaDbError.BONUS_TRACK_IN_TITLE);
          errorInfo.setTrack(track);
          errors.add(errorInfo);
        }
      }
    }
  }

  /**
   * Check that there are no {@code Track}s which don't have a non-empty title.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatAllTracksHaveANonEmptyTitle(MediaDb mediaDb, List<Object> errors) {
    for (Track track: mediaDb.getTracks()) {
      if (track.getTitle() == null  ||  track.getTitle().isEmpty()) {
        LOGGER.severe("Track which doesn't have a non-empty title: " + track.toString());
        if (errors != null) {
          MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
          errorInfo.setErrorCode(MediaDbError.TRACK_WITHOUT_TITLE);
          errorInfo.setTrack(track);
          errors.add(errorInfo);
        }
      }
    }
  }

  /**
   * Check that there are no {@code Artist}s which aren't referred to.
   * 
   * @param mediaDb the media database to check.
   * @param errors the list to which errors shall be appended, if this value is not null
   */
  public static void checkThatAllArtistsAreReferredTo(MediaDb mediaDb, List<Object> errors) {
    // Start with all artists and remove those which are referred to.
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
      LOGGER.severe("Artist not referred to: " + artistNotReferredTo.getName());
      if (errors != null) {
        MediaDbErrorInfo errorInfo = new MediaDbErrorInfo();
        errorInfo.setErrorCode(MediaDbError.ARTIST_NOT_REFERRED_TO);
        errorInfo.setArtist(artistNotReferredTo);
        errors.add(errorInfo);
      }
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

}
