package goedegep.finan.stockstest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import goedegep.finan.stocks.CompaniesContentHandler;
import goedegep.finan.stocks.CompaniesListener;
import goedegep.finan.stocks.Company;
import goedegep.finan.stocks.CompanyPool;
import goedegep.finan.stocks.CompanyPoolListener;
import goedegep.finan.stocks.Fund;
import goedegep.util.file.FileUtils;

public class CompaniesTest implements CompanyPoolListener, CompaniesListener {
  private static final String TEMP_TEST_DIR = "target/test";
  private static final String TEST_DATA_DIR = "src/test/resources/goedegep/finan/stocks";
  private static final String XSD_DIR = "../goedegep.finan.app/src/main/resources/goedegep/finan/stocks";
  private static final String COMPANIES_FILE = "Companies";
  private static final String[] REQUIRED_DATA_FILES = {COMPANIES_FILE};

  // All companies in input file.
  private static final String[] COMPANY_NAMES = {
    "ABN AMRO Holding", "ASML Holding", "Heijmans", "Heineken",
    "Kon. Ahold", "Kon. DSM", "Kon. KPN", "Kon. Philips Electronics",
    "LogicaCMG", "Nutreco", "Postbank", "Simac",
    "Tiscali", "TomTom"
  };

  // New companies.
  private static final String[] NEW_COMPANY_NAMES = {"Rabobank", "Yvile", "Global Investment Company"};

  private static File testDataDir;
  
  private CompanyPool companyPool;
  private int         companiesUpdatedCalls = 0;
  private int         companyFundAddedCalls = 0;
  
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
    
    File file = new File(testDataDir, COMPANIES_FILE + ".xml" + ".bak");
    file.delete();
    
