package goedegep.finan.effrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.util.datetime.DateUtil;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.text.SimpleDateFormat;
import java.util.List;

public abstract class EffRekStockDivTransactie extends EffRekTransactie {
  public static final short   VOLLEDIG                =  1; // transactie in een keer volledig uitgevoerd
  public static final short   DEELUITVOERING          =  2; // transactie in delen uitgevoerd, dit is niet eerste en niet laatste deel
  public static final short   DEELUITVOERING_EERSTE   =  3; // transactie in delen uitgevoerd, eerste deel
  public static final short   DEELUITVOERING_LAATSTE  =  4; // transactie in delen uitgevoerd, laatste deel

  public static final String  AANKOOP_STRING               = "aank";
  public static final String  VERKOOP_STRING               = "verk";
  
  public static final String  DEELUITVOERING_STRING         = "(deeluitvoering)";
  public static final String  DEELUITVOERING_EERSTE_STRING   = "(deeluitvoering eerste)";
  public static final String  DEELUITVOERING_LAATSTE_STRING  = "(deeluitvoering laatste)";
  
  private static final String STOCK_DIVIDEND_STRING        = "stdiv";

  private static final int    BEDRAG_SIZE = 10;

  private static final PgCurrencyFormat      CF =  new PgCurrencyFormat();
  private static final PgCurrencyFormat      CF2 = new PgCurrencyFormat(BEDRAG_SIZE);
  private static final SimpleDateFormat      DF =  new SimpleDateFormat("dd-MM-yyyy");
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat(true);

  private boolean                     aankoop = false;               // true voor aankoop, anders: verkoop
  private Share                       effect = null;                 // effect
  private ShareDividend               shareDividend = null;          // dividend
  private short                       uitvoeringsType = VOLLEDIG;    // volledig of deeluitvoering
  private EffRekStockDivTransactie    vorigDeelVanTransactie = null; // referentie naar vorig deel v.e. deeluitvoering;
  private FixedPointValue             aantalEffecten = null;           // aantal effecten, let op hier niet maal 1000.
  private PgCurrency                  koers = null;                  // aan/verkoop koers
  private PgCurrency                  provisie = null;               // Kosten, wordt alleen ingevuld als de berekening niet te verklaren is.

  public EffRekStockDivTransactie(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_STOCK_DIV_TRANSACTIE;
  }

  public void setAankoop(boolean aankoop) {
    this.aankoop = aankoop;
  }

  public boolean isAankoop() {
    return aankoop;
  }

  public void setEffect(Share effect) {
    this.effect = effect;
  }

  public Share getEffect() {
    return effect;
  }

  public ShareDividend getShareDividend() {
    return shareDividend;
  }

  public void setShareDividend(ShareDividend shareDividend) {
    this.shareDividend = shareDividend;
  }

  public void setAantalEffecten(FixedPointValue aantalEffecten) {
    this.aantalEffecten = aantalEffecten;
  }

  public FixedPointValue getAantalEffecten() {
    return aantalEffecten;
  }

  public void setUitvoeringsType(short uitvoeringsType) {
    this.uitvoeringsType = uitvoeringsType;
  }

  public short getUitvoeringsType() {
    return uitvoeringsType;
  }

  public void setKoers(PgCurrency koers) {
    this.koers = koers;
  }

  public PgCurrency getKoers() {
    return this.koers;
  }

  public PgCurrency getProvisie() {
    return provisie;
  }

  public void setProvisie(PgCurrency provisie) {
    this.provisie = provisie;
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    PgCurrency effectenPrijs = getShareDividend().getStockDividend().getKoers().multiply(getAantalEffecten()).changeFactor(100);
    PgCurrency totaalBedrag = effectenPrijs;

    // transactie kosten: alleen als aankoop ongedeeld is, of laatste deel van deeluitvoering en geen emissie
    short uitvoeringsType = getUitvoeringsType();
    PgCurrency orderKosten = null;
    if (uitvoeringsType == EffRekStockDivTransactie.VOLLEDIG  || uitvoeringsType == EffRekStockDivTransactie.DEELUITVOERING_LAATSTE) {
      if (provisie != null) {
        orderKosten = provisie;
      } else {
        orderKosten = EffRekStockDivTransactie.orderKosten(totaleOrderWaarde());
      }
    }

    if (isAankoop()) {
      output.append("af  ");
      if (orderKosten != null) {
        totaalBedrag = totaalBedrag.add(orderKosten);
      }
    } else {
      output.append("bij ");
      if (orderKosten != null) {
        totaalBedrag = totaalBedrag.subtract(orderKosten);
      }
    }
    output.append(CF2.format(totaalBedrag) + " ");

    if (isAankoop()) {
      output.append("aankoop ");
    } else {
      output.append("verkoop ");
    }

    output.append("stdiv: " + getEffect().getName() +
        " " + getShareDividend().getReferenceString()+ ",  ");

    output.append(FPVF.format(getAantalEffecten()));
    if (getAantalEffecten().equals(new FixedPointValue(getAantalEffecten().getFactor(), getAantalEffecten().getFactor()))) {
      output.append(" stuk, ");
    } else {
      output.append(" stuks, ");
    }

    output.append("koers: " + CF.format(getKoers()) + ", ");

    if (isAankoop()) {
      output.append("af:  ");
    } else {
      output.append("bij: ");
    }

    output.append(CF.format(effectenPrijs) + " ");

    if (orderKosten != null) {
      output.append(" provisie af: " + CF.format(orderKosten));
    }

    return output.toString();
  }

