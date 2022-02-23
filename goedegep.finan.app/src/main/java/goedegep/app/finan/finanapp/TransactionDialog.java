package goedegep.app.finan.finanapp;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import goedegep.app.finan.finanapp.td.CDTransactionType;
import goedegep.app.finan.finanapp.td.FinanTransactionEntryStatus;
import goedegep.app.finan.finanapp.td.TransactionInfo;
import goedegep.app.finan.gen.FinanBank;
import goedegep.app.finan.registry.FinanRegistry;
import goedegep.app.finan.td.CD;
import goedegep.app.finan.td.CDDateField;
import goedegep.app.finan.td.CDLabel;
import goedegep.app.finan.td.DC;
import goedegep.app.finan.td.TransactionDialogComponentList;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.LookAheadComboBox;
import goedegep.appgen.swing.AppDialog;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.basic.Bank;
import goedegep.finan.basic.FinanTransaction;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.basic.SumAccount;
import goedegep.finan.stocks.CompanyPool;

/**
 * @author Peter Goedegebure
 * @version 1.0
 */

@SuppressWarnings("serial")
public class TransactionDialog extends AppDialog implements ActionListener, FocusListener {
  private static final Logger         LOGGER = Logger.getLogger(TransactionDialog.class.getName());
  
  private static final String       DIALOG_TITLE = "Nieuwe transactie invoeren";
  private static final String       INITIAL_REKENINGFIELD_TEXT = "Kies eerst een bank";
//  private static final String[]     INITIAL_REKENINGSELECTOR_DATA = {INITIAL_REKENINGFIELD_TEXT};
  private final AccountWrapper[]     INITIAL_REKENINGSELECTOR_DATA = {new AccountWrapper(null)};

  private final static int  TOP_MARGIN       = 5;
  private final static int  LINE_SPACING     = 24;
  private final static int  COLUMN_1_OFFSET   = 5;
  private final static int  COLUMN_2_OFFSET   = 200;

  private static final CDLabel           RENTE_DATUM_LABEL = new CDLabel("RenteDatumLabel", "Rente Datum:");
  private static final CDDateField       RENTE_DATUM_FIELD = new CDDateField("RenteDatumField", "vul rente datum in");
  private static final CDLabel           TRANSACTIE_TYPE_LABEL = new CDLabel("TransactieTypeLabel", "Soort Transactie:");
  private static final CDTransactionType TRANSACTIE_TYPE_FIELD = new CDTransactionType("TransactieTypeField", 20, "kies transactiesoort");
  
  private Customization              customization         = null;
  private ComponentFactory           componentFactory      = null;
  private SumAccount                 sumAccount            = null;  // Account to which the new transactions shall be added.
  private FinanTransaction           insertLocation        = null;  // Transaction before/after which new transactions shall be inserted. 
  private boolean                    insertBefore;                  // Insert before if true, else insert after.
  private LookAheadComboBox<Bank>    bankSelector          = null;
  private LookAheadComboBox<AccountWrapper> rekeningSelector = null;
  private SpringLayout               layout                = null;
  private TransactionInfo            transactionInfo       = null;
  private FinanTransactionEntryStatus   transactionEntryStatus = new FinanTransactionEntryStatus();

  private JButton                    toevoegenButton       = null;
  private JButton                    klaarButton           = null;
  private JPanel                     transactionPanel      = null;
  private String                     transactionComponentFocusName = "";
  
  // Statusbar
  private JLabel                     statusBar = null;

  public TransactionDialog(AppFrame owner, CompanyPool companyPool, SumAccount sumAccount, FinanTransaction insertLocation, boolean before) {
    super(owner, DIALOG_TITLE);
    customization = getCustomization();
    componentFactory = getTheComponentFactory();
    transactionEntryStatus.setCompanyPool(companyPool);
    this.sumAccount = sumAccount;
    this.insertLocation = insertLocation;
    insertBefore = before;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try  {
      init();
    } catch(Exception e) {
      e.printStackTrace();
    }
    pack();
  }

