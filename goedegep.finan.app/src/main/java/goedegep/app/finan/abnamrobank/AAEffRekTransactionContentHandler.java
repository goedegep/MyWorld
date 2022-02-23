package goedegep.app.finan.abnamrobank;

import goedegep.app.finan.gen.AccountContentHandler;
import goedegep.app.finan.gen.XmlTags;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekAandelenTransactie;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekAanpassingDekkingsVerplichting;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekAfschrijving;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekBelastingOverzicht;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekBewaarloon;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekBijschrijving;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekDividend;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekOptieExpiratie;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekOptieTransactie;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRekRenteverrekening;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.FinanTransaction;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.SumAccount;
import goedegep.finan.effrek.EffRekTransactie;
import goedegep.finan.stocks.OptieTransactieType;
import goedegep.finan.stocks.OptionType;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.sgml.SgmlUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class AAEffRekTransactionContentHandler 
                extends AccountContentHandler<AAEffRekTransactionContentHandler.State> {
  // Formatters/parsers
  private static final DateTimeFormatter  DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat(0, false, false, true);
  
  private SumAccount  sumAccount = null; // The SumAccount to which transactions are to be added.
  private Bank        bank = null;
  private PgAccount   account = null;
  
  private AAEffRekAandelenTransactie              aandelenTransactie = null;
  private AAEffRekAanpassingDekkingsVerplichting  aanpassingDekkingsVerplichting = null;
  private AAEffRekAfschrijving                    afschrijving = null;
  private AAEffRekBelastingOverzicht              belastingOverzicht = null;
  private AAEffRekBewaarloon                      bewaarloon = null;
  private AAEffRekBijschrijving                   bijschrijving = null;
//  private AAEffRekBonusAandelen                   bonusAandelen = null;
//  private AAEffRekClaimRightsReceived             claimRechtenOntvangen = null;
//  private AAEffRekClaimRightsTransaction          claimRightsTransaction = null;
//  private AAEffRekCorrectie                       correctie = null;
  private AAEffRekDividend                        dividend = null;
//  private AAEffRekFractieVerrekeningNieuweWaarden fractieVerrNweWaarden = null;
//  private AAEffRekKwartaalOverzicht               kwartaalOverzicht = null;
  private AAEffRekOptieExpiratie                  optieExpiratie = null;
  private AAEffRekOptieTransactie                 optieTransactie = null;
//  private AAEffRekOvername                        overname = null;
//  private AAEffRekRedenominatie                   redenominatie = null;
  private AAEffRekRenteverrekening                renteVerrekening = null;
//  private AAEffRekStockDividend                   stockDividend = null;
//  private AAEffRekStockDivTransactie              stockDividendTransactie = null;
//  private AAEffRekTerugBetaling                   terugBetaling = null;
//  private AAEffRekTerugBetalingRechten            terugBetalingRechten = null;
//  private AAEffRekVerwClaimRights                 verwClaimRights = null;
//  private AAEffRekVerwStockdiv                    verwStockDiv = null;
  private String                                  dividendReferentie = null;
  
  private Share                                   share = null;

  public void setAccount(SumAccount sumAccount, Bank bank, PgAccount account) {
    this.sumAccount = sumAccount;
    this.bank = bank;
    this.account = account;
    state = State.START;
  }
  
  public String transactionToXmlString(PgTransaction transaction, String nameSpace) {
    switch (transaction.getTransactionType()) {
    case EffRekTransactie.TT_AANDELEN_TRANSACTIE:
      return aandelenTransactieToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_AANPASSING_DEKKINGSVERPLICHTING:
      return aanpassingDekkingsVerplichtingToXmlString(transaction, nameSpace);

    case EffRekTransactie.TT_AFSCHRIJVING:
      return afschrijvingToXmlString(transaction, nameSpace);

    case EffRekTransactie.TT_BELASTING_OVERZICHT:
      return belastingoverzichtToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_BEWAARLOON:
      return bewaarloonToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_BIJSCHRIJVING:
      return bijschrijvingToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_BONUS_AANDELEN:
//      return BonusAandelenToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_CLAIM_RIGHTS_RECEIVED:
//      return ClaimRightsReceivedToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_CLAIM_RIGHTS_TRANSACTION:
//      return ClaimRightsTransactionToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_CORRECTIE:
//      return CorrectieToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_DIVIDEND:
      return dividendToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_FRACTIEVERR_NWE_WAARDEN:
//      return FractieVerrekeningNieuweWaardenToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_KWARTAAL_OVERZICHT:
//      return KwartaalOverzichtToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_OPTIE_EXPIRATIE:
      return optieExpiratieToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_OPTIE_TRANSACTIE:
      return optieTransactieToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_OVERNAME:
//      return OvernameToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_REDENOMINATIE:
//      return RedenominatieToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_RENTEVERREKENING:
      return renteverrekeningToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_STOCK_DIVIDEND:
//      return StockDividendToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_STOCK_DIV_TRANSACTIE:
//      return StockDivTransactieToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_TERUGBETALING:
//      return TerugBetalingToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_TERUGBETALING_RECHTEN:
//      return TerugBetalingRechtenToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_VERW_CLAIM_RIGHTS:
//      return VerwClaimRightsToXmlString(transaction, nameSpace);
      
//    case EffRekTransactie.TT_VERW_STOCK_DIVIDEND:
//      return VerwStockdivToXmlString(transaction, nameSpace);
      
    default:
      throw new IllegalArgumentException("Onbekend transactie type voor de volgende transactie: " + transaction.toString());
    }
  }
  
  private String aandelenTransactieToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    AAEffRekAandelenTransactie aandelenTransactie = (AAEffRekAandelenTransactie) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.AANDELEN_TRANSACTIE_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, aandelenTransactie.getBookingDate().format(DF)));

    // transaction type
    String tt;
    if (aandelenTransactie.isAankoop()) {
      tt = XmlTags.AANKOOP_STRING;
    } else {
      tt = XmlTags.VERKOOP_STRING;
    }
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, tt));

//    // via beursorderlijn
//    if (aandelenTransactie.isViaBeursorderlijn()) {
//      output.append(PgXml.createElementOpenClose(nameSpace, XmlTags.BEURSORDERLIJN_TAG));
//    }

    // Deeluitvoeringen
    String du = null;
    switch (aandelenTransactie.getUitvoeringsType()) {
    case AAEffRekAandelenTransactie.UT_VOLLEDIG:
      // geen extra toevoeging
      break;

    case AAEffRekAandelenTransactie.UT_DEELUITVOERING:
      du = AAEffRekAandelenTransactie.DEELUITVOERING_STRING;
      break;

    case AAEffRekAandelenTransactie.UT_DEELUITVOERING_EERSTE:
      du = AAEffRekAandelenTransactie.DEELUITVOERING_EERSTE_STRING;
      break;

    case AAEffRekAandelenTransactie.UT_DEELUITVOERING_LAATSTE:
      du = AAEffRekAandelenTransactie.DEELUITVOERING_LAATSTE_STRING;
      break;

    default:
      // kan niet voorkomen
      break;
    }
    if (du != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.DEEL_UITVOERING_TAG, du));
    }

    // Naam van het aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, aandelenTransactie.getEffect().getName()));

    // Aantal aandelen
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(aandelenTransactie.getAantalEffecten())));

    // Koers
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.KOERS_TAG, CF.format(aandelenTransactie.getKoers())));

    // Order kosten
    if (aandelenTransactie.getOrderKosten() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.ORDER_KOSTEN_TAG, CF.format(aandelenTransactie.getOrderKosten())));
    }
    
    // Transactie datum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, aandelenTransactie.getExecutionDate().format(DF)));

    // Commentaar
    if (aandelenTransactie.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, aandelenTransactie.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.AANDELEN_TRANSACTIE_TAG));

    return output.toString();
  }
  
  private String aanpassingDekkingsVerplichtingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    AAEffRekAanpassingDekkingsVerplichting aanpassingDekkingsVerplichting = (AAEffRekAanpassingDekkingsVerplichting) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.AANPASSING_DEKKINGS_VERPLICHTING_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, aanpassingDekkingsVerplichting.getBookingDate().format(DF)));

    // transaction amount
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BEDRAG_TAG, CF.format(aanpassingDekkingsVerplichting.getTransactionAmount())));

    // Commentaar
    if (aanpassingDekkingsVerplichting.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, aanpassingDekkingsVerplichting.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.AANPASSING_DEKKINGS_VERPLICHTING_TAG));

    return output.toString();
  }
  
  private String afschrijvingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    AAEffRekAfschrijving afschrijving = (AAEffRekAfschrijving) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.AFSCHRIJVING_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, afschrijving.getBookingDate().format(DF)));

    // transaction amount
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BEDRAG_TAG, CF.format(afschrijving.getTransactionAmount())));

    // Commentaar
    if (afschrijving.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, afschrijving.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.AFSCHRIJVING_TAG));

    return output.toString();
  }
  
  private String belastingoverzichtToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    AAEffRekBelastingOverzicht belastingOverzicht = (AAEffRekBelastingOverzicht) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.BELASTING_OVERZICHT_TAG));
    
    // Jaar
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.JAAR_TAG, Integer.toString(belastingOverzicht.getJaar())));

    // Commentaar
    if (belastingOverzicht.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, belastingOverzicht.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.BELASTING_OVERZICHT_TAG));

    return output.toString();
  }
  
  private String bewaarloonToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    AAEffRekBewaarloon bewaarloon = (AAEffRekBewaarloon) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.BEWAARLOON_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, bewaarloon.getBookingDate().format(DF)));

    // bedrag
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BEDRAG_TAG, CF.format(bewaarloon.getTransactionAmount())));

    // Commentaar
    if (bewaarloon.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, bewaarloon.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.BEWAARLOON_TAG));

    return output.toString();
  }
  
  private String bijschrijvingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    AAEffRekBijschrijving bijschrijving = (AAEffRekBijschrijving) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.BIJSCHRIJVING_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, bijschrijving.getBookingDate().format(DF)));

    // transaction amount
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BEDRAG_TAG, CF.format(bijschrijving.getTransactionAmount())));

    // Commentaar
    if (bijschrijving.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, bijschrijving.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.BIJSCHRIJVING_TAG));

    return output.toString();
  }
  
