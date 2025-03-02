package goedegep.finan.stockstest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import goedegep.finan.stocks.DividendType;
import goedegep.finan.stocks.Fund;
import goedegep.finan.stocks.FundSharesContentHandler;
import goedegep.finan.stocks.Share;
import goedegep.finan.stocks.ShareDividend;
import goedegep.finan.stocks.ShareDividendsContentHandler;
import goedegep.util.file.FileUtils;

public class DividendsTest {
  private static final String TEMP_TEST_DIR = "target/test";
  private static final String TEST_DATA_DIR = "src/test/resources/goedegep/finan/stocks";
  private static final String XSD_DIR = "../goedegep.finan.app/src/main/resources/goedegep/finan/stocks";
  private static final String COMPANIES_FILE = "Companies";
  private static final String FUNDS_FILE = "CompanyFunds";
  private static final String SHARES_FILE = "FundShares";
  private static final String DIVIDENDS_FILE = "ShareDividends";
  private static final String[] REQUIRED_DATA_FILES = {COMPANIES_FILE, FUNDS_FILE, SHARES_FILE, DIVIDENDS_FILE};

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
    
    File file = new File(testDataDir, DIVIDENDS_FILE + ".xml" + ".bak");
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
  }
  
  @Test
  public void testReadingInput() {
    for (ExistingDividend existingDividend: ExistingDividend.values()) {
      Share share = Share.getShare(existingDividend.getShareName());
      assertNotNull("Expected share " + existingDividend.getShareName() + " not found", share);
      assertEquals("Wrong name", existingDividend.getShareName(), share.getName());
      assertEquals("Wrong number of dividends", existingDividend.getNrOfDividends(), share.getDividends().size());
      if (existingDividend.getNrOfDividends() != 0) {
        String dividendReference = ShareDividend.createReferenceString(existingDividend.getCheckDividendName(), existingDividend.getCheckDividendYear());
        ShareDividend dividend = share.getDividend(dividendReference);
        assertNotNull("Dividend " + dividendReference +
            " not found for share " + share.getName(), dividend);
        assertEquals("Wrong dividend name", existingDividend.getCheckDividendName(), dividend.getName());
        assertEquals("Wrong dividend year", existingDividend.getCheckDividendYear(), dividend.getYear());
      }
    }
  }
  
  @Test
  public void testWriting() throws IOException {
    ShareDividendsContentHandler.write();
    // both the written file and the .bak file should be equal to the original.
    File originalFile = new File(TEST_DATA_DIR, DIVIDENDS_FILE + ".xml");
    File file = new File(testDataDir, DIVIDENDS_FILE + ".xml");
    assertTrue("Written file differs from original", FileUtils.contentEquals(originalFile, file));
    file = new File(testDataDir, DIVIDENDS_FILE + ".xml" + ".bak");
    assertTrue("Backup file differs from original", FileUtils.contentEquals(originalFile, file));
  }
  
  @Test
  public void testAddingDividends() {
    for (NewDividend newDividend: NewDividend.values()) {
      Share share = Share.getShare(newDividend.getShareName());
      ShareDividend dividend = new ShareDividend(newDividend.getNewDividendName(),
          newDividend.getNewDividendYear(), newDividend.getNewDividendType(),
          null, share, null);
      share.addDividend(dividend);
      assertEquals("Wrong number of new dividends", newDividend.getNewNrOfDividends(), share.getDividends().size());
      String dividendReference = ShareDividend.createReferenceString(newDividend.getNewDividendName(), newDividend.getNewDividendYear());      
      dividend = share.getDividend(dividendReference);
      assertNotNull("Dividend " + dividendReference +
          " not found for share " + share.getName(), dividend);
      assertEquals("Wrong dividend name", newDividend.getNewDividendName(), dividend.getName());
      assertEquals("Wrong dividend year", newDividend.getNewDividendYear(), dividend.getYear());
    }
  }
  
  enum ExistingDividend {
    POSTBK_WERELDMERKENFD("POSTBK WERELDMERKENFD EUR0.8", 4, "DIV04", null, DividendType.CASH),
    HEINEKEN("HEINEKEN EUR2", 1, null, 2004, DividendType.CASH),
    AHOLD("AHOLD KON EUR0.25", 1, "DIV02", null, DividendType.STOCK_OR_CASH),
    POSTBANK_MULTIMEDIAFONDS("POSTBANK MULTIMEDIAFONDS EUR5", 0, null, null, null),
    ABN_AMRO("ABN AMRO HOLDING EUR0.56", 6, "-DIV03-", null, DividendType.STOCK_OR_CASH);
    
    private String       shareName;
    private int          nrOfDividends;
    private String       checkDividendName;
    private Integer      checkDividendYear;
    private DividendType checkDividendType;
    
    ExistingDividend(String shareName, int nrOfDividends,
        String checkDividendName, Integer checkDividendYear,
        DividendType checkDividendType) {
      this.shareName = shareName;
      this.nrOfDividends = nrOfDividends;
      this.checkDividendName = checkDividendName;
      this.checkDividendYear = checkDividendYear;
      this.checkDividendType = checkDividendType;
    }

    public String getShareName() {
      return shareName;
    }

    public int getNrOfDividends() {
      return nrOfDividends;
    }

    public String getCheckDividendName() {
      return checkDividendName;
    }

    public Integer getCheckDividendYear() {
      return checkDividendYear;
    }

    public DividendType getCheckDividendType() {
      return checkDividendType;
    }
  }
  
  enum NewDividend {
    AHOLD("AHOLD KON EUR0.25", "INT2020", null, DividendType.CASH, 2),
    LOGICACMG("LOGICACMG GBP0.1 -NL-", null, 2034, DividendType.CASH, 3),
    ABN_AMRO("ABN AMRO HOLDING EUR0.56", "INTERIM", 2034, DividendType.CASH, 7),
    POSTBANK_AANDELENFONDS("POSTBANK AANDELENFONDS NLG10", "keuze 2034", null, DividendType.STOCK_OR_CASH, 1),
    TOMTOM("TOMTOM EUR.2", "TomTom New", null, DividendType.CASH, 1);
    
    private String       shareName;
    private String       newDividendName;
    private Integer      newDividendYear;
    private DividendType newDividendType;
    private int          newNrOfDividends;
    
    NewDividend(String shareName, String newDividendName,
        Integer newDividendYear, DividendType newDividendType, int newNrOfDividends) {
      this.shareName = shareName;
      this.newDividendName = newDividendName;
      this.newDividendYear = newDividendYear;
      this.newDividendType = newDividendType;
      this.newNrOfDividends = newNrOfDividends;
    }

    public String getShareName() {
      return shareName;
    }

    public String getNewDividendName() {
      return newDividendName;
    }

    public Integer getNewDividendYear() {
      return newDividendYear;
    }

    public DividendType getNewDividendType() {
      return newDividendType;
    }

    public int getNewNrOfDividends() {
      return newNrOfDividends;
    }
  }
}