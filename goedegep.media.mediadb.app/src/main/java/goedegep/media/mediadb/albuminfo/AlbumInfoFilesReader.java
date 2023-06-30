package goedegep.media.mediadb.albuminfo;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.media.app.MediaRegistry;
import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;
import goedegep.media.mediadb.model.util.MediaDbUtil;
import goedegep.util.datetime.FlexDate;

/**
 * This class reads 'AlbumInfo' files and stores the information in a MediaDb structure. It also writes the information from a MediaDb structure to 'AlbumInfo' files.
 * <p>
 * This class has the knowledge of the filename conventions. For reading it scans the folder with 'AlbumInfo' files. For each file it calls an {@link AlbumInfoHandler} to parse
 * the file.
 */
public class AlbumInfoFilesReader {
  private static final Logger     LOGGER = Logger.getLogger(AlbumInfoFilesReader.class.getName());

  /**
   * The media database in which information is stored, or from which information is written.
   */
  private MediaDb mediaDb;

  /**
   * Constructor
   * 
   * @param mediaDb the media database in which to store the information read, or from which to write the information.
   */
  public AlbumInfoFilesReader(MediaDb mediaDb) {
    this.mediaDb = mediaDb;
  }

  /**
   * Read 'AlbumInfo' files.
   * <p>
   * These files follow the following naming convention: "Artist " + &lt;artist-name&gt; + ".xml".<br/>
   * The following types of errors may be added to the error list: AlbumInfoErrorInfo, goedegep.util.ParseException.
   * 
   * @param errors a list of errors, to which errors encountered while reading are added (mandatory).
   * @param albumInfoDirectory the directory where the AlbumInfo files are located (mandatory).
   * 
   * @see AlbumInfoHandler for more information on AlbumInfo files.
   */
  public void readAlbumInfoFiles(List<Object> errors, String albumInfoDirectory) {

    AlbumInfoHandler albumInfoHandler = new AlbumInfoHandler();
    albumInfoHandler.setMediaDb(mediaDb);

    Path albumsFolder = Paths.get(albumInfoDirectory);

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(albumsFolder)) {
      for (Path file: stream) {
        String fileName = file.getFileName().toString();
        if (!Files.isDirectory(file)  &&
            ((fileName.startsWith("Artist ")  &&  fileName.endsWith(".xml")) ||
                fileName.equals("Soundtracks.xml"))) {
          LOGGER.severe("Reading file: " + file.getFileName().toString());
          try {
            List<AlbumInfoErrorInfo> albumInfoErrors = albumInfoHandler.read(file.toFile().getAbsolutePath());
            errors.addAll(albumInfoErrors);
          } catch (goedegep.util.sax.ParseException e) {
            errors.add(e);
          }
        }
      }
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      System.err.println(x);
    }

  }
  
  public void writeAlbumInfoFiles(List<Object> errors, String albumInfoDirectory) {
    List<Album> albums = mediaDb.getAlbums();
    
    // find albums per (container) artist.
    Map<Artist, List<Album>> artistAlbumsMap = new HashMap<>();
    
    for (Album album: albums) {
      Artist artist = album.getArtist();
      if (artist == null) {
        errors.add("No artist for album: " + album.toString());
        continue;
      }
      
      if (artist.getContainerArtist() != null) {
        artist = artist.getContainerArtist();
      }
      
      List<Album> artistAlbums = artistAlbumsMap.get(artist);
      if (artistAlbums == null) {
        artistAlbums = new ArrayList<Album>();
        artistAlbumsMap.put(artist, artistAlbums);
      }
      artistAlbums.add(album);
    }
    
    AlbumInfoHandler albumInfoHandler = new AlbumInfoHandler();

    // For each container artist, write the album info file.
    List<Artist> artists = new ArrayList<>(artistAlbumsMap.keySet());
    Collections.sort(artists, (Artist artist1, Artist artist2) -> artist1.getName().compareTo(artist2.getName()));
    for (Artist artist: artists) {
      List<Album> artistAlbums = artistAlbumsMap.get(artist);
      Collections.sort(artistAlbums, new AlbumComparator());
      LOGGER.severe("Writing AlbumInfo file for artist: " + artist.getName());
      
      String albumInfoFileName = "Artist " + artist.getName() + ".xml";

      albumInfoHandler.write(albumInfoDirectory + "\\" + albumInfoFileName, artistAlbums);
      
      Path newAlbumInfoFilePath = Paths.get(albumInfoDirectory, albumInfoFileName);
      Path originalAlbumInfoFilePath = Paths.get(MediaRegistry.albumInfoDirectory, albumInfoFileName);
      try {
        long firstMismatch = Files.mismatch(originalAlbumInfoFilePath, newAlbumInfoFilePath);
        if (firstMismatch != -1) {
          LOGGER.severe("New file differs from original at byte: " + firstMismatch);
          break;
        }
      } catch (IOException e) {
        e.printStackTrace();
        break;
      }
    }
  }
  
  public void writeAlbumInfoFile(List<Object> errors, String albumInfoDirectory, Artist containerArtist) {
    List<Album> albums = mediaDb.getAlbums();
    
    // find albums per (container) artist.
    Map<Artist, List<Album>> artistAlbumsMap = new HashMap<>();
    
    for (Album album: albums) {
      Artist artist = album.getArtist();
      if (artist == null) {
        errors.add("No artist for album: " + album.toString());
        continue;
      }
      
      if (artist.getContainerArtist() != null) {
        artist = artist.getContainerArtist();
      }
      
      List<Album> artistAlbums = artistAlbumsMap.get(artist);
      if (artistAlbums == null) {
        artistAlbums = new ArrayList<Album>();
        artistAlbumsMap.put(artist, artistAlbums);
      }
      artistAlbums.add(album);
    }
    
    AlbumInfoHandler albumInfoHandler = new AlbumInfoHandler();

    // For each container artist, write the album info file.
    List<Artist> artists = new ArrayList<>(artistAlbumsMap.keySet());
    Collections.sort(artists, (Artist artist1, Artist artist2) -> artist1.getName().compareTo(artist2.getName()));
    
    List<Album> artistAlbums = artistAlbumsMap.get(containerArtist);
    Collections.sort(artistAlbums, new AlbumComparator());
    LOGGER.severe("Writing AlbumInfo file for artist: " + containerArtist.getName());

    String albumInfoFileName = "Artist " + containerArtist.getName() + ".xml";

    albumInfoHandler.write(albumInfoDirectory + "\\" + albumInfoFileName, artistAlbums);

    Path newAlbumInfoFilePath = Paths.get(albumInfoDirectory, albumInfoFileName);
    Path originalAlbumInfoFilePath = Paths.get(MediaRegistry.albumInfoDirectory, albumInfoFileName);
    try {
      long firstMismatch = Files.mismatch(originalAlbumInfoFilePath, newAlbumInfoFilePath);
      if (firstMismatch != -1) {
        LOGGER.severe("New file differs from original at byte: " + firstMismatch);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}

class AlbumComparator implements Comparator<Album> {

  @Override
  public int compare(Album album1, Album album2) {
    FlexDate releaseDateAlbum1 = null;
    FlexDate releaseDateAlbum2 = null;
    
    if (album1 != null) {
      if (MediaDbUtil.isOwnCompilationAlbum(album1)) {
        releaseDateAlbum1 = new FlexDate(9999);
      } else {
        releaseDateAlbum1 = album1.getReleaseDate();
      }
    }
    
    if (album2 != null) {
      if (MediaDbUtil.isOwnCompilationAlbum(album2)) {
        releaseDateAlbum2 = new FlexDate(9999);
      } else {
        releaseDateAlbum2 = album2.getReleaseDate();
      }
    }
    
    if (releaseDateAlbum1 == null) {
      return -1;
    } else if (releaseDateAlbum2 == null) {
      return 1;
    } else {
      return releaseDateAlbum1.compareTo(releaseDateAlbum2);
    }
  }
  
}
