/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.properties.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import goedegep.properties.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertiesFactoryImpl extends EFactoryImpl implements PropertiesFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static PropertiesFactory init() {
    try {
      PropertiesFactory thePropertiesFactory = (PropertiesFactory) EPackage.Registry.INSTANCE
          .getEFactory(PropertiesPackage.eNS_URI);
      if (thePropertiesFactory != null) {
        return thePropertiesFactory;
      }
    } catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new PropertiesFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PropertiesFactoryImpl() {
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
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP:
      return createPropertyDescriptorGroup();
    case PropertiesPackage.PROPERTY_DESCRIPTOR:
      return createPropertyDescriptor();
    case PropertiesPackage.FILE_PROPERTY_DESCRIPTOR:
      return createFilePropertyDescriptor();
    case PropertiesPackage.PROPERTY:
      return createProperty();
    case PropertiesPackage.PROPERTY_GROUP:
      return createPropertyGroup();
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
    case PropertiesPackage.PROPERTY_TYPE:
      return createPropertyTypeFromString(eDataType, initialValue);
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
    case PropertiesPackage.PROPERTY_TYPE:
      return convertPropertyTypeToString(eDataType, instanceValue);
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
  public PropertyDescriptorGroup createPropertyDescriptorGroup() {
    PropertyDescriptorGroupImpl propertyDescriptorGroup = new PropertyDescriptorGroupImpl();
    return propertyDescriptorGroup;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PropertyDescriptor createPropertyDescriptor() {
    PropertyDescriptorImpl propertyDescriptor = new PropertyDescriptorImpl();
    return propertyDescriptor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FilePropertyDescriptor createFilePropertyDescriptor() {
    FilePropertyDescriptorImpl filePropertyDescriptor = new FilePropertyDescriptorImpl();
    return filePropertyDescriptor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Property createProperty() {
    PropertyImpl property = new PropertyImpl();
    return property;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PropertyGroup createPropertyGroup() {
    PropertyGroupImpl propertyGroup = new PropertyGroupImpl();
    return propertyGroup;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PropertyType createPropertyTypeFromString(EDataType eDataType, String initialValue) {
    PropertyType result = PropertyType.get(initialValue);
    if (result == null)
      throw new IllegalArgumentException(
          "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertPropertyTypeToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PropertiesPackage getPropertiesPackage() {
    return (PropertiesPackage) getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static PropertiesPackage getPackage() {
    return PropertiesPackage.eINSTANCE;
  }

} //PropertiesFactoryImpl
