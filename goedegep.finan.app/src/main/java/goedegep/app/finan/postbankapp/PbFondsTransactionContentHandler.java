package goedegep.app.finan.postbankapp;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import goedegep.app.finan.gen.AccountContentHandler;
import goedegep.app.finan.gen.XmlTags;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.FinanTransaction;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.SumAccount;
import goedegep.finan.postbank.pbfonds.PbFondsDividend;
import goedegep.finan.postbank.pbfonds.PbFondsJaaroverzicht;
import goedegep.finan.postbank.pbfonds.PbFondsOverschrijving;
import goedegep.finan.postbank.pbfonds.PbFondsStockDividend;
import goedegep.finan.postbank.pbfonds.PbFondsTransaction;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.sgml.SgmlUtil;

public class PbFondsTransactionContentHandler 
                extends AccountContentHandler<PbFondsTransactionContentHandler.State> {
  // Formatters/parsers
  private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  private SumAccount  sumAccount = null; // The SumAccount to which transactions are to be added.
  private Bank        bank = null;
  private PgAccount   account = null;
  
  private PbFondsOverschrijving                   _fondsOverschrijving = null;
  private PbFondsDividend                         _fondsDividend = null;
  private PbFondsStockDividend                    _fondsStockDividend = null;
  private PbFondsJaaroverzicht                    _fondsJaaroverzicht = null;

  public void setAccount(SumAccount sumAccount, Bank bank, PgAccount account) {
    this.sumAccount = sumAccount;
    this.bank = bank;
    this.account = account;
    state = State.START;
  }
  
  public String transactionToXmlString(PgTransaction transaction, String nameSpace) {
    switch (transaction.getTransactionType()) {
    case PbFondsTransaction.TT_DIVIDEND:
      return DividendToXmlString(transaction, nameSpace);

    case PbFondsTransaction.TT_JAAROVERZICHT:
      return JaaroverzichtToXmlString(transaction, nameSpace);
      
    case PbFondsTransaction.TT_OVERSCHRIJVING:
      return OverschrijvingToXmlString(transaction, nameSpace);
      
    case PbFondsTransaction.TT_STOCK_DIVIDEND:
      return StockDividendToXmlString(transaction, nameSpace);
      
    default:
      throw new IllegalArgumentException("Onbekend transactie type voor de volgende transactie: " + transaction.toString());
    }
  }
  
  private String DividendToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder output = new StringBuilder();
    PbFondsDividend dividend = (PbFondsDividend) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.DIVIDEND_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, dividend.getBookingDate().format(DF)));

    // Dividend
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.DIVIDEND_PER_AANDEEL_TAG, CF.format(dividend.getDividendAmount())));

    // Koers
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.KOERS_TAG, CF.format(dividend.getKoers())));

    // het aantal aandelen waarover dividend uitgekeerd wordt.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.OVER_AANTAL_TAG, FPVF.format(dividend.getNumberOfShares())));

    // Commentaar
    if (dividend.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, dividend.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.DIVIDEND_TAG));

    return output.toString();
  }
  
  private String JaaroverzichtToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder output = new StringBuilder();
    PbFondsJaaroverzicht jaaroverzicht = (PbFondsJaaroverzicht) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.JAAR_OVERZICHT_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.DATUM_TAG, jaaroverzicht.getBookingDate().format(DF)));

    // Koers
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.KOERS_TAG, CF.format(jaaroverzicht.getKoers())));

    // Commentaar
    if (jaaroverzicht.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, jaaroverzicht.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.JAAR_OVERZICHT_TAG));

    return output.toString();
  }
  
  private String OverschrijvingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder output = new StringBuilder();
    PbFondsOverschrijving overschrijving = (PbFondsOverschrijving) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.OVERSCHRIJVING_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, overschrijving.getBookingDate().format(DF)));

    // transaction type
    if (overschrijving.isBijschrijving()) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, XmlTags.VAN_GIRO_TAG));
    } else {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, XmlTags.NAAR_GIRO_TAG));
    }
