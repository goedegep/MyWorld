//Title:      PbSpRekWindow
//Version:
//Copyright:  Copyright (c) 2001
//Author:     Peter Goedegebure
//Company:    Solvation
//Description:Postbank Spaarrekeningen window

package goedegep.app.finan.postbankapp;

import goedegep.appgen.swing.Customization;
import goedegep.appgen.WindowUtil;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.postbank.pbsprek.PbSpRek;
import goedegep.finan.postbank.pbsprek.PbSpRekTransaction;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PbSpRekWindow extends AppFrame {
  private static final String    WINDOW_TITLE = "Postbank Spaarrekeningen";
  private List<PbSpRek>          spaarrekeningen;

  // Layout: borderLayout
  BorderLayout borderLayout = new BorderLayout();

  // Items in main area
  JPanel        mainPanel = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel      labelRekeningNaam       = new JLabel();     // "Rekening" label (1e kolom)
  JLabel      labelSaldo              = new JLabel();     // "Saldo" label (2e kolom)
  JLabel      labelInvestering        = new JLabel();     // "Investering" label (3e kolom)
  JLabel      labelVrijOpneembaar     = new JLabel();     // "Vrij opneembaar" label (4e kolom)
  JLabel      labelTotal              = new JLabel();     // "Totaal" label (1e kolom, laatste regel)

  // Statusbar
  JPanel statusbarPanel = new JPanel();
  JLabel statusBar = new JLabel();


  //Construct the frame
  public PbSpRekWindow(Customization customization, List<PbSpRek> spaarrekeningen) {
    super(WINDOW_TITLE, customization, null);
    this.spaarrekeningen = spaarrekeningen;

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void init() throws Exception  {
    // General window initialization
    this.setSize(new Dimension(700, 409));     // set window size

    setJMenuBar(createMenuBar());


    // Main area
    this.getContentPane().setLayout(borderLayout);      // install the borderLayout

    // main part of main area
    PgCurrencyFormat  cf = new PgCurrencyFormat();

    mainPanel.setLayout(gridBagLayout1);
    labelRekeningNaam.setText("Rekening");
    labelSaldo.setText("Tegoed");
    labelInvestering.setText("Investering");
    labelVrijOpneembaar.setText("Vrij opneembaar");
    labelTotal.setText("Totaal");

    // Status bar
    statusBar.setText(" ");       // clear status bar text

    this.getContentPane().add(statusbarPanel, BorderLayout.SOUTH);
    statusbarPanel.add(statusBar, null);
    this.getContentPane().add(mainPanel, BorderLayout.CENTER);

    // First line: labels
    mainPanel.add(labelRekeningNaam, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    mainPanel.add(labelSaldo, new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    mainPanel.add(labelInvestering, new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    mainPanel.add(labelVrijOpneembaar, new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

    JTextField    textFieldRekeningNaam;
    JTextField    textFieldSaldo;
    JTextField    textFieldInvestering;
    JTextField    textFieldVrijOpneembaar;
    PgCurrency    money;
    PgCurrency    totaalSaldo = new PgCurrency(PgCurrency.GUILDER, 0L);
    PgCurrency    totaalInvestering  = new PgCurrency(PgCurrency.GUILDER, 0L);
    short         line = 1;

    for (PbSpRek account: spaarrekeningen) {
      // rekening naam
      textFieldRekeningNaam = new JTextField(account.getName());
      mainPanel.add(textFieldRekeningNaam, new GridBagConstraints(0, line, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));

      // saldo
      money = account.getBalance();
      if (totaalSaldo.getCurrency() !=  money.getCurrency()) {
        totaalSaldo = totaalSaldo.changeCurrency(money.getCurrency());
      }
      totaalSaldo = totaalSaldo.add(money);
      textFieldSaldo = new JTextField(cf.format(money));
      mainPanel.add(textFieldSaldo, new GridBagConstraints(GridBagConstraints.RELATIVE, line, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));

      // investering
      money = account.getNettoStorting();
      if (totaalInvestering.getCurrency() !=  money.getCurrency()) {
        totaalInvestering = totaalInvestering.changeCurrency(money.getCurrency());
      }
      totaalInvestering = totaalInvestering.add(money);
      textFieldInvestering = new JTextField(cf.format(money));
      mainPanel.add(textFieldInvestering, new GridBagConstraints(GridBagConstraints.RELATIVE, line, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));

      textFieldVrijOpneembaar = new JTextField(cf.format(account.getVrijOpneembaarBedragVoor((PbSpRekTransaction) account.getTransaction(account.getTransactions().size() -1))));
      mainPanel.add(textFieldVrijOpneembaar, new GridBagConstraints(GridBagConstraints.RELATIVE, line, 1, 1, 0.0, 0.0
          ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
      line++;
    }

    if (!spaarrekeningen.isEmpty()) {
      // Total line
      mainPanel.add(labelTotal, new GridBagConstraints(0, line, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));

      textFieldSaldo = new JTextField(cf.format(totaalSaldo));
      mainPanel.add(textFieldSaldo, new GridBagConstraints(GridBagConstraints.RELATIVE, line, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));

      textFieldInvestering = new JTextField(cf.format(totaalInvestering));
      mainPanel.add(textFieldInvestering, new GridBagConstraints(GridBagConstraints.RELATIVE, line, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    }

    if (spaarrekeningen.isEmpty()) {
      statusBar.setText("Er zijn geen spaarrekeningen");
    }
  }
  private JMenuBar createMenuBar() {
    JMenuBar    menuBar = new JMenuBar();
    JMenu       menu;
    
    // Menu bar
    // For each menu: set the text, "handle menu items" and add menu to menu bar.
    // "handle menu items" means: set the text, add an ActionListener and add the
    // item to the menu.

    // Menu: Transacties
    menu = new JMenu("Transacties");

    for (final PbSpRek spRek: spaarrekeningen) {
      MenuFactory.addMenuItem(menu, spRek.getName(), new ActionListener()  {
        public void actionPerformed(ActionEvent e) {
          transactiesPerSpaarrekening_actionPerformed(e, spRek);
        }
      });
    }

    menuBar.add(menu);

    return menuBar;
  }

  //File | Exit action performed
  private void fileExit_actionPerformed(ActionEvent e) {
    // System.exit(0);
  }

  // Transacties | Per aandeel action performed
  void transactiesPerSpaarrekening_actionPerformed(ActionEvent e, PbSpRek account) {
    PbSpRekTransactionsWindow   window = new PbSpRekTransactionsWindow(getCustomization(), "Postbank " + account.getName(), account);
    WindowUtil.showFrameCenteredOnScreen(window, 22, 0);
  }

  //Overridden so we can exit on System Close
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING) {
      fileExit_actionPerformed(null);
    }
  }
}