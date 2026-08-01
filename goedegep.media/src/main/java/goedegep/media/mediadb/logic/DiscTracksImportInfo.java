package goedegep.media.mediadb.logic;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class provides the information to import disc tracks into the Music Folder.<br/>
 * It contains:
 * <ul>
 * <li>The destination folder path</li>
 * <li>{@link TrackImportInfo} per track</li>
 * <li>A list of errors</li>
 * </ul>
 * 
 */
public class DiscTracksImportInfo {
  private static final String NEW_LINE = System.getProperty("line.separator");

  /**
   * The Path for the destination folder.
   * This can be used to create the folder if it doesn't exist yet.
   */
  Path destinationFolderPath;
  
  /**
   * Information per file to be copied.
   */
  ObservableList<TrackImportInfo> trackImportInfos = FXCollections.observableArrayList();

  /**
   * List of errors encountered while creating the import information.<br/>
   * Actual copying of the files shall only be done if this list is empty.
   */
  List<Object> errors = new ArrayList<>();

  /**
   * Constructor.
   * 
   * @param trackImportInfos Information per file to be copied.
   * @param errors List of errors encountered while creating the import information.
   */
  public DiscTracksImportInfo() {
  }

  /**
   * Get the path for the destination folder.
   * 
   * @return the path for the destination folder.
   */
  public Path getDestinationFolderPath() {
    return destinationFolderPath;
  }

  /**
   * Set the path for the destination folder.
   * 
   * @param destinationFolderPath The Path for the destination folder.
   */
  public void setDestinationFolderPath(Path destinationFolderPath) {
    this.destinationFolderPath = destinationFolderPath;
  }

  /**
   * Get the information per file to be copied.
   * 
   * @return an ObservableList with the information per file to be copied.
   */
  public ObservableList<TrackImportInfo> getTrackImportPreviewInfos() {
    return trackImportInfos;
  }

  /**
   * Set the information per file to be copied.
   * 
   * @param trackImportPreviewInfos Information per file to be copied.
   */
  public void setTrackImportPreviewInfos(ObservableList<TrackImportInfo> trackImportPreviewInfos) {
    this.trackImportInfos = trackImportPreviewInfos;
  }

  /**
   * Get the list of errors encountered while creating the import information.<br/>
   * The other fields shall only be used if this list is empty.
   * 
   * @return The list of errors encountered while creating the import information.
   */
  public List<Object> getErrors() {
    return errors;
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    // destination folder
    buf.append("Destination folder: ").append(destinationFolderPath.toString()).append(NEW_LINE);
    
    // track import info
    buf.append("Track import info:").append(NEW_LINE);
    for (TrackImportInfo trackImportInfo: trackImportInfos) {
      buf.append(trackImportInfo.toString());
    }
    
    // errors
    buf.append("Errors:").append(NEW_LINE);
    for (Object object: errors) {
      buf.append(object.toString()).append(NEW_LINE);
    }
    
    return buf.toString();
  }
}
