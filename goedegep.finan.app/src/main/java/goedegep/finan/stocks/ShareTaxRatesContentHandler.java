package goedegep.finan.stocks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import goedegep.util.datetime.Quarter;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.sax.AbstractValidatingHandler;
import goedegep.util.sax.ParseException;
import goedegep.util.sgml.SgmlUtil;
import goedegep.util.text.Indent;
import goedegep.util.xml.XmlUtil;

public class ShareTaxRatesContentHandler extends AbstractValidatingHandler<ShareTaxRatesContentHandler.State> {
  private static final String NEWLINE = System.getProperty("line.separator");
  private static final PgCurrencyFormat  cf = new PgCurrencyFormat(0, false, false, true);

  private static String    currentTaxRatesURI = null;
  private static String    schema = null;
  
  // Share info
  private Integer         jaar;
  private Integer         kwartaal;
  private Share           share;
  private ShareDividend   shareDividend;
  
  // Option series info
  private OptionType      optionType;
  private int             expirationMonth;
  private int             expirationYear;
  private PgCurrency      uitoefeningsKoers;
  
  public ShareTaxRatesContentHandler() {
    state = State.START;
//  setPrintStateChanges(true);
  }
  
  public void read(SAXParser parser, String taxRatesURI) throws ParseException {
    try {
      getParser(parser).parse(taxRatesURI, this);
      currentTaxRatesURI = taxRatesURI;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    } catch (SAXException e) {
      throw new ParseException(taxRatesURI, locator.getLineNumber(),
          locator.getColumnNumber(), e.getMessage());
    }
  }

