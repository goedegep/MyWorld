package goedegep.app.finan.td;

import javax.swing.JCheckBox;

import goedegep.appgen.swing.Customization;

public class CDCheckBox extends CD {
  boolean selected;
  
  public CDCheckBox(String name, boolean selected) {
    super(name, true);
    this.selected = selected;
  }

  public boolean isSelected() {
    return selected;
  }

  @Override
  public DC<JCheckBox> createComponent(TransactionEntryStatus transactionEntryStatus, Customization customization) {
    return new DCCheckBox(this, transactionEntryStatus, customization);
  }
}