    if (!testDataDir.delete()) {
      throw new RuntimeException("Test data directory could not be deleted.");
    }
  }
  
  @Before
  public void readCompanyData() throws SAXNotRecognizedException, SAXNotSupportedException {
    companiesUpdatedCalls = 0;
    companyFundAddedCalls = 0;

    File f;
    
    // Initialize companies.
    companyPool = new CompanyPool();
    Fund.resetFunds();
    f = new File(testDataDir, COMPANIES_FILE + ".xml");
    CompaniesContentHandler companiesContentHandler = new CompaniesContentHandler(companyPool);
    companiesContentHandler.read(f.getPath());
  }
  
  @Test
  public void testReadingInput() {
    assertEquals("Wrong number of companies", COMPANY_NAMES.length, companyPool.getNumberOfCompanies());
    for (String companyName: COMPANY_NAMES) {
      Company company = companyPool.getCompany(companyName);
      assertNotNull("Expected company not found", company);
      assertEquals("Wrong name", companyName, company.getName());
      assertEquals("Non-empty fund list", 0, company.getFunds().size());
    }
  }
  
  @Test
  public void testWriting() throws IOException {
    CompaniesContentHandler companiesContentHandler = new CompaniesContentHandler(companyPool);
    companiesContentHandler.write();
    // both the written file and the .bak file should be equal to the original.
    File originalFile = new File(TEST_DATA_DIR, COMPANIES_FILE + ".xml");
    File file = new File(testDataDir, COMPANIES_FILE + ".xml");
    assertTrue("Written file differs from original", FileUtils.contentEquals(originalFile, file));
    file = new File(testDataDir, COMPANIES_FILE + ".xml" + ".bak");
    assertTrue("Backup file differs from original", FileUtils.contentEquals(originalFile, file));
  }
  
  @Test
  public void testGetCompanies() {
    Collection<Company> companies = companyPool.getCompanies();
    Set<String> companyNames = new HashSet<String>();
    for (String companyName: COMPANY_NAMES) {
      companyNames.add(companyName);
    }
    assertEquals("Wrong number of companies", COMPANY_NAMES.length, companies.size());
    for (Company company: companies) {
      assertTrue("Wrong company name", companyNames.contains(company.getName()));
    }
  }
  
  @Test
  public void testAddingCompanies() {
    for (String companyName: NEW_COMPANY_NAMES) {
      companyPool.addCompany(new Company(companyName));
    }
    assertEquals("Wrong number of companies",
        COMPANY_NAMES.length + NEW_COMPANY_NAMES.length, companyPool.getNumberOfCompanies());
    
    for (String companyName: NEW_COMPANY_NAMES) {
      Company company = companyPool.getCompany(companyName);
      assertNotNull("Expected company not found", company);
      assertEquals("Wrong name", companyName, company.getName());
      assertEquals("Non-empty fund list", 0, company.getFunds().size());
    }
  }
  
  @Test
  public void testAddingFunds() {
    Company company = companyPool.getCompany("Nutreco");
    final String[] fundNames = {"FundA" , "FundB"};
    Set<String> fundNamesSet = new HashSet<String>();
    for (String fundName: fundNames) {
      fundNamesSet.add(fundName);
    }
    
    for (String fundName: fundNames) {
      Fund fund = new Fund(fundName, company);
      company.addFund(fund);
    }
    
    for (Fund fund: company.getFunds()) {
      assertTrue("verkeerd fonds", fundNamesSet.contains(fund.getName()));
    }
  }
  
  @Test
  public void testSetGetFunds() {
    Company company = companyPool.getCompany("Nutreco");
    final String[] fundNames = {"FundA" , "FundB"};
    
    // Create a list of funds and set it on the company.
    LinkedList<Fund> funds = new LinkedList<Fund>();
    for (String fundName: fundNames) {
      funds.add(new Fund(fundName, company));
    }
    company.setFunds(funds);
    
    // Try to retrieve all the added funds.
    for (String fundName: fundNames) {
      Fund fund = company.getFund(fundName);
      assertEquals("verkeerd fonds", fundName, fund.getName());
    }
    
    // Ask for a fund that doesn't exist.
    assertNull("Onverwacht fonds verkregen", company.getFund("niet bestaand fonds"));
  }
  
  @Test
  public void testCompanyPoolListenerMechanism() {
    companyPool.addCompaniesListener(this);
    for (String companyName: NEW_COMPANY_NAMES) {
      companyPool.addCompany(new Company(companyName));
    }
    assertEquals("verkeerd aantal callbacks", NEW_COMPANY_NAMES.length, companiesUpdatedCalls);
    
    companyPool.removeCompaniesListener(this);
    companyPool.addCompany(new Company("No listener company"));
    assertEquals("listener niet verwijderd", NEW_COMPANY_NAMES.length, companiesUpdatedCalls);
  }
  
  @Test
  public void testCompanyListenerMechanism() {
    Company.addCompaniesListener(this);
    Company company = companyPool.getCompany("Nutreco");
    final String[] initialFundNames = {"FundA" , "FundB"};
    final String[] newFundNames = {"FundC" , "FundD"};
    
    // Create a list of funds and set it on the company.
    LinkedList<Fund> funds = new LinkedList<Fund>();
    for (String fundName: initialFundNames) {
      funds.add(new Fund(fundName, company));
    }
    company.setFunds(funds);
    assertEquals("verkeerd aantal callbacks", 1, companyFundAddedCalls);
    
    for (String fundName: newFundNames) {
      Fund fund = new Fund(fundName, company);
      company.addFund(fund);
    }
    assertEquals("verkeerd aantal callbacks", 1 + newFundNames.length, companyFundAddedCalls);
    
    
    Company.removeCompaniesListener(this);
    
    company.addFund(new Fund("No listener fund", company));
    assertEquals("listener niet verwijderd", 1 + newFundNames.length, companyFundAddedCalls);
  }

  public void companiesUpdated() {
    companiesUpdatedCalls++;
  }

  public void CompanyFundAdded(Company company) {
    companyFundAddedCalls++;
  }
}