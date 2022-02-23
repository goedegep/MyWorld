
//Title:        Financien
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Peter Goedegebure
//Company:      Solvation
//Description:  Postbank Effectenrekening Finan Format transaction
package goedegep.app.finan.postbankapp;

import goedegep.finan.basic.PgTransaction;
import goedegep.finan.effrek.EffRekTransactie;
import goedegep.finan.postbank.pbeffrek.PbEffRek;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;

import java.util.LinkedList;
import java.util.StringTokenizer;


public class PbEffRekFinanFormatParser {
  static ConversionEntry[] conversionTable = {
    new ConversionEntry("ABN AMRO HOLDING DIV01 -INTERIM-", "ABN AMRO HOLDING", "DIV01 -INTERIM-"),
    new ConversionEntry("ABN AMRO HOLDING DIV01", "ABN AMRO HOLDING", "DIV01"),
    new ConversionEntry("ABN AMRO HOLDING INTERIM DIV02", "ABN AMRO HOLDING", "INTERIM DIV02"),
    new ConversionEntry("ABN AMRO HOLDING DIV02", "ABN AMRO HOLDING", "DIV02"),
    new ConversionEntry("AHOLD KON DIV02 1/100E", "AHOLD KON", "DIV02 1/100E"),
    new ConversionEntry("AHOLD KON DIV02", "AHOLD KON", "DIV02 "),
    new ConversionEntry("GETRONICS DIV01", "GETRONICS", "DIV01"),
    //new ConversionEntry("HAGEMEYER DIV97 1/114", "HAGEMEYER", "DIV97 1/114"),
    new ConversionEntry("HAGEMEYER DIV97?", "HAGEMEYER NLG2.50", "DIV97?"),
    //new ConversionEntry("HAGEMEYER DIV98 1/60E", "HAGEMEYER", "DIV98 1/60E"),
    new ConversionEntry("HAGEMEYER DIV98?", "HAGEMEYER NLG2.50", "DIV98?"),
    new ConversionEntry("HAGEMEYER DIV99?", "HAGEMEYER EUR1.20", "DIV99?"),
    new ConversionEntry("HAGEMEYER DIV01", "HAGEMEYER EUR1.20", "DIV01"),
    new ConversionEntry("HAGEMEYER DIV01-2", "HAGEMEYER EUR1.20", "DIV01-2"),
    new ConversionEntry("HAGEMEYER DIV02", "HAGEMEYER EUR1.20", "DIV02"),
    new ConversionEntry("KON KPN (INTERIM2000)", "KON KPN", "(INTERIM2000)"),
    new ConversionEntry("KON KPN DIV01", "KON KPN", "DIV01"),
    new ConversionEntry("NUTRECO HOLDING DIV01", "NUTRECO HOLDING", "DIV01"),
    new ConversionEntry("NUTRECO HOLDING DIV02", "NUTRECO HOLDING", "DIV01"),
    new ConversionEntry("PHILIPS ELECTRONICS KON EUR0.20 DIV00", "PHILIPS ELECTRONICS KON EUR0.20", "DIV00"),
    new ConversionEntry("PHILIPS ELECTRONICS KON EUR0.20 DIV01", "PHILIPS ELECTRONICS KON EUR0.20", "DIV01"),
    new ConversionEntry("PHILIPS ELECTRONICS KON EUR0.20 DIV02", "PHILIPS ELECTRONICS KON EUR0.20", "DIV02"),
    new ConversionEntry("POSTBK WERELDMERKENFD EUR0.8 DIV01", "POSTBK WERELDMERKENFD EUR0.8 -2-", "DIV01"),
    new ConversionEntry("POSTBANK AANDELENFONDS EUR5 -1- DIV01", "POSTBANK AANDELENFONDS EUR5 -1-", "DIV01"),
    new ConversionEntry("POSTBANK AANDELENFONDS EUR5 -1- DIV02", "POSTBANK AANDELENFONDS EUR5 -1-", "DIV02")
  };


  // Als de transactie een deeluitvoering is, wordt hij opgenomen in de
  // _deelUitvoeringen lijst. Bij de transactie objecten, krijgt ieder object
  // van een deeluitvoering (behalve de eerste) een verwijzing naar het vorige deel.
  // Na het laatste deel, wordt de opdracht uit de _deelUitvoeringen lijst verwijderd.
  LinkedList<PgTransaction>  _deelUitvoeringen = new LinkedList<PgTransaction>();       // Lijst van opdrachten die, op dit moment van parsen, gedeeltelijk uitgevoerd zijn.

