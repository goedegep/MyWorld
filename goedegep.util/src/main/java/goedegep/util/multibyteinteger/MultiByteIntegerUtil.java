package goedegep.util.multibyteinteger;

/**
 * 
 */
public class MultiByteIntegerUtil {

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
