package goedegep.media.musicfolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.similarity.FuzzyScore;

import goedegep.media.common.MediaRegistry;
import goedegep.util.datetime.FlexDate;
import goedegep.util.datetime.FlexDateFormat;
import goedegep.util.file.FileUtils;

/**
 * This class contains all information about an album folder.
 */
public class AlbumFolder {
  private static final Logger LOGGER = Logger.getLogger(AlbumFolder.class.getName());
  private static final FlexDateFormat FDF = new FlexDateFormat(true, true);
  
  private static final String ALBUM_FOLDER_NAME_REGEX = "^(\\d{4})(-\\d{2})?(-\\d{2})? - (.*) - (.*)";     // Album year(-month)(-day) - Artist - Album title
  private static final Pattern ALBUM_FOLDER_NAME_PATTERN = Pattern.compile(ALBUM_FOLDER_NAME_REGEX);
  
  private static final String COMPILATION_FOLDER_DATE_MARKER = "9999";
  private static final String COMPILATION_FOLDER_NAME_REGEX = COMPILATION_FOLDER_DATE_MARKER + " - (.*) - (.*)$";
  
  private static final String SOUNDTRACK_ALBUM_FOLDER_NAME_REGEX = "^(\\d{4})(-\\d{2})?(-\\d{2})? - (.*)";  // Album year(-month)(-day) - Album title
  private static final Pattern SOUNDTRACK_ALBUM_FOLDER_NAME_PATTERN = Pattern.compile(SOUNDTRACK_ALBUM_FOLDER_NAME_REGEX);

  
  /**
   * My compilation albums have the issue date set to 9999.
   */
  private static final FlexDate   COMPILATION_MARKER_DATE = new FlexDate(9999);

  /**
   * Check whether a folder name satisfies the naming convention for an Album Folder.
   * @param folderName the name to be checked
   * @return true if <code>folderName</code> satisfies the naming convention for an Album Folder, false otherwise.
   */
  public static boolean isValidAlbumFolderName(String folderName) {
    return folderName.matches(ALBUM_FOLDER_NAME_REGEX);
  }
  
  /**
   * Check whether a folder is an own compilation folder.
   * <p>
   * 
   * @param folder the Path of the folder to be checked.
   * @return true if <code>folder</code> is an own compilation folder.
   */
  public static boolean isCompilationFolder(Path folder) {
    LOGGER.fine("=> folder = " + folder);
    
    boolean returnValue = false;
    String folderName = folder.getFileName().toString();
    if (folderName.matches(COMPILATION_FOLDER_NAME_REGEX)) {
      LOGGER.fine("Is Compilation folder");
      returnValue = true;
    } else {
      LOGGER.fine("Not compilation folder, pattern: " + COMPILATION_FOLDER_NAME_REGEX);
    }
    
    LOGGER.fine("<= " + returnValue);
    
    return returnValue;
  }

  /**
   * Check whether a folder name satisfies the naming convention for an Album Folder.
   * @param folderName the name to be checked
   * @return true if <code>folderName</code> satisfies the naming convention for an Album Folder, false otherwise.
   */
  public static boolean isValidSoundtrackAlbumFolderName(String folderName) {
    boolean returnValue = folderName.matches(SOUNDTRACK_ALBUM_FOLDER_NAME_REGEX);
    LOGGER.info("folderName=" + folderName + ", returnValue=" + returnValue);
    return returnValue;
  }
  
