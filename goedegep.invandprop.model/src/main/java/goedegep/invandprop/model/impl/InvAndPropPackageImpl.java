/**
 */
package goedegep.invandprop.model.impl;

import goedegep.invandprop.model.InvAndPropFactory;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoiceAndProperty;
import goedegep.invandprop.model.InvoiceAndPropertyItem;
import goedegep.invandprop.model.InvoicesAndProperties;
import goedegep.types.model.TypesPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InvAndPropPackageImpl extends EPackageImpl implements InvAndPropPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass invoicesAndPropertiesEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass invoiceAndPropertyItemEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass invoiceAndPropertyEClass = null;

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
   * @see goedegep.invandprop.model.InvAndPropPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private InvAndPropPackageImpl() {
    super(eNS_URI, InvAndPropFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link InvAndPropPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static InvAndPropPackage init() {
    if (isInited) return (InvAndPropPackage)EPackage.Registry.INSTANCE.getEPackage(InvAndPropPackage.eNS_URI);

    // Obtain or create and register package
    Object registeredInvAndPropPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
    InvAndPropPackageImpl theInvAndPropPackage = registeredInvAndPropPackage instanceof InvAndPropPackageImpl ? (InvAndPropPackageImpl)registeredInvAndPropPackage : new InvAndPropPackageImpl();

    isInited = true;

    // Initialize simple dependencies
    TypesPackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theInvAndPropPackage.createPackageContents();

    // Initialize created meta-data
    theInvAndPropPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theInvAndPropPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(InvAndPropPackage.eNS_URI, theInvAndPropPackage);
    return theInvAndPropPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInvoicesAndProperties() {
    return invoicesAndPropertiesEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvoicesAndProperties_Invoicseandpropertys() {
    return (EReference)invoicesAndPropertiesEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInvoiceAndPropertyItem() {
    return invoiceAndPropertyItemEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceAndPropertyItem_Type() {
    return (EAttribute)invoiceAndPropertyItemEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceAndPropertyItem_SerialNumber() {
    return (EAttribute)invoiceAndPropertyItemEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceAndPropertyItem_Description() {
    return (EAttribute)invoiceAndPropertyItemEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceAndPropertyItem_Brand() {
    return (EAttribute)invoiceAndPropertyItemEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceAndPropertyItem_Amount() {
    return (EAttribute)invoiceAndPropertyItemEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceAndPropertyItem_FromDate() {
    return (EAttribute)invoiceAndPropertyItemEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceAndPropertyItem_UntilDate() {
    return (EAttribute)invoiceAndPropertyItemEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceAndPropertyItem_Archive() {
    return (EAttribute)invoiceAndPropertyItemEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceAndPropertyItem_Remarks() {
    return (EAttribute)invoiceAndPropertyItemEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceAndPropertyItem_NumberOfItems() {
    return (EAttribute)invoiceAndPropertyItemEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvoiceAndPropertyItem_Pictures() {
    return (EReference)invoiceAndPropertyItemEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvoiceAndPropertyItem_Documents() {
    return (EReference)invoiceAndPropertyItemEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInvoiceAndProperty() {
    return invoiceAndPropertyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceAndProperty_Date() {
    return (EAttribute)invoiceAndPropertyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceAndProperty_Company() {
    return (EAttribute)invoiceAndPropertyEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvoiceAndProperty_Invoiceandpropertyitems() {
    return (EReference)invoiceAndPropertyEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getInvoiceAndProperty__GetTotalAmountInvoiceItems() {
    return invoiceAndPropertyEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvAndPropFactory getInvAndPropFactory() {
    return (InvAndPropFactory)getEFactoryInstance();
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
    invoicesAndPropertiesEClass = createEClass(INVOICES_AND_PROPERTIES);
    createEReference(invoicesAndPropertiesEClass, INVOICES_AND_PROPERTIES__INVOICSEANDPROPERTYS);

    invoiceAndPropertyItemEClass = createEClass(INVOICE_AND_PROPERTY_ITEM);
    createEAttribute(invoiceAndPropertyItemEClass, INVOICE_AND_PROPERTY_ITEM__TYPE);
    createEAttribute(invoiceAndPropertyItemEClass, INVOICE_AND_PROPERTY_ITEM__SERIAL_NUMBER);
    createEAttribute(invoiceAndPropertyItemEClass, INVOICE_AND_PROPERTY_ITEM__DESCRIPTION);
    createEAttribute(invoiceAndPropertyItemEClass, INVOICE_AND_PROPERTY_ITEM__BRAND);
    createEAttribute(invoiceAndPropertyItemEClass, INVOICE_AND_PROPERTY_ITEM__AMOUNT);
    createEAttribute(invoiceAndPropertyItemEClass, INVOICE_AND_PROPERTY_ITEM__FROM_DATE);
    createEAttribute(invoiceAndPropertyItemEClass, INVOICE_AND_PROPERTY_ITEM__UNTIL_DATE);
    createEAttribute(invoiceAndPropertyItemEClass, INVOICE_AND_PROPERTY_ITEM__ARCHIVE);
    createEAttribute(invoiceAndPropertyItemEClass, INVOICE_AND_PROPERTY_ITEM__REMARKS);
    createEAttribute(invoiceAndPropertyItemEClass, INVOICE_AND_PROPERTY_ITEM__NUMBER_OF_ITEMS);
    createEReference(invoiceAndPropertyItemEClass, INVOICE_AND_PROPERTY_ITEM__PICTURES);
    createEReference(invoiceAndPropertyItemEClass, INVOICE_AND_PROPERTY_ITEM__DOCUMENTS);

    invoiceAndPropertyEClass = createEClass(INVOICE_AND_PROPERTY);
    createEAttribute(invoiceAndPropertyEClass, INVOICE_AND_PROPERTY__DATE);
    createEAttribute(invoiceAndPropertyEClass, INVOICE_AND_PROPERTY__COMPANY);
    createEReference(invoiceAndPropertyEClass, INVOICE_AND_PROPERTY__INVOICEANDPROPERTYITEMS);
    createEOperation(invoiceAndPropertyEClass, INVOICE_AND_PROPERTY___GET_TOTAL_AMOUNT_INVOICE_ITEMS);
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

    // Obtain other dependent packages
    TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    invoiceAndPropertyEClass.getESuperTypes().add(this.getInvoiceAndPropertyItem());

    // Initialize classes, features, and operations; add parameters
    initEClass(invoicesAndPropertiesEClass, InvoicesAndProperties.class, "InvoicesAndProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getInvoicesAndProperties_Invoicseandpropertys(), this.getInvoiceAndProperty(), null, "invoicseandpropertys", null, 0, -1, InvoicesAndProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(invoiceAndPropertyItemEClass, InvoiceAndPropertyItem.class, "InvoiceAndPropertyItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getInvoiceAndPropertyItem_Type(), ecorePackage.getEString(), "type", null, 0, 1, InvoiceAndPropertyItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvoiceAndPropertyItem_SerialNumber(), ecorePackage.getEString(), "serialNumber", null, 0, 1, InvoiceAndPropertyItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvoiceAndPropertyItem_Description(), ecorePackage.getEString(), "description", null, 0, 1, InvoiceAndPropertyItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvoiceAndPropertyItem_Brand(), ecorePackage.getEString(), "brand", null, 0, 1, InvoiceAndPropertyItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvoiceAndPropertyItem_Amount(), theTypesPackage.getEMoney(), "amount", null, 0, 1, InvoiceAndPropertyItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvoiceAndPropertyItem_FromDate(), theTypesPackage.getEFlexDate(), "fromDate", null, 0, 1, InvoiceAndPropertyItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvoiceAndPropertyItem_UntilDate(), theTypesPackage.getEFlexDate(), "untilDate", null, 0, 1, InvoiceAndPropertyItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvoiceAndPropertyItem_Archive(), ecorePackage.getEBoolean(), "archive", "false", 1, 1, InvoiceAndPropertyItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvoiceAndPropertyItem_Remarks(), ecorePackage.getEString(), "remarks", null, 0, 1, InvoiceAndPropertyItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvoiceAndPropertyItem_NumberOfItems(), ecorePackage.getEInt(), "numberOfItems", "1", 1, 1, InvoiceAndPropertyItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvoiceAndPropertyItem_Pictures(), theTypesPackage.getFileReference(), null, "pictures", null, 0, -1, InvoiceAndPropertyItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvoiceAndPropertyItem_Documents(), theTypesPackage.getFileReference(), null, "documents", null, 0, -1, InvoiceAndPropertyItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(invoiceAndPropertyEClass, InvoiceAndProperty.class, "InvoiceAndProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getInvoiceAndProperty_Date(), theTypesPackage.getEFlexDate(), "date", null, 0, 1, InvoiceAndProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvoiceAndProperty_Company(), ecorePackage.getEString(), "company", null, 0, 1, InvoiceAndProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvoiceAndProperty_Invoiceandpropertyitems(), this.getInvoiceAndPropertyItem(), null, "invoiceandpropertyitems", null, 0, -1, InvoiceAndProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getInvoiceAndProperty__GetTotalAmountInvoiceItems(), theTypesPackage.getEMoney(), "getTotalAmountInvoiceItems", 0, 1, IS_UNIQUE, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //InvAndPropPackageImpl
