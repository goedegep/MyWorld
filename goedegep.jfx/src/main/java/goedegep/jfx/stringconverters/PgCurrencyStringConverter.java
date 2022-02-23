package goedegep.jfx.stringconverters;

import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class PgCurrencyStringConverter extends FormatBasedStringConverterAndChecker<PgCurrency> {
  
  public PgCurrencyStringConverter() {
    super(new PgCurrencyFormat(0, false, false, false));
  }
  
  public PgCurrencyStringConverter(PgCurrencyFormat pgCurrencyFormat) {
    super(pgCurrencyFormat);
  }
}

