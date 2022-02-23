package goedegep.app.finan.direktbankapp;

import goedegep.app.finan.gen.AccountContentHandler;
import goedegep.app.finan.gen.XmlTags;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.FinanTransaction;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.SumAccount;
import goedegep.finan.direktbank.direktsprek.DirektSpRekAfschrijving;
import goedegep.finan.direktbank.direktsprek.DirektSpRekBijschrijving;
import goedegep.finan.direktbank.direktsprek.DirektSpRekOpheffing;
import goedegep.finan.direktbank.direktsprek.DirektSpRekRente;
import goedegep.finan.direktbank.direktsprek.DirektSpRekTransaction;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.sgml.SgmlUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DirektSpRekTransactionContentHandler 
                extends AccountContentHandler<DirektSpRekTransactionContentHandler.State> {
  // Formatters/parsers
  private static final DateTimeFormatter     DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final PgCurrencyFormat      CF = new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  private SumAccount  sumAccount = null; // The SumAccount to which transactions are to be added.
  private Bank        bank = null;
  private PgAccount   account = null;
  
  private DirektSpRekAfschrijving  afschrijving;
  private DirektSpRekBijschrijving bijschrijving;
  private DirektSpRekOpheffing     opheffing;
  private DirektSpRekRente         rente;

  public void setAccount(SumAccount sumAccount, Bank bank, PgAccount account) {
    this.sumAccount = sumAccount;
    this.bank = bank;
    this.account = account;
    state = State.START;
  }
  
  public String transactionToXmlString(PgTransaction transaction, String nameSpace) {
    switch (transaction.getTransactionType()) {
    case DirektSpRekTransaction.TT_AFSCHRIJVING:
      return AfschrijvingToXmlString(transaction, nameSpace);

    case DirektSpRekTransaction.TT_BIJSCHRIJVING:
      return BijschrijvingToXmlString(transaction, nameSpace);

    case DirektSpRekTransaction.TT_OPHEFFING:
      return OpheffingToXmlString(transaction, nameSpace);
      
    case DirektSpRekTransaction.TT_RENTE:
      return RenteToXmlString(transaction, nameSpace);
      
    default:
      throw new IllegalArgumentException("Onbekend transactie type voor de volgende transactie: " + transaction.toString());
    }
  }
  
  private String AfschrijvingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    DirektSpRekAfschrijving afschrijving = (DirektSpRekAfschrijving) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.AFSCHRIJVING_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(afschrijving.getBookingDate())));

    // bedrag.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BEDRAG_TAG, CF.format(afschrijving.getTransactionAmount())));

    // transactiedatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, DF.format(afschrijving.getExecutionDate())));

    // 'rentepercentage' veld.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.RENTE_PERCENTAGE_TAG, FPVF.format(afschrijving.getRentePercentage())));

    // Commentaar
    if (afschrijving.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, afschrijving.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.AFSCHRIJVING_TAG));

    return output.toString();
  }
  
  private String BijschrijvingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    DirektSpRekBijschrijving bijschrijving = (DirektSpRekBijschrijving) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.BIJSCHRIJVING_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(bijschrijving.getBookingDate())));

    // bedrag.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BEDRAG_TAG, CF.format(bijschrijving.getTransactionAmount())));

    // transactiedatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, DF.format(bijschrijving.getExecutionDate())));

    // 'rentepercentage' veld.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.RENTE_PERCENTAGE_TAG, FPVF.format(bijschrijving.getRentePercentage())));

    // Commentaar
    if (bijschrijving.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, bijschrijving.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.BIJSCHRIJVING_TAG));

    return output.toString();
  }
  
  private String OpheffingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder        output = new StringBuilder();
    DirektSpRekOpheffing opheffing = (DirektSpRekOpheffing) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.OPHEFFING_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(opheffing.getBookingDate())));

    // bedrag.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BEDRAG_TAG, CF.format(opheffing.getTransactionAmount())));

    // transactiedatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, DF.format(opheffing.getExecutionDate())));

    // 'rentepercentage' veld.
    if (opheffing.getRentePercentage() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.RENTE_PERCENTAGE_TAG, FPVF.format(opheffing.getRentePercentage())));
    }

    // Commentaar
    if (opheffing.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, opheffing.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.OPHEFFING_TAG));

    return output.toString();
  }
    
  private String RenteToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    DirektSpRekRente   rente = (DirektSpRekRente) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.RENTE_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(rente.getBookingDate())));

    // bedrag.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BEDRAG_TAG, CF.format(rente.getTransactionAmount())));

    // Uitvoeringsdatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, DF.format(rente.getExecutionDate())));

    // 'rentepercentage' veld.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.RENTE_PERCENTAGE_TAG, FPVF.format(rente.getRentePercentage())));

    // van datum
    if (rente.getVanDatum() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.VAN_DATUM_TAG,
                                               DF.format(rente.getVanDatum())));
    }

    // tot datum
    if (rente.getTotDatum() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.TOT_DATUM_TAG,
                                               DF.format(rente.getTotDatum())));
    }
    
    // Commentaar
    if (rente.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, rente.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.RENTE_TAG));

    return output.toString();
  }
  
  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
