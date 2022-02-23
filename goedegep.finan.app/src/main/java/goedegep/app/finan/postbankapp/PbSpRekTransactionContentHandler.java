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
import goedegep.finan.postbank.pbsprek.PbSpRekGeschenkInleg;
import goedegep.finan.postbank.pbsprek.PbSpRekOpheffing;
import goedegep.finan.postbank.pbsprek.PbSpRekOverschrijving;
import goedegep.finan.postbank.pbsprek.PbSpRekRente;
import goedegep.finan.postbank.pbsprek.PbSpRekRenteAanpassing;
import goedegep.finan.postbank.pbsprek.PbSpRekTransaction;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.sgml.SgmlUtil;

public class PbSpRekTransactionContentHandler 
                extends AccountContentHandler<PbSpRekTransactionContentHandler.State> {
  // Formatters/parsers
  private static final DateTimeFormatter  DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  private static final String   _renteDatumTag         = "RenteDatum";  // TODO Moet uitvoerings datum worden.
  
  private SumAccount  sumAccount = null; // The SumAccount to which transactions are to be added.
  private Bank        bank = null;
  private PgAccount   account = null;
  
  private PbSpRekOverschrijving  overschrijving = null;
  private PbSpRekRente           rente = null;
  private PbSpRekRenteAanpassing renteAanpassing = null;
  private PbSpRekGeschenkInleg   geschenkInleg = null;
  private PbSpRekOpheffing       opheffing = null;

  public void setAccount(SumAccount sumAccount, Bank bank, PgAccount account) {
    this.sumAccount = sumAccount;
    this.bank = bank;
    this.account = account;
    state = State.START;
  }
  
  public String transactionToXmlString(PgTransaction transaction, String nameSpace) {
    switch (transaction.getTransactionType()) {
    case PbSpRekTransaction.TT_GESCHENK_INLEG:
      return GeschenkInlegToXmlString(transaction, nameSpace);

    case PbSpRekTransaction.TT_JAAROVERZICHT:
      return JaarOverzichtToXmlString(transaction, nameSpace);
      
    case PbSpRekTransaction.TT_OPHEFFING:
      return OpheffingToXmlString(transaction, nameSpace);
      
    case PbSpRekTransaction.TT_OVERSCHRIJVING:
      return OverschrijvingToXmlString(transaction, nameSpace);
      
    case PbSpRekTransaction.TT_RENTE:
      return RenteToXmlString(transaction, nameSpace);
      
    case PbSpRekTransaction.TT_RENTE_AANPASSING:
      return RenteAanpassingToXmlString(transaction, nameSpace);
      
    default:
      throw new IllegalArgumentException("Onbekend transactie type voor de volgende transactie: " + transaction.toString());
    }
  }
  
  private String GeschenkInlegToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    PbSpRekGeschenkInleg geschenkInleg = (PbSpRekGeschenkInleg) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.GESCHENK_INLEG_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(geschenkInleg.getBookingDate())));

    // bedrag
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BEDRAG_TAG, CF.format(geschenkInleg.getTransactionAmount())));

    // transactie datum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, DF.format(geschenkInleg.getExecutionDate())));

    // rente percentage
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.RENTE_PERCENTAGE_TAG, FPVF.format(geschenkInleg.getRentePercentage())));

    // Commentaar
    if (geschenkInleg.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, geschenkInleg.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.GESCHENK_INLEG_TAG));

    return output.toString();
  }
  
  private String JaarOverzichtToXmlString(PgTransaction transaction, String nameSpace) {
    throw new IllegalArgumentException("PbSpRekJaarOverzicht not yet implemented");
//    StringBuilder      output = new StringBuilder();
//    PbSpRekJaarOverzicht jaarOverzicht = (PbSpRekJaarOverzicht) transaction;
//
//    return output.toString();
  }
  
  private String OpheffingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    PbSpRekOpheffing opheffing = (PbSpRekOpheffing) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.OPHEFFING_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(opheffing.getBookingDate())));

    // bedrag.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BEDRAG_TAG, CF.format(opheffing.getTransactionAmount())));

    // rentedatum
    // TODO RenteDatum moet TransactieDatum worden.
    output.append(SgmlUtil.createElement(nameSpace, "RenteDatum", DF.format(opheffing.getExecutionDate())));

    // 'rentepercentage' veld.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.RENTE_PERCENTAGE_TAG, FPVF.format(opheffing.getRentePercentage())));
    
    // Commentaar
    if (opheffing.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, opheffing.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.OPHEFFING_TAG));

    return output.toString();
  }
  
  private String OverschrijvingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    PbSpRekOverschrijving overschrijving = (PbSpRekOverschrijving) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.OVERSCHRIJVING_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(overschrijving.getBookingDate())));

    // transaction type (bij, ..)
    if (overschrijving.isBijschrijving()) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, PbSpRekOverschrijving.VAN_GIRO_STRING));
    } else {
      if (overschrijving.isZonderKosten()) {
        output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, PbSpRekOverschrijving.NAAR_GIRO_STRING + " " + XmlTags.ZONDER_KOSTEN));
      } else {
        output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, PbSpRekOverschrijving.NAAR_GIRO_STRING));
      }
    }
    
