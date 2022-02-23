package goedegep.app.finan.td;

import java.awt.Color;
import java.text.ParseException;

import javax.swing.JTextField;

import goedegep.appgen.swing.Customization;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;

public class DCCommaValueField extends DC<JTextField> {
  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();

  public DCCommaValueField(
      CDCommaValueField componentDescriptor,
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    setComponent(customization.getComponentFactory().createTextField(componentDescriptor.getColumns(),
        componentDescriptor.getToolTipText()));
  }
  
  @Override
  public Object getValue() {
    JTextField textField = getComponent();
    CDCommaValueField componentDescriptor = (CDCommaValueField) getComponentDescriptor();
    try {
      FixedPointValue value = FPVF.parse(textField.getText());
      value = value.certifyFactor(componentDescriptor.getFactor());
      return value;
    } catch (ParseException e) {
      System.out.println("FixidPointValue PARSE EXCEPTION ");
      return null;
    }
  }

  public boolean isFilledIn() {
    return ((JTextField) getComponent()).getText().length() != 0;
  }

  @Override
  public boolean isValid() {
//  System.out.print("Checking component: " + getName() + " => ");
    
    if (getComponentDescriptor().isOptional()  &&  !isFilledIn()) {
      return true;
    }
    
    boolean       returnValue = true;
    CDCommaValueField componentDescriptor = (CDCommaValueField) getComponentDescriptor();
    
    Long value = (Long) getValue();
    if (value != null) {
      if (((componentDescriptor.getMinValue() != null)  &&  (value < componentDescriptor.getMinValue()))  ||
          ((componentDescriptor.getMaxValue() != null)  &&  (value > componentDescriptor.getMaxValue()))) {
        returnValue = false;
      }
    } else {
      returnValue = false;
    }
//    System.out.println(returnValue == true ? "true" : "false");
    
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