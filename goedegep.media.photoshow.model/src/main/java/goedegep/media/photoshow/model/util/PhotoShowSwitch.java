/**
 */
package goedegep.media.photoshow.model.util;

import goedegep.media.photoshow.model.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

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
 * @see goedegep.media.photoshow.model.PhotoShowPackage
 * @generated
 */
public class PhotoShowSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static PhotoShowPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PhotoShowSwitch() {
    if (modelPackage == null) {
      modelPackage = PhotoShowPackage.eINSTANCE;
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
      case PhotoShowPackage.PHOTO_SHOW_SPECIFICATION: {
        PhotoShowSpecification photoShowSpecification = (PhotoShowSpecification)theEObject;
        T result = casePhotoShowSpecification(photoShowSpecification);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PhotoShowPackage.TIME_OFFSET_SPECIFICATION: {
        TimeOffsetSpecification timeOffsetSpecification = (TimeOffsetSpecification)theEObject;
        T result = caseTimeOffsetSpecification(timeOffsetSpecification);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PhotoShowPackage.FOLDER_TIME_OFFSET_SPECIFICATION: {
        FolderTimeOffsetSpecification folderTimeOffsetSpecification = (FolderTimeOffsetSpecification)theEObject;
        T result = caseFolderTimeOffsetSpecification(folderTimeOffsetSpecification);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Specification</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Specification</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePhotoShowSpecification(PhotoShowSpecification object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Time Offset Specification</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Time Offset Specification</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTimeOffsetSpecification(TimeOffsetSpecification object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Folder Time Offset Specification</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Folder Time Offset Specification</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFolderTimeOffsetSpecification(FolderTimeOffsetSpecification object) {
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

} //PhotoShowSwitch
