/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.properties.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.properties.model.PropertiesPackage
 * @generated
 */
public interface PropertiesFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  PropertiesFactory eINSTANCE = goedegep.properties.model.impl.PropertiesFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Property Descriptor Group</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Property Descriptor Group</em>'.
   * @generated
   */
  PropertyDescriptorGroup createPropertyDescriptorGroup();

  /**
   * Returns a new object of class '<em>Property Descriptor</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Property Descriptor</em>'.
   * @generated
   */
  PropertyDescriptor createPropertyDescriptor();

  /**
   * Returns a new object of class '<em>File Property Descriptor</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>File Property Descriptor</em>'.
   * @generated
   */
  FilePropertyDescriptor createFilePropertyDescriptor();

  /**
   * Returns a new object of class '<em>Property</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Property</em>'.
   * @generated
   */
  Property createProperty();

  /**
   * Returns a new object of class '<em>Property Group</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Property Group</em>'.
   * @generated
   */
  PropertyGroup createPropertyGroup();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  PropertiesPackage getPropertiesPackage();

} //PropertiesFactory
