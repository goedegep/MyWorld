/**
 */
package goedegep.util.emf.samplemodel.impl;

import goedegep.util.emf.samplemodel.*;

import org.eclipse.emf.ecore.EClass;
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
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DiscStructureSpecification createDiscStructureSpecification() {
    DiscStructureSpecificationImpl discStructureSpecification = new DiscStructureSpecificationImpl();
    return discStructureSpecification;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DirectorySpecification createDirectorySpecification() {
    DirectorySpecificationImpl directorySpecification = new DirectorySpecificationImpl();
    return directorySpecification;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DescribedItem createDescribedItem() {
    DescribedItemImpl describedItem = new DescribedItemImpl();
    return describedItem;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
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
