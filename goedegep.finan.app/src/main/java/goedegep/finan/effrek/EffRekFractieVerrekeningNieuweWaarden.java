package goedegep.finan.effrek;

import java.text.SimpleDateFormat;
import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.stocks.Share;
import goedegep.util.datetime.DateUtil;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public abstract class EffRekFractieVerrekeningNieuweWaarden extends EffRekTransactie {
  public static final String                AANKOOP_STRING = "aank";
  public static final String                VERKOOP_STRING = "verk";
  private static final String               FRACTIEVERREKENING_NIEUWE_WAARDEN = "fractie verrekening nieuwe waarden";
  
  private static final int                  BEDRAG_SIZE = 10;

  private static final PgCurrencyFormat     CF =  new PgCurrencyFormat();
  private static final PgCurrencyFormat     CF2 = new PgCurrencyFormat(BEDRAG_SIZE);
  private static final SimpleDateFormat     DF =  new SimpleDateFormat("dd-MM-yyyy");
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();

  private boolean         aankoop = false;       // true voor aankoop, anders: verkoop
  private Share           effect = null;         // effect
  private FixedPointValue aantalEffecten = null; // aantal effecten in 2 decimalen
  private PgCurrency      koers = null;          // aan/verkoop koers


  public EffRekFractieVerrekeningNieuweWaarden(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_FRACTIEVERR_NWE_WAARDEN;
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

  public void setAantalEffecten(FixedPointValue aantalEffecten) {
    this.aantalEffecten = aantalEffecten;
  }

  public FixedPointValue getAantalEffecten() {
    return aantalEffecten;
  }

  public void setKoers(PgCurrency koers) {
    this.koers = koers;
  }

  public PgCurrency getKoers() {
    return koers;
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder(256);

    PgCurrency effectenPrijs = getKoers().multiply(getAantalEffecten());

    if (isAankoop()) {
      output.append("af  ");
    } else {
      output.append("bij ");
    }

    output.append(CF2.format(effectenPrijs) + " ");

    output.append(FRACTIEVERREKENING_NIEUWE_WAARDEN);

    if (isAankoop()) {
      output.append(": aankoop ");
    } else {
      output.append(": verkocht restant ");
    }
    output.append(FPVF.format(getAantalEffecten()));

    output.append(" aandelen " + getEffect().getName());
    output.append(", bedrag per stuk " + CF.format(getKoers()));
    output.append(", dd " + DF.format(DateUtil.localDateToDate(getExecutionDate())));

    if (isAankoop()) {
      output.append(", af:  ");
    } else {
      output.append(", bij: ");
    }
    output.append(CF.format(effectenPrijs));

    return output.toString();
  }

  public String toString() {
    StringBuffer      output = new StringBuffer();

    // transaction type
    output.append("\t" + FRACTIEVERREKENING_NIEUWE_WAARDEN);

    // aankoop/verkoop
    if (isAankoop()) {
      output.append("\t" + AANKOOP_STRING);
    } else {
      output.append("\t" + VERKOOP_STRING);
    }

    output.append("\taand");

    output.append("\t" + effect.getName() +
                  "\t" + FPVF.format(getAantalEffecten()) +
                  "\t" + CF.format(koers));

    return output.toString();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek        effectenRekening = (EffRek) getAccount();
    PgCurrency    effectenPrijs;  // totale prijs van de effecten

    // converteer het saldo bij introductie van de euro
    if (transactionInEuros(getBookingDate())  &&  (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }
    
    // prijs van de aandelen
    effectenPrijs = getKoers().multiply(getAantalEffecten());

    if (aankoop) {
      // verminder het saldo met het totaalbedrag
      effectenRekening.decreaseBalance(effectenPrijs);
    } else {
      // verkoop
      // verwijder fractie uit het depot.
      effectenRekening.getVerzamelDepot().removeStockFraction(effect, aantalEffecten);
      // verhoog het saldo met het totaalbedrag
      effectenRekening.increaseBalance(effectenPrijs);
    }

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
}
