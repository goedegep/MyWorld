package goedegep.finan.stockstest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import goedegep.finan.stocks.CompaniesContentHandler;
import goedegep.finan.stocks.CompanyFundsContentHandler;
import goedegep.finan.stocks.CompanyPool;
import goedegep.finan.stocks.Fund;
import goedegep.finan.stocks.FundSharesContentHandler;
import goedegep.finan.stocks.Share;
import goedegep.types.model.DateRateTuplet;
import goedegep.types.model.TypesFactory;
import goedegep.util.file.FileUtils;
import goedegep.util.money.PgCurrency;

public class SharesTest {
  protected static TypesFactory TYPES_FACTORY = TypesFactory.eINSTANCE;
  
  private static final String TEMP_TEST_DIR = "target/test";
  private static final String TEST_DATA_DIR = "src/test/resources/goedegep/finan/stocks";
  private static final String XSD_DIR = "../goedegep.finan.app/src/main/resources/goedegep/finan/stocks";
  private static final String COMPANIES_FILE = "Companies";
  private static final String FUNDS_FILE = "CompanyFunds";
  private static final String SHARES_FILE = "FundShares";
  private static final String[] REQUIRED_DATA_FILES = {COMPANIES_FILE, FUNDS_FILE, SHARES_FILE};

  private static File testDataDir;
  
  private CompanyPool                companyPool;


  @BeforeClass
  public static void prepareFiles() throws IOException {
    testDataDir = new File(System.getProperty("user.dir") + File.separator + TEMP_TEST_DIR);
    if (testDataDir.exists()) {
      throw new RuntimeException("Can not run test as temporary directory exists: " + testDataDir.getPath());
    }

    testDataDir.mkdir();

    for (String fileName: REQUIRED_DATA_FILES) {
      File file = new File(TEST_DATA_DIR, fileName + ".xml");
      FileUtils.copyFileToDirectory(file, testDataDir);
      file = new File(XSD_DIR, fileName + ".xsd");
      FileUtils.copyFileToDirectory(file, testDataDir);
    }
  }
  
  @AfterClass
  public static void cleanup() throws IOException {
    for (String fileName: REQUIRED_DATA_FILES) {
      File file = new File(testDataDir, fileName + ".xml");
      file.delete();
      file = new File(testDataDir, fileName + ".xsd");
      file.delete();
    }
    
    File file = new File(testDataDir, SHARES_FILE + ".xml" + ".bak");
    file.delete();
    
    if (!testDataDir.delete()) {
      throw new RuntimeException("Test data directory could not be deleted.");
    }
  }
  
  @Before
  public void readCompanyData() throws SAXException, ParserConfigurationException {
    File f;
    
    // Initialize companies.
    f = new File(testDataDir, COMPANIES_FILE + ".xml");
    companyPool = new CompanyPool();
    CompaniesContentHandler companiesContentHandler = new CompaniesContentHandler(companyPool);
    companiesContentHandler.read(f.getPath());
    
    // Initialize company funds.
    Fund.resetFunds();
    f = new File(testDataDir, FUNDS_FILE + ".xml");
    CompanyFundsContentHandler companyFundsContentHandler = new CompanyFundsContentHandler(companyPool);
    companyFundsContentHandler.read(f.getPath());
    
    // Initialize fund shares.
    Share.resetShares();
    f = new File(testDataDir, SHARES_FILE + ".xml");
    FundSharesContentHandler fundSharesContentHandler = new FundSharesContentHandler();
    fundSharesContentHandler.read(f.getPath());
  }
  
  /**
   * Tests getFund(), getName(), isObsolete().
   */
  @Test
  public void testReadingInput() {
    for (ExistingShare existingShare: ExistingShare.values()) {
      Fund fund = Fund.getFund(existingShare.getFundName());
      assertNotNull("Expected fund " + existingShare.getFundName() + " not found", fund);
      assertEquals("Wrong name", existingShare.getFundName(), fund.getName());
      assertEquals("Wrong number of shares for " + fund.getName(), existingShare.getNrOfShares(), fund.getShares().size());
      Share share = fund.getShare(existingShare.getCheckShareName());
      assertNotNull("Share " + existingShare.getCheckShareName() +
          " not found for fund " + fund.getName(), share);
      assertEquals("Wrong share name", existingShare.getCheckShareName(), share.getName());
      assertEquals("Wrong obsoleteness", existingShare.isCheckShareObsolete(), share.isObsolete());
    }
  }
  
