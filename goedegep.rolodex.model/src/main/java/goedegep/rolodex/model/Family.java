/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Family</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.Family#getFamilyTitle <em>Family Title</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Family#getFamilyName <em>Family Name</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Family#getMembers <em>Members</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getFamily()
 * @model
 * @generated
 */
public interface Family extends PhoneNumberHolder, AddressHolder, Description, Archive {
  /**
   * Returns the value of the '<em><b>Family Title</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Family Title</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Family Title</em>' attribute.
   * @see #setFamilyTitle(String)
   * @see goedegep.rolodex.model.RolodexPackage#getFamily_FamilyTitle()
   * @model
   * @generated
   */
  String getFamilyTitle();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Family#getFamilyTitle <em>Family Title</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Family Title</em>' attribute.
   * @see #getFamilyTitle()
   * @generated
   */
  void setFamilyTitle(String value);

  /**
   * Returns the value of the '<em><b>Family Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Family Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Family Name</em>' attribute.
   * @see #setFamilyName(String)
   * @see goedegep.rolodex.model.RolodexPackage#getFamily_FamilyName()
   * @model
   * @generated
   */
  String getFamilyName();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Family#getFamilyName <em>Family Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Family Name</em>' attribute.
   * @see #getFamilyName()
   * @generated
   */
  void setFamilyName(String value);

  /**
   * Returns the value of the '<em><b>Members</b></em>' reference list.
   * The list contents are of type {@link goedegep.rolodex.model.Person}.
   * It is bidirectional and its opposite is '{@link goedegep.rolodex.model.Person#getFamily <em>Family</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Members</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Members</em>' reference list.
   * @see goedegep.rolodex.model.RolodexPackage#getFamily_Members()
   * @see goedegep.rolodex.model.Person#getFamily
   * @model opposite="family" required="true"
   * @generated
   */
  EList<Person> getMembers();

} // Family
