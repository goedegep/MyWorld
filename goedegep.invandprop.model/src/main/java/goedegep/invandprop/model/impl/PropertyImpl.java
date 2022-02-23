/**
 */
package goedegep.invandprop.model.impl;

import goedegep.invandprop.model.Expenditure;
import goedegep.invandprop.model.InvAndPropPackage;
import goedegep.invandprop.model.Property;

import goedegep.types.model.FileReference;

import goedegep.util.datetime.FlexDate;

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
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.invandprop.model.impl.PropertyImpl#getExpenditure <em>Expenditure</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.PropertyImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.PropertyImpl#getBrand <em>Brand</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.PropertyImpl#getType <em>Type</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.PropertyImpl#getSerialNumber <em>Serial Number</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.PropertyImpl#getRemarks <em>Remarks</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.PropertyImpl#getFromDate <em>From Date</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.PropertyImpl#getUntilDate <em>Until Date</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.PropertyImpl#isArchive <em>Archive</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.PropertyImpl#getDocuments <em>Documents</em>}</li>
 *   <li>{@link goedegep.invandprop.model.impl.PropertyImpl#getPictures <em>Pictures</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropertyImpl extends MinimalEObjectImpl.Container implements Property {
  /**
   * The cached value of the '{@link #getExpenditure() <em>Expenditure</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpenditure()
   * @generated
   * @ordered
   */
  protected Expenditure expenditure;

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
   * The cached value of the '{@link #getDocuments() <em>Documents</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDocuments()
   * @generated
   * @ordered
   */
  protected EList<FileReference> documents;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PropertyImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return InvAndPropPackage.Literals.PROPERTY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expenditure getExpenditure() {
    if (expenditure != null && expenditure.eIsProxy()) {
      InternalEObject oldExpenditure = (InternalEObject)expenditure;
      expenditure = (Expenditure)eResolveProxy(oldExpenditure);
      if (expenditure != oldExpenditure) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, InvAndPropPackage.PROPERTY__EXPENDITURE, oldExpenditure, expenditure));
      }
    }
    return expenditure;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expenditure basicGetExpenditure() {
    return expenditure;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetExpenditure(Expenditure newExpenditure, NotificationChain msgs) {
    Expenditure oldExpenditure = expenditure;
    expenditure = newExpenditure;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InvAndPropPackage.PROPERTY__EXPENDITURE, oldExpenditure, newExpenditure);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExpenditure(Expenditure newExpenditure) {
    if (newExpenditure != expenditure) {
      NotificationChain msgs = null;
      if (expenditure != null)
        msgs = ((InternalEObject)expenditure).eInverseRemove(this, InvAndPropPackage.EXPENDITURE__PURCHASE, Expenditure.class, msgs);
      if (newExpenditure != null)
        msgs = ((InternalEObject)newExpenditure).eInverseAdd(this, InvAndPropPackage.EXPENDITURE__PURCHASE, Expenditure.class, msgs);
      msgs = basicSetExpenditure(newExpenditure, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.PROPERTY__EXPENDITURE, newExpenditure, newExpenditure));
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
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.PROPERTY__DESCRIPTION, oldDescription, description, !oldDescriptionESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.PROPERTY__DESCRIPTION, oldDescription, DESCRIPTION_EDEFAULT, oldDescriptionESet));
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
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.PROPERTY__BRAND, oldBrand, brand, !oldBrandESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.PROPERTY__BRAND, oldBrand, BRAND_EDEFAULT, oldBrandESet));
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
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.PROPERTY__TYPE, oldType, type, !oldTypeESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.PROPERTY__TYPE, oldType, TYPE_EDEFAULT, oldTypeESet));
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
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.PROPERTY__SERIAL_NUMBER, oldSerialNumber, serialNumber, !oldSerialNumberESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.PROPERTY__SERIAL_NUMBER, oldSerialNumber, SERIAL_NUMBER_EDEFAULT, oldSerialNumberESet));
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
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.PROPERTY__REMARKS, oldRemarks, remarks, !oldRemarksESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.PROPERTY__REMARKS, oldRemarks, REMARKS_EDEFAULT, oldRemarksESet));
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
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.PROPERTY__FROM_DATE, oldFromDate, fromDate, !oldFromDateESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.PROPERTY__FROM_DATE, oldFromDate, FROM_DATE_EDEFAULT, oldFromDateESet));
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
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.PROPERTY__UNTIL_DATE, oldUntilDate, untilDate, !oldUntilDateESet));
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, InvAndPropPackage.PROPERTY__UNTIL_DATE, oldUntilDate, UNTIL_DATE_EDEFAULT, oldUntilDateESet));
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
      eNotify(new ENotificationImpl(this, Notification.SET, InvAndPropPackage.PROPERTY__ARCHIVE, oldArchive, archive));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FileReference> getDocuments() {
    if (documents == null) {
      documents = new EObjectContainmentEList<FileReference>(FileReference.class, this, InvAndPropPackage.PROPERTY__DOCUMENTS);
    }
    return documents;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FileReference> getPictures() {
    if (pictures == null) {
      pictures = new EObjectContainmentEList<FileReference>(FileReference.class, this, InvAndPropPackage.PROPERTY__PICTURES);
    }
    return pictures;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvAndPropPackage.PROPERTY__EXPENDITURE:
        if (expenditure != null)
          msgs = ((InternalEObject)expenditure).eInverseRemove(this, InvAndPropPackage.EXPENDITURE__PURCHASE, Expenditure.class, msgs);
        return basicSetExpenditure((Expenditure)otherEnd, msgs);
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case InvAndPropPackage.PROPERTY__EXPENDITURE:
        return basicSetExpenditure(null, msgs);
      case InvAndPropPackage.PROPERTY__DOCUMENTS:
        return ((InternalEList<?>)getDocuments()).basicRemove(otherEnd, msgs);
      case InvAndPropPackage.PROPERTY__PICTURES:
        return ((InternalEList<?>)getPictures()).basicRemove(otherEnd, msgs);
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
      case InvAndPropPackage.PROPERTY__EXPENDITURE:
        if (resolve) return getExpenditure();
        return basicGetExpenditure();
      case InvAndPropPackage.PROPERTY__DESCRIPTION:
        return getDescription();
      case InvAndPropPackage.PROPERTY__BRAND:
        return getBrand();
      case InvAndPropPackage.PROPERTY__TYPE:
        return getType();
      case InvAndPropPackage.PROPERTY__SERIAL_NUMBER:
        return getSerialNumber();
      case InvAndPropPackage.PROPERTY__REMARKS:
        return getRemarks();
      case InvAndPropPackage.PROPERTY__FROM_DATE:
        return getFromDate();
      case InvAndPropPackage.PROPERTY__UNTIL_DATE:
        return getUntilDate();
      case InvAndPropPackage.PROPERTY__ARCHIVE:
        return isArchive();
      case InvAndPropPackage.PROPERTY__DOCUMENTS:
        return getDocuments();
      case InvAndPropPackage.PROPERTY__PICTURES:
        return getPictures();
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
      case InvAndPropPackage.PROPERTY__EXPENDITURE:
        setExpenditure((Expenditure)newValue);
        return;
      case InvAndPropPackage.PROPERTY__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case InvAndPropPackage.PROPERTY__BRAND:
        setBrand((String)newValue);
        return;
      case InvAndPropPackage.PROPERTY__TYPE:
        setType((String)newValue);
        return;
      case InvAndPropPackage.PROPERTY__SERIAL_NUMBER:
        setSerialNumber((String)newValue);
        return;
      case InvAndPropPackage.PROPERTY__REMARKS:
        setRemarks((String)newValue);
        return;
      case InvAndPropPackage.PROPERTY__FROM_DATE:
        setFromDate((FlexDate)newValue);
        return;
      case InvAndPropPackage.PROPERTY__UNTIL_DATE:
        setUntilDate((FlexDate)newValue);
        return;
      case InvAndPropPackage.PROPERTY__ARCHIVE:
        setArchive((Boolean)newValue);
        return;
      case InvAndPropPackage.PROPERTY__DOCUMENTS:
        getDocuments().clear();
        getDocuments().addAll((Collection<? extends FileReference>)newValue);
        return;
      case InvAndPropPackage.PROPERTY__PICTURES:
        getPictures().clear();
        getPictures().addAll((Collection<? extends FileReference>)newValue);
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
      case InvAndPropPackage.PROPERTY__EXPENDITURE:
        setExpenditure((Expenditure)null);
        return;
      case InvAndPropPackage.PROPERTY__DESCRIPTION:
        unsetDescription();
        return;
      case InvAndPropPackage.PROPERTY__BRAND:
        unsetBrand();
        return;
      case InvAndPropPackage.PROPERTY__TYPE:
        unsetType();
        return;
      case InvAndPropPackage.PROPERTY__SERIAL_NUMBER:
        unsetSerialNumber();
        return;
      case InvAndPropPackage.PROPERTY__REMARKS:
        unsetRemarks();
        return;
      case InvAndPropPackage.PROPERTY__FROM_DATE:
        unsetFromDate();
        return;
      case InvAndPropPackage.PROPERTY__UNTIL_DATE:
        unsetUntilDate();
        return;
      case InvAndPropPackage.PROPERTY__ARCHIVE:
        setArchive(ARCHIVE_EDEFAULT);
        return;
      case InvAndPropPackage.PROPERTY__DOCUMENTS:
        getDocuments().clear();
        return;
      case InvAndPropPackage.PROPERTY__PICTURES:
        getPictures().clear();
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
      case InvAndPropPackage.PROPERTY__EXPENDITURE:
        return expenditure != null;
      case InvAndPropPackage.PROPERTY__DESCRIPTION:
        return isSetDescription();
      case InvAndPropPackage.PROPERTY__BRAND:
        return isSetBrand();
      case InvAndPropPackage.PROPERTY__TYPE:
        return isSetType();
      case InvAndPropPackage.PROPERTY__SERIAL_NUMBER:
        return isSetSerialNumber();
      case InvAndPropPackage.PROPERTY__REMARKS:
        return isSetRemarks();
      case InvAndPropPackage.PROPERTY__FROM_DATE:
        return isSetFromDate();
      case InvAndPropPackage.PROPERTY__UNTIL_DATE:
        return isSetUntilDate();
      case InvAndPropPackage.PROPERTY__ARCHIVE:
        return archive != ARCHIVE_EDEFAULT;
      case InvAndPropPackage.PROPERTY__DOCUMENTS:
        return documents != null && !documents.isEmpty();
      case InvAndPropPackage.PROPERTY__PICTURES:
        return pictures != null && !pictures.isEmpty();
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
    result.append(" (description: ");
    if (descriptionESet) result.append(description); else result.append("<unset>");
    result.append(", brand: ");
    if (brandESet) result.append(brand); else result.append("<unset>");
    result.append(", type: ");
    if (typeESet) result.append(type); else result.append("<unset>");
    result.append(", serialNumber: ");
    if (serialNumberESet) result.append(serialNumber); else result.append("<unset>");
    result.append(", remarks: ");
    if (remarksESet) result.append(remarks); else result.append("<unset>");
    result.append(", fromDate: ");
    if (fromDateESet) result.append(fromDate); else result.append("<unset>");
    result.append(", untilDate: ");
    if (untilDateESet) result.append(untilDate); else result.append("<unset>");
    result.append(", archive: ");
    result.append(archive);
    result.append(')');
    return result.toString();
  }

} //PropertyImpl
