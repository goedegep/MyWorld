package goedegep.app.finan.stocksapp;

import goedegep.appgen.SpringLayoutPanel;
import goedegep.appgen.swing.AppDialog;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.stocks.OptionSerie;
import goedegep.finan.stocks.RateType;
import goedegep.finan.stocks.Share;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class OptionRatesDialog  extends AppDialog {
  
  private final static String         TITLE = "Optie koersen";
  private final static int            COLUMN_1_LABEL_WIDTH = 77;
  private final static int            COLUMN_2_BOX_WIDTH = 250;
  private final static int            COLUMN_3_LABEL_WIDTH = 160;
  private final static int            COLUMN_4_BOX_WIDTH = 250;
  private final static int            ROW_HEIGHT = 20;

  private final static int            COLUMN_GAP = 15;
  private final static int            COLUMN_1 = 9;
  private final static int            COLUMN_2 = COLUMN_1 + COLUMN_1_LABEL_WIDTH;
  private final static int            COLUMN_3 = COLUMN_2 + COLUMN_2_BOX_WIDTH + COLUMN_GAP;
  private final static int            COLUMN_4 = COLUMN_3 + COLUMN_3_LABEL_WIDTH + 5;
  private final static int            ROW_GAP = 4;
  private final static int            ROW_1 = 15;
  private final static int            ROW_2 = ROW_1 + ROW_HEIGHT + ROW_GAP;
  
  private JComboBox<String>           shareBox;             // for share selection.
  private Share                       selectedShare;        // selected item of shareBox.
  private JComboBox<String>           optionSeriesBox;      // for option series selection (for selected share).
  private OptionSerie                 selectedOptionSeries; // selected item of optionSeriesBox.
  private JComboBox<RateType>         rateTypeBox;          // for rate type selection.
  private OptionRatesTable            optionRatesTable;

  public OptionRatesDialog(AppFrame parent, Share selectedShare) {
    super(parent, TITLE);
    System.out.println("=> OptionRatesDialog");
    this.selectedShare = selectedShare;
    
    JPanel mainPanel = getTheComponentFactory().createPanel(-1, -1, false);

    mainPanel.add(createSelectionPanel(), BorderLayout.NORTH);

    // Table
    optionRatesTable = new OptionRatesTable(parent);
    mainPanel.add(optionRatesTable, BorderLayout.SOUTH);

    setContentPane(mainPanel);

    updateRatesTable();
    
    // Add action listeners.
    shareBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleNewShareSelection();
      }
    });
    
    optionSeriesBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateOptionSeriesSelection();
      }
    });
        
    pack();
    System.out.println("<= OptionRatesDialog");
  }
  
  private JPanel createSelectionPanel() {
    System.out.println("=> createSelectionPanel()");
    ComponentFactory componentFactory = getTheComponentFactory();
    
    // Area for Fund and Share selection
    SpringLayoutPanel selectionPanel = componentFactory.createSpringLayoutPanel(800, 57, true);
    JLabel label;
    JPanel panel;
    
    // row 1, column 1: Aandeel label
    label = componentFactory.createLabel("Aandeel:", SwingConstants.LEFT);
    selectionPanel.addComponentUsingOffsets(label, ROW_1, COLUMN_1);
    
    // row 1, column 2: Aandeel selectie box
    createShareBox();
    panel = componentFactory.createPanel(COLUMN_2_BOX_WIDTH, ROW_HEIGHT, false);
    panel.add(shareBox, BorderLayout.CENTER);
    selectionPanel.addComponentUsingOffsets(panel, ROW_1, COLUMN_2);
    
    // row 1, column 3: Optie serie label
    label = componentFactory.createLabel("Optie serie:", SwingConstants.LEFT);
    selectionPanel.addComponentUsingOffsets(label, ROW_1, COLUMN_3);
    
    // row 1, column 4: Aandeel selectie box
    optionSeriesBox = componentFactory.createComboBox(10, "selecteer optie serie");
    fillOptionSeriesBox();
    panel = componentFactory.createPanel(COLUMN_4_BOX_WIDTH, ROW_HEIGHT, false);
    panel.add(optionSeriesBox, BorderLayout.CENTER);
    selectionPanel.addComponentUsingOffsets(panel, ROW_1, COLUMN_4);

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
    
    System.out.println("<= createSelectionPanel()");
    return selectionPanel;
  }

  private void createShareBox() {
    System.out.println("=> createShareBox()");
    ComponentFactory componentFactory = getTheComponentFactory();
    
    shareBox = componentFactory.createComboBox(10, "selecteer aandeel");
    
    for (Share share: Share.getShares()) {
      shareBox.addItem(share.getName());
    }
    
    if (selectedShare != null) {
      shareBox.setSelectedItem(selectedShare.getName());
    }
    
    System.out.println("<= createShareBox()");
  }
  
  private void fillOptionSeriesBox() {
    System.out.println("=> fillOptionSeriesBox()");
    
    optionSeriesBox.removeAllItems();
    
    for (OptionSerie optionSerie: OptionSerie.getOptionSeries()) {
      if (optionSerie.getShare().equals(selectedShare)) {
        optionSeriesBox.addItem(optionSerie.toString());
      }
    }
    
    if (selectedOptionSeries != null) {
      optionSeriesBox.setSelectedItem(selectedOptionSeries.toString());
    }
    
    String optionSeriesName = (String) optionSeriesBox.getSelectedItem();
    selectedOptionSeries = OptionSerie.getOptionSerie(optionSeriesName);
    
    System.out.println("<= fillOptionSeriesBox()");
  }

  private void handleNewShareSelection() {
    System.out.println("=> handleNewShareSelection()");
    
    String shareName = (String) shareBox.getSelectedItem();
    Share share = Share.getShare(shareName);
    
    if (selectedShare == null  ||  !share.equals(selectedShare)) {  // Only take action if something has really changed.
      selectedShare = share;
      fillOptionSeriesBox();
    }
    
    System.out.println("<= handleNewShareSelection()");
  }

  public void updateOptionSeriesSelection() {
    System.out.println("=> updateOptionSeriesSelection()");
    
    String optionSeriesName = (String) optionSeriesBox.getSelectedItem();
    selectedOptionSeries = OptionSerie.getOptionSerie(optionSeriesName);
    
    updateRatesTable();
    
    System.out.println("<= updateOptionSeriesSelection()");
  }
  
  private void updateRatesTable() {
    System.out.println("=> updateRatesTable()");
    
    optionRatesTable.setParameters(selectedOptionSeries, (RateType) rateTypeBox.getSelectedItem());
    
    System.out.println("<= updateRatesTable()");
  }
  
}
