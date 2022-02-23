/**
 */
package goedegep.icons.iconset.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.icons.iconset.model.ModelPackage
 * @generated
 */
public interface ModelFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ModelFactory eINSTANCE = goedegep.icons.iconset.model.impl.ModelFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Icon Set</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Icon Set</em>'.
   * @generated
   */
  IconSet createIconSet();

  /**
   * Returns a new object of class '<em>Icon Size</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Icon Size</em>'.
   * @generated
   */
  IconSize createIconSize();

  /**
   * Returns a new object of class '<em>Icon Descriptor</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Icon Descriptor</em>'.
   * @generated
   */
  IconDescriptor createIconDescriptor();

  /**
   * Returns a new object of class '<em>Icon Data</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Icon Data</em>'.
   * @generated
   */
  IconData createIconData();

  /**
   * Returns a new object of class '<em>Icon Info</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Icon Info</em>'.
   * @generated
   */
  IconInfo createIconInfo();

  /**
   * Returns a new object of class '<em>Icon Definition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Icon Definition</em>'.
   * @generated
   */
  IconDefinition createIconDefinition();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  ModelPackage getModelPackage();

} //ModelFactory
