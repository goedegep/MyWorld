package goedegep.jfx;

/**
 * Via a list of this interface you can specify the types of file references to be handled by
 * a {@code FileReferencePanel}.<br/>
 * This interface provides the following information:
 * <ul>
 * <li>tag<br/>
 * A string to refer to this type in your code
 * </li>
 * <li>display name<br/>
 * The text shown in the GUI
 * </li>
 * <li>is folder?<br/>
 * To distinguish between file and folder references. Used to show a file or folder selecter.
 * </li>
 * </ul>
 * 
 */
public interface FileReferenceTypeInfo {
  /**
   * Get the display name for this file reference type.
   * 
   * @return the display name for this file reference type.
   */
  public String getDisplayName();
  
  /**
   * Indication of whether the reference if to a file or folder.
   * 
   * @return true for a folder reference, false for a file reference.
   */
  public boolean isFolder();
  
  /**
   * Get the identifier for this type.
   * <p>
   * This value shall of course be unique across the types you define.
   * 
   * @return the identifier for this type.
   */
  public String getTag();
}