//    output.append(PgXml.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, PbSpRekTransaction.typeIDToTypeString(overschrijving.getTransactionType())));

    // bedrag.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BEDRAG_TAG, CF.format(overschrijving.getTransactionAmount())));

    // rentedatum
    // TODO RenteDatum moet TransactieDatum worden.
    output.append(SgmlUtil.createElement(nameSpace, "RenteDatum", DF.format(overschrijving.getExecutionDate())));

    // 'rentepercentage' veld.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.RENTE_PERCENTAGE_TAG, FPVF.format(overschrijving.getRentePercentage())));

    // Commentaar
    if (overschrijving.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, overschrijving.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.OVERSCHRIJVING_TAG));

    return output.toString();
  }
  
  private String RenteToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    PbSpRekRente rente = (PbSpRekRente) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.RENTE_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(rente.getBookingDate())));

    // bedrag.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BEDRAG_TAG, CF.format(rente.getTransactionAmount())));

    // rentedatum
    // TODO RenteDatum moet TransactieDatum worden.
    output.append(SgmlUtil.createElement(nameSpace, "RenteDatum", DF.format(rente.getExecutionDate())));

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
  
  private String RenteAanpassingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    PbSpRekRenteAanpassing renteAanpassing = (PbSpRekRenteAanpassing) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.RENTE_AANPASSING_TAG));

    // uitvoeringsdatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.DATUM_TAG, DF.format(renteAanpassing.getExecutionDate())));

    // 'rentepercentage' veld.
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.RENTE_PERCENTAGE_TAG, FPVF.format(renteAanpassing.getRentePercentage())));

    // Commentaar
    if (renteAanpassing.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, renteAanpassing.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.RENTE_AANPASSING_TAG));

    return output.toString();
  }
    
  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
