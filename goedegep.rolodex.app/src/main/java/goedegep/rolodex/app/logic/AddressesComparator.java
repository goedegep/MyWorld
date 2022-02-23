package goedegep.rolodex.app.logic;

import java.util.Comparator;

import goedegep.rolodex.model.Address;

/**
 * This class provides a (singleton) <code>Comparator</code> to compare Addresses.
 */
public class AddressesComparator implements Comparator<Address> {
  private static AddressesComparator instance;
  private static CitiesComparator citiesComparator;

  /**
   * Private constructor.
   */
  private AddressesComparator() {
    citiesComparator = CitiesComparator.getInstance();
  }
  
  @Override
  public int compare(Address address1, Address address2) {
    int result = 0;
    
    if ((address1 == null)  &&  (address2 == null)) {
      return 0;
    } else if ((address1 == null)  &&  (address2 != null)) {
      result = -1;
    } else if ((address1 != null)  &&  (address2 == null)) {
      result = 1;
    }
    
    if (result != 0) {
      return result;
    }
    
    String streetName1 = address1.getStreetName();
    String streetName2 = address2.getStreetName();
    if ((streetName1 == null)  &&  (streetName2 != null)) {
      result = -1;
    } else if ((streetName1 != null)  &&  (streetName2 == null)) {
      result = 1;
    } else if ((streetName1 != null)  &&  (streetName2 != null)){
      result = streetName1.compareTo(streetName2);
    }
    
    if (result != 0) {
      return result;
    }
    
    Integer houseNumber1 = address1.getHouseNumber();
    Integer houseNumber2 = address2.getHouseNumber();
    if ((houseNumber1 == null)  &&  (houseNumber2 != null)) {
      result = -1;
    } else if ((houseNumber1 != null)  &&  (houseNumber2 == null)) {
      result = 1;
    } else if ((houseNumber1 != null)  &&  (houseNumber2 != null)) {
      result = houseNumber1.compareTo(houseNumber2);
    }
    
    if (result != 0) {
      return result;
    }
    
    String houseNumberExtension1 = address1.getHouseNumberExtension();
    String houseNumberExtension2 = address2.getHouseNumberExtension();
    if ((houseNumberExtension1 == null)  &&  (houseNumberExtension2 != null)) {
      result = -1;
    } else if ((houseNumberExtension1 != null)  &&  (houseNumberExtension2 == null)) {
      result = 1;
    } else if ((houseNumberExtension1 != null)  &&  (houseNumberExtension2 != null)) {
      result = houseNumberExtension1.compareTo(houseNumberExtension2);
    }
    
    if (result != 0) {
      return result;
    }
    
    String poBox1 = address1.getPOBox();
    String poBox2 = address2.getPOBox();
    if ((poBox1 == null)  &&  (poBox2 != null)) {
      result = -1;
    } else if ((poBox1 != null)  &&  (poBox2 == null)) {
      result = 1;
    } else if ((poBox1 != null)  &&  (poBox2 != null)) {
      result = poBox1.compareTo(poBox2);
    }
    
    if (result != 0) {
      return result;
    }
        
    return citiesComparator.compare(address1.getCity(), address2.getCity());
   }

  /**
   * Get the Comparator instance.
   * 
   * @return the Comparator instance.
   */
  public static AddressesComparator getInstance() {
    if (instance == null) {
      instance = new AddressesComparator();
    }
    
    return instance;
  }
}
