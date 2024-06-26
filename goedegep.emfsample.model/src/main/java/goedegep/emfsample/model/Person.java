/**
 */
package goedegep.emfsample.model;

import java.util.Date;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.emfsample.model.Person#getFirstnames <em>Firstnames</em>}</li>
 *   <li>{@link goedegep.emfsample.model.Person#getSurname <em>Surname</em>}</li>
 *   <li>{@link goedegep.emfsample.model.Person#getGender <em>Gender</em>}</li>
 *   <li>{@link goedegep.emfsample.model.Person#getBirthday <em>Birthday</em>}</li>
 *   <li>{@link goedegep.emfsample.model.Person#getRetirementDate <em>Retirement Date</em>}</li>
 *   <li>{@link goedegep.emfsample.model.Person#isHasChildren <em>Has Children</em>}</li>
 * </ul>
 *
 * @see goedegep.emfsample.model.EmfSamplePackage#getPerson()
 * @model
 * @generated
 */
public interface Person extends EObject {
  /**
   * Returns the value of the '<em><b>Firstnames</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Firstnames</em>' attribute list.
   * @see #isSetFirstnames()
   * @see #unsetFirstnames()
   * @see goedegep.emfsample.model.EmfSamplePackage#getPerson_Firstnames()
   * @model unsettable="true"
   * @generated
   */
  EList<String> getFirstnames();

  /**
   * Unsets the value of the '{@link goedegep.emfsample.model.Person#getFirstnames <em>Firstnames</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetFirstnames()
   * @see #getFirstnames()
   * @generated
   */
  void unsetFirstnames();

  /**
   * Returns whether the value of the '{@link goedegep.emfsample.model.Person#getFirstnames <em>Firstnames</em>}' attribute list is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Firstnames</em>' attribute list is set.
   * @see #unsetFirstnames()
   * @see #getFirstnames()
   * @generated
   */
  boolean isSetFirstnames();

  /**
   * Returns the value of the '<em><b>Surname</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Surname</em>' attribute.
   * @see #isSetSurname()
   * @see #unsetSurname()
   * @see #setSurname(String)
   * @see goedegep.emfsample.model.EmfSamplePackage#getPerson_Surname()
   * @model unsettable="true"
   * @generated
   */
  String getSurname();

  /**
   * Sets the value of the '{@link goedegep.emfsample.model.Person#getSurname <em>Surname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Surname</em>' attribute.
   * @see #isSetSurname()
   * @see #unsetSurname()
   * @see #getSurname()
   * @generated
   */
  void setSurname(String value);

  /**
   * Unsets the value of the '{@link goedegep.emfsample.model.Person#getSurname <em>Surname</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetSurname()
   * @see #getSurname()
   * @see #setSurname(String)
   * @generated
   */
  void unsetSurname();

  /**
   * Returns whether the value of the '{@link goedegep.emfsample.model.Person#getSurname <em>Surname</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Surname</em>' attribute is set.
   * @see #unsetSurname()
   * @see #getSurname()
   * @see #setSurname(String)
   * @generated
   */
  boolean isSetSurname();

  /**
   * Returns the value of the '<em><b>Gender</b></em>' attribute.
   * The literals are from the enumeration {@link goedegep.emfsample.model.Gender}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Gender</em>' attribute.
   * @see goedegep.emfsample.model.Gender
   * @see #isSetGender()
   * @see #unsetGender()
   * @see #setGender(Gender)
   * @see goedegep.emfsample.model.EmfSamplePackage#getPerson_Gender()
   * @model unsettable="true"
   * @generated
   */
  Gender getGender();

  /**
   * Sets the value of the '{@link goedegep.emfsample.model.Person#getGender <em>Gender</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Gender</em>' attribute.
   * @see goedegep.emfsample.model.Gender
   * @see #isSetGender()
   * @see #unsetGender()
   * @see #getGender()
   * @generated
   */
  void setGender(Gender value);

  /**
   * Unsets the value of the '{@link goedegep.emfsample.model.Person#getGender <em>Gender</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetGender()
   * @see #getGender()
   * @see #setGender(Gender)
   * @generated
   */
  void unsetGender();

  /**
   * Returns whether the value of the '{@link goedegep.emfsample.model.Person#getGender <em>Gender</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Gender</em>' attribute is set.
   * @see #unsetGender()
   * @see #getGender()
   * @see #setGender(Gender)
   * @generated
   */
  boolean isSetGender();

  /**
   * Returns the value of the '<em><b>Birthday</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Birthday</em>' containment reference.
   * @see #setBirthday(Birthday)
   * @see goedegep.emfsample.model.EmfSamplePackage#getPerson_Birthday()
   * @model containment="true"
   * @generated
   */
  Birthday getBirthday();

  /**
   * Sets the value of the '{@link goedegep.emfsample.model.Person#getBirthday <em>Birthday</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Birthday</em>' containment reference.
   * @see #getBirthday()
   * @generated
   */
  void setBirthday(Birthday value);

  /**
   * Returns the value of the '<em><b>Retirement Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Retirement Date</em>' attribute.
   * @see #isSetRetirementDate()
   * @see #unsetRetirementDate()
   * @see #setRetirementDate(Date)
   * @see goedegep.emfsample.model.EmfSamplePackage#getPerson_RetirementDate()
   * @model unsettable="true"
   * @generated
   */
  Date getRetirementDate();

  /**
   * Sets the value of the '{@link goedegep.emfsample.model.Person#getRetirementDate <em>Retirement Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Retirement Date</em>' attribute.
   * @see #isSetRetirementDate()
   * @see #unsetRetirementDate()
   * @see #getRetirementDate()
   * @generated
   */
  void setRetirementDate(Date value);

  /**
   * Unsets the value of the '{@link goedegep.emfsample.model.Person#getRetirementDate <em>Retirement Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetRetirementDate()
   * @see #getRetirementDate()
   * @see #setRetirementDate(Date)
   * @generated
   */
  void unsetRetirementDate();

  /**
   * Returns whether the value of the '{@link goedegep.emfsample.model.Person#getRetirementDate <em>Retirement Date</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Retirement Date</em>' attribute is set.
   * @see #unsetRetirementDate()
   * @see #getRetirementDate()
   * @see #setRetirementDate(Date)
   * @generated
   */
  boolean isSetRetirementDate();

  /**
   * Returns the value of the '<em><b>Has Children</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Has Children</em>' attribute.
   * @see #isSetHasChildren()
   * @see #unsetHasChildren()
   * @see #setHasChildren(boolean)
   * @see goedegep.emfsample.model.EmfSamplePackage#getPerson_HasChildren()
   * @model unsettable="true"
   * @generated
   */
  boolean isHasChildren();

  /**
   * Sets the value of the '{@link goedegep.emfsample.model.Person#isHasChildren <em>Has Children</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Has Children</em>' attribute.
   * @see #isSetHasChildren()
   * @see #unsetHasChildren()
   * @see #isHasChildren()
   * @generated
   */
  void setHasChildren(boolean value);

  /**
   * Unsets the value of the '{@link goedegep.emfsample.model.Person#isHasChildren <em>Has Children</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetHasChildren()
   * @see #isHasChildren()
   * @see #setHasChildren(boolean)
   * @generated
   */
  void unsetHasChildren();

  /**
   * Returns whether the value of the '{@link goedegep.emfsample.model.Person#isHasChildren <em>Has Children</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Has Children</em>' attribute is set.
   * @see #unsetHasChildren()
   * @see #isHasChildren()
   * @see #setHasChildren(boolean)
   * @generated
   */
  boolean isSetHasChildren();

} // Person
