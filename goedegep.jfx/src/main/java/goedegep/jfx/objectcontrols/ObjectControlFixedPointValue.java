package goedegep.jfx.objectcontrols;

import java.util.logging.Logger;

import goedegep.jfx.stringconverters.FixedPointValueStringConverter;
import goedegep.util.fixedpointvalue.FixedPointValue;

/**
 * This class provides a TextField to be used to edit a {@link FixedPointValue}.
 * <p>
 * <b>Valid factor range.</b><br/>
 * A valid factor range can be set by specifying a minimum and maxim factor, see {@link #setValidFactorRange}.
 * By default both minimumFactor and maximumFactor are set to null, which means no restriction.
 */
public class ObjectControlFixedPointValue extends ObjectControlTextField<FixedPointValue> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlFixedPointValue.class.getName());
//  private static final FixedPointValueFormat FPVF = new FixedPointValueFormat();
  
  /**
   * Minimum factor for the FixedPointValue.
   */
  protected Integer minimumFactor = null;
  
  /**
   * Maximum factor for the FixedPointValue.
   */
  protected Integer maximumFactor = null;
  
  public ObjectControlFixedPointValue(FixedPointValue objectValue, double width, boolean isOptional, String toolTipText) {
    super(new FixedPointValueStringConverter(), objectValue, width, isOptional, toolTipText);
  }
  
  public ObjectControlFixedPointValue(String text, double width, boolean isOptional, String toolTipText) {
    super(new FixedPointValueStringConverter(), text, width, isOptional, toolTipText);
  }
  
  /**
   * Set the valid factor range for fixed point values.
   * 
   * @param minimumFactor The minumum factor. Null means no limit.
   * @param maximumFactor The maximum factor. Null means no limit.
   */
  public void setValidFactorRange(int minimumFactor, int maximumFactor) {
    this.minimumFactor = minimumFactor;
    this.maximumFactor = maximumFactor;
  }
    
  @Override
  protected boolean isEnteredDataValid(StringBuilder buf) {
    boolean valueIsValid = true;

    try {
    FixedPointValue fixedPointValue = getObjectValue();
    if ((minimumFactor != null)  &&
        (fixedPointValue.getFactor() < minimumFactor)) {
      valueIsValid = false;
      if (buf != null) {
        buf.append("not enough digits after the comma");
      }
    } else if ((maximumFactor != null)  &&
               (fixedPointValue.getFactor() > maximumFactor)) {
      valueIsValid = false;
      if (buf != null) {
        buf.append("too much digits after the comma");
      }
    }

    
//    if (getText() == null) {
//      return false;
//    }
//    
//    boolean valueIsValid = true;
//    
//    try {
//      FixedPointValue fixedPointValue = FPVF.parse(getText());
//      if ((minimumFactor != null)  &&
//          (fixedPointValue.getFactor() < minimumFactor)) {
//        valueIsValid = false;
//        if (buf != null) {
//          buf.append("not enough digits after the comma");
//        }
//      } else if ((maximumFactor != null)  &&
//                 (fixedPointValue.getFactor() > maximumFactor)) {
//        valueIsValid = false;
//        if (buf != null) {
//          buf.append("too much digits after the comma");
//        }
//      }
//    } catch (ParseException e) {
//      valueIsValid = false;
//      if (buf != null) {
//        buf.append(e.getMessage());
//      }
//    }

    return valueIsValid;
    } catch (RuntimeException e) {
      return false;
    }
  }

//  @Override
//  protected FixedPointValue stringToObject(String valueAsString) {
//    FixedPointValue fixedPointValue = null;
//    
//    try {
//      fixedPointValue = FPVF.parse(valueAsString);
//    } catch (ParseException e) {
//      LOGGER.severe("ParseException on value: " + valueAsString);
//    }
//    
//    return fixedPointValue;
//  }
//  
//  @Override
//  protected String objectToString(FixedPointValue value) {
//    return FPVF.format(value);
//  }
}
