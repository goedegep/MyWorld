package goedegep.jfx.stringconverters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateObjectStringConverter extends FormatBasedStringConverterAndChecker<Date>  {
  
  public DateObjectStringConverter() {
    super(new SimpleDateFormat("dd-MM-yyyy"));
  }
  
  public DateObjectStringConverter(String pattern) {
    super(new SimpleDateFormat(pattern));
  }
  
  public DateObjectStringConverter(DateFormat dateFormat) {
    super(dateFormat);
  }

}
