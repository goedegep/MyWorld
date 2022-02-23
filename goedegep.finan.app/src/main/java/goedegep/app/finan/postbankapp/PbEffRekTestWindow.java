//Title:      Financien
//Version:
//Copyright:  Copyright (c) 1999
//Author:     Peter Goedegebure
//Company:
//Description:Postbank Effectenrekening Test Window

package goedegep.app.finan.postbankapp;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import goedegep.app.finan.gen.FinanBank;
import goedegep.app.finan.gen.TransactionTable;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.effrek.EffRek;
import goedegep.finan.effrek.EffRekTransactie;
import goedegep.finan.postbank.pbeffrek.PbEffRek;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

@SuppressWarnings("serial")
public class PbEffRekTestWindow extends AppFrame {
  private final static Logger LOGGER = Logger.getLogger(PbEffRekTestWindow.class.getName());

  private static final String         WINDOW_TITLE = "Postbank Effectenrekening Test Window";
  private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
  
//  private static final int NR_OF_TRANSACTIONS_TO_HANDLE_INITIALY = 2331;  // current value for checked transactions
//private static final int NR_OF_TRANSACTIONS_TO_HANDLE_INITIALY = 0;  // current value for checked status window
  private static final int NR_OF_TRANSACTIONS_TO_HANDLE_INITIALY = 10;
  
  private static final PgCurrencyFormat  cf = new PgCurrencyFormat();

  private LocalDate             lastHandledDate = null;
  private FinanBank             finanBank;
  private EffRek                effectenRekening;

  private PgCurrency            balanceCurrent;
  private PgCurrency            balancePrevious;
  private PgCurrency            balanceDifference;
  private PgCurrency            valueCurrent;
  private PgCurrency            valuePrevious;
  private PgCurrency            valueDifference;
  private PgCurrency            nettoStortingCurrent;
  private PgCurrency            nettoStortingPrevious;
  private PgCurrency            nettoStortingDifference;
  private PgCurrency            investeringCurrent;
  private PgCurrency            investeringPrevious;
  private PgCurrency            investeringDifference;

  private JTextField            textFieldBalanceCurrent              = new JTextField();
  private JTextField            textFieldBalancePrevious             = new JTextField();
  private JTextField            textFieldBalanceDifference           = new JTextField();
  private JTextField            textFieldValueCurrent                = new JTextField();
  private JTextField            textFieldValuePrevious               = new JTextField();
  private JTextField            textFieldValueDifference             = new JTextField();
  private JTextField            textFieldNettoStortingCurrent        = new JTextField();
  private JTextField            textFieldNettoStortingPrevious       = new JTextField();
  private JTextField            textFieldNettoStortingDifference     = new JTextField();
  private JTextField            textFieldNettoInvesteringCurrent     = new JTextField();
  private JTextField            textFieldNettoInvesteringPrevious    = new JTextField();
  private JTextField            textFieldNettoInvesteringDifference  = new JTextField();
  private JTextField            textFieldWinstCurrent                = new JTextField();
  private JTextField            textFieldWinstPrevious               = new JTextField();
  private JTextField            textFieldWinstDifference             = new JTextField();
  
  private JTable transactionTable;
  private int    currentTransaction = 0;
  private PbEffRekWindow previousEffectenrekeningWindow;


  // Layout: borderLayout,
  //         North is Status panel (like Effectenrekening window)
  //         Center is Transaction panel (like Effectenrekening transactions window)
  //         South is statusbar (JLabel)

  // Statusbar
  private JLabel statusBar = new JLabel();

