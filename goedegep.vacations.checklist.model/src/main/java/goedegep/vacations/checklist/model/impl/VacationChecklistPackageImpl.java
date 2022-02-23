/**
 */
package goedegep.vacations.checklist.model.impl;

import goedegep.vacations.checklist.model.CurrentVacation;
import goedegep.vacations.checklist.model.ItemStatus;
import goedegep.vacations.checklist.model.PackStatus;
import goedegep.vacations.checklist.model.VacationChecklist;
import goedegep.vacations.checklist.model.VacationChecklistCategoriesList;
import goedegep.vacations.checklist.model.VacationChecklistCategory;
import goedegep.vacations.checklist.model.VacationChecklistFactory;
import goedegep.vacations.checklist.model.VacationChecklistItem;
import goedegep.vacations.checklist.model.VacationChecklistLabel;
import goedegep.vacations.checklist.model.VacationChecklistLabelsList;
import goedegep.vacations.checklist.model.VacationChecklistPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VacationChecklistPackageImpl extends EPackageImpl implements VacationChecklistPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass vacationChecklistEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass vacationChecklistItemEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass vacationChecklistLabelsListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass vacationChecklistLabelEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass vacationChecklistCategoryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass vacationChecklistCategoriesListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass currentVacationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass itemStatusEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum packStatusEEnum = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see goedegep.vacations.checklist.model.VacationChecklistPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private VacationChecklistPackageImpl() {
    super(eNS_URI, VacationChecklistFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   *
   * <p>This method is used to initialize {@link VacationChecklistPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static VacationChecklistPackage init() {
    if (isInited) return (VacationChecklistPackage)EPackage.Registry.INSTANCE.getEPackage(VacationChecklistPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredVacationChecklistPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    VacationChecklistPackageImpl theVacationChecklistPackage = registeredVacationChecklistPackage instanceof VacationChecklistPackageImpl ? (VacationChecklistPackageImpl)registeredVacationChecklistPackage : new VacationChecklistPackageImpl();

    isInited = true;

    // Create package meta-data objects
    theVacationChecklistPackage.createPackageContents();

    // Initialize created meta-data
    theVacationChecklistPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theVacationChecklistPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(VacationChecklistPackage.eNS_URI, theVacationChecklistPackage);
    return theVacationChecklistPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getVacationChecklist() {
    return vacationChecklistEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacationChecklist_VacationChecklistLabelsList() {
    return (EReference)vacationChecklistEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacationChecklist_VacationChecklistCategoriesList() {
    return (EReference)vacationChecklistEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacationChecklist_CurrentVacation() {
    return (EReference)vacationChecklistEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getVacationChecklistItem() {
    return vacationChecklistItemEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getVacationChecklistItem_Name() {
    return (EAttribute)vacationChecklistItemEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacationChecklistItem_VacationChecklistLabels() {
    return (EReference)vacationChecklistItemEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getVacationChecklistLabelsList() {
    return vacationChecklistLabelsListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacationChecklistLabelsList_VacationChecklistLabels() {
    return (EReference)vacationChecklistLabelsListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getVacationChecklistLabel() {
    return vacationChecklistLabelEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getVacationChecklistLabel_Name() {
    return (EAttribute)vacationChecklistLabelEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getVacationChecklistCategory() {
    return vacationChecklistCategoryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getVacationChecklistCategory_Name() {
    return (EAttribute)vacationChecklistCategoryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacationChecklistCategory_VacationChecklistItems() {
    return (EReference)vacationChecklistCategoryEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getVacationChecklistCategoriesList() {
    return vacationChecklistCategoriesListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getVacationChecklistCategoriesList_VacationChecklistCategories() {
    return (EReference)vacationChecklistCategoriesListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getCurrentVacation() {
    return currentVacationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getCurrentVacation_SelectedLabels() {
    return (EReference)currentVacationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getCurrentVacation_ItemStatuses() {
    return (EReference)currentVacationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getItemStatus() {
    return itemStatusEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getItemStatus_VacationChecklistItem() {
    return (EReference)itemStatusEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getItemStatus_PackStatus() {
    return (EAttribute)itemStatusEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EEnum getPackStatus() {
    return packStatusEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public VacationChecklistFactory getVacationChecklistFactory() {
    return (VacationChecklistFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    vacationChecklistEClass = createEClass(VACATION_CHECKLIST);
    createEReference(vacationChecklistEClass, VACATION_CHECKLIST__VACATION_CHECKLIST_LABELS_LIST);
    createEReference(vacationChecklistEClass, VACATION_CHECKLIST__VACATION_CHECKLIST_CATEGORIES_LIST);
    createEReference(vacationChecklistEClass, VACATION_CHECKLIST__CURRENT_VACATION);

    vacationChecklistItemEClass = createEClass(VACATION_CHECKLIST_ITEM);
    createEAttribute(vacationChecklistItemEClass, VACATION_CHECKLIST_ITEM__NAME);
    createEReference(vacationChecklistItemEClass, VACATION_CHECKLIST_ITEM__VACATION_CHECKLIST_LABELS);

    vacationChecklistLabelsListEClass = createEClass(VACATION_CHECKLIST_LABELS_LIST);
    createEReference(vacationChecklistLabelsListEClass, VACATION_CHECKLIST_LABELS_LIST__VACATION_CHECKLIST_LABELS);

    vacationChecklistLabelEClass = createEClass(VACATION_CHECKLIST_LABEL);
    createEAttribute(vacationChecklistLabelEClass, VACATION_CHECKLIST_LABEL__NAME);

    vacationChecklistCategoryEClass = createEClass(VACATION_CHECKLIST_CATEGORY);
    createEAttribute(vacationChecklistCategoryEClass, VACATION_CHECKLIST_CATEGORY__NAME);
    createEReference(vacationChecklistCategoryEClass, VACATION_CHECKLIST_CATEGORY__VACATION_CHECKLIST_ITEMS);

    vacationChecklistCategoriesListEClass = createEClass(VACATION_CHECKLIST_CATEGORIES_LIST);
    createEReference(vacationChecklistCategoriesListEClass, VACATION_CHECKLIST_CATEGORIES_LIST__VACATION_CHECKLIST_CATEGORIES);

    currentVacationEClass = createEClass(CURRENT_VACATION);
    createEReference(currentVacationEClass, CURRENT_VACATION__SELECTED_LABELS);
    createEReference(currentVacationEClass, CURRENT_VACATION__ITEM_STATUSES);

    itemStatusEClass = createEClass(ITEM_STATUS);
    createEReference(itemStatusEClass, ITEM_STATUS__VACATION_CHECKLIST_ITEM);
    createEAttribute(itemStatusEClass, ITEM_STATUS__PACK_STATUS);

    // Create enums
    packStatusEEnum = createEEnum(PACK_STATUS);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes, features, and operations; add parameters
    initEClass(vacationChecklistEClass, VacationChecklist.class, "VacationChecklist", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getVacationChecklist_VacationChecklistLabelsList(), this.getVacationChecklistLabelsList(), null, "vacationChecklistLabelsList", null, 1, 1, VacationChecklist.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVacationChecklist_VacationChecklistCategoriesList(), this.getVacationChecklistCategoriesList(), null, "vacationChecklistCategoriesList", null, 1, 1, VacationChecklist.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVacationChecklist_CurrentVacation(), this.getCurrentVacation(), null, "currentVacation", null, 1, 1, VacationChecklist.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(vacationChecklistItemEClass, VacationChecklistItem.class, "VacationChecklistItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getVacationChecklistItem_Name(), ecorePackage.getEString(), "name", null, 1, 1, VacationChecklistItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVacationChecklistItem_VacationChecklistLabels(), this.getVacationChecklistLabel(), null, "vacationChecklistLabels", null, 0, -1, VacationChecklistItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(vacationChecklistLabelsListEClass, VacationChecklistLabelsList.class, "VacationChecklistLabelsList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getVacationChecklistLabelsList_VacationChecklistLabels(), this.getVacationChecklistLabel(), null, "vacationChecklistLabels", null, 0, -1, VacationChecklistLabelsList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(vacationChecklistLabelEClass, VacationChecklistLabel.class, "VacationChecklistLabel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getVacationChecklistLabel_Name(), ecorePackage.getEString(), "name", null, 1, 1, VacationChecklistLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(vacationChecklistCategoryEClass, VacationChecklistCategory.class, "VacationChecklistCategory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getVacationChecklistCategory_Name(), ecorePackage.getEString(), "name", null, 1, 1, VacationChecklistCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVacationChecklistCategory_VacationChecklistItems(), this.getVacationChecklistItem(), null, "vacationChecklistItems", null, 0, -1, VacationChecklistCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(vacationChecklistCategoriesListEClass, VacationChecklistCategoriesList.class, "VacationChecklistCategoriesList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getVacationChecklistCategoriesList_VacationChecklistCategories(), this.getVacationChecklistCategory(), null, "vacationChecklistCategories", null, 0, -1, VacationChecklistCategoriesList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(currentVacationEClass, CurrentVacation.class, "CurrentVacation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCurrentVacation_SelectedLabels(), this.getVacationChecklistLabel(), null, "selectedLabels", null, 0, -1, CurrentVacation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCurrentVacation_ItemStatuses(), this.getItemStatus(), null, "itemStatuses", null, 0, -1, CurrentVacation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(itemStatusEClass, ItemStatus.class, "ItemStatus", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getItemStatus_VacationChecklistItem(), this.getVacationChecklistItem(), null, "vacationChecklistItem", null, 0, 1, ItemStatus.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getItemStatus_PackStatus(), this.getPackStatus(), "packStatus", null, 0, 1, ItemStatus.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(packStatusEEnum, PackStatus.class, "PackStatus");
    addEEnumLiteral(packStatusEEnum, PackStatus.TODO);
    addEEnumLiteral(packStatusEEnum, PackStatus.PACKED);
    addEEnumLiteral(packStatusEEnum, PackStatus.NOT_NEEDED);

    // Create resource
    createResource(eNS_URI);
  }

} //VacationChecklistPackageImpl
