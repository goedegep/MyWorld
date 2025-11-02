package goedegep.app.finan.effrek;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.YearMonth;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import goedegep.app.finan.gen.FinanBank;
import goedegep.app.finan.registry.FinanRegistry;
import goedegep.appgen.LookAheadComboBox;
import goedegep.appgen.MessageDialogType;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.appgen.swing.Customization;
import goedegep.finan.stocks.StockDepot;

/**
 * Een maandoverzicht bevat:
 *   - het saldo aan het eind van de maand
 *   - de aandelen posities aan het eind van de maand
 *   - de optie posities aan het eind van de maand
 *   - de rechten aan het eind van de maand
 *   - alle transacties van die maand (aankopen, verkopen, dividenden, etc.)
 */
@SuppressWarnings("serial")
public class EffRekMaandoverzichtWindow extends AppFrame {
  private static final String     WINDOW_TITLE = "Effectenrekening Maandoverzicht";

  private static final int        LEFT_MARGIN = 23;    // is also first column
  private static final int        SECOND_COLUMN = 150;
  private static final int        THIRD_COLUMN = 250;
  private static final int        FOURTH_COLUMN = 350;
  private static final int        TOP_MARGIN = 12;
  
  private static final String     JAAR_TOOLTIP = "Vul het jaar in waarvoor het overzicht weergegeven moet worden.";
  private static final String     MAAND_TOOLTIP = "Vul de maand (1 t/m 12) in waarvoor het overzicht weergegeven moet worden.";
  
  private ComponentFactory        componentFactory;
  private StockDepot              stockDepot = null;
  
  private EffectenReportPanel     effectenReportPanel = null;
  private LookAheadComboBox<Integer>      yearComboBox;
  private LookAheadComboBox<Integer>      monthComboxBox;
  
  // Jaar + kwartaal
  private Integer     year = null;
  private Integer     month = null;  // 1 t/m 12
    
  
  public EffRekMaandoverzichtWindow(Customization customization, FinanBank finanBank, StockDepot stockDepot) {
    super(finanBank.getBank().getName() + " " + WINDOW_TITLE, customization, new Dimension(800, 600));
    
    this.stockDepot = stockDepot;
    componentFactory = getTheComponentFactory();
    
    init();
  }
  
  protected void init() {
    Integer[] reportYears = stockDepot.getMonthlyReportYears();
    
    JPanel controlPanel = createControlPanel(reportYears);    
    getContentPane().add(controlPanel, BorderLayout.CENTER);
    
    effectenReportPanel = new EffectenReportPanel(this, EffectenReportPanelType.MONTH, stockDepot, null);
    getContentPane().add(effectenReportPanel, BorderLayout.SOUTH);    
   
    yearComboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        @SuppressWarnings("unchecked")
        LookAheadComboBox<Integer> yearComboBox = (LookAheadComboBox<Integer>) e.getSource();
        year = (Integer) yearComboBox.getSelectedItem();
        
        updateMonthComboBox();
      }      
    });
    monthComboxBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        @SuppressWarnings("unchecked")
        LookAheadComboBox<Integer> field = (LookAheadComboBox<Integer>) e.getSource();
        month = (Integer) field.getSelectedItem();
        
        updateEffectenData();
      }      
    });
    
    if (reportYears.length != 0) {
      yearComboBox.setSelectedItem(reportYears[reportYears.length-1]);
    }
    
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
    pack();
  }
  
  private JPanel createControlPanel(Integer[] reportYears) {
    JPanel controlPanel = componentFactory.createPanel(800, 50, true);
    SpringLayout layout = new SpringLayout();
    controlPanel.setLayout(layout);
    
    // label and textfield for selecting a year.
    JLabel yearSelectionLabel = componentFactory.createLabel("Jaar:", SwingConstants.LEFT);
    controlPanel.add(yearSelectionLabel);
    layout.putConstraint(SpringLayout.WEST, yearSelectionLabel,
        LEFT_MARGIN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, yearSelectionLabel,
        TOP_MARGIN + 4,
        SpringLayout.NORTH, controlPanel);
    
    yearComboBox = componentFactory.createLookAheadComboBox(reportYears, false, 12, 20, JAAR_TOOLTIP);
    monthComboxBox = componentFactory.createLookAheadComboBox((List<Integer>) null, false, 12, 20, MAAND_TOOLTIP);
    
    controlPanel.add(yearComboBox);
    layout.putConstraint(SpringLayout.WEST, yearComboBox,
        SECOND_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, yearComboBox,
        TOP_MARGIN,
        SpringLayout.NORTH, controlPanel);
    
    
    // label and textfield for selecting a month.
    JLabel monthSelectionLabel = componentFactory.createLabel("Maand:", SwingConstants.LEFT);
    controlPanel.add(monthSelectionLabel);
    layout.putConstraint(SpringLayout.WEST, monthSelectionLabel,
        THIRD_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, monthSelectionLabel,
        TOP_MARGIN + 4,
        SpringLayout.NORTH, controlPanel);
    
    controlPanel.add(monthComboxBox);
    layout.putConstraint(SpringLayout.WEST, monthComboxBox,
        FOURTH_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, monthComboxBox,
        TOP_MARGIN,
        SpringLayout.NORTH, controlPanel);
    
    return controlPanel;
  }

  private void updateMonthComboBox() {
    monthComboxBox.removeAllItems();
    
    if (year != null) {
      Integer months[] = stockDepot.getMonthlyReportMonths(year);
      for (Integer month: months) {
        monthComboxBox.addItem(month);
      }
      monthComboxBox.setSelectedItem(months[months.length - 1]);
    }
    updateEffectenData();
  }
  
  private void updateEffectenData() {
    System.out.println("In updateEffectenData: year = " + year + ", month = " + month);
    if (year != null  &&  month != null) {
      effectenReportPanel.setPeriod(YearMonth.of(year, month));
    } else {
      effectenReportPanel.setPeriod(null);
    }
  }
  
  private void showPopupIfTransactionsNotHandled() {
    if (!FinanRegistry.getInstance().areTransactionsHandled()) {
      showMessageDialog(MessageDialogType.WARNING, "De transacties zijn niet verwerkt, dus er zijn geen maandoverzichten beschikbaar");
    }    
  }
}