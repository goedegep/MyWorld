package goedegep.app.finan.postbankapp;

import goedegep.app.finan.effrek.EffectenReportPanel;
import goedegep.app.finan.effrek.EffectenReportPanelType;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.stocks.StockDepot;
import goedegep.util.datetime.Quarter;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

//TODO PRIO1 Can this be removed, or otherwise better be related to EffRekKwartaaloverzicht.
@SuppressWarnings("serial")
public class PbEffRekKwartaaloverzichtWindow extends AppFrame{
  private final static Logger     LOGGER = Logger.getLogger(PbEffRekKwartaaloverzichtWindow.class.getName());
  private final static String     WINDOW_TITLE = "Effectenrekening Kwartaaloverzicht";

  private static final int        LEFT_MARGIN = 23;    // is also first column
  private static final int        SECOND_COLUMN = 150;
  private static final int        THIRD_COLUMN = 250;
  private static final int        FOURTH_COLUMN = 350;
  private static final int        TOP_MARGIN = 12;
  private static final int        VERTICAL_SEPARATION_GAP = 8;
  
  private static final String     JAAR_TOOLTIP = "Vul het jaar in waarvoor het overzicht weergegeven moet worden.";
  private static final String     KWARTAAL_TOOLTIP = "Vul het kwartaal (1 t/m 4) in waarvoor het overzicht weergegeven moet worden.";
  
  private StockDepot    stockDepot = null;
  
  private EffectenReportPanel      effectenReportPanel = null;
  
  // Jaar + kwartaal
  private Integer     year = null;
  private Integer     quarter = null;  // 1 t/m 4
    
  
  public PbEffRekKwartaaloverzichtWindow(Customization customization, StockDepot stockDepot, int dummy) {
    super(WINDOW_TITLE, customization, null);
    LOGGER.fine("=>");
    
    this.stockDepot = stockDepot;
    
    init();
    LOGGER.fine("<=");
  }
  
  private void init() {
    setSize(new Dimension(800, 470));           // set window size
    ComponentFactory componentFactory = getTheComponentFactory();
    
    JPanel controlPanel = componentFactory.createPanel(-1, -1, true);
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
    
    JTextField textField = componentFactory.createTextField(4, JAAR_TOOLTIP);
    textField.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JTextField field = (JTextField) e.getSource();
        String text = field.getText().trim();
        if (text.length() > 0) {
          year = Integer.parseInt(text);
        } else {
          year = null;
        }
        
        updateEffectenData();
      }      
    });
    controlPanel.add(textField);
    layout.putConstraint(SpringLayout.WEST, textField,
        SECOND_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, textField,
        TOP_MARGIN,
        SpringLayout.NORTH, controlPanel);
    
    
    // label and textfield for selecting a quarter.
    JLabel quarterSelectionLabel = componentFactory.createLabel("Kwartaal:", SwingConstants.LEFT);
    controlPanel.add(quarterSelectionLabel);
    layout.putConstraint(SpringLayout.WEST, quarterSelectionLabel,
        THIRD_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, quarterSelectionLabel,
        TOP_MARGIN + 4,
        SpringLayout.NORTH, controlPanel);
    
    JTextField quarterTextField = componentFactory.createTextField(4, KWARTAAL_TOOLTIP);
    quarterTextField.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JTextField field = (JTextField) e.getSource();
        String text = field.getText().trim();
        if (text.length() > 0) {
          quarter = Integer.parseInt(text);
          if (quarter < 1  ||  quarter > 4) {
            quarter = null;
            field.setText("");
          }
        } else {
          quarter = -1;
        }
        
        updateEffectenData();
      }      
    });
    controlPanel.add(quarterTextField);
    layout.putConstraint(SpringLayout.WEST, quarterTextField,
        FOURTH_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, quarterTextField,
        TOP_MARGIN,
        SpringLayout.NORTH, controlPanel);
    
    effectenReportPanel = new EffectenReportPanel(this, EffectenReportPanelType.MONTH, stockDepot, null);
    controlPanel.add(effectenReportPanel);
    layout.putConstraint(SpringLayout.WEST, effectenReportPanel,
        LEFT_MARGIN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, effectenReportPanel,
        VERTICAL_SEPARATION_GAP,
        SpringLayout.SOUTH, yearSelectionLabel);
    
    
    getContentPane().add(controlPanel);
    updateEffectenData();
  }
  
  private void updateEffectenData() {
    if (year != null  &&  quarter != null) {
      effectenReportPanel.setPeriod(new Quarter(year, quarter));
    } else {
      effectenReportPanel.setPeriod(null);
    }
  }
}