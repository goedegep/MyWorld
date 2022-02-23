package goedegep.util.bitset;

import java.util.BitSet;

public class BitSetUtil {
  
  /**
   * This is a Utility Class, so there's a private constructor so it cannot be instantiated.
   */
  private BitSetUtil() {
    
  }

  /**
   * Create a BitSet with an initial value.
   * 
   * @param value the value to store in the BitSet
   * @param nrOfBits the number of bits in which the value is to be represented.
   * @return a <b>BitSet</b> of <b>nrOfBits</b> bits, representing <b>value</b>.
   */
  public static BitSet integerToBitSet(int value, int nrOfBits) {
    BitSet bitSet = new BitSet();

    bitSetAddFixedSizeInteger(value, nrOfBits, bitSet, 0);

    return bitSet;
  }

  /**
   * Add a fixed size value to a BitSet.
   * 
   * @param value the value to be added.
   * @param nrOfBits the number of bits to be used to store the value.
   * @param bitSet the BitSet to which <b>value</b> is to be added.
   * @param startIndex the index in <b>bitSet</b> where <b>value</b> is to be added.
   * @return
   */
  public static int bitSetAddFixedSizeInteger(int value, int nrOfBits, BitSet bitSet, int startIndex) {
    int currentValue = value;
    
    for (int i = nrOfBits - 1; i >= 0; i--) {
      bitSet.set(startIndex + i, (currentValue % 2 == 1) ? true : false);
      currentValue = currentValue >> 1;
    }
    
    if (currentValue != 0) {
      throw new IllegalArgumentException("The value " + value + " doesn't fit in " + nrOfBits + " bits.");
    }

    return startIndex + nrOfBits;
  }
}
