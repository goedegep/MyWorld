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
 * A representation of the model object '<em><b>Phone Number List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.PhoneNumberList#getPhoneNumbers <em>Phone Numbers</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getPhoneNumberList()
 * @model
 * @generated
 */
public interface PhoneNumberList extends EObject {
  /**
   * Returns the value of the '<em><b>Phone Numbers</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.rolodex.model.PhoneNumber}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Phone Numbers</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Phone Numbers</em>' containment reference list.
   * @see goedegep.rolodex.model.RolodexPackage#getPhoneNumberList_PhoneNumbers()
   * @model containment="true"
   * @generated
   */
  EList<PhoneNumber> getPhoneNumbers();

  public PhoneNumber getPhoneNumber(String phoneNumber);

} // PhoneNumberList
