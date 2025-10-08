package goedegep.jfx;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import goedegep.configuration.model.ConfigurationFactory;
import goedegep.configuration.model.ConfigurationPackage;
import goedegep.configuration.model.Look;
import goedegep.configuration.model.LookInfo;
import goedegep.configuration.model.ModuleLook;
import goedegep.util.Tuplet;
import goedegep.util.emf.EMFResource;
import goedegep.util.string.StringUtil;

/**
 * This class reads configuration files and creates {@link CustomizationFx}s from their contents.<br/>
 * The customizations can also be added to a set of customizations, from which they can then be obtained by their module name.<br/>
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
   * Read a single CustomizationFx from a configuration file.
   * <p>
   * This method expects the configuration file to contain exactly one Customization.
   * If the configuration file contains no Customization or more than one Customization, a RuntimeException is thrown.
   * 
   * @param configurationFileURL the URL for a configuration file.
   * @return the CustomizationFx read from the configuration file.
   * @throws RuntimeException if no Customization or more than one Customization is found in the configuration file.
   */
  public static CustomizationFx readCustomization(URL configurationFileURL) {
    List<Tuplet<String, CustomizationFx>> customizations = readCustomizations(configurationFileURL);
    if (customizations.isEmpty()) {
      throw new RuntimeException("No Customizations found in configuration file: " + configurationFileURL);
    }
    if (customizations.size() > 1) {
      throw new RuntimeException("More than one Customization found in configuration file: " + configurationFileURL);
    }
    return customizations.get(0).getObject2();
  }

  /**
   * Read the Customizations from a configuration file.
   * 
   * @param configurationFileURL the URL for a configuration file.
   * @return a list of Tuples containing the module name and the corresponding CustomizationFx.
   */
  public static List<Tuplet<String, CustomizationFx>> readCustomizations(URL configurationFileURL) {
    List<Tuplet<String, CustomizationFx>> customizations = new ArrayList<>();

    EMFResource<LookInfo> emfResource = new EMFResource<>(
        ConfigurationPackage.eINSTANCE,
        () -> ConfigurationFactory.eINSTANCE.createLookInfo(), ".xmi");
    
    LookInfo lookInfo;
    try {
      
      lookInfo = emfResource.load(configurationFileURL);

      for (ModuleLook moduleLook: lookInfo.getModuleLooks()) {
        Tuplet<String, CustomizationFx> customizationTuplet = customizationFromModuleLook(moduleLook);
        customizations.add(customizationTuplet);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return customizations;
  }
  
  /**
   * Read the Customization from a configuration file and add them to the set of Customizations.
   * 
   * @param configurationFileURL URL for a configuration file containing Customizations.
   * @return a list of Tuples containing the module name and the corresponding CustomizationFx read from the file.
   */
  public static List<Tuplet<String, CustomizationFx>> addCustomizations(URL configurationFileURL) {
    List<Tuplet<String, CustomizationFx>> customizations = readCustomizations(configurationFileURL);
    for (Tuplet<String, CustomizationFx> customization : customizations) {
      String moduleName = customization.getObject1();
      CustomizationFx existingCustomization = moduleNameToCustomizationMap.put(moduleName, customization.getObject2());
      
      if (existingCustomization != null) {
        throw new RuntimeException("Existing customization overwritten for moduleName: " + moduleName);
      }
    }
    
    return customizations;
  }

  /**
   * Read the Customization from a configuration file and add them to the set of Customizations.
   * 
   * @param configurationFileURL a File object representing the configuration file containing Customizations.
   * @return a list of Tuples containing the module name and the corresponding CustomizationFx read from the file.
   */
  public static void addCustomizations(File configurationFile) {
    URI configurationFileUri = configurationFile.toURI();
    try {
      URL configurationFileUrl = configurationFileUri.toURL();
      addCustomizations(configurationFileUrl);
    } catch (MalformedURLException e) {
      LOGGER.severe("Malformed URL for configuration file: " + configurationFile.getAbsolutePath());
    }
  }
  
  /**
   * Create a CustomizationFx from a ModuleLook.
   * 
   * @param moduleLook the ModuleLook to create a CustomizationFx from.
   * @return a Tuple containing the module name and the corresponding CustomizationFx.
   */
  private static Tuplet<String, CustomizationFx> customizationFromModuleLook(ModuleLook moduleLook) {
    String moduleName = moduleLook.getModuleName();
    if (moduleName == null) {
      throw new RuntimeException("No moduleName for moduleLook: " + moduleLook.toString());
    }
    
    Look look = moduleLook.getLook();
    if (look == null) {
      throw new RuntimeException("No Look in ModuleLook for module: " + moduleName);
    }
    
    String resourcesClassName = moduleLook.getResourcesClassName();
    if (resourcesClassName == null) {
      throw new RuntimeException("No resourcesClassName in ModuleLook for module: " + moduleName);
    }
    
    try {
      Class<?> resourceClass = Class.forName(resourcesClassName);
      Constructor<?> constructor = resourceClass.getConstructor();
      AppResourcesFx appResources = (AppResourcesFx) constructor.newInstance();
      CustomizationFx customization = new CustomizationFx(look, appResources);
      return new Tuplet<>(moduleName, customization);
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
    
    return null; // This should not happen, but just in case.
  }
  
}