  /**
   * This method performs the initialization.
   * @throws java.lang.Exception
   */
  private void init() throws Exception  {
    setSize(new Dimension(800, 400));
    Container contentPane = getContentPane();

    /*
     * Main layout:
     * NORTH topPanel
     *     NORTH = textArea with help text
     *     SOUTTH = panel with standard fields of the transaction.
     * CENTER panel with transaction specific items.
     * SOUTH panel with buttons.
     */

    /*
     * Main layout:
     * NORTH = textArea with help text
     * CENTER = mainPanel
     *     NORTH = standardFieldPanel with standard fields of the transaction.
     *     CENTER = transactionPanel with transaction specific items.
     *     SOUTH = panel with 'toevoegen' and 'klaar 'buttons.
     * SOUTH = status bar.
     */

    //
    // Main layout: NORTH
    //     Help text.
    JTextArea ta = componentFactory.createTextArea(
        "Voer de transactie gegevens in en click op \"Toevoegen\" om de" +
        " transactie toe te voegen. Click op \"Klaar\" als er geen transacties" +
        " meer toegevoegd hoeven te worden.",
        true
    );
    contentPane.add(ta, BorderLayout.NORTH);

    //
    // Main layout: CENTER = mainPanel
    //
    JPanel mainPanel = componentFactory.createPanel(-1, -1, true);
    mainPanel.setLayout(new BorderLayout());
    contentPane.add(mainPanel, BorderLayout.CENTER);

    // mainPanel: NORTH
    //    Standaard velden.
    JPanel standardFieldPanel = componentFactory.createPanel(-1, -1, false);
    standardFieldPanel.setLayout(new FlowLayout());

    // Veld om bank te selecteren.
    Bank banks[] = new Bank[sumAccount.getNrOfBanks() + 1];
    int index = 0;
    int defaultBankIndex = -1;
//    banks[index++] = new StubBank("Kies een bank");
    for (FinanBank account: sumAccount.getBanks()) {
      Bank bank = account.getBank();
      if ((defaultBankIndex == -1)  &&
          (FinanRegistry.defaultBank != null)  &&
          bank.getName().equals(FinanRegistry.defaultBank)) {
        defaultBankIndex = index;
      }
      banks[index++] = bank;
    }

    bankSelector = componentFactory.createLookAheadComboBox(banks, true, 10, 20, "Selecteer een bank");
    bankSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bankSelected();
      }
    });
    standardFieldPanel.add(bankSelector);

    // Veld om een rekening te selecteren.
    rekeningSelector = componentFactory.createLookAheadComboBox(INITIAL_REKENINGSELECTOR_DATA, true, 10, 20, "Selecteer een rekening");
//    rekeningSelector.setEnabled(false);
    standardFieldPanel.add(rekeningSelector);

    mainPanel.add(standardFieldPanel, BorderLayout.NORTH);


    // mainPanel: CENTER
    //    Lijst van invoervelden.
    transactionPanel= componentFactory.createPanel(-1, -1, false);
    transactionPanel.setOpaque(true);
    Dimension tpDim = new Dimension(500, 300);
    transactionPanel.setSize(tpDim);
    transactionPanel.setPreferredSize(tpDim);
    layout = new SpringLayout();
    transactionPanel.setLayout(layout);

    mainPanel.add(transactionPanel, BorderLayout.CENTER);

    // mainPanel: SOUTH
    //    'Toevoegen' en 'Klaar' buttons.
    JPanel buttonPanel;
    JPanel buttonsPanel = componentFactory.createPanel(-1, -1, false);
    mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

    buttonPanel = componentFactory.createPanel(-1, -1, false);
    buttonPanel.add(toevoegenButton = createButton("Toevoegen", 't'));
    toevoegenButton.setEnabled(false);
    getRootPane().setDefaultButton(toevoegenButton);
    buttonsPanel.add(buttonPanel, BorderLayout.WEST);

    buttonPanel = componentFactory.createPanel(-1, -1, false);
    buttonPanel.add(klaarButton = createButton("Klaar", 'k'));
    buttonsPanel.add(buttonPanel, BorderLayout.EAST);

    // Main layout: SOUTH
    //     status panel
    JPanel statusPanel = componentFactory.createPanel(500, 22, true);
    statusBar = componentFactory.createLabel("", SwingConstants.LEFT);
    statusPanel.add(statusBar, BorderLayout.SOUTH);
