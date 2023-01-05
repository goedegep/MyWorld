package goedegep.util.xtree;

/**
 * This enum defines the types of data that can be stored in an XTree.
 * <p>
 * Note: the numerical representation shall not be used.
 */
public enum XNodeDataType {
  /**
   * Node contains a Tag value.
   */
  TAG(1),
  
  /**
   * Node contains a Boolean value.
   */
  BOOLEAN(2),
  
  /**
   * Node contains a 32 bit signed integer value.
   */
  INTEGER(3),
  
  /**
   * Node contains an UTF-8 based String value.
   */
  STRING(4),
  
  /**
   * Node contains any amount of raw data (Binary Large OBject).
   */
  BLOB(5);

  /**
   * Numerical representation of the type.
   */
  private int value;
  
  /**
   * Constructor.
   * 
   * @param value numerical representation of the type.
   */
  private XNodeDataType(int value) {
    this.value = value;
  }

  /**
   * Get the numerical representation of the type.
   * 
   * @return the numerical representation of the type.
   */
  public int getValue() {
    return value;
  }
  
}
