package goedegep.finan.effrek;

import java.util.List;
import java.util.logging.Logger;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.stocks.Redenomination;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.StockDepot;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public abstract class EffRekRedenominatie extends EffRekTransactie {
  private final static Logger LOGGER = Logger.getLogger(StockDepot.class.getName());
  
  private static final String           REDENOMINATIE_STRING = "redenominatie";
  
  private static final PgCurrencyFormat CF =  new PgCurrencyFormat();

  private Share   naarEffect;          // 'naar' effect
  private Double  fraction;
  
  public EffRekRedenominatie(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_REDENOMINATIE;
  }

  public void setNaarEffect(Share naarEffect) {
    this.naarEffect = naarEffect;
  }

  public Share getNaarEffect() {
    return naarEffect;
  }

  public String getDescription() {
    StringBuilder   output = new StringBuilder();
    PgCurrency      rate = null;
    PgCurrency      brutoBedrag = null;
    
    if (fraction != null) {
      rate = naarEffect.getRedenominationFrom().getKoers();
      brutoBedrag = rate.multiply(fraction);
      
      if (fraction > 0.0) {
        output.append("bij ");
      } else {
        output.append("af");
      }
      output.append(CF.format(brutoBedrag)).append(" ");
    }

    output.append("Redenominatie  ");

    Redenomination redenomination = naarEffect.getRedenominationFrom();
    if (redenomination.getFromAmount() == 1) {
      output.append("ieder aand ");
    } else {
      output.append("iedere " + redenomination.getFromAmount() + " aand ");
    }

    output.append(redenomination.getShare().getName());

    if (redenomination.getFromAmount() == 1) {
      output.append(" is ");
    } else {
      output.append(" zijn ");
    }

    output.append("verw. in " + redenomination.getToAmount());

    if (redenomination.getToAmount() == 1) {
      output.append(" aand ");
    } else {
      output.append(" aand ");
    }

    output.append(getNaarEffect().getName());
    
    if (fraction != null) {
      output.append(", fractie ");
      double roundedFraction = fraction * 1000;
      roundedFraction = (double) Math.round(roundedFraction);
      roundedFraction /= 1000;
      if (fraction > 0.0) {
        output.append("verkocht");
      } else {
        output.append("gekocht");
      }
      output.append(": ").append(roundedFraction).append(" aand");
      output.append(", voor elk stuk ontvangt u ");
      output.append(CF.format(rate));
      output.append(", bruto bedrag ");
      if (fraction > 0.0) {
        output.append("bij ");
      } else {
        output.append("af ");
      }
      output.append(CF.format(brutoBedrag));
    }

    return output.toString();
  }

  public String toString() {
    Redenomination redenomination = naarEffect.getRedenominationFrom();
        return REDENOMINATIE_STRING +                   // transaction type
           " " + redenomination.getShare().getName() +
           " " + redenomination.getFromAmount() +
           " " + naarEffect.getName() +
           " " + redenomination.getToAmount();
  }

  @Override
  public void handle(List<TransactionError> errors) {
    LOGGER.info("=>");
    
    EffRek        effectenRekening = (EffRek) this.getAccount();
    
//    if (getBookingDate() != null  &&  getBookingDate().getDate() == 21  &&  getBookingDate().getMonth() == 8  &&  getBookingDate().getYear() == (2009 - 1900)) {
//      LOGGER.severe(getDescription());
//    } else {
//      LOGGER.severe(getDescription() + "  " + getBookingDate().getDate() + "  " +  getBookingDate().getMonth() + "  " + getBookingDate().getYear());
//    }

    // converteer het saldo bij introductie van de euro
    if (transactionInEuros(getBookingDate())  &&  (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }
    
    // pas de waarden in het verzameldepot aan
    fraction = effectenRekening.getVerzamelDepot().redenomineer(this.getBookingDate(), naarEffect);
    if (fraction != null) {
//      if (fraction > 0) {
        // aankoop/verkoop fractie
        PgCurrency rate = naarEffect.getRedenominationFrom().getKoers();
        PgCurrency brutoBedrag = rate.multiply(fraction);
        effectenRekening.increaseBalance(brutoBedrag);
//      }
    }

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
    
    LOGGER.info("<=");
  }
}
