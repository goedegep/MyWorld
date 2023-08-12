package goedegep.util.emf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;

import goedegep.util.emf.samplemodel.DescribedItem;
import goedegep.util.emf.samplemodel.DirectorySpecification;
import goedegep.util.emf.samplemodel.DiscStructureSpecification;
import goedegep.util.emf.samplemodel.PCToolsFactory;
import goedegep.util.logging.MyLoggingFormatter;
import goedegep.util.xtree.XNodeDataType;
import goedegep.util.xtree.XTreeNodeVisitResult;
import goedegep.util.xtree.XTreeNodeVisitor;
import goedegep.util.xtree.impl.binary.BinarySerializedXTree;
import goedegep.util.xtree.mutable.MutableXTree;
import goedegep.util.xtree.mutable.MutableXTreeFactory;
import goedegep.util.xtree.mutable.MutableXTreeNode;
import goedegep.util.xtree.serialized.SerializedXTree;

public class EObjectPath {
  private final static Logger LOGGER = Logger.getLogger(EObjectPath.class.getName());
  
  private SerializedXTree pathXTree;
  
  /**
   * Create a path to any EObject in an EObject hierarchy.
   * 
   * @param eObject the EObject for which is path is to be constructed.
   * 
   * @return an EObjectPath from the root of the EObject hierarchy to the specified eObject.
   */
  public EObjectPath(EObject eObject) {
    LOGGER.info("=> eObject=" + eObject.toString());
    
    MutableXTree xtree = MutableXTreeFactory.createMutableXTree();
    MutableXTreeNode node = null;
    
    EObject currentEObject = eObject;
    
    while (currentEObject.eContainer() != null) {
      EObject container = currentEObject.eContainer();      
      EReference reference = currentEObject.eContainmentFeature();
      
      node = xtree.insertStringSibling(null, reference.getName());
      
      if (reference.isMany()) {
        @SuppressWarnings("unchecked")
        EList<EObject> list = (EList<EObject>) container.eGet(reference);
        int index = list.indexOf(currentEObject);
        node.addIntegerChild(index);
      }
      
      currentEObject = container;
    }
            
    pathXTree = new BinarySerializedXTree(xtree);
    LOGGER.fine("EOBJECT PATH TREE: " + xtree.toString());
    LOGGER.fine("EOBJECT BINARY SERIALIZED PATH TREE: " + pathXTree.toString());
    
    LOGGER.info("<=");
  }
  
  /**
   * Get the EObject in an EObject hierarchy, for this EObjectPath.
   * 
   * @param rootEObject the root of an EObject hierarchy.
   * @return the EObject within the EObject hierarchy which is identified by this EObjectPath.
   */
  public EObject resolveToEObject(final EObject rootEObject) {
    LOGGER.info("=>");
    LOGGER.info("pathXTree=" + pathXTree.toString());
    
    EObjectResolverVisitor eObjectResolverVisitor = new EObjectResolverVisitor(rootEObject);
    pathXTree.traverse(eObjectResolverVisitor);
    
    LOGGER.info("<=");
    return eObjectResolverVisitor.getEObject();
  }
  
  /**
   * Get the ERerefence to the EObject in an EObject hierarchy, for this EObjectPath.
   * 
   * @param rootEObject the root of an EObject hierarchy.
   * @return the EReference to the EObject within the EObject hierarchy which is identified by this EObjectPath.
   */
  public EReference resolveToEObjectReference(final EObject rootEObject) {
    LOGGER.info("=>");
    LOGGER.info("pathXTree=" + pathXTree.toString());
    
    EObjectResolverVisitor eObjectResolverVisitor = new EObjectResolverVisitor(rootEObject);
    pathXTree.traverse(eObjectResolverVisitor);
    
    LOGGER.info("<=");
    return eObjectResolverVisitor.getEReference();
  }
  
  /**
   * Get the ERerefence to the EObject in an EObject hierarchy, for this EObjectPath.
   * 
   * @param rootEObject the root of an EObject hierarchy.
   * @return the EReference to the EObject within the EObject hierarchy which is identified by this EObjectPath.
   */
  public Integer resolveToEObjectReferenceIndex(final EObject rootEObject) {
    LOGGER.info("=>");
    LOGGER.info("pathXTree=" + pathXTree.toString());
    
    EObjectResolverVisitor eObjectResolverVisitor = new EObjectResolverVisitor(rootEObject);
    pathXTree.traverse(eObjectResolverVisitor);
    
    LOGGER.info("<=");
    return eObjectResolverVisitor.getIndex();
  }
  
