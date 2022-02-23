package goedegep.media.musicfolder;

import java.io.File;
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
import javaFlacEncoder.FLAC_FileEncoder;
import javaFlacEncoder.FLAC_FileEncoder.Status;

/**
 * This class contains the top level information about the music folder.
 * 
 * <pre>
 * Music structure on disc (overview):
 *  0-9, ..., Z                                       main index
 *    &lt;artist-name&gt;                                     All artists starting with a specific character
 *      &lt;year-plus-optional-index&gt; - &lt;artist-name&gt; - &lt;album-title&gt; - &lt;cd-nr&gt;                      
 *        &lt;2-digit-tracknr&gt; - &lt;track-name&gt;.&lt;extension&gt;
 *      9999 - Compilation  
 *        &lt;year&gt; - &lt;Album title&gt; - &lt;2-digit-tracknr&gt; - &lt;track-name&gt;.&lt;extension&gt;
 * zSoundtracks
 * </pre>
 * 
 * Details:
 * <ul>
 * <li>
 * &lt;artist-name&gt;<br/>
 * The name of the artist.<br/>
 * This name is also part of an album folder name, because it may differ from the artist folder name.<br/>
 * As an example:<br/>
 *   Artist: Pete Yorn<br/>
 *   Artist name for one album: Pete Yorn &amp; Scarlett Johansson<br/>
 * </li>
 * <li>
 * &lt;year-plus-optional-index&gt;<br/>
 * Four digits for the year the album is issued. For 'My compilations' this value is set to 9999.
 * An optional index: '-x'. This index is just used to sort the entries in a directory correctly in case an artist issued
 * more than one album in a specific year.
 * </li>
 * <li>
 * &lt;album-title&gt;<br/>
 * The full title of the album.
 * </li>
 * <li>
 * &lt;cd-nr&gt;<br/>
 * An optional disc number, used for multi disc albums. E.g. [cd 1]
 * </li>
 * <li>
 * My compilations are recognized by fixed name (9999 - Compilation)
 * </li>
 * </ul>
 */
public class MusicFolder {
  private static final Logger LOGGER = Logger.getLogger(MusicFolder.class.getName());
  public static final String PATH_SEPARATOR = "/";
    
    
  public static List<String> MAIN_INDEX_FOLDER_NAMES = MusicFolderDescription.getMainIndexFolders();
  public static List<String> TRACK_FOLDER_NAMES = MusicFolderDescription.getTrackFolders();
  public static String SOUNDTRACK_FOLDER_NAME = MusicFolderDescription.getSoundtracksFolder();
  public static List<String> SPECIAL_MUSIC_FOLDER_NAMES = MusicFolderDescription.getSpecialMusicFolders();
  public static List<String> NAMES_OF_FOLDERS_TO_BE_IGNORED = MusicFolderDescription.getFoldersToBeIgnored();
  public static List<String> NAMES_OF_FILES_TO_BE_IGNORED = MusicFolderDescription.getFilesToBeIgnored();

  
  /**
   * Constructor.
   */
  public MusicFolder() {
  }
  
