/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Institution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An institution can be used for anything that isn't a person, like a company, school, shop, ....
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.Institution#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Institution#getMailingAddress <em>Mailing Address</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getInstitution()
 * @model
 * @generated
 */
public interface Institution extends PhoneNumberHolder, AddressHolder, Archive {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see goedegep.rolodex.model.RolodexPackage#getInstitution_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Institution#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Mailing Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mailing Address</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mailing Address</em>' reference.
   * @see #isSetMailingAddress()
   * @see #unsetMailingAddress()
   * @see #setMailingAddress(Address)
   * @see goedegep.rolodex.model.RolodexPackage#getInstitution_MailingAddress()
   * @model unsettable="true"
   * @generated
   */
  Address getMailingAddress();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Institution#getMailingAddress <em>Mailing Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mailing Address</em>' reference.
   * @see #isSetMailingAddress()
   * @see #unsetMailingAddress()
   * @see #getMailingAddress()
   * @generated
   */
  void setMailingAddress(Address value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.Institution#getMailingAddress <em>Mailing Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMailingAddress()
   * @see #getMailingAddress()
   * @see #setMailingAddress(Address)
   * @generated
   */
  void unsetMailingAddress();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.Institution#getMailingAddress <em>Mailing Address</em>}' reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Mailing Address</em>' reference is set.
   * @see #unsetMailingAddress()
   * @see #getMailingAddress()
   * @see #setMailingAddress(Address)
   * @generated
   */
  boolean isSetMailingAddress();

} // Institution
