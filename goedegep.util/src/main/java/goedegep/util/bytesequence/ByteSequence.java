package goedegep.util.bytesequence;

import java.util.Arrays;
import java.util.logging.Logger;

import goedegep.util.multibyteinteger.ByteSupplier;

/**
 * This class represents a sequence of bytes.
 */
 public class ByteSequence implements ByteSupplier {
  private static final Logger LOGGER = Logger.getLogger(ByteSequence.class.getName());

  /**
   * The array of bytes.
   */
  private byte[] bytes;

  private int length = 0;   // current length of the byte sequence (i.e. the number of actually used bytes).
  private int index = 0;    // next read/write location.

  
  /**
   * Constructor to create an empty ByteSequence.
   */
  public ByteSequence() {
    bytes = new byte[50];
  }
  
  /**
   * Constructor where the buffer is provided as a byte array.
   * <p>
   * The provided byte array will become the buffer, so the application shouldn't use this array
   * anymore.
   *
   * @param data the byte array to set as byte buffer.
   */
  public ByteSequence(byte[] data) {
    bytes = data;
    length = data.length;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public void back() {
    index--;
  }

  public void advance() {
    index++;
  }

  /**
   * Set the byte at the specified index to a specific value.
   *
   * @param  byteIndex a byte index
   * @param value the value to set the byte, at index byteIndex, to.
   * @throws IndexOutOfBoundsException if the specified index is negative
   */
  public void set(int byteIndex, byte value) {
      if (byteIndex < 0)
          throw new IndexOutOfBoundsException("bitIndex < 0: " + byteIndex);

      ensureCapacity(byteIndex);
      
      bytes[byteIndex] = value;
      index = byteIndex + 1;
      
      if (byteIndex + 1 > length) {
        length = byteIndex + 1;
      }
  }


  /**
   * Returns the value of the byte with the specified index.
   *
   * @param  byteIndex   the bit index
   * @return the value of the bit with the specified index
   * @throws IndexOutOfBoundsException if the specified index is negative
   */
  public byte get(int byteIndex) {
      if (byteIndex < 0)
          throw new IndexOutOfBoundsException("bitIndex < 0: " + byteIndex);

      return bytes[byteIndex];
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
   * Add the values of an array of bytes to the ByteSequence.
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
   * Add a byte to the ByteSequence.
   * Writing starts at {@code index} and the index is increased by one.
   * 
   * @param byteValue the value to be added.
   */
  public void addByte(byte byteValue) {
    set(index++, byteValue);
  }


  /**
   * Add the bytes from one ByteSequence to the ByteSequence.
   * Writing starts at {@code index} and the index is increased with the number of bytes written.
   * 
   * @param byteSequence the ByteSequence from which the bytes have to be added.
   */
  public void addByteSequence(ByteSequence byteSequence) {
    LOGGER.fine("=> bitSequence=" + byteSequence.toString());

    for (int i = 0; i < byteSequence.length; i++) {
      set(index++, byteSequence.get(i));
    }

    LOGGER.fine("<= length=" + length);
  }


  public byte[] toByteArray() {
    LOGGER.fine("=> Length of byte array=" + length);
    
    if (length == 0) {
      return new byte[0];
    }
    
    byte[] result = Arrays.copyOf(bytes, length);
        
    LOGGER.fine("<= result.length=" + result.length);
    return result;
  }
  
  /**
   * Get a String representation of this ByteSequence.
   * 
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
        int value = (int) (bytes[i] & 0x7f);
        if (bytes[i] < 0) {
          value |= 0x80;
        }
        buf.append("0x");
        buf.append(Integer.toHexString(value));
        buf.append(" ");
      }
    }

    return buf.toString();
  }

  @Override
  public byte getNextByte() {
    return bytes[index++];
  }

  public byte peekNextByte() {
    return bytes[index];
  }

}
