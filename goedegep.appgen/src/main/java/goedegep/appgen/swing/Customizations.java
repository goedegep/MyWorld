package goedegep.appgen.swing;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.configuration.model.ConfigurationFactory;
import goedegep.configuration.model.ConfigurationPackage;
import goedegep.configuration.model.Look;
import goedegep.configuration.model.LookInfo;
import goedegep.configuration.model.ModuleLook;
import goedegep.util.emf.EMFResource;

/**
 * This class is used to maintain a set of {@link Customization}s, identified by their related module names.
 */
public class Customizations {
  private static final Logger LOGGER = Logger.getLogger(Customizations.class.getName());
  
  /**
   * Indication that for a moduleName no customizations are to be added.
   * In the end this should be the case for all modules.
   */
  private static final String SKIP_IDENTIFIER = "skip";
  
  /**
   * A map of module names to their Customization.
   */
  private static Map<String, Customization> moduleNameToCustomizationMap = new HashMap<>();
  
  /**
   * Dirty solution as this class will be removed when Swing is phased out.
   * A map to know the Resources class per module (in the FX version the class name is part of the ModuleLook).
   */
  private static Map<String, String> moduleNameToResourcesClassNameMap = new HashMap<>();
  
  static {
    moduleNameToResourcesClassNameMap.put("FINAN", "goedegep.app.finan.finanapp.FinanResources");
    moduleNameToResourcesClassNameMap.put("FINAN_AANSTELLING", "goedegep.app.finan.aanstelling.AanstellingResources");
    moduleNameToResourcesClassNameMap.put("FINAN_ABNAMRO_BANK", "goedegep.app.finan.abnamrobank.AbnAmroBankResources");
    moduleNameToResourcesClassNameMap.put("FINAN_COMPANIES", "goedegep.app.finan.stocksapp.StocksResources");
    moduleNameToResourcesClassNameMap.put("FINAN_DIREKTBANK", "goedegep.app.finan.direktbankapp.DirektbankResources");
    moduleNameToResourcesClassNameMap.put("FINAN_HYPOTHEEK", SKIP_IDENTIFIER);
    moduleNameToResourcesClassNameMap.put("FINAN_LYNX", "goedegep.app.finan.lynxapp.LynxResources");
    moduleNameToResourcesClassNameMap.put("FINAN_POSTBANK", "goedegep.app.finan.postbankapp.PbResources");
    moduleNameToResourcesClassNameMap.put("MEDIA", SKIP_IDENTIFIER);
    moduleNameToResourcesClassNameMap.put("INVOICES_AND_PROPERTIES", SKIP_IDENTIFIER);
    moduleNameToResourcesClassNameMap.put("ROLODEX", SKIP_IDENTIFIER);
  }
  
  
  /**
   * Get the customizaton for a specific module.
   * 
   * @param moduleName the name of the module for which the customization is requested.
   * @return the "@code Customization} for the specified module.
   * @throws RuntimeException if no customization is available for the specified module.
   */
  public static Customization getCustomization(String moduleName) {
    Customization customization = moduleNameToCustomizationMap.get(moduleName);
    if (customization == null) {
      throw new RuntimeException("No Customization found for module: " + moduleName);
    }
    return customization;
  }


  /**
   * Add a {@link Customization} for each {@link ModuleLook} in a configuration file.
   * 
   * @param configurationFile a configuration file
   */
  public static void addCustomizations(File configurationFile) {
    LOGGER.info("'=> configurationFile=" + configurationFile.getAbsolutePath());
    
    EMFResource<LookInfo> emfResource = new EMFResource<>(
        ConfigurationPackage.eINSTANCE,
        () -> ConfigurationFactory.eINSTANCE.createLookInfo(), ".xmi");
    LookInfo lookInfo;
    try {
      lookInfo = emfResource.load(configurationFile.getAbsolutePath());
      
      for (ModuleLook moduleLook: lookInfo.getModuleLooks()) {
        if (moduleLook.getModuleName().equals("FINAN_AANSTELLING")) {
          LOGGER.info("skipping ModuleLook: " + moduleLook.getModuleName());
          continue;
        }
        addCustomizations(moduleLook);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Add Customizations for a ModuleLook.
   * 
   * @param moduleLook the ModuleLook for which customizations are to be added.
   */
  private static void addCustomizations(ModuleLook moduleLook) {
    /*
     * add the customization for this module
     */
    
    String moduleName = moduleLook.getModuleName();
    if (moduleName == null) {
      throw new RuntimeException("No moduleName for moduleLook");
    }
    LOGGER.info("Adding Customizations for module: " + moduleName);
    
    
    Look look = moduleLook.getLook();
    if (look == null) {
      throw new RuntimeException("No Look in ModuleLook for module: " + moduleName);
    }
    
    String resourcesClassName = moduleNameToResourcesClassNameMap.get(moduleName);
    if (resourcesClassName == null) {
      throw new RuntimeException("No resourcesClassName in ModuleLook for module: " + moduleName);
    } else if (resourcesClassName.equals(SKIP_IDENTIFIER)) {
      LOGGER.info("skipping ModuleLook: " + moduleName);
      return;
    }
    
    try {
      LOGGER.info("resourcesClassName: " + resourcesClassName);
      Class<?> resourceClass = Class.forName(resourcesClassName);
      LOGGER.fine("resourceClass: " + resourceClass.getName());
      Constructor<?> constructor = resourceClass.getConstructor();
      AppResources appResources = (AppResources) constructor.newInstance();
      Customization customization = new Customization(appResources);
      Customization existingCustomization = moduleNameToCustomizationMap.put(moduleName, customization);
      
      if (existingCustomization != null) {
        throw new RuntimeException("Existing customization overwritten for moduleName: " + moduleName);
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    
    // handle add child modules
    for (ModuleLook childModuleLook: moduleLook.getModuleLooks()) {
      addCustomizations(childModuleLook);
    }
  }
    
  /**
   * Add a module Look.
   * @param moduleName
   * @param look
   */
  public static void addModuleLook(String moduleName) {
    Customization customization = getCustomization(moduleName);
    
    if (customization == null) {
      customization = new Customization();
      moduleNameToCustomizationMap.put(moduleName, customization);
    } else {
      throw new IllegalArgumentException("Trying to add the look for an existing module name");
    }
  }
}
