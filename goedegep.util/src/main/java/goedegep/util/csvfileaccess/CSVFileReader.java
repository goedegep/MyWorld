package goedegep.util.csvfileaccess;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/*
 * Copyright 2017 Jay Sridhar
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * @Author Jay Sridhar
 */
/**
 * This class can be used for reading CSV (Comma Separated Values) files.
 * <p>
 * The class is a modified version of the class CSV from Jay Sridhar.
 * See the article "How to Read CSV Files in Java" at {@link https://dzone.com/articles/how-to-read-csv-files-in-java}.
 *
 */
public class CSVFileReader {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(CSVFileReader.class.getName());
  
  static final private int READ_AHEAD_LIMIT = 10;  // Maximum number of characters to read ahead.
  static final private char DOUBLE_QUOTE = '"';
  static final private char CARRIAGE_RETURN = '\r';
  static final private char LINEFEED = '\n';
  static final private char COMMENT = '#';

  /**
   * Should we ignore multiple carriage-return/newline characters at the end of
   * the record?
   */
  private boolean stripMultipleNewlines;

  private char separator;               // The character that acts a separator between the fields (columns)
  private ArrayList<String> fields;
  private boolean eofSeen;
  private Reader bufferedReader;

  /**
   * Constructor for reading a CSV file via a {@link Reader}.
   * 
   * @param stripMultipleNewlines If true, any sequence of CARRIAGE_RETURN / LINEFEED is treated as a single LINEFEED.
   * @param separator The character that acts a separator between the fields (columns)
   * @param reader The Reader from which to read the data.
   */
  public CSVFileReader(boolean stripMultipleNewlines, char separator, Reader reader) {
    this.stripMultipleNewlines = stripMultipleNewlines;
    this.separator = separator;
    fields = new ArrayList<String>();
    eofSeen = false;
    bufferedReader = new BufferedReader(reader);
  }

  /**
   * Constructor for reading a CSV file via an {@link InputStream}.
   * 
   * @param stripMultipleNewlines If true, any sequence of CARRIAGE_RETURN / LINEFEED is treated as a single LINEFEED.
   * @param separator The character that acts a separator between the fields (columns)
   * @param reader The Reader from which to read the data.
   */
  public CSVFileReader(boolean stripMultipleNewlines, char separator, InputStream input)
      throws java.io.IOException, java.io.UnsupportedEncodingException {
    this.stripMultipleNewlines = stripMultipleNewlines;
    this.separator = separator;
    fields = new ArrayList<String>();
    eofSeen = false;
    bufferedReader = new BufferedReader(readAndStripBOM(input));
  }

  /**
   * Read, interpret and strip a possible BOM from an InputStream.
   * <p>
   * A CSV file generated from an application on Windows might include a BOM (Byte Order Mark) character at the very beginning of the file.
   * This character, if present, can be used to determine the encoding of the file from among UTF-8, UTF-16BE (Big Ending) or UTF-16LE (Little Endian).
   * This method reads the BOM, if present, and returns the right reader for the detected encoding.<br/>
   * See {@link https://www.w3.org/International/questions/qa-byte-order-mark} for more information about the BOM.
   * 
   * <pre>
   * First bytes (in hex) - Encoding
   * EF BB BF               UTF-8
   * FE FF                  UTF-16BE
   * FF FE                  UTF-16LE
   * </pre>
   * 
   * @param in the input stream to be handled
   * @return a Reader for the correct encoding
   * @throws java.io.IOException
   * @throws java.io.UnsupportedEncodingException
   */
  static public Reader readAndStripBOM(InputStream in) throws java.io.IOException, java.io.UnsupportedEncodingException {
    PushbackInputStream pin = new PushbackInputStream(in, 3);
    byte[] b = new byte[3];
    int len = pin.read(b, 0, b.length);
    if ((b[0] & 0xFF) == 0xEF && len == 3) {
      if ((b[1] & 0xFF) == 0xBB && (b[2] & 0xFF) == 0xBF) {
        return new InputStreamReader(pin, "UTF-8");
      } else {
        pin.unread(b, 0, len);
      }
    } else if (len >= 2) {
      if ((b[0] & 0xFF) == 0xFE && (b[1] & 0xFF) == 0xFF) {
        return new InputStreamReader(pin, "UTF-16BE");
      } else if ((b[0] & 0xFF) == 0xFF && (b[1] & 0xFF) == 0xFE) {
        return new InputStreamReader(pin, "UTF-16LE");
      } else {
        pin.unread(b, 0, len);
      }
    } else if (len > 0) {
      pin.unread(b, 0, len);
    }
    
    return new InputStreamReader(pin, "UTF-8");
  }

  /**
   * Check whether another data row is available.
   * <p>
   * This method actually reads the next row, so never called it more than once without calling next() in between (unless you would want to skip a row of course).
   * 
   * @return True, if another data row is available, false otherwise (i.e. end of file reached).
   * @throws java.io.IOException
   */
  public boolean hasNext() throws java.io.IOException {
    if (eofSeen)
      return false;
    fields.clear();
    eofSeen = split(bufferedReader, fields);
    if (eofSeen)
      return !fields.isEmpty();
    else
      return true;
  }

  /**
   * Obtain the next data row.
   * <p>
   * As the actual reading is done in hasNext(), this method can only be called after a call to hasNext().
   * @return
   */
  public List<String> next() {
    return fields;
  }

