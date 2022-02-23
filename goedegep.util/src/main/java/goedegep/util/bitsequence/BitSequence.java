package goedegep.util.bitsequence;

import java.util.Arrays;
import java.util.BitSet;
import java.util.logging.Logger;

/**
 * This class represents a sequence of bits.
 * 
 * Basic functionality is derived from java.util.BitSet
 * 
 */
public class BitSequence {
  private static final Logger LOGGER = Logger.getLogger(BitSequence.class.getName());

  /*
   * BitSets are packed into arrays of "words."  Currently a word is
   * a long, which consists of 64 bits, requiring 6 address bits.
   * The choice of word size is determined purely by performance concerns.
   */
  private final static int ADDRESS_BITS_PER_BYTE = 3;
  private final static int BITS_PER_BYTE = 1 << ADDRESS_BITS_PER_BYTE;
  private final static int HEIGHEST_BIT_INDEX_IN_BYTE = BITS_PER_BYTE - 1;


  /**
   * The array of bytes to store the "bits".
   */
  private byte[] bytes;

  private int length = 0;   // current length of the bit sequence (i.e. the number of bits).
  private int index = 0;    // next read/write location.

  
  /**
   * Constructor to create an empty BitSequence.
   */
  public BitSequence() {
    bytes = new byte[50];
  }
  
  /**
   * Constructor where the buffer is filled with a byte array.
   *
   * @param data the byte array to fill the BitSequence.
   */
  public BitSequence(byte[] data) {
    bytes = data;
    length = data.length * BITS_PER_BYTE;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * Set the bit at the specified index to {@code true}.
   *
   * @param  bitIndex a bit index
   * @throws IndexOutOfBoundsException if the specified index is negative
   */
  public void set(int bitIndex) {
      if (bitIndex < 0)
          throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);

      int byteIndex = byteIndex(bitIndex);
      ensureCapacity(byteIndex);
      
      int bitInByteIndex = bitInByteIndex(bitIndex);

      bytes[byteIndex] |= (1L << bitInByteIndex);
      
      if (bitIndex + 1 > length) {
        length = bitIndex + 1;
      }
  }

  /**
   * Sets the bit specified by the index to {@code false}.
   *
   * @param  bitIndex the index of the bit to be cleared
   * @throws IndexOutOfBoundsException if the specified index is negative
   * @since  JDK1.0
   */
  public void clear(int bitIndex) {
      if (bitIndex < 0)
          throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);

      int byteIndex = byteIndex(bitIndex);
      ensureCapacity(byteIndex);

      int bitInByteIndex = bitInByteIndex(bitIndex);
      bytes[byteIndex] &= ~(1L << bitInByteIndex);

