package goedegep.util.bitsequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

/**
 * This class contains tests for the {@link BitSequence} class.
 */
public class BitSequenceTest {


  /**
   * Test the following:
   * <ul>
   * <li>Constructor for an empty bit sequence</li>
   * <li>Setting bits via set(index)</li>
   * <li>Clearing bits via clear(index)
   * <li>toString()</li>
   * </ul>
   */
  @Test
  public void setAndClearBitsTest() {
    BitSequence bitSequence = new BitSequence();
    String expectedResult;
    StringBuilder buf = new StringBuilder();

    bitSequence.set(0);
    expectedResult = "1";
    assertEquals(expectedResult, bitSequence.toString(), "Bit 0 not correctly set");

    bitSequence.set(14);
    expectedResult = """
            10000000.\
            0000001""";
    assertEquals(expectedResult, bitSequence.toString(), "Bit 14 not correctly set");

    bitSequence.set(500);
    // Build the expected result
    buf.setLength(0);
    buf.append("10000000.00000010.");  // first 2 bytes
    // 500 / 8 = 62 bytes and remainder of 4. So 63 bytes in total, so 60 zero bytes after the first 2 bytes.
    for (int i = 1; i <= 60; i++) {
      buf.append("00000000.");
    }
    buf.append("00001");  // and 5th bit set in last byte.
    assertEquals(buf.toString(), bitSequence.toString(), "Bit 500 not correctly set");

    bitSequence.clear(516);  // clearing a bit outside the set extends the sequence.    
    // Build the expected result
    buf.setLength(0);
    buf.append("10000000.00000010.");  // first 2 bytes
    // 500 / 8 = 62 bytes and remainder of 4. So 63 bytes in total, so 60 zero bytes after the first 2 bytes.
    for (int i = 1; i <= 60; i++) {
      buf.append("00000000.");
    }
    buf.append("00001000.");  // and 5th bit set in next byte, but now rest of byte has zeros.
    // 516 / 8 = 64 bytes and remainder of 12. So one more byte with 8 zeros and one more byte with 5 zeros.
    buf.append("00000000.00000");  // and 5th bit set in next byte, but now rest of byte has zeros.
    assertEquals(buf.toString(), bitSequence.toString(), "Bit 516 not correctly cleared");

    bitSequence.clear(400);  // clearing a bit that isn't set has no effect.
    assertEquals(buf.toString(), bitSequence.toString(), "Bit 400 not correctly cleared");

    bitSequence.clear(0);
    bitSequence.clear(500);
    // Build the expected result
    buf.setLength(0);
    buf.append("00000000.00000010.");  // first 2 bytes
    // 500 / 8 = 62 bytes and remainder of 4. So 63 bytes in total, so 60 zero bytes after the first 2 bytes.
    for (int i = 1; i <= 62; i++) {
      buf.append("00000000.");
    }
    buf.append("00000");  // and 5th bit set in next byte, but now rest of byte has zeros.
    assertEquals(buf.toString(), bitSequence.toString(), "Bits 0 and 500 not correctly cleared");
  }

  /**
   * Test constructing a bit sequence from a byte array.
   */
  @Test
  public void constructFromByteArrayTest() {
    // a sequence that fits in one word (long)
    byte[] bitSequenceBytes = {
        0b01000000, 0b00110000, 0b00001010, 0b01100101, (byte) 0b11111111, (byte) 0b10000001, 0b00001111};
    BitSequence bitSequence = new BitSequence(bitSequenceBytes);
    String expectedResult =
        "00000010.00001100.01010000.10100110.11111111.10000001.11110000";
    assertEquals(56, bitSequence.getLength(), "BitSequence has the wrong length");
    assertEquals(expectedResult, bitSequence.toString(), "BitSequence not correctly constructed");

    BitSequence bitSequenceClone = new BitSequence(bitSequence.toByteArray());
    assertEquals(56, bitSequenceClone.getLength(), "BitSequence has the wrong length");
    assertEquals(expectedResult, bitSequenceClone.toString(), "BitSequence not correctly constructed");

    // a sequence that needs more than one word (long)
    byte[] bitSequenceBytes2 = {
        0b01000000, 0b00110000, 0b00001010, 0b01100101, (byte) 0b11111111, (byte) 0b10000001, 0b00001111, (byte) 0b10101010, 0b01010101};
    bitSequence = new BitSequence(bitSequenceBytes2);
    expectedResult =
        "00000010.00001100.01010000.10100110.11111111.10000001.11110000.01010101.10101010";
    assertEquals(72, bitSequence.getLength(), "BitSequence has the wrong length");
    assertEquals(expectedResult, bitSequence.toString(), "BitSequence not correctly constructed");

    bitSequenceClone = new BitSequence(bitSequence.toByteArray());
    assertEquals(72, bitSequenceClone.getLength(), "BitSequence has the wrong length");
    assertEquals(expectedResult, bitSequenceClone.toString(), "BitSequence not correctly constructed");
  }

