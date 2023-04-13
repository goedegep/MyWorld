package goedegep.properties.app;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesHandlerTest {
  
  static {
    Logger logger = Logger.getLogger("");
    logger.setLevel(Level.SEVERE);
  }
  
//  /**
//   * Test reading a property descriptors file and filling a registry, but without customer specific settings.
//   * <p>
//   * This test uses the properties descriptor file "TestPropertyDescriptorsNoUserSettings.xmi",
//   * which refers to a non existing user settings file.
//   * 
//   * @throws Exception
//   */
//  @Test
//  public void handlePropertiesNoUserSettingsFileTest() throws Exception {
//    Path projectPath = Paths.get("src", "test", "resources", "goedegep", "app", "configuration");
//    String projectPathAsString = projectPath.toAbsolutePath().toString();
//    PropertiesHandler.handleProperties(true, projectPathAsString, "TestPropertyDescriptorsNoUserSettings.xmi");
//    
//    // Check the properties which are stored in the registry.
//    assertEquals("Wrong compare result", "Peter Goedegebure", TestRegistry.author);
//    assertEquals("Wrong compare result", "TestAppConfiguration.xmi", TestRegistry.configurationFile);
//    assertEquals("Wrong compare result", "Copyright (c) 2001-2012", TestRegistry.copyrightMessage);
//    assertEquals("Wrong compare result", createResourcePath(true, projectPathAsString, "NonExistingFile.xmi"), TestRegistry.customPropertiesFile);
//    assertEquals("Wrong compare result", "1.0", TestRegistry.version);
//    assertEquals("Wrong compare result", "DefaultDataDir", TestRegistry.dataDirectory);
//  }
  
//  /**
//   * Test reading a property descriptors file and filling a registry, with customer specific settings.
//   * <p>
//   * This test uses the properties descriptor file "TestPropertyDescriptorsUserSettings.xmi",
//   * which refers to an existing user settings file.
//   * 
//   * @throws Exception
//   */
//  @Test
//  public void handlePropertiesUserSettingsFileTest() throws Exception {
//    Path projectPath = Paths.get("src", "test", "resources", "goedegep", "app", "configuration");
//    String projectPathAsString = projectPath.toAbsolutePath().toString();
//    PropertiesHandler.handleProperties(true, projectPathAsString, "TestPropertyDescriptorsUserSettings.xmi");
//    
//    // Check the properties which are stored in the registry.
//    assertEquals("Wrong compare result", "Peter Goedegebure", TestRegistry.author);
//    assertEquals("Wrong compare result", "TestAppConfiguration.xmi", TestRegistry.configurationFile);
//    assertEquals("Wrong compare result", "Copyright (c) 2001-2012", TestRegistry.copyrightMessage);
//    assertEquals("Wrong compare result", createResourcePath(true, projectPathAsString, "TestPropertiesUserSettings.xmi"), TestRegistry.customPropertiesFile);
//    assertEquals("Wrong compare result", "1.0", TestRegistry.version);
//    assertEquals("Wrong compare result", "UserDataDir", TestRegistry.dataDirectory);
//  }
//
  
  /**
   * Create the path name for a resource file.
   * <p>
   * If the program is running in Eclipse, the resource path is the <code>fileName</code> in the <code>projectPath</code> directory.
   * Else it is simply <code>fileName</code> (as it is in the current directory).
   * 
   * @param runningInEclipse indicates whether the program is running in Eclipse or not.
   * @param projectPath path to the project which contains the resource.
   * @param fileName filename of the resource.
   * @return a path to the resource
   */
  @SuppressWarnings("unused")
  private static String createResourcePath(boolean runningInEclipse, String projectPath, String fileName) {
    if (runningInEclipse  &&  (projectPath != null)) {
      File file = new File(projectPath, fileName);
      return file.getAbsolutePath();
    } else {
      return fileName;
    }
  }
}