  public static void write() {
    // TODO rewrite using a buffered writer.
    StringBuilder output = new StringBuilder();
    Indent        indent = new Indent(2);
    String        newline = System.getProperty("line.separator");

    if (currentTaxRatesURI == null) {
      throw new RuntimeException("Geen URI voor de belasting koersen file.");
    }
    if (schema == null) {
      throw new RuntimeException("Geen schema voor de belasting koersen file.");
    }
    
    try
    {
      // Rename existing file to .bak file
      File file = new File(currentTaxRatesURI);
      file.renameTo(new File(currentTaxRatesURI + ".bak"));
      
      Collection<KoersenPerKwartaal> kwartaalKoersen = createQuarterRates();
      // TODO Opties

      // Create an output writer that will write to that file.
      FileWriter out = new FileWriter(currentTaxRatesURI);
      output.append(XmlUtil.createStartLine("UTF-8")).append(NEWLINE);
      output.append(XmlUtil.createRootElementOpen(getXmlNameSpaceName(), CompanyBasics.BELASTING_KOERSEN_TAG, schema));
      indent.increment();

      // Build the String with all tax rates for all quarters, for all shares.
      for (KoersenPerKwartaal koersenPerKwartaal: kwartaalKoersen) {
        output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), CompanyBasics.KOERSEN_PER_KWARTAAL_TAG)).append(newline);
        indent.increment();
        output.append(SgmlUtil.createElement(indent, getXmlNameSpaceName(), CompanyBasics.JAAR_TAG, String.valueOf(koersenPerKwartaal.getJaar())));
        output.append(SgmlUtil.createElement(getXmlNameSpaceName(), CompanyBasics.KWARTAAL_TAG, String.valueOf(koersenPerKwartaal.getKwartaal()))).append(newline);
        output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), CompanyBasics.KOERSEN_TAG)).append(newline);
        indent.increment();
        
        for (AandeelKoersInfo aandeelKoersInfo: koersenPerKwartaal.getShareRates()) {
          output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), CompanyBasics.AANDEEL_KOERS_INFO_TAG));
          output.append(SgmlUtil.createElement(getXmlNameSpaceName(), CompanyBasics.AANDEEL_TAG, aandeelKoersInfo.getAandeel().getName()));
          output.append(SgmlUtil.createElement(getXmlNameSpaceName(), CompanyBasics.koersTag, cf.format(aandeelKoersInfo.getKoers())));
          output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(),CompanyBasics.AANDEEL_KOERS_INFO_TAG)).append(newline);
        }
        
        for (StockDividendKoersInfo stockDividendKoersInfo: koersenPerKwartaal.getStockDividendRates()) {
          output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), CompanyBasics.STOCKDIVIDEND_KOERS_INFO_TAG));
          output.append(SgmlUtil.createElement(getXmlNameSpaceName(), CompanyBasics.AANDEEL_TAG, stockDividendKoersInfo.getDividend().getShare().getName()));
          output.append(SgmlUtil.createElement(getXmlNameSpaceName(), CompanyBasics.DIVIDEND_REFERENTIE_TAG, stockDividendKoersInfo.getDividend().getReferenceString()));
          output.append(SgmlUtil.createElement(getXmlNameSpaceName(), CompanyBasics.koersTag, cf.format(stockDividendKoersInfo.getKoers())));
          output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(),CompanyBasics.STOCKDIVIDEND_KOERS_INFO_TAG)).append(newline);
        }
        
        for (OptionSerieKoersInfo optionSerieKoersInfo: koersenPerKwartaal.getOptionSerieRates()) {
          output.append(SgmlUtil.createElementOpen(indent, getXmlNameSpaceName(), CompanyBasics.OPTIE_KOERS_INFO_TAG));
          output.append(SgmlUtil.createElement(getXmlNameSpaceName(), CompanyBasics.TYPE_TAG, optionSerieKoersInfo.getOptionSerie().getOptionType().getText()));
          output.append(SgmlUtil.createElement(getXmlNameSpaceName(), CompanyBasics.FONDS_NAAM_TAG, optionSerieKoersInfo.getOptionSerie().getShare().getName()));
          output.append(SgmlUtil.createElement(getXmlNameSpaceName(), CompanyBasics.MAAND_TAG, Integer.toString(optionSerieKoersInfo.getOptionSerie().getExpirationMonth())));
          output.append(SgmlUtil.createElement(getXmlNameSpaceName(), CompanyBasics.JAAR_TAG, Integer.toString(optionSerieKoersInfo.getOptionSerie().getExpirationYear())));
          output.append(SgmlUtil.createElement(getXmlNameSpaceName(), CompanyBasics.UITOEFENINGS_KOERS_TAG, cf.format(optionSerieKoersInfo.getOptionSerie().getUitoefeningsKoers())));
          output.append(SgmlUtil.createElement(getXmlNameSpaceName(), CompanyBasics.koersTag, cf.format(optionSerieKoersInfo.getKoers())));
          output.append(SgmlUtil.createElementClose(getXmlNameSpaceName(),CompanyBasics.OPTIE_KOERS_INFO_TAG)).append(newline);
        }
        
        indent.decrement();
        output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(), CompanyBasics.KOERSEN_TAG)).append(newline);
        indent.decrement();
        output.append(SgmlUtil.createElementClose(indent, getXmlNameSpaceName(),CompanyBasics.KOERSEN_PER_KWARTAAL_TAG)).append(newline);
      }

      indent.decrement();
      output.append(XmlUtil.createRootElementClose(getXmlNameSpaceName(), CompanyBasics.BELASTING_KOERSEN_TAG));

      // write to output
      out.write(output.toString());
      out.close();
    }
    catch (IOException e) {
      throw new RuntimeException("Fout bij het schrijven van de belasting koersen file. Systeem melding: " +
          e.getMessage() + ", " + e.getCause());
    }
  }

  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
