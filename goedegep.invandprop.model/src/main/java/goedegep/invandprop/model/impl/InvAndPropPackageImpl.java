/**
 */
package goedegep.invandprop.model.impl;

import goedegep.invandprop.model.Expenditure;
import goedegep.invandprop.model.InvAndPropFactory;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.Invoice;
import goedegep.invandprop.model.InvoiceAndProperty;
import goedegep.invandprop.model.InvoiceAndPropertyItem;
import goedegep.invandprop.model.InvoiceItem;
import goedegep.invandprop.model.Invoices;
import goedegep.invandprop.model.InvoicesAndProperties;

import goedegep.invandprop.model.Properties;
import goedegep.invandprop.model.Property;
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
  private EClass invoicesEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass invoiceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass expenditureEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass invoiceItemEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass propertiesEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass propertyEClass = null;

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
  public EReference getInvoicesAndProperties_Invoices() {
    return (EReference)invoicesAndPropertiesEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvoicesAndProperties_Properties() {
    return (EReference)invoicesAndPropertiesEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvoicesAndProperties_Invoicseandpropertys() {
    return (EReference)invoicesAndPropertiesEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInvoices() {
    return invoicesEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvoices_Invoices() {
    return (EReference)invoicesEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInvoice() {
    return invoiceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoice_Date() {
    return (EAttribute)invoiceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoice_Company() {
    return (EAttribute)invoiceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvoice_InvoiceItems() {
    return (EReference)invoiceEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getInvoice_Documents() {
    return (EReference)invoiceEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EOperation getInvoice__GetTotalAmountInvoiceItems() {
    return invoiceEClass.getEOperations().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getExpenditure() {
    return expenditureEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExpenditure_Description() {
    return (EAttribute)expenditureEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExpenditure_Amount() {
    return (EAttribute)expenditureEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExpenditure_Remarks() {
    return (EAttribute)expenditureEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExpenditure_DescriptionFromProperty() {
    return (EAttribute)expenditureEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getExpenditure_Purchase() {
    return (EReference)expenditureEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInvoiceItem() {
    return invoiceItemEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getInvoiceItem_NumberOfItems() {
    return (EAttribute)invoiceItemEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getProperties() {
    return propertiesEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getProperties_Properties() {
    return (EReference)propertiesEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getProperty() {
    return propertyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getProperty_Expenditure() {
    return (EReference)propertyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperty_Description() {
    return (EAttribute)propertyEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperty_Brand() {
    return (EAttribute)propertyEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperty_Type() {
    return (EAttribute)propertyEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperty_SerialNumber() {
    return (EAttribute)propertyEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperty_Remarks() {
    return (EAttribute)propertyEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperty_FromDate() {
    return (EAttribute)propertyEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperty_UntilDate() {
    return (EAttribute)propertyEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperty_Archive() {
    return (EAttribute)propertyEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getProperty_Documents() {
    return (EReference)propertyEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getProperty_Pictures() {
    return (EReference)propertyEClass.getEStructuralFeatures().get(10);
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
    createEReference(invoicesAndPropertiesEClass, INVOICES_AND_PROPERTIES__INVOICES);
    createEReference(invoicesAndPropertiesEClass, INVOICES_AND_PROPERTIES__PROPERTIES);
    createEReference(invoicesAndPropertiesEClass, INVOICES_AND_PROPERTIES__INVOICSEANDPROPERTYS);

    invoicesEClass = createEClass(INVOICES);
    createEReference(invoicesEClass, INVOICES__INVOICES);

    invoiceEClass = createEClass(INVOICE);
    createEAttribute(invoiceEClass, INVOICE__DATE);
    createEAttribute(invoiceEClass, INVOICE__COMPANY);
    createEReference(invoiceEClass, INVOICE__INVOICE_ITEMS);
    createEReference(invoiceEClass, INVOICE__DOCUMENTS);
    createEOperation(invoiceEClass, INVOICE___GET_TOTAL_AMOUNT_INVOICE_ITEMS);

    expenditureEClass = createEClass(EXPENDITURE);
    createEAttribute(expenditureEClass, EXPENDITURE__DESCRIPTION);
    createEAttribute(expenditureEClass, EXPENDITURE__AMOUNT);
    createEAttribute(expenditureEClass, EXPENDITURE__REMARKS);
    createEAttribute(expenditureEClass, EXPENDITURE__DESCRIPTION_FROM_PROPERTY);
    createEReference(expenditureEClass, EXPENDITURE__PURCHASE);

    invoiceItemEClass = createEClass(INVOICE_ITEM);
    createEAttribute(invoiceItemEClass, INVOICE_ITEM__NUMBER_OF_ITEMS);

    propertiesEClass = createEClass(PROPERTIES);
    createEReference(propertiesEClass, PROPERTIES__PROPERTIES);

    propertyEClass = createEClass(PROPERTY);
    createEReference(propertyEClass, PROPERTY__EXPENDITURE);
    createEAttribute(propertyEClass, PROPERTY__DESCRIPTION);
    createEAttribute(propertyEClass, PROPERTY__BRAND);
    createEAttribute(propertyEClass, PROPERTY__TYPE);
    createEAttribute(propertyEClass, PROPERTY__SERIAL_NUMBER);
    createEAttribute(propertyEClass, PROPERTY__REMARKS);
    createEAttribute(propertyEClass, PROPERTY__FROM_DATE);
    createEAttribute(propertyEClass, PROPERTY__UNTIL_DATE);
    createEAttribute(propertyEClass, PROPERTY__ARCHIVE);
    createEReference(propertyEClass, PROPERTY__DOCUMENTS);
    createEReference(propertyEClass, PROPERTY__PICTURES);

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
    invoiceEClass.getESuperTypes().add(this.getExpenditure());
    invoiceItemEClass.getESuperTypes().add(this.getExpenditure());
    invoiceAndPropertyEClass.getESuperTypes().add(this.getInvoiceAndPropertyItem());

    // Initialize classes, features, and operations; add parameters
    initEClass(invoicesAndPropertiesEClass, InvoicesAndProperties.class, "InvoicesAndProperties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getInvoicesAndProperties_Invoices(), this.getInvoices(), null, "invoices", null, 1, 1, InvoicesAndProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvoicesAndProperties_Properties(), this.getProperties(), null, "properties", null, 1, 1, InvoicesAndProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvoicesAndProperties_Invoicseandpropertys(), this.getInvoiceAndProperty(), null, "invoicseandpropertys", null, 0, -1, InvoicesAndProperties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(invoicesEClass, Invoices.class, "Invoices", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getInvoices_Invoices(), this.getInvoice(), null, "invoices", null, 0, -1, Invoices.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

    initEClass(invoiceEClass, Invoice.class, "Invoice", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getInvoice_Date(), theTypesPackage.getEFlexDate(), "date", null, 0, 1, Invoice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getInvoice_Company(), ecorePackage.getEString(), "company", null, 0, 1, Invoice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvoice_InvoiceItems(), this.getInvoiceItem(), null, "invoiceItems", null, 0, -1, Invoice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getInvoice_Documents(), theTypesPackage.getFileReference(), null, "documents", null, 0, -1, Invoice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEOperation(getInvoice__GetTotalAmountInvoiceItems(), theTypesPackage.getEMoney(), "getTotalAmountInvoiceItems", 0, 1, IS_UNIQUE, IS_ORDERED);

    initEClass(expenditureEClass, Expenditure.class, "Expenditure", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getExpenditure_Description(), ecorePackage.getEString(), "description", null, 0, 1, Expenditure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExpenditure_Amount(), theTypesPackage.getEMoney(), "amount", null, 0, 1, Expenditure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExpenditure_Remarks(), ecorePackage.getEString(), "remarks", null, 0, 1, Expenditure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExpenditure_DescriptionFromProperty(), ecorePackage.getEBoolean(), "descriptionFromProperty", "true", 0, 1, Expenditure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getExpenditure_Purchase(), this.getProperty(), this.getProperty_Expenditure(), "purchase", null, 0, 1, Expenditure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(invoiceItemEClass, InvoiceItem.class, "InvoiceItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getInvoiceItem_NumberOfItems(), ecorePackage.getEInt(), "numberOfItems", "1", 1, 1, InvoiceItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(propertiesEClass, Properties.class, "Properties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getProperties_Properties(), this.getProperty(), null, "properties", null, 0, -1, Properties.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getProperty_Expenditure(), this.getExpenditure(), this.getExpenditure_Purchase(), "expenditure", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProperty_Description(), ecorePackage.getEString(), "description", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProperty_Brand(), ecorePackage.getEString(), "brand", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProperty_Type(), ecorePackage.getEString(), "type", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProperty_SerialNumber(), ecorePackage.getEString(), "serialNumber", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProperty_Remarks(), ecorePackage.getEString(), "remarks", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProperty_FromDate(), theTypesPackage.getEFlexDate(), "fromDate", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProperty_UntilDate(), theTypesPackage.getEFlexDate(), "untilDate", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProperty_Archive(), ecorePackage.getEBoolean(), "archive", "false", 1, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getProperty_Documents(), theTypesPackage.getFileReference(), null, "documents", null, 0, -1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getProperty_Pictures(), theTypesPackage.getFileReference(), null, "pictures", null, 0, -1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