//  private String bonusAandelenToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekBonusAandelen bonusAandelen = (AAEffRekBonusAandelen) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.BONUS_AANDELEN_TAG));
//
//    // valutadatum
//    output.append(PgXml.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(bonusAandelen.getBookingDate())));
//
//    // Naam van het aandeel
//    output.append(PgXml.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, bonusAandelen.getEffect().getName()));
//
//    // Aantal aandelen
//    output.append(PgXml.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(bonusAandelen.getAantalEffecten())));
//
//    // Transactie datum
//    output.append(PgXml.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, DF.format(bonusAandelen.getExecutionDate())));
//
//    // Commentaar
//    if (bonusAandelen.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, bonusAandelen.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.BONUS_AANDELEN_TAG));
//
//    return output.toString();
//  }
  
//  private String claimRightsReceivedToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekClaimRightsReceived claimRightsReceived = (AAEffRekClaimRightsReceived) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.CLAIM_RECHTEN_ONTVANGEN_TAG));
//
//    // Naam van het aandeel
//    output.append(PgXml.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, claimRightsReceived.getClaimEmission().getShare().getName()));
//
//    // Claim Id
//    output.append(PgXml.createElement(nameSpace, XmlTags.CLAIM_ID_TAG, String.valueOf(claimRightsReceived.getClaimEmission().getId())));
//
//    // Aantal rechten
//    output.append(PgXml.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(claimRightsReceived.getNumberOfRights())));
//
//    // Transactie datum
//    if (claimRightsReceived.getExecutionDate() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, DF.format(claimRightsReceived.getExecutionDate())));
//    }
//
//    // Commentaar
//    if (claimRightsReceived.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, claimRightsReceived.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.CLAIM_RECHTEN_ONTVANGEN_TAG));
//
//    return output.toString();
//  }
  
//  private String claimRightsTransactionToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekClaimRightsTransaction claimRightsTransaction = (AAEffRekClaimRightsTransaction) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.CLAIM_RECHTEN_TRANSACTIE_TAG));
//
//    // valutadatum
//    output.append(PgXml.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(claimRightsTransaction.getBookingDate())));
//
//    // transaction type
//    String tt;
//    if (claimRightsTransaction.isBuy()) {
//      tt = AAEffRekClaimRightsTransaction.AANKOOP_STRING;
//    } else {
//      tt = AAEffRekClaimRightsTransaction.VERKOOP_STRING;
//    }
//    output.append(PgXml.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, tt));
//
//    // Naam van het aandeel
//    output.append(PgXml.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, claimRightsTransaction.getClaimEmission().getShare().getName()));
//
//    // Claim Id
//    output.append(PgXml.createElement(nameSpace, XmlTags.CLAIM_ID_TAG, String.valueOf(claimRightsTransaction.getClaimEmission().getId())));
//
//    // Aantal rechten
//    output.append(PgXml.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(claimRightsTransaction.getNumberOfRights())));
//
//    // Transactie datum
//    output.append(PgXml.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, DF.format(claimRightsTransaction.getExecutionDate())));
//
//    // Commentaar
//    if (claimRightsTransaction.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, claimRightsTransaction.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.CLAIM_RECHTEN_TRANSACTIE_TAG));
//
//    return output.toString();
//  }
  
//  private String correctieToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekCorrectie correctie = (AAEffRekCorrectie) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.CORRECTIE_TAG));
//
//    // saldo
//    if (correctie.getSaldo() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.SALDO_TAG, CF.format(correctie.getSaldo())));
//    }
//
//    // Commentaar
//    if (correctie.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, correctie.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.CORRECTIE_TAG));
//
//    return output.toString();
//  }
  
  private String dividendToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    AAEffRekDividend dividend = (AAEffRekDividend) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.DIVIDEND_TAG));

    // valutadatum
    if (dividend.getBookingDate() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, dividend.getBookingDate().format(DF)));
    }

    // Naam van het aandeel en referentie van het dividend
    if (dividend.getShareDividend() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG,
          dividend.getShareDividend().getShare().getName()));
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.DIVIDEND_REFERENTIE_TAG,
          dividend.getShareDividend().getReferenceString()));
    }

    // Aantal aandelen
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(dividend.getAantalEffecten())));

    // Dividend datum
    if (dividend.getExecutionDate() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.DIVIDEND_DATUM_TAG, dividend.getExecutionDate().format(DF)));
    }

    // Zonder kosten
    if (dividend.isZonderKosten()) {
      output.append(SgmlUtil.createElementOpenClose(nameSpace, XmlTags.ZONDER_KOSTEN_TAG));
    }

    // Commentaar
    if (dividend.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, dividend.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.DIVIDEND_TAG));

    return output.toString();
  }
  
//  private String fractieVerrekeningNieuweWaardenToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekFractieVerrekeningNieuweWaarden fractieVerrekeningNieuweWaarden = (AAEffRekFractieVerrekeningNieuweWaarden) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.FRACTIE_VERREKENING_NIEUWE_WAARDEN_TAG));
//
//    // valutadatum
//    output.append(PgXml.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(fractieVerrekeningNieuweWaarden.getBookingDate())));
//
//    // transaction type
//    String tt;
//    if (fractieVerrekeningNieuweWaarden.isAankoop()) {
//      tt = AAEffRekFractieVerrekeningNieuweWaarden.AANKOOP_STRING;
//    } else {
//      tt = AAEffRekFractieVerrekeningNieuweWaarden.VERKOOP_STRING;
//    }
//    output.append(PgXml.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, tt));
//
//    // Naam van het aandeel
//    output.append(PgXml.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, fractieVerrekeningNieuweWaarden.getEffect().getName()));
//
//    // Aantal aandelen
//    output.append(PgXml.createElement(nameSpace, XmlTags.AANTAL_TAG, PgUtilities.CommaValueToString((long) fractieVerrekeningNieuweWaarden.getAantalEffecten(), 100)));
//
//    // Koers
//    output.append(PgXml.createElement(nameSpace, XmlTags.KOERS_TAG, CF.format(fractieVerrekeningNieuweWaarden.getKoers())));
//
//    // Transactie datum
//    output.append(PgXml.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, DF.format(fractieVerrekeningNieuweWaarden.getExecutionDate())));
//
//    // Commentaar
//    if (fractieVerrekeningNieuweWaarden.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, fractieVerrekeningNieuweWaarden.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.FRACTIE_VERREKENING_NIEUWE_WAARDEN_TAG));
//
//    return output.toString();
//  }
  
