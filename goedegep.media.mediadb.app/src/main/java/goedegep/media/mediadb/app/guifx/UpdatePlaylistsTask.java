package goedegep.media.mediadb.app.guifx;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import goedegep.media.musicfolder.MusicFolder;
import goedegep.util.file.FileUtils;
import javafx.concurrent.Task;

/**
 * This class is a {@link Task} for updating the playlists on the Dune media player.
 */
public class UpdatePlaylistsTask extends Task<Void> {
  private static final Logger LOGGER = Logger.getLogger(UpdatePlaylistsTask.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private String musicFolderName;
  private String dunePlayListsFolderPath;
  private double dummyProgress = 1.0;
  private long currentTimeMilles;
  
  /**
   * Constructor
   * 
   * @param musicFolderName name of the Music Folder, the source folder.
   * @param dunePlayListsFolderPath name of the playlist folder on the Dune, the destination folder.
   */
  public UpdatePlaylistsTask(String musicFolderName, String dunePlayListsFolderPath) {
    this.musicFolderName = musicFolderName;
    this.dunePlayListsFolderPath = dunePlayListsFolderPath;
  }

  @Override
  protected Void call() {
    LOGGER.info("=>");
    
    try {
      updatePlayLists(musicFolderName, dunePlayListsFolderPath, false);
    } catch (IOException e) {
      updateMessage(e.getMessage());
      e.printStackTrace();
    }
          
    LOGGER.info("<=");
    return null;
  }

  /**
   * Create play lists in a play lists folder for all albums in a music folder.
   * The play lists are organized in a similar structure as the music folder.
   *
   * @param musicFolderName name of the music folder for which the play lists have to be created.
   * @param playListFolderName name of the folder under which the play lists will be created.
   * @throws IOException 
   */
  public void updatePlayLists(String musicFolderName, String playListFolderName, boolean onlyAddNewLists) throws IOException {
    LOGGER.info("=> musicFolderName = " + musicFolderName + ", playListFoldernName = " + playListFolderName);
    Objects.requireNonNull(musicFolderName, "argument ‘musicFolderName’ must not be null");
    Objects.requireNonNull(playListFolderName, "argument ‘playListFolderName’ must not be null");
    
    Path playListFolder = Paths.get(playListFolderName);
    if (!Files.exists(playListFolder)) {
      throw new RuntimeException("Specified play list folder doesn't exist");
    }
    
    if (!onlyAddNewLists) {
      org.apache.commons.io.FileUtils.deleteDirectory(playListFolder.toFile());
      Files.createDirectory(playListFolder);
    }
    
    currentTimeMilles = System.currentTimeMillis();
    Path musicRootFolder = Paths.get(musicFolderName);
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(musicRootFolder)) {
      for (Path file: stream) {

        LOGGER.info("Handling path: " + file.getFileName().toString());
        if (Files.isDirectory(file)  && MusicFolder.MAIN_INDEX_FOLDER_NAMES.contains(file.getFileName().toString())) {
          LOGGER.info("Is main index folder: " + file.getFileName());
          Path mainIndexPlayListFolder = playListFolder.resolve(file.getFileName().toString());
          updatePlayListsForArtistFirstCharacterFolder(file, mainIndexPlayListFolder);
        } else if (Files.isDirectory(file)  && MusicFolder.TRACK_FOLDER_NAMES.contains(file.getFileName().toString())) {
          Path trackPlayListFolder = playListFolder.resolve(file.getFileName().toString());
          createPlayListForAlbumFolder(file, trackPlayListFolder);
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      // In this snippet, it can only be thrown by newDirectoryStream.
      System.err.println(x);
    }
        
    LOGGER.info("<= ");
  }

  /**
   * Update play lists for an artist first character index folder.
   * 
   * @param artistIndexFolder
   * @param playListIndexFolder
   */
  private void updatePlayListsForArtistFirstCharacterFolder(Path artistIndexFolder, Path playListIndexFolder) {
    LOGGER.info("=> artistIndexFolder = " + artistIndexFolder);
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(artistIndexFolder)) {
      for (Path file: stream) {
        
        if (isCancelled()) {
          return;
        }

        LOGGER.info("Handling path: " + file.getFileName().toString());
        if (Files.isDirectory(file)) {
          LOGGER.info("Is artist folder: " + file.getFileName());
          
          
          Path playListArtistFolder = playListIndexFolder.resolve(file.getFileName().toString());
          updatePlayListsForArtistFolder(file, playListArtistFolder);
          
        } else {
          LOGGER.severe("Skipping suspected file: " + file.getFileName().toString());
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
   * Update play lists for an artist folder.
   * 
   * @param artistFolder
   * @param playListArtistFolder
   */
  private void updatePlayListsForArtistFolder(Path artistFolder, Path playListArtistFolder) {
    LOGGER.info("=> artistFolder = " + artistFolder);
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(artistFolder)) {
      for (Path file: stream) {

        LOGGER.fine("Handling path: " + file.getFileName().toString());
        if (Files.isDirectory(file)) {
          LOGGER.info("Is album folder: " + file.getFileName());
          
          createPlayListForAlbumFolder(file, playListArtistFolder);
          
        } else {
          LOGGER.severe("Skipping suspected file: " + file.getFileName().toString());
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
   * Create a playlist for an album folder (actually any folder with music files).
   * 
   * @param albumFolder Path of the folder of the album.
   * @param playListArtistFolder Path of the play list folder, for the artist of the album.
   */
  private void createPlayListForAlbumFolder(Path albumFolder, Path playListArtistFolder) {
    LOGGER.info("=> albumFolder = " + albumFolder + ", playListArtistFolder=" + playListArtistFolder);
    
    if (isCancelled()) {
      return;
    }
    
    StringBuilder buf = new StringBuilder();
    for (int index = 0; index < albumFolder.getNameCount(); index++) {
      buf.append(MusicFolder.PATH_SEPARATOR);
      buf.append(albumFolder.getName(index).toString());
    }
    
    List<String> playListFilesNames = new ArrayList<>();
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(albumFolder)) {
      for (Path file: stream) {

        LOGGER.fine("Handling file: " + file.getFileName().toString());
        if (FileUtils.isAudioFile(file)) {
          LOGGER.fine("Is audio track: " + file.getFileName());
          
          playListFilesNames.add(buf.toString() + MusicFolder.PATH_SEPARATOR + file.getFileName().toString());
        } else {
          LOGGER.severe("Skipping non-audio file: " + file.getFileName().toString());
        }
      }
      
      Collections.sort(playListFilesNames);
      Path playListFilePath = playListArtistFolder.resolve(albumFolder.getFileName().toString() + ".m3u");
      if (playListFilesNames.size() != 0) {
        writePlayList(playListFilePath, playListFilesNames);
      } else {
        LOGGER.severe("Empty playList in folder: " + albumFolder.getParent().toString() + MusicFolder.PATH_SEPARATOR + albumFolder.getFileName().toString());
      }
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      // In this snippet, it can only be thrown by newDirectoryStream.
      System.err.println(x);
    }
    
    LOGGER.fine("<= ");
  }

  /**
   * Write a play list file.
   * 
   * @param playListFilePath the file path to which the play list will be written.
   * @param playList the list of tracks for the play list.
   */
  private void writePlayList(Path playListFilePath, List<String> playList) {
    LOGGER.fine("=> playListFilePath = " + playListFilePath.getParent().toString() + ", " + playListFilePath.getFileName().toString());
    
    if (System.currentTimeMillis() > currentTimeMilles + 1000) {
      updateProgress(dummyProgress,  10000000.0);
      dummyProgress += 0.01;
      currentTimeMilles = System.currentTimeMillis();
    }
    
    if (playList.size() == 0) {
      LOGGER.severe("Writing empty playlist.");
    }
    Path playListFolder = playListFilePath.getParent();
    try {
      if (!Files.exists(playListFolder)) {
        Files.createDirectories(playListFolder);
      }
      StringBuilder buf = new StringBuilder();
      for (String track: playList) {
        LOGGER.fine("Adding line: " + track);
        buf.append(track);
        buf.append(NEWLINE);
      }
      String output = buf.toString();
      LOGGER.fine("output: " + output);

      Charset charset = Charset.forName("windows-1252");
      BufferedWriter writer = Files.newBufferedWriter(playListFilePath, charset);
      writer.write(output, 0, output.length());
      writer.close();
    } catch (IOException e) {
      LOGGER.severe("Error occurred while writing PlayList: " + playListFilePath.getFileName().toString() + ", system message: " + e.getMessage());
      e.printStackTrace();
      System.exit(-1);
    }

    LOGGER.fine("<= ");
  }
    
}
