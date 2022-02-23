package goedegep.finan.stocks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sax.ParseException;
import goedegep.util.sgml.SgmlUtil;
import goedegep.util.text.Indent;
import goedegep.util.xml.XmlUtil;

public class ClaimEmissionsContentHandler extends AbstractValidatingHandler<ClaimEmissionsContentHandler.State> {
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final PgCurrencyFormat  cf = new PgCurrencyFormat(0, false, false, true);

  private static String                  currentClaimEmissionsURI = null;
  private static String                  schema = null;
  
  // Share info
  private Share                        share;
  private ClaimEmission                claimEmission;

  public ClaimEmissionsContentHandler() {
    state = State.START;
//  setPrintStateChanges(true);
  }
  
  public void read(SAXParser parser, String claimEmissionsURI) throws ParseException {
    try {
      getParser(parser).parse(claimEmissionsURI, this);
      currentClaimEmissionsURI = claimEmissionsURI;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    } catch (SAXException e) {
      throw new ParseException(claimEmissionsURI, locator.getLineNumber(),
          locator.getColumnNumber(), e.getMessage());
    }
  }

  public static void write() {
    StringBuffer output = new StringBuffer();
    Indent       indent = new Indent(2);

    if (currentClaimEmissionsURI == null) {
      throw new RuntimeException("Geen URI voor de claim emissies file.");
    }
    if (schema == null) {
      throw new RuntimeException("Geen schema voor de claim emissies file.");
    }
    
    try
    {
      // Rename existing file to .bak file
      File file = new File(currentClaimEmissionsURI);
      file.renameTo(new File(currentClaimEmissionsURI + ".bak"));

      // Create an output writer that will write to that file.
      FileWriter out = new FileWriter(currentClaimEmissionsURI);
      output.append(XmlUtil.createStartLine("UTF-8")).append(NEWLINE);
      output.append(XmlUtil.createRootElementOpen(getXmlNameSpaceName(), CompanyBasics.CLAIM_EMISSIONS_TAG, schema));
      indent.increment();

      // Build the String with all claim emissions.
      for (Share share: Share.getShares()) {
        for (ClaimEmission claimEmission: share.getClaimEmissions()) {
          output.append(claimEmissionToXmlString(indent, getXmlNameSpaceName(), claimEmission));          
        }
      }

      indent.decrement();
      output.append(XmlUtil.createRootElementClose(getXmlNameSpaceName(), CompanyBasics.CLAIM_EMISSIONS_TAG));

      // write to output
      out.write(output.toString());
      out.close();
    }
    catch (IOException e) {
      throw new RuntimeException("Fout bij het schrijven van de claim emissies file. Systeem melding: " +
          e.getMessage() + ", " + e.getCause());
    }
  }

  public static String claimEmissionToXmlString(Indent indent, String nameSpace, ClaimEmission claimEmission) {
    StringBuilder buf = new StringBuilder();
    String newline = System.getProperty("line.separator");
    
    buf.append(SgmlUtil.createElementOpen(indent, nameSpace, CompanyBasics.CLAIM_EMISSION_TAG));
    buf.append(newline);
    indent.increment();

    buf.append(SgmlUtil.createElement(indent, nameSpace, CompanyBasics.shareNameTag, claimEmission.getShare().getName()));
    buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.CLAIM_ID_TAG, claimEmission.getId())); 
    buf.append(newline);
    buf.append(SgmlUtil.createElement(indent, nameSpace, CompanyBasics.FROM_AMOUNT, Integer.toString(claimEmission.getFromAmount())));
    buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.TO_AMOUNT, Integer.toString(claimEmission.getToAmount())));
    buf.append(newline);
    buf.append(SgmlUtil.createElement(indent, nameSpace, CompanyBasics.PRICE_PER_SHARE_TAG, cf.format(claimEmission.getPricePerShare())));
    buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.RIGHT_RATE_TAG, cf.format(claimEmission.getRightRate())));
    buf.append(newline);
    
    indent.decrement();
    buf.append(SgmlUtil.createElementClose(indent, nameSpace, CompanyBasics.CLAIM_EMISSION_TAG));
    buf.append(newline);
    
    return buf.toString();
  }

  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
//    System.out.println("ClaimEmissionsContentHandler: startElement = " + localpart);
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }
    data = null;

    switch (state) {
    case START:
      if (tag.compareTo(CompanyBasics.CLAIM_EMISSIONS_TAG) == 0) {
        schema = attributes.getValue("xsi:noNamespaceSchemaLocation");
        pushState(State.CLAIM_EMISSIONS);
      }
      break;

    case CLAIM_EMISSIONS:
      if (tag.compareTo(CompanyBasics.CLAIM_EMISSION_TAG) == 0) {
        pushState(State.CLAIM_EMISSION);
        claimEmission = new ClaimEmission();
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

//    System.out.println("ClaimEmissionsContentHandler: endElement = " + localpart);

    switch (state) {
    case START:
      // in this state nothing should happen
      break;

    case CLAIM_EMISSIONS:
      if (tag.compareTo(CompanyBasics.CLAIM_EMISSIONS_TAG) == 0) {
        popState();
      }
      break;

    case CLAIM_EMISSION:
      if (tag.compareTo(CompanyBasics.CLAIM_EMISSION_TAG) == 0) {
        share.addClaimEmission(claimEmission);
        popState();
      } else if (tag.compareTo(CompanyBasics.shareNameTag) == 0) {
        share = Share.getShare(data);
        if (share == null) {
          throw new SAXParseException("Onbekend aandeel " + data, locator);
        }
        claimEmission.setShare(share);
      } else if (tag.compareTo(CompanyBasics.CLAIM_ID_TAG) == 0) {
        claimEmission.setId(data);
      } else if (tag.compareTo(CompanyBasics.FROM_AMOUNT) == 0) {
        claimEmission.setFromAmount(Integer.parseInt(data));
      } else if (tag.compareTo(CompanyBasics.TO_AMOUNT) == 0) {
        claimEmission.setToAmount(Integer.parseInt(data));
      } else if (tag.compareTo(CompanyBasics.PRICE_PER_SHARE_TAG) == 0) {
        try {
          claimEmission.setPricePerShare(cf.parse(data));
        } catch (java.text.ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (tag.compareTo(CompanyBasics.RIGHT_RATE_TAG) == 0) {
        try {
          claimEmission.setRightRate(cf.parse(data));
        } catch (java.text.ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      }
      break;
    }
  }
  
  protected enum State {
    START,
    CLAIM_EMISSIONS,
    CLAIM_EMISSION;
  }
}