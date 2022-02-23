/**
 */
package goedegep.invandprop.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
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
 * @see goedegep.invandprop.model.InvAndPropFactory
 * @model kind="package"
 * @generated
 */
public interface InvAndPropPackage extends EPackage {
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
  String eNS_URI = "http://goedegep.invandprop/model";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "invandprop";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  InvAndPropPackage eINSTANCE = goedegep.invandprop.model.impl.InvAndPropPackageImpl.init();

  /**
   * The meta object id for the '{@link goedegep.invandprop.model.impl.InvoicesAndPropertiesImpl <em>Invoices And Properties</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.invandprop.model.impl.InvoicesAndPropertiesImpl
   * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getInvoicesAndProperties()
   * @generated
   */
  int INVOICES_AND_PROPERTIES = 0;

  /**
   * The feature id for the '<em><b>Invoices</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICES_AND_PROPERTIES__INVOICES = 0;

  /**
   * The feature id for the '<em><b>Properties</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICES_AND_PROPERTIES__PROPERTIES = 1;

  /**
   * The number of structural features of the '<em>Invoices And Properties</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICES_AND_PROPERTIES_FEATURE_COUNT = 2;

  /**
   * The number of operations of the '<em>Invoices And Properties</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICES_AND_PROPERTIES_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.invandprop.model.impl.InvoicesImpl <em>Invoices</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.invandprop.model.impl.InvoicesImpl
   * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getInvoices()
   * @generated
   */
  int INVOICES = 1;

  /**
   * The feature id for the '<em><b>Invoices</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICES__INVOICES = 0;

  /**
   * The number of structural features of the '<em>Invoices</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICES_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Invoices</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICES_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.invandprop.model.impl.ExpenditureImpl <em>Expenditure</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.invandprop.model.impl.ExpenditureImpl
   * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getExpenditure()
   * @generated
   */
  int EXPENDITURE = 3;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPENDITURE__DESCRIPTION = 0;

  /**
   * The feature id for the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPENDITURE__AMOUNT = 1;

  /**
   * The feature id for the '<em><b>Remarks</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPENDITURE__REMARKS = 2;

  /**
   * The feature id for the '<em><b>Description From Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPENDITURE__DESCRIPTION_FROM_PROPERTY = 3;

  /**
   * The feature id for the '<em><b>Purchase</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPENDITURE__PURCHASE = 4;

  /**
   * The number of structural features of the '<em>Expenditure</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPENDITURE_FEATURE_COUNT = 5;

  /**
   * The number of operations of the '<em>Expenditure</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPENDITURE_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.invandprop.model.impl.InvoiceImpl <em>Invoice</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.invandprop.model.impl.InvoiceImpl
   * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getInvoice()
   * @generated
   */
  int INVOICE = 2;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE__DESCRIPTION = EXPENDITURE__DESCRIPTION;

  /**
   * The feature id for the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE__AMOUNT = EXPENDITURE__AMOUNT;

  /**
   * The feature id for the '<em><b>Remarks</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE__REMARKS = EXPENDITURE__REMARKS;

  /**
   * The feature id for the '<em><b>Description From Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE__DESCRIPTION_FROM_PROPERTY = EXPENDITURE__DESCRIPTION_FROM_PROPERTY;

  /**
   * The feature id for the '<em><b>Purchase</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE__PURCHASE = EXPENDITURE__PURCHASE;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE__DATE = EXPENDITURE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Company</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE__COMPANY = EXPENDITURE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Invoice Items</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE__INVOICE_ITEMS = EXPENDITURE_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Documents</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE__DOCUMENTS = EXPENDITURE_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Invoice</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_FEATURE_COUNT = EXPENDITURE_FEATURE_COUNT + 4;

  /**
   * The operation id for the '<em>Get Total Amount Invoice Items</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE___GET_TOTAL_AMOUNT_INVOICE_ITEMS = EXPENDITURE_OPERATION_COUNT + 0;

  /**
   * The number of operations of the '<em>Invoice</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_OPERATION_COUNT = EXPENDITURE_OPERATION_COUNT + 1;


  /**
   * The meta object id for the '{@link goedegep.invandprop.model.impl.InvoiceItemImpl <em>Invoice Item</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.invandprop.model.impl.InvoiceItemImpl
   * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getInvoiceItem()
   * @generated
   */
  int INVOICE_ITEM = 4;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_ITEM__DESCRIPTION = EXPENDITURE__DESCRIPTION;