//    System.out.println("ShareTaxRatesContentHandler: startElement = " + localpart);
    String tag = localpart;
    if ("".equals(tag)) { // not namespace-aware
      tag = rawname;
    }
    data = null;

    switch (state) {
    case START:
      if (tag.compareTo(CompanyBasics.BELASTING_KOERSEN_TAG) == 0) {
        schema = attributes.getValue("xsi:noNamespaceSchemaLocation");
        pushState(State.BELASTING_KOERSEN);
      }
      break;

    case BELASTING_KOERSEN:
      if (tag.compareTo(CompanyBasics.KOERSEN_PER_KWARTAAL_TAG) == 0) {
        pushState(State.KOERSEN_PER_KWARTAAL);
        jaar = null;
        kwartaal = null;
      }
      break;

    case KOERSEN_PER_KWARTAAL:
      if (tag.compareTo(CompanyBasics.KOERSEN_TAG) == 0) {
        pushState(State.KOERSEN);
      }
      break;
      
    case KOERSEN:
      if (tag.compareTo(CompanyBasics.AANDEEL_KOERS_INFO_TAG) == 0) {
        pushState(State.AANDEEL_KOERSINFO);
      } else if (tag.compareTo(CompanyBasics.STOCKDIVIDEND_KOERS_INFO_TAG) == 0) {
        pushState(State.STOCKDIVIDEND_KOERSINFO);
      } else if (tag.compareTo(CompanyBasics.OPTIE_KOERS_INFO_TAG) == 0) {
        pushState(State.OPTIE_KOERSINFO);
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

//    System.out.println("ShareTaxRatesContentHandler: endElement = " + localpart);

    switch (state) {
    case BELASTING_KOERSEN:
      if (tag.compareTo(CompanyBasics.BELASTING_KOERSEN_TAG) == 0) {
        popState();
      }
      break;

    case KOERSEN_PER_KWARTAAL:
      if (tag.compareTo(CompanyBasics.KOERSEN_PER_KWARTAAL_TAG) == 0) {
        popState();
      } else if (tag.compareTo(CompanyBasics.JAAR_TAG) == 0) {
        jaar = Integer.valueOf(data);
      } else if (tag.compareTo(CompanyBasics.KWARTAAL_TAG) == 0) {
        kwartaal = Integer.valueOf(data);
      }
      break;

    case KOERSEN:
      if (tag.compareTo(CompanyBasics.KOERSEN_TAG) == 0) {
        popState();
      }
      break;

    case AANDEEL_KOERSINFO:
      if (tag.compareTo(CompanyBasics.AANDEEL_KOERS_INFO_TAG) == 0) {
        popState();
      } else if (tag.compareTo(CompanyBasics.AANDEEL_TAG) == 0) {
        share = Share.getShare(data);
        if (share == null) {
          throw new SAXParseException("Effect " + data + " niet gevonden voor koersinformatie, jaar = " + jaar + ", kwartaal = " + kwartaal, locator);
        }
      } else if (tag.compareTo(CompanyBasics.koersTag) == 0) {
        try {
          share.addQuarterRate(jaar, kwartaal, cf.parse(data));
        } catch (java.text.ParseException e) {
          throw new SAXParseException("Ongeldig koers formaat: " + data, locator);
        }
      }
      break;

    case STOCKDIVIDEND_KOERSINFO:
      if (tag.compareTo(CompanyBasics.STOCKDIVIDEND_KOERS_INFO_TAG) == 0) {
        popState();
      } else if (tag.compareTo(CompanyBasics.AANDEEL_TAG) == 0) {
        share = Share.getShare(data);
        if (share == null) {
          throw new SAXParseException("Effect " + data + "niet gevonden voor koersinformatie, jaar = " + jaar + ", kwartaal = " + kwartaal, locator);
        }
      } else if (tag.compareTo(CompanyBasics.DIVIDEND_REFERENTIE_TAG) == 0) {
        shareDividend = share.getDividend(data);
      } else if (tag.compareTo(CompanyBasics.koersTag) == 0) {
        try {
          shareDividend.getStockDividend().addQuarterRate(jaar, kwartaal, cf.parse(data));
        } catch (java.text.ParseException e) {
          throw new SAXParseException("Ongeldig koers formaat: " + data, locator);
        }
      }
      break;

    case OPTIE_KOERSINFO:
      if (tag.compareTo(CompanyBasics.OPTIE_KOERS_INFO_TAG) == 0) {
        popState();
      } else if (tag.compareTo(CompanyBasics.TYPE_TAG) == 0) {
        optionType = OptionType.getOptionTypeForText(data);
      } else if (tag.compareTo(CompanyBasics.FONDS_NAAM_TAG) == 0) {
        share = Share.getShare(data);
        if (share == null) {
          throw new SAXParseException("Effect " + data + "niet gevonden voor koersinformatie, jaar = " + jaar + ", kwartaal = " + kwartaal, locator);
        }
      } else if (tag.compareTo(CompanyBasics.MAAND_TAG) == 0) {
        expirationMonth = Integer.parseInt(data);
      } else if (tag.compareTo(CompanyBasics.JAAR_TAG) == 0) {
        expirationYear = Integer.parseInt(data);
      } else if (tag.compareTo(CompanyBasics.UITOEFENINGS_KOERS_TAG) == 0) {
        try {
          uitoefeningsKoers = cf.parse(data);
        } catch (java.text.ParseException e) {
          throw new SAXParseException("Ongeldig koers formaat: " + data, locator);
        }
      } else if (tag.compareTo(CompanyBasics.koersTag) == 0) {
        OptionSerie optionSerie = OptionSerie.getOptionSerie(optionType, share, expirationMonth, expirationYear, uitoefeningsKoers);
        if (optionSerie == null) {
          throw new SAXParseException("Optie serie " + optionType + " " + share.getName() + " " + expirationMonth + " "  + expirationYear + " niet gevonden voor koersinformatie, jaar = " + jaar + ", kwartaal = " + kwartaal, locator);
        }
        try {
          optionSerie.addQuarterRate(jaar, kwartaal, cf.parse(data));
        } catch (java.text.ParseException e) {
          throw new SAXParseException("Ongeldig koers formaat: " + data, locator);
        }
      }
      break;
      
    default:
      throw new RuntimeException("Illegal state: " + state);
    }
  }
  
  private static Collection<KoersenPerKwartaal> createQuarterRates() {
    ShareTaxRatesContentHandler ch = new ShareTaxRatesContentHandler();
    SortedMap<Quarter, KoersenPerKwartaal> kwartaalKoersen = new TreeMap<Quarter, KoersenPerKwartaal>();
    for (Share share: Share.getShares()) {      
      // Get share quarter rates
      for (Quarter quarter: share.getQuarterRateKeys()) {
        KoersenPerKwartaal koersenPerKwartaal = kwartaalKoersen.get(quarter);        
        int jaar = quarter.getYear();
        int kwartaal = quarter.getQuarter();
        
        if (koersenPerKwartaal == null) {
          koersenPerKwartaal = ch.new KoersenPerKwartaal(jaar, kwartaal);
          kwartaalKoersen.put(quarter, koersenPerKwartaal);
        }
        
        AandeelKoersInfo aandeelKoersInfo = ch.new AandeelKoersInfo(share, share.getQuarterRate(jaar, kwartaal));
        koersenPerKwartaal.addShareRate(aandeelKoersInfo);
      }
      
      // Get the stockdividends quarter rates
      for (ShareDividend shareDividend: share.getDividends()) {
        StockDividend stockDividend = shareDividend.getStockDividend();
        if (stockDividend != null) {
          Collection<Quarter> quarters = stockDividend.getQuarterRateKeys();
          if (quarters != null) {
            for (Quarter quarter: quarters) {
              KoersenPerKwartaal koersenPerKwartaal = kwartaalKoersen.get(quarter);        
              int jaar = quarter.getYear();
              int kwartaal = quarter.getQuarter();
              
              if (koersenPerKwartaal == null) {
                koersenPerKwartaal = ch.new KoersenPerKwartaal(jaar, kwartaal);
                kwartaalKoersen.put(quarter, koersenPerKwartaal);
              }        
              
              StockDividendKoersInfo stockDividendKoersInfo = ch.new StockDividendKoersInfo(shareDividend, stockDividend.getQuarterRate(jaar, kwartaal));
              koersenPerKwartaal.addStockDividendRate(stockDividendKoersInfo);
            }
          }
        }
      }
    }
    
    // Get option series quarter rates
    for (OptionSerie optionSerie: OptionSerie.getOptionSeries()) {
      for (Quarter quarter: optionSerie.getQuarterRateKeys()) {
        KoersenPerKwartaal koersenPerKwartaal = kwartaalKoersen.get(quarter);        
        int jaar = quarter.getYear();
        int kwartaal = quarter.getQuarter();
        
        if (koersenPerKwartaal == null) {
          koersenPerKwartaal = ch.new KoersenPerKwartaal(jaar, kwartaal);
          kwartaalKoersen.put(quarter, koersenPerKwartaal);
        }
        
        OptionSerieKoersInfo optionSerieKoersInfo = ch.new OptionSerieKoersInfo(optionSerie, optionSerie.getQuarterRate(jaar, kwartaal));
        koersenPerKwartaal.addOptionSerieRate(optionSerieKoersInfo);
      }
    }
    
    return kwartaalKoersen.values();
  }

  enum State {
    START,
    BELASTING_KOERSEN,
    KOERSEN_PER_KWARTAAL,
    KOERSEN,
    AANDEEL_KOERSINFO,
    STOCKDIVIDEND_KOERSINFO,
    OPTIE_KOERSINFO;
  }
  
  public class AandeelKoersInfo {
    Share       aandeel;
    PgCurrency  koers;
    
    public AandeelKoersInfo(Share aandeel, PgCurrency koers) {
      this.aandeel = aandeel;
      this.koers = koers;
    }

    public Share getAandeel() {
      return aandeel;
    }

    public PgCurrency getKoers() {
      return koers;
    }
  }
  
  public class StockDividendKoersInfo {
    ShareDividend  dividend;
    PgCurrency     koers;
    
    public StockDividendKoersInfo(ShareDividend dividend, PgCurrency koers) {
      this.dividend = dividend;
      this.koers = koers;
    }

    public ShareDividend getDividend() {
      return dividend;
    }

    public PgCurrency getKoers() {
      return koers;
    }
  }
  
  public class OptionSerieKoersInfo {
    OptionSerie  optionSerie;
    PgCurrency   koers;
    
    public OptionSerieKoersInfo(OptionSerie optionSerie, PgCurrency koers) {
      this.optionSerie = optionSerie;
      this.koers = koers;
    }

    public OptionSerie getOptionSerie() {
      return optionSerie;
    }

    public PgCurrency getKoers() {
      return koers;
    }
  }
  
  public class KoersenPerKwartaal implements Comparator<KoersenPerKwartaal> {
    private int jaar;
    private int kwartaal;
    private List<AandeelKoersInfo> shareRates = new ArrayList<AandeelKoersInfo>();
    private List<StockDividendKoersInfo>  stockDividendRates = new ArrayList<StockDividendKoersInfo>();
    private List<OptionSerieKoersInfo> optionSerieRates = new ArrayList<OptionSerieKoersInfo>();
    
    public KoersenPerKwartaal(int jaar, int kwartaal) {
      this.jaar = jaar;
      this.kwartaal = kwartaal;
    }
    
    public int getJaar() {
      return jaar;
    }

    public int getKwartaal() {
      return kwartaal;
    }

    public List<AandeelKoersInfo> getShareRates() {
      return shareRates;
    }

    void addShareRate(AandeelKoersInfo aandeelKoersInfo) {
      shareRates.add(aandeelKoersInfo);
    }
    
    public List<StockDividendKoersInfo> getStockDividendRates() {
      return stockDividendRates;
    }
    
    void addStockDividendRate(StockDividendKoersInfo stockDividendKoersInfo) {
      stockDividendRates.add(stockDividendKoersInfo);
    }
    
    public List<OptionSerieKoersInfo> getOptionSerieRates() {
      return optionSerieRates;
    }
    
    void addOptionSerieRate(OptionSerieKoersInfo optionSerieKoersInfo) {
      optionSerieRates.add(optionSerieKoersInfo);
    }
    
    public int compare(KoersenPerKwartaal kpkt1, KoersenPerKwartaal kpk2) {
      if (kpkt1.jaar < kpk2.jaar) {
        return -1;
      } else if (kpkt1.jaar > kpk2.jaar) {
        return 1;
      } else if (kpkt1.kwartaal < kpk2.kwartaal) {
        return -1;
      } else if (kpkt1.kwartaal > kpk2.kwartaal) {
        return 1;
      } else {
        return 0;
      }
    }
  }
}