  /**
   * Generates the <code>AlbumOnDiscInfo</code> for one album and adds this to a list.
   * <p>
   * The <code>AlbumOnDiscInfo</code> is filled with the parameter values <code>albumYearOfIssue</code>, <code>albumArtist</code>,
   * <code>albumTitle</code> and <code>albumFolder</code> (using its name).
   * The name of each file in the folder is added as a trackFileName if:
   * <ul>
   * <li>
   * it is an audio track (see {@link #isAudioFile})
   * </li>
   * <li>
   * argument <code>checkNamingConvention</code> is false, or the naming convention is correct.
   * </li>
   * </ul>
   * 
   * @param albumFolder the <code>Path</code> of the folder that contains the album.
   * @param checkNamingConvention if true, only tracks with the correct naming convention are added to the <code>AlbumOnDiscInfo</code>.
   * @param albums a list of <code>AlbumOnDiscInfo</code>, to which the information of this album will be added.
   * @param errors a list to which all encountered problems have to be added
   */
  public static void getAlbumOnDiscInfoForAlbumFolder(Path albumFolder, boolean checkNamingConvention, List<AlbumOnDiscInfo> albums, List<MusicFolderStructureErrorInfo> errors) {
    LOGGER.fine("=> albumFolder=" + albumFolder + ", checkNamingConvention=" + checkNamingConvention);

    String folderName = albumFolder.getFileName().toString();
    Matcher matcher = ALBUM_FOLDER_NAME_PATTERN.matcher(folderName);
    
    if (!matcher.matches()) {
      throw new RuntimeException("Specified albumFolder '" + albumFolder + "' is not a valid Album Folder");
    }
    
    for (int i = 0; i <= matcher.groupCount(); i++) {
      LOGGER.fine("Group: " + i + ", \"" + matcher.group(i) + "\"");
    }
    StringBuilder dateBuf = new StringBuilder();
    String datePart = matcher.group(1);  // year
    if (datePart != null) {
      dateBuf.append(datePart);
    }
    datePart = matcher.group(2);  // -month
    if (datePart != null) {
      dateBuf.append(datePart);
    }
    datePart = matcher.group(3);  // -day
    if (datePart != null) {
      dateBuf.append(datePart);
    }
    FlexDate issueDate = null;
    try {
      issueDate = FDF.parse(dateBuf.toString());
      LOGGER.fine("issueDate(object): " + FDF.format(issueDate));
    } catch (ParseException e) {
      // Should not happen, as the format is determined by the regex.
      e.printStackTrace();
    }
    String artist = matcher.group(4);
    LOGGER.fine("artist:" + artist );
    String title = matcher.group(5);
    LOGGER.fine("title:" + title );

    AlbumOnDiscInfo info = gatherTrackInfoAndCreateAlbumOnDiscInfo(albumFolder, checkNamingConvention, issueDate, artist, title, errors);
    albums.add(info);
    
    LOGGER.fine("<= info.toString()");
  }
  
  /**
   * Generates the <code>AlbumOnDiscInfo</code> for one album and adds this to a list.
   * <p>
   * The <code>AlbumOnDiscInfo</code> is filled with the parameter values <code>albumYearOfIssue</code>, <code>albumArtist</code>,
   * <code>albumTitle</code> and <code>albumFolder</code> (using its name).
   * The name of each file in the folder is added as a trackFileName if:
   * <ul>
   * <li>
   * it is an audio track (see {@link #isAudioFile})
   * </li>
   * <li>
   * <code>checkNamingConvention</code> is false, or the naming convention is correct.
   * </li>
   * </ul>
   * 
   * @param albumFolder the <code>Path</code> of the folder that contains the album.
   * @param checkNamingConvention if true, only tracks with the correct naming convention are added to the <code>AlbumOnDiscInfo</code>.
   * @param issueDate the year the album was issued, one of the 3 elements that identify the album
   * @param albumArtist the artist of the album, one of the 3 elements that identify the album
   * @param albumTitle the title of the album, one of the 3 elements that identify the album
   * @param albums a list of <code>AlbumOnDiscInfo</code>, to which the information of this album will be added.
   * @param errors a list to which all encountered problems have to be added
   */
  public static void getAlbumOnDiscInfoForSoundtrackAlbumFolder(Path albumFolder, boolean checkNamingConvention, List<AlbumOnDiscInfo> albums, List<MusicFolderStructureErrorInfo> errors) {
    LOGGER.fine("=> albumFolder=" + albumFolder + ", checkNamingConvention=" + checkNamingConvention);

    String folderName = albumFolder.getFileName().toString();
    Matcher matcher = SOUNDTRACK_ALBUM_FOLDER_NAME_PATTERN.matcher(folderName);
    
    if (!matcher.matches()) {
      throw new RuntimeException("Specified albumFolder '" + albumFolder + "' is not a valid Sound Track Album Folder");
    }
    
    for (int i = 0; i <= matcher.groupCount(); i++) {
      LOGGER.fine("Group: " + i + ", \"" + matcher.group(i) + "\"");
    }
    StringBuilder dateBuf = new StringBuilder();
    String datePart = matcher.group(1);  // year
    if (datePart != null) {
      dateBuf.append(datePart);
    }
    datePart = matcher.group(2);  // -month
    if (datePart != null) {
      dateBuf.append(datePart);
    }
    datePart = matcher.group(3);  // -day
    if (datePart != null) {
      dateBuf.append(datePart);
    }
    FlexDate issueDate = null;
    try {
      issueDate = FDF.parse(dateBuf.toString());
      LOGGER.fine("issueDate(object): " + FDF.format(issueDate));
    } catch (ParseException e) {
      // Should not happen, as the format is determined by the regex.
      e.printStackTrace();
    }
    String title = matcher.group(4);
    LOGGER.fine("title:" + title );

    AlbumOnDiscInfo info = gatherTrackInfoAndCreateAlbumOnDiscInfo(albumFolder, checkNamingConvention, issueDate, null, title, errors);
    albums.add(info);
    
    LOGGER.fine("<= info.toString()");
  }
  
