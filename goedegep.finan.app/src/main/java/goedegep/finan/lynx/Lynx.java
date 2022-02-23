package goedegep.finan.lynx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import goedegep.finan.basic.Bank;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.lynx.lynxeffrek.LynxEffRek;
import goedegep.finan.stocks.StockDepot;
import goedegep.util.text.TextWriter;

public class Lynx extends Bank {
  private static String     BANK_NAME = "Lynx";

  private StockDepot    parentDepot;
  private LynxEffRek    lynxEffRek;

  public Lynx(StockDepot parentDepot) {
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

  /**
   * Verkrijg een lijst met 'alle' open rekeningen. Voor deze bank is dit
   * alleen de Lynx effectenrekening.
   */
  public List<PgAccount> getAccounts() {
    List<PgAccount> accountList = new ArrayList<PgAccount>();
    
    if (lynxEffRek != null) {
      accountList.add(lynxEffRek);
    }
    
    return accountList;
  }

  @Override
  public PgAccount getAccount(String accountName, boolean createIfNotExisting) {
    if (accountName.equals(LynxBasic.EFFECTEN_REKENING_STRING)) {
      return getEffectenrekening(createIfNotExisting);
    }
    
    return null;
  }


  public PgAccount openAccount(String accountName) {
    if (accountName.equals(LynxBasic.EFFECTEN_REKENING_STRING)) {
      return openEffectenrekening();
    }
    
    return null;
  }  


  public LynxEffRek openEffectenrekening() {
    if (lynxEffRek != null) {
      throw new IllegalArgumentException("Rekening bestaat al");      
    }

    lynxEffRek = new LynxEffRek(parentDepot);

    return lynxEffRek;
  }

  public LynxEffRek getEffectenrekening(boolean createIfNotExisting) {
    LynxEffRek lynxEffRek = this.lynxEffRek;
    if (lynxEffRek == null  &&  createIfNotExisting) {
      lynxEffRek = openEffectenrekening();
    }

    return lynxEffRek;
  }
  
  public LynxEffRek getEffectenrekening() {
    return lynxEffRek;
  }

  @Override
  public void dumpData(TextWriter textWriter) throws IOException {
    textWriter.write("LYNX DATA DUMP");
    textWriter.newLine();
    for (PgAccount account: getAccounts()) {
      account.dumpData(textWriter);
    }
  }
}