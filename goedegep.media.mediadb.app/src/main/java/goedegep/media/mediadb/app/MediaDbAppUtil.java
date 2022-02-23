package goedegep.media.mediadb.app;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Collection;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.IWant;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.MyCompilation;
import goedegep.media.mediadb.model.MyInfo;
import goedegep.media.mediadb.model.MyTrackInfo;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.media.musicfolder.AlbumFolder;
import goedegep.media.musicfolder.MusicFolder;
import goedegep.media.musicfolder.MusicFolderUtil;
import goedegep.media.musicfolder.TrackFile;
import goedegep.util.datetime.FlexDate;
import goedegep.util.file.FileUtils;
import javafx.collections.ObservableList;

/**
 * This class provides utility methods related to the MediaDb data model.
 */
public class MediaDbAppUtil {
  private static final Logger LOGGER = Logger.getLogger(MediaDbAppUtil.class.getName());
  
//  private static final MediadbFactory MEDIA_DB_FACTORY = MediadbFactory.eINSTANCE;
  
  /**
   * Constructor
   * <p>
   * As this is a utility class, it should not be instantiated. This is guaranteed by this private constructor.
   */
  private MediaDbAppUtil() {
  }

  /**
   * Check the contents of a <code>MediaDB</code>.
   * <p>
   * The following information is checked:
   * <ul>
   * <li>
   * Information for each album (see {@link #checkAlbumInfo}.
   * </li>
   * </ul>
   * 
   * @param mediaDb the media database to be checked
   * @param errors the list to which errors shall be appended
   */
  public static void checkMediaDb(MediaDb mediaDb, List<Object> errors) {
    for (Album album: mediaDb.getAlbums()) {
      checkAlbumInfo(album, errors);
    }
  }

  /**
   * Check the information of an <code>Album</code>.
   * <p>
   * The following information is checked:
   * <ul>
   * <li>
   * the album shall have a title
   * </li>
   * <li>
   * the album shall have an artist
   * </li>
   * </ul>
   * 
   * @param album the <code>Album</code> to be checked
   * @param errors the list to which errors shall be appended
   */
  public static void checkAlbumInfo(Album album, List<Object> errors) {
    MediaDbErrorInfo errorInfo;

    if (album.getTitle() == null) {
      errorInfo = new MediaDbErrorInfo();
      errorInfo.setErrorCode(MediaDbError.ALBUM_WITHOUT_TITLE);
      errorInfo.setAlbum(album);
      errors.add(errorInfo);
      LOGGER.severe("No Album title set for album: " + album.getArtistAndTitle());
    }
    if (album.getArtist() == null  &&  !album.isSoundtrack()) {
      errorInfo = new MediaDbErrorInfo();
      errorInfo.setErrorCode(MediaDbError.ALBUM_WITHOUT_ARTIST);
      errorInfo.setAlbum(album);
      errors.add(errorInfo);
      LOGGER.severe("No Artist set for album: " + album.getArtistAndTitle());
    }

    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        String trackTitle = trackReference.getTrack().getTitle();
        if (trackTitle != null) {
          if (trackTitle.toLowerCase().contains("bonus track")) {
            errorInfo = new MediaDbErrorInfo();
            errorInfo.setErrorCode(MediaDbError.BONUS_TRACK_IN_TITLE);
            errorInfo.setAlbum(album);
            errorInfo.setTrackReference(trackReference);
            errors.add(errorInfo);
            LOGGER.severe("Track title contains 'bonus track'. Album: " + album.getArtistAndTitle() + ", Track: " + trackTitle);
          }
        }
      }
    }
  }

//  /**
//   * Check that I do not want an album.
//   * 
//   * @param myInfo my information about the album.
//   * @return true if I do not want the album, false otherwise.
//   */
//  public static boolean iWantIsNo(MyInfo myInfo) {
//    if (myInfo != null) {
//      if (myInfo.getIWant() == IWant.NO) {
//        return true;
//      }
//    }
//    
//    return false;
//  }

