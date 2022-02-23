package goedegep.media.musicfolder;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Track;
import goedegep.util.datetime.FlexDate;
import goedegep.util.file.FileUtils;

/**
 * This class contains all information about an audio track (as a file) on disc.
 */
public class TrackFile {
  private static final Logger LOGGER = Logger.getLogger(TrackFile.class.getName());
  
  /**
   * Regex for the naming convention for standard album tracks (in a standard album folder): 2 digit track number - track title
   */
  private static final String TRACKNR_TRACKTITLE_REGEX = "^([0-9][0-9]) - (.*)";
  
  /**
   * Pattern for the {@link #TRACKNR_TRACKTITLE_REGEX}.
   */
  private static final Pattern TRACKNR_TRACKTITLE_PATTERN = Pattern.compile(TRACKNR_TRACKTITLE_REGEX);
  
  /**
   * Regex for a 'reference' track of a MyCompilation album: Album year(-month)(-day) - Album Title - TrackNr - Track title
   * <p>
   * This format applies to references to a single disc album.
   */
  private static final String DATE_ALBUMTITLE_TRACKNR_TRACKTITLE_REGEX = "^(\\d{4})(-\\d{2})?(-\\d{2})? - (.*) - (\\d{2}) - (.*)";
  
  /**
   * Pattern for the {@link #DATE_ALBUMTITLE_TRACKNR_TRACKTITLE_REGEX}.
   */
  @SuppressWarnings("unused")
  private static final Pattern DATE_ALBUMTITLE_TRACKNR_TRACKTITLE_PATTERN = Pattern.compile(DATE_ALBUMTITLE_TRACKNR_TRACKTITLE_REGEX);
  
  /**
   * Regex for a 'reference' track of a MyCompilation album: Album year(-month)(-day) - Album Title - DiscNr-TrackNr - Track title
   * <p>
   * This format applies to references to a multi disc album.
   */
  private static final String DATE_ALBUMTITLE_DISCNR_TRACKNR_TRACKTITLE_REGEX = "^(\\d{4})(-\\d{2})?(-\\d{2})? - (.*) - (\\d-\\d{2}) - (.*)";
  
  /**
   * Regex for a 'date - track title' track of a MyCompilation album: Album year(-month)(-day) - Track title
   * <p>
   * This format is used for tracks in a MyCompilation album, for which there is no 'reference', while other tracks of this MyCompilation album do have 'references'.
   */
  private static final String DATE_TITLE_REGEX = "^(\\d{4})(-\\d{2})?(-\\d{2})? - (.*)";
  
  /**
   * Regexes for MyCompilation albums
   */
  private static final String[] MYCOMPILATION_TRACK_NAME_REGEXES = {
      DATE_ALBUMTITLE_TRACKNR_TRACKTITLE_REGEX, DATE_ALBUMTITLE_DISCNR_TRACKNR_TRACKTITLE_REGEX, TRACKNR_TRACKTITLE_REGEX, DATE_TITLE_REGEX
  };
  
  /**
   * Regex for an 'artist - track title' track in a Tracks Folder: Artist - Title
   */
  private static final String ARTIST_TITLE_REGEX = "^(.*) - (.*)$";
  
  /**
   * Regexes for tracks in a Tracks Folder.
   */
  private static final String[] TRACK_FOLDER_TRACK_NAME_REGEXES = {
      ARTIST_TITLE_REGEX
  };
  
  /**
   * Check whether a file satisfies the album track naming convention.
   * <p>
   * The naming convention for these files is defined by the regex {@link #TRACKNR_TRACKTITLE_REGEX}.
   * 
   * @param path Path for the file to be checked.
   * @return true if <code>path</code> satisfies the album track naming convention, false otherwise.
   */
  public static boolean isNamingConventionTrackNrTitleOk(Path path) {
    LOGGER.fine("=> file = " + path);
    
    boolean returnValue = false;
    String fileName = path.getFileName().toString();
    fileName = FileUtils.getFileNameWithoutExtension(fileName);
    if (fileName.matches(TRACKNR_TRACKTITLE_REGEX)) {
      returnValue = true;
    }
    
    LOGGER.fine("<= " + returnValue);
    
    return returnValue;
  }
  
