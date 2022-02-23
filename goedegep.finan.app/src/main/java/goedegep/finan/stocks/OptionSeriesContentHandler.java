package goedegep.finan.stocks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sax.ParseException;
import goedegep.util.sgml.SgmlUtil;
import goedegep.util.text.Indent;
import goedegep.util.xml.XmlUtil;

public class OptionSeriesContentHandler extends AbstractValidatingHandler<OptionSeriesContentHandler.State> {
  private static final PgCurrencyFormat  cf = new PgCurrencyFormat(0, false, false, true);

  private static String                  currentOptionSeriesURI = null;
  private static String                  schema = null;
  
  // Option Serie info
  private OptionType         optionType;
  private Share              share;
  private Integer            expirationMonth;
  private Integer            expirationYear;
  private PgCurrency         uitoefeningsKoers;

  public OptionSeriesContentHandler() {
    state = State.START;
//  setPrintStateChanges(true);
  }
  
  public void read(SAXParser parser, String optionSeriesURI) throws ParseException {
    try {
      getParser(parser).parse(optionSeriesURI, this);
      currentOptionSeriesURI = optionSeriesURI;
    } catch (IOException e) {
      throw new RuntimeException("IOException in read. Exception=" + e.getMessage());
    } catch (SAXException e) {
      throw new ParseException(optionSeriesURI, locator.getLineNumber(),
          locator.getColumnNumber(), e.getMessage());
    }
  }
  
  public static void write() {
    StringBuilder output = new StringBuilder();
    Indent        indent = new Indent(2);
    String        newline = System.getProperty("line.separator");

    if (currentOptionSeriesURI == null) {
      throw new RuntimeException("Geen URI voor de optie series file.");
    }
    if (schema == null) {
      throw new RuntimeException("Geen schema voor de optie series file.");
    }
    
    try
    {
      // Rename existing file to .bak file
      File file = new File(currentOptionSeriesURI);
      file.renameTo(new File(currentOptionSeriesURI + ".bak"));

      // Create an output writer that will write to that file.
      FileWriter out = new FileWriter(currentOptionSeriesURI);
      output.append(XmlUtil.createStartLine("UTF-8"));
      output.append(XmlUtil.createRootElementOpen(getXmlNameSpaceName(), CompanyBasics.OPTION_SERIES_TAG, schema));
      indent.increment();
      
      // Build the String with all option series
      for (OptionSerie optionSerie: OptionSerie.getOptionSeries()) {
        output.append(toXmlString(indent, getXmlNameSpaceName(), optionSerie));
        output.append(newline);
      }

      indent.decrement();
      output.append(XmlUtil.createRootElementClose(getXmlNameSpaceName(), CompanyBasics.OPTION_SERIES_TAG));

      // write to output
      out.write(output.toString());
      out.close();
    }
    catch (IOException e) {
      throw new RuntimeException("Fout bij het schrijven van de optie series file. Systeem melding: " +
          e.getMessage() + ", " + e.getCause());
    }
  }

  public static String toXmlString(Indent indent, String nameSpace, OptionSerie optionSerie) {
    StringBuilder buf = new StringBuilder();

    buf.append(SgmlUtil.createElementOpen(indent, nameSpace, CompanyBasics.OPTION_SERIE_TAG));

    buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.TYPE_TAG, optionSerie.getOptionType().getText())); 
    buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.FONDS_NAAM_TAG, optionSerie.getShare().getName()));
    buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.MAAND_TAG, Integer.toString(optionSerie.getExpirationMonth())));
    buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.JAAR_TAG, Integer.toString(optionSerie.getExpirationYear())));
    buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.UITOEFENINGS_KOERS_TAG, cf.format(optionSerie.getUitoefeningsKoers())));

    buf.append(SgmlUtil.createElementClose(nameSpace, CompanyBasics.OPTION_SERIE_TAG));
    return buf.toString();
  }

  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }
    data = null;

    switch (state) {
    case START:
      if (tag.compareTo(CompanyBasics.OPTION_SERIES_TAG) == 0) {
        schema = attributes.getValue("xsi:noNamespaceSchemaLocation");
        pushState(State.OPTION_SERIES);
      }
      break;

    case OPTION_SERIES:
      if (tag.compareTo(CompanyBasics.OPTION_SERIE_TAG) == 0) {
        pushState(State.OPTION_SERIE);
        optionType = null;
        share = null;
        expirationMonth = null;
        expirationYear = null;
        uitoefeningsKoers = null;
      }
      break;
      
    default:
      // no action
    }
  }

  public void endElement(String uri, String localpart, String rawname) throws SAXException {
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }

    switch (state) {
    case OPTION_SERIES:
      if (tag.compareTo(CompanyBasics.OPTION_SERIES_TAG) == 0) {
        popState();
      }
      break;

    case OPTION_SERIE:
      if (tag.compareTo(CompanyBasics.OPTION_SERIE_TAG) == 0) {
        if (optionType == null) {
          throw new SAXException("optie type ontbreekt in optie serie");
        }
        if (share == null) {
          throw new SAXException("fondsnaam ontbreekt in optie serie");
        }
        if (expirationMonth == null) {
          throw new SAXException("expiratie maand ontbreekt in optie serie");
        }
        if (expirationYear == null) {
          throw new SAXException("expiratie jaar ontbreekt in optie serie");
        }
        if (uitoefeningsKoers == null) {
          throw new SAXException("uitoefeningskoers ontbreekt in optie serie");
        }
        new OptionSerie(optionType, share, expirationMonth, expirationYear, uitoefeningsKoers);
        popState();
      } else if (tag.compareTo(CompanyBasics.FONDS_NAAM_TAG) == 0) {
        share = Share.getShare(data);
        if (share == null) {
          throw new SAXException("onbekend aandeel in optie serie: FondsNaam = " + data);
        }
      } else if (tag.compareTo(CompanyBasics.TYPE_TAG) == 0) {
        optionType = OptionType.getOptionTypeForText(data);
        if (optionType == null) {
          throw new SAXException("ongeldig optie type in optie serie: Type = " + data);
        }
      } else if (tag.compareTo(CompanyBasics.MAAND_TAG) == 0) {
        expirationMonth = Integer.parseInt(data);
        if (expirationMonth == null) {
          throw new SAXException("ongeldige maand in optie serie: Type = " + data);
        }
      } else if (tag.compareTo(CompanyBasics.JAAR_TAG) == 0) {
        expirationYear = Integer.parseInt(data);
        if (expirationYear == null) {
          throw new SAXException("ongeldig jaar in optie serie: Type = " + data);
        }
      } else if (tag.compareTo(CompanyBasics.UITOEFENINGS_KOERS_TAG) == 0) {
        try {
          uitoefeningsKoers = cf.parse(data);
        } catch (java.text.ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      }
      break;
      
    default:
      throw new RuntimeException("Illegal state: " + state);
    }
  }
  
  protected enum State {
    START,
    OPTION_SERIES,
    OPTION_SERIE;
  }
}