  public String toString() {
    StringBuilder     buf = new StringBuilder();

    // valutadatum
    buf.append(DF.format(DateUtil.localDateToDate(getBookingDate())));

    // transaction type
    if (aankoop) {
      buf.append("\t").append(AANKOOP_STRING);
    } else {
      buf.append("\t").append(VERKOOP_STRING);
    }

    switch (uitvoeringsType) {
    case VOLLEDIG:
      // geen extra toevoeging
      break;

    case DEELUITVOERING:
      buf.append(" ").append(DEELUITVOERING_STRING);
      break;

    case DEELUITVOERING_EERSTE:
      buf.append(" ").append(DEELUITVOERING_EERSTE_STRING);
      break;

    case DEELUITVOERING_LAATSTE:
      buf.append(" ").append(DEELUITVOERING_LAATSTE_STRING);
      break;

    default:
      // kan niet voorkomen
      break;
    }

    buf.append("\t").append(STOCK_DIVIDEND_STRING).append("\t");
    if (effect != null) {
      buf.append(effect.getName());
    } else {
      buf.append("<Effect ontbreekt>");
    }
    buf.append("\t");
    if (shareDividend != null) {
      buf.append(shareDividend.getReferenceString());
    } else {
      buf.append("<DividendReferentie ontbreekt>");
    }
    buf.append("\t").append(FPVF.format(aantalEffecten));
    buf.append("\t").append(CF.format(koers));
    if (provisie != null) {
      buf.append("\t").append(CF.format(provisie));
    }
    
    return buf.toString();
  }
  
