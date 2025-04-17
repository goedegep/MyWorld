/**
 */
package goedegep.invandprop.model.impl;

import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.InvoiceAndPropertyItem;

import goedegep.types.model.FileReference;
import goedegep.util.datetime.FlexDate;

import goedegep.util.money.PgCurrency;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Invoice And Property Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl#getType <em>Type</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl#getSerialNumber <em>Serial Number</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl#getBrand <em>Brand</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl#getAmount <em>Amount</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl#getFromDate <em>From Date</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl#getUntilDate <em>Until Date</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl#isArchive <em>Archive</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl#getRemarks <em>Remarks</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl#getNumberOfItems <em>Number Of Items</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl#getPictures <em>Pictures</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.InvoiceAndPropertyItemImpl#getDocuments <em>Documents</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvoiceAndPropertyItemImpl extends MinimalEObjectImpl.Container implements InvoiceAndPropertyItem {
  /**
   * The default value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected static final String TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected String type = TYPE_EDEFAULT;

  /**
   * This is true if the Type attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean typeESet;

  /**
   * The default value of the '{@link #getSerialNumber() <em>Serial Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSerialNumber()
   * @generated
   * @ordered
   */
  protected static final String SERIAL_NUMBER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSerialNumber() <em>Serial Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSerialNumber()
   * @generated
   * @ordered
   */
  protected String serialNumber = SERIAL_NUMBER_EDEFAULT;

  /**
   * This is true if the Serial Number attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean serialNumberESet;

  /**
   * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected static final String DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected String description = DESCRIPTION_EDEFAULT;

  /**
   * This is true if the Description attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean descriptionESet;

  /**
   * The default value of the '{@link #getBrand() <em>Brand</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBrand()
   * @generated
   * @ordered
   */
  protected static final String BRAND_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBrand() <em>Brand</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBrand()
   * @generated
   * @ordered
   */
  protected String brand = BRAND_EDEFAULT;

  /**
   * This is true if the Brand attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean brandESet;

  /**
   * The default value of the '{@link #getAmount() <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAmount()
   * @generated
   * @ordered
   */
  protected static final PgCurrency AMOUNT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getAmount() <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAmount()
   * @generated
   * @ordered
   */
  protected PgCurrency amount = AMOUNT_EDEFAULT;

  /**
   * This is true if the Amount attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean amountESet;

  /**
   * The default value of the '{@link #getFromDate() <em>From Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromDate()
   * @generated
   * @ordered
   */
  protected static final FlexDate FROM_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFromDate() <em>From Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFromDate()
   * @generated
   * @ordered
   */
  protected FlexDate fromDate = FROM_DATE_EDEFAULT;

  /**
   * This is true if the From Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean fromDateESet;

  /**
   * The default value of the '{@link #getUntilDate() <em>Until Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUntilDate()
   * @generated
   * @ordered
   */
  protected static final FlexDate UNTIL_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUntilDate() <em>Until Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUntilDate()
   * @generated
   * @ordered
   */
  protected FlexDate untilDate = UNTIL_DATE_EDEFAULT;

  /**
   * This is true if the Until Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean untilDateESet;

  /**
   * The default value of the '{@link #isArchive() <em>Archive</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isArchive()
   * @generated
   * @ordered
   */
  protected static final boolean ARCHIVE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isArchive() <em>Archive</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isArchive()
   * @generated
   * @ordered
   */
  protected boolean archive = ARCHIVE_EDEFAULT;

  /**
   * The default value of the '{@link #getRemarks() <em>Remarks</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRemarks()
   * @generated
   * @ordered
   */
  protected static final String REMARKS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRemarks() <em>Remarks</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRemarks()
   * @generated
   * @ordered
   */
  protected String remarks = REMARKS_EDEFAULT;

  /**
   * This is true if the Remarks attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean remarksESet;

  /**
   * The default value of the '{@link #getNumberOfItems() <em>Number Of Items</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfItems()
   * @generated
   * @ordered
   */
  protected static final int NUMBER_OF_ITEMS_EDEFAULT = 1;

  /**
   * The cached value of the '{@link #getNumberOfItems() <em>Number Of Items</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberOfItems()
   * @generated
   * @ordered
   */
  protected int numberOfItems = NUMBER_OF_ITEMS_EDEFAULT;

  /**
   * The cached value of the '{@link #getPictures() <em>Pictures</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPictures()
   * @generated
   * @ordered
   */
  protected EList<FileReference> pictures;

  /**
   * The cached value of the '{@link #getDocuments() <em>Documents</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDocuments()
   * @generated
   * @ordered
   */
  protected EList<FileReference> documents;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InvoiceAndPropertyItemImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return InvAndPropPackage.Literals.INVOICE_AND_PROPERTY_ITEM;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getType() {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(String newType) {
    String oldType = type;
    type = newType;
    boolean oldTypeESet = typeESet;
    typeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__TYPE, oldType, type, !oldTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetType() {
    String oldType = type;
    boolean oldTypeESet = typeESet;
    type = TYPE_EDEFAULT;
    typeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__TYPE, oldType, TYPE_EDEFAULT, oldTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetType() {
    return typeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSerialNumber(String newSerialNumber) {
    String oldSerialNumber = serialNumber;
    serialNumber = newSerialNumber;
    boolean oldSerialNumberESet = serialNumberESet;
    serialNumberESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__SERIAL_NUMBER, oldSerialNumber, serialNumber, !oldSerialNumberESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetSerialNumber() {
    String oldSerialNumber = serialNumber;
    boolean oldSerialNumberESet = serialNumberESet;
    serialNumber = SERIAL_NUMBER_EDEFAULT;
    serialNumberESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__SERIAL_NUMBER, oldSerialNumber, SERIAL_NUMBER_EDEFAULT, oldSerialNumberESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetSerialNumber() {
    return serialNumberESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDescription() {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(String newDescription) {
    String oldDescription = description;
    description = newDescription;
    boolean oldDescriptionESet = descriptionESet;
    descriptionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__DESCRIPTION, oldDescription, description, !oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetDescription() {
    String oldDescription = description;
    boolean oldDescriptionESet = descriptionESet;
    description = DESCRIPTION_EDEFAULT;
    descriptionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__DESCRIPTION, oldDescription, DESCRIPTION_EDEFAULT, oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetDescription() {
    return descriptionESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getBrand() {
    return brand;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBrand(String newBrand) {
    String oldBrand = brand;
    brand = newBrand;
    boolean oldBrandESet = brandESet;
    brandESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__BRAND, oldBrand, brand, !oldBrandESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetBrand() {
    String oldBrand = brand;
    boolean oldBrandESet = brandESet;
    brand = BRAND_EDEFAULT;
    brandESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__BRAND, oldBrand, BRAND_EDEFAULT, oldBrandESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetBrand() {
    return brandESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PgCurrency getAmount() {
    return amount;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAmount(PgCurrency newAmount) {
    PgCurrency oldAmount = amount;
    amount = newAmount;
    boolean oldAmountESet = amountESet;
    amountESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__AMOUNT, oldAmount, amount, !oldAmountESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetAmount() {
    PgCurrency oldAmount = amount;
    boolean oldAmountESet = amountESet;
    amount = AMOUNT_EDEFAULT;
    amountESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__AMOUNT, oldAmount, AMOUNT_EDEFAULT, oldAmountESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetAmount() {
    return amountESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FlexDate getFromDate() {
    return fromDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFromDate(FlexDate newFromDate) {
    FlexDate oldFromDate = fromDate;
    fromDate = newFromDate;
    boolean oldFromDateESet = fromDateESet;
    fromDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__FROM_DATE, oldFromDate, fromDate, !oldFromDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetFromDate() {
    FlexDate oldFromDate = fromDate;
    boolean oldFromDateESet = fromDateESet;
    fromDate = FROM_DATE_EDEFAULT;
    fromDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__FROM_DATE, oldFromDate, FROM_DATE_EDEFAULT, oldFromDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetFromDate() {
    return fromDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FlexDate getUntilDate() {
    return untilDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setUntilDate(FlexDate newUntilDate) {
    FlexDate oldUntilDate = untilDate;
    untilDate = newUntilDate;
    boolean oldUntilDateESet = untilDateESet;
    untilDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__UNTIL_DATE, oldUntilDate, untilDate, !oldUntilDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetUntilDate() {
    FlexDate oldUntilDate = untilDate;
    boolean oldUntilDateESet = untilDateESet;
    untilDate = UNTIL_DATE_EDEFAULT;
    untilDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__UNTIL_DATE, oldUntilDate, UNTIL_DATE_EDEFAULT, oldUntilDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetUntilDate() {
    return untilDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isArchive() {
    return archive;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setArchive(boolean newArchive) {
    boolean oldArchive = archive;
    archive = newArchive;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__ARCHIVE, oldArchive, archive));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getRemarks() {
    return remarks;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRemarks(String newRemarks) {
    String oldRemarks = remarks;
    remarks = newRemarks;
    boolean oldRemarksESet = remarksESet;
    remarksESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__REMARKS, oldRemarks, remarks, !oldRemarksESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetRemarks() {
    String oldRemarks = remarks;
    boolean oldRemarksESet = remarksESet;
    remarks = REMARKS_EDEFAULT;
    remarksESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__REMARKS, oldRemarks, REMARKS_EDEFAULT, oldRemarksESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetRemarks() {
    return remarksESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getNumberOfItems() {
    return numberOfItems;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNumberOfItems(int newNumberOfItems) {
    int oldNumberOfItems = numberOfItems;
    numberOfItems = newNumberOfItems;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__NUMBER_OF_ITEMS, oldNumberOfItems, numberOfItems));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FileReference> getPictures() {
    if (pictures == null) {
      pictures = new EObjectContainmentEList<FileReference>(FileReference.class, this, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__PICTURES);
    }
    return pictures;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FileReference> getDocuments() {
    if (documents == null) {
      documents = new EObjectContainmentEList<FileReference>(FileReference.class, this, InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__DOCUMENTS);
    }
    return documents;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__PICTURES:
        return ((InternalEList<?>)getPictures()).basicRemove(otherEnd, msgs);
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__DOCUMENTS:
        return ((InternalEList<?>)getDocuments()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__TYPE:
        return getType();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__SERIAL_NUMBER:
        return getSerialNumber();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__DESCRIPTION:
        return getDescription();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__BRAND:
        return getBrand();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__AMOUNT:
        return getAmount();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__FROM_DATE:
        return getFromDate();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__UNTIL_DATE:
        return getUntilDate();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__ARCHIVE:
        return isArchive();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__REMARKS:
        return getRemarks();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__NUMBER_OF_ITEMS:
        return getNumberOfItems();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__PICTURES:
        return getPictures();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__DOCUMENTS:
        return getDocuments();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__TYPE:
        setType((String)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__SERIAL_NUMBER:
        setSerialNumber((String)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__BRAND:
        setBrand((String)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__AMOUNT:
        setAmount((PgCurrency)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__FROM_DATE:
        setFromDate((FlexDate)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__UNTIL_DATE:
        setUntilDate((FlexDate)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__ARCHIVE:
        setArchive((Boolean)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__REMARKS:
        setRemarks((String)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__NUMBER_OF_ITEMS:
        setNumberOfItems((Integer)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__PICTURES:
        getPictures().clear();
        getPictures().addAll((Collection<? extends FileReference>)newValue);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__DOCUMENTS:
        getDocuments().clear();
        getDocuments().addAll((Collection<? extends FileReference>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__TYPE:
        unsetType();
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__SERIAL_NUMBER:
        unsetSerialNumber();
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__DESCRIPTION:
        unsetDescription();
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__BRAND:
        unsetBrand();
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__AMOUNT:
        unsetAmount();
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__FROM_DATE:
        unsetFromDate();
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__UNTIL_DATE:
        unsetUntilDate();
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__ARCHIVE:
        setArchive(ARCHIVE_EDEFAULT);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__REMARKS:
        unsetRemarks();
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__NUMBER_OF_ITEMS:
        setNumberOfItems(NUMBER_OF_ITEMS_EDEFAULT);
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__PICTURES:
        getPictures().clear();
        return;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__DOCUMENTS:
        getDocuments().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__TYPE:
        return isSetType();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__SERIAL_NUMBER:
        return isSetSerialNumber();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__DESCRIPTION:
        return isSetDescription();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__BRAND:
        return isSetBrand();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__AMOUNT:
        return isSetAmount();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__FROM_DATE:
        return isSetFromDate();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__UNTIL_DATE:
        return isSetUntilDate();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__ARCHIVE:
        return archive != ARCHIVE_EDEFAULT;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__REMARKS:
        return isSetRemarks();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__NUMBER_OF_ITEMS:
        return numberOfItems != NUMBER_OF_ITEMS_EDEFAULT;
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__PICTURES:
        return pictures != null && !pictures.isEmpty();
      case InvAndPropPackage.INVOICE_AND_PROPERTY_ITEM__DOCUMENTS:
        return documents != null && !documents.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (type: ");
    if (typeESet) result.append(type); else result.append("<unset>");
    result.append(", serialNumber: ");
    if (serialNumberESet) result.append(serialNumber); else result.append("<unset>");
    result.append(", description: ");
    if (descriptionESet) result.append(description); else result.append("<unset>");
    result.append(", brand: ");
    if (brandESet) result.append(brand); else result.append("<unset>");
    result.append(", amount: ");
    if (amountESet) result.append(amount); else result.append("<unset>");
    result.append(", fromDate: ");
    if (fromDateESet) result.append(fromDate); else result.append("<unset>");
    result.append(", untilDate: ");
    if (untilDateESet) result.append(untilDate); else result.append("<unset>");
    result.append(", archive: ");
    result.append(archive);
    result.append(", remarks: ");
    if (remarksESet) result.append(remarks); else result.append("<unset>");
    result.append(", numberOfItems: ");
    result.append(numberOfItems);
    result.append(')');
    return result.toString();
  }

} //InvoiceAndPropertyItemImpl
