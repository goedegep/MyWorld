package goedegep.pctools.filescontrolled.types;

/**
 * This enum defines levels of 'equality'.
 * <p>
 * The following levels are defined:
 * <ul>
 * <li>SIZE - files have the same size, but have a different MD5.</li>
 * <li>MD5 - files have the same MD5 hash, but are not EQUAL (probably rare).</li>
 * <li>EQUAL - Files are equal, based on byte by byte compare. So they have also same MD5 and same size.</li>
 * </ul>
 */
public enum EqualityTypeOld {
  /**
   * files have the same size, but have a different MD5.
   */
  SIZE,
  
  /**
   * files have the same MD5 hash, but are not EQUAL.
   */
  MD5,
  
  /**
   * Files are equal, based on byte by byte compare. So they have also same MD5 and same size.
   */
  EQUAL
}
