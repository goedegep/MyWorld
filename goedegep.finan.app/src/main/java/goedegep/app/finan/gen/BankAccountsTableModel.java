package goedegep.app.finan.gen;

import goedegep.appgen.swing.AppGenAbstractTableModel;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.PgAccount;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.util.List;

@SuppressWarnings("serial")
public class BankAccountsTableModel extends AppGenAbstractTableModel {
  // Column Identifiers
  private static final int REKENING_COLUMN = 0;
  private static final int SALDO_COLUMN = 1;
  private static final int WAARDE_COLUMN = 2;
  private static final int TOTAAL_COLUMN = 3;
  
  private static final String[] columnNames = {
      "Rekening",
      "Saldo",
      "Waarde",
      "Totaal"
  };
  
  private static final Object[] longValues = {
      "Direktspaarrekening",
      PgCurrencyFormat.EURO_SYMBOL_STR + "999999,99",
      PgCurrencyFormat.EURO_SYMBOL_STR + "999999,99",
      PgCurrencyFormat.EURO_SYMBOL_STR + "999999,99"};
  
  private Bank bank;
  private int munteenheid;
  private Object[][] data;
  
  public BankAccountsTableModel(Bank bank, int munteenheid) {
    super(columnNames, longValues);
    this.bank = bank;
    this.munteenheid = munteenheid;
    
    fillData();
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
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Class getColumnClass(int col) {
    switch (col) {
    case REKENING_COLUMN:
      return String.class;

    case SALDO_COLUMN:
    case WAARDE_COLUMN:
    case TOTAAL_COLUMN:
      return PgCurrency.class;

    default:
      return null;
    }
  }
  
  public void setMunteenheid(int munteenheid) {
    this.munteenheid = munteenheid;
    fillData();
    fireTableDataChanged();
  }

  void fillData() {
    List<PgAccount>   accounts = bank.getAccounts();
    PgCurrency        rowTotal = null;
    PgCurrency        balanceTotal = null;
    PgCurrency        valueTotal = null;
    short             index;

    // allocate one extra line for the total line.
    data = new Object[accounts.size() + 1][4];

    for (index = 0; index < accounts.size(); index++) {
      PgAccount account = accounts.get(index);

      data[index][0] = account.getName();

      PgCurrency balance = certifyRequestedCurrency(account.getBalance(), munteenheid);
      data[index][1] = balance;
      rowTotal = balance;
      balanceTotal = updateTotal(balanceTotal, balance);

      PgCurrency value = certifyRequestedCurrency(account.getEstimatedValue(), munteenheid);
      data[index][2] = value;
      rowTotal = updateTotal(rowTotal, value);

      valueTotal = updateTotal(valueTotal, value);

      data[index][3] = rowTotal;
    }
    data[index][0] = "Totaal";
    data[index][1] = balanceTotal;
    data[index][2] = valueTotal;
    PgCurrency grandTotal = balanceTotal;
    grandTotal = updateTotal(grandTotal, valueTotal);
    data[index][3] = grandTotal;
  }

  private static PgCurrency certifyRequestedCurrency(PgCurrency value, int munteenheid) {
    if ((value == null)  ||
        (munteenheid == PgCurrency.NO_CURRENCY_SPECIFIED)) {
      return value;
    } else {
      return value.certifyCurrency(munteenheid);
    }
  }
  
  private PgCurrency updateTotal(PgCurrency total, PgCurrency value) {
    if (total == null) {
      return value;
    }
    
    if (value == null) {
      return total;
    }
    
    // If currencies differ, always use Euro.
    if (total.getCurrency() != value.getCurrency()) {
      total = total.certifyCurrency(PgCurrency.EURO);
      value = value.certifyCurrency(PgCurrency.EURO);
    }
    return total.add(value);
  }
}
