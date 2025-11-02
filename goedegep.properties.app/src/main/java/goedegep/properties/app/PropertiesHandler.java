package goedegep.properties.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.logging.Logger;

import goedegep.properties.model.PropertiesFactory;
import goedegep.properties.model.PropertiesPackage;
import goedegep.properties.model.Property;
import goedegep.properties.model.PropertyDescriptor;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.properties.model.PropertyGroup;
import goedegep.properties.model.PropertyType;
import goedegep.util.emf.EMFResource;

/**
 * This class handles the properties for an application.
 */
public class PropertiesHandler {
  private static final Logger LOGGER = Logger.getLogger(PropertiesHandler.class.getName());
  
  private static final String PROPERTY_DESCRIPTORS_RESOURCE_FIELDNAME = "propertyDescriptorsResource";
  private static final String CUSTOM_PROPERTIES_FILE_FIELDNAME = "customPropertiesFile";
  
  /**
   * Constructor.
   */
  private PropertiesHandler() {
  }
  
  
  /**
   * Handle the properties for an application.
   * <p>
   * The specified property descriptors file is read (via an EMFResource), which results in a PropertyDescriptorsGroup.
   * This PropertyDescriptorsGroup provides, among others, the name of the registry class and information per property.<br/>
   * The registry class is filled with the initial values of the properties (if applicable).<br/>
   * The EMFResource is stored in the field PROPERTY_DESCRIPTORS_RESOURCE_FIELDNAME of the registry.<br/>
   * If the registry field CUSTOM_PROPERTIES_FILE_FIELDNAME is set, a custom properties file with that name is read (again via an EMFResource).<br/>
   * The registry class is updated with the values from this properties file.
   * <p>
   * If the program is running in Eclipse, the actual path is <code>propertyDescriptorsFileName</code> in the <code>projectPath</code> directory.
   * Else it is simply <code>propertyDescriptorsFileName</code> (as it is in the current directory).
   * 
   * @param propertyDescriptorsFileURI the URL of the file with property descriptors.
   * @param urlForFileNameFunction a function for providing a URL for a file name. Used for handling custom property files.
   */
  public static void handleProperties(URI propertyDescriptorsFileURI) throws IOException {
    
    EMFResource<PropertyDescriptorGroup> emfResource = new EMFResource<>(
        PropertiesPackage.eINSTANCE,
        () -> PropertiesFactory.eINSTANCE.createPropertyDescriptorGroup(), ".xmi");

    PropertyDescriptorGroup propertyDescriptorGroup = emfResource.load(propertyDescriptorsFileURI);
    Class<?> registryClass = fillRegistryFromPropertiesDescriptorGroup(propertyDescriptorGroup);

    try {
      Field propertyDescriptorsResourceField = registryClass.getField(PROPERTY_DESCRIPTORS_RESOURCE_FIELDNAME);
      propertyDescriptorsResourceField.set(null, emfResource);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException("Registry \'" + registryClass.getName() +
          "\' is missing the mandatory field \'" + PROPERTY_DESCRIPTORS_RESOURCE_FIELDNAME +
          "\'. This problem was detected while handling property descriptor file \'" + propertyDescriptorsFileURI.toString() + "\'");
    } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
      throw new RuntimeException("Unexpected exception in handleProperties.");
    }

    try {
      Field customPropertiesFileField = registryClass.getField(CUSTOM_PROPERTIES_FILE_FIELDNAME);
      String customPropertiesFileName = (String) customPropertiesFileField.get(null);
      if (customPropertiesFileName != null) {
        EMFResource<PropertyGroup> propertiesResource = new EMFResource<PropertyGroup>(
            PropertiesPackage.eINSTANCE,
            () -> PropertiesFactory.eINSTANCE.createPropertyGroup(), ".xmi");

        URL url = null;
        String userHomeDir = System.getProperty("user.home");
        URI uri = Path.of(userHomeDir, "MyWorld", customPropertiesFileName).toUri();
        try {
          url = uri.toURL();
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
        if (url != null) {
          String pathString = url.getPath().substring(1);
          Path path = Path.of(pathString);
          if (Files.exists(path)) {       
            try {
              PropertyGroup propertyGroup = propertiesResource.load(uri);
              updateRegistryGroup(registryClass, propertyGroup, propertyDescriptorGroup);
              //            LOGGER.info(ClassUtil.staticFieldsToString(registryClass));
            } catch (FileNotFoundException e) {
              e.printStackTrace();
            }
          }
        }
      }
    } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      LOGGER.severe("NoSuchFieldException: " + e);
      LOGGER.severe("NoSuchFieldException; field " + CUSTOM_PROPERTIES_FILE_FIELDNAME + " doesn't exist in registry class " + registryClass.getName());
      e.printStackTrace();
    }

    LOGGER.info("<=");
  }
  
  
