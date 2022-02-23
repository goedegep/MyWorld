/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.PhoneNumberList;
import goedegep.rolodex.model.RolodexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Phone Number List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.PhoneNumberListImpl#getPhoneNumbers <em>Phone Numbers</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PhoneNumberListImpl extends MinimalEObjectImpl.Container implements PhoneNumberList {
  /**
   * The cached value of the '{@link #getPhoneNumbers() <em>Phone Numbers</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPhoneNumbers()
   * @generated
   * @ordered
   */
  protected EList<PhoneNumber> phoneNumbers;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PhoneNumberListImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.PHONE_NUMBER_LIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<PhoneNumber> getPhoneNumbers() {
    if (phoneNumbers == null) {
      phoneNumbers = new EObjectContainmentEList<PhoneNumber>(PhoneNumber.class, this,
          RolodexPackage.PHONE_NUMBER_LIST__PHONE_NUMBERS);
    }
    return phoneNumbers;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case RolodexPackage.PHONE_NUMBER_LIST__PHONE_NUMBERS:
      return ((InternalEList<?>) getPhoneNumbers()).basicRemove(otherEnd, msgs);
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
    case RolodexPackage.PHONE_NUMBER_LIST__PHONE_NUMBERS:
      return getPhoneNumbers();
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
    case RolodexPackage.PHONE_NUMBER_LIST__PHONE_NUMBERS:
      getPhoneNumbers().clear();
      getPhoneNumbers().addAll((Collection<? extends PhoneNumber>) newValue);
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
    case RolodexPackage.PHONE_NUMBER_LIST__PHONE_NUMBERS:
      getPhoneNumbers().clear();
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
    case RolodexPackage.PHONE_NUMBER_LIST__PHONE_NUMBERS:
      return phoneNumbers != null && !phoneNumbers.isEmpty();
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
    StringBuilder buf = new StringBuilder();
    buf.append("X");

    boolean first = true;
    for (PhoneNumber phoneNumber : phoneNumbers) {
      if (!first) {
        buf.append(", ");
      } else {
        first = false;
      }
      buf.append(phoneNumber.toString());
    }

    buf.append("X");
    return buf.toString();
  }

  @Override
  public PhoneNumber getPhoneNumber(String phoneNumber) {
    for (PhoneNumber phoneNumberObject : phoneNumbers) {
      if (phoneNumberObject.getPhoneNumber().equals(phoneNumber)) {
        return phoneNumberObject;
      }
    }

    return null;
  }

} //PhoneNumberListImpl
