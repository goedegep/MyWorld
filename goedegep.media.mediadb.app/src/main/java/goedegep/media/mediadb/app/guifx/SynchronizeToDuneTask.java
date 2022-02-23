package goedegep.media.mediadb.app.guifx;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.media.musicfolder.MusicFolder;
import goedegep.util.dir.DirectoryUtils;
import goedegep.util.file.FileUtils;
import javafx.concurrent.Task;

/**
 * This class is a {@link Task} for synchronizing the Music Folder to the Dune media player.
 */
public class SynchronizeToDuneTask extends Task<Void> {
  private static final Logger LOGGER = Logger.getLogger(SynchronizeToDuneTask.class.getName());
  
  private String musicFolderName;
  private String duneFolderPath;
  private double dummyProgress = 1.0;
  private long currentTimeMilles;
  
  /**
   * Constructor. 
   * 
   * @param musicFolderName name of the Music Folder, the source folder.
   * @param duneFolderPath name of the music folder on the Dune, the destination folder.
   */
  public SynchronizeToDuneTask(String musicFolderName, String duneFolderPath) {
    this.musicFolderName = musicFolderName;
    this.duneFolderPath = duneFolderPath;
  }

  @Override
  protected Void call() {
    LOGGER.info("=>");
    
    try {
      syncMusicFolder(musicFolderName, duneFolderPath);
    } catch (IOException e) {
      updateMessage(e.getMessage());
      e.printStackTrace();
    }
          
    LOGGER.info("<=");
    return null;
  }


  /**
   * Synchronize the contents of the Source (master) Music Folder to a copy of the Music Folder, e.g. on the Dune Media Player.
   * Upon successful completion, the destination folder is a copy of the source folder, apart from:
   * - At the Top Level, only the mainIndexFolders and trackFolders are copied.
   * It is advised to verify the Source Music Folder, by calling verifyStructure(), before using this method.
   * 
   * @param sourceMusicFolder the source (master) Music Folder.
   * @param destinationMusicFolder the destination Music Folder.
   * @throws IOException 
   */
  public void syncMusicFolder(String sourceMusicFolder, String destinationMusicFolder) throws IOException {
    LOGGER.info("=> sourceMusicFolder = " + destinationMusicFolder + ", musicDestinationFolder = " + destinationMusicFolder);
    
    Path sourceMusicFolderPath = Paths.get(sourceMusicFolder);
    Path destinationMusicFolderPath = Paths.get(destinationMusicFolder);
    List<String> toSyncFolders = new ArrayList<>();
    toSyncFolders.addAll(MusicFolder.MAIN_INDEX_FOLDER_NAMES);
    toSyncFolders.addAll(MusicFolder.TRACK_FOLDER_NAMES);
    currentTimeMilles = System.currentTimeMillis();
    syncFolders(sourceMusicFolderPath, destinationMusicFolderPath, toSyncFolders);
        
    LOGGER.fine("<= ");
  }

  /**
   * Synchronize a number of music folders.
   * 
   * @param sourceFolder Source (master) folder
   * @param destinationFolder Destination (target) folder
   * @param toSyncNames List of folder names to be synchronized (only used at the top level).
   * @throws IOException 
   */
  public void syncFolders(Path sourceFolder, Path destinationFolder, List<String> toSyncNames) throws IOException {
    LOGGER.info("=> sourceFolder=" + sourceFolder + ", destinationFolder=" + destinationFolder + ", toSyncNames=" + toSyncNames);
    
    if (isCancelled()) {
      return;
    }
    
    if (System.currentTimeMillis() > currentTimeMilles + 1000) {
      updateProgress(dummyProgress,  10000000.0);
      dummyProgress += 0.01;
      currentTimeMilles = System.currentTimeMillis();
    }
    
    Map<String, Path> filteredSourcePaths = createFilteredPathMap(sourceFolder, toSyncNames);
    Map<String, Path> destinationPaths = createFilteredPathMap(destinationFolder, null);

    /*
     * Delete obsolete items (files and folders) in the destinationFolder.
     * An item is obsolete if it doesn't exist in the sourceFolder, or (in case of a file) if the size differs from the file in the source folder.
     */
    List<String> deletedDestinationPaths = new ArrayList<>();
    for (String destinationName: destinationPaths.keySet()) {
      Path sourcePath = filteredSourcePaths.get(destinationName);
      Path destinationPath = destinationPaths.get(destinationName);
      
      if (sourcePath == null  ||  !equalPaths(sourcePath, destinationPath)) {
        LOGGER.info("Removing obsolete item: " + destinationPath.getParent().toString() + MusicFolder.PATH_SEPARATOR + destinationPath.getFileName().toString());
          DirectoryUtils.recursivelyDeleteDirectory(destinationPath);
          deletedDestinationPaths.add(destinationName);
      } else {
        LOGGER.info("Still valid item: " + destinationPath.getParent().toString() + MusicFolder.PATH_SEPARATOR + destinationPath.getFileName().toString());
      }
    }
    for (String destinationName: deletedDestinationPaths) {
      destinationPaths.remove(destinationName);
    }
    
    // Create new files and folders (recursively).
    for (String sourceName: filteredSourcePaths.keySet()) {
      Path sourcePath = filteredSourcePaths.get(sourceName);
      Path destinationPath = destinationPaths.get(sourceName);
      if (Files.isDirectory(sourcePath)) {
        if (destinationPath == null) {
          LOGGER.info("Creating folder: " + sourceName);
          destinationPath = destinationFolder.resolve(sourceName);
          Files.createDirectory(destinationPath);
        }
        syncFolders(sourcePath, destinationPath, null);
      } else {  // plain file
        if (destinationPath == null) {
          if (FileUtils.isAudioFile(sourcePath)) {
            LOGGER.info("Copying file: " + sourceName);
            destinationPath = destinationFolder.resolve(sourceName);
            Files.copy(sourcePath, destinationPath);
          } else {
            LOGGER.severe("Skipping non-audio file: " + sourcePath.getParent().toString() + MusicFolder.PATH_SEPARATOR + sourcePath.getFileName().toString());
          }
        }
      }
    }
  }
  
