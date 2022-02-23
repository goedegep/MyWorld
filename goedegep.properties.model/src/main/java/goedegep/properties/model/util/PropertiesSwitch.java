/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.properties.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import goedegep.properties.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see goedegep.properties.model.PropertiesPackage
 * @generated
 */
public class PropertiesSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static PropertiesPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PropertiesSwitch() {
    if (modelPackage == null) {
      modelPackage = PropertiesPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage) {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject) {
    switch (classifierID) {
    case PropertiesPackage.PROPERTY_DESCRIPTOR_GROUP: {
      PropertyDescriptorGroup propertyDescriptorGroup = (PropertyDescriptorGroup) theEObject;
      T result = casePropertyDescriptorGroup(propertyDescriptorGroup);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case PropertiesPackage.PROPERTY_DESCRIPTOR: {
      PropertyDescriptor propertyDescriptor = (PropertyDescriptor) theEObject;
      T result = casePropertyDescriptor(propertyDescriptor);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case PropertiesPackage.FILE_PROPERTY_DESCRIPTOR: {
      FilePropertyDescriptor filePropertyDescriptor = (FilePropertyDescriptor) theEObject;
      T result = caseFilePropertyDescriptor(filePropertyDescriptor);
      if (result == null)
        result = casePropertyDescriptor(filePropertyDescriptor);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case PropertiesPackage.PROPERTY: {
      Property property = (Property) theEObject;
      T result = caseProperty(property);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    case PropertiesPackage.PROPERTY_GROUP: {
      PropertyGroup propertyGroup = (PropertyGroup) theEObject;
      T result = casePropertyGroup(propertyGroup);
      if (result == null)
        result = defaultCase(theEObject);
      return result;
    }
    default:
      return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Property Descriptor Group</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Property Descriptor Group</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePropertyDescriptorGroup(PropertyDescriptorGroup object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Property Descriptor</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Property Descriptor</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePropertyDescriptor(PropertyDescriptor object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>File Property Descriptor</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>File Property Descriptor</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFilePropertyDescriptor(FilePropertyDescriptor object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Property</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseProperty(Property object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Property Group</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Property Group</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePropertyGroup(PropertyGroup object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object) {
    return null;
  }

} //PropertiesSwitch
