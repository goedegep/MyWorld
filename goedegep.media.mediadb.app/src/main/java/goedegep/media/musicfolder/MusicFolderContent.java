package goedegep.media.musicfolder;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.util.file.FileUtils;


/**
 * This class gathers the content of the MusicFolder and provides it in a way applications can use it (without disc access).
 */
public class MusicFolderContent {
  private static final Logger LOGGER = Logger.getLogger(MusicFolderContent.class.getName());
  private static final String PATH_SEPARATOR = "/";

  private static List<String> MAIN_INDEX_FOLDERS = MusicFolderDescription.getMainIndexFolders();
  private static String SOUNDTRACK_FOLDER = MusicFolderDescription.getSoundtracksFolder();
  private static List<String> TRACK_FOLDERS = MusicFolderDescription.getTrackFolders();

  /**
   * Name of the MusicFolder directory.
   */
  private String musicFolderName;
  
  
  /**
   * List of {@link AlbumOnDiscInfo} to be filled with information on the albums in the Main Index folders and the soundtrack folder.
   */
  private List<AlbumOnDiscInfo> albumsOnDiscInfo = new ArrayList<>();
  
  /**
   * List of {@link TrackOnDiscInfo} to be filled with the information of the tracks in the track folders.
   */
  private List<TrackOnDiscInfo> tracksOnDiscInfo = new ArrayList<>();
  
  /**
   * A list of errors of type {@link MusicFolderStructureErrorInfo} which are encountered while gathering the MusicFolder content.
   */
  private List<MusicFolderStructureErrorInfo> errors = new ArrayList<>();

  /**
   * Constructor
   * 
   * @param musicFolderName name of the Music Folder.
   */
  public MusicFolderContent(String musicFolderName) {
    this.musicFolderName = musicFolderName;
    
  }
 
  /**
   * Gather the Music Folder content.
   * <p>
   * This method scans the files and folders of the Music Folder; the Main Index folders, the soundtrack folder and the tracks folders.
   * The album information (from the Main Index folders and the soundtrack folder) is stored in a list of AlbumOnDiscInfo.
   * The track information (from the track folders) is stored in a list of TrackOnDiscInfo.
   * 
   * @return if there were no errors found (errors is empty).
   */
  public boolean gatherMusicFolderContent() {
    albumsOnDiscInfo.clear();
    tracksOnDiscInfo.clear();
    errors.clear();
    
    gatherMainIndexAlbumOnDiscInfo();
    gatherSoundtrackAlbumsOnDiscInfo();
    gatherTracksOnDiscInfo();
    
    return errors.isEmpty();
  }

  /**
   * Get the errors that occurred while gathering the Music Folder content.
   * 
   * @return the errors that occurred while gathering the Music Folder content
   */
  public List<MusicFolderStructureErrorInfo> getErrors() {
    return errors;
  }

  /**
   * Get the gathered information for albums on disc.
   * 
   * @return the gathered information for albums on disc
   */
  public List<AlbumOnDiscInfo> getAlbumsOnDiscInfo() {
    return albumsOnDiscInfo;
  }

  /**
   * Get the gathered information for tracks on disc.
   * 
   * @return the gathered information for tracks on disc.
   */
  public List<TrackOnDiscInfo> getTracksOnDiscInfo() {
    return tracksOnDiscInfo;
  }

  /**
   * Gather all information on albums and their tracks in the Main Index folders under the Music folder.
   */
  private void gatherMainIndexAlbumOnDiscInfo() {
        
    for (String mainIndexFolderName: MAIN_INDEX_FOLDERS) {
      Path path = Paths.get(musicFolderName, mainIndexFolderName);
      if (Files.exists(path)) {
        gatherAlbumOnDiscInfoForMainIndexFolder(path);
      }
    }
            
    LOGGER.fine("<= ");
  }
  
