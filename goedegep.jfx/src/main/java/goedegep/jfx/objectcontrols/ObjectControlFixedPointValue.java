package goedegep.jfx.objectcontrols;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
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
  
  /**
   * Minimum factor for the FixedPointValue.
   */
  protected Integer minimumFactor = null;
  
  /**
   * Maximum factor for the FixedPointValue.
   */
  protected Integer maximumFactor = null;
  
  public ObjectControlFixedPointValue(CustomizationFx customization, FixedPointValue objectValue, double width, boolean isOptional, String toolTipText) {
    super(customization, new FixedPointValueStringConverter(), objectValue, width, isOptional, toolTipText);
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
  
  /**
   * {@inheritDoc}
   */
  @Override
  public FixedPointValue ociDetermineValue(Object source) {
    FixedPointValue value = stringToObject(ocGetControl().getText().trim());
    
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
   * Check whether value satisfies the constraints.
   * 
   * @param fixedPointValue the fixedPointValue to check.
   * @return true if the {@code fixedPointValue} satisfies the constraints.
   */
  private boolean isDataValid(FixedPointValue fixedPointValue) {
    boolean valueIsValid = true;

    try {
    if ((minimumFactor != null)  &&
        (fixedPointValue.getFactor() < minimumFactor)) {
      valueIsValid = false;
      errorText = "not enough digits after the comma";
    } else if ((maximumFactor != null)  &&
               (fixedPointValue.getFactor() > maximumFactor)) {
      valueIsValid = false;
      errorText = "too much digits after the comma";
    }

    return valueIsValid;
    } catch (RuntimeException e) {
      return false;
    }
  }

}
