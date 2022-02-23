package goedegep.app.finan.td;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import goedegep.appgen.swing.Customization;

public class DCLabel extends DC<JLabel> {
  public DCLabel(
      CDLabel componentDescriptor,
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    setComponent(customization.getComponentFactory().createLabel(componentDescriptor.getText(), SwingConstants.LEFT));
  }

  @Override
  public Object getValue() {
    return null;  // A label has no value.
  }

  public boolean isFilledIn() {
    // A label is by definition filled in.
    return true;
  }

  @Override
  public boolean isValid() {
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
