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
import goedegep.rolodex.model.Description;
import goedegep.rolodex.model.Family;
import goedegep.rolodex.model.Person;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Family</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.FamilyImpl#getPhoneNumbers <em>Phone Numbers</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.FamilyImpl#getAddress <em>Address</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.FamilyImpl#getPreviousAddresses <em>Previous Addresses</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.FamilyImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.FamilyImpl#isArchived <em>Archived</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.FamilyImpl#getFamilyTitle <em>Family Title</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.FamilyImpl#getFamilyName <em>Family Name</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.FamilyImpl#getMembers <em>Members</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FamilyImpl extends MinimalEObjectImpl.Container implements Family {
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
   * The default value of the '{@link #getFamilyTitle() <em>Family Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFamilyTitle()
   * @generated
   * @ordered
   */
  protected static final String FAMILY_TITLE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFamilyTitle() <em>Family Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFamilyTitle()
   * @generated
   * @ordered
   */
  protected String familyTitle = FAMILY_TITLE_EDEFAULT;

  /**
   * The default value of the '{@link #getFamilyName() <em>Family Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFamilyName()
   * @generated
   * @ordered
   */
  protected static final String FAMILY_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFamilyName() <em>Family Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFamilyName()
   * @generated
   * @ordered
   */
  protected String familyName = FAMILY_NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getMembers() <em>Members</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMembers()
   * @generated
   * @ordered
   */
  protected EList<Person> members;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FamilyImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.FAMILY;
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
          RolodexPackage.FAMILY__PHONE_NUMBERS, RolodexPackage.PHONE_NUMBER__NUMBER_HOLDERS);
    }
    return phoneNumbers;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFamilyTitle() {
    return familyTitle;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFamilyTitle(String newFamilyTitle) {
    String oldFamilyTitle = familyTitle;
    familyTitle = newFamilyTitle;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.FAMILY__FAMILY_TITLE, oldFamilyTitle,
          familyTitle));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getFamilyName() {
    return familyName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFamilyName(String newFamilyName) {
    String oldFamilyName = familyName;
    familyName = newFamilyName;
    if (eNotificationRequired())
      eNotify(
          new ENotificationImpl(this, Notification.SET, RolodexPackage.FAMILY__FAMILY_NAME, oldFamilyName, familyName));
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
          eNotify(
              new ENotificationImpl(this, Notification.RESOLVE, RolodexPackage.FAMILY__ADDRESS, oldAddress, address));
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
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.FAMILY__ADDRESS, oldAddress, address));
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
          RolodexPackage.FAMILY__PREVIOUS_ADDRESSES);
    }
    return previousAddresses;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setDescription(String newDescription) {
    String oldDescription = description;
    description = newDescription;
    boolean oldDescriptionESet = descriptionESet;
    descriptionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.FAMILY__DESCRIPTION, oldDescription,
          description, !oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void unsetDescription() {
    String oldDescription = description;
    boolean oldDescriptionESet = descriptionESet;
    description = DESCRIPTION_EDEFAULT;
    descriptionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.FAMILY__DESCRIPTION, oldDescription,
          DESCRIPTION_EDEFAULT, oldDescriptionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isSetDescription() {
    return descriptionESet;
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
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.FAMILY__ARCHIVED, oldArchived, archived,
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
      eNotify(new ENotificationImpl(this, Notification.UNSET, RolodexPackage.FAMILY__ARCHIVED, oldArchived,
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
  public EList<Person> getMembers() {
    if (members == null) {
      members = new EObjectWithInverseResolvingEList<Person>(Person.class, this, RolodexPackage.FAMILY__MEMBERS,
          RolodexPackage.PERSON__FAMILY);
    }
    return members;
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
    case RolodexPackage.FAMILY__PHONE_NUMBERS:
      return ((InternalEList<InternalEObject>) (InternalEList<?>) getPhoneNumbers()).basicAdd(otherEnd, msgs);
    case RolodexPackage.FAMILY__MEMBERS:
      return ((InternalEList<InternalEObject>) (InternalEList<?>) getMembers()).basicAdd(otherEnd, msgs);
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
    case RolodexPackage.FAMILY__PHONE_NUMBERS:
      return ((InternalEList<?>) getPhoneNumbers()).basicRemove(otherEnd, msgs);
    case RolodexPackage.FAMILY__PREVIOUS_ADDRESSES:
      return ((InternalEList<?>) getPreviousAddresses()).basicRemove(otherEnd, msgs);
    case RolodexPackage.FAMILY__MEMBERS:
      return ((InternalEList<?>) getMembers()).basicRemove(otherEnd, msgs);
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
    case RolodexPackage.FAMILY__PHONE_NUMBERS:
      return getPhoneNumbers();
    case RolodexPackage.FAMILY__ADDRESS:
      if (resolve)
        return getAddress();
      return basicGetAddress();
    case RolodexPackage.FAMILY__PREVIOUS_ADDRESSES:
      return getPreviousAddresses();
    case RolodexPackage.FAMILY__DESCRIPTION:
      return getDescription();
    case RolodexPackage.FAMILY__ARCHIVED:
      return isArchived();
    case RolodexPackage.FAMILY__FAMILY_TITLE:
      return getFamilyTitle();
    case RolodexPackage.FAMILY__FAMILY_NAME:
      return getFamilyName();
    case RolodexPackage.FAMILY__MEMBERS:
      return getMembers();
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
    case RolodexPackage.FAMILY__PHONE_NUMBERS:
      getPhoneNumbers().clear();
      getPhoneNumbers().addAll((Collection<? extends PhoneNumber>) newValue);
      return;
    case RolodexPackage.FAMILY__ADDRESS:
      setAddress((Address) newValue);
      return;
    case RolodexPackage.FAMILY__PREVIOUS_ADDRESSES:
      getPreviousAddresses().clear();
      getPreviousAddresses().addAll((Collection<? extends AddressForPeriod>) newValue);
      return;
    case RolodexPackage.FAMILY__DESCRIPTION:
      setDescription((String) newValue);
      return;
    case RolodexPackage.FAMILY__ARCHIVED:
      setArchived((Boolean) newValue);
      return;
    case RolodexPackage.FAMILY__FAMILY_TITLE:
      setFamilyTitle((String) newValue);
      return;
    case RolodexPackage.FAMILY__FAMILY_NAME:
      setFamilyName((String) newValue);
      return;
    case RolodexPackage.FAMILY__MEMBERS:
      getMembers().clear();
      getMembers().addAll((Collection<? extends Person>) newValue);
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
    case RolodexPackage.FAMILY__PHONE_NUMBERS:
      getPhoneNumbers().clear();
      return;
    case RolodexPackage.FAMILY__ADDRESS:
      setAddress((Address) null);
      return;
    case RolodexPackage.FAMILY__PREVIOUS_ADDRESSES:
      getPreviousAddresses().clear();
      return;
    case RolodexPackage.FAMILY__DESCRIPTION:
      unsetDescription();
      return;
    case RolodexPackage.FAMILY__ARCHIVED:
      unsetArchived();
      return;
    case RolodexPackage.FAMILY__FAMILY_TITLE:
      setFamilyTitle(FAMILY_TITLE_EDEFAULT);
      return;
    case RolodexPackage.FAMILY__FAMILY_NAME:
      setFamilyName(FAMILY_NAME_EDEFAULT);
      return;
    case RolodexPackage.FAMILY__MEMBERS:
      getMembers().clear();
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
    case RolodexPackage.FAMILY__PHONE_NUMBERS:
      return phoneNumbers != null && !phoneNumbers.isEmpty();
    case RolodexPackage.FAMILY__ADDRESS:
      return address != null;
    case RolodexPackage.FAMILY__PREVIOUS_ADDRESSES:
      return previousAddresses != null && !previousAddresses.isEmpty();
    case RolodexPackage.FAMILY__DESCRIPTION:
      return isSetDescription();
    case RolodexPackage.FAMILY__ARCHIVED:
      return isSetArchived();
    case RolodexPackage.FAMILY__FAMILY_TITLE:
      return FAMILY_TITLE_EDEFAULT == null ? familyTitle != null : !FAMILY_TITLE_EDEFAULT.equals(familyTitle);
    case RolodexPackage.FAMILY__FAMILY_NAME:
      return FAMILY_NAME_EDEFAULT == null ? familyName != null : !FAMILY_NAME_EDEFAULT.equals(familyName);
    case RolodexPackage.FAMILY__MEMBERS:
      return members != null && !members.isEmpty();
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
      case RolodexPackage.FAMILY__ADDRESS:
        return RolodexPackage.ADDRESS_HOLDER__ADDRESS;
      case RolodexPackage.FAMILY__PREVIOUS_ADDRESSES:
        return RolodexPackage.ADDRESS_HOLDER__PREVIOUS_ADDRESSES;
      default:
        return -1;
      }
    }
    if (baseClass == Description.class) {
      switch (derivedFeatureID) {
      case RolodexPackage.FAMILY__DESCRIPTION:
        return RolodexPackage.DESCRIPTION__DESCRIPTION;
      default:
        return -1;
      }
    }
    if (baseClass == Archive.class) {
      switch (derivedFeatureID) {
      case RolodexPackage.FAMILY__ARCHIVED:
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
        return RolodexPackage.FAMILY__ADDRESS;
      case RolodexPackage.ADDRESS_HOLDER__PREVIOUS_ADDRESSES:
        return RolodexPackage.FAMILY__PREVIOUS_ADDRESSES;
      default:
        return -1;
      }
    }
    if (baseClass == Description.class) {
      switch (baseFeatureID) {
      case RolodexPackage.DESCRIPTION__DESCRIPTION:
        return RolodexPackage.FAMILY__DESCRIPTION;
      default:
        return -1;
      }
    }
    if (baseClass == Archive.class) {
      switch (baseFeatureID) {
      case RolodexPackage.ARCHIVE__ARCHIVED:
        return RolodexPackage.FAMILY__ARCHIVED;
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
    if (getFamilyTitle() != null) {
      result.append(getFamilyTitle());
      result.append(" ");
    }
    if (getFamilyName() != null) {
      result.append(getFamilyName());
    }

    return result.toString();
  }

} //FamilyImpl
