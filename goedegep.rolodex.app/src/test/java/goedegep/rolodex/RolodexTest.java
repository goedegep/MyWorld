package goedegep.rolodex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import goedegep.rolodex.app.RolodexRegistry;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.Gender;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.Rolodex;
import goedegep.rolodex.model.RolodexFactory;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.util.emf.EMFResource;

public class RolodexTest {
//  private static final String TEST_DATA_DIR = "src/test/resources/goedegep/rolodex";
  private static final String TEST_ROLODEX = "src/test/resources/goedegep/rolodex/TestRolodex.xmi";
  
  private static Rolodex rolodex;
  
  @BeforeClass
  public static void openTestRolodex() throws IOException {
    Logger.getLogger("").setLevel(Level.WARNING);

//    RolodexRegistry.dataDirectory = TEST_DATA_DIR;
    RolodexRegistry.rolodexFile = TEST_ROLODEX;
    
    RolodexRegistry.rolodexResource = new EMFResource<>(
        RolodexPackage.eINSTANCE,
        () -> RolodexFactory.eINSTANCE.createRolodex());
    File rolodexFile = new File(RolodexRegistry.rolodexFile);
    RolodexRegistry.rolodexResource.load(rolodexFile.getAbsolutePath());

    rolodex = RolodexRegistry.rolodexResource.getEObject();
  }
  
  @AfterClass
  public static void saveTestRolodex() throws IOException {
//    rolodexResource.save();
  }

  /**
   * Test whether the correct address is found based on its ID
   */
  @Test
  public void testFindAddressById() {
    // Find the address.
    Address address = rolodex.getAddressList().findAddressById("_Ds-pUEoiEeKPSYxGsj9hPA");

    // Assert the returned address contains the expected data.
    assertNotNull("Address not found, while it should have been found.", address);
    assertEquals("Id is wrong", "_Ds-pUEoiEeKPSYxGsj9hPA", address.getId());
    assertEquals("Streetname is wrong", "De Regge", address.getStreetName());
    assertEquals("HouseNumber is wrong", Integer.valueOf(24), address.getHouseNumber());
    assertNull("HouseNumberAddition is not null", address.getHouseNumberExtension());
    assertEquals("City is wrong", "Eindhoven", address.getCity().getCityName());
    assertEquals("PostalCode is wrong", "5626 GZ", address.getPostalCode());
    assertNull("P.O. Box is not null", address.getPOBox());
    assertEquals("Country is wrong", "Nederland", address.getCity().getCountry().getCountryName());
  }

  /**
   * Test whether the correct address is found based on its postal code and housenumber
   */
  @Test
  public void testFindAddressByPostalCode() {
    // Find the address.
    Address address = rolodex.getAddressList().findAddressByPostalCode("5626 GZ", 24, null);

    // Assert the returned address contains the expected data.
    assertNotNull("Address not found, while it should have been found.", address);
    assertEquals("Id is wrong", "_Ds-pUEoiEeKPSYxGsj9hPA", address.getId());
    assertEquals("Streetname is wrong", "De Regge", address.getStreetName());
    assertEquals("HouseNumber is wrong", Integer.valueOf(24), address.getHouseNumber());
    assertNull("HouseNumberAddition is not null", address.getHouseNumberExtension());
    assertEquals("City is wrong", "Eindhoven", address.getCity().getCityName());
    assertEquals("PostalCode is wrong", "5626 GZ", address.getPostalCode());
    assertNull("P.O. Box is not null", address.getPOBox());
    assertEquals("Country is wrong", "Nederland", address.getCity().getCountry().getCountryName());
  }


  /**
   * Test whether the correct person is found based on its Id.
   */
  @Test
  public void testFindPersonById() throws Exception {
    // Find the person.
    Person person = rolodex.getPersonList().findPersonById("_ZsnCAEolEeKdWpwP0iE9bQ");

    // Assert the returned person contains the expected data.
    assertNotNull("Person not found, while it should have been found.", person);
    assertEquals("Id is wrong", "_ZsnCAEolEeKdWpwP0iE9bQ", person.getId());
    assertEquals("Firstname is wrong", "Peter", person.getFirstname());
    assertNull("Infix is not null", person.getInfix());
    assertEquals("Surname is wrong", "Goedegebure", person.getSurname());
    assertEquals("Initials are wrong", "P.", person.getInitials());
    assertEquals("Name is wrong", "Peter Goedegebure", person.getName());
    assertEquals("Gender is wrong", Gender.MALE, person.getGender());
    assertEquals("Address postcode is wrong", "5626 GZ", person.getAddress().getPostalCode());
    assertEquals("Address house number is wrong", Integer.valueOf(24), person.getAddress().getHouseNumber());
  }
}
