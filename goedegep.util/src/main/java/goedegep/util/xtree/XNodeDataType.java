package goedegep.util.xtree;

public enum XNodeDataType {
  TAG(1),      // Node contains a Tag value.
  BOOLEAN(2),  // Node contains a Boolean value.
  INTEGER(3),  // Node contains a 32 bit signed integer value.
  STRING(4),   // Node contains a String value.
  BLOB(5);     // Node contains any amount of raw data (Binary Large OBject).

  private int value;
  
  private XNodeDataType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
  
}