      if (bitIndex + 1 > length) {
        length = bitIndex + 1;
      }
  }

  /**
   * Sets the bit at the specified index to the specified value.
   *
   * @param  bitIndex a bit index
   * @param  value a boolean value to set
   * @throws IndexOutOfBoundsException if the specified index is negative
   * @since  1.4
   */
  public void set(int bitIndex, boolean value) {
      if (value)
          set(bitIndex);
      else
          clear(bitIndex);
  }

  /**
   * Sets the bit at the specified index to the specified value.
   *
   * @param  bitIndex a bit index
   * @param  value an integer value to set
   * @throws IndexOutOfBoundsException if the specified index is negative
   */
  public void set(int bitIndex, int value) {
    if (value == 1) {
      set(bitIndex);
    } else if (value == 0) {
      clear(bitIndex);
    } else {
      throw new IllegalArgumentException("Illegal value: " + value);
    }
  }

  /**
   * Returns the value of the bit with the specified index. The value
   * is {@code true} if the bit with the index {@code bitIndex}
   * is currently set in this {@code BitSet}; otherwise, the result
   * is {@code false}.
   *
   * @param  bitIndex   the bit index
   * @return the value of the bit with the specified index
   * @throws IndexOutOfBoundsException if the specified index is negative
   */
  public boolean get(int bitIndex) {
      if (bitIndex < 0)
          throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);

      int byteIndex = byteIndex(bitIndex);
      int bitInByteIndex = bitInByteIndex(bitIndex);
      return (bytes[byteIndex] & (1L << bitInByteIndex)) != 0;
  }
  
  /**
   * Read the value of the bit at {@code index}.
   * 
   * @return 1 is the bit is set, 0 otherwise.
   */
  public int getBitAsInt() {
    return get(index++) ? 1 : 0;
  }

  /**
   * Given a bit index, return word index containing it.
   */
  private static int byteIndex(int bitIndex) {
      return bitIndex >> ADDRESS_BITS_PER_BYTE;
  }
  
  private static int bitInByteIndex(int bitIndex) {
    return HEIGHEST_BIT_INDEX_IN_BYTE - (bitIndex % BITS_PER_BYTE);
  }

  /**
   * Ensures that the BitSet can hold enough words.
   * @param wordsRequired the minimum acceptable number of words.
   */
  private void ensureCapacity(int byteIndex) {
    int bytesRequired = byteIndex + 1;
      if (bytes.length < bytesRequired) {
          // Allocate larger of doubled size or required size
          int request = Math.max(2 * bytes.length, bytesRequired);
          bytes = Arrays.copyOf(bytes, request);
      }
  }
  
  /**
   * Add the values of an array of bytes to the BitSequence.
   * Writing starts at {@code index} and the index is increased with the number of bits written.
   * 
   * @param bytes the bytes to be added.
   */
  public void addByteArray(byte[] bytes) {
    LOGGER.fine("Before adding byte array: length =" + length);
    for (byte byteValue: bytes) {
      addByte(byteValue);
    }
    LOGGER.fine("After adding byte array: length =" + length);
  }
  
  /**
   * Add the bits of a byte to the BitSequence.
   * Writing starts at {@code index} and the index is increased with the number of bits written.
   * 
   * @param byteValue the value to be added.
   */
  public void addByte(byte byteValue) {
    int intValue = byteValue;
    for (int mask = 0x80; mask > 0; mask >>= 1) {
      set(index++, (intValue & mask) != 0);
    }
  }

  /**
   * Add the bits from a BitSet to the BitSequence.
   * Writing starts at {@code index} and the index is increased with the number of bits written.
   * 
   * @param bitSet the BitSet from which the bits have to be added.
   * @param bitSetSize the size of the source BitSet (i.e. the number of bits to be added).
   */
  public void addBitSet(BitSet bitSet, int bitSetSize) {
    for (int i = 0; i < bitSetSize; i++) {
      set(index++, bitSet.get(i));
    }
  }

  /**
   * Add the bits from one BitSequence to the BitSequence.
   * Writing starts at {@code index} and the index is increased with the number of bits written.
   * 
   * @param bitSequence the BitSequence from which the bits have to be added.
   */
  public void addBitSequence(BitSequence bitSequence) {
    LOGGER.fine("=> bitSequence=" + bitSequence.toString());

    for (int i = 0; i < bitSequence.length; i++) {
      set(index++, bitSequence.get(i));
    }

    LOGGER.fine("<= length=" + length);
  }

  /**
   * Add a fixed size integer value.
   * Writing starts at {@code index} and the index is increased with the number of bits written.
   * 
   * @param value the value to be added.
   * @param nrOfBits the number of bits to be used to store the value.
   */
  public void addFixedSizeInteger(int value, int nrOfBits) {
    LOGGER.fine("=> value=" + value + ", nrOfBits=" + nrOfBits);
    
    int currentValue = value;
    
    for (int i = nrOfBits - 1; i >= 0; i--) {
      set(index + i, currentValue % 2);
      currentValue = currentValue >> 1;
    }
    
    if (currentValue != 0) {
      throw new IllegalArgumentException("The value " + value + " doesn't fit in " + nrOfBits + " bits.");
    }
    
    index += nrOfBits;

    LOGGER.fine("<= length=" + length);
    toByteArray();
  }
  
  /**
   * Read a fixed size integer value.
   * Reading starts at {@code index} and the index is increased with the number of bits read.
   * 
   * @param nrOfBits the number of bits to read.
   * @return the integer value read.
   */
  public int readFixedSizeInteger(int nrOfBits) {
    int value = 0;
    
    if (index + nrOfBits > length) {
      throw new IllegalArgumentException("nrOfBits out of bounds");
    }
    
    while (nrOfBits-- > 0) {
      value = (value << 1) + getBitAsInt();
    }
    
    return value;
  }

  public byte[] toByteArray() {
    LOGGER.fine("=> Length of byte array=" + length);
    int bytesInUse = (length + BITS_PER_BYTE - 1) / BITS_PER_BYTE;
    LOGGER.fine("bytesInUse=" + bytesInUse);
    
    if (bytesInUse == 0) {
      return new byte[0];
    }
    
    byte[] result = Arrays.copyOf(bytes, bytesInUse);
        
    LOGGER.fine("<= result.length=" + result.length);
    return result;
  }
  
  /**
   * Get a String representation of this BitSequence.
   * 
   * @param bitSet the BitSet to be represented as a String.
   * @param bitSetSize the number of bits in <b>bitSet</b>.
   * @return a String representation of <b>bitSet</b>.
   */
  @Override
  public String toString() {
    LOGGER.fine("=> Length=" + length);
    StringBuilder buf = new StringBuilder();

    if (length == 0) {
      buf.append("<empty>");
    } else {
      for (int i = 0; i < length; i++) {
        if ((i != 0)  &&  (i % 8 == 0)) {
          buf.append(".");
        }
        buf.append(get(i) ? "1" : "0");
      }
    }

    return buf.toString();
  }

}
