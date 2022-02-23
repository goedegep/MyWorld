package goedegep.app.finan.td;

import java.awt.Color;
import java.util.logging.Logger;

import goedegep.appgen.LookAheadTextField;
import goedegep.appgen.swing.Customization;
import goedegep.util.text.ListTextLookAhead;
import goedegep.util.text.TextLookAhead;

public class DCLookAheadTextField extends DC<LookAheadTextField> {
  private static final Logger         LOGGER = Logger.getLogger(DCLookAheadTextField.class.getName());

  public DCLookAheadTextField(
      CDLookAheadTextField componentDescriptor,
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    LOGGER.info("=>");
    TextLookAhead effRekTransactiesLookAhead = new ListTextLookAhead<String>(componentDescriptor.getOptions());
    LookAheadTextField transactieField =
      customization.getComponentFactory().createPgLookAheadTextField(null, 20, effRekTransactiesLookAhead, "kies transactiesoort");
    
    setComponent(transactieField);
    LOGGER.info("<=");
  }
  
  @Override
  public Object getValue() {
    LookAheadTextField textField = getComponent();
    if (textField != null) {
      return textField.getText();
    } else {
      return null;
    }
  }

  public boolean isFilledIn() {
    return ((LookAheadTextField) getComponent()).getText().length() != 0;
  }

  @Override
  public boolean isValid() {
    
    if (getComponentDescriptor().isOptional()  &&  !isFilledIn()) {
      return true;
    }
        
    boolean returnValue = false;
    
    LookAheadTextField textField = (LookAheadTextField) getComponent();
    String text = textField.getText();
    for (String option: ((CDLookAheadTextField) getComponentDescriptor()).getOptions()) {
      if (option.equals(text)) {
        returnValue = true;
        break;
      }
    }

    return returnValue;
  }

  @Override
  public void setStatusHighlight() {
    LookAheadTextField textField = (LookAheadTextField) getComponent();
    if (isValid()  ||  textField.getText().length() == 0) {
      textField.setForeground(Color.BLACK);
    } else {
      textField.setForeground(Color.RED);      
    }
  }
}
