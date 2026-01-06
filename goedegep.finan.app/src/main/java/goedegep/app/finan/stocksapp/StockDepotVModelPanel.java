package goedegep.app.finan.stocksapp;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import goedegep.appgen.LookAheadComboBoxWithSpinner;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppPanel;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.StockDepot;
import goedegep.finan.stocks.StockPosition;
import goedegep.finan.stocks.StockVModelStatus;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

@SuppressWarnings("serial")
public class StockDepotVModelPanel extends AppPanel implements ChangeListener {
  private final static Logger     LOGGER = Logger.getLogger(StockDepotVModelPanel.class.getName());

  private static final int        FIRST_COLUMN = 23;
  private static final int        SECOND_COLUMN = 145;
  private static final int        THIRD_COLUMN = 275;
  private static final int        FOURTH_COLUMN = 400;
  private static final int        FIFTH_COLUMN = 550;
  private static final int        SIXTH_COLUMN = 680;
  private static final int        TOP_MARGIN = 12;
  private static final int        ROW_DISTANCE = 28;
  private static final int        VERTICAL_SEPARATION_GAP = 10;

  private static final PgCurrencyFormat cf = new PgCurrencyFormat();
  
  private ComponentFactory componentFactory;
  private StockDepot       stockDepot;
  private LookAheadComboBoxWithSpinner<String> shareSelectionSpinner = null;
  private JTextField       numberOfShares = null;
  private JTextField       investmentField = null;
  private JTextField       averageRateField = null;
  private JTextField       totalProfitField = null;
  private JTextField       fivePercentBuyAdviceField = null;
  private JTextField       threePercentBuyAdviceField = null;
  private JTextField       twoPercentBuyAdviceField = null;
  
  // Tables for Buy-Sell combo's and Buys.
  StockBuySellComboTable stockBuySellComboTable = null;
  StockBuysTable         stockBuysTable = null;
  
  public StockDepotVModelPanel(AppFrame owner, StockDepot stockDepot, Share selectedShare) {
    super(owner, null);
    this.stockDepot = stockDepot;
    
    componentFactory = getTheComponentFactory();
    
    init(selectedShare);
  }
  
