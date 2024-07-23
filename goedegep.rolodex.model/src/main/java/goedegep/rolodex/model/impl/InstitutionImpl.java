/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.AddressForPeriod;
import goedegep.rolodex.model.AddressHolder;
import goedegep.rolodex.model.Archive;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Institution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.InstitutionImpl#getPhoneNumbers <em>Phone Numbers</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.InstitutionImpl#getAddress <em>Address</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.InstitutionImpl#getPreviousAddresses <em>Previous Addresses</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.InstitutionImpl#isArchived <em>Archived</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.InstitutionImpl#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.InstitutionImpl#getMailingAddress <em>Mailing Address</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InstitutionImpl extends MinimalEObjectImpl.Container implements Institution {
  /**
   * The cached value of the '{@link #getPhoneNumbers() <em>Phone Numbers</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoneNumbers()
   * @generated
   * @ordered
   */
  protected EList<PhoneNumber> phoneNumbers;

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
   * The cached value of the '{@link #getPreviousAddresses() <em>Previous Addresses</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPreviousAddresses()
   * @generated
   * @ordered
   */
  protected EList<AddressForPeriod> previousAddresses;

  /**
   * The default value of the '{@link #isArchived() <em>Archived</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isArchived()
   * @generated
   * @ordered
   */
  protected static final boolean ARCHIVED_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isArchived() <em>Archived</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isArchived()
   * @generated
   * @ordered
   */
  protected boolean archived = ARCHIVED_EDEFAULT;

  /**
   * This is true if the Archived attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean archivedESet;

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getMailingAddress() <em>Mailing Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMailingAddress()
   * @generated
   * @ordered
   */
  protected Address mailingAddress;

  /**
   * This is true if the Mailing Address reference has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean mailingAddressESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InstitutionImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.INSTITUTION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<PhoneNumber> getPhoneNumbers() {
    if (phoneNumbers == null) {
      phoneNumbers = new EObjectWithInverseResolvingEList.ManyInverse<PhoneNumber>(PhoneNumber.class, this,
          RolodexPackage.INSTITUTION__PHONE_NUMBERS, RolodexPackage.PHONE_NUMBER__NUMBER_HOLDERS);
    }
    return phoneNumbers;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.INSTITUTION__NAME, oldName, name));
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
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, RolodexPackage.INSTITUTION__ADDRESS, oldAddress,
              address));
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
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.INSTITUTION__ADDRESS, oldAddress, address));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<AddressForPeriod> getPreviousAddresses() {
    if (previousAddresses == null) {
      previousAddresses = new EObjectContainmentEList<AddressForPeriod>(AddressForPeriod.class, this,
          RolodexPackage.INSTITUTION__PREVIOUS_ADDRESSES);
    }
    return previousAddresses;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isArchived() {
    return archived;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setArchived(boolean newArchived) {
    boolean oldArchived = archived;
    archived = newArchived;
    boolean oldArchivedESet = archivedESet;
    archivedESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.INSTITUTION__ARCHIVED, oldArchived, archived,
          !oldArchivedESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetArchived() {
    boolean oldArchived = archived;
    boolean oldArchivedESet = archivedESet;
    archived = ARCHIVED_EDEFAULT;
    archivedESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.INSTITUTION__ARCHIVED, oldArchived,
          ARCHIVED_EDEFAULT, oldArchivedESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetArchived() {
    return archivedESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Address getMailingAddress() {
    if (mailingAddress != null && mailingAddress.eIsProxy()) {
      InternalEObject oldMailingAddress = (InternalEObject) mailingAddress;
      mailingAddress = (Address) eResolveProxy(oldMailingAddress);
      if (mailingAddress != oldMailingAddress) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, RolodexPackage.INSTITUTION__MAILING_ADDRESS,
              oldMailingAddress, mailingAddress));
      }
    }
    return mailingAddress;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Address basicGetMailingAddress() {
    return mailingAddress;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setMailingAddress(Address newMailingAddress) {
    Address oldMailingAddress = mailingAddress;
    mailingAddress = newMailingAddress;
    boolean oldMailingAddressESet = mailingAddressESet;
    mailingAddressESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.INSTITUTION__MAILING_ADDRESS,
          oldMailingAddress, mailingAddress, !oldMailingAddressESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetMailingAddress() {
    Address oldMailingAddress = mailingAddress;
    boolean oldMailingAddressESet = mailingAddressESet;
    mailingAddress = null;
    mailingAddressESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.INSTITUTION__MAILING_ADDRESS,
          oldMailingAddress, null, oldMailingAddressESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetMailingAddress() {
    return mailingAddressESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case RolodexPackage.INSTITUTION__PHONE_NUMBERS:
      return ((InternalEList<InternalEObject>) (InternalEList<?>) getPhoneNumbers()).basicAdd(otherEnd, msgs);
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
    case RolodexPackage.INSTITUTION__PHONE_NUMBERS:
      return ((InternalEList<?>) getPhoneNumbers()).basicRemove(otherEnd, msgs);
    case RolodexPackage.INSTITUTION__PREVIOUS_ADDRESSES:
      return ((InternalEList<?>) getPreviousAddresses()).basicRemove(otherEnd, msgs);
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
    case RolodexPackage.INSTITUTION__PHONE_NUMBERS:
      return getPhoneNumbers();
    case RolodexPackage.INSTITUTION__ADDRESS:
      if (resolve)
        return getAddress();
      return basicGetAddress();
    case RolodexPackage.INSTITUTION__PREVIOUS_ADDRESSES:
      return getPreviousAddresses();
    case RolodexPackage.INSTITUTION__ARCHIVED:
      return isArchived();
    case RolodexPackage.INSTITUTION__NAME:
      return getName();
    case RolodexPackage.INSTITUTION__MAILING_ADDRESS:
      if (resolve)
        return getMailingAddress();
      return basicGetMailingAddress();
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
    case RolodexPackage.INSTITUTION__PHONE_NUMBERS:
      getPhoneNumbers().clear();
      getPhoneNumbers().addAll((Collection<? extends PhoneNumber>) newValue);
      return;
    case RolodexPackage.INSTITUTION__ADDRESS:
      setAddress((Address) newValue);
      return;
    case RolodexPackage.INSTITUTION__PREVIOUS_ADDRESSES:
      getPreviousAddresses().clear();
      getPreviousAddresses().addAll((Collection<? extends AddressForPeriod>) newValue);
      return;
    case RolodexPackage.INSTITUTION__ARCHIVED:
      setArchived((Boolean) newValue);
      return;
    case RolodexPackage.INSTITUTION__NAME:
      setName((String) newValue);
      return;
    case RolodexPackage.INSTITUTION__MAILING_ADDRESS:
      setMailingAddress((Address) newValue);
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
    case RolodexPackage.INSTITUTION__PHONE_NUMBERS:
      getPhoneNumbers().clear();
      return;
    case RolodexPackage.INSTITUTION__ADDRESS:
      setAddress((Address) null);
      return;
    case RolodexPackage.INSTITUTION__PREVIOUS_ADDRESSES:
      getPreviousAddresses().clear();
      return;
    case RolodexPackage.INSTITUTION__ARCHIVED:
      unsetArchived();
      return;
    case RolodexPackage.INSTITUTION__NAME:
      setName(NAME_EDEFAULT);
      return;
    case RolodexPackage.INSTITUTION__MAILING_ADDRESS:
      unsetMailingAddress();
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
    case RolodexPackage.INSTITUTION__PHONE_NUMBERS:
      return phoneNumbers != null && !phoneNumbers.isEmpty();
    case RolodexPackage.INSTITUTION__ADDRESS:
      return address != null;
    case RolodexPackage.INSTITUTION__PREVIOUS_ADDRESSES:
      return previousAddresses != null && !previousAddresses.isEmpty();
    case RolodexPackage.INSTITUTION__ARCHIVED:
      return isSetArchived();
    case RolodexPackage.INSTITUTION__NAME:
      return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
    case RolodexPackage.INSTITUTION__MAILING_ADDRESS:
      return isSetMailingAddress();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
    if (baseClass == AddressHolder.class) {
      switch (derivedFeatureID) {
      case RolodexPackage.INSTITUTION__ADDRESS:
        return RolodexPackage.ADDRESS_HOLDER__ADDRESS;
      case RolodexPackage.INSTITUTION__PREVIOUS_ADDRESSES:
        return RolodexPackage.ADDRESS_HOLDER__PREVIOUS_ADDRESSES;
      default:
        return -1;
      }
    }
    if (baseClass == Archive.class) {
      switch (derivedFeatureID) {
      case RolodexPackage.INSTITUTION__ARCHIVED:
        return RolodexPackage.ARCHIVE__ARCHIVED;
      default:
        return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
    if (baseClass == AddressHolder.class) {
      switch (baseFeatureID) {
      case RolodexPackage.ADDRESS_HOLDER__ADDRESS:
        return RolodexPackage.INSTITUTION__ADDRESS;
      case RolodexPackage.ADDRESS_HOLDER__PREVIOUS_ADDRESSES:
        return RolodexPackage.INSTITUTION__PREVIOUS_ADDRESSES;
      default:
        return -1;
      }
    }
    if (baseClass == Archive.class) {
      switch (baseFeatureID) {
      case RolodexPackage.ARCHIVE__ARCHIVED:
        return RolodexPackage.INSTITUTION__ARCHIVED;
      default:
        return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public String toString() {
    StringBuffer result = new StringBuffer();

    result.append(name);

    return result.toString();
  }

} //InstitutionImpl
