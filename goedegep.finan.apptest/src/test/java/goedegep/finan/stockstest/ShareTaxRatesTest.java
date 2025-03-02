package goedegep.finan.stockstest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import goedegep.finan.stocks.OptionSeriesContentHandler;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividendsContentHandler;
import goedegep.finan.stocks.ShareTaxRatesContentHandler;
import goedegep.util.file.FileUtils;
import goedegep.util.money.PgCurrency;
import goedegep.util.money.PgCurrencyFormat;

public class ShareTaxRatesTest {
  private static final PgCurrencyFormat  cf = new PgCurrencyFormat();
  
  private static final String TEMP_TEST_DIR = "target/test";
  private static final String TEST_DATA_DIR = "src/test/resources/goedegep/finan/stocks";
  private static final String XSD_DIR = "../goedegep.finan.app/src/main/resources/goedegep/finan/stocks";
  private static final String COMPANIES_FILE = "Companies";
  private static final String FUNDS_FILE = "CompanyFunds";
  private static final String SHARES_FILE = "FundShares";
  private static final String DIVIDENDS_FILE = "ShareDividends";
  private static final String OPTION_SERIES_FILE = "OptionSeries";
  private static final String TAX_RATES_FILE = "BelastingKoersen";
  private static final String[] REQUIRED_DATA_FILES = {COMPANIES_FILE, FUNDS_FILE, SHARES_FILE, DIVIDENDS_FILE, OPTION_SERIES_FILE, TAX_RATES_FILE};

  private static File testDataDir;
  
  private CompanyPool                companyPool;


  @BeforeClass
  public static void prepareFiles() throws IOException {
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
    
    File file = new File(testDataDir, TAX_RATES_FILE + ".xml" + ".bak");
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
    
    // Initialize share dividends.
    f = new File(testDataDir, DIVIDENDS_FILE + ".xml");
    ShareDividendsContentHandler shareDividendsContentHandler = new ShareDividendsContentHandler();
    shareDividendsContentHandler.read(null, f.getPath());
    
    // Initialize option series.
    f = new File(testDataDir, OPTION_SERIES_FILE + ".xml");
    OptionSeriesContentHandler optionSeriesContentHandler = new OptionSeriesContentHandler();
    optionSeriesContentHandler.read(null, f.getPath());
    
    // Initialize share tax rates.
    f = new File(testDataDir, TAX_RATES_FILE + ".xml");
    ShareTaxRatesContentHandler shareTaxRatesContentHandler = new ShareTaxRatesContentHandler();
    shareTaxRatesContentHandler.read(null, f.getPath());
  }
  
  @Test
  public void testReadingInput() {
    for (ExistingTaxRate existingTaxRate: ExistingTaxRate.values()) {
      Share share = Share.getShare(existingTaxRate.getShareName());
      PgCurrency quarterRate = share.getQuarterRate(existingTaxRate.getYear(), existingTaxRate.getQuarter());
      assertEquals("Wrong rate", cf.format(existingTaxRate.getRate()), cf.format(quarterRate));
    }
  }
  
  @Test
  public void testWriting() throws IOException {
    ShareTaxRatesContentHandler.write();
    // both the written file and the .bak file should be equal to the original.
    File originalFile = new File(TEST_DATA_DIR, TAX_RATES_FILE + ".xml");
    File file = new File(testDataDir, TAX_RATES_FILE + ".xml");
    assertTrue("Written file differs from original", FileUtils.contentEquals(originalFile, file));
    file = new File(testDataDir, TAX_RATES_FILE + ".xml" + ".bak");
    assertTrue("Backup file differs from original", FileUtils.contentEquals(originalFile, file));
  }
  
  @Test
  public void testAddingTaxRates() {
    for (NewTaxRate newTaxRate: NewTaxRate.values()) {
      Share share = Share.getShare(newTaxRate.getShareName());
      share.addQuarterRate(newTaxRate.getYear(), newTaxRate.getQuarter(), newTaxRate.getRate());
      PgCurrency quarterRate = share.getQuarterRate(newTaxRate.getYear(), newTaxRate.getQuarter());
      assertEquals("Wrong rate", cf.format(newTaxRate.getRate()), cf.format(quarterRate));
    }
  }
  
  enum ExistingTaxRate {
    AHOLD_2004_2("AHOLD KON EUR0.25", 2004, 2, new PgCurrency(PgCurrency.EURO, 645)),
    TISCALI_2004_1("TISCALI EUR0.50", 2004, 1, new PgCurrency(PgCurrency.EURO, 471)),
    KPN_2000_3("KON KPN EUR0.24", 2000, 3, new PgCurrency(PgCurrency.EURO, 2468));
    
    private String     shareName;
    private int        year;
    private int        quarter;
    private PgCurrency rate;
    
    ExistingTaxRate(String shareName,
        int year, int quarter, PgCurrency rate) {
      this.shareName = shareName;
      this.year = year;
      this.quarter = quarter;
      this.rate = rate;
    }

    public String getShareName() {
      return shareName;
    }

    public int getYear() {
      return year;
    }
    public int getQuarter() {
      return quarter;
    }

    public PgCurrency getRate() {
      return rate;
    }
  }
  
  enum NewTaxRate {
    AHOLD_1992_2("AHOLD KON EUR0.25", 1992, 2, new PgCurrency(PgCurrency.EURO, 2230)),
    TISCALI_2007_1("TISCALI EUR0.50", 2007, 1, new PgCurrency(PgCurrency.EURO, 632)),
    KPN_2006_3("KON KPN EUR0.24", 2006, 3, new PgCurrency(PgCurrency.EURO, 88));
    
    private String     shareName;
    private int        year;
    private int        quarter;
    private PgCurrency rate;
    
    NewTaxRate(String shareName,
        int year, int quarter, PgCurrency rate) {
      this.shareName = shareName;
      this.year = year;
      this.quarter = quarter;
      this.rate = rate;
    }

    public String getShareName() {
      return shareName;
    }

    public int getYear() {
      return year;
    }
    public int getQuarter() {
      return quarter;
    }

    public PgCurrency getRate() {
      return rate;
    }
  }
}