  PbEffRekFinanFormatParser() {
  }

  public EffRekTransactie parse(PbEffRek effectenRekening, StringTokenizer tokens) {
    return null;
    /*
    PgCurrencyFormat  cf = new PgCurrencyFormat();
    SimpleDateFormat  df =  new SimpleDateFormat("dd-MM-yyyy");
    Date              valutadatum;                   // boekings datum
    short             transactieType;                // transactie type
    short             effectType;                    // effect type (bijv. "aandeel")
    //short             effect;                        // effect
    Share             effect = null;                 // effect
    ShareDividend     dividend = null;               // dividend
    short             effectId = -1;                 // effect Id
    int               aantal_effecten;               // aantal effecten
    PgCurrency        koers;                         // uitoefen koers
    PgCurrency        bedrag;                        // bedrag bij van/naar giro, creditrente
    PgCurrency        bedrag2;                       // debetrente
    Date              datum;                         // uitgevoerd op
    PbEffRekTransaction transaction = null;

    // valutadatum
    if (tokens.hasMoreTokens()) {
      try {
        valutadatum = df.parse(tokens.nextToken());
      } catch (ParseException e) {
        System.out.println("FOUT: formaat fout in valutadatum");
        return null;
      }
    } else {
      System.out.println("FOUT: valutadatum ontbreekt.");
      return null;
    }

    // transaction type
    if (tokens.hasMoreTokens()) {
      transactieType = PbEffRekTransaction.typeStringToTypeID(tokens.nextToken());
    } else {
      System.out.println("FOUT: transactie type ontbreekt.");
      return null;
    }

    switch (transactieType) {
    case PbEffRekTransaction.VAN_GIRO:
    case PbEffRekTransaction.NAAR_GIRO:
      // sub class Overschrijving

      //  lees het bedrag.
      if (tokens.hasMoreTokens()) {
        bedrag = cf.parse(tokens.nextToken());
      }
      else {
        System.out.println("bedrag veld ontbreekt");
        return null;
      }

      boolean vanGiro;
      if (transactieType == PbEffRekTransaction.VAN_GIRO) {
        vanGiro = true;
      } else {
        vanGiro = false;
      }
      transaction = new PbEffRekOverschrijving(effectenRekening, valutadatum, transactieType, vanGiro, bedrag);
      break;

    case PbEffRekTransaction.BEWAARLOON:
      //  lees het bedrag.
      if (tokens.hasMoreTokens()) {
        bedrag = cf.parse(tokens.nextToken());
      }
      else {
        System.out.println("bedrag veld ontbreekt");
        return null;
      }

      transaction = new PbEffRekBewaarloon(effectenRekening, valutadatum, transactieType, bedrag);
      break;

    case PbEffRekTransaction.RENTEVERREKENING:
      Date vanDatum;
      Date totDatum;

      // van datum
      if (tokens.hasMoreTokens()) {
        try {
          vanDatum = df.parse(tokens.nextToken());
        } catch (ParseException e) {
          System.out.println("formaat fout in van-datum");
          return null;
        }
      } else {
        System.out.println("van-datum ontbreekt.");
        return null;
      }

      // tot datum
      if (tokens.hasMoreTokens()) {
        try {
          totDatum = df.parse(tokens.nextToken());
        } catch (ParseException e) {
          System.out.println("formaat fout in tot-datum");
          return null;
        }
      } else {
        System.out.println("tot-datum ontbreekt.");
        return null;
      }

      //  lees de creditrente.
      if (tokens.hasMoreTokens()) {
        bedrag = cf.parse(tokens.nextToken());
      }
      else {
        System.out.println("credit bedrag veld ontbreekt");
        return null;
      }

      //  lees de debetrente.
      if (tokens.hasMoreTokens()) {
        bedrag2 = cf.parse(tokens.nextToken());
      }
      else {
        System.out.println("debet bedrag veld ontbreekt");
        return null;
      }

      // constructor voor renteverrekening
      transaction = new PbEffRekRenteverrekening(effectenRekening,
                                                 valutadatum,
                                                 transactieType,
                                                 vanDatum,
                                                 totDatum,
                                                 bedrag,
                                                 bedrag2);
      break;

    case PbEffRekTransaction.AANKOOP:
    case PbEffRekTransaction.AANKOOP_DEELUITVOERING:
    case PbEffRekTransaction.AANKOOP_DEELUITVOERING_EERSTE:
    case PbEffRekTransaction.AANKOOP_DEELUITVOERING_LAATSTE:
    case PbEffRekTransaction.VERKOOP:
    case PbEffRekTransaction.VERKOOP_DEELUITVOERING:
    case PbEffRekTransaction.VERKOOP_DEELUITVOERING_EERSTE:
    case PbEffRekTransaction.VERKOOP_DEELUITVOERING_LAATSTE:
    case PbEffRekTransaction.AANKOOP_UIT_EMISSIE:
    case PbEffRekTransaction.AANKOOP_UIT_EMISSIE_VIA_GIRO:
    case PbEffRekTransaction.DIVIDEND:
      // bepaal of het een aankoop of verkoop is
      boolean aankoop;
      if (transactieType == PbEffRekTransaction.AANKOOP  ||
          transactieType == PbEffRekTransaction.AANKOOP_DEELUITVOERING  ||
          transactieType == PbEffRekTransaction.AANKOOP_DEELUITVOERING_EERSTE  ||
          transactieType == PbEffRekTransaction.AANKOOP_DEELUITVOERING_LAATSTE  ||
          transactieType == PbEffRekTransaction.AANKOOP_UIT_EMISSIE   ||
          transactieType == PbEffRekTransaction.AANKOOP_UIT_EMISSIE_VIA_GIRO) {
        aankoop = true;
      } else {
        aankoop = false;
      }

      // bepaal het uitvoerings type: wel of geen deeluitvoering, welk deel van deeluitvoering.
      short uitvoeringsType = 0;
      switch (transactieType) {
      case PbEffRekTransaction.AANKOOP:
      case PbEffRekTransaction.VERKOOP:
      case PbEffRekTransaction.AANKOOP_UIT_EMISSIE:
      case PbEffRekTransaction.AANKOOP_UIT_EMISSIE_VIA_GIRO:
        uitvoeringsType = PbEffRekAandelenTransactie.VOLLEDIG;
        break;

      case PbEffRekTransaction.AANKOOP_DEELUITVOERING_EERSTE:
      case PbEffRekTransaction.VERKOOP_DEELUITVOERING_EERSTE:
        uitvoeringsType = PbEffRekAandelenTransactie.DEELUITVOERING_EERSTE;
        break;

      case PbEffRekTransaction.AANKOOP_DEELUITVOERING:
      case PbEffRekTransaction.VERKOOP_DEELUITVOERING:
        uitvoeringsType = PbEffRekAandelenTransactie.DEELUITVOERING;
        break;

      case PbEffRekTransaction.AANKOOP_DEELUITVOERING_LAATSTE:
      case PbEffRekTransaction.VERKOOP_DEELUITVOERING_LAATSTE:
        uitvoeringsType = PbEffRekAandelenTransactie.DEELUITVOERING_LAATSTE;
        break;
      }

      // effect type
      if (tokens.hasMoreTokens()) {
        effectType = PbEffRekTransaction.effectTypeStringToEffectTypeID(tokens.nextToken());
      } else {
        System.out.println("effect type ontbreekt.");
        return null;
      }

      // effect
      if (tokens.hasMoreTokens()) {
        String effectName = tokens.nextToken();
        if (transactieType == PbEffRekTransaction.DIVIDEND) {
          effect = Share.getShare(effectName);
          if (effect == null) {
            System.out.println("ERROR: In PbEffRekFinanFormatParser effect is null for effectName = " + effectName);
          } else {
            //System.out.println("effect found: " + effect.getName());
          }
          //ShareDividendCombo combo = effectStringToShareAndDividend(effectName);
          //effect = combo._effect;
          //dividend = combo._dividend;
          String dividendReferentie = null;
          if (tokens.hasMoreTokens()) {
            dividendReferentie = tokens.nextToken();
            dividend = effect.getDividend(dividendReferentie);
          }
          if (dividend == null) {
            System.out.println("ERROR: In PbEffRekFinanFormatParser dividend is null for effectName = " + effectName +
                               ", dividendReferentie = " + dividendReferentie);
          } else {
            //System.out.println("dividend found: " + dividend.getReferenceString());
          }
        } else {
          if (effectType == PbEffRekTransaction.STOCK_DIVIDEND) {
            effectId = PbEffRekTransaction.effectStringToEffectID(effectName);
          } else {
            effect = Share.getShare(effectName);
            if (effect == null) {
              System.out.println("ERROR: Geen effect gevonden met de naam " + effectName);
            }
          }
        }
      } else {
        System.out.println("effect ontbreekt.");
        return null;
      }

      // aantal
      if (tokens.hasMoreTokens()) {
        Integer aantalEffecten = new Integer(tokens.nextToken());
        aantal_effecten = aantalEffecten.intValue();
      } else {
        System.out.println("effecten aantal ontbreekt.");
        return null;
      }

      // koers of dividend
      if (tokens.hasMoreTokens()) {
        koers = cf.parse(tokens.nextToken());
      }
      else {
        System.out.println("koers/dividend veld ontbreekt");
        return null;
      }

      // datum
      if (tokens.hasMoreTokens()) {
        try {
          datum = df.parse(tokens.nextToken());
        } catch (ParseException e) {
          System.out.println("formaat fout in datum");
          return null;
        }
      } else {
        System.out.println("datum veld ontbreekt");
        return null;
      }

      // emissie
      boolean emissie;
      if (transactieType == PbEffRekTransaction.AANKOOP_UIT_EMISSIE  ||
          transactieType == PbEffRekTransaction.AANKOOP_UIT_EMISSIE_VIA_GIRO) {
        emissie = true;
      } else {
        emissie = false;
      }

      // betaling via giro
      boolean betalingViaGiro;
      if (transactieType == PbEffRekTransaction.AANKOOP_UIT_EMISSIE_VIA_GIRO) {
        betalingViaGiro = true;
      } else {
        betalingViaGiro = false;
      }

      if (transactieType != PbEffRekTransaction.DIVIDEND) {
        PbEffRekTransaction vorigDeelVanTransactie = null;
        if (uitvoeringsType == PbEffRekAandelenTransactie.DEELUITVOERING  ||
            uitvoeringsType == PbEffRekAandelenTransactie.DEELUITVOERING_LAATSTE) {
          vorigDeelVanTransactie = getVorigDeelVanTransactie(aankoop, effect);
        }
        if (effectType == PbEffRekTransaction.AANDEEL) {
          // constructor voor aandelen transactie
          transaction = new PbEffRekAandelenTransactie(effectenRekening,
                                                      valutadatum,
                                                      transactieType,
                                                      aankoop,
                                                      uitvoeringsType,
                                                      (PbEffRekAandelenTransactie) vorigDeelVanTransactie,
                                                      effect,
                                                      aantal_effecten,
                                                      koers,
                                                      datum,
                                                      emissie,
                                                      betalingViaGiro);
        } else {
          // constructor voor stock dividend transactie
          transaction = new PbEffRekStockDivTransactie(effectenRekening,
                                                       valutadatum,
                                                       transactieType,
                                                       aankoop,
                                                       uitvoeringsType,
                                                       (PbEffRekStockDivTransactie) vorigDeelVanTransactie,
                                                       effectId,
                                                       aantal_effecten,
                                                       koers,
                                                       datum);
        }
        if (uitvoeringsType == PbEffRekAandelenTransactie.DEELUITVOERING_EERSTE  ||
            uitvoeringsType == PbEffRekAandelenTransactie.DEELUITVOERING) {
          _deelUitvoeringen.addFirst(transaction);
        }
      } else {
        // constructor voor dividend
        transaction = new PbEffRekDividend(effectenRekening,
                                           valutadatum,
                                           transactieType,
                                           effect,
                                           dividend,
                                           aantal_effecten,
                                           koers,
                                           datum);
      }
      break;

    case PbEffRekTransaction.VERWISSELING_STOCKDIVIDEND:
      transaction = parseVerwStockdiv(effectenRekening, valutadatum, transactieType, tokens);
      break;

    case PbEffRekTransaction.REDENOMINATIE:
      transaction = parseRedenominatie(effectenRekening, valutadatum, transactieType, tokens);
      break;

    case PbEffRekTransaction.FRACTIE_VERREKENING_NIEUWE_WAARDEN:
      transaction = parseFractieVerrekeningNieuweWaarden(effectenRekening, valutadatum, transactieType, tokens);
      break;
    }

    return transaction;
    */
  }

//  private PbEffRekRedenominatie parseRedenominatie(PbEffRek effectenRekening, Date valutadatum, short transactieType, StringTokenizer tokens) {
//    //short     vanEffect;
//    Share     vanEffect;
//    int       vanAantal;
//    //short     naarEffect;
//    Share     naarEffect;
//    int       naarAantal;
//
//    // 'van' effect
//    if (tokens.hasMoreTokens()) {
//      //vanEffect = PbEffRekTransaction.effectStringToEffectID(tokens.nextToken());
//      vanEffect = Share.getShare(tokens.nextToken());
//    } else {
//      System.out.println("'van' effect ontbreekt.");
//      return null;
//    }
//
//    // 'van' aantal
//    if (tokens.hasMoreTokens()) {
//      Integer integerVanAantel = new Integer(tokens.nextToken());
//      vanAantal = integerVanAantel.intValue();
//    } else {
//      System.out.println("'van' effecten aantal ontbreekt.");
//      return null;
//    }
//
//    // 'naar' effect
//    if (tokens.hasMoreTokens()) {
//      //naarEffect = PbEffRekTransaction.effectStringToEffectID(tokens.nextToken());
//      naarEffect = Share.getShare(tokens.nextToken());
//    } else {
//      System.out.println("'naar' effect ontbreekt.");
//      return null;
//    }
//
//    // 'naar' aantal
//    if (tokens.hasMoreTokens()) {
//      Integer integerNaarAantel = new Integer(tokens.nextToken().trim());
//      naarAantal = integerNaarAantel.intValue();
//    } else {
//      System.out.println("'naar' aantal ontbreekt.");
//      return null;
//    }
//
//    return new PbEffRekRedenominatie(effectenRekening,
//                                     valutadatum,
//                                     transactieType,
//                                     vanEffect,
//                                     vanAantal,
//                                     naarEffect,
//                                     naarAantal);
//  }

//  private PbEffRekVerwStockdiv parseVerwStockdiv(PbEffRek effectenRekening, Date valutadatum, short transactieType, StringTokenizer tokens) {
//    /*
//    short   stockdividend;
//    int     aantalStockdividenden;
//    //short   verwisseldInAandeel;
//    Share   verwisseldInAandeel;
//    int     verwisseldInAantalAandelen;
//
//    // Stockdividend
//    if (tokens.hasMoreTokens()) {
//      stockdividend = PbEffRekTransaction.effectStringToEffectID(tokens.nextToken());
//    } else {
//      System.out.println("stockdividend ontbreekt.");
//      return null;
//    }
//
//    // aantal Stockdividenden
//    if (tokens.hasMoreTokens()) {
//      Integer integerAantalStockdividenden = new Integer(tokens.nextToken());
//      aantalStockdividenden = integerAantalStockdividenden.intValue();
//    } else {
//      System.out.println("stockdividenden aantal ontbreekt.");
//      return null;
//    }
//
//    // verwisseld in aandeel
//    if (tokens.hasMoreTokens()) {
//      //verwisseldInAandeel = PbEffRekTransaction.effectStringToEffectID(tokens.nextToken());
//      verwisseldInAandeel = Share.getShare(tokens.nextToken());
//    } else {
//      System.out.println("verwisseld in aandeel ontbreekt.");
//      return null;
//    }
//
//    // verwisseld in aantal
//    if (tokens.hasMoreTokens()) {
//      Integer aantalAandelen = new Integer(tokens.nextToken().trim());
//      verwisseldInAantalAandelen = aantalAandelen.intValue();
//    } else {
//      System.out.println("verwisseld in aantal aandelen ontbreekt.");
//      return null;
//    }
//
//    return new PbEffRekVerwStockdiv(effectenRekening,
//                                    valutadatum,
//                                    transactieType,
//                                    stockdividend,
//                                    aantalStockdividenden,
//                                    verwisseldInAandeel,
//                                    verwisseldInAantalAandelen);
//     */
//    return null;
//  }

//  private PbEffRekFractieVerrekeningNieuweWaarden parseFractieVerrekeningNieuweWaarden(PbEffRek effectenRekening, Date valutadatum, short transactieType, StringTokenizer tokens) {
//    PgCurrencyFormat  cf = new PgCurrencyFormat();
//    SimpleDateFormat  df =  new SimpleDateFormat("dd-MM-yyyy");
//    boolean     aankoop;
//    short       effectType;
//    //short       effect;
//    Share       effect;
//    int         aantalEffecten;
//    PgCurrency  koers;
//    Date        transactieDatum;
//
//    // aankoop/verkoop (weet niet of aankoop wel bestaat)
//    if (tokens.hasMoreTokens()) {
//      String aankoopVerkoopString = tokens.nextToken();
//      if (aankoopVerkoopString.equalsIgnoreCase(PbEffRekTransaction.aankoopString)) {
//        aankoop = true;
//      } else if (aankoopVerkoopString.equalsIgnoreCase(PbEffRekTransaction.verkoopString)) {
//        aankoop = false;
//      } else {
//        System.out.println("onbekende waarde in aankoop/verkoop veld: " + aankoopVerkoopString);
//        return null;
//      }
//    } else {
//      System.out.println("aankoop/verkoop veld ontbreekt.");
//      return null;
//    }
//
//    // effect type (niet bekend of dit iets anders dan 'aandelen' kan zijn)
//    if (tokens.hasMoreTokens()) {
//      effectType = PbEffRekTransaction.effectTypeStringToEffectTypeID(tokens.nextToken());
//    } else {
//      System.out.println("effect type ontbreekt.");
//      return null;
//    }
//
//    // effect
//    if (tokens.hasMoreTokens()) {
//      //effect = PbEffRekTransaction.effectStringToEffectID(tokens.nextToken());
//      effect = Share.getShare(tokens.nextToken());
//    } else {
//      System.out.println("effect ontbreekt.");
//      return null;
//    }
//
//    // aantal
//    if (tokens.hasMoreTokens()) {
//      aantalEffecten = (int) PgUtilities.PgUtilitiesParseCommaValue(tokens.nextToken(), 100);
//    } else {
//      System.out.println("effecten aantal ontbreekt.");
//      return null;
//    }
//
//    // koers
//    if (tokens.hasMoreTokens()) {
//      koers = cf.parse(tokens.nextToken());
//    }
//    else {
//      System.out.println("koers veld ontbreekt");
//      return null;
//    }
//
//    // datum
//    if (tokens.hasMoreTokens()) {
//      try {
//        transactieDatum = df.parse(tokens.nextToken());
//      } catch (ParseException e) {
//        System.out.println("formaat fout in datum");
//        return null;
//      }
//    } else {
//      System.out.println("datum veld ontbreekt");
//      return null;
//    }
//
//    return new PbEffRekFractieVerrekeningNieuweWaarden(effectenRekening,
//                                                       valutadatum,
//                                                       transactieType,
//                                                       aankoop,
//                                                       effectType,
//                                                       effect,
//                                                       aantalEffecten,
//                                                       koers,
//                                                       transactieDatum);
//  }

//  private PbEffRekTransaction getVorigDeelVanTransactie(boolean aankoop, Share effect) {
//    boolean                     found = false;
//    PbEffRekAandelenTransactie  currentTransaction = null;
//
//    // zoek entry in de lijst
//    for (int index = 0; (index < _deelUitvoeringen.size()) && !found; index++) {
//      //System.out.println("effect = " + effect.getName() + ", index = " + index);
//      currentTransaction = (PbEffRekAandelenTransactie) _deelUitvoeringen.get(index);
//      if (/* currentTransaction.isAankoop() == aankoop  && */
//          //currentTransaction.getEffect() == effect) {
//          currentTransaction.getEffect().equals(effect)) {
//        found = true;
//        _deelUitvoeringen.remove(index);
//      }
//    }
//
//    return currentTransaction;
//  }

  /*
  private ShareDividendCombo effectStringToShareAndDividend(String name) {
    ShareDividendCombo  combo = new ShareDividendCombo();
    Share               effect;
    ShareDividend       dividend;
    boolean             found = false;
    ConversionEntry     entry = null;

    for (int i = 0; i < conversionTable.length && !found; i++) {
      entry = conversionTable[i];
      if (entry._totalName.compareTo(name) == 0) {
        found = true;
      }
    }

    if (found) {
      effect = Share.getShare(entry._shareName);
      if (effect != null) {
        combo._effect = effect;
        dividend = effect.getDividend(entry._dividendName);
        if (dividend == null) {
          System.out.println("effectStringToShareAndDividend: Dividend name not found: " + entry._dividendName);
        } else {
          combo._dividend = dividend;
        }
      } else {
        System.out.println("effectStringToShareAndDividend: Share name not found: " + entry._shareName);
      }
    } else {
      System.out.println("effectStringToShareAndDividend: total name not found: " + name);
    }

    return combo;
  }
  */

  class ShareDividendCombo {
    Share             _effect = null;                 // effect
    ShareDividend     _dividend = null;               // dividend
  }

}
