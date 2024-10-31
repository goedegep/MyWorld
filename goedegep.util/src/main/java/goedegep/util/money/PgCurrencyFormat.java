package goedegep.util.money;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

@SuppressWarnings("serial")
public class PgCurrencyFormat extends Format {
  public static char EURO_SYMBOL_CHAR = '€';
  public static char FLORIN_SYMBOL_CHAR = 'ƒ';
  public static char EURO_SYMBOL_ASCII_CHAR = 'e';
  public static char FLORIN_SYMBOL_ASCII_CHAR = 'f';
  public static String EURO_SYMBOL_STR = "€";
  public static String FLORIN_SYMBOL_STR = "ƒ";
  
  private int     minimumLength;
  private boolean alwaysSign;
  private boolean alwaysSignPosition;
  private boolean useASCII;
  private boolean noCurrencySymbol;

  public PgCurrencyFormat(int minimumLength, boolean alwaysSign,
                          boolean alwaysSignPosition, boolean useASCII, boolean noCurrencySymbol) {
    this.minimumLength = minimumLength;
    this.alwaysSign = alwaysSign;
    this.alwaysSignPosition = alwaysSignPosition;
    this.useASCII = useASCII;
    this.noCurrencySymbol = noCurrencySymbol;
  }

  public PgCurrencyFormat(int minimumLength, boolean alwaysSign,
      boolean alwaysSignPosition, boolean useASCII) {
    this(minimumLength, alwaysSign, alwaysSignPosition, useASCII, false);
  }

  public PgCurrencyFormat(int minimumLength, boolean alwaysSign, boolean alwaysSignPosition) {
    this(minimumLength, alwaysSign, alwaysSignPosition, false, false);
  }

  public PgCurrencyFormat() {
    this(0, false, false, false, false);
  }

  public PgCurrencyFormat(int minimumLength) {
    this(minimumLength, false, false, false, false);
  }

  public PgCurrency parse(String amountString) throws ParseException {
    return parse(amountString, null);
  }
  
  public PgCurrency parse(String amountString, Integer minimumFactor) throws ParseException {
    // currency values
    int     currency = PgCurrency.DEFAULT_CURRENCY;
    long    amount = 0;
    boolean minusSign = false;
    int     factor = 1;
    
    // loop values
    int     i = 0;
    char    c;
    
    // status
    boolean prefixesReady = false;
    boolean amountReady = false;
    boolean signSeen = false;
    boolean currencySeen = false;
    boolean beforeCommaDigitSeen = false;
    boolean afterComma = false;
    boolean afterCommaDigitSeen = false;

    // handle optional sign and currency indication characters.
    // Allow any order of sign, currency indication and spaces.
    // However only one sign and one currency indication is allowed.
    while (!prefixesReady) {
      if (i >= amountString.length()) {
        prefixesReady = true;
      } else {
        c = amountString.charAt(i++);
        if (Character.isDigit(c)) {
          prefixesReady = true;
          i--;
        } else if (c == '-') {
          if (!signSeen) {
            minusSign = true;
            signSeen = true;
          } else {
            throw new ParseException("More than one minus sign in currency string.", 0);
          }
        } else if ((c == FLORIN_SYMBOL_ASCII_CHAR)  ||  (c == FLORIN_SYMBOL_CHAR)) {
          if (!currencySeen) {
            currency = PgCurrency.GUILDER;
            currencySeen = true;
          } else {
            throw new ParseException("More than one currency character in currency string.", 0);
          }
        } else if ((c == EURO_SYMBOL_ASCII_CHAR)  || (c == EURO_SYMBOL_CHAR)) {
          if (!currencySeen) {
            currency = PgCurrency.EURO;
            currencySeen = true;
          } else {
            throw new ParseException("ore than one currency character in currency string.", 0);
          }
        } else if (c != ' ') {
          throw new ParseException("Invalid character in currency string", 0);
        }
      }
    }

    while (!amountReady) {
      if (i >= amountString.length()) {
        amountReady = true;
      } else {
        c = amountString.charAt(i++);
        if (Character.isDigit(c)) {
          amount = 10 * amount + Character.getNumericValue(c);
          if (afterComma) {
            afterCommaDigitSeen = true;
            factor *= 10;
          } else {
            beforeCommaDigitSeen = true;
          }
        } else if (c == ',') {
          afterComma = true;
        } else {
          throw new ParseException("Ongeldig karakter '" + c + "' in geldbedrag.", 0);
        }
      }
    }

    // Geen comma gezien, gebruik standaard factor 100 (centen).
    if (!afterComma) {
      factor = 100;
      amount *= 100;
    }

    if (minusSign) {
      amount = -amount;
    }

    if (!beforeCommaDigitSeen  ||
        (afterComma && !afterCommaDigitSeen)) {
      throw new ParseException("Geen cijfers voor de comma in geldbedrag.", 0);
    }
    
    if (minimumFactor != null) {
      while (factor < minimumFactor) {
        amount *= 10;
        factor *= 10;
      }
    }

    return new PgCurrency(currency, amount, factor);
  }


  @Override
  public StringBuffer format(Object value, StringBuffer output, FieldPosition arg2) {
    PgCurrency currency = (PgCurrency) value;
//    StringBuffer output = new StringBuffer();

    if (currency == null) {
      for (int i = 1; i <= minimumLength; i++) {
        output.append('-');
      }

//      return output.toString();
      return output;
    }

    long         amount = currency.getAmount();

    if (amount < 0) {
      amount = -amount;
      output.append('-');
    } else {
      if (alwaysSign) {
        output.append('+');
      } else if (alwaysSignPosition) {
        output.append(' ');
      }
    }

    if (!noCurrencySymbol) {
      switch (currency.getCurrency()) {
      case PgCurrency.EURO:
        if (useASCII) {
          output.append(EURO_SYMBOL_ASCII_CHAR);
        } else {
          output.append(EURO_SYMBOL_CHAR);
        }
        break;

      case PgCurrency.GUILDER:
        if (useASCII) {
          output.append(FLORIN_SYMBOL_ASCII_CHAR);
        } else {
          output.append(FLORIN_SYMBOL_CHAR);
        }
        break;

      default:
        throw new IllegalArgumentException("Illegal currency");
      }
    }

    int factor = currency.getFactor();
    long fraction = amount % factor;
    amount = amount / factor;
    int digitsAfterComma = 0;
    while (factor > 1) {
      digitsAfterComma++;
      factor /= 10;
    }

    String      beforeCommaString = Long.toString(amount);

    int length =
      output.length() +                // length so far
      beforeCommaString.length() +     // digits before the comma
      (digitsAfterComma > 0 ? 1 : 0) + // comma, if applicable
      digitsAfterComma;                // digits after comma
    while (length++ < minimumLength) {
      output.append(' ');
    }

    output.append(beforeCommaString);
    
    if (digitsAfterComma > 0) {
      output.append(",");
      length = output.length();
      while (digitsAfterComma-- > 0) {
        int digit = (int) fraction % 10;
        output.insert(length, digit);
        fraction /= 10;
      }
    }
    

    return output;
  }

  @Override
  public Object parseObject(String arg0, ParsePosition arg1) {
    // TODO Auto-generated method stub
    return null;
  }
}