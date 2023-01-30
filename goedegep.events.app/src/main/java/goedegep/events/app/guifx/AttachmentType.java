package goedegep.events.app.guifx;

import goedegep.jfx.FileReferenceTypeInfo;

public enum AttachmentType implements FileReferenceTypeInfo {
  /**
   * Any type of file
   */
  FILE("file", "File", false),
  
  /**
   * A folder containing photos
   */
  PHOTO_FOLDER("photoFolder", "Photo folder", true),
  
  /**
   * A folder containing video takes
   */
  VIDEO_TAKES("videoTakesFolder", "Video takes folder", true);
  
  private String tag;
  private String displayName;
  private boolean isFolder;
  
  AttachmentType(String tag, String displayName, boolean isFolder) {
    this.tag = tag;
    this.displayName = displayName;
    this.isFolder = isFolder;
  }
  
  public String getTag() {
    return tag;
  }
  
  public String getDisplayName() {
    return displayName;
  }
  
  public boolean isFolder() {
    return isFolder;
  }
  
  public AttachmentType getAttachmentTypeForTag(String tag) {
    for (AttachmentType attachmentType: values()) {
      if (attachmentType.tag.equals(tag)) {
        return attachmentType;
      }
    }
    
    throw new RuntimeException("Unknown tag: " + tag);
  }
}