  /**
   * The feature id for the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_ITEM__AMOUNT = EXPENDITURE__AMOUNT;

  /**
   * The feature id for the '<em><b>Remarks</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_ITEM__REMARKS = EXPENDITURE__REMARKS;

  /**
   * The feature id for the '<em><b>Description From Property</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_ITEM__DESCRIPTION_FROM_PROPERTY = EXPENDITURE__DESCRIPTION_FROM_PROPERTY;

  /**
   * The feature id for the '<em><b>Purchase</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_ITEM__PURCHASE = EXPENDITURE__PURCHASE;

  /**
   * The feature id for the '<em><b>Number Of Items</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_ITEM__NUMBER_OF_ITEMS = EXPENDITURE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Invoice Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_ITEM_FEATURE_COUNT = EXPENDITURE_FEATURE_COUNT + 1;

  /**
   * The number of operations of the '<em>Invoice Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_ITEM_OPERATION_COUNT = EXPENDITURE_OPERATION_COUNT + 0;

  /**
   * The meta object id for the '{@link goedegep.invandprop.model.impl.PropertiesImpl <em>Properties</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.invandprop.model.impl.PropertiesImpl
   * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getProperties()
   * @generated
   */
  int PROPERTIES = 5;

  /**
   * The feature id for the '<em><b>Properties</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTIES__PROPERTIES = 0;

  /**
   * The number of structural features of the '<em>Properties</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTIES_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Properties</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTIES_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.invandprop.model.impl.PropertyImpl <em>Property</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.invandprop.model.impl.PropertyImpl
   * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getProperty()
   * @generated
   */
  int PROPERTY = 6;

  /**
   * The feature id for the '<em><b>Expenditure</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__EXPENDITURE = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__DESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>Brand</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__BRAND = 2;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__TYPE = 3;

  /**
   * The feature id for the '<em><b>Serial Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__SERIAL_NUMBER = 4;

  /**
   * The feature id for the '<em><b>Remarks</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__REMARKS = 5;

  /**
   * The feature id for the '<em><b>From Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__FROM_DATE = 6;

  /**
   * The feature id for the '<em><b>Until Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__UNTIL_DATE = 7;

  /**
   * The feature id for the '<em><b>Archive</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__ARCHIVE = 8;

  /**
   * The feature id for the '<em><b>Documents</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__DOCUMENTS = 9;

  /**
   * The feature id for the '<em><b>Pictures</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__PICTURES = 10;

  /**
   * The number of structural features of the '<em>Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_FEATURE_COUNT = 11;

  /**
   * The number of operations of the '<em>Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_OPERATION_COUNT = 0;


  /**
   * Returns the meta object for class '{@link goedegep.invandprop.model.InvoicesAndProperties <em>Invoices And Properties</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Invoices And Properties</em>'.
   * @see goedegep.invandprop.model.InvoicesAndProperties
   * @generated
   */
  EClass getInvoicesAndProperties();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.invandprop.model.InvoicesAndProperties#getInvoices <em>Invoices</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Invoices</em>'.
   * @see goedegep.invandprop.model.InvoicesAndProperties#getInvoices()
   * @see #getInvoicesAndProperties()
   * @generated
   */
  EReference getInvoicesAndProperties_Invoices();

  /**
   * Returns the meta object for the containment reference '{@link goedegep.invandprop.model.InvoicesAndProperties#getProperties <em>Properties</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Properties</em>'.
   * @see goedegep.invandprop.model.InvoicesAndProperties#getProperties()
   * @see #getInvoicesAndProperties()
   * @generated
   */
  EReference getInvoicesAndProperties_Properties();

