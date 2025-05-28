package goedegep.jfx.stringconverterandchecker;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

/**
 * This class is a {@link StringConverterAndChecker} for a {@link PgCurrency}.
 */
public class CurrencyStringConverterAndChecker extends FormatBasedStringConverterAndChecker<PgCurrency> {
  
  private static CurrencyStringConverterAndChecker defaultFormatInstance = null;
  
  /**
   * Get the instance of the {@code CurrencyStringConverterAndChecker} for the default currency format.
   * 
   * @return the {@code CurrencyStringConverterAndChecker} for the default currency format.
   */
  public static CurrencyStringConverterAndChecker getDefaultFormatInstance() {
    if (defaultFormatInstance == null) {
      defaultFormatInstance = new CurrencyStringConverterAndChecker();
    }
    
    return defaultFormatInstance;
  }
  
  /**
   * Constructor for the default currency format.
   */
  private CurrencyStringConverterAndChecker() {
    super(new PgCurrencyFormat(0, false, false, false));
  }
  
  /**
   * Constructor for a specific {@code PgCurrencyFormat}.
   * 
   * @param pgCurrencyFormat the {@code PgCurrencyFormat} to use.
   */
  public CurrencyStringConverterAndChecker(PgCurrencyFormat pgCurrencyFormat) {
    super(pgCurrencyFormat);
  }
}

