package goedegep.util.emf;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import goedegep.util.PgUtilities;

/**
 * This is a utility class, with EMF utility methods.
 *
 */
public final class EmfUtil {
  private final static Logger LOGGER = Logger.getLogger(EmfUtil.class.getName());
  private static final String NEWLINE = System.getProperty("line.separator");
  
  /**
   * List of names for Notification event types as defined in {@link org.eclipse.emf.common.notify.Notification}.
   */
  private final static String[] NOTIFICATION_EVENT_TYPES = {
      "CREATE", "SET", "UNSET", "ADD", "REMOVE", "ADD_MANY", "REMOVE_MANY", "MOVE", "REMOVING_ADAPTER", "RESOLVE", "EVENT_TYPE_COUNT"
  };
  
  /**
   * As this is a utility class, a private constructor is defined, so it cannot be instantiated.
   */
  private EmfUtil() {
  }
  
  /**
   * Get the EMF super type of an <code>EClass</code>.
   * 
   * @param eClass the <code>EClass</code> of which the super type is to be obtained.
   * @return the super type of <code>eClass</code>, or null if there is no super type.
   */
  public static EClass getSuperType(EClass eClass) {
    LOGGER.info("=> eClass=" + eClass.getInstanceTypeName());
    
    EClass superType = null;
    List<EClass> superTypes = eClass.getESuperTypes();
    
    if (superTypes.size() > 1) {
      throw new RuntimeException("More than one super type found");
    }
    
    if (superTypes.size() > 0) {
      superType = superTypes.get(0);
      LOGGER.info("superType=" + eClass.getInstanceClassName() + ",  " + eClass.getInstanceClass().getName());
    } else {
      LOGGER.info("no superType");
    }
    
    LOGGER.info("<= " + (superType != null ? superType.getInstanceTypeName() : "null"));
    return superType;
  }
  
  /**
   * Check whether one type is an 'instanceof' of another type.
   * <p>
   * Here the types are identified by their EClass.
   * 
   * @param subType the sub type
   * @param superType the potential super type of subType
   * @return true if subType is an instance of superType, false otherwise.
   */
  public static boolean isInstanceof(EClass subType, EClass superType) {
    if (subType.equals(superType)) {
      return true;
    }
    
    List<EClass> superTypes = subType.getESuperTypes();
    
    for (EClass eClass: superTypes) {
      LOGGER.info(eClass.getName());
    }
    
    if (superTypes.contains(superType)) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Get the name of the implementation class for an interface class.
   * <p>
   * With EMF there is a fixed relation between an interface name and the implementing class name.
   * The implementing class name is in a sub-package with the name 'impl' and the class name is the name of the
   * interface, extended with 'Impl'.<br/>
   * Interface is: mypackage.subpackage.SomeType<br/>
   * Implementation is: mypackage.subpackage.impl.SomeTypeImpl
   * 
   * @param interfaceClassName the interface class name for which the implementation class name is requested.
   * @return the implementation class name for the interfaceClassName.
   */
  public static String getImplementationClassName(String interfaceClassName) {
    LOGGER.severe("=> interfaceClassName=" + interfaceClassName);
    
    int lastDotIndex = interfaceClassName.lastIndexOf(".");
    String interfacePackage = interfaceClassName.substring(0, lastDotIndex);
    LOGGER.severe("interfacePackage=" + interfacePackage);
    String interfaceName = interfaceClassName.substring(lastDotIndex + 1, interfaceClassName.length());
    LOGGER.severe("interfaceName=" + interfaceName);
    
    String implementationClassName = interfacePackage + ".impl." + interfaceName + "Impl";
    
    LOGGER.severe("<= implementationClassName=" + implementationClassName);
    return implementationClassName;
  }
  
  /**
   * Find all objects of a specific <code>EClass</code> that are dependents of an <code>EObject</code>.
   * 
   * @param root the <code>EObject</code> within which the objects have to be found.
   * @param eClass the <code>EClass</code> of the objects to be found.
   * @return all objects within <code>eClass</code> that are of type <code>eClass</code>. The returned list may be empty, but it is never null.
   */
  public static List<EObject> findObjectsOfType(EObject root, EClass eClass) {
    List<EObject> objects = new ArrayList<>();
    
    TreeIterator<EObject> iterator = root.eAllContents();
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      EClass eObjectEClass = eObject.eClass();
      
      if (eObjectEClass == eClass) {
        LOGGER.info("Match on: " + eObject.toString());
        objects.add(eObject);
      }
    }
    
    return objects;
  }
  
