package goedegep.media.mediadb.app;

import java.nio.file.Path;

/**
 * This class provides the information to copy a track from some folder into the Music Folder.<br/>
 * It contains:
 * <ul>
 * <li>The track number</li>
 * <li>The source file path</li>
 * <li>The destination file path</li>
 * </ul>
 */
public class TrackImportInfo {
  /**
   * The track number (starting at 1).
   */
  private int trackNr;
  
  /**
   * Path for the source file
   */
  private Path sourceFilePath;
  
  /**
   * Path for the destination file.
   */
  private Path destinationFilePath;
  
  /**
   * Get the track number (starting at 1).
   * 
   * @return the track number.
   */
  public int getTrackNr() {
    String.valueOf(trackNr);
    return trackNr;
  }
  
  /**
   * Get the track number (starting at 1) as text.
   * 
   * @return the track number.
   */
  public String getTrackNrText() {
    return String.valueOf(trackNr);
  }
  
  /**
   * Set the track number.
   * 
   * @param trackNr the track number.
   */
  public void setTrackNr(int trackNr) {
    this.trackNr = trackNr;
  }
  
  /**
   * Get the path for the source file.
   * 
   * @return the path for the source file.
   */
  public Path getSourceFilePath() {
    return sourceFilePath;
  }
  
  /**
   * Get the file name for the source file.
   * 
   * @return the file name for the source file.
   */
  public String getSourceFile() {
    return sourceFilePath.getFileName().toString();
  }
  
  /**
   * Set the path for the source file.
   * 
   * @param sourceFilePath the path for the source file.
   */
  public void setSourceFilePath(Path sourceFilePath) {
    this.sourceFilePath = sourceFilePath;
  }
  
  /**
   * Get the path for the destination file.
   * 
   * @return the path for the destination file.
   */
  public Path getDestinationFilePath() {
    return destinationFilePath;
  }
  
  /**
   * Set the destination file path.
   * 
   * @param destinationFileName the path for the destination file.
   */
  public void setDestinationFilePath(Path destinationFileName) {
    this.destinationFilePath = destinationFileName;
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    // trackNr
    buf.append("Track Nr: ").append(trackNr);
    
    // source file path
    buf.append(", Source file path:").append(sourceFilePath.toString());
    
    // destination file path
    buf.append(", Destination file  path:").append(destinationFilePath.toString());
    
    return buf.toString();
  }

}
