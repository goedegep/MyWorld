package goedegep.util.bytearray;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ByteArrayUtilsTest {
  @Test
  public void compareBytesTest() {
    byte[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    byte[] array2 = {1, 1, 2, 2, 3, 4, 5, 6, 7, 7, 7, 7};
    
    assertEquals("Wrong compare result", 0, ByteArrayUtils.compareBytes(array1, 1, array2, 3, 6));
    assertEquals("Wrong compare result", 1, ByteArrayUtils.compareBytes(array1, 0, array2, 1, 5));
    assertEquals("Wrong compare result", -1, ByteArrayUtils.compareBytes(array2, 1, array1, 0, 5));
  }
  
  @Test
  public void toHexTest() {
    byte[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    byte[] array2 = {1, 1, 2, 2, 3, 4, 5, 6, (byte) 0xab, (byte) 0xcd, (byte) 0xef};
    
    assertEquals("Wrong compare result", "[ 01 02 03 04 05 06 07 08 09 ]", ByteArrayUtils.toHex(array1));
    assertEquals("Wrong compare result", "[ 01 01 02 02 03 04 05 06 ab cd ef ]", ByteArrayUtils.toHex(array2));
  }
}