//    short  tt = overschrijving.getUitvoeringsType();
//    String tts;
//    if (tt == PbFondsTransaction.TT_AANKOOP  ||  tt == PbFondsTransaction.TT_AANKOOP_ZONDER_KOSTEN) {
//      tts = XmlTags.VAN_GIRO_TAG;
//    } else {
//      tts = XmlTags.NAAR_GIRO_TAG;
//    }
//    output.append(PgXml.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, tts));

    // transactie bijzonderheden
//    if (tt == PbFondsTransaction.TT_AANKOOP_ZONDER_KOSTEN  ||  overschrijving.getZonderKosten()) {
    if (overschrijving.getZonderKosten()) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_ATTRIBUUT_TAG, XmlTags.ZONDER_KOSTEN));
    }

    // Bedrag
    output.append(SgmlUtil.createElement(nameSpace, "Bedrag", CF.format(overschrijving.getTransactionAmount())));

    // Koers
    output.append(SgmlUtil.createElement(nameSpace, "Koers", CF.format(overschrijving.getKoers())));

    // Commentaar
    if (overschrijving.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, "Commentaar", overschrijving.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.OVERSCHRIJVING_TAG));

    return output.toString();
  }
  
  private String StockDividendToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder output = new StringBuilder();
    PbFondsStockDividend stockDividend = (PbFondsStockDividend) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.STOCK_DIVIDEND_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, stockDividend.getBookingDate().format(DF)));

    // Stock Dividend Percentage
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.PERCENTAGE_TAG, FPVF.format(stockDividend.getStockDividendPercentage())));

    // Koers
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.KOERS_TAG, CF.format(stockDividend.getKoers())));

    // het aantal aandelen waarover dividend uitgekeerd wordt
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.OVER_AANTAL_TAG, FPVF.format(stockDividend.getNumberOfShares())));

    // Commentaar
    if (stockDividend.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, stockDividend.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.STOCK_DIVIDEND_TAG));

    return output.toString();
  }
  
  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
