package goedegep.app.finan.effrek;

import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppPanel;
import goedegep.finan.stocks.StockDepot;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class DividendenOverzichtPanel extends AppPanel {
  private DividendenOverzichtTable dividendenOverzichtTable;
  
  public DividendenOverzichtPanel(AppFrame owner, StockDepot depot, Integer year) {
    super(owner, null);
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEtchedBorder());
    add(createHeaderPanel(), BorderLayout.NORTH);
    
    dividendenOverzichtTable = new DividendenOverzichtTable(owner, depot, year);
    add(dividendenOverzichtTable, BorderLayout.CENTER);
  }
  
  private JPanel createHeaderPanel() {
    JPanel headerPanel = getTheComponentFactory().createPanel(-1, -1, false);
    headerPanel.setLayout(new BorderLayout());
    
    JLabel label = getTheComponentFactory().createLabel("<HTML><B>Verrekende dividenden.</HTML>", SwingConstants.LEFT);
    headerPanel.add(label,BorderLayout.WEST);
    
    return headerPanel;
  }

  public void setYear(Integer year) {
    dividendenOverzichtTable.setYear(year);
  }

}
