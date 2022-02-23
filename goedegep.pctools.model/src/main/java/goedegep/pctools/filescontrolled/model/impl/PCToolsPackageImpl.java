/**
 */
package goedegep.pctools.filescontrolled.model.impl;

import goedegep.pctools.filescontrolled.model.DescribedItem;
import goedegep.pctools.filescontrolled.model.DirectorySpecification;
import goedegep.pctools.filescontrolled.model.DiscStructureSpecification;
import goedegep.pctools.filescontrolled.model.PCToolsFactory;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PCToolsPackageImpl extends EPackageImpl implements PCToolsPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass discStructureSpecificationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass directorySpecificationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass describedItemEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see goedegep.pctools.filescontrolled.model.PCToolsPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private PCToolsPackageImpl() {
    super(eNS_URI, PCToolsFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   *
   * <p>This method is used to initialize {@link PCToolsPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static PCToolsPackage init() {
    if (isInited) return (PCToolsPackage)EPackage.Registry.INSTANCE.getEPackage(PCToolsPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredPCToolsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    PCToolsPackageImpl thePCToolsPackage = registeredPCToolsPackage instanceof PCToolsPackageImpl ? (PCToolsPackageImpl)registeredPCToolsPackage : new PCToolsPackageImpl();

    isInited = true;

    // Create package meta-data objects
    thePCToolsPackage.createPackageContents();

    // Initialize created meta-data
    thePCToolsPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    thePCToolsPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(PCToolsPackage.eNS_URI, thePCToolsPackage);
    return thePCToolsPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getDiscStructureSpecification() {
    return discStructureSpecificationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDiscStructureSpecification_Name() {
    return (EAttribute)discStructureSpecificationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDiscStructureSpecification_Description() {
    return (EAttribute)discStructureSpecificationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getDiscStructureSpecification_FilesToIgnoreCompletely() {
    return (EReference)discStructureSpecificationEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getDiscStructureSpecification_DirectoriesToIgnoreCompletely() {
    return (EReference)discStructureSpecificationEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getDiscStructureSpecification_DirectorySpecifications() {
    return (EReference)discStructureSpecificationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getDirectorySpecification() {
    return directorySpecificationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDirectorySpecification_DirectoryPath() {
    return (EAttribute)directorySpecificationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDirectorySpecification_Description() {
    return (EAttribute)directorySpecificationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDirectorySpecification_SynchronizationSpecification() {
    return (EAttribute)directorySpecificationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDirectorySpecification_SourceControlSpecification() {
    return (EAttribute)directorySpecificationEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDirectorySpecification_ToBeChecked() {
    return (EAttribute)directorySpecificationEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getDirectorySpecification__IsControlled() {
    return directorySpecificationEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getDescribedItem() {
    return describedItemEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDescribedItem_Item() {
    return (EAttribute)describedItemEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getDescribedItem_Description() {
    return (EAttribute)describedItemEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PCToolsFactory getPCToolsFactory() {
    return (PCToolsFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    discStructureSpecificationEClass = createEClass(DISC_STRUCTURE_SPECIFICATION);
    createEAttribute(discStructureSpecificationEClass, DISC_STRUCTURE_SPECIFICATION__NAME);
    createEAttribute(discStructureSpecificationEClass, DISC_STRUCTURE_SPECIFICATION__DESCRIPTION);
    createEReference(discStructureSpecificationEClass, DISC_STRUCTURE_SPECIFICATION__DIRECTORY_SPECIFICATIONS);
    createEReference(discStructureSpecificationEClass, DISC_STRUCTURE_SPECIFICATION__FILES_TO_IGNORE_COMPLETELY);
    createEReference(discStructureSpecificationEClass, DISC_STRUCTURE_SPECIFICATION__DIRECTORIES_TO_IGNORE_COMPLETELY);

    directorySpecificationEClass = createEClass(DIRECTORY_SPECIFICATION);
    createEAttribute(directorySpecificationEClass, DIRECTORY_SPECIFICATION__DIRECTORY_PATH);
    createEAttribute(directorySpecificationEClass, DIRECTORY_SPECIFICATION__DESCRIPTION);
    createEAttribute(directorySpecificationEClass, DIRECTORY_SPECIFICATION__SYNCHRONIZATION_SPECIFICATION);
    createEAttribute(directorySpecificationEClass, DIRECTORY_SPECIFICATION__SOURCE_CONTROL_SPECIFICATION);
    createEAttribute(directorySpecificationEClass, DIRECTORY_SPECIFICATION__TO_BE_CHECKED);
    createEOperation(directorySpecificationEClass, DIRECTORY_SPECIFICATION___IS_CONTROLLED);

    describedItemEClass = createEClass(DESCRIBED_ITEM);
    createEAttribute(describedItemEClass, DESCRIBED_ITEM__ITEM);
    createEAttribute(describedItemEClass, DESCRIBED_ITEM__DESCRIPTION);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes, features, and operations; add parameters
    initEClass(discStructureSpecificationEClass, DiscStructureSpecification.class, "DiscStructureSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDiscStructureSpecification_Name(), ecorePackage.getEString(), "name", null, 0, 1, DiscStructureSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDiscStructureSpecification_Description(), ecorePackage.getEString(), "description", null, 0, 1, DiscStructureSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDiscStructureSpecification_DirectorySpecifications(), this.getDirectorySpecification(), null, "directorySpecifications", null, 0, -1, DiscStructureSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDiscStructureSpecification_FilesToIgnoreCompletely(), this.getDescribedItem(), null, "filesToIgnoreCompletely", null, 0, -1, DiscStructureSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDiscStructureSpecification_DirectoriesToIgnoreCompletely(), this.getDescribedItem(), null, "directoriesToIgnoreCompletely", null, 0, -1, DiscStructureSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(directorySpecificationEClass, DirectorySpecification.class, "DirectorySpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDirectorySpecification_DirectoryPath(), ecorePackage.getEString(), "directoryPath", null, 0, 1, DirectorySpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDirectorySpecification_Description(), ecorePackage.getEString(), "description", null, 0, 1, DirectorySpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDirectorySpecification_SynchronizationSpecification(), ecorePackage.getEString(), "synchronizationSpecification", null, 0, 1, DirectorySpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDirectorySpecification_SourceControlSpecification(), ecorePackage.getEString(), "sourceControlSpecification", null, 0, 1, DirectorySpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDirectorySpecification_ToBeChecked(), ecorePackage.getEBoolean(), "toBeChecked", null, 0, 1, DirectorySpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getDirectorySpecification__IsControlled(), ecorePackage.getEBoolean(), "isControlled", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(describedItemEClass, DescribedItem.class, "DescribedItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDescribedItem_Item(), ecorePackage.getEString(), "item", null, 0, 1, DescribedItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDescribedItem_Description(), ecorePackage.getEString(), "description", null, 0, 1, DescribedItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //PCToolsPackageImpl
