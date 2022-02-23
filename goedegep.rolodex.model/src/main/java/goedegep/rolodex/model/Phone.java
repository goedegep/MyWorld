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
 * A representation of the model object '<em><b>Phone</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.Phone#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Phone#getPhoneType <em>Phone Type</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Phone#getPhoneAddressBook <em>Phone Address Book</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getPhone()
 * @model
 * @generated
 */
public interface Phone extends EObject {
  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #setDescription(String)
   * @see goedegep.rolodex.model.RolodexPackage#getPhone_Description()
   * @model required="true"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Phone#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
   * Returns the value of the '<em><b>Phone Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Phone Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Phone Type</em>' attribute.
   * @see #setPhoneType(String)
   * @see goedegep.rolodex.model.RolodexPackage#getPhone_PhoneType()
   * @model
   * @generated
   */
  String getPhoneType();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Phone#getPhoneType <em>Phone Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Phone Type</em>' attribute.
   * @see #getPhoneType()
   * @generated
   */
  void setPhoneType(String value);

  /**
   * Returns the value of the '<em><b>Phone Address Book</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Phone Address Book</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Phone Address Book</em>' containment reference.
   * @see #setPhoneAddressBook(PhoneAddressBook)
   * @see goedegep.rolodex.model.RolodexPackage#getPhone_PhoneAddressBook()
   * @model containment="true" required="true"
   * @generated
   */
  PhoneAddressBook getPhoneAddressBook();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Phone#getPhoneAddressBook <em>Phone Address Book</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Phone Address Book</em>' containment reference.
   * @see #getPhoneAddressBook()
   * @generated
   */
  void setPhoneAddressBook(PhoneAddressBook value);

} // Phone
