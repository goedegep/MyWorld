package goedegep.app.finan.gen;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {
  public void warning(SAXParseException e) throws SAXException {
    System.out.println("Warning: "); 
    printInfo(e);
    throw new RuntimeException(e.getMessage());
 }
  
 public void error(SAXParseException e) throws SAXException {
    System.out.println("Error: "); 
    printInfo(e);
    throw new RuntimeException(e.getMessage());
 }
 
 public void fatalError(SAXParseException e) throws SAXException {
    System.out.println("Fattal error: "); 
    printInfo(e);
    throw new RuntimeException(e.getMessage());
 }
 
 private void printInfo(SAXParseException e) {
   String indent = "  ";
    System.out.println(indent + "Public ID: " + e.getPublicId());
    System.out.println(indent + "System ID: " + e.getSystemId());
    System.out.println(indent + "Line number: " + e.getLineNumber());
    System.out.println(indent + "Column number: " + e.getColumnNumber());
    System.out.println(indent + "Message: " + e.getMessage());
 }
}