  /**
   * Create a fileName to Path map, as follows:
   * - if toSyncNames is null, all items in the folder are added to the map
   * - if toSyncNames is specified, only these names are added to the map.
   * If toSkipNames is also specified,
   *   these items are silently skipped.
   *   For all other files and folders a warning is logged.
   * 
   * @param folder Main folder
   * @param toSyncNames optional list of folder names
   * @param toSkipNames
   * @return
   */
  public Map<String, Path> createFilteredPathMap(Path folder, List<String> toSyncNames) {
    Map<String, Path> filteredPathsMap = new HashMap<>();
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
      for (Path path: stream) {
        LOGGER.fine("Handling path: " + path.getFileName().toString());
        if (toSyncNames == null  ||  toSyncNames.contains(path.getFileName().toString())) {
          LOGGER.fine("Is sync path: " + path.getFileName());
          filteredPathsMap.put(path.getFileName().toString(), path);
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      System.err.println(x);
    }
    
    return filteredPathsMap;
  }
  
  /**
   * Check whether two paths are 'equal'.
   * <p>
   * The paths are equal if:
   * <ul>
   * <li>Both paths are folders</li>
   * <li>Or both paths are files with equal sizes</li>
   * </ul>
   * 
   * @param path1 first Path for the compare
   * @param path2 second Path for the compare
   * @return true if the Paths are equal, false otherwise.
   * @throws IOException 
   */
  private boolean equalPaths(Path path1, Path path2) throws IOException {
    if (twoPathsAreFolders(path1, path2)  ||
        (twoPathsAreFiles(path1, path2) &&  equalSizes(path1, path2))) {
      LOGGER.fine("Equal Items: path1 = " + FileUtils.getFullPathName(path1, MusicFolder.PATH_SEPARATOR) + ", path2 = " + FileUtils.getFullPathName(path2, MusicFolder.PATH_SEPARATOR));
      return true;
    } else {
      LOGGER.fine("NON Equal Items: path1 = " + FileUtils.getFullPathName(path1, MusicFolder.PATH_SEPARATOR) + ", path2 = " + FileUtils.getFullPathName(path2, MusicFolder.PATH_SEPARATOR));
      return false;
    }
  }
  
  /**
   * Check whether two paths are folders.
   * 
   * @param path1 first Path for the compare
   * @param path2 second Path for the compare
   * @return true if both paths are folders, false otherwise.
   */
  private boolean twoPathsAreFolders(Path path1, Path path2) {
    if (Files.isDirectory(path1)  &&  Files.isDirectory(path2)) {
      LOGGER.fine("Both are folder: path1 = " + FileUtils.getFullPathName(path1, MusicFolder.PATH_SEPARATOR) + ", path2 = " + FileUtils.getFullPathName(path2, MusicFolder.PATH_SEPARATOR));
      return true;
    }
    
    LOGGER.fine("NOT Both are folder: path1 = " + FileUtils.getFullPathName(path1, MusicFolder.PATH_SEPARATOR) + ", path2 = " + FileUtils.getFullPathName(path2, MusicFolder.PATH_SEPARATOR));
    return false;
  }
  
  /**
   * Check whether two paths are files.
   * 
   * @param path1 first Path for the compare
   * @param path2 second Path for the compare
   * @return true if both paths are files, false otherwise.
   */
  private boolean twoPathsAreFiles(Path path1, Path path2) {
    if (Files.isRegularFile(path1)  &&  Files.isRegularFile(path2)) {
      LOGGER.fine("Both are file: path1 = " + FileUtils.getFullPathName(path1, MusicFolder.PATH_SEPARATOR) + ", path2 = " + FileUtils.getFullPathName(path2, MusicFolder.PATH_SEPARATOR));
      return true;
    }
    
    LOGGER.fine("Not Both are file: path1 = " + FileUtils.getFullPathName(path1, MusicFolder.PATH_SEPARATOR) + ", path2 = " + FileUtils.getFullPathName(path2, MusicFolder.PATH_SEPARATOR));
    return false;
  }

  /**
   * Check whether two files have equal sizes.
   * 
   * @param path1 first file Path for the compare
   * @param path2 second file Path for the compare
   * @return true ir both files have the same size, false otherwise.
   * @throws IOException 
   */
  private boolean equalSizes(Path path1, Path path2) throws IOException {
      return Files.size(path1) == Files.size(path2);
  }
}