  /**
   * Returns the meta object for class '{@link goedegep.invandprop.model.Invoices <em>Invoices</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Invoices</em>'.
   * @see goedegep.invandprop.model.Invoices
   * @generated
   */
  EClass getInvoices();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.invandprop.model.Invoices#getInvoices <em>Invoices</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Invoices</em>'.
   * @see goedegep.invandprop.model.Invoices#getInvoices()
   * @see #getInvoices()
   * @generated
   */
  EReference getInvoices_Invoices();

  /**
   * Returns the meta object for class '{@link goedegep.invandprop.model.Invoice <em>Invoice</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Invoice</em>'.
   * @see goedegep.invandprop.model.Invoice
   * @generated
   */
  EClass getInvoice();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Invoice#getDate <em>Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Date</em>'.
   * @see goedegep.invandprop.model.Invoice#getDate()
   * @see #getInvoice()
   * @generated
   */
  EAttribute getInvoice_Date();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Invoice#getCompany <em>Company</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Company</em>'.
   * @see goedegep.invandprop.model.Invoice#getCompany()
   * @see #getInvoice()
   * @generated
   */
  EAttribute getInvoice_Company();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.invandprop.model.Invoice#getInvoiceItems <em>Invoice Items</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Invoice Items</em>'.
   * @see goedegep.invandprop.model.Invoice#getInvoiceItems()
   * @see #getInvoice()
   * @generated
   */
  EReference getInvoice_InvoiceItems();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.invandprop.model.Invoice#getDocuments <em>Documents</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Documents</em>'.
   * @see goedegep.invandprop.model.Invoice#getDocuments()
   * @see #getInvoice()
   * @generated
   */
  EReference getInvoice_Documents();

  /**
   * Returns the meta object for the '{@link goedegep.invandprop.model.Invoice#getTotalAmountInvoiceItems() <em>Get Total Amount Invoice Items</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Total Amount Invoice Items</em>' operation.
   * @see goedegep.invandprop.model.Invoice#getTotalAmountInvoiceItems()
   * @generated
   */
  EOperation getInvoice__GetTotalAmountInvoiceItems();

  /**
   * Returns the meta object for class '{@link goedegep.invandprop.model.Expenditure <em>Expenditure</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expenditure</em>'.
   * @see goedegep.invandprop.model.Expenditure
   * @generated
   */
  EClass getExpenditure();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Expenditure#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.invandprop.model.Expenditure#getDescription()
   * @see #getExpenditure()
   * @generated
   */
  EAttribute getExpenditure_Description();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Expenditure#getAmount <em>Amount</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Amount</em>'.
   * @see goedegep.invandprop.model.Expenditure#getAmount()
   * @see #getExpenditure()
   * @generated
   */
  EAttribute getExpenditure_Amount();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Expenditure#getRemarks <em>Remarks</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Remarks</em>'.
   * @see goedegep.invandprop.model.Expenditure#getRemarks()
   * @see #getExpenditure()
   * @generated
   */
  EAttribute getExpenditure_Remarks();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Expenditure#isDescriptionFromProperty <em>Description From Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description From Property</em>'.
   * @see goedegep.invandprop.model.Expenditure#isDescriptionFromProperty()
   * @see #getExpenditure()
   * @generated
   */
  EAttribute getExpenditure_DescriptionFromProperty();

  /**
   * Returns the meta object for the reference '{@link goedegep.invandprop.model.Expenditure#getPurchase <em>Purchase</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Purchase</em>'.
   * @see goedegep.invandprop.model.Expenditure#getPurchase()
   * @see #getExpenditure()
   * @generated
   */
  EReference getExpenditure_Purchase();

  /**
   * Returns the meta object for class '{@link goedegep.invandprop.model.InvoiceItem <em>Invoice Item</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Invoice Item</em>'.
   * @see goedegep.invandprop.model.InvoiceItem
   * @generated
   */
  EClass getInvoiceItem();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceItem#getNumberOfItems <em>Number Of Items</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Number Of Items</em>'.
   * @see goedegep.invandprop.model.InvoiceItem#getNumberOfItems()
   * @see #getInvoiceItem()
   * @generated
   */
  EAttribute getInvoiceItem_NumberOfItems();