//    System.out.println("PbSpRekTransactionContentHandler: startElement = " + localpart + ", state = " + state);
    data = null;

    switch (state) {
    case START:
      if (localpart.compareTo(XmlTags.GESCHENK_INLEG_TAG) == 0) {
        //System.out.println("PbSpRekTransactionContentHandler: => " + GESCHENK_INLEG_TAG);
        pushState(State.GESCHENK_INLEG);
        geschenkInleg = new PbSpRekGeschenkInleg(account);
      } else if (localpart.compareTo(XmlTags.OPHEFFING_TAG) == 0) {
//      System.out.println("PbSpRekTransactionContentHandler: => " + OPHEFFING_TAG);
        pushState(State.OPHEFFING);
        opheffing = new PbSpRekOpheffing(account);
      } else if (localpart.compareTo(XmlTags.OVERSCHRIJVING_TAG) == 0) {
//      System.out.println("PbSpRekTransactionContentHandler: => " + OVERSCHRIJVING_TAG);
        pushState(State.OVERSCHRIJVING);
        overschrijving = new PbSpRekOverschrijving(account);
      } else if (localpart.compareTo(XmlTags.RENTE_AANPASSING_TAG) == 0) {
//      System.out.println("PbSpRekTransactionContentHandler: => " + RENTE_AANPASSING_TAG);
        pushState(State.RENTE_AANPASSING);
        renteAanpassing = new PbSpRekRenteAanpassing(account);
      } else if (localpart.compareTo(XmlTags.RENTE_TAG) == 0) {
//      System.out.println("PbSpRekTransactionContentHandler: => " + RENTE_TAG);
        pushState(State.RENTE);
        rente = new PbSpRekRente(account);
      }
      break;

    case GESCHENK_INLEG:
    case OPHEFFING:
    case OVERSCHRIJVING:
    case RENTE_AANPASSING:
    case RENTE:
      // nothing extra to be done on any opening tag.
      break;
    }
  }

  public void endElement(String uri, String localpart, String rawname) throws SAXException {
//    System.out.println("PbSpRekTransactionContentHandler: endElement = " + localpart);

    switch (state) {
    case START:
      break;

    case GESCHENK_INLEG:
      if (localpart.compareTo(XmlTags.GESCHENK_INLEG_TAG) == 0) {
        if (!geschenkInleg.isValid()) {
          reportTransactionContentError("spaarrekening", "geschenkinleg", geschenkInleg);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, geschenkInleg));
        geschenkInleg = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in valutadatum");
        }
        geschenkInleg.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.BEDRAG_TAG) == 0) {
        try {
          geschenkInleg.setTransactionAmount(CF.parse(data, 100));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        LocalDate rentedatum = null;                   // rente datum
        try {
          rentedatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in rentedatum");
        }
        geschenkInleg.setExecutionDate(rentedatum);
      } else if (localpart.compareTo(XmlTags.RENTE_PERCENTAGE_TAG) == 0) {
        try {
          geschenkInleg.setRentePercentage(FPVF.parse(data, 100));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig percentage formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        geschenkInleg.setComment(data);
      }
      break;

    case OPHEFFING:
      if (localpart.compareTo(XmlTags.OPHEFFING_TAG) == 0) {
        if (!opheffing.isValid()) {
          reportTransactionContentError("spaarrekening", "opheffing", opheffing);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, opheffing));
        opheffing = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in valutadatum");
        }
        opheffing.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.BEDRAG_TAG) == 0) {
        try {
          opheffing.setTransactionAmount(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(_renteDatumTag) == 0) {
        LocalDate rentedatum = null;                   // rente datum
        try {
          rentedatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in rentedatum");
        }
        opheffing.setExecutionDate(rentedatum);
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

    case OVERSCHRIJVING:
      if (localpart.compareTo(XmlTags.OVERSCHRIJVING_TAG) == 0) {
        if (!overschrijving.isValid()) {
          reportTransactionContentError("spaarrekening", "overschrijving", overschrijving);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, overschrijving));
        overschrijving = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in valutadatum");
        }
        overschrijving.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_TYPE_TAG) == 0) {
        // TODO Anders: overschrijving splitsen in af en bijschrijving
        // en aparte tag voor 'zonder kosten'.
        if (data.equals("bij")) {
          overschrijving.setBijschrijving(true);  
        } else if (data.equals("af")) {
          overschrijving.setBijschrijving(false);
        } else if (data.equals("af zonder kosten")) {
          overschrijving.setBijschrijving(false);
          overschrijving.setZonderKosten(true);
       }
      } else if (localpart.compareTo(XmlTags.BEDRAG_TAG) == 0) {
        try {
          overschrijving.setTransactionAmount(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(_renteDatumTag) == 0) {
        LocalDate rentedatum = null;                   // rente datum
        try {
          rentedatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in rentedatum");
        }
        overschrijving.setExecutionDate(rentedatum);
      } else if (localpart.compareTo(XmlTags.RENTE_PERCENTAGE_TAG) == 0) {
        try {
          overschrijving.setRentePercentage(FPVF.parse(data, 100));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig percentage formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        overschrijving.setComment(data);
      }
      break;

    case RENTE_AANPASSING:
      if (localpart.compareTo(XmlTags.RENTE_AANPASSING_TAG) == 0) {
        if (!renteAanpassing.isValid()) {
          reportTransactionContentError("spaarrekening", "rente aanpassing", renteAanpassing);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, renteAanpassing));
        renteAanpassing = null;
        popState();
      } else if (localpart.compareTo(XmlTags.DATUM_TAG) == 0) {
        LocalDate datum = null;                   // boekings datum (setBookingDate)
        try {
          datum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in valutadatum");
        }
        renteAanpassing.setExecutionDate(datum);
      } else if (localpart.compareTo(XmlTags.RENTE_PERCENTAGE_TAG) == 0) {
        try {
          renteAanpassing.setRentePercentage(FPVF.parse(data, 100));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig percentage formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        overschrijving.setComment(data);
      }
      break;

    case RENTE:
      if (localpart.compareTo(XmlTags.RENTE_TAG) == 0) {
        if (!rente.isValid()) {
          reportTransactionContentError("spaarrekening", "rente", rente);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, rente));
        rente = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in valutadatum");
        }
        rente.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.BEDRAG_TAG) == 0) {
        try {
          rente.setTransactionAmount(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(_renteDatumTag) == 0) {
        LocalDate rentedatum = null;                   // rente datum
        try {
          rentedatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in rentedatum");
        }
        rente.setExecutionDate(rentedatum);
      } else if (localpart.compareTo(XmlTags.RENTE_PERCENTAGE_TAG) == 0) {
        try {
          rente.setRentePercentage(FPVF.parse(data, 100));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig percentage formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.VAN_DATUM_TAG) == 0) {
        LocalDate valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in vandatum");
        }
        rente.setVanDatum(valutadatum);
      } else if (localpart.compareTo(XmlTags.TOT_DATUM_TAG) == 0) {
        LocalDate valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          System.err.println("FOUT: formaat fout in vandatum");
        }
        rente.setTotDatum(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        rente.setComment(data);
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
    GESCHENK_INLEG,
    OPHEFFING,
    OVERSCHRIJVING,
    RENTE_AANPASSING,
    RENTE;
  }
}