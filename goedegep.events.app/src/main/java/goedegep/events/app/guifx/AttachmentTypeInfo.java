package goedegep.events.app.guifx;


public enum AttachmentTypeInfo {
  FILE("file", "File"),
  PHOTO_FOLDER("photoFolder", "Photo folder"),
  VIDEO_TAKES_FOLDER("videoTakesFolder", "Video takes folder");
  
  /**
   * A string to refer to this type in your code.
   */
  private String tag;
  
  /**
   * The text shown in the GUI.
   */
  private String displayName;
  
  /**
   * @param tag A string to refer to this type in your code.
   * @param displayName The text shown in the GUI.
   */
  AttachmentTypeInfo(String tag, String displayName) {
    this.tag = tag;
    this.displayName = displayName;
  }

  public String getTag() {
    return tag;
  }

  public String getDisplayName() {
    return displayName;
  }

}
