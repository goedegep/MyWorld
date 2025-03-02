package goedegep.finan.basictest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import goedegep.finan.basic.FinancieleEenheid;
import goedegep.finan.basic.FinancieleEenheidHandler;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.Birthday;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.util.file.FileUtils;

public class FinancieleEenheidTest {
  private static final RolodexFactory ROLODEX_FACTORY = RolodexFactory.eINSTANCE;
  private static final String TEMP_TEST_DIR = "target/test";
  private static final String TEST_DATA_DIR = "src/test/resources/goedegep/finan/basic";
  private static final String XSD_DIR = "../goedegep.finan.app/src/main/resources/goedegep/finan/basic";
  private static final String FINANCIELE_EENHEDEN_FILE = "FinancieleEenheden";
  private static final String[] REQUIRED_DATA_FILES = {FINANCIELE_EENHEDEN_FILE};
  
  private static final String[][] CITY_DESCRIPTORS = {
    // City name
    {"Eindhoven"},
    {"Vlissingen"}
  };
  
  private static final String[][] ADDRESS_DESCRIPTORS = {
    // id ,streetname, housenumber, postal code, cityId (is name).
    {"_1Kl5IFRBEeK2xboPrrxLcg", "De Regge", "24", "5626 GZ", "Eindhoven"},
    {"_1Kl5IVRBEeK2xboPrrxLcg", "Een straat", "1098", "0123 XK", "Vlissingen"}
  };
  
  private static final String[][] PERSON_DESCRIPTORS = {
    // id, first name, day, month, year of birth
    {"_g3YpMFRCEeK_HqJvNMtzVA", "Peter", "23", "1", "1957"},
    {"_g3YpMVRCEeK_HqJvNMtzVA", "Voornaam", "1", "2", "1909"},
    {"_g3YpMlRCEeK_HqJvNMtzVA", "Xinjo", "4", "11", "1978"},
    {"_g3YpM1RCEeK_HqJvNMtzVA", "Zomaar-iemand", "28", "9", "2008"},
    {"_g3YpNFRCEeK_HqJvNMtzVA", "Een ander", "11", "12", "2001"},
    {"_g3YpNVRCEeK_HqJvNMtzVA", "Naamloos", "11", "8", "2001"}
  };
  
  private static final String[][] FINANCIELE_EENHEDEN_DESCRIPTORS = {
    // te naam stelling, addressesId, aantal pers.,  personId 1, ..., personId n
    {"one", "_1Kl5IFRBEeK2xboPrrxLcg", "4", "_g3YpMFRCEeK_HqJvNMtzVA", "_g3YpMVRCEeK_HqJvNMtzVA", "_g3YpMlRCEeK_HqJvNMtzVA", "_g3YpM1RCEeK_HqJvNMtzVA"},
    {"Het gezin", "_1Kl5IVRBEeK2xboPrrxLcg", "3", "_g3YpNVRCEeK_HqJvNMtzVA", "_g3YpNFRCEeK_HqJvNMtzVA", "_g3YpM1RCEeK_HqJvNMtzVA"},
    {"The company", "_1Kl5IVRBEeK2xboPrrxLcg", "2", "_g3YpMFRCEeK_HqJvNMtzVA", "_g3YpNVRCEeK_HqJvNMtzVA"}
  };
  
  private static File testDataDir;
  
  private static Rolodex rolodex;
  private FinancieleEenheidHandler financieleEenheidHandler;
  private List<FinancieleEenheid> financieleEenheden;
  
  static {
    rolodex = ROLODEX_FACTORY.createRolodex();    
    
    // Create cities from descriptors.
    rolodex.setCityList(ROLODEX_FACTORY.createCityList());
    for (String[] cityDescriptor: CITY_DESCRIPTORS) {
      City city = ROLODEX_FACTORY.createCity();
      city.setCityName(cityDescriptor[0]);
      rolodex.getCityList().getCities().add(city);
    }
    
    // Create addresses from descriptors.
    rolodex.setAddressList(ROLODEX_FACTORY.createAddressList());
    for (String[] addressDescriptor: ADDRESS_DESCRIPTORS) {
      Address address = ROLODEX_FACTORY.createAddress();
      address.setId(addressDescriptor[0]);
      address.setStreetName(addressDescriptor[1]);
      address.setHouseNumber(Integer.parseInt(addressDescriptor[2]));
      address.setPostalCode(addressDescriptor[3]);
      City city = rolodex.getCityList().findCityById(addressDescriptor[4]);
      if (city == null) {
        throw new RuntimeException("city not found");
      }
      address.setCity(city);
      rolodex.getAddressList().getAddresses().add(address);
    }
    
    // Create persons from descriptors.
    rolodex.setPersonList(ROLODEX_FACTORY.createPersonList());
    for (String[] personDescriptor: PERSON_DESCRIPTORS) {
      Person person = ROLODEX_FACTORY.createPerson();
      person.setId(personDescriptor[0]);
      person.setFirstname(personDescriptor[1]);
      int day = Integer.parseInt(personDescriptor[2]);
      int month = Integer.parseInt(personDescriptor[3]);
      int year = Integer.parseInt(personDescriptor[4]);
      Birthday birthday = ROLODEX_FACTORY.createBirthday();
      birthday.setDay(day);
      birthday.setMonth(month);
      birthday.setYear(year);
      person.setBirthday(birthday);
      rolodex.getPersonList().getPersons().add(person);
    }
  }
  
