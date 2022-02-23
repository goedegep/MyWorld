/**
 */
package goedegep.rolodex.model;

import goedegep.util.datetime.FlexDate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Address For Period</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.AddressForPeriod#getFromDate <em>From Date</em>}</li>
 *   <li>{@link goedegep.rolodex.model.AddressForPeriod#getUntillDate <em>Untill Date</em>}</li>
 *   <li>{@link goedegep.rolodex.model.AddressForPeriod#getAddress <em>Address</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getAddressForPeriod()
 * @model
 * @generated
 */
public interface AddressForPeriod extends EObject {
  /**
   * Returns the value of the '<em><b>From Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>From Date</em>' attribute.
   * @see #isSetFromDate()
   * @see #unsetFromDate()
   * @see #setFromDate(FlexDate)
   * @see goedegep.rolodex.model.RolodexPackage#getAddressForPeriod_FromDate()
   * @model unsettable="true" dataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  FlexDate getFromDate();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.AddressForPeriod#getFromDate <em>From Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>From Date</em>' attribute.
   * @see #isSetFromDate()
   * @see #unsetFromDate()
   * @see #getFromDate()
   * @generated
   */
  void setFromDate(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.AddressForPeriod#getFromDate <em>From Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFromDate()
   * @see #getFromDate()
   * @see #setFromDate(FlexDate)
   * @generated
   */
  void unsetFromDate();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.AddressForPeriod#getFromDate <em>From Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>From Date</em>' attribute is set.
   * @see #unsetFromDate()
   * @see #getFromDate()
   * @see #setFromDate(FlexDate)
   * @generated
   */
  boolean isSetFromDate();

  /**
   * Returns the value of the '<em><b>Untill Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Untill Date</em>' attribute.
   * @see #isSetUntillDate()
   * @see #unsetUntillDate()
   * @see #setUntillDate(FlexDate)
   * @see goedegep.rolodex.model.RolodexPackage#getAddressForPeriod_UntillDate()
   * @model unsettable="true" dataType="goedegep.types.model.EFlexDate"
   * @generated
   */
  FlexDate getUntillDate();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.AddressForPeriod#getUntillDate <em>Untill Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Untill Date</em>' attribute.
   * @see #isSetUntillDate()
   * @see #unsetUntillDate()
   * @see #getUntillDate()
   * @generated
   */
  void setUntillDate(FlexDate value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.AddressForPeriod#getUntillDate <em>Untill Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetUntillDate()
   * @see #getUntillDate()
   * @see #setUntillDate(FlexDate)
   * @generated
   */
  void unsetUntillDate();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.AddressForPeriod#getUntillDate <em>Untill Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Untill Date</em>' attribute is set.
   * @see #unsetUntillDate()
   * @see #getUntillDate()
   * @see #setUntillDate(FlexDate)
   * @generated
   */
  boolean isSetUntillDate();

  /**
   * Returns the value of the '<em><b>Address</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Address</em>' reference.
   * @see #isSetAddress()
   * @see #unsetAddress()
   * @see #setAddress(Address)
   * @see goedegep.rolodex.model.RolodexPackage#getAddressForPeriod_Address()
   * @model unsettable="true"
   * @generated
   */
  Address getAddress();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.AddressForPeriod#getAddress <em>Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Address</em>' reference.
   * @see #isSetAddress()
   * @see #unsetAddress()
   * @see #getAddress()
   * @generated
   */
  void setAddress(Address value);

  /**
   * Unsets the value of the '{@link goedegep.rolodex.model.AddressForPeriod#getAddress <em>Address</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetAddress()
   * @see #getAddress()
   * @see #setAddress(Address)
   * @generated
   */
  void unsetAddress();

  /**
   * Returns whether the value of the '{@link goedegep.rolodex.model.AddressForPeriod#getAddress <em>Address</em>}' reference is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Address</em>' reference is set.
   * @see #unsetAddress()
   * @see #getAddress()
   * @see #setAddress(Address)
   * @generated
   */
  boolean isSetAddress();

} // AddressForPeriod
