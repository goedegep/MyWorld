package goedegep.util.xtree.impl.ascii;

public enum AsciiDirection {
  CHILD('-'),
  SIBLING('*'),
  UP('+'),
  END('!');
  
  private byte value;
  
  private AsciiDirection(char value) {
    this.value = (byte) value;
  }
  
  public byte getValue() {
    return value;
  }

  public static AsciiDirection getAsciiDirectionForValue(byte asciiDirectionValue) {
    for (AsciiDirection asciiDirection: values()) {
      if (asciiDirection.getValue() == asciiDirectionValue) {
        return asciiDirection;
      }
    }
    
    throw new IllegalArgumentException(asciiDirectionValue + " is not a valid value for an AsciiDirection.");
  }
}
