package goedegep.app.finan.finanapp;

import goedegep.app.finan.gen.FinanBank;
import goedegep.appgen.DataWithIcon;
import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.SumAccount;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyPlusStatus;

@SuppressWarnings("serial")
public class BankModel extends AppGenAbstractTableModel {
  private static final String         NEWLINE = System.getProperty("line.separator");
  
  private static final int  BANK_COLUMN = 0;
  private static final int  SALDO_COLUMN = 1;
  private static final int  WAARDE_COLUMN = 2;
  private static final int  TOTAAL_COLUMN = 3;  
  
  private SumAccount      sumAccount = null;
  private Object[][]      data;
  
  private static final String[] columnNames = {
      "Bank",
      "Saldo",
      "Waarde",
      "Totaal"
  };

  private static final Object[] longValues = {
    "Postbank",
    new PgCurrency(PgCurrency.EURO, 10000000),
    new PgCurrency(PgCurrency.EURO, 10000000),
    new PgCurrency(PgCurrency.EURO, 10000000)};

  public BankModel(SumAccount sumAccount) {
    super(columnNames, longValues);
    
    this.sumAccount = sumAccount;
    updateAccountInfo(true);
  }
  
  public int getRowCount() {
    if (data != null) {
      return data.length;
    } else {
      return 0;
    }
  }

  public Object getValueAt(int row, int col) {
    return data[row][col];
  }
  
  /*
   * JTable uses this method to determine the default renderer/
   * editor for each cell.  If we didn't implement this method,
   * then the last column would contain text ("true"/"false"),
   * rather than a check box.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Class getColumnClass(int col) {
    switch (col) {
    case BANK_COLUMN:
      return DataWithIcon.class;

    case SALDO_COLUMN:
    case WAARDE_COLUMN:
    case TOTAAL_COLUMN:
      return PgCurrency.class;

    default:
      return null;
    }
  }
  
  /**
   * The account info has the following layout:
   * 
   * 'Bank name 1 (with icon)'    'Balance 1'      'Estimated value 1'     'rowTotal 1'
   * ...                          ...              ...                     ...
   * 'Bank name n (with icon)'    'Balance n'      'Estimated value n'     'rowTotal n'
   *                              'balanceTotal'   'valueTotal'            
   * 
   * The 'Balance' and 'Estimated value' per bank is always shown in the currency returned by the bank.
   * Totals are shown in the currencies returned by the banks if these are all the same, otherwise the totals
   * are given in Euro's.
   * 
   * @param ignoreDataErrors If true, no error RuntimeException is thrown when values are not available for a bank.
   */
  public void updateAccountInfo(boolean ignoreDataErrors) {
    short            index = 0;
    PgCurrency       value;
    PgCurrency       balanceTotal = null;  // Sum of the balance values of all banks
    PgCurrency       valueTotal = null;    // Sum of the estimated values of all banks
    RuntimeException firstException = null;
    
    // allocate one extra line for the total line.
    data = new Object[sumAccount.getNrOfBanks() + 1][getColumnCount()];

    for (FinanBank finanBank: sumAccount.getBanks()) {
      Bank bank = finanBank.getBank();
      data[index][BANK_COLUMN] = new DataWithIcon(bank.getName(), finanBank.getBankLogoIcon());

      PgCurrency balance = bank.getBalance();
      data[index][SALDO_COLUMN] = balance;

      try {
        value = bank.getEstimatedValue();
        data[index][WAARDE_COLUMN] = value;
      } catch (RuntimeException e) {
        System.out.println("ERROR geen waarde voor " + bank.getName());
        if (firstException == null) {
          firstException = new RuntimeException("Geen geschatte waarde voor " + bank.getName() + "." +
              NEWLINE + "Reden: " + e.getMessage());
        }
        data[index][WAARDE_COLUMN] = new PgCurrencyPlusStatus(null, PgCurrencyPlusStatus.Status.ERROR);
        value = null;
      }

      data[index][TOTAAL_COLUMN] = PgCurrency.addDifferentCurrencies(balance, value, PgCurrency.EURO);
      balanceTotal = PgCurrency.addDifferentCurrencies(balanceTotal, balance, PgCurrency.EURO);
      valueTotal = PgCurrency.addDifferentCurrencies(valueTotal, value, PgCurrency.EURO);

      index++;
    }
    
    data[index][BANK_COLUMN] = new DataWithIcon("Totaal", null);
    data[index][SALDO_COLUMN] = balanceTotal;
    data[index][WAARDE_COLUMN] = valueTotal;
    data[index][TOTAAL_COLUMN] = PgCurrency.addDifferentCurrencies(balanceTotal, valueTotal, PgCurrency.EURO);
    
    if (!ignoreDataErrors && firstException != null) {
      throw firstException;
    }
  }
}