  /**
   * Gather all information on albums and their tracks in one specific Main Index Folder (under the Music folder).
   * There's one sub folder per artist name.
   */
  private void gatherAlbumOnDiscInfoForMainIndexFolder(Path artistIndexFolder) {
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(artistIndexFolder)) {
      // Each path is a folder with the name of the artist.
      for (Path path: stream) {

        if (Files.isDirectory(path)) {
          ArtistFolder.getAlbumOnDiscInfoForArtistFolder(path, albumsOnDiscInfo, errors);
          
        } else {
          LOGGER.severe("Skipping suspected file: " + path.getFileName().toString());
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      System.err.println(x);
    }
    
    LOGGER.fine("<= ");
  }
  
  /**
   * Gather all information on albums and their tracks in the Soundtracks Folder under the Music Folder.
   */
  private void gatherSoundtrackAlbumsOnDiscInfo() {    
    LOGGER.fine("=> musicFolderName = " + musicFolderName);
    
    Path soundTracksPath = Paths.get(musicFolderName, SOUNDTRACK_FOLDER);
    LOGGER.fine("=> soundTracksPath = " + soundTracksPath.toAbsolutePath().toString());
    
    MusicFolderStructureErrorInfo error;
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(soundTracksPath)) {
      // Each path shall be a folder for a Sound track album.
      for (Path path: stream) {
        String fileName = path.getFileName().toString();

        LOGGER.fine("Handling path: " + fileName);
        if (Files.isDirectory(path)) {
          LOGGER.fine("Is folder: " + fileName);

          if (AlbumFolder.isValidSoundtrackAlbumFolderName(path.getFileName().toString())) {
            AlbumFolder.getAlbumOnDiscInfoForSoundtrackAlbumFolder(path, true, albumsOnDiscInfo, errors);
          } else {
            LOGGER.severe("Wrong name for soundtrack album folder: " + fileName);
            error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.WRONG_FOLDER_NAME);
            error.setFolder(path);
            errors.add(error);
          }
        } else {
          LOGGER.severe("Skipping suspected file: " + fileName);
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      // In this snippet, it can only be thrown by newDirectoryStream.
      System.err.println(x);
    }
    
    LOGGER.fine("<= ");
  }

  /**
   * Gather all information on separate tracks in the Track Folders under the Music folder.
   */
  private void gatherTracksOnDiscInfo() {

    LOGGER.fine("=> musicFolderName = " + musicFolderName);
    
    for (String mainIndexFolderName: TRACK_FOLDERS) {
      Path path = Paths.get(musicFolderName, mainIndexFolderName);
      LOGGER.info("Handling path: " + path.toString());
      gatherTracksOnDiscInfoForTrackFolder(path, true);
    }
        
    LOGGER.fine("<= ");
  }

  /**
   * Generates the <code>TrackOnDiscInfo</code> for all tracks in one track folder and adds this to a list.
   * <p>
   * The <code>TrackOnDiscInfo</code> is filled with the Path for the track.
   * Any file in the folder is added as TrackOnDiscInfo if:
   * <ul>
   * <li>
   * it is an audio track (see {@link #isAudioFile}) AND
   * </li>
   * <li>
   * <code>checkNamingConvention</code> is false, or the naming convention is correct.
   * </li>
   * </ul>
   * 
   * @param trackFolder the <code>Path</code> of the folder that contains tracks.
   * @param checkNamingConvention if true, only tracks with the correct naming convention are added to the <code>TrackOnDiscInfo</code>.
   */
  private void gatherTracksOnDiscInfoForTrackFolder(Path trackFolder, boolean checkNamingConvention) {
    LOGGER.fine("=> trackFolder=" + trackFolder + ", checkNamingConvention=" + checkNamingConvention);

    MusicFolderStructureErrorInfo error;

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(trackFolder)) {
      for (Path file: stream) {

        LOGGER.fine("Handling file: " + file.getFileName().toString());
        if (FileUtils.isAudioFile(file)) {
          LOGGER.fine("Is audio track: " + file.getFileName());

          if (!checkNamingConvention  ||
              TrackFile.isNamingConventionSatisfiedForTrackFolderTrack(file)) {
            LOGGER.fine("audio track OK, adding to list: " + file.toString());
            
            String trackFileName = file.toString();
            if (trackFileName.toLowerCase().contains("sun is shining")) {
              LOGGER.severe("Found" + trackFileName);
            }
            TrackOnDiscInfo trackOnDiscInfo = new TrackOnDiscInfo(file);
            tracksOnDiscInfo.add(trackOnDiscInfo);
          } else {
            LOGGER.severe("Skipping file with wrong naming convention: " + FileUtils.getFullPathName(file, PATH_SEPARATOR));
            error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.WRONG_TRACK_NAME_FORMAT);
            error.setFolder(trackFolder);
            error.setFile(file.getFileName().toString());
            errors.add(error);
          }
        } else {
          LOGGER.severe("Skipping non-audio file: " + file.getFileName().toString());
        }
      }

    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      // In this snippet, it can only be thrown by newDirectoryStream.
      System.err.println(x);
    }
        
    LOGGER.fine("<=");
  }
}
