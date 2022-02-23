package goedegep.app.finan.effrek;

import goedegep.app.finan.abnamrobank.AAEffRekWindow;
import goedegep.app.finan.gen.FinanBank;
import goedegep.app.finan.gen.TransactionTable;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRek;
import goedegep.finan.effrek.EffRek;
import goedegep.finan.effrek.EffRekTransactie;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class EffRekTestWindow extends AppFrame {
  private static final PgCurrencyFormat  cf = new PgCurrencyFormat();

  private LocalDate             lastHandledDate = null;

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

  
  // Layout: borderLayout,
  //         North is Status panel (like Effectenrekening window)
  //         Center is Transaction panel (like Effectenrekening transactions window)
  //         South is statusbar (JLabel)

  // Statusbar
  private JLabel statusBar = new JLabel();

  private FinanBank finanBank;
  private EffRek    effectenRekening;
  
  public EffRekTestWindow(Customization customization, String title, FinanBank finanBank, EffRek effRek, int nrOfTransactionsToHandleInitially) {
    super(title, customization, new Dimension(1300, 600));
    
    this.finanBank = finanBank;
    this.effectenRekening = effRek;
    
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit(nrOfTransactionsToHandleInitially);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public FinanBank getFinanBank() {
    return finanBank;
  }

  //Component initialization
  private void jbInit(int nrOfTransactionsToHandleInitially) throws Exception  {
    ComponentFactory componentFactory = getTheComponentFactory();
    
    // General window initialization
    getContentPane().setLayout(new BorderLayout());      // install the borderLayout
//    getContentPane().setBackground(getLook().getBackgroundColor());
//    setSize(new Dimension(1300, 600));

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
    textFieldWinstCurrent.setEditable(false);
    textFieldWinstPrevious.setEditable(false);
    textFieldWinstDifference.setEditable(false);

    JButton nextButton = componentFactory.createButton("volgende", "verwerk volgende transactie");
    nextButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        _nextButton_actionPerformed(e);
      }
    });

    EffRekTransactie   transaction;
    int nrOfTransactions = effectenRekening.numberOfTransactions();
    if (nrOfTransactions > nrOfTransactionsToHandleInitially) {
      nrOfTransactions = nrOfTransactionsToHandleInitially;
    }
    for (int currentTransaction = 1; currentTransaction <= nrOfTransactions; currentTransaction++) {
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
      }
    }
    if (lastHandledDate != null) {
      System.out.println("Init: Date = " + lastHandledDate.toString());
    }
    
    // Center panel
   
    // Transactions Table
    getContentPane().add(new TransactionTable(this, effectenRekening), BorderLayout.CENTER);
    

    dataChanged();

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
    this.getContentPane().add(statusBarPanel, BorderLayout.SOUTH);
  }

  private void dataChanged() {
    balancePrevious          = balanceCurrent;
    balanceCurrent           = effectenRekening.getBalance();
    if (balancePrevious.getCurrency() != balanceCurrent.getCurrency()) {
      balancePrevious = balancePrevious.changeCurrency(balanceCurrent.getCurrency());
    }
    balanceDifference        = balanceCurrent.subtract(balancePrevious);

    valuePrevious            = valueCurrent;
    valueCurrent             = effectenRekening.getVerzamelDepot().getValue(lastHandledDate);
    if (valuePrevious.getCurrency() != valueCurrent.getCurrency()) {
      valuePrevious = valuePrevious.changeCurrency(valueCurrent.getCurrency());
    }
    valueDifference          = valueCurrent.subtract(valuePrevious);

    nettoStortingPrevious    = nettoStortingCurrent;
    nettoStortingCurrent     = effectenRekening.getNettoStorting();
    if (nettoStortingPrevious.getCurrency() != nettoStortingCurrent.getCurrency()) {
      nettoStortingPrevious = nettoStortingPrevious.changeCurrency(nettoStortingCurrent.getCurrency());
    }
    nettoStortingDifference  = nettoStortingCurrent.subtract(nettoStortingPrevious);

    investeringPrevious      = investeringCurrent;
    investeringCurrent       = effectenRekening.getVerzamelDepot().getTotalInvestment();
    if (investeringPrevious.getCurrency() != investeringCurrent.getCurrency()) {
      investeringPrevious = investeringPrevious.changeCurrency(investeringCurrent.getCurrency());
    }
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
    if (nettoStortingCurrent != null) {
      textFieldWinstCurrent.setText(cf.format(balanceCurrent.add(valueCurrent).subtract(nettoStortingCurrent)));
    } else {
      textFieldWinstCurrent.setText("--");
    }
    textFieldWinstPrevious.setText(cf.format(balancePrevious.add(valuePrevious).subtract(nettoStortingPrevious)));
    textFieldWinstDifference.setText(cf.format(balanceDifference.add(valueDifference).subtract(nettoStortingDifference)));

    showEffectenRekeningWindow();
  }
  
  void showEffectenRekeningWindow() {
    statusBar.setText("Effectenrekening Window Last Date");
    boolean             packFrame = false;

    AAEffRekWindow effectenrekeningWindow = new AAEffRekWindow(getCustomization(), getFinanBank(), (AAEffRek) effectenRekening, lastHandledDate);
    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame)
      effectenrekeningWindow.pack();
    else
      effectenrekeningWindow.validate();
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = effectenrekeningWindow.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    effectenrekeningWindow.setLocation((screenSize.width - frameSize.width) / 2 + 44, (screenSize.height - frameSize.height) / 2 + 44);
    effectenrekeningWindow.setVisible(true);
  }

  void _nextButton_actionPerformed(ActionEvent e) {
    EffRekTransactie   transaction;

    if (effectenRekening != null) {
      int nrOfTransactions = effectenRekening.numberOfTransactions();
      for (int currentTransaction = 1; currentTransaction <= nrOfTransactions; currentTransaction++) {
        transaction = (EffRekTransactie) effectenRekening.getTransaction(currentTransaction);
        if (!transaction.isHandled()) {
          transaction.handle(null);
          if (transaction.getBookingDate() != null) {
            LocalDate bookingDate = transaction.getBookingDate();
            if (lastHandledDate == null  ||  bookingDate.isAfter(lastHandledDate)) {
              lastHandledDate = bookingDate;
            }
          }
          System.out.println("Last handled date = " + (lastHandledDate == null ? "??--??--??" : lastHandledDate.toString()));
          System.out.println("Transaction(toString) = " + transaction.toString());
          break;
        }
      }
    } else {
      statusBar.setText("Geen rekening opgegeven");
    }

    dataChanged();
  }

}