//    System.out.println("PbFondsTransactionContentHandler: startElement = " + localpart + ", state = " + state);
    data = null;

    switch (state) {
    case START:
      if (localpart.compareTo(XmlTags.DIVIDEND_TAG) == 0) {
        //System.out.println("PbFondsTransactionContentHandler: => " + DIVIDEND_TAG);
        pushState(State.DIVIDEND);
        _fondsDividend = new PbFondsDividend(account);
      } else if (localpart.compareTo(XmlTags.JAAR_OVERZICHT_TAG) == 0) {
//      System.out.println("PbFondsTransactionContentHandler: => " + JAAR_OVERZICHT_TAG);
        pushState(State.JAAROVERZICHT);
        _fondsJaaroverzicht = new PbFondsJaaroverzicht(account);
      } else if (localpart.compareTo(XmlTags.OVERSCHRIJVING_TAG) == 0) {
//      System.out.println("PbFondsTransactionContentHandler: => " + OVERSCHRIJVING_TAG);
        pushState(State.OVERSCHRIJVING);
        _fondsOverschrijving = new PbFondsOverschrijving(account);
      } else if (localpart.compareTo(XmlTags.STOCK_DIVIDEND_TAG) == 0) {
//      System.out.println("PbFondsTransactionContentHandler: => " + STOCK_DIVIDEND_TAG);
        pushState(State.STOCK_DIVIDEND);
        _fondsStockDividend = new PbFondsStockDividend(account);
      }
      break;

    case DIVIDEND:
    case JAAROVERZICHT:
    case OVERSCHRIJVING:
    case STOCK_DIVIDEND:
      // nothing extra to be done on any opening tag.
      break;
    }
  }

  public void endElement(String uri, String localpart, String rawname) throws SAXException {
//    System.out.println("PbFondsTransactionContentHandler: endElement = " + localpart);

    switch (state) {
    case START:
      break;

    case DIVIDEND:
      if (localpart.compareTo(XmlTags.DIVIDEND_TAG) == 0) {
        if (!_fondsDividend.isValid()) {
          reportTransactionContentError("beleggersgiro", "dividend", _fondsDividend);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, _fondsDividend));
        _fondsDividend = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
        _fondsDividend.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.DIVIDEND_PER_AANDEEL_TAG) == 0) {
        try {
          _fondsDividend.setDividendAmount(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.KOERS_TAG) == 0) {
        try {
          _fondsDividend.setKoers(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.OVER_AANTAL_TAG) == 0) {
        try {
          _fondsDividend.setNumberOfShares(FPVF.parse(data, 10000));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig formaat voor aantal: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        _fondsDividend.setComment(data);
      }
      break;
      
    case JAAROVERZICHT:
      if (localpart.compareTo(XmlTags.JAAR_OVERZICHT_TAG) == 0) {
        if (!_fondsJaaroverzicht.isValid()) {
          reportTransactionContentError("beleggersgiro", "jaaroverzicht", _fondsJaaroverzicht);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, _fondsJaaroverzicht));
        _fondsJaaroverzicht = null;
        popState();
      } else if (localpart.compareTo(XmlTags.DATUM_TAG) == 0) {
        LocalDate valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in valutadatum");
        }
        _fondsJaaroverzicht.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.KOERS_TAG) == 0) {
        try {
          _fondsJaaroverzicht.setKoers(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        _fondsJaaroverzicht.setComment(data);
      }
      break;

    case OVERSCHRIJVING:
      if (localpart.compareTo(XmlTags.OVERSCHRIJVING_TAG) == 0) {
        if (!_fondsOverschrijving.isValid()) {
          reportTransactionContentError("beleggersgiro", "overschrijving", _fondsOverschrijving);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, _fondsOverschrijving));
        _fondsOverschrijving = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in valutadatum");
        }
        _fondsOverschrijving.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_TYPE_TAG) == 0) {
        if (data.equals("van giro")) {
          _fondsOverschrijving.setBijschrijving(true);
        } else if (data.equals("naar giro")) {
          _fondsOverschrijving.setBijschrijving(false);
        } else {
          throw new IllegalArgumentException("Onbekend transactie type " + data);
        }
//        _fondsOverschrijving.setUitvoeringsType(PbFondsTransaction.typeStringToTypeID(data));
      } else if (localpart.compareTo(XmlTags.BEDRAG_TAG) == 0) {
        try {
          _fondsOverschrijving.setTransactionAmount(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.KOERS_TAG) == 0) {
        try {
          _fondsOverschrijving.setKoers(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_ATTRIBUUT_TAG) == 0) {
        if (data.compareTo(XmlTags.ZONDER_KOSTEN) == 0) {
          _fondsOverschrijving.setZonderKosten(true);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        _fondsOverschrijving.setComment(data);
      }
      break;

    case STOCK_DIVIDEND:
      if (localpart.compareTo(XmlTags.STOCK_DIVIDEND_TAG) == 0) {
        if (!_fondsStockDividend.isValid()) {
          reportTransactionContentError("beleggersgiro", "stockdividend", _fondsStockDividend);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, _fondsStockDividend));
        _fondsStockDividend = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in valutadatum");
        }
        _fondsStockDividend.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.PERCENTAGE_TAG) == 0) {
        try {
          _fondsStockDividend.setStockDividendPercentage(FPVF.parse(data, 100));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig percentage formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.KOERS_TAG) == 0) {
        try {
          _fondsStockDividend.setKoers(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.OVER_AANTAL_TAG) == 0) {
        try {
          _fondsStockDividend.setNumberOfShares(FPVF.parse(data, 10000));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig formaat voor aantal: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        _fondsStockDividend.setComment(data);
      }
      break;
    }
  }
  
  private void reportTransactionContentError(String rekening, String transactionType, PgTransaction transaction) throws SAXParseException {
    this.printStatus("Ongeldige transactie inhoud gevonden voor Postbank" +
        rekening + "." + System.getProperty("line.separator") +
        "Soort transactie: " + transactionType + System.getProperty("line.separator") +
        "Transactie is : " + transaction.toString());
  }

  private void printStatus(String message) throws SAXParseException {
    System.out.println("Fout in regel " + locator.getLineNumber() + ", kolom " + locator.getColumnNumber());
    System.out.println("state = " + state);
    System.out.println("data = " + data);
    
    throw new SAXParseException(message, null, null, locator.getLineNumber(), locator.getColumnNumber());
  }

  enum State {
    START,
    DIVIDEND,
    JAAROVERZICHT,
    OVERSCHRIJVING,
    STOCK_DIVIDEND;
  }
}