package goedegep.app.finan.td;

import goedegep.appgen.swing.Customization;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;

import java.util.List;

import javax.swing.JComboBox;

public class DCShareDividendField extends DC<JComboBox<ShareDividend>> {
  private Share           share;
  private ShareDividend[] dividends;
  
  public DCShareDividendField (
      CDShareDividendField componentDescriptor,
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    
    setComponent(customization.getComponentFactory().<ShareDividend>createComboBox(componentDescriptor.getColumns(), componentDescriptor.getToolTipText()));
  }
  
  @Override
  public Object getValue() {
    JComboBox<ShareDividend> comboBox = getComponent();
    return comboBox.getSelectedItem();
  }

  public boolean isFilledIn() {
    // a combobox always has a value.
    return true;
  }

  @Override
  public boolean isValid() {
    boolean returnValue = true;
    
    System.out.print("Checking component: " + getComponentDescriptor().getName() + " => ");
    System.out.println(returnValue == true ? "true" : "false");
    
    return returnValue;
  }

  @Override
  public void setStatusHighlight() {
    // No action, no unknown value possible.
  }
  
  @Override
  public void update(TransactionEntryStatus transactionEntryStatus) {
    TransactionDialogComponentList components = transactionEntryStatus.getTransactionComponents();
    share = ((Share) components.get(getComponentDescriptor().getDependsOnName()).getValue());
    if (share != null) {
      System.out.println("Update: share = " + share.getName());
      fillDividendReferences(share);
    }
    JComboBox<ShareDividend> comboBox = getComponent();
    comboBox.removeAllItems();
    for (ShareDividend shareDividend: dividends) {
      System.out.println("Update: adding = " + shareDividend);
      comboBox.addItem(shareDividend);
    }
  }
  
  private void fillDividendReferences(Share share) {
    System.out.println("=> fillDividendReferences");
    
    List<ShareDividend> shareDividends = share.getDividends();
    dividends = shareDividends.toArray(new ShareDividend[shareDividends.size()]);
    
    System.out.println("<= fillDividendReferences");
  }
}