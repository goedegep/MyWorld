package goedegep.events.app.guifx;

import goedegep.jfx.FileReferenceTypeInfo;

/**
 * This enum defines the reference types supported for an event.
 */
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
  
  /**
   * Constructor.
   * 
   * @param tag the identifier for the type.
   * @param displayName the display name for this file attachment type.
   * @param isFolder indication of whether the reference if to a file or folder.
   */
  AttachmentType(String tag, String displayName, boolean isFolder) {
    this.tag = tag;
    this.displayName = displayName;
    this.isFolder = isFolder;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getTag() {
    return tag;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getDisplayName() {
    return displayName;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFolder() {
    return isFolder;
  }
  
  /**
   * Get the attachment type for a specific 'tag'.
   * 
   * @param tag the identifier for an attachment type.
   * @return the {@code AttachmentType} for the {@code tag}.
   */
  public AttachmentType getAttachmentTypeForTag(String tag) {
    for (AttachmentType attachmentType: values()) {
      if (attachmentType.tag.equals(tag)) {
        return attachmentType;
      }
    }
    
    throw new RuntimeException("Unknown tag: " + tag);
  }
}
