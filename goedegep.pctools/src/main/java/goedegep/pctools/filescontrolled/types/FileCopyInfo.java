package goedegep.pctools.filescontrolled.types;

/**
 * This class stores information about probable files copies.
 * <p>
 * The information consists of:
 * <ul>
 * <li>information on the first file found</li>
 * <li>information on the second file found</li>
 * <li>the {@link EqualityType}.
 * </ul>
 */
public class FileCopyInfo implements Comparable<FileCopyInfo> {
  private static final String NEWLINE = System.getProperty("line.separator");

  /**
   * Information on the first file found
   */
  private FileInfo firstFileFoundInfo = null;
  
  /**
   * Information on the second file found
   */
  private FileInfo secondFileFoundInfo = null;
  
  /**
   * the {@link EqualityType}.
   */
  private EqualityType equalityType = null;
  
  /**
   * Get the information for the first file found.
   * 
   * @return the information for the first file found.
   */
  public FileInfo getFirstFileFoundInfo() {
    return firstFileFoundInfo;
  }
  
  /**
   * Set the information for the first file found.
   * @param firstFileFoundInfo the information for the first file found.
   */
  public void setFirstFileFoundInfo(FileInfo firstFileFoundInfo) {
    this.firstFileFoundInfo = firstFileFoundInfo;
  }
  
  /**
   * Get the information for the second file found.
   * 
   * @return the information for the second file found.
   */
  public FileInfo getSecondFileFoundInfo() {
    return secondFileFoundInfo;
  }
  
  /**
   * Set the information for the second file found.
   * @param secondFileFoundInfo the information for the second file found.
   */
  public void setSecondFileFoundInfo(FileInfo secondFileFoundInfo) {
    this.secondFileFoundInfo = secondFileFoundInfo;
  }
  
  /**
   * Get the EqualityType.
   * 
   * @return the EqualityType.
   */
  public EqualityType getEqualityType() {
    return equalityType;
  }

  /**
   * Set the EqualityType.
   * 
   * @param equalityType the EqualityType.
   */
  public void setEqualityType(EqualityType equalityType) {
    this.equalityType = equalityType;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("Equality type: " + equalityType.name());
    buf.append(NEWLINE);
    buf.append("First file found: ");
    buf.append(firstFileFoundInfo.toString());
    buf.append(NEWLINE);
    buf.append("Second file found: ");
    buf.append(secondFileFoundInfo.toString());
    
    return buf.toString();
  }
  
  @Override
  public boolean equals(Object object) {
    return compareTo((FileCopyInfo) object) == 0;
  }

  @Override
  public int compareTo(FileCopyInfo fileCopyInfo) {
    int result = firstFileFoundInfo.compareTo(fileCopyInfo.getFirstFileFoundInfo());
    if (result == 0) {
      result = equalityType.compareTo(fileCopyInfo.getEqualityType());
    }
    
    return result;
  }
}
