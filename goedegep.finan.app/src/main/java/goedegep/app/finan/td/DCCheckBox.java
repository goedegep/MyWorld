package goedegep.app.finan.td;

import javax.swing.JCheckBox;

import goedegep.appgen.swing.Customization;

public class DCCheckBox extends DC<JCheckBox> {
  public DCCheckBox(
      CDCheckBox componentDescriptor,
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    setComponent(customization.getComponentFactory().createCheckBox(null, componentDescriptor.isSelected()));
  }
  
  @Override
  public Object getValue() {
    JCheckBox checkBox = (JCheckBox) getComponent();
    return checkBox.isSelected();
  }
  
  public boolean isFilledIn() {
    // a check box always has a value.
    return true;
  }

  @Override
  public boolean isValid() {
    // As a checkbox always has a value and therefore can not be optional,
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
