package goedegep.media.mediadb.app.guifx;


import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.Track;

public class TrackWrapper {
  private Track track;

  
  public TrackWrapper(Track track) {
    this.track = track;
  }
  
  /**
   * Provide a String representation of a Track.
   * <p>
   * &lt;artist-name&gt; - &lt;track-title&gt; (&lt;original-album&gt;)
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    Album originalAlbum = null;
    Disc originalDisc = track.getOriginalDisc();
    if (originalDisc != null) {
      originalAlbum = originalDisc.getAlbum();
    }
    
    String artistName = "<no artist name>";
    Artist artist = track.getArtist();
    if (artist != null  &&  artist.getName() != null  &&  !artist.getName().isEmpty()) {
      artistName = artist.getName();
    } else {
      if (originalAlbum != null) {
        artist = originalAlbum.getArtist();
        if (artist != null  &&  artist.getName() != null  &&  !artist.getName().isEmpty()) {
          artistName = artist.getName();
        }
      }
      
    }
    buf.append(artistName);
    
    buf.append(" - ");
    
    String trackTitle = "<no track title>";
    if (track.getTitle() != null  &&  !track.getTitle().isEmpty()) {
      trackTitle = track.getTitle();
    }
    buf.append(trackTitle);
    
    buf.append(" (");
    String albumTitle = "<no album title>";
    if (originalAlbum != null) {
      if (originalAlbum.getTitle() != null  &&  !originalAlbum.getTitle().isEmpty()) {
        albumTitle = originalAlbum.getTitle();
      }
    }
    buf.append(albumTitle);
    buf.append(")");
    
    return buf.toString();
  }

  public Track getTrack() {
    return track;
  }
  
}
