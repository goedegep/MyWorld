package goedegep.util.xml;

import goedegep.util.sgml.SgmlUtil;

/**
 * This class provides XML (Extensible Markup Language) related utility methods.
 * <p>
 * As XML is a subset of SGML, many methods of {@link goedegep.util.sgml.SgmlUtil} can also be used for XML.
 */
public class XmlUtil {
  private static final String   NEWLINE = System.getProperty("line.separator");
  
  /**
   * Constructor
   * <p>
   * A private constructor, so this utility class cannot be instantiated nor extended.
   */
  private XmlUtil() {
    
  }

  /**
   * Create the most basic XML start line: '<?xml version=\"1.0\" ?>'.
   * 
   * @return the XML startline
   */
  public static String createStartLine() {
    return createStartLine(null);
  }

  /**
   * Create a start line with a specific encoding.
   * 
   * @param encoding the encoding to be used, e.g. "UTF-8". This value may be null, in which case the encoding attribute is omitted.
   * @return the XML startline with the specified encoding.
   */
  public static String createStartLine(String encoding) {
    StringBuilder buf = new StringBuilder();

    buf.append("<?xml version=\"1.0\"");
    if (encoding != null) {
      buf.append(" encoding=\"" + encoding + "\"");
    }
    buf.append(" ?>").append(NEWLINE);
    return buf.toString();
  }

  /**
   * Create the open tag for the root element.
   * <p>
   * As this is the root element, there is no indentation.
   * 
   * @param nameSpace an optional name space
   * @param element the XML element
   * @param schema an optional 'no name space schema location'.
   * @return the created open element.
   */
  public static String createRootElementOpen(String nameSpace, String element, String schema) {
    StringBuilder buf = new StringBuilder();
    
    buf.append("<");
    
    if (nameSpace != null) {
      buf.append(nameSpace).append(":");
    }
    buf.append(element);
    
    if (schema != null) {
      buf.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"");
      buf.append(schema).append("\"");
    }
    
    buf.append(">").append(NEWLINE);
    return buf.toString();
  }
  
  /**
   * Create the close tag for the root element.
   * <p>
   * As this is the root element, there is no indentation.
   * 
   * @param nameSpace an optional name space
   * @param element the XML element
   * @return the created close element
   */
  public static String createRootElementClose(String nameSpace, String element) {
    return SgmlUtil.createElementClose(nameSpace, element) + NEWLINE;
  }
}