  private void init(Share selectedShare) {
    // Set the size.
    Dimension dim = new Dimension(800, 600);
    setMinimumSize(dim);
    setPreferredSize(dim);
    
    // Use SpringLayout.
    SpringLayout layout = new SpringLayout();
    setLayout(layout);
    
//    setBackground(getLook().getPanelBackgroundColor());
    
    // First line: Label + JSpinner to select a share. JSpinner created later.
    JLabel effectLabel = componentFactory.createLabel("Effect:", SwingConstants.LEFT);
    add(effectLabel);
    layout.putConstraint(SpringLayout.WEST, effectLabel,
        FIRST_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, effectLabel,
        TOP_MARGIN,
        SpringLayout.NORTH, this);
    
    // First line: Label for nr. of shares + textfield value.
    JLabel NumberOfSharesLabel = componentFactory.createLabel("Aantal aandelen:", SwingConstants.LEFT);
    add(NumberOfSharesLabel);
    layout.putConstraint(SpringLayout.WEST, NumberOfSharesLabel,
        FIFTH_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, NumberOfSharesLabel,
        TOP_MARGIN,
        SpringLayout.NORTH, this);
    
    numberOfShares = componentFactory.createTextField(7, null);
    numberOfShares.setEditable(false);
    add(numberOfShares);
    layout.putConstraint(SpringLayout.WEST, numberOfShares,
        SIXTH_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, numberOfShares,
        TOP_MARGIN - 4,
        SpringLayout.NORTH, this);
    
    // Second line: Label for investment + textfield value.
    JLabel investmentLabel = componentFactory.createLabel("Investering:", SwingConstants.LEFT);
    add(investmentLabel);
    layout.putConstraint(SpringLayout.WEST, investmentLabel,
        FIRST_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, investmentLabel,
        ROW_DISTANCE,
        SpringLayout.NORTH, effectLabel);
    
    investmentField = componentFactory.createTextField(9, null);
    investmentField.setEditable(false);
    add(investmentField);
    layout.putConstraint(SpringLayout.WEST, investmentField,
        SECOND_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, investmentField,
        ROW_DISTANCE - 4,
        SpringLayout.NORTH, effectLabel);
    
    // Second line: Label for average rate + textfield value.
    JLabel averageRateLabel = componentFactory.createLabel("Gemiddelde koers:", SwingConstants.LEFT);
    add(averageRateLabel);
    layout.putConstraint(SpringLayout.WEST, averageRateLabel,
        THIRD_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, averageRateLabel,
        ROW_DISTANCE,
        SpringLayout.NORTH, effectLabel);
    
    averageRateField = componentFactory.createTextField(9, null);
    averageRateField.setEditable(false);
    add(averageRateField);
    layout.putConstraint(SpringLayout.WEST, averageRateField,
        FOURTH_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, averageRateField,
        ROW_DISTANCE - 4,
        SpringLayout.NORTH, effectLabel);
    
    
    // Second line: Label for total profit + textfield value.
    JLabel totalProfitLabel = componentFactory.createLabel("Totale winst:", SwingConstants.LEFT);
    add(totalProfitLabel);
    layout.putConstraint(SpringLayout.WEST, totalProfitLabel,
        FIFTH_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, totalProfitLabel,
        ROW_DISTANCE,
        SpringLayout.NORTH, effectLabel);
    
    totalProfitField = componentFactory.createTextField(9, null);
    totalProfitField.setEditable(false);
    add(totalProfitField);
    layout.putConstraint(SpringLayout.WEST, totalProfitField,
        SIXTH_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, totalProfitField,
        ROW_DISTANCE - 4,
        SpringLayout.NORTH, effectLabel);
    
    // SplitPane with buy-sell combo table on top and below the buys.
    stockBuySellComboTable = new StockBuySellComboTable(getOwner(), null);
    stockBuysTable = new StockBuysTable(getOwner(), null);
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, stockBuySellComboTable, stockBuysTable);
    add(splitPane);
    layout.putConstraint(SpringLayout.WEST, splitPane,
        FIRST_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, splitPane,
        ROW_DISTANCE,
        SpringLayout.NORTH, investmentLabel);
    
    // Bottom line: Label for 5% buy advice + textfield value.
    JLabel fivePercentBuyAdviceLabel = componentFactory.createLabel("Bijkopen 5%:", SwingConstants.LEFT);
    add(fivePercentBuyAdviceLabel);
    layout.putConstraint(SpringLayout.WEST, fivePercentBuyAdviceLabel,
        FIRST_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, fivePercentBuyAdviceLabel,
        VERTICAL_SEPARATION_GAP,
        SpringLayout.SOUTH, splitPane);
    
    fivePercentBuyAdviceField = componentFactory.createTextField(9, null);
    fivePercentBuyAdviceField.setEditable(false);
    add(fivePercentBuyAdviceField);
    layout.putConstraint(SpringLayout.WEST, fivePercentBuyAdviceField,
        SECOND_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, fivePercentBuyAdviceField,
        VERTICAL_SEPARATION_GAP - 4,
        SpringLayout.SOUTH, splitPane);
    
    // Bottom line: Label for 3% buy advice + textfield value.
    JLabel threePercentBuyAdviceLabel = componentFactory.createLabel("Bijkopen 3%:", SwingConstants.LEFT);
    add(threePercentBuyAdviceLabel);
    layout.putConstraint(SpringLayout.WEST, threePercentBuyAdviceLabel,
        THIRD_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, threePercentBuyAdviceLabel,
        VERTICAL_SEPARATION_GAP,
        SpringLayout.SOUTH, splitPane);
    
    threePercentBuyAdviceField = componentFactory.createTextField(9, null);
    threePercentBuyAdviceField.setEditable(false);
    add(threePercentBuyAdviceField);
    layout.putConstraint(SpringLayout.WEST, threePercentBuyAdviceField,
        FOURTH_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, threePercentBuyAdviceField,
        VERTICAL_SEPARATION_GAP - 4,
        SpringLayout.SOUTH, splitPane);
    
