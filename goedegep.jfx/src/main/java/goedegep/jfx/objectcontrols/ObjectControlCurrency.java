package goedegep.jfx.objectcontrols;

import java.text.ParseException;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
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
public class ObjectControlCurrency extends ObjectControlTextField<PgCurrency> {
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
  
  /**
   * Constructor
   * 
   * @param pgCurrency the initial value
   * @param width width of the TextField
   * @param isOptional indicates whether the value is optional or not
   * @param toolTipText an optional tooltip text.
   */
  public ObjectControlCurrency(CustomizationFx customization, PgCurrency pgCurrency, double width, boolean isOptional, String toolTipText) {
    super(customization, pgCurrency, width, isOptional, toolTipText);
    
    setDefaultValidFactorRange();
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
    
    if (!(valueAsString == null)  &&  !valueAsString.isEmpty()) {
      try {
        currency = CF.parse(valueAsString);
        if (currency != null) {
          if ((minimumFactor != null)  &&
              (currency.getFactor() < minimumFactor)) {
              errorText = "not enough digits after the comma";
              return null;
          } else if ((maximumFactor != null)  &&
                     (currency.getFactor() > maximumFactor)) {
            errorText = "too much digits after the comma";
            return null;
          }
        }
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
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("ObjectControl type=Currency");
    buf.append(", id=").append(ocGetId() != null ? ocGetId() : "<null>");
    buf.append(", value=").append(value != null ? value : "<null>");
    buf.append(", referenceValue=").append(referenceValue != null ? referenceValue : "<null>");
    
    return buf.toString();
  }
}