  /**
   * Get the parent EObject of the EObject in an EObject hierarchy, for this EObjectPath.
   * 
   * @param rootEObject the root of an EObject hierarchy.
   * @return the EReference to the EObject within the EObject hierarchy which is identified by this EObjectPath.
   */
  public EObject resolveToEObjectParent(final EObject rootEObject) {
    LOGGER.info("=>");
    LOGGER.info("pathXTree=" + pathXTree.toString());
    
    EObjectResolverVisitor eObjectResolverVisitor = new EObjectResolverVisitor(rootEObject);
    pathXTree.traverse(eObjectResolverVisitor);
    
    LOGGER.info("<=");
    return eObjectResolverVisitor.getParent();
  }
  
  public ByteBuffer getSerializedData() {
    byte[] binaryTree = pathXTree.getSerializedTreeData();

//    System.out.println();
//    System.out.println("Serialized data:");
//    for (byte aByte: binaryTree) {
//      int i = (int) (aByte & 0x7f);
//      if (aByte < 0) {
//        i |= 0x80;
//      }
//      System.out.print("0x" + Integer.toHexString(i));
//      System.out.print(" ");
//    }
//    System.out.println();
    return ByteBuffer.wrap(binaryTree);
  }
  
  public SerializedXTree getPathXTree() {
    return pathXTree;
  }
    
  public EObjectPath(ByteBuffer binaryTree) {
    pathXTree = new BinarySerializedXTree(binaryTree.array());
    
    LOGGER.info("DESERIALIZED TREE: " + pathXTree.toString());
  }
  
  @Override
  public String toString() {
    return pathXTree.toString();
  }
  
  public static void main(String[] args) {
    logSetup(Level.SEVERE, null);
    
    // Create an EObject hierarchy.
    DiscStructureSpecification discStructureSpecification = createSampleDiscStructureSpecification();
    DirectorySpecification directorySpecification = discStructureSpecification.getDirectorySpecifications().get(1);
    System.out.println("directorySpecification: " + directorySpecification.toString());
    
    // Get the path for an object
    EObjectPath eObjectPath = new EObjectPath(directorySpecification);
    
    // Serialize the path
    ByteBuffer serialized = eObjectPath.getSerializedData();
    
    System.out.println();
    System.out.println("Serialized data:");
    byte[] bytes = serialized.array();
    for (byte aByte: bytes) {
      int i = (int) (aByte & 0x7f);
      if (aByte < 0) {
        i |= 0x80;
      }
      System.out.print("0x" + Integer.toHexString(i));
      System.out.print(" ");
    }
    System.out.println();
    
    // Deserialize the path
    EObjectPath reconstructedPath = new EObjectPath(serialized);
    
    // Retrieve the object
    EObject retrievedObject = reconstructedPath.resolveToEObject(discStructureSpecification);
    System.out.println("retrievedObject: " + retrievedObject.toString());
    
    // Check that it is the original object.
    
  }

  /**
   * Logging setup.
   * <p>
   * The following setup is performed:
   * <ul>
   * <li>Set the specified logging level</li>
   * <li>Install an {@link MyLoggingFormatter}.</li>
   * <li>Install logging to a file, if a logFileBaseName is specified.</li>
   * </ul>
   * 
   * @param level the logging level to be set up.
   * @param logFileBaseName base name of the file to which logging information will be written. The actual filename is this base name with ".txt" appended to it.
   *                        If null, no file logging takes place.
   */
  protected static void logSetup(Level level, String logFileBaseName) {
    // Create Logger
    Logger logger = Logger.getLogger("");
    logger.setLevel(level);

    Handler consoleHandler = null;
    for (Handler handler: logger.getHandlers()) {
      if (handler.getClass().getName().equals("java.util.logging.ConsoleHandler")) {
        consoleHandler = handler;
        break;
      }
    }
    consoleHandler.setFormatter(new MyLoggingFormatter());
    consoleHandler.setLevel(level);

    if (logFileBaseName != null) {
      try {
        FileHandler fileHandler = new FileHandler(logFileBaseName + ".txt", false);   // true forces append mode
        Formatter simpleFormatter = new MyLoggingFormatter();
        fileHandler.setFormatter(simpleFormatter);
        logger.addHandler(fileHandler);
      } catch (SecurityException | IOException e) {
        e.printStackTrace();
      }
    }

  }
  
