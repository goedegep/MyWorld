package goedegep.app.finan.lynxapp;


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
import goedegep.finan.effrek.EffRekTransactie;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekAandelenTransactie;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekAfschrijving;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekBelastingOverzicht;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekBijschrijving;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekBonusAandelen;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekClaimRightsReceived;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekClaimRightsTransaction;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekDividend;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekFractieVerrekeningNieuweWaarden;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekKwartaalOverzicht;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekMaandOverzicht;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekOptieExpiratie;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekOptieTransactie;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekOvername;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekRedenominatie;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekRenteverrekening;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekStockDivTransactie;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekStockDividend;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekTerugBetaling;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekTerugBetalingRechten;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekVerwClaimRights;
import goedegep.finan.lynx.lynxeffrek.LynxEffRekVerwStockdiv;
import goedegep.finan.stocks.ClaimEmission;
import goedegep.finan.stocks.OptieTransactieType;
import goedegep.finan.stocks.OptionType;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;
import goedegep.util.sgml.SgmlUtil;

public class LynxEffRekTransactionContentHandler 
                extends AccountContentHandler<LynxEffRekTransactionContentHandler.State> {
  // Formatters/parsers
  private static final DateTimeFormatter     DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final PgCurrencyFormat      CF = new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  private SumAccount  sumAccount = null; // The SumAccount to which transactions are to be added.
  private Bank        bank = null;
  private PgAccount   account = null;
  
  private LynxEffRekAandelenTransactie              aandelenTransactie = null;
  private LynxEffRekAfschrijving                    afschrijving = null;
  private LynxEffRekBelastingOverzicht              belastingOverzicht = null;
  private LynxEffRekBijschrijving                   bijschrijving = null;
  private LynxEffRekBonusAandelen                   bonusAandelen = null;
  private LynxEffRekClaimRightsReceived             claimRechtenOntvangen = null;
  private LynxEffRekClaimRightsTransaction          claimRightsTransaction = null;
  private LynxEffRekDividend                        dividend = null;
  private LynxEffRekFractieVerrekeningNieuweWaarden fractieVerrNweWaarden = null;
  private LynxEffRekKwartaalOverzicht               kwartaalOverzicht = null;
  private LynxEffRekMaandOverzicht                  maandOverzicht = null;
  private LynxEffRekOptieExpiratie                  optieExpiratie = null;
  private LynxEffRekOptieTransactie                 optieTransactie = null;
  private LynxEffRekOvername                        overname = null;
  private LynxEffRekRedenominatie                   redenominatie = null;
  private LynxEffRekRenteverrekening                renteVerrekening = null;
  private LynxEffRekStockDividend                   stockDividend = null;
  private LynxEffRekStockDivTransactie              stockDividendTransactie = null;
  private LynxEffRekTerugBetaling                   terugBetaling = null;
  private LynxEffRekTerugBetalingRechten            terugBetalingRechten = null;
  private LynxEffRekVerwClaimRights                 verwClaimRights = null;
  private LynxEffRekVerwStockdiv                    verwStockDiv = null;
  private String                                    dividendReferentie = null;
  
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
      return AandelenTransactieToXmlString(transaction, nameSpace);

    case EffRekTransactie.TT_AFSCHRIJVING:
      return AfschrijvingToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_BELASTING_OVERZICHT:
      return BelastingoverzichtToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_BIJSCHRIJVING:
      return BijschrijvingToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_BONUS_AANDELEN:
      return BonusAandelenToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_CLAIM_RIGHTS_RECEIVED:
      return ClaimRightsReceivedToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_CLAIM_RIGHTS_TRANSACTION:
      return ClaimRightsTransactionToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_DIVIDEND:
      return DividendToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_FRACTIEVERR_NWE_WAARDEN:
      return FractieVerrekeningNieuweWaardenToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_KWARTAAL_OVERZICHT:
      return KwartaalOverzichtToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_MAAND_OVERZICHT:
      return MaandOverzichtToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_OPTIE_EXPIRATIE:
      return OptieExpiratieToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_OPTIE_TRANSACTIE:
      return OptieTransactieToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_OVERNAME:
      return OvernameToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_REDENOMINATIE:
      return RedenominatieToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_RENTEVERREKENING:
      return RenteverrekeningToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_STOCK_DIVIDEND:
      return StockDividendToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_STOCK_DIV_TRANSACTIE:
      return StockDivTransactieToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_TERUGBETALING:
      return TerugBetalingToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_TERUGBETALING_RECHTEN:
      return TerugBetalingRechtenToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_VERW_CLAIM_RIGHTS:
      return VerwClaimRightsToXmlString(transaction, nameSpace);
      
    case EffRekTransactie.TT_VERW_STOCK_DIVIDEND:
      return VerwStockdivToXmlString(transaction, nameSpace);
      
    default:
      throw new IllegalArgumentException("Onbekend transactie type voor de volgende transactie: " + transaction.toString());
    }
  }
  
  private String AandelenTransactieToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekAandelenTransactie aandelenTransactie = (LynxEffRekAandelenTransactie) transaction;

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

    // zonder kosten
    if (aandelenTransactie.isZonderKosten()) {
      output.append(SgmlUtil.createElementOpenClose(nameSpace, XmlTags.ZONDER_KOSTEN_TAG));
    }

    // Deeluitvoeringen
    String du = null;
    switch (aandelenTransactie.getUitvoeringsType()) {
    case LynxEffRekAandelenTransactie.UT_VOLLEDIG:
      // geen extra toevoeging
      break;

    case LynxEffRekAandelenTransactie.UT_DEELUITVOERING:
      du = LynxEffRekAandelenTransactie.DEELUITVOERING_STRING;
      break;

    case LynxEffRekAandelenTransactie.UT_DEELUITVOERING_EERSTE:
      du = LynxEffRekAandelenTransactie.DEELUITVOERING_EERSTE_STRING;
      break;

    case LynxEffRekAandelenTransactie.UT_DEELUITVOERING_LAATSTE:
      du = LynxEffRekAandelenTransactie.DEELUITVOERING_LAATSTE_STRING;
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
  
  private String AfschrijvingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekAfschrijving afschrijving = (LynxEffRekAfschrijving) transaction;

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
  
  private String BelastingoverzichtToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekBelastingOverzicht belastingOverzicht = (LynxEffRekBelastingOverzicht) transaction;

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
  
  private String BijschrijvingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekBijschrijving bijschrijving = (LynxEffRekBijschrijving) transaction;

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
  
  private String BonusAandelenToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekBonusAandelen bonusAandelen = (LynxEffRekBonusAandelen) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.BONUS_AANDELEN_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, bonusAandelen.getBookingDate().format(DF)));

    // Naam van het aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, bonusAandelen.getEffect().getName()));

    // Aantal aandelen
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(bonusAandelen.getAantalEffecten())));

    // Transactie datum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, bonusAandelen.getExecutionDate().format(DF)));

    // Commentaar
    if (bonusAandelen.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, bonusAandelen.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.BONUS_AANDELEN_TAG));

    return output.toString();
  }
  
  private String ClaimRightsReceivedToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekClaimRightsReceived claimRightsReceived = (LynxEffRekClaimRightsReceived) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.CLAIM_RECHTEN_ONTVANGEN_TAG));

    // Naam van het aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, claimRightsReceived.getClaimEmission().getShare().getName()));

    // Claim Id
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.CLAIM_ID_TAG, String.valueOf(claimRightsReceived.getClaimEmission().getId())));

    // Aantal rechten
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(claimRightsReceived.getNumberOfRights())));

    // Transactie datum
    if (claimRightsReceived.getExecutionDate() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, claimRightsReceived.getExecutionDate().format(DF)));
    }

    // Commentaar
    if (claimRightsReceived.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, claimRightsReceived.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.CLAIM_RECHTEN_ONTVANGEN_TAG));

    return output.toString();
  }
  
  private String ClaimRightsTransactionToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekClaimRightsTransaction claimRightsTransaction = (LynxEffRekClaimRightsTransaction) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.CLAIM_RECHTEN_TRANSACTIE_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, claimRightsTransaction.getBookingDate().format(DF)));

    // transaction type
    String tt;
    if (claimRightsTransaction.isBuy()) {
      tt = LynxEffRekClaimRightsTransaction.AANKOOP_STRING;
    } else {
      tt = LynxEffRekClaimRightsTransaction.VERKOOP_STRING;
    }
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, tt));

    // Naam van het aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, claimRightsTransaction.getClaimEmission().getShare().getName()));

    // Claim Id
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.CLAIM_ID_TAG, String.valueOf(claimRightsTransaction.getClaimEmission().getId())));

    // Aantal rechten
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(claimRightsTransaction.getNumberOfRights())));

    // Transactie datum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, claimRightsTransaction.getExecutionDate().format(DF)));

    // Commentaar
    if (claimRightsTransaction.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, claimRightsTransaction.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.CLAIM_RECHTEN_TRANSACTIE_TAG));

    return output.toString();
  }
  
  private String DividendToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekDividend dividend = (LynxEffRekDividend) transaction;

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
  
  private String FractieVerrekeningNieuweWaardenToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekFractieVerrekeningNieuweWaarden fractieVerrekeningNieuweWaarden = (LynxEffRekFractieVerrekeningNieuweWaarden) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.FRACTIE_VERREKENING_NIEUWE_WAARDEN_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, fractieVerrekeningNieuweWaarden.getBookingDate().format(DF)));

    // transaction type
    String tt;
    if (fractieVerrekeningNieuweWaarden.isAankoop()) {
      tt = LynxEffRekFractieVerrekeningNieuweWaarden.AANKOOP_STRING;
    } else {
      tt = LynxEffRekFractieVerrekeningNieuweWaarden.VERKOOP_STRING;
    }
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, tt));

    // Naam van het aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, fractieVerrekeningNieuweWaarden.getEffect().getName()));

    // Aantal aandelen
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.AANTAL_TAG, FPVF.format(fractieVerrekeningNieuweWaarden.getAantalEffecten())));

    // Koers
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.KOERS_TAG, CF.format(fractieVerrekeningNieuweWaarden.getKoers())));

    // Transactie datum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, fractieVerrekeningNieuweWaarden.getExecutionDate().format(DF)));

    // Commentaar
    if (fractieVerrekeningNieuweWaarden.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, fractieVerrekeningNieuweWaarden.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.FRACTIE_VERREKENING_NIEUWE_WAARDEN_TAG));

    return output.toString();
  }
  
  private String KwartaalOverzichtToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekKwartaalOverzicht kwartaalOverzicht = (LynxEffRekKwartaalOverzicht) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.KWARTAAL_OVERZICHT_TAG));
    
    // Jaar
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.JAAR_TAG, Integer.toString(kwartaalOverzicht.getJaar())));

    // Kwartaal
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.KWARTAAL_TAG, Integer.toString(kwartaalOverzicht.getKwartaal())));

    // Commentaar
    if (kwartaalOverzicht.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, kwartaalOverzicht.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.KWARTAAL_OVERZICHT_TAG));

    return output.toString();
  }
  
  private String MaandOverzichtToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekMaandOverzicht maandOverzicht = (LynxEffRekMaandOverzicht) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.MAAND_OVERZICHT_TAG));
    
    // Jaar
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.JAAR_TAG, Integer.toString(maandOverzicht.getJaar())));

    // Maand
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.MAAND_TAG, Integer.toString(maandOverzicht.getMaand())));

    // Commentaar
    if (maandOverzicht.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, maandOverzicht.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.MAAND_OVERZICHT_TAG));

    return output.toString();
  }
  
  private String OptieExpiratieToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekOptieExpiratie optieExpiratie = (LynxEffRekOptieExpiratie) transaction;

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
  
  private String OptieTransactieToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekOptieTransactie optieTransactie = (LynxEffRekOptieTransactie) transaction;

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
  
  private String OvernameToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekOvername overname = (LynxEffRekOvername) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.OVERNAME_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, overname.getBookingDate().format(DF)));

    // Van fonds
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VAN_FONDS_NAAM_TAG, overname.getVanEffect().getName()));

    // Van aantal
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VAN_AANTAL_TAG, String.valueOf(overname.getVanAantal())));

    // Naar fonds
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.NAAR_FONDS_NAAM_TAG, overname.getNaarEffect().getName()));

    // Naar aantal
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.NAAR_AANTAL_TAG, String.valueOf(overname.getNaarAantal())));
    
    // Commentaar
    if (overname.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, overname.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.OVERNAME_TAG));

    return output.toString();
  }
    
  private String RedenominatieToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekRedenominatie redenominatie = (LynxEffRekRedenominatie) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.REDENOMINATIE_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, redenominatie.getBookingDate().format(DF)));

    // Naam van het naar-aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.NAAR_FONDS_NAAM_TAG, redenominatie.getNaarEffect().getName()));
    
    // Commentaar
    if (redenominatie.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, redenominatie.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.REDENOMINATIE_TAG));

    return output.toString();
  }
  
  private String RenteverrekeningToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekRenteverrekening renteverrekening = (LynxEffRekRenteverrekening) transaction;

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
  
  private String StockDividendToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekStockDividend stockDividend = (LynxEffRekStockDividend) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.STOCK_DIVIDEND_TAG));

    // Naam van het aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, stockDividend.getEffect().getName()));

    // Dividend referentie
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.DIVIDEND_REFERENTIE_TAG, stockDividend.getShareDividend().getReferenceString()));

    // Aantal aandelen
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(stockDividend.getAantalEffecten())));

    // Commentaar
    if (stockDividend.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, stockDividend.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.STOCK_DIVIDEND_TAG));

    return output.toString();
  }
  
  private String StockDivTransactieToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekStockDivTransactie stockDivTransactie = (LynxEffRekStockDivTransactie) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.STOCK_DIV_TRANSACTIE_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, stockDivTransactie.getBookingDate().format(DF)));

    // transaction type
    String tt;
    if (stockDivTransactie.isAankoop()) {
      tt = LynxEffRekStockDivTransactie.AANKOOP_STRING;
    } else {
      tt = LynxEffRekStockDivTransactie.VERKOOP_STRING;
    }
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_TYPE_TAG, tt));

    // Deeluitvoeringen
    String du = null;
    switch (stockDivTransactie.getUitvoeringsType()) {
    case LynxEffRekStockDivTransactie.VOLLEDIG:
      // geen extra toevoeging
      break;

    case LynxEffRekStockDivTransactie.DEELUITVOERING:
      du = LynxEffRekStockDivTransactie.DEELUITVOERING_STRING;
      break;

    case LynxEffRekStockDivTransactie.DEELUITVOERING_EERSTE:
      du = LynxEffRekStockDivTransactie.DEELUITVOERING_EERSTE_STRING;
      break;

    case LynxEffRekStockDivTransactie.DEELUITVOERING_LAATSTE:
      du = LynxEffRekStockDivTransactie.DEELUITVOERING_LAATSTE_STRING;
      break;

    default:
      // kan niet voorkomen
      break;
    }
    if (du != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.DEEL_UITVOERING_TAG, du));
    }

    // Naam van het aandeel
    if (stockDivTransactie.getEffect() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, stockDivTransactie.getEffect().getName()));
    }

    // Referentie van het dividend
    if (stockDivTransactie.getShareDividend() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.DIVIDEND_REFERENTIE_TAG, stockDivTransactie.getShareDividend().getReferenceString()));
    }
    
    // Aantal effecten
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.STOCK_DIV_AANTAL_TAG, String.valueOf(stockDivTransactie.getAantalEffecten())));

    // Koers
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.KOERS_TAG, CF.format(stockDivTransactie.getKoers())));

    // Provisie
    if (stockDivTransactie.getProvisie() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.PROVISIE_TAG, CF.format(stockDivTransactie.getProvisie())));
    }

    // Transactie datum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, stockDivTransactie.getExecutionDate().format(DF)));

    // Commentaar
    if (stockDivTransactie.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, stockDivTransactie.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.STOCK_DIV_TRANSACTIE_TAG));

    return output.toString();
  }
  
  private String TerugBetalingToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekTerugBetaling terugBetaling = (LynxEffRekTerugBetaling) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.TERUG_BETALING_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, terugBetaling.getBookingDate().format(DF)));

    // Naam van het aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, terugBetaling.getEffect().getName()));

    // Commentaar
    if (terugBetaling.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, terugBetaling.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.TERUG_BETALING_TAG));

    return output.toString();
  }
  
  private String TerugBetalingRechtenToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekTerugBetalingRechten terugBetalingRechten = (LynxEffRekTerugBetalingRechten) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.TERUG_BETALING_RECHTEN_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, terugBetalingRechten.getBookingDate().format(DF)));

    // Naam van het aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, terugBetalingRechten.getEffect().getName()));

    // Bedrag per aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.BETALING_PER_EFFECT_TAG, CF.format(terugBetalingRechten.getBetalingPerEffect())));

    // Aantal rechten
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(terugBetalingRechten.getAantalRechten())));

    // Commentaar
    if (terugBetalingRechten.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, terugBetalingRechten.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.TERUG_BETALING_RECHTEN_TAG));

    return output.toString();
  }
  
  private String VerwClaimRightsToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekVerwClaimRights verwClaimRights = (LynxEffRekVerwClaimRights) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.VERW_CLAIM_RIGHTS_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, verwClaimRights.getBookingDate().format(DF)));

    // Naam van het aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, verwClaimRights.getClaimEmission().getShare().getName()));

    // Claim Id
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.CLAIM_ID_TAG, String.valueOf(verwClaimRights.getClaimEmission().getId())));

    // Aantal rechten
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.AANTAL_TAG, String.valueOf(verwClaimRights.getNumberOfRights())));

    // Transactie datum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.TRANSACTIE_DATUM_TAG, verwClaimRights.getExecutionDate().format(DF)));

    // Commentaar
    if (verwClaimRights.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, verwClaimRights.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.VERW_CLAIM_RIGHTS_TAG));

    return output.toString();
  }
  
  private String VerwStockdivToXmlString(PgTransaction transaction, String nameSpace) {
    StringBuilder      output = new StringBuilder();
    LynxEffRekVerwStockdiv verwStockdiv = (LynxEffRekVerwStockdiv) transaction;

    // Transactie open tag
    output.append(SgmlUtil.createElementOpen(nameSpace, XmlTags.VERW_STOCK_DIV_TAG));

    // valutadatum
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.VALUTA_DATUM_TAG, verwStockdiv.getBookingDate().format(DF)));

    // Naam van het aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.FONDS_NAAM_TAG, verwStockdiv.getEffect().getName()));

    // Referentie van het dividend
    if (verwStockdiv.getShareDividend() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.DIVIDEND_REFERENTIE_TAG,  verwStockdiv.getShareDividend().getReferenceString()));
    }

    // Aantal effecten
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.STOCK_DIV_AANTAL_TAG, FPVF.format(verwStockdiv.getAantalStockDividenden())));

    // Naam van het stock aandeel
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.AANDEEL_NAAM_TAG, verwStockdiv.getEffect().getName()));

    // Aantal aandelen
    output.append(SgmlUtil.createElement(nameSpace, XmlTags.AANDEEL_AANTAL_TAG, String.valueOf(verwStockdiv.getVerwisseldInAantal())));

    // Commentaar
    if (verwStockdiv.getComment() != null) {
      output.append(SgmlUtil.createElement(nameSpace, XmlTags.COMMENTAAR_TAG, verwStockdiv.getComment()));
    }

    // Transactie sluit tag
    output.append(SgmlUtil.createElementClose(nameSpace, XmlTags.VERW_STOCK_DIV_TAG));

    return output.toString();
  }
    
  public void startElement(String uri, String localpart, String rawname, Attributes attributes) throws SAXException {
//    System.out.println("LynxEffRekTransactionContentHandler: startElement = " + localpart + ", state = " + state);
    data = null;

    switch (state) {
    case START:
      if (localpart.compareTo(XmlTags.AANDELEN_TRANSACTIE_TAG) == 0) {
        //System.out.println("LynxEffRekTransactionContentHandler: => " + AANDELEN_TRANSACTIE_TAG);
        pushState(State.AANDELEN_TRANSACTIE);
        aandelenTransactie = new LynxEffRekAandelenTransactie(account);
      } else if (localpart.compareTo(XmlTags.AFSCHRIJVING_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + AFSCHRIJVING_TAG);
        pushState(State.AFSCHRIJVING);
        afschrijving = new LynxEffRekAfschrijving(account);
      } else if (localpart.compareTo(XmlTags.BELASTING_OVERZICHT_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + BELASTING_OVERZICHT_TAG);
        pushState(State.BELASTING_OVERZICHT);
        belastingOverzicht = new LynxEffRekBelastingOverzicht(account);
      } else if (localpart.compareTo(XmlTags.BIJSCHRIJVING_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + BijSCHRIJVING_TAG);
        pushState(State.BIJSCHRIJVING);
        bijschrijving = new LynxEffRekBijschrijving(account);
      } else if (localpart.compareTo(XmlTags.BONUS_AANDELEN_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + BONUS_AANDELEN_TAG);
        pushState(State.BONUS_AANDELEN);
        bonusAandelen = new LynxEffRekBonusAandelen(account);
      } else if (localpart.compareTo(XmlTags.CLAIM_RECHTEN_ONTVANGEN_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + CLAIM_RECHTEN_ONTVANGEN_TAG);
        pushState(State.CLAIM_RIGHTS_RECEIVED);
        claimRechtenOntvangen = new LynxEffRekClaimRightsReceived(account);
      } else if (localpart.compareTo(XmlTags.CLAIM_RECHTEN_TRANSACTIE_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + CLAIM_RECHTEN_TRANSACTIE_TAG);
        pushState(State.CLAIM_RIGHTS_TRANSACTION);
        claimRightsTransaction = new LynxEffRekClaimRightsTransaction(account);
      } else if (localpart.compareTo(XmlTags.DIVIDEND_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + DIVIDEND_TAG);
        pushState(State.DIVIDEND);
        dividend = new LynxEffRekDividend(account);
      } else if (localpart.compareTo(XmlTags.FRACTIE_VERREKENING_NIEUWE_WAARDEN_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + FRACTIE_VERREKENING_NIEUWE_WAARDEN_TAG);
        pushState(State.FRACTIEVERR_NWE_WAARDEN);
        fractieVerrNweWaarden = new LynxEffRekFractieVerrekeningNieuweWaarden(account);
      } else if (localpart.compareTo(XmlTags.KWARTAAL_OVERZICHT_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + KWARTAAL_OVERZICHT_TAG);
        pushState(State.KWARTAAL_OVERZICHT);
        kwartaalOverzicht = new LynxEffRekKwartaalOverzicht(account);
      } else if (localpart.compareTo(XmlTags.MAAND_OVERZICHT_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + MAAND_OVERZICHT_TAG);
        pushState(State.MAAND_OVERZICHT);
        maandOverzicht = new LynxEffRekMaandOverzicht(account);
      } else if (localpart.compareTo(XmlTags.OPTIE_EXPIRATIE_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + OPTIE_EXPIRATIE_TAG);
        pushState(State.OPTIE_EXPIRATIE);
        optieExpiratie = new LynxEffRekOptieExpiratie(account);
      } else if (localpart.compareTo(XmlTags.OPTIE_TRANSACTIE_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + OPTIE_TRANSACTIE_TAG);
        pushState(State.OPTIE_TRANSACTIE);
        optieTransactie = new LynxEffRekOptieTransactie(account);
      } else if (localpart.compareTo(XmlTags.OVERNAME_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + OVERNAME_TAG);
        pushState(State.OVERNAME);
        overname = new LynxEffRekOvername(account);
      } else if (localpart.compareTo(XmlTags.REDENOMINATIE_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + REDENOMINATIE_TAG);
        pushState(State.REDENOMINATIE);
        redenominatie = new LynxEffRekRedenominatie(account);
      } else if (localpart.compareTo(XmlTags.RENTEVERREKENING_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + RENTEVERREKENING_TAG);
        pushState(State.RENTEVERREKENING);
        renteVerrekening = new LynxEffRekRenteverrekening(account);
      } else if (localpart.compareTo(XmlTags.STOCK_DIVIDEND_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + STOCK_DIVIDEND_TAG);
        pushState(State.STOCK_DIVIDEND);
        stockDividend = new LynxEffRekStockDividend(account);
      } else if (localpart.compareTo(XmlTags.STOCK_DIV_TRANSACTIE_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + STOCK_DIV_TRANSACTIE_TAG);
        pushState(State.STOCK_DIV_TRANSACTIE);
        stockDividendTransactie = new LynxEffRekStockDivTransactie(account);
      } else if (localpart.compareTo(XmlTags.TERUG_BETALING_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + TERUG_BETALING_TAG);
        pushState(State.TERUGBETALING);
        terugBetaling = new LynxEffRekTerugBetaling(account);
      } else if (localpart.compareTo(XmlTags.TERUG_BETALING_RECHTEN_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + TERUG_BETALING_RECHTEN_TAG);
        pushState(State.TERUGBETALING_RECHTEN);
        terugBetalingRechten = new LynxEffRekTerugBetalingRechten(account);
      } else if (localpart.compareTo(XmlTags.VERW_CLAIM_RIGHTS_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + VERW_CLAIM_RIGHTS_TAG);
        pushState(State.VERW_CLAIM_RIGHTS);
        verwClaimRights = new LynxEffRekVerwClaimRights(account);
      } else if (localpart.compareTo(XmlTags.VERW_STOCK_DIV_TAG) == 0) {
//      System.out.println("LynxEffRekTransactionContentHandler: => " + VERW_STOCK_DIV_TAG);
        pushState(State.VERW_STOCK_DIVIDEND);
        verwStockDiv = new LynxEffRekVerwStockdiv(account);
      }
      break;

    case AANDELEN_TRANSACTIE:
    case AFSCHRIJVING:
    case BELASTING_OVERZICHT:
    case BIJSCHRIJVING:
    case BONUS_AANDELEN:
    case CLAIM_RIGHTS_RECEIVED:
    case CLAIM_RIGHTS_TRANSACTION:
    case DIVIDEND:
    case FRACTIEVERR_NWE_WAARDEN:
    case KWARTAAL_OVERZICHT:
    case MAAND_OVERZICHT:
    case OPTIE_EXPIRATIE:
    case OPTIE_TRANSACTIE:
    case OVERNAME:
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
//    System.out.println("LynxEffRekTransactionContentHandler: endElement = " + localpart);

    switch (state) {
    case START:
      break;
      
    case AANDELEN_TRANSACTIE:
      if (localpart.compareTo(XmlTags.AANDELEN_TRANSACTIE_TAG) == 0) {
        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.AANDELEN_TRANSACTIE_TAG);
        short uitVoeringsType = aandelenTransactie.getUitvoeringsType();
        if ((uitVoeringsType == LynxEffRekAandelenTransactie.UT_DEELUITVOERING)  ||
            (uitVoeringsType == LynxEffRekAandelenTransactie.UT_DEELUITVOERING_LAATSTE)) {
          boolean found = false;
          for (int i = account.numberOfTransactions(); i > 0 && !found; i--) {
            PgTransaction transaction = (PgTransaction) account.getTransaction(i);
            if (transaction instanceof LynxEffRekAandelenTransactie) {
              LynxEffRekAandelenTransactie eerdereAandelenTransactie = (LynxEffRekAandelenTransactie) transaction;
              if (aandelenTransactie.getEffect().equals(eerdereAandelenTransactie.getEffect())) {
                short uitVoeringsTypeCurrent = eerdereAandelenTransactie.getUitvoeringsType();
                if ((uitVoeringsTypeCurrent == LynxEffRekAandelenTransactie.UT_DEELUITVOERING_EERSTE)  ||
                    (uitVoeringsTypeCurrent == LynxEffRekAandelenTransactie.UT_DEELUITVOERING)) {
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
        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.VALUTA_DATUM_TAG);
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        aandelenTransactie.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_TYPE_TAG) == 0) {
        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.TRANSACTIE_TYPE_TAG);
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
//        System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.DEEL_UITVOERING_TAG);
        short uitVoeringsType = LynxEffRekAandelenTransactie.UT_VOLLEDIG;
        if (data.compareTo(LynxEffRekAandelenTransactie.DEELUITVOERING_EERSTE_STRING) == 0) {
          uitVoeringsType = LynxEffRekAandelenTransactie.UT_DEELUITVOERING_EERSTE;
        } else if (data.compareTo(LynxEffRekAandelenTransactie.DEELUITVOERING_LAATSTE_STRING) == 0) {
          uitVoeringsType = LynxEffRekAandelenTransactie.UT_DEELUITVOERING_LAATSTE;
        } else if (data.compareTo(LynxEffRekAandelenTransactie.DEELUITVOERING_STRING) == 0) {
          uitVoeringsType = LynxEffRekAandelenTransactie.UT_DEELUITVOERING;
        } else {
          throw new SAXParseException("Ongeldig uitvoeringstype: " + data, locator);
        }
        aandelenTransactie.setUitvoeringsType(uitVoeringsType);
      } else if (localpart.compareTo(XmlTags.ZONDER_KOSTEN_TAG) == 0) {
        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.ZONDER_KOSTEN_TAG);
//        aandelenTransactie.setZonderKosten(true);
        aandelenTransactie.setOrderKosten(new PgCurrency(aandelenTransactie.getKoers().getCurrency(), 0L));
//      } else if (localpart.compareTo(XmlTags.BEURSORDERLIJN_TAG) == 0) {
//        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.BEURSORDERLIJN_TAG);
//        aandelenTransactie.setViaBeursorderlijn(true);
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.FONDS_NAAM_TAG);
        Share effect = Share.getShare(data);
        if (effect == null) {
          throw new SAXParseException("Onbekend aandeel: " + data, locator);
        } else {
          aandelenTransactie.setEffect(effect);
        }
      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.AANTAL_TAG);
        Integer aantalEffecten = Integer.valueOf(data);
        aandelenTransactie.setAantalEffecten(aantalEffecten.intValue());
      } else if (localpart.compareTo(XmlTags.KOERS_TAG) == 0) {
        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.KOERS_TAG);
        try {
          aandelenTransactie.setKoers(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.TRANSACTIE_DATUM_TAG);
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        aandelenTransactie.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.COMMENTAAR_TAG);
        aandelenTransactie.setComment(data);
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
      
    case BONUS_AANDELEN:
      if (localpart.compareTo(XmlTags.BONUS_AANDELEN_TAG) == 0) {
        if (!bonusAandelen.isValid()) {
          reportTransactionContentError("effectenrekening", "bonus aandelen", bonusAandelen);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, bonusAandelen));
        bonusAandelen = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        bonusAandelen.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        Share effect = Share.getShare(data);
        if (effect == null) {
          throw new SAXParseException("Onbekend aandeel: " + data, locator);
        } else {
          bonusAandelen.setEffect(effect);
        }
      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
        Integer aantalEffecten = Integer.valueOf(data);
        bonusAandelen.setAantalEffecten(aantalEffecten.intValue());
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        bonusAandelen.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        bonusAandelen.setComment(data);
      }
      break;
      
    case CLAIM_RIGHTS_RECEIVED:
      if (localpart.compareTo(XmlTags.CLAIM_RECHTEN_ONTVANGEN_TAG) == 0) {
        if (!claimRechtenOntvangen.isValid()) {
          reportTransactionContentError("effectenrekening", "claimrechten ontvangen", claimRechtenOntvangen);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, claimRechtenOntvangen));
        claimRechtenOntvangen = null;
        popState();
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        share = Share.getShare(data);
        if (share == null) {
          throw new SAXParseException("Onbekend aandeel: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.CLAIM_ID_TAG) == 0) {
        ClaimEmission claimEmission = share.getClaimEmission(data);
        if (claimEmission == null) {
          throw new SAXParseException("Onbekende claim emissie: " + data, locator);
        } else {
          claimRechtenOntvangen.setClaimEmission(claimEmission);
        }
        share = null;
      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
        claimRechtenOntvangen.setNumberOfRights(Integer.parseInt(data));
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        claimRechtenOntvangen.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        claimRechtenOntvangen.setComment(data);
      }
      break;
      
    case CLAIM_RIGHTS_TRANSACTION:
      if (localpart.compareTo(XmlTags.CLAIM_RECHTEN_TRANSACTIE_TAG) == 0) {
        if (!claimRightsTransaction.isValid()) {
          reportTransactionContentError("effectenrekening", "claimrechten transactie", claimRightsTransaction);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, claimRightsTransaction));
        claimRightsTransaction = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        claimRightsTransaction.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_TYPE_TAG) == 0) {
        if (data.equals(LynxEffRekClaimRightsTransaction.AANKOOP_STRING)) {
          claimRightsTransaction.setBuy(true);
        } else if (data.equals(LynxEffRekClaimRightsTransaction.VERKOOP_STRING)) {
          claimRightsTransaction.setBuy(false);
        } else {
          printStatus("Ongeldig transactieType - " + data);
        }
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        share = Share.getShare(data);
        if (share == null) {
          throw new SAXParseException("Onbekend aandeel: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.CLAIM_ID_TAG) == 0) {
        ClaimEmission claimEmission = share.getClaimEmission(data);
        if (claimEmission == null) {
          throw new SAXParseException("Onbekende claim emissie: " + data, locator);
        } else {
          claimRightsTransaction.setClaimEmission(claimEmission);
        }
        share = null;
      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
        claimRightsTransaction.setNumberOfRights(Integer.parseInt(data));
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        claimRightsTransaction.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        claimRightsTransaction.setComment(data);
      }
      break;
      
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
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
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
      
    case FRACTIEVERR_NWE_WAARDEN:
      if (localpart.compareTo(XmlTags.FRACTIE_VERREKENING_NIEUWE_WAARDEN_TAG) == 0) {
        if (!fractieVerrNweWaarden.isValid()) {
          reportTransactionContentError("effectenrekening", "fractie verrekening nieuwe waarde", fractieVerrNweWaarden);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, fractieVerrNweWaarden));
        fractieVerrNweWaarden = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        fractieVerrNweWaarden.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_TYPE_TAG) == 0) {
        boolean aankoop;
        if (data.equals(LynxEffRekFractieVerrekeningNieuweWaarden.AANKOOP_STRING)) {
          aankoop = true;
        } else if (data.equals(LynxEffRekFractieVerrekeningNieuweWaarden.VERKOOP_STRING)) {
          aankoop = false;
        } else {
          throw new SAXParseException("Ongeldige waarde voor aankoop/verkoop: " + data, locator);
        }
        fractieVerrNweWaarden.setAankoop(aankoop);
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        fractieVerrNweWaarden.setEffect(Share.getShare(data));
      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
        try {
          fractieVerrNweWaarden.setAantalEffecten(FPVF.parse(data, 100));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig formaat voor aantal effecten: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.KOERS_TAG) == 0) {
        try {
          fractieVerrNweWaarden.setKoers(CF.parse(data));
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
        fractieVerrNweWaarden.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        fractieVerrNweWaarden.setComment(data);
      }
      break;
      
    case KWARTAAL_OVERZICHT:
      if (localpart.compareTo(XmlTags.KWARTAAL_OVERZICHT_TAG) == 0) {
        if (!kwartaalOverzicht.isValid()) {
          reportTransactionContentError("effectenrekening", "kwartaaloverzicht", kwartaalOverzicht);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, kwartaalOverzicht));
        kwartaalOverzicht = null;
        popState();
      } else if (localpart.compareTo(XmlTags.JAAR_TAG) == 0) {
        kwartaalOverzicht.setJaar(Integer.valueOf(data));
      } else if (localpart.compareTo(XmlTags.KWARTAAL_TAG) == 0) {
        kwartaalOverzicht.setKwartaal(Integer.valueOf(data));
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        kwartaalOverzicht.setComment(data);
      }
      break;
      
    case MAAND_OVERZICHT:
      if (localpart.compareTo(XmlTags.MAAND_OVERZICHT_TAG) == 0) {
        if (!maandOverzicht.isValid()) {
          reportTransactionContentError("effectenrekening", "maandoverzicht", maandOverzicht);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, maandOverzicht));
        maandOverzicht = null;
        popState();
      } else if (localpart.compareTo(XmlTags.JAAR_TAG) == 0) {
        maandOverzicht.setJaar(Integer.valueOf(data));
      } else if (localpart.compareTo(XmlTags.MAAND_TAG) == 0) {
        maandOverzicht.setMaand(Integer.valueOf(data));
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        maandOverzicht.setComment(data);
      }
      break;
      
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
//        System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.OPTIETRANSACTIE_TAG);
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
        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.JAAR_TAG);
        optieTransactie.setExpirationYear(Integer.parseInt(data));
      } else if (localpart.compareTo(XmlTags.UITOEFENINGS_KOERS_TAG) == 0) {
        try {
          optieTransactie.setUitoefeningsKoers(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.AANTAL_TAG);
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
      
    case OVERNAME:
      if (localpart.compareTo(XmlTags.OVERNAME_TAG) == 0) {
        if (!overname.isValid()) {
          reportTransactionContentError("effectenrekening", "overname", overname);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, overname));
        overname = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        overname.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.VAN_FONDS_NAAM_TAG) == 0) {
        overname.setVanEffect(Share.getShare(data));
      } else if (localpart.compareTo(XmlTags.VAN_AANTAL_TAG) == 0) {
        Integer aantalEffecten = Integer.valueOf(data);
        overname.setVanAantal(aantalEffecten.intValue());
      } else if (localpart.compareTo(XmlTags.NAAR_FONDS_NAAM_TAG) == 0) {
        overname.setNaarEffect(Share.getShare(data));
      } else if (localpart.compareTo(XmlTags.NAAR_AANTAL_TAG) == 0) {
        Integer aantalEffecten = Integer.valueOf(data);
        overname.setNaarAantal(aantalEffecten.intValue());
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        overname.setComment(data);
      }
      break;
      
    case REDENOMINATIE:
      if (localpart.compareTo(XmlTags.REDENOMINATIE_TAG) == 0) {
        if (!redenominatie.isValid()) {
          reportTransactionContentError("effectenrekening", "redenominatie", redenominatie);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, redenominatie));
        redenominatie = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        redenominatie.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.NAAR_FONDS_NAAM_TAG) == 0) {
        redenominatie.setNaarEffect(Share.getShare(data));
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        redenominatie.setComment(data);
      }
      break;
      
    case RENTEVERREKENING:
      if (localpart.compareTo(XmlTags.RENTEVERREKENING_TAG) == 0) {        //System.out.println("LynxEffRekTransactionContentHandler: <= " + _renteverrekeningTag);
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
      
    case STOCK_DIVIDEND:
      if (localpart.compareTo(XmlTags.STOCK_DIVIDEND_TAG) == 0) {
        Share share = stockDividend.getEffect();
        if (share != null) {
          stockDividend.setShareDividend(share.getDividend(dividendReferentie));
        } else {
          throw new SAXParseException("Fondsnaam ontbreekt: " + data, locator);
        }
        if (!stockDividend.isValid()) {
          reportTransactionContentError("effectenrekening", "stockdividend", stockDividend);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, stockDividend));
        stockDividend = null;
        popState();
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        stockDividend.setEffect(Share.getShare(data));
      } else if (localpart.compareTo(XmlTags.DIVIDEND_REFERENTIE_TAG) == 0) {
        dividendReferentie = data;
      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
        long aantalEffectenInteger = Long.valueOf(data);
        FixedPointValue aantalEffecten = new FixedPointValue(aantalEffectenInteger);
        stockDividend.setAantalEffecten(aantalEffecten);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        stockDividend.setComment(data);
      }
      break;
      
    case STOCK_DIV_TRANSACTIE:
      if (localpart.compareTo(XmlTags.STOCK_DIV_TRANSACTIE_TAG) == 0) {
        Share share = stockDividendTransactie.getEffect();
        if (share != null) {
          ShareDividend sd = share.getDividend(dividendReferentie);
          if (sd != null) {
            stockDividendTransactie.setShareDividend(sd);
          } else {
            throw new SAXParseException("Onbekend dividend, aandeel = " + share.getName() +
                ", dividend referentie = " + dividendReferentie, locator);
          }
        } else {
          throw new SAXParseException("Fondsnaam ontbreekt: " + data, locator);
        }
        if (!stockDividendTransactie.isValid()) {
          reportTransactionContentError("effectenrekening", "stockdividend transactie", stockDividendTransactie);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, stockDividendTransactie));
        stockDividendTransactie = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        stockDividendTransactie.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_TYPE_TAG) == 0) {
        boolean aankoop;
        if (data.equals(LynxEffRekStockDivTransactie.AANKOOP_STRING)) {
          aankoop = true;
        } else if (data.equals(LynxEffRekStockDivTransactie.VERKOOP_STRING)) {
          aankoop = false;
        } else {
          throw new SAXParseException("Ongeldige waarde voor aankoop/verkoop: " + data, locator);
        }
        stockDividendTransactie.setAankoop(aankoop);
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        stockDividendTransactie.setEffect(Share.getShare(data));
      } else if (localpart.compareTo(XmlTags.DIVIDEND_REFERENTIE_TAG) == 0) {
        dividendReferentie = data;
      } else if (localpart.compareTo(XmlTags.STOCK_DIV_AANTAL_TAG) == 0) {
        long aantalEffectenLong = Long.valueOf(data);
        FixedPointValue aantalEffecten = new FixedPointValue(aantalEffectenLong);
        stockDividendTransactie.setAantalEffecten(aantalEffecten);
      } else if (localpart.compareTo(XmlTags.KOERS_TAG) == 0) {
        try {
          stockDividendTransactie.setKoers(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.PROVISIE_TAG) == 0) {
        try {
          stockDividendTransactie.setProvisie(CF.parse(data));
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
        stockDividendTransactie.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        stockDividendTransactie.setComment(data);
      }
      break;
      
    case TERUGBETALING:
      if (localpart.compareTo(XmlTags.TERUG_BETALING_TAG) == 0) {
        sumAccount.addTransaction(new FinanTransaction(bank, terugBetaling));
        terugBetaling = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        terugBetaling.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        terugBetaling.setEffect(Share.getShare(data));
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        stockDividend.setComment(data);
      }
      break;
      
    case TERUGBETALING_RECHTEN:
      if (localpart.compareTo(XmlTags.TERUG_BETALING_RECHTEN_TAG) == 0) {
        sumAccount.addTransaction(new FinanTransaction(bank, terugBetalingRechten));
        terugBetalingRechten = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        terugBetalingRechten.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        terugBetalingRechten.setEffect(Share.getShare(data));
      } else if (localpart.compareTo(XmlTags.BETALING_PER_EFFECT_TAG) == 0) {
        try {
          terugBetalingRechten.setBetalingPerEffect(CF.parse(data));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig bedrag formaat: " + data, locator);
        }
      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
        Integer aantalEffecten = Integer.valueOf(data);
        terugBetalingRechten.setAantalRechten(aantalEffecten.intValue());
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        stockDividend.setComment(data);
      }
      break;
      
    case VERW_CLAIM_RIGHTS:
      if (localpart.compareTo(XmlTags.VERW_CLAIM_RIGHTS_TAG) == 0) {
        if (!verwClaimRights.isValid()) {
          reportTransactionContentError("effectenrekening", "verwisseling claimrechten", verwClaimRights);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, verwClaimRights));
        verwClaimRights = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        verwClaimRights.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        share = Share.getShare(data);
        if (share == null) {
          throw new SAXParseException("Onbekend aandeel: " + data, locator);
        }
       } else if (localpart.compareTo(XmlTags.CLAIM_ID_TAG) == 0) {
        ClaimEmission claimEmission = share.getClaimEmission(data);
        if (claimEmission == null) {
          throw new SAXParseException("Onbekende claim emissie: " + data, locator);
        } else {
          verwClaimRights.setClaimEmission(claimEmission);
        }
        share = null;
      } else if (localpart.compareTo(XmlTags.AANTAL_TAG) == 0) {
        verwClaimRights.setNumberOfRights(Integer.parseInt(data));
      } else if (localpart.compareTo(XmlTags.TRANSACTIE_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in transactiedatum: " + data, locator);
        }
        verwClaimRights.setExecutionDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        verwClaimRights.setComment(data);
      }
      break;
      
    case VERW_STOCK_DIVIDEND:    
      if (localpart.compareTo(XmlTags.VERW_STOCK_DIV_TAG) == 0) {
        Share share = verwStockDiv.getEffect();
        if (share != null) {
          ShareDividend sd = share.getDividend(dividendReferentie);
          if (sd != null) {
            verwStockDiv.setShareDividend(sd);
          } else {
            throw new SAXParseException("Onbekend dividend, aandeel = " + share.getName() +
                ", dividend referentie = " + dividendReferentie, locator);
          }
        } else {
          throw new SAXParseException("Fondsnaam ontbreekt: " + data, locator);
        }
        if (!verwStockDiv.isValid()) {
          reportTransactionContentError("effectenrekening", "verwisseling stockdividend transactie", verwStockDiv);
        }
        sumAccount.addTransaction(new FinanTransaction(bank, verwStockDiv));
        verwStockDiv = null;
        popState();
      } else if (localpart.compareTo(XmlTags.VALUTA_DATUM_TAG) == 0) {
        LocalDate  valutadatum = null;                   // boekings datum (setBookingDate)
        try {
          valutadatum = LocalDate.parse(data, DF);
        } catch (DateTimeParseException e) {
          throw new SAXParseException("Formaat fout in valutadatum: " + data, locator);
        }
        verwStockDiv.setBookingDate(valutadatum);
      } else if (localpart.compareTo(XmlTags.FONDS_NAAM_TAG) == 0) {
        //System.out.println("LynxEffRekTransactionContentHandler: <= " + XmlTags.FONDS_NAAM_TAG);
        verwStockDiv.setEffect(Share.getShare(data));
      } else if (localpart.compareTo(XmlTags.DIVIDEND_REFERENTIE_TAG) == 0) {
        dividendReferentie = data;
      } else if (localpart.compareTo(XmlTags.STOCK_DIV_AANTAL_TAG) == 0) {
        try {
          verwStockDiv.setAantalStockDividenden(FPVF.parse(data, 1000));
        } catch (ParseException e) {
          throw new SAXParseException("Ongeldig formaat voor aantal stock dividenden: " + data, locator);
        }
        data = null;
      } else if (localpart.compareTo(XmlTags.AANDEEL_NAAM_TAG) == 0) {
        verwStockDiv.setVerwisseldInAandeel(Share.getShare(data));
      } else if (localpart.compareTo(XmlTags.AANDEEL_AANTAL_TAG) == 0) {
        Integer aantalEffecten = Integer.valueOf(data);
        verwStockDiv.setVerwisseldInAantal(aantalEffecten.intValue());
      } else if (localpart.compareTo(XmlTags.COMMENTAAR_TAG) == 0) {
        verwStockDiv.setComment(data);
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
    if (bank != null) {
      System.out.println("bank is set");
    }
    if (aandelenTransactie != null) {
      System.out.println("transaction is aandelenTransactie");
    }
    if (afschrijving != null) {
      System.out.println("transaction is afschrijving");
    }
    if (bijschrijving != null) {
      System.out.println("transaction is bijschrijving");
    }
    if (dividend != null) {
      System.out.println("transaction is dividend");
    }
    if (fractieVerrNweWaarden != null) {
      System.out.println("transaction is fractieVerrNweWaarden");
    }
    if (redenominatie != null) {
      System.out.println("transaction is redenominatie");
    }
    if (renteVerrekening != null) {
      System.out.println("transaction is renteVerrekening");
    }
    if (stockDividendTransactie != null) {
      System.out.println("transaction is stockDividendTransactie");
    }
    if (verwStockDiv != null) {
      System.out.println("transaction is verwStockDiv");
    }
    
    throw new SAXParseException(message, null, null, locator.getLineNumber(), locator.getColumnNumber());
  }

  enum State {
    START,
    AANDELEN_TRANSACTIE,
    AFSCHRIJVING,
    BELASTING_OVERZICHT,
    BIJSCHRIJVING,
    BONUS_AANDELEN,
    CLAIM_RIGHTS_RECEIVED,
    CLAIM_RIGHTS_TRANSACTION,
    DIVIDEND,
    FRACTIEVERR_NWE_WAARDEN,
    KWARTAAL_OVERZICHT,
    MAAND_OVERZICHT,
    OPTIE_EXPIRATIE,
    OPTIE_TRANSACTIE,
    OVERNAME,
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