  @Test
  public void testWriting() throws IOException {
    FundSharesContentHandler.write();
    // both the written file and the .bak file should be equal to the original.
    File originalFile = new File(TEST_DATA_DIR, SHARES_FILE + ".xml");
    File file = new File(testDataDir, SHARES_FILE + ".xml");
    assertTrue("Written file differs from original", FileUtils.contentEquals(originalFile, file));
    file = new File(testDataDir, SHARES_FILE + ".xml" + ".bak");
    assertTrue("Backup file differs from original", FileUtils.contentEquals(originalFile, file));
  }
  
  @Test
  public void testAddingShares() {
    for (NewShare newShare: NewShare.values()) {
      Fund fund = Fund.getFund(newShare.getFundName());
      Share share = new Share(newShare.getNewShareName(), fund);
      fund.addShare(share);
      assertEquals("Wrong number of new shares", newShare.getNewNrOfShares(), fund.getShares().size());
      share = fund.getShare(newShare.getNewShareName());
      assertNotNull("Share " + newShare.getNewShareName() +
          " not found for fund " + fund.getName(), share);
      assertEquals("Wrong share name", newShare.getNewShareName(), share.getName());
    }
  }
  
  /**
   * Tests getShares() and resetShares().
   */
  @Test
  public void testGetShares() {
    String[] sortedNewShareNames = {
        NewShare.AHOLD.getNewShareName(),
        NewShare.LOGICACMG.getNewShareName(),
        NewShare.ABN_AMRO.getNewShareName(),
        NewShare.TOMTOM.getNewShareName(),
        NewShare.POSTBANK_AANDELENFONDS.getNewShareName()
    };
    
    Share.resetShares();
    for (NewShare newShare: NewShare.values()) {
      new Share(newShare.getNewShareName(), null);
    }
    
    int index = 0;
    for (Share share: Share.getShares()) {
      assertEquals("Wrong share name", sortedNewShareNames[index++], share.getName());      
    }
    assertEquals("Wrong number of shares", sortedNewShareNames.length, index);      
  }
  
