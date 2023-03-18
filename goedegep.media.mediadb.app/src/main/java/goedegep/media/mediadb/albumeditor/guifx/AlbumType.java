package goedegep.media.mediadb.albumeditor.guifx;

public enum AlbumType {
  COMPILATION("Compilation"),
  NORMAL("Normal"),
  OWN_COMPILATION("Own compilation"),
  SOUNDTRACK("Soundtrack"),
  VARIOUS_ARTISTS("Various artists");
  
  private String displayName;
  
  AlbumType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
  
  public String toString() {
    return displayName;
  }
}
