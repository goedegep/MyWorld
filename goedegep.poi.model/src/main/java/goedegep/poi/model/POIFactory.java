/**
 */
package goedegep.poi.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.poi.model.POIPackage
 * @generated
 */
public interface POIFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  POIFactory eINSTANCE = goedegep.poi.model.impl.POIFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Icon Resource Descriptor</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Icon Resource Descriptor</em>'.
   * @generated
   */
  POIIconResourceDescriptor createPOIIconResourceDescriptor();

  /**
   * Returns a new object of class '<em>Icon Resource Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Icon Resource Info</em>'.
   * @generated
   */
  POIIconResourceInfo createPOIIconResourceInfo();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  POIPackage getPOIPackage();

} //POIFactory
