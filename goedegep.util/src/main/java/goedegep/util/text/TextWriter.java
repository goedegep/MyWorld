package goedegep.util.text;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * A BufferedWriter extended with convenience methods for writing formatted text.
 *
 */
public class TextWriter extends BufferedWriter {
  /**
   * Current text position.
   */
  private int currentTextPos = 0;
  
  public TextWriter (Writer textWriter) {
    super(textWriter);
  }

  /**
   * Write text at a specific position.
   * 
   * @param textPos absolute position at which the text shall be written. It makes no sense to use a value less than the current text position
   *                (as we cannot move backwards). It this value is 0, the current text position will be set to 0.  
   * @param string the text to be written.
   * @return the new current text position.
   * @throws IOException
   */
  public int writeString(int textPos, String string) throws IOException {
    if (textPos == 0) {
      currentTextPos = 0;
    }
    
    while (currentTextPos < textPos) {
      write(" ");
      currentTextPos++;
    }
    
    write(string);
    currentTextPos += string.length();
    
    return currentTextPos;
  }

  /**
   * Write text at a specific position, followed by a specific number of newlines.
   * 
   * @param textPos absolute position at which the text shall be written. It makes no sense to use a value less than the current text position
   *                (as we cannot move backwards). It this value is 0, the current text position will be set to 0.  
   * @param string the text to be written.
   * @param nrOfNewLines a number of newlines that will be written after the text is written. After a newline the current text position will be 0.
   * @return the new current text position.
   * @throws IOException
   */
  public int writeString(int textPos, String string, int nrOfNewLines) throws IOException {
    writeString(textPos, string);
    while (nrOfNewLines-- > 0) {
      newLine();
    }
    
    return currentTextPos;
  }

  /**
   * Write text at a specific offset after the current text position.
   * 
   * @param offset the number of spaces that will be written before the text is written.  
   * @param string the text to be written.
   * @return the new current text position.
   * @throws IOException
   */
  public int writeStringOffset(int offset, String string) throws IOException {
    while (offset-- > 0) {
      write(" ");
      currentTextPos++;
    }
    write(string);
    currentTextPos += string.length();
    
    return currentTextPos;
  }

  /**
   * Write text at a specific offset after the current text position, followed by a specific number of newlines.
   * 
   * @param offset the number of spaces that will be written before the text is written.  
   * @param string the text to be written.
   * @param nrOfNewLines a number of newlines that will be written after the text is written. After a newline the current text position will be 0.
   * @return the new current text position.
   * @throws IOException
   */
  public int writeStringOffset(int offset, String string, int nrOfNewLines) throws IOException {
    writeStringOffset(offset, string);
    while (nrOfNewLines-- > 0) {
      newLine();
    }
    
    return currentTextPos;
  }
  
  /**
   * Write a number of text lines, optionally followed by an extra newline.
   * As complete lines are written, after this call the current text position will be 0.
   * @param lines the text lines to be written.
   * @param extraNewLineAtEnd if true, an extra newline will be written after the text lines.
   * @throws IOException
   */
  public void writeTextBlock(String[] lines, boolean extraNewLineAtEnd) throws IOException {
    for (String line: lines) {
      write(line);
      newLine();
    }
    
    if (extraNewLineAtEnd) {
      newLine();
    }
  }
  
  /**
   * Write a newline. After this call the current text position will be 0.
   */
  public void newLine() throws IOException {
    super.newLine();
    currentTextPos = 0;
  }
}
