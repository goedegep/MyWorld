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
import goedegep.media.mediadb.model.MediumInfo;
import goedegep.media.mediadb.model.MediumType;
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
   * <p>
   * This list is initially filled with all albums in the MusicFolder ({@code musicFolderContent.getAlbumsOnDiscInfo()}.
   * When an album is used in a mapping it is removed from this list.
   * The remaining items in this list (after performing the mapping) are albums in the MusicFolder which are not referred to in the media database.
   */
  private List<AlbumOnDiscInfo> albumsOnDiscInfoNotMapped;
  
  /**
   * List of TrackOnDiscInfo not yet used.
   * <p>
   * This list is initially filled with the tracks from the track folders in the MusicFolder ({@code musicFolderContent.getTracksOnDiscInfo()}.
   * When a track is used in a mapping it is removed from this list.
   * The remaining items in this list (after performing the mapping) are track in the MusicFolder which are not referred to in the media database.
   */
  private List<TrackOnDiscInfo> tracksOnDiscInfoNotMapped;

  /**
   * A map which provides information on where a single-disc album in the media database can be found on disc (in the MusicFolder).
   * <p>
   * If an album is completely available on disc, it will be in this map.<br/>
   * If any number of tracks are available on disc (partly available), in the folder for that album (so not via track references to another album), it will be in this map.
   */
  private Map<Album, AlbumOnDiscInfo> albumToMusicFolderLocationMap = new HashMap<>();
  
  /**
   * A map which provides information on where a disc of a multi-disc album in the media database can be found on disc (in the MusicFolder).
   */
  private Map<Disc, AlbumOnDiscInfo> albumDiscToMusicFolderLocationMap = new HashMap<>();
  
  /**
   * A map which provides information on where a track in the media database can be found on disc (in the MusicFolder).
   */
  private Map<Track, Path> trackDiscLocationMap = new HashMap<>();
  
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
    
    // Start with copies of the albumsOnDiscInfo and tracksOnDiscInfo, so items handled can be removed.
    albumsOnDiscInfoNotMapped = new ArrayList<>(musicFolderContent.getAlbumsOnDiscInfo());
    tracksOnDiscInfoNotMapped = new ArrayList<>(musicFolderContent.getTracksOnDiscInfo());
  }

  /**
   * Create the mapping for albums and tracks, that should be available on disc, to their locations on disc.
   * 
   * @param ignoreMissingBonusTracks if true, bonus tracks of an album that can't be found are not treated as errors.
   *        Otherwise an error is created for a missing bonus track (as for any missing track).
   */
  public boolean createMediaDbToDiscLocationMap(boolean ignoreMissingBonusTracks) {
    
    for (Album album: mediaDb.getAlbums()) {
      createAndAddAlbumMapping(album, ignoreMissingBonusTracks);
    }

    // Handle the separate tracks.
    createTrackLocationMap(ignoreMissingBonusTracks);

    for (TrackOnDiscInfo trackOnDiscInfo: tracksOnDiscInfoNotMapped) {
      LOGGER.fine("Track on disc NOT found in MediaDb: " + trackOnDiscInfo.getTrackPath().toString());
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
  public Map<Album, AlbumOnDiscInfo> getAlbumToMusicFolderLocationMap() {
    return albumToMusicFolderLocationMap;
  }

  /**
   * Get the map which provides information on where a disc of a multi-disc album in the media database can be found on disc (in the MusicFolder).
   * 
   * @return The map which provides information on where a disc of a multi-disc album in the media database can be found on disc (in the MusicFolder).
   */
  public Map<Disc, AlbumOnDiscInfo> getAlbumDiscToMusicFolderLocationMap() {
    return albumDiscToMusicFolderLocationMap;
  }

  /**
   * Get the map which provides information on where a track in the media database can be found on disc (in the MusicFolder).
   * 
   * @return the map which provides information on where a track in the media database can be found on disc (in the MusicFolder).
   */
  public Map<Track, Path> getTrackDiscLocationMap() {
    return trackDiscLocationMap;
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
   * Create a mapping from an {@code Album} in the media database to its location on disc.
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
    LOGGER.info("=> " + album.getArtistAndTitle());
    
    if ("Bigger, Better, Faster, More!".equals(album.getTitle())) {
      LOGGER.severe("STOP");
      LOGGER.severe(album.toString());
    }
    
    if (!album.isMultiDiscAlbum()) {
      // Single-disc album
      // Music folder is per album.
      
      // If I have album (partly) on disc, find the AlbumOnDiscInfo in the albumsOnDiscNotMapped
      if (MediaDbUtil.haveAlbumOnDisc(album)  ||  MediaDbUtil.haveAlbumPartlyOnDisc(album)) {
        AlbumOnDiscInfo albumOnDiscInfo = null;
        if (MediaDbUtil.haveAlbumOnDisc(album)) {
          albumOnDiscInfo = findAlbumInAlbumsOnDiscInfo(album);
        } else {
          albumOnDiscInfo = createAndAddMappingForAlbumPartlyOnDisc(album);
        }
        
        if (albumOnDiscInfo != null) {
          // Put the album and AlbumOnDiscInfo in the albumMusicFolderLocationMap
          albumToMusicFolderLocationMap.put(album, albumOnDiscInfo);
          
          // Remove the album from the AlbumOnDiscInfo
          albumsOnDiscInfoNotMapped.remove(albumOnDiscInfo);

          // add the tracks of the album to the trackDiscLocationMap
          addAlbumDiscTracksToTrackDiscLocationMap(album.getDiscs().get(0), albumOnDiscInfo, ignoreMissingBonusTracks);
        } else {
          LOGGER.severe("Album in media database not found on disc: " + album.getArtistAndTitle());
          MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.ALBUM_IN_MEDIADB_NOT_FOUND_ON_DISC);
          error.setAlbum(album);
          errors.add(error);
        }
      }
      
      if (MediaDbUtil.haveAlbumOnDisc(album)) {
      } else if (MediaDbUtil.haveAlbumPartlyOnDisc(album)) {
//        createAndAddMappingForAlbumPartlyOnDisc(album);
      }
    } else {
      // Music folders are per disc, i.e. a disc of an album is treated the same as a single disc album. Only the folder name has a disc specific extension. 
      if  (MediaDbUtil.haveAlbumOnDisc(album)) {
        for (Disc disc: album.getDiscs()) {
          createAndAddMappingForDiscCompletelyOnDisc(disc, ignoreMissingBonusTracks);
        }
      } else {
        for (Disc disc: album.getDiscs()) {
          if (MediaDbUtil.haveDiscOnDisc(disc)) {
            createAndAddMappingForDiscCompletelyOnDisc(disc, ignoreMissingBonusTracks);
          } else if (MediaDbUtil.doIHaveOneOrMoreDiscTracksOnDisc(disc)) {
            createAndAddMappingForDiscPartlyOnDisc(disc);
          }
        }
      }
      
    }
  }

  /**
   * Create and add the mapping for an album which is partly available on disc.
   * 
   * @param album the album for which the mapping is to be created.
   */
  private AlbumOnDiscInfo createAndAddMappingForAlbumPartlyOnDisc(Album album) {
    LOGGER.info("=> album: " + album.getArtistAndTitle());
    
    String albumFolderName = null;
    List<String> trackFileNames = new ArrayList<>();
    
    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
        if (myTrackInfo != null) {
          if (myTrackInfo.getCollection() != null  &&  myTrackInfo.getCollection() != Collection.NOT_SET) {
            TrackOnDiscInfo trackOnDiscInfo = createAndAddMappingForTrackInCollection(trackReference);
            if (trackOnDiscInfo != null) {
              trackFileNames.add(trackOnDiscInfo.getTrackPath().toString());
              String folderName = trackOnDiscInfo.getTrackPath().getParent().toString();
              if (albumFolderName == null) {
                albumFolderName = folderName;
              } else {
                if (!folderName.equals(albumFolderName)) {
                  LOGGER.severe("Tracks in different folders");
                }
              }
            }
          } else {         
            for (MediumInfo mediumInfo: myTrackInfo.getIHaveOn()) {
              if (mediumInfo.getMediumType() == MediumType.HARDDISK) {
                createAndAddMappingForTrackOfAlbum(trackReference);
              }
            }
          }
        } else {
          throw new RuntimeException("album partly on disc has no myTrackInfo: " + album);
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
                Track track = album.getTrackReference(discNr, trackNr).getTrack();
                TrackOnDiscInfo trackOnDiscInfo = findTrackInReferredAlbum(album, discNr, trackNr, referredAlbum, albumToMusicFolderLocationMap);

                if (trackOnDiscInfo != null) {
                  Path pathToTrack = trackOnDiscInfo.getTrackPath();
                  LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
                  trackDiscLocationMap.put(track, pathToTrack);
                  tracksOnDiscInfoNotMapped.remove(trackOnDiscInfo);
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
                Track track = album.getTrackReference(discNr, trackNr).getTrack();
                TrackReference trackReference = album.getTrackReference(discNr, trackNr);
                TrackReference originalAlbumTrackReference = trackReference.getOriginalAlbumTrackReference();
                if (originalAlbumTrackReference != null) {
                  MyTrackInfo originalAlbumTrackReferenceMyTrackInfo = originalAlbumTrackReference.getMyTrackInfo();
                  if (originalAlbumTrackReferenceMyTrackInfo != null) {
                    List<MediumInfo> mediumInfos = originalAlbumTrackReferenceMyTrackInfo.getIHaveOn();
                    for (MediumInfo mediumInfo: mediumInfos) {
                      LOGGER.severe("MediumInfo: " + mediumInfo);
                    }
                  }
                }
                TrackOnDiscInfo trackOnDiscInfo = findTrackInTracksOnDiscInfo(album, discNr, trackNr, tracksOnDiscInfoNotMapped);

                if (trackOnDiscInfo != null) {
                  Path pathToTrack = trackOnDiscInfo.getTrackPath();
                  LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
                  trackDiscLocationMap.put(track, pathToTrack);
                  tracksOnDiscInfoNotMapped.remove(trackOnDiscInfo);
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
    AlbumOnDiscInfo albumOnDiscInfo = new AlbumOnDiscInfo(null, null, null, albumFolderName, trackFileNames);
    
    return albumOnDiscInfo;
  }
  
  private void createAndAddMappingForTrackOfAlbum(TrackReference trackReference) {
    TrackOnDiscInfo trackOnDiscInfo = findTrackInTracksOnDiscInfo(trackReference, tracksOnDiscInfoNotMapped);

    if (trackOnDiscInfo != null) {
      Path pathToTrack = trackOnDiscInfo.getTrackPath();
      LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
      trackDiscLocationMap.put(trackReference.getTrack(), pathToTrack);
      tracksOnDiscInfoNotMapped.remove(trackOnDiscInfo);
    } else {
      LOGGER.fine("Path NOT found: ");
      MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC);
      error.setAlbum(trackReference.getDisc().getAlbum());
      error.setTrack(trackReference.getTrack());
      errors.add(error);
    }
  }

  
  private TrackOnDiscInfo createAndAddMappingForTrackInCollection(TrackReference trackReference) {
    TrackOnDiscInfo trackOnDiscInfo = findCollectionTrackInTracksOnDiscInfo(trackReference, tracksOnDiscInfoNotMapped);

    if (trackOnDiscInfo != null) {
      Path pathToTrack = trackOnDiscInfo.getTrackPath();
      LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
      trackDiscLocationMap.put(trackReference.getTrack(), pathToTrack);
      tracksOnDiscInfoNotMapped.remove(trackOnDiscInfo);
    } else {
      LOGGER.fine("Path NOT found: ");
      MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC);
      error.setAlbum(trackReference.getDisc().getAlbum());
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
  private static TrackOnDiscInfo findCollectionTrackInTracksOnDiscInfo(TrackReference trackReference, List<TrackOnDiscInfo> tracksOnDiscInfo) {
    String trackFileNameByConvention = MediaDbAppUtil.generateTrackFileNameForATrackInATracksFolder(trackReference.getTrack());
    if (trackFileNameByConvention == null) {
      trackFileNameByConvention = MediaDbAppUtil.generateTrackFileNameForATrackInATracksFolder(trackReference.getTrack());
      throw new RuntimeException("Error: track is not valid");
    }

    for (TrackOnDiscInfo trackOnDiscInfo: tracksOnDiscInfo) {
      // first only check on filename, if a match, check the folder.
      String trackFileNameOnDisc = trackOnDiscInfo.getTrackPath().getFileName().toString();
      String trackFileNameOnDiscWithoutExtension = FileUtils.getFileNameWithoutExtension(trackFileNameOnDisc);
      if (trackFileNameOnDiscWithoutExtension.equals(trackFileNameByConvention)) {
        String trackFolder = trackOnDiscInfo.getTrackPath().getParent().getFileName().toString();
        MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
//        if (myTrackInfo == null) { myTrackInfo cannot be null for a collection track of an album
//          return null;
//        }
        Collection collection = myTrackInfo.getCollection();
        String collectionFolder = MediaDbAppUtil.getCollectionFolderName(collection);
//        if (collection.equals(Collection.NOT_SET)) {  // cannot occur for a collection track of an album
//          LOGGER.info("Collection not set for track: track=" + trackReference.toString());
//        } else if (!trackFolder.equals(collectionFolder)) {
//          LOGGER.severe("Track in wrong collection: track=" + trackReference.toString());
//        }
        if (!trackFolder.equals(collectionFolder)) {
          LOGGER.severe("Track in wrong collection: track=" + trackReference.toString());
          return null;
        } else {
          LOGGER.info("Track folder=" + trackOnDiscInfo.getTrackPath().getParent().getFileName().toString());
          LOGGER.info("Collection=" + trackReference.getMyTrackInfo().getCollection().getLiteral());

          return trackOnDiscInfo;
        }
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
    AlbumOnDiscInfo albumOnDiscInfo = findAlbumDiscInAlbumsOnDiscInfo(disc, albumsOnDiscInfoNotMapped, mediaDb, errors);
    if (albumOnDiscInfo != null) {
      LOGGER.info("Found disc of album on disc");
//      AlbumDiscLocationInfo albumDiscLocationInfo = new AlbumDiscLocationInfo(albumOnDiscInfo.getAlbumFolderName(), albumOnDiscInfo.getTrackFileNames());
      albumsOnDiscInfoNotMapped.remove(albumOnDiscInfo);
      albumDiscToMusicFolderLocationMap.put(disc, albumOnDiscInfo);

      // add the tracks of the album to the trackDiscLocationMap
      addAlbumDiscTracksToTrackDiscLocationMap(disc, albumOnDiscInfo, ignoreMissingBonusTracks);
    } else {
      LOGGER.info("Album disc in media database not found on disc: " + disc.getAlbum().getArtistAndTitle() + ", disc: " + (disc.getAlbum().getDiscs().indexOf(disc) + 1));
      MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.ALBUM_IN_MEDIADB_NOT_FOUND_ON_DISC);
      error.setDisc(disc);
      error.setAlbum(disc.getAlbum());
      errors.add(error);
    }
  }

  private void createAndAddMappingForDiscPartlyOnDisc(Disc disc) {
    if ("Annie Lennox - Medusa, Live In Central Park".equals(disc.getAlbum().getArtistAndTitle())) {
      LOGGER.severe("STOP");
    }
    AlbumOnDiscInfo albumOnDiscInfo = findAlbumDiscInAlbumsOnDiscInfo(disc, albumsOnDiscInfoNotMapped, mediaDb, errors);
    if (albumOnDiscInfo != null) {
      addAlbumDiscTracksToTrackDiscLocationMap(disc, albumOnDiscInfo, false);
    } else {
      LOGGER.info("Still to handle references");
    }
  }

//  /**
//   * Create a mapping from Albums in the media database to their location on disc, for albums which I have partly on disc.
//   * <p>
//   * Based on information from the media database (describing which albums we should have partly) and from what we have in the 
//   * MusicFolder, the {@link #trackDiscLocationMap} is filled which maps album tracks to their location in the Music Folder.
//   */
//  private void createPartlyAlbumDiscLocationMap() {
//    for (Album album: mediaDb.getAlbums()) {
//
//      LOGGER.info("Handling album: " + album.getArtistAndTitle());
//
//      List<DiscAndTrackNrs> discAndTrackNrsList = null;
//      if (MediaDbUtil.haveAlbumPartlyOnDisc(album)) {
//        discAndTrackNrsList = MediaDbUtil.getAlbumTracksIHave(album, true);
//      }
//
//      if (discAndTrackNrsList != null) {
//        LOGGER.fine("I should have tracks on disc");
//        // Separate tracks are either stored as track within a Collection, they are on some other album, or they are part of my own compilation for that artist.
//        MyInfo myInfo = album.getMyInfo();
//
//        if (myInfo != null) {
//          Album referredAlbum = null;
//          List<Album> albumReferences = myInfo.getAlbumReferences();
//          if (!albumReferences.isEmpty()) {
//            // TODO handle multiple references
//            referredAlbum = albumReferences.get(0);
//          }
//          if (referredAlbum != null) {
//            for (DiscAndTrackNrs discAndTrackNr: discAndTrackNrsList) {
//              for (Integer trackNr: discAndTrackNr.getTrackNrs()) {
//                Integer discNr = null;
//                if (discAndTrackNr.isSetDiscNr()) {
//                  discNr = discAndTrackNr.getDiscNr();
//                }
//                try {
//                  Track track = album.getTrackReference(discNr, trackNr).getTrack();
//                  TrackOnDiscInfo trackOnDiscInfo = findTrackInReferredAlbum(album, discNr, trackNr, referredAlbum, albumToMusicFolderLocationMap);
//
//                  if (trackOnDiscInfo != null) {
//                    Path pathToTrack = trackOnDiscInfo.getTrackPath();
//                    LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
//                    trackDiscLocationMap.put(track, pathToTrack);
//                    tracksOnDiscInfoNotMapped.remove(trackOnDiscInfo);
//                  } else {
//                    LOGGER.fine("Path NOT found: ");
//                    MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC);
//                    error.setAlbum(album);
//                    error.setTrack(track);
//                    errors.add(error);
//                  }
//                } catch(Exception e) {
//                  LOGGER.severe("Track not found for album. album=" + album.toString() + "discNr=" + discNr + "trackNr=" + trackNr);
//                }
//              }
//            }
//          } else {
//            // For now find it in the Collections.
//            for (DiscAndTrackNrs discAndTrackNrs: discAndTrackNrsList) {
//              for (Integer trackNr: discAndTrackNrs.getTrackNrs()) {
//                Integer discNr = null;
//                if (discAndTrackNrs.isSetDiscNr()) {
//                  discNr = discAndTrackNrs.getDiscNr();
//                }
//                try {
//                  Track track = album.getTrackReference(discNr, trackNr).getTrack();
//                  TrackReference trackReference = album.getTrackReference(discNr, trackNr);
//                  TrackReference originalAlbumTrackReference = trackReference.getOriginalAlbumTrackReference();
//                  if (originalAlbumTrackReference != null) {
//                    MyTrackInfo originalAlbumTrackReferenceMyTrackInfo = originalAlbumTrackReference.getMyTrackInfo();
//                    if (originalAlbumTrackReferenceMyTrackInfo != null) {
//                      List<MediumInfo> mediumInfos = originalAlbumTrackReferenceMyTrackInfo.getIHaveOn();
//                      for (MediumInfo mediumInfo: mediumInfos) {
//                        LOGGER.severe("MediumInfo: " + mediumInfo);
//                      }
//                    }
//                  }
//                  TrackOnDiscInfo trackOnDiscInfo = findTrackInTracksOnDiscInfo(album, discNr, trackNr, tracksOnDiscInfoNotMapped);
//
//                  if (trackOnDiscInfo != null) {
//                    Path pathToTrack = trackOnDiscInfo.getTrackPath();
//                    LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
//                    trackDiscLocationMap.put(track, pathToTrack);
//                    tracksOnDiscInfoNotMapped.remove(trackOnDiscInfo);
//                  } else {
//                    LOGGER.fine("Path NOT found: ");
//                    MediaDbAppErrorInfo error = new MediaDbAppErrorInfo(MediaDbAppError.TRACK_IN_MEDIADB_NOT_FOUND_ON_DISC);
//                    error.setAlbum(album);
//                    error.setTrack(track);
//                    errors.add(error);
//                  }
//                } catch(Exception e) {
//                  LOGGER.severe("Track not found for album. album=" + album.toString() + "discNr=" + discNr + "trackNr=" + trackNr);
//                }
//              }
//            }
//          }
//        }
//      }
//    }
//  }

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

      TrackOnDiscInfo trackOnDiscInfo = findTrackInTracksOnDiscInfo(trackReference, tracksOnDiscInfoNotMapped);

      if (trackOnDiscInfo != null) {
        Path pathToTrack = trackOnDiscInfo.getTrackPath();
        LOGGER.fine("Path found: " + pathToTrack.toAbsolutePath().toString());
        trackDiscLocationMap.put(trackReference.getTrack(), pathToTrack);
        tracksOnDiscInfoNotMapped.remove(trackOnDiscInfo);
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
   * @param albumOnDiscInfo information on the album on disc.
   * @param ignoreMissingBonusTracks If set, a bonus track not found is not reported as an error.
   */
  public void addAlbumDiscTracksToTrackDiscLocationMap(Disc disc, AlbumOnDiscInfo albumOnDiscInfo, boolean ignoreMissingBonusTracks) {
    Album album = disc.getAlbum();
    boolean haveAllTracksOnDisc = MediaDbUtil.haveAlbumOnDisc(album);

    boolean myCompilation = false;
    if (MediaDbUtil.isOwnCompilationAlbum(album)) {
      myCompilation = true;
    }

    int trackIndex = 0;
    for (TrackReference trackReference: disc.getTrackReferences()) {
      LOGGER.info("trackReference: " + trackReference + ", trackIndex=" + trackIndex);

      if (haveAllTracksOnDisc  ||  MediaDbUtil.haveTrackOnDisc(trackReference)) {
        boolean trackFound = false;
        String trackFileName = null;
        Track track = trackReference.getTrack();

        if (trackIndex < albumOnDiscInfo.getTrackFileNames().size()) {
          trackFileName = albumOnDiscInfo.getTrackFileNames().get(trackIndex);
          trackFound = findTrackPathMatch(trackReference, trackFileName, myCompilation);
        }

        if (trackFound) {
          trackDiscLocationMap.put(track, Paths.get(albumOnDiscInfo.getAlbumFolderName(), trackFileName));
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
  private AlbumOnDiscInfo findAlbumInAlbumsOnDiscInfo(Album album) {
    LOGGER.info("=> album: " + album.getArtist() + " - " + album.getTitle());
        
    String albumFolderNameShallBe;
    if (album.isSoundtrack()) {
      albumFolderNameShallBe = MediaDbAppUtil.generateSoundtrackAlbumDiscFolderName(album.getDiscs().get(0), errors);
    } else {
      albumFolderNameShallBe = MediaDbAppUtil.generateAlbumDiscFolderName(album.getDiscs().get(0), mediaDb, errors);
    }
    
    LOGGER.info("Generated folder name: " + albumFolderNameShallBe);
    AlbumOnDiscInfo foundAlbumOnDiscInfo = null;

    for (AlbumOnDiscInfo albumOnDiscInfo: albumsOnDiscInfoNotMapped) {
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
    String trackFileNameByConvention = MediaDbAppUtil.generateTrackFileNameForATrackInATracksFolder(trackReference.getTrack());

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
      Artist trackArtist = MediaDbAppUtil.getTrackArtist(track);
      if (trackArtist != null) {
        artistName = trackArtist.getName();
      } else {
        LOGGER.severe("Track artist not set for a track on a Various Artists album. Album is " + album.getArtistAndTitle() + ", track is " + track.getTitle());
      }
    }
    
    String trackFileNameByConvention = TrackFile.generateTrackFileNameForATrackInATracksFolder(artistName, track.getTitle());
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
  public static TrackOnDiscInfo findTrackInReferredAlbum(Album album, Integer discNr, Integer trackNr, Album referredAlbum, Map<Album, AlbumOnDiscInfo> albumToMusicFolderLocationMap) {

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
      AlbumOnDiscInfo albumDiscLocationInfo = albumToMusicFolderLocationMap.get(referredAlbum);
      if (albumDiscLocationInfo != null) {
        String trackFileName = albumDiscLocationInfo.getTrackFileNames().get(foundTrackIndex);

        Path trackPath = Paths.get(albumDiscLocationInfo.getAlbumFolderName(), trackFileName);
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
   * @param trackFileName name of a file, which should match with the <code>trackReference</code>.
   * @param myCompilation if true, the <code>trackReference</code> is from a MyCompilation album.
   * @return A Path to <code>trackFileName</code> if trackFileName matches the <code>trackReference</code>, null otherwise.
   */
  public boolean findTrackPathMatch(TrackReference trackReference, String trackFileName, boolean myCompilation) {
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
