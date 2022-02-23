package goedegep.app.finan.gen;

import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.finan.basic.PgAccount;

import java.awt.BorderLayout;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class TransactionsWindow extends AppFrame {

  public TransactionsWindow(String title, Customization customization, Dimension size, PgAccount account) {
    super(title, customization, size);
    
    // Transactions Table
    getContentPane().add(new TransactionTable(this, account), BorderLayout.CENTER);
  }

}
