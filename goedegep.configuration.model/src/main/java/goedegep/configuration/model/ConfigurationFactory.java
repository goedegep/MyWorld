/**
 */
package goedegep.configuration.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.configuration.model.ConfigurationPackage
 * @generated
 */
public interface ConfigurationFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ConfigurationFactory eINSTANCE = goedegep.configuration.model.impl.ConfigurationFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Look Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Look Info</em>'.
   * @generated
   */
  LookInfo createLookInfo();

  /**
   * Returns a new object of class '<em>Module Look</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Module Look</em>'.
   * @generated
   */
  ModuleLook createModuleLook();

  /**
   * Returns a new object of class '<em>Look</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Look</em>'.
   * @generated
   */
  Look createLook();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  ConfigurationPackage getConfigurationPackage();

} //ConfigurationFactory
