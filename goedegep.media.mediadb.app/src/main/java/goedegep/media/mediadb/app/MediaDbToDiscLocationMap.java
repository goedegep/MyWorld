package goedegep.media.mediadb.app;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Collection;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.DiscAndTrackNrs;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MyCompilation;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackCollection;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.media.musicfolder.AlbumOnDiscInfo;
import goedegep.media.musicfolder.MusicFolderContent;
import goedegep.media.musicfolder.TrackFile;
import goedegep.media.musicfolder.TrackOnDiscInfo;
import goedegep.util.file.FileUtils;


/**
 * This class creates a mapping for albums and tracks, that should be available on disc, to their locations on disc.
 * 
 */
public class MediaDbToDiscLocationMap {
  private static final Logger LOGGER = Logger.getLogger(MediaDbToDiscLocationMap.class.getName());
  
  /**
   * Media database which contains the information about which albums and tracks should be available on disc.
   */
  private MediaDb mediaDb;
  
  /**
   * List of AlbumOnDiscInfo not yet used.
   */
  private List<AlbumOnDiscInfo> albumsOnDiscInfoCopy;
  /**
   * List of TrackOnDiscInfo not yet used.
   */
  private List<TrackOnDiscInfo> tracksOnDiscInfoCopy;

  private Map<Album, AlbumDiscLocationInfo> albumToMusicFolderLocationMap = new HashMap<>();
  private Map<Disc, AlbumDiscLocationInfo> albumDiscToMusicFolderLocationMap = new HashMap<>();
  private Map<Track, Path> trackDiscLocationMap = new HashMap<>();
  private List<Object> errors = new ArrayList<>();

  /**
   * Constructor
   * 
   * @param mediaDb the Media Database for which the albums and tracks are to be mapped to their locations on disc.
   * @param musicFolderContent Information on the albums and tracks in the Music Folder.
   */
  public MediaDbToDiscLocationMap(MediaDb mediaDb, MusicFolderContent musicFolderContent) {
    this.mediaDb = mediaDb;
    
    // Start with copies of the albumsOnDiscInfo and tracksOnDiscInfo, so items handled can be removed.
    List<AlbumOnDiscInfo> albumsOnDiscInfo = musicFolderContent.getAlbumsOnDiscInfo();
    albumsOnDiscInfoCopy = new ArrayList<>(albumsOnDiscInfo);
    List<TrackOnDiscInfo> tracksOnDiscInfo = musicFolderContent.getTracksOnDiscInfo();
    tracksOnDiscInfoCopy = new ArrayList<>(tracksOnDiscInfo);
  }

  /**
   * Create the mapping for albums and tracks, that should be available on disc, to their locations on disc.
   */
  public boolean createMediaDbToDiscLocationMap(boolean ignoreMissingBonusTracks) {

    // Handle all albums which I have completely on disc.
    createCompleteAlbumDiscLocationMap(ignoreMissingBonusTracks);

    // Handle all albums which I have partly on disc.
    createPartlyAlbumDiscLocationMap();

    // Handle the separate tracks.
    createTrackLocationMap(ignoreMissingBonusTracks);

    for (TrackOnDiscInfo trackOnDiscInfo: tracksOnDiscInfoCopy) {
      LOGGER.fine("Track on disc NOT found in MediaDb: " + trackOnDiscInfo.getTrackPath().toString());
      MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_ON_DISC_NOT_FOUND_IN_MEDIADB);
      error.setTrackPath(trackOnDiscInfo.getTrackPath());
      errors.add(error);
    }
    
