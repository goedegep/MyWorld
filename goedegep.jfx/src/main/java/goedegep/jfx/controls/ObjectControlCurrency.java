package goedegep.jfx.controls;

import java.text.ParseException;
import java.util.logging.Logger;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

/**
 * This class provides a TextField to be used to edit a {@link PgCurrency} value.
 * <p>
 * <b>Valid factor range.</b><br/>
 * Currencies typically are using a resolution of cents (a factor of 100). However, usually in calculations, higher factors can be used.<br/>
 * Therefore a valid factor range can be set by specifying a minimum and maximum factor, see {@link #setValidFactorRange}.
 * By default both minimumFactor and maximumFactor are set to 100.
 */
public class ObjectControlCurrency extends TextFieldObjectInput<PgCurrency> {
  private static final Logger         LOGGER = Logger.getLogger(ObjectControlCurrency.class.getName());
  private static final PgCurrencyFormat  CF = new PgCurrencyFormat();
    
  /**
   * Minimum factor for the currency.
   */
  protected Integer minimumFactor = null;
  
  /**
   * Maximum factor for the currency.
   */
  protected Integer maximumFactor = null;
  
  public ObjectControlCurrency(PgCurrency pgCurrency, double width, boolean isOptional, String toolTipText) {
    super(pgCurrency, width, isOptional, toolTipText, false);
    
    setDefaultValidFactorRange();
  }
  
  public ObjectControlCurrency(String text, double width, boolean isOptional, String toolTipText) {
    super(text, width, isOptional, toolTipText);
    
    setDefaultValidFactorRange();
  }

//  @Override
//  public PgCurrency getValue() {
//    try {
//      return CF.parse(getText());
//    } catch (ParseException | NullPointerException n) {
//      LOGGER.severe("Geldbedrag PARSE EXCEPTION ");
//      return null;
//    }
//  }
  
  @Override
  protected boolean isEnteredDataValid(StringBuilder errorMessageBuffer) {
    if (getText() == null) {
      return false;
    }
    
    boolean valueIsValid = true;
    
    try {
      PgCurrency currency = CF.parse(getText());
      if ((minimumFactor != null)  &&
          (currency.getFactor() < minimumFactor)) {
        valueIsValid = false;
        if (errorMessageBuffer != null) {
          errorMessageBuffer.append("not enough digits after the comma");
        }
      } else if ((maximumFactor != null)  &&
                 (currency.getFactor() > maximumFactor)) {
        valueIsValid = false;
        if (errorMessageBuffer != null) {
          errorMessageBuffer.append("too much digits after the comma");
        }
      }
    } catch (ParseException e) {
      valueIsValid = false;
      if (errorMessageBuffer != null) {
        errorMessageBuffer.append(e.getMessage());
      }
    }

    return valueIsValid;
  }

  /**
   * Set the valid factor range for fixed point values.
   * 
   * @param minimumFactor The minumum factor. Null means no limit.
   * @param maximumFactor The maximum factor. Null means no limit.
   */
  public void setValidFactorRange(Integer minimumFactor, Integer maximumFactor) {
    if (minimumFactor != null  &&  maximumFactor != null  &&  maximumFactor < minimumFactor) {
      throw new IllegalArgumentException("maximumFactor cannot be smaller than minimumFactor. Arguments: minimumFactor=" + minimumFactor + ", maximumFactor=" + maximumFactor);
    }
    
    this.minimumFactor = minimumFactor;
    this.maximumFactor = maximumFactor;
  }

  /**
   * Set the default valid factor range.
   * <p>
   * Most currency values are in 2 decimals, so this is the default.
   */
  private void setDefaultValidFactorRange() {
    minimumFactor = 100;
    maximumFactor = 100;
  }

  @Override
  protected PgCurrency stringToObject(String valueAsString) {
    PgCurrency currency = null;
    
    if (!valueAsString.isEmpty()) {
      try {
        currency = CF.parse(valueAsString);
      } catch (ParseException e) {
        LOGGER.info("ParseException on value: " + valueAsString);
      }
    }
    
    return currency;
  }
  
  @Override
  protected String objectToString(PgCurrency value) {
    return CF.format(value);
  }
}