  @Test
  public void testRates() {
    DateRateTuplet[] shareOpeningRates = {
        createDateRateTuplet(LocalDate.of(2002, Month.OCTOBER, 23), new PgCurrency(PgCurrency.EURO, 1234)),
        createDateRateTuplet(LocalDate.of(2002, Month.OCTOBER, 12), new PgCurrency(PgCurrency.EURO, 1256)),
        createDateRateTuplet(LocalDate.of(2002, Month.OCTOBER, 28), new PgCurrency(PgCurrency.EURO, 1155)),
        createDateRateTuplet(LocalDate.of(2003, Month.MARCH, 5), new PgCurrency(PgCurrency.EURO, 1467)),
        createDateRateTuplet(LocalDate.of(2003, Month.FEBRUARY, 23), new PgCurrency(PgCurrency.EURO, 1334))
    };
    
    DateRateTuplet[] shareClosingRates = {
        createDateRateTuplet(LocalDate.of(2002, Month.OCTOBER, 23), new PgCurrency(PgCurrency.EURO, 1324)),
        createDateRateTuplet(LocalDate.of(2002, Month.OCTOBER, 24), new PgCurrency(PgCurrency.EURO, 1278)),
        createDateRateTuplet(LocalDate.of(2002, Month.OCTOBER, 25), new PgCurrency(PgCurrency.EURO, 1198)),
        createDateRateTuplet(LocalDate.of(2003, Month.MARCH, 1), new PgCurrency(PgCurrency.EURO, 1376)),
        createDateRateTuplet(LocalDate.of(2003, Month.MARCH, 2), new PgCurrency(PgCurrency.EURO, 1344))
    };
    
    DateRateTuplet[] shareAnyRates = {
        createDateRateTuplet(LocalDate.of(2002, Month.NOVEMBER, 23), new PgCurrency(PgCurrency.EURO, 1724)),
        createDateRateTuplet(LocalDate.of(2002, Month.OCTOBER, 24), new PgCurrency(PgCurrency.EURO, 1288)),
        createDateRateTuplet(LocalDate.of(2002, Month.NOVEMBER, 25), new PgCurrency(PgCurrency.EURO, 1098)),
        createDateRateTuplet(LocalDate.of(2003, Month.MARCH, 1), new PgCurrency(PgCurrency.EURO, 1276)),
        createDateRateTuplet(LocalDate.of(2003, Month.MARCH, 1), new PgCurrency(PgCurrency.EURO, 1354))
    };
    
    DateRateTuplet[] shareRedenominationOpeningRates = {
        createDateRateTuplet(LocalDate.of(2003, Month.MARCH, 6), new PgCurrency(PgCurrency.EURO, 1434)),
        createDateRateTuplet(LocalDate.of(2004, Month.OCTOBER, 12), new PgCurrency(PgCurrency.EURO, 1456)),
        createDateRateTuplet(LocalDate.of(2004, Month.OCTOBER, 28), new PgCurrency(PgCurrency.EURO, 1555)),
        createDateRateTuplet(LocalDate.of(2005, Month.MARCH, 5), new PgCurrency(PgCurrency.EURO, 1667)),
        createDateRateTuplet(LocalDate.of(2005, Month.FEBRUARY, 23), new PgCurrency(PgCurrency.EURO, 1534))
    };
    
    // Add rates to a share.
    Share share = Share.getShare("AHOLD KON NLG0.50");
    for (DateRateTuplet tuplet: shareOpeningRates) {
      share.addOpeningRate(tuplet.getDate(), tuplet.getRate());
    }
    for (DateRateTuplet tuplet: shareClosingRates) {
      share.addClosingRate(tuplet.getDate(), tuplet.getRate());
    }
    for (DateRateTuplet tuplet: shareAnyRates) {
      share.addAnyRate(tuplet.getDate(), tuplet.getRate());
    }
    // Get opening rate for a date for which a rate is available.
    assertEquals("Wrong opening rate", shareOpeningRates[2].getRate(), share.getOpeningRate(shareOpeningRates[2].getDate()));
    // Try to get an opening rate for a date for which no rate is available.
    assertNull("Returned rate, instead of null", share.getOpeningRate(LocalDate.of(2002, Month.OCTOBER, 29))); 
    // Get the best opening rate for the current date.
    assertEquals("Wrong opening rate", shareOpeningRates[3], share.getBestOpeningRate());
    // Get the best opening rate for a specific date.
    assertEquals("Wrong opening rate", shareOpeningRates[2], share.getBestOpeningRate(LocalDate.of(2002, Month.DECEMBER, 11)));
    // Try to get the best opening rate for a date for which no rate is available.
    assertNull("Returned rate, instead of null", share.getBestOpeningRate(LocalDate.of(2001, Month.DECEMBER, 11))); 
    
    // Get closing rate for a date for which a rate is available.
    assertEquals("Wrong closing rate", shareClosingRates[2].getRate(), share.getClosingRate(shareClosingRates[2].getDate())); 
    // Try to get a closing rate for a date for which no rate is available.
    assertNull("Returned rate, instead of null", share.getOpeningRate(LocalDate.of(2002, Month.OCTOBER, 29)));      
    // Get the best closing rate for the current date.
    assertEquals("Wrong closing rate", shareClosingRates[4], share.getBestClosingRate());
    // Get the best closing rate for a specific date.
    assertEquals("Wrong closing rate", shareClosingRates[2], share.getBestClosingRate(LocalDate.of(2002, Month.DECEMBER, 11)));
    // Try to get the best closing rate for a date for which no rate is available.
    assertNull("Returned rate, instead of null", share.getBestClosingRate(LocalDate.of(2001, Month.DECEMBER, 11)));      
    
    // Get 'any' rate for a date for which a rate is available.
    assertEquals("Wrong 'any' rate", shareAnyRates[2].getRate(), share.getAnyRate(shareAnyRates[2].getDate())); 
    // Try to get an 'any' rate for a date for which no rate is available.
    assertNull("Returned rate, instead of null", share.getAnyRate(LocalDate.of(2002, Month.OCTOBER, 29)));      
    // Get the best 'any' rate for the current date.
    assertEquals("Wrong 'any' rate", shareAnyRates[3], share.getBestAnyRate());
    // Get the best 'any' rate for a specific date.
    assertEquals("Wrong 'any' rate", shareAnyRates[2], share.getBestAnyRate(LocalDate.of(2002, Month.DECEMBER, 11)));
    // Try to get the best 'any' rate for a date for which no rate is available.
    assertNull("Returned rate, instead of null", share.getBestAnyRate(LocalDate.of(2001, Month.DECEMBER, 11)));      
    
    // Get best rate for a date for which more than one rate is available (it shall return the closing rate).
    assertEquals("Wrong best rate", shareClosingRates[1], share.getBestRate(shareAnyRates[1].getDate())); 
    // Try to get a best rate for a date for which only an earlier rate is available.
    assertEquals("Wrong best rate", shareOpeningRates[2], share.getBestRate(LocalDate.of(2002, Month.OCTOBER, 29)));      
    // Get the best rate for the current date.
    assertEquals("Wrong best rate", shareOpeningRates[3], share.getBestRate());
    // Try to get the best rate for a date for which no rate is available.
    assertNull("Returned rate, instead of null", share.getBestRate(LocalDate.of(2001, Month.DECEMBER, 11)));      
    
    Share shareRedenomination = Share.getShare("AHOLD KON EUR0.25");
    DateRateTuplet bestOpeningRateShareRedenomination = createDateRateTuplet(shareOpeningRates[3].getDate(), new PgCurrency(PgCurrency.EURO, 1630));
    assertEquals("Wrong opening rate", bestOpeningRateShareRedenomination, shareRedenomination.getBestOpeningRate());
    
    // Add rates to the redenomination of the share.
    for (DateRateTuplet tuplet: shareRedenominationOpeningRates) {
      shareRedenomination.addOpeningRate(tuplet.getDate(), tuplet.getRate());
    }
    
    assertEquals("Wrong opening rate", shareRedenominationOpeningRates[3], shareRedenomination.getBestOpeningRate());
    assertEquals("Wrong opening rate", shareRedenominationOpeningRates[2], shareRedenomination.getBestOpeningRate(LocalDate.of(2004, Month.DECEMBER, 11)));
    assertNull("Returned rate, instead of null", shareRedenomination.getBestOpeningRate(LocalDate.of(2001, Month.DECEMBER, 11)));
    
    
    DateRateTuplet[] sortedRates = {
        shareOpeningRates[1],
        shareOpeningRates[0],
        shareOpeningRates[2],
        shareOpeningRates[4],
        shareOpeningRates[3]
    };
    
    int index = 0;
    for (DateRateTuplet tuplet: share.getOpeningRates()) {
      assertEquals("Wrong opening rate", sortedRates[index++], tuplet);
    }
    
    index = 0;
    for (DateRateTuplet tuplet: share.getOpeningRates(PgCurrency.GUILDER)) {
      DateRateTuplet convertedTuplet = createDateRateTuplet(sortedRates[index].getDate(),
                                                          sortedRates[index].getRate().changeCurrency(PgCurrency.GUILDER));
      index++;
      assertEquals("Wrong opening rate", convertedTuplet, tuplet);
    }
  }
  
