package goedegep.finan.effrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.postbank.pbeffrek.PbEffRek;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.TerugBetaling;
import goedegep.util.datetime.DateUtil;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.text.SimpleDateFormat;
import java.util.List;

public abstract class EffRekTerugBetaling extends EffRekTransactie {
  private static final PgCurrencyFormat  CF =  new PgCurrencyFormat();
  private static final SimpleDateFormat  DF =  new SimpleDateFormat("dd-MM-yyyy");

  private Share         effect = null;
  private TerugBetaling terugBetaling = null; // Only available if handled.
  
  public EffRekTerugBetaling(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_TERUGBETALING;
  }

  public Share getEffect() {
    return effect;
  }

  public void setEffect(Share effect) {
    this.effect = effect;
  }

  public String getDescription() {
    StringBuilder output = new StringBuilder();
    PgCurrency    bedrag = null;
    
    if (isHandled()) {
      bedrag = terugBetaling.getBetalingPerAandeel().multiply(terugBetaling.getAantalRechten());
      output.append("bij ");
      output.append(CF.format(bedrag));
      output.append(" ");   
    }
    output.append("Gedeeltelijke terugbetaling dd  ");
    output.append(DF.format(DateUtil.localDateToDate(getBookingDate())));
    output.append(",  ");
    if (isHandled()) {
      output.append(terugBetaling.getAantalRechten() + " ");    
    }
    output.append("aand  ");
    output.append(effect.getName());
    if (isHandled()) {
    output.append(",  voor elk stuk ontvangt u ");
    output.append(CF.format(terugBetaling.getBetalingPerAandeel()));
      output.append(", brutobedrag ");    
      output.append(CF.format(bedrag));
    }

    return output.toString();    
  }

  public String toString() {
    return getDescription();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    PbEffRek  effectenRekening = (PbEffRek) this.getAccount();

    // converteer het saldo bij introductie van de euro
    if (getBookingDate() != null &&
        transactionInEuros(getBookingDate())  &&
        (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }

    terugBetaling = effectenRekening.getVerzamelDepot().terugBetalingsRechtenVerwijderen(effect);
    
    PgCurrency brutoBedrag = terugBetaling.getBetalingPerAandeel().multiply(terugBetaling.getAantalRechten());
    effectenRekening.increaseBalance(brutoBedrag);
    
    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
}
