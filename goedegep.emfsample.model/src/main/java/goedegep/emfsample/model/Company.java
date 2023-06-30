/**
 */
package goedegep.emfsample.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Company</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.emfsample.model.Company#getEmployees <em>Employees</em>}</li>
 *   <li>{@link goedegep.emfsample.model.Company#getBirthdays <em>Birthdays</em>}</li>
 * </ul>
 *
 * @see goedegep.emfsample.model.EmfSamplePackage#getCompany()
 * @model
 * @generated
 */
public interface Company extends EObject {
  /**
   * Returns the value of the '<em><b>Employees</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.emfsample.model.Person}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Employees</em>' containment reference list.
   * @see goedegep.emfsample.model.EmfSamplePackage#getCompany_Employees()
   * @model containment="true"
   * @generated
   */
  EList<Person> getEmployees();

  /**
   * Returns the value of the '<em><b>Birthdays</b></em>' reference list.
   * The list contents are of type {@link goedegep.emfsample.model.Birthday}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Birthdays</em>' reference list.
   * @see goedegep.emfsample.model.EmfSamplePackage#getCompany_Birthdays()
   * @model
   * @generated
   */
  EList<Birthday> getBirthdays();

} // Company