//    statusPanel.setBackground(getCustomization().getLook().getTextFieldBackgroundColor());
    contentPane.add(statusPanel, BorderLayout.SOUTH);
    
    this.pack();
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        cancel();
      }
    });
    if (defaultBankIndex != -1) {
      bankSelector.setSelectedIndex(defaultBankIndex);
    }
    
    bankSelected();
  }

  private void bankSelected() {
//    if (bankSelector.getSelectedIndex() == 0) {
//      transactionEntryStatus.setBank(null);
//      rekeningSelector.setEnabled(false);
//      rekeningSelector.setSelectedIndex(0);
//    } else {
      transactionEntryStatus.setBank((Bank) bankSelector.getSelectedItem());
      rekeningSelector.setEnabled(true);
//    }
    listRekeningen();
  }

  private void listRekeningen() {
    if (transactionEntryStatus.getBank() != null) {
      listRekeningenVoorBank(transactionEntryStatus.getBank());
    } else {
      listGeenRekeningen();
    }

    rekeningSelected();
  }
  

  private void listRekeningenVoorBank(Bank bank) {
    for (ActionListener actionListener: rekeningSelector.getActionListeners()) {
      rekeningSelector.removeActionListener(actionListener);
    }
    rekeningSelector.removeAllItems();

    for (PgAccount account: bank.getAccounts()) {
      rekeningSelector.addItem(new AccountWrapper(account));
    }
    
    String defaultAccount = FinanRegistry.defaultAccountForBank(bank.getName());
    if (defaultAccount != null) {
      for (int i = 0; i < rekeningSelector.getItemCount(); i++) {
        if (rekeningSelector.getItemAt(i).toString().equals(defaultAccount)) {
          rekeningSelector.setSelectedIndex(i);
          break;
        }
      }
    }
    
    rekeningSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rekeningSelected();
      }
     });
  }
  

  private void listGeenRekeningen() {
    rekeningSelector.removeAllItems();

    for (AccountWrapper data: INITIAL_REKENINGSELECTOR_DATA) {
      rekeningSelector.addItem(data);
    }
  }

  private void rekeningSelected() {
    transactionComponentFocusName = "";

    // Doe niets als er geen rekening geselecteerd is.
    if (rekeningSelector.getSelectedItem() == null) {
      return;
    }

    // Bepaal welke rekening geselecteerd is op basis van bank + rekening naam
    if (transactionEntryStatus.setBankAccount(((AccountWrapper) rekeningSelector.getSelectedItem()).getAccount())) {
      handleTransactionFields(true, null);
    }
  }

  private void handleTransactionFields(boolean accountChanged, String eventComponentName){
    TransactionDialogComponentList dialogComponentList = transactionEntryStatus.getTransactionComponents();
    DC<?>         dc;
    transactionComponentFocusName = null;

    if (dialogComponentList.isEmpty()) {
      // No components in the list yet, fill in valuta datum en transactie type.
      // Put focus on valuta datum.
      addTransactionPanelComponent(RENTE_DATUM_LABEL);  // item 0
      addTransactionPanelComponent(RENTE_DATUM_FIELD);  // item 1
      transactionComponentFocusName = RENTE_DATUM_FIELD.getName();

      addTransactionPanelComponent(TRANSACTIE_TYPE_LABEL);  // item 2
      addTransactionPanelComponent(TRANSACTIE_TYPE_FIELD);  // item 3
    } else if (accountChanged) {
      // Remove the transactieTypeField and add it again
      // (to have up to date transaction types).
      DC<? extends Component> transactieTypeField = dialogComponentList.get("TransactieTypeField"); // This one shall exist.
      dialogComponentList.truncateFrom(transactieTypeField);
      addTransactionPanelComponent(TRANSACTIE_TYPE_FIELD);  // item 3
    }

    // Als "TransactieTypeField" een geldige waarde heeft, vul volgende veld in.
    DC<? extends Component> transactieTypeField = dialogComponentList.get("TransactieTypeField"); // This one shall exist.
    transactionInfo = TransactionInfo.getTransactionInfo(transactionEntryStatus.getBank().getName(),
        transactionEntryStatus.getBankAccount().getName(), (String) transactieTypeField.getValue(),
        transactionEntryStatus.getTransactionComponents());
    if (transactionInfo != null) {
      CD[] componentDescriptors = transactionInfo.getComponentDescriptors();
      // Als het volgende item niet bij dit type past, maak lijst verder leeg.
      dc = dialogComponentList.getNext(transactieTypeField);
      for (CD componentDescriptor: componentDescriptors) {
        if (dc == null) {
          break;
        }
        
        if (!componentDescriptor.getName().equals(dc.getName())) {
          dialogComponentList.truncateFrom(dc);
          dc = null;
          break;
        }
        
        dc = dialogComponentList.getNext(dc);
      }
      if (dc != null) {
        dialogComponentList.truncateFrom(dc);
      }
      
      // Add all components.
      dc = dialogComponentList.getNext(transactieTypeField);
      boolean firstNew = true;
      for (CD componentDescriptor: componentDescriptors) {
        if (dc == null) {
          addTransactionPanelComponent(componentDescriptor);
          if (firstNew) {
            if (componentDescriptor.isActive()) {
              transactionComponentFocusName = componentDescriptor.getName();
              firstNew = false;
            }
          }
        } else {
          dc = dialogComponentList.getNext(dc);
        }
      }
      
      handleComponentDependencies(eventComponentName);
      
      setTransactionComponentsStatusHighlights(componentDescriptors);
      
      if (areTransactionComponentsValid(componentDescriptors)) {
        toevoegenButton.setEnabled(true);
      } else {
        toevoegenButton.setEnabled(false);
      }
    }
    
    drawTransactionPanel();
  }
  
  private void handleComponentDependencies(String eventComponentName) {
    TransactionDialogComponentList dialogComponentList = transactionEntryStatus.getTransactionComponents();
    for (DC<? extends Component> dc: dialogComponentList) {
      String dependsOnName = dc.getComponentDescriptor().getDependsOnName();
      if ((dependsOnName != null)  &&  dependsOnName.equals(eventComponentName)) {
        dc.update(transactionEntryStatus);
      }
    }
  }

  private void setTransactionComponentsStatusHighlights(CD[] componentDescriptors) {
    for (DC<? extends Component> dialogComponent: transactionEntryStatus.getTransactionComponents()) {
      dialogComponent.setStatusHighlight();
    }
  }
  
  private boolean areTransactionComponentsValid(CD[] componentDescriptors) {
    DC<? extends Component> transactionComponent;
    TransactionDialogComponentList transactionComponents = transactionEntryStatus.getTransactionComponents();

    transactionComponent = transactionComponents.get(RENTE_DATUM_FIELD.getName());
    if (!transactionComponent.isValid()) {
      return false;
    }

    transactionComponent = transactionComponents.get(TRANSACTIE_TYPE_FIELD.getName());
    if (!transactionComponent.isValid()) {
      return false;
    }

    for (CD cd: componentDescriptors) {
      transactionComponent = transactionComponents.get(cd.getName());
      if (!transactionComponent.isValid()) {
        LOGGER.fine("Invalid component: " + transactionComponent.getName());
        return false;
      }
    }
    return true;
  }

  private void addTransactionPanelComponent(CD componentDescriptor) {
    boolean addListener = true;
    
    if (!componentDescriptor.isActive()) {
      addListener = false;
    }

    DC<? extends Component> dc = componentDescriptor.createComponent(transactionEntryStatus, customization);
    addTransactionPanelComponent(dc, componentDescriptor.getName(), addListener);
    
  }

  private void addTransactionPanelComponent(DC<? extends Component> dc, String name, boolean addListener) {
    if (addListener) {
      dc.getComponent().addFocusListener(this);
    }
    transactionEntryStatus.getTransactionComponents().add(dc);
  }

  private void drawTransactionPanel() {
    transactionPanel.removeAll();

    // Add all components to the _transactionPanel.
    int column = 0;
    int lineOffset = TOP_MARGIN;
    for (DC<? extends Component> dc: transactionEntryStatus.getTransactionComponents()) {
      Component c = dc.getComponent();
      c.setName(dc.getName()); // TODo moet dit hier, nee!
      transactionPanel.add(c);
      if (transactionComponentFocusName != null  &&
          dc.getName().equals(transactionComponentFocusName)) {
        c.requestFocus();
      }

      if (column == 0) {
        layout.putConstraint(SpringLayout.WEST, c,
                              COLUMN_1_OFFSET,
                              SpringLayout.WEST, transactionPanel);
        layout.putConstraint(SpringLayout.NORTH, c,
                              lineOffset,
                              SpringLayout.NORTH, transactionPanel);
      } else {
        layout.putConstraint(SpringLayout.WEST, c,
                              COLUMN_2_OFFSET,
                              SpringLayout.WEST, transactionPanel);
        layout.putConstraint(SpringLayout.NORTH, c,
                              lineOffset,
                              SpringLayout.NORTH, transactionPanel);
      }

      if (column == 1) {
        column = 0;
        lineOffset += LINE_SPACING;
      } else {
        column++;
      }
    }

    transactionPanel.revalidate();
    transactionPanel.repaint();
  }

  private JButton createButton(String name, char mnemonic) {
    JButton b = componentFactory.createButton(name, null);
    b.setMnemonic(mnemonic);
    b.addActionListener(this);

    return b;
  }

  @SuppressWarnings("unchecked")
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == toevoegenButton) {
      apply();
    } else if(e.getSource() == klaarButton) {
      cancel();
    } else {
      handleTransactionFields(false, ((DC<? extends Component>) e.getSource()).getName());
    }
  }

  public void focusGained(FocusEvent e) {
    // for now nothing to be done.
  }

  public void focusLost(FocusEvent e) {
    handleTransactionFields(false, ((Component)e.getSource()).getName());
  }


  @SuppressWarnings("unchecked")
  private void apply() {
    LOGGER.info("=>");
    
    @SuppressWarnings("rawtypes")
    Class constructorClass = transactionInfo.getTransactionCreationClass();
    String transactionCreationMethodName = transactionInfo.getTransactionCreationMethod();
    try {
      Method transactionCreationMethod = constructorClass.getMethod(transactionCreationMethodName, FinanTransactionEntryStatus.class);
      PgTransaction transaction = (PgTransaction) transactionCreationMethod.invoke(null, transactionEntryStatus);
      int transactionIndex = addTransaction(transaction);
      
      statusBar.setText("Transactie " + transactionIndex + " toegevoegd: " + transaction.getDescription());
    } catch (SecurityException e) {  // should never happen.
      e.printStackTrace();
    } catch (NoSuchMethodException e) {  // should never happen.
      e.printStackTrace();
    } catch (IllegalArgumentException e) {  // should never happen.
      e.printStackTrace();
    } catch (IllegalAccessException e) {  // should never happen.
      e.printStackTrace();
    } catch (InvocationTargetException e) {  // should never happen.
      e.printStackTrace();
    }
  }

  private int addTransaction(PgTransaction t) {
    FinanTransaction ft = new FinanTransaction(transactionEntryStatus.getBank(), t);
    
    // Voor het sumAccount moet de transactie op de insert locatie ingevoegd worden.
    sumAccount.addTransaction(ft, insertLocation, insertBefore);

    // TODO De volgende transactie moet voor of na deze nieuwe transactie.
    insertLocation = ft;
    
    return sumAccount.getTransactionIndex(ft);
  }

  private void cancel() {
    this.dispose();
  }

  
  private class AccountWrapper {
    PgAccount   account;
  
    public AccountWrapper(PgAccount account) {
      this.account = account;
    }
    
    protected PgAccount getAccount() {
      return account;
    }

    public String toString() {
      if (account != null) {
        return account.getName();
      } else {
        return INITIAL_REKENINGFIELD_TEXT;
      }
    }
  }
}
