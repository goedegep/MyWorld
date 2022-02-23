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
 * A representation of the model object '<em><b>Address</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An address. This can be a normal street address (visiting address), or a PO box address (mailing address).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.Address#getStreetName <em>Street Name</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Address#getHouseNumber <em>House Number</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Address#getHouseNumberExtension <em>House Number Extension</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Address#getCity <em>City</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Address#getPOBox <em>PO Box</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Address#getPostalCode <em>Postal Code</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Address#getId <em>Id</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getAddress()
 * @model
 * @generated
 */
public interface Address extends EObject {
  /**
   * Returns the value of the '<em><b>Street Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Street Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Street Name</em>' attribute.
   * @see #setStreetName(String)
   * @see goedegep.rolodex.model.RolodexPackage#getAddress_StreetName()
   * @model
   * @generated
   */
  String getStreetName();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Address#getStreetName <em>Street Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Street Name</em>' attribute.
   * @see #getStreetName()
   * @generated
   */
  void setStreetName(String value);

  /**
   * Returns the value of the '<em><b>House Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>House Number</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>House Number</em>' attribute.
   * @see #setHouseNumber(Integer)
   * @see goedegep.rolodex.model.RolodexPackage#getAddress_HouseNumber()
   * @model
   * @generated
   */
  Integer getHouseNumber();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Address#getHouseNumber <em>House Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>House Number</em>' attribute.
   * @see #getHouseNumber()
   * @generated
   */
  void setHouseNumber(Integer value);

  /**
   * Returns the value of the '<em><b>House Number Extension</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>House Number Extension</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>House Number Extension</em>' attribute.
   * @see #setHouseNumberExtension(String)
   * @see goedegep.rolodex.model.RolodexPackage#getAddress_HouseNumberExtension()
   * @model
   * @generated
   */
  String getHouseNumberExtension();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Address#getHouseNumberExtension <em>House Number Extension</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>House Number Extension</em>' attribute.
   * @see #getHouseNumberExtension()
   * @generated
   */
  void setHouseNumberExtension(String value);

  /**
   * Returns the value of the '<em><b>City</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>City</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>City</em>' reference.
   * @see #setCity(City)
   * @see goedegep.rolodex.model.RolodexPackage#getAddress_City()
   * @model
   * @generated
   */
  City getCity();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Address#getCity <em>City</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>City</em>' reference.
   * @see #getCity()
   * @generated
   */
  void setCity(City value);

  /**
   * Returns the value of the '<em><b>PO Box</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>PO Box</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>PO Box</em>' attribute.
   * @see #setPOBox(String)
   * @see goedegep.rolodex.model.RolodexPackage#getAddress_POBox()
   * @model
   * @generated
   */
  String getPOBox();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Address#getPOBox <em>PO Box</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>PO Box</em>' attribute.
   * @see #getPOBox()
   * @generated
   */
  void setPOBox(String value);

  /**
   * Returns the value of the '<em><b>Postal Code</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Postal Code</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Postal Code</em>' attribute.
   * @see #setPostalCode(String)
   * @see goedegep.rolodex.model.RolodexPackage#getAddress_PostalCode()
   * @model
   * @generated
   */
  String getPostalCode();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Address#getPostalCode <em>Postal Code</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Postal Code</em>' attribute.
   * @see #getPostalCode()
   * @generated
   */
  void setPostalCode(String value);

  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #setId(String)
   * @see goedegep.rolodex.model.RolodexPackage#getAddress_Id()
   * @model id="true" required="true" ordered="false"
   * @generated
   */
  String getId();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Address#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(String value);

} // Address
