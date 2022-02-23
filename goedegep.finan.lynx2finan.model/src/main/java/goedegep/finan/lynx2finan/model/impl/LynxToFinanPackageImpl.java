/**
 */
package goedegep.finan.lynx2finan.model.impl;

import goedegep.finan.lynx2finan.model.LynxToFinanFactory;
import goedegep.finan.lynx2finan.model.LynxToFinanPackage;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdList;
import goedegep.finan.lynx2finan.model.LynxToFinanShareIdListEntry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LynxToFinanPackageImpl extends EPackageImpl implements LynxToFinanPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass lynxToFinanShareIdListEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass lynxToFinanShareIdListEntryEClass = null;

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
   * @see goedegep.finan.lynx2finan.model.LynxToFinanPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private LynxToFinanPackageImpl() {
    super(eNS_URI, LynxToFinanFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link LynxToFinanPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static LynxToFinanPackage init() {
    if (isInited) return (LynxToFinanPackage)EPackage.Registry.INSTANCE.getEPackage(LynxToFinanPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredLynxToFinanPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    LynxToFinanPackageImpl theLynxToFinanPackage = registeredLynxToFinanPackage instanceof LynxToFinanPackageImpl ? (LynxToFinanPackageImpl)registeredLynxToFinanPackage : new LynxToFinanPackageImpl();

    isInited = true;

    // Create package meta-data objects
    theLynxToFinanPackage.createPackageContents();

    // Initialize created meta-data
    theLynxToFinanPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theLynxToFinanPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(LynxToFinanPackage.eNS_URI, theLynxToFinanPackage);
    return theLynxToFinanPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getLynxToFinanShareIdList() {
    return lynxToFinanShareIdListEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EReference getLynxToFinanShareIdList_Entries() {
    return (EReference)lynxToFinanShareIdListEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EClass getLynxToFinanShareIdListEntry() {
    return lynxToFinanShareIdListEntryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLynxToFinanShareIdListEntry_UniqueId() {
    return (EAttribute)lynxToFinanShareIdListEntryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLynxToFinanShareIdListEntry_SecurityName() {
    return (EAttribute)lynxToFinanShareIdListEntryEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLynxToFinanShareIdListEntry_FinanName() {
    return (EAttribute)lynxToFinanShareIdListEntryEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLynxToFinanShareIdListEntry_FiId() {
    return (EAttribute)lynxToFinanShareIdListEntryEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLynxToFinanShareIdListEntry_UniqueIdType() {
    return (EAttribute)lynxToFinanShareIdListEntryEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EAttribute getLynxToFinanShareIdListEntry_TickerSymbol() {
    return (EAttribute)lynxToFinanShareIdListEntryEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public LynxToFinanFactory getLynxToFinanFactory() {
    return (LynxToFinanFactory)getEFactoryInstance();
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
    lynxToFinanShareIdListEClass = createEClass(LYNX_TO_FINAN_SHARE_ID_LIST);
    createEReference(lynxToFinanShareIdListEClass, LYNX_TO_FINAN_SHARE_ID_LIST__ENTRIES);

    lynxToFinanShareIdListEntryEClass = createEClass(LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY);
    createEAttribute(lynxToFinanShareIdListEntryEClass, LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID);
    createEAttribute(lynxToFinanShareIdListEntryEClass, LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__SECURITY_NAME);
    createEAttribute(lynxToFinanShareIdListEntryEClass, LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FINAN_NAME);
    createEAttribute(lynxToFinanShareIdListEntryEClass, LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__FI_ID);
    createEAttribute(lynxToFinanShareIdListEntryEClass, LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__UNIQUE_ID_TYPE);
    createEAttribute(lynxToFinanShareIdListEntryEClass, LYNX_TO_FINAN_SHARE_ID_LIST_ENTRY__TICKER_SYMBOL);
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
    initEClass(lynxToFinanShareIdListEClass, LynxToFinanShareIdList.class, "LynxToFinanShareIdList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLynxToFinanShareIdList_Entries(), this.getLynxToFinanShareIdListEntry(), null, "entries", null, 0, -1, LynxToFinanShareIdList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

    initEClass(lynxToFinanShareIdListEntryEClass, LynxToFinanShareIdListEntry.class, "LynxToFinanShareIdListEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLynxToFinanShareIdListEntry_UniqueId(), ecorePackage.getEString(), "uniqueId", null, 0, 1, LynxToFinanShareIdListEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLynxToFinanShareIdListEntry_SecurityName(), ecorePackage.getEString(), "securityName", null, 0, 1, LynxToFinanShareIdListEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLynxToFinanShareIdListEntry_FinanName(), ecorePackage.getEString(), "finanName", null, 0, 1, LynxToFinanShareIdListEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLynxToFinanShareIdListEntry_FiId(), ecorePackage.getEString(), "fiId", null, 0, 1, LynxToFinanShareIdListEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLynxToFinanShareIdListEntry_UniqueIdType(), ecorePackage.getEString(), "uniqueIdType", "", 0, 1, LynxToFinanShareIdListEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLynxToFinanShareIdListEntry_TickerSymbol(), ecorePackage.getEString(), "tickerSymbol", null, 0, 1, LynxToFinanShareIdListEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //LynxToFinanPackageImpl