//  private String kwartaalOverzichtToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekKwartaalOverzicht kwartaalOverzicht = (AAEffRekKwartaalOverzicht) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.KWARTAAL_OVERZICHT_TAG));
//    
//    // Jaar
//    output.append(PgXml.createElement(nameSpace, XmlTags.JAAR_TAG, Integer.toString(kwartaalOverzicht.getJaar())));
//
//    // Kwartaal
//    output.append(PgXml.createElement(nameSpace, XmlTags.KWARTAAL_TAG, Integer.toString(kwartaalOverzicht.getKwartaal())));
//
//    // Commentaar
//    if (kwartaalOverzicht.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, kwartaalOverzicht.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.KWARTAAL_OVERZICHT_TAG));
//
//    return output.toString();
//  }
  
  private String optieExpiratieToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    AAEffRekOptieExpiratie optieExpiratie = (AAEffRekOptieExpiratie) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.OPTIE_EXPIRATIE_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, optieExpiratie.getBookingDate().format(DF)));

    // Expiratie maand
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.MAAND_TAG, String.valueOf(optieExpiratie.getExpirationMonth())));

    // Expiratie jaar
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.JAAR_TAG, String.valueOf(optieExpiratie.getExpirationYear())));

    // Commentaar
    if (optieExpiratie.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, optieExpiratie.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.OPTIE_EXPIRATIE_TAG));

    return output.toString();
  }
  
  private String optieTransactieToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    AAEffRekOptieTransactie optieTransactie = (AAEffRekOptieTransactie) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.OPTIE_TRANSACTIE_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, optieTransactie.getBookingDate().format(DF)));

    // transaction type
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, optieTransactie.getOptieTransactieType().getDescription()));

    // option type
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TYPE_TAG, optieTransactie.getOptionType().getText()));

    // Naam van het aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, optieTransactie.getShare().getName()));

    // Expiratie maand
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.MAAND_TAG, String.valueOf(optieTransactie.getExpirationMonth())));

    // Expiratie jaar
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.JAAR_TAG, String.valueOf(optieTransactie.getExpirationYear())));

    // Uitoefenings koers
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.UITOEFENINGS_KOERS_TAG, CF.format(optieTransactie.getUitoefeningsKoers())));
    
    // Aantal contracten
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(optieTransactie.getAantalContracten())));

    // Koers
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.KOERS_TAG, CF.format(optieTransactie.getKoers())));

    // Provisie
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.PROVISIE_TAG, CF.format(optieTransactie.getProvisie())));

    // Transactie datum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, optieTransactie.getExecutionDate().format(DF)));

    // Commentaar
    if (optieTransactie.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, optieTransactie.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.OPTIE_TRANSACTIE_TAG));

    return output.toString();
  }
  
//  private String overnameToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekOvername overname = (AAEffRekOvername) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.OVERNAME_TAG));
//
//    // valutadatum
//    output.append(PgXml.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(overname.getBookingDate())));
//
//    // Van fonds
//    output.append(PgXml.createElement(nameSpace, XmlTags.VAN_FONDS_NAAM_TAG, overname.getVanEffect().getName()));
//
//    // Van aantal
//    output.append(PgXml.createElement(nameSpace, XmlTags.VAN_AANTAL_TAG, String.valueOf(overname.getVanAantal())));
//
//    // Naar fonds
//    output.append(PgXml.createElement(nameSpace, XmlTags.NAAR_FONDS_NAAM_TAG, overname.getNaarEffect().getName()));
//
//    // Naar aantal
//    output.append(PgXml.createElement(nameSpace, XmlTags.NAAR_AANTAL_TAG, String.valueOf(overname.getNaarAantal())));
//    
//    // Commentaar
//    if (overname.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, overname.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.OVERNAME_TAG));
//
//    return output.toString();
//  }
  
//  private String redenominatieToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekRedenominatie redenominatie = (AAEffRekRedenominatie) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.REDENOMINATIE_TAG));
//
//    // valutadatum
//    output.append(PgXml.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(redenominatie.getBookingDate())));
//
//    // Naam van het naar-aandeel
//    output.append(PgXml.createElement(nameSpace, XmlTags.NAAR_FONDS_NAAM_TAG, redenominatie.getNaarEffect().getName()));
//    
//    // Commentaar
//    if (redenominatie.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, redenominatie.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.REDENOMINATIE_TAG));
//
//    return output.toString();
//  }
  
  private String renteverrekeningToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    AAEffRekRenteverrekening renteverrekening = (AAEffRekRenteverrekening) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.RENTEVERREKENING_TAG));

    // valutadatum
    LocalDate date = renteverrekening.getBookingDate();
    if (date != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, date.format(DF)));
    }

    // van datum
    if (renteverrekening.getVanDatum() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.VAN_DATUM_TAG, renteverrekening.getVanDatum().format(DF)));
    }

    // tot datum
    if (renteverrekening.getTotDatum() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.TOT_DATUM_TAG, renteverrekening.getTotDatum().format(DF)));
    }

    // credit rente
    // TODO Tijdelijk isZero wel schrijven voor vergelijking .xml met .txt
    //if (_creditRente != null  &&  !_creditRente.isZero()) {
    if (renteverrekening.getCreditRente() != null) {
     output.append(SgmlUtil.createElement(nameSpace, XmlTags.CREDIT_RENTE_TAG, CF.format(renteverrekening.getCreditRente())));
    }

    // debet rente
    //if (_debetRente != null  &&  !_debetRente.isZero()) {
    if (renteverrekening.getDebetRente() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.DEBET_RENTE_TAG, CF.format(renteverrekening.getDebetRente())));
    }

    // Commentaar
    if (renteverrekening.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, renteverrekening.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.RENTEVERREKENING_TAG));

    return output.toString();
  }
  
//  private String stockDividendToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekStockDividend stockDividend = (AAEffRekStockDividend) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.STOCK_DIVIDEND_TAG));
//
//    // Naam van het aandeel
//    output.append(PgXml.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, stockDividend.getEffect().getName()));
//
//    // Dividend referentie
//    output.append(PgXml.createElement(nameSpace, XmlTags.DIVIDEND_REFERENTIE_TAG, stockDividend.getShareDividend().getReferenceString()));
//
//    // Aantal aandelen
//    output.append(PgXml.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(stockDividend.getAantalEffecten())));
//
//    // Commentaar
//    if (stockDividend.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, stockDividend.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.STOCK_DIVIDEND_TAG));
//
//    return output.toString();
//  }
  
//  private String stockDivTransactieToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekStockDivTransactie stockDivTransactie = (AAEffRekStockDivTransactie) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.STOCK_DIV_TRANSACTIE_TAG));
//
//    // valutadatum
//    output.append(PgXml.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(stockDivTransactie.getBookingDate())));
//
//    // transaction type
//    String tt;
//    if (stockDivTransactie.isAankoop()) {
//      tt = AAEffRekStockDivTransactie.AANKOOP_STRING;
//    } else {
//      tt = AAEffRekStockDivTransactie.VERKOOP_STRING;
//    }
//    output.append(PgXml.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, tt));
//
//    // Deeluitvoeringen
//    String du = null;
//    switch (stockDivTransactie.getUitvoeringsType()) {
//    case AAEffRekStockDivTransactie.VOLLEDIG:
//      // geen extra toevoeging
//      break;
//
//    case AAEffRekStockDivTransactie.DEELUITVOERING:
//      du = PbEffRekStockDivTransactie.DEELUITVOERING_STRING;
//      break;
//
//    case AAEffRekStockDivTransactie.DEELUITVOERING_EERSTE:
//      du = PbEffRekStockDivTransactie.DEELUITVOERING_EERSTE_STRING;
//      break;
//
//    case AAEffRekStockDivTransactie.DEELUITVOERING_LAATSTE:
//      du = PbEffRekStockDivTransactie.DEELUITVOERING_LAATSTE_STRING;
//      break;
//
//    default:
//      // kan niet voorkomen
//      break;
//    }
//    if (du != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.DEEL_UITVOERING_TAG, du));
//    }
//
//    // Naam van het aandeel
//    if (stockDivTransactie.getEffect() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, stockDivTransactie.getEffect().getName()));
//    }
//
//    // Referentie van het dividend
//    if (stockDivTransactie.getShareDividend() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.DIVIDEND_REFERENTIE_TAG, stockDivTransactie.getShareDividend().getReferenceString()));
//    }
//    
//    // Aantal effecten
//    output.append(PgXml.createElement(nameSpace, XmlTags.STOCK_DIV_AANTAL_TAG, String.valueOf(stockDivTransactie.getAantalEffecten())));
//
//    // Koers
//    output.append(PgXml.createElement(nameSpace, XmlTags.KOERS_TAG, CF.format(stockDivTransactie.getKoers())));
//
//    // Provisie
//    if (stockDivTransactie.getProvisie() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.PROVISIE_TAG, CF.format(stockDivTransactie.getProvisie())));
//    }
//
//    // Transactie datum
//    output.append(PgXml.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, DF.format(stockDivTransactie.getExecutionDate())));
//
//    // Commentaar
//    if (stockDivTransactie.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, stockDivTransactie.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.STOCK_DIV_TRANSACTIE_TAG));
//
//    return output.toString();
//  }
  
//  private String terugBetalingToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekTerugBetaling terugBetaling = (AAEffRekTerugBetaling) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.TERUG_BETALING_TAG));
//
//    // valutadatum
//    output.append(PgXml.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(terugBetaling.getBookingDate())));
//
//    // Naam van het aandeel
//    output.append(PgXml.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, terugBetaling.getEffect().getName()));
//
//    // Commentaar
//    if (terugBetaling.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, terugBetaling.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.TERUG_BETALING_TAG));
//
//    return output.toString();
//  }
  
//  private String terugBetalingRechtenToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekTerugBetalingRechten terugBetalingRechten = (AAEffRekTerugBetalingRechten) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.TERUG_BETALING_RECHTEN_TAG));
//
//    // valutadatum
//    output.append(PgXml.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(terugBetalingRechten.getBookingDate())));
//
//    // Naam van het aandeel
//    output.append(PgXml.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, terugBetalingRechten.getEffect().getName()));
//
//    // Bedrag per aandeel
//    output.append(PgXml.createElement(nameSpace, XmlTags.BETALING_PER_EFFECT_TAG, CF.format(terugBetalingRechten.getBetalingPerEffect())));
//
//    // Aantal rechten
//    output.append(PgXml.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(terugBetalingRechten.getAantalRechten())));
//
//    // Commentaar
//    if (terugBetalingRechten.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, terugBetalingRechten.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.TERUG_BETALING_RECHTEN_TAG));
//
//    return output.toString();
//  }
  
