package goedegep.util.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathFunctionsTest {
  
  @Test
  public void squareRootTest() {
    assertEquals("Wrong result", 3, MathFunctions.squareRoot(9));
    assertEquals("Wrong result", 3, MathFunctions.squareRoot(10));
    assertEquals("Wrong result", 89786543l, MathFunctions.squareRoot(89786543l * 89786543l));
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void squareRootTestNegativeValue() {
    MathFunctions.squareRoot(-9);
  }
  
}
