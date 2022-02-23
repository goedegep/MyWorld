package goedegep.finan.postbank.pbsprek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

// TODO: Misschien moeten valutadatum en executiedatum omgedraaid worden!!!

public class PbSpRekOverschrijving extends PbSpRekTransaction {
  public static final String VAN_GIRO_STRING      = "bij";
  public static final String NAAR_GIRO_STRING     = "af";
  public static final String ZONDER_KOSTEN_STRING = "zonder kosten";
  
  private static final PgCurrencyFormat  CF =  new PgCurrencyFormat(0, false, false, true);
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  // TODO i.p.v. isBijschrijving, Overschrijving splitsen in Af en Bijschrijving (zie Direktspaarrekening)
  private boolean isBijschrijving= false;  // if true bijschrijving, else afschrijving.
  private boolean zonderKosten = false;     // geen opname kosten (bij overboeken naar 'hogere' rekening).

  public PbSpRekOverschrijving(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_OVERSCHRIJVING;
  }

  public boolean isBijschrijving() {
    return isBijschrijving;
  }

  public void setBijschrijving(boolean isBijschrijving) {
    this.isBijschrijving = isBijschrijving;
  }

  public void setZonderKosten(boolean zonderKosten) {
    this.zonderKosten = zonderKosten;
  }

  public boolean isZonderKosten() {
    return zonderKosten;
  }

  public String getDescription() {
    PbSpRek       account = (PbSpRek) getAccount();
    StringBuilder output = new StringBuilder();
    
    if (isBijschrijving) {
      output.append(VAN_GIRO_STRING);
    } else {
      output.append(NAAR_GIRO_STRING);
    }
    
    if (zonderKosten) {
      output.append(" ").append(ZONDER_KOSTEN_STRING);
    }
    output.append(" ");
    output.append(CF.format(getTransactionAmount()));       // bedrag

    if (!isBijschrijving) {
      if (account.hasOpnameKosten()  &&  !zonderKosten) {
        PgCurrency kosten = account.getOpnameKosten(this);
        if (!kosten.isZero()) {
          output.append(" kosten " + CF.format(account.getOpnameKosten(this)));
        }
      }
    }
    
    output.append(", huidig rentepercentage ");
    output.append(FPVF.format(this.rentePercentage));   // rentepercentage
    output.append(" %");
    
    return output.toString();
  }

  public String toString() {
    StringBuilder output = new StringBuilder();
    
    if (isBijschrijving) {
      output.append(VAN_GIRO_STRING);
    } else {
      output.append(NAAR_GIRO_STRING);
    }
    
    if (zonderKosten) {
      output.append(" ").append(ZONDER_KOSTEN_STRING);
    }
    
    output.append(" ").append(CF.format(getTransactionAmount()));
    output.append(" ").append(FPVF.format(this.rentePercentage));

    return output.toString();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    handleFirst();

    PbSpRek        account = (PbSpRek) getAccount();

    if (isBijschrijving) {
      // verhoog saldo en investering met het ingelegde bedrag.
      account.increaseBalance(getTransactionAmount());
      account.increaseNettoStorting(getTransactionAmount());
    } else {
      // af (naar giro)
      // verlaag saldo en investering met het opgenomen bedrag.
      account.decreaseBalance(getTransactionAmount());
      account.decreaseNettoStorting(getTransactionAmount());
      if (account.hasOpnameKosten()  &&  !zonderKosten) {
        account.decreaseBalance(account.getOpnameKosten(this));
      }
    }

    handleLast();
  }

//  static final PgCurrency vrijPerMaand = new PgCurrency(PgCurrency.GUILDER, 2500000L);
//  static final PgCurrency vrijPerMaandJul96 = new PgCurrency(PgCurrency.GUILDER, 5000000L);
//  static final double     transactionCosts = 0.01;
//
// TODO: uitzoeken voor welke spaarrekening dit van toepassing is.
//  public PgCurrency opnameKosten() {
//    long        bedrag;
//    long        kosten;
//
//    GregorianCalendar calendar = new GregorianCalendar(1999, Calendar.JULY, 1);
//    Date        july96 = calendar.getTime();
//    long        vrij;
//
//
//    bedrag = getTransactionAmount().getAmount();
//    if (this.getBookingDate().before(july96)) {
//      vrij = vrijPerMaand.getAmount();
//    } else {
//      vrij = vrijPerMaandJul96.getAmount();
//    }
//
//    if (bedrag > vrij) {
//      bedrag -= vrij;
//      kosten = (long) ((double) bedrag * transactionCosts + 0.5);
//    } else {
//      kosten = 0L;
//    }
//
//    return new PgCurrency(getTransactionAmount().getCurrency(), kosten);
//  }
}
