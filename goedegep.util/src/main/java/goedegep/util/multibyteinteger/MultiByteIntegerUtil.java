package goedegep.util.multibyteinteger;

/**
 * This class provides support for a multibyte integer.
 * <p>
 * A multibyte integer is an array of bytes. In each byte the most significant bit indicates whether there is a next byte or not.
 * If the MSB is set, there is a next byte.
 * The other 7 bits provide a value. The nominal value if the value of the 7 bits, shifted left (7 * the index of the byte).
 * The value of all bytes together is the value of the multibyte integer.
 */
public class MultiByteIntegerUtil {

  /**
   * Create a multibyte integer for an int.
   * 
   * @param value the int value for which a multibyte integer is to be created.
   * @return a multibyte integer with the value of {@code value}.
   */
  public static byte[] integerToMultiByteInteger(int value) {
    int remainingValue = value;
    
    int nrOfBytes = 1;
    while (remainingValue > 0x7f) {
      nrOfBytes++;
      remainingValue >>= 7;
    }
    
    byte[] buf = new byte[nrOfBytes];
    
    remainingValue = value;
    int index = 0;
    do {
      int byteValue = remainingValue & 0x7f;
      remainingValue >>= 7;
      if (remainingValue != 0) {
        byteValue |= 0x80;  // indicates more bytes follow.
      }
      buf[index++] = ((byte) byteValue);
    } while (remainingValue != 0);
    
    return buf;
  }
  
  /**
   * Get the int value of a multibyte integer.
   * 
   * @param byteSupplier Supplies the bytes of the multibyte integer
   * @return the int value for the byte supplies by the {@code byteSupplier}.
   */
  public static int parseMultiByteInteger(ByteSupplier byteSupplier) {
    int value = 0;
    int byteValue;
    int i = 0;
    
    do {
      byteValue = byteSupplier.getNextByte();
      value |= ((byteValue & 0x7f) << (7 * i++));
    } while ((byteValue & 0x80) != 0);

    return value;
  }
}
