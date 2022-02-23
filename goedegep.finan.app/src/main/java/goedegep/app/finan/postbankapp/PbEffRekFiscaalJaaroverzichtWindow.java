package goedegep.app.finan.postbankapp;

import goedegep.app.finan.effrek.DividendenOverzichtPanel;
import goedegep.app.finan.effrek.EffectenReportPanel;
import goedegep.app.finan.effrek.EffectenReportPanelType;
import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.stocks.StockDepot;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;


// TODO PRIO1 Can this be removed, or otherwise better be related to EffRekFiscaalJaaroverzicht.
@SuppressWarnings("serial")
public class PbEffRekFiscaalJaaroverzichtWindow extends AppFrame{
  private static final String     WINDOW_TITLE = "Fiscaal Jaaroverzicht";

  private static final int        LEFT_MARGIN = 23;
  private static final int        SECOND_COLUMN = 150;
  private static final int        TOP_MARGIN = 12;
  private static final int        VERTICAL_SEPARATION_GAP = 8;
  
  private static final String     JAAR_TOOLTIP = "Vul het jaar in waarvoor het overzicht weergegeven moet worden.";
  
  private StockDepot               stockDepot = null;
  private EffectenReportPanel      effectenReportPanel = null;
  private DividendenOverzichtPanel dividendenOverzichtPanel = null;
  
  public PbEffRekFiscaalJaaroverzichtWindow(Customization customization, StockDepot stockDepot) {
    super(WINDOW_TITLE, customization, null);
    this.stockDepot = stockDepot;
    
    init();
  }

  private void init() {
    setSize(new Dimension(800, 720));           // set window size
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
        Integer year = null;
        if (text.length() > 0) {
          year = Integer.parseInt(text);
        }
        
        listData(year);
      }      
    });
    controlPanel.add(textField);
    layout.putConstraint(SpringLayout.WEST, textField,
        SECOND_COLUMN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, textField,
        TOP_MARGIN,
        SpringLayout.NORTH, controlPanel);

    effectenReportPanel = new EffectenReportPanel(this, EffectenReportPanelType.TAX, stockDepot, null);
    controlPanel.add(effectenReportPanel);
    layout.putConstraint(SpringLayout.WEST, effectenReportPanel,
        LEFT_MARGIN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, effectenReportPanel,
        VERTICAL_SEPARATION_GAP,
        SpringLayout.SOUTH, yearSelectionLabel);

    dividendenOverzichtPanel = new DividendenOverzichtPanel(this, stockDepot, null);
    controlPanel.add(dividendenOverzichtPanel);
    layout.putConstraint(SpringLayout.WEST, dividendenOverzichtPanel,
        LEFT_MARGIN,
        SpringLayout.WEST, controlPanel);
    layout.putConstraint(SpringLayout.NORTH, dividendenOverzichtPanel,
        VERTICAL_SEPARATION_GAP,
        SpringLayout.SOUTH, effectenReportPanel);
    
    getContentPane().add(controlPanel);
  }
  
  private void listData(int year) {
    effectenReportPanel.setPeriod(year);
    dividendenOverzichtPanel.setYear(year);
  }
}
