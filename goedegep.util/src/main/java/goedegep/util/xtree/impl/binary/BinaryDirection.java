package goedegep.util.xtree.impl.binary;

import goedegep.util.bitsequence.BitSequence;

/**
 * Direction indication, used in the binary serialized XTree format.
 *
 */
public enum BinaryDirection {
  CHILD(0),
  SIBLING(1),
  UP(2),
  END(3);
  
  private static final int NR_OF_BITS_BINARY_INDICATION_DIRECTION = 2;
  
  private int value;
  
  BinaryDirection(int value) {
    this.value = value;
  }
  
  public static int getNrOfBitsBinaryIndicationDirection() {
    return NR_OF_BITS_BINARY_INDICATION_DIRECTION;
  }

  public int getValue() {
    return value;
  }

  public static BinaryDirection getBinaryDirectionForValue(int binaryDirectionValue) {
    for (BinaryDirection binaryDirection: values()) {
      if (binaryDirection.getValue() == binaryDirectionValue) {
        return binaryDirection;
      }
    }
    
    throw new IllegalArgumentException(binaryDirectionValue + " is not a valid value for a BinaryDirection.");
  }
  
  public static BinaryDirection readFromBitSequence(BitSequence bitSequence) {
    int binaryDirectionValue = bitSequence.readFixedSizeInteger(NR_OF_BITS_BINARY_INDICATION_DIRECTION);
    
    return getBinaryDirectionForValue(binaryDirectionValue);
  }
}