  /**
   * Test getPosition(), setPosition(), getLength(), addFixedSizeInteger() and 
   */
  @Test
  public void positionAndLengthTest() {
    BitSequence bitSequence = new BitSequence();
    
    bitSequence.setPosition(90);
    assertEquals(90, bitSequence.getPosition(), "Wrong position");
    assertEquals(0, bitSequence.getLength(), "Wrong length");
    
    bitSequence.addFixedSizeInteger(0b1001, 5);
    assertEquals(95, bitSequence.getPosition(), "Wrong position");
    assertEquals(95, bitSequence.getLength(), "Wrong length");
    
    bitSequence.setPosition(90);
    int i = bitSequence.readFixedSizeInteger(5);
    assertEquals(0b1001, i, "Wrong value");
    assertEquals(95, bitSequence.getPosition(), "Wrong position");
  }
  
  /**
   * Test addByte().
   */
  @Test
  public void addByteTest() {
    BitSequence bitSequence = new BitSequence();
    
    bitSequence.setPosition(90);
    bitSequence.addByte((byte) 0b10010110);
    assertEquals(98, bitSequence.getLength(), "Wrong length");
    String expectedResult =
        "00000000.00000000.00000000.00000000.00000000.00000000.00000000.00000000.00000000.00000000.00000000.00100101.10";
    assertEquals(expectedResult, bitSequence.toString(), "BitSequence not correctly constructed");
    
  }
  
  /**
   * Test addByteArray().
   */
  @Test
  public void addByteArrayTest() {
    BitSequence bitSequence = new BitSequence();
    
    bitSequence.setPosition(90);
    bitSequence.addByteArray(new byte[] {(byte) 0b10010110, 0b00010100});
    assertEquals(106, bitSequence.getLength(), "Wrong length");
    String expectedResult =
        "00000000.00000000.00000000.00000000.00000000.00000000.00000000.00000000.00000000.00000000.00000000.00100101.10000101.00";
    assertEquals(expectedResult, bitSequence.toString(), "BitSequence not correctly constructed");
  }
  
  /**
   * Test addBitSequence().
   */
  @Test
  public void addBitSequenceTest() {
    byte[] bitSequenceToAddBytes = {
        0b01000000, 0b00110000, 0b00001010, 0b01100101, (byte) 0b11111111, (byte) 0b10000001, 0b00001111};
//    String expectedResult =
//        "00000010.00001100.01010000.10100110.11111111.10000001.11110000";
    BitSequence bitSequenceToAdd = new BitSequence(bitSequenceToAddBytes);
    
    byte[] bitSequenceBytes = {
        0b000000001, 0b000000001, 0b000000001, 0b000000001, 0b000000001, 0b000000001, 0b000000001};
    BitSequence bitSequence = new BitSequence(bitSequenceBytes);
    bitSequence.setPosition(bitSequence.getLength());
    bitSequence.addFixedSizeInteger(0b101, 3);
    bitSequence.addBitSequence(bitSequenceToAdd);
        
    assertEquals(115, bitSequence.getLength(), "Wrong length");
    String expectedResult =
        "10000000.10000000.10000000.10000000.10000000.10000000.10000000.10100000.01000001.10001010.00010100.11011111.11110000.00111110.000";
    assertEquals(expectedResult, bitSequence.toString(), "BitSequence not correctly constructed");
  }
  
  /**
   * Read/write file test
   */
  @Test
  public void writeReadTest() {
    FileSystem imfs = Jimfs.newFileSystem(Configuration.unix());
    Path tmpDirPath = imfs.getPath("/BitSequenceTest");
    try {
      Files.createDirectory(tmpDirPath);
      
      byte[] bitSequenceBytes = {
          0b01000000, 0b00110000, 0b00001010, 0b01100101, (byte) 0b11111111, (byte) 0b10000001, 0b00001111, (byte) 0b10101010, 0b01010101};
      BitSequence bitSequence = new BitSequence(bitSequenceBytes);
      Path filePath = tmpDirPath.resolve("bitSequence.bin");      
      bitSequence.write(filePath);
      
      bitSequence = BitSequence.read(filePath);
      String expectedResult =
          "00000010.00001100.01010000.10100110.11111111.10000001.11110000.01010101.10101010";
      assertEquals(72, bitSequence.getLength(), "BitSequence has the wrong length");
      assertEquals(expectedResult, bitSequence.toString(), "BitSequence not correctly written or read");
      
      
    } catch (IOException e) {
      fail("unexpected IOException: " + e.getMessage());
    }
    
  }
}

