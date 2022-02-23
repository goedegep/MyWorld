package goedegep.app.finan.direktbankapp;


import goedegep.app.finan.gen.TransactionTable;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.appgen.swing.MenuFactory;
import goedegep.finan.direktbank.direktsprek.DirektSpRek;
import goedegep.finan.direktbank.direktsprek.DirektSpRekTransaction;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class DirektSpRekTransactionsWindow extends AppFrame {
  private static final String WINDOW_TITLE = "Direktspaarrekening";

//Status panel definitions
  private static final int        STATUS_PANEL_COLUMN_1 = 4;
  private static final int        STATUS_PANEL_COLUMN_1_SIZE = 90;
  private static final int        STATUS_PANEL_COLUMN_2 = STATUS_PANEL_COLUMN_1 + STATUS_PANEL_COLUMN_1_SIZE;
  private static final int        STATUS_PANEL_COLUMN_2_SIZE = 90;
  private static final int        STATUS_PANEL_COLUMN_3 = STATUS_PANEL_COLUMN_2 + STATUS_PANEL_COLUMN_2_SIZE;
  private static final int        STATUS_PANEL_COLUMN_3_SIZE = 90;
  private static final int        STATUS_PANEL_COLUMN_4 = STATUS_PANEL_COLUMN_3 + STATUS_PANEL_COLUMN_3_SIZE;
  private static final int        STATUS_PANEL_COLUMN_4_SIZE = 90;
  private static final int        STATUS_PANEL_COLUMN_5 = STATUS_PANEL_COLUMN_4 + STATUS_PANEL_COLUMN_4_SIZE;
  private static final int        TOP_MARGIN = 22;
  private static final int        ROW_DISTANCE = 24;
  private static final int        STATUS_PANEL_MONEY_FIELD_SIZE = 7;

  private static final int nrOfTransactionsToHandleInitially = 1;  // was 214

  private static final PgCurrencyFormat  cf = new PgCurrencyFormat();

//  private GenLook       look = DirektbankLook.getLook();
  private PgCurrency    balancePrevious;
  private PgCurrency    balanceCurrent;
  private PgCurrency    balanceDifference;
  private PgCurrency    nettoStortingPrevious;
  private PgCurrency    nettoStortingCurrent;
  private PgCurrency    nettoStortingDifference;

  private JTextField textFieldBalanceCurrent;
  private JTextField textFieldBalancePrevious;
  private JTextField textFieldBalanceDifference;
  private JTextField textFieldNettoStortingCurrent;
  private JTextField textFieldNettoStortingPrevious;
  private JTextField textFieldNettoStortingDifference;
  private JTextField textFieldWinstCurrent;
  private JTextField textFieldWinstPrevious;
  private JTextField textFieldWinstDifference;
  private JButton    nextButton = new JButton();


 //Layout: mainLayout is BorderLayout
  //Center is centerPanel is centerLayout is BorderLayout
  //North is statusPanel is statusLayout is GridBagLayout
  //South is statusBar
  //BorderLayout mainLayout = new BorderLayout();
  private DirektSpRek      account;
  private LocalDate        lastHandledDate = null;
  private ComponentFactory componentFactory;

  //Layout: borderLayout,
  //North is header (JLabel),
  //Center contains transaction lines (JScrollPane, with JTextArea),
  //South is statusbar (JLabel)

  // Statusbar
  JLabel statusBar = new JLabel();


  public DirektSpRekTransactionsWindow(Customization customization, DirektSpRek account) {
    super(WINDOW_TITLE, customization, null);
    this.account = account;
    componentFactory = getTheComponentFactory();

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
    // General window initialization
    getContentPane().setLayout(new BorderLayout());
//    getContentPane().setBackground(look.getBackGroundColor());
    setSize(new Dimension(1200, 600));            // set window size
    
    setJMenuBar(createMenuBar());              // set Menu

    getContentPane().add(createStatusPanel(), BorderLayout.NORTH);
    balanceCurrent       = account.getBalance();
    nettoStortingCurrent = account.getNettoStorting();

    nextButton.setText("volgende");
    nextButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        _nextButton_actionPerformed(e);
      }
    });


    DirektSpRekTransaction   transaction;
    int nrOfTransactions = account.numberOfTransactions();
    if (nrOfTransactions > nrOfTransactionsToHandleInitially) {
      nrOfTransactions = nrOfTransactionsToHandleInitially;
    }
    for (int currentTransaction = 1; currentTransaction <= nrOfTransactions; currentTransaction++) {
      transaction = (DirektSpRekTransaction) account.getTransaction(currentTransaction);
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
    getContentPane().add(new TransactionTable(this, account), BorderLayout.CENTER);


    dataChanged();

    // Status bar
    statusBar.setBackground(Color.blue);
    statusBar.setText("No info yet.");   // clear status bar text
    JPanel statusBarPanel = componentFactory.createPanel(-1, -1, false);
    statusBarPanel.add(statusBar, BorderLayout.CENTER);
    getContentPane().add(statusBarPanel, BorderLayout.SOUTH);
  }
  
  private JMenuBar createMenuBar() {
    JMenuBar    menuBar = new JMenuBar();
    JMenu       menu;
    
    // Menu bar
    // For each menu: set the text, "handle menu items" and add menu to menu bar.
    // "handle menu items" means: set the text, add an ActionListener and add the
    // item to the menu.

    // Menu: Overzichten
    menu = new JMenu("Overzichten");

    // Overzichten: Kwartaaloverzicht
    MenuFactory.addMenuItem(menu, "Kwartaaloverzichten", new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        kwartaalInformatie_actionPerformed(e);
      }
    });

    menuBar.add(menu);
    
    return menuBar;
  }

  /**
   * Create the status panel (the upper part of the center panel).
   * @return
   */
  private JPanel createStatusPanel() {
    JPanel panel = componentFactory.createPanel(600, 120, true);
    panel.setLayout(new SpringLayout());
    int vertOffset = TOP_MARGIN;
    JLabel label;

    textFieldBalanceCurrent = createStatusTextField();
    textFieldBalancePrevious = createStatusTextField();
    textFieldBalanceDifference = createStatusTextField();
    textFieldNettoStortingCurrent = createStatusTextField();
    textFieldNettoStortingPrevious = createStatusTextField();
    textFieldNettoStortingDifference = createStatusTextField();
    textFieldWinstCurrent = createStatusTextField();
    textFieldWinstPrevious = createStatusTextField();
    textFieldWinstDifference = createStatusTextField();

    /*
     * First row: headers (starting in 2nd column)
     */
    // saldo
    label = componentFactory.createLabel("Saldo", SwingConstants.LEFT);
    addComponentStatusPanel(panel, label, STATUS_PANEL_COLUMN_2, vertOffset);
    label = componentFactory.createLabel("Netto Storting", SwingConstants.LEFT);
    addComponentStatusPanel(panel, label, STATUS_PANEL_COLUMN_3, vertOffset);
    label = componentFactory.createLabel("Winst", SwingConstants.LEFT);
    addComponentStatusPanel(panel, label, STATUS_PANEL_COLUMN_4, vertOffset);

    /*
     * Second row: 'huidig'
     */
    vertOffset += ROW_DISTANCE;
    label = componentFactory.createLabel("Huidig", SwingConstants.LEFT);
    addComponentStatusPanel(panel, label, STATUS_PANEL_COLUMN_1, vertOffset);
    addComponentStatusPanel(panel, textFieldBalanceCurrent, STATUS_PANEL_COLUMN_2, vertOffset);
    addComponentStatusPanel(panel, textFieldNettoStortingCurrent, STATUS_PANEL_COLUMN_3, vertOffset);
    addComponentStatusPanel(panel, textFieldWinstCurrent, STATUS_PANEL_COLUMN_4, vertOffset);

    /*
     * Second row: 'vorig' + 'next button'
     */
    vertOffset += ROW_DISTANCE;
    label = componentFactory.createLabel("Vorig", SwingConstants.LEFT);
    addComponentStatusPanel(panel, label, STATUS_PANEL_COLUMN_1, vertOffset);
    addComponentStatusPanel(panel, textFieldBalancePrevious, STATUS_PANEL_COLUMN_2, vertOffset);
    addComponentStatusPanel(panel, textFieldNettoStortingPrevious, STATUS_PANEL_COLUMN_3, vertOffset);
    addComponentStatusPanel(panel, textFieldWinstPrevious, STATUS_PANEL_COLUMN_4, vertOffset);
    addComponentStatusPanel(panel, nextButton, STATUS_PANEL_COLUMN_5, vertOffset);

    /*
     * Second row: 'verschil'
     */
    vertOffset += ROW_DISTANCE;
    label = componentFactory.createLabel("Verschil", SwingConstants.LEFT);
    addComponentStatusPanel(panel, label, STATUS_PANEL_COLUMN_1, vertOffset);
    addComponentStatusPanel(panel, textFieldBalanceDifference, STATUS_PANEL_COLUMN_2, vertOffset);
    addComponentStatusPanel(panel, textFieldNettoStortingDifference, STATUS_PANEL_COLUMN_3, vertOffset);
    addComponentStatusPanel(panel, textFieldWinstDifference, STATUS_PANEL_COLUMN_4, vertOffset);

    return panel;
  }

  private static void addComponentStatusPanel(JPanel panel, JComponent component,
      int horOffset, int vertOffset) {
    SpringLayout layout = (SpringLayout) panel.getLayout();
    panel.add(component);

    layout.putConstraint(SpringLayout.WEST, component,
        horOffset,
        SpringLayout.WEST, panel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, component,
        vertOffset,
        SpringLayout.NORTH, panel);
  }

  private JTextField createStatusTextField() {
    JTextField textField = componentFactory.createTextField(STATUS_PANEL_MONEY_FIELD_SIZE, null);
    textField.setEditable(false);

    return textField;
  }

  private void dataChanged() {
    balancePrevious          = balanceCurrent;
    balanceCurrent           = account.getBalance();
    if (balancePrevious != null  &&  balanceCurrent != null  &&
        balancePrevious.getCurrency() != balanceCurrent.getCurrency()) {
      balancePrevious = balancePrevious.changeCurrency(balanceCurrent.getCurrency());
    }
    if (balancePrevious != null  &&  balanceCurrent != null) {
      balanceDifference = balanceCurrent.subtract(balancePrevious);
    }

    nettoStortingPrevious = nettoStortingCurrent;
    nettoStortingCurrent = account.getNettoStorting();
    if (nettoStortingPrevious != null  &&  nettoStortingCurrent != null  &&
        nettoStortingPrevious.getCurrency() != nettoStortingCurrent.getCurrency()) {
      nettoStortingPrevious = nettoStortingPrevious.changeCurrency(nettoStortingCurrent.getCurrency());
    }
    if (nettoStortingCurrent != null  &&  nettoStortingPrevious != null) {
      nettoStortingDifference  = nettoStortingCurrent.subtract(nettoStortingPrevious);
    }

    PgCurrency winstCurrent = null;
    if (balanceCurrent != null  &&  nettoStortingCurrent != null) {
      winstCurrent = balanceCurrent.subtract(nettoStortingCurrent);
    }
    PgCurrency winstPrevious = null;
    if (balancePrevious != null  &&  nettoStortingPrevious != null) {
      winstPrevious = balancePrevious.subtract(nettoStortingPrevious);
    }
    PgCurrency winstDifference = null;
    if (balanceDifference != null  &&  nettoStortingDifference != null) {
      winstDifference = balanceDifference.subtract(nettoStortingDifference);
    }

    textFieldBalanceCurrent.setText(cf.format(balanceCurrent));
    textFieldBalancePrevious.setText(cf.format(balancePrevious));
    textFieldBalanceDifference.setText(cf.format(balanceDifference));
    textFieldNettoStortingCurrent.setText(cf.format(nettoStortingCurrent));
    textFieldNettoStortingPrevious.setText(cf.format(nettoStortingPrevious));
    textFieldNettoStortingDifference.setText(cf.format(nettoStortingDifference));
    textFieldWinstCurrent.setText(cf.format(winstCurrent));
    textFieldWinstPrevious.setText(cf.format(winstPrevious));
    textFieldWinstDifference.setText(cf.format(winstDifference));
  }


  void _nextButton_actionPerformed(ActionEvent e) {
    DirektSpRekTransaction   transaction;

    if (account != null) {
      int nrOfTransactions = account.numberOfTransactions();
      for (int currentTransaction = 1; currentTransaction <= nrOfTransactions; currentTransaction++) {
        transaction = (DirektSpRekTransaction) account.getTransaction(currentTransaction);
        if (!transaction.isHandled()) {
          transaction.handle(null);
          if (lastHandledDate == null) {
            lastHandledDate = transaction.getBookingDate();
          } else if (transaction.getBookingDate() != null) {
            if (transaction.getBookingDate().isAfter(lastHandledDate)) {
              lastHandledDate = transaction.getBookingDate();
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
  
  private void kwartaalInformatie_actionPerformed(ActionEvent e) {
    showKwartaaloverzichtenWindow();
  }

  private void showKwartaaloverzichtenWindow() {
    boolean packFrame = false;
    DirektSpRekKwartaaloverzichtenWindow  window = new DirektSpRekKwartaaloverzichtenWindow(getCustomization(), account);

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame)
      window.pack();
    else
      window.validate();
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = window.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    window.setLocation((screenSize.width - frameSize.width) / 2 + 22, (screenSize.height - frameSize.height) / 2 + 22);
    window.setVisible(true);
  }
}