//  /**
//   * Check whether I don't know if I want the album or not.
//   * 
//   * @param myInfo my information about the album.
//   * @return true if I don't know whether I want the album, false otherwise.
//   */
//  public static boolean iWantIsDontKnow(MyInfo myInfo) {
//    if (myInfo != null) {
//      if (myInfo.getIWant() == IWant.DONT_KNOW) {
//        return true;
//      }
//    }
//    
//    return false;
//  }

  /**
   * Get the album to which an album refers to.
   * 
   * TODO change for more than 1 reference.
   * 
   * @param myInfo my information about the album.
   * @return the album to which the album refers to, or <code>null</code> if there is no reference.
   */
  public static Album getReferredAlbum(MyInfo myInfo) {
    if (myInfo == null) {
      return null;
    }
    
    if (myInfo.getAlbumReferences().size() > 0) {
      return myInfo.getAlbumReferences().get(0);
    }
    
    return null;
  }

  /**
   * Check whether I want to get an album, or some tracks of it.
   * 
   * @param myInfo my information about the album.
   * @return true if want to get the album, or some track of it, false otherwise.
   */
  public static boolean iHaveToGetAlbumOrTracks(Album album) {
    if (MediaDbUtil.getIWant(album) == IWant.YES) {
      return true;
    }
    
    for (Disc disc: album.getDiscs()) {
      for (TrackReference trackReference: disc.getTrackReferences()) {
        if (trackReference.isSetMyTrackInfo()) {
          MyTrackInfo myTrackInfo = trackReference.getMyTrackInfo();
          if (myTrackInfo.isSetIWant()) {
            IWant iWant = myTrackInfo.getIWant();
            if (iWant == IWant.YES  &&  myTrackInfo.getIHaveOn().isEmpty()) {
              return true;
            }
          }
        }
      }
    }
    
    return false;
  }
  
  /**
   * Get a sorted list of container artists in a <code>MediaDb</code>.
   * 
   * @param mediaDb the <code>MediaDb</code> from which to extract the information.
   * @return a sorted list (on artist name) of container artists.
   */
  public static List<Artist> getContainerArtists(MediaDb mediaDb) {
    List<Artist> artists = new ArrayList<>();
    
    for (Album album: mediaDb.getAlbums()) {
      Artist artist = album.getArtist();
      if (artist == null) {
        LOGGER.severe("No artist for album: " + album.toString());
        continue;
      }
      
      if (artist.getContainerArtist() != null) {
        artist = artist.getContainerArtist();
      }
      
      if (!artists.contains(artist)) {
        artists.add(artist);
      }
    }
    
    Collections.sort(artists, (artist1, artist2) -> {return artist1.getName().compareTo(artist2.getName());});
    
    return artists;
  }
  
  /**
   * Generate the filename for MyTrackReferenceInfo of a MyCompilation album in the MusicFolder.
   * 
   * @param myTrackReferenceInfo the MyTrackReferenceInfo for which the filename is to be generated.
   * @return the filename for the <code>trackReference</code>.
   */
  public static String generateTrackFileNameForMyCompilationAlbum(TrackReference originalAlbumTrackReference, MediaDb mediaDb) {
    StringBuilder buf = new StringBuilder();
    
    if (originalAlbumTrackReference == null) {
      throw new RuntimeException("myTrackReferenceInfo may not be null");
    }
    
    Disc originalAlbumDisc = originalAlbumTrackReference.getDisc();
    
    // four digit album year
    Album originalAlbum = originalAlbumDisc.getAlbum();
    FlexDate releaseDate = originalAlbum.getReleaseDate();
    int year = releaseDate.getYear();    
    
    boolean addMonthToFileName = false;
    if (originalAlbum.isSetArtist()) {
      Artist artist = originalAlbum.getArtist();
      if (artist.isSetContainerArtist()) {
        artist = artist.getContainerArtist();
      }

      List<Album> albumsReleasedInYear = new ArrayList<>();
      for (Album anAlbum: mediaDb.getAlbums()) {
        if (anAlbum.isSetArtist()) {
          Artist anAlbumArtist = anAlbum.getArtist();
          if (anAlbumArtist.isSetContainerArtist()) {
            anAlbumArtist = anAlbumArtist.getContainerArtist();
          }

          if (artist.equals(anAlbumArtist)) {
            if (anAlbum.isSetReleaseDate()) {
              FlexDate anAlbumReleaseDate = anAlbum.getReleaseDate();
              int anAlbumYear = anAlbumReleaseDate.getYear();
              if (anAlbumYear == year) {
                albumsReleasedInYear.add(anAlbum);
              }
            }
          }
        }
      }
      
      if (albumsReleasedInYear.size() > 1) {
        addMonthToFileName = true;
      }

    }
    
    buf.append(year);
    if (addMonthToFileName) {
      buf.append("-");
      Integer month = releaseDate.getMonth();
      if (month != null) {
        if (month < 9) {
          buf.append(0);
        }
        buf.append(month + 1);
      } else {
        LOGGER.info("No release month for album, which should have it: " + originalAlbum.getArtistAndTitle());
      }
    }
    
    buf.append(" - ");
    
    // album title
    String title = originalAlbum.getTitle();
    buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(title, true));
    
    buf.append(" - ");
   
    // track number on album
    if (originalAlbum.getDiscs().size() > 1) {
      int discNr = originalAlbum.getDiscs().indexOf(originalAlbumDisc) + 1;
      buf.append(discNr).append("-");
    }
    int trackNr = originalAlbumDisc.getTrackReferences().indexOf(originalAlbumTrackReference) + 1;
    buf.append(String.format("%02d", trackNr));
    
    buf.append(" - ");
   
    
    // track title
    Track track = originalAlbumTrackReference.getTrack();
    String trackTitle = track.getTitle();
    buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(trackTitle, true));
    
    return buf.toString();
  }

  /**
   * Get the Tracks Folder name corresponding to a Collection.
   * 
   * @param collection The <code>Collection</code> for which the Tracks Folder will be returned.
   * @return The Tracks Folder for the <code>collection</code>.
   */
  public static String getCollectionFolderName(Collection collection) {
    return "zTracks " + collection.getLiteral();
  }

  public static String generateTrackFileName(Track track) {
    LOGGER.fine("=> track=" + track.getArtist() + " - " + track.getTitle());
    
    String artistName = null;
    if (track.isSetArtist()) {
      artistName = track.getArtist().getName();
    }
    
    String trackTitle = track.getTitle();

    String trackFileName = TrackFile.generateTrackFileName(artistName, trackTitle);
    LOGGER.fine("<= " + trackFileName);
    return trackFileName;
  }

  /**
   * Generate the filename for a trackReference of a standard album in the MusicFolder.
   * 
   * @param trackReference the TrackReference for which the filename is to be generated.
   * @return the filename for the <code>trackReference</code>.
   */
  public static String generateTrackFileNameForStandardAlbum(TrackReference trackReference) {

    Disc disc = trackReference.getDisc();
    int trackNr = disc.getTrackReferences().indexOf(trackReference) + 1;

    Track track = trackReference.getTrack();
    String trackTitle = track.getTitle();

    return TrackFile.generateTrackFileNameForStandardAlbum(trackNr, trackTitle);
  }

  /**
   * Generate the name of an album folder, based on the album information.
   * <p>
   * See {@link MusicFolder} for the naming convention for albums.
   * 
   * @param disc the album disc information
   * @param mediaDb the media database
   * @param errors an error list, to which errors encountered by this method are added.
   * @return the name of the album folder, or null if the name can't be generated (because of missing information).
   */
  public static String generateAlbumDiscFolderName(Disc disc, MediaDb mediaDb, List<Object> errors) {
    Album album = disc.getAlbum();
    LOGGER.fine("=> album=" + album.getTitle());
    
    if (album.isSoundtrack()) {
      throw new RuntimeException("Call AlbumOnDiscFolder.generateSoundtrackAlbumFolderName(disc, errors) for soundtrack albums");
    }
    
    Artist artist = album.getArtist();
    String artistName = artist != null ? artist.getName() : null;
    if (artistName == null) {
      return null;
    }
    
    String containerArtistName;
    if (artist.isSetContainerArtist()) {
      containerArtistName = artist.getContainerArtist().getName();
    } else {
      containerArtistName = artistName;
    }
    LOGGER.fine("Artist=" + artistName + ", containerArtistName=" + containerArtistName);
    
    FlexDate releaseDate = album.getReleaseDate();
    
    if ((releaseDate == null)  &&  !(album instanceof MyCompilation)) {
      LOGGER.severe("No release date specified for album");
      if (errors != null) {
        MediaDbAppErrorInfo errorInfo = new MediaDbAppErrorInfo(MediaDbAppError.NO_RELEASE_DATE_FOR_ALBUM);
        errorInfo.setAlbum(album);
        errors.add(errorInfo);
      }
      return null;
    }
    
    boolean haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc;
    if (album instanceof MyCompilation) {
      haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc = false;
    } else {
      haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc = haveMoreThanOneAlbumOfContainerArtistInAYearOnDisc(mediaDb, artist, releaseDate.getYear());
    }
    
    if (album.isMultiDiscAlbum()) {
      if (disc.isSetTitle()) {
        return AlbumFolder.generateAlbumDiscFolderName(containerArtistName, album.getTitle(), artistName, releaseDate, disc.getTitle(), haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc, album instanceof MyCompilation, errors);
      } else {
        return AlbumFolder.generateAlbumDiscFolderName(containerArtistName, album.getTitle(), artistName, releaseDate, disc.getDiscNr(), haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc, album instanceof MyCompilation, errors);
     }
    } else {    
      return AlbumFolder.generateAlbumDiscFolderName(containerArtistName, album.getTitle(), artistName, releaseDate, haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc, album instanceof MyCompilation, errors);
    }
  }
  
  /**
   * Generate the name of a soundtrack album folder, based on the disc information.
   * <p>
   * This method is a wrapper on top of {@link AlbumFolder.generateSoundtrackAlbumDiscFolderName}.
   * 
   * @param disc the Disc information
   * @param errors an error list, to which errors encountered by this method are added.
   * @return the name of the soundtrack album folder for the specified <code>disc</code>.
   */ 
  public static String generateSoundtrackAlbumDiscFolderName(Disc disc, List<Object> errors) {
    Album album = disc.getAlbum();
    LOGGER.fine("=> album=" + album.getTitle());

    Artist artist = album.getArtist();
    String artistName = artist != null ? artist.getName() : null;
    FlexDate releaseDate = album.getReleaseDate();
    
    if ((releaseDate == null)  &&  !(album instanceof MyCompilation)) {
      LOGGER.severe("No release date specified for album");
      if (errors != null) {
        MediaDbAppErrorInfo errorInfo = new MediaDbAppErrorInfo(MediaDbAppError.NO_RELEASE_DATE_FOR_ALBUM);
        errorInfo.setAlbum(album);
        errors.add(errorInfo);
      }
      return null;
    }
    
    if (album.isMultiDiscAlbum()) {
      if (disc.isSetTitle()) {
        return AlbumFolder.generateSoundtrackAlbumDiscFolderName(album.getTitle(), artistName, album.getReleaseDate(), disc.getTitle(), album instanceof MyCompilation, errors);
      } else {
        return AlbumFolder.generateSoundtrackAlbumDiscFolderName(album.getTitle(), artistName, album.getReleaseDate(), disc.getDiscNr(), album instanceof MyCompilation, errors);
      }
    } else {    
      return AlbumFolder.generateSoundtrackAlbumDiscFolderName(album.getTitle(), artistName, album.getReleaseDate(), album instanceof MyCompilation, errors);
    }
  }

  
  public static boolean haveMoreThanOneAlbumOfContainerArtistInAYearOnDisc(MediaDb mediaDb, Artist artist, int year) {
    FlexDate releaseYearFlexDate = new FlexDate(year);
    List<Album> albumsInYear = mediaDb.getAlbums(releaseYearFlexDate, artist, null);  // should find this album and any other album from same year
    List<Album> albumsOnDisc = new ArrayList<>();
    for (Album albumInYear: albumsInYear) {
      LOGGER.fine("albumInYear: " + albumInYear.getTitle());
      if (MediaDbUtil.haveAlbumOnDisc(albumInYear, null)) {
        albumsOnDisc.add(albumInYear);
      }
    }
    
    return albumsOnDisc.size() > 1;
  }
  
  /**
   * Generate information for importing disc tracks into a music folder.
   * 
   * @param disc the disc for which the information is generated.
   * @param sourceFolder the folder from where the tracks will be copied.
   * @param mediaDb the media database.
   */
  public static DiscTracksImportInfo createDiscTracksImportData(Disc disc, String sourceFolder, MediaDb mediaDb) {
    DiscTracksImportInfo discTracksImportInfo = new DiscTracksImportInfo();
    List<Object> errors = discTracksImportInfo.getErrors();
    MediaDbAppErrorInfo error;
    
    List<TrackReference> trackReferences = disc.getTrackReferences();
    
    Path sourceFolderPath = Paths.get(sourceFolder);
    List<String> audioFileNamesInFolder = FileUtils.getAudioFileNamesInFolder(sourceFolder);
    
    // Get the file extension and check that all files have the same extension.
    String fileExtension = null;
    for (String audioFileName: audioFileNamesInFolder) {
      String thisFileExtension = FileUtils.getFileExtension(audioFileName).toLowerCase();
      LOGGER.fine("thisFileExtension: " + thisFileExtension);
      if (fileExtension == null) {
        fileExtension = thisFileExtension;
      } else {
        if (!fileExtension.equals(thisFileExtension)) {
          errors.add(new MediaDbAppErrorInfo(MediaDbAppError.TRACKS_WITH_DIFFERENT_EXTENSIONS));
          return  discTracksImportInfo;
        }
      }
    }
        
    if (trackReferences.size() != audioFileNamesInFolder.size()) {
      error = new MediaDbAppErrorInfo(MediaDbAppError.WRONG_NUMBER_OF_TRACKS);
      error.setDetails("Expected number of tracks is " + trackReferences.size() + ", number of tracks in source folder is " + audioFileNamesInFolder.size());
      errors.add(error);
      return  discTracksImportInfo;
    }
    
    String destinationFolderName;
    Album album = disc.getAlbum();
    if (album.isSoundtrack()) {
      destinationFolderName = MediaDbAppUtil.generateSoundtrackAlbumDiscFolderName(disc, errors);
    } else {
      destinationFolderName = MediaDbAppUtil.generateAlbumDiscFolderName(disc, mediaDb, errors);
    }
    LOGGER.fine("albumFolderName: " + destinationFolderName);
    Path destinationFolderPath = Paths.get(destinationFolderName);
    discTracksImportInfo.setDestinationFolderPath(destinationFolderPath);
        
    ObservableList<TrackImportInfo> trackImportInfos = discTracksImportInfo.getTrackImportPreviewInfos();
    for (int i = 0; i < trackReferences.size(); i++) {
      Path sourceFilePath = sourceFolderPath.resolve(audioFileNamesInFolder.get(i));
      LOGGER.fine("sourceFilePath: " + sourceFilePath.toAbsolutePath().toString());
      String trackFileName = MediaDbAppUtil.generateTrackFileNameForStandardAlbum(trackReferences.get(i)) + fileExtension;
      Path destinationPath = Paths.get(destinationFolderName, trackFileName);
      LOGGER.fine("destinationPath: " + destinationPath.toAbsolutePath().toString());

      TrackImportInfo trackImportPreviewInfo = new TrackImportInfo();
      trackImportPreviewInfo.setTrackNr(i + 1);
      trackImportPreviewInfo.setSourceFilePath(sourceFilePath);
      trackImportPreviewInfo.setDestinationFilePath(destinationPath);
      trackImportInfos.add(trackImportPreviewInfo);
    }
    
    return discTracksImportInfo;
  }
}
