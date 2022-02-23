/**
 */
package goedegep.vacations.checklist.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.vacations.checklist.model.VacationChecklistPackage
 * @generated
 */
public interface VacationChecklistFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  VacationChecklistFactory eINSTANCE = goedegep.vacations.checklist.model.impl.VacationChecklistFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Vacation Checklist</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Vacation Checklist</em>'.
   * @generated
   */
  VacationChecklist createVacationChecklist();

  /**
   * Returns a new object of class '<em>Item</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Item</em>'.
   * @generated
   */
  VacationChecklistItem createVacationChecklistItem();

  /**
   * Returns a new object of class '<em>Labels List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Labels List</em>'.
   * @generated
   */
  VacationChecklistLabelsList createVacationChecklistLabelsList();

  /**
   * Returns a new object of class '<em>Label</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Label</em>'.
   * @generated
   */
  VacationChecklistLabel createVacationChecklistLabel();

  /**
   * Returns a new object of class '<em>Category</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Category</em>'.
   * @generated
   */
  VacationChecklistCategory createVacationChecklistCategory();

  /**
   * Returns a new object of class '<em>Categories List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Categories List</em>'.
   * @generated
   */
  VacationChecklistCategoriesList createVacationChecklistCategoriesList();

  /**
   * Returns a new object of class '<em>Current Vacation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Current Vacation</em>'.
   * @generated
   */
  CurrentVacation createCurrentVacation();

  /**
   * Returns a new object of class '<em>Item Status</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Item Status</em>'.
   * @generated
   */
  ItemStatus createItemStatus();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  VacationChecklistPackage getVacationChecklistPackage();

} //VacationChecklistFactory
