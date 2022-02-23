package goedegep.app.finan.td;

import goedegep.appgen.swing.Customization;
import goedegep.finan.stocks.ClaimEmission;
import goedegep.finan.stocks.Share;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;

public class DCClaimEmissionField extends DC<JComboBox<String>> {
  private Share        share;
  private String[]     emissionIds;
  
  public DCClaimEmissionField (
      CDClaimEmissionField componentDescriptor,
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    
    setComponent(customization.getComponentFactory().<String>createComboBox(componentDescriptor.getColumns(), componentDescriptor.getToolTipText()));
  }
  
  @Override
  public Object getValue() {
    JComboBox<String> comboBox = getComponent();
    return share.getClaimEmission((String) comboBox.getSelectedItem());
  }

  public boolean isFilledIn() {
    // a combobox always has a value.
    return true;
  }

  @Override
  public boolean isValid() {
    // As a combobox always has a value and therefore can not be optional,
    // we don't have to check on 'optional and not filled in'.
    
    boolean returnValue = true;
    
//    System.out.print("Checking component: " + getComponentDescriptor().getName() + " => ");
//    System.out.println(returnValue == true ? "true" : "false");
    
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
//      System.out.println("Update: share = " + share.getName());
      fillClaimEmissionIds(share);
    }
    JComboBox<String> comboBox = getComponent();
    comboBox.removeAllItems();
    for (String item: emissionIds) {
//      System.out.println("Update: adding = " + item);
      comboBox.addItem(item);
    }
  }
  
  private void fillClaimEmissionIds(Share share) {
    System.out.println("=> getClaimEmissionIds");
    
    // Maak lijst van alle claim emissies voor het aandeel.
    List<String> emissionNameList = new LinkedList<String>();
    
    for (ClaimEmission claimEmission: share.getClaimEmissions()) {
//      System.out.println("fillClaimEmissionIds: adding: " + claimEmission.getId());
      emissionNameList.add(claimEmission.getId());
    }
    emissionIds = new String[emissionNameList.size()];
    for (int i = 0; i < emissionNameList.size(); i++) {
      emissionIds[i] = (String) emissionNameList.get(i);
    }
//    System.out.println("<= getClaimEmissionIds");
  }
}
