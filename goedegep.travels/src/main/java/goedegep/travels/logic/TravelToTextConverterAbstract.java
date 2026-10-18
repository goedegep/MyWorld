package goedegep.travels.logic;

import goedegep.travels.model.Travel;
import goedegep.util.datetime.FlexDateFormat;


/**
 * Abstract base class for converting Travel objects to text.
 */
public class TravelToTextConverterAbstract {
  protected static final FlexDateFormat FDF = new FlexDateFormat();

  // TODO is this the same as travel.getId()?
  protected String getTravelTitle(Travel travel) {
    StringBuilder buf = new StringBuilder();
    
    if (travel.isSetDate()) {
      buf.append(FDF.format(travel.getDate()));
      
      if (travel.isSetEndDate()) {
        buf.append(" - ");
        buf.append(FDF.format(travel.getEndDate()));
      }
    }
    
    if (travel.isSetTitle()) {
      if (travel.isSetDate()) {
        buf.append(" ");
      }
      
      buf.append(travel.getTitle());
    }
    
    return buf.toString();
  }

}