//  /**
//   * Create the path name for a resource file.
//   * <p>
//   * If the program is running in Eclipse, the resource path is the <code>fileName</code> in the <code>projectPath</code> directory.
//   * Else it is simply <code>fileName</code> (as it is in the current directory).
//   * 
//   * @param runningInEclipse indicates whether the program is running in Eclipse or not.
//   * @param projectPath path to the project which contains the resource.
//   * @param fileName filename of the resource.
//   * @return a path to the resource
//   */
//  private static String createResourcePath(boolean runningInEclipse, String projectPath, String fileName, boolean isUserFile) {
//    if (runningInEclipse) {
//      if (projectPath != null) {
//        File file = new File(projectPath, fileName);
//        LOGGER.info("Resource path = " + file.getAbsolutePath());
//        return file.getAbsolutePath();
//      } else {
//        return fileName;
//      }
//    } else {
//      if (isUserFile) {
//        LOGGER.info("Resource path = " + fileName);
//        return "..\\" + fileName;
//      } else {
//        return fileName;
//      }
//    }
//  }
  
  /**
   * Fill a Registry with values of a {@link PropertiesDescriptorGroup}.
   * <p/>
   * The Registry class is identified by packageName and the registryClassName of the PropertiesDescriptorGroup.
   * This implies that if one of these fields is not set, no action takes place.
   * 
   * @param propertyDescriptorGroup the PropertyDescriptorGroup which's values are to be stored in a Registry.
   */
  private static Class<?> fillRegistryFromPropertiesDescriptorGroup(PropertyDescriptorGroup propertyDescriptorGroup) {
    LOGGER.info("=> propertyDescriptorGroup.name=\"" + propertyDescriptorGroup.getName() + "\"");

    // Only try to fill a registry if both packageName and registryClassName are set.
    if (!propertyDescriptorGroup.isSetPackageName()  ||  !propertyDescriptorGroup.isSetRegistryClassName()) {
      LOGGER.severe("No registry entries updated, because packageName and/or registryClassName is not set ");
      return null;
    }

    String qualifiedRegistryClassName = propertyDescriptorGroup.getPackageName() + "." + propertyDescriptorGroup.getRegistryClassName();
    LOGGER.info("qualifiedRegistryClassName: " + qualifiedRegistryClassName);
    String registryName = null;
    String propertyName = "<none>";
    Class<?> registryClass = null;
//    try {
//      registryClass = Class.forName(qualifiedRegistryClassName);
//      
//
//      for (PropertyDescriptor propertyDescriptor: propertyDescriptorGroup.getPropertyDescriptors()) {
//        propertyName = propertyDescriptor.getName();
//        
//        if (propertyDescriptor.isSetInstallInitialValue()  &&  propertyDescriptor.isInstallInitialValue()  &&
//            propertyDescriptor.isSetInitialValue()) {
//          registryName = propertyDescriptor.getRegistryName();
//          if (registryName == null) {
//            registryName = propertyName;
//          }
//          LOGGER.info("Handling field: " + registryName);
////          for (Field field: registryClass.getFields()) {
////            LOGGER.severe("Field: " + field.getName());
////            if (field.getName().equals(registryName)) {
////              LOGGER.severe("Field found");
////              break;
////            }
////          }
//
//          Field field = registryClass.getField(registryName);
//          if (propertyDescriptor.getType() == PropertyType.BOOLEAN) {
//            Boolean booleanValue = Boolean.parseBoolean(propertyDescriptor.getInitialValue());
//            field.set(null, booleanValue);
//          } else {
//            field.set(null, propertyDescriptor.getInitialValue());
//          }
//          LOGGER.info("Registry entry set for property " + propertyName +
//              ". Registry entry name = " + registryName + ", value = " + propertyDescriptor.getInitialValue());
//        } else {
//          LOGGER.info("No initial value specified for property " + propertyName + ", no value written to the registry.");
//        }
//      }
//    } catch (ClassNotFoundException e1) {
//      e1.printStackTrace();
//    } catch (SecurityException e) {
//      e.printStackTrace();
//    } catch (NoSuchFieldException e) {
//      LOGGER.severe("No field found in registry for property: \"" + propertyName + "\", registry class name is: " + qualifiedRegistryClassName);    
//      e.printStackTrace();
//    } catch (IllegalArgumentException e) {
//      e.printStackTrace();
//    } catch (IllegalAccessException e) {
//      e.printStackTrace();
//    }
    
    return registryClass;
  }

  private static void updateRegistryGroup(Class<?> registryClass, PropertyGroup propertyGroup, PropertyDescriptorGroup propertyDescriptorGroup) {
    LOGGER.info("updateRegistryGroup: handling " + propertyGroup.getName() + ", group = " + propertyDescriptorGroup.getName());

    //    for (Property property: propertyGroup.getProperties()) {
    //      // Get the property descriptor for this property.
    //      if (path.length() == 0) {
    //        path = property.getName();
    //      } else {
    //        path = path + "." + property.getName();
    //      }
    //      
    //    }

    // Only try to fill a registry if both packageName and registryClassName are set.
    if (!propertyDescriptorGroup.isSetPackageName()  ||  !propertyDescriptorGroup.isSetRegistryClassName()) {
      LOGGER.severe("fillRegistryGroup: no registry entries updated, because packageName and/or registryClassName is not set ");
      return;
    }

    String registryName = null;

    try {
      for (Property property: propertyGroup.getProperties()) {
        PropertyDescriptor propertyDescriptor = propertyDescriptorGroup.getPropertyDescriptor(property.getName());

        // Throw an exception if the property doesn't exist, or if it is not user settable.
        if (propertyDescriptor == null) {
          throw new RuntimeException("Onbekende eigenschap: " + property.getName());
        }
        if (!propertyDescriptor.isUserSettable()) {
          throw new RuntimeException("De eigenschap " + property.getName() + " kan niet gewijzigd worden.");
        }

        String propertyName = propertyDescriptor.getName();

        registryName = propertyDescriptor.getRegistryName();
        if (registryName == null) {
          registryName = propertyName;
        }

        Field field = registryClass.getField(registryName);
        field.set(null, property.getValue());
        LOGGER.info("Registry entry set for property " + propertyName +
            ". Registry entry name = " + registryName + ", value = " + property.getValue());
      }
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      LOGGER.severe("No field found for registry entry " + registryName);    
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
