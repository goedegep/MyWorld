/**
 */
package goedegep.vacations.checklist.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see goedegep.vacations.checklist.model.VacationChecklistFactory
 * @model kind="package"
 * @generated
 */
public interface VacationChecklistPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "model";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.goedegep.org/vacationchecklist";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "VacationChecklist";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  VacationChecklistPackage eINSTANCE = goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.vacations.checklist.model.impl.VacationChecklistImpl <em>Vacation Checklist</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistImpl
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getVacationChecklist()
   * @generated
   */
  int VACATION_CHECKLIST = 0;

  /**
   * The feature id for the '<em><b>Vacation Checklist Labels List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST__VACATION_CHECKLIST_LABELS_LIST = 0;

  /**
   * The feature id for the '<em><b>Vacation Checklist Categories List</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST__VACATION_CHECKLIST_CATEGORIES_LIST = 1;

  /**
   * The feature id for the '<em><b>Current Vacation</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST__CURRENT_VACATION = 2;

  /**
   * The number of structural features of the '<em>Vacation Checklist</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_FEATURE_COUNT = 3;

  /**
   * The number of operations of the '<em>Vacation Checklist</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.checklist.model.impl.VacationChecklistItemImpl <em>Item</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistItemImpl
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getVacationChecklistItem()
   * @generated
   */
  int VACATION_CHECKLIST_ITEM = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_ITEM__NAME = 0;

  /**
   * The feature id for the '<em><b>Vacation Checklist Labels</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_ITEM__VACATION_CHECKLIST_LABELS = 1;

  /**
   * The number of structural features of the '<em>Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_ITEM_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_ITEM_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.checklist.model.impl.VacationChecklistLabelsListImpl <em>Labels List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistLabelsListImpl
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getVacationChecklistLabelsList()
   * @generated
   */
  int VACATION_CHECKLIST_LABELS_LIST = 2;

  /**
   * The feature id for the '<em><b>Vacation Checklist Labels</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_LABELS_LIST__VACATION_CHECKLIST_LABELS = 0;

  /**
   * The number of structural features of the '<em>Labels List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_LABELS_LIST_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Labels List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_LABELS_LIST_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.checklist.model.impl.VacationChecklistLabelImpl <em>Label</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistLabelImpl
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getVacationChecklistLabel()
   * @generated
   */
  int VACATION_CHECKLIST_LABEL = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_LABEL__NAME = 0;

  /**
   * The number of structural features of the '<em>Label</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_LABEL_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Label</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_LABEL_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.checklist.model.impl.VacationChecklistCategoryImpl <em>Category</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistCategoryImpl
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getVacationChecklistCategory()
   * @generated
   */
  int VACATION_CHECKLIST_CATEGORY = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_CATEGORY__NAME = 0;

  /**
   * The feature id for the '<em><b>Vacation Checklist Items</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_CATEGORY__VACATION_CHECKLIST_ITEMS = 1;

  /**
   * The number of structural features of the '<em>Category</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_CATEGORY_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Category</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_CATEGORY_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.checklist.model.impl.VacationChecklistCategoriesListImpl <em>Categories List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistCategoriesListImpl
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getVacationChecklistCategoriesList()
   * @generated
   */
  int VACATION_CHECKLIST_CATEGORIES_LIST = 5;

  /**
   * The feature id for the '<em><b>Vacation Checklist Categories</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_CATEGORIES_LIST__VACATION_CHECKLIST_CATEGORIES = 0;

  /**
   * The number of structural features of the '<em>Categories List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_CATEGORIES_LIST_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Categories List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VACATION_CHECKLIST_CATEGORIES_LIST_OPERATION_COUNT = 0;


  /**
   * The meta object id for the '{@link goedegep.vacations.checklist.model.impl.CurrentVacationImpl <em>Current Vacation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.checklist.model.impl.CurrentVacationImpl
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getCurrentVacation()
   * @generated
   */
  int CURRENT_VACATION = 6;

  /**
   * The feature id for the '<em><b>Selected Labels</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CURRENT_VACATION__SELECTED_LABELS = 0;

  /**
   * The feature id for the '<em><b>Item Statuses</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CURRENT_VACATION__ITEM_STATUSES = 1;

  /**
   * The number of structural features of the '<em>Current Vacation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CURRENT_VACATION_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Current Vacation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CURRENT_VACATION_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.checklist.model.impl.ItemStatusImpl <em>Item Status</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.checklist.model.impl.ItemStatusImpl
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getItemStatus()
   * @generated
   */
  int ITEM_STATUS = 7;

  /**
   * The feature id for the '<em><b>Vacation Checklist Item</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ITEM_STATUS__VACATION_CHECKLIST_ITEM = 0;

  /**
   * The feature id for the '<em><b>Pack Status</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ITEM_STATUS__PACK_STATUS = 1;

  /**
   * The number of structural features of the '<em>Item Status</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ITEM_STATUS_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Item Status</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ITEM_STATUS_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.vacations.checklist.model.PackStatus <em>Pack Status</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.vacations.checklist.model.PackStatus
   * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getPackStatus()
   * @generated
   */
  int PACK_STATUS = 8;


  /**
   * Returns the meta object for class '{@link goedegep.vacations.checklist.model.VacationChecklist <em>Vacation Checklist</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Vacation Checklist</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklist
   * @generated
   */
  EClass getVacationChecklist();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.vacations.checklist.model.VacationChecklist#getVacationChecklistLabelsList <em>Vacation Checklist Labels List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Vacation Checklist Labels List</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklist#getVacationChecklistLabelsList()
   * @see #getVacationChecklist()
   * @generated
   */
  EReference getVacationChecklist_VacationChecklistLabelsList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.vacations.checklist.model.VacationChecklist#getVacationChecklistCategoriesList <em>Vacation Checklist Categories List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Vacation Checklist Categories List</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklist#getVacationChecklistCategoriesList()
   * @see #getVacationChecklist()
   * @generated
   */
  EReference getVacationChecklist_VacationChecklistCategoriesList();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.vacations.checklist.model.VacationChecklist#getCurrentVacation <em>Current Vacation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Current Vacation</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklist#getCurrentVacation()
   * @see #getVacationChecklist()
   * @generated
   */
  EReference getVacationChecklist_CurrentVacation();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.checklist.model.VacationChecklistItem <em>Item</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Item</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklistItem
   * @generated
   */
  EClass getVacationChecklistItem();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.checklist.model.VacationChecklistItem#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklistItem#getName()
   * @see #getVacationChecklistItem()
   * @generated
   */
  EAttribute getVacationChecklistItem_Name();

  /**
   * Returns the meta object for the reference list '{@link goedegep.vacations.checklist.model.VacationChecklistItem#getVacationChecklistLabels <em>Vacation Checklist Labels</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Vacation Checklist Labels</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklistItem#getVacationChecklistLabels()
   * @see #getVacationChecklistItem()
   * @generated
   */
  EReference getVacationChecklistItem_VacationChecklistLabels();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.checklist.model.VacationChecklistLabelsList <em>Labels List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Labels List</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklistLabelsList
   * @generated
   */
  EClass getVacationChecklistLabelsList();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.checklist.model.VacationChecklistLabelsList#getVacationChecklistLabels <em>Vacation Checklist Labels</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Vacation Checklist Labels</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklistLabelsList#getVacationChecklistLabels()
   * @see #getVacationChecklistLabelsList()
   * @generated
   */
  EReference getVacationChecklistLabelsList_VacationChecklistLabels();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.checklist.model.VacationChecklistLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Label</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklistLabel
   * @generated
   */
  EClass getVacationChecklistLabel();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.checklist.model.VacationChecklistLabel#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklistLabel#getName()
   * @see #getVacationChecklistLabel()
   * @generated
   */
  EAttribute getVacationChecklistLabel_Name();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.checklist.model.VacationChecklistCategory <em>Category</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Category</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklistCategory
   * @generated
   */
  EClass getVacationChecklistCategory();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.checklist.model.VacationChecklistCategory#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklistCategory#getName()
   * @see #getVacationChecklistCategory()
   * @generated
   */
  EAttribute getVacationChecklistCategory_Name();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.checklist.model.VacationChecklistCategory#getVacationChecklistItems <em>Vacation Checklist Items</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Vacation Checklist Items</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklistCategory#getVacationChecklistItems()
   * @see #getVacationChecklistCategory()
   * @generated
   */
  EReference getVacationChecklistCategory_VacationChecklistItems();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.checklist.model.VacationChecklistCategoriesList <em>Categories List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Categories List</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklistCategoriesList
   * @generated
   */
  EClass getVacationChecklistCategoriesList();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.checklist.model.VacationChecklistCategoriesList#getVacationChecklistCategories <em>Vacation Checklist Categories</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Vacation Checklist Categories</em>'.
   * @see goedegep.vacations.checklist.model.VacationChecklistCategoriesList#getVacationChecklistCategories()
   * @see #getVacationChecklistCategoriesList()
   * @generated
   */
  EReference getVacationChecklistCategoriesList_VacationChecklistCategories();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.checklist.model.CurrentVacation <em>Current Vacation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Current Vacation</em>'.
   * @see goedegep.vacations.checklist.model.CurrentVacation
   * @generated
   */
  EClass getCurrentVacation();

  /**
   * Returns the meta object for the reference list '{@link goedegep.vacations.checklist.model.CurrentVacation#getSelectedLabels <em>Selected Labels</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Selected Labels</em>'.
   * @see goedegep.vacations.checklist.model.CurrentVacation#getSelectedLabels()
   * @see #getCurrentVacation()
   * @generated
   */
  EReference getCurrentVacation_SelectedLabels();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.vacations.checklist.model.CurrentVacation#getItemStatuses <em>Item Statuses</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Item Statuses</em>'.
   * @see goedegep.vacations.checklist.model.CurrentVacation#getItemStatuses()
   * @see #getCurrentVacation()
   * @generated
   */
  EReference getCurrentVacation_ItemStatuses();

  /**
   * Returns the meta object for class '{@link goedegep.vacations.checklist.model.ItemStatus <em>Item Status</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Item Status</em>'.
   * @see goedegep.vacations.checklist.model.ItemStatus
   * @generated
   */
  EClass getItemStatus();

  /**
   * Returns the meta object for the reference '{@link goedegep.vacations.checklist.model.ItemStatus#getVacationChecklistItem <em>Vacation Checklist Item</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Vacation Checklist Item</em>'.
   * @see goedegep.vacations.checklist.model.ItemStatus#getVacationChecklistItem()
   * @see #getItemStatus()
   * @generated
   */
  EReference getItemStatus_VacationChecklistItem();

  /**
   * Returns the meta object for the attribute '{@link goedegep.vacations.checklist.model.ItemStatus#getPackStatus <em>Pack Status</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Pack Status</em>'.
   * @see goedegep.vacations.checklist.model.ItemStatus#getPackStatus()
   * @see #getItemStatus()
   * @generated
   */
  EAttribute getItemStatus_PackStatus();

  /**
   * Returns the meta object for enum '{@link goedegep.vacations.checklist.model.PackStatus <em>Pack Status</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Pack Status</em>'.
   * @see goedegep.vacations.checklist.model.PackStatus
   * @generated
   */
  EEnum getPackStatus();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  VacationChecklistFactory getVacationChecklistFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each operation of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
     * The meta object literal for the '{@link goedegep.vacations.checklist.model.impl.VacationChecklistImpl <em>Vacation Checklist</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistImpl
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getVacationChecklist()
     * @generated
     */
    EClass VACATION_CHECKLIST = eINSTANCE.getVacationChecklist();

    /**
     * The meta object literal for the '<em><b>Vacation Checklist Labels List</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VACATION_CHECKLIST__VACATION_CHECKLIST_LABELS_LIST = eINSTANCE.getVacationChecklist_VacationChecklistLabelsList();

    /**
     * The meta object literal for the '<em><b>Vacation Checklist Categories List</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VACATION_CHECKLIST__VACATION_CHECKLIST_CATEGORIES_LIST = eINSTANCE.getVacationChecklist_VacationChecklistCategoriesList();

    /**
     * The meta object literal for the '<em><b>Current Vacation</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VACATION_CHECKLIST__CURRENT_VACATION = eINSTANCE.getVacationChecklist_CurrentVacation();

    /**
     * The meta object literal for the '{@link goedegep.vacations.checklist.model.impl.VacationChecklistItemImpl <em>Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistItemImpl
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getVacationChecklistItem()
     * @generated
     */
    EClass VACATION_CHECKLIST_ITEM = eINSTANCE.getVacationChecklistItem();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VACATION_CHECKLIST_ITEM__NAME = eINSTANCE.getVacationChecklistItem_Name();

    /**
     * The meta object literal for the '<em><b>Vacation Checklist Labels</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VACATION_CHECKLIST_ITEM__VACATION_CHECKLIST_LABELS = eINSTANCE.getVacationChecklistItem_VacationChecklistLabels();

    /**
     * The meta object literal for the '{@link goedegep.vacations.checklist.model.impl.VacationChecklistLabelsListImpl <em>Labels List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistLabelsListImpl
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getVacationChecklistLabelsList()
     * @generated
     */
    EClass VACATION_CHECKLIST_LABELS_LIST = eINSTANCE.getVacationChecklistLabelsList();

    /**
     * The meta object literal for the '<em><b>Vacation Checklist Labels</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VACATION_CHECKLIST_LABELS_LIST__VACATION_CHECKLIST_LABELS = eINSTANCE.getVacationChecklistLabelsList_VacationChecklistLabels();

    /**
     * The meta object literal for the '{@link goedegep.vacations.checklist.model.impl.VacationChecklistLabelImpl <em>Label</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistLabelImpl
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getVacationChecklistLabel()
     * @generated
     */
    EClass VACATION_CHECKLIST_LABEL = eINSTANCE.getVacationChecklistLabel();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VACATION_CHECKLIST_LABEL__NAME = eINSTANCE.getVacationChecklistLabel_Name();

    /**
     * The meta object literal for the '{@link goedegep.vacations.checklist.model.impl.VacationChecklistCategoryImpl <em>Category</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistCategoryImpl
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getVacationChecklistCategory()
     * @generated
     */
    EClass VACATION_CHECKLIST_CATEGORY = eINSTANCE.getVacationChecklistCategory();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VACATION_CHECKLIST_CATEGORY__NAME = eINSTANCE.getVacationChecklistCategory_Name();

    /**
     * The meta object literal for the '<em><b>Vacation Checklist Items</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VACATION_CHECKLIST_CATEGORY__VACATION_CHECKLIST_ITEMS = eINSTANCE.getVacationChecklistCategory_VacationChecklistItems();

    /**
     * The meta object literal for the '{@link goedegep.vacations.checklist.model.impl.VacationChecklistCategoriesListImpl <em>Categories List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistCategoriesListImpl
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getVacationChecklistCategoriesList()
     * @generated
     */
    EClass VACATION_CHECKLIST_CATEGORIES_LIST = eINSTANCE.getVacationChecklistCategoriesList();

    /**
     * The meta object literal for the '<em><b>Vacation Checklist Categories</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VACATION_CHECKLIST_CATEGORIES_LIST__VACATION_CHECKLIST_CATEGORIES = eINSTANCE.getVacationChecklistCategoriesList_VacationChecklistCategories();

    /**
     * The meta object literal for the '{@link goedegep.vacations.checklist.model.impl.CurrentVacationImpl <em>Current Vacation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.vacations.checklist.model.impl.CurrentVacationImpl
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getCurrentVacation()
     * @generated
     */
    EClass CURRENT_VACATION = eINSTANCE.getCurrentVacation();

    /**
     * The meta object literal for the '<em><b>Selected Labels</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CURRENT_VACATION__SELECTED_LABELS = eINSTANCE.getCurrentVacation_SelectedLabels();

    /**
     * The meta object literal for the '<em><b>Item Statuses</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CURRENT_VACATION__ITEM_STATUSES = eINSTANCE.getCurrentVacation_ItemStatuses();

    /**
     * The meta object literal for the '{@link goedegep.vacations.checklist.model.impl.ItemStatusImpl <em>Item Status</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.vacations.checklist.model.impl.ItemStatusImpl
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getItemStatus()
     * @generated
     */
    EClass ITEM_STATUS = eINSTANCE.getItemStatus();

    /**
     * The meta object literal for the '<em><b>Vacation Checklist Item</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ITEM_STATUS__VACATION_CHECKLIST_ITEM = eINSTANCE.getItemStatus_VacationChecklistItem();

    /**
     * The meta object literal for the '<em><b>Pack Status</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ITEM_STATUS__PACK_STATUS = eINSTANCE.getItemStatus_PackStatus();

    /**
     * The meta object literal for the '{@link goedegep.vacations.checklist.model.PackStatus <em>Pack Status</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.vacations.checklist.model.PackStatus
     * @see goedegep.vacations.checklist.model.impl.VacationChecklistPackageImpl#getPackStatus()
     * @generated
     */
    EEnum PACK_STATUS = eINSTANCE.getPackStatus();

  }

} //VacationChecklistPackage
