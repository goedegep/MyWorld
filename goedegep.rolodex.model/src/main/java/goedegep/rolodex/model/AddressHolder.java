/**
 */
package goedegep.rolodex.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Address Holder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.AddressHolder#getAddress <em>Address</em>}</li>
 *   <li>{@link goedegep.rolodex.model.AddressHolder#getPreviousAddresses <em>Previous Addresses</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getAddressHolder()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface AddressHolder extends EObject {

  /**
   * Returns the value of the '<em><b>Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Address</em>' reference.
   * @see #setAddress(Address)
   * @see goedegep.rolodex.model.RolodexPackage#getAddressHolder_Address()
   * @model
   * @generated
   */
  Address getAddress();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.AddressHolder#getAddress <em>Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Address</em>' reference.
   * @see #getAddress()
   * @generated
   */
  void setAddress(Address value);

  /**
   * Returns the value of the '<em><b>Previous Addresses</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.rolodex.model.AddressForPeriod}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Previous Addresses</em>' containment reference list.
   * @see goedegep.rolodex.model.RolodexPackage#getAddressHolder_PreviousAddresses()
   * @model containment="true"
   * @generated
   */
  EList<AddressForPeriod> getPreviousAddresses();
} // AddressHolder