  /**
   * Derive the track title from an audio track filename.
   * <p>
   * The title can only be derived if the audio track filename satisfies the album track naming convention (defined by the regex {@link #TRACKNR_TRACKTITLE_REGEX}).
   * 
   * @param audiTrackFileName the filename, without extension, to derive the track title from.
   * @return the track title, or null if the <code>audiTrackFileName</code> doesn't satisfy the album track naming convention.
   */
  public static String getTrackTitle(String audiTrackFileName) {
    Matcher matcher = TRACKNR_TRACKTITLE_PATTERN.matcher(audiTrackFileName);
    if (matcher.matches()) {
      return matcher.group(2);
    } else {
      return null;
    }
  }
  
  /**
   * Check whether a file satisfies the MyCompilation album track naming convention.
   * <p>
   * The naming convention for these files is defined by the {@link #MYCOMPILATION_TRACK_NAME_REGEXES}.
   * 
   * @param path Path for the file to be checked.
   * @return true if <code>path</code> satisfies the MyCompilation album track naming convention, false otherwise.
   */
  public static boolean isNamingConventionSatisfiedForMyCompilationAlbumTrack(Path path) {
    LOGGER.info("=> file = " + path);
    
    boolean isOk = false;
    String fileName = path.getFileName().toString();
    fileName = FileUtils.getFileNameWithoutExtension(fileName);
    
    for (String regex: MYCOMPILATION_TRACK_NAME_REGEXES) {
      if (fileName.matches(regex)) {
        isOk = true;
        LOGGER.info("Match on: " + regex);
        break;
      } else {
        LOGGER.info("No match on: " + regex);
      }
    }
    
    if (!isOk) {
      LOGGER.severe("Path doesn't satisfy naming convention: " + path.toAbsolutePath().toString());
    }
    
    LOGGER.info("<= " + isOk);
    
    return isOk;
  }
  
  /**
   * Check whether a file satisfies the naming convention for a track in a Track Folder.
   * <p>
   * The naming convention for these files is defined by the {@link #TRACK_FOLDER_TRACK_NAME_REGEXES}.
   * 
   * @param path Path for the file to be checked.
   * @return true if <code>path</code> satisfies the naming convention for a track in a Track Folder.
   */
  public static boolean isNamingConventionSatisfiedForTrackFolderTrack(Path path) {
    LOGGER.info("=> file = " + path);
    
    boolean isOk = false;
    String fileName = path.getFileName().toString();
    fileName = FileUtils.getFileNameWithoutExtension(fileName);
    
    for (String regex: TRACK_FOLDER_TRACK_NAME_REGEXES) {
      if (fileName.matches(regex)) {
        isOk = true;
        LOGGER.info("Match on: " + regex);
        break;
      } else {
        LOGGER.info("No match on: " + regex);
      }
    }
    
    if (!isOk) {
      LOGGER.severe("Path doesn't satisfy naming convention: " + path.toAbsolutePath().toString());
    }
    
    LOGGER.info("<= " + isOk);
    
    return isOk;
  }
  
  /**
   * Generate the filename for a track of a standard album in the MusicFolder.
   * <p>
   * The filename has the format as specified by the {@link #TRACKNR_TRACKTITLE_REGEX}.
   * 
   * @param trackNr the index of the track on the album.
   * @return trackTitle the title of the track.
   */
  public static String generateTrackFileNameForStandardAlbum(int trackNr, String trackTitle) {
    StringBuilder buf = new StringBuilder();

    // two digit track index
    buf.append(String.format("%02d", trackNr));

    buf.append(" - ");

    // track title
    buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(trackTitle, true));

