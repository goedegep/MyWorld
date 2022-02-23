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
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.rolodex.model.ConnectionType;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.PhoneNumberHolder;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Phone Number</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.PhoneNumberImpl#getPhoneNumber <em>Phone Number</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PhoneNumberImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PhoneNumberImpl#getConnectionType <em>Connection Type</em>}</li>
 *   <li>{@link goedegep.rolodex.model.impl.PhoneNumberImpl#getNumberHolders <em>Number Holders</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PhoneNumberImpl extends MinimalEObjectImpl.Container implements PhoneNumber {
  /**
   * The default value of the '{@link #getPhoneNumber() <em>Phone Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoneNumber()
   * @generated
   * @ordered
   */
  protected static final String PHONE_NUMBER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPhoneNumber() <em>Phone Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoneNumber()
   * @generated
   * @ordered
   */
  protected String phoneNumber = PHONE_NUMBER_EDEFAULT;

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
   * The default value of the '{@link #getConnectionType() <em>Connection Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConnectionType()
   * @generated
   * @ordered
   */
  protected static final ConnectionType CONNECTION_TYPE_EDEFAULT = ConnectionType.VAST;

  /**
   * The cached value of the '{@link #getConnectionType() <em>Connection Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConnectionType()
   * @generated
   * @ordered
   */
  protected ConnectionType connectionType = CONNECTION_TYPE_EDEFAULT;

  /**
   * The cached value of the '{@link #getNumberHolders() <em>Number Holders</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNumberHolders()
   * @generated
   * @ordered
   */
  protected EList<PhoneNumberHolder> numberHolders;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PhoneNumberImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.PHONE_NUMBER;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPhoneNumber(String newPhoneNumber) {
    String oldPhoneNumber = phoneNumber;
    phoneNumber = newPhoneNumber;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PHONE_NUMBER__PHONE_NUMBER, oldPhoneNumber,
          phoneNumber));
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
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PHONE_NUMBER__DESCRIPTION, oldDescription,
          description));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ConnectionType getConnectionType() {
    return connectionType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setConnectionType(ConnectionType newConnectionType) {
    ConnectionType oldConnectionType = connectionType;
    connectionType = newConnectionType == null ? CONNECTION_TYPE_EDEFAULT : newConnectionType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, RolodexPackage.PHONE_NUMBER__CONNECTION_TYPE,
          oldConnectionType, connectionType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<PhoneNumberHolder> getNumberHolders() {
    if (numberHolders == null) {
      numberHolders = new EObjectWithInverseResolvingEList.ManyInverse<PhoneNumberHolder>(PhoneNumberHolder.class, this,
          RolodexPackage.PHONE_NUMBER__NUMBER_HOLDERS, RolodexPackage.PHONE_NUMBER_HOLDER__PHONE_NUMBERS);
    }
    return numberHolders;
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
    case RolodexPackage.PHONE_NUMBER__NUMBER_HOLDERS:
      return ((InternalEList<InternalEObject>) (InternalEList<?>) getNumberHolders()).basicAdd(otherEnd, msgs);
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
    case RolodexPackage.PHONE_NUMBER__NUMBER_HOLDERS:
      return ((InternalEList<?>) getNumberHolders()).basicRemove(otherEnd, msgs);
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
    case RolodexPackage.PHONE_NUMBER__PHONE_NUMBER:
      return getPhoneNumber();
    case RolodexPackage.PHONE_NUMBER__DESCRIPTION:
      return getDescription();
    case RolodexPackage.PHONE_NUMBER__CONNECTION_TYPE:
      return getConnectionType();
    case RolodexPackage.PHONE_NUMBER__NUMBER_HOLDERS:
      return getNumberHolders();
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
    case RolodexPackage.PHONE_NUMBER__PHONE_NUMBER:
      setPhoneNumber((String) newValue);
      return;
    case RolodexPackage.PHONE_NUMBER__DESCRIPTION:
      setDescription((String) newValue);
      return;
    case RolodexPackage.PHONE_NUMBER__CONNECTION_TYPE:
      setConnectionType((ConnectionType) newValue);
      return;
    case RolodexPackage.PHONE_NUMBER__NUMBER_HOLDERS:
      getNumberHolders().clear();
      getNumberHolders().addAll((Collection<? extends PhoneNumberHolder>) newValue);
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
    case RolodexPackage.PHONE_NUMBER__PHONE_NUMBER:
      setPhoneNumber(PHONE_NUMBER_EDEFAULT);
      return;
    case RolodexPackage.PHONE_NUMBER__DESCRIPTION:
      setDescription(DESCRIPTION_EDEFAULT);
      return;
    case RolodexPackage.PHONE_NUMBER__CONNECTION_TYPE:
      setConnectionType(CONNECTION_TYPE_EDEFAULT);
      return;
    case RolodexPackage.PHONE_NUMBER__NUMBER_HOLDERS:
      getNumberHolders().clear();
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
    case RolodexPackage.PHONE_NUMBER__PHONE_NUMBER:
      return PHONE_NUMBER_EDEFAULT == null ? phoneNumber != null : !PHONE_NUMBER_EDEFAULT.equals(phoneNumber);
    case RolodexPackage.PHONE_NUMBER__DESCRIPTION:
      return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
    case RolodexPackage.PHONE_NUMBER__CONNECTION_TYPE:
      return connectionType != CONNECTION_TYPE_EDEFAULT;
    case RolodexPackage.PHONE_NUMBER__NUMBER_HOLDERS:
      return numberHolders != null && !numberHolders.isEmpty();
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
    if (eIsProxy())
      return super.toString();
    StringBuilder buf = new StringBuilder();

    buf.append(phoneNumber);

    if (description != null || connectionType != null) {
      buf.append(" (");
      if (connectionType != null) {
        buf.append(connectionType);
      }
      if (description != null) {
        if (connectionType != null) {
          buf.append(" - ");
        }
        buf.append(description);
      }
      buf.append(")");
    }

    return buf.toString();
  }

} //PhoneNumberImpl
