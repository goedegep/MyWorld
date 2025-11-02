package goedegep.app.finan.effrek;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import goedegep.app.finan.gen.FinanBank;
import goedegep.app.finan.registry.FinanRegistry;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.LookAheadComboBox;
import goedegep.appgen.MessageDialogType;
import goedegep.appgen.WindowUtil;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.appgen.swing.MessageDialog;
import goedegep.finan.stocks.StockDepot;
import goedegep.finan.stocks.StockDepotPeriodicReport;
import goedegep.util.datetime.Quarter;

@SuppressWarnings("serial")
public class EffRekKwartaaloverzichtWindow extends AppFrame {
  private final static Logger     LOGGER = Logger.getLogger(EffRekKwartaaloverzichtWindow.class.getName());
  private static final String     WINDOW_TITLE = "Effectenrekening Kwartaaloverzicht";

  private static final int        LEFT_MARGIN = 23;    // is also first column
  private static final int        SECOND_COLUMN = 150;
  private static final int        THIRD_COLUMN = 250;
  private static final int        FOURTH_COLUMN = 350;
  private static final int        TOP_MARGIN = 12;
  private static final int        VERTICAL_SEPARATION_GAP = 8;
  
  private static final String     JAAR_TOOLTIP = "Vul het jaar in waarvoor het overzicht weergegeven moet worden.";
  private static final String     KWARTAAL_TOOLTIP = "Vul het kwartaal (1 t/m 4) in waarvoor het overzicht weergegeven moet worden.";
  
  private FinanRegistry           finanRegistry;
  private StockDepot              stockDepot = null;
  private List<StockDepotPeriodicReport<Quarter>> quarterReports;
  private ComponentFactory        componentFactory;
  
  private EffectenReportPanel     effectenReportPanel = null;
  
  // Jaar + kwartaal
  private LookAheadComboBox<Integer> yearComboBox;
  private Integer     year = null;
  private LookAheadComboBox<Integer> quarterComboBox;
  private Integer     quarter = null;  // 1 t/m 4
    
  
  public EffRekKwartaaloverzichtWindow(Customization customization, FinanBank finanBank, StockDepot stockDepot) {
    super(finanBank.getBank().getName() + " " + WINDOW_TITLE, customization, new Dimension(800, 1000));
    
    LOGGER.severe("=>");
    LOGGER.severe("Number of Quarter Reports: " + stockDepot.getQuarterReports().size());
    this.stockDepot = stockDepot;
    finanRegistry = FinanRegistry.getInstance();
    quarterReports = stockDepot.getQuarterReports();
    componentFactory = getTheComponentFactory();
    
    init();
    LOGGER.severe("<=");
  }
  
  protected void init() {
    LOGGER.severe("=>");
    Integer[] reportYears = stockDepot.getQuarterlyReportYears();
    
    JPanel controlPanel = createControlPanel(reportYears);
    
    
    getContentPane().add(controlPanel);
    updateEffectenData();
    
    addWindowListener(new WindowListener() {

      @Override
      public void windowActivated(WindowEvent e) {
        // No action      
      }

      @Override
      public void windowClosed(WindowEvent e) {
        // No action      
      }

      @Override
      public void windowClosing(WindowEvent e) {
        // No action      
      }

      @Override
      public void windowDeactivated(WindowEvent e) {
        // No action      
      }

      @Override
      public void windowDeiconified(WindowEvent e) {
        // No action      
      }

      @Override
      public void windowIconified(WindowEvent e) {
        // No action      
      }

      @Override
      public void windowOpened(WindowEvent e) {
        showPopupIfTransactionsNotHandled();
      }
      
    });
    
    LOGGER.severe("<=");
  }
  
