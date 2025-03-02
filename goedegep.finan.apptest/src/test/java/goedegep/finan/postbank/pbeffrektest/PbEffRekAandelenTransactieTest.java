package goedegep.finan.postbank.pbeffrektest;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Month;

import goedegep.finan.effrek.EffRek;
import goedegep.finan.postbank.pbeffrek.PbEffRek;
import goedegep.finan.postbank.pbeffrek.PbEffRekAandelenTransactie;
import goedegep.finan.stocks.StockDepot;
import goedegep.util.money.PgCurrency;

import org.junit.Before;
import org.junit.Test;

public class PbEffRekAandelenTransactieTest {
  private StockDepot parentDepot;
  private EffRek effRek;
  private PbEffRekAandelenTransactie transactie;

  @Before
  public void setup() {
    effRek = new PbEffRek(parentDepot);
  }

  @Test
  public void testBerekenOrderKosten() {
    transactie = createTransactie(LocalDate.of(2000, Month.FEBRUARY, 29), 177, new PgCurrency(PgCurrency.EURO, 1790L));
    assertEquals("verkeerde order kosten", new PgCurrency(PgCurrency.EURO, 2026L), transactie.berekenOrderKosten());
    
    transactie = createTransactie(LocalDate.of(2000, Month.DECEMBER, 14), 200, new PgCurrency(PgCurrency.EURO, 5130L));
    assertEquals("verkeerde order kosten", new PgCurrency(PgCurrency.EURO, 4866L), transactie.berekenOrderKosten());
    
    transactie = createTransactie(LocalDate.of(2001, Month.DECEMBER, 10), 200, new PgCurrency(PgCurrency.EURO, 3400L));
    assertEquals("verkeerde order kosten", new PgCurrency(PgCurrency.EURO, 3520L), transactie.berekenOrderKosten());
    
    transactie = createTransactie(LocalDate.of(2010, Month.SEPTEMBER, 9), 13, new PgCurrency(PgCurrency.EURO, 11L));
    assertEquals("verkeerde order kosten", new PgCurrency(PgCurrency.EURO, 72L), transactie.berekenOrderKosten());
  }

  private PbEffRekAandelenTransactie createTransactie(LocalDate executionDate, int aantalEffecten, PgCurrency koers) {
    PbEffRekAandelenTransactie transactie = new PbEffRekAandelenTransactie(effRek);
    
    transactie.setExecutionDate(executionDate);
    transactie.setAantalEffecten(aantalEffecten);
    transactie.setKoers(koers);

    return transactie;
  }
}
