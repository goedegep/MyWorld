package goedegep.finan.stocks;

import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sax.ParseException;
import goedegep.util.sgml.SgmlUtil;
import goedegep.util.text.Indent;
import goedegep.util.xml.XmlUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class FundSharesContentHandler extends AbstractValidatingHandler<FundSharesContentHandler.State> {
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final PgCurrencyFormat  cf = new PgCurrencyFormat(0, false, false, true);

  private static String  currentFundSharesURI = null;
  private static String  schema = null;


  // Company info
  private Fund              fund       = null;
  private LinkedList<Share> fundShares = null;
  private Share             share      = null;
  private Redenomination    redenominationFrom = null;
  
  public FundSharesContentHandler() {
    state = State.START;
//    setPrintStateChanges(true);
  }

  public void read(String fundSharesURI) throws ParseException {
    try {
      getParser(null).parse(fundSharesURI, this);
      currentFundSharesURI = fundSharesURI;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    } catch (SAXException e) {
      throw new ParseException(fundSharesURI, locator.getLineNumber(),
          locator.getColumnNumber(), e.getMessage());
    }
  }

  public static void write() {
    StringBuffer output = new StringBuffer(2000);
    Indent       indent = new Indent(2);
    String       newline = System.getProperty("line.separator");

    if (currentFundSharesURI == null) {
      throw new RuntimeException("Geen URI voor de aandelen per fonds file.");
    }
    if (schema == null) {
      throw new RuntimeException("Geen schema voor de aandelen per fonds file.");
    }
    
    try
    {
      // Rename existing file to .bak file
      File file = new File(currentFundSharesURI);
      file.renameTo(new File(currentFundSharesURI + ".bak"));

      // Create an output writer that will write to that file.
      FileWriter out = new FileWriter(currentFundSharesURI);
      output.append(XmlUtil.createStartLine("UTF-8")).append(NEWLINE);
      output.append(XmlUtil.createRootElementOpen(getXmlNameSpaceName(), CompanyBasics.fundSharesTag, schema));
      indent.increment();

      // Build the String with all fundShares, the funds are handled alphabetically (as getFunds returns the funds sorted by name).
      for (Fund fund: Fund.getFunds()) {
       output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), CompanyBasics.fundSharesListTag)).append(newline);
       indent.increment();
       output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), CompanyBasics.fundNameTag, fund.getName())).append(newline);

       for (Share share: fund.getShares()) {
         output.append(toXmlString(indent, getXmlNameSpaceName(), share)).append(newline);
       }
       indent.decrement();
       output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), CompanyBasics.fundSharesListTag)).append(newline);
      }

      indent.decrement();  // not needed.
      output.append(XmlUtil.createRootElementClose(getXmlNameSpaceName(), CompanyBasics.fundSharesTag));

      // write to output
      out.write(output.toString());
      out.close();
    }
    catch (IOException e) {
      throw new RuntimeException("Fout bij het schrijven van de aandelen per fonds file. Systeem melding: " +
          e.getMessage() + ", " + e.getCause());
    }
  }

  public static String toXmlString(Indent indent, String nameSpace, Share share) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(SgmlUtil.createElementOpen(indent, nameSpace, CompanyBasics.shareTag));
    buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.shareNameTag, share.getName()));
    Redenomination redenominationFrom = share.getRedenominationFrom();
    if (redenominationFrom != null) {
      buf.append(SgmlUtil.createElementOpen(nameSpace, CompanyBasics.REDENOMINATION_FROM));
      buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.shareNameTag, redenominationFrom.getShare().getName())); 
      buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.FROM_AMOUNT, Integer.toString(redenominationFrom.getFromAmount())));
      buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.TO_AMOUNT, Integer.toString(redenominationFrom.getToAmount())));
      if (redenominationFrom.getKoers() != null) {
        buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.koersTag, cf.format(redenominationFrom.getKoers())));
      }
      if (redenominationFrom.getTerugBetaling() != null) {
        buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.TERUGBETALING_TAG, cf.format(redenominationFrom.getTerugBetaling())));
      }
      buf.append(SgmlUtil.createElementClose(nameSpace, CompanyBasics.REDENOMINATION_FROM));      
    }
    if (share.isObsolete()) {
      buf.append(SgmlUtil.createElementOpenClose(nameSpace, CompanyBasics.OBSOLETE_TAG));
    }
    buf.append(SgmlUtil.createElementClose(nameSpace, CompanyBasics.shareTag));
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
      if (tag.compareTo(CompanyBasics.fundSharesTag) == 0) {
        schema = attributes.getValue("xsi:noNamespaceSchemaLocation");
        pushState(State.FUND_SHARES);
      }
      break;

    case FUND_SHARES:
      if (tag.compareTo(CompanyBasics.fundSharesListTag) == 0) {
        pushState(State.FUND_SHARE_LIST);
        fund = null;
        fundShares = new LinkedList<Share>();
      }
      break;

    case FUND_SHARE_LIST:
      if (tag.compareTo(CompanyBasics.shareTag) == 0) {
        pushState(State.SHARE);
      }
      break;

    case SHARE:
      if (tag.compareTo(CompanyBasics.REDENOMINATION_FROM) == 0) {
        redenominationFrom = new Redenomination();
        pushState(State.REDENOMINATION_FROM);
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
    case FUND_SHARES:
      if (tag.compareTo(CompanyBasics.fundSharesTag) == 0) {
        popState();
      }
      break;

    case FUND_SHARE_LIST:
      if (tag.compareTo(CompanyBasics.fundSharesListTag) == 0) {
        fund.setShares(fundShares);
        popState();
      } else if (tag.compareTo(CompanyBasics.fundNameTag) == 0) {
        fund = Fund.getFund(data);
        if (fund == null) {
          throw new SAXParseException("Onbekend Fonds " + data, locator);
        }
      }
      break;

    case SHARE:
      if (tag.compareTo(CompanyBasics.shareTag) == 0) {
        share = null;
        popState();
      } else if (tag.compareTo(CompanyBasics.shareNameTag) == 0) {
        share = new Share(data, fund);
        fundShares.add(share);
      } else if (tag.compareTo(CompanyBasics.OBSOLETE_TAG) == 0) {
        share.setObsolete(true);
      }
      break;

    case REDENOMINATION_FROM:
      if (tag.compareTo(CompanyBasics.REDENOMINATION_FROM) == 0) {
        share.setRedenominationFrom(redenominationFrom);
        popState();
      } else if (tag.compareTo(CompanyBasics.shareNameTag) == 0) {
        redenominationFrom.setShare(Share.getShare(data));
      } else if (tag.compareTo(CompanyBasics.FROM_AMOUNT) == 0) {
        redenominationFrom.setFromAmount(Integer.parseInt(data));
      } else if (tag.compareTo(CompanyBasics.TO_AMOUNT) == 0) {
        redenominationFrom.setToAmount(Integer.parseInt(data));
      } else if (tag.compareTo(CompanyBasics.koersTag) == 0) {
        try {
          redenominationFrom.setKoers(cf.parse(data));
        } catch (java.text.ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (tag.compareTo(CompanyBasics.TERUGBETALING_TAG) == 0) {
        try {
          redenominationFrom.setTerugBetaling(cf.parse(data));
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
    FUND_SHARES,
    FUND_SHARE_LIST,
    SHARE,
    REDENOMINATION_FROM;
  }
}