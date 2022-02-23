/**
 */
package goedegep.media.photoshow.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.media.photoshow.model.PhotoShowPackage
 * @generated
 */
public interface PhotoShowFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  PhotoShowFactory eINSTANCE = goedegep.media.photoshow.model.impl.PhotoShowFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Specification</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Specification</em>'.
   * @generated
   */
  PhotoShowSpecification createPhotoShowSpecification();

  /**
   * Returns a new object of class '<em>Time Offset Specification</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Time Offset Specification</em>'.
   * @generated
   */
  TimeOffsetSpecification createTimeOffsetSpecification();

  /**
   * Returns a new object of class '<em>Folder Time Offset Specification</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Folder Time Offset Specification</em>'.
   * @generated
   */
  FolderTimeOffsetSpecification createFolderTimeOffsetSpecification();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  PhotoShowPackage getPhotoShowPackage();

} //PhotoShowFactory
