package goedegep.app.finan.effrek;

import goedegep.app.finan.gen.TransactionTable;
import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppPanel;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.basic.PgTransaction;
import goedegep.finan.stocks.StockDepot;
import goedegep.finan.stocks.StockDepotPeriodicReport;
import goedegep.util.datetime.Quarter;

import java.time.YearMonth;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * This class provides a panel with information on securities (shares?) for a given report type and period.
 * <p>
 * The supported types are defined by the enum {@link EffectenReportPanelType}:
 * <ul>
 * <li>
 * MONTH</br>
 * A monthly report
 * </li>
 * <li>
 * QUARTER</br>
 * A quarterly report
 * </li>
 * <li>
 * TAX</br>
 * A tax report, which is a yearly report
 * </li>
 * </ul>
 * 
 * The panel consists of the following parts:
 * <ul>
 * <li>
 * An {@link EffectenReportTable}
 * </li>
 * <li>
 * A {@link TransactionTable}<br/>
 * TODO Currently no transactions are shown, as {@link StockDepot} still has to implement adding transactions to the periodic reports.
 * </li>
 * </ul>
 */
@SuppressWarnings("serial")
public class EffectenReportPanel extends AppPanel {
  private final static Logger     LOGGER = Logger.getLogger(EffectenReportPanel.class.getName());

  private EffectenReportPanelType effectenReportPanelType;
  private StockDepot stockDepot;
  private StockDepotPeriodicReport<?> stockDepotPeriodicReport;
  private Object period;
  private ComponentFactory componentFactory = getTheComponentFactory();
  private EffectenReportTable effectenReportTable;
  private TransactionTable finanTransactionsTable;
  
  
  /**
   * Create an EffectenReportPanel.
   * 
   * @param owner the AppFrame which owns this panel.
   * @param effectenReportPanelType the report panel type
   * @param depot the depot for which the information is to be shown
   * @param period the period over which the information is to be shown. The actual data type depends on the <b>effectenReportPanelType</b>:
   *        <table border="0">
   *        <tr>
   *        <th>effectenReportPanelType</th>
   *        <th>period type</th>
   *        </tr>
   *        <tr>
   *        <td>MONTH</td>
   *        <td>Month - the month over which the report is shown</td>
   *        </tr>
   *        <tr>
   *        <td>QUARTER</td>
   *        <td>Quarter - the quarter over which the report is shown</td>
   *        </tr>
   *        <tr>
   *        <td>TAX</td>
   *        <td>Integer - the year over which the report is shown</td>
   *        </tr>
   *        </table>
   */
  public EffectenReportPanel(AppFrame owner, EffectenReportPanelType effectenReportPanelType, StockDepot depot, Object period) {
    super(owner, 500, 700);
    
    LOGGER.severe("=>");
    this.effectenReportPanelType = effectenReportPanelType;
    this.stockDepot = depot;
    this.period = period;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
    add(createPositionsPanel());
    add(createTransactionsPanel());

    LOGGER.severe("=>");
  }
  
  public void setPeriod(Object period) {
    effectenReportTable.setPeriod(period);
    
    if (period != null) {
      LOGGER.severe("effectenReportPanelType is: " + effectenReportPanelType);
      switch (effectenReportPanelType) {
      case MONTH:
        YearMonth yearMonth = (YearMonth) period;
        stockDepotPeriodicReport = stockDepot.getMonthlyReport(yearMonth.getYear(), yearMonth.getMonthValue());
        break;
        
      case QUARTER:
        Quarter quarter = (Quarter) period;
        stockDepotPeriodicReport = stockDepot.getQuarterReport(quarter.getYear(), quarter.getQuarter());
        break;
        
      case TAX:
        Integer year = (Integer) period;
        stockDepotPeriodicReport = stockDepot.getQuarterReport(year, 4);
        // TODO This has to be changed to a Yearly report.
        break;
        
      default:
        throw new RuntimeException("Illegal effectenReportPanelType: " + effectenReportPanelType);
      }
    } else {
      stockDepotPeriodicReport = null;
    }
    
    if (stockDepotPeriodicReport != null) {
      finanTransactionsTable.setTransactions(stockDepotPeriodicReport.getTransactions());
    } else {
      finanTransactionsTable.setTransactions(null);
    }
  }
  
  private JPanel createPositionsPanel() {
    JPanel panel = componentFactory.createPanel(-1, -1, false);
    
    Border border = BorderFactory.createEtchedBorder();
    panel.setBorder(BorderFactory.createTitledBorder(border, effectenReportPanelType.getEffectenReportTableHeader()));
    
    effectenReportTable = new EffectenReportTable(getOwner(), effectenReportPanelType, stockDepot, period);
    
    panel.add(effectenReportTable);
    
    return panel;
  }
  
  private JPanel createTransactionsPanel() {
    JPanel panel = componentFactory.createPanel(-1, -1, false);
    
    Border border = BorderFactory.createEtchedBorder();
    panel.setBorder(BorderFactory.createTitledBorder(border, effectenReportPanelType.getTransactionsTableHeader()));
    List<PgTransaction> transactions = null;
    
    if (stockDepotPeriodicReport != null) {
      transactions = stockDepotPeriodicReport.getTransactions();
    }
    finanTransactionsTable = new TransactionTable(getOwner(), transactions);
    
    panel.add(finanTransactionsTable);
    
    return panel;
  }
}