  /**
   * Verify the structure of the Music Folder.
   * <p>
   * See {@link http://mydigitallife.rf.gd/solutions/configuration-of-devices/musicfolder/} for a specification of the Music Folder.<br/>
   * The following issues are reported:
   * <ul>
   * <li>
   * No main index folder seen<br/>
   * In principle all index folders are optional, but if none of them exists, probably a wrong musicFolder is specified.
   * </li>
   * <li>
   * Folders that shouldn't be there<br/>
   * Certain folders which aren't part of the Music Folder structure, but are allowed (like e.g. the '.SynologyWorkingDirectory') are ignored.
   * For other folders, which shouldn't be there, a warning is generated.
   * </li>
   * <li>
   * Files that shouldn't be there<br/>
   * Files shall only exist in Music Files Folders. Any other files are reported.
   * </li>
   * <li>
   * Non audio files<br/>
   * Only audio files are expected (and only in Music Files Folders). Any non-audio file is reported.
   * </li>
   * <li>
   * Folders with wrong names
   * </li>
   * <li>
   * Audio files with wrong names
   * </li>
   * </ul>
   * 
   * @param musicFolder Location of the Music Folder.
   * @param repair If true problems that can be fixed are fixed.
   * @return a list of <code>MusicFolderStructureErrorInfo</code>.
   */
  public List<MusicFolderStructureErrorInfo> verifyMusicFolderStructure(String musicFolder, boolean repair) {
    LOGGER.info("=> musicFolder = " + musicFolder + ", repair = " + repair);
    
    List<MusicFolderStructureErrorInfo> errors = new ArrayList<>();
    Path folderPath = Paths.get(musicFolder);    
    MusicFolderStructureErrorInfo error;
    boolean indexFolderSeen = false;

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
      for (Path file: stream) {
        LOGGER.info("Handling path: " + file.getFileName().toString());
        if (Files.isDirectory(file)) {
          String directoryName = file.getFileName().toString();
          if (MAIN_INDEX_FOLDER_NAMES.contains(directoryName)) {
            LOGGER.info("Is main index folder: " + directoryName);
            indexFolderSeen = true;
            verifyMainIndexFolderStructure(file, errors, repair);
          } else if (isSoundtrackFolder(directoryName)) {
            LOGGER.info("Is soundtrack folder: " + directoryName);
            verifySoundtrackFolderStructure(file, errors, repair);
          } else if (isTrackFolder(directoryName)) {
            LOGGER.info("Is track folder: " + directoryName);
            verifyMusicFilesFolder(file, errors, repair);
          } else if (isSpecialMusicFolder(directoryName)) {
            LOGGER.info("Is Special Music folder: " + directoryName);
            // Not to be checked, so no action.
          } else if (isFolderToBeIgnored(directoryName)) {
            LOGGER.info("Is known folder: " + directoryName);
            // Not to be checked, so no action.
          } else {
            LOGGER.severe("Is suspected folder: " + file.getFileName().toString());
            error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.UNEXPECTED_FOLDER);
            error.setFolder(folderPath);
            error.setFile(directoryName);
            errors.add(error);
          }
        } else {
          String fileName = file.getFileName().toString();
          if (!isFileToBeIgnored(fileName)) {
            error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.UNEXPECTED_FILE);
            error.setFolder(folderPath);
            error.setFile(file.getFileName().toString());
            errors.add(error);
          }
        }
      }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    } catch (DirectoryIteratorException x) {
      System.err.println(x);
    }

    if (!indexFolderSeen) {
      error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.NO_INDEX_FOLDER_FOUND);
      error.setFolder(folderPath);
      errors.add(error);
    }
        
    LOGGER.fine("<= ");
    return errors; 
  }
  
  /**
   * This method verifies a Main Index Folder.
   * <p>
   * A Main Index Folder doesn't contain files. It contains a sub folder for each artist starting with the character of this folder.
   * 
   * @param aMainIndexFolderPath a path to a Main Index folder.
   * @param errors a list to which any errors will be added.
   * @param repair if true, errors will be repaired (if possible).
   */
  private void verifyMainIndexFolderStructure(Path aMainIndexFolderPath, List<MusicFolderStructureErrorInfo> errors, boolean repair) {
    LOGGER.info("aMainIndexFolderPath=" + aMainIndexFolderPath);
    
    String indexCharacter = aMainIndexFolderPath.getFileName().toString();
    MusicFolderStructureErrorInfo error;
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(aMainIndexFolderPath)) {
      for (Path file: stream) {
        LOGGER.info("Handling Artist folder: " + file.toAbsolutePath().toString());
        if (Files.isDirectory(file)) {
          String directoryName = file.getFileName().toString();
          if (!isValidArtistFolderForIndexFolder(indexCharacter, directoryName)) {
            LOGGER.severe("Wrong artist folder: " + file.getFileName().toString());
            error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.WRONG_FOLDER_NAME);
            error.setFolder(file);
            errors.add(error);
          }
          verifyArtistFolderStructure(file, errors, repair);
        } else {
          error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.UNEXPECTED_FILE);
          error.setFile(file.getFileName().toString());
          errors.add(error);
        }
      }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    } catch (DirectoryIteratorException x) {
      System.err.println(x);
    }
    
  }
  
  /**
   * This method verifies an Artist Folder.
   * <p>
   * These folders only contain Album Folders.<br/>
   * Because Container Artist names are used, we can't check the artist name. So only a check is done on a valid album folder name format.
   * 
   * @param artistFolderPath the Artist Folder to verify.
   * @param errors An error list to which errors will be added.
   * @param repair if true, errors will be repaired (if possible).
   */
  private void verifyArtistFolderStructure(Path artistFolderPath, List<MusicFolderStructureErrorInfo> errors, boolean repair) {
    
    MusicFolderStructureErrorInfo error;
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(artistFolderPath)) {
      for (Path file: stream) {
        LOGGER.info("Handling Album folder: " + file.toAbsolutePath().toString());
        if (Files.isDirectory(file)) {
          String directoryName = file.getFileName().toString();
          if (!AlbumFolder.isValidAlbumFolderName(directoryName)) {
            LOGGER.severe("Wrong artist folder: " + file.getFileName().toString());
            error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.WRONG_FOLDER_NAME);
            error.setFolder(file);
            errors.add(error);
          }
          verifyMusicFilesFolder(file, errors, repair);
        } else {
          error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.UNEXPECTED_FILE);
          error.setFile(file.getFileName().toString());
          errors.add(error);
        }
      }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    } catch (DirectoryIteratorException x) {
      System.err.println(x);
    }
    
  }
  
  /**
   * This method verifies the Soundtrack Folder.
   * <p>
   * This folder only contains Album Folders.<br/>
   * A check is done on a valid album folder name format.
   * 
   * @param soundtrackFolderPath the Soundtrack Folder to verify.
   * @param errors An error list to which errors will be added.
   * @param repair if true, errors will be repaired (if possible).
   */
  private void verifySoundtrackFolderStructure(Path soundtrackFolderPath, List<MusicFolderStructureErrorInfo> errors, boolean repair) {
    
    MusicFolderStructureErrorInfo error;
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(soundtrackFolderPath)) {
      for (Path file: stream) {
        LOGGER.info("Handling Soundtrack Album folder: " + file.toAbsolutePath().toString());
        if (Files.isDirectory(file)) {
          String directoryName = file.getFileName().toString();
          if (!AlbumFolder.isValidAlbumFolderName(directoryName)) {
            LOGGER.severe("Wrong soundtrack folder: " + file.getFileName().toString());
            error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.WRONG_FOLDER_NAME);
            error.setFolder(file);
            errors.add(error);
          }
          verifyMusicFilesFolder(file, errors, repair);
        } else {
          error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.UNEXPECTED_FILE);
          error.setFile(file.getFileName().toString());
          errors.add(error);
        }
      }
    } catch (IOException ioException) {
      ioException.printStackTrace();
    } catch (DirectoryIteratorException x) {
      System.err.println(x);
    }
    
  }
  
  /**
   * Check whether an artist folder name is valid.
   * <p>
   * For the Numeric Name Index, the artist name (folder name) has to start with a digit.</br>
   * For all other index folders, the artist name has to start with the character of the folder name.
   * 
   * @param indexFolderName the name of the index folder.
   * @param artistFolderName the name of the artist folder within the <code>indexFolderName</code>.
   * @return true is the name is valid, fals otherwise.
   */
  private boolean isValidArtistFolderForIndexFolder(String indexFolderName, String artistFolderName) {
    if (isNumericNameIndexFolder(indexFolderName)) {
      char firstChar = artistFolderName.charAt(0);
      return Character.isDigit(firstChar);
    } else {
      return artistFolderName.startsWith(indexFolderName);
    }
  }
  
  /**
   * Check whether a folder name is the Numeric Name Index folder name.
   * 
   * @param folderName the folder name to check
   * @return true is <code>folderName</code> is the name of the Numeric Name Index folder.
   */
  private boolean isNumericNameIndexFolder(String folderName) {
    return folderName.equals(MusicFolderDescription.NUMERIC_NAME_INDEX_FOLDER);
  }
  
  /**
   * Verify the structure of a folder with music files (an Album Folder of Tracks Folder).
   * <p>
   * These folders shall only contain audio files.
   * 
   * @param artistFolder
   * @param errors
   * @param repair if true, errors will be repaired (if possible).
   */
  private void verifyMusicFilesFolder(Path musicFilesFolder, List<MusicFolderStructureErrorInfo> errors, boolean repair) {
    MusicFolderStructureErrorInfo error;

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(musicFilesFolder)) {
      for (Path path: stream) {
        LOGGER.fine("Handling path: " + path.getFileName().toString());
        if (Files.isDirectory(path)) {
          LOGGER.severe("Found folder in Music Files Folder: " + path.getParent().toString() + PATH_SEPARATOR + path.getFileName().toString());
          error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.UNEXPECTED_FOLDER);
          error.setFolder(path);
          errors.add(error);
        } else {  // it is a file
          if (!FileUtils.isAudioFile(path)) {
            if (MusicFolderUtil.isWindowsMediaPlayerPicture(path)) {
              LOGGER.severe("Found Windows Media Player file: " + path.getFileName().toString());
              error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.FOUND_WINDOWS_MEDIA_PLAYER_FILE);
              error.setFolder(path.getParent());
              error.setFile(path.getFileName().toString());
              errors.add(error);
              if (repair) {
                LOGGER.severe("Deleting file: "  + path.getParent().toString() + PATH_SEPARATOR + path.getFileName().toString());
                Files.delete(path);
                error.setRepaired(true);
              }
            } else {
              LOGGER.severe("Found non-audio file: " + path.getParent().toString() + PATH_SEPARATOR + path.getFileName().toString());
              error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.FOUND_NON_AUDIO_FILE);
              error.setFolder(path.getParent());
              error.setFile(path.getFileName().toString());
              errors.add(error);
            }
          } else {
            if (!FileUtils.hasLowerCaseExtension(path)) {
              LOGGER.severe("Audio file with upper or mixed case extension found: " + path);
              error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.AUDIO_FILE_WITH_MIXED_OR_UPPER_CASE_EXTENSION);
              error.setFolder(path.getParent());
              error.setFile(path.getFileName().toString());
              errors.add(error);
              if (repair) {
                String[] fileNameAndExtension = FileUtils.getFileNameAndExtension(path);
                LOGGER.info("Filename = " + fileNameAndExtension[0] + ", extension = " + fileNameAndExtension[1]);
                Path tempPath= Paths.get(path.getParent().toString(), fileNameAndExtension[0] + "." + fileNameAndExtension[1].toLowerCase() + "_tempForExtToLowerCase");
                Path correctedPath= Paths.get(path.getParent().toString(), fileNameAndExtension[0] + "." + fileNameAndExtension[1].toLowerCase());
                LOGGER.severe("Corrected name = " + correctedPath.getParent().toString() + PATH_SEPARATOR + correctedPath.getFileName().toString());
                Files.move(path, tempPath);
                Files.move(tempPath, correctedPath);
                error.setRepaired(true);
              }
            } else if (FileUtils.isWavFile(path)) {
              error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.WAV_FILE_FOUND);
              error.setFolder(path.getParent());
              error.setFile(path.getFileName().toString());
              errors.add(error);
              
              if (repair) {
// Code disabled, because my Dune mediaplayer cannot play the generated files.                
//                FLAC_FileEncoder flacFileEncoder = new FLAC_FileEncoder();
//                File wavFile = path.toFile();
//                Path flacFilePath = path.getParent().resolve(FileUtils.getFileNameWithoutExtension(wavFile) + ".flac");
//                File flacFile = flacFilePath.toFile();
//                LOGGER.severe("Going to encode " + wavFile.getAbsolutePath() + " to " + flacFile.getAbsolutePath());
//                Status status = flacFileEncoder.encode(wavFile, flacFile);
//                if (status == Status.FULL_ENCODE) {
//                  LOGGER.severe("Going to remove " + wavFile.getAbsolutePath());
//                  Files.delete(path);
//                  error.setRepaired(true);
//                } else {
//                  error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.ENCODING_WAV_TO_FLAC_FAILED);
//                  error.setFolder(path.getParent());
//                  error.setFile(path.getFileName().toString());
//                  error.setDetails("Flac encoder result: " + status.name());
//                  errors.add(error);
//                }
              }
            } else if (FileUtils.isMpeg4AudioFile(path)) {
              error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.MPEG_4_AUDIO_FILE_FOUND);
              error.setFolder(path.getParent());
              error.setFile(path.getFileName().toString());
              errors.add(error);              
            } else if (FileUtils.isWindowsMediaAudioFile(path)) {
              error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.WINDOWS_MEDIA_AUDIO_FILE_FOUND);
              error.setFolder(path.getParent());
              error.setFile(path.getFileName().toString());
              errors.add(error);              
            }
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }

  }

  /**
   * Check whether a folder is the soundtracks folder.
   * 
   * @param folderName the folder name to check.
   * @return true if the folder is the soundtrack folder.
   */
  private boolean isSoundtrackFolder(String folderName) {
    return folderName.equals(SOUNDTRACK_FOLDER_NAME);
  }
  
  /**
   * Check whether a folder is a Track Folder (a folder with a collection of tracks, e.g. pop tracks).
   * 
   * @param folderName the folder name to check.
   * @return true if the folder is a track folder.
   */
  private boolean isTrackFolder(String folderName) {
    return TRACK_FOLDER_NAMES.contains(folderName);
  }
  
  /**
   * Check whether a folder is a special music folder (a folder which is not to be checked).
   * 
   * @param folderName the folder name to check.
   * @return true if the folder is a special music folder.
   */
  private boolean isSpecialMusicFolder(String folderName) {
    return SPECIAL_MUSIC_FOLDER_NAMES.contains(folderName);
  }
  
  /**
   * Check whether a folder is to be ignored.
   * 
   * @param folderName the folder name to check.
   * @return true if the folder is to be ignored.
   */
  private boolean isFolderToBeIgnored(String folderName) {
    return NAMES_OF_FOLDERS_TO_BE_IGNORED.contains(folderName);
  }
  
  /**
   * Check whether a file is to be ignored.
   * 
   * @param fileName the file name to check.
   * @return true if the file is to be ignored.
   */
  private boolean isFileToBeIgnored(String fileName) {
    return NAMES_OF_FILES_TO_BE_IGNORED.contains(fileName);
  }
}
