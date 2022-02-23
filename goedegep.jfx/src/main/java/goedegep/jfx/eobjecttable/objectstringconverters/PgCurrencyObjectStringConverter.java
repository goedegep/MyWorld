package goedegep.jfx.eobjecttable.objectstringconverters;

import goedegep.util.money.PgCurrencyFormat;

public class PgCurrencyObjectStringConverter extends FormatBasedObjectStringConverter {
  
  public PgCurrencyObjectStringConverter() {
    super(new PgCurrencyFormat(0, false, false, false));
  }
  
  public PgCurrencyObjectStringConverter(PgCurrencyFormat pgCurrencyFormat) {
    super(pgCurrencyFormat);
  }
}

