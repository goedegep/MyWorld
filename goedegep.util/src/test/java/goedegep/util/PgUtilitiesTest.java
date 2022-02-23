package goedegep.util;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PgUtilitiesTest {
  @Test
  public void equalsTest() {
    assertTrue("Wrong result, comparing null to null should return true", PgUtilities.equals(null, null));
    assertFalse("Wrong result, comparing a value to null should return false", PgUtilities.equals(Integer.valueOf(4), null));
    assertFalse("Wrong result, comparing null to a value should return false", PgUtilities.equals(null, Integer.valueOf(4)));
    assertTrue("Wrong result, comparing two equal Integers should return true", PgUtilities.equals(Integer.valueOf(4), Integer.valueOf(4)));
    assertFalse("Wrong result, comparing two different Integers should return false", PgUtilities.equals(Integer.valueOf(4), Integer.valueOf(5)));
  }
}
