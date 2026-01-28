package goedegep.unitconverter.app;

import goedegep.myworld.common.Registry;

public class UnitConverterRegistry extends Registry {
  
  /**
   * Directory where all data files are stored.
   */
  private static String dataDirectory = null;
  
  /**
   * Singleton instance of the UnitConverterRegistry.
   */
  private static UnitConverterRegistry instance = null;

  /**
   * Get the singleton instance of the UnitConverterRegistry.
   * 
   * @return the singleton instance of the UnitConverterRegistry.
   */
  public static UnitConverterRegistry getInstance() {
    if (instance == null) {
      instance = new UnitConverterRegistry();
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
    UnitConverterRegistry.dataDirectory = dataDirectory;
  }


  private UnitConverterRegistry() {
    super();
    
    setAuthor("Peter Goedegebure");
    setShortProductInfo("Unit conversion application.");
    setPropertyDescriptorsFileName("UnitConverterPropertyDescriptors.xmi");
    setUserPropertiesFileName("UnitConverterUserPreferences.xmi");
  }

}