  @BeforeClass
  public static void prepareFiles() throws IOException {
    Logger.getLogger("").setLevel(Level.WARNING);
    
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
    
    File file = new File(testDataDir, FINANCIELE_EENHEDEN_FILE + ".xml" + ".bak");
    file.delete();
    
    if (!testDataDir.delete()) {
      throw new RuntimeException("Test data directory could not be deleted.");
    }
  }

  @Before
  public void readFinancieleEenheden() throws SAXException, ParserConfigurationException {
        
    // Lees financiele eenheden.
    File f = new File(testDataDir, FINANCIELE_EENHEDEN_FILE + ".xml");
    financieleEenheidHandler = new FinancieleEenheidHandler(rolodex);
    financieleEenheidHandler.read(f.getPath());
    financieleEenheden = financieleEenheidHandler.getFinancieleEenheden();
  }
  
  
  @Test
  public void testReadingInput() {
    List<FinancieleEenheid> expectedFinancieleEenheden = createFinancieleEenheden(FINANCIELE_EENHEDEN_DESCRIPTORS);
    assertEquals("Verkeerd aantal financiele eenheden", expectedFinancieleEenheden.size(), financieleEenheden.size());
    for (FinancieleEenheid expectedFinancieleEenheid: expectedFinancieleEenheden) {
      FinancieleEenheid financieleEenheid = financieleEenheden.remove(0);
      assertTrue("Financiele eenheden niet gelijk: verwacht" + expectedFinancieleEenheid +
          ", is: " + financieleEenheid, expectedFinancieleEenheid.equals(financieleEenheid));
    }
  }
  
  @Test
  public void testWriting() throws IOException {
    financieleEenheidHandler.write();
    // both the written file and the .bak file should be equal to the original.
    File originalFile = new File(TEST_DATA_DIR, FINANCIELE_EENHEDEN_FILE + ".xml");
    File file = new File(testDataDir, FINANCIELE_EENHEDEN_FILE + ".xml");
    assertTrue("Written file differs from original", FileUtils.contentEquals(originalFile, file));
    file = new File(testDataDir, FINANCIELE_EENHEDEN_FILE + ".xml" + ".bak");
    assertTrue("Backup file differs from original", FileUtils.contentEquals(originalFile, file));
  }
  
  private List<FinancieleEenheid> createFinancieleEenheden(String[][] financieleEenhedenDescriptors) {
    List<FinancieleEenheid> financieleEenheden = new ArrayList<FinancieleEenheid>();
    
    for (String[] financieleEenheidDescriptor: financieleEenhedenDescriptors) {
      FinancieleEenheid financieleEenheid = new FinancieleEenheid();
      financieleEenheid.setTeNaamStelling(financieleEenheidDescriptor[0]);
      Address address = rolodex.getAddressList().findAddressById(financieleEenheidDescriptor[1]);
      if (address == null) {
        throw new RuntimeException("Address not found, id = " + financieleEenheidDescriptor[1]);
      }
      financieleEenheid.setAdres(address);
      int descriptorIndex = 3;  // Index for first person index.
      for (int i = 0; i < Integer.parseInt(financieleEenheidDescriptor[2]); i++) {
        Person person = rolodex.getPersonList().findPersonById(financieleEenheidDescriptor[descriptorIndex++]);
        if (person == null) {
          throw new RuntimeException("Person not found, id = " + financieleEenheidDescriptor[descriptorIndex - 1]);
        }
        financieleEenheid.addPersoon(person);
      }
      financieleEenheden.add(financieleEenheid);
    }
    return financieleEenheden;
  }
}
