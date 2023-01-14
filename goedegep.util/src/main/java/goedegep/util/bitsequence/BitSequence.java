package goedegep.util.bitsequence;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
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
  private static final int ADDRESS_BITS_PER_WORD = 6;
//  private final static int BITS_PER_BYTE = 1 << ADDRESS_BITS_PER_BYTE;
//  private final static int HEIGHEST_BIT_INDEX_IN_BYTE = BITS_PER_BYTE - 1;


  /**
   * The number of words in the logical size of this BitSet.
   */
  private transient int wordsInUse = 0;

  /**
   * The array of "words" to store the "bits".
   */
  private long[] words;

  /**
   * current logical length of the bit sequence (i.e. the number of bits).
   */
  private int length = 0;
  
  /**
   * next read/write location.
   */
  private int position = 0;

  
  /**
   * Constructor to create an empty BitSequence.
   */
  public BitSequence() {
    words = new long[50];
  }

  /**
   * Constructor to create a BitSequence from a byte array (typically representing a BitSequence).
   * 
   * @param bytes Initial content of the BitSequence.
   */
  public BitSequence(byte[] bytes) {
      this(ByteBuffer.wrap(bytes));
  }

  /**
   * Constructor to create a BitSequence from a ByteBuffer, using the bytes
   * between its position and limit.
   * <p>
   * The byte buffer is not modified by this method, and no
   * reference to the buffer is retained by the BitSequence.
   *
   * @param bb a byte buffer containing a little-endian representation
   *        of a sequence of bits between its position and limit, to be
   *        used as the initial bits of the new BitSequence
   */
  public BitSequence(ByteBuffer bb) {
    bb = bb.slice().order(ByteOrder.LITTLE_ENDIAN);
    int n = bb.remaining();
    length = n * 8;
    words = new long[(n + 7) / 8];
    wordsInUse = words.length;
    int i = 0;
    while (bb.remaining() >= 8) {
      words[i++] = bb.getLong();
    }
    for (int remaining = bb.remaining(), j = 0; j < remaining; j++) {
      words[i] |= (bb.get() & 0xffL) << (8 * j);
    }
  }
  
  /**
   * Get the current read/write position.
   * 
   * @return the current read/write position.
   */
  public int getPosition() {
    return position;
  }

  /**
   * Set the read/write position.
   * 
   * @param position the new read/write position.
   */
  public void setPosition(int position) {
    this.position = position;
  }
  
  public int getLength() {
    return length;
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

    int wordIndex = wordIndex(bitIndex);
    expandTo(wordIndex);

    // If the promoted type of the left-hand operand is long, then only the six lowest-order bits of the right-hand operand are used as the shift distance.
    // It is as if the right-hand operand were subjected to a bitwise logical AND operator & (§15.22.1) with the mask value 0x3f (0b111111).
    // The shift distance actually used is therefore always in the range 0 to 63, inclusive.
    words[wordIndex] |= (1L << bitIndex);

    if (bitIndex + 1 > length) {
      length = bitIndex + 1;
    }
  }

  /**
   * Sets the bit specified by the index to {@code false}.
   *
   * @param  bitIndex the index of the bit to be cleared
   * @throws IndexOutOfBoundsException if the specified index is negative
   */
  public void clear(int bitIndex) {
      if (bitIndex < 0)
          throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
      
      int wordIndex = wordIndex(bitIndex);
      expandTo(wordIndex);

      words[wordIndex] &= ~(1L << bitIndex);

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
   * @param  bitIndex the bit index
   * @return the value of the bit with the specified index
   * @throws IndexOutOfBoundsException if the specified index is negative
   */
  public boolean get(int bitIndex) {
      if (bitIndex < 0)
          throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
      
      if (bitIndex > length - 1) {
        throw new IndexOutOfBoundsException("bitIndex = " + bitIndex + ", length = " + length);
      }
      
      int wordIndex = wordIndex(bitIndex);
      return (words[wordIndex] & (1L << bitIndex)) != 0;
  }
  
  /**
   * Read the value of the bit at {@code index}.
   * 
   * @return 1 is the bit is set, 0 otherwise.
   */
  public int getBitAsInt() {
    return get(position++) ? 1 : 0;
  }
  
  /**
   * Add the values of an array of bytes to the BitSequence.
   * <p>
   * Writing starts at {@code position} and the position is increased with the number of bits written.
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
   * Writing starts at {@code position} and the {@code position} is increased with the number of bits written.
   * 
   * @param byteValue the value to be added.
   */
  public void addByte(byte byteValue) {
    int intValue = byteValue;
    for (int mask = 0x80; mask > 0; mask >>= 1) {
      set(position++, (intValue & mask) != 0);
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
      set(position++, bitSequence.get(i));
    }
    if (position > length) {
      length = position;
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
      set(position + i, currentValue % 2);
      currentValue = currentValue >> 1;
    }
    
    if (currentValue != 0) {
      throw new IllegalArgumentException("The value " + value + " doesn't fit in " + nrOfBits + " bits.");
    }
    
    position += nrOfBits;

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
    
    if (position + nrOfBits > length) {
      throw new IllegalArgumentException("nrOfBits out of bounds");
    }
    
    while (nrOfBits-- > 0) {
      value = (value << 1) + getBitAsInt();
    }
    
    return value;
  }

  /**
   * Get the BitSequence as a byte array.
   * 
   * @return the BitSequence as a byte array.
   */
  public byte[] toByteArray() {
    int n = wordsInUse;
    if (n == 0)
        return new byte[0];
    int len = 8 * (n-1);
    for (long x = words[n - 1]; x != 0; x >>>= 8)
        len++;
    byte[] bytes = new byte[len];
    ByteBuffer bb = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
    for (int i = 0; i < n - 1; i++)
        bb.putLong(words[i]);
    for (long x = words[n - 1]; x != 0; x >>>= 8)
        bb.put((byte) (x & 0xff));
    return bytes;
  
  }
  
  /**
   * Write the BitSequence to a file.
   * <p>
   * The bytes representing the BitSequence (as can be obtained via {@link #toByteArray()} are written the
   * file with the specified name.
   * 
   * @param filename Name of the file to which the information is written.
   * @throws FileNotFoundException
   * @throws IOException
   */
  public void write(String filename) throws FileNotFoundException, IOException {
    try (FileOutputStream os = new FileOutputStream(filename)) {
      os.write(toByteArray());
    }
  }

  /**
   * Given a bit index, return word index containing it.
   */
  private static int wordIndex(int bitIndex) {
      return bitIndex >> ADDRESS_BITS_PER_WORD;
  }

  /**
   * Ensures that the BitSet can accommodate a given wordIndex,
   * temporarily violating the invariants.  The caller must
   * restore the invariants before returning to the user,
   * possibly using recalculateWordsInUse().
   * @param wordIndex the index to be accommodated.
   */
  private void expandTo(int wordIndex) {
      int wordsRequired = wordIndex+1;
      if (wordsInUse < wordsRequired) {
          ensureCapacity(wordsRequired);
          wordsInUse = wordsRequired;
      }
  }

  /**
   * Ensures that the BitSet can hold enough words.
   * @param wordsRequired the minimum acceptable number of words.
   */
  private void ensureCapacity(int wordsRequired) {
      if (words.length < wordsRequired) {
          // Allocate larger of doubled size or required size
          int request = Math.max(2 * words.length, wordsRequired);
          words = Arrays.copyOf(words, request);
      }
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
