package goedegep.media.mediadb.app;

import goedegep.jfx.stringconverters.StringConverterAndChecker;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;

public class ArtistStringConverterAndChecker extends StringConverterAndChecker<Artist> {
  /**
   * Media database, used to get artist by name.
   */
  private MediaDb mediaDb;
  
  /**
   * Constructor
   */
  public ArtistStringConverterAndChecker(MediaDb mediaDb) {
    this.mediaDb = mediaDb;
  }

  @Override
  public boolean isValid(String string) {
    return fromString(string) != null;
  }

  @Override
  public String toString(Artist artist) {
    if (artist == null) {
      return null;
    }
    
    return artist.getName();
  }

  @Override
  public Artist fromString(String artistName) {
    return mediaDb.getArtist(artistName);
  }
}
