package goedegep.media.mediadb.albuminfo;

import goedegep.media.mediadb.model.Album;

public class AlbumReferenceInfo {
  private String albumInfoFileName;   // The AlbumInfo file which contained the reference. Only used for reporting problems.
  private int lineNumber;             // The line in the AlbumInfo file that contains the reference.
  private int column;                 // The column on lineNumber that contains the reference.
  private Album sourceAlbum;          // The album which contains the reference.
  private String referencedAlbum;     // The titel of the referred album.
  
  public AlbumReferenceInfo(String albumInfoFileName, int lineNumber, int column, Album sourceAlbum, String referencedAlbum) {
    this.albumInfoFileName = albumInfoFileName;
    this.lineNumber = lineNumber;
    this.column = column;
    this.sourceAlbum = sourceAlbum;
    this.referencedAlbum = referencedAlbum;
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

  public Album getSourceAlbum() {
    return sourceAlbum;
  }

  public String getReferencedAlbum() {
    return referencedAlbum;
  }
  
}