  private static DiscStructureSpecification createSampleDiscStructureSpecification() {
    PCToolsFactory pcToolsFactory = PCToolsFactory.eINSTANCE;
    DescribedItem describedItem = null;
        
    DiscStructureSpecification discStructureSpecification = pcToolsFactory.createDiscStructureSpecification();
    discStructureSpecification.setName("Test disc structure specification");
    discStructureSpecification.setDescription("Specification for a directory structure under 'src/test/resources' for testing this program");
    
    describedItem = pcToolsFactory.createDescribedItem();
    describedItem.setItem("blablafile.bla");
    describedItem.setDescription("Dit is alleen maar blabla");
    discStructureSpecification.getFilesToIgnoreCompletely().add(describedItem);
    
    describedItem = pcToolsFactory.createDescribedItem();
    describedItem.setItem("ignoreMeDirectory");
    describedItem.setDescription("Een map die genegeerd moet worden");
    discStructureSpecification.getDirectoriesToIgnoreCompletely().add(describedItem);
    
    DirectorySpecification directorySpecification;
    
    directorySpecification = pcToolsFactory.createDirectorySpecification();
    directorySpecification.setDirectoryPath("K:\\EclipseWorkspace\\goedegep.pctools\\target\\test-classes\\Test Directory Structure\\directory not to be checked\\controlled directory");
    directorySpecification.setDescription("Controlled directory");
    directorySpecification.setSynchronizationSpecification("Yes this is marked as synchronized");
    discStructureSpecification.getDirectorySpecifications().add(directorySpecification);
    
    directorySpecification = pcToolsFactory.createDirectorySpecification();
    directorySpecification.setDirectoryPath("K:\\EclipseWorkspace\\goedegep.pctools\\target\\test-classes\\Test Directory Structure\\directory to be checked");
    directorySpecification.setDescription("Directory to be checked");
    directorySpecification.setToBeChecked(true);
    discStructureSpecification.getDirectorySpecifications().add(directorySpecification);

    return discStructureSpecification;
  }
  
}

class EObjectResolverVisitor implements XTreeNodeVisitor {
  private final static Logger LOGGER = Logger.getLogger(EObjectResolverVisitor.class.getName());
  
  boolean handlingReferenceName = true;  // false for handling index in list for reference of type many.
  EReference reference = null;
  EObject eObject = null;
  EObject parentEObject = null;
  Integer referenceIndex = null;
  
  public EObjectResolverVisitor(EObject rootObject) {
    eObject = rootObject;
  }
  
  public EObject getEObject() {
    return eObject;
  }
  
  public EReference getEReference() {
    return reference;
  }
  
  public EObject getParent() {
    return parentEObject;
  }
  
  public Integer getIndex() {
    return referenceIndex;
  }

  @Override
  public XTreeNodeVisitResult preVisitChildren() {
    LOGGER.info("<=>");
    if (handlingReferenceName) {
      throw new RuntimeException("Unexpected child node");
    }
    return XTreeNodeVisitResult.CONTINUE;
  }

