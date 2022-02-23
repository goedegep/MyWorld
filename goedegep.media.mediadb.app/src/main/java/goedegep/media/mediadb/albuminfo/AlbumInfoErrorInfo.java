package goedegep.media.mediadb.albuminfo;

public class AlbumInfoErrorInfo {
  private AlbumInfoError errorCode;
  private AlbumReferenceInfo albumReferenceInfo;
  private TrackReferenceInfo trackReferenceInfo;
  
  public AlbumInfoError getErrorCode() {
    return errorCode;
  }
  
  public void setErrorCode(AlbumInfoError errorCode) {
    this.errorCode = errorCode;
  }
  
  public AlbumReferenceInfo getAlbumReferenceInfo() {
    return albumReferenceInfo;
  }
  
  public void setAlbumReferenceInfo(AlbumReferenceInfo albumReferenceInfo) {
    this.albumReferenceInfo = albumReferenceInfo;
  }
  
  public TrackReferenceInfo getTrackReferenceInfo() {
    return trackReferenceInfo;
  }
  
  public void setTrackReferenceInfo(TrackReferenceInfo trackReferenceInfo) {
    this.trackReferenceInfo = trackReferenceInfo;
  }
}
