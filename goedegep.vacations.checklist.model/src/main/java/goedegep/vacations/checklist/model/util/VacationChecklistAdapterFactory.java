/**
 */
package goedegep.vacations.checklist.model.util;

import goedegep.vacations.checklist.model.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.vacations.checklist.model.VacationChecklistPackage
 * @generated
 */
public class VacationChecklistAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static VacationChecklistPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VacationChecklistAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = VacationChecklistPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object) {
    if (object == modelPackage) {
      return true;
    }
    if (object instanceof EObject) {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VacationChecklistSwitch<Adapter> modelSwitch =
    new VacationChecklistSwitch<Adapter>() {
      @Override
      public Adapter caseVacationChecklist(VacationChecklist object) {
        return createVacationChecklistAdapter();
      }
      @Override
      public Adapter caseVacationChecklistItem(VacationChecklistItem object) {
        return createVacationChecklistItemAdapter();
      }
      @Override
      public Adapter caseVacationChecklistLabelsList(VacationChecklistLabelsList object) {
        return createVacationChecklistLabelsListAdapter();
      }
      @Override
      public Adapter caseVacationChecklistLabel(VacationChecklistLabel object) {
        return createVacationChecklistLabelAdapter();
      }
      @Override
      public Adapter caseVacationChecklistCategory(VacationChecklistCategory object) {
        return createVacationChecklistCategoryAdapter();
      }
      @Override
      public Adapter caseVacationChecklistCategoriesList(VacationChecklistCategoriesList object) {
        return createVacationChecklistCategoriesListAdapter();
      }
      @Override
      public Adapter caseCurrentVacation(CurrentVacation object) {
        return createCurrentVacationAdapter();
      }
      @Override
      public Adapter caseItemStatus(ItemStatus object) {
        return createItemStatusAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object) {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target) {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.checklist.model.VacationChecklist <em>Vacation Checklist</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.checklist.model.VacationChecklist
   * @generated
   */
  public Adapter createVacationChecklistAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.checklist.model.VacationChecklistItem <em>Item</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.checklist.model.VacationChecklistItem
   * @generated
   */
  public Adapter createVacationChecklistItemAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.checklist.model.VacationChecklistLabelsList <em>Labels List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.checklist.model.VacationChecklistLabelsList
   * @generated
   */
  public Adapter createVacationChecklistLabelsListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.checklist.model.VacationChecklistLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.checklist.model.VacationChecklistLabel
   * @generated
   */
  public Adapter createVacationChecklistLabelAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.checklist.model.VacationChecklistCategory <em>Category</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.checklist.model.VacationChecklistCategory
   * @generated
   */
  public Adapter createVacationChecklistCategoryAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.checklist.model.VacationChecklistCategoriesList <em>Categories List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.checklist.model.VacationChecklistCategoriesList
   * @generated
   */
  public Adapter createVacationChecklistCategoriesListAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.checklist.model.CurrentVacation <em>Current Vacation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.checklist.model.CurrentVacation
   * @generated
   */
  public Adapter createCurrentVacationAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.checklist.model.ItemStatus <em>Item Status</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.checklist.model.ItemStatus
   * @generated
   */
  public Adapter createItemStatusAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter() {
    return null;
  }

} //VacationChecklistAdapterFactory
