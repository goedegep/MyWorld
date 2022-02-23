package goedegep.app.finan.td;

import java.awt.Color;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Logger;

import javax.swing.JTextField;

import goedegep.appgen.swing.Customization;

public class DCNumberField extends DC<JTextField> {
  private static final Logger         LOGGER = Logger.getLogger(DCNumberField.class.getName());

  private static  NumberFormat nf = NumberFormat.getIntegerInstance();
  
  public DCNumberField(
      CDNumberField componentDescriptor,
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    setComponent(customization.getComponentFactory().createTextField(componentDescriptor.getColumns(),
        componentDescriptor.getToolTipText()));
  }
  
  @Override
  public Object getValue() {
    JTextField textField = getComponent();
    try {
      return (Integer) nf.parse(textField.getText()).intValue();
    } catch (ParseException e) {
      LOGGER.info("Number PARSE EXCEPTION ");
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
        
    boolean       returnValue = true;
    CDNumberField componentDescriptor = (CDNumberField) getComponentDescriptor();
    
    JTextField textField = (JTextField) getComponent();
    try {
      int value = nf.parse(textField.getText()).intValue();
      if (((componentDescriptor.getMinValue() != null)  &&  (value < componentDescriptor.getMinValue()))  ||
          ((componentDescriptor.getMaxValue() != null)  &&  (value > componentDescriptor.getMaxValue()))) {
        returnValue = false;
      }
    } catch (ParseException e) {
      LOGGER.info("Number PARSE EXCEPTION ");
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
