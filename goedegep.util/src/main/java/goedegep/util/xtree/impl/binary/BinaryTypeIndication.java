package goedegep.util.xtree.impl.binary;


import java.util.logging.Logger;

import goedegep.util.bitsequence.BitSequence;

/**
 * This enum defines the values for the data types in a binary serialized XTree.
 */
public enum BinaryTypeIndication {
  BOOLEAN_TRUE(1, 0),
  BOOLEAN_FALSE(1, 1),
  TAG(1, 2),
  POSITIVE_INTEGER(1, 3),
  NEGATIVE_INTEGER(1, 4),
  INTEGER_ZERO(1, 5),
  INTEGER_ONE(1, 6),
  STRING(2, 1),
  BLOB(2, 2),
  INTEGER_TWO(2, 3),
  INTEGER_THREE(2, 4),
  INTEGER_FOUR(2, 5),
  INTEGER_MINUS_ONE(2, 6);
  
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(BinaryTypeIndication.class.getName());

  private int value;
  private int group;
  private BitSequence bitSequence;
  
  private static final int NR_OF_BITS_BINARY_TYPE_INDICATION = 3;
  private static final int BINARY_VALUE_TYPE_INDICATION_ESCAPE = 7;
  
  BinaryTypeIndication(int group, int value) {
    bitSequence = new BitSequence();
    this.value = value;
    if (group == 1) {
      this.group = 1;
      bitSequence.addFixedSizeInteger(value, NR_OF_BITS_BINARY_TYPE_INDICATION);
    } else {
      this.group = 2;
      bitSequence.addFixedSizeInteger(BINARY_VALUE_TYPE_INDICATION_ESCAPE, NR_OF_BITS_BINARY_TYPE_INDICATION);
      bitSequence.addFixedSizeInteger(value, NR_OF_BITS_BINARY_TYPE_INDICATION);
    }
  }
  
  public static int getNrOfBitsBinaryTypeIndication() {
    return NR_OF_BITS_BINARY_TYPE_INDICATION;
  }

  public int getValue() {
    return value;
  }

  public BitSequence getBitSequence() {
    return bitSequence;
  }

  public static BinaryTypeIndication getBinaryTypeIndicationForValues(int group, int value) {
    for (BinaryTypeIndication binaryTypeIndication: values()) {
      if ((binaryTypeIndication.group == group)  &&  (binaryTypeIndication.value == value)) {
        return binaryTypeIndication;
      }
    }
    
    throw new IllegalArgumentException("Unknown group/value combination. group=" + group + ", value=" + value);
  }
  
  public static BinaryTypeIndication readFromBitSequence(BitSequence bitSequence) {
    int group;
    int value;
    int binaryTypeIndicationValue = bitSequence.readFixedSizeInteger(NR_OF_BITS_BINARY_TYPE_INDICATION);
    
    if (binaryTypeIndicationValue == BINARY_VALUE_TYPE_INDICATION_ESCAPE) {
      group = 2;
      value = bitSequence.readFixedSizeInteger(NR_OF_BITS_BINARY_TYPE_INDICATION);
    } else {
      group = 1;
      value = binaryTypeIndicationValue;
    }
    
    return getBinaryTypeIndicationForValues(group, value);
  }
}
