package goedegep.util.sax;

import java.util.Stack;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class AbstractValidatingHandler<T> extends AbstractContentHandler {
  private static final Logger       LOGGER = Logger.getLogger(AbstractValidatingHandler.class.getName());
  static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
  static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
  
  private static SAXParser parser = null;
  protected T              state;
  private Stack<T>         stateStack = new Stack<T>();
  protected String         data;
  private boolean          printStateChanges = false;
  
  protected SAXParser getParser(SAXParser providedParser) {
    parser = providedParser;
    
    if (parser == null) {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setNamespaceAware(true);
      factory.setValidating(true);

      try {
        parser = factory.newSAXParser();
        parser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
      } catch (SAXException e) {
        e.printStackTrace();
        System.exit(-1);
      } catch (ParserConfigurationException e) {
        e.printStackTrace();
        System.exit(-1);
      }
    }
    
    return parser;
  }
  
  public void characters(char[] ch, int offset, int length) throws SAXException {
    LOGGER.fine("Handling characters = !" + new String(ch, offset, length) + "!");
    String txt = new String(ch, offset, length);
    if (txt.length() == 0) {
      return;
    }

    if (data == null) {
      data = txt;
    } else {
      data = data + txt;
    }
  }

  
  protected void setPrintStateChanges(boolean printStateChanges) {
    this.printStateChanges = printStateChanges;
  }

  protected void pushState(T newState) {
    if (printStateChanges) {
      LOGGER.info("Pushing state: " + state + " => " + newState);
    }
    stateStack.push(state);
    state = newState;
  }

  protected void popState() {
    if (stateStack.isEmpty()) {
      throw new RuntimeException("Empty stack, state = " + state);
    } else {
      if (printStateChanges) {
        LOGGER.info("Popped state: " + state);
      }
      state = stateStack.pop();
      if (printStateChanges) {
        LOGGER.info(" -> " + state);
      }
    }
  }
}
