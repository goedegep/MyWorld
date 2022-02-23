package goedegep.app.finan.finanapp.td;

import java.util.HashSet;
import java.util.Set;

import goedegep.app.finan.td.DC;
import goedegep.appgen.LookAheadComboBox;
import goedegep.appgen.swing.Customization;

//public class DCTransactionType extends DC<JComboBox<String>> {
public class DCTransactionType extends DC<LookAheadComboBox<String>> {
  private String[] transactionTypes;
  
  public DCTransactionType(
      CDTransactionType componentDescriptor,
      FinanTransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    transactionTypes = getTransactionTypesForSelectedAccount(transactionEntryStatus);
//    for (String st: transactionTypes) {
//      System.out.println("Type = " + st);
//    }
//    PgTextLookAhead effRekTransactiesLookAhead = new PgStringArrayLookAhead(transactionTypes);
//    PgLookAheadTextField transactieField =
//      look.createPgLookAheadTextField(null, 20, effRekTransactiesLookAhead, "kies transactiesoort");
    
//    JComboBox<String> transactieField = customization.getComponentFactory().createComboBox(transactionTypes, 20, "kies transactiesoort");
    LookAheadComboBox<String> transactieField = customization.getComponentFactory().createLookAheadComboBox(transactionTypes, true, 20, 20, "kies transactiesoort");
    transactieField.setSelectedItem(null);
    
    setComponent(transactieField);
  }

  @Override
  public Object getValue() {
//    JComboBox<String> transactieField = getComponent();
    LookAheadComboBox<String> transactieField = getComponent();
    return transactieField.getSelectedItem();
  }

  public boolean isFilledIn() {
//    JComboBox<String> transactieField = getComponent();
    LookAheadComboBox<String> transactieField = getComponent();
    return transactieField.getSelectedItem() != null;
  }

  @Override
  public boolean isValid() {
    if (getComponentDescriptor().isOptional()  &&  !isFilledIn()) {
      return true;
    }
//    JComboBox<String> transactieField = getComponent();
    LookAheadComboBox<String> transactieField = getComponent();
    return transactieField.getSelectedItem() != null;        
  }

  @Override
  public void setStatusHighlight() {
//    PgLookAheadTextField textField = (PgLookAheadTextField) getComponent();
//    if (isValid()  ||  textField.getText().length() == 0) {
//      textField.setForeground(Color.BLACK);
//    } else {
//      textField.setForeground(Color.RED);      
//    }
  }

  private String[] getTransactionTypesForSelectedAccount(FinanTransactionEntryStatus transactionEntryStatus) {
    Set<String> transactionTypes = new HashSet<String>();
    for (TransactionInfo transactionInfo: TransactionInfo.values()) {
      if (transactionInfo.getBankName().equals(transactionEntryStatus.getBank().getName())  &&
          transactionInfo.getAccountName().equals(transactionEntryStatus.getBankAccount().getName())) {
//        System.out.println("Adding: " + transactionInfo.getTransactionType());
        transactionTypes.add(transactionInfo.getTransactionType());
      } else {
//        System.out.println("Skipping: " + transactionInfo.getTransactionType());
      }
    }

    return transactionTypes.toArray(new String[transactionTypes.size()]);
  }
}
