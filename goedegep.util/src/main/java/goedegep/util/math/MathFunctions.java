package goedegep.util.math;

/**
 * This class provides mathematical functions.
 */
public class MathFunctions {
  
  /**
   * Define a private constructor, so this class cannot be instantiated.
   */
  private MathFunctions() {
    
  }
  
  /**
   * Calculate the square root of a value.
   * An IllegalArgument
   * 
   * @param value the value of which the square root is to be calculated. This value may not be negative.
   * @return the square root of the value
   * @exception IllegalArgumentException thrown if the value is negative.
   */
  public static long squareRoot(long value) throws IllegalArgumentException {
    
    if (value < 0) {
      throw new IllegalArgumentException("value may not be negative, value = " + value);
    }
    
    long result;
    long bit;
    int g;
    
    for (bit = 0x4000000000000000l, g = 31; bit > value; bit >>= 2) {
      g--;
    }
    
    value -= bit;
    result = bit;
    
    for ( ; g > 0; g--) {
      bit >>= 2;
      result |= bit;
      
      if (result <= value) {
        value -= result;
        result = (result + bit) >> 1;
      } else {
        result = (result - bit) >> 1;
      }
    }
    
    return result;
  }
}
