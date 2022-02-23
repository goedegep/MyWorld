package goedegep.properties.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.logging.Logger;

import goedegep.appgen.EMFResource;
import goedegep.properties.model.PropertiesFactory;
import goedegep.properties.model.PropertiesPackage;
import goedegep.properties.model.Property;
import goedegep.properties.model.PropertyDescriptor;
import goedegep.properties.model.PropertyDescriptorGroup;
import goedegep.properties.model.PropertyGroup;

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
   * @param runningInEclipse shall be set to true if running within Eclipse.
   * @param projectPath a path to the Eclipse project in which the file with the name <b>propertyDescriptorsFileName</b> is located.
   * @param propertyDescriptorsFileName the name of the file with property descriptors.
   */
  public static void handleProperties(boolean runningInEclipse, String projectPath, String propertyDescriptorsFileName) {
    LOGGER.info("=> runningInEclipse=" + runningInEclipse + ", projectPath=" + projectPath + ", propertyDescriptorsFileName=" + propertyDescriptorsFileName);
    
    String resourceFileName;
    
    EMFResource<PropertyDescriptorGroup> emfResource = new EMFResource<>(
        PropertiesPackage.eINSTANCE,
        () -> PropertiesFactory.eINSTANCE.createPropertyDescriptorGroup());
    try {
      resourceFileName = createResourcePath(runningInEclipse, projectPath, propertyDescriptorsFileName);
      PropertyDescriptorGroup propertyDescriptorGroup = emfResource.load(resourceFileName);
      LOGGER.info("propertyDescriptorGroup: " + propertyDescriptorGroup.toString());
      Class<?> registryClass = fillRegistryGroup(propertyDescriptorGroup);
//      LOGGER.info(ClassUtil.staticFieldsToString(registryClass));
      
      try {
        Field propertyDescriptorsResourceField = registryClass.getField(PROPERTY_DESCRIPTORS_RESOURCE_FIELDNAME);
        propertyDescriptorsResourceField.set(null, emfResource);
      } catch (NoSuchFieldException e) {
        throw new RuntimeException("Registry \'" + registryClass.getName() +
            "\' is missing the mandatory field \'" + PROPERTY_DESCRIPTORS_RESOURCE_FIELDNAME +
            "\'. This problem was detected while handling property descriptor file \'" + resourceFileName + "\'");
      } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      }
//      LOGGER.info(ClassUtil.staticFieldsToString(registryClass));
      
      try {
        Field customPropertiesFileField = registryClass.getField(CUSTOM_PROPERTIES_FILE_FIELDNAME);
        String customPropertiesFileName = (String) customPropertiesFileField.get(null);
        if (customPropertiesFileName != null) {
          EMFResource<PropertyGroup> propertiesResource = new EMFResource<PropertyGroup>(
              PropertiesPackage.eINSTANCE,
              () -> PropertiesFactory.eINSTANCE.createPropertyGroup());
          resourceFileName = createResourcePath(runningInEclipse, projectPath, customPropertiesFileName);
          customPropertiesFileField.set(customPropertiesFileField, resourceFileName);
          try {
            PropertyGroup propertyGroup = propertiesResource.load(resourceFileName);
            updateRegistryGroup(registryClass, propertyGroup, propertyDescriptorGroup);
//            LOGGER.info(ClassUtil.staticFieldsToString(registryClass));
          } catch (FileNotFoundException e) {
            // It's not mandatory to have a custom settings file.
          }
        }
      } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      } catch (NoSuchFieldException e) {
        LOGGER.severe("NoSuchFieldException: " + e);
        LOGGER.severe("NoSuchFieldException; field " + CUSTOM_PROPERTIES_FILE_FIELDNAME + " doesn't exist in registry class " + registryClass.getName());
        e.printStackTrace();
      }
      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(-1);
    }
    
    LOGGER.info("<=");
  }
  
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
  private static String createResourcePath(boolean runningInEclipse, String projectPath, String fileName) {
    if (runningInEclipse  &&  (projectPath != null)) {
      File file = new File(projectPath, fileName);
      LOGGER.info("Resource path = " + file.getAbsolutePath());
      return file.getAbsolutePath();
    } else {
      LOGGER.info("Resource path = " + fileName);
      return fileName;
    }
  }
  
  /**
   * Fill a Registry with values of a {@link PropertiesDescriptorGroup}.
   * <p/>
   * The Registry class is identified by packageName and the registryClassName of the PropertiesDescriptorGroup.
   * This implies that if one of these fields is not set, no action takes place.
   * 
   * @param propertyDescriptorGroup the PropertyDescriptorGroup which's values are to be stored in a Registry.
   */
  private static Class<?> fillRegistryGroup(PropertyDescriptorGroup propertyDescriptorGroup) {
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
    try {
      registryClass = Class.forName(qualifiedRegistryClassName);
      

      for (PropertyDescriptor propertyDescriptor: propertyDescriptorGroup.getPropertyDescriptors()) {
        propertyName = propertyDescriptor.getName();
        
        if (propertyDescriptor.isSetInstallInitialValue()  &&  propertyDescriptor.isInstallInitialValue()  &&
            propertyDescriptor.isSetInitialValue()) {
          registryName = propertyDescriptor.getRegistryName();
          if (registryName == null) {
            registryName = propertyName;
          }
          LOGGER.info("Handling field: " + registryName);
//          for (Field field: registryClass.getFields()) {
//            LOGGER.severe("Field: " + field.getName());
//            if (field.getName().equals(registryName)) {
//              LOGGER.severe("Field found");
//              break;
//            }
//          }

          Field field = registryClass.getField(registryName);
          field.set(null, propertyDescriptor.getInitialValue());
          LOGGER.info("Registry entry set for property " + propertyName +
              ". Registry entry name = " + registryName + ", value = " + propertyDescriptor.getInitialValue());
        } else {
          LOGGER.info("No initial value specified for property " + propertyName + ", no value written to the registry.");
        }
      }
    } catch (ClassNotFoundException e1) {
      e1.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      LOGGER.severe("No field found in registry for property: \"" + propertyName + "\", registry class name is: " + qualifiedRegistryClassName);    
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    
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