    return errors.isEmpty();
  }

  public Map<Album, AlbumDiscLocationInfo> getAlbumToMusicFolderLocationMap() {
    return albumToMusicFolderLocationMap;
  }

  public Map<Disc, AlbumDiscLocationInfo> getAlbumDiscToMusicFolderLocationMap() {
    return albumDiscToMusicFolderLocationMap;
  }

  public Map<Track, Path> getTrackDiscLocationMap() {
    return trackDiscLocationMap;
  }

  public List<Object> getErrors() {
    return errors;
  }

  /**
   * Create a mapping from Albums in the media database to their location on disc, for albums which I have completely on disc.
   * <p>
   * Based on information from the media database (describing which albums we should have) and from what we have in the 
   * MusicFolder, maps are filled which map:
   * <ul>
   * <li>albums to their location in the MusicFolder (for single disc albums)</li>
   * <li>album discs to their location in the MusicFolder (for multi disc albums)</li>
   * </ul>
   * 
   * @param ignoreMissingBonusTracks If true, missing bonus tracks (for an album which should be available on disc) are not reported as error.
   */
  private void createCompleteAlbumDiscLocationMap(boolean ignoreMissingBonusTracks) {
    /*
     *  In the folder name in the MusicFolder, not all characters are allowed.
     *  So a mapping is used from the artist name and title name to the folder name.
     *  This mapping may lead to characters being removed. Therefore a reconstruction of artist name and title from the folder name
     *  is difficult.
     *  Therefore for each album/disc in the mediaDb, the MusicFolder location is determined. With this info is it looked up in the albumsOnDiscInfo.
     *  If found, the match is added to the albumLocationMap or albumDiscLocationMap, and the album is removed from the (copy of) albumsOnDiscInfo.
     *  If an album should be in the MusicFolder and it is not found, it is an error. So any remaining albums in albumsOnDiscInfoCopy are reported as errors.
     */
    for (Album album: mediaDb.getAlbums()) {
      LOGGER.info("Handling album: " + album.getArtistAndTitle());
      if (album.isMultiDiscAlbum()) {
        // Music folders are per disc, i.e. a disc of an album is treated the same as a single disc album. Only the folder name has a disc specific extension. 
        for (Disc disc: album.getDiscs()) {
          if (MediaDbUtil.haveDiscOnDisc(disc)) {
            LOGGER.info("I should have this album disc on disc");
            AlbumOnDiscInfo albumOnDiscInfo = findAlbumDiscInAlbumsOnDiscInfo(disc, albumsOnDiscInfoCopy, mediaDb, errors);
            if (albumOnDiscInfo != null) {
              LOGGER.info("Found disc of album on disc");
              AlbumDiscLocationInfo albumDiscLocationInfo = new AlbumDiscLocationInfo(albumOnDiscInfo.getAlbumFolderName(), albumOnDiscInfo.getTrackFileNames());
              albumsOnDiscInfoCopy.remove(albumOnDiscInfo);
              albumDiscToMusicFolderLocationMap.put(disc, albumDiscLocationInfo);

              // add the tracks of the album to the trackDiscLocationMap
              addAlbumDiscTracksToTrackDiscLocationMap(disc, mediaDb, albumOnDiscInfo, trackDiscLocationMap, errors, ignoreMissingBonusTracks);
            } else {
              LOGGER.info("Album disc in media database not found on disc: " + album.getArtistAndTitle() + ", disc: " + (album.getDiscs().indexOf(disc) + 1));
              MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.ALBUM_IN_MEDIADB_NOT_FOUND_ON_DISC);
              error.setDisc(disc);
              error.setAlbum(album);
              errors.add(error);
            }
          }
        }
      } else {
        // Single disc album
        // Music folders are per album.
        if (MediaDbUtil.haveAlbumOnDisc(album, errors)) {
          LOGGER.info("I should have this album in the MusicFolder");
          AlbumOnDiscInfo albumOnDiscInfo = findAlbumInAlbumsOnDiscInfo(album, albumsOnDiscInfoCopy, mediaDb, errors);
          if (albumOnDiscInfo != null) {
            LOGGER.info("Found album in MusicFolder");
            AlbumDiscLocationInfo albumDiscLocationInfo = new AlbumDiscLocationInfo(albumOnDiscInfo.getAlbumFolderName(), albumOnDiscInfo.getTrackFileNames());
            albumsOnDiscInfoCopy.remove(albumOnDiscInfo);
            albumToMusicFolderLocationMap.put(album, albumDiscLocationInfo);

            // add the tracks of the album to the trackDiscLocationMap
            addAlbumDiscTracksToTrackDiscLocationMap(album.getDiscs().get(0), mediaDb, albumOnDiscInfo, trackDiscLocationMap, errors, ignoreMissingBonusTracks);
          } else {
            LOGGER.info("Album in media database not found on disc: " + album.getArtistAndTitle());
            MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.ALBUM_IN_MEDIADB_NOT_FOUND_ON_DISC);
            error.setAlbum(album);
            errors.add(error);
          }
        }
      }
    }

    for (AlbumOnDiscInfo albumOnDiscInfo: albumsOnDiscInfoCopy) {
      LOGGER.info("Album on disc NOT found in MediaDb: " + albumOnDiscInfo.getIssueDate() + " - " + albumOnDiscInfo.getArtist() + " - " + albumOnDiscInfo.getTitle());
      MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.ALBUM_ON_DISC_NOT_FOUND_IN_MEDIADB);
      error.setAlbumOnDiscInfo(albumOnDiscInfo);
      errors.add(error);
    }
  }

  /**
   * Create a mapping from Albums in the media database to their location on disc, for albums which I have partly on disc.
   * <p>
   * Based on information from the media database (describing which albums we should have partly) and from what we have in the 
   * MusicFolder, a map is filled which maps album tracks to their location in th Music Folder.
   */
  private void createPartlyAlbumDiscLocationMap() {
    for (Album album: mediaDb.getAlbums()) {

      LOGGER.info("Handling album: " + (album.isSetArtist() ? album.getArtist().getName() : "<no artist>") + " - " + album.getTitle());

      List<DiscAndTrackNrs> discAndTrackNrs = null;
      if (MediaDbUtil.haveAlbumPartlyOnDisc(album)) {
        discAndTrackNrs = MediaDbUtil.getAlbumTracksIHave(album, true);
      }

      if (discAndTrackNrs != null) {
        LOGGER.fine("I should have tracks on disc");
        // Separate tracks are either stored as track within a Collection, or they are part of my own compilation for that artist.
        MyInfo myInfo = album.getMyInfo();

        if (myInfo != null) {
          Album referredAlbum = null;
          List<Album> albumReferences = myInfo.getAlbumReferences();
          if (!albumReferences.isEmpty()) {
            // TODO handle multiple references
            referredAlbum = albumReferences.get(0);
          }
          if (referredAlbum != null) {
            for (DiscAndTrackNrs discAndTrackNr: discAndTrackNrs) {
              for (Integer trackNr: discAndTrackNr.getTrackNrs()) {
                Integer discNr = null;
                if (discAndTrackNr.isSetDiscNr()) {
                  discNr = discAndTrackNr.getDiscNr();
                }
                try {
                  Track track = album.getTrackReference(discNr, trackNr).getTrack();
                  TrackOnDiscInfo trackOnDiscInfo = findTrackInReferredAlbum(album, discNr, trackNr, referredAlbum, albumToMusicFolderLocationMap);

                  if (trackOnDiscInfo != null) {
                    Path pathToTrack = trackOnDiscInfo.getTrackPath();
                    LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
                    trackDiscLocationMap.put(track, pathToTrack);
                    tracksOnDiscInfoCopy.remove(trackOnDiscInfo);
                  } else {
                    LOGGER.fine("Path NOT found: ");
                    MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC);
                    error.setAlbum(album);
                    error.setTrack(track);
                    errors.add(error);
                  }
                } catch(Exception e) {
                  LOGGER.severe("Track not found for album. album=" + album.toString() + "discNr=" + discNr + "trackNr=" + trackNr);
                }
              }
            }
          } else {
            // For now find it in the Collections.
            // TODO handle tracks which are part of my own Compilation
            for (DiscAndTrackNrs discAndTrackNr: discAndTrackNrs) {
              for (Integer trackNr: discAndTrackNr.getTrackNrs()) {
                Integer discNr = null;
                if (discAndTrackNr.isSetDiscNr()) {
                  discNr = discAndTrackNr.getDiscNr();
                }
                try {
                  Track track = album.getTrackReference(discNr, trackNr).getTrack();
                  TrackOnDiscInfo trackOnDiscInfo = findTrackInTracksOnDiscInfo(album, discNr, trackNr, tracksOnDiscInfoCopy);

                  if (trackOnDiscInfo != null) {
                    Path pathToTrack = trackOnDiscInfo.getTrackPath();
                    LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
                    trackDiscLocationMap.put(track, pathToTrack);
                    tracksOnDiscInfoCopy.remove(trackOnDiscInfo);
                  } else {
                    LOGGER.fine("Path NOT found: ");
                    MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC);
                    error.setAlbum(album);
                    error.setTrack(track);
                    errors.add(error);
                  }
                } catch(Exception e) {
                  LOGGER.severe("Track not found for album. album=" + album.toString() + "discNr=" + discNr + "trackNr=" + trackNr);
                }
              }
            }
          }
        }
      }
    }
  }

  private void createTrackLocationMap(boolean ignoreMissingBonusTracks) {
    for (TrackCollection trackCollection: mediaDb.getTrackcollections()) {
      createTrackLocationMapForTrackCollection(trackCollection, ignoreMissingBonusTracks);
    }
  }
  
  private void createTrackLocationMapForTrackCollection(TrackCollection trackCollection, boolean ignoreMissingBonusTracks) {

    for (TrackReference trackReference: trackCollection.getTrackReferences()) {
      if (!MediaDbUtil.haveTrackOnDisc(trackReference)) {
        continue;
      }
      
      if (trackDiscLocationMap.containsKey(trackReference.getTrack())) {
        // Seems this track has already been handled.
        LOGGER.severe("Track has already been handled.");
        continue;
      }

      TrackOnDiscInfo trackOnDiscInfo = findTrackInTracksOnDiscInfo(trackReference, tracksOnDiscInfoCopy);

      if (trackOnDiscInfo != null) {
        Path pathToTrack = trackOnDiscInfo.getTrackPath();
        LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
        trackDiscLocationMap.put(trackReference.getTrack(), pathToTrack);
        tracksOnDiscInfoCopy.remove(trackOnDiscInfo);
      } else {
        LOGGER.severe("trackReference NOT found in DiscInfo: " + trackReference.toString());
        boolean onlyReferredToAsBonusTrack = true;
        for (TrackReference referredByReference: trackReference.getTrack().getReferredBy()) {
          if (!referredByReference.isSetBonusTrack()) {
            onlyReferredToAsBonusTrack = false;
            break;
          }
        }
        if (!ignoreMissingBonusTracks  ||  !onlyReferredToAsBonusTrack) {
          MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC);
          error.setTrack(trackReference.getTrack());
          errors.add(error);
        }
      }
    }
  }

  /**
   * Add the tracks of an album Disc to a trackDiscLocationMap.
   * 
   * @param disc The album Disc for which the tracks are to be added.
   * @param mediaDb the media database, describing the albums we (should) have.
   * @param albumOnDiscInfo information on the album on disc.
   * @param trackDiscLocationMap the map for tracks to their location on disc.
   * @param errors an error list to which any encountered errors are added. E.g. albums we should have in the MusicFolder, but weren't found there.
   */
  public static void addAlbumDiscTracksToTrackDiscLocationMap(Disc disc, MediaDb mediaDb, AlbumOnDiscInfo albumOnDiscInfo, Map<Track, Path> trackDiscLocationMap, List<Object> errors, boolean ignoreMissingBonusTracks) {
    Album album = disc.getAlbum();

    boolean myCompilation = false;
    if (album instanceof MyCompilation) {
      myCompilation = true;
    }
    
    int trackIndex = 0;
    String albumFolderName = albumOnDiscInfo.getAlbumFolderName();
    for (TrackReference trackReference: disc.getTrackReferences()) {
      LOGGER.info("trackReference: " + trackReference + ", trackIndex=" + trackIndex);
      Path trackPath = null;
      Track track = trackReference.getTrack();

      if (trackIndex < albumOnDiscInfo.getTrackFileNames().size()) {
        String trackFileName = albumOnDiscInfo.getTrackFileNames().get(trackIndex++);
        trackPath = findTrackPathMatch(trackReference, albumFolderName, trackFileName, mediaDb, myCompilation);
      }

      if (trackPath != null) {
        trackDiscLocationMap.put(track, trackPath);
      } else {
        if (!ignoreMissingBonusTracks  ||  !trackReference.isSetBonusTrack()) {
          LOGGER.severe("Track not found in MusicFolder: " + album.getArtistAndTitle() + " - " + track.getTitle());
          MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC);
          error.setAlbum(album);
          error.setTrackReference(trackReference);
          errors.add(error);
        }
      }
    }
  }

  /**
   * Find an Album in the albumsOnDiscInfo.
   * 
   * @param album the Album to search for in <code>albumsOnDiscInfo</code>
   * @param albumsOnDiscInfo Information about the albums on disc.
   * @param mediaDb the media database.
   * @param errors An error list to which errors that occur shall be added.
   * @return AlbumOnDiscInfo for the <code>album</code>, or null if it couldn't be found.
   */
  private static AlbumOnDiscInfo findAlbumInAlbumsOnDiscInfo(Album album, List<AlbumOnDiscInfo> albumsOnDiscInfo, MediaDb mediaDb, List<Object> errors) {
    LOGGER.info("=> album: " + album.getArtist() + " - " + album.getTitle());
        
    String albumFolderNameShallBe;
    if (album.isSoundtrack()) {
      albumFolderNameShallBe = MediaDbAppUtil.generateSoundtrackAlbumDiscFolderName(album.getDiscs().get(0), errors);
    } else {
      albumFolderNameShallBe = MediaDbAppUtil.generateAlbumDiscFolderName(album.getDiscs().get(0), mediaDb, errors);
    }
    
    LOGGER.info("Generated folder name: " + albumFolderNameShallBe);
    AlbumOnDiscInfo foundAlbumOnDiscInfo = null;

    for (AlbumOnDiscInfo albumOnDiscInfo: albumsOnDiscInfo) {
      String albumFolderNameIs = albumOnDiscInfo.getAlbumFolderName();
      LOGGER.info("Checking against:      " + albumFolderNameIs);
      if (albumFolderNameIs.equals(albumFolderNameShallBe)) {
        if (foundAlbumOnDiscInfo == null) {
          foundAlbumOnDiscInfo = albumOnDiscInfo;
        } else {
          // error: more than one match
          LOGGER.severe("Album disc in mediaDb found more than once on disc: " + albumOnDiscInfo.getIssueDate() + " - " + albumOnDiscInfo.getArtist() + " - " + albumOnDiscInfo.getTitle());
          MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.ALBUM_IN_MEDIADB_FOUND_MORE_THAN_ONCE_ON_DISC);
          error.setAlbum(album);
          errors.add(error);
        }
      }
    }

    
    LOGGER.info("<= " + (foundAlbumOnDiscInfo != null ? foundAlbumOnDiscInfo.toString() : "(null)"));
    return foundAlbumOnDiscInfo;
  }

  /**
   *   Find a specific track in a list of <b>TrackOnDiscInfo</b>.
   *   
   *   @param trackReference The Track to be found in the <code>tracksOnDiscInfo</code>.
   *   @param tracksOnDiscInfo the list of <b>TrackOnDiscInfo</b> in which the track is to be found.
   */
  private static TrackOnDiscInfo findTrackInTracksOnDiscInfo(TrackReference trackReference, List<TrackOnDiscInfo> tracksOnDiscInfo) {
    String trackFileNameByConvention = MediaDbAppUtil.generateTrackFileName(trackReference.getTrack());

    for (TrackOnDiscInfo trackOnDiscInfo: tracksOnDiscInfo) {
      // first only check on filename, if a match, check the folder.
      String trackFileNameOnDisc = trackOnDiscInfo.getTrackPath().getFileName().toString();
      String trackFileNameOnDiscWithoutExtension = FileUtils.getFileNameWithoutExtension(trackFileNameOnDisc);
      if (trackFileNameOnDiscWithoutExtension.equals(trackFileNameByConvention)) {
        String trackFolder = trackOnDiscInfo.getTrackPath().getParent().getFileName().toString();
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        if (myTrackInfo == null) {
          return null;
        }
        Collection collection = myTrackInfo.getCollection();
        String collectionFolder = MediaDbAppUtil.getCollectionFolderName(collection);
        if (collection.equals(Collection.NOT_SET)) {
          LOGGER.info("Collection not set for track: track=" + trackReference.toString());
        } else if (!trackFolder.equals(collectionFolder)) {
          LOGGER.severe("Track in wrong collection: track=" + trackReference.toString());
        }
        LOGGER.fine("Track folder=" + trackOnDiscInfo.getTrackPath().getParent().getFileName().toString());
        LOGGER.fine("Collection=" + trackReference.getMyTrackInfo().getCollection().getLiteral());

        return trackOnDiscInfo;
      }
    }

    return null;
  }

  /**
   *   Find a specific track (identified by an album, disc number and track number) in a list of <b>TrackOnDiscInfo</b>.
   *   
   *   @param album The Album which contains the track.
   *   @param discNr The disc nummer of the album.
   *   @param trackNr The index of the track on the disc.
   *   @param tracksOnDiscInfo the list of <b>TrackOnDiscInfo</b> in which the track is to be found.
   *   @return the TrackOnDiscInfo for the specified track, or null if it could not be found.
   */
  private static TrackOnDiscInfo findTrackInTracksOnDiscInfo(Album album, Integer discNr, Integer trackNr, List<TrackOnDiscInfo> tracksOnDiscInfo) {
    String artistName = null;
    if (album.isSetArtist()) {
      artistName = album.getArtist().getName();
    }
    
    TrackReference trackReference = album.getTrackReference(discNr, trackNr);
    Track track = trackReference.getTrack();
    if ("Various Artists".equals(artistName)) {
      Artist trackArtist = track.getArtist();
      if (trackArtist != null) {
        artistName = trackArtist.getName();
      } else {
        LOGGER.severe("Track artist not set for various artist track: " + track.getTitle());
      }
    }
    
    if ("Horror Movie".equals(track.getTitle())) {
      LOGGER.severe("Handling: " + track.getTitle());
    }
    String trackFileNameByConvention = TrackFile.generateTrackFileName(artistName, track.getTitle());
    String trackFileNameByConvention2 =  TrackFile.generateTrackFileNameIncludingAlbumInfoForTracksFolder(artistName, album.getReleaseDate(), album.getTitle(), trackNr, track.getTitle());
    for (TrackOnDiscInfo trackOnDiscInfo: tracksOnDiscInfo) {
      // first only check on filename, if a match, check the folder.
      String trackFileNameOnDisc = trackOnDiscInfo.getTrackPath().getFileName().toString();
      String trackFileNameOnDiscWithoutExtension = FileUtils.getFileNameWithoutExtension(trackFileNameOnDisc);
      if ("New York Dolls".equals(album.getTitle())) {
        LOGGER.info("Checking against: trackFileNameOnDiscWithoutExtension=" + trackFileNameOnDiscWithoutExtension);
      }
      if (trackFileNameOnDiscWithoutExtension.equals(trackFileNameByConvention)  ||
          trackFileNameOnDiscWithoutExtension.equals(trackFileNameByConvention2)) {
        String trackFolder = trackOnDiscInfo.getTrackPath().getParent().getFileName().toString();
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        Collection collection = myTrackInfo.getCollection();
        String collectionFolder = MediaDbAppUtil.getCollectionFolderName(collection);
        if (collection.equals(Collection.NOT_SET)) {
          LOGGER.severe("Collection not set for track: album=" + album.getArtistAndTitle() + ", discNr=" + discNr + ", trackNr=" + trackNr);
        } else if (!trackFolder.equals(collectionFolder)) {
          LOGGER.severe("Track in wrong collection: album=" + album.getTitle() + ", discNr=" + discNr + ", trackNr=" + trackNr + ", collection=" + collection.getLiteral());
        }
        LOGGER.fine("Track folder=" + trackOnDiscInfo.getTrackPath().getParent().getFileName().toString());
        LOGGER.fine("Collection=" + collection.getLiteral());

        return trackOnDiscInfo;
      }
    }
    
    return null;
  }


  /**
   *   Find a specific track (identified by an album, disc number and track number) in a referred album.
   *   
   *   @param album The Album which contains the track.
   *   @param discNr The disc nummer of the album.
   *   @param trackNr The index of the track on the disc.
   *   @param referredAlbum the album in which to find the track.
   */
  public static TrackOnDiscInfo findTrackInReferredAlbum(Album album, Integer discNr, Integer trackNr, Album referredAlbum, Map<Album, AlbumDiscLocationInfo> albumToMusicFolderLocationMap) {

    int foundTrackIndex = -1;
    int trackIndex = 0;
    String albumTitle = album.getTitle();
    if (discNr == null) {
      discNr = 1;
    }
    
    for (TrackReference trackReference: referredAlbum.getDiscs().get(0).getTrackReferences()) {
      TrackReference originalAlbumTrackReference = trackReference.getOriginalAlbumTrackReference();
      if (originalAlbumTrackReference != null) {
        int referredTrackNr = originalAlbumTrackReference.getTrackNr();
        LOGGER.info("referredTrackNr: " + referredTrackNr);
        Disc disc = originalAlbumTrackReference.getDisc();
        if (disc != null) {
          Album referredBackAlbum = disc.getAlbum();
          String referredBackAlbumTitle = referredBackAlbum.getTitle();
          LOGGER.info("referredBackAlbumTitle: " + referredBackAlbumTitle);
          int referredBackDiscNr = referredBackAlbum.getDiscs().indexOf(disc) + 1;
          LOGGER.info("referredBackDiscNr: " + referredBackDiscNr);

          if (albumTitle.equals(referredBackAlbumTitle)  &&
              (discNr == referredBackDiscNr)  &&
              (trackNr == referredTrackNr)) {
            foundTrackIndex = trackIndex;
            break;
          }
        }
      }

      trackIndex++;
    }
    
    
    if (foundTrackIndex != -1) {
      AlbumDiscLocationInfo albumDiscLocationInfo = albumToMusicFolderLocationMap.get(referredAlbum);
      if (albumDiscLocationInfo != null) {
        String trackFileName = albumDiscLocationInfo.getTrackFileNames().get(foundTrackIndex);

        Path trackPath = Paths.get(albumDiscLocationInfo.getAbsAlbumFolderPathname(), trackFileName);
        TrackOnDiscInfo trackOnDiscInfo = new TrackOnDiscInfo(trackPath);
        return trackOnDiscInfo;
      }
    }

    return null;
  }

  /**
   * Find an album Disc in the albumsOnDiscInfo.
   * <p>
   * The value returned is the first occurrence found, for any other occurrence found an <code>ALBUM_IN_MEDIADB_FOUND_MORE_THAN_ONCE_ON_DISC</code> error is reported.
   * 
   * @param disc the Disc to search for in the <code>albumsOnDiscInfo</code>
   * @param albumsOnDiscInfo Information about the albums on disc.
   * @param mediaDb the media database.
   * @param errors An error list to which errors that occur shall be added.
   * @return AlbumOnDiscInfo for the <code>disc</code>, or null if it couldn't be found.
   */
  public static AlbumOnDiscInfo findAlbumDiscInAlbumsOnDiscInfo(Disc disc, List<AlbumOnDiscInfo> albumsOnDiscInfo, MediaDb mediaDb, List<Object> errors) {
    Album album = disc.getAlbum();
    LOGGER.info("=> album: " + album.getArtist() + " - " + album.getTitle());
    
    String albumFolderNameShallBe;
    if (disc.getAlbum().isSoundtrack()) {
      albumFolderNameShallBe = MediaDbAppUtil.generateSoundtrackAlbumDiscFolderName(disc, errors);
    } else {
      albumFolderNameShallBe = MediaDbAppUtil.generateAlbumDiscFolderName(disc, mediaDb, errors);
    }
    
    LOGGER.info("albumFolderNameShallBe: " + albumFolderNameShallBe);
    AlbumOnDiscInfo foundAlbumOnDiscInfo = null;

    for (AlbumOnDiscInfo albumOnDiscInfo: albumsOnDiscInfo) {
      String albumFolderNameIs = albumOnDiscInfo.getAlbumFolderName();
      LOGGER.info("Checking against:      " + albumFolderNameIs);
      if (albumFolderNameIs.equals(albumFolderNameShallBe)) {
        if (foundAlbumOnDiscInfo == null) {
          foundAlbumOnDiscInfo = albumOnDiscInfo;
        } else {
          // error: more than one match
          LOGGER.severe("Album disc in mediaDb found more than once on disc: " + albumOnDiscInfo.getIssueDate() + " - " + albumOnDiscInfo.getArtist() + " - " + albumOnDiscInfo.getTitle());
          MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.ALBUM_IN_MEDIADB_FOUND_MORE_THAN_ONCE_ON_DISC);
          error.setAlbum(album);
          errors.add(error);
        }
      }
    }

    
    LOGGER.info("<= " + (foundAlbumOnDiscInfo != null ? foundAlbumOnDiscInfo.toString() : "(null)"));
    return foundAlbumOnDiscInfo;
  }

  /**
   * Check whether there is a match between a TrackReference (the track information in the database) and a track file name.
   * If there is a match, the Path for the track file name is returned.
   * <p>
   * The <code>trackFileName</code> is compared to expected (allowed) names for the <code>trackReference</code>.
   * 
   * @param trackReference a reference for a track in the database
   * @param albumFolderName name of the folder in which the file with the name <trackFileName> resides. This is used to create a Path to the file.
   * @param trackFileName name of a file, which should match with the <code>trackReference</code>.
   * @param mediaDb the media database (for a MyCompilation album the file name depends on tracks from other album of the same year).
   * @param myCompilation if true, the <code>trackReference</code> is from a MyCompilation album.
   * @return A Path to <code>trackFileName</code> if trackFileName matches the <code>trackReference</code>, null otherwise.
   */
  public static Path findTrackPathMatch(TrackReference trackReference, String albumFolderName, String trackFileName, MediaDb mediaDb, boolean myCompilation) {
    boolean found = false;

    String trackFileNameWithoutExtension = FileUtils.getFileNameWithoutExtension(trackFileName);
    String expectedTrackFileName;

    if (myCompilation) {
      TrackReference originalAlbumTrackReference = trackReference.getOriginalAlbumTrackReference();
      if (originalAlbumTrackReference != null) {
        // If MyTrackReferenceInfo is available, the name shall be the name which is derived from this information.
        expectedTrackFileName = MediaDbAppUtil.generateTrackFileNameForMyCompilationAlbum(originalAlbumTrackReference, mediaDb);
        if (trackFileNameWithoutExtension.equals(expectedTrackFileName)) {
          found = true;
        } else {
          LOGGER.info("No track filename match, based on MyTrackReferenceInfo. Expected=" + expectedTrackFileName + ", Actual=" + trackFileName);
        }
      } else {
        // No MyTrackReferenceInfo available.
        // First try <trackNr> - <trackTitle>
        expectedTrackFileName = MediaDbAppUtil.generateTrackFileNameForStandardAlbum(trackReference);
        if (trackFileNameWithoutExtension.equals(expectedTrackFileName)) {
          found = true;
        } else {
          LOGGER.info("No track filename match, based on <trackNr> - <trackTitle>. albumFolderName=" + albumFolderName + ", Expected=" + expectedTrackFileName + ", Actual=" + trackFileName);
        }

        // If no match, just try the track title.
        if (!found) {
          expectedTrackFileName = trackReference.getTrack().getTitle();

          if (trackFileNameWithoutExtension.equals(expectedTrackFileName)) {
            found = true;
          } else {
            LOGGER.info("No track filename match, based on <trackTitle>. albumFolderName=" + albumFolderName + ", Expected=" + expectedTrackFileName + ", Actual=" + trackFileName);
          }
        }
      }
    } else {
      expectedTrackFileName = MediaDbAppUtil.generateTrackFileNameForStandardAlbum(trackReference);
      if (trackFileNameWithoutExtension.equals(expectedTrackFileName)) {
        found = true;
      } else {
        LOGGER.info("No track filename match for track in Standard Album. albumFolderName=" + albumFolderName + ", Expected=" + expectedTrackFileName + ", Actual=" + trackFileName);
      }
    }


    if (found) {
      return Paths.get(albumFolderName, trackFileName);
    } else {
      return null;
    }
  }

}
