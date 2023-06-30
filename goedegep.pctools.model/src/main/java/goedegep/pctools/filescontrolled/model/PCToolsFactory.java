/**
 */
package goedegep.pctools.filescontrolled.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage
 * @generated
 */
public interface PCToolsFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  PCToolsFactory eINSTANCE = goedegep.pctools.filescontrolled.model.impl.PCToolsFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Disc Structure Specification</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Disc Structure Specification</em>'.
   * @generated
   */
  DiscStructureSpecification createDiscStructureSpecification();

  /**
   * Returns a new object of class '<em>Directory Specification</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Directory Specification</em>'.
   * @generated
   */
  DirectorySpecification createDirectorySpecification();

  /**
   * Returns a new object of class '<em>Described Item</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Described Item</em>'.
   * @generated
   */
  DescribedItem createDescribedItem();

  /**
   * Returns a new object of class '<em>Result</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Result</em>'.
   * @generated
   */
  Result createResult();

  /**
   * Returns a new object of class '<em>Uncontrolled Folder Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Uncontrolled Folder Info</em>'.
   * @generated
   */
  UncontrolledFolderInfo createUncontrolledFolderInfo();

  /**
   * Returns a new object of class '<em>Folder Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Folder Info</em>'.
   * @generated
   */
  FolderInfo createFolderInfo();

  /**
   * Returns a new object of class '<em>Uncontrolled Root Folder Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Uncontrolled Root Folder Info</em>'.
   * @generated
   */
  UncontrolledRootFolderInfo createUncontrolledRootFolderInfo();

  /**
   * Returns a new object of class '<em>File Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>File Info</em>'.
   * @generated
   */
  FileInfo createFileInfo();

  /**
   * Returns a new object of class '<em>Controlled Root Folder Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Controlled Root Folder Info</em>'.
   * @generated
   */
  ControlledRootFolderInfo createControlledRootFolderInfo();

  /**
   * Returns a new object of class '<em>Controlled Folder Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Controlled Folder Info</em>'.
   * @generated
   */
  ControlledFolderInfo createControlledFolderInfo();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  PCToolsPackage getPCToolsPackage();

} //PCToolsFactory