  public boolean isValid() {
    if ((effect == null)  ||
        (shareDividend == null)  ||
        (aantalEffecten == null)) {
      return false;
    } else {
      return true;
    }  
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek  effectenRekening = (EffRek) this.getAccount();
    PgCurrency                effectenPrijs;  // totale prijs van de effecten
    PgCurrency                totaalBedrag;   // totaalbedrag inclusief kosten

    // converteer het saldo bij introductie van de euro
    if (transactionInEuros(getBookingDate())  &&  (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }

    if (aankoop) {
      // prijs van de stock dividenden
      effectenPrijs = shareDividend.getStockDividend().getKoers().multiply(getAantalEffecten()).changeFactor(100);

      // transactie kosten: alleen als aankoop ongedeeld is, of laatste deel van deeluitvoering
      if (uitvoeringsType == VOLLEDIG  || uitvoeringsType == DEELUITVOERING_LAATSTE) {
        if (provisie != null) {
          totaalBedrag = effectenPrijs.add(provisie);
        } else {
          totaalBedrag = effectenPrijs.add(orderKosten(this.totaleOrderWaarde()));
        }
      } else {
        totaalBedrag = effectenPrijs;
      }
      // verminder het saldo met het totaalbedrag
      effectenRekening.decreaseBalance(totaalBedrag);

      // voeg de stock dividenden toe aan het verzameldepot
      effectenRekening.getVerzamelDepot().stockDividendenToevoegen(getBookingDate(), shareDividend, getAantalEffecten(), totaalBedrag);
    } else {
      // verkoop
      // prijs van de stock dividenden
      effectenPrijs = shareDividend.getStockDividend().getKoers().multiply(getAantalEffecten()).changeFactor(100);

      // transactie kosten: alleen als verkoop ongedeeld is, of laatste deel van deeluitvoering
      if (uitvoeringsType == VOLLEDIG  || uitvoeringsType == DEELUITVOERING_LAATSTE) {
        if (provisie != null) {
          totaalBedrag = effectenPrijs.subtract(provisie);
        } else {
          totaalBedrag = effectenPrijs.subtract(orderKosten(this.totaleOrderWaarde()));
        }
      } else {
        totaalBedrag = effectenPrijs;
      }

      // verhoog het saldo met het totaalbedrag
      effectenRekening.increaseBalance(totaalBedrag);

      // verwijder de stock dividenden uit het verzameldepot
      effectenRekening.getVerzamelDepot().stockDividendenVerwijderen(getBookingDate(), shareDividend, getAantalEffecten(), totaalBedrag);
    }

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }

  public PgCurrency totaleOrderWaarde() {
    EffRekStockDivTransactie  transaction;

    PgCurrency totaleWaarde = shareDividend.getStockDividend().getKoers().multiply(this.getAantalEffecten());

    if (this.uitvoeringsType != VOLLEDIG) {
      for (transaction = this.vorigDeelVanTransactie;
           transaction != null;
           transaction = transaction.vorigDeelVanTransactie) {
        totaleWaarde = totaleWaarde.add(transaction.getShareDividend().getStockDividend().getKoers().multiply(transaction.getAantalEffecten()));
      }
    }

    return totaleWaarde;
  }

  public static PgCurrency orderKosten(PgCurrency orderBedrag) {
    long        bedrag = orderBedrag.getAmount();
    int         factor = orderBedrag.getFactor();
    PgCurrency  kosten = null;

    if (orderBedrag.getCurrency() == PgCurrency.EURO) {
      kosten = orderKostenInEuros(bedrag, factor);
    } else if (orderBedrag.getCurrency() == PgCurrency.GUILDER) {
      kosten = orderKostenInGuldens(bedrag, factor);
    }

    return kosten;
  }

  public static PgCurrency orderKostenInEuros(long bedrag, int factor) {
    long           multiplier = factor / 100;
    bedrag = (bedrag + multiplier / 2) / multiplier;
    final long     vasteKosten = 325L;            // 3,25 Euro vast kosten
    final long     eersteSchijf = 675000L;        // eerste schijf 6.750 euro.
    final long     percentageEersteSchijfBeursorderlijn = 60L; // 0,6% over eerste schijf
    final long     percentageRestant = 35L;                    // 0,35% over het meerdere

    long kosten = vasteKosten;
    long percentageEersteSchijf;
    long maxKosten;               // is helft van orderbedrag

    percentageEersteSchijf = percentageEersteSchijfBeursorderlijn; // DIT MOET VIA PARAMETER

    if (bedrag > eersteSchijf) {
      kosten += (percentageEersteSchijf * eersteSchijf +
                 percentageRestant * (bedrag - eersteSchijf) + 5000) / 10000;
    } else {
      kosten += (percentageEersteSchijf * bedrag + 5000) / 10000;
    }

    if (factor > 100) {   // kennelijk is bij het verhogen van het aantal decimalen, ook de afronding veranderd.
      maxKosten = bedrag/2;
    } else {
      maxKosten = (bedrag + 1)/2;

    }
    if (kosten > maxKosten) {
      kosten = maxKosten;
    }

    return new PgCurrency(PgCurrency.EURO, kosten, 100);
  }

  public static PgCurrency orderKostenInGuldens(long bedrag, int factor) {
    long           multiplier = factor / 100;
    final long     vasteKosten = multiplier * 750L;          // 7,50 Gulden vaste kosten
    final long     eersteSchijf = multiplier * 1500000L;      // eerste schijf
    final long     percentageEersteSchijf = 60L; // 0,6% over eerste schijf
    final long     percentageRestant = 35L;      // 0,35% over het meerdere

    long kosten = vasteKosten;
    long maxKosten;               // is helft van orderbedrag

    if (bedrag > eersteSchijf) {
      kosten += (percentageEersteSchijf * eersteSchijf +
                 percentageRestant * (bedrag - eersteSchijf) + 5000) / 10000;
    } else {
      kosten += (percentageEersteSchijf * bedrag + 5000) / 10000;
    }

    maxKosten = (bedrag + 1)/2;
    if (kosten > maxKosten) {
      kosten = maxKosten;
    }

    return new PgCurrency(PgCurrency.GUILDER, kosten, factor).changeFactor(100);
  }

  public static PgCurrency dividendBelasting(PgCurrency dividend) {
    return new PgCurrency(dividend.getCurrency(), (25 * dividend.getAmount() + 50) / 100);
  }
}