  /**
   * Returns the meta object for class '{@link goedegep.invandprop.model.Properties <em>Properties</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Properties</em>'.
   * @see goedegep.invandprop.model.Properties
   * @generated
   */
  EClass getProperties();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.invandprop.model.Properties#getProperties <em>Properties</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Properties</em>'.
   * @see goedegep.invandprop.model.Properties#getProperties()
   * @see #getProperties()
   * @generated
   */
  EReference getProperties_Properties();

  /**
   * Returns the meta object for class '{@link goedegep.invandprop.model.Property <em>Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Property</em>'.
   * @see goedegep.invandprop.model.Property
   * @generated
   */
  EClass getProperty();

  /**
   * Returns the meta object for the reference '{@link goedegep.invandprop.model.Property#getExpenditure <em>Expenditure</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Expenditure</em>'.
   * @see goedegep.invandprop.model.Property#getExpenditure()
   * @see #getProperty()
   * @generated
   */
  EReference getProperty_Expenditure();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Property#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.invandprop.model.Property#getDescription()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Description();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Property#getBrand <em>Brand</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Brand</em>'.
   * @see goedegep.invandprop.model.Property#getBrand()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Brand();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Property#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see goedegep.invandprop.model.Property#getType()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Type();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Property#getSerialNumber <em>Serial Number</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Serial Number</em>'.
   * @see goedegep.invandprop.model.Property#getSerialNumber()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_SerialNumber();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Property#getRemarks <em>Remarks</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Remarks</em>'.
   * @see goedegep.invandprop.model.Property#getRemarks()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Remarks();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Property#getFromDate <em>From Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>From Date</em>'.
   * @see goedegep.invandprop.model.Property#getFromDate()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_FromDate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Property#getUntilDate <em>Until Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Until Date</em>'.
   * @see goedegep.invandprop.model.Property#getUntilDate()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_UntilDate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.Property#isArchive <em>Archive</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Archive</em>'.
   * @see goedegep.invandprop.model.Property#isArchive()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Archive();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.invandprop.model.Property#getDocuments <em>Documents</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Documents</em>'.
   * @see goedegep.invandprop.model.Property#getDocuments()
   * @see #getProperty()
   * @generated
   */
  EReference getProperty_Documents();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.invandprop.model.Property#getPictures <em>Pictures</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Pictures</em>'.
   * @see goedegep.invandprop.model.Property#getPictures()
   * @see #getProperty()
   * @generated
   */
  EReference getProperty_Pictures();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  InvAndPropFactory getInvAndPropFactory();

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
     * The meta object literal for the '{@link goedegep.invandprop.model.impl.InvoicesAndPropertiesImpl <em>Invoices And Properties</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.invandprop.model.impl.InvoicesAndPropertiesImpl
     * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getInvoicesAndProperties()
     * @generated
     */
    EClass INVOICES_AND_PROPERTIES = eINSTANCE.getInvoicesAndProperties();

    /**
     * The meta object literal for the '<em><b>Invoices</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVOICES_AND_PROPERTIES__INVOICES = eINSTANCE.getInvoicesAndProperties_Invoices();

    /**
     * The meta object literal for the '<em><b>Properties</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVOICES_AND_PROPERTIES__PROPERTIES = eINSTANCE.getInvoicesAndProperties_Properties();

    /**
     * The meta object literal for the '{@link goedegep.invandprop.model.impl.InvoicesImpl <em>Invoices</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.invandprop.model.impl.InvoicesImpl
     * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getInvoices()
     * @generated
     */
    EClass INVOICES = eINSTANCE.getInvoices();

    /**
     * The meta object literal for the '<em><b>Invoices</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVOICES__INVOICES = eINSTANCE.getInvoices_Invoices();

    /**
     * The meta object literal for the '{@link goedegep.invandprop.model.impl.InvoiceImpl <em>Invoice</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.invandprop.model.impl.InvoiceImpl
     * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getInvoice()
     * @generated
     */
    EClass INVOICE = eINSTANCE.getInvoice();

