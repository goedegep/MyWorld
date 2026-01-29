package goedegep.rolodex.logic;

import goedegep.myworld.common.Registry;

/**
 * This registry class provides information to be shared within the complete Rolodex application.
 */
public class RolodexRegistry extends Registry {
  
  /**
   * Name of the file with all rolodex data.
   */
  private static String rolodexFile = null;
  
  /**
   * Singleton instance of the RolodexRegistry.
   */
  private static RolodexRegistry instance = null;

  /**
   * Get the singleton instance of the RolodexRegistry.
   * 
   * @return the singleton instance of the RolodexRegistry.
   */
  public static RolodexRegistry getInstance() {
    if (instance == null) {
      instance = new RolodexRegistry();
    }
    
    return instance;
  }
  
  /**
   * Get the name of the file with all rolodex data.
   * 
   * @return the name of the file with all rolodex data.
   */  
  public String getRolodexFile() {
    return rolodexFile;
  }

  /**
   * Set the name of the file with all rolodex data.
   * 
   * @param rolodexFile the name of the file with all rolodex data.
   */
  public void setRolodexFile(String rolodexFile) {
    RolodexRegistry.rolodexFile = rolodexFile;
  }

  /**
   * Private constructor for the singleton RolodexRegistry.
   */
  private RolodexRegistry() {
    super();
    
    setAuthor("Peter Goedegebure");
    setShortProductInfo("Addresses, phonenumbers and phone address books.");
    setPropertyDescriptorsFileName("RolodexPropertyDescriptors.xmi");
    setUserPropertiesFileName("RolodexUserPreferences.xmi");
  }

  @Override
  public boolean setValue(String name, String value) {
    if (super.setValue(name, value)) {
      return true;
    }

    boolean known = true;
    switch (name) {
    case "rolodexFile" -> rolodexFile = value;
    default -> known = false;
    }

    return known;
  }

}
