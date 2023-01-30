package goedegep.util.bytearray;


/**
 * This is a utility class, with utility methods related to bytes and byte arrays. It provides methods to represent bytes and byte arrays as strings.
 */
public final class ByteArrayUtils {
  
  /**
   * A private constructor as this is a utility class.
   */
  private ByteArrayUtils() {
  }

  /**
   * Convert a byte array to a human readable string, showing the byte values as hexadecimal numbers.
   * <p>
   * The result is similar to {@link toString()}, but instead of decimal values, hexadecimal values are used.
   * 
   * @param bytes the byte array to be presented as string.
   * @return a textual representation of the {@code bytes}
   */
  public static String toHexString(byte[] bytes) {
    StringBuilder buf= new StringBuilder("[");
    for (int i = 0; i < bytes.length; i++) {
      if (i != 0) {
        buf.append(", ");
      }
      buf.append(getHex(bytes[i]));
    }
    buf.append(']');
    return buf.toString();
  }
  
  /**
   * Convert a byte array to a human readable string, showing the byte values as characters.
   * <p>
   * The result is similar to {@link toString()}, but instead of decimal values, characters are used.
   * 
   * @param bytes the byte array to be presented as string.
   * @return a textual representation of the {@code bytes}
   */
  public static String toCharString(byte[] bytes) {
    StringBuilder buf = new StringBuilder("[");
    
    for (int i = 0; i < bytes.length; i++) {
      if (i != 0) {
        buf.append(", ");
      }
      char c = (char) bytes[i];
      buf.append(c);
    }
    
    buf.append(']');
    
    return buf.toString();
  }

  /**
   * Convert a byte to a 2 character hexadecimal string.
   * 
   * @param b the byte to be represented as hexadecimal string
   * @return a 2 character hexadecimal string representation of {@code b}.
   */
  public static String getHex(byte b) {
      char char0, char1;
      byte byte1= (byte) (0x0f & (b >> 4));
      byte byte2= (byte) (0x0f & b);
      char0 = (char) (byte1 > 9 ? 'a' + byte1 - 10 : '0' + byte1);
      char1 = (char) (byte2 > 9 ? 'a' + byte2 - 10 : '0' + byte2);
      return "" + char0 + char1;
  }
  
  /**
   * Convert a byte array to a binary string representation.
   * <p>
   * Each byte is printed on a separate line.
   * Each byte is printed as a sequence of 8 ones and/or zeros.
   * 
   * @param data the byte array to be converted.
   * @return a binary string representation of the {@code data}
   */
  public static String toBinaryString(byte[] data) {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < data.length; i++) {
      buf.append(byteToBinaryString(data[i]) + System.getProperty("line.separator"));
    }
    return buf.toString();
  }
  
  /**
   * Convert a byte to a string with 8 ones and zeros.
   * 
   * @param value the byte to be converted
   * @return {@code value} represented as a string with 8 ones and zeros
   */
  private static String byteToBinaryString(byte value) {
    StringBuffer buf = new StringBuffer();
    for (int i = 7; i >= 0; i--) {
      if ((value & ((byte) 0x01 << i)) == 0) {
        buf.append('0');
      } else {
        buf.append('1');
      }
    }
    return buf.toString();
    
  }
}
