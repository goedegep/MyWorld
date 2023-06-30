/**
 */
package goedegep.pctools.filescontrolled.model.impl;

import goedegep.pctools.filescontrolled.model.ControlledFolderInfo;
import goedegep.pctools.filescontrolled.model.ControlledRootFolderInfo;
import goedegep.pctools.filescontrolled.model.DescribedItem;
import goedegep.pctools.filescontrolled.model.DirectorySpecification;
import goedegep.pctools.filescontrolled.model.DiscStructureSpecification;
import goedegep.pctools.filescontrolled.model.EqualityType;
import goedegep.pctools.filescontrolled.model.FileInfo;
import goedegep.pctools.filescontrolled.model.FolderInfo;
import goedegep.pctools.filescontrolled.model.PCToolsFactory;
import goedegep.pctools.filescontrolled.model.PCToolsPackage;

import goedegep.pctools.filescontrolled.model.Result;
import goedegep.pctools.filescontrolled.model.UncontrolledFolderInfo;
import goedegep.pctools.filescontrolled.model.UncontrolledRootFolderInfo;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass resultEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass uncontrolledFolderInfoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass folderInfoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass uncontrolledRootFolderInfoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fileInfoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass controlledRootFolderInfoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass controlledFolderInfoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum equalityTypeEEnum = null;

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
  public EClass getResult() {
    return resultEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getResult_Controlledrootfolderinfos() {
    return (EReference)resultEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getResult_UncontrolledRootFolderInfos() {
    return (EReference)resultEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getUncontrolledFolderInfo() {
    return uncontrolledFolderInfoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getUncontrolledFolderInfo_AllContentsHasCopies() {
    return (EAttribute)uncontrolledFolderInfoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getUncontrolledFolderInfo_Fileinfos() {
    return (EReference)uncontrolledFolderInfoEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getUncontrolledFolderInfo_SubFoldersInfos() {
    return (EReference)uncontrolledFolderInfoEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getFolderInfo() {
    return folderInfoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getFolderInfo_FolderName() {
    return (EAttribute)folderInfoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getFolderInfo__GetFullPathname() {
    return folderInfoEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getUncontrolledRootFolderInfo() {
    return uncontrolledRootFolderInfoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getUncontrolledRootFolderInfo_FolderBasePath() {
    return (EAttribute)uncontrolledRootFolderInfoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getFileInfo() {
    return fileInfoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getFileInfo_FileName() {
    return (EAttribute)fileInfoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getFileInfo_CopyOf() {
    return (EReference)fileInfoEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getFileInfo_EqualityType() {
    return (EAttribute)fileInfoEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getFileInfo_Md5String() {
    return (EAttribute)fileInfoEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EOperation getFileInfo__GetFullPathname() {
    return fileInfoEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getControlledRootFolderInfo() {
    return controlledRootFolderInfoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getControlledRootFolderInfo_FolderBasePath() {
    return (EAttribute)controlledRootFolderInfoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getControlledFolderInfo() {
    return controlledFolderInfoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getControlledFolderInfo_SubFolderInfos() {
    return (EReference)controlledFolderInfoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getControlledFolderInfo_Fileinfos() {
    return (EReference)controlledFolderInfoEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getEqualityType() {
    return equalityTypeEEnum;
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

    resultEClass = createEClass(RESULT);
    createEReference(resultEClass, RESULT__CONTROLLEDROOTFOLDERINFOS);
    createEReference(resultEClass, RESULT__UNCONTROLLED_ROOT_FOLDER_INFOS);

    uncontrolledFolderInfoEClass = createEClass(UNCONTROLLED_FOLDER_INFO);
    createEAttribute(uncontrolledFolderInfoEClass, UNCONTROLLED_FOLDER_INFO__ALL_CONTENTS_HAS_COPIES);
    createEReference(uncontrolledFolderInfoEClass, UNCONTROLLED_FOLDER_INFO__FILEINFOS);
    createEReference(uncontrolledFolderInfoEClass, UNCONTROLLED_FOLDER_INFO__SUB_FOLDERS_INFOS);

    fileInfoEClass = createEClass(FILE_INFO);
    createEAttribute(fileInfoEClass, FILE_INFO__FILE_NAME);
    createEReference(fileInfoEClass, FILE_INFO__COPY_OF);
    createEAttribute(fileInfoEClass, FILE_INFO__EQUALITY_TYPE);
    createEAttribute(fileInfoEClass, FILE_INFO__MD5_STRING);
    createEOperation(fileInfoEClass, FILE_INFO___GET_FULL_PATHNAME);

    controlledRootFolderInfoEClass = createEClass(CONTROLLED_ROOT_FOLDER_INFO);
    createEAttribute(controlledRootFolderInfoEClass, CONTROLLED_ROOT_FOLDER_INFO__FOLDER_BASE_PATH);

    controlledFolderInfoEClass = createEClass(CONTROLLED_FOLDER_INFO);
    createEReference(controlledFolderInfoEClass, CONTROLLED_FOLDER_INFO__SUB_FOLDER_INFOS);
    createEReference(controlledFolderInfoEClass, CONTROLLED_FOLDER_INFO__FILEINFOS);

    folderInfoEClass = createEClass(FOLDER_INFO);
    createEAttribute(folderInfoEClass, FOLDER_INFO__FOLDER_NAME);
    createEOperation(folderInfoEClass, FOLDER_INFO___GET_FULL_PATHNAME);

    uncontrolledRootFolderInfoEClass = createEClass(UNCONTROLLED_ROOT_FOLDER_INFO);
    createEAttribute(uncontrolledRootFolderInfoEClass, UNCONTROLLED_ROOT_FOLDER_INFO__FOLDER_BASE_PATH);

    // Create enums
    equalityTypeEEnum = createEEnum(EQUALITY_TYPE);
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
    uncontrolledFolderInfoEClass.getESuperTypes().add(this.getFolderInfo());
    controlledRootFolderInfoEClass.getESuperTypes().add(this.getControlledFolderInfo());
    controlledFolderInfoEClass.getESuperTypes().add(this.getFolderInfo());
    uncontrolledRootFolderInfoEClass.getESuperTypes().add(this.getUncontrolledFolderInfo());

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

    initEClass(resultEClass, Result.class, "Result", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getResult_Controlledrootfolderinfos(), this.getControlledRootFolderInfo(), null, "controlledrootfolderinfos", null, 0, -1, Result.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResult_UncontrolledRootFolderInfos(), this.getUncontrolledRootFolderInfo(), null, "uncontrolledRootFolderInfos", null, 0, -1, Result.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(uncontrolledFolderInfoEClass, UncontrolledFolderInfo.class, "UncontrolledFolderInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getUncontrolledFolderInfo_AllContentsHasCopies(), ecorePackage.getEBoolean(), "allContentsHasCopies", null, 0, 1, UncontrolledFolderInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getUncontrolledFolderInfo_Fileinfos(), this.getFileInfo(), null, "fileinfos", null, 0, -1, UncontrolledFolderInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getUncontrolledFolderInfo_SubFoldersInfos(), this.getUncontrolledFolderInfo(), null, "subFoldersInfos", null, 0, -1, UncontrolledFolderInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fileInfoEClass, FileInfo.class, "FileInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFileInfo_FileName(), ecorePackage.getEString(), "fileName", null, 0, 1, FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getFileInfo_CopyOf(), this.getFileInfo(), null, "copyOf", null, 0, 1, FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFileInfo_EqualityType(), this.getEqualityType(), "equalityType", "", 0, 1, FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFileInfo_Md5String(), ecorePackage.getEString(), "md5String", null, 0, 1, FileInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getFileInfo__GetFullPathname(), ecorePackage.getEString(), "getFullPathname", 1, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(controlledRootFolderInfoEClass, ControlledRootFolderInfo.class, "ControlledRootFolderInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getControlledRootFolderInfo_FolderBasePath(), ecorePackage.getEString(), "folderBasePath", null, 1, 1, ControlledRootFolderInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(controlledFolderInfoEClass, ControlledFolderInfo.class, "ControlledFolderInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getControlledFolderInfo_SubFolderInfos(), this.getControlledFolderInfo(), null, "subFolderInfos", null, 0, -1, ControlledFolderInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getControlledFolderInfo_Fileinfos(), this.getFileInfo(), null, "fileinfos", null, 0, -1, ControlledFolderInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(folderInfoEClass, FolderInfo.class, "FolderInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFolderInfo_FolderName(), ecorePackage.getEString(), "folderName", null, 1, 1, FolderInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getFolderInfo__GetFullPathname(), ecorePackage.getEString(), "getFullPathname", 1, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(uncontrolledRootFolderInfoEClass, UncontrolledRootFolderInfo.class, "UncontrolledRootFolderInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getUncontrolledRootFolderInfo_FolderBasePath(), ecorePackage.getEString(), "folderBasePath", null, 1, 1, UncontrolledRootFolderInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(equalityTypeEEnum, EqualityType.class, "EqualityType");
    addEEnumLiteral(equalityTypeEEnum, EqualityType.SIZE);
    addEEnumLiteral(equalityTypeEEnum, EqualityType.MD5);
    addEEnumLiteral(equalityTypeEEnum, EqualityType.EQUAL);
    addEEnumLiteral(equalityTypeEEnum, EqualityType.NOT_EQUAL);

    // Create resource
    createResource(eNS_URI);
  }

} //PCToolsPackageImpl
