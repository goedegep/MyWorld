package goedegep.media.musicfolder.logic;

import java.util.List;

import goedegep.util.datetime.FlexDate;

public class AlbumOnHardDiskInfo {
  private FlexDate issueDate; 

  private String artist;
  
  private String title;
  private String albumFolderName;
  
  private List<String> trackFileNames;
  
  
  private AlbumOnHardDiskInfo(FlexDate issueDate, String artist, String title, String albumFolderName, List<String> trackFileNames) {
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
