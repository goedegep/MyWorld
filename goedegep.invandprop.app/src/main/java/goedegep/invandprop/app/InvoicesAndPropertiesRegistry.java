package goedegep.invandprop.app;

import goedegep.myworld.common.Registry;

public class InvoicesAndPropertiesRegistry extends Registry {
  
  /**
   * Directory with files related to the properties (documents and pictures).
   */
  private static String propertyRelatedFilesFolder = null;
  
  /**
   * Name of the file with all invoices and properties.
   */
  private static String invoicesAndPropertiesFile = null;
  
  /**
   * Singleton instance of the InvoicesAndPropertiesRegistry.
   */
  private static InvoicesAndPropertiesRegistry instance = null;

  /**
   * Get the singleton instance of the InvoicesAndPropertiesRegistry.
   * 
   * @return the singleton instance of the InvoicesAndPropertiesRegistry.
   */
  public static InvoicesAndPropertiesRegistry getInstance() {
    if (instance == null) {
      instance = new InvoicesAndPropertiesRegistry();
    }
    
    return instance;
  }
  
  /**
   * Get the directory with files related to the properties (documents and pictures).
   * 
   * @return the directory with files related to the properties (documents and pictures).
   */
  public String getPropertyRelatedFilesFolder() {
    return propertyRelatedFilesFolder;
  }

  /**
   * Set the directory with files related to the properties (documents and pictures).
   * 
   * @param propertyRelatedFilesFolder the directory with files related to the properties (documents and pictures).
   */
  public void setPropertyRelatedFilesFolder(String propertyRelatedFilesFolder) {
    InvoicesAndPropertiesRegistry.propertyRelatedFilesFolder = propertyRelatedFilesFolder;
  }

  /**
   * Get the name of the file with all invoices and properties.
   * 
   * @return the name of the file with all invoices and properties.
   */
  public String getInvoicesAndPropertiesFile() {
    return invoicesAndPropertiesFile;
  }

  /**
   * Set the name of the file with all invoices and properties.
   * 
   * @param invoicesAndPropertiesFile the name of the file with all invoices and properties.
   */
  public void setInvoicesAndPropertiesFile(String invoicesAndPropertiesFile) {
    InvoicesAndPropertiesRegistry.invoicesAndPropertiesFile = invoicesAndPropertiesFile;
  }

  /**
   * Private constructor for the singleton InvoicesAndPropertiesRegistry.
   */
  private InvoicesAndPropertiesRegistry() {
    super();
    
    setAuthor("Peter Goedegebure");
    setShortProductInfo("A database like application to store information about Invoices and Properties.");
    setPropertyDescriptorsFileName("InvoicesAndPropertiesPropertyDescriptors.xmi");
    setUserPropertiesFileName("InvoicesAndPropertiesUserPreferences.xmi");
  }
 
}
