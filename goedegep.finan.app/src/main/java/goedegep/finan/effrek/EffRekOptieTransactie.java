package goedegep.finan.effrek;

import java.util.List;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionError;
import goedegep.finan.stocks.OptieTransactieType;
import goedegep.finan.stocks.OptionSerie;
import goedegep.finan.stocks.OptionType;
import goedegep.finan.stocks.Share;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public abstract class EffRekOptieTransactie extends EffRekTransactie {
  public static final int                  EXPIRATION_MONTH_INVALID = -1;
  public static final int                  EXPIRATION_YEAR_INVALID = -1;
  public static final int                  SHARES_PER_CONTRACT = 100;
  private static final String[]            MONTH_NAMES = {"JAN", "FEB", "MAR", "APR", "MEI", "JUN",
                                                          "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

  private static final int                 BEDRAG_SIZE = 10;
  
  private static final PgCurrencyFormat    CF =  new PgCurrencyFormat();
  private static final PgCurrencyFormat    CF2 = new PgCurrencyFormat(BEDRAG_SIZE);

  private OptieTransactieType  optieTransactieType = null;
  private OptionType           optionType = null;
  private Share                share = null;                  // effect
  private int                  expirationMonth = EXPIRATION_MONTH_INVALID;
  private int                  expirationYear = EXPIRATION_YEAR_INVALID;
  private PgCurrency           uitoefeningsKoers = null;
  private int                  aantalContracten = -1;
  private PgCurrency           koers = null;                  // aan/verkoop koers
  private PgCurrency           provisie = null;               // aan/verkoop kosten

  public EffRekOptieTransactie(PgAccount  account) {
    super(account);
  }
  
  public short getTransactionType() {
    return TT_OPTIE_TRANSACTIE;
  }

  public OptieTransactieType getOptieTransactieType() {
    return optieTransactieType;
  }

  public void setOptieTransactieType(OptieTransactieType optieTransactieType) {
    this.optieTransactieType = optieTransactieType;
  }

  public OptionType getOptionType() {
    return optionType;
  }

  public void setOptionType(OptionType optionType) {
    this.optionType = optionType;
  }

  public Share getShare() {
    return share;
  }

  public void setShare(Share share) {
    this.share = share;
  }

  public int getExpirationMonth() {
    return expirationMonth;
  }

  public void setExpirationMonth(int expirationMonth) {
    this.expirationMonth = expirationMonth;
  }

  public int getExpirationYear() {
    return expirationYear;
  }

  public void setExpirationYear(int expirationYear) {
    this.expirationYear = expirationYear;
  }

  public PgCurrency getUitoefeningsKoers() {
    return uitoefeningsKoers;
  }

  public void setUitoefeningsKoers(PgCurrency uitoefeningsKoers) {
    this.uitoefeningsKoers = uitoefeningsKoers;
  }

  public int getAantalContracten() {
    return aantalContracten;
  }

  public void setAantalContracten(int aantalContracten) {
    this.aantalContracten = aantalContracten;
  }

  public PgCurrency getKoers() {
    return koers;
  }

  public void setKoers(PgCurrency koers) {
    this.koers = koers;
  }

  public PgCurrency getProvisie() {
    return provisie;
  }

  public void setProvisie(PgCurrency provisie) {
    this.provisie = provisie;
  }
  
  public OptionSerie getOptionSerie() {
    return OptionSerie.getOptionSerie(optionType, share, expirationMonth, expirationYear, uitoefeningsKoers);
  }

  public String getDescription() {
    StringBuilder      output = new StringBuilder();

    PgCurrency optiePrijs = koers.multiply(SHARES_PER_CONTRACT * aantalContracten);
    PgCurrency totaalBedrag = null;

    switch(optieTransactieType) {
    case OPENINGSKOOP:
    case SLUITINGSKOOP:
      output.append("af  ");
      totaalBedrag = optiePrijs.add(provisie);
      break;
      
    case OPENINGSVERKOOP:
    case SLUITINGSVERKOOP:
      output.append("bij ");
      totaalBedrag = optiePrijs.subtract(provisie);
      break;  
    }
    output.append(CF2.format(totaalBedrag));

    output.append(" ").append(optieTransactieType.getDescription());
    output.append(" opties: ");
    output.append(optionType.getText());
    output.append(" ").append(share.getName());
    output.append(" ").append(MONTH_NAMES[expirationMonth - 1]);
    output.append(" ").append(expirationYear);
    output.append(" ").append(CF.format(uitoefeningsKoers));
    output.append(", ").append(aantalContracten);
    if (aantalContracten == 1) {
      output.append(" contract, ");
    } else {
      output.append(" contracten, ");
    }

    output.append("koers: ").append(CF.format(koers)).append(", ");
    switch(optieTransactieType) {
    case OPENINGSKOOP:
    case SLUITINGSKOOP:
      output.append("af  ");
      break;
      
    case OPENINGSVERKOOP:
    case SLUITINGSVERKOOP:
      output.append("bij ");
      break;  
    }
    output.append(CF.format(optiePrijs));

    output.append(", provisie af: ").append(CF.format(provisie));

    return output.toString();
  }

  public String toString() {
    return getDescription();
  }
  
  public boolean isValid() {
    if ((optieTransactieType != null)  &&
        (optionType != null)  &&
        (share != null)  &&
        (expirationMonth != EXPIRATION_MONTH_INVALID)  &&
        (expirationYear != EXPIRATION_YEAR_INVALID)  &&
        (uitoefeningsKoers !=  null)  &&
        (koers != null)  &&
        (provisie != null)  &&
        (getOptionSerie() != null)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void handle(List<TransactionError> errors) {
    EffRek        effectenRekening = (EffRek) getAccount();
    PgCurrency      totaalBedrag;   // totaalbedrag inclusief kosten

    // converteer het saldo bij introductie van de euro
    if (transactionInEuros(getBookingDate())  &&  (effectenRekening.getBalance().getCurrency() != PgCurrency.EURO)) {
      effectenRekening.certifyCurrency(PgCurrency.EURO);
      effectenRekening.getVerzamelDepot().convertToEuros();
    }

    // prijs van de aandelen
    PgCurrency optiePrijs = koers.multiply(SHARES_PER_CONTRACT * aantalContracten);

    OptionSerie optionSerie = getOptionSerie();
    switch(optieTransactieType) {
    case OPENINGSKOOP:
    case SLUITINGSKOOP:
      // totaal is prijs van de opties plus provisie.
      totaalBedrag = optiePrijs.add(provisie);
      
      // verminder het saldo met het totaalbedrag
      effectenRekening.decreaseBalance(totaalBedrag);

      // voeg de opties toe aan het verzameldepot
      effectenRekening.getVerzamelDepot().optiesToevoegen(getBookingDate(), optionSerie, aantalContracten, koers, totaalBedrag);
      break;
      
    case OPENINGSVERKOOP:
    case SLUITINGSVERKOOP:
      totaalBedrag = optiePrijs.subtract(provisie);

      // verhoog het saldo met het totaalbedrag
      effectenRekening.increaseBalance(totaalBedrag);

      // verwijder de opties uit het verzameldepot
      boolean allowNegativePosition = false;
      if (optieTransactieType == OptieTransactieType.OPENINGSVERKOOP) {
        allowNegativePosition = true;
      }
      effectenRekening.getVerzamelDepot().optiesVerwijderen(getBookingDate(), optionSerie, aantalContracten, koers, totaalBedrag, allowNegativePosition);
      break;  
    }

    setNieuwTegoed(effectenRekening.getBalance());
    setHandled(true);
  }
}