    return buf.toString();
  }

  /**
   * Generate the file name for a 'reference' audio track in a MyCompilation Album Folder.
   * <p>
   * The filename has the format as specified by the {@link #DATE_ALBUMTITLE_TRACKNR_TRACKTITLE_PATTERN} (for a reference to a single disc album track),
   * or the {@link #DATE_ALBUMTITLE_DISCNR_TRACKNR_TRACKTITLE_PATTERN} (for a reference to a multi disc album track).
   * 
   * @param album The Album that contains the track.
   * @param discNr The Disc number (starting at 1) of the disc on which the track is to be found.
   * @param trackNr The Track number (starting at 1) on the Disc with <code>discNr</code> on the Album <code>album</code>.
   * @return
   */
  public static String generateTrackFileNameForReferenceTrackOfMyCompilationAlbum(Album album, Integer discNr, Integer trackNr) {
    LOGGER.fine("=> album=" + album.getTitle() + ", discNr=" + discNr + ", trackNr=" + trackNr);
    
    StringBuilder buf = new StringBuilder();
    if (album.isSetReleaseDate()) {
      buf.append(album.getReleaseDate().getYear());
    } else {
      buf.append("<no year>");
    }
    
    buf.append(" - ");
    
    buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(album.getTitle(), true));
    buf.append(" - ");
    if (album.isMultiDiscAlbum()) {
      buf.append(discNr);
      buf.append("-");
    }
    buf.append(String.format("%02d", trackNr));
    buf.append(" - ");

    LOGGER.fine("Number of discs=" + album.getDiscs().size());
    Track track = album.getTrackReference(discNr, trackNr).getTrack();

    buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(track.getTitle(), true));
    String trackFileName = buf.toString();

    LOGGER.fine("<= " + trackFileName);
    return trackFileName;
  }

  /**
   * Generate a basic file name for a track in a Tracks Folder. The format is: <artist> - <title>
   * 
   * @param artistName the artist name.
   * @param trackTitle the track title.
   * @return the generated file name.
   */
  public static String generateTrackFileName(String artistName, String trackTitle) {
    LOGGER.fine("=> artistName=" + artistName + ", trackTitle=" + trackTitle);
    StringBuilder buf = new StringBuilder();
    
    if (artistName != null) {
      buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(artistName, true));
    } else {
      buf.append("<no artist>");
    }
    buf.append(" - ");
    if (trackTitle != null) {
      buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(trackTitle, true));
    } else {
      buf.append("<no trackTitle>");
    }

    String trackFileName = buf.toString();
    
    LOGGER.fine("<= " + trackFileName);
    return trackFileName;
  }
  
  /**
   * Generate a file name, including info, for a track in a Tracks Folder. The format is: <artist> - <issue-date> - <album-title> - <track-nr> - <track-title>
   * 
   * @param artistName the artist name.
   * @param issueDate the release date of the album on which the track appeared.
   * @param albumTitle the title of the album on which the track appeared.
   * @param trackNr the track number (starting at 1) of the track on the album on which it appeared.
   * @param trackTitle the track titl.
   * @return the generated file name.
   */
  public static String generateTrackFileNameIncludingAlbumInfoForTracksFolder(String artistName, FlexDate issueDate, String albumTitle, int trackNr, String trackTitle) {
    LOGGER.info("=> artistName=" + artistName + ", albumTitle=" + albumTitle + ", trackNr=" + trackNr + ", trackTitle=" + trackTitle);
    StringBuilder buf = new StringBuilder();
    
    if (artistName != null) {
      buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(artistName, true));
    } else {
      buf.append("<no artist>");
    }
    
    buf.append(" - ");
    
    if (issueDate != null) {
      buf.append(issueDate.getYear());
    } else {
      buf.append("<no issueDate>");
    }
    
    buf.append(" - ");
    
    if (albumTitle != null) {
      buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(albumTitle, true));
    } else {
      buf.append("<no albumTitle>");
    }
    
    buf.append(" - ");
    
    buf.append(String.format("%02d", trackNr));
    
    buf.append(" - ");
    
    if (trackTitle != null) {
      buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(trackTitle, true));
    } else {
      buf.append("<no trackTitle>");
    }

    String trackFileName = buf.toString();
    
    LOGGER.info("<= " + trackFileName);
    return trackFileName;
  }
  
  /**
   * Get a ( audio track) file from a folder, identified by its index.
   * 
   * @param folderPath the folder from which to get the file
   * @param index the index of the audio track (starting at 1).
   * @return the file name of a file in <code>folderPath</code> starting with the 2 digit <code>index</code>.
   */
  public static String getTrackFileNameForTrackIndex(Path folderPath, int index) {
    LOGGER.info("=> folderPath=" + folderPath + ", index=" + index);
    StringBuilder buf = new StringBuilder();
    buf.append(String.format("%02d", index));
    String indexString = buf.toString();
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
      for (Path path: stream) {
        if (Files.isRegularFile(path)  &&  FileUtils.isAudioFile(path)) {
          String fileName = path.getFileName().toString();
          if (fileName.startsWith(indexString)) {
            LOGGER.info("Track file found: " + fileName);
            return fileName;
          }
        }
      }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }  catch (DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    return null;
  }
}
