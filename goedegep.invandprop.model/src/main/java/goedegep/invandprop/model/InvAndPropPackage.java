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
   * The feature id for the '<em><b>Invoicseandpropertys</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICES_AND_PROPERTIES__INVOICSEANDPROPERTYS = 0;

  /**
   * The number of structural features of the '<em>Invoices And Properties</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICES_AND_PROPERTIES_FEATURE_COUNT = 1;

  /**
   * The number of operations of the '<em>Invoices And Properties</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICES_AND_PROPERTIES_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl <em>Invoice And Property Item</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl
   * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getInvoiceAndPropertyItem()
   * @generated
   */
  int INVOICE_AND_PROPERTY_ITEM = 1;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM__TYPE = 0;

  /**
   * The feature id for the '<em><b>Serial Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM__SERIAL_NUMBER = 1;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM__DESCRIPTION = 2;

  /**
   * The feature id for the '<em><b>Brand</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM__BRAND = 3;

  /**
   * The feature id for the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM__AMOUNT = 4;

  /**
   * The feature id for the '<em><b>From Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM__FROM_DATE = 5;

  /**
   * The feature id for the '<em><b>Until Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM__UNTIL_DATE = 6;

  /**
   * The feature id for the '<em><b>Archive</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM__ARCHIVE = 7;

  /**
   * The feature id for the '<em><b>Remarks</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM__REMARKS = 8;

  /**
   * The feature id for the '<em><b>Number Of Items</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM__NUMBER_OF_ITEMS = 9;

  /**
   * The feature id for the '<em><b>Pictures</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM__PICTURES = 10;

  /**
   * The feature id for the '<em><b>Documents</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM__DOCUMENTS = 11;

  /**
   * The number of structural features of the '<em>Invoice And Property Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM_FEATURE_COUNT = 12;

  /**
   * The number of operations of the '<em>Invoice And Property Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_ITEM_OPERATION_COUNT = 0;

  /**
   * The meta object id for the '{@link goedegep.invandprop.model.impl.InvoiceAndPropertyImpl <em>Invoice And Property</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see goedegep.invandprop.model.impl.InvoiceAndPropertyImpl
   * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getInvoiceAndProperty()
   * @generated
   */
  int INVOICE_AND_PROPERTY = 2;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__TYPE = INVOICE_AND_PROPERTY_ITEM__TYPE;

  /**
   * The feature id for the '<em><b>Serial Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__SERIAL_NUMBER = INVOICE_AND_PROPERTY_ITEM__SERIAL_NUMBER;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__DESCRIPTION = INVOICE_AND_PROPERTY_ITEM__DESCRIPTION;

  /**
   * The feature id for the '<em><b>Brand</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__BRAND = INVOICE_AND_PROPERTY_ITEM__BRAND;

  /**
   * The feature id for the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__AMOUNT = INVOICE_AND_PROPERTY_ITEM__AMOUNT;

  /**
   * The feature id for the '<em><b>From Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__FROM_DATE = INVOICE_AND_PROPERTY_ITEM__FROM_DATE;

  /**
   * The feature id for the '<em><b>Until Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__UNTIL_DATE = INVOICE_AND_PROPERTY_ITEM__UNTIL_DATE;

  /**
   * The feature id for the '<em><b>Archive</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__ARCHIVE = INVOICE_AND_PROPERTY_ITEM__ARCHIVE;

  /**
   * The feature id for the '<em><b>Remarks</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__REMARKS = INVOICE_AND_PROPERTY_ITEM__REMARKS;

  /**
   * The feature id for the '<em><b>Number Of Items</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__NUMBER_OF_ITEMS = INVOICE_AND_PROPERTY_ITEM__NUMBER_OF_ITEMS;

  /**
   * The feature id for the '<em><b>Pictures</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__PICTURES = INVOICE_AND_PROPERTY_ITEM__PICTURES;

  /**
   * The feature id for the '<em><b>Documents</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__DOCUMENTS = INVOICE_AND_PROPERTY_ITEM__DOCUMENTS;

  /**
   * The feature id for the '<em><b>Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__DATE = INVOICE_AND_PROPERTY_ITEM_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Company</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__COMPANY = INVOICE_AND_PROPERTY_ITEM_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Invoiceandpropertyitems</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY__INVOICEANDPROPERTYITEMS = INVOICE_AND_PROPERTY_ITEM_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Invoice And Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_FEATURE_COUNT = INVOICE_AND_PROPERTY_ITEM_FEATURE_COUNT + 3;

  /**
   * The operation id for the '<em>Get Total Amount Invoice Items</em>' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY___GET_TOTAL_AMOUNT_INVOICE_ITEMS = INVOICE_AND_PROPERTY_ITEM_OPERATION_COUNT + 0;

  /**
   * The number of operations of the '<em>Invoice And Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INVOICE_AND_PROPERTY_OPERATION_COUNT = INVOICE_AND_PROPERTY_ITEM_OPERATION_COUNT + 1;


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
   * Returns the meta object for the containment reference list '{@link goedegep.invandprop.model.InvoicesAndProperties#getInvoicseandpropertys <em>Invoicseandpropertys</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Invoicseandpropertys</em>'.
   * @see goedegep.invandprop.model.InvoicesAndProperties#getInvoicseandpropertys()
   * @see #getInvoicesAndProperties()
   * @generated
   */
  EReference getInvoicesAndProperties_Invoicseandpropertys();

  /**
   * Returns the meta object for class '{@link goedegep.invandprop.model.InvoiceAndPropertyItem <em>Invoice And Property Item</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Invoice And Property Item</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem
   * @generated
   */
  EClass getInvoiceAndPropertyItem();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem#getType()
   * @see #getInvoiceAndPropertyItem()
   * @generated
   */
  EAttribute getInvoiceAndPropertyItem_Type();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getSerialNumber <em>Serial Number</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Serial Number</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem#getSerialNumber()
   * @see #getInvoiceAndPropertyItem()
   * @generated
   */
  EAttribute getInvoiceAndPropertyItem_SerialNumber();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem#getDescription()
   * @see #getInvoiceAndPropertyItem()
   * @generated
   */
  EAttribute getInvoiceAndPropertyItem_Description();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getBrand <em>Brand</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Brand</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem#getBrand()
   * @see #getInvoiceAndPropertyItem()
   * @generated
   */
  EAttribute getInvoiceAndPropertyItem_Brand();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getAmount <em>Amount</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Amount</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem#getAmount()
   * @see #getInvoiceAndPropertyItem()
   * @generated
   */
  EAttribute getInvoiceAndPropertyItem_Amount();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getFromDate <em>From Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>From Date</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem#getFromDate()
   * @see #getInvoiceAndPropertyItem()
   * @generated
   */
  EAttribute getInvoiceAndPropertyItem_FromDate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getUntilDate <em>Until Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Until Date</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem#getUntilDate()
   * @see #getInvoiceAndPropertyItem()
   * @generated
   */
  EAttribute getInvoiceAndPropertyItem_UntilDate();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#isArchive <em>Archive</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Archive</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem#isArchive()
   * @see #getInvoiceAndPropertyItem()
   * @generated
   */
  EAttribute getInvoiceAndPropertyItem_Archive();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getRemarks <em>Remarks</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Remarks</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem#getRemarks()
   * @see #getInvoiceAndPropertyItem()
   * @generated
   */
  EAttribute getInvoiceAndPropertyItem_Remarks();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getNumberOfItems <em>Number Of Items</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Number Of Items</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem#getNumberOfItems()
   * @see #getInvoiceAndPropertyItem()
   * @generated
   */
  EAttribute getInvoiceAndPropertyItem_NumberOfItems();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getPictures <em>Pictures</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Pictures</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem#getPictures()
   * @see #getInvoiceAndPropertyItem()
   * @generated
   */
  EReference getInvoiceAndPropertyItem_Pictures();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.invandprop.model.InvoiceAndPropertyItem#getDocuments <em>Documents</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Documents</em>'.
   * @see goedegep.invandprop.model.InvoiceAndPropertyItem#getDocuments()
   * @see #getInvoiceAndPropertyItem()
   * @generated
   */
  EReference getInvoiceAndPropertyItem_Documents();

  /**
   * Returns the meta object for class '{@link goedegep.invandprop.model.InvoiceAndProperty <em>Invoice And Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Invoice And Property</em>'.
   * @see goedegep.invandprop.model.InvoiceAndProperty
   * @generated
   */
  EClass getInvoiceAndProperty();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceAndProperty#getDate <em>Date</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Date</em>'.
   * @see goedegep.invandprop.model.InvoiceAndProperty#getDate()
   * @see #getInvoiceAndProperty()
   * @generated
   */
  EAttribute getInvoiceAndProperty_Date();

  /**
   * Returns the meta object for the attribute '{@link goedegep.invandprop.model.InvoiceAndProperty#getCompany <em>Company</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Company</em>'.
   * @see goedegep.invandprop.model.InvoiceAndProperty#getCompany()
   * @see #getInvoiceAndProperty()
   * @generated
   */
  EAttribute getInvoiceAndProperty_Company();

  /**
   * Returns the meta object for the containment reference list '{@link goedegep.invandprop.model.InvoiceAndProperty#getInvoiceandpropertyitems <em>Invoiceandpropertyitems</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Invoiceandpropertyitems</em>'.
   * @see goedegep.invandprop.model.InvoiceAndProperty#getInvoiceandpropertyitems()
   * @see #getInvoiceAndProperty()
   * @generated
   */
  EReference getInvoiceAndProperty_Invoiceandpropertyitems();

  /**
   * Returns the meta object for the '{@link goedegep.invandprop.model.InvoiceAndProperty#getTotalAmountInvoiceItems() <em>Get Total Amount Invoice Items</em>}' operation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the '<em>Get Total Amount Invoice Items</em>' operation.
   * @see goedegep.invandprop.model.InvoiceAndProperty#getTotalAmountInvoiceItems()
   * @generated
   */
  EOperation getInvoiceAndProperty__GetTotalAmountInvoiceItems();

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
     * The meta object literal for the '<em><b>Invoicseandpropertys</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVOICES_AND_PROPERTIES__INVOICSEANDPROPERTYS = eINSTANCE.getInvoicesAndProperties_Invoicseandpropertys();

    /**
     * The meta object literal for the '{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl <em>Invoice And Property Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl
     * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getInvoiceAndPropertyItem()
     * @generated
     */
    EClass INVOICE_AND_PROPERTY_ITEM = eINSTANCE.getInvoiceAndPropertyItem();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_AND_PROPERTY_ITEM__TYPE = eINSTANCE.getInvoiceAndPropertyItem_Type();

    /**
     * The meta object literal for the '<em><b>Serial Number</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_AND_PROPERTY_ITEM__SERIAL_NUMBER = eINSTANCE.getInvoiceAndPropertyItem_SerialNumber();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_AND_PROPERTY_ITEM__DESCRIPTION = eINSTANCE.getInvoiceAndPropertyItem_Description();

    /**
     * The meta object literal for the '<em><b>Brand</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_AND_PROPERTY_ITEM__BRAND = eINSTANCE.getInvoiceAndPropertyItem_Brand();

    /**
     * The meta object literal for the '<em><b>Amount</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_AND_PROPERTY_ITEM__AMOUNT = eINSTANCE.getInvoiceAndPropertyItem_Amount();

    /**
     * The meta object literal for the '<em><b>From Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_AND_PROPERTY_ITEM__FROM_DATE = eINSTANCE.getInvoiceAndPropertyItem_FromDate();

    /**
     * The meta object literal for the '<em><b>Until Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_AND_PROPERTY_ITEM__UNTIL_DATE = eINSTANCE.getInvoiceAndPropertyItem_UntilDate();

    /**
     * The meta object literal for the '<em><b>Archive</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_AND_PROPERTY_ITEM__ARCHIVE = eINSTANCE.getInvoiceAndPropertyItem_Archive();

    /**
     * The meta object literal for the '<em><b>Remarks</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_AND_PROPERTY_ITEM__REMARKS = eINSTANCE.getInvoiceAndPropertyItem_Remarks();

    /**
     * The meta object literal for the '<em><b>Number Of Items</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_AND_PROPERTY_ITEM__NUMBER_OF_ITEMS = eINSTANCE.getInvoiceAndPropertyItem_NumberOfItems();

    /**
     * The meta object literal for the '<em><b>Pictures</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVOICE_AND_PROPERTY_ITEM__PICTURES = eINSTANCE.getInvoiceAndPropertyItem_Pictures();

    /**
     * The meta object literal for the '<em><b>Documents</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVOICE_AND_PROPERTY_ITEM__DOCUMENTS = eINSTANCE.getInvoiceAndPropertyItem_Documents();

    /**
     * The meta object literal for the '{@link goedegep.invandprop.model.impl.InvoiceAndPropertyImpl <em>Invoice And Property</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see goedegep.invandprop.model.impl.InvoiceAndPropertyImpl
     * @see goedegep.invandprop.model.impl.InvAndPropPackageImpl#getInvoiceAndProperty()
     * @generated
     */
    EClass INVOICE_AND_PROPERTY = eINSTANCE.getInvoiceAndProperty();

    /**
     * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_AND_PROPERTY__DATE = eINSTANCE.getInvoiceAndProperty_Date();

    /**
     * The meta object literal for the '<em><b>Company</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INVOICE_AND_PROPERTY__COMPANY = eINSTANCE.getInvoiceAndProperty_Company();

    /**
     * The meta object literal for the '<em><b>Invoiceandpropertyitems</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INVOICE_AND_PROPERTY__INVOICEANDPROPERTYITEMS = eINSTANCE.getInvoiceAndProperty_Invoiceandpropertyitems();

    /**
     * The meta object literal for the '<em><b>Get Total Amount Invoice Items</b></em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EOperation INVOICE_AND_PROPERTY___GET_TOTAL_AMOUNT_INVOICE_ITEMS = eINSTANCE.getInvoiceAndProperty__GetTotalAmountInvoiceItems();

  }

} //InvAndPropPackage
