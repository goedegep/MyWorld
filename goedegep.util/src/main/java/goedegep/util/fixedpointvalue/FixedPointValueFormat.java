package goedegep.util.fixedpointvalue;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

@SuppressWarnings("serial")
public class FixedPointValueFormat extends Format {
  int     minimumLength;
  boolean alwaysSign;
  boolean alwaysSignPosition;
  boolean onlyFractionIfNotZero = false;

  public FixedPointValueFormat(int minimumLength, boolean alwaysSign,
                          boolean alwaysSignPosition) {
    this.minimumLength = minimumLength;
    this.alwaysSign = alwaysSign;
    this.alwaysSignPosition = alwaysSignPosition;
  }

  /**
   * Constructor for a default formatter.
   */
  public FixedPointValueFormat() {
    this(0, false, false);
  }

  public FixedPointValueFormat(int minimumLength) {
    this(minimumLength, false, false);
  }

  public FixedPointValue parse(String valueString) throws ParseException {
    return parse(valueString, null);
  }
  
  public FixedPointValueFormat(boolean onlyFractionIfNotZero) {
    this(0, false, false);
    this.onlyFractionIfNotZero = onlyFractionIfNotZero;
  }

  public FixedPointValue parse(String amountString, Integer minimumFactor) throws ParseException {
    // values for the FixedPointValue
    long    value = 0;
    boolean minusSign = false;
    int     factor = 1;
    
    // loop values
    int     i = 0;
    char    c;
    
    // status
    boolean prefixesReady = false;
    boolean valueReady = false;
    boolean signSeen = false;
    boolean beforeCommaDigitSeen = false;
    boolean afterComma = false;
    boolean afterCommaDigitSeen = false;

    // handle optional sign character.
    // Allow any order of sign, and spaces.
    // However only one sign is allowed.
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
            throw new ParseException("Meer dan 1 min teken in fixed point waarde.", 0);
          }
        } else if (c == ' ') {
          // just skip spaces.
        } else {
          throw new ParseException("Ongeldig teken in fixed point waarde.", 0);
        }
      }
    }

    while (!valueReady) {
      if (i >= amountString.length()) {
        valueReady = true;
      } else {
        c = amountString.charAt(i++);
        if (Character.isDigit(c)) {
          value = 10 * value + Character.getNumericValue(c);
          if (afterComma) {
            afterCommaDigitSeen = true;
            factor *= 10;
          } else {
            beforeCommaDigitSeen = true;
          }
        } else if (c == ',') {
          afterComma = true;
        } else {
          throw new ParseException("Ongeldig karakter '" + c + "' in fixed point waarde.", 0);
        }
      }
    }

    if (minusSign) {
      value = -value;
    }

    if (!beforeCommaDigitSeen  ||
        (afterComma && !afterCommaDigitSeen)) {
      throw new ParseException("Geen cijfers voor de comma in fixed point waarde.", 0);
    }
    
    if (minimumFactor != null) {
      while (factor < minimumFactor) {
        value *= 10;
        factor *= 10;
      }
    }

    return new FixedPointValue(value, factor);
  }

  @Override
  public StringBuffer format(Object value, StringBuffer output, FieldPosition arg2) {
    if (value == null) {
      for (int i = 1; i <= minimumLength; i++) {
        output.append('-');
      }

      return output;
    }
    
    if (!(value instanceof FixedPointValue)) {
      throw new IllegalArgumentException();
    }
    
    FixedPointValue fixedPointValue = (FixedPointValue) value;
   
    long fpValue = fixedPointValue.getValue();

    if (fpValue < 0) {
      fpValue = -fpValue;
      output.append('-');
    } else {
      if (alwaysSign) {
        output.append('+');
      } else if (alwaysSignPosition) {
        output.append(' ');
      }
    }

    int factor = fixedPointValue.getFactor();
    long fraction = fpValue % factor;
    fpValue = fpValue / factor;
    int digitsAfterComma = 0;
    if (!onlyFractionIfNotZero  ||  (fraction != 0)) {
      while (factor > 1) {
        digitsAfterComma++;
        factor /= 10;
      }
    }

    String      beforeCommaString = Long.toString(fpValue);

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
  public Object parseObject(String valueString, ParsePosition parsePosition) {
    if (parsePosition.getIndex() == 0) {
      try {
        FixedPointValue result = parse(valueString);
        parsePosition.setIndex(valueString.length() - 1);
        return result;
      } catch (ParseException e) {
        parsePosition.setErrorIndex(0);
        return null;
      }
    } else {
      return null;
    }
  }
}