//  private String verwClaimRightsToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekVerwClaimRights verwClaimRights = (AAEffRekVerwClaimRights) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.VERW_CLAIM_RIGHTS_TAG));
//
//    // valutadatum
//    output.append(PgXml.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(verwClaimRights.getBookingDate())));
//
//    // Naam van het aandeel
//    output.append(PgXml.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, verwClaimRights.getClaimEmission().getShare().getName()));
//
//    // Claim Id
//    output.append(PgXml.createElement(nameSpace, XmlTags.CLAIM_ID_TAG, String.valueOf(verwClaimRights.getClaimEmission().getId())));
//
//    // Aantal rechten
//    output.append(PgXml.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(verwClaimRights.getNumberOfRights())));
//
//    // Transactie datum
//    output.append(PgXml.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, DF.format(verwClaimRights.getExecutionDate())));
//
//    // Commentaar
//    if (verwClaimRights.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, verwClaimRights.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.VERW_CLAIM_RIGHTS_TAG));
//
//    return output.toString();
//  }
  
//  private String verwStockdivToXmlString(PgTransaction transaction, String nameSpace) {
//    StringBuilder      output = new StringBuilder();
//    AAEffRekVerwStockdiv verwStockdiv = (AAEffRekVerwStockdiv) transaction;
//
//    // Transactie open tag
//    output.append(PgXml.createElementOpen(nameSpace, XmlTags.VERW_STOCK_DIV_TAG));
//
//    // valutadatum
//    output.append(PgXml.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, DF.format(verwStockdiv.getBookingDate())));
//
//    // Naam van het aandeel
//    output.append(PgXml.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, verwStockdiv.getEffect().getName()));
//
//    // Referentie van het dividend
//    if (verwStockdiv.getShareDividend() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.DIVIDEND_REFERENTIE_TAG,  verwStockdiv.getShareDividend().getReferenceString()));
//    }
//
//    // Aantal effecten
//    output.append(PgXml.createElement(nameSpace, XmlTags.STOCK_DIV_AANTAL_TAG, PgUtilities.CommaValueToString(verwStockdiv.getAantalStockDividenden(), 1000)));
//
//    // Naam van het stock aandeel
//    output.append(PgXml.createElement(nameSpace, XmlTags.AANDEEL_NAAM_TAG, verwStockdiv.getEffect().getName()));
//
//    // Aantal aandelen
//    output.append(PgXml.createElement(nameSpace, XmlTags.AANDEEL_AANTAL_TAG, String.valueOf(verwStockdiv.getVerwisseldInAantal())));
//
//    // Commentaar
//    if (verwStockdiv.getComment() != null) {
//      output.append(PgXml.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, verwStockdiv.getComment()));
//    }
//
//    // Transactie sluit tag
//    output.append(PgXml.createElementClose(nameSpace, XmlTags.VERW_STOCK_DIV_TAG));
//
//    return output.toString();
//  }
    
  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