//    System.out.println("DirektSpRekTransactionContentHandler: startElement = " + localpart + ", state = " + state);
    data = null;

    switch (state) {
    case START:
      if (localpart.compareTo(XmlTags.AFSCHRIJVING_TAG) == 0) {
        //System.out.println("DirektSpRekTransactionContentHandler: => " + XmlTags.AFSCHRIJVING_TAG);
        pushState(State.AFSCHRIJVING);
        afschrijving = new DirektSpRekAfschrijving(account);
      } else if (localpart.compareTo(XmlTags.BIJSCHRIJVING_TAG) == 0) {
//        System.out.println("DirektSpRekTransactionContentHandler: => " + XmlTags.BIJSCHRIJVING_TAG);
        pushState(State.BIJSCHRIJVING);
        bijschrijving = new DirektSpRekBijschrijving(account);
      } else if (localpart.compareTo(XmlTags.OPHEFFING_TAG) == 0) {
//      System.out.println("DirektSpRekTransactionContentHandler: => " + XmlTags.OPHEFFING_TAG);
        pushState(State.OPHEFFING);
        opheffing = new DirektSpRekOpheffing(account);
      } else if (localpart.compareTo(XmlTags.RENTE_TAG) == 0) {
//      System.out.println("DirektSpRekTransactionContentHandler: => " + XmlTags.RENTE_TAG);
        pushState(State.RENTE);
        rente = new DirektSpRekRente(account);
      }
      break;
      
    case AFSCHRIJVING:
    case BIJSCHRIJVING:
    case OPHEFFING:
    case RENTE:
      // nothing extra to be done on any opening tag.
      break;
    }
  }

  public void endElement(String uri, String localpart, String rawname) throws SAXException {
//    System.out.println("DirektSpRekTransactionContentHandler: endElement = " + localpart);

    switch (state) {
    case START:
      break;
      
    case AFSCHRIJVING:
      if (localpart.compareTo(XmlTags.AFSCHRIJVING_TAG) == 0) {
        if (!afschrijving.isValid()) {
          throw new SAXParseException("Ongeldige transactie bij einde van " + localpart +
              " voor " + account.getName(), locator);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, afschrijving));
        afschrijving = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        afschrijving.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.BEDRAG_TAG) == 0) {
        try {
          afschrijving.setTransactionAmount(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        LocalDate transactieDatum = null;
        try {
          transactieDatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        afschrijving.setExecutionDate(transactieDatum);
      } else if (localpart.compareTo(XmlTags.RENTE_PERCENTAGE_TAG) == 0) {
        try {
          afschrijving.setRentePercentage(FPVF.parse(data, 100));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig percentage formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        afschrijving.setComment(data);
      }
      break;
      
    case BIJSCHRIJVING:
      if (localpart.compareTo(XmlTags.BIJSCHRIJVING_TAG) == 0) {
        if (!bijschrijving.isValid()) {
          throw new SAXParseException("Ongeldige transactie bij einde van " + localpart +
              " voor " + account.getName(), locator);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, bijschrijving));
        bijschrijving = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        bijschrijving.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.BEDRAG_TAG) == 0) {
        try {
          bijschrijving.setTransactionAmount(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        LocalDate  transactieDatum = null;
        try {
          transactieDatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        bijschrijving.setExecutionDate(transactieDatum);
      } else if (localpart.compareTo(XmlTags.RENTE_PERCENTAGE_TAG) == 0) {
        try {
          bijschrijving.setRentePercentage(FPVF.parse(data, 100));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig percentage formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        bijschrijving.setComment(data);
      }
      break;
      
    case OPHEFFING:
      if (localpart.compareTo(XmlTags.OPHEFFING_TAG) == 0) {
        if (!opheffing.isValid()) {
          throw new SAXParseException("Ongeldige transactie bij einde van " + localpart +
              " voor " + account.getName(), locator);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, opheffing));
        opheffing = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        opheffing.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.BEDRAG_TAG) == 0) {
        try {
          opheffing.setTransactionAmount(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        LocalDate  transactieDatum = null;
        try {
          transactieDatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        opheffing.setExecutionDate(transactieDatum);
      } else if (localpart.compareTo(XmlTags.RENTE_PERCENTAGE_TAG) == 0) {
        try {
          opheffing.setRentePercentage(FPVF.parse(data, 100));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig percentage formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        opheffing.setComment(data);
      }
      break;

    case RENTE:
      if (localpart.compareTo(XmlTags.RENTE_TAG) == 0) {
        if (!rente.isValid()) {
          throw new SAXParseException("Ongeldige Rente transactie", locator);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, rente));
        rente = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        try {
          rente.setBookingDate(LocalDate.parse(data, DF));
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.BEDRAG_TAG) == 0) {
        try {
          rente.setTransactionAmount(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        try {
          rente.setExecutionDate(LocalDate.parse(data, DF));
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.RENTE_PERCENTAGE_TAG) == 0) {
        try {
          rente.setRentePercentage(FPVF.parse(data, 100));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig percentage formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.VAN_DATUM_TAG) == 0) {
        try {
          rente.setVanDatum(LocalDate.parse(data, DF));
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in van-datum: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.TOT_DATUM_TAG) == 0) {
        try {
          rente.setTotDatum(LocalDate.parse(data, DF));
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in tot-datum: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        rente.setComment(data);
      }
      break;
    }
  }

  enum State {
    START,
    AFSCHRIJVING,
    BIJSCHRIJVING,
    OPHEFFING,
    RENTE;
  }
}
