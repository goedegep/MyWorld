package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.stocks.DividendType;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public abstract class EffRekStockDividend extends EffRekTransactie {
  private static final PgCurrencyFormat  CF =  new PgCurrencyFormat();
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat(true);

  private Share            effect = null;  // TODO kan weg, kan afgeleid worden van shareDividend (zie Dividend)
  private ShareDividend    shareDividend = null;
  private FixedPointValue  aantalEffecten = null;  // Let op, hier niet maal 1000.

  public EffRekStockDividend(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_STOCK_DIVIDEND;
  }

  public void setEffect(Share effect) {
    this.effect = effect;
  }

  public Share getEffect() {
    return effect;
  }

  public void setShareDividend(ShareDividend shareDividend) {
    this.shareDividend = shareDividend;
  }

  public ShareDividend getShareDividend() {
    return shareDividend;
  }

  public void setAantalEffecten(FixedPointValue aantalEffecten) {
    this.aantalEffecten = aantalEffecten;
  }

  public FixedPointValue getAantalEffecten() {
    return aantalEffecten;
  }

  public String getDescription() {
    if (shareDividend.getType() == DividendType.DRIP) {
      return getDripDescription();
    } else {
      return getStockDividendDescription();
    }
  }
  
  private String getStockDividendDescription() {
    StringBuilder      output = new StringBuilder();

    output.append("StockDividend  ");
    Share share = getEffect();
    if (share == null) {
      output.append("<geen fonds>");
    } else {
      output.append(share.getName() + " ");
    }
    output.append(",  ");
    if (shareDividend == null) {
      output.append("<geen dividend> ");
    } else {
      output.append(shareDividend.getReferenceString() + " ");
    }
    output.append(FPVF.format(getAantalEffecten()));
    output.append(" stuks");
    if (shareDividend != null) {
      output.append(", Koers  ");
      PgCurrency koers = shareDividend.getStockDividend().getKoers();
      if (koers != null) {
        output.append(CF.format(koers));
      }
      else {
        output.append("--");
      }
    }

    return output.toString();
  }

  private String getDripDescription() {
    StringBuilder      output = new StringBuilder();

    output.append("Drip  ");
    Share share = getEffect();
    if (share == null) {
      output.append("<geen fonds>");
    } else {
      output.append(share.getName() + " ");
    }
    output.append(",  ");
    if (shareDividend == null) {
      output.append("<geen dividend> ");
    } else {
      output.append(shareDividend.getReferenceString() + " ");
    }
    output.append(FPVF.format(getAantalEffecten()));
    output.append(" stuks");

    return output.toString();
  }

  public String toString() {
    return (effect == null ? "<geen fonds>" : effect.getName()) +
           "\t" + (shareDividend == null ? "<geen dividend>" : shareDividend.getReferenceString()) +
           "\t" + String.valueOf(aantalEffecten);
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek  effectenRekening = (EffRek) this.getAccount();

    // converteer het saldo bij introductie van de euro
    if (getBookingDate() != null &&
        transactionInEuros(getBookingDate())  &&
        (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }

    effectenRekening.getVerzamelDepot().stockDividendenToevoegen(getBookingDate(), getShareDividend(), getAantalEffecten(), null);
    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
}
