package goedegep.finan.effrek;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.stocks.Share;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public abstract class EffRekAandelenTransactie extends EffRekTransactie {
  private static final Logger LOGGER = Logger.getLogger(EffRekAandelenTransactie.class.getName());
  
  // Uitvoerings typen.
  public static final short   UT_VOLLEDIG                =  1; // transactie in een keer volledig uitgevoerd
  public static final short   UT_DEELUITVOERING          =  2; // transactie in delen uitgevoerd, dit is niet eerste en niet laatste deel
  public static final short   UT_DEELUITVOERING_EERSTE   =  3; // transactie in delen uitgevoerd, eerste deel
  public static final short   UT_DEELUITVOERING_LAATSTE  =  4; // transactie in delen uitgevoerd, laatste deel

  public static final String  DEELUITVOERING_STRING         = "(deeluitvoering)";
  public static final String  DEELUITVOERING_EERSTE_STRING  = "(deeluitvoering eerste)";
  public static final String  DEELUITVOERING_LAATSTE_STRING = "(deeluitvoering laatste)";
  
  protected static final String AANKOOP_STRING               = "aank";
  protected static final String VERKOOP_STRING               = "verk";
  protected static final String AANDEEL_STRING               = "aand";

  private static final int                  BEDRAG_SIZE = 10;
  
  protected static final PgCurrencyFormat     CF =  new PgCurrencyFormat();
  protected static final PgCurrencyFormat     CF2 = new PgCurrencyFormat(BEDRAG_SIZE);
  private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");


  private boolean                    aankoop = false;               // true voor aankoop, anders: verkoop
  private Share                      effect = null;                 // effect
  private short                      uitvoeringsType = UT_VOLLEDIG; // volledig of deeluitvoering
  private EffRekAandelenTransactie   vorigDeelVanTransactie = null; // referentie naar vorig deel v.e. deeluitvoering;
  private int                        aantalEffecten = -1;           // aantal effecten
  private PgCurrency                 koers = null;                  // aan/verkoop koers
  private boolean                    zonderKosten = false;          // transactie zonder aankoop- of verkoopkosten.
  private PgCurrency                 orderKosten = null;            // aan/verkoop kosten
  
  public EffRekAandelenTransactie(PgAccount  account) {
    super(account);
  }

  public short getTransactionType() {
    return TT_AANDELEN_TRANSACTIE;
  }

  public void setAankoop(boolean aankoop) {
    this.aankoop = aankoop;
  }

  public boolean isAankoop() {
    return aankoop;
  }

  public void setZonderKosten() {
    zonderKosten = true;
  }

  public boolean isZonderKosten() {
    if (getOrderKosten() != null  &&  getOrderKosten().isZero()) {
      return true;
    } else {
      return false;
    }
    
//    return zonderKosten;
  }

  public void setEffect(Share effect) {
    this.effect = effect;
  }

  public Share getEffect() {
    return effect;
  }

  public void setUitvoeringsType(short uitVoeringsType) {
    this.uitvoeringsType = uitVoeringsType;
  }

  public short getUitvoeringsType() {
    return uitvoeringsType;
  }

  public EffRekAandelenTransactie getVorigDeelVanTransactie() {
    return vorigDeelVanTransactie;
  }

  public void setVorigDeelVanTransactie(EffRekAandelenTransactie vorigDeelVanTransactie) {
    this.vorigDeelVanTransactie = vorigDeelVanTransactie;
  }

  public void setAantalEffecten(int aantalEffecten) {
    this.aantalEffecten = aantalEffecten;
  }

  public int getAantalEffecten() {
    return aantalEffecten;
  }

  public void setKoers(PgCurrency koers) {
    this.koers = koers;
  }

  public PgCurrency getKoers() {
    return koers;
  }
  
  public int getCurrency() {
    return koers.getCurrency();
  }

  public PgCurrency getOrderKosten() {
    if (zonderKosten) {
      return new PgCurrency(koers.getCurrency(), 0L);
    } else {
      return orderKosten;
    }
  }

  public void setOrderKosten(PgCurrency orderKosten) {
    this.orderKosten = orderKosten;
  }

  public PgCurrency totaleOrderWaarde() {
    EffRekAandelenTransactie  transaction;

    PgCurrency totaleWaarde = this.getKoers().multiply(this.getAantalEffecten()).certifyFactor(100);

    if (this.uitvoeringsType != UT_VOLLEDIG) {
      for (transaction = vorigDeelVanTransactie;
           transaction != null;
           transaction = transaction.vorigDeelVanTransactie) {
        totaleWaarde = totaleWaarde.add(transaction.getKoers().multiply(transaction.getAantalEffecten()).certifyFactor(100));
      }
    }

    return totaleWaarde;
  }
  
  public boolean isSameTransaction(PgTransaction transaction) {
    if (!(transaction instanceof EffRekAandelenTransactie)) {
      return false;
    }
    if ((getExecutionDate() != null)  &&  (transaction.getExecutionDate() == null)) {
      LOGGER.fine("No executionDate for 'this', but there's one for 'transaction'.");
      return false;
    }
    if ((getExecutionDate() == null)  &&  (transaction.getExecutionDate() != null)) {
      LOGGER.fine("No executionDate for 'transaction', but there's one for 'this'.");
      return false;
    }
    if ((getExecutionDate() != null)  &&  (transaction.getExecutionDate() != null)) {
      if (!getExecutionDate().equals(transaction.getExecutionDate())) {
        LOGGER.fine("ExecutionDates differ: this:" + getExecutionDate().format(DF) + ", transaction:" + transaction.getExecutionDate().format(DF));
        return false;
      }
    }
    
    EffRekAandelenTransactie effRekAandelenTransactie = (EffRekAandelenTransactie) transaction;
    if (isAankoop()!= effRekAandelenTransactie.isAankoop()) {
      return false;
    }
    
    if (getAantalEffecten() != effRekAandelenTransactie.getAantalEffecten()) {
      return false;
    }
    
    // The effect should be filled in.
    if ((getEffect() == null)  ||  (effRekAandelenTransactie.getEffect() == null)  ||  !getEffect().equals(effRekAandelenTransactie.getEffect())) {
      return false;
    }
    // The koers should be filled in.
    if ((getKoers() == null)  ||  (effRekAandelenTransactie.getKoers() == null)  ||  !getKoers().equals(effRekAandelenTransactie.getKoers())) {
      return false;
    }
    
    if (getTransactionType() != effRekAandelenTransactie.getTransactionType()) {
      return false;
    }
    
    return true;
  }
}