  @Override
  public XTreeNodeVisitResult visitTreeItem(XNodeDataType dataType, Object value) {
    LOGGER.info("=> dataType=" + dataType.toString() + ", value=" + value.toString());
    if (handlingReferenceName) {
      LOGGER.info("Handling reference");
      
      reference = null;
      referenceIndex = null;
      
      // reference name expected
      if (dataType != XNodeDataType.STRING) {
        throw new RuntimeException("Wrong dataType; type STRING expected (for reference name), but is " + dataType.name());
      }
      String referenceName = (String) value;
      LOGGER.info("reference name: " + referenceName);
      
      for (EReference currentReference: eObject.eClass().getEAllReferences()) {
        LOGGER.info("currentReference=" + currentReference.getName());
        if (currentReference.getName().equals(referenceName)) {
          reference = currentReference;
          LOGGER.info("Reference found");
          break;
        }
      }
      
      if (reference == null) {
        throw new RuntimeException("No reference found for reference name: " + referenceName);
      }
      LOGGER.fine("reference: " + reference.getName());
      
      if (reference.isMany()) {
        LOGGER.info("is many");
        handlingReferenceName = false;
      } else {
        LOGGER.info("is NOT many");
        parentEObject = eObject;
        eObject = (EObject) eObject.eGet(reference);
      }
      LOGGER.info("eObject=" + eObject.toString());
    } else {
      LOGGER.info("Handling index");
      // List index expected
      if (dataType != XNodeDataType.INTEGER) {
        throw new RuntimeException("Wrong dataType; type INTEGER expected (for reference name), but is " + dataType.name());
      }
      LOGGER.info("eObject=" + eObject.toString());
      LOGGER.info("reference=" + reference.getName());
      Object listObject = eObject.eGet(reference);
      LOGGER.info("listObject=" + listObject.toString());
      if (!(listObject instanceof EList)) {
        throw new RuntimeException("Wrong object in hierarchy; EList expected, but is " + listObject);
      }
      @SuppressWarnings("unchecked")
      EList<EObject> list = (EList<EObject>) listObject;
      int index = (int) value;
      if (index > list.size() - 1) {
        LOGGER.info("Wrong index, index=" + index + ", size=" + list.size());
      }
      parentEObject = eObject;
      eObject = list.get(index);
      referenceIndex = index;
      LOGGER.info("eObject obtained from list, eObject=" + eObject.toString());
      
      handlingReferenceName = true;
    }
    return XTreeNodeVisitResult.CONTINUE;
  }

  @Override
  public XTreeNodeVisitResult postVisitChildren() {
    LOGGER.info("<=>");
    if (!handlingReferenceName) {
      throw new RuntimeException("Unexpected move to parent");
    }
    return XTreeNodeVisitResult.CONTINUE;
  }
  
}


class APISampleModel {
  private static EPackage apiPackage;
  
  private static EClass apiClass;
  private static EAttribute apiNameAttribute;
  private static EReference interfacesReference;
  
  private static EClass interfaceClass;
  private static EAttribute interfaceNameAttribute;
  
  static {
    createApiEPackage();
  }
  
  private static EPackage createApiEPackage() {
    EcoreFactory ecoreFactory = EcoreFactory.eINSTANCE;
    EcorePackage ecorePackage = EcorePackage.eINSTANCE;
    
    apiPackage = ecoreFactory.createEPackage();
    
    interfaceClass = ecoreFactory.createEClass();
    interfaceClass.setName("Interface");
    interfaceNameAttribute = ecoreFactory.createEAttribute();
    interfaceNameAttribute.setName("name");
    interfaceNameAttribute.setEType(ecorePackage.getEString());
    interfaceClass.getEStructuralFeatures().add(interfaceNameAttribute);
    apiPackage.getEClassifiers().add(interfaceClass);
    
    apiClass = ecoreFactory.createEClass();
    apiClass.setName("API");
    
    apiNameAttribute = ecoreFactory.createEAttribute();
    apiNameAttribute.setName("name");
    apiNameAttribute.setEType(ecorePackage.getEString());
    apiClass.getEStructuralFeatures().add(apiNameAttribute);
    
    interfacesReference = ecoreFactory.createEReference();
    interfacesReference.setName("interfaces");
    interfacesReference.setEType(interfaceClass);
    interfacesReference.setContainment(true);
    interfacesReference.setLowerBound(0);
    interfacesReference.setUpperBound(-1);
    apiClass.getEStructuralFeatures().add(interfacesReference);
    
    apiPackage.getEClassifiers().add(apiClass);
    
    return apiPackage;
  }

  public static EObject createSampleAPI() {
    EFactory apiFactory = apiPackage.getEFactoryInstance();
    
    EObject api = apiFactory.create(apiClass);
    api.eSet(apiNameAttribute, "SampleAPI");
    
    EObject simpleInterface = apiFactory.create(interfaceClass);
    simpleInterface.eSet(interfaceNameAttribute, "SimpleInterface");
    
    @SuppressWarnings("unchecked")
    EList<EObject> interfaces = (EList<EObject>) api.eGet(interfacesReference);
    interfaces.add(simpleInterface);

    return api;
  }
}
