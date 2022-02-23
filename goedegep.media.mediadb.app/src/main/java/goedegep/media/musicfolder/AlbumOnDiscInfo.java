package goedegep.media.musicfolder;

import java.util.List;

import goedegep.util.datetime.FlexDate;

/**
 * This class stores information on the folder name and track filenames of an album.
 * <p>
 * The album is identified by the year of issue, the artist and the title.<br>
 * For the album the folder in which it can be found is stored, and the filenames of the tracks are stored.
 */
public class AlbumOnDiscInfo {
  // First 3 fields identify the album.
  private FlexDate issueDate;          // Note: On disc, in the folder name, usually only the year is used, when needed extended with the month,
                                       // and very rarely extended with the day of the month. This means that this issueDate normally differs from
                                       // the issueDate in the database.
  private String artist;
  private String title;
  
  // These fields provide the information on where the album can be found on disc.
  private String albumFolderName;      // The location where the album is stored on disc. This is an absolute path.
  private List<String> trackFileNames; // The file names of the tracks, in track order.
  
  public AlbumOnDiscInfo(FlexDate issueDate, String artist, String title, String albumFolderName, List<String> trackFileNames) {
    this.issueDate = issueDate;
    this.artist = artist;
    this.title = title;
    this.albumFolderName = albumFolderName;
    this.trackFileNames = trackFileNames;
  }

  public FlexDate getIssueDate() {
    return issueDate;
  }

  public String getArtist() {
    return artist;
  }

  public String getTitle() {
    return title;
  }

  public String getAlbumFolderName() {
    return albumFolderName;
  }

  public List<String> getTrackFileNames() {
    return trackFileNames;
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("Album: ");
    
    if (issueDate != null) {
      buf.append(issueDate);
    } else {
      buf.append("<no year of issue>");
    }
    
    buf.append(" - ");
    
    if (artist != null) {
      buf.append(artist);
    } else {
      buf.append("<no artist>");
    }
    
    buf.append(" - ");
    
    if (title != null) {
      buf.append(title);
    } else {
      buf.append("<no title>");
    }
    
    buf.append(", in folder: ");
    if (albumFolderName != null) {
      buf.append(albumFolderName);
    } else {
      buf.append("<no albumFolderName>");
    }
    
    buf.append(", tracks: ");
    boolean first = true;
    for (String trackFileName: trackFileNames) {
      if (first) {
        first = false;
      } else {
        buf.append(", ");
      }
      buf.append(trackFileName);
    }
    
    return buf.toString();
  }
}
