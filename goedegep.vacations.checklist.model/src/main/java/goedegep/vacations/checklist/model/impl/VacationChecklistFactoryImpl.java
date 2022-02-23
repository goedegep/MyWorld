/**
 */
package goedegep.vacations.checklist.model.impl;

import goedegep.vacations.checklist.model.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VacationChecklistFactoryImpl extends EFactoryImpl implements VacationChecklistFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static VacationChecklistFactory init() {
    try {
      VacationChecklistFactory theVacationChecklistFactory = (VacationChecklistFactory)EPackage.Registry.INSTANCE.getEFactory(VacationChecklistPackage.eNS_URI);
      if (theVacationChecklistFactory != null) {
        return theVacationChecklistFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new VacationChecklistFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VacationChecklistFactoryImpl() {
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
      case VacationChecklistPackage.VACATION_CHECKLIST: return createVacationChecklist();
      case VacationChecklistPackage.VACATION_CHECKLIST_ITEM: return createVacationChecklistItem();
      case VacationChecklistPackage.VACATION_CHECKLIST_LABELS_LIST: return createVacationChecklistLabelsList();
      case VacationChecklistPackage.VACATION_CHECKLIST_LABEL: return createVacationChecklistLabel();
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORY: return createVacationChecklistCategory();
      case VacationChecklistPackage.VACATION_CHECKLIST_CATEGORIES_LIST: return createVacationChecklistCategoriesList();
      case VacationChecklistPackage.CURRENT_VACATION: return createCurrentVacation();
      case VacationChecklistPackage.ITEM_STATUS: return createItemStatus();
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
      case VacationChecklistPackage.PACK_STATUS:
        return createPackStatusFromString(eDataType, initialValue);
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
      case VacationChecklistPackage.PACK_STATUS:
        return convertPackStatusToString(eDataType, instanceValue);
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
  public VacationChecklist createVacationChecklist() {
    VacationChecklistImpl vacationChecklist = new VacationChecklistImpl();
    return vacationChecklist;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public VacationChecklistItem createVacationChecklistItem() {
    VacationChecklistItemImpl vacationChecklistItem = new VacationChecklistItemImpl();
    return vacationChecklistItem;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public VacationChecklistLabelsList createVacationChecklistLabelsList() {
    VacationChecklistLabelsListImpl vacationChecklistLabelsList = new VacationChecklistLabelsListImpl();
    return vacationChecklistLabelsList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public VacationChecklistLabel createVacationChecklistLabel() {
    VacationChecklistLabelImpl vacationChecklistLabel = new VacationChecklistLabelImpl();
    return vacationChecklistLabel;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public VacationChecklistCategory createVacationChecklistCategory() {
    VacationChecklistCategoryImpl vacationChecklistCategory = new VacationChecklistCategoryImpl();
    return vacationChecklistCategory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public VacationChecklistCategoriesList createVacationChecklistCategoriesList() {
    VacationChecklistCategoriesListImpl vacationChecklistCategoriesList = new VacationChecklistCategoriesListImpl();
    return vacationChecklistCategoriesList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CurrentVacation createCurrentVacation() {
    CurrentVacationImpl currentVacation = new CurrentVacationImpl();
    return currentVacation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ItemStatus createItemStatus() {
    ItemStatusImpl itemStatus = new ItemStatusImpl();
    return itemStatus;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PackStatus createPackStatusFromString(EDataType eDataType, String initialValue) {
    PackStatus result = PackStatus.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertPackStatusToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public VacationChecklistPackage getVacationChecklistPackage() {
    return (VacationChecklistPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static VacationChecklistPackage getPackage() {
    return VacationChecklistPackage.eINSTANCE;
  }

} //VacationChecklistFactoryImpl
