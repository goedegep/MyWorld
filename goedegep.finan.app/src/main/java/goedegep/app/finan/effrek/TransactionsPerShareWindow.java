package goedegep.app.finan.effrek;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import goedegep.app.finan.gen.TransactionTable;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.LookAheadComboBoxWithSpinner;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.basic.PgAccount;
import goedegep.finan.basic.TransactionFilter;
import goedegep.finan.stocks.Fund;
import goedegep.finan.stocks.Share;

@SuppressWarnings("serial")
public class TransactionsPerShareWindow extends AppFrame {
  private final static Logger     LOGGER = Logger.getLogger(TransactionsPerShareWindow.class.getName()); 

  private static final int        COLUMN_1 = 12;
  private static final int        COLUMN_1_SIZE = 190;
  private static final int        COLUMN_2 = COLUMN_1 + COLUMN_1_SIZE;
  private static final int        COLUMN_2_SIZE = 50;
  private static final int        COLUMN_3 = COLUMN_2 + COLUMN_2_SIZE;
  private static final int        COLUMN_3_SIZE = 210;
  private static final int        COLUMN_4 = COLUMN_3 + COLUMN_3_SIZE;
  private static final int        COLUMN_4_SIZE = 190;
  private static final int        COLUMN_5 = COLUMN_4 + COLUMN_4_SIZE;
  private static final int        COLUMN_5_SIZE = 290;
  
  private static final int        ROW_1 = 25;
  private static final int        ROW_2 = 50;
  
  private ComponentFactory        componentFactory;
  private boolean                 listAllFunds;   // if true, selected fund is ignored. All funds are listed.
  private JLabel                  fundLabel;
  private LookAheadComboBoxWithSpinner<String> fundBox;        // for fund selection.
  private Fund                    selectedFund;   // selected item of fundBox.
  private JRadioButton            allSharesButton;
  private JRadioButton            selectedShareButton;
  private LookAheadComboBoxWithSpinner<String> shareBox;       // for share (of selected fund) selection.
  private boolean                 listAllShares;  // if true, selected share is ignored. All shares of selected fund are listed.
  private Share                   selectedShare;  // selected item of shareBox.
  private TransactionTable        transactionTable;

  public TransactionsPerShareWindow(String title, Customization customization, Dimension size, PgAccount account) {
    super(title, customization, size);
    
    componentFactory = getTheComponentFactory();
    
    // Transactions Table
    transactionTable = new TransactionTable(this, account);
    JPanel filterPanel = createFilterPanel((int) size.getWidth());
    getContentPane().add(filterPanel, BorderLayout.NORTH);
    
    getContentPane().add(transactionTable, BorderLayout.CENTER);
  }

