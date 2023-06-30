package goedegep.media.musicfolder;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;


/**
 * This class contains all information about an Artist Folder.
 */
public class ArtistFolder {
  private static final Logger LOGGER = Logger.getLogger(MusicFolder.class.getName());
  
  /**
   * Gather all information on albums and their tracks for one specific artist.
   * There's one sub folder per album.
   */
  public static void getAlbumOnDiscInfoForArtistFolder(Path artistFolder, List<AlbumOnDiscInfo> albumsOnDiscInfo, List<MusicFolderStructureErrorInfo> errors) {
    LOGGER.fine("=> artistFolder = " + artistFolder);
    
    MusicFolderStructureErrorInfo error;
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(artistFolder)) {
      // Each path should be an Album Folder
      for (Path path: stream) {
        String fileName = path.getFileName().toString();

        if (Files.isDirectory(path)) {
          LOGGER.info("Is folder: " + fileName);
          
          if (AlbumFolder.isValidAlbumFolderName(fileName)) {
            AlbumFolder.getAlbumOnDiscInfoForAlbumFolder(path, true, albumsOnDiscInfo, errors);
          } else {
            LOGGER.severe("Wrong name for album folder: " + fileName);
            error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.WRONG_FOLDER_NAME);
            error.setFolder(path);
            errors.add(error);
          }
        } else {
          LOGGER.severe("Skipping suspected file: " + artistFolder + "\\" + fileName);
          error = new MusicFolderStructureErrorInfo(MusicFolderStructureError.UNEXPECTED_FILE);
          error.setFile(path.getFileName().toString());
          error.setFolder(artistFolder);
          errors.add(error);
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      // In this snippet, it can only be thrown by newDirectoryStream.
      System.err.println(x);
    }
    
    LOGGER.fine("<= ");
  }

}