  /**
   * Get the root object of an <code>EObject</code>.
   * 
   * @param eObject the <code>EObject</code> of which the root is to be returned.
   * @return the root object of <code>eObject</code>, or null if <code>eObject</code> is a root object.
   */
  public static EObject getRoot(EObject eObject) {
    EObject root = eObject;
    
    while (root.eContainer() != null) {
      root = root.eContainer();      
    }
    
    return root;
  }

  /**
   * Check whether a notification is from a specific EObject, or any of its children.
   * 
   * @param notification the {@code Notification} to be checked.
   * @param parent the EObject the check
   * @return true if {@code parent} or any of its children is the source of the {@code notification}, false otherwise.
   */
  public static boolean isNotificationFromEObjectOrAnyChild(Notification notification, EObject parent) {
    LOGGER.info("=> notification=" + printNotification(notification));
    Object notifier = notification.getNotifier();
    EObject notifierEObject = (EObject) notifier;
    boolean result = false;
    
    TreeIterator<EObject> iterator = parent.eAllContents();
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      LOGGER.info("Handling: " + eObject.toString());
      if (eObject.equals(notifierEObject)) {
        result = true;
        break;
      }
    }
        
    LOGGER.info("<= " + result);
    return result;
  }

  /**
   * Check whether a notification is from a specific feature of a specific class, or any of its children.
   * 
   * @param notification the {@code Notification} to be checked.
   * @param notifyingClass the {@code EClass} which is expected.
   * @param eStructuralFeature the {@code EStructuralFeature} which is expected.
   * @return true if the {@code eStructuralFeature} in class {@code notifyingClass} or any of its children is the source of the {@code notification}, false otherwise.
   */
  public static boolean isNotificationFromClassFeatureOrAnyChild(Notification notification, EClass notifyingClass, EStructuralFeature eStructuralFeature, EObject parent) {
    LOGGER.info("=> notification=" + printNotification(notification) + ", notifyingClass=" + notifyingClass.getName() + ", eStructuralFeature=" + eStructuralFeature.getName());
    Object notifier = notification.getNotifier();
    
    boolean result = isNotificationFromClassFeature(notification, notifyingClass, eStructuralFeature);
    
    if (!result) {
      LOGGER.info("notifier: " + notifier);
      
      // Step one: if it is a list, check whether it's one of the items in the list
      Object object = parent.eGet(eStructuralFeature);
      if (object instanceof EList) {
        EList<?> items = (EList<?>) object;
        LOGGER.info("items: " + items);
        if (items.contains(notifier)) {
          result = true;
        }
      } else {
        throw new RuntimeException("Type not yet supported: " + object.getClass().getName());
      }
    }
    
    LOGGER.info("<= " + result);
    return result;
  }

  /**
   * Check whether a notification is from a specific feature of a specific class.
   * 
   * @param notification the {@code Notification} to be checked.
   * @param notifyingClass the {@code EClass} which is expected.
   * @param eStructuralFeature the {@code EStructuralFeature} which is expected.
   * @return true if the {@code eStructuralFeature} in class {@code notifyingClass} is the source of the {@code notification}, false otherwise.
   */
  public static boolean isNotificationFromClassFeature(Notification notification, EClass notifyingClass, EStructuralFeature eStructuralFeature) {
    Object notifier = notification.getNotifier();
    EObject eNotifier = (EObject) notifier;
    EClass notifierEClass = eNotifier.eClass();
    
    if (notifierEClass.equals(notifyingClass)) {
      Object feature = notification.getFeature();
      if (feature.equals(eStructuralFeature)) {
        LOGGER.info("FOUND");
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * Provide a textual representation of an org.eclipse.emf.common.notify.Notification.
   * <p>
   * The notifier value is printed.
   * 
   * @param notification the <code>Notification</code> to print.
   * @return a textual representation of the <code>notification</code>
   */
  public static String printNotification(Notification notification) {
    return printNotification(notification, true);
  }
  
  /**
   * Provide a textual representation of an org.eclipse.emf.common.notify.Notification.
   * 
   * @param notification the <code>Notification</code> to print.
   * @param printNotifierValue only if this is set to true, the value of the notifier (which may be a large text) is printed.
   * @return a textual representation of the <code>notification</code>
   */
  public static String printNotification(Notification notification, boolean printNotifierValue) {
    StringBuilder buf = new StringBuilder();
    
    buf.append(NEWLINE);
    
    buf.append("    Event type: ");
    buf.append(NOTIFICATION_EVENT_TYPES[notification.getEventType()]).append(NEWLINE);
    
    Object notifier = notification.getNotifier();
    buf.append("    Notifier: type=").append(notifier.getClass().getName());
    if (printNotifierValue) {
      buf.append(", value=").append(notifier.toString());
    }
    buf.append(NEWLINE);
    
    String featureType = "Unknown";
    Object feature = notification.getFeature();
    String featureName = "<unknown>";
    if (feature instanceof EReference eReference) {
      featureType = "EReference";
      featureName = eReference.getName();
    } else if (feature instanceof EAttribute eAttribute) {
      featureType = "EAttribute";
      featureName = eAttribute.getName();
    } else if (feature == null) {
      featureType = "(null)";
      featureName = "(null)";
    } else {
      buf.append("TODO");
    }
    buf.append("    Feature type: ").append(featureType).append(", Feature name: ").append(featureName).append(NEWLINE);

    
    EObject eObject = null;
    if (notifier instanceof EClass) {
      throw new RuntimeException("notifier of type EClass is not supported.");
    } else if (notifier instanceof EObject) {
      eObject = (EObject) notifier;
    }
    
    String valueType = "Unknown";
    switch (featureType) {
    case "EReference":
      EReference eReference = (EReference) feature;
      EClass referenceType = eReference.getEReferenceType();
      String referenceTypeName = referenceType.getName();
      if (eReference.isMany()) {
        referenceTypeName = "List<" + referenceTypeName + ">";
      }
      buf.append("    Referred value type: ").append(referenceTypeName).append(NEWLINE);
      Object object1 = eObject.eGet(eReference);
      
      if (!eReference.isMany()) {
        buf.append("    Referred object: ").append(object1 != null ? object1.toString() : "(null)").append(NEWLINE);
      }
      valueType = "Object";
      break;
      
    case "EAttribute":
      EAttribute eAttribute = (EAttribute) feature;
      EClassifier attributeType = eAttribute.getEType();
      buf.append("    Attribute value type: " + attributeType.getName()).append(NEWLINE);
      Object object2 = eObject.eGet(eAttribute);
      buf.append("    Attribute value: ").append(object2 != null ? object2.toString() : "(null)").append(NEWLINE);
      switch (attributeType.getName()) {
      case "EString":
        valueType = "String";
        break;
        
      }
      break;
      
    }
    
    buf.append("    FeatureID: ");
    buf.append(notification.getFeatureID(null)).append(NEWLINE);
    
    switch (valueType) {
    case "Boolean":
      buf.append("    OldBooleanValue: ");
      buf.append(notification.getOldBooleanValue()).append(NEWLINE);
      buf.append("    NewBooleanValue: ");
      buf.append(notification.getNewBooleanValue()).append(NEWLINE);
      break;
      
    case "Byte":
      buf.append("    OldByteValue: ");
      buf.append(notification.getOldByteValue()).append(NEWLINE);
      buf.append("    NewByteValue: ");
      buf.append(notification.getNewByteValue()).append(NEWLINE);
      break;
      
    case "Char":
      buf.append("    OldCharValue: ");
      buf.append(notification.getOldCharValue()).append(NEWLINE);
      buf.append("    NewCharValue: ");
      buf.append(notification.getNewCharValue()).append(NEWLINE);
      break;
      
    case "Double":
      buf.append("    OldDoubleValue: ");
      buf.append(notification.getOldDoubleValue()).append(NEWLINE);
      buf.append("    NewDoubleValue: ");
      buf.append(notification.getNewDoubleValue()).append(NEWLINE);
      break;
      
    case "Float":
      buf.append("    OldFloatValue: ");
      buf.append(notification.getOldFloatValue()).append(NEWLINE);
      buf.append("    NewFloatValue: ");
      buf.append(notification.getNewFloatValue()).append(NEWLINE);
      break;
      
    case "Int":
      buf.append("    OldIntValue: ");
      buf.append(notification.getOldIntValue()).append(NEWLINE);
      buf.append("    NewIntValue: ");
      buf.append(notification.getNewIntValue()).append(NEWLINE);
      break;
      
    case "Long":
      buf.append("    OldLongValue: ");
      buf.append(notification.getOldLongValue()).append(NEWLINE);
      buf.append("    NewLongValue: ");
      buf.append(notification.getNewLongValue()).append(NEWLINE);
      break;
      
    case "Short":
      buf.append("    OldShortValue: ");
      buf.append(notification.getOldShortValue()).append(NEWLINE);
      buf.append("    NewShortValue: ");
      buf.append(notification.getNewShortValue()).append(NEWLINE);
      break;
      
    case "String":
      buf.append("    OldStringValue: ");
      buf.append(notification.getOldStringValue()).append(NEWLINE);
      buf.append("    NewStringValue: ");
      buf.append(notification.getNewStringValue()).append(NEWLINE);
      break;
      
    case "Object":
      Object value = notification.getOldValue();
      buf.append("    Old(Object)Value: type=").append(value != null ? value.getClass().getName() : "-").append(", value=").append(valueToString(value)).append(NEWLINE);
      value = notification.getNewValue();
      buf.append("    New(Object)Value: type=").append(value != null ? value.getClass().getName() : "-").append(", value=").append(value != null ? valueToString(value) : "null").append(NEWLINE);
      break;
      
    default:
      buf.append("    UNKNOWN VALUE TYPE").append(NEWLINE);
      break;
    }
    
    buf.append("    Position: ");
    buf.append(notification.getPosition()).append(NEWLINE);
    
    buf.append("    isReset: ");
    buf.append(notification.isReset()).append(NEWLINE);
    
    buf.append("    isTouch: ");
    buf.append(notification.isTouch()).append(NEWLINE);
    
    buf.append("    wasSet: ");
    buf.append(notification.wasSet()).append(NEWLINE);
    
    return buf.toString();
  }
  
  /**
   * Provide a String for an object value.
   * 
   * @param value the value to be represented as a String.
   * @return a String for {@code value}.
   */
  public static String valueToString(Object value) {
    if (value == null) {
      return "<null>";
    }
    
    StringBuilder buf = new StringBuilder();
    if (value instanceof List<?> list) {
      buf.append(NEWLINE);
      for (Object listItem: list) {
        buf.append(valueToString(listItem)).append(NEWLINE);
      }
    } else if (value instanceof EObject eObject) {
      buf.append(eObjectToString(eObject));
    } else if (value instanceof int[] intArray) {
      boolean first = true;
      for (int i: intArray) {
        if (first) {
          first = false;
        } else {
          buf.append(", ");
        }
        buf.append(i);
      }
    } else {
      buf.append("value of unknown type: ").append(value.getClass().getName());
    }
    String result = buf.toString();
    if (result.length() > 50) {
      result = result.substring(0, 50) + "....." + NEWLINE;
    }
    return result;
  }
  
  /**
   * Provide a String for an EObject.
   * 
   * @param eObject the value to be represented as a String.
   * @return a String for {@code eObject}.
   */
  public static String eObjectToString(EObject eObject) {
    StringBuilder buf = new StringBuilder();
    
    buf.append("    Features: ").append(NEWLINE);
    for (EStructuralFeature feature: eObject.eClass().getEAllStructuralFeatures()) {
      buf.append("      ").append(feature.getName()).append(" ").append(eObject.eGet(feature)).append(NEWLINE);
    }
    
    return buf.toString();
  }
  
  /**
   * Check whether all items that are referred to are contained in a hierarchy of EObjects.
   * 
   * @param eObject the root of the structure to check
   */
  public static void checkCompleteContainment(EObject rootEObject) {
    TreeIterator<EObject> iterator = rootEObject.eAllContents();
    while (iterator.hasNext()) {
      EObject eObject = iterator.next();
      if (!isContainedUnderEObject(eObject, rootEObject)) {
        LOGGER.severe("EObject not contained: " + eObject.toString());
      }
    }
  }

  /**
   * Check whether an EObject is contained by a rootEObject, or by any child of the rootEObject.
   * 
   * @param eObject the EObject to check
   * @param rootEObject the root EObject that shall contain the eObject.
   * @return true if eObject is contained by rootEObject, false otherwise.
   */
  public static boolean isContainedUnderEObject(EObject eObject, EObject rootEObject) {
    String found = eObject.getClass().getName();
    EObject container = eObject.eContainer();
    found = container.getClass().getName() + "." + found;
    while (container != null) {
      if (container == rootEObject) {
        return true;
      }
      container = container.eContainer();
      found = container.getClass().getName() + "." + found;
    }
    return false;
  }
  
  /**
   * Set a feature value, but only if the new value differs from the current value.
   * 
   * @param <T> the type of the value
   * @param eObject the {@code EObject} of which the feature is to be set.
   * @param feature the {@code EStructuralFeature} to set
   * @param value the value to set.
   */
  public static <T> void setFeatureValue(EObject eObject, EStructuralFeature feature, T value) {
    if (!PgUtilities.equals(value, eObject.eGet(feature))) {
      eObject.eSet(feature, value);
    }    
  }
}