    /**
     * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE__DATE = eINSTANCE.getInvoice_Date();

    /**
     * The meta object literal for the '<em><b>Company</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE__COMPANY = eINSTANCE.getInvoice_Company();

    /**
     * The meta object literal for the '<em><b>Invoice Items</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVOICE__INVOICE_ITEMS = eINSTANCE.getInvoice_InvoiceItems();

    /**
     * The meta object literal for the '<em><b>Documents</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVOICE__DOCUMENTS = eINSTANCE.getInvoice_Documents();

    /**
     * The meta object literal for the '<em><b>Get Total Amount Invoice Items</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation INVOICE___GET_TOTAL_AMOUNT_INVOICE_ITEMS = eINSTANCE.getInvoice__GetTotalAmountInvoiceItems();

    /**
     * The meta object literal for the '{@link goedegep.invandprop.model.impl.ExpenditureImpl <em>Expenditure</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.invandprop.model.impl.ExpenditureImpl
     * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getExpenditure()
     * @generated
     */
    EClass EXPENDITURE = eINSTANCE.getExpenditure();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPENDITURE__DESCRIPTION = eINSTANCE.getExpenditure_Description();

    /**
     * The meta object literal for the '<em><b>Amount</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPENDITURE__AMOUNT = eINSTANCE.getExpenditure_Amount();

    /**
     * The meta object literal for the '<em><b>Remarks</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPENDITURE__REMARKS = eINSTANCE.getExpenditure_Remarks();

    /**
     * The meta object literal for the '<em><b>Description From Property</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPENDITURE__DESCRIPTION_FROM_PROPERTY = eINSTANCE.getExpenditure_DescriptionFromProperty();

    /**
     * The meta object literal for the '<em><b>Purchase</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPENDITURE__PURCHASE = eINSTANCE.getExpenditure_Purchase();

    /**
     * The meta object literal for the '{@link goedegep.invandprop.model.impl.InvoiceItemImpl <em>Invoice Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.invandprop.model.impl.InvoiceItemImpl
     * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getInvoiceItem()
     * @generated
     */
    EClass INVOICE_ITEM = eINSTANCE.getInvoiceItem();

    /**
     * The meta object literal for the '<em><b>Number Of Items</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_ITEM__NUMBER_OF_ITEMS = eINSTANCE.getInvoiceItem_NumberOfItems();

    /**
     * The meta object literal for the '{@link goedegep.invandprop.model.impl.PropertiesImpl <em>Properties</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.invandprop.model.impl.PropertiesImpl
     * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getProperties()
     * @generated
     */
    EClass PROPERTIES = eINSTANCE.getProperties();

    /**
     * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROPERTIES__PROPERTIES = eINSTANCE.getProperties_Properties();

    /**
     * The meta object literal for the '{@link goedegep.invandprop.model.impl.PropertyImpl <em>Property</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.invandprop.model.impl.PropertyImpl
     * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getProperty()
     * @generated
     */
    EClass PROPERTY = eINSTANCE.getProperty();

    /**
     * The meta object literal for the '<em><b>Expenditure</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROPERTY__EXPENDITURE = eINSTANCE.getProperty_Expenditure();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__DESCRIPTION = eINSTANCE.getProperty_Description();

    /**
     * The meta object literal for the '<em><b>Brand</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__BRAND = eINSTANCE.getProperty_Brand();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__TYPE = eINSTANCE.getProperty_Type();

    /**
     * The meta object literal for the '<em><b>Serial Number</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__SERIAL_NUMBER = eINSTANCE.getProperty_SerialNumber();

    /**
     * The meta object literal for the '<em><b>Remarks</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__REMARKS = eINSTANCE.getProperty_Remarks();

    /**
     * The meta object literal for the '<em><b>From Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__FROM_DATE = eINSTANCE.getProperty_FromDate();

    /**
     * The meta object literal for the '<em><b>Until Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__UNTIL_DATE = eINSTANCE.getProperty_UntilDate();

    /**
     * The meta object literal for the '<em><b>Archive</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__ARCHIVE = eINSTANCE.getProperty_Archive();

    /**
     * The meta object literal for the '<em><b>Documents</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROPERTY__DOCUMENTS = eINSTANCE.getProperty_Documents();

    /**
     * The meta object literal for the '<em><b>Pictures</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROPERTY__PICTURES = eINSTANCE.getProperty_Pictures();

  }

} //InvAndPropPackage
