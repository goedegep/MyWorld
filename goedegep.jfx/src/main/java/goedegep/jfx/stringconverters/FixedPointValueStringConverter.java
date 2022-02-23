package goedegep.jfx.stringconverters;

import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.fixedpointvalue.FixedPointValueFormat;

public class FixedPointValueStringConverter extends FormatBasedStringConverterAndChecker<FixedPointValue> {
  
  public FixedPointValueStringConverter() {
    super(new FixedPointValueFormat());
  }
  
  private FixedPointValueStringConverter(FixedPointValueFormat fixedPointValueFormat) {
    super(fixedPointValueFormat);
  }
}


