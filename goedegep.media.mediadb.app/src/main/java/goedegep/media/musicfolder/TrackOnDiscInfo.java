package goedegep.media.musicfolder;

import java.nio.file.Path;

/**
 * This class provides information on where a music track is stored on disc (file system).
 */
public class TrackOnDiscInfo {
  /**
   * Path for the track on disc.
   */
  private Path trackPath;

  /**
   * Constructor.
   * 
   * @param trackPath Path for the track on disc.
   */
  public TrackOnDiscInfo(Path trackPath) {
    this.trackPath = trackPath;
  }

  /**
   * Get the path for the track on disc.
   * @return
   */
  public Path getTrackPath() {
    return trackPath;
  }
  
  @Override
  public String toString() {
    return trackPath.toString();
  }
}
