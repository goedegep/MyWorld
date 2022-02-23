/**
 */
package goedegep.vacations.checklist.model.util;

import goedegep.vacations.checklist.model.*;

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
 * @see goedegep.vacations.checklist.model.VacationChecklistPackage
 * @generated
 */
public class VacationChecklistSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static VacationChecklistPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VacationChecklistSwitch() {
    if (modelPackage == null) {
      modelPackage = VacationChecklistPackage.eINSTANCE;
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
      case VacationChecklistPackage.VACATION_CHECKLIST: {
        VacationChecklist vacationChecklist = (VacationChecklist)theEObject;
        T result = caseVacationChecklist(vacationChecklist);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VacationChecklistPackage.VACATION_CHECKLIST_ITEM: {
        VacationChecklistItem vacationChecklistItem = (VacationChecklistItem)theEObject;
        T result = caseVacationChecklistItem(vacationChecklistItem);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VacationChecklistPackage.VACATION_CHECKLIST_LABELS_LIST: {
        VacationChecklistLabelsList vacationChecklistLabelsList = (VacationChecklistLabelsList)theEObject;
        T result = caseVacationChecklistLabelsList(vacationChecklistLabelsList);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VacationChecklistPackage.VACATION_CHECKLIST_LABEL: {
        VacationChecklistLabel vacationChecklistLabel = (VacationChecklistLabel)theEObject;
        T result = caseVacationChecklistLabel(vacationChecklistLabel);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY: {
        VacationChecklistCategory vacationChecklistCategory = (VacationChecklistCategory)theEObject;
        T result = caseVacationChecklistCategory(vacationChecklistCategory);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORIES_LIST: {
        VacationChecklistCategoriesList vacationChecklistCategoriesList = (VacationChecklistCategoriesList)theEObject;
        T result = caseVacationChecklistCategoriesList(vacationChecklistCategoriesList);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VacationChecklistPackage.CURRENT_VACATION: {
        CurrentVacation currentVacation = (CurrentVacation)theEObject;
        T result = caseCurrentVacation(currentVacation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case VacationChecklistPackage.ITEM_STATUS: {
        ItemStatus itemStatus = (ItemStatus)theEObject;
        T result = caseItemStatus(itemStatus);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Vacation Checklist</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Vacation Checklist</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVacationChecklist(VacationChecklist object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Item</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Item</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVacationChecklistItem(VacationChecklistItem object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Labels List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Labels List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVacationChecklistLabelsList(VacationChecklistLabelsList object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Label</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Label</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVacationChecklistLabel(VacationChecklistLabel object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Category</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Category</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVacationChecklistCategory(VacationChecklistCategory object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Categories List</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Categories List</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVacationChecklistCategoriesList(VacationChecklistCategoriesList object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Current Vacation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Current Vacation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCurrentVacation(CurrentVacation object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Item Status</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Item Status</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseItemStatus(ItemStatus object) {
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

} //VacationChecklistSwitch
