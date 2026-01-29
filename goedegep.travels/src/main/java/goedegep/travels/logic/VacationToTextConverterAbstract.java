package goedegep.travels.logic;

import goedegep.travels.model.Vacation;
import goedegep.util.datetime.FlexDateFormat;


public class VacationToTextConverterAbstract {
  protected static final FlexDateFormat FDF = new FlexDateFormat();

  protected String getVacationTitle(Vacation vacation) {
    StringBuilder buf = new StringBuilder();
    
    if (vacation.isSetDate()) {
      buf.append(FDF.format(vacation.getDate()));
      
      if (vacation.isSetEndDate()) {
        buf.append(" - ");
        buf.append(FDF.format(vacation.getEndDate()));
      }
    }
    
    if (vacation.isSetTitle()) {
      if (vacation.isSetDate()) {
        buf.append(" ");
      }
      
      buf.append(vacation.getTitle());
    }
    
    return buf.toString();
  }

}
