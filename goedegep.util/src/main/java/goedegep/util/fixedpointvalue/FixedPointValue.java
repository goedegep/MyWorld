package goedegep.util.fixedpointvalue;

import java.util.Comparator;
import java.util.Objects;

/**
 * TODO Check whether this can be replaced by java.math.BigDecimal
 */
public class FixedPointValue implements Comparator<FixedPointValue>, Comparable<FixedPointValue> {
  long    value;
  int     factor;

  public FixedPointValue(long value, int factor) {
    this.value = value;
    this.factor = factor;
  }

  public FixedPointValue(long value) {
    this(value, 100);
  }

  public FixedPointValue cloneFixedPointValue() {
    return new FixedPointValue(this.value, this.factor);
  }

  public long getValue() {
    return value;
  }

  public int getFactor() {
    return factor;
  }

  public FixedPointValue add(FixedPointValue fixedPointValue) {
    if (factor != fixedPointValue.factor) {
      throw new IllegalArgumentException("Incompatible factors, can not add factor " +
          fixedPointValue.factor + " to this factor " + factor);
    }
    return new FixedPointValue(value + fixedPointValue.value, factor);
  }
  
  public FixedPointValue subtract(FixedPointValue fixedPointValue) {
    if (factor != fixedPointValue.factor) {
      throw new IllegalArgumentException("Incompatible factors, can not subtract factor " +
          fixedPointValue.factor + " from factor " + this.factor);
    }
    return new FixedPointValue(value - fixedPointValue.value, factor);
  }

  public FixedPointValue multiply(long factor) {
    return new FixedPointValue(value * factor, this.factor);
  }

  public FixedPointValue multiply(double factor) {
    return new FixedPointValue((long) (value * factor + 0.5d), this.factor);
  }

  public FixedPointValue multiply(FixedPointValue factor) {
    return new FixedPointValue((value * factor.value) / factor.factor, this.factor);
  }

  public FixedPointValue divide(long factor) {
    return new FixedPointValue((value + factor/2) / factor, this.factor);
  }

  public FixedPointValue divide(double factor) {
    return new FixedPointValue(Math.round(value / factor), this.factor);
  }

  public FixedPointValue divide(FixedPointValue divider, int resultFactor) {
    FixedPointValue thisWithAdaptedFactor = certifyMinimalFactor(resultFactor);
    FixedPointValue dividerWithAdaptedFactor = divider.certifyMinimalFactor(resultFactor);
    return new FixedPointValue((thisWithAdaptedFactor.value * dividerWithAdaptedFactor.getFactor() + dividerWithAdaptedFactor.getValue() / 2) / dividerWithAdaptedFactor.getValue(), thisWithAdaptedFactor.factor);
  }

  public FixedPointValue divideWithRoundUp(double factor) {
    return new FixedPointValue(Math.round((value + (factor / 2)) / factor), this.factor);
  }
  
  public FixedPointValue modulo(long factor) {
    return new FixedPointValue(value % factor, this.factor);
  }

  public FixedPointValue changeSign() {
    return new FixedPointValue(-value, factor);
  }

  public FixedPointValue abs() {
    if (isNegative()) {
      return changeSign();
    } else {
      return this;
    }
  }

  
  /**
   * Returns the object itself if it already has the specified factor,
   * else it returns an object which is converted to the specified factor.
   * @param factor The factor to be certified.
   * @return
   */
  public FixedPointValue certifyFactor(int factor) {
    if (this.factor == factor) {
      return this;
    } else {
      return changeFactor(factor);
    }
  }

  public FixedPointValue certifyMinimalFactor(int factor) {
    if (this.factor >= factor) {
      return this;
    } else {
      return changeFactor(factor);
    }
  }

  public FixedPointValue changeFactor(int factor) {
    long newValue = value;
    int  currentFactor = this.factor;

    if (factor > currentFactor) {
      while (factor > currentFactor) {
        newValue *= 10;
        currentFactor *= 10;
      }
    } else {
      long divider = currentFactor / factor;
      newValue = (newValue + (divider/2)) / divider;
    }

    return new FixedPointValue(newValue, factor);
  }
  
  public FixedPointValue round() {
    return this.changeFactor(1).changeFactor(this.factor);
  }
  
  public FixedPointValue roundUp() {
    long fraction = this.value % this.factor;
    long whole = this.value / this.factor;
    if (fraction != 0) {
      whole++;
    }
    return new FixedPointValue(whole * this.factor, this.factor);
  }
  
  public boolean isLessThan(FixedPointValue fixedPointValue) {
    return compareTo(fixedPointValue) == -1;
  }

  public boolean isGreaterThan(FixedPointValue fixedPointValue) {
    return compareTo(fixedPointValue) == 1;
  }

  public boolean isNegative() {
    if (value < 0L) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isZero() {
    if (value == 0L) {
      return true;
    } else {
      return false;
    }
  }
  
  public int compareTo(FixedPointValue fixedPointValue) {
    return compare(this, fixedPointValue);
  }
  
  public int compare(FixedPointValue fixedPointValue1, FixedPointValue fixedPointValue2) {
    if ((fixedPointValue1.factor != fixedPointValue2.factor)) {
//      // If the factors aren't equal, the fixedPointValues aren't equal.
//      // The factors are made equal, only to return -1 or 1.
//      if (fixedPointValue1.factor < fixedPointValue2.factor) {
//        fixedPointValue1.certifyFactor(fixedPointValue2.factor);
//      } else {
//        fixedPointValue2.certifyFactor(fixedPointValue1.factor);
//      }
      throw new IllegalArgumentException("Imcompatible factors");
    }
    if (fixedPointValue1.value < fixedPointValue2.value) {
      return -1;
    } else if (fixedPointValue1.value > fixedPointValue2.value) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }

    if (this == object) {
      return true;
    }

    if (getClass() != object.getClass()) {
      return false;
    }
    
    FixedPointValue money = (FixedPointValue) object;
    if ((this.factor == money.factor)  &&
        (this.value == money.value)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
      return Objects.hash(factor, value);
  }
  
  public String toString() {
    return ("value = " + value + ", factor = " + factor);
  }
  
  public static FixedPointValue max(FixedPointValue a, FixedPointValue b) {
	if (a.isGreaterThan(b)) {
		return a;
	} else {
		return b;
	}
  }
  
  public static FixedPointValue min(FixedPointValue a, FixedPointValue b) {
	if (a.isGreaterThan(b)) {
		return b;
	} else {
		return a;
	}
  }

  public double doubleValue() {
    double doubleValue = value;
    
    return doubleValue / factor;
  }
  
  public long getIntegerPart() {
    return value / factor;
  }

  public boolean hasFraction() {
    return (value % factor) != 0;
  }

  public FixedPointValue getFraction() {
    return new FixedPointValue(value % factor, factor);
  }
}