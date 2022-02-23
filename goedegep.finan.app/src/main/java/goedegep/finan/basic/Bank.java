package goedegep.finan.basic;

import java.util.List;

import goedegep.util.money.PgCurrency;

/**
 * Bij een bank kan je een aantal rekeningen hebben, zoals een betaalrekening,
 * spaarrekeningen, een effectenrekening, enz. 
 *
 */
public abstract class Bank implements DataDump {
  /**
   * Naam van de bank.
   */
  private String        name;
  
  /**
   * Maakt een bank aan.

   */
  public Bank() {
  }
  
  /**
   * Geeft de bank een (nieuwe) naam.
   *
   * @param name    Naam van de bank.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Verkrijg de naam van de bank.
   *
   * @return De naam van de bank.
   */

  public String getName() {
    return name;
  }

  /**
   * Verkrijg alle rekeningen van de bank.
   * 
   * @return de complete lijst van rekeningen bij deze bank.
   */
  public abstract List<PgAccount> getAccounts();

  /**
   * Verkrijg de rekening met een bepaalde naam.
   * 
   * @return de rekening met de gespecificeerde naam.
   */
  public PgAccount getAccount(String accountName) {
    return getAccount(accountName, false);
  }

  /**
   * Verkrijg de rekening met een bepaalde naam.
   * 
   * @return de rekening met de gespecificeerde naam.
   */
  public PgAccount getAccount(String accountName, boolean createIfNotExisting) {
    for (PgAccount account: getAccounts()) {
      if (account.getName().equals(accountName)) {
        return account;
      }
    }
    
    if (createIfNotExisting) {
      return openAccount(accountName);
    } else {
      return null;
    }
  }
  
  public abstract PgAccount openAccount(String accountName);
  
  /**
   * Verkrijg het totale saldo van de rekeningen bij deze bank.
   * 
   * @return het totale saldo van de rekeningen bij deze bank.
   */
  public PgCurrency getBalance() {
    PgCurrency balance = null;
    
    for (PgAccount account: getAccounts()) {
      if (account.hasBalance()) {
        PgCurrency accountBalance = account.getBalance();
        if (accountBalance != null) {
          if (balance == null) {
            balance = accountBalance;
          } else {
            // reken in guldens als er verschillende munteenheden zijn.
            // Voor effecten is de euro eerder ingevoerd.
            if (balance.getCurrency() != accountBalance.getCurrency()) {
              balance = balance.certifyCurrency(PgCurrency.GUILDER);
              accountBalance = accountBalance.certifyCurrency(PgCurrency.GUILDER);
            }
            balance = balance.add(accountBalance);
          }
        }
      }
    }
    
    return balance;
  }
  
  /**
   * Verkrijg een schatting van de gezamelijke waarde van de rekeningen
   * bij deze bank.
   * 
   * @return de gezamelijke waarde  van de rekeningen bij deze bank.
   */
  public PgCurrency getEstimatedValue() {
    PgCurrency value = null;
    
    for (PgAccount account: getAccounts()) {
      if (account.hasValue()) {
        PgCurrency accountValue = account.getEstimatedValue();
        if (accountValue != null) {
          if (value == null) {
            value = accountValue;
          } else {
            // reken in guldens als er verschillende munteenheden zijn.
            // Voor effecten is de euro eerder ingevoerd.
            if (value.getCurrency() != accountValue.getCurrency()) {
              value = value.certifyCurrency(PgCurrency.GUILDER);
              accountValue = accountValue.certifyCurrency(PgCurrency.GUILDER);
            }
            value = value.add(accountValue);
          }
        }
      }
    }
    
    return value;
  }

  public void clear() {
    for (PgAccount account : getAccounts()) {
      account.clear();
    } 
  }
  
//  public abstract void dumpData(BufferedWriter out) throws IOException;
}
