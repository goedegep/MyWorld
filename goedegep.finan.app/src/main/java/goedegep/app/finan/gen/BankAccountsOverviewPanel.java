package goedegep.app.finan.gen;

import goedegep.appgen.swing.AppFrame;
import goedegep.appgen.swing.AppPanel;
import goedegep.appgen.swing.ComponentFactory;
import goedegep.finan.basic.Bank;
import goedegep.util.money.PgCurrency;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class BankAccountsOverviewPanel extends AppPanel {
  private static final MuntKeuze DEFAULT_MUNT_KEUZE = MuntKeuze.EURO;
  private BankAccountsTable bankAccountsTable;
  
  public BankAccountsOverviewPanel(AppFrame owner, Bank bank) {
    super(owner, null);
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEtchedBorder());
    add(createHeaderPanel(), BorderLayout.NORTH);
    
    bankAccountsTable = new BankAccountsTable(owner, bank, DEFAULT_MUNT_KEUZE.getMunteenheid());
    add(bankAccountsTable, BorderLayout.CENTER);
  }
  
  private JPanel createHeaderPanel() {
    ComponentFactory componentFactory = getTheComponentFactory();
    
    JPanel headerPanel = componentFactory.createPanel(-1, -1, false);
    headerPanel.setLayout(new BorderLayout());
    
    JLabel label = componentFactory.createLabel("<HTML><B>Saldo's en waarden per rekening</HTML>", SwingConstants.LEFT);
    headerPanel.add(label,BorderLayout.WEST);
    
    JPanel muntKeuzePanel = componentFactory.createPanel(-1, -1, false);
    muntKeuzePanel.setLayout(new FlowLayout());
    label = componentFactory.createLabel("Toon bedragen in:", SwingConstants.RIGHT);
    muntKeuzePanel.add(label);
    
    JComboBox<MuntKeuze> muntKeuzeBox = componentFactory.createComboBox(MuntKeuze.values(), -1, "kies een vaste munteenheid, of de werkelijke eenheid");
    muntKeuzeBox.setSelectedItem(DEFAULT_MUNT_KEUZE);
    muntKeuzeBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nieuweMuntKeuze(e);
      }
    });
    muntKeuzePanel.add(muntKeuzeBox);
    headerPanel.add(muntKeuzePanel, BorderLayout.EAST);
    
    return headerPanel;
  }
  
  private void nieuweMuntKeuze(ActionEvent e) {
    @SuppressWarnings("unchecked")
    JComboBox<MuntKeuze> muntKeuzeBox = (JComboBox<MuntKeuze>) e.getSource();
    MuntKeuze muntKeuze = (MuntKeuze) muntKeuzeBox.getSelectedItem();
    bankAccountsTable.setMunteenheid(muntKeuze.getMunteenheid());
  }
  
  enum MuntKeuze {
    ORIGINEEL("oorspronkelijke munteinheid", PgCurrency.NO_CURRENCY_SPECIFIED),
    EURO("euro's", PgCurrency.EURO),
    GULDEN("guldens", PgCurrency.GUILDER);
    
    private String displayText;
    private int    munteenheid;
    
    MuntKeuze(String displayText, int munteenheid) {
      this.displayText = displayText;
      this.munteenheid = munteenheid;
    }
    
    public String toString() {
      return displayText;
    }
    
    public int getMunteenheid() {
      return munteenheid;
    }
  }
}
