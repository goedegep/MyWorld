package goedegep.app.finan.td;

import javax.swing.JLabel;

import goedegep.appgen.swing.Customization;

public class CDLabel extends CD {
  String          text;

  public CDLabel(String name, String text) {
    super(name, false);
    this.text = text;
  }

  public String getText() {
    return text;
  }
  
  public DC<JLabel> createComponent(TransactionEntryStatus transactionEntryStatus, Customization customization) {
    return new DCLabel(this, transactionEntryStatus, customization);
  }
}