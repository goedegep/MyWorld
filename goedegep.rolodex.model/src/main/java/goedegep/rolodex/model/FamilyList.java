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
 * A representation of the model object '<em><b>Family List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.FamilyList#getFamilies <em>Families</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getFamilyList()
 * @model
 * @generated
 */
public interface FamilyList extends EObject {
  /**
   * Returns the value of the '<em><b>Families</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.rolodex.model.Family}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Families</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Families</em>' containment reference list.
   * @see goedegep.rolodex.model.RolodexPackage#getFamilyList_Families()
   * @model containment="true"
   * @generated
   */
  EList<Family> getFamilies();

  Family getFamily(String familyName);

} // FamilyList
