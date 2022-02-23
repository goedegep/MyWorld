/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import goedegep.rolodex.model.Phone;
import goedegep.rolodex.model.PhoneAddressBook;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Phone</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.PhoneImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PhoneImpl#getPhoneType <em>Phone Type</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PhoneImpl#getPhoneAddressBook <em>Phone Address Book</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PhoneImpl extends MinimalEObjectImpl.Container implements Phone {
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
   * The default value of the '{@link #getPhoneType() <em>Phone Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoneType()
   * @generated
   * @ordered
   */
  protected static final String PHONE_TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPhoneType() <em>Phone Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoneType()
   * @generated
   * @ordered
   */
  protected String phoneType = PHONE_TYPE_EDEFAULT;

  /**
   * The cached value of the '{@link #getPhoneAddressBook() <em>Phone Address Book</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoneAddressBook()
   * @generated
   * @ordered
   */
  protected PhoneAddressBook phoneAddressBook;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PhoneImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.PHONE;
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
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PHONE__DESCRIPTION, oldDescription,
          description));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getPhoneType() {
    return phoneType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPhoneType(String newPhoneType) {
    String oldPhoneType = phoneType;
    phoneType = newPhoneType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PHONE__PHONE_TYPE, oldPhoneType, phoneType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public PhoneAddressBook getPhoneAddressBook() {
    return phoneAddressBook;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPhoneAddressBook(PhoneAddressBook newPhoneAddressBook, NotificationChain msgs) {
    PhoneAddressBook oldPhoneAddressBook = phoneAddressBook;
    phoneAddressBook = newPhoneAddressBook;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
          RolodexPackage.PHONE__PHONE_ADDRESS_BOOK, oldPhoneAddressBook, newPhoneAddressBook);
      if (msgs == null)
        msgs = notification;
      else
        msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPhoneAddressBook(PhoneAddressBook newPhoneAddressBook) {
    if (newPhoneAddressBook != phoneAddressBook) {
      NotificationChain msgs = null;
      if (phoneAddressBook != null)
        msgs = ((InternalEObject) phoneAddressBook).eInverseRemove(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.PHONE__PHONE_ADDRESS_BOOK, null, msgs);
      if (newPhoneAddressBook != null)
        msgs = ((InternalEObject) newPhoneAddressBook).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - RolodexPackage.PHONE__PHONE_ADDRESS_BOOK, null, msgs);
      msgs = basicSetPhoneAddressBook(newPhoneAddressBook, msgs);
      if (msgs != null)
        msgs.dispatch();
    } else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PHONE__PHONE_ADDRESS_BOOK,
          newPhoneAddressBook, newPhoneAddressBook));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case RolodexPackage.PHONE__PHONE_ADDRESS_BOOK:
      return basicSetPhoneAddressBook(null, msgs);
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
    case RolodexPackage.PHONE__DESCRIPTION:
      return getDescription();
    case RolodexPackage.PHONE__PHONE_TYPE:
      return getPhoneType();
    case RolodexPackage.PHONE__PHONE_ADDRESS_BOOK:
      return getPhoneAddressBook();
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
    case RolodexPackage.PHONE__DESCRIPTION:
      setDescription((String) newValue);
      return;
    case RolodexPackage.PHONE__PHONE_TYPE:
      setPhoneType((String) newValue);
      return;
    case RolodexPackage.PHONE__PHONE_ADDRESS_BOOK:
      setPhoneAddressBook((PhoneAddressBook) newValue);
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
    case RolodexPackage.PHONE__DESCRIPTION:
      setDescription(DESCRIPTION_EDEFAULT);
      return;
    case RolodexPackage.PHONE__PHONE_TYPE:
      setPhoneType(PHONE_TYPE_EDEFAULT);
      return;
    case RolodexPackage.PHONE__PHONE_ADDRESS_BOOK:
      setPhoneAddressBook((PhoneAddressBook) null);
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
    case RolodexPackage.PHONE__DESCRIPTION:
      return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
    case RolodexPackage.PHONE__PHONE_TYPE:
      return PHONE_TYPE_EDEFAULT == null ? phoneType != null : !PHONE_TYPE_EDEFAULT.equals(phoneType);
    case RolodexPackage.PHONE__PHONE_ADDRESS_BOOK:
      return phoneAddressBook != null;
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
    result.append(" (description: ");
    result.append(description);
    result.append(", phoneType: ");
    result.append(phoneType);
    result.append(')');
    return result.toString();
  }

} //PhoneImpl
