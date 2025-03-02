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
import goedegep.finan.stocks.Company;
import goedegep.finan.stocks.CompanyFundsContentHandler;
import goedegep.finan.stocks.CompanyPool;
import goedegep.finan.stocks.Fund;
import goedegep.util.file.FileUtils;

public class FundsTest {
  private static final String TEMP_TEST_DIR = "target/test";
  private static final String TEST_DATA_DIR = "src/test/resources/goedegep/finan/stocks";
  private static final String XSD_DIR = "../goedegep.finan.app/src/main/resources/goedegep/finan/stocks";
  private static final String COMPANIES_FILE = "Companies";
  private static final String FUNDS_FILE = "CompanyFunds";
  private static final String[] REQUIRED_DATA_FILES = {COMPANIES_FILE, FUNDS_FILE};

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
    
    File file = new File(testDataDir, FUNDS_FILE + ".xml" + ".bak");
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
  }
  
  @Test
  public void testReadingInput() {
    for (ExistingFund existingFund: ExistingFund.values()) {
      Company company = companyPool.getCompany(existingFund.getCompanyName());
      assertNotNull("Expected company not found", company);
      assertEquals("Wrong name", existingFund.getCompanyName(), company.getName());
      assertEquals("Wrong number of funds for company " + existingFund.getCompanyName(),
          existingFund.getNrOfFunds(), company.getFunds().size());
      Fund fund = company.getFund(existingFund.getCheckFundName());
      assertNotNull("Fund " + existingFund.getCheckFundName() +
          " not found for company " + company.getName(), fund);
      assertEquals("Wrong fund name", existingFund.getCheckFundName(), fund.getName());
    }
  }
  
  @Test
  public void testWriting() throws IOException {
    CompanyFundsContentHandler companyFundsContentHandler = new CompanyFundsContentHandler(companyPool);
    companyFundsContentHandler.write();
    // both the written file and the .bak file should be equal to the original.
    File originalFile = new File(TEST_DATA_DIR, FUNDS_FILE + ".xml");
    File file = new File(testDataDir, FUNDS_FILE + ".xml");
    assertTrue("Written file differs from original", FileUtils.contentEquals(originalFile, file));
    file = new File(testDataDir, FUNDS_FILE + ".xml" + ".bak");
    assertTrue("Backup file differs from original", FileUtils.contentEquals(originalFile, file));
  }
  
  @Test
  public void testAddingFunds() {
    for (NewFund newFund: NewFund.values()) {
      Company company = companyPool.getCompany(newFund.getCompanyName());
      Fund fund = new Fund(newFund.getNewFundName(), company);
      company.addFund(fund);
      assertEquals("Wrong number of new funds", newFund.getNewNrOfFunds(), company.getFunds().size());
      fund = company.getFund(newFund.getNewFundName());
      assertNotNull("Fund " + newFund.getNewFundName() +
          " not found for company " + company.getName(), fund);
      assertEquals("Wrong fund name", newFund.getNewFundName(), fund.getName());
    }
  }
  
  enum ExistingFund {
    AHOLD("Kon. Ahold", 1, "Kon. Ahold"),
    LOGICACMG("LogicaCMG", 1, "LogicaCMG"),
    ABN_AMRO("ABN AMRO Holding", 1, "ABN AMRO Holding"),
    POSTBANK("Postbank", 5, "Postbank Financiele Wereldfonds"),
    TOMTOM("TomTom", 1, "TomTom");
    
    private String  companyName;
    private int     nrOfFunds;
    private String  checkFundName;
    
    ExistingFund(String companyName, int nrOfFunds, String checkFundName) {
      this.companyName = companyName;
      this.nrOfFunds = nrOfFunds;
      this.checkFundName = checkFundName;
    }

    public String getCompanyName() {
      return companyName;
    }

    public int getNrOfFunds() {
      return nrOfFunds;
    }

    public String getCheckFundName() {
      return checkFundName;
    }
  }
  
  enum NewFund {
    AHOLD("Kon. Ahold", "Kon. Ahold New", 2),
    LOGICACMG("LogicaCMG", "LogicaCMG New", 2),
    ABN_AMRO("ABN AMRO Holding", "New ABN AMRO Holding", 2),
    POSTBANK("Postbank", "Postbank nieuwfonds", 6),
    TOMTOM("TomTom", "TomTom New", 2);
    
    private String  companyName;
    private String  newFundName;
    private int     newNrOfFunds;
    
    NewFund(String companyName, String newFundName, int newNrOfFunds) {
      this.companyName = companyName;
      this.newFundName = newFundName;
      this.newNrOfFunds = newNrOfFunds;
    }

    public String getCompanyName() {
      return companyName;
    }

    public String getNewFundName() {
      return newFundName;
    }

    public int getNewNrOfFunds() {
      return newNrOfFunds;
    }

  }
}