//    System.out.println("PbEffRekTransactionContentHandler: startElement = " + localpart + ", state = " + state);
    data = null;

    switch (state) {
    case START:
      if (localpart.compareTo(XmlTags.AANDELEN_TRANSACTIE_TAG) == 0) {
        //System.out.println("AAEffRekTransactionContentHandler: => " + AANDELEN_TRANSACTIE_TAG);
        pushState(State.AANDELEN_TRANSACTIE);
        aandelenTransactie = new AAEffRekAandelenTransactie(account);
      } else if (localpart.compareTo(XmlTags.AANPASSING_DEKKINGS_VERPLICHTING_TAG) == 0) {
//      System.out.println("AAEffRekTransactionContentHandler: => " + AANPASSING_DEKKINGS_VERPLICHTING_TAG);
        pushState(State.AANPASSING_DEKKINGS_VERPLICHTING);
        aanpassingDekkingsVerplichting = new AAEffRekAanpassingDekkingsVerplichting(account);
      } else if (localpart.compareTo(XmlTags.AFSCHRIJVING_TAG) == 0) {
//      System.out.println("AAEffRekTransactionContentHandler: => " + AFSCHRIJVING_TAG);
        pushState(State.AFSCHRIJVING);
        afschrijving = new AAEffRekAfschrijving(account);
      } else if (localpart.compareTo(XmlTags.BELASTING_OVERZICHT_TAG) == 0) {
//      System.out.println("AAEffRekTransactionContentHandler: => " + BELASTING_OVERZICHT_TAG);
        pushState(State.BELASTING_OVERZICHT);
        belastingOverzicht = new AAEffRekBelastingOverzicht(account);
      } else if (localpart.compareTo(XmlTags.BEWAARLOON_TAG) == 0) {
//      System.out.println("AAEffRekTransactionContentHandler: => " + BEWAARLOON_TAG);
        pushState(State.BEWAARLOON);
        bewaarloon = new AAEffRekBewaarloon(account);
      } else if (localpart.compareTo(XmlTags.BIJSCHRIJVING_TAG) == 0) {
//      System.out.println("AAEffRekTransactionContentHandler: => " + BijSCHRIJVING_TAG);
        pushState(State.BIJSCHRIJVING);
        bijschrijving = new AAEffRekBijschrijving(account);
//      } else if (localpart.compareTo(XmlTags.BONUS_AANDELEN_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + BONUS_AANDELEN_TAG);
//        pushState(State.BONUS_AANDELEN);
//        bonusAandelen = new AAEffRekBonusAandelen(account);
//      } else if (localpart.compareTo(XmlTags.CLAIM_RECHTEN_ONTVANGEN_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + CLAIM_RECHTEN_ONTVANGEN_TAG);
//        pushState(State.CLAIM_RIGHTS_RECEIVED);
//        claimRechtenOntvangen = new AAEffRekClaimRightsReceived(account);
//      } else if (localpart.compareTo(XmlTags.CLAIM_RECHTEN_TRANSACTIE_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + CLAIM_RECHTEN_TRANSACTIE_TAG);
//        pushState(State.CLAIM_RIGHTS_TRANSACTION);
//        claimRightsTransaction = new AAEffRekClaimRightsTransaction(account);
//      } else if (localpart.compareTo(XmlTags.CORRECTIE_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + CORRECTIE_TAG);
//        pushState(State.CORRECTIE);
//        correctie = new AAEffRekCorrectie(account);
      } else if (localpart.compareTo(XmlTags.DIVIDEND_TAG) == 0) {
//      System.out.println("AAEffRekTransactionContentHandler: => " + DIVIDEND_TAG);
        pushState(State.DIVIDEND);
        dividend = new AAEffRekDividend(account);
//      } else if (localpart.compareTo(XmlTags.FRACTIE_VERREKENING_NIEUWE_WAARDEN_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + FRACTIE_VERREKENING_NIEUWE_WAARDEN_TAG);
//        pushState(State.FRACTIEVERR_NWE_WAARDEN);
//        fractieVerrNweWaarden = new AAEffRekFractieVerrekeningNieuweWaarden(account);
//      } else if (localpart.compareTo(XmlTags.KWARTAAL_OVERZICHT_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + KWARTAAL_OVERZICHT_TAG);
//        pushState(State.KWARTAAL_OVERZICHT);
//        kwartaalOverzicht = new AAEffRekKwartaalOverzicht(account);
      } else if (localpart.compareTo(XmlTags.OPTIE_EXPIRATIE_TAG) == 0) {
//      System.out.println("AAEffRekTransactionContentHandler: => " + OPTIE_EXPIRATIE_TAG);
        pushState(State.OPTIE_EXPIRATIE);
        optieExpiratie = new AAEffRekOptieExpiratie(account);
      } else if (localpart.compareTo(XmlTags.OPTIE_TRANSACTIE_TAG) == 0) {
//      System.out.println("AAEffRekTransactionContentHandler: => " + OPTIE_TRANSACTIE_TAG);
        pushState(State.OPTIE_TRANSACTIE);
        optieTransactie = new AAEffRekOptieTransactie(account);
//      } else if (localpart.compareTo(XmlTags.OVERNAME_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + OVERNAME_TAG);
//        pushState(State.OVERNAME);
//        overname = new AAEffRekOvername(account);
//      } else if (localpart.compareTo(XmlTags.REDENOMINATIE_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + REDENOMINATIE_TAG);
//        pushState(State.REDENOMINATIE);
//        redenominatie = new AAEffRekRedenominatie(account);
      } else if (localpart.compareTo(XmlTags.RENTEVERREKENING_TAG) == 0) {
//      System.out.println("AAEffRekTransactionContentHandler: => " + RENTEVERREKENING_TAG);
        pushState(State.RENTEVERREKENING);
        renteVerrekening = new AAEffRekRenteverrekening(account);
//      } else if (localpart.compareTo(XmlTags.STOCK_DIVIDEND_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + STOCK_DIVIDEND_TAG);
//        pushState(State.STOCK_DIVIDEND);
//        stockDividend = new AAEffRekStockDividend(account);
//      } else if (localpart.compareTo(XmlTags.STOCK_DIV_TRANSACTIE_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + STOCK_DIV_TRANSACTIE_TAG);
//        pushState(State.STOCK_DIV_TRANSACTIE);
//        stockDividendTransactie = new AAEffRekStockDivTransactie(account);
//      } else if (localpart.compareTo(XmlTags.TERUG_BETALING_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + TERUG_BETALING_TAG);
//        pushState(State.TERUGBETALING);
//        terugBetaling = new AAEffRekTerugBetaling(account);
//      } else if (localpart.compareTo(XmlTags.TERUG_BETALING_RECHTEN_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + TERUG_BETALING_RECHTEN_TAG);
//        pushState(State.TERUGBETALING_RECHTEN);
//        terugBetalingRechten = new AAEffRekTerugBetalingRechten(account);
//      } else if (localpart.compareTo(XmlTags.VERW_CLAIM_RIGHTS_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + VERW_CLAIM_RIGHTS_TAG);
//        pushState(State.VERW_CLAIM_RIGHTS);
//        verwClaimRights = new AAEffRekVerwClaimRights(account);
//      } else if (localpart.compareTo(XmlTags.VERW_STOCK_DIV_TAG) == 0) {
////      System.out.println("AAEffRekTransactionContentHandler: => " + VERW_STOCK_DIV_TAG);
//        pushState(State.VERW_STOCK_DIVIDEND);
//        verwStockDiv = new AAEffRekVerwStockdiv(account);
      }
      break;

    case AANDELEN_TRANSACTIE:
    case AANPASSING_DEKKINGS_VERPLICHTING:
    case AFSCHRIJVING:
    case BELASTING_OVERZICHT:
    case BEWAARLOON:
    case BIJSCHRIJVING:
    case BONUS_AANDELEN:
    case CLAIM_RIGHTS_RECEIVED:
    case CLAIM_RIGHTS_TRANSACTION:
    case CORRECTIE:
    case DIVIDEND:
    case FRACTIEVERR_NWE_WAARDEN:
    case KWARTAAL_OVERZICHT:
    case OPTIE_EXPIRATIE:
    case OPTIE_TRANSACTIE:
    case OVERNAME:
    case OVERSCHRIJVING:
    case REDENOMINATIE:
    case RENTEVERREKENING:
    case STOCK_DIVIDEND:
    case STOCK_DIV_TRANSACTIE:
    case TERUGBETALING:
    case TERUGBETALING_RECHTEN:
    case VERW_CLAIM_RIGHTS:
    case VERW_STOCK_DIVIDEND:    
      // nothing extra to be done on any opening tag.
      break;
    }
  }

  public void endElement(String uri, String localpart, String rawname) throws SAXException {
//    System.out.println("PbEffRekTransactionContentHandler: endElement = " + localpart);

    switch (state) {
    case START:
      break;
      
    case AANDELEN_TRANSACTIE:
      if (localpart.compareTo(XmlTags.AANDELEN_TRANSACTIE_TAG) == 0) {
        //System.out.println("PbEffRekTransactionContentHandler: <= " + XmlTags.AANDELEN_TRANSACTIE_TAG);
        short uitVoeringsType = aandelenTransactie.getUitvoeringsType();
        if ((uitVoeringsType == AAEffRekAandelenTransactie.UT_DEELUITVOERING)  ||
            (uitVoeringsType == AAEffRekAandelenTransactie.UT_DEELUITVOERING_LAATSTE)) {
          boolean found = false;
          for (int i = account.numberOfTransactions(); i > 0 && !found; i--) {
            PgTransaction transaction = (PgTransaction) account.getTransaction(i);
            if (transaction instanceof AAEffRekAandelenTransactie) {
              AAEffRekAandelenTransactie eerdereAandelenTransactie = (AAEffRekAandelenTransactie) transaction;
              if (aandelenTransactie.getEffect().equals(eerdereAandelenTransactie.getEffect())) {
                short uitVoeringsTypeCurrent = eerdereAandelenTransactie.getUitvoeringsType();
                if ((uitVoeringsTypeCurrent == AAEffRekAandelenTransactie.UT_DEELUITVOERING_EERSTE)  ||
                    (uitVoeringsTypeCurrent == AAEffRekAandelenTransactie.UT_DEELUITVOERING)) {
                  aandelenTransactie.setVorigDeelVanTransactie(eerdereAandelenTransactie);
                  found = true;
                } else {
                  throw new RuntimeException("Geen vorig deel van deeluitvoering gevonden voor transactie: " +
                      aandelenTransactie);
                }
              }
            }
          }
        }
        if (!aandelenTransactie.isValid()) {
          reportTransactionContentError("effectenrekening", "aandelen transactie", aandelenTransactie);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, aandelenTransactie));
        aandelenTransactie = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        //System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.VALUTA_DATUM_TAG);
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        aandelenTransactie.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_TYPE_TAG) == 0) {
        //System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.TRANSACTIE_TYPE_TAG);
        boolean aankoop;
        if (data.equals(XmlTags.AANKOOP_STRING)) {
          aankoop = true;
        } else if (data.equals(XmlTags.VERKOOP_STRING)) {
          aankoop = false;
        } else {
          throw new SAXParseException("Ongeldige waarde voor aankoop/verkoop: " + data, locator);
        }
        aandelenTransactie.setAankoop(aankoop);
      } else if (localpart.compareTo(XmlTags.DEEL_UITVOERING_TAG) == 0) {
//        System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.DEEL_UITVOERING_TAG);
        short uitVoeringsType = AAEffRekAandelenTransactie.UT_VOLLEDIG;
        if (data.compareTo(AAEffRekAandelenTransactie.DEELUITVOERING_EERSTE_STRING) == 0) {
          uitVoeringsType = AAEffRekAandelenTransactie.UT_DEELUITVOERING_EERSTE;
        } else if (data.compareTo(AAEffRekAandelenTransactie.DEELUITVOERING_LAATSTE_STRING) == 0) {
          uitVoeringsType = AAEffRekAandelenTransactie.UT_DEELUITVOERING_LAATSTE;
        } else if (data.compareTo(AAEffRekAandelenTransactie.DEELUITVOERING_STRING) == 0) {
          uitVoeringsType = AAEffRekAandelenTransactie.UT_DEELUITVOERING;
        } else {
          throw new SAXParseException("Ongeldig uitvoeringstype: " + data, locator);
        }
        aandelenTransactie.setUitvoeringsType(uitVoeringsType);
//      } else if (localpart.compareTo(XmlTags.BEURSORDERLIJN_TAG) == 0) {
//        //System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.BEURSORDERLIJN_TAG);
//        aandelenTransactie.setViaBeursorderlijn(true);
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        //System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.FONDS_NAAM_TAG);
        Share effect = Share.getShare(data);
        if (effect == null) {
          throw new SAXParseException("Onbekend aandeel: " + data, locator);
        } else {
          aandelenTransactie.setEffect(effect);
        }
      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
        //System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.AANTAL_TAG);
        Integer aantalEffecten = Integer.valueOf(data);
        aandelenTransactie.setAantalEffecten(aantalEffecten.intValue());
      } else if (localpart.compareTo(XmlTags.KOERS_TAG) == 0) {
        //System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.KOERS_TAG);
        try {
          aandelenTransactie.setKoers(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.ORDER_KOSTEN_TAG) == 0) {
        //System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.ORDER_KOSTEN_TAG);
        try {
          aandelenTransactie.setOrderKosten(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        //System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.TRANSACTIE_DATUM_TAG);
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        aandelenTransactie.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        //System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.COMMENTAAR_TAG);
        aandelenTransactie.setComment(data);
      }
      break;
      
    case AANPASSING_DEKKINGS_VERPLICHTING:
      if (localpart.compareTo(XmlTags.AANPASSING_DEKKINGS_VERPLICHTING_TAG) == 0) {
        if (!aanpassingDekkingsVerplichting.isValid()) {
          reportTransactionContentError("effectenrekening", "aanpassing dekkingsverplichting", aanpassingDekkingsVerplichting);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, aanpassingDekkingsVerplichting));
        aanpassingDekkingsVerplichting = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        aanpassingDekkingsVerplichting.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.BEDRAG_TAG) == 0) {
        try {
          aanpassingDekkingsVerplichting.setTransactionAmount(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        aanpassingDekkingsVerplichting.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        aanpassingDekkingsVerplichting.setComment(data);
        data = null;
      }
      break;
      
    case AFSCHRIJVING:
      if (localpart.compareTo(XmlTags.AFSCHRIJVING_TAG) == 0) {
        if (!afschrijving.isValid()) {
          reportTransactionContentError("effectenrekening", "afschrijving", afschrijving);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, afschrijving));
        afschrijving = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
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
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        afschrijving.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        afschrijving.setComment(data);
        data = null;
      }
      break;
      
    case BELASTING_OVERZICHT:
      if (localpart.compareTo(XmlTags.BELASTING_OVERZICHT_TAG) == 0) {
        if (!belastingOverzicht.isValid()) {
          reportTransactionContentError("effectenrekening", "belastingoverzicht", belastingOverzicht);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, belastingOverzicht));
        belastingOverzicht = null;
        popState();
      } else if (localpart.compareTo(XmlTags.JAAR_TAG) == 0) {
        belastingOverzicht.setJaar(Integer.valueOf(data));
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        belastingOverzicht.setComment(data);
      }
      break;
      
    case BEWAARLOON:
      if (localpart.compareTo(XmlTags.BEWAARLOON_TAG) == 0) {
        if (!bewaarloon.isValid()) {
          reportTransactionContentError("effectenrekening", "bewaarloon", bewaarloon);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, bewaarloon));
        bewaarloon = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        bewaarloon.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.BEDRAG_TAG) == 0) {
        try {
          bewaarloon.setTransactionAmount(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        bewaarloon.setComment(data);
      }
      break;
      
    case BIJSCHRIJVING:
      if (localpart.compareTo(XmlTags.BIJSCHRIJVING_TAG) == 0) {
        if (!bijschrijving.isValid()) {
          reportTransactionContentError("effectenrekening", "bijschrijving", bijschrijving);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, bijschrijving));
        bijschrijving = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
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
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        bijschrijving.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        bijschrijving.setComment(data);
        data = null;
      }
      break;
      
//    case BONUS_AANDELEN:
//      if (localpart.compareTo(XmlTags.BONUS_AANDELEN_TAG) == 0) {
//        if (!bonusAandelen.isValid()) {
//          reportTransactionContentError("effectenrekening", "bonus aandelen", bonusAandelen);
//        }
//        sumAccount.addTransaction(new FinanTransaction(bank, bonusAandelen));
//        bonusAandelen = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
//        }
//        bonusAandelen.setBookingDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
//        Share effect = Share.getShare(data);
//        if (effect == null) {
//          throw new SAXParseException("Onbekend aandeel: " + data, locator);
//        } else {
//          bonusAandelen.setEffect(effect);
//        }
//      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
//        Integer aantalEffecten = new Integer(data);
//        bonusAandelen.setAantalEffecten(aantalEffecten.intValue());
//      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
//        }
//        bonusAandelen.setExecutionDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        bonusAandelen.setComment(data);
//      }
//      break;
      
//    case CLAIM_RIGHTS_RECEIVED:
//      if (localpart.compareTo(XmlTags.CLAIM_RECHTEN_ONTVANGEN_TAG) == 0) {
//        if (!claimRechtenOntvangen.isValid()) {
//          reportTransactionContentError("effectenrekening", "claimrechten ontvangen", claimRechtenOntvangen);
//        }
//        sumAccount.addTransaction(new FinanTransaction(bank, claimRechtenOntvangen));
//        claimRechtenOntvangen = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
//        share = Share.getShare(data);
//        if (share == null) {
//          throw new SAXParseException("Onbekend aandeel: " + data, locator);
//        }
//      } else if (localpart.compareTo(XmlTags.CLAIM_ID_TAG) == 0) {
//        ClaimEmission claimEmission = share.getClaimEmission(data);
//        if (claimEmission == null) {
//          throw new SAXParseException("Onbekende claim emissie: " + data, locator);
//        } else {
//          claimRechtenOntvangen.setClaimEmission(claimEmission);
//        }
//        share = null;
//      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
//        claimRechtenOntvangen.setNumberOfRights(Integer.parseInt(data));
//      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
//        }
//        claimRechtenOntvangen.setExecutionDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        claimRechtenOntvangen.setComment(data);
//      }
//      break;
      
//    case CLAIM_RIGHTS_TRANSACTION:
//      if (localpart.compareTo(XmlTags.CLAIM_RECHTEN_TRANSACTIE_TAG) == 0) {
//        if (!claimRightsTransaction.isValid()) {
//          reportTransactionContentError("effectenrekening", "claimrechten transactie", claimRightsTransaction);
//        }
//        sumAccount.addTransaction(new FinanTransaction(bank, claimRightsTransaction));
//        claimRightsTransaction = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
//        Date  valutadatum = null;
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
//        }
//        claimRightsTransaction.setBookingDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.TRANSACTIE_TYPE_TAG) == 0) {
//        if (data.equals(AAEffRekClaimRightsTransaction.AANKOOP_STRING)) {
//          claimRightsTransaction.setBuy(true);
//        } else if (data.equals(AAEffRekClaimRightsTransaction.VERKOOP_STRING)) {
//          claimRightsTransaction.setBuy(false);
//        } else {
//          printStatus("Ongeldig transactieType - " + data);
//        }
//      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
//        share = Share.getShare(data);
//        if (share == null) {
//          throw new SAXParseException("Onbekend aandeel: " + data, locator);
//        }
//      } else if (localpart.compareTo(XmlTags.CLAIM_ID_TAG) == 0) {
//        ClaimEmission claimEmission = share.getClaimEmission(data);
//        if (claimEmission == null) {
//          throw new SAXParseException("Onbekende claim emissie: " + data, locator);
//        } else {
//          claimRightsTransaction.setClaimEmission(claimEmission);
//        }
//        share = null;
//      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
//        claimRightsTransaction.setNumberOfRights(Integer.parseInt(data));
//      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
//        }
//        claimRightsTransaction.setExecutionDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        claimRightsTransaction.setComment(data);
//      }
//      break;
      
//    case CORRECTIE:
//      if (localpart.compareTo(XmlTags.CORRECTIE_TAG) == 0) {
//        sumAccount.addTransaction(new FinanTransaction(bank, correctie));
//        correctie = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.SALDO_TAG) == 0) {
//        try {
//          correctie.setSaldo(CF.parse(data));
//        } catch (ParseException e) {
//          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
//        }
//      }
//      break;
      
    case DIVIDEND:
      if (localpart.compareTo(XmlTags.DIVIDEND_TAG) == 0) {
        if (share != null) {
          ShareDividend sd = share.getDividend(dividendReferentie);
          if (sd != null) {
            dividend.setShareDividend(sd);
          } else {
            throw new SAXParseException("Onbekend dividend, aandeel = " + share.getName() +
                ", dividend referentie = " + dividendReferentie, locator);
          }
        } else {
          throw new SAXParseException("Fondsnaam ontbreekt", locator);
        }
        if (!dividend.isValid()) {
          reportTransactionContentError("effectenrekening", "dividend", dividend);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, dividend));
        dividend = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        dividend.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        share = Share.getShare(data);
        if (share == null) {
          throw new SAXParseException("Onbekend aandeel: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.DIVIDEND_REFERENTIE_TAG) == 0) {
        dividendReferentie = data;
      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
        Integer aantalEffecten = Integer.valueOf(data);
        dividend.setAantalEffecten(aantalEffecten.intValue());
      } else if (localpart.compareTo(XmlTags.DIVIDEND_DATUM_TAG) == 0) {
        LocalDate valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        dividend.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.ZONDER_KOSTEN_TAG) == 0) {
        dividend.setZonderKosten(true);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        dividend.setComment(data);
      }
      break;
      
//    case FRACTIEVERR_NWE_WAARDEN:
//      if (localpart.compareTo(XmlTags.FRACTIE_VERREKENING_NIEUWE_WAARDEN_TAG) == 0) {
//        if (!fractieVerrNweWaarden.isValid()) {
//          reportTransactionContentError("effectenrekening", "fractie verrekening nieuwe waarde", fractieVerrNweWaarden);
//        }
//        sumAccount.addTransaction(new FinanTransaction(bank, fractieVerrNweWaarden));
//        fractieVerrNweWaarden = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
//        }
//        fractieVerrNweWaarden.setBookingDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.TRANSACTIE_TYPE_TAG) == 0) {
//        boolean aankoop;
//        if (data.equals(AAEffRekFractieVerrekeningNieuweWaarden.AANKOOP_STRING)) {
//          aankoop = true;
//        } else if (data.equals(AAEffRekFractieVerrekeningNieuweWaarden.VERKOOP_STRING)) {
//          aankoop = false;
//        } else {
//          throw new SAXParseException("Ongeldige waarde voor aankoop/verkoop: " + data, locator);
//        }
//        fractieVerrNweWaarden.setAankoop(aankoop);
//      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
//        fractieVerrNweWaarden.setEffect(Share.getShare(data));
//      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
//        try {
//          fractieVerrNweWaarden.setAantalEffecten((int) PgUtilities.PgUtilitiesParseCommaValue(data, 100));
//        } catch (ParseException e) {
//          throw new SAXParseException("Ongeldig formaat voor aantal effecten: " + data, locator);
//        }
//      } else if (localpart.compareTo(XmlTags.KOERS_TAG) == 0) {
//        try {
//          fractieVerrNweWaarden.setKoers(CF.parse(data));
//        } catch (ParseException e) {
//          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
//        }
//      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
//        }
//        fractieVerrNweWaarden.setExecutionDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        fractieVerrNweWaarden.setComment(data);
//      }
//      break;
      
//    case KWARTAAL_OVERZICHT:
//      if (localpart.compareTo(XmlTags.KWARTAAL_OVERZICHT_TAG) == 0) {
//        if (!kwartaalOverzicht.isValid()) {
//          reportTransactionContentError("effectenrekening", "kwartaaloverzicht", kwartaalOverzicht);
//        }
//        sumAccount.addTransaction(new FinanTransaction(bank, kwartaalOverzicht));
//        kwartaalOverzicht = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.JAAR_TAG) == 0) {
//        kwartaalOverzicht.setJaar(new Integer(data));
//      } else if (localpart.compareTo(XmlTags.KWARTAAL_TAG) == 0) {
//        kwartaalOverzicht.setKwartaal(new Integer(data));
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        kwartaalOverzicht.setComment(data);
//      }
//      break;
      
    case OPTIE_EXPIRATIE:
      if (localpart.compareTo(XmlTags.OPTIE_EXPIRATIE_TAG) == 0) {
        if (!optieExpiratie.isValid()) {
          reportTransactionContentError("effectenrekening", "optie expiratie", optieExpiratie);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, optieExpiratie));
        optieExpiratie = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        optieExpiratie.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.MAAND_TAG) == 0) {
        optieExpiratie.setExpirationMonth(Integer.parseInt(data));
      } else if (localpart.compareTo(XmlTags.JAAR_TAG) == 0) {
        optieExpiratie.setExpirationYear(Integer.parseInt(data));
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        optieExpiratie.setComment(data);
      }
      break;
      
    case OPTIE_TRANSACTIE:
      if (localpart.compareTo(XmlTags.OPTIE_TRANSACTIE_TAG) == 0) {
//        System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.OPTIETRANSACTIE_TAG);
        if (!optieTransactie.isValid()) {
          reportTransactionContentError("effectenrekening", "optie transactie", optieTransactie);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, optieTransactie));
        optieTransactie = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        optieTransactie.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_TYPE_TAG) == 0) {
        optieTransactie.setOptieTransactieType(OptieTransactieType.getOptieTransactieTypeVoorDescription(data));
      } else if (localpart.compareTo(XmlTags.TYPE_TAG) == 0) {
        optieTransactie.setOptionType(OptionType.getOptionTypeForText(data));
        data = null;
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        Share share = Share.getShare(data);
        if (share == null) {
          throw new SAXParseException("Onbekend aandeel: " + data, locator);
        } else {
          optieTransactie.setShare(share);
        }
      } else if (localpart.compareTo(XmlTags.MAAND_TAG) == 0) {
        optieTransactie.setExpirationMonth(Integer.parseInt(data));
      } else if (localpart.compareTo(XmlTags.JAAR_TAG) == 0) {
        //System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.JAAR_TAG);
        optieTransactie.setExpirationYear(Integer.parseInt(data));
      } else if (localpart.compareTo(XmlTags.UITOEFENINGS_KOERS_TAG) == 0) {
        try {
          optieTransactie.setUitoefeningsKoers(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
        //System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.AANTAL_TAG);
        optieTransactie.setAantalContracten(Integer.parseInt(data));
      } else if (localpart.compareTo(XmlTags.KOERS_TAG) == 0) {
        try {
          optieTransactie.setKoers(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.PROVISIE_TAG) == 0) {
        try {
          optieTransactie.setProvisie(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        optieTransactie.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        optieTransactie.setComment(data);
      }
      break;
      
//    case OVERNAME:
//      if (localpart.compareTo(XmlTags.OVERNAME_TAG) == 0) {
//        if (!overname.isValid()) {
//          reportTransactionContentError("effectenrekening", "overname", overname);
//        }
//        sumAccount.addTransaction(new FinanTransaction(bank, overname));
//        overname = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
//        }
//        overname.setBookingDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.VAN_FONDS_NAAM_TAG) == 0) {
//        overname.setVanEffect(Share.getShare(data));
//      } else if (localpart.compareTo(XmlTags.VAN_AANTAL_TAG) == 0) {
//        Integer aantalEffecten = new Integer(data);
//        overname.setVanAantal(aantalEffecten.intValue());
//      } else if (localpart.compareTo(XmlTags.NAAR_FONDS_NAAM_TAG) == 0) {
//        overname.setNaarEffect(Share.getShare(data));
//      } else if (localpart.compareTo(XmlTags.NAAR_AANTAL_TAG) == 0) {
//        Integer aantalEffecten = new Integer(data);
//        overname.setNaarAantal(aantalEffecten.intValue());
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        overname.setComment(data);
//      }
//      break;
      
//    case REDENOMINATIE:
//      if (localpart.compareTo(XmlTags.REDENOMINATIE_TAG) == 0) {
//        if (!redenominatie.isValid()) {
//          reportTransactionContentError("effectenrekening", "redenominatie", redenominatie);
//        }
//        sumAccount.addTransaction(new FinanTransaction(bank, redenominatie));
//        redenominatie = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
//        }
//        redenominatie.setBookingDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.NAAR_FONDS_NAAM_TAG) == 0) {
//        redenominatie.setNaarEffect(Share.getShare(data));
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        redenominatie.setComment(data);
//      }
//      break;
      
    case RENTEVERREKENING:
      if (localpart.compareTo(XmlTags.RENTEVERREKENING_TAG) == 0) {        //System.out.println("AAEffRekTransactionContentHandler: <= " + _renteverrekeningTag);
        if (!renteVerrekening.isValid()) {
          reportTransactionContentError("effectenrekening", "renteverrekening", renteVerrekening);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, renteVerrekening));
        renteVerrekening = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        renteVerrekening.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.VAN_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in van datum: " + data, locator);
        }
        renteVerrekening.setVanDatum(valutadatum);
      } else if (localpart.compareTo(XmlTags.TOT_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in tot datum: " + data, locator);
        }
        renteVerrekening.setTotDatum(valutadatum);
      } else if (localpart.compareTo(XmlTags.CREDIT_RENTE_TAG) == 0) {
        try {
          renteVerrekening.setCreditRente(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.DEBET_RENTE_TAG) == 0) {
        try {
          renteVerrekening.setDebetRente(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        renteVerrekening.setComment(data);
      }
      break;
      
//    case STOCK_DIVIDEND:
//      if (localpart.compareTo(XmlTags.STOCK_DIVIDEND_TAG) == 0) {
//        Share share = stockDividend.getEffect();
//        if (share != null) {
//          stockDividend.setShareDividend(share.getDividend(dividendReferentie));
//        } else {
//          throw new SAXParseException("Fondsnaam ontbreekt: " + data, locator);
//        }
//        if (!stockDividend.isValid()) {
//          reportTransactionContentError("effectenrekening", "stockdividend", stockDividend);
//        }
//        sumAccount.addTransaction(new FinanTransaction(bank, stockDividend));
//        stockDividend = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
//        stockDividend.setEffect(Share.getShare(data));
//      } else if (localpart.compareTo(XmlTags.DIVIDEND_REFERENTIE_TAG) == 0) {
//        dividendReferentie = data;
//      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
//        Integer aantalEffecten = new Integer(data);
//        stockDividend.setAantalEffecten(aantalEffecten.intValue());
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        stockDividend.setComment(data);
//      }
//      break;
      
//    case STOCK_DIV_TRANSACTIE:
//      if (localpart.compareTo(XmlTags.STOCK_DIV_TRANSACTIE_TAG) == 0) {
//        Share share = stockDividendTransactie.getEffect();
//        if (share != null) {
//          ShareDividend sd = share.getDividend(dividendReferentie);
//          if (sd != null) {
//            stockDividendTransactie.setShareDividend(sd);
//          } else {
//            throw new SAXParseException("Onbekend dividend, aandeel = " + share.getName() +
//                ", dividend referentie = " + dividendReferentie, locator);
//          }
//        } else {
//          throw new SAXParseException("Fondsnaam ontbreekt: " + data, locator);
//        }
//        if (!stockDividendTransactie.isValid()) {
//          reportTransactionContentError("effectenrekening", "stockdividend transactie", stockDividendTransactie);
//        }
//        sumAccount.addTransaction(new FinanTransaction(bank, stockDividendTransactie));
//        stockDividendTransactie = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
//        }
//        stockDividendTransactie.setBookingDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.TRANSACTIE_TYPE_TAG) == 0) {
//        boolean aankoop;
//        if (data.equals(AAEffRekStockDivTransactie.AANKOOP_STRING)) {
//          aankoop = true;
//        } else if (data.equals(AAEffRekStockDivTransactie.VERKOOP_STRING)) {
//          aankoop = false;
//        } else {
//          throw new SAXParseException("Ongeldige waarde voor aankoop/verkoop: " + data, locator);
//        }
//        stockDividendTransactie.setAankoop(aankoop);
//      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
//        stockDividendTransactie.setEffect(Share.getShare(data));
//      } else if (localpart.compareTo(XmlTags.DIVIDEND_REFERENTIE_TAG) == 0) {
//        dividendReferentie = data;
//      } else if (localpart.compareTo(XmlTags.STOCK_DIV_AANTAL_TAG) == 0) {
//        Integer aantalEffecten = new Integer(data);
//        stockDividendTransactie.setAantalEffecten(aantalEffecten.intValue());
//      } else if (localpart.compareTo(XmlTags.KOERS_TAG) == 0) {
//        try {
//          stockDividendTransactie.setKoers(CF.parse(data));
//        } catch (ParseException e) {
//          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
//        }
//      } else if (localpart.compareTo(XmlTags.PROVISIE_TAG) == 0) {
//        try {
//          stockDividendTransactie.setProvisie(CF.parse(data));
//        } catch (ParseException e) {
//          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
//        }
//      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
//        }
//        stockDividendTransactie.setExecutionDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        stockDividendTransactie.setComment(data);
//      }
//      break;
      
//    case TERUGBETALING:
//      if (localpart.compareTo(XmlTags.TERUG_BETALING_TAG) == 0) {
//        sumAccount.addTransaction(new FinanTransaction(bank, terugBetaling));
//        terugBetaling = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
//        }
//        terugBetaling.setBookingDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
//        terugBetaling.setEffect(Share.getShare(data));
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        stockDividend.setComment(data);
//      }
//      break;
      
//    case TERUGBETALING_RECHTEN:
//      if (localpart.compareTo(XmlTags.TERUG_BETALING_RECHTEN_TAG) == 0) {
//        sumAccount.addTransaction(new FinanTransaction(bank, terugBetalingRechten));
//        terugBetalingRechten = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
//        }
//        terugBetalingRechten.setBookingDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
//        terugBetalingRechten.setEffect(Share.getShare(data));
//      } else if (localpart.compareTo(XmlTags.BETALING_PER_EFFECT_TAG) == 0) {
//        try {
//          terugBetalingRechten.setBetalingPerEffect(CF.parse(data));
//        } catch (ParseException e) {
//          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
//        }
//      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
//        Integer aantalEffecten = new Integer(data);
//        terugBetalingRechten.setAantalRechten(aantalEffecten.intValue());
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        stockDividend.setComment(data);
//      }
//      break;
      
//    case VERW_CLAIM_RIGHTS:
//      if (localpart.compareTo(XmlTags.VERW_CLAIM_RIGHTS_TAG) == 0) {
//        if (!verwClaimRights.isValid()) {
//          reportTransactionContentError("effectenrekening", "verwisseling claimrechten", verwClaimRights);
//        }
//        sumAccount.addTransaction(new FinanTransaction(bank, verwClaimRights));
//        verwClaimRights = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
//        Date  valutadatum = null;
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
//        }
//        verwClaimRights.setBookingDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
//        share = Share.getShare(data);
//        if (share == null) {
//          throw new SAXParseException("Onbekend aandeel: " + data, locator);
//        }
//       } else if (localpart.compareTo(XmlTags.CLAIM_ID_TAG) == 0) {
//        ClaimEmission claimEmission = share.getClaimEmission(data);
//        if (claimEmission == null) {
//          throw new SAXParseException("Onbekende claim emissie: " + data, locator);
//        } else {
//          verwClaimRights.setClaimEmission(claimEmission);
//        }
//        share = null;
//      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
//        verwClaimRights.setNumberOfRights(Integer.parseInt(data));
//      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
//        }
//        verwClaimRights.setExecutionDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        verwClaimRights.setComment(data);
//      }
//      break;
      
//    case VERW_STOCK_DIVIDEND:    
//      if (localpart.compareTo(XmlTags.VERW_STOCK_DIV_TAG) == 0) {
//        Share share = verwStockDiv.getEffect();
//        if (share != null) {
//          ShareDividend sd = share.getDividend(dividendReferentie);
//          if (sd != null) {
//            verwStockDiv.setShareDividend(sd);
//          } else {
//            throw new SAXParseException("Onbekend dividend, aandeel = " + share.getName() +
//                ", dividend referentie = " + dividendReferentie, locator);
//          }
//        } else {
//          throw new SAXParseException("Fondsnaam ontbreekt: " + data, locator);
//        }
//        if (!verwStockDiv.isValid()) {
//          reportTransactionContentError("effectenrekening", "verwisseling stockdividend transactie", verwStockDiv);
//        }
//        sumAccount.addTransaction(new FinanTransaction(bank, verwStockDiv));
//        verwStockDiv = null;
//        popState();
//      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
//        Date  valutadatum = null;                   // boekings datum (setBookingDate)
//        try {
//          valutadatum = DF.parse(data);
//        } catch (ParseException e) {
//          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
//        }
//        verwStockDiv.setBookingDate(valutadatum);
//      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
//        //System.out.println("AAEffRekTransactionContentHandler: <= " + XmlTags.FONDS_NAAM_TAG);
//        verwStockDiv.setEffect(Share.getShare(data));
//      } else if (localpart.compareTo(XmlTags.DIVIDEND_REFERENTIE_TAG) == 0) {
//        dividendReferentie = data;
//      } else if (localpart.compareTo(XmlTags.STOCK_DIV_AANTAL_TAG) == 0) {
//        try {
//          verwStockDiv.setAantalStockDividenden((int) PgUtilities.PgUtilitiesParseCommaValue(data, 1000));
//        } catch (ParseException e) {
//          throw new SAXParseException("Ongeldig formaat voor aantal stock dividenden: " + data, locator);
//        }
//        data = null;
//      } else if (localpart.compareTo(XmlTags.AANDEEL_NAAM_TAG) == 0) {
//        verwStockDiv.setVerwisseldInAandeel(Share.getShare(data));
//      } else if (localpart.compareTo(XmlTags.AANDEEL_AANTAL_TAG) == 0) {
//        Integer aantalEffecten = new Integer(data);
//        verwStockDiv.setVerwisseldInAantal(aantalEffecten.intValue());
//      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
//        verwStockDiv.setComment(data);
//      }
//      break;
      default:
        throw new RuntimeException("Illegal state: " + state);
    }
  }

  private void reportTransactionContentError(String rekening, String transactionType, PgTransaction transaction) throws SAXParseException {
    this.printStatus("Ongeldige transactie inhoud gevonden voor ABN AMRO Bank" +
        rekening + "." + System.getProperty("line.separator") +
        "Soort transactie: " + transactionType + System.getProperty("line.separator") +
        "Transactie is : " + transaction.toString());
  }

  private void printStatus(String message) throws SAXParseException {
    System.out.println("Fout in regel " + locator.getLineNumber() + ", kolom " + locator.getColumnNumber());
    System.out.println("state = " + state);
    System.out.println("data = " + data);
    if (bank != null) {
      System.out.println("bank is set");
    }
    if (aandelenTransactie != null) {
      System.out.println("transaction is _erAandelenTransactie");
    }
    if (afschrijving != null) {
      System.out.println("transaction is afschrijving");
    }
    if (bewaarloon != null) {
      System.out.println("transaction is _erBewaarloon");
    }
    if (bijschrijving != null) {
      System.out.println("transaction is bijschrijving");
    }
    if (dividend != null) {
      System.out.println("transaction is _erDividend");
    }
//    if (fractieVerrNweWaarden != null) {
//      System.out.println("transaction is _erFractieVerrNweWaarden");
//    }
//    if (redenominatie != null) {
//      System.out.println("transaction is _erRedenominatie");
//    }
    if (renteVerrekening != null) {
      System.out.println("transaction is _erRenteVerrekening");
    }
//    if (stockDividendTransactie != null) {
//      System.out.println("transaction is _erStockDividendTransactie");
//    }
//    if (verwStockDiv != null) {
//      System.out.println("transaction is _erVerwStockDiv");
//    }
    
    throw new SAXParseException(message, null, null, locator.getLineNumber(), locator.getColumnNumber());
  }

  enum State {
    START,
    AANDELEN_TRANSACTIE,
    AANPASSING_DEKKINGS_VERPLICHTING,
    AFSCHRIJVING,
    BELASTING_OVERZICHT,
    BEWAARLOON,
    BIJSCHRIJVING,
    BONUS_AANDELEN,
    CLAIM_RIGHTS_RECEIVED,
    CLAIM_RIGHTS_TRANSACTION,
    CORRECTIE,
    DIVIDEND,
    FRACTIEVERR_NWE_WAARDEN,
    KWARTAAL_OVERZICHT,
    OPTIE_EXPIRATIE,
    OPTIE_TRANSACTIE,
    OVERNAME,
    OVERSCHRIJVING,
    REDENOMINATIE,
    RENTEVERREKENING,
    STOCK_DIVIDEND,
    STOCK_DIV_TRANSACTIE,
    TERUGBETALING,
    TERUGBETALING_RECHTEN,
    VERW_CLAIM_RIGHTS,
    VERW_STOCK_DIVIDEND;
  }
}