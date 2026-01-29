package goedegep.media.mediadb.app;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Track;
import goedegep.media.mediadb.model.TrackReference;

public class MediaDbErrorInfo {
  private MediaDbError errorCode;
  private Album album;
  private TrackReference trackReference;
  private Track track;
  private Artist artist;

  public MediaDbError getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(MediaDbError errorCode) {
    this.errorCode = errorCode;
  }

  public Album getAlbum() {
    return album;
  }

  public void setAlbum(Album album) {
    this.album = album;
  }

  public TrackReference getTrackReference() {
    return trackReference;    
  }

  public void setTrackReference(TrackReference trackReference) {
    this.trackReference = trackReference;    
  }

  public Track getTrack() {
    return track;
  }

  public void setTrack(Track track) {
    this.track = track;
  }

  public Artist getArtist() {
    return artist;
  }

  public void setArtist(Artist artist) {
    this.artist = artist;
  }

}
