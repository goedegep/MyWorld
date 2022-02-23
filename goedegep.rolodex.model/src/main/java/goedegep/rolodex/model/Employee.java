/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package goedegep.rolodex.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Employee</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An employee is a <code>Person</code> working at an <code>Institution</code>.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.Employee#getInstitution <em>Institution</em>}</li>
 *   <li>{@link goedegep.rolodex.model.Employee#getPerson <em>Person</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getEmployee()
 * @model
 * @generated
 */
public interface Employee extends PhoneNumberHolder {
  /**
   * Returns the value of the '<em><b>Institution</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Institution</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Institution</em>' reference.
   * @see #setInstitution(Institution)
   * @see goedegep.rolodex.model.RolodexPackage#getEmployee_Institution()
   * @model required="true"
   * @generated
   */
  Institution getInstitution();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Employee#getInstitution <em>Institution</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Institution</em>' reference.
   * @see #getInstitution()
   * @generated
   */
  void setInstitution(Institution value);

  /**
   * Returns the value of the '<em><b>Person</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Person</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Person</em>' reference.
   * @see #setPerson(Person)
   * @see goedegep.rolodex.model.RolodexPackage#getEmployee_Person()
   * @model required="true"
   * @generated
   */
  Person getPerson();

  /**
   * Sets the value of the '{@link goedegep.rolodex.model.Employee#getPerson <em>Person</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Person</em>' reference.
   * @see #getPerson()
   * @generated
   */
  void setPerson(Person value);

} // Employee
