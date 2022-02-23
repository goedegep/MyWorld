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
 * A representation of the model object '<em><b>Phone Number</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.PhoneNumber#getPhoneNumber <em>Phone Number</em>}</li>
 *   <li>{@link goedegep.rolodex.model.PhoneNumber#getDescription <em>Description</em>}</li>
 *   <li>{@link goedegep.rolodex.model.PhoneNumber#getConnectionType <em>Connection Type</em>}</li>
 *   <li>{@link goedegep.rolodex.model.PhoneNumber#getNumberHolders <em>Number Holders</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getPhoneNumber()
 * @model
 * @generated
 */
public interface PhoneNumber extends EObject {
  /**
   * Returns the value of the '<em><b>Phone Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Phone Number</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Phone Number</em>' attribute.
   * @see #setPhoneNumber(String)
   * @see goedegep.rolodex.model.RolodexPackage#getPhoneNumber_PhoneNumber()
   * @model required="true"
   * @generated
   */
  String getPhoneNumber();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.PhoneNumber#getPhoneNumber <em>Phone Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Phone Number</em>' attribute.
   * @see #getPhoneNumber()
   * @generated
   */
  void setPhoneNumber(String value);

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
   * @see goedegep.rolodex.model.RolodexPackage#getPhoneNumber_Description()
   * @model
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.PhoneNumber#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
   * Returns the value of the '<em><b>Connection Type</b></em>' attribute.
   * The literals are from the enumeration {@link goedegep.rolodex.model.ConnectionType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Connection Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Connection Type</em>' attribute.
   * @see goedegep.rolodex.model.ConnectionType
   * @see #setConnectionType(ConnectionType)
   * @see goedegep.rolodex.model.RolodexPackage#getPhoneNumber_ConnectionType()
   * @model
   * @generated
   */
  ConnectionType getConnectionType();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.PhoneNumber#getConnectionType <em>Connection Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Connection Type</em>' attribute.
   * @see goedegep.rolodex.model.ConnectionType
   * @see #getConnectionType()
   * @generated
   */
  void setConnectionType(ConnectionType value);

  /**
   * Returns the value of the '<em><b>Number Holders</b></em>' reference list.
   * The list contents are of type {@link goedegep.rolodex.model.PhoneNumberHolder}.
   * It is bidirectional and its opposite is '{@link goedegep.rolodex.model.PhoneNumberHolder#getPhoneNumbers <em>Phone Numbers</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Number Holders</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Number Holders</em>' reference list.
   * @see goedegep.rolodex.model.RolodexPackage#getPhoneNumber_NumberHolders()
   * @see goedegep.rolodex.model.PhoneNumberHolder#getPhoneNumbers
   * @model opposite="phoneNumbers"
   * @generated
   */
  EList<PhoneNumberHolder> getNumberHolders();

} // PhoneNumber
