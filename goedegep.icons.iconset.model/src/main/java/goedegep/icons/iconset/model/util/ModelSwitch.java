/**
 */
package goedegep.icons.iconset.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import goedegep.icons.iconset.model.*;

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
 * @see goedegep.icons.iconset.model.ModelPackage
 * @generated
 */
public class ModelSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static ModelPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelSwitch() {
    if (modelPackage == null) {
      modelPackage = ModelPackage.eINSTANCE;
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
      case ModelPackage.ICON_SET: {
        IconSet iconSet = (IconSet)theEObject;
        T result = caseIconSet(iconSet);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ModelPackage.ICON_SIZE: {
        IconSize iconSize = (IconSize)theEObject;
        T result = caseIconSize(iconSize);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ModelPackage.ICON_DESCRIPTOR: {
        IconDescriptor iconDescriptor = (IconDescriptor)theEObject;
        T result = caseIconDescriptor(iconDescriptor);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ModelPackage.ICON_DATA: {
        IconData iconData = (IconData)theEObject;
        T result = caseIconData(iconData);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ModelPackage.ICON_INFO: {
        IconInfo iconInfo = (IconInfo)theEObject;
        T result = caseIconInfo(iconInfo);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ModelPackage.ICON_DEFINITION: {
        IconDefinition iconDefinition = (IconDefinition)theEObject;
        T result = caseIconDefinition(iconDefinition);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Icon Set</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Icon Set</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIconSet(IconSet object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Icon Size</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Icon Size</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIconSize(IconSize object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Icon Descriptor</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Icon Descriptor</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIconDescriptor(IconDescriptor object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Icon Data</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Icon Data</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIconData(IconData object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Icon Info</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Icon Info</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIconInfo(IconInfo object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Icon Definition</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Icon Definition</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIconDefinition(IconDefinition object) {
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

} //ModelSwitch
