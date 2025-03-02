package goedegep.finan.stockstest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import goedegep.finan.stocks.ClaimEmission;
import goedegep.finan.stocks.ClaimEmissionsContentHandler;
import goedegep.finan.stocks.CompaniesContentHandler;
import goedegep.finan.stocks.CompanyFundsContentHandler;
import goedegep.finan.stocks.CompanyPool;
import goedegep.finan.stocks.Fund;
import goedegep.finan.stocks.FundSharesContentHandler;
import goedegep.finan.stocks.Share;
import goedegep.util.file.FileUtils;
import goedegep.util.logging.MyLoggingFormatter;
import goedegep.util.money.PgCurrency;

public class ClaimEmissionsTest {
  private static final String TEMP_TEST_DIR = "target/test";
  private static final String TEST_DATA_DIR = "src/test/resources/goedegep/finan/stocks";
  private static final String XSD_DIR = "../goedegep.finan.app/src/main/resources/goedegep/finan/stocks";
  private static final String COMPANIES_FILE = "Companies";
  private static final String FUNDS_FILE = "CompanyFunds";
  private static final String SHARES_FILE = "FundShares";
  private static final String CLAIM_EMISSIONS_FILE = "ClaimEmissions";
  private static final String[] REQUIRED_DATA_FILES = {COMPANIES_FILE, FUNDS_FILE, SHARES_FILE, CLAIM_EMISSIONS_FILE};
  
  private static final String SHARE_NAME = "AHOLD KON EUR0.25";
  private static final String CLAIM_ID = "10-12-2003";
  private static final int FROM_AMOUNT = 3;
  private static final int TO_AMOUNT = 2;
  private static final PgCurrency PRICE_PER_SHARE = new PgCurrency(PgCurrency.EURO, 483L);
  private static final PgCurrency RIGHT_RATE = new PgCurrency(PgCurrency.EURO, 63L);

  private static File testDataDir;
  
  private CompanyPool                companyPool;
  
  
  @BeforeClass
  public static void prepareFiles() throws IOException {
    logSetup();
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
    
    File file = new File(testDataDir, CLAIM_EMISSIONS_FILE + ".xml" + ".bak");
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
    
    // Read the claim emissions.
    f = new File(testDataDir, CLAIM_EMISSIONS_FILE + ".xml");
    ClaimEmissionsContentHandler claimEmissionsContentHandler = new ClaimEmissionsContentHandler();
    claimEmissionsContentHandler.read(null, f.getPath());
  }
  
  @Test
  public void testReadingInput() {
    Share share = Share.getShare(SHARE_NAME);
    ClaimEmission claimEmission = share.getClaimEmission(CLAIM_ID);
    assertNotNull("Expected emission not found", claimEmission);
    assertEquals("Wrong share", share, claimEmission.getShare());
    assertEquals("Wrong from amount", FROM_AMOUNT, claimEmission.getFromAmount());
    assertEquals("Wrong to amount", TO_AMOUNT, claimEmission.getToAmount());
    assertEquals("Wrong price per share", PRICE_PER_SHARE, claimEmission.getPricePerShare());
    assertEquals("Wrong right rate", RIGHT_RATE, claimEmission.getRightRate());
  }
  
  @Test
  public void testWriting() throws IOException {
    ClaimEmissionsContentHandler.write();
    // both the written file and the .bak file should be equal to the original.
    File originalFile = new File(TEST_DATA_DIR, CLAIM_EMISSIONS_FILE + ".xml");
    File file = new File(testDataDir, CLAIM_EMISSIONS_FILE + ".xml");
    assertTrue("Written file differs from original", FileUtils.contentEquals(originalFile, file));
    file = new File(testDataDir, CLAIM_EMISSIONS_FILE + ".xml" + ".bak");
    assertTrue("Backup file differs from original", FileUtils.contentEquals(originalFile, file));
  }
  
  private static void logSetup() {
    // Create Logger
    Logger logger = Logger.getLogger("");
    
    Handler consoleHandler = null;
    for (Handler handler: logger.getHandlers()) {
      if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
        consoleHandler = handler;
        break;
      }
    }
    consoleHandler.setFormatter(new MyLoggingFormatter());
    consoleHandler.setLevel(Level.INFO);
  }
}
