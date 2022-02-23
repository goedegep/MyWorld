package goedegep.jfx.controls;

import java.util.logging.Logger;

import goedegep.jfx.stringconverters.IntegerObjectStringConverter;
import goedegep.util.money.PgCurrency;

/**
 * This class provides a TextField to be used to edit a {@link PgCurrency} value.
 * <p>
 * <b>Valid factor range.</b><br/>
 * Currencies typically are using a resolution of cents (a factor of 100). However, usually in calculations, higher factors can be used.<br/>
 * Therefore a valid factor range can be set by specifying a minimum and maximum factor, see {@link #setValidValueRange}.
 * By default both minimumFactor and maximumFactor are set to 100.
 */
public class ObjectControlInteger extends TextFieldObjectInput<Integer> {
  @SuppressWarnings("unused")
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlInteger.class.getName());
    
  /**
   * Minimum value.
   */
  protected Integer minimumValue = null;
  
  /**
   * Maximum value.
   */
  protected Integer maximumValue = null;
  
  public ObjectControlInteger(Integer integer, double width, boolean isOptional, String toolTipText) {
    super(new IntegerObjectStringConverter(), integer, width, isOptional, toolTipText);
    
    setDefaultValidValueRange();
  }
  
  public ObjectControlInteger(String text, double width, boolean isOptional, String toolTipText) {
    super(new IntegerObjectStringConverter(), text, width, isOptional, toolTipText);
    
    setDefaultValidValueRange();
  }
  
  @Override
  protected boolean isEnteredDataValid(StringBuilder errorMessageBuffer) {
    if (getText() == null) {
      return false;
    }
    
    boolean valueIsValid = true;

    try {
    Integer value = getObjectValue();
    if ((minimumValue != null)  &&
        (value < minimumValue)) {
      valueIsValid = false;
      if (errorMessageBuffer != null) {
        errorMessageBuffer.append("value too low");
      }
    } else if ((maximumValue != null)  &&
        (value > maximumValue)) {
      valueIsValid = false;
      if (errorMessageBuffer != null) {
        errorMessageBuffer.append("value too high");
      }
    }
    } catch (NumberFormatException e) {
      valueIsValid = false;
    }

    return valueIsValid;
  }

  /**
   * Set the valid factor range for fixed point values.
   * 
   * @param minimumValue The minumum factor. Null means no limit.
   * @param maximumValue The maximum factor. Null means no limit.
   */
  public void setValidValueRange(Integer minimumValue, Integer maximumValue) {
    if (minimumValue != null  &&  maximumValue != null  &&  maximumValue < minimumValue) {
      throw new IllegalArgumentException("maximumValue cannot be smaller than minimumValue. Arguments: minimumValue=" + minimumValue + ", maximumValue=" + maximumValue);
    }
    
    this.minimumValue = minimumValue;
    this.maximumValue = maximumValue;
  }

  /**
   * Set the default valid factor range.
   * <p>
   * Most currency values are in 2 decimals, so this is the default.
   */
  private void setDefaultValidValueRange() {
    minimumValue = null;
    maximumValue = null;
  }

}
