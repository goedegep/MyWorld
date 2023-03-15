package goedegep.media.mediadb.app;

import java.util.logging.Logger;

import goedegep.jfx.stringconverters.StringConverterAndChecker;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.MediaDb;

/**
 * This class is a {@link StringConverterAndChecker} for en {@link Artist}.
 */
public class ArtistStringConverterAndChecker extends StringConverterAndChecker<Artist> {
  private static final Logger LOGGER = Logger.getLogger(ArtistStringConverterAndChecker.class.getName());
  
  /**
   * Media database, used to get artist by name.
   */
  private MediaDb mediaDb;
  
  /**
   * Constructor
   * 
   * @param mediaDb the media database providing an artist list.
   */
  public ArtistStringConverterAndChecker(MediaDb mediaDb) {
    this.mediaDb = mediaDb;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid(String string) {
    LOGGER.severe("=> " + string);
    
    if (string == null) {
      return true;
    }
    
    boolean returnValue = fromString(string) != null;
    LOGGER.severe("<= " + returnValue);
    return returnValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString(Artist artist) {
    if (artist == null) {
      return null;
    }
    
    return artist.getName();
  }

  /**
   * {@inheritDoc}
   * 
   * This method never throws an exception. Either an artist is found, or not (in which case null is returned).
   */
  @Override
  public Artist fromString(String artistName) {
    Artist artist = mediaDb.getArtist(artistName);
    LOGGER.info("=> " + (artist != null ? artist.getName() : "<null>"));
    return artist;
  }
}
