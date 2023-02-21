package goedegep.util.emf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import goedegep.util.tree.TreeNode;
import goedegep.util.tree.TreeUtil;

/**
 * This class provides some useful functionality for an EMF package.
 * <ul>
 * <li>Get all subtypes of an EClass within the package</li>
 * <li>Get the EClass for a qualified name</li>
 * <li>Get the EEnum for a qualified name</li>
 * </ul>
 *
 */
public class EmfPackageHelper {
  private static final Logger         LOGGER = Logger.getLogger(EmfPackageHelper.class.getName());
  
  private EPackage ePackage;

  /**
   * The map from qualified name to EClass for all EClasses in the package.
   */
  private Map<String, EClass> eNameToEClasMap = null;

  /**
   * The map from qualified name to EEnum for all ENums in the package.
   */
  private Map<String, EEnum> eNameToEEnumMap = null;
  
  /**
   * A tree representing the class hierarchy of all classes in the package
   */
  TreeNode<EClass> eClassTree = null;
  

  /**
   * Constructor
   * 
   * @param ePackage the <code>EPackage</code> for which this instance will be the helper.
   */
  public EmfPackageHelper(EPackage ePackage) {
    this.ePackage = ePackage;
  }
  
  /**
   * Get all sub types (decendents) of an EClass.
   * <p>
   * The method returns all types which extend (directly or indirectly and within the package) the given type.
   * 
   * @param aClass the EClass for which the sub types are requested.
   * @return All sub types of aClass.
   */
  public List<EClass> getSubTypes(EClass aClass) {
    LOGGER.info("=> aClass=" + aClass.getName());
        
    if (eClassTree == null) {
      buildEClassTree();
    }
    
    TreeNode<EClass> aClassNode = TreeUtil.findNode(eClassTree, aClass);
    if (aClassNode == null) {
      throw new IllegalArgumentException("EClass '" + aClass.getName() + "' is not part of package '" + ePackage.getName() + "'.");
    }
    
    List<EClass> subTypes = null;
    if (aClassNode != null) {
      subTypes = TreeUtil.getValuesOfAllDecendentsOfANode(aClassNode, false);
    }
    
    LOGGER.info("<= " + (subTypes != null ? subTypes.toString() : "null"));
    return subTypes;
  }
  
  /**
   * Get the EClass for a qualified name.
   * <p>
   * There is an EPackage.getEClassifier(String name), but this uses only the simple name. This is not safe.
   * There can be qualifiers with the same simple name. That implementation will return the first one.
   * <p>
   * Its safer to use the qualified name, which is what this method does.
   * 
   * @param qualifiedName the qualified name of the EClassifier to be returned.
   * @return the EClassifier for the given <b>name</b>, or null if such qualifier doesn't exist.
   */
  public EClass getEClass(String qualifiedName) {
    if (eNameToEClasMap == null) {
      LOGGER.info("Initializing eNameToEClassifierMap");
      List<EClassifier> eClassifiers = ePackage.getEClassifiers();
      Map<String, EClass> result = new HashMap<>(eClassifiers.size());
      for (EClassifier eClassifier : eClassifiers) {
        if (eClassifier instanceof EClass eClass) {
          String key = eClass.getInstanceTypeName();
          LOGGER.info("Going to add entry for key=" + key);
          EClass duplicate = result.put(key, eClass);
          if (duplicate != null)
          {
            LOGGER.severe("eClass: " + eClass.toString());
            LOGGER.severe("duplicate: " + duplicate.toString());
            throw new RuntimeException("More than one EClass with the qualified name: " + key);
          }
        }
      }
      
      eNameToEClasMap = result;
    }

    return eNameToEClasMap.get(qualifiedName);
  }
  
  
  /**
   * Get the EEnum for a qualified name.
   * 
   * @param qualifiedName the qualified name of the EEnum to be returned.
   * @return the EEnum for the given <b>qualifiedName</b>, or null if such enum doesn't exist.
   */
  public EEnum getEEnum(String qualifiedName) {
    if (eNameToEEnumMap == null) {
      LOGGER.info("Initializing eNameToEEnumMap");
      List<EClassifier> eClassifiers = ePackage.getEClassifiers();
      Map<String, EEnum> result = new HashMap<>(eClassifiers.size());
      for (EClassifier eClassifier : eClassifiers) {
        if (eClassifier instanceof EEnum eEnum) {
          String key = eEnum.getInstanceTypeName();
          LOGGER.info("Going to add entry for key=" + key);
          EEnum duplicate = result.put(key, eEnum);
          if (duplicate != null)
          {
            LOGGER.severe("eEnum: " + eEnum.toString());
            LOGGER.severe("duplicate: " + duplicate.toString());
            throw new RuntimeException("More than one EEnum with the qualified name: " + key);
          }
        }
      }
      
      eNameToEEnumMap = result;
    }

    return eNameToEEnumMap.get(qualifiedName);
  }
  
