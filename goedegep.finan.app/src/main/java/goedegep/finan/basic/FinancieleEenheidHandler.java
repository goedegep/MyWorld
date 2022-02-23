package goedegep.finan.basic;

import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.Rolodex;
import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sax.ParseException;
import goedegep.util.sgml.SgmlUtil;
import goedegep.util.text.Indent;
import goedegep.util.xml.XmlUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class FinancieleEenheidHandler extends AbstractValidatingHandler<FinancieleEenheidHandler.State> {
  private static final Logger         LOGGER = Logger.getLogger(FinancieleEenheidHandler.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  private static final String FINANCIELE_EENHEDEN_TAG = "FinancieleEenheden";
  private static final String FINANCIELE_EENHEID_TAG = "FinancieleEenheid";
  private static final String TE_NAAM_STELLING_TAG = "TeNaamStelling";
  private static final String ADRES_ID_TAG = "AdresId";
  private static final String PERSONEN_TAG = "Personen";
  private static final String PERSOON_ID_TAG = "PersoonId";
  
  private static String       currentFinancieleEenhedenURI = null;
  private static String       schema = null;
  
  private Rolodex                  rolodex = null;
  private List<FinancieleEenheid>  financieleEenheden = null;
  private FinancieleEenheid        financieleEenheid = null;


  public FinancieleEenheidHandler(Rolodex rolodex) {
    this.rolodex = rolodex;
//    setPrintStateChanges(true);
  }
  
  public List<FinancieleEenheid> getFinancieleEenheden() {
    return financieleEenheden;
  }

  public void read(String financieleEenhedenURI) throws ParseException {
    LOGGER.info("Reading financiele eenheden from: " + financieleEenhedenURI.toString());
    state = State.START;
    boolean parsingSuccessful = false;
    try {
      financieleEenheden = new ArrayList<FinancieleEenheid>();
      currentFinancieleEenhedenURI = financieleEenhedenURI;
      getParser(null).parse(financieleEenhedenURI, this);
      parsingSuccessful = true;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    } catch (SAXException e) {
      throw new ParseException(financieleEenhedenURI, locator.getLineNumber(),
          locator.getColumnNumber(), e.getMessage());
    } finally {
      LOGGER.fine("FINALLY parsingSuccessful = " + parsingSuccessful);
      if (!parsingSuccessful) {
        financieleEenheden = null;
        currentFinancieleEenhedenURI = null;
      }
    }
  }
  
  public void write() {
    StringBuilder output = new StringBuilder();
    Indent indent = new Indent(2);

    if (currentFinancieleEenhedenURI == null) {
      throw new RuntimeException("Geen URI voor de financiele eenheden file.");
    }
    if (schema == null) {
      throw new RuntimeException("Geen schema voor de financiele eenheden file.");
    }
    
    try
    {
      // Rename existing file to .bak file
      File file = new File(currentFinancieleEenhedenURI);
      file.renameTo(new File(currentFinancieleEenhedenURI + ".bak"));

      // Create an output writer that will write to that file.
      FileWriter out = new FileWriter(currentFinancieleEenhedenURI);
      output.append(XmlUtil.createStartLine("UTF-8")).append(NEWLINE);
      output.append(XmlUtil.createRootElementOpen(getXmlNameSpaceName(), FINANCIELE_EENHEDEN_TAG, schema));
      indent.increment();

      // Build the String with all 'financiele eenheden'.
      for (FinancieleEenheid financieleEenheid: financieleEenheden) {
        output.append(financieleEenheidToXmlString(indent, getXmlNameSpaceName(), financieleEenheid));
      }

      indent.decrement();
      output.append(XmlUtil.createRootElementClose(getXmlNameSpaceName(), FINANCIELE_EENHEDEN_TAG));

      // write to output
      out.write(output.toString());
      out.close();
    }
    catch (IOException e) {
      throw new RuntimeException("Fout bij het schrijven van de financiele eenheden file. Systeem melding: " +
          e.getMessage() + ", " + e.getCause());
    }
  }
  
  public static String financieleEenheidToXmlString(Indent indent, String nameSpace, FinancieleEenheid financieleEenheid) {
    StringBuffer buf = new StringBuffer();
    String newline = System.getProperty("line.separator");
    
    buf.append(SgmlUtil.createElementOpen(indent, nameSpace, FINANCIELE_EENHEID_TAG)).append(newline);
    indent.increment();
    
    buf.append(SgmlUtil.createElement(indent, nameSpace, TE_NAAM_STELLING_TAG, financieleEenheid.getTeNaamStelling())).append(newline);
    buf.append(SgmlUtil.createElement(indent, nameSpace, ADRES_ID_TAG, String.valueOf(financieleEenheid.getAdres().getId()))).append(newline);
    
    buf.append(SgmlUtil.createElementOpen(indent, nameSpace, PERSONEN_TAG)).append(newline);
    indent.increment();
    for (Person person: financieleEenheid.getPersonen()) {
      buf.append(SgmlUtil.createElement(indent, nameSpace, PERSOON_ID_TAG, String.valueOf(person.getId()))).append(newline);
    }
    indent.decrement();
    buf.append(SgmlUtil.createElementClose(indent, nameSpace, PERSONEN_TAG)).append(newline);
    
    indent.decrement();
    buf.append(SgmlUtil.createElementClose(indent, nameSpace, FINANCIELE_EENHEID_TAG)).append(newline);

    return buf.toString();
  }

  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
//    System.out.println("FinancieleEenheidHandler: startElement = " + localpart);
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }
    data = null;
    
    switch (state) {
    case START:
      if (tag.compareTo(FINANCIELE_EENHEDEN_TAG) == 0) {
        schema = attributes.getValue("xsi:noNamespaceSchemaLocation");
        pushState(State.FINANCIELE_EENHEDEN);
      }
      break;

    case FINANCIELE_EENHEDEN:
      if (tag.compareTo(FINANCIELE_EENHEID_TAG) == 0) {
        financieleEenheid = new FinancieleEenheid();
        pushState(State.FINANCIELE_EENHEID);
      }
      break;
      
    case FINANCIELE_EENHEID:
      if (tag.compareTo(PERSONEN_TAG) == 0) {
        pushState(State.PERSONEN);
      } else {
        data = null;
      }
      break;
      
    case PERSONEN:
      data = null;
      break;
    }
  }

  public void endElement(String uri, String localpart, String rawname) throws SAXException {
//    System.out.println("FinancieleEenheidHandler: endElement = " + localpart);
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }

    switch (state) {
    case START:
      break;

    case FINANCIELE_EENHEDEN:
      if (tag.compareTo(FINANCIELE_EENHEDEN_TAG) == 0) {
        popState();
      }
      break;

    case FINANCIELE_EENHEID:
      if (tag.compareTo(FINANCIELE_EENHEID_TAG) == 0) {
        financieleEenheden.add(financieleEenheid);
        financieleEenheid = null;
        popState();
      } else if (tag.compareTo(TE_NAAM_STELLING_TAG) == 0) {
        financieleEenheid.setTeNaamStelling(data);
      } else if (tag.compareTo(ADRES_ID_TAG) == 0) {
        Address address = rolodex.getAddressList().findAddressById(data);
        if (address == null) {
          throw new ParseException(currentFinancieleEenhedenURI, locator.getLineNumber(),
              locator.getColumnNumber(), "onbekend adres voor id: " + data);
        }
        financieleEenheid.setAdres(address);
      }
      break;

    case PERSONEN:
      if (tag.compareTo(PERSONEN_TAG) == 0) {
        popState();
      } else if (tag.compareTo(PERSOON_ID_TAG) == 0) {
        Person person = rolodex.getPersonList().findPersonById(data);
        if (person == null) {
          throw new ParseException(currentFinancieleEenhedenURI, locator.getLineNumber(),
              locator.getColumnNumber(), "onbekende persoon voor id: " + data);
        }
        financieleEenheid.addPersoon(person);
      }
      break;
    }
  }

  protected enum State {
    START,
    FINANCIELE_EENHEDEN,
    FINANCIELE_EENHEID,
    PERSONEN;
  }
}