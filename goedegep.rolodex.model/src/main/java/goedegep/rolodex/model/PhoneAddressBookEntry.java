/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Phone Address Book Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.PhoneAddressBookEntry#getEntryName <em>Entry Name</em>}</li>
 *   <li>{@link goedegep.rolodex.model.PhoneAddressBookEntry#getPhoneNumber <em>Phone Number</em>}</li>
 *   <li>{@link goedegep.rolodex.model.PhoneAddressBookEntry#getEntryType <em>Entry Type</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getPhoneAddressBookEntry()
 * @model
 * @generated
 */
public interface PhoneAddressBookEntry extends EObject {
  /**
   * Returns the value of the '<em><b>Entry Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Entry Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Entry Name</em>' attribute.
   * @see #setEntryName(String)
   * @see goedegep.rolodex.model.RolodexPackage#getPhoneAddressBookEntry_EntryName()
   * @model
   * @generated
   */
  String getEntryName();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.PhoneAddressBookEntry#getEntryName <em>Entry Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Entry Name</em>' attribute.
   * @see #getEntryName()
   * @generated
   */
  void setEntryName(String value);

  /**
   * Returns the value of the '<em><b>Phone Number</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Phone Number</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Phone Number</em>' reference.
   * @see #setPhoneNumber(PhoneNumber)
   * @see goedegep.rolodex.model.RolodexPackage#getPhoneAddressBookEntry_PhoneNumber()
   * @model required="true"
   * @generated
   */
  PhoneNumber getPhoneNumber();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.PhoneAddressBookEntry#getPhoneNumber <em>Phone Number</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Phone Number</em>' reference.
   * @see #getPhoneNumber()
   * @generated
   */
  void setPhoneNumber(PhoneNumber value);

  /**
   * Returns the value of the '<em><b>Entry Type</b></em>' attribute.
   * The literals are from the enumeration {@link goedegep.rolodex.model.PhoneAddressBookEntryType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Entry Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Entry Type</em>' attribute.
   * @see goedegep.rolodex.model.PhoneAddressBookEntryType
   * @see #setEntryType(PhoneAddressBookEntryType)
   * @see goedegep.rolodex.model.RolodexPackage#getPhoneAddressBookEntry_EntryType()
   * @model
   * @generated
   */
  PhoneAddressBookEntryType getEntryType();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.PhoneAddressBookEntry#getEntryType <em>Entry Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Entry Type</em>' attribute.
   * @see goedegep.rolodex.model.PhoneAddressBookEntryType
   * @see #getEntryType()
   * @generated
   */
  void setEntryType(PhoneAddressBookEntryType value);

} // PhoneAddressBookEntry
