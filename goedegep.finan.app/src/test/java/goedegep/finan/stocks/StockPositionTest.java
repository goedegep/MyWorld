package goedegep.finan.stocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import goedegep.util.money.PgCurrency;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StockPositionTest {
  private static final float EPSILON = 0.00015f;
  private static Company company;  
  private static Company companyA;
  private static Company companyB;
  private static Fund    fund;
  private static Fund    fundA;
  private static Fund    fundB;
  private static Share   share;
  private static Share   shareA;
  private static Share   shareB;
  
  @BeforeClass
  public static void prepareCompaniesEtc() throws IOException {
    company = new Company("MyCompanyName");
    companyA = new Company("MyCompanyNameZ");
    companyB = new Company("MyCompanyNameY");
    fund = new Fund("MyFundName", company);
    fundA = new Fund("MyFundNameZ", companyA);
    fundB = new Fund("MyFundNameY", companyB);
  }
  
  @Before
  public void prepareShares() throws IOException {
    Share.resetShares();
    share = new Share("MyShareName", fund);
    shareA = new Share("MyShareNameA", fundA);
    shareB = new Share("MyShareNameB", fundB);
  }
  

  /**
   * 
   */
  @Test
  public void creationTest() {
    // Create a stockPosition.
    StockPosition stockPosition = new StockPosition(share);
    
    // Check the stock (Share).
    assertEquals("Stock is wrong", share, stockPosition.getStock());    
    
    // After creation, the current amount shall be 0.
    assertEquals("Initial current amount is wrong", 0, stockPosition.getCurrentAmount());
    
    // After creation, the investment shall be null.
    assertNull("Initial investment is wrong", stockPosition.getInvestment());
    
    // Set the investment and check the new value.
    PgCurrency money = new PgCurrency(PgCurrency.EURO, 1299950l);
    stockPosition.increaseInvestment(money);
    assertEquals("New investment is wrong", money, stockPosition.getInvestment());
  }
  
  @Test
  public void compareTest() {
    // Create stockPositions to compare.
    StockPosition stockPositionA = new StockPosition(shareA);
    StockPosition stockPositionB = new StockPosition(shareB);
    
    assertEquals("Compare result is wrong", -1, stockPositionA.compareTo(stockPositionB));
    assertEquals("Compare result is wrong", 0, stockPositionA.compareTo(stockPositionA));
    assertEquals("Compare result is wrong", 1, stockPositionB.compareTo(stockPositionA));
  }
  
  @Test
  public void effectenToevoegenEnVerwijderenTest() {
    // Create a stockPosition.
    StockPosition stockPosition = new StockPosition(share);
    
    // Voeg effecten toe en controleer.
    int amount1 = 500;
    int amount2 = 100;
    int amount3 = 10;
    PgCurrency bedrag1 = new PgCurrency(PgCurrency.EURO, 501234);
    PgCurrency bedrag2 = new PgCurrency(PgCurrency.EURO,  91234);
    PgCurrency investment2 = new PgCurrency(PgCurrency.EURO, 592468);
    
    stockPosition.effectenToevoegen(LocalDate.now(), amount1, new PgCurrency(PgCurrency.EURO, 1000), bedrag1);
    assertEquals("Amount is wrong", amount1, stockPosition.getCurrentAmount());
    assertEquals("Investment is wrong", bedrag1, stockPosition.getInvestment());
    
    stockPosition.effectenToevoegen(LocalDate.now(), amount2, new PgCurrency(PgCurrency.EURO, 990), bedrag2);
    assertEquals("Amount is wrong", amount1 + amount2, stockPosition.getCurrentAmount());
    assertEquals("Investment is wrong", investment2, stockPosition.getInvestment());
    
    stockPosition.effectenToevoegen(LocalDate.now(), amount3, null, null);
    assertEquals("Amount is wrong", amount1 + amount2 + amount3, stockPosition.getCurrentAmount());
    assertEquals("Investment is wrong", investment2, stockPosition.getInvestment());
    
    // Verwijder effecten toe en controleer.
    int amount4 = 400;
    int amount5 = 50;
    PgCurrency bedrag4 = new PgCurrency(PgCurrency.EURO, 480999);
    PgCurrency bedrag5 = new PgCurrency(PgCurrency.EURO, 3000);
    PgCurrency investment3 = new PgCurrency(PgCurrency.EURO, 111469);
    PgCurrency investment4 = new PgCurrency(PgCurrency.EURO, 108469);
    
    stockPosition.effectenVerwijderen(LocalDate.now(), amount4, new PgCurrency(PgCurrency.EURO, 1200), bedrag4);
    assertEquals("Amount is wrong", amount1 + amount2 + amount3 - amount4, stockPosition.getCurrentAmount());
    assertEquals("Investment is wrong", investment3, stockPosition.getInvestment());
    
    stockPosition.effectenVerwijderen(LocalDate.now(), amount5, new PgCurrency(PgCurrency.EURO, 600), bedrag5);
    assertEquals("Amount is wrong", amount1 + amount2 + amount3 - amount4 - amount5, stockPosition.getCurrentAmount());
    assertEquals("Investment is wrong", investment4, stockPosition.getInvestment());
  }
  
  /**
   * Test redenominatie van een aandeel, met een verhouding 4 op 1.
   */
  @Test
  public void redenomineer4to1Test() {
    Share   redenominatedShare;
    redenominatedShare = new Share("RedenominatedShare", fund);
    Redenomination redenomination = new Redenomination();
    redenomination.setFromAmount(4);
    redenomination.setToAmount(1);
    redenomination.setShare(share);
    redenominatedShare.setRedenominationFrom(redenomination);
    
    // 1e = aankoop
    LocalDate datum1 = LocalDate.of(2001, Month.DECEMBER, 1);
    int amount1Orig = 500;
    int amount1New = 125;
    PgCurrency koers1Orig = new PgCurrency(PgCurrency.EURO, 1000);
    PgCurrency koers1New = new PgCurrency(PgCurrency.EURO, 4000);
    PgCurrency bedrag1 = new PgCurrency(PgCurrency.EURO, 501234);
    
    // 2e = aankoop
    LocalDate datum2 = LocalDate.of(2001, Month.DECEMBER, 15);
    int amount2Orig = 100;
    int amount2New = 25;
    PgCurrency koers2Orig = new PgCurrency(PgCurrency.EURO, 990);
    PgCurrency koers2New = new PgCurrency(PgCurrency.EURO, 3960);
    PgCurrency bedrag2 = new PgCurrency(PgCurrency.EURO,  91234);
    
    // 3e = verkoop
    LocalDate datum3 = LocalDate.of(2002, Month.JANUARY , 23);
    int amount3Orig = 200;
    int amount3New = 50;
    PgCurrency koers3Orig = new PgCurrency(PgCurrency.EURO, 1220);
    PgCurrency koers3New = new PgCurrency(PgCurrency.EURO, 4880);
    PgCurrency bedrag3 = new PgCurrency(PgCurrency.EURO,  246134);
    
    // 4e = aankoop
    LocalDate datum4 = LocalDate.of(2003, Month.DECEMBER , 1);
    int amount4Orig = 10;
    float amount4New = 2.5f;
    
    LocalDate redenominatieDatum = LocalDate.of(2008, Month.JULY , 21);

    // Maak een stockPosition aan en voeg effecten toe en verwijder effecten.
    StockPosition stockPosition = new StockPosition(share);
    stockPosition.effectenToevoegen(datum1, amount1Orig, koers1Orig, bedrag1);
    stockPosition.effectenToevoegen(datum2, amount2Orig, koers2Orig, bedrag2);
    stockPosition.effectenVerwijderen(datum3, amount3Orig, koers3Orig, bedrag3);
    stockPosition.effectenToevoegen(datum4, amount4Orig, null, null);

    stockPosition.redenomineer(redenominatieDatum, redenominatedShare);

    List<StockPositionHistory> history = stockPosition.getHistory();
    
    assertEquals("Verkeerde historie lengte", 4, history.size());
    StockPositionHistory sph;
    StockPositionHistoryRedenomination sphr;
    
    // Check first entry
    sph = history.get(0);
    assertTrue("Geen redenominatie history entry", sph instanceof StockPositionHistoryRedenomination);
    sphr = (StockPositionHistoryRedenomination) sph;
    assertEquals("Verkeerde nieuwe datum", datum1, sphr.getDate());
    assertTrue("Geen nieuw heel aantal", sphr.isIntegerAmount());
    assertEquals("Verkeerde nieuwe hoeveelheid", amount1New, sphr.getIntAmount());
    assertEquals("Verkeerde nieuwe koers", koers1New, sphr.getRate());
    assertEquals("Verkeerd nieuw bedrag", bedrag1, sphr.getBedrag());
    sph = sphr.getOriginalHistory();
    assertEquals("Verkeerde oude datum", datum1, sph.getDate());
    assertTrue("Geen oud heel aantal", sph.isIntegerAmount());
    assertEquals("Verkeerde oude hoeveelheid", amount1Orig, sph.getIntAmount());
    assertEquals("Verkeerde oude koers", koers1Orig, sph.getRate());
    assertEquals("Verkeerd oud bedrag", bedrag1, sph.getBedrag());
    
    // Check second entry
    sph = history.get(1);
    assertTrue("Geen redenominatie history entry", sph instanceof StockPositionHistoryRedenomination);
    sphr = (StockPositionHistoryRedenomination) sph;
    assertEquals("Verkeerde nieuwe datum", datum2, sphr.getDate());
    assertTrue("Geen nieuw heel aantal", sphr.isIntegerAmount());
    assertEquals("Verkeerde nieuwe hoeveelheid", amount2New, sphr.getIntAmount());
    assertEquals("Verkeerde nieuwe koers", koers2New, sphr.getRate());
    assertEquals("Verkeerd nieuw bedrag", bedrag2, sphr.getBedrag());
    sph = sphr.getOriginalHistory();
    assertEquals("Verkeerde oude datum", datum2, sph.getDate());
    assertTrue("Geen oud heel aantal", sph.isIntegerAmount());
    assertEquals("Verkeerde oude hoeveelheid", amount2Orig, sph.getIntAmount());
    assertEquals("Verkeerde oude koers", koers2Orig, sph.getRate());
    assertEquals("Verkeerd oud bedrag", bedrag2, sph.getBedrag());
    
    // Check third entry
    sph = history.get(2);
    assertTrue("Geen redenominatie history entry", sph instanceof StockPositionHistoryRedenomination);
    sphr = (StockPositionHistoryRedenomination) sph;
    assertEquals("Verkeerde nieuwe datum", datum3, sphr.getDate());
    assertTrue("Geen nieuw heel aantal", sphr.isIntegerAmount());
    assertEquals("Verkeerde nieuwe hoeveelheid", -amount3New, sphr.getIntAmount());  // verkoop, dus amount is negatief in historie.
    assertEquals("Verkeerde nieuwe koers", koers3New, sphr.getRate());
    assertEquals("Verkeerd nieuw bedrag", bedrag3.changeSign(), sphr.getBedrag());  // Verkoop, dus bedrag negatief in historie.
    sph = sphr.getOriginalHistory();
    assertEquals("Verkeerde oude datum", datum3, sph.getDate());
    assertTrue("Geen oud heel aantal", sph.isIntegerAmount());
    assertEquals("Verkeerde oude hoeveelheid", -amount3Orig, sph.getIntAmount());  // Verkoop, dus amount negatief in historie.
    assertEquals("Verkeerde oude koers", koers3Orig, sph.getRate());
    assertEquals("Verkeerd oud bedrag", bedrag3.changeSign(), sph.getBedrag());  // Verkoop, dus bedrag negatief in historie.
    
    // Check fourth entry
    sph = history.get(3);
    assertTrue("Geen redenominatie history entry", sph instanceof StockPositionHistoryRedenomination);
    sphr = (StockPositionHistoryRedenomination) sph;
    assertEquals("Verkeerde nieuwe datum", datum4, sphr.getDate());
    assertFalse("Geen nieuw float aantal", sphr.isIntegerAmount());
    assertEquals("Verkeerde nieuwe hoeveelheid", amount4New, sphr.getAmount(), EPSILON);
    assertNull("Verkeerde nieuwe koers", sphr.getRate());
    assertNull("Verkeerd nieuw bedrag", sphr.getBedrag());
    sph = sphr.getOriginalHistory();
    assertEquals("Verkeerde oude datum", datum4, sph.getDate());
    assertTrue("Geen oud heel aantal", sph.isIntegerAmount());
    assertEquals("Verkeerde oude hoeveelheid", amount4Orig, sph.getIntAmount());
    assertNull("Verkeerde oude koers", sph.getRate());
    assertNull("Verkeerd oud bedrag", sph.getBedrag());
  }
  
  
  /**
   * Test redenominatie van een aandeel, met een verhouding 4 op 1.
   */
  @Test
  public void redenomineer3to7Test() {
    Share   redenominatedShare;
    redenominatedShare = new Share("RedenominatedShare", fund);
    Redenomination redenomination = new Redenomination();
    redenomination.setFromAmount(3);
    redenomination.setToAmount(7);
    redenomination.setShare(share);
    redenominatedShare.setRedenominationFrom(redenomination);
    
    // 1e = aankoop
    LocalDate datum1 = LocalDate.of(2001, Month.DECEMBER , 1);
    int amount1Orig = 500;
    float amount1New = 1166.6667f;
    PgCurrency koers1Orig = new PgCurrency(PgCurrency.EURO, 1000);
    PgCurrency koers1New = new PgCurrency(PgCurrency.EURO, 42857, 10000);
    PgCurrency bedrag1 = new PgCurrency(PgCurrency.EURO, 501234);
    
    // 2e = aankoop
    LocalDate datum2 = LocalDate.of(2001, Month.DECEMBER , 15);
    int amount2Orig = 100;
    float amount2New = 233.3333f;
    PgCurrency koers2Orig = new PgCurrency(PgCurrency.EURO, 990);
    PgCurrency koers2New = new PgCurrency(PgCurrency.EURO, 42429, 10000);
    PgCurrency bedrag2 = new PgCurrency(PgCurrency.EURO,  91234);
    
    // 3e = verkoop
    LocalDate datum3 = LocalDate.of(2002, Month.JANUARY , 23);
    int amount3Orig = 200;
    float amount3New = 466.6667f;
    PgCurrency koers3Orig = new PgCurrency(PgCurrency.EURO, 1220);
    PgCurrency koers3New = new PgCurrency(PgCurrency.EURO, 52286, 10000);
    PgCurrency bedrag3 = new PgCurrency(PgCurrency.EURO,  246134);
    
    // 4e = aankoop
    LocalDate datum4 = LocalDate.of(2003, Month.DECEMBER , 1);
    int amount4Orig = 10;
    float amount4New = 23.33333f;
    
    LocalDate redenominatieDatum = LocalDate.of(2008, Month.JULY , 21);

    // Maak een stockPosition aan en voeg effecten toe en verwijder effecten.
    StockPosition stockPosition = new StockPosition(share);
    stockPosition.effectenToevoegen(datum1, amount1Orig, koers1Orig, bedrag1);
    stockPosition.effectenToevoegen(datum2, amount2Orig, koers2Orig, bedrag2);
    stockPosition.effectenVerwijderen(datum3, amount3Orig, koers3Orig, bedrag3);
    stockPosition.effectenToevoegen(datum4, amount4Orig, null, null);

    stockPosition.redenomineer(redenominatieDatum, redenominatedShare);

    List<StockPositionHistory> history = stockPosition.getHistory();
    
    assertEquals("Verkeerde historie lengte", 4, history.size());
    StockPositionHistory sph;
    StockPositionHistoryRedenomination sphr;
    
    // Check first entry
    sph = history.get(0);
    assertTrue("Geen redenominatie history entry", sph instanceof StockPositionHistoryRedenomination);
    sphr = (StockPositionHistoryRedenomination) sph;
    assertEquals("Verkeerde nieuwe datum", datum1, sphr.getDate());
    assertFalse("Geen nieuw float aantal", sphr.isIntegerAmount());
    assertEquals("Verkeerde nieuwe hoeveelheid", amount1New, sphr.getAmount(), EPSILON);
    assertEquals("Verkeerde nieuwe koers", koers1New, sphr.getRate());
    assertEquals("Verkeerd nieuw bedrag", bedrag1, sphr.getBedrag());
    sph = sphr.getOriginalHistory();
    assertEquals("Verkeerde oude datum", datum1, sph.getDate());
    assertTrue("Geen oud heel aantal", sph.isIntegerAmount());
    assertEquals("Verkeerde oude hoeveelheid", amount1Orig, sph.getIntAmount());
    assertEquals("Verkeerde oude koers", koers1Orig, sph.getRate());
    assertEquals("Verkeerd oud bedrag", bedrag1, sph.getBedrag());
    
    // Check second entry
    sph = history.get(1);
    assertTrue("Geen redenominatie history entry", sph instanceof StockPositionHistoryRedenomination);
    sphr = (StockPositionHistoryRedenomination) sph;
    assertEquals("Verkeerde nieuwe datum", datum2, sphr.getDate());
    assertFalse("Geen nieuw float aantal", sphr.isIntegerAmount());
    assertEquals("Verkeerde nieuwe hoeveelheid", amount2New, sphr.getAmount(), EPSILON);
    assertEquals("Verkeerde nieuwe koers", koers2New, sphr.getRate());
    assertEquals("Verkeerd nieuw bedrag", bedrag2, sphr.getBedrag());
    sph = sphr.getOriginalHistory();
    assertEquals("Verkeerde oude datum", datum2, sph.getDate());
    assertTrue("Geen oud heel aantal", sph.isIntegerAmount());
    assertEquals("Verkeerde oude hoeveelheid", amount2Orig, sph.getIntAmount());
    assertEquals("Verkeerde oude koers", koers2Orig, sph.getRate());
    assertEquals("Verkeerd oud bedrag", bedrag2, sph.getBedrag());
    
    // Check third entry
    sph = history.get(2);
    assertTrue("Geen redenominatie history entry", sph instanceof StockPositionHistoryRedenomination);
    sphr = (StockPositionHistoryRedenomination) sph;
    assertEquals("Verkeerde nieuwe datum", datum3, sphr.getDate());
    assertFalse("Geen nieuw float aantal", sphr.isIntegerAmount());
    assertEquals("Verkeerde nieuwe hoeveelheid", -amount3New, sphr.getAmount(), EPSILON);  // verkoop, dus amount is negatief in historie.
    assertEquals("Verkeerde nieuwe koers", koers3New, sphr.getRate());
    assertEquals("Verkeerd nieuw bedrag", bedrag3.changeSign(), sphr.getBedrag());  // Verkoop, dus bedrag negatief in historie.
    sph = sphr.getOriginalHistory();
    assertEquals("Verkeerde oude datum", datum3, sph.getDate());
    assertTrue("Geen oud heel aantal", sph.isIntegerAmount());
    assertEquals("Verkeerde oude hoeveelheid", -amount3Orig, sph.getIntAmount());  // Verkoop, dus amount negatief in historie.
    assertEquals("Verkeerde oude koers", koers3Orig, sph.getRate());
    assertEquals("Verkeerd oud bedrag", bedrag3.changeSign(), sph.getBedrag());  // Verkoop, dus bedrag negatief in historie.
    
    // Check fourth entry
    sph = history.get(3);
    assertTrue("Geen redenominatie history entry", sph instanceof StockPositionHistoryRedenomination);
    sphr = (StockPositionHistoryRedenomination) sph;
    assertEquals("Verkeerde nieuwe datum", datum4, sphr.getDate());
    assertFalse("Geen nieuw float aantal", sphr.isIntegerAmount());
    assertEquals("Verkeerde nieuwe hoeveelheid", amount4New, sphr.getAmount(), EPSILON);
    assertNull("Verkeerde nieuwe koers", sphr.getRate());
    assertNull("Verkeerd nieuw bedrag", sphr.getBedrag());
    sph = sphr.getOriginalHistory();
    assertEquals("Verkeerde oude datum", datum4, sph.getDate());
    assertTrue("Geen oud heel aantal", sph.isIntegerAmount());
    assertEquals("Verkeerde oude hoeveelheid", amount4Orig, sph.getIntAmount());
    assertNull("Verkeerde oude koers", sph.getRate());
    assertNull("Verkeerd oud bedrag", sph.getBedrag());
  }
  
// TODO reactivate test after modification of getVModelStatus.
//  /**
//   * VModel: Check that an exception is thrown when more shares are sold than
//   * bought.
//   */
//  @Test(expected=IllegalArgumentException.class)
//  public void vModelTooMuchSellsTest() {
//    // Create a stockPosition.
//    StockPosition stockPosition = new StockPosition(share);
//    
//    // Sell some shares.
//    stockPosition.effectenVerwijderen(new Date(), 100, new PgCurrency(PgCurrency.EURO, 1200), new PgCurrency(PgCurrency.EURO, 120900));
//    
//    // Trying to get a V Model status shall throw an exception.
//    stockPosition.getVModelStatus();
//  }
  
  
  /**
   * VModel: Check a single buy, followed by single sell with profit.
   */
  @Test
  public void vModelSimpleBuySellWithProfitTest() {
     LocalDate  aankoopDatum1 = LocalDate.of(2001, Month.DECEMBER , 1);
     int        aankoopAantal1 = 100;
     PgCurrency aankoopKoers1 = new PgCurrency(PgCurrency.EURO, 1100);
     PgCurrency aankoopBedrag1 = new PgCurrency(PgCurrency.EURO, 110500);
     
     LocalDate  verkoopDatum2 = LocalDate.of(2001, Month.DECEMBER , 10);
     int        verkoopAantal2 = aankoopAantal1;
     PgCurrency verkoopKoers2 = new PgCurrency(PgCurrency.EURO, 1200);
     PgCurrency verkoopBedrag2 = new PgCurrency(PgCurrency.EURO, 121000);
     
     PgCurrency profit = verkoopBedrag2.subtract(aankoopBedrag1);
     
    // Create a stockPosition.
    StockPosition stockPosition = new StockPosition(share);
    
    // Buy some shares.
    stockPosition.effectenToevoegen(aankoopDatum1, aankoopAantal1, aankoopKoers1, aankoopBedrag1);
    
    // Sell some shares.
    stockPosition.effectenVerwijderen(verkoopDatum2, verkoopAantal2, verkoopKoers2, verkoopBedrag2);
    
    StockVModelStatus status = stockPosition.getVModelStatus();
    
    // There shall be one buy/sell combo.
    assertEquals("Wrong number of buy/sell combo's", 1, status.getBuySellCombos().size());
    StockBuySellCombo combo = status.getBuySellCombos().get(0);
    assertTrue("Amount is not an integer", combo.isIntegerAmount());
    assertEquals("Wrong amount", 100, combo.getIntAmount());
    assertEquals("Verkeerde aankoopdatum", aankoopDatum1, combo.getBuyDate());
    assertEquals("Verkeerde aankoopkoers", aankoopKoers1, combo.getBuyRate());
    assertEquals("Verkeerde winst", profit, combo.getProfit());
    assertEquals("Verkeerde verkoopdatum", verkoopDatum2, combo.getSellDate());
    assertEquals("Verkeerde verkoopkoers", verkoopKoers2, combo.getSellRate());
    
    // The buy list shall be empty.
    assertTrue("Buy list not empty", status.getBuys().isEmpty());
    
    // Check profit.
    assertEquals("Verkeerde winst", profit, status.getTotalBuySellProfit());
  }
  
  /**
   * VModel: Check a single buy, followed by single sell with loss.
   */
  @Test
  public void vModelSimpleBuySellWithLossTest() {
     LocalDate  aankoopDatum1 = LocalDate.of(2001, Month.DECEMBER , 1);
     int        aankoopAantal1 = 100;
     PgCurrency aankoopKoers1 = new PgCurrency(PgCurrency.EURO, 1100);
     PgCurrency aankoopBedrag1 = new PgCurrency(PgCurrency.EURO, 110500);
     
     LocalDate  verkoopDatum2 = LocalDate.of(2001, Month.DECEMBER , 10);
     int        verkoopAantal2 = aankoopAantal1;
     PgCurrency verkoopKoers2 = new PgCurrency(PgCurrency.EURO, 800);
     PgCurrency verkoopBedrag2 = new PgCurrency(PgCurrency.EURO, 81000);
     
     PgCurrency profit = verkoopBedrag2.subtract(aankoopBedrag1);
     
    // Create a stockPosition.
    StockPosition stockPosition = new StockPosition(share);
    
    // Buy some shares.
    stockPosition.effectenToevoegen(aankoopDatum1, aankoopAantal1, aankoopKoers1, aankoopBedrag1);
    
    // Sell some shares.
    stockPosition.effectenVerwijderen(verkoopDatum2, verkoopAantal2, verkoopKoers2, verkoopBedrag2);
    
    StockVModelStatus status = stockPosition.getVModelStatus();
    
    // There shall be one buy/sell combo.
    assertEquals("Wrong number of buy/sell combo's", 1, status.getBuySellCombos().size());
    StockBuySellCombo combo = status.getBuySellCombos().get(0);
    assertTrue("Amount is not an integer", combo.isIntegerAmount());
    assertEquals("Wrong amount", 100, combo.getIntAmount());
    assertEquals("Verkeerde aankoopdatum", aankoopDatum1, combo.getBuyDate());
    assertEquals("Verkeerde aankoopkoers", aankoopKoers1, combo.getBuyRate());
    assertEquals("Verkeerde winst", profit, combo.getProfit());
    assertEquals("Verkeerde verkoopdatum", verkoopDatum2, combo.getSellDate());
    assertEquals("Verkeerde verkoopkoers", verkoopKoers2, combo.getSellRate());
    
    // The buy list shall be empty.
    assertTrue("Buy list not empty", status.getBuys().isEmpty());
    
    // Check profit.
    assertEquals("Verkeerde winst", profit, status.getTotalBuySellProfit());
  }
  
  /**
   * VModel: Check a single buy, followed by single sell with profit.
   * The buy rate has 4 decimals.
   */
  @Test
  public void vModelSimpleBuy4DecSellWithProfitTest() {
     LocalDate  aankoopDatum1 = LocalDate.of(2001, Month.DECEMBER , 1);
     int        aankoopAantal1 = 100;
     PgCurrency aankoopKoers1 = new PgCurrency(PgCurrency.EURO, 110001, 10000);
     PgCurrency aankoopBedrag1 = new PgCurrency(PgCurrency.EURO, 110501);
     
     LocalDate  verkoopDatum2 = LocalDate.of(2001, Month.DECEMBER , 10);
     int        verkoopAantal2 = aankoopAantal1;
     PgCurrency verkoopKoers2 = new PgCurrency(PgCurrency.EURO, 1200);
     PgCurrency verkoopBedrag2 = new PgCurrency(PgCurrency.EURO, 121000);
     
     PgCurrency profit = verkoopBedrag2.subtract(aankoopBedrag1);
     
    // Create a stockPosition.
    StockPosition stockPosition = new StockPosition(share);
    
    // Buy some shares.
    stockPosition.effectenToevoegen(aankoopDatum1, aankoopAantal1, aankoopKoers1, aankoopBedrag1);
    
    // Sell some shares.
    stockPosition.effectenVerwijderen(verkoopDatum2, verkoopAantal2, verkoopKoers2, verkoopBedrag2);
    
    StockVModelStatus status = stockPosition.getVModelStatus();
    
    // There shall be one buy/sell combo.
    assertEquals("Wrong number of buy/sell combo's", 1, status.getBuySellCombos().size());
    StockBuySellCombo combo = status.getBuySellCombos().get(0);
    assertTrue("Amount is not an integer", combo.isIntegerAmount());
    assertEquals("Wrong amount", 100, combo.getIntAmount());
    assertEquals("Verkeerde aankoopdatum", aankoopDatum1, combo.getBuyDate());
    assertEquals("Verkeerde aankoopkoers", aankoopKoers1, combo.getBuyRate());
    assertEquals("Verkeerde winst", profit, combo.getProfit());
    assertEquals("Verkeerde verkoopdatum", verkoopDatum2, combo.getSellDate());
    assertEquals("Verkeerde verkoopkoers", verkoopKoers2, combo.getSellRate());
    
    // The buy list shall be empty.
    assertTrue("Buy list not empty", status.getBuys().isEmpty());
    
    // Check profit.
    assertEquals("Verkeerde winst", profit, status.getTotalBuySellProfit());
  }
  
  
  /**
   * VModel: Check a single buy, followed by single sell with profit.
   * The sell rate has 4 decimals.
   */
  @Test
  public void vModelSimpleBuySell4DecWithProfitTest() {
     LocalDate  aankoopDatum1 = LocalDate.of(2001, Month.DECEMBER , 1);
     int        aankoopAantal1 = 100;
     PgCurrency aankoopKoers1 = new PgCurrency(PgCurrency.EURO, 1100);
     PgCurrency aankoopBedrag1 = new PgCurrency(PgCurrency.EURO, 110500);
     
     LocalDate  verkoopDatum2 = LocalDate.of(2001, Month.DECEMBER , 10);
     int        verkoopAantal2 = aankoopAantal1;
     PgCurrency verkoopKoers2 = new PgCurrency(PgCurrency.EURO, 120001, 10000);
     PgCurrency verkoopBedrag2 = new PgCurrency(PgCurrency.EURO, 121001);
     
     PgCurrency profit = verkoopBedrag2.subtract(aankoopBedrag1);
     
    // Create a stockPosition.
    StockPosition stockPosition = new StockPosition(share);
    
    // Buy some shares.
    stockPosition.effectenToevoegen(aankoopDatum1, aankoopAantal1, aankoopKoers1, aankoopBedrag1);
    
    // Sell some shares.
    stockPosition.effectenVerwijderen(verkoopDatum2, verkoopAantal2, verkoopKoers2, verkoopBedrag2);
    
    StockVModelStatus status = stockPosition.getVModelStatus();
    
    // There shall be one buy/sell combo.
    assertEquals("Wrong number of buy/sell combo's", 1, status.getBuySellCombos().size());
    StockBuySellCombo combo = status.getBuySellCombos().get(0);
    assertTrue("Amount is not an integer", combo.isIntegerAmount());
    assertEquals("Wrong amount", 100, combo.getIntAmount());
    assertEquals("Verkeerde aankoopdatum", aankoopDatum1, combo.getBuyDate());
    assertEquals("Verkeerde aankoopkoers", aankoopKoers1, combo.getBuyRate());
    assertEquals("Verkeerde winst", profit, combo.getProfit());
    assertEquals("Verkeerde verkoopdatum", verkoopDatum2, combo.getSellDate());
    assertEquals("Verkeerde verkoopkoers", verkoopKoers2, combo.getSellRate());
    
    // The buy list shall be empty.
    assertTrue("Buy list not empty", status.getBuys().isEmpty());
    
    // Check profit.
    assertEquals("Verkeerde winst", profit, status.getTotalBuySellProfit());
  }
  
  /**
   * VModel test in combination with a redenomination.
   */
  @Test
  public void vModelBuySellRedenominationTest() {
    Share   redenominatedShare;
    redenominatedShare = new Share("RedenominatedShare", fund);
    Redenomination redenomination = new Redenomination();
    redenomination.setFromAmount(3);
    redenomination.setToAmount(7);
    redenomination.setShare(share);
    redenominatedShare.setRedenominationFrom(redenomination);
    
    // 1e = aankoop
    LocalDate datum1 = LocalDate.of(2003, Month.DECEMBER , 1);
    int amount1Orig = 500;
    PgCurrency koers1Orig = new PgCurrency(PgCurrency.EURO, 1000);
    PgCurrency koers1New = new PgCurrency(PgCurrency.EURO, 42857, 10000);
    PgCurrency bedrag1 = new PgCurrency(PgCurrency.EURO, 501234);
    
    // 2e = aankoop
    LocalDate datum2 = LocalDate.of(2003, Month.DECEMBER , 15);
    int amount2Orig = 100;
    PgCurrency koers2Orig = new PgCurrency(PgCurrency.EURO, 990);
    PgCurrency bedrag2 = new PgCurrency(PgCurrency.EURO,  91234);
    
    // 3e = verkoop
    LocalDate datum3 = LocalDate.of(2003, Month.JANUARY , 23);
    int amount3Orig = 200;
    PgCurrency koers3Orig = new PgCurrency(PgCurrency.EURO, 1220);
    PgCurrency koers3New = new PgCurrency(PgCurrency.EURO, 52286, 10000);
    PgCurrency bedrag3 = new PgCurrency(PgCurrency.EURO,  246134);
    
    // 4e = aankoop
    LocalDate datum4 = LocalDate.of(2003, Month.DECEMBER , 1);
    int amount4Orig = 10;
    
    LocalDate redenominatieDatum = LocalDate.of(2003, Month.JULY , 21);
    
    // 5e = verkoop (na redenominatie)
    LocalDate datum5 = LocalDate.of(2003, Month.JANUARY , 23);
    int amount5 = 800;
    PgCurrency koers5 = new PgCurrency(PgCurrency.EURO, 620);
    PgCurrency bedrag5 = new PgCurrency(PgCurrency.EURO,  491500);

    // Maak een stockPosition aan en voeg effecten toe en verwijder effecten.
    StockPosition stockPosition = new StockPosition(share);
    stockPosition.effectenToevoegen(datum1, amount1Orig, koers1Orig, bedrag1);
    stockPosition.effectenToevoegen(datum2, amount2Orig, koers2Orig, bedrag2);
    stockPosition.effectenVerwijderen(datum3, amount3Orig, koers3Orig, bedrag3);
    stockPosition.effectenToevoegen(datum4, amount4Orig, null, null);
    stockPosition.redenomineer(redenominatieDatum, redenominatedShare);
    stockPosition.effectenVerwijderen(datum5, amount5, koers5, bedrag5);
    
    // Trying to get a V Model status shall throw an exception.
    StockVModelStatus status = stockPosition.getVModelStatus();
    
    // History (VModel input):
    // Type      Date         Amount       Koers       Bedrag
    // aankoop   01-12-2001   1166,6667     4,2857      5012,34
    // aankoop   15-12-2001    233,3333    4,2429       912,34
    // verkoop   23-01-2002    466,6667    5,2286      2461,34
    // aankoop   01-12-2003     23,33333   -              -
    // verkoop   23-01-2006    800         6,20        4915,00
    
    // 1e verkoop: heel 2e aankoop + (466,6667 - 233,3333 = 233,3334) van 1e aankoop.
    // Koopdatum is datum 1e aankoop.
    // Koopkoers is: (233,3333 * 4,2429 + 233,3334 * 4,2857) / 466,6667 = 4,2643
    // Winst is: verkoopbedrag - aankoopbedrag =
    //   2461,34 - 912,34 - 233,3334/1166,6667*5012,34 = 
    //   2461,34 - 912,34 - 1002,47 = 546,53
    // Restant 1e aankoop:
    //   Amount = 1166,6667 - 233,3334 = 933,33333
    //   Bedrag = 5012,34 - 1002,47 = 4009,87
    //
    // Buys
    // Type      Date         Amount       Koers       Bedrag
    // aankoop   01-12-2001   933,33333    4,2857      4009,87
    //
    // Buy sell combo's:
    // Amount    Koopdatum    Koopkoers    Verkoopdatum    Verkoopkoers   Winst
    // 466,6667  01-12-2001   4,2643       23-01-2002      5,2286         546,53
    //
    // 2e verkoop: heel 3e aankoop + (800 - 23,33333 = 776,66667) van buys entry.
    // Koopdatum is datum 1e aankoop (buys entry).
    // Koopkoers is: (23,33333 * - + 776,66667 * 4,2857) / 800 = 4,1607.
    // Winst is: verkoopbedrag - aankoopbedrag =
    //   4915,00 - '-' - 776,66667/933,3336*4009,87 = 
    //   4915,00 - 3336,78 = 1578,22
    // Restant 1e aankoop:
    //   Amount = 933,33333 - 776,66667 = 156,66666
    //   Bedrag = 4009,87 - 3336,78 = 673,09
    //
    // Buys
    // Type      Date         Amount       Koers       Bedrag
    // aankoop   01-12-2001   156,66693    4,2857      673,09
    //
    // Buy sell combo's:
    // Amount    Koopdatum    Koopkoers    Verkoopdatum    Verkoopkoers   Winst
    // 466,6667  01-12-2001   4,2643       23-01-2002      5,2286          546,53
    // 800       01-12-2001   4,1607       23-01-2006      6,20           1578,22
    float buyAmount = 156.66666f;
    PgCurrency buyMoney = new PgCurrency(PgCurrency.EURO, 67309);
    float combo1Amount = 466.6667f;
    PgCurrency buyRate1 = new PgCurrency(PgCurrency.EURO, 42643, 10000);
    PgCurrency profit1 = new PgCurrency(PgCurrency.EURO, 54653);
    float combo2Amount = 800f;
    PgCurrency buyRate2 = new PgCurrency(PgCurrency.EURO, 41607, 10000);
    PgCurrency profit2 = new PgCurrency(PgCurrency.EURO, 157822);
    PgCurrency profit = new PgCurrency(PgCurrency.EURO, 212475);
    
    // There shall be two buy/sell combo's.
    List<StockBuySellCombo> buySellCombos = status.getBuySellCombos();
    assertEquals("Wrong number of buy/sell combo's", 2, buySellCombos.size());

    StockBuySellCombo combo = buySellCombos.get(0);
    assertFalse("Amount is not a float", combo.isIntegerAmount());
    assertEquals("Wrong amount", combo1Amount, combo.getAmount(), EPSILON);
    assertEquals("Verkeerde aankoopdatum", datum1, combo.getBuyDate());
    assertEquals("Verkeerde aankoopkoers", buyRate1, combo.getBuyRate());
    assertEquals("Verkeerde verkoopdatum", datum3, combo.getSellDate());
    assertEquals("Verkeerde verkoopkoers", koers3New, combo.getSellRate());
    assertEquals("Verkeerde winst", profit1, combo.getProfit());

    combo = buySellCombos.get(1);
    assertFalse("Amount is not a float", combo.isIntegerAmount());
    assertEquals("Wrong amount", combo2Amount, combo.getAmount(), EPSILON);
    assertEquals("Verkeerde aankoopdatum", datum1, combo.getBuyDate());
    assertEquals("Verkeerde aankoopkoers", buyRate2, combo.getBuyRate());
    assertEquals("Verkeerde verkoopdatum", datum5, combo.getSellDate());
    assertEquals("Verkeerde verkoopkoers", koers5, combo.getSellRate());
    assertEquals("Verkeerde winst", profit2, combo.getProfit());

   
    List<StockPositionHistory> buys = status.getBuys();
    
    // There shall be one buy left.
    assertEquals("Wrong number of buys left", 1, buys.size());
    
    StockPositionHistory buy = buys.get(0);
    assertFalse("Amount is not a float", buy.isIntegerAmount());
    assertEquals("Wrong amount", buyAmount, buy.getAmount(), EPSILON);
    assertEquals("Verkeerde aankoopdatum", datum1, buy.getDate());
    assertEquals("Verkeerde aankoopkoers", koers1New, buy.getRate());
    assertEquals("Verkeerde bedrag", buyMoney, buy.getBedrag());    
    
    // Check profit.
    assertEquals("Verkeerde winst", profit, status.getTotalBuySellProfit());
  } 
}
