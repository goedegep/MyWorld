package goedegep.util.bytearraytest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import goedegep.util.bytearray.ByteArrayUtils;

public class ByteArrayUtilsTest {
  private static String NEWLINE = System.getProperty("line.separator");
  
  @Test
  public void compareBytesTest() {
    byte[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    byte[] array2 = {1, 1, 2, 2, 3, 4, 5, 6, 7, 7, 7, 7};
    assertEquals(0, Arrays.compare(array1, 1, 6, array2, 3, 8), "Wrong compare result");
    assertEquals(1, Arrays.compare(array1, 0, 4, array2, 1, 5), "Wrong compare result");
    assertEquals(-1, Arrays.compare(array2, 1, 5, array1, 0, 4), "Wrong compare result");
  }
  
  @Test
  public void toHexStringTest() {
    byte[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    byte[] array2 = {1, 1, 2, 2, 3, 4, 5, 6, (byte) 0xab, (byte) 0xcd, (byte) 0xef};
    
    assertEquals("[01, 02, 03, 04, 05, 06, 07, 08, 09]", ByteArrayUtils.toHexString(array1), "Wrong print result");
    assertEquals("[01, 01, 02, 02, 03, 04, 05, 06, ab, cd, ef]", ByteArrayUtils.toHexString(array2), "Wrong print result");
  }
  
  @Test
  public void toCharStringTest() {
    byte[] array1 = {'a', 'b', 'c','d', 'e', 'f', 'g', 'h', 'i'};
    
    assertEquals("[a, b, c, d, e, f, g, h, i]", ByteArrayUtils.toCharString(array1), "Wrong print result");
  }
  
  @Test
  public void toBinaryStringTest() {
    byte[] array2 = {1, 1, 2, 2, 3, 4, 5, 6, (byte) 0xab, (byte) 0xcd, (byte) 0xef};
    
    String expectedResult =
            "00000001" + NEWLINE +
            "00000001" + NEWLINE +
            "00000010" + NEWLINE +
            "00000010" + NEWLINE +
            "00000011" + NEWLINE +
            "00000100" + NEWLINE +
            "00000101" + NEWLINE +
            "00000110" + NEWLINE +
            "10101011" + NEWLINE +
            "11001101" + NEWLINE +
            "11101111" + NEWLINE;
    assertEquals(expectedResult, ByteArrayUtils.toBinaryString(array2), "Wrong print result");
  }
}