  private JPanel createFilterPanel(int width) {
    JPanel selectionPanel = componentFactory.createPanel(width, 75, true);
    SpringLayout layout = new SpringLayout();
    selectionPanel.setLayout(layout);
    
    // row 1 and 2, column 1: ButtonGroup with buttons for 'all funds' or 'selected fund'.
    ButtonGroup  allOrSelectedFundGroup = new ButtonGroup();
    JRadioButton allFundsButton = componentFactory.createRadioButton("Alle fondsen", true);
    listAllFunds = true;
    allOrSelectedFundGroup.add(allFundsButton);
    JPanel panel = componentFactory.createPanel(COLUMN_1_SIZE - 20, 18, false);
    panel.add(allFundsButton, BorderLayout.CENTER);
    selectionPanel.add(panel);
    layout.putConstraint(SpringLayout.WEST, panel,
        COLUMN_1,
        SpringLayout.WEST, selectionPanel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, panel,
        ROW_1,
        SpringLayout.NORTH, selectionPanel);

    JRadioButton selectedFundButton = componentFactory.createRadioButton("Geselecteerd fonds", false);
    allOrSelectedFundGroup.add(selectedFundButton);
    panel = componentFactory.createPanel(COLUMN_1_SIZE - 20, 18, false);
    panel.add(selectedFundButton, BorderLayout.CENTER);
    selectionPanel.add(panel);
    layout.putConstraint(SpringLayout.WEST, panel,
        COLUMN_1,
        SpringLayout.WEST, selectionPanel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, panel,
        ROW_2,
        SpringLayout.NORTH, selectionPanel);
        
    // row 2, column 2: Fonds label
    fundLabel = componentFactory.createLabel("Fonds:", SwingConstants.LEFT);
    selectionPanel.add(fundLabel);
    layout.putConstraint(SpringLayout.WEST, fundLabel,
        COLUMN_2,
        SpringLayout.WEST, selectionPanel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, fundLabel,
        ROW_2,
        SpringLayout.NORTH, selectionPanel);
    
    // row 2, column 3: Fonds selectie box
    createFundBox();
    panel = componentFactory.createPanel(COLUMN_3_SIZE - 20, 25, false);
    panel.add(fundBox, BorderLayout.CENTER);
    selectionPanel.add(panel);
    layout.putConstraint(SpringLayout.WEST, panel,
        COLUMN_3,
        SpringLayout.WEST, selectionPanel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, panel,
        ROW_2,
        SpringLayout.NORTH, selectionPanel);

    // row 1 and 2, column 4: ButtonGroup with buttons for 'all shares' or 'selected share'.
    ButtonGroup  allOrSelectedShareGroup = new ButtonGroup();
    allSharesButton = componentFactory.createRadioButton("Alle aandelen", true);
    listAllShares = true;
    allOrSelectedShareGroup.add(allSharesButton);
    panel = componentFactory.createPanel(COLUMN_4_SIZE - 20, 18, false);
    panel.add(allSharesButton, BorderLayout.CENTER);
    selectionPanel.add(panel);
    layout.putConstraint(SpringLayout.WEST, panel,
        COLUMN_4,
        SpringLayout.WEST, selectionPanel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, panel,
        ROW_1,
        SpringLayout.NORTH, selectionPanel);

    selectedShareButton = componentFactory.createRadioButton("Geselecteerd aandeel", false);
    allOrSelectedShareGroup.add(selectedShareButton);
    panel = componentFactory.createPanel(COLUMN_4_SIZE - 20, 18, false);
    panel.add(selectedShareButton, BorderLayout.CENTER);
    selectionPanel.add(panel);
    layout.putConstraint(SpringLayout.WEST, panel,
        COLUMN_4,
        SpringLayout.WEST, selectionPanel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, panel,
        ROW_2,
        SpringLayout.NORTH, selectionPanel);

    // row 2, column 5: Aandeel selectie box
    shareBox = componentFactory.createLookAheadComboBoxWithSpinner(null, false, 10, 20, "selecteer aandeel");
    fillShareBox();
    panel = componentFactory.createPanel(COLUMN_5_SIZE - 20, 25, false);
    panel.add(shareBox, BorderLayout.CENTER);
    selectionPanel.add(panel);
    layout.putConstraint(SpringLayout.WEST, panel,
        COLUMN_5,
        SpringLayout.WEST, selectionPanel);
    layout.putConstraint(SpringLayout.VERTICAL_CENTER, panel,
        ROW_2,
        SpringLayout.NORTH, selectionPanel);

    updateFundVisibilityAndFilter();
    
    //
    // Add action listeners.
    //
    
    allFundsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        listAllFunds = true;
        updateFundVisibilityAndFilter();
      }
    });
    
    selectedFundButton.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
       listAllFunds = false;
       updateFundVisibilityAndFilter();
      }
    });
    
    fundBox.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        handleNewFundSelection();
      }
      
    });
    
    allSharesButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        listAllShares = true;
        updateShareVisibility();
      }
    });
    
    selectedShareButton.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
       listAllShares = false;
       updateShareVisibility();
      }
    });
    
    addShareBoxActionListener();
    
    return selectionPanel;
  }
  
  private void addShareBoxActionListener() {
    shareBox.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        updateShareSelection();
      }
    });
  }

  private void createFundBox() {
    List<String> fundNames = new ArrayList<>();
    for  (Fund fund: Fund.getFunds()) {
      fundNames.add(fund.getName());
    }
    
    fundBox = componentFactory.createLookAheadComboBoxWithSpinner(fundNames, false, 10, 20, "selecteer fonds");
    
//    for  (Fund fund: Fund.getFunds()) {
//      fundBox.addItem(fund.getName());
//    }
//    fundBox.setSelectedIndex(0);

    List<String> items = fundBox.getItems();
    for (String item: items) {
      LOGGER.severe("Aandeel naam: " + item);
    }
    Object selectedItem = fundBox.getValue();
    LOGGER.severe("Selected: " + selectedItem);
    String fundName = (String) fundBox.getValue();
    selectedFund = Fund.getFund(fundName);
  }

  private void handleNewFundSelection() {
    String fundName = (String) fundBox.getValue();
    Fund fund = Fund.getFund(fundName);
    if (selectedFund == null  ||  !fund.equals(selectedFund)) {
      selectedFund = fund;
      fillShareBox();
      updateFilter();
    }
  }

  private void fillShareBox() {
    for (ChangeListener changeListener: shareBox.getChangeListeners()) {
      shareBox.removeChangeListener(changeListener);
    }
    
    List<String> spinnerItems = new ArrayList<>();
    
    LOGGER.fine("selectedFund: " + selectedFund.getName());
    for (Share share: selectedFund.getShares()) {
      spinnerItems.add(share.getName());
      LOGGER.fine("adding share: " + share.getName());
    }
    shareBox.setValues(spinnerItems);
    if (selectedShare != null  &&  selectedShare.getFund().equals(selectedFund)) {
      shareBox.setValue(selectedShare.getName());
    } else {
      shareBox.setValue(spinnerItems.get(0));
    }

    String shareName = (String) shareBox.getValue();
    selectedShare = Share.getShare(shareName);
    
    updateFilter();
    addShareBoxActionListener();
  }

  public void updateShareSelection() {
    String shareName = (String) shareBox.getValue();
    selectedShare = Share.getShare(shareName);

    updateFilter();
  }
  
  private void updateFundVisibilityAndFilter() {
    if (listAllFunds) {
      // Hide all other items.
      fundLabel.setVisible(false);
      fundBox.setVisible(false);
      allSharesButton.setVisible(false);
      selectedShareButton.setVisible(false);
    } else {
      // Show Fund selection items, update share selection items.
      fundLabel.setVisible(true);
      fundBox.setVisible(true);
      allSharesButton.setVisible(true);
      selectedShareButton.setVisible(true);
    }
    
    updateShareVisibility();
  }
  
  private void updateShareVisibility(){
    if (listAllShares) {
      // Hide share selection.
      shareBox.setVisible(false);
    } else {
      shareBox.setVisible(true);
    }
    
    updateFilter();
  }
  
  private void updateFilter() {
    TransactionFilter filter;
    if (listAllFunds) {
      filter = new FundFilter((Fund) null);
    } else {
      if (listAllShares) {
        filter = new FundFilter(selectedFund);
      } else {
        filter = new FundFilter(selectedShare);
      }
    }
    
    List<TransactionFilter> filters = new ArrayList<TransactionFilter>();
    filters.add(filter);
    
    transactionTable.setFilters(filters);
  }
}
