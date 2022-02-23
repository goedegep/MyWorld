package goedegep.finan.stocks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sax.ParseException;
import goedegep.util.sgml.SgmlUtil;
import goedegep.util.text.Indent;
import goedegep.util.xml.XmlUtil;

public class ShareDividendsContentHandler extends AbstractValidatingHandler<ShareDividendsContentHandler.State> {
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final PgCurrencyFormat  cf = new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();

  private static String    currentShareDividendsURI = null;
  private static String    schema = null;
  
  // Share info
  private Share                      share          = null;
  private ShareDividend              dividend       = null;
  private LinkedList<ShareDividend>  shareDividends = null;    // of type ShareDividend
  private StockDividend              stockDividend  = null;
  private Drip                       drip           = null;
  
  public ShareDividendsContentHandler() {
    state = State.START;
//    setPrintStateChanges(true);
  }

  public void read(SAXParser parser, String shareDividendsURI) throws ParseException {
    try {
      getParser(parser).parse(shareDividendsURI, this);
      currentShareDividendsURI = shareDividendsURI;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    } catch (SAXException e) {
      throw new ParseException(currentShareDividendsURI, locator.getLineNumber(),
          locator.getColumnNumber(), e.getMessage());
    }
  }

  public static void write() {
    StringBuilder output = new StringBuilder();
    Indent        indent = new Indent(2);
    String        newline = System.getProperty("line.separator");

    if (currentShareDividendsURI == null) {
      throw new RuntimeException("Geen URI voor de dividenden file.");
    }
    if (schema == null) {
      throw new RuntimeException("Geen schema voor de dividenden file.");
    }
    
    try
    {
      // Rename existing file to .bak file
      File file = new File(currentShareDividendsURI);
      file.renameTo(new File(currentShareDividendsURI + ".bak"));

      // Create an output writer that will write to that file.
      FileWriter out = new FileWriter(currentShareDividendsURI);
      output.append(XmlUtil.createStartLine("UTF-8")).append(NEWLINE);
      output.append(XmlUtil.createRootElementOpen(getXmlNameSpaceName(), CompanyBasics.shareDividendsTag, schema));
      indent.increment();

      // Build the String with all shareDividends
      for (Share share: Share.getShares()) {
       output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), CompanyBasics.shareDividendsListTag)).append(newline);
       indent.increment();
       output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), CompanyBasics.shareNameTag, share.getName())).append(newline);

       for (ShareDividend dividend: share.getDividends()) {
         output.append(dividendToXmlString(indent, getXmlNameSpaceName(), dividend)).append(newline);
       }
       indent.decrement();
       output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(),CompanyBasics.shareDividendsListTag)).append(newline);
      }

      indent.decrement();
      output.append(XmlUtil.createRootElementClose(getXmlNameSpaceName(), CompanyBasics.shareDividendsTag));

      // write to output
      out.write(output.toString());
      out.close();
    }
    catch (IOException e) {
      throw new RuntimeException("Fout bij het schrijven van de dividended file. Systeem melding: " +
          e.getMessage() + ", " + e.getCause());
    }
  }

  public static String dividendToXmlString(Indent indent, String nameSpace, ShareDividend dividend) {
    StringBuffer buf = new StringBuffer();
    buf.append(SgmlUtil.createElementOpen(indent, nameSpace, CompanyBasics.dividendTag));
    if (dividend.getName() != null) {
      buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.dividendNameTag, dividend.getName()));
    }
    if (dividend.getYear() != null) {
      buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.dividendYearTag, dividend.getYear().toString()));
    }
    String s = null;
    if (dividend.getType() == DividendType.CASH) {
      s = CompanyBasics.dividendTypeContant;
    } else if (dividend.getType() == DividendType.STOCK) {
      s = CompanyBasics.dividendTypeStock;
    } else if (dividend.getType() == DividendType.STOCK_OR_CASH) {
      s = CompanyBasics.dividendTypeContantOfStock;
    } else if (dividend.getType() == DividendType.DRIP) {
      s = CompanyBasics.dividendTypeDrip;
    }
    buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.dividendTypeTag, s));
    if (dividend.getAmount() != null) {
      buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.dividendAmountTag, cf.format(dividend.getAmount())));
    }
    if (dividend.getStockDividend()!= null) {
      buf.append(stockDividendToXmlString(nameSpace, dividend.getStockDividend()));
    }
    if (dividend.getType() == DividendType.DRIP) {
      Drip drip = dividend.getDrip();
      buf.append(SgmlUtil.createElementOpen(nameSpace, CompanyBasics.DRIP_TAG));
      buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.FROM_AMOUNT, FPVF.format(drip.getFromAmount()))); 
      buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.PRICE_PER_SHARE_TAG, cf.format(drip.getPricePerShare())));
      buf.append(SgmlUtil.createElementClose(nameSpace, CompanyBasics.DRIP_TAG));      
    }
    if (dividend.getTaxPercentage() != null) {
      buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.TAX_PERCENTAGE_TAG, dividend.getTaxPercentage().toString()));
    }
    buf.append(SgmlUtil.createElementClose(nameSpace, CompanyBasics.dividendTag));

    return buf.toString();
  }
  
  public static String stockDividendToXmlString(String nameSpace, StockDividend stockDividend) {
    StringBuilder buf = new StringBuilder();
    buf.append(SgmlUtil.createElementOpen(nameSpace, CompanyBasics.stockDividendTag));
    if (stockDividend.getFromAmount() != -1) {
      buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.FROM_AMOUNT, Integer.toString(stockDividend.getFromAmount())));      
    }
    if (stockDividend.getToAmount() != -1) {
      buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.TO_AMOUNT, Integer.toString(stockDividend.getToAmount())));      
    }
    if (stockDividend.getKoers() != null) {
      buf.append(SgmlUtil.createElement(nameSpace, CompanyBasics.koersTag, cf.format(stockDividend.getKoers())));
    }
    buf.append(SgmlUtil.createElementClose(nameSpace, CompanyBasics.stockDividendTag));

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
      if (tag.compareTo(CompanyBasics.shareDividendsTag) == 0) {
        schema = attributes.getValue("xsi:noNamespaceSchemaLocation");
        pushState(State.SHARE_DIVIDENDS);
      }
      break;

    case SHARE_DIVIDENDS:
      if (tag.compareTo(CompanyBasics.shareDividendsListTag) == 0) {
        pushState(State.SHARE_DIVIDEND_LIST);
        share = null;
        shareDividends = new LinkedList<ShareDividend>();
      }
      break;

    case SHARE_DIVIDEND_LIST:
      if (tag.compareTo(CompanyBasics.dividendTag) == 0) {
        pushState(State.DIVIDEND);
        dividend = new ShareDividend();
        dividend.setShare(share);
      }
      break;

    case DIVIDEND:
      if (tag.compareTo(CompanyBasics.stockDividendTag) == 0) {
        pushState(State.STOCK_DIVIDEND);
        stockDividend = new StockDividend();
      } else if (tag.compareTo(CompanyBasics.DRIP_TAG) == 0) {
        pushState(State.DRIP);
        drip = new Drip();
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

//    System.out.println("ShareDividendsContentHandler: endElement = " + localpart);

    switch (state) {
    case SHARE_DIVIDENDS:
      if (tag.compareTo(CompanyBasics.shareDividendsTag) == 0) {
        popState();
      }
      break;

    case SHARE_DIVIDEND_LIST:
      if (tag.compareTo(CompanyBasics.shareNameTag) == 0) {
        share = Share.getShare(data);
        if (share == null) {
          throw new SAXParseException("Onbekend aandeel " + data, locator);
        }
      } else if (tag.compareTo(CompanyBasics.shareDividendsListTag) == 0) {
        share.setDividends(shareDividends);
        popState();
      }
      break;

    case DIVIDEND:
      if (tag.compareTo(CompanyBasics.dividendTag) == 0) {
        shareDividends.add(dividend);
        popState();
      } else if (tag.compareTo(CompanyBasics.dividendNameTag) == 0) {
        dividend.setName(data);
      } else if (tag.compareTo(CompanyBasics.dividendYearTag) == 0) {
        Integer year = Integer.valueOf(data);
        dividend.setYear(year.intValue());
      } else if (tag.compareTo(CompanyBasics.dividendTypeTag) == 0) {
        DividendType dividendType = null;
        if (data.compareTo(CompanyBasics.dividendTypeContant) == 0) {
          dividendType = DividendType.CASH;
        } else if (data.compareTo(CompanyBasics.dividendTypeContantOfStock) == 0) {
          dividendType = DividendType.STOCK_OR_CASH;
        } else if (data.compareTo(CompanyBasics.dividendTypeStock) == 0) {
          dividendType = DividendType.STOCK;
        } else if (data.compareTo(CompanyBasics.dividendTypeDrip) == 0) {
          dividendType = DividendType.DRIP;
        } else {
          throw new SAXParseException("Onbekend dividend type " + data, locator);
        }
        dividend.setType(dividendType);
      } else if (tag.compareTo(CompanyBasics.dividendAmountTag) == 0) {
        try {
          dividend.setAmount(cf.parse(data));
        } catch (java.text.ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (tag.compareTo(CompanyBasics.TAX_PERCENTAGE_TAG) == 0) {
        dividend.setTaxPercentage(Integer.parseInt(data));
      }
      break;

    case STOCK_DIVIDEND:
      if (tag.compareTo(CompanyBasics.stockDividendTag) == 0) {
        dividend.setStockDividend(stockDividend);
        popState();
        stockDividend = null;
      } else if (tag.compareTo(CompanyBasics.FROM_AMOUNT) == 0) {
        stockDividend.setFromAmount(Integer.parseInt(data));
        data = null;
      } else if (tag.compareTo(CompanyBasics.TO_AMOUNT) == 0) {
        stockDividend.setToAmount(Integer.parseInt(data));
        data = null;
      } else if (tag.compareTo(CompanyBasics.koersTag) == 0) {
        try {
          stockDividend.setKoers(cf.parse(data));
        } catch (java.text.ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
        data = null;
      }
      break;

    case DRIP:
      if (tag.compareTo(CompanyBasics.DRIP_TAG) == 0) {
        dividend.setDrip(drip);
        popState();
        drip = null;
      } else if (tag.compareTo(CompanyBasics.FROM_AMOUNT) == 0) {
        try {
          drip.setFromAmount(FPVF.parse(data, 1000));
        } catch (java.text.ParseException e) {
          throw new SAXParseException("Ongeldig formaat voor aantal: " + data, locator);
        }
        data = null;
      } else if (tag.compareTo(CompanyBasics.PRICE_PER_SHARE_TAG) == 0) {
        try {
          drip.setPricePerShare(cf.parse(data));
        } catch (java.text.ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
        data = null;
      }
      break;
      
    default:
      throw new RuntimeException("Illegal state: " + state);
    }
  }
  
  protected enum State {
    START,
    SHARE_DIVIDENDS,
    SHARE_DIVIDEND_LIST,
    DIVIDEND,
    STOCK_DIVIDEND,
    DRIP;
  }
}