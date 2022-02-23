/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.rolodex.model.PhoneAddressBookEntry;
import goedegep.rolodex.model.PhoneAddressBookEntryType;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Phone Address Book Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.PhoneAddressBookEntryImpl#getEntryName <em>Entry Name</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PhoneAddressBookEntryImpl#getPhoneNumber <em>Phone Number</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PhoneAddressBookEntryImpl#getEntryType <em>Entry Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PhoneAddressBookEntryImpl extends MinimalEObjectImpl.Container implements PhoneAddressBookEntry {
  /**
   * The default value of the '{@link #getEntryName() <em>Entry Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEntryName()
   * @generated
   * @ordered
   */
  protected static final String ENTRY_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEntryName() <em>Entry Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEntryName()
   * @generated
   * @ordered
   */
  protected String entryName = ENTRY_NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getPhoneNumber() <em>Phone Number</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoneNumber()
   * @generated
   * @ordered
   */
  protected PhoneNumber phoneNumber;

  /**
   * The default value of the '{@link #getEntryType() <em>Entry Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEntryType()
   * @generated
   * @ordered
   */
  protected static final PhoneAddressBookEntryType ENTRY_TYPE_EDEFAULT = PhoneAddressBookEntryType.NUMBER_LOCATION_0;

  /**
   * The cached value of the '{@link #getEntryType() <em>Entry Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEntryType()
   * @generated
   * @ordered
   */
  protected PhoneAddressBookEntryType entryType = ENTRY_TYPE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PhoneAddressBookEntryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.PHONE_ADDRESS_BOOK_ENTRY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getEntryName() {
    return entryName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setEntryName(String newEntryName) {
    String oldEntryName = entryName;
    entryName = newEntryName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__ENTRY_NAME,
          oldEntryName, entryName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PhoneNumber getPhoneNumber() {
    if (phoneNumber != null && phoneNumber.eIsProxy()) {
      InternalEObject oldPhoneNumber = (InternalEObject) phoneNumber;
      phoneNumber = (PhoneNumber) eResolveProxy(oldPhoneNumber);
      if (phoneNumber != oldPhoneNumber) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE,
              RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__PHONE_NUMBER, oldPhoneNumber, phoneNumber));
      }
    }
    return phoneNumber;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PhoneNumber basicGetPhoneNumber() {
    return phoneNumber;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPhoneNumber(PhoneNumber newPhoneNumber) {
    PhoneNumber oldPhoneNumber = phoneNumber;
    phoneNumber = newPhoneNumber;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__PHONE_NUMBER,
          oldPhoneNumber, phoneNumber));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PhoneAddressBookEntryType getEntryType() {
    return entryType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setEntryType(PhoneAddressBookEntryType newEntryType) {
    PhoneAddressBookEntryType oldEntryType = entryType;
    entryType = newEntryType == null ? ENTRY_TYPE_EDEFAULT : newEntryType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__ENTRY_TYPE,
          oldEntryType, entryType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__ENTRY_NAME:
      return getEntryName();
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__PHONE_NUMBER:
      if (resolve)
        return getPhoneNumber();
      return basicGetPhoneNumber();
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__ENTRY_TYPE:
      return getEntryType();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__ENTRY_NAME:
      setEntryName((String) newValue);
      return;
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__PHONE_NUMBER:
      setPhoneNumber((PhoneNumber) newValue);
      return;
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__ENTRY_TYPE:
      setEntryType((PhoneAddressBookEntryType) newValue);
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
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__ENTRY_NAME:
      setEntryName(ENTRY_NAME_EDEFAULT);
      return;
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__PHONE_NUMBER:
      setPhoneNumber((PhoneNumber) null);
      return;
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__ENTRY_TYPE:
      setEntryType(ENTRY_TYPE_EDEFAULT);
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
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__ENTRY_NAME:
      return ENTRY_NAME_EDEFAULT == null ? entryName != null : !ENTRY_NAME_EDEFAULT.equals(entryName);
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__PHONE_NUMBER:
      return phoneNumber != null;
    case RolodexPackage.PHONE_ADDRESS_BOOK_ENTRY__ENTRY_TYPE:
      return entryType != ENTRY_TYPE_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("Naam: ");
    result.append(entryName);
    result.append(", Type: ");
    result.append(entryType);
    return result.toString();
  }

} //PhoneAddressBookEntryImpl
