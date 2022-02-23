package goedegep.app.finan.lynxapp.transactioncheck;

import java.time.YearMonth;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppPanel;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.lynx.lynxeffrek.LynxEffRek;
import goedegep.finan.lynx.lynxeffrek.LynxMonthlyActivityStatements;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdList;

@SuppressWarnings("serial")
public class LynxTransactionCheckResultPanel extends AppPanel {
  
  private ComponentFactory componentFactory = getTheComponentFactory();
  private LynxTranactionDifferencesTable lynxTranactionDifferencesTable;
  
  public LynxTransactionCheckResultPanel(AppFrame owner, LynxEffRek effectenRekening, LynxToFinanShareIdList lynxToFinanShareIdList, LynxMonthlyActivityStatements lynxMonthlyActivityStatements) {
    super(owner, null);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
    add(createTransactionDifferencesPanel(effectenRekening, lynxToFinanShareIdList, lynxMonthlyActivityStatements));
  }
  
  private JPanel createTransactionDifferencesPanel(LynxEffRek effectenRekening, LynxToFinanShareIdList lynxToFinanShareIdList, LynxMonthlyActivityStatements lynxMonthlyActivityStatements) {
    JPanel panel = componentFactory.createPanel(-1, -1, false);
    
    Border border = BorderFactory.createEtchedBorder();
    setBorder(BorderFactory.createTitledBorder(border, "Gevonden verschillen"));
    
    lynxTranactionDifferencesTable = new LynxTranactionDifferencesTable(getOwner(), effectenRekening, lynxToFinanShareIdList, lynxMonthlyActivityStatements);
    
    panel.add(lynxTranactionDifferencesTable);
    
    return panel;
  }

  public void setMonth(YearMonth month) {
    lynxTranactionDifferencesTable.setMonth(month);
  }
}
