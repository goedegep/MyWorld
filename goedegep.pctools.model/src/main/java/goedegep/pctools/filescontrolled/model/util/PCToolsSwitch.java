/**
 */
package goedegep.pctools.filescontrolled.model.util;

import goedegep.pctools.filescontrolled.model.*;

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
 * @see goedegep.pctools.filescontrolled.model.PCToolsPackage
 * @generated
 */
public class PCToolsSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static PCToolsPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PCToolsSwitch() {
    if (modelPackage == null) {
      modelPackage = PCToolsPackage.eINSTANCE;
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
      case PCToolsPackage.DISC_STRUCTURE_SPECIFICATION: {
        DiscStructureSpecification discStructureSpecification = (DiscStructureSpecification)theEObject;
        T result = caseDiscStructureSpecification(discStructureSpecification);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PCToolsPackage.DIRECTORY_SPECIFICATION: {
        DirectorySpecification directorySpecification = (DirectorySpecification)theEObject;
        T result = caseDirectorySpecification(directorySpecification);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PCToolsPackage.DESCRIBED_ITEM: {
        DescribedItem describedItem = (DescribedItem)theEObject;
        T result = caseDescribedItem(describedItem);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PCToolsPackage.RESULT: {
        Result result = (Result)theEObject;
        T theResult = caseResult(result);
        if (theResult == null) theResult = defaultCase(theEObject);
        return theResult;
      }
      case PCToolsPackage.UNCONTROLLED_FOLDER_INFO: {
        UncontrolledFolderInfo uncontrolledFolderInfo = (UncontrolledFolderInfo)theEObject;
        T result = caseUncontrolledFolderInfo(uncontrolledFolderInfo);
        if (result == null) result = caseFolderInfo(uncontrolledFolderInfo);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PCToolsPackage.FILE_INFO: {
        FileInfo fileInfo = (FileInfo)theEObject;
        T result = caseFileInfo(fileInfo);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PCToolsPackage.CONTROLLED_ROOT_FOLDER_INFO: {
        ControlledRootFolderInfo controlledRootFolderInfo = (ControlledRootFolderInfo)theEObject;
        T result = caseControlledRootFolderInfo(controlledRootFolderInfo);
        if (result == null) result = caseControlledFolderInfo(controlledRootFolderInfo);
        if (result == null) result = caseFolderInfo(controlledRootFolderInfo);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PCToolsPackage.CONTROLLED_FOLDER_INFO: {
        ControlledFolderInfo controlledFolderInfo = (ControlledFolderInfo)theEObject;
        T result = caseControlledFolderInfo(controlledFolderInfo);
        if (result == null) result = caseFolderInfo(controlledFolderInfo);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PCToolsPackage.FOLDER_INFO: {
        FolderInfo folderInfo = (FolderInfo)theEObject;
        T result = caseFolderInfo(folderInfo);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PCToolsPackage.UNCONTROLLED_ROOT_FOLDER_INFO: {
        UncontrolledRootFolderInfo uncontrolledRootFolderInfo = (UncontrolledRootFolderInfo)theEObject;
        T result = caseUncontrolledRootFolderInfo(uncontrolledRootFolderInfo);
        if (result == null) result = caseUncontrolledFolderInfo(uncontrolledRootFolderInfo);
        if (result == null) result = caseFolderInfo(uncontrolledRootFolderInfo);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Disc Structure Specification</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Disc Structure Specification</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDiscStructureSpecification(DiscStructureSpecification object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Directory Specification</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Directory Specification</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDirectorySpecification(DirectorySpecification object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Described Item</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Described Item</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDescribedItem(DescribedItem object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Result</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Result</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseResult(Result object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Uncontrolled Folder Info</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Uncontrolled Folder Info</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUncontrolledFolderInfo(UncontrolledFolderInfo object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Folder Info</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Folder Info</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFolderInfo(FolderInfo object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Uncontrolled Root Folder Info</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Uncontrolled Root Folder Info</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUncontrolledRootFolderInfo(UncontrolledRootFolderInfo object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>File Info</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>File Info</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFileInfo(FileInfo object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Controlled Root Folder Info</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Controlled Root Folder Info</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseControlledRootFolderInfo(ControlledRootFolderInfo object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Controlled Folder Info</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Controlled Folder Info</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseControlledFolderInfo(ControlledFolderInfo object) {
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

} //PCToolsSwitch
