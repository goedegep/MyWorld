/**
 */
package goedegep.emfsample.model;

import java.util.Date;
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
 *   <li>{@link goedegep.emfsample.model.Company#getFormerEmployees <em>Former Employees</em>}</li>
 *   <li>{@link goedegep.emfsample.model.Company#getCompanyName <em>Company Name</em>}</li>
 *   <li>{@link goedegep.emfsample.model.Company#getDateOfEstablishment <em>Date Of Establishment</em>}</li>
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

  /**
   * Returns the value of the '<em><b>Former Employees</b></em>' containment reference list.
   * The list contents are of type {@link goedegep.emfsample.model.Person}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Former Employees</em>' containment reference list.
   * @see goedegep.emfsample.model.EmfSamplePackage#getCompany_FormerEmployees()
   * @model containment="true"
   * @generated
   */
  EList<Person> getFormerEmployees();

  /**
   * Returns the value of the '<em><b>Company Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Company Name</em>' attribute.
   * @see #isSetCompanyName()
   * @see #unsetCompanyName()
   * @see #setCompanyName(String)
   * @see goedegep.emfsample.model.EmfSamplePackage#getCompany_CompanyName()
   * @model unsettable="true"
   * @generated
   */
  String getCompanyName();

  /**
   * Sets the value of the '{@link goedegep.emfsample.model.Company#getCompanyName <em>Company Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Company Name</em>' attribute.
   * @see #isSetCompanyName()
   * @see #unsetCompanyName()
   * @see #getCompanyName()
   * @generated
   */
  void setCompanyName(String value);

  /**
   * Unsets the value of the '{@link goedegep.emfsample.model.Company#getCompanyName <em>Company Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCompanyName()
   * @see #getCompanyName()
   * @see #setCompanyName(String)
   * @generated
   */
  void unsetCompanyName();

  /**
   * Returns whether the value of the '{@link goedegep.emfsample.model.Company#getCompanyName <em>Company Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Company Name</em>' attribute is set.
   * @see #unsetCompanyName()
   * @see #getCompanyName()
   * @see #setCompanyName(String)
   * @generated
   */
  boolean isSetCompanyName();

  /**
   * Returns the value of the '<em><b>Date Of Establishment</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Date Of Establishment</em>' attribute.
   * @see #isSetDateOfEstablishment()
   * @see #unsetDateOfEstablishment()
   * @see #setDateOfEstablishment(Date)
   * @see goedegep.emfsample.model.EmfSamplePackage#getCompany_DateOfEstablishment()
   * @model unsettable="true"
   * @generated
   */
  Date getDateOfEstablishment();

  /**
   * Sets the value of the '{@link goedegep.emfsample.model.Company#getDateOfEstablishment <em>Date Of Establishment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Date Of Establishment</em>' attribute.
   * @see #isSetDateOfEstablishment()
   * @see #unsetDateOfEstablishment()
   * @see #getDateOfEstablishment()
   * @generated
   */
  void setDateOfEstablishment(Date value);

  /**
   * Unsets the value of the '{@link goedegep.emfsample.model.Company#getDateOfEstablishment <em>Date Of Establishment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetDateOfEstablishment()
   * @see #getDateOfEstablishment()
   * @see #setDateOfEstablishment(Date)
   * @generated
   */
  void unsetDateOfEstablishment();

  /**
   * Returns whether the value of the '{@link goedegep.emfsample.model.Company#getDateOfEstablishment <em>Date Of Establishment</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Date Of Establishment</em>' attribute is set.
   * @see #unsetDateOfEstablishment()
   * @see #getDateOfEstablishment()
   * @see #setDateOfEstablishment(Date)
   * @generated
   */
  boolean isSetDateOfEstablishment();

} // Company
