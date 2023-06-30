/**
 */
package goedegep.pctools.filescontrolled.model.impl;

import goedegep.pctools.filescontrolled.model.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PCToolsFactoryImpl extends EFactoryImpl implements PCToolsFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static PCToolsFactory init() {
    try {
      PCToolsFactory thePCToolsFactory = (PCToolsFactory)EPackage.Registry.INSTANCE.getEFactory(PCToolsPackage.eNS_URI);
      if (thePCToolsFactory != null) {
        return thePCToolsFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new PCToolsFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PCToolsFactoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION: return createDiscStructureSpecification();
      case PCToolsPackage.DIRECTORY_SPECIFICATION: return createDirectorySpecification();
      case PCToolsPackage.DESCRIBED_ITEM: return createDescribedItem();
      case PCToolsPackage.RESULT: return createResult();
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO: return createUncontrolledFolderInfo();
      case PCToolsPackage.FILE_INFO: return createFileInfo();
      case PCToolsPackage.CONTROLLED_ROOT_FOLDER_INFO: return createControlledRootFolderInfo();
      case PCToolsPackage.CONTROLLED_FOLDER_INFO: return createControlledFolderInfo();
      case PCToolsPackage.FOLDER_INFO: return createFolderInfo();
      case PCToolsPackage.UNCONTROLLED_ROOT_FOLDER_INFO: return createUncontrolledRootFolderInfo();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue) {
    switch (eDataType.getClassifierID()) {
      case PCToolsPackage.EQUALITY_TYPE:
        return createEqualityTypeFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue) {
    switch (eDataType.getClassifierID()) {
      case PCToolsPackage.EQUALITY_TYPE:
        return convertEqualityTypeToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public DiscStructureSpecification createDiscStructureSpecification() {
    DiscStructureSpecificationImpl discStructureSpecification = new DiscStructureSpecificationImpl();
    return discStructureSpecification;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public DirectorySpecification createDirectorySpecification() {
    DirectorySpecificationImpl directorySpecification = new DirectorySpecificationImpl();
    return directorySpecification;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public DescribedItem createDescribedItem() {
    DescribedItemImpl describedItem = new DescribedItemImpl();
    return describedItem;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Result createResult() {
    ResultImpl result = new ResultImpl();
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public UncontrolledFolderInfo createUncontrolledFolderInfo() {
    UncontrolledFolderInfoImpl uncontrolledFolderInfo = new UncontrolledFolderInfoImpl();
    return uncontrolledFolderInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FolderInfo createFolderInfo() {
    FolderInfoImpl folderInfo = new FolderInfoImpl();
    return folderInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public UncontrolledRootFolderInfo createUncontrolledRootFolderInfo() {
    UncontrolledRootFolderInfoImpl uncontrolledRootFolderInfo = new UncontrolledRootFolderInfoImpl();
    return uncontrolledRootFolderInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FileInfo createFileInfo() {
    FileInfoImpl fileInfo = new FileInfoImpl();
    return fileInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ControlledRootFolderInfo createControlledRootFolderInfo() {
    ControlledRootFolderInfoImpl controlledRootFolderInfo = new ControlledRootFolderInfoImpl();
    return controlledRootFolderInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ControlledFolderInfo createControlledFolderInfo() {
    ControlledFolderInfoImpl controlledFolderInfo = new ControlledFolderInfoImpl();
    return controlledFolderInfo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EqualityType createEqualityTypeFromString(EDataType eDataType, String initialValue) {
    EqualityType result = EqualityType.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertEqualityTypeToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PCToolsPackage getPCToolsPackage() {
    return (PCToolsPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static PCToolsPackage getPackage() {
    return PCToolsPackage.eINSTANCE;
  }

} //PCToolsFactoryImpl
