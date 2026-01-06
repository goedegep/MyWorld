package goedegep.finan.effrek;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.stocks.DividendType;
import goedegep.finan.stocks.Drip;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.util.datetime.DateUtil;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public abstract class EffRekVerwStockDiv extends EffRekTransactie {
  private static final Logger LOGGER = Logger.getLogger(EffRekVerwStockDiv.class.getName());
  private static final String            VERWISSELING_STOCKDIVIDEND_STRING = "verwisseling stockdividend";

  private static final SimpleDateFormat  DF =  new SimpleDateFormat("dd-MM-yyyy");
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private static final PgCurrencyFormat  CF =  new PgCurrencyFormat();
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat(0);
  
  FixedPointValue             aantalStockdividenden = new FixedPointValue(1000);   // aantal effecten * 1000
  Share                       effect = null;                // verwisseld in aandeel
  ShareDividend               shareDividend = null;         // dividend
  int                         aantalAandelen = -1;          // aantal aandelen

  public EffRekVerwStockDiv(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_VERW_STOCK_DIVIDEND;
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

  public void setAantalStockDividenden(FixedPointValue aantalStockDividenden) {
    this.aantalStockdividenden = aantalStockDividenden;
  }

  public FixedPointValue getAantalStockDividenden() {
    return aantalStockdividenden;
  }

  public void setVerwisseldInAandeel(Share aandeel) {
    this.effect = aandeel;
  }

  public Share getVerwisseldInAandeel() {
    return effect;
  }

  public void setVerwisseldInAantal(int aantalAandelen) {
    this.aantalAandelen = aantalAandelen;
  }

  public int getVerwisseldInAantal() {
    return aantalAandelen;
  }

  public String getDescription() {
    if (shareDividend.getType() == DividendType.DRIP) {
      return getDripDescription();
    } else {
      return getStockDividendDescription();
    }
  }
  
  private String getDripDescription() {
    StringBuilder      output = new StringBuilder();
    
    output.append("af  ");
    PgCurrency brutoBedrag = getBrutoBedrag();
    output.append(CF.format(brutoBedrag) + " ");
    
    output.append("keuzediv. zonder contante verw. dd  ");
    output.append(DF.format(DateUtil.localDateToDate(getBookingDate())));
    output.append(", ");
    FixedPointValue verwisseldeDividenden = shareDividend.getDrip().getFromAmount().multiply(aantalAandelen);
    output.append(FPVF.format(verwisseldeDividenden) + " stdiv ");
    output.append(getEffect().getName() + " " +
        getShareDividend().getReferenceString());
    output.append(" zijn verwisseld in " + getVerwisseldInAantal());
    output.append(" aand " + getVerwisseldInAandeel().getName());
    output.append(" voor elke ");
    Drip drip = shareDividend.getDrip();
    output.append(FPVF.format(drip.getFromAmount()));
    output.append(" stuks en  ");
    output.append(CF.format(drip.getPricePerShare()));
    output.append(" ontvangt u 1 stuk, brutobedrag af ");
    output.append(CF.format(brutoBedrag) + " ");
    
    return output.toString();
  }
  
  private String getStockDividendDescription() {
    StringBuilder output = new StringBuilder();

    output.append("verwisseling stockdividend ");
//    String aantalStockdividendenString = FPVF.format(aantalStockdividenden);
    output.append(FPVF.format(aantalStockdividenden));
    output.append(" stdiv ");
    output.append(getEffect().getName() + " " +
        getShareDividend().getReferenceString());
    output.append(" zijn dd " + DTF.format(getBookingDate()));
    output.append(" verwisseld in " + getVerwisseldInAantal());
    output.append(" aand " + getVerwisseldInAandeel().getName());

    return output.toString();
  }

  public String toString() {
    Integer verwisseldInAantal = Integer.valueOf(aantalAandelen);

    return VERWISSELING_STOCKDIVIDEND_STRING +               // transaction type
           "\t" + ((effect == null) ? "<Effect ontbreekt>" : effect.getName()) +
           "\t" + ((shareDividend == null) ? "<DividendReferentie ontbreekt>" : shareDividend.getReferenceString()) +
           "\t" + FPVF.format(aantalStockdividenden) +
           "\t" + verwisseldInAantal.toString();

  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek        effectenRekening = (EffRek) getAccount();

    // converteer het saldo bij introductie van de euro
    if (transactionInEuros(getBookingDate())  &&  (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }
    
    // Depending on the type: handle either the real stock dividend or the Drip.
    if (shareDividend.getType() == DividendType.DRIP) {
      handleDrip(errors);
    } else {
      handleStockDividend(errors);
    }

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
  
  private void handleStockDividend(List<TransactionError> errors) {
    EffRek        effectenRekening = (EffRek) getAccount();

    // verwijder de stock dividenden uit het verzameldepot
    effectenRekening.getVerzamelDepot().stockDividendenVerwijderen(this.getBookingDate(), shareDividend, aantalStockdividenden, null);

    // voeg de aandelen toe aan het verzameldepot
    int fromAmount = shareDividend.getStockDividend().getFromAmount();
    int toAmount = shareDividend.getStockDividend().getToAmount();
    PgCurrency rate = shareDividend.getStockDividend().getKoers().multiply(fromAmount).divide(toAmount);
    effectenRekening.getVerzamelDepot().effectenToevoegen(getBookingDate(), effect, aantalAandelen, rate, new PgCurrency(rate.getCurrency(), 0));
  }
  
  private void handleDrip(List<TransactionError> errors) {
//    LOGGER.severe("=> " + toString());
    EffRek        effectenRekening = (EffRek) getAccount();

    /*
     * Check whether the number of exchanged stock dividends in the transaction are in line with the rate mentioned in the DRIP.
     * If not, report an error.
     */
    FixedPointValue exchangedStockDividendsBasedOnDripRate = shareDividend.getDrip().getFromAmount().multiply(aantalAandelen);
    if (!exchangedStockDividendsBasedOnDripRate.equals(aantalStockdividenden)) {
      TransactionError transactionError = new TransactionError(this,
          "Aantal verwisselde stock dividenden volgens de transactie (" +
          FPVF.format(aantalStockdividenden) +
          ") komt niet overeen met het aantal berekend op basis van de omwisselverhouding in de DRIP (" +
          FPVF.format(exchangedStockDividendsBasedOnDripRate) +
          ")."
          );
      errors.add(transactionError);
      LOGGER.severe("Fout in verwisseld aantal: " + transactionError.getErrorMessage());
      return;
    }
    
    /*
     * verwijder de stock dividenden uit het verzameldepot
     * De Postbank gebruikt kennelijk fractie-afronding bij de Drip afhandeling.
     * Controleer of het aantal dividenden in het depot, inclusief afronding klopt.
     * Zo ja, verwijder wat er in het depot zit, zo niet geen actie en rapporteer een fout.
     */
    FixedPointValue dividendenInDepot = effectenRekening.getVerzamelDepot().getStockDividendAmount(shareDividend);
//    LOGGER.severe(shareDividend.getName() + "  dividendenInDepot: " + FPVF.format(dividendenInDepot));
//    LOGGER.severe(shareDividend.getName() + "  this.aantalStockdividenden: " + FPVF.format(aantalStockdividenden));
//    LOGGER.severe(shareDividend.getName() + "  fromAmount: " + FPVF.format(shareDividend.getDrip().getFromAmount()));
    FixedPointValue roundingValue = shareDividend.getDrip().getFromAmount().divide(2l);
//    LOGGER.severe(shareDividend.getName() + "  roundingValue: " + FPVF.format(roundingValue));
//    LOGGER.severe(shareDividend.getName() + "  this.verwisseldeDividenden: " + FPVF.format(aantalStockdividenden));
    FixedPointValue differenceBetweenDividendsInDepotAndExchanged = dividendenInDepot.subtract(aantalStockdividenden).abs();
    if (differenceBetweenDividendsInDepotAndExchanged.isGreaterThan(roundingValue)) {
      LOGGER.severe("Aantal dividenden klopt niet voor " + shareDividend.getName() +
          ", datum is " + DF.format(getBookingDate()) +
          ". Aantal dividenden in depot is " + FPVF.format(dividendenInDepot) + ", aantal dividenden verwisseld is " + FPVF.format(aantalStockdividenden));
      TransactionError transactionError = new TransactionError(this,
          "Na afronding, is het aantal dividenden in het depot (" + FPVF.format(dividendenInDepot) +
          ") nog steeds niet gelijk aan het aantal verwisselde dividenden (" + FPVF.format(aantalStockdividenden) + ").");
      errors.add(transactionError);
      return;
    }
    effectenRekening.getVerzamelDepot().stockDividendenVerwijderen(this.getBookingDate(), shareDividend, dividendenInDepot, null);

    // voeg de aandelen toe aan het verzameldepot
    Drip drip = shareDividend.getDrip();
    PgCurrency brutoBedrag = getBrutoBedrag();
    effectenRekening.getVerzamelDepot().effectenToevoegen(getBookingDate(), effect, aantalAandelen, drip.getPricePerShare(), brutoBedrag);

    // Verminder het saldo met het brutobedrag.
    effectenRekening.decreaseBalance(brutoBedrag);
  }
  
  private PgCurrency getBrutoBedrag() {
    Drip drip = shareDividend.getDrip();
    return drip.getPricePerShare().multiply(aantalAandelen).certifyFactor(100);
  }
}
