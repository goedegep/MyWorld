package goedegep.finan.stockstest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

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
import goedegep.finan.stocks.OptionSerie;
import goedegep.finan.stocks.OptionSeriesContentHandler;
import goedegep.finan.stocks.OptionType;
import goedegep.finan.stocks.Share;
import goedegep.util.file.FileUtils;
import goedegep.util.money.PgCurrency;

public class OptionSeriesTest {
  private static final String TEMP_TEST_DIR = "target/test";
  private static final String TEST_DATA_DIR = "src/test/resources/goedegep/finan/stocks";
  private static final String XSD_DIR = "../goedegep.finan.app/src/main/resources/goedegep/finan/stocks";
  private static final String COMPANIES_FILE = "Companies";
  private static final String FUNDS_FILE = "CompanyFunds";
  private static final String SHARES_FILE = "FundShares";
  private static final String OPTION_SERIES_FILE = "OptionSeries";
  private static final String[] REQUIRED_DATA_FILES = {COMPANIES_FILE, FUNDS_FILE, SHARES_FILE, OPTION_SERIES_FILE};

  // Option serie in input file.
  private static final OptionType OPTION_TYPE = OptionType.CALL;
  private static final String SHARE_NAME = "ASM LITHOGRAPHY EUR0.02";
  private static final int EXPIRATION_MONTH = 7;
  private static final int EXPIRATION_YEAR = 2002;
  private static final PgCurrency UITOEFENINGS_KOERS = new PgCurrency(PgCurrency.EURO, 2200);

  private static File testDataDir;
  
  private CompanyPool                companyPool;


  @BeforeClass
  public static void prepareFiles() throws IOException {
//    System.out.println("Test data dir: " + System.getProperty("user.dir") + File.separator + TEMP_TEST_DIR);
    testDataDir = new File(System.getProperty("user.dir") + File.separator + TEMP_TEST_DIR);
    if (testDataDir.exists()) {
      throw new RuntimeException("Can not run test as temporary directory exists");
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
    
    File file = new File(testDataDir, OPTION_SERIES_FILE + ".xml" + ".bak");
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
        
    // Initialize option series.
    f = new File(testDataDir, OPTION_SERIES_FILE + ".xml");
    OptionSeriesContentHandler optionSeriesContentHandler = new OptionSeriesContentHandler();
    optionSeriesContentHandler.read(null, f.getPath());
  }
  
  @Test
  public void testReadingInput() {
    assertEquals("Wrong number of option series", 1, OptionSerie.getOptionSeries().size());
    Share share = Share.getShare(SHARE_NAME);
    OptionSerie optionSerie = OptionSerie.getOptionSerie(OPTION_TYPE, share, EXPIRATION_MONTH, EXPIRATION_YEAR, UITOEFENINGS_KOERS);
    assertNotNull("Expected option series not found", optionSerie);
    assertEquals("Wrong option type", OPTION_TYPE, optionSerie.getOptionType());
    assertEquals("Wrong share", SHARE_NAME, optionSerie.getShare().getName());
    assertEquals("Wrong expirationMonth", EXPIRATION_MONTH, optionSerie.getExpirationMonth());
    assertEquals("Wrong expirationYear", EXPIRATION_YEAR, optionSerie.getExpirationYear());
    assertEquals("Wrong uitoefeningsKoers", UITOEFENINGS_KOERS, optionSerie.getUitoefeningsKoers());
  }
  
// TODO check why these tests fail. Check on Before.  
//  @Test
//  public void testWriting() throws IOException {
//    OptionSeriesContentHandler.write();
//    // both the written file and the .bak file should be equal to the original.
//    File originalFile = new File(TEST_DATA_DIR, OPTION_SERIES_FILE + ".xml");
//    File file = new File(testDataDir, OPTION_SERIES_FILE + ".xml");
//    assertTrue("Written file differs from original", FileUtils.contentEquals(originalFile, file));
//    file = new File(testDataDir, OPTION_SERIES_FILE + ".xml" + ".bak");
//    assertTrue("Backup file differs from original", FileUtils.contentEquals(originalFile, file));
//  }
  
//  @Test
//  public void testAddingOptionSeries() {
//    for (NewOptionSerie newOptionSerie: NewOptionSerie.values()) {
//      new OptionSerie(newOptionSerie.getOptionType(), Share.getShare(newOptionSerie.getShareName()),
//          newOptionSerie.getExpirationMonth(), newOptionSerie.getExpirationYear(),
//          newOptionSerie.getUitOefeningsKoers());
//    }
//    assertEquals("Wrong number of option series", 3, OptionSerie.getOptionSeries().size());
//    
//    for (NewOptionSerie newOptionSerie: NewOptionSerie.values()) {
//      Share share = Share.getShare(newOptionSerie.getShareName());
//      OptionSerie optionSerie = OptionSerie.getOptionSerie(newOptionSerie.getOptionType(), share,
//          newOptionSerie.getExpirationMonth(), newOptionSerie.getExpirationYear(), newOptionSerie.getUitOefeningsKoers());
//      assertNotNull("Expected option series not found", optionSerie);
//      assertEquals("Wrong option type", newOptionSerie.getOptionType(), optionSerie.getOptionType());
//      assertEquals("Wrong share", newOptionSerie.getShareName(), optionSerie.getShare().getName());
//      assertEquals("Wrong expirationMonth", newOptionSerie.getExpirationMonth(), optionSerie.getExpirationMonth());
//      assertEquals("Wrong expirationYear", newOptionSerie.getExpirationYear(), optionSerie.getExpirationYear());
//      assertEquals("Wrong uitoefeningsKoers", newOptionSerie.getUitOefeningsKoers(), optionSerie.getUitoefeningsKoers());
//    }
//  }
  
  enum NewOptionSerie {
    ASML(OptionType.CALL, "ASM LITHOGRAPHY EUR0.02", 10, 2002, new PgCurrency(PgCurrency.EURO, 2500)),
    HEINEKEN(OptionType.PUT, "HEINEKEN EUR1.6", 10, 2002, new PgCurrency(PgCurrency.EURO, 500));

    private OptionType optionType;
    private String     shareName;
    private int        expirationMonth;
    private int        expirationYear;
    private PgCurrency uitOefeningsKoers;
    
    private NewOptionSerie(OptionType optionType, String shareName,
        int expirationMonth, int expirationYear, PgCurrency uitOefeningsKoers) {
      this.optionType = optionType;
      this.shareName = shareName;
      this.expirationMonth = expirationMonth;
      this.expirationYear = expirationYear;
      this.uitOefeningsKoers = uitOefeningsKoers;
    }

    protected OptionType getOptionType() {
      return optionType;
    }

    protected String getShareName() {
      return shareName;
    }

    protected int getExpirationMonth() {
      return expirationMonth;
    }

    protected int getExpirationYear() {
      return expirationYear;
    }

    protected PgCurrency getUitOefeningsKoers() {
      return uitOefeningsKoers;
    }
  }
}