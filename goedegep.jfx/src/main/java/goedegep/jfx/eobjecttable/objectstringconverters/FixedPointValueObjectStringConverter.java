package goedegep.jfx.eobjecttable.objectstringconverters;

import goedegep.util.fixedpointvalue.FixedPointValueFormat;

public class FixedPointValueObjectStringConverter extends FormatBasedObjectStringConverter {
  
  public FixedPointValueObjectStringConverter() {
    super(new FixedPointValueFormat());
  }
  
  private FixedPointValueObjectStringConverter(FixedPointValueFormat fixedPointValueFormat) {
    super(fixedPointValueFormat);
  }
}


