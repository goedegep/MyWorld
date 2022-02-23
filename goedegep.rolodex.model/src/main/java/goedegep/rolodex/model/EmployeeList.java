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
 * A representation of the model object '<em><b>Employee List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.rolodex.model.EmployeeList#getEmployees <em>Employees</em>}</li>
 * </ul>
 *
 * @see goedegep.rolodex.model.RolodexPackage#getEmployeeList()
 * @model
 * @generated
 */
public interface EmployeeList extends EObject {
  /**
   * Returns the value of the '<em><b>Employees</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.rolodex.model.Employee}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Employees</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Employees</em>' containment reference list.
   * @see goedegep.rolodex.model.RolodexPackage#getEmployeeList_Employees()
   * @model containment="true"
   * @generated
   */
  EList<Employee> getEmployees();

  Employee getEmployee(Institution institution, Person person);

} // EmployeeList
