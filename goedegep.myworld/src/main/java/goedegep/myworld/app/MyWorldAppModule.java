package goedegep.myworld.app;

import java.util.ArrayList;
import java.util.List;

/**
 * This enum provides constants for all application modules within MyWorld.
 * <p>
 * MyWorld is a top level application, on top of a number of separate application modules. This enum provides constants to identify
 * those application modules.
 * A module is identified by its moduleName.<br/>
 * A module also has a list of modules on which it depends.
 */
public enum MyWorldAppModule {
  DEMO("Demo"),
//  EVENTS("Events"),
  INVOICES_AND_PROPERTIES("InvoicesAndProperties"),
  ROLODEX("Rolodex"),
//  FINAN("Finan", ROLODEX),
//  FINAN_AANSTELLING(null),
//  FINAN_ABNAMRO_BANK(null),
//  FINAN_COMPANIES(null),
//  FINAN_DIREKTBANK(null),
//  FINAN_HYPOTHEEK(null),
//  FINAN_INVESTMENT_INSURANCES(null),
//  FINAN_LYNX(null),
//  FINAN_POSTBANK(null),
  MEDIA("Media"),
  PCTOOLS("PCTools"),
  UNIT_CONVERTER("UnitConverter"),
  VACATIONS("Vacations"),
  MY_WORLD("MyWorld", MEDIA, INVOICES_AND_PROPERTIES, PCTOOLS, ROLODEX, UNIT_CONVERTER, VACATIONS);
  
  private String moduleName;
  private List<MyWorldAppModule> dependsOnModules = new ArrayList<>();
  
  MyWorldAppModule(String moduleName, MyWorldAppModule... dependsOnModules) {
    this.moduleName = moduleName;
    for (MyWorldAppModule appModule: dependsOnModules) {
      this.dependsOnModules.add(appModule);
    }
  }
  
  /**
   * Get the name of the application module.
   * 
   * @return The name of the application module.
   */
  public String getModuleName() {
    return moduleName;
  }
  
  /**
   * Get the list of other application modules on which this application module depends.
   * 
   * @return the list of application modules on which this application module depends. There is always a list returned, although it may be empty.
   */
  public List<MyWorldAppModule> getDependsOnModules() {
    return dependsOnModules;
  }
  
  /**
   * Get the application module for a specific module name.
   * 
   * @param moduleName the name of the application module.
   * @return the AppModule for the specified name, or null if no module with the given name exists.
   */
  public static MyWorldAppModule getAppModuleForName(String moduleName) {
    for (MyWorldAppModule appModule: values()) {
      if ((appModule.moduleName != null)  &&
          appModule.moduleName.equals(moduleName)) {
        return appModule;
      }
    }
    
    return null;
  }
  
}