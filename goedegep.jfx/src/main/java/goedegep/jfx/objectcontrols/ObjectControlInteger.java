package goedegep.jfx.objectcontrols;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
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
public class ObjectControlInteger extends ObjectControlTextField<Integer> {
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
  
  /**
   * Constructor.
   * 
   * @param integer initial value.
   * @param width The width of the TextField
   * @param isOptional Indicates whether the control is optional (if true) or mandatory.
   * @param toolTipText An optional ToolTip text.
   */
  public ObjectControlInteger(CustomizationFx customization, Integer integer, double width, boolean isOptional, String toolTipText) {
    super(customization, new IntegerObjectStringConverter(), integer, width, isOptional, toolTipText);
    
    setDefaultValidValueRange();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Integer ociDetermineValue(Object source) {
    Integer value = stringToObject(getControl().getText().trim());
    
    if (value == null) {
      return null;
    }
    
    if (isDataValid(value)) {
      return value;
    } else {
      return null;
    }
  }
  
  /**
   * Check whether value is within range (if set).
   * 
   * @param value the value to check.
   * @return true if the value is valid.
   */
  public boolean isDataValid(Integer value) {
    boolean valueIsValid = true;

    if ((minimumValue != null)  &&
        (value < minimumValue)) {
      valueIsValid = false;
      errorText = "value too low";
    } else if ((maximumValue != null)  &&
        (value > maximumValue)) {
      valueIsValid = false;
      errorText = "value too high";
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
   * Set the default valid range.
   */
  private void setDefaultValidValueRange() {
    minimumValue = null;
    maximumValue = null;
  }

}