  private DateRateTuplet createDateRateTuplet(LocalDate date, PgCurrency rate) {
    DateRateTuplet adaptedDateRateTuple = TYPES_FACTORY.createDateRateTuplet();
    adaptedDateRateTuple.setDate(date);
    adaptedDateRateTuple.setRate(rate);
    
    return adaptedDateRateTuple;
  }

  private enum ExistingShare {
    ASML("ASML Holding", 1, "ASM LITHOGRAPHY EUR0.02", false),
    HEINEKEN("Heineken", 2, "HEINEKEN EUR2", false),
    AHOLD("Kon. Ahold", 2, "AHOLD KON NLG0.50", true),
    POSTBANK_AANDELENFONDS("Postbank Aandelenfonds", 2, "POSTBANK AANDELENFONDS EUR5 -1-", false),
    PHILIPS("Kon. Philips Electronics", 1, "PHILIPS ELECTRONICS KON EUR0.20", false);

    private String  fundName;
    private int     nrOfShares;
    private String  checkShareName;
    private boolean checkShareObsolete;
    
    ExistingShare(String fundName, int nrOfShares, String checkShareName,
        boolean checkShareObsolete) {
      this.fundName = fundName;
      this.nrOfShares = nrOfShares;
      this.checkShareName = checkShareName;
      this.checkShareObsolete = checkShareObsolete;
    }

    public String getFundName() {
      return fundName;
    }

    public int getNrOfShares() {
      return nrOfShares;
    }

    public String getCheckShareName() {
      return checkShareName;
    }

    public boolean isCheckShareObsolete() {
      return checkShareObsolete;
    }
  }
  
  private enum NewShare {
    AHOLD("Kon. Ahold", "Kon. Ahold New", 3),
    LOGICACMG("LogicaCMG", "LogicaCMG New", 2),
    ABN_AMRO("ABN AMRO Holding", "New ABN AMRO Holding", 3),
    POSTBANK_AANDELENFONDS("Postbank Aandelenfonds", "new Postbank Aandelenfonds EUR9", 3),
    TOMTOM("TomTom", "TomTom New", 2);
    
    private String  fundName;
    private String  newShareName;
    private int     newNrOfShares;
    
    NewShare(String fundName, String newShareName, int newNrOfShares) {
      this.fundName = fundName;
      this.newShareName = newShareName;
      this.newNrOfShares = newNrOfShares;
    }

    public String getFundName() {
      return fundName;
    }

    public String getNewShareName() {
      return newShareName;
    }

    public int getNewNrOfShares() {
      return newNrOfShares;
    }
  }
}