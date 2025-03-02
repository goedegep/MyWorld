package goedegep.util.financetest;

import static org.junit.Assert.assertEquals;

import goedegep.util.finance.FinanceUtil;
import goedegep.util.fixedpointvalue.FixedPointValue;
import goedegep.util.money.PgCurrency;

import org.junit.Test;

public class FinanceUtilTest {
  @Test
  public void returnOnInvestmentTest() {
    assertEquals("Wrong return on investment", new FixedPointValue(0), FinanceUtil.returnOnInvestment(new PgCurrency(10000), new PgCurrency(10000), 1));
    assertEquals("Wrong return on investment", new FixedPointValue(500), FinanceUtil.returnOnInvestment(new PgCurrency(10000), new PgCurrency(10500), 1));
    assertEquals("Wrong return on investment", new FixedPointValue(500), FinanceUtil.returnOnInvestment(new PgCurrency(10000), new PgCurrency(11025), 2));
    assertEquals("Wrong return on investment", new FixedPointValue(500), FinanceUtil.returnOnInvestment(new PgCurrency(10000), new PgCurrency(11577), 3));
    assertEquals("Wrong return on investment", new FixedPointValue(-500), FinanceUtil.returnOnInvestment(new PgCurrency(10000), new PgCurrency(9500), 1));
    assertEquals("Wrong return on investment", new FixedPointValue(-500), FinanceUtil.returnOnInvestment(new PgCurrency(10000), new PgCurrency(9025), 2));
  }

}
