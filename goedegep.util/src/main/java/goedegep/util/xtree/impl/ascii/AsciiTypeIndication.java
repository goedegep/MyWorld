package goedegep.util.xtree.impl.ascii;

public enum AsciiTypeIndication {
  BOOLEAN_TRUE('T'),
  BOOLEAN_FALSE('F'),
  TAG('G'),
  INTEGER('I'),
  STRING('S'),
  BLOB('D');
  
  private byte value;
  
  private AsciiTypeIndication(char value) {
    this.value = (byte) value;
  }
  
  public byte getValue() {
    return value;
  }

  public static AsciiTypeIndication getAsciiTypeIndicationForValue(byte asciiTypeIndicationValue) {
    for (AsciiTypeIndication asciiTypeIndication: values()) {
      if (asciiTypeIndication.getValue() == asciiTypeIndicationValue) {
        return asciiTypeIndication;
      }
    }
    
    throw new IllegalArgumentException(asciiTypeIndicationValue + " is not a valid value for an AsciiTypeIndication.");
  }

}
