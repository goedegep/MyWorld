package goedegep.finan.effrek;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.stocks.DividendOntvangst;
import goedegep.finan.stocks.ShareDividend;
import goedegep.finan.stocks.StockDepot;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public abstract class EffRekDividend extends EffRekTransactie {
  private static final String            DIVIDEND_STRING = "dividend";
  private static final String            AANDEEL_STRING  = "aand";

  private static final int               BEDRAG_SIZE = 10;
  private static final LocalDate         NEW_RATE_DATE_2007 = LocalDate.of(2007, Month.JANUARY, 1);
  
  private static final PgCurrencyFormat  CF =  new PgCurrencyFormat();
  private static final PgCurrencyFormat  CF2 = new PgCurrencyFormat(BEDRAG_SIZE);

  private ShareDividend     shareDividend = null;  // het dividend.
  private int               aantalEffecten = -1;   // Het aantal effecten waarover dividend ontvangen is.
  private boolean           zonderKosten = false;  // Geeft aan of er kosten (provisie) in rekening gebracht zijn. (default wel kosten).
  public EffRekDividend(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_DIVIDEND;
  }
  
  public void setShareDividend(ShareDividend shareDividend) {
    this.shareDividend = shareDividend;
  }


  public ShareDividend getShareDividend() {
    return shareDividend;
  }

  public void setAantalEffecten(int aantalEffecten) {
    this.aantalEffecten = aantalEffecten;
  }

  public int getAantalEffecten() {
    return aantalEffecten;
  }

  public boolean isZonderKosten() {
    return zonderKosten;
  }

  public void setZonderKosten(boolean zonderKosten) {
    this.zonderKosten = zonderKosten;
  }
  
  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    PgCurrency dividend = getBrutoBedrag();
    PgCurrency dividendbelasting = dividendBelasting();
    PgCurrency total = dividend;
    if (dividendbelasting != null) {
      total = dividend.subtract(dividendbelasting);
    }
    PgCurrency provisie = dividendOfCouponProvisie(dividend);
    total = total.subtract(provisie);

    output.append("bij " + CF2.format(total));
    output.append(" dividend aand " + getShareDividend().getShare().getName());
    output.append(" " + getAantalEffecten());
    if (getAantalEffecten() == 1) {
      output.append(" stuk a ");
    } else {
      output.append(" stuks a ");
    }
    output.append(CF.format(getShareDividend().getAmount()));
    output.append(" bij ").append(CF.format(dividend));
    if (dividendbelasting != null) {
      output.append(" dividendbelasting ").append(getDividendTaxPercentage());
      output.append(".00% af " + CF.format(dividendbelasting));
    }
    output.append(" provisie af " + CF.format(provisie));

    return output.toString();
  }

  public String toString() {
    return DIVIDEND_STRING +
           "\t" + AANDEEL_STRING +
           "\t" + (shareDividend == null ? "<geen fondsnaam>" : shareDividend.getShare().getName()) +
           "\t" + (shareDividend == null ? "<geen dividend>" : shareDividend.getReferenceString()) +
           "\t" + aantalEffecten;
  }
  
  public boolean isValid() {
    if ((shareDividend == null)  ||
        (aantalEffecten == -1)) {
      return false;
    } else {
      return true;
    }      
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek       effectenRekening = (EffRek) this.getAccount();
    StockDepot     stockDepot = effectenRekening.getVerzamelDepot();

    // converteer het saldo bij introductie van de euro
    if (getBookingDate() != null &&
        transactionInEuros(getBookingDate())  &&
        (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }

    PgCurrency     brutoDividend = getBrutoBedrag(); 

    // provisie
    brutoDividend = brutoDividend.subtract(dividendOfCouponProvisie(brutoDividend));

    // dividendbelasting
    PgCurrency dividendbelasting = dividendBelasting();
    if (dividendbelasting != null) {
      brutoDividend = brutoDividend.subtract(dividendbelasting);
    }

    // verhoog het saldo met het totaalbedrag
    effectenRekening.increaseBalance(brutoDividend);

    setNieuwTegoed(effectenRekening.getBalance());
    
    DividendOntvangst dividendOntvangst = new DividendOntvangst();
    dividendOntvangst.setOntvangstDatum(getExecutionDate());
    dividendOntvangst.setEffect(getShareDividend().getShare());
    dividendOntvangst.setBrutoBedrag(brutoDividend);
    dividendOntvangst.setDividendBelasting(dividendbelasting);
    dividendOntvangst.setProvisie(dividendOfCouponProvisie(brutoDividend));
    stockDepot.addDividendToTaxReport(dividendOntvangst);
    
    setHandled(true);
  }
  
  /**
   * Bereken het bruto dividend: aantal dividenden maal het dividend bedrag.
   * Als het dividend in guldens is, terwijl er al in euro's gerekend wordt,
   * wordt het totaalbedrag in euro's gegeven.
   * 
   * @return Het bruto dividend bedrag.
   */
  public PgCurrency getBrutoBedrag() {
    // totaal dividend
    PgCurrency brutoDividend = getShareDividend().getAmount().multiply(getAantalEffecten());

    // als het dividend in guldens is, terwijl er al in euro's gerekend wordt,
    // zet het totaalBedrag dan om in euro's
    if (transactionInEuros(getBookingDate())  &&  (getShareDividend().getAmount().getCurrency() != PgCurrency.EURO)) {
      brutoDividend = brutoDividend.changeCurrency(PgCurrency.EURO);
    }
    
    return brutoDividend.certifyFactor(100);
  }
  
  public int getDividendTaxPercentage() {
    if (shareDividend.getTaxPercentage() != null) {
      return shareDividend.getTaxPercentage();
    } else {
      if (getExecutionDate().isBefore(NEW_RATE_DATE_2007)) {
        return 25;   // Default 25 percent dividend tax.
      } else {
        return 15;
      }
    }
  }

  /**
   * Bereken de in te houden dividendbelasting.
   * 
   * @param dividend Het dividend waarover de belasting berekend moet worden.
   * @return De te betalen dividend belasting.
   */
  public PgCurrency dividendBelasting() {
    PgCurrency brutoDividendAmount = this.getBrutoBedrag();
    int taxPercentage = getDividendTaxPercentage();
    if (taxPercentage == 0) {
      return null;
    } else {
      return new PgCurrency(brutoDividendAmount.getCurrency(), (taxPercentage * brutoDividendAmount.getAmount() + 50) / 100);
    }
  }
  

  /**
   * Bereken de te betalen kosten (provisie).
   * 
   * @param dividend Het dividendbedrag.
   * @return De te betalen kosten (provisie).
   */
  public PgCurrency dividendOfCouponProvisie(PgCurrency dividend) {
    long        bedrag = dividend.getAmount();
    PgCurrency  provisie = null;

    if (dividend.getCurrency() == PgCurrency.EURO) {
      provisie = dividendOfCouponProvisieInEuros(bedrag);
    } else if (dividend.getCurrency() == PgCurrency.GUILDER) {
      provisie = dividendOfCouponProvisieInGuldens(bedrag);
    }

    return provisie;
  }

  /**
   * Bereken de te betalen kosten (provisie) voor bedragen in euro's.
   * @param bedrag Het bedrag in euro centen.
   * @return De te betalen kosten (provisie) in euro's.
   */
  public PgCurrency dividendOfCouponProvisieInEuros(long bedrag) {
    final long eersteSchijf           = 225000;  // eerste schijf
    final long percentageEersteSchijf = 750;     // 0,750% over eerste schijf
    final long percentageRestant      = 375;     // 0,375% over het meerdere

    long provisie;

    if (zonderKosten) {
      provisie = 0L;
    } else {
      if (bedrag > eersteSchijf) {
        provisie = (percentageEersteSchijf * eersteSchijf +
            percentageRestant * (bedrag - eersteSchijf) + 50000) / 100000;
      } else {
        provisie = (percentageEersteSchijf * bedrag + 50000) / 100000;
      }
    }

    return new PgCurrency(PgCurrency.EURO, provisie);
  }

  /**
   * Bereken de te betalen kosten (provisie) voor bedragen in guldens.
   * @param bedrag Het bedrag in gulden centen.
   * @return De te betalen kosten (provisie) in guldens.
   */
  public PgCurrency dividendOfCouponProvisieInGuldens(long bedrag) {
    final long eersteSchijf           = 500000;  // eerste schijf
    final long percentageEersteSchijf = 750;     // 0,750% over eerste schijf
    final long percentageRestant      = 375;     // 0,375% over het meerdere

    long provisie;

    if (zonderKosten) {
      provisie = 0L;
    } else {
      if (bedrag > eersteSchijf) {
        provisie = (percentageEersteSchijf * eersteSchijf +
                   percentageRestant * (bedrag - eersteSchijf) + 50000) / 100000;
      } else {
        provisie = (percentageEersteSchijf * bedrag + 50000) / 100000;
      }
    }

    return new PgCurrency(PgCurrency.GUILDER, provisie);
  }
}
