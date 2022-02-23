/**
 */
package goedegep.rolodex.model.impl;

import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.AddressForPeriod;
import goedegep.rolodex.model.RolodexPackage;
import goedegep.util.datetime.FlexDate;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Address For Period</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.AddressForPeriodImpl#getFromDate <em>From Date</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.AddressForPeriodImpl#getUntillDate <em>Untill Date</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.AddressForPeriodImpl#getAddress <em>Address</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AddressForPeriodImpl extends MinimalEObjectImpl.Container implements AddressForPeriod {
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
   * The default value of the '{@link #getUntillDate() <em>Untill Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUntillDate()
   * @generated
   * @ordered
   */
  protected static final FlexDate UNTILL_DATE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUntillDate() <em>Untill Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUntillDate()
   * @generated
   * @ordered
   */
  protected FlexDate untillDate = UNTILL_DATE_EDEFAULT;

  /**
   * This is true if the Untill Date attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean untillDateESet;

  /**
   * The cached value of the '{@link #getAddress() <em>Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAddress()
   * @generated
   * @ordered
   */
  protected Address address;

  /**
   * This is true if the Address reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean addressESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AddressForPeriodImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.ADDRESS_FOR_PERIOD;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FlexDate getFromDate() {
    return fromDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFromDate(FlexDate newFromDate) {
    FlexDate oldFromDate = fromDate;
    fromDate = newFromDate;
    boolean oldFromDateESet = fromDateESet;
    fromDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ADDRESS_FOR_PERIOD__FROM_DATE, oldFromDate,
          fromDate, !oldFromDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetFromDate() {
    FlexDate oldFromDate = fromDate;
    boolean oldFromDateESet = fromDateESet;
    fromDate = FROM_DATE_EDEFAULT;
    fromDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.ADDRESS_FOR_PERIOD__FROM_DATE, oldFromDate,
          FROM_DATE_EDEFAULT, oldFromDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetFromDate() {
    return fromDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public FlexDate getUntillDate() {
    return untillDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setUntillDate(FlexDate newUntillDate) {
    FlexDate oldUntillDate = untillDate;
    untillDate = newUntillDate;
    boolean oldUntillDateESet = untillDateESet;
    untillDateESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ADDRESS_FOR_PERIOD__UNTILL_DATE,
          oldUntillDate, untillDate, !oldUntillDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetUntillDate() {
    FlexDate oldUntillDate = untillDate;
    boolean oldUntillDateESet = untillDateESet;
    untillDate = UNTILL_DATE_EDEFAULT;
    untillDateESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.ADDRESS_FOR_PERIOD__UNTILL_DATE,
          oldUntillDate, UNTILL_DATE_EDEFAULT, oldUntillDateESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetUntillDate() {
    return untillDateESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Address getAddress() {
    if (address != null && address.eIsProxy()) {
      InternalEObject oldAddress = (InternalEObject) address;
      address = (Address) eResolveProxy(oldAddress);
      if (address != oldAddress) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, RolodexPackage.ADDRESS_FOR_PERIOD__ADDRESS,
              oldAddress, address));
      }
    }
    return address;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Address basicGetAddress() {
    return address;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setAddress(Address newAddress) {
    Address oldAddress = address;
    address = newAddress;
    boolean oldAddressESet = addressESet;
    addressESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.ADDRESS_FOR_PERIOD__ADDRESS, oldAddress,
          address, !oldAddressESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetAddress() {
    Address oldAddress = address;
    boolean oldAddressESet = addressESet;
    address = null;
    addressESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.ADDRESS_FOR_PERIOD__ADDRESS, oldAddress,
          null, oldAddressESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetAddress() {
    return addressESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case RolodexPackage.ADDRESS_FOR_PERIOD__FROM_DATE:
      return getFromDate();
    case RolodexPackage.ADDRESS_FOR_PERIOD__UNTILL_DATE:
      return getUntillDate();
    case RolodexPackage.ADDRESS_FOR_PERIOD__ADDRESS:
      if (resolve)
        return getAddress();
      return basicGetAddress();
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
    case RolodexPackage.ADDRESS_FOR_PERIOD__FROM_DATE:
      setFromDate((FlexDate) newValue);
      return;
    case RolodexPackage.ADDRESS_FOR_PERIOD__UNTILL_DATE:
      setUntillDate((FlexDate) newValue);
      return;
    case RolodexPackage.ADDRESS_FOR_PERIOD__ADDRESS:
      setAddress((Address) newValue);
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
    case RolodexPackage.ADDRESS_FOR_PERIOD__FROM_DATE:
      unsetFromDate();
      return;
    case RolodexPackage.ADDRESS_FOR_PERIOD__UNTILL_DATE:
      unsetUntillDate();
      return;
    case RolodexPackage.ADDRESS_FOR_PERIOD__ADDRESS:
      unsetAddress();
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
    case RolodexPackage.ADDRESS_FOR_PERIOD__FROM_DATE:
      return isSetFromDate();
    case RolodexPackage.ADDRESS_FOR_PERIOD__UNTILL_DATE:
      return isSetUntillDate();
    case RolodexPackage.ADDRESS_FOR_PERIOD__ADDRESS:
      return isSetAddress();
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
    if (eIsProxy())
      return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (fromDate: ");
    if (fromDateESet)
      result.append(fromDate);
    else
      result.append("<unset>");
    result.append(", untillDate: ");
    if (untillDateESet)
      result.append(untillDate);
    else
      result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //AddressForPeriodImpl
