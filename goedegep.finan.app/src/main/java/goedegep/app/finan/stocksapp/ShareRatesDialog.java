package goedegep.app.finan.stocksapp;

import goedegep.appgen.SpringLayoutPanel;
import goedegep.appgen.swing.AppDialog;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.stocks.Fund;
import goedegep.finan.stocks.RateType;
import goedegep.finan.stocks.Share;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ShareRatesDialog extends AppDialog {
  
  private final static String              TITLE = "Aandelen koersen";
  private final static int                 COLUMN_1_LABEL_WIDTH = 77;
  private final static int                 COLUMN_2_BOX_WIDTH = 250;
  private final static int                 COLUMN_3_BUTTON_WIDTH = 160;
  private final static int                 COLUMN_4_BOX_WIDTH = 250;
  private final static int                 ROW_HEIGHT = 20;

  private final static int                 COLUMN_GAP = 15;
  private final static int                 COLUMN_1 = 9;
  private final static int                 COLUMN_2 = COLUMN_1 + COLUMN_1_LABEL_WIDTH;
  private final static int                 COLUMN_3 = COLUMN_2 + COLUMN_2_BOX_WIDTH + COLUMN_GAP;
  private final static int                 COLUMN_4 = COLUMN_3 + COLUMN_3_BUTTON_WIDTH + 5;
  private final static int                 ROW_GAP = 4;
  private final static int                 ROW_1 = 15;
  private final static int                 ROW_2 = ROW_1 + ROW_HEIGHT + ROW_GAP;
  
  private JComboBox<String>           fundBox;              // for fund selection.
  private Fund                        selectedFund;         // selected item of fundBox.
  private JRadioButton                allSharesButton;      // Show rates for all shares of 'selectedFund'.
  private JRadioButton                selectedShareButton;  // Only show rates for 'selectedShare'of 'selectedFund'.
  private JComboBox<String>           shareBox;             // for share (of selected fund) selection.
  private Share                       selectedShare;        // selected item of shareBox.
  private JComboBox<RateType>         rateTypeBox;          // for rate type selection.
  private boolean                     listAllShares;        // if true, selected share is ignored. All shares of selected fund are listed.
  private ShareRatesTable             shareRatesTable;

  public ShareRatesDialog(AppFrame parent, Fund selectedFund, Share selectedShare) {
    super(parent, TITLE);
    JPanel mainPanel = getTheComponentFactory().createPanel(-1, -1, false);

    mainPanel.add(createSelectionPanel(), BorderLayout.NORTH);

    // Table
    shareRatesTable = new ShareRatesTable(parent);
    mainPanel.add(shareRatesTable, BorderLayout.SOUTH);

    setContentPane(mainPanel);

    updateRatesTable();

    // Add action listeners.
    fundBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleNewFundSelection();
      }
    });
    
    shareBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateShareSelection();
      }
    });
    
    allSharesButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        shareBox.setVisible(false);
        listAllShares = true;
        updateRatesTable();
      }
    });
    
    selectedShareButton.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
       shareBox.setVisible(true);
       listAllShares = false;
       updateShareSelection();
      }
    });
    
    rateTypeBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateRatesTable();
      }
    });

    pack();
  }
  
  private JPanel createSelectionPanel() {
    ComponentFactory componentFactory = getTheComponentFactory();
    
    // Area for Fund and Share selection
    SpringLayoutPanel selectionPanel = componentFactory.createSpringLayoutPanel(800, 57, true);
    JLabel label;
    JPanel panel;
    // row 1, column 1: Fonds label
    label = componentFactory.createLabel("Fonds:", SwingConstants.LEFT);
    selectionPanel.addComponentUsingOffsets(label, ROW_1, COLUMN_1);
    
    // row 1, column 2: Fonds selectie box
    createFundBox(selectedFund);
    panel = componentFactory.createPanel(COLUMN_2_BOX_WIDTH, ROW_HEIGHT, false);
    panel.add(fundBox, BorderLayout.CENTER);
    selectionPanel.addComponentUsingOffsets(panel, ROW_1, COLUMN_2);

    ButtonGroup  allOrSelectedShareGroup = new ButtonGroup();
    // row 1, column 3: Alle aandelen radio button
    allSharesButton = componentFactory.createRadioButton("Alle aandelen", true);
    listAllShares = true;
    allOrSelectedShareGroup.add(allSharesButton);
    panel = componentFactory.createPanel(COLUMN_3_BUTTON_WIDTH, ROW_HEIGHT + 4, false);
    panel.add(allSharesButton, BorderLayout.CENTER);
    selectionPanel.addComponentUsingOffsets(panel, ROW_1, COLUMN_3);

    // row 2, column 1: "Soort koers" label
    label = componentFactory.createLabel("Soort koers:", SwingConstants.LEFT);
    selectionPanel.addComponentUsingOffsets(label, ROW_2, COLUMN_1);

    // row 2, column 2: Soort koers selectie box
    rateTypeBox = componentFactory.createComboBox(3, "selecteer koerstype");
    for (RateType rateType: RateType.values()) {
      rateTypeBox.addItem(rateType);
    }
    panel = componentFactory.createPanel(COLUMN_2_BOX_WIDTH, ROW_HEIGHT, false);
    panel.add(rateTypeBox, BorderLayout.CENTER);
    selectionPanel.addComponentUsingOffsets(panel, ROW_2, COLUMN_2);

    // row 2, column 3: Geselecteerd aandeel radio button
    selectedShareButton = componentFactory.createRadioButton("Geselecteerd aandeel:", false);
    allOrSelectedShareGroup.add(selectedShareButton);
    panel = componentFactory.createPanel(COLUMN_3_BUTTON_WIDTH, ROW_HEIGHT + 4, false);
    panel.add(selectedShareButton, BorderLayout.CENTER);
    selectionPanel.addComponentUsingOffsets(panel, ROW_2, COLUMN_3);

    // row 2, column 4: Aandeel selectie box
    shareBox = componentFactory.createComboBox(10, "selecteer aandeel");
    fillShareBox(selectedShare);
    panel = componentFactory.createPanel(COLUMN_4_BOX_WIDTH, ROW_HEIGHT, false);
    panel.add(shareBox, BorderLayout.CENTER);
    shareBox.setVisible(!listAllShares);
    selectionPanel.addComponentUsingOffsets(panel, ROW_2, COLUMN_4);
    
    return selectionPanel;
  }

  private void createFundBox(Fund initiallySelectedFund) {
    fundBox = getTheComponentFactory().createComboBox(10, "selecteer fonds");
    
    for  (Fund fund: Fund.getFunds()) {
      fundBox.addItem(fund.getName());
    }
    if (initiallySelectedFund != null) {
      fundBox.setSelectedItem(initiallySelectedFund.getName());
    }

    String fundName = (String) fundBox.getSelectedItem();
    selectedFund = Fund.getFund(fundName);
  }

  private void fillShareBox(Share initiallySelectedShare) {
    shareBox.removeAllItems();
    Iterator<Share>  shareIterator = selectedFund.getShares().iterator();
    while (shareIterator.hasNext()) {
      Share share = shareIterator.next();
      shareBox.addItem(share.getName());
    }
    if (initiallySelectedShare != null) {
      shareBox.setSelectedItem(initiallySelectedShare.getName());
    }

    String shareName = (String) shareBox.getSelectedItem();
    selectedShare = Share.getShare(shareName);
  }

  private void handleNewFundSelection() {
    String fundName = (String) fundBox.getSelectedItem();
    Fund fund = Fund.getFund(fundName);
    if (selectedFund == null  ||  !fund.equals(selectedFund)) {
      selectedFund = fund;
      fillShareBox(null);
    }
  }

  public void updateShareSelection() {
    String shareName = (String) shareBox.getSelectedItem();
    selectedShare = Share.getShare(shareName);

    updateRatesTable();
  }

  private void updateRatesTable() {
    if (listAllShares) {
      shareRatesTable.setParameters(selectedFund, null, (RateType) rateTypeBox.getSelectedItem());
    } else {
      shareRatesTable.setParameters(selectedFund, selectedShare, (RateType) rateTypeBox.getSelectedItem());
    }
  }
}
