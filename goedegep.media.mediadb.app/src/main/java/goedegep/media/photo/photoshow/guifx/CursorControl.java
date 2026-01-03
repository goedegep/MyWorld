package goedegep.media.photo.photoshow.guifx;

/**
 * Interface to let a MediumPresentationPanel control the cursor at application level.
 */
public interface CursorControl {
  /**
   * Set the cursor to a dragging cursor.
   */
  void setDraggingCursor();
  
  /**
   * Restore the cursor after dragging.
   */
  void restoreCursor();
}
