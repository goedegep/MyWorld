package goedegep.app.finan.td;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import goedegep.appgen.PgFormattedTextField;
import goedegep.appgen.swing.Customization;

public class DCDateField extends DC<PgFormattedTextField> {
  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("ddMMyyyy");

  public DCDateField(
      CDDateField componentDescriptor,
      TransactionEntryStatus transactionEntryStatus, Customization customization) {
    super(componentDescriptor);
    GregorianCalendar calendar = new GregorianCalendar();
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH) + 1;
    NumberFormat numberFormat = new DecimalFormat("00");
    String todayString = numberFormat.format(day) + numberFormat.format(month) + calendar.get(Calendar.YEAR);
    PgFormattedTextField.FormatSpec dateFormatSpec = new PgFormattedTextField.FormatSpec("..-..-....", "**-**-****");
    PgFormattedTextField formattedTextField =
      customization.getComponentFactory().createPgFormattedTextField(todayString, 0, dateFormatSpec, componentDescriptor.getToolTipText());

    setComponent(formattedTextField);
  }  

  @Override
  public Object getValue() {
    PgFormattedTextField textField = getComponent();
    
    try {
      return LocalDate.parse(textField.getText(), DTF);
    } catch (DateTimeParseException e) {
      System.out.println("Rente datum PARSE EXCEPTION ");
      return null;
    }
  }

  public boolean isFilledIn() {
    return ((PgFormattedTextField) getComponent()).getText().length() != 0;
  }

  @Override
  public boolean isValid() {
    if (getComponentDescriptor().isOptional()  &&  !isFilledIn()) {
      return true;
    }
        
    boolean returnValue = true;
    
    PgFormattedTextField textField = (PgFormattedTextField) getComponent();
        
    try {
      LocalDate.parse(textField.getText(), DTF);
    } catch (DateTimeParseException e) {
      System.out.println("Rente datum PARSE EXCEPTION ");
      returnValue = false;
    }
    
    return returnValue;
  }

  @Override
  public void setStatusHighlight() {
    PgFormattedTextField textField = (PgFormattedTextField) getComponent();
    if (isValid()) {
      textField.setForeground(Color.BLACK);
    } else {
      textField.setForeground(Color.RED);      
    }
  }

}
