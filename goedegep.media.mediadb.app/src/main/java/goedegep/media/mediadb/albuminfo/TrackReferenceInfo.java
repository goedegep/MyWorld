package goedegep.media.mediadb.albuminfo;

import goedegep.media.mediadb.model.Album;
import goedegep.media.mediadb.model.MediadbFactory;
import goedegep.media.mediadb.model.TrackReference;

/**
 * This class stores information on a TrackReference (element Reference within element Track in an album info file).
 * <p>
 * While parsing the XML file, when a TrackReference is encountered, the information is stored in a list of this TrackReferenceInfo.
 * When the whole file is read, the references are resolved.<br/>
 * The following information is stored:
 * <ul>
 * <li>
 * albumInfoFileName, lineNumber, column<br/>
 * The album info file, lineNumer and column of the reference. This is only used in error messages.
 * </li>
 * <li>
 * sourceAlbum<br/>
 * The album which contains the reference. This is not used for dereferencing, only to provide clear error messages.
 * </li>
 * <li>
 * sourceTrackReference<br/>
 * This is created in the constructor. It is added to the Tracks in the mediaDb.
 * The Track in this reference is filled in when the references are resolved.
 * </li>
 * <li>
 * referencedAlbumTitle, referencedDiscNr, referencedTrackNr<br/>
 * The actual reference: album title, disc number and track number.
 * </li>
 * </ul>
 * 
 */
public class TrackReferenceInfo {
  private static MediadbFactory FACTORY = MediadbFactory.eINSTANCE;

  private String albumInfoFileName;   // The AlbumInfo file which contained the reference. Only used for reporting problems.
  private int lineNumber;             // The line in the AlbumInfo file that contains the reference.
  private int column;                 // The column on lineNumber that contains the reference.
  private Album sourceAlbum;
  private TrackReference sourceTrackReference;
  private String referencedAlbumTitle;
  private int referencedDiscNr = -1;
  private int referencedTrackNr;
  
  public TrackReferenceInfo(Album sourceAlbum, String albumInfoFileName, int lineNumber, int column) {
    this.sourceAlbum = sourceAlbum;
    this.albumInfoFileName = albumInfoFileName;
    this.lineNumber = lineNumber;
    this.column = column;
    
    sourceTrackReference = FACTORY.createTrackReference();
  }

  public String getAlbumInfoFileName() {
    return albumInfoFileName;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public int getColumn() {
    return column;
  }

  public String getReferencedAlbumTitle() {
    return referencedAlbumTitle;
  }

  public void setReferencedAlbumTitle(String referencedAlbumTitle) {
    this.referencedAlbumTitle = referencedAlbumTitle;
  }

  public int getReferencedDiscNr() {
    return referencedDiscNr;
  }

  public void setReferencedDiscNr(int referencedDiscNr) {
    this.referencedDiscNr = referencedDiscNr;
  }

  public int getReferencedTrackNr() {
    return referencedTrackNr;
  }

  public void setReferencedTrackNr(int referencedTrackNr) {
    this.referencedTrackNr = referencedTrackNr;
  }

  public Album getSourceAlbum() {
    return sourceAlbum;
  }

  public TrackReference getSourceTrackReference() {
    return sourceTrackReference;
  }

  public void setSourceTrackReference(TrackReference sourceTrackReference) {
    this.sourceTrackReference = sourceTrackReference;
  }

  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("Reference in album: \"");
    buf.append(sourceAlbum.getTitle());
    buf.append("\" to AlbumTitle: \"");
    buf.append(referencedAlbumTitle);
    buf.append("\", DiscNr: ");
    buf.append(referencedDiscNr);
    buf.append(", TrackNr: ");
    buf.append(referencedTrackNr);

    return buf.toString();
  }
}