  /**
   * Build an 'extends' tree for all classes in the package.
   * <p>
   * Any class that has no super type (for now; within this package) is added as child of the root node.<br/>
   * Any other class is added as child of the node of its super type.
   */
  private void buildEClassTree() {
    LOGGER.fine("=>");

    // Create a list of all classes in the package
    // go through the list, add tree nodes for the classes that have no supertype, or for which the supertype is already in the tree.
    // other nodes are added to a new, todo list.
    // make the todo list the new list to go through
    // repeat until the todo list is empty
    List<EClass> allClassesInPackage = new ArrayList<>();
    eClassTree = new TreeNode<>();
    
    // Build the list of classes.
    LOGGER.fine("Building list of classes:");
    for (EClassifier eClassifier: ePackage.getEClassifiers()) {
      if (eClassifier instanceof EClass) {
        EClass eClass = (EClass) eClassifier;
        allClassesInPackage.add(eClass);
        LOGGER.fine("aClass added: " + eClass.getInstanceTypeName());
      }
    }
    
    List<EClass> toDoList = new ArrayList<>(allClassesInPackage);
    
    LOGGER.fine("Building eClassTree");
    TreeNode<EClass> newNode;
    while (!toDoList.isEmpty()) {
      List<EClass> nextToDoList = new ArrayList<>();
      
      for (EClass eClass: toDoList) {
        LOGGER.fine("In inner loop, eClass: " + eClass.getInstanceTypeName());
        List<EClass> allSuperTypes = eClass.getESuperTypes();
        
        // Ignore superTypes which aren't in this package
        List<EClass> superTypes = new ArrayList<>();
        for (EClass superType: allSuperTypes) {
          if (allClassesInPackage.contains(superType)) {
            superTypes.add(superType);
          }
        }
        
        if (superTypes.isEmpty()) {
          LOGGER.fine("No super type, eClass is added as child of the root.");
          newNode = new TreeNode<>(eClass);
          newNode.setParent(eClassTree);
          eClassTree.getChildren().add(newNode);
        } else {
          LOGGER.fine("eClass has super type(s)");
          // Only add node if all supertypes are already in the tree.
          boolean allSuperTypesInTree = true;
          for (EClass superType: superTypes) {
            TreeNode<EClass> superTypeNode = TreeUtil.findNode(eClassTree, superType);
            if (superTypeNode == null) {
              allSuperTypesInTree = false;
              break;
            }
          }
          
          if (allSuperTypesInTree) {
            for (EClass superType: superTypes) {
              TreeNode<EClass> superTypeNode = TreeUtil.findNode(eClassTree, superType);
              LOGGER.fine("super type node found: " + superTypeNode.getContent().getInstanceTypeName());
              newNode = new TreeNode<>(eClass);
              newNode.setParent(superTypeNode);
              superTypeNode.getChildren().add(newNode);
            }
          }  else {
            LOGGER.fine("not all super type nodes found, eClass is added to nextToDoList");
            nextToDoList.add(eClass);
          }
        }
      }
      
      toDoList = nextToDoList;
    }
    
    LOGGER.info(eClassTree.toStringRecursively(true));
    
    LOGGER.fine("<=");
  }
}