  public PbEffRekTestWindow(Customization customization, FinanBank finanBank, EffRek effectenRekening) {
    super(WINDOW_TITLE, customization, null);
    this.finanBank = finanBank;
    this.effectenRekening = effectenRekening;

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception  {
    ComponentFactory componentFactory = getTheComponentFactory();
    
    // General window initialization
    getContentPane().setLayout(new BorderLayout());      // install the borderLayout
//    getContentPane().setBackground(getLook().getBackgroundColor());
    setSize(new Dimension(1300, 600));             // set window size

    // Status panel (upper part of Center panel)
    JPanel statusPanel = new JPanel();
    statusPanel.setLayout(new GridBagLayout());
//    statusPanel.setBackground(getLook().getBackgroundColor());

    balanceCurrent       = effectenRekening.getBalance();
    valueCurrent         = effectenRekening.getVerzamelDepot().getValue(lastHandledDate);
    nettoStortingCurrent = effectenRekening.getNettoStorting();
    investeringCurrent   = effectenRekening.getVerzamelDepot().getTotalInvestment();

    textFieldBalanceCurrent.setEditable(false);
    textFieldBalancePrevious.setEditable(false);
    textFieldBalanceDifference.setEditable(false);
    textFieldValueCurrent.setEditable(false);
    textFieldValuePrevious.setEditable(false);
    textFieldValueDifference.setEditable(false);
    textFieldNettoStortingCurrent.setEditable(false);
    textFieldNettoStortingPrevious.setEditable(false);
    textFieldNettoStortingDifference.setEditable(false);
    textFieldNettoInvesteringCurrent.setEditable(false);
    textFieldNettoInvesteringPrevious.setEditable(false);
    textFieldNettoInvesteringDifference.setEditable(false);

    JButton nextButton = componentFactory.createButton("volgende", "verwerk volgende transactie");
    nextButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        _nextButton_actionPerformed(e);
      }
    });

    EffRekTransactie   transaction;
    int nrOfTransactions = effectenRekening.numberOfTransactions();
    if (nrOfTransactions > NR_OF_TRANSACTIONS_TO_HANDLE_INITIALY) {
      nrOfTransactions = NR_OF_TRANSACTIONS_TO_HANDLE_INITIALY;
    }
    for (currentTransaction = 1; currentTransaction <= nrOfTransactions; currentTransaction++) {
      transaction = (EffRekTransactie) effectenRekening.getTransaction(currentTransaction);
      if (!transaction.isHandled()) {
        transaction.handle(null);
        if (lastHandledDate == null) {
          lastHandledDate = transaction.getBookingDate();
        } else {
          if (transaction.getBookingDate() != null) {
            if (transaction.getBookingDate().isAfter(lastHandledDate)) {
              lastHandledDate = transaction.getBookingDate();
            }
          }
        }
        LOGGER.severe("Last handled date = " + (lastHandledDate == null ? "??--??--??" : DF.format(lastHandledDate)));
        LOGGER.severe("Transaction(toString) = " + transaction.toString());
      }
    }
    LOGGER.severe("currentTransaction: " + currentTransaction);
    if (lastHandledDate != null) {
      LOGGER.severe("Date of last initially handled transaction = " + lastHandledDate.toString());
    }
    
    // header
    statusPanel.add(componentFactory.createLabel("saldo----------", SwingConstants.LEFT),
            new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(componentFactory.createLabel("waarde         ", SwingConstants.LEFT),
            new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    statusPanel.add(componentFactory.createLabel("netto storting ", SwingConstants.LEFT),
        new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(new JLabel("investering    "), new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(new JLabel("Winst          "), new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));

    // huidig
    statusPanel.add(new JLabel("huidig  "), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(textFieldBalanceCurrent, new GridBagConstraints(GridBagConstraints.RELATIVE, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    statusPanel.add(textFieldValueCurrent, new GridBagConstraints(GridBagConstraints.RELATIVE, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(textFieldNettoStortingCurrent, new GridBagConstraints(GridBagConstraints.RELATIVE, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(textFieldNettoInvesteringCurrent, new GridBagConstraints(GridBagConstraints.RELATIVE, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    statusPanel.add(textFieldWinstCurrent, new GridBagConstraints(GridBagConstraints.RELATIVE, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

    // vorig
    statusPanel.add(new JLabel("vorig   "), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(textFieldBalancePrevious, new GridBagConstraints(GridBagConstraints.RELATIVE, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    statusPanel.add(textFieldValuePrevious, new GridBagConstraints(GridBagConstraints.RELATIVE, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(textFieldNettoStortingPrevious, new GridBagConstraints(GridBagConstraints.RELATIVE, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(textFieldNettoInvesteringPrevious, new GridBagConstraints(GridBagConstraints.RELATIVE, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    statusPanel.add(textFieldWinstPrevious, new GridBagConstraints(GridBagConstraints.RELATIVE, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    statusPanel.add(nextButton, new GridBagConstraints(GridBagConstraints.RELATIVE, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

    // verschil
    statusPanel.add(new JLabel("verschil"), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(textFieldBalanceDifference, new GridBagConstraints(GridBagConstraints.RELATIVE, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    statusPanel.add(textFieldValueDifference, new GridBagConstraints(GridBagConstraints.RELATIVE, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(textFieldNettoStortingDifference, new GridBagConstraints(GridBagConstraints.RELATIVE, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(textFieldNettoInvesteringDifference, new GridBagConstraints(GridBagConstraints.RELATIVE, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    statusPanel.add(textFieldWinstDifference, new GridBagConstraints(GridBagConstraints.RELATIVE, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));


    // Status bar
    statusBar.setBackground(Color.blue);
    statusBar.setText("No info yet.");   // clear status bar text
    JPanel statusBarPanel = componentFactory.createPanel(-1, -1, false);
    statusBarPanel.add(statusBar, BorderLayout.CENTER);
    this.getContentPane().add(statusPanel, BorderLayout.NORTH);
    TransactionTable table = new TransactionTable(this, effectenRekening);
    transactionTable = table.getTable();
    this.getContentPane().add(transactionTable, BorderLayout.CENTER);
    this.getContentPane().add(statusBarPanel, BorderLayout.SOUTH);
    
    dataChanged();
  }

  private void dataChanged() {
    balancePrevious          = balanceCurrent;
    balanceCurrent           = effectenRekening.getBalance();
    int currencyUnitToUse = balanceCurrent.getCurrency();
    
    balancePrevious = balancePrevious.certifyCurrency(currencyUnitToUse);
    balanceDifference        = balanceCurrent.subtract(balancePrevious);

    valuePrevious            = valueCurrent;
    valuePrevious = valuePrevious.certifyCurrency(currencyUnitToUse);
    
    valueCurrent             = effectenRekening.getVerzamelDepot().getValue(lastHandledDate);
    valueCurrent = valueCurrent.certifyCurrency(currencyUnitToUse);
    
    if ((valuePrevious != null)  &&  (valueCurrent != null)) {
      valueDifference          = valueCurrent.subtract(valuePrevious);
    } else {
      valueDifference = null;
    }

    nettoStortingPrevious    = nettoStortingCurrent;
    nettoStortingPrevious = nettoStortingPrevious.certifyCurrency(currencyUnitToUse);
    
    nettoStortingCurrent     = effectenRekening.getNettoStorting();
    nettoStortingCurrent = nettoStortingCurrent.certifyCurrency(currencyUnitToUse);
    nettoStortingDifference  = nettoStortingCurrent.subtract(nettoStortingPrevious);

    investeringPrevious      = investeringCurrent;
    investeringPrevious = investeringPrevious.changeCurrency(currencyUnitToUse);
    
    investeringCurrent       = effectenRekening.getVerzamelDepot().getTotalInvestment();
    investeringDifference    = investeringCurrent.subtract(investeringPrevious);

    textFieldBalanceCurrent.setText(cf.format(balanceCurrent));
    textFieldBalancePrevious.setText(cf.format(balancePrevious));
    textFieldBalanceDifference.setText(cf.format(balanceDifference));
    textFieldValueCurrent.setText(cf.format(valueCurrent));
    textFieldValuePrevious.setText(cf.format(valuePrevious));
    textFieldValueDifference.setText(cf.format(valueDifference));
    textFieldNettoStortingCurrent.setText(cf.format(nettoStortingCurrent));
    textFieldNettoStortingPrevious.setText(cf.format(nettoStortingPrevious));
    textFieldNettoStortingDifference.setText(cf.format(nettoStortingDifference));
    textFieldNettoInvesteringCurrent.setText(cf.format(investeringCurrent));
    textFieldNettoInvesteringPrevious.setText(cf.format(investeringPrevious));
    textFieldNettoInvesteringDifference.setText(cf.format(investeringDifference));
    if (valueCurrent != null) { 
      textFieldWinstCurrent.setText(cf.format(balanceCurrent.add(valueCurrent).subtract(nettoStortingCurrent)));
    } else {
      textFieldWinstCurrent.setText("--");
    }
    if (valuePrevious != null) {
      textFieldWinstPrevious.setText(cf.format(balancePrevious.add(valuePrevious).subtract(nettoStortingPrevious)));
    } else {
      textFieldWinstPrevious.setText("--");
    }
    if (valueDifference != null) {
      textFieldWinstDifference.setText(cf.format(balanceDifference.add(valueDifference).subtract(nettoStortingDifference)));
    } else {
      textFieldWinstDifference.setText("--");
    }
    
//    if ((transactionTable != null)  &&  (currentTransaction >= 2)) {
    LOGGER.severe("currentTransaction: " + currentTransaction);
      transactionTable.setRowSelectionInterval(currentTransaction - 2, currentTransaction - 2);
//    }
    repaint();

    showEffectenRekeningWindow();
  }
  
  void showEffectenRekeningWindow() {
    statusBar.setText("Datum laatst afgehandelde transactie: " +  (lastHandledDate == null ? "??--??--??" : DF.format(lastHandledDate)));
    boolean             packFrame = false;

    if (previousEffectenrekeningWindow != null) {
      previousEffectenrekeningWindow.setLocation(20, 20);
    }
    
    PbEffRekWindow effectenrekeningWindow = new PbEffRekWindow(getCustomization(), finanBank, (PbEffRek) effectenRekening, lastHandledDate);
    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame)
      effectenrekeningWindow.pack();
    else
      effectenrekeningWindow.validate();
    // Put the new window in the top right corner
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = effectenrekeningWindow.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    effectenrekeningWindow.setLocation(screenSize.width - frameSize.width - 20, 20);
    effectenrekeningWindow.setVisible(true);
    
    previousEffectenrekeningWindow = effectenrekeningWindow;
  }

  void _nextButton_actionPerformed(ActionEvent e) {
    EffRekTransactie   transaction;

    if (effectenRekening != null) {
      int nrOfTransactions = effectenRekening.numberOfTransactions();
      for (currentTransaction = 1; currentTransaction <= nrOfTransactions; currentTransaction++) {
        transaction = (EffRekTransactie) effectenRekening.getTransaction(currentTransaction);
        if (!transaction.isHandled()) {
          transaction.handle(null);
          if (transaction.getBookingDate() != null) {
            if ((lastHandledDate == null)  ||  transaction.getBookingDate().isAfter(lastHandledDate)) {
              lastHandledDate = transaction.getBookingDate();
            }
          }
          LOGGER.severe("Last handled date = " + (lastHandledDate == null ? "??--??--??" : DF.format(lastHandledDate)));
          LOGGER.severe("Transaction(toString) = " + transaction.toString());
          currentTransaction++;
          break;
        }
      }
      LOGGER.severe("currentTransaction: " + currentTransaction);
    } else {
      statusBar.setText("Geen rekening opgegeven");
    }

    dataChanged();
  }
}