package goedegep.finan.direktbank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import goedegep.finan.basic.Bank;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.direktbank.direktsprek.DirektSpRek;
import goedegep.util.text.TextWriter;

public class Direktbank extends Bank {
  private static String     BANK_NAME = "Direktbank";

  private DirektSpRek    direktSpRek;

  public Direktbank() {
    super();

    setName(BANK_NAME);
  }

  public String toString() {
    return getName();
  }

  public String toString(PgTransaction transaction) {
    return transaction.getAccount().getName() + "\t" +
    transaction.toString();
  }

//  public String toXmlString(PgTransaction transaction, String nameSpace) {
//    String  rekeningString = transaction.getAccount().getName();
//
//    return PgXml.createElement(nameSpace, "Rekening", rekeningString) + " " +
//    transaction.toXmlString(nameSpace);
//  }


  /**
   * Verkrijg een lijst met 'alle' open rekeningen. Voor deze bank is dit
   * alleen de direktspaarrekening.
   */
  public List<PgAccount> getAccounts() {
    List<PgAccount> accountList = new ArrayList<PgAccount>();
    
    if (direktSpRek != null) {
      accountList.add(direktSpRek);
    }
    
    return accountList;
  }


  public PgAccount openAccount(String accountName) {
    if (accountName.equals("Direktspaarrekening")) {
      return openDirektspaarrekening();
    }
    
    return null;
  }  


  public DirektSpRek openDirektspaarrekening() {
    if (direktSpRek != null) {
      throw new IllegalArgumentException("Rekening bestaat al");      
    }

    direktSpRek = new DirektSpRek();

    return direktSpRek;
  }

  public DirektSpRek getDirektspaarrekening(boolean createIfNotExisting) {
    DirektSpRek direktSpRek = this.direktSpRek;
    if (direktSpRek == null  &&  createIfNotExisting) {
      direktSpRek = openDirektspaarrekening();
    }

    return direktSpRek;
  }
  
  public DirektSpRek getDirektspaarrekening() {
    return direktSpRek;
  }


  @Override
  public void dumpData(TextWriter out) throws IOException {
    out.write("DIREKTBANK DATA DUMP");
    out.newLine();
    for (PgAccount account: getAccounts()) {
      account.dumpData(out);
    }
  }
}
