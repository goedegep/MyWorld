package goedegep.app.finan.td;

import goedegep.appgen.swing.Customization;
import goedegep.util.money.PgCurrencyFormat;

import java.awt.Color;
import java.text.ParseException;
import java.util.logging.Logger;

import javax.swing.JTextField;

public class DCCurrencyField extends DC<JTextField> {
  private static final Logger         LOGGER = Logger.getLogger(DCCurrencyField.class.getName());
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat();
  
  public DCCurrencyField(
      CDCurrencyField componentDescriptor,
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    setComponent(customization.getComponentFactory().createTextField(componentDescriptor.getColumns(),
        componentDescriptor.getToolTipText()));
  }
  
  @Override
  public Object getValue() {
    JTextField textField = getComponent();
    try {
      return CF.parse(textField.getText());
    } catch (ParseException e) {
      LOGGER.info("Geldbedrag PARSE EXCEPTION ");
      return null;
    }
  }

  public boolean isFilledIn() {
    return ((JTextField) getComponent()).getText().length() != 0;
  }

  @Override
  public boolean isValid() {
    
    if (getComponentDescriptor().isOptional()  &&  !isFilledIn()) {
      return true;
    }
        
    boolean returnValue = true;
    
    JTextField textField = (JTextField) getComponent();
    try {
      CF.parse(textField.getText());
    } catch (ParseException e) {
      LOGGER.info("Geldbedrag PARSE EXCEPTION ");
      returnValue = false;
    }
    
    return returnValue;
  }

  @Override
  public void setStatusHighlight() {
    JTextField textField = (JTextField) getComponent();
    if (isValid()  ||  textField.getText().length() == 0) {
      textField.setForeground(Color.BLACK);
    } else {
      textField.setForeground(Color.RED);      
    }
  }
}
