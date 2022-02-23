package goedegep.app.finan.finanapp.td;

import java.util.ArrayList;
import java.util.List;

import goedegep.app.finan.td.DC;
import goedegep.appgen.LookAheadComboBox;
import goedegep.appgen.swing.Customization;
import goedegep.finan.stocks.Company;
import goedegep.finan.stocks.Fund;
import goedegep.finan.stocks.Share;

public class DCAandeelNaamField extends DC<LookAheadComboBox<String>> {
//  private String[]     shareNames;
  private List<String> shareNames = new ArrayList<>();
  
  public DCAandeelNaamField(
      CDAandeelNaamField componentDescriptor,
      FinanTransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    // Maak lijst van alle bekende aandelen namen.
    for (Company comp: transactionEntryStatus.getCompanyPool().getCompanies()) {
      List<Fund> funds = comp.getFunds();
      for (int li = 0; li < funds.size(); li++) {
        Fund f = funds.get(li);
        List<Share> shares = f.getShares();
        for (int si = 0; si < shares.size(); si++) {
          Share s = shares.get(si);
          //System.out.println(s.getName());
          if (!s.isObsolete()) {
            shareNames.add(s.getName());
          }
        }
      }
    }

    setComponent(customization.getComponentFactory().createLookAheadComboBox(shareNames, true, componentDescriptor.getColumns(), 30, componentDescriptor.getToolTipText()));
  }
  
  public boolean isFilledIn() {
    LookAheadComboBox<String> transactieField = getComponent();
    return transactieField.getSelectedItem() != null;
  }

  public boolean isValid() {
    if (getComponentDescriptor().isOptional()  &&  !isFilledIn()) {
      return true;
    }
    LookAheadComboBox<String> transactieField = getComponent();
    return transactieField.getSelectedItem() != null;
  }

  @Override
  public void setStatusHighlight() {
  }

  @Override
  public Object getValue() {
    LookAheadComboBox<String>  transactieField = getComponent();
    String shareName = (String) transactieField.getSelectedItem();    
    
    return Share.getShare(shareName);
  }
}