    // Bottom line: Label for 2% buy advice + textfield value.
    JLabel twoPercentBuyAdviceLabel = componentFactory.createLabel("Bijkopen 2%:", SwingConstants.LEFT);
    add(twoPercentBuyAdviceLabel);
    layout.putConstraint(SpringLayout.WEST, twoPercentBuyAdviceLabel,
        FIFTH_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, twoPercentBuyAdviceLabel,
        VERTICAL_SEPARATION_GAP,
        SpringLayout.SOUTH, splitPane);
    
    twoPercentBuyAdviceField = componentFactory.createTextField(9, null);
    twoPercentBuyAdviceField.setEditable(false);
    add(twoPercentBuyAdviceField);
    layout.putConstraint(SpringLayout.WEST, twoPercentBuyAdviceField,
        SIXTH_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, twoPercentBuyAdviceField,
        VERTICAL_SEPARATION_GAP - 4,
        SpringLayout.SOUTH, splitPane);

    
    // Create the spinner as last, to directly react to its value.
    createShareSelectionSpinner(selectedShare);
    add(shareSelectionSpinner);
    layout.putConstraint(SpringLayout.WEST, shareSelectionSpinner,
        SECOND_COLUMN,
        SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, shareSelectionSpinner,
        TOP_MARGIN - 4,
        SpringLayout.NORTH, this);
  }
  
  private void createShareSelectionSpinner(Share selectedShare) {
    List<String> shareNames = stockDepot.getStockPositionsShareNames();
    shareSelectionSpinner = new LookAheadComboBoxWithSpinner<>(shareNames, false, 25, 30, "Kies een aandeel");
    shareSelectionSpinner.addChangeListener(this);
    
    // If there's only one item in the list, we never get a change event.
    if (shareNames.size() == 1) {
      ChangeEvent changeEvent = new ChangeEvent(shareSelectionSpinner);
      stateChanged(changeEvent);
    }
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    LOGGER.severe("=>");
    if (e.getSource() == shareSelectionSpinner) {
      String shareName = (String) shareSelectionSpinner.getValue();
      LOGGER.severe("shareName=" + shareName);
      Share share = Share.getShare(shareName);
      if (share != null) {
        StockPosition stockPosition = stockDepot.getStockPosition(share);
        int amount = stockPosition.getCurrentAmount();
        numberOfShares.setText(String.valueOf(amount));
        PgCurrency investment = stockPosition.getInvestment();
        investmentField.setText(cf.format(investment));
        if (amount > 0) {
          PgCurrency avarageRate = investment.divide(amount);
          averageRateField.setText(cf.format(avarageRate));
        } else {
          averageRateField.setText("-");          
        }
        StockVModelStatus status = stockPosition.getVModelStatus();
        if (status != null) {
        totalProfitField.setText(cf.format(status.getTotalBuySellProfit()));
        } else {
          totalProfitField.setText("-");          
        }
        stockBuySellComboTable.setBuySellCombos(status != null ? status.getBuySellCombos() : new ArrayList<>());
        stockBuysTable.setBuys(status.getBuys());
        PgCurrency lastBuyRate = null;
        if (status.getBuys().size() != 0) {
          lastBuyRate = status.getBuys().get(status.getBuys().size() - 1).getRate();
        } else {
          lastBuyRate = status.getBuySellCombos().get(status.getBuySellCombos().size() - 1).getBuyRate();
        }
        if (lastBuyRate != null) {
          fivePercentBuyAdviceField.setText(cf.format(lastBuyRate.multiply(0.95)));
          threePercentBuyAdviceField.setText(cf.format(lastBuyRate.multiply(0.97)));
          twoPercentBuyAdviceField.setText(cf.format(lastBuyRate.multiply(0.98)));
        } else {
          fivePercentBuyAdviceField.setText("-");
          threePercentBuyAdviceField.setText("-");
          twoPercentBuyAdviceField.setText("-");
        }
      } else {
        numberOfShares.setText("-");
        investmentField.setText("-");
        averageRateField.setText("-");          
        totalProfitField.setText("-");
        stockBuySellComboTable.setBuySellCombos(null);
        stockBuysTable.setBuys(null);
        fivePercentBuyAdviceField.setText("-");
        threePercentBuyAdviceField.setText("-");
        twoPercentBuyAdviceField.setText("-");
      }
    }
    
    LOGGER.severe("<=");
  }
}
