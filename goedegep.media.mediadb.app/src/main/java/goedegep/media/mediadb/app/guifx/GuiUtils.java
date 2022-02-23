package goedegep.media.mediadb.app.guifx;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.mediadb.model.MediumType;
import goedegep.media.mediadb.model.TrackReference;

public class GuiUtils {

  /**
   * Constructor
   * <p>
   * As this is a utility class, it should not be instantiated. This is guaranteed by this private constructor.
   */
  private GuiUtils() {
    
  }
  
  
  /**
   * Translate a MediumType/Writability pair into a text shown in the GUI.
   * 
   * @param mediumType the MediumType.
   * @param writability the Writability.
   * @return A text representing the <code>mediumTypa</code> and <code>Writability</code>.
   */
  public static String createMediumText(MediumType mediumType) {
    switch (mediumType) {
    case CD_AUDIO:
      return "CD";
      
    case CDR_AUDIO:
      return "CDR";
      
    case CDRW_AUDIO:
      return "CDRW";
      
    case CD_ROM:
      return "CD_ROM";
      
    case DVD_ROM:
      return "DVD_ROM";
      
    case DVD_VIDEO:
      return "DVD";
      
    case HARDDISK:
      return "Harddisk";
      
    case LP:
      return "LP";
      
    case SACD:
      return "SA CD";
      
    case SINGLE:
      return "Single";
      
    case NOT_SET:
      throw new IllegalArgumentException("mediumType not set");
      
    default:
      throw new IllegalArgumentException("Unknown MediumType: " + mediumType);
    }
  }
  
  /**
   * Create the text for a track reference; <album-title> - <track-nr> - <track-title>
   * @param trackReference the TrackReference for which the text is to be created.
   * @return the text for the <code>trackReference</code>.
   */
  public static String createTrackReferenceText(TrackReference trackReference) {
    StringBuilder buf = new StringBuilder();
    Disc disc = trackReference.getDisc();
    Album album = null;
    if (disc != null) {
      album = disc.getAlbum();
      buf.append(album.getTitle());
    }
    
    buf.append(" - ");
    
    if (album.isMultiDiscAlbum()) {
      int discNr = album.getDiscs().indexOf(disc);
      buf.append(discNr).append("-");
    }
    
    int trackNr = trackReference.getTrackNr();
    buf.append(String.format("%02d", trackNr));
    
    buf.append(" - ");
    
    buf.append(trackReference.getTrack().getTitle());
    
    return buf.toString();
  }
}
