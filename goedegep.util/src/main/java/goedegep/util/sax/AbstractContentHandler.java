package goedegep.util.sax;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class AbstractContentHandler extends DefaultHandler {
  private static String   xmlNameSpaceName = null;
  private static String   xmlNameSpaceURI = null;
  protected Locator       locator;

  public static String getXmlNameSpaceName() {
    return xmlNameSpaceName;
  }

  public static String getXmlNameSpaceURI() {
    return xmlNameSpaceURI;
  }

  public static void setXmlNameSpace(String name, String uri) {
    xmlNameSpaceName = name;
    xmlNameSpaceURI = uri;
  }

  public static String quoteString(String s) {
    if (s == null) {
      return "null";
    }
    StringBuffer str = new StringBuffer();
    str.append('"');
    int length = s.length();
    for (int i = 0; i < length; i++) {
      char c = s.charAt(i);
      switch (c) {
      case '\n': {
        str.append("\\n");
        break;
      }
      case '\r': {
        str.append("\\r");
        break;
      }
      case '\t': {
        str.append("\\t");
        break;
      }
      case '\\': {
        str.append("\\\\");
        break;
      }
      case '"': {
        str.append("\\\"");
        break;
      }
      default: {
        str.append(c);
      }
      }
    }
    str.append('"');
    return str.toString();
  }

  /**
   * <p>
   * Verwijs naar <code>Locator</code> met informatie
   * over de locatie van callbacks in een document.
   * <\p>
   *
   * @param locator <code>Locator</code> object
   * gekoppeld aan callback-process.
   */
  public void setDocumentLocator(Locator locator) {
    this.locator = locator;
  }

  public void startDocument() throws SAXException {
    //System.out.println("startDocument()");
  }

  public void endDocument() throws SAXException {
    //System.out.println("endDocument()");
  }

  public void processingInstruction(String target, String data) throws SAXException {
//    System.out.print("processingInstruction(");
//    System.out.print("target="+quoteString(target));
//    System.out.print(',');
//    System.out.print("data="+quoteString(data));
//    System.out.println(')');
  }

  public void startPrefixMapping(String prefix, String uri) throws SAXException {
    //System.out.print("startPrefixMapping(");
    //System.out.print("prefix="+quoteString(prefix));
    //System.out.print(',');
    //System.out.print("uri="+quoteString(uri));
    //System.out.println(')');
  }

  public void endPrefixMapping(String prefix) throws SAXException {
    //System.out.print("endPrefixMapping(");
    //System.out.print("prefix="+quoteString(prefix));
    //System.out.println(')');
  }

  public void ignorableWhitespace(char[] ch, int offset, int length) throws SAXException {
  }

  public void skippedEntity(String name) throws SAXException {
    System.out.print("skippedEntity(");
    System.out.print("name="+quoteString(name));
    System.out.println(')');
  }

//  
//  public void startElementError(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
//    System.out.println("CompaniesContentHandler: Illegal startElement");
//    System.out.print("startElement(");
//    System.out.print("element={"+localpart+'}');
//    System.out.print(',');
//    System.out.print("attributes=");
//    if (attributes == null) {
//      System.out.println("null");
//    } else {
//      System.out.print('{');
//      int length = attributes.getLength();
//      for (int i = 0; i < length; i++) {
//        if (i > 0) {
//          System.out.print(',');
//        }
//        String attrURI = attributes.getURI(i);
//        String attrLocalpart = attributes.getLocalName(i);
//        String attrRawname = attributes.getQName(i);
//        System.out.print('{'+attrURI+','+attrLocalpart+','+attrRawname+"}=");
//        System.out.print(quoteString(attributes.getValue(i)));
//      }
//      System.out.print('}');
//    }
//    System.out.println(')');
//  }
//
}