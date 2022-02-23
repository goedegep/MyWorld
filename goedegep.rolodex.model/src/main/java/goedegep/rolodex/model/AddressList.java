/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Address List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A list of addresses.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.AddressList#getAddresses <em>Addresses</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getAddressList()
 * @model
 * @generated
 */
public interface AddressList extends EObject {
  /**
   * Returns the value of the '<em><b>Addresses</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.rolodex.model.Address}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Addresses</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Addresses</em>' containment reference list.
   * @see goedegep.rolodex.model.RolodexPackage#getAddressList_Addresses()
   * @model containment="true"
   * @generated
   */
  EList<Address> getAddresses();

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  Address findAddressById(String id);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  Address findAddressByPostalCode(String postalCode, Integer houseNumber, String houseNumberExtension);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  Address getAddress(String streetName, Integer houseNumber, String houseNumberExternsion, String postalCode,
      String city);

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @model
   * @generated
   */
  Address getAddress(String streetName, Integer houseNumber, String houseNumberExtension, String city);

  Address getAddress(String streetName, Integer houseNumber, String houseNumberExtension, String postalCode, City city);

  Address getAddress(String poBox, String postalCode, City city);

} // AddressList
