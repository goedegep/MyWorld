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
 * A representation of the model object '<em><b>Institution List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.InstitutionList#getInstitutions <em>Institutions</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getInstitutionList()
 * @model
 * @generated
 */
public interface InstitutionList extends EObject {
  /**
   * Returns the value of the '<em><b>Institutions</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.rolodex.model.Institution}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Institutions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Institutions</em>' containment reference list.
   * @see goedegep.rolodex.model.RolodexPackage#getInstitutionList_Institutions()
   * @model containment="true"
   * @generated
   */
  EList<Institution> getInstitutions();

  Institution getInstitution(String name);

} // InstitutionList
