package goedegep.finan.abnamrobank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import goedegep.app.finan.abnamrobank.AbnAmroBankResources;
import goedegep.appgen.swing.AppResources;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRek;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.DefaultAccount;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.stocks.StockDepot;
import goedegep.util.text.TextWriter;

public class AbnAmroBank extends Bank {
  private static String     BANK_NAME = "ABN AMRO";

  private StockDepot  parentDepot;
  private AAEffRek    aaEffRek;
  private PgAccount   aaDekkingsRekening;
  private static AppResources appResources = null;

  public AbnAmroBank(StockDepot parentDepot) {
    super();

    setName(BANK_NAME);
    this.parentDepot = parentDepot;
  }

  public String toString() {
    return getName();
  }

  public String toString(PgTransaction transaction) {
    return transaction.getAccount().getName() + "\t" +
    transaction.toString();
  }
  
  public static AppResources getAppResources() {
    if (appResources == null) {
      appResources = new AbnAmroBankResources();
    }
    
    return appResources;
  }

  /**
   * Verkrijg een lijst met 'alle' open rekeningen. Voor deze bank is dit
   * voorlopig alleen de effectenrekening.
   */
  public List<PgAccount> getAccounts() {
    List<PgAccount> accountList = new ArrayList<PgAccount>();
    
    if (aaEffRek != null) {
      accountList.add(aaEffRek);
      accountList.add(aaDekkingsRekening);
    }
    
    return accountList;
  }

  @Override
  public PgAccount getAccount(String accountName, boolean createIfNotExisting) {
    if (accountName.equals(AbnAmroBankBasic.EFFECTEN_REKENING_STRING)) {
      return getEffectenrekening(createIfNotExisting);
    }
    
    return null;
  }


  public PgAccount openAccount(String accountName) {
    if (accountName.equals(AbnAmroBankBasic.EFFECTEN_REKENING_STRING)) {
      return openEffectenrekening();
    }
    
    return null;
  }  


  public AAEffRek openEffectenrekening() {
    if (aaEffRek != null) {
      throw new IllegalArgumentException("Rekening bestaat al");      
    }

    aaDekkingsRekening = new DefaultAccount(AbnAmroBankBasic.DEKKINGS_REKENING_STRING);
    aaEffRek = new AAEffRek(parentDepot, aaDekkingsRekening);

    return aaEffRek;
  }

  public AAEffRek getEffectenrekening(boolean createIfNotExisting) {
    AAEffRek aaEffRek = this.aaEffRek;
    if (aaEffRek == null  &&  createIfNotExisting) {
      aaEffRek = openEffectenrekening();
    }

    return aaEffRek;
  }
  
  public AAEffRek getEffectenrekening() {
    return aaEffRek;
  }


  @Override
  public void dumpData(TextWriter textWriter) throws IOException {
    textWriter.write("ABN AMRO DATA DUMP");
    textWriter.newLine();
    for (PgAccount account: getAccounts()) {
      account.dumpData(textWriter);
    }
  }
}