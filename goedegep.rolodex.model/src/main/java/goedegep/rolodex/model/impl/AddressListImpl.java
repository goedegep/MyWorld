/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model.impl;

import java.util.Collection;
import java.util.logging.Logger;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.AddressList;
import goedegep.rolodex.model.City;
import goedegep.rolodex.model.RolodexPackage;

import java.lang.reflect.InvocationTargetException;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Address List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.impl.AddressListImpl#getAddresses <em>Addresses</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AddressListImpl extends MinimalEObjectImpl.Container implements AddressList {
  private static final Logger LOGGER = Logger.getLogger(AddressListImpl.class.getName());

  /**
   * The cached value of the '{@link #getAddresses() <em>Addresses</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAddresses()
   * @generated
   * @ordered
   */
  protected EList<Address> addresses;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AddressListImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return RolodexPackage.Literals.ADDRESS_LIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Address> getAddresses() {
    if (addresses == null) {
      addresses = new EObjectContainmentEList<Address>(Address.class, this, RolodexPackage.ADDRESS_LIST__ADDRESSES);
    }
    return addresses;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Address findAddressById(String id) {
    for (Address address : getAddresses()) {
      AddressImpl addressImpl = (AddressImpl) address;
      if (addressImpl.eIsSet(RolodexPackage.ADDRESS__ID) && address.getId().equals(id)) {
        return address;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Address findAddressByPostalCode(String postalCode, Integer houseNumber, String houseNumberExtension) {
    LOGGER.info("=> postalCode = " + postalCode + ", houseNumber = " + houseNumber + ", houseNumberExtension = "
        + houseNumberExtension);
    for (Address address : getAddresses()) {
      AddressImpl addressImpl = (AddressImpl) address;
      LOGGER.info("Checking address: " + addressImpl);
      if (addressImpl.eIsSet(RolodexPackage.ADDRESS__POSTAL_CODE) && addressImpl.getPostalCode().equals(postalCode)) {
        LOGGER.info("Postal code OK");
        if (addressImpl.eIsSet(RolodexPackage.ADDRESS__HOUSE_NUMBER)
            && addressImpl.getHouseNumber().equals(houseNumber)) {
          LOGGER.info("house number OK");
          if (houseNumberExtension != null && addressImpl.eIsSet(RolodexPackage.ADDRESS__HOUSE_NUMBER_EXTENSION)
              && addressImpl.getHouseNumberExtension().equals(houseNumberExtension)) {
            LOGGER.info("=> " + addressImpl);
            return addressImpl;
          } else {
            LOGGER.info("=> " + addressImpl);
            return addressImpl;
          }
        }
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Address getAddress(String streetName, Integer houseNumber, String houseNumberExternsion, String postalCode,
      String city) {
    // TODO: implement this method
    // Ensure that you remove @generated or mark it @generated NOT
    throw new UnsupportedOperationException();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public Address getAddress(String streetName, Integer houseNumber, String houseNumberExtension, String city) {
    for (Address address : addresses) {
      if (valuesNotUnequal(address.getStreetName(), streetName)
          && valuesNotUnequal(address.getHouseNumber(), houseNumber)
          && valuesNotUnequal(address.getHouseNumberExtension(), houseNumberExtension)
          && valuesNotUnequal(address.getCity(), city)) {
        return address;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
    case RolodexPackage.ADDRESS_LIST__ADDRESSES:
      return ((InternalEList<?>) getAddresses()).basicRemove(otherEnd, msgs);
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
    case RolodexPackage.ADDRESS_LIST__ADDRESSES:
      return getAddresses();
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
    case RolodexPackage.ADDRESS_LIST__ADDRESSES:
      getAddresses().clear();
      getAddresses().addAll((Collection<? extends Address>) newValue);
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
    case RolodexPackage.ADDRESS_LIST__ADDRESSES:
      getAddresses().clear();
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
    case RolodexPackage.ADDRESS_LIST__ADDRESSES:
      return addresses != null && !addresses.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
    switch (operationID) {
    case RolodexPackage.ADDRESS_LIST___FIND_ADDRESS_BY_ID__STRING:
      return findAddressById((String) arguments.get(0));
    case RolodexPackage.ADDRESS_LIST___FIND_ADDRESS_BY_POSTAL_CODE__STRING_INTEGER_STRING:
      return findAddressByPostalCode((String) arguments.get(0), (Integer) arguments.get(1), (String) arguments.get(2));
    case RolodexPackage.ADDRESS_LIST___GET_ADDRESS__STRING_INTEGER_STRING_STRING_STRING:
      return getAddress((String) arguments.get(0), (Integer) arguments.get(1), (String) arguments.get(2),
          (String) arguments.get(3), (String) arguments.get(4));
    case RolodexPackage.ADDRESS_LIST___GET_ADDRESS__STRING_INTEGER_STRING_STRING:
      return getAddress((String) arguments.get(0), (Integer) arguments.get(1), (String) arguments.get(2),
          (String) arguments.get(3));
    }
    return super.eInvoke(operationID, arguments);
  }

  @Override
  public Address getAddress(String streetName, Integer houseNumber, String houseNumberExtension, String postalCode,
      City city) {
    for (Address address : addresses) {
      if (valuesNotUnequal(address.getStreetName(), streetName)
          && valuesNotUnequal(address.getHouseNumber(), houseNumber)
          && valuesNotUnequal(address.getHouseNumberExtension(), houseNumberExtension)
          && valuesNotUnequal(address.getPostalCode(), postalCode) && valuesNotUnequal(address.getCity(), city)) {
        return address;
      }
    }

    return null;
  }

  @Override
  public Address getAddress(String poBox, String postalCode, City city) {
    for (Address address : addresses) {
      if (valuesNotUnequal(address.getPOBox(), poBox) && valuesNotUnequal(address.getPostalCode(), postalCode)
          && valuesNotUnequal(address.getCity(), city)) {
        return address;
      }
    }

    return null;
  }

  private boolean valuesNotUnequal(Object string1, Object string2) {
    if (((string1 == null) && (string2 == null))
        || ((string1 != null) && (string2 != null) && (string1.equals(string2)))) {
      return true;
    } else {
      return false;
    }
  }

} //AddressListImpl
