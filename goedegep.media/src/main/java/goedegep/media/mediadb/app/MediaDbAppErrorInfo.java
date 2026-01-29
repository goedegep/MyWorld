package goedegep.media.mediadb.app;

import java.nio.file.Path;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;
import goedegep.media.musicfolder.AlbumOnDiscInfo;

public class MediaDbAppErrorInfo {
  private static final String NEWLINE = System.getProperty("line.separator");

  private MediaDbAppError errorCode = null;
  private AlbumOnDiscInfo albumOnDiscInfo = null;
  private Path trackPath = null;
  private Album album = null;
  private Disc disc = null;
  private Track track = null;
  private TrackReference trackReference = null;
  private String details; // additional information

  public MediaDbAppErrorInfo(MediaDbAppError errorCode) {
    this.errorCode = errorCode;
  }
  
  public MediaDbAppError getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(MediaDbAppError errorCode) {
    this.errorCode = errorCode;
  }

  public AlbumOnDiscInfo getAlbumOnDiscInfo() {
    return albumOnDiscInfo;
  }

  public void setAlbumOnDiscInfo(AlbumOnDiscInfo albumOnDiscInfo) {
    this.albumOnDiscInfo = albumOnDiscInfo;
  }

  public Path getTrackPath() {
    return trackPath;
  }

  public void setTrackPath(Path trackPath) {
    this.trackPath = trackPath;
  }

  public Album getAlbum() {
    return album;
  }

  public void setAlbum(Album album) {
    this.album = album;
  }

  public Disc getDisc() {
    return disc;
  }

  public void setDisc(Disc disc) {
    this.disc = disc;
  }

  public Track getTrack() {
    return track;
  }

  public void setTrack(Track track) {
    this.track = track;
  }  
  
  public TrackReference getTrackReference() {
    return trackReference;
  }

  public void setTrackReference(TrackReference trackReference) {
    this.trackReference = trackReference;
  }  

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("errorCode: ").append(errorCode != null ? errorCode : "(null)").append(NEWLINE);
    
    buf.append("albumOnDiscInfo: ").append(NEWLINE);
    buf.append(albumOnDiscInfo != null ? albumOnDiscInfo.toString() : "(null)").append(NEWLINE);
    
    buf.append("trackPath: ").append(trackPath != null ? trackPath.toString() : "(null)").append(NEWLINE);
    
    buf.append("album: ").append(NEWLINE);
    buf.append(album != null ? album.toString() : "(null)").append(NEWLINE);
    
    buf.append("disc: ").append(NEWLINE);
    buf.append(disc != null ? disc.toString() : "(null)").append(NEWLINE);
    
    buf.append("track: ").append(NEWLINE);
    buf.append(track != null ? track.toString() : "(null)").append(NEWLINE);
    
    return buf.toString();
  }
}