  /**
   * Skip a single LINEFEED or multiple CARRIAGE_RETURN / LINEFEED characters.
   * 
   * @param in The <b>Reader</b> from which to skip the character(s).
   * @param stripMultipleNewlines If false, only a possible LINEFEED is skipped. If true, any sequence of CARRIAGE_RETURN / LINEFEED is skipped.
   * @return True, if end of file is encountered, false otherwise.
   * @throws java.io.IOException
   */
  static private boolean discardLinefeed(Reader in, boolean stripMultipleNewlines) throws java.io.IOException {
    if (stripMultipleNewlines) {
      in.mark(READ_AHEAD_LIMIT);
      int value = in.read();
      while (value != -1) {
        char c = (char) value;
        if (c != CARRIAGE_RETURN && c != LINEFEED) {
          in.reset();
          return false;
        } else {
          in.mark(READ_AHEAD_LIMIT);
          value = in.read();
        }
      }
      return true;
    } else {
      in.mark(READ_AHEAD_LIMIT);
      int value = in.read();
      if (value == -1)
        return true;
      else if ((char) value != LINEFEED)
        in.reset();
      return false;
    }
  }

  /**
   * Skip the remainder of a line.
   * <p>
   * This method can be called if a line is detected to be a comment line.
   * 
   * @param in the Reader to read from.
   * @return true if end of file is reached, false otherwise.
   * @throws java.io.IOException
   */
  private boolean skipComment(Reader in) throws java.io.IOException {
    /* Discard line. */
    int value;
    while ((value = in.read()) != -1) {
      char c = (char) value;
      if (c == CARRIAGE_RETURN)
        return discardLinefeed(in, stripMultipleNewlines);
    }
    return true;
  }

  /**
   * Read one line (one row) from a CSV file.
   * 
   * @param in the Reader to read from
   * @param fields a List that will be filled will the fields read.
   * @return true if end of file is reached, false otherwise.
   * @throws java.io.IOException
   */
  private boolean split(Reader in, ArrayList<String> fields) throws java.io.IOException {
    StringBuilder buf = new StringBuilder();
    int value;
    while ((value = in.read()) != -1) {
      char c = (char) value;
      switch (c) {
      case CARRIAGE_RETURN:
        if (buf.length() > 0) {
          fields.add(buf.toString());
//          buf.delete(0, buf.length());
          buf.setLength(0);
        }
        return discardLinefeed(in, stripMultipleNewlines);

      case LINEFEED:
        if (buf.length() > 0) {
          fields.add(buf.toString());
          buf.delete(0, buf.length());
        }
        if (stripMultipleNewlines)
          return discardLinefeed(in, stripMultipleNewlines);
        else
          return false;

      case DOUBLE_QUOTE: {
        // Processing double-quoted string ..
        while ((value = in.read()) != -1) {
          c = (char) value;
          if (c == DOUBLE_QUOTE) {
            // Saw another double-quote. Check if
            // another char can be read.
            in.mark(READ_AHEAD_LIMIT);
            if ((value = in.read()) == -1) {
              // Nope, found EOF; means End of
              // field, End of record and End of
              // File
              if (buf.length() > 0) {
                fields.add(buf.toString());
                buf.delete(0, buf.length());
              }
              return true;
            } else if ((c = (char) value) == DOUBLE_QUOTE) {
              // Found a second double-quote
              // character. Means the double-quote
              // is included.
              buf.append(DOUBLE_QUOTE);
            } else if (c == CARRIAGE_RETURN) {
              // Found End of line. Means End of
              // field, and End of record.
              if (buf.length() > 0) {
                fields.add(buf.toString());
                buf.delete(0, buf.length());
              }
              // Read and discard a line-feed if we
              // can indeed do so.
              return discardLinefeed(in, stripMultipleNewlines);
            } else if (c == LINEFEED) {
              // Found end of line. Means End of
              // field, and End of record.
              if (buf.length() > 0) {
                fields.add(buf.toString());
                buf.delete(0, buf.length());
              }
              // No need to check further. At this
              // point, we have not yet hit EOF, so
              // we return false.
              if (stripMultipleNewlines)
                return discardLinefeed(in, stripMultipleNewlines);
              else
                return false;
            } else {
              // Not one of EOF, double-quote,
              // newline or line-feed. Means end of
              // double-quote processing. Does NOT
              // mean end-of-field or end-of-record.
              // System.err.println("EOR on '" + c +
              // "'");
              in.reset();
              break;
            }
          } else {
            // Not a double-quote, so no special meaning.
            buf.append(c);
          }
        }
        // Hit EOF, and did not see the terminating double-quote.
        if (value == -1) {
          // We ignore this error, and just add whatever
          // left as the next field.
          if (buf.length() > 0) {
            fields.add(buf.toString());
            buf.delete(0, buf.length());
          }
          return true;
        }
      }
        break;

      default:
        if (c == separator) {
          fields.add(buf.toString());
          buf.delete(0, buf.length());
        } else {
          /*
           * A comment line is a line starting with '#' with optional whitespace
           * at the start.
           */
          if (c == COMMENT && fields.isEmpty() && buf.toString().trim().isEmpty()) {
            boolean eof = skipComment(in);
            if (eof)
              return eof;
            else
              buf.delete(0, buf.length());
            /* Continue with next line if not eof. */
          } else
            buf.append(c);
        }
      }
    }
    if (buf.length() > 0) {
      fields.add(buf.toString());
//      buf.delete(0, buf.length());
      buf.setLength(0);
    }
    return true;
  }
  
}
