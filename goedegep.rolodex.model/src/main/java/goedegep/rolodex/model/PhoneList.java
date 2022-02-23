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
 * A representation of the model object '<em><b>Phone List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.PhoneList#getPhones <em>Phones</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getPhoneList()
 * @model
 * @generated
 */
public interface PhoneList extends EObject {
  /**
   * Returns the value of the '<em><b>Phones</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.rolodex.model.Phone}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Phones</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Phones</em>' containment reference list.
   * @see goedegep.rolodex.model.RolodexPackage#getPhoneList_Phones()
   * @model containment="true"
   * @generated
   */
  EList<Phone> getPhones();

} // PhoneList
