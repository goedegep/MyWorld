package goedegep.travels.logic;

import java.net.URL;

import goedegep.myworld.common.Registry;

/**
 * This registry class provides information to be shared within the complete Vacations application.
 */
public class TravelsRegistry extends Registry {
  
  /**
   * Name of the file with information about the vacations.
   */
  private static String vacationsFileName = null;
  
  /**
   * Name of the file with the vacation checklist information.
   */
  private static String vacationChecklistFileName = null;
  
  /**
   * Name of the folder with all information about vacations.
   */
  private static String vacationsFolderName = null;
  
  /**
   * Name of the folder with all vacation pictures.
   */
  private static String vacationPicturesFolderName = null;
  
  /**
   * Comma-separated list of folder names to ignore in the vacation pictures folder.
   */
  private static String ignoreVacationPictureFolders = null;
  
  /**
   * List of known files in the vacations folder.
   */
  private static String knownFiles = null;
  
  /**
   * User Agent string for the Nominatim geocoding service.
   */
  private static String nominatimUserAgent = null;
  
  /**
   * Singleton instance of the VacationsRegistry.
   */
  private static TravelsRegistry instance = null;
    
  public URL getURLForFileName(String fileName) {
    URL url = getClass().getResource(fileName);
    
    return url;
  }
  

  /**
   * Get the singleton instance of the VacationsRegistry.
   * 
   * @return the singleton instance of the VacationsRegistry.
   */
  public static TravelsRegistry getInstance() {
    if (instance == null) {
      instance = new TravelsRegistry();
    }
    
    return instance;
  }
  
  /**
   * Get the name of the file with information about the vacations.
   * 
   * @return the name of the file with information about the vacations.
   */  
  public String getVacationsFileName() {
    return vacationsFileName;
  }

  /**
   * Set the name of the file with information about the vacations.
   * 
   * @param vacationsFileName the name of the file with information about the vacations.
   */
  public void setVacationsFileName(String vacationsFileName) {
    TravelsRegistry.vacationsFileName = vacationsFileName;
  }

  /**
   * Get the name of the file with the vacation checklist information.
   * 
   * @return the name of the file with the vacation checklist information.
   */
  public String getVacationChecklistFileName() {
    return vacationChecklistFileName;
  }

   /**
    * Set the name of the file with the vacation checklist information.
    * 
    * @param vacationChecklistFileName the name of the file with the vacation checklist information.
    */
  public void setVacationChecklistFileName(String vacationChecklistFileName) {
    TravelsRegistry.vacationChecklistFileName = vacationChecklistFileName;
  }

  /**
   * Get the name of the folder with all information about vacations.
   * 
   * @return the name of the folder with all information about vacations.
   */
  public String getVacationsFolderName() {
    return vacationsFolderName;
  }

  /**
   * Set the name of the folder with all information about vacations.
   * 
   * @param vacationsFolderName the name of the folder with all information about vacations.
   */
  public void setVacationsFolderName(String vacationsFolderName) {
    TravelsRegistry.vacationsFolderName = vacationsFolderName;
  }

  /**
   * Get the name of the folder with all vacation pictures.
   * 
   * @return the name of the folder with all vacation pictures.
   */
  public String getVacationPicturesFolderName() {
    return vacationPicturesFolderName;
  }

  /**
   * Set the name of the folder with all vacation pictures.
   * 
   * @param vacationPicturesFolderName the name of the folder with all vacation pictures.
   */
  public void setVacationPicturesFolderName(String vacationPicturesFolderName) {
    TravelsRegistry.vacationPicturesFolderName = vacationPicturesFolderName;
  }

  /**
   * Get the comma-separated list of folder names to ignore in the vacation pictures folder.
   * 
   * @return the comma-separated list of folder names to ignore in the vacation pictures folder.
   */
  public String getIgnoreVacationPictureFolders() {
    return ignoreVacationPictureFolders;
  }

  /**
   * Set the comma-separated list of folder names to ignore in the vacation pictures folder.
   * 
   * @param ignoreVacationPictureFolders the comma-separated list of folder names to ignore in the vacation pictures folder.
   */
  public void setIgnoreVacationPictureFolders(String ignoreVacationPictureFolders) {
    TravelsRegistry.ignoreVacationPictureFolders = ignoreVacationPictureFolders;
  }

  /**
   * Get the list of known files in the vacations folder.
   * 
   * @return the list of known files in the vacations folder.
   */
  public String getKnownFiles() {
    return knownFiles;
  }
  /**
   * Set the list of known files in the vacations folder.
   * 
   * @param knownFiles the list of known files in the vacations folder.
   */

  public void setKnownFiles(String knownFiles) {
    TravelsRegistry.knownFiles = knownFiles;
  }

  /**
   * Get the User Agent string for the Nominatim geocoding service.
   * 
   * @return the User Agent string for the Nominatim geocoding service.
   */
  public String getNominatimUserAgent() {
    return nominatimUserAgent;
  }

  /**
   * Set the User Agent string for the Nominatim geocoding service.
   * 
   * @param nominatimUserAgent the User Agent string for the Nominatim geocoding service.
   */
  public void setNominatimUserAgent(String nominatimUserAgent) {
    TravelsRegistry.nominatimUserAgent = nominatimUserAgent;
  }


  private TravelsRegistry() {
    super();
    
    setAuthor("Peter Goedegebure");
    setShortProductInfo("Travels - Information about travels, like vacations, trips, etc.");
    setPropertyDescriptorsFileName("..\\..\\..\\goedegep.travels.app\\src\\main\\resources\\goedegep\\travels\\app\\logic\\VacationsPropertyDescriptors.xmi");
    setUserPropertiesFileName("VacationsUserPreferences.xmi");
    setVacationsFileName("D:\\Database\\Vacations\\Vacation.xmi");
    setVacationChecklistFileName("D:\\Database\\Vacations\\VacationChecklist.xmi");
    setVacationsFolderName("D:\\Database\\Vacations");
    setKnownFiles("Vakanties.xmi;VakantieChecklist.xmi");
    setVacationPicturesFolderName("D:\\Photo\\Vacations");
    setIgnoreVacationPictureFolders("weg;Originals");
  }
    
  @Override
  public boolean setValue(String name, String value) {
    if (super.setValue(name, value)) {
      return true;
    }
    
    boolean known = true;
    switch (name) {
      case "vacationsFileName" -> vacationsFileName = value;
      case "vacationChecklistFileName" -> vacationChecklistFileName = value;
      case "vacationsFolderName" -> vacationsFolderName = value;
      case "vacationPicturesFolderName" -> vacationPicturesFolderName = value;
      case "ignoreVacationPictureFolders" -> ignoreVacationPictureFolders = value;
      case "nominatimUserAgent" -> nominatimUserAgent = value;
      default -> known = false;
    }
    
    return known;
  }

}