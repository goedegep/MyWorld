/**
 */
package goedegep.finan.lynx2finan.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.finan.lynx2finan.model.LynxToFinanPackage
 * @generated
 */
public interface LynxToFinanFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  LynxToFinanFactory eINSTANCE = goedegep.finan.lynx2finan.model.impl.LynxToFinanFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Share Id List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Share Id List</em>'.
   * @generated
   */
  LynxToFinanShareIdList createLynxToFinanShareIdList();

  /**
   * Returns a new object of class '<em>Share Id List Entry</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Share Id List Entry</em>'.
   * @generated
   */
  LynxToFinanShareIdListEntry createLynxToFinanShareIdListEntry();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  LynxToFinanPackage getLynxToFinanPackage();

} //LynxToFinanFactory
