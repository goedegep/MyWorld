package goedegep.util.csvfileaccesstest;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import goedegep.util.csvfileaccess.CSVFileReader;

public class CSVFileReaderTest {
  
  /**
   * Commas can be quoted with the double quote character and included in a field.
   * @throws Exception
   */
  @Test
  public void quotedCommaTest() throws Exception {
    try (Reader in = new StringReader("A,B,\"C,D\",e")) {
      CSVFileReader csvFileReader = new CSVFileReader(true, ',', in);
      assertTrue(csvFileReader.hasNext());
      List<String> fields = csvFileReader.next();
      assertTrue(fields.size() == 4);
      List<String> expected = Arrays.asList("A", "B", "C,D", "e");
      assertThat(fields, is(expected));
    }
  }

  /**
   * Single instances of double-quotes are read and stripped away from the field value; they are assumed to quote text containing commas and new-lines.
   * @throws Exception
   */
  @Test
  public void quotesStrippedTest() throws Exception {
    try (Reader in = new StringReader("A,B,I said \"How are you?\",d");) {
      CSVFileReader csvFileReader = new CSVFileReader(true, ',', in);
      assertTrue(csvFileReader.hasNext());
      List<String> fields = csvFileReader.next();
      assertTrue(fields.size() == 4);
      List<String> expected = Arrays.asList("A", "B", "I said How are you?", "d");
      assertThat(fields, is(expected));
    }
  }

  /**
   * To include double quotes as part of the field, it must be repeated and enclosed within double quotes.
   * @throws Exception
   */
  @Test
  public void includeQuotesTest() throws Exception {
    try (Reader in = new StringReader("A,B,\"I said \"\"How about you?\"\"\",d");) {
      CSVFileReader csvFileReader = new CSVFileReader(true, ',', in);
      assertTrue(csvFileReader.hasNext());
      List<String> fields = csvFileReader.next();
      assertTrue(fields.size() == 4);
      List<String> expected = Arrays.asList("A", "B", "I said \"How about you?\"", "d");
      assertThat(fields, is(expected));
    }
  }

  /**
   * Text spanning across lines is sometimes included within CSV data. The class handles this case also without hiccups.
   * @throws Exception
   */
  @Test
  public void multilineFieldQuoteTest() throws Exception {
    try (Reader in = new StringReader("A,B,\"I said\n\"\"How are you?\"\"\",d");) {
      CSVFileReader csvFileReader = new CSVFileReader(true, ',', in);
      assertTrue(csvFileReader.hasNext());
      List<String> fields = csvFileReader.next();
      assertTrue(fields.size() == 4);
      List<String> expected = Arrays.asList("A", "B", "I said\n\"How are you?\"", "d");
      assertThat(fields, is(expected));
    }
  }
  
  /**
   * This test illustrates how to read a CSV file and load the rows and columns into a List.
   * The file is opened with an InputStream so the Byte Order Marker can be automatically detected and discarded.
   * Also, the first row of the CSV file is assumed to be column headers and is loaded into a separate array.
   *
   * @throws Exception
   */
  @Test
  public void readCsvFileTest() throws Exception {
    String csvFile = "src\\test\\resources\\goedegep\\util\\csvfileaccess\\Sample.csv";
    List<String> colNamesExpected = Arrays.asList("A", "B", "C");
    List<String> rowExpected1 = Arrays.asList("one", "two", "three");
    List<String> rowExpected2 = Arrays.asList("xx", "yy", "zz");
    List<List<String>> rowsExpected = new ArrayList<>();
    rowsExpected.add(rowExpected1);
    rowsExpected.add(rowExpected2);
    
    List<String> colNames = null;
    List<List<String>> rows = new ArrayList<>();
    
    try (InputStream in = new FileInputStream(csvFile);) {
      CSVFileReader csv = new CSVFileReader(true, ',', in);
      if (csv.hasNext())
        colNames = new ArrayList<String>(csv.next());
      while (csv.hasNext()) {
        List<String> fields = new ArrayList<String>(csv.next());
        rows.add(fields);
      }
      
      assertThat(colNames, is(colNamesExpected));
      assertThat(rows, is(rowsExpected));
    }
    
  }
}
