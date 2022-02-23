package goedegep.app.finan.td;

import javax.swing.JTextField;

import goedegep.appgen.swing.Customization;

public class DCTextField extends DC<JTextField> {
  public DCTextField(
      CDTextField componentDescriptor,
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    setComponent(customization.getComponentFactory().createTextField(componentDescriptor.getColumns(),
        componentDescriptor.getToolTipText()));
  }

  @Override
  public Object getValue() {
    JTextField textField = getComponent();
    return textField.getText().trim();
  }

  public boolean isFilledIn() {
    return ((JTextField) getComponent()).getText().length() != 0;
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
