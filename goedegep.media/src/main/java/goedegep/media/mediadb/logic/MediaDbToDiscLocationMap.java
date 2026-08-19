package goedegep.media.mediadb.logic;

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
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackCollection;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.media.musicfolder.logic.AlbumOnHardDiskInfo;
import goedegep.media.musicfolder.logic.MusicFolderContent;
import goedegep.media.musicfolder.logic.TrackFile;
import goedegep.media.musicfolder.logic.TrackOnHardDiskInfo;
import goedegep.util.file.FileUtils;


/**
 * This class creates a mapping for albums and tracks in the media database, that should be available on hard disk, to their locations on hard disk.
 * <p>
 * There are no albums partly on hard disk, only complete albums and tracks in collections.
 * Albums partly available always refer to an available album or a track in a collection.
 * 
 */
public class MediaDbToDiscLocationMap {
  private static final Logger LOGGER = Logger.getLogger(MediaDbToDiscLocationMap.class.getName());
  
  /**
   * Media database which contains the information about which albums and tracks should be available on hard disk.
   */
  private MediaDb mediaDb;
  
  /**
   * List of {@code AlbumOnHardDiskInfo} not yet used.
   * <p>
   * This list is initially filled with all albums in the MusicFolder ({@code musicFolderContent.getAlbumsOnHardDiskInfo()}.
   * When an album is used in a mapping it is removed from this list.
   * The remaining items in this list (after performing the mapping) are albums in the MusicFolder which are not referred to in the media database.
   */
  private List<AlbumOnHardDiskInfo> albumsOnHardDiskInfoNotMapped;
  
  /**
   * List of {@code TrackOnHardDiskInfo} not yet used.
   * <p>
   * This list is initially filled with the tracks from the track folders in the MusicFolder ({@code musicFolderContent.getTracksOnHardDiskInfo()}.
   * When a track is used in a mapping it is removed from this list.
   * The remaining items in this list (after performing the mapping) are tracks in the MusicFolder which are not referred to in the media database.
   */
  private List<TrackOnHardDiskInfo> tracksOnHardDiskInfoNotMapped;

  /**
   * A map which provides information on where a single-disc album in the media database can be found on disc (in the MusicFolder).
   * <p>
   * If an album is completely available on disc, it will be in this map.<br/>
   * If any number of tracks are available on disc (partly available), in the folder for that album (so not via track references to another album), it will be in this map.
   */
  private Map<Album, AlbumOnHardDiskInfo> albumToMusicFolderLocationMap = new HashMap<>();
  
  /**
   * A map which provides information on where a disc of a multi-disc album in the media database can be found on disc (in the MusicFolder).
   */
  private Map<Disc, AlbumOnHardDiskInfo> albumDiscToMusicFolderLocationMap = new HashMap<>();
  
  /**
   * A map which provides information on where a track in the media database can be found on disc (in the MusicFolder).
   */
  private Map<Track, Path> trackHardDiskLocationMap = new HashMap<>();
  
  /**
   * Errors which are detected while creating the mapping.
   */
  private List<Object> errors = new ArrayList<>();

  /**
   * Constructor
   * 
   * @param mediaDb the Media Database for which the albums and tracks are to be mapped to their locations on disc.
   * @param musicFolderContent Information on the albums and tracks in the Music Folder.
   */
  public MediaDbToDiscLocationMap(MediaDb mediaDb, MusicFolderContent musicFolderContent) {
    this.mediaDb = mediaDb;
    
    // Start with copies of the albumsOnHardDiskInfo and tracksOnHardDiskInfo lists, so items handled can be removed.
    albumsOnHardDiskInfoNotMapped = new ArrayList<>(musicFolderContent.getAlbumsOnHardDiskInfo());
    tracksOnHardDiskInfoNotMapped = new ArrayList<>(musicFolderContent.getTracksOnHardDiskInfo());
  }

  /**
   * Create the mapping for albums and tracks, that should be available on hard disk, to their locations on hard disk.
   * 
   * @param ignoreMissingBonusTracks if true, bonus tracks of an album that can't be found are not treated as errors.
   *        Otherwise an error is created for a missing bonus track (as for any missing track).
   */
  public boolean createMediaDbToHardDiskLocationMap(boolean ignoreMissingBonusTracks) {
    
    for (Album album: mediaDb.getAlbums()) {
      createAndAddAlbumMapping(album, ignoreMissingBonusTracks);
    }

    // Handle the separate tracks in the track collections.
    createTrackLocationMap(ignoreMissingBonusTracks);

    for (AlbumOnHardDiskInfo albumOnHardDiskInfo: albumsOnHardDiskInfoNotMapped) {
      LOGGER.severe("Album on hard disk NOT found in MediaDb" + albumOnHardDiskInfo.toString());
      MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.ALBUM_IN_MEDIADB_NOT_FOUND_ON_DISC);
      error.setAlbumOnDiscInfo(albumOnHardDiskInfo);
      errors.add(error);
    }
    