  /**
   * Gather audio track information for the tracks in an Album Folder and create AlbumOnDiscInfo for this Album Folder.
   * 
   * @param albumFolder The Album Folder for which the information is to be gathered.
   * @param checkNamingConvention if true, only audio files that satisfy the naming conventions are added to the AlbumOnDiscInfo.
   * @param issueDate the issue date of the album.
   * @param artist the artist of the album.
   * @param title the title of the album.
   * @param errors a list to which all encountered problems have to be added.
   * @return the {@code AlbumOnDiscInfo} for the album in the {@code albumFolder}.
   */
  private static AlbumOnDiscInfo gatherTrackInfoAndCreateAlbumOnDiscInfo(Path albumFolder, boolean checkNamingConvention, FlexDate issueDate, String artist, String title, List<MusicFolderStructureErrorInfo> errors) {
    MusicFolderStructureErrorInfo error;
    
    // Create a sorted track list; the file names of the audio files in the albumFolder.
    List<String> trackList = new ArrayList<>();
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(albumFolder)) {
      for (Path path: stream) {

        if (FileUtils.isAudioFile(path)) {
          LOGGER.fine("Is audio track: " + path.getFileName());
          
          if (!FileUtils.hasLowerCaseExtension(path)) {
            error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.AUDIO_FILE_WITH_MIXED_OR_UPPER_CASE_EXTENSION);
            error.setFolder(albumFolder);
            error.setFile(path.getFileName().toString());
            errors.add(error);
          }

          if (!checkNamingConvention  ||
              (!isCompilationFolder(albumFolder) && TrackFile.isNamingConventionTrackNrTitleOk(path))  ||
              (isCompilationFolder(albumFolder) && TrackFile.isNamingConventionSatisfiedForMyCompilationAlbumTrack(path))) {
            LOGGER.fine("audio track OK, adding to trackList: " + path.getParent().toString());
            trackList.add(path.getFileName().toString());
          } else {
            LOGGER.severe("Skipping audio file with wrong naming convention: " + path.toAbsolutePath().toString());
            error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.WRONG_TRACK_NAME_FORMAT);
            error.setFolder(albumFolder);
            error.setFile(path.getFileName().toString());
            errors.add(error);
          }
        } else {
          LOGGER.info("Skipping non-audio file: " + path.getFileName().toString() + ", in folder: " + albumFolder);
        }
      }

      Collections.sort(trackList);
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      // In this snippet, it can only be thrown by newDirectoryStream.
      System.err.println(x);
    }
    return new AlbumOnDiscInfo(issueDate, artist, title, albumFolder.toString(), trackList);
  }
  

  /**
   * Generate the name of an album folder.
   * <p>
   * This method is for single disc albums.<br/>
   * It's a wrapper on top of {@link #generateAlbumDiscFolderName(String, String, String, FlexDate, String, boolean, boolean, List)}, which is called with <code>null</code> as <code>discId</code>.
   * 
   * @param containerArtistName name of the 'container artist' for the artist identified by <code>artistNam</code>
   * @param albumTitle the album title
   * @param artistName the artist name
   * @param releaseDate the release date
   * @param haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc true if you have more than one album of this container artist and released in the same year, on disc.
   * @param isMyCompilationAlbum to be set to true for a MyCompilation album.
   * @param errors an error list, to which errors encountered by this method are added.
   * @return the name of the album folder, or null if the name can't be generated (because of missing information).
   */
  public static String generateAlbumDiscFolderName(String containerArtistName, String albumTitle, String artistName, FlexDate releaseDate,
      boolean haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc, boolean isMyCompilationAlbum, List<Object> errors) {
    return generateAlbumDiscFolderName(containerArtistName, albumTitle, artistName, releaseDate, null, haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc, isMyCompilationAlbum, errors);
  }
  
  /**
   * Generate the name of an album folder.
   * <p>
   * This method is for multi disc albums, where the disc title is not set.</br>
   * Based on the disc number a discId is generated with the format: '[cd ' discNr ']'.</br>
   * This method is a wrapper on top of {@link #generateAlbumDiscFolderName(String, String, String, FlexDate, String, boolean, boolean, List)}, which is called with the generated discId.
   * 
   * @param containerArtistName name of the 'container artist' for the artist identified by <code>artistNam</code>
   * @param albumTitle the album title
   * @param artistName the artist name
   * @param releaseDate the release date
   * @param discNr the disc number
   * @param haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc true if you have more than one album of this container artist and released in the same year, on disc.
   * @param isMyCompilationAlbum to be set to true for a MyCompilation album.
   * @param errors an error list, to which errors encountered by this method are added.
   * @return the name of the album folder, or null if the name can't be generated (because of missing information).
   */
  public static String generateAlbumDiscFolderName(String containerArtistName, String albumTitle, String artistName, FlexDate releaseDate, int discNr,
      boolean haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc, boolean isMyCompilationAlbum, List<Object> errors) {
    return generateAlbumDiscFolderName(containerArtistName, albumTitle, artistName, releaseDate, generateDiscIdForDiscNr(discNr), haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc, isMyCompilationAlbum, errors);
  }
  
  /**
   * Generate the name of an album folder.
   * <p>
   * See {@link MusicFolder} for the naming convention for albums.
   * 
   * @param containerArtistName name of the 'container artist' for the artist identified by <code>artistNam</code>
   * @param albumTitle the album title
   * @param artistName the artist name
   * @param releaseDate the release date
   * @param discId identification of the disc. This value shall be null for the disc of a single disc album.
   * @param haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc true if you have more than one album of this container artist and released in the same year, on disc.
   * @param isMyCompilationAlbum to be set to true for a MyCompilation album.
   * @param errors an error list, to which errors encountered by this method are added.
   * @return the name of the album folder, or null if the name can't be generated (because of missing information).
   */
  public static String generateAlbumDiscFolderName(String containerArtistName, String albumTitle, String artistName, FlexDate releaseDate, String discId,
      boolean haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc, boolean isMyCompilationAlbum, List<Object> errors) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(MediaRegistry.musicDirectory);
    
    buf.append(File.separator);
    
    // if the container artist name is (starts with) a numer, the folder is 0-9, else
    // it is the first character of the container artist name.
    char artistNameStartsWith = containerArtistName.charAt(0);
    if (Character.isDigit(artistNameStartsWith)) {
      buf.append("0-9");
    } else {
      buf.append(Character.toUpperCase(artistNameStartsWith));
    }
    
    buf.append(File.separator);
    
    buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(containerArtistName, false));
    
    buf.append(File.separator);
    
    if (isMyCompilationAlbum) {
      buf.append(COMPILATION_MARKER_DATE);
    } else {
      buf.append(releaseDate.getYear());

      if (haveMoreThanOneAlbumOfContainerArtistInThisYearOnDisc) {
        Integer releaseMonth = releaseDate.getMonth();
        if (releaseMonth != null) {
          releaseMonth++;
          buf.append("-");
          buf.append(String.format("%02d", releaseMonth));
        } else {
          LOGGER.info("Needed release month not specified, album is: " + releaseDate + " - " + artistName + " - " + albumTitle);
        }
      }
    }
    buf.append(" - ");
    buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(artistName, true));
    buf.append(" - ");
    buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(albumTitle, discId != null));
    
    if (discId != null) {
      buf.append(" - ").append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(discId, false));
    }
    
    LOGGER.fine("album folder name: " + buf.toString());
        
    return buf.toString();
  }
  
  /**
   * Generate the name of a soundtrack album disc folder.
   * <p>
   * This method is for single disc albums.<br/>
   * It's a wrapper on top of {@link #generateSoundtrackAlbumDiscFolderName(String, String, FlexDate, String, boolean, List)}, which is called with <code>null</code> as <code>discId</code>.
   * 
   * @param albumTitle the album title
   * @param artistName the artist name
   * @param releaseDate the release date
   * @param isMyCompilationAlbum to be set to true for a MyCompilation album.
   * @param errors an error list, to which errors encountered by this method are added.
   * @return the name of the soundtrack album folder
   */
  public static String generateSoundtrackAlbumDiscFolderName(String albumTitle, String artistName, FlexDate releaseDate, boolean isMyCompilationAlbum, List<Object> errors) {
    return generateSoundtrackAlbumDiscFolderName(albumTitle, artistName, releaseDate, null, isMyCompilationAlbum, errors);
  }
  
  /**
   * Generate the name of a soundtrack album disc folder.
   * <p>
   * This method is for multi disc albums, where the disc title is not set.</br>
   * Based on the disc number a discId is generated with the format: '[cd ' discNr ']'.</br>
   * This method is a wrapper on top of {@link #generateSoundtrackAlbumDiscFolderName(String, String, FlexDate, String, boolean, List)}, which is called with the generated discId.
   * 
   * @param albumTitle the album title
   * @param artistName the artist name
   * @param releaseDate the release date
   * @param discNr the disc number
   * @param isMyCompilationAlbum to be set to true for a MyCompilation album.
   * @param errors an error list, to which errors encountered by this method are added.
   * @return the name of the soundtrack album folder
   */
  public static String generateSoundtrackAlbumDiscFolderName(String albumTitle, String artistName, FlexDate releaseDate, int discNr, boolean isMyCompilationAlbum, List<Object> errors) {
    return generateSoundtrackAlbumDiscFolderName(albumTitle, artistName, releaseDate, generateDiscIdForDiscNr(discNr), isMyCompilationAlbum, errors);
  }
  
  /**
   * Generate the name of a soundtrack album disc folder.
   * <p>
   * The format of the name is: &lt;MediaRegistry.musicDirectory&gt;\zSoundtracks\&lt;date&gt; - &lt;artist&gt; - &lt;title&gt; - [cd &lt;discNr&gt;]<br/>
   * 
   * Where:
   * <ul>
   *   <li>
   *   &lt;date&gt; is:
   *   </li>
   *   <ul>
   *     <li>
   *     For My Compilation albums<br/>
   *     The {@link #COMPILATION_MARKER_DATE}.
   *     </li>
   *     <li>
   *     For other albums<br/>
   *     The release year and, if the release month is set,  a '-' and the release month.
   *     </li>
   *   </ul>
   *   <li>
   *   &lt;artist&gt; is the artist name, where any occurrence of ' - ' (which is used as separator in my convention) is replaced by ', '.
   *   </li>
   *   <li>
   *   &lt;title&gt; is the album title, where any occurrence of ' - ' (which is used as separator in my convention) is replaced by ', '.
   *   For example "Das Boot - Compilation" is changed to "Das Boot, Compilation".
   *   </li>
   *   <li>
   *   &lt;discNr&gt; is the number of the disc, starting at number 1.
   *   </li>
   * </ul>
   * An error is generated if the release date isn't set.
   * 
   * @param albumTitle the album title
   * @param artistName the artist name
   * @param releaseDate the release date
   * @param discId identification of the disc. This value shall be null for the disc of a single disc album.
   * @param isMyCompilationAlbum to be set to true for a MyCompilation album.
   * @param errors an error list, to which errors encountered by this method are added.
   * @return the name of the soundtrack album folder
   */
  public static String generateSoundtrackAlbumDiscFolderName(String albumTitle, String artistName, FlexDate releaseDate, String discId, boolean isMyCompilationAlbum, List<Object> errors) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(MediaRegistry.musicDirectory);
    
    buf.append(File.separator);
    
    buf.append(MusicFolderDescription.getSoundtracksFolder());
    
    buf.append(File.separator);
    
    if (isMyCompilationAlbum) {
      buf.append(COMPILATION_MARKER_DATE);
    } else {
      buf.append(releaseDate.getYear());
      if (releaseDate.isMonthSet()) {
        buf.append("-");
        buf.append(releaseDate.getMonth() + 1);
      }
    }
    if (artistName != null) {
      buf.append(" - ");
      buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(artistName, true));
    }
    buf.append(" - ");
    buf.append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(albumTitle, discId != null));
    
    if (discId != null) {
      buf.append(" - ").append(MusicFolderUtil.replaceGroupSeparatorAndNameToFileName(discId, false));
    }
    
    return buf.toString();
  }
  
  public static String findBestMatchingTrackFileName(Path folderPath, String trackFileName) {
    LOGGER.severe("=> folderPath=" + folderPath + ", trackFileName=" + trackFileName);
    if (trackFileName == null) {
      return null;
    }
    FuzzyScore fuzzyScore = new FuzzyScore(Locale.getDefault());
    
    String bestTrackFileName = null;
    int bestScore = 0;
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
      for (Path path: stream) {
        if (Files.isRegularFile(path)) {
          String fileName = path.getFileName().toString();
          int score = fuzzyScore.fuzzyScore(trackFileName, fileName);
          LOGGER.severe("score=" + score + " for " + fileName);
          if (score > bestScore) {
            bestScore = score;
            bestTrackFileName = fileName;
          }
        }
      }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }  catch (DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    return bestTrackFileName;
  }
  
  public static String generateDiscIdForDiscNr(int discNr) {
    return "[cd " + discNr + "]";
  }
}
