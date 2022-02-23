package goedegep.jfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.appgen.EMFResource;
import goedegep.configuration.model.ConfigurationFactory;
import goedegep.configuration.model.ConfigurationPackage;
import goedegep.configuration.model.Look;
import goedegep.configuration.model.LookInfo;
import goedegep.configuration.model.ModuleLook;
import goedegep.util.string.StringUtil;

/**
 * This class can be used to maintain a set of {@link CustomizationFx}s, identified by their related module names.
 */
public class CustomizationsFx {
  private static final Logger LOGGER = Logger.getLogger(CustomizationsFx.class.getName());
  
  /**
   * A map of module names to their Customization.
   */
  private static Map<String, CustomizationFx> moduleNameToCustomizationMap = new HashMap<>();
  
  /**
   * Get the customizaton for a specific module.
   * 
   * @param moduleName the name of the module for which the customization is requested.
   * @return the CustomizationFx for the specified module.
   * @throws RuntimeException if no customization is available for the specified module.
   */
  public static CustomizationFx getCustomization(String moduleName) {
    CustomizationFx customization = moduleNameToCustomizationMap.get(moduleName);
    if (customization == null) {
      throw new RuntimeException("No Customization found for module: " + moduleName +
          ", available Customizations: " + StringUtil.stringCollectionToCommaSeparatedStrings(moduleNameToCustomizationMap.keySet()));
    }
    return customization;
  }


  /**
   * Add a {@link CustomizationFx} for each {@link ModuleLook} in a configuration file.
   * 
   * @param configurationFile a configuration file
   */
  public static void addCustomizations(File configurationFile) {
    LOGGER.info("'=> configurationFile=" + configurationFile.getAbsolutePath());

    EMFResource<LookInfo> emfResource = new EMFResource<>(
        ConfigurationPackage.eINSTANCE,
        () -> ConfigurationFactory.eINSTANCE.createLookInfo());
    LookInfo lookInfo;
    try {
      lookInfo = emfResource.load(configurationFile.getAbsolutePath());
      
      for (ModuleLook moduleLook: lookInfo.getModuleLooks()) {
        addCustomizations(moduleLook);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Add Customizations for a ModuleLook (and recursively for any sub module looks).
   * 
   * @param moduleLook the ModuleLook for which customizations are to be added.
   */
  public static void addCustomizations(ModuleLook moduleLook) {
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
    
    String resourcesClassName = moduleLook.getResourcesClassName();
    if (resourcesClassName == null) {
      throw new RuntimeException("No resourcesClassName in ModuleLook for module: " + moduleName);
    }
    
    try {
      LOGGER.info("resourcesClassName: " + resourcesClassName);
      Class<?> resourceClass = Class.forName(resourcesClassName);
      LOGGER.fine("resourceClass: " + resourceClass.getName());
      Constructor<?> constructor = resourceClass.getConstructor();
      AppResourcesFx appResources = (AppResourcesFx) constructor.newInstance();
      CustomizationFx customization = new CustomizationFx(look, appResources);
      CustomizationFx existingCustomization = moduleNameToCustomizationMap.put(moduleName, customization);
      
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
    
    /*
     * handle add child modules
     */
    for (ModuleLook childModuleLook: moduleLook.getModuleLooks()) {
      addCustomizations(childModuleLook);
    }
  }
}
