package goedegep.pctools.app.logic;

import goedegep.myworld.common.Registry;

/**
 * This registry class provides information to be shared within the complete PCTools application.
 */
public class PCToolsRegistry extends Registry {
  
  /**
   * Directory where all data files are stored.
   */
  private static String dataDirectory = null;
  
  /**
   * Default file for the disc structure specification.
   */
  private static String defaultDiscStructureSpecificationFile = null;
  
  /**
   * Singleton instance of the PCToolsRegistry.
   */
  private static PCToolsRegistry instance = null;

  /**
   * Get the singleton instance of the PCToolsRegistry.
   * 
   * @return the singleton instance of the PCToolsRegistry.
   */
  public static PCToolsRegistry getInstance() {
    if (instance == null) {
      instance = new PCToolsRegistry();
    }
    
    return instance;
  }
  
  /**
   * Get the directory where all data files are stored.
   * 
   * @return the directory where all data files are stored.
   */
  public String getDataDirectory() {
    return dataDirectory;
  }

  /**
   * Set the directory where all data files are stored.
   * 
   * @param dataDirectory the directory where all data files are stored.
   */
  public void setDataDirectory(String dataDirectory) {
    PCToolsRegistry.dataDirectory = dataDirectory;
  }

  /**
   * Get the default file for the disc structure specification.
   * 
   * @return the default file for the disc structure specification.
   */
  public String getDefaultDiscStructureSpecificationFile() {
    return defaultDiscStructureSpecificationFile;
  }

  /**
   * Set the default file for the disc structure specification.
   * 
   * @param defaultDiscStructureSpecificationFile the default file for the disc structure specification.
   */
  public void setDefaultDiscStructureSpecificationFile(String defaultDiscStructureSpecificationFile) {
    PCToolsRegistry.defaultDiscStructureSpecificationFile = defaultDiscStructureSpecificationFile;
  }

  /**
   * Private constructor for the singleton pattern.
   */
  private PCToolsRegistry() {
    super();
    
    setAuthor("Peter Goedegebure");
    setShortProductInfo("My computer tools");
    setPropertyDescriptorsFileName("PCToolsPropertyDescriptors.xmi");
    setUserPropertiesFileName("PCToolsUserPreferences.xmi");
    setDefaultDiscStructureSpecificationFile("D:\\Database\\MyWorld\\DiscStructureSpecification.xmi");
  }

  @Override
  public boolean setValue(String name, String value) {
    if (super.setValue(name, value)) {
      return true;
    }

    boolean known = true;
    switch (name) {
    case "defaultDiscStructureSpecificationFile" -> defaultDiscStructureSpecificationFile = value;
    case "dataDirectory" -> dataDirectory = value;
    default -> known = false;
    }

    return known;
  }

}