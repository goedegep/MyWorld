package goedegep.media.mediadb.app;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class provides the information to import disc tracks into the Music Folder.
 */
public class DiscTracksImportInfo {
  /**
   * The Path for the destination folder.
   * This can be used to create the folder if it doesn't exist yet.
   */
  Path destinationFolderPath;
  
  /**
   * Information per file to be copied.
   */
  ObservableList<TrackImportInfo> trackImportPreviewInfos = FXCollections.observableArrayList();

  /**
   * List of errors encountered while creating the import information.<br/>
   * Actual copying of the files shall only be done if this list is empty.
   */
  List<Object> errors = new ArrayList<>();

  /**
   * Constructor.
   * 
   * @param trackImportPreviewInfos Information per file to be copied.
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
    return trackImportPreviewInfos;
  }

  /**
   * Set the information per file to be copied.
   * 
   * @param trackImportPreviewInfos Information per file to be copied.
   */
  public void setTrackImportPreviewInfos(ObservableList<TrackImportInfo> trackImportPreviewInfos) {
    this.trackImportPreviewInfos = trackImportPreviewInfos;
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
  
}
