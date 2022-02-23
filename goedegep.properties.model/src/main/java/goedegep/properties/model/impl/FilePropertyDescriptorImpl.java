/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.properties.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import goedegep.properties.model.FilePropertyDescriptor;
import goedegep.properties.model.PropertiesPackage;
import goedegep.properties.model.PropertyType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>File Property Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.properties.model.impl.FilePropertyDescriptorImpl#getFileExtensions <em>File Extensions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FilePropertyDescriptorImpl extends PropertyDescriptorImpl implements FilePropertyDescriptor {
  /**
   * The cached value of the '{@link #getFileExtensions() <em>File Extensions</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFileExtensions()
   * @generated
   * @ordered
   */
  protected EList<String> fileExtensions;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  protected FilePropertyDescriptorImpl() {
    super();
    setType(PropertyType.FILE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return PropertiesPackage.Literals.FILE_PROPERTY_DESCRIPTOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<String> getFileExtensions() {
    if (fileExtensions == null) {
      fileExtensions = new EDataTypeUniqueEList<String>(String.class, this,
          PropertiesPackage.FILE_PROPERTY_DESCRIPTOR__FILE_EXTENSIONS);
    }
    return fileExtensions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case PropertiesPackage.FILE_PROPERTY_DESCRIPTOR__FILE_EXTENSIONS:
      return getFileExtensions();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case PropertiesPackage.FILE_PROPERTY_DESCRIPTOR__FILE_EXTENSIONS:
      getFileExtensions().clear();
      getFileExtensions().addAll((Collection<? extends String>) newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
    case PropertiesPackage.FILE_PROPERTY_DESCRIPTOR__FILE_EXTENSIONS:
      getFileExtensions().clear();
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
    case PropertiesPackage.FILE_PROPERTY_DESCRIPTOR__FILE_EXTENSIONS:
      return fileExtensions != null && !fileExtensions.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy())
      return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (fileExtensions: ");
    result.append(fileExtensions);
    result.append(')');
    return result.toString();
  }

} //FilePropertyDescriptorImpl