  private JPanel createControlPanel(Integer[] reportYears) {
    LOGGER.severe("=>");

    JPanel controlPanel = componentFactory.createPanel(-1, -1, true);
    SpringLayout layout = new SpringLayout();
    controlPanel.setLayout(layout);
    
    effectenReportPanel = new EffectenReportPanel(this, EffectenReportPanelType.QUARTER, stockDepot, null);
    
    // label and 'look ahead combobox' for selecting a year.
    JLabel yearSelectionLabel = componentFactory.createLabel("Jaar:", SwingConstants.LEFT);
    controlPanel.add(yearSelectionLabel);
    layout.putConstraint(SpringLayout.WEST, yearSelectionLabel,
        LEFT_MARGIN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, yearSelectionLabel,
        TOP_MARGIN + 4,
        SpringLayout.NORTH, controlPanel);
    
    yearComboBox = componentFactory.createLookAheadComboBox(reportYears, false, 5, 30, JAAR_TOOLTIP);
    yearComboBox.setSelectedIndex(reportYears.length - 1);
    year = (Integer) yearComboBox.getSelectedItem();
    yearComboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        @SuppressWarnings("unchecked")
        LookAheadComboBox<Integer> comboBox = (LookAheadComboBox<Integer>) e.getSource();
        year = (Integer) comboBox.getSelectedItem();
        
        updateQuarterComboBox();
//        updateEffectenData();
      }
    });
    controlPanel.add(yearComboBox);
    layout.putConstraint(SpringLayout.WEST, yearComboBox,
        SECOND_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, yearComboBox,
        TOP_MARGIN,
        SpringLayout.NORTH, controlPanel);
    
    
    // label and 'look ahead combobox' for selecting a quarter.
    JLabel quarterSelectionLabel = componentFactory.createLabel("Kwartaal:", SwingConstants.LEFT);
    controlPanel.add(quarterSelectionLabel);
    layout.putConstraint(SpringLayout.WEST, quarterSelectionLabel,
        THIRD_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, quarterSelectionLabel,
        TOP_MARGIN + 4,
        SpringLayout.NORTH, controlPanel);
     
    quarterComboBox = componentFactory.createLookAheadComboBox((List<Integer>) null, false, 4, 30, KWARTAAL_TOOLTIP);
    quarterComboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        @SuppressWarnings("unchecked")
        LookAheadComboBox<Integer> comboBox = (LookAheadComboBox<Integer>) e.getSource();
        quarter = (Integer) comboBox.getSelectedItem();
//        JTextField field = (JTextField) e.getSource();
//        String text = field.getText().trim();
//        if (text.length() > 0) {
//          quarter = Integer.parseInt(text);
//          if (quarter < 1  ||  quarter > 4) {
//            quarter = null;
//            field.setText("");
//          }
//        } else {
//          quarter = -1;
//        }
        
        updateEffectenData();
      }
    });
    updateQuarterComboBox();
    controlPanel.add(quarterComboBox);
    layout.putConstraint(SpringLayout.WEST, quarterComboBox,
        FOURTH_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, quarterComboBox,
        TOP_MARGIN,
        SpringLayout.NORTH, controlPanel);
    
    controlPanel.add(effectenReportPanel);
    layout.putConstraint(SpringLayout.WEST, effectenReportPanel,
        LEFT_MARGIN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, effectenReportPanel,
        VERTICAL_SEPARATION_GAP,
        SpringLayout.SOUTH, yearSelectionLabel);
    
    LOGGER.severe("<=");
    return controlPanel;
  }

  protected void updateQuarterComboBox() {
    List<Integer> quarters = new ArrayList<>();
    
    for (StockDepotPeriodicReport<Quarter> report: quarterReports) {
      Quarter reportQuarter = report.getPeriod();
      if (reportQuarter.getYear() == year) {
        quarters.add(reportQuarter.getQuarter());
      } else if (reportQuarter.getYear() > year) {
        break;
      }
    }
    quarterComboBox.setValues(quarters);
    if (quarters.size() > 0) {
      quarterComboBox.setSelectedItem(quarters.get(quarters.size() - 1));
    }
  }

  private void showPopupIfTransactionsNotHandled() {
    if (!finanRegistry.areTransactionsHandled()) {
      MessageDialog messageDialog = MessageDialog.createMessageDialog(this, MessageDialogType.WARNING, null, "De transacties zijn niet verwerkt, dus er zijn geen kwartaaloverzichten beschikbaar");
      WindowUtil.showDialogCenteredOnParent(this, messageDialog);
    }    
  }
  
  private void updateEffectenData() {
    LOGGER.severe("=>");
    
    if (year != null  &&  quarter != null) {
      effectenReportPanel.setPeriod(new Quarter(year, quarter));
    } else {
      effectenReportPanel.setPeriod(null);
    }
    
    LOGGER.severe("<=");
  }
}
