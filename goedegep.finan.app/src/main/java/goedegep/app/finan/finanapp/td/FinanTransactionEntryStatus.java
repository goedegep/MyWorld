package goedegep.app.finan.finanapp.td;

import goedegep.app.finan.td.TransactionEntryStatus;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.stocks.CompanyPool;

public class FinanTransactionEntryStatus extends TransactionEntryStatus {
  private Bank                           bank                  = null;
  private PgAccount                      bankAccount           = null;
  private CompanyPool                    companyPool           = null;
  
  public Bank getBank() {
    return bank;
  }
  
  public void setBank(Bank bank) {
    this.bank = bank;
  }
  
  public PgAccount getBankAccount() {
    return bankAccount;
  }
  
  public boolean setBankAccount(PgAccount bankAccount) {
    if ((this.bankAccount == null)  &&  (bankAccount == null)) {
      return false;
    }
    
    if (((this.bankAccount == null)  &&  (bankAccount != null))  ||
        ((this.bankAccount != null)  &&  (bankAccount == null))  ||
        !this.bankAccount.equals(bankAccount)) {
      this.bankAccount = bankAccount;
      return true;
    } else {
      return false;
    }
  }
  
  public CompanyPool getCompanyPool() {
    return companyPool;
  }

  public void setCompanyPool(CompanyPool companyPool) {
    this.companyPool = companyPool;
  }
}
