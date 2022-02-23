package goedegep.util.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import goedegep.util.sgml.SgmlUtil;
import goedegep.util.text.Indent;

/**
 * This class provides utility methods for writing to HTML files.
 */
public final class HtmlUtil {
  private static final String       NEWLINE = System.getProperty("line.separator");
  private static String[] HTML_TAGS = {
      "<ul>", "<li>", "<b>", "<i>", "<br/>"
  };
  
  /**
   * Constructor
   * <p>
   * Private constructor to avoid class being instantiated or extended.
   */
  private HtmlUtil() {
  }

  /**
   * Create an HTML file which contains a table.
   * 
   * @param file the File to be written
   * @param columnNames the values for the table header
   * @param tableData the values for the table rows
   * @throws IOException
   */
  public static void writeHtmlTable(File file, List<String> columnNames, List<List<String>> tableData) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    Indent indent = new Indent(2);
    
    writer.write(SgmlUtil.createElementOpen(indent, null, "html"));
    writer.write(NEWLINE);
    indent.increment();
    writer.write(SgmlUtil.createElementOpen(indent, null, "header"));
    writer.write(NEWLINE);
    indent.increment();
    writer.write(SgmlUtil.createElement(indent, null, "title", "Tabel titel"));
    writer.write(NEWLINE);
    indent.decrement();
    writer.write(SgmlUtil.createElementClose(indent, null, "header"));
    writer.write(NEWLINE);

    writer.write(SgmlUtil.createElementOpen(indent, null, "body"));
    writer.write(NEWLINE);
    indent.increment();
    
    writer.write(SgmlUtil.createElementOpen(indent, null, "table border=\"2\""));
    writer.write(NEWLINE);
    indent.increment();
    
    writeHtmlTableHeader(writer, indent, columnNames);
    writeHtmlTableBody(writer, indent, tableData);
    
    indent.decrement();
    writer.write(SgmlUtil.createElementClose(indent, null, "table"));
    writer.write(NEWLINE);
    
    indent.decrement();
    writer.write(SgmlUtil.createElementClose(indent, null, "body"));
    writer.write(NEWLINE);
    
    writer.write(SgmlUtil.createElementClose(indent, null, "html"));
    writer.write(NEWLINE);
    
    writer.close();
  }
  
  /**
   * Write an HTML table header.
   * @param writer the BufferedWriter to which the header will be written.
   * @param indent the indentation for the header
   * @param columnNames the header values
   * @throws IOException
   */
  public static void writeHtmlTableHeader(BufferedWriter writer, Indent indent, List<String> columnNames) throws IOException {
    writer.write(SgmlUtil.createElementOpen(indent, null, "tr"));
    writer.write(NEWLINE);
    indent.increment();
    
    for (String columnName: columnNames) {
      writer.write(SgmlUtil.createElement(indent, null, "th", encodeHTML(columnName)));
      writer.write(NEWLINE);
    }

    indent.decrement();
    writer.write(SgmlUtil.createElementClose(indent, null, "tr"));
    writer.write(NEWLINE);
  }
  
  /**
   * Write an HTML table body
   * @param writer the BufferedWriter to which the body will be written
   * @param indent the indentation for the table body
   * @param tableData the data for the table. The outer list entries are the rows, each row is a list of values for each column
   * @throws IOException
   */
  public static void writeHtmlTableBody(BufferedWriter writer, Indent indent, List<List<String>> tableData) throws IOException {
   
    for (List<String> rowData: tableData) {
      writer.write(SgmlUtil.createElementOpen(indent, null, "tr"));
      writer.write(NEWLINE);
      indent.increment();
      
      for (String columnValue: rowData) {
        writer.write(SgmlUtil.createElement(indent, null, "td", encodeHTML(columnValue)));
        writer.write(NEWLINE);
      }
      
      indent.decrement();
      writer.write(SgmlUtil.createElementClose(indent, null, "tr"));
      writer.write(NEWLINE);
    }
  }
  
  /**
   * Encode a String for usage in an HTML file, which means that reserved HTML characters (like '<') are 'escaped'.
   * 
   * @param s the String to be HTML encoded.
   * @return the encoded string
   */
  public static String encodeHTML(String s)
  {
    if (s.startsWith("<![CDATA[")) {
      return s;
    }
    
      StringBuilder out = new StringBuilder();
      for(int i=0; i<s.length(); i++)
      {
          char c = s.charAt(i);
//          if(c > 127 || c=='"' || c=='<' || c=='>')
          if(c > 127 || c=='<' || c=='>')
          {
             out.append("&#"+(int)c+";");
          }
          else if (c == '&') {
             out.append("&amp;");
          } else
          {
              out.append(c);
          }
      }
      return out.toString();
  }

  public static String embedInCDataIfTextContainsHtmlMarkup(String text) {
    boolean containsHtmlMarkup = false;
    
    for (String htmlTag: HTML_TAGS) {
      if (text.contains(htmlTag)) {
        containsHtmlMarkup = true;
        break;
      }
    }
    
    if (containsHtmlMarkup) {
      return "<![CDATA[" + text + "]]>";
    } else {
      return text;
    }
  }  
  
}
