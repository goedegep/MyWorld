package goedegep.app.finan.td;

import javax.swing.JComboBox;

import goedegep.appgen.swing.Customization;

public class DCComboBox extends DC<JComboBox<Object>> {
  public DCComboBox(
      CDComboBox componentDescriptor,
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    setComponent(customization.getComponentFactory().<Object>createComboBox(componentDescriptor.getOptions(),
        componentDescriptor.getColumns(), componentDescriptor.getToolTipText()));
  }
  
  @Override
  public Object getValue() {
    JComboBox<Object> comboBox = getComponent();
    return (String) comboBox.getSelectedItem();
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
    
//    System.out.print("Checking component: " + getName() + " => ");
//    System.out.println(returnValue == true ? "true" : "false");
    
    return returnValue;
  }

  @Override
  public void setStatusHighlight() {
    // No action, no unknown value possible.
  }

}
