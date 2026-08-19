package goedegep.media.musicfolder.logic;

import java.util.List;

import goedegep.util.datetime.FlexDate;


/**
 * This record stores information on the folder name and track filenames of an album.
 * <p>
 * The album is identified by the year of issue, the artist and the title.<br>
 * For the album the folder in which it can be found is stored, and the filenames of the tracks are stored.<br/>
 * The first 3 parameters identify the album.<br/>
 * The next 2 parameters provide the information on where the album tracks can be found on hard disk.
 * 
 * TODO rewrite the following
 * Note: On disc, in the folder name, usually only the year is used, when needed extended with the month,
 * and very rarely extended with the day of the month. This means that this issueDate normally differs from
 * the issueDate in the database.
 * 
 * @param issueDate The issue (release) date of the album.
 * @param artist The album artist
 * @param title The title of the album
 * @param albumFolderName The folder, as an absolute path, in which the album tracks are stored.
 * @param trackFileNames The file names of the tracks, in the order as they appear on the album.
 */
public record AlbumOnHardDiskInfo(FlexDate issueDate, String artist, String title, String albumFolderName, List<String> trackFileNames) {
  
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