    for (TrackOnHardDiskInfo trackOnDiscInfo: tracksOnHardDiskInfoNotMapped) {
      LOGGER.severe("Track on disc NOT found in MediaDb: " + trackOnDiscInfo.getTrackPath().toString());
      MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_ON_DISC_NOT_FOUND_IN_MEDIADB);
      error.setTrackPath(trackOnDiscInfo.getTrackPath());
      errors.add(error);
    }
    
    return errors.isEmpty();
  }

  /**
   * Get the map which provides information on where a single-disc album in the media database can be found on disc (in the MusicFolder).
   * 
   * @return  The map which provides information on where a single-disc album in the media database can be found on disc (in the MusicFolder).
   */
  public Map<Album, AlbumOnHardDiskInfo> getAlbumToMusicFolderLocationMap() {
    return albumToMusicFolderLocationMap;
  }

  /**
   * Get the map which provides information on where a disc of a multi-disc album in the media database can be found on disc (in the MusicFolder).
   * 
   * @return The map which provides information on where a disc of a multi-disc album in the media database can be found on disc (in the MusicFolder).
   */
  public Map<Disc, AlbumOnHardDiskInfo> getAlbumDiscToMusicFolderLocationMap() {
    return albumDiscToMusicFolderLocationMap;
  }

  /**
   * Get the map which provides information on where a track in the media database can be found on disc (in the MusicFolder).
   * 
   * @return the map which provides information on where a track in the media database can be found on disc (in the MusicFolder).
   */
  public Map<Track, Path> getTrackDiscLocationMap() {
    return trackHardDiskLocationMap;
  }

  /**
   * Get the errors which are detected while creating the mapping.
   * 
   * @return the errors which are detected while creating the mapping.
   */
  public List<Object> getErrors() {
    return errors;
  }
  
  /**
   * Create a mapping from an {@code Album} in the media database to its location on hard disk.
   * <p>
   * Based on information from the media database (describing which albums/tracks we should have) and from what we have in the 
   * MusicFolder, maps are filled which map:
   * <ul>
   * <li>albums to their location in the MusicFolder (for single disc albums)</li>
   * <li>album discs to their location in the MusicFolder (for multi disc albums)</li>
   * </ul>
   * 
   * @param album the album for which the mapping is to be created.
   * @param ignoreMissingBonusTracks if true, bonus tracks of an album that can't be found are not treated as errors.
   *        Otherwise an error is created for a missing bonus track (as for any missing track).
   */
  private void createAndAddAlbumMapping(Album album, boolean ignoreMissingBonusTracks) {
    /*
     *  In the folder name in the MusicFolder, not all characters are allowed.
     *  So a mapping is used from the artist name and title name to the folder name.
     *  This mapping may lead to characters being removed. Therefore a reconstruction of artist name and title from the folder name
     *  is difficult.
     *  Therefore for each album/disc in the mediaDb, the MusicFolder location is determined. With this info is it looked up in the albumsOnDiscInfo.
     *  If found, the match is added to the albumLocationMap or albumDiscLocationMap, and the album is removed from the (copy of) albumsOnDiscInfo.
     *  If an album should be in the MusicFolder and it is not found, it is an error. So any remaining albums in albumsOnDiscInfoCopy are reported as errors.
     */
    
     if (!MediaDbUtil.isMultiDiscAlbum(album)) {
      // Single-disc album
      // Music folder is per album.
       
      if ("Het Gebeurde In Het Westen".equals(album.getTitle())) {
        LOGGER.severe("STOP");
      }
      
      // If I have album (partly) on disc, find the AlbumOnDiscInfo in the albumsOnDiscNotMapped
      if (MediaDbUtil.haveAlbumOnHardDisk(album)) {   //   ||  MediaDbUtil.haveAlbumPartlyOnHardDisk(album)
        AlbumOnHardDiskInfo albumOnHardDiskInfo = null;
        if (MediaDbUtil.haveAlbumOnHardDisk(album)) {
          albumOnHardDiskInfo = findAlbumInAlbumsOnDiscInfo(album);
        } else {
//          albumOnHardDiskInfo = createAndAddMappingForAlbumPartlyOnHardDisk(album);
        }
        
        if (albumOnHardDiskInfo != null) {
          // Put the album and AlbumOnDiscInfo in the albumMusicFolderLocationMap
          albumToMusicFolderLocationMap.put(album, albumOnHardDiskInfo);
          
          // Remove the album from the AlbumOnDiscInfo
          albumsOnHardDiskInfoNotMapped.remove(albumOnHardDiskInfo);

          // add the tracks of the album to the trackDiscLocationMap
          addAlbumDiscTracksToTrackDiscLocationMap(album.getDiscs().get(0), albumOnHardDiskInfo, ignoreMissingBonusTracks);
        } else {
          LOGGER.severe("Album in media database not found on hard disk: " + MediaDbUtil.getArtistAndTitle(album));
          MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.ALBUM_IN_MEDIADB_NOT_FOUND_ON_DISC);
          error.setAlbum(album);
          errors.add(error);
        }
      }
      
      if (MediaDbUtil.haveAlbumOnHardDisk(album)) {
      } else if (MediaDbUtil.haveAlbumPartlyOnHardDisk(album)) {
//        createAndAddMappingForAlbumPartlyOnDisc(album);
      }
    } else {
      // Music folders are per disc, i.e. a disc of an album is treated the same as a single disc album. Only the folder name has a disc specific extension. 
      if  (MediaDbUtil.haveAlbumOnHardDisk(album)) {
        for (Disc disc: album.getDiscs()) {
          createAndAddMappingForDiscCompletelyOnDisc(disc, ignoreMissingBonusTracks);
        }
      } else {
        for (Disc disc: album.getDiscs()) {
          if (MediaDbUtil.haveDiscOnHardDisk(disc)) {
            createAndAddMappingForDiscCompletelyOnDisc(disc, ignoreMissingBonusTracks);
          } else if (MediaDbUtil.doIHaveOneOrMoreDiscTracksOnHardDisk(disc)) {
            createAndAddMappingForDiscPartlyOnDisc(disc);
          }
        }
      }
      
    }
  }

  /**
   * Create and add the mapping for an album which is partly available on hard disk.
   * 
   * @param album the album for which the mapping is to be created.
   */
  private AlbumOnHardDiskInfo createAndAddMappingForAlbumPartlyOnHardDisk(Album album) {
    String albumFolderName = null;
    List<String> trackFileNames = new ArrayList<>();

    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        TrackReference finalTrackReference = MediaDbUtil.getFinalTrackReference(trackReference);
        MyTrackInfo myTrackInfo = finalTrackReference.getMyTrackInfo();
        if (myTrackInfo != null) {
          for (MediumInfo mediumInfo: myTrackInfo.getIHaveOn()) {
            if (mediumInfo.getMediumType() == MediumType.HARDDISK) {
              createAndAddMappingForTrackOfAlbum(trackReference);
            }
          }
        } else {
//          throw new RuntimeException("album partly on disc has no myTrackInfo: " + album);
        }
      }
    }

    List<DiscAndTrackNrs> discAndTrackNrsList = MediaDbUtil.getAlbumTracksIHave(album, true);

    if (discAndTrackNrsList != null) {
      // Separate tracks are either stored as track within a Collection, they are on some other album, or they are part of my own compilation for that artist.
      MyInfo myInfo = album.getMyInfo();

      if (myInfo != null) {
        Album referredAlbum = null;
        List<Album> albumReferences = myInfo.getAlbumReferences();
        if (!albumReferences.isEmpty()) {
          // TODO handle multiple references
          referredAlbum = albumReferences.get(0);
        }
        if (referredAlbum != null) {
          for (DiscAndTrackNrs discAndTrackNr: discAndTrackNrsList) {
            for (Integer trackNr: discAndTrackNr.getTrackNrs()) {
              Integer discNr = null;
              if (discAndTrackNr.isSetDiscNr()) {
                discNr = discAndTrackNr.getDiscNr();
              }
              try {
                Track track = MediaDbUtil.getTrackReference(album, discNr, trackNr).getTrack();
                TrackOnHardDiskInfo trackOnDiscInfo = findTrackInReferredAlbum(album, discNr, trackNr, referredAlbum, albumToMusicFolderLocationMap);

                if (trackOnDiscInfo != null) {
                  Path pathToTrack = trackOnDiscInfo.getTrackPath();
                  LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
                  trackHardDiskLocationMap.put(track, pathToTrack);
                  tracksOnHardDiskInfoNotMapped.remove(trackOnDiscInfo);
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
          for (DiscAndTrackNrs discAndTrackNrs: discAndTrackNrsList) {
            for (Integer trackNr: discAndTrackNrs.getTrackNrs()) {
              Integer discNr = null;
              if (discAndTrackNrs.isSetDiscNr()) {
                discNr = discAndTrackNrs.getDiscNr();
              }
              try {
                Track track = MediaDbUtil.getTrackReference(album, discNr, trackNr).getTrack();
                TrackReference trackReference = MediaDbUtil.getTrackReference(album, discNr, trackNr);
                TrackReference originalAlbumTrackReference = MediaDbUtil.getOriginalDiscTrackReference(trackReference.getTrack());
                if (originalAlbumTrackReference != null) {
                  MyTrackInfo originalAlbumTrackReferenceMyTrackInfo = originalAlbumTrackReference.getMyTrackInfo();
                  if (originalAlbumTrackReferenceMyTrackInfo != null) {
                    List<MediumInfo> mediumInfos = originalAlbumTrackReferenceMyTrackInfo.getIHaveOn();
                    for (MediumInfo mediumInfo: mediumInfos) {
                      LOGGER.severe("MediumInfo: " + mediumInfo);
                    }
                  }
                }
                TrackOnHardDiskInfo trackOnDiscInfo = findTrackInTracksOnDiscInfo(album, discNr, trackNr, tracksOnHardDiskInfoNotMapped);

                if (trackOnDiscInfo != null) {
                  Path pathToTrack = trackOnDiscInfo.getTrackPath();
                  LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
                  trackHardDiskLocationMap.put(track, pathToTrack);
                  tracksOnHardDiskInfoNotMapped.remove(trackOnDiscInfo);
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
    } else {
      throw new RuntimeException("discAndTrackNrsList is null, while there should be tracks on harddisc");
    }
    AlbumOnHardDiskInfo albumOnDiscInfo = new AlbumOnHardDiskInfo(null, null, null, albumFolderName, trackFileNames);
    
    return albumOnDiscInfo;
  }
  
  private void createAndAddMappingForTrackOfAlbum(TrackReference trackReference) {
    TrackOnHardDiskInfo trackOnHardDiskInfo = findTrackInTracksOnDiscInfo(trackReference, tracksOnHardDiskInfoNotMapped);

    if (trackOnHardDiskInfo != null) {
      Path pathToTrack = trackOnHardDiskInfo.getTrackPath();
      LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
      trackHardDiskLocationMap.put(trackReference.getTrack(), pathToTrack);
      tracksOnHardDiskInfoNotMapped.remove(trackOnHardDiskInfo);
    } else {
      LOGGER.fine("Path NOT found: ");
      MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC);
      error.setAlbum(MediaDbUtil.getAlbum(MediaDbUtil.getDisc(trackReference)));
      error.setTrack(trackReference.getTrack());
      errors.add(error);
    }
  }

  
  private TrackOnHardDiskInfo createAndAddMappingForTrackInCollection(TrackReference trackReference) {
    TrackOnHardDiskInfo trackOnDiscInfo = findCollectionTrackInTracksOnDiscInfo(trackReference, tracksOnHardDiskInfoNotMapped);

    if (trackOnDiscInfo != null) {
      Path pathToTrack = trackOnDiscInfo.getTrackPath();
      LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
      trackHardDiskLocationMap.put(trackReference.getTrack(), pathToTrack);
      tracksOnHardDiskInfoNotMapped.remove(trackOnDiscInfo);
    } else {
      LOGGER.fine("Path NOT found: ");
      MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC);
      error.setAlbum(MediaDbUtil.getAlbum(MediaDbUtil.getDisc(trackReference)));
      error.setTrack(trackReference.getTrack());
      errors.add(error);
    }
    
    return trackOnDiscInfo;
  }

  /**
   *   Find a specific track in a list of <b>TrackOnDiscInfo</b>.
   *   
   *   @param trackReference The Track to be found in the <code>tracksOnDiscInfo</code>.
   *   @param tracksOnDiscInfo the list of <b>TrackOnDiscInfo</b> in which the track is to be found.
   */
  private TrackOnHardDiskInfo findCollectionTrackInTracksOnDiscInfo(TrackReference trackReference, List<TrackOnHardDiskInfo> tracksOnDiscInfo) {
    String trackFileNameByConvention = MediaDbAppUtil.generateTrackFileNameForATrackInATracksFolder(trackReference.getTrack(), mediaDb);
    if (trackFileNameByConvention == null) {
      trackFileNameByConvention = MediaDbAppUtil.generateTrackFileNameForATrackInATracksFolder(trackReference.getTrack(), mediaDb);
      throw new RuntimeException("Error: track is not valid");
    }

    for (TrackOnHardDiskInfo trackOnDiscInfo: tracksOnDiscInfo) {
      // first only check on filename, if a match, check the folder.
      String trackFileNameOnDisc = trackOnDiscInfo.getTrackPath().getFileName().toString();
      String trackFileNameOnDiscWithoutExtension = FileUtils.getFileNameWithoutExtension(trackFileNameOnDisc);
      if (trackFileNameOnDiscWithoutExtension.equals(trackFileNameByConvention)) {
//        if (myTrackInfo == null) { myTrackInfo cannot be null for a collection track of an album
//          return null;
//        }
 //        if (collection.equals(Collection.NOT_SET)) {  // cannot occur for a collection track of an album
//          LOGGER.info("Collection not set for track: track=" + trackReference.toString());
//        } else if (!trackFolder.equals(collectionFolder)) {
//          LOGGER.severe("Track in wrong collection: track=" + trackReference.toString());
//        }
      }
    }

    return null;
  }

  /**
   * Create and add the mapping for a disc which is completely available on disc.
   * 
   * @param disc the disc for which the mapping is to be created.
   * @param ignoreMissingBonusTracks if true, bonus tracks of an album (disc) that can't be found are not treated as errors.
   *        Otherwise an error is created for a missing bonus track (as for any missing track).
   */
  private void createAndAddMappingForDiscCompletelyOnDisc(Disc disc, boolean ignoreMissingBonusTracks) {
    LOGGER.info("=> " + disc.getTitle());
    AlbumOnHardDiskInfo albumOnDiscInfo = findAlbumDiscInAlbumsOnDiscInfo(disc, albumsOnHardDiskInfoNotMapped, errors);
    if (albumOnDiscInfo != null) {
      LOGGER.info("Found disc of album on disc");
//      AlbumDiscLocationInfo albumDiscLocationInfo = new AlbumDiscLocationInfo(albumOnDiscInfo.getAlbumFolderName(), albumOnDiscInfo.getTrackFileNames());
      albumsOnHardDiskInfoNotMapped.remove(albumOnDiscInfo);
      albumDiscToMusicFolderLocationMap.put(disc, albumOnDiscInfo);

      // add the tracks of the album to the trackDiscLocationMap
      addAlbumDiscTracksToTrackDiscLocationMap(disc, albumOnDiscInfo, ignoreMissingBonusTracks);
    } else {
      LOGGER.info("Album disc in media database not found on disc: " + MediaDbUtil.getArtistAndTitle(MediaDbUtil.getAlbum(disc)) + ", disc: " + (MediaDbUtil.getAlbum(disc).getDiscs().indexOf(disc) + 1));
      MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.ALBUM_IN_MEDIADB_NOT_FOUND_ON_DISC);
      error.setDisc(disc);
      error.setAlbum(MediaDbUtil.getAlbum(disc));
      errors.add(error);
    }
  }

  private void createAndAddMappingForDiscPartlyOnDisc(Disc disc) {
    AlbumOnHardDiskInfo albumOnDiscInfo = findAlbumDiscInAlbumsOnDiscInfo(disc, albumsOnHardDiskInfoNotMapped, errors);
    if (albumOnDiscInfo != null) {
      addAlbumDiscTracksToTrackDiscLocationMap(disc, albumOnDiscInfo, false);
    } else {
      LOGGER.info("Still to handle references");
    }
  }

  /**
   * Create a mapping for all tracks in all track collections to their location on disc.
   * 
   * @param ignoreMissingBonusTracks TODO is this needed?
   */
  private void createTrackLocationMap(boolean ignoreMissingBonusTracks) {
    for (TrackCollection trackCollection: mediaDb.getTrackcollections()) {
      createTrackLocationMapForTrackCollection(trackCollection, ignoreMissingBonusTracks);
    }
  }
  
  /**
   * Create a mapping for all tracks in a track collection to their location on disc.
   * <p>
   * Tracks that aren't available on disc are ignored. 
   * 
   * @param trackCollection the {@code TrackCollection} for which a mapping is to be created.
   * @param ignoreMissingBonusTracks TODO is this needed?
   */
  private void createTrackLocationMapForTrackCollection(TrackCollection trackCollection, boolean ignoreMissingBonusTracks) {

    for (TrackReference trackReference: trackCollection.getTrackReferences()) {
      if (!MediaDbUtil.doIHaveTrackOnHardDisk(trackReference)) {
        continue;
      }
      
      if (trackHardDiskLocationMap.containsKey(trackReference.getTrack())) {
        // Seems this track has already been handled.
        LOGGER.severe("Track has already been handled.");
        continue;
      }

      TrackOnHardDiskInfo trackOnDiscInfo = findTrackInTracksOnDiscInfo(trackReference, tracksOnHardDiskInfoNotMapped);

      if (trackOnDiscInfo != null) {
        Path pathToTrack = trackOnDiscInfo.getTrackPath();
        LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
        trackHardDiskLocationMap.put(trackReference.getTrack(), pathToTrack);
        tracksOnHardDiskInfoNotMapped.remove(trackOnDiscInfo);
      } else {
        LOGGER.severe("trackReference NOT found in DiscInfo:\n" + trackReference.toString());
        LOGGER.severe("Handling track collection: " + trackCollection.getCollection().getName());
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
          error.setCollection(trackCollection.getCollection());
          errors.add(error);
        }
      }
    }
  }

  /**
   * Add the tracks of an album Disc to a trackDiscLocationMap.
   * 
   * @param disc The album Disc for which the tracks are to be added.
   * @param albumOnDiscInfo information on the album on disc.
   * @param ignoreMissingBonusTracks If set, a bonus track not found is not reported as an error.
   */
  public void addAlbumDiscTracksToTrackDiscLocationMap(Disc disc, AlbumOnHardDiskInfo albumOnDiscInfo, boolean ignoreMissingBonusTracks) {
    Album album = MediaDbUtil.getAlbum(disc);
    boolean haveAllTracksOnDisc = MediaDbUtil.haveAlbumOnHardDisk(album);

    boolean myCompilation = false;
    if (MediaDbUtil.isOwnCompilationAlbum(album)) {
      myCompilation = true;
    }

    int trackIndex = 0;
    for (TrackReference trackReference: disc.getTrackReferences()) {
      LOGGER.info("trackReference: " + trackReference + ", trackIndex=" + trackIndex);

      if (haveAllTracksOnDisc  ||  MediaDbUtil.doIHaveTrackOnHardDisk(trackReference)) {
        boolean trackFound = false;
        String trackFileName = null;
        Track track = trackReference.getTrack();

        if (trackIndex < albumOnDiscInfo.trackFileNames().size()) {
          trackFileName = albumOnDiscInfo.trackFileNames().get(trackIndex);
          trackFound = findTrackPathMatch(trackReference, trackFileName, myCompilation);
        }

        if (trackFound) {
          trackHardDiskLocationMap.put(track, Paths.get(albumOnDiscInfo.albumFolderName(), trackFileName));
        } else {
          if (!ignoreMissingBonusTracks  ||  !trackReference.isSetBonusTrack()) {
            LOGGER.severe("Track not found in MusicFolder: " + MediaDbUtil.getArtistAndTitle(album) + " - " + track.getTitle());
            MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC);
            error.setAlbum(album);
            error.setTrackReference(trackReference);
            errors.add(error);
          }
        }
      }

      trackIndex++;
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
  private AlbumOnHardDiskInfo findAlbumInAlbumsOnDiscInfo(Album album) {
    LOGGER.info("=> album: " + album.getArtist() + " - " + album.getTitle());
        
    String albumFolderNameShallBe;
    if (album.isSoundtrack()) {
      albumFolderNameShallBe = MediaDbAppUtil.generateSoundtrackAlbumDiscFolderName(album.getDiscs().get(0), errors);
    } else {
      albumFolderNameShallBe = MediaDbAppUtil.generateAlbumDiscFolderName(album.getDiscs().get(0), mediaDb, errors);
    }
    
    LOGGER.info("Generated folder name: " + albumFolderNameShallBe);
    AlbumOnHardDiskInfo foundAlbumOnDiscInfo = null;

    for (AlbumOnHardDiskInfo albumOnDiscInfo: albumsOnHardDiskInfoNotMapped) {
      String albumFolderNameIs = albumOnDiscInfo.albumFolderName();
      LOGGER.info("Checking against:      " + albumFolderNameIs);
      if (albumFolderNameIs.equals(albumFolderNameShallBe)) {
        if (foundAlbumOnDiscInfo == null) {
          foundAlbumOnDiscInfo = albumOnDiscInfo;
        } else {
          // error: more than one match
          LOGGER.severe("Album disc in mediaDb found more than once on disc: " + albumOnDiscInfo.issueDate() + " - " + albumOnDiscInfo.artist() + " - " + albumOnDiscInfo.title());
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
  private TrackOnHardDiskInfo findTrackInTracksOnDiscInfo(TrackReference trackReference, List<TrackOnHardDiskInfo> tracksOnDiscInfo) {
    String trackFileNameByConvention = MediaDbAppUtil.generateTrackFileNameForATrackInATracksFolder(trackReference.getTrack(), mediaDb);
    Artist artist = MediaDbAppUtil.getTrackArtist(trackReference.getTrack());
    String trackFileNameByConventionShort = TrackFile.generateTrackFileNameForATrackInATracksFolder(artist.getName(), trackReference.getTrack().getTitle());
    for (TrackOnHardDiskInfo trackOnDiscInfo: tracksOnDiscInfo) {
      // first only check on filename, if a match, check the folder.
      String trackFileNameOnDisc = trackOnDiscInfo.getTrackPath().getFileName().toString();
      String trackFileNameOnDiscWithoutExtension = FileUtils.getFileNameWithoutExtension(trackFileNameOnDisc);
      if (trackFileNameOnDiscWithoutExtension.equals(trackFileNameByConvention)
          || trackFileNameOnDiscWithoutExtension.equals(trackFileNameByConventionShort)) {
        String trackFolder = trackOnDiscInfo.getTrackPath().getParent().getFileName().toString();
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        if (myTrackInfo == null) {
          return null;
        }
        LOGGER.fine("Track folder=" + trackOnDiscInfo.getTrackPath().getParent().getFileName().toString());

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
  private static TrackOnHardDiskInfo findTrackInTracksOnDiscInfo(Album album, Integer discNr, Integer trackNr, List<TrackOnHardDiskInfo> tracksOnDiscInfo) {
    String artistName = null;
    if (album.isSetArtist()) {
      artistName = album.getArtist().getName();
    }
    
    TrackReference trackReference = MediaDbUtil.getTrackReference(album, discNr, trackNr);
    Track track = trackReference.getTrack();
    if ("Various Artists".equals(artistName)) {
      Artist trackArtist = MediaDbAppUtil.getTrackArtist(track);
      if (trackArtist != null) {
        artistName = trackArtist.getName();
      } else {
        LOGGER.severe("Track artist not set for a track on a Various Artists album. Album is " + MediaDbUtil.getArtistAndTitle(album) + ", track is " + track.getTitle());
      }
    }
    
    String trackFileNameByConvention = TrackFile.generateTrackFileNameForATrackInATracksFolder(artistName, track.getTitle());
    String trackFileNameByConvention2 =  TrackFile.generateTrackFileNameIncludingAlbumInfoForTracksFolder(artistName, album.getReleaseDate(), album.getTitle(), trackNr, track.getTitle());
    for (TrackOnHardDiskInfo trackOnDiscInfo: tracksOnDiscInfo) {
      // first only check on filename, if a match, check the folder.
      String trackFileNameOnDisc = trackOnDiscInfo.getTrackPath().getFileName().toString();
      String trackFileNameOnDiscWithoutExtension = FileUtils.getFileNameWithoutExtension(trackFileNameOnDisc);
      if ("New York Dolls".equals(album.getTitle())) {
        LOGGER.info("Checking against: trackFileNameOnDiscWithoutExtension=" + trackFileNameOnDiscWithoutExtension);
      }
      if (trackFileNameOnDiscWithoutExtension.equals(trackFileNameByConvention)  ||
          trackFileNameOnDiscWithoutExtension.equals(trackFileNameByConvention2)) {
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        LOGGER.fine("Track folder=" + trackOnDiscInfo.getTrackPath().getParent().getFileName().toString());

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
  public static TrackOnHardDiskInfo findTrackInReferredAlbum(Album album, Integer discNr, Integer trackNr, Album referredAlbum, Map<Album, AlbumOnHardDiskInfo> albumToMusicFolderLocationMap) {

    int foundTrackIndex = -1;
    int trackIndex = 0;
    String albumTitle = album.getTitle();
    if (discNr == null) {
      discNr = 1;
    }
    
    for (TrackReference trackReference: referredAlbum.getDiscs().get(0).getTrackReferences()) {
      TrackReference originalAlbumTrackReference = MediaDbUtil.getOriginalDiscTrackReference(trackReference.getTrack());
      if (originalAlbumTrackReference != null) {
        int referredTrackNr = MediaDbUtil.getTrackNr(originalAlbumTrackReference);
        LOGGER.info("referredTrackNr: " + referredTrackNr);
        Disc disc = MediaDbUtil.getDisc(originalAlbumTrackReference);
        if (disc != null) {
          Album referredBackAlbum = MediaDbUtil.getAlbum(disc);
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
      AlbumOnHardDiskInfo albumDiscLocationInfo = albumToMusicFolderLocationMap.get(referredAlbum);
      if (albumDiscLocationInfo != null) {
        String trackFileName = albumDiscLocationInfo.trackFileNames().get(foundTrackIndex);

        Path trackPath = Paths.get(albumDiscLocationInfo.albumFolderName(), trackFileName);
        TrackOnHardDiskInfo trackOnDiscInfo = new TrackOnHardDiskInfo(trackPath);
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
   * @param errors An error list to which errors that occur shall be added.
   * @return AlbumOnDiscInfo for the <code>disc</code>, or null if it couldn't be found.
   */
  public AlbumOnHardDiskInfo findAlbumDiscInAlbumsOnDiscInfo(Disc disc, List<AlbumOnHardDiskInfo> albumsOnDiscInfo, List<Object> errors) {
    Album album = MediaDbUtil.getAlbum(disc);
    LOGGER.info("=> album: " + album.getArtist() + " - " + album.getTitle());
    
    String albumFolderNameShallBe;
    if (MediaDbUtil.getAlbum(disc).isSoundtrack()) {
      albumFolderNameShallBe = MediaDbAppUtil.generateSoundtrackAlbumDiscFolderName(disc, errors);
    } else {
      albumFolderNameShallBe = MediaDbAppUtil.generateAlbumDiscFolderName(disc, mediaDb, errors);
    }
    
    LOGGER.info("albumFolderNameShallBe: " + albumFolderNameShallBe);
    AlbumOnHardDiskInfo foundAlbumOnDiscInfo = null;

    for (AlbumOnHardDiskInfo albumOnDiscInfo: albumsOnDiscInfo) {
      String albumFolderNameIs = albumOnDiscInfo.albumFolderName();
      LOGGER.info("Checking against:      " + albumFolderNameIs);
      if (albumFolderNameIs.equals(albumFolderNameShallBe)) {
        if (foundAlbumOnDiscInfo == null) {
          foundAlbumOnDiscInfo = albumOnDiscInfo;
        } else {
          // error: more than one match
          LOGGER.severe("Album disc in mediaDb found more than once on disc: " + albumOnDiscInfo.issueDate() + " - " + albumOnDiscInfo.artist() + " - " + albumOnDiscInfo.title());
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
   * @param trackFileName name of a file, which should match with the <code>trackReference</code>.
   * @param myCompilation if true, the <code>trackReference</code> is from a MyCompilation album.
   * @return A Path to <code>trackFileName</code> if trackFileName matches the <code>trackReference</code>, null otherwise.
   */
  public boolean findTrackPathMatch(TrackReference trackReference, String trackFileName, boolean myCompilation) {
    boolean found = false;

    String trackFileNameWithoutExtension = FileUtils.getFileNameWithoutExtension(trackFileName);
    String expectedTrackFileName;

    if (myCompilation) {
      TrackReference originalAlbumTrackReference = MediaDbUtil.getOriginalDiscTrackReference(trackReference.getTrack());
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
          LOGGER.info("No track filename match, based on <trackNr> - <trackTitle>. " + " Expected=" + expectedTrackFileName + ", Actual=" + trackFileName);
        }

        // If no match, just try the track title.
        if (!found) {
          expectedTrackFileName = trackReference.getTrack().getTitle();

          if (trackFileNameWithoutExtension.equals(expectedTrackFileName)) {
            found = true;
          } else {
            LOGGER.info("No track filename match, based on <trackTitle>. " + " Expected=" + expectedTrackFileName + ", Actual=" + trackFileName);
          }
        }
      }
    } else {
      expectedTrackFileName = MediaDbAppUtil.generateTrackFileNameForStandardAlbum(trackReference);
      if (trackFileNameWithoutExtension.equals(expectedTrackFileName)) {
        found = true;
      } else {
        LOGGER.info("No track filename match for track in Standard Album. " + " Expected=" + expectedTrackFileName + ", Actual=" + trackFileName);
      }
    }


    return found;
  }

}
